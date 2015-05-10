package com.canoo.cog;

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


import java.io.IOException;

import com.canoo.cog.sonar.SonarService;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {

        SonarService sonarService = new SonarService();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("welcome.fxml"));
        WelcomeController welcomeController = new WelcomeController(sonarService, stage);
        loader.setController(welcomeController);
        Pane welcomePane = loader.load();

        WelcomeController controller = loader.getController();
        controller.init();
        Scene welcomeScene = new Scene(welcomePane);

        stage.setTitle("Code of Gotham");
        stage.setScene(welcomeScene);
        stage.show();
    }
}
