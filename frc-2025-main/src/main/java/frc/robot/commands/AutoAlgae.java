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

 
public class AutoAlgae extends SequentialCommandGroup { 
    private CoralIntake s_CoralIntake;   
    private Elevator s_Elevator;
    private VisionSystem s_Vision;
    private CommandSwerveDrivetrain s_swerve; 
    
    public AutoAlgae(Elevator elevator, CoralIntake coralIntake, VisionSystem vision, CommandSwerveDrivetrain swerve){
        this.s_Elevator = elevator;
        this.s_CoralIntake =coralIntake;
        this.s_Vision =vision;
        this.s_swerve =swerve;

        
   
        addCommands(
            new InstantCommand(() -> s_CoralIntake.wristpose(30)),
            new WaitCommand(1),
            new InstantCommand(()-> elevator.setPose(-5))
            //new InstantCommand(() -> s_CoralIntake.AlgaeIntakeSpeed(1.5)) //changed to increased speed

            // new Alignment(s_swerve, s_Vision),
            // new InstantCommand(() -> s_CoralIntake.wristpose(CoralIntake.CORAL_SCORE)),
            //new WaitCommand(1),
            //new InstantCommand(() -> s_Elevator.setPose(0)),
            // new WaitCommand(1),
            // new InstantCommand(() -> s_CoralIntake.wristpose(15)),
            // new WaitCommand(1),
            // new InstantCommand(() -> s_CoralIntake.AlgaeIntakeSpeed(1.5)), //changed to increased speed
            // new WaitCommand(2),
            // new InstantCommand(() -> s_CoralIntake.AlgaeIntakeSpeed(0)), //new, to change speed
            // //new InstantCommand(() -> s_Elevator.setPose(0)),
            // new InstantCommand(() -> s_CoralIntake.wristpose(17)), //new, to get out of the coral lock
            // new WaitCommand(1), // added
            // new InstantCommand(() -> s_CoralIntake.AlgaeIntakeSpeed(1.5)), //new, to change speed
            // new WaitCommand(2), //added
            // new InstantCommand(() -> s_CoralIntake.AlgaeIntakeSpeed(0)),
            // new WaitCommand(1)
            // new InstantCommand(() -> s_Elevator.startPose()), //removed for not being good
            // new WaitCommand(1)
        );
    }

}