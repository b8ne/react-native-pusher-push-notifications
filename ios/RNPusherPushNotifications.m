
#import "RNPusherPushNotifications.h"
#import "UIKit/UIKit.h"
#import <UIKit/UIKit.h>
#import "RCTLog.h"
@import PushNotifications;

@implementation RNPusherPushNotifications

- (dispatch_queue_t)methodQueue
{
  return dispatch_get_main_queue();
}
RCT_EXPORT_MODULE()

- (NSArray<NSString *> *)supportedEvents
{
    return @[@"registered", @"notification"];
}

RCT_EXPORT_METHOD(setAppKey:(NSString *)appKey)
{
    dispatch_async(dispatch_get_main_queue(), ^{
        RCTLogInfo(@"Creating pusher with App Key: %@", appKey);
        [[PushNotifications shared] startWithInstanceId:appKey];
        [[PushNotifications shared] registerForRemoteNotifications];
    });
}

RCT_EXPORT_METHOD(subscribe:(NSString *)interest)
{
    dispatch_async(dispatch_get_global_queue(DISPATCH_QUEUE_PRIORITY_DEFAULT, 0), ^{
        RCTLogInfo(@"Subscribing to: %@", interest);
        NSError *anyError;
        [[PushNotifications shared] subscribeWithInterest:interest error:&anyError completion:^{
          RCTLogInfo(@"Subscribed to interest: %@", interest);
        }];
    });
}

RCT_EXPORT_METHOD(unsubscribe:(NSString *)interest)
{
  dispatch_async(dispatch_get_global_queue(DISPATCH_QUEUE_PRIORITY_DEFAULT, 0), ^{
      RCTLogInfo(@"Unsubscribing from: %@", interest);
      NSError *anyError;
      [[PushNotifications shared] unsubscribeWithInterest:interest error:&anyError completion:^{
        RCTLogInfo(@"Unsubscribed from interest: %@", interest);
      }];
  });
}

- (void)handleNotification:(NSDictionary *)notification
{
    [self sendEventWithName:@"notification" body:notification];
    [[PushNotifications shared] handleNotificationWithUserInfo:notification];
}

- (void)setDeviceToken:(NSData *)deviceToken
{
    RCTLogInfo(@"setDeviceToken: %@", deviceToken);
    [[PushNotifications shared] registerDeviceToken:deviceToken completion:^{
      RCTLogInfo(@"SEND REGISTERED");
      [self sendEventWithName:@"registered" body:@{}];
    }];
}

@end
