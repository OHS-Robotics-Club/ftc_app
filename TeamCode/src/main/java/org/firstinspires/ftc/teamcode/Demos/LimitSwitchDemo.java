package org.firstinspires.ftc.teamcode.Demos;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DigitalChannel;

@Autonomous(name="Limit Switch Test", group="Tests")
public class LimitSwitchDemo extends LinearOpMode {
    DigitalChannel limitSwitch;
    @Override
    public void runOpMode() {
        limitSwitch = hardwareMap.digitalChannel.get("limitSwitch");
        limitSwitch.setMode(DigitalChannel.Mode.INPUT);
        waitForStart();

        while (opModeIsActive()){
            boolean limit = limitSwitch.getState();
            telemetry.addData("State: ", limit);
            telemetry.update();
        }
    }
}
