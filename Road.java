import java.util.ArrayList;

import be.kuleuven.cs.som.annotate.*;

public class Road {

	private String id;
	private ArrayList<String> idArray = new ArrayList<String>();
	private int minIdLength=2;
	private int maxIdLength=3;
	private int length;
	private double[] startCoordinate;
	private double[] endCoordinate;
	private double MAX_COORDINATE = 70.0;
	private float speedLimit;
	private float speed;
	private float currentDelay;
	private boolean blocked;
	private final float MAX_SPEED = (float) 299792458.0;

	
	/**
	 * Sets the identification of the road to the given ID value if it is unique
	 * if the road id is being changed, then delete the old id from the id list and add the new id
	 * @param id
	 * @throws IllegalArgumentException
	 */
	public void setID(String id) throws IllegalArgumentException {
		if (!isValidID(id))
			throw new IllegalArgumentException();
		String oldID = this.getId();
		if(oldID != null)
			idArray.remove(oldID);
		idArray.add(id);
		this.id = id;
	}

	/**
	 * Returns the unique identifier of the road
	 * @return this.id
	 */
	public String getId() {
		return this.id;
	}

	/**
	 * Checks if the given id is a valid id
	 * @param ID identification to verify
	 * @return true if the id length is between the minimum and maximum id lenght
	 * 			follows the correct naming convention
	 * 			is a unique id not used for another road
	 * 		| 	(id.length() >= getMinIdLength() || id.length() <= getMaxIdLength()) && (correctIdFormat(id) && (isUniqueId(id))
	 */
	public boolean isValidID(String id) {
		if(id.length() >= minIdLength || id.length() <= maxIdLength)
			if(correctIdFormat(id))
				isUniqueID(id);
		return false;						
	}
	
	/**
	 * Checks if the given follows the correct naming conventions
	 * @param id the id to be checked
	 * @return true if the id has the first character as an upper case letter and at least one number following
	 * 		| if(Character.isUpperCase(id.charAt(0)))
	 * 			then for(int i=1;i<=id.length();i++)
					if(!Character.isDigit(i))
						return false;
				else
					return true;
	 */
	public boolean correctIdFormat(String id) {
		if(Character.isUpperCase(id.charAt(0)))
			for(int i=1;i<=id.length();i++) {
				if(!Character.isDigit(i))
					return false;
			}
		return true;
	}
	
	/**
	 * Checks if the given id is unique
	 * @param id the id to be checked
	 * @return true if the id is not used for another road
	 * 		| for(String i:idArray)
	 * 		 then if(id.equals(i))
					return false;
			else return true;
	 */
	public boolean isUniqueID(String id) {
		for(String i:idArray) {
			if(id.equals(i))
				return false;
		}
		return true;
	}
	
	/**
	 * return the minimum id length of the road identification
	 * @return minIdLength
	 */
	@Basic
	public int getMinIdLength() {
		return minIdLength;
	}

	/**
	 * Sets the minimum id length for the road identifications
	 * @param minIdLength the given minimum length
	 * @post the minimum id length is set to the given value
	 * 		| new.getMinIdLength() == minIdLength
	 * @throws NullPointerException if the given value is null
	 * 		| minIdLength == null
	 * @throws IllegalArgumentException if the given value is less than two or greater the maximum id length
	 * 		| if((minIdLength<2) || (minIdLength>maxIdLength))
	 */
	public void setMinIdLength(int minIdLength) throws NullPointerException, IllegalArgumentException {
		if((minIdLength<2) || (minIdLength>maxIdLength))
			throw new IllegalArgumentException();
		this.minIdLength = minIdLength;
	}

	/**
	 * returns the max id length of the road identification
	 * @return maxIdLength;
	 */
	@Basic
	public int getMaxIdLength() {
		return maxIdLength;
	}

	/**
	 * Sets the maximum id length for the road identification
	 * @param maxIdLength the given maximum length
	 * @post the maximum id length is set to the given value
	 * 		| new.getMaxIdLength() == maxIdLength
	 * @throws NullPointerException if the given value is null
	 * 		maxIdLength == null
	 * @throws IllegalArgumentException if the given value is less than the minimum value or greater than the maximum integer value
	 * 		| if((maxIdLength<minIdLength) || (maxIdLength>Integer.MAX_VALUE))
	 */
	public void setMaxIdLength(int maxIdLength) throws NullPointerException, IllegalArgumentException{
		if((maxIdLength<minIdLength) || (maxIdLength>Integer.MAX_VALUE))
			throw new IllegalArgumentException();
		this.maxIdLength = maxIdLength;
	}

	/**
	 * Set the length of the road to this given length in meters
	 * 
	 * @param length The new length in meters for this road
	 * @post the length in meters of this road is set to 1 if the given length is
	 *       equal to zero | if(length == 0) then new.getLength() == 1;
	 * @post the length in meters of this road is set to the absolute value of the
	 *       given length | new.getLength() == Math.abs(length);
	 */

	public void setLength(int length) {
		if (length == 0)
			this.length = 1;
		else
			this.length = Math.abs(length);
	}

	public int getLength() {
		return length;
	}

	/**
	 * Sets the speed limit (in meters per second) of the road to the given
	 * speedlimit
	 * 
	 * @param speedLimit the new speedlimit (in meters per second) for this road
	 * @post the speed limit of the road is set to the given speed limit |
	 *       new.getSpeedLimit()==speedLimit
	 */
	public void setSpeedLimit(float speedLimit) {
		this.speedLimit = speedLimit;
	}

	@Basic
	public float getSpeedLimit() {
		return speedLimit;
	}

	/**
	 * Checks if the given speed limit is a valid speed limit
	 * 
	 * @param speedLimit the speed limit to check
	 * @return returns true if the given speed limit is greater than zero and less
	 *         than or equal to the max speedlimit | result == ((speedLimit > 0) &&
	 *         (speedLimit <= MAX_SPEED))
	 */
	public boolean isValidSpeedLimit(float speedLimit) {
		return ((speedLimit > 0) && (speedLimit <= MAX_SPEED));
	}

	
	public void setMaxCoordinate(double coordinate) {
		this.MAX_COORDINATE = coordinate;
	}

	/**
	 * Checks if the given coordinate is a valid coordinate
	 * 
	 * @param coordinate the coordinate to check
	 * @return returns true if both the lattitude and longitude of the given
	 *         coordinate are greater than or equal to zero and less than or equal
	 *         to the maximum coordinate | result == ((coordinate[0] > 0) &&
	 *         (coordinate[1] > 0) && (coordinate[0] <= MAX_COORDINATE) &&
	 *         (coordinate[1] <= MAX_COORDINATE));
	 * 
	 */
	public boolean isValidCoordinate(double[] coordinate) {
		return ((coordinate[0] > 0) && (coordinate[1] > 0) && (coordinate[0] <= MAX_COORDINATE)
				&& (coordinate[1] <= MAX_COORDINATE));
	}



}
