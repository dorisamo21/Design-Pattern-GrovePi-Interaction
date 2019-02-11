package edu.ccsu.error;

/**
 * Exception to be thrown when attempting to set port number
 * to a port already in use
 * @author Adrian
 *
 */
public class PortInUseException extends Exception{
	public PortInUseException(String s) {
		super(s);
	}		
}