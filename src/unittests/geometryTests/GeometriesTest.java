package unittests.geometryTests;

import geometries.*;
import org.junit.jupiter.api.Test;
import primitives.*;



import static org.junit.jupiter.api.Assertions.*;
/**
 * Class GeometriesTest is a test class for findIntersections() method of Geometries class
 * authors Polina Frolov Korogodsky and Tselia Tebol
 */
class GeometriesTest {

    @Test
    void findIntersections() {
        Geometries geo = new Geometries(new Sphere(1d, new Point3D(1, 0, 0)), new Triangle(new Point3D(1, 1, 0), new Point3D(0, 0, 1), new Point3D(0, 1, 0)), new Polygon(new Point3D(1, 2, 5), new Point3D(-3, 2, 5), new Point3D(-3, -2, 5), new Point3D(1, -2, 5)));
        Geometries geo2 = new Geometries(new Sphere(10, new Point3D(0, 1, 0)), new Polygon(new Point3D(1, 2, 5), new Point3D(-3, 2, 5), new Point3D(-3, -2, 5), new Point3D(1, -2, 5)), new Triangle(new Point3D(1, 1, 0), new Point3D(2, 1, 0), new Point3D(1, 0, 0)));
        Geometries geo3 = new Geometries(new Sphere(10, new Point3D(0, 1, 0)), new Polygon(new Point3D(1, 2, 5), new Point3D(-3, 2, 5), new Point3D(-3, -2, 5), new Point3D(1, -2, 5)), new Triangle(new Point3D(-2, 0, 0), new Point3D(0, 3, 0), new Point3D(1, 0, 0)));
        //===============BVA=============
        //TC01: empty list of geometries
        Geometries empty=new Geometries();
        assertNull(empty.findIntersections(new Ray(new Point3D(1, 1, 0), new Vector(0, 0, 1))));
        System.out.println("TC01 complete");
        //TC02: no geometry intersects the ray
        assertNull(geo.findIntersections(new Ray(new Point3D(-5, -5, -5), new Vector(0, 0, -1))));
        System.out.println("TC02 complete");
        //TC03: Only one geometry has intersections with the ray
        assertEquals(geo.findIntersections(new Ray(new Point3D(-1, 1, 3), new Vector(0, 0, 1))).size(), 1);//only the polygon intersects
        System.out.println("TC03.1 complete");
        assertEquals(geo.findIntersections(new Ray(new Point3D(-1, 0, 0),
                new primitives.Vector(3, 1, 0))).size(), 2);//if it's a 3D geometry that has two intersection points
        System.out.println("TC03.2 complete");
        //=======EP======
        //TC04: More then one geometry has intersection, but not all of them
        assertEquals(geo2.findIntersections(new Ray(new Point3D(-1, 1, 3), new Vector(0, 0, 1))).size(), 2);//the sphere and the polygon has one intersection point each one, the triangle has no intersections
        System.out.println("TC04 complete");
        //TC05: All the geometries have intersection points with the ray
        assertEquals(geo3.findIntersections((new Ray(new Point3D(0, 1, -1),
                new primitives.Vector(0, 0, 1)))).size(), 3);
        System.out.println("TC05 complete");

    }
}