////package renderer;
////
////import elements.Camera;
////import elements.LightSource;
////import geometries.Geometries;
////import geometries.Geometry;
////import geometries.Intersectable;
////import primitives.*;
////import scene.Scene;
////
////import java.util.ArrayList;
////import java.util.List;
////
////import static primitives.Util.alignZero;
//
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
//    private int nRays;
//
//
//    /**
//     *Constructor
//     * @param scene (Scene)
//     * @param imageWriter (ImageWriter)
//     */
//    public Render(Scene scene, ImageWriter imageWriter) {
//        this(imageWriter, scene, 1);
//    }
//
//    /**
//     * Constructor
//     * @param imageWriter
//     * @param scene
//     * @param _nRays
//     */
//    public Render(ImageWriter imageWriter, Scene scene, int _nRays) { //for Dan's tests
//        this.scene = scene;
//        this.imageWriter = imageWriter;
//        nRays = _nRays;
//    }
//
//    /**
//     *
//     * @param imageWriter
//     * @param scene
//     */
//    public Render(ImageWriter imageWriter, Scene scene) { //for Dan's tests
//        this(imageWriter, scene, 1);
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
//        List <java.awt.Color> pixelColors = new ArrayList<>();
//        int numOfPixelStart = 0;
//        for (int row = 0; row < Ny*nRays; row++) { //scaling to nRays is added
//            for (int column = numOfPixelStart; (column +1)%nRays!=0 /*Nx*nRays*/ ; column++) { //scaling to nRays is added
//                ray = camera.constructRayThroughPixel(Nx, Ny, column, row, distance, width, height);
//                List<Intersectable.GeoPoint> intersectionPoints = scene.getGeometries().findIntersections(ray);
//                if (intersectionPoints == null) {
//                    pixelColors.add(background);
//                    //imageWriter.writePixel(column, row, background);
//                }
//                else {
//                    Intersectable.GeoPoint closestPoint = getClosestPoint(intersectionPoints);
//                    pixelColors.add(calcColor(closestPoint, ray).getColor());
//                    //imageWriter.writePixel(column/*-1*/, row/*-1*/, calcColor(closestPoint, ray).getColor());
//                }
//                if((row+1)%nRays==0)
//                    imageWriter.writePixel((column/nRays), (row/nRays), averageColor(pixelColors).getColor());
//            }
//            numOfPixelStart+=nRays;
//
//        }
//    }
//
//
//    private Color averageColor(List<java.awt.Color> colours){
//        Color result = Color.BLACK;
//        Color [] colors = new Color[colours.size()];
//        for(int i=0; i<colours.size(); i++){
//            colors[i]=new Color(colours.get(i));
//        }
//        result = result.add(colors);
//        return result.reduce(colors.length);
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
//        Color color = intersection.getGeometry().getEmission();
//        if (level == 1 || influenceLevel < MIN_CALC_COLOR_K) {
//            return Color.BLACK;
//        }
//        //Color color = new Color(0, 0, 0);
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
//        }
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
//        return new Ray(intersection.getPoint().add(r.scale(DELTA)), r);
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
//        return new Ray(point.getPoint().add(point.getGeometry().getNormal(point.getPoint()).scale(-DELTA)),
//                inRay.getDirection());
//    }
//
//    /**
//     * function finds the closest intersection point with this ray
//     * by calling the findIntersections(Intersectable) function and then with it's result -
//     * to the getClosestPoint(List<Intersectable>) function
//     * @param ray
//     * @return
//     */
//    private Intersectable.GeoPoint findClosestPoint(Ray ray){
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
//        // if(gp.getGeometry().getMaterial().getKTransparency()!=0)
//        //   return true;
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
//                if(/*light.getDistance(intersection.getPoint())<dist)
//                nonFarIntersection.add(intersection*/ alignZero(intersection.getPoint().distance(gp.getPoint())-dist)<=0&&intersection.getGeometry().getMaterial().getKTransparency()==0)
//                    return false;
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
//                ktr*=point.getGeometry().getMaterial().getKTransparency();
//                if (ktr<=MIN_CALC_COLOR_K) return 0d;
//            }
//        }
//        return  ktr;
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
import geometries.Intersectable;
import primitives.*;
import scene.Scene;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import static primitives.Util.alignZero;

/**
 * The Render class is a union of Scene and ImageWriter so that the specific scene could be printed  as picture
 * authors Polina Frolov Korogodsky and Tselia Tebol
 */

public class Render {
    private Scene scene;
    private  ImageWriter imageWriter ;
    public int numSuperSampling;
    private static final double DELTA = 0.1;
    private static final int MAX_CALC_COLOR_LEVEL = 10;
    private static final double MIN_CALC_COLOR_K = 0.001;


    /**
     *Constructor
     * @param scene (Scene)
     * @param imageWriter (ImageWriter)
     */
    public Render(Scene scene, ImageWriter imageWriter) {
        this(scene, imageWriter, 1);
    }

    /**
     * Constructor
     * @param imageWriter
     * @param scene
     */
    public Render(ImageWriter imageWriter, Scene scene) {
        this(scene, imageWriter, 1);
    }

    public Render(ImageWriter imgwrt, Scene scene, int _nS){
        this.scene = scene;
        this.imageWriter = imgwrt;
        numSuperSampling = _nS;
    }

    public Render(Scene scene, ImageWriter imgwrt, int _nS){
        this(imgwrt, scene, _nS);
    }
    /**
     * The function that saves the 3D scene's 2D representation in matrix
     */
    public void renderImage() {
       // if(numSuperSampling==0) {
            //String name = this.imageWriter.getImageName();
            this.imageWriter = new ImageWriter(imageWriter.getImageName(), imageWriter.getWidth(), imageWriter.getHeight(), imageWriter.getNx()*numSuperSampling, imageWriter.getNy()*numSuperSampling);
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

            for (int row = 0; row < Ny; row++) {
                for (int column = 0; column < Nx; column++) {
                    ray = camera.constructRayThroughPixel(Nx, Ny, column, row, distance, width, height);
                    List<Intersectable.GeoPoint> intersectionPoints = scene.getGeometries().findIntersections(ray);
                    if (intersectionPoints == null) {
                        imageWriter.writePixel(column, row, background);
                    } else {
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



       /* if(level==0||influenceLevel<MIN_CALC_COLOR_K)
            return Color.BLACK;
        Color color = intersection.getGeometry().getEmission();//scene.getAmbientLight().getIntensity();
        color = color.add(intersection.getGeometry().getEmission());
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
                    if (ktr*influenceLevel>MIN_CALC_COLOR_K ) {
                        Color lightIntensity = lightSource.getIntensity(intersection.getPoint()).scale(ktr);
                        color = color.add(calcDiffusive(kd, l, n, lightIntensity),
                                calcSpecular(ks, l, n, v, nShininess, lightIntensity));
                    }
                }
            }
            if (level==1)
                return Color.BLACK;
            double kReflection = intersection.getGeometry().getMaterial().getKReflectance();
            //System.out.println(kReflection);
            double kkr = kReflection*influenceLevel;
            //System.out.println(kkr);
            if(kkr>MIN_CALC_COLOR_K){
               // System.out.println("I am here!");
                Ray reflectedRay=constructReflectedRay(intersection,  inRay);
                Intersectable.GeoPoint reflectedPoint = findClosestPoint(reflectedRay);
                if (reflectedPoint!=null){
                    color=color.add(calcColor(reflectedPoint, reflectedRay, level-1, kkr).scale(kReflection));
                }
                double kTransparency = intersection.getGeometry().getMaterial().getKTransparency();
                double kkt = kTransparency*influenceLevel;
               // System.out.println(influenceLevel);
                if(kkt>MIN_CALC_COLOR_K){
                    //System.out.println("I am here");
                    Ray refractedRay = constructRefractedRay(intersection, inRay);
                    Intersectable.GeoPoint refractedPoint = findClosestPoint(refractedRay);
                    if (refractedPoint!=null) {
                        color = color.add(calcColor(refractedPoint, refractedRay, level - 1, kkt).scale(ktr * kTransparency));
                        //return color;
                      // System.out.println("I am here");
                    }
                }
            }
        }
        return color;
    }*/

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
        if(numSuperSampling==1)
            imageWriter.writeToImage();
        else {
            Supersampling sps = new Supersampling((int)imageWriter.getWidth(), (int)imageWriter.getHeight(), numSuperSampling);
            imageWriter.writeToImage(sps.superSamplingImprovement(imageWriter.getImage()));
        }
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

    /**
     * Inner class for the renderer use for supersampling only
     */
    private class Supersampling {
        /**
         * Constructor for the Supersampling object, by width,
         * height and num of additional rays added in renderer
         * @param _outW
         * @param _outH
         * @param samples
         */
        public Supersampling(int _outW, int _outH, int samples) {
            outWidth = _outW*2;
            outHeight = _outH*2;
            nSamples = samples;
        }

        /**
         * Function that makes the supersampling effect by making the average color of supersampling^2
         * pixels and saving it to the new image
         * @param image
         * @return
         */
        public BufferedImage superSamplingImprovement(BufferedImage image) {
            BufferedImage output = new BufferedImage(image.getColorModel(), image.getColorModel().createCompatibleWritableRaster(outWidth, outHeight), false, new Hashtable<String, Object>());
            WritableRaster sourceRaster = image.getRaster();
            WritableRaster outRaster = output.getRaster();
            int sourceNumBands = sourceRaster.getNumBands();

            for(int x = 0; x < outRaster.getWidth(); x++) {
                for(int y = 0; y < outRaster.getHeight(); y++) {
                    double[] newValues = new double[sourceNumBands];

                    for(int i = 0; i < nSamples; i++) {
                        for(int j = 0; j < nSamples; j++) {
                            for(int k = 0; k < sourceNumBands; k++) {

                                // System.out.println("x*_samples+i= " + x*_samples+i +" i= "+i);
                                // System.out.println("y*_samples+j= " + y*_samples+j +" j= "+j);
                                // System.out.println("*****************************************");
                                try {
                                    newValues[k] += sourceRaster.getSample(x * nSamples + i, y * nSamples + j, k);

                                }
                                catch (Exception ex){
                                    System.out.println("width = " + sourceRaster.getWidth());
                                    System.out.println("height= "+ sourceRaster.getHeight());
                                    System.out.println("samples= "+ nSamples);
                                    //System.out.println("SystemModelTranslateY" + sourceRaster.getSys);
                                    throw ex;
                                }
                            }
                        }
                    }

                    for(int i = 0; i < newValues.length; i++) {
                        newValues[i] = newValues[i]/(nSamples * nSamples);
                        outRaster.setSample(x, y, i, newValues[i]);
                    }


                }
            }

            return output;
        }

        private int outWidth, outHeight, nSamples;

    }
}