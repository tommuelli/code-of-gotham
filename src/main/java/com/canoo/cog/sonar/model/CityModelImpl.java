package com.canoo.cog.sonar.model;

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

public class CityModelImpl extends ModelImpl implements CityModel {

    private final List<HoodModelImpl> hoods;

    public CityModelImpl(String cityName) {
        super(cityName);
        hoods = new ArrayList<>();
    }

    @Override
    public List<HoodModel> getHoods() {
        return new ArrayList<>(hoods);
    }

    public void addHoodToCity(HoodModelImpl hood) {
        int hoodIndex = getAppropriateHoodIndex(hood);
        if (hoodIndex == -1) {
            hoods.add(hood);
        } else {
            hoods.get(hoodIndex).addHoodToNeighborhood(hood);
        }
    }

    public void addBuildingToCity(BuildingModelImpl building) {
        int hoodIndex = getAppropriateHoodIndex(building);
        if (hoodIndex != -1) {
            hoods.get(hoodIndex).addBuildingToHood(building);
        }
    }

    private int getAppropriateHoodIndex(ModelImpl newModel) {
        int prefixLength = 0;
        int hoodIndex = -1;
        for (HoodModelImpl hood : hoods) {
            if (newModel.getName().startsWith(hood.getName())) {
                if (hood.getName().length() > prefixLength) {
                    hoodIndex = hoods.indexOf(hood);
                    prefixLength = hood.getName().length();
                }
            }
            ;
        }
        return hoodIndex;
    }

    public void removeEmptyHoods() {
        List<HoodModelImpl> toDeleteHoods = new ArrayList<>();
        for (HoodModelImpl hoodModel : hoods) {
            if (hoodModel.getBuildings().isEmpty() && hoodModel.getHoods().isEmpty()) {
                toDeleteHoods.add(hoodModel);
            }
        }
        for (HoodModelImpl toDeleteHood : toDeleteHoods) {
            hoods.remove(toDeleteHood);
        }

        for (HoodModelImpl hood : hoods) {
            hood.removeEmptyHoods();
        }
    }
}
