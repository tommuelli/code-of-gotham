package com.canoo.cog.ui.model;

import javafx.scene.shape.Box;


public class City extends Hood {

	public City(double inHeight, double inWidth, double inDepth, String info) {
		super(inHeight ,inWidth, inDepth, 0, 0, info);
	}
	protected Box createShape() {
		Box box =  new Box();
		box.setHeight(8);
		box.setTranslateY(4);
		return box;
	}
}
