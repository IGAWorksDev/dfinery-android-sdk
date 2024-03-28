# 👤 유저 프로필 설정하기

유저 프로필은 Dfinery 서버에서 관리하고 있는 사용자에 대한 프로필 정보입니다. 해당 정보들은 모두 선택사항이며 단말기에 저장되지 않습니다.

## 설정하기

SDK의 `DfineryProperties.setUserProfile()` 메소드 혹은 `DfineryProperties.setUserProfiles()` 사용하여 유저 프로필을 설정할 수 있습니다.

### 유저 프로필 한 건 설정하기
설정해야 할 유저 프로필이 하나 일 때 사용 합니다.

```java
void setUserProfile(String key, String value)
```

- 첫번째 인자인 `key`은 설정할 유저 프로필의 이름을 의미합니다. `String` 타입의 값이 입력이 가능합니다.
-  두번째 인자인 `value`는 설정할 유저 프로필의 값을 의미합니다. `null`도 입력 가능하며 다음의 유형의 타입이 입력 가능합니다.
	-  `String`, `Boolean`, `Long`, `Double`, `java.util.Date`, `Array of String`, `Array of Long`, `Array of Double`

> [!WARNING]
> 가변 인자로 입력받는 `Array of String` 형식은 최대 10개, `Array of Long`, `Array of Double` 형식은 최대 5개까지 입력이 가능합니다.

```kotlin
DfineryProperties.setUserProfile("{key}", value)
```

### 유저 프로필 한번에 여러건 설정하기
설정해야 할 유저 프로필이 여러개 일 때 사용 합니다.

```java
void setUserProfiles(Map<String, Object> values)
```

- 첫번째 인자인 `values `은 설정할 유저 프로필을 의미합니다. 해당 값은 `java.util.Map<String,Object>` 형태로 이름과 값을 입력해야 합니다. 해당 값은 `null`을 입력 할 수 없습니다.

<details open>
<summary>Java</summary>

```java
Map<String, Object> values = new HashMap<>();
//PredefinedUserProfile
values.put(DF.UserProfile.NAME, "{value}");//String
values.put(DF.UserProfile.INFORMATIONAL_NOTIFICATION_FOR_PUSH_CHANNEL, true);//Boolean
//CustomUserProfile
values.put("custom1", 34000L);//Long
values.put("custom2", 42.195);//Double
values.put("custom3", new Date());//Date
values.put("custom4", new Long[]{20L,30L});//Array of Long
values.put("custom5", new Double[]{1.1,1.2});//Array of Double
values.put("custom6", new String[]{"Michael","Jackson"});//Array of String
DfineryProperties.setUserProfiles(values);
```

</details>
<details open>
<summary open >Kotlin</summary>

```kotlin
val values = mapOf<String, Any>(
    //PredefinedUserProfile
    DF.UserProfile.NAME to "{value}",
    DF.UserProfile.INFORMATIONAL_NOTIFICATION_FOR_PUSH_CHANNEL to true,
    //CustomUserProfile
    "custom1" to 34000L,//Long
    "custom" to 42.195,//Double
    "custom" to Date(),//Date
    "custom" to longArrayOf(20L, 30L),//Array of Long
    "custom" to doubleArrayOf(1.1, 1.2),//Array of Double
    "custom" to arrayOf<String>("Michael", "Jackson")//Array of String
)
DfineryProperties.setUserProfiles(values)
```

</details>


## 기 정의 된 유저 프로필 설정하기 예제
유저 프로필에는 기 정의된 키 값 들이 있으며, 키 값에 `DF.UserProfile`에 있는 상수를 입력하면 적용됩니다.

### 이름 설정하기 
- 지원 유형
    - key : `DF.UserProfile.NAME`
    - value : `String`

```kotlin
DfineryProperties.setUserProfile(DF.UserProfile.NAME, "{value}")
```
### 성별 설정하기
- 지원 유형
    - key : `DF.UserProfile.GENDER`
    - value : `DF.Gender.MALE`, `DF.Gender.FEMALE`, `DF.Gender.NON_BINARY`, `DF.Gender.OTHER` 

```kotlin
DfineryProperties.setUserProfile(DF.UserProfile.GENDER, DF.Gender.MALE)
```
### 회원 등급 설정하기
- 지원 유형
    - key : `DF.UserProfile.MEMBERSHIP`
    - value : `String`

```kotlin
DfineryProperties.setUserProfile(DF.UserProfile.MEMBERSHIP, "{value}"
```
### 생년월일 설정하기
- 지원 유형
    - key : `DF.UserProfile.BIRTH`
    - value : `java.util.Date`

<details open>
<summary>Java</summary>

```java
//1991.08.26를 Calendar를 사용해 입력했을 경우
Calendar birthday = Calendar.getInstance();
birthday.clear();
birthday.set(Calendar.YEAR, 1991);
birthday.set(Calendar.MONTH, Calendar.AUGUST);
birthday.set(Calendar.DATE, 26);
DfineryProperties.setUserProfile(DF.UserProfile.BIRTH, birthday.getTime());

//1991.08.26을 SimpleDateFormat을 사용해 입력했을 경우
SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
Date birthday = simpleDateFormat.parse("1991-08-26");
DfineryProperties.setUserProfile(DF.UserProfile.BIRTH, birthday);
```

</details>
<details open>
<summary open >Kotlin</summary>

```kotlin
//1991.08.26를 Calendar를 사용해 입력했을 경우
val birthday = Calendar.getInstance()
birthday.clear()
birthday[Calendar.YEAR] = 1991
birthday[Calendar.MONTH] = Calendar.AUGUST
birthday[Calendar.DATE] = 26
DfineryProperties.setUserProfile(DF.UserProfile.BIRTH, birthday.time);

//1991.08.26을 SimpleDateFormat을 사용해 입력했을 경우
val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
val birthday = simpleDateFormat.parse("1991-08-26")
DfineryProperties.setUserProfile(DF.UserProfile.BIRTH, birthday)
```

</details>

### 알림 수신 동의 정보 설정하기
알림 수신 동의 정보는 유저 프로필에 속해있으며 다양한 채널에 대한 수신 동의 정보 값을 설정할 수 있습니다.

> [!CAUTION]
> [오후 9시 부터 오전 8시 사이에는 별도의 수신 동의를 받아야 광고성 알림을 전송할 수 있습니다.](https://spam.kisa.or.kr/spam/na/ntt/selectNttInfo.do?mi=1037&nttSn=1351&bbsId=1003)

#### 알림 수신 동의 유형

|명칭|채널|설명|
|---|---|---|
|DF.UserProfile.INFORMATIONAL_NOTIFICATION_FOR_PUSH_CHANNEL|푸시|푸시 채널에 대한 정보성 알림 동의|
|DF.UserProfile.ADVERTISING_NOTIFICATION_FOR_PUSH_CHANNEL|푸시|푸시 채널에 대한 광고성 알림 동의|
|DF.UserProfile.ADVERTISING_NOTIFICATION_FOR_SMS_CHANNEL|문자|문자 채널에 대한 광고성 알림 동의|
|DF.UserProfile.ADVERTISING_NOTIFICATION_FOR_KAKAO_CHANNEL|알림톡|카카오 알림톡 채널에 대한 광고성 알림 동의|
|DF.UserProfile.ADVERTISING_NOTIFICATION_AT_NIGHT_FOR_PUSH_CHANNEL|푸시|푸시 채널에 대한 야간 광고성 알림 동의|

#### 설정하기
- 지원 유형
    - key : `알림 수신 동의 유형`
    - value : `Boolean`

```kotlin
DfineryProperties.setUserProfile(DF.UserProfile.INFORMATIONAL_NOTIFICATION_FOR_PUSH_CHANNEL, true);
```

## 사용자 정의 유저 프로필 설정하기 
 사용자 정의 유저 프로필을 설정하고 싶을 경우 콘솔에 해당하는 유저 프로필의 속성을 생성하고 키 값으로 입력하여 설정하면 됩니다.


