package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.navigation.Position;
import org.firstinspires.ftc.robotcore.external.navigation.Velocity;
import org.opencv.android.CameraBridgeViewBase;

/**
 * Created by Ryley on 3/15/18.
 */

@Autonomous(name="CF_Test_Gyro", group = "test")
public class CF_Test_Gyro extends OpMode{
    CF_Hardware robot = new CF_Hardware();
    CF_Autonomous_Motor_Library auto = new CF_Autonomous_Motor_Library();
    CF_Master_Motor_Library motors = new CF_Master_Motor_Library();
    double offset;
    public void init() {
        robot.init(hardwareMap);
        robot.imu.startAccelerationIntegration(new Position(), new Velocity(), 10);
        offset = auto.resetEncoders(robot);
    }

    public void loop() {
        if(auto.encoderStrafeStateGyro(robot, -0.6f, 2000, offset)) {
            motors.setMechPowers(robot,1,0,0,0,0,0);
            requestOpModeStop();
        }
        telemetry.addData("x", robot.imu.getAcceleration().xAccel);
        telemetry.addData("y", robot.imu.getAcceleration().yAccel);
        telemetry.addData("z", robot.imu.getAcceleration().zAccel);
        telemetry.update();
    }
}
