# 🏞️ 인앱메시지

인앱 메시지는 푸시 알림과 달리 앱이 켜진 상태에서 동작하므로 사용자를 덜 방해를 하면서 콘텐츠를 보여줄 수 있습니다. Dfinery의 인앱메시지는 이벤트 기반으로 동작하며 자동으로 표시됩니다.

## 인앱메시지 표시 성능 향상
[하드웨어 가속 옵션](https://developer.android.com/topic/performance/hardware-accel?hl=ko)을 활성화 하여 인앱메시지 표시에 대한 성능을 향상 시킬 수 있습니다.

### Application 수준
Android 매니페스트 파일에서 다음 속성을 <application> 태그에 추가하고 전체 애플리케이션에 하드웨어 가속을 사용 설정합니다.

```xml
<application android:hardwareAccelerated="true" ...>
```

### Activity 수준
개별 Activity에 맞게 제어할 수도 있습니다. Activity 수준에서 하드웨어 가속을 사용 또는 중지하려면 <activity> 요소의 `android:hardwareAccelerated` 속성을 사용할 수 있습니다. 

```xml
<application>
    <activity ... />
    <activity android:hardwareAccelerated="true" />
</application>
```

## 인앱메시지 사용자 지정 부모 뷰 설정
SDK는 자동으로 표시되고 있는 Activity의 최상위 뷰를 찾아 인앱 메시지를 표시합니다. 
만약 자동 설정을 사용하지 않으실 경우 `setCustomInAppMessageParentView()`를 사용하여 직접 표시할 부모 뷰를 설정 할 수있습니다.

```kotlin
Dfinery.getInstance().setCustomInAppMessageParentView(parentView);
```