package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

public class Robot extends TimedRobot {
  private DifferentialDrive m_myRobot1;
  private DifferentialDrive m_myRobot2;
  private Joystick m_joyStick;
  private static final int leftDeviceID1 = 1; 
  private static final int leftDeviceID2 = 2;
  private static final int rightDeviceID1 = 4;
  private static final int rightDeviceID2 = 5;
  private static final int clawID = 3;
  private CANSparkMax m_leftMotor1;
  private CANSparkMax m_rightMotor1;
  private CANSparkMax m_leftMotor2;
  private CANSparkMax m_rightMotor2;
  private CANSparkMax m_garra;
  private Garra claw;
  private Timer timer;
  private Limelight limelight;

  @Override
  public void robotInit() {
  /**
   * SPARK MAX controllers are intialized over CAN by constructing a CANSparkMax object
   * 
   * The CAN ID, which can be configured using the SPARK MAX Client, is passed as the
   * first parameter
   * 
   * The motor type is passed as the second parameter. Motor type can either be:
   *  com.revrobotics.CANSparkMaxLowLevel.MotorType.kBrushless
   *  com.revrobotics.CANSparkMaxLowLevel.MotorType.kBrushed
   * 
   * The example below initializes four brushless motors with CAN IDs 1 and 2. Change
   * these parameters to match your setup
   */
    m_leftMotor1 = new CANSparkMax(leftDeviceID1, MotorType.kBrushless);
    m_rightMotor1 = new CANSparkMax(rightDeviceID1, MotorType.kBrushless);
    m_leftMotor2 = new CANSparkMax(leftDeviceID2, MotorType.kBrushless);
    m_rightMotor2 = new CANSparkMax(rightDeviceID2, MotorType.kBrushless);
    m_rightMotor1.setInverted(false);
    m_rightMotor2.setInverted(false);
    m_garra = new CANSparkMax(clawID, MotorType.kBrushed);
    claw = new Garra(m_garra, clawID);
    limelight = new Limelight();
    
    /**
     * The RestoreFactoryDefaults method can be used to reset the configuration parameters
     * in the SPARK MAX to their factory default state. If no argument is passed, these
     * parameters will not persist between power cycles
     */
    m_leftMotor1.restoreFactoryDefaults();
    m_rightMotor1.restoreFactoryDefaults();
    m_leftMotor2.restoreFactoryDefaults();
    m_rightMotor2.restoreFactoryDefaults();

    m_myRobot1 = new DifferentialDrive(m_leftMotor1, m_rightMotor1);
    m_myRobot2 = new DifferentialDrive(m_leftMotor2, m_rightMotor2);

    m_joyStick = new Joystick(0);

    timer = new Timer();
   
  }

  @Override
  public void teleopPeriodic() {
    m_myRobot1.arcadeDrive(m_joyStick.getZ()*0.45, m_joyStick.getY()*0.60);
    m_myRobot2.arcadeDrive(m_joyStick.getZ()*0.45, m_joyStick.getY()*0.60);
  
  if (m_joyStick.getRawButtonPressed(6))
  {
      claw.levantarGarra();
  }
  if (m_joyStick.getRawButtonReleased(6))
  {
      claw.pararGarra();
  }

  if (m_joyStick.getRawButtonPressed(5))
  {
      claw.abaixarGarra();
  }

  if (m_joyStick.getRawButtonReleased(5))
  {
      claw.pararGarra();
  }
  
  }
  @Override
  public void autonomousInit(){
    timer.reset();
    timer.start();
  }

  @Override
  public void autonomousPeriodic(){
    timer.reset();
    timer.start();
    
    limelight.update();
    limelight.dashboard();

    m_myRobot1.arcadeDrive(limelight.pidX(), -0.32);

    /* 
    while(timer.get()<0.2){
      m_myRobot1.arcadeDrive(0, 0.5);
      m_myRobot2.arcadeDrive(0, 0.5);
    }
    while(timer.get()>0.2 && timer.get()<0.45){
      m_myRobot1.arcadeDrive(0, -0.7);
      m_myRobot2.arcadeDrive(0, -0.7);
    }
    while(timer.get()>0.45 && timer.get()<3){
      m_myRobot1.arcadeDrive(0, -0.4);
      m_myRobot2.arcadeDrive(0, -0.4);
    }
    while(timer.get()>3 && timer.get()<7){
      m_myRobot1.arcadeDrive(0, 0);
      m_myRobot2.arcadeDrive(0, 0);
    }
    */
    
  }
}