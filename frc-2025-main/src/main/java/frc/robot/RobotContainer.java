
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
import edu.wpi.first.wpilibj.XboxController.Button;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.POVButton;
import edu.wpi.first.wpilibj2.command.sysid.SysIdRoutine.Direction;
import frc.robot.commands.ScoreCoral;
import frc.robot.commands.AlgaeIntake;
import frc.robot.commands.AlgaeScore;
import frc.robot.commands.Alignment;
import frc.robot.commands.HighCoralScore;
import frc.robot.commands.HumanPlayer;
import frc.robot.generated.TunerConstants;
import frc.robot.subsystems.Climb;
import frc.robot.subsystems.CommandSwerveDrivetrain;
import frc.robot.subsystems.Values;
import frc.robot.subsystems.VisionSystem;
import frc.robot.subsystems.PoleAlignment;
import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.CoralIntake;

import frc.robot.commands.HumanPlayer;
import frc.robot.commands.AutoCoral;


public class RobotContainer {

    private final VisionSystem s_Vision = new VisionSystem("photonvision");
    public final PoleAlignment align = new PoleAlignment (s_Vision); 
    private double MaxSpeed = TunerConstants.kSpeedAt12Volts.in(MetersPerSecond); // kSpeedAt12Volts desired top speed
    private double MaxAngularRate = RotationsPerSecond.of(0.5).in(RadiansPerSecond); // 3/4 of a max rad/s

    private CoralIntake coralScore;
    private Elevator elevator;
    private Climb climb;
    
    

    private final SwerveRequest.FieldCentric drive = new SwerveRequest.FieldCentric()
            .withDeadband(MaxSpeed * 0.05).withRotationalDeadband(MaxAngularRate * 0) // turning has no deadband and driver is lower
            .withDriveRequestType(DriveRequestType.OpenLoopVoltage); // Use open-loop control for drive motors
    private final SwerveRequest.SwerveDriveBrake brake = new SwerveRequest.SwerveDriveBrake();
    private final SwerveRequest.PointWheelsAt point = new SwerveRequest.PointWheelsAt();
    private final Telemetry logger = new Telemetry(MaxSpeed);
    private final CommandXboxController joystick = new CommandXboxController(0);
    private final XboxController operator = new XboxController(1);
    public final CommandSwerveDrivetrain drivetrain = TunerConstants.createDrivetrain();
    private final SendableChooser<Command> autoChooser;
    private final HighCoralScore highCoralScore;
    private final HumanPlayer humanPlayer;
    private final AlgaeIntake algaeIntake;
    private final AlgaeScore algaeScore;
    private final AutoCoral autoCoral;
    //private final Alignment alignment;
 

    public RobotContainer() {
        // Subsystem initialization
        coralScore = new CoralIntake(55,52,60);
        elevator = new Elevator(54,51);
        climb = new Climb(53);

        //alignment = new Alignment(drivetrain, s_Vision, MaxAngularRate);
        //NamedCommands.registerCommand("alignment", alignment);

        highCoralScore = new HighCoralScore(elevator, coralScore, s_Vision, drivetrain);
        NamedCommands.registerCommand("highCoral", highCoralScore);

        humanPlayer = new HumanPlayer(elevator, coralScore, s_Vision, drivetrain);
        NamedCommands.registerCommand("humanPlayer", humanPlayer);

        algaeIntake = new AlgaeIntake(elevator, coralScore, s_Vision, drivetrain);
        NamedCommands.registerCommand("algaeIntake", algaeIntake);

        algaeScore = new AlgaeScore(elevator, coralScore, s_Vision, drivetrain);
        NamedCommands.registerCommand("algaeScore", algaeScore);

        autoCoral = new AutoCoral(elevator, coralScore, s_Vision, drivetrain, 0);
        NamedCommands.registerCommand("autoCoral", autoCoral);

        autoChooser = AutoBuilder.buildAutoChooser("SimpleAuto");
        SmartDashboard.putData("Auto Mode", autoChooser);
        configureBindings();
    }

    private void configureBindings() {
        // Note that X is defined as forward according to WPILib convention,
        // and Y is defined as to the left according to WPILib convention.
        drivetrain.setDefaultCommand(
            // Drivetrain will execute this command periodically
            drivetrain.applyRequest(() ->
                drive.withVelocityX((-joystick.getLeftY() * ((-1*joystick.getRawAxis(3))+1) /2) * MaxSpeed) // Drive forward with negative Y (forward)
                    .withVelocityY((-joystick.getLeftX()  * ((-1*joystick.getRawAxis(3))+1) /2) * MaxSpeed) // Drive left with negative X (left)
                    .withRotationalRate(-(joystick.getRawAxis(2) * ((-1*joystick.getRawAxis(3))+1) /2)* MaxAngularRate) // Drive counterclockwise with negative X (left)

            )
        );

        //joystick.a().whileTrue(drivetrain.applyRequest(() -> brake));
        //joystick.b().whileTrue(drivetrain.applyRequest(() ->
         //   point.withModuleDirection(new Rotation2d(-joystick.getLeftY(), -joystick.getLeftX()))
        //));

       
        // Run SysId routines when holding back/start and X/Y.
        // Note that each routine should be run exactly once in a single log.
        joystick.back().and(joystick.y()).whileTrue(drivetrain.sysIdDynamic(Direction.kForward));
        joystick.back().and(joystick.x()).whileTrue(drivetrain.sysIdDynamic(Direction.kReverse));
        joystick.start().and(joystick.y()).whileTrue(drivetrain.sysIdQuasistatic(Direction.kForward));
        joystick.start().and(joystick.x()).whileTrue(drivetrain.sysIdQuasistatic(Direction.kReverse));
        drivetrain.registerTelemetry(logger::telemeterize);

        
    JoystickButton operatorA = new JoystickButton(operator, XboxController.Button.kA.value);
    JoystickButton operatorB = new JoystickButton(operator, XboxController.Button.kB.value);
    JoystickButton operatorX = new JoystickButton(operator, XboxController.Button.kX.value);
    JoystickButton operatorY = new JoystickButton(operator, XboxController.Button.kY.value);
    
    JoystickButton operatorL1 = new JoystickButton(operator, XboxController.Button.kLeftBumper.value);
    JoystickButton operatorL2 = new JoystickButton(operator, XboxController.Button.kLeftStick.value);
    JoystickButton operatorR1 = new JoystickButton(operator, XboxController.Button.kRightBumper.value);
    JoystickButton operatorR2 = new JoystickButton(operator, XboxController.Button.kRightStick.value);
    
    POVButton dPadUp = new POVButton(operator,0);
    POVButton dPadRight = new POVButton(operator,90);
    POVButton dPadDown = new POVButton(operator,180);
    POVButton dPadLeft = new POVButton(operator,270);

    JoystickButton thumbutton = new JoystickButton(joystick.getHID(), 2); //put intake on the real later 
    JoystickButton drivertrigger = new JoystickButton(joystick.getHID(), 1); //put score on the real later 
    JoystickButton driver12Button = new JoystickButton(joystick.getHID(), 12); //put score on the real later 
    JoystickButton driver11Button = new JoystickButton(joystick.getHID(), 11); //put score on the real later 
    JoystickButton driver10Button = new JoystickButton(joystick.getHID(), 10); //put score on the real later 
    JoystickButton driver9Button = new JoystickButton(joystick.getHID(), 9); 
    JoystickButton driver8Button = new JoystickButton(joystick.getHID(), 8); //put score on the real later 
    JoystickButton driver7Button = new JoystickButton(joystick.getHID(), 7); 

    //not used buttons
    // operatorL1.whileTrue(new InstantCommand(()-> coralScore.wristspeed(-1.0))); //changed to while since there isn't a speed 0 function
    // operatorL2.whileTrue(new InstantCommand(()-> coralScore.wristspeed(1.0)));
    operatorR1.whileTrue(new InstantCommand(()-> elevator.setElevatorspeed(-1.0)));
    operatorR2.whileTrue(new InstantCommand(()-> elevator.setElevatorspeed(1.0)));
    operatorA.onTrue(new InstantCommand(() -> coralScore.getWrist()));
    //CLIMBBBBBBBB

    //ALIGNMENT PITCH TEST
    //operatorB.whileTrue(alignment);


    driver12Button.whileTrue(new InstantCommand(()-> coralScore.wristspeed(.3)));  
    driver12Button.whileFalse(new InstantCommand(()-> coralScore.wristspeed(.01))); 

    driver11Button.whileTrue(new InstantCommand(()-> coralScore.wristspeed(-.3))); 
    driver11Button.whileFalse(new InstantCommand(()-> coralScore.wristspeed(.01))); 

    driver9Button.onTrue(new InstantCommand(()-> coralScore.wristpose(90)));
    driver10Button.onTrue(new InstantCommand(()-> coralScore.wristpose(50))); 

    driver7Button.whileTrue(new InstantCommand(()-> coralScore.CoralIntakeSpeed(1))); 
    driver7Button.whileFalse(new InstantCommand(()-> coralScore.CoralIntakeSpeed(0.1)));

    driver8Button.whileTrue(new InstantCommand(()-> coralScore.CoralIntakeSpeed(-1))); 
    driver8Button.whileFalse(new InstantCommand(()-> coralScore.CoralIntakeSpeed(.1)));

    // driver12Button.onFalse(new InstantCommand(()-> climb.climbSpeed(0))); 
    // driver11Button.onFalse(new InstantCommand(()-> climb.climbSpeed(0)));
    

    // thumbutton.onTrue(new InstantCommand(()-> elevator.setPose(-4)));
    // thumbutton.whileTrue(new InstantCommand(()->coralScore.AlgaeIntakeSpeed(1))); //set this up for you Nicole :)
    // thumbutton.whileFalse(new InstantCommand(()->coralScore.AlgaeIntakeSpeed(0)));
    //thumbutton.onTrue(algaeIntake);

    drivertrigger.onTrue(new ScoreCoral(elevator, coralScore, s_Vision, drivetrain, Values.getLeftOrRight()));

    operatorX.onTrue(new InstantCommand(()-> elevator.startPose()));
    operatorX.onTrue(new InstantCommand(() -> coralScore.wristpose(0)));
    // operatorX.whileTrue(new InstantCommand(()->coralScore.AlgaeIntakeSpeed(1))); 
    // operatorX.whileFalse(new InstantCommand(()->coralScore.AlgaeIntakeSpeed(.1))); 

    // operatorY.whileTrue(new InstantCommand(()->coralScore.AlgaeIntakeSpeed(-1)));
    // operatorY.whileFalse(new InstantCommand(()->coralScore.AlgaeIntakeSpeed(.1)));

    // operatorA.whileTrue(new InstantCommand(()->coralScore.AlgaeIntakeSpeed(1)));
    // operatorA.whileFalse(new InstantCommand(()->coralScore.AlgaeIntakeSpeed(.1)));

    // operatorB.whileTrue(algaeScore);

    dPadUp.onTrue(new InstantCommand(()-> Values.increaseLevel())); //changed while true to ontrue
    dPadRight.onTrue(new InstantCommand(()-> Values.setLeftOrRight(true)));
    dPadDown.onTrue(new InstantCommand(()-> Values.decreaseLevel()));
    dPadLeft.onTrue(new InstantCommand(()-> Values.setLeftOrRight(false)));
    
    }

    public void postToSmartDashboard(){
        SmartDashboard.putNumber("Rotation P", 100);
        SmartDashboard.putNumber("Rotation D", 0.5);
        SmartDashboard.putNumber("level", Values.getLevel());
        SmartDashboard.putNumber("yaw", s_Vision.getYaw());

    }


    public Command getAutonomousCommand() {
        return (new PathPlannerAuto("maybe score"));
       //return new SequentialCommandGroup( new PathPlannerAuto("SimpleAuto") , motor2.withTimeout(.5 ));
    }
}
