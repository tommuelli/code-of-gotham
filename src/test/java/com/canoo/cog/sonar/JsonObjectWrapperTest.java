package com.canoo.cog.sonar;

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
        upperObject.addProperty("name", "com/canoo/volta");

        JsonObject lowerObject = new JsonObject();
        lowerObject.addProperty("name", "com/canoo/volta/resources");

        JsonObjectWrapper upperWrapper = new JsonObjectWrapper(upperObject);
        JsonObjectWrapper lowerWrapper = new JsonObjectWrapper(lowerObject);

        int result = upperWrapper.compareTo(lowerWrapper);
        assertTrue(result < 0);
    }

    @Test
    public void testCompareToEqual() throws Exception {

        JsonObject upperObject = new JsonObject();
        upperObject.addProperty("name", "com/canoo/volta/resources");

        JsonObject lowerObject = new JsonObject();
        lowerObject.addProperty("name", "com/canoo/volta/resources");

        JsonObjectWrapper upperWrapper = new JsonObjectWrapper(upperObject);
        JsonObjectWrapper lowerWrapper = new JsonObjectWrapper(lowerObject);

        int result = upperWrapper.compareTo(lowerWrapper);
        assertTrue(result == 0);
    }

    @Test
    public void testCompareToBigger() throws Exception {

        JsonObject upperObject = new JsonObject();
        upperObject.addProperty("name", "com/canoo/volta/resources");

        JsonObject lowerObject = new JsonObject();
        lowerObject.addProperty("name", "com/canoo/volta");

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

        assertEquals(one, elements.get(0).object.get("name").getAsString());
        assertEquals(two, elements.get(1).object.get("name").getAsString());
        assertEquals(three, elements.get(2).object.get("name").getAsString());
        assertEquals(four, elements.get(3).object.get("name").getAsString());
        assertEquals(five, elements.get(4).object.get("name").getAsString());
    }

    private void addElementToList(List<JsonObjectWrapper> elements, String name) {
        JsonObject object = new JsonObject();
        object.addProperty("name", name);
        elements.add(new JsonObjectWrapper(object));
    }
}