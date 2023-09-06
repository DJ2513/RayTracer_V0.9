/**
 * [1968] - [2023] Centros Culturales de Mexico A.C / Universidad Panamericana
 * All Rights Reserved.
 */
package edu.up.isgc.cg.raytracer;

/**
 * @author Jafet Rodríguez
 * @description Here we costruct the Vector3D
 */
public class Vector3D {
    private static final Vector3D ZERO = new Vector3D(0.0, 0.0, 0.0);
    private double x, y, z;

    /**
     *
     * @param x param x
     * @param y param y
     * @param z param z
     */
    public Vector3D(double x, double y, double z) {
        setX(x);
        setY(y);
        setZ(z);
    }

    /**
     *
     * @return param x
     */
    public double getX() {
        return x;
    }

    /**
     *
     * @param x param x
     */
    public void setX(double x) {
        this.x = x;
    }

    /**
     *
     * @return param y
     */
    public double getY() {
        return y;
    }

    /**
     *
     * @param y param y
     */
    public void setY(double y) {
        this.y = y;
    }

    /**
     *
     * @return param z
     */
    public double getZ() {
        return z;
    }

    /**
     *
     * @param z param z
     */
    public void setZ(double z) {
        this.z = z;
    }

    /**
     *
     * @return a clone of a Vector3D
     */
    public Vector3D clone() {
        return new Vector3D(getX(), getY(), getZ());
    }

    /**
     *
     * @return Vector3D in (0,0,0)
     */
    public static Vector3D ZERO() {
        return ZERO.clone();
    }

    /**
     *
     * @return The Vector3D in a string
     */
    @Override
    public String toString() {
        return "Vector3D{" +
                "x=" + getX() +
                ", y=" + getY() +
                ", z=" + getZ() +
                "}";
    }

    /**
     *
     * @param vectorA Vector A
     * @param vectorB Vector B
     * @return The dot product between these two
     */
    public static double dotProduct(Vector3D vectorA, Vector3D vectorB) {
        return (vectorA.getX() * vectorB.getX()) + (vectorA.getY() * vectorB.getY()) + (vectorA.getZ() * vectorB.getZ());
    }

    /**
     *
     * @param vectorA Vector A
     * @param vectorB Vector B
     * @return The cross product between these two
     */
    public static Vector3D crossProduct(Vector3D vectorA, Vector3D vectorB){
        return new Vector3D((vectorA.getY() * vectorB.getZ()) - (vectorA.getZ() * vectorB.getY()),
                (vectorA.getZ() * vectorB.getX()) - (vectorA.getX() * vectorB.getZ()),
                (vectorA.getX() * vectorB.getY()) - (vectorA.getY() * vectorB.getX()));
    }

    /**
     *
     * @param vectorA Vector A
     * @return The magnitude of the vector
     */
    public static double magnitude (Vector3D vectorA){
        return Math.sqrt(dotProduct(vectorA, vectorA));
    }

    /**
     *
     * @param vectorA Vector A
     * @param vectorB Vector B
     * @return The sum of the Vectors
     */
    public static Vector3D add(Vector3D vectorA, Vector3D vectorB){
        return new Vector3D(vectorA.getX() + vectorB.getX(), vectorA.getY() + vectorB.getY(), vectorA.getZ() + vectorB.getZ());
    }

    /**
     *
     * @param vectorA Vector A
     * @param vectorB Vector B
     * @return The substract of the vectors
     */
    public static Vector3D substract(Vector3D vectorA, Vector3D vectorB){
        return new Vector3D(vectorA.getX() - vectorB.getX(), vectorA.getY() - vectorB.getY(), vectorA.getZ() - vectorB.getZ());
    }

    /**
     *
     * @param vectorA Vector A
     * @return The normal of the vector
     */
    public static Vector3D normalize(Vector3D vectorA){
        double mag = Vector3D.magnitude(vectorA);
        return new Vector3D(vectorA.getX() / mag, vectorA.getY() / mag, vectorA.getZ() / mag);
    }

    /**
     *
     * @param vectorA Vector A
     * @param scalar scalar value
     * @return The result of the multiplication of the vector and the scalar
     */
    public static Vector3D scalarMultiplication(Vector3D vectorA, double scalar){
        return new Vector3D(vectorA.getX() * scalar, vectorA.getY() * scalar, vectorA.getZ() * scalar);
    }
}
