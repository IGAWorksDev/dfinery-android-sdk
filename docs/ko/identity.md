# 🪪 통합 ID 식별 정보 설정하기

Dfinery는 사용자를 식별하기 위해 통합 ID를 발급하여 관리합니다. 통합 ID는 여러 정보를 규합하여 하나의 값을 만들기 때문에 정보가 많이 입력 될 경우 아이디의 고유성이 더 보장됩니다. 해당 정보들은 모두 선택사항이며 단말기에 암호화하여 저장됩니다.

## 설정하기
통합 ID 식별 정보는`DfineryProperties.setIdentity()` 메소드를 사용하여 설정이 가능합니다. 통합 ID 식별 종류는 `DF.Identity`에 정의되어 있으며, 기 정의된 값만 사용 할 수 있습니다.

> [!WARNING]
> `EXTERNAL_ID`의 경우 통합 ID 식별 정보 중 사용자를 구분하는 중요한 값으로 사용되므로 되도록 고정 값을 써주시기 바랍니다.

### 통합 ID 식별 정보 유형
|유형|내용|
|---|---|
|DF.Identity.EXTERNAL_ID|사용자 ID|
|DF.Identity.EMAIL|사용자 이메일|
|DF.Identity.PHONE_NO|사용자 전화번호|
|DF.Identity.KAKAO_USER_ID|사용자의 카카오 계정 아이디|
|DF.Identity.LINE_USER_ID|사용자의 라인 계정 아이디|

### 항목 별로 설정하기

```java
void setIdentity(Identity identity, String value)
```
    
- 첫번째 인자인 `identity`은 설정할 통합 ID 식별 정보의 종류를 의미합니다.
-  두번째 인자인 `value`는 설정할 식별 정보의 값을 의미합니다. `null`도 입력 가능합니다.

```kotlin
DfineryProperties.setIdentity(DF.Identity.EXTERNAL_ID, "{value}")
```

### 한번에 여러건 설정하기

```java
void setIdentities(Map<DF.Identity, String> values)
```

- 첫번째 인자인 `values`은 설정할 통합 ID 식별 정보를 의미합니다. 해당 값은 `java.util.Map<DF.Identity, String>`의 형태로 통합 ID 식별 정보의 종류와 값을 입력해야 합니다. `values`는 `null`이 입력될 수 없습니다.

<details open>
<summary>Java</summary>

```java
Map<Identity, String> identities = new HashMap<>();
identities.put(DF.Identity.EXTERNAL_ID, "{value}");
identities.put(DF.Identity.EMAIL,"{value}");
DfineryProperties.setIdentities(identities);
```

</details>
<details open>
<summary open >Kotlin</summary>

```kotlin
val identities = mapOf<Identity, String>(
    DF.Identity.EXTERNAL_ID to "{value}", 
    DF.Identity.EMAIL to "{value}"
)
DfineryProperties.setIdentities(identities)
```

</details>

## 통합 ID 식별 정보 초기화하기
`DfineryProperties.resetIdentity()` 메소드를 호출하면 기존 저장하고 있던 통합 ID 식별 정보를 제거하고 초기화할 수 있습니다.

> [!CAUTION]
> 이 메소드를 호출 할 경우 기존 사용자의 이벤트 흐름이 끊기므로 반영하고자 하는 이벤트를 먼저 호출 한 후에 호출하여 주시기 바랍니다.

```kotlin
void resetIdentity()
```
```kotlin
DfineryProperties.resetIdentity();
```

## 추가 정보
통합 ID 연동에 대한 더 자세한 내용이 필요할 경우 고급 사용 사례에 있는 [통합 ID 연동 시나리오](./identity_scenario.md)를 참고하세요.