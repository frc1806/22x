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
}
