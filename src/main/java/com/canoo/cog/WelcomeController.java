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
import java.util.List;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

import com.canoo.cog.sonar.SonarService;
import com.canoo.cog.sonar.model.CityModel;
import com.canoo.cog.sonar.model.SonarProject;
import com.canoo.cog.ui.CityBuilder;

class WelcomeController {

    @FXML
    private TextField sonarHostname;

    @FXML
    private PasswordField passwordTextField;

    @FXML
    private TextField usernameField;

    @FXML
    private Button loadButton;

    @FXML
    private Button startButton;

    @FXML
    private TableView projectTable;

    @FXML
    private TableColumn keyColumn;

    @FXML
    private TableColumn nameColumn;

    @FXML
    private TableColumn versionColumn;

    private SonarService sonarService;
    private Stage stage;
    private List<SonarProject> projects;

    WelcomeController(SonarService sonarService, Stage stage) {
        this.sonarService = sonarService;
        this.stage = stage;
    }

    public void init() {

       // sonarHostname.setText("https://ci.canoo.com/sonar/");http://nemo.sonarqube.org/
    	sonarHostname.setText("http://localhost:9000/");
    	sonarHostname.setText("http://nemo.sonarqube.org/");
    	 

        keyColumn.setCellValueFactory(new PropertyValueFactory<SonarProject, String>("key"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<SonarProject, String>("name"));
        versionColumn.setCellValueFactory(new PropertyValueFactory<SonarProject, String>("version"));

        loadButton.setOnMouseClicked(event -> {
            loadProjects();
        });

        startButton.setOnMouseClicked(event -> {
            loadCodeCity();
        });

        passwordTextField.setOnKeyPressed(event -> {
            if(event.getCode().equals(KeyCode.ENTER)) {
                loadProjects();
            }
        });
    }

    private void loadCodeCity() {

        CityModel cityData;
        try {
        	
        	SonarProject selectedItem = (SonarProject)projectTable.getSelectionModel().getSelectedItem();
            cityData = sonarService.getCityData(selectedItem.getKey());
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        CityBuilder cityBuilder = new CityBuilder(cityData);
		Scene scene = cityBuilder.build();
        stage.setTitle(cityBuilder.getTitle());
        stage.setScene(scene);
        stage.setFullScreen(true);
    }

    private void loadProjects() {

        new Thread(() -> {
            sonarService.setSonarSettings(sonarHostname.getText(), usernameField.getText(), passwordTextField.getText());
            projects = null;
            try {
                projects = sonarService.getProjects();
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
            Platform.runLater(() -> projectTable.setItems(FXCollections.observableArrayList(projects)));
        }).start();

    }
}
