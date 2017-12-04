package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.navigation.Position;
import org.firstinspires.ftc.robotcore.external.navigation.Velocity;

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
    CF_Accessory_Motor_Library accessory = new CF_Accessory_Motor_Library();
    // Instantiates variables
    int mode = 0;

    double positionUpper = 0.35;
    double positionLower = 0.71;

    boolean changeDirectionLast = false;
    boolean changeDirection = false;

    boolean lastX = false;
    boolean X = false;

    boolean lastB = false;
    boolean B = false;

    boolean lastRB = false;
    boolean RB = false;

    boolean lastLB = false;
    boolean LB = false;
    public void init() {
        // Inits robot
        robot.init(hardwareMap);
        telemetry.addData("", "init");
    }

    public void loop(){
        // Calls appropriate methods to run the robot.  These 4 methods do everything that the robot does, excepting telemetry
        updateMode();
        drive();
        lift();

        clamp();

        telemetry.clearAll();
        telemetry.addData("Mode", mode);
        telemetry.addData("Position Upper", positionUpper);
        telemetry.addData("Position Lower", positionLower);
        telemetry.update();

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
        changeDirection = gamepad1.y;

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
        changeDirectionLast = changeDirection;
    }

    // Implements the lifter motors
    public void lift() {
        accessory.setPowerToPower(robot.clawMotor, gamepad2.right_stick_y, 3);
        accessory.setPowerToPower(robot.mastMotor, -gamepad2.left_stick_y, 3);
    }

    // Clamps the block
    public void clamp() {
//        if(gamepad2.x && positionUpper < 1.0) {
//            positionUpper = positionUpper + 0.01;
//        } else if(gamepad2.b && positionUpper > 0.41) {
//            positionUpper = positionUpper - 0.01;
//        }
//
//        if(gamepad2.dpad_right && positionLower < 0.61) {
//            positionLower = positionLower + 0.01;
//        } else if(gamepad2.dpad_left && positionLower > 0.30) {
//            positionLower = positionLower - 0.01;
//        }
        X = gamepad2.x;
        B = gamepad2.b;
        RB = gamepad2.right_bumper;
        LB = gamepad2.left_bumper;

        if(!lastX && X) {
            if(positionLower == 0.6) {
                positionLower = 0.3;
            } else if(positionLower == 0.3) {
                positionLower = 0.6;
            }
        }

        if(!lastB && B) {
            if(positionUpper == 0.71) {
                positionUpper = 0.41;
            } else if(positionUpper == 0.41) {
                positionUpper = 0.71;
            }
        }

        if(!lastRB && RB) {
            positionUpper = 0.41;
            positionLower = 0.6;
        }

        if(!lastLB && LB) {
            positionUpper = 0.71;
            positionLower = 0.3;
        }

        robot.clamp.setPosition(positionUpper);
        robot.lowerClamp.setPosition(positionLower);
        //lower = 0.386
        //upper = 0.71  0.41
        lastX = X;
        lastB = B;
        lastRB = RB;
        lastLB = LB;
    }
}
