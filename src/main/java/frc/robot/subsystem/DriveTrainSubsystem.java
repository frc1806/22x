package frc.robot.subsystem;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.robot.RobotMap;

public class DriveTrainSubsystem implements Subsystem {

    public enum DriveStates {
        DRIVING,
        CREEP,
        VISION,
        PATH_FOLLOWING,
        PARKING_BRAKE,
        NOTHING,
    }

    private static DriveTrainSubsystem mDriveTrainSubsystem = new DriveTrainSubsystem();

    private CANSparkMax leaderLeft, leaderRight, followerLeft, followerRight;
    private DriveStates mDriveStates;
    private DifferentialDrive DriveTrain = new DifferentialDrive(leaderLeft, leaderRight);

    public static DriveTrainSubsystem getInstance() {
        return mDriveTrainSubsystem;
    }

    public void setDriveTrain(){

    }

    public DriveTrainSubsystem(){
        leaderLeft = new CANSparkMax(RobotMap.leftLeader, CANSparkMaxLowLevel.MotorType.kBrushless);
        leaderRight = new CANSparkMax(RobotMap.rightLeader, CANSparkMaxLowLevel.MotorType.kBrushless);

        followerLeft = new CANSparkMax(RobotMap.leftFollower, CANSparkMaxLowLevel.MotorType.kBrushless);
        followerRight = new CANSparkMax(RobotMap.rightFollower, CANSparkMaxLowLevel.MotorType.kBrushless);
    }

    @Override
    public void writeToLog() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void stop() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void zeroSensors() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void setDebug(boolean _debug) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void retractAll() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void setupDriverTab() {
        // TODO Auto-generated method stub
        
    }
    
}
