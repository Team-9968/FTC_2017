package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

/**
 * Created by Ryley on 3/14/18.
 */
@TeleOp(name="CF_Encoder_Prog_Test", group = "test")
public class CF_Encoder_Prog_Test extends OpMode{
    Prog_Hardware robot = new Prog_Hardware();

    public void init() {
        robot.init(hardwareMap);
    }

    public void loop() {
        telemetry.addData("pos", robot.test.getCurrentPosition());
        telemetry.update();
    }
}
