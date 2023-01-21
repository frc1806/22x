package first.frc.team1806.robot.subsystems;

import java.util.List;

import first.frc.team1806.robot.loop.Looper;

public class SubsystemManager {

    private final List<Subsystem> mAllSubsystems;

    public SubsystemManager(List<Subsystem> allSubsystems) {
        mAllSubsystems = allSubsystems;
    }

    public void stop() {
        mAllSubsystems.forEach((s) -> s.stop());
    }

    public void registerEnabledLoops(Looper enabledLooper){
        mAllSubsystems.forEach((s) -> s.registerEnabledLoops(enabledLooper));
    }

    public void setupDriverTabs(){
        mAllSubsystems.forEach((s) -> s.setupDriverTab());
    }

    public void outputToSmartDashboard(){
        mAllSubsystems.forEach((s) -> s.outputToSmartDashboard());
    }

    public void zeroSensors(){
        mAllSubsystems.forEach((s) -> s.zeroSensors());
    }
}