package renderer;

import elements.Camera;
import geometries.Intersectable;
import primitives.Color;
import primitives.Point3D;
import primitives.Ray;
import scene.Scene;

import java.util.List;

public class Render {
    private Scene scene;
    private  ImageWriter imageWriter ;

    public Render(Scene scene, ImageWriter imageWriter) {
        this.scene = scene;
        this.imageWriter = imageWriter;
    }

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
                    imageWriter.writePixel(column-1, row-1, calcColor(closestPoint).getColor());
                }
            }
        }
    }

    public Color calcColor(Point3D p) {
        return scene.get_ambientLight().get_intensity();
    }

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


    public void writeToImage() {
        imageWriter.writeToImage();
    }
}
