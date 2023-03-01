package frc.robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.cscore.CvSink;
import edu.wpi.first.cscore.CvSource;
import edu.wpi.first.cscore.UsbCamera;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.opencv.core.Mat;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

public class Robot extends TimedRobot {

  CANSparkMax m_garra;
  Garra claw;
  Pneumatics pneumatic;
  Gyroscope gyro;
  static final int clawID = 3;
  Joystick m_joyStick1;
  Joystick m_joyStick2;
  Drivetrain drive;
  
  double velocityX = 0.6;
  double velocityY = 0.6;
  int countFlag = 0;
  
  
  Thread m_visionThread;
  boolean flag = false;
  boolean mode = false;
  private Timer m_Timer;

  @Override
  public void robotInit(){

    m_Timer = new Timer();
    gyro = new Gyroscope();
    drive = new Drivetrain();
    pneumatic = new Pneumatics();
    m_joyStick1 = new Joystick(0);
    m_joyStick2 = new Joystick(1);
    m_garra = new CANSparkMax(clawID, MotorType.kBrushless);
    claw = new Garra(m_garra, clawID);

    gyro.init();
    claw.init();
    drive.init();
    pneumatic.init();
    
    m_visionThread = new Thread( () -> {
      UsbCamera r_camera = CameraServer.startAutomaticCapture(0);
          r_camera.setResolution(640, 480);
          CvSink cvSink = CameraServer.getVideo();
          CvSource outputStream = CameraServer.putVideo("Camera", 640, 480);
          Mat mat = new Mat();
          while (!Thread.interrupted()) {
            if (cvSink.grabFrame(mat) == 0) {
              outputStream.notifyError(cvSink.getError());
              continue;
            }
            outputStream.putFrame(mat);
          }
        });
      }
      
      @Override
      public void robotPeriodic(){
        SmartDashboard.putNumber("Yaw", gyro.yaw());
        SmartDashboard.putNumber("Roll", gyro.roll());
        SmartDashboard.putNumber("Turn", velocityX);
        SmartDashboard.putNumber("Velocity", velocityY);
        SmartDashboard.putNumber("Rotation", m_joyStick1.getZ());
        SmartDashboard.putNumber("Acceleration", m_joyStick1.getY()*-1);
        SmartDashboard.putBoolean("ON/OFF", RobotController.isSysActive());
        
        
      }
      
      
      @Override
  public void teleopInit(){
    pneumatic.compressorDisable();
    pneumatic.init();
    
  }
  
  @Override
  public void teleopPeriodic(){

    
    drive.drive(m_joyStick1.getZ()*velocityX, m_joyStick1.getY()*velocityY);
    // 
    if(m_joyStick1.getRawButtonPressed(8) && velocityY<1){
      velocityY += 0.1;
    }
    if(m_joyStick1.getRawButtonPressed(7)&& velocityY>0){
      velocityY -= 0.1;
    }//
/* 
    if(m_joyStick1.getRawButtonPressed(8)&& velocityX<1){
      velocityX += 0.1;
    }
    if(m_joyStick1.getRawButtonPressed(7)&& velocityX>0){
      velocityX -= 0.1;
    }
    */
  //================== GARRA ================== 
  // -------------- Movimentação --------------
  //Levantar garra
  if(m_joyStick2.isConnected()){
    if(m_joyStick2.getRawButton(5)){
      claw.abaixarGarra();
    }
    if(m_joyStick2.getRawButton(6)){
      claw.levantarGarra();
    }
    if(m_joyStick2.getPOV() == 0){
      claw.setPosition(25);
    }
    if(m_joyStick2.getPOV() == 270){
      claw.setPosition(15);
    }
    if (m_joyStick2.getPOV() == 180 ){
      claw.setPosition(5);
    }
    
  }
  if (m_joyStick1.getRawButton(5)){
    claw.abaixarGarra();
  }
  
  //Abaixar garra
  if (m_joyStick1.getRawButton(6)){
    claw.levantarGarra();
  }
  
  //Posicionar garra no topo
  if (m_joyStick1.getPOV() == 0){
    claw.setPosition(25);
  }
  
  //Posicionar garra no meio
  if (m_joyStick1.getPOV() == 270){
    claw.setPosition(15);
  }
  
  //Posicionar garra no meio
  if (m_joyStick1.getPOV() == 180 ){
    claw.setPosition(5);
  }
  
  SmartDashboard.putNumber("Posição da garra", m_garra.getEncoder().getPosition());

  // ==============  Pneumática ==============
  // -------------- Compressor --------------
  // Ligar compressor
  if (m_joyStick1.getRawButtonPressed(1) ||m_joyStick2.getRawButtonPressed(1)){
    if(flag == false)
    {
      flag = true;
      pneumatic.compressorEnable();
    }
    else{
      flag = false;
      pneumatic.compressorDisable();
    }
  }

  // Ligar/Desligar pistão
  if (m_joyStick1.getRawButtonReleased(2) || m_joyStick2.getRawButtonReleased(2)){
    pneumatic.onoffToggle();
    pneumatic.mode.set(Value.kForward);
    
    
  }
  // Ligar/Desligar pistão
  if (m_joyStick1.getRawButtonReleased(3) || m_joyStick2.getRawButtonReleased(3)){
    pneumatic.onoffToggle();
    pneumatic.mode.set(Value.kReverse);

  }
  

  } //Fim do TeleopPeriodic


  @Override
  public void autonomousInit(){
    pneumatic.compressorDisable();
    m_Timer.reset();
    m_Timer.start();
    while(gyro.roll()>-8){
    drive.drive(0, -0.49);
   }
  }
    @Override
    public void autonomousPeriodic(){
      m_Timer.reset();
      m_Timer.start();

    drive.drive(0, gyro.pidBalance(gyro.roll(),0));


    
  }
}