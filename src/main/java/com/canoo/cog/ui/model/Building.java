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

public class Building extends AbstractElement<Box> {

	private Color color;
	
	public Building(double inWidth, double inHeight, double xOffset, double zOffset, Color color, String info) {
		super(inWidth, xOffset, zOffset, inHeight, info);
		this.color = color;
		getShape().setWidth(inWidth);
        getShape().setHeight(getHeight());
        getShape().setDepth(inWidth); // same as width
		PhongMaterial phongMaterial = new PhongMaterial();
		phongMaterial.setDiffuseColor(color);
		getShape().setMaterial(phongMaterial);
    }

    @Override
	protected Box createShape() {
		return new Box();
	}

    

    
}
