package org.firstinspires.ftc.teamcode;

import android.graphics.Bitmap;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.teamcode.Enums.CF_TypeEnum;
import org.opencv.core.Mat;
import java.util.concurrent.TimeUnit;

/**
 * Created by dawson on 1/8/2018.
 */

//Autonomous mode for starting on the red team, balancing stone nearest the cryptobox in between the balancing stones


//Note: This program is only to be used when our team is on the red alliance, however,
   //it may easily be adapted for blue by switching the motor powers from positive values
   //to negative ones.


@Autonomous(name = "Red Aim Vuforia", group = "Sensor")
//@Disabled
public class CF_Red_Vuforia extends OpMode
{
    //Allows this file to access pieces of hardware created in other files.
    CF_Hardware robot = new CF_Hardware();
    static ElapsedTime runTime = new ElapsedTime();
    CF_Autonomous_Motor_Library auto = new CF_Autonomous_Motor_Library();
    CF_Color_Sensor sensor = new CF_Color_Sensor();
    CF_OpenCV_Library cam = new CF_OpenCV_Library();
    CF_Vuforia_Library vuforia = new CF_Vuforia_Library();
    //CF_OpenCV_Library.ballColor cam_color = null;

    boolean ArmCenter;

    CF_OpenCV_Library.ballColor col;
    RelicRecoveryVuMark pic;

    int counts = 0;

    //A "checklist" of things this program must do IN ORDER for it to work
    private enum checks
    {
        GRABBLOCK, MOVEMAST, SENSEPICTURE, JEWELHITTER, PASTBALANCE, RELEASEBLOCK, PARK, END
    }

    //Sets current stage of the "List"
    checks Check = checks.GRABBLOCK;

    //Ensures that we do not go over thirty seconds of runtime. This endtime variable is
    //a backup method in case the coach forgets to turn on the timer built into the robot app.
    int endTime = 29;

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
                runTime.reset();
                robot.clamp.setPosition(0.81);
                robot.lowerClamp.setPosition(0.3);
                checkTime();
                Check = checks.JEWELHITTER;
                Mat x = vuforia.getFrame();
                telemetry.addData("Found picture", "Found");
                telemetry.update();
                col = cam.getColor(this, x);

                Bitmap y = vuforia.getMap();
                cam.save(this, y);
                break;

            //This method runs the color sensors and determines which jewel is which color
           //and hits the correct jewel
            case JEWELHITTER:
                telemetry.addData("Case Jewelpusher", "");

                robot.armDown(0.14);

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

                if (classification == CF_TypeEnum.LEFTISBLUE) //&& cam_color == CF_OpenCV_Library.ballColor.BLUE) ||
                //(classification == CF_TypeEnum.RIGHTISBLUE && cam_color == CF_OpenCV_Library.ballColor.UNKNOWN) ||
                //(classification == CF_TypeEnum.UNKNOWN && cam_color == CF_OpenCV_Library.ballColor.BLUE))

                {
                    telemetry.addData("Right is"," blue");
                    robot.jewelHitter.setPosition(0.7);

                    try
                    {
                        TimeUnit.MILLISECONDS.sleep(500);
                    } catch(InterruptedException e) {}

                    ArmCenter = true;
                    checkTime();
                }

                else if ((classification == CF_TypeEnum.LEFTISRED)) //&& cam_color == CF_OpenCV_Library.ballColor.RED)// ||
                //(classification == CF_TypeEnum.RIGHTISRED && cam_color == CF_OpenCV_Library.ballColor.UNKNOWN) ||
                //(classification == CF_TypeEnum.UNKNOWN && cam_color == CF_OpenCV_Library.ballColor.RED))

                {
                    telemetry.addData("Right is"," red");
                    robot.jewelHitter.setPosition(0.0);

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

                    if(col == CF_OpenCV_Library.ballColor.RIGHTISBLUE) {
                        telemetry.addData("Right is"," blue - Camera");
                        robot.jewelHitter.setPosition(0.0);

                        try
                        {
                            TimeUnit.MILLISECONDS.sleep(500);
                        } catch(InterruptedException e) {}

                        ArmCenter = true;
                        checkTime();
                    }
                    else if(col == CF_OpenCV_Library.ballColor.RIGHTISRED) {
                        telemetry.addData("Right is"," red - Camera");
                        robot.jewelHitter.setPosition(0.7);

                        try
                        {
                            TimeUnit.MILLISECONDS.sleep(500);
                        } catch(InterruptedException e) {}

                        ArmCenter = true;
                        checkTime();
                    }

                    checkTime();
                }

                telemetry.update();
                robot.tailLight.setPower(0.0);
                robot.armUp(0.45);
                robot.jewelHitter.setPosition(0.15);

                try
                {
                    TimeUnit.MILLISECONDS.sleep(100);
                } catch(InterruptedException e) {}

                if (ArmCenter = true)
                {
                    robot.jewelHitter.setPosition(0.333);
                }
                robot.armUp(1.0);
                checkTime();
                Check = checks.MOVEMAST;
                break;

            //After the jewel has been hit off, this state raises the glyph so
           //it does not interfere with the robot driving off of the balancing stone.
            case MOVEMAST:
                auto.clawMotorMove(robot, -1.0f, 2000);
                Check = checks.SENSEPICTURE;
                break;

            //This state incorporates Vuforia to look at the the picture attached to the wall.
           //Based off of the input from Vuforia, the robot willl drive a certain number of encoder counts
            case SENSEPICTURE:
                vuforia.activate();
                auto.EncoderIMUDrive(this, robot, CF_Autonomous_Motor_Library.mode.DRIVE, 0.2f, 100);
                try{
                    TimeUnit.MILLISECONDS.sleep(2000);
                } catch (InterruptedException e) {}
                pic = vuforia.getMark();
                try{
                    TimeUnit.MILLISECONDS.sleep(500);
                } catch (InterruptedException e) {}
                telemetry.addData("pic", pic);
                telemetry.update();
                //1875 for far
                //1500 for middle
                //1250 for near
                if(pic == RelicRecoveryVuMark.LEFT) {
                    counts = 1100;
                } else if (pic == RelicRecoveryVuMark.CENTER) {
                    counts = 800;
                } else {
                    counts = 450;
                }
                vuforia.deactivate();


                Check = checks.PASTBALANCE;
                break;

            //Turns the robot and drives forward to place the glyph in the cryptobox
            case PASTBALANCE:
                auto.EncoderIMUDrive(this, robot, CF_Autonomous_Motor_Library.mode.DRIVE, 0.2f, counts);
                //740
                try{
                    TimeUnit.MILLISECONDS.sleep(300);
                } catch (InterruptedException e) {}
                auto.rotate(this, robot, 0.5f, 470);
                try{
                    TimeUnit.MILLISECONDS.sleep(100);
                } catch (InterruptedException e) {}

                auto.EncoderIMUDrive(this, robot, CF_Autonomous_Motor_Library.mode.DRIVE, 0.2f, 300);
                Check = checks.RELEASEBLOCK;
                break;

            //This state is fairly self - explanatory. It releases the claws' hold on the glyph.
            case RELEASEBLOCK:

                auto.clawMotorMove(robot, 1.0f, 1500);
                try
                {
                    TimeUnit.MILLISECONDS.sleep(500);
                } catch(InterruptedException e) {}

                robot.clamp.setPosition(0.4);
                robot.lowerClamp.setPosition(0.6);
                checkTime();
                Check = checks.PARK;
                break;

            // Backs robot up slightly so we aren't touching the block, but are still parking
            case PARK:

                try
                {
                    TimeUnit.MILLISECONDS.sleep(500);
                } catch(InterruptedException e) {}

                auto.EncoderIMUDrive(this, robot, CF_Autonomous_Motor_Library.mode.DRIVE, -0.2f, 275);
                try{
                    TimeUnit.MILLISECONDS.sleep(200);
                } catch(InterruptedException e) {}
                auto.EncoderIMUDrive(this, robot, CF_Autonomous_Motor_Library.mode.DRIVE, 0.2f, 200);
                try{
                    TimeUnit.MILLISECONDS.sleep(100);
                } catch(InterruptedException e) {}
                auto.EncoderIMUDrive(this, robot, CF_Autonomous_Motor_Library.mode.DRIVE, -0.2f, 200);

                checkTime();
                Check = checks.END;
                break;

            //End state. Does nothing.
            case END:
                break;
        }
    }
}