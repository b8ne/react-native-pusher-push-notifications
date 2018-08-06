#import <React/RCTBridgeModule.h>
#import <React/RCTEventEmitter.h>

@interface RNPusherEventHelper : RCTEventEmitter

+ (void)emitEventWithName:(NSString *)name andPayload:(NSDictionary *)payload;

@end
