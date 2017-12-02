package org.firstinspires.ftc.teamcode;

import android.graphics.Color;

public class CF_Color_Sensor

{
   // hsvValues is an array that will hold the hue, saturation, and value information.
   private float hsvValues[] = {0F,0F,0F};
   private float secondValues[] = {0F, 0F, 0F};

   private CF_Color_Enum color = CF_Color_Enum.INIT;
   private CF_SecondSensorEnum wiffleball = CF_SecondSensorEnum.INIT;
   private CF_TypeEnum type = CF_TypeEnum.INIT;

   public CF_Color_Sensor()
   {
      // Constructor
   }

   /**
    * This method reds the color values from an Adafruit color sensor, converts it to a
    * hue equivalent, and determines what color the sensor is seeing (red or blue)
    *
    * @param robot
    * @return color as red, blue, or unknown
    */
   public CF_Color_Enum getColorValues(CF_Hardware robot)   //Sensor on RIGHT side of bot when viewed from back
   {
      // convert the RGB values to HSV values.
      Color.RGBToHSV((robot.adafruitRGB.red() * 255) / 800, (robot.adafruitRGB.green() * 255) / 800, (robot.adafruitRGB.blue() * 255) / 800, hsvValues);

      // Check which color is seen by sensor based on threshold values
      if (robot.adafruitRGB.red() - robot.adafruitRGB.blue() > 20)
      {
         color  = CF_Color_Enum.RED;
      }
      else if (robot.adafruitRGB.blue() - robot.adafruitRGB.red() > 20)
      {
         color = CF_Color_Enum.BLUE;
      }
      else
      {
         color = CF_Color_Enum.UNKNOWN;
      }

      return color;
   }

   /**
    * This method reds the color values from an Adafruit color sensor, converts it to a
    * hue equivalent, and determines what color the sensor is seeing (red or blue)
    *
    * @param robot
    * @return color as red, blue, or unknown
    */
   public CF_SecondSensorEnum getWiffleBallValues(CF_Hardware robot)  //Sensor is  LEFT when viewed from back of robot
   {
      // convert the RGB values to HSV values.
      Color.RGBToHSV((robot.adafruitRGBTwo.red() * 255) / 800, (robot.adafruitRGBTwo.green() * 255) / 800, (robot.adafruitRGBTwo.blue() * 255) / 800, hsvValues);

      // Check which color is seen by the second sensor based on threshold values
      if (robot.adafruitRGBTwo.red() - robot.adafruitRGBTwo.blue() > 20)
      {
         wiffleball  = CF_SecondSensorEnum.RED;
      }
      else if (robot.adafruitRGBTwo.blue() - robot.adafruitRGBTwo.red() > 20)
      {
         wiffleball = CF_SecondSensorEnum.BLUE;
      }
      else
      {
         wiffleball = CF_SecondSensorEnum.UNKNOWN;
      }

      return wiffleball;
   }

   public CF_TypeEnum setType(CF_Hardware robot)
   {
      getColorValues(robot);
      getWiffleBallValues(robot);

      if (color == CF_Color_Enum.RED && wiffleball == CF_SecondSensorEnum.BLUE)
      {
         type = CF_TypeEnum.RIGHTISRED;
      }

      else if (color == CF_Color_Enum.BLUE && wiffleball == CF_SecondSensorEnum.RED)
      {
         type = CF_TypeEnum.RIGHTISBLUE;
      }

      else
      {
         type = CF_TypeEnum.UNKNOWN;
      }

      return type;
   }
}