/**
 * [1968] - [2023] Centros Culturales de Mexico A.C / Universidad Panamericana
 * All Rights Reserved.
 */
package edu.up.isgc.cg.raytracer;
import edu.up.isgc.cg.raytracer.lights.DirectionalLight;
import edu.up.isgc.cg.raytracer.lights.Light;
import edu.up.isgc.cg.raytracer.lights.PointLight;
import edu.up.isgc.cg.raytracer.objects.*;
import edu.up.isgc.cg.raytracer.tools.OBJReader;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author Jafet Rodríguez
 * @author Diego Jiménez
 * @Description This class in the one in charge to make everything work together
 * @Param imagen The image where you paint everything
 * @params objects List of all the objects in the class
 * @params lights List of all the lights in the project
 */
public class Raytracer {
    public static BufferedImage image;
    public static List<Object3D> objects;
    public static List<Light> lights;

    public static void main(String[] args) {
        System.out.println(new Date());
        Scene scene = new Scene();
        scene.setCamera(new Camera(new Vector3D(0, 0, -4), 100, 100, 1920, 1080, 0.6, 50.0));
        scene.addLight(new PointLight(new Vector3D(0,2,-1),Color.WHITE,20));
        scene.addLight(new PointLight(new Vector3D(0,2,0.3),Color.WHITE,15));
        scene.addLight(new PointLight(new Vector3D(-2,2,-1),Color.BLUE,10));
        scene.addLight(new PointLight(new Vector3D(2,2,-1),Color.RED,10));
        scene.addObject(OBJReader.getModel3D("FLOOR1.obj", new Vector3D(0.0,-3.5,0.0),Color.GRAY, new Material(0.3,0.3,10, 0.1,0.5)));
        scene.addObject(OBJReader.getModel3D("left.obj", new Vector3D(0.0,1.3,0.3),Color.GRAY, new Material(0.3,0.3,10,0.1,0.5)));
        scene.addObject(OBJReader.getModel3D("CubeQuad.obj", new Vector3D(1.5,-1.3,0.3),Color.RED, new Material(0.3,0.3,10,0.1,0.5)));
        scene.addObject(OBJReader.getModel3D("CubeQuad.obj", new Vector3D(-1.5,-1.3,0.3),Color.BLUE, new Material(0.3,0.3,10,0.1,0.5)));
        scene.addObject(OBJReader.getModel3D("front.obj", new Vector3D(17,0,0),Color.RED, new Material(0.3,0.3,10,0.1,0.5)));
        scene.addObject(OBJReader.getModel3D("front.obj", new Vector3D(-2,0,0),Color.BLUE, new Material(0.3,0.3,10,0.1,0.5)));


        parallelMethod(scene);
        File outputImage = new File("image.png");
        try {
            ImageIO.write(image, "png", outputImage);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(new Date());
    }

    /**
     *
     * @param scene Where we paint and put all the objects and lights
     */
    public static void parallelMethod(Scene scene){
        ExecutorService executorService = Executors.newFixedThreadPool(18);
        Camera mainCamera = scene.getCamera();
        image = new BufferedImage(mainCamera.getResolutionWidth(),mainCamera.getResolutionHeight(), BufferedImage.TYPE_INT_RGB);
        double[] nearFarPlanes = mainCamera.getNearFarPlanes();
        double cameraZ = mainCamera.getPosition().getZ();
        objects = scene.getObjects();
        lights = scene.getLights();
        Vector3D[][] positionsToRaytrace = mainCamera.calculatePositionsToRay();
        for (int i = 0; i < positionsToRaytrace.length; i++) {
            for (int j = 0; j < positionsToRaytrace[i].length; j++) {
                double x = positionsToRaytrace[i][j].getX() + mainCamera.getPosition().getX();
                double y = positionsToRaytrace[i][j].getY() + mainCamera.getPosition().getY();
                double z = positionsToRaytrace[i][j].getZ() + mainCamera.getPosition().getZ();
                Ray ray = new Ray(mainCamera.getPosition(), new Vector3D(x, y, z));
                Intersection closestIntersection = raycast(ray, objects, null, new double[]{cameraZ + nearFarPlanes[0], cameraZ + nearFarPlanes[1]});
                Runnable runnable = draw( i,j,scene, closestIntersection, ray);
                executorService.execute(runnable);    // Dibujas la escena y lanzas el rayo y pintas los objetos
            }
        }
        System.out.println(new Date());
        executorService.shutdown();
        try{
            if(!executorService.awaitTermination(5, TimeUnit.HOURS)){
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            if(!executorService.isTerminated()){
                System.err.println("Cancel non-finished");
            }
        }
        executorService.shutdownNow();
    }

    /**
     *
     * @param x int x
     * @param y int y
     * @param scene Where we paint and put all the objects and lights
     * @param closestObject The closest intersection between the camera and the object
     * @param ray The ray that has been thrown at that moment
     * @return A serie of instructions that need to be done
     */
    public static Runnable draw(int x, int y, Scene scene, Intersection closestObject, Ray ray){
        Runnable aRunnable = new Runnable() {
            @Override
            public void run() {
                Color color = decideColor(x, closestObject,scene.getCamera(),ray);  // scene, closest, depth 0
                setRGB(x, y, color);
            }
        };
        return aRunnable;
    }

    /**
     *
     * @param x int x
     * @param closestIntersection The closest intersection between the camera and the object
     * @param mainCamera The camera that lets you see everything
     * @param ray The ray that has been thrown at that moment
     * @return The color which the pixel is suposed to have
     */
    public static Color decideColor(double x, Intersection closestIntersection, Camera mainCamera, Ray ray){ // Metes todo lo que pinta los colores
        Color pixelColor = Color.BLACK;
        if (closestIntersection != null) {
            Color objColor = closestIntersection.getObject().getColor();
            for (Light light : lights) {
                double nDotL = light.getNDotL(closestIntersection);
                double intensity = light.getIntensity() * nDotL;
                double lightFallOf = intensity / Math.pow(closestIntersection.getDistance(), 1.2);
                Color lightColor = light.getColor();
                // Shadows
                Ray shadowRay = new Ray(closestIntersection.getPosition(), Vector3D.normalize(Vector3D.substract(light.getPosition(), closestIntersection.getPosition())));
                Intersection closestObject = raycast(shadowRay, objects, closestIntersection.getObject(), null);
                if (closestObject != null) {
                    if (closestObject.getDistance() < Vector3D.magnitude(Vector3D.substract(light.getPosition(), closestIntersection.getPosition()))) {
                        continue;
                    }
                }
                // Refraction
                Color refractionColor = Color.BLACK;
                double refraction = closestIntersection.getObject().getMaterial().getRefraction();
                if (refraction != 0) {
                    Intersection recursiveIntersection = null;
                    Intersection a = refraction(closestIntersection, mainCamera, objects);
                    if (a != null)
                        recursiveIntersection = refraction(closestIntersection, mainCamera, objects);
                    if(recursiveIntersection != null){
                        refractionColor = multipliColorByScalar(recursiveIntersection.getObject().getColor(),refraction);
                    }
                    objColor = multipliColorByScalar(objColor,1-refraction);
                    objColor = addColor(objColor, refractionColor);
                }
                // Reflection
                Color reflectionColor = Color.BLACK;
                double reflection = closestIntersection.getObject().getMaterial().getReflection();
                if(reflection != 0){
                    Intersection recursiveInterrsection = null;
                    Intersection a = reflection(closestIntersection, mainCamera, objects);
                    if (a != null){
                        recursiveInterrsection = reflection(closestIntersection, mainCamera, objects);
                    }
                    if(recursiveInterrsection != null && recursiveInterrsection.getObject() != closestIntersection.getObject()){
                        reflectionColor = multipliColorByScalar(recursiveInterrsection.getObject().getColor(),reflection);
                    }
                    objColor = multipliColorByScalar(objColor,1-reflection);
                    objColor = addColor(objColor, reflectionColor);
                }
                // Blinn-Phong
                double ambientLight = closestIntersection.getObject().getMaterial().getAmbientLight();
                Vector3D lightDirection = Vector3D.normalize(Vector3D.substract(light.getPosition(), closestIntersection.getPosition()));
                Vector3D cameraDirection = Vector3D.normalize(ray.getDirection());
                Vector3D lightView = Vector3D.normalize(Vector3D.add(cameraDirection, lightDirection));
                Vector3D halfVector = Vector3D.normalize(Vector3D.scalarMultiplication(lightView, 1 / Vector3D.magnitude(lightView)));
                double specularIntensity = Vector3D.dotProduct(closestIntersection.getNormal(), halfVector);
                double specular = Math.pow(specularIntensity, closestIntersection.getObject().getMaterial().getShininess());
                double objectDiffuse = closestIntersection.getObject().getMaterial().getDiffuseLight();
                double[] lightColors = new double[]{(lightColor.getRed() / 255.0) * objectDiffuse, (lightColor.getGreen() / 255.0) * objectDiffuse, (lightColor.getBlue() / 255.0) * objectDiffuse};
                double[] objColors = new double[]{objColor.getRed() / 255.0, objColor.getGreen() / 255.0, objColor.getBlue() / 255.0};
                for (int colorIndex = 0; colorIndex < objColors.length; colorIndex++) {
                    objColors[colorIndex] *= (lightFallOf + specular + ambientLight) * lightColors[colorIndex];
                }
                Color diffuse = new Color(clamp(objColors[0], 0, 1), clamp(objColors[1], 0, 1), clamp(objColors[2], 0, 1));
                pixelColor = addColor(pixelColor, diffuse);
            }
        }
        return pixelColor;
    }

    /**
     *
     * @param x int x
     * @param y int y
     * @param pixelColor The color of the pixel at the moment
     */
    public static synchronized void setRGB(int x, int y, Color pixelColor){ // Se quede igual
        image.setRGB( x, y, pixelColor.getRGB());
    }

    /**
     *
     * @param value A value you pass to verify
     * @param min The minimum
     * @param max The maximum
     * @return If your value is higher than the max returns max. If it is lower than the minimum returns the minimum.
     *          If it is between the values returns the value
     */
    public static float clamp(double value, double min, double max) {
        if (value < min) {
            return (float) min;
        }
        if (value > max) {
            return (float) max;
        }
        return (float) value;
    }

    /**
     *
     * @param original Original color
     * @param otherColor The color you want to add
     * @return The sum of the colors
     */
    public static Color addColor(Color original, Color otherColor) {
        float red = clamp((original.getRed() / 255.0) + (otherColor.getRed() / 255.0), 0, 1);
        float green = clamp((original.getGreen() / 255.0) + (otherColor.getGreen() / 255.0), 0, 1);
        float blue = clamp((original.getBlue() / 255.0) + (otherColor.getBlue() / 255.0), 0, 1);
        return new Color(red, green, blue);
    }

    /**
     *
     * @param original Original color
     * @param scalar A scalar value you get
     * @return The color multiplied by the scalar variable
     */
    public static Color multipliColorByScalar(Color original, double scalar) {
        float red = clamp((original.getRed() / 255.0) *scalar, 0, 1);
        float green = clamp((original.getGreen() / 255.0) *scalar, 0, 1);
        float blue = clamp((original.getBlue() / 255.0) *scalar, 0, 1);
        return new Color(red, green, blue);
    }

    /**
     *
     * @param original Original Vector
     * @param aux A number
     * @return The multiplication of the number with the x,y and z value of the vector
     */
    public static Vector3D multipliVector(Vector3D original, double aux) {
        double x = original.getX() * aux;
        double y = original.getY() * aux;
        double z = original.getZ() * aux;
        return new Vector3D(x, y, z);
    }

    /**
     *
     * @param ray A ray you cast
     * @param objects List of all the objects in the project
     * @param caster Where the ray is going to be thrown
     * @param clippingPlanes Where the scene cuts the objects
     * @return A intersection in which the ray hits or not.
     */
    public static Intersection raycast(Ray ray, List<Object3D> objects, Object3D caster, double[] clippingPlanes) {
        Intersection closestIntersection = null;

        for (int k = 0; k < objects.size(); k++) {
            Object3D currentObj = objects.get(k);
            if (caster == null || !currentObj.equals(caster)) {
                Intersection intersection = currentObj.getIntersection(ray);
                if (intersection != null) {
                    double distance = intersection.getDistance();
                    double intersectionZ = intersection.getPosition().getZ();
                    if (distance >= 0 &&
                            (closestIntersection == null || distance < closestIntersection.getDistance()) &&
                            (clippingPlanes == null || (intersectionZ >= clippingPlanes[0] && intersectionZ <= clippingPlanes[1]))) {
                        closestIntersection = intersection;
                    }
                }
            }
        }
        return closestIntersection;
    }

    /**
     *
     * @param intersection The closest intersection
     * @param camera The camera of the scene
     * @param objects The list of objetcs in the project
     * @return Returns if there is an intersection with a refracting object
     */
    public static Intersection refraction(Intersection intersection, Camera camera, List<Object3D> objects) {
        Intersection intersecting = null;
        for (Object3D object : objects) {
            double refraction = object.getMaterial().getRefraction();
            Vector3D I = camera.getPosition();
            Vector3D N = intersection.getNormal();
            double n1 = 1.0;
            double n2 = refraction;
            double n = (n1 / n2);
            double nSquared = Math.pow(n, 2);
            double c1 = Vector3D.dotProduct(N, I);
            double c1Squared = Math.pow(c1, 2);
            double aux = (1 - nSquared) * (1 - c1Squared);
            double c2 = Math.pow(aux, 0.5);
            Vector3D nI = multipliVector(I, n);
            double aux2 = (n * c1 - c2);
            Vector3D refractionTotal = Vector3D.add(nI, multipliVector(N, aux2));
            Vector3D directionRay = Vector3D.normalize(Vector3D.substract(intersection.getPosition(), I));
            Ray ray = new Ray(refractionTotal, directionRay);
            intersecting = raycast(ray, objects, intersection.getObject(), null);
        }
        return intersecting;
    }

    /**
     *
     * @param intersection The closest intersection
     * @param camera The camera of the scene
     * @param objects The list of objetcs in the project
     * @return Returns if there is an intersection with a reflecting object
     */
    public static Intersection reflection(Intersection intersection, Camera camera, List<Object3D> objects){
        Intersection intersecting;
        Vector3D N = intersection.getNormal();
        Vector3D I = camera.getPosition();
        double dot = Vector3D.dotProduct(I,N)*2;
        Vector3D aux =Vector3D.scalarMultiplication(N,dot);
        Vector3D reflection = Vector3D.substract(aux,I);
        Ray ray = new Ray(intersection.getPosition(), reflection);
        intersecting = raycast(ray, objects, intersection.getObject(), null);
        return intersecting;
    }
}