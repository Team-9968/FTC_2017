package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.SyncdDevice;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.robotcontroller.internal.CF_Globals;
import org.firstinspires.ftc.robotcore.internal.system.SystemProperties;
import org.opencv.android.JavaCameraView;
import org.opencv.android.OpenCVLoader;
import org.firstinspires.ftc.robotcontroller.internal.FtcRobotControllerActivity;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;

import java.util.concurrent.TimeUnit;

import static java.util.logging.Logger.global;


/**
 * Created by Ryley on 1/11/18.
 */
@Autonomous(name="openCV", group = "test")
//@Disabled
public class CF_openCV extends OpMode {
    CF_OpenCV_Library cam = new CF_OpenCV_Library();
    CF_Globals global = new CF_Globals();
    public void init() {
    }

    public void loop() {
//        cam.getColor();
//        telemetry.addData("Red Total", global.getRed());
//        telemetry.addData("Blue Total", global.getBlue());
//        telemetry.addData("Difference, Red - Blue", global.getRed() - global.getBlue());
        telemetry.clearAll();
        telemetry.addData("Red", cam.getRGB(240, 320)[0]);
        telemetry.addData("Blue", cam.getRGB(240, 320)[2]);
        telemetry.update();
//        try{
//            TimeUnit.MILLISECONDS.sleep(5);
//        } catch (InterruptedException e) {}

    }
}

