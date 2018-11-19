package org.firstinspires.ftc.teamcode.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.corningrobotics.enderbots.endercv.CameraViewDisplay;
import org.firstinspires.ftc.teamcode.Demos.BlockVisionGRIP;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Rect;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.Locale;

@Autonomous(name="AutonTrialPushBot", group="Competition")
public class AutonPushBot extends LinearOpMode {

    DcMotor mLeft, mRight, mHanging;
    BlockVisionGRIP blockVision;
    @Override
    public void runOpMode() throws InterruptedException {
        mLeft = hardwareMap.dcMotor.get("left");
        mRight = hardwareMap.dcMotor.get("right");
        mHanging = hardwareMap.dcMotor.get("hangingMotor");
        double resizeImageWidth = 320.0;
        double resizeImageHeight = 240.0;
        double imageMidpoint = resizeImageWidth / 2;

        mRight.setDirection(DcMotor.Direction.REVERSE);

        mHanging.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        blockVision = new BlockVisionGRIP();

        telemetry.addData("Caption","Init Started");

        blockVision.init(hardwareMap.appContext, CameraViewDisplay.getInstance());
        blockVision.enable();

        telemetry.addData("Caption","Init Finished");

        waitForStart();

        mHanging.setPower(0.9);
        sleep(2000);
        mHanging.setPower(0.0);

        mLeft.setPower(0.2);
        mRight.setPower(-0.2);

        ArrayList<MatOfPoint> contours = blockVision.filterContoursOutput();
        for (int i = 0; i < contours.size(); i++) {
            Rect boundingRect = Imgproc.boundingRect(contours.get(i));
            double xCenter = (boundingRect.x + boundingRect.width)/2;
            double yCenter = (boundingRect.y + boundingRect.height)/2;
            if (xCenter > imageMidpoint - 10 && xCenter < imageMidpoint + 10) {
                telemetry.addData("Center Found ", "Driving");

                mRight.setPower(0.2);
                mLeft.setPower(0.2);

                wait(3000); // Will need to be adjusted

                mRight.setPower(0);
                mLeft.setPower(0);
            }


            telemetry.addData("contour" + Integer.toString(i),
                    String.format(Locale.getDefault(), "(%d, %d)", (boundingRect.x + boundingRect.width) / 2, (boundingRect.y + boundingRect.height) / 2));
            telemetry.update();
        }
    }
}
