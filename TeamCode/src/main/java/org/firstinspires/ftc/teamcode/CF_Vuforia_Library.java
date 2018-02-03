package org.firstinspires.ftc.teamcode;

import android.graphics.Bitmap;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.vuforia.Frame;
import com.vuforia.Image;
import com.vuforia.PIXEL_FORMAT;
import com.vuforia.Vuforia;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;
import org.opencv.android.Utils;
import org.opencv.core.CvType;
import org.opencv.core.Mat;

/**
 * Created by Ryley on 1/7/18.
 */

public class CF_Vuforia_Library {
    VuforiaLocalizer v;
    VuforiaTrackable template;
    VuforiaTrackables relicTrackables;
    VuforiaLocalizer.CloseableFrame frame;
    Image rgb;
    Mat pic;
    public void init(OpMode m) {
       int cameraMonitorViewId = m.hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", m.hardwareMap.appContext.getPackageName());
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);

        parameters.vuforiaLicenseKey = "AU2Vspr/////AAAAGSWZlF6AQEHFh9mbNlt5KlFGl/PX8qeeKea7jh5Xk8Ei573/nsoAjsJu9Cbi2MlRCuEIkZHQJoDGAxXmNgioA+0+DbRC6mG+1QbBu8ACMw0pBk6x3h+wvvqDeyZmjV0Fdji5Bk2bV3AaZ0AanljM2nuosjfFYOeUsoFqjE0+MQfJCOoG2ED2hxhJM88dhMaAH45kQqJ99Pn9c/F8whHUkRLeh71wW3O8qGdHEieX7WQO86VfVadHTrg0Ut8ALwiU/qVqB9pJPn+oVe9rYCixcJztb7XOp4T4Mo0IPUwVtkTUZtZTW1mAOPdbbWx3RX1OohA6q6BBU7ozDdQ1W33/L/mdETevYMf7rKPrb82Zbw8r";

        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;
        this.v = ClassFactory.createVuforiaLocalizer(parameters);

        Vuforia.setFrameFormat(PIXEL_FORMAT.RGB565, true);

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

    public Mat getFrame() {
        if(pic != null) {
            pic.release();
        }
        System.out.println("FIRST");

        try {
            v.setFrameQueueCapacity(2);
        frame = v.getFrameQueue().take();
        } catch (InterruptedException e) {
            System.out.println("INterruped Exception");
        }

        System.out.println("SECOND");

        long num = frame.getNumImages();
        if(frame != null) {
            rgb = frame.getImage(0);
        }
        System.out.println("ONE/////////////");

        Bitmap bm = Bitmap.createBitmap(rgb.getWidth(), rgb.getHeight(), Bitmap.Config.RGB_565);
        bm.copyPixelsFromBuffer(rgb.getPixels());
        System.out.println("TWO////////////");

        pic = new Mat(bm.getWidth(), bm.getHeight(), CvType.CV_8UC4);
        Utils.bitmapToMat(bm, pic);
        frame.close();
        return pic;

    }

    public Bitmap getMap() {
        if(pic != null) {
            pic.release();
        }
        System.out.println("FIRST");

        try {
            v.setFrameQueueCapacity(2);
            frame = v.getFrameQueue().take();
        } catch (InterruptedException e) {
            System.out.println("INterruped Exception");
        }

        System.out.println("SECOND");

        long num = frame.getNumImages();
        if(frame != null) {
            rgb = frame.getImage(0);
        }
        System.out.println("ONE/////////////");

        Bitmap bm = Bitmap.createBitmap(rgb.getWidth(), rgb.getHeight(), Bitmap.Config.RGB_565);
        bm.copyPixelsFromBuffer(rgb.getPixels());
        return bm;
    }

    public RelicRecoveryVuMark getMark() {
        return RelicRecoveryVuMark.from(template);
    }


}
