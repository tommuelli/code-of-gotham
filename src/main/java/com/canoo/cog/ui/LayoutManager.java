package com.canoo.cog.ui;

import com.canoo.cog.ui.model.AbstractElement;
import com.canoo.cog.ui.model.Building;
import com.canoo.cog.ui.model.City;
import com.canoo.cog.ui.model.Hood;
import javafx.collections.ObservableList;
import javafx.scene.Node;

public class LayoutManager {

    void moveCityToChildren(City city) {
        double xOffset = city.getShape().getWidth()/2;
        double zOffset = city.getShape().getWidth()/2;
        city.getShape().setTranslateX(-xOffset);
        city.getShape().setTranslateZ(-zOffset);
    }

    void moveCityChildrenBackToCity(AbstractElement element) {
        ObservableList<Node> children = element.getChildren();
        for (Node child : children) {
            if (child instanceof AbstractElement) {
                AbstractElement item = (AbstractElement) child;
                double xOffset = item.getWidth()/2;
                double zOffset = item.getWidth()/2;
                item.getShape().setTranslateX(-xOffset);
                item.getShape().setTranslateZ(-zOffset);
                moveCityChildrenBackToCity(item);
            }
        }
    }

    void correctYDirection(AbstractElement element) {
        ObservableList<Node> children = element.getChildren();
        for (Node child : children) {
            if (child instanceof AbstractElement) {
                AbstractElement abstractElement = (AbstractElement) child;
                double yOffset = 0;
                if(child instanceof Building) {
                    yOffset = abstractElement.getHeight() / 2;
                }
                if(child instanceof Hood) {
                    yOffset = abstractElement.getHeight();
                }
                abstractElement.setTranslateY(-yOffset);
                correctYDirection((AbstractElement) child);
            }
        }
    }

    void setRelativeOffset(AbstractElement element) {
        ObservableList<Node> children = element.getChildren();
        for (Node child : children) {
            if (child instanceof AbstractElement) {
                AbstractElement item = (AbstractElement) child;
                double xOffset = item.getxOffset();
                double zOffset = item.getzOffset();
                item.setTranslateX(-zOffset);
                item.setTranslateZ(-xOffset);
                setRelativeOffset(item);
            }
        }
    }
}
