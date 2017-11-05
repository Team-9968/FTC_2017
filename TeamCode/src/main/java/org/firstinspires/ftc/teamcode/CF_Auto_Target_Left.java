package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import java.util.concurrent.TimeUnit;

/**
 * Created by dawso on 10/30/2017.
 */




@Autonomous(name = "Target is Left", group = "Sensor")
//@Disabled
public class CF_Auto_Target_Left extends LinearOpMode
{
   CF_Hardware robot = new CF_Hardware();
   CF_Color_Sensor sensor = new CF_Color_Sensor();
   CF_Master_Motor_Library motors = new CF_Master_Motor_Library();
   CF_Autonomous_Motor_Library auto = new CF_Autonomous_Motor_Library();

   private enum steps
   {
      BACKUP, JEWELPUSHER, ENDOPMODE
   }

   @Override
   public void runOpMode() throws InterruptedException
   {
      steps Step = steps.BACKUP;

      float hsvValues[] = {0F, 0F, 0F};
      final float values[] = hsvValues;
      robot.init(hardwareMap);

      waitForStart();

      while (opModeIsActive())
      {
         switch (Step)
         {
            case BACKUP:
               auto.driveIMU(this, robot, 0.25, 500);
               telemetry.addData("1", " and done");
               telemetry.update();
               TimeUnit.MILLISECONDS.sleep(500);
               Step = steps.JEWELPUSHER;
               break;
            case JEWELPUSHER:
               TimeUnit.MILLISECONDS.sleep(750);
               auto.driveIMUStrafe(this, robot, 0.5, 400);
               telemetry.addData("2", "");
               telemetry.update();
               Step = steps.ENDOPMODE;
               break;
            case ENDOPMODE:
               telemetry.addData("Done", "");
               telemetry.update();
               break;
         }

      }
   }
}
