package com.wonkystar.phonegap.infantium;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.infantium.android.sdk.Animation;
import com.infantium.android.sdk.DynamicField;
import com.infantium.android.sdk.Element;
import com.infantium.android.sdk.InfantiumResponse;
import com.infantium.android.sdk.Infantium_SDK;
import com.infantium.android.sdk.Sound;
import com.infantium.android.sdk.Target;


/***
	This class handles the communication between the JS part of the plugin
	and Infantium's Android SDK. As part of Phonegap's plugin architecture
	the "execute" method is the entry point. It's called with the "action"
	string which will help determine what to do here.

	Except for the "setup" method, all methods expect a JS object 
	(or array of objects) as argument. This class takes care of transforming 
	the object/array into the corresponding Infantium classes (Element, 
	DynamicField etc.). All the object field names are the ones given in 
	Infantium's constructors/methods signature.
***/
public class Infantium extends CordovaPlugin {

	// We get our handler accessible class-wise to set the callbacks when needed
	private InfantiumResponseHandler infantiumHandler = new InfantiumResponseHandler();
	private Infantium_SDK infantium;
	
	@Override
	public boolean execute (String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
		
		// There seems to be some problems if the variable is initialised when the class is instanciated,
		// therefore we get Infantium's singleton on every call
		infantium = Infantium_SDK.getInfantium_SDK(cordova.getActivity());
		
		if (action.equals("generateUUID"))
		{
			callbackContext.success(UUID.randomUUID().toString());
			return true;
		}
		else if (action.equals("setup"))
		{
			this.setup(args.getString(0), args.getString(1), args.getInt(2), args.getInt(3), callbackContext);
			return true;
		}
		else if (action.equals("setContentAppUUID"))
		{
			infantiumHandler.setContentAppCallback(callbackContext);
			infantium.setContentAppUUID(args.getString(0));
			return true;
		}
		else if (action.equals("getPlayerUUIDFromApp"))
		{
			infantiumHandler.setPlayerUUIDCallback(callbackContext);
			infantium.getPlayerUUIDFromApp();
			return true;
		}
		else if (action.equals("createGameplay"))
		{
			infantiumHandler.setCreateGameplayCallback(callbackContext);
			infantium.createGameplay(args.getString(0));
			return true;
		}
		else if (action.equals("startPlaying"))
		{
			infantium.startPlaying();
			callbackContext.success();
			return true;
		}
		else if (action.equals("sendGameRawData"))
		{
			infantiumHandler.setGameRawdataCallback(callbackContext);
			infantium.sendGameRawData();			
			return true;
		}
		else if (action.equals("closeGameplay"))
		{
			infantiumHandler.setCloseGameplayCallback(callbackContext);
			infantium.closeGameplay();
			return true;
		}
		else if (action.equals("addElement"))
		{
			this.processResponse(infantium.addElement(this.toElement(args.getJSONObject(0))), callbackContext);
			return true;
		}
		else if (action.equals("addElements"))
		{
			this.processResponse(infantium.addElements(this.toElementList(args.getJSONArray(0))), callbackContext);
			return true;
		}
		else if (action.equals("tapNoObjects"))
		{
			JSONObject o = args.getJSONObject(0);
			
			if (o.has("sound_id")) {
				this.processResponse(infantium.tapNoObjects(this.toIntArray(o.getJSONArray("position")), o.getString("output"), o.getString("sound_id")), callbackContext);
			}
			else {
				this.processResponse(infantium.tapNoObjects(this.toIntArray(o.getJSONArray("position")), o.getString("output")), callbackContext);
			}
			
			return true;
		}
		else if (action.equals("tapOnObjects"))
		{
			JSONObject o = args.getJSONObject(0);
			
			if (o.has("sound_id") && o.has("extra_fields")) {
				this.processResponse(infantium.tapOnObjects(o.getString("element_id"), o.getString("output"), o.getString("sound_id"), this.toDynamicFieldList(o.getJSONArray("extra_fields"))), callbackContext);
			}
			else if (o.has("sound_id")) {
				this.processResponse(infantium.tapOnObjects(o.getString("element_id"), o.getString("output"), o.getString("sound_id")), callbackContext);
			}
			else if (o.has("extra_fields")) {
				this.processResponse(infantium.tapOnObjects(o.getString("element_id"), o.getString("output"), this.toDynamicFieldList(o.getJSONArray("extra_fields"))), callbackContext);
			}
			else {
				this.processResponse(infantium.tapOnObjects(o.getString("element_id"), o.getString("output")), callbackContext);
			}
			
			return true;
		}
		else if (action.equals("setSuccesses"))
		{
			this.processResponse(infantium.setSuccesses(args.getInt(0)), callbackContext);
			return true;
		}
		else if (action.equals("setFailures"))
		{
			this.processResponse(infantium.setFailures(args.getInt(0)), callbackContext);
			return true;
		}
		else if (action.equals("setTarget"))
		{
			this.processResponse(infantium.setTarget(this.toTarget(args.getJSONObject(0))), callbackContext);
			return true;
		}
		else if (action.equals("setTargets"))
		{
			this.processResponse(infantium.setTargets(this.toTargetList(args.getJSONArray(0))), callbackContext);
			return true;
		}
		else if (action.equals("setEvaluate"))
		{
			this.processResponse(infantium.setEvaluate(this.toStringArray(args.getJSONArray(0))), callbackContext);
			return true;
		}
		else if (action.equals("addSound"))
		{
			this.processResponse(infantium.addSound(this.toSound(args.getJSONObject(0))), callbackContext);
			return true;
		}
		else if (action.equals("addSounds"))
		{
			this.processResponse(infantium.addSounds(this.toSoundList(args.getJSONArray(0))), callbackContext);
			return true;
		}
		else if (action.equals("addFixedAnimation"))
		{
			this.processResponse(infantium.addFixedAnimation(this.toAnimation(args.getJSONObject(0))), callbackContext);
			return true;
		}
		else if (action.equals("addFixedAnimations"))
		{
			this.processResponse(infantium.addFixedAnimations(this.toAnimationList(args.getJSONArray(0))), callbackContext);
			return true;
		}
		else if (action.equals("addDynamicField"))
		{
			this.processResponse(infantium.addDynamicField(this.toDynamicField(args.getJSONObject(0))), callbackContext);
			return true;
		}
		else if (action.equals("addDynamicFields"))
		{
			this.processResponse(infantium.addDynamicFields(this.toDynamicFieldList(args.getJSONArray(0))), callbackContext);
			return true;
		}
		else if (action.equals("startAnimation"))
		{
			JSONObject o = args.getJSONObject(0);

			this.processResponse(infantium.startAnimation(o.getString("element_id"), this.toIntArray(o.getJSONArray("st_pos")), o.getString("type")), callbackContext);
			return true;
		}
		else if (action.equals("endAnimation"))
		{
			JSONObject o = args.getJSONObject(0);
			
			if (o.has("sound_id") && o.has("end_pos")) {
				this.processResponse(infantium.endAnimation(o.getString("element_id"), o.getString("sound_id"), this.toIntArray(o.getJSONArray("end_pos"))), callbackContext);
			}
			else if (o.has("sound_id")) {
				this.processResponse(infantium.endAnimation(o.getString("element_id"), o.getString("sound_id")), callbackContext);
			}
			else if (o.has("end_pos")) {
				this.processResponse(infantium.endAnimation(o.getString("element_id"), this.toIntArray(o.getJSONArray("end_pos"))), callbackContext);
			}
			else {
				this.processResponse(infantium.endAnimation(o.getString("element_id")), callbackContext);
			}
			
			return true;
		}
		else if (action.equals("startDragging"))
		{
			JSONObject o = args.getJSONObject(0);
			this.processResponse(infantium.startDragging(o.getString("element_id"), this.toIntArray(o.getJSONArray("position"))), callbackContext);
			return true;
		}
		else if (action.equals("finishDragging"))
		{
			JSONObject o = args.getJSONObject(0);

			if (o.has("max_x") && o.has("max_y") && o.has("sound_id")) {
				this.processResponse(infantium.finishDragging(this.toIntArray(o.getJSONArray("position")), o.getString("output"), o.getString("sound_id"), o.getInt("max_x"), o.getInt("max_y")), callbackContext);
			}
			else if (o.has("max_x") && o.has("max_y")) {
				this.processResponse(infantium.finishDragging(this.toIntArray(o.getJSONArray("position")), o.getString("output"), o.getInt("max_x"), o.getInt("max_y")), callbackContext);
			}
			else if (o.has("sound_id")) {
				this.processResponse(infantium.finishDragging(this.toIntArray(o.getJSONArray("position")), o.getString("output"), o.getString("sound_id")), callbackContext);
			}
			else {
				this.processResponse(infantium.finishDragging(this.toIntArray(o.getJSONArray("position")), o.getString("output")), callbackContext);
			}
			
			return true;
		}
		else if (action.equals("returnToInfantiumApp"))
		{
			if (infantium.returnToInfantiumApp(cordova.getActivity())) {
				callbackContext.success();
			}
			else {
				callbackContext.error("Failed to return to Infantium app");
			}
			
			return true;
		}
		else if (action.equals("onResumeInfantium"))
		{
			infantium.onResumeInfantium();
			callbackContext.success();
			return true;
		}
		else if (action.equals("onPauseInfantium"))
		{
			infantium.onPauseInfantium();
			callbackContext.success();			
			return true;
		}
		
		return false;
	}


	// Does the 3 necessary calls to stqart using the SDK
	private void setup (String api_user, String api_key, int w_dev, int h_dev, CallbackContext callbackContext) {
				
		infantium.setDeveloperCredentials(api_user, api_key);
		infantium.setDeviceInfo(w_dev, h_dev);
		infantium.setDeveloperHandler(this.infantiumHandler);
		
		callbackContext.success();
	}


	// Shortcut to handle InfantiumResponses
	private void processResponse (InfantiumResponse response, CallbackContext callbackContext) {
		
		if (response.equals(InfantiumResponse.Valid)) {
			callbackContext.success();
		}
		else {
			callbackContext.error(response.toString());
		}
	}


	// Single object converters

	private Element toElement (JSONObject o) throws JSONException {
		
		String id = o.getString("id");
		String type = o.getString("type");
		String visual_type = o.getString("visual_type");
		ArrayList<Integer> size = this.toIntArray(o.getJSONArray("size"));
		String color = o.getString("color");
		String category = o.getString("category");;
		String subcategory = o.getString("subcategory");
		ArrayList<Integer> pos = this.toIntArray(o.getJSONArray("pos")); 
		float visibility = Float.parseFloat(o.getString("visibility"));
		
		Element el = new Element(id, type, visual_type, size, color, category, subcategory, pos, visibility);;
				
		if (o.has("dimension")) {
			el.setDimension(o.getString("dimension"));
		}
		
		if (o.has("alpha")) {
			el.setAlpha(Float.parseFloat(o.getString("alpha")));
		}
		
		if (o.has("extra_fields")) {
			el.setExtra_fields(this.toDynamicFieldList(o.getJSONArray("extra_fields")));
		}
		
		return el;
	}
	
	private Target toTarget (JSONObject o) throws JSONException {
		
		return new Target(o.getString("element_id"), o.getString("goal"));
	}
	
	private Sound toSound (JSONObject o) throws JSONException {
		
		Sound sound = new Sound(o.getString("id"));
		
		if (o.has("subcategory")) {
			sound.setSubcategory(o.getString("subcategory"));
		}
		
		if (o.has("category")) {
			sound.setCategory(o.getString("category"));
		}
		
		return sound;
	}
	
	private DynamicField toDynamicField (JSONObject o) throws JSONException {
				
		String name = o.getString("name");
		String type = o.getString("type");
		String value = o.getString("value");
				
		DynamicField dynamicField = new DynamicField(name, type, value);
		
		if (o.has("category")) {
			dynamicField.setCategory(o.getString("category"));
		}
		
		if (o.has("subcategory")) {
			dynamicField.setCategory(o.getString("subcategory"));
		}
		
		if (o.has("fields")) {
			dynamicField.setFields(this.toDynamicFieldList(o.getJSONArray("fields")));
		}

		return dynamicField;
	}
	
	private Animation toAnimation (JSONObject o) throws JSONException {
		
		String element_id = o.getString("element_id");
		ArrayList<Integer> st_pos = this.toIntArray(o.getJSONArray("st_pos"));
		ArrayList<Integer> end_pos = this.toIntArray(o.getJSONArray("end_pos"));
		String type = o.getString("type");
		long duration = o.getLong("duration");
		
		Animation anim = new Animation(element_id, st_pos, end_pos, type, duration);
		
		if (o.has("max_x") && o.has("max_y")) {
			anim.setMax_x(o.getInt("max_x"));
			anim.setMax_y(o.getInt("max_y"));
		}
		
		if (o.has("sound_id")) {
			anim.setSound_id(o.getString("sound_id"));
		}

		return anim;
	}


	// List/ArrayList converters 
	
	private ArrayList<Integer> toIntArray (JSONArray jArr) throws JSONException {
		
		ArrayList<Integer> arr = new ArrayList<Integer>();
		
		for (int i = 0; i < jArr.length(); i++) {
			arr.add(jArr.getInt(i));
		}
		
		return arr;
	}
	
	private ArrayList<String> toStringArray (JSONArray jArr) throws JSONException {
		
		ArrayList<String> arr = new ArrayList<String>();
		
		for (int i = 0; i < jArr.length(); i++) {
			arr.add(jArr.getString(i));
		}
		
		return arr;
	}
	
	private List<Element> toElementList (JSONArray jArr) throws JSONException {
		
		List<Element> list = new ArrayList<Element>();
		
		for (int i = 0; i < jArr.length(); i++) {
			list.add(this.toElement(jArr.getJSONObject(i)));
		}
		
		return list;
	}
	
	private List<Target> toTargetList (JSONArray jArr) throws JSONException {
		
		List<Target> list = new ArrayList<Target>();
		
		for (int i = 0; i < jArr.length(); i++) {
			list.add(this.toTarget(jArr.getJSONObject(i)));
		}
		
		return list;
	}
	
	private List<Sound> toSoundList (JSONArray jArr) throws JSONException {
		
		List<Sound> list = new ArrayList<Sound>();
		
		for (int i = 0; i < jArr.length(); i++) {
			list.add(this.toSound(jArr.getJSONObject(i)));
		}
		
		return list;
	}
	
	private List<Animation> toAnimationList (JSONArray jArr) throws JSONException {
		
		List<Animation> list = new ArrayList<Animation>();
		
		for (int i = 0; i < jArr.length(); i++) {
			list.add(this.toAnimation(jArr.getJSONObject(i)));
		}
		
		return list;
	}
	
	private ArrayList<DynamicField> toDynamicFieldList (JSONArray jArr) throws JSONException {
		
		ArrayList<DynamicField> list = new ArrayList<DynamicField>();
		
		for (int i = 0; i < jArr.length(); i++) {
			list.add(this.toDynamicField(jArr.getJSONObject(i)));
		}
		
		return list;
	}
}