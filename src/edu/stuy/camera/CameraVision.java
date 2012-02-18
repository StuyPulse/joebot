package edu.stuy.camera;

import edu.stuy.RobotMap;
import edu.stuy.commands.CommandBase;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.camera.AxisCamera;
import edu.wpi.first.wpilibj.camera.AxisCameraException;
import edu.wpi.first.wpilibj.image.NIVision.MeasurementType;
import edu.wpi.first.wpilibj.image.*;
import java.util.Vector;

/**
 * Sample program to use NIVision to find rectangles in the scene that are
 * illuminated by a red ring light (similar to the model from FIRSTChoice). The
 * camera sensitivity is set very low so as to only show light sources and
 * remove any distracting parts of the image.
 *
 * The CriteriaCollection is the set of criteria that is used to filter the set
 * of rectangles that are detected. In this example we're looking for rectangles
 * with a minimum width of 30 pixels and maximum of 400 pixels. Similar for
 * height (see the addCriteria() methods below.
 *
 * The algorithm first does a color threshold operation that only takes objects
 * in the scene that have a significant red color component. Then removes small
 * objects that might be caused by red reflection scattered from other parts of
 * the scene. Then a convex hull operation fills all the rectangle outlines
 * (even the partially occluded ones). Finally a particle filter looks for all
 * the shapes that meet the requirements specified in the criteria collection.
 *
 * Look in the VisionImages directory inside the project that is created for the
 * sample images as well as the NI Vision Assistant file that contains the
 * vision command chain (open it with the Vision Assistant)
 */
public class CameraVision extends Thread {

    private static CameraVision instance;   // CameraVision is a singleton
    AxisCamera camera;          // the axis camera object (connected to the switch)
    CriteriaCollection cc;      // the criteria for doing the particle filter operation
    private Relay targetLight;              // Are-we-aligned? indicator
    private Relay reflectiveLight;          // To make the targets luminous
    private int targetCenter;               // x-coord os the particle center-of-mass
    int CAMERA_CENTER;                      // center of camera width
    Vector massCenter = new Vector();       // list of center-of-mass-es
    boolean cameraOn;

    public static CameraVision getInstance() {  // CameraVision is a singleton
        if (instance == null) {
            instance = new CameraVision();
        }
        return instance;
    }

    private CameraVision() {
        targetLight = new Relay(RobotMap.TARGET_LIGHT);
        targetLight.setDirection(Relay.Direction.kForward);

        reflectiveLight = new Relay(RobotMap.REFLECTIVE_LIGHT);
        reflectiveLight.setDirection(Relay.Direction.kForward);

        camera = AxisCamera.getInstance();  // get an instance of the camera
        CAMERA_CENTER = camera.getResolution().width / 2;
        cc = new CriteriaCollection();      // create the criteria for the particle filter
        cc.addCriteria(MeasurementType.IMAQ_MT_BOUNDING_RECT_WIDTH, 30, 240, false);  // any particles at least 30 pixels wide
        cc.addCriteria(MeasurementType.IMAQ_MT_BOUNDING_RECT_HEIGHT, 40, 320, false); // any particles at least 40 pixels high

    }

    public void doCamera() {
        while(cameraOn){
        if (toggleReflectLightIfInRange()) { // if we're within image-accurate distance
            try {
                 // Do the image capture with the camera and apply the algorithm described above.
                ColorImage image = camera.getImage(); // get the image from the camera
                BinaryImage rectImage = image.thresholdHSL(145, 182, 0, 255, 120, 255); // mark only areas that have high
                image.free();                                                                        // luminance (the last two numbers
                                                                                        // are low-high limits
                BinaryImage bigObjectsImage = rectImage.removeSmallObjects(false, 2);  // remove small artifacts
                rectImage.free();
                BinaryImage convexHullImage = bigObjectsImage.convexHull(false);          // fill in occluded rectangles
                bigObjectsImage.free();
                BinaryImage filteredImage = convexHullImage.particleFilter(cc);           // filter particles using our criteria
                convexHullImage.free();
                ParticleAnalysisReport[] reports = filteredImage.getOrderedParticleAnalysisReports();  // get list of results
                massCenter.removeAllElements();                                         // remove previous center-of-mass-es
                
                
                ParticleAnalysisReport r = reports[0]; // Gets largest detected target
                
                // [target size feet]/[target size pixels] = [FOV feet]/[FOV pixels]
                // [FOV feet] = ([FOV pixels]*[target size feet])/[target size pixels]
                int fovFeet = (360 * 2) / r.boundingRectWidth;                          // use it to calculate our field of view
                massCenter.addElement(new Integer(r.center_mass_x));                    // add each center-of-mass to our list


                if (massCenter.size() > 0) {            // if there are center-of-mass-es in the list
                    targetCenter = getCenterMass(0);    // set targetCenter to the largest one (the first one)
                }

                filteredImage.free();
               
             } catch (AxisCameraException ex) {          // this is needed if the camera.getImage() is called
                ex.printStackTrace();
            } catch (NIVisionException ex) {
                ex.printStackTrace();
            }
        }
    }
    }
    /**
     * Displays the center of the largest, rectangular target detected
     * @return targetCenter
     */
    public double getTargetCenter() {
        return targetCenter;    // returns the largest center-of-mass
    }

    public int getCenterMass(int rectid) {
        if (rectid < massCenter.size()) {
            return ((Integer) massCenter.elementAt(rectid)).intValue(); // return the center-of-mass for the specified rectangle
        }
        return -1; // but if trying to find a non-existent rectangle, return nonsense
    }

    public boolean isAligned() {
        double absValue = Math.abs(CAMERA_CENTER - targetCenter);
        return absValue < 20; // if the pixel difference between the center-of-mass
                              // and the center-of-camera, return True

    }

    /**
     * Uses the sonar to determine whether to turn on camera light.
     *
     * If Alliance Wall is within 144 inches, then turn on light.  Else, turn off.
     */
    public boolean toggleReflectLightIfInRange() {
        boolean withinRange = CommandBase.drivetrain.getSonarVoltage() < 144; // TODO: Find actual effective limit for range of LEDs
        reflectiveLight.set(withinRange ? Relay.Value.kOn : Relay.Value.kOff);

        return withinRange;
    }

    /**
     * If aligned to target, turn on target light.  Else, off.
     */
    public void toggleTargetLightIfAligned() {
        targetLight.set(isAligned() ? Relay.Value.kOn : Relay.Value.kOff);
    }

    public void run(){
        doCamera();
        toggleTargetLightIfAligned();
    }

    public void setCamera(boolean  isRunning){
        cameraOn = isRunning;
    }
}
