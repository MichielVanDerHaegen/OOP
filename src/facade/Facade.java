package facade;

import java.util.Collection;
import connections.Location;
import connections.Road;
import connections.Route;

/**
 * A facade to enable a collection of tests.
 * 
 * The facade is an interface, a concept that we have not covered in the course.
 * An interface in Java is very similar to an abstract class. It groups definitions
 * of abstract and non-abstract methods. By definition, all methods in an interface
 * are public, even if they are not explicitly qualified public. Methods with an
 * implementation in an interface are called default methods, and must have the
 * keyword default for that purpose. 
 * 
 * The facade assumes that you have at least a class Location, a class Road
 * and a class Route. If these classes are in some package (which they should be),
 * you must add import statements in the beginning of this file. In our solution,
 * these classes are all in a package named connections, hence the import statements
 * in the supplied version of the facade.
 * 
 * The facade is used by the test suite, and is some kind of intermediary
 * between the tests and your code. You must implement each method using
 * the code that you have written. To get some idea of how to do this,
 * the facade includes the implementation of the methods getRoadIdentification
 * and changeRoadIdentification in view of our solution.
 * Typically, you invoke the corresponding method from your code, you catch
 * any kind of exception that invocation may throw, and throw a new ModelException
 * as a signal that the execution was not successful.
 */
public interface Facade {

	/********************
	 * Location methods *
	 ********************/

	/**
	 * Return a new location with given coordinate, given address and no adjoining roads yet.
	 */
	default Location createLocation(double[] coordinates, String address) throws ModelException {
		try {
			Location location = new Location(coordinates, address);
			return location;
		}
		catch(Throwable exc) {
			throw new ModelException();
		}
	}

	/**
	 * Terminate the given location.
	 */
	default void terminateLocation(Location location) throws ModelException {
		try {
			location.terminate();
		}
		catch(Throwable exc) {
			throw new ModelException();
		}
	}

	/**
	 * Check whether the given location is terminated.
	 */
	default boolean isTerminatedLocation(Location location) throws ModelException {
		try{
			return location.isTerminated();
		}
		catch(Throwable exc) {
			throw new ModelException();
		}
	}

	/**
	 * Return the coordinates of the given location.
	 *   The method returns an array of length 2 containing the latitude followed
	 *   by the longitude as two values of type double.
	 */
	default double[] getLocationCoordinates(Location location) throws ModelException {
		try {
			return location.getCoordinate();
		}
		catch(Throwable exc) {
			throw new ModelException();
		}
	}

	/**
	 * Return the address of the given location.
	 */
	default String getLocationAddress(Location location) throws ModelException {
		try {
			return location.getAddress();
		}
		catch(Throwable exc) {
			throw new ModelException();
		}
	}

	/**
	 * Return a collection of all adjoining roads of the given location.
	 */
	default Collection<Road> getLocationAllAdjoiningRoads(Location location) throws ModelException {
		try {
			return location.getAdjoiningRoads();
		}
		catch(Throwable exc) {
			throw new ModelException();
		}
	}

	/**
	 * Check whether the given location ahs the given road as one of its adjoining roads.
	 */
	default boolean locationHasAsAdjoiningRoad(Location location, Road road) throws ModelException {
		try {
			return location.canHaveAsAdjoiningRoad(road);
		}
		catch(Throwable exc) {
			throw new ModelException();
		}
	}

	/****************
	 * Road methods *
	 ****************/

	/**
	 * Return a new road with the given identification, given end points, given length,
	 * given speed limit, given average speed. with no delays in both directions nor
	 * with blockages in both directions.
	 */
	default Road createRoad(String identification, Location endPoint1, Location endPoint2,
			int length, float speedLimit, float averageSpeed)
			throws ModelException {
		try {
			Road road = new Road(identification,endPoint1,endPoint2,length,speedLimit,averageSpeed);
			return road;
		}
		catch(Throwable exc) {
			throw new ModelException();
		}
	}

	/**
	 * Terminate the given road.
	 */
	default void terminateRoad(Road road) throws ModelException {
		try {
			road.terminate();
		}
		catch(Throwable exc) {
			throw new ModelException();
		}
	}

	/**
	 * Check whether the given road is terminated.
	 */
	default boolean isTerminatedRoad(Road road) throws ModelException {
		try {
			return road.isTerminated();
		}
		catch(Throwable exc) {
			throw new ModelException();
		}
	}

	/**
	 * Return the identification of the given road.
	 */
	default String getRoadIdentification(Road road) throws ModelException {
		// Example implementation
		try {
			return road.getID();
		} catch (Throwable exc) {
			throw new ModelException();
		}
	}

	/**
	 * Change the identification of the given road to the given identification.
	 */
	default void changeRoadIdentification(Road road, String newIdentification) throws ModelException {
		// Example implementation
		try {
			road.setID(newIdentification);
		} catch (Throwable exc) {
			throw new ModelException();
		}
	}

	/**
	 * Return the end points of the given road.
	 *   The method returns an array of length 2. Both elements, in turn, are arrays of
	 *   length 2 containing the latitude followed by the longitude as two values of
	 *   type double.
	 */
	default Location[] getEndPoints(Road road) throws ModelException {
		try {
			return road.getEndPoints();
		}
		catch(Throwable exc) {
			throw new ModelException();
		}
	}

	/**
	 * Return the length of the given road.
	 */
	default int getRoadLength(Road road) throws ModelException {
		try {
			return road.getLength();
		}
		catch(Throwable exc) {
			throw new ModelException();
		}
	}

	/**
	 * Change the length of the given road to the given length.
	 */
	default void changeRoadLength(Road road, int newLength) throws ModelException {
		try {
			road.setLength(newLength);
		}
		catch(Throwable exc) {
			throw new ModelException();
		}
	}

	/**
	 * Return the speed limit for the given road.
	 */
	default float getRoadSpeedLimit(Road road) throws ModelException {
		try {
			return road.getSpeedlimit();
		}
		catch(Throwable exc) {
			throw new ModelException();
		}
	}
	
	/**
	 * Change the speed limit of the given road to the given speed limit.
	 */
	default void changeRoadSpeedLimit(Road road, float newSpeedLimit) throws ModelException {
		try {
			road.setSpeedLimit(newSpeedLimit);
		}
		catch(Throwable exc) {
			throw new ModelException();
		}
	}

	/**
	 * Return the average speed for the given road.
	 */
	default float getRoadAverageSpeed(Road road) throws ModelException {
		try {
			return road.getRoadSpeed();
		}
		catch(Throwable exc) {
			throw new ModelException();
		}
	}
	
	/**
	 * Change the average speed of the given road to the given average speed.
	 */
	default void changeRoadAverageSpeed(Road road, float newAverageSpeed) throws ModelException {
		try {
			road.setAvgRoadSpeed(newAverageSpeed);
		}
		catch(Throwable exc) {
			throw new ModelException();
		}
	}
	
	/**
	 * Get the current delay in the direction from the first end point to the second
	 * end point, if directionForth is true, and in the opposite direction if it 
	 * is false.
	 */
	default float getRoadDelayinDirection(Road road, boolean directionForth) throws ModelException {
		try {
			if (directionForth)
				road.getDelayDirectionEndPointTwo();
			return road.getDelayDirectionEndPointOne();
		} catch (Throwable exc) {
			throw new ModelException();
		}
	}

	/**
	 * Set the current delay in the direction from the first end point to the second
	 * end point to the given delay, if directionForth is true, and to the given delay
	 * in the opposite direction if directionForth is false.
	 */
	default void changeRoadDelayinDirection(Road road, float delay, boolean directionForth) throws ModelException {
		try {
			if (directionForth)
				road.setDelayDirectionEndPointTwo(delay);
			road.setDelayDirectionEndPointOne(delay);
		} catch (Throwable exc) {
			throw new ModelException();
		}
	}

	/**
	 * Check whether the given road is blocked in the in the direction from the first
	 * end point to the second end point, if directionForth is true, and in the opposite
	 * direction if it  is false.
	 */
	default boolean getRoadIsBlocked(Road road, boolean directionForth) throws ModelException {
		// To be implemented
		try {
			if (directionForth)
				return road.isBlockedDirectionEndPointTwo();
			return road.isBlockedDirectionEndPointOne();
		} catch (Throwable exc) {
			throw new ModelException();
		}
	}

	/**
	 * Set the blocked state of the given road in the direction from the first end point
	 * to the second end point according to the given flag, if directionForth is true,
	 * and according to the given flag in the opposite direction if directionForth is false.
	 */
	default void changeRoadBlockedState(Road road, boolean flag, boolean directionForth) throws ModelException {
		try {
			if (directionForth)
				road.setBlockedDirectionEndPointTwo(flag);
			road.setBlockedDirectionEndPointOne(flag);
		} catch (Throwable exc) {
			throw new ModelException();
		}
	}

	/*****************
	 * Route methods *
	 *****************/
	
	/**
	 * Return a new route with given start location and given segments.
	 */
	default Route createRoute(Location startLocation, Road... roads) throws ModelException {
		try {
			Route route = new Route(startLocation, roads);
			return route;
		}
		catch (Throwable exc){
			throw new ModelException();
		}
	}
	
	/**
	 * Return the start location of the given route.
	 */
	default Location getRouteStartLocation(Route route) throws ModelException {
		try {
			return route.getStartLocation();
		}
		catch (Throwable exc){
			throw new ModelException();
		}
	}
	
	/**
	 * Return the segments of the given route.
	 */
	default Road[] getRouteSegments(Route route) throws ModelException {
		try{
			return route.getRouteSegements();
		}
		catch (Throwable exc){
			throw new ModelException();
		}
	}
	
	/**
	 * Add the given road at the end of the sequence of segments of the given route.
	 */
	default void addRouteSegment(Route route, Road road) throws ModelException {
		// To be implemented
	}
	
	/**
	 * Remove the segment at the given index from the sequence of segments of the given route.
	 */
	default void removeRouteSegment(Route route, int index) throws ModelException {
		// To be implemented
	}
	
	/**
	 * Return the total length of the given route.
	 */
	default int getRouteTotalLength(Route route) throws ModelException {
		// To be implemented
		return -1;
	}
	
	/**
	 * Check whether the given route is traversable from its start location
	 * to its end location.
	 */
	default boolean isRouteTraversable(Route route) throws ModelException {
		// To be implemented
		return true;
	}
	
	/**
	 * Check whether the given route is traversable from its start location
	 * to its end location.
	 */
	default Location[] getAllLocations(Route route) throws ModelException {
		// To be implemented
		return null;
	}

}
