package br.unicamp.feec.graphics.geometry;

import java.util.ArrayList;

import com.jogamp.opengl.GL4;

import br.unicamp.feec.graphics.shader.BaseShader;

public class SphereGeometry extends Geometry
{
	private static final float RADIUS = 1.0f;
	
	private static final int LATITUDE = 50;
	private static final int LONGITUDE = 50;
	
	public SphereGeometry(BaseShader pShader)
	{
		super(pShader, buildSphere(RADIUS, LATITUDE, LONGITUDE), new int[] {});
	}
	
	protected static float[] buildSphere(float radius, int latitude, int longitude)
	{
		ArrayList<Float> vertices = new ArrayList<Float>();
		
		for (int i = 0; i <= latitude; i++)
		{
			float currentLatitude1 = ((float) Math.PI) * (-0.5f + (i - 1) / (float) latitude);
			
			float z0 = (float) Math.sin(currentLatitude1);
			float zr0 = (float) Math.cos(currentLatitude1);
			
			float currentLatitude2 = ((float) Math.PI) * (-0.5f + i / (float) latitude);
			
			float z1 = (float) Math.sin(currentLatitude2);
			float zr1 = (float) Math.cos(currentLatitude2);
			
			for (int j = 0; j <= longitude; j++)
			{
				
				float currentLongitude = 2 * ((float) Math.PI) * (j - 1) / longitude;
				
				float x = (float) Math.cos(currentLongitude);
				float y = (float) Math.sin(currentLongitude);
				
				// South Hemisphere
				vertices.add(radius * x * zr0);
				vertices.add(radius * y * zr0);
				vertices.add(radius * z0);
				
				// South Hemisphere Normals
				vertices.add(x * zr0);
				vertices.add(y * zr0);
				vertices.add(z0);
				
				// North Hemisphere
				vertices.add(radius * x * zr1);
				vertices.add(radius * y * zr1);
				vertices.add(radius * z1);
				
				// North Hemisphere Normals
				vertices.add(x * zr1);
				vertices.add(y * zr1);
				vertices.add(z1);
			}
		}
		
		float[] data = new float[vertices.size()];
		
		for (int i = 0; i < vertices.size(); i++)
			data[i] = vertices.get(i);
		
		return data;
	}
	
	@Override
	protected void drawGeometry(GL4 gl)
	{
		gl.glDrawArrays(GL4.GL_TRIANGLE_STRIP, 0, 2 * (LATITUDE + 1) * (LONGITUDE + 1));
	}

}
