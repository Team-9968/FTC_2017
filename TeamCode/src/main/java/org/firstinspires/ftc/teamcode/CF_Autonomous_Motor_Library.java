package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Created by Ryley on 10/22/17.
 */

public class CF_Autonomous_Motor_Library {
    CF_Master_Motor_Library motors = new CF_Master_Motor_Library();
    CF_IMU_Library imuLib = new CF_IMU_Library();

    double kP = 1.0;



    void strafeIMU(CF_Hardware robot, double power, double encoderCounts) {
        motors.setMode(robot, DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motors.setMode(robot, DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        double count = Math.abs(encoderCounts);
        double error = 0;
        double RFPower = 0;
        double LFPower = 0;
        double RRPower = 0;
        double LRPower = 0;

        while(Math.abs(motors.getEncoderCounts(robot, 1)) < count && Math.abs(motors.getEncoderCounts(robot, 2)) < count && Math.abs(motors.getEncoderCounts(robot, 3)) < count && Math.abs(motors.getEncoderCounts(robot, 4)) < count)
        {
            imuLib.updateNumbers(robot);
            error = (0 + imuLib.getyAccel()) * kP;
            RFPower = error + power;
            LFPower = error - power;
            RRPower = error + power;
            LRPower = error - power;

            motors.setMechPowers(robot, 1, LFPower, RFPower, LRPower, RRPower, 0);
        }

        motors.setMechPowers(robot,1,0,0,0,0,0);

    }
}