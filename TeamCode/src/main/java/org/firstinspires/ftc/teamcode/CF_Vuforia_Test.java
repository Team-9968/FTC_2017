package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

/**
 * Created by Ryley on 1/6/18.
 */
@Autonomous(name = "Vuforia Test", group = "Test")
//@Disabled
public class CF_Vuforia_Test extends OpMode {
    CF_Hardware robot = new CF_Hardware();
    ElapsedTime runTime = new ElapsedTime();
    CF_Autonomous_Motor_Library auto = new CF_Autonomous_Motor_Library();
    CF_Vuforia_Library vuforia = new CF_Vuforia_Library();
    int endTime = 29;
//    VuforiaLocalizer vuforia;
//    VuforiaTrackable relicTemplate;


    private void checkTime() {
        if(runTime.seconds() >= endTime)
        {
            requestOpModeStop();
        }
    }


    @Override
    public void init() {
        robot.init(hardwareMap);

//        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
//        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);
//
//        parameters.vuforiaLicenseKey = "AU2Vspr/////AAAAGSWZlF6AQEHFh9mbNlt5KlFGl/PX8qeeKea7jh5Xk8Ei573/nsoAjsJu9Cbi2MlRCuEIkZHQJoDGAxXmNgioA+0+DbRC6mG+1QbBu8ACMw0pBk6x3h+wvvqDeyZmjV0Fdji5Bk2bV3AaZ0AanljM2nuosjfFYOeUsoFqjE0+MQfJCOoG2ED2hxhJM88dhMaAH45kQqJ99Pn9c/F8whHUkRLeh71wW3O8qGdHEieX7WQO86VfVadHTrg0Ut8ALwiU/qVqB9pJPn+oVe9rYCixcJztb7XOp4T4Mo0IPUwVtkTUZtZTW1mAOPdbbWx3RX1OohA6q6BBU7ozDdQ1W33/L/mdETevYMf7rKPrb82Zbw8r";
//
//        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;
//        this.vuforia = ClassFactory.createVuforiaLocalizer(parameters);
//
//        VuforiaTrackables relicTrackables = this.vuforia.loadTrackablesFromAsset("RelicVuMark");
//        relicTemplate = relicTrackables.get(0);
//        relicTemplate.setName("relicVuMarkTemplate");
//
//        relicTrackables.activate();

        vuforia.init(this);
        vuforia.activate();



    }

    @Override
    public void loop()
    {
        runTime.reset();
        checkTime();

//        RelicRecoveryVuMark vuMark = RelicRecoveryVuMark.from(relicTemplate);

        telemetry.addData("mark", vuforia.getMark());
        telemetry.update();

    }
}
