# 🔎 Additional settings

This document introduces additional settings and features provided by Dfinery.

## Debugging

### Activate logging
Log activation can be configured using DfineryConfig, which applies settings from Dfinery. DfineryConfig can be applied by entering it as a parameter when calling the `init()` method. it can be set using `res/values/dfinery.xml`.

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
<details open>
  <summary>dfinery.xml</summary>

```xml
<?xml version="1.0" encoding="utf-8"?>
<resources>
    <bool name="com_igaworks_dfinery_log_enable" translatable="false">true</bool>
</resources>
```

</details>

### Enabling verbose logging
Log levels can be set using DfineryConfig, which applies Dfinery's settings. DfineryConfig can be applied by entering it as a parameter when calling the `init()` method. it can be set using `res/values/dfinery.xml`.

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
<details open>
  <summary>dfinery.xml</summary>

```xml
<?xml version="1.0" encoding="utf-8"?>
<resources>
    <integer name="com_igaworks_dfinery_log_level">2</integer>
</resources>
```

</details>