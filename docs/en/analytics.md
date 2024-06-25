# 📊 Analytics

This document describes what you need to do to track user behavior using the Dfinery SDK.

## Logging events

You can log user actions using the SDK's `logEvent()` method.

> [!IMPORTANT]
> All events must be created and registered in advance in [Dfinery Console](https://console.dfinery.ai/) to be properly reflected on the server. If it is not registered, it will not be reflected even if called.

```java
void logEvent(String eventName)
void logEvent(String eventName, JSONObject properties)
```

- The first argument, `eventName`, refers to the name of the event to be recorded.
- The second argument, `properties`, refers to the properties of the event to be recorded. If there is no attribute, you can enter `null`.

### If the event has no properties

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

### If the event has properties

<details open>
  <summary>Java</summary>

```java
DfineryJSONObject eventParam = new DfineryJSONObject();
eventParam.put("key", "value");	
Dfinery.getInstance().logEvent("{event_name}", eventParam);
```

</details>

<details open>
  <summary>Kotlin</summary>

```kotlin
val eventParam = DfineryJSONObject()
eventParam.put("key", "value")	
Dfinery.getInstance().logEvent("{event_name}", eventParam)
```

</details>


## Example of logging a Pre-defined Event 

### Login
This event indicates that user signing up for a service as a member.

> [!TIP]
> If you log in and [set Unified ID identification](./identity.md), the information will be set in the Unified ID, allowing you to identify the user more clearly.

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


### Logout
This event indicates that the user logs out of the app.

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

### Sign Up
This event indicates that the action of a user signing up as a member.

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

#### Pre-defined property values
| Name            | Type   | Description          |is Required|
| --------------- | ------ | ------------- |---|
| DF.EventProperty.KEY_STRING_SIGN_CHANNEL | String | membership registration channel |✅|

### View home
This event indicates the user's action of viewing the app's home screen.

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

### View cart
This event indicates the action of the user checking the shopping cart.

<details open>
  <summary>Java</summary>

```java
DfineryJSONObject item = new DfineryJSONObject();
try {
    item.put(DF.EventProperty.KEY_STRING_ITEM_ID, "ProductNumber");
    item.put(DF.EventProperty.KEY_STRING_ITEM_NAME, "ProductName");
    item.put(DF.EventProperty.KEY_STRING_CATEGORY1, "Food");
    item.put(DF.EventProperty.KEY_STRING_CATEGORY2, "Cracker");
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
    item.put(DF.EventProperty.KEY_STRING_ITEM_ID, "ProductNumber")
    item.put(DF.EventProperty.KEY_STRING_ITEM_NAME, "ProductName")
    item.put(DF.EventProperty.KEY_STRING_CATEGORY1, "Food")
    item.put(DF.EventProperty.KEY_STRING_CATEGORY2, "Cracker")
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

#### Pre-defined property values
| Name     | Type  | Description                                                                                 |is Required|
| -------- | ----- | ---|------------------------------------------------------------------------------------ |
| DF.EventProperty.KEY_ARRAY_ITEMS | Array | [Product](#product-property-values) |✅|

### View list
This event indicates the user's action of viewing the product list.

<details open>
  <summary>Java</summary>

```java
DfineryJSONObject item = new DfineryJSONObject();
try {
    item.put(DF.EventProperty.KEY_STRING_ITEM_ID, "ProductNumber");
    item.put(DF.EventProperty.KEY_STRING_ITEM_NAME, "ProductName");
    item.put(DF.EventProperty.KEY_STRING_CATEGORY1, "Food");
    item.put(DF.EventProperty.KEY_STRING_CATEGORY2, "Cracker");
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
    item.put(DF.EventProperty.KEY_STRING_ITEM_ID, "ProductNumber")
    item.put(DF.EventProperty.KEY_STRING_ITEM_NAME, "ProductName")
    item.put(DF.EventProperty.KEY_STRING_CATEGORY1, "Food")
    item.put(DF.EventProperty.KEY_STRING_CATEGORY2, "Cracker")
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

#### Pre-defined property values
| Name     | Type  | Description                                                                                 |is Required|
| -------- | ----- | ---|------------------------------------------------------------------------------------ |
| DF.EventProperty.KEY_ARRAY_ITEMS | Array | [Product](#product-property-values) |✅|

### Share product
This event indicates the action of a user sharing a product.

<details open>
  <summary>Java</summary>

```java
DfineryJSONObject item = new DfineryJSONObject();
try {
    item.put(DF.EventProperty.KEY_STRING_ITEM_ID, "ProductNumber");
    item.put(DF.EventProperty.KEY_STRING_ITEM_NAME, "ProductName");
    item.put(DF.EventProperty.KEY_STRING_CATEGORY1, "Food");
    item.put(DF.EventProperty.KEY_STRING_CATEGORY2, "Cracker");
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
    item.put(DF.EventProperty.KEY_STRING_ITEM_ID, "ProductNumber")
    item.put(DF.EventProperty.KEY_STRING_ITEM_NAME, "ProductName")
    item.put(DF.EventProperty.KEY_STRING_CATEGORY1, "Food")
    item.put(DF.EventProperty.KEY_STRING_CATEGORY2, "Cracker")
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

#### Pre-defined property values
| Name               | Type  | Description                                                                                 |is Required|
| ------------------ | ----- | ------------------------------------------------------------------------------------ |---|
| DF.EventProperty.KEY_ARRAY_ITEMS           | Array | [Product](#product-property-values) |✅|
| DF.EventProperty.KEY_STRING_SHARING_CHANNEL | Enum  | Channel used to share product                                                                       |✅|

### View search result
This event indicates the action of a user searching for a product and checking the results.

<details open>
  <summary>Java</summary>

```java
DfineryJSONObject item = new DfineryJSONObject();
try {
    item.put(DF.EventProperty.KEY_STRING_ITEM_ID, "ProductNumber");
    item.put(DF.EventProperty.KEY_STRING_ITEM_NAME, "ProductName");
    item.put(DF.EventProperty.KEY_STRING_CATEGORY1, "Food");
    item.put(DF.EventProperty.KEY_STRING_CATEGORY2, "Cracker");
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
    eventParam.put(DF.EventProperty.KEY_STRING_KEYWORD, "Fork");
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
    item.put(DF.EventProperty.KEY_STRING_ITEM_ID, "ProductNumber")
    item.put(DF.EventProperty.KEY_STRING_ITEM_NAME, "ProductName")
    item.put(DF.EventProperty.KEY_STRING_CATEGORY1, "Food")
    item.put(DF.EventProperty.KEY_STRING_CATEGORY2, "Cracker")
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
    eventParam.put(DF.EventProperty.KEY_STRING_KEYWORD, "Fork")
} catch (e: JSONException) {
    e.printStackTrace()
}
Dfinery.getInstance().logEvent(DF.Event.VIEW_SEARCH_RESULT, eventParam)
```

</details>

#### Pre-defined property values
| Name       | Type   | Description                                                                                 |is Required|
| ---------- | ------ | ------------------------------------------------------------------------------------ |---|
| DF.EventProperty.KEY_ARRAY_ITEMS   | Array  | [Product](#product-property-values) |✅|
| DF.EventProperty.KEY_STRING_KEYWORD | String | Searching keyword                                                                          |✅|

### Add to wishlist
This event indicates the action of a user adding a product to his/her interest list.

<details open>
  <summary>Java</summary>

```java
DfineryJSONObject item = new DfineryJSONObject();
try {
    item.put(DF.EventProperty.KEY_STRING_ITEM_ID, "ProductNumber");
    item.put(DF.EventProperty.KEY_STRING_ITEM_NAME, "ProductName");
    item.put(DF.EventProperty.KEY_STRING_CATEGORY1, "Food");
    item.put(DF.EventProperty.KEY_STRING_CATEGORY2, "Cracker");
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
    item.put(DF.EventProperty.KEY_STRING_ITEM_ID, "ProductNumber")
    item.put(DF.EventProperty.KEY_STRING_ITEM_NAME, "ProductName")
    item.put(DF.EventProperty.KEY_STRING_CATEGORY1, "Food")
    item.put(DF.EventProperty.KEY_STRING_CATEGORY2, "Cracker")
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

#### Pre-defined property values
| Name     | Type  | Description                                                                                 |is Required|
| -------- | ----- | ------------------------------------------------------------------------------------ |---|
| DF.EventProperty.KEY_ARRAY_ITEMS | Array | [Product](#product-property-values) |✅|

### Add to cart
This event indicates the user putting a product into the shopping cart.

<details open>
  <summary>Java</summary>

```java
DfineryJSONObject item = new DfineryJSONObject();
try {
    item.put(DF.EventProperty.KEY_STRING_ITEM_ID, "ProductNumber");
    item.put(DF.EventProperty.KEY_STRING_ITEM_NAME, "ProductName");
    item.put(DF.EventProperty.KEY_STRING_CATEGORY1, "Food");
    item.put(DF.EventProperty.KEY_STRING_CATEGORY2, "Cracker");
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
    item.put(DF.EventProperty.KEY_STRING_ITEM_ID, "ProductNumber")
    item.put(DF.EventProperty.KEY_STRING_ITEM_NAME, "ProductName")
    item.put(DF.EventProperty.KEY_STRING_CATEGORY1, "Food")
    item.put(DF.EventProperty.KEY_STRING_CATEGORY2, "Cracker")
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

#### Pre-defined property values

| Name     | Type  | Description                                                                                 |is Required|
| -------- | ----- | ------------------------------------------------------------------------------------ |---|
| DF.EventProperty.KEY_ARRAY_ITEMS | Array | [Product](#product-property-values) |✅|

### Remove cart
This event indicates that the user removes a product from the shopping cart.

<details open>
  <summary>Java</summary>

```java
DfineryJSONObject item = new DfineryJSONObject();
try {
    item.put(DF.EventProperty.KEY_STRING_ITEM_ID, "ProductNumber");
    item.put(DF.EventProperty.KEY_STRING_ITEM_NAME, "ProductName");
    item.put(DF.EventProperty.KEY_STRING_CATEGORY1, "Food");
    item.put(DF.EventProperty.KEY_STRING_CATEGORY2, "Cracker");
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
    item.put(DF.EventProperty.KEY_STRING_ITEM_ID, "ProductNumber")
    item.put(DF.EventProperty.KEY_STRING_ITEM_NAME, "ProductName")
    item.put(DF.EventProperty.KEY_STRING_CATEGORY1, "Food")
    item.put(DF.EventProperty.KEY_STRING_CATEGORY2, "Cracker")
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

#### Pre-defined property values

| Name     | Type  | Description                                                                                 |is Required|
| -------- | ----- | ---|------------------------------------------------------------------------------------ |
| DF.EventProperty.KEY_ARRAY_ITEMS | Array | [Product](#product-property-values) |✅|

### View product details
This event indicates the action of a user searching for detailed information about a specific product.

<details open>
  <summary>Java</summary>

```java
DfineryJSONObject item = new DfineryJSONObject();
try {
    item.put(DF.EventProperty.KEY_STRING_ITEM_ID, "ProductNumber");
    item.put(DF.EventProperty.KEY_STRING_ITEM_NAME, "ProductName");
    item.put(DF.EventProperty.KEY_STRING_CATEGORY1, "Food");
    item.put(DF.EventProperty.KEY_STRING_CATEGORY2, "Cracker");
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
    item.put(DF.EventProperty.KEY_STRING_ITEM_ID, "ProductNumber")
    item.put(DF.EventProperty.KEY_STRING_ITEM_NAME, "ProductName")
    item.put(DF.EventProperty.KEY_STRING_CATEGORY1, "Food")
    item.put(DF.EventProperty.KEY_STRING_CATEGORY2, "Cracker")
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


#### Pre-defined property values
| Name     | Type  | Description                                                                                 |is Required|
| -------- | ----- | ------------------------------------------------------------------------------------ |---|
| DF.EventProperty.KEY_ARRAY_ITEMS | Array | [Product](#product-property-values) |✅|

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

### Purchase
This event indicates the action of a user purchasing a product or service.

<details open>
  <summary>Java</summary>

```java
DfineryJSONObject item = new DfineryJSONObject();
try {
    item.put(DF.EventProperty.KEY_STRING_ITEM_ID, "ProductNumber");
    item.put(DF.EventProperty.KEY_STRING_ITEM_NAME, "ProductName");
    item.put(DF.EventProperty.KEY_STRING_CATEGORY1, "Food");
    item.put(DF.EventProperty.KEY_STRING_CATEGORY2, "Cracker");
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
    eventParam.put(DF.EventProperty.KEY_STRING_ORDER_ID, "ProductNumber");
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
    item.put(DF.EventProperty.KEY_STRING_ITEM_ID, "ProductNumber")
    item.put(DF.EventProperty.KEY_STRING_ITEM_NAME, "ProductName")
    item.put(DF.EventProperty.KEY_STRING_CATEGORY1, "Food")
    item.put(DF.EventProperty.KEY_STRING_CATEGORY2, "Cracker")
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
    eventParam.put(DF.EventProperty.KEY_STRING_ORDER_ID, "ProductNumber")
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

#### Pre-defined property values
| Name                     | Type   | Description                                                                                 | is Required |
| ------------------------ | ------ | ------------------------------------------------------------------------------------ |---|
| DF.EventProperty.KEY_ARRAY_ITEMS                 | Array  | [Product](#product-property-values) |✅|
| DF.EventProperty.KEY_STRING_ORDER_ID              | String | Order Number(ID)                                                                        |✅|
| DF.EventProperty.KEY_STRING_PAYMENT_METHOD        | String | Payment Method                                                                            |✅|
| DF.EventProperty.KEY_DOUBLE_TOTAL_PURCHASE_AMOUNT | Double | Total of the price                                                                            |✅|
| DF.EventProperty.KEY_DOUBLE_DELIVERY_CHARGE       | Double | Delivery fee                                                                               |✅|
| DF.EventProperty.KEY_DOUBLE_DISCOUNT              | Double | Discount                                                                             |✅|

### Refund
This event indicates the action of canceling and refunding an order purchased by a user.

<details open>
  <summary>Java</summary>

```java
DfineryJSONObject item = new DfineryJSONObject();
try {
    item.put(DF.EventProperty.KEY_STRING_ITEM_ID, "ProductNumber");
    item.put(DF.EventProperty.KEY_STRING_ITEM_NAME, "ProductName");
    item.put(DF.EventProperty.KEY_STRING_CATEGORY1, "Food");
    item.put(DF.EventProperty.KEY_STRING_CATEGORY2, "Cracker");
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
    item.put(DF.EventProperty.KEY_STRING_ITEM_ID, "ProductNumber")
    item.put(DF.EventProperty.KEY_STRING_ITEM_NAME, "ProductName")
    item.put(DF.EventProperty.KEY_STRING_CATEGORY1, "Food")
    item.put(DF.EventProperty.KEY_STRING_CATEGORY2, "Cracker")
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

#### Pre-defined property values
| Name                   | Type   | Description                                                                                 |is Required|
| ---------------------- | ------ | ------------------------------------------------------------------------------------ |---|
| DF.EventProperty.KEY_ARRAY_ITEMS               | Array  | [Product](#product-property-values) |✅|
| DF.EventProperty.KEY_DOUBLE_TOTAL_REFUND_AMOUNT | Double | total of the refund fee                                                                            |✅|

<div id="product"></div>

### Product property values
This is information about pre-defined property values for products loaded as an array in `KEY_ARRAY_ITEMS`.
4
| Name         | Type   | Description           |is Required|
| ------------ | ------ | -------------- |---|
| DF.EventProperty.KEY_STRING_ITEM_ID   | String | Product number(ID)  |✅|
| DF.EventProperty.KEY_STRING_ITEM_NAME | String | product name        |✅|
| DF.EventProperty.KEY_DOUBLE_PRICE     | Number | price      |✅|
| DF.EventProperty.KEY_LONG_QUANTITY  | Number | quantity      |✅|
| DF.EventProperty.KEY_DOUBLE_DISCOUNT  | Number | discount    |✅|
| DF.EventProperty.KEY_STRING_CATEGORY1 | String | product category 1 ||
| DF.EventProperty.KEY_STRING_CATEGORY2 | String | product category 2 ||
| DF.EventProperty.KEY_STRING_CATEGORY3 | String | product category 3 ||
| DF.EventProperty.KEY_STRING_CATEGORY4 | String | product category 4 ||
| DF.EventProperty.KEY_STRING_CATEGORY5 | String | product category 5 ||


## Custom Event
This is an event that the user sets by directly entering an arbitrary event name and properties. Event names and properties must be registered in advance in [Dfinery Console](https://console.dfinery.ai/).

## Data collected automatically
Dfinery automatically collects the following information:

### Tracking Sessions
Dfinery uses [Activity Lifecycle Callbacks](https://developer.android.com/reference/android/app/Application.ActivityLifecycleCallbacks) to track sessions.

### App Set ID
Dfinery uses [App Set Id](https://www.google.com/url?sa=t&rct=j&q=&esrc=s&source=web&cd=&ved=2ahUKEwjl6-Lfg8CDAxXdQPUHHa9BCYMQFnoECBIQAQ&url=https%3A%2F%2Fdeveloper to specify users. Automatically collects .android.com%2Ftraining%2Farticles%2Fapp-set-id&usg=AOvVaw2BN0DC8U-gaq6r7U2PulxJ&opi=89978449).

### Device information
Dfinery automatically collects the following device information:
> The value may not be collected depending on the app's environment and granted permissions.

- Model of the device
- The device's operating system
- The mobile carrier the device is currently connected to
- The language set on the device
- The region set on the device
- Time Zone Offset set on the device
- Whether the device has a phone function
- Type of network the device is currently connected to
- The device manufacturer of the device

### Application information
Dfinery automatically collects the following application information:

- App version of the application
- Package name of the application