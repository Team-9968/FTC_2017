package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

/**
 * Created by Ryley on 1/29/18.
 */

@TeleOp(name="Test_OpMode_Rotate", group="test")
public class CF_Test_OpMode_Rotate extends OpMode {
    CF_Hardware robot = new CF_Hardware();
    CF_IMU_Library imuLib = new CF_IMU_Library();
    double sum = 0;
    double theta = 0;
    double lastAng = 0;
    double ang;
    @Override
    public void init() {
        robot.init(hardwareMap);
        imuLib.updateNumbers(robot);
        lastAng = imuLib.getRotation(2);
    }
    public void loop() {
        imuLib.updateNumbers(robot);
        ang = imuLib.getRotation(2);
        if (Math.signum(lastAng) != Math.signum(ang)) {
            if (Math.abs(ang) < 90) {
                theta = Math.signum(ang) * (Math.abs(lastAng) + Math.abs(ang));
            } else if (Math.abs(ang) > 90) {
                theta = Math.signum(lastAng) * (360 - (Math.abs(lastAng) + Math.abs(ang)));
            }
        } else {
            theta = ang - lastAng;
        }
        sum += theta;
        theta = 0;

        System.out.println("Last " + lastAng + " New " + ang + " Sum " + sum);
        lastAng = ang;
    }
}
