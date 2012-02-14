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
public class CameraVision {

    private static CameraVision instance;
    //TODO get number from Peter
    public static final int CAMERA_CENTER = 320;
    AxisCamera camera;          // the axis camera object (connected to the switch)
    CriteriaCollection cc;      // the criteria for doing the particle filter operation
    private Relay targetLight;
    private Relay reflectiveLight;
    private int targetCenter;
    int numRectangles;
    Vector massCenter = new Vector();
    int lumLow = 116;

    public static CameraVision getInstance() {
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

        camera = AxisCamera.getInstance();  // get an instance ofthe camera
        cc = new CriteriaCollection();      // create the criteria for the particle filter
        cc.addCriteria(MeasurementType.IMAQ_MT_BOUNDING_RECT_WIDTH, 30, 400, false);
        cc.addCriteria(MeasurementType.IMAQ_MT_BOUNDING_RECT_HEIGHT, 40, 400, false);
        //cc.addCriteria(MeasurementType.IMAQ_MT_ORIENTATION, 0, 180, false);
        // TODO: look into IMAQ_MT_ORIENTATION and IMAQ_MT_MOMENT_OF_INERTIA_XX
    }

    public void doCamera() {
        if (toggleReflectLightIfInRange()) {
            try {
                 // Do the image capture with the camera and apply the algorithm described above.
                ColorImage image = camera.getImage();
                BinaryImage rectImage = image.thresholdHSL(136, 182, 0, 255, lumLow, 255);
                //rectImage.write("red.png");


                BinaryImage bigObjectsImage = rectImage.removeSmallObjects(false, 2);  // remove small artifacts
                BinaryImage convexHullImage = bigObjectsImage.convexHull(false);          // fill in occluded rectangles
                //convexHullImage.write("box.png");
                BinaryImage filteredImage = convexHullImage.particleFilter(cc);           // find filled in rectangles

                ParticleAnalysisReport[] reports = filteredImage.getOrderedParticleAnalysisReports();  // get list of results
                massCenter.removeAllElements();
                for (int i = 0; i < reports.length; i++) {                                // print results
                    ParticleAnalysisReport r = reports[i];
                    // [target size feet]/[target size pixels] = [FOV feet]/[FOV pixels]
                    // [FOV feet] = ([FOV pixels]*[target size feet])/[target size pixels]
                    int fovFeet = (360 * 2) / r.boundingRectWidth;
                    massCenter.addElement(new Integer(r.center_mass_x));
                    //System.out.println("Our field of view in feet is: " + fovFeet);
                    //System.out.println("Particle: " + i + ":  Center of mass x: " + r.center_mass_x);
                }
                if (reports.length > 4) {
                    
                }
//                System.out.println(filteredImage.getNumberParticles() + "  " + Timer.getFPGATimestamp());
                if (massCenter.size() > 0) {
                    targetCenter = getCenterMass(0);
                }

                /**
                 * all images in Java must be freed after they are used since
                 * they are allocated out of C data structures. Not calling
                 * free() will cause the memory to accumulate over each pass of
                 * this loop.
                 */
                filteredImage.free();
                convexHullImage.free();
                bigObjectsImage.free();
                rectImage.free();
                image.free();

            } catch (AxisCameraException ex) {          // this is needed if the camera.getImage() is called
                ex.printStackTrace();
            } catch (NIVisionException ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * Displays the center of the largest, rectangular target detected
     * @return targetCenter
     */
    public double getTargetCenter() {
        return targetCenter;
    }

    public int getCenterMass(int rectid) {
        if (rectid < massCenter.size()) {
            return ((Integer) massCenter.elementAt(rectid)).intValue();
        }
        return 0;
    }

    public boolean isAligned() {
        double absValue = Math.abs(CAMERA_CENTER - targetCenter);
        return absValue < 50;

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
}
