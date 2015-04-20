package com.canoo.cog.ui.model.text;

public class Info {
	private static InfoProperty infoProperty = new InfoProperty();
	public static void update (String info) {
		infoProperty.setValue(info);
	}
	public static InfoProperty getInfoProperty() {
		return infoProperty;
	}

	
}
