package renderer;

import elements.Camera;
import geometries.Intersectable;
import primitives.Color;
import primitives.Point3D;
import primitives.Ray;
import scene.Scene;

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
     * The function that saves the 3D scene's 2D representation in matrix
     */
    public void renderImage() {
        java.awt.Color background = scene.get_background().getColor();
        Camera camera= scene.get_camera();
        Intersectable geometries = scene.get_geometries();
        double  distance = scene.get_distance();
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
                List<Point3D> intersectionPoints = scene.get_geometries().findIntersections(ray);
                if (intersectionPoints == null) {
                    imageWriter.writePixel(column, row, background);
                }
                else {
                    Point3D closestPoint = getClosestPoint(intersectionPoints);
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
    public Color calcColor(Point3D p) {
        return scene.get_ambientLight().get_intensity();
    }

    /**
     * function that checks which of the intersection points that were returned by findIntersections
     * is the closest one to the camera
     * @param points
     * @return
     */
    public Point3D getClosestPoint(List<Point3D> points) {
        Point3D closest = null;
        double t = Double.MAX_VALUE;

        Point3D p0 = this.scene.get_camera().getP0();

        for (Point3D p: points ) {
            double dist = p0.distance(p);
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
