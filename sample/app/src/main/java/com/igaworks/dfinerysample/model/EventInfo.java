package com.igaworks.dfinerysample.model;

import androidx.annotation.NonNull;

import org.json.JSONObject;

public class EventInfo {
    private String eventName;
    private JSONObject properties;

    public EventInfo(String eventName) {
        this.eventName = eventName;
        this.properties = new JSONObject();
    }

    public EventInfo(String eventName, JSONObject properties) {
        this.eventName = eventName;
        this.properties = properties;
    }

    public String getEventName() {
        return eventName;
    }

    public JSONObject getProperties() {
        return properties;
    }

    @NonNull
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(eventName);
        if(properties.length() > 0){
            stringBuilder.append(", [...]");
        } else{
            stringBuilder.append(", []");
        }
        return stringBuilder.toString();
    }
}
