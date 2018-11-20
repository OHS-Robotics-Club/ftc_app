package org.firstinspires.ftc.teamcode.Utils;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.teamcode.HardwareBot;

public class GyroUtils {

    private HardwareBot bot;
    private LinearOpMode opMode;
    private DrivingUtils drive;

    public GyroUtils(HardwareBot bot, LinearOpMode opMode){
        this.bot = bot;
        this.opMode = opMode;
        this.drive = new DrivingUtils(bot, opMode);

    }

    /**
     * Turn a certain number of degrees
     * With a certain power
     * @param degrees
     * @param turnPower
     */
    public void turnDegrees(int degrees, double turnPower) {
        Orientation angles;

        angles = bot.imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
        double initialAngle = angles.firstAngle;
        double targetAngle = initialAngle + degrees;
        if (targetAngle < initialAngle) {
            while (angles.firstAngle > targetAngle && opMode.opModeIsActive()) {
                angles = bot.imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
                opMode.idle();
            }
        } else {
            drive.tankLeft(turnPower);
            while (angles.firstAngle < targetAngle && opMode.opModeIsActive()) {
                angles = bot.imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
                opMode.idle();
            }
        }
        drive.stop();
    }

    public double getHeading() {
        Orientation angles;
        angles = bot.imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
        return angles.firstAngle;

    }


}
