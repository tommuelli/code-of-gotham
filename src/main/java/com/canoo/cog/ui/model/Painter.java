package com.canoo.cog.ui.model;

import java.util.ArrayList;
import java.util.Random;
import java.util.List;

import javafx.scene.paint.Color;

public class Painter {
	List<Color> colors = new ArrayList<Color>();

	public Painter() {
//		colors.add(Color.ALICEBLUE );
//		colors.add(Color.BEIGE );
//		colors.add(Color.CORNFLOWERBLUE );
//		colors.add(Color.DARKBLUE );
		colors.add(Color.DARKGREY );
//		colors.add(Color.DARKSLATEBLUE );
//		colors.add(Color.DEEPSKYBLUE );
		colors.add(Color.DIMGREY );
//		colors.add(Color.DODGERBLUE );
		colors.add(Color.LIGHTGREY );
//		colors.add(Color.LIGHTSTEELBLUE );
//		colors.add(Color.LIGHTSLATEGREY );
//		colors.add(Color.MEDIUMBLUE );
//		colors.add(Color.MIDNIGHTBLUE);
//		colors.add(Color.ROYALBLUE );
//		colors.add(Color.SKYBLUE );
//		colors.add(Color.WHITESMOKE );
	}
		

	public  Color paint(){

	    Random r = new Random();
        int colorIdx = r.nextInt(colors.size());
        return colors.get(colorIdx);
	}
}
