// RobotBuilder Version: 2.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Constants;
import frc.robot.Robot;

/**
 *
 */
public class driveStraightFeet extends Command {

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_DECLARATIONS
    private double m_distanceInFeet;

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_DECLARATIONS

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTOR
    public driveStraightFeet(double distanceInFeet) {

        // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTOR
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_SETTING
        m_distanceInFeet = distanceInFeet;

        // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_SETTING
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=REQUIRES

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=REQUIRES
    }

    boolean ranOnce = false;

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        setTimeout(5);
        Robot.driveTrain.motorConfigFalcon();
        Robot.driveTrain.zeroSensors();
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        if (!ranOnce) {
            ranOnce = true;
            double m_encoderUnits = Constants.encodeUnitsToFeet * m_distanceInFeet;
            Robot.driveTrain.driveToEncoderUnits(m_encoderUnits);
        }
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        double m_encoderUnits = Constants.encodeUnitsToFeet * m_distanceInFeet;
        return (Robot.driveTrain.atTarget(m_encoderUnits) || isTimedOut());
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
        Robot.driveTrain.stop();
        Robot.driveTrain.motorConfigFalcon();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
        end();
    }
}
