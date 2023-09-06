/**
 * [1968] - [2023] Centros Culturales de Mexico A.C / Universidad Panamericana
 * All Rights Reserved.
 */
package edu.up.isgc.cg.raytracer;

import edu.up.isgc.cg.raytracer.objects.Object3D;

/**
 * @author Jafet Rodríguez
 * @Dexcription Here we calculate when, where and if a ray intersectswith an object
 */
public class Intersection {
    private double distance;
    private Vector3D normal;
    private Vector3D position;
    private Object3D object;

    /**
     *
     * @return the distance between the objects
     */
    public double getDistance() {
        return distance;
    }

    /**
     *
     * @param distance distance between the objects
     */
    public void setDistance(double distance) {
        this.distance = distance;
    }

    /**
     *
     * @return the normal
     */
    public Vector3D getNormal() {
        return normal;
    }

    /**
     *
     * @param normal the normal of the object you interrsected with
     */
    public void setNormal(Vector3D normal) {
        this.normal = normal;
    }

    /**
     *
     * @return the position of the intersection
     */
    public Vector3D getPosition() {
        return position;
    }

    /**
     *
     * @param position position of the intersection
     */
    public void setPosition(Vector3D position) {
        this.position = position;
    }

    /**
     *
     * @return the object you intersected with
     */
    public Object3D getObject() {
        return object;
    }

    /**
     *
     * @param object the object you intersected with
     */
    public void setObject(Object3D object) {
        this.object = object;
    }

    /**
     *
     * @param position where it intersected an object
     * @param distance the distance between the objects
     * @param normal Normal of the surface you intersected with
     * @param object The object you intersected width
     */
    public Intersection(Vector3D position, double distance, Vector3D normal, Object3D object) {
        setPosition(position);
        setDistance(distance);
        setNormal(normal);
        setObject(object);
    }
}
