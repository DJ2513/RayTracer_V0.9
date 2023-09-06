/**
 * [1968] - [2023] Centros Culturales de Mexico A.C / Universidad Panamericana
 * All Rights Reserved.
 */
package edu.up.isgc.cg.raytracer;

import edu.up.isgc.cg.raytracer.lights.Light;
import edu.up.isgc.cg.raytracer.objects.Camera;
import edu.up.isgc.cg.raytracer.objects.Object3D;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jafet Rodríguez
 * @description Here we create the scene, where we create the images and put all the objects
 */
public class Scene {

    private Camera camera;
    private List<Object3D> objects;
    private List<Light> lights;

    public Scene() {
        setObjects(new ArrayList<>());
        setLights(new ArrayList<>());
    }

    /**
     *
     * @return a list of lights
     */
    public List<Light> getLights() {
        if (lights == null) {
            setLights(new ArrayList<>());
        }
        return lights;
    }

    /**
     *
     * @param lights list of lights
     */
    public void setLights(List<Light> lights) {
        this.lights = lights;
    }

    /**
     *
     * @param light new light
     */
    public void addLight(Light light) {
        getLights().add(light);
    }

    /**
     *
     * @return the camera
     */
    public Camera getCamera() {
        return camera;
    }

    /**
     *
     * @param camera the camera
     */
    public void setCamera(Camera camera) {
        this.camera = camera;
    }

    /**
     *
     * @param object New object
     */
    public void addObject(Object3D object) {
        getObjects().add(object);
    }

    /**
     *
     * @return list of objects
     */
    public List<Object3D> getObjects() {
        if (objects == null) {
            objects = new ArrayList<>();
        }
        return objects;
    }

    /**
     *
     * @param objects list of objects
     */
    public void setObjects(List<Object3D> objects) {
        this.objects = objects;
    }
}

