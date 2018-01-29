package org.firstinspires.ftc.teamcode;

import android.graphics.Color;

//This OpMode does not show up on the phone
public class CF_Color_Sensor

{
   // hsvValues is an array that will hold the hue, saturation, and value information from the sensors.
   private float hsvValues[] = {0F,0F,0F};
   private float secondValues[] = {0F, 0F, 0F};

   //color, wiffleball, and type all contain different labels: red, blue, unknown, and init
   //Since the balls have not yet been seen by the sensor, they are automatically set to init.
   private CF_Color_Enum color = CF_Color_Enum.INIT;
   private CF_SecondSensorEnum wiffleball = CF_SecondSensorEnum.INIT;
   private CF_TypeEnum type = CF_TypeEnum.INIT;

   //This method runs one of the sensors and determines whether that specific ball is blue, red, or unknown
   //based off of the red and blue values it senses.
   public CF_Color_Enum getColorValues(CF_Hardware robot)   //Sensor on RIGHT side of bot when viewed from back
   {
      // convert the RGB values to HSV values.
      Color.RGBToHSV((robot.adafruitRGB.red() * 255) / 800, (robot.adafruitRGB.green() * 255) / 800, (robot.adafruitRGB.blue() * 255) / 800, hsvValues);

      // Check which color is seen by sensor based on threshold values
      if (robot.adafruitRGB.red() - robot.adafruitRGB.blue() > 15)
      {
         color  = CF_Color_Enum.RED;
      }

      else if (robot.adafruitRGB.blue() - robot.adafruitRGB.red() > 15)
      {
         color = CF_Color_Enum.BLUE;
      }

      else
      {
         color = CF_Color_Enum.UNKNOWN;
      }

      return color;
   }

   //This method is the exact same as the one above, except for the other sensor.
   public CF_SecondSensorEnum getWiffleBallValues(CF_Hardware robot)  //Sensor is  LEFT when viewed from back of robot
   {
      // convert the RGB values to HSV values.
      Color.RGBToHSV((robot.adafruitRGBTwo.red() * 255) / 800, (robot.adafruitRGBTwo.green() * 255) / 800, (robot.adafruitRGBTwo.blue() * 255) / 800, secondValues);

      // Check which color is seen by the second sensor based on threshold values
      if (robot.adafruitRGBTwo.red() - robot.adafruitRGBTwo.blue() > 15)
      {
         wiffleball  = CF_SecondSensorEnum.RED;
      }
      else if (robot.adafruitRGBTwo.blue() - robot.adafruitRGBTwo.red() > 15)
      {
         wiffleball = CF_SecondSensorEnum.BLUE;
      }
      else
      {
         wiffleball = CF_SecondSensorEnum.UNKNOWN;
      }

      return wiffleball;
   }

   //This method is a compilation of the two individual sensors' information into one master "Color decision".
   //If the ball on the right is red, the program will not call it red unless the other ball is determined to be blue,
   //and vice versa. It is a safety precaution.
   // This information is then sent to the main autonomous program for use in that opmode.
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