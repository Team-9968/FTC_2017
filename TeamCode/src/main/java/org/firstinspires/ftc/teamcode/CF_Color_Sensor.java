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
   // Threshold values are mapped for rotating the color wheel 90 degrees
   // Reason for rotating is to avoid red color wrapping from 360 to 0
   private static final int BLUE_LOWER_LIMIT = 240;
   private static final int BLUE_UPPER_LIMIT = 300;
   private static final int RED_LOWER_LIMIT = 60;
   private static final int RED_UPPER_LIMIT = 120;

   // hsvValues is an array that will hold the hue, saturation, and value information.
   private float hsvValues[] = {0F,0F,0F};

   private CF_Color_En color = CF_Color_En.INIT;
   float hue = 0.0f;

   public CF_Color_Sensor()
   {
      // Constructor
   }

   /**
    * This method reds the color values from an Adafruit color sensor, converts it to a
    * hue equivalent, and determines what color the sensor is seein (red or blue)
    *
    * @param robot
    * @return color as red, blue, or unknown
    */
   public CF_Color_En getColorValues(CF_Hardware robot)
   {
      // convert the RGB values to HSV values.
      Color.RGBToHSV((robot.adafruitRGB.red() * 255) / 800, (robot.adafruitRGB.green() * 255) / 800, (robot.adafruitRGB.blue() * 255) / 800, hsvValues);

      // hue is the first value in the hsvValues array
      hue = hsvValues[0];

      // Rotate color wheel by 90 degrees to handle red value wrapping from 360 to 0 degrees
      hue += 90.0f;

      // Values greater than 360 need be be mapped back to the 0 to 90 range
      if (hue > 360.0f)
      {
         hue -= 360.0f;
      }

      // Check which color is seen by sensor based on threshold values
      if ((hue >= BLUE_LOWER_LIMIT) && (hue <= BLUE_UPPER_LIMIT))
      {
         color  = CF_Color_En.BLUE;
      }
      else if ((hue >= RED_LOWER_LIMIT) && (hue <= RED_UPPER_LIMIT))
      {
         color = CF_Color_En.RED;
      }
      else
      {
         color = CF_Color_En.UNKNOWN;
      }

      return color;
   }


   /**
    * Get function returning the hue equivalent of RGB values read by Adafruit sensor
    *
    * @return hsvValues[0] which is the hue equivalent value from Color.RGBToHSV()
    */
   public float getColorHue()
   {
      return hue;
   }


   /**
    * Method to turn on Adafruit LED sensor
    */
   public void turnOnAdafruiLED(CF_Hardware robot)
   {
      robot.adafruitRGB.enableLed(true);
   }


   /**
    * Method to turn off Adafruit LED sensor
    */
   public void turnOffAdafruiLED(CF_Hardware robot)
   {
      robot.adafruitRGB.enableLed(false);
   }
}