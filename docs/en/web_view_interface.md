# Integrate Dfinery WebView
To use Dfinery features in your app's WebView, simply add `dfinery-html-bridge.js` and `DfineryBridge` provided in the SDK to your WebView and HTML. Once items are added following the guide, the same API available for Dfinery can be used within WebView.

### dfinery-html-bridge.js
This is a js file that implements javascript to facilitate using Dfinery in HTML.

> [!WARNING]
> Do not modify this script file. Integration problems may occur.

### DfineryBridge
This is a commonly used class in plugins such as WebView, Unity, and ReactNative. You can register using WebView's [`addJavascriptInterface()`](https://developer.android.com/reference/android/webkit/WebView#addJavascriptInterface(java.lang.Object,%20java.lang.String)) there is.

### Try integrate Dfinery WebView

> You can also see an example of integration by referring to the [sample](../../sample/) project.

#### 1. Add `com.igaworks.dfinery.DfineryBridge` to WebView.

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

#### 2. [Download](../../assets/dfinery-html-bridge.js) `dfinery-html-bridge.js`.
#### 3. Add the `dfinery-html-bridge.js` file to the `src/main/assets` path.
#### 4. Add the `dfinery-html-bridge.js` script to the HTML document where you want to use Dfinery.

##### 4.1 When adding directly to an HTML document

```html
<script src="./dfinery-html-bridge.js"></script>
```

##### 4.2 When adding to WebView

<details open>
  <summary>Java</summary>

```java
myWebView.setWebViewClient(new WebViewClient(){
    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
        Log.d(TAG, "onPageFinished. url:"+url);
        if(url.equals("{myUrl}")){
            BufferedReader reader = null;
            StringBuilder javascriptString = new StringBuilder();
            try {
                reader = new BufferedReader(new InputStreamReader(getAssets().open("dfinery-html-bridge.js"), "UTF-8"));
                String line;
                while ((line = reader.readLine()) != null) {
                    javascriptString.append(line);
                }
            } catch (IOException e) {

            } finally {
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        //log the exception
                    }
                }
            }
            view.evaluateJavascript(javascriptString.toString(), null);
        }
    }
});
```

</details>

<details open>
  <summary>Kotlin</summary>

```kotlin
myWebView.setWebViewClient(object : WebViewClient() {
    fun onPageFinished(view: WebView, url: String) {
        super.onPageFinished(view, url)
        if (url == "{myUrl}") {
            var reader: java.io.BufferedReader? = null
            val javascriptString: java.lang.StringBuilder = java.lang.StringBuilder()
            try {
                reader = java.io.BufferedReader(
                    InputStreamReader(
                        getAssets().open("dfinery-html-bridge.js"),
                        "UTF-8"
                    )
                )
                var line: String?
                while ((reader.readLine().also { line = it }) != null) {
                    javascriptString.append(line)
                }
            } catch (e: java.io.IOException) {
            } finally {
                if (reader != null) {
                    try {
                        reader.close()
                    } catch (e: java.io.IOException) {
                        //log the exception
                    }
                }
            }
            view.evaluateJavascript(javascriptString.toString(), null)
        }
    }
})
```

</details>

#### 5. Call Dfinery using the `dfineryBridge` object within the HTML document.

> Below is an example.

```javascript
function Login() {
    dfineryBridge.logEvent(LOGIN);
    console.log("Login was called");
}
function Purchase() {
    const item = {
        [KEY_STRING_ITEM_ID]: "itemId",
        [KEY_STRING_ITEM_NAME]: "itemName",
        [KEY_STRING_CATEGORY1]: "food",
        [KEY_STRING_CATEGORY2]: "snack",
        [KEY_DOUBLE_PRICE]: 5000.0,
        [KEY_DOUBLE_DISCOUNT]: 500.0,
        [KEY_LONG_QUANTITY]: 5
    }
    const items = [item];
    const param = {
        [KEY_ARRAY_ITEMS]: items,
        [KEY_STRING_ORDER_ID]: "orderId",
        [KEY_STRING_PAYMENT_METHOD]: "BankTransfer",
        [KEY_DOUBLE_TOTAL_PURCHASE_AMOUNT]: 25500.0,
        [KEY_DOUBLE_DELIVERY_CHARGE]: 3000.0,
        [KEY_DOUBLE_DISCOUNT]:0
    };
    dfineryBridge.logEvent(PURCHASE, param);
    console.log("Purchase was called.");
}
function setIdentity() {
    dfineryBridge.setIdentity(EMAIL, 'wa@naver.com');
    console.log("setIdentity was called.");
}
```

#### 6. Complete.