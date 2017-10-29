package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Created by Ryley on 10/22/17.
 */

public class CF_Autonomous_Motor_Library {
    CF_Master_Motor_Library motors = new CF_Master_Motor_Library();
    CF_IMU_Library imuLib = new CF_IMU_Library();




    void strafeIMU(CF_Hardware robot, double power, double encoderCounts) {
        motors.setMode(robot, DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motors.setMode(robot, DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        double kP = 0.25;
        double count = Math.abs(encoderCounts);
        double error = 0;
        double RFPower = 0;
        double LFPower = 0;
        double RRPower = 0;
        double LRPower = 0;

        while(Math.abs(motors.getEncoderCounts(robot, 1)) < count /* && Math.abs(motors.getEncoderCounts(robot, 2)) < count && Math.abs(motors.getEncoderCounts(robot, 3)) < count && Math.abs(motors.getEncoderCounts(robot, 4)) < count*/)
        {
            imuLib.updateNumbers(robot);
            error = (0 + imuLib.getyAccel()) * kP;
            RFPower = power + error;
            LFPower = -power + error;
            RRPower = -power + error;
            LRPower = power + error;

            motors.setMechPowers(robot, 1, LFPower, RFPower, LRPower, RRPower, 0);
        }

        motors.setMechPowers(robot,1,0,0,0,0,0);

    }

    void strafeIMURotation(CF_Hardware robot, double power, double encoderCounts) {
        motors.setMode(robot, DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motors.setMode(robot, DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        imuLib.updateNumbers(robot);
        double kP = 0.10;
        double count = Math.abs(encoderCounts);
        double start = imuLib.getRotation(3);
        double error = 0;
        double RFPower = 0;
        double LFPower = 0;
        double RRPower = 0;
        double LRPower = 0;

        while(Math.abs(motors.getEncoderCounts(robot, 1)) < count /* && Math.abs(motors.getEncoderCounts(robot, 2)) < count && Math.abs(motors.getEncoderCounts(robot, 3)) < count && Math.abs(motors.getEncoderCounts(robot, 4)) < count*/)
        {
            imuLib.updateNumbers(robot);
            error = (start + imuLib.getRotation(3)) * kP;
            RFPower = power - error;
            LFPower = -power + error;
            RRPower = -power - error;
            LRPower = power + error;

            motors.setMechPowers(robot, 1, LFPower, RFPower, LRPower, RRPower, 0);
        }

        motors.setMechPowers(robot,1,0,0,0,0,0);

    }


}