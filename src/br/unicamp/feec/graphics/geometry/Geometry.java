package br.unicamp.feec.graphics.geometry;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import com.jogamp.common.nio.Buffers;
import com.jogamp.opengl.GL4;
import com.jogamp.opengl.GLContext;
import com.sun.media.jfxmediaimpl.MediaDisposer.Disposable;

import br.unicamp.feec.graphics.shader.BaseShader;

public abstract class Geometry implements Disposable{
	private BaseShader mShader;
	private int mVaoId, mVboId, mVioId;
	
	public Geometry(BaseShader pShader, float[] pVertices, int[] pIndices){
		this(pShader, Buffers.newDirectFloatBuffer(pVertices), Buffers.newDirectIntBuffer(pIndices));
	}
	
	public Geometry(BaseShader pShader, FloatBuffer pVertices, IntBuffer pIndices){
		mShader = pShader;
		
		GL4 gl = GLContext.getCurrentGL().getGL4();

		int[] vaoPointer = new int[1];
		gl.glGenVertexArrays(1, vaoPointer, 0);
		mVaoId = vaoPointer[0];
		gl.glBindVertexArray(mVaoId);
		
		int[] boPointers = new int[2];
		gl.glGenBuffers(2, boPointers, 0);
		mVboId = boPointers[0];
		mVioId = boPointers[1];
		
    	gl.glBindBuffer(GL4.GL_ARRAY_BUFFER, mVboId);
    	gl.glBufferData(GL4.GL_ARRAY_BUFFER, pVertices.remaining() * (Float.SIZE / Byte.SIZE), pVertices, GL4.GL_STATIC_DRAW);
    	
    	gl.glBindBuffer(GL4.GL_ELEMENT_ARRAY_BUFFER, mVioId);
    	gl.glBufferData(GL4.GL_ELEMENT_ARRAY_BUFFER, pIndices.remaining() * (Integer.SIZE / Byte.SIZE), pIndices, GL4.GL_STATIC_DRAW);
    	
    	mShader.bindAttributes();

    	gl.glBindVertexArray(0);
	}
	
	public void bind(){
		GLContext.getCurrentGL().getGL4().glBindVertexArray(mVaoId);
	}
	
	public void unbind(){
		GLContext.getCurrentGL().getGL4().glBindVertexArray(0);
	}
	
	@Override
	public void dispose() {
		GL4 gl = GLContext.getCurrentGL().getGL4();
		
		gl.glDeleteBuffers(2, new int[] { mVboId, mVioId }, 0);
		gl.glDeleteVertexArrays(1, new int[] { mVaoId }, 0);
	}
	
	public void draw(GL4 gl){
		bind();
		drawGeometry(gl);
		unbind();
	}

	protected abstract void drawGeometry(GL4 gl);
}
