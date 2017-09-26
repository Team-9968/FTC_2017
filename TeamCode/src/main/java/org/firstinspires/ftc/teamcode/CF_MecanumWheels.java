package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import java.util.concurrent.TimeUnit;

/**
 * Created by Ryley on 9/25/17.
 */
@TeleOp(name="CF_MecanumWheels", group = "test")
//@Disabled
public class CF_MecanumWheels extends OpMode {
    CF_Hardware robot = new CF_Hardware();

    double LFPower = 0.0;
    double RFPower = 0.0;
    double LRPower = 0.0;
    double RRPower = 0.0;
    double drive;
    double strafe;
    double rotate;

    double clawPos = 0;

    int mode = 0;


    public void init() {
        robot.init(hardwareMap);
        telemetry.addData("", "init");
    }

    public void loop(){
        if(gamepad1.a) {
            if(mode == 2) {
                mode = 0;
            }
            else if(mode < 2) {
                mode++;
            }

            try {
                TimeUnit.MILLISECONDS.sleep(500);
            } catch (Exception e) {}
        }


        if(mode == 0){
            runMechWheels();
        }

        if(mode == 1) {
            robot.leftFront.setPower(gamepad1.right_stick_y);
            robot.leftRear.setPower(gamepad1.right_stick_y);
            robot.rightFront.setPower(gamepad1.left_stick_y);
            robot.rightRear.setPower(gamepad1.left_stick_y);
        }

        if(mode == 2) {
            runMechWheelsSlow();
        }
        if(gamepad2.y) {
            if(clawPos < 1) {
                clawPos += 0.01;
            }
        }

        if(gamepad2.a) {
            if(clawPos > 0) {
                clawPos -= 0.01;
            }
        }

        robot.Clamp.setPosition(clawPos);
        telemetry.clearAll();
        telemetry.addData("Mode", mode);

    }

    public void runMechWheels() {
        drive = gamepad1.left_stick_y;
        strafe = gamepad1.left_stick_x;
        rotate = gamepad1.right_stick_x;
        // Strafe + Drive + Rotate
        LFPower = -strafe + drive + rotate;
        RFPower = +strafe + drive - rotate;
        LRPower = +strafe + drive + rotate;
        RRPower = -strafe + drive - rotate;
        setMechPowers(LFPower, RFPower, LRPower, RRPower);
    }

    public void runMechWheelsSlow() {
        drive = gamepad1.left_stick_y;
        strafe = gamepad1.left_stick_x;
        rotate = gamepad1.right_stick_x;
        // Strafe + Drive + Rotate
        LFPower = -strafe + drive + rotate;
        RFPower = +strafe + drive - rotate;
        LRPower = +strafe + drive + rotate;
        RRPower = -strafe + drive - rotate;
        setMechPowers(0.5 * LFPower, 0.5 * RFPower, 0.5 * LRPower, 0.5 * RRPower);
    }

    public void setMechPowers(double LF, double RF, double LR, double RR) {
        robot.rightRear.setPower(RR);
        robot.rightFront.setPower(RF);
        robot.leftRear.setPower(LR);
        robot.leftFront.setPower(LF);
    }
}
