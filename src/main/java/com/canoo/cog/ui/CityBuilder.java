package com.canoo.cog.ui;

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
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
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
import com.canoo.cog.ui.model.text.Info;
import com.canoo.cog.ui.strategy.CityNode;
import com.canoo.cog.ui.strategy.LittleBetterSolverEver;
import com.canoo.cog.ui.strategy.Solver;
import com.canoo.cog.ui.strategy.SonarToStrategyConerter;

public class CityBuilder {

    private static final int STREET_SIZE = 6;

    private CityModel cityData;

    private String title = "";

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

    public CityBuilder(CityModel cityData) {
        this.cityData = cityData;
    }

    public Scene build() {

        // Solve problem for city
        Solver solver = new LittleBetterSolverEver();
        CityNode resultNode = new SonarToStrategyConerter().convertCityToNode(cityData);
        solver.solveProblem(resultNode, STREET_SIZE);

        // Start music
        Media media = new Media(getClass().getResource("Starship - We Built This City.mp3").toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.stop();
        mediaPlayer.setAutoPlay(true);

        // Create root group and scene
        Group root = new Group();

        root.getTransforms().addAll(rxBox, ryBox, rzBox);
        Scene scene = new Scene(root, 1500, 1000, true);
        scene.setFill(Color.BLACK);
        scene.setCamera(camera);

        // Add city to group and translate them according to model
        int hoodHeight = 1;
        int potentialHeight = resultNode.getSize() / 300;
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
        // Return scene with city
        return scene;
    }



	private void setTextProperties(Group root, City city) {
		double depthCorrection = -city.getLayoutBounds().getDepth()/2;
		double heightCorrectionTitle =  -city.getLayoutBounds().getHeight() / 3;
		double heightCorrectionInfo =  -city.getLayoutBounds().getHeight() / 4;
		
		double widthCorrectionTitle =  city.getLayoutBounds().getHeight() / 3 -10;
		double widthCorrectionInfo =  city.getLayoutBounds().getHeight() / 3;
		
		double tileFont = Math.max(city.getLayoutBounds().getDepth()/6, 42);
		double txtFont = Math.max(city.getLayoutBounds().getDepth()/12, 32);
		
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
//		titleGroup.setTranslateZ(depthCorrection);

		elementInfoGroup.setTranslateY(heightCorrectionInfo);
		elementInfoGroup.setTranslateX(widthCorrectionInfo);
//		elementInfoGroup.setTranslateZ(depthCorrection);
	}

  private void addAllNodesRecursively(Hood hood, List<CityNode> children) {
        for (CityNode node : children) {
            String info = "File: " + node.getModel().getName() + "\nFunctions: " + node.getModel().getFunctions() + "\nLines of Code: "
                    + node.getModel().getLinesOfCode() + "\nComplexity: " + node.getModel().getComplexity() + "\nCoverage: " + node.getModel().getCoverage()
                    + "\nTests: " + node.getModel().getTests();

            if (node.isLeaf()) {

                double height = node.getModel().getLinesOfCode() / 2;

                hood.addBuilding(new Building(node.getSize(), height, node.getX(), node.getY(), hood.getColor(), info)); // Y==Z
            } else {
                double incrementedHeight = hood.getHeight();
                Hood childHood = new Hood(incrementedHeight, node.getSize(), node.getSize(), node.getX(), node.getY(), info);
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
            System.out.println(event.getDeltaY());
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
