package geometries;
import primitives.*;

/**
 * Class Radial Geometry is an abstract representation of radial geometrical objects, motherclass of all radial geometrical objects
 * radial geometrical objects
 * Authors : Polina Frolov Korogodsky and Tselia Tebol
 */
public abstract class RadialGeometry extends Geometry {
double _radius;

    /**
     *constructor that receives double for radius, color and material
     * @param _radius
     */
    public RadialGeometry(Color _emission, Material mat, double _radius) {
        super(_emission, mat);
        this._radius = _radius;
    }

    /**
     * Constructor with color and radius
     * @param _emission
     * @param _radius
     */
    public RadialGeometry(Color _emission, double _radius) {
        super(_emission);
        this._radius = _radius;
    }

    /**
     * constructor without emission color and material
     * @param _radius
     */
    public RadialGeometry(double _radius) {
       super(Color.BLACK);
        this._radius = _radius;
    }


    /**
     * Constructor that receives other RadialGeometry
     * and constructs the one with same radius, emission and material
     * @param geometry
     */
    public RadialGeometry(RadialGeometry geometry){
        super(geometry._emission, geometry._material);
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
