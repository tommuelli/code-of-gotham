package com.canoo.cog;

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
