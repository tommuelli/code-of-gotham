package com.canoo.cog.ui.strategy;

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

    CityNode(int size) {
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
