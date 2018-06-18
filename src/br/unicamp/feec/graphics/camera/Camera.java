package br.unicamp.feec.graphics.camera;

import br.unicamp.feec.utils.MatrixUtils;

public abstract class Camera {
    private float[][] mViewMatrix;
    private float [][] mProjectionMatrix;

    protected Camera(float[][] pProjectionMatrix){
        mProjectionMatrix = pProjectionMatrix;
        mViewMatrix = MatrixUtils.getIdentityMatrix4x4();
    }

    public float[][] getViewMatrix() {
        return mViewMatrix.clone();
    }

    public void setViewMatrix(float[][] pViewMatrix) {
        mViewMatrix = pViewMatrix.clone();
    }

    public float[][] getProjectionMatrix() {
        return mProjectionMatrix.clone();
    }
}
