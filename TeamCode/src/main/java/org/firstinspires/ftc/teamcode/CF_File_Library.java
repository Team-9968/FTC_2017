package org.firstinspires.ftc.teamcode;

import android.content.Context;

import org.firstinspires.ftc.teamcode.CF_Hardware;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOError;
import java.io.IOException;

/**
 * Created by Ryley on 3/1/18.
 */

public class CF_File_Library {

    void writeToLog(CF_Hardware robot, String message) throws IOException{
        File path = robot.hwMap.appContext.getFilesDir();
        File file = new File(path, "customLog.txt");

        FileOutputStream stream = new FileOutputStream(file);

        try {
            stream.write(message.getBytes());
        } finally {
             stream.close();
        }


    }

}
