package main;

import static org.junit.Assert.*;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextPane;
import javax.swing.table.TableModel;

import org.junit.Test;

/**
 * 
 * @author FlashCloud
 * Class for testing the main functionality of the program. Tests include search queries and usability functions.
 */
public class MainTest {
	JTable testTable   = new JTable();
	JTextPane testPane = new JTextPane();
	Main testMain      = new Main();

	/**
	 * Tests bus search from locks way road to fratton station at 12:00
	 */
	@Test
	public void testSearchNormal() {
		testMain.search("Locks Way Road", "Fratton Station", "12:00", false, testTable);
		TableModel results = testTable.getModel();
		int numRows = results.getRowCount();
		String latestTime = (String) results.getValueAt(numRows-1, 1);
		String hour = latestTime.split(":")[0];
		String minutes = latestTime.split(":")[1];
		int intHour = Integer.parseInt(hour);
		int intMinute = Integer.parseInt(minutes);
		if (intHour < 12) { //looks bad
			assertTrue(intHour < 12);
		} else if (intHour == 12) { 
		//because we're checking that the times are less than or equal to
		//the current time, we need to ensure that if the hours are the same, the minutes are
			assertTrue(intMinute == 0);
		} else {
			fail();
		}
	}
	
	/**
	 * Tests bus search from the same bus stop (Lidl)
	 */
	public void testSearchEqualSStops() {
		testMain.search("Lidl", "Lidl", "12:30", false, testTable);
		TableModel results = testTable.getModel();
		assertEquals(0, results.getRowCount());
	}
	
	@Test
	public void testSearchWeirdDateFormatting() {
		testMain.search("Locks Way Road",  "Fratton Station", "12-30", false, testTable);
		TableModel results = testTable.getModel();
		assertEquals(0, results.getRowCount());
	}
	
	@Test
	public void testSearchWrongTime() {
		testMain.search("Locks Way Road",  "Fratton Station", "Half 12", false, testTable);
		TableModel results = testTable.getModel();
		assertEquals(0, results.getRowCount());
	}
	
	// konrad please fix me	
	@Test
	public void testGetOrderedPopularRoutes() throws SQLException {
		//fist, connect to the database and get the most popular routes
		DatabaseConnection dbconn = new DatabaseConnection();
		dbconn.connect();
//		ResultSet results = dbconn.runQuery("Select distinct Route_Name, max(Route_Count) from Popular natural join Route");
		
	}
	
	@Test
	public void testChangeFontSizeIncrease() {
		JLabel lblTest1 = new JLabel();
		JLabel lblTest2 = new JLabel();
		int lbltest1fontSizeBefore = lblTest1.getFont().getSize();
		int lbltest2fontSizeBefore = lblTest2.getFont().getSize();
		JComponent[] testList = { lblTest1, lblTest2 };
		testMain.changeFontSize(true, testList);
		assertEquals(lbltest1fontSizeBefore + 5, testList[0].getFont().getSize());
		assertEquals(lbltest2fontSizeBefore + 5, testList[1].getFont().getSize());	
	}
	
	@Test
	public void testChangeFontSizeDecrease() {
		JLabel lblTest1 = new JLabel();
		JLabel lblTest2 = new JLabel();
		int lbltest1fontSizeBefore = lblTest1.getFont().getSize();
		int lbltest2fontSizeBefore = lblTest2.getFont().getSize();
		JComponent[] testList = { lblTest1, lblTest2 };
		testMain.changeFontSize(false, testList);
		assertEquals(lbltest1fontSizeBefore - 5, testList[0].getFont().getSize());
		assertEquals(lbltest2fontSizeBefore - 5, testList[1].getFont().getSize());
	}
	
	@Test
	public void testChangeFontSizeTriplePress() {
		JLabel lblTest1 = new JLabel();
		JLabel lblTest2 = new JLabel();
		int lbltest1fontSizeBefore = lblTest1.getFont().getSize();
		int lbltest2fontSizeBefore = lblTest2.getFont().getSize();
		JComponent[] testList = { lblTest1, lblTest2 };
		testMain.changeFontSize(true, testList);
		testMain.changeFontSize(false, testList);
		testMain.changeFontSize(true, testList);
		assertEquals(lbltest1fontSizeBefore + 5, testList[0].getFont().getSize());
		assertEquals(lbltest2fontSizeBefore + 5, testList[1].getFont().getSize());
	}

}
