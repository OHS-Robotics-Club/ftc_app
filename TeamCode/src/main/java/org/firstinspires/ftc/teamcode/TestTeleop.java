package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.PWMOutput;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

import java.util.Random;

public class TestTeleop extends LinearOpMode {
    DcMotor A;
    DcMotor B;
    PWMOutput b;

    Servo a;

    boolean hi = true;


    @Override
    public void runOpMode() throws InterruptedException {
        A = hardwareMap.dcMotor.get("");
        B = hardwareMap.dcMotor.get("");
        b = hardwareMap.pwmOutput.get("motor");

        while (opModeIsActive()){
            // Left Stick A Motor
            // Right Stick B Motor

            double left_stick_y = Range.clip(gamepad1.left_stick_y, -1.0, 1.0);
            double right_stick_y = Range.clip(gamepad1.right_stick_y, -1.0,1.0);

            A.setPower(left_stick_y);
            B.setPower(right_stick_y);

            a.setPosition(0.5);
            //b.setPulseWidthOutputTime();
        }

    }
}
