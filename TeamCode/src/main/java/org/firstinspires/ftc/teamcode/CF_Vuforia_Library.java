package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

/**
 * Created by Ryley on 1/7/18.
 */

public class CF_Vuforia_Library {
    VuforiaLocalizer v;
    VuforiaTrackable template;
    VuforiaTrackables relicTrackables;
    public void init(OpMode m) {
        int cameraMonitorViewId = m.hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", m.hardwareMap.appContext.getPackageName());
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);

        parameters.vuforiaLicenseKey = "AU2Vspr/////AAAAGSWZlF6AQEHFh9mbNlt5KlFGl/PX8qeeKea7jh5Xk8Ei573/nsoAjsJu9Cbi2MlRCuEIkZHQJoDGAxXmNgioA+0+DbRC6mG+1QbBu8ACMw0pBk6x3h+wvvqDeyZmjV0Fdji5Bk2bV3AaZ0AanljM2nuosjfFYOeUsoFqjE0+MQfJCOoG2ED2hxhJM88dhMaAH45kQqJ99Pn9c/F8whHUkRLeh71wW3O8qGdHEieX7WQO86VfVadHTrg0Ut8ALwiU/qVqB9pJPn+oVe9rYCixcJztb7XOp4T4Mo0IPUwVtkTUZtZTW1mAOPdbbWx3RX1OohA6q6BBU7ozDdQ1W33/L/mdETevYMf7rKPrb82Zbw8r";

        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;
        this.v = ClassFactory.createVuforiaLocalizer(parameters);

        relicTrackables = this.v.loadTrackablesFromAsset("RelicVuMark");
        template = relicTrackables.get(0);
        template.setName("relicVuMarkTemplate");
    }

    public void activate() {
        relicTrackables.activate();
    }

    public void deactivate() {
        relicTrackables.deactivate();
    }

    public RelicRecoveryVuMark getMark() {
        return RelicRecoveryVuMark.from(template);
    }
}
