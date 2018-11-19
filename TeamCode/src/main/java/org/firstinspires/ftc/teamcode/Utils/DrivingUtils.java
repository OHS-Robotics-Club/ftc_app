package org.firstinspires.ftc.teamcode.Utils;

import org.firstinspires.ftc.teamcode.HardwareBot;

public class DrivingUtils {
    private HardwareBot bot;

    public DrivingUtils(HardwareBot bot){
        this.bot = bot;
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
}
