package frc.robot.subsystems;

/* 
import com.ctre.phoenix6.configs.FeedbackConfigs;
import com.ctre.phoenix6.StatusCode;
import com.ctre.phoenix6.configs.*;
import com.ctre.phoenix6.configs.Slot0Configs;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import com.ctre.phoenix6.signals.ControlModeValue;
import edu.wpi.first.wpilibj2.command.Command;
*/

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.*;
import com.ctre.phoenix6.hardware.TalonFX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class AlgaeIntake extends SubsystemBase {
    private TalonFX rolls; 
    private TalonFX wrist;
    private final int algaePosition = 10;
    private final int startPosition = 10;
    private final double deadband = 0.1;
    final PositionVoltage m_request = new PositionVoltage(0).withSlot(0);

    public AlgaeIntake(int canID, int canID2){
        TalonFX r = new TalonFX(canID);
        TalonFX w = new TalonFX(canID2);
        rolls = r;
        wrist = w;
        // TalonFX Configuration
        var talonFXConfigs = new TalonFXConfiguration();

        // Set slot 0 gains
        var slot0Configs = talonFXConfigs.Slot0;
        slot0Configs.kS = 0.25; 
        slot0Configs.kV = 0.12; 
        slot0Configs.kA = 0.01; 
        slot0Configs.kP = 4.8; 
        slot0Configs.kI = 0; 
        slot0Configs.kD = 0.1;

        // Set Motion Magic Expo settings
        var motionMagicConfigs = talonFXConfigs.MotionMagic;
        motionMagicConfigs.MotionMagicCruiseVelocity = 0; 
        motionMagicConfigs.MotionMagicExpo_kV = 0.12; 
        motionMagicConfigs.MotionMagicExpo_kA = 0.1; 

        // Apply configurations to the motor
        wrist.getConfigurator().apply(talonFXConfigs);
    
    }

    //sets speed on algae intake
    public void AlgaeIntakeSpeed(double speed){
        if(speed>1){
            speed = 1;
        }
        if(speed<-1){
            speed = -1;
        }
        if(Math.abs(speed) > deadband){
            rolls.set(speed);
        }
        else{
            speed = 0;
        }
    }


    public void AlgaeWristSpeed(double speed){
        wrist.set(speed);
    }


    public void startWrist(){
        wrist.setControl(m_request.withPosition(startPosition));
    }

    
    public void algaePosition(){
        wrist.setControl(m_request.withPosition(algaePosition));
    }

}