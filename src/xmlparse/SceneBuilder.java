package xmlparse;

import elements.Camera;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import primitives.Color;
import primitives.Point3D;
import primitives.Vector;
import scene.Scene;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.awt.*;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * a class for parsing an ml file with scene description to multiple scene objects
 * authors: Polina Frolov Korogodsky an Tselia Tebol
 * this video was used for studying DOM parsing method usage: https://www.youtube.com/watch?v=HfGWVy-eMRc
 * לא הספקנו לסיים את המחלקה הזו, אבל בעקרון אפשר לראות כי הPARSING עובד, אז נסיים אותו לקראת התרגיל הבא
 */
public class SceneBuilder{
    public List<Scene> build(String path) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newDefaultInstance();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(path);
            NodeList scenes = doc.getElementsByTagName("scene");
            List <Scene> scenesList  = new LinkedList<Scene>();
            for(int i = 0; i<scenes.getLength(); i++){
                Node s = scenes.item(i);
                if (s.getNodeType()==Node.ELEMENT_NODE){
                    Element scene = (Element)s;
                    Scene currentScene = new Scene("Scene"+i);
                    String background = scene.getAttribute("background-color");
                    String[] rgb = background.split(" ", 3);
                    int [] RGB = new int[3];
                    for (int k=0; k<3; k++){
                        try{
                            RGB[k]=Integer.parseInt(rgb[k]);
                        }
                        catch (Exception ex)
                        {
                            RGB[k]=0;
                        }
                    }
                    currentScene.set_background(new Color(RGB[0], RGB[1], RGB[2]));
                   // String screenWidth = scene.getAttribute("screen-width");
                    //String screenHeight = scene.getAttribute("screen-height");
                    //String screenDistance = scene.getAttribute("screen-distance");
                    NodeList sceneElements  = scene.getChildNodes();
                    for(int j = 0; j<sceneElements.getLength(); j++)
                    {
                        Node el = sceneElements.item(j);
                        if (el.getNodeType()==Node.ELEMENT_NODE){
                            Element camera = (Element) el;
                            //String  = element.getAttribute("image");
                           // String screenWidth = image.getAttribute("screen-width");
                           // String screenHeight = image.getAttribute("screen-height");
                           // String screenDistance = image.getAttribute("screen-distance");
                            String p0 = camera.getAttribute("P0");
                            String vTo = camera.getAttribute("Vto");
                            String vUp = camera.getAttribute("Vup");
                            String[] coordinates = p0.split(" ", 3);
                            int [] P0= new int[3];
                            for (int k=0; k<3; k++){
                                try{
                                    P0[k]=Integer.parseInt(coordinates[k]);
                                }
                                catch (Exception ex)
                                {
                                    //P0[k]=0;
                                    throw ex;
                                }
                            }
                            coordinates = vTo.split(" ", 3);
                            int [] VTo = new int[3];
                            for (int k=0; k<3; k++){
                                try{
                                    VTo[k]=Integer.parseInt(coordinates[k]);
                                }
                                catch (Exception ex)
                                {
                                    //P0[k]=0;
                                    throw ex;
                                }
                            }
                            coordinates = vUp.split(" ", 3);
                            int[] VUp = new int[3];
                            for (int k=0; k<3; k++){
                                try{
                                    VUp[k]=Integer.parseInt(coordinates[k]);
                                }
                                catch (Exception ex)
                                {
                                    //P0[k]=0;
                                    throw ex;
                                }
                            }
                            Camera cam = new Camera(new Point3D(P0[0], P0[1], P0[2]),
                                    new Vector(VUp[0], VUp[1], VUp[2]), new Vector(VTo[0], VTo[1], VTo[2]));
                            currentScene.set_camera(cam);



                        }
                    }
                    scenesList.add(currentScene);
                }

            }
            return scenesList;
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        catch (SAXException e){
            e.printStackTrace();
        }
        catch (IOException e){
            e.printStackTrace();
        }

    return null;}
}