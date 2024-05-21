# 🪪 Setting up Unifed ID

Dfinery issues a unified ID to identify you. Since the Unified ID combines multiple pieces of information to create one value, the uniqueness of the ID is further guaranteed when a lot of information is entered. All of this information is optional and is stored encrypted on the device.

## Setting
Unified ID can be set using the `DfineryProperties.setIdentity()` method. The Unified ID identification type is defined in `DF.Identity`, and only predefined values can be used.

> [!WARNING]
> In the case of `EXTERNAL_ID`, it is recommended to use a fixed value as it is used as an important value to distinguish users among Unified ID.

### Type of Unified ID Identification
|Name|Description|
|---|---|
|DF.Identity.EXTERNAL_ID|ID of user|
|DF.Identity.EMAIL|Email Address of user|
|DF.Identity.PHONE_NO|Phone Number of user|
|DF.Identity.KAKAO_USER_ID|User ID of user's kakao account|
|DF.Identity.LINE_USER_ID|User ID of user's [line](https://line.me/ko/) account|

### Setting for each item

```java
void setIdentity(Identity identity, String value)
```
    
- The first argument, `identity`, indicates the type of Unified ID identification to be set.
- The second argument, `value`, refers to the value of the identification information to be set. `null` can also be entered.

```kotlin
DfineryProperties.setIdentity(DF.Identity.EXTERNAL_ID, "{value}")
```

### Setting up multiple things at once

```java
void setIdentities(Map<DF.Identity, String> values)
```

- The first argument, `values`, refers to the Unified ID identification to be set.
- The value must be entered as the type and value of the Unified ID identification in the form of `java.util.Map<DF.Identity, String>`. `values` cannot be `null`.

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

## Reset Unified ID
By calling the `DfineryProperties.resetIdentity()` method, you can remove and initialize the previously stored Unified ID identifications.

> [!CAUTION]
> If you reset the Unified ID, the existing event flow will be interrupted and the connection between the terminal and the integrated ID will be disconnected, so please be careful when calling.
> 
> If you call it, please call the event first before calling the API.

```kotlin
void resetIdentity()
```
```kotlin
DfineryProperties.resetIdentity();
```

## Find out More
If you need more details about unified ID linking, please refer to [Unified ID linking scenario](./identity_scenario.md) in Advanced Use Cases.