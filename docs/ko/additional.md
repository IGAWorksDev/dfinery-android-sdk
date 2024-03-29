# 🔎 부가 설정

이 문서에서는 Dfinery에서 제공하는 부가적인 설정 및 기능에 대해서 소개합니다.

## 디버깅

### 로그 활성화 하기
로그 활성화는 Dfinery의 설정을 적용하는 DfineryConfig를 사용하여 설정 가능합니다. DfineryConfig는 `init()` 메소드 호출 시에 파라미터로 넣어 적용할 수 있습니다.

```java
setLogEnable(boolean enable)
```

- 첫번째 인자인 `enable`은 로그를 표시할 지 말지 설정하는 값입니다. 기본값은 `false` 입니다.

> [!TIP]
> 값에 `BuildConfig.DEBUG` 값을 넣어 디버그 모드일때만 로그가 출력 되고 배포시에는 출력되지 않게끔 설정할 수 있습니다.

<details open>
  <summary>Java</summary>

```java
DfineryConfig config = new DfineryConfig.Builder()
    .setLogEnable(true)
    .build();
Dfinery.getInstance().init(this, "{your_application_key}", config);
```

</details>

<details open>
  <summary>Kotlin</summary>

```kotlin
val config = DfineryConfig.Builder()
    .setLogEnable(true)
    .build()
Dfinery.getInstance().init(this, "{your_application_key}", config)
```

</details>

### 로그 레벨 변경하기
로그 레벨은 Dfinery의 설정을 적용하는 DfineryConfig를 사용하여 설정 가능합니다. DfineryConfig는 `init()` 메소드 호출 시에 파라미터로 넣어 적용할 수 있습니다.

```java
setLogLevel(int logLevel)
```

- 첫번째 인자인 `logLevel `은 로그 표시 레벨을 설정하는 값입니다. 기본값은 `Log.ERROR(6)` 입니다.

<details open>
  <summary>Java</summary>

```java
DfineryConfig config = new DfineryConfig.Builder()
    .setLogLevel(Log.DEBUG)
    .build();
Dfinery.getInstance().init(this, "{your_application_key}", config);
```

</details>

<details open>
  <summary>Kotlin</summary>

```kotlin
val config = DfineryConfig.Builder()
    .setLogLevel(Log.DEBUG)
    .build()
Dfinery.getInstance().init(this, "{your_application_key}", config)
```

</details>

## 값 가져오기
### SDK 버전 정보 가져오기
SDK의 현재 버전 값을 가져옵니다.

```java
String getSdkVersion();
```

- 반환유형 : `String`

<details open>
  <summary>Java</summary>

```java
String value = DfineryProperties.getSdkVersion();
```

</details>

<details open>
  <summary>Kotlin</summary>

```kotlin
val value = DfineryProperties.getSdkVersion()
```

</details>

### 서비스 ID 가져오기
SDK에 설정된 서비스 ID 값을 가져옵니다.

```java
String getServiceId();
```

- 반환유형 : `String`

<details open>
  <summary>Java</summary>

```java
String value = DfineryProperties.getServiceId();
```

</details>

<details open>
  <summary>Kotlin</summary>

```kotlin
val value = DfineryProperties.getServiceId()
```

</details>