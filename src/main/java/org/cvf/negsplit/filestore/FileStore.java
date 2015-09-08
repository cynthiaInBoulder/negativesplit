package org.cvf.negsplit.filestore;

import java.io.InputStream;
import  com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@JsonIgnoreProperties({"contest"})
public class FileStore {
	
	private String fileName;
	private String fileSize;
	private String fileType;
	
	private InputStream content;

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileSize() {
		return fileSize;
	}

	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public InputStream getContent() {
		return content;
	}

	public void setContent(InputStream content) {
		this.content = content;
	}
	

}
