package frc.robot.commands;
import frc.robot.Constants;
import frc.robot.Constants;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.CoralIntake;
import frc.robot.subsystems.Elevator;

 
public class CoralIntakeCommand extends SequentialCommandGroup { 
    private CoralIntake s_CoralIntake;   
    private Elevator s_Elevator;
    
    public CoralIntakeCommand(Elevator s_Elevator, CoralIntake s_CoralIntake){
        this.s_Elevator = s_Elevator;
        this.s_CoralIntake =s_CoralIntake;

        
    
   
        addCommands(
            new InstantCommand(() -> s_Elevator.rotateMotor(10)),
            new WaitCommand(1),
            new InstantCommand(() -> s_CoralIntake.CoralIntakeSpeed(-1.0)),
            new WaitCommand(1),
            new InstantCommand(() -> s_Elevator.rotateMotor(10)),
            new WaitCommand(1)
        );
    }

}
    

