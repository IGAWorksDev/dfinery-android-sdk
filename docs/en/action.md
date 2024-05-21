# 📺 Action

Actions are a way to virtually connect with your customers through push notifications, in-app messages, and more.

## Push Notification
Push notifications are a proven way to reach your customers via mobile or web.

To use push notifications, please set up the app according to the provided [guide](./push_notification.md) and then create a campaign in [Dfinery Console](https://console.dfinery.ai/).

![push_notification](https://help.dfinery.io/hc/article_attachments/22386154961305)

## In-app Message
Unlike push notifications, in-app messages operate while the app is on, allowing you to display content with less disruption to the user. To use it, please check the [Guide](./inappmessage.md) and then create a campaign on [Dfinery Console](https://console.dfinery.ai/).

![in_app_message](https://help.dfinery.io/hc/article_attachments/18405655671961)

## Connecting the Unified ID with device
In order for a user to receive an action, the user information set based on the Unifed ID must be linked to the device so that the user can be targeted.

### Connect
If you set Unifed ID information through [Set Unifed ID](./identity.md), the SDK automatically connects the integrated ID to the device.

### Disconnect
You can use the `suspendUserTargeting()` method to disconnect a device from a unified ID. If you set the unified ID again, a new connection will be established again.

```java
Dfinery.getInstance().suspendUserTargeting();
```

## Find out More
If you need more details about Unified ID linking and terminal connection, please refer to [Unified ID linking scenario](./identity_scenario.md) in Advanced Use Cases.