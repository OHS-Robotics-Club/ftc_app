package org.firstinspires.ftc.teamcode.OpModes.CompOpModes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.HardwareBot;
import org.firstinspires.ftc.teamcode.Utils.CameraUtils;
import org.firstinspires.ftc.teamcode.Utils.DrivingUtils;
import org.firstinspires.ftc.teamcode.Utils.GyroUtils;

public class AutonAwayRed extends LinearOpMode {
    HardwareBot bot;
    DrivingUtils drive;
    CameraUtils camera;
    GyroUtils gyro;

    enum BlockPosition {
        LEFT, RIGHT, CENTER;
    }

    @Override
    public void runOpMode() throws InterruptedException {
        // Initialize the Bot Object With Motors
        bot.init(hardwareMap, true, true);
        drive = new DrivingUtils(bot, this);
        camera = new CameraUtils(bot, this);

        waitForStart();

        //Descend (Drive Rack Motor)
        //TODO Write Limit Switch Based Descend Method

        //Turn using Camera
        //TODO Track Initial Gyro Turn
        camera.cameraBlockPivot(0.4);

        //Drive Forward
        //TODO Find Encoder Distance and Use Encoder
        drive.driveUniform(0.5);
        sleep(2000);
        drive.stop();

        /*
        //Drive Forward More
        //TODO Find Encoder Value
        drive.driveUniform(0.5);
        sleep(2000);
        drive.stop();
        */

        BlockPosition pos = BlockPosition.CENTER;
        //Returns Enum Value

        //Robot Perspective
        switch (pos) {

            case CENTER:
                // Drive back to the rover
                //TODO Find Encoder Value
                drive.driveUniform(-0.5);
                sleep(2000);
                drive.stop();

                //turn to face far wall
                //TODO find the gyro value
                drive.tankLeft(0.4);
                sleep(1000);
                drive.stop();

                //Drive towards the wall
                //TODO find the encoder value
                drive.driveUniform(0.5);
                sleep(2000);
                drive.stop();

                //Turn to drive towards depot
                gyro.turnDegrees(90,0.4);

                //Drive forward to depot
                //TODO find the encoder value
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
                // Drive back to the rover
                //TODO Find Encoder Value
                drive.driveUniform(-0.5);
                sleep(2000);
                drive.stop();

                //turn to face far wall
                //TODO find the gyro value
                drive.tankLeft(0.4);
                sleep(700);
                drive.stop();

                //Drive towards the wall
                //TODO find the encoder value
                drive.driveUniform(0.5);
                sleep(2000);
                drive.stop();

                //Turn to drive towards depot
                gyro.turnDegrees(90,0.4);

                //Drive forward to depot
                //TODO find the encoder value
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
                // Drive back to the rover
                //TODO Find Encoder Value
                drive.driveUniform(-0.5);
                sleep(2000);
                drive.stop();

                //turn to face far wall
                //TODO find the gyro value
                drive.tankLeft(0.4);
                sleep(1300);
                drive.stop();

                //Drive towards the wall
                //TODO find the encoder value
                drive.driveUniform(0.5);
                sleep(2000);
                drive.stop();

                //Turn to drive towards depot
                gyro.turnDegrees(90,0.4);

                //Drive forward to depot
                //TODO find the encoder value
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