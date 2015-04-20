package com.canoo.cog.sonar;

import com.google.gson.JsonObject;

class JsonObjectWrapper implements Comparable {
    JsonObject object;

    JsonObjectWrapper(JsonObject object) {
        this.object = object;
    }

    JsonObject getObject() {
        return object;
    }

    @Override
    public int compareTo(Object o) {
        if (o != null && o instanceof JsonObjectWrapper && object != null) {
            JsonObjectWrapper inputObject = (JsonObjectWrapper) o;
            if(inputObject.object != null) {
                String thisString = object.get("lname").getAsString();
                String inputString = inputObject.object.get("lname").getAsString();
                return thisString.compareTo(inputString);
            }
        }
        return -1;
    }
}
