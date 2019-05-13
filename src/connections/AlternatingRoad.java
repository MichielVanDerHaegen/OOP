package connections;

public class AlternatingRoad extends Road{
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
     * @post The given location1 is stored as location1.
     * | new.location1 = location1
     * @post The given location2 is stored as location2.
     * | new.location2 = location2
     */
    public AlternatingRoad(String id, Location location1, Location location2, int length, float roadSpeed) {
        super(id, location1, location2, length, roadSpeed);
        startLocation = location1;
        endLocation = location2;
        locationOne = location1;
        locationTwo = location2;
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
     * @post The given location1 is stored as location1.
     * | new.location1 = location1
     * @post The given location2 is stored as location2.
     * | new.location2 = location2
     */
    public AlternatingRoad(String id, Location location1, Location location2, int length, float speedlimit, float roadSpeed) {
        super(id, location1, location2, length, speedlimit, roadSpeed);
        startLocation = location1;
        endLocation = location2;
        locationOne = location1;
        locationTwo = location2;
    }

    /**
     * The start location of this one-way road.
     */
    private Location startLocation;

    /**
     * The end location of this one-way road.
     */
    private Location endLocation;

    /**
     * The original given location for location one.
     */
    private Location locationOne;

    /**
     * The original given location for location two.
     */
    private Location locationTwo;

    /**
     * Boolean value that tracks the direction of the road, if true then road is moving in direction of endpoint two.
     */
    private boolean directionOfRoad = true;


    /**
     * If the road is traveling in the direction of endpoint two does nothing, else runs the method from Road.
     * @param delay The new delay time for the road going towards endpoint one
     *
     * @post The delay in the direction of endpoint one is equal to the given delay, if travelling in the direction of endpoint one
     * | if !(directionOfRoad)
     * |    then new.getDelayDirectionOne() == delay
     */
    @Override
    public void setDelayDirectionEndPointOne(float delay) {
        if (!directionOfRoad) {
            super.setDelayDirectionEndPointOne(delay);
        }
    }

    /**
     * If the road is traveling in the direction of endpoint one does nothing, else runs the method from Road.
     * @param delay The new delay time for the road going towards endpoint one
     *
     * @post The delay in the direction of endpoint two is equal to the given delay, if travelling in the direction of endpoint two.
     * | if (directionOfRoad)
     * |    then new.getDelayDirectionTwo() == delay
     */
    @Override
    public void setDelayDirectionEndPointTwo(float delay) {
        if (directionOfRoad) {
            super.setDelayDirectionEndPointTwo(delay);
        }
    }

    /**
     * Gets the delay of endpoint one if travelling in direction of endpoint one.
     * @throws NullPointerException If travelling in direction of endpoint two.
     */
    @Override
    public float getDelayDirectionEndPointOne() throws NullPointerException{
        if (!directionOfRoad){
            return super.getDelayDirectionEndPointOne();
        }
        throw new NullPointerException();
    }

    /**
     * Gets the delay of endpoint two if travelling in the direction of endpoint two.
     * @throws NullPointerException If travelling in direction of endpoint one.
     */
    @Override
    public float getDelayDirectionEndPointTwo() throws NullPointerException{
        if (directionOfRoad){
            return super.getDelayDirectionEndPointTwo();
        }
        throw new NullPointerException();
    }

    /**
     * Sets the blocked status of the road to the given value if the road is travelling in the direction of endpoint one.
     * @param blocked The new blocked status of the road.
     * @post The blocked status of the road is equal to the given blocked boolean if the road is travelling in the direction of endpoint one.
     *  | if (!directionOfRoad)
     *  |   new.isBlockedDirectionEndPointOne == blocked
     */
    @Override
    public void setBlockedDirectionEndPointOne(boolean blocked){
        if (!directionOfRoad){
            super.setBlockedDirectionEndPointOne(blocked);
        }
    }

    /**
     * Sets the blocked status of the road to the given value if the road is travelling in the direction of endpoint tow.
     * @param blocked The new blocked status of the road.
     * @post The blocked status of the road is equal to the given blocked boolean if the road is travelling in the direction of endpoint two.
     * |    if (directionOfRoad)
     *          new.isBlockedDirectionEndPointTwo == blocked
     */
    @Override
    public void setBlockedDirectionEndPointTwo(boolean blocked){
        if (directionOfRoad){
            super.setBlockedDirectionEndPointTwo(blocked);
        }
    }

    /**
     * Returns the blocked status of the road, only works if road is going in direction of endpoint one
     * @throws NullPointerException If road is travelling in direction of endpoint two
     */
    @Override
    public boolean isBlockedDirectionEndPointOne() throws NullPointerException{
        if (!directionOfRoad){
            return super.isBlockedDirectionEndPointOne();
        }
        throw new NullPointerException();
    }

    /**
     * Returns the blocked status of the road, only works if road is going in direction of endpoint two.
     * @throws NullPointerException If road is travelling in direction of endpoint one.
     */
  @Override
  public boolean isBlockedDirectionEndPointTwo() throws NullPointerException{
      if (directionOfRoad){
      	return super.isBlockedDirectionEndPointTwo();
      }
      throw new NullPointerException();
  }

    /**
     * Swaps the direction the road is going.
     * @post If direction of the road was going in direction of endpoint two, now it is going in direction of endpoint one and vice versa.
     * | if (directionOfRoad)
     * |    new.directionOfRoad = false
     * |    new.startLocation = locationTwo
     * |    new.endLocation = locationOne
     * | else
     * |    new.directionOfRoad = true
     * |    new.startLocation = locationOne
     * |    new.endLocation = locationTwo
     */
    public void swapRoadDirection(){
        if (directionOfRoad){
            directionOfRoad = false;
            startLocation = locationTwo;
            endLocation = locationOne;
        }
        else{
            directionOfRoad = true;
            startLocation = locationOne;
            endLocation = locationTwo;
        }
    }
    


}

