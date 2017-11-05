package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import java.util.concurrent.TimeUnit;

/**
 * Created by dawso on 10/1/2017.
 */


@Autonomous(name = "Target is Right", group = "Sensor")
//@Disabled                            // Comment this out to add to the opmode list
public class CF_Auto_Target_Right extends LinearOpMode
{
   CF_Hardware robot = new CF_Hardware();
   CF_Color_Sensor sensor = new CF_Color_Sensor();
   CF_Master_Motor_Library motors = new CF_Master_Motor_Library();
   CF_Autonomous_Motor_Library auto = new CF_Autonomous_Motor_Library();

   private enum states
   {
      BACKUP, JEWELPUSHER, ENDOPMODE
   }

   @Override
   public void runOpMode() throws InterruptedException
   {
      states State = states.BACKUP;

      float hsvValues[] = {0F, 0F, 0F};
      final float values[] = hsvValues;
      robot.init(hardwareMap);

      waitForStart();

      while (opModeIsActive())
      {
         switch (State)
         {
            case BACKUP:
               auto.driveIMU(this, robot, 0.25, 500);
               telemetry.addData("1", " and done");
               telemetry.update();
               TimeUnit.MILLISECONDS.sleep(500);
               State = states.JEWELPUSHER;
               break;
            case JEWELPUSHER:
               TimeUnit.MILLISECONDS.sleep(500);
               auto.driveIMUStrafe(this, robot, -0.5, 600);
               telemetry.addData("2" , "");
               telemetry.update();
               State = states.ENDOPMODE;
                break;
            case ENDOPMODE:
               telemetry.addData("Done", "");
               telemetry.update();
               break;
         }

      }
   }
}