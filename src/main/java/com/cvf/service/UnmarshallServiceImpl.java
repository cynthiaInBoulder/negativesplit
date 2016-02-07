package com.cvf.service;

import java.io.InputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import com.cvf.model.trainingdb.TrainingCenterDatabase;

public class UnmarshallServiceImpl implements UnmarshallService {

	public TrainingCenterDatabase unmarshallFile(InputStream xmlFile) {

		// Load data file into objects
		JAXBContext jaxbContext;
		TrainingCenterDatabase tcd = null;
		
		try {
			jaxbContext = JAXBContext.newInstance(TrainingCenterDatabase.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			tcd = (TrainingCenterDatabase) jaxbUnmarshaller.unmarshal(xmlFile);
		} catch (JAXBException e) {
			
			e.printStackTrace();
		}

		return tcd;
	}

}
