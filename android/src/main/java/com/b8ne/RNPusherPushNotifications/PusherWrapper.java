package com.b8ne.RNPusherPushNotifications;

import android.util.Log;

import com.google.firebase.messaging.RemoteMessage;
import com.pusher.android.PusherAndroid;
import com.pusher.android.notifications.PushNotificationRegistration;
import com.pusher.android.notifications.fcm.FCMPushNotificationReceivedListener;
import com.pusher.android.notifications.interests.InterestSubscriptionChangeListener;

/**
 * Created by bensutter on 13/1/17.
 */

public class PusherWrapper {
    private static PushNotificationRegistration nativePusher;

    public PusherWrapper(String appKey) {
        if (nativePusher == null) {
            PusherAndroid pusher = new PusherAndroid(appKey);
            nativePusher = pusher.nativePusher();
        }
    }

    public void onCreate() {
        nativePusher.setFCMListener(new FCMPushNotificationReceivedListener() {
            @Override
            public void onMessageReceived(RemoteMessage remoteMessage) {
                RemoteMessage temp = remoteMessage;
                Log.d("PUSHER_WRAPPER", remoteMessage.toString());
                System.out.print(remoteMessage.toString());
            }
        });
    }

    public PushNotificationRegistration getNativePusher() {
        return this.nativePusher;
    }

    public void subscribe(final String channel) {
        nativePusher.subscribe(channel, new InterestSubscriptionChangeListener() {
            @Override
            public void onSubscriptionChangeSucceeded() {
                Log.d("PUSHER_WRAPPER", "Success! " + channel);
                System.out.print("Success! " + channel);
            }

            @Override
            public void onSubscriptionChangeFailed(int statusCode, String response) {
                Log.d("PUSHER_WRAPPER", ":(: received " + statusCode + " with" + response);
                System.out.print(":(: received " + statusCode + " with" + response);
            }
        });
    }

    public void unsubscribe(final String channel) {
        nativePusher.unsubscribe(channel, new InterestSubscriptionChangeListener() {
            @Override
            public void onSubscriptionChangeSucceeded() {
              Log.d("PUSHER_WRAPPER", "Success! " + channel);
              System.out.print("Success! " + channel);
            }

            @Override
            public void onSubscriptionChangeFailed(int statusCode, String response) {
                Log.d("PUSHER_WRAPPER", ":(: received " + statusCode + " with" + response);
                System.out.print(":(: received " + statusCode + " with" + response);
            }
        });
    }
}
