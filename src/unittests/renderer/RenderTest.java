package unittests.renderer;

import org.junit.jupiter.api.Test;

import elements.*;
import geometries.*;
import primitives.*;
import renderer.ImageWriter;
import renderer.Render;
import scene.Scene;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test rendering abasic image
 *
 * @author Dan
 */
public class RenderTest {

    /**
     * Produce a scene with basic 3D model and render it into a jpeg image with a
     * grid
     */
    @Test
    public void basicRenderTwoColorTest() {
        Scene scene = new Scene("Test scene");
        scene.setCamera(new Camera(Point3D.zero, new Vector(0, -1, 0), new Vector(0, 0, 1)));
        scene.setDistance(100);
        scene.setBackground(new Color(75, 127, 90));
        scene.setAmbientLight(new AmbientLight(new Color(255, 191, 191), 1));

        scene.addGeometries(new Sphere(50, new Point3D(0, 0, 100)));

        scene.addGeometries(
                new Triangle(new Point3D(100, 0, 100), new Point3D(0, 100, 100), new Point3D(100, 100, 100)),
                new Triangle(new Point3D(100, 0, 100), new Point3D(0, -100, 100), new Point3D(100, -100, 100)),
                new Triangle(new Point3D(-100, 0, 100), new Point3D(0, 100, 100), new Point3D(-100, 100, 100)),
                new Triangle(new Point3D(-100, 0, 100), new Point3D(0, -100, 100), new Point3D(-100, -100, 100)));

        ImageWriter imageWriter = new ImageWriter("base render test", 500, 500, 500, 500);
        Render render = new Render(imageWriter, scene);

        render.renderImage();
        render.printGrid(50, java.awt.Color.YELLOW);
        render.writeToImage();
    }

    @Test
    public void basicRenderMultiColorTest() {
        Scene scene = new Scene("Test scene");
        scene.setCamera(new Camera(Point3D.zero,  new Vector(0, -1, 0), new Vector(0, 0, 1)));
        scene.setDistance(100);
        scene.setBackground(Color.BLACK);
        scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.2));

        scene.addGeometries(new Sphere(50, new Point3D(0, 0, 100)));
        scene.addLights(new PointLight(new Color(255, 153, 51), new Point3D(-5, 98, 95), 0.1, 0.05, 0.05));
        scene.addGeometries(
                new Triangle(new Color(java.awt.Color.BLUE), new Material(0.5, 0.5, 3),
                        new Point3D(100, 0, 100), new Point3D(0, 100, 100), new Point3D(100, 100, 100)),      // lower right
                new Triangle(
                        new Point3D(100, 0, 100), new Point3D(0, -100, 100), new Point3D(100, -100, 100)),    // upper right
                new Triangle(new Color(java.awt.Color.RED),
                        new Point3D(-100, 0, 100), new Point3D(0, 100, 100), new Point3D(-100, 100, 100)),    // lower left
                new Triangle(new Color(java.awt.Color.GREEN),
                        new Point3D(-100, 0, 100), new Point3D(0, -100, 100), new Point3D(-100, -100, 100))); // upper left

        ImageWriter imageWriter = new ImageWriter("color render test + point", 500, 500, 500, 500);
        Render render = new Render(imageWriter, scene);

        render.renderImage();
        render.printGrid(50, java.awt.Color.WHITE);
        render.writeToImage();
    }

    @Test
    void averageColor(){
        Color truth = new Color(79, 178, 154);
        List<java.awt.Color> clrs = new ArrayList<>();
        clrs.add(new java.awt.Color(15, 228, 176));
        clrs.add(new java.awt.Color(10, 28, 197));
        clrs.add(new java.awt.Color(151, 201, 7));
        clrs.add(new java.awt.Color(140, 255, 236));

        //Color result = Render.averageColor(clrs);
        //assertEquals(truth.getColor().getRed(), result.getColor().getRed(), "Red error");//3
        //assertEquals(truth.getColor().getGreen(), result.getColor().getGreen(), "Green Error");//57
        //assertEquals(truth.getColor().getBlue(), result.getColor().getBlue(), "Blue error");//44
    }
}
