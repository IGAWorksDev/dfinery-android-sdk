# 📺 액션

액션은 푸시 알림, 인앱 메시지 등을 통해 고객과 가상으로 소통할 수 있는 방법입니다. 

> [!CAUTION]
> 액션에 대한 연동은 추후 오픈 예정입니다!

## 통합 ID와 단말기를 연결하기
사용자가 액션을 받기 위해서는 통합 ID 기반으로 설정된 유저 정보와 단말기의 연결하여 해당 유저가 타겟팅이 되도록 해야합니다.

### 연결하기
[통합 ID 설정하기](./identity.md)를 통해 통합 ID 정보를 설정하면 SDK가 자동으로 통합 ID와 단말기를 연결합니다.

### 연결 해제하기
`Dfinery.getInstance().suspendUserTargeting()` 메소드를 사용하면 통합 ID와 단말기의 연결을 해제할 수 있습니다. 다시 통합 ID를 설정할 경우에는 다시 새로 연결이 구축됩니다.

```java
void suspendUserTargeting()
```
```java
Dfinery.getInstance().suspendUserTargeting();
```

## 추가 정보
통합 ID 연동과 단말기 연결에 대한 더 자세한 내용이 필요할 경우 고급 사용 사례에 있는 [통합 ID 연동 시나리오](./identity_scenario.md)를 참고하세요.