package br.unicamp.feec.utils;

public final class ColorUtils {
	private ColorUtils(){}
	
	public static float[] create(float r, float g, float b, float a)
	{
		float[] result = { r, g, b, a };
		return result;
	}
}
