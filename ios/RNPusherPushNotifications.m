#import "RNPusherEventHelper.h"
#import "RNPusherPushNotifications.h"
#import "UIKit/UIKit.h"
#import <UIKit/UIKit.h>
#import "RCTLog.h"
@import PushNotifications;

@implementation RNPusherPushNotifications

RCT_EXPORT_MODULE();

- (dispatch_queue_t)methodQueue
{
  return dispatch_get_main_queue();
}

RCT_EXPORT_METHOD(setInstanceId:(NSString *)instanceId)
{
  dispatch_async(dispatch_get_main_queue(), ^{
    RCTLogInfo(@"Creating pusher with Instance ID: %@", instanceId);

    [[PushNotifications shared] startWithInstanceId:instanceId];
    [[PushNotifications shared] registerForRemoteNotifications];
  });
}

RCT_EXPORT_METHOD(subscribe:(NSString *)interest callback:(RCTResponseSenderBlock)callback) {
  dispatch_async(dispatch_get_main_queue(), ^{
    NSError *anyError;
    [[PushNotifications shared] subscribeWithInterest:interest error:&anyError completion:^{
      if (anyError) {
        callback(@[anyError, [NSNull null]]);
      }
      else {
        RCTLogInfo(@"Subscribed to interest: %@", interest);
      }
    }];
  });
}

RCT_EXPORT_METHOD(setSubscriptions:(NSArray *)interests callback:(RCTResponseSenderBlock)callback) {
  dispatch_async(dispatch_get_main_queue(), ^{
    NSError *anyError;
    [[PushNotifications shared] setSubscriptionsWithInterests:interests error:&anyError completion:^{
      if (anyError) {
        callback(@[anyError, [NSNull null]]);
      }
      else {
        RCTLogInfo(@"Subscribed to interests: %@", interests);
      }
    }];
  });
}

RCT_EXPORT_METHOD(unsubscribe:(NSString *)interest callback:(RCTResponseSenderBlock)callback) {
  dispatch_async(dispatch_get_main_queue(), ^{
    NSError *anyError;
    [[PushNotifications shared] unsubscribeWithInterest:interest error:&anyError completion:^{
      if (anyError) {
        callback(@[anyError, [NSNull null]]);
      }
      else {
        RCTLogInfo(@"Unsubscribed from interest: %@", interest);
      }
    }];
  });
}

- (void)handleNotification:(NSDictionary *)userInfo
{
    RCTLogInfo(@"handleNotification: %@", userInfo);
    //[self sendEventWithName:@"notification" body:userInfo];
    [RNPusherEventHelper emitEventWithName:@"notification" andPayload:@{@"userInfo":userInfo}];
    [[PushNotifications shared] handleNotificationWithUserInfo:userInfo];
}

- (void)setDeviceToken:(NSData *)deviceToken
{
    RCTLogInfo(@"setDeviceToken: %@", deviceToken);
    [[PushNotifications shared] registerDeviceToken:deviceToken completion:^{
        [RNPusherEventHelper emitEventWithName:@"registered" andPayload:@{}];
        //[self sendEventWithName:@"registered" body:@{@"deviceToken": deviceToken}];
        RCTLogInfo(@"REGISTERED!");
    }];
}

@end
