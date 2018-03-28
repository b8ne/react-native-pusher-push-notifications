package com.b8ne.RNPusherPushNotifications;

import android.util.Log;
import java.util.Map;
import org.json.JSONObject;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableNativeMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.google.firebase.messaging.RemoteMessage;
import com.pusher.android.PusherAndroid;
import com.facebook.react.bridge.ReactApplicationContext;
import com.pusher.android.notifications.PushNotificationRegistration;
import com.pusher.android.notifications.fcm.FCMPushNotificationReceivedListener;
import com.pusher.android.notifications.interests.InterestSubscriptionChangeListener;
import com.pusher.android.notifications.tokens.PushNotificationRegistrationListener;

/**
 * Created by bensutter on 13/1/17.
 */

public class PusherWrapper {
    private static PushNotificationRegistration nativePusher;
    private String notificationEvent = "notification";

    public PusherWrapper(String appKey) {
        Log.d("PUSHER_WRAPPER", "Creating Pusher with App Key: " + appKey);
        System.out.print("Creating Pusher with App Key: " + appKey);
        if (nativePusher == null) {
            PusherAndroid pusher = new PusherAndroid(appKey);
            nativePusher = pusher.nativePusher();
        }
    }

    public PusherWrapper(String appKey, final ReactContext context) {
      Log.d("PUSHER_WRAPPER", "Creating Pusher with App Key: " + appKey);
      System.out.print("Creating Pusher with App Key: " + appKey);
        if (nativePusher == null) {
            PusherAndroid pusher = new PusherAndroid(appKey);
            nativePusher = pusher.nativePusher();
        }

        try {
            nativePusher.registerFCM(context, new PushNotificationRegistrationListener() {
                @Override
                public void onSuccessfulRegistration() {
                    Log.d("PUSHER_WRAPPER", "Successfully registered to FCM");
                    System.out.print("Successfully registered to FCM");
                }

                @Override
                public void onFailedRegistration(int statusCode, String response) {
                    Log.d("PUSHER_WRAPPER", "FCM Registration failed with code " + statusCode + " " + response);
                    System.out.print("FCM Registration failed with code " + statusCode + " " + response);
                }
            });
            nativePusher.setFCMListener(new FCMPushNotificationReceivedListener() {
                @Override
                public void onMessageReceived(RemoteMessage remoteMessage) {
                    WritableMap map = Arguments.createMap();
                    Map<String, String> messageMap = remoteMessage.getData();
                    for(Map.Entry<String, String> entry : messageMap.entrySet()) {
                      map.putString(entry.getKey(), entry.getValue());
                    }
                    context.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class).emit(notificationEvent, map);

                    System.out.print(remoteMessage.toString());
                }
            });

        } catch (Exception ex) {
            Log.d("PUSHER_WRAPPER", "Exception in PusherWrapper " + ex.getMessage());
            System.out.print("Exception in PusherWrapper " + ex.getMessage());
        }
    }

    public PushNotificationRegistration getNativePusher() {
        return this.nativePusher;
    }

    public void subscribe(final String interest, final Callback errorCallback, final Callback successCallback) {
        Log.d("PUSHER_WRAPPER", "Attempting to subscribe to " +  interest);
        System.out.print("Attempting to subscribe to " +  interest);
        nativePusher.subscribe(interest, new InterestSubscriptionChangeListener() {
            @Override
            public void onSubscriptionChangeSucceeded() {
                Log.d("PUSHER_WRAPPER", "Success! " + interest);
                System.out.print("Success! " + interest);
                successCallback.invoke();
            }

            @Override
            public void onSubscriptionChangeFailed(int statusCode, String response) {
                Log.d("PUSHER_WRAPPER", ":(: received " + statusCode + " with" + response);
                System.out.print(":(: received " + statusCode + " with" + response);
                errorCallback.invoke(statusCode, response);
            }
        });
    }

    public void unsubscribe(final String interest, final Callback errorCallback, final Callback successCallback) {
        nativePusher.unsubscribe(interest, new InterestSubscriptionChangeListener() {
            @Override
            public void onSubscriptionChangeSucceeded() {
              Log.d("PUSHER_WRAPPER", "Success! " + interest);
              System.out.print("Success! " + interest);
              successCallback.invoke();
            }

            @Override
            public void onSubscriptionChangeFailed(int statusCode, String response) {
                Log.d("PUSHER_WRAPPER", ":(: received " + statusCode + " with" + response);
                System.out.print(":(: received " + statusCode + " with" + response);
                errorCallback.invoke(statusCode, response);
            }
        });
    }
}
