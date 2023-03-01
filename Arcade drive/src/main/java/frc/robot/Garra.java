package frc.robot;

import com.revrobotics.SparkMaxAlternateEncoder;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMax;

public class Garra{
    public final  CANSparkMax m_drive;
    public final int CanID;
    SparkMaxAlternateEncoder.Type kAltEncType = SparkMaxAlternateEncoder.Type.kQuadrature;
    public double kP, kI, kD, kIz, kFF, kMaxOutput, kMinOutput;
    public SparkMaxPIDController m_pidController;
    double position = 0.0;

    public Garra(CANSparkMax receive, int ID)
    {
        m_drive = receive;
        CanID = ID;
    }

    public void init(){
    m_pidController = m_drive.getPIDController();
    //m_pidController.setFeedbackDevice();

    // PID coefficients
    kP = 0.04; 
    kI = 8.9e-7;
    kD = 0.0000099;
    kIz = 0; 
    kFF = 0; 
    kMaxOutput = 0.4; 
    kMinOutput = -0.4;

    // set PID coefficients
    m_pidController.setP(kP);
    m_pidController.setI(kI);
    m_pidController.setD(kD);
    m_pidController.setIZone(kIz);
    m_pidController.setFF(kFF);
    m_pidController.setOutputRange(kMinOutput, kMaxOutput);

    }

    public void abaixarGarra(){
        if(position > -10){
            position -= 0.4;
        }
        m_pidController.setReference(Math.round(position), CANSparkMax.ControlType.kPosition);
    }
    
    public void levantarGarra(){
        if(position < 28){
            position += 0.4;
        }
        m_pidController.setReference(Math.round(position), CANSparkMax.ControlType.kPosition);
    }
    
    public void setPosition(int xposition){
        position = xposition;
        m_pidController.setReference(Math.round(xposition), CANSparkMax.ControlType.kPosition);

    }

}
