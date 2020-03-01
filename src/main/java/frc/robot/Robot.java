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

import edu.wpi.first.hal.FRCNetComm.tInstances;
import edu.wpi.first.hal.FRCNetComm.tResourceType;
import edu.wpi.first.hal.HAL;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.LimelightUtility.StreamMode;
import frc.robot.commands.AutoSelector;
import frc.robot.commands.AutonomousCommand;
import frc.robot.commands.setHoodToZero;
import frc.robot.subsystems.BallAcquisition;
import frc.robot.subsystems.BallIndexer;
import frc.robot.subsystems.BallShooter;
import frc.robot.subsystems.Climb;
import frc.robot.subsystems.ControlPanel;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.Shifter;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.properties file in the
 * project.
 */
public class Robot extends TimedRobot {

    Command autonomousCommand;
    SendableChooser<Command> chooser = new SendableChooser<>();

    public static OI oi;
    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
public static DriveTrain driveTrain;
public static ControlPanel controlPanel;
public static BallAcquisition ballAcquisition;
public static BallIndexer ballIndexer;
public static BallShooter ballShooter;
public static Climb climb;
public static Shifter shifter;

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS

    /**
     * This function is run when the robot is first started up and should be used
     * for any initialization code.
     */
    @Override
    public void robotInit() {
        LimelightUtility.WriteDouble("ledMode", 3); // 3 = Limelight On

        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
driveTrain = new DriveTrain();
controlPanel = new ControlPanel();
ballAcquisition = new BallAcquisition();
ballIndexer = new BallIndexer();
ballShooter = new BallShooter();
climb = new Climb();
shifter = new Shifter();

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
        // OI must be constructed after subsystems. If the OI creates Commands
        // (which it very likely will), subsystems are not guaranteed to be
        // constructed yet. Thus, their requires() statements may grab null
        // pointers. Bad news. Don't move it.
        oi = new OI();

        HAL.report(tResourceType.kResourceType_Framework, tInstances.kFramework_RobotBuilder);

        // Add commands to Autonomous Sendable Chooser
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=AUTONOMOUS

        chooser.addObject("Autonomous Command", new AutonomousCommand());
        chooser.setDefaultOption("Autonomous Command", new AutonomousCommand());

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=AUTONOMOUS
        SmartDashboard.putData("drive/Auto mode", chooser);
        LimelightUtility.StreamingMode(LimelightUtility.StreamMode.PIPSecondary);
        SmartDashboard.putString(Constants.autoPosition, "L");
    }

    /**
     * This function is called when the disabled button is hit. You can use it to
     * reset subsystems before shutting down.
     */
    @Override
    public void disabledInit() {
        // Set the limelight so that it can be configured.
        LimelightUtility.EnableDriverCamera(false);
        LimelightUtility.StreamingMode(StreamMode.Standard);
        Robot.ballShooter.teleopWithIdle = false;
        Robot.ballShooter.setShootIdleVelocity(0);
    }

    @Override
    public void disabledPeriodic() {
        Scheduler.getInstance().run();
    }


    @Override
    public void autonomousInit() {
        initializeSubsystems();
        Robot.driveTrain.autonomousLimiting();        
        // schedule the autonomous command (example)
        // if (autonomousCommand != null) autonomousCommand.start();

        autonomousCommand = new AutoSelector();
        autonomousCommand.start();
    }

    /**
     * This function is called periodically during autonomous
     */
    @Override
    public void autonomousPeriodic() {
        ballShooter.inAuton = true;
        SmartDashboard.putBoolean("drive/LimeLight Target", LimelightUtility.ValidTargetFound());
        Scheduler.getInstance().run();
        SmartDashboard.putNumber("drive/Game Timer", Timer.getMatchTime());

    }

    @Override
    public void teleopInit() {
        // This makes sure that the autonomous stops running when
        // teleop starts running. If you want the autonomous to
        // continue until interrupted by another command, remove
        // this line or comment it out.
        if (autonomousCommand != null)
            autonomousCommand.cancel();

        // Robot.driveTrain.teleopLimiting();
        initializeSubsystems();
    }

    /**
     * This function is called periodically during operator control
     */
    @Override
    public void teleopPeriodic() {
        ballShooter.inAuton = false;
        Scheduler.getInstance().run();
        LimelightUtility.RefreshTrackingData();
        SmartDashboard.putBoolean("drive/LimeLight Target", LimelightUtility.ValidTargetFound());
        SmartDashboard.putNumber("drive/Game Timer", Timer.getMatchTime());
    }

    public void initializeSubsystems() {
        Robot.driveTrain.motorConfig();
        Robot.driveTrain.zeroSensors();
        Robot.ballIndexer.reinitializeIndexer();
        Robot.ballIndexer.resetCount();
        Robot.driveTrain.reinitializeDriveTrain();
        Robot.ballShooter.reinitializeShooter();
        Robot.shifter.reinitializeShifter();
        Robot.ballAcquisition.reinitializeAquisition();
        Robot.climb.reInitializeClimb();
        Robot.controlPanel.reinitializeControlPanel();
    }
}
