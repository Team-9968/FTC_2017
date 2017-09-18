package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;
/**
 * Created by Ryley on 9/15/17.
 */


public class CF_Hardware {
    public DcMotor motorOne = null;
    public DcMotor motorTwo = null;
    public DcMotor motorThree = null;
    public DcMotor motorFour = null;
    HardwareMap hwMap = null;

    public CF_Hardware() {}

    public void init(HardwareMap ahwMap) {
        hwMap = ahwMap;

        motorOne = hwMap.get(DcMotor.class, "motorOne");
        motorOne.setDirection(DcMotorSimple.Direction.FORWARD);

        motorTwo = hwMap.get(DcMotor.class, "motorTwo");
        motorTwo.setDirection(DcMotorSimple.Direction.FORWARD);

        motorThree = hwMap.get(DcMotor.class, "motorThree");
        motorThree.setDirection(DcMotorSimple.Direction.FORWARD);

        motorFour = hwMap.get(DcMotor.class, "motorFour");
        motorFour.setDirection(DcMotorSimple.Direction.FORWARD);




    }
}
