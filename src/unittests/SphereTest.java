package unittests;

import org.junit.jupiter.api.Test;
import geometries.*;
import primitives.*;

import static org.junit.jupiter.api.Assertions.*;
/**
 * Class SphereTest is a test class for sphere's fuctions
 * (no check needed for constructor - it can't throw exception or make smth except for simple initiation by the '=' operator
 * Authors : Polina Frolov Korogodsky and Tselia Tebol
 */
class SphereTest {
Sphere s1 = new Sphere (1, new Point3D(0, 0, 0));
Sphere s2 = new Sphere(8, new Point3D(0, 0, 0));
Sphere s3 = new Sphere (0.5, new Point3D(0, 0, 0));

    /**
     * Checks whether sphere's getNormal function works well
     */
    @Test
    void getNormal() {
        //System.out.println("HI!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        //==========================Equivalence Partitioning=====================
        //Point on the sphere's surface
            assertTrue(s1.getNormal(new Point3D(0, 0, 1)).equals(new Vector(0, 0, 1)));


            assertTrue(s2.getNormal(new Point3D(8, 0, 0)).equals(new Vector(1, 0, 0)));

            assertTrue(s3.getNormal(new Point3D(0, 0.5, 0)).equals(new Vector(0, 1, 0)));


        //Point inside the sphere
        boolean isCorrect = false;
        try{
            Vector v = new Vector(s1.getNormal(new Point3D(0.5, 0.5, 0)));
        }
        catch(Exception x){
            isCorrect = true;
        }
        assertTrue(isCorrect);

        //Point outside the sphere
        isCorrect = false;
        try{
            Vector v = new Vector(s1.getNormal(new Point3D(20, 20, 20)));
        }
        catch(Exception x){
            isCorrect = true;
        }assertTrue(isCorrect);

    }

    /**
     * toString() member method check
     */
    @Test
    void toStringTest(){
        assertTrue(s1.toString().equals("Sphere{center=Point3D:(0.0,0.0,0.0) RadialGeometry: radius =  1.0} "));
    }

}