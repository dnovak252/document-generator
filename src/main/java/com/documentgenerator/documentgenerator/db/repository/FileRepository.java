package com.documentgenerator.documentgenerator.db.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.documentgenerator.documentgenerator.db.entity.File;

public interface FileRepository extends MongoRepository<File, String>{
	
}
