package org.usfirst.frc.team4276.robot;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Joystick;

public class BallCollector {

	VictorSP ballCollectorRelay;
	Joystick ballCollectorJoystick;
	
	boolean collecting = false;
	boolean previousState = false;
	
	
	public BallCollector(int pwm7) {
		
		ballCollectorRelay = new VictorSP(pwm7);
		ballCollectorJoystick = new Joystick(2);
		
	}

void joystick(){
	if(ballCollectorJoystick.getRawButton(3)) {
		if(previousState == false)
		{
			toggle();
			previousState = true;
		}
		else
		{
			previousState = true;
		}
	
	}
	else
	{
		previousState = false;
	}
	
	
}

void toggle(){
	if(collecting == true){
		collecting = false;
	}
	else{
		collecting = true;
		}
		
	
}

	void run(){
		joystick();
		if(collecting == true){
			ballCollectorRelay.set(1);
		}
		
		else{
			ballCollectorRelay.set(0);
		}
		
		SmartDashboard.putBoolean("Collector", collecting);
		
		}
	
}