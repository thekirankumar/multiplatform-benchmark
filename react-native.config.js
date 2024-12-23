
/** @type import("@react-native-community/cli-types").Config */
module.exports = {
  project: {
    android: {
      sourceDir: './multiplatform',
      manifestPath: './multiplatform/androidApp/AndroidManifest.xml',
      packageName: 'com.thekirankumar.crossplatformbenchmark.android'
    },
    ios: {
      sourceDir: './multiplatform/iosApp'
    },
  },
};