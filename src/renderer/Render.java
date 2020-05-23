package renderer;

import elements.Camera;
import elements.LightSource;
import geometries.Intersectable;
import primitives.*;
import scene.Scene;

import java.util.ArrayList;
import java.util.List;

import static primitives.Util.alignZero;

/**
 * The Render class is a union of Scene and ImageWriter so that the specific scene could be printed  as picture
 * authors Polina Frolov Korogodsky and Tselia Tebol
 */

public class Render {
    private Scene scene;
    private  ImageWriter imageWriter ;
    private static final double DELTA = 0.1;
    private static final int MAX_CALC_COLOR_LEVEL = 10;
    private static final double MIN_CALC_COLOR_K = 0.001;


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
                ray = camera.constructRayThroughPixel(Nx, Ny, column, row, distance, width, height);
                List<Intersectable.GeoPoint> intersectionPoints = scene.getGeometries().findIntersections(ray);
                if (intersectionPoints == null) {
                    imageWriter.writePixel(column, row, background);
                }
                else {
                    Intersectable.GeoPoint closestPoint = getClosestPoint(intersectionPoints);
                    imageWriter.writePixel(column/*-1*/, row/*-1*/, calcColor(closestPoint, , ).getColor());
                }
            }
        }
    }

    /**
     * function that should calculate the color of specific point
     * @param intersection
     * @param level
     * @param influenceLevel
     * @return
     */


    private Color calcColor(Intersectable.GeoPoint intersection, Ray inRay, int level, double influenceLevel) {
        if(level==0||influenceLevel<MIN_CALC_COLOR_K)
            return Color.BLACK;
        Color color = intersection.getGeometry().getEmission();//scene.getAmbientLight().getIntensity();
        color = color.add(intersection.getGeometry().getEmission());
        Vector v = intersection.getPoint().subtract(scene.getCamera().getP0()).normalize();
        Vector n = intersection.getGeometry().getNormal(intersection.getPoint());
        Material material = intersection.getGeometry().getMaterial();
        int nShininess = material.getNShininess();
        double kd = material.getKD();
        double ks = material.getKS();
        if (scene.getLights() != null) {

            List<LightSource> lightSources = scene.getLights();
            for (LightSource lightSource : lightSources) {
                Vector l = lightSource.getL(intersection.getPoint());
                if (sign(alignZero(n.dotProduct(l))) == sign(alignZero(n.dotProduct(v)))) {
                    if (unshaded(l, n, intersection, lightSource)) {
                        Color lightIntensity = lightSource.getIntensity(intersection.getPoint());
                        color = color.add(calcDiffusive(kd, l, n, lightIntensity),
                                calcSpecular(ks, l, n, v, nShininess, lightIntensity));
                    }
                }
            }
            if (level==1)
                return Color.BLACK;
            double kReflection = intersection.getGeometry().getMaterial().getKReflectance();
            double kkr = kReflection*influenceLevel;
            if(kkr>MIN_CALC_COLOR_K){
                Ray reflectedRay=constructReflectedRay(intersection,  inRay);
                Intersectable.GeoPoint reflectedPoint = findClosestPoint(reflectedRay);
                if (reflectedPoint!=null){
                    color=color.add(calcColor(reflectedPoint, reflectedRay, level-1, kkr).scale(kReflection));
                }
                double kTransparency = intersection.getGeometry().getMaterial().getKTransparency();
                double kkt = kTransparency*influenceLevel;
                if(kkt>MIN_CALC_COLOR_K){
                    Ray refractedRay = constructRefractedRay(intersection.getPoint(), inRay);
                    Intersectable.GeoPoint refractedPoint = findClosestPoint(refractedRay);
                    if (refractedPoint!=null)
                        color = color.add(calcColor(refractedPoint, refractedRay, level-1, kkt).scale(kTransparency));
                }
            }

        }

        return color;
    }

    /**
     * function constructs a reflected ray
     * @param intersection
     * @param inRay
     * @return
     */
    private Ray constructReflectedRay(Intersectable.GeoPoint intersection, Ray inRay) {

        Vector rayDirection = inRay.getDirection();
        Vector normal = intersection.getGeometry().getNormal(intersection.getPoint());
        Vector r = rayDirection.subtract(normal.scale(2*(rayDirection.dotProduct(normal))));
        return new Ray(intersection.getPoint().add(r.scale(0.00000000001)), r);
    }



    /**
     * function constructs a refracted ray initializing it by point that it received and the vector of the ray it received
     * @param point
     * @param inRay
     * @return
     */
    Ray constructRefractedRay(Point3D point, Ray inRay){
        return new Ray(point.add(inRay.getDirection().scale(0.000000000001)), inRay.getDirection());
    }

    /**
     * function finds the closest intersection point with this ray
     * by calling the findIntersections(Intersectable) function and then with it's result -
     * to the getClosestPoint(List<Intersectable>) function
     * @param ray
     * @return
     */
    private Intersectable.GeoPoint findClosestPoint(Ray ray){
        return getClosestPoint(scene.getGeometries().findIntersections(ray));
    }
    /**
     * function that checks which of the intersection points that were returned by findIntersections
     * is the closest one to the camera
     * @param points
     * @return
     */
    public Intersectable.GeoPoint getClosestPoint(List< Intersectable.GeoPoint > points) {
        Intersectable.GeoPoint closest = null;
        double t = Double.MAX_VALUE;

        Point3D p0 = this.scene.getCamera().getP0();

        for (Intersectable.GeoPoint p: points ) {
            double dist = p0.distance(p.getPoint());
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

    /**
     * function that calculate the diffuse composant of the light reflection
     * @param kd
     * @param n
     * @param lightIntensity
     * @return
     */
    private Color calcDiffusive(double kd, Vector l ,Vector n, Color lightIntensity) {
        double nl = alignZero(n.dotProduct(l));
        if (nl < 0) { nl = -nl; }
        return lightIntensity.scale(nl * kd);
    }


    /**
     * function that calculate the specular composant of the light reflection
     * @param ks
     * @param l
     * @param n
     * @param v
     * @param nShininess
     * @param lightIntensity
     * @return
     */
    private Color calcSpecular(double ks, Vector l, Vector n, Vector v, int nShininess, Color lightIntensity) {
        try {
            double nl = alignZero(n.dotProduct(l));
            double sh = nShininess;

            Vector R = l.add(n.scale(-2 * nl));

            double minusVR = -alignZero(R.dotProduct(v));
            if (minusVR <= 0) {     // if view in opposite direction
                return Color.BLACK;
            }
            return lightIntensity.scale(ks * Math.pow(minusVR, sh));
        }
        catch (Exception ex){throw ex;}// if nl=0
    }

    /**
     * function that verifies the sign of value
     * @param val
     * @return
     */
    private boolean sign(double val) {
        return (val > 0d);
    }

    /**
     * Function that checks whether an object is shadowed or not.
     * It constructs the ray from the intersection point to the light source and if it has no intersections with other
     * geometries, it returns true, otherwise false
     * @param l
     * @param n
     * @param gp
     * @param light
     * @return
     */
    private boolean unshaded(Vector l, Vector n, Intersectable.GeoPoint gp, LightSource light){
        Vector pointToLight = l.scale(-1);
        Vector delta = n.scale(Util.alignZero(pointToLight.dotProduct(n))>0? DELTA:-DELTA);
        Point3D point = gp.getPoint().add(delta);
        Ray toLight = new Ray(point, pointToLight);
        List<Intersectable.GeoPoint> intersections = this.scene.getGeometries().findIntersections(toLight);
        if(intersections!=null){
            double dist = light.getDistance(gp.getPoint());

            List<Intersectable.GeoPoint> nonFarIntersection = new ArrayList<>();
            for(Intersectable.GeoPoint intersection: intersections)
            if(light.getDistance(intersection.getPoint())<dist)
                nonFarIntersection.add(intersection);

            return nonFarIntersection==null;
        }
        return true;

    }
    private Color calcColor(Intersectable.GeoPoint gp, Ray ray){

    }
}

