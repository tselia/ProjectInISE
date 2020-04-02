package geometries;

import primitives.*;

/**
 * Class Sphere is a basic abstract representation of Sphere, contains Radius and the Center point
 * radial geometrical objects
 * Authors : Polina Frolov Korogodsky and Tselia Tebol
 */
public class Sphere extends RadialGeometry{
Point3D center;

    /**
     * Constructor receiving radius and center point
     * @param _radius
     * @param center
     */
    public Sphere(double _radius, Point3D center) {
        super(_radius);
        this.center = center;
    }


    /**
     * returns null because by the exercise instructions should not yet be implemented; should return the normal vector
     * @param point
     * @return
     */
    @Override
    public Vector getNormal(Point3D point) {
        try {
            if ((point.subtract(this.center)).length() != this._radius)
                throw new IllegalArgumentException("The point is not on the sphere's surface");
        }
        catch (IllegalArgumentException ex){
            throw ex;
        }
        return (point.subtract(this.center)).normalize();

    }

    /**
     * returns center point
     * @return center
     */
    public Point3D getCenter() {
        return center;
    }

    /**
     * returns a string containing the sphere's radius and center point
     * @return
     */
    @Override
    public String toString() {
        return "Sphere{" +
                "center=" + center +
                " " + super.toString()+"} ";
    }
}
