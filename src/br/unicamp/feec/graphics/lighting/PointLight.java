package br.unicamp.feec.graphics.lighting;

public class PointLight extends Light {
    public PointLight(float[] pPosition){
        setPosition(new float[] { pPosition[0], pPosition[1], pPosition[2], 1 });
    }
}
