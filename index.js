import {
  DeviceEventEmitter,
  NativeEventEmitter,
  NativeModules,
  Platform,
} from 'react-native';

const { RNPusherPushNotifications } = NativeModules;
const rnPusherPushNotificationsEmitter = new NativeEventEmitter(
  RNPusherPushNotifications
);

const errorCallback = callback => (statusCode, response) =>
  callback && callback(statusCode, response);
const successCallback = callback => () => callback && callback();

export default {
  setInstanceId: instanceId => {
    if (Platform.OS === 'ios') {
      RNPusherPushNotifications.setInstanceId(instanceId);
    } else {
      RNPusherPushNotifications.setAppKey(instanceId);
    }
  },
  subscribe: (channel, onError, onSuccess) => {
    if (Platform.OS === 'ios') {
      RNPusherPushNotifications.subscribe(channel, onError);
    } else {
      RNPusherPushNotifications.subscribe(
        channel,
        errorCallback(onError),
        successCallback(onSuccess)
      );
    }
  },
  setSubscriptions: (interests, onError) => {
    if (Platform.OS === 'ios') {
      RNPusherPushNotifications.setSubscriptions(interests, onError);
    } else {
      console.log('Not implemented yet');
    }
  },
  unsubscribe: (channel, onError, onSuccess) => {
    if (Platform.OS === 'ios') {
      RNPusherPushNotifications.unsubscribe(channel, onError);
    } else {
      RNPusherPushNotifications.unsubscribe(
        channel,
        errorCallback(onError),
        successCallback(onSuccess)
      );
    }
  },
  unsubscribeAll: (onError, onSuccess) => {
    if (Platform.OS === 'ios') {
      RNPusherPushNotifications.unsubscribeAll();
    } else {
      console.log('Not implemented yet');
    }
  },
  on: (eventName, callback) => {
    if (Platform.OS === 'ios') {
      rnPusherPushNotificationsEmitter.addListener(eventName, payload =>
        callback(payload)
      );
    } else {
      DeviceEventEmitter.addListener(eventName, payload => callback(payload));
    }
  },
};
