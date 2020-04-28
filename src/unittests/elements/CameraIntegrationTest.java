package unittests.elements;

import elements.Camera;
import geometries.Plane;
import geometries.Sphere;
import geometries.Triangle;
import org.junit.jupiter.api.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

//import java.util.List;

//import //primitives.Vector;
/**
 * class CameraIntegrationTest contains the basic tests for camera integration
 * authors Polina Frolov Korogodsky and Tselia Tebol
 */


public class CameraIntegrationTest {
    /**
     * two intersection points for the sphere that only one ray crosses it
     */
    @Test
    void SphereBeforeTheViewPlaneTest() {
        Sphere sphere = new Sphere(1, new Point3D(0, 0, 3));
        Camera cam = new Camera(Point3D.zero, new Vector(0, -1, 0), new Vector(0, 0, 1));
        List<Point3D> intersections = new ArrayList<Point3D>();
        for(int i=0; i<3; i++) {
            for (int j = 0; j < 3; j++) {
                Ray ray = cam.constructRayThroughPixel(3, 3, i, j, 1, 3, 3);
                System.out.println(ray.toString());
                List<Point3D> pts = sphere.findIntersections(ray);
                if(pts!=null)
                    System.out.println(pts.size());
                //System.out.println(pts.size());
                if (pts != null)
                    for (Point3D point : pts) {
                        intersections.add(point);
                    }

            }
        }

        assertEquals(2, intersections.size(), "wrong amount of points");
    }

    /**
     * the sphere is before the view plane and it's crossed by all the rays
     */
    @Test
    void viewPlaneInsideSphereTest(){
        Sphere sphere = new Sphere(2.5, new Point3D(0, 0, 2.5));
        Camera cam = new Camera(new Point3D(0, 0, -0.5), new Vector(0, -1, 0), new Vector(0, 0, 1));
        List<Point3D> intersections = new ArrayList<Point3D>();
        for(int i=0; i<3; i++)
            for(int j=0; j<3; j++){
                List<Point3D> pts = sphere.findIntersections(cam.constructRayThroughPixel(3, 3, j, i, 1, 3, 3));
                if(pts!=null)
                    for (Point3D point:pts) {
                        intersections.add(point);
                    }

            }

        assertEquals(18, intersections.size(), "wrong amount of points");
    }

    /**
     * 10 intersection points in the special case when the view plane is inside the sphere
     */
    @Test
    void viewPlaneIntersectsTheSphereTest(){
        Sphere sphere = new Sphere(2, new Point3D(0, 0, 2));
        Camera cam = new Camera(new Point3D(0, 0, -0.5), new Vector(0, -1, 0), new Vector(0, 0, 1));
        List<Point3D> intersections = new ArrayList<Point3D>();
        for(int i=0; i<3; i++)
            for(int j=0; j<3; j++){
                List<Point3D> pts = sphere.findIntersections(cam.constructRayThroughPixel(3, 3, j, i, 1, 3, 3));
                if(pts!=null)
                    for (Point3D point:pts) {
                        intersections.add(point);
                    }

            }

        assertEquals(10, intersections.size(), "wrong amount of points");
    }

    /**
     * 9 intersection points if the view plane is inside the sphere
     */
    @Test
    void viewPlaneIsInsideTheSphere(){
        // Sphere sphere = new Sphere()
        Sphere sphere = new Sphere(4, new Point3D(0, 0, 2));
        Camera cam = new Camera(new Point3D(0, 0, -0.5), new Vector(0, -1, 0), new Vector(0, 0, 1));
        List<Point3D> intersections = new ArrayList<Point3D>();
        for(int i=0; i<3; i++)
            for(int j=0; j<3; j++){
                List<Point3D> pts = sphere.findIntersections(cam.constructRayThroughPixel(3, 3, j, i, 1, 3, 3));
                if(pts!=null)
                    for (Point3D point:pts) {
                        intersections.add(point);
                    }

            }

        assertEquals(9, intersections.size(), "wrong amount of points");
    }

    /**
     * no intersections if the sphere is behind the view plane
     */
    @Test
    void sphereBehindTheViewPlaneTest(){
        Sphere sphere = new Sphere(0.5, new Point3D(0, 0, -1));
        Camera cam = new Camera(new Point3D(0, 0, -0.4), new Vector(0, -1, 0), new Vector(0, 0, 1));
        List<Point3D> intersections = new ArrayList<Point3D>();
        for(int i=0; i<3; i++)
            for(int j=0; j<3; j++){
                List<Point3D> pts = sphere.findIntersections(cam.constructRayThroughPixel(3, 3, j, i, 1, 3, 3));
                if(pts!=null)
                    for (Point3D point:pts) {
                        intersections.add(point);
                    }

            }
        assertTrue(intersections.isEmpty());
    }
    Camera cam = new Camera(new Point3D(0, 0, -1), new Vector(0, -1, 0), new Vector(0, 0, 1));

    /**
     * all the rays intersect the plane that is parallel to the view plane
     */
    @Test
    void planeIsParallelToTheViewPlane(){
        Plane plane = new Plane(new Point3D(0, 0, 1), new Vector(0, 0, 1));
        List<Point3D> intersections = new ArrayList<Point3D>();
        for(int i=0; i<3; i++)
            for(int j=0; j<3; j++){
                List<Point3D> pts = plane.findIntersections(cam.constructRayThroughPixel(3, 3, j, i, 1, 3, 3));
                if(pts!=null)
                    for (Point3D point:pts) {
                        intersections.add(point);
                    }

            }
        assertEquals(9, intersections.size(), "Wrong amount of points");
    }

    /**
     * all the rays intersect the plane that it's nearly parallel to the view plane
     */
    @Test
    void diagonalPlaneTest(){
        Plane plane = new Plane(new Point3D(0, 0, 10), new Vector(0.05, 0.009, 1.08));
        System.out.println(plane.toString());
        List<Point3D> intersections = new ArrayList<Point3D>();
        for(int i=0; i<3; i++)
            for(int j=0; j<3; j++){
                Ray ray = cam.constructRayThroughPixel(3, 3, i, j, 1, 3, 3);

                List<Point3D> pts = plane.findIntersections(cam.constructRayThroughPixel(3, 3, j, i, 1, 3, 3));
                if(pts!=null)
                    for (Point3D point:pts) {
                        intersections.add(point);
                    }
                else
                    System.out.println(ray.toString());
            }
        assertEquals(9, intersections.size(), "Wrong amount of points");

    }

    /**
     * if the plane is defined by cross product of two vectors that were constructed by camera,
     * it's parallel to this vectors and this rays will not intersect the plane
     */
    @Test
    void notAllRaysCrossThePlane(){
        Plane plane = new Plane(new Point3D(3, 3, 3), new Vector(-1, -1, 1).crossProduct(new Vector(-1, 1, 1)));
        List<Point3D> intersections = new ArrayList<Point3D>();
        for(int i=0; i<3; i++)
            for(int j=0; j<3; j++){
                List<Point3D> pts = plane.findIntersections(cam.constructRayThroughPixel(3, 3, j, i, 1, 3, 3));
                if(pts!=null) {
                    System.out.println(cam.constructRayThroughPixel(3, 3, j, i, 1, 3, 3).toString() + "has "+ pts.get(0).toString());
                    for (Point3D point : pts) {
                        intersections.add(point);
                    }
                }
            }
        assertEquals(6, intersections.size(), "Wrong amount of points");

    }

    /**
     * triangle is intersected by one ray
     */
    @Test
    void littleTriangleBeforeTheViewPlane(){
        Triangle triangle = new Triangle(new Point3D(0, -1, 2), new Point3D(1, 1, 2), new Point3D(-1, 1, 2));
        List<Point3D> intersections = new ArrayList<Point3D>();
        for(int i=0; i<3; i++)
            for(int j=0; j<3; j++){
                List<Point3D> pts = triangle.findIntersections(cam.constructRayThroughPixel(3, 3, j, i, 1, 3, 3));
                if(pts!=null)
                    for (Point3D point:pts) {
                        intersections.add(point);
                    }

            }
        assertEquals(1, intersections.size(), "Wrong amount of points");
    }

    /**
     * The triangle is intersected by 2 rays
     */
    @Test
    void highTriangleBeforeTeViewPlane(){
        Triangle tri = new Triangle(new Point3D(1, 1, 2),new Point3D(-1, 1, 2),new Point3D(0, -20, 2));
        List<Point3D> intersections = new ArrayList<Point3D>();
        for(int i=0; i<3; i++)
            for(int j=0; j<3; j++){
                List<Point3D> pts = tri.findIntersections(cam.constructRayThroughPixel(3, 3, j, i, 1, 3, 3));
                if(pts!=null)
                    for (Point3D point:pts) {
                        intersections.add(point);
                    }

            }
        assertEquals(2, intersections.size(), "Wrong amount of points");
    }

}
