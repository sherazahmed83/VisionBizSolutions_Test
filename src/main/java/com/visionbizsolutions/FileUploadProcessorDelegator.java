package com.visionbizsolutions;

import java.util.LinkedList;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.visionbizsolutions.mvc.models.FileMeta;
import com.visionbizsolutions.orm.jpa.service.UserService;

public class FileUploadProcessorDelegator implements FileUploadProcessor {
	
	private String fileuploadRequestType = null;
	public static final String TYPE_HTTP_SERVLET_REQUEST = "http_servlet_request";
	public static final String TYPE_MULTIPART_HTTP_SERVLET_REQUEST = "multipart_http_servlet_request";
	
	private static final Logger logger = LoggerFactory
			.getLogger(FileUploadProcessorDelegator.class);
	
	private FileUploadProcessor processor = null;

	public String getFileuploadRequestType() {
		return fileuploadRequestType;
	}

	public void setFileuploadRequestType(String fileuploadRequestType) {
		this.fileuploadRequestType = fileuploadRequestType;
	}
	
	public FileUploadProcessorDelegator() {
		this(TYPE_HTTP_SERVLET_REQUEST);
	}
	
	public FileUploadProcessorDelegator(String fileUploadRequestType) {
		this.fileuploadRequestType = fileUploadRequestType;
		createObject();
	}
	
	public void createObject(){
		if (fileuploadRequestType == null) {
			logger.error("No request type is set");
			return;
		}
		
		if (fileuploadRequestType.equals(TYPE_HTTP_SERVLET_REQUEST)) {
			processor = new HttpServletRequestFileUploadProcessor();
		} else if (fileuploadRequestType.equals(TYPE_MULTIPART_HTTP_SERVLET_REQUEST)) {
			processor = new MultipartHttpServletRequestFileUploadProcessor();
		}
	}

	@Override
	public LinkedList<FileMeta> processUploadedFile(
			HttpServletRequest request, Long fileSizeAllowed, String dirPath, UserService userService) throws Exception {
		
		if (processor == null) {
			logger.error("No FileUpload Processor instance found");
			
			FileMeta meta = new FileMeta();
			meta.setFileName("");
			meta.setFileType("We applogies for inconvenience, "
					+ "there is some error at the server-side. Please try again after some time. ");
			
			LinkedList<FileMeta> files = new LinkedList<FileMeta>();
			files.add(meta);
			return files;
		}
		
		return processor.processUploadedFile(request, fileSizeAllowed, dirPath, userService);
	}
	
	

}
