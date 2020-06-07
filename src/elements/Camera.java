
    package elements;
/**
 * class camera is a representation of he camera with one point and 3 vectors
 * authors Polina Frolov Korogodsky and Tselia Tebol
 */

import geometries.Plane;
import geometries.Polygon;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.ArrayList;
import java.util.List;

import static primitives.Util.isZero;

    public class Camera {

        Point3D p0;
        Vector vUp;
        Vector vTo;
        Vector vRight;
        //Polygon focus;

        /**
         * constructor with parameteres
         * @param p0
         * @param vUp
         * @param vTo
         */
        public Camera(Point3D p0, Vector vUp, Vector vTo) {
            // verify if the vectors are orthogonal
            if(vTo.dotProduct(vUp)!=0)
                throw new IllegalArgumentException ("vectors have to be orthogonal");

            this.p0 = p0;
            this.vUp = vUp.normalize();
            this.vTo = vTo.normalize();

            vRight = this.vTo.crossProduct(vUp).normalize();
        }

        /**
         * camera point get method
         * @return p0
         */
        public Point3D getP0() {
            return p0;
        }

        /**
         * vector vUp get method
         * @return vUp
         */
        public Vector getvUp() {
            return vUp;
        }

        /**
         * vector vTo get method
         * @return vTo
         */
        public Vector getvTo() {
            return vTo;
        }

        /**
         * vector vRight get method
         * @return vRight
         */
        public Vector getvRight() {
            return vRight;
        }

        /**
         * function that shuts rays through pixel in view plane
         * @param nX
         * @param nY
         * @param j
         * @param i
         * @param screenDistance
         * @param screenWidth
         * @param screenHeight
         * @return Ray
         */
        public Ray constructRayThroughPixel (int nX, int nY, int j, int i, double screenDistance,
                                             double screenWidth, double screenHeight)
        {
            //return null ;
            if (screenDistance<=0) {

                throw new IllegalArgumentException("Screen distance should be a positive number");
            }
            Point3D Pc = p0.add(vTo.scale(screenDistance));// first line of presentation


            double rX = screenWidth/nX ;
            double rY = screenHeight/nY ;

            double yi =  ((i - nY/2d)*rY + rY/2d);//next line
            double xj=   ((j - nX/2d)*rX + rX/2d);

            Point3D Pij = Pc ;

            if (! isZero(xj))
            {
                Pij = Pij.add(vRight.scale(xj));
            }
            if (! isZero(yi))
            {
                Pij = Pij.add(vUp.scale(-yi));
            }

            Vector Vij = Pij.subtract(p0);

            return new Ray(p0, Vij);

        }

        public List<Ray> constructMultipleRaysThroughPixel(int nX, int nY, int j, int i, double screenDistance,
                                                           double screenWidth, double screenHeight, int nRays,
                                                           /*double gridLength,*/ Point3D focalPoint){
            //double distance = gridLength/nRays;
            Ray mainRay = this.constructRayThroughPixel(nX, nY, j, i, screenDistance, screenWidth, screenHeight);
            Plane viewPlane = new Plane(p0.add(vTo.scale(screenDistance)), vTo);

            Point3D centerOfPixel = viewPlane.findIntersections(mainRay).get(0).getPoint();

            //getting the left/ right pixel's center
            double distanceToTheNearestPixel = 0;
            if(i!=0){
                Ray leftRay = this.constructRayThroughPixel(nX, nY, j, i-1, screenDistance, screenWidth, screenHeight);
                Point3D leftCenter = viewPlane.findIntersections(leftRay).get(0).getPoint();
                distanceToTheNearestPixel = centerOfPixel.distance(leftCenter)/2;
            }
            else {
                Ray rightRay = this.constructRayThroughPixel(nX, nY, j, i+1, screenDistance, screenWidth, screenHeight);
                Point3D rightCenter = viewPlane.findIntersections(rightRay).get(0).getPoint();
                distanceToTheNearestPixel=centerOfPixel.distance(rightCenter)/2;

            }
            double distance = distanceToTheNearestPixel*2;
            List<Ray> rays = new ArrayList<>();
            //boolean rayNumEven = nRays/2==0;
            for(int rayNum=0; rayNum<nRays; rayNum++) {
                double coefficient = rayNum * distance;

                    if (rayNum > nRays / 2)
                        coefficient = -coefficient;

                Point3D startOfRay = centerOfPixel.add(vTo.scale(0.1));
                if(coefficient!=0)
                    startOfRay = centerOfPixel.add(vRight.scale(coefficient));
                rays.add(new Ray(startOfRay, startOfRay.subtract(focalPoint)));
            }
            return rays;


        }



    }