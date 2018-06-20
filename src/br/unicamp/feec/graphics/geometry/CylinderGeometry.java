package br.unicamp.feec.graphics.geometry;

import com.jogamp.opengl.GL4;

import br.unicamp.feec.graphics.shader.BaseShader;

public class CylinderGeometry extends Geometry
{
	private static float CYLINDER_RADIUS = 3.0f;
	private static float CYLINDER_HEIGHT = 6.0f;
	private static int CYLINDER_RESOLUTION = 20;
	
	public CylinderGeometry(BaseShader pShader)
	{
		super(pShader, buildCylinder(), new int[] {});
	}
	
	public static float[] buildCylinder()
	{
		if (CYLINDER_RADIUS == 0 || CYLINDER_HEIGHT == 0)
				return null;
		
		float[] vertices = new float[8 * (CYLINDER_RESOLUTION + 1) + 8 * (CYLINDER_RESOLUTION + 2) + 4];
		
		float x = 0.0f, y = 0.0f, z = 0.0f, w = 1.0f;
		
		int count = 0;
		
		for (int i = 0; i <= CYLINDER_RESOLUTION; i++)
		{
			y = 0;
			x= (float) (CYLINDER_RADIUS * Math.cos((double) i * 2 * Math.PI / CYLINDER_RESOLUTION));
			z= (float) (CYLINDER_RADIUS * Math.sin((double) i * 2 * Math.PI / CYLINDER_RESOLUTION));
			
			vertices[i * 8] = x;
			vertices[i * 8 + 1] = y;
			vertices[i * 8 + 2] = z;
			vertices[i * 8 + 3] = w;
			
			y = CYLINDER_HEIGHT;
			
			vertices[i * 8 + 4] = x;
			vertices[i * 8 + 5] = y;
			vertices[i * 8 + 6] = z;
			vertices[i * 8 + 7] = w;
		}
		
		count = (CYLINDER_RESOLUTION + 1) * 8 + 4;
		
		vertices[count] = 0.0f;
		vertices[count + 1] = 0.0f;
		vertices[count + 2] = 0.0f;
		vertices[count + 3] = 1.0f;
		
		count += 4;
		
		y = 0.0f;
		
		for (int i = 0; i <= CYLINDER_RESOLUTION; i++)
		{
			x = (float) (CYLINDER_RADIUS * Math.cos((double) i * 2 * Math.PI / CYLINDER_RESOLUTION));
			z = (float) (CYLINDER_RADIUS * Math.sin((double) i * 2 * Math.PI / CYLINDER_RESOLUTION));
			
			vertices[count + i * 4] = x;
			vertices[count + i * 4 + 1] = y;
			vertices[count + i * 4 + 2] = z;
			vertices[count + i * 4 + 3] = w;
		}
		
		y = CYLINDER_HEIGHT;
		
		count += (CYLINDER_RESOLUTION + 1) * 4;
		
		vertices[count] = 0.0f;
		vertices[count + 1] = y;
		vertices[count + 2] = 0.0f;
		vertices[count + 3] = 1.0f;
		
		count += 4;
		
		for (int i = 0; i <= CYLINDER_RESOLUTION; i++)
		{
			x= (float) (CYLINDER_RADIUS * Math.cos((double) i * 2 * Math.PI / CYLINDER_RESOLUTION));
			z= (float) (CYLINDER_RADIUS * Math.sin((double) i * 2 * Math.PI / CYLINDER_RESOLUTION));
			
			vertices[count + i * 4] = x;
			vertices[count + i * 4 + 1] = y; 
			vertices[count + i * 4 + 2] = z; 
			vertices[count + i * 4 + 3] = w;
		}
		
		return vertices;
	}
	
	@Override
	protected void drawGeometry(GL4 gl)
	{
		gl.glDrawArrays(GL4.GL_TRIANGLE_STRIP, 0, 2 * (CYLINDER_RESOLUTION + 1));
		gl.glDrawArrays(GL4.GL_TRIANGLE_FAN, 2 * (CYLINDER_RESOLUTION + 1) + 1, CYLINDER_RESOLUTION + 1);
		gl.glDrawArrays(GL4.GL_TRIANGLE_FAN, 2 * (CYLINDER_RESOLUTION + 1) + CYLINDER_RESOLUTION + 3, CYLINDER_RESOLUTION + 1);
	}

	
}
