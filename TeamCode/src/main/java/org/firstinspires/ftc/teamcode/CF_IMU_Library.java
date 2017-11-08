package org.firstinspires.ftc.teamcode;

import android.graphics.drawable.GradientDrawable;

import com.qualcomm.hardware.bosch.BNO055IMU;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;


/**
 * Created by Ryley on 10/22/17.
 */

public class CF_IMU_Library {
    double xAccel;
    double yAccel;
    double zAccel;
    Orientation rotation;

    void updateNumbers(CF_Hardware robot) {
        xAccel = robot.imu.getAcceleration().xAccel;
        yAccel = robot.imu.getAcceleration().yAccel;
        zAccel = robot.imu.getAcceleration().zAccel;
        rotation = robot.imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.XYZ, AngleUnit.DEGREES);

    }

    double getxAccel() {
        return xAccel;
    }

    double getyAccel() {
        return yAccel;
    }

    double getzAccel() {
        return zAccel;
    }

    // ThirdAngle is rotation
    double getRotation(int x) {
        switch (x) {
            case 1:
                return rotation.firstAngle;
            case 2:
                return rotation.secondAngle;
            case 3:
                return rotation.thirdAngle;
            default:
                return 0;
        }
    }

}
