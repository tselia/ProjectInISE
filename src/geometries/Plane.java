package geometries;

import primitives.*;
/**
 * Class plane is a basic representation of plane
 * Authors - Polina Frolov Korogodsky and Tselia Tebol
 */
public class Plane {
    Point3D point;
    Vector normal;

    /**
     * Constructor with point and vector
     * @param point
     * @param normal
     */
    public Plane(Point3D point, Vector normal) {
        this.point = point;
        this.normal = normal;
    }

    /**
     * finds a normal and defines a plane by normal and point
     * @param A
     * @param B
     * @param C
     * @throws ArithmeticException
     */
    public Plane(Point3D A, Point3D B, Point3D C) {
        Vector AB = new Vector(A.subtract(B));
        Vector BC = new Vector(B.subtract(C));
        try {
            this.normal = AB.crossProduct(BC).normalized();// don't have any idea whether it works
        }
        catch ( ArithmeticException ex){
            throw ex;
        }
        if( A.equals(Point3D.zero)||B.equals(Point3D.zero)||C.equals(Point3D.zero))
            this.point = Point3D.zero;
        else  this.point = A;

    }

    /**
     * returns normal
     * @return vector
     */
    public Vector getNormal() {
        return normal;
    }

    //public Point3D getPoint() {

    /**
     * returns a point that by it a plane is defined
     * @return point
     */
    public Point3D getPoint() {
        return point;
    }

    @Override
    public String toString() {
        return "Plane{" +
                "point=" + point +
                ", normal=" + normal +
                '}';
    }
}
