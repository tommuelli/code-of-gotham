package com.canoo.cog.sonar;

/*
 * #%L
 * code-of-gotham
 * %%
 * Copyright (C) 2015 Canoo Engineering AG
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */


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
