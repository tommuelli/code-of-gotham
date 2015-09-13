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

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;

import java.util.Collections;
import java.util.List;

import com.canoo.cog.solver.CityNode;
import com.canoo.cog.solver.Solver;
import com.canoo.cog.solver.SolverMaximusHaeckius;
import com.canoo.cog.solver.SonarToStrategyConerter;
import com.canoo.cog.sonar.SonarException;
import com.canoo.cog.sonar.SonarService;
import com.canoo.cog.sonar.model.CityModel;
import com.canoo.cog.sonar.model.SonarProject;
import com.canoo.cog.sonar.model.SonarTimeMachineEntry;
import com.canoo.cog.ui.city.CityStage;

class WelcomeController {

	private static final String COG_PROXY_KEY = "cogProxy";
	private static final String COG_SONAR_HOST_KEY = "cogSonarHost";

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
	private TableView timeTable;

	@FXML
	private TableColumn timeColumn;

	private SonarService sonarService;
	private List<SonarProject> projects;

	WelcomeController(SonarService sonarService) {
		this.sonarService = sonarService;
	}

	public void init() {

		// Set default Sonar
		String sonarHostnameDefault = System.getProperty(COG_SONAR_HOST_KEY);
		if (sonarHostnameDefault != null && !sonarHostnameDefault.isEmpty()) {
			sonarHostname.setText(sonarHostnameDefault);
		} else {
			sonarHostname.setText("http://");
		}

		// Set proxy default
		String proxyDefault = System.getProperty(COG_PROXY_KEY);
		if (proxyDefault != null && !proxyDefault.isEmpty()) {
			proxy.setText(proxyDefault);
		}

		// Initialize the Welcome UI widgets
		keyColumn
				.setCellValueFactory(new PropertyValueFactory<SonarProject, String>(
						"key"));
		nameColumn
				.setCellValueFactory(new PropertyValueFactory<SonarProject, String>(
						"name"));
		versionColumn
				.setCellValueFactory(new PropertyValueFactory<SonarProject, String>(
						"version"));

		timeColumn
				.setCellValueFactory(new PropertyValueFactory<SonarTimeMachineEntry, String>(
						"time"));

		loadButton.setOnMouseClicked(event -> loadProjects());

		startButton.setOnMouseClicked(event -> loadCodeCity());

		passwordTextField.setOnKeyPressed(event -> {
			if (event.getCode() == KeyCode.ENTER) {
				loadProjects();
			}
		});

		projectTable.setRowFactory(tv -> {
			TableRow<String> row = new TableRow<>();
			row.setOnMouseClicked(event -> {
				if (event.getClickCount() == 2 && (!row.isEmpty())) {
					loadProjectsTimeMachine();
					loadCodeCity();
				} else {
					loadProjectsTimeMachine();
				}
			});

			return row;
		});
	}

	private void loadCodeCity() {

		int selectedIndex = timeTable.getSelectionModel().getSelectedIndex();

		try {
			// Get City Data from Sonar
			SonarProject selectedItem = (SonarProject) projectTable
					.getSelectionModel().getSelectedItem();
			CityModel cityData = sonarService
					.getCityData(selectedItem.getKey());

			if (0 != selectedIndex) {
				ObservableList<SonarTimeMachineEntry> items = timeTable
						.getItems();
				//SonarTimeMachineEntry from = items.get(selectedIndex);
				SonarTimeMachineEntry to = items.get(selectedIndex);
				sonarService.update(cityData, null, to);
			}

			// Solve problem with Solver
			final int STREET_SIZE = 6;
			Solver solver = new SolverMaximusHaeckius();
			CityNode resultNode = new SonarToStrategyConerter()
					.convertCityToNode(cityData);
			solver.solveProblem(resultNode, STREET_SIZE);

			// Start City Application
			final CityStage cityStage = new CityStage(cityData, resultNode);
			cityStage.startStage();

		} catch (SonarException e) {
			System.err.println(e);
		}

	}

	private void loadProjects() {
		new Thread(() -> {
			try {
				sonarService.setSonarSettings(sonarHostname.getText(),
						usernameField.getText(), passwordTextField.getText(),
						proxy.getText());
			} catch (SonarException e) {
				// show error message in sonar
			}
			try {
				projects = sonarService.getProjects();
				if (projects.isEmpty()) {
					// Show warning login might be wrong
				} else {
					Platform.runLater(() -> projectTable.setItems(FXCollections
							.observableArrayList(projects)));
				}
			} catch (SonarException e) {
				// show error message in Sonar
			}
		}).start();
	}

	private void loadProjectsTimeMachine() {
		new Thread(
				() -> {
					try {
						SonarProject selectedItem = (SonarProject) projectTable
								.getSelectionModel().getSelectedItem();
						List<SonarTimeMachineEntry> list = sonarService
								.getCityTimeMachine(selectedItem.getKey());

						Collections.sort(list);
						Platform.runLater(() -> fillTimemachineTable(list));

					} catch (SonarException e) {
						System.err.println(e);
					}
				}).start();
	}

	private void fillTimemachineTable(List<SonarTimeMachineEntry> list) {
		timeTable.setItems(FXCollections.observableArrayList(list));
		timeTable.getSelectionModel().select(0);
	}
}
