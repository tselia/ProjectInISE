package unittests.unittests.xmlparse;

//import javafx.scene.Scene;
import elements.AmbientLight;
import elements.Camera;
import geometries.Intersectable;
import org.junit.jupiter.api.Test;
import primitives.Point3D;
import primitives.Vector;
import renderer.ImageWriter;
import renderer.Render;
import scene.Scene;
import xmlparse.ImageWriterBuilder;
import xmlparse.SceneBuilder;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for SceneBuilder and ImageWriterBuilder
 * * authors: Polina Frolov Korogodsky an Tselia Tebol
 */
class SceneBuilderTest {
    /**
     * The function that checks that the Camera was built correctly
     */
    @Test
    void testParse(){
        try {
           // SceneBuilder builder = new SceneBuilder("basicRenderTestTwoColors.xml");
            SceneBuilder builder = new SceneBuilder();
            List<Scene> scenes = builder.build("basicRenderTestTwoColors.xml");
            //assertEquals(scenes.get(0).get_camera(), new Camera(Point3D.zero, new Vector(0, -1, 0), new Vector(0, 0, 1)));
           assertEquals(Point3D.zero, scenes.get(0).getCamera().getP0());
           assertEquals(new Vector(0, -1, 0), scenes.get(0).getCamera().getvUp());
           assertEquals(new Vector(0, 0, 1), scenes.get(0).getCamera().getvTo());
            //System.out.println(scenes.get(0).get_camera().getP0());
        }
        catch (Exception ex){
            System.out.println("Exception" + ex.toString());
        }
    }

    /**
     * The function that prints all the geometries that were read from xml so that we can check the list
     */
    @Test
    void  testGeometryAdding(){
        SceneBuilder builder = new SceneBuilder();
        List<Scene> scenes = builder.build("basicRenderTestTwoColors.xml");
        List<Intersectable> geos = scenes.get(0).getGeometries().getElements();
        for(Intersectable g : geos)
            System.out.println(g);
        //System.out.println(scenes.get(0).get_geometries().toString());
    }

    /**
     * the function to check that the AmbientLight was read correctly
     */
    @Test
    void gettingAmbient(){
        SceneBuilder builder = new SceneBuilder();
        List<Scene> scenes = builder.build("basicRenderTestTwoColors.xml");
       assertEquals(scenes.get(0).getAmbientLight().getIntensity().getColor().getBlue(),
             new AmbientLight(new java.awt.Color(255, 191, 191), 1).getIntensity().getColor().getBlue());
        assertEquals(scenes.get(0).getAmbientLight().getIntensity().getColor().getGreen(),
                new AmbientLight(new java.awt.Color(255, 191, 191), 1).getIntensity().getColor().getGreen());
        assertEquals(scenes.get(0).getAmbientLight().getIntensity().getColor().getRed(),
                new AmbientLight(new java.awt.Color(255, 191, 191), 1).getIntensity().getColor().getRed());
    }

    /**
     * the function to check that background color was read correctly
     */
    @Test
    void background(){
        SceneBuilder builder = new SceneBuilder();
        List<Scene> scenes = builder.build("basicRenderTestTwoColors.xml");
        assertEquals(scenes.get(0).getBackground().getColor(), new java.awt.Color(75, 127, 190));

    }

    /**
     * the function to check that the picture was drawn correctly
     */
    @Test
    void drawPicture(){
        SceneBuilder builder = new SceneBuilder();
        Scene scene = builder.build("basicRenderTestTwoColors.xml").get(0);
        ImageWriterBuilder iBuilder = new ImageWriterBuilder();
        ImageWriter writer = iBuilder.buildImageWriters("basicRenderTestTwoColors.xml").get(0);
        Render render = new Render(scene, writer);
        render.renderImage();
        render.writeToImage();
    }
}