/**
 * [1968] - [2023] Centros Culturales de Mexico A.C / Universidad Panamericana
 * All Rights Reserved.
 */
package edu.up.isgc.cg.raytracer.objects;

import edu.up.isgc.cg.raytracer.Intersection;
import edu.up.isgc.cg.raytracer.Ray;
import edu.up.isgc.cg.raytracer.Vector3D;
import java.awt.*;

/**
 * @author Jafet Rodríguez
 * @Description Here we construct the camera
 *
 */

public class Camera extends Object3D {
    //FOV[0] = Horizontal | FOV[1] = Vertical
    private double[] fieldOfView = new double[2];
    private double defaultZ = 15.0;
    //0 is width
    //1 is height
    private int[] resolution;
    private double[] nearFarPlanes = new double[2];

    /**
     *
     * @param position Where the camera is going to be
     * @param fieldOfViewHorizontal How  much horizontal view we have
     * @param fieldOfViewVertical How  much vertical view we have
     * @param widthResolution The width resolution of the image
     * @param heightResolution The height resolution of the image
     * @param nearPlane Where the scene cut objects that are close
     * @param farPlane Where the scene cut objects that are far
     */
    public Camera(Vector3D position, double fieldOfViewHorizontal, double fieldOfViewVertical,
                  int widthResolution, int heightResolution, double nearPlane, double farPlane) {
        super(position, Color.BLACK, null);
        setFieldOfViewHorizontal(fieldOfViewHorizontal);
        setFieldOfViewVertical(fieldOfViewVertical);
        setResolution(new int[]{widthResolution, heightResolution});
        setNearFarPlanes(new double[]{nearPlane, farPlane});
    }

    /**
     *
     * @return returns near-far planes
     */
    public double[] getNearFarPlanes() {
        return nearFarPlanes;
    }

    /**
     *
     * @param nearFarPlanes Where the scene cut objects that are close of far
     */
    private void setNearFarPlanes(double[] nearFarPlanes) {
        this.nearFarPlanes = nearFarPlanes;
    }

    /**
     *
     * @return returns the field of view
     */
    public double[] getFieldOfView() {
        return fieldOfView;
    }

    /**
     *
     * @param fieldOfView How much of the scene is going to bee seen
     */
    private void setFieldOfView(double[] fieldOfView) {
        this.fieldOfView = fieldOfView;
    }

    /**
     *
     * @return the horizantal field of view
     */
    public double getFieldOfViewHorizontal() {
        return fieldOfView[0];
    }

    /**
     *
     * @param fieldOfViewHorizontal How  much horizontal view we have
     */
    public void setFieldOfViewHorizontal(double fieldOfViewHorizontal) {
        fieldOfView[0] = fieldOfViewHorizontal;
    }

    /**
     *
     * @return returns the vertical field of view
     */
    public double getFieldOfViewVertical() {
        return fieldOfView[1];
    }

    /**
     *
     * @param fieldOfViewVertical How  much vertical view we have
     */
    public void setFieldOfViewVertical(double fieldOfViewVertical) {
        fieldOfView[1] = fieldOfViewVertical;
    }

    /**
     *
     * @return returns the defaultZ
     */
    public double getDefaultZ() {
        return defaultZ;
    }

    /**
     *
     * @param defaultZ a default value for the Z component
     */
    public void setDefaultZ(double defaultZ) {
        this.defaultZ = defaultZ;
    }

    /**
     *
     * @return returns the resolution
     */
    public int[] getResolution() {
        return resolution;
    }

    /**
     *
     * @return returns the width of the resolution
     */
    public int getResolutionWidth() {
        return resolution[0];
    }

    /**
     *
     * @return returns the height of the resolution
     */
    public int getResolutionHeight() {
        return resolution[1];
    }

    /**
     *
     * @param resolution resolution of the image
     */
    private void setResolution(int[] resolution) {
        this.resolution = resolution;
    }

    /**
     *
     * @return how far is the ray from the camera
     */
    public Vector3D[][] calculatePositionsToRay() {
        double angleMaxX = getFieldOfViewHorizontal() / 2.0;
        double radiusMaxX = getDefaultZ() / (double) Math.cos(Math.toRadians(angleMaxX));

        double maxX = (double) Math.sin(Math.toRadians(angleMaxX)) * radiusMaxX;
        double minX = -maxX;

        double angleMaxY = getFieldOfViewVertical() / 2.0;
        double radiusMaxY = getDefaultZ() / (double) Math.cos(Math.toRadians(angleMaxY));

        double maxY = (double) Math.sin(Math.toRadians(angleMaxY)) * radiusMaxY;
        double minY = -maxY;

        Vector3D[][] positions = new Vector3D[getResolutionWidth()][getResolutionHeight()];
        double posZ = getDefaultZ();

        for (int x = 0; x < positions.length; x++) {
            for (int y = 0; y < positions[x].length; y++) {
                double posX = minX + (((maxX - minX) / (double) getResolutionWidth()) * x);
                double posY = maxY - (((maxY - minY) / (double) getResolutionHeight()) * y);
                positions[x][y] = new Vector3D(posX, posY, posZ);
            }
        }

        return positions;
    }

    /**
     *
     * @param ray The ray that we cast
     * @return The intersection with an object
     */
    @Override
    public Intersection getIntersection(Ray ray) {
        return new Intersection(Vector3D.ZERO(), -1, Vector3D.ZERO(), null);
    }
}
