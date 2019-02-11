package edu.ccsu.interfaces;

/**
 * Screen enabled device that provides functionality
 * to print Messages and change the sceen color
 * @author Adrian
 *
 */
public interface ScreenEnabledDevice extends LightEnabledDevice {

	/**
	 * Print a message to the 
	 * @param message
	 */
	public void printMessage(String message);
	
	/**
	 * Print a message to the LCD RGB Backlight for
	 * a certain number of seconds
	 * @param message
	 * @param duration
	 */
	public void printMessage(String message, int duration);

	/**
	 * Print a message to the LCD RGB with Backlight color 
	 * @param message	message printed on Lcd Screen
	 * @param color 	Color of Lcd Screen
	 */
	public void printMessageColor(String message, String Color);	
	
	/**
	 * Gets the color
	 * @return
	 */
	public String getColor();
	
	/**
	 * Set the color
	 * @param RGB
	 */
	public void setColor(String rgb);
}
