package com.canoo.cog.ui.model;

import com.canoo.cog.ui.model.text.Info;

import javafx.scene.Group;
import javafx.scene.control.Tooltip;
import javafx.scene.shape.Shape3D;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public abstract class AbstractElement<T extends Shape3D> extends Group {

	private T shape;
	private double width;
	private double height;
	private double xOffset;
	private double zOffset;

	private Tooltip tooltip = new Tooltip();
	private String info;

	
	
	protected abstract T createShape();

	public AbstractElement(double inWidth, double xOffset, double zOffset, double inHeight, String info) {
		
		this.height = inHeight;
		this.width = inWidth;
		this.xOffset = xOffset;
		this.zOffset = zOffset;
		this.info = info;
		shape = createShape();
		Tooltip.install(shape, tooltip);
	
		tooltip.setText(info);
		
		
		getChildren().add(shape);
		shape.setOnMouseClicked(event -> {
		
            	Info.update(info);
			
           });
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
