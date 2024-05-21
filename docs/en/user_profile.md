# 👤 Setting up a user profile

A user profile is profile information about a user managed by the Dfinery server. All of this information is optional and is not stored on the device.

## Setting

You can set user profiles using the SDK's `DfineryProperties.setUserProfile()` method or `DfineryProperties.setUserProfiles()`.

> [!NOTE]
> Before calling the user profile, you must create and register the user profile properties in [Dfinery Console](https://console.dfinery.ai/) to be set up properly on the server. If it is not registered, it will not be set even if you call it.

### Setting for each item

```java
void setUserProfile(String key, String value)
```

- The first argument, `key`, represents the name of the user profile to be set. Values of type `String` can be input.
- The second argument, `value`, refers to the value of the user profile to be set. `null` can also be input, and the following types can be input.
	-  `String`, `Boolean`, `Long`, `Double`, `java.util.Date`, `Array of String`, `Array of Long`, `Array of Double`

> [!WARNING]
> Up to 10 `Array of String` types and up to 5 `Array of Long` and `Array of Double` types can be input as variable arguments.

```kotlin
DfineryProperties.setUserProfile("{key}", value)
```

### Setting up multiple things at once

```java
void setUserProfiles(Map<String, Object> values)
```

- The first argument, `values`, refers to the user profile to be set. The name and value must be entered in the form `java.util.Map<String,Object>`. The value cannot be `null`.

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


## Example of setting up a pre-defined user profile
There are predefined key values in the user profile, and they are applied by entering the constant in `DF.UserProfile` as the key value.

### Setting the name
- Type
    - key : `DF.UserProfile.NAME`
    - value : `String`

```kotlin
DfineryProperties.setUserProfile(DF.UserProfile.NAME, "{value}")
```
### Setting the gender
- Type
    - key : `DF.UserProfile.GENDER`
    - value : `DF.Gender.MALE`, `DF.Gender.FEMALE`, `DF.Gender.NON_BINARY`, `DF.Gender.OTHER` 

```kotlin
DfineryProperties.setUserProfile(DF.UserProfile.GENDER, DF.Gender.MALE)
```
### Setting membership level
- 지원 유형
    - key : `DF.UserProfile.MEMBERSHIP`
    - value : `String`

```kotlin
DfineryProperties.setUserProfile(DF.UserProfile.MEMBERSHIP, "{value}"
```
### Setting your date of birth
- Type
    - key : `DF.UserProfile.BIRTH`
    - value : `java.util.Date`

<details open>
<summary>Java</summary>

```java
//If 1991.08.26 is entered using java.util.Calendar
Calendar birthday = Calendar.getInstance();
birthday.clear();
birthday.set(Calendar.YEAR, 1991);
birthday.set(Calendar.MONTH, Calendar.AUGUST);
birthday.set(Calendar.DATE, 26);
DfineryProperties.setUserProfile(DF.UserProfile.BIRTH, birthday.getTime());

//If 1991.08.26 is entered using java.text.SimpleDateFormat
SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
Date birthday = simpleDateFormat.parse("1991-08-26");
DfineryProperties.setUserProfile(DF.UserProfile.BIRTH, birthday);
```

</details>
<details open>
<summary open >Kotlin</summary>

```kotlin
//If 1991.08.26 is entered using java.util.Calendar
val birthday = Calendar.getInstance()
birthday.clear()
birthday[Calendar.YEAR] = 1991
birthday[Calendar.MONTH] = Calendar.AUGUST
birthday[Calendar.DATE] = 26
DfineryProperties.setUserProfile(DF.UserProfile.BIRTH, birthday.time);

//If 1991.08.26 is entered using java.text.SimpleDateFormat
val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
val birthday = simpleDateFormat.parse("1991-08-26")
DfineryProperties.setUserProfile(DF.UserProfile.BIRTH, birthday)
```

</details>

### Setting consent to receive notifications
Notification consent information belongs to the user profile, and you can set the consent information values for various channels.

> [!NOTE]
> Exceptionally, the user profile for notification consent information is already automatically registered when creating a service in Dfinery, so it can be set without having to set it in the Dfinery console.

#### Notification Consent Type

|Name|Channel Type|Description|
|---|---|---|
|DF.UserProfile.INFORMATIONAL_NOTIFICATION_FOR_PUSH_CHANNEL|Push|Consent to informational notifications for push channels|
|DF.UserProfile.ADVERTISING_NOTIFICATION_FOR_PUSH_CHANNEL|Push|Consent to advertising notifications for push channels|
|DF.UserProfile.ADVERTISING_NOTIFICATION_FOR_SMS_CHANNEL|SMS|Consent to advertising notifications for sms channels|
|DF.UserProfile.ADVERTISING_NOTIFICATION_FOR_KAKAO_CHANNEL|[Alim Talk](https://docs.kakaoi.ai/kakao_i_connect_message/bizmessage_eng/agent/at/)|Consent to advertising notifications for Kakao Alim Talk channel|
|DF.UserProfile.ADVERTISING_NOTIFICATION_AT_NIGHT_FOR_PUSH_CHANNEL|PUSH|Consent to advertising notifications at night for push channels|

> [!CAUTION]
> In Korea, advertising notifications can be sent only with [separate consent](https://spam.kisa.or.kr/spam/na/ntt/selectNttInfo.do?mi=1037&nttSn=1351&bbsId=1003) between 9 PM and 8 AM, so if you want to send messages during that time, please use the `ADVERTISING_NOTIFICATION_AT_NIGHT_FOR_PUSH_CHANNEL` value to obtain consent.

#### Setting
- Type
    - key : [Notification Consent Type](#notification-consent-type)
    - value : `Boolean`

```kotlin
DfineryProperties.setUserProfile(DF.UserProfile.INFORMATIONAL_NOTIFICATION_FOR_PUSH_CHANNEL, true);
```

## Setting up a custom user profile
 If you want to set up a custom user profile, you can create the properties of the corresponding user profile in [Dfinery Console](https://console.dfinery.ai/) and enter them as key values to set them.
