package com.visionbizsolutions.orm.jpa.bean;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "USER_ROLES")
public class UserRole implements Serializable {

	private static final long serialVersionUID = -5916052313372767899L;
	private Integer Id = null;
	private String authority = null;
	private Date created = null;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Integer getId() {
		return Id;
	}

	public void setId(Integer id) {
		Id = id;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public String getAuthority() {
		return authority;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}

	/**
	 * Returns a string representation of the object.
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append(this.getClass().getName() + "-");
		sb.append("  id=" + Id);
		sb.append("  role=" + authority);
		sb.append("  created=" + created);

		return sb.toString();
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

		final UserRole other = (UserRole) obj;

		if (Id == null) {
			if (other.Id != null)
				return false;
		} else if (!Id.equals(other.Id)) {
			return false;
		}

		return true;
	}

}
