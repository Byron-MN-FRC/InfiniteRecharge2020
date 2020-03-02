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





    runIndexBelt indexBeltRunner;
    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        LimelightUtility.RefreshTrackingData();
        // Lookup optimal RPMS &  Hood encoder units based on area (if target seen)
        if (LimelightUtility.ValidTargetFound()) {
            area = LimelightUtility.TargetAreaPercentage * 100; 
        } else {
            System.out.println("No target");
            area = 100;
        }  
        rpms = BallShooterConstants.targetPercent2ShooterParms.floorEntry((int)area).getValue()[0];
        hoodEncoderUnits = BallShooterConstants.targetPercent2ShooterParms.floorEntry((int)area).getValue()[1];

        numberOfBalls = Robot.ballIndexer.ballCount(); 
        //Robot.ballShooter.prepareToShoot(rpms,hoodEncoderUnits);
        setTimeout(BallShooterConstants.teleopAutoShootCmdTimeout);
        indexBeltRunner = new runIndexBelt();
        Robot.ballIndexer.shooterActive = true;
    }



    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        if (Robot.ballShooter.ready2Shoot(rpms, hoodEncoderUnits)) {
            if (!indexBeltRunner.isRunning()) {
                System.out.println("teleopAutoShootCMD is Running belt motor");
                indexBeltRunner.start();
                
            } else if(!Robot.ballIndexer.ballPresent(1)) {
                Robot.ballAcquisition.startAcquireMotor();
            }
        } else {
            if (indexBeltRunner.isRunning()) {
                System.out.println("teleopAutoShootCMD is Cancelling belt motor");
                
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
        //Robot.ballShooter.stopShooter();
        if (indexBeltRunner.isRunning()) {
            indexBeltRunner.cancel();
        }
        Robot.ballAcquisition.stopAcquireMotor();
        Robot.ballIndexer.shooterActive =false;
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
        end();
    }
}
