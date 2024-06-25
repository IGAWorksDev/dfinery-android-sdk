# 📊 분석

이 문서는 Dfinery SDK를 사용하여 사용자의 동작을 추적하기 위해 수행해야할 작업에 대해 서술합니다.

## 이벤트 기록 하기

SDK의 `logEvent()` 메소드를 사용하여 사용자의 동작을 기록할 수 있습니다.

> [!IMPORTANT]
> 모든 이벤트는 [Dfinery Console](https://console.dfinery.ai/)에서 미리 이벤트 생성을 하여 등록해야 서버에 정상적으로 반영됩니다. 등록되어 있지 않을 경우 호출 하더라도 반영되지 않습니다.

```java
void logEvent(String eventName)
void logEvent(String eventName, JSONObject properties)
```

- 첫번째 인자인 `eventName`은 기록할 이벤트의 이름을 의미합니다.
-  두번째 인자인 `properties`는 기록할 이벤트의 속성을 의미합니다. 만약 속성이 없을 경우 `null`을 입력할 수 있습니다.

### 속성이 없을 경우

<details open>
  <summary>Java</summary>

```java
Dfinery.getInstance().logEvent("{event_name}");
```

</details>

<details open>
  <summary>Kotlin</summary>

```kotlin
Dfinery.getInstance().logEvent("{event_name}")
```

</details>

### 속성이 있을 경우

<details open>
  <summary>Java</summary>

```java
DfineryJSONObject eventParam = new DfineryJSONObject();
eventParam.put("key", "value");	//사용자 정의 속성 값(Optional)
Dfinery.getInstance().logEvent("{event_name}", eventParam);
```

</details>

<details open>
  <summary>Kotlin</summary>

```kotlin
val eventParam = DfineryJSONObject()
eventParam.put("key", "value")	//사용자 정의 속성 값(Optional)
Dfinery.getInstance().logEvent("{event_name}", eventParam)
```

</details>


## 기 정의된 이벤트 기록 예제

### 로그인
유저가 서비스에 회원으로 가입하는 동작을 나타내는 이벤트입니다.

> [!TIP]
> 로그인을 한 [사용자의 식별 정보를 설정](./identity.md)하면 통합 아이디에 정보가 반영되어 사용자를 더 명확하게 식별할 수 있게 됩니다.

<details open>
  <summary>Java</summary>

```java
Dfinery.getInstance().logEvent(DF.Event.LOGIN);
```

</details>

<details open>
  <summary>Kotlin</summary>

```kotlin
Dfinery.getInstance().logEvent(DF.Event.LOGIN)
```

</details>


### 로그아웃 
유저가 앱에서 로그아웃하는 동작을 나타내는 이벤트입니다.

<details open>
  <summary>Java</summary>

```java
Dfinery.getInstance().logEvent(DF.Event.LOGOUT);
```

</details>

<details open>
  <summary>Kotlin</summary>

```kotlin
Dfinery.getInstance().logEvent(DF.Event.LOGOUT)
```

</details>

### 회원가입
유저가 회원으로 가입하는 동작을 나타내는 이벤트입니다.

<details open>
  <summary>Java</summary>

```java
DfineryJSONObject eventParam = new DfineryJSONObject();
try {
    eventParam.put(DF.EventProperty.KEY_STRING_SIGN_CHANNEL, "Kakao");
} catch (JSONException e) {
    e.printStackTrace();
}
Dfinery.getInstance().logEvent(DF.Event.SIGN_UP, eventParam);
```

</details>

<details open>
  <summary>Kotlin</summary>

```kotlin
val eventParam = DfineryJSONObject()
try {
    eventParam.put(DF.EventProperty.KEY_STRING_SIGN_CHANNEL, "Kakao")
} catch (e: JSONException) {
    e.printStackTrace()
}
Dfinery.getInstance().logEvent(DF.Event.SIGN_UP, eventParam)

```

</details>

#### 기 정의된 속성 값
| 이름            | 타입   | 설명          |필수|
| --------------- | ------ | ------------- |---|
| DF.EventProperty.KEY_STRING_SIGN_CHANNEL | String | 회원가입 채널 |✅|

### 홈 화면 조회
유저가 앱의 홈 화면을 조회하는 동작을 나타내는 이벤트입니다.

<details open>
  <summary>Java</summary>

```java
Dfinery.getInstance().logEvent(DF.Event.VIEW_HOME);
```

</details>

<details open>
  <summary>Kotlin</summary>

```kotlin
Dfinery.getInstance().logEvent(DF.Event.VIEW_HOME)
```

</details>

### 장바구니 조회
유저가 장바구니를 조회하는 동작을 나타내는 이벤트입니다.

<details open>
  <summary>Java</summary>

```java
DfineryJSONObject item = new DfineryJSONObject();
try {
    item.put(DF.EventProperty.KEY_STRING_ITEM_ID, "상품번호");
    item.put(DF.EventProperty.KEY_STRING_ITEM_NAME, "상품이름");
    item.put(DF.EventProperty.KEY_STRING_CATEGORY1, "식품");
    item.put(DF.EventProperty.KEY_STRING_CATEGORY2, "과자");
    item.put(DF.EventProperty.KEY_DOUBLE_PRICE, 5000.0);
    item.put(DF.EventProperty.KEY_DOUBLE_DISCOUNT, 500.0);
    item.put(DF.EventProperty.KEY_LONG_QUANTITY, 5L);
} catch (JSONException e) {
    e.printStackTrace();
}
JSONArray itemList = new JSONArray();
itemList.put(item);
DfineryJSONObject eventParam = new DfineryJSONObject();
try {
    eventParam.put(DF.EventProperty.KEY_ARRAY_ITEMS, itemList);
} catch (JSONException e) {
    e.printStackTrace();
}
Dfinery.getInstance().logEvent(DF.Event.VIEW_CART, eventParam);
```

</details>

<details open>
  <summary>Kotlin</summary>

```kotlin
val item = DfineryJSONObject()
try {
    item.put(DF.EventProperty.KEY_STRING_ITEM_ID, "상품번호")
    item.put(DF.EventProperty.KEY_STRING_ITEM_NAME, "상품이름")
    item.put(DF.EventProperty.KEY_STRING_CATEGORY1, "식품")
    item.put(DF.EventProperty.KEY_STRING_CATEGORY2, "과자")
    item.put(DF.EventProperty.KEY_DOUBLE_PRICE, 5000.0)
    item.put(DF.EventProperty.KEY_DOUBLE_DISCOUNT, 500.0)
    item.put(DF.EventProperty.KEY_LONG_QUANTITY, 5L)
} catch (e: JSONException) {
    e.printStackTrace()
}
val itemList = JSONArray()
itemList.put(item)
val eventParam = DfineryJSONObject()
try {
    eventParam.put(DF.EventProperty.KEY_ARRAY_ITEMS, itemList)
} catch (e: JSONException) {
    e.printStackTrace()
}
Dfinery.getInstance().logEvent(DF.Event.VIEW_CART, eventParam)

```

</details>

#### 기 정의된 속성 값
| 이름     | 타입  | 설명                                                                                 |필수|
| -------- | ----- | ---|------------------------------------------------------------------------------------ |
| DF.EventProperty.KEY_ARRAY_ITEMS | Array | [상품](#상품-속성) |✅|

### 상품 목록 조회
유저가 상품 목록을 조회하는 동작을 나타내는 이벤트입니다.

<details open>
  <summary>Java</summary>

```java
DfineryJSONObject item = new DfineryJSONObject();
try {
    item.put(DF.EventProperty.KEY_STRING_ITEM_ID, "상품번호");
    item.put(DF.EventProperty.KEY_STRING_ITEM_NAME, "상품이름");
    item.put(DF.EventProperty.KEY_STRING_CATEGORY1, "식품");
    item.put(DF.EventProperty.KEY_STRING_CATEGORY2, "과자");
    item.put(DF.EventProperty.KEY_DOUBLE_PRICE, 5000.0);
    item.put(DF.EventProperty.KEY_DOUBLE_DISCOUNT, 500.0);
    item.put(DF.EventProperty.KEY_LONG_QUANTITY, 5L);
} catch (JSONException e) {
    e.printStackTrace();
}
JSONArray itemList = new JSONArray();
itemList.put(item);
DfineryJSONObject eventParam = new DfineryJSONObject();
try {
    eventParam.put(DF.EventProperty.KEY_ARRAY_ITEMS, itemList);
} catch (JSONException e) {
    e.printStackTrace();
}
Dfinery.getInstance().logEvent(DF.Event.VIEW_LIST, eventParam);
```

</details>

<details open>
  <summary>Kotlin</summary>

```kotlin
val item = DfineryJSONObject()
try {
    item.put(DF.EventProperty.KEY_STRING_ITEM_ID, "상품번호")
    item.put(DF.EventProperty.KEY_STRING_ITEM_NAME, "상품이름")
    item.put(DF.EventProperty.KEY_STRING_CATEGORY1, "식품")
    item.put(DF.EventProperty.KEY_STRING_CATEGORY2, "과자")
    item.put(DF.EventProperty.KEY_DOUBLE_PRICE, 5000.0)
    item.put(DF.EventProperty.KEY_DOUBLE_DISCOUNT, 500.0)
    item.put(DF.EventProperty.KEY_LONG_QUANTITY, 5L)
} catch (e: JSONException) {
    e.printStackTrace()
}
val itemList = JSONArray()
itemList.put(item)
val eventParam = DfineryJSONObject()
try {
    eventParam.put(DF.EventProperty.KEY_ARRAY_ITEMS, itemList)
} catch (e: JSONException) {
    e.printStackTrace()
}
Dfinery.getInstance().logEvent(DF.Event.VIEW_LIST, eventParam)
```

</details>

#### 기 정의된 속성 값
| 이름     | 타입  | 설명                                                                                 |필수|
| -------- | ----- | ---|------------------------------------------------------------------------------------ |
| DF.EventProperty.KEY_ARRAY_ITEMS | Array | [상품](#상품-속성) |✅|

### 상품 공유하기
유저가 상품을 공유하는 동작을 나타내는 이벤트입니다.

<details open>
  <summary>Java</summary>

```java
DfineryJSONObject item = new DfineryJSONObject();
try {
    item.put(DF.EventProperty.KEY_STRING_ITEM_ID, "상품번호");
    item.put(DF.EventProperty.KEY_STRING_ITEM_NAME, "상품이름");
    item.put(DF.EventProperty.KEY_STRING_CATEGORY1, "식품");
    item.put(DF.EventProperty.KEY_STRING_CATEGORY2, "과자");
    item.put(DF.EventProperty.KEY_DOUBLE_PRICE, 5000.0);
    item.put(DF.EventProperty.KEY_DOUBLE_DISCOUNT, 500.0);
    item.put(DF.EventProperty.KEY_LONG_QUANTITY, 5L);
} catch (JSONException e) {
    e.printStackTrace();
}
JSONArray itemList = new JSONArray();
itemList.put(item);
DfineryJSONObject eventParam = new DfineryJSONObject();
try {
    eventParam.put(DF.EventProperty.KEY_ARRAY_ITEMS, itemList);
    eventParam.put(DF.EventProperty.KEY_STRING_SHARING_CHANNEL, "Facebook");
} catch (JSONException e) {
    e.printStackTrace();
}
Dfinery.getInstance().logEvent(DF.Event.SHARE_PRODUCT, eventParam);
```

</details>

<details open>
  <summary>Kotlin</summary>

```kotlin
val item = DfineryJSONObject()
try {
    item.put(DF.EventProperty.KEY_STRING_ITEM_ID, "상품번호")
    item.put(DF.EventProperty.KEY_STRING_ITEM_NAME, "상품이름")
    item.put(DF.EventProperty.KEY_STRING_CATEGORY1, "식품")
    item.put(DF.EventProperty.KEY_STRING_CATEGORY2, "과자")
    item.put(DF.EventProperty.KEY_DOUBLE_PRICE, 5000.0)
    item.put(DF.EventProperty.KEY_DOUBLE_DISCOUNT, 500.0)
    item.put(DF.EventProperty.KEY_LONG_QUANTITY, 5L)
} catch (e: JSONException) {
    e.printStackTrace()
}
val itemList = JSONArray()
itemList.put(item)
val eventParam = DfineryJSONObject()
try {
    eventParam.put(DF.EventProperty.KEY_ARRAY_ITEMS, itemList)
    eventParam.put(DF.EventProperty.KEY_STRING_SHARING_CHANNEL, "Facebook")
} catch (e: JSONException) {
    e.printStackTrace()
}
Dfinery.getInstance().logEvent(DF.Event.SHARE_PRODUCT, eventParam)
```

</details>

#### 기 정의된 속성 값
| 이름               | 타입  | 설명                                                                                 |필수|
| ------------------ | ----- | ------------------------------------------------------------------------------------ |---|
| DF.EventProperty.KEY_ARRAY_ITEMS           | Array | [상품](#상품-속성) |✅|
| DF.EventProperty.KEY_STRING_SHARING_CHANNEL | Enum  | 상품 공유 채널                                                                       |✅|

### 상품 검색하기
유저가 상품을 검색하여 결과를 확인하는 동작을 나타내는 이벤트입니다.

<details open>
  <summary>Java</summary>

```java
DfineryJSONObject item = new DfineryJSONObject();
try {
    item.put(DF.EventProperty.KEY_STRING_ITEM_ID, "상품번호");
    item.put(DF.EventProperty.KEY_STRING_ITEM_NAME, "상품이름");
    item.put(DF.EventProperty.KEY_STRING_CATEGORY1, "식품");
    item.put(DF.EventProperty.KEY_STRING_CATEGORY2, "과자");
    item.put(DF.EventProperty.KEY_DOUBLE_PRICE, 5000.0);
    item.put(DF.EventProperty.KEY_DOUBLE_DISCOUNT, 500.0);
    item.put(DF.EventProperty.KEY_LONG_QUANTITY, 5L);
} catch (JSONException e) {
    e.printStackTrace();
}
JSONArray itemList = new JSONArray();
itemList.put(item);
DfineryJSONObject eventParam = new DfineryJSONObject();
try {
    eventParam.put(DF.EventProperty.KEY_ARRAY_ITEMS, itemList);
    eventParam.put(DF.EventProperty.KEY_STRING_KEYWORD, "삼겹살");
} catch (JSONException e) {
    e.printStackTrace();
}
Dfinery.getInstance().logEvent(DF.Event.VIEW_SEARCH_RESULT, eventParam);
```

</details>

<details open>
  <summary>Kotlin</summary>

```kotlin
val item = DfineryJSONObject()
try {
    item.put(DF.EventProperty.KEY_STRING_ITEM_ID, "상품번호")
    item.put(DF.EventProperty.KEY_STRING_ITEM_NAME, "상품이름")
    item.put(DF.EventProperty.KEY_STRING_CATEGORY1, "식품")
    item.put(DF.EventProperty.KEY_STRING_CATEGORY2, "과자")
    item.put(DF.EventProperty.KEY_DOUBLE_PRICE, 5000.0)
    item.put(DF.EventProperty.KEY_DOUBLE_DISCOUNT, 500.0)
    item.put(DF.EventProperty.KEY_LONG_QUANTITY, 5L)
} catch (e: JSONException) {
    e.printStackTrace()
}
val itemList = JSONArray()
itemList.put(item)
val eventParam = DfineryJSONObject()
try {
    eventParam.put(DF.EventProperty.KEY_ARRAY_ITEMS, itemList)
    eventParam.put(DF.EventProperty.KEY_STRING_KEYWORD, "삼겹살")
} catch (e: JSONException) {
    e.printStackTrace()
}
Dfinery.getInstance().logEvent(DF.Event.VIEW_SEARCH_RESULT, eventParam)
```

</details>

#### 기 정의된 속성 값
| 이름       | 타입   | 설명                                                                                 |필수|
| ---------- | ------ | ------------------------------------------------------------------------------------ |---|
| DF.EventProperty.KEY_ARRAY_ITEMS   | Array  | [상품](#상품-속성) |✅|
| DF.EventProperty.KEY_STRING_KEYWORD | String | 검색 키워드                                                                          |✅|

### 관심 상품 추가
유저가 상품을 관심 목록에 추가하는 동작을 나타내는 이벤트입니다.

<details open>
  <summary>Java</summary>

```java
DfineryJSONObject item = new DfineryJSONObject();
try {
    item.put(DF.EventProperty.KEY_STRING_ITEM_ID, "상품번호");
    item.put(DF.EventProperty.KEY_STRING_ITEM_NAME, "상품이름");
    item.put(DF.EventProperty.KEY_STRING_CATEGORY1, "식품");
    item.put(DF.EventProperty.KEY_STRING_CATEGORY2, "과자");
    item.put(DF.EventProperty.KEY_DOUBLE_PRICE, 5000.0);
    item.put(DF.EventProperty.KEY_DOUBLE_DISCOUNT, 500.0);
    item.put(DF.EventProperty.KEY_LONG_QUANTITY, 5L);
} catch (JSONException e) {
    e.printStackTrace();
}
JSONArray itemList = new JSONArray();
itemList.put(item);
DfineryJSONObject eventParam = new DfineryJSONObject();
try {
    eventParam.put(DF.EventProperty.KEY_ARRAY_ITEMS, itemList);
} catch (JSONException e) {
    e.printStackTrace();
}
Dfinery.getInstance().logEvent(DF.Event.ADD_TO_WISHLIST, eventParam);
```

</details>

<details open>
  <summary>Kotlin</summary>

```kotlin
val item = DfineryJSONObject()
try {
    item.put(DF.EventProperty.KEY_STRING_ITEM_ID, "상품번호")
    item.put(DF.EventProperty.KEY_STRING_ITEM_NAME, "상품이름")
    item.put(DF.EventProperty.KEY_STRING_CATEGORY1, "식품")
    item.put(DF.EventProperty.KEY_STRING_CATEGORY2, "과자")
    item.put(DF.EventProperty.KEY_DOUBLE_PRICE, 5000.0)
    item.put(DF.EventProperty.KEY_DOUBLE_DISCOUNT, 500.0)
    item.put(DF.EventProperty.KEY_LONG_QUANTITY, 5L)
} catch (e: JSONException) {
    e.printStackTrace()
}
val itemList = JSONArray()
itemList.put(item)
val eventParam = DfineryJSONObject()
try {
    eventParam.put(DF.EventProperty.KEY_ARRAY_ITEMS, itemList)
} catch (e: JSONException) {
    e.printStackTrace()
}
Dfinery.getInstance().logEvent(DF.Event.ADD_TO_WISHLIST, eventParam)
```

</details>

#### 기 정의된 속성 값
| 이름     | 타입  | 설명                                                                                 |필수|
| -------- | ----- | ------------------------------------------------------------------------------------ |---|
| DF.EventProperty.KEY_ARRAY_ITEMS | Array | [상품](#상품-속성) |✅|

### 장바구니에 상품 담기
유저가 상품을 장바구니에 담는 동작을 나타내는 이벤트입니다.

<details open>
  <summary>Java</summary>

```java
DfineryJSONObject item = new DfineryJSONObject();
try {
    item.put(DF.EventProperty.KEY_STRING_ITEM_ID, "상품번호");
    item.put(DF.EventProperty.KEY_STRING_ITEM_NAME, "상품이름");
    item.put(DF.EventProperty.KEY_STRING_CATEGORY1, "식품");
    item.put(DF.EventProperty.KEY_STRING_CATEGORY2, "과자");
    item.put(DF.EventProperty.KEY_DOUBLE_PRICE, 5000.0);
    item.put(DF.EventProperty.KEY_DOUBLE_DISCOUNT, 500.0);
    item.put(DF.EventProperty.KEY_LONG_QUANTITY, 5L);
} catch (JSONException e) {
    e.printStackTrace();
}
JSONArray itemList = new JSONArray();
itemList.put(item);
DfineryJSONObject eventParam = new DfineryJSONObject();
try {
    eventParam.put(DF.EventProperty.KEY_ARRAY_ITEMS, itemList);
} catch (JSONException e) {
    e.printStackTrace();
}
Dfinery.getInstance().logEvent(DF.Event.ADD_TO_CART, eventParam);
```

</details>

<details open>
  <summary>Kotlin</summary>

```kotlin
val item = DfineryJSONObject()
try {
    item.put(DF.EventProperty.KEY_STRING_ITEM_ID, "상품번호")
    item.put(DF.EventProperty.KEY_STRING_ITEM_NAME, "상품이름")
    item.put(DF.EventProperty.KEY_STRING_CATEGORY1, "식품")
    item.put(DF.EventProperty.KEY_STRING_CATEGORY2, "과자")
    item.put(DF.EventProperty.KEY_DOUBLE_PRICE, 5000.0)
    item.put(DF.EventProperty.KEY_DOUBLE_DISCOUNT, 500.0)
    item.put(DF.EventProperty.KEY_LONG_QUANTITY, 5L)
} catch (e: JSONException) {
    e.printStackTrace()
}
val itemList = JSONArray()
itemList.put(item)
val eventParam = DfineryJSONObject()
try {
    eventParam.put(DF.EventProperty.KEY_ARRAY_ITEMS, itemList)
} catch (e: JSONException) {
    e.printStackTrace()
}
Dfinery.getInstance().logEvent(DF.Event.ADD_TO_CART, eventParam)
```

</details>

#### 기 정의된 속성 값

| 이름     | 타입  | 설명                                                                                 |필수|
| -------- | ----- | ------------------------------------------------------------------------------------ |---|
| DF.EventProperty.KEY_ARRAY_ITEMS | Array | [상품](#상품-속성) |✅|

### 장바구니에 담긴 상품 제거하기
유저가 장바구니에 담긴 상품을 제거하는 나타내는 이벤트입니다.

<details open>
  <summary>Java</summary>

```java
DfineryJSONObject item = new DfineryJSONObject();
try {
    item.put(DF.EventProperty.KEY_STRING_ITEM_ID, "상품번호");
    item.put(DF.EventProperty.KEY_STRING_ITEM_NAME, "상품이름");
    item.put(DF.EventProperty.KEY_STRING_CATEGORY1, "식품");
    item.put(DF.EventProperty.KEY_STRING_CATEGORY2, "과자");
    item.put(DF.EventProperty.KEY_DOUBLE_PRICE, 5000.0);
    item.put(DF.EventProperty.KEY_DOUBLE_DISCOUNT, 500.0);
    item.put(DF.EventProperty.KEY_LONG_QUANTITY, 5L);
} catch (JSONException e) {
    e.printStackTrace();
}
JSONArray itemList = new JSONArray();
itemList.put(item);
DfineryJSONObject eventParam = new DfineryJSONObject();
try {
    eventParam.put(DF.EventProperty.KEY_ARRAY_ITEMS, itemList);
} catch (JSONException e) {
    e.printStackTrace();
}
Dfinery.getInstance().logEvent(DF.Event.REMOVE_CART, eventParam);
```

</details>

<details open>
  <summary>Kotlin</summary>

```kotlin
val item = DfineryJSONObject()
try {
    item.put(DF.EventProperty.KEY_STRING_ITEM_ID, "상품번호")
    item.put(DF.EventProperty.KEY_STRING_ITEM_NAME, "상품이름")
    item.put(DF.EventProperty.KEY_STRING_CATEGORY1, "식품")
    item.put(DF.EventProperty.KEY_STRING_CATEGORY2, "과자")
    item.put(DF.EventProperty.KEY_DOUBLE_PRICE, 5000.0)
    item.put(DF.EventProperty.KEY_DOUBLE_DISCOUNT, 500.0)
    item.put(DF.EventProperty.KEY_LONG_QUANTITY, 5L)
} catch (e: JSONException) {
    e.printStackTrace()
}
val itemList = JSONArray()
itemList.put(item)
val eventParam = DfineryJSONObject()
try {
    eventParam.put(DF.EventProperty.KEY_ARRAY_ITEMS, itemList)
} catch (e: JSONException) {
    e.printStackTrace()
}
Dfinery.getInstance().logEvent(DF.Event.REMOVE_CART, eventParam)
```

</details>

#### 기 정의된 속성 값

| 이름     | 타입  | 설명                                                                                 |필수|
| -------- | ----- | ---|------------------------------------------------------------------------------------ |
| DF.EventProperty.KEY_ARRAY_ITEMS | Array | [상품](#상품-속성) |✅|

### 상품 상세 보기
유저가 특정 상품의 상세 정보를 조회하는 동작을 나타내는 이벤트입니다.

<details open>
  <summary>Java</summary>

```java
DfineryJSONObject item = new DfineryJSONObject();
try {
    item.put(DF.EventProperty.KEY_STRING_ITEM_ID, "상품번호");
    item.put(DF.EventProperty.KEY_STRING_ITEM_NAME, "상품이름");
    item.put(DF.EventProperty.KEY_STRING_CATEGORY1, "식품");
    item.put(DF.EventProperty.KEY_STRING_CATEGORY2, "과자");
    item.put(DF.EventProperty.KEY_DOUBLE_PRICE, 5000.0);
    item.put(DF.EventProperty.KEY_DOUBLE_DISCOUNT, 500.0);
    item.put(DF.EventProperty.KEY_LONG_QUANTITY, 5L);
} catch (JSONException e) {
    e.printStackTrace();
}
JSONArray itemList = new JSONArray();
itemList.put(item);
DfineryJSONObject eventParam = new DfineryJSONObject();
try {
    eventParam.put(DF.EventProperty.KEY_ARRAY_ITEMS, itemList);
} catch (JSONException e) {
    e.printStackTrace();
}
Dfinery.getInstance().logEvent(DF.Event.VIEW_PRODUCT_DETAILS, eventParam);
```

</details>

<details open>
  <summary>Kotlin</summary>

```kotlin
val item = DfineryJSONObject()
try {
    item.put(DF.EventProperty.KEY_STRING_ITEM_ID, "상품번호")
    item.put(DF.EventProperty.KEY_STRING_ITEM_NAME, "상품이름")
    item.put(DF.EventProperty.KEY_STRING_CATEGORY1, "식품")
    item.put(DF.EventProperty.KEY_STRING_CATEGORY2, "과자")
    item.put(DF.EventProperty.KEY_DOUBLE_PRICE, 5000.0)
    item.put(DF.EventProperty.KEY_DOUBLE_DISCOUNT, 500.0)
    item.put(DF.EventProperty.KEY_LONG_QUANTITY, 5L)
} catch (e: JSONException) {
    e.printStackTrace()
}
val itemList = JSONArray()
itemList.put(item)
val eventParam = DfineryJSONObject()
try {
    eventParam.put(DF.EventProperty.KEY_ARRAY_ITEMS, itemList)
} catch (e: JSONException) {
    e.printStackTrace()
}
Dfinery.getInstance().logEvent(DF.Event.VIEW_PRODUCT_DETAILS, eventParam)

```

</details>


#### 기 정의된 속성 값
| 이름     | 타입  | 설명                                                                                 |필수|
| -------- | ----- | ------------------------------------------------------------------------------------ |---|
| DF.EventProperty.KEY_ARRAY_ITEMS | Array | [상품](#상품-속성) |✅|

### 구매 정보 입력
유저가 구매 정보를 입력하는 동작을 나타내는 이벤트입니다.

<details open>
  <summary>Java</summary>

```java
Dfinery.getInstance().logEvent(DF.Event.ADD_PAYMENT_INFO);
```

</details>

<details open>
  <summary>Kotlin</summary>

```kotlin
Dfinery.getInstance().logEvent(DF.Event.ADD_PAYMENT_INFO)
```

</details>

### 구매하기
유저가 상품이나 서비스를 구매하는 동작을 나타내는 이벤트입니다.

<details open>
  <summary>Java</summary>

```java
DfineryJSONObject item = new DfineryJSONObject();
try {
    item.put(DF.EventProperty.KEY_STRING_ITEM_ID, "상품번호");
    item.put(DF.EventProperty.KEY_STRING_ITEM_NAME, "상품이름");
    item.put(DF.EventProperty.KEY_STRING_CATEGORY1, "식품");
    item.put(DF.EventProperty.KEY_STRING_CATEGORY2, "과자");
    item.put(DF.EventProperty.KEY_DOUBLE_PRICE, 5000.0);
    item.put(DF.EventProperty.KEY_DOUBLE_DISCOUNT, 500.0);
    item.put(DF.EventProperty.KEY_LONG_QUANTITY, 5L);
} catch (JSONException e) {
    e.printStackTrace();
}
JSONArray itemList = new JSONArray();
itemList.put(item);
DfineryJSONObject eventParam = new DfineryJSONObject();
try {
    eventParam.put(DF.EventProperty.KEY_ARRAY_ITEMS, itemList);
    eventParam.put(DF.EventProperty.KEY_STRING_ORDER_ID, "상품번호");
    eventParam.put(DF.EventProperty.KEY_STRING_PAYMENT_METHOD, "BankTransfer");
    eventParam.put(DF.EventProperty.KEY_DOUBLE_TOTAL_PURCHASE_AMOUNT, 25500.0);
    eventParam.put(DF.EventProperty.KEY_DOUBLE_DELIVERY_CHARGE, 3000.0);
    eventParam.put(DF.EventProperty.KEY_DOUBLE_DISCOUNT, 0);
} catch (JSONException e) {
    e.printStackTrace();
}
Dfinery.getInstance().logEvent(DF.Event.PURCHASE, eventParam);
```

</details>

<details open>
  <summary>Kotlin</summary>

```kotlin
val item = DfineryJSONObject()
try {
    item.put(DF.EventProperty.KEY_STRING_ITEM_ID, "상품번호")
    item.put(DF.EventProperty.KEY_STRING_ITEM_NAME, "상품이름")
    item.put(DF.EventProperty.KEY_STRING_CATEGORY1, "식품")
    item.put(DF.EventProperty.KEY_STRING_CATEGORY2, "과자")
    item.put(DF.EventProperty.KEY_DOUBLE_PRICE, 5000.0)
    item.put(DF.EventProperty.KEY_DOUBLE_DISCOUNT, 500.0)
    item.put(DF.EventProperty.KEY_LONG_QUANTITY, 5L)
} catch (e: JSONException) {
    e.printStackTrace()
}
val itemList = JSONArray()
itemList.put(item)
val eventParam = DfineryJSONObject()
try {
    eventParam.put(DF.EventProperty.KEY_ARRAY_ITEMS, itemList)
    eventParam.put(DF.EventProperty.KEY_STRING_ORDER_ID, "상품번호")
    eventParam.put(DF.EventProperty.KEY_STRING_PAYMENT_METHOD, "BankTransfer")
    eventParam.put(DF.EventProperty.KEY_DOUBLE_TOTAL_PURCHASE_AMOUNT, 25500.0)
    eventParam.put(DF.EventProperty.KEY_DOUBLE_DELIVERY_CHARGE, 3000.0)
    eventParam.put(DF.EventProperty.KEY_DOUBLE_DISCOUNT, 0)
} catch (e: JSONException) {
    e.printStackTrace()
}
Dfinery.getInstance().logEvent(DF.Event.PURCHASE, eventParam)
```

</details>

#### 기 정의된 속성 값
| 이름                     | 타입   | 설명                                                                                 | 필수 |
| ------------------------ | ------ | ------------------------------------------------------------------------------------ |---|
| DF.EventProperty.KEY_ARRAY_ITEMS                 | Array  | [상품](#상품-속성) |✅|
| DF.EventProperty.KEY_STRING_ORDER_ID              | String | 주문 번호(ID)                                                                        |✅|
| DF.EventProperty.KEY_STRING_PAYMENT_METHOD        | String | 결제 방법                                                                            |✅|
| DF.EventProperty.KEY_DOUBLE_TOTAL_PURCHASE_AMOUNT | Double | 주문 총액                                                                            |✅|
| DF.EventProperty.KEY_DOUBLE_DELIVERY_CHARGE       | Double | 배송료                                                                               |✅|
| DF.EventProperty.KEY_DOUBLE_DISCOUNT              | Double | 상품할인가                                                                             |✅|

### 주문 취소하기
유저가 구매한 주문을 취소하고 환불하는 동작을 나타내는 이벤트입니다.

<details open>
  <summary>Java</summary>

```java
DfineryJSONObject item = new DfineryJSONObject();
try {
    item.put(DF.EventProperty.KEY_STRING_ITEM_ID, "상품번호");
    item.put(DF.EventProperty.KEY_STRING_ITEM_NAME, "상품이름");
    item.put(DF.EventProperty.KEY_STRING_CATEGORY1, "식품");
    item.put(DF.EventProperty.KEY_STRING_CATEGORY2, "과자");
    item.put(DF.EventProperty.KEY_DOUBLE_PRICE, 5000.0);
    item.put(DF.EventProperty.KEY_DOUBLE_DISCOUNT, 500.0);
    item.put(DF.EventProperty.KEY_LONG_QUANTITY, 5L);
} catch (JSONException e) {
    e.printStackTrace();
}
JSONArray itemList = new JSONArray();
itemList.put(item);
DfineryJSONObject eventParam = new DfineryJSONObject();
try {
    eventParam.put(DF.EventProperty.KEY_ARRAY_ITEMS, itemList);
    eventParam.put(DF.EventProperty.KEY_DOUBLE_TOTAL_REFUND_AMOUNT, 22500.0);
} catch (JSONException e) {
    e.printStackTrace();
}
Dfinery.getInstance().logEvent(DF.Event.REFUND, eventParam);
```

</details>

<details open>
  <summary>Kotlin</summary>

```kotlin
val item = DfineryJSONObject()
try {
    item.put(DF.EventProperty.KEY_STRING_ITEM_ID, "상품번호")
    item.put(DF.EventProperty.KEY_STRING_ITEM_NAME, "상품이름")
    item.put(DF.EventProperty.KEY_STRING_CATEGORY1, "식품")
    item.put(DF.EventProperty.KEY_STRING_CATEGORY2, "과자")
    item.put(DF.EventProperty.KEY_DOUBLE_PRICE, 5000.0)
    item.put(DF.EventProperty.KEY_DOUBLE_DISCOUNT, 500.0)
    item.put(DF.EventProperty.KEY_LONG_QUANTITY, 5L)
} catch (e: JSONException) {
    e.printStackTrace()
}
val itemList = JSONArray()
itemList.put(item)
val eventParam = DfineryJSONObject()
try {
    eventParam.put(DF.EventProperty.KEY_ARRAY_ITEMS, itemList)
    eventParam.put(DF.EventProperty.KEY_DOUBLE_TOTAL_REFUND_AMOUNT, 22500.0)
} catch (e: JSONException) {
    e.printStackTrace()
}
Dfinery.getInstance().logEvent(DF.Event.REFUND, eventParam)

```

</details>

#### 기 정의된 속성 값
| 이름                   | 타입   | 설명                                                                                 |필수|
| ---------------------- | ------ | ------------------------------------------------------------------------------------ |---|
| DF.EventProperty.KEY_ARRAY_ITEMS               | Array  | [상품](#상품-속성) |✅|
| DF.EventProperty.KEY_DOUBLE_TOTAL_REFUND_AMOUNT | Double | 환불(취소) 총액                                                                            |✅|

<div id="product"></div>

### 상품 속성
`KEY_ARRAY_ITEMS` 내에 배열로 적재되는 상품에 대한 기 정의된 속성 값에 대한 정보입니다.
4
| 이름         | 타입   | 설명           |필수|
| ------------ | ------ | -------------- |---|
| DF.EventProperty.KEY_STRING_ITEM_ID   | String | 상품 번호(ID)  |✅|
| DF.EventProperty.KEY_STRING_ITEM_NAME | String | 상품 명        |✅|
| DF.EventProperty.KEY_DOUBLE_PRICE     | Number | 상품 단가      |✅|
| DF.EventProperty.KEY_LONG_QUANTITY  | Number | 상품 수량      |✅|
| DF.EventProperty.KEY_DOUBLE_DISCOUNT  | Number | 상품 할인가    |✅|
| DF.EventProperty.KEY_STRING_CATEGORY1 | String | 상품 카테고리1 ||
| DF.EventProperty.KEY_STRING_CATEGORY2 | String | 상품 카테고리2 ||
| DF.EventProperty.KEY_STRING_CATEGORY3 | String | 상품 카테고리3 ||
| DF.EventProperty.KEY_STRING_CATEGORY4 | String | 상품 카테고리4 ||
| DF.EventProperty.KEY_STRING_CATEGORY5 | String | 상품 카테고리5 ||


## 사용자 정의 이벤트
사용자가 직접 임의의 이벤트 명칭과 속성을 입력하여 반영하는 이벤트입니다. 이벤트 명칭과 속성은 [Dfinery Console](https://console.dfinery.ai/)에서 사전에 등록해야합니다.

## 자동으로 수집되는 데이터
Dfinery는 다음의 정보에 대해서 자동으로 수집합니다.

### 세션 추적
Dfinery는 [활동의 생명주기 콜백](https://developer.android.com/reference/android/app/Application.ActivityLifecycleCallbacks) 을 사용하여 세션을 추적합니다.

### App Set ID
Dfinery는 사용자를 특정하기 위해 [App Set Id](https://www.google.com/url?sa=t&rct=j&q=&esrc=s&source=web&cd=&ved=2ahUKEwjl6-Lfg8CDAxXdQPUHHa9BCYMQFnoECBIQAQ&url=https%3A%2F%2Fdeveloper.android.com%2Ftraining%2Farticles%2Fapp-set-id&usg=AOvVaw2BN0DC8U-gaq6r7U2PulxJ&opi=89978449) 를 자동으로 수집합니다.

### 단말기 정보 
Dfinery는 다음의 단말기 정보를 자동으로 수집합니다.
> 해당 값은 앱의 환경 및 허용된 권한에 따라 수집되지 않을 수 있습니다.

- 단말기의 모델
- 단말기의 운영체제
- 단말기의 현재 연결되어 있는 통신사
- 단말기의 설정된 언어
- 단말기의 설정된 지역
- 단말기의 설정된 Time Zone Offset
- 단말기의 전화 기능 여부
- 단말기의 현재 연결된 네트워크 종류
- 단말기의 기기 제조사

### 어플리케이션 정보
Dfinery는 다음의 어플리케이션 정보를 자동으로 수집합니다.

- 어플리케이션의 앱 버전
- 어플리케이션의 패키지 이름