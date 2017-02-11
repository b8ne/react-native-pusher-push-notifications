
#import "RNReactNativePusherPushNotifications.h"
#import "RCTLog.h"

@implementation RNReactNativePusherPushNotifications

- (dispatch_queue_t)methodQueue
{
    return dispatch_get_main_queue();
}
RCT_EXPORT_MODULE()

RCT_EXPORT_METHOD(subscribe:(NSString *)channel)
{
  dispatch_async(dispatch_get_global_queue(DISPATCH_QUEUE_PRIORITY_DEFAULT, 0), ^{
    RCTLogInfo(@"Subscribing to: %@", channel);
    AppDelegate *appDelegate = (AppDelegate *)[[UIApplication sharedApplication] delegate];
    [appDelegate subscribeToChannel:(NSString *)channel];
  });
  
}

RCT_EXPORT_METHOD(unsubscribe:(NSString *)channel)
{
  dispatch_async(dispatch_get_global_queue(DISPATCH_QUEUE_PRIORITY_DEFAULT, 0), ^{
    RCTLogInfo(@"Unsubscribing from: %@", channel);
    AppDelegate *appDelegate = (AppDelegate *)[[UIApplication sharedApplication] delegate];
    [appDelegate unsubscribeChannel:(NSString *)channel];
  });
 
}

@end