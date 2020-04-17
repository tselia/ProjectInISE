package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;
/**
 * Class Triangle is a basic representation of triangle geometry as a son class of polygon
 * Authors - Polina Frolov Korogodsky and Tselia Tebol
 */
import java.util.List;

public class Triangle extends Polygon {
    /**
     * Constructor receives 3 points  and adds them to the list
     * @param A
     * @param B
     * @param C
     */
    public Triangle(Point3D A, Point3D B, Point3D C) {
        super(A, B, C);
    }

    /**
     * returns a normal to triangle
     * @param point
     * @return
     */
    @Override
    public Vector getNormal(Point3D point) {
        return super.getNormal(point);
    }

    /**
     * returns a string description of triangle by points
     * @return
     */
    @Override
    public String toString() {
        return "Triangle{" +
                "_vertices=" + _vertices +
                "} ";
    }
    @Override
    public List<Point3D> findIntersections(Ray ray) {
        return super.findIntersections(ray);
    }

}
