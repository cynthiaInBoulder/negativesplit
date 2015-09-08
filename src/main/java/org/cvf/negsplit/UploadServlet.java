package org.cvf.negsplit;

import garmin.xmlschemas.trainingcenterdatabase.v2.Activity;
import garmin.xmlschemas.trainingcenterdatabase.v2.Lap;
import garmin.xmlschemas.trainingcenterdatabase.v2.Trackpoint;
import garmin.xmlschemas.trainingcenterdatabase.v2.TrainingCenterDatabase;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.commons.fileupload.FileUploadException;
import org.cvf.negsplit.filestore.FileStore;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.Interval;
import org.w3c.dom.Node;

/**
 * Servlet implementation class UploadServlet
 */
public class UploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	// this will store the uploaded files
	private static List<FileStore> files = new LinkedList<FileStore>();

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


		// Load all files in FileObjects
		try {
			files.addAll(FileImportHandler.uploadByApacheFileUpload(request));
		} catch (FileUploadException e1) {
		
			e1.printStackTrace();
		}

		try {

		int noOfFiles = files.size();
		for (int i = 0; i < noOfFiles; i++) {

			String filePath = (files.get(i).getFileName()).toLowerCase();
		
			// Verify valid file type
			if (filePath.endsWith("tcx") || filePath.endsWith("gpx")) {
				InputStream xmlFile = files.get(i).getContent();
				
				// Load data file into objects
				JAXBContext jaxbContext = JAXBContext.newInstance(TrainingCenterDatabase.class);
				Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
				TrainingCenterDatabase tcd = (TrainingCenterDatabase) jaxbUnmarshaller.unmarshal(xmlFile);

				// Trackpoints are embedded in Lap objects - extract all TrackPoints to separate List
				List<Trackpoint> tpList = new ArrayList<Trackpoint>();

				Activity laps = tcd.getActivities().getActivity();
				int totalNoLaps = tcd.getActivities().getActivity().getLap().size();
				extractTrackPointsFromLaps(laps, totalNoLaps, tpList);

				// Determine actual midpoint by distance
				int actualMidPoint = calculateMidPointDistanceElement(tpList);

				// Return Race Results Object to jsp
				Results resultsData =  calcualteResults(actualMidPoint, tpList);
			
				request.setAttribute("resultsData", resultsData );
				request.getRequestDispatcher("/result.jsp").forward(request, response);
			
			} else {
				request.setAttribute("message", filePath + " file must be tcx or gpx file");
				request.getRequestDispatcher("/error.jsp").forward(request, response);
				}
			}
		
			//  Clear list of imported files after response sent to browser
			files.clear();
		}
		catch (Exception e) {
			request.setAttribute("message", " fatal error  - process aborted");
			request.getRequestDispatcher("/error.jsp").forward(request, response);
			e.printStackTrace();
		}

		
	

	}

	public static void extractTrackPointsFromLaps(Activity laps, int totalNoLaps, List<Trackpoint> tpList) {
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

	public static int calculateMidPointDistanceElement(List<Trackpoint> tpList) {

		String cMP = null;
		int elementCount = 0;
		boolean scanUp = false;
		boolean scanDown = false;

		int noOfTrackpoints = tpList.size();
		int approxHalfwayPoint = noOfTrackpoints / 2;

		// Current distance midpoint in TrackPoint data was calculated by taking
		// 1/2 the number of <TrackPoint> elements
		// Due to races not run on even splits we need to find the midpoint by
		// distance

		String currentMidPointContainsMetersData = (tpList
				.get(approxHalfwayPoint)).getDistanceMeters();

		// Convert to double in order to be able to compare to totalDistance
		// meters - do not want to compare Strings
		double currentMP = Double.valueOf(currentMidPointContainsMetersData);

		// Calculate true midpoint distance
		double totalDistanceInMeters = Double.parseDouble(tpList.get(
				noOfTrackpoints - 1).getDistanceMeters());
		double validDistanceMidPoint = totalDistanceInMeters / 2;

		// Set boolean to determine whether to scan up or scan down for actual
		// mid point
		if (currentMP > validDistanceMidPoint) {
			scanDown = true;
		} else if (currentMP < validDistanceMidPoint) {
			scanUp = true;
		}

		// TODO - can this be made into a more efficient search
		for (int i = approxHalfwayPoint; i <= noOfTrackpoints;) {
			// Break out of loop. When scan up and currentMP >= or scan down and
			// current <= that means the distancemeter value closest to the
			// validMidPoint has been found
			if (currentMP >= validDistanceMidPoint && scanUp
					|| currentMP <= validDistanceMidPoint && scanDown) {
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
	
	public static Results calcualteResults(int midPointIndex, List<Trackpoint> tpList) {

		XMLGregorianCalendar st = tpList.get(0).getTime();
		XMLGregorianCalendar mpt = tpList.get(midPointIndex).getTime();
		int indexLastElement = tpList.size() - 1;
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

		int percentage = 0;
		int result = compareDurationTimes(firstHalf, lastHalf);
		Results raceResult = new Results();
		String totalDurationTime = convertMsToTime(totalDuration.getMillis());
		String firstHalfTime = convertMsToTime(firstHalf.getMillis());
		String lastHalfTime = convertMsToTime(lastHalf.getMillis());
		
	

		raceResult.setTotalDistance(convertMetersToMiles(metersTotalDistance));
		raceResult.setTotalTime(convertMsToTime(totalDuration.getMillis()));
		raceResult.setTime1stHalf(firstHalfTime = convertMsToTime(firstHalf.getMillis()));
		raceResult.setDistance1stHalf(convertMetersToMiles(metersFirstHalf));
		raceResult.setDistance2ndHalf(convertMetersToMiles(metersLastHalf));
		raceResult.setTime2ndHalf(lastHalfTime = convertMsToTime(lastHalf.getMillis()));
		
		int percent = 0;
		if (result < 0){
			raceResult.setResult("Positive Split");
			long diff = firstHalf.minus(lastHalf).getMillis();
			raceResult.setSplit(convertMsToTime(diff));
			percent =  (int) (lastHalf.getMillis() * 100.0 / firstHalf.getMillis() + 0.5);	
		}
		else{
			raceResult.setResult("Negative Split");
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
