// RobotBuilder Version: 2.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.


package frc.robot;

import frc.robot.commands.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.*;
import frc.robot.subsystems.*;


/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
    //// CREATING BUTTONS
    // One type of button is a joystick button which is any button on a joystick.
    // You create one by telling it which joystick it's on and which button
    // number it is.
    // Joystick stick = new Joystick(port);
    // Button button = new JoystickButton(stick, buttonNumber);

    // There are a few additional built in buttons you can use. Additionally,
    // by subclassing Button you can create custom triggers and bind those to
    // commands the same as any other Button.

    //// TRIGGERING COMMANDS WITH BUTTONS
    // Once you have a button, it's trivial to bind it to a button in one of
    // three ways:

    // Start the command when the button is pressed and let it run the command
    // until it is finished as determined by it's isFinished method.
    // button.whenPressed(new ExampleCommand());

    // Run the command while the button is being held down and interrupt it once
    // the button is released.
    // button.whileHeld(new ExampleCommand());

    // Start the command when the button is released  and let it run the command
    // until it is finished as determined by it's isFinished method.
    // button.whenReleased(new ExampleCommand());


    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
public JoystickButton btnAcquire;
public JoystickButton btnReverseAcquire;
public JoystickButton btnShiftDown;
public JoystickButton btnShiftUp;
public JoystickButton btnEnableClimb;
public JoystickButton btnEngageColorSensor;
public JoystickButton btnDisengageColorSensor;
public JoystickButton btnHoodUp;
public JoystickButton btnHoodDown;
public Joystick driveJoystick;
public JoystickButton btnStartShooter;
public JoystickButton btnStopShooting;
public JoystickButton btnReverseIndex;
public JoystickButton btnLimeLightTurn;
public JoystickButton btnSpinUp;
public JoystickButton btnStopFlyWheel;
public JoystickButton btnRobotUp;
public JoystickButton btnDeployNet;
public JoystickButton btnBuddyUp;
public JoystickButton tempReleaseWinch;
public JoystickButton tempReleaseWinchStop;
public JoystickButton btnStopBuddyClimb;
public Joystick accessoriesJoystick;

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS

    public OI() {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS

accessoriesJoystick = new Joystick(1);

btnStopBuddyClimb = new JoystickButton(accessoriesJoystick, 11);
btnStopBuddyClimb.whenReleased(new toggleServoOff());
tempReleaseWinchStop = new JoystickButton(accessoriesJoystick, 8);
tempReleaseWinchStop.whenReleased(new stopReleaseWinch());
tempReleaseWinch = new JoystickButton(accessoriesJoystick, 8);
tempReleaseWinch.whileHeld(new releaseWinch());
btnBuddyUp = new JoystickButton(accessoriesJoystick, 11);
btnBuddyUp.whileHeld(new pullBuddyUp());
btnDeployNet = new JoystickButton(accessoriesJoystick, 9);
btnDeployNet.whenPressed(new deployNet());
btnRobotUp = new JoystickButton(accessoriesJoystick, 7);
btnRobotUp.whenPressed(new pullRobotUp());
btnStopFlyWheel = new JoystickButton(accessoriesJoystick, 4);
btnStopFlyWheel.whenReleased(new stopFlyWheel());
btnSpinUp = new JoystickButton(accessoriesJoystick, 4);
btnSpinUp.whileHeld(new spinUp());
btnLimeLightTurn = new JoystickButton(accessoriesJoystick, 3);
btnLimeLightTurn.whenPressed(new LimeLightTurn());
btnReverseIndex = new JoystickButton(accessoriesJoystick, 2);
btnReverseIndex.whileHeld(new indexReverseOne());
btnStopShooting = new JoystickButton(accessoriesJoystick, 1);
btnStopShooting.whenReleased(new stopShooting());
btnStartShooter = new JoystickButton(accessoriesJoystick, 1);
btnStartShooter.whileHeld(new startShooter());
driveJoystick = new Joystick(0);

btnHoodDown = new JoystickButton(driveJoystick, 9);
btnHoodDown.whenPressed(new hoodDown());
btnHoodUp = new JoystickButton(driveJoystick, 10);
btnHoodUp.whenPressed(new hoodUp());
btnDisengageColorSensor = new JoystickButton(driveJoystick, 7);
btnDisengageColorSensor.whenPressed(new disengageColorSensor());
btnEngageColorSensor = new JoystickButton(driveJoystick, 8);
btnEngageColorSensor.whenPressed(new engageColorSensor());
btnEnableClimb = new JoystickButton(driveJoystick, 6);
btnEnableClimb.whenPressed(new climbMode());
btnShiftUp = new JoystickButton(driveJoystick, 12);
btnShiftUp.whenPressed(new shiftUp());
btnShiftDown = new JoystickButton(driveJoystick, 11);
btnShiftDown.whenPressed(new shiftDown());
btnReverseAcquire = new JoystickButton(driveJoystick, 2);
btnReverseAcquire.whileHeld(new reverseAcquisition());
btnAcquire = new JoystickButton(driveJoystick, 1);
btnAcquire.whileHeld(new startAcquisition());


        // SmartDashboard Buttons
        SmartDashboard.putData("releaseWinch", new releaseWinch());
        SmartDashboard.putData("teleopAutoShoot", new teleopAutoShoot());
        SmartDashboard.putData("toggleServoOff", new toggleServoOff());

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
    }

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=FUNCTIONS
public Joystick getDriveJoystick() {
        return driveJoystick;
    }

public Joystick getAccessoriesJoystick() {
        return accessoriesJoystick;
    }


    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=FUNCTIONS
}

