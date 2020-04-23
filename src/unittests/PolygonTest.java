/**
 *
 */
package unittests;
//import static org.junit.Assert.*;
import org.junit.jupiter.api.Test;
//import org.junit.Test;
import static org.junit.jupiter.api.Assertions.*;
import geometries.*;
import primitives.*;

import java.util.List;

/**
 * Testing Polygons
 * @author Dan
 *
 */
public class PolygonTest {

    /**
     * Test method for
     * {@link geometries.Polygon# Polygon(primitives.Point3D, primitives.Point3D, primitives.Point3D, primitives.Point3D)}.
     */
    @Test
    public void testConstructor() {
        // ============ Equivalence Partitions Tests ==============

        // TC01: Correct concave quadrangular with vertices in correct order
        try {
            new Polygon(new Point3D(0, 0, 1), new Point3D(1, 0, 0),
                    new Point3D(0, 1, 0), new Point3D(-1, 1, 1));
        } catch (IllegalArgumentException e) {
            fail("Failed constructing a correct polygon");
        }

        // TC02: Wrong vertices order
        try {
            new Polygon(new Point3D(0, 0, 1), new Point3D(0, 1, 0),
                    new Point3D(1, 0, 0), new Point3D(-1, 1, 1));
            fail("Constructed a polygon with wrong order of vertices");
        } catch (IllegalArgumentException e) {}

        // TC03: Not in the same plane
        try {
            new Polygon(new Point3D(0, 0, 1), new Point3D(1, 0, 0),
                    new Point3D(0, 1, 0), new Point3D(0, 2, 2));
            fail("Constructed a polygon with vertices that are not in the same plane");
        } catch (IllegalArgumentException e) {}

        // TC04: Concave quadrangular
        try {
            new Polygon(new Point3D(0, 0, 1), new Point3D(1, 0, 0),
                    new Point3D(0, 1, 0), new Point3D(0.5, 0.25, 0.5));
            fail("Constructed a concave polygon");
        } catch (IllegalArgumentException e) {}

        // =============== Boundary Values Tests ==================

        // TC10: Vertix on a side of a quadrangular
        try {
            new Polygon(new Point3D(0, 0, 1), new Point3D(1, 0, 0),
                    new Point3D(0, 1, 0), new Point3D(0, 0.5, 0.5));
            fail("Constructed a polygon with vertex on a side");
        } catch (IllegalArgumentException e) {}

        // TC11: Last point = first point
        try {
            new Polygon(new Point3D(0, 0, 1), new Point3D(1, 0, 0),
                    new Point3D(0, 1, 0), new Point3D(0, 0, 1));
            fail("Constructed a polygon with vertice on a side");
        } catch (IllegalArgumentException e) {}

        // TC12: Collocated points
        try {
            new Polygon(new Point3D(0, 0, 1), new Point3D(1, 0, 0),
                    new Point3D(0, 1, 0), new Point3D(0, 1, 0));
            fail("Constructed a polygon with vertice on a side");
        } catch (IllegalArgumentException e) {}

    }

    /**
     * Test method for {@link geometries.Polygon#getNormal(primitives.Point3D)}.
     */
    @Test
    public void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: The point is the vertex
        Polygon pl = new Polygon(new Point3D(0, 0, 1), new Point3D(1, 0, 0), new Point3D(0, 1, 0),
                new Point3D(-1, 1, 1));
        double sqrt3 = Math.sqrt(1d / 3);



        assertTrue(pl.getNormal(new Point3D(0, 0, 1)).equals(new Vector(1, 1, 1).normalize())||pl.getNormal(new Point3D(0, 0, 1)).equals(new Vector(1, 1, 1).scale(-1).normalize()));

    }
    /**
     * Test method for findIntersections()
     */
    @Test
    void intersectionPoints(){
        // ============ Equivalence Partitions Tests ==============
        Polygon polygon = new Polygon(new Point3D(1, 2, 5), new Point3D(-3, 2, 5), new Point3D(-3, -2, 5), new Point3D(1, -2, 5));
        Polygon tri = new Triangle(new Point3D(1, 1, 0), new Point3D(0, 0, 1), new Point3D(0, 1, 0));
        if(tri.findIntersections(new Ray(new Point3D(-1, 1, 3), new Vector(0, 0, 1)))==null)
            System.out.println("Triangle has no intersections");
        //if(polygon.findIntersections(new Ray(new Point3D(2, 0, 3), new Vector(0, 0, 1)))==null)
           // System.out.println("Polygon has no intersections");
        //System.out.println(polygon.findIntersections(new Ray(new Point3D(-1, 1, 3), new Vector(0, 0, 1))).size());
        //TC01:Inside
        Ray ray01 = new Ray(new Point3D(-1, 1, 3), new Vector(0, 0, 1));
        List<Point3D> points01 = polygon.findIntersections(ray01);
        assertTrue(points01.contains(new Point3D(-1, 1, 5)));
        assertEquals(points01.size(), 1);

        //TC02: Outside against edge

        Ray ray02 = new Ray(new Point3D(2, 0, 3), new Vector(0, 0, 1));
        assertNull(polygon.findIntersections(ray02));
        //TC03: Outside against the vertex
        Ray ray03 = new Ray(new Point3D(2, 3, 3), new Vector(0, 0, 1));
        assertNull(polygon.findIntersections(ray03));
        //=================BVA==========================
        // TC11:On edge
        assertNull(polygon.findIntersections(new Ray(new Point3D(1, 1, 3), new Vector(0, 0, 1))));
        // TC12: On Vertex
        assertNull(polygon.findIntersections(new Ray(new Point3D(-3, 2, 3), new Vector(0, 0, 1))));
        // TC13: On edge's continuation
        assertNull(polygon.findIntersections(new Ray(new Point3D(3, 2, 1), new Vector(0, 0, 1))));


       //System.out.println(tri.findIntersections((new Ray(new Point3D(-1, 0, 0),
        //        new primitives.Vector(3, 1, 0)))));
        //System.out.println(polygon.findIntersections((new Ray(new Point3D(-1, 0, 0),
          //      new primitives.Vector(3, 1, 0)))));
    }

}
