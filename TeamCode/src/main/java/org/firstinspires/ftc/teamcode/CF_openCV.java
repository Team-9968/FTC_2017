package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.opencv.android.JavaCameraView;
import org.opencv.android.OpenCVLoader;
import org.firstinspires.ftc.robotcontroller.internal.FtcRobotControllerActivity;
import org.firstinspires.ftc.robotcontroller.internal.CF_Vision_Getter;

/**
 * Created by Ryley on 1/11/18.
 */
@Autonomous(name="openCV", group = "test")
//@Disabled
public class CF_openCV extends OpMode {
    CF_Vision_Getter getter = new CF_Vision_Getter();
    public void init() {
        if(!OpenCVLoader.initDebug()) {
            telemetry.addData("OpenCV not loaded","");
        } else {
            telemetry.addData("OpenCV loaded", "");
        }

    }
    public void loop() {

    }
}

