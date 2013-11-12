var exec = require('cordova/exec');

// This phonegap plugins accepts node-style callbacks for a cleaner interface
// To respect Phonegap's win/lose callbacks, they're defined inside each function
// and they call the callback provided with the correct args depending on if
// it's a success or a failure.

module.exports = {
	
	generateUUID: function (callback) {

		callback = callback || function() {};
		var win = function (res) { callback(null, res) };
		var lose = function (msg) { callback(true, null) };

		exec(win, lose, "Infantium", "generateUUID", []);
	},

	onResumeInfantium: function (callback) {

		callback = callback || function() {};
		var win = function (res) { callback(null, res) };
		var lose = function (msg) { callback(true, null) };

		exec(win, lose, "Infantium", "onResumeInfantium", []);
	},

	onPauseInfantium: function (callback) {

		callback = callback || function() {};
		var win = function (res) { callback(null, res) };
		var lose = function (msg) { callback(true, null) };

		exec(win, lose, "Infantium", "onPauseInfantium", []);
	},

	setup: function (api_user, api_key, callback, w_dev, h_dev) {

		w_dev = w_dev || window.innerWidth;
		h_dev = h_dev || window.innerHeight;
		callback = callback || function() {};

		var win = function () { callback(false, true) };
		var lose = function (msg) { callback(msg || "Failed", false) };

		exec(win, lose, "Infantium", "setup", [api_user, api_key, w_dev, h_dev]);
	},

	setContentAppUUID: function (contentapp_uuid, callback) {

		callback = callback || function() {};
		var win = function () { callback(false, true) };
		var lose = function (msg) { callback(msg || "Failed", false) };

		exec(win, lose, "Infantium", "setContentAppUUID", [contentapp_uuid]);
	},
	
	getPlayerUUIDFromApp: function (callback) {

		callback = callback || function() {};
		var win = function () { callback(false, true) };
		var lose = function (msg) { callback(msg || "Failed", false) };

		exec(win, lose, "Infantium", "getPlayerUUIDFromApp", []);
	},
	
	createGameplay: function (subcontent_uuid, callback) {

		callback = callback || function() {};
		var win = function () { callback(false, true) };
		var lose = function (msg) { callback(msg || "Failed", false) };

		exec(win, lose, "Infantium", "createGameplay", [subcontent_uuid]);
	},
	
	startPlaying: function (callback) {

		callback = callback || function() {};
		var win = function () { callback(false, true) };
		var lose = function (msg) { callback(msg || "Failed", false) };

		exec(win, lose, "Infantium", "startPlaying", []);
	},
	
	addElement: function (element, callback) {

		callback = callback || function() {};

		if (!element.id) {

			module.exports.generateUUID(function (err, res) {

				if (!err) {

					element.id = res;

					var win = function () { callback(false, res) };
					var lose = function (msg) { callback(msg || "Failed", false) };

					exec(win, lose, "Infantium", "addElement", [element]);
				}
				else {

					callback(true, false);
				}
			});
		}
		else {

			var win = function () { callback(false, element.id) };
			var lose = function (msg) { callback(msg || "Failed", false) };

			exec(win, lose, "Infantium", "addElement", [element]);
		}
	},
	
	addElements: function (elements, callback) {
		
		callback = callback || function() {};

		var ids = [];
		var calls = elements.length;

		var ready = function () {

			if (--calls === 0) {
				
				var win = function () { callback(false, ids) };
				var lose = function (msg) { callback(msg || "Failed", false) };

				exec(win, lose, "Infantium", "addElements", [elements]);
			}
 		}

		elements.forEach(function (element, i) {

			if (!element.id) {

				module.exports.generateUUID(function (err, res) {

					if (!err) {
						element.id = res;
						ids[i] = res;
						ready();
					}
					else {
						callback(true, false);
					}
				});
			}
			else {
				ids[i] = element.id;
				ready();
			}
		});
	},
	
	tapNoObjects: function (object, callback) {

		callback = callback || function() {};
		var win = function () { callback(false, true) };
		var lose = function (msg) { callback(msg || "Failed", false) };

		exec(win, lose, "Infantium", "tapNoObjects", [object]);
	},
	
	tapOnObjects: function (object, callback) {

		callback = callback || function() {};
		var win = function () { callback(false, true) };
		var lose = function (msg) { callback(msg || "Failed", false) };

		exec(win, lose, "Infantium", "tapOnObjects", [object]);
	},
	
	setSuccesses: function (successes, callback) {

		callback = callback || function() {};
		var win = function () { callback(false, true) };
		var lose = function (msg) { callback(msg || "Failed", false) };

		exec(win, lose, "Infantium", "setSuccesses", [successes]);
	},
	
	setFailures: function (failures, callback) {

		callback = callback || function() {};
		var win = function () { callback(false, true) };
		var lose = function (msg) { callback(msg || "Failed", false) };

		exec(win, lose, "Infantium", "setFailures", [failures]);
	},
	
	setTarget: function (target, callback) {

		callback = callback || function() {};
		var win = function () { callback(false, true) };
		var lose = function (msg) { callback(msg || "Failed", false) };

		exec(win, lose, "Infantium", "setTarget", [target]);
	},
	
	setTargets: function (targets, callback) {

		callback = callback || function() {};
		var win = function () { callback(false, true) };
		var lose = function (msg) { callback(msg || "Failed", false) };

		exec(win, lose, "Infantium", "setTargets", [targets]);
	},
	
	setEvaluate: function (eval, callback) {

		callback = callback || function() {};
		var win = function () { callback(false, true) };
		var lose = function (msg) { callback(msg || "Failed", false) };

		exec(win, lose, "Infantium", "setEvaluate", [eval]);
	},
	
	addSound: function (sound, callback) {

		callback = callback || function() {};
		var win = function () { callback(false, true) };
		var lose = function (msg) { callback(msg || "Failed", false) };

		exec(win, lose, "Infantium", "addSound", [sound]);
	},
	
	addSounds: function (sounds, callback) {

		callback = callback || function() {};
		var win = function () { callback(false, true) };
		var lose = function (msg) { callback(msg || "Failed", false) };

		exec(win, lose, "Infantium", "addSounds", [sounds]);
	},
	
	addFixedAnimation: function (fixedAnimation, callback) {

		callback = callback || function() {};
		var win = function () { callback(false, true) };
		var lose = function (msg) { callback(msg || "Failed", false) };

		exec(win, lose, "Infantium", "addFixedAnimation", [fixedAnimation]);
	},
	
	addFixedAnimations: function (fixedAnimations, callback) {

		callback = callback || function() {};
		var win = function () { callback(false, true) };
		var lose = function (msg) { callback(msg || "Failed", false) };

		exec(win, lose, "Infantium", "addFixedAnimations", [fixedAnimations]);
	},
	
	addDynamicField: function (field, callback) {

		callback = callback || function() {};
		var win = function () { callback(false, true) };
		var lose = function (msg) { callback(msg || "Failed", false) };

		exec(win, lose, "Infantium", "addDynamicField", [field]);
	},
	
	addDynamicFields: function (fields, callback) {

		callback = callback || function() {};
		var win = function () { callback(false, true) };
		var lose = function (msg) { callback(msg || "Failed", false) };

		exec(win, lose, "Infantium", "addDynamicFields", [fields]);
	},
	
	startAnimation: function (animation, callback) {

		callback = callback || function() {};
		var win = function () { callback(false, true) };
		var lose = function (msg) { callback(msg || "Failed", false) };

		exec(win, lose, "Infantium", "startAnimation", [animation]);
	},
	
	endAnimation: function (animation, callback) {

		callback = callback || function() {};
		var win = function () { callback(false, true) };
		var lose = function (msg) { callback(msg || "Failed", false) };

		exec(win, lose, "Infantium", "startAnimations", [animation]);
	},
	
	startDragging: function (drag) {

		callback = callback || function() {};
		var win = function () { callback(false, true) };
		var lose = function (msg) { callback(msg || "Failed", false) };

		exec(win, lose, "Infantium", "startDragging", [drag]);
	},
	
	finishDragging: function (drag, callback) {

		callback = callback || function() {};
		var win = function () { callback(false, true) };
		var lose = function (msg) { callback(msg || "Failed", false) };

		exec(win, lose, "Infantium", "finishDragging", [drag]);
	},

	sendGameRawData: function (callback) {

		callback = callback || function() {};
		var win = function () { callback(false, true) };
		var lose = function (msg) { callback(msg || "Failed", false) };

		exec(win, lose, "Infantium", "sendGameRawData", []);
	},

	closeGameplay: function (callback) {

		callback = callback || function() {};
		var win = function () { callback(false, true) };
		var lose = function (msg) { callback(msg || "Failed", false) };

		exec(win, lose, "Infantium", "closeGameplay", []);
	},

	returnToInfantiumApp: function (callback) {

		callback = callback || function() {};
		var win = function () { callback(false, true) };
		var lose = function (msg) { callback(msg || "Failed", false) };

		exec(win, lose, "Infantium", "returnToInfantiumApp", []);
	}
};
