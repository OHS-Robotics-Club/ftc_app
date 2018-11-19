package org.firstinspires.ftc.teamcode.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

@Autonomous(name="AutonTrialBASIC", group="Competition")
public class AutonTrialBASIC extends LinearOpMode {
    DcMotor LFMotor, RFMotor, LBMotor, RBMotor, SpatuSlideMotor,  LiftSlideMotor, SpatuSpoolMotor, hangingMotor;
    @Override
    public void runOpMode() throws InterruptedException {
        telemetry.addData("Caption","Init Started");
        LFMotor = hardwareMap.dcMotor.get("frontLeft");
        LBMotor = hardwareMap.dcMotor.get("backLeft");
        RFMotor = hardwareMap.dcMotor.get("frontRight");
        RBMotor = hardwareMap.dcMotor.get("backRight");
        hangingMotor = hardwareMap.dcMotor.get("hangingMotor");

        RFMotor.setDirection(DcMotor.Direction.REVERSE);
        RBMotor.setDirection(DcMotor.Direction.REVERSE);

        hangingMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        telemetry.addData("Caption","Init Finished");

        waitForStart();

        hangingMotor.setPower(0.9);
        sleep(3000);
        hangingMotor.setPower(0.0);

        LFMotor.setPower(0.2);
        LBMotor.setPower(0.2);

        RFMotor.setPower(-0.2);
        RBMotor.setPower(-0.2);

        sleep(3300); //3000 too small

        LFMotor.setPower(0.0);
        LBMotor.setPower(0.0);

        RFMotor.setPower(0.0);
        RBMotor.setPower(0.0);

        sleep(1000);

        LFMotor.setPower(0.2);
        LBMotor.setPower(0.2);

        RFMotor.setPower(-0.2);
        RBMotor.setPower(-0.2);




        sleep(1000);

        RFMotor.setPower(0.2);
        RBMotor.setPower(0.2);
        LFMotor.setPower(0.2);
        LBMotor.setPower(0.2);

        sleep(3000); // Will need to be adjusted

        LFMotor.setPower(0);
        LBMotor.setPower(0);
        RFMotor.setPower(0);
        RBMotor.setPower(0);
    }
}
