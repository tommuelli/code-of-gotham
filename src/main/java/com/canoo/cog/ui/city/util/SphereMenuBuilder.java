package com.canoo.cog.ui.city.util;

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


import com.canoo.cog.ui.city.model.style.CityStyle;
import com.canoo.cog.ui.city.model.style.CityStyle.Style;
import javafx.scene.Group;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;


public class SphereMenuBuilder {


    private double size;
    private double correction;

    public Group build(double depthCorrection, double cityWidth) {
        size = cityWidth / 100;
        correction = size / 4;


        Group g = new Group();
        Sphere gotham = createSphere(Style.GOTHAM);
        g.getChildren().add(gotham);

        Sphere techDebt = createSphere(Style.TECH_DEBT);
        techDebt.setTranslateX(-techDebt.getRadius() * 2);
        g.getChildren().add(techDebt);

        Sphere techDebtPerLine = createSphere(Style.TECH_DEBT_PER_LINE);
        techDebtPerLine.setTranslateX(-techDebtPerLine.getRadius() * 4);
        g.getChildren().add(techDebtPerLine);

        Sphere tc = createSphere(Style.TEST_COVERAGE);
        tc.setTranslateX(tc.getRadius() * 2);
        g.getChildren().add(tc);

        Sphere complexity = createSphere(Style.COMPLEXITY_PER_LINE);
        g.getChildren().add(complexity);
        complexity.setTranslateX(complexity.getRadius() * 4);

        g.setTranslateZ(depthCorrection);
        g.setTranslateY(40);
        return g;
    }

    private Sphere createSphere(Style aStyle) {
        Sphere s = new Sphere(size);

        s.setUserData(aStyle);
        PhongMaterial phongMaterial = new PhongMaterial();
        phongMaterial.setDiffuseMap(CityStyle.getStyleImage(aStyle));
        phongMaterial.setDiffuseColor(CityStyle.getStyleColor(aStyle));
        s.setMaterial(phongMaterial);
        s.setOnMouseClicked(event -> {
            updateCityStyle(((Style) s.getUserData()).name());
        });
        s.setOnMouseEntered(event -> {
            s.setTranslateZ(-correction);
            s.setRadius(size + correction);
        });
        s.setOnMouseExited(event -> {
            s.setTranslateZ(correction);
            s.setRadius(size);
        });
        return s;
    }

    public void updateCityStyle(String style) {
        CityStyle.setStyle(style);
    }
}
