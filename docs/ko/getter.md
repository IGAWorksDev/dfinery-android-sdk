# 값 가져오기
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