package frc.robot;

import java.util.Timer;

import com.revrobotics.CANSparkMax;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;


public class Drivetrain {

    private DifferentialDrive m_myRobot1;
    private DifferentialDrive m_myRobot2;
    private static final int leftDeviceID1 = 1; 
    private static final int leftDeviceID2 = 2;
    private static final int rightDeviceID1 = 4;
    private static final int rightDeviceID2 = 5;
    private CANSparkMax m_leftMotor1;
    private CANSparkMax m_rightMotor1;
    private CANSparkMax m_leftMotor2;
    private CANSparkMax m_rightMotor2;
    private Timer timer;
    private Joystick m_joyStick;

    public void init(){
        m_leftMotor1 = new CANSparkMax(leftDeviceID1, MotorType.kBrushless);
        m_rightMotor1 = new CANSparkMax(rightDeviceID1, MotorType.kBrushless);
        m_leftMotor2 = new CANSparkMax(leftDeviceID2, MotorType.kBrushless);
        m_rightMotor2 = new CANSparkMax(rightDeviceID2, MotorType.kBrushless);
        m_rightMotor1.setInverted(false);
        m_rightMotor2.setInverted(false);

        m_leftMotor1.restoreFactoryDefaults();
        m_rightMotor1.restoreFactoryDefaults();
        m_leftMotor2.restoreFactoryDefaults();
        m_rightMotor2.restoreFactoryDefaults();
    
        m_myRobot1 = new DifferentialDrive(m_leftMotor1, m_rightMotor1);
        m_myRobot2 = new DifferentialDrive(m_leftMotor2, m_rightMotor2);

        timer = new Timer();
        m_joyStick = new Joystick(0);
    }
    
    public void drive(double rotation, double speed){
        m_myRobot1.arcadeDrive(rotation,speed);
        m_myRobot2.arcadeDrive(rotation,speed);

    }



}