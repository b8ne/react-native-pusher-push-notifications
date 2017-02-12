#import "RCTBridgeModule.h"
#import <Pusher/Pusher.h>

@interface RNPusherPushNotifications : NSObject <RCTBridgeModule>

@property (nonatomic, strong) PTPusher *pusher;
@property (nonatomic, strong) NSString *pusherKey;

-(void)init:(PTPusher *)pusher;

@end

  