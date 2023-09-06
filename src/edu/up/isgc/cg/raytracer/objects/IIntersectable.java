/**
 * [1968] - [2023] Centros Culturales de Mexico A.C / Universidad Panamericana
 * All Rights Reserved.
 */
package edu.up.isgc.cg.raytracer.objects;

import edu.up.isgc.cg.raytracer.Intersection;
import edu.up.isgc.cg.raytracer.Ray;

/**
 * @author Jafet Rodríguez
 */
public interface IIntersectable {
    /**
     *
     * @param ray The ray we cast
     * @return If there is an intersection with an object
     */
    Intersection getIntersection(Ray ray);
}
