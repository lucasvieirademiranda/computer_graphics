package br.unicamp.feec.graphics.geometry;

import java.util.ArrayList;

import com.jogamp.opengl.GL4;

import br.unicamp.feec.graphics.shader.BaseShader;

public class CylinderGeometry extends Geometry
{
	private static float CYLINDER_RADIUS = 1.0f;
	private static float CYLINDER_HEIGHT = 1.0f;
	
	private static int CYLINDER_RESOLUTION = 20;
	
	public CylinderGeometry(BaseShader pShader)
	{
		super(pShader, buildCylinder(CYLINDER_RADIUS, CYLINDER_HEIGHT, CYLINDER_RESOLUTION), new int[] {});
	}
	
	private static float[] buildCylinder(float radius, float height, float circleResolution)
	{
		ArrayList<Float> vertices = new ArrayList<Float>();
		
		float half = height / 2;
		
		// Circle Resolution * 2 - TRIANGLE_STRIP
		for (int i = 0; i <= circleResolution; i++)
		{
			float angle = (2 * ((float) Math.PI)) * ( i / circleResolution );
			
			float x = (float) Math.cos(angle);
			float z = (float) Math.sin(angle);
			
			// Bottom Circle
			vertices.add(radius * x);
			vertices.add(-half);
			vertices.add(radius * z);
			
			// Bottom Circle Normals
			vertices.add(x);
			vertices.add(0f);
			vertices.add(z);
			
			// Top Circle
			vertices.add(radius * x);
			vertices.add(half);
			vertices.add(radius * z);
			
			// Top Circle Normals
			
			vertices.add(x);
			vertices.add(0f);
			vertices.add(z);
		}
		
		// Center of Bottom Circle
		vertices.add(0.0f);
		vertices.add(-half);
		vertices.add(0.0f);
		
		// Center of Bottom Circle - Normal
		vertices.add(0f);
		vertices.add(-1f);
		vertices.add(0f);
		
		// CircleResolution - Bottom Circle - TRIANGLE_FAN
		for (int j = 0; j <= circleResolution; j++)
		{
			float angle = (2 * ((float) Math.PI)) * ( j / circleResolution );
			
			float x = (float) Math.cos(angle);
			float z = (float) Math.sin(angle);
			
			// Vertices
			vertices.add(radius * x);
			vertices.add(-half);
			vertices.add(radius * z);
			
			// Normals
			vertices.add(0f);
			vertices.add(-1f);
			vertices.add(0f);
		}
		
		// Center of Top Circle
		vertices.add(0.0f);
		vertices.add(half);
		vertices.add(0.0f);
		
		// Center of Bottom Circle - Normal
		vertices.add(0f);
		vertices.add(1f);
		vertices.add(0f);
		
		// CircleResolution - Top Circle - TRIANGLE_FAN
		for (int k = 0; k <= circleResolution; k++)
		{
			float angle = (2 * ((float) Math.PI)) * ( k / circleResolution );
			
			float x = (float) Math.cos(angle);
			float z = (float) Math.sin(angle);
			
			// Vertices
			vertices.add(radius * x);
			vertices.add(half);
			vertices.add(radius * z);
			
			// Normals
			vertices.add(0f);
			vertices.add(1f);
			vertices.add(0f);
		}
		
		float[] data = new float[vertices.size()];
		
		for (int i = 0; i < vertices.size(); i++)
			data[i] = vertices.get(i);
		
		return data;
	}
	
	@Override
	protected void drawGeometry(GL4 gl)
	{
		gl.glDrawArrays(GL4.GL_TRIANGLE_STRIP, 0, 2 * (CYLINDER_RESOLUTION + 1));
		gl.glDrawArrays(GL4.GL_TRIANGLE_FAN, 2 * (CYLINDER_RESOLUTION + 1), (CYLINDER_RESOLUTION + 1));
		gl.glDrawArrays(GL4.GL_TRIANGLE_FAN, 3 * (CYLINDER_RESOLUTION + 1) + 1, (CYLINDER_RESOLUTION + 1) + 1);
	}

	
}
