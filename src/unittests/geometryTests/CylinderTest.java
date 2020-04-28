package unittests.geometryTests;

import geometries.*;
import primitives.*;
import java.util.List;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Class Cylinder is a class that contains basic checks for Cylinder class member functions
 * author@ Polina Frolov Korogodsky and Tselia Tebol
 */
class CylinderTest {
Cylinder cylinder = new Cylinder(2, new Ray(new Point3D(0, 0, 0), new Vector(1, 0, 0)), 5);
Cylinder cylinder2 = new Cylinder(3, new Ray (new Point3D(0, 0, 0), new Vector(0, 0, 1)), 5);

    /**
     * getNormal() Equivalent partitioning tests
     */
    @Test
    void getNormal() {
        //====================Equivalent Partitioning===========================
        //TC1: the point on upper base's edge (Boundary Value Analysis)
            assertTrue(cylinder.getNormal(new Point3D(5, 2, 0)).equals(new Vector(1, 0, 0)));
        //TC2: the point is on upper base but not on the edge

            assertTrue(cylinder.getNormal(new Point3D(5, 1, 0)).equals(new Vector(1, 0, 0)));

        //TC3: the point is inside the cylinder
        boolean isCorrect = false;
        try{
            cylinder.getNormal(new Point3D(2, 1, 0));
        }
        catch (Exception ex){
            isCorrect = true;
        }
        //assertThrows(Exception, cylinder.getNormal(new Point3D(2, 1, 0)));
        assertTrue(isCorrect);

        //TC4: The point is outside the cylinder
        isCorrect =false;
        try {
            cylinder.getNormal(new Point3D(10, 0.1, 0));
        }
        catch (Exception ex){
            isCorrect = true;
        }
        assertTrue(isCorrect);
        // TC5: the point is on the down base

        assertTrue(cylinder2.getNormal(new Point3D(1, 2, 0)).equals(new Vector(0, 0, -1)));

        //TC6: the point is on the surface
        assertTrue(cylinder2.getNormal(new Point3D(0, 3, 4)).equals(new Vector(0, 3, 0).normalized()));
        //TC7: the point is on tube's surface, but the height of cylinder is not getting there
        isCorrect = false;
        try{
            cylinder2.getNormal(new Point3D(0, 3, 12));
        }
        catch (Exception ex){
            isCorrect=true;
        }
        assertTrue(isCorrect);

    }

    /**
     * getHeight test
     */
    @Test
    void getHeight() {
        assertTrue(cylinder2.getHeight()==5);
    }
    /**
     * toString() test
     */
    @Test
    void toStringTest(){
        assertTrue(cylinder2.toString().equals("Cylinder{height=5.0 Tube{axe=Ray:start:Point3D:(0.0,0.0,0.0), direction:Vector: end point: Point3D:(0.0,0.0,1.0) RadialGeometry: radius =  3.0} } "));

}
    Cylinder oz = new Cylinder(1, new Ray(new Point3D(0, 0, 1), new Vector(0, 0, 1)), 5);

    @Test
    void debug(){
        List<Point3D> points = oz.findIntersections(new Ray(new Point3D(0, 2, 2), new Vector(0, -1, 0)));
    }
    @Test
    void findIntersections() {


        //=================BVA==================
        //Group: ray's line intersects the surface but not the base
        //TC11: The ray starts before the cylinder
        assertTrue(oz.findIntersections(new Ray(new Point3D(0, 2, 2), new Vector(0, -1, 0))).contains(new Point3D(0, 1, 2)));
        assertTrue(oz.findIntersections(new Ray(new Point3D(0, 2, 2), new Vector(0, -1, 0))).contains(new Point3D(0, -1, 2)));
        assertEquals(oz.findIntersections(new Ray(new Point3D(0, 2, 2), new Vector(0, -1, 0))).size(), 2, "Wrong amount of points");
        //TC12: The ray starts after cylinder
        assertNull(oz.findIntersections(new Ray(new Point3D(0, 2, 2), new Vector(0, 1, 0))));
        //TC13: The ray starts inside the cylinder
        assertTrue(oz.findIntersections(new Ray(new Point3D(0, 0.5, 2), new Vector(0, -1, 0))).contains(new Point3D(0, -1, 2)));
        //TC14: The ray starts on the cylinder's surface
        //TC14.1: goes inside
        assertTrue(oz.findIntersections(new Ray(new Point3D(0, 1, 2), new Vector(0, -1, 0))).contains(new Point3D(0, -1, 2)));
        assertEquals(oz.findIntersections(new Ray(new Point3D(0, 1, 2), new Vector(0, -1, 0))).size(), 1, "Wrong amount of points TC14.1");
        //TC14.2: goes outside
        assertNull(oz.findIntersections(new Ray(new Point3D(0, 1, 2), new Vector(0, 1, 0))));
        //Group: ray's line is higher then cylinder and intersects a tube with the same ray and radius
        //TC15: ray is higher
        assertNull(oz.findIntersections(new Ray(new Point3D(0, 1, 15), new Vector(0, 1, 0))));
        //Group: Ray's line intersects two bases
        //TC16: The ray starts before the cylinder
        assertTrue(oz.findIntersections(new Ray(new Point3D(0.5, 0.5, 0), new Vector(0, 0, 1))).contains(new Point3D(0.5, 0.5, 1)));
        assertTrue(oz.findIntersections(new Ray(new Point3D(0.5, 0.5, 0), new Vector(0, 0, 1))).contains(new Point3D(0.5, 0.5, 6)));
        assertEquals(oz.findIntersections(new Ray(new Point3D(0.5, 0.5, 0), new Vector(0, 0, 1))).size(), 2, "TC16 wrong num of points");
        //TC17: The ray starts after cylinder
        assertNull(oz.findIntersections(new Ray(new Point3D(0.5, 0.5, 0), new Vector(0, 0, -1))));
        //TC18: The ray starts inside the cylinder
        assertTrue(oz.findIntersections(new Ray(new Point3D(0.5, 0.5, 3), new Vector(0, 0, 1))).contains(new Point3D(0.5, 0.5, 6)));
        assertEquals(oz.findIntersections(new Ray(new Point3D(0.5, 0.5, 3), new Vector(0, 0, 1))).size(), 1, "TC18: wrong amount of points");
        //TC19: The ray starts on the cylinder's surface
        //TC19.1: and goes inside
        assertTrue(oz.findIntersections(new Ray(new Point3D(0.5, 0.5, 1), new Vector(0, 0, 1))).contains(new Point3D(0.5, 0.5, 6)));
        assertEquals(oz.findIntersections(new Ray(new Point3D(0.5, 0.5, 1), new Vector(0, 0, 1))).size(), 1, "TC19.1: Wrong amount of points");
        //TC19.2: and goes outside
        assertNull((oz.findIntersections(new Ray(new Point3D(0.5, 0.5, 1), new Vector(0, 0, -1)))));
        //Group: the ray intersects a base and a side surface
        //TC20: The ray starts before the cylinder
        assertTrue(oz.findIntersections(new Ray(new Point3D(-1, -1, 0), new Vector(1, 1, 1))).contains(new Point3D(0, 0, 1)));
        assertTrue(oz.findIntersections(new Ray(new Point3D(-1, -1, 0), new Vector(1, 1, 1))).contains(new Point3D(0.707, 0.707, 1.707)));
        assertEquals(2, oz.findIntersections(new Ray(new Point3D(-1, -1, 0), new Vector(1, 1, 1))), "TC20: Wrong amount of points");
        //TC21: The ray starts after cylinder
        assertNull(oz.findIntersections(new Ray(new Point3D(-1, -1, 0), new Vector(-1, -1, -1))));
        //TC22: The ray starts on the cylinder's surface
        //TC22.1: and goes inside
        assertTrue(oz.findIntersections(new Ray(new Point3D(0, 0, 1), new Vector(1, 1, 1))).contains(new Point3D(0.707, 0.707, 1.707)));
        assertEquals(oz.findIntersections(new Ray(new Point3D(0, 0, 1), new Vector(1, 1, 1))).size(), 1, "TC22.1: wrong amount of points");
        //TC22.2: and goes out
        assertNull(oz.findIntersections(new Ray(new Point3D(0, 0, 1), new Vector(-1, -1, -1))));
        //Group: ray's line is tangent o the surface of cylinder
        //TC23: Tangent to base
        assertNull(oz.findIntersections(new Ray(new Point3D(-1, -1, 1), new Vector(1, 1, 0))));
        //TC24: Tangent to side surface
        //TC24.1: parallel to the axis
        assertNull(oz.findIntersections(new Ray(new Point3D(1, 0, 0), new Vector(0, 0, 1))));
        //TC24.2: not parallel to the axis
        assertNull(oz.findIntersections(new Ray(new Point3D(0, -1, 3), new Vector(0, 1, 0))));
        //TC25: Tangent to the "angle"
        assertNull(oz.findIntersections(new Ray(new Point3D(0, -1, 0), new Vector(1, 1, 1))));
    }
}