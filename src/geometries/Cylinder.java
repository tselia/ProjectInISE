package geometries;
import MyMathOperations.*;
import com.sun.nio.sctp.IllegalReceiveException;
import primitives.*;

import java.util.ArrayList;
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
     * Constructs a cylinder by radius, axe, height, emission color and material
     *
     * @param _radius
     * @param height
     */
    public Cylinder(Color _emission, Material _mat, double _radius, Ray axe, double height) {
        super(_emission, _mat, _radius, axe);
        this.height = height;
    }

    /**
     * Constructor without color and material
     * @param _radius
     * @param axe
     * @param height
     */
    public Cylinder(double _radius, Ray axe, double height) {
        super(_radius, axe);
        this.height = height;
    }
    /**
     * Constructs new cylinder by color, radius, axis and height
     * @param _emission
     * @param _radius
     * @param axe
     * @param height
     */
    public Cylinder(Color _emission, double _radius, Ray axe, double height) {
        super(_emission, _radius, axe);
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
    private List<GeoPoint> getIntersectionPointsInStraightCoordinates(Cylinder cylinder, Ray ray){
        System.out.println(cylinder.toString());
        double a = Math.pow(ray.getDirection().getEnd().getY().get(), 2)+Math.pow(ray.getDirection().getEnd().getZ().get(), 2);
        double b = 2 * ((ray.getStart().getZ().get()-cylinder.getAxe().getStart().getZ().get())*ray.getDirection().getEnd().getZ().get() +
                (ray.getStart().getY().get()-cylinder.getAxe().getStart().getY().get())*ray.getDirection().getEnd().getY().get());
        double c = Math.pow(ray.getStart().getY().get()-cylinder.getAxe().getStart().getY().get(), 2)
                + Math.pow(ray.getStart().getZ().get()-cylinder.getAxe().getStart().getZ().get(), 2)
                - Math.pow(cylinder._radius, 2);
        double[] ti;

        try {
            ti = MathOperations.solveQuadrantEquation(a, b, c);
        }
        catch (Exception ex){
            return null;
        }
    if(ti==null)
        return null;
    double maxHeight = cylinder.axe.getPoint(cylinder.height).getX().get();
    double minHeight = cylinder.axe.getStart().getX().get();
    if(ti.length==1)
    {
        System.out.println("One point only; ti="+ti[0]);
        if(ti[0]<=0)//the ray starts on the surface and goes outside
            return null;
        Point3D intersectionPoint = ray.getPoint(ti[0]);
        if (intersectionPoint.getX().get()>maxHeight||intersectionPoint.getX().get()<minHeight)
            return null;
        if(intersectionPoint.getX().get()==maxHeight||intersectionPoint.getX().get()==minHeight)//the point on the angle
        {if(Math.pow(intersectionPoint.getZ().get(), 2)+Math.pow(intersectionPoint.getY().get(), 2)==Math.pow(cylinder._radius, 2))
            {return null;}}
        List<GeoPoint> intersection = new ArrayList<GeoPoint>();
        intersection.add(new GeoPoint(this, intersectionPoint));
        System.out.println("One intersection point "+ intersectionPoint.toString());
        return intersection;
    }
    else if(ti.length==2)
    {
        System.out.println("Two points only; ti="+ti[0]+ti[1]);
        Point3D intersectionPoint1 = ray.getPoint(ti[0]);
        Point3D intersectionPoint2 = ray.getPoint(ti[1]);
        boolean hasPoints = false;
        boolean hasTi2 = false;
        if ((intersectionPoint1.getX().get()<maxHeight)&&intersectionPoint1.getX().get()>minHeight)//the height suits the cylinder
        { if(ti[0]>0) //the ray's vector is scaled by positive number

            hasPoints = true;}
        if(intersectionPoint1.getX().get()==maxHeight||intersectionPoint1.getX().get()==minHeight)
        {
            if(Math.pow(intersectionPoint1.getZ().get(), 2)+Math.pow(intersectionPoint1.getY().get(), 2)==Math.pow(cylinder._radius, 2))
                hasPoints = false;
        }
        if (intersectionPoint2.getX().get()<maxHeight&&intersectionPoint2.getX().get()>minHeight) {
            if (ti[1]>0)
                hasTi2 = true;
        }
        if(intersectionPoint1.getX().get()==maxHeight||intersectionPoint1.getX().get()==minHeight)
        {
            if(Math.pow(intersectionPoint2.getZ().get(), 2)+Math.pow(intersectionPoint2.getY().get(), 2)==Math.pow(cylinder._radius, 2))
                hasTi2 = false;
        }
        if(hasPoints||hasTi2)
        {
            List<GeoPoint> intersection = new ArrayList<GeoPoint>();
            if(hasPoints)
                intersection.add(new GeoPoint(this, intersectionPoint1));
            if(hasTi2)
                intersection.add(new GeoPoint(this, intersectionPoint2));
            for (int i=0; i<intersection.size(); i++)
                System.out.println(intersection.get(i).toString());
            return intersection;
        }

    }
    throw new IllegalReceiveException("Error");
    }

    @Override
    public List<GeoPoint> findIntersections(Ray ray) {
        if(this.getAxe().getDirection().equals(new Vector(1, 0, 0)))
            return getIntersectionPointsInStraightCoordinates(this, ray);
       // change coordinates to make the cylinder "straight" by x axis
       Vector newI = new Vector(this.axe.getDirection());
       //System.out.println(newI.toString());
       Vector newK = new Vector(1, 1, (-newI.getEnd().getX().get()-newI.getEnd().getY().get())/newI.getEnd().getZ().get());//by formula "dot product is 0, let's initialize x and y by 1"
       //System.out.println(newK.toString());
        Vector newJ = new Vector(newI.crossProduct(newK).normalize());
        //System.out.println(newJ.toString());
       // find the transformation matrix by solve three equations systems
       //first equation system, to find the first line of matrix
        double[][] firstRowMatrix = new double[3][4];
        firstRowMatrix[0]=new double[]{newI.getEnd().getX().get(), newI.getEnd().getY().get(), newI.getEnd().getZ().get(), 1};
        firstRowMatrix[1] = new double []{1, 1, newK.getEnd().getZ().get(), 0};
        firstRowMatrix[2] = new double[]{newJ.getEnd().getX().get(), newJ.getEnd().getY().get(), newJ.getEnd().getX().get(), 0};
        //solving the equation

        double[][] firstRowValues = MathOperations.solveEquationSystem(firstRowMatrix);
        double [][] secondRaw = new double[3][4];
        secondRaw[0]=new double[]{newI.getEnd().getX().get(), newI.getEnd().getY().get(), newI.getEnd().getZ().get(), 0};
        secondRaw[1] = new double[]{1, 1, newK.getEnd().getZ().get(), 0};
        secondRaw[2] = new double[]{newJ.getEnd().getX().get(), newJ.getEnd().getY().get(), newJ.getEnd().getX().get(),1};
        double [][] secondRowValues = MathOperations.solveEquationSystem(secondRaw);
        double[][] thirdRow = new double[3][4];
        thirdRow[0] = new double[]{newI.getEnd().getX().get(), newI.getEnd().getY().get(), newI.getEnd().getZ().get(), 0};
        thirdRow[1] = new double[]{newJ.getEnd().getX().get(), newJ.getEnd().getY().get(), newJ.getEnd().getX().get(),1};
        thirdRow[2] = new double[]{newJ.getEnd().getX().get(), newJ.getEnd().getY().get(), newJ.getEnd().getX().get(),0};
        double[][] thirdRowValues = MathOperations.solveEquationSystem(thirdRow);
        double[][] transformationMatrix = new double[3][3];
        for(int i=0; i<3; i++)
            transformationMatrix[0][i]= firstRowValues[i][0];
        for(int i=0; i<3; i++)
            transformationMatrix[1][i]= secondRowValues[i][0];
        for(int i=0; i<3; i++)
            transformationMatrix[2][i]= thirdRowValues[i][0];
        double [][] startPoint = new double[3][1];//assigning the transformation matrix
        startPoint[0][0] = this.axe.getStart().getX().get();
        startPoint[1][0] = this.axe.getStart().getY().get();
        startPoint[2][0] = this.axe.getStart().getZ().get();
        double [][] newPointCoordinates = MathOperations.multiplyMatrices(transformationMatrix, startPoint, 3, 3, 1);
        Point3D axeStartPointInNewCoordinates = new Point3D(newPointCoordinates[0][0],newPointCoordinates[1][0], newPointCoordinates[2][0]);
        // Ray transformation to the new coordinates
        // Point transformation
        startPoint[0][0] = ray.getStart().getX().get();
        startPoint[1][0] = ray.getStart().getY().get();
        startPoint[2][0] = ray.getStart().getZ().get();
        newPointCoordinates = MathOperations.multiplyMatrices(transformationMatrix, startPoint, 3, 3, 1);
        //for(int i=0; i<3; i++)
       //     for(int j=0; j<3; j++)
       //         System.out.println(transformationMatrix[i][j]);
        Point3D rayStartPointInNewCoordinates = new Point3D(newPointCoordinates[0][0],newPointCoordinates[1][0], newPointCoordinates[2][0]);
        System.out.println("new Point is "+ rayStartPointInNewCoordinates.toString());
        //Vector transformation
        startPoint[0][0] = ray.getDirection().getEnd().getX().get();
        startPoint[1][0] = ray.getDirection().getEnd().getY().get();
        startPoint[2][0] = ray.getDirection().getEnd().getZ().get();
        System.out.println("X ="+ startPoint[0][0]);
        System.out.println("Y ="+ startPoint[1][0]);
        System.out.println("Z ="+ startPoint[2][0]);
        newPointCoordinates = MathOperations.multiplyMatrices(transformationMatrix, startPoint, 3, 3, 1);
        //System.out.println("X ="+ newPointCoordinates[0][0]);
        //System.out.println("Y ="+ newPointCoordinates[1][0]);
        //System.out.println("Z ="+ newPointCoordinates[2][0]);

        Vector dir = new Vector(newPointCoordinates[0][0],newPointCoordinates[1][0], newPointCoordinates[2][0]);
        //Constructing a new Ray
        Ray exactRay = new Ray(rayStartPointInNewCoordinates, dir);
        List<GeoPoint> intersectionPoints = getIntersectionPointsInStraightCoordinates(new Cylinder(this._radius, new Ray(axeStartPointInNewCoordinates, newI), this.height), exactRay);
        //needs return to the old coordinates
        if(intersectionPoints==null)
            return null;
        List<GeoPoint> realIntersections = new ArrayList<GeoPoint>();
        double [][] inverseMatrix = MathOperations.invert(transformationMatrix);
        int numOfPoints = intersectionPoints.size();
        for(int i=0; i<numOfPoints; i++)
        {
            //transforming point to the source coordinate system
            startPoint[0][0] = intersectionPoints.get(i).point.getX().get();
            startPoint[1][0] = intersectionPoints.get(i).point.getY().get();
            startPoint[2][0] = intersectionPoints.get(i).point.getZ().get();
            newPointCoordinates = MathOperations.multiplyMatrices(inverseMatrix, startPoint, 3, 3, 1);
            realIntersections.add( new GeoPoint(this,
                    new Point3D(newPointCoordinates[0][0],newPointCoordinates[1][0], newPointCoordinates[2][0])));

        }
        return realIntersections;

    }
}
