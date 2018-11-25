package org.firstinspires.ftc.teamcode.TeleOperated;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name = "Oly_Cow_Telop", group = "Comp")
public class TestTeleop extends LinearOpMode {

    final double HANG_POWER = 0.9;
    final double STOP_POWER = 0.0;
    final double LIFT_POWER = 0.3;
    final double PICKUP_EXTEND_POWER = 0.3;
    final double PICKUP_ROTATE_POWER = 0.3;
    double powFact = 1;

    DcMotor mLeftBackMotor, mRightBackMotor, mRightFrontMotor, mLeftFrontMotor;
    DcMotor mPickupRotator, mPickupExtender, mLiftExtender, mHangingMotor;
    Servo sDump, sPickup;

    boolean left_pressed = false;
    boolean right_pressed = false;
    boolean pressedOnce = false;
    boolean lowPower = false;

    double dump_pos = 1.0;
    double pickup_pos = 0.0;

    @Override
    public void runOpMode() throws InterruptedException {
        mLeftBackMotor = hardwareMap.dcMotor.get("backLeft");
        mLeftFrontMotor = hardwareMap.dcMotor.get("frontLeft");

        mRightBackMotor = hardwareMap.dcMotor.get("backRight");
        mRightFrontMotor = hardwareMap.dcMotor.get("frontRight");

        mPickupExtender = hardwareMap.dcMotor.get("horizontalSlideExtend");
        mPickupRotator = hardwareMap.dcMotor.get("linearSlideRotate");
        mLiftExtender = hardwareMap.dcMotor.get("verticalSlide");
        mHangingMotor = hardwareMap.dcMotor.get("hangingMotor");

        sPickup = hardwareMap.servo.get("SpatuServo");
        sDump = hardwareMap.servo.get("dumpServo");

        mRightFrontMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        mRightBackMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        mPickupRotator.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        mHangingMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        mLiftExtender.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        mHangingMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        waitForStart();

        while (opModeIsActive()) {

            // Raise and Lower Dumping Lift (A and Y)
            if (gamepad1.a) {
                mLiftExtender.setPower(LIFT_POWER * powFact);
            } else if (gamepad1.y) {
                mLiftExtender.setPower(-LIFT_POWER * powFact);
            } else {
                mLiftExtender.setPower(STOP_POWER * powFact);
            }

            // Extend and Retract Pickup Linear Slide (B and X)
            if (gamepad1.b) {
                mPickupExtender.setPower(PICKUP_EXTEND_POWER * powFact);
            } else if (gamepad1.x) {
                mPickupExtender.setPower(-PICKUP_EXTEND_POWER * powFact);
            } else {
                mPickupExtender.setPower(STOP_POWER * powFact);
            }

            // Extend and Retract Hanging Motor (Right Bumper and Left Bumper)
            if (gamepad1.right_bumper) {
                mHangingMotor.setPower(HANG_POWER * powFact);
            } else if (gamepad1.left_bumper) {
                mHangingMotor.setPower(-HANG_POWER * powFact);
            } else {
                mHangingMotor.setPower(STOP_POWER * powFact);
            }

            // Rotate Pickup Linear Slide (Up and Down)
            if (gamepad1.dpad_up) {
                mPickupRotator.setPower(PICKUP_ROTATE_POWER * powFact);
            } else if (gamepad1.dpad_down) {
                mPickupRotator.setPower(-PICKUP_ROTATE_POWER * powFact);
            } else {
                mPickupRotator.setPower(STOP_POWER * powFact);
            }

            // Dump Servo (Left Dpad and Right Dpad)
            if (gamepad1.dpad_right) {
                right_pressed = true;
            } else {
                if (right_pressed) {
                    dump_pos += 0.05;
                    sDump.setPosition(dump_pos);
                    right_pressed = false;
                }
            }

            if (gamepad1.dpad_left) {
                left_pressed = true;
            } else {
                if (left_pressed) {
                    dump_pos -= 0.05;
                    sDump.setPosition(dump_pos);
                    left_pressed = false;
                }
            }
            telemetry.addData("Dump_Position", dump_pos);

            // Drive base (Left and Right Joystick)
            // Strafing (Left and Right Triggers)

            double strafingPower = 0.0;
            double lPower, rPower;

            lPower = Range.clip((gamepad1.left_stick_y), -1.0, 1.0);
            rPower = Range.clip((gamepad1.right_stick_y), -1.0, 1.0);

            if (gamepad1.left_trigger > 0 && gamepad1.right_trigger > 0) {
                pressedOnce = true;
            } else {
                if(pressedOnce){
                    pressedOnce = false;
                    powFact = 0.2;
                } else{
                    powFact = 1.0;
                }
                if (gamepad1.left_trigger > 0) { // Strafe left

                    strafingPower = gamepad1.left_trigger;
                    mLeftFrontMotor.setPower(strafingPower * powFact);
                    mLeftBackMotor.setPower(-strafingPower * powFact);
                    mRightFrontMotor.setPower(-strafingPower * powFact);
                    mRightBackMotor.setPower(strafingPower * powFact);
                    telemetry.addData("Drive", "Strafing left");

                } else if (gamepad1.right_trigger > 0) { // Strafe right

                    strafingPower = gamepad1.right_trigger;
                    mLeftFrontMotor.setPower(-strafingPower * powFact);
                    mLeftBackMotor.setPower(strafingPower * powFact);
                    mRightFrontMotor.setPower(strafingPower * powFact);
                    mRightBackMotor.setPower(-strafingPower * powFact);
                    telemetry.addData("Drive", "Strafing right");

                } else { // Tank driving

                    mLeftFrontMotor.setPower(lPower * powFact);
                    mLeftBackMotor.setPower(lPower * powFact);
                    mRightFrontMotor.setPower(rPower * powFact);
                    mRightBackMotor.setPower(rPower * powFact);
                    telemetry.addData("Drive", "Left Power - " + lPower + "; Right Power - " + rPower);

                }
            }
            telemetry.update();

        }

    }
}
