package org.firstinspires.ftc.teamcode.Demos;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.HardwareBot;
import org.firstinspires.ftc.teamcode.Utils.GyroUtils;

@Autonomous(name="GyroDemo", group="Tests")
public class GyroDemo extends LinearOpMode {
    HardwareBot bot;
    GyroUtils gyro;
    @Override
    public void runOpMode() {
        bot = new HardwareBot();
        bot.init(hardwareMap, true, true);
        gyro = new GyroUtils(bot,this);
        waitForStart();

        //gyro.turnDegrees(90,0.4);
        double angle = gyro.getHeading();
        while (opModeIsActive()){
            angle = gyro.getHeading();
            telemetry.addData("Angle: ", angle);
            telemetry.update();
        }


    }
}
