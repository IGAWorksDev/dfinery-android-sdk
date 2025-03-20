## 2.1.3 (2024-03-20)

<details open>
 <summary>EN</summary>

#### Fixed
- Fixed the bug where changed OS version in device profile was not updated to the server.

</details>
<details open>
 <summary>KO</summary>

#### 고쳐짐
- 기기 프로필에서 변경된 OS 버전이 서버에 업데이트되지 않는 버그를 수정했습니다.

</details>

## 2.1.2 (2024-02-24)

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

## 2.1.1 (2024-02-06)

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

## 2.1.0 (2024-01-09)

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