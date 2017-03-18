package main;

import static org.junit.Assert.*;

import javax.swing.JComboBox;
import javax.swing.table.DefaultTableModel;

import org.junit.*;

@SuppressWarnings({ "rawtypes", "static-access" })
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
		testCmbRoutes.setSelectedIndex(1);
		assertNotSame("101", testTimetable.thisStop);
	}

	@Test
	public void testGetWeekTwoYears() {
		// TODO
	}
	
	@Test
	public void testGetWeekManyYearsAgo() {
		// TODO
	}
	
	@Test
	public void testGetWeekIncorrectParse() {
		// TODO
	}
	
	@Test
	public void testGetWeekNonExistantDate() {
		// TODO
	}

	@Test
	public void testParseDate() {
		// TODO
	}

}
