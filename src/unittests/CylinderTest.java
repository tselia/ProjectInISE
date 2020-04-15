package unittests;

import geometries.*;
import primitives.*;
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
}