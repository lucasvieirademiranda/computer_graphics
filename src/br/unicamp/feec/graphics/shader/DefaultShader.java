package br.unicamp.feec.graphics.shader;

import com.jogamp.opengl.GL4;
import com.jogamp.opengl.GLContext;

public class DefaultShader extends BaseShader{
	private int mModelViewMatrixUniformLocation, mProjectionMatrixUniformLocation;
	private int mPositionAttributeLocation;

	public DefaultShader(String pVertexShader, String pFragmentShader) {
		super(pVertexShader, pFragmentShader);
	}
	
	@Override
	public void init() {
		super.init();
		
		GL4 gl = GLContext.getCurrentGL().getGL4();
		mModelViewMatrixUniformLocation = gl.glGetUniformLocation(mProgramId, "u_modelViewMatrix");
		mProjectionMatrixUniformLocation = gl.glGetUniformLocation(mProgramId, "u_projectionMatrix");
		
		mPositionAttributeLocation = gl.glGetAttribLocation(mProgramId, "a_position");
	}
	
	@Override
	public void bindAttributes(){
		GL4 gl = GLContext.getCurrentGL().getGL4();
		
		// Associate attribute with the last bound VBO
		gl.glVertexAttribPointer(mPositionAttributeLocation, 4, GL4.GL_FLOAT, false, 0, 0);
		gl.glEnableVertexAttribArray(mPositionAttributeLocation);
	}
	
	public void setModelViewMatrixUniform(float[] matrix){
		GL4 gl = GLContext.getCurrentGL().getGL4();
		
		gl.glUniformMatrix4fv(mModelViewMatrixUniformLocation, 1, false, matrix, 0);
	}
	
	public void setProjectionMatrixUniform(float[] matrix){
		GL4 gl = GLContext.getCurrentGL().getGL4();
		
		gl.glUniformMatrix4fv(mProjectionMatrixUniformLocation, 1, false, matrix, 0);
	}
}
