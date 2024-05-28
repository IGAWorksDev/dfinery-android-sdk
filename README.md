![dfinery](https://www.dfinery.io/assets/images/logos/logo_color.svg)

## Dfinery Android SDK

![Sonatype Nexus (Releases)](https://img.shields.io/nexus/r/com.igaworks.dfinery/android-sdk?server=https%3A%2F%2Fs01.oss.sonatype.org)

Dfinery's official Android SDK for Mobile Marketing

### ℹ️ Information
- minSdk : 19 / Android 4.4+
- compileSdk : 34
- targetSdk : 33

### 🔗 Dependencies

- [`com.google.android.gms:play-services-appset`](https://developer.android.com/training/articles/app-set-id)
- [`com.google.android.gms:play-services-ads-identifier`](https://support.google.com/googleplay/android-developer/answer/6048248) : Required only if you want to collect Google ADID(Optional)
- [`androidx.core:core`](https://developer.android.com/jetpack/androidx/releases/core) : Required only if you want to use push action(Optional)
- [`com.google.firebase:firebase-messaging`](https://firebase.google.com/docs/cloud-messaging) : Required only if you want to use push action(Optional)

### 📁 Components
- `docs` : a folder containing documents needed to use the SDK
- `sample` :  a folder containing sample app

### ✨ Features

<details open>
 <summary>EN</summary>

- [Quick Start](./docs/en/integration.md)
- [Analytics](./docs/en/analytics.md)
- [Setting up Unifed ID](./docs/en/identity.md)
- [Setting up a user profile](./docs/en/user_profile.md)
- [Action](./docs/en/action.md)
  - [Push Notification](./docs/en/push_notification.md)
  - [In-app message](./docs/en/inappmessage.md)
- Advanced Use Cases
  - [Unified ID linking scenario](./docs/en/identity_scenario.md)
  - [Additional settings](./docs/en/additional.md)
  - [Get SDK properties](./docs/en/getter.md)
  - [Setting up using XML](./docs/en/config_by_xml.md)
  - [Integrate Dfinery WebView](./docs/en/web_view_interface.md)
</details>
<details open>
 <summary>KO</summary>

- [빠른 시작](./docs/ko/integration.md)
- [분석](./docs/ko/analytics.md)
- [통합 ID 식별 정보 설정하기](./docs/ko/identity.md)
- [유저 프로필 설정하기](./docs/ko/user_profile.md)
- [액션](./docs/ko/action.md)
  - [푸시 알림](./docs/ko/push_notification.md)
  - [인앱메시지](./docs/ko/inappmessage.md)
- 고급 사용 사례
  - [통합 ID 연동 시나리오](./docs/ko/identity_scenario.md)
  - [부가 설정](./docs/ko/additional.md)
  - [값 가져오기](./docs/ko/getter.md)
  - [XMl을 사용하여 설정하기](./docs/ko/config_by_xml.md)
  - [Dfinery WebView 연동](./docs/ko/web_view_interface.md)

</details>

### 🗄️ Changelog

Click [here](./CHANGELOG.md) to view the Android SDK Changelog.