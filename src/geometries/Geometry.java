package geometries;
import primitives.*;

/**
 * Interface Geometry is a basic abstract representation of Geometries
 * Authors - Polina Frolov Korogodsky and Tselia Tebol
 */
public abstract class Geometry implements Intersectable {

    /**
     * Constructor initializing color
     * @param _emission
     */
    public Geometry(Color _emission) {
        this(_emission, new Material(0d, 0d, 0));
    }

    /**
     * Default constructor initializing color as black
     */
    public Geometry() {
        this._emission = Color.BLACK;
    }

    /**
     * Construct a Geometry by it's color and material
     * @param _emission
     * @param _material
     */
    public Geometry(Color _emission, Material _material) {
        this._emission = _emission;
        this._material = _material;

    }

    /**
     * Prototype function for getNormal
     * @param point
     * @return
     */
    public abstract Vector getNormal(Point3D point);
    protected Color _emission;

    /**
     * Getter method for _material field
     * @return Material material
     */
    public Material getMaterial() {
        return _material;
    }

    protected Material _material;

    /**
     * Getter of _emission
     * @return
     */
    public Color getEmission() {
        return _emission;
    }
}
