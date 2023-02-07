// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;


public class Robot extends TimedRobot {
  private final WPI_VictorSPX m_leftMotor = new WPI_VictorSPX(0);
  private final WPI_VictorSPX m_rightMotor = new WPI_VictorSPX(1);
  private final DifferentialDrive drive = new DifferentialDrive(m_leftMotor, m_rightMotor);
  private final Joystick m_stick = new Joystick(0);
  Trajectory trajectory = new Trajectory();

  String trajectoryJSON = "Paths/output/Test 1.wpilib.json";

  @Override
  public void robotInit() {
    //Seta o motor direito do robô como invertido
    m_rightMotor.setInverted(true);

    

  }

  @Override
  public void teleopPeriodic() {
    drive.arcadeDrive(-m_stick.getY(), -m_stick.getZ()*0.9);

  }

  @Override
  public void autonomousPeriodic(){


  }

}
