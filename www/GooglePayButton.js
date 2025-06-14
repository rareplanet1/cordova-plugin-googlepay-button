// GooglePayButton.js - JavaScript interface for the Cordova plugin

var exec = require('cordova/exec');

var GooglePayButton = {
    
    /**
     * Show the Google Pay button
     * When clicked, the successCallback will be called with "clicked"
     * @param {Function} successCallback - Success callback function, receives "clicked" when button is tapped
     * @param {Function} errorCallback - Error callback function
     */
    showPayButton: function(successCallback, errorCallback) {
        exec(successCallback, errorCallback, 'GooglePayButton', 'showPayButton', []);
    },
    
    /**
     * Hide the Google Pay button
     * @param {Function} successCallback - Success callback function
     * @param {Function} errorCallback - Error callback function
     */
    hidePayButton: function(successCallback, errorCallback) {
        exec(successCallback, errorCallback, 'GooglePayButton', 'hidePayButton', []);
    }
};

module.exports = GooglePayButton;