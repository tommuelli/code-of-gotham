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
