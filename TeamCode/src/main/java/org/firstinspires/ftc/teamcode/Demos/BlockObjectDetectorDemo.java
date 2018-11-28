package org.firstinspires.ftc.teamcode.Demos;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.teamcode.HardwareBot;

import java.util.List;

@Autonomous(name="Block Detect", group="Tests")
public class BlockObjectDetectorDemo extends LinearOpMode {

    private static final String TF_MODEL_FILE = "RoverRuckus.tflite";
    private static final String GOLD_LABEL = "Gold Mineral";
    private static final String SILVER_LABEL = "Silver Mineral";

    private final String VU_KEY = "ASyzkIH/////AAABma7eqG1nFUxlqU7ahO0szv8wRPEmH6yv8C8KdIk7Xz9ju4YBSkeZ3cjEWl0Baj99OT/h/1m2b3EZUKTq8VdAJH2UEEg4sTLgwyWO7yk+1dX2cvFp1j0beRFvmRo4bh3ThXA6zpE/i9q9ukAp/A9vKVviRtwyHaTK4nLo2cnypVpCa4nqsS6/dRLaZU6p4/A3vtqWMAzzjyOEDK23iv8UupfoqUw+bPm0y8qNkJHRJ2QXZaqRaevSWADwovEBTHBV6wcuUlc4G766sjG6ooYqmwp9OmMLMxUF+u0PapeiNaSl/aRAQV9gxyrttAP5i3It3QvILn9RLOa1AXc5nUxKMzExpeUXkbCSg7AR5I2Y3TJQ";
     /**
     * {@link #vuforia} is the variable we will use to store our instance of the Vuforia
     * localization engine.
     */
    private VuforiaLocalizer vuforia;

    /**
     * {@link #tfod} is the variable we will use to store our instance of the Tensor Flow Object
     * Detection engine.
     */
    private TFObjectDetector tfod;

    @Override
    public void runOpMode() {

        // Vuforia Initilization to Use Camera Frames
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();

        parameters.vuforiaLicenseKey = VU_KEY;
        parameters.cameraName = hardwareMap.get(WebcamName.class, "Webcam 1");

        //  Instantiate the Vuforia engine
        vuforia = ClassFactory.getInstance().createVuforia(parameters);

        // Local Hardware Bot Initilization for Hardware Access
        //bot.init(hardwareMap, true, false);

        // Check Device
        if (ClassFactory.getInstance().canCreateTFObjectDetector()) {
            initTfod();
        } else {
            telemetry.addData("Sorry!", "This device is not compatible with TFOD");
        }

        // Initilization Complete
        telemetry.addData(">", "Initialization Complete");
        telemetry.update();
        waitForStart();

        if (opModeIsActive()) {
            /** Activate Tensor Flow Object Detection. */
            if (tfod != null) {
                tfod.activate();
            }

            while (opModeIsActive()) {
                if (tfod != null) {
                    // getUpdatedRecognitions() will return null if no new information is available since
                    // the last time that call was made.
                    List<Recognition> updatedRecognitions = tfod.getUpdatedRecognitions();
                    if (updatedRecognitions != null) {
                        telemetry.addData("# Object Detected", updatedRecognitions.size());
                        for (Recognition recognition : updatedRecognitions) {
                            telemetry.addData("Object: ", recognition.getLabel());
                        }
                        telemetry.update();
                    }
                }
            }
        }

        if (tfod != null) {
            tfod.shutdown();
        }
    }

    /**
     * Initialize the Vuforia localization engine.
     */
    private void initVuforia() {
        /*
         * Configure Vuforia by creating a Parameter object, and passing it to the Vuforia engine.
         */
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();

        parameters.vuforiaLicenseKey = VU_KEY;
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;

        //  Instantiate the Vuforia engine
        vuforia = ClassFactory.getInstance().createVuforia(parameters);

        // Loading trackables is not necessary for the Tensor Flow Object Detection engine.
    }

    /**
     * Initialize the Tensor Flow Object Detection engine.
     */
    private void initTfod() {
        int tfodMonitorViewId = hardwareMap.appContext.getResources().getIdentifier(
                "tfodMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);
        tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);
        tfod.loadModelFromAsset(TF_MODEL_FILE, GOLD_LABEL, SILVER_LABEL);
    }
}
