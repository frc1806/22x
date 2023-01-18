package first.frc.team1806.robot.subsystems;

import java.util.function.DoubleSupplier;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;

import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import first.frc.team1806.robot.Robot;
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
            setOpenLoop(DriveSignal.NEUTRAL);
            
        }

        @Override
        public void onStop(double timestamp) {
            setOpenLoop(DriveSignal.NEUTRAL);
            
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
            System.out.println("Driving");
        }
        leaderLeft.setVoltage(signal.getLeft() * 12);
        leaderRight.setVoltage(signal.getRight() * 12);
    }

    public synchronized void setCreepMode(DriveSignal signal){
        if (mDriveStates != DriveStates.CREEP){
            mDriveStates = DriveStates.CREEP;
            System.out.println("Creeping");
        }
        leaderLeft.setVoltage(signal.getLeft() / 2);
        leaderRight.setVoltage(signal.getRight() / 2);
    }

    public synchronized void setCoastMode(){
        leaderLeft.setIdleMode(CANSparkMax.IdleMode.kCoast);
        leaderRight.setIdleMode(CANSparkMax.IdleMode.kCoast);
        followerLeft.setIdleMode(CANSparkMax.IdleMode.kCoast);
        followerRight.setIdleMode(CANSparkMax.IdleMode.kCoast);
    }

    public synchronized void setBrakeMode(){
        leaderLeft.setIdleMode(CANSparkMax.IdleMode.kBrake);
        leaderRight.setIdleMode(CANSparkMax.IdleMode.kBrake);
        followerLeft.setIdleMode(CANSparkMax.IdleMode.kBrake);
        followerRight.setIdleMode(CANSparkMax.IdleMode.kBrake);
    }

    public synchronized void setNeutralMode(boolean brake){
        CANSparkMax.IdleMode currentMode = brake ? CANSparkMax.IdleMode.kBrake : CANSparkMax.IdleMode.kCoast;
        leaderLeft.setIdleMode(currentMode);
        leaderRight.setIdleMode(currentMode);
        followerLeft.setIdleMode(currentMode);
        followerRight.setIdleMode(currentMode);
    }

    public boolean isDriving(){
        return mDriveStates == DriveStates.DRIVING;
    }

    public boolean isCreeping(){
        return mDriveStates == DriveStates.CREEP;
    }

    public boolean isStopped(){
        return mDriveStates == DriveStates.NOTHING;
    }

    private synchronized void stopDrive() {
        if (mDriveStates != DriveStates.DRIVING){
            mDriveStates = DriveStates.DRIVING;
        }

        leaderLeft.setVoltage(0);
        leaderRight.setVoltage(0);
    }
    
    @Override
    public void stop() {
        stopDrive();
    }


    @Override
    public void setupDriverTab() {
       Robot.getMainCompetitionTab().addNumber("Left Drive Power", new DoubleSupplier() {

        @Override
        public double getAsDouble() {
            return leaderLeft.getAppliedOutput();
        }
           
       }).withWidget(BuiltInWidgets.kNumberBar).withPosition(3,0);

       Robot.getMainCompetitionTab().addNumber("Right Drive Power", new DoubleSupplier(){

        @Override
        public double getAsDouble() {
            return leaderRight.getAppliedOutput();
        }
           
       }).withWidget(BuiltInWidgets.kNumberBar).withPosition(3,1);
        
    }

    @Override
    public void registerEnabledLoops(Looper enabledLooper) {
        enabledLooper.register(mLoop);
    }
    
}
