package beans;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Hashtable;
import java.util.Map;
import java.util.Properties;
import java.util.Random;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.InternetHeaders;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.jniwrapper.win32.jexcel.el;

import managers.LoginProcessManager;
import managers.LoginProcessManager.SMTPAuthenticator;
import utils.Globals;
import utils.Inventory;
import utils.PropUtil;


@ManagedBean(name = "loginRegistrationBean")
@SessionScoped
public class LoginRegistrationBean {
	String HIERARCHY_XML_DIR=" ";
	public String userEmailId;
	public String emailPassword;
	public String cfmPassword;
	public String cmpyName;
	public String msgLbl;
	public String confrmPassword;
	public String getConfrmPassword() {
		return confrmPassword;
	}
	public void setConfrmPassword(String confrmPassword) {
		this.confrmPassword = confrmPassword;
	}
	public boolean  newcustomerBoxVAL;
	public boolean  superPrivilegeVAL;

	public boolean  activeBoxVAL;
	public boolean  disableBoxVAL;

	public boolean isActiveBoxVAL() {
		return activeBoxVAL;
	}



	public void setActiveBoxVAL(boolean activeBoxVAL) {
		this.activeBoxVAL = activeBoxVAL;
	}



	public boolean isDisableBoxVAL() {
		return disableBoxVAL;
	}



	public void setDisableBoxVAL(boolean disableBoxVAL) {
		this.disableBoxVAL = disableBoxVAL;
	}



	public String getCustomerKey() {
		return CustomerKey;
	}



	public void setCustomerKey(String customerKey) {
		CustomerKey = customerKey;
	}







	public String listfirstNameTXT;

	public String getListfirstNameTXT() {
		return listfirstNameTXT;
	}



	public void setListfirstNameTXT(String listfirstNameTXT) {
		this.listfirstNameTXT = listfirstNameTXT;
	}
	public boolean isSuperPrivilegeVAL() {
		return superPrivilegeVAL;
	}



	public void setSuperPrivilegeVAL(boolean superPrivilegeVAL) {
		this.superPrivilegeVAL = superPrivilegeVAL;
	}




	String CustomerKey="2345678";

	public void registervalidation(String userMail,String useremailPswd,String confrmPassword,Boolean newcustomerBoxVAL,Boolean superPrivilegeVAL,Boolean activeBoxVAL,Boolean disableBoxVAL,String listfirstNameTXT,String listlastNameTXT,String listtitleTXT,String listph1TXT,String listph2TXT,String listph3TXT,String listMail1TXT,String listMail2TXT,String listaddressTXT,String listcityTXT,String liststateTXT,String listcountryTXT,String listzipTXT) throws Exception {


		FacesContext ctx1 = FacesContext.getCurrentInstance();
		ExternalContext extContext1 = ctx1.getExternalContext();
		Map sessionMap1 = extContext1.getSessionMap();
		HierarchyBean hbean = (HierarchyBean) sessionMap1.get("hierarchyBean");

		hbean.setColor4AddUserMsg("blue");
		hbean.setColor4EditUserMsg("blue");
		if(newcustomerBoxVAL) {

			System.out.println("YESssssssssssss1sssssssssssssss---"+listfirstNameTXT);
			System.out.println("YESssssssssssss2sssssssssssssss---"+listlastNameTXT);
			System.out.println("YESssssssssssss3sssssssssssssss---"+listtitleTXT);
			System.out.println("YESsssssssssssss4ssssssssssssss---"+listph1TXT);
			System.out.println("YESssssssssssss5sssssssssssssss---"+listph2TXT);

			System.out.println("YESsssssssssssss6ssssssssssssss---"+listph3TXT);
			System.out.println("YESssssssssssssss7sssssssssssss---"+listMail1TXT);
			System.out.println("YESssssssssssssss8sssssssssssss---"+listMail2TXT);

			saveCustomerdetails();

			hbean.writeUserToXml(userMail,useremailPswd,confrmPassword,newcustomerBoxVAL,superPrivilegeVAL,activeBoxVAL,disableBoxVAL,listfirstNameTXT,listlastNameTXT,listtitleTXT,listph1TXT,listph2TXT,listph3TXT,listMail1TXT,listMail2TXT,listaddressTXT,listcityTXT, liststateTXT,listcountryTXT,listzipTXT);
			emptyfield();
		}else {
			System.out.println("No0000000000000000");
			//		
			hbean.writeUserToXml(userMail,useremailPswd,confrmPassword,newcustomerBoxVAL,superPrivilegeVAL,activeBoxVAL,disableBoxVAL,listfirstNameTXT,listlastNameTXT,listtitleTXT,listph1TXT,listph2TXT,listph3TXT,listMail1TXT,listMail2TXT,listaddressTXT,listcityTXT, liststateTXT,listcountryTXT,listzipTXT);
		}



		/*if(mailId.trim()=="" || password.trim()=="" || userKey.trim()=="" )
		{
			System.out.println("YES");
			return;
		}else {


		}*/

	}



	public void saveCustomerdetails() {

		try {

			writeregistr_TO_XML();

			String[] userMailAddress = new String[1];
			userMailAddress[0] = userEmailId;
			System.out.println("OOOOOOOOOOOOOOOOO : "+userMailAddress);


			PropUtil prop = new PropUtil();
			String heirLevelXML = prop.getProperty("HIERARCHY_XML_FILE");
			String heirLeveldir = prop.getProperty("HIERARCHY_XML_DIR");
			String hierlink4Mail= prop.getProperty("HIERARCHY_BASE_URL");
			Document xmlDoc = Globals.openXMLFile(heirLeveldir, heirLevelXML);
			FacesContext ctx = FacesContext.getCurrentInstance();
			ExternalContext extContext = ctx.getExternalContext();
			Map sessionMap = extContext.getSessionMap();
			LoginBean lgnbn = (LoginBean) sessionMap.get("loginBean");


			HierarchyBean hryb = (HierarchyBean) sessionMap.get("hierarchyBean");
			String Hierarchy_ID=hryb.getHierarchy_ID();
			String currentStageNo=hryb.getCurrentStageNumb();

			String adminUsername=lgnbn.getUsername();



			LoginProcessManager rgm = new LoginProcessManager();

			//Hashtable mailDtailsHT=rgm.getMailDetailsFromConfig(xmlDoc,Hierarchy_ID,currentStageNo,adminUsername);
			//			 PropUtil prop = new PropUtil();
			//				String hierarchyDIR = prop.getProperty("HIERARCHY_XML_DIR");
			String configFileName=prop.getProperty("HIERARCHY_CONFIG_FILE");

			String mailSendBy="";
			String mailPassword="";
			String mailMessage="";
			Document configdoc=Globals.openXMLFile(heirLeveldir, configFileName);
			NodeList ndlist=configdoc.getElementsByTagName("Mail_Configuration");
			Node nd=ndlist.item(0);
			NodeList ndlist1=nd.getChildNodes();

			for(int i=0;i<ndlist1.getLength();i++){
				Node nd1=ndlist1.item(i);
				if(nd1.getNodeType()==Node.ELEMENT_NODE && nd1.getNodeName().equalsIgnoreCase("Mail_ID_Send_by")){
					mailSendBy=nd1.getTextContent();
					//						 mailDtailsHT.put("Mail_ID_Send_by", mailSendBy);

				}
				if(nd1.getNodeType()==Node.ELEMENT_NODE && nd1.getNodeName().equalsIgnoreCase("Mail_Password")){
					mailPassword=nd1.getTextContent();
					//						 mailDtailsHT.put("Mail_Password", mailPassword);
				}
				if(nd1.getNodeType()==Node.ELEMENT_NODE && nd1.getNodeName().equalsIgnoreCase("Mail_Message")){
					mailMessage=nd1.getTextContent();
					//						 mailDtailsHT.put("Mail_Message", mailMessage);
				}
			}

			//			 String mailSendBy=(String)mailDtailsHT.get("Mail_ID_Send_by");
			//			 String mailPassword=(String)mailDtailsHT.get("Mail_Password");
			//			 String mailMessage=(String)mailDtailsHT.get("Mail_Message");
			Random rd = new Random();
			RandomString rs = new RandomString(16, rd);
			String userKey=rs.nextString();
			postMail(userMailAddress, "Test", userKey, mailSendBy, mailPassword, null, hierlink4Mail);
			emptyfield();

		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}


	}



	public void emptyfield(){

		try {

			userEmailId="";
			emailPassword="";
			cfmPassword="";
			cmpyName="";
			newcustomerBoxVAL=false;
			superPrivilegeVAL=false;
			activeBoxVAL=false;
			disableBoxVAL=false;



		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}


	}


	public void writeregistr_TO_XML() {
		try {

			PropUtil prop = new PropUtil();
			String customerXmlFile = prop.getProperty("CUSTOMER_DETAILS_XML_FILE");
			HIERARCHY_XML_DIR = prop.getProperty("HIERARCHY_XML_DIR");		
			boolean fileexists = Globals.isFileExists(HIERARCHY_XML_DIR,customerXmlFile);
			Document doc = null;
			FileInputStream fis = null;
			FileOutputStream fos = null;
			DocumentBuilderFactory dbf = null;
			DocumentBuilder db = null;


			if (!fileexists) {

				System.out.println("XML File does not exist. Creating New One.");

				dbf = DocumentBuilderFactory.newInstance();
				db = dbf.newDocumentBuilder();
				doc = db.newDocument();
				String root1 = "Customers";
				Element rootElement1 = doc.createElement(root1);
				doc.appendChild(rootElement1);

				TransformerFactory transformerFactory = TransformerFactory
						.newInstance();
				Transformer transformer = transformerFactory.newTransformer();
				transformer.setOutputProperty(OutputKeys.INDENT, "yes");
				DOMSource source = new DOMSource(doc);
				fos = new FileOutputStream(HIERARCHY_XML_DIR
						+ customerXmlFile);
				StreamResult result = new StreamResult(fos);
				transformer.transform(source, result);
				result.getOutputStream().flush();
				result.getOutputStream().close();
				System.out.println("XML File was Created."); 

			}

			doc = Globals.openXMLFile(HIERARCHY_XML_DIR, customerXmlFile);

			NodeList root = doc.getElementsByTagName("Customers");
			Node firstRootNode = root.item(0);

			Element childNode = doc.createElement("Customer");
			firstRootNode.appendChild(childNode);
			childNode.setAttribute("CompanyName", cmpyName);
			childNode.setAttribute("LoginID", userEmailId);
			childNode.setAttribute("password", Inventory.store(emailPassword));
			childNode.setAttribute("CustomerKey", CustomerKey);

			Globals.writeXMLFile(doc, HIERARCHY_XML_DIR, customerXmlFile);
			//red[]=userEmailId;




		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}


	public void postMail(String recipients[], String subject, String message,
			String from, String emailPassword, String[] files,String hostNameWthPrtNum) throws MessagingException, UnsupportedEncodingException { //code change Jayaramu 03FEB14
		System.out.println("Entering: " + new Exception().getStackTrace()[0].getClassName() + "."
				+ new Exception().getStackTrace()[0].getMethodName());
		boolean debug = false;
		//java.security.Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider()); // ?? Commented - Devan

		String HOST_NAME = "smtp.cygnussoftwares.com";
		String messageBody;

		//Set the host smtp address
		Properties props = new Properties();
		
		props.put("mail.transport.protocol", "smtp");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", HOST_NAME);
		props.put("mail.smtp.port", "2525");
		props.put("mail.smtp.auth", "true");	        
		props.put("mail.smtp.ssl.trust", HOST_NAME);
		SMTPAuthenticator authenticator = new SMTPAuthenticator(from, emailPassword);
		Session session = Session.getDefaultInstance(props, authenticator);

		InternetHeaders headers = new InternetHeaders();

		MimeBodyPart nn = new MimeBodyPart();
		nn.setText("","UTF-8","html");
		session.setDebug(debug);

		// create a message
		Message msg = new MimeMessage(session);

		// set the from and to address
		InternetAddress addressFrom = new InternetAddress(from);
		msg.setFrom(addressFrom);

		InternetAddress[] addressTo = new InternetAddress[recipients.length];
		for (int i = 0; i < recipients.length; i++) {
			addressTo[i] = new InternetAddress(recipients[i]);
			System.out.println("recipients[i]========================>>>>>>>>>>>>>>"+recipients[i]);
		}
		msg.setRecipients(Message.RecipientType.TO, addressTo);

		// Setting the Subject and Content Type
		msg.setSubject(subject);
		msg.setContent(message, "text/html");

		//	        headers.addHeader("Content-type", "text/html; charset=UTF-8");
		//	        String html = "Test\n" + "Click Here" + "\n<a href='http://test.com'>Test.com</a>";
		//	        
		MimeBodyPart messageBodyPart = new MimeBodyPart();
		message = message+"<br/>"+ "<a href=\""+hostNameWthPrtNum+"\">Please login in the Heirarchy Genarator to review the Workflow details.</a> <br/><br/><br/>"+"Regards,<br/>"+
				"accel-BI Team";
		messageBodyPart.setText(message,"UTF-8","html");

		Multipart multipart = new MimeMultipart();

		//add the message body to the mime message
		multipart.addBodyPart(messageBodyPart);

		// add any file attachments to the message
		if(files!=null)
		{
			addAtachments(files, multipart);	
		}

		//Put all message parts in the message
		msg.setContent(multipart);
		Transport.send(msg);
		System.out.println("*******************Mail sent sucessfully*******************");
		System.out.println("Exting: " + new Exception().getStackTrace()[0].getClassName() + "."
				+ new Exception().getStackTrace()[0].getMethodName());
	}
	public class SMTPAuthenticator extends javax.mail.Authenticator {

		String username;
		String password;

		private SMTPAuthenticator(String authenticationUser, String authenticationPassword) {
			username = authenticationUser;
			password = authenticationPassword;
		}

		@Override
		public PasswordAuthentication getPasswordAuthentication() {

			return new PasswordAuthentication(username, password);
		}
	}

	public  void addAtachments(String[] attachments, Multipart multipart)
			throws MessagingException, AddressException {
		for (int i = 0; i <= attachments.length - 1; i++) {
			String filename = attachments[i];
			MimeBodyPart attachmentBodyPart = new MimeBodyPart();
			//use a JAF FileDataSource as it does MIME type detection
			DataSource source = new FileDataSource(filename);
			attachmentBodyPart.setDataHandler(new DataHandler(source));
			attachmentBodyPart.setFileName(filename);
			//add the attachment
			multipart.addBodyPart(attachmentBodyPart);
		}
	}








	private void sendMail(String mailId,String userKey) {
		// TODO Auto-generated method stub
		try {
			String to = mailId;//change accordingly  
			String from = "vijay@cygnussoftwares.com";//change accordingly  
			String host = "localhost";//or IP address  

			//Get the session object  
			Properties properties = System.getProperties();  
			properties.setProperty("mail.smtp.host", host);  
			Session session = Session.getDefaultInstance(properties);  

			//compose the message  
			try{  
				MimeMessage message = new MimeMessage(session);  
				message.setFrom(new InternetAddress(from));  
				message.addRecipient(Message.RecipientType.TO,new InternetAddress(to));  
				message.setSubject("Testing");  
				message.setText("Your Registeration is Successfull.\n Your Key is"+userKey+".");  

				// Send message  
				Transport.send(message);  
				System.out.println("message sent successfully....");  

			}catch (MessagingException mex) {mex.printStackTrace();} 
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}




	////////////////////////////////////////////////////////////////////////////////////////////////////
	public String getUserEmailId() {
		return userEmailId;
	}
	public void setUserEmailId(String userEmailId) {
		this.userEmailId = userEmailId;
	}
	public String getEmailPassword() {
		return emailPassword;
	}
	public void setEmailPassword(String emailPassword) {
		this.emailPassword = emailPassword;
	}
	public String getCfmPassword() {
		return cfmPassword;
	}
	public void setCfmPassword(String cfmPassword) {
		this.cfmPassword = cfmPassword;
	}
	public String getCmpyName() {
		return cmpyName;
	}


	public void setCmpyName(String cmpyName) {
		this.cmpyName = cmpyName;
	}
	public String getMsgLbl() {
		return msgLbl;
	}

	public void setMsgLbl(String msgLbl) {
		this.msgLbl = msgLbl;
	}

	public boolean isNewcustomerBoxVAL() {
		return newcustomerBoxVAL;
	}



	public void setNewcustomerBoxVAL(boolean newcustomerBoxVAL) {
		this.newcustomerBoxVAL = newcustomerBoxVAL;
	}




}
