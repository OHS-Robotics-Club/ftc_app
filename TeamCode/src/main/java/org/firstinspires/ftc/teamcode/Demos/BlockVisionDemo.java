package org.firstinspires.ftc.teamcode.Demos;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.corningrobotics.enderbots.endercv.CameraViewDisplay;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Rect;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.Locale;


@Autonomous(name = "BlockVision")
public class BlockVisionDemo extends OpMode {
    BlockVisionGRIP blockVision;

    @Override
    public void init() {
        blockVision = new BlockVisionGRIP();

        telemetry.addData("Caption","Init Started");

        blockVision.init(hardwareMap.appContext, CameraViewDisplay.getInstance(), 2);
        blockVision.enable();
        telemetry.addData("Caption","Init Finished");

    }

    @Override
    public void loop() {
        ArrayList<MatOfPoint> contours = blockVision.filterContoursOutput();
        for (int i = 0; i < contours.size(); i++) {
            Rect boundingRect = Imgproc.boundingRect(contours.get(i));
            telemetry.addData("contour" + Integer.toString(i),
                    String.format(Locale.getDefault(), "(%d, %d)", (boundingRect.x + boundingRect.width) / 2, (boundingRect.y + boundingRect.height) / 2));
        }
    }

    public void stop() {
        // stop the vision system
        blockVision.disable();
    }


}
