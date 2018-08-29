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
  RCTLogInfo(@"Subscribing to interest: %@", interest);
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
    UIApplicationState state = [UIApplication sharedApplication].applicationState;

    NSString *appState = @"active";
    RCTLogInfo(@"handleNotification: %@", userInfo);

    if ( state == UIApplicationStateActive)
    {
        RCTLogInfo(@"1. App is foreground and notification is recieved. Show a alert.");
    }
    else if( state == UIApplicationStateBackground)
    {
        RCTLogInfo(@"2. App is in background and notification is received. You can fetch required data here don't do anything with UI.");
        appState = @"background";
    }
    else if( state == UIApplicationStateInactive)
    {
        RCTLogInfo(@"3. App came in foreground by used clicking on notification. Use userinfo for redirecting to specific view controller.");
        appState = @"inactive";
    }

    if((bool)[userInfo valueForKeyPath:@"aps.data.incrementBadge"]) {
        NSInteger badgeCount = [[UIApplication sharedApplication] applicationIconBadgeNumber];
        [UIApplication sharedApplication].applicationIconBadgeNumber = (badgeCount+1);
        RCTLogInfo(@"increment badge number too: %ld", (long)( badgeCount+1 ));
    }

    [RNPusherEventHelper emitEventWithName:@"notification" andPayload:@{
      @"userInfo":userInfo,
      @"appState":appState
    }];

    [[PushNotifications shared] handleNotificationWithUserInfo:userInfo];
}

- (void)setDeviceToken:(NSData *)deviceToken
{
    RCTLogInfo(@"setDeviceToken: %@", deviceToken);
    [[PushNotifications shared] registerDeviceToken:deviceToken completion:^{
        [RNPusherEventHelper emitEventWithName:@"registered" andPayload:@{}];
        RCTLogInfo(@"REGISTERED!");
    }];
}

@end
