package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;


/**
 * Created by Ryley on 9/15/17.
 */


public class CF_Hardware
{
   public DcMotor rightFront = null;
   public DcMotor rightRear = null;
   public DcMotor leftFront = null;
   public DcMotor leftRear = null;

    public DcMotor mastMotor = null;
    public DcMotor clawMotor = null;

   ColorSensor adafruitRGB;

    BNO055IMU imu = null;

    public Servo clamp = null;

    HardwareMap hwMap = null;

    public CF_Hardware() {}

    public void init(HardwareMap ahwMap) {
        hwMap = ahwMap;

        rightFront = hwMap.get(DcMotor.class, "motorOne");
        rightFront.setDirection(DcMotorSimple.Direction.FORWARD);  //Check motor directions

        rightRear = hwMap.get(DcMotor.class, "motorTwo");
        rightRear.setDirection(DcMotorSimple.Direction.FORWARD);

        leftRear = hwMap.get(DcMotor.class, "motorThree");
        leftRear.setDirection(DcMotorSimple.Direction.REVERSE);

        leftFront = hwMap.get(DcMotor.class, "motorFour");
        leftFront.setDirection(DcMotorSimple.Direction.REVERSE);

        mastMotor = hwMap.get(DcMotor.class, "mastMotor");
        mastMotor.setDirection(DcMotorSimple.Direction.FORWARD);

        clawMotor = hwMap.get(DcMotor.class, "clawMotor");
        clawMotor.setDirection(DcMotorSimple.Direction.FORWARD);

        clamp = hwMap.get(Servo.class, "clamp");

       adafruitRGB = hwMap.get(ColorSensor.class, "adafruitRGB");

        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.calibrationDataFile = "BNO055IMUCalibration.json";
        parameters.loggingEnabled = true;
        parameters.loggingTag = "IMU";
        parameters.accelerationIntegrationAlgorithm = new JustLoggingAccelerationIntegrator();

        imu = hwMap.get(BNO055IMU.class, "imu");
        imu.initialize(parameters);

    }
}