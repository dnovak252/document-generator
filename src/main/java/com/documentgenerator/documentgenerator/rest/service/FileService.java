package com.documentgenerator.documentgenerator.rest.service;

import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.documentgenerator.documentgenerator.db.entity.File;
import com.documentgenerator.documentgenerator.db.repository.FileRepository;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.client.gridfs.model.GridFSFile;

@Service
public class FileService {
	
	@Autowired
	private GridFsTemplate template;
	
	@Autowired
    private GridFsOperations operations;
	
	@Autowired
    private FileRepository fileRepository;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(FileService.class);
	
	public void generateFile(MultipartFile file) throws IOException {

		String content = new String(file.getBytes());
		
		XWPFDocument document = new XWPFDocument();
		
		XWPFParagraph title = document.createParagraph();
		title.setAlignment(ParagraphAlignment.CENTER);
		
		XWPFRun titleRun = title.createRun();
		titleRun.setText("Text generated from .txt file");
		titleRun.setColor("000000");
		titleRun.setBold(true);
		titleRun.setFontSize(20);
		
		XWPFParagraph text = document.createParagraph();
		text.setAlignment(ParagraphAlignment.BOTH);
		XWPFRun textRun = text.createRun();
		textRun.setText(content);
		
		String fileName = file.getOriginalFilename();
		
		String output = fileName.substring(0, fileName.lastIndexOf(".")) + ".docx";
		
		FileOutputStream out = new FileOutputStream(output);
		document.write(out);
		out.close();
		document.close();
	}
	
	public String uploadFile(MultipartFile file) throws IOException { 
		 DBObject metadata = new BasicDBObject();
		 metadata.put("fileSize", file.getSize());
	     Object fileID = template.store(file.getInputStream(), file.getOriginalFilename(), file.getContentType(), metadata);
	     return "ID: " + fileID.toString();
    }

    public File downloadFile(String id) throws IllegalStateException, IOException { 
        GridFSFile gridFSFile = template.findOne( new Query(Criteria.where("_id").is(id)) );
        
        File file = new File();
        
        if (gridFSFile != null && gridFSFile.getMetadata() != null) {
        	file.setFileName( gridFSFile.getFilename() );

        	file.setFileType( gridFSFile.getMetadata().get("_contentType").toString() );

        	file.setFileSize( gridFSFile.getMetadata().get("fileSize").toString() );

        	file.setFile( IOUtils.toByteArray(operations.getResource(gridFSFile).getInputStream()) );
        }

        return file;
    }
}

