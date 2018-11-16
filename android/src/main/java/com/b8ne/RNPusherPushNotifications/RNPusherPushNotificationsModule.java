
package com.b8ne.RNPusherPushNotifications;

import android.app.Activity;
import android.os.AsyncTask;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;


// SEE: https://docs.pusher.com/beams/reference/android

public class RNPusherPushNotificationsModule extends ReactContextBaseJavaModule {

  private final ReactApplicationContext reactContext;
  private PusherWrapper pusher;

  public RNPusherPushNotificationsModule(ReactApplicationContext reactContext) {
    super(reactContext);
    this.reactContext = reactContext;
  }

  @Override
  public String getName() {
    return "RNPusherPushNotifications";
  }

  @ReactMethod
  public void setAppKey(String appKey) {
    this.pusher = new PusherWrapper(appKey, this.reactContext);
  }

//    @ReactMethod
//    public void subscribe(final String channel, final Callback errorCallback, final Callback successCallback) {
//        AsyncTask.execute(new Runnable() {
//            @Override
//            public void run() {
//                pusher.subscribe(channel, errorCallback, successCallback);
//            }
//        });
//    }
//
//    @ReactMethod
//    public void unsubscribe(final String channel, final Callback errorCallback, final Callback successCallback) {
//        AsyncTask.execute(new Runnable() {
//            @Override
//            public void run() {
//                pusher.unsubscribe(channel, errorCallback, successCallback);
//            }
//        });
//    }

    @ReactMethod
    public void subscribe(final String interest) {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                pusher.subscribe(interest);
            }
        });
    }

    @ReactMethod
    public void unsubscribe(final String interest) {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                pusher.unsubscribe(interest);
            }
        });
    }

    @ReactMethod
    public void unsubscribeAll() {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                pusher.unsubscribeAll();
            }
        });
    }

    @ReactMethod
    public void getSubscriptions( final Callback subscriptionCallback) {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                pusher.getSubscriptions(subscriptionCallback);
            }
        });
    }

    @ReactMethod
    public void setOnSubscriptionsChangedListener(final Callback subscriptionChangedListener) {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                pusher.setOnSubscriptionsChangedListener(subscriptionChangedListener);
            }
        });
    }

    @ReactMethod
    public void setOnMessageReceivedListenerForVisibleActivity(final Callback pushNotificationReceivedListener) {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                // TODO: Figure out how to get the activity.
                pusher.setOnMessageReceivedListenerForVisibleActivity(null, pushNotificationReceivedListener);
            }
        });
    }

}
