package renderer;

import elements.Camera;
import geometries.Intersectable;
import primitives.Color;
import primitives.Point3D;
import primitives.Ray;
import scene.Scene;

import java.util.ArrayList;
import java.util.List;

/**
 * The Render class is a union of Scene and ImageWriter so that the specific scene could be printed  as picture
 * authors Polina Frolov Korogodsky and Tselia Tebol
 */

public class Render {
    private Scene scene;
    private  ImageWriter imageWriter ;

    /**
     *Constructor
     * @param scene (Scene)
     * @param imageWriter (ImageWriter)
     */
    public Render(Scene scene, ImageWriter imageWriter) {
        this.scene = scene;
        this.imageWriter = imageWriter;
    }

    /**
     * Constructor
     * @param imageWriter
     * @param scene
     */
    public Render(ImageWriter imageWriter, Scene scene) {
        this.scene = scene;
        this.imageWriter = imageWriter;
    }
    /**
     * The function that saves the 3D scene's 2D representation in matrix
     */
    public void renderImage() {
        java.awt.Color background = scene.getBackground().getColor();
        Camera camera= scene.getCamera();
        Intersectable geometries = scene.getGeometries();
        double  distance = scene.getDistance();
        Ray ray;
        // number of pixels in the rows of the view plane
        int width = (int) imageWriter.getWidth();
        // number of pixels in the columns of the view plane
        int height = (int) imageWriter.getHeight();

        //  width of the image.
        int Nx = imageWriter.getNx();
        // height of the image
        int Ny = imageWriter.getNy();

        for (int row = 0; row < Ny; row++) {
            for (int column = 0; column < Nx; column++) {
                ray = camera.constructRayThroughPixel(Nx, Ny, row, column, distance, width, height);
                List<Intersectable.GeoPoint> intersectionPoints = scene.getGeometries().findIntersections(ray);
                if (intersectionPoints == null) {
                    imageWriter.writePixel(column, row, background);
                }
                else {
                    Intersectable.GeoPoint closestPoint = getClosestPoint(intersectionPoints);
                    imageWriter.writePixel(column/*-1*/, row/*-1*/, calcColor(closestPoint).getColor());
                }
            }
        }
    }

    /**
     * function that should calculate the color of specific point
     * @param p
     * @return
     */
    public Color calcColor(Intersectable.GeoPoint p) {
        Color color = scene.getAmbientLight().getIntensity();
        return color.add(p.geometry.getEmission()); //refactoring with colors' implementation
        //return
    }

    /**
     * function that checks which of the intersection points that were returned by findIntersections
     * is the closest one to the camera
     * @param points
     * @return
     */
    public Intersectable.GeoPoint getClosestPoint(List<Intersectable.GeoPoint> points) {
        Intersectable.GeoPoint closest = null;
        double t = Double.MAX_VALUE;

        Point3D p0 = this.scene.getCamera().getP0();

        for (Intersectable.GeoPoint p: points ) {
            double dist = p0.distance(p.point);
            if (dist < t){
                t= dist;
                closest =p;
            }
        }
        return closest;

    }

    /**
     * function that makes grids of specific color on the picture's matrix
     * @param interval
     * @param color
     */
    public void printGrid(int interval, java.awt.Color color) {
        double rows = this.imageWriter.getNx();
        double collumns = this.imageWriter.getNy();
        for (int col = 0; col < collumns; col++) {
            for (int row = 0; row < rows; row++) {
                if (col % interval == 0 || row % interval == 0) {
                    imageWriter.writePixel(row, col, color);
                }
            }
        }
    }

    /**
     * function that saves matrix as picture
     */
    public void writeToImage() {
        imageWriter.writeToImage();
    }
}
