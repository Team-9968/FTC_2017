package org.firstinspires.ftc.teamcode;

import android.graphics.Path;

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

    double positionUpperRightClosed = 0.25;
    double positionUpperRightOpen = 0.75;
    double positionUpperRightMiddle = 0.43;

    double positionUpperLeftClosed = 0.7;
    double positionUpperLeftOpen = 0.3;
    double positionUpperLeftMiddle = 0.52;

    double positionLowerRightOpen = 0.95;
    double positionLowerRightClosed = 0.25;
    double positionLowerRightMiddle = 0.43;

    double positionLowerLeftOpen = 0.15;
    double positionLowerLeftClosed = 0.75;
    double positionLowerLeftMiddle = 0.57;


    double positionUpperRight = positionUpperRightOpen;
    double positionUpperLeft = positionUpperLeftOpen;
    double positionLowerRight = positionLowerRightOpen;
    double positionLowerLeft = positionLowerLeftOpen;

    double positionLower = 0.6;
    double botRightClawPos = 0.5;
    double botLeftClawPos = 0.5;

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

    boolean down = false;
    boolean lastDown = false;

    boolean up = false;
    boolean lastUp = false;

    boolean left = false;
    boolean lastLeft = false;

    boolean aDriver = false;
    boolean lastADriver = false;

    boolean bDriver = false;
    boolean lastBDriver = false;

    double start = 0;
    double end = 0;

    double startTime = 0;

    enum mastDown {
        START, STOP, STANDBY
    }

    enum runClaws{
        STOP, FORWARDS, BACKWARDS
    }

    mastDown mDown = mastDown.STANDBY;
    runClaws claws = runClaws.STOP;

    public void init() {
        // Inits robot
        robot.init(hardwareMap);
        telemetry.addData("", "init");
        start = robot.clawMotor.getCurrentPosition();
//        robot.jewelHitter.setPosition(0.333);
//        robot.colorArm.setPosition(1.0);

        end = start + 1719;
        robot.leftFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        robot.rightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        robot.leftRear.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        robot.rightRear.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        // Inits masts to down position
//        while(robot.limit.getState()) {
//            accessory.setPowerToPower(robot.clawMotor, 1, 3);
//            accessory.setPowerToPower(robot.mastMotor, 1, 3);
//        }
//        accessory.setPowerToPower(robot.clawMotor, 0, 3);
//        accessory.setPowerToPower(robot.mastMotor, 0, 3);
    }

    public void loop(){
        // Calls appropriate methods to run the robot.  These 3 methods do everything that the robot does, excepting telemetry
        updateMode();
        drive();
        lift();
        clamp();

        telemetry.clearAll();
        telemetry.addData("Mode", mode);
        telemetry.addData("Position Side", robot.colorArm.getPosition());
        telemetry.addData("Position Lower", positionLower);
        telemetry.addData("Position Claw", robot.clawMotor.getCurrentPosition());

        telemetry.addData("Position RightFront", robot.rightFront.getCurrentPosition());
        telemetry.addData("Position LeftFront", robot.leftFront.getCurrentPosition());
        telemetry.addData("Position RightRear", robot.rightRear.getCurrentPosition());
        telemetry.addData("Position LeftRear", robot.leftRear.getCurrentPosition());

        telemetry.addData("Position TopRight", botRightClawPos);
        telemetry.addData("Position TopLeft", botLeftClawPos);

        bDriver = gamepad1.b;

        if(bDriver && !lastBDriver){
            robot.rightFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            robot.leftFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            robot.rightRear.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            robot.leftRear.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

            robot.rightFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            robot.leftFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            robot.rightRear.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            robot.leftRear.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        }

        lastBDriver = bDriver;


        telemetry.update();

    }

    // Updates drive mode.  0 = normal mech, 1 = tank, 2 = slow mech, 3 = backwards mech
    public void updateMode() {
        aDriver = gamepad1.a;
        if(aDriver && !lastADriver) {
            if(mode == 1) {
                mode = 0;
            }
            else if(mode == 0) {
                mode = 1;
            }
        }
        lastADriver = aDriver;
    }
    // Implements the drive modes
    public void drive() {
        // Implements Owen's switchy thingamajigger
        changeDirection = gamepad1.y;
        if(!changeDirectionLast && changeDirection) {
            //Invert is the multiplier to switch the gizmo
            invert = -1 * invert;
        }
        // Mode to drive mechanum wheels forward at 100 percent power
        if (mode == 0) {
            driveMan.changeDirectonAndPower(1);
            driveMan.runMechWheels(robot, invert * gamepad1.left_stick_y, invert * gamepad1.left_stick_x, gamepad1.right_stick_x, 3, 7);
        }
        // Mode for slow mode
        if (mode == 1) {
            driveMan.runMechWheels(robot, invert * gamepad1.left_stick_y, invert * gamepad1.left_stick_x, gamepad1.right_stick_x, 3, 7, 0.6);
        }
        // Mode for half power forward mechanum
        if (mode == 2) {
            driveMan.changeDirectonAndPower(0.5);
            driveMan.runMechWheels(robot, invert * gamepad1.left_stick_y, invert * gamepad1.left_stick_x, gamepad1.right_stick_x, 3);
        }
        // Mode for full power backwards mechanum
        if (mode == 3) {
            driveMan.changeDirectonAndPower(-1);
            driveMan.runMechWheels(robot, invert * gamepad1.left_stick_y, invert * gamepad1.left_stick_x, gamepad1.right_stick_x, 3);
        }
        telemetry.addData("mode", mode);
        changeDirectionLast = changeDirection;
    }

    // Implements the lifter motors
    public void lift() {
        //To get rid of the quick lift method, comment out everything in this method except for the top two lines.
        accessory.setPowerToPower(robot.clawMotor, gamepad2.right_stick_y, 3);
        accessory.setPowerToPower(robot.mastMotor, gamepad2.left_stick_y, 3);
 //       down = gamepad2.dpad_down;

//        switch(mDown) {
//            case STANDBY:
//                if (down && !lastDown) {
//                    mDown = mastDown.START;
//                    startTime = getRuntime();
//                }
//                break;
//            case START:
//                accessory.setPowerToPower(robot.clawMotor, 1, 3);
//                accessory.setPowerToPower(robot.mastMotor, 1, 3);
//                // Implemented 5 second timeout
//                if (!robot.limit.getState() || getRuntime() - startTime > 5000) {
//                    mDown = mastDown.STOP;
//                }
//                break;
//            case STOP:
//                accessory.setPowerToPower(robot.clawMotor, 0, 3);
//                accessory.setPowerToPower(robot.mastMotor, 0, 3);
//                positionLower = 0.6;
//                positionUpper = 0.41;
//                mDown = mastDown.STANDBY;
//                break;
//        }
    }

    // Clamps the block
    public void clamp() {
        A = gamepad2.a;
        Y = gamepad2.y;
        RB = gamepad2.right_bumper;
        LB = gamepad2.left_bumper;
        B = gamepad2.b;
        X = gamepad2.x;
        up = gamepad2.dpad_up;
        left = gamepad2.dpad_left;

        if(left && !lastLeft) {
            if(claws == runClaws.STOP) {
                claws = runClaws.FORWARDS;
            } else if(claws == runClaws.FORWARDS){
                claws = runClaws.BACKWARDS;
            } else if(claws == runClaws.BACKWARDS) {
                claws = runClaws.STOP;
            }
        }

//        switch(claws) {
//            case STOP:
//                botRightClawPos = 0.5;
//                botLeftClawPos = 0.5;
//                break;
//            case FORWARDS:
//                botRightClawPos = 0.9;
//                botLeftClawPos = 0.1;
//                break;
//            case BACKWARDS:
//                botRightClawPos = 0.1;
//                botLeftClawPos = 0.9;
//                break;
//        }

        if(!lastUp && up) {
            positionUpperLeft = positionUpperLeftMiddle;
            positionUpperRight = positionUpperRightMiddle;

            positionLowerLeft = positionLowerLeftMiddle;
            positionLowerRight = positionLowerRightMiddle;
        }

        if(!lastLB && LB) {
            positionLowerLeft = positionLowerLeftOpen;
            positionLowerRight = positionLowerRightOpen;

            positionUpperLeft = positionUpperLeftOpen;
            positionUpperRight = positionUpperRightOpen;
        }
        if(!lastRB && RB) {
            positionLowerLeft = positionLowerLeftClosed;
            positionLowerRight = positionLowerRightClosed;

            positionUpperLeft = positionUpperLeftClosed;
            positionUpperRight = positionUpperRightClosed;
        }

        if(!lastA && A) {
            //0.2
            if(positionLowerLeft == positionLowerLeftClosed) {
                positionLowerLeft = positionLowerLeftOpen;
            } else if(positionLowerLeft == positionLowerLeftOpen) {
                positionLowerLeft = positionLowerLeftClosed;
            }

            if(positionLowerLeft == positionLowerLeftMiddle) {
                positionLowerLeft = positionLowerLeftOpen;
            }

            if(positionLowerRight == positionLowerRightClosed) {
                positionLowerRight = positionLowerRightOpen;
            } else if(positionLowerRight == positionLowerRightOpen) {
                positionLowerRight = positionLowerRightClosed;
            }

            if(positionLowerRight == positionLowerRightMiddle) {
                positionLowerRight = positionLowerRightOpen;
            }
        }

        if(!lastY && Y) {
            if(positionUpperLeft == positionUpperLeftClosed) {
                positionUpperLeft = positionUpperLeftOpen;
            } else if(positionUpperLeft == positionUpperLeftOpen) {
                positionUpperLeft = positionUpperLeftClosed;
            }

            if(positionUpperLeft == positionUpperLeftMiddle) {
                positionUpperLeft = positionUpperLeftOpen;
            }

            if(positionUpperRight == positionUpperRightClosed) {
                positionUpperRight = positionUpperRightOpen;
            } else if(positionUpperRight == positionUpperRightOpen) {
                positionUpperRight = positionUpperRightClosed;
            }

            if(positionUpperRight == positionUpperRightMiddle) {
                positionUpperRight = positionUpperRightOpen;
            }
        }
        robot.upperRightClaw.setPosition(positionUpperRight);
        robot.upperLeftClaw.setPosition(positionUpperLeft);
        robot.lowerRightClaw.setPosition(positionLowerRight);
        robot.lowerLeftClaw.setPosition(positionLowerLeft);

        //lower = 0.386
        //upper = 0.71  0.41
        lastX = X;
        lastB = B;
        lastY = Y;
        lastA = A;
        lastRB = RB;
        lastLB = LB;
        lastUp = up;
        lastLeft = left;
    }
}
