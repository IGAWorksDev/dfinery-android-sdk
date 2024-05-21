# 🔔 Push Notification

Dfinery uses Firebase Cloud Messaging to receive pushes, so your application requires Firebase integration.

## Add Firebase to your Android project
Add Firebase to your Android project by following the instructions in [document](https://firebase.google.com/docs/android/setup#next_steps) provided by Firebase.

## Register Firebase information in Dfinery Console
In the console, go to [Additional Settings/Channel Additional Settings/Push/Android Settings Management](https://console.dfinery.ai), enter the sender ID, and enter Firebase JSON format. Upload your credential private key file.

### How to get a Firebase credentials private key file

Refer to [Provide credentials manually](https://firebase.google.com/docs/cloud-messaging/auth-server#provide-credentials-manually) provided by Firebase and save the key in JSON format, then save the file in the console. Please upload.

Here are the main things you can find on that page:
 > 1. In the Firebase console, open Settings > [Service Accounts](https://console.firebase.google.com/project/_/settings/serviceaccounts/adminsdk).
 > 2. Click Generate New Private Key, then confirm by clicking Generate Key.
 > 3. Securely store the JSON file containing the key.

 If you are having difficulty issuing a private key file, please refer to [Follow along with issuing Firebase user credentials private key file](#follow-along-with-issuing-firebase-user-credentials-private-key-file).

## Set up a Firebase Cloud Messaging client app 
Apply the following items according to the instructions in the [document](https://firebase.google.com/docs/cloud-messaging/android/client) provided by Firebase.
- [Edit your app manifest](https://firebase.google.com/docs/cloud-messaging/android/client#manifest)
- [Request runtime notification permission on Android 13+](https://firebase.google.com/docs/cloud-messaging/android/client#request-permission13)
- [Access the device registration token](https://firebase.google.com/docs/cloud-messaging/android/client#sample-register)

## Adding Support Library dependencies
Dfinery requires the [Support Library](https://developer.android.com/jetpack/androidx/releases/core) to create push notifications, so complete the following series of steps:
### 1. Open the `build.gradle` file within your app's module directory.
### 2. Add Support Library dependency to dependencies.

```
 dependencies {
    implementation 'androidx.core:core:1.9.0'
}
```

## Setting the registration token in Dfinery
To set the token issued by Firebase to Dfinery, please write the following.
### Settings when getting current registration token

> [!TIP]
> Since the token does not change frequently, it is recommended to write it in the [onCreate()](https://developer.android.com/reference/android/app/Application#onCreate()) method of the object that inherits Application so that the code is not called frequently.

<details open>
  <summary>Java</summary>

```java
FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
    @Override
    public void onComplete(@NonNull Task<String> task) {
        if (!task.isSuccessful()) {
            Log.w(TAG, "Fetching FCM registration token failed", task.getException());
            return;
        }

        // Get new FCM registration token
        String token = task.getResult();
        DfineryProperties.setPushToken(token);
    }
});
```

</details>

<details open>
  <summary>Kotlin</summary>

```kotlin
FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
    if (!task.isSuccessful) {
        Log.w(TAG, "Fetching FCM registration token failed", task.exception)
        return@OnCompleteListener
    }

    // Get new FCM registration token
    val token = task.result
    DfineryProperties.setPushToken(token)
})
```

</details>

### Settings when monitoring token generation
Override the [onNewToken(String)](https://firebase.google.com/docs/reference/android/com/google/firebase/messaging/FirebaseMessagingService#onNewToken(java.lang.String)) method in the class that inherits FirebaseMessagingService and set the token in Dfinery.

<details open>
  <summary>Java</summary>

```java
@Override
public void onNewToken(@NonNull String token) {
    Log.d(TAG, "Refreshed token: " + token);
    DfineryProperties.setPushToken(token);
}
```

</details>

<details open>
  <summary>Kotlin</summary>

```kotlin
override fun onNewToken(token: String) {
    Log.d(TAG, "Refreshed token: $token")
    DfineryProperties.setPushToken(token)
}
```

</details>

## Setting up a notification channel in Dfinery
### Create a notification channel
Starting with Android 8.0, you need to create a notification channel to receive notifications. Please create a notification channel by referring to the instructions in [Documentation](https://developer.android.com/training/notify-user/channels) provided by Android and [Follow along on creating a notification channel](#follow-along-on-creating-a-notification-channel) .

> [!WARNING]
> Once created, the setting information for the notification channel is not changed through code, except for information such as name and description. Also, please note that the settings information for notification channels can be changed by the user.

### Register the created notification channel ID
Use DfineryConfig's `setDefaultNotificationChannelId()` method to register the ID of the notification channel you created.

<details open>
  <summary>Java</summary>

```java
DfineryConfig config = new DfineryConfig.Builder()
    .setDefaultNotificationChannelId("{your_notification_channel_id}")
    .build();
Dfinery.getInstance().init(this, "{your_application_key}", config);
```

</details>

<details open>
  <summary>Kotlin</summary>

```kotlin
val config = DfineryConfig.Builder()
    .setDefaultNotificationChannelId("{your_notification_channel_id}")
    .build()
Dfinery.getInstance().init(this, "{your_application_key}", config)
```

</details>

## Setting the push notification icon
Icon settings are required to display push notifications. Please set the icon using DfineryConfig's `setNotificationIconResourceId()` method.

> [!TIP]
> Since this is an icon that is displayed not only in the notification itself but also in the top status bar, the color of the image is ignored, so an image with a transparent color (alpha channel) of 72x72px is recommended.

<details open>
  <summary>Java</summary>

```java
DfineryConfig config = new DfineryConfig.Builder()
    .setNotificationIconResourceId(R.drawable.icon)
    .build();
Dfinery.getInstance().init(this, "{your_application_key}", config);
```

</details>

<details open>
  <summary>Kotlin</summary>

```kotlin
val config = DfineryConfig.Builder()
    .setNotificationIconResourceId(R.drawable.icon)
    .build()
Dfinery.getInstance().init(this, "{your_application_key}", config)
```

</details>

## Handle receiving push notifications
When a push is received, a push received event is raised in an object that inherits FirebaseMessagingService. Since Dfinery creates a notification based on the received push payload, please write the following within the [onMessageReceived(RemoteMessage)](https://firebase.google.com/docs/reference/android/com/google/firebase/messaging/FirebaseMessagingService#onMessageReceived(com.google.firebase.messaging.RemoteMessage)) method of the object.

<details open>
  <summary>Java</summary>

```java
@Override
public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
    super.onMessageReceived(remoteMessage);
    if(Dfinery.getInstance().handleRemoteMessage(getApplicationContext(), remoteMessage)){
        //dfinery push
    }else{
        //This is not a push notification sent from Dfinery.
    }
}
```

</details>

<details open>
  <summary>Kotlin</summary>

```kotlin
override fun onMessageReceived(remoteMessage: RemoteMessage) {
  if(Dfinery.getInstance().handleRemoteMessage(applicationContext, remoteMessage)){
        //dfinery push
    }else{
        //This is not a push notification sent from Dfinery.
    }
}
```

</details>

## Handling push notification clicks
When you click Push, the activity set in the deep link is executed, or if there is no deep link, the activity with the `android.intent.action.MAIN` action is executed.

The clicked push data is passed to the `onCreate(Bundle)` method of the corresponding Activity.
If you want to use the clicked data, you can use `getDfineryPushNotification()` here to get the [PushNotification](#pushpayload) object containing the push data.

> [!WARNING]
> If data parsing fails or there is no push data in the Intent, null is returned, so a null check is required.

<details open>
  <summary>Java</summary>

```java
@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    PushNotification pushNotification = Dfinery.getInstance().getDfineryPushNotification(getIntent());
    if(pushNotification != null){
        String title = pushNotification.getTitle();
    }
}
```

</details>

<details open>
  <summary>Kotlin</summary>

```kotlin
override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val pushNotification = Dfinery.getInstance().getDfineryPushNotification(intent)
    pushNotification?.let { 
        it.title
    }
}
```
</details>

## Setting whether to allow receipt of push notifications
In the case of pushes with advertising purposes in Korea, the [Information and Communications Network Act](https://www.law.go.kr/법령/정보통신망%20이용촉진%20및%20정보보호%20등에%20관한%20법률/제50조#:~:text=제50조) requires prior consent from the user. To set Dfinery with information that a user has been allowed to receive push notifications, please perform the following steps:

### 1. Notify users about allowing push notifications
### 2. Write the following code to set the value for the user’s allow/deny intent.

> [Setting consent to receive notifications](./user_profile.md#setting-consent-to-receive-notifications), and enter the values for the items you agreed to. The code below is an example of allowing consent to receive informational and advertising information for a push channel.

<details open>
<summary>Java</summary>

```java
Map<String, Object> consents = new HashMap<>();
consents.put(DF.UserProfile.INFORMATIONAL_NOTIFICATION_FOR_PUSH_CHANNEL, true);
consents.put(DF.UserProfile.ADVERTISING_NOTIFICATION_FOR_PUSH_CHANNEL, true);
DfineryProperties.setUserProfiles(consents);
```

</details>
<details open>
<summary open >Kotlin</summary>

```kotlin
val consents = mapOf<String, Any>(
    DF.UserProfile.INFORMATIONAL_NOTIFICATION_FOR_PUSH_CHANNEL to true, 
    DF.UserProfile.ADVERTISING_NOTIFICATION_FOR_PUSH_CHANNEL to true
)
DfineryProperties.setUserProfiles(consents)
```

## Complete
You are now ready to start using push notifications in Dfinery.

## Find out More
### Follow along on creating a notification channel
This is an example of creating a notification channel. Please refer to the [Example provided by Android](https://developer.android.com/develop/ui/views/notifications/channels#CreateChannel).

#### 1. Create a notification channel.

> Notification channel API is supported in [Android 8.0](https://developer.android.com/about/versions/oreo) or higher.

- The first parameter id refers to the ID of the notification channel.
- The second parameter name refers to the name of the notification channel. The value is exposed to the user in `Settings/Notifications`.
- The third parameter importance refers to the [importance](https://developer.android.com/develop/ui/views/notifications/channels#importance) of the push notifications to be received through this notification channel.

<details open>
  <summary>Java</summary>

```java
NotificationChannel notificationChannel = new NotificationChannel("{id}", "{name}", NotificationManager.IMPORTANCE_HIGH);
```

</details>

<details open>
  <summary>Kotlin</summary>

```kotlin
val notificationChannel = NotificationChannel("{id}", "{name}", NotificationManager.IMPORTANCE_HIGH)
```

</details>

#### 2. Set the description of the notification channel. (Optional)
The value is exposed to the user in `Settings/Notifications`.

<details open>
  <summary>Java</summary>

```java
notificationChannel.setDescription("{description}");
```

</details>

<details open>
  <summary>Kotlin</summary>

```kotlin
notificationChannel.description = "{description}"
```

</details>

#### 3. Set whether or not to vibrate the notification channel. (Optional)

> The value of the notification channel will always take precedence over all settings set in the Dfinery Console except 'Expose push messages while app is running'.

<details open>
  <summary>Java</summary>

```java
notificationChannel.enableVibration(true);
```

</details>

<details open>
  <summary>Kotlin</summary>

```kotlin
notificationChannel.enableVibration(true)
```

</details>

#### 4. Set the notification sound for the notification channel. (Optional)
This is an example of setting the system default notification sound to play.

<details open>
  <summary>Java</summary>

```java
Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
AudioAttributes audioAttributes = new AudioAttributes.Builder()
        .setUsage(AudioAttributes.USAGE_NOTIFICATION)
        .build();
notificationChannel.setSound(soundUri, audioAttributes);
```

</details>

<details open>
  <summary>Kotlin</summary>

```kotlin
val soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
val audioAttributes = AudioAttributes.Builder()
    .setUsage(AudioAttributes.USAGE_NOTIFICATION)
    .build()
notificationChannel.setSound(soundUri, audioAttributes)
```

</details>

#### 5. Register the notification channel you created in NotificationManager.
<details open>
  <summary>Java</summary>

```java
NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
notificationManager.createNotificationChannel(notificationChannel);
```

</details>

<details open>
  <summary>Kotlin</summary>

```kotlin
val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
notificationManager.createNotificationChannel(notificationChannel)
```

</details>

#### 6. Complete.

### PushPayload
PushPayload is an object containing push data. Provides methods to retrieve key-value constants and values from payloads received through Dfinery.

#### getNotificationId()
- Return type : Integer
- Description : Returns the notification ID value.

#### getTitle()
- Return type : String
- Description : Returns the title of the notification.

#### getBody()
- Return type : String
- Description : Returns the body of the notification.

#### getImageUrl()
- Return type : String
- Description : Returns the url of the notification image.

#### getCampaignId()
- Return type : String
- Description : Returns the campaign ID of the notification.

#### getDeeplink()
- Return type : String
- Description : Get the deep link value when a notification is clicked.

#### getPushClickAction()
- Return type : `com.igaworks.dfinery.models.PushClickAction`
- Description : Get data when a notification is clicked.

#### useRing()
- Return type : Boolean
- Description : Get the check value for “Use sound” set in the message additional settings of [Dfinery Console](https://console.dfinery.ai/). For devices with Android 8.0 and higher, this value is overridden by the notification channel setting when creating a notification.

#### useVibrate()
- Return type : Boolean
- Description : Get the value of whether or not “Use Vibration” is checked in the message additional settings of [Dfinery Console](https://console.dfinery.ai/). For devices with Android 8.0 and higher, this value is overridden by the notification channel setting when creating a notification.

#### isNotifyWhenApplicationIsForeground()
- Return type : Boolean
- Description : Get the check status value for “Push message exposure while app running” set in the message additional settings of [Dfinery Console](https://console.dfinery.ai/).

### Using PushPayload without SDK handling
Using the `getDfineryPushNotification()` API, you can parse the Extra or RemoteMessage included in the Intent without any SDK handling.

<details open>
  <summary>Java</summary>

```java
@Override
public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
    super.onMessageReceived(remoteMessage);
    PushNotification pushNotification = Dfinery.getInstance().getDfineryPushNotification(remoteMessage);
}
```

</details>

<details open>
  <summary>Kotlin</summary>

```kotlin
override fun onMessageReceived(remoteMessage: RemoteMessage) {
  val pushNotification = Dfinery.getInstance().getDfineryPushNotification(remoteMessage);
}
```
</details>

### Follow along with issuing Firebase user credentials private key file

#### 1. Access the [Service Account](https://console.cloud.google.com/iam-admin/serviceaccounts).
#### 2. Select the project for the key you want to issue.

> If there is no project, it means that the Firebase project has not been created, so refer to the [Add Firebase to your Android project](https://firebase.google.com/docs/android/setup#next_steps) document to create the Firebase project. 

![select a recent project](../../assets/certificate_1.png)
#### 3. Click ⋮ in Actions at the bottom right.
![click a action](../../assets/certificate_2.png)
#### 4. Click Manage keys from the dropdown.
![click a manage keys](../../assets/certificate_3.png)
#### 5. Select Add Key.

> If there is a key that has already been created, it means that there is a history of creating a key in the past, and you can use that key. If you cannot find the key, please create a new key.

![click a add key](../../assets/certificate_4.png)
#### 6. Click Create New Key from the dropdown.
![click a create new key](../../assets/certificate_5.png)
#### 7. Select JSON and click Create.
![create a key](../../assets/certificate_6.png)
#### 8. Complete