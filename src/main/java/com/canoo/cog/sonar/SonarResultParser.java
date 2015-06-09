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

import com.canoo.cog.sonar.model.BuildingModelImpl;
import com.canoo.cog.sonar.model.CityModelImpl;
import com.canoo.cog.sonar.model.HoodModelImpl;
import com.canoo.cog.sonar.model.SonarProject;
import com.canoo.cog.sonar.model.SonarProjectImpl;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

class SonarResultParser {

    private final JsonParser jsonParser = new JsonParser();

    List<SonarProject> parseProjects(String projectResultString) {
        List<SonarProject> sonarProjects = new ArrayList<>();

        JsonElement projects = jsonParser.parse(projectResultString);
        JsonArray projectArray = projects.getAsJsonArray();
        for (JsonElement projectJson : projectArray) {
            String key = projectJson.getAsJsonObject().get("key").getAsString();
            String name = projectJson.getAsJsonObject().get("name").getAsString();
            String version = projectJson.getAsJsonObject().get("version").getAsString();
            sonarProjects.add(new SonarProjectImpl(key, name, version));
        }
        return sonarProjects;
    }

    CityModelImpl parseCity(String cityResultString) {

        JsonElement cityJson = jsonParser.parse(cityResultString);
        JsonArray allComponents = cityJson.getAsJsonArray();

        // Create city
        JsonObject cityObject = allComponents.get(0).getAsJsonObject();
        JsonToModelConverter jsonToModelConverter = new JsonToModelConverter();
        CityModelImpl city = jsonToModelConverter.convertToCity(cityObject);

        // Sort by name
        List<JsonObjectWrapper> javaElements = new ArrayList<>();
        for (JsonElement component : allComponents) {
            javaElements.add(new JsonObjectWrapper(component.getAsJsonObject()));
        }
        Collections.sort(javaElements);

        // Add neighborhoods
        for (JsonObjectWrapper component : javaElements) {
            JsonObject compObject = component.getObject();
            String scopeString = compObject.get("scope").getAsString();
            if (scopeString.equals("DIR")) {
                HoodModelImpl newHood = jsonToModelConverter.convertToHood(compObject);
                city.addHoodToCity(newHood);
            }
        }

        // Add buildings
        for (JsonObjectWrapper component : javaElements) {
            JsonObject compObject = component.getObject();
            String scopeString = compObject.get("scope").getAsString();
            String qualifierString = compObject.get("qualifier").getAsString();
            if (scopeString.equals("FIL") && !qualifierString.equals("UTS")) {
                BuildingModelImpl buildingModel = jsonToModelConverter.convertToBuilding(compObject);
                city.addBuildingToCity(buildingModel);
            }
        }

        // drop empty hoods
        city.removeEmptyHoods();

        return city;
    }
}
