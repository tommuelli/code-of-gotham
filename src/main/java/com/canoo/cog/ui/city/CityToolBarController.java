package com.canoo.cog.ui.city;

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

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;

import java.util.HashMap;
import java.util.Map;

import com.canoo.cog.ui.city.model.style.CityStyle;
import com.canoo.cog.ui.city.model.style.CityStyle.Style;

class CityToolBarController {

	@FXML
	private Label projectTitle;
	
	@FXML
	private ToggleGroup styleGroup;
	@FXML
	private RadioButton cc;
	@FXML
	private RadioButton tc;
	@FXML
	private RadioButton cog;
	@FXML
	private RadioButton td;
	@FXML
	private RadioButton tdpl;

	Map<RadioButton, CityStyle.Style> styleMap = new HashMap<>();

	public void init(String cityName) {
		
		projectTitle.setText(cityName);
		
		cog.setSelected(true);

		styleMap.put(cc, CityStyle.Style.COMPLEXITY_PER_LINE);
		styleMap.put(tc, CityStyle.Style.TEST_COVERAGE);
		styleMap.put(cog, CityStyle.Style.GOTHAM);
		styleMap.put(td, CityStyle.Style.TECH_DEBT);
		styleMap.put(tdpl, CityStyle.Style.TECH_DEBT_PER_LINE);

		cc.setOnAction(EventHandler -> setCityStyle());
		tc.setOnAction(EventHandler -> setCityStyle());
		cog.setOnAction(EventHandler -> setCityStyle());
		td.setOnAction(EventHandler -> setCityStyle());
		tdpl.setOnAction(EventHandler -> setCityStyle());

	}

	private void setCityStyle() {
		Toggle selectedToggle = styleGroup.getSelectedToggle();
		Style style = styleMap.get(selectedToggle);
		CityStyle.setStyle(style);
	};
}
