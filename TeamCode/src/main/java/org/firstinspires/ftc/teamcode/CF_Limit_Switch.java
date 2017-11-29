package org.firstinspires.ftc.teamcode;

/**
 * Created by Ryley on 11/28/17.
 */

public class CF_Limit_Switch {
    public boolean getMastLower(CF_Hardware robot) {
        return robot.limitMastLiftLower.getState();
    }
    public boolean getMastUpper(CF_Hardware robot) {
        return robot.limitClawLiftUpper.getState();
    }
    public boolean getClawLower(CF_Hardware robot) {
        return robot.limitClawLiftLower.getState();
    }
    public boolean getClawUpper(CF_Hardware robot) {
        return robot.limitClawLiftUpper.getState();
    }
}
