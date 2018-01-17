package org.firstinspires.ftc.teamcode;

import org.firstinspires.ftc.robotcontroller.internal.CF_Globals;
import org.firstinspires.ftc.robotcontroller.internal.FtcRobotControllerActivity;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;

/**
 * Created by Ryley on 1/13/18.
 */

public class CF_OpenCV_Library {
    CF_Globals globals = new CF_Globals();
    Mat source = null;
    Mat image = null;
    double red = 0;
    double blue = 0;
    double[] ret = new double[3];


    public enum ballColor {
        RED, BLUE, UNKNOWN
    }
    ballColor color;


    public ballColor getColor() {
        //if(source != null) {
            source = CF_Globals.getmRgba().clone();
            image = new Mat(source.cols(), source.rows(), CvType.CV_8UC4);
            red = 0;
            blue = 0;

            Core.rotate(source, image, Core.ROTATE_90_CLOCKWISE);

            for (int i = 0; i < image.size().height - 1; i += 2) {
                for (int x = 0; x < image.size().width - 1; x += 2) {
                    if (image.get(i, x)[0] > 208 && image.get(i, x)[0] < 238 && image.get(i, x)[2] < 70) {
                        red += x;
                    } else if (image.get(i, x)[2] > 170 && image.get(i, x)[2] < 192 && image.get(i, x)[0] < 58) {
                        blue += x;
                    }
                }
            }
            if (red > blue + 2500) {
                color = ballColor.RED;
            } else if (blue > red + 2500) {
                color = ballColor.BLUE;
            } else {
                color = ballColor.UNKNOWN;
            }

            CF_Globals.setRed(red);
            CF_Globals.setBlue(blue);
//        } else {
//            color = ballColor.UNKNOWN;
//        }

        return color;

    }

    public double[] getRGB(int x, int y) {
        source = null;
        //source = CF_Globals.getmRgba().clone();
        source = FtcRobotControllerActivity.getmRgba().clone();

        image = new Mat(source.cols(), source.rows(), CvType.CV_8UC4);
        Core.rotate(source, image, Core.ROTATE_90_CLOCKWISE);
        if(image != null) {
            // 0 = red;
            // 1 = green;
            // 2 = blue;
            if(image == null) {
                System.out.println("Cnull");
            }
            try {
                Thread.sleep(1);
            } catch (InterruptedException e){}
            double z = image.get(x, y)[0];
            ret[0] = z;
            ret[1] = image.get(x, y)[1];
            ret[2] = image.get(x, y)[2];

        }
        return ret;
    }
}
