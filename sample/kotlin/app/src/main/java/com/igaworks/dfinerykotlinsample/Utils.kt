package com.igaworks.dfinerykotlinsample

import com.igaworks.dfinery.constants.DFEventProperty
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

object Utils {
    fun <T> makeJSONArray(vararg element: T): JSONArray {
        val array = JSONArray()
        for (temp in element) {
            array.put(temp)
        }
        return array
    }

    fun <T> makeList(vararg element: T): List<T> {
        val list: MutableList<T> = ArrayList()
        for (temp in element) {
            list.add(temp)
        }
        return list
    }

    fun parseDateToDfineryDatetimeStringFormat(date: Date?): String {
        val dateFormat = SimpleDateFormat(DFEventProperty.DATETIME_FORMAT, Locale.US)
        dateFormat.timeZone = TimeZone.getTimeZone("UTC")
        return dateFormat.format(date)
    }
    class JSONObjectBuilder {
        private val jsonObject = JSONObject()

        fun addProperty(key: String?, value: Any?): JSONObjectBuilder {
            try {
                if (value == null) {
                    jsonObject.put(key, JSONObject.NULL)
                } else {
                    jsonObject.put(key, value)
                }
            } catch (e: JSONException) {
                println(e.toString())
            }
            return this@JSONObjectBuilder
        }

        fun build(): JSONObject {
            return jsonObject
        }
    }
}