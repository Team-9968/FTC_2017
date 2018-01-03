//package org.firstinspires.ftc.teamcode;
//
//import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
//import com.qualcomm.robotcore.eventloop.opmode.Disabled;
//import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
//import com.qualcomm.robotcore.eventloop.opmode.OpMode;
//
//import java.util.concurrent.TimeUnit;
//
///**
// * Created by dawso on 11/18/2017.
// */
//
//
//@Autonomous(name = "Auto Test", group = "Sensor")
//@Disabled
//public class CF_Test extends LinearOpMode
//{
//   CF_Hardware robot = new CF_Hardware();
//   CF_Color_Sensor sensor = new CF_Color_Sensor();
//   CF_Autonomous_Motor_Library auto = new CF_Autonomous_Motor_Library();
//
//   private enum tests
//   {
//      BACKUP, DRIVEFORWARD
//   }
//
//   public void runOpMode() throws InterruptedException
//   {
//      tests Test = tests.BACKUP;
//
//      while (opModeIsActive())
//      {
//         switch (Test)
//         {
//            case BACKUP:
//               auto.driveIMU(this, robot, 0.15, 350);
//               TimeUnit.MILLISECONDS.sleep(500);
//               Test = tests.BACKUP;
//               break;
//
//            case DRIVEFORWARD:
//               auto.driveIMU(this, robot, -0.15, 350);
//               TimeUnit.MILLISECONDS.sleep(200);
//               break;
//         }
//      }
//   }
//}
