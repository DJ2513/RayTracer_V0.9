/**
 * [1968] - [2023] Centros Culturales de Mexico A.C / Universidad Panamericana
 * All Rights Reserved.
 */
package edu.up.isgc.cg.raytracer.objects;

import edu.up.isgc.cg.raytracer.Intersection;
import edu.up.isgc.cg.raytracer.Material;
import edu.up.isgc.cg.raytracer.Ray;
import edu.up.isgc.cg.raytracer.Vector3D;
import edu.up.isgc.cg.raytracer.tools.Barycentric;

import java.awt.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Jafet Rodríguez
 * @description Here we construct the Models3D
 */

public class Model3D extends Object3D {
    private List<Triangle> triangles;

    /**
     *
     * @param position Where it is on the scene
     * @param triangles A triangle
     * @param color Color of the object
     * @param material Coefficients that affects the object
     */
    public Model3D(Vector3D position, Triangle[] triangles, Color color, Material material) {
        super(position, color, material);
        setTriangles(triangles);
    }

    /**
     *
     * @return A list of triangles
     */
    public List<Triangle> getTriangles() {
        return triangles;
    }

    /**
     *
     * @param triangles A list of all the triangles
     */
    public void setTriangles(Triangle[] triangles) {
        Vector3D position = getPosition();
        Set<Vector3D> uniqueVertices = new HashSet<>();
        for (Triangle triangle : triangles) {
            uniqueVertices.addAll(Arrays.asList(triangle.getVertices()));
        }

        for (Vector3D vertex : uniqueVertices) {
            vertex.setX(vertex.getX() + position.getX());
            vertex.setY(vertex.getY() + position.getY());
            vertex.setZ(vertex.getZ() + position.getZ());
        }

        this.triangles = Arrays.asList(triangles);
    }

    /**
     *
     * @param ray The ray we cast
     * @return An intersection
     */
    @Override
    public Intersection getIntersection(Ray ray) {
        double distance = -1;
        Vector3D normal = Vector3D.ZERO();
        Vector3D position = Vector3D.ZERO();

        for (Triangle triangle : getTriangles()) {
            Intersection intersection = triangle.getIntersection(ray);
            double intersectionDistance = intersection.getDistance();
            if (intersection != null && intersectionDistance > 0 &&
                    (intersectionDistance < distance || distance < 0)) {
                distance = intersectionDistance;
                position = Vector3D.add(ray.getOrigin(), Vector3D.scalarMultiplication(ray.getDirection(), distance));
                //normal = triangle.getNormal();
                normal = Vector3D.ZERO();
                double[] uVw = Barycentric.CalculateBarycentricCoordinates(position, triangle);
                Vector3D[] normals = triangle.getNormals();
                for (int i = 0; i < uVw.length; i++) {
                    normal = Vector3D.add(normal, Vector3D.scalarMultiplication(normals[i], uVw[i]));
                }
            }
        }

        if (distance == -1) {
            return null;
        }

        return new Intersection(position, distance, normal, this);
    }
}
