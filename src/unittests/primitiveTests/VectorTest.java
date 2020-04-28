package unittests.primitiveTests;

import org.junit.jupiter.api.Test;
import primitives.Coordinate;
import primitives.Point3D;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;
import static primitives.Util.isZero;
/**
 * class VectorTest is a class made to check Vector functions
 * authors Polina Frolov Korogodsky and Tselia Tebol
 */
class VectorTest {

    Vector v1 = new Vector(1, 2, 3);
    Vector v2 = new Vector(-2, -4, -6);
    Vector v3 = new Vector(0, 3, -2);

    /**
     * test method for scale() function, by Dan
     */
    @Test
    void scale() {
        // ============ Boundary Values Tests ==============
        int x = 0;
        try{
            v1.scale(0);

        }
        catch (Exception ex){
            //assertTrue();
            x=1;
        }
        assertTrue(x==1);
        // ============ Equivalence Partitions Tests ==============
        assertTrue(v2.scale(-1).equals(new Vector(2, 4, 6)));
        // ============ Equivalence Partitions Tests ==============
        assertTrue(v2.scale(0.5).equals(v1.scale(-1)));

    }

    /**
     * The method normalize() and normalized() functions tests
     */
    @Test
    void normalize() {
        Vector v = new Vector(1, 2, 3);
        Vector vCopy = new Vector(v);
        Vector vCopyNormalize = vCopy.normalize();
        assertTrue(vCopy == vCopyNormalize);
        assertTrue(isZero(vCopyNormalize.length() - 1));
        Vector u = v.normalized();
        assertTrue(u!=v);

    }

    /**
     * Test method for lengthSquared() member function
     */
    @Test
    void lengthSquared() {
        assertTrue(isZero(v1.lengthSquared() - 14));
    }

    /**
     * Test method for length() member function
     */
    @Test
    void length() {
        isZero(new Vector(0, 3, 4).length() - 5);
    }

    /**
     * Test method to check adding af two vectors
     */
    @Test
    void add() {
        // ============ Equivalence Partitions Tests ==============
        assertTrue(v1.add(new Vector (1, 1, 1)).equals(new Vector(2, 3, 4)));
        // ============ Equivalence Partitions Tests ==============
        assertTrue(v1.add(new Vector (-1, -1, -1)).equals(new Vector(0, 1, 2)));
    }

    /**
     * Test method for vector subtraction function
     */
    @Test
    void subtract() {
        // ============ Equivalence Partitions Tests ==============
        assertTrue(v1.subtract(new Vector (1, 1, 1)).equals(new Vector(0, 1, 2)));
        // ============ Equivalence Partitions Tests ==============
        //System.out.println(v1.subtract(new Vector (-1, -1, -1)));
        assertTrue(v1.subtract(new Vector (-1, -1, -1)).equals(new Vector(2, 3, 4)));
    }

    /**
     * Test method for scalar multiplying of vectors
     */
    @Test
    void dotProduct() {
        // ============ Equivalence Partitions Tests ==============
        assertTrue(v1.dotProduct(v2)==-28);
        // ============ Equivalence Partitions Tests ==============
        assertTrue(v1.dotProduct(v3)==0);
    }

    /**
     * Test method for vector multiplying of vectors
     */
    @Test
    void crossProduct() {
        Vector vr = v1.crossProduct(v3);
        assertTrue(isZero(vr.length() - v1.length() * v3.length()));
        assertTrue(isZero(vr.dotProduct(v1)) || !isZero(vr.dotProduct(v3)));

    }
    @Test
    void ConstructorTest(){
        //===========================Equivalent Partitioning==================
        //TC1: zero point
        boolean isCorrect = false;
        try{
            Vector v = new Vector(new Point3D(0, 0, 0));
        }
        catch (Exception ex){
            isCorrect = true;
        }
        assertTrue(isCorrect);
        //TC2: creating vector by doubles

        Vector v = new Vector(1.5, 5.2, 7.1);
        assertTrue(v.toString().equals("Vector: end point: Point3D:(1.5,5.2,7.1)"));// tests toString() function too, no need for further tests of toString()

        //TC3: initialise vector by Point3D
        Vector v7 = new Vector(new Point3D(1.5, 5.2, 7.1));
        assertTrue(v7.toString().equals("Vector: end point: Point3D:(1.5,5.2,7.1)"));

        //TC4: initialise vector by vector

        Vector v8 = new Vector(v7);
        assertTrue(v8.equals(v7));

        //TC5: initialise vector by Coordinate

        Vector v11 = new Vector(new Coordinate(1.5), new Coordinate(5.2), new Coordinate(7.1));
        assertTrue(v11.equals(v7));


    }

    /**
     * Testing equals() method
     */
    @Test
    void equalsTest(){
        Vector a=new Vector(7, 15, 23);
        Vector b=new Vector(7, 15, 23);
        Vector c = new Vector(7, 15, 1);
        Vector d = new Vector(7, 1, 23);
        Vector e = new Vector(1, 15, 23);
        //================================Equivalent Partitioning====================
        //TC1: Vectors are equal
        assertTrue(a.equals(b));
        //TC2: Vectors are different only by one coordinate
        assertFalse(a.equals(c));
        assertFalse(a.equals(d));
        assertFalse(a.equals(e));
        //TC3: vectors are different and have none common coordinates
        assertFalse(a.equals(new Vector(-3, 0, 3.2)));
    }
    /**
     * getEnd() member function test
     */
    @Test
    void getEnd(){
        Point3D point  = new Point3D(1, 1, 0);
        Vector vec = new Vector (1, 1, 0);
        assertTrue(point.equals(vec.getEnd()));
    }



}