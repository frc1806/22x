package first.frc.team1806.robot;

import first.frc.team1806.robot.util.XboxController;

public class OI {
    private static XboxController DriverController = new XboxController(Constants.kDriverControllerPort, "Driver", Constants.kDriverControllerDefaultConfig);
    private static XboxController OperatorController = new XboxController(Constants.kOperatorControllerPort, "Operator", Constants.kOperatorControllerDefaultConfig);

    public static XboxController GetDriverController(){
        return DriverController;
    }

    public static XboxController GetOperatorController(){
        return OperatorController;
    }
}
