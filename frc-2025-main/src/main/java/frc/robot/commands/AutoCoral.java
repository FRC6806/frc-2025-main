package frc.robot.commands;
import frc.robot.Constants;
import frc.robot.Constants;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.CommandSwerveDrivetrain;
import frc.robot.subsystems.CoralIntake;
import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.VisionSystem;

 
public class AutoCoral extends SequentialCommandGroup { 
   private CoralIntake s_CoralIntake;   
    private Elevator s_Elevator;
    private VisionSystem s_Vision;
    private CommandSwerveDrivetrain s_swerve;
    private double position;
    
    public AutoCoral(Elevator elevator, CoralIntake coralIntake, VisionSystem vision, CommandSwerveDrivetrain swerve, double position){
        this.s_Elevator = elevator;
        this.s_CoralIntake =coralIntake;
        this.s_Vision =vision;
        this.s_swerve =swerve;
        this.position = position; 
   
        addCommands(
            new InstantCommand(() -> s_CoralIntake.CoralIntakeSpeed(1.0)),
            new WaitCommand(2),
            new InstantCommand(() -> s_CoralIntake.wristpose(CoralIntake.MOVE_ELEVATOR)),
            new WaitCommand(1),
            // new Alignment(s_swerve, s_Vision),
            // new InstantCommand(() -> s_CoralIntake.wristpose(CoralIntake.CORAL_SCORE)),
            new WaitCommand(1),
            new InstantCommand(() -> s_Elevator.setPose(-1)),
            new WaitCommand(1),
            new InstantCommand(() -> s_CoralIntake.CoralIntakeSpeed(-1.0)),
            new WaitCommand(3),
            new InstantCommand(() -> s_CoralIntake.CoralIntakeSpeed(0)),
            new WaitCommand(1),
            new InstantCommand(() -> s_Elevator.startPose()),
            new WaitCommand(1)
        );
    }

}
    

