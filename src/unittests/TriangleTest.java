package unittests;

import geometries.*;
import org.junit.jupiter.api.Test;
import primitives.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
/**
 * Class Triangle is a test class for triangle
 * Authors - Polina Frolov Korogodsky and Tselia Tebol
 */
class TriangleTest {

    @Test
    void findIntersections() {
        Vector k=new Vector(0, 0, 1);
        Triangle tri = new Triangle(new Point3D(2, 0, 7), new Point3D(0, 3, 7), new Point3D(-3, 0, 7));
        // ============ Equivalence Partitions Tests ==============
        //TC01:Inside
        Ray ray01 = new Ray(new Point3D(-1, 1, 0), k);
        List<Point3D> inter01 = tri.findIntersections(ray01);
        assertEquals(inter01.size(), 1);
        assertEquals(inter01.get(0), new Point3D(-1,  1, 7), "The point is not correct");

        //TC02: Outside against edge
        Ray ray02 = new Ray(new Point3D(-1, -2, 2), k);
        assertNull(tri.findIntersections(ray02));

        //TC03: Outside against the vertex
        assertNull(tri.findIntersections(new Ray(new Point3D (0, 4, 3), k)));
        //=================BVA==========================
        // TC11:On edge
        assertNull(tri.findIntersections(new Ray(new Point3D(-2, 0, 4), k)));
        // TC12: On Vertex
        assertNull(tri.findIntersections(new Ray(new Point3D(2, 0, 1), k)));
        // TC13: On edge's continuation
        assertNull(tri.findIntersections(new Ray(new Point3D(3, 0, 1), k)));
    }
}