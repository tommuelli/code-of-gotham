package com.canoo.cog.sonar.model;

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
        if (hoodIndex == -1) {
            throw new RuntimeException("Building does not belong to a neighborhood! This should not happen! Check implementationi.");
        } else {
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
