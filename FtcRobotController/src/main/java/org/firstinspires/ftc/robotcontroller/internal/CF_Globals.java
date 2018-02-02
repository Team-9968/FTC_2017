package org.firstinspires.ftc.robotcontroller.internal;

import android.hardware.Camera;

import org.opencv.core.Mat;

import java.util.concurrent.locks.ReentrantLock;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

/**
 * Created by Ryley on 1/12/18.
 */

public class CF_Globals {

    public static Mat mRgba;

    public static double red;

    public static boolean imageFlag = FALSE;

    public static final Object lock = new Object();

    public static void setImageFlag(boolean f) {
        imageFlag = f;
    }

    public static boolean getImageFlag() {
        return imageFlag;
    }

    public static double getRed() {
        return red;
    }

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

    public static void setmRgba(Mat s) {
       // mRgba = null;
        if(s != null) {
            synchronized (lock) {
                mRgba = s;
            }
        }
    }
    public static Mat getmRgba() {
        return mRgba;
    }
    public static void releasemRgba() {
        mRgba.release();
    }

}
