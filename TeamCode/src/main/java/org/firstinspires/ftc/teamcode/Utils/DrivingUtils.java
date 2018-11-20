package org.firstinspires.ftc.teamcode.Utils;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.HardwareBot;

public class DrivingUtils {

    static final double     COUNTS_PER_MOTOR_REV    = 1120 ;    // eg: TETRIX Motor Encoder
    static final double     DRIVE_GEAR_REDUCTION    = 1.0 ;     // This is < 1.0 if geared UP
    static final double     WHEEL_DIAMETER_INCHES   = 4.0 ;     // For figuring circumference
    static final double     COUNTS_PER_INCH         = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
            (WHEEL_DIAMETER_INCHES * 3.1415);

    static final double HANG_POWER = 0.9;
    static final double STOP_POWER = 0.0;
    static final double LIFT_POWER = 0.3;

    private HardwareBot bot;
    private LinearOpMode opMode;

    public DrivingUtils(HardwareBot bot, LinearOpMode opMode){
        this.bot = bot;
        this.opMode = opMode;
    }

    /**
     * Raises and lowers hanging mechanism
     * using the limit switch as an input;
     * @param raise
     */
    public void hangLimit(boolean raise){

        bot.mHangingMotor.setPower(HANG_POWER);
        boolean reached = false;
        while (opMode.opModeIsActive() && !reached){
            if(bot.limitSwitch.getState()){
                reached = true;
            }
        }
        bot.mHangingMotor.setPower(STOP_POWER);

    }

    /**
     * Drives either a test robot or a comp robot
     * with a uniform power
     * @param power
     */
    public void driveUniform(double power){

        if(bot.testBot){
            bot.mRightFrontMotor.setPower(power);
            bot.mLeftFrontMotor.setPower(power);
        }
        else {
            bot.mRightFrontMotor.setPower(power);
            bot.mLeftFrontMotor.setPower(power);
            bot.mLeftBackMotor.setPower(power);
            bot.mRightBackMotor.setPower(power);
        }

    }

    /**
     * Drive for and Encoder Value Distance
     * @param power
     * @param distance
     */
    public void driveUniform(double power, double distance){
        int counts = (int)(distance * COUNTS_PER_INCH);

        setMotorEncoderPosition(counts);
        setMotorModeRun();
        driveUniform(power);

        boolean reached = false;
        while (opMode.opModeIsActive() && !reached){
            if(!bot.mRightBackMotor.isBusy()){
                reached = true;
            }

        }
        stop();
        setMotorModeDefault();
    }

    /**
     * Stop function
     * Sets all robot powers to Zero
     */
    public void stop(){

        if(bot.testBot){
            bot.mRightFrontMotor.setPower(0.0);
            bot.mLeftFrontMotor.setPower(0.0);
        }
        else {
            bot.mRightFrontMotor.setPower(0.0);
            bot.mLeftFrontMotor.setPower(0.0);
            bot.mLeftBackMotor.setPower(0.0);
            bot.mRightBackMotor.setPower(0.0);
        }

    }

    /**
     * Pivot Right using tank motor turn
     * @param power
     */
    public void tankRight(double power){

        if(bot.testBot){
            bot.mRightFrontMotor.setPower(-power);
            bot.mLeftFrontMotor.setPower(power);
        }
        else {
            bot.mRightFrontMotor.setPower(-power);
            bot.mRightBackMotor.setPower(-power);

            bot.mLeftFrontMotor.setPower(power);
            bot.mLeftBackMotor.setPower(power);
        }

    }

    /**
     * Pivot Left using tank motor turn
     * @param power
     */
    public void tankLeft(double power){

        if(bot.testBot){
            bot.mRightFrontMotor.setPower(power);
            bot.mLeftFrontMotor.setPower(-power);
        }
        else {
            bot.mRightFrontMotor.setPower(power);
            bot.mRightBackMotor.setPower(power);

            bot.mLeftFrontMotor.setPower(-power);
            bot.mLeftBackMotor.setPower(-power);
        }

    }



    /**
     * Set all motors to Run to Position
     */
    private void setMotorModeRun(){
        bot.mLeftBackMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        bot.mRightFrontMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        bot.mLeftFrontMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        bot.mRightBackMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    private void setMotorModeDefault(){
        bot.mRightBackMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        bot.mRightFrontMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        bot.mLeftFrontMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        bot.mLeftBackMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    /**
     * Reset all motor encoders
     */
    private void setMotorModeReset(){
        bot.mLeftBackMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        bot.mRightFrontMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        bot.mLeftFrontMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        bot.mRightBackMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    private void setMotorEncoderPosition(int counts){
        bot.mLeftBackMotor.setTargetPosition((counts + bot.mLeftBackMotor.getCurrentPosition()));
        bot.mRightFrontMotor.setTargetPosition((counts + bot.mRightFrontMotor.getCurrentPosition()));
        bot.mLeftFrontMotor.setTargetPosition((counts + bot.mLeftFrontMotor.getCurrentPosition()));
        bot.mRightBackMotor.setTargetPosition((counts + bot.mRightBackMotor.getCurrentPosition()));
    }
}
