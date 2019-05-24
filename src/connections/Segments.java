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
	
	
	public abstract int getLength();
	
	public abstract Location getOtherLocation(Location location);
	
	public abstract Object[] getRouteSegments();

	public abstract boolean containsItself(Object segment);
	
}
