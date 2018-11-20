package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.corningrobotics.enderbots.endercv.CameraViewDisplay;
import org.firstinspires.ftc.teamcode.Demos.BlockVisionGRIP;
import org.firstinspires.ftc.teamcode.Utils.DrivingUtils;

public class HardwareBot {

    public DcMotor mLeftBackMotor, mRightBackMotor, mRightFrontMotor, mLeftFrontMotor;
    public DcMotor mPickupRotator, mPickupExtender, mLiftExtender, mHangingMotor;
    public Servo sDump, sPickup;
    public boolean testBot;
    public BNO055IMU imu;
    public BlockVisionGRIP blockVision;
    public DigitalChannel limitSwitch;

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

            blockVision = new BlockVisionGRIP();

            blockVision.init(hardwareMap.appContext, CameraViewDisplay.getInstance());
            blockVision.enable();
        }


    }


}
