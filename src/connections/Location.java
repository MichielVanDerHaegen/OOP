package connections;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;
import be.kuleuven.cs.som.annotate.Raw;

public class Location {

	/**
	 * Variable registering the address of this location.
	 */
	private String address;

	/**
	 * Variable that tracks the minimum number of characters a valid address can have.
	 */
	private final int minAddressLength = 2;

	/**
	 * Variable registering the coordinates (longitude and latitude) of this location.
	 */
	private final double[] coordinate;

	/**
	 * Variable registering whether or not this location has been terminated.
	 */
	private boolean isTerminated = false;

	/**
	 * Initialize a new Location that is not terminated, with given Address, and set of Longitude and Latitude coordinates
	 *
	 * @param address
	 * 		The address of this location
	 * @param coordinate
	 * 		The coordinates (longitude and latitude) of this location
	 * @throws IllegalArgumentException
	 *
	 * @pre The given coordinates must be a set of valid coordinates
	 * 		| canHaveAsCoordinate(coordinate)
	 * @post The address of this road will be equal to the given address
	 * 		| new.getAddress() == address
	 * @post The coordinates of this location will be equal to the given coordinates
	 * 		| new.getCoordinate() == coordinate
	 */
	public Location(String address, double[] coordinate) throws IllegalArgumentException {
		this.setAddress(address);
		if (this.address.equals(""))
			throw new IllegalArgumentException();
		assert canHaveAsCoordinate(coordinate);
		this.coordinate = coordinate.clone();
	}

	/**
	 * Return the address of this location.
	 */
	@Basic
	@Raw
	public String getAddress() {
		return this.address;
	}

	/**
	 * Checks whether the given address is a valid address for any location.
	 * 
	 * @param address The address to check.
	 * @return  True if the address consists of at least the minimum address length and can only contain letters, digits,
	 * 			commas and spaces. The address of each location must start with a capital letter.
	 * 			| if (address.length() >= minAddressLength)
	 * 			| 	then if (Character.isUpperCase(address.charAt(0)))
	 * 			|		address.matches("(\\w|\\s|\\,)+"))
	 */
	public boolean isValidAddress(String address) {
		if (address.length() >= minAddressLength)
			if (Character.isUpperCase(address.charAt(0))) {
				if (address.matches("(\\w|\\s|\\,)+")) {
					return true;
				}
				return false;
			}
		return false;
	}

	/**
	 * Set the address of this location to the given address.
	 * 
	 * @param address The new address for this location.
	 * @post If the given address is a valid address for any location, the address
	 *       of this new location is equal to the given address. | if
	 *       (isValidAddress(address)) | then new.getAddress() == address
	 */
	@Raw
	public void setAddress(String address) {
		if (isValidAddress(address))
			this.address = address;
		else
			this.address = "";
	}

	/**
	 * @invar Each location can have its coordinate as coordinate. |
	 *        canHaveAsCoordinate(this.getCoordinate())
	 */

	/**
	 * Return the coordinate of this location.
	 */
	@Basic
	@Raw
	@Immutable
	public double[] getCoordinate() {
		return this.coordinate;
	}

	/**
	 * Check whether this location can have the given coordinate as its coordinate.
	 * 
	 * @param coordinate The coordinate to check.
	 * @return True if the coordinate (both longitude and latitude) is a finite number
	 * | result ==
	 */
	@Raw
	public boolean canHaveAsCoordinate(double[] coordinate) {
		return ((coordinate[0]!=Double.POSITIVE_INFINITY)&&(coordinate[1]!=Double.POSITIVE_INFINITY)&&(coordinate[0]!=Double.NEGATIVE_INFINITY)&&(coordinate[1]!=Double.NEGATIVE_INFINITY));
	}

	/**
	 * Checks if this location is terminated.
	 */
	@Basic
	public boolean isTerminated() {return this.isTerminated;}

	public void terminate(){

	}

	//Still have to add adjoining roads --> HashMap where key is location and value is all the roads connected to this location


}
