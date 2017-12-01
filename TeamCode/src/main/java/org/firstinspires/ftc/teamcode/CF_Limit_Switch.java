package org.firstinspires.ftc.teamcode;

/**
 * Created by Ryley on 11/28/17.
 */

//This is a class to run the limit switches.  Any limit switch code needs to go here
public class CF_Limit_Switch {
    // These are all just accessor methods for the limit switches
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
