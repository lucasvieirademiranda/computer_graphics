package br.unicamp.feec.graphics.geometry;

import java.util.ArrayList;

import com.jogamp.opengl.GL4;

import br.unicamp.feec.graphics.shader.BaseShader;

public class ConeGeometry extends Geometry
{
	private static final float RADIUS = 5.0f;
	private static final float HEIGHT = 5.0f;
	
	private static final int CIRCLE_RESOLUTION = 10;
	private static final int HEIGHT_RESOLUTION = 10;
	
	public ConeGeometry(BaseShader pShader)
	{
		super(pShader, buildCone(RADIUS, HEIGHT, CIRCLE_RESOLUTION, HEIGHT_RESOLUTION), new int[] {});
	}
	
	public static float[] buildCone(float radius, float height, int circleResolution, int heightResolution)
	{
		ArrayList<Float> vertices = new ArrayList<Float>();
		
		float x = 0.0f, y = 0.0f, z = 0.0f,
			  ratio = 0.0f, angle = 0.0f,
			  coneCos = 0.0f, coneSin = 0.0f,
			  cos = 0.0f, sin = 0.0f;
				
		float coneAngle = (float) Math.atan(radius / height);
		
		coneCos = (float) Math.cos(coneAngle);
		coneSin = (float) Math.sin(coneAngle);
		
		// circleResolution * heightResolution * 3
		
		for (int i = 0; i <= circleResolution; i++)
		{
			for(int j = 0; j <= heightResolution; j++)
			{
				// Cone Parametric Equation
				y = height * (j / (float) heightResolution);
				
				ratio = (height - y) / height;
				
				angle = 2 * ((float) Math.PI) * (i / (float) circleResolution);
				
				cos = ((float) Math.cos(angle));
				sin = ((float) Math.sin(angle));
				
				x = ratio * radius * cos;
				z = ratio * radius * sin;	
				
				// Coordenadas da Seção Cônica
				vertices.add(x);
				vertices.add(y);
				vertices.add(z);				
				
				// Normal
				vertices.add(coneCos * cos);
				vertices.add(coneSin);
				vertices.add(coneCos * sin);
				
				angle = 2 * ((float) Math.PI) * (i + 1 / (float) circleResolution);
				
				cos = ((float) Math.cos(angle));
				sin = ((float) Math.sin(angle));
				
				x = ratio * radius * cos;
				z = ratio * radius * sin;
				
				// Coordenadas da Seção Cônica
				vertices.add(x);
				vertices.add(y);
				vertices.add(z);
				
				// Normal
				vertices.add(coneCos * cos);
				vertices.add(coneSin);
				vertices.add(coneCos * sin);
			}
		}
		
		// Center of Bottom Circle - Vertices
		vertices.add(0.0f);
		vertices.add(0.0f);
		vertices.add(0.0f);
		
		// Center of Bottom Circle - Normal
		vertices.add(0.0f);
		vertices.add(-1.0f);
		vertices.add(0.0f);
		
		// CircleResolution - Bottom Circle - TRIANGLE_FAN
		for (int j = 0; j <= circleResolution; j++)
		{
			angle = (2 * ((float) Math.PI)) * ( j / (float) circleResolution );
			
			x = (float) Math.cos(angle);
			z = (float) Math.sin(angle);
			
			vertices.add(radius * x);
			vertices.add(0.0f);
			vertices.add(radius * z);
			
			// Normal
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
		for (int j = 0; j <= CIRCLE_RESOLUTION; j++)
			gl.glDrawArrays(GL4.GL_TRIANGLE_STRIP, j * 2 * (HEIGHT_RESOLUTION + 1), 2 * (HEIGHT_RESOLUTION + 1));
		
		gl.glDrawArrays(GL4.GL_TRIANGLE_FAN, 2 * (CIRCLE_RESOLUTION + 1) * (HEIGHT_RESOLUTION + 1), HEIGHT_RESOLUTION + 1);
	}

}
