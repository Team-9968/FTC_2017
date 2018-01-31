package org.firstinspires.ftc.teamcode;

import android.graphics.Color;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Enums.CF_Color_Enum;
import org.firstinspires.ftc.teamcode.Enums.CF_SecondSensorEnum;

/**
 * Created by dawso on 11/20/2017.
 */

@TeleOp(name = "Sensor_Raw", group = "Sensor")
//@Disabled
public class CF_Sensor_Raw extends LinearOpMode
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

            robot.jewelHitter.setPosition(0.53);


            // convert the RGB values to HSV values.
            Color.RGBToHSV((robot.adafruitRGB.red() * 255) / 800, (robot.adafruitRGB.green() * 255) / 800, (robot.adafruitRGB.blue() * 255) / 800, hsvValues);
            Color.RGBToHSV((robot.adafruitRGBTwo.red() * 255) / 800, (robot.adafruitRGBTwo.green() * 255) / 800, (robot.adafruitRGBTwo.blue() * 255) / 800, hsvValues);

            //this sensor is on the right when robot is viewed from the back
            // Check which color is seen by sensor based on threshold values

            telemetry.addData("Right Red", robot.adafruitRGB.red());
            telemetry.addData("Right Blue", robot.adafruitRGB.blue());
            telemetry.addData("Left Red", robot.adafruitRGBTwo.red());
            telemetry.addData("Left Blue", robot.adafruitRGBTwo.blue());

            if((robot.adafruitRGB.red() > robot.adafruitRGB.blue()) && (robot.adafruitRGBTwo.blue() > robot.adafruitRGBTwo.red())){
                telemetry.addData("Right Red, Left Blue","");
            } else if((robot.adafruitRGB.blue() > robot.adafruitRGB.red()) && (robot.adafruitRGBTwo.red() > robot.adafruitRGBTwo.blue())) {
                telemetry.addData("Right Blue, Left Red","");
            } else {
                telemetry.addData("Unknown", "");
            }

//            if (robot.adafruitRGB.red() - robot.adafruitRGB.blue() > 35)
//            {
//                color  = CF_Color_Enum.RED;
//                telemetry.addData("Right Sensor = red" , robot.adafruitRGB.red() - robot.adafruitRGB.blue());
//            }
//            else if (robot.adafruitRGB.blue() - robot.adafruitRGB.red() > 20)
//            {
//                color = CF_Color_Enum.BLUE;
//                telemetry.addData("Right Sensor = blue" , robot.adafruitRGB.blue() - robot.adafruitRGB.red());
//            }
//            else
//            {
//                color = CF_Color_Enum.UNKNOWN;
//                telemetry.addData("Right Sensor = unknown" , Math.abs(robot.adafruitRGB.red() - robot.adafruitRGB.blue()));
//            }

            //When robot is viewed form back, this sensor is on the left.
            // Check which color is seen by the second sensor based on threshold values
//            if (robot.adafruitRGBTwo.red() - robot.adafruitRGBTwo.blue() > 35)
//            {
//                wiffleball  = CF_SecondSensorEnum.RED;
//                telemetry.addData("Left Sensor = red" , robot.adafruitRGBTwo.red() - robot.adafruitRGBTwo.blue());
//            }
//            else if (robot.adafruitRGBTwo.blue() - robot.adafruitRGBTwo.red() > 20)
//            {
//                wiffleball = CF_SecondSensorEnum.BLUE;
//                telemetry.addData("Left Sensor = blue" , robot.adafruitRGBTwo.blue() - robot.adafruitRGBTwo.red());
//            }
//            else
//            {
//                wiffleball = CF_SecondSensorEnum.UNKNOWN;
//                telemetry.addData("Left Sensor = unknown" , Math.abs(robot.adafruitRGBTwo.red() - robot.adafruitRGBTwo.blue()));
//            }

            telemetry.update();
        }
    }
}
