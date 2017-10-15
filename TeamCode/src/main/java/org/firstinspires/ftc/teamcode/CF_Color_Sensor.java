package org.firstinspires.ftc.teamcode;

import android.app.Activity;
import android.graphics.Color;
import android.hardware.Sensor;
import android.view.View;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;

/**
 * Created by dawso on 10/1/2017.
 */

@Autonomous(name = "CF_ColorSensor", group = "Sensor")
//@Disabled                            // Comment this out to add to the opmode list
public class CF_Color_Sensor extends LinearOpMode
{
   CF_Hardware robot = new CF_Hardware();
   public ColorSensor adafruitRGB = null;

   public void runOpMode()
   {
         adafruitRGB = hardwareMap.colorSensor.get("adafruitRGB");


      // hsvValues is an array that will hold the hue, saturation, and value information.
      float hsvValues[] = {0F,0F,0F};

      // values is a reference to the hsvValues array.
      final float values[] = hsvValues;

      // get a reference to the RelativeLayout so we can change the background
      // color of the Robot Controller app to match the hue detected by the adafruitRGB sensor.
      int relativeLayoutId = hardwareMap.appContext.getResources().getIdentifier("RelativeLayout", "id", hardwareMap.appContext.getPackageName());
      final View relativeLayout = ((Activity) hardwareMap.appContext).findViewById(relativeLayoutId);

      // bPrevState and bCurrState represent the previous and current state of the button.
      boolean bPrevState = false;
      boolean bCurrState = false;

//      // get a reference to our DeviceInterfaceModule object.
//      cdim = hardwareMap.deviceInterfaceModule.get("dim");

      // set the digital channel to output mode.
      // remember, the Adafruit sensor is actually two devices.
//      // It's an I2C sensor and it's also an LED that can be turned on or off.
//      cdim.setDigitalChannelMode(LED_CHANNEL, DigitalChannel.Mode.OUTPUT);

      // wait for the start button to be pressed.
      waitForStart();

      // loop and read the adafruitRGB data.
      // Note we use opModeIsActive() as our loop condition because it is an interruptible method.
      while (opModeIsActive())  {

         // check the status of the x button on gamepad.
         bCurrState = gamepad1.x;

         // update previous state variable.
         bPrevState = bCurrState;

          //convert the adafruitRGB values to HSV values.
         Color.RGBToHSV(((adafruitRGB.red() * 255) / 800), (adafruitRGB.green() * 255) / 800, ((adafruitRGB.blue() * 255) / 800), hsvValues);

         // send the info back to driver station using telemetry function.
         //telemetry.addData("Red", robot.adafruitRGB.red());

         // change the background color to match the color detected by the adafruitRGB sensor.
         // pass a reference to the hue, saturation, and value array as an argument
         // to the HSVToColor method.
         relativeLayout.post(new Runnable() {
            public void run() {
               relativeLayout.setBackgroundColor(Color.HSVToColor(0xff, values));
            }
         });

         telemetry.update();
      }

      // Set the panel back to the default color
      relativeLayout.post(new Runnable() {
         public void run() {
            relativeLayout.setBackgroundColor(Color.WHITE);
         }
      });
   }
}
