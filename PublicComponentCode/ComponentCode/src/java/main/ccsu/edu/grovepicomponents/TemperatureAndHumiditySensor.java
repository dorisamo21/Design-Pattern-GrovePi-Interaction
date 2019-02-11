package ccsu.edu.grovepicomponents;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import edu.ccsu.error.IncompatibleSensorError;
import edu.ccsu.interfaces.Iterator;
import edu.ccsu.utility.CommonConstants;

/***
 * Temperature and humidity sensor that reads data from
 * the GrovePi temp/humidity sensor and returns that data
 *
 */
public class TemperatureAndHumiditySensor extends Sensor {

	private List<TempAndHumidityData> sensorData;
	/**
	 * Creates a new Temperature and Humidity Sensor
	 * @param name
	 * @param portNumber
	 */
	public TemperatureAndHumiditySensor(String name, String portNumber) {
		this.name = name;
		this.portNumber = portNumber;
		this.sensorData = new ArrayList<>();
		this.sensorFile = CommonConstants.TEMPHUMIDITY;
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
			String fahrenheit = makeIntoData[0];
			String celcius = makeIntoData[1];
			String humidityPercentage = makeIntoData[2];
			Date date = new Date();
			builder.append("Fahrenheit: " + fahrenheit + " Celcius: " + celcius + " HumidityPercentage: " + humidityPercentage + " Date: "+ date + "\n");
			//value from output will be three numbers, first is fahrenheit, second is celcius, third is humidity percentage
			sensorData.add(new TempAndHumidityData(Float.parseFloat(fahrenheit), Float.parseFloat(celcius), Float.parseFloat(humidityPercentage), date));
		}
		return builder.toString();
	}
	
	@Override
	protected boolean checkPort(String portNumber) {
		if(!this.getPortNumber().contains("D")) {
			System.out.println("Must use a digital port starting with D");
			return false;
		}
		return true;
	}
	
	@Override
	public void setNextSensor(Sensor nextSensor) throws IncompatibleSensorError {
		if(nextSensor instanceof TemperatureAndHumiditySensor) {
			this.nextSensor = nextSensor;
		} else {
			throw new IncompatibleSensorError("Sensor not compatible with sensor. Sensor chain can only be use other Sensors");
		}
	}
    
	@Override
	public Iterator getIterator() {
		return new TempAndHumidityIterator();
	}
	
	/**
	 * Inner class to store data 
	 * @author Adrian
	 *
	 */
	private class TempAndHumidityData{
		 float degreesFahrenheit;
		 float celcius;
		 float humidityValue;
		 Date date;
		 /**
		  * Takes in data from temperature and humidity sensor and stores it
		  * @param degreesFahrenheit
		  * @param celcius
		  * @param humidityValue
		  * @param date
		  */
		 public TempAndHumidityData(float degreesFahrenheit, float celcius, float humidityValue, Date date) {
			 this.degreesFahrenheit = degreesFahrenheit;
			 this.celcius = celcius;
			 this.humidityValue = humidityValue;
			 this.date = date;
		 }
		 /**
		  * Returns data as string
		  */
		 public String toString() {
			 return "\n******************\n" + 
					 "DegressFahrenheit: " + this.degreesFahrenheit + "\n" +
					 "DegressCelcius: " + this.celcius + "\n" +
					 "Humidity Percentage: " + this.humidityValue + "\n" +
					 "Date: " + this.date + 
					 "\n******************\n";
		 }
	}
	/**
	 * Inner Class for iterating over tempAndHumidity data
	 * */
	private class TempAndHumidityIterator implements Iterator{

		int index;
       
		@Override
		public boolean hasNext() {
			if(index < sensorData.size() )
				return true;
			return false;
		}
        
		@Override
		public TempAndHumidityData next() {
			if(this.hasNext())
				return sensorData.get(index++);
			return null;
		}
	}
}