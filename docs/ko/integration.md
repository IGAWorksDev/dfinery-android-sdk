# 👋 빠른 시작

이 문서는 Android SDK를 Android에 통합하는 방법을 다룹니다. Dfinery SDK를 설치하면 이벤트 분석 기능과 유저 프로필 기능이 제공됩니다.

## SDK 연동하기

### 의존성 추가하기
앱에서 Dfinery SDK의 의존성을 적용하려면 다음 단계를 완료하세요.
 
#### 1. maven 의존성을 가져오기 위해 repositories 내에 `mavenCentral`을 추가합니다. 

<details open>
  <summary>build.gradle (Gradle 3.5 이전)</summary>

```
allprojects {
    repositories {
        mavenCentral()
    }
}
```
</details>
<details open>
  <summary>settings.gradle (Gradle 3.5 이후)</summary>

```
pluginManagement {
    repositories {
        mavenCentral()
    }
}
dependencyResolutionManagement {
    repositories {
        mavenCentral()
    }
}
```

</details>

#### 2. 앱의 모듈 디렉터리 내에 있는 `build.gradle` 파일을 엽니다.
#### 3. dependencies에 Dfinery와 필요한 요소에 대해 SDK 종속 항목을 추가합니다.

> [!NOTE]
> Dfinery는 AppSetId를 수집하기 위해 [play-services-appset](https://developer.android.com/training/articles/app-set-id) 의존성을 필요로 합니다. 

```
 dependencies {
  implementation 'com.igaworks.dfinery:android-sdk:+'
  implementation 'com.google.android.gms:play-services-appset:16.0.2'
}
```

> [!TIP]
> Dfinery의 최신 버전을 확인하려면 [Dfinery Android SDK Release](https://github.com/IGAWorksDev/dfinery-android-sdk/releases)를 방문하세요.

### 초기화하기
앱에서 Dfinery SDK를 초기화하려면 다음 단계를 완료하세요.

#### 1. Application을 상속한 객체를 생성합니다. 상속한 객체가 이미 있을 경우 해당 객체를 사용합니다.

<details open>
  <summary>Java</summary>

```java
public class BaseApplication extends Application
```

</details>

<details open>
  <summary>Kotlin</summary>

```kotlin
class BaseApplication: Application()
```

</details>

#### 2. Application을 상속한 객체에서 onCreate() 메소드를 Override 합니다.

<details open>
  <summary>Java</summary>

```java
@Override
public void onCreate() {
    super.onCreate();
}
```

</details>

<details open>
  <summary>Kotlin</summary>

```kotlin
override fun onCreate() {
    super.onCreate()
}
```

</details>

#### 3. onCreate() 메소드 내에 다음 코드를 작성합니다.

> Service ID는 [Console](https://dev-cdp.dfinery.io/)의 `서비스 관리/서비스 정보/Key 정보/서비스키` 경로에서 확인이 가능합니다. 추가적인 정보를 원한다면 이 [섹션](#서비스-id-가져오기)을 참고하세요 

```java
Dfinery.getInstance().init(this, "{your_service_id}")
```
다음 코드 스니펫은 초기화 작성 완료시의 예를 보여줍니다.

<details open>
  <summary>Java</summary>


```java
public class BaseApplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        Dfinery.getInstance().init(this, "{your_service_id}");
    }
}
```

</details>

<details open>
  <summary>Kotlin</summary>

```kotlin
class BaseApplication: Application(){
    override fun onCreate() {
        super.onCreate()
        Dfinery.getInstance().init(this, "{your_service_id}")
    }
}
```
</details>

#### 4. AndroidManifest.xml에 작성한 Application을 등록합니다.

```xml
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android">
    <application
        android:name=".BaseApplication">
    </application>
</manifest>
```

#### 5. AndroidManifest.xml에 필요한 권한을 추가합니다.
```xml
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
```

### 구글 광고 ID 설정하기(선택사항)
Google 광고 ID를 수집하기 위해선 `setGoogleAdvertisingId()` 메소드를 통해 수동으로 설정해야합니다.

#### 1. 앱의 모듈 디렉터리 내에 있는 `build.gradle` 파일을 엽니다.
#### 2. dependencies에 [광고 ID를 수집하기 위해 필요한 종속 항목](https://developers.google.com/android/guides/setup)을 추가합니다.

```java
 dependencies {
  implementation 'com.google.android.gms:play-services-ads-identifier:18.0.1'
}
```

#### 3. AndroidManifest.xml에 필요한 권한을 추가합니다.
```xml
<uses-permission android:name="com.google.android.gms.permission.AD_ID" />
```

#### 4. `setGoogleAdvertisingId()` 메소드를 통해 광고 ID를 설정합니다.

> [!TIP]
> 광고 ID 수집 로직은 별도의 스레드에서 동작해야합니다. 아래 코드 스니펫은 Thread를 생성하여 동작했지만 필요에 따라 다른 방법(AsyncTask, 코루틴 등)을 사용하여도 됩니다.


<details open>
  <summary>Java</summary>


```java
new Thread(new Runnable() {
    @Override
    public void run() {
        try {
            AdvertisingIdClient.Info idInfo = AdvertisingIdClient.getAdvertisingIdInfo(getApplicationContext());
            DfineryProperties.setGoogleAdvertisingId(idInfo.getId(), idInfo.isLimitAdTrackingEnabled());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}).start();
```

</details>

<details open>
  <summary>Kotlin</summary>

```kotlin
Thread {
    try {
        val idInfo = AdvertisingIdClient.getAdvertisingIdInfo(
            applicationContext
        )
        DfineryProperties.setGoogleAdvertisingId(idInfo.id, idInfo.isLimitAdTrackingEnabled)
    } catch (e: Exception) {
        e.printStackTrace()
    }
}.start()
```
</details>

### 완료
이제 Dfinery의 초기화가 완료되었습니다. 

## 더 알아보기

### 서비스 ID 가져오기

#### 1. [Dfinery 콘솔](https://dev-cdp.dfinery.io/login)에 접속합니다.
#### 2. 이메일과 비밀번호를 입력하여 로그인 합니다. 
![login](../../assets/integration_1.png)
#### 3. 좌측 상단에 네모난 서비스 아이콘을 클릭하여 화면을 활성화 합니다.
![click_service_icon](../../assets/integration_3.png)
#### 4. 관리 버튼을 클릭하여 서비스 정보 페이지에 진입합니다.
![click_service_config](../../assets/integration_2.png)
#### 4. 하단에 있는 Key 정보에서 서비스키를 가져옵니다.
![copy_service_id](../../assets/integration_4.png)