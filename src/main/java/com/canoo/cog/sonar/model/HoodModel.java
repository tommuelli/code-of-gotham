package com.canoo.cog.sonar.model;

import java.util.List;

public interface HoodModel extends Model {
    List<HoodModel> getHoods();

    List<BuildingModel> getBuildings();
}
