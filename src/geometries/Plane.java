package geometries;

import primitives.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Class plane is a basic representation of plane
 * Authors - Polina Frolov Korogodsky and Tselia Tebol
 */
public class Plane extends Geometry {
    Point3D point;
    Vector normal;

//    /**
//     * Constructor with point and vector
//     *
//     * @param point
//     * @param normal
//     */
//    public Plane(Point3D point, Vector normal) {
//        this.point = point;
//        this.normal = normal;
//    }


    public Plane(Color _emission, Material _mat, Point3D point, Vector normal) {
        super(_emission, _mat);
        this.point = point;
        this.normal = normal;
    }
    /**
     * Constructor with color, point and vector
     * @param _emission
     * @param point
     * @param normal
     */
    public Plane(Color _emission, Point3D point, Vector normal) {
        super(_emission);
        this.point = point;
        this.normal = normal;
    }


    /**
     * Constructor with point and vector
     * @param point
     * @param normal
     */
    public Plane(Point3D point, Vector normal) {
        super(Color.BLACK);
        this.point = point;
        this.normal = normal;
    }
    /**
     * finds a normal and defines a plane by normal and point
     *
     * @param A
     * @param B
     * @param C
     * @throws ArithmeticException
     */
    public Plane(Point3D A, Point3D B, Point3D C) {
        Vector AB = new Vector(A.subtract(B));
        Vector BC = new Vector(B.subtract(C));
        try {
            this.normal = AB.crossProduct(BC).normalized();
        } catch (ArithmeticException ex) {
            throw ex;
        }
        if (A.equals(Point3D.zero) || B.equals(Point3D.zero) || C.equals(Point3D.zero))
            this.point = Point3D.zero;
        else this.point = A;

}

    /**
     * Constructor with color and three points
     * @param _emission
     * @param A
     * @param B
     * @param C
     */
    public Plane(Color _emission, Point3D A, Point3D B, Point3D C) {
        super(_emission);
        Vector AB = new Vector(A.subtract(B));
        Vector BC = new Vector(B.subtract(C));
        try {
            this.normal = AB.crossProduct(BC).normalized();// don't have any idea whether it works
        } catch (ArithmeticException ex) {
            throw ex;
        }
        if (A.equals(Point3D.zero) || B.equals(Point3D.zero) || C.equals(Point3D.zero))
            this.point = Point3D.zero;
        else this.point = A;

    }

    /**
     * Constructs the plane by it's color, material and 3 points
     * @param _emission
     * @param _material
     * @param A
     * @param B
     * @param C
     */
    public Plane(Color _emission, Material _material, Point3D A, Point3D B, Point3D C) {
        super(_emission, _material);
        Vector AB = new Vector(A.subtract(B));
        Vector BC = new Vector(B.subtract(C));
        try {
            this.normal = AB.crossProduct(BC).normalized();// don't have any idea whether it works
        } catch (ArithmeticException ex) {
            throw ex;
        }
        if (A.equals(Point3D.zero) || B.equals(Point3D.zero) || C.equals(Point3D.zero))
            this.point = Point3D.zero;
        else this.point = A;
    }

    /**
     * returns normal
     *
     * @return vector
     */
    public Vector getNormal() {
        return normal;
    }

    //public Point3D getPoint() {

    /**
     * returns a point that by it a plane is defined
     *
     * @return point
     */
    public Point3D getPoint() {
        return point;
    }

    /**
     * returns a string describing the plane
     * @return
     */
    @Override
    public String toString() {
        return "Plane{" +
                "point=" + point +
                ", normal=" + normal +
                '}';
    }
    /**
     * Method to get intersection points with a specific ray
     * @param ray (Ray)
     * @return List(Point3D)
     */
    @Override
    public List<GeoPoint> findIntersections(Ray ray) {
        if (ray.getStart().equals(point))
            return null;
        if(ray.getDirection().dotProduct(normal)==0)//the ray is parallel to the plane
            return null;
        double n = normal.dotProduct(ray.getDirection());
        if (!Util.isZero(n)) {
            double coefficient = normal.dotProduct(point.subtract(ray.getStart())) / n;
            if (coefficient <= 0)
                return null;
            //if()
            else {
                List<GeoPoint> intersectionPoints = new ArrayList<GeoPoint>();
                //Point3D intersectionPoint = new Point3D(ray.getStart().add(ray.getDirection().scale(coefficient)));
                intersectionPoints.add(new GeoPoint(this, ray.getPoint(coefficient)));
                return intersectionPoints;
            }
        } else {
            double sum = point.getX().get() * normal.getEnd().getX().get() + //so we can get to the plane representation as ax+by+cz=d
                    point.getY().get() * normal.getEnd().getY().get() +
                    point.getZ().get() * normal.getEnd().getZ().get();
            double coefficient = (sum - ray.getStart().getX().get() - ray.getStart().getY().get() - ray.getStart().getZ().get()) /
                    (ray.getDirection().getEnd().getX().get() + ray.getDirection().getEnd().getY().get() + ray.getDirection().getEnd().getZ().get());
            if (coefficient <= 0)
                return null;
            else {
                List<GeoPoint> intersectionPoints = new ArrayList<GeoPoint>();
                //Point3D intersectionPoint = new Point3D(ray.getStart().add(ray.getDirection().scale(coefficient)));
                try {
                    intersectionPoints.add(new GeoPoint(this, ray.getPoint(coefficient)));
                }
                catch(Exception ex){
                    throw new ExceptionInInitializerError("Error: coefficient is illegal");
                }
                return intersectionPoints;
            }
        }

    }

    @Override
    public Vector getNormal(Point3D point) {
        return normal;
    }
}
