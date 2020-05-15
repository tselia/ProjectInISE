package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Util;
import primitives.Vector;

/**
 * class SpotLight is a basic representation of the light source defined by direction, location,
 * and three attenuation coefficients
 */
public class SpotLight extends PointLight implements LightSource {
    private Vector _direction;
    double _concentration;

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
    public SpotLight(Color _intensity, Point3D _position, Vector _direction, double kC, double kL, double kQ, double _conc) {
        super(_intensity, _position, kC, kL, kQ);
        this._direction = _direction.normalized();
        this._concentration = _conc;
    }

    /**
     * Constructor without concentration parameter
     * @param _intensity
     * @param _position
     * @param _dir
     * @param kC
     * @param kL
     * @param kQ
     */
    public SpotLight(Color _intensity, Point3D _position, Vector _dir, double kC, double kL, double kQ ){
        this(_intensity, _position, _dir, kC, kL, kQ, 1);
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

        double cos = _direction.dotProduct(super.getL(point));// projection in Eliezer's project
        if(Util.isZero(cos))
            return Color.BLACK;
        double coefficient = Math.max(0, cos);
        Color colorOfPointInSuper = super.getIntensity();
        if(_concentration!=0){
            coefficient = Math.pow(coefficient, _concentration);
        }
        return colorOfPointInSuper.scale(coefficient);
//        if(cos>0)
//            return super.getIntensity(point).scale(cos);
//        else return Color.BLACK;
    }
}
