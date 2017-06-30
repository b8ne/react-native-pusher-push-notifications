
#import "RNPusherPushNotifications.h"
#import "UIKit/UIKit.h"
#import <UIKit/UIKit.h>
#import "RCTLog.h"

@implementation RNPusherPushNotifications

- (dispatch_queue_t)methodQueue
{
  return dispatch_get_main_queue();
}
RCT_EXPORT_MODULE()

- (NSArray<NSString *> *)supportedEvents
{
    return @[@"registered"];
}

RCT_EXPORT_METHOD(setAppKey:(NSString *)appKey)
{
    dispatch_async(dispatch_get_global_queue(DISPATCH_QUEUE_PRIORITY_DEFAULT, 0), ^{
        RCTLogInfo(@"Creating pusher with App Key: %@", appKey);
        // Pusher init
        self.pusher = [PTPusher pusherWithKey:appKey delegate:self encrypted:YES];
        UIUserNotificationType notificationTypes = UIUserNotificationTypeAlert | UIUserNotificationTypeBadge | UIUserNotificationTypeSound;
        UIUserNotificationSettings *pushNotificationSettings = [UIUserNotificationSettings settingsForTypes:notificationTypes categories: NULL];
        [[UIApplication sharedApplication ] registerUserNotificationSettings:pushNotificationSettings];
        [[UIApplication sharedApplication] registerForRemoteNotifications];

    });
}

RCT_EXPORT_METHOD(subscribe:(NSString *)channel)
{
    dispatch_async(dispatch_get_global_queue(DISPATCH_QUEUE_PRIORITY_DEFAULT, 0), ^{
        RCTLogInfo(@"Subscribing to: %@", channel);
        [[[self pusher] nativePusher] subscribe:(NSString *)channel];
    });
}

RCT_EXPORT_METHOD(unsubscribe:(NSString *)channel)
{

  dispatch_async(dispatch_get_global_queue(DISPATCH_QUEUE_PRIORITY_DEFAULT, 0), ^{
      RCTLogInfo(@"Unsubscribing from: %@", channel);
      [[[self pusher] nativePusher] unsubscribe:(NSString *)channel];
  });
}

- (void)setDeviceToken:(NSData *)deviceToken
{
    RCTLogInfo(@"setDeviceToken: %@", deviceToken);
    [[[self pusher] nativePusher] registerWithDeviceToken:deviceToken];

    [self sendEventWithName:@"registered" body:@{}];
}

@end
