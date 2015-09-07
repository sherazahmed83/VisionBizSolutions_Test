package com.visionbizsolutions.mvc.commands;

import java.util.Date;

public class Contact {
	private String company;
	private String name;
	private String address;
	private String zip;
	private String country;
	private String province;
	private String phone;
	private String email;
	private String preferred;
	private String stage;
	private String software;
	private String frequency;
	private String about;
	private String fname;
	private String lname;
	private String username;
	private String password;
	private String verificationHash;
	private String salt;
	private Date created;
	private boolean accountNonExpired = true;
    private boolean credentialsNonExpired = true;
    private boolean accountNonLocked = true;
	private String role = "ROLE_USER";
	

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
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

	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public String getLname() {
		return lname;
	}

	public void setLname(String lname) {
		this.lname = lname;
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

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
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

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("An Enquiry received with following details: ");
		sb.append("\n");
		sb.append("\n");
		sb.append("Company Name: " + company);
		sb.append("\n");
		
		if (name != null && !name.trim().equals("")) {
			sb.append("Contact's Name: " + name);
		} 
		else if ((fname != null && !fname.trim().equals(""))
				&&
				(lname != null && !lname.trim().equals(""))){
			sb.append("Contact's Name: " + fname + " " + lname);
		}
		
		sb.append("\n");
		sb.append("Address: " + address);
		sb.append("\n");
		sb.append("Country: " + country);
		sb.append("\n");
		sb.append("Province: " + province);
		sb.append("\n");
		sb.append("Zip Code: " + zip);
		sb.append("\n");
		sb.append("Phone No: " + phone);
		sb.append("\n");
		sb.append("Email: " + email);
		sb.append("\n");
		sb.append("Preferred Method of Contact: " + preferred);
		sb.append("\n");
		sb.append("Outsourcing Stage: " + stage);
		sb.append("\n");
		sb.append("Accounting Software Used: " + software);
		sb.append("\n");
		sb.append("Preferred Bookkeeping frequency requirment: " + frequency);
		sb.append("\n");
		sb.append("Details about the Enqurier: " + about);
		sb.append("\n");

		return sb.toString();
	}

}
