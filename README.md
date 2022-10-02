# 22x
Team 1806's 2022 Offseason Project

22x is a Robot built by the students of S.W.A.T. over the course of the summer and fall where our design choices and programming was mainly up to the students. Most of the problem solving, design, programming, and fabrication were all up to the students, receiving minimal help from the mentors. The goal of the project was to create a basic robot to be able to learn your specific skill.

## Code
22x and just like a lot of our previous robots are based off the `TimedRobot` and each mechanism of the robot is organised in its own `Subystem`.

### Notable Files
  * `Robot.java`
  * `OI.java`: OI is short for Operator Interface and it holds all of the controls for 22x
  * `/subsystems/`:
    * `DriveTrainSubsystem.java`: Our one and only subsystem for 22x which is for our Drive Train. This robot uses a 6 wheel West Coast Drive.
  * `/util/`
    * `XboxController.java`: This is our custom XboxController class where we are able to tune our inputs using a Shuffleboard frontend which can be found in `/util/XboxControllerConfigDashboard.java`
    * `CheesyDriveHelper.java`: This is a helper class made by Team 254 Cheesy Poofs which inspired WPILib to make CurvurtureDrive. We use CheesyDrive over CurvurtureDrive because it is more flexible allowing us to configure more than CurvurtureDrive will let us.
  * `/auto/`: WIP/Coming Soon
 
### References
WIP/Coming Soon
