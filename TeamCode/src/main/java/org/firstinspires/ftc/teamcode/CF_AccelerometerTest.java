package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.Func;
import org.firstinspires.ftc.robotcore.external.navigation.Acceleration;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.Position;
import org.firstinspires.ftc.robotcore.external.navigation.Velocity;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

/**
 * Created by Ryley on 9/25/17.
 */
@TeleOp(name="CF_AccelerometerTest", group = "test")
//@Disabled
public class CF_AccelerometerTest extends OpMode {
    CF_Hardware robot = new CF_Hardware();

    BNO055IMU imu;

    double LFPower = 0.0;
    double RFPower = 0.0;
    double LRPower = 0.0;
    double RRPower = 0.0;
    double drive;
    double strafe;
    double rotate;

    double clawPos = 0;

    int mode = 0;

    Orientation angles;
    Acceleration gravity;


    public void init() {
        robot.init(hardwareMap);


        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.calibrationDataFile = "BNO055IMUCalibration.json";
        parameters.loggingEnabled = false;
        parameters.loggingTag = "IMU";
        parameters.accelerationIntegrationAlgorithm = new JustLoggingAccelerationIntegrator();

        imu = hardwareMap.get(BNO055IMU.class, "imu");
        imu.initialize(parameters);
        telemetry.addData("", "init");

    }

    public void loop(){
        imu.startAccelerationIntegration(new Position(), new Velocity(), 1000);
        updateMode();
        drive();
        runClamp();
        runSpinner();
        runWinch();
        composeTelemetry();



        telemetry.clearAll();
        telemetry.addData("Mode", mode);

    }

    public void updateMode() {
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
    }

    public void drive() {
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
    }

    public void runClamp() {
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
    }

    public void runWinch() {
        robot.Winch.setPower(gamepad2.left_stick_y);
    }

    public void runSpinner() {
        if(gamepad2.x) {
            robot.Spinner.setPosition(0.565);
        } else if(gamepad2.b) {
            robot.Spinner.setPosition(0.55);
        }
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

    void composeTelemetry() {

        // At the beginning of each telemetry update, grab a bunch of data
        // from the IMU that we will then display in separate lines.
        telemetry.addAction(new Runnable() { @Override public void run()
        {
            // Acquiring the angles is relatively expensive; we don't want
            // to do that in each of the three items that need that info, as that's
            // three times the necessary expense.
            angles   = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
            gravity  = imu.getGravity();
        }
        });

        telemetry.addLine()
                .addData("status", new Func<String>() {
                    @Override public String value() {
                        return imu.getSystemStatus().toShortString();
                    }
                })
                .addData("calib", new Func<String>() {
                    @Override public String value() {
                        return imu.getCalibrationStatus().toString();
                    }
                });

        telemetry.addLine()
                .addData("heading", new Func<String>() {
                    @Override public String value() {
                        return formatAngle(angles.angleUnit, angles.firstAngle);
                    }
                })
                .addData("roll", new Func<String>() {
                    @Override public String value() {
                        return formatAngle(angles.angleUnit, angles.secondAngle);
                    }
                })
                .addData("pitch", new Func<String>() {
                    @Override public String value() {
                        return formatAngle(angles.angleUnit, angles.thirdAngle);
                    }
                });

        telemetry.addLine()
                .addData("grvty", new Func<String>() {
                    @Override public String value() {
                        return gravity.toString();
                    }
                })
                .addData("mag", new Func<String>() {
                    @Override public String value() {
                        return String.format(Locale.getDefault(), "%.3f",
                                Math.sqrt(gravity.xAccel*gravity.xAccel
                                        + gravity.yAccel*gravity.yAccel
                                        + gravity.zAccel*gravity.zAccel));
                    }
                });
    }
    String formatAngle(AngleUnit angleUnit, double angle) {
        return formatDegrees(AngleUnit.DEGREES.fromUnit(angleUnit, angle));
    }

    String formatDegrees(double degrees){
        return String.format(Locale.getDefault(), "%.1f", AngleUnit.DEGREES.normalize(degrees));
    }
}
