package xmlparse;

import elements.AmbientLight;
import elements.Camera;
import geometries.Plane;
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
import renderer.ImageWriter;
import scene.Scene;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.awt.*;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * a class for parsing an xml file with scene description to multiple scene objects - gets Scene objects from it only
 * authors: Polina Frolov Korogodsky an Tselia Tebol
 * this video was used for studying DOM parsing method usage: https://www.youtube.com/watch?v=HfGWVy-eMRc
 */
public class SceneBuilder{
    /**
     * The function for building a scene from xml file
     * @param path - path to the xml file
     * @return List<Scenes>
     */
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
                    currentScene.setBackground(new Color(RGB[0], RGB[1], RGB[2]));
                    String distance = scene.getAttribute("screen-distance");
                    try {
                       // double d = ;
                        currentScene.setDistance(Double.parseDouble(distance));
                    }
                    catch (Exception ex){
                        throw ex;
                    }

                    NodeList sceneElements  = scene.getChildNodes();
                    for(int j = 0; j<sceneElements.getLength(); j++)
                    {
                        Node el = sceneElements.item(j);
                        if (el.getNodeType()==Node.ELEMENT_NODE){
                            if (el.getNodeName().contains("camera")) {
                                Element camera = (Element) el;
                                String p0 = camera.getAttribute("P0");
                                String vTo = camera.getAttribute("Vto");
                                String vUp = camera.getAttribute("Vup");
                                String[] coordinates = p0.split(" ", 3);
                                Point3D centralPoint = getPointFromString(p0);
                                Point3D vTO = getPointFromString(vTo);
                                Point3D vUP = getPointFromString(vUp);
                                Camera cam = new Camera(centralPoint, new Vector(vUP), new Vector(vTO));
                                currentScene.setCamera(cam);
                            }
                            if (el.getNodeName().contains("ambient")){
                                Element ambient = (Element) el;
                                String color = ambient.getAttribute("color");
                                String [] colorSplitted = color.split(" ", 3);
                                for (int k=0; k<3; k++){
                                    try{
                                        RGB[k]=Integer.parseInt(colorSplitted[k]);
                                    }
                                    catch (Exception ex)
                                    {
                                        RGB[k]=0;
                                    }
                                }
                                currentScene.setAmbientLight(new AmbientLight(new java.awt.Color(RGB[0], RGB[1], RGB[2]), 1));


                            }
                            else if (el.getNodeName().contains("geometries")){
                                System.out.println("Entered");
                                Element geometries= (Element) el;
                                NodeList triangles = geometries.getElementsByTagName("triangle");
                                NodeList spheres = geometries.getElementsByTagName("sphere");
                                NodeList planes = geometries.getElementsByTagNameNS("plane", "plain");
                                if(planes==null&&spheres==null&&triangles==null){
                                    break;
                                }else
                                    {
                                    if (triangles!=null){ //adding all triangles if they exist in scene's description
                                        for(int v=0; v<triangles.getLength(); v++){
                                            Element tri = (Element)triangles.item(v);
                                            Point3D P0 = getPointFromString(tri.getAttribute("p0"));
                                            Point3D P1 = getPointFromString(tri.getAttribute("p1"));
                                            Point3D P2 = getPointFromString(tri.getAttribute("p2"));
                                            currentScene.addGeometries(new Triangle(P0, P1, P2));
                                        }
                                        if (spheres!= null){
                                            for(int v=0; v<spheres.getLength(); v++){
                                                Element sph = (Element)spheres.item(v);
                                                String rad = sph.getAttribute("radius");
                                                Point3D PZero = getPointFromString(sph.getAttribute("center"));
                                                try{
                                                double r = Double.parseDouble(rad);
                                                currentScene.addGeometries(new Sphere(r, PZero));
                                                }
                                                catch(Exception ex){};
                                            }
                                        }
                                        if(planes!=null){
                                            for(int v=0; v<planes.getLength(); v++){
                                                Element pl = (Element)planes.item(v);
                                                Point3D pt = getPointFromString(pl.getAttribute("point"));
                                                Vector norm = new Vector(getPointFromString(pl.getAttribute("normal")));
                                                currentScene.addGeometries(new Plane(pt, norm));
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
            System.out.println("ParserConfigurationException");
        }
        catch (SAXException e){
            e.printStackTrace();
            System.out.println("SAXException");
        }
        catch (IOException e){
            e.printStackTrace();
            System.out.println("IOException");
        }

    return null;}

    /**
     * The function that receives String that contains 3 combinations of numbers, splits it to three separate strings
     * then parses each one of them into number and returns Point3D with coordinates read from this strings
     * @param s (String)
     * @return Point3D
     */
    private Point3D getPointFromString(String s){
        String [] coordinates = s.split(" ", 3);
        double [] point = new double[3];
        for(int i=0; i<3; i++)
        {
            try{
                point[i]=Double.parseDouble(coordinates[i]);
            }
            catch (Exception ex){
                point[i]=0;
            }

        }
        return new Point3D(point[0], point[1], point[2]);
    }
}