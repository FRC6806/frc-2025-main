package frc.robot.commands;
import frc.robot.Constants;
import frc.robot.Constants;

import com.pathplanner.lib.auto.NamedCommands;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.CommandSwerveDrivetrain;
import frc.robot.subsystems.CoralIntake;
import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.VisionSystem;
import frc.robot.subsystems.Values;
import edu.wpi.first.wpilibj2.command.Command;
 
public class AutoAlgae extends SequentialCommandGroup { 
    private CoralIntake s_CoralIntake;   
    private Elevator s_Elevator;
    private VisionSystem s_Vision;
    private CommandSwerveDrivetrain s_swerve; 
    private double position;
    private AlignmentPitch alignmentPitch;
    private AlignmentPitch alignmentPitch2;
    private AlignmentYaw alignmentYaw;
    
    public AutoAlgae(Elevator elevator, CoralIntake coralIntake, VisionSystem vision, CommandSwerveDrivetrain swerve){
        this.s_Elevator = elevator;
        this.s_CoralIntake = coralIntake;
        this.s_Vision = vision;
        this.s_swerve = swerve;
        this.position = position;

        // Initialize alignment commands
        alignmentPitch = new AlignmentPitch(swerve, vision, 1000);
        alignmentYaw = new AlignmentYaw(swerve, vision, 0);
        alignmentPitch2 = new AlignmentPitch(swerve, vision, 300);
        // Register alignment commands
        NamedCommands.registerCommand("alignmentPitch", alignmentPitch);
        NamedCommands.registerCommand("alignmentYaw", alignmentYaw);
        NamedCommands.registerCommand("alignmentPitch2", alignmentPitch2);
        
        
        addCommands(
            alignmentPitch, 
            new InstantCommand(() -> s_CoralIntake.wristpose(28)), 
            new WaitCommand(.2),
            //new InstantCommand(() -> Values.setLeftOrRight()),
            alignmentYaw,
            alignmentPitch2,
            new InstantCommand(() -> s_Elevator.setPose(-7)),
            new InstantCommand(() -> coralIntake.AlgaeIntakeSpeed(3.0)), 
            new WaitCommand(1), 
            new InstantCommand(() -> coralIntake.CoralIntakeSpeed(0))
        );
    }

}