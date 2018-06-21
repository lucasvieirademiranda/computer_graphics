package br.unicamp.feec.graphics.lighting;

public class SpotLight extends Light {
    public SpotLight(float[] pPosition, float[] pDirection, float pCutoff, float pExponent){
        setPosition(new float[] { pPosition[0], pPosition[1], pPosition[2], 1 });
        setSpotDirection(pDirection);
        setSpotCutoff(pCutoff);
        setSpotExponent(pExponent);
    }
}
