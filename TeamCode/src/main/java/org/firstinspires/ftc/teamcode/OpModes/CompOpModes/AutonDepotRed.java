package org.firstinspires.ftc.teamcode.OpModes.CompOpModes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.HardwareBot;

public class AutonDepotRed extends LinearOpMode {
    HardwareBot bot;

    @Override
    public void runOpMode() throws InterruptedException {
        // Initialize the Bot Object With Motors
        bot.init(hardwareMap, true, true);
        waitForStart();





    }
}
