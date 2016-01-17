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

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class CityToolBarService {

    public Pane load(String cityName) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("cityToolBar.fxml"));
        CityToolBarController citySideBarController = new CityToolBarController();
        loader.setController(citySideBarController);
        Pane toolBar = loader.load();
        CityToolBarController controller = loader.getController();
        controller.init(cityName);
        return toolBar;
    }
}
