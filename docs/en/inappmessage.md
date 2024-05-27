# 🏞️ In-app message

Unlike push notifications, in-app messages operate while the app is on, allowing you to display content with less disruption to the user. Dfinery's in-app messages operate based on events and are displayed automatically.

## Improved in-app message display performance
You can improve the performance of in-app message display by activating [Hardware acceleration](https://developer.android.com/topic/performance/hardware-accel).

### Application level
In your Android manifest file, add the following attribute to the <application> tag and enable hardware acceleration for the entire application.

```xml
<application android:hardwareAccelerated="true" ...>
```

### Activity level
You can also control it according to individual activities. To enable or disable hardware acceleration at the Activity level, you can use the `android:hardwareAccelerated` attribute on the <activity> element.

```xml
<application>
    <activity ... />
    <activity android:hardwareAccelerated="true" />
</application>
```

## Set In-app message custom parent view

The SDK automatically finds the top view of the activity being displayed and displays an in-app message.
If you do not want to use automatic set, you can use `setCustomInAppMessageParentView()` to set the parent view to display.

```kotlin
Dfinery.getInstance().setCustomInAppMessageParentView(parentView);
```