package first.frc.team1806.robot.subsystems;

import java.util.List;

public class SubsystemManager {

    private final List<Subsystem> mAllSubsystems;

    public SubsystemManager(List<Subsystem> allSubsystems) {
        mAllSubsystems = allSubsystems;
    }

    public void stop() {
        mAllSubsystems.forEach((s) -> s.stop());
    }

    public void registerEnabledLoops(){
        mAllSubsystems.forEach((s) -> s.registerEnabledLoops());
    }

    public void setupDriverTab(){
        mAllSubsystems.forEach((s) -> s.setupDriverTab());
    }
}