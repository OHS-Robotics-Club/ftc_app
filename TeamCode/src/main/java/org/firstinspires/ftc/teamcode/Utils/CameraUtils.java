package org.firstinspires.ftc.teamcode.Utils;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.HardwareBot;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Rect;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;

public class CameraUtils {
    private static final String GOLD_LABEL = "Gold Mineral";
    private static final String SILVER_LABEL = "Silver Mineral";

    private HardwareBot bot;
    private LinearOpMode opMode;
    private DrivingUtils drive;

    /**
     * CameraUtils constructor to great Utils Object
     * Controls Camera Function
     * @param bot
     */
    public CameraUtils(HardwareBot bot, LinearOpMode opMode){
        this.bot = bot;
        this.opMode = opMode;
        drive = new DrivingUtils(bot, opMode);
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

    public void cameraBlockTFODPivot(double power){

        boolean foundBlock = false;
        int imageMid = 0;
        int buffer = 10;

        bot.mRightFrontMotor.setPower(-power);
        bot.mRightBackMotor.setPower(-power);

        bot.mLeftBackMotor.setPower(power);
        bot.mLeftFrontMotor.setPower(power);

        while (opMode.opModeIsActive() && !(foundBlock)) {
            List<Recognition> updatedRecognitions = bot.tfod.getUpdatedRecognitions();
            if (updatedRecognitions != null) {

                opMode.telemetry.addData("# Object Detected", updatedRecognitions.size());
                for (Recognition recognition : updatedRecognitions) {
                    opMode.telemetry.addData("Object: ", recognition.getLabel());
                    if(recognition.getLabel().equals(GOLD_LABEL)){
                        if((recognition.getLeft() > imageMid + buffer) && recognition.getLeft() < imageMid - buffer){
                            foundBlock = true;
                        }
                    }
                }
                opMode.telemetry.update();
            }
        }

        drive.stop();

    }


}
