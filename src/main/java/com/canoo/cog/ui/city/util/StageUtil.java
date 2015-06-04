package com.canoo.cog.ui.city.util;

import com.canoo.cog.ui.city.CityBuilder;
import com.canoo.cog.ui.city.model.City;
import com.canoo.cog.ui.city.model.text.Info;
import javafx.scene.Group;
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
}
