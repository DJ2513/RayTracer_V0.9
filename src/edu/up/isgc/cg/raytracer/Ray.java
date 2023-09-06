/**
 * [1968] - [2023] Centros Culturales de Mexico A.C / Universidad Panamericana
 * All Rights Reserved.
 */
package edu.up.isgc.cg.raytracer;

import edu.up.isgc.cg.raytracer.Vector3D;

/**
 * @author Jafet Rodríguez
 * @description Here we create the rays we cast
 */
public class Ray {
    private Vector3D origin;
    private Vector3D direction;

    /**
     *
     * @param origin where it is cast
     * @param direction where it goes
     */
    public Ray(Vector3D origin, Vector3D direction) {
        setOrigin(origin);
        setDirection(direction);
    }
    /**
     *
     * @return the origin of the ray
     */
    public Vector3D getOrigin() {
        return origin;
    }

    /**
     *
     * @param origin the origin of the ray
     */
    public void setOrigin(Vector3D origin) {
        this.origin = origin;
    }

    /**
     *
     * @return the direction of the ray
     */
    public Vector3D getDirection() {
        return Vector3D.normalize(direction);
    }

    /**
     *
     * @param direction the direction of the ray
     */
    public void setDirection(Vector3D direction) {
        this.direction = direction;
    }
}
