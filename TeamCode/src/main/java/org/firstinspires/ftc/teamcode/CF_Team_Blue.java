
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.Hardware;

import java.util.concurrent.TimeUnit;

/**
 * Created by dawso on 10/1/2017.
 */


@Autonomous(name = "Team Blue", group = "Sensor")
//@Disabled                            // Comment this out to add to the opmode list
public class CF_Team_Blue extends LinearOpMode
{
   CF_Hardware robot = new CF_Hardware();
   CF_Color_Sensor sensor = new CF_Color_Sensor();
   CF_Autonomous_Motor_Library auto = new CF_Autonomous_Motor_Library();

   private enum steps
   {
      BACKUP, JEWELPUSHER, ENDOPMODE
   }

   @Override
   public void runOpMode() throws InterruptedException
   {
      steps Step = steps.BACKUP;
      float hsvValues[] = {0F,0F,0F};

      robot.init(hardwareMap);

      waitForStart();

      while (opModeIsActive())
      {
         //sensor.turnOffAdafruiLED(robot);

         CF_Color_En sensorColor = sensor.getColorValues(robot);

         switch (Step)
         {
            case BACKUP:
               auto.driveIMU(this, robot, 0.25, 500);
               TimeUnit.MILLISECONDS.sleep(500);
               Step = steps.JEWELPUSHER;
               break;
            case JEWELPUSHER:
               TimeUnit.MILLISECONDS.sleep(500);

               //Servo down

               if (sensorColor == CF_Color_En.BLUE)
               {
                  telemetry.addData("Ball is"," blue");
                  TimeUnit.MILLISECONDS.sleep(750);
                  auto.driveIMUStrafe(this, robot, 0.5, 400);  //COLOR SENSOR IS RIGHT when robot is viewed from the back.
               }

               else if (sensorColor == CF_Color_En.RED)
               {
                  telemetry.addData("Ball is"," red");
                  TimeUnit.MILLISECONDS.sleep(500);
                  auto.driveIMUStrafe(this, robot, -0.5, 600);
               }

               else
               {
                  telemetry.addData("Ball is", " unknown");
               }

               Step = steps.ENDOPMODE;
               break;
            case ENDOPMODE:
               telemetry.addData("Done", "");
               break;
         }

         //telemetry.addData("Color: ", sensorColor);
         telemetry.addData("Hue: ", sensor.getColorHue());
         telemetry.update();
      }
   }
}