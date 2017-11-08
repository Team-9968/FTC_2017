package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import java.util.concurrent.TimeUnit;

/**
 * Created by Ryley on 9/25/17.
 */
@TeleOp(name="CF_Manual", group = "code")
//@Disabled
public class CF_Manual extends OpMode {
    // Instance of Robot
    CF_Hardware robot = new CF_Hardware();
    CF_Manual_Motor_Library driveMan = new CF_Manual_Motor_Library();

    // Instantiates variables
    int mode = 0;

    public void init() {
        // Inits robot
        robot.init(hardwareMap);
        telemetry.addData("", "init");

    }

    public void loop(){
        // Calls appropriate methods to run the robot.  These 2 methods do everything that the robot does
        updateMode();
        drive();

        telemetry.clearAll();
        telemetry.addData("Mode", mode);

    }

    // Updates drive mode.  0 = normal mech, 1 = tank, 2 = slow mech, 3 = backwards mech
    public void updateMode() {
        if(gamepad1.a) {
            if(mode == 3) {
                mode = 0;
            }
            else if(mode < 3) {
                mode++;
            }

            try {
                TimeUnit.MILLISECONDS.sleep(500);
            } catch (Exception e) {}
        }
    }
    // Implements the drive modes
    public void drive() {
        // Mode to drive mechanum wheels forward at 100 percent power
        if (mode == 0) {
            driveMan.changeDirectonAndPower(1);
            driveMan.runMechWheels(robot, gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x);
        }
        // Mode for tank mode
        if (mode == 1) {
            driveMan.tankMode(robot, gamepad1.left_stick_y, gamepad1.right_stick_y);
        }
        // Mode for half power forward mechanum
        if (mode == 2) {
            driveMan.changeDirectonAndPower(0.5);
            driveMan.runMechWheels(robot, gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x);
        }
        // Mode for full power backwards mechanum
        if (mode == 3) {
            driveMan.changeDirectonAndPower(-1);
            driveMan.runMechWheels(robot, gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x);
        }
    }
}
