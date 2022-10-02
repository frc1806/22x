package first.frc.team1806.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;

import first.frc.team1806.robot.RobotMap;
import first.frc.team1806.robot.loop.Loop;
import first.frc.team1806.robot.loop.Looper;
import first.frc.team1806.robot.util.DriveSignal;

public class DriveTrainSubsystem implements Subsystem {

    public enum DriveStates {
        DRIVING,
        CREEP,
        PATH_FOLLOWING,
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

    private CANSparkMax leaderLeft, leaderRight, followerLeft, followerRight;
    private Loop mLoop = new Loop() {
        @Override
        public void onLoop(double timestamp) {
            synchronized (DriveTrainSubsystem.this) {
                switch (mDriveStates) {
                    case DRIVING:
                        return;
                    case CREEP:
                        return;
                    case NOTHING:
                        return;
                    case PATH_FOLLOWING:
                        // TODO (when path follower gets added)
                        return;
                }
            }
            
        }

        @Override
        public void onStart(double timestamp) {
            // TODO Auto-generated method stub
            
        }

        @Override
        public void onStop(double timestamp) {
            // TODO Auto-generated method stub
            
        }
        
    };

    public DriveTrainSubsystem(){
        
        leaderLeft = new CANSparkMax(RobotMap.leftLeader, CANSparkMaxLowLevel.MotorType.kBrushless);
        leaderRight = new CANSparkMax(RobotMap.rightLeader, CANSparkMaxLowLevel.MotorType.kBrushless);

        followerLeft = new CANSparkMax(RobotMap.leftFollower, CANSparkMaxLowLevel.MotorType.kBrushless);
        followerRight = new CANSparkMax(RobotMap.rightFollower, CANSparkMaxLowLevel.MotorType.kBrushless);

        followerLeft.follow(leaderLeft);
        followerRight.follow(leaderRight);

        leaderLeft.setInverted(true);
        followerLeft.setInverted(true);

        leaderRight.setInverted(false);
        followerRight.setInverted(false);

        mDriveStates = DriveStates.DRIVING;
    }

    public void setOpenLoop(DriveSignal signal){
        if (mDriveStates != DriveStates.DRIVING){
            mDriveStates = DriveStates.DRIVING;
        }
        leaderLeft.setVoltage(signal.getLeft() * 12);
        leaderRight.setVoltage(signal.getRight() * 12);
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
    public void setupDriverTab() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void registerEnabledLoops(Looper enabledLooper) {
        enabledLooper.register(mLoop);
    }
    
}
