package unittests.primitiveTests;

import org.junit.jupiter.api.Test;
import renderer.ImageWriter;

import java.awt.*;

public class ColorTest {
    @Test
    void printGrids() {
        ImageWriter artist = new ImageWriter("Stripes", 1600, 1000, 800, 500);
        for (int i = 0; i < 500; i++) {
            for (int j = 0; j < 800; j++) {
                if (i<200)
                    artist.writePixel(j, i, new java.awt.Color(255, 228, 181));
                else if(i>300)
                artist.writePixel(j, i, new java.awt.Color(147, 112, 219));
                else artist.writePixel(j, i, primitives.Color.averageColor(new primitives.Color(new java.awt.Color(255, 228, 181)), new primitives.Color(new java.awt.Color(147, 112, 219))/*, new primitives.Color(new java.awt.Color(255, 228, 181))*/).getColor());
                //else artist.writePixel(j, i, primitives.Color.averageColor(new primitives.Color(new java.awt.Color(255, 228, 181)), new primitives.Color(new java.awt.Color(147, 112, 219)), primitives.Color.averageColor(new primitives.Color(new java.awt.Color(147, 112, 219)), new primitives.Color(new java.awt.Color(147, 112, 219)))).getColor());
            }
        }
        artist.writeToImage();

    }

}

