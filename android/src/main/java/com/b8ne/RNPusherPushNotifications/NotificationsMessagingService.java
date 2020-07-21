package com.b8ne.RNPusherPushNotifications;

import android.app.Activity;

import android.os.Handler;
import android.os.Bundle;
import android.os.Looper;
import android.content.Intent;

import java.util.Map;

import com.facebook.react.ReactApplication;
import com.facebook.react.ReactInstanceManager;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.WritableNativeMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;

public class NotificationsMessagingService {

    private static String notificationEvent = "notification";
    private static ReactContext context;
    private static ReactInstanceManager reactInstanceManager;

    public static void read(final ReactInstanceManager reactInstanceManager, Activity reactActivity) {
        Intent intent = reactActivity.getIntent();
        final WritableMap map = new WritableNativeMap();

        boolean launchedFromHistory = intent != null ? (intent.getFlags() & Intent.FLAG_ACTIVITY_LAUNCHED_FROM_HISTORY) != 0 : false;

        Bundle extras = intent.getExtras();
        if (!launchedFromHistory & extras != null) {
            WritableMap payload = Arguments.createMap();

            for (String key : extras.keySet()) {
                Object value = extras.get(key);
                payload.putString(key, value.toString());
            }
            map.putMap("data", payload);

            if (payload != null) {
                // We need to run this on the main thread, as the React code assumes that is true.
                // Namely, DevServerHelper constructs a Handler() without a Looper, which triggers:
                // "Can't create handler inside thread that has not called Looper.prepare()"
                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(new Runnable() {
                    public void run() {
                        // Construct and load our normal React JS code data
                        // ReactInstanceManager reactInstanceManager = ((ReactApplication) getApplication()).getReactNativeHost().getReactInstanceManager();
                        ReactContext context = reactInstanceManager.getCurrentReactContext();
                        // If it's constructed, send a notification
                        if (context != null) {
                            context.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                                    .emit(notificationEvent, map);
                        } else {
                            // Otherwise wait for construction, then send the notification
                            reactInstanceManager.addReactInstanceEventListener(new ReactInstanceManager.ReactInstanceEventListener() {
                                public void onReactContextInitialized(ReactContext context) {
                                    context.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                                            .emit(notificationEvent, map);
                                }
                            });
                            if (!reactInstanceManager.hasStartedCreatingInitialContext()) {
                                // Construct it in the background
                                reactInstanceManager.createReactContextInBackground();
                            }
                        }
                    }
                });
            }
        }
    }
}
