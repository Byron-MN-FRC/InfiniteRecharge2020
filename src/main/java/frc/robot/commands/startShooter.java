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
import frc.robot.BallIndexerConstants;
import frc.robot.BallShooterConstants;
import frc.robot.Robot;
import frc.robot.subsystems.BallShooter;

/**
 *
 */
public class startShooter extends Command {
    private runIndexBelt indexBeltRunner;

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_DECLARATIONS

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_DECLARATIONS

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTOR
    public startShooter() {

        // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTOR
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_SETTING

        // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_SETTING
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=REQUIRES
        requires(Robot.ballShooter);

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=REQUIRES
    }

    private void requires(BallShooter ballShooter) {
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        indexBeltRunner = new runIndexBelt();
        Robot.ballIndexer.shooterActive = true;
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        if (Robot.ballShooter.ready2Shoot(-Robot.ballShooter.getShooterRPM(), BallShooterConstants.kHoodUpEncoderMax)) {
            if (!indexBeltRunner.isRunning()) {
                System.out.println("Running belt motor");
                indexBeltRunner.start();
            }
        } else {
            if (indexBeltRunner.isRunning()) {
                System.out.println("cancelling belt motor");

                indexBeltRunner.cancel();
            }
        }
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
        if (indexBeltRunner.isRunning()) {
            indexBeltRunner.cancel();
        }
        Robot.ballIndexer.shooterActive = false;
        System.out.println("StartShooter Ended");
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
        end();
    }
}
