package com.canoo.cog.ui.model;

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
import java.util.Random;
import java.util.List;

import javafx.scene.paint.Color;

public class Painter {
	List<Color> colors = new ArrayList<Color>();

	public Painter() {
		colors.add(Color.ALICEBLUE );
		colors.add(Color.BEIGE );
		colors.add(Color.CORNFLOWERBLUE );
		colors.add(Color.DARKBLUE );
		colors.add(Color.DARKGREY );
		colors.add(Color.DARKSLATEBLUE );
		colors.add(Color.DEEPSKYBLUE );
		colors.add(Color.DIMGREY );
		colors.add(Color.DODGERBLUE );
		colors.add(Color.LIGHTGREY );
		colors.add(Color.LIGHTSTEELBLUE );
		colors.add(Color.LIGHTSLATEGREY );
		colors.add(Color.MEDIUMBLUE );
		colors.add(Color.MIDNIGHTBLUE );
		colors.add(Color.ROYALBLUE );
		colors.add(Color.SKYBLUE );
		colors.add(Color.WHITESMOKE );
	}
		

	public  Color paint(){

	    Random r = new Random();
        int colorIdx = r.nextInt(colors.size());
        return colors.get(colorIdx);
	}
}
