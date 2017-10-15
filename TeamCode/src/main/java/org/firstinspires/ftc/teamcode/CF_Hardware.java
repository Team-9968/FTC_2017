package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by Ryley on 9/15/17.
 */


public class CF_Hardware {
   public DcMotor rightFront = null;
   public DcMotor rightRear = null;
   public DcMotor leftFront = null;
   public DcMotor leftRear = null;
   public DcMotor leftLiftMotor = null;

   public Servo leftClaw = null;
   public Servo jewelPusher = null;


   HardwareMap hwMap = null;

   CF_Color_Sensor sensor = new CF_Color_Sensor();


   public void init(HardwareMap ahwMap)
   {
      hwMap = ahwMap;

      rightFront = hwMap.get(DcMotor.class, "motorOne");
      rightFront.setDirection(DcMotorSimple.Direction.FORWARD);

      rightRear = hwMap.get(DcMotor.class, "motorTwo");
      rightRear.setDirection(DcMotorSimple.Direction.FORWARD);

      leftRear = hwMap.get(DcMotor.class, "motorThree");
      leftRear.setDirection(DcMotorSimple.Direction.REVERSE);

      leftFront = hwMap.get(DcMotor.class, "motorFour");
      leftFront.setDirection(DcMotorSimple.Direction.REVERSE);

      leftLiftMotor = hwMap.get(DcMotor.class, "leftLiftMotor");
      leftLiftMotor.setDirection(DcMotorSimple.Direction.FORWARD);

      leftClaw = hwMap.get(Servo.class, "leftClaw");

      sensor.adafruitRGB = hwMap.get(ColorSensor.class, "adafruitRGB");

      jewelPusher = hwMap.get(Servo.class, "jewelPusher");

   }

   /***
    * Convenience method to assign motor power for mecanum drive.  Each mecanum
    * wheel is independently driven.
    *
    * @param lFPower Left front mecanum wheel
    * @param rFPower Right front mecanum wheel
    * @param lRPower Left rear mecanum wheel
    * @param rRPower Right Rear mecanum wheel
    */
   public void setMecanumPowers(double lFPower, double rFPower, double lRPower, double rRPower)
   {
      leftFront.setPower(lFPower);
      rightFront.setPower(rFPower);
      leftRear.setPower(lRPower);
      rightRear.setPower(rRPower);
   }


   /***
    * Convenience method for setting encoder runmode for all four mecanum drive motors
    *
    * @param mode Encoder run mode
    */
   public void setMecanumEncoderMode(DcMotor.RunMode mode)
   {
      leftFront.setMode(mode);
      rightFront.setMode(mode);
      leftRear.setMode(mode);
      rightRear.setMode(mode);
   }


   /***
    * Convenience method for setting encoder counts to all four mecanum drive motors
    *
    * @param lFcount Left front encoder counts
    * @param rFcount Right front encoder counts
    * @param lRcount Left rear encoder counts
    * @param rRcount Right rear encoder counts
    */
   public void setMecanumEncoderTargetPosition(int lFcount, int rFcount, int lRcount, int rRcount)
   {
      // Only want to use absolute values.  Take abs of inputs in case user sent negative value.
      leftFront.setTargetPosition(Math.abs(lFcount));
      rightFront.setTargetPosition(Math.abs(rFcount));
      leftRear.setTargetPosition(Math.abs(lRcount));
      rightRear.setTargetPosition(Math.abs(rRcount));
   }

   public void SetPosition()
   {

   }
}


