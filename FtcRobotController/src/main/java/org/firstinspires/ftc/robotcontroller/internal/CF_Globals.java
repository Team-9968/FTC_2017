package org.firstinspires.ftc.robotcontroller.internal;

import org.opencv.core.Mat;

/**
 * Created by Ryley on 1/12/18.
 */

public class CF_Globals {
    public static Mat mRgba;
    public void setmRgba(Mat s) {
        mRgba = s;
    }
    public Mat getmRgba() {
        return mRgba;
    }
    public void releasemRgba() {
        mRgba.release();
    }

}
