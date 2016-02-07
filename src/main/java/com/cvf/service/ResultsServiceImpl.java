package com.cvf.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.datatype.XMLGregorianCalendar;

import org.joda.time.DateTime;
import org.joda.time.Duration;


import org.springframework.stereotype.Service;

import com.cvf.model.Results;
import com.cvf.model.trainingdb.Activity;
import com.cvf.model.trainingdb.Lap;
import com.cvf.model.trainingdb.Trackpoint;
import com.cvf.model.trainingdb.TrainingCenterDatabase;



@Service("resultsService")
public class ResultsServiceImpl implements ResultsService {

	private static final String POSITIVE = "Positive Split";
	private static final String NEGATIVE = "Negative Split";
	private static final String DISTANCE_EST_MSG = "*Using Distance Estimate - last gps trackpoint did not contain distance info";
	
	public List<Trackpoint> buildTrackpointList(TrainingCenterDatabase tcd) {
		List<Trackpoint> tpList =	new ArrayList<Trackpoint>();

		Activity laps = tcd.getActivities().getActivity();
		int totalNoLaps = tcd.getActivities().getActivity().getLap().size();
		extractTrackPointDataFromLaps(laps, totalNoLaps, tpList);
		
	    return tpList;    
	}
	
	public void extractTrackPointDataFromLaps(Activity laps, int totalNoLaps, List<Trackpoint> tpList) {
		int k = 0;

		for (int i = 0; i < totalNoLaps; i++) {
			Lap lap = laps.getLap().get(i);
			int noOfTps = lap.getTrack().getTrackpoint().size();
			List<Trackpoint> tempTpList = lap.getTrack().getTrackpoint();
			for (int j = 0; j < noOfTps; j++) {
				tpList.add(k, tempTpList.get(j));
				k = k + 1;
			}
		}
	}

	public int calculateMidPointDistanceElement(List<Trackpoint> tpList) {
		String cMP = null;
		int elementCount = 0;
		boolean scanUp = false;
		boolean scanDown = false;

		int approxHalfwayPoint = (tpList.size() - 1) / 2;

		// Current distance midpoint in TrackPoint data was calculated by taking
		// 1/2 the number of <TrackPoint> elements
		// Due to races not run on even splits we need to find the midpoint by
		// distance
		String currentMidPointContainsMetersData = (tpList
				.get(approxHalfwayPoint)).getDistanceMeters();

		// Convert to double in order to be able to compare to totalDistance
		// meters - do not want to compare Strings
		double currentMP = Double.valueOf(currentMidPointContainsMetersData);

		// Validate activity file contains a valid total distance meter value
		int	lastTrackpoint = validLastTrackpoint(tpList);

		// Calculate true midpoint distance
		double totalDistanceInMeters = Double.parseDouble(tpList.get(lastTrackpoint).getDistanceMeters());
		double validDistanceMidPoint = totalDistanceInMeters / 2;

		// Set boolean to determine whether to scan up or scan down for actual
		// mid point
		if (currentMP == validDistanceMidPoint){
			return elementCount = approxHalfwayPoint;
		}
		else if (currentMP > validDistanceMidPoint) {
			scanDown = true;
		} 
		else {
			scanUp = true;
		}

		// Start at approxHalfwayPoint and work you way down based on scan boolean
		for (int i = approxHalfwayPoint; i <= lastTrackpoint && i >= 0;) {
			// Break out of loop. When scan up and currentMP >= or scan down and
			// current <= that means the distancemeter value closest to the
			// validMidPoint has been found 
			if (currentMP >= validDistanceMidPoint && scanUp || currentMP <= validDistanceMidPoint && scanDown) {
				break;
			}
			if (scanUp)
				i++;
			if (scanDown)
				i--;

			cMP = tpList.get(i).getDistanceMeters();

			// Convert String to Double
			currentMP = Double.valueOf(cMP);
			elementCount = i;

		}

		return elementCount;

	} 
	
	public  int validLastTrackpoint(List<Trackpoint> tpList)
	{
		int lastTrackpoint = 0;
		int noOfTrackpoints = tpList.size() -1;
		
	    // Check to see if last trackpoint contains distanceMeters element
		// if not - go back one trackpoint until distanceMeters element is found
		for (int i = noOfTrackpoints; i > 0; i--){
			if (tpList.get(i).getDistanceMeters() != null){
				lastTrackpoint = i;
				break;
			}
		}
			
		return lastTrackpoint;
	}
	

	public Results calcualteResults(int midPointIndex, List<Trackpoint> tpList) {
		// Get index for the total distance meter value
				int indexLastElement = 	validLastTrackpoint(tpList);
			 
				XMLGregorianCalendar st = tpList.get(0).getTime();
				XMLGregorianCalendar mpt = tpList.get(midPointIndex).getTime();
				XMLGregorianCalendar et = tpList.get(indexLastElement).getTime();

				Date tmpStartDate = st.toGregorianCalendar().getTime();
				DateTime startTime = new DateTime(tmpStartDate);
				Date tmpMidDate = mpt.toGregorianCalendar().getTime();
				DateTime midPointTime = new DateTime(tmpMidDate);
				Date tmpEndDate = et.toGregorianCalendar().getTime();
				DateTime endTime = new DateTime(tmpEndDate);

				Duration firstHalf = new Duration(startTime, midPointTime);
				Duration lastHalf = new Duration(midPointTime, endTime);
				Duration totalDuration = new Duration(startTime, endTime);

				double metersTotalDistance = Double.valueOf(tpList.get(indexLastElement).getDistanceMeters());
				double metersFirstHalf = Double.valueOf(tpList.get(midPointIndex).getDistanceMeters());
				double metersLastHalf = (metersTotalDistance - metersFirstHalf);

				
				int result = compareDurationTimes(firstHalf, lastHalf);
				Results raceResult = new Results();

				raceResult.setTotalDistance(convertMetersToMiles(metersTotalDistance));
				raceResult.setTotalTime(convertMsToTime(totalDuration.getMillis()));
				raceResult.setTime1stHalf(convertMsToTime(firstHalf.getMillis()));
				raceResult.setDistance1stHalf(convertMetersToMiles(metersFirstHalf));
				raceResult.setDistance2ndHalf(convertMetersToMiles(metersLastHalf));
				raceResult.setTime2ndHalf(convertMsToTime(lastHalf.getMillis()));

				if (indexLastElement < (tpList.size() - 1)){
					raceResult.setEstDist(DISTANCE_EST_MSG);
				}
				
				int percent = 0;
				if (result < 0){
					raceResult.setResult(POSITIVE);
					long diff = firstHalf.minus(lastHalf).getMillis();
					raceResult.setSplit(convertMsToTime(diff));
					percent =  (int) (lastHalf.getMillis() * 100.0 / firstHalf.getMillis() + 0.5);	
				}
				else{
					raceResult.setResult(NEGATIVE); 
					long diff2 = firstHalf.minus(lastHalf).getMillis();
					raceResult.setSplit(convertMsToTime(diff2));
					percent =  (int) (lastHalf.getMillis() * 100.0 / firstHalf.getMillis() + 0.5);	
				}
			
				raceResult.setPercentage(Math.abs(100 - percent));
				
				return raceResult;
	}

	
	
	public static int compareDurationTimes(Duration firstHalf, Duration lastHalf) {
		return firstHalf.compareTo(lastHalf);
	}

	public static double round(double value, int places) {
		if (places < 0)
			throw new IllegalArgumentException();

		BigDecimal bd = new BigDecimal(value);
		bd = bd.setScale(places, RoundingMode.HALF_UP);
		return bd.doubleValue();
	}

	public static double convertMetersToMiles(double meters) {

		double miles = (meters / 1609.344);
		return (round(miles, 2));
	}


	public static String convertMsToTime(Long millisToCalculate) {

		StringBuilder formattedTime = new StringBuilder();
		long seconds, minutes, hours;

		seconds = Math.abs(millisToCalculate) / 1000;
		minutes = seconds / 60;
		seconds = seconds % 60;
		hours = minutes / 60;
		minutes = minutes % 60;

		int min = (int) minutes;
		int sec = (int) seconds;

		DecimalFormat formatter = new DecimalFormat("00");
		formattedTime.append(hours);
		formattedTime.append(":");
		formattedTime.append(formatter.format(min));
		formattedTime.append(":");
		formattedTime.append(formatter.format(sec));

		return formattedTime.toString();
	}

}
