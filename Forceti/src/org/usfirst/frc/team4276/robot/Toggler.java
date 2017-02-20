package org.usfirst.frc.team4276.robot;

public class Toggler {

	private int button;
	private boolean state = false;

	private boolean currentButtonStatus = false;
	private boolean previousButtonStatus;

	public Toggler(int joystickButton) {
		button = joystickButton;
	}

	void updateMechanismState() {
		previousButtonStatus = currentButtonStatus;
		currentButtonStatus = Robot.XBoxController.getRawButton(button);

		if (currentButtonStatus == true) {
			if (previousButtonStatus == false) {
				if (state == true) {
					state = false;
				} else {
					state = true;
				}
			}
		}
	}

	boolean getMechanismState() {
		return state;
	}
}
