package com.wonkystar.phonegap.infantium;

import org.apache.cordova.CallbackContext;

import com.infantium.android.sdk.InfantiumAsyncResponseHandler;


/***
	We're extending InfantiumAsyncResponseHandler to handle
	the inclusion of Phonegap's callbacks into Infantium's.

	Each callback has its own variable to allow making multiple
	concurrent calls to different methods. 

	We're always checking if the callback has been set first in
	case Infantium has some old calls in its database and the
	callbacks are not set anymore (app has been killed etc.).
***/
public class InfantiumResponseHandler extends InfantiumAsyncResponseHandler {

	private CallbackContext callbackContentApp;
	private CallbackContext callbackPlayerUUID;
	private CallbackContext callbackCreateGameplay;
	private CallbackContext callbackCloseGameplay;
	private CallbackContext callbackGameRawdata;
	
	public void setContentAppCallback(CallbackContext callback) {
		this.callbackContentApp = callback;
	}
	
	public void setPlayerUUIDCallback(CallbackContext callback) {
		this.callbackPlayerUUID = callback;
	}
	
	public void setCreateGameplayCallback(CallbackContext callback) {
		this.callbackCreateGameplay = callback;
	}
	
	public void setCloseGameplayCallback(CallbackContext callback) {
		this.callbackCloseGameplay = callback;
	}
	
	public void setGameRawdataCallback(CallbackContext callback) {
		this.callbackGameRawdata = callback;
	}
	
	@Override
	public void onSuccessContentApp() {
		
		if (this.callbackContentApp != null) {
			this.callbackContentApp.success();
			this.callbackContentApp = null;
		}
	}

	@Override
	public void onFailureContentApp(String description) {
		
		if (this.callbackContentApp != null) {
			this.callbackContentApp.error(description);
			this.callbackContentApp = null;
		}
	}

	@Override
	public void onSuccessGetPlayerByUUID() {
		
		if (this.callbackPlayerUUID != null) {
			this.callbackPlayerUUID.success();
			this.callbackPlayerUUID = null;
		}
	}
	
	@Override
	public void onFailureGetPlayerByUUID(String description) {
		
		if (this.callbackPlayerUUID != null) {
			this.callbackPlayerUUID.error(description);
			this.callbackPlayerUUID = null;
		}
	}

	@Override
	public void onSuccessCreateGameplay() {

		if (this.callbackCreateGameplay != null) {
			this.callbackCreateGameplay.success();
			this.callbackCreateGameplay = null;
		}
	}

	@Override
	public void onFailureCreateGameplay(String description) {
				
		if (this.callbackCreateGameplay != null) {
			this.callbackCreateGameplay.error(description);
			this.callbackCreateGameplay = null;
		}
	}

	@Override
	public void onSuccessGameRawData() {

		if (this.callbackGameRawdata != null) {
			this.callbackGameRawdata.success();
			this.callbackGameRawdata = null;
		}
	}

	@Override
	public void onFailureGameRawdata(String description) {

		if (this.callbackGameRawdata != null) {
			this.callbackGameRawdata.error(description);
			this.callbackGameRawdata = null;
		}
	}

	@Override
	public void onSuccessCloseGameplay() {
		
		if (this.callbackCloseGameplay != null) {
			this.callbackCloseGameplay.success();
			this.callbackCloseGameplay = null;
		}
	}

	@Override
	public void onFailureCloseGameplay(String description) {

		if (this.callbackCloseGameplay != null) {
			this.callbackCloseGameplay.error(description);
			this.callbackCloseGameplay = null;
		}
	}
};