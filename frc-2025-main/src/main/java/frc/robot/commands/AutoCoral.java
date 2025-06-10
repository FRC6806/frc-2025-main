package frc.robot.commands;

import frc.robot.Constants; // If Constants are used later, keep this import
import com.pathplanner.lib.auto.NamedCommands;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.CommandSwerveDrivetrain;
import frc.robot.subsystems.CoralIntake;
import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.VisionSystem;
import frc.robot.subsystems.Values;

public class AutoCoral extends SequentialCommandGroup {
    private CoralIntake s_CoralIntake;
    private Elevator s_Elevator;
    private VisionSystem s_Vision;
    private CommandSwerveDrivetrain s_swerve;
    private double position;
    private AlignmentPitch alignmentPitch;
    private AlignmentPitch alignmentPitch2;
    private AlignmentPitch alignmentPitch3;
    private AlignmentYaw alignmentYaw;
    
    public AutoCoral(Elevator elevator, CoralIntake coralIntake, VisionSystem vision, CommandSwerveDrivetrain swerve, double position) {
        this.s_Elevator = elevator;
        this.s_CoralIntake = coralIntake;
        this.s_Vision = vision;
        this.s_swerve = swerve;
        this.position = position;

        // Initialize alignment commands
        alignmentPitch = new AlignmentPitch(swerve, vision, 1000);
        alignmentPitch2 = new AlignmentPitch(swerve,vision,600);
        alignmentPitch3 = new AlignmentPitch(swerve,vision,500);
        alignmentYaw = new AlignmentYaw(swerve, vision, 30);
        
        // Register alignment commands
        NamedCommands.registerCommand("alignmentPitch", alignmentPitch);
        NamedCommands.registerCommand("alignmentYaw", alignmentYaw);
        NamedCommands.registerCommand("alignmentPitch2", alignmentPitch2);

        
        addCommands(
            alignmentPitch,  
            new InstantCommand(() -> coralIntake.wristpose(CoralIntake.CORAL_SCORE)), 
            new WaitCommand(.2),
            new InstantCommand(() -> Values.setLeftOrRight(true)), 
            alignmentYaw, 
            alignmentPitch2,
            new InstantCommand(() -> s_Elevator.setPose(-18)),
            alignmentPitch3,
            new WaitCommand(2), 
            new InstantCommand(() -> coralIntake.CoralIntakeSpeed(1.0)), 
            new WaitCommand(3), 
            new InstantCommand(() -> coralIntake.CoralIntakeSpeed(0))
        );
    }
}


