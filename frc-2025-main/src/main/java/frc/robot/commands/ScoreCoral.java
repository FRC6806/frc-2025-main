package frc.robot.commands;
import frc.robot.Constants;

import com.pathplanner.lib.auto.NamedCommands;
import com.pathplanner.lib.commands.PathPlannerAuto;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.CommandSwerveDrivetrain;
import frc.robot.subsystems.CoralIntake;
import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.VisionSystem;
import frc.robot.subsystems.Values;
import frc.robot.commands.AlignmentAll;

 
public class ScoreCoral extends SequentialCommandGroup { 
    private CoralIntake s_CoralIntake;   
    private Elevator s_Elevator;
    private VisionSystem s_Vision;
    private CommandSwerveDrivetrain s_swerve;
    private double position;
    private AlignmentPitch alignmentPitch;
    private AlignmentSkew alignmentSkew;
    private AlignmentYaw alignmentYaw;
    private AlignmentAll alignmentAll;
    private AlignmentPitch alignmentPitch2;

    public ScoreCoral(Elevator elevator, CoralIntake coralIntake, VisionSystem vision, CommandSwerveDrivetrain swerve, double TargetYaw){
        this.s_Elevator = elevator;
        this.s_CoralIntake =coralIntake;
        this.s_Vision =vision;
        this.s_swerve =swerve;
        // addRequirements(s_Vision);
        // addRequirements(s_CoralIntake);
        // addRequirements(s_Elevator);
        // addRequirements(s_swerve);
        alignmentPitch = new AlignmentPitch(swerve, vision, 1000);
        alignmentSkew = new AlignmentSkew(swerve, vision, 0);
        alignmentPitch2 = new AlignmentPitch(swerve,vision,650);
        alignmentYaw = new AlignmentYaw(swerve, vision, TargetYaw);
        alignmentAll = new AlignmentAll(swerve, vision, TargetYaw, 1000);
        NamedCommands.registerCommand("alignmentAll", alignmentAll);
        NamedCommands.registerCommand("alignmentPitch", alignmentPitch);
        NamedCommands.registerCommand("alignmentYaw", alignmentYaw);
        NamedCommands.registerCommand("alignmentSkew", alignmentSkew);
        NamedCommands.registerCommand("alignmentPitch2",alignmentPitch2);
        

        Command nextCommand;
            if (Values.getCoralLevel()<=25){
                nextCommand = new InstantCommand(() -> {
                        s_Elevator.setPose(Values.coralSetLevel());
                });
            
            }else{

              nextCommand = new SequentialCommandGroup(new InstantCommand(() ->s_Elevator.setPose(Values.coralSetLevel())));
               //new InstantCommand(() ->s_CoralIntake.wristpose(78)));
             
            }

        addCommands(
            //alignmentSkew
            //alignmentPitch, 
            // new WaitCommand(.1),
            new InstantCommand(() -> s_CoralIntake.wristpose(CoralIntake.CORAL_SCORE)), 
            new WaitCommand(.5),
            new InstantCommand(() -> s_Elevator.setPose(Values.coralSetLevel())) //testing line
            // alignmentYaw,
            // alignmentPitch2,
            //nextCommand
            // new InstantCommand(() -> s_CoralIntake.CoralIntakeSpeed(1.0)),
            // new WaitCommand(3),
            // new InstantCommand(() -> s_CoralIntake.CoralIntakeSpeed(0))

        );
    }

}
    

