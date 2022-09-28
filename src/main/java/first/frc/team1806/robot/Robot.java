package first.frc.team1806.robot;

import java.util.HashMap;
import java.util.Map;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;

public class Robot extends TimedRobot {
  private static ShuffleboardTab CompetitionTab;
  private OI mOI;

  @Override
  public void robotInit() {
    CompetitionTab = Shuffleboard.getTab("Main Competition Tab");
    SetupMainCompetitionTab();
    mOI = new OI();
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
    mOI.runCommands();
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

  private static Map<String, Object> Delay = new HashMap<>();

  static {
    Delay.put("Min", 0.0d);
    Delay.put("Max", 15.0d);
    Delay.put("Block Increment", 0.01d);
  }

  private void SetupMainCompetitionTab(){
    CompetitionTab.addCamera("Front Camera", "FrontCamera", "None").withPosition(4,1);
    CompetitionTab.addPersistent("Auto Delay", 0).withProperties(Delay)
      .withWidget(BuiltInWidgets.kNumberSlider).withSize(2, 1).withPosition(0, 0).getEntry();
  }

  public static ShuffleboardTab GetMainCompetitionTab(){
    return CompetitionTab;
  }
}
