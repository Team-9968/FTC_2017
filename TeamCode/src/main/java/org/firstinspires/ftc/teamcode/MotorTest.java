package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;

import java.util.concurrent.TimeUnit;

/**
 * Created by jd72958 on 10/23/2017.
 */

@TeleOp(name="IsaacsSandbox", group="Iterative Opmode")
//@Disabled
public class MotorTest extends OpMode
{
   RobotHardware robot = new RobotHardware();

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

      telemetry.addData("Power: ", robot.MotorThingy1.getPower());
      telemetry.addData("Power: ", robot.MotorThingy2.getPower());
      telemetry.update();
   }
}
