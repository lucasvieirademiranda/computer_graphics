package br.unicamp.feec.graphics.lighting;

import br.unicamp.feec.graphics.shader.DefaultShader;
import br.unicamp.feec.utils.VectorUtils;

public abstract class Light {
    private float[] mPosition;
    private float[] mSpotDirection;
    private float mSpotExponent;
    private float mSpotCutoff;
    private float[] mDiffuseColor, mSpecularColor, mAmbientColor;
    private float mAmbientStrength;

    public Light(){
        mPosition = new float[]{0, 0, 0, 1};
        mSpotDirection = new float[]{0, 0, 0};
        mSpotExponent = 0;
        mSpotCutoff = -1;
        mDiffuseColor = new float[]{1, 1, 1};
        mAmbientColor = new float[]{1, 1, 1};
        mSpecularColor = new float[]{1, 1, 1};
        mAmbientStrength = 0.1f;
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

    public float[] getPosition(){
        return mPosition.clone();
    }

    public void setPosition(float[] pPosition){
        System.arraycopy(pPosition, 0, mPosition, 0,mPosition.length );
    }

    public float[] getSpotDirection() {
        return mSpotDirection.clone();
    }

    public void setSpotDirection(float[] pSpotDirection) {
        mSpotDirection = pSpotDirection.clone();
    }

    public float getSpotExponent() {
        return mSpotExponent;
    }

    public void setSpotExponent(float pSpotExponent) {
        mSpotExponent = pSpotExponent;
    }

    public float getSpotCutoff() {
        return mSpotCutoff;
    }

    public void setSpotCutoff(float pSpotCutoff) {
        mSpotCutoff = pSpotCutoff;
    }

    public float getAmbientStrength() {
        return mAmbientStrength;
    }

    public void setAmbientStrength(float pAmbientStrength) {
        mAmbientStrength = pAmbientStrength;
    }

    public void sendUniforms(DefaultShader pShader){
        pShader.setLightPositionUniform(mPosition);
        pShader.setLightAmbientColorUniform(mAmbientColor);
        pShader.setLightDiffuseColorUniform(mDiffuseColor);
        pShader.setLightSpecularColorUniform(mSpecularColor);
        pShader.setLightSpotDirectionUniform(mSpotDirection);
        pShader.setLightSpotExponentUniform(mSpotExponent);
        pShader.setLightSpotCutoffUniform(mSpotCutoff);
        pShader.setLightAmbientStrengthUniform(mAmbientStrength);
    }
}
