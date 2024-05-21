# 📺 액션

액션은 푸시 알림, 인앱 메시지 등을 통해 고객과 가상으로 소통할 수 있는 방법입니다. 

## 푸시 알림
푸시 알림은 모바일이나 웹을 통해 고객에게 다가갈 수 있는 검증된 방법입니다. 

푸시 알림을 사용하기 위해서 제공되는 [가이드](./push_notification.md)에 따라 앱을 설정한 후 [Dfinery Console](https://console.dfinery.ai/)에서 캠페인을 생성하여 주시기 바랍니다.

![push_notification](https://help.dfinery.io/hc/article_attachments/22386154961305)

## 인앱 메시지
인앱 메시지는 푸시 알림과 달리 앱이 켜진 상태에서 동작하므로 사용자를 덜 방해를 하면서 콘텐츠를 보여줄 수 있습니다. 사용하기 위해 [가이드](./inappmessage.md)를 확인한 후에 [Dfinery Console](https://console.dfinery.ai/)에서 캠페인을 생성하여 주시기 바랍니다.

![in_app_message](https://help.dfinery.io/hc/article_attachments/18405655671961)

## 통합 ID와 단말기를 연결하기
사용자가 액션을 받기 위해서는 통합 ID 기반으로 설정된 유저 정보를 단말기에 연결하여 해당 유저가 타겟팅이 되도록 해야합니다.

### 연결하기
[통합 ID 설정하기](./identity.md)를 통해 통합 ID 정보를 설정하면 SDK가 자동으로 통합 ID를 단말기에 연결합니다.

### 연결 해제하기
`suspendUserTargeting()` 메소드를 사용하면 통합 ID와 단말기의 연결을 해제할 수 있습니다. 다시 통합 ID를 설정할 경우에는 다시 새로 연결이 구축됩니다.

```java
Dfinery.getInstance().suspendUserTargeting();
```

## 더 알아보기
통합 ID 연동과 단말기 연결에 대한 더 자세한 내용이 필요할 경우 고급 사용 사례에 있는 [통합 ID 연동 시나리오](./identity_scenario.md)를 참고하세요.