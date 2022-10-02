package first.frc.team1806.robot.auto.modes;

import edu.wpi.first.math.geometry.Pose2d;
import first.frc.team1806.robot.auto.AutoModeEndedException;

public class DoNothingMode extends AutoModeBase {

    @Override
    protected void routine() throws AutoModeEndedException {
        System.out.println("Doing Nothing");
    }

    @Override
    public Pose2d getStartingPose() {
        return new Pose2d();
    }
    
}
