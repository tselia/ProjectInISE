package elements;

import primitives.Color;

/**
 * class AmbientLight is a representation of of light of environment
 * authors Polina Frolov Korogodsky and Tselia Tebol
 */
public class AmbientLight extends Light {


    /**
     * Constructor computes the _intensity value by multiplying Ia by Ka and sends it to father's class constructor
     * @param Ia java.awt.Color
     * @param Ka
     */
    public AmbientLight(java.awt.Color Ia, double Ka) {
        super(new Color(Ia.getRed()*Ka, Ia.getGreen()*Ka, Ia.getBlue()*Ka));
        //this._intensity = new Color(Ia.getRed()*Ka, Ia.getGreen()*Ka, Ia.getBlue()*Ka);
    }


    /**
     * Constructor computes the _intensity value by multiplying Ia by Ka and sending it's value to father's class constructor
     * @param Ia primitives.Color
     * @param Ka
     */
    public AmbientLight( primitives.Color Ia, double Ka) {
        super(new Color(Ia.getColor().getRed()*Ka, Ia.getColor().getGreen()*Ka, Ia.getColor().getBlue()*Ka));
    }
}
