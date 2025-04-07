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

 
public class ScoreAlgae extends SequentialCommandGroup { 
    private CoralIntake s_CoralIntake;   
    private Elevator s_Elevator;
    private VisionSystem s_Vision;
    private CommandSwerveDrivetrain s_swerve; 
    
    public ScoreAlgae(Elevator elevator, CoralIntake coralIntake, VisionSystem vision, CommandSwerveDrivetrain swerve){
        this.s_Elevator = elevator;
        this.s_CoralIntake =coralIntake;
        this.s_Vision =vision;
        this.s_swerve =swerve;

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
            // new Alignment(s_swerve, s_Vision),
            // new InstantCommand(() -> s_CoralIntake.wristpose(CoralIntake.CORAL_SCORE)),
            new InstantCommand(() -> s_CoralIntake.wristpose(25)),
            new WaitCommand(.5),
            nextCommand
        );
    }

}