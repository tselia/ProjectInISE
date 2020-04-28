package unittests.geometryTests;
import geometries.*;
import primitives.*;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
/**
 * Class PlaneTest is a class that contains the basic tests of class Plane
 * Authors - Polina Frolov Korogodsky and Tselia Tebol
 */
class PlaneTest {
Plane xz = new Plane(new Point3D(1, 0, 27), new Point3D(5, 0, 32), new Point3D (7, 0, 19));
Plane yx = new Plane(new Point3D(0, 17, 25), new Vector(0, 0, 1));

    /**
     * Tests whether returns the correct normalized normal
     */
    @Test
    void getNormal() {
        assertTrue(xz.getNormal().equals(new Vector(0, 1, 0)));
        assertTrue(yx.getNormal().equals(new Vector(0, 0, 1)));
    }

    /**
     * Checks whether returns one of the initial points
     */
    @Test
    void getPoint() {
        assertTrue(xz.getPoint().equals(new Point3D(1, 0, 27))||xz.getPoint().equals(new Point3D(5, 0, 32))||xz.getPoint().equals(new Point3D (7, 0, 19)));
        assertTrue(yx.getPoint().equals(new Point3D(0, 17, 25)));
    }

    /**
     * tests toString() method
     */
    @Test
    void testToString() {
        assertTrue(xz.toString().equals("Plane{point="+xz.getPoint().toString() + ", normal=" + xz.getNormal().toString()+"}"));
    }
    /**
     * Test method for findIntersections()
     */
    @Test
    void findIntersections() {
        //============EP==============
        //TC01: Ray intersects the plane
        Plane plane01=new Plane(new Point3D(0, 0, -3), new Vector(1, 1, -1));
        Ray ray01= new Ray(new Point3D(-48, -46, -48), new Vector(1, 1, 1) );
        List<Point3D> intersectionPoints01 = plane01.findIntersections(ray01);
        assertTrue(intersectionPoints01.contains(new Point3D(1, 3, 1)));
        assertEquals(intersectionPoints01.size(), 1);
        //TC02: Ray does not intersect the plane
        Ray ray02 = new Ray(new Point3D(2, 4, 2), new Vector(1, 1, 1));
        assertNull(plane01.findIntersections(ray02));
        //============BVA=============
        //Group: The ray is parallel to the plane
        //TC11: The ray included in the plane
        Plane plane11=new Plane(new Point3D(0, -2, 2), new Vector(1, -1, 1));
        Ray ray11 = new Ray (new Point3D(5, 3, -4), new Vector (-1, 0, 1));;
        assertNull(plane11.findIntersections(ray11));
        //TC12: The ray is not included in the plane //example from algebra1
        //Plane plane12=new Plane(new Point3D(0, -2, 2), new Vector(1, -1, 1));
        Ray ray12 = new Ray (new Point3D(1, 0, 2), new Vector (-1, 0, 1));
        assertNull(plane11.findIntersections(ray12));
        //Group: Ray is orthogonal to the plane
        //TC13: The ray starts before the plane
        Plane plane13 = new Plane(new Point3D(1, 2, 0), new Vector(0, 0, 7));//plane xy
        Ray ray13 = new Ray(new Point3D(1, 1, -1), new Vector(0, 0, 2));
        List<Point3D> intersectionPoints13 = plane13.findIntersections(ray13);
        assertTrue(intersectionPoints13.contains(new Point3D(1, 1, 0)));
        assertEquals(intersectionPoints13.size(), 1);
        //TC14: The ray starts after the plane
        Ray ray14 = new Ray(new Point3D(14, 0, 15), new Vector(0, 0, 1));
        assertNull(plane13.findIntersections(ray14));
        //TC15: The ray starts in the plane
        Ray ray15= new Ray(new Point3D(1, 5, 0), new Vector(0, 0, 1));
        assertNull(plane13.findIntersections(ray15));
        //Group: ray begins at the plane (not parallel and not orthogonal)
        //TC16: The ray begins at the plane
        Ray ray16 = new Ray (new Point3D(1, 7, 0), new Vector(0, 1, 1));
        assertNull(plane13.findIntersections(ray16));
        //Group: Ray is neither orthogonal nor parallel to the plane and begins in the same point which appears as reference point in the plane
        //TC17: ray begins at reference point
        Ray ray17 = new Ray(new Point3D(1, 2, 0), new Vector(-1, 7, 12));
        assertNull(plane13.findIntersections(ray17));
    }
    /*
*/
}