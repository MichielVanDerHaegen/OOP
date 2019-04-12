import road.Road;

/**
 * A facade to enable a collection of tests.
 * 
 * The facade is an interface, a concept that we have not covered in the course.
 * An interface in Java is very similar to a class. It groups definitions of methods.
 * By definition, all methods in an interface are public, even if they are not
 * explicitly qualified public. Methods with an implementation in an interface are
 * called default methods, and must have the keyword default for that purpose. 
 * 
 * The facade assumes that you have at least a class Road.
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

	/**
	 * Return a new road with the given identification, given end points, given length,
	 * given speed limit, given average speed. with no delays in both directions nor
	 * with blockages in both directions.
	 */
	default Road createRoad(String identification, double[] endPoint1, double[] endPoint2,
			int length, float speedLimit, float averageSpeed)
			throws ModelException {
		try {
		Road road = new Road(identification, endPoint1, endPoint2, length, speedLimit, averageSpeed);
		return road;
		}catch (Throwable exc) {
			throw new ModelException();
		}
		
	}


	/**
	 * Return the identification of the given road.
	 */
	default String getRoadIdentification(Road road) throws ModelException {
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
	default double[][] getEndPoints(Road road) throws ModelException {
		// To be implemented
		try {
			return road.getEndPoints();
		} catch (Throwable exc) {
			throw new ModelException();
		}
	}

	/**
	 * Return the length of the given road.
	 */
	default int getRoadLength(Road road) throws ModelException {
		// To be implemented
		try {
			return road.getLength();
		} catch (Throwable exc) {
			throw new ModelException();
		}
	}

	/**
	 * Change the length of the given road to the given length.
	 */
	default void changeRoadLength(Road road, int newLength) throws ModelException {
		// To be implemented
		try {
			road.setLength(newLength);
		} catch (Throwable exc) {
			throw new ModelException();
		}
	}

	/**
	 * Return the speed limit for the given road.
	 */
	default float getRoadSpeedLimit(Road road) throws ModelException {
		// To be implemented
		try {
			return road.getSpeedlimit();
		} catch (Throwable exc) {
			throw new ModelException();
		}
	}
	
	/**
	 * Change the speed limit of the given road to the given speed limit.
	 */
	default void changeRoadSpeedLimit(Road road, float newSpeedLimit) throws ModelException {
		// To be implemented
		try {
			road.setSpeedLimit(newSpeedLimit);
		} catch (Throwable exc) {
			throw new ModelException();
		}
	}

	/**
	 * Return the average speed for the given road.
	 */
	default float getRoadAverageSpeed(Road road) throws ModelException {
		// To be implemented
		try {
			return road.getRoadSpeed();
		} catch (Throwable exc) {
			throw new ModelException();
		}
	}
	
	/**
	 * Change the average speed of the given road to the given average speed.
	 */
	default void changeRoadAverageSpeed(Road road, float newAverageSpeed) throws ModelException {
		// To be implemented
		try {
			road.setAvgRoadSpeed(newAverageSpeed);
		} catch (Throwable exc) {
			throw new ModelException();
		}
	}
	
	/**
	 * Get the current delay in the direction from the first end point to the second
	 * end point, if directionForth is true, and in the opposite direction if it 
	 * is false.
	 */
	default float getRoadDelayinDirection(Road road, boolean directionForth) throws ModelException {
		// To be implemented
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
		// To be implemented
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
		// To be implemented
		try {
			if (directionForth)
				road.setBlockedDirectionEndPointTwo(flag);
			road.setBlockedDirectionEndPointOne(flag);
		} catch (Throwable exc) {
			throw new ModelException();
		}
	}
}
