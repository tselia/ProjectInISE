package xmlparse;

import elements.AmbientLight;
import elements.Camera;
import geometries.Sphere;
import geometries.Triangle;
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
                    NodeList sceneElements  = scene.getChildNodes();
                    for(int j = 0; j<sceneElements.getLength(); j++)
                    {
                      //Zahodit  System.out.println(j);
                        Node el = sceneElements.item(j);
                        System.out.println(el.toString());
                        if (el.getNodeType()==3){
                            if (el.getTextContent().contains("camera")) {
                                System.out.println("Camera");
                                Element camera = (Element) el;
                                String p0 = camera.getAttribute("P0");
                                String vTo = camera.getAttribute("Vto");
                                String vUp = camera.getAttribute("Vup");
                                String[] coordinates = p0.split(" ", 3);
                                double[] P0 = new double[3];
                                for (int k = 0; k < 3; k++) {
                                    try {
                                        P0[k] = Double.parseDouble(coordinates[k]);
                                    } catch (Exception ex) {
                                        //P0[k]=0;
                                        throw ex;
                                    }
                                }
                                coordinates = vTo.split(" ", 3);
                                double[] VTo = new double[3];
                                for (int k = 0; k < 3; k++) {
                                    try {
                                        VTo[k] = Double.parseDouble(coordinates[k]);
                                    } catch (Exception ex) {
                                        //P0[k]=0;
                                        throw ex;
                                    }
                                }
                                coordinates = vUp.split(" ", 3);
                                double[] VUp = new double[3];
                                for (int k = 0; k < 3; k++) {
                                    try {
                                        VUp[k] = Double.parseDouble(coordinates[k]);
                                    } catch (Exception ex) {
                                        //P0[k]=0;
                                        throw ex;
                                    }
                                }
                                Camera cam = new Camera(new Point3D(P0[0], P0[1], P0[2]),
                                        new Vector(VUp[0], VUp[1], VUp[2]), new Vector(VTo[0], VTo[1], VTo[2]));
                                currentScene.set_camera(cam);
                            }
                            if (el.getTextContent().contains("ambient")){
                                System.out.println("Ambient");
                                Element ambient = (Element) el;
                                String color = ambient.getAttribute("color");
                                String [] colorSplitted = color.split(" ", 3);
                                for (int k=0; k<3; k++){
                                    try{
                                        RGB[k]=Integer.parseInt(rgb[k]);
                                    }
                                    catch (Exception ex)
                                    {
                                        RGB[k]=0;
                                    }
                                }
                                currentScene.set_ambientLight(new AmbientLight(new java.awt.Color(RGB[0], RGB[1], RGB[2]), 1));


                            }
                            else if (el.getTextContent().contains("geometries")){
                                //NodeList geometries = el.getChildNodes();
                                System.out.println("Entered");
                                Element geometries= (Element) el;
                                NodeList triangles = geometries.getElementsByTagName("triangle");
                                NodeList spheres = geometries.getElementsByTagName("sphere");
                               // NodeList polygons = geometries.getElementsByTagName("polygon");
                                NodeList cylinders = geometries.getElementsByTagName("cylinder");
                                NodeList planes = geometries.getElementsByTagNameNS("plane", "plain");
                                if(planes==null&&cylinders==null&&/*polygons==null&&*/spheres==null&&triangles==null){
                                    break;
                                }else
                                    {
                                    if (triangles!=null){ //adding all triangles if they exist in scene's description
                                        for(int v=0; v<triangles.getLength(); v++){
                                            Element tri = (Element)triangles.item(v);
                                            String P0 = tri.getAttribute("p0");
                                            String P1 = tri.getAttribute("p1");
                                            String P2 = tri.getAttribute("p2");
                                            String [] coordinates = P0.split(" ", 3);
                                            double [] p0 = new double[3];
                                            for (int k = 0; k < 3; k++) {
                                                try {
                                                    p0[k] = Double.parseDouble(coordinates[k]);
                                                } catch (Exception ex) {
                                                    //P0[k]=0;
                                                    throw ex;
                                                }
                                            }
                                            Point3D PZero = new Point3D(p0[0], p0[1], p0[2]);
                                            coordinates = P1.split(" ", 3);
                                            //int[] p0 = new int[3];
                                            for (int k = 0; k < 3; k++) {
                                                try {
                                                    p0[k] = Double.parseDouble(coordinates[k]);//Integer.parseInt(coordinates[k]);
                                                } catch (Exception ex) {
                                                    //P0[k]=0;
                                                    throw ex;
                                                }
                                            }
                                            Point3D POne = new Point3D(p0[0], p0[1], p0[2]);
                                            coordinates = P2.split(" ", 3);
                                            //p0 = new int[3];
                                            for (int k = 0; k < 3; k++) {
                                                try {
                                                    p0[k] = Double.parseDouble(coordinates[k]);//Integer.parseInt(coordinates[k]);
                                                } catch (Exception ex) {
                                                    //P0[k]=0;
                                                    throw ex;
                                                }
                                            }
                                            Point3D PTwo = new Point3D(p0[0], p0[1], p0[2]);
                                            currentScene.addGeometries(new Triangle(PZero, POne, PTwo));
                                        }
                                        if (spheres!= null){
                                            for(int v=0; v<spheres.getLength(); v++){
                                                Element sph = (Element)spheres.item(v);
                                                String P0 = sph.getAttribute("center");
                                                String rad = sph.getAttribute("radius");
                                                String [] coordinates = P0.split(" ", 3);
                                                double [] p0 = new double[3];
                                                for (int k = 0; k < 3; k++) {
                                                    try {
                                                        p0[k] = Double.parseDouble(coordinates[k]);
                                                    } catch (Exception ex) {
                                                        //P0[k]=0;
                                                        throw ex;
                                                    }
                                                }
                                                Point3D PZero = new Point3D(p0[0], p0[1], p0[2]);

                                                try{
                                                double r = Double.parseDouble(rad);
                                                currentScene.addGeometries(new Sphere(r, PZero));
                                                }
                                                catch(Exception ex){};
                                            }
                                        }
                                    }
                                    }
                            }



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