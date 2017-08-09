'use strict';

import { NativeEventEmitter, NativeModules, Platform } from 'react-native';
const { RNPusherPushNotifications } = NativeModules
const rnPusherPushNotificationsEmitter = new NativeEventEmitter(RNPusherPushNotifications)

const errorCallback = callback => (statusCode, response) => callback && callback(statusCode, response);
const successCallback = callback => () => callback && callback();

export default {
  setAppKey: (appKey) => {
    RNPusherPushNotifications.setAppKey(appKey)
  },
  subscribe: (channel, onError, onSuccess) => {
    if (Platform.OS === 'ios') {
      RNPusherPushNotifications.subscribe(channel)
    } else {
      RNPusherPushNotifications.subscribe(channel, errorCallback(onError), successCallback(onSuccess))
    }
  },
  unsubscribe: (channel, onError, onSuccess) => {
    if (Platform.OS === 'ios') {
      RNPusherPushNotifications.unsubscribe(channel)
    } else {
      RNPusherPushNotifications.unsubscribe(channel, errorCallback(onError), successCallback(onSuccess))
    }
  },
  on: (eventName, callback) => {
    rnPusherPushNotificationsEmitter.addListener(
      eventName,
      (payload) => callback(payload)
    )
  }
};
