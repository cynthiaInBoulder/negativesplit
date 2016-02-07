package com.cvf.utils;

/*import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.Part;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.apache.tomcat.util.http.fileupload.FileUploadException;  */
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.cvf.model.FileStore;
/*
import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;*/





public class FileImportHandler  {

	private static final int THRESHOLD_SIZE = 1024 * 1024 * 3; // 3MB
	private static final int MAX_FILE_SIZE = 1024 * 1024 * 40; // 40MB
	private static final int MAX_REQUEST_SIZE = 1024 * 1024 * 50; // 50MB
	
	public static List<FileStore> uploadByApacheFileUpload(HttpServletRequest request) throws org.apache.commons.fileupload.FileUploadException{

		List<FileStore> files = new LinkedList<FileStore>();

		// Check to see if request contains a file to upload
		if (!org.apache.commons.fileupload.servlet.ServletFileUpload.isMultipartContent(request)) {
			System.out.println("No file selected to upload");
			return null;
		}
		// configure upload settings
				DiskFileItemFactory factory = new DiskFileItemFactory();
				factory.setSizeThreshold(THRESHOLD_SIZE);
				factory.setRepository(new File(System.getProperty("java.io.tmpdir")));

				ServletFileUpload upload = new ServletFileUpload(factory);
				upload.setFileSizeMax(MAX_FILE_SIZE);
				upload.setSizeMax(MAX_REQUEST_SIZE);
				
				FileStore temp = null;
					
				try{
					// get all files selected for upload
					List<FileItem> items = upload.parseRequest(request);
					
					for (FileItem item: items){
						// create file object
						temp = new FileStore();
						temp.setFileName(item.getName());
						temp.setContent(item.getInputStream());
						temp.setFileType(item.getContentType());
						temp.setFileSize(item.getSize()/1024 + "Kb");
						
						files.add(temp);	
						}
					}
					
				 catch (FileUploadException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
							
	 		return files;
	}

}
