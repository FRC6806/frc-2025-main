package frc.robot.commands;
import frc.robot.Constants;
import frc.robot.Constants;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.CommandSwerveDrivetrain;
import frc.robot.subsystems.CoralIntake;
import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.Values;
import frc.robot.subsystems.VisionSystem;
import frc.robot.commands.AlignmentPitch;
import frc.robot.commands.AlignmentYaw;


 
public class Reset extends SequentialCommandGroup { 
    private CoralIntake s_CoralIntake;   
    private Elevator s_Elevator;
    private VisionSystem s_Vision;
    private CommandSwerveDrivetrain s_swerve; 
    private AlignmentPitch alignmentPitch;
    private AlignmentYaw alignmentYaw;
    
    public Reset(Elevator elevator, CoralIntake coralIntake, VisionSystem vision, CommandSwerveDrivetrain swerve){
        this.s_Elevator = elevator;
        this.s_CoralIntake =coralIntake;
        this.s_Vision =vision;
        this.s_swerve =swerve;

        addCommands(
            new InstantCommand(() -> s_Elevator.setPose(0)),
            //new InstantCommand(() -> s_CoralIntake.wristpose(0)), 
            new InstantCommand(() -> s_CoralIntake.AlgaeIntakeSpeed(0))
        );
    }

}