package br.unicamp.feec.graphics.geometry;

import java.util.ArrayList;

import com.jogamp.opengl.GL4;

import br.unicamp.feec.graphics.shader.BaseShader;

public class ToroidGeometry extends Geometry
{
	private static final float INNER_RADIUS = 8f;
	private static final float OUTER_RADIUS = 2f;
	
	private static final int INNER_RESOLUTION = 10;
	private static final int OUTER_RESOLUTION = 10;
	
	public ToroidGeometry(BaseShader pShader)
	{
		super(pShader, buildToroid(INNER_RADIUS, OUTER_RADIUS, INNER_RESOLUTION, OUTER_RESOLUTION), new int[] {});
	}
	
	public static float[] buildToroid(float innerRadius, float outerRadius, int innerResolution, int outerResolution)
	{
		ArrayList<Float> vertices = new ArrayList<Float>();
		
		float x = 0.0f, y = 0.0f, 
			  cosTheta = 0.0f, sinTheta = 0.0f,
			  cosRho = 0.0f, sinRho = 0.0f;
		
		for(int i = 0; i <= innerResolution; i++)
		{
			for (int j = 0; j <= outerResolution; j++)
			{
				cosRho = ((float) Math.cos(2 * Math.PI * j / (float) outerResolution));
				sinRho = ((float) Math.sin(2 * Math.PI * j / (float) outerResolution));
				
				x = outerRadius * cosRho;
				y = outerRadius * sinRho;
				
				x = x + innerRadius;
				
				cosTheta = ((float) Math.cos(2 * Math.PI * i / (float) innerResolution));
				sinTheta = ((float) Math.sin(2 * Math.PI * i / (float) innerResolution));
				
				vertices.add(x * cosTheta);
				vertices.add(y);
				vertices.add(x * sinTheta);
				
				vertices.add(cosTheta * cosRho);
				vertices.add(sinRho);
				vertices.add(sinTheta * cosRho);
				
				cosTheta = ((float) Math.cos(2 * Math.PI * (i + 1) / (float) innerResolution));
				sinTheta = ((float) Math.sin(2 * Math.PI * (i + 1) / (float) innerResolution));
				
				vertices.add(x * cosTheta);
				vertices.add(y);
				vertices.add(x * sinTheta);
				
				vertices.add(cosTheta * cosRho);
				vertices.add(sinRho);
				vertices.add(sinTheta * cosRho);
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
		for (int j = 0; j <= INNER_RESOLUTION; j++)
			gl.glDrawArrays(GL4.GL_TRIANGLE_STRIP, j * 2 * (OUTER_RESOLUTION + 1), 2 * (OUTER_RESOLUTION + 1));	
	}
	
	
	
}
