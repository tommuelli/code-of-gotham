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


import java.util.ArrayList;
import java.util.List;

import com.canoo.cog.sonar.model.Model;

public class CityNode {
    List<CityNode> children;
    int size;
    int x;
    int y;

    Model model;

    CityNode(List<CityNode> children) {
        if (children != null) {
            this.children = children;
        } else {
            children = new ArrayList<>();
        }
    }

    public CityNode(int size) {
        this.size = size;
        children = new ArrayList<>();
    }

    public List<CityNode> getChildren() {
        return children;
    }

    public boolean isLeaf() {
        return children.isEmpty();
    }

    public int getSize() {
        return size;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    void setX(int x) {
        this.x = x;
    }

    void setY(int y) {
        this.y = y;
    }

    void setChildren(List<CityNode> children) {
        this.children = children;
    }

    void setSize(int size) {
        this.size = size;
    }

    @Override
    public String toString() {
        if (isLeaf()) {
            return "Leaf size = " + size + " (" + x + "," + y + ")";
        }
        String str = "Node size = " + size + " (" + x + "," + y + ")";
        for (CityNode child : children) {
            str += "\n - " + child.toString().replace("\n", "\n -");
        }
        return str;
    }

    public String toStringWithNames() {
        if (isLeaf()) {
            return "Leaf " + getModel().getName() + " size = " + size + " (" + x + "," + y + ")";
        }
        String str = "Node " + getModel().getName() + " size = " + size + " (" + x + "," + y + ")";
        for (CityNode child : children) {
            str += "\n - " + child.toStringWithNames().replace("\n", "\n -");
        }
        return str;
    }

    double getTotalArea() {
        if (isLeaf()) {
            return Math.pow(size, 2);
        }

        double result = 0.0;
        for (CityNode child : children) {
            result += child.getTotalArea();
        }
        return result;
    }

    public Model getModel() {
        return model;
    }

    void setModel(Model model) {
        this.model = model;
    }
}
