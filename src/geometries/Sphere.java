package geometries;

import primitives.*;
import primitives.Vector;

import java.util.*;
import java.lang.*;

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
    @Override
    public List<Point3D> findIntersections(Ray ray) {
        if(!ray.getStart().equals(center)) {
            Vector u = this.center.subtract(ray.getStart());
            double tmCoefficient = u.dotProduct(ray.getDirection());
            double distance = Math.sqrt(u.lengthSquared() - (tmCoefficient * tmCoefficient));
            if (distance > this._radius)
                return null;
            double thCoefficient = Math.sqrt(_radius * _radius - distance * distance);
            double t1 = tmCoefficient + thCoefficient;
            double t2 = tmCoefficient - thCoefficient;
            //System.out.println(t1);
            //System.out.println(t2);
            if (Util.alignZero(t1) <= 0 && Util.alignZero(t2) <= 0)
                return null;
           if (Util.alignZero(t1) ==Util.alignZero(t2))
               if (ray.getStart().distance(center)>=_radius)
                return null;
           // if (Util.alignZero(t2)==Util.alignZero(t1))
             //   if(ray.getStart().distance(center)>)
            List<Point3D> intersectionPoints = new ArrayList<Point3D>();
            if (Util.alignZero(t1) > 0)
                intersectionPoints.add(ray.getPoint((t1)));
            if (Util.alignZero(t2) > 0&&Util.alignZero(t2)!=Util.alignZero(t1)) {
                intersectionPoints.add(ray.getPoint(t2));
            }
            return intersectionPoints;
        }
        else {
            List<Point3D> intersectionPoints = new ArrayList<Point3D>();
            intersectionPoints.add(center.add(ray.getDirection().scale(_radius)));
            return intersectionPoints;


        }

        }
    }

