package xmlparse;

import org.junit.jupiter.api.extension.ExtensionContextException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import renderer.ImageWriter;
import scene.Scene;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
/**
 * a class for parsing an xml file with scene description to multiple scene objects - gets only ImageWriters from it
 * authors: Polina Frolov Korogodsky an Tselia Tebol
 * this video was used for studying DOM parsing method usage: https://www.youtube.com/watch?v=HfGWVy-eMRc
 */
public class ImageWriterBuilder {
    /**
     * The function that builds ImageWriters List from xml file
     * @param path
     * @return List<ImageWriter>
     */
    public List<ImageWriter> buildImageWriters(String path){
        DocumentBuilderFactory factory = DocumentBuilderFactory.newDefaultInstance();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(path);
            NodeList scenes = doc.getElementsByTagName("scene");
            List <ImageWriter> writers  = new LinkedList<ImageWriter>();
            for(int i = 0; i<scenes.getLength(); i++) {
                Node s = scenes.item(i);
                if (s.getNodeType() == Node.ELEMENT_NODE) {
                    Element scene = (Element) s;
                    //String width = scene.getAttribute("screen-width");
                    //String height = scene.getAttribute("screen-height");
                    String distance = scene.getAttribute("screen-distance");
                    try {
                        double d = Double.parseDouble(distance);
                    }
                    catch (Exception ex){
                        throw ex;
                    }
                    NodeList sceneElements  = scene.getChildNodes();
                    boolean flag = false;
                    for(int j = 0; j<sceneElements.getLength(); j++)
                    {
                        Node el = sceneElements.item(j);
                        if (el.getNodeType()==Node.ELEMENT_NODE) {
                            if (el.getNodeName().contains("image")) {
                                if(!flag) {
                                    flag = true;
                                    Element image = (Element) el;
                                    String imageWidth = image.getAttribute("screen-width");
                                    String imageHeight = image.getAttribute("screen-height");
                                    String nX = image.getAttribute("Nx");
                                    String nY = image.getAttribute("Ny");
                                    int width, height, NX, NY;
                                    try {
                                       width = Integer.parseInt(imageWidth);
                                    }
                                    catch (Exception ex){
                                        throw new ParserConfigurationException ("Width can not be parsed");
                                    }
                                    try {
                                        height = Integer.parseInt(imageHeight);
                                    }
                                    catch (Exception ex){
                                        throw new ParserConfigurationException ("Height can not be parsed");
                                    }
                                    try {
                                        NX = Integer.parseInt(nX);
                                    }
                                    catch (Exception ex){
                                        throw new ParserConfigurationException ("NX can not be parsed");
                                    }
                                    try {
                                       NY = Integer.parseInt(nY);
                                    }
                                    catch (Exception ex){
                                        throw new ParserConfigurationException ("NY can not be parsed");
                                    }
                                    writers.add(new ImageWriter("Scene"+i, width, height, NX, NY));
                                }
                                else {
                                    throw new ExtensionContextException ("More then one image defined");
                                }
                            }
                        }
                            }
                }
            }
            return writers;
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
        return null;
    }
}
