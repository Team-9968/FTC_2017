package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.util.Range;

import java.util.concurrent.TimeUnit;

/**
 * Created by jd72958 on 10/23/2017.
 */

@TeleOp(name="IsaacsSandbox", group="Iterative Opmode")
//@Disabled
public class MotorTest extends OpMode
{
   RobotHardware robot = new RobotHardware();
   double footPosition = robot.FOOT_HOME;
   public final double FOOT_SPEED = 0.01;

   //@Override
   public void init()
   {
      robot.init(hardwareMap);
   }

   //@Override
   public void loop()
   {
      double yAxis1 = gamepad1.left_stick_y;
      double yAxis2 = gamepad1.right_stick_y;

      robot.MotorThingy1.setPower(yAxis1);//yAxis);
      robot.MotorThingy2.setPower(yAxis2);

      if (gamepad1.a)
      {
         footPosition += FOOT_SPEED;
      }
      else if (gamepad1.y)
      {
         footPosition -= FOOT_SPEED;
      }

      footPosition  = Range.clip(footPosition, robot.FOOT_MIN_RANGE, robot.FOOT_MAX_RANGE);

      robot.Foot.setPosition(footPosition);

//      robot.MotorThingy.setPower(0.6);
//
//      try
//      {
//         Thread.sleep(1000);
//      }
//      catch(Exception e)
//      {
//         /* This is a generic Exception handler which means it can handle
//          * all the exceptions. This will execute if the exception is not
//          * handled by previous catch blocks.
//          */
//         System.out.println("Exception occurred");
//      }
//
//      robot.MotorThingy.setPower(-0.3);

      telemetry.addData("ButtonA: ", gamepad1.a);
      telemetry.addData("ButtonY: ", gamepad1.y);
      telemetry.addData("position: ", footPosition);
      telemetry.update();
   }
}
