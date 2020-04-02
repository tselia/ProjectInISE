package unittests;
import geometries.*;
import primitives.*;
import org.junit.jupiter.api.Test;

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
    /*
*/
}