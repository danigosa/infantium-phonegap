infantium-phonegap-android
==================

## Phonegap Plugin for the Infantium Android SDK

# Warning

This only concerns the Android version of the SDK and therefore will only work on Android versions of your Phonegap apps.  
Also, this is the debug version of the Infantium SDK that you should use for development. You will need to switch to the production version later by just replacing the jar file in the libs folder of the Android project.

# How to

First you need to install the plugin:

`cordova plugins add com.wonkystar.phonegap.infantium` to get the latest published version.  

`cordova plugins add https://github.com/WonkyStar/infantium-phonegap.git` if you want the latest version on Github.


This will add all the necessary libs to your Android app (Infantium SDK etc.). It will also add Infantium's required permissions and receiver to your Android Manifest.

Now your will have access to Infantium's SDK in `window.plugins.infantium`.  
You will find the functions' signatures in `www/infantium.js`.

Besides `setup` (which requires your Infantium credentials and allows to set your device resolution if you _really_ want to), `setContentAppUUID` (which requires your Content App UUID) and `createGameplay` (which requires a Subcontent UUID) functions, all other functions require an object or an array of objects if they need arguments. Each function also accepts a single Node-style callback (with `(err, res)` as arguments). The plugin handles Phonegap's callbacks behind the scenes for a cleaner interface.  
All the object field names are the same as the ones provided in Infantium's methods/constructors signature. You can also have a look in the converter methods of `src/android/Infantium.java`.

The plugin also has a `generateUUID` utility function which generates a UUID using Java. This is particularly useful for Infantium's `Element` which require a unique id. However, the functions `addElement` and `addElements` will be able to generate one on the fly for you and return it (or a list of id for `addElements`) if no id has been set and if the call is successful.

Infantium also requires that you call a function when the app is paused and when it's resumed. To hook them into Phonegap, you can use [event listeners](http://docs.phonegap.com/en/3.1.0/cordova_events_events.md.html#pause).

# Example

```js
	// This needs to happen after 'deviceready'
	
	document.addEventListener("resume", function() {
		window.plugins.infantium.onResumeInfantium();
	}, false);

	document.addEventListener("pause", function() {
		window.plugins.infantium.onPauseInfantium();
	}, false);

	window.plugins.infantium.setup("INFANTIUM API USER", "API USER KEY", function() {
	    
	    window.plugins.infantium.setContentAppUUID("CONTENT UUID", function() {
	    
	        window.plugins.infantium.getPlayerUUIDFromApp(function() {

                window.plugins.infantium.createGameplay("SUBCONTENT UUID", function() {

                    window.plugins.infantium.startPlaying(function() {
                        window.plugins.infantium.addElement({
                            color: "0,0,0",
                            alpha: 1.0,
                            type: 'Static_object',
                            visual_type: 'Draw_object',
                            visibility: 1.0,
                            category: 'Polygon',
                            subcategory: 'Freeshape',
                            size: [10, 10],
                            pos: [10, 10],
                            extra_fields: [
                            	{
                            		name: 'something',
                                    type: 'string',
                                    value: 'something'
                               	},
                                {
                                	name: 'thing',
                                    type: 'dict',
                                    value: '',
                                    fields: [
                                    	{
	                                    	name: 'start',
	                                        type: 'int',
	                                        value: 5
	                                   	},
                                        {
                                        	name: 'end',
                                            type: 'int',
                                            value: 15
                                        }
                                   	]
                               	}
                            ]
                        }, function (err, id) {
                            window.plugins.infantium.tapOnObjects({element_id: id, output: 'success'});
                        });

                        window.plugins.infantium.setSuccesses(1)
                        
                        window.plugins.infantium.addDynamicField({
                            name: 'language',
                            type: 'string',
                            value: 'en-GB'
                        });

                        window.plugins.infantium.sendGameRawData(function() {
                            window.plugins.infantium.closeGameplay();
                        });
                    });
                });
	        });
	    });
	});
```

# Troubleshooting

If you're having trouble with the `resume` or `pause` event not firing you can also inject code directly in your main activity by overriding `onResume` and `onPause` like so:

```java

@Override
protected void onPause() {
    super.onPause();
    
    Infantium_SDK infantium = Infantium_SDK.getInfantium_SDK(this);
    infantium.onPauseInfantium();
}

@Override
protected void onResume() {
    super.onResume();
    
    Infantium_SDK infantium = Infantium_SDK.getInfantium_SDK(this);
    infantium.onResumeInfantium();
}
```

You will also have to import the infantium SDK lib to do this by adding this with the other packages: `import com.infantium.android.sdk.Infantium_SDK`

# Changelog

- 0.1.0 - Implements all the methods in the walkthrough
