package frc.robot.commands;
import frc.robot.Constants;
import frc.robot.Constants;

import com.ctre.phoenix6.swerve.SwerveRequest;
import com.pathplanner.lib.auto.NamedCommands;
import com.pathplanner.lib.commands.PathPlannerAuto;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.CommandSwerveDrivetrain;
import frc.robot.subsystems.CoralIntake;
import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.Moving;
import frc.robot.subsystems.VisionSystem;
import frc.robot.subsystems.Moving;
 
public class AlignmentAll extends SequentialCommandGroup { 
    private AlignmentPitch alignmentPitch;
    private AlignmentYaw alignmentYaw;
    private Moving move;
    
    public AlignmentAll(  CommandSwerveDrivetrain drivetrain, VisionSystem vision, double TargetYaw, double TargetPitch){
        
        
        alignmentPitch = new AlignmentPitch(drivetrain, vision, TargetPitch);
        alignmentYaw = new AlignmentYaw(drivetrain, vision, TargetYaw);
        NamedCommands.registerCommand("alignmentPitch", alignmentPitch);
        NamedCommands.registerCommand("alignmentYaw", alignmentYaw);
        addCommands(
            //alignmentYaw,
            alignmentPitch
            //new WaitCommand(.5)
            // new InstantCommand(() -> move.moveForward(drivetrain)),
            // new InstantCommand(() -> move.stop(drivetrain)),
            // new WaitCommand(0.5),
            // new InstantCommand(() -> move.stop(drivetrain))
        );
    }
// IF U WANT TO CHANGE YOUR YAW LOOK INSIDE OF BINDINGS IN ROBOT CONTAINER >:(
}
    

