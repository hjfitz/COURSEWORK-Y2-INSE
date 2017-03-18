package main;

import static org.junit.Assert.*;

import java.text.ParseException;

import javax.swing.JComboBox;
import javax.swing.table.DefaultTableModel;

import org.junit.*;

@SuppressWarnings({ "rawtypes", "static-access", "deprecation" })
public class TimetableTest {
	// Because we want to ensure that there's no interference
	// We create objects that will be used for each test
	Timetable testTimetable = new Timetable();
	DefaultTableModel testTableModel = new DefaultTableModel();
	JComboBox testCmbRoutes = new JComboBox();
	JComboBox testCmbStops = new JComboBox();

	// Because this function is completed before any interaction with the form
	// may happen
	// and raw data from the database *must* be used
	// we test this first.
	
	@Test
	public void testGetStops() {
		int initialLength = testCmbRoutes.getItemCount();
		testTimetable.getStops(testCmbRoutes);
		assertNotSame(initialLength, testCmbRoutes.getItemCount());
	}

	@Test
	public void testgetRoutes() {
		int initialLength = testCmbStops.getItemCount();
		testTimetable.getRoutes(testCmbStops);
		assertNotSame(initialLength, testCmbStops.getItemCount());
	}

	@Test
	public void testGetTimesDAY() {
		int initialLength = testTableModel.getRowCount();
		testTimetable.getTimes("1 DAY", testTableModel);
		assertNotSame(initialLength, testTableModel.getRowCount());
	}

	@Test
	public void testGetTimesMONTH() {
		int initialLength = testTableModel.getRowCount();
		testTimetable.getTimes("1 MONTH", testTableModel);
		assertNotSame(initialLength, testTableModel.getRowCount());
	}

	@Test
	public void testGetTimesWEEK() {
		int initialLength = testTableModel.getRowCount();
		testTimetable.getTimes("1 WEEK", testTableModel);
		assertNotSame(initialLength, testTableModel.getRowCount());
	}

	@Test
	public void testGetTimesNULL() {
		int initialLength = testTableModel.getRowCount();
		System.out.println("initial count");
		System.out.println(initialLength);
		testTimetable.getTimes("", testTableModel);
		assertNotSame(initialLength, testTableModel.getRowCount());
	}

	@Test
	public void testGetByRoute() {
		int initialLength = testTableModel.getRowCount();
		testTimetable.getByRoute("University Bus 1", testTableModel);
		assertNotSame(initialLength, testTableModel.getRowCount());
	}

	@Test
	public void testChangeRouteNo() {
		//firstly populate
		testTimetable.getStops(testCmbRoutes);
		//then check
		testCmbRoutes.setSelectedIndex(1);
		testTimetable.changeRouteNo(testCmbRoutes);
		assertNotSame("101", testTimetable.thisStop);
	}

	@Test
	public void testGetWeekTwoYears() {
		int initialLength = testTableModel.getRowCount();
		try {
			testTimetable.getWeek("2019-03-09", testTableModel);
		} catch (ParseException e) {
			fail(e.getMessage());
		}
		//we check that the table has changed
		assertSame(initialLength, testTableModel.getRowCount());
		//Because we know that there will be no records in the database, we can assume that there are no rows.
		assertSame(0, testTableModel.getRowCount());
	}
	
	@Test
	public void testGetWeekManyYearsAgo() {
		int initialLength = testTableModel.getRowCount();
		try {
			testTimetable.getWeek("919-03-09", testTableModel);
		} catch (ParseException e) {
			fail(e.getMessage());
		}
		assertSame(initialLength, testTableModel.getRowCount());
		//Because we know that there will be no records in the database, we can assume that there are no rows.
		assertSame(0, testTableModel.getRowCount());
	}
	
	@Test
	public void testGetWeekIncorrectParse() {
		int initialLength = testTableModel.getRowCount();
		try {
			testTimetable.getWeek("31st jan", testTableModel);
		} catch (ParseException e) {
			fail(e.getMessage());
		}
		assertSame(initialLength, testTableModel.getRowCount());
		//Because we know that there will be no records in the database, we can assume that there are no rows.
		assertSame(0, testTableModel.getRowCount());
	}
	
	@Test
	public void testGetWeekNonExistantDate() {
		int initialLength = testTableModel.getRowCount();
		try {
			testTimetable.getWeek("2017-02-30", testTableModel);
		} catch (ParseException e) {
			fail(e.getMessage());
		}
		assertSame(initialLength, testTableModel.getRowCount());
		//Because we know that there will be no records in the database, we can assume that there are no rows.
		assertSame(0, testTableModel.getRowCount());
	}

	@Test
	public void testParseDateNormal() {
		String expected = "Thu 09-Mar";
		try {
			String actual = testTimetable.parseDate("2017-03-09");
			assertEquals(expected, actual);
		} catch (ParseException e) {
			fail();
		}	
	}
	
	@Test
	public void testParseDateDifferentOrder() {
		boolean passes = false;
		try {
			String ret = testTimetable.parseDate("08-12-2017");
			System.out.println("JUNIT: differentOrder" + ret);
		} catch (ParseException e) {
			//hacky fix to show that this passes.
			//TODO make a better way to test this.
			assertEquals("a", "a");
			passes = true;
		}
		if (!passes) {
			fail();
		}
	}
	
	@Test
	public void testParseDateUnknownDate() {
		boolean passes = false;
		try {
			String ret = testTimetable.parseDate("2017-02-30");
			System.out.println("JUNIT: testParseUnknown " + ret);
		} catch (ParseException e) {
			//hacky fix to show that this passes.
			//TODO make a better way to test this.
			assertEquals("a", "a");
			passes = true;
		}
		if (!passes) {
			fail();
		}
	}
	
	@Test
	public void testParseDateIncorrectFormat() {
		boolean passes = false;
		try {
			String ret = testTimetable.parseDate("31st Jan");
			System.out.println("JUNIT - testParseDateIncorrect: " + ret);
		} catch (ParseException e) {
			//hacky fix to show that this passes.
			//TODO make a better way to test this.
			assertEquals("a", "a");
			passes = true;
		}
		if (!passes) {
			fail();
		}
	}

}
