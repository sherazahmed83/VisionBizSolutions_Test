package com.visionbizsolutions.mvc.models;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties({"bytes"})
public class FileMeta {

	private String fileName = null;
	private String fileSize = null;
	private String fileType = null;
	private boolean isFileUploaded = false;
	private boolean isFileTypeAllowed = false;
	
	private byte[] bytes;
	
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
	public byte[] getBytes() {
		return bytes;
	}
	public void setBytes(byte[] bytes) {
		this.bytes = bytes;
	}
	public boolean isFileUploaded() {
		return isFileUploaded;
	}
	public void setFileUploaded(boolean isFileUploaded) {
		this.isFileUploaded = isFileUploaded;
	}
	public boolean isFileTypeAllowed() {
		return isFileTypeAllowed;
	}
	public void setFileTypeAllowed(boolean isFileTypeAllowed) {
		this.isFileTypeAllowed = isFileTypeAllowed;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("FileMeta [fileName=");
		builder.append(fileName);
		builder.append(", fileSize=");
		builder.append(fileSize);
		builder.append(", fileType=");
		builder.append(fileType);
		builder.append(", isFileUploaded=");
		builder.append(isFileUploaded);
		builder.append(", isFileTypeAllowed=");
		builder.append(isFileTypeAllowed);
		builder.append("]");
		return builder.toString();
	}
}