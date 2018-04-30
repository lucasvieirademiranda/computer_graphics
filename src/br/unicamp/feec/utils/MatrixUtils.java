package br.unicamp.feec.utils;

/*
 * Author: Lucas Vieira de Miranda 
 * RA: 211499
 */

public class MatrixUtils
{
	public static float[][] toSquareMatrix4x4(float[] matrix)
	{
		float[][] square = new float[4][4];
		
		for(int i = 0; i < square.length; i++)
			for(int j = 0; j < square[i].length; j++)
				square[j][i] = matrix[4 * i + j];
		
		return square;
	}
	
	public static float[] toPlainMatrix4x4(float[][] matrix)
	{	
		float[] plain = new float[16];
		
		for(int i = 0; i < matrix.length; i++)
			for(int j = 0; j < matrix[i].length; j++)
				plain[4 * i + j] = matrix[j][i];
	
		return plain;
	}
	
	public static float[][] transposeMatrix4x4(float[][] matrix)
	{
		float[][] transpose = new float[4][4];
		
		for(int i = 0; i < matrix.length; i++)
			for(int j = 0; j < matrix[i].length; i++)
				transpose[j][i] = matrix[i][j];
		
		return transpose;
	}
	
	public static float[][] multiplyMatrix4x4(float[][] matrix2, float[][] matrix1)
	{
		float[][] result = new float[4][4];
		
		for(int i = 0; i < 4; i++)
		{
			for(int j = 0; j < 4; j++)
			{
				float sum = 0.0f;
				
				for(int k = 0; k < 4; k++)
					sum += matrix1[i][k] * matrix2[k][j];
				
				result[i][j] = sum;
			}
		}
		
		return result;
	}
	
	public static float[][] getIdentityMatrix4x4()
	{
		float[][] result = {
			{ 1.0f, 0.0f, 0.0f, 0.0f },
			{ 0.0f, 1.0f, 0.0f, 0.0f },
			{ 0.0f, 0.0f, 1.0f, 0.0f },
			{ 0.0f, 0.0f, 0.0f, 1.0f }
		};
		
		return result;
	}
	
	public static float[][] getTransalationMatrix4x4(float x, float y, float z)
	{
		float[][] result = {
			{ 1.0f, 0.0f, 0.0f, x },
			{ 0.0f, 1.0f, 0.0f, y },
			{ 0.0f, 0.0f, 1.0f, z },
			{ 0.0f, 0.0f, 0.0f, 1.0f }
		};
		
		return result;
	}
	
	public static float[][] getScaleMatrix4x4(float x, float y, float z)
	{
		float[][] result = {
			{ x, 0.0f, 0.0f, 0.0f },
			{ 0.0f, y, 0.0f, 0.0f },
			{ 0.0f, 0.0f, z, 0.0f },
			{ 0.0f, 0.0f, 0.0f, 1.0f }
		};
		
		return result;
	}
	
	public static float[][] getRotationMatrix4x4(float angle, float x, float y, float z)
	{
		float module = 0.0f, xn = 0.0f, yn = 0.0f, zn = 0.0f,
			  rad = 0.0f, sin = 0.0f, cos = 0.0f,
			  a11 = 0.0f, a12 = 0.0f, a13 = 0.0f,
			  a21 = 0.0f, a22 = 0.0f, a23 = 0.0f,
			  a31 = 0.0f, a32 = 0.0f, a33 = 0.0f;
		
		// Normalização do vetor
		
		module = VectorUtils.module(x, y, z);
		
		xn = x / module;
		yn = y / module;
		zn = z / module;
		
		// Calculo do Seno e Cosseno do Ângulo
		
		rad = (float) Math.toRadians(angle);
		
		sin = (float) Math.sin(rad);
		cos = (float) Math.cos(rad);
		
		// Termos da matriz
		
		a11 = xn * xn * (1 - cos) + cos;
		a12 = xn * yn * (1 - cos) - zn * sin;
		a13 = xn * zn * (1 - cos) + yn * sin;
		
		a21 = xn * yn * (1 - cos) + zn * sin;
		a22 = yn * yn * (1 - cos) + cos;
		a23 = yn * zn * (1 - cos) - xn * sin;
		
		a31 = xn * zn * (1 - cos) - yn * sin;
		a32 = yn * zn * (1 - cos) + xn * sin;
		a33 = zn * zn * (1 - cos) + cos;
		
		float[][] result = {
			{ a11, a12, a13, 0.0f },
			{ a21, a22, a23, 0.0f },
			{ a31, a32, a33, 0.0f },
			{ 0.0f, 0.0f, 0.0f, 1.0f }
		};
		
		return result;
	}
	
	public static float[][] getLookAtMatrix4x4(float ex, float ey, float ez, float cx, float cy, float cz, float ux, float uy, float uz)
	{
		float[] u = VectorUtils.create(ux, uy, uz);
		
		// Calcula o vetor unitário D
		float[] d = VectorUtils.subtract(cx, cy, cz, ex, ey, ez);
		float[] dn = VectorUtils.normalize(d);
		
		// Calcula o vetor unitário I
		float[] i = VectorUtils.crossProduct(d, u);
		float[] in = VectorUtils.normalize(i);
		
		// Calcula o vetor unitário O
		float[] on = VectorUtils.crossProduct(in, dn);
		
		float[][] result = {
			{ in[0], in[1], in[2], 0 },
			{ on[0], on[1], on[2], 0 },
			{ -dn[0], -dn[1], -dn[2], 0 },
			{ 0, 0, 0, 1 }
		};
		
		return result;
	}
	
	public static float[][] getOrthographicMatrix4x4(float left, float right, float bottom, float top, float near, float far)
	{
		float a11 = 0.0f, a22 = 0.0f, a33 = 0.0f,
			  a14 = 0.0f, a24 = 0.0f, a34 = 0.0f;
		
		a11 = 2 / (right - left);
		a22 = 2 / (top - bottom);
		a33 = -2 / (far - near);
		
		a14 = - (right + left) / (right - left);
		a24 = - (top + bottom) / (top - bottom);
		a34 = - (far + near) / (far - near);
		
		float[][] result = {
			{ a11, 0, 0, a14 },
			{ 0, a22, 0, a24 },
			{ 0, 0, a33, a34 },
			{ 0, 0, 0, 1 }
		};
		
		return result;
	}
	
	public static float[][] getFrustrumMatrix4x4(float left, float right, float bottom, float top, float near, float far)
	{
		float a11 = 0.0f, a22 = 0.0f, a33 = 0.0f,
			  a13 = 0.0f, a23 = 0.0f, a34 = 0.0f, a43 = 0.0f;
		
		a11 = (2 * near) / (right - left);
		a22 = (2 * near) / (top - bottom);
		a33 = - (far + near) / (far - near);
		
		a13 = (right + left) / (right - left);
		a23 = (top + bottom) / (top - bottom);
		a34 = - (2 * far * near) / (far - near);
		
		a43 = - 1;
		
		float[][] result = {
			{ a11, 0, a13, 0 },
			{ 0, a22, a23, 0 },
			{ 0, 0, a33, a34 },
			{ 0, 0, a43, 0}
		};
		
		return result;
	}
}
