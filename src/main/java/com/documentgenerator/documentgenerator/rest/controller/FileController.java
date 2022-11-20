package com.documentgenerator.documentgenerator.rest.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.documentgenerator.documentgenerator.db.entity.File;
import com.documentgenerator.documentgenerator.rest.service.FileService;

@RestController
public class FileController {
	
	 @Autowired
	 private FileService fileService;
	 
	 @PostMapping("/generate")
	 public void createNewObjectWithImage(@RequestParam(value = "file", required = false) MultipartFile file) throws IOException {
		 fileService.generateFile(file);
	 }
	 
	 @PostMapping("/upload")
	 public ResponseEntity<?> upload(@RequestParam("file")MultipartFile file) throws IOException {
	     return new ResponseEntity<>(fileService.uploadFile(file), HttpStatus.OK);
	 }
	 
	 @GetMapping("/download/{id}")
	 public ResponseEntity<ByteArrayResource> download(@PathVariable String id) throws IOException {
		 File file = fileService.downloadFile(id);
		
		 return ResponseEntity.ok()
				 .contentType(MediaType.parseMediaType(file.getFileType() ))
		         .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFileName() + "\"")
		         .body(new ByteArrayResource(file.getFile()));
	 }

}
