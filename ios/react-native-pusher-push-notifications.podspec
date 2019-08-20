require 'json'
package = JSON.parse(File.read(File.join(__dir__, '../', 'package.json')))

Pod::Spec.new do |s|
  s.name          = package['name']
  s.version       = package['version']
  s.summary       = package['description']

  s.author        = package['author']
  s.homepage      = package['homepage']
  s.license       = package['license']
  s.platform      = :ios, '10.0'

  s.source        = { :git => package['repository']['url'], :tag => s.version.to_s }
  s.source_files  = '**/*.{h,m}'

  s.dependency      'React'
  s.dependency      'PushNotifications', '>= 2.1.2'
end
