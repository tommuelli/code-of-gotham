package com.canoo.cog.ui;

import com.canoo.cog.ui.model.AbstractElement;

import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.geometry.Point3D;
import javafx.scene.Node;
import javafx.scene.shape.Box;
import javafx.scene.shape.Shape3D;

public class OriginalLayoutManager2 {

	private static final double STREET_SIZE = 20;

	@SuppressWarnings("unchecked")
	public void doLayout(AbstractElement<? extends Box> inParent) {
		Point3D nextEdgeZ = new Point3D(0d, 0d, 0d);
		Point3D nextEdgeX = new Point3D(0d, 0d, 0d);
		Point3D nextFreeXonZAxis = new Point3D(0d, 0d, 0d);
		Point3D nextFreeZonXAxis = new Point3D(0d, 0d, 0d);
		
		for (Node node : inParent.getChildren()) {
			Node shape = null;
			double nodeX = 0d;
			double nodeZ = 0d;
			
			if (node instanceof Shape3D) {
				shape = node;
			}

			if (node instanceof AbstractElement) {
				AbstractElement<? extends Box> child = (AbstractElement<? extends Box>) node;
				shape = child.getShape();
				double childWidthAndStreet = child.getShape().getLayoutBounds().getWidth() + STREET_SIZE;
				double childDepthAndStreet = child.getShape().getLayoutBounds().getDepth() + STREET_SIZE;

				boolean needsResetXAxis = needsResetXAxis(nextEdgeX, nextFreeXonZAxis, child);
				boolean needsResetZAxis = needsResetZAxis(nextEdgeZ, nextFreeZonXAxis, child);
				
				boolean nextFreeXPointsIntersect = intersects(nextFreeXonZAxis, nextFreeZonXAxis, childWidthAndStreet, childDepthAndStreet);

				boolean nextEdgeXSmallerThanNextEdgeZ = nextEdgeX.getX() < nextEdgeZ.getZ();
				
				if (!needsResetXAxis) {
					nodeX = nextFreeXonZAxis.getX();
					nodeZ = nextFreeXonZAxis.getZ();
					
					boolean needsResetZAxisXContext = needsResetZAxis(nextEdgeZ, nextFreeXonZAxis, child);
					if (needsResetZAxisXContext) {
						nextEdgeZ = nextEdgeZ.add(0d, 0d, childDepthAndStreet);
					}
					nextFreeXonZAxis = nextFreeXonZAxis.add(childWidthAndStreet, 0d, 0d);
					
				} else if (!needsResetZAxis && !nextFreeXPointsIntersect) {
					nodeX = nextFreeZonXAxis.getX();
					nodeZ = nextFreeZonXAxis.getZ();
					
					boolean needsResetXAxisZContext = needsResetXAxis(nextEdgeX, nextFreeZonXAxis, child);
					if (needsResetXAxisZContext) {
						nextEdgeX = nextEdgeX.add(childWidthAndStreet, 0d, 0d);
					}
					nextFreeZonXAxis = nextFreeZonXAxis.add(0d, 0d, childDepthAndStreet);
					
				} else {
					if(nextEdgeXSmallerThanNextEdgeZ) {
						nodeX = nextEdgeX.getX();
					} else {
						nodeZ = nextEdgeZ.getZ();
					}

					if (nextFreeXPointsIntersect && nextEdgeXSmallerThanNextEdgeZ) {
						nextFreeZonXAxis = new Point3D(nextEdgeX.getX(), nextEdgeX.getY(), nextEdgeX.getZ());
						nextFreeZonXAxis = nextFreeZonXAxis.add(0d, 0d, childDepthAndStreet);
						nextEdgeX = nextEdgeX.add(childWidthAndStreet, 0d, 0d);
					} else if(needsResetXAxis) {
						nextEdgeX = nextEdgeX.add(childWidthAndStreet, 0d, 0d);
						nextFreeZonXAxis = new Point3D(nextEdgeX.getX(), nextEdgeX.getY(), nextEdgeX.getZ());
					}
					
					if(needsResetZAxis) {
						nextEdgeZ = nextEdgeZ.add(0d, 0d, childDepthAndStreet);
						nextFreeXonZAxis = new Point3D(nextEdgeZ.getX(), nextEdgeZ.getY(), nextEdgeZ.getZ());
					} else if(nextFreeXPointsIntersect && !nextEdgeXSmallerThanNextEdgeZ) {
						nextFreeXonZAxis = new Point3D(nextEdgeZ.getX(), nextEdgeZ.getY(), nextEdgeZ.getZ());
						nextFreeXonZAxis = nextFreeXonZAxis.add(childWidthAndStreet, 0d, 0d);
						nextEdgeZ = nextEdgeZ.add(0d, 0d, childDepthAndStreet);
					}
				}

				Bounds parentShapeBounds = inParent.getShape().getLayoutBounds();
				Bounds childShapeBounds = shape.getLayoutBounds();
				
				node.setTranslateX(childShapeBounds.getWidth()/2 - parentShapeBounds.getWidth()/2 + nodeX);
				node.setTranslateY(-childShapeBounds.getHeight()/2 - parentShapeBounds.getHeight()/2 );
				node.setTranslateZ(-childShapeBounds.getDepth()/2 + parentShapeBounds.getDepth()/2 - nodeZ);
			}
		}

	}

	private boolean needsResetXAxis(Point3D nextEdge, Point3D nextFreeOnAxis, AbstractElement<? extends Box> child) {
		return nextEdge.getX() - nextFreeOnAxis.getX() < child.getShape().getLayoutBounds().getWidth() + STREET_SIZE;
	}

	private boolean needsResetZAxis(Point3D nextEdge, Point3D nextFreeOnAxis, AbstractElement<? extends Box> child) {
		return nextEdge.getZ() - nextFreeOnAxis.getZ() < child.getShape().getLayoutBounds().getDepth() + STREET_SIZE;
	}

	private boolean intersects(Point3D checkPoint, Point3D childStartPoint, double childWidth, double childDepth) {
		BoundingBox boundingBox = new BoundingBox(childStartPoint.getX(), childStartPoint.getZ(), childWidth, childDepth);
		Point2D checkPoint2D = new Point2D(checkPoint.getX(), checkPoint.getZ());
		return boundingBox.contains(checkPoint2D);
	}
	
}
