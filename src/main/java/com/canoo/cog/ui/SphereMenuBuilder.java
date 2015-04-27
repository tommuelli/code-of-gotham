package com.canoo.cog.ui;

import javafx.scene.Group;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;

import com.canoo.cog.ui.model.style.CityStyle;
import com.canoo.cog.ui.model.style.CityStyle.Style;


public class SphereMenuBuilder {

	


	
	private double size;
	private double correction;

	public Group build(double depthCorrection, double cityWidth){
		size = cityWidth /100;
		correction = size /4;
				
		
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
		tc.setTranslateX(tc.getRadius()*2);
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
    		updateCityStyle(((Style)s.getUserData()).name());
    		
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
	
	public void updateCityStyle(String style){
		CityStyle.setStyle(style);
	}
}
