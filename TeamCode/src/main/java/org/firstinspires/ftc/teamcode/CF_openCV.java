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


/**
 * Created by Ryley on 1/11/18.
 */
@Autonomous(name="openCV", group = "test")
//@Disabled
public class CF_openCV extends OpMode {
    CF_Globals globals = new CF_Globals();
    Mat source = null;
    Mat image = null;
    Mat rotatedImage = null;
    int x = 1;
    double red = 0;
    double blue = 0;
//    double redM [] [];
//    double blueM [] [];

    public void init() {

    }

    public void loop() {

        while (x == 1) {
            source = globals.getmRgba();

//            double redM [] [] = new double[(int)image.size().width][(int)image.size().height];
//            double blueM [] [] = new double[(int)image.size().width] [(int)image.size().height];
//
//            for(int h = 0; h < image.size().height - 1; h++) {
//                for(int w = 0; w < image.size().width - 1; w++) {
//                    for(int hb = (int)image.size().height -1; h > 0; h--) {
//                        redM[w][hb] = image.get(h, w)[0];
//                        blueM[w][hb] = image.get(h, w)[2];
//                        resetStartTime();
//                    }
//                }
//            }

            //if (image != null) {

            image = new Mat(source.cols(), source.rows(), CvType.CV_8UC4);

            Core.rotate(source, image, Core.ROTATE_90_CLOCKWISE);
            System.out.println("Height " + image.size().height);
            System.out.println("Width " + image.size().width);
            System.out.println("Red" + image.get(320, 240)[0]);
            System.out.println("Green" + image.get(320, 240)[1]);
            System.out.println("Blue" + image.get(320, 240)[2]);
            System.out.println("Alpha" + image.get(320, 240)[3]);

                for (int i = 0; i < image.size().height - 1; i+= 2) {
                    for (int x = 0; x < image.size().width - 1; x+= 2) {
                        if(image.get(i,x)[0] > 192 && image.get(i,x)[2] < 70) {
                            red += x;
                        } else if(image.get(i,x)[2] > 159 && image.get(i,x)[0] < 58) {
                            blue += x;
                        }
                    }
                }
            //}
            System.out.println("Total Red " + red);
            System.out.println("Total Blue " + blue);
            if(red > blue) {
                System.out.println("Red Right");
            } else if (blue > red) {
                System.out.println("Blue Right");
            }
            x = 2;
            requestOpModeStop();
        }

    }
}

