package unittests.unittests.xmlparse;

//import javafx.scene.Scene;
import elements.AmbientLight;
import elements.Camera;
import geometries.Intersectable;
import org.junit.jupiter.api.Test;
import primitives.Point3D;
import primitives.Vector;
import scene.Scene;
import xmlparse.SceneBuilder;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SceneBuilderTest {
    @Test
    void testParse(){
        try {
           // SceneBuilder builder = new SceneBuilder("basicRenderTestTwoColors.xml");
            SceneBuilder builder = new SceneBuilder();
            List<Scene> scenes = builder.build("basicRenderTestTwoColors.xml");
            assertEquals(scenes.get(0).get_camera(), new Camera(Point3D.zero, new Vector(0, -1, 0), new Vector(0, 0, 1)));
            //System.out.println(scenes.get(0).get_camera().getP0());
        }
        catch (Exception ex){
            System.out.println("Exception" + ex.toString());
        }
    }
    @Test
    void  testGeometryAdding(){
        SceneBuilder builder = new SceneBuilder();
        List<Scene> scenes = builder.build("basicRenderTestTwoColors.xml");
        List<Intersectable> geos = scenes.get(0).get_geometries().getElements();
        for(Intersectable g : geos)
            System.out.println(g);
        //System.out.println(scenes.get(0).get_geometries().toString());
    }

    @Test
    void gettingAmbient(){
        SceneBuilder builder = new SceneBuilder();
        List<Scene> scenes = builder.build("basicRenderTestTwoColors.xml");
        assertEquals(scenes.get(0).get_ambientLight(), new AmbientLight(new java.awt.Color(255, 191, 191), 1));
    }
    @Test
    void background(){
        SceneBuilder builder = new SceneBuilder();
        List<Scene> scenes = builder.build("basicRenderTestTwoColors.xml");
        assertEquals(scenes.get(0).get_background().getColor(), new java.awt.Color(75, 127, 190));

    }
}