package org.firstinspires.ftc.teamcode;

//Pulls in variables and methods written in other files so we can use them in this one.
import android.graphics.Bitmap;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.Position;
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.robotcore.external.navigation.Velocity;
import org.firstinspires.ftc.teamcode.Enums.CF_TypeEnum;
import org.opencv.core.Mat;
import java.util.concurrent.TimeUnit;

/**
 * Created by dawson on 1/8/2018.
 */

//Autonomous mode for starting on the red team, balancing stone nearest the cryptobox that is in between the balancing stones

//Note: This program is only to be used when our team is on the red alliance, however,
//it may easily be adapted for blue by switching the motor powers from positive values
//to negative ones.


@Autonomous(name = "Blue Auto Turn", group = "Sensor")
//@Disabled
public class CF_Blue_Turn extends OpMode
{
    //Allows this file to access pieces of hardware created in other files.
    CF_Hardware robot = new CF_Hardware();
    static ElapsedTime runTime = new ElapsedTime();
    CF_Autonomous_Motor_Library auto = new CF_Autonomous_Motor_Library();
    CF_Color_Sensor sensor = new CF_Color_Sensor();
    CF_OpenCV_Library cam = new CF_OpenCV_Library();
    CF_Vuforia_Library vuforia = new CF_Vuforia_Library();
    CF_Master_Motor_Library motors = new CF_Master_Motor_Library();
    //CF_OpenCV_Library.ballColor cam_color = null;

    boolean ArmCenter;

    CF_OpenCV_Library.ballColor col;
    RelicRecoveryVuMark pic;

    int strafe = 0;
    int rot = 0;
    int forwards = 0;
    int nudge = 0;
    double jewelHitterPos = 0.333;
    double jewelHitterIncrememt = 0;
    double offset;

    double multiplier = 0.85;

    //A list of all of the steps in this program
    private enum checks
    {
        GRABBLOCK, MOVEMAST, SENSEPICTURE, JEWELHITTER, PASTBALANCE, RELEASEBLOCK, PARK, END
    }

    private enum jewelHitterState
    {
        ARMDOWN, CHECKCOL, HITBALL, ARMUP, CENTERARM, OTHERSTUFF, END
    }

    private enum picSenseState
    {
        INITVUFORIA, DRIVEENCODERS, SENSEPICTURE, END
    }

    private enum pastBalanceState
    {
        RESETENCODERS, ROTATE, RESETENCODERS2, STRAFE, RESETENCODERS3, ROTATE2, RESETENCODERS4, DRIVE, END
    }

    private enum releaseBlockState
    {
        RELEASEBLOCK, RESETENCODERS, DRIVE, END
    }

    private enum parkState
    {
        RESETENCODERS1, DRIVE1, RESETENCODERS2, DRIVE2, RESETENCODERS3, DRIVE3, END
    }

    //Sets current stages of the "List"
    checks Check = checks.GRABBLOCK;
    jewelHitterState jewelHitter = jewelHitterState.ARMDOWN;
    picSenseState picSense = picSenseState.INITVUFORIA;
    pastBalanceState pastBalance = pastBalanceState.RESETENCODERS;
    releaseBlockState releaseBlock = releaseBlockState.RELEASEBLOCK;
    parkState park = parkState.RESETENCODERS1;

    //Ensures that we do not go over thirty seconds of runtime. This endtime variable is
    //a backup method in case the coach forgets to turn on the timer built into the robot app.
    int endTime = 29;
    double servoIncrement = 0;

    RelicRecoveryVuMark markIn = RelicRecoveryVuMark.UNKNOWN;

    private void checkTime()
    {
        // Kills the robot if time is over the endTime
        if(getRuntime() >= endTime)
        {
            requestOpModeStop();
        }
    }

    @Override
    public void init()
    {
        msStuckDetectLoop = 15000;
        robot.init(hardwareMap);
        robot.leftFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        robot.rightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        robot.leftRear.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        robot.rightRear.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        //robot.imu.startAccelerationIntegration(new Position(), new Velocity(), 100);
        vuforia.init(this);
    }

    @Override
    public void loop()
    {
        switch (Check)
        {
            //This method grabs the glyph and uses the phone's camera to take a picture
            //to help determine ball color
            case GRABBLOCK:
                resetStartTime();
                vuforia.activate();
                runTime.reset();
                robot.upperRightClaw.setPosition(0.25);
                robot.upperLeftClaw.setPosition(0.7);
                robot.lowerRightClaw.setPosition(0.95);
                robot.lowerLeftClaw.setPosition(0.15);
                checkTime();
                Check = checks.JEWELHITTER;
                Mat x = vuforia.getFrame();
                telemetry.addData("Found picture", "Found");
                col = cam.getColor(this, x);

                Bitmap y = vuforia.getMap();
                cam.save(this, y);
                servoIncrement = robot.colorArm.getPosition();
                break;

            //This method runs the color sensors and determines which jewel is which color
            //and uses that info to hit the correct jewel
            case JEWELHITTER:
                telemetry.addData("Case Jewelpusher", "");
                markIn = vuforia.getMark();
                if(!(markIn == RelicRecoveryVuMark.UNKNOWN)) {
                    pic = markIn;
                }
                switch (jewelHitter) {
                    case ARMDOWN:
                        servoIncrement -= 0.0025;
                        robot.colorArm.setPosition(servoIncrement);
                        if(robot.isArmDown(0.08f)) {
                            jewelHitter = jewelHitterState.CHECKCOL;
                        }
                        break;
                    case CHECKCOL:
                        try
                        {
                            TimeUnit.MILLISECONDS.sleep(300);
                        } catch(InterruptedException e) {}

                        robot.tailLight.setPower(1);

                        try
                        {
                            TimeUnit.MILLISECONDS.sleep(700);
                        } catch(InterruptedException e) {}

                        sensor.setType(robot);
                        CF_TypeEnum classification = sensor.setType(robot);

                        if (classification == CF_TypeEnum.LEFTISRED)

                        {
                            telemetry.addData("Right is"," blue");
                            jewelHitterPos = 0.7;

                            try
                            {
                                TimeUnit.MILLISECONDS.sleep(500);
                            } catch(InterruptedException e) {}

                            ArmCenter = true;
                            checkTime();
                        }

                        else if ((classification == CF_TypeEnum.LEFTISBLUE))

                        {
                            telemetry.addData("Right is"," red");
                            jewelHitterPos = 0.0;

                            try
                            {
                                TimeUnit.MILLISECONDS.sleep(500);
                            } catch(InterruptedException e) {}

                            ArmCenter = true;
                            checkTime();
                        }

                        //If the color sensors failed to determine the jewels' colors, this
                        //else statement relies on the camera as a backup.
                        else
                        {
                            telemetry.addData("Ball is", " unknown");

                            //if the ball on the right is blue, the arm will move to knock off that ball.
                            if(col == CF_OpenCV_Library.ballColor.RIGHTISRED) {
                                telemetry.addData("Right is"," blue - Camera");
                                jewelHitterPos = 0.0;

                                try
                                {
                                    TimeUnit.MILLISECONDS.sleep(500);
                                } catch(InterruptedException e) {}

                                ArmCenter = true;
                                checkTime();
                            }

                            //if the ball on the right is red, the arm will hit the blue ball.
                            else if(col == CF_OpenCV_Library.ballColor.RIGHTISBLUE) {
                                telemetry.addData("Right is"," red - Camera");
                                jewelHitterPos = 0.7;

                                try
                                {
                                    TimeUnit.MILLISECONDS.sleep(500);
                                } catch(InterruptedException e) {}

                                ArmCenter = true;
                                checkTime();
                            }

                            checkTime();
                        }

                        robot.tailLight.setPower(0.0);
                        servoIncrement = robot.colorArm.getPosition();
                        jewelHitterIncrememt = robot.jewelHitter.getPosition();
                        jewelHitter = jewelHitterState.HITBALL;
                        break;
                    case HITBALL:
                        if(jewelHitterPos < 0.33) {
                            jewelHitterIncrememt -= 0.001;
                        } else if(jewelHitterPos > 0.33) {
                            jewelHitterIncrememt += 0.001;
                        }
                        robot.jewelHitter.setPosition(jewelHitterIncrememt);
                        if(robot.isJewelHitterAtPos((float)jewelHitterPos)) {
                            jewelHitter = jewelHitterState.ARMUP;
                        }
                        servoIncrement = robot.colorArm.getPosition();
                        jewelHitterIncrememt = robot.jewelHitter.getPosition();
                        break;

                    case ARMUP:
                        if(robot.isArmUp(0.99f)) {
                            servoIncrement += 0;
                        } else {
                            servoIncrement += 0.0025;
                        }
                        if(robot.isJewelHitterAtPos(0.333f)) {
                            jewelHitterIncrememt += 0;
                        } else {
                            if(jewelHitterPos < 0.33) {
                                jewelHitterIncrememt += 0.015;
                            } else if(jewelHitterPos > 0.33) {
                                jewelHitterIncrememt -= 0.015;
                            }
                        }

                        robot.colorArm.setPosition(servoIncrement);
                        robot.jewelHitter.setPosition(jewelHitterIncrememt);

                        if(robot.isArmUp(0.99f) && robot.isJewelHitterAtPos(0.333f)) {
                            jewelHitter = jewelHitterState.OTHERSTUFF;
                        }
                        break;
                    case OTHERSTUFF:

                        try
                        {
                            TimeUnit.MILLISECONDS.sleep(100);
                        } catch(InterruptedException e) {}

                        if (ArmCenter = true)
                        {
                            robot.jewelHitter.setPosition(0.333);
                        }
                        jewelHitter = jewelHitterState.END;
                        servoIncrement = robot.colorArm.getPosition();
                        break;
                    case END:
                        Check = checks.MOVEMAST;
                        break;




                }
                break;

            //After the jewel has been hit off, this state raises the glyph so
            //it does not interfere with the robot driving off of the balancing stone.
            case MOVEMAST:
                telemetry.addData("Move Mast", "");
                markIn = vuforia.getMark();
                if(!(markIn == RelicRecoveryVuMark.UNKNOWN)) {
                    pic = markIn;
                }
                robot.clawMotor.setPower(-1.0f);
                try {TimeUnit.MILLISECONDS.sleep(400);} catch (InterruptedException e) {}
                robot.clawMotor.setPower(0.0f);
                Check = checks.SENSEPICTURE;
                break;

            //This state incorporates Vuforia to look at the the picture attached to the wall.
            //Based off of the input from Vuforia, the robot will drive a certain number of encoder counts
            case SENSEPICTURE:
                telemetry.addData("Sense Picture", picSense);
                switch (picSense) {
                    case INITVUFORIA:
                        offset = auto.resetEncoders(robot);
                        picSense = picSenseState.DRIVEENCODERS;
                        break;
                    case DRIVEENCODERS:
                        markIn = vuforia.getMark();
                        if(!(markIn == RelicRecoveryVuMark.UNKNOWN)) {
                            pic = markIn;
                        }

                        if(auto.encoderDriveState(robot, -0.2f, (int)(multiplier * 910), offset)) {
                            motors.setMechPowers(robot, 1,0,0,0,0,0);
                            picSense = picSenseState.SENSEPICTURE;
                        }
                        break;
                    case SENSEPICTURE:
                        try{
                            TimeUnit.MILLISECONDS.sleep(2000);
                        } catch (InterruptedException e) {}
                        markIn = vuforia.getMark();
                        if(!(markIn == RelicRecoveryVuMark.UNKNOWN)) {
                            pic = vuforia.getMark();
                        }
                        telemetry.addData("pic", pic);
                        //telemetry.update();
                        //1875 for far
                        //1500 for middle
                        //1250 for near
                        if(pic == RelicRecoveryVuMark.LEFT) {
                            strafe = (int)(multiplier * 0);
                            // 1700
                            rot = (int)(multiplier * 1800);
                            forwards = (int)(multiplier * 125);
                            nudge = (int)(multiplier * 200);
                        } else if (pic == RelicRecoveryVuMark.CENTER) {
                            strafe = (int)(multiplier * 750);
                            rot = (int)(multiplier * 1365);
                            forwards = (int)(multiplier * 100);
                            nudge = (int)(multiplier * 200);
                        } else {
                            strafe = (int)(multiplier * 1120);
                            rot = (int)(multiplier * 1350);
                            forwards = (int)(multiplier * 150);
                            nudge = (int)(multiplier * 200);
                        }
                        vuforia.deactivate();
                        picSense = picSenseState.END;
                        break;
                    case END:
                        Check = checks.PASTBALANCE;
                        motors.setMechPowers(robot, 1,0,0,0,0,0);
                        try{
                            TimeUnit.MILLISECONDS.sleep(100);
                        } catch (InterruptedException e) {};
                }
                break;

            //Turns the robot and drives forward to place the glyph in the cryptobox
            case PASTBALANCE:
                switch(pastBalance) {
                    case RESETENCODERS:
                        offset = auto.resetEncoders(robot);
                        pastBalance = pastBalanceState.STRAFE;
                        break;
                    case ROTATE:

                        if(auto.encoderRotateState(robot, -0.5f, (int)(multiplier * 1200), offset)) {
                            pastBalance = pastBalanceState.RESETENCODERS2;
                        }
                        break;
                    case RESETENCODERS2:
                        offset = auto.resetEncoders(robot);
                        pastBalance = pastBalanceState.STRAFE;
                        break;
                    case STRAFE:
                        if(auto.encoderStrafeState(robot, -0.6f, strafe, offset)) {
                            motors.setMechPowers(robot, 1,0,0,0,0,0);
                            pastBalance = pastBalanceState.RESETENCODERS3;
                        }
                        break;
                    case RESETENCODERS3:
                        offset = auto.resetEncoders(robot);
                        pastBalance = pastBalanceState.ROTATE2;
                        break;
                    case ROTATE2:
                        if(auto.encoderRotateState(robot, 0.4f, rot, offset)) {
                            motors.setMechPowers(robot, 1,0,0,0,0,0);
                            pastBalance = pastBalanceState.RESETENCODERS4;
                        }
                        break;
                    case RESETENCODERS4:
                        offset = auto.resetEncoders(robot);
                        pastBalance = pastBalanceState.DRIVE;
                        break;
                    case DRIVE:
                        if(auto.encoderDriveState(robot, 0.75f, forwards, offset)) {
                            motors.setMechPowers(robot, 1,0,0,0,0,0);
                            pastBalance = pastBalanceState.END;
                        }
                        break;
                    case END:
                        motors.setMechPowers(robot, 1,0,0,0,0,0);
                        Check = checks.RELEASEBLOCK;
                        break;
                }
                break;

            //This state is fairly self - explanatory. It releases the claws' hold on the glyph.
            case RELEASEBLOCK:

                switch(releaseBlock) {
                    case RELEASEBLOCK:
                        robot.clawMotor.setPower(1.0f);
                        try {TimeUnit.MILLISECONDS.sleep(200);} catch (InterruptedException e) {}
                        robot.clawMotor.setPower(0.0f);
                        try
                        {
                            TimeUnit.MILLISECONDS.sleep(500);
                        } catch(InterruptedException e) {}

                        robot.upperRightClaw.setPosition(0.75);
                        robot.upperLeftClaw.setPosition(0.3);
                        robot.lowerRightClaw.setPosition(0.95);
                        robot.lowerLeftClaw.setPosition(0.15);
                        releaseBlock = releaseBlockState.RESETENCODERS;
                        break;
                    case RESETENCODERS:
                        offset = auto.resetEncoders(robot);
                        releaseBlock = releaseBlockState.DRIVE;
                        break;
                    case DRIVE:
                        if(auto.encoderDriveState(robot, 0.8f, nudge, offset)) {
                            motors.setMechPowers(robot, 1,0,0,0,0,0);
                            releaseBlock = releaseBlockState.END;
                        }
                        break;
                    case END:
                        motors.setMechPowers(robot, 1,0,0,0,0,0);
                        Check = checks.PARK;
                }
                break;

            // Backs robot up slightly so we aren't touching the block, but are still parking
            case PARK:
                switch(park) {
                    case RESETENCODERS1:
                        offset = auto.resetEncoders(robot);
                        try
                        {
                            TimeUnit.MILLISECONDS.sleep(500);
                        } catch(InterruptedException e) {}
                        park = parkState.DRIVE1;
                        break;
                    case DRIVE1:
                        if(auto.encoderDriveState(robot, -0.2f, (int)(multiplier * 275), offset)){
                            motors.setMechPowers(robot, 1,0,0,0,0,0);
                            park = parkState.RESETENCODERS2;
                        }
                        break;
                    case RESETENCODERS2:
                        offset = auto.resetEncoders(robot);
                        try
                        {
                            TimeUnit.MILLISECONDS.sleep(200);
                        } catch(InterruptedException e) {}
                        park = parkState.DRIVE2;
                        break;
                    case DRIVE2:
                        if(auto.encoderDriveState(robot, 0.2f, (int)(multiplier * 200), offset)){
                            motors.setMechPowers(robot, 1,0,0,0,0,0);
                            park = parkState.RESETENCODERS3;
                        }
                        break;
                    case RESETENCODERS3:
                        offset = auto.resetEncoders(robot);
                        try
                        {
                            TimeUnit.MILLISECONDS.sleep(100);
                        } catch(InterruptedException e) {}
                        park = parkState.DRIVE3;
                        break;
                    case DRIVE3:
                        if(auto.encoderDriveState(robot, -0.2f, (int)(multiplier * 200), offset)){
                            motors.setMechPowers(robot, 1,0,0,0,0,0);
                            park = parkState.END;
                        }
                        break;
                    case END:
                        motors.setMechPowers(robot, 1,0,0,0,0,0);
                        Check = checks.END;
                }
                break;

            //End state. Does nothing.
            case END:
                requestOpModeStop();
                break;
        }
        checkTime();
        //telemetry.update();
    }
}