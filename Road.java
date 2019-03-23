import java.util.ArrayList;

import be.kuleuven.cs.som.annotate.*;

public class Road {

	private String ID;
	private ArrayList<String> idArray = new ArrayList<>();
	private int minIDLength = 2;
	private int maxIDLength = 3;
	private double[] startCoordinate;
	private double[] endCoordinate;
	private double MAX_COORDINATE = 70.0;
	private int length;
	private float speedlimit = (float)19.5
	private float roadSpeed;
	private float delayDirectionOne = 0;
	private float delayDirectionTwo = 0;
	private boolean blockedDirectionOne = false;
	private boolean blockedDirectionTwo = false;

	/**
	 * Initialize a new road with given ID, Start and End coordinates and
	 * roadspeed. The road will be given the standard speed limit of 19.5 m/s.
	 *
	 * @param id        The unique identifier for our new road.
	 * @param startCoordinate The coordinates of the start of the new road.
	 * @param endCoordinate The coordinates of the end of the new road.
	 * @param roadSpeed The average speed obtained on the new road under standard
	 *                  conditions.
	 * @pre The given startCoordinates must be valid coordinates for a road
	 * 		| isValidCoordinate(startCoordinate)
	 * @pre The given endCoordinates must be valid coordinates for a road
	 * 		| isValidCoordinate(endCoordinate)
	 * @post The ID of this new road will be equal to the given id | new.getID() ==
	 *       id
	 * @post The start coordinates of the new road will be equal to the given startCoordinates
	 * 		| new.get
	 * @post The end coordinates of the new road will be equal to the given endCoordinates
	 * 		| new.get
	 * @post The average roadspeed for the new road will be equal to the given roadSpeed
	 * 		| new.getRoadSpeed() == roadSpeed
	 */
	public Road(String id, double[] startCoordinate, double[] endCoordinate, float roadSpeed) {
		setID(id);
		assert isValidCoordinate(startCoordinate);
		assert isValidCoordinate(endCoordinate);
		this.startCoordinate = startCoordinate; //switch to setter
		this.endCoordinate = endCoordinate; //switch to setter
		setAvgRoadSpeed(roadSpeed);
	}

	/**
	 * Initialize a new road with given ID, Start and End coordinates, speed limit and
	 * roadspeed.
	 *
	 * @param id        The unique identifier for our new road.
	 * @param startCoordinate The coordinates of the start of the new road.
	 * @param endCoordinate The coordinates of the end of the new road.
	 * @param roadSpeed The average speed obtained on the new road under standard
	 *                  conditions.
	 * @pre The given startCoordinates must be valid coordinates for a road
	 * 		| isValidCoordinate(startCoordinate)
	 * @pre The given endCoordinates must be valid coordinates for a road
	 * 		| isValidCoordinate(endCoordinate)
	 * @post The ID of this new road will be equal to the given id | new.getID() ==
	 *       id
	 * @post The start coordinates of the new road will be equal to the given startCoordinates
	 * 		| new.get
	 * @post The end coordinates of the new road will be equal to the given endCoordinates
	 * 		| new.get
	 * @post The speed limit of the new road will be equal to the given speedlimit
	 * 		| new.getSpeedLimit() == speedlimit
	 * @post The average roadspeed for the new road will be equal to the given roadSpeed
	 * 		| new.getRoadSpeed() == roadSpeed
	 */
	public Road(String id, double[] startCoordinate, double[] endCoordinate, float speedlimit, float roadSpeed) {
		setID(id);
		assert isValidCoordinate(startCoordinate);
		assert isValidCoordinate(endCoordinate);
		this.startCoordinate = startCoordinate; //switch to setter
		this.endCoordinate = endCoordinate; //switch to setter
		setSpeedLimit(speedlimit);
		setAvgRoadSpeed(roadSpeed);
	}

	/**
	 * Sets the Identification of the road to the given ID value, if it is unique.
	 * If the road ID is being changed, delete the old ID from the ID list and add
	 * the new ID to the ID list
	 * 
	 * @param ID The new unique ID for our road
	 * @post The ID of the road is set to the given ID | new.getID() == ID
	 * @throws IllegalArgumentException The given ID of the road is not valid. |
	 *                                  !isValidID(ID)
	 * @throws NullPointerException
	 * 			The given ID is null
	 * 		|	ID == null
	 */

	public void setID(String ID) throws IllegalArgumentException, NullPointerException {
		if (!isValidID(ID)) {
			throw new IllegalArgumentException();
		}
		String oldID = this.getID();
		if (oldID != null) {
			idArray.remove(oldID);
		}
		idArray.add(ID);
		this.ID = ID;
	}

	/**
	 * Returns the unique identification of our road.
	 */
	@Basic
	public String getID() {
		return ID;
	}

	/**
	 * Checks to see if the ID given is valid
	 * 
	 * @param ID The ID of the road to check
	 * @return True if the ID length is between the minimum and maximum ID length,
	 *         if the ID follows the correct naming conventions is a unique ID not
	 *         used for another road. | (ID.length()>= getMinIDLength() ||
	 *         ID.length() <= getMaxIDLength()) && correctIDFormat(ID) &&
	 *         isUniqueID(ID)
	 */
	public boolean isValidID(String ID) {
		if (ID.length() >= getMinIDLength() || ID.length() <= getMaxIDLength()) {
			if (correctIDFormat(ID)) {
				isUniqueID(ID);
			}
		}
		return false;
	}

	// fix the documentation using quantifiers - maybe good now?
	/**
	 * Checks to see if the given ID follows the correct naming conventions
	 * 
	 * @param ID The ID to be checked
	 * @return True if the first character as a UpperCase letter and then has at
	 *         least one number following
	 *         | if (Character.isUpperCase(ID.charAt(0)))
	 *         then for each additional character i in 1:ID.length():
	 *         Character.isDigit(i))
	 */
	public boolean correctIDFormat(String ID) {
		if (Character.isUpperCase(ID.charAt(0))) {
			for (int i = 1; i < ID.length(); i++) {
				if (!Character.isDigit(i)) {
					return false;
				}
			}
		}
		return true;
	}

	// fix the documentation using quantifiers - maybe good now?
	/**
	 * Checks the given ID to see if it is Unique
	 * 
	 * @param   ID The ID to be checked
	 * @return	True if the ID given is not used for another road
	 * 		 |  for each element in idArray:
	 *		    !ID.equals(element)
	 */
	public boolean isUniqueID(String ID) {
		for (String i : idArray) {
			if (ID.equals(i)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Sets the minimum ID length for the roads identifier
	 * 
	 * @param minIDLength The minimum ID length to be set
	 * @post The minimum ID length is set to the given value | new.getMinIDLength()
	 *       == minIDLength
	 * @throws NullPointerException     If given value is null | minIDLength == null
	 * @throws IllegalArgumentException If the given value is less than 2 or greater
	 *                                  than the maximum ID length | if(minIDLength
	 *                                  <2 || minIDLength > maxIDLength)
	 */
	public void setMinIDLength(int minIDLength) throws NullPointerException, IllegalArgumentException {
		if (minIDLength < 2 || minIDLength > maxIDLength) {
			throw new IllegalArgumentException();
		}
		this.minIDLength = minIDLength;
	}

	/**
	 * Returns the minimum ID length
	 */
	@Basic
	public int getMinIDLength() {
		return minIDLength;
	}

	/**
	 * Sets the maximum ID length for the roads identifier
	 * 
	 * @param maxIDLength The maximum ID length to be set
	 * @post The maximum ID length is set to the given value | new.getMaxIDLength()
	 *       == maxIDLength
	 * @throws NullPointerException     If given value is null | maxIDLength == null
	 * @throws IllegalArgumentException If the given value is less than the minimum
	 *                                  ID length or greater than the maximum
	 *                                  Integer Value allowed | maxIDLength <
	 *                                  minIDLength || maxIDLength >
	 *                                  Integer.MAX_VALUE)
	 */
	public void setMaxIDLength(int maxIDLength) throws NullPointerException, IllegalArgumentException {
		if (maxIDLength < minIDLength || maxIDLength > Integer.MAX_VALUE) {
			throw new IllegalArgumentException();
		}
		this.maxIDLength = maxIDLength;

	}

	/**
	 * Returns the maximum ID length
	 */
	@Basic
	public int getMaxIDLength() {
		return maxIDLength;
	}

	/**
	 * Sets the length of the road to the given length in meters.
	 *
	 * @param length The new length for the given road in meters.
	 * @post The length of the road is set to 1 if the given length is 0 | if
	 *       (length == 0) then new.getLength() == 1)
	 * @post The length of the road is set to the absolute value of the given length
	 *       | new.getLength() == Math.abs(length)
	 */
	public void setLength(int length) {
		if (length == 0)
			this.length = 1;
		this.length = Math.abs(length);
	}

	/**
	 * Returns the length of the road expressed in meters.
	 */
	@Basic
	public int getLength() {
		return length;
	}

	private final float maxSpeed = (float) 299792458.0;

	/**
	 * Sets the speedlimit of the road to the given value in meters per second.
	 * 
	 * @param speedlimit The new speed limit for the road in meters per second.
	 * @post The speed limit of the road is set to the given speed limit |
	 *       new.getSpeedLimit() == speedlimit
	 * @throws IllegalArgumentException The given speed limit for the road is not
	 *                                  valid | !isValidSpeedLimit(speedlimit)
	 * @throws NullPointerException
	 * 			The given speed limit is null
	 * 		|	speedlimit == null
	 */
	public void setSpeedLimit(float speedlimit) throws IllegalArgumentException, NullPointerException {
		if (!isValidSpeedLimit(speedlimit))
			throw new IllegalArgumentException(); // What exception should be throwing?
		this.speedlimit = speedlimit;
	}

	/**
	 * Returns the speed limit for the road in meters per second
	 */
	@Basic
	public float getSpeedlimit() {
		return speedlimit;
	}

	/**
	 * Checks to see if the given speed limit for the road is acceptable
	 * 
	 * @param speedlimit The speed limit to check
	 * @return True if the given speed limit is greater than 0 and less than or
	 *         equal to the max speed limit | result == (speedlimit > 0 &&
	 *         speedlimit <= maxSpeed)
	 */
	public boolean isValidSpeedLimit(float speedlimit) {
		return (speedlimit > 0 && speedlimit <= maxSpeed);
	}

	/**
	 * Sets the average road speed of the road to the given value in meters per
	 * second
	 * 
	 * @param roadspeed The new average speed obtained by driving on the road under
	 *                  standard conditions
	 * @post The average speed of the road under standard conditions is set to the
	 *       given roadspeed value | new.getRoadSpeed() == roadspeed
	 * @throws IllegalArgumentException The given roadspeed for the road is not
	 *                                  valid | !isValidRoadSpeed(roadspeed)
	 * @throws NullPointerException
	 * 			The given roadspeed is null
	 * 		|	roadspeed == null
	 */
	public void setAvgRoadSpeed(float roadspeed) throws IllegalArgumentException, NullPointerException{
		if (!isValidRoadSpeed(roadspeed))
			throw new IllegalArgumentException();
		this.roadSpeed = roadspeed;
	}

	/**
	 * Returns the average speed obtained driving on the road under standard
	 * conditions
	 */
	@Basic
	public float getRoadSpeed() {
		return roadSpeed;
	}

	/**
	 * Checks to see if the given roadSpeed value is acceptable
	 * 
	 * @param roadSpeed The average road speed under standard conditions to check
	 * @return True if the given road speed is greater than 0 and less than or equal
	 *         to the roads speed limit | result == (roadSpeed > 0 && roadSpeed <=
	 *         speedlimit)
	 */
	public boolean isValidRoadSpeed(float roadSpeed) {
		return (roadSpeed > 0 && roadSpeed <= speedlimit);
	}

	public void setMaxCoordinate(double coordinate) {
		this.MAX_COORDINATE = coordinate;
	}

	/**
	 * Checks if the given coordinate is a valid coordinate
	 * 
	 * @param coordinate the coordinate to check
	 * @return returns true if both the lattitude and longitude of the given
	 *         coordinate are greater than or equal to zero and less than or equal
	 *         to the maximum coordinate | result == ((coordinate[0] > 0) &&
	 *         (coordinate[1] > 0) && (coordinate[0] <= MAX_COORDINATE) &&
	 *         (coordinate[1] <= MAX_COORDINATE));
	 * 
	 */
	public boolean isValidCoordinate(double[] coordinate) {
		return ((coordinate[0] > 0) && (coordinate[1] > 0) && (coordinate[0] <= MAX_COORDINATE)
				&& (coordinate[1] <= MAX_COORDINATE));
	}

	/**
	 * Sets the delay of the road in Direction One to the given delay in seconds or to infinity
	 *
	 * @param delay
	 *		The new delay time for the road going towards Direction One
	 * @pre	The given delay of the road must be a valid delay for a road
	 * 		| isValidDelay(delay)
	 * @post The delay of the road going towards Direction One is equal to the given delay
	 * 		| new.getDelayDirectionOne() == delay
	 */
	public void setDelayDirectionOne(float delay){
		assert isValidDelay(delay);
		this.delayDirectionOne = delay;
	}

	/**
	 * Sets the delay of the road in Direction Two to the given delay in seconds or to infinity
	 *
	 * @param delay
	 *		The new delay time for the road going towards Direction Two
	 * @pre	The given delay of the road must be a valid delay for a road
	 * 		| isValidDelay(delay)
	 * @post The delay of the road going towards Direction Two is equal to the given delay
	 * 		| new.getDelayDirectionTwo() == delay
	 */
	public void setDelayDirectionTwo(float delay){
		assert isValidDelay(delay);
		this.delayDirectionTwo = delay;
	}

	/**
	 * Checks to see if the given delay value for the road is valid
	 * @param delay
	 * 		The delay in seconds to check
	 * @return True if and only if the given delay is non negative or positive infinity
	 *		result == (delay >= 0 || delay == Float.POSITIVE_INFINITY)
	 */
	public boolean isValidDelay(float delay){
		delay >= 0 || delay == Float.POSITIVE_INFINITY;

	}

	/**
	 * Returns the delay in seconds for the road in Direction Two
	 */
	public float getDelayDirectionOne(){return delayDirectionOne;}

	/**
	 * Returns the delay in seconds for the road in Direction Two
	 */
	public float getDelayDirectionTwo(){return delayDirectionTwo;}


	/**
	 * Sets the blcoked status of the road going towards direction one to the given boolean blocked value.
	 * True = blocked
	 * False = not blocked
	 * @param blocked
	 * 		The new blocked status of the road.
	 * 	|	new.isBlockedDirectionOne() == blocked
	 */
	public void setBlockedDirectionOne(boolean blocked){
		this.blockedDirectionOne = blocked;
	}

	/**
	 * Sets the blcoked status of the road going toward direction two to the given boolean blocked value.
	 * True = blocked
	 * False = not blocked
	 * @param blocked
	 * 		The new blocked status of the road.
	 * 	|	new.isBlockedDirectionTwo() == blocked
	 */
	public void setBlockedDirectionTwo(boolean blocked){
		this.blockedDirectionTwo = blocked;
	}

	/**
	 * Returns the blocked status of the road going towards DirectionOne
	 */
	public boolean isBlockedDirectionOne(){
		return blockedDirectionOne;
	}

	/**
	 * Returns the blocked status of the road going towards DirectionTwo
	 */
	public boolean isBlockedDirectionTwo(){
		return blockedDirectionTwo;
	}
}
