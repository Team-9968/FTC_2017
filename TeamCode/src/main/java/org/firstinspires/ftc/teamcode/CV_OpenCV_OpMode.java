package org.firstinspires.ftc.teamcode;

import android.graphics.Camera;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.robotcontroller.internal.CF_Globals;

import java.util.concurrent.TimeUnit;

/**
 * Created by Ryley on 1/13/18.
 */

@Autonomous(name="OpenCV_OpMode", group="Test")
public class CV_OpenCV_OpMode extends OpMode {
    CF_OpenCV_Library cam = new CF_OpenCV_Library();
    CF_Globals global = new CF_Globals();

    @Override
    public void init() {
        msStuckDetectLoop = 7000;
        global.setmRgba(null);



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


    }
}
