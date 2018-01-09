package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import java.util.concurrent.TimeUnit;

/**
 * Created by dawson on 12/18/2017.
 */

//Autonomous mode to knock off the right color of jewel, then puch a block into the cryptobox and park in
   //the parking zone.
@Autonomous(name = "Auto OpMode Red", group = "Sensor")
//@Disabled
public class CF_OpMode_Auto_Red extends OpMode
{
   //Allows this file to access pieces of hardware created in other files.
   CF_Hardware robot = new CF_Hardware();
   ElapsedTime runTime = new ElapsedTime();
   CF_Autonomous_Motor_Library auto = new CF_Autonomous_Motor_Library();
   CF_Color_Sensor sensor = new CF_Color_Sensor();
   boolean distance;

   //A "checklist" of things this program must do IN ORDER for it to work
    private enum states
    {
       BACKUP, JEWELHITTER, PASTBALANCE, ROTATETOBOX, GOTOBOX, RELEASEBLOCK, PARK
    }

    //Sets current stage of the "List"
   states State = states.BACKUP;

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

      switch (State)
      {
         //Drives the robot off ot the balance pad to the jewel stand
         case BACKUP:
            auto.driveIMU(this, robot, 0.15, 250);
            robot.jewelHitter.setPosition(0.54);
            try {
               TimeUnit.MILLISECONDS.sleep(1000);
            } catch (InterruptedException I) {}
            checkTime();
            robot.tailLight.setPower(1.0);
            State = states.JEWELHITTER;
            break;

         //Decides which color the ball on the right is and uses that to determine which way to strafe
         case JEWELHITTER:
            telemetry.addData("Case Jewelpusher", "");
            sensor.setType(robot);

            if (classification == CF_TypeEnum.RIGHTISRED)
            {
               robot.jewelHitter.setPosition(0.54);
               telemetry.addData("Right is"," red");
               auto.driveIMUStrafe(this, robot, 1, 250); //90
               checkTime();
//               auto.driveIMU(this, robot, -0.3, 70);
//               robot.jewelHitter.setPosition(0.1);
//               auto.driveIMUStrafe(this, robot, -0.3, 250); //90
 //              distance = true;
            }

            else if (classification == CF_TypeEnum.RIGHTISBLUE)
            {
               robot.jewelHitter.setPosition(0.54);
               telemetry.addData("Right is"," blue");
               auto.driveIMUStrafe(this, robot, -1, 250);
               checkTime();
//               auto.driveIMU(this, robot, -0.3, 70);
//               robot.jewelHitter.setPosition(0.1);
//               auto.driveIMUStrafe(this, robot, 0.3, 250);
//               checkTime();
//               distance = false;
            }

            else
            {
               telemetry.addData("Ball is", " unknown");
               checkTime();
            }

            robot.jewelHitter.setPosition(0.1);
            robot.tailLight.setPower(0.0);
//            try{
//            TimeUnit.MILLISECONDS.sleep(1000);}
//            catch (InterruptedException I) {}
//            requestOpModeStop();
//            State = states.PASTBALANCE;
            break;

//         //Drives the robot back onto the balance pad and over it to the floor
//         case PASTBALANCE:
//            auto.driveIMU(this, robot, -0.3, 1300);
//            checkTime();
//            robot.tailLight.setPower(0.0);
//            State = states.ROTATETOBOX;
//            break;
//
//          //Rotates the robot 90 degrees ish so it faces the cryptobox
//         case ROTATETOBOX:
//            auto.driveIMUTurnLeft(this, robot, -0.5, 700);
//            checkTime();
//            State = states.GOTOBOX;
//            break;

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
