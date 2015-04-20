package com.canoo.cog.ui.strategy;

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
