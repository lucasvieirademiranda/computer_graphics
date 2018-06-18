package br.unicamp.feec.graphics.lighting;

public class DirectionalLight extends Light {
    public DirectionalLight(float[] pDirection){
        setPosition(new float[] { pDirection[0], pDirection[1], pDirection[2], 0 });
    }
}
