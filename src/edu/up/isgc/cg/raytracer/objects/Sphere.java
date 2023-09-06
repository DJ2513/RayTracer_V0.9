/**
 * [1968] - [2023] Centros Culturales de Mexico A.C / Universidad Panamericana
 * All Rights Reserved.
 */
package edu.up.isgc.cg.raytracer.objects;

import edu.up.isgc.cg.raytracer.Intersection;
import edu.up.isgc.cg.raytracer.Material;
import edu.up.isgc.cg.raytracer.Ray;
import edu.up.isgc.cg.raytracer.Vector3D;

import java.awt.*;

/**
 * @author Jafet Rodríguez
 * @decription Here we create the Spheres
 */
public class Sphere extends Object3D{
    private double radius;

    /**
     *
     * @param position Where it is in the scene
     * @param radius The radius of the circle
     * @param color Color of the circle
     * @param material Coefficients that affects the object
     */
    public Sphere(Vector3D position, double radius, Color color, Material material) {
        super(position, color, material);
        setRadius(radius);
    }

    /**
     *
     * @return The radius
     */
    public double getRadius() {
        return radius;
    }

    /**
     *
     * @param radius The radius of the object
     */
    public void setRadius(double radius) {
        this.radius = radius;
    }

    /**
     *
     * @param ray The ray we cast
     * @return The intersection between the circle and the ray
     */
    @Override
    public Intersection getIntersection(Ray ray) {
        Vector3D L = Vector3D.substract(ray.getOrigin(), getPosition());
        double tca = Vector3D.dotProduct(ray.getDirection(), L);
        double L2 = Math.pow(Vector3D.magnitude(L), 2);
        //Intersection
        double d2 = Math.pow(tca, 2) - L2 + Math.pow(getRadius(), 2);
        if(d2 >= 0){
            double d = Math.sqrt(d2);
            double t0 = -tca + d;
            double t1 = -tca - d;

            double distance = Math.min(t0, t1);
            Vector3D position = Vector3D.add(ray.getOrigin(), Vector3D.scalarMultiplication(ray.getDirection(), distance));
            Vector3D normal = Vector3D.normalize(Vector3D.substract(position, getPosition()));
            return new Intersection(position, distance, normal, this);
        }

        return null;
    }
}
