package first.frc.team1806.robot.subsystem;

import java.util.List;

public class SubsystemManager {

    private final List<Subsystem> mAllSubsystems;

    public SubsystemManager(List<Subsystem> allSubsystems) {
        mAllSubsystems = allSubsystems;
    }

    public void outputToSmartDashboard() {
        mAllSubsystems.forEach((s) -> s.outputToSmartDashboard());
    }

    public void writeToLog() {
        mAllSubsystems.forEach((s) -> s.writeToLog());
    }

    public void stop() {
        mAllSubsystems.forEach((s) -> s.stop());
    }

    public void zeroSensors() {
        mAllSubsystems.forEach((s) -> s.zeroSensors());
    }

    public void retractAll() { mAllSubsystems.forEach((s) -> s.retractAll());
    
    }
}