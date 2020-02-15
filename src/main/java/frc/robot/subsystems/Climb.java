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
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.ClimbConstants;
import frc.robot.commands.manualDriveLinearSlide;

// END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS

/**
 *
 */
public class Climb extends Subsystem {

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTANTS

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTANTS

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
private WPI_VictorSPX buddyWinchBack;
private WPI_VictorSPX buddyWinchFront;
private WPI_TalonSRX hookMotor;
private WPI_TalonSRX winchMotor;
private AnalogInput proximitySensor;
private Solenoid buddyPiston;

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS

    public Climb() {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
buddyWinchBack = new WPI_VictorSPX(15);


        
buddyWinchFront = new WPI_VictorSPX(12);


        
hookMotor = new WPI_TalonSRX(10);


        
winchMotor = new WPI_TalonSRX(14);


        
proximitySensor = new AnalogInput(3);
addChild("ProximitySensor",proximitySensor);

        
buddyPiston = new Solenoid(10, 0);
addChild("buddyPiston",buddyPiston);

        

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
        motorConfig();
        

        
    }

    @Override
    public void initDefaultCommand() {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DEFAULT_COMMAND

        setDefaultCommand(new manualDriveLinearSlide());

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DEFAULT_COMMAND

        // Set the default command for a subsystem here.
        // setDefaultCommand(new MySpecialCommand());
    }

    @Override
    public void periodic() {
        // Put code here to be run every loop
        SmartDashboard.putNumber("Encoder Units", hookMotor.getSelectedSensorPosition());
    }

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CMDPIDGETTERS

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CMDPIDGETTERS
    public static boolean cMode = false;

    // stopClimbHook and stopPullRobotUp set hookMotor and winchMotor to 0 speed
    public void stopClimbHook() {
        System.out.println("hook stop");
        hookMotor.set(0);
    }

    public void stopPullRobotUp() {
        System.out.println("up stop");
        winchMotor.set(0);
    }

    public void deployClimbHooks() {
        // hookMotor.set(0.4), (0.1);
        if (!cMode) {
            System.out.println("Not deploying climb hooks due to cMode");
            return;
        }
        double hookUpEncoderUnits = ClimbConstants.hookUpEncoderUnits;
        hookMotor.set(ControlMode.MotionMagic, hookUpEncoderUnits);
    }
    /*
     * not currently used, could slowly lower climb hooks public void
     * lowerClimbHooks() { hookMotor.set(-0.1); }
     */

    public void lowerClimbHooks() {
        hookMotor.set(-0.4);
    }

    public void pullRobotUp() {
        if (!cMode) {
            System.out.println("Not raising robot due to cMode");
            return;
        }
        winchMotor.set(-0.7);
        if (hookMotor.getSelectedSensorPosition() >= 0) {
            hookMotor.set(-0.1);
        } else {
            hookMotor.set(0);
        }
    }
    // uses winch motors for buddy front and back, at different speeds, to lift up
    // a friendly bot

    public void pullBuddyUp() {
        if (!cMode) {
            System.out.println("not pulling buddy up due to cMode");
            return;
        }
        buddyWinchBack.set(0.4);
        buddyWinchFront.set(0.5);
    }
    public void releaseWinch() {
        if (!cMode) {
            System.out.println("Not reversing winch due to cMode");
            return;
        }
        winchMotor.set(0.1);
    }

    public void driveWithClimbJoystick(Joystick pJoystick) {
        if (!cMode){
         //   System.out.println("Not raising climb hooks due to cMode");
            return;
        }
        double y = -pJoystick.getY();
        hookMotor.set(ControlMode.PercentOutput, y);
    }

    public void motorConfig() {
        /* Factory default hardware to prevent unexpected behavior */
        hookMotor.configFactoryDefault();

        /* Configure Sensor Source for Pirmary PID */
        hookMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, ClimbConstants.kPIDLoopIdx,
                ClimbConstants.kTimeoutMs);

        /**
         * Configure Talon SRX Output and Sesnor direction accordingly Invert Motor to
         * have green LEDs when driving Talon Forward / Requesting Postiive Output Phase
         * sensor to have positive increment when driving Talon Forward (Green LED)
         */
        hookMotor.setSensorPhase(false); // required to stop
        hookMotor.setInverted(false);

        /* Set relevant frame periods to be at least as fast as periodic rate */
        hookMotor.setStatusFramePeriod(StatusFrameEnhanced.Status_13_Base_PIDF0, 10, ClimbConstants.kTimeoutMs);
        hookMotor.setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, 10, ClimbConstants.kTimeoutMs);

        /* Set the peak and nominal outputs */
        hookMotor.configNominalOutputForward(0, ClimbConstants.kTimeoutMs);
        hookMotor.configNominalOutputReverse(0, ClimbConstants.kTimeoutMs);
        hookMotor.configPeakOutputForward(1, ClimbConstants.kTimeoutMs);
        hookMotor.configPeakOutputReverse(-1, ClimbConstants.kTimeoutMs);

        /* Set Motion Magic gains in slot0 - see documentation */
        hookMotor.selectProfileSlot(ClimbConstants.kSlot_Turning, ClimbConstants.kPIDLoopIdx);
        hookMotor.config_kF(ClimbConstants.kSlot_Turning, ClimbConstants.kGains_hookMotor.kF,
                ClimbConstants.kTimeoutMs);
        hookMotor.config_kP(ClimbConstants.kSlot_Turning, ClimbConstants.kGains_hookMotor.kP,
                ClimbConstants.kTimeoutMs);
        hookMotor.config_kI(ClimbConstants.kSlot_Turning, ClimbConstants.kGains_hookMotor.kI,
                ClimbConstants.kTimeoutMs);
        hookMotor.config_kD(ClimbConstants.kSlot_Turning, ClimbConstants.kGains_hookMotor.kD,
                ClimbConstants.kTimeoutMs);
        /* Set acceleration and vcruise velocity - see documentation */
        hookMotor.configMotionCruiseVelocity(15000, ClimbConstants.kTimeoutMs);
        hookMotor.configMotionAcceleration(6000, ClimbConstants.kTimeoutMs);

        /* Zero the sensor */
        hookMotor.setSelectedSensorPosition(0, ClimbConstants.kPIDLoopIdx, ClimbConstants.kTimeoutMs);

    }

    public WPI_TalonSRX getHookMotor() {
        return hookMotor;

    }
    /*
     * 
     * 
     * hookMotor.setSelectedSensorPosition(0, Constants.kPIDLoopIdx,
     * Constants.kTimeoutMs);
     */
    // }

    public Boolean atHeight() {
        double currentEncoderUnits = hookMotor.getSelectedSensorPosition(ClimbConstants.kPIDLoopIdx);
        double targetEncoderUnits = ClimbConstants.hookUpEncoderUnits;
        if (Math.abs(currentEncoderUnits - targetEncoderUnits)  < 1000) {
            return true;
        }
        return false;
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
    }

    public int getProximity() {
        return proximitySensor.getValue();
    }

    public AnalogInput proxSenor() {
        return proximitySensor;
    }
    
     //supposed to deploy buddy net, not currently being used.
    public void deployBuddyNet (){
        //buddyNetPiston.set(Value.kForward);
    }
}
