# react-native-pusher-push-notifications
Manage pusher channel subscriptions from within React Native JS

## Getting started

`$ npm install react-native-react-native-pusher-push-notifications --save`

### Mostly automatic installation

`$ react-native link react-native-react-native-pusher-push-notifications`

### Manual installation


#### iOS

1. In XCode, in the project navigator, right click `Libraries` ➜ `Add Files to [your project's name]`
2. Go to `node_modules` ➜ `react-native-react-native-pusher-push-notifications` and add `RNReactNativePusherPushNotifications.xcodeproj`
3. In XCode, in the project navigator, select your project. Add `libRNReactNativePusherPushNotifications.a` to your project's `Build Phases` ➜ `Link Binary With Libraries`
4. Run your project (`Cmd+R`)<

#### Android

1. Open up `android/app/src/main/java/[...]/MainActivity.java`
  - Add `import com.reactlibrary.RNReactNativePusherPushNotificationsPackage;` to the imports at the top of the file
  - Add `new RNReactNativePusherPushNotificationsPackage()` to the list returned by the `getPackages()` method
2. Append the following lines to `android/settings.gradle`:
  	```
  	include ':react-native-react-native-pusher-push-notifications'
  	project(':react-native-react-native-pusher-push-notifications').projectDir = new File(rootProject.projectDir, 	'../node_modules/react-native-react-native-pusher-push-notifications/android')
  	```
3. Insert the following lines inside the dependencies block in `android/app/build.gradle`:
  	```
      compile project(':react-native-react-native-pusher-push-notifications')
  	```

#### Windows
[Read it! :D](https://github.com/ReactWindows/react-native)

1. In Visual Studio add the `RNReactNativePusherPushNotifications.sln` in `node_modules/react-native-react-native-pusher-push-notifications/windows/RNReactNativePusherPushNotifications.sln` folder to their solution, reference from their app.
2. Open up your `MainPage.cs` app
  - Add `using Cl.Json.RNReactNativePusherPushNotifications;` to the usings at the top of the file
  - Add `new RNReactNativePusherPushNotificationsPackage()` to the `List<IReactPackage>` returned by the `Packages` method


## Usage
```javascript
import RNReactNativePusherPushNotifications from 'react-native-react-native-pusher-push-notifications';

// TODO: What do with the module?
RNReactNativePusherPushNotifications;
```
  