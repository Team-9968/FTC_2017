package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import java.security.cert.CertStoreParameters;

/**
 * Created by Ryley on 10/22/17.
 */

public class CF_Autonomous_Motor_Library {
    CF_Master_Motor_Library motors = new CF_Master_Motor_Library();
    CF_IMU_Library imuLib = new CF_IMU_Library();




    // Unused method, may be refined in the future to be used
    void strafeIMUCombined(CF_Hardware robot, double power, double encoderCounts) {
        motors.setMode(robot, DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motors.setMode(robot, DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        imuLib.updateNumbers(robot);
        double kPy = 0.05;
        double kPr = 0.008;
        double count = Math.abs(encoderCounts);
        double start = imuLib.getRotation(3);
        double errorRotate = 0;
        double errorY = 0;
        double RFPower = 0;
        double LFPower = 0;
        double RRPower = 0;
        double LRPower = 0;

        while(Math.abs(motors.getEncoderCounts(robot, 1)) < count /* && Math.abs(motors.getEncoderCounts(robot, 2)) < count && Math.abs(motors.getEncoderCounts(robot, 3)) < count && Math.abs(motors.getEncoderCounts(robot, 4)) < count*/)
        {
            imuLib.updateNumbers(robot);
            errorY = (0 + imuLib.getyAccel()) * kPy;
            errorRotate = (start + imuLib.getRotation(3)) * kPr;
            RFPower = power + errorY - errorRotate;
            LFPower = -power + errorY + errorRotate;
            RRPower = -power + errorY - errorRotate;
            LRPower = power + errorY + errorRotate;

            motors.setMechPowers(robot, 1, LFPower, RFPower, LRPower, RRPower, 0);
        }

        motors.setMechPowers(robot,1,0,0,0,0,0);

    }

    void strafeIMURotation(LinearOpMode mode, CF_Hardware robot, double power, double encoderCounts) {
        motors.setMode(robot, DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motors.setMode(robot, DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        imuLib.updateNumbers(robot);
        double kP = 0.05;
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
            error = (start + (imuLib.getRotation(3) * kP));
            RFPower = power - error;
            LFPower = -power + error;
            RRPower = -power - error;
            LRPower = power + error;

            motors.setMechPowers(robot, 1, LFPower, RFPower, LRPower, RRPower, 0);
        }

        motors.setMechPowers(robot,1,0,0,0,0,0);

    }

    void driveIMU(LinearOpMode mode, CF_Hardware robot, double power, double encoderCounts) {
        motors.setMode(robot, DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motors.setMode(robot, DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        imuLib.updateNumbers(robot);
        double kP = 0.05;
        double count = Math.abs(encoderCounts);
        double start = imuLib.getRotation(3);
        double error = 0;
        double RFPower = 0;
        double LFPower = 0;
        double RRPower = 0;
        double LRPower = 0;

        while(!mode.isStopRequested() && Math.abs(motors.getEncoderCounts(robot, 1)) < count /* && Math.abs(motors.getEncoderCounts(robot, 2)) < count && Math.abs(motors.getEncoderCounts(robot, 3)) < count && Math.abs(motors.getEncoderCounts(robot, 4)) < count*/)
        {

            imuLib.updateNumbers(robot);
            error = (start + (imuLib.getRotation(3) * kP));
            RFPower = power;// + error;
            LFPower = power;// - error;
            RRPower = power;// + error;
            LRPower = power;// - error;

            motors.setMechPowers(robot, 1, LFPower, RFPower, LRPower, RRPower, 0);
        }

        motors.setMechPowers(robot,1,0,0,0,0,0);


    }

    void rotateIMU(LinearOpMode mode, CF_Hardware robot, double power, double degrees) {
        motors.setMode(robot, DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motors.setMode(robot, DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        imuLib.updateNumbers(robot);
        double start = imuLib.getRotation(3);
        degrees += start;
        double kP = 0.01;
        double error = 10.5;
        double effort = 0;
        while(!mode.isStopRequested() && Math.abs(error) > 10) {
            imuLib.updateNumbers(robot);
            error = degrees - imuLib.getRotation(3);
            effort = error * kP;
            if(Math.abs(effort) > power) {
                if(effort < 0) {
                    effort = -power;
                }
                if(effort > 0) {
                    effort = power;
                }
            }
            mode.telemetry.addData("error", error);
            mode.telemetry.addData("degrees", degrees);
            mode.telemetry.addData("totalDegrees", imuLib.getRotation(3));
            mode.telemetry.update();
            motors.setMechPowers(robot, 1, +effort, -effort, +effort, -effort, 0);
        }
        motors.setMechPowers(robot,1,0,0,0,0,0);
    }


}