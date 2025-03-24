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

public class Climb extends SubsystemBase{
    private TalonFX clamp; 
    private final int climbPosition = 10;
    private final int startPosition = 10;
    final PositionVoltage m_request = new PositionVoltage(0).withSlot(0);

    public Climb(int canID){
        TalonFX c = new TalonFX(canID);
        clamp = c;
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
        clamp.getConfigurator().apply(talonFXConfigs);
    }
    public void climbSpeed(double speed){
        clamp.set(speed);
    }

    // public void endClimb(){
    //     clamp.setControl(m_request.withPosition(climbPosition));
    // }

}

