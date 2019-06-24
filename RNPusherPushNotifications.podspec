require 'json'
package = JSON.parse(File.read('./package.json'))

Pod::Spec.new do |s|
  s.name         = 'RNPusherPushNotifications'
  s.version      = package['version']
  s.summary      = package['description']
  s.homepage     = "https://github.com/b8ne/react-native-pusher-push-notifications"
  s.license      = { :type => 'MIT' }
  s.author       = "Ben Sutter"
  s.source       = { :git => "https://github.com/b8ne/react-native-pusher-push-notifications.git", :tag => "v#{s.version}" }
  s.source_files = 'ios/**/*.{h,m}'
  s.dependency 'React'
  s.dependency 'PushNotifications', '~> 2.0.0'
  s.platform = :ios, '10.0'
end
