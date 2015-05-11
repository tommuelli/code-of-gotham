package com.canoo.cog.ui;

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

import com.canoo.cog.sonar.model.CityModel;
import com.canoo.cog.ui.model.Building;
import com.canoo.cog.ui.model.City;
import com.canoo.cog.ui.model.Hood;
import com.canoo.cog.ui.strategy.CityNode;
import com.canoo.cog.ui.strategy.LittleBetterSolverEver;
import com.canoo.cog.ui.strategy.Solver;
import com.canoo.cog.ui.strategy.SonarToStrategyConerter;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

import com.canoo.cog.sonar.model.CityModel;
import com.canoo.cog.ui.model.AbstractElement;
import com.canoo.cog.ui.model.Building;
import com.canoo.cog.ui.model.City;
import com.canoo.cog.ui.model.Hood;
import com.canoo.cog.ui.model.style.CityStyle;
import com.canoo.cog.ui.model.style.CityStyle.Style;
import com.canoo.cog.ui.model.text.Info;
import com.canoo.cog.ui.strategy.CityNode;
import com.canoo.cog.ui.strategy.LittleBetterSolverEver;
import com.canoo.cog.ui.strategy.Solver;
import com.canoo.cog.ui.strategy.SonarToStrategyConerter;

public class CityBuilder {

    private static final int HEIGHT_DIVISOR = 3;

	private static final int SCENE_HEIGHT = 1000;

	private static final int SCENE_WIDTH = 1500;

	private static final int STREET_SIZE = 6;
	
	public static final Style INITIAL_STYLE =Style.GOTHAM; 

    private CityModel cityData;

    private String title = "";

    private SimpleStringProperty styleProperty = new SimpleStringProperty();
    
    public String getTitle() {
        return title;
    }

    final PerspectiveCamera camera = new PerspectiveCamera(true);
    final Xform cameraXform = new Xform();
    final Xform cameraXform2 = new Xform();
    final Xform cameraXform3 = new Xform();
    private double CAMERA_INITIAL_DISTANCE = -1200;
    private final double CAMERA_INITIAL_X_ANGLE = -30;
    private final double CAMERA_INITIAL_Y_ANGLE = 30.0;
    private final double CAMERA_NEAR_CLIP = 0.1;
    private final double CAMERA_FAR_CLIP = 10000.0;
    private final double CONTROL_MULTIPLIER = 0.1;
    private final double SHIFT_MULTIPLIER = 10.0;
    private final double MOUSE_SPEED = 0.1;
    private final double ROTATION_SPEED = 1.0;
    private final double TRACK_SPEED = 0.3;

    double mousePosX;
    double mousePosY;
    double mouseOldX;
    double mouseOldY;
    double mouseDeltaX;
    double mouseDeltaY;
    Rotate rxBox = new Rotate(0, 0, 0, 0, Rotate.X_AXIS);
    Rotate ryBox = new Rotate(0, 0, 0, 0, Rotate.Y_AXIS);
    Rotate rzBox = new Rotate(0, 0, 0, 0, Rotate.Z_AXIS);

	private Scene scene;

    public CityBuilder(CityModel cityData) {
        this.cityData = cityData;
    }

    public Scene build() { 	
        // Solve problem for city
        Solver solver = new LittleBetterSolverEver();
        CityNode resultNode = new SonarToStrategyConerter().convertCityToNode(cityData);
        solver.solveProblem(resultNode, STREET_SIZE);

        // Start music
        Media media = new Media(getClass().getResource("We Built This City Cover.mp3").toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.stop();
        mediaPlayer.setAutoPlay(true);

        // Create root group and scene
        Group root = new Group();

        root.getTransforms().addAll(rxBox, ryBox, rzBox);
        scene = new Scene(root, SCENE_WIDTH, SCENE_HEIGHT, true);
        
        scene.setCamera(camera);

        // Add city to group and translate them according to model
        int hoodHeight = 1;
        int potentialHeight = resultNode.getSize() / 400;
        if(potentialHeight > 1) {
            hoodHeight = potentialHeight;
        }
        City city = new City(hoodHeight,resultNode.getSize(), resultNode.getSize(), cityData.getName());
        addAllNodesRecursively(city, resultNode.getChildren());

        LayoutManager layoutManager = new LayoutManager();
        layoutManager.moveCityChildrenBackToCity(city);
        layoutManager.correctYDirection(city);
        layoutManager.setRelativeOffset(city);
        layoutManager.moveCityToChildren(city);

        city.setTranslateX(city.getWidth() / 2);
        city.setTranslateZ(city.getWidth() / 2);
        city.setTranslateY(100);

        title = city.getInfo();
        root.getChildren().add(city);

        // Add mouse events
        setMouseEventsToScene(scene);

        // Timeline for Intro
        buildCamera(root, city);
        Timeline timeline = new Timeline();
        timeline.setCycleCount(1);
        KeyValue kv = new KeyValue(camera.translateZProperty(), CAMERA_INITIAL_DISTANCE);
        KeyFrame kf = new KeyFrame(Duration.millis(5000), kv);
        timeline.getKeyFrames().add(kf);
        timeline.play();

        setTextProperties(root, city);
        
        
        
    	// initial style
    	styleProperty.bind(CityStyle.getStyleProperty());
    	styleProperty.addListener(listener -> setBackgroundColor());
    	CityStyle.setStyle(INITIAL_STYLE);
        return scene;
    }

	private void setBackgroundColor() {
		scene.setFill(CityStyle.getBackgroundColor(styleProperty.getValue()));
	}



	private void setTextProperties(Group root, City city) {
		double depthCorrection = -city.getLayoutBounds().getDepth()/2;
		
		double heightCorrectionTitle =  -(SCENE_HEIGHT / 2 -20);
		double heightCorrectionInfo =  -SCENE_HEIGHT / 4;
		
		double widthCorrectionTitle =  SCENE_WIDTH / 3 -10;
		double widthCorrectionInfo =  SCENE_WIDTH / 3;
		
		double tileFont = Math.min(city.getLayoutBounds().getDepth()/6, 42);
		double txtFont = Math.min(city.getLayoutBounds().getDepth()/12, 18);
		
		Text titleTxt = new Text(title);
		titleTxt.setFont(Font.font("Helvetica", FontWeight.BOLD, tileFont));
		titleTxt.setFill(Color.WHITESMOKE);

		Text elementInfoTxt = new Text();
		elementInfoTxt.setFont(Font.font("Helvetica", FontWeight.BOLD, txtFont));
		elementInfoTxt.setFill(Color.WHITESMOKE);
		elementInfoTxt.textProperty().bind(Info.getInfoProperty());

		Group titleGroup = new Group();
		titleGroup.getChildren().add(titleTxt);

		Group elementInfoGroup = new Group();
		elementInfoGroup.getChildren().add(elementInfoTxt);

		root.getChildren().add(titleGroup);
		root.getChildren().add(elementInfoGroup);

		titleGroup.setTranslateY(heightCorrectionTitle);
		titleGroup.setTranslateX(widthCorrectionTitle);
		titleGroup.setTranslateZ(depthCorrection);

		elementInfoGroup.setTranslateY(heightCorrectionInfo);
		elementInfoGroup.setTranslateX(widthCorrectionInfo);
		elementInfoGroup.setTranslateZ(depthCorrection);
		
		root.getChildren().addAll(new SphereMenuBuilder().build(depthCorrection, city.getWidth()));
		
		
	}

  private void addAllNodesRecursively(Hood hood, List<CityNode> children) {
        for (CityNode node : children) {

        	String info = node.getModel().getInfo();
            if (node.isLeaf()) {
                double height = node.getModel().getLinesOfCode() / HEIGHT_DIVISOR;
                hood.addBuilding(new Building(node.getSize(), height, node.getX(), node.getY(), info, node.getModel(), hood.getLevel()+1)); // Y==Z
            } else {
                double incrementedHeight = hood.getHeight();
                Hood childHood = new Hood(incrementedHeight, node.getSize(), node.getSize(), node.getX(), node.getY(), info, hood.getLevel()+1);
                hood.addHood(childHood);
                addAllNodesRecursively(childHood, node.getChildren());
            }
        }
    }

 
  
    private void buildCamera(Group root, City city) {
        System.out.println("buildCamera()");
        root.getChildren().add(cameraXform);
        cameraXform.getChildren().add(cameraXform2);
        cameraXform2.getChildren().add(cameraXform3);
        cameraXform3.getChildren().add(camera);
//        cameraXform3.setRotateZ(0.0);
//        cameraXform3.setRotateY(0.0);
//        cameraXform3.setRotateX(0.0);

        camera.setNearClip(CAMERA_NEAR_CLIP);
        camera.setFarClip(CAMERA_FAR_CLIP);
        // camera.setTranslateX(city.getWidth()/2);
        // camera.setTranslateZ(city.getWidth()/2);
        cameraXform.ry.setAngle(CAMERA_INITIAL_Y_ANGLE);
        cameraXform.rx.setAngle(CAMERA_INITIAL_X_ANGLE);
    }

    private void setMouseEventsToScene(Scene scene) {
        scene.setOnMousePressed(me -> {
            mousePosX = me.getSceneX();
            mousePosY = me.getSceneY();
            mouseOldX = me.getSceneX();
            mouseOldY = me.getSceneY();
        });

        scene.setOnScroll(event -> {
            if (event.getDeltaY() < 0) {
                CAMERA_INITIAL_DISTANCE *= 1.1;
            } else {
                CAMERA_INITIAL_DISTANCE *= 0.9;
            }
            camera.setTranslateZ(CAMERA_INITIAL_DISTANCE);
        });

        scene.setOnMouseDragged(me -> {
            mouseOldX = mousePosX;
            mouseOldY = mousePosY;
            mousePosX = me.getSceneX();
            mousePosY = me.getSceneY();
            mouseDeltaX = (mousePosX - mouseOldX);
            mouseDeltaY = (mousePosY - mouseOldY);

            double modifier = 1.0;

            if (me.isControlDown()) {
                modifier = CONTROL_MULTIPLIER;
            }
            if (me.isShiftDown()) {
                modifier = SHIFT_MULTIPLIER;
            }
            if (me.isPrimaryButtonDown()) {
                cameraXform.ry.setAngle(cameraXform.ry.getAngle() - mouseDeltaX * MOUSE_SPEED * modifier * ROTATION_SPEED);
                cameraXform.rx.setAngle(cameraXform.rx.getAngle() + mouseDeltaY * MOUSE_SPEED * modifier * ROTATION_SPEED);
            } else if (me.isSecondaryButtonDown()) {
                double z = camera.getTranslateZ();
                double newZ = z + mouseDeltaX * MOUSE_SPEED * modifier;
                camera.setTranslateZ(newZ);
            } else if (me.isMiddleButtonDown()) {
                cameraXform2.t.setX(cameraXform2.t.getX() + mouseDeltaX * MOUSE_SPEED * modifier * TRACK_SPEED);
                cameraXform2.t.setY(cameraXform2.t.getY() + mouseDeltaY * MOUSE_SPEED * modifier * TRACK_SPEED);
            }
        });
    }
}
