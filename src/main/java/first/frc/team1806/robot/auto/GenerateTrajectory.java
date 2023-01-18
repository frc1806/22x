package first.frc.team1806.robot.auto;

import java.io.IOException;
import java.nio.file.Path;

import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryConfig;
import edu.wpi.first.math.trajectory.TrajectoryGenerator;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Filesystem;

public class GenerateTrajectory {
    public static Trajectory generateTrajectoryFromFile(String filepath, TrajectoryConfig config){
        try {
            Path traj_path = Filesystem.getDeployDirectory().toPath().resolve(filepath);
            TrajectoryGenerator.ControlVectorList control_vectors = WaypointReader.getControlVectors(traj_path);
            
            return TrajectoryGenerator.generateTrajectory(control_vectors, config);
        } catch (IOException ex) {
                DriverStation.reportError("Unable to open trajectory: " + filepath, ex.getStackTrace());
                return null;
            } 
        }
    }
