package com.canoo.cog.ui.welcome;

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


import java.util.List;

import com.canoo.cog.solver.CityNode;
import com.canoo.cog.solver.Solver;
import com.canoo.cog.solver.SolverMaximusHaeckius;
import com.canoo.cog.solver.SonarToStrategyConerter;
import com.canoo.cog.sonar.SonarException;
import com.canoo.cog.sonar.SonarService;
import com.canoo.cog.sonar.model.CityModel;
import com.canoo.cog.sonar.model.SonarProject;
import com.canoo.cog.ui.city.CityStage;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;

import static javafx.scene.control.Alert.AlertType;

class WelcomeController {

    // Property Keys
    private static final String COG_PROXY_KEY = "cogProxy";
    private static final String COG_SONAR_HOST_KEY = "cogSonarHost";

    // Alert Messages
    public static final String PROXY_FORM_ERROR_HEADER = "Proxy is not well formed.";
    public static final String PROXY_FORM_ERROR_CONTENT = "The proxy %s does not have the appropriate format {hostname}:{port}.";
    public static final String PROJECT_LIST_EMPTY_HEAER = "Project list is empty.";
    public static final String PROJECT_LIST_EMPTY_CONTENT = "The project list retrieved from %s does not return any projects.";
    public static final String PROJECTS_LOAD_ERROR_HEADER = "Could not load Projects.";
    public static final String PROJECTS_LOAD_ERROR_CONTENT = "An error occurred when communicating with %s. The server does not seem to be reachable.";
    public static final String CITY_BUILD_ERROR_HEADER = "The city could not be built.";
    public static final String CITY_BUILD_ERROR_CONTENT = "An Exception occurred when getting the city information from the sonar server %s";

    @FXML
    private TextField sonarHostname;

    @FXML
    private TextField proxy;

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

    @FXML
    private ProgressIndicator progressIndicator;

    @FXML
    private Label progressText;

    private SonarService sonarService;
    private List<SonarProject> projects;
    private boolean isCityLoading;
    private BooleanBinding isDataOrCityLoading;

    WelcomeController(SonarService sonarService) {
        this.sonarService = sonarService;
    }

    public void init() {
        // Set server values
        setConfiguredServerValues();

        //Initialize Is Loading Property
        isCityLoading = false;
        isDataOrCityLoading = new BooleanBinding() {
            @Override
            protected boolean computeValue() {
                return isCityLoading;
            }
        };

        // Bind progress indicator
        progressIndicator.visibleProperty().bind(isDataOrCityLoading);
        progressText.visibleProperty().bind(isDataOrCityLoading);

        // Enter event that triggers project load
        addLoadProjectsOnEnterEvent(sonarHostname);
        addLoadProjectsOnEnterEvent(proxy);
        addLoadProjectsOnEnterEvent(usernameField);
        addLoadProjectsOnEnterEvent(passwordTextField);

        // Bind disable property to is city loading
        projectTable.disableProperty().bind(isDataOrCityLoading);
        sonarHostname.disableProperty().bind(isDataOrCityLoading);
        proxy.disableProperty().bind(isDataOrCityLoading);
        usernameField.disableProperty().bind(isDataOrCityLoading);
        passwordTextField.disableProperty().bind(isDataOrCityLoading);

        // Initialize the projects table
        keyColumn.setCellValueFactory(new PropertyValueFactory<SonarProject, String>("key"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<SonarProject, String>("name"));
        versionColumn.setCellValueFactory(new PropertyValueFactory<SonarProject, String>("version"));
        projectTable.setRowFactory(tv -> {
            TableRow<String> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    loadCodeCity();
                }
            });
            return row;
        });

        // Initialize the buttons
        loadButton.setOnMouseClicked(event -> loadProjects());
        loadButton.disableProperty().bind(Bindings.or(sonarHostname.textProperty().isEmpty(), isDataOrCityLoading));
        startButton.setOnMouseClicked(event -> loadCodeCity());
        startButton.disableProperty().bind(Bindings.or(isDataOrCityLoading, projectTable.getSelectionModel().selectedIndexProperty().lessThan(0)));
        Platform.runLater(projectTable::requestFocus);
    }

    private void addLoadProjectsOnEnterEvent(TextField sonarHostname) {
        sonarHostname.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                loadProjects();
            }
        });
    }

    private void setConfiguredServerValues() {
        // Sonar
        String sonarHostnameDefault = System.getProperty(COG_SONAR_HOST_KEY);
        if (sonarHostnameDefault != null && !sonarHostnameDefault.isEmpty()) {
            sonarHostname.setText(sonarHostnameDefault);
        }
        // Proxy
        String proxyDefault = System.getProperty(COG_PROXY_KEY);
        if (proxyDefault != null && !proxyDefault.isEmpty()) {
            proxy.setText(proxyDefault);
        }
    }

    private void loadProjects() {
        // Load Projects in separate UI thread
        new Thread(() -> {
            setIsDataOrCityLoading(true);
            try {
                sonarService.setSonarSettings(sonarHostname.getText(), usernameField.getText(), passwordTextField.getText(), proxy.getText());
                try {
                    projects = sonarService.getProjects();
                    if (projects.isEmpty()) {
                        showAlert(AlertType.WARNING, PROJECT_LIST_EMPTY_HEAER, String.format(PROJECT_LIST_EMPTY_CONTENT, sonarHostname.getText()));
                    }
                    Platform.runLater(() -> projectTable.setItems(FXCollections.observableArrayList(projects)));
                } catch (SonarException e) {
                    showAlert(AlertType.ERROR, PROJECTS_LOAD_ERROR_HEADER, String.format(PROJECTS_LOAD_ERROR_CONTENT, sonarHostname.getText()));
                }
            } catch (SonarException e) {
                showAlert(AlertType.ERROR, PROXY_FORM_ERROR_HEADER, String.format(PROXY_FORM_ERROR_CONTENT, proxy.getText()));
                setIsDataOrCityLoading(false);
            }
            setIsDataOrCityLoading(false);
        }).start();
    }

    private void loadCodeCity() {
        // Load Projects in separate UI thread
        new Thread(() -> {
            try {
                // Disable Welcome Scren while City is loaded
                setIsDataOrCityLoading(true);

                // Get City Data from Sonar
                SonarProject selectedItem = (SonarProject) projectTable.getSelectionModel().getSelectedItem();
                CityModel cityData = sonarService.getCityData(selectedItem.getKey());

                // Solve problem with Solver
                final int STREET_SIZE = 6;
                Solver solver = new SolverMaximusHaeckius();
                CityNode resultNode = new SonarToStrategyConerter().convertCityToNode(cityData);
                solver.solveProblem(resultNode, STREET_SIZE);

                // Start City Application
                final CityStage cityStage = new CityStage(cityData, resultNode);

                Platform.runLater(cityStage::startStage);

                // Enable Welcome Screen again after loading City
                setIsDataOrCityLoading(false);
            } catch (SonarException e) {
                showAlert(AlertType.ERROR, CITY_BUILD_ERROR_HEADER, String.format(CITY_BUILD_ERROR_CONTENT, sonarHostname));
            }
        }).start();
    }

    private void setIsDataOrCityLoading(boolean isCityLoading) {
        this.isCityLoading = isCityLoading;
        isDataOrCityLoading.invalidate();
    }

    private void showAlert(final AlertType alertType, final String header, final String content) {
        Platform.runLater(() -> {
            Alert alert = new Alert(alertType);
            alert.setTitle(alertType.toString() + ": Code Of Gotham");
            alert.setHeaderText(header);
            alert.setContentText(content);
            alert.showAndWait();
        });
    }
}
