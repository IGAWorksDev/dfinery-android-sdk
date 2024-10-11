![dfinery](https://dfn-v2-frontend.s3.ap-northeast-2.amazonaws.com/console-remote/prod/shared/0.1.0/img/dfinery-logo-with-text.bde4279c.svg)

## Dfinery Android SDK

![Sonatype Nexus (Releases)](https://img.shields.io/nexus/r/com.igaworks.dfinery/android-sdk?server=https%3A%2F%2Fs01.oss.sonatype.org)

Dfinery's official Android SDK for Mobile Marketing

- [Dfinery User Guide](https://docs.dfinery.ai/user-guide)
- [Dfinery Developer Guide](https://docs.dfinery.ai/developer-guide/platform/android)

### ℹ️ Information
- minSdk : 19 / Android 4.4
- compileSdk : 34
- targetSdk : 33

### 🔗 Dependencies

- [`com.google.android.gms:play-services-appset`](https://developer.android.com/training/articles/app-set-id) : **Required**
- [`com.google.android.gms:play-services-ads-identifier`](https://support.google.com/googleplay/android-developer/answer/6048248) : Required only if you want to collect Google ADID(Optional)
- [`androidx.core:core`](https://developer.android.com/jetpack/androidx/releases/core) : Required only if you want to use push action(Optional)
- [`com.google.firebase:firebase-messaging`](https://firebase.google.com/docs/cloud-messaging) : Required only if you want to use push action(Optional)

### 📁 Components
- [sample](https://github.com/IGAWorksDev/dfinery-android-sdk/tree/main/sample) :  a folder containing sample app

### 🗄️ Changelog

Click [here](./CHANGELOG.md) to view the Android SDK Changelog.