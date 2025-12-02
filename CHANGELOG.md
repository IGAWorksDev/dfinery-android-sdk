## 2.4.3 (2025-10-23)

<details open>
 <summary>EN</summary>


## 2.4.4 (2025-12-02)

##### Fixed
- We resolved an issue where, rarely, when a new user identity was set (via `setIdentity()`), stored events were sent to the server with the new identity applied earlier than intended.
#### Fixed
- In applications with the Dfinery SDK installed and push integration completed, the issue where push notifications received through a route other than **Dfinery Console** were notified with empty content has been resolved.


</details>
<details open>
 <summary>KO</summary>


## 2.4.4 (2025-12-02)

##### 고쳐짐
- `setIdentity()` API를 사용할 때, 드물게 저장되어 있던 이벤트에 새로운 식별자가 의도보다 일찍 적용되어 서버로 전송되는 문제가 해결되었습니다.
#### 고쳐짐

- Dfinery SDK가 탑재되고 푸시 연동이 완료된 애플리케이션에서, **Dfinery Console을 통하지 않고** 다른 경로로 수신된 푸시 알림의 내용이 비어있는 상태로 노출되는 문제가 해결되었습니다.

</details>

## 2.4.2 (2025-10-02)

<details open>
 <summary>EN</summary>

#### Fixed
- Fixed an issue where in-app message custom parent views would not be set in certain situations.


</details>
<details open>
 <summary>KO</summary>

#### 고쳐짐

- 특정 상황에서 인앱메시지 커스텀 부모 뷰가 설정되지 않는 문제가 해결되었습니다.

</details>

## 2.4.1 (2025-10-01)

<details open>
 <summary>EN</summary>

#### Fixed

- API call prevention logic added for failed initialization.
- Improved stability of in-app message display.
- Enhanced stability of deep link opening from push notifications.

</details>
<details open>
 <summary>KO</summary>

#### 고쳐짐

- 초기화 실패 시 API 호출 방지 로직 추가.
- 인앱 메시지 표시 안정성 향상.
- 푸시 알림에서 딥 링크 열기 안정성 향상.

</details>

## 2.4.0 (2025-08-12)

<details open>
 <summary>EN</summary>

#### Added

- Push ad campaigns are supported.
- `WebLink` action type is supported when a push clicking.

</details>
<details open>
 <summary>KO</summary>

#### 추가됨

- 푸시 광고 캠페인이 지원됩니다.
- 푸시 클릭시 `WebLink` 액션 타입이 지원됩니다.

</details>

## 2.3.5 (2025-07-04)

<details open>
 <summary>EN</summary>

#### Fixed

- Fixed an issue in `2.3.4` where [ANR](https://developer.android.com/topic/performance/vitals/anr) could occur when an event request failed.

</details>
<details open>
 <summary>KO</summary>

#### 고쳐짐

- `2.3.4` 버전에서 이벤트 요청 실패시 [ANR](https://developer.android.com/topic/performance/vitals/anr)이 발생할 수 있는 문제가 해결되었습니다.

</details>

## 2.3.4 (2025-07-04)

<details open>
 <summary>EN</summary>

 > **⚠️Warning: A potential issue has been identified in this version (`v2.3.4`) that may cause an Application Not Responding (ANR) error under certain conditions.**
>
> Please **never use** this in production environments and immediately update to the latest stable version, **`v2.3.5`** or later.
>
> We sincerely apologize for any inconvenience.

- Enhanced data processing stability by resolving event duplication, omission, and ordering errors that occurred during concurrent API calls in a multi-threaded environment.

- Ensured compatibility with the Adbrix SDK by resolving a database (DB) conflict that occurred when used concurrently.

- Removed the [com.android.support:support-annotations](https://mvnrepository.com/artifact/com.android.support/support-annotations) dependency.

- Updated compileSdk to **35**.

</details>
<details open>
 <summary>KO</summary>

> **⚠️경고: 이 버전(`v2.3.4`)에서 특정 조건 하에 ANR(Application Not Responding)을 유발할 수 있는 잠재적 문제가 발견되었습니다.**
> 
> 운영 환경(Production)에서는 **절대 사용하지 마시고**, 안정성이 확보된 최신 버전인 **`v2.3.5`** 이상으로 즉시 업데이트해주시기 바랍니다.
> 
> 이용에 불편을 드려 대단히 죄송합니다.

- 다중 스레드 환경에서 API 동시 호출 시 발생하던 이벤트 정보의 중복, 누락 및 순서 오류를 해결하여 데이터 처리 안정성을 강화했습니다.
- Adbrix SDK와 함께 사용 시 발생하던 데이터베이스(DB) 충돌 문제를 해결하여 SDK 호환성을 확보했습니다.
- [com.android.support:support-annotations](https://mvnrepository.com/artifact/com.android.support/support-annotations) 의존성이 제거되었습니다.
- compileSdk가 **35**로 변경되었습니다.

</details>

## 2.3.3 (2025-06-25)

<details open>
 <summary>EN</summary>

 #### Added

 - You can now set notification accent color using `dfinery.xml`.

```xml
<string name="com_igaworks_dfinery_notification_accent_color">#e00052</string>
```

 #### Fixed

- Fixed a timing issue when calling APIs before SDK initialization.

</details>
<details open>
 <summary>KO</summary>

#### 추가됨

- 이제 `dfinery.xml`을 사용하여 알림 강조 색상을 설정할 수 있습니다.

```xml
<string name="com_igaworks_dfinery_notification_accent_color">#e00052</string>
```

#### 고쳐짐

- SDK 초기화 전 API 호출 시 드물게 발생하던 타이밍 이슈를 수정했습니다.

</details>

## 2.3.2 (2025-06-20)

<details open>
 <summary>EN</summary>

 #### Notes

 - Changes for third-party integration.

</details>
<details open>
 <summary>KO</summary>

#### 주요사항

- 서드파티 연동을 위한 구조 변경.

</details>

## 2.3.1 (2025-05-28)

<details open>
 <summary>EN</summary>

 #### Deprecated
- `PUSH_OPTIN` in DFUserProfile is deprecated. If you need to change whether to allow receiving push notifications, please use the `PUSH_ADS_OPTIN`.

</details>
<details open>
 <summary>KO</summary>

#### 더 이상 사용되지 않음
- DFUserProfile의 `PUSH_OPTIN`이 Deprecated 되었습니다. 푸시 알림 수신 허용 여부를 변경해야 하실 경우 `PUSH_ADS_OPTIN`을 써주시길 바랍니다.

</details>

## 2.3.0 (2025-04-14)

<details open>
 <summary>EN</summary>

 #### Added
- You can now specify the accent color for push notifications.

```java
new DfineryConfig.Builder()
    .setNotificationAccentColor(Color.GREEN)
    .build();
```

</details>
<details open>
 <summary>KO</summary>

#### 추가됨
- 푸시 알림의 강조 색상을 지정할 수 있게 되었습니다.

```java
new DfineryConfig.Builder()
    .setNotificationAccentColor(Color.GREEN)
    .build();
```

</details>

## 2.2.0 (2025-03-27)

<details open>
 <summary>EN</summary>

 #### Added
- The following in-app message operators have been added:
    - NDaysAfterEveryYear
    - NMonthsAfter
    - NMonthsAfterEveryYear
    - NDaysAfter

#### Fixed
- If a timeout occurs during a request, the request will be retried.

</details>
<details open>
 <summary>KO</summary>

#### 추가됨
- 다음의 인앱메시지 연산자가 추가 되었습니다.
    - N일 후 특정일 연도제외
    - N개월 후 특정일 연도포함
    - N개월 후 특정일 연도제외
    - N일 후 특정일 연도포함

#### 고쳐짐
- 요청 중 Timeout 발생시 재시도 하도록 변경되었습니다.

</details>

## 2.1.4 (2025-03-20)

<details open>
 <summary>EN</summary>

#### Fixed
- Fixed the bug where push click events would not be counted if the Activity that was launched after the push click was finished within `onCreate()`.

</details>
<details open>
 <summary>KO</summary>

#### 고쳐짐
- 푸시 클릭 후 실행되는 Activity가 `onCreate()` 내에서 종료될 경우 푸시 클릭 이벤트가 집계되지 않는 버그가 수정되었습니다.

</details>

## 2.1.3 (2025-03-20)

<details open>
 <summary>EN</summary>

#### Fixed
- Fixed the bug where changed OS version in device profile was not updated to the server.

</details>
<details open>
 <summary>KO</summary>

#### 고쳐짐
- 기기 프로필에서 변경된 OS 버전이 서버에 업데이트되지 않는 버그를 수정되었습니다.

</details>

## 2.1.2 (2025-02-24)

<details open>
 <summary>EN</summary>

#### Fixed
- Fixed the issue where push notifications with buttons were collected at the wrong click location.
- Fixed the issue where push click events were not collected depending on the specific `launchMode` setting of the Activity.
    > Please add `setIntent()` to `onNewIntent()` of the Activity that runs when a push click is made.
    > ```java
    > protected void onNewIntent(Intent intent) {
    > super.onNewIntent(intent);
    > setIntent(intent);
    >}
    >```

#### Added
- Support for the campaign journey feature.

</details>
<details open>
 <summary>KO</summary>

#### 고쳐짐
- 버튼이 있는 푸시 알림 클릭 시 잘못된 클릭 위치로 수집되는 현상이 수정되었습니다.
- Activity의 특정 `launchMode` 설정에 따라 푸시 클릭 이벤트가 수집되지 않는 현상이 수정되었습니다.
    > 푸시 클릭시 실행하는 Activity의 `onNewIntent()`에 `setIntent()`를 추가해 주시기 바랍니다.
    > ```java
    > protected void onNewIntent(Intent intent) {
    >    super.onNewIntent(intent);
    >    setIntent(intent); 
    >}
    >```

#### 추가됨
- 캠페인 여정 기능이 지원됩니다.

</details>

## 2.1.1 (2025-02-06)

<details open>
 <summary>EN</summary>

#### Fixed
- Fixed an issue where in-app message exposure caused a load internally under certain conditions on Android 15.

</details>
<details open>
 <summary>KO</summary>

#### 고쳐짐
- Android 15의 특정 조건에서 내부적으로 인앱메시지 노출이 부하를 일으키는 현상 수정되었습니다.

#### 추가됨
- DfineryProperties에 `setUserProfile(String key, JSONArray value)`가 추가되었습니다.

</details>

## 2.1.0 (2025-01-09)

<details open>
 <summary>EN</summary>

#### Fixed
- Fixed an issue where the following in-app message would not be displayed if the in-app message rendering failed.

#### Added
- Added `setUserProfile(String key, JSONArray value)` in DfineryProperties.

</details>
<details open>
 <summary>KO</summary>

#### 고쳐짐
- 인앱메시지 렌더링이 실패했을 경우 다음 인앱메시지가 표시되지 않는 문제가 해결되었습니다.

#### 추가됨
- DfineryProperties에 `setUserProfile(String key, JSONArray value)`가 추가되었습니다.

</details>

## 2.0.1 (2024-12-30)

<details open>
 <summary>EN</summary>

#### Fixed
- When an API request fails due to issues such as network communication, it is retried.
- Changed so that network request payload is output when the log level is set to Debug or higher.

#### Added
- Personalization of event attributes is supported when displaying in-app messages.
- `disableSDK()` has been added to stop the SDK and remove local data.
- `enableSDK()` has been added to resume the SDK when it is stopped using `disableSDK()`.

#### Removed
- suspendUserTargeting() has been removed.

</details>
<details open>
 <summary>KO</summary>

#### 고쳐짐
- 네트워크 통신 등의 이슈로 API 요청이 실패했을때 재시도하게 되었습니다.
- Log 레벨을 Debug 이상으로 설정했을 경우 네트워크 요청 페이로드가 출력되도록 변경되었습니다.

#### 추가됨
- 인앱메시지 표시시 이벤트 속성 개인화가 지원됩니다.
- SDK를 중단시키고 로컬 데이터를 제거하는 `disableSDK()`가 추가되었습니다.
- `disableSDK()`를 사용하여 SDK가 중지 상태일때 재개하는 `enableSDK()`가 추가되었습니다.

#### 제거됨
- suspendUserTargeting()가 제거되었습니다.

</details>

## 1.2.1 (2024-11-27)

<details open>
 <summary>EN</summary>

- Fixed a bug that prevented in-app messages from being used in version 1.2.0
- Change the name of a constant.

</details>
<details open>
 <summary>KO</summary>

- 1.2.0 버전에서 인앱메시지를 사용할 수 없던 버그가 수정되었습니다.
- 상수 명칭 변경.

</details>

## 1.2.0 (2024-11-21)

<details open>
 <summary>EN</summary>

- Change the name of a constant.
- You can now reset the google advertising ID and push token by set a null value.

</details>
<details open>
 <summary>KO</summary>

- 상수 명칭 변경
- 광고 ID와 푸시 토큰 값을 `null`을 입력하여 초기화 할 수 있게 되었습니다.

</details>

## 1.1.2 (2024-11-14)

<details open>
 <summary>EN</summary>

- Support for in-app message operators with Null, NotNull and Empty.
- Fixed a bug where display restrictions based on exposure frequency of test in-app messages were not applied.

</details>
<details open>
 <summary>KO</summary>

- 값이 있는, 값이 없는, 빈 배열 인앱메시지 연산자 지원.
- 테스트 인앱메시지의 노출 빈도에 따른 표시 제한이 적용되지 않는 버그 수정.

</details>

## 1.1.1 (2024-10-29)

<details open>
 <summary>EN</summary>

- Fixed bug that occurs when delaying in-app messages without setting an end event.
- Changed the display logic when a delayed in-app message for an event with the same name is registered.
- Fixed error that some operators of in-app messages are processed as matching conditions.
- Modified so that test in-app messages are displayed with priority over general in-app messages.
- Fixed in-app messages to not appear darker than the console webpage.

</details>
<details open>
 <summary>KO</summary>

- 종료 이벤트를 설정하지 않고 인앱메시지를 지연하였을때 발생하는 버그 수정.
- 동일한 이름을 가진 이벤트의 지연된 인앱메시지가 등록되었을때 노출 로직 변경.
- 인앱메시지 일부 연산자의 조건이 부합으로 처리되는 오류 수정
- 테스트 인앱메시지가 일반 인앱메시지 보다 우선 노출되도록 수정.
- 인앱메시지가 콘솔 웹페이지 보다 어둡게 표시되지 않도록 수정.

</details>

## 1.1.0 (2024-10-10)
<details open>
 <summary>EN</summary>
 
- Add push module.
- Add in-app message module.

</details>
<details open>
 <summary>KO</summary>
 
- 푸시 기능 추가.
- 인앱메시지 기능 추가.

</details>

## 1.0.0 (2024-03-29)
<details open>
 <summary>EN</summary>
 
- Initial release.

</details>
<details open>
 <summary>KO</summary>
 
- 최초 배포.

</details>