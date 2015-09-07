package com.visionbizsolutions.orm.jpa.bean;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "USER_FILES")
public class UserFile implements Serializable {

	private static final long serialVersionUID = 5851896435025698344L;
	
	private Integer Id = null;
	private String fileName = null;  
	private String fileType = null;
	private Long fileSize = null;
	private Date created;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Integer getId() {
		return Id;
	}
	public void setId(Integer id) {
		Id = id;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	public Long getFileSize() {
		return fileSize;
	}
	public void setFileSize(Long fileSize) {
		this.fileSize = fileSize;
	}
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		
		builder.append(this.getClass().getName() + "-" + "Id=");
		builder.append(Id);
		builder.append("  fileName=");
		builder.append(fileName);
		builder.append("  fileType=");
		builder.append(fileType);
		builder.append("  fileSize=");
		builder.append(fileSize);
		builder.append("  created=");
		builder.append(created);
		
		return builder.toString();
	}
	
	/**
	 * Returns a hash code value for the object.
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;

		result = prime * result + ((Id == null) ? 0 : Id.hashCode());

		return result;
	}

	/**
	 * Indicates whether some other object is equal to this one.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}

		final UserFile other = (UserFile) obj;

		if (Id == null) {
			if (other.Id != null)
				return false;
		} else if (!Id.equals(other.Id)) {
			return false;
		}

		return true;
	}
}
