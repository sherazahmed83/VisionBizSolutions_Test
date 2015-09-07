package com.visionbizsolutions.orm.jpa.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "USERS")
public class User implements Serializable {

	private static final long serialVersionUID = 5747580189344176917L;

	private Integer Id = null;
	private String username = null;
	private String password = null;
	private String salt = null;
	private String company = null;
	private String firstName = null;
	private String lastName = null;
	private String address = null;
	private String country = null;
	private String province = null;
	private Integer zip = null;
	private String phone = null;
	private String email = null;
	private String preferred = null;
	private String stage = null;
	private String software = null;
	private String frequency = null;
	private String about = null;
	private boolean enabled;
	private String verificationHash;
	private Date created = null;
	private boolean accountNonExpired;
    private boolean credentialsNonExpired;
    private boolean accountNonLocked;

	private Set<UserRole> authorities = new HashSet<UserRole>();
	private Set<UserFile> files = new HashSet<UserFile>();
	private UserAttempts attempts = new UserAttempts();

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	public Integer getId() {
		return Id;
	}

	public void setId(Integer id) {
		Id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public Integer getZip() {
		return zip;
	}

	public void setZip(Integer zip) {
		this.zip = zip;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPreferred() {
		return preferred;
	}

	public void setPreferred(String preferred) {
		this.preferred = preferred;
	}

	public String getStage() {
		return stage;
	}

	public void setStage(String stage) {
		this.stage = stage;
	}

	public String getSoftware() {
		return software;
	}

	public void setSoftware(String software) {
		this.software = software;
	}

	public String getFrequency() {
		return frequency;
	}

	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}

	public String getAbout() {
		return about;
	}

	public void setAbout(String about) {
		this.about = about;
	}

	public String getVerificationHash() {
		return verificationHash;
	}

	public void setVerificationHash(String verificationHash) {
		this.verificationHash = verificationHash;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}


	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public boolean isAccountNonExpired() {
		return accountNonExpired;
	}

	public void setAccountNonExpired(boolean accountNonExpired) {
		this.accountNonExpired = accountNonExpired;
	}

	public boolean isCredentialsNonExpired() {
		return credentialsNonExpired;
	}

	public void setCredentialsNonExpired(boolean credentialsNonExpired) {
		this.credentialsNonExpired = credentialsNonExpired;
	}

	public boolean isAccountNonLocked() {
		return accountNonLocked;
	}

	public void setAccountNonLocked(boolean accountNonLocked) {
		this.accountNonLocked = accountNonLocked;
	}
	
	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "ATTEMPTS_ID", nullable = false)
	public UserAttempts getAttempts() {
		return attempts;
	}

	public void setAttempts(UserAttempts attempts) {
		this.attempts = attempts;
	}

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "USER_ID", nullable = false)
	public Set<UserRole> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(Set<UserRole> authorities) {
		this.authorities = authorities;
	}

	public UserRole findRoleById(Integer id) {
		UserRole result = null;

		if (authorities != null) {
			for (UserRole role : authorities) {
				if (role.getId().equals(id)) {
					result = role;

					break;
				}
			}
		}

		return result;
	}
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "USER_ID", nullable = false)
	public Set<UserFile> getFiles() {
		return files;
	}

	public void setFiles(Set<UserFile> files) {
		this.files = files;
	}
	
	public UserFile findFileById(Integer id) {
		UserFile result = null;

		if (files != null) {
			for (UserFile file : files) {
				if (file.getId().equals(id)) {
					result = file;

					break;
				}
			}
		}

		return result;
	}

	/**
	 * Returns a string representation of the object.
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append(this.getClass().getName() + "-");
		sb.append("  id=" + Id);
		sb.append("  username=" + username);
		sb.append("  company=" + company);
		sb.append("  username=" + username);
		sb.append("  firstName=" + firstName);
		sb.append("  lastName=" + lastName);
		sb.append("  addresss=" + address);
		sb.append("  country=" + country);
		sb.append("  province/state=" + province);
		sb.append("  zip=" + zip);
		sb.append("  phone=" + phone);
		sb.append("  email=" + email);
		sb.append("  preferred=" + preferred);
		sb.append("  stage=" + stage);
		sb.append("  software=" + software);
		sb.append("  frequency=" + frequency);
		sb.append("  about=" + about);
		sb.append("  enabled=" + enabled);
		
        sb.append("  roles=[");
        
        if (authorities != null) {
            for (UserRole role : authorities) {
                sb.append(role.toString());
            }
        }
        
        sb.append("]");
        
        sb.append("  files=[");

        if (files != null) {
            for (UserFile file : files) {
                sb.append(file.toString());
            }
        }
        
        sb.append("]");
        
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
		final User other = (User) obj;

		if (Id == null) {
			if (other.Id != null) {
				return false;
			}
		} else if (!Id.equals(other.Id)) {
			return false;
		}

		return true;
	}

	


}
