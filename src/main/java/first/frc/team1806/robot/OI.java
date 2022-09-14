package first.frc.team1806.robot;

import edu.wpi.first.wpilibj.XboxController;

public class OI {
    protected static XboxController DriverController = new XboxController(Constants.kDriverControllerPort);
    protected static XboxController OperatorController = new XboxController(Constants.kOperatorControllerPort);

    public static XboxController GetDriverController(){
        return DriverController;
    }

    public static XboxController GetOperatorController(){
        return OperatorController;
    }
}
