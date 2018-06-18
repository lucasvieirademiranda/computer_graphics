package br.unicamp.feec.graphics.material;

import br.unicamp.feec.graphics.shader.DefaultShader;
import br.unicamp.feec.utils.ColorUtils;

public class Material {
	private float[] mAmbientColor, mDiffuseColor, mSpecularColor;
	private float mShininess;

	public Material(){
		mAmbientColor = ColorUtils.create(1, 1, 1, 1);
		mDiffuseColor = ColorUtils.create(1, 1, 1, 1);
		mSpecularColor = ColorUtils.create(1, 1, 1, 1);
		mShininess = 64;
	}

	public float[] getAmbientColor() {
		return mAmbientColor.clone();
	}

	public void setAmbientColor(float[] pAmbientColor) {
		System.arraycopy(pAmbientColor, 0, mAmbientColor, 0,mAmbientColor.length );
	}

	public float[] getDiffuseColor() {
		return mDiffuseColor.clone();
	}

	public void setDiffuseColor(float[] pDiffuseColor) {
		System.arraycopy(pDiffuseColor, 0, mDiffuseColor, 0,mDiffuseColor.length );
	}

	public float[] getSpecularColor() {
		return mSpecularColor.clone();
	}

	public void setSpecularColor(float[] pSpecularColor) {
		System.arraycopy(pSpecularColor, 0, mSpecularColor, 0,mSpecularColor.length );
	}

	public float getShineness() {
		return mShininess;
	}

	public void setShineness(float pShininess) {
		mShininess = pShininess;
	}

	public void sendUniforms(DefaultShader pShader) {
		pShader.setMaterialAmbientColorUniform(mAmbientColor);
		pShader.setMaterialDiffuseColorUniform(mDiffuseColor);
		pShader.setMaterialSpecularColorUniform(mSpecularColor);
		pShader.setMaterialShininessUniform(mShininess);
	}
}
