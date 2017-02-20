package org.usfirst.frc.team4276.robot;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Climber {
	DigitalInput climberLimitSwitch;
	VictorSP climber;

	final static double LIMIT_SWITCH_DELAY = 1.0; // seconds
	final static double CLIMBER_POWER = -1.0; // -1.0 to 1.0

	boolean initializeLimitSwitchDelay = true;
	Toggler climberToggler;
	SoftwareTimer limitSwitchDelayTimer;

	public Climber(int pwm5, int dio13) {
		limitSwitchDelayTimer = new SoftwareTimer();
		climber = new VictorSP(pwm5);
		climberLimitSwitch = new DigitalInput(dio13);
		climberToggler = new Toggler(JoystickMappings.climberControl, XBox.POV, JoystickMappings.climberControlValue);
	}

	void performMainProcessing() {
		climberToggler.updateMechanismState();
		if (climberToggler.getMechanismState()) {
			//if (climberLimitSwitch.get() == false) {
			if (false){
				if (initializeLimitSwitchDelay) {
					limitSwitchDelayTimer.setTimer(LIMIT_SWITCH_DELAY);
					initializeLimitSwitchDelay = false;
				} else if (limitSwitchDelayTimer.isExpired()) {
					climber.set(0.0);
				}
			} else {
				climber.set(CLIMBER_POWER);
			}
		}

		else {
			climber.set(0.0);
		}

		//SmartDashboard.putBoolean("Climber", climbing);
	}
}
