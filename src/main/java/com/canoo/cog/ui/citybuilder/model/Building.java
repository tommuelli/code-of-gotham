package com.canoo.cog.ui.citybuilder.model;


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

import com.canoo.cog.ui.citybuilder.model.style.CityStyle;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.beans.property.*;

import javafx.scene.image.Image;

import com.canoo.cog.sonar.model.Model;

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

		if (CityStyle.Style.GOTHAM.name().equals(styleProperty.getValue())) {
			phongMaterial.setDiffuseColor(new Painter().getPaintByLevel(super
					.getLevel()));
		} else if (CityStyle.Style.TEST_COVERAGE.name().equals(styleProperty.getValue())) {
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

		} else if (CityStyle.Style.TECH_DEBT.name().equals(styleProperty.getValue())) {
			phongMaterial.setDiffuseColor(new Painter().getPaintForSqualeIndex(model.getSqaleIndex(), model.getTopSqualeIndex()));
		} else if (CityStyle.Style.TECH_DEBT_PER_LINE.name().equals(styleProperty.getValue())) {
			phongMaterial.setDiffuseColor(new Painter()
			.getPaintForSqaleIndexPerLineOfCode(
					model.getSqualeIndexPerLineOfCode(),
					model.getTopSqualeIndexPerLineOfCode()));
		} else if (CityStyle.Style.COMPLEXITY_PER_LINE.name().equals(styleProperty.getValue())) {
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
