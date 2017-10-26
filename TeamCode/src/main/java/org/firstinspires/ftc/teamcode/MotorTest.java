package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

/**
 * Created by jd72958 on 10/23/2017.
 */

@TeleOp(name=":Isaac's_Sandbox", group="Iterative Opmode")
//@Disabled
public class MotorTest extends OpMode
{
   @Override
   public void init()
   {
   }

   @Override
   public void loop()
   {
      telemetry.addData("Isaac", "Dawson");
      telemetry.update();
   }
}
