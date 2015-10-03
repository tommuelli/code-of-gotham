package com.canoo.cog.ui.city.model.style;

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


import java.util.HashMap;
import java.util.Map;

import javafx.beans.property.SimpleStringProperty;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import com.canoo.cog.ui.city.model.Painter;

public class CityStyle {

	private static final int NUMBER_OF_IMAGES = 4;
	private static SimpleStringProperty styleProperty = new SimpleStringProperty();
	static Map<String, Image> imagesMap = new HashMap<>();

	public enum Style {
		GOTHAM, TECH_DEBT, TECH_DEBT_PER_LINE, TEST_COVERAGE, COMPLEXITY_PER_LINE
	};

	public static void setStyle(Style aCityStyle) {
		styleProperty.setValue(aCityStyle.name());
	}

	public static void setStyle(String aCityStyleAsString) {
		styleProperty.setValue(findStyle(aCityStyleAsString).name());
	}

	private static Style findStyle(String aCityStyleAsString) {
		Style[] styles = Style.values();
		for (Style s : styles) {
			if (s.name().equals(aCityStyleAsString)) {
				return s;
			}
		}
		return Style.GOTHAM;
	}

	public static SimpleStringProperty getStyleProperty() {
		return styleProperty;
	}

	public static Color getBackgroundColor(String styleAsString) {
		Style s = findStyle(styleAsString);
		if (Style.GOTHAM.equals(s)) {
			return Color.BLACK;
		}
		return Color.BLACK;

	}

	private static void initImages() {

		imagesMap.put(Style.GOTHAM.name(), new Image(CityStyle.class
				.getResource("cog.jpg").toString()));
		imagesMap.put(Style.TECH_DEBT.name(), new Image(CityStyle.class
				.getResource("td.jpg").toString()));
		imagesMap.put(Style.TECH_DEBT_PER_LINE.name(), new Image(CityStyle.class
				.getResource("tdpl.jpg").toString()));
		imagesMap.put(Style.TEST_COVERAGE.name(), new Image(CityStyle.class
				.getResource("tc.jpg").toString()));
		imagesMap.put(Style.COMPLEXITY_PER_LINE.name(), new Image(CityStyle.class
				.getResource("cc.jpg").toString()));
	}

	public static Image getStyleImage(Style style) {
		String key = style.name();
		initImages();
		return imagesMap.get(key);
	}
	public static Color getStyleColor(Style style) {
		return new Painter().getStyleColor(style);
	}
}
