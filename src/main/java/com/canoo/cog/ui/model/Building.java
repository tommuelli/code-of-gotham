package com.canoo.cog.ui.model;
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
