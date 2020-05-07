package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

/**
 * Class PointLight is a basic representation of the light source defined by it's location
 * Authors: Polina Frolov and Tselia Tebol
 */
public class PointLight extends Light implements LightSource {

    protected Point3D _position;
    protected double kC;
    protected double kL;
    protected double kQ;

    /**
     * Constructs a new object of PointLight by referencing to Light's constructor with _intensity parameter
     * and defining _intensity, kC, kL, kQ by received values
     * @param _intensity
     * @param _position
     * @param kC
     * @param kL
     * @param kQ
     */
    public PointLight(Color _intensity, Point3D _position, double kC, double kL, double kQ) {
        super(_intensity);
        this._position = _position;
        this.kC = kC;
        this.kL = kL;
        this.kQ = kQ;
    }

    /**
     * the color of the specific point depends on it's distance from the _position - it will be less and less
     * intensive when point will be more and more further
     * @param point
     * @return
     */
    @Override
    public Color getIntensity(Point3D point) {
        double distance = this._position.subtract(point).length();
        double denominator = kC+kL*distance+kQ*distance*distance;
        return this._intensity.scale(1/denominator);
    }

    /**
     * The vector from the light source to specific point is it's position - point
     * Function returns this vector normalized
     * @param point
     * @return
     */
    @Override
    public Vector getL(Point3D point) {
        return this._position.subtract(point).normalized();
    }
}
