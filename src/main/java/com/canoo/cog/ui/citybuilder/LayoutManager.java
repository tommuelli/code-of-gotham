package com.canoo.cog.ui.citybuilder;

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


import com.canoo.cog.ui.citybuilder.model.AbstractElement;
import com.canoo.cog.ui.citybuilder.model.Building;
import com.canoo.cog.ui.citybuilder.model.City;
import com.canoo.cog.ui.citybuilder.model.Hood;
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
