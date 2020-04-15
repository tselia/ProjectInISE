package geometries;
import primitives.*;
import java.util.*;

/**
 * Interface Intersectable is an interface that enables all geometries to find and return their intersection
 * points with ray
 * Authors - Polina Frolov Korogodsky and Tselia Tebol
 */
public interface Intersectable {
    List<Point3D> findIntersections(Ray ray);
}
