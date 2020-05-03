
    package elements;
/**
 * class camera is a representation of he camera with one point and 3 vectors
 * authors Polina Frolov Korogodsky and Tselia Tebol
 */

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;
import static primitives.Util.isZero;

    public class Camera {

        Point3D p0;
        Vector vUp;
        Vector vTo;
        Vector vRight;

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

            return new Ray(p0, Vij/*new Vector(Vij.getEnd().getX(), Vij.getEnd().getZ(), Vij.getEnd().getY()).scale(-1)/*Vij*/);

        }


    }