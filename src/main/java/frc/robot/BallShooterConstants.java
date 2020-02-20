/**
 * Simple class containing constants used throughout project
 */
package frc.robot;
import java.util.TreeMap;

public class BallShooterConstants {
	//hood  Constants 
	public final static double kHoodUpEncoderUnits = 2000;//-3500;//4000
	public final static double kHoodPositionTolerance = 190;
	
	// Shoot motor Constants
	public final static double kShootMotorRPMTolerance = 50;//rpms
	public static final boolean kEnableCurrentLimiting_BS = true;

	// Current (amp) limit
	public static final double currentLimit = 20;

	// Threshold that must be exceeded for current limiting to occur
	public static final double thresholdLimit = 30;

	// How long the current has to be above the threshold to trigger limiting
	public static final double thresholdTime = 0;



    public static final int kPIDLoopIdx = 0; // Check how it is done with talon

    /**
	 * Set to zero to skip waiting for confirmation. Set to nonzero to wait and
	 * report to DS if action fails.
	 */
	public final static int kTimeoutMs = 30;

	/*
	 * Firmware currently supports slots [0, 3] and can be used for either PID Set
	 */
	public final static int SLOT_0 = 0;
	public final static int SLOT_1 = 1;
	public final static int SLOT_2 = 2;
	public final static int SLOT_3 = 3;
	/* ---- Named slots, used to clarify code ---- */
	public final static int kSlot_Distanc = SLOT_0;
	public final static int kSlot_Turning = SLOT_1;
	public final static int kSlot_Velocit = SLOT_2;
	public final static int kSlot_MotProf = SLOT_3;

    /*
	 * Gains(kp, ki, kd, kf, izone, peak output);
	 */
	public static final Gains kGains_hoodMotor = new Gains(0.2, 0.001, 0.0, 0.2, 0, 1.0);
	public static final Gains kGains_shootMotor = new Gains(0.1, 0.001, 0.0, 0.49, 0, 1.0);


	//
	public static final TreeMap<Integer, double[]> targetPercent2ShooterParms = new TreeMap<Integer, double[]>() {
		private static final long serialVersionUID = 1L;
		{	   //target							Hood
			   //percentage				  RPMs	Encoder
			put( 198, new double[] { -1000, 3000 });
			put( 129, new double[] { -3500, 3500 });
			put( 78, new double[] { -5000, 4000 });

		}};
	
	
	
	/**
	 * How many sensor units per rotation. Using Talon FX Integrated Encoder.
	 * 
	 * @link https://github.com/CrossTheRoadElec/Phoenix-Documentation#what-are-the-units-of-my-sensor
	 */
	public final static int kSensorUnitsPerRotation = 2048;
		//example usage:
	/*public static void main(String[] args) throws Exception {
		double ta = 150;
		Integer key = (int)ta;
		if (key < 0 || key >= 100) {
			//percentage out of range
		}
		else {
			System.out.println("RPMS: " + targetPercent2ShooterParms.floorEntry(key).getValue()[0]);
			System.out.println("HE: " + targetPercent2ShooterParms.floorEntry(key).getValue()[1]);
		}
		}*/
	public static final boolean debug = true;

	}

