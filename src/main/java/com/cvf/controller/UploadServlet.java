package com.cvf.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileUploadException;

import com.cvf.model.FileStore;
import com.cvf.model.Results;
import com.cvf.model.trainingdb.Trackpoint;
import com.cvf.model.trainingdb.TrainingCenterDatabase;
import com.cvf.service.ResultsService;
import com.cvf.service.ResultsServiceImpl;
import com.cvf.service.UnmarshallService;
import com.cvf.service.UnmarshallServiceImpl;
import com.cvf.utils.FileImportHandler;

/**
 * Servlet implementation class UploadServlet
 */
public class UploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final String INVALID_FILE_TYPE = " file must be tcx or gpx file";
	private static final String INVALID_FILE = "<strong> Input file is missing required elements per Garmin's schema.  <br></br>Please use different file</a></strong>";
	private static final String NO_VALID_DISTANCE = "<strong> file does not have a valid trackpoint for total distance <br></br> unable to calculate negative split</strong>";
	
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
				ResultsService resultsService = new ResultsServiceImpl();
				UnmarshallService unmarshallService = new UnmarshallServiceImpl();
				
				// Call service to load data file into objects
				TrainingCenterDatabase tcd = unmarshallService.unmarshallFile(xmlFile);
				
				// Trackpoints are embedded in Lap objects - extract all TrackPoints to separate List
				List<Trackpoint> tpList = resultsService.buildTrackpointList(tcd);

				// Determine actual midpoint by distance
				int actualMidPoint = resultsService.calculateMidPointDistanceElement(tpList);

				// Check for valid midpoint
				if (actualMidPoint > 0){
					// Return Race Results Object to jsp		
					Results resultsData =  resultsService.calcualteResults(actualMidPoint, tpList);
					request.setAttribute("resultsData", resultsData );
					request.getRequestDispatcher("/result.jsp").forward(request, response);
				}
				else{
					request.setAttribute("message", NO_VALID_DISTANCE);
					request.getRequestDispatcher("/error.jsp").forward(request, response);
				}
			
			} else {
				request.setAttribute("message", filePath + INVALID_FILE_TYPE);
				request.getRequestDispatcher("/error.jsp").forward(request, response);
				}
			}
		}
		catch (Exception e) {
			
		String errorString = e.getMessage();
	
	     if (errorString == null || errorString.contains("null") ||errorString.contains("empty String")){
			request.setAttribute("message", INVALID_FILE);
	     }
	     request.getRequestDispatcher("/error.jsp").forward(request, response);
		}
		
		finally{
			//  Clear list of imported files after response sent to browser
			files.clear();
		}

	}
}
