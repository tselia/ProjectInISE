package elements;

import primitives.Color;

/**
 * Class Light is a basic abstract representation of light
 * Authors - Polina Frolov Korogodsky and Tselia Tebol
 */
abstract class Light {
    protected Color _intensity;

    /**
     * Constructor that creates a new object of light extending class by a parameter of primitives.Color
     * @param _intensity
     */
    public Light(Color _intensity) {
        this._intensity = _intensity;
    }

    /**
     * Getter method for _intensity field
     * @return
     */
    public Color getIntensity() {
        return _intensity;
    }
}
