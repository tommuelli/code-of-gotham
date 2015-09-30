package com.canoo.cog.solver;

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


import java.util.List;

public class SimplestSolverEver implements Solver {

    @Override
    public String toString() {
        return "Simplest Solver Ever";
    }

    @Override
    public void solveProblem(CityNode node, int streetSize) {

        // Solve leaf
        if (node.isLeaf()) {
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
            offset += child.getSize();
            offset += streetSize;
        }
        node.setSize(offset - 1);
    }
}
