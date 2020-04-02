package primitives;
import primitives.Coordinate;

/**
 * class Point 3D is representation of Point in 3-dimension. Can't be heritated
 * authors Polina Frolov Korogodsky and Tselia Tebol
 */
public final class Point3D {
    private Coordinate x;
    private Coordinate y;
    private Coordinate z;
    //public static Point3D zero(0.0, 0.0, 0.0);
    /**
     * assigns a point values to values of x, y, z
     * @param x
     * @param y
     * @param z
     */
    public Point3D(Coordinate x, Coordinate y, Coordinate z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Point3D(double _x, double _y, double _z) {
        this(new Coordinate(_x), new Coordinate(_y), new Coordinate(_z));
    }

    public Point3D(Point3D point) {
        this(point.getX(), point.getY(), point.getZ());
    }

    //public Point3D() {
    //    this(new Coordinate (0.0), new Coordinate (0.0),new Coordinate (0.0));
   // }

    /**
     * returns x value
     * @return Coordinate x
     */
    public Coordinate getX() {
        return x;
    }
    /**
     * returns y value
     * @return Coordinate y
     */
    public Coordinate getY() {
        return y;
    }
    /**
     * returns y value
     * @return Coordinate y
     */
    public Coordinate getZ() {
        return z;
    }

    /**
     * Checks whether an object is equal to current point
     * @param o
     * @return boolean true if the points are equal or false in an object is not 3D point or the points are not equal returns false
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Point3D)) return false;

        Point3D point3D = (Point3D) o;

        if (getX() != null ? !getX().equals(point3D.getX()) : point3D.getX() != null) return false;
        if (getY() != null ? !getY().equals(point3D.getY()) : point3D.getY() != null) return false;
        return getZ() != null ? getZ().equals(point3D.getZ()) : point3D.getZ() == null;
    }

    /**
     * prints a point values in the format "Point3D:(x, y, z)
     * @return String
     */
    @Override
    public String toString() {
        return "Point3D:(" +
                x +
                "," + y +
                "," + z +
                ')';
    }
    public static Point3D zero = new Point3D(0.0, 0.0, 0.0);
    public Vector subtract (Point3D point){
        return new Vector(new Point3D(-point.getX()._coord + this.getX()._coord, -point.getY()._coord + this.getY()._coord, -point.getZ()._coord + this.getZ()._coord));
    }
    public Point3D add (Vector vec){
        return new Point3D(this.getX()._coord+vec.getEnd().getX()._coord, this.getY()._coord+vec.getEnd().getY()._coord, this.getZ()._coord+vec.getEnd().getZ()._coord);
    }
    public double squaredDistance (Point3D point){
        return this.subtract(point).length()*this.subtract(point).length();
    }
    public double distance (Point3D point){
        return Math.sqrt(this.squaredDistance(point));
    }
}
