# 🔎 Additional settings

This document introduces additional settings and features provided by Dfinery.

## Debugging

### Activate logging
Log activation can be configured using DfineryConfig, which applies settings from Dfinery. DfineryConfig can be applied by entering it as a parameter when calling the `init()` method.

```java
setLogEnable(boolean enable)
```

- The first argument, `enable`, is a value that sets whether to display the log or not. The default is `false`.

> [!TIP]
> You can set the value to `BuildConfig.DEBUG` so that logs are output only in debug mode and not after deployment.

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

### Enabling verbose logging
Log levels can be set using DfineryConfig, which applies Dfinery's settings. DfineryConfig can be applied by entering it as a parameter when calling the `init()` method.

```java
setLogLevel(int logLevel)
```

- The first argument, `logLevel `, is a value that sets the log display level. The default is `Log.ERROR(6)`.

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

## Get SDK properties
### Get SDK version
Gets the current version of the SDK.

```java
String getSdkVersion();
```

- Return Type : `String`

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

### Get Service ID
Get the service ID set in the SDK.

```java
String getServiceId();
```

- Return Type : `String`

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