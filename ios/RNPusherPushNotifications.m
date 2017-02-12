
#import "RNPusherPushNotifications.h"
#import "UIKit/UIKit.h"
#import "RCTLog.h"

@implementation RNPusherPushNotifications
    - (dispatch_queue_t)methodQueue
{
  return dispatch_get_main_queue();
}
RCT_EXPORT_MODULE()

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

- (void)init:(PTPusher *)pusher
{
  self.pusher = pusher;
}

    @end
