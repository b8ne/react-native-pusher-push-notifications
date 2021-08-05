import {
  DeviceEventEmitter,
  NativeEventEmitter,
  NativeModules,
  Platform,
} from 'react-native';

const { RNPusherPushNotifications, RNPusherEventHelper } = NativeModules;
const rnPusherPushNotificationsEmitter = new NativeEventEmitter(
    RNPusherEventHelper
);

export default {
  clearAllState: () => {
    if (Platform.OS === 'ios') {
      RNPusherPushNotifications.clearAllState();
    } else {
      RNPusherPushNotifications.clearAllState();
    }
  },
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
      RNPusherPushNotifications.subscribe(channel, onError, onSuccess);
    }
  },
  setSubscriptions: (interests, onError) => {
    if (Platform.OS === 'ios') {
      RNPusherPushNotifications.setSubscriptions(interests, onError);
    } else {
      console.log('Not implemented yet');
    }
  },
  getSubscriptions: (onSuccess, onError) => {
    if (Platform.OS === 'ios') {
        console.log('Not implemented yet');
    } else {
        RNPusherPushNotifications.getSubscriptions(onSuccess, onError);
    }
  },
  unsubscribe: (channel, onError, onSuccess) => {
    if (Platform.OS === 'ios') {
      RNPusherPushNotifications.unsubscribe(channel, onError);
    } else {
      RNPusherPushNotifications.unsubscribe(channel, onError, onSuccess);
    }
  },
  setUserId: (userId, token, onError, onSuccess) => {
    if (Platform.OS === 'ios') {
      RNPusherPushNotifications.setUserId(userId, token, onError, onSuccess);
    } else {
      RNPusherPushNotifications.setUserId(userId, token, onError, onSuccess);
    }
  },
  setOnSubscriptionsChangedListener: (onChange) => {
    if (Platform.OS === 'ios') {
      console.log('Not implemented yet');
    } else {
      RNPusherPushNotifications.setOnSubscriptionsChangedListener(onChange);
    }
  },
  on: (eventName, callback) => {
    if (Platform.OS === 'ios') {
      return rnPusherPushNotificationsEmitter.addListener(eventName, payload => callback(payload));
    } else {
      return DeviceEventEmitter.addListener(eventName, payload => callback(payload));
    }
  },
};
