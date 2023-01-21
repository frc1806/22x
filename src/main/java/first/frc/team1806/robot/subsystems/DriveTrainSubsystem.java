package first.frc.team1806.robot.subsystems;

import java.util.function.DoubleSupplier;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.SPI;
import first.frc.team1806.robot.Constants;
import first.frc.team1806.robot.Robot;
import first.frc.team1806.robot.RobotMap;
import first.frc.team1806.robot.RobotState;
import first.frc.team1806.robot.loop.Loop;
import first.frc.team1806.robot.loop.Looper;
import first.frc.team1806.robot.util.DriveSignal;
import first.frc.team1806.robot.util.NavX;
import first.frc.team1806.robot.util.Rotation2d;

public class DriveTrainSubsystem implements Subsystem {

    public enum DriveStates {
        DRIVING,
        CREEP,
        PATH_FOLLOWING,
        NOTHING,
    }

    boolean debug = true;

    private DriveStates mDriveStates;

    private static DriveTrainSubsystem mDriveTrainSubsystem = new DriveTrainSubsystem();

    public static DriveTrainSubsystem getInstance(){
        return mDriveTrainSubsystem;
    }

    public DriveStates getDriveStates(){
        return mDriveStates;
    }

    private CANSparkMax leaderLeft, leaderRight, followerLeft, followerRight;
    private Encoder leftEncoder, rightEncoder;
    private NavX navx;

    private RobotState mRobotState = RobotState.getInstance();

    private double leftEncoderDistance, rightEncoderDistance, leftVelocity, rightVelocity;

    private Loop mLoop = new Loop() {
        @Override
        public void onLoop(double timestamp) {
            leftEncoderDistance = leftEncoder.getDistance();
            rightEncoderDistance = rightEncoder.getDistance();
            leftVelocity = leftEncoder.getRate();
            rightVelocity = rightEncoder.getRate();

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
            setNeutralMode(false);
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

        leftEncoder = new Encoder(Constants.kDIODriveLeftEncoderA, Constants.kDIODriveLeftEncoderB);
        rightEncoder = new Encoder(Constants.kDIODriveRightEncoderA, Constants.kDIODriveRightEncoderB);

        navx = new NavX(SPI.Port.kMXP);

        followerLeft.follow(leaderLeft);
        followerRight.follow(leaderRight);

        leaderLeft.setInverted(true);
        followerLeft.setInverted(true);

        leaderRight.setInverted(false);
        followerRight.setInverted(false);

        mDriveStates = DriveStates.DRIVING;

        leftEncoderDistance = 0;
        rightEncoderDistance = 0;
        leftVelocity = 0;
        rightVelocity = 0;
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

    public double getLeftDistanceInches(){
        return leftEncoderDistance * Constants.kDriveInchesPerCount;
    }

    public double getRightDistanceInches(){
        return rightEncoderDistance * Constants.kDriveInchesPerCount;
    }

    public double getLeftVelocityInchesPerSec() {
		return leftVelocity * Constants.kDriveInchesPerCount / 60;
	}

    public double getRightVelocityInchesPerSec(){
        return rightVelocity * Constants.kDriveInchesPerCount / 60;
    }

    public String returnDriveState() {
		return mDriveStates.toString();
	}

    public synchronized Rotation2d getGyroYaw() {
		return navx.getYaw();
	}

    public synchronized void setGyroAngle(Rotation2d angle) {
		navx.zeroYaw();
		navx.setAngleAdjustment(angle);
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
    
    @Override
    public void zeroSensors(){
        leftEncoderDistance = 0;
        rightEncoderDistance = 0;
    }

    @Override
    public void outputToSmartDashboard(){
        if(debug){
            SmartDashboard.putNumber(Constants.kDriveTrainKey + "position left (in)", getLeftDistanceInches());
			SmartDashboard.putNumber(Constants.kDriveTrainKey + "position right (in)", getRightDistanceInches());
			SmartDashboard.putNumber(Constants.kDriveTrainKey + "velocity left (in/sec)",
					getLeftVelocityInchesPerSec());
			SmartDashboard.putNumber(Constants.kDriveTrainKey + "velocity right (in/sec)",
					getRightVelocityInchesPerSec());
			SmartDashboard.putNumber(Constants.kDriveTrainKey + "encoder count left", leftEncoderDistance);
			SmartDashboard.putNumber(Constants.kDriveTrainKey + "encoder count right", rightEncoderDistance);

			SmartDashboard.putString(Constants.kDriveTrainKey + "drive state", returnDriveState());
			SmartDashboard.putNumber(Constants.kDriveTrainKey + "navX yaw", getGyroYaw().getDegrees());

			SmartDashboard.putNumber(Constants.kDriveTrainKey + "temp leaderLeft", leaderLeft.getMotorTemperature());
			SmartDashboard.putNumber(Constants.kDriveTrainKey + "temp leftA", followerLeft.getMotorTemperature());
			SmartDashboard.putNumber(Constants.kDriveTrainKey + "temp leaderRight", leaderRight.getMotorTemperature());
			SmartDashboard.putNumber(Constants.kDriveTrainKey + "temp rightA", followerRight.getMotorTemperature());

			SmartDashboard.putNumber(Constants.kDriveTrainKey + "amps leaderLeft", leaderLeft.getOutputCurrent());
			SmartDashboard.putNumber(Constants.kDriveTrainKey + "amps leftA", followerLeft.getOutputCurrent());
			SmartDashboard.putNumber(Constants.kDriveTrainKey + "amps leaderRight", leaderRight.getOutputCurrent());
			SmartDashboard.putNumber(Constants.kDriveTrainKey + "amps rightA", followerRight.getOutputCurrent());
        }
    }
}
