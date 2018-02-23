package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ThreadPool;
import com.vuforia.STORAGE_TYPE;

import org.firstinspires.ftc.teamcode.Enums.CF_State_Enum;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

/**
 * Created by Ryley on 10/22/17.
 */

public class CF_Autonomous_Motor_Library {
   CF_Master_Motor_Library motors = new CF_Master_Motor_Library();
   CF_IMU_Library imuLib = new CF_IMU_Library();
   double exitTime = 29;

   enum mode
   {
      DRIVE, STRAFE, ROTATE
   }
   enum wrap {
      NORMAL, BACKWARDS, FORWARDS
   }

   /*
   The rotate part of this method does not work.
   a method call for this would look like: EncoderIMUDrive(this, robot, CF_Autonomous_Motor_Library.mode.STRAFE, 0.5f, 2000);

   gotta pass in an OpMode
   Counts is always positive
   Negative motor power makes it strafe left or drive backwards;

    */

   boolean encoderDriveState(CF_Hardware robot, float power, int counts, double offset) {
       motors.setMechPowers(robot, -1, power, power, power, power, 0);
       if((motors.getEncoderCounts(robot, 1) - offset) < counts && (motors.getEncoderCounts(robot, 1) - offset) > (-1 * counts))
       {
           return FALSE;
       } else {
           return TRUE;
       }
   }
   boolean encoderRotateState(CF_Hardware robot, float power, int counts, double offset) {
       motors.setMechPowers(robot, -1, power, -power, power, -power, 0);
       if((motors.getEncoderCounts(robot, 1) - offset) < counts && (motors.getEncoderCounts(robot, 1) - offset) > (-1 * counts))
       {
           return FALSE;
       } else {
           return TRUE;
       }
   }
   boolean encoderStrafeState(CF_Hardware robot, float power, int counts, double offset) {
       motors.setMechPowers(robot, -1, power, -power, -power, power, 0);
       if(counts != 0) {
           if ((motors.getEncoderCounts(robot, 1) - offset) < counts && (motors.getEncoderCounts(robot, 1) - offset) > (-1 * counts)) {
               return FALSE;
           } else {
               return TRUE;
           }
       } else {
           return TRUE;
       }
   }
   double resetEncoders(CF_Hardware robot) {
       motors.setMode(robot, DcMotor.RunMode.STOP_AND_RESET_ENCODER);
       motors.setMode(robot, DcMotor.RunMode.RUN_WITHOUT_ENCODER);
       return motors.getEncoderCounts(robot, 1);

   }
   void EncoderIMUDrive(OpMode opmode, CF_Hardware robot, mode m, float power, int counts) {
      // Enum tells the method what operation it wants the robot to perform
      // DRIVE
      double RFPower = 0;
      double LFPower = 0;
      double RRPower = 0;
      double LRPower = 0;

      if(m == mode.DRIVE) {
         // This is a trial and error value
         double kP = 0.01;
         // Values for the P controller
         double error = 0;
         double gain = error * kP;
         // Reset encoders
         motors.setMode(robot, DcMotor.RunMode.STOP_AND_RESET_ENCODER);
         motors.setMode(robot, DcMotor.RunMode.RUN_WITHOUT_ENCODER);
         // The offset for the encoder counts
         double offset = motors.getEncoderCounts(robot, 1);
         // Gets the rotation in the Z axis(Up and down through the center of the bot)
         imuLib.updateNumbers(robot);
         double rot = imuLib.getRotation(2);
         // This logic should work whether the encoder counts are positive or negative, and either way they go
         while((motors.getEncoderCounts(robot, 1) - offset) < counts && (motors.getEncoderCounts(robot, 1) - offset) > (-1 * counts)) {
            imuLib.updateNumbers(robot);
            error = imuLib.getRotation(2) - rot;
            gain = error * kP;
            RFPower = -power;// + gain;
            LFPower = -power;// - gain;
            RRPower = -power;// + gain;
            LRPower = -power;// - gain;

            motors.setMechPowers(robot, 1, LFPower, RFPower, LRPower, RRPower, 0);
            System.out.println("Rotation " + imuLib.getRotation(2));
            if(opmode.getRuntime() > exitTime) {
               motors.setMechPowers(robot,1,0,0,0,0,0);
               opmode.requestOpModeStop();
               break;
            }
            Thread.yield();
            try{
            Thread.sleep(1);
            }
            catch(InterruptedException e) {}
         }

      }

      // STRAFE
      else if(m == mode.STRAFE) {
         double kP = 0.1;
         double error = 0;
         double gain = error * kP;
         motors.setMode(robot, DcMotor.RunMode.STOP_AND_RESET_ENCODER);
         motors.setMode(robot, DcMotor.RunMode.RUN_WITHOUT_ENCODER);
         double offset = motors.getEncoderCounts(robot, 1);
         imuLib.updateNumbers(robot);
         double rot = imuLib.getRotation(2);
         while((motors.getEncoderCounts(robot, 1) - offset) < counts && (motors.getEncoderCounts(robot, 1) - offset) > (-1 * counts)) {
            imuLib.updateNumbers(robot);
            error = imuLib.getRotation(2) - rot;
            gain = error * kP;
            RFPower = power + gain;
            LFPower = -power - gain;
            RRPower = -power + gain;
            LRPower = power - gain;
            motors.setMechPowers(robot, 1, LFPower, RFPower, LRPower, RRPower, 0);
            if(opmode.getRuntime() > exitTime) {
               motors.setMechPowers(robot,1,0,0,0,0,0);
               opmode.requestOpModeStop();
               break;
            }
            Thread.yield();
             try{
                 Thread.sleep(1);
             }
             catch(InterruptedException e) {}
         }
      }
      motors.setMechPowers(robot,1,0,0,0,0,0);

      }

    boolean ifDone(CF_Hardware robot, double offset, int counts)
    {
        if((motors.getEncoderCounts(robot, 1) - offset) < counts && (motors.getEncoderCounts(robot, 1) - offset) > (-1 * counts))
        {
            return FALSE;
        } else {
            return TRUE;
        }
    }

   void rotate(OpMode opmode, CF_Hardware robot, float power, int counts) {

      double RFPower = 0;
      double LFPower = 0;
      double RRPower = 0;
      double LRPower = 0;

      // Reset encoders
      motors.setMode(robot, DcMotor.RunMode.STOP_AND_RESET_ENCODER);
      motors.setMode(robot, DcMotor.RunMode.RUN_WITHOUT_ENCODER);
      // The offset for the encoder counts
      double offset = motors.getEncoderCounts(robot, 1);
      // Gets the rotation in the Z axis(Up and down through the center of the bot)
      imuLib.updateNumbers(robot);
      double rot = imuLib.getRotation(2);
      // This logic should work whether the encoder counts are positive or negative, and either way they go
      while((motors.getEncoderCounts(robot, 1) - offset) < counts && (motors.getEncoderCounts(robot, 1) - offset) > (-1 * counts)) {
         imuLib.updateNumbers(robot);
         RFPower = +power;
         LFPower = -power;
         RRPower = +power;
         LRPower = -power;
         motors.setMechPowers(robot, 1, LFPower, RFPower, LRPower, RRPower, 0);
         if(opmode.getRuntime() > exitTime) {
            motors.setMechPowers(robot,1,0,0,0,0,0);
            opmode.requestOpModeStop();
            break;
         }
          try{
              Thread.sleep(1);
          }
          catch(InterruptedException e) {}

      }

      motors.setMechPowers(robot, 1, 0,0,0,0,0);


   }

   void mastMotorMove(CF_Hardware robot, float power, int counts)
   {
      // Reset encoders
      robot.mastMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
      robot.mastMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

      while(Math.abs(robot.mastMotor.getCurrentPosition()) < Math.abs(counts))
      {
         robot.mastMotor.setPower(power);
      }

      robot.mastMotor.setPower(0);
   }

   void clawMotorMove(CF_Hardware robot, float power, int counts)
   {
      // Reset encoders
      robot.clawMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
      robot.clawMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

      while(Math.abs(robot.clawMotor.getCurrentPosition()) < Math.abs(counts))
      {
         robot.clawMotor.setPower(power);
          System.out.println("Position " + robot.clawMotor.getCurrentPosition());
      }

      robot.clawMotor.setPower(0);
   }
}