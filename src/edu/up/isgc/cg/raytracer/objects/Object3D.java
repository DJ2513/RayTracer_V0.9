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
 * @description The father class of all objects
 */
public abstract class Object3D implements IIntersectable{
    private Color color;
    private Vector3D position;
    private Material material;

    /**
     *
     * @param position Where it is
     * @param color What color it has
     * @param material Coefficients that affects the object
     */
    public Object3D(Vector3D position, Color color, Material material) {
        setPosition(position);
        setColor(color);
        setMaterial(material);
    }

    /**
     *
     * @return The Material
     */
    public Material getMaterial() {
        return material;
    }

    /**
     *
     * @param material The material of the object
     */
    public void setMaterial(Material material) {
        this.material = material;
    }

    /**
     *
     * @return The color of the object
     */
    public Color getColor() {
        return color;
    }

    /**
     *
     * @param color The color of the object
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     *
     * @return The position of the object
     */
    public Vector3D getPosition() {
        return position;
    }

    /**
     *
     * @param position The position of the object
     */
    public void setPosition(Vector3D position) {
        this.position = position;
    }

}
