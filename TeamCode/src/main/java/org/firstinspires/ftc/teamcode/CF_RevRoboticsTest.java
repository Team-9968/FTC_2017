package org.firstinspires.ftc.teamcode;

import android.annotation.TargetApi;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

/**
 * Created by Ryley on 9/15/17.
 */

//This is a test of the Rev Robotics modules to make a motor spin.
    // Uses CF_Hardware

@TeleOp(name="CF_RevRoboticsTest", group = "test")
//@Disabled
public class CF_RevRoboticsTest extends OpMode {

    // New hardware instance of CF_Hardware
    CF_Hardware robot = new CF_Hardware();

    // Init mode for the robot
    public void init() {
        // Inits the robot to hardwareMap
        robot.init(hardwareMap);
        telemetry.addData("", "init");

    }
    public void start() {
        telemetry.addData("", "Start");
    }
    public void loop() {
        // Sets the motor to full power.
        robot.motorOne.setPower(1);
        robot.motorTwo.setPower(2);
    }
}
