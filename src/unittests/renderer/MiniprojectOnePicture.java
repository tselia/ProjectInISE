
/**
 * Mini project 1

 * GitHub repository: https://github.com/polinafr/ProjectInISE
 */
package unittests.renderer;

import elements.*;
import geometries.Sphere;
import geometries.Triangle;
import org.junit.jupiter.api.Test;
import primitives.Color;
import primitives.Material;
import primitives.Point3D;
import primitives.Vector;
import renderer.ImageWriter;
import renderer.Render;
import scene.Scene;

/**
 * Class MiniprojectOnePicture contains a function that makes a picture of Solar System
 * The level of Supersamling effect is defined by third parameter of Render constructor
 * If there are two parametres only the picture will not be supersampled
 * Final pictures' names contain word "Final"
 */
public class MiniprojectOnePicture {
    /**
     * function creates a picture of Solar System
     */
    @Test
    void spherePic1() {

        Scene scene = new Scene("Test scene");
        scene.setCamera(new Camera(new Point3D(50, 100, -1700), new Vector(0, -1, 0), new Vector(0, 0, 1)));
        scene.setDistance(1000);
        scene.setBackground(/*Color.BLACK);*/new Color(5,15,30));
        scene.setAmbientLight(new AmbientLight(Color.BLACK, 0));

       /* scene.addGeometries(
                new Sphere(new Color(255,200,40), new Material(0.5, 0.5, 100), 140, new Point3D(-780, 0, 50))
        , new Sphere(new Color(255,255,255), new Material(0.5, 0.5, 100), 215, new Point3D(-820, 0, 200))
        , new Sphere(Color.BLACK, new Material(0.5, 0.5, 100), 205, new Point3D(-790, 0, 150))
        ,  new Sphere(new Color(190,180,60), new Material(0.5, 0.5, 100), 50, new Point3D(-550, 0, 100)));
        */
        scene.addGeometries(
                new Sphere(new Color(255,200,40), new Material(0.5, 0.5, 300, 0.25, 0.05), 330, new Point3D(-755, -440, 100))

                // planets
                ,  new Sphere(new Color(90,110,125), new Material(0.5, 0.5, 100, 0, 0.01), 40, new Point3D(-410, -200, 20))
                ,  new Sphere(new Color(220,150,60), new Material(0.5, 0.5, 110, 0, 0.09), 50, new Point3D(-480, 10, 20))
                ,  new Sphere(new Color(60,150,220), new Material(0.5, 0.7, 100, 0, 0.5), 60, new Point3D(-220, 0, 20))


                ,  new Sphere(new Color(215,85,60), new Material(0.5, 0.5, 120, 0, 0.15), 50, new Point3D(-30, -150, 20))
                ,  new Sphere(new Color(225,175,105), new Material(0.5, 0.5, 100, 0, 0.4), 100, new Point3D(130, 0, 20))
                ,  new Sphere(new Color(143, 161, 102/*250,220,80*/), new Material(0.5, 0.5, 100, 0.3, 0), 90, new Point3D(290, 180, 20))
                ,  new Sphere(new Color(20,205,230), new Material(0.5, 0.5, 100, 0, 0.09), 55, new Point3D(440, -80, 20))
                ,  new Sphere(new Color(40,145,190), new Material(0.5, 0.3, 100, 0, 0.11), 55, new Point3D(590, 90, 20))

                ,  new Sphere(new Color(160,160,160), new Material(0.5, 0.5, 100, 0.1, 0.17), 20, new Point3D(-290, -30, 20))

                //star
                , new Triangle(new Color(255,255,255), new Material(0.8, 0.2, 300),
                        new Point3D(-645, 350, 20), new Point3D(-655, 360, 20), new Point3D(-665, 350, 20))
                , new Triangle(new Color(255,255,255), new Material(0.8, 0.2, 300),
                        new Point3D(-645, 350, 20), new Point3D(-655, 340, 20), new Point3D(-665, 350, 20))

                , new Triangle(new Color(255,255,255), new Material(0.8, 0.2, 300),
                        new Point3D(645, -350, 20), new Point3D(655, -360, 20), new Point3D(665, -350, 20))
                , new Triangle(new Color(255,255,255), new Material(0.8, 0.2, 300),
                        new Point3D(645, -350, 20), new Point3D(655, -340, 20), new Point3D(665, -350, 20))

                //1
                , new Sphere(new Color(255,255,255), new Material(0.5, 0.5, 100), 385, new Point3D(-815, -460, 200))
                , new Sphere(new Color(5,15,30), new Material(0.5, 0.5, 100), 375, new Point3D(-790, -450, 150))

                //2
                , new Sphere(new Color(255,255,255), new Material(0.5, 0.5, 100), 507, new Point3D(-920, -510, 800))
                , new Sphere(new Color(255,255,255), new Material(0.5, 0.5, 100), 508, new Point3D(-1030, -430, 800))
                , new Sphere(new Color(5,15,30), new Material(0, 0.5, 100), 513, new Point3D(-915, -510, 700))
                , new Sphere(new Color(5,15,30), new Material(0, 0.5, 100), 503, new Point3D(-1005, -440, 700))

                //3
                , new Sphere(new Color(255,255,255), new Material(0.5, 0.5, 100), 578, new Point3D(-910, -560, 1400))
                , new Sphere(new Color(255,255,255), new Material(0.5, 0.5, 100), 578, new Point3D(-880, -520, 1400))
                , new Sphere(new Color(255,255,255), new Material(0.5, 0.5, 100), 578, new Point3D(-950, -420, 1400))
                , new Sphere(new Color(255,255,255), new Material(0.5, 0.5, 100), 588, new Point3D(-1110, -350, 1400))
                , new Sphere(new Color(255,255,255), new Material(0.5, 0.5, 100), 580, new Point3D(-1230, -320, 1400))

                , new Sphere(new Color(5,15,30), new Material(0, 0.5, 100), 578, new Point3D(-885, -525, 1300))
                , new Sphere(new Color(5,15,30), new Material(0, 0.5, 100), 580, new Point3D(-955, -425, 1300))
                , new Sphere(new Color(5,15,30), new Material(0, 0.5, 100), 570, new Point3D(-1150, -345, 1300))
                , new Sphere(new Color(5,15,30), new Material(0, 0.5, 100), 570, new Point3D(-1100, -345, 1300))
                , new Sphere(new Color(5,15,30), new Material(0, 0.5, 100), 570, new Point3D(-1235, -325, 1300))

                //4
                , new Sphere(new Color(255,255,255), new Material(0.5, 0.5, 100), 570, new Point3D(-623, -660, 1930))
                , new Sphere(new Color(255,255,255), new Material(0.5, 0.5, 100), 570, new Point3D(-730, -380, 1930))
                , new Sphere(new Color(255,255,255), new Material(0.5, 0.5, 100), 570, new Point3D(-900, -220, 1930))
                , new Sphere(new Color(255,255,255), new Material(0.5, 0.5, 100), 570, new Point3D(-1180, -100, 1930))

                , new Sphere(new Color(5,15,30), new Material(0, 0.5, 100), 569, new Point3D(-630, -660, 1900))
                , new Sphere(new Color(5,15,30), new Material(0, 0.5, 100), 569, new Point3D(-734, -380, 1900))
                , new Sphere(new Color(5,15,30), new Material(0, 0.5, 100), 569, new Point3D(-900, -227, 1900))
                , new Sphere(new Color(5,15,30), new Material(0, 0.5, 100), 569, new Point3D(-1180, -108, 1900))

                //Saturn's rings
                // ,  new Sphere(new Color(255, 0, 0), new Material(0.5, 0.5, 100), 90, new Point3D(290, 180, 20))
                , new Sphere (new Color(238, 228, 219), new Material (0.3, 0.8, 50, 0, 0.0001), 10, new Point3D(290, 180, -100))
                , new Sphere (new Color(149, 97, 51), new Material (0.7, 0.8, 70, 0, 0.001), 7, new Point3D(320, 180, -110))
                , new Sphere (new Color(107, 97, 51), new Material (0.4, 0.1, 50, 0, 0.02), 13, new Point3D(354, 180, -90))
                , new Sphere (new Color(25, 74, 53), new Material (0.3, 0.2, 110, 0, 0.0003), 6, new Point3D(274, 180, -110))
                , new Sphere (new Color(172, 143, 76), new Material (0.7, 0.1, 85, 0, 0.001), 11, new Point3D(267, 170, -110))
                , new Sphere (new Color(78, 56, 3), new Material (0.4, 0.6, 78, 0, 0.0071), 5, new Point3D(247, 183, -110))
                , new Sphere (new Color(159, 148, 152), new Material (0.3, 0.5, 90, 0, 0.004), 9, new Point3D(230, 180, -110))
                , new Sphere (new Color(161, 78, 6), new Material (0.4, 0.8, 85, 0, 0.001), 7, new Point3D(215, 176, -110))
                , new Sphere (new Color(78, 38, 3), new Material (0.2, 0.8, 70, 0, 0.0009), 11, new Point3D(201, 165, -110))
                , new Sphere (new Color(84, 84, 84), new Material (0.14, 0.56, 115, 0, 0.0034), 8, new Point3D(182, 155, -110))
                , new Sphere (new Color(54, 78, 64), new Material (0.784, 0.1256, 130, 0, 0.1), 7, new Point3D(164, 148, -110))
                , new Sphere (new Color(58, 36, 3), new Material (0.2, 0.4, 130, 0, 0.3), 8, new Point3D(143, 140, -90))
                , new Sphere (new Color(230, 238, 233), new Material (0.7, 0.8, 110, 0, 0.4), 10, new Point3D(150, 135, -70))
                , new Sphere (new Color(236, 162, 50), new Material (0.7, 0.8, 70, 0, 0.001), 7, new Point3D(168, 130, -65))
                , new Sphere (new Color(236, 100, 50), new Material (0.3, 0.95, 190, 0, 0.01), 5, new Point3D(183, 128, -53))


                //, new Sphere(new Color(5,15,30), new Material(0, 0.5, 100), 555, new Point3D(-805, -430, 1300))
        );


        scene.addLights(new PointLight(new Color(255, 205, 100),/* new Vector(1, 1, 1),*/ new Point3D(-755, -440, 100), 0.3, 0.4, 100)
        , new DirectionalLight(new Color(255, 255, 100), new Vector(0.5, 1, -2)));




        ImageWriter imageWriter = new ImageWriter("SolarSystemTwoLightsNew", 800, 500, 1600, 1000);
        Render render = new Render(imageWriter, scene, 10);

        render.renderImage();
        render.writeToImage();
    }
    @Test
    void spherePic2() {

        Scene scene = new Scene("Test scene");
        scene.setCamera(new Camera(new Point3D(50, 100, -1700), new Vector(0, -1, 0), new Vector(0, 0, 1)));
        scene.setDistance(1000);
        scene.setBackground(/*Color.BLACK);*/new Color(5,15,30));
        scene.setAmbientLight(new AmbientLight(Color.BLACK, 0));

       /* scene.addGeometries(
                new Sphere(new Color(255,200,40), new Material(0.5, 0.5, 100), 140, new Point3D(-780, 0, 50))
        , new Sphere(new Color(255,255,255), new Material(0.5, 0.5, 100), 215, new Point3D(-820, 0, 200))
        , new Sphere(Color.BLACK, new Material(0.5, 0.5, 100), 205, new Point3D(-790, 0, 150))
        ,  new Sphere(new Color(190,180,60), new Material(0.5, 0.5, 100), 50, new Point3D(-550, 0, 100)));
        */
        scene.addGeometries(
                new Sphere(new Color(255,200,40), new Material(0.5, 0.5, 300, 0.25, 0.05), 330, new Point3D(-755, -440, 100))

                // planets
                ,  new Sphere(new Color(90,110,125), new Material(0.5, 0.5, 100, 0, 0.01), 40, new Point3D(-410, -200, 20))
                ,  new Sphere(new Color(220,150,60), new Material(0.5, 0.5, 110, 0, 0.09), 50, new Point3D(-480, 10, 20))
                ,  new Sphere(new Color(60,150,220), new Material(0.5, 0.7, 100, 0, 0.5), 60, new Point3D(-220, 0, 20))


                ,  new Sphere(new Color(215,85,60), new Material(0.5, 0.5, 120, 0, 0.15), 50, new Point3D(-30, -150, 20))
                ,  new Sphere(new Color(225,175,105), new Material(0.5, 0.5, 100, 0, 0.4), 100, new Point3D(130, 0, 20))
                ,  new Sphere(new Color(143, 161, 102/*250,220,80*/), new Material(0.5, 0.5, 100, 0.3, 0), 90, new Point3D(290, 180, 20))
                ,  new Sphere(new Color(20,205,230), new Material(0.5, 0.5, 100, 0, 0.09), 55, new Point3D(440, -80, 20))
                ,  new Sphere(new Color(40,145,190), new Material(0.5, 0.3, 100, 0, 0.11), 55, new Point3D(590, 90, 20))

                ,  new Sphere(new Color(160,160,160), new Material(0.5, 0.5, 100, 0.1, 0.17), 20, new Point3D(-290, -30, 20))

                //star
                , new Triangle(new Color(255,255,255), new Material(0.8, 0.2, 300),
                        new Point3D(-645, 350, 20), new Point3D(-655, 360, 20), new Point3D(-665, 350, 20))
                , new Triangle(new Color(255,255,255), new Material(0.8, 0.2, 300),
                        new Point3D(-645, 350, 20), new Point3D(-655, 340, 20), new Point3D(-665, 350, 20))

                , new Triangle(new Color(255,255,255), new Material(0.8, 0.2, 300),
                        new Point3D(645, -350, 20), new Point3D(655, -360, 20), new Point3D(665, -350, 20))
                , new Triangle(new Color(255,255,255), new Material(0.8, 0.2, 300),
                        new Point3D(645, -350, 20), new Point3D(655, -340, 20), new Point3D(665, -350, 20))

                //1
                , new Sphere(new Color(255,255,255), new Material(0.5, 0.5, 100), 385, new Point3D(-815, -460, 200))
                , new Sphere(new Color(5,15,30), new Material(0.5, 0.5, 100), 375, new Point3D(-790, -450, 150))

                //2
                , new Sphere(new Color(255,255,255), new Material(0.5, 0.5, 100), 507, new Point3D(-920, -510, 800))
                , new Sphere(new Color(255,255,255), new Material(0.5, 0.5, 100), 508, new Point3D(-1030, -430, 800))
                , new Sphere(new Color(5,15,30), new Material(0, 0.5, 100), 513, new Point3D(-915, -510, 700))
                , new Sphere(new Color(5,15,30), new Material(0, 0.5, 100), 503, new Point3D(-1005, -440, 700))

                //3
                , new Sphere(new Color(255,255,255), new Material(0.5, 0.5, 100), 578, new Point3D(-910, -560, 1400))
                , new Sphere(new Color(255,255,255), new Material(0.5, 0.5, 100), 578, new Point3D(-880, -520, 1400))
                , new Sphere(new Color(255,255,255), new Material(0.5, 0.5, 100), 578, new Point3D(-950, -420, 1400))
                , new Sphere(new Color(255,255,255), new Material(0.5, 0.5, 100), 588, new Point3D(-1110, -350, 1400))
                , new Sphere(new Color(255,255,255), new Material(0.5, 0.5, 100), 580, new Point3D(-1230, -320, 1400))

                , new Sphere(new Color(5,15,30), new Material(0, 0.5, 100), 578, new Point3D(-885, -525, 1300))
                , new Sphere(new Color(5,15,30), new Material(0, 0.5, 100), 580, new Point3D(-955, -425, 1300))
                , new Sphere(new Color(5,15,30), new Material(0, 0.5, 100), 570, new Point3D(-1150, -345, 1300))
                , new Sphere(new Color(5,15,30), new Material(0, 0.5, 100), 570, new Point3D(-1100, -345, 1300))
                , new Sphere(new Color(5,15,30), new Material(0, 0.5, 100), 570, new Point3D(-1235, -325, 1300))

                //4
                , new Sphere(new Color(255,255,255), new Material(0.5, 0.5, 100), 570, new Point3D(-623, -660, 1930))
                , new Sphere(new Color(255,255,255), new Material(0.5, 0.5, 100), 570, new Point3D(-730, -380, 1930))
                , new Sphere(new Color(255,255,255), new Material(0.5, 0.5, 100), 570, new Point3D(-900, -220, 1930))
                , new Sphere(new Color(255,255,255), new Material(0.5, 0.5, 100), 570, new Point3D(-1180, -100, 1930))

                , new Sphere(new Color(5,15,30), new Material(0, 0.5, 100), 569, new Point3D(-630, -660, 1900))
                , new Sphere(new Color(5,15,30), new Material(0, 0.5, 100), 569, new Point3D(-734, -380, 1900))
                , new Sphere(new Color(5,15,30), new Material(0, 0.5, 100), 569, new Point3D(-900, -227, 1900))
                , new Sphere(new Color(5,15,30), new Material(0, 0.5, 100), 569, new Point3D(-1180, -108, 1900))

                //Saturn's rings
                // ,  new Sphere(new Color(255, 0, 0), new Material(0.5, 0.5, 100), 90, new Point3D(290, 180, 20))
                , new Sphere (new Color(238, 228, 219), new Material (0.3, 0.8, 50, 0, 0.0001), 10, new Point3D(290, 180, -100))
                , new Sphere (new Color(149, 97, 51), new Material (0.7, 0.8, 70, 0, 0.001), 7, new Point3D(320, 180, -110))
                , new Sphere (new Color(107, 97, 51), new Material (0.4, 0.1, 50, 0, 0.02), 13, new Point3D(354, 180, -90))
                , new Sphere (new Color(25, 74, 53), new Material (0.3, 0.2, 110, 0, 0.0003), 6, new Point3D(274, 180, -110))
                , new Sphere (new Color(172, 143, 76), new Material (0.7, 0.1, 85, 0, 0.001), 11, new Point3D(267, 170, -110))
                , new Sphere (new Color(78, 56, 3), new Material (0.4, 0.6, 78, 0, 0.0071), 5, new Point3D(247, 183, -110))
                , new Sphere (new Color(159, 148, 152), new Material (0.3, 0.5, 90, 0, 0.004), 9, new Point3D(230, 180, -110))
                , new Sphere (new Color(161, 78, 6), new Material (0.4, 0.8, 85, 0, 0.001), 7, new Point3D(215, 176, -110))
                , new Sphere (new Color(78, 38, 3), new Material (0.2, 0.8, 70, 0, 0.0009), 11, new Point3D(201, 165, -110))
                , new Sphere (new Color(84, 84, 84), new Material (0.14, 0.56, 115, 0, 0.0034), 8, new Point3D(182, 155, -110))
                , new Sphere (new Color(54, 78, 64), new Material (0.784, 0.1256, 130, 0, 0.1), 7, new Point3D(164, 148, -110))
                , new Sphere (new Color(58, 36, 3), new Material (0.2, 0.4, 130, 0, 0.3), 8, new Point3D(143, 140, -90))
                , new Sphere (new Color(230, 238, 233), new Material (0.7, 0.8, 110, 0, 0.4), 10, new Point3D(150, 135, -70))
                , new Sphere (new Color(236, 162, 50), new Material (0.7, 0.8, 70, 0, 0.001), 7, new Point3D(168, 130, -65))
                , new Sphere (new Color(236, 100, 50), new Material (0.3, 0.95, 190, 0, 0.01), 5, new Point3D(183, 128, -53))


                //, new Sphere(new Color(5,15,30), new Material(0, 0.5, 100), 555, new Point3D(-805, -430, 1300))
        );


        scene.addLights(new DirectionalLight(new Color(255, 205, 100), new Vector(1, 1, 1)));




        ImageWriter imageWriter = new ImageWriter("SolarSystemNonSupersampled", 800, 500, 1600, 1000); //14 s 620 ms without threads
        Render render = new Render(imageWriter, scene);

        render.renderImage();
        render.writeToImage();
    }
}
