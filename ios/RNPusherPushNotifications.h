#import "RCTBridgeModule.h"
#import <Pusher/Pusher.h>

@interface RNPusherPushNotifications : NSObject <RCTBridgeModule>

@property (nonatomic, strong) PTPusher *pusher;

+(void)setPusher:(PTPusher *)pusher;

@end


  