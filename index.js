'use strict';

import { NativeEventEmitter, NativeModules } from 'react-native';
const { RNPusherPushNotifications } = NativeModules
const rnPusherPushNotificationsEmitter = new NativeEventEmitter(RNPusherPushNotifications)

export default {
  setAppKey: (appKey) => {
    RNPusherPushNotifications.setAppKey(appKey)
  },
  subscribe: (channel, onError, onSuccess) => {
    RNPusherPushNotifications.subscribe(channel, onError, onSuccess)
  },
  unsubscribe: (channel, onError, onSuccess) => {
    RNPusherPushNotifications.unsubscribe(channel, onError, onSuccess)
  },
  on: (eventName, callback) => {
    rnPusherPushNotificationsEmitter.addListener(
      eventName,
      (payload) => callback(payload)
    )
  }
};
