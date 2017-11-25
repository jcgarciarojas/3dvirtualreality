package com.ece554.objects;

import java.awt.Color;

import javax.vecmath.Color3f;

/**
 * 
 * 
 * University of Michigan - Dearborn
 * ECE556 Embedded Systems 
 * Summer 2008
 * Author: Juan Garcia
 */

public final class ColorFactory {
	
	public static Color3f getWhite() {
		return new Color3f(0.1f, 0.1f, 0.1f);
	}

	public static Color3f getBlack() {
		return new Color3f(0.0f, 0.0f, 0.0f);
	}

	public static Color3f getSpecular() {
		return new Color3f(1.0f, 1.0f, 1.0f);
	}

	public static Color3f getBlue() {
		return new Color3f(0.0f, 0.0f, 1.0f);
	}

	public static Color3f getMedBlue() {
		return new Color3f(0.0f, 0.1f, 0.5f);
	}

	public static Color3f getDarkGray() {
		return new Color3f(Color.DARK_GRAY);
	}

	public static Color3f getGray() {
		return new Color3f(Color.GRAY);
	}

	public static Color3f getLightGray() {
		return new Color3f(Color.LIGHT_GRAY);
	}

	public static Color3f getRed() {
		return new Color3f(1.0f, 0.0f, 0.0f);
	}

	public static Color3f getBrick() {
		return new Color3f(0.7f, 0.7f, 0.6f);
	}

	public static Color3f getGreen() {
		return new Color3f(0.0f, 1.0f, 0.0f);
	}

	public static Color3f getMedGreen() {
		return new Color3f(0.0f, 0.5f, 0.1f);
	}

}
