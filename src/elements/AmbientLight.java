package elements;

import primitives.Color;

/**
 * class AmbientLight is a representation of of light of environment
 * authors Polina Frolov Korogodsky and Tselia Tebol
 */
public class AmbientLight {
   private Color _intensity;

    /**
     * Constructor computs the _intensity value by multiplying Ia by Ka
     * @param Ia
     * @param Ka
     */
    public AmbientLight(java.awt.Color Ia, double Ka) {
        this._intensity = new Color(Ia.getRed()*Ka, Ia.getGreen()*Ka, Ia.getBlue()*Ka);
    }

    /**
     * a get method for _intensity field
     * @return
     */
    public Color get_intensity() {
        return _intensity;
    }
}