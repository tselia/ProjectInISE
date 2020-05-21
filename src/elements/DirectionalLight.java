package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

/**
 * class DirectionalLight is a basic representation of light source that is defined by direction
 */
public class DirectionalLight extends Light implements LightSource {
    private Vector _direction;

    /**
     * Constructs a new DirectionalLight object by it's intencity (via Light's constructor) and direction
     * @param _intensity
     * @param _direction
     */
    public DirectionalLight(Color _intensity, Vector _direction) {
        super(_intensity);
        this._direction = _direction.normalized();
    }

    /**
     * The function that returns the color in specific point, for the directional light
     * if this point is lightened the color will be the same as the _intensity
     * @param point
     * @return
     */
    @Override
    public Color getIntensity(Point3D point) {
        return _intensity;
    }//is quicker then calling for super's function

    /**
     * overridden function that should return the direction of light in specific point. for directional light
     * the direction will always be equal to _direction Vector
     * @param point
     * @return
     */
    @Override
    public Vector getL(Point3D point) {
        return _direction;
    }

    @Override
    public double getDistance(Point3D point) {
        return Double.POSITIVE_INFINITY;
    }
}
