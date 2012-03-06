package in.mobme.tvticker.helpers;

import android.graphics.Color;

public class TVMessage {
	
	public static final int NO_PRIORITY_COLOR = Color.BLACK;
	public static final int LOW_PRIORITY_COLOR = Color.BLUE;
	public static final int MEDIUM_PRIORITY_COLOR = Color.GREEN;
	public static final int HIGH_PRIORITY_COLOR = Color.MAGENTA;
	public static final int HIGHEST_PRIORITY_COLOR = Color.RED;
	
	String message;
	int style = 0;
	int color;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getStyle() {
		return style;
	}

	public void setStyle(int style) {
		this.style = style;
	}

	public int getColor() {
		return color;
	}

	public void setColor(int color) {
		this.color = color;
	}

}
