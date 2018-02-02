package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.Enums.CF_TypeEnum;

import java.util.concurrent.TimeUnit;

/**
 * Created by dawso on 1/28/2018.
 */


//Autonomous mode for starting on the red team, pad nearest the cryptobox in b/w the balancing stones
@Autonomous(name = "Red Aim CP", group = "Sensor")
@Disabled
public class CF_Red_Aim_FC extends OpMode
{
   //Allows this file to access pieces of hardware created in other files.
   CF_Hardware robot = new CF_Hardware();
   ElapsedTime runTime = new ElapsedTime();
   CF_Autonomous_Motor_Library auto = new CF_Autonomous_Motor_Library();
   CF_Color_Sensor sensor = new CF_Color_Sensor();
   //CF_OpenCV_Library cam = new CF_OpenCV_Library();
   //CF_OpenCV_Library.ballColor cam_color = null;

   //A "checklist" of things this program must do IN ORDER for it to work
   private enum checks
   {
      GRABBLOCK, MOVEMAST, SENSECOLOR, PICTURE, JEWELHITTER, PASTBALANCE, GOTOBOX, RELEASEBLOCK, PARK
   }

   //Sets current stage of the "List"
   checks Check = checks.GRABBLOCK;

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
      msStuckDetectLoop = 10000000;
      robot.init(hardwareMap);
   }

   @Override
   public void loop()
   {
      switch (Check)
      {
         case GRABBLOCK:
            robot.clamp.setPosition(0.81);
            robot.lowerClamp.setPosition(0.3);

            try
            {
               TimeUnit.MILLISECONDS.sleep(1000);
            } catch(InterruptedException e) {}

            checkTime();
            Check = checks.SENSECOLOR;
            break;

         case SENSECOLOR:
            //cam_color = cam.getColor();
            checkTime();
            Check = checks.PICTURE;
            break;

         case PICTURE:
            //
            //
            //
            //
            //
            //
            checkTime();
            Check = checks.JEWELHITTER;
            break;


         //Decides which color the ball on the right is and uses that to determine which way to strafe
         case JEWELHITTER:
            telemetry.addData("Case Jewelpusher", "");

            robot.armDown(0.13);

            try
            {
               TimeUnit.MILLISECONDS.sleep(1000);
            } catch(InterruptedException e) {}

            robot.tailLight.setPower(1);

            try
            {
               TimeUnit.MILLISECONDS.sleep(1000);
            } catch(InterruptedException e) {}

            sensor.setType(robot);
            CF_TypeEnum classification = sensor.setType(robot);

            if (classification == CF_TypeEnum.RIGHTISBLUE) //&& cam_color == CF_OpenCV_Library.ballColor.BLUE) ||
            //(classification == CF_TypeEnum.RIGHTISBLUE && cam_color == CF_OpenCV_Library.ballColor.UNKNOWN) ||
            //(classification == CF_TypeEnum.UNKNOWN && cam_color == CF_OpenCV_Library.ballColor.BLUE))

            {
               telemetry.addData("Right is"," blue");
               robot.jewelHitter.setPosition(0.7);

               try
               {
                  TimeUnit.MILLISECONDS.sleep(500);
               } catch(InterruptedException e) {}

               checkTime();
            }

            else if ((classification == CF_TypeEnum.RIGHTISRED)) //&& cam_color == CF_OpenCV_Library.ballColor.RED)// ||
            //(classification == CF_TypeEnum.RIGHTISRED && cam_color == CF_OpenCV_Library.ballColor.UNKNOWN) ||
            //(classification == CF_TypeEnum.UNKNOWN && cam_color == CF_OpenCV_Library.ballColor.RED))

            {
               telemetry.addData("Right is"," red");
               robot.jewelHitter.setPosition(0.0);

               try
               {
                  TimeUnit.MILLISECONDS.sleep(500);
               } catch(InterruptedException e) {}

               checkTime();
            }

            else
            {
               telemetry.addData("Ball is", " unknown");
               checkTime();
            }

            telemetry.update();
            robot.tailLight.setPower(0.0);
            robot.armUp(0.25);
            robot.jewelHitter.setPosition(0.333);
            robot.armUp(1.0);
            checkTime();
            Check = checks.MOVEMAST;
            break;
//
////         case MOVEMAST:
////            auto.mastMotorMove(robot, -1.0f, 2000);
////            Check = checks.PASTBALANCE;
////            break;
//
////         //Drives the robot off of the balance pad
////         case PASTBALANCE:
////            auto.EncoderIMUDrive(robot, CF_Autonomous_Motor_Library.mode.DRIVE, 0.4f, 1300); //No idea how far that goes
////            checkTime();
////            Check = checks.GOTOBOX;
////            break;
//
//         //Drives robot to cryptobox and alignes it
////         case GOTOBOX:
//
//
////            checkTime();
////            State = states.RELEASEBLOCK;
////            break;
////
//         //Opens claw(s) so the block is dropped in the box
////         case RELEASEBLOCK:
////            robot.clamp.setPosition(0.4);
////            robot.lowerClamp.setPosition(0.6);
////            checkTime();
////            State = states.PARK;
////            break;
////
//         //Backs robot up slightly so we aren't touching the block, but are still parking
////         case PARK:
////            auto.driveIMU(robot, 0.5, 75);
////            checkTime();
////            break;
      }
   }
}