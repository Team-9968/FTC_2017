package org.firstinspires.ftc.teamcode;

/**
 * Created by Ryley on 10/15/17.
 */

public class CF_Manual_Motor_Library {
    // Variables to hold motor powers
    double LFPower;
    double RFPower;
    double LRPower;
    double RRPower;

    // Default direction: full power forwards.  When
    // this variable is negative and backwards, and this number
    // can't be over 1 or it will try to send too much power
    // to the motors.  If the absolute value of this variable
    // is smaller than 1, then it will be sending the motor
    // a power directly proportional to the number
    double DirectionPower = 1;

    // Tank mode.  It drives the robot based on the left and right
    // sticks.  The left one drives the left side of the robot,
    // and the right one drives the right side of the robot
    void tankMode(CF_Hardware bot, double left, double right) {
        bot.leftFront.setPower(left);
        bot.rightFront.setPower(right);
        bot.leftRear.setPower(left);
        bot.rightRear.setPower(right);
    }

    // This runs the mech wheels.  The signs are appropriate to drive the correct motors
    // in the correct direction
    void runMechWheels(CF_Hardware bot, double drive, double strafe, double rotate) {
        // Sets the individual powers to the individual motors
        LFPower = -strafe + drive + rotate;
        RFPower = +strafe + drive - rotate;
        LRPower = +strafe + drive + rotate;
        RRPower = -strafe + drive - rotate;
        // Sets the mechanum powers
        setMechPowers(bot, LFPower, RFPower, LRPower, RRPower);
    }

    // This method sets the mechanum powers.  It sets them and multiplies them by the directionpower
    // multiplier.  This modifies direction and power
    void setMechPowers(CF_Hardware robo, double LF, double RF, double LR, double RR) {
        robo.rightRear.setPower(DirectionPower * RR);
        robo.rightFront.setPower(DirectionPower * RF);
        robo.leftRear.setPower(DirectionPower * LR);
        robo.leftFront.setPower(DirectionPower * LF);
    }

    // This is just a mutator method to set the directionpower variable
    void changeDirectonAndPower(double power) {
        DirectionPower = power;
    }
}
