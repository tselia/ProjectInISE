package unittests.unittests.xmlparse;

//import javafx.scene.Scene;
import org.junit.jupiter.api.Test;
import primitives.Point3D;
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
            assertEquals(Point3D.zero, scenes.get(0).get_camera().getP0());
            //System.out.println(scenes.get(0).get_camera().getP0());
        }
        catch (Exception ex){
            System.out.println("Exception" + ex.toString());
        }
    }

}