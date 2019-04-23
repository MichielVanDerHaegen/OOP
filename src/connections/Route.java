package connections;

import java.util.ArrayList;
import java.util.Arrays;

public class Route {

	private final Location startLocation;
	private Road[] roadSegments;

	//private final Location endLocation;


	public Route(Location startLocation, Road...roads) throws IllegalArgumentException, NullPointerException {
		if (startLocation == null)
			throw new NullPointerException();
		this.startLocation=startLocation;
		if(!areValidSegments(roads))
			throw new IllegalArgumentException();
		roadSegments = roads.clone();
	}

	public boolean areValidSegments(Road...roads) {
		int length = roads.length;
		if (length == 0)
			return true;
		Road firstSegment=roads[0];
		Road lastSegment=roads[length-1];
		//if the startlocation is equal to one of the locations of the first segment
		if(firstSegment.getEndPoint1()==this.startLocation || firstSegment.getEndPoint2()==this.startLocation) {
			//then check that the other location of the firstsegment is equal to one of the locations of the second segment
			if (length == 1)
					return true;
			if(getOtherLocation(firstSegment)==roads[1].getEndPoint1() || getOtherLocation(firstSegment)==roads[1].getEndPoint2()) {
				//then for each segment, check that one of its locations is equal to one of the locations of the previous one
				//and equal to one of the locations of the next one
				for(int i=2;i<roads.length-1;i++) {
					if (roads[i].getEndPoint1() == roads[i - 1].getEndPoint1()
							|| roads[i].getEndPoint1() == roads[i - 1].getEndPoint2()) {
						if (roads[i].getEndPoint2() == roads[i + 1].getEndPoint1()
								|| roads[i].getEndPoint2() == roads[i + 1].getEndPoint2()) {
						}
					}
					if (roads[i].getEndPoint2() == roads[i - 1].getEndPoint1()
							|| roads[i].getEndPoint2() == roads[i - 1].getEndPoint2()) {
						if (roads[i].getEndPoint1() == roads[i + 1].getEndPoint1()
								|| roads[i].getEndPoint1() == roads[i + 1].getEndPoint2()) {
						}
					}
				}
//					assert (roads[i].getEndPoint1()==roads[i-1].getEndPoint1()) || (roads[i].getEndPoint1()==roads[i-1].getEndPoint2()) || (roads[i].getEndPoint2()==roads[i-1].getEndPoint1()) || (roads[i].getEndPoint2()==roads[i-1].getEndPoint2());
//					assert (roads[i].getEndPoint1()==roads[i+1].getEndPoint1()) || (roads[i].getEndPoint1()==roads[i+1].getEndPoint2()) || (roads[i].getEndPoint2()==roads[i+1].getEndPoint1()) || (roads[i].getEndPoint2()==roads[i+1].getEndPoint2());
				
				//finally, check that the second to last segment has one location in common with the last segment
				assert (roads[length-2].getEndPoint1()==lastSegment.getEndPoint1()) || (roads[length-2].getEndPoint1()==lastSegment.getEndPoint2()) || (roads[length-2].getEndPoint2()==lastSegment.getEndPoint1()) || (roads[length-2].getEndPoint2()==lastSegment.getEndPoint2());
				return true;
			}
			return false;
		}
		return false;
	}
	
	
	public Location getOtherLocation(Road road) {
		if(road.getEndPoint1()==this.startLocation)
			return road.getEndPoint2();
		else
			return road.getEndPoint1();
		
	}

	public Location getStartLocation(){
		return startLocation;
	}

	public Road[] getRouteSegements(){
		return roadSegments.clone();
	}
	
	
	public void addRouteSegment(Road road) throws IllegalArgumentException{
		if(road==null)
			throw new IllegalArgumentException();
		ArrayList<Road> list = new ArrayList<Road>(Arrays.asList(roadSegments));
		list.add(road);
		roadSegments=list.toArray(roadSegments);
		assert(areValidSegments(roadSegments));
	}
	
	public void removeRouteSegment(int index) throws IllegalArgumentException{
		if(index < 0 || index>=roadSegments.length)
			throw new IllegalArgumentException();
		ArrayList<Road> list = new ArrayList<Road>(Arrays.asList(roadSegments));
		list.remove(index);
		roadSegments=list.toArray(roadSegments);
		assert(areValidSegments(roadSegments));
	}
	
	public int getTotalLength() {
		int length = 0;
		for(Road road: roadSegments) {
			length += road.getLength();
		}
		return length;
	}
	
	public boolean isTraversable() {
		return false;
	}
	
	public Location[] getAllLocations() {
		ArrayList<Location> list = new ArrayList<Location>();
		for(Road road: roadSegments) {
			list.add(road.getEndPoint1());
			list.add(road.getEndPoint2());
		}
		Location[] array = (Location[]) list.toArray();
		return array;
	}

}
