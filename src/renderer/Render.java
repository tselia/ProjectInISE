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

    private int _threads= 1;
    private final int SPARE_THREADS= 2; // Spare threads if trying to use all the cores
    private boolean _print= false; // printing progress percentage
    /*** Set multithreading <br>* -if the parameter is 0 -number of coressless SPARE (2) is taken
     * @param threads number of threads
     * @return the Render object itself*/
    public Render setMultithreading(int threads)
    {if(threads< 0) throw new IllegalArgumentException("Multithreading must be 0 or higher");
        if(threads!= 0) _threads= threads;
        else {int cores= Runtime.getRuntime().availableProcessors() -SPARE_THREADS;
            _threads= cores<= 2 ? 1 : cores;}
        return this;}
    /*** Set debug printing on
     * @return the Render object itself*/
    public Render setDebugPrint() { _print= true;
        return this; }

    /**
     * Constructor without supersampling effect, it reffers to another constructor
     * and puts 1 as supersampling level
     * @param scene (Scene)
     * @param imageWriter (ImageWriter)
     */
    public Render(Scene scene, ImageWriter imageWriter) {
        this(scene, imageWriter, 1);
    }

    /**
     * Constructor without supersampling effect, it reffers to another constructor
     * and puts 1 as supersampling level
     * @param imageWriter
     * @param scene
     */
    public Render(ImageWriter imageWriter, Scene scene) {
        this(scene, imageWriter, 1);
    }

    /**
     * Constructor that receives ImageWriter, Scene and num of rays for Supersampling
     * @param imgwrt
     * @param scene
     * @param _nS
     */
    public Render(ImageWriter imgwrt, Scene scene, int _nS){
        this.scene = scene;
        this.imageWriter = imgwrt;
        numSuperSampling = _nS;
    }

    /**
     * Constructor that receives ImageWriter, Scene and num of rays for Supersampling
     * @param imgwrt
     * @param scene
     * @param _nS
     */
    public Render(Scene scene, ImageWriter imgwrt, int _nS){
        this(imgwrt, scene, _nS);
    }
    /**
     * The function that saves the 3D scene's 2D representation in matrix
     */
    public void renderImageWithThreads() {

            this.imageWriter = new ImageWriter(imageWriter.getImageName(), imageWriter.getWidth(), imageWriter.getHeight(), imageWriter.getNx()*numSuperSampling, imageWriter.getNy()*numSuperSampling);
            java.awt.Color background = scene.getBackground().getColor();
            Camera camera = scene.getCamera();
           // Intersectable geometries = scene.getGeometries();
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
                    final Pixel thePixel= new Pixel(Ny, Nx); // Main pixel management object
                    Thread[] threads= new Thread[_threads];
                    for(int i= _threads-1; i>= 0; --i) {
                        int finalColumn = column;
                        int finalRow = row;
                        threads[i] = new Thread(() -> {
                            Pixel pixel= new Pixel();
                            while(thePixel.nextPixel(pixel)) {
                                final Ray CurrRay = camera.constructRayThroughPixel(Nx, Ny, finalColumn, finalRow, distance, width, height);
                                List<Intersectable.GeoPoint> intersectionPoints = scene.getGeometries().findIntersections(CurrRay);
                                if (intersectionPoints == null) {
                                    imageWriter.writePixel(finalColumn, finalRow, background);
                                } else {
                                    Intersectable.GeoPoint closestPoint = getClosestPoint(intersectionPoints);
                                    imageWriter.writePixel(finalColumn/*-1*/, finalRow/*-1*/, calcColor(closestPoint, CurrRay).getColor());
                                }

                            }
                        });


                    }
                    for(Thread thread: threads) thread.start();
                    for(Thread thread: threads) try{ thread.join(); } catch(Exception e) {}
                    if(_print) System.out.printf("\r100%%\n");
                }
//                    ray = camera.constructRayThroughPixel(Nx, Ny, column, row, distance, width, height);
//                    List<Intersectable.GeoPoint> intersectionPoints = scene.getGeometries().findIntersections(ray);
//                    if (intersectionPoints == null) {
//                        imageWriter.writePixel(column, row, background);
//                    } else {
//                        Intersectable.GeoPoint closestPoint = getClosestPoint(intersectionPoints);
//                        imageWriter.writePixel(column/*-1*/, row/*-1*/, calcColor(closestPoint, ray).getColor());
//                    }
                //}
            }


    }

    /**
     * renderImage function that does not use threads
     * (for more confidence, it the thread version will not work)
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
        private int maxPictureScalingLevel = 2;
        /**
         * Constructor for the Supersampling object, by width,
         * height and num of additional rays added in renderer
         * @param _outW
         * @param _outH
         * @param samples
         */
        //private BufferedImage imageForImprovement;
        public Supersampling(int _outW, int _outH, int samples) {
            outWidth = _outW* maxPictureScalingLevel;
            outHeight = _outH*maxPictureScalingLevel;
            nSamples = samples;
        }

        public BufferedImage superSamplingImprovementWithoutAcceleration(BufferedImage image) {
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

            for(int x = 0; x < outRaster.getWidth(); x+=nSamples) {
                for(int y = 0; y < outRaster.getHeight(); y+=nSamples) {
                    double[] newValues = new double[sourceNumBands];
                    for(int k = 0; k < sourceNumBands; k++){
                        int pixel[][] = new int[nSamples][nSamples];
                        for(int i=0; i<nSamples; i++)
                            for (int j=0; j<nSamples; j++)
                                pixel[i][j]=sourceRaster.getSample(x * nSamples + i, y * nSamples + j, k);
                                newValues[k]=adaptiveSuperSampling(pixel, x, y, nSamples);

                    }


                    for(int i = 0; i < newValues.length; i++) {

                            outRaster.setSample(x, y, i, newValues[i]);
                    }


                }
            }

            return output;
        }

        private int outWidth, outHeight, nSamples;
        private int maxDepth;

        double adaptiveSuperSampling(int[][] pixel, int x, int y,  int counter){
            maxDepth=nSamples;
            int pixSize = pixel.length;
            if(pixel[0][pixSize-1]==pixel[0][0]&&
                    pixel[0][0]==pixel[pixSize-1][0]&&
                    pixel[0][0]==pixel[pixSize-1][pixSize-1] ||
                    counter==maxDepth) {

                return pixel[0][0];
            }
            else {
                counter+=1;
                int  topLeft [][];
               int topRight[][];
               int downLeft[][];
               int downRight[][];

               if (pixSize%2==0) {
                   topRight = new int[pixSize / 2][pixSize / 2];
                   topLeft =new int[pixSize/2][pixSize/2];
                   downRight=new int[pixSize/2][pixSize/2];
                   downLeft=new int[pixSize/2][pixSize/2];
               }
               else {topRight=new int[(pixSize/2)+1][(pixSize/2)+1];
                    topLeft = new int[(pixSize/2)+1][(pixSize/2)+1];
                downLeft = new int[(pixSize/2)+1][(pixSize/2)+1];
               downRight = new int[(pixSize/2)+1][(pixSize/2)+1];}

                for(int copyI=0; copyI<pixSize/2; copyI++){
                    for (int copyJ=0; copyJ<pixSize/2; copyJ++){

                        topLeft[copyI][copyJ]=pixel[copyI][copyJ];
                    }
                }
                for(int copyI=pixSize/2; copyI<pixSize; copyI++){
                    for (int copyJ=0; copyJ<pixSize/2; copyJ++){

                        topRight[copyI-pixSize/2][copyJ]=pixel[copyI][copyJ];
                    }
                }
                for(int copyI=0; copyI<pixSize/2; copyI++){
                    for (int copyJ=pixSize/2; copyJ<pixSize; copyJ++){

                        downLeft[copyI][copyJ-pixSize/2]=pixel[copyI][copyJ];
                    }
                }
                for(int copyI=pixSize/2; copyI<pixSize; copyI++){
                    for (int copyJ=pixSize/2; copyJ<pixSize; copyJ++){

                        downRight[copyI-pixSize/2][copyJ-pixSize/2]=pixel[copyI][copyJ];
                    }
                }
                return (adaptiveSuperSampling(topLeft, x, y, counter)+
                        adaptiveSuperSampling(topRight, x+nSamples/counter, y,  counter)
                        +adaptiveSuperSampling(downLeft, x, y+nSamples/counter, counter)
                        +adaptiveSuperSampling(downRight, x+nSamples/counter, y+nSamples/counter, counter))/4;
            }
        }

    }
    private class Pixel{
        private long _maxRows= 0;     // Ny
        private long _maxCols= 0; // Nx
        private long _pixels= 0;// Total number of pixels: Nx*Ny
        public volatile int row= 0;// Last processed row
        public volatile int col= -1;// Last processed column
        private long _counter= 0;// Total number of pixels processed
        private int _percents= 0;// Percent of pixels processed
        private long _nextCounter= 0;// Next amount of processed pixels for percent progress
        /*** The constructor for initializing the main follow up Pixel object
         * @parammaxRowsthe amount of pixel rows
         * @parammaxColsthe amount of pixel columns*/
        public Pixel(int maxRows, int maxCols) {
            _maxRows= maxRows;
            _maxCols= maxCols;
            _pixels= maxRows* maxCols;
            _nextCounter= _pixels/ 100;
            if(Render.this._print)
                System.out.printf("\r %02d%%", _percents);}
        /***  Default constructor for secondary Pixel objects*/
        public Pixel() {}
        /*** Public function for getting next pixel number into secondary Pixel object.
         *  The function prints also progress percentage in the console window.
         *  @param target targetsecondary Pixel object to copy the row/column of the next pixel
         *  @returntrue if the work still in progress, -1 if it's done*/
        public boolean nextPixel(Pixel target)
        {
            int percents= nextP(target);
            if(_print&& percents> 0)
                System.out.printf("\r %02d%%", percents);
            if(percents>= 0)
                return true;
            if(_print)
                System.out.printf("\r %02d%%", 100);
            return false;
        }

        /*** Internal function for thread-safe manipulating of main follow up Pixel object
         * -this function is critical section for all the threads,
         * and main Pixel object data is the shared data of this critical section.<br/>
         * The function provides next pixel number each call.
         * @param target targetsecondary Pixel object to copy the row/column of the next pixel
         * @return the progress percentage for follow up: if it is 0 -nothing to print,
         * if it is -1 -the task is finished, any other value -the progress percentage
         * (only when it changes)*/
        private synchronized int nextP(Pixel target) {
            ++col;
            ++_counter;
            if(col< _maxCols)
            {target.row= this.row;
                target.col= this.col;
                if(_print&& _counter== _nextCounter) {
                    ++_percents;
                    _nextCounter= _pixels* (_percents+ 1) / 100;
                    return _percents;
                }
                return 0;}
            ++row;
            if(row< _maxRows) {
                col= 0;
                if(_print&& _counter== _nextCounter) {
                    ++_percents;
                    _nextCounter= _pixels* (_percents+ 1) / 100;
                    return _percents;}
                return 0;}
            return -1;}
    }


}