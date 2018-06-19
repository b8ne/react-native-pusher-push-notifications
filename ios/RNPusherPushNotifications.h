#if __has_include(<React/RCTBridgeModule.h>)
  #import <React/RCTBridgeModule.h>
#else
  #import "RCTBridgeModule.h"
#endif

#import <React/RCTEventEmitter.h>

@interface RNPusherPushNotifications : RCTEventEmitter <RCTBridgeModule>

-(void)setDeviceToken:(NSData *)deviceToken;
-(void)handleNotification:(NSDictionary *)notification;

@end
