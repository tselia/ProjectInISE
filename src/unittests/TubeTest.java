package unittests;
import geometries.*;
import primitives.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TubeTest {
Tube tube1 = new Tube (1, new Ray(new Point3D (0, 0, 0), new Vector (0, 0, 1)));// simple vertical tube
    Tube tube2 = new Tube (3, new Ray (new Point3D(3, 4, 0), new Vector(1, 1, 1)));// not-straight tube
    @Test
    void getNormal() {
        //====================Equivalent Partitioning===========================
        assertTrue(tube1.getNormal(new Point3D(1, 0, 3)).equals(new Vector(1, 0, 0)));
        //====================Equivalent Partitioning===========================
        boolean isCorrect = false;
        try{
            tube1.getNormal(new Point3D(0, 0.5, 8));
        }
        catch(Exception ex){
            isCorrect = true;
        }
        assertTrue(isCorrect);
    }
}