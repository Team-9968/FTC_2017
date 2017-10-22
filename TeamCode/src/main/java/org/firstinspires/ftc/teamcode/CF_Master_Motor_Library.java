package org.firstinspires.ftc.teamcode;

/**
 * Created by Ryley on 10/19/17.
 */

public class CF_Master_Motor_Library {
    // This method sets the mechanum powers.  It sets them and multiplies them by the directionpower
    // multiplier.  This modifies direction and power
    // Set DirectionPower to 1 for default motor direction and power
    void setMechPowers(CF_Hardware bot, double DirectionPower, double LF, double RF, double LR, double RR, double rotate) {
        bot.rightRear.setPower((DirectionPower * RR - rotate));
        bot.rightFront.setPower((DirectionPower * RF) - rotate);
        bot.leftRear.setPower((DirectionPower * LR) + rotate);
        bot.leftFront.setPower((DirectionPower * LF) + rotate);
    }
}
