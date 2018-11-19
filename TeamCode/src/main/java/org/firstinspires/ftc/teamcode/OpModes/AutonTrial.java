package org.firstinspires.ftc.teamcode.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.corningrobotics.enderbots.endercv.CameraViewDisplay;
import org.firstinspires.ftc.teamcode.Demos.BlockVisionGRIP;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Rect;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.Locale;

@Autonomous(name="AutonTrial", group="Competition")
public class AutonTrial extends LinearOpMode {
    DcMotor LFMotor, RFMotor, LBMotor, RBMotor, SpatuSlideMotor,  LiftSlideMotor, SpatuSpoolMotor, hangingMotor;
    Servo sDump;
    BlockVisionGRIP blockVision;
    @Override
    public void runOpMode() throws InterruptedException {
        LFMotor = hardwareMap.dcMotor.get("frontLeft");
        LBMotor = hardwareMap.dcMotor.get("backLeft");
        RFMotor = hardwareMap.dcMotor.get("frontRight");
        RBMotor = hardwareMap.dcMotor.get("backRight");
        hangingMotor = hardwareMap.dcMotor.get("hangingMotor");
        sDump = hardwareMap.servo.get("dumpServo");

        double resizeImageWidth = 320.0;
        double resizeImageHeight = 240.0;

        double imageMidpoint = 82;

        RFMotor.setDirection(DcMotor.Direction.REVERSE);
        RBMotor.setDirection(DcMotor.Direction.REVERSE);

        hangingMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        blockVision = new BlockVisionGRIP();

        telemetry.addData("Caption","Init Started");

        blockVision.init(hardwareMap.appContext, CameraViewDisplay.getInstance());
        blockVision.enable();

        telemetry.addData("Caption","Init Finished");

        waitForStart();

        sDump.setPosition(0.5);

        hangingMotor.setPower(0.9);
        sleep(3000);
        hangingMotor.setPower(0.0);

        LFMotor.setPower(0.2);
        LBMotor.setPower(0.2);

        RFMotor.setPower(-0.2);
        RBMotor.setPower(-0.2);

        sleep(3300); // 3000 too small

        LFMotor.setPower(0.0);
        LBMotor.setPower(0.0);

        RFMotor.setPower(0.0);
        RBMotor.setPower(0.0);

        sleep(1000);

        LFMotor.setPower(0.2);
        LBMotor.setPower(0.2);

        RFMotor.setPower(-0.2);
        RBMotor.setPower(-0.2);


        boolean foundBlock = false;



        while (opModeIsActive() && !(foundBlock)) {

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

        LFMotor.setPower(0.2);
        LBMotor.setPower(0.2);

        RFMotor.setPower(-0.2);
        RBMotor.setPower(-0.2);

        sleep(2000); //3000 too small

        LFMotor.setPower(0.0);
        LBMotor.setPower(0.0);

        RFMotor.setPower(0.0);
        RBMotor.setPower(0.0);

        sleep(1000);

        RFMotor.setPower(0.2);
        RBMotor.setPower(0.2);
        LFMotor.setPower(0.2);
        LBMotor.setPower(0.2);

        sleep(4000); // Will need to be adjusted

        LFMotor.setPower(0);
        LBMotor.setPower(0);
        RFMotor.setPower(0);
        RBMotor.setPower(0);

        sDump.setPosition(0.4);
        sleep(1000);
        sDump.setPosition(0.5);


    }
}
