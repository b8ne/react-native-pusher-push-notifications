package com.b8ne.RNPusherPushNotifications;

import android.util.Log;

import android.app.Activity;

import java.util.Set;

import com.facebook.react.bridge.*;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.google.firebase.messaging.RemoteMessage;

import com.pusher.pushnotifications.PushNotifications;
import com.pusher.pushnotifications.SubscriptionsChangedListener;
import com.pusher.pushnotifications.PushNotificationReceivedListener;

//
// TODO: verify the android manifest after https://docs.pusher.com/beams/reference/android
/**
 * Created by bensutter on 13/1/17.
 * https://docs.pusher.com/beams/getting-started/android/init-beams
 */

public class PusherWrapper {

    private String registeredEvent = "registered";
    private String notificationEvent = "notification";
    private ReactContext context;


    public PusherWrapper(String appKey, final ReactContext context) {
        Log.d("PUSHER_WRAPPER", "Creating Pusher with App Key: " + appKey);
        System.out.print("Creating Pusher with App Key: " + appKey);

        try {
            // the new-ed version of PushNotificationsInstance doesn't seem to have
            // an instance of setOnMessageReceivedListenerForVisibleActivity, so
            // use the singleton version.
            // this.pushNotifications = new PushNotificationsInstance(context, appKey);
            // Does .start actually throw an exception?
            PushNotifications.start(context, appKey);
            context.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class).emit(registeredEvent, null);
            this.context = context;
        } catch (Exception ex) {
            Log.d("PUSHER_WRAPPER", "Exception in PusherWrapper " + ex.getMessage());
            System.out.print("Exception in PusherWrapper " + ex.getMessage());
        }
    }

    public void onResume(final Activity activity) {
        // SEE: https://docs.pusher.com/beams/reference/android
        Log.i("PUSHER_WRAPPER", "onResume subscribing with activity " + getActivityName(activity));
        System.out.print("onResume subscribing with activity " + getActivityName(activity));

        PushNotifications.setOnMessageReceivedListenerForVisibleActivity(activity, new PushNotificationReceivedListener() {
            @Override
            public void onMessageReceived(RemoteMessage remoteMessage) {
                // Arguments.createMap seems to be for testing
                // see: https://github.com/facebook/react-native/blob/master/ReactAndroid/src/main/java/com/facebook/react/bridge/WritableNativeMap.java#L16
                //WritableMap map = Arguments.createMap();
                final WritableMap map = new WritableNativeMap();
                RemoteMessage.Notification notification = remoteMessage.getNotification();

                if(notification != null) {
                    map.putString("body", notification.getBody());
                    map.putString("title", notification.getTitle());
                    map.putString("tag", notification.getTag());
                    map.putString("click_action", notification.getClickAction());
                    map.putString("icon", notification.getIcon());
                    map.putString("color", notification.getColor());
                    //map.putString("link", notification.getLink());

                    context.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class).emit(notificationEvent, map);
                    //System.out.print(remoteMessage.toString());
                    Log.d("PUSHER_WRAPPER", "Notification received: " + notification.toString());
                }
                else {
                    Log.d("PUSHER_WRAPPER", "No notification received");
                }
            }
        });

    }

    public void onDestroy(Activity activity) {
        Log.i("PUSHER_WRAPPER", "onDestroy: " +getActivityName(activity));
    }

    public void onPause(Activity activity) {
        Log.i("PUSHER_WRAPPER", "onPause: " +getActivityName(activity));
    }

    private String getActivityName(Activity activity) {
        return activity.getClass().getSimpleName();
    }

    public void subscribe(final String interest) {
        Log.d("PUSHER_WRAPPER", "Attempting to subscribe to " +  interest);
        System.out.print("Attempting to subscribe to " +  interest);
        try {
            //this.pushNotifications.subscribe(interest);
            PushNotifications.subscribe(interest);
            Log.d("PUSHER_WRAPPER", "Success! " + interest);
            System.out.print("Success! " + interest);
            //successCallback.invoke(); // TODO: Should these be re-enabled?
        } catch (Exception ex) {
            Log.d("PUSHER_WRAPPER", "Exception in PusherWrapper " + ex.getMessage());
            System.out.print("Exception in PusherWrapper.subscribe " + ex.getMessage());
            //errorCallback.invoke(0, ex.message); // TODO: Should these be re-enabled?
        }
    }

    public void unsubscribe(final String interest) {
        PushNotifications.unsubscribe(interest);
        // TODO: maybe re-enable callbacks?  Are they necessary, or do the API calls even fail?
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



}
