package com.cvf.service;

import java.util.List;

import com.cvf.model.Results;
import com.cvf.model.trainingdb.Trackpoint;
import com.cvf.model.trainingdb.TrainingCenterDatabase;


public interface ResultsService {

	List<Trackpoint>  buildTrackpointList(TrainingCenterDatabase tcd); 
	
	int  calculateMidPointDistanceElement(List<Trackpoint> tpList);
	
	Results calcualteResults(int actualMidPoint, List<Trackpoint> tpList);
}