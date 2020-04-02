package primitives;
import java.io.*;

/**
 *  PrimitivesTestClass is a main class for basic debug of member classes of primitives package
 *  author@ Polina Frolov Korogodsky and Tselia Tebol
 */
public class PrimitivesTestClass {
    public static void main(String[] args) {
        //testing Point3D
/*        Point3D A = new Point3D(5.2, 0.1, 8);
        Point3D B = new Point3D(new Coordinate(4.5), new Coordinate(10), new Coordinate(2.3));
        */
        Point3D Cn = new Point3D(1, 1, 1);/*
        Point3D C = new Point3D(Cn);

        System.out.println(A.toString());
        System.out.println(B.toString());
        System.out.println(C.toString());

        System.out.println(A.subtract(Point3D.zero));
        System.out.println((A.distance(Point3D.zero)));
        System.out.println((A.squaredDistance(Point3D.zero)));

        //System.out.println(A.add(B));
        Vector dn = new Vector(1, 1, 1);
        Vector AB = new Vector(A.subtract(B));
        System.out.println(AB);
        System.out.println(C.add(dn));
        System.out.println(Point3D.zero.add(dn));
        //System.out.println();*/

        //Vector testing
/*
        Vector AB = new Vector(1, 0, 0);
        //System.out.println(AB);
        Vector CD = new Vector(3, 0, 0);
        //System.out.println(CD.normalize());
       // System.out.println(CD.length());
        try {
            System.out.println(CD.crossProduct(AB));
        }
        catch (ArithmeticException ex){
           System.out.println(ex.getMessage());
        }*/

// Ray tests

Vector v1 = new Vector(8, 12, -5);
Vector v2 = new Vector(v1.scale(4.521));
System.out.println(v1);
        System.out.println(v2);

    }
}
