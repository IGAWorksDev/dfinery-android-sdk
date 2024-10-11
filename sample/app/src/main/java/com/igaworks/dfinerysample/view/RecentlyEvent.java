package com.igaworks.dfinerysample.view;

import org.json.JSONObject;

public class RecentlyEvent {
    private String name;
    private JSONObject properties;
    private int hashCode;

    public RecentlyEvent(String name, JSONObject properties) {
        this.name = name;
        this.properties = properties;
        makeHashCode();
    }

    public String getName() {
        return name;
    }

    public JSONObject getProperties() {
        return properties;
    }

    public int getHashCode() {
        return hashCode;
    }

    private void makeHashCode(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(name);
        if(properties != null){
            stringBuilder.append(properties.toString());
        }
        this.hashCode = stringBuilder.toString().hashCode();
    }
}
