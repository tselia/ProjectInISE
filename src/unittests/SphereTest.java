package unittests;

import org.junit.jupiter.api.Test;
import geometries.*;
import primitives.*;
import primitives.Vector;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
/**
 * Class SphereTest is a test class for sphere's fuctions
 * (no check needed for constructor - it can't throw exception or make smth except for simple initiation by the '=' operator
 * Authors : Polina Frolov Korogodsky and Tselia Tebol
 */
class SphereTest {
Sphere s1 = new Sphere (1, new Point3D(0, 0, 0));
Sphere s2 = new Sphere(8, new Point3D(0, 0, 0));
Sphere s3 = new Sphere (0.5, new Point3D(0, 0, 0));

    /**
     * Checks whether sphere's getNormal function works well
     */
    @Test
    void getNormal() {
        //System.out.println("HI!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        //==========================Equivalence Partitioning=====================
        //Point on the sphere's surface
            assertTrue(s1.getNormal(new Point3D(0, 0, 1)).equals(new primitives.Vector(0, 0, 1)));


            assertTrue(s2.getNormal(new Point3D(8, 0, 0)).equals(new primitives.Vector(1, 0, 0)));

            assertTrue(s3.getNormal(new Point3D(0, 0.5, 0)).equals(new primitives.Vector(0, 1, 0)));


        //Point inside the sphere
        boolean isCorrect = false;
        try{
            primitives.Vector v = new primitives.Vector(s1.getNormal(new Point3D(0.5, 0.5, 0)));
        }
        catch(Exception x){
            isCorrect = true;
        }
        assertTrue(isCorrect);

        //Point outside the sphere
        isCorrect = false;
        try{
            primitives.Vector v = new primitives.Vector(s1.getNormal(new Point3D(20, 20, 20)));
        }
        catch(Exception x){
            isCorrect = true;
        }assertTrue(isCorrect);

    }

    /**
     * toString() member method check
     */
    @Test
    void toStringTest(){
        assertTrue(s1.toString().equals("Sphere{center=Point3D:(0.0,0.0,0.0) RadialGeometry: radius =  1.0} "));
    }

    /**
     * Test method for {@link geometries.Sphere#findIntersections(primitives.Ray)}.
     */
    @Test
    public void testFindIntersections() {
        Sphere sphere = new Sphere(1d, new Point3D(1, 0, 0));//1d means "1 double"

        // ============ Equivalence Partitions Tests ==============

        // TC01: Ray's line is outside the sphere (0 points)
        Ray ray = new Ray(new Point3D(-10,0,0), new Vector(0,0,1));
        assertTrue(sphere.findIntersections(ray).isEmpty());



        // TC02: Ray starts before and crosses the sphere (2 points)
        Point3D p1 = new Point3D(0.0651530771650466, 0.355051025721682, 0);
        Point3D p2 = new Point3D(1.53484692283495, 0.844948974278318, 0);
        List<Point3D> result = sphere.findIntersections(new Ray(new Point3D(-1, 0, 0),
                new primitives.Vector(3, 1, 0)));
        assertTrue(result.contains(p1)&&result.contains(p2)&&result.size()==2);


        // TC03: Ray starts inside the sphere (1 point)
        List<Point3D> intersetionPoints=sphere.findIntersections(new Ray(new Point3D(1, 0, 0), new Vector(0, 0, 1)));
        assertTrue(sphere.findIntersections(new Ray(new Point3D(1, 0, 0), new Vector(0, 0, 1))).contains(new Point3D(1, 0, 1)));
        intersetionPoints.remove(new Point3D(1, 0, 1));
        assertTrue(intersetionPoints.isEmpty());// checks that no other points were found as intersection points


        // TC04: Ray starts after the sphere (0 points)
        assertTrue(sphere.findIntersections(new Ray(new Point3D(10, 0, 0), new Vector(0, 0, 1))).isEmpty());



        // =============== Boundary Values Tests ==================

        // **** Group: Ray's line crosses the sphere (but not the center)
        // TC11: Ray starts at sphere and goes inside (1 points)
        assertTrue(sphere.findIntersections(new Ray(new Point3D(1,1, 0), new Vector(-0.2, -0.5, 0.84261497))).contains(new Point3D(-0.2, -0.5, 0.84261497)));
        assertTrue(sphere.findIntersections(new Ray(new Point3D(1,1, 0), new Vector(-0.2, -0.5, 0.84261497))).size()==1);// the ues is whether I've counted well


        // TC12: Ray starts at sphere and goes outside (0 points)
       assertTrue(sphere.findIntersections(new Ray(new Point3D(1,1, 0), new Vector(0.2, 0.5, -0.84261497))).isEmpty());

        // **** Group: Ray's line goes through the center
        // TC13: Ray starts before the sphere (2 points)
        assertTrue(sphere.findIntersections(new Ray(new Point3D(-1, 0, 0), new Vector(1, 0,0))).size()==2);
        assertTrue(sphere.findIntersections(new Ray(new Point3D(-1, 0, 0), new Vector(1, 0,0))).contains(new Point3D(0, 0, 0)));
        assertTrue(sphere.findIntersections(new Ray(new Point3D(-1, 0, 0), new Vector(1, 0,0))).contains(new Point3D(2, 0, 0)));
        // TC14: Ray starts at sphere and goes inside (1 points)
        assertTrue(sphere.findIntersections(new Ray(new Point3D(1, 1, 0), new Vector(0, -1, 0))).size()==1);
        assertTrue(sphere.findIntersections(new Ray(new Point3D(1, 1, 0), new Vector(0, -1, 0))).contains(new Point3D(1, -1, 0)));
        // TC15: Ray starts inside (1 points)
        assertTrue(sphere.findIntersections(new Ray(new Point3D(1, 0.5, 0), new Vector(0, -1, 0))).size()==1);
        assertTrue(sphere.findIntersections(new Ray(new Point3D(1, 0.5, 0), new Vector(0, -1, 0))).contains(new Point3D(1, -1, 0)));
        // TC16: Ray starts at the center (1 points)
        assertTrue(sphere.findIntersections(new Ray(new Point3D(1, 0, 0), new Vector(0, -1, 0))).size()==1);
        assertTrue(sphere.findIntersections(new Ray(new Point3D(1, 0, 0), new Vector(0, -1, 0))).contains(new Point3D(1, -1, 0)));
        // TC17: Ray starts at sphere and goes outside (0 points)
        assertTrue(sphere.findIntersections(new Ray(new Point3D(1, 1, 0), new Vector(0, 1, 0))).isEmpty());
        // TC18: Ray starts after sphere (0 points)
        assertTrue(sphere.findIntersections(new Ray(new Point3D(-1, 0, 0), new Vector(-1, 0,0))).isEmpty());
        // **** Group: Ray's line is tangent to the sphere (all tests 0 points)
        // TC19: Ray starts before the tangent point
        assertTrue(sphere.findIntersections(new Ray(new Point3D(0, 1, 0), new Vector(1, 0, 0))).isEmpty());
        // TC20: Ray starts at the tangent point
        assertTrue(sphere.findIntersections(new Ray(new Point3D(1, 1, 0), new Vector(1, 0, 0))).isEmpty());
        // TC21: Ray starts after the tangent point
        assertTrue(sphere.findIntersections(new Ray(new Point3D(2, 1, 0), new Vector(1, 0, 0))).isEmpty());
        // **** Group: Special cases
        // TC19: Ray's line is outside, ray is orthogonal to ray start to sphere's center line
        assertTrue(sphere.findIntersections(new Ray(new Point3D(1, 1, 0), sphere.getNormal(new Point3D(1, 1, 0)))).isEmpty());

    }

}