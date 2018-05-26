package br.unicamp.feec.graphics.shader;

import com.jogamp.opengl.GL4;
import com.jogamp.opengl.GLContext;

public class DefaultShader extends BaseShader{
	private int mModelViewMatrixUniformLocation, mProjectionMatrixUniformLocation, mNormalMatrixUniformLocation, mModelMatrixUniformLocation, mViewMatrixUniformLocation;
	private int mAmbientColorUniformLocation, mAmbientStrengthUniformLocation;
	private int mLightPositionUniformLocation, mLightColorUniformLocation;
	private int mDiffuseColorUniformLocation;
	private int mPositionAttributeLocation, mNormalAttributeLocation;
	private int mSpecularStrengthUniformLocation;
	private int mViewPositionUniformLocation;

	public DefaultShader(String pVertexShader, String pFragmentShader) {
		super(pVertexShader, pFragmentShader);
	}
	
	@Override
	public void init() {
		super.init();
		
		GL4 gl = GLContext.getCurrentGL().getGL4();
		mModelViewMatrixUniformLocation = getUniformLocation(gl, "u_modelViewMatrix");
		mModelMatrixUniformLocation = getUniformLocation(gl, "u_modelMatrix");
		//mViewMatrixUniformLocation = getUniformLocation(gl, "u_viewMatrix");
		mProjectionMatrixUniformLocation = getUniformLocation(gl, "u_projectionMatrix");
		mNormalMatrixUniformLocation = getUniformLocation(gl, "u_normalMatrix");
		mAmbientColorUniformLocation = getUniformLocation(gl, "u_ambientColor");
		mAmbientStrengthUniformLocation = getUniformLocation(gl, "u_ambientStrength");
		mLightPositionUniformLocation = getUniformLocation(gl, "u_lightPosition");
		mLightColorUniformLocation = getUniformLocation(gl, "u_lightColor");
		mDiffuseColorUniformLocation = getUniformLocation(gl, "u_diffuseColor");
		mSpecularStrengthUniformLocation = getUniformLocation(gl, "u_specularStrength");
		mViewPositionUniformLocation = getUniformLocation(gl, "u_viewPosition");
		
		mPositionAttributeLocation = gl.glGetAttribLocation(mProgramId, "a_position");
		mNormalAttributeLocation = gl.glGetAttribLocation(mProgramId, "a_normal");
	}
	
	@Override
	public void bindAttributes(){
		GL4 gl = GLContext.getCurrentGL().getGL4();
		
		// Associate attribute with the last bound VBO
		
		gl.glVertexAttribPointer(mPositionAttributeLocation, 3, GL4.GL_FLOAT, false, (Float.SIZE / Byte.SIZE) * 6, 0);
		gl.glEnableVertexAttribArray(mPositionAttributeLocation);
		
		gl.glVertexAttribPointer(mNormalAttributeLocation, 3, GL4.GL_FLOAT, false, (Float.SIZE / Byte.SIZE) * 6, (Float.SIZE / Byte.SIZE) * 3);
		//gl.glVertexAttribPointer(mNormalAttributeLocation, 3, GL4.GL_FLOAT, false, Float.SIZE * 6, Float.SIZE * 3);
		gl.glEnableVertexAttribArray(mNormalAttributeLocation);
	}
	
	public void setModelViewMatrixUniform(float[] matrix){
		GL4 gl = GLContext.getCurrentGL().getGL4();
		
		gl.glUniformMatrix4fv(mModelViewMatrixUniformLocation, 1, false, matrix, 0);
	}

	public void setModelMatrixUniform(float[] matrix){
		GL4 gl = GLContext.getCurrentGL().getGL4();

		gl.glUniformMatrix4fv(mModelMatrixUniformLocation, 1, false, matrix, 0);
	}

	public void setViewMatrixUniform(float[] matrix){
		GL4 gl = GLContext.getCurrentGL().getGL4();

		gl.glUniformMatrix4fv(mViewMatrixUniformLocation, 1, false, matrix, 0);
	}
	
	public void setProjectionMatrixUniform(float[] matrix){
		GL4 gl = GLContext.getCurrentGL().getGL4();
		
		gl.glUniformMatrix4fv(mProjectionMatrixUniformLocation, 1, false, matrix, 0);
	}
	
	public void setNormalMatrixUniform(float[] matrix){
		GL4 gl = GLContext.getCurrentGL().getGL4();
		
		gl.glUniformMatrix3fv(mNormalMatrixUniformLocation, 1, false, matrix, 0);
	}
	
	public void setAmbientColorUniform(float[] vector){
		GL4 gl = GLContext.getCurrentGL().getGL4();
		
		gl.glUniform3fv(mAmbientColorUniformLocation, 1, vector, 0);
	}
	
	public void setAmbientStrengthUniform(float value){
		GL4 gl = GLContext.getCurrentGL().getGL4();
		
		gl.glUniform1f(mAmbientStrengthUniformLocation, value);
	}
	
	public void setLighPositionUniform(float[] vector){
		GL4 gl = GLContext.getCurrentGL().getGL4();
		
		gl.glUniform4fv(mLightPositionUniformLocation, 1, vector, 0);
	}
	
	public void setLightColorUniform(float[] vector){
		GL4 gl = GLContext.getCurrentGL().getGL4();
		
		gl.glUniform3fv(mLightColorUniformLocation, 1, vector, 0);
	}
	
	public void setDiffuseColorUniform(float[] vector){
		GL4 gl = GLContext.getCurrentGL().getGL4();
		
		gl.glUniform4fv(mDiffuseColorUniformLocation, 1, vector, 0);
	}

	public void setSpecularStrengthUniform(float value){
		GL4 gl = GLContext.getCurrentGL().getGL4();

		gl.glUniform1f(mSpecularStrengthUniformLocation, value);
	}

	public void setViewPositionUniform(float[] vector){
		GL4 gl = GLContext.getCurrentGL().getGL4();

		gl.glUniform3fv(mViewPositionUniformLocation, 1, vector, 0);
	}
}
