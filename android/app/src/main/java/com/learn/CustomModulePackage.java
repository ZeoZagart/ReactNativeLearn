package com.learn;

import androidx.annotation.NonNull;

import com.facebook.react.ReactPackage;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.uimanager.ViewManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// Native modules need to be registered with React-native.
// this happens in this ReactPackage - which initializes and returns the list of native modules
// On initialization - react-native loops over all the react-packages and initializes all the native-modules
public class CustomModulePackage implements ReactPackage {

    @NonNull
    @Override
    public List<NativeModule> createNativeModules(@NonNull ReactApplicationContext reactApplicationContext) {
        List<NativeModule> modules = new ArrayList<>();

        // see that the native modules are initialized. This code runs on startup and
        // adds to startup delay
        modules.add(new CalendarModule(reactApplicationContext));
        modules.add(new ImagePickerModule(reactApplicationContext));
        modules.add(new ReactNativeIdnowVideoidentModule(reactApplicationContext));
        return modules;
    }

    @NonNull
    @Override
    public List<ViewManager> createViewManagers(@NonNull ReactApplicationContext reactApplicationContext) {
        return Collections.emptyList();
    }
}
