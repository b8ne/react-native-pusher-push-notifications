//
//  RNPusherLocalTokenProvider.m
//  RNPusherPushNotifications
//
//  Created by Gianni Settino on 6/25/19.
//

#import "RNPusherLocalTokenProvider.h"

@implementation RNPusherLocalTokenProvider

- (instancetype)initWithToken:(NSString *)token
{
    self = [super init];
    if (self) {
        self._token = token;
    }
    return self;
}

- (BOOL)fetchTokenWithUserId:(NSString * _Nonnull)userId error:(NSError *__autoreleasing  _Nullable * _Nullable)error completionHandler:(void (^ _Nonnull)(NSString * _Nonnull, NSError * _Nullable))completion {
    completion(self._token, nil);
    return YES;
}

@end
