# Dfinery WebView 연동
앱의 WebView에서 Dfinery 기능을 사용하려면 SDK에서 제공되는 `dfinery-html-bridge.js`와 `DfineryBridge`를 WebView와 HTML에 추가하면 됩니다. 가이드에 따라 항목들이 추가되면 Dfinery에 사용할 수 있는 동일한 API를 WebView 내에서 사용할 수 있습니다.

### dfinery-html-bridge.js
HTML에서 Dfinery를 사용하기 용이하게 하기 위한 javascript가 구현되어 있는 js 파일입니다.

### DfineryBridge
WebView 혹은 Unity, ReactNative등의 플러그인에서 공통적으로 사용되는 클래스입니다. WebView의 [`addJavascriptInterface()`](https://developer.android.com/reference/android/webkit/WebView#addJavascriptInterface(java.lang.Object,%20java.lang.String))를 사용하여 등록할 수 있습니다.

### Dfinery WebView 연동 따라해보기

> [sample](../../sample/) 프로젝트를 참고하여 연동이 된 사례를 보실 수 있습니다.

#### 1. WebView에 `com.igaworks.dfinery.DfineryBridge`를 추가합니다.

<details open>
  <summary>Java</summary>

```java
myWebview.getSettings().setJavaScriptEnabled(true);
myWebView.addJavascriptInterface(new DfineryBridge(), DfineryBridge.JAVASCRIPT_INTERFACE_NAME);
```

</details>

<details open>
  <summary>Kotlin</summary>

```kotlin
webView.settings.apply {
  this.javaScriptEnabled = true
}
myWebView.addJavascriptInterface(DfineryBridge(), DfineryBridge.JAVASCRIPT_INTERFACE_NAME)
```

</details>

#### 2. `dfinery-html-bridge.js`를 [다운로드](../../assets/dfinery-html-bridge.js)합니다. 
#### 3. `dfinery-html-bridge.js` 파일을 `src/main/assets` 경로에 추가합니다.
#### 4. Dfinery를 사용하려는 HTML 문서에 `dfinery-html-bridge.js` 스크립트를 추가합니다.

```html
<script src="./dfinery-html-bridge.js"></script>
```

#### 5. HTML 문서 내에서 `dfineryBridge` 객체를 사용하여 Dfinery를 호출합니다.

> 아래는 적용 예시입니다.

```javascript
function Login() {
    dfineryBridge.logEvent(LOGIN);
    console.log("Login 함수가 호출되었습니다.");
}
function Purchase() {
    const item = {
        [KEY_STRING_ITEM_ID]: "상품번호",
        [KEY_STRING_ITEM_NAME]: "상품이름",
        [KEY_STRING_CATEGORY1]: "식품",
        [KEY_STRING_CATEGORY2]: "과자",
        [KEY_DOUBLE_PRICE]: 5000.0,
        [KEY_DOUBLE_DISCOUNT]: 500.0,
        [KEY_LONG_QUANTITY]: 5
    }
    const items = [item];
    const param = {
        [KEY_ARRAY_ITEMS]: items,
        [KEY_STRING_ORDER_ID]: "상품번호",
        [KEY_STRING_PAYMENT_METHOD]: "BankTransfer",
        [KEY_DOUBLE_TOTAL_PURCHASE_AMOUNT]: 25500.0,
        [KEY_DOUBLE_DELIVERY_CHARGE]: 3000.0,
        [KEY_DOUBLE_DISCOUNT]:0
    };
    dfineryBridge.logEvent(PURCHASE, param);
    console.log("Purchase 함수가 호출되었습니다.");
}
function setIdentity() {
    dfineryBridge.setIdentity(EMAIL, 'wa@naver.com');
    console.log("setIdentity 함수가 호출되었습니다.");
}
```

#### 6. 완료되었습니다.