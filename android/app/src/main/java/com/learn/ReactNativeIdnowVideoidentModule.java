package com.learn;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.ActivityEventListener;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.BaseActivityEventListener;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableMap;

import de.idnow.sdk.IDnowSDK;

public class ReactNativeIdnowVideoidentModule extends ReactContextBaseJavaModule {

    private Promise globalPromise;

    private final ActivityEventListener activityEventListener = new BaseActivityEventListener() {
        @Override
        public void onActivityResult(Activity activity, int requestCode, int resultCode, Intent intent) {
            if (resultCode == IDnowSDK.REQUEST_ID_NOW_SDK) {
                resultCallback(resultCode, null, intent);
            }
        }
    };

    private String getErrorKeyFromCode(int code) {
        switch (code) {
            case IDnowSDK.RESULT_CODE_FAILED: {
                return "FAILED";
            }
            case IDnowSDK.RESULT_CODE_CANCEL: {
                return "CANCEL";
            }
            default: {
                return "INTERNAL_ERROR";
            }
        }
    }

    private void resultCallback(int resultCode, Exception e, Intent data) {
        WritableMap params = Arguments.createMap();
        String resultCodeKey = "resultCode";
        String errorMessageKey = "errorMessage";

        if (resultCode == IDnowSDK.RESULT_CODE_SUCCESS) {
            params.putString(resultCodeKey, "SUCCESS");
            globalPromise.resolve(params);
        } else {
            String errorCode = getErrorKeyFromCode(resultCode);
            String errorMessage = "Error from native world";
            if (e != null) {
                errorMessage = e.getMessage();
            } else {
                errorMessage = data.getStringExtra(IDnowSDK.RESULT_DATA_ERROR);
            }
            globalPromise.reject(errorCode, errorMessage);
        }
    }

    ReactNativeIdnowVideoidentModule(ReactApplicationContext context) {
        super(context);
        context.addActivityEventListener(activityEventListener);
    }

    @NonNull
    @Override
    public String getName() {
        return "ReactNativeIDnowVideoIdent";
    }

    @ReactMethod
    public void startVideoIdent(ReadableMap settings, final Promise promise) {
        Log.d("VideoIdentModule: ", "received api call");
        globalPromise = promise;
        Activity currentActivity = getCurrentActivity();
        try {
            IDnowSDK instance = ReactNativeIDnowHelper.initializeWithSettings(currentActivity, settings);
            instance.start(IDnowSDK.getTransactionToken());
        } catch (Exception e) {
            resultCallback(IDnowSDK.RESULT_CODE_INTERNAL, e, null);
        }
    }
}
