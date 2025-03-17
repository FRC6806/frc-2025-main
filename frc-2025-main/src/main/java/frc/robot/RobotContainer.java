
// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import static edu.wpi.first.units.Units.*;

import com.ctre.phoenix6.swerve.SwerveModule.DriveRequestType;
import com.ctre.phoenix6.CANBus;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.swerve.SwerveRequest;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.auto.NamedCommands;
import com.pathplanner.lib.commands.PathPlannerAuto;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.POVButton;
import edu.wpi.first.wpilibj2.command.sysid.SysIdRoutine.Direction;
import frc.robot.commands.MotorCommand;
import frc.robot.commands.Alignment;
import frc.robot.generated.TunerConstants;
import frc.robot.subsystems.AlgaeIntake;
import frc.robot.subsystems.CommandSwerveDrivetrain;
import frc.robot.subsystems.MotorTesting;
import frc.robot.subsystems.Values;
import frc.robot.subsystems.VisionSystem;
import frc.robot.subsystems.PoleAlignment;
import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.CoralIntake;

public class RobotContainer {
    private final CANBus canbus = new CANBus("elavator");
    private final TalonFX m3 = new TalonFX(0,canbus);

    private final VisionSystem s_Vision = new VisionSystem("photonvision");
    public final PoleAlignment align = new PoleAlignment (s_Vision); 
    private Elevator elevator= new Elevator(54,51);

    private double MaxSpeed = TunerConstants.kSpeedAt12Volts.in(MetersPerSecond); // kSpeedAt12Volts desired top speed
    private double MaxAngularRate = RotationsPerSecond.of(0.75).in(RadiansPerSecond); // 3/4 of a max rad/s

    private CoralIntake coralScore;
    private AlgaeIntake algaeScore;

    private final SwerveRequest.FieldCentric drive = new SwerveRequest.FieldCentric()
            .withDeadband(MaxSpeed * 0.15).withRotationalDeadband(MaxAngularRate * 0.15) // Add a 10% deadband
            .withDriveRequestType(DriveRequestType.OpenLoopVoltage); // Use open-loop control for drive motors
    private final SwerveRequest.SwerveDriveBrake brake = new SwerveRequest.SwerveDriveBrake();
    private final SwerveRequest.PointWheelsAt point = new SwerveRequest.PointWheelsAt();
    //private final SwerveRequest.RobotCentric forwardStraight = new SwerveRequest.RobotCentric()
    //        .withDriveRequestType(DriveRequestType.OpenLoopVoltage);
    //private final SwerveRequest.RobotCentric drive_robotcentric = new SwerveRequest.RobotCentric();
    private final Telemetry logger = new Telemetry(MaxSpeed);
    private final CommandXboxController joystick = new CommandXboxController(0);
    private final XboxController operator = new XboxController(1);
    public final CommandSwerveDrivetrain drivetrain = TunerConstants.createDrivetrain();
    private final SendableChooser<Command> autoChooser;

    public RobotContainer() {
        // AUTOCOMMANDS
        // Subsystem initialization
        coralScore = new CoralIntake(55,52,60, canbus); //80 is the algae motor but we dont know it is
        algaeScore = new AlgaeIntake(62, 61);

        // Register Named Commands
        //NamedCommands.registerCommand("CoralScore", new InstantCommand(() -> coralScore.CoralIntakeSpeed(-1)));
        

        autoChooser = AutoBuilder.buildAutoChooser("SimpleAuto");
        SmartDashboard.putData("Auto Mode", autoChooser);
        configureBindings();


    }

    private void configureBindings() {
      //  joystick.a().whileTrue(alignment);
        // Note that X is defined as forward according to WPILib convention,
        // and Y is defined as to the left according to WPILib convention.

        
        drivetrain.setDefaultCommand(
            // Drivetrain will execute this command periodically
            drivetrain.applyRequest(() ->
                drive.withVelocityX((joystick.getLeftY() * ((-1*joystick.getRawAxis(3))+1) /2) * MaxSpeed) // Drive forward with negative Y (forward)
                    .withVelocityY((joystick.getLeftX()  * ((-1*joystick.getRawAxis(3))+1) /2) * MaxSpeed) // Drive left with negative X (left)
                    .withRotationalRate(-(joystick.getRawAxis(2) * ((-1*joystick.getRawAxis(3))+1) /2)* MaxAngularRate) // Drive counterclockwise with negative X (left)

            )
        );

        joystick.a().whileTrue(drivetrain.applyRequest(() -> brake));
        joystick.b().whileTrue(drivetrain.applyRequest(() ->
            point.withModuleDirection(new Rotation2d(-joystick.getLeftY(), -joystick.getLeftX()))
        ));

       
        // Run SysId routines when holding back/start and X/Y.
        // Note that each routine should be run exactly once in a single log.
        joystick.back().and(joystick.y()).whileTrue(drivetrain.sysIdDynamic(Direction.kForward));
        joystick.back().and(joystick.x()).whileTrue(drivetrain.sysIdDynamic(Direction.kReverse));
        joystick.start().and(joystick.y()).whileTrue(drivetrain.sysIdQuasistatic(Direction.kForward));
        joystick.start().and(joystick.x()).whileTrue(drivetrain.sysIdQuasistatic(Direction.kReverse));

        // reset the field-centric heading on left bumper press
        //joystick.leftBumper().onTrue(drivetrain.runOnce(() -> drivetrain.seedFieldCentric()));
        
        drivetrain.registerTelemetry(logger::telemeterize);

        
    JoystickButton operatorA = new JoystickButton(operator, XboxController.Button.kA.value);
    JoystickButton operatorB = new JoystickButton(operator, XboxController.Button.kB.value);
    //operatorA.whileTrue(new InstantCommand(()-> coralIntake.wristpose(110)));
    //operatorA.whileTrue(new InstantCommand(()-> coralIntake.CoralIntakeSpeed(1)));
    //operatorA.onFalse(new InstantCommand(()-> coralIntake.CoralIntakeSpeed(0)));
    //operatorA.whileTrue(new InstantCommand(()-> elevator.rotateMotor(-15)));
   
    //operatorA.whileTrue(new InstantCommand(()-> elevator.setElevatorspeed(-.25)));
    //operatorA.onFalse(new InstantCommand(()-> elevator.setElevatorspeed(0)));
    //operatorB.whileTrue(new InstantCommand(()-> elevator.setElevatorspeed(.25)));

    operatorA.whileTrue(new InstantCommand(()-> coralScore.CoralIntakeSpeed(.2)));
    operatorA.onFalse(new InstantCommand(()-> coralScore.CoralIntakeSpeed(0)));
    operatorB.whileTrue(new InstantCommand(()-> coralScore.CoralIntakeSpeed(-.2)));

    operatorA.whileTrue(new InstantCommand(() -> algaeScore.AlgaeIntakeSpeed(.2)));
    operatorA.onFalse(new InstantCommand(()-> algaeScore.AlgaeIntakeSpeed(0)));
    operatorB.whileTrue(new InstantCommand(() -> algaeScore.AlgaeIntakeSpeed(.2)));


    //operatorB.whileTrue(new InstantCommand(()-> coralIntake.wristpose(214)));
    // operatorB.whileTrue(new InstantCommand(()-> coralIntake.CoralIntakeSpeed(-1)));
    // operatorB.onFalse(new InstantCommand(()-> coralIntake.CoralIntakeSpeed(0)));
    //operatorB.whileTrue(new InstantCommand(()-> elevator.rotateMotor(-5)));
    

     POVButton dPadUp = new POVButton(operator,0);
     dPadUp.whileTrue(new InstantCommand(()-> Values.increaseLevel()));
    
     POVButton dPadDown = new POVButton(operator,180);
     dPadDown.whileTrue(new InstantCommand(()-> Values.decreaseLevel()));

     POVButton dPadLeft = new POVButton(operator,270);
     dPadLeft.whileTrue(new InstantCommand(()-> Values.setLeftOrRight(false)));

    POVButton dPadRight = new POVButton(operator,90);
     dPadRight.whileTrue(new InstantCommand(()-> Values.setLeftOrRight(true)));

    

    }

    public void postToSmartDashboard(){
        SmartDashboard.putNumber("Rotation P", 100);
        SmartDashboard.putNumber("Rotation D", 0.5);
        SmartDashboard.putNumber("level", Values.getLevel());
        SmartDashboard.putString("direction", Values.getLeftOrRight());  
        SmartDashboard.putNumber("level", Values.getLevel());
        SmartDashboard.putString("direction", Values.getLeftOrRight());
        SmartDashboard.putNumber("yaw", s_Vision.getYaw());
    }


    public Command getAutonomousCommand() {
        /* Run the path selected from the auto chooser */
        return null;
       //return new SequentialCommandGroup( new PathPlannerAuto("SimpleAuto") , motor2.withTimeout(.5 ));
    }
}
