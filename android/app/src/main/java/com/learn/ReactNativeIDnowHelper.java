package com.learn;

import android.app.Activity;

import com.facebook.react.bridge.ReadableMap;

import de.idnow.sdk.IDnowSDK;

public class ReactNativeIDnowHelper {

    public static IDnowSDK initializeWithSettings(Activity activity, ReadableMap settings) throws Exception {
        // initialize SDK
        IDnowSDK instance = IDnowSDK.getInstance();
        instance.initialize(activity, "solarisbankvideoidentcoinbase");
        IDnowSDK.setShowVideoOverviewCheck(true, activity.getApplicationContext());
        IDnowSDK.setShowErrorSuccessScreen(false, activity.getApplicationContext());
        // IDnowSDK.setTransactionToken(settings.getString("transactionToken"));
        IDnowSDK.setTransactionToken("KTS-BRADF");

        return instance;
    }

}
