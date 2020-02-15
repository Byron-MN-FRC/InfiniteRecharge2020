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

import com.ctre.phoenix.motorcontrol.SupplyCurrentLimitConfiguration;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Timer;
// END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants;
import frc.robot.Robot;

/**
 *
 */
public class BallShooter extends Subsystem {

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTANTS

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTANTS

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
private WPI_TalonFX shootMotor;
private Solenoid coolingSolenoidShooter;

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    private double prev = 0;
    private double timer = 0;
    private boolean coolingOn = false;

    public BallShooter() {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
shootMotor = new WPI_TalonFX(13);


        
coolingSolenoidShooter = new Solenoid(10, 5);
addChild("coolingSolenoidShooter",coolingSolenoidShooter);

        

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
        motorConfig();
    }

    @Override
    public void initDefaultCommand() {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DEFAULT_COMMAND


    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DEFAULT_COMMAND

        // Set the default command for a subsystem here.
        // setDefaultCommand(new MySpecialCommand());
    }

    @Override
    public void periodic() {
        SmartDashboard.putNumber("Shooter Temp", shootMotor.getTemperature());
        SmartDashboard.putNumber("Shooter Stator Amps", shootMotor.getStatorCurrent());
        SmartDashboard.putNumber("Shooter Velocity",
                -shootMotor.getSelectedSensorVelocity() * 600 / Constants.kSensorUnitsPerRotation);

        if (timer >= 2000) {
            coolMotor();
            timer = 0;
        }

        else {
            timer = timer + 20;
        }
    }
     
    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CMDPIDGETTERS

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CMDPIDGETTERS
    public void fireMotor() {
        shootMotor.set(-.7);
    }

    public void stopShooter() {
        shootMotor.set(0);
    }

    // public void runBelt() {
    // Robot.ballIndexer.getBeltMotor().set(-.6);
    // }

    public void stopBelt() {
        Robot.ballIndexer.getBeltMotor().set(0);
    }

    public final SupplyCurrentLimitConfiguration currentLimiting = new SupplyCurrentLimitConfiguration(
            Constants.kEnableCurrentLimiting_BS, Constants.currentLimit, Constants.thresholdLimit,
            Constants.thresholdTime);

    public void motorConfig() {
        shootMotor.configFactoryDefault();
        shootMotor.configSupplyCurrentLimit(currentLimiting);
        shootMotor.configOpenloopRamp(5, Constants.kTimeoutMs);

    }

    public void coolerTime() {
        
    }

    public void coolMotor() {
        if (shootMotor.getTemperature() > 50) {
            coolingSolenoidShooter.set(true);
            coolingOn = true;
        } else if (coolingOn) {
            coolingSolenoidShooter.set(false);
            coolingOn = false;
        }
    }

    public void reinitializeShooter() {
        coolingSolenoidShooter.set(false);
        coolingOn = false;
        timer = 0;
    }
}
// Put methods for controlling this subsystem
// here. Call these from Commands.
