package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.opencv.android.JavaCameraView;
import org.opencv.android.OpenCVLoader;
import org.firstinspires.ftc.robotcontroller.internal.FtcRobotControllerActivity;
import org.firstinspires.ftc.robotcontroller.internal.CF_Vision_Getter;
import org.opencv.core.Mat;

/**
 * Created by Ryley on 1/11/18.
 */
@Autonomous(name="openCV", group = "test")
//@Disabled
public class CF_openCV extends OpMode {
    CF_Vision_Getter getter = new CF_Vision_Getter();
    Mat image = null;
    public void init() {
        if(!OpenCVLoader.initDebug()) {
            telemetry.addData("OpenCV not loaded","");
        } else {
            telemetry.addData("OpenCV loaded", "");
        }

    }
    public void loop() {
        image = getter.getmRgba();
        for(int i = 0; i < image.size().height; i++){
            for(int x = 0; x < image.size().width; x++) {
                System.out.println("B" + image.get(i,x)[0]);
                System.out.println("G" + image.get(i,x)[1]);
                System.out.println("R" + image.get(i,x)[2]);
                System.out.println("A" + image.get(i,x)[3]);


            }
        }
    }
}

