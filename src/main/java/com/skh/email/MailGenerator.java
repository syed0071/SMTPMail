package com.skh.email;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.Reader;
import java.io.StringReader;
import java.util.Properties;
import java.util.logging.Logger;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Hyperlink;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * @author
 *
 */
@Component
public class MailGenerator {

	private static final Logger logger = Logger.getLogger(MailGenerator.class.getName());
	@Autowired
	Environment environment;

	/**
	 * 
	 */
	public static final String DELIMETER = ",";


	/**
	 * @return Session
	 */
	public Session getSession() {
		Properties props = new Properties();
		props.setProperty("mail.transport.protocol", environment.getProperty("mailSmtpTransportProtocol"));

		props.setProperty("mail.host", environment.getProperty("mailSmtpHost"));
		props.put("mail.smtp.auth", environment.getProperty("mailSmtpAuth"));
		props.put("mail.smtp.port", environment.getProperty("mailSmtpPort"));
		props.put("mail.smtp.socketFactory.port", environment.getProperty("mailSmtpSocketFactoryPort"));
		props.put("mail.smtp.socketFactory.class", environment.getProperty("mailSmtpSocketFactoryClass"));
		props.put("mail.smtp.socketFactory.fallback", environment.getProperty("mailSmtpSocketFactoryFallback"));
		Session session = Session.getDefaultInstance(props);
		// Get the Session object.
		// Session session = Session.getInstance( props, new javax.mail.Authenticator()
		// {
		// @Override
		// protected PasswordAuthentication getPasswordAuthentication()
		// {
		// return new PasswordAuthentication( environment.getProperty( "mailUsername" ),
		// environment.getProperty( "mailPassword" ) );
		// }
		// } );

		return session;
	}

	/**
	 * @param data
	 * @param filename
	 */
	public void writeToFile(String data, String filename) {
		logger.info("write to file started");
		try {
			String xlsxFileAddress = filename;
			XSSFWorkbook workBook = new XSSFWorkbook();
			CreationHelper createHelper = workBook.getCreationHelper();
			XSSFSheet sheet = workBook.createSheet("sheet1");
			XSSFCellStyle hlinkstyle = workBook.createCellStyle();
			XSSFFont hlinkfont = workBook.createFont();
			hlinkfont.setUnderline(XSSFFont.U_SINGLE);
			hlinkfont.setColor(HSSFColor.BLUE.index);
			hlinkstyle.setFont(hlinkfont);
			String currentLine = null;
			int RowNum = 0;
			Reader inputString = new StringReader(data);
			BufferedReader br = new BufferedReader(inputString);
			currentLine = br.readLine();
			String str[] = currentLine.split(",");
			XSSFRow currentRow = sheet.createRow(RowNum);
			for (int i = 0; i < str.length; i++) {

				currentRow.createCell(i).setCellValue(str[i]);

			}
			while ((currentLine = br.readLine()) != null) {
				RowNum++;
				str = currentLine.split(",");

				currentRow = sheet.createRow(RowNum);

				for (int i = 0; i < str.length; i++) {
					if (i == 0) {
						Hyperlink link = createHelper.createHyperlink(Hyperlink.LINK_FILE);
						link.setAddress("https://jira3.skh.com/browse/CTSTCHTRAN-" + str[i]);
						Cell cell = currentRow.createCell(i);
						cell.setCellValue("CTSTCHTRAN-" + str[i]);
						cell.setHyperlink(link);
						cell.setCellStyle(hlinkstyle);

						;
					} else {
						currentRow.createCell(i).setCellValue(str[i]);
					}

				}

			}

			FileOutputStream fileOutputStream = new FileOutputStream(xlsxFileAddress);
			workBook.write(fileOutputStream);
			fileOutputStream.close();
			logger.info("Write to file completed successfully");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * @param filename
	 * 
	 */
	@SuppressWarnings("static-access")
	public void sendEmail() {
		logger.info("Sending mail");

		// Get the Session object.
		Session session = getSession();

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(environment.getProperty("mailFromAddress")));
			message.setRecipient(Message.RecipientType.TO,
					new InternetAddress(environment.getProperty("mailToAddress")));
			message.setSubject(environment.getProperty("mailSubject"));
			MimeBodyPart messageBodyPart = new MimeBodyPart();
			messageBodyPart.setText(environment.getProperty("mailBody"), "UTF-8", "html");
			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(messageBodyPart);
			messageBodyPart = new MimeBodyPart();
			multipart.addBodyPart(messageBodyPart);
			message.setContent(multipart);
			Transport transport = session.getTransport();
			transport.connect();
			transport.send(message);
			transport.close();
			logger.info("Report mailed successfully....");
		} catch (MessagingException e) {
			logger.info("Exception while sending report to uCern team" + e.getMessage());
		}
	}

	/**
	 * @param userId
	 * @return String
	 */
	public String getManager(String userId) {
		logger.info("called getManager for " + userId);
		String managerName = "";
		if (userId == null) {
			logger.info("Userid is null");
			return "";
		}
		// first create the service context
		DirContext serviceCtx = null;
		try {
			// use the service user to authenticate
			Properties serviceEnv = new Properties();
			serviceEnv.put(Context.INITIAL_CONTEXT_FACTORY, environment.getProperty("contextFactory"));
			serviceEnv.put(Context.PROVIDER_URL, environment.getProperty("ldapURI"));
			serviceEnv.put(Context.SECURITY_AUTHENTICATION, "simple");
			serviceEnv.put(Context.SECURITY_PRINCIPAL, environment.getProperty("ldapSource"));
			serviceEnv.put(Context.SECURITY_CREDENTIALS, environment.getProperty("ldapServicePassword"));
			serviceCtx = new InitialDirContext(serviceEnv);

			// we don't need all attributes, just let it get the identifying one
			SearchControls sc = new SearchControls();
			sc.setSearchScope(SearchControls.SUBTREE_SCOPE);

			// use a search filter to find only the user we want to authenticate
			String filter = "(sAMAccountName=" + userId.toUpperCase() + ")";
			// String filter1 = "(sAMAccountName=SS050367" + ")";
			NamingEnumeration<SearchResult> results = serviceCtx.search(environment.getProperty("ldapBase"), filter,
					sc);

			if (results.hasMore()) {
				SearchResult result = results.next();

				managerName = result.getAttributes().get("extensionAttribute6").get().toString();
				logger.info("Manager for " + userId + ":" + managerName);

			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (serviceCtx != null) {
				try {
					serviceCtx.close();
				} catch (NamingException e) {
					e.printStackTrace();
				}
			}
		}
		return managerName;

	}

}
