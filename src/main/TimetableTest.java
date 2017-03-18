package main;

import static org.junit.Assert.*;

import javax.swing.table.DefaultTableModel;

import org.junit.*;

@SuppressWarnings("static-access")
public class TimetableTest {
	//Because we want to ensure that there's no interference
	//We create objects that will be used for each test
	Timetable testTimetable = new Timetable();
	DefaultTableModel testTableModel = new DefaultTableModel();

	@BeforeClass
	public static void setup() {
	}
	
	@AfterClass
	public static void tearDown() {
		System.out.println("Teardown");
	}
	
	@Test
	public void testGetTimesDAY() {
		testTimetable.getTimes("1 DAY", testTableModel);
		assertNotSame(1, testTableModel.getRowCount());
	}
	
	@Test
	public void testGetTimesMONTH() {
		testTimetable.getTimes("1 MONTH", testTableModel);
		assertNotSame(1, testTableModel.getRowCount());
	}
	
	@Test 
	public void testGetTimesWEEK() {
		testTimetable.getTimes("1 WEEK",  testTableModel);
		assertNotSame(1, testTableModel.getRowCount());
	}
	
	@Test 
	public void testGetTimesNULL() {
		testTimetable.getTimes("", testTableModel);
		assertNotSame(1, testTableModel.getRowCount());
	}
	
	@Test 
	public void testGetByRoute() {
		testTimetable.getByRoute("University Bus 1",testTableModel);
	}

}
