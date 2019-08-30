package com.b8ne.RNPusherPushNotifications;

import com.pusher.pushnotifications.auth.TokenProvider;

public class LocalTokenProvider implements TokenProvider {
  String _token;

  public LocalTokenProvider(String token) {
    _token = token;
  }

  @Override
  public String fetchToken(String userId) {
    return _token;
  }
}
