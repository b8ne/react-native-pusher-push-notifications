# react-native-pusher-push-notifications
Manage pusher interest subscriptions from within React Native JS

IMPORTANT!!! This module is intended to complement the default [Pusher setup](https://pusher.com/docs/push_notifications).  This module simply allows that implementation to be accessed directly from React Native JS.

[![npm version](https://badge.fury.io/js/react-native-pusher-push-notifications.svg)](https://badge.fury.io/js/react-native-pusher-push-notifications)

## Getting started

`$ npm install react-native-pusher-push-notifications --save`

### Mostly automatic installation

`$ react-native link react-native-pusher-push-notifications`

### Manual installation

#### iOS

1. In XCode, in the project navigator, right click `Libraries` ➜ `Add Files to [your project's name]`
2. Go to `node_modules` ➜ `react-native-pusher-push-notifications` and add `RNPusherPushNotifications.xcodeproj`
3. In XCode, in the project navigator, select your project. Add `libRNPusherPushNotifications.a` to your project's `Build Phases` ➜ `Link Binary With Libraries`
4. Run your project (`Cmd+R`)<

#### Android

1. Open up `android/app/src/main/java/[...]/MainActivity.java`
  - Add `import com.reactlibrary.RNPusherPushNotificationsPackage;` to the imports at the top of the file
  - Add `new RNPusherPushNotificationsPackage()` to the list returned by the `getPackages()` method
2. Append the following lines to `android/settings.gradle`:
  	```
  	include ':react-native-pusher-push-notifications'
  	project(':react-native-pusher-push-notifications').projectDir = new File(rootProject.projectDir, 	'../node_modules/react-native-react-native-pusher-push-notifications/android')
  	```
3. Insert the following lines inside the dependencies block in `android/app/build.gradle`:
  	```
      compile project(':react-native-pusher-push-notifications')
  	```

### All installations

#### iOS

** DO NOT follow the pusher.com push notification docs that detail modifying the AppDelegate.h/m files! - this package takes care of most of the steps for you**

1. After package installation open `AppDelegate.h` and add:
```aidl
    #import "RNPusherPushNotifications.h"
    // ..after @implements and before @end
    @property (nonatomic, strong) RNPusherPushNotifications *RNPusher;
```
2. Open `AppDelegate.m` and add:
```aidl
    // Inside didFinishLaunchingWithOptions, near the bottom (after rootView has been initialised)
    self.RNPusher = [rootView.bridge moduleForName:@"RNPusherPushNotifications"];

    // Add the following as a new methods to AppDelegate.m
    - (void)application:(UIApplication *)application didRegisterForRemoteNotificationsWithDeviceToken:(NSData *)deviceToken {
      [[((RCTRootView *) self.window.rootViewController.view).bridge moduleForName:@"RNPusherPushNotifications"] setDeviceToken:deviceToken];
    }

    - (void)application:(UIApplication *)application didReceiveRemoteNotification:(NSDictionary *)notification {
      [[((RCTRootView *) self.window.rootViewController.view).bridge moduleForName:@"RNPusherPushNotifications"] handleNotification:notification];
    }
```

## Usage
```javascript
// Import module
import RNPusherPushNotifications from 'react-native-pusher-push-notifications';

// Get your interest
const interest = "donuts";

// Set your app key and register for push
RNPusherPushNotifications.setAppKey(ENV.PUSHER_APP_KEY);

if (Platform.OS === 'ios') {
  // iOS must wait for rego
  RNPusherPushNotifications.on('registered', initInterests)
  RNPusherPushNotifications.on('notification', handleNotification)
} else {
  // Android is immediate
  initInterests()
}

function handleNotification(notification) {
  console.log('# ', notification)
}

function initInterests() {
    // Subscribe to push notifications
    if (Platform.OS === 'ios') {
        // iOS callbacks are beta, so dont use them
        RNPusherPushNotifications.subscribe(interest);
    } else {
        // Android is better, so handle faults
        RNPusherPushNotifications.subscribe(
            interest,
            (statusCode, response) => {
                console.error(statusCode, response);
            },
            (success) => {
                console.log(success);
            }
        );
    }
}

// Unsubscribe from push notifications
if (Platform.OS === 'ios') {
    // iOS callbacks are beta, so dont use them
    RNPusherPushNotifications.unsubscribe(interest);
} else {
    // Android is better, so handle faults
    RNPusherPushNotifications.unsubscribe(
        interest,
        (statusCode, response) => {
            console.error(statusCode, response);
        },
        (success) => {
            console.log(success);
        }
    );
}
```
