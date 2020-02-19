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
import frc.robot.LimelightUtility;
import frc.robot.Robot;

/**
 *
 */
public class teleopAutoShootCMD extends Command {
    double hoodEncoderUnits = 0;
    double area = 0;
    double rpms = 0;
    Integer numberOfBalls = 0;

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_DECLARATIONS
 
    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_DECLARATIONS

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTOR
    public teleopAutoShootCMD() {

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTOR
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_SETTING

        // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_SETTING
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=REQUIRES
        requires(Robot.ballShooter);

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=REQUIRES
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        LimelightUtility.RefreshTrackingData();
        // Lookup optimal RPMS &  Hood encoder units based on area (if target seen)
        if (LimelightUtility.ValidTargetFound()) {
            area = LimelightUtility.TargetAreaPercentage * 100; 
        } else {
            System.out.println("No target");
            area = 129;
        }  
        rpms = BallShooterConstants.targetPercent2ShooterParms.floorEntry((int)area).getValue()[0];
        hoodEncoderUnits = BallShooterConstants.targetPercent2ShooterParms.floorEntry((int)area).getValue()[1];
        System.out.println("RPMs:" +  rpms);
        System.out.println("HE:" + hoodEncoderUnits);
        numberOfBalls = Robot.ballIndexer.ballCount(); 
        Robot.ballShooter.fireMotor(rpms,hoodEncoderUnits);
        setTimeout(10);
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        if (Robot.ballShooter.shooterSpunUp(rpms, hoodEncoderUnits)) {
            Robot.ballIndexer.startIndexMotor(BallIndexerConstants.indexMotorSpeed);
        } else {
            Robot.ballIndexer.stopIndexMotor();
       }
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
       return isTimedOut();
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
        Robot.ballShooter.stopBelt();
        Robot.ballShooter.stopShooter();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
        end();
    }
}
