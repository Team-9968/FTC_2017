package org.firstinspires.ftc.teamcode;

import android.graphics.Bitmap;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.teamcode.Enums.CF_TypeEnum;
import org.opencv.core.Mat;

import java.util.concurrent.TimeUnit;

/**
 * Created by dawson on 2/7/2018.
 */

public class CF_Blue_2nd_Auto extends OpMode
{
   //Allows this file to access pieces of hardware created in other files.
   CF_Hardware robot = new CF_Hardware();
   static ElapsedTime runTime = new ElapsedTime();
   CF_Autonomous_Motor_Library auto = new CF_Autonomous_Motor_Library();
   CF_Color_Sensor sensor = new CF_Color_Sensor();
   CF_OpenCV_Library cam = new CF_OpenCV_Library();
   CF_Vuforia_Library vuforia = new CF_Vuforia_Library();
   //CF_OpenCV_Library.ballColor cam_color = null;

   boolean ArmCenter;

   CF_OpenCV_Library.ballColor col;
   RelicRecoveryVuMark pic;

   int counts = 0;
   int rot = 0;
   int forwards = 0;

   //A "checklist" of things this program must do IN ORDER for it to work
   private enum checks
   {
      GRABBLOCK, MOVEMAST, SENSEPICTURE, JEWELHITTER, PASTBALANCE, RELEASEBLOCK, PARK, END
   }

   //Sets current stage of the "List"
   CF_Blue_Vuforia.checks Check = CF_Blue_Vuforia.checks.GRABBLOCK;

   //Ensures that we do not go over thirty seconds of runtime. This endtime variable is
   //a backup method in case the coach forgets to turn on the timer built into the robot app.
   int endTime = 29;

   private void checkTime()
   {
      // Kills the robot if time is over the endTime
      if(getRuntime() >= endTime)
      {
         requestOpModeStop();
      }
   }

   @Override
   public void init()
   {
      msStuckDetectLoop = 15000;
      robot.init(hardwareMap);
      vuforia.init(this);
   }

   @Override
   public void loop()
   {
      switch (Check)
      {
         case GRABBLOCK:
            resetStartTime();
            runTime.reset();
            robot.clamp.setPosition(0.81);
            robot.lowerClamp.setPosition(0.3);
            checkTime();
            Check = CF_Blue_Vuforia.checks.JEWELHITTER;
            Mat x = vuforia.getFrame();
            telemetry.addData("Found picture", "Found");
            telemetry.update();
            col = cam.getColor(this, x);

            Bitmap y = vuforia.getMap();
            cam.save(this, y);
            break;

         //Decides which color the ball on the right is and uses that to determine which way to strafe
         case JEWELHITTER:
            telemetry.addData("Case Jewelpusher", "");

            robot.armDown(0.11);

            try
            {
               TimeUnit.MILLISECONDS.sleep(300);
            } catch(InterruptedException e) {}

            robot.tailLight.setPower(1);

            try
            {
               TimeUnit.MILLISECONDS.sleep(700);
            } catch(InterruptedException e) {}

            sensor.setType(robot);
            CF_TypeEnum classification = sensor.setType(robot);

            if (classification == CF_TypeEnum.LEFTISBLUE)
            {
               telemetry.addData("Right is"," blue");
               robot.jewelHitter.setPosition(0.0);

               try
               {
                  TimeUnit.MILLISECONDS.sleep(500);
               } catch(InterruptedException e) {}

               ArmCenter = true;
               checkTime();
            }

            else if ((classification == CF_TypeEnum.LEFTISRED))
            {
               telemetry.addData("Right is"," red");
               robot.jewelHitter.setPosition(0.7);

               try
               {
                  TimeUnit.MILLISECONDS.sleep(500);
               } catch(InterruptedException e) {}

               ArmCenter = true;
               checkTime();
            }

            else
            {
               telemetry.addData("Ball is", " unknown");

               if(col == CF_OpenCV_Library.ballColor.RIGHTISBLUE) {
                  telemetry.addData("Right is"," blue - Camera");
                  robot.jewelHitter.setPosition(0.7);

                  try
                  {
                     TimeUnit.MILLISECONDS.sleep(500);
                  } catch(InterruptedException e) {}

                  ArmCenter = true;
                  checkTime();
               }
               else if(col == CF_OpenCV_Library.ballColor.RIGHTISRED) {
                  telemetry.addData("Right is"," red - Camera");
                  robot.jewelHitter.setPosition(0.0);

                  try
                  {
                     TimeUnit.MILLISECONDS.sleep(500);
                  } catch(InterruptedException e) {}

                  ArmCenter = true;
                  checkTime();
               }

               checkTime();
            }

            telemetry.update();
            robot.tailLight.setPower(0.0);
            robot.armUp(0.45);
            robot.jewelHitter.setPosition(0.15);

            try
            {
               TimeUnit.MILLISECONDS.sleep(100);
            } catch(InterruptedException e) {}

            if (ArmCenter = true)
            {
               robot.jewelHitter.setPosition(0.333);
            }
            robot.armUp(1.0);
            checkTime();
            Check = CF_Blue_Vuforia.checks.MOVEMAST;
            break;
}
