package unittests;

import geometries.*;
import primitives.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CylinderTest {
Cylinder cylinder = new Cylinder(2, new Ray(new Point3D(0, 0, 0), new Vector(1, 0, 0)), 5);
    @Test
    void getNormal() {
        //====================Equivalent Partitioning===========================

            assertTrue(cylinder.getNormal(new Point3D(5, 2, 0)).equals(new Vector(1, 0, 0)));
        //====================Equivalent Partitioning===========================

            assertTrue(cylinder.getNormal(new Point3D(5, 1, 0)).equals(new Vector(1, 0, 0)));

        //====================Equivalent Partitioning===========================
        boolean isCorrect = false;
        try{
            cylinder.getNormal(new Point3D(2, 1, 0));
        }
        catch (Exception ex){
            isCorrect = true;
        }
        //assertThrows(Exception, cylinder.getNormal(new Point3D(2, 1, 0)));
        assertTrue(isCorrect);

        //====================Equivalent Partitioning===========================
        isCorrect =false;
        try {
            cylinder.getNormal(new Point3D(10, 0.1, 0));
        }
        catch (Exception ex){
            isCorrect = true;
        }
        assertTrue(isCorrect);

        // all other checks are same to tube, and that's the reason why their tests are not implemented here

    }

}