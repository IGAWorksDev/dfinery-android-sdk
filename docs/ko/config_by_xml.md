# XMl을 사용하여 설정하기
## dfinery.xml
SDK의 설정 값을 기입하는 XML 파일입니다. 어플리케이션의 `src/main/res/values` 폴더 내에 `dfinery.xml` 파일을 생성하여 사용합니다. 다음은 일반적인 사용의 예시입니다.

> [!IMPORTANT]
> dfinery.xml에서 설정한 값보다 항상 런타임에서 메소드를 직접 호출하여 설정한 값이 우선됩니다.

```xml
<?xml version="1.0" encoding="utf-8"?>
<resources>
    <string name="com_igaworks_dfinery_service_id" translatable="false">{service_id}</string>
    <string name="com_igaworks_dfinery_default_notification_channel_id" translatable="false" >dfinery</string>
    <drawable name="com_igaworks_dfinery_notification_icon" >@drawable/ic_dfinery</drawable>
    <bool name="com_igaworks_dfinery_log_enable" translatable="false">true</bool>
    <integer name="com_igaworks_dfinery_log_level">2</integer>
</resources>
```