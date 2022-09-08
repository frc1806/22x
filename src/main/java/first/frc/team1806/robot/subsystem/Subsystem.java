package first.frc.team1806.robot.subsystem;

public interface Subsystem {
    public void writeToLog();

    public abstract void stop();

    public abstract void zeroSensors();

    public abstract void setDebug(boolean _debug);

    public abstract void retractAll();

    public abstract void setupDriverTab();
}
