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

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.commands.AutonomousCommand;
import frc.robot.commands.LimeLightTurn;
import frc.robot.commands.deployLinearSlide;
import frc.robot.commands.deployNet;
import frc.robot.commands.driveForward;
import frc.robot.commands.driveWithJoyStick;
import frc.robot.commands.driveWithLimeLight;
import frc.robot.commands.indexOneBall;
import frc.robot.commands.indexReverseOne;
import frc.robot.commands.indexToShoot;
import frc.robot.commands.lowerLinearSlide;
import frc.robot.commands.manualDriveLinearSlide;
import frc.robot.commands.pullBuddyUp;
import frc.robot.commands.pullRobotUp;
import frc.robot.commands.shiftDown;
import frc.robot.commands.shiftUp;
import frc.robot.commands.spinUp;
import frc.robot.commands.startAcquisition;
import frc.robot.commands.startShooter;
import frc.robot.commands.stopAcquire;
import frc.robot.commands.stopDrive;
import frc.robot.commands.stopFlyWheel;
import frc.robot.commands.stopShooting;
import frc.robot.commands.testClosedLoopTurn;

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

    // Start the command when the button is released and let it run the command
    // until it is finished as determined by it's isFinished method.
    // button.whenReleased(new ExampleCommand());

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
public JoystickButton btnAcquire;
public JoystickButton btnStopShooting;
public JoystickButton btnShiftUp;
public JoystickButton btnSpinUp;
public JoystickButton btnStopFlyWheel;
public JoystickButton btnStartShooter;
public JoystickButton btnLimeLightTurn;
public JoystickButton btnShiftDown;
public Joystick joystick;
public JoystickButton btnRobotUp;
public JoystickButton btnDeployLinearSlide;
public JoystickButton btnDeployNet;
public JoystickButton btnBuddyUp;
public Joystick climbJoystick;

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS

    public OI() {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS

climbJoystick = new Joystick(1);

btnBuddyUp = new JoystickButton(climbJoystick, 1);
btnBuddyUp.whileHeld(new pullBuddyUp());
btnDeployNet = new JoystickButton(climbJoystick, 3);
btnDeployNet.whenPressed(new deployNet());
btnDeployLinearSlide = new JoystickButton(climbJoystick, 6);
btnDeployLinearSlide.whenPressed(new deployLinearSlide());
btnRobotUp = new JoystickButton(climbJoystick, 4);
btnRobotUp.whenPressed(new pullRobotUp());
joystick = new Joystick(0);

btnShiftDown = new JoystickButton(joystick, 11);
btnShiftDown.whenReleased(new shiftDown());
btnLimeLightTurn = new JoystickButton(joystick, 3);
btnLimeLightTurn.whenPressed(new LimeLightTurn());
btnStartShooter = new JoystickButton(joystick, 1);
btnStartShooter.whileHeld(new startShooter());
btnStopFlyWheel = new JoystickButton(joystick, 4);
btnStopFlyWheel.whenReleased(new stopFlyWheel());
btnSpinUp = new JoystickButton(joystick, 4);
btnSpinUp.whileHeld(new spinUp());
btnShiftUp = new JoystickButton(joystick, 11);
btnShiftUp.whileHeld(new shiftUp());
btnStopShooting = new JoystickButton(joystick, 1);
btnStopShooting.whenReleased(new stopShooting());
btnAcquire = new JoystickButton(joystick, 2);
btnAcquire.whileHeld(new startAcquisition());


        // SmartDashboard Buttons
        SmartDashboard.putData("Autonomous Command", new AutonomousCommand());
        SmartDashboard.putData("shiftUp", new shiftUp());
        SmartDashboard.putData("shiftDown", new shiftDown());
        SmartDashboard.putData("driveWithJoyStick", new driveWithJoyStick());
        SmartDashboard.putData("driveForward", new driveForward());
        SmartDashboard.putData("stopDrive", new stopDrive());
        SmartDashboard.putData("startAcquisition", new startAcquisition());
        SmartDashboard.putData("driveWithLimeLight", new driveWithLimeLight());
        SmartDashboard.putData("startShooter", new startShooter());
        SmartDashboard.putData("deployLinearSlide", new deployLinearSlide());
        SmartDashboard.putData("pullRobotUp", new pullRobotUp());
        SmartDashboard.putData("deployNet", new deployNet());
        SmartDashboard.putData("pullBuddyUp", new pullBuddyUp());
        SmartDashboard.putData("indexOneBall", new indexOneBall());
        SmartDashboard.putData("indexToShoot", new indexToShoot());
        SmartDashboard.putData("indexReverseOne", new indexReverseOne());
        SmartDashboard.putData("stopAcquire", new stopAcquire());
        SmartDashboard.putData("testClosedLoopTurn: left_90", new testClosedLoopTurn(-90));
        SmartDashboard.putData("testClosedLoopTurn: right_90", new testClosedLoopTurn(90));
        SmartDashboard.putData("lowerLinearSlide", new lowerLinearSlide());
        SmartDashboard.putData("spinUp", new spinUp());
        SmartDashboard.putData("stopFlyWheel", new stopFlyWheel());
        SmartDashboard.putData("stopShooting", new stopShooting());
        SmartDashboard.putData("LimeLightTurn", new LimeLightTurn());
        SmartDashboard.putData("manualDriveLinearSlide", new manualDriveLinearSlide());

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
       SmartDashboard.putNumber(Constants.kAmpLimitStr, 2);
       SmartDashboard.putNumber(Constants.kAmpPeakStr,3);
    }

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=FUNCTIONS
public Joystick getJoystick() {
        return joystick;
    }

public Joystick getClimbJoystick() {
        return climbJoystick;
    }


    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=FUNCTIONS
}
