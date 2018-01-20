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
    double red = 0;
    double blue = 0;
    double[] ret = new double[3];
    Mat image = new Mat(FtcRobotControllerActivity.getmRgba().cols(), FtcRobotControllerActivity.getmRgba().rows(), CvType.CV_8UC4);



    public enum ballColor {
        RED, BLUE, UNKNOWN
    }
    ballColor color;


    public ballColor getColor() {
        //if(source != null) {
            source = FtcRobotControllerActivity.getmRgba();
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
        source = FtcRobotControllerActivity.getmRgba();

        Core.rotate(source, image, Core.ROTATE_90_CLOCKWISE);
        if(source != null) {
            // 0 = red;
            // 1 = green;
            // 2 = blue;
            if(source == null) {
                System.out.println("Cnull");
            }
            try {
                Thread.sleep(1);
            } catch (InterruptedException e){}

            ret[0] = source.get(x, y)[0];
            ret[1] = source.get(x, y)[1];
            ret[2] = source.get(x, y)[2];

        }
        return ret;
    }

    public ballColor getColor(int x, int y) {
        source = FtcRobotControllerActivity.getmRgba();
        red = 0;
        blue = 0;
        Core.rotate(source, image, Core.ROTATE_90_CLOCKWISE);
        if(image != null) {
            if (image.get(x, y)[0] > 208 && image.get(x, y)[0] < 238 && image.get(x, y)[2] < 70) {
                red += x;
            } else if (image.get(x, y)[2] > 170 && image.get(x, y)[2] < 192 && image.get(x, y)[0] < 58) {
                blue += x;
            }

        }
        if(red > blue) {
            return ballColor.RED;
        } else if(blue > red) {
            return ballColor.BLUE;
        } else {
            return ballColor.UNKNOWN;
        }
    }
}
