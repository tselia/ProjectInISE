package geometries;
import primitives.*;

import java.util.List;

/**
 * Class Cylinder is a basic abstract representation of cylinder geometry, contains Radius and height
 * radial geometrical objects
 * author@ Polina Frolov Korogodsky and Tselia Tebol
 */
public class Cylinder extends Tube{
    double height;
    //double radius

    /**
     * Constructs a cylinder by radius and height
     *
     * @param _radius
     * @param height
     */
    public Cylinder(double _radius, Ray axe, double height) {
        super(_radius, axe);
        this.height = height;
    }

    /**
     * the Cylinder.getNormal(Point3D) function checks if the input Point is at the upper base (that differs it from tube) , checks
     * that the point is not higher then upper base and the if it's needed turns to the Tube's getNormal(Point3D) function
     *
     * @param point
     * @return
     */
    @Override
    public Vector getNormal(Point3D point) {
        //whether the point is on base
        if (point.subtract(this.axe.getStart()).dotProduct(this.axe.getDirection()) == 0)//if the point is in the same plane that starting surface
            if (point.subtract(this.axe.getStart()).length() <= _radius) //if the point is in the down circle
                return this.axe.getDirection().scale(-1);
            else throw new IllegalArgumentException("Point is outside the tube");
        //Whether the point is on the upper surface
        Point3D finalCircleCenter = new Point3D(this.axe.getStart().add(this.axe.getDirection().scale(this.height)));
        double straightDistance = this.axe.getDirection().dotProduct(point.subtract(this.axe.getStart()));
        if( point.subtract(finalCircleCenter).length()<=_radius)
        {if (point.subtract(finalCircleCenter).dotProduct(this.axe.getDirection())==0)
                return this.axe.getDirection();
            else throw  new IllegalArgumentException("The point is outside the figure");}
        //double straightDistance = this.axe.getDirection().dotProduct(point.subtract(this.axe.getStart()));
        //Point3D intersectionPointOfOrthogonalWithRay = new Point3D(this.axe.getStart().add(this.axe.getDirection().scale(straightDistance)));

        // if the point is higher then the cylinder's end
        if (straightDistance>this.height)
            throw new IllegalArgumentException("The point is outside the figure");
        return super.getNormal(point);
    }

    /**
     * height field getter method
     *
     * @return height
     */
    public double getHeight() {
        return height;
    }

    /**
     * Concatenates the cylinder information to string
     *
     * @return
     */
    @Override
    public String toString() {
        return "Cylinder{" +
                "height=" + height +
                " " + super.toString() + "} ";
    }




    /**
     * function equals() was added to the Cylinder class on the step 2 to make the tests writing easier
     *
     * @param o
     * @return boolean
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cylinder)) return false;
        if (!super.equals(o)) return false;

        Cylinder cylinder = (Cylinder) o;

        return ((super.equals(o))&&(Double.compare(cylinder.getHeight(), getHeight()) == 0));
    }

    @Override
    public List<Point3D> findIntersections(Ray ray) {
        return null;
    }
}
