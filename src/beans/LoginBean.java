package beans;

import java.io.*;
import java.lang.ProcessBuilder.Redirect;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;
import java.util.Map;

import javax.faces.FacesException;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.swing.JOptionPane;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import managers.CatalogService;
import managers.WorkflowManager;
import utils.Globals;
import utils.Inventory;
import utils.PropUtil;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.novell.ldap.LDAPConnection;

@ManagedBean(name = "loginBean")
@SessionScoped
public class LoginBean {

	String username;
	String password;
	String frgtusername;
	String frgtMsgstr;
	String frgtMsgColor;


	
	int uniqueID;
	public int screenHeight;
	public int screenWidth;
	private String customerKey = "";
	private String loginprvilageKey = "";


	public String getLoginprvilageKey() {
		return loginprvilageKey;
	}

	public void setLoginprvilageKey(String loginprvilageKey) {
		this.loginprvilageKey = loginprvilageKey;
	}

	String hierarchyID4Users="";  // code change Menaka 21MAR2014

	java.sql.Timestamp useraccsDate;
	
	String changepwdText;
	
	String changepwd2Text;
	String changeuserLabelstr;
	String changepwdMsgColor;
	String changepwdMsgstr;
	
	private boolean spadminstr=false;
	
	
	String oldpwdSTR;
	public String getOldpwdSTR() {
		return oldpwdSTR;
	}

	public void setOldpwdSTR(String oldpwdSTR) {
		this.oldpwdSTR = oldpwdSTR;
	}
	
	public String getFrgtMsgColor() {
		return frgtMsgColor;
	}

	public void setFrgtMsgColor(String frgtMsgColor) {
		this.frgtMsgColor = frgtMsgColor;
	}

	public String getFrgtMsgstr() {
		return frgtMsgstr;
	}
	public boolean isSpadminstr() {
		return spadminstr;
	}

	public void setSpadminstr(boolean spadminstr) {
		this.spadminstr = spadminstr;
	}

	public void setFrgtMsgstr(String frgtMsgstr) {
		this.frgtMsgstr = frgtMsgstr;
	}
	public String getChangepwdText() {
		return changepwdText;
	}

	public void setChangepwdText(String changepwdText) {
		this.changepwdText = changepwdText;
	}

	public String getChangepwd2Text() {
		return changepwd2Text;
	}

	public void setChangepwd2Text(String changepwd2Text) {
		this.changepwd2Text = changepwd2Text;
	}

	public String getChangeuserLabelstr() {
		return changeuserLabelstr;
	}

	public void setChangeuserLabelstr(String changeuserLabelstr) {
		this.changeuserLabelstr = changeuserLabelstr;
	}

	public String getChangepwdMsgColor() {
		return changepwdMsgColor;
	}

	public void setChangepwdMsgColor(String changepwdMsgColor) {
		this.changepwdMsgColor = changepwdMsgColor;
	}

	public String getChangepwdMsgstr() {
		return changepwdMsgstr;
	}

	public void setChangepwdMsgstr(String changepwdMsgstr) {
		this.changepwdMsgstr = changepwdMsgstr;
	}

	


	public String getFrgtusername() {
		return frgtusername;
	}

	public void setFrgtusername(String frgtusername) {
		this.frgtusername = frgtusername;
	}

	public java.sql.Timestamp getUseraccsDate() {
		return useraccsDate;
	}

	public void setUseraccsDate(java.sql.Timestamp useraccsDate) {
		this.useraccsDate = useraccsDate;
	}

	public String getHierarchyID4Users() {
		return hierarchyID4Users;
	}

	public void setHierarchyID4Users(String hierarchyID4Users) {
		this.hierarchyID4Users = hierarchyID4Users;
	}

	public int getUniqueID() {
		return uniqueID;
	}

	public void setUniqueID(int uniqueID) {
		this.uniqueID = uniqueID;
	}

	public String httpSessionID;

	public String getHttpSessionID() {
		return httpSessionID;
	}

	public void setHttpSessionID(String httpSessionID) {
		this.httpSessionID = httpSessionID;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	public int getScreenHeight() {
		return screenHeight;
	}

	public void setScreenHeight(int screenHeight) {
		this.screenHeight = screenHeight;
	}

	public int getScreenWidth() {
		return screenWidth;
	}

	public void setScreenWidth(int screenWidth) {
		this.screenWidth = screenWidth;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	//start code change Vishnu 22Jan2014
	public String msg="";



	public String getMsg() {

		return msg;
	}

	public void setMsg(String msg) {

		this.msg = msg;

	}


	Hashtable loginDetHT=new Hashtable();   // code change Menaka 14MAR2014


	public Hashtable getLoginDetHT() {
		return loginDetHT;
	}

	public void setLoginDetHT(Hashtable loginDetHT) {
		this.loginDetHT = loginDetHT;
	}

	public void validation4newpopup() {
		try {

			//			System.out.println("Hello KISHOR");
			//			String url = FacesContext.getCurrentInstance().getExternalContext().encodeActionURL(FacesContext.getCurrentInstance().getApplication()
			//					.getViewHandler().getActionURL(FacesContext.getCurrentInstance(), "/" + "NewRegisteration.xhtml"));
			//					FacesContext.getCurrentInstance().getExternalContext().redirect(url);






		}catch(Exception e) {
			e.printStackTrace();
		}

	}


	public void calpage() {


		System.out.println("Entering: page ");
		String url = FacesContext.getCurrentInstance().getExternalContext().encodeActionURL(FacesContext.getCurrentInstance().getApplication()
				.getViewHandler().getActionURL(FacesContext.getCurrentInstance(), "/" + "HierarchyList.xhtml"));



		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect(url);


		}catch(Exception e) {
			e.printStackTrace();
		}

	}

	public String getCustomerKey() {
		return customerKey;
	}

	public void setCustomerKey(String customerKey) {
		this.customerKey = customerKey;
	}
	
	
	public void calchangepwdpopup() {


		try {
			System.out.println("changeuserLabelstr :"+changeuserLabelstr  +"username :" +username);

			setChangeuserLabelstr(username); 

		}	catch (Exception e) {
			// TODO: handle exception
		}
	}

	
public void saveChangePWD() {
		
		try {
			changepwdMsgstr="";
			changepwdMsgColor="blue";
			System.out.println("changepwdText :"+changepwdText  +"changepwd2Text :" +changepwd2Text);
			
			if(changeuserLabelstr==null || changeuserLabelstr.equals("")) {
				
				changepwdMsgstr="Please fill all the fields.";
				changepwdMsgColor="red";
				return;
			}
		
			if(oldpwdSTR==null || oldpwdSTR.equals("")) {
				
				changepwdMsgstr="Please fill all the fields.";
				changepwdMsgColor="red";
				return;
			}
			
			if(changepwdText == null || changepwdText.equals("") && changepwd2Text==null || changepwd2Text.equals("") ) {
				System.out.println("**** :"+changepwdText  +"**** :" +changepwd2Text);
				changepwdMsgstr="Please fill all thefields.";
				changepwdMsgColor="red";
				return;
			}
			
			
			if(!changepwdText.isEmpty() && !changepwd2Text.isEmpty()) {
				
				if(!changepwdText.equals(changepwd2Text)) {
				changepwdMsgstr="Password is not match, please enter your correct password.";
				changepwdMsgColor="red";
				return;
			}
			
			}
			
			changepassword2XML();
			
		}
		catch (Exception e) {
			// TODO: handle exception
		}
	}
	
public void changepassword2XML() {
	try {
	
	PropUtil prop = new PropUtil();
	String loginXmlFile = prop.getProperty("LOGIN_XML_FILE");
	String HIERARCHY_XML_DIR = prop.getProperty("HIERARCHY_XML_DIR");	
	Document doc = Globals.openXMLFile(HIERARCHY_XML_DIR, loginXmlFile);
	Node dlNode=Globals.getNodeByAttrVal(doc, "User", "Login_ID", username);
	String accessStartDte = "";
	String accessEndDte = "";
	if(dlNode.getNodeType() == Node.ELEMENT_NODE){
		
	String dateFormat = prop.getProperty("DATE_FORMAT");
	SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
	String newPass=Inventory.store(String.valueOf(oldpwdSTR).toString());
	Element childNode = (Element)dlNode;
	String oldPass=(String)childNode.getAttribute("User_Password");
	
	System.out.println(newPass+" ----NewPassWord-----------OldPassWord----- "+oldPass);
	if(newPass.equals(oldPass)) {
		childNode.setAttribute("User_Password",Inventory.store(String.valueOf(changepwdText)));
	}
	else {
		changepwdMsgstr="You have entered the wrong password. Please enter the valid password.";
		changepwdMsgColor="red";
		return;
	}
	Globals.writeXMLFile(doc, HIERARCHY_XML_DIR, loginXmlFile);
	changepwdMsgstr="Your password is changed successfully.";
	changepwdMsgColor="blue";
	
	}		
		
	}catch(Exception e) {
		e.printStackTrace();
	}
	
}


	public void emptyMsglabl() {
		
		try {
			
			changepwdMsgstr="";
			changepwdMsgColor="";
		}
		catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public String validation() {
		try {

			System.out.println("Entering: " + new Exception().getStackTrace()[0].getClassName() + "."
					+ new Exception().getStackTrace()[0].getMethodName());

			FacesContext ftx=FacesContext.getCurrentInstance();
			ExternalContext extContext = ftx.getExternalContext();
			Map sessionMap = extContext.getSessionMap();
			ExceptionBean eb = (ExceptionBean) sessionMap.get("exceptionBean");
			String message="";
			if(eb!=null)
				//			eb.msg="";

				if(username==null||username.equals("")){
					//				eb.msg="Enter the UserName";
					FacesContext
					.getCurrentInstance()
					.addMessage(
							null,
							new FacesMessage(
									FacesMessage.SEVERITY_ERROR,
									"Enter the user name.",
									"Enter the user name."));
					return "";
				}
			if(password==null||password.equals("")){

				FacesContext
				.getCurrentInstance()
				.addMessage(
						null,
						new FacesMessage(
								FacesMessage.SEVERITY_ERROR,
								"Enter the Password.",
								"Enter the Password."));
				return "";
			}



			//			System.out.println("hib.memberloginID=============>>>"+hib.memberloginID);


			boolean renderFlag4StatusMsg;
			PropUtil prop = new PropUtil();
			String HIERARCHY_XML_DIR = "";
			Hashtable readLoginHT = new Hashtable<>();
			Hashtable usernameHT = new Hashtable<>();
			Hashtable passWordHT = new Hashtable<>();

			String loginID="",loginDate="";  // code change Menaka 14MAR2014


			HIERARCHY_XML_DIR = prop.getProperty("HIERARCHY_XML_DIR");

			readLoginHT = Globals.readSecurityxml(HIERARCHY_XML_DIR);

			if(String.valueOf(readLoginHT.get("status")).equalsIgnoreCase("Error")){
				//				msg="Entered path is wrong";
				FacesContext
				.getCurrentInstance()
				.addMessage(
						null,
						new FacesMessage(
								FacesMessage.SEVERITY_ERROR,
								"Missing system configuration files. Please contact your Administrator.",
								"Missing system configuration files. Please contact your Administrator."));
				return "";
			}

			String format=prop.getProperty("DATE_FORMAT");
			SimpleDateFormat formatter = new SimpleDateFormat(format);
			Hashtable HT = new Hashtable<>();
			String loginStatus = WorkflowManager.checkLogin(username, password, HT);
			if(loginStatus.equalsIgnoreCase("Success")) {  // Validating User name & Password

				String startDate=(String) HT.get("Access_Start_Date");
				String endDate=(String) HT.get("Access_End_Date");
				String customerKey = (String) HT.get("CustomerKey");
				this.customerKey = customerKey;
				boolean enabledDuration=false;
				Date currentDate=new Date();


				Date stdate=null,eddate = null;
				String accessType="";

				if(startDate!=null&&!startDate.equals("")){
					stdate = formatter.parse(startDate);
				}

				if(endDate!=null&&!endDate.equals("")){
					eddate = formatter.parse(endDate);
				}

				accessType=(String) HT.get("Access_Type");

//				if(stdate==null||eddate==null||accessType==null||accessType.equals("")){
//
//					System.out.println("********Login Faild----- Login access is not updated**********");
//
//					//									eb.msg="Access Denied (Access date null)";
//
//					FacesContext
//					.getCurrentInstance()
//					.addMessage(
//							null,
//							new FacesMessage(
//									FacesMessage.SEVERITY_ERROR,
//									"Access denied (access date is null).",
//									"Access denied (access date is null)."));
//
//					//									return "";
//
//					//					break;
//
//				}else{
//
//					if(accessType.equals("Enabled")&&(currentDate.compareTo(stdate)>=0&&currentDate.compareTo(eddate)<=0)){
//					}else{
//						System.out.println("********Error: Login failed – Your Login has expired or disabled. Please contact your Administrator.**********");
//
//						//										eb.msg="Login Faild since access time may exceed/Disabled";
//
//						FacesContext
//						.getCurrentInstance()
//						.addMessage(
//								null,
//								new FacesMessage(
//										FacesMessage.SEVERITY_ERROR,
//										"Error: Login failed – Your Login has expired or disabled. Please contact your Administrator.",
//										"Error: Login failed – Your Login has expired or disabled. Please contact your Administrator."));
//
//						//										return "";
//						//						break;
//					}
//
//				}

				String privillage=	(String) HT.get("Super_Privilege_Admin");



				SecurityBean secbn= (SecurityBean) sessionMap.get("securityBean");
				HierarchyBean hbn= (HierarchyBean) sessionMap.get("hierarchyBean");
				if(hbn != null)
				hbn.userNameID=username;
				
				if(secbn==null){
					secbn=new SecurityBean();
				}



				//							SecurityBean secbn = new SecurityBean();
				
				if(privillage.equalsIgnoreCase("true")){

					
					secbn.setAlliconObject("SuperPrivilegeAdmin",secbn);  // code change Menaka 24MAR2014
					System.out.println("Enabled All icons because the member is Admin");	

				}else{
					secbn.setAlliconObject("AllDisable",secbn);  // code change Menaka 24MAR2014
					System.out.println("Disabled All icons because the member is AllDisable");

				}

				message="Connection Success";
				//				break;

			}else {
				System.out.println("-=-=-=-=-=-=-=loginStatus-=-=-=-=-=-=-=-="+loginStatus);
				if(!loginStatus.equalsIgnoreCase("failure"))
					FacesContext
					.getCurrentInstance()
					.addMessage(
							null,
							new FacesMessage(
									FacesMessage.SEVERITY_ERROR,
									loginStatus, loginStatus));
			}




			if(message.equalsIgnoreCase("Connection Success")){
				//				FacesContext.getCurrentInstance().addMessage(
				//						null,
				//						new FacesMessage(FacesMessage.SEVERITY_INFO,
				//								"User Name and Passsword is Correct!!",
				//								"User Name and Passsword is Correct!!"));

				FileInputStream fis = null;
				FileOutputStream fos = null;
				Document doc = null;
				DocumentBuilderFactory dbf = null;
				DocumentBuilder db = null;


				String trackingFileName=prop.getProperty("TRACKING_XML_FILE");

				boolean fileexists = Globals.isFileExists(HIERARCHY_XML_DIR,trackingFileName);

				if (!fileexists) {

					System.out.println("XML File does not exist. Creating New One.");

					dbf = DocumentBuilderFactory.newInstance();
					db = dbf.newDocumentBuilder();
					doc = db.newDocument();
					String root1 = "Login_users";
					Element rootElement1 = doc.createElement(root1);
					doc.appendChild(rootElement1);

					TransformerFactory transformerFactory = TransformerFactory
							.newInstance();
					Transformer transformer = transformerFactory.newTransformer();
					transformer.setOutputProperty(OutputKeys.INDENT, "yes");
					DOMSource source = new DOMSource(doc);
					fos = new FileOutputStream(HIERARCHY_XML_DIR
							+ trackingFileName);
					StreamResult result = new StreamResult(fos);
					transformer.transform(source, result);
					result.getOutputStream().flush();
					result.getOutputStream().close();
					System.out.println("XML File was Created."); 

				}


				fis = new FileInputStream(HIERARCHY_XML_DIR
						+ trackingFileName);
				Element em = null;
				DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory
						.newInstance();
				DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
				doc = docBuilder.parse((fis));

				Node node = doc.getLastChild();  
				Element  rootEm = (Element) node;    

				//				Element em1 = doc.createElement("Login_ID");
				String us = "User";
				Element em1 = doc.createElement(us);

				node.appendChild(em1);
				Date curDate=new Date();
				loginDate=String.valueOf(formatter.format(curDate));


				String hierarchyConfigFile=prop.getProperty("HIERARCHY_CONFIG_FILE");
				uniqueID = Globals.getMaxID(HIERARCHY_XML_DIR, hierarchyConfigFile,"Login_Acces_ID", "ID");
				em1.setAttribute("Unique_ID",String.valueOf(uniqueID));
				em1.setAttribute("Login_Date",loginDate);
				em1.setAttribute("Logout_Date","");
				em1.setAttribute("Time_Spent","");
				em1.setAttribute("User_ID",username);
				// end code change Menaka 13MAR2014	




				//code change pandian 24Aug2013
				///////////// for checking session if session expire go to login screen .jsf
				
				HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);

				if(session!=null){   // its take getExternalContext().getSession(false); session
					javax.servlet.http.HttpSession ses=(javax.servlet.http.HttpSession)session;


					System.out.println("HttpSession session already created");
					System.out.println("session CreationTime : " + ses.getCreationTime());
					System.out.println("session sessionId: " + ses.getId());
					System.out.println("session LastAccessedTime: " + ses.getLastAccessedTime());
					System.out.println("session MaxInactiveInterval: " + ses.getMaxInactiveInterval());
					System.out.println("session isNew: " + ses.isNew());

					setHttpSessionID(ses.getId());
					Hashtable ht=new Hashtable<>();
					ht.put("HTTP_Session_ID", httpSessionID);
					ht.put("Unique_ID", uniqueID);
					loginDetHT.put("username", username);
					loginDetHT.put(username, ht);
					ses.setAttribute("User",loginDetHT );


				}else{  // its take getExternalContext().getSession(false); session

					HttpSession session1 = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
					javax.servlet.http.HttpSession ses1=(javax.servlet.http.HttpSession)session1;

					System.out.println("HttpSession session newly created");
					System.out.println("session CreationTime : " + ses1.getCreationTime());
					System.out.println("session sessionId: " + ses1.getId());
					System.out.println("session LastAccessedTime: " + ses1.getLastAccessedTime());
					System.out.println("session MaxInactiveInterval: " + ses1.getMaxInactiveInterval());
					System.out.println("session isNew: " + ses1.isNew());

					setHttpSessionID(ses1.getId());
					Hashtable ht=new Hashtable<>();
					ht.put("HTTP_Session_ID", httpSessionID);
					ht.put("Unique_ID", uniqueID);
					loginDetHT.put("username", username);
					loginDetHT.put(username, ht);
					ses1.setAttribute("User",loginDetHT );


				}


				String privillage=	(String) HT.get("Super_Privilege_Admin");
				this.loginprvilageKey = privillage;
				String url="";
				System.out.println("privillage :"+privillage);
				if(privillage.equalsIgnoreCase("true")){

					this.loginprvilageKey="true";
					url = FacesContext.getCurrentInstance().getExternalContext().encodeActionURL(FacesContext.getCurrentInstance().getApplication()
							.getViewHandler().getActionURL(FacesContext.getCurrentInstance(), "/" + "HierarchyList.xhtml"));

				}else{
					this.loginprvilageKey="false";
					url = FacesContext.getCurrentInstance().getExternalContext().encodeActionURL(FacesContext.getCurrentInstance().getApplication()
							.getViewHandler().getActionURL(FacesContext.getCurrentInstance(), "/" + "DocumentList.xhtml"));
				}

				
				//				
				//				

				try {
					//start code change Jayaramu 10APR14
					Date access_Date = new Date();
					DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss a");
					String userAccessDate = sdf.format(access_Date);
					Date userDate = new Date(userAccessDate);
					useraccsDate = new java.sql.Timestamp(userDate.getTime()); 
					//End code change Jayaramu 10APR14
					FacesContext.getCurrentInstance().getExternalContext().redirect(url);

				} catch (IOException ioe) {
					throw new FacesException(ioe);
				}

				System.out.println("login Succeeded");
				Globals.writeXMLFile(doc, HIERARCHY_XML_DIR,trackingFileName); //  code change Menaka 13MAR2014	
				renderFlag4StatusMsg=false;
			}else{
				//				FacesContext
				//				.getCurrentInstance()
				//				.addMessage(
				//					null,
				//					new FacesMessage(
				//							FacesMessage.SEVERITY_ERROR,
				//							"The Username or Password you entered is incorrect.",
				//							"The Username or Password you entered is incorrect."));
				System.out.println("Login failed");
				FacesContext
				.getCurrentInstance()
				.addMessage(
						null,
						new FacesMessage(
								FacesMessage.SEVERITY_ERROR,
								"Invalid login ID / password. Retry.",
								"Invalid login ID / password. Retry."));

			}



			if(!message.equalsIgnoreCase("Connection Success")){



				System.out.println("Login failed");
				HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);

				if(session!=null){  // its take getExternalContext().getSession(false); session
					javax.servlet.http.HttpSession ses=(javax.servlet.http.HttpSession)session;


					System.out.println("HttpSession session already created this going to invalidate");
					System.out.println("session CreationTime : " + ses.getCreationTime());
					System.out.println("session sessionId: " + ses.getId());
					System.out.println("session LastAccessedTime: " + ses.getLastAccessedTime());
					System.out.println("session MaxInactiveInterval: " + ses.getMaxInactiveInterval());
					System.out.println("session isNew: " + ses.isNew());

					setHttpSessionID("");
					ses.invalidate();


				}else{  // its take getExternalContext().getSession(false); session

					HttpSession session1 = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
					javax.servlet.http.HttpSession ses1=(javax.servlet.http.HttpSession)session1;

					System.out.println("HttpSession session newly  created this going to invalidate");
					System.out.println("session CreationTime : " + ses1.getCreationTime());
					System.out.println("session sessionId: " + ses1.getId());
					System.out.println("session LastAccessedTime: " + ses1.getLastAccessedTime());
					System.out.println("session MaxInactiveInterval: " + ses1.getMaxInactiveInterval());
					System.out.println("session isNew: " + ses1.isNew());
					setHttpSessionID("");
					ses1.invalidate();
				}


				FacesContext
				.getCurrentInstance()
				.addMessage(
						null,
						new FacesMessage(
								FacesMessage.SEVERITY_ERROR,
								"The user name or password you entered is incorrect.",
								"The user name or password you entered is incorrect."));
			}
			//	}
			////////////////




		} catch (Exception e) {
			e.printStackTrace();

		}


		System.out.println("Exiting: " + new Exception().getStackTrace()[0].getClassName() + "."
				+ new Exception().getStackTrace()[0].getMethodName());

		return "";
	}

	public void calForgetPopup(){ 

		try{

			
			frgtusername=username;
			
			System.out.println("frgtusername: "+frgtusername);




		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	public void saveForgetPopup(){ 

		try{

			
			if(frgtusername==null||frgtusername.equals("")){
				
				frgtMsgstr="Please enter the E mail ID";
				frgtMsgColor="red";
				return;
			}

			frgtMsgstr="Send Email success,get link in ur email";
			frgtMsgColor="blue";

		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	public void emptyMsg(){ 

		try{


			frgtMsgstr="";
			frgtMsgColor="blue";

		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public void forgetPassword(String username) {
		if(username == null || username.trim().isEmpty()) {
			FacesContext
			.getCurrentInstance()
			.addMessage(
					null,
					new FacesMessage(
							FacesMessage.SEVERITY_ERROR,
							"Please enter the email id.",
							"Please enter the email id."));
			return;
		}
		boolean flag;
		try {
			flag = WorkflowManager.checkUserisAvailable(username);
			if(!flag) {
				FacesContext
				.getCurrentInstance()
				.addMessage(
						null,
						new FacesMessage(
								FacesMessage.SEVERITY_ERROR,
								"Entered user id is not a vaild user.",
								"Entered user id is not a vaild user."));
			}
			String message = WorkflowManager.changePasswordMail(username);
			if(!message.equalsIgnoreCase("true")) {
				FacesContext
				.getCurrentInstance()
				.addMessage(
						null,
						new FacesMessage(
								FacesMessage.SEVERITY_ERROR,
								"Entered user id is not a vaild user.",
								"Entered user id is not a vaild user."));
			}
			FacesContext
			.getCurrentInstance()
			.addMessage(
					"Password changed successfully and mailed to your email id.",
					new FacesMessage(
							"Password changed successfully and mailed to your email id.",
							"Password changed successfully and mailed to your email id."));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			FacesContext
			.getCurrentInstance()
			.addMessage(
					null,
					new FacesMessage(
							FacesMessage.SEVERITY_ERROR,
							"Entered user id is not a vaild user.",
							"Entered user id is not a vaild user."));
		}
		
	}



	public void insertDateAndUser2DB(){ //code change Jayaramu 31MAR14

		try{
			System.out.println("Entering: " + new Exception().getStackTrace()[0].getClassName() + "."

		     + new Exception().getStackTrace()[0].getMethodName());
			PropUtil prop = new PropUtil();
			Date access_Date = new Date();
			DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss a");
			String userAccessDate = sdf.format(access_Date);
			Date userDate = new Date(userAccessDate);
			String action_By = username;
			java.sql.Timestamp useraccsDate = new java.sql.Timestamp(userDate.getTime()); 
			//		  System.out.println("useraccsDate====>>>"+useraccsDate);
			Connection connection = Globals.getDBConnection("DW_Connection");
			String insertSql = "INSERT INTO WORK_FLOW_ACTIVITY (LOGIN_ID,LOGIN_DATE_TIME,ACTION_BY) VALUES (?,?,?)";
			//		  System.out.println("insertSql====>>>"+insertSql);
			System.out.println("User Login Details For UPDATE DB TABLE : uniqueID "+uniqueID+" useraccsDate "+useraccsDate+" action_By "+action_By);
			PreparedStatement ps=connection.prepareStatement(insertSql);
			ps.setInt(1, uniqueID);
			ps.setTimestamp(2, useraccsDate);
			ps.setString(3, action_By);
			ps.execute();
			ps.close();

		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		System.out.println("Exiting: " + new Exception().getStackTrace()[0].getClassName() + "."
				+ new Exception().getStackTrace()[0].getMethodName());
	}



}
