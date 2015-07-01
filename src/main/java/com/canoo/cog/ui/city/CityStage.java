package com.canoo.cog.ui.city;

import com.canoo.cog.solver.CityNode;
import com.canoo.cog.sonar.model.CityModel;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

public class CityStage {

    private final CityModel cityData;
    private final CityNode resultNode;

    public CityStage(CityModel cityData, CityNode resultNode) {
        this.cityData = cityData;
        this.resultNode = resultNode;
    }

    public void startStage() {
        // Start music
        Media media = new Media(getClass().getResource("song.mp3").toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.stop();
        mediaPlayer.setAutoPlay(true);

        // Build city
        CityBuilder cityBuilder = new CityBuilder();
        Stage stage = new Stage();
        Scene scene = cityBuilder.buildScene(resultNode, cityData.getName());

        scene.addEventHandler(KeyEvent.KEY_PRESSED,
                keyEvent -> {
                    if (keyEvent.getCode() == KeyCode.F11) {
                        stage.setFullScreen(!stage.isFullScreen());
                    }
                    ;
                });


        stage.setScene(scene);
        stage.setFullScreen(false);
        stage.setOnCloseRequest(we -> mediaPlayer.stop());
        stage.show();
    }
}
