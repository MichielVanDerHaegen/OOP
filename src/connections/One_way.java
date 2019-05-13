package connections;

public class One_way extends Road{
    /**
     * Initializes a new non-terminated one-way road with given ID, first and second location and
     * roadspeed. The road will be given the standard speed limit of 19.5 m/s.
     *
     * @param id        The unique identifier for our new road.
     * @param location1 The first endpoint of the new road.
     * @param location2 The second endpoint of the new road.
     * @param length    The length of the road.
     * @param roadSpeed The average speed obtained on the new road under standard conditions.
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
     * @post The start location of this road is equal to location1.
     * | new.startLocation == location1
     * @post The end location of this road is equal to location2.
     * | new.endLocation == location2
     */
    public One_way(String id, Location location1, Location location2, int length, float roadSpeed) {
        super(id, location1, location2, length, roadSpeed);
        startLocation = location1;
        endLocation = location2;
    }

    /**
     * Initialize a new non-terminated one-way road with given ID, first and second location, speed limit and roadspeed.
     *
     * @param id         The unique identifier for our new road.
     * @param location1  The first endpoint of the new road.
     * @param location2  The second endpoint of the new road.
     * @param length     The length of the new road.
     * @param speedlimit The speed limit of the new road.
     * @param roadSpeed  The average speed obtained on the new road under standard conditions.
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
     * @post The start location of this road is equal to location1.
     * | new.startLocation == location1
     * @post The end location of this road is equal to location2.
     * | new.endLocation == location2
     */
    public One_way(String id, Location location1, Location location2, int length, float speedlimit, float roadSpeed) {
        super(id, location1, location2, length, speedlimit, roadSpeed);
        startLocation = location1;
        endLocation = location2;
    }

    /**
     * The start location of this one-way road.
     */
    private final Location startLocation;

    /**
     * The end location of this one-way road.
     */
    private final Location endLocation;

    /**
     * Does nothing, road only travels in direction of end location.
     * @param delay The new delay time for the road going towards endpoint one
     */
    @Override
    public void setDelayDirectionEndPointOne(float delay){}


}
