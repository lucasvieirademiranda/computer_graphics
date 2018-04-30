package br.unicamp.feec.utils;

/*
 * Author: Lucas Vieira de Miranda 
 * RA: 211499
 */

public class JGLU
{	
	public float[] matrixIdentity()
	{
		float[][] identity = MatrixUtils.getIdentityMatrix4x4();
		
		float[] plainIdentity = MatrixUtils.toPlainMatrix4x4(identity);
		
		return plainIdentity;
	}
	
	public float[] matrixMultiply(float[] matrix2, float[] matrix1)
	{
		float[][] matrixA = MatrixUtils.toSquareMatrix4x4(matrix1);
		float[][] matrixB = MatrixUtils.toSquareMatrix4x4(matrix2);
		
		float[][] result = MatrixUtils.multiplyMatrix4x4(matrixB, matrixA);
		
		float[] plainResult = MatrixUtils.toPlainMatrix4x4(result);
		
		return plainResult;
	}
	
	public float[] matrixTranslate(float x, float y, float z)
	{
		float[][] result = MatrixUtils.getTransalationMatrix4x4(x, y, z);
		
		float[] plainResult = MatrixUtils.toPlainMatrix4x4(result);
		
		return plainResult;
	}
	
	public float[] matrixRotate(float angle, float x, float y, float z)
	{
		float[][] result = MatrixUtils.getRotationMatrix4x4(angle, x, y, z);
		
		float[] plainResult = MatrixUtils.toPlainMatrix4x4(result);
		
		return plainResult;
	}
	
	public float[] matrixScale(float x, float y, float z)
	{
		float[][] result = MatrixUtils.getScaleMatrix4x4(x, y, z);
		
		float[] plainResult = MatrixUtils.toPlainMatrix4x4(result);
		
		return plainResult;
	}
	
	public float[] lookAt(float ex, float ey, float ez, float cx, float cy, float cz, float ux, float uy, float uz)
	{
		float[][] result = MatrixUtils.getLookAtMatrix4x4(ex, ey, ez, cx, cy, cz, ux, uy, uz);
		
		float[] plainResult = MatrixUtils.toPlainMatrix4x4(result);
		
		return plainResult;
	}
	
	public float[] ortho(float left, float right, float bottom, float top, float near, float far)
	{
		float [][] result = MatrixUtils.getOrthographicMatrix4x4(left, right, bottom, top, near, far);
		
		float[] plainResult = MatrixUtils.toPlainMatrix4x4(result);
		
		return plainResult;
	}
	
	public float[] frustum(float left, float right, float bottom, float top, float near, float far)
	{
		float[][] result = MatrixUtils.getFrustrumMatrix4x4(left, right, bottom, top, near, far);
		
		float[] plainResult = MatrixUtils.toPlainMatrix4x4(result);
		
		return plainResult;
	}
	
	public float[] quaternionCopy(float[] quaternion)
	{
		float[] newQuaternion = new float[4];
		
		newQuaternion[0] = quaternion[0];
		newQuaternion[1] = quaternion[1];
		newQuaternion[2] = quaternion[2];
		newQuaternion[3] = quaternion[3];
		
		return newQuaternion;
	}
	
	public float quaternionNorm(float[] quaternion)
	{
		float q0 = 0f, q1 = 0f, q2 = 0f, q3 = 0f, norm = 0f;
		
		q0 = (float) Math.pow(quaternion[0], 2);
		q1 = (float) Math.pow(quaternion[1], 2);
		q2 = (float) Math.pow(quaternion[2], 2);
		q3 = (float) Math.pow(quaternion[3], 2);
		
		norm = (float) Math.sqrt(q0 + q1 + q2 + q3);
		
		return norm;
	}
	
	public void quaternionNormalize(float[] quaternion)
	{
		float norm = this.quaternionNorm(quaternion);
		
		for(int i = 0; i < 4; i++)
			quaternion[i] = quaternion[i] / norm;
	}
	
	public float[] quaternionAdd(float[] quaternion1, float[] quaternion2)
	{
		float[] newQuaternion = new float[4];
		
		for(int i = 0; i < 4; i++)
			newQuaternion[i] = quaternion1[i] + quaternion2[i];
		
		return newQuaternion;
	}
	
	public float[] quaternionMultiply(float[] quaternion1, float[] quaternion2)
	{
		float dotProduct = 0.0f; 
	    float[] scalarProduct1 = null, scalarProduct2 = null,  crossProduct = null, vector = null;
		float[] newQuaternion = new float[4];
		
		// Parte Escalar
		
		dotProduct = VectorUtils.dotProduct(quaternion1[1], quaternion1[2], quaternion1[3], quaternion2[1], quaternion2[2], quaternion2[3]);
		
		newQuaternion[0] = (float) (quaternion1[0] * quaternion2[0]) - dotProduct;
		
		// Parte Vetorial
		
		scalarProduct1 = VectorUtils.scalarProduct(quaternion1[0], quaternion2[1], quaternion2[2], quaternion2[3]);
		
		scalarProduct2 = VectorUtils.scalarProduct(quaternion2[0], quaternion1[1], quaternion1[2], quaternion1[3]);
		
		crossProduct = VectorUtils.crossProduct(quaternion1[1], quaternion1[2], quaternion1[3], quaternion2[1], quaternion2[2], quaternion2[3]);
		
		vector = VectorUtils.sum(VectorUtils.sum(scalarProduct1, scalarProduct2), crossProduct);
		
		newQuaternion[1] = vector[0];
		newQuaternion[2] = vector[1];
		newQuaternion[3] = vector[2];
		
		return newQuaternion;
	}
	
	public float[] quaternionConjugate(float[] quaternion)
	{
		float[] conjugate = new float[4];
		
		conjugate[0] = quaternion[0];
		conjugate[1] = -quaternion[1];
		conjugate[2] = -quaternion[2];
		conjugate[3] = -quaternion[3];
		
		return conjugate;
	}
	
	public float[] quaternionRotation(float angle, float x, float y, float z)
	{
		float rad = 0f, quaternionAngle = 0f;
		float[] quaternion = new float[4], normalizedVector = null;
		
		normalizedVector = VectorUtils.normalize(x, y, z);
		
		quaternionAngle = angle / 2;
		
		rad = (float) Math.toRadians(quaternionAngle);
		
		// cos 0 + u * sen 0
		
		quaternion[0] = (float) Math.cos(rad);
		quaternion[1] = (float) Math.sin(rad) * normalizedVector[0];
		quaternion[2] = (float) Math.sin(rad) * normalizedVector[1];
		quaternion[3] = (float) Math.sin(rad) * normalizedVector[2];
		
		return quaternion;
	}
	
	public float[] quaternionRotationX(float angle)
	{
		float[] quaternion = null;
		
		quaternion = quaternionRotation(angle, 1, 0, 0);
		
		return quaternion;
	}
	
	public float[] quaternionRotationY(float angle)
	{
		float[] quaternion = null;
		
		quaternion = quaternionRotation(angle, 0, 1, 0);
		
		return quaternion;
	}
	
	public float[] quaternionRotationZ(float angle)
	{
		float[] quaternion = null;
		
		quaternion = quaternionRotation(angle, 0, 0, 1);
		
		return quaternion;
	}
	
	public float[] quaternion2Matrix(float[] quaternion)
	{
		float q0 = 0f, q1 = 0f, q2 = 0f, q3 = 0f;
		
		float a11 = 0, a12 = 0, a13 = 0,
			  a21 = 0, a22 = 0, a23 = 0,
			  a31 = 0, a32 = 0, a33 = 0;
		
		q0 = quaternion[0];
		q1 = quaternion[1];
		q2 = quaternion[2];
		q3 = quaternion[3];
		
		a11 = (2 * q0 * q0) - 1 + (2 * q1 * q1);
		a12 = (2 * q1 * q2) - (2 * q0 * q3);
		a13 = (2 * q1 * q3) + (2 * q0 * q2);
		
		a21 = (2 * q1 * q2) + (2 * q0 * q3);
		a22 = (2 * q0 * q0) - 1 + (2 * q2 * q2);
		a23 = (2 * q2 * q3) - (2 * q0 * q1);
		
		a31 = (2 * q1 * q3) - (2 * q0 * q2);
		a32 = (2 * q2 * q3) + (2 * q0 * q1);
		a33 = (2 * q0 * q0) - 1 + (2 * q3 * q3);
		
		float matrix[][] = {
			{ a11, a12, a13, 0 },
			{ a21, a22, a23, 0 },
			{ a31, a32, a33, 0 },
			{ 0, 0, 0, 1 }
		};
		
		float result[] = MatrixUtils.toPlainMatrix4x4(matrix);
		
		return result;
	}
	
	public float[] matrix2QuaternionAula(float[] matrix)
	{
		float r1 = 0f, r2 = 0f, r3 = 0f, tr = 0f, angle = 0f, quaternioAngle = 0f,
			  a11 = 0f, a12 = 0f, a13 = 0f,
			  a21 = 0f, a22 = 0f, a23 = 0f,
			  a31 = 0f, a32 = 0f, a33 = 0f;
		
		float[][] matrix4x4 = MatrixUtils.toSquareMatrix4x4(matrix);
		
		a11 = matrix4x4[0][0];
		a12 = matrix4x4[0][1];
		a13 = matrix4x4[0][2];
		
		a21 = matrix4x4[1][0];
		a22 = matrix4x4[1][1];
		a23 = matrix4x4[1][2];
		
		a31 = matrix4x4[2][0];
		a32 = matrix4x4[2][1];
		a33 = matrix4x4[2][2];
		
		r1 = a12 * a23 - (a22 - 1) * a13;
		r2 = a21 * a13 - (a11 - 1) * a23;
		r3 = (a11 - 1) * (a22 - 1) - a12 * a21;
		
		tr = a11 + a22 + a33;
		
		angle = (float) Math.acos((tr - 1) / 2);
		
		quaternioAngle = angle / 2;
		
		float real = (float) Math.cos(quaternioAngle);
		
		float[] vector = VectorUtils.normalize(r1, r2, r3);
		
		vector = VectorUtils.scalarProduct((float) Math.sin(quaternioAngle), vector);
		
		float[] quaternion = {
			real,
			vector[0],
			vector[1],
			vector[2]
		};
		
		return quaternion;
	}
	
	
	// http://www.euclideanspace.com/maths/geometry/rotations/conversions/matrixToQuaternion/
	public float[] matrix2Quaternion(float[] matrix)
	{
		float r0 = 0f, r1 = 0f, r2 = 0f, r3 = 0f, tr = 0f, angle = 0f, T = 0f, S = 0f,
			  a11 = 0f, a12 = 0f, a13 = 0f,
			  a21 = 0f, a22 = 0f, a23 = 0f,
			  a31 = 0f, a32 = 0f, a33 = 0f;
		
		float[][] matrix4x4 = MatrixUtils.toSquareMatrix4x4(matrix);
		
		a11 = matrix4x4[0][0];
		a12 = matrix4x4[0][1];
		a13 = matrix4x4[0][2];
		
		a21 = matrix4x4[1][0];
		a22 = matrix4x4[1][1];
		a23 = matrix4x4[1][2];
		
		a31 = matrix4x4[2][0];
		a32 = matrix4x4[2][1];
		a33 = matrix4x4[2][2];
		
		tr = a11 + a22 + a33;
		
		if (tr > 0)
		{	
			S = 2 * (float) Math.sqrt(tr + 1);
			
			angle = (float) Math.acos(0.25f * S);
			
			r1 = (a32 - a23) / S;
			r2 = (a13 - a31) / S;
			r3 = (a21 - a12) / S;
		}
		else if (a11 > a22 && a11 > a33)
		{
			S = 2 * (float) Math.sqrt(a11 - a22 - a33 + 1); // S = 4*qx
			
			angle = (float) Math.acos((a32 - a23) / S);
			
			r1 = 0.25f * S;
			r2 = (a12 + a21) / S;
			r3 = (a13 + a31) / S;
		}
		else if (a22 > a11 && a22 > a33)
		{
			S = 2 * (float) Math.sqrt(a22 - a11 - a33 + 1); // S = 4*qy
			
			angle = (float) Math.acos(a13 - a31);
			
			r1 = (a12 + a21) / S;
			r2 = 0.25f * S;
			r3 = (a23 + a32) / S;
		}
		else
		{
			S = 2 * (float) Math.sqrt(a33 - a11 - a21 + 1);
			
			angle = (float) Math.acos(a21 - a12);
			
			r1 = (a13 + a31) / S;
			r2 = (a23 + a32) / S;
			r3 = 0.25f * S;
		}
		
		angle = 2 * (float) Math.toDegrees(angle);
		
		float[] quaternion = quaternionRotation(angle, r1, r2, r3);
		
		return quaternion;
	}
	
	public float[] drawParabola(float focus, float end, int triangles)
	{
		if(focus < 0) focus = -1 * focus;
		
		if(end < 0) end = -1 * end;
		
		triangles = triangles * 2;
		
		int iterations = triangles + 1;
		
		float[] vertices = new float[(triangles + 2) * 4];
		
		float maxY = (float) Math.sqrt(4 * focus * end);
		float minY = (float) -1 * maxY;
		
		float maxT = maxY / (2 * focus);
		float minT = minY / (2 * focus);
		
		float step = (maxT - minT) / triangles;
		
		float x = end, y = minY;
		
		vertices[0] = x; vertices[1] = 0.0f; vertices[2] = 0.0f; vertices[3] = 1.0f;
		
		vertices[4] = x; vertices[5] = y; vertices[6] = 0.0f; vertices[7] = 1.0f;
		
		for(int i = 2; i <= iterations; i++)
		{
			x = x + y * step + focus * step * step;
			y = y + 2 * focus * step;
			
			vertices[i * 4] = x; vertices[i * 4 + 1] = y; vertices[i * 4 + 2] = 0.0f; vertices[i * 4 + 3] = 1.0f;
		}
		
		return vertices;
	}
	
	public float[] drawEllipse(float a, float b, int triangles)
	{
		if (a < 0) a = -1 * a;
		
		if (b < 0) b = -1 * b;
		
		triangles = 4 * triangles;
		
		int iterations = triangles + 1;
		
		float[] vertices = new float[(triangles + 2) * 4];
		
		float stepSize = (float) (2 * Math.PI / triangles);
		
		float x = 0, y = 0, step = 0;
		
		vertices[0] = 0.0f; vertices[1] = 0.0f; vertices[2] = 0.0f; vertices[3] = 1.0f;
		
		for(int i = 1; i <= iterations; i++)
		{
			x = (float) (a * Math.cos(step));
			y = (float) (b * Math.sin(step));
			
			vertices[i*4] = x; vertices[i*4 + 1] = y; vertices[i*4 + 2] = 0.0f; vertices[i*4 + 3] = 1.0f;
			
			step += stepSize;
		}

		return vertices;
	}
	
	public float[] drawHyperbola(float a, float b, float end, int triangles)
	{
		if (a < 0) a = -1 * a;
		
		if (b < 0) b = -1 * b;
		
		if (end < 0) end = -1 * end;
			
		triangles = triangles * 2;
		
		int iterations = triangles + 1;
		
		float[] vertices = new float[(triangles + 2) * 4];
		
		float maxY = (float) Math.sqrt((b * b * end * end / (a * a)) - (b * b));
		float minY = -1 * maxY;
		
		float maxT = (float) Math.atan(maxY / b);
		float minT = -1 * maxT;
		
		float step = (maxT - minT) / triangles;
		
		float x = end, y = minY;
		
		vertices[0] = x; vertices[1] = 0.0f; vertices[2] = 0.0f; vertices[3] = 1.0f;
		
		vertices[4] = x; vertices[5] = y; vertices[6] = 0.0f; vertices[7] = 1.0f;
		
		for (int i = 2; i <= iterations; i++)
		{
			x = (float)  ((b * x) / (b * Math.cos(step) - y * Math.sin(step)));
			y = (float)  ((b * y + b * b * Math.tan(step)) / (b - y * Math.tan(step)));
			vertices[i*4] = x; vertices[i*4 + 1] = y; vertices[i*4 + 2] = 0.0f; vertices[i*4 + 3] = 1.0f;
		}
		
		return vertices;
	}
}