package unittests;

import org.junit.jupiter.api.Test;
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

    @Test
    void lengthSquared() {
        assertTrue(isZero(v1.lengthSquared() - 14));
    }

    @Test
    void length() {
        isZero(new Vector(0, 3, 4).length() - 5);
    }

    @Test
    void add() {
        // ============ Equivalence Partitions Tests ==============
        assertTrue(v1.add(new Vector (1, 1, 1)).equals(new Vector(2, 3, 4)));
        // ============ Equivalence Partitions Tests ==============
        assertTrue(v1.add(new Vector (-1, -1, -1)).equals(new Vector(0, 1, 2)));
    }

    @Test
    void subtract() {
        // ============ Equivalence Partitions Tests ==============
        assertTrue(v1.subtract(new Vector (1, 1, 1)).equals(new Vector(0, 1, 2)));
        // ============ Equivalence Partitions Tests ==============
        //System.out.println(v1.subtract(new Vector (-1, -1, -1)));
        assertTrue(v1.subtract(new Vector (-1, -1, -1)).equals(new Vector(2, 3, 4)));
    }

    @Test
    void dotProduct() {
        // ============ Equivalence Partitions Tests ==============
        assertTrue(v1.dotProduct(v2)==-28);
        // ============ Equivalence Partitions Tests ==============
        assertTrue(v1.dotProduct(v3)==0);
    }

    @Test
    void crossProduct() {
        Vector vr = v1.crossProduct(v3);
        assertTrue(isZero(vr.length() - v1.length() * v3.length()));
        assertTrue(isZero(vr.dotProduct(v1)) || !isZero(vr.dotProduct(v3)));

    }
}