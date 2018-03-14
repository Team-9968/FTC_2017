package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

/**
 * Created by Ryley on 3/14/18.
 */

public class Prog_Hardware {

    public DcMotor test = null;
    HardwareMap hwMap = null;


    public void init(HardwareMap ahwMap){
        hwMap = ahwMap;
        test = hwMap.get(DcMotor.class, "test");
    }
}
