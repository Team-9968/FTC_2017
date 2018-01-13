package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import java.util.concurrent.TimeUnit;

/**
 * Created by Ryley on 1/13/18.
 */

@Autonomous(name="OpenCV_OpMode", group="Test")
public class CV_OpenCV_OpMode extends OpMode {
    CF_OpenCV_Library cam = new CF_OpenCV_Library();

    @Override
    public void init() {
        msStuckDetectLoop = 7000;

    }

    public void loop() {
        CF_OpenCV_Library.ballColor col = cam.getColor();
        if(col == CF_OpenCV_Library.ballColor.RED) {
            telemetry.addData("Right Red","");
            System.out.println("Right Red");
        } else if(col == CF_OpenCV_Library.ballColor.BLUE) {
            telemetry.addData("Right Blue", "");
            System.out.println("Right Blue");
        } else {
            telemetry.addData("Unknown", "");
            System.out.println("Unknown");
        }
        telemetry.update();
        try {
            TimeUnit.MILLISECONDS.sleep(4500);
        } catch (InterruptedException e) {}

        requestOpModeStop();
    }
}
