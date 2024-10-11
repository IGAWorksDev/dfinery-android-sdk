# Sample

To run a sample app, replace the placeholder value for `service_id` in the `src/main/java/com/igaworks/dfinerysample/BaseApplication.java` file of the sample app you wish to run with your Service ID.

```java
@Override
public void onCreate() {
    Dfinery.getInstance().init(this, "{service_id}", config); //replace this value with your service ID. 
}
```

## Push Notification

To test push, uncomment com.google.gms.google-services at the top of your app-level build.gradle file, register this application with your Firebase project, [add the Google configuration file](https://firebase.google.com/docs/android/setup).

```groovy
plugins {
    id 'com.android.application'
    //id 'com.google.gms.google-services' <- Please uncomment it.
}
```