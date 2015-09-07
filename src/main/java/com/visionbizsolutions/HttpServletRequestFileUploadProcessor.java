package com.visionbizsolutions;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.FileCopyUtils;

import com.visionbizsolutions.mvc.models.FileMeta;
import com.visionbizsolutions.orm.jpa.bean.User;
import com.visionbizsolutions.orm.jpa.service.UserService;

public class HttpServletRequestFileUploadProcessor implements
		FileUploadProcessor {
	private static final Logger logger = LoggerFactory
			.getLogger(HttpServletRequestFileUploadProcessor.class);
	
	@SuppressWarnings("unchecked")
	@Override
	public LinkedList<FileMeta> processUploadedFile(HttpServletRequest request,
			Long fileSizeAllowed, String dirPath, UserService userService) throws Exception {
		
		LinkedList<FileMeta> files = new LinkedList<FileMeta>();
		
		ServletFileUpload fileUpload = new ServletFileUpload(new DiskFileItemFactory());
		List<FileItem> fileItems = fileUpload.parseRequest(request);
		
		// 1. build an iterator
		Iterator<FileItem> mitr = fileItems.iterator();

		// 2. get each file
		while (mitr.hasNext()) {
			boolean isValidFileSize = true;
			boolean isValidFileType = true;
			boolean isFileSavedToDir = true;
			boolean isUserAuthenticated = true;
			boolean isFileInfoSavedToDB = true;

			String _fileUploadStatusMessage = null;
			FileMeta fileMeta = new FileMeta();

			// FileItem item = itr.next();
			FileItem item = mitr.next();
			logger.debug("Uploading file: " + item.getName());
			/* , Integer fileSizeAllowed, String dirPath */

			if (Utils.isValidFileType(item.getContentType())) {
				if (item.getSize() <= fileSizeAllowed) {

					// 2.2 Populate FileMeta object
					fileMeta.setFileName(item.getName());
					fileMeta.setFileSize(item.getSize() / 1024 + " Kb");
					fileMeta.setFileType(item.getContentType());

					File file = new File(dirPath + fileMeta.getFileName());

					try {
						// 2.3 save file in directory
						FileCopyUtils.copy(item.getInputStream(),
								new FileOutputStream(file));
					} catch (IOException e) {
						e.printStackTrace();
						isFileSavedToDir = false;
						_fileUploadStatusMessage = e.getLocalizedMessage();
						logger.debug(_fileUploadStatusMessage);
					}

					if (isFileSavedToDir) {
						// File saving has some problems
						logger.debug(file + " uploaded! ");

						// 2.4 save file info in database
						Authentication auth = SecurityContextHolder
								.getContext().getAuthentication();
						logger.debug("Getting User from Context !!");
						User user = null;
						if (auth instanceof UsernamePasswordAuthenticationToken) {
							UserDetails userDetail = (UserDetails) auth
									.getPrincipal();
							String username = userDetail.getUsername();

							logger.info("Got username for saving file info. which is "
									+ username);
							user = userService.getUserByUsername(username);
						}

						if (user != null) {
							logger.debug("Got user with username: "
									+ user.getUsername());
							try {
								// TODO: check before adding info to DB that the
								// same file name was uploaded then
								// Step1: delete the file from directory
								// Step2: Get current time in long
								// Step3: Save file again with
								// filename+current-time_stamp
								// Step4: Then save file info in Database
								userService.saveFile(user, fileMeta
										.getFileName(), item.getSize(), Utils
										.getShortFileType(fileMeta
												.getFileType())); // converting
																	// long
																	// file_type
																	// to short
																	// one
								_fileUploadStatusMessage = fileMeta
										.getFileName()
										+ " is uploaded successfully. Relavent person will look into this,"
										+ " and you will be notified, if more information is needed.";
								logger.debug("File information saved into database.");
								logger.debug("Congratulations! File Saved completely.");
							} catch (Exception e) {																
								isFileInfoSavedToDB = false;
								_fileUploadStatusMessage = "Sorry! "
										+ fileMeta.getFileName()
										+ " is not uploaded. There was an error in saving file info. in Database."
										+ " Please try again later.";
								logger.error("File information is not saved into database "
										+ System.getProperty("line.separator")
										+ Utils.getStackTrace(e.fillInStackTrace()));
								// Deleting file because its information is not
								// saved in database.
								file.delete();
							}
						}// user is found and authenticated to upload files
						else {
							isUserAuthenticated = false;
							_fileUploadStatusMessage = "Sorry! "
									+ fileMeta.getFileName()
									+ " is not uploaded. Either you are not logged-in OR"
									+ "your session is expried. Please re-login to upload your file.";

							logger.error("User is authenticated!");
							// Deleting file because user is not logged-in or
							// his
							// session is expired.
							file.delete();
						}
					}// isFileUploaded if()
					else {
						_fileUploadStatusMessage = "Sorry! "
								+ fileMeta.getFileName()
								+ " is not uploaded. There was an error in uploading, which is as: "
								+ _fileUploadStatusMessage;
						logger.error("File is not saved into the specified directory "
								+ dirPath);
					}
				}// isValidFileSize
				else {
					isValidFileSize = false;
					_fileUploadStatusMessage = "Sorry! "
							+ item.getName()
							+ " is not uploaded. This file size allowed is only "
							+ (fileSizeAllowed / 1000000)
							+ " MB. Please try again with smaller size file.";
					logger.debug("File size " + item.getSize()
							+ " is greater then the limit "
							+ (fileSizeAllowed / 1000000) + " MB.");
				}
			}// isValidFileType if()
			else {
				isValidFileType = false;
				_fileUploadStatusMessage = item.getName()
						+ " is not uploaded. This file type is not allowed. Please upload"
						+ " the files from mentioned file types only. Please try again with other file.";
				logger.debug("File is not of a valid type "
						+ item.getContentType());
			}

			// 2.5 add to files
			if (isValidFileSize && isValidFileType && isFileSavedToDir
					&& isUserAuthenticated && isFileInfoSavedToDB) {
				fileMeta.setFileName(_fileUploadStatusMessage);
			} else {
				fileMeta.setFileName("");
				fileMeta.setFileType(_fileUploadStatusMessage);
			}
			logger.debug("Adding file objects for providing status.....");
			files.add(fileMeta);
		}// while loop
		
		return files;

	}

}
