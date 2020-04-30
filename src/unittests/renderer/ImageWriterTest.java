package unittests.renderer;
import org.junit.jupiter.api.Test;
import primitives.Color;
import renderer.ImageWriter;

import static org.junit.jupiter.api.Assertions.*;

/**
 * ImageWriterTest is a class for testing ImageWriter's methods
 * authors Polina Frolov Korogodsky and Tselia Tebol
 */
class ImageWriterTest {
    /**
     * prints 16x10 squares of mediumpurple color separated by moccasin color straight lines
     */
    @Test
    void printGrids() {
        ImageWriter artist = new ImageWriter("grids", 1600, 1000, 800, 500);
        for (int i = 0; i < 500; i++) {
            for (int j = 0; j < 800; j++) {
                if (j % 50 == 0 || i % 50 == 0)
                    artist.writePixel(j, i, new java.awt.Color(255, 228, 181));
                else artist.writePixel(j, i, new java.awt.Color(147, 112, 219));
            }
        }
        artist.writeToImage();

    }

}