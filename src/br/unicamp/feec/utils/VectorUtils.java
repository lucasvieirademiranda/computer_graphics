package br.unicamp.feec.utils;

/*
 * Author: Lucas Vieira de Miranda 
 * RA: 211499
 */

public class VectorUtils {

	public static float[] create(float x, float y, float z)
	{
		float[] result = { x, y, z };
		return result;
	}
	
	public static float[] multiplyMatrix4x4ByVector4(float[][] matrix, float[] vector)
	{
		float[] result = new float[4];
		
		for(int i = 0; i < matrix.length; i++)
			for(int j = 0; j < vector.length; j++)
				result[i] += matrix[i][j] * vector[j];
		
		return result;
	}
	
	public static float[] scalarProduct(float scalar, float x1, float y1, float z1)
	{
		float[] result = new float[3];
		
		result[0] = scalar * x1;
		result[1] = scalar * y1;
		result[2] = scalar * z1;
		
		return result;
	}
	
	public static float[] scalarProduct(float scalar, float[] v1)
	{
		return scalarProduct(scalar, v1[0], v1[1], v1[2]);
	}
	
	public static float dotProduct(float x1, float y1, float z1, float x2, float y2, float z2)
	{
		float scalar = 0.0f;
		
		scalar = (x1 * x2) + (y1 * y2) + (z1 * z2);
		
		return scalar;
	}
	
	public static float dotProduct(float[] v1, float[] v2)
	{
		return dotProduct(v1[0], v1[1], v1[2], v2[0], v2[1], v2[2]);
	}
	
	public static float[] crossProduct(float x1, float y1, float z1, float x2, float y2, float z2)
	{
		float newX = 0.0f, newY = 0.0f, newZ = 0.0f;
		
		newX = (y1 * z2) - (z1 * y2);
		newY = (z1 * x2) - (x1 * z2);
		newZ = (x1 * y2) - (y1 * x2);

		float [] result = { newX, newY, newZ };
		
		return result;
	}
	
	public static float[] crossProduct(float[] v1, float [] v2)
	{
		return crossProduct(v1[0], v1[1], v1[2], v2[0], v2[1], v2[2]);
	}
	
	public static float[] sum(float x1, float y1, float z1, float x2, float y2, float z2)
	{
		float newX = 0.0f, newY = 0.0f, newZ = 0.0f;
		
		newX = x1 + x2;
		newY = y1 + y2;
		newZ = z1 + z2;
		
		float[] result = { newX, newY, newZ };
		
		return result;
	}
	
	public static float[] sum(float[] v1, float[] v2)
	{
		return sum(v1[0], v1[1], v1[2], v2[0], v2[1], v2[2]);
	}
	
	public static float[] subtract(float x1, float y1, float z1, float x2, float y2, float z2)
	{
		float newX = 0.0f, newY = 0.0f, newZ = 0.0f;
		
		newX = x1 - x2;
		newY = y1 - y2;
		newZ = z1 - z2;
		
		float[] result = { newX, newY, newZ };
		
		return result;
	}
	
	public static float[] subtract(float[] v1, float[] v2)
	{
		return subtract(v1[0], v2[1], v2[2], v2[0], v2[1], v2[2]);
	}
	
	public static float[] normalize(float x, float y, float z)
	{
		float xn = 0.0f, yn = 0.0f, zn = 0.0f, module = 0.0f;
		
		module = module(x, y, z);
		
		xn = x / module;
		yn = y / module;
		zn = z / module;
		
		float[] result = { xn, yn, zn };
		
		return result;
	}
	
	public static float[] normalize(float[] v)
	{
		return normalize(v[0], v[1], v[2]);
	}
	
	public static float module(float x, float y, float z)
	{
		return (float) Math.sqrt((x * x) + (y * y) + (z * z));
	}
	
	public static float module(float[] v)
	{
		return module(v[0], v[1], v[2]);
	}
}
