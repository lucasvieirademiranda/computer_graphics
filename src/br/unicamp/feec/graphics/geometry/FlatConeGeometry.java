package br.unicamp.feec.graphics.geometry;

import java.util.ArrayList;

import com.jogamp.opengl.GL4;

import br.unicamp.feec.graphics.shader.BaseShader;
import br.unicamp.feec.utils.VectorUtils;

public class FlatConeGeometry extends Geometry
{
	private static final float RADIUS = 1;
	private static final float HEIGHT = 1;
	
	private static final int TRIANGLES = 100;
	
	public FlatConeGeometry(BaseShader pShader)
	{
		super(pShader, buildCone(RADIUS, HEIGHT, TRIANGLES), new int[] {});
	}
	
	public static float[] buildCone(float radius, float height, int triangles)
	{
		ArrayList<Float> vertices = new ArrayList<Float>();
		
		float currentAngle = 0.0f, nextAngle = 0.0f,
			  x1 = 0, y1 = 0, z1 = 0,
			  x2 = 0, y2 = 0, z2 = 0,
			  x3 = 0, y3 = 0, z3 = 0;
		
		for(int i = 0; i <= triangles; i++)
		{
			currentAngle = 2 * ((float) Math.PI) * (i / (float) (triangles + 1));
			
			nextAngle = 2 * ((float) Math.PI) * ((i + 1) / (float) (triangles + 1));
			
			x1 = (float) Math.cos(currentAngle);
			y1 = 0;
			z1 = (float) Math.sin(currentAngle);
			
			x3 = (float) Math.cos(nextAngle);
			y3 = 0;
			z3 = (float) Math.sin(nextAngle);
			
			x2 = 0;
			y2 = height;
			z2 = 0;
		
			float[] p1 = VectorUtils.create(x2, y2, z2);
			float[] p2 = VectorUtils.create(radius * x1, y1, radius * z1);
			float[] p3 = VectorUtils.create(radius * x3, y3, radius * z3);
			
			float[] v1 = VectorUtils.normalize(VectorUtils.subtract(p2, p1));
			float[] v2 = VectorUtils.normalize(VectorUtils.subtract(p3, p1));
			
			float[] normal = VectorUtils.crossProduct(v2, v1);
			
			vertices.add(x2);
			vertices.add(y2);
			vertices.add(z2);

			vertices.add(normal[0]);
			vertices.add(normal[1]);
			vertices.add(normal[2]);
					
			vertices.add(radius * x1);
			vertices.add(y1);
			vertices.add(radius * z1);
			
			vertices.add(normal[0]);
			vertices.add(normal[1]);
			vertices.add(normal[2]);
			
			vertices.add(radius * x3);
			vertices.add(y3);
			vertices.add(radius * z3);
			
			vertices.add(normal[0]);
			vertices.add(normal[1]);
			vertices.add(normal[2]);
		}
		
		vertices.add(0.0f);
		vertices.add(0.0f);
		vertices.add(0.0f);
		
		vertices.add(0.0f);
		vertices.add(-1.0f);
		vertices.add(0.0f);
		
		for(int i = 0; i <= triangles; i++)
		{
			float angle = 2 * ((float) Math.PI) * (i / (float) (triangles + 1) );
		
			float x = (float) Math.cos(angle);
			float z = (float) Math.sin(angle);
			
			vertices.add(radius * x);
			vertices.add(0.0f);
			vertices.add(radius * z);
			
			vertices.add(0.0f);
			vertices.add(-1.0f);
			vertices.add(0.0f);
		}
		
		float[] data = new float[vertices.size()];
		
		for (int i = 0; i < vertices.size(); i++)
			data[i] = vertices.get(i);
		
		return data;
	}
	
	@Override
	protected void drawGeometry(GL4 gl)
	{
		// Test
		gl.glDrawArrays(GL4.GL_TRIANGLES, 0, 3 * (TRIANGLES + 1));
		
		// Works
		gl.glDrawArrays(GL4.GL_TRIANGLE_FAN, 3 * (TRIANGLES + 1), TRIANGLES + 2);
	}
	
}
