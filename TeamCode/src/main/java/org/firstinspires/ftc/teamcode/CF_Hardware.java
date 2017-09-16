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
    HardwareMap hwMap = null;

    public CF_Hardware() {}

    public void init(HardwareMap ahwMap) {
        hwMap = ahwMap;

        motorOne = hwMap.get(DcMotor.class, "motorOne");
        motorOne.setDirection(DcMotorSimple.Direction.FORWARD);
    }
}
