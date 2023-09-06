package edu.up.isgc.cg.raytracer.lights;

import edu.up.isgc.cg.raytracer.Intersection;
import edu.up.isgc.cg.raytracer.Vector3D;
/**
 * @author Diego Jiménez
 * @Description Here we construct the Point light
 */
import java.awt.*;

public class PointLight extends Light{
    /**
     *
     * @param position Where the light is
     * @param color The color of the light
     * @param intensity How intense is the light
     */
    public PointLight(Vector3D position, Color color, double intensity) {
        super(position, color, intensity);
    }

    /**
     *
     * @param intersection Where the light hits an object
     * @return The N dot L result
     */
    @Override
    public double getNDotL(Intersection intersection) {
        return Math.max(Vector3D.dotProduct(intersection.getNormal(), Vector3D.normalize(Vector3D.substract(getPosition(), intersection.getPosition()))), 0.0);
    }
}
