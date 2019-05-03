package connections;

import java.util.ArrayList;
import java.util.Arrays;

/**
 *  A class of Routes where every route has a start location and a list of 0 to n connecting road segments in it.
 *
 *
 * @author Michiel Van der Haegen
 * @author Sam Haberman
 */
public class Route {

	/**
	 * Variable registering the startLocation of the route
	 */
	private final Location startLocation;
	
	/**
	 * Variable registering the set of roadSegments of the route
	 */
	private Road[] roadSegments;
	
	/**
	 * Variable registering the list of locations for each route
	 */
	private ArrayList<Location> locationList = new ArrayList<Location>();
	
	/**
	 * Initialize a new Route with given start location and collection of road segments.
	 *
	 * @param startLocation
	 * 		The start location of this route
	 * @param roads
	 * 		The connecting road segments that are a part of this route
	 * @throws IllegalArgumentException
	 * 		The given road segments are not valid connecting segments.
	 * 	|	!areValidSegments(roads)
	 * @throws NullPointerException
	 * 		The given start location is null
	 * 	|	startLocation == null
	 * @post The start location of this route is equal to the given start location
	 * 	|	new.getStartLocation() == startLocation
	 * @post The road segments of this route are equal to the given road segments.
	 * 	|	new.getRouteSegments() == roads
	 */
	public Route(Location startLocation, Road... roads) throws IllegalArgumentException, NullPointerException {
		if (startLocation == null)
			throw new NullPointerException();
		this.startLocation = startLocation;
		if (!areValidSegments(roads))
			throw new IllegalArgumentException();
		roadSegments = roads.clone();
	}

	/**
	 * Checks to see if the given road segments are valid, as well as adding each location in the road segments to locationList.
	 * @param roads The road segments to check
	 * @return True if each segment
	 *
	 * @post Each location contained in roads is now in the locationList.
	 * 	|	for each location in roads
	 * 			locationList.contains(location)
	 */
	public boolean areValidSegments(Road... roads) {
		Location startLocation = this.startLocation;
		locationList.add(startLocation);
		if (roads.length == 0)
			return true;
		if (roads[0].getEndPoint1() == startLocation || roads[0].getEndPoint2() == startLocation) {
			if (roads.length == 1) {
				locationList.add(getOtherLocation(roads[0], startLocation));
				return true;
			}
			startLocation = getOtherLocation(roads[0], startLocation);
			for (int i = 1; i <= roads.length - 1; i++) {
				locationList.add(startLocation);
				assert (roads[i].getEndPoint1() == startLocation || roads[i].getEndPoint2() == startLocation);
				startLocation = getOtherLocation(roads[i], startLocation);
			}
			locationList.add(startLocation);
			return true;
		}
		return false;
	}

	/**
	 * 
	 * @param road
	 * @param startLocation
	 * @return
	 */
	public Location getOtherLocation(Road road, Location startLocation) {
		if (road.getEndPoint1() == startLocation)
			return road.getEndPoint2();
		else
			return road.getEndPoint1();
	}

	/**
	 * 
	 * @return
	 */
	public Location getStartLocation() {
		return startLocation;
	}

	/**
	 * 
	 * @return
	 */
	public Road[] getRouteSegements() {
		return roadSegments.clone();
	}

	/**
	 * 
	 * @param road
	 * @throws IllegalArgumentException
	 */
	public void addRouteSegment(Road road) throws IllegalArgumentException {
		if (road == null)
			throw new IllegalArgumentException();
		ArrayList<Road> list = new ArrayList<Road>(Arrays.asList(roadSegments));
		list.add(road);
		roadSegments = new Road[list.size()];
		list.toArray(roadSegments);
		assert (areValidSegments(roadSegments));
	}

	/**
	 * 
	 * @param index
	 * @throws IllegalArgumentException
	 */
	public void removeRouteSegment(int index) throws IllegalArgumentException {
		if (index < 0 || index >= roadSegments.length)
			throw new IllegalArgumentException();
		ArrayList<Road> list = new ArrayList<Road>(Arrays.asList(roadSegments));
		list.remove(index);
		roadSegments = new Road[list.size()];
		list.toArray(roadSegments);
		assert (areValidSegments(roadSegments));
	}

	/**
	 * 
	 * @return
	 */
	public int getTotalLength() {
		int length = 0;
		for (Road road : roadSegments) {
			length += road.getLength();
		}
		return length;
	}

	/**
	 * 
	 * @return
	 */
	public boolean isTraversable() {
		for(int i=0;i<=roadSegments.length-1;i++) {
			if(locationList.get(i)==roadSegments[i].getEndPoint1()) {
				if(roadSegments[i].isBlockedDirectionEndPointTwo())
					return false;
			}
			if(locationList.get(i)==roadSegments[i].getEndPoint2()) {
				if(roadSegments[i].isBlockedDirectionEndPointOne())
					return false;
			}	
		}
		return true;
	}

	/**
	 * 
	 * @return
	 */
	public Location[] getAllLocations() {
		Location[] array = new Location[locationList.size()];
		locationList.toArray(array);
		return array;
	}
	
	@Override
	public String toString() {
		String string1 = new String("This route has the following properties:"+"\n"+"It connects these segments: ");
		String segments = roadSegments[0].getID();
		for(int i = 1; i < roadSegments.length; i++) {
			segments = segments + ", "+roadSegments[i].getID();
		}	
		String string2 = new String("\n"+"With these respective start locations: ");
		String locations = locationList.get(0).getAddress();
		for(int i = 1; i < locationList.size(); i++) {
			locations = locations + ", "+locationList.get(i).getAddress();
		}
		return string1+segments+string2+locations+"\n"+"Blocked in the direction of the route: "+!isTraversable()+"\n";
	}

}
