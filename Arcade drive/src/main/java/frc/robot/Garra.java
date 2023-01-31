package frc.robot;

import com.revrobotics.CANSparkMax;

public class Garra{
    public final  CANSparkMax m_drive;
    public final int CanID;

    public Garra(CANSparkMax receive, int ID)
    {
        m_drive = receive;
        CanID = ID;
    }

    public void levantarGarra()
    {
        m_drive.set(1.0);
    }

    public void abaixarGarra()
    {
        m_drive.set(-1.0);
    }

    public void pararGarra()
    {
        m_drive.set(0);
    }
}
