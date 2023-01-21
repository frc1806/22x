package first.frc.team1806.robot.subsystems;

import first.frc.team1806.robot.loop.Looper;

public interface Subsystem {

    public abstract void stop();

    public abstract void setupDriverTab();

    public abstract void outputToSmartDashboard();

    public abstract void registerEnabledLoops(Looper enabledLooper);

    public abstract void zeroSensors();
}
