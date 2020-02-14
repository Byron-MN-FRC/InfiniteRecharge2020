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

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.commands.indexOneBall;

// END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS

/**
 *
 */
public class BallIndexer extends Subsystem {
    private int indexedBallCount = 0;

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTANTS

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTANTS

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
private AnalogInput indexAcquiredSensor;
private AnalogInput indexSensor;
private AnalogInput indexToShootSensor;
private WPI_TalonSRX beltMotor;

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    public boolean indexerRunning = false;

    public BallIndexer() {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
indexAcquiredSensor = new AnalogInput(0);
addChild("indexAcquiredSensor",indexAcquiredSensor);

        
indexSensor = new AnalogInput(1);
addChild("indexSensor",indexSensor);

        
indexToShootSensor = new AnalogInput(2);
addChild("indexToShootSensor",indexToShootSensor);

        
beltMotor = new WPI_TalonSRX(11);


        

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
    }

    @Override
    public void initDefaultCommand() {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DEFAULT_COMMAND


    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DEFAULT_COMMAND

        // Set the default command for a subsystem here.
        // setDefaultCommand(new MySpecialCommand());
    }

    boolean ballPresentAtExitPrevious = false;
    boolean ballPresentAtExit;

    @Override
    public void periodic() {
        // Put code here to be run every loop
        if (ballPresent(0) && !indexerRunning) {
            indexOneBall idxCmd = new indexOneBall();
            indexerRunning = true;
            idxCmd.start();
        }

        // The following code watches for balls appearing and leaving,
        // causing the ball count to be decremented.
        ballPresentAtExit = ballPresent(2);
        if (ballPresentAtExitPrevious && !ballPresentAtExit) {
            decrementBallCount();
        }
        ballPresentAtExitPrevious = ballPresentAtExit;
        SmartDashboard.putNumber("Power Cells Indexed", ballCount());
    }

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CMDPIDGETTERS

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CMDPIDGETTERS
    public void testSensor() {
        SmartDashboard.putNumber("is sensor working", indexAcquiredSensor.getValue());
    }

    public boolean ballPresent(int aPort) {
        boolean retValue = false;
        switch (aPort) {
        case 0:
            retValue = (indexAcquiredSensor.getValue() < 500);
            break;
        case 1:
            retValue = (indexSensor.getValue() < 500);
            break;
        case 2:
            retValue = (indexToShootSensor.getValue() < 500);
            break;
        default:
            retValue = false;
            break;
        }
        return retValue;
    }

    public void startIndexMotor(double speed) {
        beltMotor.set(speed);
    }

    public void stopIndexMotor() {
        beltMotor.set(0);
        indexerRunning = false;
    }

    public WPI_TalonSRX getBeltMotor() {
        return beltMotor;
    }

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public int incrementPwrCellCount() {
        return ++indexedBallCount;
    }

    public int decrementBallCount() {
        return --indexedBallCount;
    }

    public int ballCount() {
        return indexedBallCount;
    }

    public void resetCount() {
        indexedBallCount = 0;
    }

}
