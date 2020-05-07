package geometries;
import primitives.*;

/**
 * Interface Geometry is a basic abstract representation of Geometries
 * Authors - Polina Frolov Korogodsky and Tselia Tebol
 */
public abstract class Geometry implements Intersectable {

    /**
     * Constructor imnitializing color
     * @param _emission
     */
    public Geometry(Color _emission) {
        this._emission = _emission;
    }

    /**
     * Default constructor initializing color as black
     */
    public Geometry() {
        this._emission = Color.BLACK;
    }

    /**
     * Prototype function for getNormal
     * @param point
     * @return
     */
    public abstract Vector getNormal(Point3D point);
    protected Color _emission;

    /**
     * Getter of _emission
     * @return
     */
    public Color getEmission() {
        return _emission;
    }
}
