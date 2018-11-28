package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.vuforia.Vuforia;

import org.corningrobotics.enderbots.endercv.CameraViewDisplay;
import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.teamcode.Demos.BlockVisionGRIP;
import org.firstinspires.ftc.teamcode.Utils.DrivingUtils;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;

public class HardwareBot {

    private static final String VU_KEY = "ASyzkIH/////AAABma7eqG1nFUxlqU7ahO0szv8wRPEmH6yv8C8KdIk7Xz9ju4YBSkeZ3cjEWl0Baj99OT/h/1m2b3EZUKTq8VdAJH2UEEg4sTLgwyWO7yk+1dX2cvFp1j0beRFvmRo4bh3ThXA6zpE/i9q9ukAp/A9vKVviRtwyHaTK4nLo2cnypVpCa4nqsS6/dRLaZU6p4/A3vtqWMAzzjyOEDK23iv8UupfoqUw+bPm0y8qNkJHRJ2QXZaqRaevSWADwovEBTHBV6wcuUlc4G766sjG6ooYqmwp9OmMLMxUF+u0PapeiNaSl/aRAQV9gxyrttAP5i3It3QvILn9RLOa1AXc5nUxKMzExpeUXkbCSg7AR5I2Y3TJQ";
    public DcMotor mLeftBackMotor, mRightBackMotor, mRightFrontMotor, mLeftFrontMotor;
    public DcMotor mPickupRotator, mPickupExtender, mLiftExtender, mHangingMotor;
    public Servo sDump, sPickup;
    public boolean testBot;
    public BNO055IMU imu;
    public BlockVisionGRIP blockVision;
    public DigitalChannel limitSwitch;

    private static final String TF_MODEL_FILE = "RoverRuckus.tflite";
    private static final String GOLD_LABEL = "Gold Mineral";
    private static final String SILVER_LABEL = "Silver Mineral";

    public VuforiaLocalizer vuforia;

    public TFObjectDetector tfod;

    public static String getVuforiaKey() {
        return VU_KEY;
    }

    /**
     * Initialize the Hardware Bot in
     * any opmode based on bot and
     * the opmode type
     * @param hardwareMap
     * @param autonRun
     * @param testBot
     */
    public void init(HardwareMap hardwareMap, boolean autonRun, boolean testBot){
        this.testBot = testBot;

        if (testBot) {
            mLeftFrontMotor = hardwareMap.dcMotor.get("left");
            mRightFrontMotor = hardwareMap.dcMotor.get("right");

            mRightFrontMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            mLeftFrontMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }

        else {
            mLeftBackMotor = hardwareMap.dcMotor.get("backLeft");
            mLeftFrontMotor = hardwareMap.dcMotor.get("frontLeft");

            mRightBackMotor = hardwareMap.dcMotor.get("backRight");
            mRightFrontMotor = hardwareMap.dcMotor.get("frontRight");

            mPickupExtender = hardwareMap.dcMotor.get("horizontalSlideExtend");
            mPickupRotator = hardwareMap.dcMotor.get("linearSlideRotate");
            mLiftExtender = hardwareMap.dcMotor.get("verticalSlide");
            mHangingMotor = hardwareMap.dcMotor.get("hangingMotor");

            sPickup = hardwareMap.servo.get("SpatuServo");
            sDump = hardwareMap.servo.get("dumpServo");

            limitSwitch = hardwareMap.digitalChannel.get("limitSwitch");
            limitSwitch.setMode(DigitalChannel.Mode.INPUT);

            mRightFrontMotor.setDirection(DcMotorSimple.Direction.REVERSE);
            mRightBackMotor.setDirection(DcMotorSimple.Direction.REVERSE);

            mPickupRotator.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            mHangingMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            mLiftExtender.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            mHangingMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

            mRightBackMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            mRightFrontMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            mLeftFrontMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            mLeftBackMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }


        if(autonRun) {

            // IMU
            BNO055IMU.Parameters imuParameters = new BNO055IMU.Parameters();
            imuParameters.angleUnit           = BNO055IMU.AngleUnit.DEGREES;
            imuParameters.accelUnit           = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
            imuParameters.calibrationDataFile = "BNO055IMUCalibration.json"; // see the calibration sample opmode
            imuParameters.loggingEnabled      = true;
            imuParameters.loggingTag          = "IMU";
            imuParameters.accelerationIntegrationAlgorithm = new JustLoggingAccelerationIntegrator();
            imu = hardwareMap.get(BNO055IMU.class, "imu");
            imu.initialize(imuParameters);

            /**
             * Open CV Implementation
             */

            /*
            blockVision = new BlockVisionGRIP();

            blockVision.init(hardwareMap.appContext, CameraViewDisplay.getInstance());
            blockVision.enable();
            */

            /**
             * Vuforia Implementation
             */

            // Vuforia Initilization to Use Camera Frames
            VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();

            parameters.vuforiaLicenseKey = VU_KEY;
            parameters.cameraName = hardwareMap.get(WebcamName.class, "Webcam 1");

            //  Instantiate the Vuforia engine
            vuforia = ClassFactory.getInstance().createVuforia(parameters);

            // Check Device
            if (ClassFactory.getInstance().canCreateTFObjectDetector()) {
                int tfodMonitorViewId = hardwareMap.appContext.getResources().getIdentifier(
                        "tfodMonitorViewId", "id", hardwareMap.appContext.getPackageName());
                TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);
                tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);
                tfod.loadModelFromAsset(TF_MODEL_FILE, GOLD_LABEL, SILVER_LABEL);
            }

        }


    }



}
