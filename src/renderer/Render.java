//package renderer;
//
//import elements.Camera;
//import elements.LightSource;
//import geometries.Geometries;
//import geometries.Geometry;
//import geometries.Intersectable;
//import primitives.*;
//import scene.Scene;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import static primitives.Util.alignZero;
//
///**
// * The Render class is a union of Scene and ImageWriter so that the specific scene could be printed  as picture
// * authors Polina Frolov Korogodsky and Tselia Tebol
// */
//
//public class Render {
//    private Scene scene;
//    private  ImageWriter imageWriter ;
//    private static final double DELTA = 0.1;
//    private static final int MAX_CALC_COLOR_LEVEL = 10;
//    private static final double MIN_CALC_COLOR_K = 0.001;
//
//
//    /**
//     *Constructor
//     * @param scene (Scene)
//     * @param imageWriter (ImageWriter)
//     */
//    public Render(Scene scene, ImageWriter imageWriter) {
//        this.scene = scene;
//        this.imageWriter = imageWriter;
//    }
//
//    /**
//     * Constructor
//     * @param imageWriter
//     * @param scene
//     */
//    public Render(ImageWriter imageWriter, Scene scene) {
//        this.scene = scene;
//        this.imageWriter = imageWriter;
//    }
//    /**
//     * The function that saves the 3D scene's 2D representation in matrix
//     */
//    public void renderImage() {
//        java.awt.Color background = scene.getBackground().getColor();
//        Camera camera= scene.getCamera();
//        Intersectable geometries = scene.getGeometries();
//        double  distance = scene.getDistance();
//        Ray ray;
//        // number of pixels in the rows of the view plane
//        int width = (int) imageWriter.getWidth();
//        // number of pixels in the columns of the view plane
//        int height = (int) imageWriter.getHeight();
//
//        //  width of the image.
//        int Nx = imageWriter.getNx();
//        // height of the image
//        int Ny = imageWriter.getNy();
//
//        for (int row = 0; row < Ny; row++) {
//            for (int column = 0; column < Nx; column++) {
//                ray = camera.constructRayThroughPixel(Nx, Ny, column, row, distance, width, height);
//                List<Intersectable.GeoPoint> intersectionPoints = scene.getGeometries().findIntersections(ray);
//                if (intersectionPoints == null) {
//                    imageWriter.writePixel(column, row, background);
//                }
//                else {
//                    Intersectable.GeoPoint closestPoint = getClosestPoint(intersectionPoints);
//                    imageWriter.writePixel(column/*-1*/, row/*-1*/, calcColor(closestPoint, ray).getColor());
//                }
//            }
//        }
//    }
//
//    /**
//     * function that should calculate the color of specific point
//     * @param intersection
//     * @param level
//     * @param influenceLevel
//     * @return
//     */
//
//
//    private Color calcColor(Intersectable.GeoPoint intersection, Ray inRay, int level, double influenceLevel) {
//       Color color = intersection.getGeometry().getEmission();
//        if (level == 1 || influenceLevel < MIN_CALC_COLOR_K) {
//            return Color.BLACK;
//        }
//       // Color color = new Color(0, 0, 0);
//        Vector v = intersection.getPoint().subtract(scene.getCamera().getP0()).normalize();
//        Vector n = intersection.getGeometry().getNormal(intersection.getPoint());
//        Material material = intersection.getGeometry().getMaterial();
//        int nShininess = material.getNShininess();
//        double kd = material.getKD();
//        double ks = material.getKS();
//        double ktr = 0d;
//        if (scene.getLights() != null) {
//
//            List<LightSource> lightSources = scene.getLights();
//            for (LightSource lightSource : lightSources) {
//                Vector l = lightSource.getL(intersection.getPoint());
//                if (sign(alignZero(n.dotProduct(l))) == sign(alignZero(n.dotProduct(v)))) {
//                    ktr = transparency(lightSource, l, n, intersection);
//                    if (ktr * influenceLevel > MIN_CALC_COLOR_K) {
//                        Color lightIntensity = lightSource.getIntensity(intersection.getPoint()).scale(ktr);
//                        color = color.add(calcDiffusive(kd, l, n, lightIntensity),
//                                calcSpecular(ks, l, n, v, nShininess, lightIntensity));
//                    }
//
//
//                }
//            }
//        }
//       // color=color.add(getLightSourcesColors(intersection, influenceLevel, color, v, n, n.dotProduct(v), nShininess, kd, ks));
//        double kr= material.getKReflectance();
//        double kkr= influenceLevel * kr;
//        if (kkr>MIN_CALC_COLOR_K){
//            Ray reflectedRay = constructReflectedRay(intersection, inRay);
//            Intersectable.GeoPoint closestPoint = findClosestPoint(reflectedRay);
//            if(closestPoint!=null)
//                color=color.add(calcColor(closestPoint, reflectedRay, level-1, kkr).scale(kr));
//        }
//        double kt = material.getKTransparency();
//        double kkt = kt*influenceLevel;
//        if(kkt>MIN_CALC_COLOR_K){
//            Ray refractedRay = constructRefractedRay(intersection, inRay);
//            Intersectable.GeoPoint refractedPoint = findClosestPoint(refractedRay);
//            if (refractedPoint!=null)
//                color = color.add(calcColor(refractedPoint, refractedRay, level-1, kkt).scale(kt));
//        }
//        return  color;
//    }
//
//
//
//       /* if(level==0||influenceLevel<MIN_CALC_COLOR_K)
//            return Color.BLACK;
//        Color color = intersection.getGeometry().getEmission();//scene.getAmbientLight().getIntensity();
//        color = color.add(intersection.getGeometry().getEmission());
//        Vector v = intersection.getPoint().subtract(scene.getCamera().getP0()).normalize();
//        Vector n = intersection.getGeometry().getNormal(intersection.getPoint());
//        Material material = intersection.getGeometry().getMaterial();
//        int nShininess = material.getNShininess();
//        double kd = material.getKD();
//        double ks = material.getKS();
//        double ktr = 0d;
//        if (scene.getLights() != null) {
//
//            List<LightSource> lightSources = scene.getLights();
//            for (LightSource lightSource : lightSources) {
//                Vector l = lightSource.getL(intersection.getPoint());
//                if (sign(alignZero(n.dotProduct(l))) == sign(alignZero(n.dotProduct(v)))) {
//                     ktr = transparency(lightSource, l, n, intersection);
//                    if (ktr*influenceLevel>MIN_CALC_COLOR_K ) {
//                        Color lightIntensity = lightSource.getIntensity(intersection.getPoint()).scale(ktr);
//                        color = color.add(calcDiffusive(kd, l, n, lightIntensity),
//                                calcSpecular(ks, l, n, v, nShininess, lightIntensity));
//                    }
//                }
//            }
//            if (level==1)
//                return Color.BLACK;
//            double kReflection = intersection.getGeometry().getMaterial().getKReflectance();
//            //System.out.println(kReflection);
//            double kkr = kReflection*influenceLevel;
//            //System.out.println(kkr);
//            if(kkr>MIN_CALC_COLOR_K){
//               // System.out.println("I am here!");
//                Ray reflectedRay=constructReflectedRay(intersection,  inRay);
//                Intersectable.GeoPoint reflectedPoint = findClosestPoint(reflectedRay);
//                if (reflectedPoint!=null){
//                    color=color.add(calcColor(reflectedPoint, reflectedRay, level-1, kkr).scale(kReflection));
//                }
//                double kTransparency = intersection.getGeometry().getMaterial().getKTransparency();
//                double kkt = kTransparency*influenceLevel;
//               // System.out.println(influenceLevel);
//                if(kkt>MIN_CALC_COLOR_K){
//                    //System.out.println("I am here");
//                    Ray refractedRay = constructRefractedRay(intersection, inRay);
//                    Intersectable.GeoPoint refractedPoint = findClosestPoint(refractedRay);
//                    if (refractedPoint!=null) {
//                        color = color.add(calcColor(refractedPoint, refractedRay, level - 1, kkt).scale(ktr * kTransparency));
//                        //return color;
//                      // System.out.println("I am here");
//                    }
//                }
//            }
//
//        }
//
//        return color;
//    }*/
//
//    /**
//     * function constructs a reflected ray
//     * @param intersection
//     * @param inRay
//     * @return
//     */
//    private Ray constructReflectedRay(Intersectable.GeoPoint intersection, Ray inRay) {
//
//        Vector rayDirection = inRay.getDirection();
//        Vector normal = intersection.getGeometry().getNormal(intersection.getPoint());
//        Vector r = rayDirection.subtract(normal.scale(2*(rayDirection.dotProduct(normal))));
//        return new Ray(intersection.getPoint(), r, normal);
//        //return new Ray(intersection.getPoint().add(r.scale(DELTA)), r);
//    }
//
//
//
//    /**
//     * function constructs a refracted ray initializing it by point that it received and the vector of the ray it received
//     * @param point
//     * @param inRay
//     * @return
//     */
//    Ray constructRefractedRay(Intersectable.GeoPoint point, Ray inRay){
//        return new Ray(point.getPoint(), inRay.getDirection(), point.getGeometry().getNormal(point.getPoint()));
//        //return new Ray(point.getPoint().add(point.getGeometry().getNormal(point.getPoint()).scale(-DELTA)),
//          //      inRay.getDirection());
//    }
//
//
//    /**
//     * function finds the closest intersection point with this ray
//     * by calling the findIntersections(Intersectable) function and then with it's result -
//     * to the getClosestPoint(List<Intersectable>) function
//     * @param ray
//     * @return
//     */
//    private Intersectable.GeoPoint findClosestPoint(Ray ray){
//        if(ray==null||scene.getGeometries()==null)
//            return null;
//        List <Intersectable.GeoPoint> points = scene.getGeometries().findIntersections(ray);
//        if (points!=null)
//            return getClosestPoint(points);
//        return  null;
//    }
//    /**
//     * function that checks which of the intersection points that were returned by findIntersections
//     * is the closest one to the camera
//     * @param points
//     * @return
//     */
//    public Intersectable.GeoPoint getClosestPoint(List< Intersectable.GeoPoint > points) {
//        Intersectable.GeoPoint closest = null;
//        double t = Double.MAX_VALUE;
//
//        Point3D p0 = this.scene.getCamera().getP0();
//
//        for (Intersectable.GeoPoint p: points ) {
//            double dist = p0.distance(p.getPoint());
//            if (dist < t){
//                t= dist;
//                closest =p;
//            }
//        }
//        return closest;
//
//    }
//
//    /**
//     * function that makes grids of specific color on the picture's matrix
//     * @param interval
//     * @param color
//     */
//    public void printGrid(int interval, java.awt.Color color) {
//        double rows = this.imageWriter.getNx();
//        double collumns = this.imageWriter.getNy();
//        for (int col = 0; col < collumns; col++) {
//            for (int row = 0; row < rows; row++) {
//                if (col % interval == 0 || row % interval == 0) {
//                    imageWriter.writePixel(row, col, color);
//                }
//            }
//        }
//    }
//
//    /**
//     * function that saves matrix as picture
//     */
//    public void writeToImage() {
//        imageWriter.writeToImage();
//    }
//
//    /**
//     * function that calculate the diffuse composant of the light reflection
//     * @param kd
//     * @param n
//     * @param lightIntensity
//     * @return
//     */
//    private Color calcDiffusive(double kd, Vector l ,Vector n, Color lightIntensity) {
//        double nl = alignZero(n.dotProduct(l));
//        if (nl < 0) { nl = -nl; }
//        return lightIntensity.scale(nl * kd);
//    }
//
//
//    /**
//     * function that calculate the specular composant of the light reflection
//     * @param ks
//     * @param l
//     * @param n
//     * @param v
//     * @param nShininess
//     * @param lightIntensity
//     * @return
//     */
//    private Color calcSpecular(double ks, Vector l, Vector n, Vector v, int nShininess, Color lightIntensity) {
//        try {
//            double nl = alignZero(n.dotProduct(l));
//            double sh = nShininess;
//
//            Vector R = l.add(n.scale(-2 * nl));
//
//            double minusVR = -alignZero(R.dotProduct(v));
//            if (minusVR <= 0) {     // if view in opposite direction
//                return Color.BLACK;
//            }
//            return lightIntensity.scale(ks * Math.pow(minusVR, sh));
//        }
//        catch (Exception ex){throw ex;}// if nl=0
//    }
//
//    /**
//     * function that verifies the sign of value
//     * @param val
//     * @return
//     */
//    private boolean sign(double val) {
//        return (val > 0d);
//    }
//
//    /**
//     * Function that checks whether an object is shadowed or not.
//     * It constructs the ray from the intersection point to the light source and if it has no intersections with other
//     * geometries, it returns true, otherwise false
//     * @param l
//     * @param n
//     * @param gp
//     * @param light
//     * @return
//     */
//    private boolean unshaded(Vector l, Vector n, Intersectable.GeoPoint gp, LightSource light){
//       // if(gp.getGeometry().getMaterial().getKTransparency()!=0)
//         //   return true;
//        Vector pointToLight = l.scale(-1); //lightDirection
//
//        Vector delta = n.scale(Util.alignZero(pointToLight.dotProduct(n))>0? DELTA:-DELTA);
//        Point3D point = gp.getPoint().add(delta);
//        Ray toLight = new Ray(point, pointToLight);
//        List<Intersectable.GeoPoint> intersections = this.scene.getGeometries().findIntersections(toLight);
//        if(intersections!=null){
//            double dist = light.getDistance(gp.getPoint());
//
//            List<Intersectable.GeoPoint> nonFarIntersection = new ArrayList<>();
//            for(Intersectable.GeoPoint intersection: intersections)
//            if(/*light.getDistance(intersection.getPoint())<dist)
//                nonFarIntersection.add(intersection*/ alignZero(intersection.getPoint().distance(gp.getPoint())-dist)<=0&&intersection.getGeometry().getMaterial().getKTransparency()==0)
//                return false;
//
//            //return nonFarIntersection==null;
//        }
//        return true;
//
//    }
//
//    /**
//     * the function to get transparency level
//     * @param light
//     * @param l
//     * @param n
//     * @param geopoint
//     * @return
//     */
//    private double transparency(LightSource light, Vector l, Vector n, Intersectable.GeoPoint geopoint){
//        Vector connectingToLight = l.scale(-1);
//        Vector delta = n.scale(Util.alignZero(connectingToLight.dotProduct(n))>0? DELTA:-DELTA);
//        Point3D startPoint = geopoint.getPoint().add(delta);
//        Ray toLight = new Ray(startPoint, connectingToLight);
//        List<Intersectable.GeoPoint> intersections = scene.getGeometries().findIntersections(toLight);
//        if (intersections==null) return  1d;
//        double lightDistance = light.getDistance(geopoint.getPoint());
//        double ktr = 1d;
//        for(Intersectable.GeoPoint point: intersections){
//            if(alignZero(point.getPoint().distance(geopoint.getPoint())-lightDistance)<=0){
//                if(alignZero(point.getGeometry().getMaterial().getKTransparency())==0)
//                    return ktr;
//                ktr*=point.getGeometry().getMaterial().getKTransparency();
//                if (ktr<=MIN_CALC_COLOR_K) return 0d;
//            }
//        }
//        return  ktr;
//
//
//
//    }
//
//
//    /**
//     * function that is called from renderImage();
//     * it calls for recursive calcColor and then returns it's result added to the color of the geometry
//     * @param gp
//     * @param ray
//     * @return
//     */
//    private Color calcColor(Intersectable.GeoPoint gp, Ray ray){
//        //return calcColor(gp, ray, MAX_CALC_COLOR_LEVEL, 1d).add(scene.getAmbientLight().getIntensity());
//        return calcColor(gp, ray, MAX_CALC_COLOR_LEVEL, 1.0).add(scene.getAmbientLight().getIntensity());
//    }
//}
//
package renderer;

import elements.Camera;
import elements.LightSource;
import geometries.*;
import primitives.*;
import scene.Scene;

import java.util.ArrayList;
import java.util.LinkedList;
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
    private double apertureWidth;
    private double appertureHeight;
    private double focalLength;
    private int numOfRays;


    /**
     *Constructor
     * @param scene (Scene)
     * @param imageWriter (ImageWriter)
     */
    public Render(Scene scene, ImageWriter imageWriter) {
        this(imageWriter, scene, Double.NaN, Double.NaN, Double.NaN, 0);
    }

    /**
     * Constructor
     * @param imageWriter
     * @param scene
     */
    public Render(ImageWriter imageWriter, Scene scene) {
        this(imageWriter, scene, Double.NaN, Double.NaN, Double.NaN, 0);
    }

    /**
     * Constructor for DepthOfField
     * @param imgWrt
     * @param scene
     * @param _ap
     * @param _focalLength
     * @param _nRays
     */
    public Render(ImageWriter imgWrt, Scene scene, double _apW, double _apHeight, double _focalLength, int _nRays){
        this.scene = scene;
        this.imageWriter = imgWrt;
        this.apertureWidth = _apW;
        this.appertureHeight = _apHeight;
        this.focalLength = _focalLength;
        this.numOfRays = _nRays;
    }

    public Render(Scene scene, ImageWriter imgWrt, double _apW, double _apH, double _fLength, int nRays){
        this(imgWrt, scene, _apW, _apH, _fLength, nRays);
    }

    public void renderImageWithDepthOfField() {
        System.out.println("Depth");
        java.awt.Color background = scene.getBackground().getColor();
        Camera camera = scene.getCamera();
        Intersectable geometries = scene.getGeometries();
        double distance = scene.getDistance();
        Ray ray;
        // number of pixels in the rows of the view plane
        int width = (int) imageWriter.getWidth();
        // number of pixels in the columns of the view plane
        int height = (int) imageWriter.getHeight();

        //  width of the image.
        int Nx = imageWriter.getNx();
        // height of the image
        int Ny = imageWriter.getNy();
        //focal plane definition
        Point3D planePoint = this.scene.getCamera().getP0().add(this.scene.getCamera().getvTo().scale(this.scene.getDistance() + this.focalLength));
        Vector planeVector = this.scene.getCamera().getvTo();
        Plane focalPlane = new Plane(planePoint, planeVector);
        Camera currentCamera = this.scene.getCamera();
        //Point3D centerOfFocus = currentCamera.getP0().add(planeVector.scale(this.scene.getDistance()));
        //Plane viewPlane = new Plane(, planeVector);
        Polygon focus = new Polygon(new Color(255, 255, 255), new Material(0, 0, 0, 1, 0), planePoint.add(currentCamera.getvUp().scale(appertureHeight / 2)),
                planePoint.add(currentCamera.getvRight().scale(apertureWidth / 2)),
                planePoint.add(currentCamera.getvUp().scale(-appertureHeight / 2)),
                planePoint.add(currentCamera.getvRight().scale(-apertureWidth / 2)));


        for (int row = 0; row < Ny; row++) {
            for (int column = 0; column < Nx; column++) {
                ray = currentCamera.constructRayThroughPixel(Nx, Ny, column, row, distance, width, height);
                List<Intersectable.GeoPoint> focalGeoPoint = focalPlane.findIntersections(ray);
                if (focalGeoPoint == null)
                    throw new RuntimeException("Ray is parallel to the plane");
                if (focus.findIntersections(ray) != null) {//if the object is in focus
                    Point3D focalPoint = focalGeoPoint.get(0).getPoint();
                    ray = camera.constructRayThroughPixel(Nx, Ny, column, row, distance, width, height);
                    List<Intersectable.GeoPoint> intersectionPoints = geometries.findIntersections(ray);//differ between in focus / outside focus
                    if (intersectionPoints == null) {
                        imageWriter.writePixel(column, row, background);
                    } else {
                        Intersectable.GeoPoint closestPoint = getClosestPoint(intersectionPoints);
                        imageWriter.writePixel(column/*-1*/, row/*-1*/, calcColor(closestPoint, ray).getColor());
                    }
                }


            /*ray = camera.constructRayThroughPixel(Nx, Ny, column, row, distance, width, height);
                List<Intersectable.GeoPoint> focalGeoPoint = focalPlane.findIntersections(ray);
                if (focalGeoPoint == null)
                    throw new RuntimeException("Ray is parallel to the plane");
                if (focus.findIntersections(ray) != null) {//if the object is in focus
                    Point3D focalPoint = focalGeoPoint.get(0).getPoint();


                    List<Ray> depthRays = new ArrayList<>();
                    for(int w=0; w<apertureWidth; w++) {
                        for (int h = 0; h < appertureHeight; h++) {

                            depthRays.add(currentCamera.constructRayThroughPixel((Nx + w + h) * numOfRays, (Ny + w + h) * numOfRays, (column + w + h) * numOfRays, (row + w + h) * numOfRays, distance, width, height));
                        }
                    }
                    Color[] colors = new Color[depthRays.size()];
                    for (int k=0; k<depthRays.size()-1; k++){
                        List<Intersectable.GeoPoint> intersectionPoints = geometries.findIntersections(depthRays.get(k));//differ between in focus / outside focus
                        if (intersectionPoints == null) {
                            //imageWriter.writePixel(column, row, background);
                            colors[k] = this.scene.getBackground();
                        } else {
                            Intersectable.GeoPoint closestPoint = getClosestPoint(intersectionPoints);
                            colors[k] = calcColor(closestPoint, ray);
                        }
                    }
                    if(colors!=null) {
                        Color ans = Color.averageColor(colors);
                        imageWriter.writePixel(column, row, new java.awt.Color(ans.getColor().getRGB()));
                    }
                    else imageWriter.writePixel(column, row, background);

                    }*/


                        /*List<Ray> depthRays = scene.getCamera().constructMultipleRaysThroughPixel(Nx, Ny, column, row, distance, width, height, numOfRays, focalPoint);
                    for (int i = 0; i < numOfRays; i++) {

                        List<Intersectable.GeoPoint> intersectionPoints = geometries.findIntersections(depthRays.get(i));//differ between in focus / outside focus
                        if (intersectionPoints == null) {
                            //imageWriter.writePixel(column, row, background);
                            colors[i] = this.scene.getBackground();
                        } else {
                            Intersectable.GeoPoint closestPoint = getClosestPoint(intersectionPoints);
                            colors[i] = calcColor(closestPoint, ray);
                        }
                    }
                    Color ans = Color.averageColor(colors);
                    imageWriter.writePixel(column, row, new java.awt.Color(ans.getColor().getRGB()));*/


                else {
                    List<Intersectable.GeoPoint> intersectionPoints = geometries.findIntersections(currentCamera.constructRayThroughPixel(Nx, Ny, column, row, distance, width, height));
                    List<Color> colors = new LinkedList<>();

                    if (intersectionPoints == null) {
                        colors.add(new Color(background));
                    } else {
                        Intersectable.GeoPoint closestPoint = getClosestPoint(intersectionPoints);
                        colors.add(new Color(calcColor(closestPoint, ray).getColor()));
                    }
                    if (row != 0) {
                        intersectionPoints = geometries.findIntersections(currentCamera.constructRayThroughPixel(Nx, Ny, column, row - 1, distance, width, height));
                        if (intersectionPoints == null) {
                            colors.add(new Color(background));
                        } else {
                            Intersectable.GeoPoint closestPoint = getClosestPoint(intersectionPoints);
                            colors.add(new Color(calcColor(closestPoint, ray).getColor()));
                        }
                        if(column!=0){
                            intersectionPoints = geometries.findIntersections(currentCamera.constructRayThroughPixel(Nx, Ny, column-1, row, distance, width, height));
                            if (intersectionPoints == null) {
                                colors.add(new Color(background));
                            } else {
                                Intersectable.GeoPoint closestPoint = getClosestPoint(intersectionPoints);
                                colors.add(new Color(calcColor(closestPoint, ray).getColor()));
                            }
                        }
                        if(column<Nx){
                            intersectionPoints = geometries.findIntersections(currentCamera.constructRayThroughPixel(Nx, Ny, column+1, row, distance, width, height));
                            if (intersectionPoints == null) {
                                colors.add(new Color(background));
                            } else {
                                Intersectable.GeoPoint closestPoint = getClosestPoint(intersectionPoints);
                                colors.add(new Color(calcColor(closestPoint, ray).getColor()));
                            }
                        }
                        if(column>1){
                            intersectionPoints = geometries.findIntersections(currentCamera.constructRayThroughPixel(Nx, Ny, column-1, row, distance, width, height));
                            if (intersectionPoints == null) {
                                colors.add(new Color(background));
                            } else {
                                Intersectable.GeoPoint closestPoint = getClosestPoint(intersectionPoints);
                                colors.add(new Color(calcColor(closestPoint, ray).getColor()));
                            }
                            if(column>2){
                                intersectionPoints = geometries.findIntersections(currentCamera.constructRayThroughPixel(Nx, Ny, column-2, row, distance, width, height));
                                if (intersectionPoints == null) {
                                    colors.add(new Color(background));
                                } else {
                                    Intersectable.GeoPoint closestPoint = getClosestPoint(intersectionPoints);
                                    colors.add(new Color(calcColor(closestPoint, ray).getColor()));
                                }
                            }
                        }
                        if(column<Nx-1){
                            intersectionPoints = geometries.findIntersections(currentCamera.constructRayThroughPixel(Nx, Ny, column+1, row, distance, width, height));
                            if (intersectionPoints == null) {
                                colors.add(new Color(background));
                            } else {
                                Intersectable.GeoPoint closestPoint = getClosestPoint(intersectionPoints);
                                colors.add(new Color(calcColor(closestPoint, ray).getColor()));
                            }
                            if(column<Nx-2){
                                intersectionPoints = geometries.findIntersections(currentCamera.constructRayThroughPixel(Nx, Ny, column+2, row, distance, width, height));
                                if (intersectionPoints == null) {
                                    colors.add(new Color(background));
                                } else {
                                    Intersectable.GeoPoint closestPoint = getClosestPoint(intersectionPoints);
                                    colors.add(new Color(calcColor(closestPoint, ray).getColor()));
                                }
                            }
                        }
                    }  if (row!=Ny)
                        {
                        intersectionPoints = geometries.findIntersections(currentCamera.constructRayThroughPixel(Nx, Ny, column, row  + 1, distance, width, height));
                        if (intersectionPoints == null) {
                            colors.add(new Color(background));
                        } else {
                            Intersectable.GeoPoint closestPoint = getClosestPoint(intersectionPoints);
                            colors.add(new Color(calcColor(closestPoint, ray).getColor()));
                        }
                            if(column!=0){
                                intersectionPoints = geometries.findIntersections(currentCamera.constructRayThroughPixel(Nx, Ny, column-1, row, distance, width, height));
                                if (intersectionPoints == null) {
                                    colors.add(new Color(background));
                                } else {
                                    Intersectable.GeoPoint closestPoint = getClosestPoint(intersectionPoints);
                                    colors.add(new Color(calcColor(closestPoint, ray).getColor()));
                                }
                            }
                            if(column!=Nx){
                                intersectionPoints = geometries.findIntersections(currentCamera.constructRayThroughPixel(Nx, Ny, column+1, row, distance, width, height));
                                if (intersectionPoints == null) {
                                    colors.add(new Color(background));
                                } else {
                                    Intersectable.GeoPoint closestPoint = getClosestPoint(intersectionPoints);
                                    colors.add(new Color(calcColor(closestPoint, ray).getColor()));
                                }
                            }
                            if(column>1){
                                intersectionPoints = geometries.findIntersections(currentCamera.constructRayThroughPixel(Nx, Ny, column-1, row, distance, width, height));
                                if (intersectionPoints == null) {
                                    colors.add(new Color(background));
                                } else {
                                    Intersectable.GeoPoint closestPoint = getClosestPoint(intersectionPoints);
                                    colors.add(new Color(calcColor(closestPoint, ray).getColor()));
                                }
                                if(column>2){
                                    intersectionPoints = geometries.findIntersections(currentCamera.constructRayThroughPixel(Nx, Ny, column-2, row, distance, width, height));
                                    if (intersectionPoints == null) {
                                        colors.add(new Color(background));
                                    } else {
                                        Intersectable.GeoPoint closestPoint = getClosestPoint(intersectionPoints);
                                        colors.add(new Color(calcColor(closestPoint, ray).getColor()));
                                    }
                                }
                            }
                            if(column<Nx-1){
                                intersectionPoints = geometries.findIntersections(currentCamera.constructRayThroughPixel(Nx, Ny, column+1, row, distance, width, height));
                                if (intersectionPoints == null) {
                                    colors.add(new Color(background));
                                } else {
                                    Intersectable.GeoPoint closestPoint = getClosestPoint(intersectionPoints);
                                    colors.add(new Color(calcColor(closestPoint, ray).getColor()));
                                }
                                if(column<Nx-2){
                                    intersectionPoints = geometries.findIntersections(currentCamera.constructRayThroughPixel(Nx, Ny, column+2, row, distance, width, height));
                                    if (intersectionPoints == null) {
                                        colors.add(new Color(background));
                                    } else {
                                        Intersectable.GeoPoint closestPoint = getClosestPoint(intersectionPoints);
                                        colors.add(new Color(calcColor(closestPoint, ray).getColor()));
                                    }
                                }
                            }

                    }
                    if( column!=0){
                        intersectionPoints = geometries.findIntersections(currentCamera.constructRayThroughPixel(Nx, Ny, column-1, row, distance, width, height));
                        if (intersectionPoints == null) {
                            colors.add(new Color(background));
                        } else {
                            Intersectable.GeoPoint closestPoint = getClosestPoint(intersectionPoints);
                            colors.add(new Color(calcColor(closestPoint, ray).getColor()));
                        }
                    }
                    if(column !=Nx){
                        intersectionPoints = geometries.findIntersections(currentCamera.constructRayThroughPixel(Nx, Ny, column+1, row, distance, width, height));
                        if (intersectionPoints == null) {
                            colors.add(new Color(background));
                        } else {
                            Intersectable.GeoPoint closestPoint = getClosestPoint(intersectionPoints);
                            colors.add(new Color(calcColor(closestPoint, ray).getColor()));
                        }
                    }

                    //if(row!=Nx){

                    //}
                    imageWriter.writePixel(column, row, new java.awt.Color(Color.averageColor(colors).getColor().getRGB()));
                }
            }
        }
    }





    /**
     * The function that saves the 3D scene's 2D representation in matrix
     */
    public void renderImage() {
        if(this.apertureWidth!=Double.NaN){
            renderImageWithDepthOfField();
            return;
        }

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
                List<Intersectable.GeoPoint> intersectionPoints = geometries.findIntersections(ray);//differ between in focus / outside focus
                if (intersectionPoints == null) {
                    imageWriter.writePixel(column, row, background);
                }
                else {
                    Intersectable.GeoPoint closestPoint = getClosestPoint(intersectionPoints);
                    imageWriter.writePixel(column/*-1*/, row/*-1*/, calcColor(closestPoint, ray).getColor());
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
        Color color = intersection.getGeometry().getEmission();
        if (level == 1 || influenceLevel < MIN_CALC_COLOR_K) {
            return Color.BLACK;
        }
        //Color color = new Color(0, 0, 0);
        Vector v = intersection.getPoint().subtract(scene.getCamera().getP0()).normalize();
        Vector n = intersection.getGeometry().getNormal(intersection.getPoint());
        Material material = intersection.getGeometry().getMaterial();
        int nShininess = material.getNShininess();
        double kd = material.getKD();
        double ks = material.getKS();
        double ktr = 0d;
        if (scene.getLights() != null) {

            List<LightSource> lightSources = scene.getLights();
            for (LightSource lightSource : lightSources) {
                Vector l = lightSource.getL(intersection.getPoint());
                if (sign(alignZero(n.dotProduct(l))) == sign(alignZero(n.dotProduct(v)))) {
                    ktr = transparency(lightSource, l, n, intersection);
                    if (ktr * influenceLevel > MIN_CALC_COLOR_K) {
                        Color lightIntensity = lightSource.getIntensity(intersection.getPoint()).scale(ktr);
                        color = color.add(calcDiffusive(kd, l, n, lightIntensity),
                                calcSpecular(ks, l, n, v, nShininess, lightIntensity));
                    }


                }
            }
        }
        double kr= material.getKReflectance();
        double kkr= influenceLevel * kr;
        if (kkr>MIN_CALC_COLOR_K){
            Ray reflectedRay = constructReflectedRay(intersection, inRay);
            Intersectable.GeoPoint closestPoint = findClosestPoint(reflectedRay);
            if(closestPoint!=null)
                color=color.add(calcColor(closestPoint, reflectedRay, level-1, kkr).scale(kr));
        }
        double kt = material.getKTransparency();
        double kkt = kt*influenceLevel;
        if(kkt>MIN_CALC_COLOR_K){
            Ray refractedRay = constructRefractedRay(intersection, inRay);
            Intersectable.GeoPoint refractedPoint = findClosestPoint(refractedRay);
            if (refractedPoint!=null)
                color = color.add(calcColor(refractedPoint, refractedRay, level-1, kkt).scale(kt));
        }
        return  color;
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
        return new Ray(intersection.getPoint().add(r.scale(DELTA)), r);
    }



    /**
     * function constructs a refracted ray initializing it by point that it received and the vector of the ray it received
     * @param point
     * @param inRay
     * @return
     */
    Ray constructRefractedRay(Intersectable.GeoPoint point, Ray inRay){
        return new Ray(point.getPoint().add(point.getGeometry().getNormal(point.getPoint()).scale(-DELTA)),
                inRay.getDirection());
    }

    /**
     * function finds the closest intersection point with this ray
     * by calling the findIntersections(Intersectable) function and then with it's result -
     * to the getClosestPoint(List<Intersectable>) function
     * @param ray
     * @return
     */
    private Intersectable.GeoPoint findClosestPoint(Ray ray){
        List <Intersectable.GeoPoint> points = scene.getGeometries().findIntersections(ray);
        if (points!=null)
            return getClosestPoint(points);
        return  null;
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
        double columns = this.imageWriter.getNy();
        for (int col = 0; col < columns; col++) {
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
        // if(gp.getGeometry().getMaterial().getKTransparency()!=0)
        //   return true;
        Vector pointToLight = l.scale(-1); //lightDirection

        Vector delta = n.scale(Util.alignZero(pointToLight.dotProduct(n))>0? DELTA:-DELTA);
        Point3D point = gp.getPoint().add(delta);
        Ray toLight = new Ray(point, pointToLight);
        List<Intersectable.GeoPoint> intersections = this.scene.getGeometries().findIntersections(toLight);
        if(intersections!=null){
            double dist = light.getDistance(gp.getPoint());

            List<Intersectable.GeoPoint> nonFarIntersection = new ArrayList<>();
            for(Intersectable.GeoPoint intersection: intersections)
                if(/*light.getDistance(intersection.getPoint())<dist)
                nonFarIntersection.add(intersection*/ alignZero(intersection.getPoint().distance(gp.getPoint())-dist)<=0&&intersection.getGeometry().getMaterial().getKTransparency()==0)
                    return false;

            //return nonFarIntersection==null;
        }
        return true;

    }

    /**
     * the function to get transparency level
     * @param light
     * @param l
     * @param n
     * @param geopoint
     * @return
     */
    private double transparency(LightSource light, Vector l, Vector n, Intersectable.GeoPoint geopoint){
        Vector connectingToLight = l.scale(-1);
        Vector delta = n.scale(Util.alignZero(connectingToLight.dotProduct(n))>0? DELTA:-DELTA);
        Point3D startPoint = geopoint.getPoint().add(delta);
        Ray toLight = new Ray(startPoint, connectingToLight);
        List<Intersectable.GeoPoint> intersections = scene.getGeometries().findIntersections(toLight);
        if (intersections==null) return  1d;
        double lightDistance = light.getDistance(geopoint.getPoint());
        double ktr = 1d;
        for(Intersectable.GeoPoint point: intersections){
            if(alignZero(point.getPoint().distance(geopoint.getPoint())-lightDistance)<=0){
                ktr*=point.getGeometry().getMaterial().getKTransparency();
                if (ktr<=MIN_CALC_COLOR_K) return 0d;
            }
        }
        return  ktr;
    }


    /**
     * function that is called from renderImage();
     * it calls for recursive calcColor and then returns it's result added to the color of the geometry
     * @param gp
     * @param ray
     * @return
     */
    private Color calcColor(Intersectable.GeoPoint gp, Ray ray){
        //return calcColor(gp, ray, MAX_CALC_COLOR_LEVEL, 1d).add(scene.getAmbientLight().getIntensity());
        return calcColor(gp, ray, MAX_CALC_COLOR_LEVEL, 1.0).add(scene.getAmbientLight().getIntensity());
    }
}