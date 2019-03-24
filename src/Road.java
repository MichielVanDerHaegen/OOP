import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;

import be.kuleuven.cs.som.annotate.*;

public class Road implements Facade {

	private String ID;
	private ArrayList<String> idArray = new ArrayList<>();
	private int minIDLength = 2;
	private int maxIDLength = 3;
	private double[] endPoint1;
	private double[] endPoint2;
	private double MAX_COORDINATE = 70.0;
	private int length;
	private float speedlimit = 19.5F;
	private float roadSpeed;
	private float delayDirectionOne = 0;
	private float delayDirectionTwo = 0;
	private boolean blockedDirectionOne = false;
	private boolean blockedDirectionTwo = false;

	/**
	 * Initialize a new road with given ID, first and second endpoints and
	 * roadspeed. The road will be given the standard speed limit of 19.5 m/s.
	 *
	 * @param id        The unique identifier for our new road.
	 * @param endPoint1 The first endpoint of the new road.
	 * @param endPoint2 The second endpoint of the new road.
	 * @param length The length of the road.
	 * @param roadSpeed The average speed obtained on the new road under standard
	 *                  conditions.
	 * @pre The given end point must be valid end point for a road
	 * 		| isValidEndPoint(endPoint1)
	 * @pre The given end point must be valid end point for a road
	 * 		| isValidEndPoint(endPoint2)
	 * @post The ID of this new road will be equal to the given id | new.getID() ==
	 *       id
	 * @post The first endpoint of the new road will be equal to the given endPoint1
	 * 		| new.getEndPoint1() == endPoint1
	 * @post The second endpoint of the new road will be equal to the given endPoint2
	 * 		| new.getEndPoint2() == endPoint2
	 * @post The length of the new road will be equal to the given length in meters
	 * 		| new.getLength() == length
	 * @post The average roadspeed for the new road will be equal to the given roadSpeed
	 * 		| new.getRoadSpeed() == roadSpeed
	 */
	public Road(String id, double[] endPoint1, double[] endPoint2, int length, float roadSpeed) {
		this.setID(id);
		assert isValidEndPoint(endPoint1);
		assert isValidEndPoint(endPoint2);
		this.endPoint1 = endPoint1;
		this.endPoint2 = endPoint2;
		this.setLength(length);
		this.setAvgRoadSpeed(roadSpeed);
	}

	/**
	 * Initialize a new road with given ID, first end second endpoint, speed limit and
	 * roadspeed.
	 *
	 * @param id        The unique identifier for our new road.
	 * @param endPoint1 The first endpoint of the new road.
	 * @param endPoint2 The second endpoint of the new road.
	 * @param length The length of the new road.
	 * @param roadSpeed The average speed obtained on the new road under standard
	 *                  conditions.
	 * @pre The given first endpoint must be valid endpoint for a road
	 * 		| isValidEndPoint(endPoint1)
	 * @pre The given second endpoint must be valid endpoint for a road
	 * 		| isValidEndPoint(endPoint2)
	 * @post The ID of this new road will be equal to the given id | new.getID() ==
	 *       id
	 * @post The first endpoint of the new road will be equal to the given endPoint1s
	 * 		| new.getEndPoint1() == endPoint1
	 * @post The second endpoint of the new road will be equal to the given endPoint2s
	 * 		| new.getEndPoint2() == endPoint2
	 * @post The length of the new road will be equal to the given length in meters
	 * 		| new.getLength() == length
	 * @post The speed limit of the new road will be equal to the given speedlimit
	 * 		| new.getSpeedLimit() == speedlimit
	 * @post The average roadspeed for the new road will be equal to the given roadSpeed
	 * 		| new.getRoadSpeed() == roadSpeed
	 */
	public Road(String id, double[] endPoint1, double[] endPoint2, int length, float speedlimit, float roadSpeed) {
		this.setID(id);
		assert isValidEndPoint(endPoint1);
		assert isValidEndPoint(endPoint2);
		this.endPoint1 = endPoint1;
		this.endPoint2 = endPoint2;
		this.setLength(length);
		this.setSpeedLimit(speedlimit);
		this.setAvgRoadSpeed(roadSpeed);
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
		return this.ID;
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
		if (ID.length() >= getMinIDLength() && ID.length() <= getMaxIDLength()) {
			if (correctIDFormat(ID)) {
				return isUniqueID(ID);
			}
		}
		return false;
	}

	/**
	 * Checks to see if the given ID follows the correct naming conventions
	 * 
	 * @param ID The ID to be checked
	 * @return True if the first character as a UpperCase letter and then has at
	 *         least one number following
	 *         | if (Character.isUpperCase(ID.charAt(0)))
	 *         then for each additional character i in 1:ID.length():
	 *         Character.isDigit(ID.charAt(i)))
	 */
	public boolean correctIDFormat(String ID) {
		if (Character.isUpperCase(ID.charAt(0))) {
			for (int i = 1; i < ID.length(); i++) {
				if (!Character.isDigit(ID.charAt(i))) {
					return false;
				}
			}
			return true;
		}
		return false;
	}

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
	 * Return the first endpoint of this road.
	 */
	@Basic
    @Immutable
	public double[] getEndPoint1() {
		return this.endPoint1;
	}

	/**
	 * Return the second endpoint of this road.
	 */
	@Basic
    @Immutable
	public double[] getEndPoint2() {
		return this.endPoint2;
	}
	
	/**
	 * Return both endpoints of this road.
	 */
	@Immutable
	public double[][] getEndPoints(){		
		double[][] endpoints = new double[][] {new double[] {endPoint1[0],endPoint1[1]},new double[] {endPoint2[0],endPoint2[1]}};
		return endpoints;
	}

	/**
	 * Set the maximum value a coordinate can have to the given value.
	 * 
	 * @param value The new maximum value for coordinates.
	 * @throws NullPointerException if the given value is null
	 * 		| value == null
	 * @throws IllegalArgumentException if the given value is not between 0 and 360 degrees
	 * 		| ((value <= 0.0) || (value >= 360.0))
	 */
	public void setMaxCoordinate(double value) throws NullPointerException, IllegalArgumentException {
		if((value <= 0.0) || (value >= 360.0))
			throw new IllegalArgumentException();
		this.MAX_COORDINATE = value;
	}
	
	
	
	
	public boolean isValidCoordinate(double coordinate) {
		return coordinate >= 0.0 && coordinate <= MAX_COORDINATE;
	}

	/**
	 * Check whether the given endpoint is a valid endpoint for any road.
	 * 
	 * @param endpoint The endpoint to check.
	 * @return returns true if both the lattitude and longitude of the given
	 *         endpoint are greater than or equal to zero and less than or equal
	 *         to the maximum coordinate | result == ((coordinate[0] > 0) &&
	 *         (coordinate[1] > 0) && (coordinate[0] <= MAX_COORDINATE) &&
	 *         (coordinate[1] <= MAX_COORDINATE));
	 * 
	 */
	public boolean isValidEndPoint(double[] endpoint) {
//		return ((coordinate[0] >= 0.0) && (coordinate[1] >= 0.0) && (coordinate[0] <= MAX_COORDINATE)
//				&& (coordinate[1] <= MAX_COORDINATE));
		if(endpoint==null) {
			return false;
		}
		if(endpoint.length==2) {
			assert isValidCoordinate(endpoint[0]);
			assert isValidCoordinate(endpoint[1]);
			return true;
		}
		return false;	
	}
	
	/**
	 * A method to calculate the minimum possible length of a road
	 * @return returns the euclidian distance between the endpoints of a road
	 * 		| sqrt((x2-x1)^2+(y2-y1)^2)
	 */
	public int calculateMinLength() {
		double ydif=getEndPoint2()[1]-getEndPoint1()[1];
		double xdif=getEndPoint2()[0]-getEndPoint1()[0];
		return (int) Math.sqrt((xdif*xdif)+(ydif*ydif));
	}

	/**
	 * Return the length of this road.
	 */
	@Basic
	public int getLength() {
		return this.length;
	}

	/**
	 * Check whether the given length is a valid length for
	 * any road.
	 * 
	 * @param length The length to check.
	 * @return true if the given length is greater or equal to the minimal possible length of a road
	 * 		| result == (length >= calculateMinLength());
	 */
	public boolean isValidLength(int length) {
		return (length >= this.calculateMinLength());
	}

	/**
	 * Set the length of this road to the given length.
	 * 
	 * @param length The new length for this object_name.
	 * @post If the given length is a valid length for any
	 *       road, the length of this new road is equal to
	 *       the given length. | if
	 *       (isValidLength(length)) | then
	 *       new.getLength() == length
	 */
	public void setLength(int length) {
		if (isValidLength(length))
			this.length = length;
		else
			this.length=this.calculateMinLength();
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
	 *         equal to the max speed limit and greater or equal to the road speed | result == (speedlimit > 0) &&
	 *         (speedlimit <= maxSpeed) && (speedlimit >= roadSpeed)
	 */
	public boolean isValidSpeedLimit(float speedlimit) {
		return ((speedlimit > 0.0F) && (speedlimit <= maxSpeed) && (speedlimit >= roadSpeed));
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
	 * @return False if the given road speed is not a number
	 * 		| roadSpeed == Float.NaN
	 */
	public boolean isValidRoadSpeed(float roadSpeed) {
		if(roadSpeed == Float.NaN)
			return false;
		return ((roadSpeed > 0.0F) && (roadSpeed <= speedlimit));
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
		return delay >= 0 || delay == Float.POSITIVE_INFINITY ;

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

	
