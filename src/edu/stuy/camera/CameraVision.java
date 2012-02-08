package edu.stuy.camera;

import edu.stuy.RobotMap;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.SimpleRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.camera.AxisCamera;
import edu.wpi.first.wpilibj.camera.AxisCameraException;
import edu.wpi.first.wpilibj.image.BinaryImage;
import edu.wpi.first.wpilibj.image.ColorImage;
import edu.wpi.first.wpilibj.image.CriteriaCollection;
import edu.wpi.first.wpilibj.image.NIVision.MeasurementType;
import edu.wpi.first.wpilibj.image.NIVisionException;
import edu.wpi.first.wpilibj.image.ParticleAnalysisReport;
import edu.wpi.first.wpilibj.image.RGBImage;

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

    //TODO get number from Peter
    public static final int TARGET_POSITION = 7;
    AxisCamera camera;          // the axis camera object (connected to the switch)
    CriteriaCollection cc;      // the criteria for doing the particle filter operation
    private Relay targetLight;
    private double targetCenter;

    public CameraVision() {
    targetLight = new Relay(RobotMap.TARGET_LIGHT);
    targetLight.setDirection(Relay.Direction.kForward);

        camera = AxisCamera.getInstance();  // get an instance ofthe camera
        cc = new CriteriaCollection();      // create the criteria for the particle filter
        cc.addCriteria(MeasurementType.IMAQ_MT_BOUNDING_RECT_WIDTH, 30, 400, false);
        cc.addCriteria(MeasurementType.IMAQ_MT_BOUNDING_RECT_HEIGHT, 40, 400, false);
    }

    public void doCamera() {
        try {
            /**
             * Do the image capture with the camera and apply the algorithm
             * described above. This sample will either get images from the
             * camera or from an image file stored in the top level directory in
             * the flash memory on the cRIO. The file name in this case is
             * "10ft2.jpg"
             *
             */
            ColorImage image = camera.getImage();
            BinaryImage rectImage = image.thresholdHSL(136, 182, 45, 255, 116, 255);
            rectImage.write("myimage.jpg");
            //BinaryImage thresholdImage = rectImage.thresholdRGB(25, 255, 0, 45, 0, 47);   // keep only red objects
            BinaryImage bigObjectsImage = rectImage.removeSmallObjects(false, 2);  // remove small artifacts
            BinaryImage convexHullImage = bigObjectsImage.convexHull(false);          // fill in occluded rectangles
            BinaryImage filteredImage = convexHullImage.particleFilter(cc);           // find filled in rectangles

            ParticleAnalysisReport[] reports = filteredImage.getOrderedParticleAnalysisReports();  // get list of results
            ParticleAnalysisReport r = reports[0];
            System.out.println("Center of mass x: " + r.center_mass_x);
            targetCenter = r.center_mass_x;
            System.out.println(filteredImage.getNumberParticles() + "  " + Timer.getFPGATimestamp());

            /**
             * all images in Java must be freed after they are used since they
             * are allocated out of C data structures. Not calling free() will
             * cause the memory to accumulate over each pass of this loop.
             */
            filteredImage.free();
            convexHullImage.free();
            bigObjectsImage.free();
            rectImage.free();
            image.free();

            } catch (AxisCameraException ex) {        // this is needed if the camera.getImage() is called
                ex.printStackTrace();
        } catch (NIVisionException ex) {
            ex.printStackTrace();
        }
        if(isFacingTarget()){
            targetLight.set(Relay.Value.kOn);
        }else{
            targetLight.set(Relay.Value.kOff);
        
        }
    }

    /**
     * Displays the center of the largest, rectangular target detected
     * @return targetCenter
     */
    public double getTargetCenter() {
        return targetCenter;
    }

    public boolean isFacingTarget() {
        double absValue = Math.abs(TARGET_POSITION - targetCenter);
        return absValue < 50;

    }


}
