package beans;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.security.GeneralSecurityException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import javax.swing.JOptionPane;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import managers.GoogleStorageMaintenace;
import managers.LoginProcessManager;
import managers.OneDriveFileManipulation;
import managers.RulesManager;
import managers.dashboardManager;
import model.RuleAttributeDataModel;
import model.RuleDataModel;
import test.Authorizer;

import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.primefaces.event.DashboardReorderEvent;
import org.primefaces.model.DashboardColumn;
import org.primefaces.model.DashboardModel;
import org.primefaces.model.DefaultDashboardColumn;
import org.primefaces.model.DefaultDashboardModel;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import org.richfaces.event.FileUploadEvent;
import org.richfaces.model.UploadedFile;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.drive.model.User;
import com.jniwrapper.Int;
import com.jniwrapper.win32.jexcel.el;

import POI.ExcelProcess;
import POI.ExcelReadUsingPOI;
import POI.LoadDataTableProcess;
import utils.Globals;
import utils.Inventory;
import utils.PropUtil;

@ManagedBean(name = "dashboardBean")
@SessionScoped
public class DashboardBean implements Serializable {  

	private DashboardModel model; 
	private DashboardModel modelold; 
	private DashboardModel stageModel; 
	private TreeNode stageRoot;  
	private String rbid_metadata_dir = "";
	public String BIMetadataFile = null;

	private String msg4WF="";  // code change Menaka 28MAR2014

	private String filterVal;
	private String filterVal1;

	private String fileuploadstatusStr;

	//ArrayList membersNameAL = new ArrayList<>();
	//private ArrayList allMembersNameAL = new ArrayList<>();

	private String selectionType="";
	String propertiesType = "";
	String minApproveValue = "";
	String hierID="";
	private String hierName = "";
	private String folderType = "";
	private String folderPath = "";
	private DashboardModel custommodel;
	String setStageMsg = "";
	String setStageMsgcolor= "";
	String authorizeMsgstr="";

	UploadedFile file;
	private boolean useFlash = false;

	private boolean autologinbool;
	private boolean allowuploadbool;
	private boolean enableEsign;
	private boolean allowEditUD;


	private String externalfilename; 

	private String bi_metadata_dir;

	
	private String workflowName;
	private String workflowID;
	private String editwrkflowflag="";
	private String pureEditModeURL="false";
	public String getPureEditModeURL() {
		return pureEditModeURL;
	}





	public void setPureEditModeURL(String pureEditModeURL) {
		this.pureEditModeURL = pureEditModeURL;
	}

	private boolean ischangedStage=false;
	private boolean ischangedStage1=false;
	private String message4SetStage="";
	private String message4SetStageColor="";


	private String message4MemberSetStage="";
	private String message4MemberSetColor="";

	

	private String rejectedReturn2 = "";
	private String rejectedSetStatus2 = "";
	private String rejectedSendNotification2 = "";
	private String passSendNotification2="";
	private String externalUserName = "";
	private String externalAuthURL = "";
	private String developerKey = "";
	private String accessToken = "";
	private String folderID = "";
	private String showhandleTitleBox= "none";
	private String  authorizeMsgColor="";

	private String returnRejectedMessage = "";
	private String returnRejectedMessagecolor="";
	

	private String returnPassMessage;
	private String returnPassMessagecolor;
	
	private String selectedStatus = "";
	private String selectedgotoStages="";
	

	private boolean showRejectedButton = false;
	private boolean showPassButton = false;
	public boolean saveRejectedFlag = false;
	public boolean savePauseFlag = false;

	
	

	String stageNameFromAddStage="";
	String stageNumberFromAddStage="";
	private String cusStageNameValue = "EnterStage";
	private String tapName = "Default";
	private String parallelType = "Members";  // code change Menaka 19APR2014
	private String minlablVAL="Minimum member count: ";
	private String minlablVALpercent="false";


	String stageNameValue  =  "Enter_Stage_Name";
	String stageNameValue1  =  "s1";	

	String wfAdminValidationmsg = "";
	ArrayList stageRoleAL = new ArrayList<>();
	ArrayList selectedStatusCodesAL = new ArrayList<>();
	ArrayList membersNameAL = new ArrayList<>();
	private ArrayList allMembersNameAL = new ArrayList<>();


	ArrayList statusCodesAL = new ArrayList<>();
	String[] selectedMembers;
	String[] frwdMembers;

	ArrayList selectedMembersAL = new ArrayList<>();

	String[] selectedStatusCodes;
	String[] frwdStatusCodes;

	String selectedStageRole = "";
	String selectedMember = "";
	
	
	
	
	private ArrayList panelAL=new ArrayList<>();
	private ArrayList panelModelAL=new ArrayList<>();
	
	private ArrayList SMsgAL=new ArrayList<>();
	private ArrayList empNamesAL=new ArrayList<>();
	private String tempValue="";
	private String usercreateStageNumb="999";
	private String hierarchyConfigFile="Hierarchy_Config.xml";
	String uniqueid="";
	private String selectedAttributeType = "";
	private String selectedAttributeDataType = "";
	private String selectedAttribute = "";
	private ArrayList<String> attributes = new ArrayList<>();
	private Hashtable<String, String> attrDataTypeHT = new Hashtable<>();
	private Part uploadedFile;
	private ArrayList<RuleAttributeDataModel> ruleAttrAL = new ArrayList<>();
	private ArrayList<RuleDataModel> rulesAL = new ArrayList<>();
	private ArrayList<RuleDataModel> selectedrulesAL = new ArrayList<>();
	private ArrayList<RuleAttributeDataModel> rulesALTemp = new ArrayList<>();
	private Hashtable<String, String> ruleOperHT = new Hashtable<>();
	private Hashtable<String, String> defaultAttrsHT = new Hashtable<>();
	private boolean externalUserFlag = false;
	private boolean sendPrimaryFileOnlyFlag = false; 
	private boolean statusCode4DocsFlag = false;
	private boolean changeprimarydocFlag = false;
	
		
	private String createNewuserMessage = "";  
	private String createNewusercolor = "";
	private String urlParameter = "";
	private String urlLogin = "";
	private String urlWfhandshkeID = "";
	private String urlcus_key = "";
	private String urlCompanyName = "";
	private String closeDisplay="block";
	private String closeDisplayADV="none";
	private String dashboardexpire="";
	

	

	public ArrayList<UltraDataBean> crateuserDetailsAL = new ArrayList<UltraDataBean>();
	public static final Pattern VALID_EMAIL_ADDRESS_REGEX = 
		    Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
	
	private String oneDriveHTxt = "";
	private String authText = "";
	private String oneDriveMailID = "";
	private String oneDriveAuthURL = "";
	private String authMsg = "";
	//	private String[] valuesArr = null;
	String rejectedPrevStage_Number="";
	public DashboardBean() throws Exception {
		System.out.println("**********DASHBOARDBEAN************:");
		try {
		attributes.clear();
		
		rulesAL.clear();
		selectedrulesAL.clear();
		String[] attrStr = {"Today", "Year", "Month", "Weekend", "Team Assigned Date", "Team Due Date", "Member Email ID", "Team Status", "Member Count", "Member Name", "Vendor ID", "Custom"};
		attrDataTypeHT.clear();
		attrDataTypeHT.put("Text", "Text");
		attrDataTypeHT.put("Number", "Number");
		attrDataTypeHT.put("Currency", "Currency");
		attrDataTypeHT.put("Date", "Date");


		ruleOperHT.put("Equals", "equals");
		ruleOperHT.put("Not Equals", "notequals");
		ruleOperHT.put("Greater Than", "greaterthan");
		ruleOperHT.put("Greater Than and Equals", "greaterthanequals");
		ruleOperHT.put("Lesser Than", "lesserthan");
		ruleOperHT.put("Lesser Than and Equals", "lesserthanequals");
		ruleOperHT.put("Between", "between");
		ruleOperHT.put("Is Empty", "isempty");
		
		 
		getDashboardexpire();
			
			
			
					
		for(String attr : attrStr) {
			defaultAttrsHT.put(attr, attr);
		}
		this.msg4WF="";
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	
	
	
	
	public String getDashboardexpire(){
		
		try {
		
		HttpServletRequest req = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
		this.urlParameter=req.getParameter("advanceWorkflow")== null || req.getParameter("advanceWorkflow").length()<= 0 ? "false" :req.getParameter("advanceWorkflow");
		String localurlLogin=req.getParameter("emailid")== null || req.getParameter("emailid").length()<= 0 ? "" :req.getParameter("emailid");
		String localurlWfhandshkeID=req.getParameter("hand_shake_key")== null || req.getParameter("hand_shake_key").length()<= 0 ? "" :req.getParameter("hand_shake_key");
		String wrkflowType=req.getParameter("workflowType")== null || req.getParameter("workflowType").length()<= 0 ? "Default" :req.getParameter("workflowType");
		workflowName=req.getParameter("hirarchyName")== null || req.getParameter("hirarchyName").length()<= 0 ? "" :req.getParameter("hirarchyName");
		workflowID=req.getParameter("workflowID")== null || req.getParameter("workflowID").length()<= 0 ? "" :req.getParameter("workflowID");
		editwrkflowflag=req.getParameter("editAdvanceWorkflow")== null || req.getParameter("editAdvanceWorkflow").length()<= 0 ? "false" :req.getParameter("editAdvanceWorkflow");
		
		System.out.println("*******editwrkflowflag**********editwrkflowflag********"+editwrkflowflag);
		System.out.println("*******workflowName**********workflowName********"+workflowName);
		System.out.println("*******workflowID**********workflowID********"+workflowID);
		
		System.out.println("*******urlParameter**********urlParameter********"+urlParameter);
		System.out.println("*******wrkflowType**********wrkflowType********"+wrkflowType);
		System.out.println("*******localurlWfhandshkeID**********localurlWfhandshkeID********"+localurlWfhandshkeID);
		
		if(editwrkflowflag.toLowerCase().equals("true")) {
			System.out.println(workflowID+"**********urlParameter***111111111*********:"+workflowName+"<====workflowName==>"+urlParameter);
			//saveASPlogindetails(localurlLogin);
			this.urlLogin=localurlLogin;
			pureEditModeURL="true";
			this.urlWfhandshkeID=localurlWfhandshkeID;
			ArrayList<String> detailsAL = getcustomerkey(localurlLogin);
			this.urlcus_key=detailsAL != null && detailsAL.size() == 2 ? detailsAL.get(0) : "";
			this.urlCompanyName = detailsAL != null && detailsAL.size() == 2 ? detailsAL.get(1) : "";
			DashboardStage(wrkflowType, "hierarchyTree", workflowID, workflowName);
			this.closeDisplay="none";
			this.closeDisplayADV="block";
		}else {
			if(this.urlParameter.equalsIgnoreCase("true")){

				if(this.urlWfhandshkeID !=null && !this.urlWfhandshkeID.isEmpty()) {

					if(this.urlWfhandshkeID.equalsIgnoreCase(localurlWfhandshkeID)) {

					}else {
						saveASPlogindetails(localurlLogin);
						this.urlLogin=localurlLogin;
						this.urlWfhandshkeID=localurlWfhandshkeID;
						ArrayList<String> detailsAL = getcustomerkey(localurlLogin);
						this.urlcus_key=detailsAL != null && detailsAL.size() == 2 ? detailsAL.get(0) : "";
						this.urlCompanyName = detailsAL != null && detailsAL.size() == 2 ? detailsAL.get(1) : "";
						this.closeDisplay="none";
						this.closeDisplayADV="block";
						createNewWF();

					}

				}else {

					saveASPlogindetails(localurlLogin);
					this.urlLogin=localurlLogin;
					this.urlWfhandshkeID=localurlWfhandshkeID;
					ArrayList<String> detailsAL = getcustomerkey(localurlLogin);
					this.urlcus_key=detailsAL != null && detailsAL.size() == 2 ? detailsAL.get(0) : "";
					this.urlCompanyName = detailsAL != null && detailsAL.size() == 2 ? detailsAL.get(1) : "";
					this.closeDisplay="none";
					this.closeDisplayADV="block";
					createNewWF();
				}

			}else {


				this.closeDisplay="block";
				this.closeDisplayADV="none";
				FacesContext ctx1 = FacesContext.getCurrentInstance();
				ExternalContext extContext1 = ctx1.getExternalContext();
				Map sessionMap1 = extContext1.getSessionMap();
				LoginBean lgB = (LoginBean) sessionMap1.get("loginBean");
				String userLoginName=lgB.getUsername();
				ArrayList<String> detailsAL = getcustomerkey(userLoginName);
				this.urlcus_key=detailsAL != null && detailsAL.size() == 2 ? detailsAL.get(0) : "";
				this.urlCompanyName = detailsAL != null && detailsAL.size() == 2 ? detailsAL.get(1) : "";
			}
		}
		
		
		
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		
		return dashboardexpire;
	}
	
	
	
	
		public void saveASPlogindetails(String UltraMailID) {
			
			String customerkey="";
			String userUWFPassword="";
			String userUWFfname="";
			String userUWFlname="";
			
			try {
				
			  if(UltraMailID.equals("")||UltraMailID.isEmpty()) {
					
				 return;
				 
				}else {

				PropUtil prop = new PropUtil();
				String adminXMLPath = prop.getProperty("IISUSERADMINFILEPATH");
				String IISUSERADMINFILENAME = prop.getProperty("IISUSERADMINFILENAME");
				Document adminXmlDoc = Globals.openXMLFile(adminXMLPath,IISUSERADMINFILENAME);
				NodeList adminChNdList = adminXmlDoc.getElementsByTagName("Admin");
				
				for(int i=0;i<adminChNdList.getLength();i++) {
					
					if(adminChNdList.item(i).getNodeType() == Node.ELEMENT_NODE) {
					    Element adminEle = (Element)adminChNdList.item(i);
					    NodeList adminsubChNdList = adminXmlDoc.getElementsByTagName("User");
					   for (int j = 0; j <adminsubChNdList.getLength(); j++)
					   {
					    Element adminEle1 = (Element)adminsubChNdList.item(j);
						String userId = adminEle1.getAttribute("UserID");
						
						if(userId.equalsIgnoreCase(UltraMailID)) {
							
						customerkey= adminEle1.getParentNode().getAttributes().getNamedItem("CustomerKey").getNodeValue();
						
						userUWFPassword=adminEle1.getAttribute("Key");
						userUWFfname=adminEle1.getAttribute("FirstName");
						userUWFlname=adminEle1.getAttribute("LastName");
							// break;
						
						}
					        }
					}
				}
				
				
				System.out.println("**************customerkey************"+customerkey);
				System.out.println("**************userUWFPassword************"+userUWFPassword);
				System.out.println("**************userUWFfname************"+userUWFfname);
				System.out.println("**************userUWFlname************"+userUWFlname);
				
				
				String loginXmlFile = prop.getProperty("LOGIN_XML_FILE");
				String	HIERARCHY_XML_DIR = prop.getProperty("HIERARCHY_XML_DIR");		
				boolean fileexists = Globals.isFileExists(HIERARCHY_XML_DIR,loginXmlFile);
				Document doc = null;
				FileInputStream fis = null;
				FileOutputStream fos = null;
				DocumentBuilderFactory dbf = null;
				DocumentBuilder db = null;
				String alredythrMailID="";
				
				if (!fileexists) {

					System.out.println("XML File does not exist. Creating New One.");

					dbf = DocumentBuilderFactory.newInstance();
					db = dbf.newDocumentBuilder();
					doc = db.newDocument();
					String root1 = "Obiee_Users";
					Element rootElement1 = doc.createElement(root1);
					doc.appendChild(rootElement1);

					TransformerFactory transformerFactory = TransformerFactory
							.newInstance();
					Transformer transformer = transformerFactory.newTransformer();
					transformer.setOutputProperty(OutputKeys.INDENT, "yes");
					DOMSource source = new DOMSource(doc);
					fos = new FileOutputStream(HIERARCHY_XML_DIR
							+ loginXmlFile);
					StreamResult result = new StreamResult(fos);
					transformer.transform(source, result);
					result.getOutputStream().flush();
					result.getOutputStream().close();
					System.out.println("XML File was Created."); 

				}
				
				doc = Globals.openXMLFile(HIERARCHY_XML_DIR, loginXmlFile);
				NodeList root = doc.getElementsByTagName("Obiee_Users");
				Node firstRootNode = root.item(0);
				NodeList childNodes = firstRootNode.getChildNodes();
				
				
				boolean flag = true; 
				for(int m=0;m<childNodes.getLength();m++){

					if(childNodes.item(m).getNodeType() == Node.ELEMENT_NODE){

						alredythrMailID = childNodes.item(m).getTextContent();
						
						System.out.println(childNodes.getLength()+":*********%%%%%%%UltraMailID%%%%%%%%*************:"+alredythrMailID);

						System.out.println(alredythrMailID+":*********UltraMailID*************:"+UltraMailID);

						if(alredythrMailID.equalsIgnoreCase(UltraMailID)) {
							flag = false;
							break;
						}
					}

				}
				if(flag) {
					Element childNode = doc.createElement("User");
					firstRootNode.appendChild(childNode);
					System.out.println(alredythrMailID+":*********UltraMailID*$$$$$$$$$$$$$$$$$************:"+UltraMailID);
					childNode.setAttribute("Email_ID",UltraMailID);
					childNode.setAttribute("Access_Start_Date", "");
					childNode.setAttribute("Access_End_Date", "");
					int accessMaxID = Globals.getMaxID(HIERARCHY_XML_DIR, hierarchyConfigFile,"Acess_User_ID", "ID");
					childNode.setAttribute("Access_Unique_ID", String.valueOf(accessMaxID));
					childNode.setAttribute("Access_Type", "Enabled");
					childNode.setAttribute("Allowed_Hierarchies", "");	
					childNode.setAttribute("CustomerKey",customerkey);
					childNode.setAttribute("Super_Privilege_Admin", "false");	 // code change VIJAY
					childNode.setAttribute("Login_ID",UltraMailID);
					childNode.setAttribute("First_Name",userUWFfname);
					childNode.setAttribute("Last_Name",userUWFlname);	
					childNode.setAttribute("Title","");
					childNode.setAttribute("Phone_Number_1","");	
					childNode.setAttribute("Phone_Number_2","");	
					childNode.setAttribute("Phone_Number_3","");	
					childNode.setAttribute("E_Mail_ID_1","");
					childNode.setAttribute("E_Mail_ID_2","");	
					childNode.setAttribute("Address","");
					childNode.setAttribute("City","");
					childNode.setAttribute("State","");
					childNode.setAttribute("Country","");
					childNode.setAttribute("Zip","");
					childNode.setAttribute("User_Password",Inventory.store(userUWFPassword));
					childNode.setAttribute("New_Customer", "");
					childNode.setAttribute("Active", "true");
					childNode.setAttribute("Disable", "false");
					childNode.setTextContent(UltraMailID);

					System.out.println("END");
				}
			

			Globals.writeXMLFile(doc, HIERARCHY_XML_DIR, loginXmlFile);
				
				}
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
			
		}
		
	public ArrayList<String> getcustomerkey(String loginMailID) {
		ArrayList<String> detailsAL = new ArrayList<>();
		String customerkey = "";

		try {

			PropUtil prop = new PropUtil();
			String adminXMLPath = prop.getProperty("IISUSERADMINFILEPATH");
			String IISUSERADMINFILENAME = prop.getProperty("IISUSERADMINFILENAME");
			Document adminXmlDoc = Globals.openXMLFile(adminXMLPath, IISUSERADMINFILENAME);
			NodeList adminChNdList = adminXmlDoc.getElementsByTagName("Admin");

			for (int i = 0; i < adminChNdList.getLength(); i++) {
				if (adminChNdList.item(i).getNodeType() == Node.ELEMENT_NODE) {
					Element adminEle = (Element) adminChNdList.item(i);
					NodeList adminsubChNdList = adminEle.getElementsByTagName("User");
					for (int j = 0; j < adminsubChNdList.getLength(); j++) {
						Element adminEle1 = (Element) adminsubChNdList.item(j);
						String userId = adminEle1.getAttribute("UserID");

						if (userId.equalsIgnoreCase(loginMailID)) {
							customerkey = adminEle.getAttribute("CustomerKey");
							String companyName = adminEle.getAttribute("CompanyName");
							// break;
							detailsAL.add(customerkey);
							detailsAL.add(companyName);
						}
					}
				}
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return detailsAL;

	}
	
	
	public void chgeType() {
		
		
		if(parallelType.equals("Percent")){
		
		this.minlablVAL="Minimum percent of members: ";
		this.minlablVALpercent="true";
		}else {
			this.minlablVAL="Minimum member count: ";
			this.minlablVALpercent="false";
		}
	}

	public boolean createBIMetaXML(String rbid_metadata_dir) {

		System.out.println("Entering: " + new Exception().getStackTrace()[0].getClassName() + "."
				+ new Exception().getStackTrace()[0].getMethodName());

		FileInputStream fis = null;
		FileOutputStream fos = null;
		DocumentBuilderFactory dbf = null;
		DocumentBuilder db = null;
		Document doc = null;
		boolean file_created = false;
		try {

			// Check if BI Metadata File exists
			boolean fileexists = Globals.isFileExists(rbid_metadata_dir, "BI Metadata.xml");

			// If BI Metadata.xml exists already return
			if (fileexists) {
				System.out.println("BI Metadata File " + rbid_metadata_dir + "BI Metadata.xml"
						+ " exists already. Exiting.");
			}
			// If BI Metadata.xml does NOT exists, Create it
			else {
				dbf = DocumentBuilderFactory.newInstance();
				db = dbf.newDocumentBuilder();
				doc = db.newDocument();
				String root = "BI_Metadatas";
				Element rootElement = doc.createElement(root);

				doc.appendChild(rootElement);
				TransformerFactory transformerFactory = TransformerFactory.newInstance();
				Transformer transformer = transformerFactory.newTransformer();
				transformer.setOutputProperty(OutputKeys.INDENT, "yes");
				DOMSource source = new DOMSource(doc);
				fos = new FileOutputStream(rbid_metadata_dir + "BI Metadata.xml");
				StreamResult result = new StreamResult(fos);
				transformer.transform(source, result);
				result.getOutputStream().flush();
				result.getOutputStream().close();
			}

		} catch (Exception e) {
			Globals.getException(e);
			file_created = false;
		} finally {
			try {
				if (fis != null)
					fis.close();
				if (fos != null)
					fis.close();

			} catch (Exception ioe) {
				Globals.getException(ioe);
				file_created = false;
			}
		}

		System.out.println("Exiting: " + new Exception().getStackTrace()[0].getClassName() + "."
				+ new Exception().getStackTrace()[0].getMethodName());
		return file_created;

	}

	public String randomGenstring() {


		char[] chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();
		Random rnd = new Random();
		StringBuilder sb = new StringBuilder((100000 + rnd.nextInt(900000)) + "");
		for (int i = 0; i < 5; i++)
			sb.append(chars[rnd.nextInt(chars.length)]);

		return sb.toString();


	}
	
	public void emptyimportuser() {
		
		try {
			
			this.externalfilename="";
			this.fileuploadstatusStr="";
			
		}catch (Exception e) {
			
			// TODO: handle exception
		}
		
		
		
	} 

	public void externalUpload(FileUploadEvent event) throws Exception {

		try {

			this.externalfilename = "";
			String absolutePath="";
			System.out.println("file Upload START");
			fileuploadstatusStr="Please wait. Import users...";
			
			PropUtil prop = new PropUtil();
			rbid_metadata_dir = prop.getProperty("RBID_METADATA_DIR");
			bi_metadata_dir = prop.getProperty("BI_METADATA_DIR");
			createBIMetaXML(rbid_metadata_dir);
			System.out.println("rbid_metadata_dir: " + rbid_metadata_dir);
			// String target = rbid_metadata_dir;
			File sourceFile = null;
			File targetFile = null;
			String absoluteFILEPath="";
			String infile = "";
			InputStream fileContent = null;
			//	ischagesMade = true;
			//deletemsg = "";

			UploadedFile item = event.getUploadedFile();
			infile = item.getName();

			this.externalfilename=infile;
			String infilesize = String.valueOf(item.getSize());
			String infiletype = item.getContentType();
			System.out.println("File to be uploaded: " + infile);

			sourceFile = new File(infile);
			targetFile = new File(bi_metadata_dir + sourceFile.getName());

			BIMetadataFile = sourceFile.getName();

			if (Globals.isFileExists(bi_metadata_dir, BIMetadataFile)) {

				System.out.println("*** Warning: BI Metadata File exists already. Deleting it: " + bi_metadata_dir
						+ BIMetadataFile);

				FileUtils.deleteQuietly(new File(bi_metadata_dir + BIMetadataFile));
			}

			//		FileUtils.copyFile(sourceFile, targetFile, false);   code change pandian 12Aug2013




			absoluteFILEPath=bi_metadata_dir + BIMetadataFile;

			fileContent =item.getInputStream();
			BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(targetFile));
			byte[] buffer = new byte[1024];
			int len;
			while ((len = fileContent.read(buffer)) >= 0) {

				bos.write(buffer, 0, len);
			}

			fileContent.close();

			bos.close();


			if (Globals.isFileExists(bi_metadata_dir, BIMetadataFile)) {



				System.out.println("BI Metadata File Uploaded Successfully: " + bi_metadata_dir + BIMetadataFile);
			} else {

				System.out.println("*** Error: BI Metadata File is not Uplodaed: " + bi_metadata_dir + BIMetadataFile);
				System.out.println("*** Error: Discovery Process Aborted.");

				// ??Devan: Disable Discover Button. If the file is uploaded later correctly, ensure Discover Button is
				// enabled.
				return;
			}



			String loginXmlFile = prop.getProperty("LOGIN_XML_FILE");
			String hierarchyXMLdir= prop.getProperty("HIERARCHY_XML_DIR");		
			boolean fileexists = Globals.isFileExists(hierarchyXMLdir,loginXmlFile);
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
				String root1 = "Obiee_Users";
				Element rootElement1 = doc.createElement(root1);
				doc.appendChild(rootElement1);

				TransformerFactory transformerFactory = TransformerFactory
						.newInstance();
				Transformer transformer = transformerFactory.newTransformer();
				transformer.setOutputProperty(OutputKeys.INDENT, "yes");
				DOMSource source = new DOMSource(doc);
				fos = new FileOutputStream(hierarchyXMLdir
						+ loginXmlFile);
				StreamResult result = new StreamResult(fos);
				transformer.transform(source, result);
				result.getOutputStream().flush();
				result.getOutputStream().close();
				System.out.println("XML File was Created."); 

			}

			doc = Globals.openXMLFile(hierarchyXMLdir, loginXmlFile);
			NodeList root = doc.getElementsByTagName("Obiee_Users");
			Node firstRootNode = root.item(0);






			File excelFileWthPath=new File(absoluteFILEPath);
			//	File excelFileWthPath=new File("C:\\Users\\Developer\\Desktop\\vijay.xlsx");
			System.out.println("excelFileWthPath :"+excelFileWthPath);
			FacesContext ctx1 = FacesContext.getCurrentInstance();
			ExternalContext extContext1 = ctx1.getExternalContext();
			Map sessionMap1 = extContext1.getSessionMap();
			LoginBean lgB = (LoginBean) sessionMap1.get("loginBean");
			String userLoginName=lgB.getUsername();
			Workbook workbook =ExcelProcess.getWorkbook(excelFileWthPath);
			ArrayList columnNameAL = new ArrayList();
			ArrayList rowValueAL = new ArrayList();
			int limitedRowNumber=10;
			int staringtRow=1;
			String EmailName="";
			ExcelReadUsingPOI.readDataFromExcel(workbook, 0,excelFileWthPath.getAbsolutePath(),staringtRow,limitedRowNumber,columnNameAL,rowValueAL);
			String	HIERARCHY_XML_DIR = prop.getProperty("HIERARCHY_XML_DIR");
			String	hierarchyConfigFile = prop.getProperty("HIERARCHY_CONFIG_FILE");
			int accessMaxID = Globals.getMaxID(HIERARCHY_XML_DIR, hierarchyConfigFile,"Acess_User_ID", "ID");



			System.out.println("rowValueAL.size() :"+rowValueAL.size());

			for(int j=0;j<rowValueAL.size();j++){
				LoadDataTableProcess colObj=(LoadDataTableProcess)rowValueAL.get(j);
				ArrayList eachLineColumnValueAL =colObj.eachLineColumnValueAL;
				// String emailnName =(String) rowValueAL.get(3);
				// System.out.println(" &&&&&&&&&&&&&&&: "+emailnName);

				Element childNode = doc.createElement("User");
				firstRootNode.appendChild(childNode);

				for(int k=0;(eachLineColumnValueAL!=null && k<eachLineColumnValueAL.size());k++){


					LoadDataTableProcess columnObj=(LoadDataTableProcess)eachLineColumnValueAL.get(k);
					for(int g=0;g<columnNameAL.size();g++){
						String randomnum=  randomGenstring();
						String columnName=(String)columnNameAL.get(k);

						//String cellValue = ExcelProcess.getCellValue(cell);
						//userLoginName

						if(columnName.equalsIgnoreCase("Email_ID")) {
							EmailName=(String)columnObj.getColumnValue();
						}
						System.out.println(columnName+": columnValue: "+columnObj.getColumnValue());
						childNode.setAttribute(columnName, (String)columnObj.getColumnValue().trim());
						childNode.setAttribute("Access_End_Date", "");
						childNode.setAttribute("Access_Start_Date", "");
						childNode.setAttribute("Access_Type", "Enabled");
						childNode.setAttribute("New_Customer", "false");
						childNode.setAttribute("Super_Privilege_Admin", "false");
						childNode.setAttribute("Active", "true");
						childNode.setAttribute("Allowed_Hierarchies", "");
						childNode.setAttribute("Disable", "");
						childNode.setAttribute("E_Mail_ID_1", "");
						childNode.setAttribute("E_Mail_ID_2", "");
						childNode.setAttribute("Login_ID",EmailName);
						childNode.setAttribute("CustomerKey", lgB.getCustomerKey());
						childNode.setAttribute("Access_Unique_ID", String.valueOf(accessMaxID));
						childNode.setAttribute("User_Password", Inventory.store(String.valueOf(randomnum)));
						childNode.setTextContent(EmailName);
					}

					//break;


				}
			}

			Globals.writeXMLFile(doc, hierarchyXMLdir, loginXmlFile);

			fileuploadstatusStr="Import File Sucessfully";
			System.out.println("file Upload END");


		}catch (Exception e) {
			// TODO: handle exception
		}

	}
	public void assort(){	        

		for(int i=0;i<membersNameAL.size();i++)
		{
			

			Collections.sort(membersNameAL);

		

		}			


	}
	public void desort(){
		for(int i=0;i<membersNameAL.size();i++)
		{
			Collections.sort(membersNameAL, Collections.reverseOrder());
			System.out.println("List in Java sorted in descending order: " + membersNameAL); 
		}}
	
	
	public void assort1(){	        

		for(int i=0;i<viewMenberNameAL.size();i++)
		{
			

			Collections.sort(viewMenberNameAL);

		

		}			


	}
	public void desort1(){
		for(int i=0;i<viewMenberNameAL.size();i++)
		{
			Collections.sort(viewMenberNameAL, Collections.reverseOrder());
			
		}}


	public void createNewWF() throws Exception {

		System.out.println("COMING*******************");
		PropUtil prop=new PropUtil();
		String hierLevelXML=prop.getProperty("HIERARCHY_XML_FILE");
		String hierLeveldir=prop.getProperty("HIERARCHY_XML_DIR");
		String uniqueid=String.valueOf(Globals.getMaxID(hierLeveldir, hierarchyConfigFile,"Row_ID4Oracle", "ID"));
		this.uniqueid = uniqueid;
		String hierID = String.valueOf(Globals.getMaxID(hierLeveldir, hierarchyConfigFile,"Hierarchy_ID", "ID"));
		this.hierID = hierID;

		Document xmlDoc=Globals.openXMLFile(hierLeveldir, hierLevelXML);
		Element hierEle = xmlDoc.createElement("Hierarchy_Level"); 
		hierEle.setAttribute("Hierarchy_ID", hierID);
		hierEle.setAttribute("Unique_ID", uniqueid);
		xmlDoc.getFirstChild().appendChild(hierEle);
		Globals.writeXMLFile(xmlDoc, hierLeveldir, hierLevelXML);

		DashboardStage("Default", "hierarchyTree", hierID, "");
		//		workFlowBean wfb = new workFlowBean();
		//		wfb.loadDefaultLayoutTree("Default", hierID);
	}


	public void emptystrMsg() {
		// TODO Auto-generated method stub

		try{
			returnPassMessage="";
			returnPassMessagecolor="blue";

		}catch (Exception e) {
			// TODO: handle exception
		}

	} 
	public void addtoNode(String hierarchyID){
		System.out.println("Entering: " + new Exception().getStackTrace()[0].getClassName() + "."
				+ new Exception().getStackTrace()[0].getMethodName());

		try{
			msg4WF = "";
			if(tempValue.equals(stageNameValue)){
				tempValue = "";
				return;
			}
			stageModel = new DefaultDashboardModel();  
			DashboardColumn column1 = new DefaultDashboardColumn(); 

			if(stageNameValue.equals("Enter_Stage_Name")){
				return;
			}
			this.wfAdminValidationmsg = "";
			FacesContext ctx = FacesContext.getCurrentInstance();
			ExternalContext extContext = ctx.getExternalContext();
			Map sessionMap = extContext.getSessionMap();
			//		workFlowBean wfb = (workFlowBean) sessionMap.get("workflowbean");
			//HierarchyBean hrybn = (HierarchyBean) sessionMap.get("hierarchyBean");
			//String hierarchyID=hrybn.getHierarchy_ID();

			PropUtil prop=new PropUtil();
			String XMLFileName=prop.getProperty("HIERARCHY_XML_FILE");
			String dir = prop.getProperty("HIERARCHY_XML_DIR");
			Document doc=Globals.openXMLFile(dir, XMLFileName);

			Node workFlowNode = Globals.getNodeByAttrVal(doc, "Workflow", "Hierarchy_ID", hierarchyID);
			if(workFlowNode != null){
				Node stage;
				NodeList stageNodes = workFlowNode.getChildNodes();
				for(int i=0;i<stageNodes.getLength();i++){
					stage =  stageNodes.item(i);
					if(stage.getNodeType() == Node.ELEMENT_NODE){
						Node stageName = stage.getAttributes().getNamedItem("Stage_Name"); 
						if(stageName != null){
							String name = stageName.getNodeValue();
							if(stageNameValue.equals(name)){
								this.wfAdminValidationmsg = "Stage name is already exist.";
								System.out.println("Stage name is already exist.");
								stageNameValue = "Enter_Stage_Name";
								return;
							}
						}
					}
				}
			}

			System.out.println(stageModel.getColumnCount()+"<<-----tapName===>>>"+tapName); 
			for(int i=0;i<stageModel.getColumnCount();i++) {
				DashboardColumn dc = stageModel.getColumn(i);
				System.out.println(dc.getWidgetCount()+"<<-----getWidgetCount===>>>"); 
				for(int j=0;j<dc.getWidgetCount();j++) {
					System.out.println("-=-=-getWidget=-=-="+dc.getWidget(j));;
				}

			}
			System.out.println(model.getColumnCount()+"<<-----custommodel===>>>"+tapName); 
			for(int i=0;i<model.getColumnCount();i++) {
				DashboardColumn dc = model.getColumn(i);
				System.out.println(dc.getWidgetCount()+"<<--custommodel---getWidgetCount===>>>"); 
				for(int j=0;j<dc.getWidgetCount();j++) {
					System.out.println("-=custommodel-=-getWidget=-=-="+dc.getWidget(j));;
				}

			}

			if(tapName.equals("Default")){

				column1.addWidget("Stage_"+panelAL.size());

				stageRoot = new DefaultTreeNode("Root", null);
				TreeNode stage = new DefaultTreeNode("", stageRoot);

				if(tempValue.equals(stageNameValue)){}else{
					model.addColumn(column1);  
					System.out.println(model.getColumnCount()+"<<--model---custommodel===>>>"+panelAL.size()); 
					setTempValue(stageNameValue);
					String Array[]=new String[0];
					String ArrayST[]=new String[0];
					String empEmail[]=new String[0];
					String empUid[]=new String[0];
					String propName="Serial";
					stageNameFromAddStage = stageNameValue;
					tempValue = stageNameValue;
					workFlowBean wk=new workFlowBean();
					wk.addStageintoXML(hierarchyID, stageNameValue, Array, ArrayST, empEmail, empUid, propName, "","","","","","",new Hashtable(),
							new Hashtable(),new Hashtable(),false,true, false, false, false, false, false, new ArrayList<RuleDataModel>(), new ArrayList<RuleAttributeDataModel>(), false);
					dashboardManager dm = new dashboardManager(stageNameValue, new ArrayList<>(), new ArrayList<>(), "", "", String.valueOf(model.getColumnCount()), "false");
					panelAL.add(dm);

				}
			}else if(tapName.equals("Custom")){

				column1.addWidget(cusStageNameValue);
				stageModel.addColumn(column1);  
				stageRoot = new DefaultTreeNode("Root", null);
				TreeNode stage = new DefaultTreeNode("", stageRoot);

				if(tempValue.equals(cusStageNameValue)){}else{
					setTempValue(cusStageNameValue);
					String Array[]=new String[0];
					String ArrayST[]=new String[0];
					String empEmail[]=new String[0];
					String empUid[]=new String[0];
					String propName="Serial";
					stageNameFromAddStage = cusStageNameValue;
					workFlowBean wk=new workFlowBean();

					wk.addStageintoXML(hierarchyID, cusStageNameValue, Array, ArrayST, empEmail, empUid, propName, "","","","","","",new Hashtable(),
							new Hashtable(),new Hashtable(),false,true, false, false, false, false, false, new ArrayList<RuleDataModel>(), new ArrayList<RuleAttributeDataModel>(), false);

				}
			}

			stageNameValue = "";


			System.out.println("Exiting: " + new Exception().getStackTrace()[0].getClassName() + "."
					+ new Exception().getStackTrace()[0].getMethodName());


		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}

	//code change pandian 20MAR2014
	private String viewstagememberRole="";
	private ArrayList viewstagestatusAL=new ArrayList<>();
	private ArrayList viewMenberNameAL=new ArrayList<>();
	private ArrayList allviewMenberNameAL=new ArrayList<>();
	

	private String viewstagePropertiesValue;
	private String viewstageminApprsRender="false";
	private String vstageMinApprVal;
	private String fieldsetWidth = "";
	private ArrayList rejectedReturnstagestatusAL=new ArrayList<>();

	private ArrayList rejectedReturngotostageAL=new ArrayList<>();
	
	

	private String render4publicNadmin="block";
	private String visable4publicNadmin="true";

	


	LoginProcessManager lgnmngr=new LoginProcessManager();
	public void getStageInformation(String StageNo,String StageName) { 
		System.out.println("Entering: " + new Exception().getStackTrace()[0].getClassName() + "."
				+ new Exception().getStackTrace()[0].getMethodName());

		try{
			oneDriveMailID = "";
			oneDriveHTxt = "";
			authText = "";
			authMsg = "";
			msg4WF = "";
			showRejectedButton = false;
			showPassButton = false;
			showhandleTitleBox ="none";
			setStageMsg="";
			setStageMsgcolor="";
			rejectedReturn2 = "";
			rejectedSetStatus2 = "";
			rejectedSendNotification2 = "";
			selectedStatus = "";
			allowuploadbool = false;
			enableEsign = false;
			allowEditUD = false;
			autologinbool = false;
			externalUserFlag = false;
			sendPrimaryFileOnlyFlag = false;
			statusCode4DocsFlag = false;
			changeprimarydocFlag = false;
			rulesAL.clear();
			selectedrulesAL.clear();
			rulesALTemp.clear();
			System.out.println("View Stage Here StageNo"+StageNo+" StageName"+StageName);
			//		stageNameValue="Enter_Stage_Name"; //code change Jayaramu 07APR14
			message4SetStage = "";
			FacesContext ctx = FacesContext.getCurrentInstance();
			ExternalContext extContext = ctx.getExternalContext();
			Map sessionMap = extContext.getSessionMap();
			LoginBean lb = (LoginBean) sessionMap.get("loginBean");

			PropUtil prop=new PropUtil();

			String configFileName=prop.getProperty("HIERARCHY_CONFIG_FILE");
			String hierLeveldir=prop.getProperty("HIERARCHY_XML_DIR");
			String heirLevelXML=prop.getProperty("HIERARCHY_XML_FILE");
			Document xmlDoc=Globals.openXMLFile(hierLeveldir, heirLevelXML);
			Document docs=Globals.openXMLFile(hierLeveldir, configFileName);
			Element rejectedval = (Element)docs.getElementsByTagName("Rejected_Return").item(0);
			Element pausehandl = (Element)docs.getElementsByTagName("Pause_Handling").item(0);

			//HierarchyBean hrybn = (HierarchyBean) sessionMap.get("hierarchyBean");	        
			//hierID=hrybn.getHierarchy_ID();
			stageNameFromAddStage = StageName;
			stageNumberFromAddStage = StageNo;
			ischangedStage = false;
			ischangedStage1 = false;
			Hashtable stageDetailsHT=lgnmngr.getStageDetails(hierID, StageNo,"getStageInfo");//code Change Gokul 07MAY2013
			int currStageNo=Integer.parseInt(StageNo) ;
			stageModel = new DefaultDashboardModel();
			if(stageDetailsHT == null || stageDetailsHT.isEmpty()){ //code change Jayaramu 10APR14
				ischangedStage = false;
				return;
			}
			ischangedStage = true;
			String stagename=(String)stageDetailsHT.get("Stage_Name");
			String stageno=String.valueOf(stageDetailsHT.get("Stage_No"));
			String stageRole=String.valueOf(stageDetailsHT.get("Stage_Role"));

			String auto_LoginBol=stageDetailsHT.get("Auto_Login") == null || String.valueOf(stageDetailsHT.get("Auto_Login")).trim().isEmpty() ? "false" : String.valueOf(stageDetailsHT.get("Auto_Login"));
			String allow_UploadBol=stageDetailsHT.get("Allow_Upload") == null || String.valueOf(stageDetailsHT.get("Allow_Upload")).trim().isEmpty() ? "false" : String.valueOf(stageDetailsHT.get("Allow_Upload"));
			String enableesign=stageDetailsHT.get("Enable_Esign") == null || String.valueOf(stageDetailsHT.get("Enable_Esign")).trim().isEmpty() ? "false" : String.valueOf(stageDetailsHT.get("Enable_Esign"));
			String externalFlag=stageDetailsHT.get("ExternalUser") == null || String.valueOf(stageDetailsHT.get("ExternalUser")).trim().isEmpty() ? "false" : String.valueOf(stageDetailsHT.get("ExternalUser"));
			String sendPrimaryFileOnlyFlag=stageDetailsHT.get("SendPrimaryFileOnly") == null || String.valueOf(stageDetailsHT.get("SendPrimaryFileOnly")).trim().isEmpty() ? "false" : String.valueOf(stageDetailsHT.get("SendPrimaryFileOnly"));
			String statusCode4DocsFlag=stageDetailsHT.get("StatusCode4Documents") == null || String.valueOf(stageDetailsHT.get("StatusCode4Documents")).trim().isEmpty() ? "false" : String.valueOf(stageDetailsHT.get("StatusCode4Documents"));
			String changeprimarydocFlag=stageDetailsHT.get("Change_PrimaryFileOnly") == null || String.valueOf(stageDetailsHT.get("Change_PrimaryFileOnly")).trim().isEmpty() ? "false" : String.valueOf(stageDetailsHT.get("Change_PrimaryFileOnly"));
			String allowEditUD=stageDetailsHT.get("AllowUltraDocEdit") == null || String.valueOf(stageDetailsHT.get("AllowUltraDocEdit")).trim().isEmpty() ? "false" : String.valueOf(stageDetailsHT.get("AllowUltraDocEdit"));
			
			this.autologinbool=Boolean.valueOf(auto_LoginBol);
			this.allowuploadbool=Boolean.valueOf(allow_UploadBol);
			this.enableEsign = Boolean.valueOf(enableesign);
			this.externalUserFlag=Boolean.valueOf(externalFlag);
			this.sendPrimaryFileOnlyFlag=Boolean.valueOf(sendPrimaryFileOnlyFlag);
			this.statusCode4DocsFlag = Boolean.valueOf(statusCode4DocsFlag);
			this.changeprimarydocFlag=Boolean.valueOf(changeprimarydocFlag);
			this.allowEditUD = Boolean.valueOf(allowEditUD);
			
			Hashtable escalteManager = (Hashtable) stageDetailsHT.get("EscalateManagersHT");
			if(escalteManager == null || escalteManager.isEmpty())
				this.selectedMember = "";
			else {
				Hashtable empHT = (Hashtable)escalteManager.get(0);
				this.selectedMember = empHT == null || empHT.isEmpty() ? "" : String.valueOf(empHT.get("empName"));
			}
			this.viewstagememberRole=stageRole;
			this.selectedStageRole=stageRole; 
			//this.selectedMember=stageRole; //code change pandian 54Mar2014 5.30pm

			if(StageName.equals("Admin") || StageName.equals("public")){

				visable4publicNadmin="false";

			}else{

				visable4publicNadmin="true";

			}

			SecurityBean sb = new SecurityBean();
			stageRoleAL = new ArrayList<>();

			Hashtable setStageRoleinHT = sb.gettingAllRoleinHT("allRole","");    	
			if(setStageRoleinHT != null){

				for(int i=0;i<setStageRoleinHT.size();i++){

					stageRoleAL.add(setStageRoleinHT.get(i));
				}
			}

			viewMenberNameAL=new ArrayList<>();
			Hashtable employeeDetailsHT=(Hashtable)stageDetailsHT.get("EmployeedetHT");
			if(employeeDetailsHT!=null && employeeDetailsHT.size()>0){
				for(int i=0;i<employeeDetailsHT.size();i++){
					Hashtable empHT=(Hashtable)employeeDetailsHT.get(i);
					String empName=(String)empHT.get("empName");
					viewMenberNameAL.add(empName);
					allviewMenberNameAL.add(empName);
					System.out.println("Adding an array list setting empName "+empName);
				} 

			}

			Hashtable statusmsgDetailsHT=(Hashtable)stageDetailsHT.get("MessagedetHT");
			viewstagestatusAL=new ArrayList<>();
			rejectedReturnstagestatusAL = new ArrayList<>();			
			System.out.println("statusmsgDetailsHT+++++++++"+statusmsgDetailsHT);
			if(statusmsgDetailsHT!=null && statusmsgDetailsHT.size()>0){
				int statusnum=0;
				for(int i=0;i<statusmsgDetailsHT.size();i++){
					statusnum=i+1;
					String stmsg="";
					String stges="";
					if(statusnum==statusmsgDetailsHT.size()){
						stmsg=(String)statusmsgDetailsHT.get("Final");
					}else{
						stmsg=(String)statusmsgDetailsHT.get(String.valueOf(statusnum));
					}		

					viewstagestatusAL.add(stmsg);

					if(stmsg.equals("Rejected")){ //code change Jayaramu 10APR14
						showRejectedButton = true;
						showhandleTitleBox="block";
						rejectedReturn2 = rejectedval.getAttribute("Previus_Stage");
						rejectedSetStatus2=rejectedval.getAttribute("Set_Status");
						rejectedSendNotification2 = rejectedval.getAttribute("Send_Notification");
					}
					if(stmsg.equals("Pause")){ //code change Jayaramu 10APR14
						showPassButton = true;
						showhandleTitleBox="block";
						passSendNotification2=pausehandl.getAttribute("Send_Notificaion_Flag");
					}
					if(stmsg.equals("Pause") || stmsg.equals("Rejected")){ //code change Jayaramu 10APR14
						System.out.println("xxxxxxxxxxxxxxxxxxx");
						showhandleTitleBox="block";
					}
					System.out.println("Adding an array list setting stmsg "+stmsg);
				}

			}
			Node workFlowN=Globals.getNodeByAttrVal(xmlDoc, "Workflow", "Hierarchy_ID", hierID);
			Element workflowEle = (Element) workFlowN;
			String ruleAttriName = workflowEle.getAttribute("RuleAttributeName");
			String ruleAttriType = workflowEle.getAttribute("RuleAttributeType");
			loadRulesFromStr(ruleAttriName, ruleAttriType);
			getRulesDetailsFromXML(workFlowN);
			System.out.println(rulesALTemp.size()+"-=-=-=-=-=-=-=-=-=-="+rulesAL.size());
			workFlowBean wfb = new workFlowBean();
			Hashtable statusMsg = wfb. getStatusMsgFromConfig();
			statusCodesAL = new ArrayList<>();
			rejectedReturnstagestatusAL = new ArrayList<>();
			for(int i=0;i<statusMsg.size();i++){

				statusCodesAL.add(statusMsg.get(i));
				rejectedReturnstagestatusAL.add(statusMsg.get(i));
			}
			statusCodesAL.removeAll(viewstagestatusAL);
			Hashtable membersFromLaXmlHT = wfb.getAllUsersFromlaXML();
			Hashtable membersHT = new Hashtable<>();
			membersNameAL = new ArrayList<>();
			allMembersNameAL = new ArrayList<>();


			for(int i=0;i<membersFromLaXmlHT.size();i++){
				membersHT = (Hashtable)membersFromLaXmlHT.get(i);
				
				if(this.urlParameter.equalsIgnoreCase("true")|| editwrkflowflag.toLowerCase().equals("true")){
					
				if(membersHT.get("CustomerKey") != null && !String.valueOf(membersHT.get("CustomerKey")).equalsIgnoreCase(this.urlcus_key)) {
					
					continue;
					
				 }
				
				}
				
				else {
					
				if(membersHT.get("CustomerKey") != null && !String.valueOf(membersHT.get("CustomerKey")).equalsIgnoreCase(lb.getCustomerKey())){
					continue;
				}
				
				}
				
				String empname=(String)membersHT.get("Employee_Name");
				if(viewMenberNameAL.contains(empname))
					continue;
				membersNameAL.add(empname);
				allMembersNameAL.add(empname);

			}
//			membersNameAL.removeAll(viewMenberNameAL);
//			allMembersNameAL.removeAll(viewMenberNameAL);
//
//			membersNameAL.removeAll(allviewMenberNameAL);
//			allMembersNameAL.removeAll(allviewMenberNameAL);


			//	Collections.sort(membersNameAL);


			viewstagePropertiesValue = "";
			vstageMinApprVal = "1"; // code change Menaka 19APR2014
			viewstageminApprsRender = "false";
			Hashtable propDetailsHT=(Hashtable)stageDetailsHT.get("PropertiesHT");
			System.out.println("-=-=-=-=-=propDetailsHT-=-=-=-=-=-=-="+propDetailsHT);
			if(propDetailsHT!=null && !propDetailsHT.isEmpty()){

				String propties=(String)propDetailsHT.get("Properties");
				if(propties.equals("Serial")){       		  
					this.vstageMinApprVal="1";
					this.viewstagePropertiesValue="Serial";
					this.minlablVAL="Minimum member count: ";
					this.minlablVALpercent="false";
					this.viewstageminApprsRender="false";
				}else  if(propties.equals("Parallel")){       
					this.viewstagePropertiesValue="Parallel";
					this.minlablVAL="Minimum percent of members: ";
					this.parallelType = (String)propDetailsHT.get("Parallel_Type"); 
					if(parallelType.equals("Percent")){ //code change Jayaramu 10APR14
						this.vstageMinApprVal = (String)propDetailsHT.get("Min_Approvers_Percent");
						this.minlablVAL="Minimum percent of members: ";
						this.minlablVALpercent="true";
					}else{
						this.vstageMinApprVal=(String)propDetailsHT.get("Min_Approvers");
						this.minlablVAL="Minimum member count: ";
						this.minlablVALpercent="false";
					}
					this.viewstageminApprsRender="true";
				}else {
					this.vstageMinApprVal="1";
					this.viewstagePropertiesValue="Serial";
					this.minlablVAL="Minimum member count: ";
					this.minlablVALpercent="false";
					this.viewstageminApprsRender="false";
				}

			}else {
				this.vstageMinApprVal="1";
				this.viewstagePropertiesValue="Serial";
				this.minlablVAL="Minimum member count: ";
				this.minlablVALpercent="false";
				this.viewstageminApprsRender="false";
			}


			System.out.println("settig stagename "+stagename+" viewstagememberRole :"+viewstagememberRole+"vstageMinApprVal : "+vstageMinApprVal+"" +
					"viewstagePropertiesValue : "+viewstagePropertiesValue+"vstageMinApprVal : "+vstageMinApprVal);
			
			String iisConfigFilePath = prop.getProperty("IISCONFIGFILEPATH");
			String iisConnFilename = prop.getProperty("IISCONNECTIONFILENAME");
			iisConfigFilePath = iisConfigFilePath+this.urlCompanyName+"_"+this.urlcus_key+"\\"+"User_Templates_Config\\";
			Document doc = Globals.openXMLFile(iisConfigFilePath, iisConnFilename);
			NodeList repositoryNdList = doc.getElementsByTagName("Repository");
			if(repositoryNdList != null && repositoryNdList.getLength() > 0) {
				String userId = "";
				for(int i=0;i<repositoryNdList.getLength();i++) {
					if(repositoryNdList.item(i).getNodeType() == Node.ELEMENT_NODE) {
						Element ele = (Element)repositoryNdList.item(i);
						for(int j=0;j<ele.getChildNodes().getLength();j++) {
							if(ele.getChildNodes().item(j).getNodeType() == Node.ELEMENT_NODE) {
								Element ele1 = (Element)ele.getChildNodes().item(j);
								userId = ele1.getAttribute("UserID_Name");
								break;
							}
						}
						
					}
				}
				System.out.println("-=-=-=-=-userId=-=-=-="+userId);
				oneDriveMailID = userId;
				oneDriveHTxt = "You have authenticated "+(userId.isEmpty() ? "your" : userId) +" account. If you want to reauthenticate or authenticate another account, please click below link.";
				authText = "Click here to authenticate your account.";
			}else {
				oneDriveHTxt = "To select Enable E-Sign option, please authenticate your OneDrive account by clicking below link.";
				authText = "Click here to authenticate your account.";
			}
			System.out.println("-=-=-=-=-oneDriveMailID=-=-=-=-="+oneDriveMailID);
			String connectionType = "OneDrive";
			String iisCommonConfigFilePath = prop.getProperty("IISCOMMONCONFIGDIRECTORY");
			String iisCommonConfigFileName = prop.getProperty("IISCOMMONCONFIGNAME");
			Hashtable smartShturlHT = null;
			smartShturlHT = OneDriveFileManipulation.getsmartSheetProp("OneDrive", iisCommonConfigFilePath, iisCommonConfigFileName);
			String clintID = (String)smartShturlHT.get("Client_Id");
			String codeURL = (String)smartShturlHT.get("Code_URL");
			String redirectURL = (String)smartShturlHT.get("Redirect_URL");
			if (connectionType.equalsIgnoreCase("google drive"))
			{

				codeURL = codeURL.replace("$$Client_Id$$", clintID).replace("$$redirect_uri$$", redirectURL);

			}
			else
			{

				codeURL = codeURL.replace("$$Client_Id$$", clintID).replace("$$redirect$$", redirectURL);
			}
			this.oneDriveAuthURL = codeURL;
			System.out.println("Exiting: " + new Exception().getStackTrace()[0].getClassName() + "."
					+ new Exception().getStackTrace()[0].getMethodName());


		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}
	
	public void setOneDriveDetails() throws Exception {
		oneDriveMailID = "";
		oneDriveHTxt = "";
		authText = "";
		authMsg = "";
		msg4WF = "";
		PropUtil prop=new PropUtil();
		String iisConfigFilePath = prop.getProperty("IISCONFIGFILEPATH");
		String iisConnFilename = prop.getProperty("IISCONNECTIONFILENAME");
		iisConfigFilePath = iisConfigFilePath+this.urlCompanyName+"_"+this.urlcus_key+"\\"+"User_Templates_Config\\";
		System.out.println("-=-=-=-=-oneDriveMailID=-=setOneDriveDetails-=-=-="+oneDriveMailID);
		System.out.println("-=-=-=-=-iisConfigFilePath=-=-=-=-="+iisConfigFilePath);
		Document doc = Globals.openXMLFile(iisConfigFilePath, iisConnFilename);
		NodeList repositoryNdList = doc.getElementsByTagName("Repository");
		if(repositoryNdList != null && repositoryNdList.getLength() > 0) {
			String userId = "";
			for(int i=0;i<repositoryNdList.getLength();i++) {
				if(repositoryNdList.item(i).getNodeType() == Node.ELEMENT_NODE) {
					Element ele = (Element)repositoryNdList.item(i);
					for(int j=0;j<ele.getChildNodes().getLength();j++) {
						if(ele.getChildNodes().item(j).getNodeType() == Node.ELEMENT_NODE) {
							Element ele1 = (Element)ele.getChildNodes().item(j);
							userId = ele1.getAttribute("UserID_Name");
							break;
						}
					}
					
				}
			}
			System.out.println("-=-=-=-=-userId=-=-=-="+userId);
			oneDriveMailID = userId;
			oneDriveHTxt = "You have authenticated "+(userId.isEmpty() ? "your" : userId) +" account. If you want to reauthenticate or authenticate another account, please click below link.";
			authText = "Click here to authenticate your account.";
		}else {
			oneDriveHTxt = "To select Enable E-Sign option, please authenticate your OneDrive account by clicking below link.";
			authText = "Click here to authenticate your account.";
		}
	}
	
	public void check4AuthDate()
    {
        try
        {
        	authMsg = "";
        	msg4WF = "";
        	System.out.println("ex>>>gggfgg>>>>>>"+oneDriveMailID);
        	PropUtil prop=new PropUtil();
        	String iisCommonConfigFilePath = prop.getProperty("IISCOMMONCONFIGDIRECTORY");
			String iisCommonConfigFileName = prop.getProperty("IISCOMMONCONFIGNAME");
			String iisConfigFilePath = prop.getProperty("IISCONFIGFILEPATH");
			String iisConnFilename = prop.getProperty("IISCONNECTIONFILENAME");
			iisConfigFilePath = iisConfigFilePath+this.urlCompanyName+"_"+this.urlcus_key+"\\"+"User_Templates_Config\\";
            System.out.println("ex>>>>>>>>>"+iisConfigFilePath);
//            String usermetadirectory = templateXMLDir;
//            String connectionfilename = connConfigFilename;
            boolean isExecute = false;
            String connectionType = "OneDrive";
            String connectionname = "temp";
            String useridname = oneDriveMailID;
//            String accelfilename = accelFilename;
            String codeFilePath = iisCommonConfigFilePath+"ProcessText.txt";
            Hashtable connectionHT = OneDriveFileManipulation.getNodeName(connectionname, iisConfigFilePath, iisConnFilename, connectionType);
            if (connectionHT == null || connectionHT.size() < 0)
            {
                connectionHT = new Hashtable();
            }

            connectionHT.put("Connection_Type", connectionType);
            connectionHT.put("Connection_Name", connectionname);
            connectionHT.put("UserID_Name", useridname);
            //String mailID = (String)connectionHT["UserID_Name"];// "vishnu@cygnussoftwares.com";
            Hashtable smartShturlHT = null;
            smartShturlHT = OneDriveFileManipulation.getsmartSheetProp(connectionType, iisCommonConfigFilePath, iisCommonConfigFileName);
            String clintID = (String)smartShturlHT.get("Client_Id");
            String codeURL = (String)smartShturlHT.get("Code_URL");
            String redirectURL = (String)smartShturlHT.get("Redirect_URL");
            String strUrl = "";
            System.out.println("-=-=#####-=-=" + connectionType);
            if (connectionType.equalsIgnoreCase("smartsheet"))
            {
//                codeFilePath = smartsheetDir + "ProcessData.txt";
//                if (System.IO.File.Exists(codeFilePath))
//                    System.IO.File.Delete(codeFilePath);
                strUrl = Globals.getwrkflowUserloginUrl(iisCommonConfigFilePath, iisCommonConfigFileName, "Smartsheet_Auth");
            }
            else if (connectionType.equalsIgnoreCase("dropbox"))
            {
//                codeFilePath = dropboxDir + "ProcessData.txt";
//                if (System.IO.File.Exists(codeFilePath))
//                    System.IO.File.Delete(codeFilePath);
                strUrl = Globals.getwrkflowUserloginUrl(iisCommonConfigFilePath, iisCommonConfigFileName, "DropBox_Auth");
            }
            else if (connectionType.equalsIgnoreCase("google drive"))
            {
//                System.out.println("-=-=####11#-=-=" + connectionType.ToLower());
//                codeFilePath = googledriveDir + "ProcessData.txt";
//                if (System.IO.File.Exists(codeFilePath))
//                    System.IO.File.Delete(codeFilePath);
                strUrl = Globals.getwrkflowUserloginUrl(iisCommonConfigFilePath, iisCommonConfigFileName, "Googledrive_Auth");
                System.out.println("-=-strUrl-=-=" + strUrl);
            }
            else if (connectionType.equalsIgnoreCase("onedrive"))
            {
//                codeFilePath = onedriveDir + "ProcessData.txt";
//                if (!Directory.Exists(onedriveDir))
//                    Directory.CreateDirectory(onedriveDir);
//                if (System.IO.File.Exists(codeFilePath))
//                    System.IO.File.Delete(codeFilePath);
                strUrl = Globals.getwrkflowUserloginUrl(iisCommonConfigFilePath, iisCommonConfigFileName, "Onedrive_Auth");
            }
            if (!useridname.isEmpty())
            {
                strUrl = strUrl.replace("$$mailID$$", useridname).replace("$$Client_Id$$", clintID).replace("$$redirect$$", redirectURL);
            }
            System.out.println("-=-=ddddd-=-=" + strUrl);

            String timeoutStr = Globals.getwrkflowUserloginUrl(iisCommonConfigFilePath, iisCommonConfigFileName, "TimeoutCheck");
            int timeout = timeoutStr == null || timeoutStr.trim().length() <= 0 ? 15 : Integer.valueOf(timeoutStr);
            Boolean isCode = true;
            String codeStr = "";
            int count = 0;
            String errMsg = "";
            while (isCode && count < timeout)
            {
                count++;
                String mess = Globals.wrkflw_webService(strUrl, false);
                if (mess.toLowerCase().startsWith("error~"))
                {
                    codeStr = "error";
                    errMsg = mess.substring(mess.indexOf("~") + 1);
                    break;
                }
                else if (!mess.equalsIgnoreCase(""))
                {
                    codeStr = mess;
                    isCode = false;
                }
                else
                {
                    Thread.sleep(5000);
                }
            }
            System.out.println(connectionType + "-=-=-=-=666666666-=-=-=-=" + codeStr);
            if (codeStr.equalsIgnoreCase("error"))
            {
//                ScriptManager.RegisterStartupScript(this.Page, this.GetType(), "script", "alert('Error: " + errMsg + ".');", true);
            	authMsg = "failed";
                msg4WF = errMsg;
                return;
            }
            else if (codeStr.isEmpty())
            {
//                ScriptManager.RegisterStartupScript(this.Page, this.GetType(), "script", "alert('Error: Account is not authenticated.');", true);
            	authMsg = "failed";
                msg4WF = "Account is not authenticated.";
                return;
            }
            String code = "";
            if (connectionType.equalsIgnoreCase("dropbox"))
            {
                String[] temp = codeStr.split("?")[1].split("&");

                for (String str : temp)
                {
                    if (str.toLowerCase().contains("code"))
                    {
                        code = str.split("=")[1];
                    }
                }
            }
            else if (connectionType.trim().equalsIgnoreCase("google drive"))
            {
                String[] temp = codeStr.split("?")[1].split("&");

                for (String str : temp)
                {
                    if (str.toLowerCase().contains("code"))
                    {
                        code = str.split("=")[1];
                    }
                }

            }
            else
            {
                code = codeStr.split("=")[1].split("&")[0];
            }
            connectionHT.put("code", code);

            if (connectionType.equalsIgnoreCase("smartsheet") || connectionType.equalsIgnoreCase("onedrive"))
            {
                isExecute = OneDriveFileManipulation.getaccesToken(connectionHT, code, "authorization_code", codeFilePath, connectionType, iisCommonConfigFilePath, iisCommonConfigFileName);
            }
            else if (connectionType.equalsIgnoreCase("dropbox"))
            {

                isExecute = OneDriveFileManipulation.getaccesToken(connectionHT, code, "authorization_code", codeFilePath, connectionType, iisCommonConfigFilePath, iisCommonConfigFileName);
            }
            else if (connectionType.equalsIgnoreCase("google drive"))
            {
                isExecute = OneDriveFileManipulation.getaccesToken(connectionHT, code, "authorization_code", codeFilePath, connectionType, iisCommonConfigFilePath, iisCommonConfigFileName);
            }
            String accessToken = (String)connectionHT.get("access_token");
            System.out.println(connectionHT + "-=-=-=-=33333333333-=-=-=-=" + accessToken);

            if (isExecute)
            {
                Globals.addConnectionDetails(iisConfigFilePath, iisConnFilename, iisCommonConfigFileName, connectionHT, connectionname,
                		true, connectionType, iisCommonConfigFilePath);
                authMsg = "saved successfully";
                msg4WF = "Your account "+useridname+" is authenticated successfully.";
                System.out.println(isExecute + "-=-=-=-=44444444-=-=-=-=" + accessToken);
            }
            
        }
        catch (Exception ex)
        {
        	ex.printStackTrace();
            //File.WriteAllText("", ex.Message + "-=-=-=-=Exception-=-=-=-=" + ex.StackTrace);
//            System.out.println(ex.Message + "-=-=-=-=Exception-=-=-=-=" + ex.StackTrace);
//            ScriptManager.RegisterStartupScript(this.Page, this.GetType(), "script", "alert('Error: " + Globals.getExceptionMessage(ex) + ".');", true);
        }
        return;
    }
	
	public void gotoStageDetails() {
		
		
		
		try {
			rejectedPrevStage_Number="";
			System.out.println("checking");
		PropUtil pop=new PropUtil();
		String hierarchyDir=pop.getProperty("HIERARCHY_XML_DIR");
		String hierarchyXML=pop.getProperty("HIERARCHY_XML_FILE");
		//ArrayList<Object> rejectedReturn_StageNameAL=new ArrayList<>();
		rejectedReturngotostageAL=new ArrayList<>();
		Document xmlDoc = Globals.openXMLFile(hierarchyDir, hierarchyXML);
		Node workFlowN = Globals.getNodeByAttrVal(xmlDoc, "Workflow", "Hierarchy_ID", hierID);
		int currStageNo=Integer.parseInt(stageNumberFromAddStage);
		if(workFlowN != null){


			
			NodeList listnod=workFlowN.getChildNodes();
			Element workfloeEl=(Element)workFlowN;
			String totalStage=workfloeEl.getAttribute("Total_No_Stages");
			String stage_Name="";
			
			for(int i=0;i<listnod.getLength();i++){
				
				Node stagenode=listnod.item(i);
				if(stagenode.getNodeType()== Node.ELEMENT_NODE)
				{
				Element stagenodeELE=(Element)stagenode;
				int stage_No=Integer.parseInt(stagenodeELE.getAttribute("Stage_No"));
				
				
				if(currStageNo>stage_No && stage_No!=0 && stage_No!=-1) {
					rejectedPrevStage_Number=stagenodeELE.getAttribute("Stage_No");
					stage_Name=stagenodeELE.getAttribute("Stage_Name");
					rejectedReturngotostageAL.add(stage_Name);	
					
				}
				}
							
			}
			
		}
		
	
			
			System.out.println("rejectedReturngotostageAL::::::"+rejectedReturngotostageAL);
			
			
		
		
	}catch (Exception e) {
		// TODO: handle exception
		e.printStackTrace();
	}
		
	}
	public void refreshWorkFlow(String hierarchyID, String hierarchyName) { 

		System.out.println("Entering: " + new Exception().getStackTrace()[0].getClassName() + "."
				+ new Exception().getStackTrace()[0].getMethodName());

		try{

			HeaderBean head=new HeaderBean();
			//head.processMenu("Hierarchytree.xhtml");

			//code change remove this code pandian 21Mar2014 11.40pm
			if(tapName.equalsIgnoreCase("Default")){
				head.processMenu("WFadminscreen.xhtml");
			}else if(tapName.equalsIgnoreCase("Custom")){
				head.processMenu("customWFScreen.xhtml");
			}



			DashboardStage(tapName,"refresh", hierarchyID, hierarchyName);


			System.out.println("Exiting: " + new Exception().getStackTrace()[0].getClassName() + "."
					+ new Exception().getStackTrace()[0].getMethodName());



		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}





	}  

	public void DashboardStage(String workFlowType,String wfStagefrom, String hierarchyID, String hierarchyName) { 

		System.out.println("Entering: " + new Exception().getStackTrace()[0].getClassName() + "."
				+ new Exception().getStackTrace()[0].getMethodName());

		try{
			//			msg4WF="";   
			
			
			this.hierID = hierarchyID;
			this.hierName = hierarchyName;
			
//			HttpServletRequest req = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
//			this.urlParameter=req.getParameter("advanceWorkflow")== null || req.getParameter("advanceWorkflow").length()<= 0 ? "false" :req.getParameter("advanceWorkflow");
//			this.urlLogin=req.getParameter("emailid");
//			this.urlWfhandshkeID=req.getParameter("hand_shake_key");
//			this.urlcus_key=getcustomerkey(this.urlLogin);
			
			System.out.println("**********DashboardStage************:"+this.urlParameter);
						
			if(this.urlParameter.equalsIgnoreCase("true") || this.editwrkflowflag.equalsIgnoreCase("true")){
				saveASPlogindetails(this.urlLogin);
				this.closeDisplay="none";
				this.closeDisplayADV="block";
				
				System.out.println("**********editwrkflowflag************:"+this.editwrkflowflag);
				
			}else {
				
				this.closeDisplay="block";
				this.closeDisplayADV="none";
				
			}
			this.msg4WF="";
			setStageMsg = "";
			model = new DefaultDashboardModel();  
			modelold = new DefaultDashboardModel();
			DashboardColumn column1 = new DefaultDashboardColumn();  
			DashboardColumn column2 = new DefaultDashboardColumn();  
			DashboardColumn column3 = new DefaultDashboardColumn();  


			//	        FacesContext ctx = FacesContext.getCurrentInstance();
			//			ExternalContext extContext = ctx.getExternalContext();
			//			Map sessionMap = extContext.getSessionMap();
			//			HierarchyBean hrybn = (HierarchyBean) sessionMap.get("hierarchyBean");
			//			workFlowBean wrkbn = (workFlowBean) sessionMap.get("workflowbean");
			//			DashboardBean dashbnbn = (DashboardBean) sessionMap.get("dashboardBean");

			//			dashbnbn.setPanelAL(new ArrayList<>());
			//			dashbnbn.setStageNameValue("s");

			selectionType = workFlowType;
			tapName = workFlowType;
			if(!wfStagefrom.equals("refresh") && !wfStagefrom.equals("modify")){
				workFlowBean wfb = new workFlowBean();
				wfb.loadDefaultLayoutTree(workFlowType, hierarchyID);
			}
			//	        String hierarchyID=hrybn.getHierarchy_ID();

			PropUtil pop=new PropUtil();
			String hierarchyDir=pop.getProperty("HIERARCHY_XML_DIR");//"E:\\Hierarchy project sub Backup\\Hierarchy Project\\Hierarchy\\XML DATA\\";
			String hierarchyXML=pop.getProperty("HIERARCHY_XML_FILE");//"HierachyLevel.xml";


			//	        {EmployeedetHT={
			//	        		2={empName=Vishnu, Last_Access=13-03-2014 03:25 PM, User_Status=Completed, E-mail=vishnu@cygnussoftwares.com, Access_Unique_ID=387}, 
			//	        				1={empName=Gokul, Last_Access=13-03-2014 03:25 PM, User_Status=Completed, E-mail=Gokul@cygnussoftwares.com, Access_Unique_ID=456},
			//	        				0={empName=Pandian, User_Status=YTS, E-mail=pandi@cygnussoftwares.com, Access_Unique_ID=134}},
			//	          MessagedetHT={Final=Completed, 2=WIP, 1=YTS},  
			//	          Stage_No=1,
			//	          PropertiesHT={Properties=Serial, Concurrency=Serial}, 
			//	          Mail_Sent_Time=13-03-2014 03:25 PM, 
			//	          Stage_Status=Completed,
			//	          Stage_Name=Creator,
			//	          MailSent=Yes}


			ruleAttrAL.clear();

			Document xmlDoc = Globals.openXMLFile(hierarchyDir, hierarchyXML);
			Node workFlowN = Globals.getNodeByAttrVal(xmlDoc, "Workflow", "Hierarchy_ID", hierarchyID);
			if(workFlowN != null){



				Element workfloeEl=(Element)workFlowN;
				String totalStage=workfloeEl.getAttribute("Total_No_Stages");
				String ruleAttriName = workfloeEl.getAttribute("RuleAttributeName") == null ? "" : workfloeEl.getAttribute("RuleAttributeName");
				String ruleAttriType = workfloeEl.getAttribute("RuleAttributeType") == null ? "" : workfloeEl.getAttribute("RuleAttributeType");
				String ruleAttriDataType = workfloeEl.getAttribute("RuleAttributeDataType") == null ? "" : workfloeEl.getAttribute("RuleAttributeDataType");
				if(!ruleAttriName.isEmpty()) {
					String[] opt = ruleAttriName.split("#~#");
					List<String> tempAL = Arrays.asList(opt);
					String[] ruleAttriTypeArr = ruleAttriType.split("#~#");
					List<String> ruleAttriTypeAL = Arrays.asList(ruleAttriTypeArr);
					String[] ruleAttriDataTypeArr = ruleAttriDataType.split("#~#");
					List<String> ruleAttriDataTypeAL = Arrays.asList(ruleAttriDataTypeArr);
					for(int i=0;i<tempAL.size();i++) {
						if(ruleAttriTypeAL.size() <= i || ruleAttriDataTypeAL.size() <= i)
							continue;
						RuleAttributeDataModel radm = new RuleAttributeDataModel(String.valueOf(i+1), tempAL.get(i), ruleAttriTypeAL.get(i), ruleAttriDataTypeAL.get(i));
						ruleAttrAL.add(radm);
					}
				}
				System.out.println("Total Stage "+totalStage+" hierarchyID "+hierarchyID);
				int totstage=Integer.parseInt(totalStage);

				String workflowstasge=workfloeEl.getAttribute("Workflow_Level");
				if(workflowstasge.equals("Custom")){

					this.setSelectionType("Custom");

				}else  if(workflowstasge.equals("Default")){
					this.setSelectionType("Default");
				}

				LoginProcessManager lgnmngr=new LoginProcessManager();
				boolean firstEnter=true;
				boolean secondEnter=true;
				boolean thirdEnter=true;

				panelAL=new ArrayList<>(); //code change pandian 20MAR2014
				panelModelAL=new ArrayList<>();

				System.out.println("totstage "+totstage);


				NodeList wfNL=workFlowN.getChildNodes();

				ArrayList list=new ArrayList<>();
				for(int j=0;j<wfNL.getLength();j++){
					Node nl=wfNL.item(j);
					if(nl.getNodeType()==Node.ELEMENT_NODE){

						Hashtable  currentStageHT=new Hashtable();
						currentStageHT=Globals.getAttributeNameandValHT(nl);
						int currentstage=Integer.parseInt(String.valueOf(currentStageHT.get("Stage_No")));

						list.add(currentstage);

					}
				}

				//sort the list
				Collections.sort(list);

				int fieldsize; //code change Jayaramu 22MAR14

				if(list.size() == 1){ 
					fieldsize = 400;

				}else{
					fieldsize = 400+(list.size()*200);
				}
				fieldsetWidth = String.valueOf(fieldsize); //code change Jayaramu 22MAR14

				int k=0;
				for(int h=0;h<list.size();h++){

					int currentstage=(int)list.get(h);

					System.out.println("currentstage "+currentstage);
					Hashtable stageDetailsHT=lgnmngr.getStageDetails(hierarchyID, String.valueOf(currentstage),"");
					String stagename=(String)stageDetailsHT.get("Stage_Name");
					System.out.println("stagename  ; :"+stagename);
					String stageno=String.valueOf(stageDetailsHT.get("Stage_No"));
					System.out.println("stagename "+stagename);
					if(stagename!=null){


						if(stagename.equals("public") || stagename.equals("Admin")) {

							column1 = new DefaultDashboardColumn(); //code change Jayaramu 22MAR14

							column1.addWidget(stagename); 

							modelold.addColumn(column1);

						}else {
							column1 = new DefaultDashboardColumn(); //code change Jayaramu 22MAR14

							column1.addWidget("Stage_"+k); 

							model.addColumn(column1);
							k++;
						}


						Hashtable employeeDetailsHT=(Hashtable)stageDetailsHT.get("EmployeedetHT");
						Hashtable statusmsgDetailsHT=(Hashtable)stageDetailsHT.get("MessagedetHT");
						Hashtable propDetailsHT=(Hashtable)stageDetailsHT.get("PropertiesHT");
						//	     	        	 if(firstEnter){
						//	     	        		
						//	     	        		 column1.addWidget(stagename);        		 
						//
						//	     	        		     firstEnter=false;
						//	     	        	         secondEnter=true;
						//	     	        	         thirdEnter=false;
						//	     	        	 }else if(secondEnter){
						//	     	        		 
						//	     	        		 column2.addWidget(stagename);  
						//	     	        		 firstEnter=false;
						//	     	    	         secondEnter=false;
						//	     	    	         thirdEnter=true;
						//	     	        	 }else if(thirdEnter){
						//	     	        		 
						//	     	        		 column3.addWidget(stagename);   
						//
						//	     	        		 firstEnter=true;
						//	     	    	         secondEnter=false;
						//	     	    	         thirdEnter=false;
						//	     	        	 }



						////////////////////
						//	     	        	 panelAL.add(stagename);

						SMsgAL=new ArrayList<>();

						int statusnum=0;
						if(statusmsgDetailsHT!=null){   //code change pandian 20MAR20149pm


							for(int i=0;i<statusmsgDetailsHT.size();i++){
								statusnum=i+1;
								String stmsg="";
								if(statusnum==statusmsgDetailsHT.size()){
									stmsg=(String)statusmsgDetailsHT.get("Final");
								}else{
									stmsg=(String)statusmsgDetailsHT.get(String.valueOf(statusnum));
								}		

								//	     	       		   System.out.println("============> msg "+stmsg);
								SMsgAL.add(stmsg);
							}	

						}

						empNamesAL=new ArrayList<>();
						if(employeeDetailsHT!=null){   //code change pandian 20MAR20149pm
							for(int i=0;i<employeeDetailsHT.size();i++){
								Hashtable empHT=(Hashtable)employeeDetailsHT.get(i);
								String empName=(String)empHT.get("empName");
								empNamesAL.add(empName);


							}

						}

						String minApprv="";
						String prop="";
						String minApprovLbleRender="";
						if(propDetailsHT!=null){   //code change pandian 20MAR20149pm
							String propties=(String)propDetailsHT.get("Properties");

							if(propties.equals("Serial")){       		  
								minApprv="0";
								prop="Serial";
								minApprovLbleRender="false";
							}else  if(propties.equals("Parallel")){       
								prop="Parallel";
								minApprv=(String)propDetailsHT.get("Min_Approvers");      		 
								minApprovLbleRender="true";
							}

						}

						dashboardManager dashbrd=new dashboardManager(stagename,SMsgAL,empNamesAL,prop,minApprv,stageno,minApprovLbleRender);
						if(stagename.equalsIgnoreCase("public") || stagename.equalsIgnoreCase("admin")) {
							panelModelAL.add(dashbrd);
						}else {
							panelAL.add(dashbrd);
						}




						//	panelAL.remove(0);
						//	panelAL.remove(1);

						/////////////////////

					} //stage !=null

				}



			}

			System.out.println("Total Default Column Count : "+modelold.getColumnCount());
			System.out.println("Exiting: " + new Exception().getStackTrace()[0].getClassName() + "."
					+ new Exception().getStackTrace()[0].getMethodName());


		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}  
	public void pageOnLoadAction() {
		System.out.println("Page load Action");
	}
	String color4WFMsg = "";
	public String getColor4WFMsg() {
		return color4WFMsg;
	}
	public void setColor4WFMsg(String color4wfMsg) {
		color4WFMsg = color4wfMsg;
	}
	public void deleteStage(String stageName,String stageNO) { 
		System.out.println("Entering: " + new Exception().getStackTrace()[0].getClassName() + "."
				+ new Exception().getStackTrace()[0].getMethodName());
		try{
			System.out.println("Delete the Stage : "+stageName+" Stage Number :"+stageNO);
			msg4WF = "";
			FacesContext ctx = FacesContext.getCurrentInstance();
			ExternalContext extContext = ctx.getExternalContext();
			Map sessionMap = extContext.getSessionMap();
			//HierarchyBean hrybn = (HierarchyBean) sessionMap.get("hierarchyBean");
			//			String hierarchyID=hrybn.getHierarchy_ID();
			String hierarchyID=hierID;

			workFlowBean ef=new workFlowBean();
			String stagestatus=ef.deleteNodefromWorkflow(hierarchyID,"Stage",stageNO,stageName,"");
			//code change pandian 09APR2014
			System.out.println(" stagestatus: *** : "+stagestatus);
			if(stagestatus.equalsIgnoreCase("sendWarnningMsgCurrent")){
				// send warning status
				msg4WF="Error: The Stage:"+stageName+" you are trying to delete is active (current stage). So you cannot delete this Stage.";//msg4WF="WorkFlow Stage :"+stageName+" is Current Stage So You can't delete this Stage.";
				System.out.println(" 1. msg4WF : *** : "+msg4WF);
				color4WFMsg = "white";
			}else if(stagestatus.equalsIgnoreCase("sendWarnningMsgCompleted")){
				// send warning status
				msg4WF="Error: The Stage:"+stageName+" you are trying to delete is active (completed). So you cannot delete this Stage.";//msg4WF="WorkFlow Stage :"+stageName+" is already Completed So You can't delete this Stage.";
				System.out.println(" 2. msg4WF : *** : "+msg4WF);
				color4WFMsg = "white";
			}


			DashboardStage("Default", "hierarchyTree", hierarchyID, "");
			///refreshWorkFlow(hierarchyID, hierName);

		}catch(Exception e){
			e.printStackTrace();  }
		System.out.println("Exiting: " + new Exception().getStackTrace()[0].getClassName() + "."
				+ new Exception().getStackTrace()[0].getMethodName());

	}

	private String renameStage=""; 

	public String getRenameStage() {
		return renameStage;
	}
	public void setRenameStage(String renameStage) {
		this.renameStage = renameStage;
	}
	public void setVarable2RenameStage(String stageName,String stageNO) { 
		//		 System.out.println("Entering: " + new Exception().getStackTrace()[0].getClassName() + "."
		//	             + new Exception().getStackTrace()[0].getMethodName());
		try{
			msg4WF="";
			//renameStage="";
			stageNameFromAddStage = "";
			stageNumberFromAddStage = "";
			System.out.println("Rename Stage:  the Stage : "+stageName+" Stage Number :"+stageNO);
			renameStage=stageName;
			stageNameFromAddStage = stageName;
			stageNumberFromAddStage = stageNO;


		}catch(Exception e){
			e.printStackTrace();  }
		//	  	  System.out.println("Exiting: " + new Exception().getStackTrace()[0].getClassName() + "."
		//		             + new Exception().getStackTrace()[0].getMethodName());

	}

	public void renameStage(String renameStagefrmfrnt) {
		System.out.println("Entering: " + new Exception().getStackTrace()[0].getClassName() + "."
				+ new Exception().getStackTrace()[0].getMethodName());
		try{
			System.out.println("RenameStage the Stage : "+renameStagefrmfrnt+" old Stage Name :"+stageNameFromAddStage+"  Stage Number :"+stageNumberFromAddStage);


			if(stageNameFromAddStage.equals("Admin") || stageNameFromAddStage.equals("public")){
				msg4WF="Error: The Stage name is "+stageNameFromAddStage+".So you cannot rename this Stage.";
				color4WFMsg = "white";
				renameStage=""; 
				return;
			}

			FacesContext ctx = FacesContext.getCurrentInstance();
			ExternalContext extContext = ctx.getExternalContext();
			Map sessionMap = extContext.getSessionMap();
			//HierarchyBean hrybn = (HierarchyBean) sessionMap.get("hierarchyBean");
			//			String hierarchyID=hrybn.getHierarchy_ID();
			String hierarchyID=hierID;

			PropUtil prop=new PropUtil();
			String heirLevelXML=prop.getProperty("HIERARCHY_XML_FILE");
			String heirLeveldir=prop.getProperty("HIERARCHY_XML_DIR");
			Document xmlDoc=Globals.openXMLFile(heirLeveldir, heirLevelXML);
			Node workFlowN=Globals.getNodeByAttrVal(xmlDoc, "Workflow", "Hierarchy_ID", hierarchyID);
			if(workFlowN!=null)
			{
				//code change pandian 09APR2014
				Element workFlowNEL=(Element)workFlowN;
				String totalStage=workFlowNEL.getAttribute("Total_No_Stages");
				String currentStageName=workFlowNEL.getAttribute("Current_Stage_Name");
				String currentStageNo=workFlowNEL.getAttribute("Current_Stage_No");

				NodeList listnod=workFlowN.getChildNodes();
				for(int stagenod=0;stagenod<listnod.getLength();stagenod++){
					Node stagenode=listnod.item(stagenod);
					if(stagenode.getNodeType()==Node.ELEMENT_NODE && stagenode.getNodeName().equals("Stage")){
						Element StageNEL=(Element)stagenode; 
						String nameOfStage= StageNEL.getAttribute("Stage_Name");
						if(nameOfStage.equals(renameStagefrmfrnt)){
							msg4WF="Error: The Stage name :"+stageNameFromAddStage+" is already available in this workflow. So you cannot rename please try Using another name.";
							color4WFMsg = "white";
							return;

						}


					}

				}

				/*if(currentStageNo.equalsIgnoreCase(stageNumberFromAddStage)){

					msg4WF="Error: The Stage:"+stageNameFromAddStage+" you are trying to rename the stage is active (current stage). So you cannot rename this Stage.";
					color4WFMsg = "red";
					renameStage=""; 
					return;

					//							workFlowNEL.setAttribute("Current_Stage_Name",renameStage);
				}else */if(currentStageNo.equals("Completed")){ // && totalStage.equals(stageNumberFromAddStage)){

					msg4WF="Error: The Stage:"+stageNameFromAddStage+" you are trying to rename the stage is active (completed). So you cannot rename this Stage.";
					color4WFMsg = "white";
					renameStage=""; 
					return;
					//							workFlowNEL.setAttribute("Current_Stage_Name",renameStage);
				}

				Node StageN=Globals.getNodeByAttrValUnderParent(xmlDoc, workFlowN,"Stage_No", stageNumberFromAddStage);
				if(StageN!=null)
				{
					Element StageNEL=(Element)StageN; 
					String stautsOfStage= StageNEL.getAttribute("Stage_Status");
					String stageNo = StageNEL.getAttribute("Stage_No");
					if(stautsOfStage.equals("Completed")){
						msg4WF="Error: The Stage:"+stageNameFromAddStage+" you are trying to rename the stage is active (completed). So you cannot rename this Stage.";
						color4WFMsg = "white";
						renameStage=""; 
						return;
					}else{
						StageNEL.setAttribute("Stage_Name", renameStagefrmfrnt);
						workFlowNEL.setAttribute("Current_Stage_Name", stageNo.equalsIgnoreCase(currentStageNo) ? renameStagefrmfrnt : currentStageName);
					}

					Globals.writeXMLFile(xmlDoc, heirLeveldir, heirLevelXML);


				}


			}

			DashboardStage("Default", "hierarchyTree", hierarchyID, "");

			renameStage=""; 

//			refreshWorkFlow(hierarchyID, hierName);

		}catch(Exception e){
			e.printStackTrace();  }
		System.out.println("Exiting: " + new Exception().getStackTrace()[0].getClassName() + "."
				+ new Exception().getStackTrace()[0].getMethodName());

	}

	public void handleReorder(DashboardReorderEvent event) { 


		FacesMessage message = new FacesMessage();  
		message.setSeverity(FacesMessage.SEVERITY_INFO);  
		message.setSummary("Reordered: " + event.getWidgetId());  
		message.setDetail("Item index: " + event.getItemIndex() + ", Column index: " + event.getColumnIndex() + ", Sender index: " + event.getSenderColumnIndex());  
		stageNameValue = "Enter_Stage_Name";
		addMessage(message); 
		//        refreshWorkFlow();
		modifyChanges("addOrModify", hierName);
		System.out.println("model.getColumnCount();"+model.getColumnCount());
		//        System.out.println("model.getColumnCount();"+model.getColumnCount());
		//        System.out.println("model.getColumnCount();"+model.getColumns().get(0).getWidget(0)); 
		//        System.out.println("model.getColumnCount();"+model.getColumns().get(1).getWidget(0)); 
		//        System.out.println("model.getColumnCount();"+model.getColumns().get(2).getWidget(0));
		//        System.out.println("model.getColumnCount();"+model.getColumns().iterator().hasNext()); 

	}  






	public void modifyChanges(String changesFrom, String hierName) { 

		System.out.println("Entering: " + new Exception().getStackTrace()[0].getClassName() + "."
				+ new Exception().getStackTrace()[0].getMethodName());
		try{
			this.hierName = hierName;
			msg4WF=""; 
			
			if(hierName.equals("") && hierName.isEmpty()) {
			msg4WF="Please enter the workflow name."; 
			color4WFMsg = "white";
			return;
		}
			
			  
		//	System.out.println("hierName : "+hierName);
		//	System.out.println("Total Column Count : "+model.getColumnCount());



//			if(hierName.equals("") && hierName.isEmpty()) {
//				msg4WF="Please enter the workflow name";   
//				color4WFMsg = "red";
//				return;
//			}

			//            for(int col=0; col<model.getColumnCount();col++){
			//            	
			//            	if(model.getColumns().get(col)!=null){
			//            		 for(int row=0; row<model.getColumns().get(col).getWidgetCount();row++){
			//            			 System.out.println(" Row Number : "+row+" Column Number : "+col +" Widget Name : "+model.getColumns().get(col).getWidget(row));
			//            		 }
			//            	}
			//            }

			DashboardModel model = new DefaultDashboardModel();  
			if(changesFrom.equals("DefaultScreen") || changesFrom.equals("addOrModify")){
				model = this.model;
			}else if(changesFrom.equals("CustomScreen")){

				model = this.custommodel;
			}
			PropUtil prop=new PropUtil();
			String heirLevelXML=prop.getProperty("HIERARCHY_XML_FILE");
			String heirLeveldir=prop.getProperty("HIERARCHY_XML_DIR");
			Document xmlDoc=Globals.openXMLFile(heirLeveldir, heirLevelXML);

			FacesContext ctx = FacesContext.getCurrentInstance();
			ExternalContext extContext = ctx.getExternalContext();
			Map sessionMap = extContext.getSessionMap();
			HierarchyBean hrybn = (HierarchyBean) sessionMap.get("hierarchyBean");
			LoginBean lb = (LoginBean) sessionMap.get("loginBean");
			
			DashboardBean dshbn = (DashboardBean) sessionMap.get("dashboardBean");
			String dashboardURL= dshbn.getUrlParameter();
			String dashboardEditURL= dshbn.getEditwrkflowflag();
			String currentLogin="";
			String currentLoginKEY="";
			String currenthandshkeID="";
			
			
			if(dashboardURL.equalsIgnoreCase("true") || dashboardEditURL.equalsIgnoreCase("true")) {
				
				currentLogin=dshbn.getUrlLogin();
				currentLoginKEY=dshbn.getUrlcus_key();
				currenthandshkeID=dshbn.getUrlWfhandshkeID();
				
				
			}else {
				
				currentLogin=lb.username;
				currentLoginKEY=lb.getCustomerKey();
				currenthandshkeID="";
			}
			
			//			String hierarchyID=hrybn.getHierarchy_ID();
			String hierarchyID=hierID;
			String dateFormat = prop.getProperty("DATE_FORMAT");
			Date HIERYDate = new Date();
			DateFormat sdf1 = new SimpleDateFormat(dateFormat);
			String StartDate = sdf1.format(HIERYDate);
			Node workFlowN=Globals.getNodeByAttrVal(xmlDoc, "Workflow", "Hierarchy_ID", hierarchyID);
			Element hierEle=(Element)Globals.getNodeByAttrVal(xmlDoc, "Hierarchy_Level", "Hierarchy_ID", hierarchyID);

			if(hierEle == null) {
				//				Hierarchy_Level Code_Combination="DontCreateCodeCombination" Created_By="rbid" Created_Date="2014/07/22 14:38:26" Dim_Status="Not Generated" 
				//				Dim_Status_Details="Not Generated" Hierarchy_Category="" Hierarchy_ID="385" Hierarchy_Name="hierar22" Modified_By="rbid" Modified_Date="2014/07/22 14:39:51" 
				//						RI_Hierarchy_Type="Independent" Unique_ID="87778" Version="Master">

				if(hierEle == null) {
					hierEle = xmlDoc.createElement("Hierarchy_Level"); 
					xmlDoc.getFirstChild().appendChild(hierEle);
				}

				hierEle.setAttribute("Hierarchy_ID", hierarchyID);
				hierEle.setAttribute("Hierarchy_Name", hierName);
				hierEle.setAttribute("Created_By", currentLogin);
				hierEle.setAttribute("Modified_By", currentLogin);
				hierEle.setAttribute("Created_Date", StartDate);
				hierEle.setAttribute("Modified_Date", StartDate);
				hierEle.setAttribute("Unique_ID", uniqueid);
				hierEle.setAttribute("Hand_Shake_Key", currenthandshkeID);
			}else {
				if(hierEle.getAttribute("Hierarchy_Name").isEmpty()) {
					hierEle.setAttribute("Hierarchy_ID", hierarchyID);
					hierEle.setAttribute("Hierarchy_Name", hierName);
					hierEle.setAttribute("Created_By", currentLogin);
					hierEle.setAttribute("Modified_By", currentLogin);
					hierEle.setAttribute("Created_Date", StartDate);
					hierEle.setAttribute("Modified_Date", StartDate);
					hierEle.setAttribute("Unique_ID", uniqueid);
					hierEle.setAttribute("Hand_Shake_Key", currenthandshkeID);
				}else {
					hierEle=(Element)Globals.getNodeByAttrVal(xmlDoc, "Hierarchy_Level", "Hierarchy_ID", hierarchyID);
					hierEle.setAttribute("Hierarchy_Name", hierName);
					hierEle.setAttribute("Modified_By", currentLogin);
					hierEle.setAttribute("Modified_Date", StartDate);
					hierEle.setAttribute("Hand_Shake_Key", currenthandshkeID);
				}
			}
			hierEle.setAttribute("CustomerKey", currentLoginKEY);
			Element wfEle = (Element) workFlowN;
			if(wfEle != null)	wfEle.setAttribute("CustomerKey", currentLoginKEY);
			int bigcolCount=0;
			Hashtable colcountHT=new Hashtable<>();
			for(int col=0; col<model.getColumnCount();col++){            	 
				if(model.getColumns().get(col)!=null){
					System.out.println("------->"+model.getColumns().get(col).getWidgetCount());
					colcountHT.put(col, model.getColumns().get(col).getWidgetCount());
					if(bigcolCount<model.getColumns().get(col).getWidgetCount()){
						bigcolCount=model.getColumns().get(col).getWidgetCount();
					}
				}
			}



			int modifystagenumb=1;
			
			for(int col=0; col<model.getColumnCount();col++){
				bigcolCount=model.getColumns().get(col).getWidgetCount();
				for(int row=0;row<bigcolCount;row++){
					if(model.getColumns().get(col)!=null){
						System.out.println("------->"+model.getColumns().get(col).getWidgetCount());

						int conum=(int)colcountHT.get(col);
						if(conum!=0){
							if(model.getColumns().get(col).getWidget(row)!=null){    

								if(workFlowN!=null){
									//        					 NodeList stageNL=workFlowN.getChildNodes();
									//        						for(int i=0;i<stageNL.getLength();i++){
									//        							Node stageN=stageNL.item(i);
									//        							if(stageN.getNodeType()==Node.ELEMENT_NODE)
									//        							{
									//        								
									//        							}
									//        						}



									System.out.println(" Row Number : "+row+" Column Number : "+col +" Widget Name : "+model.getColumns().get(col).getWidget(row));

									String indexStr = model.getColumns().get(col).getWidget(row).replace("Stage_", "");
									String StageName=((dashboardManager)panelAL.get(Integer.parseInt(indexStr))).getPanelName();
									Node updateStageN=Globals.getNodeByAttrValUnderParent(xmlDoc, workFlowN,"Stage_Name", StageName);
									Element workFlowE=(Element)workFlowN;
									Element updateStageNEL=(Element)updateStageN;


									if(StageName.equals("Admin") || StageName.equals("public")){

									}else{
										updateStageNEL.setAttribute("Stage_No", String.valueOf(modifystagenumb));  
										if(modifystagenumb==1)//Start Code Change Gokul 07MAY2014
										{
											workFlowE.setAttribute("Current_Stage_Name",StageName);
										}//End Code Change Gokul 07MAY2014
										modifystagenumb++;
									}




								}

								conum=conum-1;
								colcountHT.put(col, conum);
							}
						}

					}


				}

			}    

			Globals.writeXMLFile(xmlDoc, heirLeveldir, heirLevelXML);


			stageModel = new DefaultDashboardModel(); 
			stageNameValue="Enter_Stage_Name";



			//    		//head.processMenu("Hierarchytree.xhtml");
			//
			HeaderBean head=new HeaderBean();
			if(changesFrom.equals("DefaultScreen") || changesFrom.equals("addOrModify")){
				hrybn.setStageMessageinFront(hierID);
				//head.processMenu("WFadminscreen.xhtml");
			}else if(changesFrom.equals("CustomScreen")){
				cusStageNameValue = "EnterStage";
				head.processMenu("customWFScreen.xhtml");
			}
			DashboardStage(tapName,"modify", hierarchyID, hierName);
			if(!changesFrom.equals("addOrModify")){
//				HttpServletRequest req = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
//				this.urlParameter=req.getParameter("advanceWorkflow")== null || req.getParameter("advanceWorkflow").length()<= 0 ? "false" :req.getParameter("advanceWorkflow");
				
				if(this.urlParameter.equalsIgnoreCase("true")) {
					
					this.msg4WF="Workflow "+hierName+" is saved successfully. Please click [Close] button to go back.";  
					
					
				}else {
					
					
					this.msg4WF="Workflow is saved successfully."; 
					
				}
				
				
				color4WFMsg = "white";
			}


		}catch(Exception e){
			
			e.printStackTrace();
			
			}



		System.out.println("Exiting: " + new Exception().getStackTrace()[0].getClassName() + "."
				+ new Exception().getStackTrace()[0].getMethodName());

	}

	public void externalUserValue() {
		

		try {
			
			
			
		}catch(Exception e){
			e.printStackTrace();  }

		
	}
	public void moveForwardBackward(String cpmeFrom,String process){

		try {
			PropUtil prop=new PropUtil();

			String configFileName=prop.getProperty("HIERARCHY_CONFIG_FILE");
			String hierLeveldir=prop.getProperty("HIERARCHY_XML_DIR");
			Document docs=Globals.openXMLFile(hierLeveldir, configFileName);
			Element rejectedval = (Element)docs.getElementsByTagName("Rejected_Return").item(0);
			Element pausehandl = (Element)docs.getElementsByTagName("Pause_Handling").item(0);
			if(process.equals("forward")){

				if(cpmeFrom.equals("statusCodes")){

					for(int i=0;i<selectedStatusCodes.length;i++){
						if(selectedStatusCodes[i].equalsIgnoreCase("Rejected")){ //code change Jayaramu 10APR14 
							showRejectedButton = true;
							showhandleTitleBox="block";
							rejectedReturn2 = rejectedval.getAttribute("Previus_Stage");
							rejectedSetStatus2=rejectedval.getAttribute("Set_Status");
							rejectedSendNotification2 = rejectedval.getAttribute("Send_Notification");
						}
						if(selectedStatusCodes[i].equalsIgnoreCase("Pause")){ //code change Jayaramu 10APR14 
							showPassButton = true;
							showhandleTitleBox="block";
							passSendNotification2=pausehandl.getAttribute("Send_Notificaion_Flag");
						}
						if(selectedStatusCodes[i].equalsIgnoreCase("Pause") || selectedStatusCodes[i].equalsIgnoreCase("Rejected")){ //code change VIJAY

							showhandleTitleBox="block";
						}
						statusCodesAL.remove(selectedStatusCodes[i]);
						viewstagestatusAL.add(selectedStatusCodes[i]);
						frwdStatusCodes = selectedStatusCodes;	//code change Vishnu 10Apr2014 selected user to highlight
					}


				}else if(cpmeFrom.equals("members")){

					for(int i=0;i<selectedMembers.length;i++){

						membersNameAL.remove(selectedMembers[i]);
						allMembersNameAL.remove(selectedMembers[i]);

						viewMenberNameAL.add(selectedMembers[i]);
						allviewMenberNameAL.add(selectedMembers[i]);
						frwdMembers = selectedMembers; 	//code change Vishnu 10Apr2014 selected user to highlight
					}
					Collections.sort(membersNameAL);

				}

			}else if(process.equals("backward")){

				if(cpmeFrom.equals("statusCodes")){

					for(int i=0;i<frwdStatusCodes.length;i++){
						if(frwdStatusCodes[i].equalsIgnoreCase("Rejected")){ //code change Jayaramu 10APR14
							showRejectedButton = false;
							showhandleTitleBox="none";
						}
						if(frwdStatusCodes[i].equalsIgnoreCase("Pause")){ //code change Jayaramu 10APR14 
							showPassButton = false;
							showhandleTitleBox="none";
						}
						if(frwdStatusCodes[i].equalsIgnoreCase("Pause")||frwdStatusCodes[i].equalsIgnoreCase("Rejected")){ //code change Vijay 
							System.out.println("ZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZ");
							showhandleTitleBox="none";
						}
						statusCodesAL.add(frwdStatusCodes[i]);
						viewstagestatusAL.remove(frwdStatusCodes[i]);
					}



				}else if(cpmeFrom.equals("members")){

					for(int i=0;i<frwdMembers.length;i++){

						membersNameAL.add(frwdMembers[i]);

						allMembersNameAL.add(frwdMembers[i]);
						viewMenberNameAL.remove(frwdMembers[i]);
						allviewMenberNameAL.remove(frwdMembers[i]);
					}
					Collections.sort(membersNameAL);

				}
			}
		}catch(Exception ex) {
			ex.printStackTrace();
		}


	}

	public void filter() {

		try {

			membersNameAL.clear();
			for(int i=0;i<allMembersNameAL.size();i++)
			{
				if(((String) allMembersNameAL.get(i)).contains(filterVal.toLowerCase()))

				{
					membersNameAL.add((String) allMembersNameAL.get(i));
				}

			}

			Collections.sort(membersNameAL);

		
		}
		catch(Exception e)
		{
			//System.out.println(e.printStackTrace());
		}



	}

	public void filter1() {

		try {

			viewMenberNameAL.clear();
			
			for(int i=0;i<allviewMenberNameAL.size();i++)
			{
				if(((String) allviewMenberNameAL.get(i)).contains(filterVal1.toLowerCase()))

				{
					viewMenberNameAL.add((String) allviewMenberNameAL.get(i));
				}

			}

			Collections.sort(viewMenberNameAL);

		
		}
		catch(Exception e)
		{
			//System.out.println(e.printStackTrace());
		}



	}



	public void writeStagesToXml(String saveType){ 
		try{



			//			if(selectedMember==null){
			//
			//				setStageMsg = "Please select a selected Member to save";
			//				return;
			//			}



			/*	if(selectedStageRole==null){

				setStageMsg = "Please select a Role to save";
				return;
			}*/
			//code change Vishnu 10Apr2014
			System.out.println("viewMenberNameAL==>"+viewMenberNameAL.size());
			System.out.println("viewstagestatusAL==>"+viewstagestatusAL.size());
			if(viewMenberNameAL.size()==0){
				setStageMsg = "Please select Member Code before save.";
				setStageMsgcolor="red";
				return;
			}

			if(!visable4publicNadmin.equals("false"))
				if(viewstagestatusAL.size()==0){
					setStageMsg = "Please select Status Code before save.";
					setStageMsgcolor="red";
					return;
				}



			setStageMsg = "";


			String[] empNames = new String[viewMenberNameAL.size()]; 
			String[] statusMsg = new String[viewstagestatusAL.size()]; 
			String[] empUid = new String[viewMenberNameAL.size()];
			String[] empEmail = new String[viewMenberNameAL.size()];

			for(int i=0;i<viewstagestatusAL.size();i++){

				statusMsg[i] = (String) viewstagestatusAL.get(i);

			}


			for(int i=0;i<viewMenberNameAL.size();i++){	
				//	empNameWithID = (String) viewMenberNameAL.get(i);
				//	empUid[i] = empNameWithID.substring(empNameWithID.indexOf("(")+1,empNameWithID.indexOf(")"));
				//	empUid[i] =empNameWithID;
				empNames[i] = (String) viewMenberNameAL.get(i);
			}



			PropUtil prop=new PropUtil();
			String configFileName=prop.getProperty("HIERARCHY_CONFIG_FILE");
			String hierLeveldir=prop.getProperty("HIERARCHY_XML_DIR");
			String hierXmlFileName=prop.getProperty("HIERARCHY_XML_FILE");
			Document docs=Globals.openXMLFile(hierLeveldir, configFileName);
			Element rejectedval = (Element)docs.getElementsByTagName("Rejected_Return").item(0);
			Element pausehandl = (Element)docs.getElementsByTagName("Pause_Handling").item(0);

			String usersXmlFileName=prop.getProperty("LOGIN_XML_FILE");
			Document doc = Globals.openXMLFile(hierLeveldir, usersXmlFileName);
			Document doc1 = Globals.openXMLFile(hierLeveldir, hierXmlFileName);
			NodeList obiee_usersList = doc.getElementsByTagName("Obiee_Users");
			Node obieeUserparentNode = obiee_usersList.item(0);
			NodeList usersNode = obieeUserparentNode.getChildNodes();
			int j=0;

			FacesContext ctx = FacesContext.getCurrentInstance();
			ExternalContext extContext = ctx.getExternalContext();
			Map sessionMap = extContext.getSessionMap();
			//HierarchyBean hrybn = (HierarchyBean) sessionMap.get("hierarchyBean");

			Hashtable hashValueTable=new Hashtable();


			if(viewMenberNameAL !=null && !viewMenberNameAL.isEmpty()){

				//				setAllowedHierarchyToLaXML(viewMenberNameAL,hrybn.getHierarchy_ID());
				setAllowedHierarchyToLaXML(viewMenberNameAL,hierID);

			}


			for(int i=0;i<usersNode.getLength();i++){

				if(usersNode.item(i).getNodeType() == Node.ELEMENT_NODE){


					String unidID = usersNode.item(i).getAttributes().getNamedItem("Access_Unique_ID").getNodeValue();
					String Email_ID = usersNode.item(i).getAttributes().getNamedItem("Email_ID").getNodeValue();
					//String userTextcontent = usersNode.item(i).getAttributes().getNamedItem("User").getTextContent().toString();

					String empNameFrmlaxml = usersNode.item(i).getTextContent();

					for(int k=0;k<empNames.length;k++){
						if(empNames[k].equals(empNameFrmlaxml)){
							empEmail[k] = usersNode.item(i).getAttributes().getNamedItem("Email_ID").getNodeValue();
							empUid[k] = usersNode.item(i).getAttributes().getNamedItem("Access_Unique_ID").getNodeValue();

							
							j++;
							
						}
					}
					if(Email_ID.equals(selectedMember)) {

						hashValueTable.put("Email_ID", Email_ID);
						hashValueTable.put("unidID", unidID);
						hashValueTable.put("empNameFrmlaxml", empNameFrmlaxml);


					}
				}
			}

			/*	

			String hierXmlFileName=prop.getProperty("HIERARCHY_XML_FILE");
			String hierXmlDir = prop.getProperty("HIERARCHY_XML_DIR");
			Document doc1 = Globals.openXMLFile(hierXmlDir, hierXmlFileName);

			FacesContext ctx1 = FacesContext.getCurrentInstance();
			ExternalContext extContext1 = ctx1.getExternalContext();
			Map sessionMap1 = extContext1.getSessionMap();
			//HierarchyBean hrybn = (HierarchyBean) sessionMap.get("hierarchyBean");

			Node workFlowNode = Globals.getNodeByAttrVal(doc1, "Workflow", "Hierarchy_ID", hierID);
			if(workFlowNode != null){
				String rejectedSetStatus = "";
				Node currentStageNode=Globals.getNodeByAttrValUnderParent(doc1, workFlowNode, "Stage_Name", stageNameFromAddStage);
				Element curStageElement = (Element)currentStageNode;
				if(type.equalsIgnoreCase("Rejection")) {
				curStageElement.setAttribute("Rejected_Return_To", rejectedReturn2);
				curStageElement.setAttribute("Rejected_Set_Status_To_Type", rejectedSetStatus2);

				curStageElement.setAttribute("Rejected_Set_Status_To",rejectedSetStatus);
				curStageElement.setAttribute("Rejected_Notification_To", rejectedSendNotification2);
					returnRejectedMessage = "Rejected return to saved successfully.";
					returnRejectedMessagecolor="blue";
				}else if(type.equalsIgnoreCase("Pause")) {
					curStageElement.setAttribute("Pass_Notification_To", passSendNotification2);
					returnPassMessage = "Pause notification details saved successfully.";
					returnPassMessagecolor="blue";
				}
			 */
			
			
			
			
			Hashtable rejectHT=new Hashtable();
			if(showRejectedButton) {
				String rejectedGotoState = "";
				Node workFlowNode = Globals.getNodeByAttrVal(doc1, "Workflow", "Hierarchy_ID", hierID);
				Node rejectprev_StageN=Globals.getNodeByAttrValUnderParent(doc1, workFlowNode, "Stage_Name", selectedgotoStages);
				System.out.println("selectedgotoStages+++++++++++"+selectedgotoStages);
				Element rejectprev_StageEle = (Element)rejectprev_StageN;
				System.out.println("curStageElement++++++++++ "+rejectprev_StageEle);
				String rejectprev_StageNo="";
			
				/*if(!saveRejectedFlag) {

					setStageMsg = "Please configure Rejection handling procedure by clicking Rejection Handling Button.";
					setStageMsgcolor="red";
					return;
				}*/
				String rejectedSetStatus = "";
				if(rejectedSetStatus2 != null) {
					if(rejectedSetStatus2.equalsIgnoreCase("Choose Status")){
						rejectedSetStatus = selectedStatus;

					}else if(rejectedSetStatus2.equalsIgnoreCase("First Status Code")){
						if(viewstagestatusAL.size()>0){
							rejectedSetStatus = (String)viewstagestatusAL.get(0);
						}
					}
				}


				if((rejectedReturn2==null || rejectedReturn2.equals("")) && (rejectedSetStatus2==null || rejectedSetStatus2.equals("")) && (rejectedSetStatus==null || rejectedSetStatus.equals("")) &&  (rejectedSendNotification2==null || rejectedSendNotification2.equals(""))) {

					rejectedReturn2 = rejectedval.getAttribute("Previus_Stage");
					rejectedSetStatus2=rejectedval.getAttribute("Set_Status");
					rejectedSendNotification2 = rejectedval.getAttribute("Send_Notification");


					rejectHT.put("Rejected_Return_To", rejectedReturn2);
					rejectHT.put("Rejected_Set_Status_To_Type", rejectedSetStatus2);
					rejectHT.put("Rejected_Set_Status_To", "");
					rejectHT.put("Rejected_Notification_To", rejectedSendNotification2);
				}
				else {
					if(rejectedReturn2.equalsIgnoreCase("Goto_Stage")){
						System.out.println("curStageElement++++++++++ "+rejectprev_StageEle.getAttribute("Stage_No"));
					rejectprev_StageNo=(rejectprev_StageEle.getAttribute("Stage_No"))==null?" ":rejectprev_StageEle.getAttribute("Stage_No");
					}
					if(rejectedReturn2.equalsIgnoreCase("Goto_Stage")){
						
						System.out.println("curStageElement++++++++++ "+rejectprev_StageEle.getAttribute("Stage_No"));
						
						if(selectedgotoStages !=null&&rejectprev_StageNo!=null){							
						rejectedGotoState ="Goto_Stage~"+selectedgotoStages+"~"+rejectprev_StageNo;
						System.out.println("xml save value"+rejectedGotoState);
						}
					}else {
						rejectedGotoState =rejectedReturn2;
					}
					rejectHT.put("Rejected_Return_To", rejectedGotoState); 
					rejectHT.put("Rejected_Set_Status_To_Type", rejectedSetStatus2);
					rejectHT.put("Rejected_Set_Status_To", rejectedSetStatus);
					rejectHT.put("Rejected_Notification_To", rejectedSendNotification2);


				}
			}else {
				rejectHT.put("Rejected_Return_To", "");
				rejectHT.put("Rejected_Set_Status_To_Type", "");
				rejectHT.put("Rejected_Set_Status_To", "");
				rejectHT.put("Rejected_Notification_To", "");
			}


			Hashtable pauseHT=new Hashtable();
			//	viewstagestatusAL
			if(showPassButton) {
				if(passSendNotification2==null || passSendNotification2.equals("")) {

					passSendNotification2=pausehandl.getAttribute("Send_Notificaion_Flag");
					System.out.println("passSendNotification2--->"+passSendNotification2);
					pauseHT.put("Pass_Notification_To", passSendNotification2);
				}else {

					pauseHT.put("Pass_Notification_To", passSendNotification2);

				}
			}else {
				pauseHT.put("Pass_Notification_To", "");
			}

			System.out.println("%%%%%%%%%%%%%%%%%%%saveRejectedFlag%%%% :"+saveRejectedFlag);



			System.out.println("%%%%%%%%%%%%%%%%%%%%%%%% :"+selectedMember);
			workFlowBean wfb = new workFlowBean();
			wfb.addStageintoXML(hierID, stageNameFromAddStage,empNames, statusMsg, empEmail, empUid, viewstagePropertiesValue, vstageMinApprVal,
					stageNumberFromAddStage,saveType,selectedStageRole,parallelType,selectedMember,hashValueTable,rejectHT,pauseHT,
					autologinbool,allowuploadbool,enableEsign, externalUserFlag, sendPrimaryFileOnlyFlag, statusCode4DocsFlag, changeprimarydocFlag, rulesAL, rulesALTemp, allowEditUD);
			ischangedStage1 = false;
			setStageMsg = "Stage is updated successfully.";
			setStageMsgcolor="blue";

		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	private void addMessage(FacesMessage message) {
		FacesContext.getCurrentInstance().addMessage(null, message);  
	}  

	public DashboardModel getModel() {
		return model;  
	}  


	public void setAllowedHierarchyToLaXML(ArrayList usersFromSetStageAL,String hierID){
		System.out.println("Entering: " + new Exception().getStackTrace()[0].getClassName() + "."
				+ new Exception().getStackTrace()[0].getMethodName());
		try{

			PropUtil prop=new PropUtil();
			String usersXmlFileName=prop.getProperty("LOGIN_XML_FILE");
			String laXmlDir = prop.getProperty("HIERARCHY_XML_DIR");
			Document doc = Globals.openXMLFile(laXmlDir, usersXmlFileName);
			Node userNode = null;
			String[] userID = null;
			String[] allowedHierarchy = null;

			NodeList userList = doc.getElementsByTagName("Obiee_Users");
			Node firstNode = userList.item(0);

			if(firstNode.getNodeType() == Node.ELEMENT_NODE){

				NodeList childNodes = firstNode.getChildNodes();
				userID = new String[childNodes.getLength()];
				allowedHierarchy = new String[childNodes.getLength()];
				int k=0;

				for(int j=0;j<childNodes.getLength();j++){

					userNode = childNodes.item(j);

					if(userNode.getNodeType() == Node.ELEMENT_NODE){

						userID[k] = userNode.getTextContent();
						String userAllowedHierarchy = hierID;
						for(int l=0;l<usersFromSetStageAL.size();l++){

							if(userID[k].equals(usersFromSetStageAL.get(l))){

								Element user = (Element)userNode;
								if(userNode.getAttributes().getNamedItem("Allowed_Hierarchies") == null){
									user.setAttribute("Allowed_Hierarchies", userAllowedHierarchy);

								}else{

									allowedHierarchy[k] = userNode.getAttributes().getNamedItem("Allowed_Hierarchies").getNodeValue();

									if(allowedHierarchy[k].contains(",")){  // 60,45,52

										String[] splitedVal = allowedHierarchy[k].split(",");

										for(int n=0;n<splitedVal.length;n++){


											if(!userAllowedHierarchy.contains(splitedVal[n])){

												userAllowedHierarchy = userAllowedHierarchy.concat(",")+splitedVal[n];
											}

										}

										user.setAttribute("Allowed_Hierarchies", userAllowedHierarchy);

									}else{

										if(allowedHierarchy[k].equals("")){


											user.setAttribute("Allowed_Hierarchies", userAllowedHierarchy);

										}
										else if(!userAllowedHierarchy.contains(allowedHierarchy[k])){

											userAllowedHierarchy = userAllowedHierarchy.concat(",")+allowedHierarchy[k];
											user.setAttribute("Allowed_Hierarchies", userAllowedHierarchy);
										}
									}
								}	

							}

						}


						k++;
					}

				}

				Globals.writeXMLFile(doc, laXmlDir, usersXmlFileName);
			}

		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		System.out.println("Exiting: " + new Exception().getStackTrace()[0].getClassName() + "."
				+ new Exception().getStackTrace()[0].getMethodName());
	}

	//start code change Jayaramu 10APR14
	public void UpDownStatusCode(String upDownFlag){
		try{
			System.out.println("Entering: " + new Exception().getStackTrace()[0].getClassName() + "."
					+ new Exception().getStackTrace()[0].getMethodName());

			message4SetStage = "";
			if(upDownFlag.equals("up")){

				if(frwdStatusCodes.length<1){
					message4SetStage = "Please select status code to proceed further.";
					message4SetStageColor="red";
					return;
				}
				for(int j=0;j<frwdStatusCodes.length;j++){
					for(int i=0;i<viewstagestatusAL.size();i++){
						if(viewstagestatusAL.get(i).equals(frwdStatusCodes[j])){
							if(i==0){
								System.out.println("Can't up status code");
								message4SetStage = "Can't up status code.";
								message4SetStageColor="red";
								return;
							}
							String up = (String)viewstagestatusAL.get(i-1);
							viewstagestatusAL.set(i, up);
							viewstagestatusAL.set(i-1, frwdStatusCodes[j]);
							if(ischangedStage){
								ischangedStage1 = true;
							}
							message4SetStage = "Status code up successfully.";
							message4SetStageColor="blue";
							break;
						}
					}
				}
			}
			if(upDownFlag.equals("down")){
				for(int j=frwdStatusCodes.length-1;j>=0;j--){
					for(int i=0;i<viewstagestatusAL.size();i++){

						if(viewstagestatusAL.get(i).equals(frwdStatusCodes[j])){
							if(viewstagestatusAL.size() == i+1){
								System.out.println("Can't down status code");
								message4SetStage = "Can't down status code.";
								message4SetStageColor="red";
								return;
							}
							String up = (String)viewstagestatusAL.get(i+1);
							viewstagestatusAL.set(i, up);
							viewstagestatusAL.set(i+1, frwdStatusCodes[j]);
							if(ischangedStage){
								ischangedStage1 = true;
							}
							message4SetStage = "Status code down successfully.";
							message4SetStageColor="blue";
							break;
						}
					}
				}
			}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		System.out.println("Exiting: " + new Exception().getStackTrace()[0].getClassName() + "."
				+ new Exception().getStackTrace()[0].getMethodName());
	} //end code change Jayaramu 10APR14

	public void UpDownMembersCode(String upDownFlag){
		try{
			System.out.println("Entering: " + new Exception().getStackTrace()[0].getClassName() + "."
					+ new Exception().getStackTrace()[0].getMethodName());


			message4MemberSetStage = "";
			message4MemberSetColor="blue";
			if(upDownFlag.equals("up")){

				if(frwdMembers.length<1){
					message4MemberSetStage = "Please select Member(s) to proceed further.";
					message4MemberSetColor="red";
					return;
				}
				for(int j=0;j<frwdMembers.length;j++){
					for(int i=0;i<viewMenberNameAL.size();i++){
						if(viewMenberNameAL.get(i).equals(frwdMembers[j])){
							if(i==0){
								System.out.println("Can't up Member code");
								message4MemberSetStage = "Can't up Member code.";
								message4MemberSetColor="red";
								return;
							}
							String up = (String)viewMenberNameAL.get(i-1);
							viewMenberNameAL.set(i, up);
							viewMenberNameAL.set(i-1, frwdMembers[j]);
							if(ischangedStage){
								ischangedStage1 = true;
							}
							message4MemberSetStage = "Member up successfully.";
							message4MemberSetColor="blue";
							break;
						}
					}
				}
			}
			if(upDownFlag.equals("down")){
				for(int j=frwdMembers.length-1;j>=0;j--){
					for(int i=0;i<viewstagestatusAL.size();i++){

						if(viewMenberNameAL.get(i).equals(frwdMembers[j])){
							if(viewMenberNameAL.size() == i+1){
								System.out.println("Can't down Member code");
								message4MemberSetStage = "Can't down Member code.";
								message4MemberSetColor="red";
								return;
							}
							String up = (String)viewMenberNameAL.get(i+1);
							viewMenberNameAL.set(i, up);
							viewMenberNameAL.set(i+1, frwdMembers[j]);
							if(ischangedStage){
								ischangedStage1 = true;
							}
							message4MemberSetStage = "Member down successfully.";
							message4MemberSetColor="blue";
							break;
						}
					}
				}
			}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		System.out.println("Exiting: " + new Exception().getStackTrace()[0].getClassName() + "."
				+ new Exception().getStackTrace()[0].getMethodName());
	}



	public void setStageMsgEmpty(){
		setStageMsg = "";
		message4MemberSetStage="";
	}



	public void WritePassReturnToXml(){
		try{
			System.out.println("Entering: " + new Exception().getStackTrace()[0].getClassName() + "."
					+ new Exception().getStackTrace()[0].getMethodName());

			if(passSendNotification2 == null){
				returnPassMessage = "Please select Send Notification to.";
				returnPassMessagecolor="red";
				return;
			}
			PropUtil prop=new PropUtil();
			String hierXmlFileName=prop.getProperty("HIERARCHY_XML_FILE");
			String hierXmlDir = prop.getProperty("HIERARCHY_XML_DIR");
			Document doc = Globals.openXMLFile(hierXmlDir, hierXmlFileName);

			FacesContext ctx = FacesContext.getCurrentInstance();
			ExternalContext extContext = ctx.getExternalContext();
			Map sessionMap = extContext.getSessionMap();
			//HierarchyBean hrybn = (HierarchyBean) sessionMap.get("hierarchyBean");

			Node workFlowNode = Globals.getNodeByAttrVal(doc, "Workflow", "Hierarchy_ID", hierID);
			if(workFlowNode != null){
				String rejectedSetStatus = "";
				Node currentStageNode=Globals.getNodeByAttrValUnderParent(doc, workFlowNode, "Stage_Name", stageNameFromAddStage);
				Element curStageElement = (Element)currentStageNode;
				curStageElement.setAttribute("Rejected_Return_To", rejectedReturn2);
				curStageElement.setAttribute("Rejected_Set_Status_To_Type", rejectedSetStatus2);
				if(rejectedSetStatus2.equalsIgnoreCase("Choose Status")){
					rejectedSetStatus = selectedStatus;

				}else if(rejectedSetStatus2.equalsIgnoreCase("First Status Code")){
					if(viewstagestatusAL.size()>0){
						rejectedSetStatus = (String)viewstagestatusAL.get(0);
					}
				}
				curStageElement.setAttribute("Rejected_Set_Status_To",rejectedSetStatus);
				curStageElement.setAttribute("Rejected_Notification_To", rejectedSendNotification2);
				Globals.writeXMLFile(doc, hierXmlDir, hierXmlFileName);
				returnPassMessage = "Pause return to saved successfully.";
				returnPassMessagecolor="blue";
			}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		System.out.println("Exiting: " + new Exception().getStackTrace()[0].getClassName() + "."
				+ new Exception().getStackTrace()[0].getMethodName());
	}



	public void getPassReturnFromHierXml(){
		try{
			System.out.println("Entering: " + new Exception().getStackTrace()[0].getClassName() + "."
					+ new Exception().getStackTrace()[0].getMethodName());
			returnPassMessage = "";

			returnPassMessagecolor="";
			passSendNotification2 = "";
			PropUtil prop=new PropUtil();
			String hierXmlFileName=prop.getProperty("HIERARCHY_XML_FILE");
			String hierXmlDir = prop.getProperty("HIERARCHY_XML_DIR");
			Document doc = Globals.openXMLFile(hierXmlDir, hierXmlFileName);

			FacesContext ctx = FacesContext.getCurrentInstance();
			ExternalContext extContext = ctx.getExternalContext();
			Map sessionMap = extContext.getSessionMap();
			//HierarchyBean hrybn = (HierarchyBean) sessionMap.get("hierarchyBean");

			Node workFlowNode = Globals.getNodeByAttrVal(doc, "Workflow", "Hierarchy_ID", hierID);
			if(workFlowNode != null){
				String rejectedSetStatus = "";
				Node currentStageNode=Globals.getNodeByAttrValUnderParent(doc, workFlowNode, "Stage_Name", stageNameFromAddStage);
				if(currentStageNode != null){
					Node rejectedReturnTo = currentStageNode.getAttributes().getNamedItem("Rejected_Return_To");
					Node rejectedSetStatusToType = currentStageNode.getAttributes().getNamedItem("Rejected_Set_Status_To_Type");
					Node rejectedSetStatusTo = currentStageNode.getAttributes().getNamedItem("Rejected_Set_Status_To");
					Node rejectedSendToNotification = currentStageNode.getAttributes().getNamedItem("Rejected_Notification_To");
									
					if(rejectedReturnTo != null){
						rejectedReturn2 = rejectedReturnTo.getNodeValue();
					}
					if(rejectedSetStatusToType != null){
						rejectedSetStatus2 = rejectedSetStatusToType.getNodeValue();
					}
					if(rejectedSetStatusTo != null){
						selectedStatus = rejectedSetStatusTo.getNodeValue();
					}
					if(rejectedSendToNotification != null){
						passSendNotification2 = rejectedSendToNotification.getNodeValue();
					}
				}
			}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		System.out.println("Exiting: " + new Exception().getStackTrace()[0].getClassName() + "."
				+ new Exception().getStackTrace()[0].getMethodName());
	}







	//Start code change Jayaramu 10APR14
	public void WriteRejectedReturnToXml(String type){
		try{
			System.out.println("Entering: " + new Exception().getStackTrace()[0].getClassName() + "."
					+ new Exception().getStackTrace()[0].getMethodName());

			if(type.equalsIgnoreCase("Rejection")) {
				if(rejectedReturn2 == null || rejectedReturn2.isEmpty()){
					returnRejectedMessage = "Please select Rejected return to.";
					returnRejectedMessagecolor="red";
					return;
				}else if(rejectedReturn2 != null){
					if(rejectedReturn2.equalsIgnoreCase("Goto_Stage")){
						if(selectedgotoStages == null||selectedgotoStages.isEmpty()){
							System.out.println("YYYYYYYYYYYYYYYYYYYYY"+selectedgotoStages);
							returnRejectedMessage = "Please Choose Goto Stage.";
							returnRejectedMessagecolor="red";
							return;
						}
					}
			    }else if(rejectedSetStatus2 == null || rejectedSetStatus2.isEmpty()){
					returnRejectedMessage = "Please select Set status to.";
					returnRejectedMessagecolor="red";
					return;
				}else if(rejectedSetStatus2 != null){
					if(rejectedSetStatus2.equalsIgnoreCase("Choose Status")){
						if(selectedStatus == null){
							returnRejectedMessage = "Please Choose Status.";
							returnRejectedMessagecolor="red";
							return;
						}

					}else if(rejectedSetStatus2.equalsIgnoreCase("First Status Code")){
						if(viewstagestatusAL.size()<=0){
							returnRejectedMessage = "First status code is not available.";
							returnRejectedMessagecolor="red";
							return;
						}

					}
				}if(rejectedSendNotification2 == null || rejectedSendNotification2.isEmpty()){
					returnRejectedMessage = "Please select Send Notification to.";
					returnRejectedMessagecolor="red";
					return;
				}
			}else if(type.equalsIgnoreCase("Pause")) {
				if(passSendNotification2 == null || passSendNotification2.isEmpty()){
					returnPassMessage = "Please select Send Notification to.";
					returnRejectedMessagecolor="red";
					return;
				}
			}



			PropUtil prop=new PropUtil();
			String hierXmlFileName=prop.getProperty("HIERARCHY_XML_FILE");
			String hierXmlDir = prop.getProperty("HIERARCHY_XML_DIR");
			Document doc = Globals.openXMLFile(hierXmlDir, hierXmlFileName);

			FacesContext ctx = FacesContext.getCurrentInstance();
			ExternalContext extContext = ctx.getExternalContext();
			Map sessionMap = extContext.getSessionMap();
			//HierarchyBean hrybn = (HierarchyBean) sessionMap.get("hierarchyBean");

			Node workFlowNode = Globals.getNodeByAttrVal(doc, "Workflow", "Hierarchy_ID", hierID);
			if(workFlowNode != null){
				String rejectedSetStatus = "";
				String rejectedGotoState = "";
				Node currentStageNode=Globals.getNodeByAttrValUnderParent(doc, workFlowNode, "Stage_Name", stageNameFromAddStage);
				Element curStageElement = (Element)currentStageNode;
				Node rejectprev_StageN=Globals.getNodeByAttrValUnderParent(doc, workFlowNode, "Stage_Name", selectedgotoStages);
				Element rejectprev_StageEle = (Element)rejectprev_StageN;
				String rejectprev_StageNo="";
				if(type.equalsIgnoreCase("Rejection")) {
					
					if(rejectedReturn2.equalsIgnoreCase("Goto_Stage")){
						rejectprev_StageNo=rejectprev_StageEle.getAttribute("Stage_No");
						System.out.println("curStageElement++++++++++ "+rejectprev_StageEle.getAttribute("Stage_No"));
						
						if(selectedgotoStages ==null){
							returnRejectedMessage = "Please Choose Goto Stage.";
							returnRejectedMessagecolor="red";
							return;}
						else {
						rejectedGotoState ="Goto_Stage~"+selectedgotoStages+"~"+rejectprev_StageNo;
						System.out.println("xml save value"+rejectedGotoState);
						}
					}else {
						rejectedGotoState =rejectedReturn2;
					}
					curStageElement.setAttribute("Rejected_Return_To", rejectedGotoState);
					
					curStageElement.setAttribute("Rejected_Set_Status_To_Type", rejectedSetStatus2);
					if(rejectedSetStatus2.equalsIgnoreCase("Choose Status")){
						rejectedSetStatus = selectedStatus;

					}else if(rejectedSetStatus2.equalsIgnoreCase("First Status Code")){
						if(viewstagestatusAL.size()>0){
							rejectedSetStatus = (String)viewstagestatusAL.get(0);
						}
					}
					curStageElement.setAttribute("Rejected_Set_Status_To",rejectedSetStatus);
					curStageElement.setAttribute("Rejected_Notification_To", rejectedSendNotification2);
					returnRejectedMessage = "Rejection handling saved successfully.";
					returnRejectedMessagecolor="blue";
					saveRejectedFlag=true;
				}else if(type.equalsIgnoreCase("Pause")) {
					curStageElement.setAttribute("Pass_Notification_To", passSendNotification2);
					returnPassMessage = "Pause notification details saved successfully.";
					returnPassMessagecolor="blue";
					savePauseFlag=true;
				}

				Globals.writeXMLFile(doc, hierXmlDir, hierXmlFileName);


			}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		System.out.println("Exiting: " + new Exception().getStackTrace()[0].getClassName() + "."
				+ new Exception().getStackTrace()[0].getMethodName());
	}
	//End code change Jayaramu 10APR14

	//Start code change Jayaramu 10APR14
	public void getRejectedReturnFromHierXml(String type){
		try{
			gotoStageDetails();
			System.out.println("Entering: " + new Exception().getStackTrace()[0].getClassName() + "."
					+ new Exception().getStackTrace()[0].getMethodName());
			returnRejectedMessage = "";
			/*rejectedReturn2 = "";
			rejectedSetStatus2 = "";
			selectedStatus = "";
			rejectedSendNotification2 = "";
			passSendNotification2 = "";*/
			PropUtil prop=new PropUtil();
			String hierXmlFileName=prop.getProperty("HIERARCHY_XML_FILE");
			String hierXmlDir = prop.getProperty("HIERARCHY_XML_DIR");
			Document doc = Globals.openXMLFile(hierXmlDir, hierXmlFileName);

			FacesContext ctx = FacesContext.getCurrentInstance();
			ExternalContext extContext = ctx.getExternalContext();
			Map sessionMap = extContext.getSessionMap();
			//HierarchyBean hrybn = (HierarchyBean) sessionMap.get("hierarchyBean");

			Node workFlowNode = Globals.getNodeByAttrVal(doc, "Workflow", "Hierarchy_ID", hierID);
			if(workFlowNode != null){
				String rejectedSetStatus = "";
				Node currentStageNode=Globals.getNodeByAttrValUnderParent(doc, workFlowNode, "Stage_Name", stageNameFromAddStage);
				if(currentStageNode != null){
					if(type.equalsIgnoreCase("Rejection")) {
						Node rejectedReturnTo = currentStageNode.getAttributes().getNamedItem("Rejected_Return_To");
						Node rejectedSetStatusToType = currentStageNode.getAttributes().getNamedItem("Rejected_Set_Status_To_Type");
						Node rejectedSetStatusTo = currentStageNode.getAttributes().getNamedItem("Rejected_Set_Status_To");
						Node rejectedSendToNotification = currentStageNode.getAttributes().getNamedItem("Rejected_Notification_To");
						if(rejectedReturnTo != null){
							String rejectedReturnTosplit=rejectedReturnTo.getNodeValue().isEmpty()?rejectedReturn2:rejectedReturnTo.getNodeValue();	
							String[] arrOfStr = rejectedReturnTosplit.split("~", 3);
							if(arrOfStr[0].equalsIgnoreCase("Goto_Stage")){
								System.out.println(arrOfStr[0]+"  IS that it or wat"+arrOfStr[1]+","+ arrOfStr[2]);
								rejectedReturn2 =arrOfStr[0];
								selectedgotoStages=arrOfStr[1];
							}else {
								rejectedReturn2 = rejectedReturnTo.getNodeValue().isEmpty()?rejectedReturn2:rejectedReturnTo.getNodeValue();
							}
						}
						if(rejectedSetStatusToType != null){
							rejectedSetStatus2 = rejectedSetStatusToType.getNodeValue().isEmpty()?rejectedSetStatus2:rejectedSetStatusToType.getNodeValue();
						}
						if(rejectedSetStatusTo != null){
							selectedStatus = rejectedSetStatusTo.getNodeValue();
						}

						if(rejectedSendToNotification != null){
							rejectedSendNotification2 = rejectedSendToNotification.getNodeValue().isEmpty()?rejectedSendNotification2:rejectedSendToNotification.getNodeValue();
						}

					}else if(type.equalsIgnoreCase("Pause")) {
						Element currEle = (Element) currentStageNode;
						passSendNotification2 = currEle.getAttribute("Pass_Notification_To").isEmpty()?passSendNotification2 : currEle.getAttribute("Pass_Notification_To");
					}
				}
			}
			System.out.println("-=-=-=-=-=-=rejectedReturn2-=-=-=-=-=-="+rejectedReturn2);
			System.out.println("-=-=-=-=-=-=rejectedSetStatus2-=-=-=-=-=-="+rejectedSetStatus2);
			System.out.println("-=-=-=-=-=-=selectedStatus-=-=-=-=-=-="+selectedStatus);
			System.out.println("-=-=-=-=-=-=rejectedSendNotification2-=-=-=-=-=-="+rejectedSendNotification2);
			System.out.println("-=-=-=-=-=-=passSendNotification2-=-=-=-=-=-="+passSendNotification2);
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		System.out.println("Exiting: " + new Exception().getStackTrace()[0].getClassName() + "."
				+ new Exception().getStackTrace()[0].getMethodName());
	}//End code change Jayaramu 10APR14

	public void getAccessForExternalStorage(String externalUserName) throws Exception {
		PropUtil prop=new PropUtil();
		String configFileName=prop.getProperty("HIERARCHY_CONFIG_FILE");
		String hierLeveldir=prop.getProperty("HIERARCHY_XML_DIR");
		Document doc=Globals.openXMLFile(hierLeveldir, configFileName);
		Element redirectEle = (Element)doc.getElementsByTagName("GoogleRedirectURL").item(0);
		String redirectURL = redirectEle.getAttribute("URL")==null || redirectEle.getAttribute("URL").trim().isEmpty() ? "http://localhost:8080/rbid/Callback" : redirectEle.getAttribute("URL");
		String developerKey = "AIzaSyAley2MHh7OSgTxwquQSL2Rt77ODyPggrI";
		/*String scopes = "https://www.googleapis.com/auth/drive.file";
		String state = "User="+externalUserName+"~~"+this.hierID;
		String redirectURL = "http://localhost:8080/rbid/Callback";
		String clientID = "529892980888-474215ug7erl2qoube52n81u6k7q26bi.apps.googleusercontent.com";
		String urlSyntax = "https://accounts.google.com/o/oauth2/v2/auth?scope=$$Scope$$&access_type=offline&include_granted_scopes=true&"
				+ "state=$$State$$&redirect_uri=$$RedirectURL$$&response_type=code&client_id=$$ClientID$$&login_hint=$$UserID$$";
		urlSyntax = urlSyntax.replace("$$Scope$$", scopes);
		urlSyntax = urlSyntax.replace("$$State$$", state);
		urlSyntax = urlSyntax.replace("$$RedirectURL$$", redirectURL);
		urlSyntax = urlSyntax.replace("$$ClientID$$", clientID);
		urlSyntax = urlSyntax.replace("$$UserID$$", externalUserName);
		this.externalAuthURL = urlSyntax;*/
		User a = new User();
		a.setEmailAddress(externalUserName);
		HttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
		JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
		Authorizer auth = new Authorizer(a, httpTransport, JSON_FACTORY, redirectURL);
		if(auth.get() == null || auth.get().getRefreshToken() == null || auth.get().getRefreshToken().isEmpty()) {
			this.externalAuthURL = auth.createCredentialForNewUser(hierID);
			this.developerKey = developerKey;
		}else {
			this.externalAuthURL = "";
		}
	}

	public void getAccessToken4Picker() throws GeneralSecurityException, IOException {
		User a = new User();
		a.setEmailAddress(externalUserName);
		System.out.println("-=-=-=-=-=-=-=externalUserName-=-=-=-=-=-="+externalUserName);
		HttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
		JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
		Authorizer auth = new Authorizer(a, httpTransport, JSON_FACTORY, "");
		Credential cre = auth.get();
		String developerKey = "AIzaSyAley2MHh7OSgTxwquQSL2Rt77ODyPggrI";
		this.developerKey = developerKey;
		this.accessToken = cre.getAccessToken();

		System.out.println("-=-=-=-=developerKey-=-=-=-=-=-=-="+developerKey+"-=-=-=-=-=-=accessToken-=-=-=-=-=-="+cre.getRefreshToken());
	}

	public void saveFolderID(String folderID) throws Exception {
		System.out.println("-=-=-=folderIDfolderID-=-=-=-="+folderID);

		if(folderID.equals("") && folderID.isEmpty()) {

			authorizeMsgstr="Enter the E Mail ID";
			authorizeMsgColor="red";
			return;
		}


		PropUtil prop=new PropUtil();
		String hierLevelXML=prop.getProperty("HIERARCHY_XML_FILE");
		String hierLeveldir=prop.getProperty("HIERARCHY_XML_DIR");
		Document xmlDoc=Globals.openXMLFile(hierLeveldir, hierLevelXML);
		Element ele = (Element)Globals.getNodeByAttrVal(xmlDoc, "Workflow", "Hierarchy_ID", hierID);
		ele.setAttribute("FolderID", folderID);
		//		GoogleStorageMaintenace.createFolder(externalUserName, folderID, folderName)
		Globals.writeXMLFile(xmlDoc, hierLeveldir, hierLevelXML);

		authorizeMsgstr="Saved Successfully.";
		authorizeMsgColor="blue";
	}

	public void processUploadFile(ValueChangeEvent eve) {
		try (InputStream input = uploadedFile.getInputStream()) {
			String fileName = uploadedFile.getName();
			Files.copy(input, new File("C:\\Users\\Officepc\\Downloads\\", fileName).toPath());
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void emtypropertiesMSG() {
		authorizeMsgstr="";
	}

	public void redirect(){ //code change Jayaramu 11APR14
		msg4WF = "";
		FacesContext ctx = FacesContext.getCurrentInstance();
		ExternalContext extContext = ctx.getExternalContext();
		Map sessionMap = extContext.getSessionMap();
	
		LoginBean lgnbn = (LoginBean) sessionMap.get("loginBean");
		String drprvilage=(String)lgnbn.getLoginprvilageKey();
		
		if(drprvilage.equals("true")) {
			
			
			HeaderBean hb = new HeaderBean();
			hb.processMenu("HierarchyList.xhtml");
			
		}else {
			
			HeaderBean hb = new HeaderBean();
			hb.processMenu("DocumentList.xhtml");
			
		}
		
	
	}

	public void addRuleAttribute2Table(String attributeName, String attributeType, String attrDataType) {
		try {
			System.out.println("-=-=-=-=-=attributeName-=-=-=-="+attributeName);
			authorizeMsgstr = "";
			authorizeMsgColor = "blue";
			if(attributeName.trim().isEmpty()) {
				authorizeMsgstr = "Enter attribute name before addding.";
				authorizeMsgColor = "red";
				return;
			}
			attributeType = attributeType.equalsIgnoreCase("Custom") ? "Custom" : "Default";
			String status = RulesManager.saveRuleAttributeInXML(hierID, attributeName, attributeType, attrDataType);
			if(!status.equalsIgnoreCase("success")) {
				authorizeMsgstr = status;
				authorizeMsgColor = "red";
				return;
			}

			int index = ruleAttrAL.size()+1;
			RuleAttributeDataModel radm = new RuleAttributeDataModel(String.valueOf(index), attributeName, attributeType, attrDataType);
			ruleAttrAL.add(radm);
			selectedAttributeType = "Custom";
			selectedAttribute = "";
			selectedAttributeDataType = "text";
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}

	public void deleteRuleAttributeByName(String attributeName, String sno) {
		try {
			System.out.println("-=-="+sno);
			RulesManager.deleteRuleAttributesFromXML(hierID, attributeName);

			int ind = Integer.valueOf(sno)-1;
			if(ruleAttrAL.size() > ind && ind >= 0)
				ruleAttrAL.remove(ind);		
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public void createDynamicColumn4Rules() {
		try {
			authorizeMsgstr = "";
			authorizeMsgColor = "blue";
			setStageMsg = "";
			setStageMsgcolor = "blue";
			rulesAL.clear();
			selectedrulesAL.clear();
			PropUtil prop=new PropUtil();
			String heirLevelXML=prop.getProperty("HIERARCHY_XML_FILE");
			String heirLeveldir=prop.getProperty("HIERARCHY_XML_DIR");
			Document xmlDoc=Globals.openXMLFile(heirLeveldir, heirLevelXML);
			String hierarchyID=hierID;
			Node workFlowN=Globals.getNodeByAttrVal(xmlDoc, "Workflow", "Hierarchy_ID", hierarchyID);
			Element workflowEle = (Element) workFlowN;
			String ruleAttriName = workflowEle.getAttribute("RuleAttributeName");
			String ruleAttriType = workflowEle.getAttribute("RuleAttributeType");
			if(ruleAttriName == null || ruleAttriName.trim().isEmpty()) {
				setStageMsg = "Please create rule attribute first before creating rule. \nTo create rule attribute click Add Rule Attribute button.";
				setStageMsgcolor = "red";
				return;
			}
			loadRulesFromStr(ruleAttriName, ruleAttriType);
			getRulesDetailsFromXML(workFlowN);
			addNewRulesInStage();
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public void loadRulesFromStr(String ruleAttriName, String ruleAttriType) {
		String[] opt = ruleAttriName.split("#~#");
		List<String> tempAL = Arrays.asList(opt);
		ArrayList<String> ruleAttrName = new ArrayList<>(tempAL);
		
		String[] attrType = ruleAttriType.split("#~#");
		List<String> attrTypeAL = Arrays.asList(attrType);
		ArrayList<String> ruleAttrType = new ArrayList<>(attrTypeAL);
		
		ArrayList<RuleAttributeDataModel> rdmAL = new ArrayList<>();

		for(int i=0;i<ruleAttrName.size()*2;i++) {
			if(ruleAttrType.size() <= (i/2))
				continue;
			System.out.println(ruleAttrType.get(i/2)+"-=-=-=-=-=-=-=-=-="+ruleAttrName.get(i/2));
			if(i % 2 == 0) {
				RuleAttributeDataModel rdm = new RuleAttributeDataModel(ruleAttrName.get(i/2), ruleAttrType.get(i/2), ruleOperHT);
				rdmAL.add(rdm);
			}else {
				RuleAttributeDataModel rdm = new RuleAttributeDataModel("Operator", ruleAttrType.get(i/2), ruleOperHT);
				rdmAL.add(rdm);
			}
		}
		rulesALTemp.clear();
		rulesALTemp.addAll(rdmAL);
	}

	public void addNewRulesInStage() {
		try {
			authorizeMsgstr = "";
			authorizeMsgColor = "blue";
			String ruleName = "Rule #"+(rulesAL.size()+1);
			ArrayList<String> stageNameAL = new ArrayList<>();
			for(int i=0;i<panelAL.size();i++) {
				dashboardManager dm = (dashboardManager)panelAL.get(i);
				stageNameAL.add(dm.getPanelName());
			}
			String[] valuesArr = new String[rulesALTemp.size()];
			for(int i=0;i<rulesALTemp.size();i++) {
				if(i % 2 == 0)
					valuesArr[i] = "";
				else
					valuesArr[i] = "equals";
			}
			RuleDataModel rdm = new RuleDataModel(ruleName, stageNameFromAddStage, stageNameAL.get(0), "And", stageNameAL, valuesArr);
			rulesAL.add(rdm);
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public void getRulesDetailsFromXML(Node workFlowN) {
		try {
			ArrayList<String> stageNameAL = new ArrayList<>();
			for(int i=0;i<panelAL.size();i++) {
				dashboardManager dm = (dashboardManager)panelAL.get(i);
				stageNameAL.add(dm.getPanelName());
			}
			rulesAL = RulesManager.getRulesDetailsFromXML(stageNameFromAddStage, stageNameAL, workFlowN);
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public void saveRulesInStage() {
		try {
			RulesManager.saveRulesInStage(hierID, stageNameFromAddStage, rulesAL, rulesALTemp);
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public void changeAttributeTypeLisenter(String attrType) {
		try {
			if(attrType.equalsIgnoreCase("Custom")) {
				selectedAttribute = "";
			}else {
				selectedAttribute = attrType;
			}
			//			String[] attrStr = {"Today", "Year", "Month", "Weekend", "Team Assigned Date", "Team Due Date", "Member Email ID", "Team Status", "Member Count", "Member Name", "Custom"};
			if(attrType.equalsIgnoreCase("Today") || attrType.equalsIgnoreCase("Year") || attrType.equalsIgnoreCase("Month") || 
					attrType.equalsIgnoreCase("Weekend") || attrType.equalsIgnoreCase("Team Assigned Date") || attrType.equalsIgnoreCase("Team Due Date"))
				selectedAttributeDataType = "Date";
			else if(attrType.equalsIgnoreCase("Member Email ID") || attrType.equalsIgnoreCase("Vendor ID") || attrType.equalsIgnoreCase("Team Status") || attrType.equalsIgnoreCase("Member Name"))
				selectedAttributeDataType = "Text";
			else if(attrType.equalsIgnoreCase("Member Count"))
				selectedAttributeDataType = "Number";
		}catch (Exception e) {
		
			e.printStackTrace();
		}
	}

	public void setRulesDefaultValues() {
		selectedAttribute = "";
		selectedAttributeDataType = "text";
		authorizeMsgstr = "";
		authorizeMsgColor = "blue";
	}

	public void deleteRules(ArrayList<RuleDataModel> selectedrulesAL) {
		Iterator<RuleDataModel> it = rulesAL.iterator();
		for(RuleDataModel rdm : selectedrulesAL)
			while (it.hasNext()) {
				RuleDataModel ruleDataModel = (RuleDataModel) it.next();
				if(rdm.getRuleName().equalsIgnoreCase(ruleDataModel.getRuleName())) {
					it.remove();
					break;
				}
			}
	}
	
	
	public void opennewuserPop(){

		try{
			
			this.createNewuserMessage = "";  
			this.createNewusercolor = "";
			crateuserDetailsAL.clear();
			
			if(crateuserDetailsAL.size()==0) {
							
					UltraDataBean ultraTablevalue = new UltraDataBean("","","");
					crateuserDetailsAL.add(ultraTablevalue);
		   }
			
		}catch (Exception e) {
			
		}
		
		}
	
	
	
	
	
	public void emailTextboxListen(){

		try{
			
			this.createNewuserMessage = "";  
			this.createNewusercolor = "";
		}catch (Exception e) {
			
		}
		
		}
	
	
	public void nameTextboxListen(){

		try{
			
			this.createNewuserMessage = "";  
			this.createNewusercolor = "";
			
		}catch (Exception e) {
			
		}
		
		}
	
	
	
	public void deletetableMethod(int rowIndex){

		try{
			System.out.println(crateuserDetailsAL.size()+":::::::::::::rowIndex:::::"+rowIndex);
			crateuserDetailsAL.remove(rowIndex);
			
			
		}catch (Exception e) {
			
		}
		
		}
	
	public void addtablemethod() {
		
		try {
			
			for(int i=0;i<crateuserDetailsAL.size();i++) {
					
					String newulwfEmail=crateuserDetailsAL.get(i).emailID.toString();
					
					if(newulwfEmail.equals("")||newulwfEmail.isEmpty()) {
						
						this.createNewuserMessage = "Please enter the Email fields and to proceed further.";  
						this.createNewusercolor = "red";
						return;
						}
					
					}
				
				
				UltraDataBean ultraTablevalue = new UltraDataBean("","","");
				crateuserDetailsAL.add(ultraTablevalue);
								
				
			
			}catch (Exception e) {
				
				
			}
		
		}

	
	
	
	
	public String randomGenpwd(int length) {


		char[] chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();
		Random rnd = new Random();
		StringBuilder sb = new StringBuilder((100000 + rnd.nextInt(900000)) + "");
		for (int i = 0; i < length; i++)
			sb.append(chars[rnd.nextInt(chars.length)]);

		return sb.toString();


	}
	
	
	public static boolean validate(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX .matcher(emailStr);
        return matcher.find();
}
	
	public void userSaveAction() {
		System.out.println("Entering: " + new Exception().getStackTrace()[0].getClassName() + "."
				+ new Exception().getStackTrace()[0].getMethodName());
		try {

			System.out.println("HIIIIIIIIIIIIIIIxcIIIIIIIIIIIIII"+crateuserDetailsAL.size());

		
			
			if(crateuserDetailsAL.size()==0)
			{

				this.createNewuserMessage = "Please add atlest one user and to proceed further.";  
				this.createNewusercolor = "red";
				return;
				
			}
			
			for(int i=0;i<crateuserDetailsAL.size();i++) {
				
				String ulwfEmail=crateuserDetailsAL.get(i).emailID.toString();
				if(ulwfEmail.equals("")||ulwfEmail.isEmpty()) {
					
					this.createNewuserMessage = "Please enter the Email fields and to proceed further.";  
					this.createNewusercolor = "red";
					return;
					}
				
				
				boolean boolval= validate(ulwfEmail);
				
				System.out.println("boolval*********"+boolval);
				if(!boolval) {
					
					this.createNewuserMessage = "Please enter the valid Email and to proceed further.";  
					this.createNewusercolor = "red";
					return;
					
				}
				

			}
			
			
			
			PropUtil prop = new PropUtil();
			String loginXmlFile = prop.getProperty("LOGIN_XML_FILE");
			String HIERARCHY_XML_DIR = prop.getProperty("HIERARCHY_XML_DIR");		
			boolean fileexists = Globals.isFileExists(HIERARCHY_XML_DIR,loginXmlFile);
			String hierarchyConfigFile="Hierarchy_Config.xml";
			Document doc = null;
			FileInputStream fis = null;
			FileOutputStream fos = null;
			DocumentBuilderFactory dbf = null;
			DocumentBuilder db = null;
			String alredythrMailID="";
			FacesContext fxContext = FacesContext.getCurrentInstance();
			ExternalContext extContext = fxContext.getExternalContext();
			Map sessionMap = extContext.getSessionMap();
			LoginBean lb = (LoginBean) sessionMap.get("loginBean");
			String pwsd=randomGenpwd(8);
			System.out.println("PPPPPPPPPPPPPPPPPPPPPPP"+pwsd);
			

			System.out.println("^^^^^^^^^^::::::::::::::^^^^^^"+pureEditModeURL);
			Hashtable userNameHT=new Hashtable();
			if (!fileexists) {

				System.out.println("XML File does not exist. Creating New One.");

				dbf = DocumentBuilderFactory.newInstance();
				db = dbf.newDocumentBuilder();
				doc = db.newDocument();
				String root1 = "Obiee_Users";
				Element rootElement1 = doc.createElement(root1);
				doc.appendChild(rootElement1);

				TransformerFactory transformerFactory = TransformerFactory
						.newInstance();
				Transformer transformer = transformerFactory.newTransformer();
				transformer.setOutputProperty(OutputKeys.INDENT, "yes");
				DOMSource source = new DOMSource(doc);
				fos = new FileOutputStream(HIERARCHY_XML_DIR
						+ loginXmlFile);
				StreamResult result = new StreamResult(fos);
				transformer.transform(source, result);
				result.getOutputStream().flush();
				result.getOutputStream().close();
				System.out.println("XML File was Created."); 

			}
			String custKey = ""; 	
			System.out.println("^^^^^^^^^^:::11111111111111:::::::::::^^^^^^");
			if(this.urlParameter.equalsIgnoreCase("true") || pureEditModeURL.equalsIgnoreCase("true")){
				custKey = this.urlcus_key;
				}
				else {
					custKey = lb.getCustomerKey();
				}
			ArrayList<String> cannotSaveAL = new ArrayList<>();
			String users = "";
			doc = Globals.openXMLFile(HIERARCHY_XML_DIR, loginXmlFile);
			NodeList root = doc.getElementsByTagName("Obiee_Users");
			Node firstRootNode = root.item(0);
			NodeList childNodes = firstRootNode.getChildNodes();
			for (int i = 0; i < crateuserDetailsAL.size(); i++) {
				
				
				String accessStartDte = "";
				String accessEndDte = "";
				String dateFormat = prop.getProperty("DATE_FORMAT");
				String emailId = crateuserDetailsAL.get(i).emailID;
				userNameHT.put("UsernameList", crateuserDetailsAL.get(i).emailID.toString());
				boolean flag = true; 
				for(int m=0;m<childNodes.getLength();m++){

					if(childNodes.item(m).getNodeType() == Node.ELEMENT_NODE){

						alredythrMailID = childNodes.item(m).getTextContent();
						String key = ((Element) childNodes.item(m)).getAttribute("CustomerKey");
						System.out.println(key+":*********%%%%%%%%%%%%%%%*************:"+custKey);

						System.out.println(alredythrMailID+":*********alredythrMailID*************:"+emailId);

						if(alredythrMailID.equalsIgnoreCase(emailId) && custKey.equalsIgnoreCase(key)) {
							cannotSaveAL.add(emailId);
							users = users.isEmpty() ? emailId : users+", "+emailId;
							break;
						}
					}

				}
			}
			
			
//			if(!cannotSaveAL.isEmpty()) {
//				this.createNewuserMessage = "These users "+users+" is already exists under different company. Please provide different id.";  
//				this.createNewusercolor = "red";
//				return;
//			}
			System.out.println(crateuserDetailsAL.size()+":*********%%%%%%%crateuserDetailsAL.size()%%%%%%%%*************:"+custKey);
			for (int i = 0; i < crateuserDetailsAL.size(); i++) {
				
				
				String accessStartDte = "";
				String accessEndDte = "";
				String dateFormat = prop.getProperty("DATE_FORMAT");
				String emailId = crateuserDetailsAL.get(i).emailID;
				userNameHT.put("UsernameList", crateuserDetailsAL.get(i).emailID.toString());
				boolean flag = true; 
				for(int m=0;m<childNodes.getLength();m++){

					if(childNodes.item(m).getNodeType() == Node.ELEMENT_NODE){

						alredythrMailID = childNodes.item(m).getTextContent();
						String key = ((Element) childNodes.item(m)).getAttribute("CustomerKey");
						System.out.println(key+":*********%%%%%%%UltraMailID%%%%%%%%*************:"+custKey);

						System.out.println(alredythrMailID+":*********alredythrMailID*************:"+emailId);

						if(alredythrMailID.equalsIgnoreCase(emailId) && custKey.equalsIgnoreCase(key)) {
							flag = false;
							break;
						}
					}

				}
				if(flag) {
					Element childNode = doc.createElement("User");
					firstRootNode.appendChild(childNode);
					System.out.println(alredythrMailID+":*********alredythrMailID*$$$$$$$$$$$$$$$$$************:"+emailId);
					childNode.setAttribute("Email_ID", emailId);
					childNode.setAttribute("Access_Start_Date", accessStartDte);
					childNode.setAttribute("Access_End_Date", accessEndDte);
					int accessMaxID = Globals.getMaxID(HIERARCHY_XML_DIR, hierarchyConfigFile,"Acess_User_ID", "ID");
					childNode.setAttribute("Access_Unique_ID", String.valueOf(accessMaxID));
					childNode.setAttribute("Access_Type", "Enabled");
					childNode.setAttribute("Allowed_Hierarchies", "");	
					//childNode.setAttribute("CustomerKey",ultraWfcustomerKey);
					if(this.urlParameter.equalsIgnoreCase("true") || pureEditModeURL.equalsIgnoreCase("true")){
					childNode.setAttribute("CustomerKey",this.urlcus_key);
					}
					else {
						childNode.setAttribute("CustomerKey",lb.getCustomerKey());
					}
					childNode.setAttribute("Super_Privilege_Admin", "false");	 // code change VIJAY
					childNode.setAttribute("Login_ID",emailId);
					childNode.setAttribute("First_Name", String.valueOf(crateuserDetailsAL.get(i).username));
					childNode.setAttribute("Last_Name","");	
					childNode.setAttribute("Title","");
					childNode.setAttribute("Phone_Number_1","");	
					childNode.setAttribute("Phone_Number_2","");	
					childNode.setAttribute("Phone_Number_3","");	
					childNode.setAttribute("E_Mail_ID_1","");
					childNode.setAttribute("E_Mail_ID_2","");	
					childNode.setAttribute("Address","");
					childNode.setAttribute("City","");
					childNode.setAttribute("State","");
					childNode.setAttribute("Country","");
					childNode.setAttribute("Zip","");
					childNode.setAttribute("User_Password",Inventory.store(pwsd));
					childNode.setAttribute("New_Customer", "");
					childNode.setAttribute("Active", "true");
					childNode.setAttribute("Disable", "false");
					childNode.setTextContent(emailId);

					System.out.println("END");
				}
			}

			Globals.writeXMLFile(doc, HIERARCHY_XML_DIR, loginXmlFile);
		//	getUltraUSERXML(doc,ultraWfLoginName);
			//this.createNewuserMessage = "Workflow User saved successfully. Please close the popup and select the member for Workflow. ";  
			workFlowBean wfb = new workFlowBean();
			Hashtable membersFromLaXmlHT = wfb.getAllUsersFromlaXML();
			Hashtable membersHT = new Hashtable<>();
			membersNameAL.clear();
			membersNameAL = new ArrayList<>();
			allMembersNameAL = new ArrayList<>();


			for(int i=0;i<membersFromLaXmlHT.size();i++){
				membersHT = (Hashtable)membersFromLaXmlHT.get(i);
				System.out.println("********userSaveAction*****************"+this.urlParameter);
				
			if(this.urlParameter.equalsIgnoreCase("true") || pureEditModeURL.equalsIgnoreCase("true")){
					
				System.out.println("********TRUE*****************"+this.urlParameter);
					if(membersHT.get("CustomerKey") != null && !String.valueOf(membersHT.get("CustomerKey")).equalsIgnoreCase(this.urlcus_key)) {
						
						continue;
						
					 }
					
			}else {
						
				System.out.println("********FALSE*****************"+this.urlParameter);
						if(membersHT.get("CustomerKey") != null && !String.valueOf(membersHT.get("CustomerKey")).equalsIgnoreCase(lb.getCustomerKey())){
							continue;
						}
						
					}
			
				String empname=(String)membersHT.get("Employee_Name");
				if(viewMenberNameAL.contains(empname))
					continue;
				membersNameAL.add(empname);
				allMembersNameAL.add(empname);

			}
			
			this.createNewuserMessage = "User saved successfully.";
			this.createNewusercolor = "blue";
			
		}catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("Exiting: " + new Exception().getStackTrace()[0].getClassName() + "."
				+ new Exception().getStackTrace()[0].getMethodName());
	}
	
	
	public String getMsg4WF() {
		return msg4WF;
	}
	public void setMsg4WF(String msg4wf) {
		msg4WF = msg4wf;
	}
	public boolean isShowRejectedButton() {
		return showRejectedButton;
	}
	public void setShowRejectedButton(boolean showRejectedButton) {
		this.showRejectedButton = showRejectedButton;
	}
	public String getSelectedStatus() {
		return selectedStatus;
	}
	public void setSelectedStatus(String selectedStatus) {
		this.selectedStatus = selectedStatus;
	}
	public String getReturnRejectedMessage() {
		return returnRejectedMessage;
	}
	public void setReturnRejectedMessage(String returnRejectedMessage) {
		this.returnRejectedMessage = returnRejectedMessage;
	}
	public String getRejectedSendNotification2() {
		return rejectedSendNotification2;
	}
	public void setRejectedSendNotification2(String rejectedSendNotification2) {
		this.rejectedSendNotification2 = rejectedSendNotification2;
	}
	public String getRejectedSetStatus2() {
		return rejectedSetStatus2;
	}
	public void setRejectedSetStatus2(String rejectedSetStatus2) {
		this.rejectedSetStatus2 = rejectedSetStatus2;
	}
	public String getRejectedReturn2() {
		return rejectedReturn2;
	}
	public void setRejectedReturn2(String rejectedReturn2) {
		this.rejectedReturn2 = rejectedReturn2;
	}
	public String getMessage4SetStage() {
		return message4SetStage;
	}
	public void setMessage4SetStage(String message4SetStage) {
		this.message4SetStage = message4SetStage;
	}
	public boolean isIschangedStage1() {
		return ischangedStage1;
	}
	public void setIschangedStage1(boolean ischangedStage1) {
		this.ischangedStage1 = ischangedStage1;
	}
	public boolean isIschangedStage() {
		return ischangedStage;
	}
	public void setIschangedStage(boolean ischangedStage) {
		this.ischangedStage = ischangedStage;
	}
	public String getSetStageMsg() {
		return setStageMsg;
	}
	public void setSetStageMsg(String setStageMsg) {
		this.setStageMsg = setStageMsg;
	}
	public void setModel(DashboardModel model) {
		this.model = model;
	}
	public DashboardModel getCustommodel() {
		return custommodel;
	}

	public void setCustommodel(DashboardModel custommodel) {
		this.custommodel = custommodel;
	}
	public String getHierID() {
		return hierID;
	}

	public void setHierID(String hierID) {
		this.hierID = hierID;
	}

	public String getStageNameFromAddStage() {
		return stageNameFromAddStage;
	}

	public void setStageNameFromAddStage(String stageNameFromAddStage) {
		this.stageNameFromAddStage = stageNameFromAddStage;
	}
	public String getParallelType() {
		return parallelType;
	}
	public void setParallelType(String parallelType) {
		this.parallelType = parallelType;
	}
	public String getTapName() {
		return tapName;
	}

	public void setTapName(String tapName) {
		this.tapName = tapName;
	}

	public String getCusStageNameValue() {
		return cusStageNameValue;
	}

	public void setCusStageNameValue(String cusStageNameValue) {
		this.cusStageNameValue = cusStageNameValue;
	}
	public String getStageNumberFromAddStage() {
		return stageNumberFromAddStage;
	}

	public void setStageNumberFromAddStage(String stageNumberFromAddStage) {
		this.stageNumberFromAddStage = stageNumberFromAddStage;
	}

	public String getMinApproveValue() {
		return minApproveValue;
	}

	public void setMinApproveValue(String minApproveValue) {
		this.minApproveValue = minApproveValue;
	}

	public String getPropertiesType() {
		return propertiesType;
	}

	public void setPropertiesType(String propertiesType) {
		this.propertiesType = propertiesType;
	}
	public String getSelectionType() {
		return selectionType;
	}

	public void setSelectionType(String selectionType) {
		this.selectionType = selectionType;
	}

	public TreeNode getStageRoot() {
		return stageRoot;
	}

	public void setStageRoot(TreeNode stageRoot) {
		this.stageRoot = stageRoot;
	}

	public DashboardModel getStageModel() {
		return stageModel;
	}

	public void setStageModel(DashboardModel stageModel) {
		this.stageModel = stageModel;
	}

	public String getStageNameValue1() {
		return stageNameValue1;
	}

	public void setStageNameValue1(String stageNameValue1) {
		this.stageNameValue1 = stageNameValue1;
	}
	public ArrayList getMembersNameAL() {
		return membersNameAL;
	}

	public void setMembersNameAL(ArrayList membersNameAL) {
		this.membersNameAL = membersNameAL;
	}

	public ArrayList getStatusCodesAL() {
		return statusCodesAL;
	}

	public void setStatusCodesAL(ArrayList statusCodesAL) {
		this.statusCodesAL = statusCodesAL;
	}
	public String[] getFrwdMembers() {
		return frwdMembers;
	}

	public void setFrwdMembers(String[] frwdMembers) {
		this.frwdMembers = frwdMembers;
	}
	public ArrayList getSelectedMembersAL() {
		return selectedMembersAL;
	}

	public void setSelectedMembersAL(ArrayList selectedMembersAL) {
		this.selectedMembersAL = selectedMembersAL;
	}

	public String[] getSelectedMembers() {
		return selectedMembers;
	}

	public void setSelectedMembers(String[] selectedMembers) {
		this.selectedMembers = selectedMembers;
	}
	public ArrayList getSelectedStatusCodesAL() {
		return selectedStatusCodesAL;
	}

	public void setSelectedStatusCodesAL(ArrayList selectedStatusCodesAL) {
		this.selectedStatusCodesAL = selectedStatusCodesAL;
	}

	public String[] getFrwdStatusCodes() {
		return frwdStatusCodes;
	}

	public void setFrwdStatusCodes(String[] frwdStatusCodes) {
		this.frwdStatusCodes = frwdStatusCodes;
	}

	public String[] getSelectedStatusCodes() {
		return selectedStatusCodes;
	}

	public void setSelectedStatusCodes(String[] selectedStatusCodes) {
		this.selectedStatusCodes = selectedStatusCodes;
	}
	public String getSelectedStageRole() {
		return selectedStageRole;
	}

	public void setSelectedStageRole(String selectedStageRole) {
		this.selectedStageRole = selectedStageRole;
	}

	public ArrayList getStageRoleAL() {

		return stageRoleAL;
	}

	public void setStageRoleAL(ArrayList stageRoleAL) {
		this.stageRoleAL = stageRoleAL;
	}

	public String getWfAdminValidationmsg() {
		return wfAdminValidationmsg;
	}

	public void setWfAdminValidationmsg(String wfAdminValidationmsg) {
		this.wfAdminValidationmsg = wfAdminValidationmsg;
	}

	public String getStageNameValue() {
		return stageNameValue;
	}

	public void setStageNameValue(String stageNameValue) {
		this.stageNameValue = stageNameValue;
	}
	public ArrayList getPanelAL() {
		return panelAL;
	}

	public void setPanelAL(ArrayList panelAL) {
		this.panelAL = panelAL;
	}
	public ArrayList getEmpNamesAL() {
		return empNamesAL;
	}

	public void setEmpNamesAL(ArrayList empNamesAL) {
		this.empNamesAL = empNamesAL;
	}

	public ArrayList getSMsgAL() {
		return SMsgAL;
	}

	public void setSMsgAL(ArrayList sMsgAL) {
		SMsgAL = sMsgAL;
	}
	public String getTempValue() {
		return tempValue;
	}

	public void setTempValue(String tempValue) {
		this.tempValue = tempValue;
	}
	public String getUsercreateStageNumb() {
		return usercreateStageNumb;
	}

	public void setUsercreateStageNumb(String usercreateStageNumb) {
		this.usercreateStageNumb = usercreateStageNumb;
	}
	public String getHierName() {
		return hierName;
	}
	public void setHierName(String hierName) {
		this.hierName = hierName;
	}
	public String getFolderType() {
		return folderType;
	}
	public void setFolderType(String folderType) {
		this.folderType = folderType;
	}
	public String getFolderPath() {
		return folderPath;
	}
	public void setFolderPath(String folderPath) {
		this.folderPath = folderPath;
	}

	public String getExternalUserName() {
		return externalUserName;
	}

	public void setExternalUserName(String externalUserName) {
		this.externalUserName = externalUserName;
	}

	public String getExternalAuthURL() {
		return externalAuthURL;
	}

	public void setExternalAuthURL(String externalAuthURL) {
		this.externalAuthURL = externalAuthURL;
	}

	public String getDeveloperKey() {
		return developerKey;
	}

	public void setDeveloperKey(String developerKey) {
		this.developerKey = developerKey;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getFolderID() {
		return folderID;
	}

	public void setFolderID(String folderID) {
		this.folderID = folderID;
	}

	public String getSelectedAttributeType() {
		return selectedAttributeType;
	}

	public void setSelectedAttributeType(String selectedAttributeType) {
		this.selectedAttributeType = selectedAttributeType;
	}

	public String getSelectedAttribute() {
		return selectedAttribute;
	}

	public void setSelectedAttribute(String selectedAttribute) {
		this.selectedAttribute = selectedAttribute;
	}

	public ArrayList<String> getAttributes() {
		return attributes;
	}

	public void setAttributes(ArrayList<String> attributes) {
		this.attributes = attributes;
	}

	public Part getUploadedFile() {
		return uploadedFile;
	}

	public void setUploadedFile(Part uploadedFile) {
		this.uploadedFile = uploadedFile;
	}

	public ArrayList<RuleAttributeDataModel> getRuleAttrAL() {
		return ruleAttrAL;
	}

	public void setRuleAttrAL(ArrayList<RuleAttributeDataModel> ruleAttrAL) {
		this.ruleAttrAL = ruleAttrAL;
	}

	public ArrayList<RuleDataModel> getRulesAL() {
		return rulesAL;
	}

	public void setRulesAL(ArrayList<RuleDataModel> rulesAL) {
		this.rulesAL = rulesAL;
	}

	public ArrayList<RuleAttributeDataModel> getRulesALTemp() {
		return rulesALTemp;
	}

	public void setRulesALTemp(ArrayList<RuleAttributeDataModel> rulesALTemp) {
		this.rulesALTemp = rulesALTemp;
	}

	public String getSelectedAttributeDataType() {
		return selectedAttributeDataType;
	}

	public void setSelectedAttributeDataType(String selectedAttributeDataType) {
		this.selectedAttributeDataType = selectedAttributeDataType;
	}

	public Hashtable<String, String> getAttrDataTypeHT() {
		return attrDataTypeHT;
	}

	public void setAttrDataTypeHT(Hashtable<String, String> attrDataTypeHT) {
		this.attrDataTypeHT = attrDataTypeHT;
	}

	public Hashtable<String, String> getDefaultAttrsHT() {
		return defaultAttrsHT;
	}

	public void setDefaultAttrsHT(Hashtable<String, String> defaultAttrsHT) {
		this.defaultAttrsHT = defaultAttrsHT;
	}
	
	public boolean isExternalUserFlag() {
		return externalUserFlag;
	}

	public void setExternalUserFlag(boolean externalUserFlag) {
		this.externalUserFlag = externalUserFlag;
	}

	public boolean isSendPrimaryFileOnlyFlag() {
		return sendPrimaryFileOnlyFlag;
	}

	public void setSendPrimaryFileOnlyFlag(boolean sendPrimaryFileOnlyFlag) {
		this.sendPrimaryFileOnlyFlag = sendPrimaryFileOnlyFlag;
	}
	
	public String getReturnPassMessagecolor() {
		return returnPassMessagecolor;
	}

	public void setReturnPassMessagecolor(String returnPassMessagecolor) {
		this.returnPassMessagecolor = returnPassMessagecolor;
	}

	public String getReturnPassMessage() {
		return returnPassMessage;
	}

	public void setReturnPassMessage(String returnPassMessage) {
		this.returnPassMessage = returnPassMessage;
	}

	
	

	public boolean isAutologinbool() {
		return autologinbool;
	}

	public void setAutologinbool(boolean autologinbool) {
		this.autologinbool = autologinbool;
	}

	public boolean isAllowuploadbool() {
		return allowuploadbool;
	}

	public void setAllowuploadbool(boolean allowuploadbool) {
		this.allowuploadbool = allowuploadbool;
	}
	public String getFileuploadstatusStr() {
		return fileuploadstatusStr;
	}

	public void setFileuploadstatusStr(String fileuploadstatusStr) {
		this.fileuploadstatusStr = fileuploadstatusStr;
	}

	public String getFilterVal() {
		return filterVal;
	}

	public void setFilterVal(String filterVal) {
		this.filterVal = filterVal;
	}
	public DashboardModel getModelold() {
		return modelold;
	}

	public void setModelold(DashboardModel modelold) {
		this.modelold = modelold;
	}
	public String getBi_metadata_dir() {
		return bi_metadata_dir;
	}

	public void setBi_metadata_dir(String bi_metadata_dir) {
		this.bi_metadata_dir = bi_metadata_dir;
	}

	public UploadedFile getFile() {
		return file;
	}

	public void setFile(UploadedFile file) {
		this.file = file;
	}

	public boolean isUseFlash() {
		return useFlash;
	}

	public void setUseFlash(boolean useFlash) {
		this.useFlash = useFlash;
	}

	public String getExternalfilename() {
		return externalfilename;
	}

	public void setExternalfilename(String externalfilename) {
		this.externalfilename = externalfilename;
	}

	public String getAuthorizeMsgstr() {
		return authorizeMsgstr;
	}

	public void setAuthorizeMsgstr(String authorizeMsgstr) {
		this.authorizeMsgstr = authorizeMsgstr;
	}

	public String getSetStageMsgcolor() {
		return setStageMsgcolor;
	}

	public void setSetStageMsgcolor(String setStageMsgcolor) {
		this.setStageMsgcolor = setStageMsgcolor;
	}
	public String getMessage4MemberSetStage() {
		return message4MemberSetStage;
	}

	public void setMessage4MemberSetStage(String message4MemberSetStage) {
		this.message4MemberSetStage = message4MemberSetStage;
	}


	public String getMessage4MemberSetColor() {
		return message4MemberSetColor;
	}
	public String getMessage4SetStageColor() {
		return message4SetStageColor;
	}

	public void setMessage4SetStageColor(String message4SetStageColor) {
		this.message4SetStageColor = message4SetStageColor;
	}

	public void setMessage4MemberSetColor(String message4MemberSetColor) {
		this.message4MemberSetColor = message4MemberSetColor;
	}
	public String getAuthorizeMsgColor() {
		return authorizeMsgColor;
	}

	public void setAuthorizeMsgColor(String authorizeMsgColor) {
		this.authorizeMsgColor = authorizeMsgColor;
	}

	public String getShowhandleTitleBox() {
		return showhandleTitleBox;
	}

	public void setShowhandleTitleBox(String showhandleTitleBox) {
		this.showhandleTitleBox = showhandleTitleBox;
	}

	public String getPassSendNotification2() {
		return passSendNotification2;
	}

	public void setPassSendNotification2(String passSendNotification2) {
		this.passSendNotification2 = passSendNotification2;
	}

	public String getSelectedMember() {
		return selectedMember;
	}

	public void setSelectedMember(String selectedMember) {
		this.selectedMember = selectedMember;
	}
	public ArrayList getPanelModelAL() {
		return panelModelAL;
	}

	public void setPanelModelAL(ArrayList panelModelAL) {
		this.panelModelAL = panelModelAL;
	}
	public String getReturnRejectedMessagecolor() {
		return returnRejectedMessagecolor;
	}

	public void setReturnRejectedMessagecolor(String returnRejectedMessagecolor) {
		this.returnRejectedMessagecolor = returnRejectedMessagecolor;
	}

	public boolean isShowPassButton() {
		return showPassButton;
	}

	public void setShowPassButton(boolean showPassButton) {
		this.showPassButton = showPassButton;
	}
	
	public ArrayList getRejectedReturnstagestatusAL() {
		return rejectedReturnstagestatusAL;
	}
	public void setRejectedReturnstagestatusAL(ArrayList rejectedReturnstagestatusAL) {
		this.rejectedReturnstagestatusAL = rejectedReturnstagestatusAL;
	}
	public String getFieldsetWidth() {
		return fieldsetWidth;
	}
	public void setFieldsetWidth(String fieldsetWidth) {
		this.fieldsetWidth = fieldsetWidth;
	}
	public String getViewstagememberRole() {
		return viewstagememberRole;
	}

	public void setViewstagememberRole(String viewstagememberRole) {
		this.viewstagememberRole = viewstagememberRole;
	}

	public ArrayList getViewstagestatusAL() {
		return viewstagestatusAL;
	}

	public void setViewstagestatusAL(ArrayList viewstagestatusAL) {
		this.viewstagestatusAL = viewstagestatusAL;
	}

	public ArrayList getViewMenberNameAL() {
		return viewMenberNameAL;
	}

	public void setViewMenberNameAL(ArrayList viewMenberNameAL) {
		this.viewMenberNameAL = viewMenberNameAL;
	}

	public String getViewstagePropertiesValue() {
		return viewstagePropertiesValue;
	}

	public void setViewstagePropertiesValue(String viewstagePropertiesValue) {
		this.viewstagePropertiesValue = viewstagePropertiesValue;
	}

	public String getViewstageminApprsRender() {
		return viewstageminApprsRender;
	}

	public void setViewstageminApprsRender(String viewstageminApprsRender) {
		this.viewstageminApprsRender = viewstageminApprsRender;
	}

	public String getVstageMinApprVal() {
		return vstageMinApprVal;
	}

	public void setVstageMinApprVal(String vstageMinApprVal) {
		this.vstageMinApprVal = vstageMinApprVal;
	}

	
	public String getVisable4publicNadmin() {
		return visable4publicNadmin;
	}

	public void setVisable4publicNadmin(String visable4publicNadmin) {
		this.visable4publicNadmin = visable4publicNadmin;
	}

	public String getRender4publicNadmin() {
		return render4publicNadmin;
	}
	public void setRender4publicNadmin(String render4publicNadmin) {
		this.render4publicNadmin = render4publicNadmin;
	}
	public String getMinlablVAL() {
		return minlablVAL;
	}

	public void setMinlablVAL(String minlablVAL) {
		this.minlablVAL = minlablVAL;
	}
	public String getMinlablVALpercent() {
		return minlablVALpercent;
	}

	public void setMinlablVALpercent(String minlablVALpercent) {
		this.minlablVALpercent = minlablVALpercent;
	}
	public boolean isStatusCode4DocsFlag() {
		return statusCode4DocsFlag;
	}

	public void setStatusCode4DocsFlag(boolean statusCode4DocsFlag) {
		this.statusCode4DocsFlag = statusCode4DocsFlag;
	}
	public boolean isChangeprimarydocFlag() {
		return changeprimarydocFlag;
	}

	public void setChangeprimarydocFlag(boolean changeprimarydocFlag) {
		this.changeprimarydocFlag = changeprimarydocFlag;
	}

	public ArrayList<RuleDataModel> getSelectedrulesAL() {
		return selectedrulesAL;
	}

	public void setSelectedrulesAL(ArrayList<RuleDataModel> selectedrulesAL) {
		this.selectedrulesAL = selectedrulesAL;
	}

	public String getFilterVal1() {
		return filterVal1;
	}

	public void setFilterVal1(String filterVal1) {
		this.filterVal1 = filterVal1;
	}
	
	public ArrayList getAllviewMenberNameAL() {
		return allviewMenberNameAL;
	}

	public void setAllviewMenberNameAL(ArrayList allviewMenberNameAL) {
		this.allviewMenberNameAL = allviewMenberNameAL;
	}
	
	public String getCreateNewuserMessage() {
		return createNewuserMessage;
	}

	public void setCreateNewuserMessage(String createNewuserMessage) {
		this.createNewuserMessage = createNewuserMessage;
	}

	public String getCreateNewusercolor() {
		return createNewusercolor;
	}

	public void setCreateNewusercolor(String createNewusercolor) {
		this.createNewusercolor = createNewusercolor;
	}

	public ArrayList<UltraDataBean> getCrateuserDetailsAL() {
		return crateuserDetailsAL;
	}

	public void setCrateuserDetailsAL(ArrayList<UltraDataBean> crateuserDetailsAL) {
		this.crateuserDetailsAL = crateuserDetailsAL;
	}

	public String getUrlParameter() {
		return urlParameter;
	}

	public void setUrlParameter(String urlParameter) {
		this.urlParameter = urlParameter;
	}

	
	public String getUrlLogin() {
		return urlLogin;
	}
	public void setUrlLogin(String urlLogin) {
		this.urlLogin = urlLogin;
	}
	public String getUrlcus_key() {
		return urlcus_key;
	}
	public void setUrlcus_key(String urlcus_key) {
		this.urlcus_key = urlcus_key;
	}
	public String getUrlWfhandshkeID() {
		return urlWfhandshkeID;
	}
	public void setUrlWfhandshkeID(String urlWfhandshkeID) {
		this.urlWfhandshkeID = urlWfhandshkeID;
	}
	public String getCloseDisplay() {
		return closeDisplay;
	}
	public void setCloseDisplay(String closeDisplay) {
		this.closeDisplay = closeDisplay;
	}
	public String getCloseDisplayADV() {
		return closeDisplayADV;
	}
	public void setCloseDisplayADV(String closeDisplayADV) {
		this.closeDisplayADV = closeDisplayADV;
	}
	
	public void setDashboardexpire(String dashboardexpire) {
		this.dashboardexpire = dashboardexpire;
	}
	public String getSelectedgotoStages() {
		return selectedgotoStages;
	}
	public void setSelectedgotoStages(String selectedgotoStages) {
		this.selectedgotoStages = selectedgotoStages;
	}
	public ArrayList getRejectedReturngotostageAL() {
		return rejectedReturngotostageAL;
	}
	public void setRejectedReturngotostageAL(ArrayList rejectedReturngotostageAL) {
		this.rejectedReturngotostageAL = rejectedReturngotostageAL;
	}
	public boolean isEnableEsign() {
		return enableEsign;
	}
	public void setEnableEsign(boolean enableEsign) {
		this.enableEsign = enableEsign;
	}
	public String getWorkflowName() {
		return workflowName;
	}
	public void setWorkflowName(String workflowName) {
		this.workflowName = workflowName;
	}
	public String getWorkflowID() {
		return workflowID;
	}
	public void setWorkflowID(String workflowID) {
		this.workflowID = workflowID;
	}
	public String getEditwrkflowflag() {
		return editwrkflowflag;
	}
	public void setEditwrkflowflag(String editwrkflowflag) {
		this.editwrkflowflag = editwrkflowflag;
	}
	public String getOneDriveHTxt() {
		return oneDriveHTxt;
	}
	public void setOneDriveHTxt(String oneDriveHTxt) {
		this.oneDriveHTxt = oneDriveHTxt;
	}
	public String getAuthText() {
		return authText;
	}
	public void setAuthText(String authText) {
		this.authText = authText;
	}
	public String getOneDriveAuthURL() {
		return oneDriveAuthURL;
	}
	public void setOneDriveAuthURL(String oneDriveAuthURL) {
		this.oneDriveAuthURL = oneDriveAuthURL;
	}
	public String getOneDriveMailID() {
		return oneDriveMailID;
	}
	public void setOneDriveMailID(String oneDriveMailID) {
		this.oneDriveMailID = oneDriveMailID;
	}
	public String getAuthMsg() {
		return authMsg;
	}
	public void setAuthMsg(String authMsg) {
		this.authMsg = authMsg;
	}
	public boolean isAllowEditUD() {
		return allowEditUD;
	}
	public void setAllowEditUD(boolean allowEditUD) {
		this.allowEditUD = allowEditUD;
	}
	
}  


