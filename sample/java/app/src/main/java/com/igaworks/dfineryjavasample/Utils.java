package com.igaworks.dfineryjavasample;

import com.igaworks.dfinery.constants.DFEventProperty;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class Utils {
    public static <T> JSONArray makeJSONArray(T... element) {
        JSONArray array = new JSONArray();
        for(T temp : element){
            array.put(temp);
        }
        return array;
    }
    public static <T> List<T> makeList(T... element){
        List<T> list = new ArrayList<>();
        for(T temp : element){
            list.add(temp);
        }
        return list;
    }
    public static String parseDateToDfineryDatetimeStringFormat(Date date){
        SimpleDateFormat dateFormat = new SimpleDateFormat(DFEventProperty.DATETIME_FORMAT, Locale.US);
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        return dateFormat.format(date);
    }
    public static class JSONObjectBuilder {
        private final JSONObject object;

        public JSONObjectBuilder() {
            this.object = new JSONObject();
        }

        public JSONObjectBuilder addProperty(String key, Object value){
            try {
                if(value == null){
                    object.put(key, JSONObject.NULL);
                } else{
                    object.put(key, value);
                }
            } catch (JSONException e) {
                System.out.println(e.toString());
            }
            return JSONObjectBuilder.this;
        }
        public JSONObject build(){
            return object;
        }
    }
}
