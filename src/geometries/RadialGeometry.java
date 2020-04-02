package geometries;
import primitives.*;

/**
 * Class Radial Geometry is an abstract representation of radial geometrical objects, motherclass of all radial geometrical objects
 * radial geometrical objects
 * Authors : Polina Frolov Korogodsky and Tselia Tebol
 */
public abstract class RadialGeometry implements Geometry{
double _radius;

    /**
     *constructor that receives double for radius
     * @param _radius
     */
    public RadialGeometry(double _radius) {
        this._radius = _radius;
    }

    /**
     * Constructor that receives other RadialGeometry
     * @param geometry
     */
    public RadialGeometry(RadialGeometry geometry){
        this._radius = geometry._radius;
    }

    /**
     * function returns radius
     * @return
     */
    public double get_radius() {
        return _radius;
    }

    /**
     * function that returns a string of RadialGeometry
     * @return
     */
    @Override
    public String toString() {
        return "RadialGeometry: radius =  "+ _radius;
    }

    /**
     * function equals() was added to make test easier on the second step
     * @param o
     * @return boolean
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RadialGeometry)) return false;

        RadialGeometry that = (RadialGeometry) o;

        return Double.compare(that.get_radius(), get_radius()) == 0;
    }
}
