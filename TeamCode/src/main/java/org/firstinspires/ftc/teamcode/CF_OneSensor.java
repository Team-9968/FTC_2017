package org.firstinspires.ftc.teamcode;

import android.graphics.Color;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

/**
 * Created by dawso on 11/20/2017.
 */

@TeleOp(name = "One Sensor", group = "Sensor")
@Disabled
public class CF_OneSensor extends LinearOpMode
{
   CF_Hardware robot = new CF_Hardware();

   @Override
   public void runOpMode()
   {
      robot.init(hardwareMap);

      // hsvValues is an array that will hold the hue, saturation, and value information.
      float hsvValues[] = {0F,0F,0F};

      // wait for the start button to be pressed.
      waitForStart();

      // Note we use opModeIsActive() as our loop condition because it is an interruptible method.
      while (opModeIsActive())
      {
         // convert the RGB values to HSV values.
         Color.RGBToHSV((robot.adafruitRGB.red() * 255) / 800, (robot.adafruitRGB.green() * 255) / 800, (robot.adafruitRGB.blue() * 255) / 800, hsvValues);

         telemetry.addData("Red  ", robot.adafruitRGB.red());
         telemetry.addData("Blue ", robot.adafruitRGB.blue());

         telemetry.update();
      }
   }
}
