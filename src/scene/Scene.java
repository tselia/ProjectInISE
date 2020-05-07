package scene;

import elements.AmbientLight;
import elements.Camera;
import elements.LightSource;
import geometries.Geometries;
import geometries.Geometry;
import geometries.Intersectable;
import primitives.Color;

import java.util.LinkedList;
import java.util.List;

/**
 *   Class Scene is a basic representation of union of things that define scene image
 *   authors Polina Frolov Korogodsky and Tselia Tebol
 */
public class Scene {
    private String _name;
    private Color _background;
    private AmbientLight _ambientLight;
    private Geometries _geometries;
    private Camera _camera;
    private double _distance;
    private List<LightSource> _lights;

    /**
     * The constructor receives name of the scene and initializes the
     * geometries list to be a new empty list
     * @param _name
     */
    public Scene(String _name) {
        this._name = _name;
        this._geometries=new Geometries();
        this._lights = new LinkedList<>();
    }

    /**
     * get method for _name field
     * @return String _name
     */
    public String getName() {
        return _name;
    }

    /**
     * get method for _background field
     * @return Color _background
     */
    public Color getBackground() {
        return _background;
    }

    /**
     * get method for _ambientLight field
     * @return AmbientLight _ambientLight
     */
    public AmbientLight getAmbientLight() {
        return _ambientLight;
    }

    /**
     * get method for _geometries field
     * @return Geometries _geometries
     */
    public Geometries getGeometries() {
        return _geometries;
    }

    /**
     * get method for _camera field
     * @return Camera _camera
     */
    public Camera getCamera() {
        return _camera;
    }

    /**
     * get method for _distance field
     * @return double distance
     */
    public double getDistance() {
        return _distance;
    }

    /**
     * set method for _background field
     * @param _background (Color)
     */
    public void setBackground(Color _background) {
        this._background = _background;
    }

    /**
     * set method for _ambientLight field
     * @param _ambientLight (AmbientLight)
     */

    public void setAmbientLight(AmbientLight _ambientLight) {
        this._ambientLight = _ambientLight;
    }

    /**
     * set method for _camera field
     * @param _camera (Camera)
     */

    public void setCamera(Camera _camera) {
        this._camera = _camera;
    }

    /**
     * set method for _distance field
     * @param _distance (double)
     */
    public void setDistance(double _distance) {
        this._distance = _distance;
    }

    /**
     * a method to add geometries to scene
     * @param geometries (Intersectable)
     */
    public void addGeometries(Intersectable ... geometries){

            this._geometries.add(geometries);

    }

    /**
     * A method to return the list of light sources
     * @return List<LightSource>
     */
    public List<LightSource> getLights() {
        return _lights;
    }

    /**
     * function to add some number of light sources to the scene
     * @param lights
     */
    public void addLights (LightSource... lights){
        for(LightSource light: lights)
            _lights.add(light);
    }
}
