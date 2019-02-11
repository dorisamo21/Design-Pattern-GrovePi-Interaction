package edu.ccsu.error;
/**
 * Error to be thrown when using CoR and setting chain of devices 
 * not of the same type
 * @author Adrian
 * @author Kim
 * @author GaYoung
 */
public class IncompatibleDeviceError extends Exception {

	public IncompatibleDeviceError(String s) {
		super(s);
	}	
}
