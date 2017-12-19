package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import java.util.concurrent.TimeUnit;

/**
 * Created by dawso on 12/18/2017.
 */



@Autonomous(name = "Auto OpMode", group = "Sensor")
//@Disabled
public class CF_OpMode_Auto_Red extends OpMode
{

      CF_Hardware robot = new CF_Hardware();
      ElapsedTime runTime = new ElapsedTime();
      CF_Autonomous_Motor_Library auto = new CF_Autonomous_Motor_Library();
      CF_Color_Sensor sensor = new CF_Color_Sensor();

      private enum states
      {
         BACKUP, JEWELHITTER, PASTBALANCE, ROTATETOBOX, GOTOBOX, RELEASEBLOCK, PARK
      }

      states State = states.BACKUP;

      int endTime = 10;

      private void checkTime()
      {
         // Kills the robot if time is over the endTime
         if(runTime.seconds() >= endTime) {
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
            case BACKUP:

               auto.driveIMU(robot, 0.15, 350);
               robot.jewelHitter.setPosition(0.65);
               checkTime();
               State = states.JEWELHITTER;
               break;

//         case JEWELHITTER:
//            telemetry.addData("Case Jewelpusher", "");
//            sensor.setType(robot);
//
//            if (classification == CF_TypeEnum.RIGHTISRED)
//            {
//               auto.driveIMU(robot, -0.15, 0);
//               telemetry.addData("Ball is"," red");
//               auto.driveIMUStrafe(robot, 0.3, 400);
//               checkTime();
//            }
//
//            else if (classification == CF_TypeEnum.RIGHTISBLUE)
//            {
//               auto.driveIMU(robot, -0.15, 0);
//               telemetry.addData("Ball is"," blue");
//               auto.driveIMUStrafe(robot, -0.3, 250);
//               checkTime();
//            }
//
//            else
//            {
//               telemetry.addData("Ball is", " unknown");
//               checkTime();
//            }
//
//            State = states.PASTBALANCE;
//            break;
//
//         case PASTBALANCE:
//            auto.driveIMU(robot, 0.3, 800);
//            State = states.ROTATETOBOX;
//            break;
//
//         case ROTATETOBOX:
//            auto.driveIMUTurnRight(robot, 0.5, 100); //the number of counts is a not-so-educated guess.
//            State = states.GOTOBOX;
//            break;

            case GOTOBOX:
               auto.driveIMU(robot, 0.4, 1000); //again, just a guess
               //maybe some strafing somewhere in there
               State = states.RELEASEBLOCK;
               break;

            case RELEASEBLOCK:
               robot.positionUpper = 0.41;
               positionLower = 0.6;
         }
      }

}
