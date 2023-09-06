package edu.up.isgc.cg.raytracer;


/**
 * @author Diego Jimenez
 * @description Here we build the materials
 */
public class Material {
    private double ambientLight;
    private double diffuseLight;
    private double shininess;
    private double reflection;
    private double refraction;

    /**
     *
     * @param ambientLight How much ambient light the object absorbs
     * @param diffuseLight how much light it will diffuse
     * @param shininess How shiny the object will be
     * @param reflection Coefficient that tells how reflective it will be
     * @param refraction Coefficient that tells how refractive it will be
     */
    public Material(double ambientLight, double diffuseLight, double shininess,double reflection, double refraction) {
        setAmbientLight(ambientLight);
        setDiffuseLight(diffuseLight);
        setShininess(shininess);
        setReflection(reflection);
        setRefraction(refraction);
    }

    /**
     *
     * @return The ambient coefficient
     */
    public double getAmbientLight() {
        return ambientLight;
    }

    /**
     *
     * @param ambientLight The ambient coefficient
     */
    public void setAmbientLight(double ambientLight) {
        this.ambientLight = ambientLight;
    }

    /**
     *
     * @return The diffuse coefficient
     */
    public double getDiffuseLight() {
        return diffuseLight;
    }

    /**
     *
     * @param diffuseLight The diffuse coefficient
     */
    public void setDiffuseLight(double diffuseLight) {
        this.diffuseLight = diffuseLight;
    }

    /**
     *
     * @return The diffuse coefficient
     */
    public double getShininess() {
        return shininess;
    }

    /**
     *
     * @param shininess The shininess coefficient
     */
    public void setShininess(double shininess) {
        this.shininess = shininess;
    }

    /**
     *
     * @return The reflective coefficient
     */
    public double getReflection() {
        return reflection;
    }

    /**
     *
     * @param reflection The reflective coefficient
     */
    public void setReflection(double reflection) {
        this.reflection = reflection;
    }

    /**
     *
     * @return The refractive coefficient
     */
    public double getRefraction() {
        return refraction;
    }

    /**
     *
     * @param refraction The refractive coefficient
     */
    public void setRefraction(double refraction) {
        this.refraction = refraction;
    }
}
