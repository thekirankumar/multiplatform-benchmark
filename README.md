# Current usecases
- Redux counter (increment every frame)
- Remote images grid + API

# React native
## To create iOS release bundle
Execute this on the root dir
`npx react-native bundle --platform ios --dev false --entry-file index.js --bundle-output multiplatform/iOSApp/iOSApp/main.bundle
`
Also make sure you are running/profiling in release mode in XCode

# Getting Started
- Root folder is react native
- Multiplatform code is in `/multiplatform`
- Android code is in `/multiplatform/androidApp` folder
- iOS code is in `/multiplatform/iOSApp`