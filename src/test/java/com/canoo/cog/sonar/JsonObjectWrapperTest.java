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


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.google.gson.JsonObject;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

public class JsonObjectWrapperTest {

    @Test
    public void testCompareToSmaller() throws Exception {

        JsonObject upperObject = new JsonObject();
        upperObject.addProperty("lname", "com/canoo/volta");

        JsonObject lowerObject = new JsonObject();
        lowerObject.addProperty("lname", "com/canoo/volta/resources");

        JsonObjectWrapper upperWrapper = new JsonObjectWrapper(upperObject);
        JsonObjectWrapper lowerWrapper = new JsonObjectWrapper(lowerObject);

        int result = upperWrapper.compareTo(lowerWrapper);
        assertTrue(result < 0);
    }

    @Test
    public void testCompareToEqual() throws Exception {

        JsonObject upperObject = new JsonObject();
        upperObject.addProperty("lname", "com/canoo/volta/resources");

        JsonObject lowerObject = new JsonObject();
        lowerObject.addProperty("lname", "com/canoo/volta/resources");

        JsonObjectWrapper upperWrapper = new JsonObjectWrapper(upperObject);
        JsonObjectWrapper lowerWrapper = new JsonObjectWrapper(lowerObject);

        int result = upperWrapper.compareTo(lowerWrapper);
        assertTrue(result == 0);
    }

    @Test
    public void testCompareToBigger() throws Exception {

        JsonObject upperObject = new JsonObject();
        upperObject.addProperty("lname", "com/canoo/volta/resources");

        JsonObject lowerObject = new JsonObject();
        lowerObject.addProperty("lname", "com/canoo/volta");

        JsonObjectWrapper upperWrapper = new JsonObjectWrapper(upperObject);
        JsonObjectWrapper lowerWrapper = new JsonObjectWrapper(lowerObject);

        int result = upperWrapper.compareTo(lowerWrapper);
        assertTrue(result > 0);
    }

    @Test
    public void testCompareToNull() throws Exception {

        JsonObject lowerObject = new JsonObject();
        lowerObject.addProperty("name", "com/canoo/volta");

        JsonObjectWrapper upperWrapper = new JsonObjectWrapper(null);
        JsonObjectWrapper lowerWrapper = new JsonObjectWrapper(lowerObject);

        int result = upperWrapper.compareTo(lowerWrapper);
        assertTrue(result < 0);

        result = lowerWrapper.compareTo(upperWrapper);
        assertTrue(result < 0);
    }

    @Test
    public void testCompareToSort() throws Exception {

        List<JsonObjectWrapper> elements = new ArrayList<>();

        String four = "com/canoo/volta/services";
        addElementToList(elements, four);
        String two = "com/canoo/volta/resources";
        addElementToList(elements, two);
        String one = "com/canoo/volta";
        addElementToList(elements, one);
        String three = "com/canoo/volta/resources/util";
        addElementToList(elements, three);
        String five = "com/canoo/volta/services/dao";
        addElementToList(elements, five);

        Collections.sort(elements);

        assertEquals(one, elements.get(0).object.get("lname").getAsString());
        assertEquals(two, elements.get(1).object.get("lname").getAsString());
        assertEquals(three, elements.get(2).object.get("lname").getAsString());
        assertEquals(four, elements.get(3).object.get("lname").getAsString());
        assertEquals(five, elements.get(4).object.get("lname").getAsString());
    }

    private void addElementToList(List<JsonObjectWrapper> elements, String name) {
        JsonObject object = new JsonObject();
        object.addProperty("lname", name);
        elements.add(new JsonObjectWrapper(object));
    }
}