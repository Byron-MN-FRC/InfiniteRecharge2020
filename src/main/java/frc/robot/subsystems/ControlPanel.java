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

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants;
import frc.robot.ControlPanelConstants;

// END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS
import java.util.HashMap;
import java.util.Map;
//import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.revrobotics.ColorMatch;
import com.revrobotics.ColorMatchResult;
import com.revrobotics.ColorSensorV3;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.util.Color;

/**
 *
 */
public class ControlPanel extends Subsystem {

  // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTANTS

  // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTANTS
  private final I2C.Port i2cPort = I2C.Port.kOnboard;
  private boolean colorSensorStatus = false;
  /**
   * A Rev Color Sensor V3 object is constructed with an I2C port as a parameter.
   * The device will be automatically initialized with default parameters.
   */
  private final ColorSensorV3 m_colorSensor = new ColorSensorV3(i2cPort);

  /**
   * A Rev Color Match object is used to register and detect known colors. This
   * can be calibrated ahead of time or during operation.
   * 
   * This object uses a simple euclidian distance to estimate the closest match
   * with given confidence range.
   */
  private final ColorMatch m_colorMatcher = new ColorMatch();

  /**
   * Note: Any example colors should be calibrated as the user needs, these are
   * here as a basic example.
   */
  private final Color kBlueTarget = ColorMatch.makeColor(0.143, 0.427, 0.429);
  private final Color kGreenTarget = ColorMatch.makeColor(0.197, 0.561, 0.240);
  private final Color kRedTarget = ColorMatch.makeColor(0.561, 0.232, 0.114);
  private final Color kYellowTarget = ColorMatch.makeColor(0.361, 0.524, 0.113);

  public int colorCount = 0;
  public double numRotations = 0;
  public String prevColorStr = "Unknown";
  public String startingColorStr = "Unknown";
  public String readColorStr = "Unknown";
  public Color readColor = null;
  public Map<String, Integer> colorMap = new HashMap<String, Integer>() {
    private static final long serialVersionUID = 1L;

    {
      put("Yellow", 0);
      put("Blue", 0);
      put("Green", 0);
      put("Red", 0);
    }
  };
  // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
  private WPI_TalonSRX spinMotor;
  private DoubleSolenoid colorSolenoid;

  // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS

  public ControlPanel() {
    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
    spinMotor = new WPI_TalonSRX(6);

    colorSolenoid = new DoubleSolenoid(10, 3, 1);
    addChild("colorSolenoid", colorSolenoid);

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
    m_colorMatcher.addColorMatch(kBlueTarget);
    m_colorMatcher.addColorMatch(kGreenTarget);
    m_colorMatcher.addColorMatch(kRedTarget);
    m_colorMatcher.addColorMatch(kYellowTarget);

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
    // Put code here to be run every loop

  }

  // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CMDPIDGETTERS

  // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CMDPIDGETTERS

  // Put methods for controlling this subsystem
  // here. Call these from Commands.

  public void spin(double motorSpeed) {
    if (colorSensorStatus == true) {
      spinMotor.set(motorSpeed);	   
    } else {
      toConsoleln("Color sensor disengaged - no spining");  
    }	  
  }

  public void spinCount() {   
    if (colorSensorStatus == true) {
      spinMotor.set(.6);	   
    } else {
      toConsoleln("Color sensor disengaged - no spining");  
    }	
  }  

  public void stop() {
    spinMotor.set(0);
  }

  public boolean colormatch(String gameData) {
    /**
     * The method GetColor() returns a normalized color value from the sensor and
     * can be useful if outputting the color to an RGB LED or similar. To read the
     * raw color, use GetRawColor().
     * 
     * The color sensor works best when within a few inches from an object in well
     * lit conditions (the built in LED is a big help here!). The farther an object
     * is the more light from the surroundings will bleed into the measurements and
     * make it difficult to accurately determine its color.
     */
    Color detectedColor = m_colorSensor.getColor();
    //String rgb = m_colorSensor.getRawColor().toString();

    /**
     * Run the color match algorithm on our detected color
     */
    ColorMatchResult match = m_colorMatcher.matchClosestColor(detectedColor);
    String colorString = getColorString(match.color);
    if (Constants.testControl) {
      SmartDashboard.putString("test/control/Game Color", gameData);
      SmartDashboard.putString("test/control/Color Detected", colorString);
      SmartDashboard.putNumber("test/control/Color Confidence", match.confidence);
    }

    switch (gameData.charAt(0)) {
    case 'B':
      // Blue case code
      if (match.color == kBlueTarget) {
        if (Constants.testControl) SmartDashboard.putString("test/control/Color seen", "Blue");
        return validColor(getColorString(match.color));
      }
      break;
    case 'G':
      // Green case code
      if (match.color == kGreenTarget) {
        if (Constants.testControl) SmartDashboard.putString("test/control/Color seen", "Green");
        return validColor(getColorString(match.color));
      }
      break;

    case 'R':
      // Red case code
      if (match.color == kRedTarget) {
        if (Constants.testControl) SmartDashboard.putString("test/control/Color seen", "Red");
        return validColor(getColorString(match.color));
      }
      break;

    case 'Y':
      // Yellow case code
      if (match.color == kYellowTarget) {
        if (Constants.testControl) SmartDashboard.putString("test/control/Color seen", "Yellow");
        return validColor(getColorString(match.color));
      }
      break;
    default:
      // This is corrupt data
      break;
    }

    return false;
  }

  public void setStartingPoint(Double rotations) {
    numRotations = rotations;
    colorCount = 0;
    readColor = m_colorSensor.getColor();
    startingColorStr = getColorString(readColor);
    prevColorStr = "Unknown";

  }

  public boolean goalreached() {
    return (colorCount / 2) > numRotations;
  }

  public void colorCounter() {
    // /**
    // * Method used to count how many times the color sensor reads a color Set up
    // to
    // * count when the starting color matches the current color being read, the
    // * current color is equal to Rcolor When Rcolor is equal to previous color do
    // * nothing when Rcolor is equal to starting color, count one rotation color
    // * count is less than or equal to 7
    // */
    readColorStr = getColorString(m_colorSensor.getColor());
    if (!readColorStr.equals("Unknown") && validColor(readColorStr)) {
      if (!readColorStr.equals(prevColorStr)) {
        if (readColorStr.equals(startingColorStr)) {
          colorCount = colorCount + 1;
          // System.out.println("Verified Color");
        }
        // colorCount = colorCount + 1;
      }
      prevColorStr = readColorStr;
    }
  }

  // readColorStr = getColorString
  // comments from Lauren
  private String getColorString(Color detectedColor) {
    ColorMatchResult match = m_colorMatcher.matchClosestColor(detectedColor);
    String colorString;
    if (match.color == kBlueTarget) {
      colorString = "Blue";
    } else if (match.color == kRedTarget) {
      colorString = "Red";
    } else if (match.color == kGreenTarget) {
      colorString = "Green";
    } else if (match.color == kYellowTarget) {
      colorString = "Yellow";
    } else {
      colorString = "Unknown";
    }
    return colorString;
  }

  // motor config taken from MotionMagic example at
  // https://github.com/CrossTheRoadElec/Phoenix-Examples-Languages
  public void motorConfig() {
    /* Factory default hardware to prevent unexpected behavior */
    spinMotor.configFactoryDefault();

    /* Configure Sensor Source for Pirmary PID */
    spinMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, Constants.kPIDLoopIdx,
        Constants.kTimeoutMs);

    /**
     * Configure Talon SRX Output and Sesnor direction accordingly Invert Motor to
     * have green LEDs when driving Talon Forward / Requesting Postiive Output Phase
     * sensor to have positive increment when driving Talon Forward (Green LED)
     */
    spinMotor.setSensorPhase(false); // required to stop
    spinMotor.setInverted(false);

    /* Set relevant frame periods to be at least as fast as periodic rate */
    spinMotor.setStatusFramePeriod(StatusFrameEnhanced.Status_13_Base_PIDF0, 10, Constants.kTimeoutMs);
    spinMotor.setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, 10, Constants.kTimeoutMs);

    /* Set the peak and nominal outputs */
    spinMotor.configNominalOutputForward(0, Constants.kTimeoutMs);
    spinMotor.configNominalOutputReverse(0, Constants.kTimeoutMs);
    spinMotor.configPeakOutputForward(1, Constants.kTimeoutMs);
    spinMotor.configPeakOutputReverse(-1, Constants.kTimeoutMs);

    /* Set Motion Magic gains in slot0 - see documentation */
    spinMotor.selectProfileSlot(Constants.kSlotIdx, Constants.kPIDLoopIdx);
    spinMotor.config_kF(Constants.kSlotIdx, Constants.kGains.kF, Constants.kTimeoutMs);
    spinMotor.config_kP(Constants.kSlotIdx, Constants.kGains.kP, Constants.kTimeoutMs);
    spinMotor.config_kI(Constants.kSlotIdx, Constants.kGains.kI, Constants.kTimeoutMs);
    spinMotor.config_kD(Constants.kSlotIdx, Constants.kGains.kD, Constants.kTimeoutMs);
    /* Set acceleration and vcruise velocity - see documentation */
    spinMotor.configMotionCruiseVelocity(15000, Constants.kTimeoutMs);
    spinMotor.configMotionAcceleration(6000, Constants.kTimeoutMs);

    /* Zero the sensor */
    spinMotor.setSelectedSensorPosition(0, Constants.kPIDLoopIdx, Constants.kTimeoutMs);
  }

  public void spinMotorToEncoder() {

    if (colorSensorStatus == true) {
      spinMotor.set(ControlMode.MotionMagic, Constants.kTargetEncoderUnits);	   
    } else {
      toConsoleln("Color sensor disengaged - no spining to encoders");  
    }	  
  }

  //
  public Boolean targetEncoderUnits() {
    double currentEncoderUnits = spinMotor.getSelectedSensorPosition(Constants.kPIDLoopIdx);
    if (Math.abs(currentEncoderUnits - Constants.kTargetEncoderUnits) < 3500) {
      return true;
    }
    return false;

  }

  public Boolean validColor(String colorSeen) {
    Integer count = colorMap.get(colorSeen);
    if (count > 4) {
      System.out.println("Seeing:" + colorSeen + " count:" + count);
      clearColorMap();
      return true;
    }
    colorMap.put(colorSeen, ++count);
    return false;
  }

  // Clear color tracking information - starting over with new color
  // or is initial color looking for
  public void clearColorMap() {
    for (Map.Entry<String, Integer> entry : colorMap.entrySet())
      colorMap.put(entry.getKey(), 0);
  }

  public void retractColorSensor() {
    colorSolenoid.set(Value.kReverse);
    colorSensorStatus = false;
  }

  public void raiseColorSensor() {
    colorSolenoid.set(Value.kForward);
    colorSensorStatus = true;
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
      if (ControlPanelConstants.debug) {
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
  // private void toConsole(final String s) {
  //     if (ControlPanelConstants.debug) {
  //         System.out.print(s);
  //     }
  // }
  public void reinitializeControlPanel() {
    toConsoleln("reinitializeControlPanel() called.");
    colorSolenoid.set(Value.kReverse);
    colorSensorStatus = false;
    motorConfig();
    
  }
}
