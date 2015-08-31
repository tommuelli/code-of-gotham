package com.canoo.cog.ui.welcome;

import java.io.IOException;

import com.canoo.cog.sonar.SonarService;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class WelcomeService {
    private final SonarService sonarService;
    private Stage stage;

    public WelcomeService(Stage stage, SonarService sonarService) {
        this.stage = stage;
        this.sonarService = sonarService;
    }

    public void startScene() throws IOException {

        // Create Scene for Welcome Screen
        FXMLLoader loader = new FXMLLoader(getClass().getResource("welcome.fxml"));
        WelcomeController welcomeController = new WelcomeController(sonarService);
        loader.setController(welcomeController);
        Pane welcomePane = loader.load();
        WelcomeController controller = loader.getController();
        controller.init();
        Scene welcomeScene = new Scene(welcomePane);

        // Initialize JavaFx Welcome Stage
        stage.setTitle("Code of Gotham");
        stage.setScene(welcomeScene);
        stage.show();
    }
}
