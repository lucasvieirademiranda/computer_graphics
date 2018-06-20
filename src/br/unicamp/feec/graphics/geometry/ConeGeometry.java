package br.unicamp.feec.graphics.geometry;

import com.jogamp.opengl.GL4;

import br.unicamp.feec.graphics.shader.BaseShader;

public class ConeGeometry extends Geometry
{
	private static float RADIUS = 3.0f;
	private static float HEIGHT = 6.0f;
	private static int HEIGHT_RESOLUTION = 10;
	private static int REVOLUTION_RESOLUTION = 15;
	
	public ConeGeometry(BaseShader pShader)
	{
		super(pShader, buildCone(), new int[] {});
	}
	
	public static float[] buildCone()
	{
		if (RADIUS == 0 || HEIGHT == 0)
			return null;
		
		float[] vertices = new float[2 * 4 * (HEIGHT_RESOLUTION + 1) * (REVOLUTION_RESOLUTION + 1) + 4 * (REVOLUTION_RESOLUTION + 1)];
		
		float[] auxiliar = new float[4 * (REVOLUTION_RESOLUTION + 1)];
		
		float x = 0.0f, y = 0.0f, z = 0.0f, w = 1.0f;
		
		int index = 0;
		
		auxiliar[0] = 0.0f;
		auxiliar[1] = 0.0f;
		auxiliar[2] = 0.0f;
		auxiliar[3] = 1.0f;
		
		int count = 1;
		
		for (int i = 0; i < REVOLUTION_RESOLUTION; i++)
		{
			for (int j = 0; j <= HEIGHT_RESOLUTION; j++)
			{
				y = HEIGHT * j / HEIGHT_RESOLUTION;
				x= (float) (((HEIGHT - y) / HEIGHT) * RADIUS * Math.cos((double) i * 2 * Math.PI / REVOLUTION_RESOLUTION));
				z= (float) (((HEIGHT - y) / HEIGHT) * RADIUS * Math.sin((double) i * 2 * Math.PI / REVOLUTION_RESOLUTION));
				
				index = i * (HEIGHT_RESOLUTION + 1) * 8 + j * 8;
				
				vertices[index] = x;
				vertices[index + 1] = y;
				vertices[index + 2] = z;
				vertices[index + 3] = w;

				if (y == 0.0f)
				{
					index = 4 * count;
					
					auxiliar[index] = x;
					auxiliar[index + 1] = y;
					auxiliar[index + 2] = z;
					auxiliar[index + 3] = w;
					
					count++;
				}

				x= (float) (((HEIGHT - y) / HEIGHT) * RADIUS * Math.cos((double) (i + 1) * 2 * Math.PI / REVOLUTION_RESOLUTION));
				z= (float) (((HEIGHT - y) / HEIGHT) * RADIUS * Math.sin((double) (i + 1) * 2 * Math.PI / REVOLUTION_RESOLUTION));
				
				index = i * (10 + 1) * 8 + j * 8;
				
				vertices[index + 4] = x;
				vertices[index + 5] = y;
				vertices[index + 6] = z;
				vertices[index + 7] = w;
			}
		}
		
		for (int i = 0; i <= REVOLUTION_RESOLUTION; i++)
		{
			index = 2 * 4 * (HEIGHT_RESOLUTION + 1) * (REVOLUTION_RESOLUTION) + 4 * i;
			
			vertices[index] = auxiliar[4 * i];
			vertices[index + 1] =auxiliar[4 * i + 1];
			vertices[index + 2] = auxiliar[4 * i + 2];
			vertices[index + 3] = auxiliar[4 * i + 3];
		}
		
		return vertices;
	}
	
	@Override
	protected void drawGeometry(GL4 gl)
	{
		for (int j = 0; j < REVOLUTION_RESOLUTION; j++)
			gl.glDrawArrays(GL4.GL_TRIANGLE_STRIP, j * 2 * (HEIGHT_RESOLUTION + 1), 2 * (HEIGHT_RESOLUTION + 1));
		
		gl.glDrawArrays(GL4.GL_TRIANGLE_FAN, REVOLUTION_RESOLUTION * 2 * (HEIGHT_RESOLUTION + 1), HEIGHT_RESOLUTION + 1);
	}

}
