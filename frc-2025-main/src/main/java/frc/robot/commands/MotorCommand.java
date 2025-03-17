package frc.robot.commands;

import frc.robot.Constants;
import frc.robot.subsystems.CommandSwerveDrivetrain;
import frc.robot.subsystems.VisionSystem;
import frc.robot.subsystems.MotorTesting;

import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.swerve.SwerveRequest;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.WaitCommand;


public class MotorCommand extends Command {    
   public MotorTesting motor;

    public MotorCommand(MotorTesting m) {
       motor = m;
        addRequirements(m);
        //addRequirements(s_Swerve);
    }

public void intitialize(){
   
     
}

public void end(boolean interupted){

}

public boolean isFinished(){

        return false;


    
}

public void setEnd(){
    motor.spin(0);
}

    @Override
    public void execute() {
            motor.spin(0.05);
        }

        public void periodic(){
         

        }
        
        }
