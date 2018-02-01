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
    double position = 0.33;
    int mode = 0;

    double positionUpper = 0.81;
    double positionLower = 0.6;

    boolean changeDirectionLast = false;
    boolean changeDirection = false;

    int invert = 1;

    boolean lastY = false;
    boolean Y = false;

    boolean lastA = false;
    boolean A = false;

    boolean lastB = false;
    boolean B = false;

    boolean lastRB = false;
    boolean RB = false;

    boolean lastLB = false;
    boolean LB = false;

    boolean X = false;
    boolean lastX = false;

    double start = 0;
    double end = 0;

    public void init() {
        // Inits robot
        robot.init(hardwareMap);
        telemetry.addData("", "init");
        start = robot.clawMotor.getCurrentPosition();
       // robot.jewelHitter.setPosition(1);
        end = start + 1719;
    }

    public void loop(){
        // Calls appropriate methods to run the robot.  These 4 methods do everything that the robot does, excepting telemetry
        updateMode();
        drive();
        lift();
        clamp();

        telemetry.clearAll();
        telemetry.addData("Mode", mode);
        telemetry.addData("Position Side", robot.colorArm.getPosition());
        telemetry.addData("Position Upper", positionUpper);
        telemetry.addData("Position Lower", positionLower);
        telemetry.addData("Position Claw", robot.clawMotor.getCurrentPosition());
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
        // Implements Owen's switchy thingamajigger
        changeDirection = gamepad1.y;
        if(!changeDirectionLast && changeDirection) {
            //Invert is the multiplyer to switch the gizmo
            invert = -1 * invert;
        }
        // Mode to drive mechanum wheels forward at 100 percent power
        if (mode == 0) {
            driveMan.changeDirectonAndPower(1);
            driveMan.runMechWheels(robot, invert * gamepad1.left_stick_y, invert * gamepad1.left_stick_x, gamepad1.right_stick_x);
        }
        // Mode for tank mode
        if (mode == 1) {
            driveMan.tankMode(robot, invert * gamepad1.left_stick_y, invert * gamepad1.right_stick_y);
        }
        // Mode for half power forward mechanum
        if (mode == 2) {
            driveMan.changeDirectonAndPower(0.5);
            driveMan.runMechWheels(robot, invert * gamepad1.left_stick_y, invert * gamepad1.left_stick_x, gamepad1.right_stick_x);
        }
        // Mode for full power backwards mechanum
        if (mode == 3) {
            driveMan.changeDirectonAndPower(-1);
            driveMan.runMechWheels(robot, invert * gamepad1.left_stick_y, invert * gamepad1.left_stick_x, gamepad1.right_stick_x);
        }
        changeDirectionLast = changeDirection;
    }

    // Implements the lifter motors
    public void lift() {
        //To get rid of the quick lift method, comment out everything in this method except for the top two lines.
        accessory.setPowerToPower(robot.clawMotor, gamepad2.right_stick_y, 3);
        accessory.setPowerToPower(robot.mastMotor, -gamepad2.left_stick_y, 3);
//        B = gamepad2.b;
//        X = gamepad2.start;
//        if(!lastB && B && robot.clawMotor.getCurrentPosition() < end) {
//            while(robot.clawMotor.getCurrentPosition() < end) {
//                accessory.setPowerToPower(robot.clawMotor, -1, 3);
//            }
//            accessory.setPowerToPower(robot.clawMotor,0,3);
//        }
//
//        if(!lastX && X) {
//            start = robot.clawMotor.getCurrentPosition();
//            end = start + 1719;
//        }
//
//        lastB = B;
//        lastX = X;
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
        A = gamepad2.a;
        Y = gamepad2.y;
        RB = gamepad2.right_bumper;
        LB = gamepad2.left_bumper;
        B = gamepad2.b;
        X = gamepad2.x;

        if(X) {
            position += 0.001;
        }
        if(B) {
            position -= 0.001;
        }

        if(!lastA && A) {
            //0.3
            if(positionLower == 0.6) {
                positionLower = 0.3;
            } else if(positionLower == 0.3) {
                positionLower = 0.6;
            }
        }

        // Debouncing for the buttons
        if(!lastY && Y) {
            //0.81
            if(positionUpper == 0.81) {
                positionUpper = 0.41;
            } else if(positionUpper == 0.41) {
                positionUpper = 0.81;
            }
        }

        if(!lastRB && RB) {
            positionUpper = 0.41;
            positionLower = 0.6;
        }

        if(!lastLB && LB) {
            positionUpper = 0.81;
            positionLower = 0.3;
        }

        robot.clamp.setPosition(positionUpper);
        robot.lowerClamp.setPosition(positionLower);
       // robot.jewelHitter.setPosition(position);
        robot.colorArm.setPosition(position);

        //lower = 0.386
        //upper = 0.71  0.41
        lastX = X;
        lastB = B;
        lastY = Y;
        lastA = A;
        lastRB = RB;
        lastLB = LB;
    }
}
