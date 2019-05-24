package connections;

public abstract class Segments {

	/**
	 * Returns all valid start locations for this road
	 */
	public abstract Location[] getStartLocations();
	
	/**
	 * Returns all valid end locations for this road
	 */
	public abstract Location[] getEndLocations();

	/**
	 * Returns the length of the segment
	 */
	public abstract int getLength();

	/**
	 * Returns the other location of the road when given a location
	 */
	public abstract Location getOtherLocation(Location location);

	/**
	 * Returns a array of all the segments for a Route
	 */
	public abstract Object[] getRouteSegments();

	/**
	 * Checks to see whether an object contains itself in one of its segments.
	 * @param segment The segment to check
	 */
	public abstract boolean containsItself(Object segment);
	
}
