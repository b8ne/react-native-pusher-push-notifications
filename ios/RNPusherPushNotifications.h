#import <React/RCTBridgeModule.h>

@interface RNPusherPushNotifications : NSObject <RCTBridgeModule>

-(void)setDeviceToken:(NSData *)deviceToken;
-(void)handleNotification:(NSDictionary *)userInfo;

@end
