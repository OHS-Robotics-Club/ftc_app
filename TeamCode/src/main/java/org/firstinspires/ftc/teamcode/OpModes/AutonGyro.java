package org.firstinspires.ftc.teamcode.OpModes;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.corningrobotics.enderbots.endercv.CameraViewDisplay;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.teamcode.Demos.BlockVisionGRIP;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Rect;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.Locale;

@Autonomous(name="AutonGyro", group="Competition")
public class AutonGyro extends LinearOpMode {
    DcMotor LFMotor, RFMotor, LBMotor, RBMotor, SpatuSlideMotor,  LiftSlideMotor, SpatuSpoolMotor, hangingMotor;
    BlockVisionGRIP blockVision;
    Orientation angles;
    BNO055IMU imu;


    @Override
    public void runOpMode() throws InterruptedException {
        LFMotor = hardwareMap.dcMotor.get("frontLeft");
        LBMotor = hardwareMap.dcMotor.get("backLeft");
        RFMotor = hardwareMap.dcMotor.get("frontRight");
        RBMotor = hardwareMap.dcMotor.get("backRight");
        hangingMotor = hardwareMap.dcMotor.get("hangingMotor");
        double resizeImageWidth = 320.0;
        double resizeImageHeight = 240.0;

        double imageMidpoint = 82;

        RFMotor.setDirection(DcMotor.Direction.REVERSE);
        RBMotor.setDirection(DcMotor.Direction.REVERSE);

        hangingMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

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

        telemetry.addData("Caption","Init Started");

        blockVision.init(hardwareMap.appContext, CameraViewDisplay.getInstance());
        blockVision.enable();

        telemetry.addData("Caption","Init Finished");

        waitForStart();

        hangingMotor.setPower(0.9);
        sleep(3000);
        hangingMotor.setPower(0.0);

        sleep(1000);

        turnGyro(-30);

        sleep(1000);

        LFMotor.setPower(0.2);
        LBMotor.setPower(0.2);

        RFMotor.setPower(-0.2);
        RBMotor.setPower(-0.2);


        boolean foundBlock = false;

        while (!foundBlock) {

            if(blockVision.filterContoursOutput() != null) {

                ArrayList<MatOfPoint> contours = blockVision.filterContoursOutput();
                for (int i = 0; i < contours.size(); i++) {

                    Rect boundingRect = Imgproc.boundingRect(contours.get(i));
                    double xCenter = (boundingRect.x + boundingRect.width) / 2;
                    double yCenter = (boundingRect.y + boundingRect.height) / 2;

                    if (xCenter >  imageMidpoint - 20 && xCenter < imageMidpoint + 20) {

                        telemetry.addData("Caption: ", "Found Block");
                        telemetry.update();

                        LFMotor.setPower(0.0);
                        LBMotor.setPower(0.0);

                        RFMotor.setPower(0.0);
                        RBMotor.setPower(0.0);

                        foundBlock = true;
                    }


                    telemetry.addData("contour" + Integer.toString(i),
                            String.format(Locale.getDefault(), "(%d, %d)", (boundingRect.x + boundingRect.width) / 2, (boundingRect.y + boundingRect.height) / 2));
                    telemetry.update();
                }
            }
        }

        sleep(1000);

        RFMotor.setPower(0.2);
        RBMotor.setPower(0.2);
        LFMotor.setPower(0.2);
        LBMotor.setPower(0.2);

        sleep(3000); // Will need to be adjusted

        LFMotor.setPower(0);
        LBMotor.setPower(0);
        RFMotor.setPower(0);
        RBMotor.setPower(0);
    }


    public void turnGyro(double angle) {
        double turnPower = 0.3;

        angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
        double initialAngle = angles.firstAngle;
        double targetAngle = initialAngle - angle;
        LFMotor.setPower(turnPower);
        LBMotor.setPower(turnPower);
        RBMotor.setPower(-1 * turnPower);
        RFMotor.setPower(-1 * turnPower);
        while (angles.firstAngle > targetAngle && opModeIsActive()) {
            angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
            telemetry.addData("Orientation", angles.firstAngle);
            idle();
        }
        LBMotor.setPower(0.0);
        LFMotor.setPower(0.0);
        RBMotor.setPower(0.0);
        RFMotor.setPower(0.0);
    }
}
