package unittests.renderer;

import elements.*;
import geometries.Polygon;
import geometries.Sphere;
import geometries.Triangle;
import primitives.Color;
import primitives.Material;
import primitives.Point3D;
import primitives.Vector;
import renderer.ImageWriter;
import renderer.Render;
import scene.Scene;
import org.junit.jupiter.api.Test;


public class PictureStep7 {
    @Test
      void printShip(){
        Scene scene = new Scene("Test scene");
        Vector move = new Vector(0, 15, -30);
        scene.setCamera(new Camera(new Point3D(0, 0, -1000), new Vector(0, -1, 0), new Vector(0, 0, 1)));
        scene.setDistance(1000);
        scene.setBackground(new Color(174, 214, 241));
        scene.setAmbientLight(new AmbientLight(new Color(255, 255, 255), 0.1));
        scene.addGeometries(
                new Polygon(new Color(245, 203, 167), new Material(0.5, 0.4, 3, 1, 0.1), new Point3D(-30, 0, 20).add(move), new Point3D(30, 0, 20).add(move), new Point3D(30, 0, 90).add(move), new Point3D(-30, 0, 90).add(move)),
                new Triangle(new Color(245, 203, 167), new Material(0.5, 0.4, 3, 1, 0.1), new Point3D(-30, 0, 20).add(move), new Point3D(-30, 0, 90).add(move), new Point3D(-60, 20, 90).add(move))
                //, new Sphere(new Color(245, 203, 167), new Material(0.5, 0.4, 3, 0, 0.1), 40, new Point3D(0, 0, 20))
        );
        scene.addLights(new SpotLight(new Color(1000, 600, 0), new Point3D(-100, 100, -500), new Vector(-1, 1, 2), 1,
                0.0004, 0.0000006)
       // , new DirectionalLight(new Color( 249, 231, 159 ), new Vector(1, 1, 1)),
        ,        new DirectionalLight(new Color( 249, 231, 159 ), new Vector(-1, -1, -1))
        //, new DirectionalLight(new Color( 249, 231, 159 ), new Vector(0, 0, 1))
        , new PointLight(new Color( 213, 245, 227 ), new Point3D(0, -20, 35), 0.9, 0.5, 0.3)
        );

        ImageWriter imageWriter = new ImageWriter("ship", 150, 150, 500, 500);
        Render render = new Render(imageWriter, scene);

        render.renderImage();
        render.writeToImage();
    }
    @Test
  void pictureStep7(){
      Scene scene = new Scene("Test scene");
      scene.setCamera(new Camera(new Point3D(0, 0, -1000), new Vector(0, -1, 0), new Vector(0, 0, 1)));
      scene.setDistance(3000);
      scene.setBackground(new Color(174, 214, 241/*Color.BLACK*/));
      Vector move = new Vector(0, 0, -300);
      Vector move2 = new Vector(0, -250, 0);
      scene.setAmbientLight(new AmbientLight(Color.BLACK, 0));

      scene.addGeometries(
              new Sphere(new Color( 255, 0, 0), new Material(0.4, 0.3, 100, 0.6, 0), 50,
                      new Point3D(0, 0, 50).add(move))
      , new Polygon(Color.BLACK, new Material(0.5, 0.4, 40, 0.2, 0), new Point3D(0, -50, -300).add((move2)), new Point3D(0, 50, -300).add(move2),  new Point3D(0, 50, -400).add(move2),new Point3D(0, -50, -400).add(move2))
      , new Sphere (new Color( 0, 0, 255/*74, 35, 90*/), new Material(0.7, 0.9, 48, 0.7, 0),  30, new Point3D(0, 50, 50).add(move))
      , new Sphere(new Color( 135, 54, 0 ), new Material(0.2, 0.8, 120, 0, 0.9), 40, new Point3D(30, 30, 55).add(move))
              , new Polygon(new Color(14, 98, 81), new Material(0.3, 0.2, 30, 0, 0.923), new Point3D(100, 75, 50).add(move2), /*new Point3D(90, 80, 50),*/ new Point3D(107, 208, 75).add(move2), new Point3D(25, 30, 50).add(move2)/*, *new Point3D(32.5, 4.5, 50).add(move2), new Point3D(97 , 60, 50)*/)
      , new Sphere(new Color(11, 83, 69), new Material(0.5, 0.4, 200, 0, 0.912),  15, new Point3D(-20, 80, -30).add(move))
      );

             // new Sphere(new Color(java.awt.Color.RED), new Material(0.5, 0.5, 100), 25, new Point3D(0, 0, 50)));

      scene.addLights(new SpotLight(new Color(1000, 600, 0), new Point3D(-100, 100, -500), new Vector(-1, 1, 2), 1,
              0.0004, 0.0000006));

      ImageWriter imageWriter = new ImageWriter("Step7.3.10", 1000, 1600, 500, 800);
      Render render = new Render(imageWriter, scene);

      render.renderImage();
      render.writeToImage();
    }
}
