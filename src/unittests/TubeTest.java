package unittests;
import geometries.*;
import primitives.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
/**
 * TubeTests implements basic tests for Tube class member functions
 * Authors : Polina Frolov Korogodsky and Tselia Tebol
 */
class TubeTest {
Tube tube1 = new Tube (1, new Ray(new Point3D (0, 0, 0), new Vector (0, 0, 1)));// simple vertical tube
    Tube tube2 = new Tube (3, new Ray (new Point3D(3, 4, 0), new Vector(1, 1, 1)));// not-straight tube

    /**
     * getNormal 3 EP tests
     */
    @Test
    void getNormal() {
        //====================Equivalent Partitioning===========================
        //TC1: the point is on surface
        assertTrue(tube1.getNormal(new Point3D(1, 0, 3)).equals(new Vector(1, 0, 0)));
        //TC2: inside the tube
        boolean isCorrect = false;
        try{
            tube1.getNormal(new Point3D(0, 0.5, 8));
        }
        catch(Exception ex){
            isCorrect = true;
        }
        assertTrue(isCorrect);
        //TC3: the point is outside the tube
        isCorrect = false;
        try{
            tube1.getNormal(new Point3D(20, 15, 8));
        }
        catch(Exception ex){
            isCorrect = true;
        }
        assertTrue(isCorrect);
    }

    /**
     * getAxe() test
     */

    @Test
    void getAxe(){
        assertTrue(tube1.getAxe().equals(new Ray(new Point3D (0, 0, 0), new Vector (0, 0, 1))));
    }

    /**
     * Tests whether constructor enables to make a tube wit axe that is zero-vector-based
     */
    @Test
    void Tube(){
        boolean isCorrect  = false;
        try{
            Tube t = new Tube (0.5, new Ray(new Point3D(Point3D.zero), new Vector(Point3D.zero)));
        }
        catch(Exception ex){
            isCorrect = true;
        }
        assertTrue(isCorrect);
        //no need for more tests of constructors because tube1, tube2 were created correctly
    }



}