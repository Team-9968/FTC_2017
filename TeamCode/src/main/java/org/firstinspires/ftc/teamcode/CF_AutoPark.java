package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import java.util.concurrent.TimeUnit;

/**
 * Created by dawson on 12/1/2017.
 */



//Autonomous program that drives from the balance pad to the parking zone.
@Autonomous(name = "", group = "Sensor")
//@Disabled
public class CF_AutoPark extends LinearOpMode
{
   CF_Hardware robot = new CF_Hardware();
   CF_Autonomous_Motor_Library auto = new CF_Autonomous_Motor_Library();

   @Override
   public void runOpMode() throws InterruptedException
   {
      //Sets each piece of the robot to the desired position prior to the
      //start of the match.
      robot.init(hardwareMap);

      //robot waits until coach presses play button on phone
      waitForStart();

      while (opModeIsActive())
      {
         auto.driveIMU(robot, -0.2, 1800);
         TimeUnit.MILLISECONDS.sleep(500);
         break;
      }
   }
}
