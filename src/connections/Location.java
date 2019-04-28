package connections;
import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashSet;
import java.util.Set;
//not sure what invars we need
/**
 * A class of locations where each location has an address and a longitude and latitude coordinate.
 *
 * @invar The referenced map is effective
 * 		| roadMap != null
 * @invar Each key registered in the map is an effective location.
 * 		| for each key in roadMap.keySet():
 * 		|(key != null)
 * @invar Each value associated with a key in this map is an effective, non-terminated road connected to this location
 * 		  and involving a location whose coordinates are identical to the associated keys coordinates.
 * 		| for each key in roadMap.keySet():
 * 		|	roadMap.get(key) != null &&
 * 		|	(! roadMap.get(key).isTerminated()) &&
 * 		|	(roadMap.get(key).getEndpoint1() == this.getCoordinate()) ||
 * 		|	(roadMap.get(key).getEndpoint2() == this.getCoordinate())
 * 		|	(roadMap.get(key).getCoordinate() == this.getCoordinate())
 * @invar Each location can have its coordinate as coordinate. |
 *      |  canHaveAsCoordinate(this.getCoordinate())
 *
 *
 * @author Michiel Van der Haegen
 * @author Sam Haberman
 */
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
	 * Variable referencing a map collecting all the adjoining roads connected to each location.
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
	public Location(double[] coordinate, String address) {
		assert canHaveAsCoordinate(coordinate);
		this.coordinate = coordinate.clone();
		this.setAddress(address);
	}

	/**
	 * Return the address of this location.
	 */
	@Basic
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
		if(address!=null) {
			if (address.length() >= minAddressLength) {
				if (Character.isUpperCase(address.charAt(0))) {
					if (address.matches("(\\w|\\s|\\,)+")) {
						return true;
					}
					return false;
				}
				return false;
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
	public void setAddress(String address) {
		if (isValidAddress(address))
			this.address = address;
		else
			this.address = "Celestijnenlaan 200A, 3001 Heverlee";
	}

	/**
	 * Return the coordinate of this location.
	 */
	@Basic
	@Immutable
	public double[] getCoordinate() {
		double clone1 = this.coordinate[0];
		double clone2 = this.coordinate[1];
		double[] clone = new double[] {clone1,clone2};
		return clone;
	}

	/**
	 * Checks to see if the given coordinate is valid.
	 * @param coordinate The coordinate to check
	 * @return True if the given coordinate is not equal to positive or negative infinity and is not Not A Number.
	 * 		| result == ((coordinate!=Double.POSITIVE_INFINITY)&&(coordinate!=Double.POSITIVE_INFINITY)&&(!Double.isNaN(coordinate)))
	 */
	public boolean isValidCoordinate(double coordinate) {
		return ((coordinate!=Double.POSITIVE_INFINITY)&&(coordinate!=Double.POSITIVE_INFINITY)&&(!Double.isNaN(coordinate)));
	}

	/**
	 * Check whether this location can have the given coordinate as its coordinate.
	 * 
	 * @param coordinate The coordinate to check.
	 * @return True if the coordinate (both longitude and latitude) are valid and not null, and if coordinate only has a length of two.
	 * | result == (coordinate!=null && coordinate.length == 2 && isValidCoordinate(coordinate[0]) && isValidCoordinate(coordinate[1]))
	 */
	public boolean canHaveAsCoordinate(double[] coordinate) {
		if(coordinate==null)
			return false;
		if(coordinate.length!=2)
			return false;
		if(!isValidCoordinate(coordinate[0]))
			return false;
		if(!isValidCoordinate(coordinate[1]))
			return false;
		return true;
	}

	/**
	 * Checks if this location is terminated.
	 */
	@Basic
	public boolean isTerminated() {
		return this.isTerminated;
	}

	/**
	 * Terminates this location
	 *
	 * @post
	 */
	public void terminate(){
		if(!this.isTerminated) {
			this.isTerminated=true;
			HashSet<Road> cloneSet = new HashSet<>();
			cloneSet.addAll(roadMap);
			for(Road road : cloneSet) {
				road.terminate();
			}
			roadMap.clear();

		}
	}

	/**
	 * Checks to see if this location has a road as one of its adjoining roads.
	 * @param road The road to check
	 * @return True if the road given has this location as one of its two endpoints.
	 * 		| result == (roadMap.contains(road))
	 */
	@Basic
	public boolean hasAsAdjoiningRoad(Road road) throws NullPointerException{
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
	 * @return A list of Roads that are adjoining to the given location
	 * 		| result == roadMap
	 */
	public Set<Road> getAdjoiningRoads() throws NullPointerException{
		HashSet<Road> cloneSet = new HashSet<>();
		cloneSet.addAll(roadMap);
		return cloneSet;
	}

	void addAdjoiningRoad(Road road) {
		assert road != null;
		assert (road.getEndPoint1()==this) || (road.getEndPoint2()==this);
		this.roadMap.add(road);
	}
	
	void removeAdjoiningRoad(Road road) {
		//assert hasAsAdjoiningRoad(road);
		assert road.isTerminated();
		this.roadMap.remove(road);
	}
	
	@Override
	public String toString() {
		return "This location, "+this.getAddress()+" has the following coordinates: "+this.getCoordinate()[0]+", "+this.getCoordinate()[1];
	}
}
