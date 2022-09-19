package first.frc.team1806.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import first.frc.team1806.robot.subsystem.DriveTrainSubsystem;
import first.frc.team1806.robot.util.XboxController;

public class Robot extends TimedRobot {

  private DriveTrainSubsystem mDriveTrainSubsystem = DriveTrainSubsystem.getInstance();
  private DifferentialDrive DriveTrain = mDriveTrainSubsystem.GetDrive();
  private XboxController DriverController = OI.GetDriverController();

  @Override
  public void robotInit() {
    ShuffleboardTab CompetitionTab = Shuffleboard.getTab("Main Competition Tab");

  }


  @Override
  public void robotPeriodic() {}


  @Override
  public void autonomousInit() {
    
  }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {

  }

  /** This function is called once when teleop is enabled. */
  @Override
  public void teleopInit() {
    
  }

  /** This function is called periodically during operator control. */
  @Override
  public void teleopPeriodic() {
    DriveTrain.curvatureDrive(DriverController.getLeftJoyY(), DriverController.getRightJoyX(), DriverController.getButtonB());
  }

  /** This function is called once when the robot is disabled. */
  @Override
  public void disabledInit() {}

  /** This function is called periodically when disabled. */
  @Override
  public void disabledPeriodic() {}

  /** This function is called once when test mode is enabled. */
  @Override
  public void testInit() {}

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {}

  /** This function is called once when the robot is first started up. */
  @Override
  public void simulationInit() {}

  /** This function is called periodically whilst in simulation. */
  @Override
  public void simulationPeriodic() {}

  private void SetupMainCompetitionTab(){
    
  }
}
