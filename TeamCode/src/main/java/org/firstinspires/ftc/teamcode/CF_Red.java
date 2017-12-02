package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import java.util.concurrent.TimeUnit;

/**
 * Created by dawso on 10/1/2017.
 */


@Autonomous(name = "Team Red", group = "Sensor")
//@Disabled                            // Comment this out to add to the opmode list
public class CF_Red extends LinearOpMode
{
   CF_Hardware robot = new CF_Hardware();
   CF_Color_Sensor sensor = new CF_Color_Sensor();
   CF_Autonomous_Motor_Library auto = new CF_Autonomous_Motor_Library();

   private enum states
   {
      BACKUP, JEWELPUSHER, ENDOPMODE
   }

   @Override
   public void runOpMode() throws InterruptedException
   {
      states State = states.BACKUP;
      float hsvValues[] = {0F,0F,0F};

      robot.init(hardwareMap);

      waitForStart();

      while (opModeIsActive())
      {
         //sensor.turnOffAdafruiLED(robot);

         CF_TypeEnum classification = sensor.setType(robot);

         switch (State)
         {
            case BACKUP:
               auto.driveIMU(this, robot, 0.15, 350);
               TimeUnit.MILLISECONDS.sleep(500);
               State = states.JEWELPUSHER;
               break;
            case JEWELPUSHER:
               TimeUnit.MILLISECONDS.sleep(1500);
               if (classification == CF_TypeEnum.RIGHTISRED)
               {
                  auto.driveIMU(this, robot, -0.15, 80);
                  telemetry.addData("Ball is"," red");
                  robot.jewelHitter.setPosition(0.2);
                  TimeUnit.MILLISECONDS.sleep(700);
                  auto.driveIMUStrafe(this, robot, 0.3, 400);
               }

               else if (classification == CF_TypeEnum.RIGHTISBLUE)
               {
                  auto.driveIMU(this, robot, -0.15, 80);
                  telemetry.addData("Ball is"," blue");
                  robot.jewelHitter.setPosition(0.2);
                  TimeUnit.MILLISECONDS.sleep(700);
                  auto.driveIMUStrafe(this, robot, -0.3, 250);
               }

               else
               {
                  telemetry.addData("Ball is", " unknown");
               }

               State = states.ENDOPMODE;
               break;
            case ENDOPMODE:
               telemetry.addData("Done", "");
               break;
         }

         telemetry.addData("Color: ", classification);
         telemetry.update();
      }
   }
}