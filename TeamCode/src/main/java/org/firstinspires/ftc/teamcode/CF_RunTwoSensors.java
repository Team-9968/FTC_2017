package org.firstinspires.ftc.teamcode;

import android.graphics.Color;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

/**
 * Created by dawso on 11/20/2017.
 */

@TeleOp(name = "Both Sensors", group = "Sensor")
//@Disabled
public class CF_RunTwoSensors extends LinearOpMode
{
   CF_Hardware robot = new CF_Hardware();
   private CF_Color_Enum color = CF_Color_Enum.INIT;
   private CF_SecondSensorEnum wiffleball = CF_SecondSensorEnum.INIT;

   @Override
   public void runOpMode()
   {
      robot.init(hardwareMap);

      // hsvValues is an array that will hold the hue, saturation, and value information.
      float hsvValues[] = {0F,0F,0F};

      // wait for the start button to be pressed.
      waitForStart();

      // Note we use opModeIsActive() as our loop condition because it is an interruptible method.
      robot.tailLight.setPower(1.0);
      while (opModeIsActive())
      {

         robot.armDown(0.13);
         // convert the RGB values to HSV values.
         Color.RGBToHSV((robot.adafruitRGB.red() * 255) / 800, (robot.adafruitRGB.green() * 255) / 800, (robot.adafruitRGB.blue() * 255) / 800, hsvValues);
         Color.RGBToHSV((robot.adafruitRGBTwo.red() * 255) / 800, (robot.adafruitRGBTwo.green() * 255) / 800, (robot.adafruitRGBTwo.blue() * 255) / 800, hsvValues);

         //this sensor is on the right when robot is viewed from the back
         // Check which color is seen by sensor based on threshold values
         if (robot.adafruitRGB.red() - robot.adafruitRGB.blue() > 15)
         {
            color  = CF_Color_Enum.RED;
            telemetry.addData("Right Sensor = red" , robot.adafruitRGB.red() - robot.adafruitRGB.blue());
         }
         else if (robot.adafruitRGB.blue() - robot.adafruitRGB.red() > 15)
         {
            color = CF_Color_Enum.BLUE;
            telemetry.addData("Right Sensor = blue" , robot.adafruitRGB.blue() - robot.adafruitRGB.red());
         }
         else
         {
            color = CF_Color_Enum.UNKNOWN;
            telemetry.addData("Right Sensor = unknown" , Math.abs(robot.adafruitRGB.red() - robot.adafruitRGB.blue()));
         }

         //When robot is viewed form back, this sensor is on the left.
         // Check which color is seen by the second sensor based on threshold values
         if (robot.adafruitRGBTwo.red() - robot.adafruitRGBTwo.blue() > 15)
         {
            wiffleball  = CF_SecondSensorEnum.RED;
            telemetry.addData("Left Sensor = red" , robot.adafruitRGBTwo.red() - robot.adafruitRGBTwo.blue());
         }
         else if (robot.adafruitRGBTwo.blue() - robot.adafruitRGBTwo.red() > 15)
         {
            wiffleball = CF_SecondSensorEnum.BLUE;
            telemetry.addData("Left Sensor = blue" , robot.adafruitRGBTwo.blue() - robot.adafruitRGBTwo.red());
         }
         else
         {
            wiffleball = CF_SecondSensorEnum.UNKNOWN;
            telemetry.addData("Left Sensor = unknown" , Math.abs(robot.adafruitRGBTwo.red() - robot.adafruitRGBTwo.blue()));
         }

         telemetry.update();
      }
   }
}
