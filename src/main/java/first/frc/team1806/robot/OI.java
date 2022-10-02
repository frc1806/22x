package first.frc.team1806.robot;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import first.frc.team1806.robot.subsystems.DriveTrainSubsystem;
import first.frc.team1806.robot.util.CheesyDriveHelper;
import first.frc.team1806.robot.util.XboxController;

public class OI {
    private static XboxController DriverController = new XboxController(Constants.kDriverControllerPort, "Driver", Constants.kDriverControllerDefaultConfig);
    private static XboxController OperatorController = new XboxController(Constants.kOperatorControllerPort, "Operator", Constants.kOperatorControllerDefaultConfig);

    private DriveTrainSubsystem mDriveTrainSubsystem;
    private CheesyDriveHelper mCheesyDriveHelper;

    private SendableChooser<DriverControls> controllerConfigChooser;

    public static XboxController GetDriverController(){
        return DriverController;
    }

    public static XboxController GetOperatorController(){
        return OperatorController;
    }

	private enum DriverControls {
        kForza(
            new DoubleSupplier() {

                @Override
                public double getAsDouble() {
                    // throttle
                    return DriverController.getConfigValues().getRightYMinimumOutput();
                }
            }, new DoubleSupplier() {

                @Override
                public double getAsDouble() {
                    // wheel
                    return DriverController.getConfigValues().getLeftXMinimumOutput();
                }
            }),
        kClassic(
            new DoubleSupplier() {

                @Override
                public double getAsDouble() {
                    // throttle
                    return DriverController.getConfigValues().getRightYMinimumOutput();
                }
            }, new DoubleSupplier() {

                @Override
                public double getAsDouble() {
                    // wheel
                    return DriverController.getConfigValues().getLeftXMinimumOutput();
                }
            });

            DoubleSupplier throttleDeadBandGetter, wheelDeadBandGetter;

            private DriverControls(DoubleSupplier throttleDeadbandGetter, DoubleSupplier wheelDeadbandGetter) {
                this.throttleDeadBandGetter = throttleDeadbandGetter;
                this.wheelDeadBandGetter = wheelDeadbandGetter;
            }
    
            public double getThrottleDeadBand() {
                return throttleDeadBandGetter.getAsDouble();
            }
    
            public double getWheelDeadBand() {
                return wheelDeadBandGetter.getAsDouble();
            }
    
            public CheesyDriveHelper getCheesyDriveHelper() {
                return new CheesyDriveHelper(getWheelDeadBand(), getThrottleDeadBand());
            }
    }

    public OI(){
        controllerConfigChooser = new SendableChooser<DriverControls>();
        controllerConfigChooser.addOption("Forza", DriverControls.kForza);
        controllerConfigChooser.addOption("Classic", DriverControls.kClassic);
        Robot.GetMainCompetitionTab().add("Driver Controls", controllerConfigChooser).withPosition(2,2);

        mDriveTrainSubsystem = DriveTrainSubsystem.getInstance();
        mCheesyDriveHelper = DriverControls.kForza.getCheesyDriveHelper();
    }
    
    public void runCommands(){
        DriverControls currentControllerConfig;

        if(controllerConfigChooser.getSelected() != null)
		{
			currentControllerConfig = controllerConfigChooser.getSelected();
			mCheesyDriveHelper = controllerConfigChooser.getSelected().getCheesyDriveHelper();
        }
		else
		{
			currentControllerConfig = DriverControls.kClassic;
		}
        //Operator Controls
        //None

        //Driver Controls
        boolean quickTurn;
        double throttle;
        double turn;

        switch (currentControllerConfig) {
            case kForza:
                return;
            default:
            case kClassic:
                throttle = DriverController.getLeftJoyY();
                turn = DriverController.getRightJoyX();
                quickTurn = DriverController.getRightTrigger() > 0;
                break;
        }

        mDriveTrainSubsystem.setOpenLoop(mCheesyDriveHelper.cheesyDrive(throttle, turn, quickTurn, false));
    }
}