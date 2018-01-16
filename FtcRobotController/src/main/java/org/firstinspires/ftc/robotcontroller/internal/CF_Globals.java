package org.firstinspires.ftc.robotcontroller.internal;

import android.hardware.Camera;

import org.opencv.core.Mat;

/**
 * Created by Ryley on 1/12/18.
 */

public class CF_Globals {
    public static Mat mRgba;

    public static double getRed() {
        return red;
    }

    public static double red;

    public static double getBlue() {
        return blue;
    }

    public static void setBlue(double blue) {
        CF_Globals.blue = blue;
    }

    public static void setRed(double red) {
        CF_Globals.red = red;
    }

    public static double blue;

//    public static Camera cam;
//    public static Camera.Parameters params;
//    public void setParams(Camera.Parameters p) {
//        params = p;
//    }
//    public Camera.Parameters getParams() {
//        return params;
//    }
//    public Camera getCam() {
//        return cam;
//    }
//    public void setCam(Camera command) {
//        cam = command;
//    }
    public void setmRgba(Mat s) {
        mRgba = null;
        mRgba = s;
    }
    public Mat getmRgba() {
        return mRgba;
    }
    public void releasemRgba() {
        mRgba.release();
    }

}
