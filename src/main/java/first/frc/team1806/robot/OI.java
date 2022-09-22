package first.frc.team1806.robot;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import first.frc.team1806.robot.util.XboxController;

public class OI {
    private static XboxController DriverController = new XboxController(Constants.kDriverControllerPort, "Driver", Constants.kDriverControllerDefaultConfig);
    private static XboxController OperatorController = new XboxController(Constants.kOperatorControllerPort, "Operator", Constants.kOperatorControllerDefaultConfig);

    private SendableChooser<DriverControls> controllerConfigChooser;

    public static XboxController GetDriverController(){
        return DriverController;
    }

    public static XboxController GetOperatorController(){
        return OperatorController;
    }

	private enum DriverControls {
        kForza,
        kClassic,
    }

    DriverControls mDriverControls;

    public OI(){
        controllerConfigChooser = new SendableChooser<DriverControls>();
        controllerConfigChooser.addOption("Forza", DriverControls.kForza);
        controllerConfigChooser.addOption("Classic", DriverControls.kClassic);
        Robot.GetMainCompetitionTab().add("Driver Controls", controllerConfigChooser).withPosition(2,2);
    }

    public void runCommands(){
        //Operator Controls
        //None

        //Driver Controls
        double throttle;
        double turn;

        switch (mDriverControls) {
            case kClassic:
                throttle = DriverController.getLeftJoyY();
                turn = DriverController.getRightJoyX();
                break;
            case kForza:
                return;
        }
    }
}