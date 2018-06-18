package br.unicamp.feec.graphics.shader;

import com.jogamp.opengl.GL4;
import com.jogamp.opengl.GLContext;

public class DefaultShader extends BaseShader{
	private int mModelViewMatrixUniformLocation, mProjectionMatrixUniformLocation, mNormalMatrixUniformLocation, mModelMatrixUniformLocation, mViewMatrixUniformLocation;

	private int mViewPositionUniformLocation;

	private int mMaterialAmbientColorUniformLocation, mMaterialDiffuseColorUniformLocation, mMaterialSpecularColorUniformLocation, mMaterialShininessUniformLocation;

	private int mLightPositionUniformLocation;
	private int mLightAmbientStrengthUniformLocation;
	private int mLightAmbientColorUniformLocation, mLightDiffuseColorUniformLocation, mLightSpecularColorUniformLocation;
	private int mLightSpotDirectionUniformLocation, mLightSpotExponentUniformLocation, mLightSpotCutoffUniformLocation;

	private int mPositionAttributeLocation, mNormalAttributeLocation;

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
		mMaterialAmbientColorUniformLocation = getUniformLocation(gl, "u_material.ambientColor");
		mMaterialDiffuseColorUniformLocation = getUniformLocation(gl, "u_material.diffuseColor");
		mMaterialSpecularColorUniformLocation = getUniformLocation(gl, "u_material.specularColor");
		mMaterialShininessUniformLocation = getUniformLocation(gl, "u_material.shininess");
		mLightPositionUniformLocation = getUniformLocation(gl, "u_light.position");
		mLightAmbientColorUniformLocation = getUniformLocation(gl, "u_light.ambientColor");
		mLightDiffuseColorUniformLocation = getUniformLocation(gl, "u_light.diffuseColor");
		mLightSpecularColorUniformLocation = getUniformLocation(gl, "u_light.specularColor");
		mLightAmbientStrengthUniformLocation = getUniformLocation(gl, "u_light.ambientStrength");
		mLightSpotDirectionUniformLocation = getUniformLocation(gl, "u_light.spotDirection");
		mLightSpotExponentUniformLocation = getUniformLocation(gl, "u_light.spotExponent");
		mLightSpotCutoffUniformLocation = getUniformLocation(gl, "u_light.spotCutoff");
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

	public void setMaterialAmbientColorUniform(float[] vector) {
		GL4 gl = GLContext.getCurrentGL().getGL4();

		gl.glUniform3fv(mMaterialAmbientColorUniformLocation, 1, vector, 0);
	}

	public void setMaterialDiffuseColorUniform(float[] vector) {
		GL4 gl = GLContext.getCurrentGL().getGL4();

		gl.glUniform3fv(mMaterialDiffuseColorUniformLocation, 1, vector, 0);
	}

	public void setMaterialSpecularColorUniform(float[] vector) {
		GL4 gl = GLContext.getCurrentGL().getGL4();

		gl.glUniform3fv(mMaterialSpecularColorUniformLocation, 1, vector, 0);
	}

	public void setMaterialShininessUniform(float value){
		GL4 gl = GLContext.getCurrentGL().getGL4();

		gl.glUniform1f(mMaterialShininessUniformLocation, value);
	}

	public void setLightPositionUniform(float[] vector){
		GL4 gl = GLContext.getCurrentGL().getGL4();

		gl.glUniform4fv(mLightPositionUniformLocation, 1, vector, 0);
	}

	public void setLightAmbientColorUniform(float[] vector) {
		GL4 gl = GLContext.getCurrentGL().getGL4();

		gl.glUniform3fv(mLightAmbientColorUniformLocation, 1, vector, 0);
	}

	public void setLightDiffuseColorUniform(float[] vector) {
		GL4 gl = GLContext.getCurrentGL().getGL4();

		gl.glUniform3fv(mLightDiffuseColorUniformLocation, 1, vector, 0);
	}

	public void setLightSpecularColorUniform(float[] vector) {
		GL4 gl = GLContext.getCurrentGL().getGL4();

		gl.glUniform3fv(mLightSpecularColorUniformLocation, 1, vector, 0);
	}

	public void setLightSpotDirectionUniform(float[] vector){
		GL4 gl = GLContext.getCurrentGL().getGL4();

		gl.glUniform3fv(mLightSpotDirectionUniformLocation, 1, vector, 0);
	}

	public void setLightSpotExponentUniform(float value){
		GL4 gl = GLContext.getCurrentGL().getGL4();

		gl.glUniform1f(mLightSpotExponentUniformLocation, value);
	}

	public void setLightSpotCutoffUniform(float value){
		GL4 gl = GLContext.getCurrentGL().getGL4();

		gl.glUniform1f(mLightSpotCutoffUniformLocation, value);
	}

	public void setViewPositionUniform(float[] vector){
		GL4 gl = GLContext.getCurrentGL().getGL4();

		gl.glUniform3fv(mViewPositionUniformLocation, 1, vector, 0);
	}

	public void setLightAmbientStrengthUniform(float value){
		GL4 gl = GLContext.getCurrentGL().getGL4();

		gl.glUniform1f(mLightAmbientStrengthUniformLocation, value);
	}
}
