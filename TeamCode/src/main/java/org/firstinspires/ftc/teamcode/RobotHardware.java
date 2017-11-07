package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class RobotHardware
{
   DcMotor MotorThingy1;
   DcMotor MotorThingy2;
   HardwareMap hwMap;
   public final static double FOOT_HOME = 0.2;
   public final static double FOOT_MIN_RANGE = 0.1;
   public final static double FOOT_MAX_RANGE = 0.9;

   Servo Foot;

   public void init(HardwareMap ahwMap)
   {
      hwMap = ahwMap;

      // Initialize MotorThingy - give it a name for config file, set rotation reference,
      // set to not use encoders and make sure it's off when software starts to run
      MotorThingy1 = hwMap.get(DcMotor.class, "MotorThingyBop");
      MotorThingy1.setDirection(DcMotor.Direction.FORWARD);
      MotorThingy1.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
      MotorThingy1.setPower(0.0);

      MotorThingy2 = hwMap.get(DcMotor.class, "MotorThingyBop2");
      MotorThingy2.setDirection(DcMotor.Direction.FORWARD);
      MotorThingy2.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
      MotorThingy2.setPower(0.0);

      Foot = hwMap.get(Servo.class, "FootServo");
      Foot.setPosition(FOOT_HOME);

   }
}
