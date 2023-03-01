package frc.robot;

import edu.wpi.first.wpilibj.SPI;
import com.kauailabs.navx.frc.AHRS;

public class Gyroscope {
    AHRS ahrs;
    double lastError;

    public void init(){
    ahrs = new AHRS(SPI.Port.kMXP);
    ahrs.reset();
    ahrs.zeroYaw();
    ahrs.calibrate();
    }

    public float yaw(){return ahrs.getYaw();}
    public float roll(){return ahrs.getRoll();}
    public float pitch(){return ahrs.getPitch();}

    public double pidBalance(float x, float setPoint){
        double output = ((x-setPoint)*0.02)+(lastError+x)*0.003+(x/lastError)*0.02;
        lastError = x-setPoint;
        return output;
    }
    
}
