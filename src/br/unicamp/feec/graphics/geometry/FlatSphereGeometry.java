package br.unicamp.feec.graphics.geometry;

import java.util.ArrayList;
import java.util.Arrays;

import com.jogamp.opengl.GL4;

import br.unicamp.feec.graphics.shader.BaseShader;
import br.unicamp.feec.utils.VectorUtils;

public class FlatSphereGeometry extends Geometry
{
	private static final int DETAIL_LEVEL = 0;
	
	public FlatSphereGeometry(BaseShader pShader)
	{
		super(pShader, buildSphere(DETAIL_LEVEL), new int[] {});
	}
	
	protected void drawGeometry(GL4 gl)
	{
		int size = ((int) Math.pow(4, DETAIL_LEVEL)) * 20 * 3;
		gl.glDrawArrays(GL4.GL_TRIANGLES, 0,  size);		
	}
	
	private static float[] buildSphere(int detailLevel)
	{
		ArrayList<Float> vertices = new ArrayList<Float>();
		
		vertices = buildIcosaedron();
		
		for(int i = 0; i < detailLevel; i++)
			vertices = subdivideIcosaedron(vertices);
		
		vertices = normalizeIcosaedron(vertices);
		
		float[] sphere = new float[vertices.size()];
		
		for(int i = 0; i < vertices.size(); i++)
			sphere[i] = vertices.get(i);
		
		return sphere;
	}

	private static ArrayList<Float> normalizeIcosaedron(ArrayList<Float> icosaedron)
	{
		ArrayList<Float> vertices = new ArrayList<Float>();
		
		float[] point = null, normalized = null;
		int index = 0;
		
		for (int i = 0; i < icosaedron.size(); i += 6)
		{
			index = i;
			
			point = VectorUtils.create(icosaedron.get(index), 
									   icosaedron.get(index + 1), 
									   icosaedron.get(index + 2));
			
			normalized = VectorUtils.normalize(point);
			
			vertices.add(normalized[0]);
			vertices.add(normalized[1]);
			vertices.add(normalized[2]);
			
			index = (i + 3);
			
			vertices.add(icosaedron.get(index));
			vertices.add(icosaedron.get(index + 1));
			vertices.add(icosaedron.get(index + 2));
		}
		
		return vertices;
	}
	
    ///      p0
    ///     /  \
    ///    m02-m01
    ///   /  \ /  \
    /// p2---m12---p1
	
	private static ArrayList<Float> subdivideIcosaedron(ArrayList<Float> icosaedron)
	{
		ArrayList<Float> vertices = new ArrayList<Float>();
		
		float[] p0 = null, p1 = null, p2 = null,
				m01 = null, m12 = null, m02 = null;
		
		int index = 0;
		
		// 3 Vertices (A Triangle)
		for(int i = 0; i < icosaedron.size(); i += 18)
		{
			index = i;
			
			p0 = VectorUtils.create(icosaedron.get(index), 
									icosaedron.get(index + 1), 
									icosaedron.get(index + 2));
			
			index = (i + 6);
			
			p1 = VectorUtils.create(icosaedron.get(index), 
									icosaedron.get(index + 1), 
									icosaedron.get(index + 2));
			
			index = (i + 12);
			
			p2 = VectorUtils.create(icosaedron.get(index), 
									icosaedron.get(index + 1), 
									icosaedron.get(index + 2));
			
			m01 = VectorUtils.scalarProduct(0.5f, VectorUtils.sum(p0, p1));
			m12 = VectorUtils.scalarProduct(0.5f, VectorUtils.sum(p1, p2));
			m02 = VectorUtils.scalarProduct(0.5f, VectorUtils.sum(p2, p0));
		
			// Triangle 1
			vertices.add(p0[0]); vertices.add(p0[1]); vertices.add(p0[2]);
			vertices.add(0f); vertices.add(0f); vertices.add(0f);
			
			vertices.add(m01[0]); vertices.add(m01[1]); vertices.add(m01[2]);
			vertices.add(0f); vertices.add(0f); vertices.add(0f);
			
			vertices.add(m02[0]); vertices.add(m02[1]); vertices.add(m02[2]);
			vertices.add(0f); vertices.add(0f); vertices.add(0f);
			
			// Triangle 2
			vertices.add(p1[0]); vertices.add(p1[1]); vertices.add(p1[2]);
			vertices.add(0f); vertices.add(0f); vertices.add(0f);
			
			vertices.add(m12[0]); vertices.add(m12[1]); vertices.add(m12[2]);
			vertices.add(0f); vertices.add(0f); vertices.add(0f);
			
			vertices.add(m01[0]); vertices.add(m01[1]); vertices.add(m01[2]);
			vertices.add(0f); vertices.add(0f); vertices.add(0f);
			
			// Triangle 3
			vertices.add(p2[0]); vertices.add(p2[1]); vertices.add(p2[2]);
			vertices.add(0f); vertices.add(0f); vertices.add(0f);
			
			vertices.add(m02[0]); vertices.add(m02[1]); vertices.add(m02[2]);
			vertices.add(0f); vertices.add(0f); vertices.add(0f);
			
			vertices.add(m12[0]); vertices.add(m12[1]); vertices.add(m12[2]);
			vertices.add(0f); vertices.add(0f); vertices.add(0f);
			
			// Triangle 4
			vertices.add(m02[0]); vertices.add(m02[1]); vertices.add(m02[2]);
			vertices.add(0f); vertices.add(0f); vertices.add(0f);
			
			vertices.add(m01[0]); vertices.add(m01[1]); vertices.add(m01[2]);
			vertices.add(0f); vertices.add(0f); vertices.add(0f);
			
			vertices.add(m12[0]); vertices.add(m12[1]); vertices.add(m12[2]);
			vertices.add(0f); vertices.add(0f); vertices.add(0f);
		}
		
		calculateNormal(vertices);
		
		return vertices;
	}
	
	private static ArrayList<Float> buildIcosaedron()
	{		
		float x = 0.525731112119133606f;
		float z = 0.850650808352039932f;

		ArrayList<Float> vertices = new ArrayList<Float>(Arrays.asList(
			-x, 0f, z, // 0
			0f, 0f, 0f, // Normal
			0f, z, x,  // 4
			0f, 0f, 0f, // Normal
			x, 0f, z,  // 1
			0f, 0f, 0f, // Normal
			
			-x, 0f, z, // 0
			0f, 0f, 0f, // Normal
			-z, x, 0f, // 9
			0f, 0f, 0f, // Normal
			0f, z, x,  // 4
			0f, 0f, 0f, // Normal
			
			-z, x, 0f, // 9
			0f, 0f, 0f, // Normal
			0f, z, -x, // 5
			0f, 0f, 0f, // Normal
			0f, z, x,  // 4
			0f, 0f, 0f, // Normal
			
			0f, z, x,  // 4
			0f, 0f, 0f, // Normal
			0f, z, -x, // 5
			0f, 0f, 0f, // Normal
			z, x, 0f,  // 8
			0f, 0f, 0f, // Normal
			
			0f, z, x,  // 4
			0f, 0f, 0f, // Normal
			z, x, 0f,  // 8
			0f, 0f, 0f, // Normal
			x, 0f, z,  // 1
			0f, 0f, 0f, // Normal
			
			z, x, 0f,  // 8
			0f, 0f, 0f, // Normal
			z, -x, 0f, // 10
			0f, 0f, 0f, // Normal
			x, 0f, z,  // 1
			0f, 0f, 0f, // Normal
			
			z, x, 0f,  // 8
			0f, 0f, 0f, // Normal
			x, 0f, -z, // 3
			0f, 0f, 0f, // Normal
			z, -x, 0f, // 10
			0f, 0f, 0f, // Normal
			
			0f, z, -x, // 5
			0f, 0f, 0f, // Normal
			x, 0f, -z, // 3
			0f, 0f, 0f, // Normal
			z, x, 0f,  // 8
			0f, 0f, 0f, // Normal
			
			0f, z, -x,  // 5
			0f, 0f, 0f, // Normal
			-x, 0f, -z, // 2
			0f, 0f, 0f, // Normal
			x, 0f, -z,  // 3
			0f, 0f, 0f, // Normal
			
			-x, 0f, -z, // 2
			0f, 0f, 0f, // Normal
			0f, -z, -x, // 7
			0f, 0f, 0f, // Normal
			x, 0f, -z,  // 3
			0f, 0f, 0f, // Normal
			
			0f, -z, -x, // 7
			0f, 0f, 0f, // Normal
			z, -x, 0f, // 10
			0f, 0f, 0f, // Normal
			x, 0f, -z,  // 3
			0f, 0f, 0f, // Normal
			
			0f, -z, -x, // 7
			0f, 0f, 0f, // Normal
			0f, -z, x, // 6
			0f, 0f, 0f, // Normal
			z, -x, 0f, // 10
			0f, 0f, 0f, // Normal
			
			0f, -z, -x, // 7
			0f, 0f, 0f, // Normal
			-z, -x, 0f, // 11
			0f, 0f, 0f, // Normal
			0f, -z, x, // 6
			0f, 0f, 0f, // Normal
			
			-z, -x, 0f, // 11
			0f, 0f, 0f, // Normal
			-x, 0f, z, // 0
			0f, 0f, 0f, // Normal
			0f, -z, x, // 6
			0f, 0f, 0f, // Normal
			
			-x, 0f, z, // 0
			0f, 0f, 0f, // Normal
			x, 0f, z,  // 1
			0f, 0f, 0f, // Normal
			0f, -z, x, // 6
			0f, 0f, 0f, // Normal
			
			0f, -z, x, // 6
			0f, 0f, 0f, // Normal
			x, 0f, z,  // 1
			0f, 0f, 0f, // Normal
			z, -x, 0f, // 10
			0f, 0f, 0f, // Normal
			
			-z, x, 0f, // 9
			0f, 0f, 0f, // Normal
			-x, 0f, z, // 0
			0f, 0f, 0f, // Normal
			-z, -x, 0f, // 11
			0f, 0f, 0f, // Normal
			
			-z, x, 0f, // 9
			0f, 0f, 0f, // Normal
			-z, -x, 0f, // 11
			0f, 0f, 0f, // Normal
			-x, 0f, -z, // 2
			0f, 0f, 0f, // Normal
			
			-z, x, 0f, // 9
			0f, 0f, 0f, // Normal
			-x, 0f, -z, // 2
			0f, 0f, 0f, // Normal
			0f, z, -x,  // 5
			0f, 0f, 0f, // Normal
			
			0f, -z, -x, // 7
			0f, 0f, 0f, // Normal
			-x, 0f, -z, // 2
			0f, 0f, 0f, // Normal
			-z, -x, 0f, // 11
			0f, 0f, 0f // Normal
		));
		
		calculateNormal(vertices);
		
		return vertices;
	}
	
	private static void calculateNormal(ArrayList<Float> icosaedron)
	{
		float[] p1 = null, p2 = null, p3 = null,
				v1 = null, v2 = null, normal = null;
		
		int index = 0;
		
		for(int i = 0; i < icosaedron.size(); i += 18)
		{
			index = i;
			
			p1 = VectorUtils.create(icosaedron.get(index), icosaedron.get(index + 1), icosaedron.get(index + 2));
			
			index = (i + 6);
			
			p2 = VectorUtils.create(icosaedron.get(index), icosaedron.get(index + 1), icosaedron.get(index + 2));
			
			index = (i + 12);
			
			p3 = VectorUtils.create(icosaedron.get(index), icosaedron.get(index + 1), icosaedron.get(index + 2));
			
			v1 = VectorUtils.normalize(VectorUtils.subtract(p2, p1));
			v2 = VectorUtils.normalize(VectorUtils.subtract(p3, p1));
			
			normal = VectorUtils.crossProduct(v2, v1);
			
			index = i + 3;
			
			icosaedron.set(index, normal[0]);
			icosaedron.set(index + 1, normal[1]);
			icosaedron.set(index + 2, normal[2]);
			
			index = i + 9;
			
			icosaedron.set(index, normal[0]);
			icosaedron.set(index + 1, normal[1]);
			icosaedron.set(index + 2, normal[2]);
			
			index = i + 15;
			
			icosaedron.set(index, normal[0]);
			icosaedron.set(index + 1, normal[1]);
			icosaedron.set(index + 2, normal[2]);
		}
	}
}
