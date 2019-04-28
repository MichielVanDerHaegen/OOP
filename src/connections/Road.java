package connections;
import java.util.ArrayList;
import be.kuleuven.cs.som.annotate.*;

/**
 * A class of roads where each road has an ID, connects two end points, has a length in meters, 
 * a speed limit an average road speed under normal conditions in meters per second,
 * a delay in seconds, and a blocked status for each endpoint direction.
 * 
 * @invar The ID of each road must be a valid ID for any road
 * 		| isValidID(getID())
 * @invar The end point of each road must be a valid end point for any road
 * 		| isValidEndPoint(location1.getCoordinate())
 * 		| isValidEndPoint(location2.getCoordinate())
 * @invar The length of each road must be a valid length for any road
 * 		| isValidLength(getLength())
 * @invar The speed limit of each road must be a valid speed limit for any road
 * 		| isValidSpeedLimit(getSpeedLimit())
 * @invar The average road speed of each road must be a valid average road speed for any road
 * 		| isValidRoadSpeed(getRoadSpeed())
 * @invar The delay in the direction of an end point must be a valid delay for any road
 * 		| isValidDelay(getDelayDirectionEndPoint1())
 * 		| isValidDelay(getDelayDirectionEndPoint2())
 * 
 * @author Michiel Van der Haegen
 * @author Sam Haberman
 */

public class Road {
	/**
	 * The unique identifier of the road
	 */
	private String ID;
	/**
	 * The list containing the IDs of all roads
	 */
	private static ArrayList<String> idArray = new ArrayList<>();
	/**
	 * The minimum length an ID can have
	 */
	private static int minIDLength = 2;
	/**
	 * The maximum length an ID can have
	 */
	private static int maxIDLength = 3;
	/**
	 * The first endpoint of the road
	 */
	private int length;
	/**
	 * The standard speed limit of the road in meters per second
	 */
	private float speedlimit = 19.5F;
	/**
	 * The average speed of the road in meters per second
	 */
	private float roadSpeed;
	/**
	 * The maximum speed limit a road can have, equal to the speed of light
	 */
	private static final float MAX_SPEED = (float) 299792458.0;
	/**
	 * The delay of a road in the direction of the first endpoint
	 */
	private float delayDirectionOne = 0.0F;
	/**
	 * The delay of a road in the direction of the second endpoint
	 */
	private float delayDirectionTwo = 0.0F;
	/**
	 * The blocked status of a road in the direction of the first endpoint
	 */
	private boolean blockedDirectionOne = false;
	/**
	 * The blocked status of a road in the direction of the second endpoint
	 */
	private boolean blockedDirectionTwo = false;
	/**
	 * The maximum latitude coordinate
	 */
	private static double MAX_COORDINATE_LATITUDE = 70.0;
	/**
	 * The maximum longitude coordinate
	 */
	private static double MAX_COORDINATE_LONGITUDE = 70.0;

	/**
	 * The first location (endpoint) of the road
	 */
	private Location location1;
	/**
	 * The second location (endpoint) of the road
	 */
	private Location location2;

	/**
	 * Variable registering whether or not this road has been terminated.
	 */
	private boolean isTerminated = false;

	/**
	 * Initializes a new non-terminated road with given ID, first and second location and
	 * roadspeed. The road will be given the standard speed limit of 19.5 m/s.
	 *
	 * @param id
	 * 		The unique identifier for our new road.
	 * @param location1
	 * 		The first endpoint of the new road.
	 * @param location2
	 * 		The second endpoint of the new road.
	 * @param length
	 * 		The length of the road.
	 * @param roadSpeed
	 * 		The average speed obtained on the new road under standard conditions.
	 * @pre The given end point must be a valid end point for a road
	 * | isValidEndPoint(location1.getCoordinate())
	 * @pre The given end point must be a valid end point for a road
	 * | isValidEndPoint(location2.getCoordinate())
	 * @post The ID of this new road will be equal to the given id
	 * | new.getID() == id
	 * @post The first endpoint of the new road will be equal to the values of the given endPoint1
	 * | new.getEndPoint1() == endPoint1
	 * @post The second endpoint of the new road will be equal to the values of the given endPoint2
	 * | new.getEndPoint2() == endPoint2
	 * @post The length of the new road will be equal to the given length in meters
	 * | new.getLength() == length
	 * @post The average roadspeed for the new road will be equal to the given roadSpeed
	 * | new.getRoadSpeed() == roadSpeed
	 * @post This road is added to the list of adjoining roads for location 1.
	 * | new.location1.hasAsAdjoiningRoad(this) == true
	 * @post This road is added to the list of adjoining roads for location 2.
	 * | new.location2.hasAsAdjoiningRoad(this) == true
	 */
	public Road(String id, Location location1, Location location2, int length, float roadSpeed) {
		this.setID(id);
		assert isValidEndPoint(location1.getCoordinate());
		assert isValidEndPoint(location2.getCoordinate());
		this.location1 = location1;
		this.location2 = location2;
		this.setLength(length);
		this.setAvgRoadSpeed(roadSpeed);
		location1.addAdjoiningRoad(this);
		location2.addAdjoiningRoad(this);
	}

	/**
	 * Initialize a new non-terminated road with given ID, first and second location, speed limit and roadspeed.
	 *
	 * @param id        The unique identifier for our new road.
	 * @param location1 The first endpoint of the new road.
	 * @param location2 The second endpoint of the new road.
	 * @param length    The length of the new road.
	 * @param roadSpeed The average speed obtained on the new road under standard conditions.
	 * @pre The given first endpoint must be valid endpoint for a road
	 * | isValidEndPoint(location1.getCoordinate())
	 * @pre The given second endpoint must be valid endpoint for a road
	 * | isValidEndPoint(location2.getCoordinate())
	 * @post The ID of this new road will be equal to the given id
	 * | new.getID() == id
	 * @post The first endpoint of the new road will be equal to the values of the given endPoint1
	 * | new.getEndPoint1() == endPoint1
	 * @post The second endpoint of the new road will be equal to the values of the given endPoint2
	 * | new.getEndPoint2() == endPoint2
	 * @post The length of the new road will be equal to the given length in meters
	 * | new.getLength() == length
	 * @post The speed limit of the new road will be equal to the given speedlimit
	 * | new.getSpeedLimit() == speedlimit
	 * @post The average roadspeed for the new road will be equal to the given roadSpeed
	 * | new.getRoadSpeed() == roadSpeed
	 * @post This road is added to the list of adjoining roads for location 1.
	 * | new.location1.hasAsAdjoiningRoad(this) == true
	 * @post This road is added to the list of adjoining roads for location 2.
	 * | new.location2.hasAsAdjoiningRoad(this) == true
	 */
	public Road(String id, Location location1, Location location2, int length, float speedlimit, float roadSpeed) {
		this.setID(id);
		assert isValidEndPoint(location1.getCoordinate());
		assert isValidEndPoint(location2.getCoordinate());
		this.location1 = location1;
		this.location2 = location2;
		this.setLength(length);
		this.speedlimit = speedlimit;
		this.roadSpeed = roadSpeed;
		this.setSpeedLimit(speedlimit);
		this.setAvgRoadSpeed(roadSpeed);
		location1.addAdjoiningRoad(this);
		location2.addAdjoiningRoad(this);
	}

	/**
	 * Sets the Identification of the road to the given ID value, if it is valid.
	 * If the road ID is being changed, deletes the old ID from the ID list and adds
	 * the new ID to the ID list
	 *
	 * @param ID The new ID for our road
	 * @throws IllegalArgumentException The given ID of the road is not valid.
	 *                                  | !isValidID(ID)
	 * @throws NullPointerException     The given ID is null
	 *                                  | ID == null
	 * @post The ID of the road is set to the given ID
	 * | new.getID() == ID
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
	 * if the ID follows the correct naming conventions and is a unique ID not used for another road.
	 * | (ID.length()>= getMinIDLength() || ID.length() <= getMaxIDLength()) && correctIDFormat(ID) && isUniqueID(ID)
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
	 * @return True if the first character is an UpperCase letter and then has at least one number following
	 * | if (Character.isUpperCase(ID.charAt(0)))
	 * then for each additional character i in 1:ID.length():
	 * Character.isDigit(ID.charAt(i)))
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
	 * @param ID The ID to be checked
	 * @return True if the ID given is not used for another road
	 * |  for each element in idArray:
	 * !ID.equals(element)
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
	 * Sets the minimum ID length for the road's identifier
	 *
	 * @param value The minimum ID length to be set
	 * @throws IllegalArgumentException If the given value is less than 2 or greater than the maximum ID length
	 *                                  | if(value <2 || value > maxIDLength)
	 * @post The minimum ID length is set to the given value
	 * | new.getMinIDLength() == value
	 */
	public static void setMinIDLength(int value) throws IllegalArgumentException {
		if (value < 2 || value > maxIDLength) {
			throw new IllegalArgumentException();
		}
		minIDLength = value;
	}

	/**
	 * Returns the minimum ID length
	 */
	@Basic
	public static int getMinIDLength() {
		return minIDLength;
	}

	/**
	 * Sets the maximum ID length for the road's identifier
	 *
	 * @param value The maximum ID length to be set
	 * @throws IllegalArgumentException If the given value is less than the minimum ID length
	 *                                  | value < minIDLength
	 * @post The maximum ID length is set to the given value
	 * | new.getMaxIDLength() == value
	 */
	public static void setMaxIDLength(int value) throws IllegalArgumentException {
		if (value < minIDLength) {
			throw new IllegalArgumentException();
		}
		maxIDLength = value;

	}

	/**
	 * Returns the maximum ID length
	 */
	@Basic
	public static int getMaxIDLength() {
		return maxIDLength;
	}


	/**
	 * A method to calculate the minimum possible length of a road
	 *
	 * @return returns the euclidian distance between the endpoints of a road
	 * | sqrt((x2-x1)^2+(y2-y1)^2)
	 */
	public int calculateMinLength() {
		double ydif = getEndPoint2().getCoordinate()[1] - getEndPoint1().getCoordinate()[1];
		double xdif = getEndPoint2().getCoordinate()[0] - getEndPoint1().getCoordinate()[0];
		return (int) Math.sqrt((xdif * xdif) + (ydif * ydif));
	}

	/**
	 * Returns the length of this road.
	 */
	@Basic
	public int getLength() {
		return this.length;
	}

	/**
	 * Checks whether the given length is a valid length for any road.
	 *
	 * @param length The length to check.
	 * @return True if the given length is greater or equal to the minimal possible length of a road
	 * | result == (length >= calculateMinLength());
	 */
	public boolean isValidLength(int length) {
		return (length >= this.calculateMinLength());
	}

	/**
	 * Sets the length of this road to the given length in meters.
	 *
	 * @param length The new length for this road.
	 * @post If the given length is a valid length for any road, the length of this new road is equal to the given length.
	 * | if(isValidLength(length))
	 * | then new.getLength() == length
	 * @post If the given length is not a valid length, the length of this new road is set to the minimum possible length.
	 * | if(!isValidLength(length))
	 * | then new.getLength() == this.calculateMinLength()
	 */
	public void setLength(int length) {
		if (isValidLength(length))
			this.length = length;
		else
			this.length = this.calculateMinLength();
	}

	/**
	 * Sets the speedlimit of the road to the given value in meters per second.
	 *
	 * @param speedlimit The new speed limit for the road in meters per second.
	 * @throws IllegalArgumentException The given speed limit for the road is not valid
	 *                                  | !isValidSpeedLimit(speedlimit)
	 * @post The speed limit of the road is set to the given speed limit
	 * | new.getSpeedLimit() == speedlimit
	 */
	public void setSpeedLimit(float speedlimit) throws IllegalArgumentException {
		if (!isValidSpeedLimit(speedlimit))
			throw new IllegalArgumentException();
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
	 * Checks to see if the given speed limit in meters per second for the road is acceptable
	 *
	 * @param speedlimit The speed limit to check
	 * @return True if the given speed limit is greater than 0 and less than or equal to the max speed limit
	 * and greater or equal to the road speed
	 * | result == (speedlimit > 0) && (speedlimit <= maxSpeed) && (speedlimit >= roadSpeed)
	 */
	public boolean isValidSpeedLimit(float speedlimit) {
		return ((speedlimit > 0.0F) && (speedlimit <= MAX_SPEED) && (speedlimit >= roadSpeed));
	}

	/**
	 * Sets the average road speed of the road to the given value in meters per second
	 *
	 * @param roadspeed The new average speed obtained by driving on the road under standard conditions
	 * @throws IllegalArgumentException The given roadspeed for the road is not valid
	 *                                  | !isValidRoadSpeed(roadspeed)
	 * @post The average speed of the road under standard conditions is set to the given roadspeed value
	 * | new.getRoadSpeed() == roadspeed
	 */
	public void setAvgRoadSpeed(float roadspeed) throws IllegalArgumentException {
		if (!isValidRoadSpeed(roadspeed))
			throw new IllegalArgumentException();
		this.roadSpeed = roadspeed;
	}

	/**
	 * Returns the average speed obtained driving on the road under standard conditions
	 */
	@Basic
	public float getRoadSpeed() {
		return roadSpeed;
	}

	/**
	 * Checks to see if the given roadSpeed value is acceptable
	 *
	 * @param roadSpeed The average road speed under standard conditions to check
	 * @return True if the given road speed is greater than 0
	 * and less than or equal to the roads speed limit and less or equal than te maximum speed
	 * | result == (roadSpeed > 0 && roadSpeed <= speedlimit && roadSpeed <= maxSpeed)
	 */
	public boolean isValidRoadSpeed(float roadSpeed) {
		return ((roadSpeed > 0.0F) && (roadSpeed <= speedlimit) && (roadSpeed <= MAX_SPEED));
	}

	/**
	 * Sets the delay of the road in the direction of endpoint one to the given delay in seconds
	 *
	 * @param delay The new delay time for the road going towards endpoint one
	 * @pre The given delay of the road must be a valid delay for a road
	 * | isValidDelay(delay)
	 * @post The delay of the road going towards endpoint one is equal to the given delay
	 * | new.getDelayDirectionOne() == delay
	 */

	public void setDelayDirectionEndPointOne(float delay) {
		assert isValidDelay(delay);
		this.delayDirectionOne = delay;
	}

	/**
	 * Sets the delay of the road in the direction of endpoint two to the given delay in seconds
	 *
	 * @param delay The new delay time for the road going towards endpoint two
	 * @pre The given delay of the road must be a valid delay for a road
	 * | isValidDelay(delay)
	 * @post The delay of the road going towards endpoint two is equal to the given delay
	 * | new.getDelayDirectionTwo() == delay
	 */

	public void setDelayDirectionEndPointTwo(float delay) {
		assert isValidDelay(delay);
		this.delayDirectionTwo = delay;
	}

	/**
	 * Checks to see if the given delay value for the road is valid
	 *
	 * @param delay The delay in seconds to check
	 * @return True if and only if the given delay is non negative or positive infinity
	 * | result == (delay >= 0 || delay == Float.POSITIVE_INFINITY)
	 */
	public boolean isValidDelay(float delay) {
		return delay >= 0 || delay == Float.POSITIVE_INFINITY;

	}

	/**
	 * Returns the delay in seconds for the road in the direction of endpoint one
	 */
	public float getDelayDirectionEndPointOne() {
		return delayDirectionOne;
	}

	/**
	 * Returns the delay in seconds for the road in the direction of endpoint two
	 */
	public float getDelayDirectionEndPointTwo() {
		return delayDirectionTwo;
	}


	/**
	 * Sets the blocked status of the road going towards endpoint one to the given boolean blocked value.
	 *
	 * @param blocked The new blocked status of the road.
	 * @post The blocked status of the road is equal to the given blocked status
	 * True = blocked
	 * False = not blocked
	 * | new.isBlockedDirectionOne() == blocked
	 */
	public void setBlockedDirectionEndPointOne(boolean blocked) {
		this.blockedDirectionOne = blocked;
	}

	/**
	 * Sets the blocked status of the road going towards endpoint two to the given boolean blocked value.
	 *
	 * @param blocked The new blocked status of the road.
	 * @post The blocked status of the road is equal to the given blocked status
	 * True = blocked
	 * False = not blocked
	 * | new.isBlockedDirectionTwo() == blocked
	 */
	public void setBlockedDirectionEndPointTwo(boolean blocked) {
		this.blockedDirectionTwo = blocked;
	}

	/**
	 * Returns the blocked status of the road going towards endpoint one
	 */
	public boolean isBlockedDirectionEndPointOne() {
		return blockedDirectionOne;
	}

	/**
	 * Returns the blocked status of the road going towards endpoint two
	 */
	public boolean isBlockedDirectionEndPointTwo() {
		return blockedDirectionTwo;
	}

	/**
	 * This method overrides the toString representation of an instance of the road class
	 *
	 * @return Returns a string stating "This road has the following properties" which lists the ID, both endpoints,
	 * the length of the road in meters, the speed limit and the average speed of the road in meters per second,
	 * the blocked status of the road and the delay in seconds.
	 */
	@Override
	public String toString() {
		return ("This road has the following properties:" + '\n' + "ID: " + this.getID() + '\n' +
				"End point 1: " + this.getEndPoint1().getCoordinate()[0] + "," + this.getEndPoint1().getCoordinate()[1] + '\n' + "End point 2: " + this.getEndPoint2().getCoordinate()[0] + "," + this.getEndPoint2().getCoordinate()[1] + '\n' + "Length: " + this.getLength()
				+ '\n' + "Speed limit: " + this.getSpeedlimit() + '\n' + "Average speed: " + this.getRoadSpeed() + '\n' + "Blocked in the direction of end point 1: " + this.isBlockedDirectionEndPointOne() + '\n' +
				"Blocked in the direction of end point 2: " + this.isBlockedDirectionEndPointTwo() + '\n' + "Delay in the direction of end point 1: " + this.getDelayDirectionEndPointOne() + '\n' +
				"Delay in the direction of end point 2: " + this.getDelayDirectionEndPointTwo() + '\n');
	}

	/**
	 * A method to calculate the travel time of a road in the direction of endpoint one
	 *
	 * @return Infinity if the road is blocked in this direction
	 * | time = Float.POSITIVE_INFINITY;
	 */
	public float calculateTravelTimeEndPointOne() {
		if (this.isBlockedDirectionEndPointOne()) {
			System.out.println("The road " + this.getID() + " is blocked in this direction!");
			return Float.POSITIVE_INFINITY;
		}
		float time = (this.getLength() / this.getRoadSpeed()) + this.getDelayDirectionEndPointOne();
		return time;
	}

	/**
	 * A method to calculate the travel time of a road in the direction of endpoint two
	 *
	 * @return Infinity if the road is blocked in this direction
	 * | time = Float.POSITIVE_INFINITY;
	 */
	public float calculateTravelTimeEndPointTwo() {
		if (this.isBlockedDirectionEndPointTwo()) {
			System.out.println("The road " + this.getID() + "  is blocked in this direction!");
			return Float.POSITIVE_INFINITY;
		}
		float time = (this.getLength() / this.getRoadSpeed()) + this.getDelayDirectionEndPointTwo();
		return time;
	}


//	public static void main(String[] args) {
//		double[] point1 = new double[] {10.0,20.0};
//		double[] point2 = new double[] {15.3,60.6};
//		double[] point3 = new double[] {40.5,5.5};
//		double[] point4 = new double[] {3.14,42.0};
//
//		Road road = new Road("E40",point1,point2,1000,120.0F,100.0F);
//		Road road2 = new Road("E17",point2,point3,200,120.0F,50.0F);
//		Road road3 = new Road("R0",point3,point4,500,120.0F,25.0F);
//		System.out.println(road);
//		System.out.println(road2);
//		System.out.println(road3);
//
//		road.setDelayDirectionEndPointTwo(100);
//		System.out.println("A delay of 100 seconds was set in the forward direction of "+road.getID());
//		road.setBlockedDirectionEndPointOne(true);
//		System.out.println("The "+road.getID()+" was blocked in the backwards direction");
//		float totalTime = road.calculateTravelTimeEndPointTwo()+road2.calculateTravelTimeEndPointTwo()+road3.calculateTravelTimeEndPointTwo();
//		System.out.println("The total travel time is: "+totalTime+" seconds."+'\n');
//
//		road2.setDelayDirectionEndPointTwo(Float.POSITIVE_INFINITY);
//		System.out.println("A delay of infinity was set in the forward direction of "+road2.getID());
//		float totalTime2 = road.calculateTravelTimeEndPointTwo()+road2.calculateTravelTimeEndPointTwo()+road3.calculateTravelTimeEndPointTwo();
//		System.out.println("The total travel time is: "+totalTime2+" seconds."+'\n');
//
//		System.out.println("This is a test for ourselves: if the road is blocked, does it return infinity?");
//		float totalTime3 = road.calculateTravelTimeEndPointOne()+road2.calculateTravelTimeEndPointOne()+road3.calculateTravelTimeEndPointOne();
//		System.out.println("The total travel time is: "+totalTime3+" seconds."+'\n');
//
//	}


	//Ask about whether we want to return location or return the coordinates of the location. Also ask about the coordinate system
	//minimum coordinates vs leaving it as zero?

	/**
	 * Returns the first endpoint of this road as a location.
	 */
	@Basic
	@Immutable
	public Location getEndPoint1() {
		return this.location1;
	}

	/**
	 * Returns the second endpoint of this road as a location.
	 */
	@Basic
	@Immutable
	public Location getEndPoint2() {
		return this.location2;
	}

	/**
	 * Returns both endpoints of this road as locations.
	 */
	@Immutable
	public Location[] getEndPoints() {
		Location[] endpoints = new Location[]{location1, location2};
		return endpoints;
	}

	//end question part.

	/**
	 * Sets the maximum value of latitude a coordinate can have to the given value.
	 *
	 * @param value The new maximum value for latitude.
	 * @throws IllegalArgumentException If the given value is not between -90 and 90
	 *                                  degrees | ((value < -90.0) || (value >
	 *                                  90.0))
	 * @post The maximum value latidude a coordinate can have is set to the given value |
	 * new.getMaxCoordinateLatitude() == value
	 */
	public static void setMaxCoordinateLatitude(double value) throws IllegalArgumentException {
		if ((value < -90.0) || (value > 90.0))
			throw new IllegalArgumentException();
		MAX_COORDINATE_LATITUDE = value;
	}

	/**
	 * Sets the maximum value a coordinate can have to the given value.
	 *
	 * @param value The new maximum value of longitude for coordinates.
	 * @throws IllegalArgumentException If the given value is not between -180 and 180
	 *                                  degrees | ((value < -180.0) || (value >
	 *                                  180.0))
	 * @post The maximum value longitude a coordinate can have is set to the given value |
	 * new.getMaxCoordinateLongitude() == value
	 */
	public static void setMaxCoordinateLongitude(double value) throws IllegalArgumentException {
		if ((value < -180.0) || (value > 180.0))
			throw new IllegalArgumentException();
		MAX_COORDINATE_LONGITUDE = value;
	}

	/**
	 * Returns the maximum latitude value a coordinate can have
	 */
	public static double getMaxCoordinateLatidude() {
		return MAX_COORDINATE_LATITUDE;
	}

	/**
	 * Returns the maximum longitude value a coordinate can have
	 */
	public static double getMaxCoordinateLongitude() {
		return MAX_COORDINATE_LONGITUDE;
	}

	/**
	 * Checks to see if the given latitude is valid.
	 *
	 * @param latitude The latitude value to check
	 * @return True if the latitude is both greater than or equal to 0 and less
	 * than or equal to the Max latitude value | result == ((latitude >= 0.0)
	 * && (latitude <= MAX_COORDINATE_LATITUDE))
	 */
	public boolean isValidLatitude(double latitude) {
		return ((latitude >= 0.0) && (latitude <= MAX_COORDINATE_LATITUDE));
	}

	/**
	 * Checks to see if the given longitude is valid.
	 *
	 * @param longitude The longitude value to check
	 * @return True if the longitude is both greater than or equal to 0 and less
	 * than or equal to the Max longitude value | result == ((longitude >= 0.0)
	 * && (longitude <= MAX_COORDINATE_LONGITUDE))
	 */
	public boolean isValidLongitude(double longitude) {
		return ((longitude >= 0.0) && (longitude <= MAX_COORDINATE_LONGITUDE));
	}

	/**
	 * Checks whether the given endpoint is a valid endpoint for any road.
	 *
	 * @param endpoint The endpoint to check.
	 * @return True if the number of coordinates is equal to two and both
	 * coordinates are valid endpoints | result == (endpoint.length==2) &&
	 * (isValidCoordinate(endpoint[0])) && (isValidCoordinate(endpoint[1]))
	 */
	public boolean isValidEndPoint(double[] endpoint) {
		if (endpoint.length == 2) {
			if (isValidLatitude(endpoint[0])) {
				if (isValidLongitude(endpoint[1])) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Checks to see whether this road is terminated.
	 */
	@Basic
	public boolean isTerminated() {
		return this.isTerminated;
	}

	/**
	 *Terminates this road
	 *
	 * @post This road is terminated.
	 * 		| new.isTerminated() == true
	 * @post Both locations connected to this road are set to null.
	 * 		| new.getEndpoint1 == null && new.getEndpoint2 == null
	 * @post Both locations connected to this road remove this road from their respective list of adjoining roads
	 * 		| new.location1.getAdjoiningRoads.contains(this) == false && new.location2.getAdjoiningRoads.contains(this) == false
	 * @post The list of IDs no longer contains this roads ID.
	 * 		| new.idArray.contains(this.getID()) == false
	 */
	public void terminate() {
		if (!this.isTerminated) {
			this.isTerminated = true;
			location1.removeAdjoiningRoad(this);
			location2.removeAdjoiningRoad(this);
			location1 = null;
			location2 = null;
			idArray.remove(this.ID);
		}
	}
}

	
