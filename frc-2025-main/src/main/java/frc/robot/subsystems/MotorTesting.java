package frc.robot.subsystems;


import com.ctre.phoenix6.hardware.TalonFX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class MotorTesting extends SubsystemBase{
    private TalonFX motor;


    public MotorTesting(int canID){


        TalonFX m = new TalonFX(canID);
        motor = m;
    }


    public void spin(double speed){
        motor.set(speed);
        SmartDashboard.putNumber("SPeed" , speed);
        //woah this totally moves the motor or whatever
       
    }


}
