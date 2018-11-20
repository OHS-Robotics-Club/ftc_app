package org.firstinspires.ftc.teamcode.Utils;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.HardwareBot;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Rect;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.Locale;

public class CameraUtils {
    private HardwareBot bot;
    private LinearOpMode opMode;

    /**
     * CameraUtils constructor to great Utils Object
     * Controls Camera Function
     * @param bot
     */
    public CameraUtils(HardwareBot bot, LinearOpMode opMode){
        this.bot = bot;
        this.opMode = opMode;
    }

    /**
     * Pivot in a certain direction
     * Until the Block is found
     * Using Camera GRIP Functionality
     *
     * Positive Power is a Right Turn
     * From the Front
     * @param power
     */
    public void cameraBlockPivot(double power){

        boolean foundBlock = false;
        double imageMidpoint = 82;

        bot.mRightFrontMotor.setPower(-power);
        bot.mRightBackMotor.setPower(-power);

        bot.mLeftBackMotor.setPower(power);
        bot.mLeftFrontMotor.setPower(power);

        while (opMode.opModeIsActive() && !(foundBlock)) {

            if(bot.blockVision.filterContoursOutput() != null) {

                ArrayList<MatOfPoint> contours = bot.blockVision.filterContoursOutput();
                for (int i = 0; i < contours.size(); i++) {

                    Rect boundingRect = Imgproc.boundingRect(contours.get(i));
                    double xCenter = (boundingRect.x + boundingRect.width) / 2;
                    double yCenter = (boundingRect.y + boundingRect.height) / 2;

                    if (xCenter >  imageMidpoint - 20 && xCenter < imageMidpoint + 20) {

                        opMode.telemetry.addData("Caption: ", "Found Block");
                        opMode.telemetry.update();

                        bot.mLeftFrontMotor.setPower(0.0);
                        bot.mLeftBackMotor.setPower(0.0);

                        bot.mRightBackMotor.setPower(0.0);
                        bot.mRightFrontMotor.setPower(0.0);

                        foundBlock = true;
                    }


                    opMode.telemetry.addData("contour" + Integer.toString(i),
                            String.format(Locale.getDefault(), "(%d, %d)", (boundingRect.x + boundingRect.width) / 2, (boundingRect.y + boundingRect.height) / 2));
                    opMode.telemetry.update();
                }
            }
        }

    }


}
