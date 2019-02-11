package ccsu.edu.grovepicomponents;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import edu.ccsu.error.IncompatibleSensorError;
import edu.ccsu.interfaces.Iterator;
import edu.ccsu.utility.CommonConstants;

/**
 * Class can access light sensor and 
 * get the light intensity
 */
public class LightSensor extends Sensor {

	private List<LightSensorData> sensorData;
	/**
	 * Creates a concrete LightSensor object
	 * @param name
	 * @param portNumber
	 */
	public LightSensor(String name, String portNumber) {
		this.name = name;
		this.portNumber = portNumber;
		this.sensorData = new ArrayList<>();
		this.sensorFile = CommonConstants.LIGHTSENSOR;
	}
	
	/**
	 * Method that takes data string, parses it, creates LightSensorData objects,
	 * and adds them to list
	 * @param data
	 */
	@Override
	protected String addToList(String data) {
		StringBuilder builder = new StringBuilder();
		String[] dataToAdd = data.split(",");
		for(String str: dataToAdd) {
			String[] makeIntoData = str.split(" ");
			String sensorValue = makeIntoData[0];
			String voltage = makeIntoData[1];
			String watts = makeIntoData[2];
			Date date = new Date();
			builder.append("SensorValue: " + sensorValue + " Voltage: " + voltage + " Watts: " + watts + " Date: " + date + "\n");
			//value from output will be three numbers, first is sensorValue second is voltage, third is watts
			sensorData.add(new LightSensorData(Integer.parseInt(sensorValue), Float.parseFloat(voltage), Float.parseFloat(watts), date));
		}
		return builder.toString();
	}
    
	@Override
	protected boolean checkPort(String portNumber) {
		if(!this.getPortNumber().contains("A")) {
			System.out.println("Must use a digital port starting with A");
			return false;
		}
		return true;
	}
	
	@Override
	public void setNextSensor(Sensor nextSensor) throws IncompatibleSensorError  {
		if(nextSensor instanceof LightSensor) {
			this.nextSensor = nextSensor;
		} else {
			throw new IncompatibleSensorError("Sensor not compatible with sensor. Sensor chain can only be use other Sensors");
		}
	}
	
	@Override
	public Iterator getIterator() {
		return new SensorIterator();
	}
	
	/**
	 * Class to hold LightSensorData.
	 * @author Adrian
	 *
	 */
	private class LightSensorData{
		int sensorValue;
		float watts;
		float voltage;
		Date date;
		/**
		 * Takes in data from the light sensor to hold it
		 * @param sensor
		 * @param voltage
		 * @param watts
		 * @param date
		 */
		public LightSensorData(int sensor, float voltage, float watts, Date date) {
			this.sensorValue = sensor;
			this.voltage =  voltage;
			this.watts = watts;
			this.date = date;
		}
		/**
		 * Returns data as string
		 */
		 public String toString(){
			return "\n**********************\n" +
					"SensorValue: " + this.sensorValue + "\n" +
					"Voltage: " + this.voltage + "\n" + 
					"Watts: " + this.watts + "\n" +
					"Date: " + this.date + "\n" +
					"**********************\n";
		}
	}
	/**
	 * Class provides iteration logic 
	 * */
	private class SensorIterator implements Iterator{
		
		int index;
        
		@Override
		public boolean hasNext() {
			if(index < sensorData.size() )
				return true;
			return false;
		}
        
		@Override
		public LightSensorData next() {
			if(this.hasNext())
				return sensorData.get(index++);
			return null;
		}
	}
}