package frc.robot;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Pneumatics {
    
    Compressor m_Compressor = new Compressor(0, PneumaticsModuleType.CTREPCM);
    DoubleSolenoid mode = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, 1, 0);
    Solenoid onoff = new Solenoid(PneumaticsModuleType.CTREPCM, 2);

    boolean pressureSwitch = m_Compressor.getPressureSwitchValue();

    public void compressorEnable(){
        m_Compressor.enableDigital();
    }
    public void compressorDisable(){
        m_Compressor.disable();
    }

    public void init(){
        mode.set(Value.kOff);
        mode.set(Value.kForward);
        onoff.set(false);
    }

    public void modeToggle(){
        mode.toggle();
    }
    
    public void onoffToggle(){
        onoff.toggle();
    }

    public void pressureSwitch(){
        SmartDashboard.putBoolean("Pressure", m_Compressor.getPressureSwitchValue());
    }

    public void onoffSet(boolean on){
        onoff.set(on);
    }

/*     public void modeSet(int on){
        switch(on)
        {
            case 0: mode.set(Value.kOff); break;
            case 1: mode.set(Value.kForward); break;
            case 2: mode.set(Value.kReverse); break;
        }
    }
*/
}
