package org.firstinspires.ftc.teamcode;

import org.firstinspires.ftc.robotcontroller.internal.CF_Globals;
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

    public enum ballColor {
        RED, BLUE, UNKNOWN
    }
    ballColor color;


    public ballColor getColor() {
        source = globals.getmRgba();
        image = new Mat(source.cols(), source.rows(), CvType.CV_8UC4);

        Core.rotate(source, image, Core.ROTATE_90_CLOCKWISE);

        for (int i = 0; i < image.size().height - 1; i+= 2) {
            for (int x = 0; x < image.size().width - 1; x+= 2) {
                if(image.get(i,x)[0] > 208 && image.get(i,x)[0] < 238 && image.get(i,x)[2] < 70) {
                    red += x;
                } else if(image.get(i,x)[2] > 170 && image.get(i,x)[2] < 192 && image.get(i,x)[0] < 58) {
                    blue += x;
                }
            }
        }
        if(red > blue) {
            color = ballColor.RED;
        } else if(blue > red) {
            color = ballColor.BLUE;
        } else {
            color = ballColor.UNKNOWN;
        }

        return color;

    }
}
