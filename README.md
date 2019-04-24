# react-native-pusher-push-notifications

Manage pusher interest subscriptions from within React Native JS

**IMPORTANT!!!**

This module is intended to complement the setup with [Carthage](https://pusher.com/docs/beams/getting-started/ios/sdk-integration#install-from-carthage). This module allows that implementation to be accessed directly from React Native JS.

```Cartfile
github "pusher/push-notifications-swift" ~> 2.0.0
```

More information about Pusher Beams and their Swift library, `push-notifications-swift`, can be found on their [Github repo](https://github.com/pusher/push-notifications-swift).

There are some issues installing this repo you need to be aware of. More info can be found in [issue 65](https://github.com/pusher/push-notifications-swift/issues/65).

[![npm version](https://badge.fury.io/js/react-native-pusher-push-notifications.svg)](https://badge.fury.io/js/react-native-pusher-push-notifications)

## Getting started

`$ npm install react-native-pusher-push-notifications --save`

or yarn

`$ yarn add react-native-pusher-push-notifications`

### Automatic installation

React native link has shown to incorrectly setup projects, so follow the manual instructions below.

### Manual installation

#### iOS

1.  In Xcode, in the project navigator, right click `Libraries` ➜ `Add Files to [your project's name]`
2.  Go to `node_modules` ➜ `react-native-pusher-push-notifications` and add `RNPusherPushNotifications.xcodeproj`
3.  In Xcode, in the project navigator, select your project. Add `libRNPusherPushNotifications.a` to your project's `Build Phases` ➜ `Link Binary With Libraries`

** DO NOT follow the pusher.com push notification docs that detail modifying the AppDelegate.h/m files! - this package takes care of most of the steps for you**

1.  Open `AppDelegate.m` and add:

```aidl
    // Add this at the top of AppDelegate.m
    #import "RNPusherPushNotifications.h"

    // Add the following as a new methods to AppDelegate.m
    - (void)application:(UIApplication *)application didRegisterForRemoteNotificationsWithDeviceToken:(NSData *)deviceToken {
      NSLog(@"Registered for remote with token: %@", deviceToken);
      [[RNPusherPushNotifications alloc] setDeviceToken:deviceToken];
    }

    - (void)application:(UIApplication *)application didReceiveRemoteNotification:(NSDictionary *)userInfo fetchCompletionHandler:(void (^)(UIBackgroundFetchResult))completionHandler {
      [[RNPusherPushNotifications alloc] handleNotification:userInfo];
    }

    -(void)application:(UIApplication *)application didFailToRegisterForRemoteNotificationsWithError:(NSError *)error {
      NSLog(@"Remote notification support is unavailable due to error: %@", error.localizedDescription);
    }
```

#### Android

COMING SOON

## Usage

```javascript
// Import module
import RNPusherPushNotifications from 'react-native-pusher-push-notifications';

// Get your interest
const donutsInterest = 'debug-donuts';

// Initialize notifications
export const init = () => {
  // Set your app key and register for push
  RNPusherPushNotifications.setInstanceId(CONSTANTS.PUSHER_INSTANCE_ID);

  // Init interests after registration
  RNPusherPushNotifications.on('registered', () => {
    subscribe(donutsInterest);
  });

  // Setup notification listeners
  RNPusherPushNotifications.on('notification', handleNotification);
};

// Handle notifications received
const handleNotification = notification => {
  console.log(notification);

  // iOS app specific handling
  if (Platform.OS === 'ios') {
    switch (notification.appState) {
      case 'inactive':
      // inactive: App came in foreground by clicking on notification.
      //           Use notification.userInfo for redirecting to specific view controller
      case 'background':
      // background: App is in background and notification is received.
      //             You can fetch required data here don't do anything with UI
      case 'active':
      // App is foreground and notification is received. Show a alert or something.
      default:
        break;
    }
  }
};

// Subscribe to an interest
const subscribe = interest => {
  // Note that only Android devices will respond to success/error callbacks
  RNPusherPushNotifications.subscribe(
    interest,
    (statusCode, response) => {
      console.error(statusCode, response);
    },
    () => {
      console.log('Success');
    }
  );
};

// Unsubscribe from an interest
const unsubscribe = interest => {
  RNPusherPushNotifications.unsubscribe(
    interest,
    (statusCode, response) => {
      console.tron.logImportant(statusCode, response);
    },
    () => {
      console.tron.logImportant('Success');
    }
  );
};
```

## iOS only methods

```javascript
// Set interests
const donutInterests = ['debug-donuts', 'debug-general'];
const setSubscriptions = donutInterests => {
  // Note that only Android devices will respond to success/error callbacks
  RNPusherPushNotifications.setSubscriptions(
    donutInterests,
    (statusCode, response) => {
      console.error(statusCode, response);
    },
    () => {
      console.log('Success');
    }
  );
};
```

## Troubleshooting

1.  `dyld: Library not loaded: @rpath/PushNotifications.framework/PushNotifications`
    Ensure the PushNotifications.framework library is added to `General => Embedded Binaries`

2.  `dyld: Library not loaded: @rpath/libswiftCore.dylib`
    Ensure `Build Settings => Always Embed Swift Standard Libraries` is set to `Yes` and a valid Development provisioning profile exists on your system.
