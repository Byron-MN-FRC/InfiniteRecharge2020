package frc.robot;

public class ThrottleLookup {

        // LowGearRamp
        public static double[][] correctionTable1 = {
        {.02, .25, .500, .75, 1.00},
        {.000, .15, .35, .6, 1.00}};



        // HighGearRamp
        public static double[][] correctionTable2 = {
        {.02, .25, .50, .75, 1.0},
        {.000, .10, .35, .55, .65}};

    	// LowGearTurn
		public static double[][] correctionTable3 = {
			{.02, .25, .5, .75, 1.0},
			{.000, .15, .3, .45, 0.6}};

		// HighGearTurn
		public static double[][] correctionTable4 = {
			{.02, .25, .50, .75, 1.00},
			{.000, .15, .35, .50, 0.60}};



        public static double calcJoystickCorrection(String tableName, double x)
        {
            double[][] correctionTable;
            
            switch (tableName)
            {
                case "LowGearRamp" : correctionTable = correctionTable1;
                    break;
                case "HighGearRamp" : correctionTable = correctionTable2;
                    break;
                case "LowGearTurn": correctionTable = correctionTable3;
				    break;
			    case "HighGearTurn": correctionTable = correctionTable4;
				    break;
                default : correctionTable = correctionTable1;
            }
            boolean isNegative = x < 0;
            
            x = Math.abs(x);
            
            int pos = 0; 
            double returnValue;
            
            while ((pos < 5) && (x > correctionTable[0][pos]))
            {
                pos++;
            }
            
            if (pos < 5)
            {
                if (pos != 0)
                {
                    double y1 = correctionTable[1][pos];
                    double y2 = correctionTable[1][pos-1];
                    double x1 = correctionTable[0][pos];
                    double x2 = correctionTable[0][pos-1];
                    returnValue = y1 + ((y1-y2)/(x1-x2)) * (x-x1);
                }
                else
                {
                    return 0.0;
                }
            }
            else
            {
                return 1.0;
            }
            
            if (isNegative)
                returnValue = -returnValue;
            
            return returnValue;
        }

}

