package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.navigation.Position;
import org.firstinspires.ftc.robotcore.external.navigation.Velocity;

/**
 * Created by Ryley on 10/22/17.
 */
@Autonomous(name="Auto_Test", group="test")
public class CF_IMU_Auto_Test extends LinearOpMode {
    CF_Hardware robot = new CF_Hardware();
    CF_IMU_Library imuLib = new CF_IMU_Library();
    CF_Autonomous_Motor_Library auto = new CF_Autonomous_Motor_Library();

    @Override
    public void runOpMode() {
        robot.init(hardwareMap);

        waitForStart();

        robot.imu.startAccelerationIntegration(new Position(), new Velocity(), 50);
        auto.rotateIMU(this, robot, 0.1, -90);

//        while(opModeIsActive()) {
//            imuLib.updateNumbers(robot);
//            telemetry.clearAll();
//            telemetry.addData("y: ", imuLib.getyAccel());
//            telemetry.addData("encoder", robot.rightFront.getCurrentPosition());
//            telemetry.update();
//
//        }

    }
}
