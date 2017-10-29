package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;

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

    void setMode(CF_Hardware bot, DcMotor.RunMode mode){
        bot.rightRear.setMode(mode);
        bot.rightFront.setMode(mode);
        bot.leftRear.setMode(mode);
        bot.leftFront.setMode(mode);
    }

    double getEncoderCounts(CF_Hardware bot, int x) {
        if(x == 1)
        {
            return bot.rightFront.getCurrentPosition();
        } else if ( x == 2) {
            return bot.rightRear.getCurrentPosition();
        } else if (x == 3) {
            return bot.leftRear.getCurrentPosition();
        } else if (x == 4) {
            return bot.leftFront.getCurrentPosition();
        } else {
            return 0;
        }
    }

    void setEncoderTargetPosition(CF_Hardware bot, int LFcount, int RFcount, int LRcount, int RRcount)
    {
       // Only want to use absolute values.  Take abs of inputs in case user sent negative value.
       bot.leftFront.setTargetPosition(Math.abs(LFcount));
       bot.rightFront.setTargetPosition(Math.abs(RFcount));
       bot.leftRear.setTargetPosition(Math.abs(LRcount));
       bot.rightRear.setTargetPosition(Math.abs(RRcount));
    }
}

