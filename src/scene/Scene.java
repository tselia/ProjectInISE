package scene;

import elements.AmbientLight;
import elements.Camera;
import geometries.Geometries;
import geometries.Geometry;
import geometries.Intersectable;
import primitives.Color;

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

    /**
     * The constructor receives name of the scene and initializes the
     * geometries list to be a new empty list
     * @param _name
     */
    public Scene(String _name) {
        this._name = _name;
        this._geometries=new Geometries();
    }

    /**
     * get method for _name field
     * @return String _name
     */
    public String get_name() {
        return _name;
    }

    /**
     * get method for _background field
     * @return Color _background
     */
    public Color get_background() {
        return _background;
    }

    /**
     * get method for _ambientLight field
     * @return AmbientLight _ambientLight
     */
    public AmbientLight get_ambientLight() {
        return _ambientLight;
    }

    /**
     * get method for _geometries field
     * @return Geometries _geometries
     */
    public Geometries get_geometries() {
        return _geometries;
    }

    /**
     * get method for _camera field
     * @return Camera _camera
     */
    public Camera get_camera() {
        return _camera;
    }

    /**
     * get method for _distance field
     * @return double distance
     */
    public double get_distance() {
        return _distance;
    }

    /**
     * set method for _background field
     * @param _background (Color)
     */
    public void set_background(Color _background) {
        this._background = _background;
    }

    /**
     * set method for _ambientLight field
     * @param _ambientLight (AmbientLight)
     */

    public void set_ambientLight(AmbientLight _ambientLight) {
        this._ambientLight = _ambientLight;
    }

    /**
     * set method for _camera field
     * @param _camera (Camera)
     */

    public void set_camera(Camera _camera) {
        this._camera = _camera;
    }

    /**
     * set method for _distance field
     * @param _distance (double)
     */
    public void set_distance(double _distance) {
        this._distance = _distance;
    }

    /**
     * a method to add geometries to scene
     * @param geometries (Intersectable)
     */
    public void addGeometries(Intersectable ... geometries){
        //for(Intersectable geo: geometries){
            this._geometries.add(geometries);

    }
}
