package com.cvf.service;

import java.io.InputStream;

import com.cvf.model.trainingdb.TrainingCenterDatabase;

public interface UnmarshallService {

	TrainingCenterDatabase unmarshallFile(InputStream xmlFile);
}
