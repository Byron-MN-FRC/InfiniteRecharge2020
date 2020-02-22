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

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.SupplyCurrentLimitConfiguration;
import com.ctre.phoenix.motorcontrol.TalonFXFeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Solenoid;
// END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.BallIndexerConstants;
import frc.robot.BallShooterConstants;
import frc.robot.Robot;
import frc.robot.commands.idleBallShooter;

/**
 *
 */
public class BallShooter extends Subsystem {

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTANTS

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTANTS

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
private WPI_TalonFX shootMotor;
private Solenoid coolingSolenoidShooter;
private WPI_TalonSRX hoodMotor;
private DigitalInput limitSwitchDown;

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS

    private double timer = 0;
    private boolean coolingOn = false;

    public final SupplyCurrentLimitConfiguration currentLimiting = new SupplyCurrentLimitConfiguration(
            BallShooterConstants.kEnableCurrentLimiting_BS, BallShooterConstants.currentLimit,
            BallShooterConstants.thresholdLimit, BallShooterConstants.thresholdTime);

    /**
     * This is the constructor for the BallShooter subsystem.
     */
    public BallShooter() {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
shootMotor = new WPI_TalonFX(13);


        
coolingSolenoidShooter = new Solenoid(10, 5);
addChild("coolingSolenoidShooter",coolingSolenoidShooter);

        
hoodMotor = new WPI_TalonSRX(8);


        
limitSwitchDown = new DigitalInput(0);
addChild("limitSwitchDown",limitSwitchDown);

        

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
        shootMotorConfig();
        hoodMotorConfig();
    }

    @Override
    public void initDefaultCommand() {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DEFAULT_COMMAND

        setDefaultCommand(new idleBallShooter());

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DEFAULT_COMMAND

        // Set the default command for a subsystem here.
        // setDefaultCommand(new MySpecialCommand());
    }

    @Override
    public void periodic() {
        SmartDashboard.putNumber("Shooter Temp", shootMotor.getTemperature());
        SmartDashboard.putNumber("Shooter Stator Amps", shootMotor.getStatorCurrent());
        SmartDashboard.putNumber("Shooter Velocity",
                -shootMotor.getSelectedSensorVelocity() * 600 / BallShooterConstants.kSensorUnitsPerRotation);

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
        toConsoleln("fireMotor() called");
        // this is dangerous as we are not looking at the state of the hood
        shootMotor.set(-.85);
    }

    /**
     * idleSubsystem() will stow the hood and idle the motor based on constants stored
     * in BallShooterConstants.
     */
	public void idleSubsystem() {
        hoodMotor.set(ControlMode.Position, BallShooterConstants.hoodIdlePosition);
        shootMotor.set(ControlMode.Velocity, rpmToVelocityPer100ms(BallShooterConstants.shootIdleVelocity));
    }
    
    private int loop = 0;
    /**
     * ready2Shoot tests both the shooter rpm and the hood position to determine if
     * they are ready to attempt to shoot.
     * 
     * @param rpms             - Target RPM that we wish to reach
     * @param hoodEncoderUnits - Target position in encoder units for the hood.
     * @return True if both the shooter and the hood have reached their specified
     *         speed and position within tolerances.
     */
    public boolean ready2Shoot(final double rpms, final double hoodEncoderUnits) {
        final double velocityPer100ms = rpmToVelocityPer100ms(rpms);

        // issue set commands on motors to set velocity and position.
        //hoodMotor.set(ControlMode.Position, hoodEncoderUnits);
        shootMotor.set(ControlMode.Velocity, velocityPer100ms);

        // report debugging information 
        if (++loop >= 15) {
            toConsoleln("ShootTarget=" + rpmToVelocityPer100ms(rpms) + " Cur="
                    + shootMotor.getSelectedSensorVelocity(BallShooterConstants.kPIDLoopIdx) + " Err="
                    + shootMotor.getClosedLoopError(BallShooterConstants.kPIDLoopIdx) +
                     "In Threshold " + isWithinThreshold());
        }

        //return isWithinThreshold() && hoodAtPosition();        
        return isWithinThreshold() ;

    }

    public void stopShooter() {
        // The default command should take over and pull us back up to an idled velocity
        shootMotor.set(ControlMode.Position,0);
        toConsoleln("stopShooter() called.");
    }

    public void stopBelt() {
        Robot.ballIndexer.getBeltMotor().set(0);
        toConsoleln("stopBelt() called.");
    }

    public void shootMotorConfig() {
        /* Factory Default all hardware to prevent unexpected behaviour */
        shootMotor.configFactoryDefault();

        /* Config neutral deadband to be the smallest possible */
        shootMotor.configNeutralDeadband(0.001);

        /* Config sensor used for Primary PID [Velocity] */
        shootMotor.configSelectedFeedbackSensor(TalonFXFeedbackDevice.IntegratedSensor,
                BallShooterConstants.kPIDLoopIdx, BallShooterConstants.kTimeoutMs);

        /* Config the peak and nominal outputs */
        shootMotor.configNominalOutputForward(0, BallShooterConstants.kTimeoutMs);
        shootMotor.configNominalOutputReverse(0, BallShooterConstants.kTimeoutMs);
        shootMotor.configPeakOutputForward(1, BallShooterConstants.kTimeoutMs);
        shootMotor.configPeakOutputReverse(-1, BallShooterConstants.kTimeoutMs);

        /* Config the Velocity closed loop gains in slot0 */
        shootMotor.config_kF(BallShooterConstants.kPIDLoopIdx, BallShooterConstants.kGains_shootMotor.kF,
                BallShooterConstants.kTimeoutMs);
        shootMotor.config_kP(BallShooterConstants.kPIDLoopIdx, BallShooterConstants.kGains_shootMotor.kP,
                BallShooterConstants.kTimeoutMs);
        shootMotor.config_kI(BallShooterConstants.kPIDLoopIdx, BallShooterConstants.kGains_shootMotor.kI,
                BallShooterConstants.kTimeoutMs);
        shootMotor.config_kD(BallShooterConstants.kPIDLoopIdx, BallShooterConstants.kGains_shootMotor.kD,
                BallShooterConstants.kTimeoutMs);
        /*
         * Talon FX does not need sensor phase set for its integrated sensor This is
         * because it will always be correct if the selected feedback device is
         * integrated sensor (default value) and the user calls getSelectedSensor* to
         * get the sensor's position/velocity.
         * 
         * https://phoenix-documentation.readthedocs.io/en/latest/ch14_MCSensor.html#
         * sensor-phase
         */
        // shootMotor.setSensorPhase(true);

        // Ramp motor w/ current limiting on
        shootMotor.configSupplyCurrentLimit(currentLimiting);
        shootMotor.configOpenloopRamp(1.75, BallShooterConstants.kTimeoutMs);
        hoodMotorConfig();

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
        toConsoleln("reinitializeShooter() called.");

        shootMotorConfig(); // temporary
        coolingSolenoidShooter.set(false);
        coolingOn = false;
        timer = 0;
        hoodMotor.setSelectedSensorPosition(0, BallShooterConstants.kPIDLoopIdx, BallShooterConstants.kTimeoutMs);

    }

    public double getShooterRPM() {
        return -shootMotor.getSelectedSensorVelocity() * 600 / BallShooterConstants.kSensorUnitsPerRotation;

    }

    public Boolean targetEncoder() {
        final double currentEncoderUnits = hoodMotor.getSelectedSensorPosition(0);
        if (Math.abs(currentEncoderUnits - BallShooterConstants.kHoodUpEncoderUnits) < 190) {
            return true;
        }
        return false;
    }

    public void hoodUp() {
        hoodMotor.set(ControlMode.MotionMagic, BallShooterConstants.kHoodUpEncoderUnits);

    }

    public void hoodDown() {
        toConsoleln("hoodDown() called.");
        hoodMotor.set(ControlMode.MotionMagic, 0);
    }

    public double getHoodEncoderUnits() {
        return hoodMotor.getSelectedSensorPosition(0);
    }

    public void stopHoodMotor() {
        toConsoleln("stopHoodMotor() called.");
        hoodMotor.set(0);
        // hoodMotor.setSelectedSensorPosition(0);
    }

    public void hoodMotorConfig() {
        /* Factory default hardware to prevent unexpected behavior */
        hoodMotor.configFactoryDefault();

        /* Configure Sensor Source for Pirmary PID */
        hoodMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative,
                BallShooterConstants.kPIDLoopIdx, BallShooterConstants.kTimeoutMs);

        /**
         * Configure Talon SRX Output and Sesnor direction accordingly Invert Motor to
         * have green LEDs when driving Talon Forward / Requesting Postiive Output Phase
         * sensor to have positive increment when driving Talon Forward (Green LED)
         */
        hoodMotor.setSensorPhase(false); // required to stop
        hoodMotor.setInverted(false);

        /* Set relevant frame periods to be at least as fast as periodic rate */
        hoodMotor.setStatusFramePeriod(StatusFrameEnhanced.Status_13_Base_PIDF0, 10, BallShooterConstants.kTimeoutMs);
        hoodMotor.setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, 10, BallShooterConstants.kTimeoutMs);

        /* Set the peak and nominal outputs */
        hoodMotor.configNominalOutputForward(0, BallShooterConstants.kTimeoutMs);
        hoodMotor.configNominalOutputReverse(0, BallShooterConstants.kTimeoutMs);
        hoodMotor.configPeakOutputForward(1, BallShooterConstants.kTimeoutMs);
        hoodMotor.configPeakOutputReverse(-1, BallShooterConstants.kTimeoutMs);

        /* Set Motion Magic gains in slot0 - see documentation */
        hoodMotor.selectProfileSlot(BallShooterConstants.kSlot_Turning, BallShooterConstants.kPIDLoopIdx);
        hoodMotor.config_kF(BallShooterConstants.kSlot_Turning, BallShooterConstants.kGains_hoodMotor.kF,
                BallShooterConstants.kTimeoutMs);
        hoodMotor.config_kP(BallShooterConstants.kSlot_Turning, BallShooterConstants.kGains_hoodMotor.kP,
                BallShooterConstants.kTimeoutMs);
        hoodMotor.config_kI(BallShooterConstants.kSlot_Turning, BallShooterConstants.kGains_hoodMotor.kI,
                BallShooterConstants.kTimeoutMs);
        hoodMotor.config_kD(BallShooterConstants.kSlot_Turning, BallShooterConstants.kGains_hoodMotor.kD,
                BallShooterConstants.kTimeoutMs);
        /* Set acceleration and vcruise velocity - see documentation */
        hoodMotor.configMotionCruiseVelocity(150, BallShooterConstants.kTimeoutMs);
        hoodMotor.configMotionAcceleration(150, BallShooterConstants.kTimeoutMs);

        /* Zero the sensor */
        hoodMotor.setSelectedSensorPosition(0, BallShooterConstants.kPIDLoopIdx, BallShooterConstants.kTimeoutMs);

    }

    public boolean isLimitSwitchDown() {
        return !limitSwitchDown.get();
    }

    /**
     * Given velocity in rotations per minute (rpm), rpmToVelocityPer100ms
     * calculates and returns the velocity in encoder units per 100ms
     * 
     * @param rpm - Motor Rotations per minute
     * @return - Encoder units per 100ms
     */
    public double rpmToVelocityPer100ms(final double rpm) {
        return rpm * BallShooterConstants.kSensorUnitsPerRotation / 600;
    }

    /**
     * Given velocity in encoder units per second, velocityPer100msToRpm calculates
     * and returns the velocity in rotations per minute (rpm)
     * 
     * @param velPer100ms
     * @return
     */
    public double velocityPer100msToRpm(final double velPer100ms) {
        return velPer100ms / BallShooterConstants.kSensorUnitsPerRotation * 600;
    }

    /**
     * toConsoleln - this routine looks at the BallShooterConstants.debug to
     * determine if it should write something to the console or not. If debug is
     * true, it will to a println of the string passed in.
     * 
     * @param s - the string to write to the console out with a carriage return
     *          after it.
     */
    private void toConsoleln(final String s) {
        if (BallShooterConstants.debug) {
            System.out.println(s);
        }
    }

    /**
     * toConsole - this routine looks at the BallShooterConstants.debug to determine
     * if it should write something to the console or not. If debug is true, it will
     * to a println of the string passed in.
     * 
     * @param s - the string to write out to the console..
     */
    private void toConsole(final String s) {
        if (BallShooterConstants.debug) {
            System.out.print(s);
        }
    }

    private double _withinThresholdLoops = 0;

    /**
     * isWithinThreshold tracks the error reported by the shooter motor and
     * determines if it has "settled". It uses an error threshhold (and a counter
     * (kPIDLoopIdx)to determine if
     * 
     * @return - true if it has settled, otherwise false.
     */
    public boolean isWithinThreshold() {
        /* Check if closed loop error is within the threshld */
        if (shootMotor.getClosedLoopError(BallShooterConstants.kPIDLoopIdx) == 0) {
            _withinThresholdLoops = 0;
            return false;
        }

        if (shootMotor.getClosedLoopError(BallShooterConstants.kPIDLoopIdx) < +BallShooterConstants.kErrThreshold
                && shootMotor
                        .getClosedLoopError(BallShooterConstants.kPIDLoopIdx) > -BallShooterConstants.kErrThreshold) {
            ++_withinThresholdLoops;
            //toConsoleln("incrementing threshold loops: " + _withinThresholdLoops);
        } else {
            _withinThresholdLoops = 0;
            //toConsoleln("restart threshold loops: " + _withinThresholdLoops);
        }
        return (_withinThresholdLoops > BallShooterConstants.kLoopsToSettle);
    }

    private boolean hoodAtPosition() {
        final double err = hoodMotor.getClosedLoopError(BallShooterConstants.kPIDLoopIdx);
        return Math.abs(err) < BallShooterConstants.kHoodPositionTolerance;
    }

}
