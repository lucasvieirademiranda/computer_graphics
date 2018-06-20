package br.unicamp.feec.graphics.geometry;

import com.jogamp.opengl.GL4;

import br.unicamp.feec.graphics.shader.BaseShader;

public class ToroidGeometry extends Geometry
{
	private static float RADIUS = 8.0f;
	private static float THICKNESS = 2.0f;
	private static int CIRCLE_RESOLUTION = 10;
	private static int REVOLUTION_RESOLUTION = 30;
	
	public ToroidGeometry(BaseShader pShader)
	{
		super(pShader, buildToroid(), new int[] {});
	}
	
	public static float[] buildToroid()
	{
		float radius = RADIUS;
		float thickness = THICKNESS;
		
		if (radius < thickness)
		{
			float change = radius;
			radius = thickness;
			thickness = change;
		}
		
		if (radius == 0 || thickness == 0)
			return null;
		
		float[] vertices = new float[2 * 4 * (CIRCLE_RESOLUTION+1) * (REVOLUTION_RESOLUTION)], auxiliar = new float[4];
		
		float x = 0.0f, y = 0.0f, z = 0.0f, w = 1.0f, cos = 0, sin = 0;
		
		int  index = 0;
		
		for (int i = 0; i < REVOLUTION_RESOLUTION; i++)
		{
			for (int j = 0; j <= CIRCLE_RESOLUTION; j++)
			{
				x = (float) (thickness * Math.cos((double) j * 2 * Math.PI / CIRCLE_RESOLUTION));
				y = (float) (thickness * Math.sin((double) j * 2 * Math.PI / CIRCLE_RESOLUTION));
				
				auxiliar[0] = x + radius; 
				auxiliar[1] = y; 
				auxiliar[2] = z; 
				auxiliar[3] = w; 
				
				cos = (float) (Math.cos((double) i * 2 * Math.PI / REVOLUTION_RESOLUTION));
				sin = (float) (Math.sin((double) i * 2 * Math.PI / REVOLUTION_RESOLUTION));
				
				index = i * (CIRCLE_RESOLUTION + 1) * 8 + j * 8;
				
				vertices[index] = auxiliar[0] * cos;
				vertices[index + 1] = auxiliar[1];
				vertices[index + 2] = auxiliar[0] * sin;
				vertices[index + 3] = auxiliar[3];
				
				cos = (float) (Math.cos((double) (i + 1) * 2 * Math.PI / REVOLUTION_RESOLUTION));
				sin = (float) (Math.sin((double) (i + 1) * 2 * Math.PI / REVOLUTION_RESOLUTION));
				
				index = i * (CIRCLE_RESOLUTION + 1) * 8 + j * 8;
				
				vertices[index + 4] = auxiliar[0] * cos; 
				vertices[index + 5] = auxiliar[1];
				vertices[index + 6] = auxiliar[0] * sin;
				vertices[index + 7] = auxiliar[3];
			}
		}
		
		return vertices;
	}
	
	@Override
	protected void drawGeometry(GL4 gl)
	{
		for (int j = 0; j <= REVOLUTION_RESOLUTION; j++)
			gl.glDrawArrays(GL4.GL_TRIANGLE_STRIP, j *2 * (CIRCLE_RESOLUTION + 1), 2 * (CIRCLE_RESOLUTION + 1));	
	}
	
	
	
}
