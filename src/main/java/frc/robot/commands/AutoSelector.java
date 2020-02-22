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

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.subsystems.*;

/**
 *
 */
public class AutoSelector extends CommandGroup {


    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=PARAMETERS
    public AutoSelector() {

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=PARAMETERS
        // Add Commands here:
        // e.g. addSequential(new Command1());
        //      addSequential(new Command2());
        // these will run in order.

        // To run multiple commands at the same time,
        // use addParallel()
        // e.g. addParallel(new Command1());
        //      addSequential(new Command2());
        // Command1 and Command2 will run in parallel.

        // A command group will require all of the subsystems that each member
        // would require.
        // e.g. if Command1 requires chassis, and Command2 requires arm,
        // a CommandGroup containing them would require both the chassis and the
        // arm.
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=COMMAND_DECLARATIONS

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=COMMAND_DECLARATIONS
    String startingPosition = SmartDashboard.getString("Starting Position (L, C, R)", "L");  
      if (startingPosition.equals("L")) {

        //addParallel(new AutoSpinup());
        addSequential(new driveStraightFeet(7.5));
        addSequential(new autoTurn(-80));
        //addSequential(new AutoLimeLightTurn());
        //addSequential(new AutoIndexer());
        //addSequential(new stopShooter());
      }
      
       if (startingPosition.equals("C")) {

        //addParallel(new AutoSpinup());
        addSequential(new driveStraightFeet(4.25));
        addSequential(new autoTurn(-60));
        addSequential(new driveStraightFeet(2.5));
        //addSequential(new autoTurn(-90));
        //addSequential(new AutoLimeLightTurn());
        //addSequential(new AutoIndexer());
        //addSequential(new stopShooter());
        //addSequential(new autoTurn(-60));
        //addParallel(new startAcquisition());
        //addSequential(new driveStraightFeet(2.5));
    }

        if (startingPosition.equals("R")) {

        //addParallel(new AutoSpinup());
        addSequential(new autoTurn(45));
        addSequential(new driveStraightFeet(11));
        //addSequential(new AutoLimeLightTurn());
        //addSequential(new AutoIndexer());
        //addSequential(new stopShooter());

    }

}

}

