package first.frc.team1806.robot.subsystem;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;

import first.frc.team1806.robot.OI;
import first.frc.team1806.robot.RobotMap;
import first.frc.team1806.robot.util.XboxController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
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
    private DifferentialDrive DriveTrain;
    private XboxController mDriverController;

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

        DriveTrain = new DifferentialDrive(GroupLeft, GroupRight);
        DriveTrain.setSafetyEnabled(true);

        mDriveStates = DriveStates.DRIVING;
        mDriverController = OI.GetDriverController();
    }

    public String getVarsInString(){
        return leaderLeft.toString() + leaderRight.toString() + followerLeft.toString() + followerRight.toString() + GroupLeft.toString() + GroupRight.toString() + DriveTrain.toString();
    }

    @Override
    public void writeToLog() {
        // TODO Auto-generated method stub
        
    }

    public void runDrive() {
        DriveTrain.curvatureDrive(mDriverController.getLeftJoyY(), mDriverController.getRightJoyX(), mDriverController.getButtonB());
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
