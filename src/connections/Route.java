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
	private Location startLocation;
	
	private Location endLocation;
	
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
	 * Checks to see if the given road segments are valid, as well as
	 * adding each location in the road segments to locationList, and recording the last location visited as end location
	 * for the route.
	 * @param roads The road segments to check
	 * @return True if each road segment in the given segments connects in a unbroken line where the location that is not the
	 * 			start location for the first road segment is the same as a location in the next segment where in turn the second
	 * 			segments other location is in turn the same as one of the locations in the third road segment until there is a final
	 * 			location in the last road segment where the road ends.
	 * 		|	result == True if
	 * 		|	for 0..roads.length-1
	 * 		|		assert(roads[i].getEndPoint1() == startLocation || roads[i].getEndPoint2() == startLocation)
	 * 		|		startLocation = getOtherLocation(roads[i], startLocation)
	 * @return True if there are zero road segements given
	 * 		|	if roads.length ==0
	 * 		|	result == True
	 * @return True if there is one road segment, and one of the segments locations is the start location of the route.
	 * 		|	if roads.length ==1
	 * 		|		result == roads[0].getEndPoint1() == startLocation || roads[0].getEndpoint2() == startLocation
	 * @post Each location contained in roads is now in the locationList.
	 * 	|	for each location in roads
	 * 			locationList.contains(location)
	 * @post The final location visited for each Route is the end location of the route.
	 * 	| 	 if roads.length == 0 then
	 * 	|		new.getEndLocation == startLocation
	 * 	|	 else if roads.length ==1 then
	 * 	|	 	new.getEndLocation == getOtherLocation(roads[0], startLocation)
	 * 	|	 else
	 * 	|	 	if roads[-1].getEndPoint1() == roads[-2].getEndPoint2() || roads[-1].getEndPoint1() == roads[-2].getEndPoint2()
	 * 	|	 		then new.getEndLocation() == roads[-1].getEndPoint2()
	 * 	|	 	else
	 *  |	 		new.getEndLocation() == roads[-1].getEndPoint1()
	 */
	public boolean areValidSegments(Road... roads) {
		Location startLocation = this.startLocation;
		locationList.add(startLocation);
		if (roads.length == 0) {
			this.endLocation=startLocation;
			return true;
		}
		if (roads[0].getEndPoint1() == startLocation || roads[0].getEndPoint2() == startLocation) {
			if (roads.length == 1) {
				locationList.add(getOtherLocation(roads[0], startLocation));
				this.endLocation=getOtherLocation(roads[0], startLocation);
				return true;
			}
			startLocation = getOtherLocation(roads[0], startLocation);
			for (int i = 1; i <= roads.length - 1; i++) {
				locationList.add(startLocation);
				assert (roads[i].getEndPoint1() == startLocation || roads[i].getEndPoint2() == startLocation);
				startLocation = getOtherLocation(roads[i], startLocation);
			}
			locationList.add(startLocation);
			this.endLocation=startLocation;
			return true;
		}
		return false;
	}

	/**
	 * Given a road and one of its locations, gets the other location of that road
	 *
	 * @param road
	 * 		The road to check
	 * @param startLocation
	 * 		One of the locations of the road
	 * @return If the first endpoint of the road is the start location returns the second endpoint.
	 * 		|	if (road.getEndPoint1() == startLocation)
	 * 		|	return road.getEndPoint2()
	 * @return If the second endpoint of the road is the start location, returns the first endpoint.
	 * 		|	if (road.getEndPoint2() == startLocation)
	 * 		|	return road.getEndPoint1()
	 */
	public Location getOtherLocation(Road road, Location startLocation) {
		if (road.getEndPoint1() == startLocation)
			return road.getEndPoint2();
		else
			return road.getEndPoint1();
	}

	/**
	 * Returns the start location of the Route
	 *
	 */
	public Location getStartLocation() {
		return startLocation;
	}

	/**
	 * Returns the end location of the Route
	 */
	public Location getEndLocation() {
		 return endLocation;
	}
	/**
	 * Returns an array of Roads consisting of each road segment in the Route
	 */
	public Road[] getRouteSegements() {
		return roadSegments.clone();
	}

	/**
	 * Adds a road segment to the route if it connects to a previous road segment already in the list.
	 *
	 * @param road The road segment to add
	 * @throws IllegalArgumentException
	 * 		The given road is null
	 * 	|	road == null
	 * @post The given road segment is added to the routes list of road segments, assuming it is a valid list after addition
	 * 	|	if areValidSegments(roadSegments)
	 * 	|		new.roadSegments.contains(road)
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
	 * Remove the segment at the given index from the sequence of road segments for this route.
	 *
	 * @param  index
	 *         The index of the segment to be removed.
	 * @post   The number of segments of this route is
	 *         decreased by 1.
	 *       | new.getTotalLength() == getTotalLength() - 1
	 * @post   This route no longer has the segment at the given index as
	 *         one of its segments.
	 *       | ! new.getRouteSegments().contains(roadSegment[index])
	 * @post   All segments registered at an index beyond the index at
	 *         which the removed segment was registered, are shifted
	 *         one position to the left.
	 *       | for each I,J in 1..getTotalLength():
	 *       |   if ( (getSegmentAt(I) == getSegmentAt(index)) and (I < J) )
	 *       |     then new.getSegmentAt(J-1) == getSegmentAt(J)
	 * @post The new array of road segments for the route is a valid list of road segments
	 * 		 | areValidSegments(roadSegments)
	 * @throws IndexOutOfBoundsException
	 *         If the chosen index number is greater than or equal to the number of road segments
	 *         in the route or less than 0
	 *       | (index < 0) || (index >= roadSegments.length)
	 *
	 */
	public void removeRouteSegment(int index) throws IndexOutOfBoundsException {
		if (index < 0 || index >= roadSegments.length)
			throw new IndexOutOfBoundsException();
		ArrayList<Road> list = new ArrayList<Road>(Arrays.asList(roadSegments));
		list.remove(index);
		roadSegments = new Road[list.size()];
		list.toArray(roadSegments);
		assert (areValidSegments(roadSegments));
	}

	/**
	 * Returns the total road length of the road segments in this route.
	 *
	 * @return the number of roads that are a part of this Route.
	 *	| int length = 0
	 *	| for road in roadSegments
	 *	|	length += road.getLength
	 *	| return length
	 */
	public int getTotalLength() {
		int length = 0;
		for (Road road : roadSegments) {
			length += road.getLength();
		}
		return length;
	}

	/**
	 * Checks to see if a route is traversable.
	 *
	 * @return Status of route checking whether each road segment in the route is not blocked in the
	 * direction being traveled.
	 * 	|	result == True if
	 * 			for 0..roadSegments.length-1
	 * 				if (locationList.get(i) == roadSegments[i].getEndPoint1()
	 * 					! roadSegments[i].isBlockedDirectionEndPointTwo()
	 * 				if (locationList.get(i) == roadSegments[i].getEndPoint2()
	 * 					! roadSegments[i].isBlockedDirectionEndPointOne()
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
	 * Returns an array of all the locations that are visited when traveling through this route
	 */
	public Location[] getAllLocations() {
		Location[] array = new Location[locationList.size()];
		locationList.toArray(array);
		return array;
	}

	/**
	 * This method overrides the toString representation of an instance of the road class
	 *
	 * @return Returns a string stating "This route has the following properties, It connects these segments:" then listing
	 * each road segment in the array of road segments, then stating "with these respective locations" listing each location
	 * visited while traveling along the route. Finally returning a string saying "Blocked in the direction of the route:"
	 * stating the current status of the route and whether it is traversable or not.
	 */
	@Override
	public String toString() {
		String string1 = new String("This route has the following properties:"+"\n"+"It connects these segments: ");
		String segments = roadSegments[0].getID();
		for(int i = 1; i < roadSegments.length; i++) {
			segments = segments + ", "+roadSegments[i].getID();
		}	
		String string2 = new String("\n"+"With these respective locations: ");
		String locations = locationList.get(0).getAddress();
		for(int i = 1; i < locationList.size(); i++) {
			locations = locations + ", "+locationList.get(i).getAddress();
		}
		return string1+segments+string2+locations+"\n"+"Blocked in the direction of the route: "+!isTraversable()+"\n";
	}

}
