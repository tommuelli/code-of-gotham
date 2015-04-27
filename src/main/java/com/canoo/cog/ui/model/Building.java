package com.canoo.cog.ui.model;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.beans.property.*;

import java.util.Random;

import com.canoo.cog.sonar.model.Model;
import com.canoo.cog.ui.model.style.CityStyle;
import com.canoo.cog.ui.model.style.CityStyle.Style;

public class Building extends AbstractElement<Box> {

	private SimpleStringProperty styleProperty = new SimpleStringProperty();
	private int imgId;
	private Model model;
	private Painter painter = new Painter();

	public Building(double inWidth, double inHeight, double xOffset,
			double zOffset, String info, Model inModel, int aLevel) {
		super(inWidth, xOffset, zOffset, inHeight, info, aLevel);
		getShape().setWidth(inWidth);
		getShape().setHeight(getHeight());
		getShape().setDepth(inWidth); // same as width
		model = inModel;
		update();
		styleProperty.bind(CityStyle.getStyleProperty());
		styleProperty.addListener(listener -> update());
	}

	@Override
	protected void update() {
		Image image = null;
		PhongMaterial phongMaterial = new PhongMaterial();

		if (Style.GOTHAM.name().equals(styleProperty.getValue())) {
			phongMaterial.setDiffuseColor(new Painter().getPaintByLevel(super
					.getLevel()));
		} else if (Style.TEST_COVERAGE.name().equals(styleProperty.getValue())) {
			if (model.getCoverage() != null) {
				try {
					double coverage = Double.valueOf(model.getCoverage());
					phongMaterial.setDiffuseColor(painter
							.paintForCoverage(coverage));
				} catch (NumberFormatException e) {
					phongMaterial.setSpecularColor(new Painter().paint());
				}
			} else {
				phongMaterial.setSpecularColor(new Painter().paint());
			}

		} else if (Style.TECH_DEBT.name().equals(styleProperty.getValue())) {
			phongMaterial.setDiffuseColor(new Painter().getPaintForSqualeIndex(model.getSqaleIndex(), model.getTopSqualeIndex()));
		} else if (Style.TECH_DEBT_PER_LINE.name().equals(styleProperty.getValue())) {
			phongMaterial.setDiffuseColor(new Painter()
			.getPaintForSqaleIndexPerLineOfCode(
					model.getSqualeIndexPerLineOfCode(),
					model.getTopSqualeIndexPerLineOfCode()));
		} else if (Style.COMPLEXITY_PER_LINE.name().equals(styleProperty.getValue())) {
			phongMaterial.setDiffuseColor(new Painter()
					.getPaintForComplexityPerLineOfCode(
							model.getComplexityPerLineOfCode(),
							model.getTopComplexityPerLineOfCode()));
		}
		getShape().setMaterial(phongMaterial);
	}

	@Override
	protected Box createShape() {
		return new Box();
	}

}
