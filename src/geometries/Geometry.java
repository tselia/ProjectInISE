package geometries;
import primitives.*;

/**
 * Interface Geometry is a basic abstract representation of Geometries
 * Authors - Polina Frolov Korogodsky and Tselia Tebol
 */
public interface Geometry extends Intersectable {
    Vector getNormal (Point3D point);

    }
