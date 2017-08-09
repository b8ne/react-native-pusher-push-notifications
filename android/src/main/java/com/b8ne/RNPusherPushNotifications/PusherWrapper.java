package com.b8ne.RNPusherPushNotifications;

import android.util.Log;

import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactContext;
import com.google.firebase.messaging.RemoteMessage;
import com.pusher.android.PusherAndroid;
import com.pusher.android.notifications.PushNotificationRegistration;
import com.pusher.android.notifications.fcm.FCMPushNotificationReceivedListener;
import com.pusher.android.notifications.interests.InterestSubscriptionChangeListener;
import com.pusher.android.notifications.tokens.PushNotificationRegistrationListener;

/**
 * Created by bensutter on 13/1/17.
 */

public class PusherWrapper {
    private static PushNotificationRegistration nativePusher;

    public PusherWrapper(String appKey) {
        Log.d("PUSHER_WRAPPER", "Creating Pusher with App Key: " + appKey);
        System.out.print("Creating Pusher with App Key: " + appKey);
        if (nativePusher == null) {
            PusherAndroid pusher = new PusherAndroid(appKey);
            nativePusher = pusher.nativePusher();
        }
    }

    public PusherWrapper(String appKey, ReactContext context) {
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
                    RemoteMessage temp = remoteMessage;
                    Log.d("PUSHER_WRAPPER", remoteMessage.toString());
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

    public void subscribe(final String channel, final Callback errorCallback, final Callback successCallback) {
        Log.d("PUSHER_WRAPPER", "Attempting to subscribe to " +  channel);
        System.out.print("Attempting to subscribe to " +  channel);
        nativePusher.subscribe(channel, new InterestSubscriptionChangeListener() {
            @Override
            public void onSubscriptionChangeSucceeded() {
                Log.d("PUSHER_WRAPPER", "Success! " + channel);
                System.out.print("Success! " + channel);
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

    public void unsubscribe(final String channel, final Callback errorCallback, final Callback successCallback) {
        nativePusher.unsubscribe(channel, new InterestSubscriptionChangeListener() {
            @Override
            public void onSubscriptionChangeSucceeded() {
              Log.d("PUSHER_WRAPPER", "Success! " + channel);
              System.out.print("Success! " + channel);
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
