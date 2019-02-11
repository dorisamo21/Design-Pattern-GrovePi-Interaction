package ccsu.edu.grovepicomponents;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import edu.ccsu.utility.CommonConstants;

/**
 * Class contains common methods used throughout package
 * @author Adrian
 * @author Kim
 * @author Ga Young
 */
 class GrovePiUtilities {

	private GrovePiUtilities() {
		//prevent instantiation
	}
	
	/**
	 * Give method a pythonFileName stored in pythonScripts.  Will run the script.  
	 * If an error occurred with return a String equaling "Error"
	 * @param pythonFileName
	 * @param function
	 * @returns String
	 */
	public static String callPython(String pythonFileName, String function) {
		StringBuilder response = new StringBuilder();
		 
		try {
			Process process = null;
			if(function == null || function.isEmpty()) {
				process = Runtime.getRuntime().exec(CommonConstants.PYTHON + CommonConstants.BLANK + CommonConstants.RELATIVE_PATH +  pythonFileName);
			}
			else {
				process = Runtime.getRuntime().exec(CommonConstants.PYTHON + CommonConstants.BLANK + 
												CommonConstants.RELATIVE_PATH +  pythonFileName + CommonConstants.BLANK +
												function);
			}
			//get output from process and return it to the caller
			BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String bufferString = "";
			while((bufferString = input.readLine()) != null)
				response.append(bufferString);
			input.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(response.toString());
		return response.toString();			
	}
	
	/**
	 * TODO- delete this method completely unneccessary 
	 * Builds the string needed in calling python process
	 * @param portNumber
	 * @param params
	 * @return
	 */
	public static String buildArgsString(String portNumber, String params) {
		StringBuilder args = new StringBuilder();
		//NOTE - our custom Python code always will have portNumber as first entry in params string
		args.append(portNumber.substring(1));
		String[] strToAppend = params.split("\\s+");
		for(String str: strToAppend) {
			args.append(CommonConstants.BLANK);
			args.append(str);			
		}
		return args.toString();
	}
	
	/**
	 * Returns true if you run code on raspberry pi
	 * @return
	 */
	public static boolean checkOperatingSystem() {
		String os = System.getProperty("os.name").trim().toLowerCase();
		if(!os.contains(CommonConstants.LINUX)){
			return false;
		}
		return true;
	}
}