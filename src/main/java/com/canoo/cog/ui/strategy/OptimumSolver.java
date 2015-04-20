package com.canoo.cog.ui.strategy;

import javafx.geometry.BoundingBox;
import javafx.geometry.Point2D;
import javafx.geometry.Point3D;

public class OptimumSolver implements Solver {
	
	private int streetSize;

	@Override
	public void solveProblem(CityNode node, int streetSize) {
		this.streetSize = streetSize;
		
        if(node.isLeaf()) {
            return;
        }

        for (CityNode childNode : node.getChildren()) {
            solveProblem(childNode, streetSize);
        }

        doLayout(node, streetSize);
	}

	public void doLayout(CityNode parentNode, int streetSize) {
		Point3D nextEdgeZ = new Point3D(0d, 0d, 0d);
		Point3D nextEdgeX = new Point3D(0d, 0d, 0d);
		Point3D nextFreeXonZAxis = new Point3D(0d, 0d, 0d);
		Point3D nextFreeZonXAxis = new Point3D(0d, 0d, 0d);

		for (CityNode childNode : parentNode.getChildren()) {
			double nodeX = 0d;
			double nodeZ = 0d;

			double childSize = childNode.getSize() + streetSize;

			boolean needsResetXAxis = needsResetXAxis(nextEdgeX, nextFreeXonZAxis, childNode);
			boolean needsResetZAxis = needsResetZAxis(nextEdgeZ, nextFreeZonXAxis, childNode);

			boolean nextFreeXPointsIntersect = intersects(nextFreeXonZAxis, nextFreeZonXAxis, childSize);

			boolean nextEdgeXSmallerThanNextEdgeZ = nextEdgeX.getX() < nextEdgeZ.getZ();

			if (!needsResetXAxis) {
				nodeX = nextFreeXonZAxis.getX();
				nodeZ = nextFreeXonZAxis.getZ();

				boolean needsResetZAxisXContext = needsResetZAxis(nextEdgeZ, nextFreeXonZAxis, childNode);
				if (needsResetZAxisXContext) {
					nextEdgeZ = nextEdgeZ.add(0d, 0d, childSize);
				}
				nextFreeXonZAxis = nextFreeXonZAxis.add(childSize, 0d, 0d);

			} else if (!needsResetZAxis && !nextFreeXPointsIntersect) {
				nodeX = nextFreeZonXAxis.getX();
				nodeZ = nextFreeZonXAxis.getZ();

				boolean needsResetXAxisZContext = needsResetXAxis(nextEdgeX, nextFreeZonXAxis, childNode);
				if (needsResetXAxisZContext) {
					nextEdgeX = nextEdgeX.add(childSize, 0d, 0d);
				}
				nextFreeZonXAxis = nextFreeZonXAxis.add(0d, 0d, childSize);

			} else {
				if (nextEdgeXSmallerThanNextEdgeZ) {
					nodeX = nextEdgeX.getX();
				} else {
					nodeZ = nextEdgeZ.getZ();
				}

				if (nextFreeXPointsIntersect && nextEdgeXSmallerThanNextEdgeZ) {
					nextFreeZonXAxis = new Point3D(nextEdgeX.getX(), nextEdgeX.getY(), nextEdgeX.getZ());
					nextFreeZonXAxis = nextFreeZonXAxis.add(0d, 0d, childSize);
					nextEdgeX = nextEdgeX.add(childSize, 0d, 0d);
				} else if (needsResetXAxis) {
					nextEdgeX = nextEdgeX.add(childSize, 0d, 0d);
					nextFreeZonXAxis = new Point3D(nextEdgeX.getX(), nextEdgeX.getY(), nextEdgeX.getZ());
				}

				if (needsResetZAxis) {
					nextEdgeZ = nextEdgeZ.add(0d, 0d, childSize);
					nextFreeXonZAxis = new Point3D(nextEdgeZ.getX(), nextEdgeZ.getY(), nextEdgeZ.getZ());
				} else if (nextFreeXPointsIntersect && !nextEdgeXSmallerThanNextEdgeZ) {
					nextFreeXonZAxis = new Point3D(nextEdgeZ.getX(), nextEdgeZ.getY(), nextEdgeZ.getZ());
					nextFreeXonZAxis = nextFreeXonZAxis.add(childSize, 0d, 0d);
					nextEdgeZ = nextEdgeZ.add(0d, 0d, childSize);
				}
			}

			int parentSize = parentNode.getSize();

			childNode.setX((int) (childSize / 2 - parentSize / 2 + nodeX));
			childNode.setY((int) (-childSize / 2 + parentSize / 2 - nodeZ));
		}
	}

	private boolean needsResetXAxis(Point3D nextEdge, Point3D nextFreeOnAxis, CityNode child) {
		return nextEdge.getX() - nextFreeOnAxis.getX() < child.getSize() + streetSize;
	}

	private boolean needsResetZAxis(Point3D nextEdge, Point3D nextFreeOnAxis, CityNode child) {
		return nextEdge.getZ() - nextFreeOnAxis.getZ() < child.getSize() + streetSize;
	}

	private boolean intersects(Point3D checkPoint, Point3D childStartPoint, double childSize) {
		BoundingBox boundingBox = new BoundingBox(childStartPoint.getX(), childStartPoint.getZ(), childSize, childSize);
		Point2D checkPoint2D = new Point2D(checkPoint.getX(), checkPoint.getZ());
		return boundingBox.contains(checkPoint2D);
	}

	
}
