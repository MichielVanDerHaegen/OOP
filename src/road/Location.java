package road;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;
import be.kuleuven.cs.som.annotate.Raw;


public class Location {

	/**
	 * Variable registering the address of this location.
	 */
	private String address;

	private final int minAddressLength = 2;

	private final double[] endPoint1;
	/**
	 * The second endpoint of the road
	 */
	private final double[] endPoint2;
	/**
	 * The maximum coordinate an endpoint can have
	 */
	private static double MAX_COORDINATE = 70.0;

	public Location(String address, double[] endPoint1, double[] endPoint2) {
		this.setAddress(address);
		assert isValidEndPoint(endPoint1);
		assert isValidEndPoint(endPoint2);
		this.endPoint1 = endPoint1.clone();
		this.endPoint2 = endPoint2.clone();
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
	 * Every location has a particular address, which consists of at least two
	 * characters and can only contain letters, digits, commas and spaces. The
	 * address of each location must start with a capital letter. Check whether the
	 * given address is a valid address for any location.
	 * 
	 * @param propertyName_Java The address to check.
	 * @return | result ==
	 */
	public boolean isValidAddress(String address) {
		String pattern = new String("(\w|\s|\,)*");
		if (address.length() >= minAddressLength)
			if (Character.isUpperCase(address.charAt(0))) {
				if (address.matches()) {
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
	}

	/**
	 * Returns the first endpoint of this road.
	 */
	@Basic
	@Immutable
	public double[] getEndPoint1() {
		return this.endPoint1;
	}

	/**
	 * Returns the second endpoint of this road.
	 */
	@Basic
	@Immutable
	public double[] getEndPoint2() {
		return this.endPoint2;
	}

	/**
	 * Returns both endpoints of this road.
	 */
	@Immutable
	public double[][] getEndPoints() {
		double[][] endpoints = new double[][] { new double[] { endPoint1[0], endPoint1[1] },
				new double[] { endPoint2[0], endPoint2[1] } };
		return endpoints;
	}

	/**
	 * Sets the maximum value a coordinate can have to the given value.
	 * 
	 * @param value The new maximum value for coordinates.
	 * @post The maximum value a coordinate can have is set to the given value |
	 *       new.getMaxCoordinate() == value
	 * @throws IllegalArgumentException If the given value is not between 0 and 360
	 *                                  degrees | ((value <= 0.0) || (value >=
	 *                                  360.0))
	 */
	public static void setMaxCoordinate(double value) throws IllegalArgumentException {
		if ((value <= 0.0) || (value >= 360.0))
			throw new IllegalArgumentException();
		MAX_COORDINATE = value;
	}

	/**
	 * Returns the maximum value a coordinate can have
	 */
	public static double getMaxCoordinate() {
		return MAX_COORDINATE;
	}

	/**
	 * Checks to see if the given coordinate is valid.
	 * 
	 * @param coordinate The coordinate value to check
	 * @return True if the coordinate is both greater than or equal to 0 and less
	 *         than or equal to the Max coordinate | result == ((coordinate >= 0.0)
	 *         && (coordinate <= MAX_COORDINATE))
	 */
	public boolean isValidCoordinate(double coordinate) {
		return ((coordinate >= 0.0) && (coordinate <= MAX_COORDINATE));
	}

	/**
	 * Checks whether the given endpoint is a valid endpoint for any road.
	 * 
	 * @param endpoint The endpoint to check.
	 * @return True if the number of coordinates is equal to two and both
	 *         coordinates are valid endpoints | result == (endpoint.length==2) &&
	 *         (isValidCoordinate(endpoint[0])) && (isValidCoordinate(endpoint[1]))
	 * 
	 */
	public boolean isValidEndPoint(double[] endpoint) {
		if (endpoint.length == 2) {
			if (isValidCoordinate(endpoint[0])) {
				if (isValidCoordinate(endpoint[1])) {
					return true;
				}
			}
		}
		return false;
	}
}
