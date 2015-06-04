package com.canoo.cog.ui.city.util;

import java.util.List;

import com.canoo.cog.solver.CityNode;
import com.canoo.cog.ui.city.model.Building;
import com.canoo.cog.ui.city.model.City;
import com.canoo.cog.ui.city.model.Hood;
import com.canoo.cog.ui.city.model.style.CityStyle;
import com.canoo.cog.ui.city.model.text.Info;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class StageUtil {
    private static final int SCENE_HEIGHT = 1000;
    private static final int SCENE_WIDTH = 1500;

    public void setTextProperties(Group root, City city, String title) {
        double depthCorrection = -city.getLayoutBounds().getDepth()/2;

        double heightCorrectionTitle =  -(SCENE_HEIGHT / 2 -20);
        double heightCorrectionInfo =  -SCENE_HEIGHT / 4;

        double widthCorrectionTitle =  SCENE_WIDTH / 3 -10;
        double widthCorrectionInfo =  SCENE_WIDTH / 3;

        double tileFont = Math.min(city.getLayoutBounds().getDepth() / 6, 42);
        double txtFont = Math.min(city.getLayoutBounds().getDepth() / 12, 18);

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

        root.getChildren().addAll(new SphereMenuBuilder().build(depthCorrection, city.getWidth()));
    }

    private SimpleStringProperty styleProperty = new SimpleStringProperty();

    public static final CityStyle.Style INITIAL_STYLE = CityStyle.Style.GOTHAM;

    public void setStyle(Scene scene) {
        // initial style
        styleProperty.bind(CityStyle.getStyleProperty());
        styleProperty.addListener(listener -> setBackgroundColor(scene));
        CityStyle.setStyle(INITIAL_STYLE);
    }

    private void setBackgroundColor(Scene scene) {
        scene.setFill(CityStyle.getBackgroundColor(styleProperty.getValue()));
    }

    public City createCity(CityNode resultNode, String cityName) {
        // Add city to group and translate them according to model
        int hoodHeight = 1;
        int potentialHeight = resultNode.getSize() / 400;
        if (potentialHeight > 1) {
            hoodHeight = potentialHeight;
        }
        City city = new City(hoodHeight, resultNode.getSize(), resultNode.getSize(), cityName);
        addAllNodesRecursively(city, resultNode.getChildren());

        LayoutManager layoutManager = new LayoutManager();
        layoutManager.moveCityChildrenBackToCity(city);
        layoutManager.correctYDirection(city);
        layoutManager.setRelativeOffset(city);
        layoutManager.moveCityToChildren(city);

        city.setTranslateX(city.getWidth() / 2);
        city.setTranslateZ(city.getWidth() / 2);
        city.setTranslateY(100);
        return city;
    }

    private static final int HEIGHT_DIVISOR = 3;

    private void addAllNodesRecursively(Hood hood, List<CityNode> children) {
        for (CityNode node : children) {

            String info = node.getModel().getInfo();
            if (node.isLeaf()) {
                double height = node.getModel().getLinesOfCode() / HEIGHT_DIVISOR;
                hood.addBuilding(new Building(node.getSize(), height, node.getX(), node.getY(), info, node.getModel(), hood.getLevel() + 1)); // Y==Z
            } else {
                double incrementedHeight = hood.getHeight();
                Hood childHood = new Hood(incrementedHeight, node.getSize(), node.getSize(), node.getX(), node.getY(), info, hood.getLevel() + 1);
                hood.addHood(childHood);
                addAllNodesRecursively(childHood, node.getChildren());
            }
        }
    }
}
