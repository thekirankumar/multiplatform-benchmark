# iOS
## To create iOS release bundle
Execute this on the root dir
```npx react-native bundle --platform ios --dev false --entry-file index.js --bundle-output multiplatform/iOSApp/iOSApp/main.bundle
```
Also make sure you are running/profiling in release mode in XCode