package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

/**
 * class SpotLight is a basic representation of the light source defined by direction, location,
 * and three attenuation coefficients
 */
public class SpotLight extends PointLight implements LightSource {
    private Vector _direction;

    /**
     * Construct a new object of SpotLight by creating a new instance of PointLight and adding a Vector to it
     * by Vector's value that the function received
     * @param _intensity
     * @param _position
     * @param kC
     * @param kL
     * @param kQ
     * @param _direction
     */
    public SpotLight(Color _intensity, Point3D _position, double kC, double kL, double kQ, Vector _direction) {
        super(_intensity, _position, kC, kL, kQ);
        this._direction = _direction.normalized();
    }

    @Override
    public Vector getL(Point3D point) {
      return _direction;

    }

    /**
     * if angle's cos is more that 0 the color of the point will be the color computed in super's function multiplied
     * by cos of the angle; else returned value is 0 => black color
     * @param point
     * @return
     */
    public Color getIntensity(Point3D point){
        double cos = _direction.dotProduct(super.getL(point));
        if(cos>0)
            return super.getIntensity(point).scale(cos);
        else return Color.BLACK;
    }
}
