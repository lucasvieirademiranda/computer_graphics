package br.unicamp.feec.graphics.camera;

import br.unicamp.feec.utils.MatrixUtils;

public class PerspectiveCamera extends Camera {
    public PerspectiveCamera(float fov, float ratio, float near, float far){
        super(MatrixUtils.getPerspectiveMatrix4x4(fov, ratio, near, far));
    }
}
