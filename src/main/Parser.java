/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


/**
 *
 * @author hjf
 */
public class Parser {
    
        /**
         * 
         * @param args Not used
         * 
         * Main: Searches for the new file, ex.json CHANGEME and splits in to arrays.
         * Then transfers in to query Stings, stored in a global arraylist
         * Should then iterate through the arraylist and update the database.
         * 
         */
        public static void main(String[] args) {
            
            JSONParser parser = new JSONParser();
            try {
                //attempt to read main file
                Object obj                = parser.parse(new FileReader("ex.json"));
                JSONObject newInfo        = (JSONObject) obj;
                // all items in the json file are stored in to their jsonarray counterparts
                //until they're looped and turned in to queries
                JSONArray newRoutes       = (JSONArray) newInfo.get("route");
                JSONArray newStops        = (JSONArray) newInfo.get("stop");
                JSONArray newArrivalTimes = (JSONArray) newInfo.get("arrival_times");
                JSONArray newArrivalStops = (JSONArray) newInfo.get("arrival_stop");
                
                //create iterators so that we may loop through all of the information
                //as we create queries
                Iterator<JSONObject> routeIter   = newRoutes.iterator();
                Iterator<JSONObject> stopIter    = newStops.iterator();
                Iterator<JSONObject> timeIter    = newArrivalTimes.iterator();
                Iterator<JSONObject> arrivalIter = newArrivalStops.iterator();
                
                while (routeIter.hasNext()) {
                    JSONObject curRoute = routeIter.next();
                    String routeName = "\""+(String)curRoute.get("name")+"\"";
                    createQuery("ROUTE", "Route_Name", routeName);
                }
                
                while (stopIter.hasNext()) {
                    JSONObject curStop = stopIter.next();
                    String stopName = "\""+(String)curStop.get("name")+"\"";
                    createQuery("Stop", "Stop_Name", stopName);
                }
                
                while (timeIter.hasNext()) {
                    JSONObject curTime = timeIter.next();
                    String time = "\"" + (String)curTime.get("time") + "\"";
                    String stopID = Long.toString((Long)curTime.get("stop_id"));
                    createQuery("Arrival_Times", "Arrival_Time", time, "Stop_ID", stopID);
                }
                
                while (arrivalIter.hasNext()) {
                    JSONObject curArrival = arrivalIter.next();
                    String routeID = Long.toString((Long)curArrival.get("route_id"));
                    String arrivalID = Long.toString((Long)curArrival.get("arrival_id"));
                    createQuery("Arrival_Stop", "Route_ID", routeID, "Arrival_ID", arrivalID);
                }
            } catch (FileNotFoundException e) {
            } catch (IOException | ParseException e) {
                //need to change these to message boxes.
                System.out.println("There was an issue with parsing the file!");
                System.out.println("Either it doesn't exist, or there is a syntax error in the JSON file");
                System.out.println(e);
            }

    }
        
    /**
     * 
     * @param table The table to insert to
     * @param row The name of the row that the value is to be inserted to
     * @param value The value to be inserted
     * 
     * createQuery, allows us to create a query with one row. 
     * Ensures that with only one row, the larger function will produce a string with one row
     * Calls createQuery with the afformentioned arguments and strings "empty" for the other arguments
     * 
     */
    private static void createQuery(String table, String row, String value) {
        createQuery(table, row, value, "empty", "empty");
    }
        
    /**
     * 
     * @param table The table to insert to
     * @param row1 The first row to insert to
     * @param value1 The value for the first row in the table
     * @param row2 The second row in the table to insert to
     * @param value2 The value for the second row
     * 
     * Takes the afformentioned parameters and transforms them in to an INSERT query
     * Checks to see if the second row is empty and created a query with one or two rows
     * 
     */
    private static void createQuery(String table, String row1, String value1, String row2, String value2) {
        String qryStart = "INSERT INTO " + table + " (" + row1;
        String qryValues = ") VALUES (" + value1;
        String qryEnd = ");";
        if (row2 != "empty") {
            qryStart += ", " + row2;
            qryValues += ", " + value2;
        }
        String query = qryStart + qryValues + "" + qryEnd;
        System.out.println(query);
    }
 
}