package com.canoo.cog.ui.model;

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



import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;

public class Hood extends AbstractElement<Box> {
	public Hood(double height, double inWidth, double inDepth, double xOffset, double zOffset, String info, int aLevel) {
		super(inWidth, xOffset, zOffset, height, info, aLevel);

		PhongMaterial phongMaterial = new PhongMaterial();
		phongMaterial.setDiffuseColor(new Painter().getPaintByLevel(super.getLevel()));
		getShape().setWidth(inWidth);
		getShape().setDepth(inDepth);
		getShape().setMaterial(phongMaterial);
	}

	@Override
	protected Box createShape() {
		Box box = new Box();
		box.setHeight(getHeight());
		return box;
	}

	public void addBuilding(Building inBuilding) {
		addElement(inBuilding);
	}

	public void addHood(Hood inHood) {
		addElement(inHood);
	}
}