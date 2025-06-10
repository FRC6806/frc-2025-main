package frc.robot.commands;
import frc.robot.Constants;
import frc.robot.Constants;

import com.pathplanner.lib.auto.NamedCommands;

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


 
public class ScoreAlgae extends SequentialCommandGroup { 
    private CoralIntake s_CoralIntake;   
    private Elevator s_Elevator;
    private VisionSystem s_Vision;
    private CommandSwerveDrivetrain s_swerve; 
    private AlignmentPitch alignmentPitch;
    private AlignmentYaw alignmentYaw;
    private AlignmentPitch alignmentPitch2;
    
    public ScoreAlgae(Elevator elevator, CoralIntake coralIntake, VisionSystem vision, CommandSwerveDrivetrain swerve){
        this.s_Elevator = elevator;
        this.s_CoralIntake =coralIntake;
        this.s_Vision =vision;
        this.s_swerve =swerve;

        alignmentPitch = new AlignmentPitch(swerve, vision, 1000);
        alignmentYaw = new AlignmentYaw(swerve, vision, 0);
        alignmentPitch2 = new AlignmentPitch(swerve, vision, 650);
        // NamedCommands.registerCommand("alignmentPitch", alignmentPitch);
        // NamedCommands.registerCommand("alignmentYaw", alignmentYaw);
        //NamedCommands.registerCommand("alignmentPitch2", alignmentPitch2); 

        Command nextCommand;
            if (Values.getCoralLevel()<=-10){
                nextCommand = new InstantCommand(() -> {
                        s_Elevator.setPose(Values.algaeSetLevel());
                });
            }else{

                nextCommand = new SequentialCommandGroup(new InstantCommand(() ->s_Elevator.setPose(Values.algaeSetLevel())),
                new InstantCommand(() ->s_CoralIntake.wristpose(28)));
             
            }
   
        addCommands(
            alignmentPitch, 
            new WaitCommand(.5),
            //new InstantCommand(() -> s_CoralIntake.wristpose(28)), 
            new WaitCommand(.5),
            alignmentYaw, 
            alignmentPitch2,
            nextCommand,
            new InstantCommand(() -> s_CoralIntake.CoralIntakeSpeed(-1.0)),
            new WaitCommand(3),
            new InstantCommand(() -> s_CoralIntake.CoralIntakeSpeed(0))
        );
    }

}