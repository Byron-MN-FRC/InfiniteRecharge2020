// RobotBuilder Version: 2.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.

package frc.robot.subsystems;

import frc.robot.Constants;
import frc.robot.Robot;
import frc.robot.commands.*;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;

import com.ctre.phoenix.motorcontrol.SupplyCurrentLimitConfiguration;
// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS

/**
 *
 */
public class BallShooter extends Subsystem {

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTANTS

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTANTS

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
private WPI_TalonFX shootMotor;

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    double prev = 0;

    public BallShooter() {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
shootMotor = new WPI_TalonFX(13);
    }

        

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS

    @Override
    public void initDefaultCommand() {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DEFAULT_COMMAND


    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DEFAULT_COMMAND

        // Set the default command for a subsystem here.
        // setDefaultCommand(new MySpecialCommand());
    }

    @Override
    public void periodic() {
        SmartDashboard.putNumber("Shooter Velocity", shootMotor.getSelectedSensorVelocity());
        SmartDashboard.putNumber("Shooter Temp", shootMotor.getTemperature());
        SmartDashboard.putNumber("Current", shootMotor.getStatorCurrent());
        // Put code here to be run every loop

    }

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CMDPIDGETTERS

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CMDPIDGETTERS
    public void fireMotor() {
        shootMotor.set(-.7);
        double RPM = ((shootMotor.getSelectedSensorVelocity() - prev) * 600) / 2048;
        prev = shootMotor.getSelectedSensorVelocity();
        SmartDashboard.putNumber("RPM", RPM);
    }

    public void stopShooter() {
        shootMotor.set(0);
    }

    public void runBelt() {
        Robot.ballIndexer.getBeltMotor().set(-.6);
    }

    public void stopBelt() {
        Robot.ballIndexer.getBeltMotor().set(0);
    }

    public final SupplyCurrentLimitConfiguration currentLimiting = new SupplyCurrentLimitConfiguration(Constants.enable,
            Constants.currentLimit, Constants.thresholdLimit, Constants.thresholdTime);

    public void motorConfig() {
        shootMotor.configSupplyCurrentLimit(currentLimiting);
    }

}
// Put methods for controlling this subsystem
// here. Call these from Commands.
