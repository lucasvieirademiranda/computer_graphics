package br.unicamp.feec.graphics.geometry;

import java.util.ArrayList;

import com.jogamp.opengl.GL4;

import br.unicamp.feec.graphics.shader.BaseShader;
import br.unicamp.feec.utils.VectorUtils;

public class FlatCylinderGeometry extends Geometry
{
	private static float CYLINDER_RADIUS = 3.0f;
	private static float CYLINDER_HEIGHT = 6.0f;
	
	private static int TRIANGLES = 100;
	
	public FlatCylinderGeometry(BaseShader pShader)
	{
		super(pShader, drawCylinder(CYLINDER_RADIUS, CYLINDER_HEIGHT, TRIANGLES), new int[] {});
	}
	
	public static float[] drawCylinder(float radius, float height, int triangles)
	{
		ArrayList<Float> vertices = new ArrayList<Float>();
		
		float currentAngle = 0.0f, nextAngle = 0.0f,
				  x1 = 0, z1 = 0,
				  x2 = 0, z2 = 0;
		
		for(int i = 0; i <= triangles; i++)
		{
			currentAngle = 2 * ((float) Math.PI) * (i / (float) (triangles + 1));
			
			nextAngle = 2 * ((float) Math.PI) * ((i + 1) / (float) (triangles + 1));
			
			x1 = (float) Math.cos(currentAngle);
			z1 = (float) Math.sin(currentAngle);
			
			x2 = (float) Math.cos(nextAngle);
			z2 = (float) Math.sin(nextAngle);

			float[] p1 = VectorUtils.create(radius * x1, 0f, radius * z1);
			float[] p2 = VectorUtils.create(radius * x1, height, radius * z1);
			float[] p3 = VectorUtils.create(radius * x2, 0f, radius * z2);
			
			float[] v1 = VectorUtils.normalize(VectorUtils.subtract(p2, p1));
			float[] v2 = VectorUtils.normalize(VectorUtils.subtract(p3, p1));
			
			float[] normal = VectorUtils.crossProduct(v1, v2);
			
			vertices.add(radius * x1);
			vertices.add(0f);
			vertices.add(radius * z1);
			
			vertices.add(normal[0]);
			vertices.add(normal[1]);
			vertices.add(normal[2]);
			
			vertices.add(radius * x1);
			vertices.add(height);
			vertices.add(radius * z1);

			vertices.add(normal[0]);
			vertices.add(normal[1]);
			vertices.add(normal[2]);
			
			vertices.add(radius * x2);
			vertices.add(height);
			vertices.add(radius * z2);
			
			vertices.add(normal[0]);
			vertices.add(normal[1]);
			vertices.add(normal[2]);
			
			vertices.add(radius * x2);
			vertices.add(height);
			vertices.add(radius * z2);
			
			vertices.add(normal[0]);
			vertices.add(normal[1]);
			vertices.add(normal[2]);
			
			vertices.add(radius * x2);
			vertices.add(0f);
			vertices.add(radius * z2);
			
			vertices.add(normal[0]);
			vertices.add(normal[1]);
			vertices.add(normal[2]);
			
			vertices.add(radius * x1);
			vertices.add(0f);
			vertices.add(radius * z1);
			
			vertices.add(normal[0]);
			vertices.add(normal[1]);
			vertices.add(normal[2]);
		}
		
		// Center of Bottom Circle
		vertices.add(0.0f);
		vertices.add(0f);
		vertices.add(0.0f);
		
		// Center of Bottom Circle - Normal
		vertices.add(0f);
		vertices.add(-1f);
		vertices.add(0f);
		
		// CircleResolution - Bottom Circle - TRIANGLE_FAN
		for (int j = 0; j <= triangles; j++)
		{
			float angle = 2 * ((float) Math.PI) * ( j / (float) (triangles + 1) );
			
			float x = (float) Math.cos(angle);
			float z = (float) Math.sin(angle);
			
			// Vertices
			vertices.add(radius * x);
			vertices.add(0f);
			vertices.add(radius * z);
			
			// Normals
			vertices.add(0f);
			vertices.add(-1f);
			vertices.add(0f);
		}
		
		// Center of Top Circle
		vertices.add(0.0f);
		vertices.add(height);
		vertices.add(0.0f);
		
		// Center of Bottom Circle - Normal
		vertices.add(0f);
		vertices.add(1f);
		vertices.add(0f);
		
		// CircleResolution - Top Circle - TRIANGLE_FAN
		for (int k = 0; k <= triangles; k++)
		{
			float angle = 2 * ((float) Math.PI) * ( k / (float) (triangles + 1) );
			
			float x = (float) Math.cos(angle);
			float z = (float) Math.sin(angle);
			
			// Vertices
			vertices.add(radius * x);
			vertices.add(height);
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
	
	protected void drawGeometry(GL4 gl)
	{
		gl.glDrawArrays(GL4.GL_TRIANGLES, 0, 6 * (TRIANGLES + 1));
		gl.glDrawArrays(GL4.GL_TRIANGLE_FAN, 6 * (TRIANGLES + 1), TRIANGLES + 2);
		gl.glDrawArrays(GL4.GL_TRIANGLE_FAN, 6 * (TRIANGLES + 1) + (TRIANGLES + 2), TRIANGLES + 2);
	}
}
