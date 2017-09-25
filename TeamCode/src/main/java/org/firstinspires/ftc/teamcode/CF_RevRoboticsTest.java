package org.firstinspires.ftc.teamcode;

import android.annotation.TargetApi;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.Range;

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


    // Joystick threshold sets a minimum value for the controller joysticks
// to reach before the robot will begin to move.
    private static final float joystickThreshold = 0.003f;

    //    When both of the joysticks used for driving are held at the same time, the
//    robot cannot fully do both functions. Priority sets the team's driving
//    priority - whether strafing, turning, or driving straight should have more
//    importance, causing to robot to perform more of one action than of the others.
    private static final float forwardPriority = 1.0f;
    private static final float strafePriority = 1.0f;
    private static final float steerPriority = 1.0f;
    private static final float forwardGain_Scoop = 1.0f;
    private static final float strafeGain_Scoop = 1.0f;
    private static final float steerGain_Scoop = 1.0f;
    private static final float forwardGain_Lifter = 0.5f;
    private static final float strafeGain_Lifter = 1.0f;
    private static final float steerGain_Lifter = 0.5f;

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
//        robot.leftFront.setPower(-gamepad1.left_stick_y);
//        robot.leftRear.setPower(-gamepad1.left_stick_y);
//        robot.rightFront.setPower(-gamepad1.right_stick_y);
//        robot.rightRear.setPower(-gamepad1.right_stick_y);


        robot.Pincher.setPower(0.0f);
        robot.Clamp.setPosition(gamepad2.left_stick_y);
        robot.Pincher.setPower(gamepad2.right_stick_y);
        //setMecanumPowers(1,1,1,1);
        //RunMecanumWheels();
    }

    public void RunMecanumWheels()
    {
        double LFPower = 0.0;
        double RFPower = 0.0;
        double LRPower = 0.0;
        double RRPower = 0.0;
        double leftStickY;
        double leftStickX;
        double rightStickX;
        // Calculate motor powers but only if any of the joystick commands are greater then
        // a minimum threshold.  Adjust this threshold if the motor has motion when the joystick
        // is not being used and in the center position.
        if ((Math.abs(gamepad1.left_stick_y) >= joystickThreshold) ||
                (Math.abs(gamepad1.left_stick_x) >= joystickThreshold) ||
                (Math.abs(gamepad1.right_stick_x) >= joystickThreshold))
        {
            leftStickY = ScaleJoystickCommand(gamepad1.left_stick_y);
            leftStickX = ScaleJoystickCommand(gamepad1.left_stick_x);
            rightStickX = ScaleJoystickCommand(gamepad1.right_stick_x);


            // Calculate power for each mecanum wheel based on joystick inputs.  Each power is
            // based on three drive components: forward/reverse, strafe, and tank turn.
            //There are three drive modes. This mode, Beacon, drives the robot with the beacon
            //pusher servo in the front.

            LFPower = (forwardPriority * -leftStickY) - (strafePriority * -leftStickX) - (steerPriority * -rightStickX);
            RFPower = (forwardPriority * -leftStickY) + (strafePriority * -leftStickX) + (steerPriority * -rightStickX);
            LRPower = (forwardPriority * -leftStickY) + (strafePriority * -leftStickX) - (steerPriority * -rightStickX);
            RRPower = (forwardPriority * -leftStickY) - (strafePriority * -leftStickX) + (steerPriority * -rightStickX);

            // Find maximum power commanded to all the mecanum wheels.  Using the above power
            // equations, it is possible to calculate a power command greater than 1.0f (100%).
            // We want to find the max value so we can proportionally reduce motor powers.
            double maxPower = Math.max(LFPower, Math.max(RFPower, Math.max(LRPower, RRPower)));

            // If max power is greater than 1.0f (100% command), then proportionally reduce all motor
            // powers by the maximum power calculated.  This will equally reduce all powers so no
            // motor power is clipped and the robot responds predictably to joystick commands.  If we
            // don't, then one or more motor commands will clip, others will not, and the robot will not
            // behave predictably.  The end result of this reduction is the motor requesting max power
            // will set power to 1.0f (100%) and all other powers will be reduced by the same ratio.
            if (Math.abs(maxPower) > 1.0f)
            {
                LFPower /= maxPower; // Shorthand for LFPower = LFPower / maxPower
                RFPower /= maxPower;
                LRPower /= maxPower;
                RRPower /= maxPower;
            }

            // Update motor powers with new value.
            setMecanumPowers(LFPower, RFPower, LRPower, RRPower);
        }
        else
        {
            // Explicitly set powers to zero.  May not be necessary but is good practice.
            setMecanumPowers(0.0, 0.0, 0.0, 0.0);
        }
    }

    public void setMecanumPowers(double rf, double rr, double lr, double lf) {
        robot.rightFront.setPower(rf);
        robot.rightRear.setPower(rr);
        robot.leftRear.setPower(lr);
        robot.leftFront.setPower(lf);
    }

    public void SetClampPosition(double servoPositionDesired)
    {
        double servoPosotionActual = Range.clip(servoPositionDesired, 0.0, 1.0);
        robot.Clamp.setPosition(servoPosotionActual);
    }

//    public void GetClampPosition()
//    {
//        double position = 0.0;
//
//        if (robot.Clamp != null)
//        {
//            position = robot.Clamp.getPosition();
//            return position;
//        }
//
//    }
//    public void ServiceServo()
//    {
//        double ClampPosition = GetClampPosition();
//
//        if (gamepad1.x)
//        {
//            SetClampPosition(ClampPosition + 0.05);
//        }
//
//        else if (gamepad1.b)
//        {
//            SetClampPosition(ClampPosition - 0.05);
//        }
//    }
    public double ScaleJoystickCommand(double input)
    {
        double scaledInput;
        final int numPointsInMap = 34;

        // Ensure the values make sense.  Clip the values to max/min values
        double clippedPower = Range.clip(input, -1, 1);

//      // Array used to map joystick input to motor output
//      double[] scalingArray = {0, 0.01, 0.02, 0.04, 0.05, 0.08, 0.11,
//         0.13, 0.17, 0.23, 0.32, 0.4, 0.48, 0.61, 0.73, 0.89, 1};

        // Array used to map joystick input to motor output
        double[] scalingArray =
                {0, 0.001, 0.0015, 0.002, 0.0026, 0.003, 0.0036,  0.004, 0.0046, 0.005, 0.058, 0.0063, 0.007, 0.01, 0.015, 0.02, 0.025, 0.03, 0.035, 0.04, 0.047, 0.05, 0.065, 0.08, 0.11,
                        0.13, 0.17, 0.23, 0.32, 0.4, 0.48, 0.61, 0.73, 0.89, 1};

        // Get the corresponding index for the specified argument/parameter.
        int index = (int) (clippedPower * numPointsInMap);

        // Array indexes can only be positive so we need to drop the negative
        if (index < 0)
        {
            index = -index;
        }

        // Limit indexes to actual size of array so we don't overflow
        if (index > numPointsInMap)
        {
            index = numPointsInMap;
        }

        // Handle negative power values as the table only had positive values
        if (clippedPower < 0)
        {
            scaledInput = -scalingArray[index];
        }
        else
        {
            scaledInput = scalingArray[index];
        }

        return scaledInput;
    }
}
