# Setting up using XML
## dfinery.xml
This is an XML file that contains SDK settings. Create and use the `dfinery.xml` file in the `src/main/res/values` folder of the application. Here is an example:

> [!IMPORTANT]
> Values ​​set by directly calling methods at runtime always take precedence over values ​​set in dfinery.xml.

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