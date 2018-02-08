package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import android.graphics.Bitmap;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.teamcode.Enums.CF_TypeEnum;
import org.opencv.core.Mat;
import java.util.concurrent.TimeUnit;

/**
 * Created by dawson on 2/4/2018.
 */

//Autonomous mode for starting on the red team, pad nearest the cryptobox in b/w the balancing stones
@Autonomous(name = "Blue Aim Vuforia", group = "Sensor")
//@Disabled
public class CF_Blue_Vuforia extends OpMode
{
   //Allows this file to access pieces of hardware created in other files.
   CF_Hardware robot = new CF_Hardware();
   static ElapsedTime runTime = new ElapsedTime();
   CF_Autonomous_Motor_Library auto = new CF_Autonomous_Motor_Library();
   CF_Color_Sensor sensor = new CF_Color_Sensor();
   CF_OpenCV_Library cam = new CF_OpenCV_Library();
   CF_Vuforia_Library vuforia = new CF_Vuforia_Library();
   CF_Master_Motor_Library motors = new CF_Master_Motor_Library();
   //CF_OpenCV_Library.ballColor cam_color = null;

   boolean ArmCenter;

   CF_OpenCV_Library.ballColor col;
   RelicRecoveryVuMark pic;

   int counts = 0;
   int rot = 0;
   int forwards = 0;
   double offset;

   //A "checklist" of things this program must do IN ORDER for it to work
   private enum checks
   {
      GRABBLOCK, MOVEMAST, SENSEPICTURE, JEWELHITTER, PASTBALANCE, RELEASEBLOCK, PARK, END
   }

   private enum jewelHitterState
   {
      ARMDOWN, CHECKCOL, ARMUP, END
   }

   private enum picSenseState
   {
      INITVUFORIA, DRIVEENCODERS, SENSEPICTURE, END
   }

   private enum pastBalanceState
   {
      RESETENCODERS, DRIVE, RESETENCODERS2, ROTATE, RESETENCODERS3, DRIVE2, END
   }

   private enum releaseBlockState
   {
      RELEASEBLOCK, RESETENCODERS, DRIVE, END
   }

   private enum parkState
   {
      RESETENCODERS1, DRIVE1, RESETENCODERS2, DRIVE2, RESETENCODERS3, DRIVE3, END
   }

   //Sets current stage of the "List"
   checks Check = checks.GRABBLOCK;
   jewelHitterState jewelHitter = jewelHitterState.ARMDOWN;
   picSenseState picSense = picSenseState.INITVUFORIA;
   pastBalanceState pastBalance = pastBalanceState.RESETENCODERS;
   releaseBlockState releaseBlock = releaseBlockState.RELEASEBLOCK;
   parkState park = parkState.RESETENCODERS1;

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
            Check = checks.JEWELHITTER;
            Mat x = vuforia.getFrame();
            telemetry.addData("Found picture", "Found");
            telemetry.update();
            col = cam.getColor(this, x);

            Bitmap y = vuforia.getMap();
            cam.save(this, y);
            break;

         //Decides which color the ball on the right is and uses that to determine which way to strafe
         case JEWELHITTER:
//            switch (jewelHitter) {
//               case ARMDOWN:
//
//            }
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

            if (classification == CF_TypeEnum.LEFTISBLUE) //&& cam_color == CF_OpenCV_Library.ballColor.BLUE) ||
            //(classification == CF_TypeEnum.RIGHTISBLUE && cam_color == CF_OpenCV_Library.ballColor.UNKNOWN) ||
            //(classification == CF_TypeEnum.UNKNOWN && cam_color == CF_OpenCV_Library.ballColor.BLUE))

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

            else if ((classification == CF_TypeEnum.LEFTISRED)) //&& cam_color == CF_OpenCV_Library.ballColor.RED)// ||
            //(classification == CF_TypeEnum.RIGHTISRED && cam_color == CF_OpenCV_Library.ballColor.UNKNOWN) ||
            //(classification == CF_TypeEnum.UNKNOWN && cam_color == CF_OpenCV_Library.ballColor.RED))

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
            Check = checks.MOVEMAST;
            break;

         case MOVEMAST:
            auto.clawMotorMove(robot, -1.0f, 2000);
            Check = checks.SENSEPICTURE;
            break;

         case SENSEPICTURE:
            switch(picSense) {
               case INITVUFORIA:
                  vuforia.activate();
                  motors.setMode(robot, DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                  motors.setMode(robot, DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                  offset = motors.getEncoderCounts(robot, 1);

                  picSense = picSenseState.DRIVEENCODERS;
                  break;

               case DRIVEENCODERS:
                  motors.setMechPowers(robot, -1, -0.2f, -0.2f, -0.2f, -0.2f, 0);
                  if(auto.ifDone(robot, offset, 100)){
                     motors.setMechPowers(robot, 1,0,0,0,0,0);
                     picSense = picSenseState.SENSEPICTURE;
                  }
                  break;
               case SENSEPICTURE:
                  try{
                     TimeUnit.MILLISECONDS.sleep(3000);
                  } catch (InterruptedException e) {}
                  pic = vuforia.getMark();
                  try{
                     TimeUnit.MILLISECONDS.sleep(500);
                  } catch (InterruptedException e) {}
                  //auto.rotate(this, robot, -0.25f, 225);
                  telemetry.addData("pic", pic);
                  telemetry.update();
                  //1875 for far
                  //1500 for middle
                  //1250 for near
                  if (pic == RelicRecoveryVuMark.CENTER) {
                     rot = 575;
                     counts = 1500;
                     forwards = 240;
                     // counts = 1200;
                  } else if(pic == RelicRecoveryVuMark.RIGHT){
                     counts = 1075;
                     rot = 1150;
                     forwards = 250;
                     //counts = 1800;
                  } else {
                     rot = 575;
                     counts = 1150;
                     forwards = 260;
                     // counts = 850;
                  }
                  vuforia.deactivate();
                  picSense = picSenseState.END;
                  break;
               case END:
                  Check = checks.PASTBALANCE;
                  motors.setMechPowers(robot, 1,0,0,0,0,0);
                  break;
            }

            break;

         //Drives the robot off of the balance pad
         case PASTBALANCE:
            switch (pastBalance){
               case RESETENCODERS:
                  motors.setMode(robot, DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                  motors.setMode(robot, DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                  offset = motors.getEncoderCounts(robot, 1);
                  pastBalance = pastBalanceState.DRIVE;
                  break;
               case DRIVE:
                  motors.setMechPowers(robot, 1, 0.2f, 0.2f, 0.2f, 0.2f, 0);
                  if(auto.ifDone(robot, offset, counts)){
                     motors.setMechPowers(robot, 1,0,0,0,0,0);
                     pastBalance = pastBalanceState.RESETENCODERS2;
                  }
                  break;
               case RESETENCODERS2:
                  motors.setMode(robot, DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                  motors.setMode(robot, DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                  offset = motors.getEncoderCounts(robot, 1);
                  pastBalance = pastBalanceState.ROTATE;
                  break;
               case ROTATE:
                  motors.setMechPowers(robot, 1, -0.4f, 0.4f, -0.4f, 0.4f, 0);
                  if(auto.ifDone(robot, offset, rot)){
                     motors.setMechPowers(robot, 1,0,0,0,0,0);
                     pastBalance = pastBalanceState.RESETENCODERS3;
                  }
                  break;
               case RESETENCODERS3:
                  motors.setMode(robot, DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                  motors.setMode(robot, DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                  offset = motors.getEncoderCounts(robot, 1);
                  pastBalance = pastBalanceState.DRIVE2;
                  break;
               case DRIVE2:
                  motors.setMechPowers(robot, 1, -0.2f, -0.2f, -0.2f, -0.2f, 0);
                  if(auto.ifDone(robot, offset, forwards)){
                     motors.setMechPowers(robot, 1,0,0,0,0,0);
                     pastBalance = pastBalanceState.END;
                  }
                  break;
               case END:
                  motors.setMechPowers(robot, 1,0,0,0,0,0);
                  Check = checks.RELEASEBLOCK;
                  break;






            }
            break;

         case RELEASEBLOCK:

            switch(releaseBlock) {
               case RELEASEBLOCK:
                  auto.clawMotorMove(robot, 1.0f, 1500);


                  try
                  {
                     TimeUnit.MILLISECONDS.sleep(500);
                  } catch(InterruptedException e) {}

                  robot.clamp.setPosition(0.4);
                  robot.lowerClamp.setPosition(0.6);
                  checkTime();
                  releaseBlock = releaseBlockState.RESETENCODERS;
                  break;
               case RESETENCODERS:
                  motors.setMode(robot, DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                  motors.setMode(robot, DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                  offset = motors.getEncoderCounts(robot, 1);
                  releaseBlock = releaseBlockState.END;
                  break;
               case DRIVE:
                  motors.setMechPowers(robot, 1, -0.2f, -0.2f, -0.2f, -0.2f, 0);
                  if(auto.ifDone(robot, offset, 75)){
                     motors.setMechPowers(robot, 1,0,0,0,0,0);
                     releaseBlock = releaseBlockState.END;
                  }
                  break;
               case END:
                  motors.setMechPowers(robot, 1,0,0,0,0,0);
                  Check = checks.PARK;
                  break;
            }
            break;

         // Backs robot up slightly so we aren't touching the block, but are still parking
         case PARK:

            switch(park) {
               case RESETENCODERS1:
                  motors.setMode(robot, DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                  motors.setMode(robot, DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                  offset = motors.getEncoderCounts(robot, 1);
                  try
                  {
                     TimeUnit.MILLISECONDS.sleep(500);
                  } catch(InterruptedException e) {}
                  park = parkState.DRIVE1;
                  break;
               case DRIVE1:
                  motors.setMechPowers(robot, 1, 0.2f, 0.2f, 0.2f, 0.2f, 0);
                  if(auto.ifDone(robot, offset, 275)){
                     motors.setMechPowers(robot, 1,0,0,0,0,0);
                     park = parkState.RESETENCODERS2;
                  }
                  break;
               case RESETENCODERS2:
                  motors.setMode(robot, DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                  motors.setMode(robot, DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                  offset = motors.getEncoderCounts(robot, 1);
                  try
                  {
                     TimeUnit.MILLISECONDS.sleep(200);
                  } catch(InterruptedException e) {}
                  park = parkState.DRIVE2;
                  break;
               case DRIVE2:
                  motors.setMechPowers(robot, 1, -0.2f, -0.2f, -0.2f, -0.2f, 0);
                  if(auto.ifDone(robot, offset, 200)){
                     motors.setMechPowers(robot, 1,0,0,0,0,0);
                     park = parkState.RESETENCODERS3;
                  }
                  break;
               case RESETENCODERS3:
                  motors.setMode(robot, DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                  motors.setMode(robot, DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                  offset = motors.getEncoderCounts(robot, 1);
                  try
                  {
                     TimeUnit.MILLISECONDS.sleep(100);
                  } catch(InterruptedException e) {}
                  park = parkState.DRIVE3;
                  break;
               case DRIVE3:
                  motors.setMechPowers(robot, 1, 0.2f, 0.2f, 0.2f, 0.2f, 0);
                  if(auto.ifDone(robot, offset, 200)){
                     motors.setMechPowers(robot, 1,0,0,0,0,0);
                     park = parkState.END;
                  }
                  break;
               case END:
                  motors.setMechPowers(robot, 1,0,0,0,0,0);
                  Check = checks.END;
            }
            break;

         //End state. Does nothing.
         case END:
            requestOpModeStop();
            break;
      }

      checkTime();
   }
}
