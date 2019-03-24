import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FullPart1Test {
	
	public static final float SPEED_OF_LIGHT = 299792458.0F;

	private static int actualScore = 0;
	private static int maxScore = 0;

	Facade theFacade = new Facade() {};

	private static double[] coord_10_20;
	private static double[] coord_15_60;

	private static Road road_A;
	private static int roadNb = 0;

	@BeforeAll
	static void setUpBeforeAll() throws Exception {
		coord_10_20 = new double[] {10.0,20.0};
		coord_15_60 = new double[] {15.3,60.6};
	}

	@AfterAll
	static void tearDownAfterAll() throws Exception {
		System.out.println("Final score: " + Integer.toString(actualScore) + " / " + Integer.toString(maxScore));
	}

	@BeforeEach
	void setUpBeforeEach() throws Exception {
		String roadId = "A" + Integer.toString(roadNb);
		roadNb += 1;
		road_A = theFacade.createRoad
			(roadId, new double[] { 10.0, 20.0 }, new double[] { 22.3, 66.9 },1111, 15.5F,10.66F);
	}

	@Test
	void createRoad_LegalCase() throws Exception {
		maxScore += 24;
		Road theRoad = theFacade.createRoad("E4",coord_10_20,coord_15_60,1500,16.66F,12.22F);
		assertEquals("E4", theFacade.getRoadIdentification(theRoad));
		assertArrayEquals(coord_10_20, theFacade.getEndPoints(theRoad)[0]);
		assertArrayEquals(coord_15_60, theFacade.getEndPoints(theRoad)[1]);
		assertEquals(1500,theFacade.getRoadLength(theRoad));
		assertEquals(16.66F,theFacade.getRoadSpeedLimit(theRoad),0.1E-10F);
		assertEquals(12.22F,theFacade.getRoadAverageSpeed(theRoad),0.1E-10F);
		assertEquals(0.0F,theFacade.getRoadDelayinDirection(theRoad,true),0.1E-10F);
		assertEquals(0.0F,theFacade.getRoadDelayinDirection(theRoad,false),0.1E-10F);
		assertFalse(theFacade.getRoadIsBlocked(theRoad, true));
		assertFalse(theFacade.getRoadIsBlocked(theRoad, false));
		actualScore += 24;
	}


	@Test
	void createRoad_IllegalIdentification() throws Exception {
		maxScore += 5;
		try {
			theFacade.createRoad("6",coord_10_20,coord_15_60,250,14.44F,10.0F);
			fail("Expecting exception to be thrown");
		} catch (ModelException exc) {
			actualScore += 5;
		}
	}

	@Test
	void createRoad_IllegalLength() throws Exception {
		maxScore += 5;
		Road theRoad = theFacade.createRoad("L2",coord_10_20,coord_15_60,-10,5.55F,3.6F);
		assertTrue(theFacade.getRoadLength(theRoad) > 0);
		actualScore += 5;
	}

	@Test
	void createRoad_IllegalEndPoints() throws Exception {
		maxScore += 14;
		try {
			theFacade.createRoad("X2",null,coord_15_60,300,2.22F,1.0F);
			fail("Expecting exception to be thrown");
		} catch (ModelException exc) {
			actualScore += 2;
		}
		try {
			theFacade.createRoad("X3",new double[] {1.0},coord_15_60,400,3.33F,2.0F);
			fail("Expecting exception to be thrown");
		} catch (ModelException exc) {
			actualScore += 2;
		}
		try {
			theFacade.createRoad("X4",new double[] {1.0,2.0,3.0,4.0},coord_15_60,500,4.44F,3.0F);
			fail("Expecting exception to be thrown");
		} catch (ModelException exc) {
			actualScore += 2;
		}
		try {
			theFacade.createRoad("X5",new double[] {-5.0,40.0},coord_15_60,600,5.55F,4.0F);
			fail("Expecting exception to be thrown");
		} catch (ModelException exc) {
			actualScore += 2;
		}
		try {
			theFacade.createRoad("X6",new double[] {71.0,12.4},coord_15_60,700,6.66F,5.0F);
			fail("Expecting exception to be thrown");
		} catch (ModelException exc) {
			actualScore += 2;
		}
		try {
			theFacade.createRoad("X7",new double[] {Double.POSITIVE_INFINITY,30.0},coord_15_60,800,7.77F,6.0F);
			fail("Expecting exception to be thrown");
		} catch (ModelException exc) {
			actualScore += 2;
		}
		try {
			theFacade.createRoad("X8",new double[] {Double.NaN,22.6},coord_15_60,900,8.88F,7.0F);
			fail("Expecting exception to be thrown");
		} catch (ModelException exc) {
			actualScore += 2;
		}
	}

	@Test
	void createRoad_IllegalSpeedLimit() throws Exception {
		maxScore += 5;
		try {
			theFacade.createRoad("X2",coord_10_20,coord_15_60,-10,-4.56F,3.0F);
			fail("Expecting exception to be thrown");
		} catch (ModelException exc) {
			actualScore += 5;
		}
	}

	@Test
	void createRoad_IllegalAverageSpeed() throws Exception {
		maxScore += 5;
		try {
			theFacade.createRoad("X2",coord_10_20,coord_15_60,-10,5.0F,-3.0F);
			fail("Expecting exception to be thrown");
		} catch (ModelException exc) {
			actualScore += 5;
		}
	}

	@Test
	void createRoad_SpeedLimitBelowAverageSpeed() throws Exception {
		maxScore += 8;
		try {
			theFacade.createRoad("X2",coord_10_20,coord_15_60,-10,2.0F,3.0F);
			fail("Expecting exception to be thrown");
		} catch (ModelException exc) {
			actualScore += 8;
		}
	}

	@Test
	void changeIdentification_LegalCase() throws Exception {
		maxScore += 3;
		theFacade.changeRoadIdentification(road_A, "E12");
		assertEquals("E12", theFacade.getRoadIdentification(road_A));
		actualScore += 3;
	}

	@Test
	void changeIdentification_IllegalCases() throws Exception {
		maxScore += 14;
		try {
			theFacade.changeRoadIdentification(road_A, null);
			fail("Expecting exception to be thrown");
		} catch (ModelException exc) {
			actualScore += 2;
		}
		try {
			theFacade.changeRoadIdentification(road_A, "");
			fail("Expecting exception to be thrown");
		} catch (ModelException exc) {
			actualScore += 2;
		}
		try {
			theFacade.changeRoadIdentification(road_A, "012");
			fail("Expecting exception to be thrown");
		} catch (ModelException exc) {
			actualScore += 2;
		}
		try {
			theFacade.changeRoadIdentification(road_A, "A");
			fail("Expecting exception to be thrown");
		} catch (ModelException exc) {
			actualScore += 2;
		}
		try {
			theFacade.changeRoadIdentification(road_A, "Aa");
			fail("Expecting exception to be thrown");
		} catch (ModelException exc) {
			actualScore += 2;
		}
		try {
			theFacade.changeRoadIdentification(road_A, "A2b");
			fail("Expecting exception to be thrown");
		} catch (ModelException exc) {
			actualScore += 2;
		}
		try {
			theFacade.changeRoadIdentification(road_A, "E333");
			fail("Expecting exception to be thrown");
		} catch (ModelException exc) {
			actualScore += 2;
		}
	}

	@Test
	void changeIdentification_NonUniqueCode() throws Exception {
		maxScore += 10;
		try {
			theFacade.changeRoadIdentification(road_A, "A1");
			fail("Expecting exception to be thrown");
		} catch (ModelException exc) {
			actualScore += 10;
		}
	}

	@Test
	void getEndPoints_LeakTests() throws Exception {
		maxScore += 20;
		try {
			double[] endPoint1 = new double[] { 10.0, 20.0 };
			double[] endPoint2 = new double[] { 22.3, 66.9 };
			Road theSpookyRoad = theFacade.createRoad("X2", endPoint1, endPoint2,1000,2.0F,1.0F);
			endPoint1[0] = -333.33;
			assertEquals(10.0,theFacade.getEndPoints(theSpookyRoad)[0][0],1.0E-10);
			actualScore += 10;
		} catch (Throwable exc) {
		}
		try {
			Road theSpookyRoad = 
				theFacade.createRoad("X3", new double[] { 10.0, 20.0 }, new double[] { 22.3, 66.9 },2000,3.0F,2.0F);
			double[] endPoint1 = theFacade.getEndPoints(theSpookyRoad)[0];
			endPoint1[0] = Double.NaN;
			assertEquals(10.0,theFacade.getEndPoints(theSpookyRoad)[0][0],1.0E-10);
			actualScore += 10;
		} catch (Throwable exc) {
		}
	}

	@Test
	void changeLength_LegalCase() throws Exception {
		maxScore += 3;
		int newLength = theFacade.getRoadLength(road_A) + 200;
		theFacade.changeRoadLength(road_A, newLength);
		assertEquals(newLength, theFacade.getRoadLength(road_A));
		actualScore += 3;
	}

	@Test
	void changeLength_IllegalCase() throws Exception {
		maxScore += 5;
		theFacade.changeRoadLength(road_A, -1);
		assertTrue(theFacade.getRoadLength(road_A) > 0);
		actualScore += 5;
	}

	@Test
	void changeSpeedLimit_LegalCase() throws Exception {
		maxScore += 3;
		float newSpeedLimit = theFacade.getRoadSpeedLimit(road_A) + 10.0F;
		theFacade.changeRoadSpeedLimit(road_A, newSpeedLimit);
		assertEquals(newSpeedLimit, theFacade.getRoadSpeedLimit(road_A),0.1E-10F);
		actualScore += 3;
	}

	@Test
	void changeSpeedLimit_IllegalCases() throws Exception {
		maxScore += 10;
		try {
			theFacade.changeRoadSpeedLimit(road_A, Float.NaN);
			fail("Expecting exception to be thrown");
		} catch (ModelException exc) {
			actualScore += 2;
		}
		try {
			theFacade.changeRoadSpeedLimit(road_A, Float.NEGATIVE_INFINITY);
			fail("Expecting exception to be thrown");
		} catch (ModelException exc) {
			actualScore += 2;
		}
		try {
			theFacade.changeRoadSpeedLimit(road_A, Float.POSITIVE_INFINITY);
			fail("Expecting exception to be thrown");
		} catch (ModelException exc) {
			actualScore += 2;
		}
		try {
			theFacade.changeRoadSpeedLimit(road_A, SPEED_OF_LIGHT+100.0F);
			fail("Expecting exception to be thrown");
		} catch (ModelException exc) {
			actualScore += 2;
		}
		try {
			theFacade.changeRoadSpeedLimit(road_A, -0.5E-10F);
			fail("Expecting exception to be thrown");
		} catch (ModelException exc) {
			actualScore += 2;
		}
	}

	@Test
	void changeSpeedLimit_ValueBelowAverageSpeed() throws Exception {
		maxScore += 15;
		try {
			float speedLimit = theFacade.getRoadAverageSpeed(road_A) - 1.0F;
			theFacade.changeRoadSpeedLimit(road_A, speedLimit);
			fail("Expecting exception to be thrown");
		} catch (ModelException exc) {
			actualScore += 15;
		}
	}

	@Test
	void changeAverageSpeed_LegalCase() throws Exception {
		maxScore += 3;
		float averageSpeed = theFacade.getRoadAverageSpeed(road_A) / 2.0F;
		theFacade.changeRoadAverageSpeed(road_A, averageSpeed);
		assertEquals(averageSpeed, theFacade.getRoadAverageSpeed(road_A),0.1E-10F);
		actualScore += 3;
	}

	@Test
	void changeAverageSpeed_IllegalCases() throws Exception {
		maxScore += 10;
		try {
			theFacade.changeRoadAverageSpeed(road_A, Float.NaN);
			fail("Expecting exception to be thrown");
		} catch (ModelException exc) {
			actualScore += 2;
		}
		try {
			theFacade.changeRoadAverageSpeed(road_A, Float.NEGATIVE_INFINITY);
			fail("Expecting exception to be thrown");
		} catch (ModelException exc) {
			actualScore += 2;
		}
		try {
			theFacade.changeRoadAverageSpeed(road_A, Float.POSITIVE_INFINITY);
			fail("Expecting exception to be thrown");
		} catch (ModelException exc) {
			actualScore += 2;
		}
		try {
			theFacade.changeRoadAverageSpeed(road_A, SPEED_OF_LIGHT+100.0F);
			fail("Expecting exception to be thrown");
		} catch (ModelException exc) {
			actualScore += 2;
		}
		try {
			theFacade.changeRoadAverageSpeed(road_A, -0.5E-10F);
			fail("Expecting exception to be thrown");
		} catch (ModelException exc) {
			actualScore += 2;
		}
	}

	@Test
	void changeAverageSpeed_ValueExceedingSpeedLimit() throws Exception {
		maxScore += 15;
		try {
			float averageSpeed = theFacade.getRoadSpeedLimit(road_A) + 2.0F;
			theFacade.changeRoadAverageSpeed(road_A, averageSpeed);
			fail("Expecting exception to be thrown");
		} catch (ModelException exc) {
			actualScore += 15;
		}
	}

	@Test
	void changeCurrentDelay_LegalCases() throws Exception {
		maxScore += 6;
		theFacade.changeRoadDelayinDirection(road_A, 12.0F,true);
		assertEquals(12.0F, theFacade.getRoadDelayinDirection(road_A,true),0.1);
		actualScore += 3;
		theFacade.changeRoadDelayinDirection(road_A, 15.0F,false);
		assertEquals(15.0F, theFacade.getRoadDelayinDirection(road_A,false),0.1);
		actualScore += 3;
	}

	@Test
	void changeIsBlocked_LegalCases() throws Exception {
		maxScore += 8;
		theFacade.changeRoadBlockedState(road_A, true, true);
		assertTrue(theFacade.getRoadIsBlocked(road_A, true));
		actualScore += 2;
		theFacade.changeRoadBlockedState(road_A, false, true);
		assertFalse(theFacade.getRoadIsBlocked(road_A, true));
		actualScore += 2;
		theFacade.changeRoadBlockedState(road_A, true, false);
		assertTrue(theFacade.getRoadIsBlocked(road_A, false));
		actualScore += 2;
		theFacade.changeRoadBlockedState(road_A, false, false);
		assertFalse(theFacade.getRoadIsBlocked(road_A, false));
		actualScore += 2;
	}

}