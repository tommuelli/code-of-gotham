package com.canoo.cog.solver;

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
import java.util.List;

import com.canoo.cog.sonar.model.BuildingModel;
import com.canoo.cog.sonar.model.CityModel;
import com.canoo.cog.sonar.model.HoodModel;

public class SonarToStrategyConerter {
    
    public CityNode convertCityToNode(CityModel cityModel) {

        List<CityNode> cityNodes = new ArrayList<>();

        for (HoodModel hoodModel : cityModel.getHoods()) {
            cityNodes.add(convertHoodToNode(hoodModel));
        }

        final CityNode nodeCity = new CityNode(cityNodes);
        nodeCity.setModel(cityModel);
        return nodeCity;
    }

    private CityNode convertHoodToNode(HoodModel hoodModel) {

        List<CityNode> hoodNodes = new ArrayList<>();

        for (HoodModel hood : hoodModel.getHoods()) {
            hoodNodes.add(convertHoodToNode(hood));
        }

        for (BuildingModel building : hoodModel.getBuildings()) {
            hoodNodes.add(convertBuildingToNode(building));
        }

        final CityNode hoodNode = new CityNode(hoodNodes);
        hoodNode.setModel(hoodModel);
        return hoodNode;
    }

    private CityNode convertBuildingToNode(BuildingModel buildingModel) {
        final CityNode nodeBuilding = new CityNode(buildingModel.getFunctions());
        nodeBuilding.setModel(buildingModel);
        return nodeBuilding;
    }
}
