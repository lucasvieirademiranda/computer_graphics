package br.unicamp.feec.graphics.geometry;

import com.jogamp.opengl.GL4;

import br.unicamp.feec.graphics.shader.BaseShader;

public class SphereGeometry extends Geometry
{
	private static final int SPHERE_RESOLUTION = 10;
	private static final float RADIUS = 1.0f;
	
	public SphereGeometry(BaseShader pShader)
	{
		super(pShader, buildSphere(), new int[] {});
	}
	
	protected static float[] buildSphere()
	{
		float[] vertices = new float[8 * (SPHERE_RESOLUTION + 1) * 2 * SPHERE_RESOLUTION + 1];
		
		float x = 0.0f, y = 0.0f, z = 0.0f, w = 1.0f;
		
		int index = 0;
		
		for (int i = 0; i < SPHERE_RESOLUTION; i++)
		{
			for (int j = 0; j <= 2 * SPHERE_RESOLUTION; j++)
			{
				x = (float) (RADIUS * Math.cos((double) i * 2 * Math.PI / SPHERE_RESOLUTION) * Math.sin((double) j * Math.PI / (2 * SPHERE_RESOLUTION)));
				y = (float) (RADIUS * Math.cos((double) j * Math.PI / (2 * SPHERE_RESOLUTION)));	
				z = (float) (RADIUS * Math.sin((double) i * 2 * Math.PI / SPHERE_RESOLUTION) * Math.sin((double) j * Math.PI / (2 * SPHERE_RESOLUTION)));
				
				index = i * (2 * SPHERE_RESOLUTION + 1) * 8 + j * 8;
				
				vertices[index] = x;
				vertices[index + 1] = y;
				vertices[index + 2] = z;
				vertices[index + 3] = w;
				
				x = (float) (RADIUS * Math.cos((double) (i + 1) * 2 * Math.PI / SPHERE_RESOLUTION) * Math.sin((double) j * Math.PI / (2 * SPHERE_RESOLUTION)));
				y = (float) (RADIUS * Math.cos((double) j * Math.PI / (2 * SPHERE_RESOLUTION)));
				z = (float) (RADIUS * Math.sin((double) (i + 1) * 2 * Math.PI / SPHERE_RESOLUTION) * Math.sin((double) j * Math.PI / (2 * SPHERE_RESOLUTION)));
				
				vertices[index + 4] = x;
				vertices[index + 5] = y;
				vertices[index + 6] = z;
				vertices[index + 7] = w;
			}
		}
		
		return vertices;
	}
	
	@Override
	protected void drawGeometry(GL4 gl)
	{
		for (int j = 0; j < 2 * SPHERE_RESOLUTION; j++)
			gl.glDrawArrays(GL4.GL_TRIANGLE_STRIP, j * 2 * (SPHERE_RESOLUTION), 4 * (SPHERE_RESOLUTION));
	}

}
