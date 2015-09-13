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

public class HoodModelImpl extends ModelImpl implements HoodModel {

    private final List<HoodModelImpl> hoods;
    private final List<BuildingModelImpl> buildings;

    public HoodModelImpl(String hoodName, String id) {
        super(hoodName, id);
        buildings = new ArrayList<>();
        hoods = new ArrayList<>();
    }

    public List<HoodModel> getHoods() {
        return new ArrayList<>(hoods);
    }

    public List<BuildingModel> getBuildings() {
        return new ArrayList<>(buildings);
    }

    void addHoodToNeighborhood(HoodModelImpl hood) {
        int hoodIndex = getAppropriateHoodIndex(hood);
        if (hoodIndex == -1) {
            hoods.add(hood);
        } else {
            hoods.get(hoodIndex).addHoodToNeighborhood(hood);
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

    void addBuildingToHood(BuildingModelImpl building) {
        int hoodIndex = getAppropriateHoodIndex(building);
        if (hoodIndex == -1) {
            buildings.add(building);
        } else {
            hoods.get(hoodIndex).addBuildingToHood(building);
        }
    }

    void removeEmptyHoods() {
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
