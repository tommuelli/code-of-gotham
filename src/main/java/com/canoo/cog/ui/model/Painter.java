package com.canoo.cog.ui.model;

import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.canoo.cog.ui.model.style.CityStyle.Style;

public class Painter {
	List<Color> colors = new ArrayList<Color>();
	List<Color> colorsBlue = new ArrayList<Color>();
	List<Color> colorsViolet = new ArrayList<Color>();

	public Painter() {
		colors.add(Color.DARKGREY);
		colors.add(Color.DIMGREY);
		colors.add(Color.LIGHTGREY);
		colors.add(Color.WHITESMOKE);

		colorsBlue.add(Color.web("#CCE5FF"));
		colorsBlue.add(Color.web("#99CCFF"));
		colorsBlue.add(Color.web("#66B2FF"));
		colorsBlue.add(Color.web("#3399FF"));
		colorsBlue.add(Color.web("#0080FF"));
		colorsBlue.add(Color.web("#0066CC"));
		colorsBlue.add(Color.web("#004C99"));
		colorsBlue.add(Color.web("#003366"));

		colorsViolet.add(Color.web("#E5CCFF"));
		colorsViolet.add(Color.web("#CC99FF"));
		colorsViolet.add(Color.web("#B266FF"));
		colorsViolet.add(Color.web("#9933FF"));
		colorsViolet.add(Color.web("#7F00FF"));
		colorsViolet.add(Color.web("#6600CC"));
		colorsViolet.add(Color.web("#4C0099"));
		colorsViolet.add(Color.web("#330066"));
	}

	public Color getPaintByLevel(int aLevel) {
		int startRGB = 64;

		int levelRGB = startRGB + (aLevel * 32);

		while (levelRGB > 224) {
			levelRGB = 224;
		}
		return Color.rgb(levelRGB, levelRGB, levelRGB);
	}

	public Color getPaintForComplexityPerLineOfCode(double aValue,
			double aTopValue) {
		return getColorByValues(aValue, aTopValue, colorsBlue);
	}
	public Color getPaintForSqaleIndexPerLineOfCode(double aValue,
			double aTopValue) {
		return getColorByValues(aValue, aTopValue, colorsViolet);
	}

	public Color getPaintForSqualeIndex(double aValue,
			double aTopValue) {
		return getColorByValues(aValue, aTopValue, colorsViolet);
		
	}
	private Color getColorByValues(double aValue, double aTopValue,
			List<Color> colorList) {
		int colorIdx = 0;
		if (!(aValue <= 0d)) {
			int topIdx = colorList.size() - 1;
			colorIdx = new Double(aValue * topIdx / aTopValue).intValue();
		}
		return colorList.get(colorIdx);
	}

	public Color paint() {

		Random r = new Random();
		int colorIdx = r.nextInt(colors.size());
		return colors.get(colorIdx);
	}

	public Color paintForCoverage(double coverage) {
		if (coverage <= 0d) {
			return Color.RED;
		} else if (coverage <= 10d) {
			return Color.ORANGERED;
		} else if (coverage <= 25d) {
			return Color.ORANGE;
		} else if (coverage <= 50d) {
			return Color.YELLOW;
		} else if (coverage <= 75d) {
			return Color.YELLOWGREEN;
		} else if (coverage <= 75d) {
			return Color.GREEN;
		} else {
			return Color.DARKGREEN;
		}
	}

	public Color getStyleColor(Style style) {
		if (Style.GOTHAM.equals(style)){
			return Color.DARKGREY;
		}
		else 	if (Style.TECH_DEBT.equals(style)){
			return colorsViolet.get(3);
		}
		else 	if (Style.TECH_DEBT_PER_LINE.equals(style)){
			return colorsViolet.get(6);
		}
		else 	if (Style.TEST_COVERAGE.equals(style)){
			return Color.GREEN;
		}
		else 	if (Style.COMPLEXITY_PER_LINE.equals(style)){
			return colorsBlue.get(4);
		}
		return Color.DARKGREY;
	}
}
