package first.frc.team1806.robot;

import first.frc.team1806.robot.util.XboxControllerConfigValues;

public class Constants {

    public static final XboxControllerConfigValues kDriverControllerDefaultConfig = XboxControllerConfigValues.Builder
        .create().withLeftXDeadzone(.12).withLeftXMinimumOutput(.04).withLeftXLinearity(.32).withLeftYDeadzone(.12)
        .withLeftYMinimumOutput(.04).withLeftYLinearity(.32).withRightXDeadzone(.12).withRightXMinimumOutput(.04)
        .withRightXLinearity(.32).withRightYDeadzone(.12).withRightYMinimumOutput(.04).withRightYLinearity(.32)
        .withTriggerDeadzone(.2).withTriggerMinimumOutput(.04).withTriggerLinearity(.32)
        .withTriggerAsDigitalDeadzone(.5).build();

    public static final XboxControllerConfigValues kOperatorControllerDefaultConfig = XboxControllerConfigValues.Builder
        .create().withLeftXDeadzone(.12).withLeftXMinimumOutput(.04).withLeftXLinearity(.32).withLeftYDeadzone(.12)
        .withLeftYMinimumOutput(.04).withLeftYLinearity(.32).withRightXDeadzone(.12).withRightXMinimumOutput(.04)
        .withRightXLinearity(.32).withRightYDeadzone(.12).withRightYMinimumOutput(.04).withRightYLinearity(.32)
        .withTriggerDeadzone(.2).withTriggerMinimumOutput(.04).withTriggerLinearity(.32)
        .withTriggerAsDigitalDeadzone(.5).build();

    public final static int kDriverControllerPort = 0;
    public final static int kOperatorControllerPort = 1;

    public final static double kLooperDt = 0.005;

    public final static String kCompressorKey = "Compressor ";
    public final static String kDriveTrainKey = "DriveTrain ";
    public final static String kRobotStateKey = "robot";

    public final static boolean enableAutoInTeleOp = false;
    public final static boolean enableDebugMode = true;
    public final static double kDriveWheelDiameterInches = 4;
    public final static double kTrackWidthInches = 27.5;
    public final static double kTrackScrubFactor = .978;

}
