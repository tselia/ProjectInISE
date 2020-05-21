package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

/**
 * Interface LightSource is a base for describing various light source
 * Authors - Polina Frolov Korogodsky and Tselia Tebol
 */
public interface LightSource {
    /**
     * Function that will return the color of specific point on specific geometry
     * @param point
     * @return primitives.Color
     */
    public Color getIntensity(Point3D point);

    /**
     * function that return a direction of light for the specific point
     * @param point
     * @return Vector
     */
    public Vector getL(Point3D point);

    /**
     * function returns a distance from lightSource object to point sent as parameter
     * @param point
     * @return
     */
    double getDistance(Point3D point);
}
