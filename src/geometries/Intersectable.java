package geometries;
import primitives.*;
import java.util.*;

/**
 * Interface Intersectable is an interface that enables all geometries to find and return their intersection
 * points with ray
 * Authors - Polina Frolov Korogodsky and Tselia Tebol
 */
public interface Intersectable {
    /**
     * A class that connects the point to the geometry that to it's surface the point belongs
     */
    static class GeoPoint {//there is an access problem
        protected final Geometry geometry;
        protected final Point3D point;

        /**
         * Constructs a GeoPoint object by parameters
         * @param geometry
         * @param point
         */
        public GeoPoint(Geometry geometry, Point3D point) {
            this.geometry = geometry;
            this.point = point;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof GeoPoint)) return false;

            GeoPoint geoPoint = (GeoPoint) o;


            if (!Objects.equals(geometry, geoPoint.geometry)) return false;
            return point.equals(geoPoint.point);
        }

        public Geometry getGeometry() {
            return geometry;
        }

        public Point3D getPoint() {
            return point;
        }
    }
    /**
     * The method of getting the intersection points with the ray
     * @param ray
     * @return
     */
    List<GeoPoint> findIntersections(Ray ray);






}