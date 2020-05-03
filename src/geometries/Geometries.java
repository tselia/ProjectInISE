package geometries;

import primitives.Point3D;
import primitives.Ray;

import java.util.ArrayList;
import java.util.List;

/**
 * Class Geometries is a basic representation of some amount of geometries united with each other
 * authors Polina Frolov Korogodsky and Tselia Tebol
 */
public class Geometries implements Intersectable {
    private List<Intersectable> elements;

    /**
     * Default constructor
     */
    public Geometries() {
        elements = new ArrayList<Intersectable>(); //the ArrayList was chosen because the counts take lots of time, and this time is much more critical than the memory
    }

    /**
     * Constructor that adds specified geometries to the list, uses default constructor and add() function to implement the DRY principle
     * @param geometries (Intersectable)
     */
    public Geometries(Intersectable ... geometries) {
        this();
        add(geometries);
    }

    /**
     * function that adds some amount of Intersectables to specific union (Geometries object)
     * @param geometries
     */
    public void add(Intersectable... geometries){
        for(int i=0; i<geometries.length; i++)
            elements.add(geometries[i]);
    }

    /**
     * function that finds the intersection points of all the objects included in the elements' list
     * @param ray (Ray)
     * @return List<Point3D>
     */
    @Override
    public List<Point3D> findIntersections(Ray ray) {
        int size = elements.size();
        List<Point3D> intersections = new ArrayList<Point3D>();
        for(int i=0; i<size; i++)
        {
            List<Point3D> currentIntersections = elements.get(i).findIntersections(ray);
            if (currentIntersections!=null) {
                int curSize = currentIntersections.size();
                System.out.println(curSize);
                //if(intersections==null)
                for (int j=0; j<curSize; j++){
                    intersections.add(currentIntersections.get(j));
                }
            }
        }
       // if(intersections.isEmpty())
         //   System.out.println("isEmpty");
        //else System.out.println(intersections.size());
        if (intersections.isEmpty())
            return null;
        else return intersections;

    }
    public List<Intersectable> getElements(){
        return elements;
    }
}
