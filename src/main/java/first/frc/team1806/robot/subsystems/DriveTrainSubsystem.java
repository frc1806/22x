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
import first.frc.team1806.robot.util.CheesyDriveHelper;
import first.frc.team1806.robot.util.DriveSignal;
import first.frc.team1806.robot.util.NavX;
import first.frc.team1806.robot.util.Rotation2d;

import first.frc.team1806.robot.subsystems.VisionSubsystem;

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
    private VisionSubsystem mVisionSubsystem =VisionSubsystem.GetInstance();

    public static DriveTrainSubsystem getInstance(){
        return mDriveTrainSubsystem;
    }

    public DriveStates getDriveStates(){
        return mDriveStates;
    }

    private CANSparkMax leftLeader, rightLeader, leftFollower, rightFollower;
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
        
        leftLeader = new CANSparkMax(RobotMap.leftLeader, CANSparkMaxLowLevel.MotorType.kBrushless);
        rightLeader = new CANSparkMax(RobotMap.rightLeader, CANSparkMaxLowLevel.MotorType.kBrushless);

        leftFollower = new CANSparkMax(RobotMap.leftFollower, CANSparkMaxLowLevel.MotorType.kBrushless);
        rightFollower = new CANSparkMax(RobotMap.rightFollower, CANSparkMaxLowLevel.MotorType.kBrushless);

        leftEncoder = new Encoder(Constants.kDIODriveLeftEncoderA, Constants.kDIODriveLeftEncoderB);
        rightEncoder = new Encoder(Constants.kDIODriveRightEncoderA, Constants.kDIODriveRightEncoderB);

        navx = new NavX(SPI.Port.kMXP);

        leftFollower.follow(leftLeader);
        rightFollower.follow(rightLeader);

        leftLeader.setInverted(true);
        leftFollower.setInverted(true);
        rightLeader.setInverted(false);
        rightFollower.setInverted(false);

        leftLeader.setSmartCurrentLimit(Constants.kDriveTrainPerMotorCurrentLimit);
        leftFollower.setSmartCurrentLimit(Constants.kDriveTrainPerMotorCurrentLimit);
        rightLeader.setSmartCurrentLimit(Constants.kDriveTrainPerMotorCurrentLimit);
        rightFollower.setSmartCurrentLimit(Constants.kDriveTrainPerMotorCurrentLimit);


        mDriveStates = DriveStates.DRIVING;

        leftEncoderDistance = 0;
        rightEncoderDistance = 0;
        leftVelocity = 0;
        rightVelocity = 0;
    }

    /**
    * Turns the robot towards the goal and allows the driver to control the throttle to drive to the goal 
    * @param driveHelperToUse the {@link CheesyDriveHelper} to use for deadzones and such
    * @param throttle the driver's throttle input
    */
    public void setOpenLoopWithVision(CheesyDriveHelper driveHelperToUse, double throttle)
    {
       
        driveHelperToUse.cheesyDrive(throttle, mVisionSubsystem.getTarget() * .01, true, true);
    }


    public void setOpenLoop(DriveSignal signal){
        if (mDriveStates != DriveStates.DRIVING){
            mDriveStates = DriveStates.DRIVING;
            System.out.println("Driving");
        }
        leftLeader.setVoltage(signal.getLeft() * 12);
        rightLeader.setVoltage(signal.getRight() * 12);
    }

    public synchronized void setCreepMode(DriveSignal signal){
        if (mDriveStates != DriveStates.CREEP){
            mDriveStates = DriveStates.CREEP;
            System.out.println("Creeping");
        }
        leftLeader.setVoltage(signal.getLeft() * 3);
        rightLeader.setVoltage(signal.getRight() * 3);
    }

    public synchronized void setCoastMode(){
        leftLeader.setIdleMode(CANSparkMax.IdleMode.kCoast);
        rightLeader.setIdleMode(CANSparkMax.IdleMode.kCoast);
        leftFollower.setIdleMode(CANSparkMax.IdleMode.kCoast);
        rightFollower.setIdleMode(CANSparkMax.IdleMode.kCoast);
    }

    public synchronized void setBrakeMode(){
        leftLeader.setIdleMode(CANSparkMax.IdleMode.kBrake);
        rightLeader.setIdleMode(CANSparkMax.IdleMode.kBrake);
        leftFollower.setIdleMode(CANSparkMax.IdleMode.kBrake);
        rightFollower.setIdleMode(CANSparkMax.IdleMode.kBrake);
    }

    public synchronized void setNeutralMode(boolean brake){
        CANSparkMax.IdleMode currentMode = brake ? CANSparkMax.IdleMode.kBrake : CANSparkMax.IdleMode.kCoast;
        leftLeader.setIdleMode(currentMode);
        rightLeader.setIdleMode(currentMode);
        leftFollower.setIdleMode(currentMode);
        rightFollower.setIdleMode(currentMode);
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

        leftLeader.setVoltage(0);
        rightLeader.setVoltage(0);
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
            return leftLeader.getAppliedOutput();
        }
           
       }).withWidget(BuiltInWidgets.kNumberBar).withPosition(3,0);

       Robot.getMainCompetitionTab().addNumber("Right Drive Power", new DoubleSupplier(){

        @Override
        public double getAsDouble() {
            return rightLeader.getAppliedOutput();
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

			SmartDashboard.putNumber(Constants.kDriveTrainKey + "temp leftLeader", leftLeader.getMotorTemperature());
			SmartDashboard.putNumber(Constants.kDriveTrainKey + "temp leftA", leftFollower.getMotorTemperature());
			SmartDashboard.putNumber(Constants.kDriveTrainKey + "temp rightLeader", rightLeader.getMotorTemperature());
			SmartDashboard.putNumber(Constants.kDriveTrainKey + "temp rightA", rightFollower.getMotorTemperature());

			SmartDashboard.putNumber(Constants.kDriveTrainKey + "amps leftLeader", leftLeader.getOutputCurrent());
			SmartDashboard.putNumber(Constants.kDriveTrainKey + "amps leftA", leftFollower.getOutputCurrent());
			SmartDashboard.putNumber(Constants.kDriveTrainKey + "amps rightLeader", rightLeader.getOutputCurrent());
			SmartDashboard.putNumber(Constants.kDriveTrainKey + "amps rightA", rightFollower.getOutputCurrent());
        }
    }
}