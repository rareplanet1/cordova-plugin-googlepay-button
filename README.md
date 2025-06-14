# Cordova Google PayButton API integration

Cordova plugin for implementing the Google [PayButton API](https://developers.google.com/pay/api/android/guides/resources/pay-button-api), which is now required when using a Google Pay branded payment button.

## Installation

```
cordova plugin add cordova-plugin-googlepay-button
```

or for Cordova build service, e.g. [VoltBuilder](https://volt.build/), add the following to config.xml:

```
<plugin name="cordova-plugin-googlepay-button" source="npm" spec="0.0.10" />
```

## Usage

This plugin is in active development and has not yet been tested or used in a submission to Google. The version will be updated to 1.0.0 when it has been and is ready for public production use.

```
cordova.plugins.GooglePayButton.showPayButton(
    (result) => {
        console.log('GooglePay button result:', result);
        // The Java code sends "clicked" when the button is actually clicked
        if (result === 'clicked') {
            // Display pay sheet (see plugin cordova-plugin-apple-pay-google-pay-with-environment)
            // cordova.plugins.ApplePayGooglePay.makePaymentRequest(...)
        } else {
            console.log('GooglePay button shown successfully');
        }
    },
    (error) => {
        console.error('Error showing Google Pay button:', error);
    }
);
```

```
cordova.plugins.GooglePayButton.hidePayButton(
    (result) => {
        console.log('GooglePay button hidden:', result);
    },
    (error) => {
        console.error('Error hiding Google Pay button:', error);
    }
);
```