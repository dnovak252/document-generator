package com.documentgenerator.documentgenerator;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.documentgenerator.documentgenerator.rest.service.FileService;

@SpringBootTest
@AutoConfigureMockMvc
public class FileGenerateTest {
	@MockBean  private FileService fileService;
	
	@Autowired
	private WebApplicationContext webApplicationContext;
	
	
	@Autowired 
	MockMvc mockMvc;
	
	@Test
	public void whenFileUploaded_thenVerifyStatus() throws Exception {
	    MockMultipartFile file 
	      = new MockMultipartFile(
	        "file", 
	        "hello.txt", 
	        MediaType.TEXT_PLAIN_VALUE, 
	        "Hello, World!".getBytes()
	      );

	    MockMvc mockMvc 
	      = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	    mockMvc.perform(multipart("/upload").file(file))
	      .andExpect(status().isOk());
	}
	
	@Test
	public void whenFileUploaded_thenNotOk() throws Exception {
		MockMultipartHttpServletRequestBuilder multipartRequest = MockMvcRequestBuilders.multipart("/upload");
		mockMvc.perform(multipartRequest).andExpect(status().isBadRequest());
	}
	
	
}
