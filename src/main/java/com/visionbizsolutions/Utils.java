package com.visionbizsolutions;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.pdfbox.pdmodel.PDDocument;
import org.pdfbox.util.PDFTextStripper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.visionbizsolutions.mvc.commands.Contact;
import com.visionbizsolutions.orm.jpa.bean.User;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

@SuppressWarnings("restriction")
public class Utils {
	private static final Logger logger = LoggerFactory.getLogger(Utils.class);

	private static final boolean THROW_ON_LOAD_FAILURE = true;
	private static final boolean LOAD_AS_RESOURCE_BUNDLE = false;
	private static final String DEFAULT_CONFIGURATION_FILE = "application-configuration";
	private static final String SUFFIX = ".properties";
	private static final String[] VALID_FILE_TYPES_ALLOWED_TO_UPLOAD = new String[]{
		 "application/vnd.openxmlformats-officedocument.wordprocessingml.document",
		 "application/pdf",
		 "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
	};

	private final static int ITERATION_NUMBER = 1000;

	public static Properties loadProperties(String name, ClassLoader loader) {
		if (name == null)
			throw new IllegalArgumentException("null input: name");
		if (name.startsWith("/"))
			name = name.substring(1);
		if (name.endsWith(SUFFIX))
			name = name.substring(0, name.length() - SUFFIX.length());
		Properties result = null;
		InputStream in = null;
		try {
			if (loader == null)
				loader = ClassLoader.getSystemClassLoader();
			if (LOAD_AS_RESOURCE_BUNDLE) {
				name = name.replace('/', '.'); // Throws
												// MissingResourceException on
												// lookup failures:
				final ResourceBundle rb = ResourceBundle.getBundle(name,
						Locale.getDefault(), loader);
				result = new Properties();
				for (Enumeration<?> keys = rb.getKeys(); keys.hasMoreElements();) {
					final String key = (String) keys.nextElement();
					final String value = rb.getString(key);
					result.put(key, value);
				}
			} else {
				name = name.replace('.', '/');
				if (!name.endsWith(SUFFIX))
					name = name.concat(SUFFIX); // Returns null on lookup
												// failures:
				in = loader.getResourceAsStream(name);
				if (in != null) {
					result = new Properties();
					result.load(in); // Can throw IOException
				}
			}
		} catch (Exception e) {
			logger.error(Utils.getStackTrace(e.fillInStackTrace()));
			result = null;
		} finally {
			if (in != null)
				try {
					in.close();
				} catch (Throwable ignore) {
				}
		}
		if (THROW_ON_LOAD_FAILURE && (result == null)) {
			throw new IllegalArgumentException("could not load ["
					+ name
					+ "]"
					+ " as "
					+ (LOAD_AS_RESOURCE_BUNDLE ? "a resource bundle"
							: "a classloader resource"));
		}
		return result;
	}

	/**
	 * * A convenience overload of {@link #loadProperties(String, ClassLoader)}
	 * * that uses the current thread's context classloader.
	 */
	public static Properties loadProperties(final String name) {
		return loadProperties(name, Thread.currentThread()
				.getContextClassLoader());
	}

	/**
	 * * A convenience overload of {@link #loadProperties(String, ClassLoader)}
	 * * that uses the current thread's context classloader.
	 */
	public static Properties loadProperties() {
		return loadProperties(Utils.DEFAULT_CONFIGURATION_FILE, Thread.currentThread()
				.getContextClassLoader());
	}

	/**
	 * This method takes the http-request and take out the g-recaptcha-response
	 * parameter and then talk to Google for verifying that the status is true,
	 * that means the user has used the CAPTCHA.
	 * 
	 * 
	 * @param request
	 * @return
	 */
	public static boolean captchaValidatated(HttpServletRequest request) {
		try {
			String recaptchaResponse = request
					.getParameter("g-recaptcha-response");
			logger.info(recaptchaResponse);

			String secretParameter = "6LftYwUTAAAAAM1V52iC9hJU6T5XX7kNLlRfYYlO";
			System.out.println(recaptchaResponse);

			// Send get request to Google reCaptcha server with secret key
			URL url = new URL(
					"https://www.google.com/recaptcha/api/siteverify?secret="
							+ secretParameter + "&response="
							+ recaptchaResponse + "&remoteip="
							+ request.getRemoteAddr());
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			String line, outputString = "";

			BufferedReader reader = new BufferedReader(new InputStreamReader(
					conn.getInputStream()));
			while ((line = reader.readLine()) != null) {
				outputString += line;
			}
			logger.info("Status in string: " + outputString);
			// Convert response into Object

			Object object = JSONValue.parse(outputString);
			JSONObject jsonObject = (JSONObject) object;

			if (jsonObject.containsKey("success")) {
				Object objectValue = (Object) jsonObject.get("success");

				boolean value = false;
				if (objectValue instanceof String) {
					String strValue = (String) objectValue;
					if (strValue != null && !strValue.trim().equals("")) {
						value = Boolean.valueOf(strValue);
					}
				} else if (objectValue instanceof Boolean) {
					Boolean bolValue = (Boolean) objectValue;
					if (bolValue != null) {
						value = bolValue.booleanValue();
					}
				}

				if (value) {
					logger.debug("The user is not a ROBOT.");
					return true;
				} else {
					logger.debug("The user is either a ROBOT or the user skips the CAPTCHA.");
					return false;
				}

			}// End outer if
			logger.debug("after if");
		}// try
		catch (Exception e) {
			e.printStackTrace();
			logger.error(Utils.getStackTrace(e.fillInStackTrace()));
			return false;
		}
		return false;
	}

	/**
	 * This method generates the hash for the user Email Verification message
	 * 
	 * @param iterationNb
	 * @param data
	 * @param salt
	 * @return
	 * @throws Exception
	 */
	public static byte[] getHash(String data, byte[] salt) throws Exception {
		MessageDigest digest = MessageDigest.getInstance("SHA-1");
		digest.reset();
		digest.update(salt);
		byte[] input = digest.digest(data.getBytes("UTF-8"));
		for (int i = 0; i < ITERATION_NUMBER; i++) {
			digest.reset();
			input = digest.digest(input);
		}
		return input;
	}

	/**
	 * This method generates the hash for the user
	 * 
	 * @return
	 * @throws Exception
	 */
	public static byte[] getSalt() throws Exception {
		// Uses a secure Random not a simple Random
		SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
		// Salt generation 64 bits long
		byte[] bSalt = new byte[8];
		random.nextBytes(bSalt);

		return bSalt;
	}

	/**
	 * From a base 64 representation, returns the corresponding byte[]
	 * 
	 * @param data
	 *            String The base64 representation
	 * @return byte[]
	 * @throws IOException
	 */
	public static byte[] base64ToByte(String data) throws IOException {
		BASE64Decoder decoder = new BASE64Decoder();
		return decoder.decodeBuffer(data);
	}

	/**
	 * From a byte[] returns a base 64 representation
	 * 
	 * @param data
	 *            byte[]
	 * @return String
	 * @throws IOException
	 */
	public static String byteToBase64(byte[] data) {
		BASE64Encoder endecoder = new BASE64Encoder();
		return endecoder.encode(data);
	}

	public static void populateContact(User user, Contact contact) {
		contact.setCompany(user.getCompany());
		contact.setUsername(user.getUsername());
		contact.setFname(user.getFirstName());
		contact.setLname(user.getLastName());
		contact.setAddress(user.getAddress());
		contact.setCountry(user.getCountry());
		contact.setProvince(user.getProvince());
		contact.setZip(String.valueOf(user.getZip()));
		contact.setPhone(user.getPhone());
		contact.setEmail(user.getEmail());
		contact.setPreferred(user.getPreferred());
		contact.setStage(user.getStage());
		contact.setSoftware(user.getSoftware());
		contact.setFrequency(user.getFrequency());
		contact.setAbout(user.getAbout());
	}

	public static Map<String, String> getCountriesMap() {
		Map<String, String> countries = new HashMap<String, String>();
		countries.put("Canada", "Canada");
		countries.put("Afghanistan", "Afghanistan");
		countries.put("Albania", "Albania");
		countries.put("Algeria", "Algeria");

		countries.put("American Samoa", "American Samoa");
		countries.put("Andorra", "Andorra");
		countries.put("Angola", "Angola");
		countries.put("Anguilla", "Anguilla");
		countries.put("Antarctica", "Antarctica");
		countries.put("Antigua and Barbuda", "Antigua and Barbuda");

		countries.put("Argentina", "Argentina");
		countries.put("Armenia", "Armenia");
		countries.put("Aruba", "Aruba");
		countries.put("Australia", "Australia");
		countries.put("Austria", "Austria");
		countries.put("Azerbaijan", "Azerbaijan");

		countries.put("Bahamas", "Bahamas");
		countries.put("Bahrain", "Bahrain");
		countries.put("Bangladesh", "Bangladesh");
		countries.put("Barbados", "Barbados");
		countries.put("Belarus", "Belarus");
		countries.put("Belgium", "Belgium");

		countries.put("Belize", "Belize");
		countries.put("Benin", "Benin");
		countries.put("Bermuda", "Bermuda");
		countries.put("Bhutan", "Bhutan");
		countries.put("Bolivia", "Bolivia");
		countries.put("Bosnia and Herzegovina", "Bosnia and Herzegovina");

		countries.put("Botswana", "Botswana");
		countries.put("Bouvet Island", "Bouvet Island");
		countries.put("Brazil", "Brazil");
		countries.put("British Indian Ocean Territory",
				"British Indian Ocean Territory");
		countries.put("Brunei Darussalam", "Brunei Darussalam");
		countries.put("Bulgaria", "Bulgaria");

		countries.put("Burkina Faso", "Burkina Faso");
		countries.put("Burma", "Burma");
		countries.put("Burundi", "Burundi");
		countries.put("Cambodia", "Cambodia");
		countries.put("Cameroon", "Cameroon");

		countries.put("Canton and Enderbury Islands",
				"Canton and Enderbury Islands");
		countries.put("Cape Verde", "Cape Verde");
		countries.put("Cayman Islands", "Cayman Islands");
		countries.put("Central African Republic", "Central African Republic");
		countries.put("Chad", "Chad");
		countries.put("Chile", "Chile");

		countries.put("China", "China");
		countries.put("Christmas Island", "Christmas Island");
		countries.put("Cocos (Keeling) Islands", "Cocos (Keeling) Islands");
		countries.put("Colombia", "Colombia");
		countries.put("Comoros", "Comoros");
		countries.put("Congo", "Congo");

		countries.put("Cook Islands", "Cook Islands");
		countries.put("Costa Rica", "Costa Rica");
		countries.put("Cote D'Ivoire", "Cote D'Ivoire");
		countries.put("Croatia (Hrvatska)", "Croatia (Hrvatska)");
		countries.put("Cuba", "Cuba");
		countries.put("Cyprus", "Cyprus");

		countries.put("Czech Republic", "Czech Republic");
		countries.put("Democratic Yemen", "Democratic Yemen");
		countries.put("Denmark", "Denmark");
		countries.put("Djibouti", "Djibouti");
		countries.put("Dominica", "Dominica");
		countries.put("Dominican Republic", "Dominican Republic");

		countries.put("Dronning Maud Land", "Dronning Maud Land");
		countries.put("East Timor", "East Timor");
		countries.put("Ecuador", "Ecuador");
		countries.put("Egypt", "Egypt");
		countries.put("El Salvador", "El Salvador");
		countries.put("Equatorial Guinea", "Equatorial Guinea");

		countries.put("Eritrea", "Eritrea");
		countries.put("Estonia", "Estonia");
		countries.put("Ethiopia", "Ethiopia");
		countries.put("Falkland Islands (Malvinas)",
				"Falkland Islands (Malvinas)");
		countries.put("Faroe Islands", "Faroe Islands");
		countries.put("Fiji", "Fiji");

		countries.put("Finland", "Finland");
		countries.put("France", "France");
		countries.put("France, Metropolitan", "France, Metropolitan");
		countries.put("French Guiana", "French Guiana");
		countries.put("French Polynesia", "French Polynesia");
		countries.put("French Southern Territories",
				"French Southern Territories");

		countries.put("Gabon", "Gabon");
		countries.put("Gambia", "Gambia");
		countries.put("Georgia", "Georgia");
		countries.put("Germany", "Germany");
		countries.put("Ghana", "Ghana");
		countries.put("Gibraltar", "Gibraltar");

		countries.put("Greece", "Greece");
		countries.put("Greenland", "Greenland");
		countries.put("Grenada", "Grenada");
		countries.put("Guadeloupe", "Guadeloupe");
		countries.put("Guam", "Guam");
		countries.put("Guatemala", "Guatemala");

		countries.put("Guinea", "Guinea");
		countries.put("Guinea-bisseu", "Guinea-bisseu");
		countries.put("Guyana", "Guyana");
		countries.put("Haiti", "Haiti");
		countries.put("Heard and Mc Donald Islands",
				"Heard and Mc Donald Islands");
		countries.put("Honduras", "Honduras");

		countries.put("Hong Kong", "Hong Kong");
		countries.put("Hungary", "Hungary");
		countries.put("Iceland", "Iceland");
		countries.put("India", "India");
		countries.put("Indonesia", "Indonesia");
		countries.put("Iran (Islamic Republic of)",
				"Iran (Islamic Republic of)");

		countries.put("Iraq", "Iraq");
		countries.put("Ireland", "Ireland");
		countries.put("Israel", "Israel");
		countries.put("Italy", "Italy");
		countries.put("Ivory Coast", "Ivory Coast");
		countries.put("Jamaica", "Jamaica");

		countries.put("Japan", "Japan");
		countries.put("Johnston Island", "Johnston Island");
		countries.put("Jordan", "Jordan");
		countries.put("Kazakstan", "Kazakstan");
		countries.put("Kenya", "Kenya");
		countries.put("Kiribati", "Kiribati");

		countries.put("Korea, Democratic People's Republic of",
				"Korea, Democratic People's Republic of");
		countries.put("Korea, Republic of", "Korea, Republic of");
		countries.put("Kuwait", "Kuwait");
		countries.put("Kyrgyzstan", "Kyrgyzstan");
		countries.put("Lao People's Democratic Republic",
				"Lao People's Democratic Republic");
		countries.put("Latvia", "Latvia");

		countries.put("Lebanon", "Lebanon");
		countries.put("Lesotho", "Lesotho");
		countries.put("Liberia", "Liberia");
		countries.put("Libyan Arab Jamahiriya", "Libyan Arab Jamahiriya");
		countries.put("Liechtenstein", "Liechtenstein");
		countries.put("Lithuania", "Lithuania");

		countries.put("Luxembourg", "Luxembourg");
		countries.put("Macau", "Macau");
		countries.put("Macedonia", "Macedonia");
		countries.put("Madagascar", "Madagascar");
		countries.put("Malawi", "Malawi");
		countries.put("Malaysia", "Malaysia");

		countries.put("Maldives", "Maldives");
		countries.put("Mali", "Mali");
		countries.put("Malta", "Malta");
		countries.put("Marshall Islands", "Marshall Islands");
		countries.put("Martinique", "Martinique");
		countries.put("Mauritania", "Mauritania");

		countries.put("Mauritius", "Mauritius");
		countries.put("Mayotte", "Mayotte");
		countries.put("Mexico", "Mexico");
		countries.put("Micronesia, Federated States of",
				"Micronesia, Federated States of");
		countries.put("Midway Islands", "Midway Islands");
		countries.put("Moldova, Republic of", "Moldova, Republic of");

		countries.put("Monaco", "Monaco");
		countries.put("Mongolia", "Mongolia");
		countries.put("Montserrat", "Montserrat");
		countries.put("Morocco", "Morocco");
		countries.put("Mozambique", "Mozambique");
		countries.put("Myanmar", "Myanmar");

		countries.put("Namibia", "Namibia");
		countries.put("Nauru", "Nauru");
		countries.put("Nepal", "Nepal");
		countries.put("Netherlands", "Netherlands");
		countries.put("Netherlands Antilles", "Netherlands Antilles");
		countries.put("Neutral Zone", "Neutral Zone");

		countries.put("New Calidonia", "New Calidonia");
		countries.put("New Zealand", "New Zealand");
		countries.put("Nicaragua", "Nicaragua");
		countries.put("Niger", "Niger");
		countries.put("Nigeria", "Nigeria");
		countries.put("Niue", "Niue");

		countries.put("Norfolk Island", "Norfolk Island");
		countries.put("Northern Mariana Islands", "Northern Mariana Islands");
		countries.put("Norway", "Norway");
		countries.put("Oman", "Oman");
		countries.put("Pacific Islands", "Pacific Islands");
		countries.put("Pakistan", "Pakistan");

		countries.put("Palau", "Palau");
		countries.put("Panama", "Panama");
		countries.put("Papua New Guinea", "Papua New Guinea");
		countries.put("Paraguay", "Paraguay");
		countries.put("Peru", "Peru");
		countries.put("Philippines", "Philippines");

		countries.put("Pitcairn Island", "Pitcairn Island");
		countries.put("Poland", "Poland");
		countries.put("Portugal", "Portugal");
		countries.put("Puerto Rico", "Puerto Rico");
		countries.put("Qatar", "Qatar");
		countries.put("Reunion", "Reunion");

		countries.put("Romania", "Romania");
		countries.put("Russian Federation", "Russian Federation");
		countries.put("Rwanda", "Rwanda");
		countries.put("S. Georgia and S. Sandwich Isls",
				"S. Georgia and S. Sandwich Isls.");
		countries.put("Saint Lucia", "Saint Lucia");
		countries.put("Saint Vincent/Grenadines", "Saint Vincent/Grenadines");

		countries.put("Samoa", "Samoa");
		countries.put("San Marino", "San Marino");
		countries.put("Sao Tome and Principe", "Sao Tome and Principe");
		countries.put("Saudi Arabia", "Saudi Arabia");
		countries.put("Senegal", "Senegal");
		countries.put("Seychelles", "Seychelles");

		countries.put("Sierra Leone", "Sierra Leone");
		countries.put("Singapore", "Singapore");
		countries.put("Slovakia (Slovak Republic)",
				"Slovakia (Slovak Republic)");
		countries.put("Slovenia", "Slovenia");
		countries.put("Solomon Islands", "Solomon Islands");
		countries.put("Somalia", "Somalia");

		countries.put("South Africa", "South Africa");
		countries.put("Spain", "Spain");
		countries.put("Sri Lanka", "Sri Lanka");
		countries.put("St. Helena", "St. Helena");
		countries.put("St. Kitts Nevis Anguilla", "St. Kitts Nevis Anguilla");
		countries.put("St. Pierre and Miquelon", "St. Pierre and Miquelon");

		countries.put("Sudan", "Sudan");
		countries.put("Suriname", "Suriname");
		countries.put("Svalbard and Jan Mayen Islands",
				"Svalbard and Jan Mayen Islands");
		countries.put("Swaziland", "Swaziland");
		countries.put("Sweden", "Sweden");
		countries.put("Switzerland", "Switzerland");

		countries.put("Syran Arab Republic", "Syran Arab Republic");
		countries.put("Taiwan", "Taiwan");
		countries.put("Tajikistan", "Tajikistan");
		countries.put("Tanzania, United Republic of",
				"Tanzania, United Republic of");
		countries.put("Thailand", "Thailand");
		countries.put("Togo", "Togo");

		countries.put("Tokelau", "Tokelau");
		countries.put("Tonga", "Tonga");
		countries.put("Trinidad and Tobago", "Trinidad and Tobago");
		countries.put("Tunisia", "Tunisia");
		countries.put("Turkey", "Turkey");
		countries.put("Turkmenistan", "Turkmenistan");

		countries.put("Turks and Caicos Islands", "Turks and Caicos Islands");
		countries.put("Tuvalu", "Tuvalu");
		countries.put("US Minor Outlying Islands", "US Minor Outlying Islands");
		countries.put("Uganda", "Uganda");
		countries.put("Ukraine", "Ukraine");
		countries.put("United Arab Emirates", "United Arab Emirates");

		countries.put("United Kingdom", "United Kingdom");
		countries.put("United States", "United States");
		countries.put("United States Pacific Islands",
				"United States Pacific Islands");
		countries.put("Upper Volta", "Upper Volta");
		countries.put("Uruguay", "Uruguay");
		countries.put("Uzbekistan", "Uzbekistan");

		countries.put("Vanuatu", "Vanuatu");
		countries.put("Vatican City State", "Vatican City State");
		countries.put("Venezuela", "Venezuela");
		countries.put("VietNam", "VietNam");
		countries.put("Virgin Islands, British", "Virgin Islands, British");
		countries.put("Virgin Islands, Unites States",
				"Virgin Islands, Unites States");

		countries.put("Wake Island", "Wake Island");
		countries.put("Wallis and Futuma Islands", "Wallis and Futuma Islands");
		countries.put("Western Sahara", "Western Sahara");
		countries.put("Yemen", "Yemen");
		countries.put("Yugoslavia", "Yugoslavia");
		countries.put("Zaire", "Zaire");

		countries.put("Zambia", "Zambia");
		countries.put("Zimbabwe", "Zimbabwe");
		countries.put("Other", "Other");

		return countries;

	}

	@SuppressWarnings("unchecked")
	public static Contact getContactObjectFromJSON(String contactObjectJSON) {
		Contact contact = new Contact();
		Object object = JSONValue.parse(contactObjectJSON);
		JSONObject jsonObject = (JSONObject) object;
		
		Set<String> keys = jsonObject.keySet();
		Iterator<String> iterator = keys.iterator();
		while (iterator.hasNext()) {
			String key = iterator.next();
			switch (key) {
			case "company":
				contact.setCompany((String) jsonObject.get(key));
				break;
			case "fName":
				contact.setFname((String) jsonObject.get(key));
				break;
			case "lName":
				contact.setLname((String) jsonObject.get(key));
				break;
			case "address":
				contact.setAddress((String) jsonObject.get(key));
				break;
			case "country":
				contact.setCountry((String) jsonObject.get(key));
				break;
			case "province":
				contact.setProvince((String)jsonObject.get(key));
				break;
			case "zip":
				contact.setZip((String) jsonObject.get(key));
				break;
			case "phone":
				contact.setPhone((String) jsonObject.get(key));
				break;
			case "email":
				contact.setEmail((String) jsonObject.get(key));
				break;
			case "username":
				contact.setUsername((String) jsonObject.get(key));
				break;
			case "preferred":
				contact.setPreferred((String) jsonObject.get(key));
				break;
			case "stage":
				contact.setStage((String) jsonObject.get(key));
				break;
			case "software":
				contact.setSoftware((String) jsonObject.get(key));
				break;
			case "frequency":
				contact.setFrequency((String) jsonObject.get(key));
				break;
			case "about":
				contact.setAbout((String) jsonObject.get(key));
				break;

			}
		}
		return contact;
	}
	
	public static void populateUser(Contact contact, User user) {
		user.setCompany(contact.getCompany());
		user.setUsername(contact.getUsername());
		user.setFirstName(contact.getFname());
		user.setLastName(contact.getLname());
		user.setAddress(contact.getAddress());
		user.setCountry(contact.getCountry());
		user.setProvince(contact.getProvince());
		user.setZip(Integer.valueOf(contact.getZip()));
		user.setPhone(contact.getPhone());
		user.setEmail(contact.getEmail());
		user.setPreferred(contact.getPreferred());
		user.setStage(contact.getStage());
		user.setSoftware(contact.getSoftware());
		user.setFrequency(contact.getFrequency());
		user.setAbout(contact.getAbout());
	}
	
	public static String[] getPasswordsFromJSON(String passwordsJSON) {
		String passwords[] = new String[2];
		Object object = JSONValue.parse(passwordsJSON);
		JSONObject jsonObject = (JSONObject) object;
		
		if(jsonObject.containsKey("oldPassword")){
			Object objectValue = (Object) jsonObject.get("oldPassword");

			if (objectValue instanceof String) {
				String strValue = (String) objectValue;
				if (strValue != null && !strValue.trim().equals("")) {
					passwords[0] = String.valueOf(strValue);
				}
			}
		}//end if oldPassword
		
		if(jsonObject.containsKey("newPassword")){
			Object objectValue = (Object) jsonObject.get("newPassword");

			if (objectValue instanceof String) {
				String strValue = (String) objectValue;
				if (strValue != null && !strValue.trim().equals("")) {
					passwords[1] = String.valueOf(strValue);
				}
			}
		}//end if newPassword
		
		return passwords;
	}
	
	public static boolean checkPdfValidity(String fileName) throws FileNotFoundException, IOException{
		String text = null;
		PDDocument pdfDocument = null;
		
		try {
			pdfDocument = PDDocument.load(new FileInputStream(fileName));
			text = new PDFTextStripper().getText(pdfDocument);
		} catch(Exception e){
			logger.error(Utils.getStackTrace(e.fillInStackTrace()));
			return false;
		} finally {
			if(pdfDocument != null){
				pdfDocument.close();
			}
		}
		
		if(text == null || text.equals("")){
			return false;
		}
			
		return true;
	}
	/**
	 * docx application/vnd.openxmlformats-officedocument.wordprocessingml.document
	 * PDF application/pdf
	 * application/vnd.openxmlformats-officedocument.spreadsheetml.sheet
	 */
	
	public static boolean isValidFileType(String fileType){
		logger.debug("Validating received file type: " + fileType);
		for(String _fileType: VALID_FILE_TYPES_ALLOWED_TO_UPLOAD){
			if(_fileType.equalsIgnoreCase(fileType)){
				return true;
			}
		}
		return false;
	}
	
	public static String getShortFileType(String originalFileType){
		logger.debug("Converting received file type to short type: " + originalFileType);
		if(originalFileType.equalsIgnoreCase(VALID_FILE_TYPES_ALLOWED_TO_UPLOAD[0])){
			return "Microsoft Word";
		}
		else if(originalFileType.equalsIgnoreCase(VALID_FILE_TYPES_ALLOWED_TO_UPLOAD[1])){
			return "PDF";
		}
		else if(originalFileType.equalsIgnoreCase(VALID_FILE_TYPES_ALLOWED_TO_UPLOAD[2])){
			return "Microsoft Excel";
		}
		return null;
	}
	
	public static long getMinutes(Date lastModified){
		Calendar calCurrentTime = Calendar.getInstance();
		
		Calendar calLastModified = Calendar.getInstance();
		calLastModified.setTime(lastModified);
		
		long timeDifference = calCurrentTime.getTimeInMillis() - calLastModified.getTimeInMillis();
		long seconds = timeDifference/1000;
		long minutes = seconds/60;
		
		return minutes;
	}
	
	public static String getStackTrace(Throwable aThrowable) {
		//add the class name and any message passed to constructor
	    StringBuilder result = new StringBuilder();
	    result.append(aThrowable.toString());
	    String NEW_LINE = System.getProperty("line.separator");
	    result.append(NEW_LINE);

	    //add each element of the stack trace
	    for (StackTraceElement element : aThrowable.getStackTrace()){
	      result.append(element);
	      result.append(NEW_LINE);
	    }
	    return result.toString();
	}
	
	public static String genSecureRandom() {
        Random r = new Random();
        MessageDigest md = null;

        try {
            md = MessageDigest.getInstance("MD5");
        } catch ( NoSuchAlgorithmException e ) {
            logger.error( "Unsupported Algorithm!" + getStackTrace(e.fillInStackTrace()) );
            return null;
        }

        byte[] entropy = new byte[1024];
        r.nextBytes(entropy);
        md.update( entropy , 0, 1024 );

        return new BigInteger(1, md.digest()).toString(16).substring(0, 20);
    }
}
