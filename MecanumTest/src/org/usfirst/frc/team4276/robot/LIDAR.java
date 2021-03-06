package org.usfirst.frc.team4276.robot;

import java.util.TimerTask;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.I2C.Port;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.smartdashboard.*;

public class LIDAR implements PIDSource {
	public String name;
	public int nSequenceLidar = 0;
	public int lidarDistanceCentimeters = 0;

	private I2C i2c;
	private byte[] distance;
	private java.util.Timer updater;
	private LIDARUpdater task;
	int samples = 0, errors = 0;

	private final int LIDAR_CONFIG_REGISTER = 0x00;
	private final int LIDAR_DISTANCE_REGISTER = 0x8f;

	public LIDAR(String nam, Port port, int addr) {
		try {
			name = nam;
			i2c = new I2C(port, addr);
	
			distance = new byte[2];
	
			task = new LIDARUpdater();
			updater = new java.util.Timer();
		} catch(Exception e) {
			SmartDashboard.putString("debug", "LIDAR constructor failed");
		}
	}

	// Distance in cm
	public int getDistance() {
		return (int) Integer.toUnsignedLong(distance[0] << 8) + Byte.toUnsignedInt(distance[1]);
	}

	public double pidGet() {
		return getDistance();
	}

	// Start 10Hz polling
	public void start() {
		updater.scheduleAtFixedRate(task, 0, 1000);
	}

	// Start polling for period in milliseconds
	public void start(int period) {
		updater.scheduleAtFixedRate(task, 0, period);
	}

	public void stop() {
		updater.cancel();
	}

	// Update distance variable
	public void update() {

		i2c.write(LIDAR_CONFIG_REGISTER, 0x4); // Initiate measurement
		Timer.delay(0.012); // Delay for measurement to be taken

		SmartDashboard.putBoolean(name + " LIDAR Aborted", i2c.read(LIDAR_DISTANCE_REGISTER, 2, distance)); // Read
																									// in
																									// measurement
		samples++;
		SmartDashboard.putNumber(name + " LIDAR Counter", samples);
		if (getDistance() > 0) {
			nSequenceLidar++;
			lidarDistanceCentimeters = getDistance();
			SmartDashboard.putNumber(name + " LIDAR Distance", getDistance());
		} else
			errors++;
		SmartDashboard.putNumber(name + " Errors", errors);

	}

	// Timer task to keep distance updated
	private class LIDARUpdater extends TimerTask {
		public void run() {
			while (true) {
				update();

				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public void setPIDSourceType(PIDSourceType pidSource) {
		// TODO Auto-generated method stub

	}

	@Override
	public PIDSourceType getPIDSourceType() {
		// TODO Auto-generated method stub
		return null;
	}
}