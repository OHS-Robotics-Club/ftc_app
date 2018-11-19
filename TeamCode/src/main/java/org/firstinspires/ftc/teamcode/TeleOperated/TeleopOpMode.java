package org.firstinspires.ftc.teamcode.TeleOperated;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.util.Range;
import com.qualcomm.robotcore.hardware.Servo;



//@TeleOp(name="OlyCow_TeleOp", group="Linear Opmode")

public class TeleopOpMode extends LinearOpMode {

    DcMotor LFMotor, RFMotor, LBMotor, RBMotor, SpatuSlideMotor,  LiftSlideMotor, SpatuSpoolMotor, hangingMotor;
    Servo SpatuServo, dumpServo;
    float   leftPower=0, rightPower=0, xValue=0, yValue=0, rightY=0;
    double sPower, bMove = 0.75, servoMove = 0, sMove;
    boolean isPressedUp = false;
    boolean isPressedDown = false;
    double hangingPower = 0.5;

    @Override

    public void runOpMode() throws InterruptedException
    {
        LFMotor = hardwareMap.dcMotor.get("frontLeft");
        LBMotor = hardwareMap.dcMotor.get("backLeft");
        RFMotor = hardwareMap.dcMotor.get("frontRight");
        RBMotor = hardwareMap.dcMotor.get("backRight");

        SpatuSlideMotor = hardwareMap.dcMotor.get("verticalSlide");
        LiftSlideMotor = hardwareMap.dcMotor.get("horizontalSlideExtend");
        SpatuSpoolMotor = hardwareMap.dcMotor.get("linearSlideRotate");

        hangingMotor = hardwareMap.dcMotor.get("hangingMotor");

        SpatuServo = hardwareMap.servo.get("SpatuServo");
        dumpServo = hardwareMap.servo.get("dumpServo");

        RFMotor.setDirection(DcMotor.Direction.REVERSE);
        RBMotor.setDirection(DcMotor.Direction.REVERSE);

        SpatuSlideMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        SpatuSpoolMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        LiftSlideMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        hangingMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);


        //telemetry.addData("Mode", "waiting");
        //telemetry.update();

        // wait for start button.

        waitForStart();

        //telemetry.addData("Mode", "waiting");
        //telemetry.update;

        sleep(200);


        while (opModeIsActive())
        {
            //making the basic movement with joystick

            yValue = gamepad1.right_stick_y;
            xValue = gamepad1.right_stick_x;

            leftPower =  yValue - xValue;
            rightPower = yValue + xValue;

            LFMotor.setPower(Range.clip(leftPower, -bMove, bMove));
            LBMotor.setPower(Range.clip(leftPower, -bMove, bMove));
            RBMotor.setPower(Range.clip(rightPower, -bMove, bMove));
            RFMotor.setPower(Range.clip(rightPower, -bMove, bMove));


            telemetry.addData("Mode", "running");
            telemetry.addData("stick", "  y=" + yValue + "  x=" + xValue);
            telemetry.addData("power", "  left=" + leftPower + "  right=" + rightPower);
            telemetry.update();

            //allowing the robot to strafe



            if (gamepad1.left_bumper == true){

                LFMotor.setPower(0.5);
                LBMotor.setPower(-0.4);
                RFMotor.setPower(-0.4);
                RBMotor.setPower(0.5);

            } else if (gamepad1.right_bumper == true){

                LFMotor.setPower(-0.5);
                LBMotor.setPower(0.4);
                RFMotor.setPower(0.4);
                RBMotor.setPower(-0.5);

            }


            //making the Lift Linear Slide run to lift or lower
            if (gamepad1.y == true){
                LiftSlideMotor.setPower(bMove);

                //telemetry.addData("Depositing Linear Slide:","  Lifting");
                //telemetry.update();

            }else if (gamepad1.a == true){
                LiftSlideMotor.setPower(-bMove);

                //telemetry.addData("Depositing Linear Slide:","  Lowering");
                //telemetry.update();

            }else{
                LiftSlideMotor.setPower(0);

                //telemetry.addData("Depositing Linear Slide:","  Still");
                //telemetry.update();

            }

            //making the Spatula Linear Slide run to extend or retract
            if (gamepad1.x == true){
                SpatuSlideMotor.setPower(bMove);

                //telemetry.addData("Spatula Linear Slide:","  Extending");
                //telemetry.update();


            } else if (gamepad1.b  == true){
                SpatuSlideMotor.setPower(-bMove);

                //telemetry.addData("Spatula Linear Slide:","  Retracting");
                //telemetry.update();

            }else{
                SpatuSlideMotor.setPower(0);

                //telemetry.addData("Spatula Linear Slide:","  Still");
                //telemetry.update();

            }

            //making the Spatula Linear Slide rotate with the spool
            if (gamepad1.dpad_up == true){
                SpatuSpoolMotor.setPower(bMove);

                //telemetry.addData("Spatula Linear Slide:","  Extending");
                //telemetry.update();


            } else if (gamepad1.dpad_down  == true){
                SpatuSpoolMotor.setPower(-bMove);

                //telemetry.addData("Spatula Linear Slide:","  Retracting");
                //telemetry.update();

            }else{
                SpatuSpoolMotor.setPower(0);

                //telemetry.addData("Spatula Linear Slide:","  Still");
                //telemetry.update();

            }

            rightY = (gamepad1.left_stick_y * 0.01f);
            if (rightY < 0){

                servoMove += rightY;

                SpatuServo.setPosition(servoMove);


            }else if (rightY > 0){

                servoMove += rightY;

                SpatuServo.setPosition(servoMove);


            }

            /*if(gamepad1.dpad_up){
                isPressedUp = true;
            }
            else {
                if(isPressedUp){
                    hangingPower += 0.1;
                    telemetry.addData("power", hangingPower);
                    isPressedUp = false;
                }
            }

            if(gamepad1.dpad_down){
                isPressedDown = true;
            }
            else {
                if(isPressedDown){
                    hangingPower -= 0.1;
                    telemetry.addData("power", hangingPower);
                    isPressedDown = false;
                }
            }*/

            if (gamepad1.right_trigger > 0.0){

                hangingMotor.setPower(hangingPower);

            }else if (gamepad1.left_trigger > 0.0){


                hangingMotor.setPower(-hangingPower);
            }
            else{
                hangingMotor.setPower(0.0);
            }

            if (gamepad1.dpad_right == true){

                sMove += 0.2;

                dumpServo.setPosition(sMove);

            } else if (gamepad1.dpad_left == true){

                sMove -=0.2;

                dumpServo.setPosition(sMove);

            }

        }
    }
}

