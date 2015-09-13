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


import com.canoo.cog.sonar.model.BuildingModelImpl;
import com.canoo.cog.sonar.model.CityModelImpl;
import com.canoo.cog.sonar.model.HoodModelImpl;
import com.canoo.cog.sonar.model.ModelImpl;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class JsonToModelConverter {

    public CityModelImpl convertToCity(JsonObject modelObject) {
        String cityName = modelObject.get("name").getAsString();
        String id = modelObject.get("key").getAsString();
        CityModelImpl newCity = new CityModelImpl(cityName, id);
        fillModel(modelObject.get("msr").getAsJsonArray(), newCity);
        return newCity;
    }

    public HoodModelImpl convertToHood(JsonObject modelObject) {
        String hoodName = modelObject.get("name").getAsString();
        String id = modelObject.get("key").getAsString();
        HoodModelImpl newHood = new HoodModelImpl(hoodName, id);
        fillModel(modelObject.get("msr").getAsJsonArray(), newHood);
        return newHood;
    }

    public BuildingModelImpl convertToBuilding(JsonObject modelObject) {
        String buildingName = modelObject.get("lname").getAsString();
        String id = modelObject.get("key").getAsString();
        BuildingModelImpl newBuilding = new BuildingModelImpl(buildingName, id);
        fillModel(modelObject.get("msr").getAsJsonArray(), newBuilding);
        return newBuilding;
    }

    private void fillModel(JsonArray msr, ModelImpl model) {
        for (JsonElement jsonElement : msr) {
            String key = jsonElement.getAsJsonObject().get("key").getAsString();
            String val = jsonElement.getAsJsonObject().get("val").getAsString();

            switch (key) {
                case SonarConstants.CLASSES:
                    model.setClasses(val);
                    break;
                case SonarConstants.CLASS_COMPLEXITY:
                    model.setClassComplexity(val);
                    break;
                case SonarConstants.COMMENT_LINES:
                    model.setCommentLines(val);
                    break;
                case SonarConstants.COMMENT_LINES_DENSITY:
                    model.setCommentLineDensity(val);
                    break;
                case SonarConstants.COMPLEXITY:
                    model.setComplexity(val);
                    break;
                case SonarConstants.COVERAGE:
                    model.setCoverage(val);
                    break;
                case SonarConstants.DIRECTORIES:
                    model.setDirectories(val);
                    break;
                case SonarConstants.FILE_COMPLEXITY:
                    model.setFileComplexity(val);
                    break;
                case SonarConstants.FILES:
                    model.setFiles(val);
                    break;
                case SonarConstants.FUNCTION_COMPLEXITY:
                    model.setFunctionComplexity(val);
                    break;
                case SonarConstants.FUNCTIONS:
                    model.setFunctions(val);
                    break;
                case SonarConstants.LINES_OF_CODE:
                    model.setLinesOfCode(val);
                    break;
                case SonarConstants.PUBLIC_API:
                    model.setPublicApi(val);
                    break;
                case SonarConstants.TESTS:
                    model.setTests(val);
                    break;
                case SonarConstants.STATEMENTS:
                    model.setStatements(val);
                    break;
                case SonarConstants.SQALE_INDEX:
                	model.setSqaleIndex(val);
                	break;
            }
        }
    }
}
