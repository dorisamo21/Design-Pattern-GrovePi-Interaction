package edu.ccsu.utility;

import java.util.ArrayList;
import java.util.List;

/**
 * Simple singleton class used internally to maintain ports in use
 * @author Adrian
 * @author Kim
 * @author Ga Young
 */
public class PortManagement {

	private static PortManagement portManagement = new PortManagement();
	private static List<String> portList = new ArrayList<>();  
	
	private PortManagement() {
		//can only instantiate itself
	}
	
	public static PortManagement getInstance() {
		return portManagement;
	}
	
	/**
	 * Will add portNumber to list of in use 
	 * port numbers.  
	 * @param portNumber
	 * @return False if port already in list or argument in null.  True if portNumber
	 * can be added to list
	 */
	public boolean add(String portNumber) {
		if(portList.isEmpty()) {
			portList.add(portNumber);
			return true;
		}
		if(portList.contains(portNumber)) {
			return false;
		}
		if(portNumber != null) {
			portList.add(portNumber);
			return true;
		}
		return false;
	}
	
	/**
	 * Remove element from list if it's in the list
	 * @param portNumber
	 */
	public void remove(String portNumber) {
		if(portNumber != null && portList.contains(portNumber))
			portList.remove(portNumber);
	}
	
	/**
	 * 
	 * @return A copy of the list of ports in use
	 */
	public static List<String> getPortsInUse(){
		return new ArrayList<String>(portList);
	}
}