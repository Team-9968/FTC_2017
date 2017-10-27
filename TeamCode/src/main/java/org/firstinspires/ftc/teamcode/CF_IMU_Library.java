package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;

/**
 * Created by Ryley on 10/22/17.
 */

public class CF_IMU_Library {
    double xAccel;
    double yAccel;
    double zAccel;

    void updateNumbers(CF_Hardware robot) {
        xAccel = robot.imu.getAcceleration().xAccel;
        yAccel = robot.imu.getAcceleration().yAccel;
        zAccel = robot.imu.getAcceleration().zAccel;
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

}
