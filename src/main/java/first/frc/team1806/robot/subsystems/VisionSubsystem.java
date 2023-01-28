package first.frc.team1806.robot.subsystems;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

import first.frc.team1806.robot.loop.Loop;
import first.frc.team1806.robot.loop.Looper;


import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import first.frc.team1806.robot.Constants;
import first.frc.team1806.robot.Robot;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.WidgetType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class VisionSubsystem implements Subsystem {

    private NetworkTable limelightTable;
    private double currentTimestamp;

    public VisionSubsystem() { 
        limelightTable = NetworkTableInstance.getDefault().getTable("limelight");
        currentTimestamp = 0.0;
    }

    public double getValidTargetUpdateTimestamp(){
        return limelightTable.getEntry("tv").getLastChange();
    }

    public boolean hasLimelightUpdatedRecently(){
        return getValidTargetUpdateTimestamp()/ 1000000 > currentTimestamp - 1.0;
    }


    public double getAprilTagId(){
        return limelightTable.getEntry("tid").getDouble(0);
    }

    public double getTarget(){
        return limelightTable.getEntry("tx").getDouble(0);
    }


    @Override
    public void stop() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void setupDriverTab() {

               
        Robot.getMainCompetitionTab().addNumber("April Tag ID", new DoubleSupplier() {

            @Override
            public double getAsDouble() {
                return getAprilTagId();
            }
               
           }).withWidget(BuiltInWidgets.kNumberBar).withPosition(3,0).withSize(1, 1);

           Robot.getMainCompetitionTab().addNumber("Yaw Value", new DoubleSupplier() {

            @Override
            public double getAsDouble() {
                return getTarget();
            }
               
           }).withWidget(BuiltInWidgets.kNumberBar).withPosition(3,2).withSize(1, 1);

           Robot.getMainCompetitionTab().addBoolean("Limelight Connection", new BooleanSupplier() {

            @Override
            public boolean getAsBoolean() {
                // TODO Auto-generated method stub
                return hasLimelightUpdatedRecently();
            }
            
           }).withWidget(BuiltInWidgets.kBooleanBox).withPosition(5, 4).withSize(1, 1);
     


    }

    @Override
    public void outputToSmartDashboard() {

        SmartDashboard.putBoolean("Limelight Connection", hasLimelightUpdatedRecently());
    }



    @Override
    public void registerEnabledLoops(Looper enabledLooper) {
        enabledLooper.register(new Loop() {

            @Override
            public void onStart(double timestamp) {
                // TODO Auto-generated method stub
                currentTimestamp = timestamp;
            }

            @Override
            public void onLoop(double timestamp) {
                // TODO Auto-generated method stub
                currentTimestamp = timestamp;
            }

            @Override
            public void onStop(double timestamp) {
                // TODO Auto-generated method stub
                currentTimestamp = timestamp;
            }
            
        });        
    }

    @Override
    public void zeroSensors() {
        // TODO Auto-generated method stub
        
    }
    
}
