/**
 * [1968] - [2023] Centros Culturales de Mexico A.C / Universidad Panamericana
 * All Rights Reserved.
 */
package edu.up.isgc.cg.raytracer.lights;

import edu.up.isgc.cg.raytracer.Intersection;
import edu.up.isgc.cg.raytracer.Ray;
import edu.up.isgc.cg.raytracer.Vector3D;
import edu.up.isgc.cg.raytracer.objects.Object3D;
import java.awt.*;

/**
 * @author Jafet Rodríguez
 * @Description Here we declare atributes all lights have, so they can take them
 */

public abstract class Light extends Object3D {

    private double intensity;

    /**
     *
     * @param position Where the light is
     * @param color Color of the light
     * @param intensity How intense the light is
     */
    public Light(Vector3D position, Color color, double intensity) {
        super(position, color, null);
        setIntensity(intensity);
    }

    /**
     *
     * @return The intensity
     */
    public double getIntensity() {
        return intensity;
    }

    /**
     *
     * @param intensity how intense is the light going to be
     */
    public void setIntensity(double intensity) {
        this.intensity = intensity;
    }

    /**
     *
     * @param intersection Where the light hits an object
     * @return The multiplication of N dot L
     */
    public abstract double getNDotL(Intersection intersection);

    /**
     *
     * @param ray A ray you cast
     * @return The intersection where the ray hits
     */
    public Intersection getIntersection(Ray ray) {
        return new Intersection(Vector3D.ZERO(), -1, Vector3D.ZERO(), null);
    }

}
