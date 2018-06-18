package br.unicamp.feec.graphics.camera;

import br.unicamp.feec.utils.MatrixUtils;

public class OrthographicCamera extends Camera{
    public OrthographicCamera(float width, float height, float near, float far){
        super(MatrixUtils.getOrthographicMatrix4x4(-width / 2f, width / 2f, - height / 2f, height / 2f, near, far));
    }
}
