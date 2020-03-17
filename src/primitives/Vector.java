package primitives;
//import primitives.Point3D;
/**
 * basic representation of vector in 3d
 * authors Polina Frolov Korogodsky and Tselia Tebol
 */
import java.lang.*;
public class Vector {

    private Point3D end;

    /**
     * constructor with parameteres
     * @param end
     */
    public Vector(Point3D end) {
        if(end.equals(Point3D.zero))
            throw new IllegalArgumentException("Zero vector can't be used");
        this.end = end;
    }

    /**
     * Constructor receives three double
     * @param _x
     * @param _y
     * @param _z
     */
    public Vector (double _x, double _y, double _z){
        this(new Point3D(_x, _y, _z));
    }

    /**
     * Constructor receives vector
     * @param  vec
     */
    public Vector (Vector vec){
        this(vec.getEnd());
    }
    /**
     * Constructor receives 3 coordinates
     * @param _x
     * @param _y
     * @param _z
     */
    public Vector(Coordinate _x, Coordinate _y, Coordinate _z) {
    this(new Point3D (_x, _y, _z));
    }

    /**
     *
     * @returns end point
     */
    public Point3D getEnd() {
        return end;
    }

    /**
     * Checks if an object is equal to current vector
     * @param o
     * @return boolean
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vector)) return false;

        Vector vector = (Vector) o;

        return getEnd() != null ? getEnd().equals(vector.getEnd()) : vector.getEnd() == null;
    }

    /**
     * prints a vector value
     * @return string
     */
    @Override
    public String toString() {
        return "Vector: end point: "
                + end.toString();
    }

    /**
     * normalization of vector via end Point
     * @return end point of normalized vector
     */
    public Vector normalize() {
        double distance = this.length();
        if (distance != 1) {
            double ex = (end.getX()._coord) / distance;
            double ya = (end.getY()._coord) / distance;
            double zed = (end.getZ()._coord) / distance;
            Point3D point = new Point3D(new Coordinate(ex), new Coordinate(ya), new Coordinate(zed));
            this.end = point;
           //            this. = new Vector(point);
        }
        return this;
    }
public Vector normalized(){
        return new Vector(this.normalize());
}
    /**
     * returns the square of vector's length
     * @return double
     */
    public double lengthSquared(){
      return (end.getX()._coord)*(end.getX()._coord) + (end.getY()._coord)*(end.getY()._coord) + (end.getZ()._coord)*(end.getZ()._coord);
    }

    /**
     * returns a length of vector
     * @return double
     */
    public double length(){
        return Math.sqrt(this.lengthSquared());
    }

    /**
     * returns the sum of 2 vectors
     * @param vec
     * @return
     */
    public Vector add (Vector vec){
        return  new Vector (this.end.getX()._coord+vec.end.getX()._coord, this.end.getY()._coord+vec.end.getY()._coord, this.end.getZ()._coord+vec.end.getZ()._coord);
    }

    /**
     * subtracts two vectors
     * @param vec
     * @return
     */
    public Vector subtract (Vector vec){
        return  new Vector (this.end.getX()._coord-vec.end.getX()._coord, this.end.getY()._coord-vec.end.getY()._coord, this.end.getZ()._coord-vec.end.getZ()._coord);
    }

    /**
     * Dot Product of two vectors
     * @param vec
     * @return
     */
    public double dotProduct (Vector vec){
        return this.end.getX()._coord*vec.end.getX()._coord + this.end.getY()._coord*vec.end.getY()._coord +this.end.getZ()._coord*vec.end.getZ()._coord;
    }

    /**
     * Cross Product of two vectors
     * @param vec
     * @return
     */
    public Vector crossProduct (Vector vec){
        double newX = (this.end.getY()._coord * vec.end.getZ()._coord)-(this.end.getZ()._coord * vec.end.getY()._coord);
        double newY = -((this.end.getX()._coord * vec.end.getZ()._coord)-(this.end.getZ()._coord * vec.end.getX()._coord));
        double newZ = (this.end.getX()._coord * vec.end.getY()._coord)-(this.end.getY()._coord * vec.end.getX()._coord);
        return new Vector(newX, newY, newZ);
    }

}
