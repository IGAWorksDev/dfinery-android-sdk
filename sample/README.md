# Sample

To run a sample app, replace the placeholder value for `service_id` in the `src/main/java/com/igaworks/dfinerysample/BaseApplication.java` file of the sample app you wish to run with your Service ID.

```java
@Override
public void onCreate() {
    ...
    Dfinery.getInstance().init(this, "{service_id}", config); //replace this value with your service ID. 
}
```