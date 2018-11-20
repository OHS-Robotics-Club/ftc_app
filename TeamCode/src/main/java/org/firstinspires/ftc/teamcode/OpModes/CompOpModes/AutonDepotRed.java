package org.firstinspires.ftc.teamcode.OpModes.CompOpModes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.HardwareBot;
import org.firstinspires.ftc.teamcode.Utils.CameraUtils;
import org.firstinspires.ftc.teamcode.Utils.DrivingUtils;
import org.firstinspires.ftc.teamcode.Utils.GyroUtils;

public class AutonDepotRed extends LinearOpMode {
    HardwareBot bot;
    DrivingUtils drive;
    CameraUtils camera;
    GyroUtils gyro;
    double turnedAngle;

    enum BlockPosition{
        LEFT,RIGHT,CENTER;
    }

    @Override
    public void runOpMode() throws InterruptedException {
        // Initialize the Bot Object With Motors
        bot.init(hardwareMap, true, true);
        drive = new DrivingUtils(bot, this);
        camera = new CameraUtils(bot,this);
        gyro = new GyroUtils(bot, this);

        waitForStart();

        //Descend (Drive Rack Motor)
        //TODO Write Limit Switch Based Descend Method
        drive.hangLimit(true);

        //Turn using Camera
        //TODO Track Initial Gyro Turn
        gyro.turnDegrees(30,0.4);
        turnedAngle = gyro.getHeading();

        //Pivot with Camera Input
        camera.cameraBlockPivot(0.4);
        turnedAngle = gyro.getHeading() - turnedAngle;

        //Drive Forward
        //TODO Find Encoder Distance and Use Encoder
        drive.driveUniform(0.4);
        sleep(2000);
        drive.stop();

        //Drive Forward More
        //TODO Find Encoder Value
        drive.driveUniform(0.5);
        sleep(2000);
        drive.stop();

        BlockPosition pos = BlockPosition.CENTER;
        //Returns Enum Value

        //Robot Perspective
        switch (pos){

            case CENTER:
                // Drive Forward
                //TODO Find Encoder Value
                drive.driveUniform(0.5);
                sleep(2000);
                drive.stop();

                //TODO Find Continous Servo Values
                //Dump Our Marker (COW)
                bot.sPickup.setPosition(0.7);
                sleep(500);
                bot.sPickup.setPosition(0.5);
                break;

            case LEFT:
                //TODO Turn Based On Gyro Measure or Direction
                drive.tankRight(0.3);
                sleep(1000);
                drive.stop();

                // Drive Forward
                //TODO Find Encoder Value
                drive.driveUniform(0.5);
                sleep(2000);
                drive.stop();

                //TODO Find Continous Servo Values
                //Dump Our Marker (COW)
                bot.sPickup.setPosition(0.7);
                sleep(500);
                bot.sPickup.setPosition(0.5);

                break;
            case RIGHT:

                //TODO Turn Based On Gyro Measure or Direction
                drive.tankLeft(0.3);
                sleep(1000);
                drive.stop();

                // Drive Forward
                //TODO Find Encoder Value
                drive.driveUniform(0.5);
                sleep(2000);
                drive.stop();

                //TODO Find Continous Servo Values
                //Dump Our Marker (COW)
                bot.sPickup.setPosition(0.7);
                sleep(500);
                bot.sPickup.setPosition(0.5);
                break;
        }






    }
}
