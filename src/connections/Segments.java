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
}
