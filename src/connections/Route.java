package connections;

import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.ArrayList;
import java.util.Arrays;

public class Route {

	/**
	 * 
	 */
	private final Location startLocation;
	
	/**
	 * 
	 */
	private Road[] roadSegments;
	private ArrayList<Location> locationList = new ArrayList<Location>();

	// private final Location endLocation;
	
	/**
	 * 
	 * @param startLocation
	 * @param roads
	 * @throws IllegalArgumentException
	 * @throws NullPointerException
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
	 * 
	 * @param roads
	 * @return
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
		
		
//		for(Road road : roadSegments) {
//			assert (!road.isBlockedDirectionEndPointOne() || !road.isBlockedDirectionEndPointTwo());
//			//change this, because for each seperate road we need to check which direction it is (the method in the facade ask for the direction of the startLocation to the endLocation)
//			return true;
//		}
//		return false;
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

}
