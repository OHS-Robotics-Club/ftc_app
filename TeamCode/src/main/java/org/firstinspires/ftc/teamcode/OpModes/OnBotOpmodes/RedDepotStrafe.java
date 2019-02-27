package org.firstinspires.ftc.teamcode.OpModes.OnBotOpmodes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.HardwareBot;
import org.firstinspires.ftc.teamcode.Utils.CameraUtils;
import org.firstinspires.ftc.teamcode.Utils.DrivingUtils;
import org.firstinspires.ftc.teamcode.Utils.GyroUtils;

@Autonomous(name = "AutonDepotRed", group = "Competition")
public class RedDepotStrafe extends LinearOpMode {

    HardwareBot bot = new HardwareBot();
    DrivingUtils drive;
    CameraUtils camera;
    GyroUtils gyro;
    double turnedAngle;

    @Override
    public void runOpMode() throws InterruptedException {
        // Initialize the Bot Object With Motors
        bot.init(hardwareMap, true, false);
        drive = new DrivingUtils(bot, this);
        camera = new CameraUtils(bot, this);
        gyro = new GyroUtils(bot, this);

        waitForStart();

        if (bot.tfod != null) {
            bot.tfod.activate();
        }

        //Descend (Drive Rack Motor)
        //TODO Write Limit Switch Based Descend Method
        //drive.hangLimit(true);

        sleep(500);

        double strafingPower = -.45;                             //strafe away from lader
        double powFact = 1;
        bot.mLeftFrontMotor.setPower(strafingPower * powFact);
        bot.mLeftBackMotor.setPower(-strafingPower * powFact);
        bot.mRightFrontMotor.setPower(-strafingPower * powFact);
        bot.mRightBackMotor.setPower(strafingPower * powFact);

        sleep(500);

        drive.stop();

        sleep(500);

        drive.driveUniform(-0.3, -5);
        //
        sleep(500);
        drive.stop();

        //Turn using Camera
        //TODO Track Initial Gyro Turn
        gyro.turnDegrees(30, -0.4);
        turnedAngle = gyro.getHeading();

        //Pivot with Camera Input
        camera.cameraBlockPivot(0.4);
        sleep(500);
        gyro.turnDegrees(10, -0.4);
        turnedAngle = gyro.getHeading() - turnedAngle;

        //Drive Forward
        //TODO Find Encoder Distance and Use Encoder
        drive.driveUniform(0.4, 37.5);

        //sleep(2000);
        //drive.stop();

        //Drive Forward More
        //TODO Find Encoder Value
        drive.driveUniform(0.5, 8);

        //sleep(2000);
        //drive.stop();

        BlockPosition pos = BlockPosition.CENTER;
        telemetry.addData("Angle: ", turnedAngle);
        telemetry.update();

        if (turnedAngle < 40) {
            pos = BlockPosition.RIGHT;
        } else if (turnedAngle > 70) {
            pos = BlockPosition.LEFT;
        }

        telemetry.addData("Position: ", pos);
        telemetry.update();
        //Returns Enum Value

        //Robot Perspective
        switch (pos) {

            case CENTER:
                // Drive Forward
                //TODO Find Encoder Value
                drive.driveUniform(0.5, 6);

                //TODO Find Continous Servo Values
                //Dump Our Marker (COW)
                bot.sPickup.setPosition(0.7);
                sleep(500);
                bot.sPickup.setPosition(0.5);

                //Go Backwards
                drive.driveUniform(-0.5, 8);
                break;

            case LEFT:
                //TODO Turn Based On Gyro Measure or Direction
                gyro.turnDegrees(30, 0.4);

                // Drive Forward
                //TODO Find Encoder Value
                drive.driveUniform(0.5, 8);

                //TODO Find Continous Servo Values
                //Dump Our Marker (COW)
                bot.sPickup.setPosition(0.7);
                sleep(500);
                bot.sPickup.setPosition(0.5);

                //Go Backwards
                drive.driveUniform(-0.5, 8);
                break;

            case RIGHT:

                //TODO Turn Based On Gyro Measure or Direction
                gyro.turnDegrees(-60, 0.4);

                // Drive Forward
                //TODO Find Encoder Value
                drive.driveUniform(0.5, 8);

                //TODO Find Continous Servo Values
                //Dump Our Marker (COW)
                bot.sPickup.setPosition(0.7);
                sleep(500);
                bot.sPickup.setPosition(0.5);

                //Go Backwards
                drive.driveUniform(-0.5, 8);

                break;
        }


    }

    enum BlockPosition {
        LEFT, RIGHT, CENTER;
    }
}
