package com.visionbizsolutions;

import java.util.LinkedList;

import javax.servlet.http.HttpServletRequest;

import com.visionbizsolutions.mvc.models.FileMeta;
import com.visionbizsolutions.orm.jpa.service.UserService;

public interface FileUploadProcessor {
	
	public LinkedList<FileMeta> processUploadedFile(HttpServletRequest request, Long fileSizeAllowed, String dirPath, UserService userService) throws Exception;

}
