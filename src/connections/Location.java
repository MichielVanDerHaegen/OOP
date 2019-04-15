package connections;
import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;
import be.kuleuven.cs.som.annotate.Raw;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Location {

	public static void main(String[] args) {
		Location theLocation = new Location(new double[] {10.0,20.0},"Leuven");
		assertEquals(10.0,theLocation.getCoordinate()[0],0.05);
		assertEquals(20.0,theLocation.getCoordinate()[1],0.05);
		assertEquals("Leuven",theLocation.getAddress());
	}
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
	 * Variable referencing a map collecting all the adjoining roads connected to each location.
	 *
	 * @invar The referenced map is effective
	 *		| roadMap != null
	 * @invar Each key registered in the map is an effective location.
	 *		| for each key in roadMap.keySet():
	 *		|(key != null)
	 * @invar Each value associated with a key in this map is an effective, non-terminated road connected to this location
	 * 		  and involving a location whose coordinates are identical to the associated keys coordinates.
	 *		| for each key in roadMap.keySet():
	 *		|	roadMap.get(key) != null &&
	 *		|	(! roadMap.get(key).isTerminated()) &&
	 *		|	(roadMap.get(key).getEndpoint1() == this.getCoordinate()) ||
	 *		|	(roadMap.get(key).getEndpoint2() == this.getCoordinate())
	 *		|	(roadMap.get(key).getCoordinate() == this.getCoordinate())
	 */
	private final Set<Road> roadMap = new HashSet<>();

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
	public Location(double[] coordinate, String address) throws IllegalArgumentException {
		assert canHaveAsCoordinate(coordinate);
		this.coordinate = coordinate.clone();
		this.setAddress(address);
		if (this.address.equals(""))
			throw new IllegalArgumentException();
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
	
	public boolean isValidCoordinate(double[] coordinate) {
		return ((coordinate[0]!=Double.POSITIVE_INFINITY)&&(coordinate[1]!=Double.POSITIVE_INFINITY)&&(coordinate[0]!=Double.NEGATIVE_INFINITY)&&(coordinate[1]!=Double.NEGATIVE_INFINITY));
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
		if(coordinate.length==2) {
			if(isValidCoordinate(coordinate)) {
					return true;
			}
		}
		return false;	
	}

	/**
	 * Checks if this location is terminated.
	 */
	@Basic
	public boolean isTerminated() {
		return this.isTerminated;
	}

	public void terminate(){
		if(!this.isTerminated) {
			for(Road road : roadMap)
				road.terminate();
			this.isTerminated=true;
		}
	}

	/**
	 * Checks to see if this location has a road as one of its adjoining roads.
	 * @param road The road to check
	 * @return True if the road given has this location as one of its two endpoints.
	 * 		| result == (roadMap.contains(road))
	 */
	@Basic
	public boolean hasAsAdjoiningRoad(Road road){
		try {
			return (roadMap.contains(road));
		}
		catch(NullPointerException npe) {
			throw new NullPointerException();
		}
	}

	/**
	 * Checks to see if the given road can be an adjoining road for this location.
	 * @param road The road to check
	 * @return  True if one of the two endpoints of the given road is this location, and that the road is not terminated.
	 * 		|   result == (road.getEndPoint1() == this || road.getEndPoint2() == this) &&
	 * 		|	!road.isTerminated()
	 */
	public boolean canHaveAsAdjoiningRoad(Road road){
		return (road.getEndPoint1() == this || road.getEndPoint2() == this) && !road.isTerminated();
	}

//	/**
//	 * Checks to see if every adjoining road for this location is proper.
//	 * @return True if each road for this
//	 */
//	//NOT SURE IF WE NEED THIS METHOD
//	public boolean hasProperAdjoiningRoads(){
//		for (List<Road> road : roadMap.values()){
//			for (Road road1 : road){
//				if (!canHaveAsAdjoiningRoad(road1))
//					return false;
//			}
//			//if ()
//		}
//		return true;
//	}

	/**
	 * Returns all the adjoining roads for the given location.
	 * @param location The location to check
	 * @return A list of Roads that are adjoining to the given location
	 * 		| result == roadMap.get(this)
	 */
	public Set<Road> getAdjoiningRoads() throws NullPointerException{
		return roadMap;
	}

	void addAdjoiningRoad(Road road) {
		assert (this != null);
		assert (road != null);
		assert (road.getEndPoint1()==this) || (road.getEndPoint2()==this);
		this.roadMap.add(road);
	}
	
	void removeAdjoiningRoad(Road road) {
		assert (hasAsAdjoiningRoad(road));
		assert (road == null);
		this.roadMap.remove(road);
	}
	
	

}
