package org.firstinspires.ftc.teamcode.OpModes.CompOpModes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.HardwareBot;
import org.firstinspires.ftc.teamcode.Utils.DrivingUtils;

public class TeleOpComp extends LinearOpMode {

    final double HANG_POWER = 0.9;
    final double STOP_POWER = 0.0;
    final double LIFT_POWER = 0.3;
    final double PICKUP_EXTEND_POWER = 0.3;
    final double PICKUP_ROTATE_POWER = 0.3;

    final double PICKUP_SERVO_POS_FORW = 0.3;
    final double PICKUP_SERVO_POS_BACK = 0.7;
    final double PICKUP_SERVO_POS_STOP = 0.5;

    boolean left_pressed = false;
    boolean right_pressed = false;
    boolean pow_pressed = false;
    boolean pressedOnce = false;
    boolean lowPower = false;

    double dump_pos = 1.0;
    double pickup_pos = 0.0;
    double powFact = 1.0;

    private HardwareBot bot;
    private DrivingUtils drive;

    @Override
    public void runOpMode() throws InterruptedException {
        // Initialize Hardware Bot
        bot = new HardwareBot();
        bot.init(hardwareMap,false,false);
        drive = new DrivingUtils(bot, this);
        telemetry.addData("Initialization: ", "Complete");

        // Finish Initialization Phase
        waitForStart();

        while (opModeIsActive()){

            /*
             * Gamepad 2 Controls and Code
             */
            // Raise and Lower Dumping Lift (A and Y)
            // Player 2 Gamepad 2
            if (gamepad2.a) {
                bot.mLiftExtender.setPower(LIFT_POWER);
            } else if (gamepad2.y) {
                bot.mLiftExtender.setPower(-LIFT_POWER);
            } else {
                bot.mLiftExtender.setPower(STOP_POWER);
            }

            // Extend and Retract Pickup Linear Slide (B and X)
            // Player 2 Gamepad 2
            if (gamepad2.b) {
                bot.mPickupExtender.setPower(PICKUP_EXTEND_POWER);
            } else if (gamepad2.x) {
                bot.mPickupExtender.setPower(-PICKUP_EXTEND_POWER);
            } else {
                bot.mPickupExtender.setPower(STOP_POWER);
            }

            // Rotate Pickup Linear Slide (Up and Down)
            // Player 2 Gamepad 2
            if (gamepad2.dpad_up) {
                bot.mPickupRotator.setPower(PICKUP_ROTATE_POWER);
            } else if (gamepad2.dpad_down) {
                bot.mPickupRotator.setPower(-PICKUP_ROTATE_POWER);
            } else {
                bot.mPickupRotator.setPower(STOP_POWER);
            }

            // Dump Servo (Left Bumper and Right Bumper)
            // Player 2 Gamepad 2
            if (gamepad2.right_bumper) {
                right_pressed = true;
            } else {
                if (right_pressed) {
                    dump_pos += 0.05;
                    bot.sDump.setPosition(dump_pos);
                    right_pressed = false;
                }
            }

            if (gamepad2.left_bumper) {
                left_pressed = true;
            } else {
                if (left_pressed) {
                    dump_pos -= 0.05;
                    bot.sDump.setPosition(dump_pos);
                    left_pressed = false;
                }
            }
            telemetry.addData("Dump_Position", dump_pos);

            // Pickup Servo (Left Trigger and Right Trigger)
            // Player 2 Gamepad 2
            if (gamepad2.right_trigger > 0.0) {
                bot.sPickup.setPosition(PICKUP_SERVO_POS_BACK);
            } else if (gamepad2.left_trigger > 0.0) {
                bot.sPickup.setPosition(PICKUP_SERVO_POS_FORW);
            } else {
                bot.sPickup.setPosition(PICKUP_SERVO_POS_STOP);
            }
            telemetry.addData("Pickup_Position", bot.sPickup.getPosition());

            /*
             * Gamepad 1 Controls and Code
             */

            // Drive base (Left and Right Joystick)
            // Strafing (Left and Right Triggers)

            // Extend and Retract Hanging Motor (Right Bumper and Left Bumper)
            // Player 1 Gamepad 1
            if (gamepad1.right_bumper) {
                bot.mHangingMotor.setPower(HANG_POWER);
            } else if (gamepad1.left_bumper) {
                bot.mHangingMotor.setPower(-HANG_POWER);
            } else {
                bot.mHangingMotor.setPower(STOP_POWER);
            }

            // Dump Servo (Left Bumper and Right Bumper)
            // Player 2 Gamepad 2

            if (gamepad1.a) {
                pow_pressed = true;
            } else {
                if (pow_pressed) {
                    lowPower = !(lowPower);
                    pow_pressed = false;
                    if(lowPower){
                        powFact = 0.5;
                    }
                    else {
                        powFact = 1.0;
                    }
                }
            }


            double strafingPower = 0.0;
            double lPower, rPower;

            lPower = Range.clip((gamepad1.left_stick_y), -1.0, 1.0);
            rPower = Range.clip((gamepad1.right_stick_y), -1.0, 1.0);

            if (gamepad1.left_trigger > 0) { // Strafe left

                strafingPower = gamepad1.left_trigger;
                bot.mLeftFrontMotor.setPower(strafingPower * powFact);
                bot.mLeftBackMotor.setPower(-strafingPower * powFact);
                bot.mRightFrontMotor.setPower(-strafingPower * powFact);
                bot.mRightBackMotor.setPower(strafingPower * powFact);
                telemetry.addData("Drive", "Strafing left");

            } else if (gamepad1.right_trigger > 0) { // Strafe right

                strafingPower = gamepad1.right_trigger;
                bot.mLeftFrontMotor.setPower(-strafingPower * powFact);
                bot.mLeftBackMotor.setPower(strafingPower * powFact);
                bot.mRightFrontMotor.setPower(strafingPower * powFact);
                bot.mRightBackMotor.setPower(-strafingPower * powFact);
                telemetry.addData("Drive", "Strafing right");

            } else { // Tank driving

                bot.mLeftFrontMotor.setPower(lPower * powFact);
                bot.mLeftBackMotor.setPower(lPower * powFact);
                bot.mRightFrontMotor.setPower(rPower * powFact);
                bot.mRightBackMotor.setPower(rPower * powFact);
                telemetry.addData("Drive", "Left Power - " + lPower + "; Right Power - " + rPower);

            }
            telemetry.update();

        }


    }
}
