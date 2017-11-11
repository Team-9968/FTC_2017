package org.firstinspires.ftc.teamcode;

import android.graphics.Color;
import com.qualcomm.robotcore.hardware.HardwareMap;

/*
 *
 * This is an example LinearOpMode that shows how to use
 * the Adafruit RGB Sensor.  It assumes that the I2C
 * cable for the sensor is connected to an I2C port on the
 * Core Device Interface Module.
 *
 * It also assuems that the LED pin of the sensor is connected
 * to the digital signal pin of a digital port on the
 * Core Device Interface Module.
 *
 * You can use the digital port to turn the sensor's onboard
 * LED on or off.
 *
 * The op mode assumes that the Core Device Interface Module
 * is configured with a name of "dim" and that the Adafruit color sensor
 * is configured as an I2C device with a name of "sensor_color".
 *
 * It also assumes that the LED pin of the RGB sensor
 * is connected to the signal pin of digital port #5 (zero indexed)
 * of the Core Device Interface Module.
 *
 * You can use the X button on gamepad1 to toggle the LED on and off.
 *
 * Use Android Studio to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list
 */

public class CF_Color_Sensor
{

   int BlueLowerLimit = 185;
   int BlueUpperLimit = 330;
   int RedLowerLimit = 1;
   int RedUpperLimit = 13;

   HardwareMap hwMap = null;

   boolean hueVal;

   CF_Color_En color;

   // we assume that the LED pin of the RGB sensor is connected to
   // digital port 5 (zero indexed).
   static final int LED_CHANNEL = 3;

   public CF_Color_Sensor(CF_Hardware robot)
   {
      //hwMap = ahwMap;
      // get a reference to our ColorSensor object.
      //robot.adafruitRGB = hwMap.get("adafruitRGB");
   }

   public CF_Color_En getColorValues(CF_Hardware robot)
   {
      color = CF_Color_En.UNKNOWN;

      // hsvValues is an array that will hold the hue, saturation, and value information.
      float hsvValues[] = {0F, 0F, 0F};

//      // bLedOn represents the state of the LED.
//      boolean bLedOn = true;

      // convert the RGB values to HSV values.
      Color.RGBToHSV((robot.adafruitRGB.red() * 255) / 800, (robot.adafruitRGB.green() * 255) / 800, (robot.adafruitRGB.blue() * 255) / 800, hsvValues);

      float hue = hsvValues[0];

      if ((hue >= BlueLowerLimit) && (hue <= BlueUpperLimit))
      {
         //find limits. Run w/ two sensors w/ both  saying if b/w this and this
         //"if greater than my other sensor buddy"
         color  = CF_Color_En.BLUE;
      }

      else if ((hue >= RedLowerLimit) && (hue <= RedUpperLimit))
      {
         color = CF_Color_En.RED;
      }

      else
      {
         color = CF_Color_En.UNKNOWN;
      }

      return color;
   }
}