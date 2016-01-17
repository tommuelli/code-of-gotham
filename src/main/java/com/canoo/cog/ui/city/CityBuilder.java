package com.canoo.cog.ui.city;

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

import com.canoo.cog.solver.CityNode;
import com.canoo.cog.ui.city.model.City;
import com.canoo.cog.ui.city.util.StageUtil;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;

public class CityBuilder {

    private final StageUtil stageUtil = new StageUtil();

    final PerspectiveCamera camera = new PerspectiveCamera(true);

    public Scene buildScene(CityNode resultNode, String cityName) {

        // Create city
        City city = stageUtil.createCity(resultNode, cityName);

        // Create root group and scene
        final Scene scene = stageUtil.initScene(city, camera, cityName);

        // Add mouse events
        stageUtil.setMouseEventsToScene(scene, camera);

        // Create initial time line
        stageUtil.makeTimeLine(camera);

        // Set Text Properties
        stageUtil.setTextProperties((Group) stageUtil.getSubScene().getRoot(), city, city.getInfo());

        // Set city style
        stageUtil.setStyle(stageUtil.getSubScene());

        return scene;
    }
}
