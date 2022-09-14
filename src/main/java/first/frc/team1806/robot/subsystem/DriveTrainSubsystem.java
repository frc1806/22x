package first.frc.team1806.robot.subsystem;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;

import first.frc.team1806.robot.OI;
import first.frc.team1806.robot.RobotMap;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

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

    public static DriveTrainSubsystem getInstance(){
        return mDriveTrainSubsystem;
    }

    private CANSparkMax leaderLeft, leaderRight, followerLeft, followerRight; 
    private MotorControllerGroup GroupLeft;
    private MotorControllerGroup GroupRight;
    private DifferentialDrive DriveTrain;

    private XboxController mDriverController;

    private DriveStates mDriveStates;

    public DriveTrainSubsystem(){
        leaderLeft = new CANSparkMax(RobotMap.leftLeader, CANSparkMaxLowLevel.MotorType.kBrushless);
        leaderRight = new CANSparkMax(RobotMap.rightLeader, CANSparkMaxLowLevel.MotorType.kBrushless);

        followerLeft = new CANSparkMax(RobotMap.leftFollower, CANSparkMaxLowLevel.MotorType.kBrushless);
        followerRight = new CANSparkMax(RobotMap.rightFollower, CANSparkMaxLowLevel.MotorType.kBrushless);

        GroupLeft = new MotorControllerGroup(leaderLeft, followerLeft);
        GroupRight = new MotorControllerGroup(leaderRight, followerRight);

        DriveTrain = new DifferentialDrive(GroupLeft, GroupRight);
        mDriverController = OI.GetDriverController();
    }

    @Override
    public void writeToLog() {
        // TODO Auto-generated method stub
        
    }

    public void ControlDrive() {
        DriveTrain.curvatureDrive(mDriverController.getLeftY(), mDriverController.getLeftX(), mDriverController.getBButton());
    }

    public synchronized void StopDrive() {
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
