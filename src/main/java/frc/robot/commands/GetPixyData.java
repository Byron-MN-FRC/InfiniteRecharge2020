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

import java.time.Duration;
import java.time.Instant;

import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.SerialPort.Port;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants;

/**
 *
 */
public class GetPixyData extends Command {
    private SerialPort serial = null;
    private boolean useLED = true;
    String dataBuffer;

    private Instant lastTimeStamp = Instant.now();
    private long msBetweenUpdates = 25;
    private boolean active = false;
    private boolean cmdSent = false;
    private long msTransactinTimeOut = 100;
    private Instant timeoutTrackStart = Instant.now();

    public int blockPos = -999;
    public int blockHeight = 999;

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_DECLARATIONS

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_DECLARATIONS

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTOR
    public GetPixyData() {

        // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTOR
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_SETTING

        // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_SETTING
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=REQUIRES

        // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=REQUIRES
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        System.out.println("==>Attempting to connect to the Arduino via Port.kUSB1 serial:");
        try {
            if (serial == null){
                serial = new SerialPort(115200, Port.kUSB1);
            }
        } catch (Exception ex) {
            System.out.println("==>FAILED to connect to the Arduino via Port.kUSB1 serial!");
        }
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        // see if we have timed out.  Timer starts when we send the command.
        if (Duration.between(timeoutTrackStart, Instant.now()).toMillis() > msTransactinTimeOut){
            reset();
            System.out.println("Timed out reading from pixy.  Resetting");
        }

        long msSinceLast = Duration.between(lastTimeStamp, Instant.now()).toMillis();
        active = msSinceLast > msBetweenUpdates;
        if (active) {
            if (!cmdSent) {
                sendCommand();
                timeoutTrackStart = Instant.now();
            }

            if (readFromPixy()) {
                if (evaluateDataBuffer()) {
                    reset();
                    lastTimeStamp = Instant.now();
                    if (Constants.pixyDebug) {
                        System.out.print("Pixy Data Retrieved in ");
                        System.out.print(Duration.between(timeoutTrackStart, Instant.now()).toMillis());
                        System.out.println(" ms.");
                    }
                }
            }
        }
        if (Constants.pixyOnDashboard) {
            SmartDashboard.putNumber("pixy/PwrCellXPos", blockPos); 
            SmartDashboard.putNumber("pixy/PwrCellHeight", blockHeight); 
        }
    }

    private boolean readFromPixy() {
        Instant startTime = Instant.now();
        boolean retval;
        if (serial.getBytesReceived() > 0) {
            String response = serial.readString();
            if (Constants.pixyDebug) System.out.println("Received Pixy Data:'" + response + "''");
            dataBuffer = dataBuffer + response;
            retval = true;
        } else {
            retval = false;
        }

        logMethodCalltime("readFromPixy", startTime);
        return retval;

    }

    private boolean evaluateDataBuffer() {
        Instant startTime = Instant.now();

        boolean retVal = false;

        // get the indexes of the interesting bits
        int ePos = dataBuffer.indexOf('E');
        int commaPos = dataBuffer.indexOf(',');
        int sPos = dataBuffer.indexOf('S');

        boolean lengthOk = (dataBuffer.length() >= ("S-999,999E").length());
        boolean markersFound = ((sPos != -1) && (commaPos != -1) && (ePos != -1));
        boolean orderOk = ((sPos < commaPos) && (commaPos < ePos));

        if (lengthOk && markersFound && orderOk) {
            blockPos = decodeX(dataBuffer);
            blockHeight = decodeHeight(dataBuffer);

            if (Constants.pixyDebug) {
                System.out.print("Data received from pixy: ");
                System.out.println(dataBuffer);
                System.out.print("blockPos = ");
                System.out.print(blockPos);
                System.out.print(", blockHeight = ");
                System.out.println(blockHeight);
            }
            retVal = true;
        }

        logMethodCalltime("EvaluateDataBuffer", startTime);
        return retVal;
    }

    private void sendCommand() {
        Instant startTime = Instant.now();
        cmdSent = true;
        if (serial.getBytesReceived() > 0) {
            System.out.print("Pixy data found in sendCommand and will be dropped.- ");
            System.out.println(serial.readString());
            serial.reset();
        }

        // Send 'T' if you want the LEDs turned on for sensing
        byte[] b = new byte[1];
        b[0] = (byte) (useLED ? 'T' : 'F');
        serial.write(b, 1);
        logMethodCalltime("sendCommand", startTime);
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
    }

    private void logMethodCalltime(String method, Instant lastTimeStamp) {
        if (Constants.pixyDebug) {
            System.out.print(method);
            System.out.print(" - ");
            System.out.print(Duration.between(lastTimeStamp, Instant.now()).toMillis());
            System.out.println("ms");
        }
    }

    private int decodeX(String input) {
        int sPos = input.indexOf('S');
        int commaPos = input.indexOf(',');
        int result = -999;
        try {
            result = Integer.parseInt(input.substring(sPos + 1, commaPos));
        } catch (Exception e) {
            System.out.println("pixy decodeX Error - input=" + input);
        }
        return result;
    }

    private int decodeHeight(String input) {
        int commaPos = input.indexOf(',');
        int ePos = input.indexOf('E');
        int result = 999;
        try {
            result = Integer.parseInt(input.substring(commaPos + 1, ePos));
        } catch (Exception ex) {
            System.out.println("pixy decodeHeight Error - input=" + input);
        }
        return result;
    }

    private void reset() {
        active = false;
        cmdSent = false;
        dataBuffer = "";
    }
}
