package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

/**
 * Created by Ryley on 1/13/18.
 */
@Autonomous(name="CF_Test_Encoder_Method", group="test")
public class CF_Test_Encoder_Method extends OpMode{
    CF_Hardware robot = new CF_Hardware();
    CF_Autonomous_Motor_Library auto = new CF_Autonomous_Motor_Library();
    int x = 1;
    @Override
    public void init() {
        robot.init(hardwareMap);
    }

    @Override
    public void loop() {
        if(x ==1 ) {
            auto.EncoderIMUDrive(this, robot, CF_Autonomous_Motor_Library.mode.ROTATE, 0.5f, 90);
            x = 2;
        }
    }
}
