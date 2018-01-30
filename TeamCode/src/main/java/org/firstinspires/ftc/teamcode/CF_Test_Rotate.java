package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.robocol.TelemetryMessage;

import java.util.concurrent.TimeUnit;

/**
 * Created by Ryley on 1/24/18.
 */

@Autonomous(name="Test_Rotate", group="Test")
//@Disabled
public class CF_Test_Rotate extends OpMode {

    CF_Hardware robot = new CF_Hardware();
    CF_Autonomous_Motor_Library mot = new CF_Autonomous_Motor_Library();
    CF_IMU_Library emu = new CF_IMU_Library();
    int x = 1;

    @Override
    public void init() {
        msStuckDetectLoop = 30000;
        robot.init(hardwareMap);
    }
    public void loop() {
        if(x == 1) {
            mot.EncoderIMUDrive(this, robot, CF_Autonomous_Motor_Library.mode.STRAFE, -0.5f, 2000);
            try {
                TimeUnit.MILLISECONDS.sleep(1000);
            } catch (InterruptedException e) {}
            mot.EncoderIMUDrive(this, robot, CF_Autonomous_Motor_Library.mode.STRAFE, 0.5f, 2000);
            x = 2;
        }

//        emu.updateNumbers(robot);
//        telemetry.addData("Ang", emu.getRotation(3));
//        telemetry.update();

    }
}
