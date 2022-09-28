package first.frc.team1806.robot.subsystem;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;

import first.frc.team1806.robot.RobotMap;
import first.frc.team1806.robot.util.DriveSignal;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;

public class DriveTrainSubsystem implements Subsystem {

    public enum DriveStates {
        DRIVING,
        CREEP,
        VISION,
        PATH_FOLLOWING,
        PARKING_BRAKE,
        NOTHING,
    }

    private DriveStates mDriveStates;

    private static DriveTrainSubsystem mDriveTrainSubsystem = new DriveTrainSubsystem();

    public static DriveTrainSubsystem getInstance(){
        return mDriveTrainSubsystem;
    }

    public DriveStates getDriveStates(){
        return mDriveStates;
    }

    private CANSparkMax leaderLeft;
    private CANSparkMax leaderRight;
    private CANSparkMax followerLeft;
    private CANSparkMax followerRight; 
    private MotorControllerGroup GroupLeft;
    private MotorControllerGroup GroupRight;

    public DriveTrainSubsystem(){
        
        leaderLeft = new CANSparkMax(RobotMap.leftLeader, CANSparkMaxLowLevel.MotorType.kBrushless);
        leaderRight = new CANSparkMax(RobotMap.rightLeader, CANSparkMaxLowLevel.MotorType.kBrushless);

        followerLeft = new CANSparkMax(RobotMap.leftFollower, CANSparkMaxLowLevel.MotorType.kBrushless);
        followerRight = new CANSparkMax(RobotMap.rightFollower, CANSparkMaxLowLevel.MotorType.kBrushless);

        followerLeft.follow(leaderLeft);
        followerRight.follow(leaderRight);

        GroupLeft = new MotorControllerGroup(leaderLeft, followerLeft);
        GroupRight = new MotorControllerGroup(leaderRight, followerRight);

        GroupLeft.setInverted(true);
        GroupRight.setInverted(false);

        mDriveStates = DriveStates.DRIVING;
    }

    public void setOpenLoop(DriveSignal signal){
        leaderLeft.setVoltage(signal.getLeft() * 12);
        leaderRight.setVoltage(signal.getRight() * 12);
    }

    @Override
    public void writeToLog() {
        // TODO Auto-generated method stub
        
    }

    private synchronized void StopDrive() {
        if (mDriveStates != DriveStates.NOTHING){
            mDriveStates = DriveStates.NOTHING;
        }

        leaderLeft.setVoltage(0);
        leaderRight.setVoltage(0);
    }
    
    @Override
    public void stop() {
        StopDrive();
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

    @Override
    public void outputToSmartDashboard() {
        
    }
    
}
