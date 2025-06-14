package com.plugin.googlepaybutton;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaWebView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.util.Log;

import com.google.android.gms.wallet.button.ButtonConstants;
import com.google.android.gms.wallet.button.ButtonOptions;
import com.google.android.gms.wallet.button.PayButton;

public class GooglePayButton extends CordovaPlugin {
    
    private static final String TAG = "GooglePayButton";
    private PayButton payButton;
    private CallbackContext callbackContext;
    private CordovaWebView webView;
    
    @Override
    public void initialize(CordovaInterface cordova, CordovaWebView webView) {
        super.initialize(cordova, webView);
        this.webView = webView;
    }
    
    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        this.callbackContext = callbackContext;
        
        if (action.equals("showPayButton")) {
            this.showPayButton();
            return true;
        } else if (action.equals("hidePayButton")) {
            this.hidePayButton();
            return true;
        }
        return false;
    }
    
    private void showPayButton() {
        cordova.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    // Get the WebView's parent container
                    View webViewView = webView.getView();
                    ViewGroup parent = (ViewGroup) webViewView.getParent();
                    
                    // Create PayButton if not exists
                    if (payButton == null) {
                        Context context = cordova.getActivity().getApplicationContext();
                        payButton = new PayButton(context);
                        
                        // Configure button using the official API
                        ButtonOptions buttonOptions = ButtonOptions.newBuilder()
                            .setButtonTheme(ButtonConstants.ButtonTheme.DARK)
                            .setButtonType(ButtonConstants.ButtonType.CHECKOUT)
                            .setCornerRadius(4)
                            .build();
                        
                        payButton.initialize(buttonOptions);
                        
                        // Set click listener
                        payButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Log.d(TAG, "PayButton clicked");
                                if (callbackContext != null) {
                                    callbackContext.success("clicked");
                                }
                            }
                        });
                    }
                    
                    // Remove from any previous parent
                    if (payButton.getParent() != null) {
                        ((ViewGroup) payButton.getParent()).removeView(payButton);
                    }
                    
                    // Add to bottom of screen
                    FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                        FrameLayout.LayoutParams.MATCH_PARENT,
                        FrameLayout.LayoutParams.WRAP_CONTENT
                    );
                    params.gravity = android.view.Gravity.BOTTOM;
                    params.setMargins(40, 20, 40, 100); // left, top, right, bottom margins
                    
                    parent.addView(payButton, params);
                    payButton.setVisibility(View.VISIBLE);
                    
                    Log.d(TAG, "PayButton shown successfully");
                    
                } catch (Exception e) {
                    Log.e(TAG, "Error showing PayButton: " + e.getMessage(), e);
                    if (callbackContext != null) {
                        callbackContext.error("Error showing PayButton: " + e.getMessage());
                    }
                }
            }
        });
    }
    
    private void hidePayButton() {
        cordova.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    if (payButton != null) {
                        payButton.setVisibility(View.GONE);
                        if (payButton.getParent() != null) {
                            ((ViewGroup) payButton.getParent()).removeView(payButton);
                        }
                        Log.d(TAG, "PayButton hidden successfully");
                    }
                } catch (Exception e) {
                    Log.e(TAG, "Error hiding PayButton: " + e.getMessage(), e);
                }
            }
        });
    }
}