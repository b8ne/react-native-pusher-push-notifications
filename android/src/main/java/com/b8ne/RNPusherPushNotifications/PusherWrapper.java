package com.b8ne.RNPusherPushNotifications;

import android.util.Log;

import android.app.Activity;

import java.util.Map;
import java.util.Set;

import org.json.JSONObject;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableNativeMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.google.firebase.messaging.RemoteMessage;
import com.facebook.react.bridge.ReactApplicationContext;

import com.google.firebase.messaging.RemoteMessage;
import com.pusher.pushnotifications.PushNotifications;
import com.pusher.pushnotifications.PushNotificationsInstance;
import com.pusher.pushnotifications.SubscriptionsChangedListener;
import com.pusher.pushnotifications.PushNotificationReceivedListener;

//
// TODO: verify the android manifest after https://docs.pusher.com/beams/reference/android
/**
 * Created by bensutter on 13/1/17.
 * https://docs.pusher.com/beams/getting-started/android/init-beams
 */

public class PusherWrapper {
    //private static PushNotificationRegistration nativePusher;
    private String registeredEvent = "registered";
    private String notificationEvent = "notification";
    //private final PushNotificationsInstance pushNotifications;
    private ReactContext context;
//    public PusherWrapper(String appKey) {
//        Log.d("PUSHER_WRAPPER", "Creating Pusher with App Key: " + appKey);
//        System.out.print("Creating Pusher with App Key: " + appKey);
////        if (nativePusher == null) {
////            PusherAndroid pusher = new PusherAndroid(appKey);
////            nativePusher = pusher.nativePusher();
////        }
//    }


    public PusherWrapper(String appKey, final ReactContext context) {
        Log.d("PUSHER_WRAPPER", "Creating Pusher with App Key: " + appKey);
        System.out.print("Creating Pusher with App Key: " + appKey);

        // the new-ed version of PushNotificationsInstance doesn't seem to have
        // an instance of setOnMessageReceivedListenerForVisibleActivity, so
        // use the singleton version.
        //this.pushNotifications = new PushNotificationsInstance(context, appKey);
        PushNotifications.start(context, appKey);
        this.context = context;
        //if (nativePusher == null) {
            //PusherAndroid pusher = new PusherAndroid(appKey);
            //nativePusher = pusher.nativePusher();
        //}

        try {
            //PushNotifications.subscribe("hello");

//            nativePusher.registerFCM(context, new PushNotificationRegistrationListener() {
//                @Override
//                public void onSuccessfulRegistration() {
//                    Log.d("PUSHER_WRAPPER", "Successfully registered to FCM");
//                    System.out.print("Successfully registered to FCM");
//                    context.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class).emit(registeredEvent, null);
//                }
//
//                @Override
//                public void onFailedRegistration(int statusCode, String response) {
//                    Log.d("PUSHER_WRAPPER", "FCM Registration failed with code " + statusCode + " " + response);
//                    System.out.print("FCM Registration failed with code " + statusCode + " " + response);
//                }
//            });
//            nativePusher.setFCMListener(new FCMPushNotificationReceivedListener() {
//                @Override
//                public void onMessageReceived(RemoteMessage remoteMessage) {
//                    WritableMap map = Arguments.createMap();
//                    RemoteMessage.Notification notification = remoteMessage.getNotification();
//
//                    if(notification != null) {
//                        map.putString("body", notification.getBody());
//                        map.putString("title", notification.getTitle());
//                        map.putString("tag", notification.getTag());
//                        map.putString("click_action", notification.getClickAction());
//                        map.putString("icon", notification.getIcon());
//
//                        context.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class).emit(notificationEvent, map);
//                        System.out.print(remoteMessage.toString());
//                    }
//                    else {
//                        Log.d("PUSHER_WRAPPER", "No notification received");
//                    }
//                }
//            });

        } catch (Exception ex) {
            Log.d("PUSHER_WRAPPER", "Exception in PusherWrapper " + ex.getMessage());
            System.out.print("Exception in PusherWrapper " + ex.getMessage());
        }
    }

    public void onResume(Activity activity) {
        // SEE: https://docs.pusher.com/beams/reference/android
        // TODO: Uncomment
        PushNotifications.setOnMessageReceivedListenerForVisibleActivity(activity, new PushNotificationReceivedListener() {
            @Override
            public void onMessageReceived(RemoteMessage remoteMessage) {
                String messagePayload = remoteMessage.getData().get("inAppNotificationMessage");
                if (messagePayload == null) {
                    // Message payload was not set for this notification
                    Log.i("MainActivity", "Payload was missing");
                } else {
                    Log.i("MainActivity", messagePayload);
                    // Now update the UI based on your message payload!
                    // TODO: create a WriteableMap as above?
                    context.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class).emit(notificationEvent, messagePayload);
                }
            }
        });

    }

//    public PushNotificationRegistration getNativePusher() {
//        return this.nativePusher;
//    }

    public void subscribe(final String interest) {
        Log.d("PUSHER_WRAPPER", "Attempting to subscribe to " +  interest);
        System.out.print("Attempting to subscribe to " +  interest);
        try {
            //this.pushNotifications.subscribe(interest);
            PushNotifications.subscribe(interest);
            Log.d("PUSHER_WRAPPER", "Success! " + interest);
            System.out.print("Success! " + interest);
            //successCallback.invoke();
        } catch (Exception ex) {
            Log.d("PUSHER_WRAPPER", "Exception in PusherWrapper " + ex.getMessage());
            System.out.print("Exception in PusherWrapper.subscribe " + ex.getMessage());
            // probably these should be removed
            //errorCallback.invoke(0, ex.message);
        }
//        nativePusher.subscribe(interest, new InterestSubscriptionChangeListener() {
//            @Override
//            public void onSubscriptionChangeSucceeded() {
//                Log.d("PUSHER_WRAPPER", "Success! " + interest);
//                System.out.print("Success! " + interest);
//                successCallback.invoke();
//            }
//
//            @Override
//            public void onSubscriptionChangeFailed(int statusCode, String response) {
//                Log.d("PUSHER_WRAPPER", ":(: received " + statusCode + " with" + response);
//                System.out.print(":(: received " + statusCode + " with" + response);
//                errorCallback.invoke(statusCode, response);
//            }
//        });
    }

    public void unsubscribe(final String interest) {
        //this.pushNotifications.unsubscribe(interest);
        PushNotifications.unsubscribe(interest);

//        nativePusher.unsubscribe(interest, new InterestSubscriptionChangeListener() {
//            @Override
//            public void onSubscriptionChangeSucceeded() {
//              Log.d("PUSHER_WRAPPER", "Success! " + interest);
//              System.out.print("Success! " + interest);
//              successCallback.invoke();
//            }
//
//            @Override
//            public void onSubscriptionChangeFailed(int statusCode, String response) {
//                Log.d("PUSHER_WRAPPER", ":(: received " + statusCode + " with" + response);
//                System.out.print(":(: received " + statusCode + " with" + response);
//                errorCallback.invoke(statusCode, response);
//            }
//        });
    }

    public void unsubscribeAll() {
        PushNotifications.unsubscribeAll();
        //this.pushNotifications.unsubscribeAll();
    }

    public void getSubscriptions( final Callback subscriptionCallback) {
        Set<String> subscriptions = PushNotifications.getSubscriptions();
        //Set<String> subscriptions = this.pushNotifications.getSubscriptions();
        subscriptionCallback.invoke(subscriptions);
    }

    public void setOnSubscriptionsChangedListener(final Callback subscriptionChangedListener) {

        PushNotifications.setOnSubscriptionsChangedListener(new SubscriptionsChangedListener() {
            @Override
            public void onSubscriptionsChanged(Set<String> interests) {
                // call
                subscriptionChangedListener.invoke(interests);
            }
        });
    }

    public void setOnMessageReceivedListenerForVisibleActivity(final Activity activity, final Callback pushNotificationReceivedListener) {
        // TODO: Uncomment
        // This only seems to exist on the singleton.
        // https://github.com/pusher/push-notifications-android/issues/58
        PushNotifications.setOnMessageReceivedListenerForVisibleActivity(activity, new PushNotificationReceivedListener() {
            @Override
            public void onMessageReceived(RemoteMessage remoteMessage) {
                pushNotificationReceivedListener.invoke(remoteMessage);
            }
        });
    }



}
