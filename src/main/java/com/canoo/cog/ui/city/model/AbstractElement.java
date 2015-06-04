package com.canoo.cog.ui.city.model;

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


import com.canoo.cog.ui.city.model.text.Info;

import javafx.scene.Group;
import javafx.scene.control.Tooltip;
import javafx.scene.shape.Shape3D;

public abstract class AbstractElement<T extends Shape3D> extends Group {

	private T shape;
	private double width;
	private double height;
	private double xOffset;
	private double zOffset;

	private Tooltip tooltip = new Tooltip();
	private String info;
	private int level;

	
	
	protected abstract T createShape();

	public AbstractElement(double inWidth, double xOffset, double zOffset, double inHeight, String info, int aLevlel) {
		
		this.height = inHeight;
		this.width = inWidth;
		this.xOffset = xOffset;
		this.zOffset = zOffset;
		this.info = info;
		this.level = aLevlel;
		shape = createShape();
		Tooltip.install(shape, tooltip);
	
		tooltip.setText(info);
		
		
		getChildren().add(shape);
		shape.setOnMouseClicked(event -> {
            	Info.update(info);
           });
	}

	
	public int getLevel() {
		return level;
	}

	protected void update(){
		
	}
	
	public T getShape() {
		return shape;
	}

	public String getInfo() {
		return info; 
	}

	public void addElement(AbstractElement<?> inElement) {
		getChildren().add(inElement);
	}

	public String toString() {

		return this.getClass().getSimpleName() + " bounds :" + this.getLayoutBounds() + " bounds in local :" + this.getBoundsInLocal()
				+ " bounds in parent :" + this.getBoundsInParent() + " xOffset:" + xOffset + " zOffset: " + zOffset;
	}

	public double getxOffset() {
		return xOffset;
	}

	public double getzOffset() {
		return zOffset;
	}

	public double getWidth() {
		return width;
	}

	public double getHeight() {
		return height;
	}
}
