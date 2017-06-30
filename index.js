'use strict';

import { NativeEventEmitter, NativeModules } from 'react-native';
const { RNPusherPushNotifications } = NativeModules
const rnPusherPushNotificationsEmitter = new NativeEventEmitter(RNPusherPushNotifications)

export default {
  setAppKey: (appKey) => {
    RNPusherPushNotifications.setAppKey(appKey)
  },
  subscribe: (channel) => {
    RNPusherPushNotifications.subscribe(channel)
  },
  unsubscribe: (channel) => {
    RNPusherPushNotifications.unsubscribe(channel)
  },
  on: (eventName, callback) => {
    rnPusherPushNotificationsEmitter.addListener(
      eventName,
      (payload) => callback(payload)
    )
  }
};
