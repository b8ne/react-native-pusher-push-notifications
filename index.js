'use strict';

import { NativeEventEmitter, NativeModules, Platform } from 'react-native';
const { RNPusherPushNotifications } = NativeModules
const rnPusherPushNotificationsEmitter = new NativeEventEmitter(RNPusherPushNotifications)

export default {
  setAppKey: (appKey) => {
    RNPusherPushNotifications.setAppKey(appKey)
  },
  subscribe: (channel, onError, onSuccess) => {
    if (Platform.os === 'ios') {
      RNPusherPushNotifications.subscribe(channel)
    } else {
      RNPusherPushNotifications.subscribe(channel, onError, onSuccess)
    }
  },
  unsubscribe: (channel, onError, onSuccess) => {
    if (Platform.os === 'ios') {
      RNPusherPushNotifications.unsubscribe(channel)
    } else {
      RNPusherPushNotifications.unsubscribe(channel, onError, onSuccess)
    }
  },
  on: (eventName, callback) => {
    rnPusherPushNotificationsEmitter.addListener(
      eventName,
      (payload) => callback(payload)
    )
  }
};
