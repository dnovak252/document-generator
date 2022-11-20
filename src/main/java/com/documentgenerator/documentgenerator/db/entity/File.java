package com.documentgenerator.documentgenerator.db.entity;

import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class File {
	
	@Id
	private String id;
	
	@Field
	private String fileName;
	
	@Field
    private String fileType;
	
	@Field
    private String fileSize;
	
	@Field
    private byte[] file;
	
	public File() {
		super();
	}

	public String getId() {
		return id;
	}

	private void setId(String id) {
		this.id = id;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String filename) {
		this.fileName = filename;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	private String getFileSize() {
		return fileSize;
	}

	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}

	public byte[] getFile() {
		return file;
	}

	public void setFile(byte[] file) {
		this.file = file;
	}


	@Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                "filename=" + fileName +
                "fileType=" + fileType +
                "fileSize=" + fileSize +
                '}';
    }
}