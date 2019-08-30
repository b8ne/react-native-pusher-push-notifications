//
//  RNPusherLocalTokenProvider.h
//  RNPusherPushNotifications
//
//  Created by Gianni Settino on 6/25/19.
//

#import <Foundation/Foundation.h>
@import PushNotifications;

NS_ASSUME_NONNULL_BEGIN

@interface RNPusherLocalTokenProvider : NSObject <TokenProvider>

@property (nonatomic, strong) NSString *_token;

- (instancetype)initWithToken:(NSString *)token;

@end

NS_ASSUME_NONNULL_END
