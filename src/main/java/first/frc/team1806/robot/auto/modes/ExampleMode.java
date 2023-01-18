package first.frc.team1806.robot.auto.modes;

import com.pathplanner.lib.PathConstraints;
import com.pathplanner.lib.PathPlanner;
import com.pathplanner.lib.PathPlannerTrajectory;

import edu.wpi.first.math.geometry.Pose2d;
import first.frc.team1806.robot.auto.AutoModeEndedException;

public class ExampleMode extends AutoModeBase {

    @Override
    protected void routine() throws AutoModeEndedException {
        PathPlannerTrajectory example_trajectory = PathPlanner.loadPath("New Path", new PathConstraints(4,4)); 
        
    }

    @Override
    public Pose2d getStartingPose() {
        // TODO Auto-generated method stub
        return null;
    }
    
}
