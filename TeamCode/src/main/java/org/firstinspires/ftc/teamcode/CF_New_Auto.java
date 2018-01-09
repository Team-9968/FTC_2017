package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;
import java.util.concurrent.TimeUnit;

/**
 * Created by dawso on 1/8/2018.
 */

@Autonomous(name = "New", group = "Sensor")
//@Disabled
public class CF_New_Auto extends OpMode
{
   //Allows this file to access pieces of hardware created in other files.
   CF_Hardware robot = new CF_Hardware();
   ElapsedTime runTime = new ElapsedTime();
   CF_Autonomous_Motor_Library auto = new CF_Autonomous_Motor_Library();
   CF_Color_Sensor sensor = new CF_Color_Sensor();
   boolean distance;

   //A "checklist" of things this program must do IN ORDER for it to work
   private enum checks
   {
      JEWELHITTER, PASTBALANCE, GOTOBOX, RELEASEBLOCK, PARK
   }

   //Sets current stage of the "List"
   checks Check = checks.JEWELHITTER;

   //Ensures that we do not go over thirty seconds of runtime. This endtime variable is
   //a backup method in case the coach forgets to turn on the timer built into the robot app.
   int endTime = 29;

   private void checkTime()
   {
      // Kills the robot if time is over the endTime
      if(runTime.seconds() >= endTime)
      {
         requestOpModeStop();
      }
   }

   @Override
   public void init()
   {
      robot.init(hardwareMap);
   }

   @Override
   public void loop()
   {
      runTime.reset();
      checkTime();
      CF_TypeEnum classification = sensor.setType(robot);

      switch (Check)
      {
         //Decides which color the ball on the right is and uses that to determine which way to strafe
         case JEWELHITTER:

            auto.driveIMUTurnLeft(this,robot, 0.3, 60);
            auto.driveIMUTurnLeft(this, robot, -0.3, 60);
            Check = checks.PASTBALANCE;
            break;
//
//         //Drives the robot back onto the balance pad and over it to the floor
         case PASTBALANCE:
//            auto.driveIMU(this, robot, -0.3, 1300);
            checkTime();
//            robot.tailLight.setPower(0.0);
//            Check = checks.GOTOBOX;
            break;

         //Drives robot to cryptobox and alignes it
//         case GOTOBOX:
//              if (distance = true)
//              {
//                 auto.driveIMU(robot, -0.5, 1000);
//              }
//
//              else if (distance = false)
//              {
//                 auto.driveIMU(robot, -0.5, 700);
//              }
//
//              else
//              {
//                 auto.driveIMU(robot, -0.5, 850)
//              }

//            checkTime();
//            State = states.RELEASEBLOCK;
//            break;
//
         //Opens claw(s) so the block is dropped in the box
//         case RELEASEBLOCK:
//            robot.clamp.setPosition(0.41);
//            robot.lowerClamp.setPosition(0.6);
//            checkTime();
//            State = states.PARK;
//            break;
//
         //Backs robot up slightly so we aren't touching the block, but are still parking
//         case PARK:
//            auto.driveIMU(robot, 0.5, 75);
//            checkTime();
//            break;
      }
   }
}
