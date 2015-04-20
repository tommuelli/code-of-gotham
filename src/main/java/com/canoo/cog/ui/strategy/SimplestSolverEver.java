package com.canoo.cog.ui.strategy;

import java.util.List;

public class SimplestSolverEver implements Solver {
    @Override
    public void solveProblem(CityNode node, int streetSize) {

        // Solve leaf
        if(node.isLeaf()) {
            return;
        }

        // Solve problem for children hoods
        for (CityNode childNode : node.getChildren()) {
            solveProblem(childNode, streetSize);
        }

        //Solve problem for this node
        int offset = 0;
        List<CityNode> children = node.getChildren();
        for (CityNode child : children) {
            child.setX(offset);
            child.setY(0);
            offset+=child.getSize();
            offset+=streetSize;
        }
        node.setSize(offset-1);
    }
}
