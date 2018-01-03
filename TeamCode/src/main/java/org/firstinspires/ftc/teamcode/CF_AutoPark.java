package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import java.util.concurrent.TimeUnit;

/**
 * Created by dawso on 12/1/2017.
 */


@Autonomous(name = "", group = "Sensor")
//@Disabled
public class CF_AutoPark extends LinearOpMode
{
   CF_Hardware robot = new CF_Hardware();
   CF_Autonomous_Motor_Library auto = new CF_Autonomous_Motor_Library();

   @Override
   public void runOpMode() throws InterruptedException
   {
      robot.init(hardwareMap);

      waitForStart();

      while (opModeIsActive())
      {
         auto.driveIMU(robot, -0.2, 1800);
         TimeUnit.MILLISECONDS.sleep(500);
         break;
      }
   }
}
