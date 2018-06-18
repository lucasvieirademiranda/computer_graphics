package br.unicamp.feec.graphics.mesh;

import br.unicamp.feec.graphics.camera.Camera;
import br.unicamp.feec.graphics.geometry.Geometry;
import br.unicamp.feec.graphics.material.Material;
import br.unicamp.feec.graphics.shader.DefaultShader;
import br.unicamp.feec.utils.MatrixUtils;
import com.jogamp.opengl.GL4;

public class Mesh {
    private float[][] mTransformationMatrix;
    private Geometry mGeometry;
    private Material mMaterial;

    public Mesh(Geometry pGeometry, Material pMaterial){
        mTransformationMatrix = MatrixUtils.getIdentityMatrix4x4();
        mGeometry = pGeometry;
        mMaterial = pMaterial;
    }

    float[][] getTransformationMatrix(){
        return mTransformationMatrix.clone();
    }

    void setTransformationMatrix(float[][] pTransformationMatrix){
        mTransformationMatrix = pTransformationMatrix.clone();
    }

    public void draw(GL4 pGl, DefaultShader pShader, Camera pCamera){
        float[][] viewMatrix = pCamera.getViewMatrix();

        pShader.setProjectionMatrixUniform(MatrixUtils.toPlainMatrix4x4(pCamera.getProjectionMatrix()));
        pShader.setModelMatrixUniform(MatrixUtils.toPlainMatrix4x4(mTransformationMatrix));
        pShader.setModelViewMatrixUniform(MatrixUtils.toPlainMatrix4x4(MatrixUtils.multiplyMatrix4x4(viewMatrix, mTransformationMatrix)));
        //pShader.setNormalMatrixUniform(MatrixUtils.toPlainMatrix3x3(MatrixUtils.transposeMatrix4x4(MatrixUtils.inverse(mTransformationMatrix))));TODO: implementar inversa da matrix 4x4
        pShader.setNormalMatrixUniform(MatrixUtils.toPlainMatrix3x3(mTransformationMatrix));
        pShader.setViewPositionUniform(new float[]{ -viewMatrix[3][0], -viewMatrix[3][1], -viewMatrix[3][2], 1 });
        mMaterial.sendUniforms(pShader);
        mGeometry.draw(pGl);
    }
}
