package edu.ccsu.interfaces;

/**
 * Iterator used by classes that realize sensor interface.
 * Used to iterate through the data these classes hold
 * @author Adrian
 *
 */
public interface Iterator {
	/***
	 * Checks to see if next element in collection exists 
	 * @return
	 */
	public boolean hasNext();
	/**
	 * Returns next element in collection
	 * @return
	 */
	public Object next();
	
}