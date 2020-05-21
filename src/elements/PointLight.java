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
     * @param _intensit
     * @param _position
     * @param kC
     * @param kL
     * @param kQ
     */
    public PointLight(Color _intensit, Point3D _position, double kC, double kL, double kQ) {
        super(_intensit);
        _intensity = _intensit;
        this._position = new Point3D(_position);
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
        double squaredDistance = _position.squaredDistance(point);
        double distance = _position.distance(point);
        double denominator = kC+kL*distance+kQ*squaredDistance; //_intensity.reduce(_kC + _kL * d + _kQ * dsquared)
        return _intensity.reduce(denominator);

    }

    /**
     * The vector from the light source to specific point is it's position - point
     * Function returns this vector normalized
     * @param point
     * @return
     */
    @Override
    public Vector getL(Point3D point) {

        if(!(_position.equals(point)))
            return point.subtract(_position).normalize();
        else
            return null;
    }

    /**
     * overriding of interface's getDistance(Point3D) function;
     * returns distance between the point and the light source's position
     * @param point
     * @return double
     */
    @Override
    public double getDistance(Point3D point) {
        return point.distance(_position);
    }
   /* @Override
    public Vector getL(Point3D p) {
        if (p.equals(_position)) {
            return null;
        }
        return p.subtract(_position).normalize();
    }*/

    /**
     * overriding getIntensity method
     * @return
     */
    @Override
    public Color getIntensity(){
        return super.getIntensity();
    }
}
///*package elements;
//
//import primitives.Color;
//import primitives.Point3D;
//import primitives.Vector;
//
///**
// * The PointLight object specifies an attenuated light source at a fixed point in space that radiates light equally
// * in all directions away from the light source. PointLight has the same attributes as a Light node,
// * with the addition of location and attenuation parameters.
// * <p>
// * A point light contributes to diffuse and specular reflections,
// * which in turn depend on the orientation and position of a surface.
// * A point light does not contribute to ambient reflections.
// * <p>
// * A PointLight is attenuated by multiplying the contribution of the light by an attenuation factor.
// * The attenuation factor causes the the PointLight's brightness to decrease
// * as distance from the light source increases.
// * A PointLight's attenuation factor contains three values:
// * <p>
// * Constant attenuation
// * Linear attenuation
// * Quadratic attenuation
// * <p>
// * A PointLight is attenuated by the reciprocal of the sum of:
// * <p>
// * The constant attenuation factor
// * The Linear attenuation factor times the distance between the light and the vertex being illuminated
// * The quadratic attenuation factor times the square of the distance between the light and the vertex
// * <p>
// * By default, the constant attenuation value is 1 and the other two values are 0,
// * resulting in no attenuation.
// */
//public class PointLight extends Light implements LightSource {
//    Point3D _position;
//    double _kC; // Constant attenuation
//    double _kL; // Linear attenuation
//    double _kQ; // Quadratic attenuation
//
//public PointLight(Color colorIntensity, Point3D position, double kC, double kL, double kQ){
//    super(colorIntensity);
//    this._intensity = colorIntensity;
//    this._position = new Point3D(position);
//    this._kC = kC;
//    this._kL = kL;
//    this._kQ = kQ;
//}
//
//    // by default, the constant attenuation value is 1 and the other two values are 0
//    public PointLight(Color colorIntensity, Point3D position) {
//        this(colorIntensity, position, 1d, 0d, 0d);
//    }
//
//    //dummy overriding Light getIntensity()
//    @Override
//    public Color getIntensity() {
//        return super.getIntensity();
//    }
//
//    //overriding LightSource getIntensity(Point3D)
//    @Override
//    public Color getIntensity(Point3D p) {
//        double dsquared = p.squaredDistance(_position);
//        double d = p.distance(_position);
//
//        return (_intensity.reduce(_kC + _kL * d + _kQ * dsquared));
//    }
//
//    // Light vector
//    @Override
//    public Vector getL(Point3D p) {
//        if (p.equals(_position)) {
//            return null;
//        }
//        return p.subtract(_position).normalize();
//    }
//}
