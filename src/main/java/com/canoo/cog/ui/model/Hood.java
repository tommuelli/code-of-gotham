package com.canoo.cog.ui.model;


import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;

public class Hood extends AbstractElement<Box> {

	private Color color  = new Painter().paint();
	
	public Color getColor() {
		return color;
	}

	public Hood(double height, double inWidth, double inDepth, double xOffset, double zOffset, String info) {
		super(inWidth, xOffset, zOffset, height, info);
		
		PhongMaterial phongMaterial = new PhongMaterial();
		phongMaterial.setDiffuseColor(color);
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