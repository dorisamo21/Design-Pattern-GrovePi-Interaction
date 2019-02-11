package edu.ccsu.interfaces;
/***
 * 
 * @author Adrian
 *
 */
public interface Fan extends Device{

	/**
	 * Accepts a value between 0-255 (0 being off and 255 being fastest speed).
	 * Use this method to adjust the fan speed
	 * @param speed
	 */
	public void adjustSpeed(int speed);
}
