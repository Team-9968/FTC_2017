package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Created by Ryley on 10/19/17.
 */

public class CF_Accessory_Motor_Library {

    void setPowerToPower(DcMotor motor, double power, double exponent) {
        motor.setPower(Math.pow(power, exponent));
    }

}
