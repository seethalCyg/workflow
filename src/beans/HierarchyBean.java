package beans;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.print.Doc;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.swing.tree.TreeNode;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import managers.LoginProcessManager;
import managers.RulesManager;
import managers.WorkflowManager;
import model.ReportTree;

import org.richfaces.event.FileUploadEvent;
import org.richfaces.model.UploadedFile;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.novell.ldap.LDAPConnection;
import com.novell.ldap.LDAPEntry;
import com.novell.ldap.LDAPException;
import com.novell.ldap.LDAPSearchResults;

import rootLevel.dataProcess;
import utils.Globals;
import utils.Inventory;
import utils.PropUtil;

@ManagedBean(name = "hierarchyBean")
@SessionScoped
public class HierarchyBean implements Runnable{

	private static final boolean SOP_ENABLED = true;
	private static final boolean UPDATE_DB_FOR_EACH_NODE = false;

	/*public String msg = "";*/
	public String document_ID;
	public  String documentNameWF;
	public String redirectURL;
	public String  userNameID;
	
	public  String docType;
	public  String doccreatedBy;
	public  String doccreatedDate;
	
	public  String changeBTNpanelStr="false";
	public String statusCode4DocsFlag = "false";
	public String delete4DocsFlag = "false";
	public String ischangedUser="false";
	
	public String getIschangedUser() {
		return ischangedUser;
	}
	public void setIschangedUser(String ischangedUser) {
		this.ischangedUser = ischangedUser;
	}
	public  String docdueDate; 
	
	
	public String getDocdueDate() {
		return docdueDate;
	}
	public void setDocdueDate(String docdueDate) {
		this.docdueDate = docdueDate;
	}
	public String getDueDteClr() {
		return dueDteClr;
	}
	public void setDueDteClr(String dueDteClr) {
		this.dueDteClr = dueDteClr;
	}
	public String  dueDteClr;
	
	
	

	UploadedFile file;
	private boolean useFlash = false;
	boolean autoUpload = false;
	private String uploadedFolder4Attachment = "";
	private String dirname = null;
	private String rbid_metadata_dir = "";
	public String BIMetadataFile = null;
	private String bi_metadata_dir;
	
	public ArrayList<String> testAL=new ArrayList<>();
	public String getDocument_ID() {
		return document_ID;
	}
	public void setDocument_ID(String document_ID) {
		this.document_ID = document_ID;
	}
	public String userEmailId = "";
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
	public String confrmPassword="";
	public String getConfrmPassword() {
	return confrmPassword;
}
public void setConfrmPassword(String confrmPassword) {
	this.confrmPassword = confrmPassword;
}
	public String emailPassword = "";

	public String listfirstNameTXT = "";
	public String listlastNameTXT = "";
	public String listtitleTXT;

	public String listph1TXT;
	public String listph2TXT;
	public String listph3TXT;

	public String listMail1TXT;
	public String listMail2TXT;
	public String listaddressTXT;
	public String listcityTXT;
	public  String liststateTXT;
	public  String listcountryTXT;
	public  String listzipTXT ;
	public  String urlGlbParameter ="";

	public HierarchyBean(){
		try{

			//			FacesContext context = FacesContext.getCurrentInstance();                  //code change pandian  08AUG13
			//			Map sessionMap = context.getExternalContext().getSessionMap();
			//			LoginBean lb = (LoginBean) sessionMap.get("loginBean");

//			SessionExpireBean sesExpire=new SessionExpireBean();  // code change pandian 26Aug2013
//			String isSameSession=sesExpire.isSameSession();
//			System.out.println("isSameSession---->>"+isSameSession);
//			if(isSameSession.equalsIgnoreCase("redirect")){
//
//				System.out.println("Session timed out");
//				return;
//			}else{
//
//			}
			
			HttpServletRequest req = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
			String urlParameter=req.getParameter("advanceWorkflow")== null || req.getParameter("advanceWorkflow").length()<= 0 ? "false" :req.getParameter("advanceWorkflow");
			String urlLogin=req.getParameter("emailid");
		
			
			System.out.println("**********######urlParameter#########************:"+urlParameter);
						
			if(urlParameter.equalsIgnoreCase("true")){
				
				this.userNameID=urlLogin;
				this.urlGlbParameter=urlParameter;
			}
			
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	String formID = "";
	public String getFormID() {
		return formID;
	}
	public void setFormID(String formID) {
		this.formID = formID;
	}
	public HierarchyBean(String HierarchyID, boolean flag4GeneratingFact,String userName,String factGenType){
		this.regeneratedHierarchyID=HierarchyID;
		flag4reGeneratingFact = flag4GeneratingFact;
		this.userName = userName;
		this.factGenType = factGenType;
	}
	
	public void newcustomerBoxChangeListener() {
        System.out.println("newcustomerBoxChangeListener#############");
        this.ischangedUser="true";
}
	
	
	public void activeBoxListener() {
        System.out.println("activeBoxListener##########");
    this.ischangedUser="true";
}
	
	public void superPrivilegeListener() {
        System.out.println("superPrivilegeListener#########");
        this.ischangedUser="true";
}
	public void disableBoxChangeListener() {
		
        System.out.println("disableBoxChangeListener############");
        this.ischangedUser="true";
        
}
	
	public HierarchyBean(String addedName, int levelNo,String typeName,Hashtable valueHT,Hashtable basicValueHT,String selectedNdID	
			,String selectednodeName, String insertType,String addedNodeID,String previoustype4ReGenerate,Connection con,String addedNodeName,Document insertDoc){		//code change Vishnu 20Feb2014
		this.addedName=addedName;
		this.levelNo=levelNo;
		this.typeName=typeName;
		this.valueHT=valueHT;
		this.basicValueHT=basicValueHT;
		this.selectedNdID=selectedNdID;
		this.selectednodeName=selectednodeName;
		this.insertType=insertType;
		this.addedNodeID=addedNodeID;
		this.previoustype4ReGenerate=previoustype4ReGenerate;
		this.dbConnection=con;
		this.addedNodeName=addedNodeName;
		this.insertDoc=insertDoc;
	}

	private String LoginUserName=""; // code change Menaka 22APR2014

	public String getListfirstNameTXT() {
		return listfirstNameTXT;
	}
	public void setListfirstNameTXT(String listfirstNameTXT) {
		this.listfirstNameTXT = listfirstNameTXT;
	}
	public String getListlastNameTXT() {
		return listlastNameTXT;
	}
	public void setListlastNameTXT(String listlastNameTXT) {
		this.listlastNameTXT = listlastNameTXT;
	}
	public String getListtitleTXT() {
		return listtitleTXT;
	}
	public void setListtitleTXT(String listtitleTXT) {
		this.listtitleTXT = listtitleTXT;
	}
	public String getListph1TXT() {
		return listph1TXT;
	}
	public void setListph1TXT(String listph1txt) {
		listph1TXT = listph1txt;
	}
	public String getListph2TXT() {
		return listph2TXT;
	}
	public void setListph2TXT(String listph2txt) {
		listph2TXT = listph2txt;
	}
	public String getListph3TXT() {
		return listph3TXT;
	}
	public void setListph3TXT(String listph3txt) {
		listph3TXT = listph3txt;
	}
	public String getListMail1TXT() {
		return listMail1TXT;
	}
	public void setListMail1TXT(String listMail1TXT) {
		this.listMail1TXT = listMail1TXT;
	}
	public String getListMail2TXT() {
		return listMail2TXT;
	}
	public void setListMail2TXT(String listMail2TXT) {
		this.listMail2TXT = listMail2TXT;
	}
	public String getListaddressTXT() {
		return listaddressTXT;
	}
	public void setListaddressTXT(String listaddressTXT) {
		this.listaddressTXT = listaddressTXT;
	}
	public String getListcityTXT() {
		return listcityTXT;
	}
	public void setListcityTXT(String listcityTXT) {
		this.listcityTXT = listcityTXT;
	}
	public String getListstateTXT() {
		return liststateTXT;
	}
	public void setListstateTXT(String liststateTXT) {
		this.liststateTXT = liststateTXT;
	}
	public String getListcountryTXT() {
		return listcountryTXT;
	}
	public void setListcountryTXT(String listcountryTXT) {
		this.listcountryTXT = listcountryTXT;
	}
	public String getListzipTXT() {
		return listzipTXT;
	}
	public void setListzipTXT(String listzipTXT) {
		this.listzipTXT = listzipTXT;
	}
	private String fullCurrentprocessMember="";  // code change Menaka 23APR2014
	private String renderEditbutton = "true";
	public String getRenderEditbutton() {
		return renderEditbutton;
	}
	public void setRenderEditbutton(String renderEditbutton) {
		this.renderEditbutton = renderEditbutton;
	}
	public String getRenderEditbutton1() {
		return renderEditbutton1;
	}
	public void setRenderEditbutton1(String renderEditbutton1) {
		this.renderEditbutton1 = renderEditbutton1;
	}
	private String renderEditbutton1 = "false";

	public String getFullCurrentprocessMember() {
		return fullCurrentprocessMember;
	}
	public void setFullCurrentprocessMember(String fullCurrentprocessMember) {
		this.fullCurrentprocessMember = fullCurrentprocessMember;
	}
	public String getLoginUserName() {

		// code change Menaka 21APR2014
		FacesContext ctx = FacesContext.getCurrentInstance();
		ExternalContext extContext = ctx.getExternalContext();
		Map sessionMap = extContext.getSessionMap();
		LoginBean logbn = (LoginBean) sessionMap.get("loginBean");	
		LoginUserName=logbn.username;

		return LoginUserName;
	}
	public void setLoginUserName(String loginUserName) {
		LoginUserName = loginUserName;
	}

	private String msg4VersDisp="";  // code change Menaka 28MAR2014

	private String versionRestore="Save"; // code change Menaka 29MAR2014
	private boolean verNotePopupVisible;  // code change Menaka 03APR2014
	private String newVersionNo="";
	boolean primaryType = false;
	boolean matchCase4workFlow = false;
	public boolean isMatchCase4workFlow() {
		return matchCase4workFlow;
	}
	public void setMatchCase4workFlow(boolean matchCase4workFlow) {
		this.matchCase4workFlow = matchCase4workFlow;
	}
	public boolean isMatchCase4AuditTable() {
		return matchCase4AuditTable;
	}
	public void setMatchCase4AuditTable(boolean matchCase4AuditTable) {
		this.matchCase4AuditTable = matchCase4AuditTable;
	}


	boolean matchCase4AuditTable = false;


	public boolean isPrimaryType() {
		return primaryType;
	}
	public void setPrimaryType(boolean primaryType) {
		this.primaryType = primaryType;
	}
	public String getNewVersionNo() {
		return newVersionNo;
	}
	public void setNewVersionNo(String newVersionNo) {
		this.newVersionNo = newVersionNo;
	}
	public boolean isVerNotePopupVisible() {
		return verNotePopupVisible;
	}
	public void setVerNotePopupVisible(boolean verNotePopupVisible) {
		this.verNotePopupVisible = verNotePopupVisible;
	}
	public String getVersionRestore() {
		return versionRestore;
	}
	public void setVersionRestore(String versionRestore) {
		this.versionRestore = versionRestore;
	}
	public String getMsg4VersDisp() {
		return msg4VersDisp;
	}
	public void setMsg4VersDisp(String msg4VersDisp) {
		this.msg4VersDisp = msg4VersDisp;
	}

	private String fullCommonStatus="";  // code change Menaka 31MAR2014


	public String getFullCommonStatus() {
		return fullCommonStatus;
	}
	public void setFullCommonStatus(String fullCommonStatus) {
		this.fullCommonStatus = fullCommonStatus;
	}


	private String msg4UpdateUser="";  // code change Menaka 19MAR2014
	private boolean disableDataNHier=false;    // code change Menaka 20MAR2014

	private String note4VersionRestore="";  // code change Menaka 29MAR2014
	private String message=""; // code change Menaka 29MAR2014



	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getNote4VersionRestore() {
		return note4VersionRestore;
	}
	public void setNote4VersionRestore(String note4VersionRestore) {
		this.note4VersionRestore = note4VersionRestore;
	}


	private String hierType="0";  // code change Menaka 20MAR2014
	private String formName4OpenAddSegPopup = "";
	private String cmngFromPage4RIConstraints = "";

	String cmgFromViewVersion="";  // code change Menaka 03APR2014
	String cmgFromFactpage="";  // code change Menaka 07APR2014



	public String getCmgFromFactpage() {
		return cmgFromFactpage;
	}
	public void setCmgFromFactpage(String cmgFromFactpage) {
		this.cmgFromFactpage = cmgFromFactpage;
	}
	public String getCmgFromViewVersion() {
		return cmgFromViewVersion;
	}
	public void setCmgFromViewVersion(String cmgFromViewVersion) {
		this.cmgFromViewVersion = cmgFromViewVersion;
	}

	public String getCmngFromPage4RIConstraints() {
		return cmngFromPage4RIConstraints;
	}
	public void setCmngFromPage4RIConstraints(String cmngFromPage4RIConstraints) {
		this.cmngFromPage4RIConstraints = cmngFromPage4RIConstraints;
	}
	public String getFormName4OpenAddSegPopup() {
		return formName4OpenAddSegPopup;
	}

	public void setFormName4OpenAddSegPopup(String formName4OpenAddSegPopup) {
		this.formName4OpenAddSegPopup = formName4OpenAddSegPopup;
	}


	public String getHierType() {
		return hierType;
	}
	public void setHierType(String hierType) {
		this.hierType = hierType;
	}
	public boolean isDisableDataNHier() {
		return disableDataNHier;
	}
	public void setDisableDataNHier(boolean disableDataNHier) {
		this.disableDataNHier = disableDataNHier;
	}
	public String getMsg4UpdateUser() {
		return msg4UpdateUser;
	}
	public void setMsg4UpdateUser(String msg4UpdateUser) {

		this.msg4UpdateUser = msg4UpdateUser;
	}

	String value4Login="";  // code change Menaka 25MAR2014


	public String getValue4Login() {
		return value4Login;
	}
	public void setValue4Login(String value4Login) {
		this.value4Login = value4Login;
	}


	public boolean superAdminPrivillage=false;  // code change Menaka 24MAR2014

	public boolean isSuperAdminPrivillage() {
		return superAdminPrivillage;
	}
	public void setSuperAdminPrivillage(boolean superAdminPrivillage) {
		this.superAdminPrivillage = superAdminPrivillage;
	}



	public String msg4AddUsr="";   // code change Menaka 24MAR2014
	private String titleMsg4AddUsr = "";

	public String getTitleMsg4AddUsr() {
		return titleMsg4AddUsr;
	}
	public void setTitleMsg4AddUsr(String titleMsg4AddUsr) {
		this.titleMsg4AddUsr = titleMsg4AddUsr;
	}
	public String getMsg4AddUsr() {
		return msg4AddUsr;
	}
	public void setMsg4AddUsr(String msg4AddUsr) {
		this.msg4AddUsr = msg4AddUsr;
	}

	public String hostName="";  // code change Menaka 20MAR2014
	public String loginID="";
	public String password="";
	private String versionNumber = ""; //code change Jayaramu 22MAR14
	private int wfUniqueID=0; 
	public int getWfUniqueID() {
		return wfUniqueID;
	}
	public void setWfUniqueID(int wfUniqueID) {
		this.wfUniqueID = wfUniqueID;
	}
	public String getVersionNumber() {
		return versionNumber;
	}
	public void setVersionNumber(String versionNumber) {
		this.versionNumber = versionNumber;
	}


	public ArrayList<HeirarchyDataBean> selectedUserList=new ArrayList<>();   // code change Menaka 20MAR2014

	public ArrayList<HeirarchyDataBean> allUsersAL=new ArrayList<HeirarchyDataBean>();
	public ArrayList<HeirarchyDataBean> copyAllUsersAL=new ArrayList<HeirarchyDataBean>();


	public ArrayList getCopyAllUsersAL() {
		return copyAllUsersAL;
	}
	public void setCopyAllUsersAL(ArrayList copyAllUsersAL) {
		this.copyAllUsersAL = copyAllUsersAL;
	}
	public String getHostName() {
		return hostName;
	}
	public void setHostName(String hostName) {
		this.hostName = hostName;
	}
	public String getLoginID() {
		return loginID;
	}
	public void setLoginID(String loginID) {
		this.loginID = loginID;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}




	public ArrayList getAllUsersAL() {
		return allUsersAL;
	}
	public void setAllUsersAL(ArrayList allUsersAL) {
		this.allUsersAL = allUsersAL;
	}
	public ArrayList getSelectedUserList() {
		return selectedUserList;
	}
	public void setSelectedUserList(ArrayList selectedUserList) {
		this.selectedUserList = selectedUserList;
	}


	public ArrayList<HeirarchyDataBean> attachmentTabDetailsAL1=new ArrayList<HeirarchyDataBean>();
	
	public ArrayList hierarchyAL = new ArrayList<>();
	public ArrayList<HierarchydataBean> hierarchyList = new ArrayList<HierarchydataBean>();
	private String hierarchyID4Tree;
	//start code change Jayaramu
	private String type="";
	private String name="";
	private String flagForAdd="";
	private String parentNodeName="";
	private String editRollupFlag="";
	private String editDataFlag="";
	private String editHiryFlag="";
	private String reloadFlag="";
	private String segmentNumber="";
	private String hryCategory="";
	private String status="Independent";   // code change Menaka 13FEB2014
	private String statusColor="";   // code change Menaka 13FEB2014
	private String infoMessage="";
	private String segmentID=""; 
	private ArrayList statusValueAL = new ArrayList<>();   // code change Menaka 15FEB2014
	String msg1="";
	String fullMsg1="";  // code change Menaka 10APR2014



	public String getFullMsg1() {
		return fullMsg1;
	}
	public void setFullMsg1(String fullMsg1) {
		this.fullMsg1 = fullMsg1;
	}


	private String msg2="";
	private String message4RIconstraints = "";
	public String getMessage4RIconstraints() {
		return message4RIconstraints;
	}
	public void setMessage4RIconstraints(String message4rIconstraints) {
		message4RIconstraints = message4rIconstraints;
	}
	public String getMsg2() {
		return msg2;
	}
	public void setMsg2(String msg2) {
		this.msg2 = msg2;
	}

	private ArrayList selectType4InsAL= new ArrayList<>();   // code change Menaka 17FEB2014
	private String selectType4Ins="";
	private String popuptop;//Code change Harini 19FEB14
	private String popupleft;
	private String label4Rollup="";   // code change Menaka 19FEB2014
	private boolean disableType4Root=false;  
	public String addedNodeID="";
	public String addedNodeName="";
	private String segValueAttr4Edit = "";
	private String datasegRendered="";
	private ArrayList userListAL = new ArrayList<>();
	ArrayList<HeirarchyDataBean> selectedUserslistAL=new ArrayList<>();
	ArrayList rIConstraintsRulesAL = new ArrayList<>();
	ArrayList rIConstraintsRulesAL3 = new ArrayList<>();
	public ArrayList getrIConstraintsRulesAL3() {
		return rIConstraintsRulesAL3;
	}
	public void setrIConstraintsRulesAL3(ArrayList rIConstraintsRulesAL3) {
		this.rIConstraintsRulesAL3 = rIConstraintsRulesAL3;
	}
	ArrayList rIConstraintsRulesAL1 = new ArrayList<>();
	public ArrayList getrIConstraintsRulesAL1() {
		return rIConstraintsRulesAL1;
	}
	public void setrIConstraintsRulesAL1(ArrayList rIConstraintsRulesAL1) {
		this.rIConstraintsRulesAL1 = rIConstraintsRulesAL1;
	}
	public ArrayList getrIConstraintsRulesAL2() {
		return rIConstraintsRulesAL2;
	}
	public void setrIConstraintsRulesAL2(ArrayList rIConstraintsRulesAL2) {
		this.rIConstraintsRulesAL2 = rIConstraintsRulesAL2;
	}

	ArrayList rIConstraintsRulesAL2 = new ArrayList<>();
	ArrayList<HeirarchyDataBean> selectedRIConsRule=new ArrayList<>();
	ArrayList<HeirarchyDataBean> selectedRIConsRuleCompare=new ArrayList<>();


	public ArrayList<HeirarchyDataBean> getSelectedRIConsRuleCompare() {
		return selectedRIConsRuleCompare;
	}
	public void setSelectedRIConsRuleCompare(
			ArrayList<HeirarchyDataBean> selectedRIConsRuleCompare) {
		this.selectedRIConsRuleCompare = selectedRIConsRuleCompare;
	}
	public ArrayList<HeirarchyDataBean> getSelectedRIConsRule() {
		return selectedRIConsRule;
	}
	public void setSelectedRIConsRule(
			ArrayList<HeirarchyDataBean> selectedRIConsRule) {
		this.selectedRIConsRule = selectedRIConsRule;
	}
	public ArrayList getrIConstraintsRulesAL() {
		return rIConstraintsRulesAL;
	}
	public void setrIConstraintsRulesAL(ArrayList rIConstraintsRulesAL) {
		this.rIConstraintsRulesAL = rIConstraintsRulesAL;
	}
	public ArrayList<HeirarchyDataBean> getAttachmentTabDetailsAL1() {
		return attachmentTabDetailsAL1;
	}
	public void setAttachmentTabDetailsAL1(ArrayList<HeirarchyDataBean> attachmentTabDetailsAL1) {
		this.attachmentTabDetailsAL1 = attachmentTabDetailsAL1;
	}
	////////////////////
	public String memberloginID;
	private String currentStatgeName;
	private String currentStageNumb;
	private String totalStageNum;
	private String sendPrimaryFileOnly;
	private String allow_Upload;
	private String stageStatus;
	private String accessUniqID;
	private String memberStatus;
	private String memberMailID;
	private String commonStatus="";
	public String renderstatus4compltd; //code change pandian 19MAR2014	
	public String renderstatus4Rejected;
	public String renderstatus4Approved;
	public String renderstatus4Wip;
	private String currentprocessMember; // code change pandian 21APR2014 add member who is process and set his Role
	private String currentprocessMemberRole;
	private String wfmemberAcessType;


	public String getCurrentprocessMember() {
		return currentprocessMember;
	}
	public void setCurrentprocessMember(String currentprocessMember) {
		this.currentprocessMember = currentprocessMember;
	}
	public String getCurrentprocessMemberRole() {
		return currentprocessMemberRole;
	}
	public void setCurrentprocessMemberRole(String currentprocessMemberRole) {
		this.currentprocessMemberRole = currentprocessMemberRole;
	}
	public String getWfmemberAcessType() {
		return wfmemberAcessType;
	}
	public void setWfmemberAcessType(String wfmemberAcessType) {
		this.wfmemberAcessType = wfmemberAcessType;
	}


	private String selectedHierarchyType = "Independent";
	private String selectedHierarchyTypeCompare = "";
	private boolean isAdminConfigureRIConstraints = false;
	public boolean isAdminConfigureRIConstraints() {
		return isAdminConfigureRIConstraints;
	}
	public void setAdminConfigureRIConstraints(boolean isAdminConfigureRIConstraints) {
		this.isAdminConfigureRIConstraints = isAdminConfigureRIConstraints;
	}
	public String getSelectedHierarchyTypeCompare() {
		return selectedHierarchyTypeCompare;
	}
	public void setSelectedHierarchyTypeCompare(String selectedHierarchyTypeCompare) {
		this.selectedHierarchyTypeCompare = selectedHierarchyTypeCompare;
	}

	private String selectedReferenceHierarchy = "";
	private String selectedReferenceHierarchyCompare = "";
	private boolean flag4OpenRIConsValiPopup = false;
	public boolean isFlag4OpenRIConsValiPopup() {
		return flag4OpenRIConsValiPopup;
	}
	public void setFlag4OpenRIConsValiPopup(boolean flag4OpenRIConsValiPopup) {
		this.flag4OpenRIConsValiPopup = flag4OpenRIConsValiPopup;
	}
	public String getSelectedReferenceHierarchyCompare() {
		return selectedReferenceHierarchyCompare;
	}
	public void setSelectedReferenceHierarchyCompare(
			String selectedReferenceHierarchyCompare) {
		this.selectedReferenceHierarchyCompare = selectedReferenceHierarchyCompare;
	}

	private boolean flag4RenderedHyrType=true;
	private boolean enforceRIConstraints=true;
	public boolean isEnforceRIConstraints() {
		return enforceRIConstraints;
	}
	public void setEnforceRIConstraints(boolean enforceRIConstraints) {
		this.enforceRIConstraints = enforceRIConstraints;
	}
	public boolean isFlag4RenderedHyrType() {
		return flag4RenderedHyrType;
	}
	public void setFlag4RenderedHyrType(boolean flag4RenderedHyrType) {
		this.flag4RenderedHyrType = flag4RenderedHyrType;
	}

	private ArrayList referenceHierarchyAL = new ArrayList<>();

	public ArrayList getReferenceHierarchyAL() {
		return referenceHierarchyAL;
	}
	public void setReferenceHierarchyAL(ArrayList referenceHierarchyAL) {
		this.referenceHierarchyAL = referenceHierarchyAL;
	}
	public String getSelectedReferenceHierarchy() {
		return selectedReferenceHierarchy;
	}
	public void setSelectedReferenceHierarchy(String selectedReferenceHierarchy) {
		this.selectedReferenceHierarchy = selectedReferenceHierarchy;
	}
	public String getSelectedHierarchyType() {
		return selectedHierarchyType;
	}
	public void setSelectedHierarchyType(String selectedHierarchyType) {
		this.selectedHierarchyType = selectedHierarchyType;
	}
	public String getRenderstatus4Wip() {
		return renderstatus4Wip;
	}
	public void setRenderstatus4Wip(String renderstatus4Wip) {
		this.renderstatus4Wip = renderstatus4Wip;
	}
	public String getRenderstatus4compltd() {
		return renderstatus4compltd;
	}
	public void setRenderstatus4compltd(String renderstatus4compltd) {
		this.renderstatus4compltd = renderstatus4compltd;
	}
	public String getRenderstatus4Rejected() {
		return renderstatus4Rejected;
	}
	public void setRenderstatus4Rejected(String renderstatus4Rejected) {
		this.renderstatus4Rejected = renderstatus4Rejected;
	}
	public String getRenderstatus4Approved() {
		return renderstatus4Approved;
	}
	public void setRenderstatus4Approved(String renderstatus4Approved) {
		this.renderstatus4Approved = renderstatus4Approved;
	}
	public String getAccessUniqID() {
		return accessUniqID;
	}
	public void setAccessUniqID(String accessUniqID) {
		this.accessUniqID = accessUniqID;
	}
	public String getStageStatus() {
		return stageStatus;
	}
	public void setStageStatus(String stageStatus) {
		this.stageStatus = stageStatus;
	}


	public String getMemberloginID() {
		return memberloginID;
	}
	public void setMemberloginID(String memberloginID) {
		this.memberloginID = memberloginID;
	}
	public String getCurrentStatgeName() {
		return currentStatgeName;
	}
	public void setCurrentStatgeName(String currentStatgeName) {
		this.currentStatgeName = currentStatgeName;
	}
	public String getCurrentStageNumb() {
		return currentStageNumb;
	}
	public void setCurrentStageNumb(String currentStageNumb) {
		this.currentStageNumb = currentStageNumb;
	}
	public String getTotalStageNum() {
		return totalStageNum;
	}
	public void setTotalStageNum(String totalStageNum) {
		this.totalStageNum = totalStageNum;
	}

	public String getMemberStatus() {
		return memberStatus;
	}
	public void setMemberStatus(String memberStatus) {
		this.memberStatus = memberStatus;
	}
	public String getMemberMailID() {
		return memberMailID;
	}
	public void setMemberMailID(String memberMailID) {
		this.memberMailID = memberMailID;
	}
	public String getCommonStatus() {
		return commonStatus;
	}
	public void setCommonStatus(String commonStatus) {
		this.commonStatus = commonStatus;
	}


	///////////////

	public ArrayList<HeirarchyDataBean> getSelectedUserslistAL() {
		return selectedUserslistAL;
	}
	public void setSelectedUserslistAL(
			ArrayList<HeirarchyDataBean> selectedUserslistAL) {
		this.selectedUserslistAL = selectedUserslistAL;
	}
	public ArrayList getUserListAL() {
		return userListAL;
	}
	public void setUserListAL(ArrayList userListAL) {
		this.userListAL = userListAL;
	}
	public String getDatasegRendered() {
		return datasegRendered;
	}

	public void setDatasegRendered(String datasegRendered) {
		this.datasegRendered = datasegRendered;
	}

	public String getSegValueAttr4Edit() {
		return segValueAttr4Edit;
	}

	public void setSegValueAttr4Edit(String segValueAttr4Edit) {
		this.segValueAttr4Edit = segValueAttr4Edit;
	}


	public Document insertDoc;

	public Document getInsertDoc() {
		return insertDoc;
	}
	public void setInsertDoc(Document insertDoc) {
		this.insertDoc = insertDoc;
	}
	public String getAddedNodeName() {
		return addedNodeName;
	}
	public void setAddedNodeName(String addedNodeName) {
		this.addedNodeName = addedNodeName;
	}
	public String getAddedNodeID() {
		return addedNodeID;
	}
	public void setAddedNodeID(String addedNodeID) {
		this.addedNodeID = addedNodeID;
	}
	public boolean isDisableType4Root() {
		return disableType4Root;
	}

	public void setDisableType4Root(boolean disableType4Root) {
		this.disableType4Root = disableType4Root;
	}

	public String getLabel4Rollup() {
		return label4Rollup;
	}

	public void setLabel4Rollup(String label4Rollup) {
		this.label4Rollup = label4Rollup;
	}
	public String getPopuptop() {
		return popuptop;
	}

	public void setPopuptop(String popuptop) {
		this.popuptop = popuptop;
	}

	public String getPopupleft() {
		return popupleft;
	}

	public void setPopupleft(String popupleft) {
		this.popupleft = popupleft;
	}




	public String getSelectType4Ins() {
		return selectType4Ins;
	}

	public void setSelectType4Ins(String selectType4Ins) {
		this.selectType4Ins = selectType4Ins;
	}
	
	private String downloadURL = ""; 

	public ArrayList getSelectType4InsAL() {

		selectType4InsAL=new ArrayList<>();

		selectType4InsAL.add("---Select Type---");
		selectType4InsAL.add("Rollup");
		selectType4InsAL.add("Data");
		selectType4InsAL.add("Hierarchy");

		return selectType4InsAL;
	}

	public void setSelectType4InsAL(ArrayList selectType4InsAL) {
		this.selectType4InsAL = selectType4InsAL;
	}



	public String getMsg1() {
		return msg1;
	}

	public void setMsg1(String msg1) {
		this.msg1 = msg1;
	}

	public ArrayList getStatusValueAL() {

		statusValueAL = new ArrayList<>();
		//		statusValueAL.add("---Select Status---");
		statusValueAL.add("Draft");
		statusValueAL.add("Approved");
		statusValueAL.add("Published");
		statusValueAL.add("Disabled");


		return statusValueAL;
	}

	public void setStatusValueAL(ArrayList statusValueAL) {
		this.statusValueAL = statusValueAL;
	}



	private boolean editHierarchy=true;   // code change Menaka 14FEB2014


	public boolean isEditHierarchy() {
		return editHierarchy;
	}

	public void setEditHierarchy(boolean editHierarchy) {
		this.editHierarchy = editHierarchy;
	}



	public String getSegmentID() {
		return segmentID;
	}

	public void setSegmentID(String segmentID) {
		this.segmentID = segmentID;
	}

	public String getInfoMessage() {
		return infoMessage;
	}
	public String getDocumentNameWF() {
		return documentNameWF;
	}
	public void setDocumentNameWF(String documentNameWF) {
		this.documentNameWF = documentNameWF;
	}
	public String getDocType() {
		return docType;
	}
	public void setDocType(String docType) {
		this.docType = docType;
	}
	public String getDoccreatedBy() {
		return doccreatedBy;
	}
	public void setDoccreatedBy(String doccreatedBy) {
		this.doccreatedBy = doccreatedBy;
	}
	public String getDoccreatedDate() {
		return doccreatedDate;
	}
	public void setDoccreatedDate(String doccreatedDate) {
		this.doccreatedDate = doccreatedDate;
	}
	public void setInfoMessage(String infoMessage) {
		this.infoMessage = infoMessage;
	}

	public String getStatusColor() {
		return statusColor;
	}

	public void setStatusColor(String statusColor) {
		this.statusColor = statusColor;
	}

	public String getStatus() {
		//		System.out.println("status=======in gerret====>>"+status);
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getHryCategory() {
		return hryCategory;
	}

	public void setHryCategory(String hryCategory) {
		this.hryCategory = hryCategory;
	}

	private boolean disableSibling=true;  // code change Menaka 12FEB2014


	public boolean isDisableSibling() {
		return disableSibling;
	}

	public void setDisableSibling(boolean disableSibling) {
		this.disableSibling = disableSibling;
	}

	public String getSegmentNumber() {
		return segmentNumber;
	}

	public void setSegmentNumber(String segmentNumber) {
		this.segmentNumber = segmentNumber;
	}

	private String msg="";  // code change Menaka 07FEB2014


	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	private String selectedType="Child";
	//end code change Jayaramu
	public String getSelectedType() {
		return selectedType;
	}

	public void setSelectedType(String selectedType) {
		this.selectedType = selectedType;
	}

	public String getReloadFlag() {
		return reloadFlag;
	}

	public void setReloadFlag(String reloadFlag) {
		this.reloadFlag = reloadFlag;
	}

	public String getEditRollupFlag() {
		return editRollupFlag;
	}

	public void setEditRollupFlag(String editRollupFlag) {
		this.editRollupFlag = editRollupFlag;
	}

	public String getEditDataFlag() {
		return editDataFlag;
	}

	public void setEditDataFlag(String editDataFlag) {
		this.editDataFlag = editDataFlag;
	}

	public String getEditHiryFlag() {
		return editHiryFlag;
	}

	public void setEditHiryFlag(String editHiryFlag) {
		this.editHiryFlag = editHiryFlag;
	}

	public boolean valid=true;  // Code change Menaka 11MAR2014


	// Code change Menaka 19MAR2014
	public String userLoginId4Edit="";
	public String eMailID4Edit="";
	public Date accessStartDate4Edit;
	public Date accessEndDate4Edit;
	public String accessStatus4Edit="";

	public String userLoginNameId4Edit="";
	public String userLoginpassId4Edit="";

	public String userLoginfirstNameId4Edit="";
	public String userLoginlastNameId4Edit="";
	public String userLogintitleId4Edit="";
	public String userLoginph1Id4Edit="";
	public String userLoginph2Id4Edit="";
	public String userLoginph3Id4Edit="";
	public String userLoginMail1Id4Edit="";
	public String userLoginMail2Id4Edit="";
	public String userLoginaddresId4Edit="";
	public String userLogincityId4Edit="";
	public String userLoginstateId4Edit="";
	public String userLogincountryId4Edit="";
	public String userLoginzipId4Edit="";



	public String getUserLoginNameId4Edit() {
		return userLoginNameId4Edit;
	}
	public void setUserLoginNameId4Edit(String userLoginNameId4Edit) {
		this.userLoginNameId4Edit = userLoginNameId4Edit;
	}
	public String getUserLoginId4Edit() {
		return userLoginId4Edit;
	}
	public void setUserLoginId4Edit(String userLoginId4Edit) {
		this.userLoginId4Edit = userLoginId4Edit;
	}
	public String geteMailID4Edit() {
		return eMailID4Edit;
	}
	public void seteMailID4Edit(String eMailID4Edit) {
		this.eMailID4Edit = eMailID4Edit;
	}
	public Date getAccessStartDate4Edit() {
		return accessStartDate4Edit;
	}
	public void setAccessStartDate4Edit(Date accessStartDate4Edit) {
		this.accessStartDate4Edit = accessStartDate4Edit;
	}
	public Date getAccessEndDate4Edit() {
		return accessEndDate4Edit;
	}
	public void setAccessEndDate4Edit(Date accessEndDate4Edit) {
		this.accessEndDate4Edit = accessEndDate4Edit;
	}
	public String getAccessStatus4Edit() {
		return accessStatus4Edit;
	}
	public void setAccessStatus4Edit(String accessStatus4Edit) {
		this.accessStatus4Edit = accessStatus4Edit;
	}
	public boolean isValid() {
		return valid;
	}
	public void setValid(boolean valid) {
		this.valid = valid;
	}

	private Map<String, String> hierarchyNameList=new LinkedHashMap<String, String>();;
	private String selectedHierarchy="";
	public String getSelectedHierarchy() {
		return selectedHierarchy;
	}

	public void setSelectedHierarchy(String selectedHierarchy) {
		this.selectedHierarchy = selectedHierarchy;
	}
	//start code change Jayaramu
	public Map<String, String> getHierarchyNameList() throws Exception {
		System.out.println("Entering: "
				+ new Exception().getStackTrace()[0].getClassName() + "."
				+ new Exception().getStackTrace()[0].getMethodName());
		try {

			SessionExpireBean sesExpire=new SessionExpireBean();  // code change pandian 26Aug2013
			String isSameSession=sesExpire.isSameSession();
			if(isSameSession.equalsIgnoreCase("redirect")){

				return new LinkedHashMap();
			}else{

				PropUtil prop = new PropUtil();
				HIERARCHY_XML_DIR = prop.getProperty("HIERARCHY_XML_DIR");
				Hashtable<String, String> hierarchyAttr = new Hashtable<String, String>();
				FileInputStream fis = new FileInputStream(HIERARCHY_XML_DIR + hierarchyXmlFileName);

				DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory
						.newInstance();
				DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
				Document doc = docBuilder.parse((fis));
				doc.getDocumentElement().normalize();
				hierarchyNameList=new LinkedHashMap<String, String>();;
				NodeList pan = doc.getElementsByTagName("Hierarchy_Levels");
				Node n=null;
				if(pan != null){

					for(int pnode=0;pnode<pan.getLength();pnode++)
					{
						n = pan.item(pnode);

						if(n != null){
							NodeList nl1 = n.getChildNodes();
							Node nn = null;



							for(int i=0;i<nl1.getLength();i++){

								nn= nl1.item(i);


								if (nn.getNodeType() == Node.ELEMENT_NODE) {
									Element ele = (Element)nn;
									String Hierarchy_Name = ele.getAttribute("Hierarchy_Name");
									//		     if(!Hierarchy_Name.equals(this.hierarchyName)){
									//			     hierarchyNameList.put(Hierarchy_Name,Hierarchy_Name);
									//			     }	
									//		       }

									// code change Menaka 05APR2014
									String hierarchyID=ele.getAttribute("Hierarchy_ID");
									Node workFlowN = Globals.getNodeByAttrVal(doc, "Workflow", "Hierarchy_ID", hierarchyID);

									if(!Globals.getAttrVal4AttrName(nn, "RI_Hierarchy_Type").equals("Reference")){  // code change Menaka 25APR2014
										if(workFlowN!=null){
											Hashtable workFlowHT=Globals.getAttributeNameandValHT(workFlowN);

											String iscompleted=(String)workFlowHT.get("Current_Stage_No");
											if(iscompleted.equalsIgnoreCase("Completed") || iscompleted.equals("Cancel") || iscompleted.equals("Rules Failed")){

												if(!Hierarchy_Name.equals(this.hierarchyName)){
													hierarchyNameList.put(Hierarchy_Name,Hierarchy_Name);
												}	
											}
										}
									}

								}

							}}}}
			}		  
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("Exiting: "
				+ new Exception().getStackTrace()[0].getClassName() + "."
				+ new Exception().getStackTrace()[0].getMethodName());

		System.out.println("hierarchyNameList---->>"+hierarchyNameList);

		return hierarchyNameList;
	}

	//	public void updateStatus(){  // code change Menaka 15FEB2014
	//		
	//		System.out.println("status---->>>"+status);
	//		System.out.println("hierarchy_ID---->>>"+hierarchy_ID);
	//		System.out.println("HIERARCHY_XML_DIR---->>>"+HIERARCHY_XML_DIR);
	//		
	//		if(status==null||status.equals("---Select Status---")||status.isEmpty()){  // code change Menaka 19FEB2014
	//			
	//			this.msg1="Select the hierarchy status";
	//			System.out.println(msg1);
	//			return;
	//		}
	//		
	//		this.msg1="";
	//		
	//		Document doc=Globals.openXMLFile(HIERARCHY_XML_DIR, hierarchyXmlFileName);	
	//
	//		Node nd=Globals.getNodeByAttrVal(doc, "Hierarchy_Level", "Hierarchy_ID", hierarchy_ID);
	//	if(nd!=null)	{
	//		if(nd.getNodeType() == Node.ELEMENT_NODE){
	//			Element el = (Element)nd;
	//			el.setAttribute("Hierarchy_Status",status);
	//			Globals.writeXMLFile(doc, HIERARCHY_XML_DIR, hierarchyXmlFileName);
	//		}
	//	}
	//	}



	public void delete(){   // code change Menaka 07FEB2014

		System.out.println("Entering: "
				+ new Exception().getStackTrace()[0].getClassName() + "."
				+ new Exception().getStackTrace()[0].getMethodName());
		try {


			msg = "";
			if(hierarchyList.size()<=0){
				this.msg="Please select atleast one hierarchy to proceed further.";
				return;
			}
			String xmlDir = "";
			String hierLevelFileName = "";
			String laXMLFileName = "";
			PropUtil prop = new PropUtil();
			xmlDir = prop.getProperty("HIERARCHY_XML_DIR");
			hierLevelFileName  = prop.getProperty("HIERARCHY_XML_FILE");
			laXMLFileName = prop.getProperty("LOGIN_XML_FILE");
			Document doc = Globals.openXMLFile(xmlDir, hierLevelFileName);
			Document laDoc = Globals.openXMLFile(xmlDir, laXMLFileName);

			String hierarchyID="";   // code change Menaka 10FEB2014
			Connection conn = Globals.getDBConnection("DW_Connection");
			PreparedStatement pre=null;  // code change Menaka 25FEB2014
			for(int i=0;i<hierarchyList.size();i++){
				hierarchyID=String.valueOf(hierarchyList.get(i).getHierarchyID());
				//			System.out.println("hierarchyID---->"+hierarchyID);
				Node deleteNd = Globals.getNodeByAttrVal(doc, "Hierarchy_Level", "Hierarchy_ID", hierarchyID);
				if(Globals.getAttrVal4AttrName(deleteNd, "RI_Hierarchy_Type").equals("Reference")){
					if(Globals.getAttrVal4AttrName(deleteNd, "RI_Dependent_Hierarchies").equals("")){

					}else{
						msg = "This hierarchy is reference to other hierarchies. This cannot be deleted.";
						System.out.println("msg===>"+msg);
						continue;
					}

				}
				updatingRefDependentHier4delete(hierarchyID);
				deleteHierIDinLaXml(hierarchyID);
				Globals.removeNodeFromXML("Hierarchy_Level", "Hierarchy_ID",hierarchyID,hierLevelFileName);
				hierarchyAL.remove(i);
				if(conn!=null){
					String SQL="DELETE FROM "+"WC_FLEX_HIERARCHY_D"+" WHERE "+"HIER_CODE='"+hierarchyID+"'";
					System.out.println("SQL---->"+SQL);
					pre=conn.prepareStatement(SQL);
					pre.execute();	
					String deleteWFActivity="DELETE FROM "+"WORK_FLOW_ACTIVITY"+" WHERE "+"HIERARCHY_ID='"+hierarchyID+"'";
					System.out.println("deleteWFActivity---->"+deleteWFActivity);
					pre=conn.prepareStatement(deleteWFActivity);
					pre.execute();
				}
				System.out.println("Deleted successfully");
			}
			// code change Menaka 10FEB2014

			if(pre!=null)
				pre.close();


		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("Exiting: "
				+ new Exception().getStackTrace()[0].getClassName() + "."
				+ new Exception().getStackTrace()[0].getMethodName());

	}

	public void updatingRefDependentHier4delete(String id) {
		try{
			String xmlDir = "";
			String hierLevelFileName = "";
			PropUtil prop = new PropUtil();
			xmlDir = prop.getProperty("HIERARCHY_XML_DIR");
			hierLevelFileName  = prop.getProperty("HIERARCHY_XML_FILE");
			Document doc = Globals.openXMLFile(xmlDir, hierLevelFileName);
			Node deleteNd = Globals.getNodeByAttrVal(doc, "Hierarchy_Level", "Hierarchy_ID", id);
			if(Globals.getAttrVal4AttrName(deleteNd, "RI_Hierarchy_Type").equals("Dependent")){
				String referenceHierId = Globals.getAttrVal4AttrName(deleteNd, "RI_Reference_Hierarchy_ID");
				Node nd = Globals.getNodeByAttrVal(doc, "Hierarchy_Level", "Hierarchy_ID", referenceHierId);
				System.out.println("===>nd"+nd);
				if(nd==null){
					return;
				}
				Element ele = (Element) nd;
				String temp = "";
				if(Globals.getAttrVal4AttrName(nd, "RI_Dependent_Hierarchies").contains(id+",")){
					temp = Globals.getAttrVal4AttrName(nd, "RI_Dependent_Hierarchies").replace(id+",", "");
					System.out.println("===>temp"+temp);
					//					if(Globals.getAttrVal4AttrName(nd, "RI_Dependent_Hierarchies").contains(id)){
					//						temp = Globals.getAttrVal4AttrName(nd, "RI_Dependent_Hierarchies").replace(","+id, "");
					//					}
				}else if(Globals.getAttrVal4AttrName(nd, "RI_Dependent_Hierarchies").contains(","+id)){
					temp = Globals.getAttrVal4AttrName(nd, "RI_Dependent_Hierarchies").replace(","+id, "");
					System.out.println("temp===>"+temp);
				}else{
					temp = Globals.getAttrVal4AttrName(nd, "RI_Dependent_Hierarchies").replace(id, "");
				}

				ele.setAttribute("RI_Dependent_Hierarchies", temp);
			}else if(Globals.getAttrVal4AttrName(deleteNd, "RI_Hierarchy_Type").equals("Reporting")){
				String referenceHierId = Globals.getAttrVal4AttrName(deleteNd, "RI_Reference_Hierarchy_ID");
				Node nd = Globals.getNodeByAttrVal(doc, "Hierarchy_Level", "Hierarchy_ID", referenceHierId);
				if(nd==null){
					return;
				}
				Element ele = (Element) nd;
				String temp = "";
				if(Globals.getAttrVal4AttrName(nd, "RI_Dependent_Hierarchies").contains(id+",")){
					temp = Globals.getAttrVal4AttrName(nd, "RI_Dependent_Hierarchies").replace(id+",", "");
					//					if(Globals.getAttrVal4AttrName(nd, "RI_Dependent_Hierarchies").contains(id)){
					//						temp = Globals.getAttrVal4AttrName(nd, "RI_Dependent_Hierarchies").replace(","+id, "");
					//					}
				}else if(Globals.getAttrVal4AttrName(nd, "RI_Dependent_Hierarchies").contains(","+id)){
					temp = Globals.getAttrVal4AttrName(nd, "RI_Dependent_Hierarchies").replace(","+id, "");
					System.out.println("temp===>"+temp);
				}else{
					temp = Globals.getAttrVal4AttrName(nd, "RI_Dependent_Hierarchies").replace(id, "");
				}
				ele.setAttribute("RI_Dependent_Hierarchies", temp);
			}else{

			}
			Globals.writeXMLFile(doc, xmlDir, hierLevelFileName);
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public void deleteHierIDinLaXml(String id) {
		try{
			String xmlDir = "";
			String laXMLFileName = "";
			PropUtil prop = new PropUtil();
			xmlDir = prop.getProperty("HIERARCHY_XML_DIR");
			laXMLFileName = prop.getProperty("LOGIN_XML_FILE");
			Document laDoc = Globals.openXMLFile(xmlDir, laXMLFileName);
			NodeList obieeNdList = laDoc.getElementsByTagName("Obiee_Users");
			Node obieeNd = obieeNdList.item(0);
			NodeList userNdList = obieeNd.getChildNodes();
			for(int i=0;i<userNdList.getLength();i++){
				if(userNdList.item(i).getNodeType() == Node.ELEMENT_NODE){
					if(Globals.getAttrVal4AttrName(userNdList.item(i), "Allowed_Hierarchies").contains(id)){
						Element ele = (Element)userNdList.item(i); 
						String temp = "";
						if(Globals.getAttrVal4AttrName(userNdList.item(i), "Allowed_Hierarchies").contains(id+",")){
							temp = Globals.getAttrVal4AttrName(userNdList.item(i), "Allowed_Hierarchies").replace(id+",", "");

						}else if(Globals.getAttrVal4AttrName(userNdList.item(i), "Allowed_Hierarchies").contains(","+id)){
							temp = Globals.getAttrVal4AttrName(userNdList.item(i), "Allowed_Hierarchies").replace(","+id, "");
						}else{
							temp = Globals.getAttrVal4AttrName(userNdList.item(i), "Allowed_Hierarchies").replace(id, "");
						}

						ele.setAttribute("Allowed_Hierarchies", temp);
					}

				}
			}
			Globals.writeXMLFile(laDoc, xmlDir, laXMLFileName);
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public void setHierarchyNameList(Map<String, String> hierarchyNameList) {
		this.hierarchyNameList = hierarchyNameList;
	}

	public String getParentNodeName() {
		return parentNodeName;
	}

	public void setParentNodeName(String parentNodeName) {
		this.parentNodeName = parentNodeName;
	}

	public String getFlagForAdd() {
		return flagForAdd;
	}

	public void setFlagForAdd(String flagForAdd) {
		this.flagForAdd = flagForAdd;
	}



	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	private  String statusMesg = "Success";
	private String hirename="";
	private String refreshId="";
	public List<ReportTree> selectedRows = new ArrayList<ReportTree>();
	private String selectedRowsstr = "";
	private List<ReportTree> hierTreeSelectedRows = new ArrayList<ReportTree>();
	public List<ReportTree> getHierTreeSelectedRows() {
		return hierTreeSelectedRows;
	}
	public void setHierTreeSelectedRows(List<ReportTree> hierTreeSelectedRows) {
		this.hierTreeSelectedRows = hierTreeSelectedRows;
	}
	public String getSelectedRowsstr() {
		return selectedRowsstr;
	}

	public void setSelectedRowsstr(String selectedRowsstr) {
		this.selectedRowsstr = selectedRowsstr;
	}

	private String flagNew="";
	private String code="";
	private String renderedID="";
	//start code change Jayaramu 008FEB14
	private String renderValue4SessionInfo="";
	private String hierarchyCreatedDate="";
	private String renderValue4SessionInfo1="";
	private String hierarchyCategory="";
	private String hrchyCategory="";
	private String subHrchyCategory="";  // code change Menaka 05APR2014
	private String flag4openDataPopup = "Data";
	public String getFlag4openDataPopup() {
		return flag4openDataPopup;
	}
	public void setFlag4openDataPopup(String flag4openDataPopup) {
		this.flag4openDataPopup = flag4openDataPopup;
	}



	public String getSubHrchyCategory() {
		return subHrchyCategory;
	}
	public void setSubHrchyCategory(String subHrchyCategory) {
		this.subHrchyCategory = subHrchyCategory;
	}


	private String primarySegment = "";	
	private String eMailID = "";
	private String accessStatus = "";
	private Date accessStartDate;
	public Date getAccessStartDate() {
		return accessStartDate;
	}
	public void setAccessStartDate(Date accessStartDate) {
		this.accessStartDate = accessStartDate;
	}

	private String userLegendName = "";
	private String flag4AddUpdUser = "";
	private String flag4AddUpdUser1 = "";
	public String getFlag4AddUpdUser1() {
		return flag4AddUpdUser1;
	}
	public void setFlag4AddUpdUser1(String flag4AddUpdUser1) {
		this.flag4AddUpdUser1 = flag4AddUpdUser1;
	}
	public String getFlag4AddUpdUser() {
		return flag4AddUpdUser;
	}
	public void setFlag4AddUpdUser(String flag4AddUpdUser) {
		this.flag4AddUpdUser = flag4AddUpdUser;
	}
	public String getUserLegendName() {
		return userLegendName;
	}
	public void setUserLegendName(String userLegendName) {
		this.userLegendName = userLegendName;
	}

	private Date accessEndDate;

	public Date getAccessEndDate() {
		return accessEndDate;
	}
	public void setAccessEndDate(Date accessEndDate) {
		this.accessEndDate = accessEndDate;
	}
	public String getAccessStatus() {
		return accessStatus;
	}
	public void setAccessStatus(String accessStatus) {
		this.accessStatus = accessStatus;
	}
	public String geteMailID() {
		return eMailID;
	}
	public void seteMailID(String eMailID) {
		this.eMailID = eMailID;
	}

	private String userLoginId = "";

	public String getUserLoginId() {
		return userLoginId;
	}
	public void setUserLoginId(String userLoginId) {
		this.userLoginId = userLoginId;
	}
	public String getPrimarySegment() {
		return primarySegment;
	}
	public void setPrimarySegment(String primarySegment) {
		this.primarySegment = primarySegment;
	}
	public String getHrchyCategory() {
		return hrchyCategory;
	}

	public void setHrchyCategory(String hrchyCategory) {
		this.hrchyCategory = hrchyCategory;
	}

	public String getHierarchyCategory() {
		return hierarchyCategory;
	}


	public void setHierarchyCategory(String hierarchyCategory) {
		this.hierarchyCategory = hierarchyCategory;
	}
	public String getRenderValue4SessionInfo1() {
		return renderValue4SessionInfo1;
	}

	public void setRenderValue4SessionInfo1(String renderValue4SessionInfo1) {
		this.renderValue4SessionInfo1 = renderValue4SessionInfo1;
	}

	public String getHierarchyCreatedDate() {
		return hierarchyCreatedDate;
	}

	public void setHierarchyCreatedDate(String hierarchyCreatedDate) {
		this.hierarchyCreatedDate = hierarchyCreatedDate;
	}

	public String getHierarchyModifiedDate() {
		return hierarchyModifiedDate;
	}

	public void setHierarchyModifiedDate(String hierarchyModifiedDate) {
		this.hierarchyModifiedDate = hierarchyModifiedDate;
	}

	private String hierarchyModifiedDate="";
	private String hierarchyName="";
	private String subHierarchyName="";   // code change Menaka 05APR2014

	public String getSubHierarchyName() {
		return subHierarchyName;
	}
	public void setSubHierarchyName(String subHierarchyName) {
		this.subHierarchyName = subHierarchyName;
	}
	public String getHierarchyName() {
		return hierarchyName;
	}

	public void setHierarchyName(String hierarchyName) {
		this.hierarchyName = hierarchyName;
	}

	public String getRenderValue4SessionInfo() {
		return renderValue4SessionInfo;
	}

	public void setRenderValue4SessionInfo(String renderValue4SessionInfo) {
		this.renderValue4SessionInfo = renderValue4SessionInfo;
	}
	//End code change Jayaramu 008FEB14
	public String getRenderedID() {
		return renderedID;
	}

	public void setRenderedID(String renderedID) {
		this.renderedID = renderedID;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getFlagNew() {
		return flagNew;
	}

	public void setFlagNew(String flagNew) {
		this.flagNew = flagNew;
	}

	public List<ReportTree> getSelectedRows() {

		for (int i = 0; i < selectedRows.size(); i++)
			System.out.println("selectedReports: "
					+ selectedRows.get(i).getId());

		return selectedRows;
	}

	public void setSelectedRows(List<ReportTree> selectedRows) {
		this.selectedRows = selectedRows;
	}



	public String getRefreshId() {
		return refreshId;
	}

	public void setRefreshId(String refreshId) {
		this.refreshId = refreshId;
	}

	public String getHirename() {
		return hirename;
	}

	public void setHirename(String hirename) {
		this.hirename = hirename;
	}





	public String getStatusMesg() {
		return statusMesg;
	}

	public void setStatusMesg(String statusMesg) {
		this.statusMesg = statusMesg;
	}

	public String getHierarchyID4Tree() {
		return hierarchyID4Tree;
	}

	public void setHierarchyID4Tree(String hierarchyID4Tree) {
		this.hierarchyID4Tree = hierarchyID4Tree;
	}

	private List<TreeNode> rootNodes = new ArrayList<TreeNode>();
	//	private List<HierarchyTree> hierarchyTree = new ArrayList<HierarchyTree>();

	public ArrayList getHierarchyList() {
		return hierarchyList;
	}

	public void setHierarchyList(ArrayList hierarchyList) {
		this.hierarchyList = hierarchyList;
	}

	private String hierarchyXmlFileName="HierachyLevel.xml";
	private String hierarchyConfigFile="Hierarchy_Config.xml";





	public String getHierarchyConfigFile() {
		return hierarchyConfigFile;
	}

	public void setHierarchyConfigFile(String hierarchyConfigFile) {
		this.hierarchyConfigFile = hierarchyConfigFile;
	}

	public String getHierarchyXmlFileName() {
		return hierarchyXmlFileName;
	}

	public void setHierarchyXmlFileName(String hierarchyXmlFileName) {
		this.hierarchyXmlFileName = hierarchyXmlFileName;
	}

	String HIERARCHY_XML_DIR = "";


	//start code change Jayaramu for show all Hierarchy details in Hierarchy table
	public ArrayList getHierarchyAL() throws Exception {

		System.out.println("Entering: "
				+ new Exception().getStackTrace()[0].getClassName() + "."
				+ new Exception().getStackTrace()[0].getMethodName());

		try{

			hierarchyList= new ArrayList<>();  // code change Menaka 02MAY2014

			SessionExpireBean sesExpire=new SessionExpireBean();  // code change pandian 26Aug2013
			String isSameSession=sesExpire.isSameSession();

			if(isSameSession.equalsIgnoreCase("redirect")){

				return new ArrayList<>();
			}else{

				PropUtil prop = new PropUtil();
				HIERARCHY_XML_DIR = prop.getProperty("HIERARCHY_XML_DIR");
				Hashtable<String, String> hierarchyAttr = new Hashtable<String, String>();
				FileInputStream fis = new FileInputStream(HIERARCHY_XML_DIR	+ hierarchyXmlFileName);

				DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory
						.newInstance();
				DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
				Document doc = docBuilder.parse((fis));
				doc.getDocumentElement().normalize();
				ArrayList HierarchyAttrAL=new ArrayList();
				NodeList HRYNodesAL = Globals.getNodeList(HIERARCHY_XML_DIR, hierarchyXmlFileName, "Hierarchy_Level");
				String hierarchyID="";
				int hierarchyIDInt = 1;
				String hierarchyName="";
				String hierarchyCreatedDate="";
				String hierarchyModifiedDate="";
				String hierchyCategory="";
				String hierarchyStatus="";  // code change Menaka 15FEB2014
				String hierCodeCombination;
				String factStatus="";
				String errorMsg=""; // code change Menaka 21FEB2014
				String dimStatus = "";
				String dimErrorMsg = "";
				String createdBy = "";
				String modifiedBy = "";
				String noOfStages = "";
				//		String workFlowStage = "";
				//		String workFlowStageStatus = "";



				//start code change Menaka 21MAR2014

				String loginXMLfile=prop.getProperty("LOGIN_XML_FILE");
				Document doc1=Globals.openXMLFile(HIERARCHY_XML_DIR, loginXMLfile);


				NodeList ndlist=Globals.getNodeList(HIERARCHY_XML_DIR, loginXMLfile, "User");

				FacesContext ctx1 = FacesContext.getCurrentInstance();
				ExternalContext extContext1 = ctx1.getExternalContext();
				Map sessionMap1 = extContext1.getSessionMap();
				LoginBean lgnbn = (LoginBean) sessionMap1.get("loginBean");

				String hierIDS = "";

				NodeList nodlist=Globals.getNodeList(HIERARCHY_XML_DIR, loginXMLfile, "User");

				String defaultUserID=nodlist.item(0).getTextContent();

				System.out.println("defaultUserID=====>>"+defaultUserID);

				Node nod=Globals.getNodeByAttrVal(doc1, "User", "Login_ID", lgnbn.username);


				Node attr=null;
				if(nod!=null){  // code change Menaka 24MAR2014
					if(nod.getNodeType()==Node.ELEMENT_NODE){

						for(int m=0;m<nod.getAttributes().getLength();m++){
							attr=nod.getAttributes().item(m);
							if(attr.getNodeName().equals("Allowed_Hierarchies")){
								hierIDS=attr.getNodeValue();
							}
						}

					}
				}


				String hierID4users[]=hierIDS.split(","); 

				ArrayList hierIDList=new ArrayList<>();

				for(int ii=0;ii<hierID4users.length;ii++){
					hierIDList.add(hierID4users[ii]);
				}

				System.out.println("hierIDList==========>>>"+hierIDList);
				System.out.println("lgnbn.username==========>>>"+lgnbn.username);
				for (int hry = 0; hry < HRYNodesAL.getLength(); hry++) {
					Node node = HRYNodesAL.item(hry);

					Hashtable<String, String> HRYAttrHt = Globals.getAttributeNameandValHT(node);


					hierarchyID = HRYAttrHt.get("Hierarchy_ID");
					System.out.println((hierIDList.contains(hierarchyID))+"hierarchyID==========>>>"+hierarchyID);
//					if(!lgnbn.username.equals(defaultUserID)){
//						if(hierIDList.contains(hierarchyID)){  // code change Menaka 21MAR2014
//
//						}else{
//							continue;
//						}
//					}
					System.out.println(String.valueOf(HRYAttrHt.get("CustomerKey"))+"::lgnbn.getCustomerKey()==========>>>"+lgnbn.getCustomerKey());
					if(String.valueOf(HRYAttrHt.get("CustomerKey")) == null || String.valueOf(HRYAttrHt.get("CustomerKey")).equals("null") || String.valueOf(HRYAttrHt.get("CustomerKey")).isEmpty() || 
							!String.valueOf(HRYAttrHt.get("CustomerKey")).equalsIgnoreCase(lgnbn.getCustomerKey())) {
						continue;
					}


					hierarchyIDInt = Integer.parseInt(hierarchyID);
					hierarchyName = HRYAttrHt.get("Hierarchy_Name");
					hierarchyCreatedDate = HRYAttrHt.get("Created_Date");
					hierarchyModifiedDate = HRYAttrHt.get("Modified_Date");
					hierchyCategory =  HRYAttrHt.get("Hierarchy_Category");
					hierCodeCombination =  String.valueOf(HRYAttrHt.get("Code_Combination"));
					dimStatus = HRYAttrHt.get("Dim_Status");
					dimErrorMsg = HRYAttrHt.get("Dim_Status_Details");
					createdBy = HRYAttrHt.get("Created_By") == null ? "" : HRYAttrHt.get("Created_By");
					modifiedBy = HRYAttrHt.get("Modified_By") == null ? "" : HRYAttrHt.get("Modified_By");
					hierarchyStatus=HRYAttrHt.get("RI_Hierarchy_Type");   // code change Menaka 15FEB2014 // code change Menaka 31MAR2014
					Node workFlowNode = Globals.getNodeByAttrVal(doc, "Workflow", "Hierarchy_ID", hierarchyID);
					String workFlowStage = "";
					String workFlowStageStatus = "";
					if(workFlowNode != null){
						Hashtable curHT=Globals.getAttributeNameandValHT(workFlowNode);
						String currentStageNo="";
						noOfStages = (String)curHT.get("Total_No_Stages");
						String stageno=(String)curHT.get("Current_Stage_No");
						if(stageno.equalsIgnoreCase("Completed") || stageno.equalsIgnoreCase("Cancel") || stageno.equalsIgnoreCase("Rules Failed")){
							currentStageNo=(String)curHT.get("Total_No_Stages");
						}else{
							currentStageNo=(String)curHT.get("Current_Stage_No");
						}
						System.out.println("-=-=-=-=-=-=-=-currentStageNo=-=-=-=-=-="+currentStageNo);
						Node currentStageNode=Globals.getNodeByAttrValUnderParent(doc, workFlowNode, "Stage_No", currentStageNo);
						Hashtable curStageHT=Globals.getAttributeNameandValHT(currentStageNode);
						workFlowStage=(String)curStageHT.get("Stage_Name");
						String stagests=(String)curStageHT.get("Stage_Status");
						if(stagests.equalsIgnoreCase("Completed")){
							LoginProcessManager lgnpro=new LoginProcessManager();
							Hashtable stageDetHT=lgnpro.getStageDetails(hierarchyID, currentStageNo,"");
							Hashtable msgHT=(Hashtable)stageDetHT.get("MessagedetHT");
							String finalmsg=(String)msgHT.get("Final");
							workFlowStageStatus=finalmsg;		
						}else{
							workFlowStageStatus=(String)curStageHT.get("Stage_Status");
						}



					}
					System.out.println("hierarchyID---->>>"+hierarchyID);


					Node rootnode = Globals.getNodeByAttrVal(doc, "Fact_Tables", "ID", hierarchyID);
					if(rootnode != null){

						if(rootnode.getNodeType() == Node.ELEMENT_NODE){
							NodeList child = rootnode.getChildNodes();
							for(int i=0;i<child.getLength();i++){

								if(child.item(i).getNodeName().equals("Fact_Result")){

									factStatus=child.item(i).getTextContent();
									if(factStatus==null||factStatus.equals("")||factStatus.isEmpty()){
										factStatus="Not generated";  // code change Menaka 20FEB2014
									}
								}
								if(child.item(i).getNodeName().equals("Error_Message")){ // code change Menaka 21FEB2014
									errorMsg=child.item(i).getTextContent();
									if(errorMsg==null||errorMsg.equals("")||errorMsg.isEmpty()){
										errorMsg="Not generated"; 
									}
								}


							}
						}
					}else{
						factStatus="Not generated";   // code change Menaka 21FEB2014
						errorMsg="Not generated";
					}

					System.out.println("factStatus---->>>"+factStatus);
					if(HRYAttrHt.get("Version")==null){		//code change Vishnu 20Mar2014
						HierarchydataBean hryData4Table = new HierarchydataBean(hierarchyIDInt,hierarchyName,hierarchyCreatedDate,hierarchyModifiedDate,hierchyCategory,hierCodeCombination,hierarchyStatus,factStatus,errorMsg,dimStatus,dimErrorMsg,workFlowStage,workFlowStageStatus, createdBy, modifiedBy, noOfStages);
						HierarchyAttrAL.add(hryData4Table);
					}else{
						if(HRYAttrHt.get("Version").equalsIgnoreCase("Master")){
							HierarchydataBean hryData4Table = new HierarchydataBean(hierarchyIDInt,hierarchyName,hierarchyCreatedDate,hierarchyModifiedDate,hierchyCategory,hierCodeCombination,hierarchyStatus,factStatus,errorMsg,dimStatus,dimErrorMsg,workFlowStage,workFlowStageStatus, createdBy, modifiedBy, noOfStages);
							HierarchyAttrAL.add(hryData4Table);
						}else{

						}
					}

				}
				hierarchyAL = HierarchyAttrAL;

			}
		}catch(Exception e){
			e.printStackTrace();
		}


		System.out.println("Exiting: "
				+ new Exception().getStackTrace()[0].getClassName() + "."
				+ new Exception().getStackTrace()[0].getMethodName());

		return hierarchyAL;
	}
	//End code change Jayaramu


	private boolean isCmngFrmInsertUPNDWN=false;

	public boolean isCmngFrmInsertUPNDWN() {
		return isCmngFrmInsertUPNDWN;
	}

	public void setCmngFrmInsertUPNDWN(boolean isCmngFrmInsertUPNDWN) {
		this.isCmngFrmInsertUPNDWN = isCmngFrmInsertUPNDWN;
	}
	boolean isReGenerated;


	public boolean isReGenerated() {
		return isReGenerated;
	}
	public void setReGenerated(boolean isReGenerated) {
		this.isReGenerated = isReGenerated;
	}
	String codeCombinationflag4Data = "";
	public String getCodeCombinationflag4Data() {
		return codeCombinationflag4Data;
	}
	public void setCodeCombinationflag4Data(String codeCombinationflag4Data) {
		this.codeCombinationflag4Data = codeCombinationflag4Data;
	}
	String color4HierTreeMsg = "";
	public String getColor4HierTreeMsg() {
		return color4HierTreeMsg;
	}
	public void setColor4HierTreeMsg(String color4HierTreeMsg) {
		this.color4HierTreeMsg = color4HierTreeMsg;
	}
	String nodeID="";
	//start code change Jayaramu for add Node In xml
	public void addHierarchyNodes(String flagValue, boolean codeFlag) throws Exception{
		System.out.println("Entering: "
				+ new Exception().getStackTrace()[0].getClassName() + "."
				+ new Exception().getStackTrace()[0].getMethodName());
		Document doc = null;
		Hashtable dimStatusHT = new Hashtable<>();
		try{

			msg1="";  // code change Menaka 29MAR2014
			msg="";
			if(name==null||name.equals("")){
				msg1="Enter the node name to proceed further.";
				color4HierTreeMsg = "red";
				return;
			}

			System.out.println("selectedType"+selectedType);
			PropUtil prop = new PropUtil();
			HIERARCHY_XML_DIR = prop.getProperty("HIERARCHY_XML_DIR");
			Connection con=Globals.getDBConnection("DW_Connection");
			FileInputStream fis = null;
			FileOutputStream fos = null;
			DocumentBuilderFactory dbf = null;
			DocumentBuilder db = null;
			Element modifiedDate = null;
			String dateFormat = prop.getProperty("DATE_FORMAT");
			Date HIERYDate = new Date();
			DateFormat sdf = new SimpleDateFormat(dateFormat);
			//		DateFormat sdf1 = new SimpleDateFormat("yyyy/MM/dd"); 
			DateFormat sdf1 = new SimpleDateFormat(dateFormat);
			FacesContext ctx = FacesContext.getCurrentInstance();
			ExternalContext extContext = ctx.getExternalContext();
			Map sessionMap = extContext.getSessionMap();
			HierarchyDBBean hdb = (HierarchyDBBean) sessionMap.get("hierarchyDBBean");
			LoginBean lb = (LoginBean) sessionMap.get("loginBean");

			boolean fileexists = Globals.isFileExists(HIERARCHY_XML_DIR,hierarchyXmlFileName);

			if (!fileexists) {

				System.out.println("XML File does not exist. Creating New One.");

				dbf = DocumentBuilderFactory.newInstance();
				db = dbf.newDocumentBuilder();
				doc = db.newDocument();
				String root1 = "Hierarchy_Levels";
				Element rootElement1 = doc.createElement(root1);
				doc.appendChild(rootElement1);

				TransformerFactory transformerFactory = TransformerFactory
						.newInstance();
				Transformer transformer = transformerFactory.newTransformer();
				transformer.setOutputProperty(OutputKeys.INDENT, "yes");
				DOMSource source = new DOMSource(doc);
				fos = new FileOutputStream(HIERARCHY_XML_DIR
						+ hierarchyXmlFileName);
				StreamResult result = new StreamResult(fos);
				transformer.transform(source, result);
				result.getOutputStream().flush();
				result.getOutputStream().close();
				System.out.println("XML File was Created."); 

			}





			fis = new FileInputStream(HIERARCHY_XML_DIR
					+ hierarchyXmlFileName);
			Element em = null;
			DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
			doc = docBuilder.parse((fis));
			this.isReGenerated = false;

			this.renderedID = hierarchy_ID;
			if(selectedType.equals("InsertUP")){
				//start code change Jayaramu 08FEB14
				Node ModifiedDateUpdateNode = Globals.getNodeByAttrVal(doc, "Hierarchy_Level", "Hierarchy_ID", hierarchy_ID);
				if(ModifiedDateUpdateNode.getNodeType() == Node.ELEMENT_NODE){
					modifiedDate = (Element)ModifiedDateUpdateNode;
					String modifiedDateUpdate = sdf.format(HIERYDate);
					dimStatusHT.put("dimResult", "Not Generated");
					dimStatusHT.put("errorMessage", "Not Generated");
					Globals.updateDimStatus(dimStatusHT,hierarchyXmlFileName,doc);

					this.hierarchyModifiedDate = modifiedDateUpdate;
				}
				//End code change Jayaramu 08FEB14
				String levelID = selectedRows.get(0).getID();
				String nodeName = selectedRows.get(0).getLevelNode();			
				int newinsertNode_Level_ID=Globals.getMaxID(HIERARCHY_XML_DIR, hierarchyConfigFile,"Node_Level_ID", "ID");
				String uniqueid=String.valueOf(Globals.getMaxID(HIERARCHY_XML_DIR, hierarchyConfigFile,"Row_ID4Oracle", "ID"));

				dimStatusHT.put("dimResult", "Generated");
				dimStatusHT.put("errorMessage", "Generated");

				if(type.equals("Rollup")){
					insertUPNDOWNAgainsCurrentNode(nodeName,"ID",levelID,"InsertUP",
							String.valueOf(codecombinationFlag),String.valueOf(newinsertNode_Level_ID),"Rollup",uniqueid,name,HIERARCHY_XML_DIR,hierarchyXmlFileName,doc,"","","",dimStatusHT);


				}else if(type.equals("Hierarchy")){

					insertUPNDOWNAgainsCurrentNode(nodeName,"ID",levelID,"InsertUP",
							String.valueOf(codecombinationFlag),String.valueOf(newinsertNode_Level_ID),"Hierarchy",uniqueid,name,HIERARCHY_XML_DIR,hierarchyXmlFileName,doc,"","","",dimStatusHT);	


				}else if(type.equals("Data")){

					insertUPNDOWNAgainsCurrentNode(nodeName,"ID",levelID,"InsertUP",
							String.valueOf(codecombinationFlag),String.valueOf(newinsertNode_Level_ID),"Data",uniqueid,name,HIERARCHY_XML_DIR,hierarchyXmlFileName,doc,code,segmentNumber,segmentID,dimStatusHT);


				}

			}else if(selectedType.equals("InsertDOWN")){


				//start code change Jayaramu 08FEB14
				Node ModifiedDateUpdateNode = Globals.getNodeByAttrVal(doc, "Hierarchy_Level", "Hierarchy_ID", hierarchy_ID);
				if(ModifiedDateUpdateNode.getNodeType() == Node.ELEMENT_NODE){
					modifiedDate = (Element)ModifiedDateUpdateNode;
					String modifiedDateUpdate = sdf.format(HIERYDate);
					modifiedDate.setAttribute("Modified_Date", modifiedDateUpdate);
					this.hierarchyModifiedDate = modifiedDateUpdate;
				}
				dimStatusHT.put("dimResult", "Not Generated");
				dimStatusHT.put("errorMessage", "Not Generated");
				Globals.updateDimStatus(dimStatusHT,hierarchyXmlFileName,doc);

				//End code change Jayaramu 08FEB14
				String levelID = selectedRows.get(0).getID();
				String nodeName = selectedRows.get(0).getLevelNode();			
				int newinsertNode_Level_ID=Globals.getMaxID(HIERARCHY_XML_DIR, hierarchyConfigFile,"Node_Level_ID", "ID");
				String uniqueid=String.valueOf(Globals.getMaxID(HIERARCHY_XML_DIR, hierarchyConfigFile,"Row_ID4Oracle", "ID"));

				dimStatusHT.put("dimResult", "Generated");
				dimStatusHT.put("errorMessage", "Generated");

				if(type.equals("Rollup")){

					insertUPNDOWNAgainsCurrentNode(nodeName,"ID",levelID,"InsertDOWN",
							String.valueOf(codecombinationFlag),String.valueOf(newinsertNode_Level_ID),"Rollup",uniqueid,name,HIERARCHY_XML_DIR,hierarchyXmlFileName,doc,"","","",dimStatusHT);


				}else if(type.equals("Hierarchy")){

					insertUPNDOWNAgainsCurrentNode(nodeName,"ID",levelID,"InsertDOWN",
							String.valueOf(codecombinationFlag),String.valueOf(newinsertNode_Level_ID),"Hierarchy",uniqueid,name,HIERARCHY_XML_DIR,hierarchyXmlFileName,doc,"","","",dimStatusHT);


				}else if(type.equals("Data")){

					insertUPNDOWNAgainsCurrentNode(nodeName,"ID",levelID,"InsertDOWN",
							String.valueOf(codecombinationFlag),String.valueOf(newinsertNode_Level_ID),"Data",uniqueid,name,HIERARCHY_XML_DIR,hierarchyXmlFileName,doc,code,segmentNumber,segmentID,dimStatusHT);


				}

			}else{
				if(flagValue.equals("newAdded")){

					if(hirename==null||hirename.isEmpty()){  // code change Menaka 19FEB2014
						this.msg1 = "Enter the hierarchy name."; 
						color4HierTreeMsg = "red";
						return;
					}

					if(hierarchyNameList.containsValue(hirename)){

						System.out.println("Hierarchy Name already exists.. Please Enter another Name...");

						this.msg1 = "Hierarchy name already exists. Please enter another name.";
						color4HierTreeMsg = "red";
						return;

					}

					System.out.println("status---------->>>"+status);



					this.msg1 = "";
					String uniqueid=String.valueOf(Globals.getMaxID(HIERARCHY_XML_DIR, hierarchyConfigFile,"Row_ID4Oracle", "ID"));
					Node node = doc.getLastChild();  
					Element  rootEm = (Element) node;      

					Element em1 = doc.createElement("Hierarchy_Level");
					node.appendChild(em1);
					String StartDate = sdf1.format(HIERYDate);
					node.appendChild(em1);
					em1.setAttribute("Hierarchy_ID",hierarchy_ID);
					em1.setAttribute("Hierarchy_Name",hirename);
					this.hierarchyName = hirename;
					em1.setAttribute("Hierarchy_Category", hierarchyCategory);
					em1.setAttribute("Created_Date",StartDate);
					em1.setAttribute("Modified_Date",StartDate);
					em1.setAttribute("Dim_Status", "Not Generated");
					em1.setAttribute("Dim_Status_Details", "Not Generated");
					em1.setAttribute("Unique_ID",uniqueid);
					em1.setAttribute("Code_Combination", String.valueOf(codecombinationFlag));
					em1.setAttribute("RI_Hierarchy_Type", status);  // code change Menaka 15FEB2014
					//			em1.setAttribute("Hierarchy_Status", status);  // code change Menaka 15FEB2014
					em1.setAttribute("Version", "Master");

					//			em1.setAttribute("Restored_From", "Master");
					//			em1.setAttribute("Restore_Date", StartDate);
					em1.setAttribute("Created_By", lb.username);
					em1.setAttribute("Modified_By", lb.username);
					Element em2 = doc.createElement("RootLevel"); 
					em1.appendChild(em2);
					this.refreshId =hierarchy_ID;
					em2.setAttribute("ID", hierarchy_ID);
					em2.setAttribute("Type", type);
					em2.setAttribute("value", name);
					Node rootLevel = (Node)em2;
					Element em3 = doc.createElement("ID");
					rootLevel.appendChild(em3);
					em3.setTextContent(hierarchy_ID);
					Element em4 = doc.createElement("RootLevel_Name");
					rootLevel.appendChild(em4);
					em4.setTextContent(name);
					em4.setAttribute("ID", hierarchy_ID);
					em4.setAttribute("Type", type);
					em4.setAttribute("value", name);
					em4.setAttribute("Level", "1");
					Globals.writeXMLFile(doc, HIERARCHY_XML_DIR, hierarchyXmlFileName);
					//			FacesContext ctx = FacesContext.getCurrentInstance();
					//			ExternalContext extContext = ctx.getExternalContext();
					//			Map sessionMap = extContext.getSessionMap();
					//			loadData2TreeBean ftt = (loadData2TreeBean) sessionMap.get("loadData2TreeBean");
					setFlagNew("alreadyadded");
					genarateFactsBean gfb=new genarateFactsBean("","","");
					if(UPDATE_DB_FOR_EACH_NODE){
						String sortorder=String.valueOf(String.valueOf(Globals.getMaxID(HIERARCHY_XML_DIR, hierarchyConfigFile,"Sort_Order4Oracle", "ID")));
						//updating db datda
						Hashtable<Integer,String> basicValueHT=new Hashtable<>();
						basicValueHT.put(0, uniqueid);
						basicValueHT.put(1, "GL");
						basicValueHT.put(2, hierarchyCategory);
						basicValueHT.put(3, String.valueOf(hierarchy_ID));
						basicValueHT.put(4, String.valueOf(hierarchy_ID));
						basicValueHT.put(5, String.valueOf(hirename));
						basicValueHT.put(6, sortorder);
						basicValueHT.put(7, "null");
						basicValueHT.put(8, String.valueOf(hierarchy_ID));
						basicValueHT.put(9, String.valueOf(hirename));
						//			System.out.println("basicValueHT1==>"+basicValueHT);
						Hashtable valueHT=new Hashtable<>();
						Node nd=Globals.getNodeByAttrVal(doc, "RootLevel", "value", name);
						//			System.out.println("ssssssssss"+nd.getNodeName());
						int j=0;
						valueHT.put(0, name);

						//			for(int i=0;i>0;i--){
						//				
						//				nd=nd.getParentNode();
						//				
						//				System.out.println(">>>>>"+nd.getAttributes().getNamedItem("value").getNodeValue());
						//				valueHT.put(i-1, nd.getAttributes().getNamedItem("value").getNodeValue());
						//			}

						HierarchyBean he=new HierarchyBean();
						//			Thread t=new Thread(new HierarchyBean(addedName,levelNo,typeName,valueHT,basicValueHT,selectedNdID,selectednodeName,insertType,addedNodeID,previoustype4ReGenerate,con,addedNodeName,insertDoc));
						//			t.start();
						//			t.start run(){
						dimStatusHT.put("dimResult","Generated"); 
						dimStatusHT.put("errorMessage","Generated");
						Hashtable coldetHT=new Hashtable<>();
						Hashtable pdatatypeHT=new Hashtable<>();
						String pdatatype="";
						Hashtable columnNameHT=Globals.getcolumnName();

						Hashtable coldetHTtemp=new Hashtable<>();
						for(int i=0;i<columnNameHT.size();i++){
							coldetHTtemp=gfb.getDatatypefromDBtable("WC_FLEX_HIERARCHY_D."+String.valueOf(columnNameHT.get(i)),con,"","","");
							coldetHT.put(i, coldetHTtemp);
						}
						he.insertionofValues2DB(name, 0, "Rollup",valueHT,basicValueHT,hierarchy_ID,"Hierarchy_Level","childInsert",hierarchy_ID,"",con,"RootLevel",doc,dimStatusHT,coldetHT,pdatatype,0);
					}
					//			he.insertionofValues2DB(name, 0, "Rollup",valueHT,basicValueHT,hierarchy_ID,"Hierarchy_Level","childInsert",hierarchy_ID,"",con,"RootLevel",doc,dimStatusHT);

					//			}

					//		System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%MMMMMM%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");


					//start code change Menaka 21MAR2014

					String XMLfile=prop.getProperty("LOGIN_XML_FILE");
					Document doc1=Globals.openXMLFile(HIERARCHY_XML_DIR, XMLfile);


					FacesContext ctx1 = FacesContext.getCurrentInstance();
					ExternalContext extContext1 = ctx1.getExternalContext();
					Map sessionMap1 = extContext1.getSessionMap();
					LoginBean lgnbn = (LoginBean) sessionMap1.get("loginBean");

					//		System.out.println("UserName==========>>>"+lgnbn.username);

					String hierIDS = "";
					Node nod=Globals.getNodeByAttrVal(doc1, "User", "Login_ID", lgnbn.username);
					Node attr=null;
					if(nod!=null){



						if(nod.getNodeType()==nod.ELEMENT_NODE){

							for(int m=0;m<nod.getAttributes().getLength();m++){
								attr=nod.getAttributes().item(m);
								if(attr.getNodeName().equals("Allowed_Hierarchies")){
									hierIDS=attr.getNodeValue();
								}

							}
						}


						if(hierIDS.length()==0){				
							hierIDS=hierarchy_ID;				
						}else{

							String al="";
							if(hierIDS.contains(",")){
								String temp[]=hierIDS.split(",");		

								for(int i=0;i<temp.length;i++){
									if(i==0){
										al=temp[i];
									}else{
										al=al+","+temp[i];
									}						
								}

								hierIDS=al+","+hierarchy_ID;


							}else{

								hierIDS=hierIDS+","+hierarchy_ID;
							}


						}




						for(int n=0;n<nod.getAttributes().getLength();n++){
							attr=nod.getAttributes().item(n);
							if(attr.getNodeName().equals("Allowed_Hierarchies")){
								attr.setNodeValue(hierIDS);
							}

						}


						Globals.writeXMLFile(doc1, HIERARCHY_XML_DIR, XMLfile);
						//end code change Menaka 21MAR2014
					}

					Hashtable auditcolumnNameHT=Globals.auditColumnName();
					Hashtable auditcoldetHT=new Hashtable<>();
					Hashtable auditcoldetHTtemp=new Hashtable<>();
					for(int i=0;i<auditcolumnNameHT.size();i++){
						auditcoldetHTtemp=gfb.getDatatypefromDBtable("HIERARCHY_AUDIT."+String.valueOf(auditcolumnNameHT.get(i)),con,"","","");
						auditcoldetHT.put(i, auditcoldetHTtemp);
					}
					Hashtable auditHT=new Hashtable<>();

					auditHT.put(0, lb.username);
					auditHT.put(1, StartDate);
					auditHT.put(2, hierarchy_ID);
					auditHT.put(3, "Add");

					auditHT.put(4, "");
					auditHT.put(5, "");
					auditHT.put(6, hierarchy_ID);


					Hashtable beforHT=Globals.getAttributeNameandValHT(rootLevel);
					//			int i=doc.getDocumentElement().getChildNodes().item(0).Position();
					String nodeText=getConcatedString4AttrHT(beforHT);
					auditHT.put(7, nodeText);
					auditHT.put(8, nodeText);


					auditHT.put(9, "");
					auditHT.put(10, "");
					auditHT.put(11, "");
					auditHT.put(12, "");
					auditHT.put(13, "");
					auditHT.put(14, "");


					logChanges("Add",auditHT,auditcoldetHT,con);
					this.disableDataNHier=false;  // code change Menaka 20MAR2014
				}

				else if(flagValue.equals("Fromsegment")){  


					if(hirename==null||hirename.equals("")||hirename.isEmpty()){  // code change Menaka 19FEB2014
						this.msg1 = "Enter the hierarchy name.";
						color4HierTreeMsg = "red";
						return;
					}

					if(hierarchyNameList.containsValue(hirename)){

						System.out.println("Hierarchy Name already exists.. Please Enter another Name...");
						this.msg1 = "Hierarchy name already exists. Please enter another name.";
						color4HierTreeMsg = "red";
						return;

					}
					this.msg1 = "";
					Node node = doc.getLastChild();  
					Element em1 = doc.createElement("Hierarchy_Level");
					node.appendChild(em1);
					String StartDate = sdf1.format(HIERYDate);
					node.appendChild(em1);
					em1.setAttribute("Hierarchy_ID",hierarchy_ID);
					em1.setAttribute("Hierarchy_Name",hirename);
					em1.setAttribute("Created_Date",StartDate);
					em1.setAttribute("Modified_Date",StartDate);
					em1.setAttribute("Hierarchy_Category", hierarchyCategory);
					em1.setAttribute("Dim_Status", "Not Generated");
					em1.setAttribute("Dim_Status_Details", "Not Generated");

					this.hierarchyName = hirename;
					setFlagNew("alreadyadded");
					String hierName="";
					Globals.writeXMLFile(doc, HIERARCHY_XML_DIR, hierarchyXmlFileName);
				}
				else
				{		

					if(selectedRows.size() == 0)
					{
						//			

						NodeList pan = doc.getElementsByTagName("Hierarchy_Level");
						String hierarchyID4SetAttr="";
						Node Level1=null;
						if(pan != null){

							for(int pnode=0;pnode<pan.getLength();pnode++)
							{
								Level1 = pan.item(pnode);

								if(Level1.getAttributes().getNamedItem("Hierarchy_ID").getTextContent().toString().equals(hierarchy_ID))
								{
									//code change Jayaramu 08FEB14
									hierarchyID4SetAttr = hierarchy_ID; 
									NodeList checkRootLevelExists = Level1.getChildNodes();
									Node checkChildnodeExists = null;
									int noOfchikdNode=0;
									for(int i=0;i<checkRootLevelExists.getLength();i++){

										checkChildnodeExists = checkRootLevelExists.item(i);
										if(checkChildnodeExists.getNodeType() == Node.ELEMENT_NODE){

											if(checkRootLevelExists.item(i).getNodeName().equals("RootLevel")){
												System.out.println("checkRootLevelExists---->>>"+checkChildnodeExists);
												noOfchikdNode++;
											}
										}

									}

									if(noOfchikdNode >= 1){
										if(false){ //want to add two root in one Hierarchy change false to true
											int hierarchyMaxID = Globals.getMaxID(HIERARCHY_XML_DIR, hierarchyConfigFile,"Hierarchy_Level_ID", "ID");
											hierarchyID4SetAttr = String.valueOf(hierarchyMaxID);
										}else{

											System.out.println("-----RootLevel Already Exists----");
											return;
										}
									}


									//		    			 System.out.println("hierarchyID4SetAttr------>>>>"+hierarchyID4SetAttr); 
									//End code change jayaramu 08FEB14
									String uniqueid=String.valueOf(Globals.getMaxID(HIERARCHY_XML_DIR, hierarchyConfigFile,"Row_ID4Oracle", "ID"));
									String modifiedDateUpdate = sdf.format(HIERYDate);
									String segNo = "";
									modifiedDate = (Element)Level1;
									modifiedDate.setAttribute("Modified_Date",modifiedDateUpdate);
									modifiedDate.setAttribute("Dim_Status","Not Generated");
									modifiedDate.setAttribute("Dim_Status_Details","Not Generated");
									this.hierarchyModifiedDate = modifiedDateUpdate;
									em = doc.createElement("RootLevel");
									Level1.appendChild(em);
									em.setAttribute("ID", hierarchyID4SetAttr);
									em.setAttribute("Type", type);
									em.setAttribute("value", name);
									em.setAttribute("Unique_ID", uniqueid);
									em.setAttribute("Code_Combination", String.valueOf(codecombinationFlag));
									Node rootLevel = (Node)em;
									Element em1 = doc.createElement("ID");
									rootLevel.appendChild(em1);
									em1.setTextContent(hierarchyID4SetAttr);
									Element em2 = doc.createElement("RootLevel_Name");
									rootLevel.appendChild(em2);
									em2.setTextContent(name);
									em2.setAttribute("ID", hierarchyID4SetAttr);
									em2.setAttribute("Type", type);
									em2.setAttribute("Level", "1");
									if(type.equals("Data")){

										if(segmentNumber.equals("0")){ //when segment zero its data else its segment
											em.setAttribute("Segment", segmentNumber);	
											em.setAttribute("Primary_Segment","0");
										}else{

											segNo = segmentNumber.substring(segmentNumber.length()-1);

											segNo = "seg"+segNo+"_";
											em.setAttribute(segNo+"value", name+"("+code+");");
											em.setAttribute(segNo+"Code", code+";");
											em.setAttribute("Segment", segmentNumber+"$~$");
											em.setAttribute("Primary_Segment",segmentNumber);
											em.setAttribute("CostCenter_TableName_ColumnName", hdb.costCenterTableName+"."+hdb.costCenterColumnName);
											em.setAttribute("OverWrite_Code_Combination", String.valueOf(codeFlag));



											//		    					Getting hierarchy Type  if(reference=true) chech this cost center already available or not 
											// if already availabe  code change pandian 31Mar2014
											String hierarchyType=modifiedDate.getAttribute("RI_Hierarchy_Type");
											if(hierarchyType.equals("Reference")){

												Node rulenodeDH=Globals.getNodeByAttrVal(doc, "RI-Rules", "Hierarchy_ID", hierarchy_ID);  
												Hashtable ruleHT=Globals.getAttributeNameandValHT(rulenodeDH);
												String rule7="";
												rule7=(String)ruleHT.get("RI-007");
												if(rule7.equals("true")){
													String hierID=hierarchy_ID;
													String nodeType="Data";
													String dataNodeLevel="RootLevel"; 
													String dataNodeID=hierarchyID4SetAttr;
													String dataNodeuniqueID=uniqueid;
													String dataNodeValue=name+"("+code+");";
													String depTableNColName=hdb.costCenterTableName+"."+hdb.costCenterColumnName;
													//check this is
													RIConstrainsBean ri=new RIConstrainsBean();
													Boolean isAlreadyExsist=ri.addDataNodetoRefXML(hierID,nodeType,dataNodeLevel,dataNodeuniqueID,dataNodeID,dataNodeValue,depTableNColName);
													if(isAlreadyExsist){			    						
														System.out.println("R1-007: A Data element can appear in only one Hierarchy Family, Validate in same hierarchy and other RHs as well. so return this Value");
														// set msg to visual User

														FacesContext ctx1 = FacesContext.getCurrentInstance();
														ExternalContext extContext1 = ctx1.getExternalContext();
														Map sessionMap1 = extContext1.getSessionMap();
														HierarchyDBBean hiedbbn = (HierarchyDBBean) sessionMap1.get("hierarchyDBBean");
														hiedbbn.setSegInfoMsg("R1-007: Cannot Violate All A Data element can appear in only one Hierarchy Family.");

														return;
													}
												}




											}


										}

										em.setAttribute("value", name+"("+code+");");
										em.setAttribute("Code", code);
										em.setAttribute("Segment_ID", segmentID);

									}else{
										em2.setAttribute("value", name);
									}
									Globals.writeXMLFile(doc, HIERARCHY_XML_DIR, hierarchyXmlFileName);
									//updating db datda
									genarateFactsBean gfb=new genarateFactsBean("","","");
									if(UPDATE_DB_FOR_EACH_NODE){
										Hashtable valueHT=new Hashtable<>();

										String sortorder=String.valueOf(String.valueOf(Globals.getMaxID(HIERARCHY_XML_DIR, hierarchyConfigFile,"Sort_Order4Oracle", "ID")));
										int j=0;
										Hashtable<Integer,String> basicValueHT=new Hashtable<>();
										basicValueHT.put(0, uniqueid);
										basicValueHT.put(1, "GL");
										basicValueHT.put(2, Level1.getAttributes().getNamedItem("Hierarchy_Category").getNodeValue());
										basicValueHT.put(3, String.valueOf(Level1.getAttributes().getNamedItem("Hierarchy_ID").getNodeValue()));
										basicValueHT.put(4, String.valueOf(Level1.getAttributes().getNamedItem("Hierarchy_ID").getNodeValue()));
										basicValueHT.put(5, String.valueOf(Level1.getAttributes().getNamedItem("Hierarchy_Name").getNodeValue()));
										basicValueHT.put(6, sortorder);
										basicValueHT.put(7, "null");
										basicValueHT.put(8, String.valueOf(Level1.getAttributes().getNamedItem("Hierarchy_ID").getNodeValue()));
										basicValueHT.put(9, String.valueOf(Level1.getAttributes().getNamedItem("Hierarchy_Name").getNodeValue()));
										//		    			System.out.println("basicValueHT1==>"+basicValueHT);

										valueHT.put(0, name);
										if(type.equals("Data")){
											valueHT.put(0, name+"~"+code);
										}
										//		    			for(int i=0;i>0;i--){
										//		    				
										//		    				nd=nd.getParentNode();
										//		    				
										//		    				System.out.println(">>>>>"+nd.getAttributes().getNamedItem("value").getNodeValue());
										//		    				valueHT.put(i-1, nd.getAttributes().getNamedItem("value").getNodeValue());
										//		    			}
										//		    			Hashtable basicValueHT1=new Hashtable<>();
										HierarchyBean he=new HierarchyBean();
										//		    			Thread t=new Thread(new HierarchyBean(addedName,levelNo,typeName,valueHT,basicValueHT,selectedNdID,selectednodeName,insertType,addedNodeID,previoustype4ReGenerate,con,addedNodeName,insertDoc));
										//		    			t.start();
										dimStatusHT.put("dimResult", "Generated");
										dimStatusHT.put("errorMessage", "Generated");
										Hashtable coldetHT=new Hashtable<>();
										String pdatatype="";
										Hashtable columnNameHT=Globals.getcolumnName();

										Hashtable coldetHTtemp=new Hashtable<>();
										for(int i=0;i<columnNameHT.size();i++){
											coldetHTtemp=gfb.getDatatypefromDBtable("WC_FLEX_HIERARCHY_D."+String.valueOf(columnNameHT.get(i)),con,"","","");
											coldetHT.put(i, coldetHTtemp);
										}
										he.insertionofValues2DB(name, 0, "Rollup",valueHT,basicValueHT,hierarchy_ID,"Hierarchy_Level","childInsert",hierarchy_ID,"",con,"RootLevel",doc,dimStatusHT,coldetHT,pdatatype,0);
									}

									Hashtable auditcolumnNameHT=Globals.auditColumnName();
									Hashtable auditcoldetHT=new Hashtable<>();
									Hashtable auditcoldetHTtemp=new Hashtable<>();
									for(int i=0;i<auditcolumnNameHT.size();i++){
										auditcoldetHTtemp=gfb.getDatatypefromDBtable("HIERARCHY_AUDIT."+String.valueOf(auditcolumnNameHT.get(i)),con,"","","");
										auditcoldetHT.put(i, auditcoldetHTtemp);
									}
									Hashtable auditHT=new Hashtable<>();
									String StartDate = sdf1.format(HIERYDate);
									auditHT.put(0, lb.username);
									auditHT.put(1, StartDate);
									auditHT.put(2, hierarchyID4SetAttr);
									auditHT.put(3, "Add");

									auditHT.put(4, "");
									auditHT.put(5, "");
									auditHT.put(6, hierarchyID4SetAttr);


									Hashtable beforHT=Globals.getAttributeNameandValHT(rootLevel);
									//		    			int i=doc.getDocumentElement().getChildNodes().item(0).Position();
									String nodeText=getConcatedString4AttrHT(beforHT);
									auditHT.put(7, nodeText);
									auditHT.put(8, nodeText);


									auditHT.put(9, "");
									auditHT.put(10, "");
									auditHT.put(11, "");
									auditHT.put(12, "");
									auditHT.put(13, "");
									auditHT.put(14, "");


									logChanges("Add",auditHT,auditcoldetHT,con);




								}
							}


						}}

					System.out.println("selectedRows.size()---->>>"+selectedRows.size());

					if(selectedRows.size()>0){


						dimStatusHT.put("dimResult", "Not Generated");
						dimStatusHT.put("errorMessage", "Not Generated");
						Globals.updateDimStatus(dimStatusHT,hierarchyXmlFileName,doc);
						String segNo="";
						//start code change Jayaramu 08FEB14
						Node ModifiedDateUpdateNode = Globals.getNodeByAttrVal(doc, "Hierarchy_Level", "Hierarchy_ID", hierarchy_ID);
						if(ModifiedDateUpdateNode.getNodeType() == Node.ELEMENT_NODE){
							modifiedDate = (Element)ModifiedDateUpdateNode;
							String modifiedDateUpdate = sdf.format(HIERYDate);
							modifiedDate.setAttribute("Modified_Date", modifiedDateUpdate);
							this.hierarchyModifiedDate = modifiedDateUpdate;
						}
						//End code change Jayaramu 08FEB14
						String levelID = selectedRows.get(0).getID();
						String nodeName = selectedRows.get(0).getLevelNode();
						String levels = "";
						int levelNo =0;
						Node parentNode = null;
						Node siblingNode = null;
						//		System.out.println("levelID----->>>>"+levelID);
						//		System.out.println("nodeName----->>>>"+nodeName);
						String selectedValue="";
						Node addAppendNode = Globals.getNodeByAttrVal(doc, nodeName, "ID", levelID);
						selectedValue = Globals.getAttrVal4AttrName(addAppendNode, "value");
						int Node_Level_ID=Globals.getMaxID(HIERARCHY_XML_DIR, hierarchyConfigFile,"Node_Level_ID", "ID");
						nodeID=String.valueOf(Node_Level_ID);

						if(selectedType.equals("Sibling")){
							if(nodeName.equals("RootLevel")){

								System.out.println("Sibling Not available for root node");
								return; // code change Menaka 10FEB2014
							}
							else
							{

								parentNode = addAppendNode.getParentNode();
								//		System.out.println("addAppendNode--->>>>>"+addAppendNode);
								siblingNode = addAppendNode.getNextSibling().getNextSibling();
								//		System.out.println("SiblingNode--->>>>>"+siblingNode);	
								levels = nodeName;

								// code change Menaka 10FEB2014
								String nodeNamesub = nodeName.substring(6);  
								levelNo = Integer.parseInt(nodeNamesub);
								levelNo++;


							}
						}
						else{
							if(nodeName.equals("RootLevel"))
							{
								levels = "Level_2";
								levelNo=2;
							}

							else
							{
								String nodeNamesub = nodeName.substring(6);
								levelNo = Integer.parseInt(nodeNamesub);
								levelNo++;
								System.out.println("nodeNamesub"+nodeNamesub);
								levels = "Level_"+levelNo;
								//		System.out.println("levels----->>>>"+levels);
							}
						}
						String uniqueid=String.valueOf(Globals.getMaxID(HIERARCHY_XML_DIR, hierarchyConfigFile,"Row_ID4Oracle", "ID"));
						if((parentNode!=null && parentNode.getNodeType() == Node.ELEMENT_NODE) || selectedType.equals("Child")){
							em = doc.createElement(levels);
							addAppendNode.appendChild(em);
							em.setAttribute("ID", String.valueOf(Node_Level_ID));
							em.setAttribute("Type", type);
							em.setAttribute("Unique_ID", uniqueid);
							if(type.equals("Data")){
								if(hdb.getFlag4AddData().equals("fromData")){ //when segment zero its data else its segment

									segNo = segmentNumber.substring(segmentNumber.length()-1);
									segNo = "seg"+segNo+"_";
									em.setAttribute(segNo+"value", "Direct");
									em.setAttribute(segNo+"Code", "SQL");
									em.setAttribute("Segment", segmentNumber+"$~$");
									em.setAttribute("value", "Direct");
									em.setAttribute("Primary_Segment",segmentNumber);
									em.setAttribute("CostCenter_TableName_ColumnName", hdb.costCenterTableName+"."+hdb.costCenterColumnName);
									em.setAttribute("Data_SubType", "DirectSQL");
									em.setAttribute("Join_Type", hdb.selectedJoinType4Direct);
									em.setAttribute("Code", "SQL");
									String selectedColumnName = "";
									for(int i=0;i<hdb.selectColNameHT.size();i++){
										selectedColumnName = selectedColumnName+String.valueOf(hdb.selectColNameHT.get(i))+"#~#";
									}
									em.setAttribute("Selected_ColumnName", selectedColumnName);
									String selectedTableName = "";
									for(int i=0;i<hdb.SelectTableNameHT.size();i++){
										selectedTableName = selectedTableName+String.valueOf(hdb.SelectTableNameHT.get(i))+"#~#";
									}
									em.setAttribute("Selected_TableName", selectedTableName);
									String constructedSQL = "";
									em.setAttribute("Custom_SQL", hdb.directSQL);
									constructedSQL = hdb.constructingSQL("Full");
									em.setAttribute("Normal_SQL", constructedSQL);
									em.setAttribute("Flag4_ConfiguredSQL", String.valueOf(hdb.customDirectSql));
									String whereClause = "";
									for(int i=0;i<hdb.whereClauseList.size();i++){
										HierarchydataBean hdataBean = (HierarchydataBean) hdb.whereClauseList.get(i);
										if(hdataBean.tarColumnName.equals("")){
											if(hdataBean.joinColumnOper.equalsIgnoreCase("Between")){
												whereClause = whereClause+hdataBean.selectedTableName+"."+hdataBean.selectedSrcColumn+"~"+hdataBean.joinColumnOper+"~"+
														hdataBean.getTarTableName()+" and "+hdataBean.getTarColumnName()+"#~#";
											}else{
												whereClause = whereClause+hdataBean.selectedTableName+"."+hdataBean.selectedSrcColumn+"~"+hdataBean.joinColumnOper+"~"+
														hdataBean.getTarTableName()+"#~#";
											}
										}else{
											whereClause = whereClause+hdataBean.selectedTableName+"."+hdataBean.selectedSrcColumn+"~"+hdataBean.joinColumnOper+"~"+
													hdataBean.getTarTableName()+"."+hdataBean.getTarColumnName()+"#~#";
										}
									}
									em.setAttribute("where_Caluse", whereClause);
									//	em.setAttribute("OverWrite_Code_Combination", String.valueOf(codeFlag));
									System.out.println("levels----->>>>"+codeCombinationflag4Data);
									em.setAttribute("Create_Code_Combination", codeCombinationflag4Data);

								}else if(hdb.getFlag4AddData().equals("fromData4DirectSQL")){
									//				System.out.println("hdb.getFlag4AddData()----->>>>"+hdb.getFlag4AddData());segNo = segmentNumber.substring(segmentNumber.length()-1);

									segNo = segmentNumber.substring(segmentNumber.length()-1);//code change Jayaramu for substring segnumber
									segNo = "seg"+segNo+"_";
									em.setAttribute(segNo+"value", name+";");
									em.setAttribute(segNo+"Code", code+";");
									em.setAttribute("Segment", segmentNumber+"$~$");
									em.setAttribute("value", name+"("+code+");");
									em.setAttribute("Primary_Segment",segmentNumber);
									em.setAttribute("CostCenter_TableName_ColumnName", hdb.costCenterTableName+"."+hdb.costCenterColumnName);
									em.setAttribute("Data_SubType", "DirectSQL");
									em.setAttribute("Join_Type", hdb.selectedJoinType4Direct);
									String selectedColumnName = "";
									for(int i=0;i<hdb.selectColNameHT.size();i++){
										selectedColumnName = selectedColumnName+String.valueOf(hdb.selectColNameHT.get(i))+"#~#";
									}
									em.setAttribute("Selected_ColumnName", selectedColumnName);
									String selectedTableName = "";
									for(int i=0;i<hdb.SelectTableNameHT.size();i++){
										selectedTableName = selectedTableName+String.valueOf(hdb.SelectTableNameHT.get(i))+"#~#";
									}
									em.setAttribute("Selected_TableName", selectedTableName);
									String constructedSQL = "";
									em.setAttribute("Custom_SQL", hdb.directSQL);
									constructedSQL = hdb.constructingSQL("Full");
									em.setAttribute("Normal_SQL", constructedSQL);
									em.setAttribute("Flag4_ConfiguredSQL", String.valueOf(hdb.customDirectSql));
									String whereClause = "";
									for(int i=0;i<hdb.whereClauseList.size();i++){
										HierarchydataBean hdataBean = (HierarchydataBean) hdb.whereClauseList.get(i);
										if(hdataBean.tarColumnName.equals("")){
											if(hdataBean.joinColumnOper.equalsIgnoreCase("Between")){
												whereClause = whereClause+hdataBean.selectedTableName+"."+hdataBean.selectedSrcColumn+"~"+hdataBean.joinColumnOper+"~"+
														hdataBean.getTarTableName()+" and "+hdataBean.getTarColumnName()+"#~#";
											}else{
												whereClause = whereClause+hdataBean.selectedTableName+"."+hdataBean.selectedSrcColumn+"~"+hdataBean.joinColumnOper+"~"+
														hdataBean.getTarTableName()+"#~#";
											}
										}else{
											whereClause = whereClause+hdataBean.selectedTableName+"."+hdataBean.selectedSrcColumn+"~"+hdataBean.joinColumnOper+"~"+
													hdataBean.getTarTableName()+"."+hdataBean.getTarColumnName()+"#~#";
										}
									}
									em.setAttribute("where_Caluse", whereClause);
									//				em.setAttribute("OverWrite_Code_Combination", String.valueOf(codeFlag));
									System.out.println("levels----->>>>"+codeCombinationflag4Data);
									em.setAttribute("Create_Code_Combination", codeCombinationflag4Data);
									em.setAttribute("Data_SubType", "fromData4DirectSQL");
									em.setAttribute("Code", code);
								}else{

									segNo = segmentNumber.substring(segmentNumber.length()-1);
									segNo = "seg"+segNo+"_";
									em.setAttribute(segNo+"value", name+";");
									em.setAttribute(segNo+"Code", code+";");
									em.setAttribute("Segment", segmentNumber+"$~$");
									em.setAttribute("value", name+"("+code+");");
									em.setAttribute("Primary_Segment",segmentNumber);
									em.setAttribute("CostCenter_TableName_ColumnName", hdb.costCenterTableName+"."+hdb.costCenterColumnName);
									em.setAttribute("Data_SubType", "Segment");
									//				em.setAttribute("OverWrite_Code_Combination", String.valueOf(codeFlag));


									//				Getting hierarchy Type  if(reference=true) chech this cost center already available or not 
									// if already availabe  code change pandian 31Mar2014
									String hierarchyType=modifiedDate.getAttribute("RI_Hierarchy_Type");
									if(hierarchyType.equals("Reference")){

										Node rulenodeDH=Globals.getNodeByAttrVal(doc, "RI-Rules", "Hierarchy_ID", hierarchy_ID);  
										Hashtable ruleHT=Globals.getAttributeNameandValHT(rulenodeDH);
										String rule7="";
										rule7=(String)ruleHT.get("RI-007");
										if(rule7.equals("true")){

											String hierID=hierarchy_ID;
											String nodeType="Data";
											String dataNodeLevel=levels; 
											String dataNodeID=String.valueOf(Node_Level_ID);
											String dataNodeuniqueID=uniqueid;
											String dataNodeValue= name+"("+code+");";
											String depTableNColName=hdb.costCenterTableName+"."+hdb.costCenterColumnName;
											//check this is
											RIConstrainsBean ri=new RIConstrainsBean();
											Boolean isAlreadyExsist=ri.addDataNodetoRefXML(hierID,nodeType,dataNodeLevel,dataNodeuniqueID,dataNodeID,dataNodeValue,depTableNColName);

											if(isAlreadyExsist){

												// set msg to visual User

												FacesContext ctx1 = FacesContext.getCurrentInstance();
												ExternalContext extContext1 = ctx1.getExternalContext();
												Map sessionMap1 = extContext1.getSessionMap();
												HierarchyDBBean hiedbbn = (HierarchyDBBean) sessionMap1.get("hierarchyDBBean");
												hiedbbn.setSegInfoMsg("R1-007: Cannot Violate All A Data element can appear in only one Hierarchy Family.");

												return;
											}
										}

									}

									em.setAttribute("Create_Code_Combination", String.valueOf(codeCombinationflag4Lastnode));
									em.setAttribute("Code", code);


								}


								em.setAttribute("Segment_ID", segmentID);

								em.setAttribute("Create_Code_Combination4Data", String.valueOf(createCodeCombinationflag4Lastnode));	//code change Vishnu 26Apr2014

							}
							else
							{
								em.setAttribute("value", name);


							}
							if(selectedType.equals("Sibling")){
								Node SibleNode = (Node)em;
								//		System.out.println("SibleNode----->>>"+SibleNode+"siblingNode---->>>"+siblingNode);

								if(siblingNode==null){   // code change Menaka 19FEB2014
									parentNode.appendChild(em);
								}else{
									parentNode.insertBefore(SibleNode,siblingNode);
								}

								System.out.println("Sibling node added successfullty");

							}}
						Node addedNode=(Node)em;
						//getfromhere
						//End code change Jayaramu
						genarateFactsBean gfb=new genarateFactsBean("","","");
						if(UPDATE_DB_FOR_EACH_NODE){
							Node nd4BasicInf=Globals.getNodeByAttrVal(doc, nodeName, "ID", selectedRows.get(0).getID());
							//		System.out.println("nd4BasicInf----->"+selectedRows.get(0).getLevelName());
							Hashtable<Integer,String> basicValueHT=new Hashtable<>();
							basicValueHT.put(0, uniqueid);
							basicValueHT.put(1, "GL");
							basicValueHT.put(2, ModifiedDateUpdateNode.getAttributes().getNamedItem("Hierarchy_Category").getNodeValue());

							String name4Hirerachy="";
							String sortorder=String.valueOf(String.valueOf(Globals.getMaxID(HIERARCHY_XML_DIR, hierarchyConfigFile,"Sort_Order4Oracle", "ID")));
							//		System.out.println("nd----->"+nd4BasicInf.getNodeName());
							for(int i=levelNo;i>0;i--){
								nd4BasicInf=nd4BasicInf.getParentNode();

								if(nd4BasicInf.getNodeName().equalsIgnoreCase("Hierarchy_Level") && nd4BasicInf.getNodeType()==Node.ELEMENT_NODE){
									if(type.equalsIgnoreCase("Hierarchy")){
										Node nd1=Globals.getNodeByAttrVal(doc, "Hierarchy_Level", "Hierarchy_Name", name);
										basicValueHT.put(3, nd4BasicInf.getAttributes().getNamedItem("Hierarchy_ID").getNodeValue());
										basicValueHT.put(4, nd4BasicInf.getAttributes().getNamedItem("Hierarchy_ID").getNodeValue());
										basicValueHT.put(5, nd4BasicInf.getAttributes().getNamedItem("Hierarchy_Name").getNodeValue());
										basicValueHT.put(6, sortorder);
										basicValueHT.put(7, "");
										basicValueHT.put(8, String.valueOf(Node_Level_ID));
										basicValueHT.put(9, name);
										NodeList ndlist1=nd1.getChildNodes();
										for(int t=0;t<ndlist1.getLength();t++){
											if(ndlist1.item(t).getNodeType()==Node.ELEMENT_NODE && ndlist1.item(t).getNodeName().equalsIgnoreCase("RootLevel")){
												name4Hirerachy=ndlist1.item(t).getAttributes().getNamedItem("value").getNodeValue();

											}

										}
										break;
									}else{
										basicValueHT.put(3, nd4BasicInf.getAttributes().getNamedItem("Hierarchy_ID").getNodeValue());
										basicValueHT.put(4, nd4BasicInf.getAttributes().getNamedItem("Hierarchy_ID").getNodeValue());
										basicValueHT.put(5, nd4BasicInf.getAttributes().getNamedItem("Hierarchy_Name").getNodeValue());
										basicValueHT.put(6, sortorder);
										basicValueHT.put(7, "");
										basicValueHT.put(8, String.valueOf(Node_Level_ID));
										basicValueHT.put(9, hierarchyName);
										break;
									}

								}
							}
							//		System.out.println("basicValueHT==>"+basicValueHT);
							//		System.out.println("name4Hirerachy==>"+name4Hirerachy);
							Hashtable valueHT=new Hashtable<>();


							Node nd=Globals.getNodeByAttrVal(doc, nodeName, "ID", selectedRows.get(0).getID());
							//		System.out.println("ssssssssss"+nd.getNodeName());
							int j=0;
							int t=0;
							if(!selectedType.equals("Sibling")){
								if(nd.getAttributes().getNamedItem("Type").getNodeValue().equalsIgnoreCase("Data")){
									if(Globals.getAttrVal4AttrName(nd, "Segment").contains("$~$")){
										if(!Globals.getAttrVal4AttrName(nd, "Primary_Segment").equals("") && Globals.getAttrVal4AttrName(nd, "Primary_Segment")!=null
												&& !Globals.getAttrVal4AttrName(nd, "Primary_Segment").equals("")){
											String primarySeg=nd.getAttributes().getNamedItem("Primary_Segment").getNodeValue();
											String primarysegValue = nd.getAttributes().getNamedItem("seg"+primarySeg.substring(primarySeg.indexOf("_")+1)+"_value").getNodeValue().replace(";", "");
											String primarysegCode = nd.getAttributes().getNamedItem("seg"+primarySeg.substring(primarySeg.indexOf("_")+1)+"_Code").getNodeValue().replace(";", "");
											valueHT.put(levelNo-2, primarysegValue+"~"+primarysegCode);
										}else{
											String primarySeg=Globals.getAttrVal4AttrName(nd, "Segment").split("\\$~\\$")[0];
											String primarysegValue = nd.getAttributes().getNamedItem("seg"+primarySeg.substring(primarySeg.indexOf("_")+1)+"_value").getNodeValue().replace(";", "");
											String primarysegCode = nd.getAttributes().getNamedItem("seg"+primarySeg.substring(primarySeg.indexOf("_")+1)+"_Code").getNodeValue().replace(";", "");
											valueHT.put(levelNo-2, primarysegValue+"~"+primarysegCode);
										}
									}else{
										valueHT.put(levelNo-2, Globals.getAttrVal4AttrName(nd, "value")+"~"+Globals.getAttrVal4AttrName(nd, "Code"));
									}

								}else{
									valueHT.put(levelNo-2, nd.getAttributes().getNamedItem("value").getNodeValue());
								}
								t=2;
							}else{
								t=1;
							}
							//


							for(int i=levelNo-2;i>0;i--){

								nd=nd.getParentNode();

								//			System.out.println(">>>>>"+nd.getNodeName());
								//			System.out.println("22222222"+nd.getAttributes().getNamedItem("value").getNodeValue());
								//			System.out.println("lllllllll"+nd.getAttributes().getNamedItem("Type").getNodeValue());
								if(nd.getAttributes().getNamedItem("Type").getNodeValue().equalsIgnoreCase("Data")){
									//				String primarySeg=nd.getAttributes().getNamedItem("Primary_Segment").getNodeValue();
									//				String primarysegValue = nd.getAttributes().getNamedItem("seg"+primarySeg.substring(primarySeg.indexOf("_")+1)+"_value").getNodeValue().replace(";", "");
									//				String primarysegCode = nd.getAttributes().getNamedItem("seg"+primarySeg.substring(primarySeg.indexOf("_")+1)+"_Code").getNodeValue().replace(";", "");
									//				valueHT.put(i-1, primarysegValue+"~"+primarysegCode);
									if(Globals.getAttrVal4AttrName(nd, "Segment").contains("$~$")){
										if(!Globals.getAttrVal4AttrName(nd, "Primary_Segment").equals("") && Globals.getAttrVal4AttrName(nd, "Primary_Segment")!=null
												&& !Globals.getAttrVal4AttrName(nd, "Primary_Segment").equals("")){
											String primarySeg=nd.getAttributes().getNamedItem("Primary_Segment").getNodeValue();
											String primarysegValue = nd.getAttributes().getNamedItem("seg"+primarySeg.substring(primarySeg.indexOf("_")+1)+"_value").getNodeValue().replace(";", "");
											String primarysegCode = nd.getAttributes().getNamedItem("seg"+primarySeg.substring(primarySeg.indexOf("_")+1)+"_Code").getNodeValue().replace(";", "");
											valueHT.put(levelNo-2, primarysegValue+"~"+primarysegCode);
										}else{
											String primarySeg=Globals.getAttrVal4AttrName(nd, "Segment").split("\\$~\\$")[0];
											String primarysegValue = nd.getAttributes().getNamedItem("seg"+primarySeg.substring(primarySeg.indexOf("_")+1)+"_value").getNodeValue().replace(";", "");
											String primarysegCode = nd.getAttributes().getNamedItem("seg"+primarySeg.substring(primarySeg.indexOf("_")+1)+"_Code").getNodeValue().replace(";", "");
											valueHT.put(levelNo-2, primarysegValue+"~"+primarysegCode);
										}
									}else{
										valueHT.put(levelNo-2, Globals.getAttrVal4AttrName(nd, "value")+"~"+Globals.getAttrVal4AttrName(nd, "Code"));
									}
									//				valueHT.put(i-1, primarysegValue+"~"+nd.getAttributes().getNamedItem("Code").getNodeValue());
								}else{
									valueHT.put(i-1, nd.getAttributes().getNamedItem("value").getNodeValue());
								}

							}
							String parent_code=Globals.gettingParentCodefromDB(levelID,con);

							basicValueHT.put(7, parent_code);
							dimStatusHT.put("dimResult", "Generated");
							dimStatusHT.put("errorMessage", "Generated");

							HierarchyBean he=new HierarchyBean();
							Hashtable coldetHT=new Hashtable<>();
							String pdatatype="";
							Hashtable columnNameHT=Globals.getcolumnName();

							Hashtable coldetHTtemp=new Hashtable<>();
							for(int i=0;i<columnNameHT.size();i++){
								coldetHTtemp=gfb.getDatatypefromDBtable("WC_FLEX_HIERARCHY_D."+String.valueOf(columnNameHT.get(i)),con,"","","");
								coldetHT.put(i, coldetHTtemp);
							}
							if(type.equalsIgnoreCase("Data")){
								//			String nameTemp=name.split(";")[0];
								valueHT.put(levelNo-1, name+"~"+code);
								he.insertionofValues2DB(name+"~"+code, levelNo, type,valueHT,basicValueHT,levelID,nodeName,"childInsert",String.valueOf(Node_Level_ID),"",con,levels,doc,dimStatusHT,coldetHT,pdatatype,0);
								//				Thread t1=new Thread(new HierarchyBean(addedName,levelNo,typeName,valueHT,basicValueHT,selectedNdID,selectednodeName,insertType,addedNodeID,previoustype4ReGenerate,con,addedNodeName,insertDoc));
								//		t1.start();
							}else if(type.equalsIgnoreCase("Hierarchy")){
								he.insertionofValues2DB(name, levelNo-1, type,valueHT,basicValueHT,levelID,nodeName,"childInsert",String.valueOf(Node_Level_ID),"",con,levels,doc,dimStatusHT,coldetHT,pdatatype,0);
								//			Thread t1=new Thread(new HierarchyBean(addedName,levelNo,typeName,valueHT,basicValueHT,selectedNdID,selectednodeName,insertType,addedNodeID,previoustype4ReGenerate,con,addedNodeName,insertDoc));
								//			t1.start();
							}else if(selectedType.equals("Sibling")){
								he.insertionofValues2DB(name, levelNo-2, type,valueHT,basicValueHT,levelID,nodeName,"Sibling",String.valueOf(Node_Level_ID),"",con,levels,doc,dimStatusHT,coldetHT,pdatatype,0);
								//			Thread t1=new Thread(new HierarchyBean(addedName,levelNo,typeName,valueHT,basicValueHT,selectedNdID,selectednodeName,insertType,addedNodeID,previoustype4ReGenerate,con,addedNodeName,insertDoc));
								//			t1.start();
							}else{

								valueHT.put(levelNo-1, name);


								he.insertionofValues2DB(name, levelNo, type,valueHT,basicValueHT,levelID,nodeName,"childInsert",String.valueOf(Node_Level_ID),"",con,levels,doc,dimStatusHT,coldetHT,pdatatype,0);
								//			Thread t1=new Thread(new HierarchyBean(addedName,levelNo,typeName,valueHT,basicValueHT,selectedNdID,selectednodeName,insertType,addedNodeID,previoustype4ReGenerate,con,addedNodeName,insertDoc));
								//			t1.start();
							}
							//		System.out.println("2222222"+valueHT);
						}
						Hashtable auditcolumnNameHT=Globals.auditColumnName();
						Hashtable auditcoldetHT=new Hashtable<>();
						Hashtable auditcoldetHTtemp=new Hashtable<>();
						for(int i=0;i<auditcolumnNameHT.size();i++){
							auditcoldetHTtemp=gfb.getDatatypefromDBtable("HIERARCHY_AUDIT."+String.valueOf(auditcolumnNameHT.get(i)),con,"","","");
							auditcoldetHT.put(i, auditcoldetHTtemp);
						}
						Hashtable auditHT=new Hashtable<>();
						String StartDate = sdf1.format(HIERYDate);
						auditHT.put(0, lb.username);
						auditHT.put(1, StartDate);
						auditHT.put(2, hierarchy_ID);
						auditHT.put(3, "Add");

						auditHT.put(4, levelID);
						auditHT.put(5, selectedValue);
						auditHT.put(6, Node_Level_ID);


						Hashtable beforHT=Globals.getAttributeNameandValHT(addedNode);
						//		int i=doc.getDocumentElement().getChildNodes().item(0).Position();
						String nodeText=getConcatedString4AttrHT(beforHT);
						auditHT.put(7, nodeText);
						auditHT.put(8, nodeText);


						auditHT.put(9, "");
						auditHT.put(10, "");
						auditHT.put(11, "");
						auditHT.put(12, "");
						auditHT.put(13, "");
						auditHT.put(14, "");


						logChanges("Add",auditHT,auditcoldetHT,con);


					}
				}

				this.infoMessage = "Node Added Successfully.";
				//start code change Jayaramu
				if(flagValue.equals("newAdded")){
					ReportsInvXMLTreeBean rpt1 = new ReportsInvXMLTreeBean();
					//			System.out.println("hierarchyID----->>>>"+String.valueOf(hierarchyID));
					ReportsInvXMLTreeBean rpt = new ReportsInvXMLTreeBean(hierarchyXmlFileName, hierarchy_ID,null,"");
					rpt.getReportsForSession(hierarchyXmlFileName, hierarchy_ID,null,"");
				}

			}


		}catch(Exception e){


			e.printStackTrace();
			try{



				String errorMsg=Globals.getErrorString(e);  // code change Menaka 24FEB2014

				dimStatusHT.put("dimResult", "Error");
				dimStatusHT.put("errorMessage", errorMsg);

			}catch(Exception ex){

				ex.printStackTrace();
			}
		}


		System.out.println("isReGenerated====>>>>>"+isReGenerated);

		//		if(!isReGenerated){
		//		Globals.updateDimStatus(dimStatusHT,hierarchyXmlFileName,doc);
		//		}
		if(!flagValue.equals("newAdded")){
			Globals.writeXMLFile(doc, HIERARCHY_XML_DIR, hierarchyXmlFileName);
		}
		if(flagValue.equalsIgnoreCase("AddRefHier")){
			System.out.println("Sibling node added successfullty++++****+*****");
			RIConstrainsBean ri = new RIConstrainsBean();
			Node DHHierNode = Globals.getNodeByAttrVal(doc, "Hierarchy_Level", "Hierarchy_ID", hierarchy_ID);
			//			String refHierID = Globals.getAttrVal4AttrName(DHHierNode, "RI_Reference_Hierarchy_ID");
			ri.implementationRI(hierarchy_ID, nodeID, null);
		}
		//code change Jayaramu 20MAY2014 for load hierarchy tree.
		Map<String, Object> viewMap = FacesContext.getCurrentInstance().getViewRoot().getViewMap();
		ReportsInvXMLTreeBean viewScopedBean = (ReportsInvXMLTreeBean) viewMap.get("reportsInvXMLTreeBean");
		viewScopedBean.getReportsForSession(hierarchyXmlFileName,hierarchy_ID,null,"FromHierTree");

		isReGenerated = false;
		System.out.println("Exiting: "
				+ new Exception().getStackTrace()[0].getClassName() + "."
				+ new Exception().getStackTrace()[0].getMethodName());
	}//End code change Jayaramu




	public  void insertUPNDOWNAgainsCurrentNode(String currentnodeName4Navi,String currentnoderefAttri,String currentnodeId,String inserType,
			String insertCodeCombination,String insertNewID,String insertNewType,String insertNewUniqID,String insertNewValue,String xmlfilePath,String xmlFileName,Document doc,String code,String segmentNumber,String segmentID,Hashtable dimStatusHT)
	{//Node Navigation UP and DOWN
		String nodeValidator="Valid";
		try{
			//		Document doc=Globals.openXMLFile(xmlfilePath,xmlFileName);
			Node currentNode=Globals.getNodeByAttrVal(doc, currentnodeName4Navi,currentnoderefAttri, currentnodeId);
			Node previousNode=null;
			Node nextNode=null;
			Node nextNodeinDB=null;

			Element impNodeE =null;
			String Currentlevel=currentNode.getNodeName();
			String increLevel="1";	



			if(inserType.equalsIgnoreCase("InsertUP")){
				///// up current node	
				Node grandparent=currentNode.getParentNode();
				if(Currentlevel.equals("RootLevel")){

					NodeList ndlist=currentNode.getChildNodes();			
					for(int k=0;k<ndlist.getLength();k++){
						Node childNodes=ndlist.item(k);
						if (childNodes.getNodeType() == Node.ELEMENT_NODE) {

							if(childNodes.getNodeName().equals("ID")){
								childNodes.getParentNode().removeChild(childNodes);
							}

							if(childNodes.getNodeName().equals("RootLevel_Name")){
								childNodes.getParentNode().removeChild(childNodes);
							}

						}
					}


					impNodeE =doc.createElement("RootLevel");
					impNodeE.setAttribute("Code_Combination", insertCodeCombination);
					impNodeE.setAttribute("ID", insertNewID);
					impNodeE.setAttribute("Type", insertNewType);
					impNodeE.setAttribute("Unique_ID", insertNewUniqID);

					if(insertNewType.equals("Data")){

						FacesContext ctx = FacesContext.getCurrentInstance();
						ExternalContext extContext = ctx.getExternalContext();
						Map sessionMap = extContext.getSessionMap();
						HierarchyDBBean hdb = (HierarchyDBBean) sessionMap.get("hierarchyDBBean");
						// code  mm
						Node ModifiedDateUpdateNode = Globals.getNodeByAttrVal(doc, "Hierarchy_Level", "Hierarchy_ID", hierarchy_ID);
						Element modifiedDate=null;
						if(ModifiedDateUpdateNode.getNodeType() == Node.ELEMENT_NODE){
							modifiedDate = (Element)ModifiedDateUpdateNode;
						}
						//					Getting hierarchy Type  if(reference=true) chech this cost center already available or not 
						// if already availabe  code change pandian 31Mar2014
						String hierarchyType=modifiedDate.getAttribute("RI_Hierarchy_Type");
						if(hierarchyType.equals("Reference")){

							Node rulenodeDH=Globals.getNodeByAttrVal(doc, "RI-Rules", "Hierarchy_ID", hierarchy_ID);  
							Hashtable ruleHT=Globals.getAttributeNameandValHT(rulenodeDH);
							String rule7="";
							rule7=(String)ruleHT.get("RI-007");
							if(rule7.equals("true")){

								String hierID=hierarchy_ID;
								String nodeType="Data";
								String dataNodeLevel="RootLevel"; 
								String dataNodeID=String.valueOf(insertNewID);
								String dataNodeuniqueID=insertNewUniqID;
								String dataNodeValue= name+"("+code+");";
								String depTableNColName=hdb.costCenterTableName+"."+hdb.costCenterColumnName;
								//check this is
								RIConstrainsBean ri=new RIConstrainsBean();
								Boolean isAlreadyExsist=ri.addDataNodetoRefXML(hierID,nodeType,dataNodeLevel,dataNodeuniqueID,dataNodeID,dataNodeValue,depTableNColName);

								if(isAlreadyExsist){

									// set msg to visual User

									FacesContext ctx1 = FacesContext.getCurrentInstance();
									ExternalContext extContext1 = ctx1.getExternalContext();
									Map sessionMap1 = extContext1.getSessionMap();
									HierarchyDBBean hiedbbn = (HierarchyDBBean) sessionMap1.get("hierarchyDBBean");
									hiedbbn.setSegInfoMsg("R1-007: Cannot Violate All A Data element can appear in only one Hierarchy Family.");

									return;
								}
							}

						}



						impNodeE.setAttribute("value", insertNewValue+"("+code+")");
						impNodeE.setAttribute("Code", code);
						impNodeE.setAttribute("Segment", segmentNumber);
						impNodeE.setAttribute("Segment_ID", segmentID);
					}else{
						impNodeE.setAttribute("value", insertNewValue);	
					}

					grandparent.appendChild(impNodeE);
					Element grandParentNodeE =(Element)grandparent;

					// code change Menaka 20FEB2014
					Connection conn = Globals.getDBConnection("Data_Connection");
					String	SQL="DELETE FROM "+"WC_FLEX_HIERARCHY_D"+" WHERE HIER_CODE='"+hierarchy_ID+"'";
					System.out.println("SQL====>"+SQL);
					PreparedStatement pre=conn.prepareStatement(SQL);
					pre.execute();
					pre.close();

					grandParentNodeE.setAttribute("Hierarchy_ID", insertNewID); // pandian
					this.hierarchy_ID=insertNewID;
					Node newParent=impNodeE;
					Element impNodeID =doc.createElement("ID");
					impNodeID.setTextContent(insertNewID);
					newParent.appendChild(impNodeID);	
					Element impNoderootlevel =doc.createElement("RootLevel_Name");
					impNoderootlevel.setAttribute("ID",insertNewID);
					impNoderootlevel.setAttribute("Level", "1");
					impNoderootlevel.setAttribute("Type", insertNewType);
					impNoderootlevel.setAttribute("value", insertNewValue);
					impNoderootlevel.setTextContent(insertNewValue);
					newParent.appendChild(impNoderootlevel);
					newParent.appendChild(currentNode);		

					increLevel="1";	
				}else{
					impNodeE =doc.createElement(Currentlevel);
					impNodeE.setAttribute("ID", insertNewID);
					impNodeE.setAttribute("Type", insertNewType);
					impNodeE.setAttribute("Unique_ID", insertNewUniqID);

					if(insertNewType.equals("Data")){

						FacesContext ctx = FacesContext.getCurrentInstance();
						ExternalContext extContext = ctx.getExternalContext();
						Map sessionMap = extContext.getSessionMap();
						HierarchyDBBean hdb = (HierarchyDBBean) sessionMap.get("hierarchyDBBean");

						Node ModifiedDateUpdateNode = Globals.getNodeByAttrVal(doc, "Hierarchy_Level", "Hierarchy_ID", hierarchy_ID);
						Element modifiedDate=null;
						if(ModifiedDateUpdateNode.getNodeType() == Node.ELEMENT_NODE){
							modifiedDate = (Element)ModifiedDateUpdateNode;
						}
						//					Getting hierarchy Type  if(reference=true) chech this cost center already available or not 
						// if already availabe  code change pandian 31Mar2014
						String hierarchyType=modifiedDate.getAttribute("RI_Hierarchy_Type");
						if(hierarchyType.equals("Reference")){

							Node rulenodeDH=Globals.getNodeByAttrVal(doc, "RI-Rules", "Hierarchy_ID", hierarchy_ID);  
							Hashtable ruleHT=Globals.getAttributeNameandValHT(rulenodeDH);
							String rule7="";
							rule7=(String)ruleHT.get("RI-007");
							if(rule7.equals("true")){

								String hierID=hierarchy_ID;
								String nodeType="Data";
								String dataNodeLevel=Currentlevel; 
								String dataNodeID=String.valueOf(insertNewID);
								String dataNodeuniqueID=insertNewUniqID;
								String dataNodeValue= name+"("+code+");";
								String depTableNColName=hdb.costCenterTableName+"."+hdb.costCenterColumnName;
								//check this is
								RIConstrainsBean ri=new RIConstrainsBean();
								Boolean isAlreadyExsist=ri.addDataNodetoRefXML(hierID,nodeType,dataNodeLevel,dataNodeuniqueID,dataNodeID,dataNodeValue,depTableNColName);

								if(isAlreadyExsist){

									// set msg to visual User

									FacesContext ctx1 = FacesContext.getCurrentInstance();
									ExternalContext extContext1 = ctx1.getExternalContext();
									Map sessionMap1 = extContext1.getSessionMap();
									HierarchyDBBean hiedbbn = (HierarchyDBBean) sessionMap1.get("hierarchyDBBean");
									hiedbbn.setSegInfoMsg("R1-007: Cannot Violate All A Data element can appear in only one Hierarchy Family.");

									return;
								}
							}

						}



						impNodeE.setAttribute("value", insertNewValue+"("+code+")");
						impNodeE.setAttribute("Code", code);
						impNodeE.setAttribute("Segment", segmentNumber);
						impNodeE.setAttribute("Segment_ID", segmentID);
					}else{
						impNodeE.setAttribute("value", insertNewValue);
					}

					grandparent.appendChild(impNodeE);		
					Node newParent=impNodeE;
					newParent.appendChild(currentNode);		
					increLevel=Currentlevel.replace("Level_", "");	
				}		
				int increlevel=Integer.parseInt(increLevel);
				int nextincre=increlevel+1;		
				doc.renameNode(currentNode, null, "Level_"+nextincre);
				processallchiled(nextincre,currentNode,doc,"");		// code change Menaka 22FEB2014
				Globals.writeXMLFile(doc,xmlfilePath,xmlFileName);
				if(UPDATE_DB_FOR_EACH_NODE)
					reGenerateHierarchy(hierarchy_ID,codecombinationFlag,false,"");  // code change Menaka 20FEB2014


			}else if(inserType.equalsIgnoreCase("InsertDOWN")){
				////////////////down
				Hashtable allchildNodesHT=new Hashtable<>();
				NodeList ndlist=currentNode.getChildNodes();
				int child=0;
				for(int k=0;k<ndlist.getLength();k++){
					Node childNodes=ndlist.item(k);
					if (childNodes.getNodeType() == Node.ELEMENT_NODE) {
						if(childNodes.getNodeName().equals("ID") || childNodes.getNodeName().equals("RootLevel_Name")){}else{ // code change Menaka 20FEB2014
							allchildNodesHT.put(child, childNodes);				 
							child++;  
						}


					}
				}


				if(Currentlevel.equals("RootLevel")){
					impNodeE =doc.createElement("Level_2");
					impNodeE.setAttribute("ID", insertNewID);
					impNodeE.setAttribute("Type", insertNewType);
					impNodeE.setAttribute("Unique_ID", insertNewUniqID);
					if(insertNewType.equals("Data")){

						FacesContext ctx = FacesContext.getCurrentInstance();
						ExternalContext extContext = ctx.getExternalContext();
						Map sessionMap = extContext.getSessionMap();
						HierarchyDBBean hdb = (HierarchyDBBean) sessionMap.get("hierarchyDBBean");
						// code  mm
						Node ModifiedDateUpdateNode = Globals.getNodeByAttrVal(doc, "Hierarchy_Level", "Hierarchy_ID", hierarchy_ID);
						Element modifiedDate=null;
						if(ModifiedDateUpdateNode.getNodeType() == Node.ELEMENT_NODE){
							modifiedDate = (Element)ModifiedDateUpdateNode;
						}
						//			Getting hierarchy Type  if(reference=true) chech this cost center already available or not 
						// if already availabe  code change pandian 31Mar2014
						String hierarchyType=modifiedDate.getAttribute("RI_Hierarchy_Type");
						if(hierarchyType.equals("Reference")){

							Node rulenodeDH=Globals.getNodeByAttrVal(doc, "RI-Rules", "Hierarchy_ID", hierarchy_ID);  
							Hashtable ruleHT=Globals.getAttributeNameandValHT(rulenodeDH);
							String rule7="";
							rule7=(String)ruleHT.get("RI-007");
							if(rule7.equals("true")){

								String hierID=hierarchy_ID;
								String nodeType="Data";
								String dataNodeLevel="Level_2"; 
								String dataNodeID=String.valueOf(insertNewID);
								String dataNodeuniqueID=insertNewUniqID;
								String dataNodeValue= name+"("+code+");";
								String depTableNColName=hdb.costCenterTableName+"."+hdb.costCenterColumnName;
								//check this is
								RIConstrainsBean ri=new RIConstrainsBean();
								Boolean isAlreadyExsist=ri.addDataNodetoRefXML(hierID,nodeType,dataNodeLevel,dataNodeuniqueID,dataNodeID,dataNodeValue,depTableNColName);

								if(isAlreadyExsist){

									// set msg to visual User

									FacesContext ctx1 = FacesContext.getCurrentInstance();
									ExternalContext extContext1 = ctx1.getExternalContext();
									Map sessionMap1 = extContext1.getSessionMap();
									HierarchyDBBean hiedbbn = (HierarchyDBBean) sessionMap1.get("hierarchyDBBean");
									hiedbbn.setSegInfoMsg("R1-007: Cannot Violate All A Data element can appear in only one Hierarchy Family.");

									return;
								}
							}

						}




						impNodeE.setAttribute("value", insertNewValue+"("+code+")");
						impNodeE.setAttribute("Code", code);  // code change Pandi 21FEB2014
						impNodeE.setAttribute("Segment", segmentNumber);
						impNodeE.setAttribute("Segment_ID", segmentID);
					}else{
						impNodeE.setAttribute("value", insertNewValue);
					}

					currentNode.appendChild(impNodeE);
				}else{
					increLevel=Currentlevel.replace("Level_", "");
					int increlevel=Integer.parseInt(increLevel);
					int nextincre=increlevel+1;	

					impNodeE =doc.createElement("Level_"+nextincre);
					impNodeE.setAttribute("ID", insertNewID);
					impNodeE.setAttribute("Type", insertNewType);
					impNodeE.setAttribute("Unique_ID", insertNewUniqID);
					if(insertNewType.equals("Data")){

						FacesContext ctx = FacesContext.getCurrentInstance();
						ExternalContext extContext = ctx.getExternalContext();
						Map sessionMap = extContext.getSessionMap();
						HierarchyDBBean hdb = (HierarchyDBBean) sessionMap.get("hierarchyDBBean");
						// code  mm
						Node ModifiedDateUpdateNode = Globals.getNodeByAttrVal(doc, "Hierarchy_Level", "Hierarchy_ID", hierarchy_ID);
						Element modifiedDate=null;
						if(ModifiedDateUpdateNode.getNodeType() == Node.ELEMENT_NODE){
							modifiedDate = (Element)ModifiedDateUpdateNode;
						}
						//			Getting hierarchy Type  if(reference=true) chech this cost center already available or not 
						// if already availabe  code change pandian 31Mar2014
						String hierarchyType=modifiedDate.getAttribute("RI_Hierarchy_Type");
						if(hierarchyType.equals("Reference")){

							Node rulenodeDH=Globals.getNodeByAttrVal(doc, "RI-Rules", "Hierarchy_ID", hierarchy_ID);  
							Hashtable ruleHT=Globals.getAttributeNameandValHT(rulenodeDH);
							String rule7="";
							rule7=(String)ruleHT.get("RI-007");
							if(rule7.equals("true")){

								String hierID=hierarchy_ID;
								String nodeType="Data";
								String dataNodeLevel="Level_"+nextincre; 
								String dataNodeID=String.valueOf(insertNewID);
								String dataNodeuniqueID=insertNewUniqID;
								String dataNodeValue= name+"("+code+");";
								String depTableNColName=hdb.costCenterTableName+"."+hdb.costCenterColumnName;
								//check this is
								RIConstrainsBean ri=new RIConstrainsBean();
								Boolean isAlreadyExsist=ri.addDataNodetoRefXML(hierID,nodeType,dataNodeLevel,dataNodeuniqueID,dataNodeID,dataNodeValue,depTableNColName);

								if(isAlreadyExsist){

									// set msg to visual User

									FacesContext ctx1 = FacesContext.getCurrentInstance();
									ExternalContext extContext1 = ctx1.getExternalContext();
									Map sessionMap1 = extContext1.getSessionMap();
									HierarchyDBBean hiedbbn = (HierarchyDBBean) sessionMap1.get("hierarchyDBBean");
									hiedbbn.setSegInfoMsg("R1-007: Cannot Violate All A Data element can appear in only one Hierarchy Family.");

									return;
								}
							}

						}

						impNodeE.setAttribute("value", insertNewValue+"("+code+")");
						impNodeE.setAttribute("Code", code);
						impNodeE.setAttribute("Segment", segmentNumber);
						impNodeE.setAttribute("Segment_ID", segmentID);
					}else{
						impNodeE.setAttribute("value", insertNewValue);
					}

					currentNode.appendChild(impNodeE);	

				}
				Node newParent=impNodeE;

				if(allchildNodesHT.size()>0){

					for(int k=0;k<allchildNodesHT.size();k++){

						Node chnode=(Node)allchildNodesHT.get(k);
						newParent.appendChild(chnode);

						//		System.out.println("amp--->"+chnode.getNodeName());
						Currentlevel=chnode.getNodeName();
						increLevel=Currentlevel.replace("Level_", "");
						int increlevel=Integer.parseInt(increLevel);
						int nextincre=increlevel+1;	
						doc.renameNode(chnode, null, "Level_"+nextincre);
						processallchiled(nextincre,chnode,doc,"");  // code change Menaka 22FEB2014

					}
				}	

				Globals.writeXMLFile(doc,xmlfilePath,xmlFileName);
				if(UPDATE_DB_FOR_EACH_NODE)
					reGenerateHierarchy(hierarchy_ID,codecombinationFlag,false,"");  // code change Menaka 20FEB2014

			}



		}catch(Exception e){
			e.printStackTrace();
			String dimErrorMsg = Globals.getErrorString(e);
			dimStatusHT.put("dimResult", "Error");
			dimStatusHT.put("errorMessage", dimErrorMsg);
			Globals.updateDimStatus(dimStatusHT,hierarchyXmlFileName,doc);
		}


	}


	public  void cutNxtSilbling(Node parent,Node currentNode,Node sibl,Hashtable siblingHT,int k){
		try{


			if(sibl==null){
				System.out.println("return  sib2-->");
				return;
			}if(sibl.getNodeType()==Node.ELEMENT_NODE){

				Node sib3=sibl.getNextSibling();
				if(sib3==null){
					System.out.println("return 1 sib2-->");
					return;
				}

				Node sib2=sibl.getNextSibling().getNextSibling();

				if(sib2==null){
					return;
				}else if(sib2.getNodeType()==Node.ELEMENT_NODE){
					//				parent.removeChild(sib2);
					//				currentNode.appendChild(sib2);
					System.out.println("sib2-->"+sib2.getNodeName());
					siblingHT.put(k, sib2);
					k++;
					cutNxtSilbling(parent,currentNode,sib2,siblingHT,k);
				}
			}


		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}

	boolean rhContainsDH = false;

	public boolean isRhContainsDH() {
		return rhContainsDH;
	}
	public void setRhContainsDH(boolean rhContainsDH) {
		this.rhContainsDH = rhContainsDH;
	}
	public  void movePreviousNNext(String type){  // code change Menaka 22FEB2014

		Document doc =null;
		try{
			if(selectedRows.size()<=0){	//code change Vishnu 10Apr2014
				valid = false;
				this.msg1="Select the node to move";
				color4HierTreeMsg = "red";
				return;
			}
			msg1 = "";
			valid = true;
			rhContainsDH =false;
			PropUtil prop=new PropUtil();
			String HIERARCHY_XML_DIR=prop.getProperty("HIERARCHY_XML_DIR");
			String hierarchyXmlFileName=prop.getProperty("HIERARCHY_XML_FILE");

			FileInputStream  fis = new FileInputStream(HIERARCHY_XML_DIR
					+ hierarchyXmlFileName);
			Element em = null;
			DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
			doc = docBuilder.parse((fis));
			Connection conn1 = Globals.getDBConnection("Data_Connection");
			FacesContext ctx = FacesContext.getCurrentInstance();  // Code change Menaka 10MAR2014
			ExternalContext extContext = ctx.getExternalContext();
			Map sessionMap = extContext.getSessionMap();
			LoginBean lb = (LoginBean) sessionMap.get("loginBean");

			cmngFromPage4RIConstraints="RI-RHDeleteProcess";
			Node rootNd = Globals.getNodeByAttrVal(doc, "Hierarchy_Level", "Hierarchy_ID", hierarchy_ID);

			if(!Globals.getAttrVal4AttrName(rootNd, "RI_Dependent_Hierarchies").equals("")){
				riMessage = "This Reference Hierarchy has Dependant Hierarchies. Do you want to Validate them now?";
				rhContainsDH =true;
			}
			String levelID = selectedRows.get(0).getID();
			String nodeName = selectedRows.get(0).getLevelNode();	

			System.out.println("levelID------->>>"+levelID);
			System.out.println("nodeName------->>>"+nodeName);


			if(nodeName.equals("RootLevel")){
				this.msg1="Not possible to move the root node";
				color4HierTreeMsg = "red";
				return;
			}


			Node currentNode=Globals.getNodeByAttrVal(doc, nodeName,"ID", levelID);

			Node currentNodeTemp = currentNode.cloneNode(true);
			Node parent=currentNode.getParentNode();
			String Currentlevel=currentNode.getNodeName();
			String increLevel=Currentlevel.replace("Level_", ""); 
			int increlevel=Integer.parseInt(increLevel);
			int nextincre=0;

			System.out.println("currentNode--------->>>"+currentNode);
			System.out.println("parent--------->>>"+parent);


			Hashtable siblingHT = new Hashtable<>();
			if(type.equals("Previous")){	

				if(parent.getNodeName().equals("RootLevel")){
					this.msg1="Not possible to move";
					color4HierTreeMsg = "red";
					return;
				}

				Node grandParent= parent.getParentNode();  //2

				System.out.println("parent.getNextSibling()------------->>>"+parent.getNextSibling());

				Node siblingNode=parent.getNextSibling().getNextSibling();
				Hashtable beforHT=Globals.getAttributeNameandValHT(currentNodeTemp);
				Hashtable auditHT=new Hashtable<>();
				auditHT.put(9, Globals.getAttrVal4AttrName(parent, "ID"));
				auditHT.put(10, Globals.getAttrVal4AttrName(parent, "value"));
				auditHT.put(11, test(parent, Globals.getAttrVal4AttrName(parent, "ID"), currentNode, Globals.getAttrVal4AttrName(currentNode, "ID"), doc));

				if(siblingNode==null){  

					//				Node next=currentNode.getNextSibling().getNextSibling();
					Node sibl=currentNode.getNextSibling().getNextSibling();

					System.out.println("next--------->>>"+sibl);


					if(sibl==null){
						parent.removeChild(currentNode);
						grandParent.appendChild(currentNode);
						nextincre=increlevel-1;
						doc.renameNode(currentNode, null, "Level_"+nextincre);
						processallchiled(nextincre,currentNode,doc,type);	
					}else{
						System.out.println("next--------->>>"+sibl);
						siblingHT.put(0, sibl);
						cutNxtSilbling(parent,currentNode,sibl,siblingHT,1);



						parent.removeChild(currentNode);
						grandParent.appendChild(currentNode);
						nextincre=increlevel-1;
						doc.renameNode(currentNode, null, "Level_"+nextincre);
						processallchiled(nextincre,currentNode,doc,type);	
						System.out.println("siblingHT--------->>>"+siblingHT);
						for(int i=0;i<siblingHT.size();i++){
							parent.removeChild((Node) siblingHT.get(i));
							currentNode.appendChild((Node) siblingHT.get(i));
						}



					}
				}else{
					System.out.println("siblingNode------------->>>"+siblingNode);

					Node sibl=currentNode.getNextSibling().getNextSibling();
					if(sibl==null){
						parent.removeChild(currentNode);
						grandParent.insertBefore(currentNode,siblingNode);
						nextincre=increlevel-1;
						doc.renameNode(currentNode, null, "Level_"+nextincre);
						processallchiled(nextincre,currentNode,doc,type);	
					}else{
						System.out.println("next1111--------->>>"+sibl);
						parent.removeChild(currentNode);
						grandParent.insertBefore(currentNode,siblingNode);

						nextincre=increlevel-1;
						doc.renameNode(currentNode, null, "Level_"+nextincre);
						processallchiled(nextincre,currentNode,doc,type);

						siblingHT.put(0, sibl);
						cutNxtSilbling(parent,currentNode,sibl,siblingHT,1);
						for(int i=0;i<siblingHT.size();i++){
							parent.removeChild((Node) siblingHT.get(i));
							currentNode.appendChild((Node) siblingHT.get(i));
						}

					}



				}
				Hashtable auditcolumnNameHT=Globals.auditColumnName();
				Hashtable auditcoldetHT=new Hashtable<>();
				Hashtable auditcoldetHTtemp=new Hashtable<>();
				genarateFactsBean gfb=new genarateFactsBean("","","");
				Date HIERYDate = new Date();
				String dateFormat = prop.getProperty("DATE_FORMAT");
				DateFormat sdf1 = new SimpleDateFormat(dateFormat);
				String StartDate = sdf1.format(HIERYDate);
				for(int i=0;i<auditcolumnNameHT.size();i++){
					auditcoldetHTtemp=gfb.getDatatypefromDBtable("HIERARCHY_AUDIT."+String.valueOf(auditcolumnNameHT.get(i)),conn1,"","","");
					auditcoldetHT.put(i, auditcoldetHTtemp);
				}


				auditHT.put(0, lb.getUsername());
				auditHT.put(1, StartDate);
				auditHT.put(2, hierarchy_ID);
				auditHT.put(3, "Move");

				auditHT.put(4, Globals.getAttrVal4AttrName(grandParent, "ID"));
				auditHT.put(5, Globals.getAttrVal4AttrName(grandParent, "value"));
				auditHT.put(6, Globals.getAttrVal4AttrName(currentNode, "ID"));



				Hashtable afterHT=Globals.getAttributeNameandValHT(currentNode);
				String nodeText=getConcatedString4AttrHT(beforHT);
				String afternodeText=getConcatedString4AttrHT(afterHT);
				auditHT.put(7, nodeText);
				auditHT.put(8, afternodeText);



				auditHT.put(12, Globals.getAttrVal4AttrName(grandParent, "ID"));
				auditHT.put(13, Globals.getAttrVal4AttrName(grandParent, "value"));
				auditHT.put(14, test(grandParent, Globals.getAttrVal4AttrName(grandParent, "ID"), currentNode, Globals.getAttrVal4AttrName(currentNode, "ID"), doc));
				logChanges("Move",auditHT,auditcoldetHT,conn1);

				// Pre
			}else if(type.equals("Next")){

				Hashtable beforHT=Globals.getAttributeNameandValHT(currentNodeTemp);
				Hashtable auditHT=new Hashtable<>();
				auditHT.put(9, Globals.getAttrVal4AttrName(parent, "ID"));
				auditHT.put(10, Globals.getAttrVal4AttrName(parent, "value"));
				auditHT.put(11, test(parent, Globals.getAttrVal4AttrName(parent, "ID"), currentNode, Globals.getAttrVal4AttrName(currentNode, "ID"), doc));

				Node sibl=currentNode.getPreviousSibling().getPreviousSibling();

				if(sibl==null||sibl.getNodeName().equals("RootLevel_Name")){
					this.msg1="Not possible to move";
					color4HierTreeMsg = "red";
					return;
				}

				parent.removeChild(currentNode);

				sibl.appendChild(currentNode);
				nextincre=increlevel+1;
				doc.renameNode(currentNode, null, "Level_"+nextincre);
				processallchiled(nextincre,currentNode,doc,type);


				Hashtable auditcolumnNameHT=Globals.auditColumnName();
				Hashtable auditcoldetHT=new Hashtable<>();
				Hashtable auditcoldetHTtemp=new Hashtable<>();
				genarateFactsBean gfb=new genarateFactsBean("","","");
				Date HIERYDate = new Date();
				String dateFormat = prop.getProperty("DATE_FORMAT");
				DateFormat sdf1 = new SimpleDateFormat(dateFormat);
				String StartDate = sdf1.format(HIERYDate);
				for(int i=0;i<auditcolumnNameHT.size();i++){
					auditcoldetHTtemp=gfb.getDatatypefromDBtable("HIERARCHY_AUDIT."+String.valueOf(auditcolumnNameHT.get(i)),conn1,"","","");
					auditcoldetHT.put(i, auditcoldetHTtemp);
				}


				auditHT.put(0, lb.getUsername());
				auditHT.put(1, StartDate);
				auditHT.put(2, hierarchy_ID);
				auditHT.put(3, "Move");

				auditHT.put(4, Globals.getAttrVal4AttrName(sibl, "ID"));
				auditHT.put(5, Globals.getAttrVal4AttrName(sibl, "value"));
				auditHT.put(6, Globals.getAttrVal4AttrName(currentNode, "ID"));



				Hashtable afterHT=Globals.getAttributeNameandValHT(currentNode);
				String nodeText=getConcatedString4AttrHT(beforHT);
				String afternodeText=getConcatedString4AttrHT(afterHT);
				auditHT.put(7, nodeText);
				auditHT.put(8, afternodeText);



				auditHT.put(12, Globals.getAttrVal4AttrName(sibl, "ID"));
				auditHT.put(13, Globals.getAttrVal4AttrName(sibl, "value"));
				auditHT.put(14, test(sibl, Globals.getAttrVal4AttrName(sibl, "ID"), currentNode, Globals.getAttrVal4AttrName(currentNode, "ID"), doc));
				logChanges("Move",auditHT,auditcoldetHT,conn1);


			}


			Globals.writeXMLFile(doc, HIERARCHY_XML_DIR, hierarchyXmlFileName);
			if(UPDATE_DB_FOR_EACH_NODE)
				reGenerateHierarchy(hierarchy_ID,codecombinationFlag,false,"");  

			Node hierarchyNode=Globals.getNodeByAttrVal(doc, "Hierarchy_Level","Hierarchy_ID", hierarchy_ID);
			if(hierarchyNode != null){
				Node hierarchyTypeNode = hierarchyNode.getAttributes().getNamedItem("RI_Hierarchy_Type");
				if(hierarchyTypeNode != null){
					String hierarchyType = hierarchyTypeNode.getNodeValue();
					if(hierarchyType.equals("Reporting") || hierarchyType.equals("Dependent")){
						RIConstrainsBean ri = new RIConstrainsBean();
						//			ri.implementationRI(hierarchy_ID,levelID,currentNode);
						ri.setRIConstrainStatusProcess(doc, hierarchy_ID, Globals.getAttrVal4AttrName(hierarchyNode, "RI_Reference_Hierarchy_ID"), "Yes", HIERARCHY_XML_DIR, hierarchyXmlFileName);	//code change Vishnu 31July2014
					}
				}
			}



		}catch(Exception e){
			e.printStackTrace();
		}


	}



	public static int processallchiled(int key,Node node,Document doc,String type)
	{

		System.out.println("Entering: " + new Exception().getStackTrace()[0].getClassName() + "."
				+ new Exception().getStackTrace()[0].getMethodName());

		try {

			if (node.getNodeType() == Node.ELEMENT_NODE) {
				NodeList nl = node.getChildNodes();
				//         System.out.println(key+"**************>Reading HierarchyNode Name : " + node.getNodeName()+" "+node.getAttributes().getNamedItem("ID"));

				//        System.out.println("Reading HierarchyNode Length: " + nl.getLength());
				for (int i = 0; i < nl.getLength(); i++) {
					node = nl.item(i);
					if (node.getNodeType() == Node.ELEMENT_NODE) {


						Hashtable NodeHT = new Hashtable();
						NodeHT = Globals.getAttributeNameandValHT(node);         
						System.out.println("NodeHT" + NodeHT);        

						String Currentlevel=node.getNodeName();
						String increLevel=Currentlevel.replace("Level_", ""); 
						int increlevel=Integer.parseInt(increLevel);
						int nextincre=increlevel+1;  
						if(type.equals("Previous")){
							nextincre=increlevel-1;   // pre (-)
						}
						String renameNode="Level_"+nextincre;   
						doc.renameNode(node, null, renameNode);


						System.out.println(key+"------------>Reading HierarchyNode Name : " + node.getNodeName()+" "+node.getAttributes().getNamedItem("ID"));

						key++;
						key= processallchiled(key,node,doc,type); 
						//        System.out.println("******** " + node.getNodeName()+" "+node.getAttributes().getNamedItem("ID"));
						continue;


					}

				}


			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Globals.getErrorString(e);
		}

		System.out.println("Exiting: " + new Exception().getStackTrace()[0].getClassName() + "."
				+ new Exception().getStackTrace()[0].getMethodName());
		return key;
	}

	// code change pandian move node up and down
	public void moveUpNDown(String moveMode){
		try{

			this.msg1="";
			String nideId = "";
			String nodeName = "";
			if(selectedRows.size() > 0){
				valid = true;
				nideId=selectedRows.get(0).getID();
				nodeName=selectedRows.get(0).getLevelNode();

				if(nodeName.equals("RootLevel")){
					this.msg1="Not possible to move the root node";
					color4HierTreeMsg = "red";
					return;
				}
				//			riMessage = "This Reference Hierarchy has Dependant Hierarchies. Do you want to Validate them now?";
				cmngFromPage4RIConstraints="RI-RHDeleteProcess";
				String hierID=hierarchy_ID;
				String retnValue=""; // code change Menaka 10MAR2014
				FacesContext ctx = FacesContext.getCurrentInstance();  // Code change Menaka 10MAR2014
				ExternalContext extContext = ctx.getExternalContext();
				Map sessionMap = extContext.getSessionMap();
				LoginBean lb = (LoginBean) sessionMap.get("loginBean");
				String LoginID=lb.getUsername();
				if(moveMode.equals("UP")){

					retnValue=dataProcess.nodeNavigationUpandDown(nodeName,"ID",nideId,"UP",HIERARCHY_XML_DIR,hierarchyXmlFileName,hierID,LoginID);
				}else{
					retnValue=dataProcess.nodeNavigationUpandDown(nodeName,"ID",nideId,"DOWN",HIERARCHY_XML_DIR,hierarchyXmlFileName,hierID,LoginID);
				}

				if(retnValue.equalsIgnoreCase("Valid")){  // code change Menaka 10MAR2014
					//				redirect("Hierarchytree.xhtml","",hierID,"","","","",false,"");
				}

			}else{
				valid = false;
				this.msg1="Select a node to move up / down";
				color4HierTreeMsg = "red";
				return;
			}


		}catch(Exception e){

			e.printStackTrace();
		}

	}
	ArrayList hierarchyAL1 = new ArrayList<>();
	public ArrayList getHierarchyAL1() {
		return hierarchyAL1;
	}
	public void setHierarchyAL1(ArrayList hierarchyAL1) {
		this.hierarchyAL1 = hierarchyAL1;
	}
	public void copynode() {
		try{
			System.out.println("hierarchyAL==>"+hierarchyAL.size());
			hierarchyAL1 = new ArrayList<>();
			for(int i=0;i<hierarchyAL.size();i++){
				HierarchydataBean hdb = (HierarchydataBean) hierarchyAL.get(i);
				//				System.out.println("String.valueOf(hierarchyAL.get(i))==>"+hdb.g/*etHierarchyName());
				//				System.out.println("hierarchyName==>"+hierarchyName);*/
				//				if(hdb.getHierarchyName().equalsIgnoreCase(hierarchyName)){
				System.out.println("hierarchyName==>"+hierarchyName);
				//					hierarchyAL.remove(i);

				//				}else{
				hierarchyAL1.add(hdb);
				//				}
			}
			System.out.println("hierarchyAL==>1"+hierarchyAL.size());
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public void rollupPopupDetail(String parentNodeType){
		try{

			// Start Code change Harini 10FEB14 for retrieving parent node and emptying name textbox
			PropUtil prop=new PropUtil();
			String XMLFileName=prop.getProperty("HIERARCHY_XML_FILE");
			Document doc=Globals.openXMLFile(HIERARCHY_XML_DIR, XMLFileName);  // code change Menaka 20MAR2014
			String ParentNodeValue = "";
			selectedType="Child";//code Change Harini 19FEB14
			this.msg1 = "";
			this.name="";//code Change Harini 19FEB14
			if(selectedRows.size() > 0){
				String selectedNodeID=selectedRows.get(0).getID();

				Node nd=Globals.getNodeByAttrVal(doc, selectedRows.get(0).getLevelNode(), "ID", selectedNodeID);
				if(nd != null && (nd.getParentNode().getNodeType() == Node.ELEMENT_NODE) && nd.getParentNode() != null && nd.getNodeType() == Node.ELEMENT_NODE && !selectedRows.get(0).getLevelNode().equals("RootLevel")){
					ParentNodeValue = nd.getParentNode().getAttributes().getNamedItem("value").getTextContent().toString();
					setDisableSibling(false); // code change Menaka 12FEB2014
				}
				else
				{
					setDisableSibling(true);  // code change Menaka 12FEB2014
					ParentNodeValue=selectedRows.get(0).getLevelName();
				}
				// end Code change Harini 10FEB14 for retrieving parent node and emptying name textbox
			}
			if(selectedRows.size() == 0){

				Node nd=Globals.getNodeByAttrVal(doc, "RootLevel", "ID", hierarchy_ID);

				if(nd==null)	{
					this.parentNodeName = "";
					this.type = parentNodeType;
					this.setLabel4Rollup("Add Root Node"); // code change Menaka 19FEB2014
					setDisableType4Root(true); 
					this.valid=true;  // Code change Menaka 11MAR2014
					System.out.println("valid============>>>"+valid);
				}
				else{
					setLabel4Rollup("Add Node"); // code change Menaka 19FEB2014
					setDisableType4Root(true); 
					this.msg1 = "Please select a node";
					color4HierTreeMsg = "red";
					this.valid=false;
					System.out.println("valid============>>>"+valid);
					return;

				}


			}
			else{

				String currentNode = selectedRows.get(0).getLevelNode();

				if(currentNode.equals("RootLevel")){ // Code change Menaka 11MAR2014
					this.parentNodeName = ParentNodeValue;   // Code change Menaka 02MAY2014
					this.type = parentNodeType;
					this.name="";
					this.setLabel4Rollup("Add Node");
					this.setDisableType4Root(false);
					this.setDisableSibling(true);
					this.valid=true;
				}else{

					this.parentNodeName = ParentNodeValue;//Code change Harini 10FEB14	
					this.type = parentNodeType;
					this.setLabel4Rollup("Add Node"); // code change Menaka 19FEB2014
					this.setDisableType4Root(false); 
					this.setDisableSibling(false);
					this.valid=true;  // Code change Menaka 11MAR2014
					this.name="";

				}

			}

		}
		catch(Exception e){
			e.printStackTrace();


		}

	}

	public void deleteSelectedHRY(String deleteFlag,List<ReportTree> fromRefHierAL) throws Exception{
		System.out.println("Entering: "
				+ new Exception().getStackTrace()[0].getClassName() + "."
				+ new Exception().getStackTrace()[0].getMethodName());

		try{
			List<ReportTree> selectedRows = new ArrayList<ReportTree>();
			msg1="";

			if(deleteFlag.equalsIgnoreCase("FromRefHierTree")){
				selectedRows = fromRefHierAL;
			}else{
				selectedRows = this.selectedRows;
			}

			if(selectedRows.size()<=0){ // code change Menaka 14MAR2014
				msg1="Please select a Node to Delete.";
				color4HierTreeMsg = "red";
				return;
			}
			cmngFromPage4RIConstraints = "RI-RHDeleteProcess";
			PropUtil prop = new PropUtil();
			HIERARCHY_XML_DIR = prop.getProperty("HIERARCHY_XML_DIR");
			Hashtable<String, String> hierarchyAttr = new Hashtable<String, String>();
			FileInputStream fis = new FileInputStream(HIERARCHY_XML_DIR
					+ hierarchyXmlFileName);
			Element em = null;
			DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
			Document doc = docBuilder.parse((fis));
			String SQL="";
			FacesContext fcxt=FacesContext.getCurrentInstance();
			ExternalContext exc=fcxt.getExternalContext();
			Map sessionMap = exc.getSessionMap();

			Connection conn = Globals.getDBConnection("Data_Connection");

			String selectedNodeName= selectedRows.get(0).getLevelNode();
			String selectedhryId = selectedRows.get(0).getID();
			Node selectedNode = Globals.getNodeByAttrVal(doc, selectedNodeName, "ID", selectedhryId);
			Node selectedNodeparentNode = selectedNode.getParentNode();

			if(selectedRows.size()>0){

				for(int j=0;j<selectedRows.size();j++){

					String NodeName= selectedRows.get(j).getLevelNode();
					String hryId = selectedRows.get(j).getID();
					int nodelevelno=0;
					Node addAppendNode = Globals.getNodeByAttrVal(doc, NodeName, "ID", hryId);
					String selectedparentLevelNo="";
					String selectedparentNodeName="";
					Node parentnd=null;
					if(addAppendNode != null){
						parentnd=addAppendNode.getParentNode();
						System.out.println("parentnd====>"+parentnd.getNodeName());
						//					NodeList parentNdList=parentnd.getChildNodes();
						//					for(int j=0;j<parentNdList.getLength();j++){
						//						
						//						if(parentNdList.item(j).getNodeType()==Node.ELEMENT_NODE){
						if(!parentnd.getNodeName().equalsIgnoreCase("Hierarchy_Level")){
							if(parentnd.getNodeName().equals("RootLevel")){
								if(parentnd.getAttributes().getNamedItem("Type").getNodeValue().equalsIgnoreCase("Data")){
									selectedparentNodeName=parentnd.getAttributes().getNamedItem("Code").getNodeValue();
									selectedparentLevelNo="1";
								}else{
									selectedparentNodeName=parentnd.getAttributes().getNamedItem("value").getNodeValue();
									selectedparentLevelNo="1";
								}
							}else{
								if(parentnd.getAttributes().getNamedItem("Type").getNodeValue().equalsIgnoreCase("Data")){
									selectedparentNodeName=parentnd.getAttributes().getNamedItem("Code").getNodeValue();
									selectedparentLevelNo=parentnd.getNodeName().substring(6);
								}else{
									selectedparentNodeName=parentnd.getAttributes().getNamedItem("value").getNodeValue();
									selectedparentLevelNo=parentnd.getNodeName().substring(6);
								}
							}

						}
						//					}


						Node hierNode=Globals.getNodeByAttrVal(doc, "Hierarchy_Level", "Hierarchy_ID", hierarchy_ID);
						Hashtable hierattHT=Globals.getAttributeNameandValHT(hierNode);
						if(hierattHT.get("RI_Hierarchy_Type")!=null){
							String hierType=(String)hierattHT.get("RI_Hierarchy_Type");
							if(hierType.equalsIgnoreCase("Reference")){
								Hashtable removeNodeHT=Globals.getAttributeNameandValHT(addAppendNode);
								String nodeType=(String)removeNodeHT.get("Type");
								if(nodeType.equalsIgnoreCase("Data")){
									String nodeID=(String)removeNodeHT.get("ID");
									RIConstrainsBean ri=new RIConstrainsBean();
									ri.removedataNode(hierarchy_ID,nodeID);
								}



							}
						}


						addAppendNode.getParentNode().removeChild(addAppendNode);
						System.out.println("Deleted Successfully");
					}//End code change Jayaramu 07FEb14
					String namevalue="";
					//				System.out.println("nodelevelno==>"+nodelevelno+"namevalue==>"+namevalue);
					if(UPDATE_DB_FOR_EACH_NODE){
						if(selectedRows.get(j).getLevelNode().equalsIgnoreCase("RootLevel")){
							nodelevelno=1;
							namevalue=Globals.getAttrVal4AttrName(addAppendNode, "value");
							SQL="DELETE FROM "+"WC_FLEX_HIERARCHY_D"+" WHERE HIER_CODE='"+hierarchy_ID+"'";
							System.out.println("SQL---->"+SQL);
							PreparedStatement pre=conn.prepareStatement(SQL);
							pre.execute();
							pre.close();
						}else{
							nodelevelno=Integer.parseInt(NodeName.substring(6));
							if(Globals.getAttrVal4AttrName(addAppendNode, "Type").equalsIgnoreCase("Data")){
								namevalue=Globals.getAttrVal4AttrName(addAppendNode, "Code");
								SQL="DELETE FROM "+"WC_FLEX_HIERARCHY_D"+" WHERE "+"HIER"+nodelevelno+"_CODE='"+namevalue+"' AND HIER_CODE='"+hierarchy_ID+"'";
								System.out.println("SQL====>"+SQL);
								PreparedStatement pre=conn.prepareStatement(SQL);
								pre.execute();
								pre.close();
							}else if(Globals.getAttrVal4AttrName(addAppendNode, "Type").equalsIgnoreCase("Hierarchy")){
								namevalue=Globals.getAttrVal4AttrName(addAppendNode, "value");
								Node parnode=Globals.getNodeByAttrVal(doc, "Hierarchy_Level", "Hierarchy_Name", namevalue);
								NodeList ndlist1=parnode.getChildNodes();
								for(int t=0;t<ndlist1.getLength();t++){
									if(ndlist1.item(t).getNodeType()==Node.ELEMENT_NODE && ndlist1.item(t).getNodeName().equalsIgnoreCase("RootLevel")){
										namevalue=ndlist1.item(t).getAttributes().getNamedItem("value").getNodeValue();
									}
								}

								SQL="DELETE FROM "+"WC_FLEX_HIERARCHY_D"+" WHERE "+"HIER"+nodelevelno+"_CODE='"+namevalue+"' AND HIER_CODE='"+hierarchy_ID+"' AND HIER"+selectedparentLevelNo+"_CODE='"+selectedparentNodeName+"'";
								System.out.println("SQL====>mmm"+SQL);
								PreparedStatement pre=conn.prepareStatement(SQL);
								pre.execute();
								pre.close();
							}else {
								namevalue=Globals.getAttrVal4AttrName(addAppendNode, "value");
								SQL="DELETE FROM "+"WC_FLEX_HIERARCHY_D"+" WHERE "+"HIER"+nodelevelno+"_CODE='"+namevalue+"' AND HIER_CODE='"+hierarchy_ID+"'";
								System.out.println("SQL====>"+SQL);
								PreparedStatement pre=conn.prepareStatement(SQL);
								pre.execute();
								pre.close();
							}
							System.out.println("nodelevelno==>"+nodelevelno+"namevalue==>"+namevalue);


						}
					}
					LoginBean lb = (LoginBean) sessionMap.get("loginBean");
					Date HIERYDate = new Date();
					String dateFormat = prop.getProperty("DATE_FORMAT");
					DateFormat sdf1 = new SimpleDateFormat(dateFormat);
					genarateFactsBean gfb=new genarateFactsBean("","","");
					Hashtable auditcolumnNameHT=Globals.auditColumnName();
					Hashtable auditcoldetHT=new Hashtable<>();
					Hashtable auditcoldetHTtemp=new Hashtable<>();
					for(int i=0;i<auditcolumnNameHT.size();i++){
						auditcoldetHTtemp=gfb.getDatatypefromDBtable("HIERARCHY_AUDIT."+String.valueOf(auditcolumnNameHT.get(i)),conn,"","","");
						auditcoldetHT.put(i, auditcoldetHTtemp);
					}
					Hashtable auditHT=new Hashtable<>();
					String StartDate = sdf1.format(HIERYDate);

					auditHT.put(0, lb.username);
					auditHT.put(1, StartDate);
					auditHT.put(2, hierarchy_ID);
					auditHT.put(3, "Delete");

					auditHT.put(4, Globals.getAttrVal4AttrName(parentnd, "ID"));
					auditHT.put(5,  Globals.getAttrVal4AttrName(parentnd, "value"));
					auditHT.put(6, hryId);


					Hashtable beforHT=Globals.getAttributeNameandValHT(addAppendNode);
					//				int i=doc.getDocumentElement().getChildNodes().item(0).Position();
					String nodeText=getConcatedString4AttrHT(beforHT);
					auditHT.put(7, nodeText);
					auditHT.put(8, "");


					auditHT.put(9, "");
					auditHT.put(10, "");
					auditHT.put(11, "");
					auditHT.put(12, "");
					auditHT.put(13, "");
					auditHT.put(14, "");


					logChanges("Delete",auditHT,auditcoldetHT,conn);
				}
				RIConstrainsBean ri = new RIConstrainsBean();	//code change Vishnu 7Apr2014
				Node hierNd = Globals.getNodeByAttrVal(doc, "Hierarchy_Level", "Hierarchy_ID", hierarchy_ID);
				if(!deleteFlag.equalsIgnoreCase("FromRefHierTree")){




					if(selectedNodeparentNode != null){


						Node hierarchyNode=Globals.getNodeByAttrVal(doc, "Hierarchy_Level","Hierarchy_ID", hierarchy_ID);
						if(hierarchyNode != null){
							Node hierarchyTypeNode = hierarchyNode.getAttributes().getNamedItem("RI_Hierarchy_Type");
							if(hierarchyTypeNode != null){
								String hierarchyType = hierarchyTypeNode.getNodeValue();
								if(hierarchyType.equals("Reporting") || hierarchyType.equals("Dependent")){
									ri.setRIConstrainStatusProcess(doc, hierarchy_ID, Globals.getAttrVal4AttrName(hierNd, "RI_Reference_Hierarchy_ID"), "Yes", HIERARCHY_XML_DIR, hierarchyXmlFileName);
								}
							}
						}



					}
				}
				Globals.writeXMLFile(doc, HIERARCHY_XML_DIR, hierarchyXmlFileName);	

			}
			else
			{
				System.out.println("Please Select Hierarchy to Delete");
			}





			//		if(!deleteFlag.equalsIgnoreCase("FromRefHierTree")){
			//			
			//			
			//			
			//			
			//			if(selectedNodeparentNode != null){
			//				
			//				
			//				Node hierarchyNode=Globals.getNodeByAttrVal(doc, "Hierarchy_Level","Hierarchy_ID", hierarchy_ID);
			//				if(hierarchyNode != null){
			//					Node hierarchyTypeNode = hierarchyNode.getAttributes().getNamedItem("RI_Hierarchy_Type");
			//					if(hierarchyTypeNode != null){
			//						String hierarchyType = hierarchyTypeNode.getNodeValue();
			//						if(hierarchyType.equals("Reporting") || hierarchyType.equals("Dependent")){
			//							RIConstrainsBean ri = new RIConstrainsBean();
			//							ri.implementationRI(hierarchy_ID,selectedhryId,selectedNodeparentNode);
			//				}
			//						}
			//					}
			//				
			//				
			//		
			//			}
			//			}

		}
		catch(Exception e){

			e.printStackTrace();
		}

		System.out.println("Exiting: "
				+ new Exception().getStackTrace()[0].getClassName() + "."
				+ new Exception().getStackTrace()[0].getMethodName());

	}


	boolean overwriteFlag4Data;
	//Start change Jayaramu 06FEB14 for update node in xml
	public void UpdateSelectedHierarchyLevel(String updateFrom,String tableName,String columnName,boolean overwriteFlag4Data) throws Exception{
		System.out.println("Entering: "
				+ new Exception().getStackTrace()[0].getClassName() + "."
				+ new Exception().getStackTrace()[0].getMethodName());



		try{
			PropUtil prop = new PropUtil();
			HIERARCHY_XML_DIR = prop.getProperty("HIERARCHY_XML_DIR");

			Document doc = Globals.openXMLFile(HIERARCHY_XML_DIR, hierarchyXmlFileName);
			String insertvalue="";
			FacesContext fcxt=FacesContext.getCurrentInstance();
			ExternalContext exc=fcxt.getExternalContext();
			Map sessionMap = exc.getSessionMap();
			HierarchyDBBean hdb = (HierarchyDBBean) sessionMap.get("hierarchyDBBean");
			String hryId = "";
			String NodeName = "";
			Node addAppendNode = null;
			if(selectedRows.size()>0){


				NodeName= selectedRows.get(0).getLevelNode();
				hryId = selectedRows.get(0).getID();
				Hashtable dimStatusHT = new Hashtable<>();
				//					System.out.println("NodeName--------->>>>>"+NodeName);
				//					System.out.println("hryId---------->>>>>>"+hryId);
				Element updateParemtEm = null;
				Element updateEm = null;
				String updatedNodeID="";
				String updatedNodeName="";
				//start code change Jayaramu 08FEB14
				Node ModifiedDateUpdateNode = Globals.getNodeByAttrVal(doc, "Hierarchy_Level", "Hierarchy_ID", hierarchy_ID);
				Element modifiedDate = (Element)ModifiedDateUpdateNode;
				if(ModifiedDateUpdateNode.getNodeType() == Node.ELEMENT_NODE){
					String dateFormat = prop.getProperty("DATE_FORMAT");
					Date HIERYDate = new Date();
					DateFormat sdf = new SimpleDateFormat(dateFormat);
					String modifiedDateUpdate = sdf.format(HIERYDate);
					modifiedDate.setAttribute("Modified_Date", modifiedDateUpdate);
					this.hierarchyModifiedDate = modifiedDateUpdate;
				}
				//End code change Jayaramu 08FEB14

				String nodelevelno="";
				String namevalue=selectedRows.get(0).getLevelName();
				String setValueAttr = "";
				String setcodeAttr = "";
				Connection conn=Globals.getDBConnection("DW_Connection");
				//for audit
				LoginBean lb = (LoginBean) sessionMap.get("loginBean");
				Date HIERYDate = new Date();
				String dateFormat = prop.getProperty("DATE_FORMAT");
				DateFormat sdf1 = new SimpleDateFormat(dateFormat);
				genarateFactsBean gfb=new genarateFactsBean("","","");
				Hashtable auditcolumnNameHT=Globals.auditColumnName();
				Hashtable auditcoldetHT=new Hashtable<>();
				Hashtable auditcoldetHTtemp=new Hashtable<>();

				for(int i=0;i<auditcolumnNameHT.size();i++){
					auditcoldetHTtemp=gfb.getDatatypefromDBtable("HIERARCHY_AUDIT."+String.valueOf(auditcolumnNameHT.get(i)),conn,"","","");
					auditcoldetHT.put(i, auditcoldetHTtemp);
				}
				Hashtable auditHT=new Hashtable<>();



				if(NodeName.equals("RootLevel")){
					Node addAppendparentNode = Globals.getNodeByAttrVal(doc, "RootLevel", "ID", hryId);
					Node addAppendparentNodeTemp = addAppendparentNode.cloneNode(true);
					addAppendNode = Globals.getNodeByAttrVal(doc, "RootLevel_Name", "ID", hryId);
					addAppendNode.setTextContent(name);
					updateEm = (Element)addAppendNode;
					updateParemtEm = (Element)addAppendparentNode;

					if(updateFrom.equals("DataUpdate")){
						if(segmentNumber.equals("0")){

							updateParemtEm.setAttribute("value", name+"("+code+")");
							updateParemtEm.setAttribute("Code", code);
							updateParemtEm.setAttribute("Segment", segmentNumber);
							updateParemtEm.setAttribute("Primary_Segment", "0");

							updateEm.setAttribute("value", name+"("+code+")");
							updateEm.setAttribute("Code", code);
							updateEm.setAttribute("Segment", segmentNumber);
							updateEm.setAttribute("Primary_Segment", "0");

							updateEm.setAttribute("Create_Code_Combination", String.valueOf(editcodeCombinationflag4Lastnode));
							updateEm.setAttribute("OverWrite_Code_Combination", String.valueOf(overwriteFlag4Data));
							updateEm.setAttribute("Create_Code_Combination4Data", String.valueOf(editcreateCodeCombinationflag4Lastnode)); //code change Vishnu 26Apr2014


						}else{



							String segNo = segmentNumber.substring(segmentNumber.length()-1);

							segNo = "seg"+segNo+"_";

							String codeAttr = "";
							String valueAttr = "";
							String segvalueAttr = "";

							String segmentAttr =  addAppendNode.getAttributes().getNamedItem("Segment").getTextContent().toString();
							segvalueAttr = addAppendNode.getAttributes().getNamedItem("value").getTextContent().toString();
							if(segmentAttr.contains("$~$")){


								if(segmentAttr.contains(segmentNumber)){


								}else{


									updateEm.setAttribute("Segment", segmentAttr+segmentNumber+"$~$");
								}

								updateParemtEm.setAttribute("value", segvalueAttr+name);
								updateParemtEm.setAttribute(segNo+"value", segValueAttr4Edit);
								updateParemtEm.setAttribute(segNo+"Code", code);
								String primarySeg = primarySegment.replace(" ", "_");
								updateParemtEm.setAttribute("Primary_Segment", primarySeg);


								updateEm.setAttribute("Primary_Segment", primarySeg);
								String segmentNo="";
								segmentNo = primarySeg.replace("Segment_", "");
								if(segmentNo == segNo){

									insertvalue = segValueAttr4Edit+"~"+code;
								}
							}else{
								String tempsegNo="seg"+segmentAttr.substring(segmentAttr.length()-1)+"_";
								segmentAttr = segmentAttr+"$~$";
								updateEm.setAttribute("Segment", segmentAttr+segmentNumber+"$~$");
								String tempvalueAttr = addAppendNode.getAttributes().getNamedItem("value").getTextContent().toString();
								String tempcodeAttr = addAppendNode.getAttributes().getNamedItem("Code").getTextContent().toString();
								System.out.println("segNo===>"+tempsegNo);
								System.out.println("valueAttr===>"+tempvalueAttr);
								System.out.println("codeAttr===>"+tempcodeAttr);
								updateEm.setAttribute(tempsegNo+"value", tempvalueAttr+";");
								updateEm.setAttribute(tempsegNo+"Code", tempcodeAttr+";");
								//								tempvalueAttr=tempvalueAttr+";";
								//								codeAttr=codeAttr+";";
								segvalueAttr=segvalueAttr+";";
							}

							updateEm.setAttribute("value", segvalueAttr+name);
							updateEm.setAttribute(segNo+"value", segValueAttr4Edit);
							updateEm.setAttribute(segNo+"Code", code);
							updateEm.setAttribute("CostCenter_TableName_ColumnName", tableName+"."+columnName);

							//							Getting hierarchy Type  if(reference=true) chech this cost center already available or not 
							// if already availabe  code change pandian 31Mar2014
							String hierarchyType=modifiedDate.getAttribute("RI_Hierarchy_Type");
							if(hierarchyType.equals("Reference")){

								Node rulenodeDH=Globals.getNodeByAttrVal(doc, "RI-Rules", "Hierarchy_ID", hierarchy_ID);  
								Hashtable ruleHT=Globals.getAttributeNameandValHT(rulenodeDH);
								String rule7="";
								rule7=(String)ruleHT.get("RI-007");
								if(rule7.equals("true")){

									String hierID=hierarchy_ID;
									String nodeType="Data";
									String dataNodeLevel=updateEm.getNodeName(); 
									String dataNodeID=hryId;
									String dataNodeuniqueID=updateEm.getAttribute("Unique_ID");
									String dataNodeValue=segvalueAttr+name;
									String depTableNColName=tableName+"."+columnName;
									//check this is
									RIConstrainsBean ri=new RIConstrainsBean();
									Boolean isAlreadyExsist=ri.addDataNodetoRefXML(hierID,nodeType,dataNodeLevel,dataNodeuniqueID,dataNodeID,dataNodeValue,depTableNColName);
									if(isAlreadyExsist){			    						
										System.out.println("R1-007: A Data element can appear in only one Hierarchy Family, Validate in same hierarchy and other RHs as well. so return this Value");
										// set msg to visual User

										FacesContext ctx1 = FacesContext.getCurrentInstance();
										ExternalContext extContext1 = ctx1.getExternalContext();
										Map sessionMap1 = extContext1.getSessionMap();
										HierarchyDBBean hiedbbn = (HierarchyDBBean) sessionMap1.get("hierarchyDBBean");
										hiedbbn.setSegInfoMsg("R1-007: Cannot Violate All A Data element can appear in only one Hierarchy Family.");

										return;
									}
								}



							}

						}}else{

							updateParemtEm.setAttribute("value", name);
							updateEm.setAttribute("value", name);
							insertvalue=name;
						}
					nodelevelno="1";
					updatedNodeName="RootLevel";
					Node updatedNd=(Node) updateEm;

					String StartDate = sdf1.format(HIERYDate);

					auditHT.put(0, lb.username);
					auditHT.put(1, StartDate);
					auditHT.put(2, hierarchy_ID);
					auditHT.put(3, "Modify");

					auditHT.put(4, "");
					auditHT.put(5,  "");
					auditHT.put(6, hryId);


					Hashtable beforHT=Globals.getAttributeNameandValHT(addAppendparentNodeTemp);
					Hashtable afterHT=Globals.getAttributeNameandValHT(updatedNd);
					//						int i=doc.getDocumentElement().getChildNodes().item(0).Position();
					String nodeText=getConcatedString4AttrHT(beforHT);
					String afterNodeText=getConcatedString4AttrHT(afterHT);
					auditHT.put(7, nodeText);
					auditHT.put(8, afterNodeText);


					auditHT.put(9, "");
					auditHT.put(10, "");
					auditHT.put(11, "");
					auditHT.put(12, "");
					auditHT.put(13, "");
					auditHT.put(14, "");


					logChanges("Modify",auditHT,auditcoldetHT,conn);



				}
				else{
					addAppendNode = Globals.getNodeByAttrVal(doc, NodeName, "ID", hryId);
					Hashtable beforHT=Globals.getAttributeNameandValHT(addAppendNode);
					Node parentNd=addAppendNode.getParentNode();
					nodelevelno=NodeName.substring(6);
					if(addAppendNode != null){

						updateEm = (Element)addAppendNode;
						if(updateFrom.equals("DataUpdate")){
							if(Globals.getAttrVal4AttrName(addAppendNode, "Data_SubType").equalsIgnoreCase("DirectSQL")){
								//when segment zero its data else its segment
								updateEm.setAttribute("Flag4_ConfiguredSQL", String.valueOf(hdb.customDirectSql));
								String segNo = "";
								segNo = segmentNumber.substring(segmentNumber.length()-1);
								segNo = "seg"+segNo+"_";
								updateEm.setAttribute(segNo+"value", "Direct");
								updateEm.setAttribute(segNo+"Code", "SQL");
								updateEm.setAttribute("Segment", segmentNumber+"$~$");
								updateEm.setAttribute("value", "Direct");
								updateEm.setAttribute("Code", "SQL");
								updateEm.setAttribute("Primary_Segment",segmentNumber);
								//								updateEm.setAttribute("CostCenter_TableName_ColumnName", hdb.costCenterTableName+"."+hdb.costCenterColumnName);
								updateEm.setAttribute("Data_SubType", "DirectSQL");
								updateEm.setAttribute("Join_Type", hdb.selectedJoinType4Direct);
								String selectedColumnName = "";
								for(int i=0;i<hdb.selectColNameHT.size();i++){
									selectedColumnName = selectedColumnName+String.valueOf(hdb.selectColNameHT.get(i))+"#~#";
								}
								updateEm.setAttribute("Selected_ColumnName", selectedColumnName);
								String selectedTableName = "";
								for(int i=0;i<hdb.SelectTableNameHT.size();i++){
									selectedTableName = selectedTableName+String.valueOf(hdb.SelectTableNameHT.get(i))+"#~#";
								}
								updateEm.setAttribute("Selected_TableName", selectedTableName);
								String constructedSQL = "";

								updateEm.setAttribute("Custom_SQL", hdb.directSQL);
								constructedSQL = hdb.constructingSQL("Full");
								updateEm.setAttribute("Normal_SQL", constructedSQL);
								String whereClause = "";
								for(int i=0;i<hdb.whereClauseList.size();i++){
									HierarchydataBean hdataBean = (HierarchydataBean) hdb.whereClauseList.get(i);
									System.out.println("hdb.whereClauseList.get(i)===>"+hdataBean.selectedTableName);
									if(hdataBean.tarColumnName.equals("")){
										if(hdataBean.joinColumnOper!=null){
											if(hdataBean.joinColumnOper.equalsIgnoreCase("Between")){
												whereClause = whereClause+hdataBean.selectedTableName+"."+hdataBean.selectedSrcColumn+"~"+hdataBean.joinColumnOper+"~"+
														hdataBean.getTarTableName()+" and "+hdataBean.getTarColumnName()+"#~#";
											}else{
												whereClause = whereClause+hdataBean.selectedTableName+"."+hdataBean.selectedSrcColumn+"~"+hdataBean.joinColumnOper+"~"+
														hdataBean.getTarTableName()+"#~#";
											}
										}
									}else{
										if(hdataBean.joinColumnOper!=null){
											whereClause = whereClause+hdataBean.selectedTableName+"."+hdataBean.selectedSrcColumn+"~"+hdataBean.joinColumnOper+"~"+
													hdataBean.getTarTableName()+"."+hdataBean.getTarColumnName()+"#~#";
										}
									}
								}
								updateEm.setAttribute("where_Caluse", whereClause);


								updateEm.setAttribute("Create_Code_Combination", codeCombinationflag4Data);
							}else{
								if(segmentNumber.equals("0")){


									updateEm.setAttribute("value", name+"("+code+")");
									updateEm.setAttribute("Code", code);
									updateEm.setAttribute("Segment", segmentNumber);
									updateEm.setAttribute("Primary_Segment", "0");



								}else{

									updateEm.setAttribute("Create_Code_Combination", String.valueOf(editcodeCombinationflag4Lastnode));
									String segNo = segmentNumber.substring(segmentNumber.length()-1);

									segNo = "seg"+segNo+"_";

									String codeAttr = "";
									String valueAttr = "";
									String segvalueAttr = "";
									String primarySeg = "";
									String segmentAttr = addAppendNode.getAttributes().getNamedItem("Segment").getTextContent().toString();
									String segNoValue = "";
									String segNoCode = "";
									Node valueNode = addAppendNode.getAttributes().getNamedItem("value");
									//							segNoValue = addAppendNode.getAttributes().getNamedItem(segNo+"value").getTextContent().toString();
									//							segNoCode = addAppendNode.getAttributes().getNamedItem(segNo+"Code").getTextContent().toString();
									segNoValue = Globals.getAttrVal4AttrName(addAppendNode, segNo+"value");
									segNoCode = Globals.getAttrVal4AttrName(addAppendNode, segNo+"Code");
									if(valueNode != null){
										segvalueAttr = addAppendNode.getAttributes().getNamedItem("value").getTextContent().toString();

									}
									if(segmentAttr.contains("$~$")){
										if(segmentAttr.contains(segmentNumber)){

										}else{
											updateEm.setAttribute("Segment", segmentAttr+segmentNumber+"$~$");
										}
										primarySeg = primarySegment.replace(" ", "_");
										//							updateEm.setAttribute("value", segvalueAttr+name);
										//							updateEm.setAttribute(segNo+"value", segNoValue+segValueAttr4Edit);
										//							updateEm.setAttribute(segNo+"Code", segNoCode+code);
										//							updateEm.setAttribute("Primary_Segment", primarySeg);
										String segmentNo="";
										segmentNo = primarySeg.replace("Segment_", "");
										if(segmentNo == segNo){

											insertvalue = segValueAttr4Edit+"~"+code;
										}
									}else{
										String tempsegNo = "";

										if(!segmentAttr.equals("")){
											tempsegNo="seg"+segmentAttr.substring(segmentAttr.length()-1)+"_";
											segmentAttr = segmentAttr+"$~$";
										}else{
											tempsegNo = "seg"+segmentNumber.substring(segmentNumber.length()-1)+"_";
										}


										updateEm.setAttribute("Segment", segmentAttr+segmentNumber+"$~$");
										Node ValuNode = addAppendNode.getAttributes().getNamedItem("value");
										String tempvalueAttr = "";
										if(ValuNode != null){
											tempvalueAttr = addAppendNode.getAttributes().getNamedItem("value").getTextContent().toString();
										}

										String tempcodeAttr = addAppendNode.getAttributes().getNamedItem("Code").getTextContent().toString();
										System.out.println("segNo===>"+tempsegNo);
										System.out.println("valueAttr===>"+tempvalueAttr);
										System.out.println("codeAttr===>"+tempcodeAttr);
										//								updateEm.setAttribute(tempsegNo+"value", tempvalueAttr+";");
										//								updateEm.setAttribute(tempsegNo+"Code", tempcodeAttr+";");
										//								tempvalueAttr=tempvalueAttr+";";
										//								codeAttr=codeAttr+";";
										segvalueAttr=segvalueAttr+";";
									}
									primarySeg = primarySegment.replace(" ", "_");
									updateEm.setAttribute("CostCenter_TableName_ColumnName", tableName+"."+columnName);
									System.out.println("segvalueAttr===>"+segvalueAttr);
									System.out.println("name===>"+name);
									System.out.println("(segvalueAttr.contains(name))===>"+(segvalueAttr.contains(name)));
									if(segvalueAttr.contains(name)){

									}else{
										updateEm.setAttribute("value", segvalueAttr+name);
									}
									if(segNoValue.contains(segValueAttr4Edit)){

									}else{
										updateEm.setAttribute(segNo+"value", segNoValue+segValueAttr4Edit);
									}
									if(segNoCode.contains(code)){

									}else{
										updateEm.setAttribute(segNo+"Code", segNoCode+code);
									}


									//							updateEm.setAttribute(segNo+"value", segNoValue+segValueAttr4Edit);
									//							updateEm.setAttribute(segNo+"Code", segNoCode+code);
									updateEm.setAttribute("Primary_Segment", primarySeg);
									//							System.out.println("editcodeCombinationflag4Lastnode===>"+editcodeCombinationflag4Lastnode);
									updateEm.setAttribute("OverWrite_Code_Combination", String.valueOf(overwriteFlag4Data));
									updateEm.setAttribute("Create_Code_Combination4Data", String.valueOf(editcreateCodeCombinationflag4Lastnode));  //code change Vishnu 26Apr2014 
									//							updateEm.setAttribute("value", segvalueAttr+name);
									//							updateEm.setAttribute(segNo+"value", valueAttr+segValueAttr4Edit);
									//							updateEm.setAttribute(segNo+"Code", codeAttr+code);




								}
							}}
						else{

							//							System.out.println("name----->>>>"+name);
							updateEm.setAttribute("value", name);
						}
						Node updatedNd=(Node) updateEm;

						String StartDate = sdf1.format(HIERYDate);

						auditHT.put(0, lb.username);
						auditHT.put(1, StartDate);
						auditHT.put(2, hierarchy_ID);
						auditHT.put(3, "Modify");

						auditHT.put(4, Globals.getAttrVal4AttrName(parentNd, "ID"));
						auditHT.put(5, Globals.getAttrVal4AttrName(parentNd, "value"));
						auditHT.put(6, hryId);



						Hashtable afterHT=Globals.getAttributeNameandValHT(updatedNd);
						//						int i=doc.getDocumentElement().getChildNodes().item(0).Position();
						String nodeText=getConcatedString4AttrHT(beforHT);
						String afterNodeText=getConcatedString4AttrHT(afterHT);
						auditHT.put(7, nodeText);
						auditHT.put(8, afterNodeText);


						auditHT.put(9, "");
						auditHT.put(10, "");
						auditHT.put(11, "");
						auditHT.put(12, "");
						auditHT.put(13, "");
						auditHT.put(14, "");


						logChanges("Modify",auditHT,auditcoldetHT,conn);
						updatedNodeName=NodeName;
						System.out.println("Updated Successfully");
					}
				}
				Node updateNd=(Node)updateEm;
				updatedNodeID=updateNd.getAttributes().getNamedItem("ID").getNodeValue();
				Globals.writeXMLFile(doc, HIERARCHY_XML_DIR, hierarchyXmlFileName);	

				if(updateFrom.equalsIgnoreCase("FromHry")){
					String rootValue="";
					Node nd=Globals.getNodeByAttrVal(doc, "Hierarchy_Level", "Hierarchy_Name", namevalue);
					NodeList ndlist=nd.getChildNodes();
					for(int i=0;i<ndlist.getLength();i++){
						if(ndlist.item(i).getNodeType()==Node.ELEMENT_NODE && ndlist.item(i).getNodeName().equals("RootLevel")){
							rootValue=ndlist.item(i).getAttributes().getNamedItem("value").getNodeValue();
							break;
						}
					}
					//					System.out.println("nodelevelno===>"+nodelevelno+"rootValue===>"+rootValue);
					String SQL="DELETE FROM "+"WC_FLEX_HIERARCHY_D"+" WHERE "+"HIER"+nodelevelno+"_CODE='"+rootValue+"' AND HIER_CODE='"+hierarchy_ID+"'";

					PreparedStatement pre=conn.prepareStatement(SQL);
					pre.execute();
					pre.close();
					if(UPDATE_DB_FOR_EACH_NODE){
						Hashtable<Integer,String> valueHT=new Hashtable<>();
						Hashtable<Integer,String> BasicvalueHT=new Hashtable<>();

						Node nd1=Globals.getNodeByAttrVal(doc, "Hierarchy_Level", "Hierarchy_ID", hierarchy_ID);
						BasicvalueHT.put(0, String.valueOf(Globals.getMaxID(HIERARCHY_XML_DIR, hierarchyConfigFile,"Row_ID4Oracle", "ID")));
						BasicvalueHT.put(1, nd1.getAttributes().getNamedItem("Hierarchy_Category").getNodeValue());
						BasicvalueHT.put(2, "GL");
						BasicvalueHT.put(3, hierarchy_ID);
						BasicvalueHT.put(4, hierarchy_ID);
						BasicvalueHT.put(5, nd1.getAttributes().getNamedItem("Hierarchy_Name").getNodeValue());

						Node newHierarchyNd=Globals.getNodeByAttrVal(doc, "Hierarchy_Level", "Hierarchy_Name", name);
						NodeList newHierarchylist=newHierarchyNd.getChildNodes();
						for(int i=0;i<newHierarchylist.getLength();i++){
							if(newHierarchylist.item(i).getNodeType()==Node.ELEMENT_NODE && newHierarchylist.item(i).getNodeName().equals("RootLevel")){
								valueHT.put(Integer.parseInt(nodelevelno)-1, newHierarchylist.item(i).getAttributes().getNamedItem("value").getNodeValue());
								break;
							}
						}

						Node selectedNd=Globals.getNodeByAttrVal(doc, NodeName, "value", name);
						//					NodeList seleList=selectedNd.getChildNodes();
						String nodeName=selectedNd.getParentNode().getNodeName();
						String selectedNdID=selectedNd.getParentNode().getAttributes().getNamedItem("ID").getNodeValue();
						for(int i=Integer.parseInt(nodelevelno)-1;i>0;i--){
							selectedNd=selectedNd.getParentNode();
							valueHT.put(i-1, selectedNd.getAttributes().getNamedItem("value").getNodeValue());

						}
						//					System.out.println("valueHT===>"+valueHT);
						//					System.out.println("BasicvalueHT===>"+BasicvalueHT);
						Hashtable coldetHT=new Hashtable<>();
						String pdatatype="";
						Hashtable columnNameHT=Globals.getcolumnName();
						//			genarateFactsBean gfb=new genarateFactsBean("","");
						Hashtable coldetHTtemp=new Hashtable<>();
						for(int i=0;i<columnNameHT.size();i++){
							coldetHTtemp=gfb.getDatatypefromDBtable("WC_FLEX_HIERARCHY_D."+String.valueOf(columnNameHT.get(i)),conn,"","","");
							coldetHT.put(i, coldetHTtemp);
						}
						insertionofValues2DB(name, Integer.parseInt(nodelevelno), "Hierarchy", valueHT, BasicvalueHT, selectedNdID, nodeName,"Update",updatedNodeID,"",conn,updatedNodeName,doc,dimStatusHT,coldetHT,pdatatype,0);
					}
				}else{
					if(UPDATE_DB_FOR_EACH_NODE)
						Globals.updateSingleNode(NodeName, hryId);
				}
				this.infoMessage = "Node updated Successfully.";
			}
			else
			{
				System.out.println("Please Select HierarchyLevel to Update");
			}

			if(!NodeName.equals("RootLevel")){
				if(addAppendNode != null){


					Node hierarchyNode=Globals.getNodeByAttrVal(doc, "Hierarchy_Level","Hierarchy_ID", hierarchy_ID);
					if(hierarchyNode != null){
						Node hierarchyTypeNode = hierarchyNode.getAttributes().getNamedItem("RI_Hierarchy_Type");
						if(hierarchyTypeNode != null){
							String hierarchyType = hierarchyTypeNode.getNodeValue();
							if(hierarchyType.equals("Reporting") || hierarchyType.equals("Dependent")){
								RIConstrainsBean ri = new RIConstrainsBean();
								ri.implementationRI(hierarchy_ID,hryId,addAppendNode);
							}
						}
					}


				}
			}

			//code change Jayaramu 20MAY2014 for load hierarchy tree.
			Map<String, Object> viewMap = FacesContext.getCurrentInstance().getViewRoot().getViewMap();
			ReportsInvXMLTreeBean viewScopedBean = (ReportsInvXMLTreeBean) viewMap.get("reportsInvXMLTreeBean");
			viewScopedBean.getReportsForSession(hierarchyXmlFileName,hierarchy_ID,null,"FromHierTree");

		}catch(Exception e){
			e.printStackTrace();
		}

		System.out.println("Exiting: "
				+ new Exception().getStackTrace()[0].getClassName() + "."
				+ new Exception().getStackTrace()[0].getMethodName());

	}

	public void setHierarchyAL(ArrayList hierarchyAL) {
		this.hierarchyAL = hierarchyAL;
	}

	private String hierarchy_ID;



	public String getHierarchy_ID() {
		return hierarchy_ID;
	}

	public void setHierarchy_ID(String hierarchy_ID) {
		this.hierarchy_ID = hierarchy_ID;
	}

	boolean disableOption4hier = false;

	public boolean isDisableOption4hier() {
		return disableOption4hier;
	}
	public void setDisableOption4hier(boolean disableOption4hier) {
		this.disableOption4hier = disableOption4hier;
	}
	//start code change Jayaramu 06FEB14 for rendered edit popup if user select rollup type and click edit button hide all other popup(data,hierarchy) 
	public void setEditFlag() throws Exception{
		try{

			if(selectedRows.size()<=0){  // Code change Menaka 10MAR2014
				this.msg1="Please select a Node to Edit.";
				color4HierTreeMsg = "red";
				this.valid=false;
				return;
			}



			//			System.out.println("selectedRows---->>>"+selectedRows.size());

			if(selectedRows.size() > 0){
				cmngFromPage4RIConstraints="RI-RHDeleteProcess";
				this.msg1="";
				this.valid=true;
				//Start code change Jayaramu to render the parent node name 10FEB14
				//			Document doc=Globals.openXMLFile(HIERARCHY_XML_DIR, "HierachyLevel.xml");	
				PropUtil prop=new PropUtil();
				String XMLFileName=prop.getProperty("HIERARCHY_XML_FILE");  // code change Menaka 20MAR2014
				String configFileName = prop.getProperty("HIERARCHY_CONFIG_FILE"); 
				Document doc=Globals.openXMLFile(HIERARCHY_XML_DIR, XMLFileName);	
				String selectedNodeID=selectedRows.get(0).getID();
				String ParentNodeValue = "";
				this.name = selectedRows.get(0).getLevelName();
				System.out.println("%%"+selectedRows.get(0).getLevelName());
				Node nd=Globals.getNodeByAttrVal(doc, selectedRows.get(0).getLevelNode(), "ID", selectedNodeID);
				if(nd != null && (nd.getParentNode().getNodeType() == Node.ELEMENT_NODE) && nd.getParentNode() != null && nd.getNodeType() == Node.ELEMENT_NODE && !selectedRows.get(0).getLevelNode().equals("RootLevel")){
					ParentNodeValue = nd.getParentNode().getAttributes().getNamedItem("value").getTextContent().toString();
				}
				System.out.println("getHierType---->>>>"+selectedRows.get(0).getHierType());
				//End code change Jayaramu to render the parent node name 10FEB14

				if(selectedRows.get(0).getHierType().equals("Rollup")){
					flag4openDataPopup = "Rollup";
					this.editRollupFlag="true";
					this.editDataFlag="false";
					this.editHiryFlag="false";
					this.parentNodeName=ParentNodeValue;
					this.type="Rollup";
					this.popupleft="200";
					this.popuptop="200";

				}
				else if(selectedRows.get(0).getHierType().equals("Data")){

					this.editRollupFlag="false";
					this.editDataFlag="true";
					this.editHiryFlag="false";
					this.parentNodeName=ParentNodeValue;
					this.type="Data";
					this.popupleft="200";
					this.popuptop="0";
					FacesContext ctx = FacesContext.getCurrentInstance();
					ExternalContext extContext = ctx.getExternalContext();
					Map sessionMap = extContext.getSessionMap();
					HierarchyDBBean hdb = (HierarchyDBBean) sessionMap.get("hierarchyDBBean");
					//				hdb.getTableName1("FromEdit");
					Connection con = Globals.getDBConnection("Data_Connection");
					hdb.tableNameTM = hdb.getTableName1("FromEdit",con);
					hdb.codeandNameAL = new ArrayList<>();
					hdb.setValiMsg("");
					//				hdb.setSegmentName("Segment_Name");
					//				hdb.setCodeValue("Segment_Code");
					//				hdb.setNameValue("Segment_Value");

					//				hdb.setSegmentNameRendered("true");
					HeirarchyDataBean hdb1=new HeirarchyDataBean();;

					hdb.modeType = "EditMode";
					String levelID = selectedRows.get(0).getID();
					String nodeName = selectedRows.get(0).getLevelNode();				
					Node levelNode = Globals.getNodeByAttrVal(doc, nodeName, "ID", levelID);


					String segmentNo = levelNode.getAttributes().getNamedItem("Segment").getTextContent().toString();

					//				editcodeCombinationflag4Lastnode = Globals.getAttrVal4AttrName(levelNode, "Create_Code_Combination");
					if(codecombinationFlag.equalsIgnoreCase("DontCreateCodeCombination")){
						disableOption4hier = true;
						setEditcodeCombinationflag4Lastnode("");
						setCodeCombinationflag4Data("");
					}else if(codecombinationFlag.equalsIgnoreCase("CreateCodeCombinationInDim")){
						disableOption4hier = false;
						setEditcodeCombinationflag4Lastnode(Globals.getAttrVal4AttrName(levelNode, "Create_Code_Combination"));
						setCodeCombinationflag4Data(Globals.getAttrVal4AttrName(levelNode, "Create_Code_Combination4Data"));
					}else{
						disableOption4hier = true;
						setEditcodeCombinationflag4Lastnode("DuringFactGen");
						setCodeCombinationflag4Data("DuringFactGen");
					}
					//				editcreateCodeCombinationflag4Lastnode=Globals.getAttrVal4AttrName(levelNode, "Create_Code_Combination4Data");
					String overwriteFlag = "";
					overwriteFlag = Globals.getAttrVal4AttrName(levelNode, "OverWrite_Code_Combination");
					///////want to change//////
					hdb.whereClauseList = new ArrayList<>();
					hdb.selectedTableNameTM4DirectSQL = new TreeMap<>();
					hdb.selectedColumnNameTM4DirectSQL = new TreeMap<>();
					hdb.columnNameTM4DirectSQL = new TreeMap<>();
					hdb.directSQL = "";
					hdb.data4DirectSQLAL = new ArrayList<>();
					////////////
					if(Globals.getAttrVal4AttrName(levelNode, "Data_SubType").equalsIgnoreCase("DirectSQL")){
						hdb.setFlag4AddData("fromData");
						flag4openDataPopup = "DirectSQL";
						hdb.setCustomDirectSql(true);
						if(Globals.getAttrVal4AttrName(levelNode, "Flag4_ConfiguredSQL").equalsIgnoreCase("false")){
							hdb.enable4DirectSQL = true;
							hdb.customDirectSql = false;
							hdb.directSQL = Globals.getAttrVal4AttrName(levelNode, "Custom_SQL");
						}else{
							hdb.enable4DirectSQL = false;
							hdb.customDirectSql = true;
							hdb.directSQL = Globals.getAttrVal4AttrName(levelNode, "Normal_SQL");
						}
						Hashtable ht = new Hashtable<>();
						Collection c = hdb.getSegmentAL().keySet();
						Iterator itr = c.iterator();
						hdb.name4DirectSQL = Globals.getAttrVal4AttrName(levelNode, "value");
						hdb.value4DirectSQL = Globals.getAttrVal4AttrName(levelNode, "Code");
						codeCombinationflag4Data = Globals.getAttrVal4AttrName(levelNode, "Create_Code_Combination");

						hdb.setSelectedsegment1(Globals.getAttrVal4AttrName(levelNode, "Primary_Segment"));
						String[] selectedTable = Globals.getAttrVal4AttrName(levelNode, "Selected_TableName").split("#~#");
						for(int i=0;i<selectedTable.length;i++){
							hdb.selectedTableNameTM4DirectSQL.put(selectedTable[i], selectedTable[i]);
							hdb.tableNameTM1.put(selectedTable[i], selectedTable[i]);
							if(!hdb.SelectTableNameHT.containsValue(selectedTable[i]))
								hdb.SelectTableNameHT.put(hdb.SelectTableNameHT.size(), selectedTable[i]);
						}
						String[] selectedColumn = Globals.getAttrVal4AttrName(levelNode, "Selected_ColumnName").split("#~#");
						String selectClauseTable = "";
						for(int i=0;i<selectedColumn.length;i++){
							selectClauseTable = selectedColumn[i].split("\\.")[0];
							hdb.selectedColumnNameTM4DirectSQL.put(selectedColumn[i].split("\\.")[1], selectedColumn[i].split("\\.")[1]);
							hdb.selectColNameHT.put(i, selectedColumn[i]);
						}
						hdb.columnNameTM4DirectSQL = hdb.columnName1("", selectClauseTable, con);
						String[] whereClauseList = Globals.getAttrVal4AttrName(levelNode, "where_Caluse").split("#~#");
						hdb.joinColumnOperTM.put("=", "=");
						hdb.joinColumnOperTM.put("BETWEEN", "BETWEEN");
						hdb.joinColumnOperTM.put("<", "<");
						hdb.joinColumnOperTM.put(">", ">");
						hdb.joinColumnOperTM.put("!=", "!=");
						hdb.joinColumnOperTM.put("<=", "<=");
						hdb.joinColumnOperTM.put(">=", ">=");
						hdb.joinColumnOperTM.put("LIKE", "LIKE");
						hdb.joinColumnOperTM.put("IN", "IN");
						for(int i=0;i<whereClauseList.length;i++){
							System.out.println("whereClauseList[i]==>"+whereClauseList[i]);
							String[] whereClause = whereClauseList[i].split("~");

							String tableName = "";
							String columnName = "";
							String joinOper = "";
							String tartableName = "";
							String tarcolumnName = "";
							TreeMap srcColumnNameTM = new TreeMap<>();
							for(int j=0;j<whereClause.length;j++){
								System.out.println("whereClause==>"+whereClause[j]);
								if(whereClause[j].equals("")){
									continue;
								}
								if(j==0){
									tableName = whereClause[j].split("\\.")[0];
									srcColumnNameTM = hdb.columnName1("", tableName, con);
									columnName = whereClause[j].split("\\.")[1];
								}else if(j==1){
									joinOper =whereClause[j];
								}else{
									if(whereClause[j].contains(".")){
										tartableName = whereClause[j].split("\\.")[0];
										//									srcColumnNameTM = hdb.columnName1("", tartableName, con);
										tarcolumnName = whereClause[j].split("\\.")[1];
									}else if(whereClause[j].contains(" and ")){
										tartableName = whereClause[j].split(" and ")[0];
										tarcolumnName = whereClause[j].split(" and ")[1];
									}else{
										tartableName = whereClause[j];
										tarcolumnName = "";
									}
								}

							}
							HierarchydataBean hDataBean = new HierarchydataBean(tableName, hdb.selectedTableNameTM4DirectSQL, columnName, 
									srcColumnNameTM, joinOper, hdb.joinColumnOperTM, tartableName, tarcolumnName);
							hdb.whereClauseList.add(hDataBean);
						}

						hdb.selectedJoinType4Direct =  Globals.getAttrVal4AttrName(levelNode, "Join_Type");
						hdb.dispTable4Data = "none";
						String segment = Globals.getAttrVal4AttrName(levelNode, "Primary_Segment");
						String segNo = segment.substring(segment.lastIndexOf("_")+1);
						String[] segValue = Globals.getAttrVal4AttrName(levelNode, "seg"+segNo+"_value").split(";");
						String[] segCode = Globals.getAttrVal4AttrName(levelNode, "seg"+segNo+"_Code").split(";");
						hdb.setDispTable4Data("true");
						for(int i=0;i<segValue.length;i++){
							System.out.println("segCode[i] direct sql===>"+segCode[i]);
							System.out.println("segValue[i] direct sql===>"+segValue[i]);
							HierarchydataBean hydb = new HierarchydataBean(segCode[i], segValue[i]);
							hdb.data4DirectSQLAL.add(hydb);
						}
					}else if(Globals.getAttrVal4AttrName(levelNode, "Data_SubType").equalsIgnoreCase("fromData4DirectSQL")){
						hdb.setFlag4AddData("fromData");
						flag4openDataPopup = "DirectSQL";
						hdb.dispTable4Data = "block";
						hdb.setCustomDirectSql(true);
						if(Globals.getAttrVal4AttrName(levelNode, "Flag4_ConfiguredSQL").equalsIgnoreCase("false")){
							hdb.enable4DirectSQL = true;
						}else{
							hdb.enable4DirectSQL = false;
						}
						Hashtable ht = new Hashtable<>();
						Collection c = hdb.getSegmentAL().keySet();
						Iterator itr = c.iterator();
						hdb.name4DirectSQL = Globals.getAttrVal4AttrName(levelNode, "value").substring(0, Globals.getAttrVal4AttrName(levelNode, "value").lastIndexOf("("));
						hdb.value4DirectSQL = Globals.getAttrVal4AttrName(levelNode, "Code");
						codeCombinationflag4Data = Globals.getAttrVal4AttrName(levelNode, "Create_Code_Combination");

						hdb.setSelectedsegment1(Globals.getAttrVal4AttrName(levelNode, "Primary_Segment"));
						String[] selectedTable = Globals.getAttrVal4AttrName(levelNode, "Selected_TableName").split("#~#");
						for(int i=0;i<selectedTable.length;i++){
							hdb.selectedTableNameTM4DirectSQL.put(selectedTable[i], selectedTable[i]);
							hdb.tableNameTM1.put(selectedTable[i], selectedTable[i]);
							if(!hdb.SelectTableNameHT.containsValue(selectedTable[i]))
								hdb.SelectTableNameHT.put(hdb.SelectTableNameHT.size(), selectedTable[i]);
						}
						String[] selectedColumn = Globals.getAttrVal4AttrName(levelNode, "Selected_ColumnName").split("#~#");
						String selectClauseTable = "";
						if(!Globals.getAttrVal4AttrName(levelNode, "Selected_ColumnName").equals("")){
							for(int i=0;i<selectedColumn.length;i++){
								selectClauseTable = selectedColumn[i].split("\\.")[0];
								hdb.selectedColumnNameTM4DirectSQL.put(selectedColumn[i].split("\\.")[1], selectedColumn[i].split("\\.")[1]);
								hdb.selectColNameHT.put(i, selectedColumn[i]);
							}
						}
						hdb.columnNameTM4DirectSQL = hdb.columnName1("", selectClauseTable, con);
						String[] whereClauseList = Globals.getAttrVal4AttrName(levelNode, "where_Caluse").split("#~#");
						hdb.joinColumnOperTM.put("=", "=");
						hdb.joinColumnOperTM.put("BETWEEN", "BETWEEN");
						hdb.joinColumnOperTM.put("<", "<");
						hdb.joinColumnOperTM.put(">", ">");
						hdb.joinColumnOperTM.put("!=", "!=");
						hdb.joinColumnOperTM.put("<=", "<=");
						hdb.joinColumnOperTM.put(">=", ">=");
						hdb.joinColumnOperTM.put("LIKE", "LIKE");
						hdb.joinColumnOperTM.put("IN", "IN");
						if(!Globals.getAttrVal4AttrName(levelNode, "where_Caluse").equals("")){
							for(int i=0;i<whereClauseList.length;i++){
								System.out.println("whereClauseList[i]==>"+whereClauseList[i]);
								String[] whereClause = whereClauseList[i].split("~");

								String tableName = "";
								String columnName = "";
								String joinOper = "";
								String tartableName = "";
								String tarcolumnName = "";
								TreeMap srcColumnNameTM = new TreeMap<>();
								for(int j=0;j<whereClause.length;j++){
									System.out.println("whereClause==>"+whereClause[j]);
									if(j==0){
										tableName = whereClause[j].split("\\.")[0];
										srcColumnNameTM = hdb.columnName1("", tableName, con);
										columnName = whereClause[j].split("\\.")[1];
									}else if(j==1){
										joinOper =whereClause[j];
									}else{
										if(whereClause[j].contains(".")){
											tartableName = whereClause[j].split("\\.")[0];
											//									srcColumnNameTM = hdb.columnName1("", tartableName, con);
											tarcolumnName = whereClause[j].split("\\.")[1];
										}else if(whereClause[j].contains(" and ")){
											tartableName = whereClause[j].split(" and ")[0];
											tarcolumnName = whereClause[j].split(" and ")[1];
										}else{
											tartableName = whereClause[j];
											tarcolumnName = "";
										}
									}

								}
								HierarchydataBean hDataBean = new HierarchydataBean(tableName, hdb.selectedTableNameTM4DirectSQL, columnName, 
										srcColumnNameTM, joinOper, hdb.joinColumnOperTM, tartableName, tarcolumnName);
								hdb.whereClauseList.add(hDataBean);
							}
						}
						hdb.directSQL = Globals.getAttrVal4AttrName(levelNode, "Custom_SQL");
						hdb.selectedJoinType4Direct =  Globals.getAttrVal4AttrName(levelNode, "Join_Type");
						hdb.setSelectedsegment1(Globals.getAttrVal4AttrName(levelNode, "Primary_Segment"));
						String segment = Globals.getAttrVal4AttrName(levelNode, "Primary_Segment");
						String segNo = segment.substring(segment.lastIndexOf("_")+1);
						System.out.println("segNo===>"+segNo);
						String[] segValue = Globals.getAttrVal4AttrName(levelNode, "seg"+segNo+"_value").split(";");
						String[] segCode = Globals.getAttrVal4AttrName(levelNode, "seg"+segNo+"_Code").split(";");
						for(int i=0;i<segValue.length;i++){
							System.out.println("segCode[i]===>"+segCode[i]);
							System.out.println("segValue[i]===>"+segValue[i]);
							HierarchydataBean hydb = new HierarchydataBean(segCode[i], segValue[i]);
							hdb.data4DirectSQLAL.add(hydb);
						}
					}else{
						flag4openDataPopup = "Data";
						if(segmentNo.equals("0")){ //when segment zero its data else its segment
							hdb.setFlag4AddData("fromSegment");
							hdb.setSegmentName("");
							hdb.setCodeValue("Data_Code");
							hdb.setNameValue("Data_Value");
							hdb.setSegmentNameRendered("false");
							datasegRendered = "true";
							String code = levelNode.getAttributes().getNamedItem("Code").getTextContent().toString();
							String value = levelNode.getAttributes().getNamedItem("value").getTextContent().toString();


							hdb1 = new HeirarchyDataBean("",code,value,"","","","",primaryType,false,"");
							hdb.codeandNameAL.add(hdb1);

						}
						else{
							String segValueWithCode = "";
							hdb.setSegmentName("Segment_Name");
							hdb.setCodeValue("Segment_Code");
							hdb.setNameValue("Segment_Value");
							hdb.setSegmentNameRendered("true");
							hdb.setMsg4Editseg("");
							datasegRendered = "false";
							String primesegNo = "";
							String primaryseg = "";
							if(levelNode.getAttributes().getNamedItem("Primary_Segment") != null){
								primaryseg = levelNode.getAttributes().getNamedItem("Primary_Segment").getTextContent().toString();
							}
							if(!primaryseg.equals("")){
								primesegNo = primaryseg.substring(primaryseg.indexOf("_")+1,primaryseg.length());
							}
							String[] splitSegmentNo = new String[1000];
							Document hierConfXml = Globals.openXMLFile(HIERARCHY_XML_DIR, configFileName);
							Node nd1 = Globals.getNodeByAttrVal(doc, "Hierarchy_Level", "Hierarchy_ID", hierarchy_ID);
							NodeList ndList = nd1.getChildNodes();
							Node segSlqNd = null;
							for(int i=0;i<ndList.getLength();i++){
								if(ndList.item(i).getNodeType() == Node.ELEMENT_NODE && ndList.item(i).getNodeName().equalsIgnoreCase("Segment_SQLS")){
									segSlqNd = ndList.item(i);
								}
							}
							System.out.println("segSlqNd first--------->>>>"+segSlqNd);
							if(segSlqNd==null){
								NodeList segSlqNdList = hierConfXml.getElementsByTagName("Segment_SQLS");
								segSlqNd = segSlqNdList.item(0);
							}
							System.out.println("segSlqNd second--------->>>>"+segSlqNd.getNodeName());
							NodeList segList = segSlqNd.getChildNodes();
							overwriteFlag = Globals.getAttrVal4AttrName(levelNode, "OverWrite_Code_Combination");
							if(segmentNo.contains("$~$")){
								//					Document hierXml = Globals.openXMLFile(HIERARCHY_XML_DIR, hierarchyXmlFileName);
								Hashtable ht = new Hashtable<>();
								Collection c = hdb.getSegmentAL().keySet();
								Iterator itr = c.iterator();
								while(itr.hasNext()){
									String temp = (String)itr.next();
									//						System.out.println("itr.next()===>"+itr.next());
									//						System.out.println("String.valueOf(segmentAL.get(itr.next()))===>"+String.valueOf(segmentAL.get(String.valueOf(itr.next()))));
									ht.put(String.valueOf(hdb.getSegmentAL().get(temp)), temp);
								}
								System.out.println("ht--------->>>>"+ht);
								splitSegmentNo = segmentNo.split("\\$~\\$");  
								for(int i=0;i<splitSegmentNo.length;i++){

									String costCenterTableName = "";
									String costCenterColumnName = "";
									String segNo = splitSegmentNo[i].substring(splitSegmentNo[i].length()-1);	
									boolean isprime = false;

									if(primesegNo.equals(segNo)){
										isprime = true;
									}
									segNo = "seg"+segNo+"_";

									System.out.println("segNo--------->>>>"+splitSegmentNo[i]);
									System.out.println("segNoCode--------->>>>"+segNo+"Code");
									System.out.println("segNoCode--------->>>>"+segNo+"value");

									String segCode = levelNode.getAttributes().getNamedItem(segNo+"Code").getTextContent().toString();
									String segValue = levelNode.getAttributes().getNamedItem(segNo+"value").getTextContent().toString();
									System.out.println("segList.item(j)--------->>>>"+segList.getLength());
									for(int j=0;j<segList.getLength();j++){
										System.out.println("segList--------->>>>"+segList.item(j).getNodeName());
										if(segList.item(j).getNodeType() == Node.ELEMENT_NODE){
											String segNoTemp = segList.item(j).getNodeName().replace("Segment_SQL", "");
											if(segNoTemp.equals(splitSegmentNo[i].substring(splitSegmentNo[i].length()-1))){
												costCenterTableName = Globals.getAttrVal4AttrName(segList.item(j), "Table_Name");
												costCenterColumnName = Globals.getAttrVal4AttrName(segList.item(j), "Column_Name1");
											}
										}
									}
									System.out.println("costCenterTableName--------->>>>"+costCenterTableName);
									System.out.println("costCenterColumnName--------->>>>"+costCenterColumnName);
									String splitSegValue[] = segValue.split(";");
									String splitSegCode[] = segCode.split(";");
									for(int k=0;k<splitSegValue.length;k++){

										segValueWithCode = segValueWithCode+splitSegValue[k]+"("+splitSegCode[k]+");";

									}

									String[] CostCenter_TableName_ColumnName = Globals.getAttrVal4AttrName(levelNode, "CostCenter_TableName_ColumnName").split("\\.");
									if(overwriteFlag.equalsIgnoreCase("true")){
										if(isprime){
											overwriteFlag = "true";
										}else{
											overwriteFlag = "false";
										}
									}else{
										overwriteFlag = "false";
									}
									hdb1 = new HeirarchyDataBean(splitSegmentNo[i].replace("_", " "),segCode,segValue,"",segValueWithCode,costCenterTableName,costCenterColumnName,isprime,Boolean.valueOf(overwriteFlag),String.valueOf(ht.get(splitSegmentNo[i])));
									hdb.codeandNameAL.add(hdb1);
								}
							}else{

								splitSegmentNo[0] = segmentNo;
								//					for(int i=0;i<splitSegmentNo.length;i++){

								String costCenterTableName = "";
								String costCenterColumnName = "";
								//						String segNo = splitSegmentNo[i].substring(splitSegmentNo[i].length()-1);	
								//						segNo = "seg"+segNo+"_";
								//						
								//						System.out.println("segNo--------->>>>"+segNo);
								//						System.out.println("segNoCode--------->>>>"+segNo+"Code");
								System.out.println("segNoCode--------->>>>"+splitSegmentNo[0].replace("_", " "));

								for(int j=0;j<segList.getLength();j++){
									if(segList.item(j).getNodeType() == Node.ELEMENT_NODE){
										String segNoTemp = segList.item(j).getNodeName().replace("Segment_SQL", "");
										if(segNoTemp.equals(splitSegmentNo[0].substring(splitSegmentNo[0].length()-1))){
											costCenterTableName = Globals.getAttrVal4AttrName(segList.item(j), "Table_Name");
											costCenterColumnName = Globals.getAttrVal4AttrName(segList.item(j), "Column_Name1");
										}
									}
								}

								String segCode = levelNode.getAttributes().getNamedItem("Code").getTextContent().toString();
								String segValue = levelNode.getAttributes().getNamedItem("value").getTextContent().toString();


								String splitSegValue[] = segValue.split(";");
								String splitSegCode[] = segCode.split(";");
								for(int k=0;k<splitSegValue.length;k++){

									segValueWithCode = segValueWithCode+splitSegValue[k]+"("+splitSegCode[k]+");";

								}
								//					String[] CostCenter_TableName_ColumnName = Globals.getAttrVal4AttrName(levelNode, "CostCenter_TableName_ColumnName").split("\\.");
								//					String overwriteFlag = Globals.getAttrVal4AttrName(levelNode, "OverWrite_Code_Combination");
								Hashtable ht = new Hashtable<>();
								Collection c = hdb.getSegmentAL().keySet();
								Iterator itr = c.iterator();
								while(itr.hasNext()){
									String temp = (String)itr.next();
									//						System.out.println("itr.next()===>"+itr.next());
									//						System.out.println("String.valueOf(segmentAL.get(itr.next()))===>"+String.valueOf(segmentAL.get(String.valueOf(itr.next()))));
									ht.put(String.valueOf(hdb.getSegmentAL().get(temp)), temp);
								}

								//					if(overwriteFlag.equalsIgnoreCase("true")){
								//						if(isprime){
								//							overwriteFlag = "true";
								//						}else{
								//							overwriteFlag = "false";
								//						}
								//					}else{
								//						overwriteFlag = "false";
								//					}
								//					hdb1 = new HeirarchyDataBean(String.valueOf(ht.get(splitSegmentNo[0])),segCode,segValue,"",segValueWithCode,costCenterTableName,costCenterColumnName,true,Boolean.valueOf(overwriteFlag));
								hdb1 = new HeirarchyDataBean(splitSegmentNo[0].replace("_", " "),segCode,segValue,"",segValueWithCode,costCenterTableName,costCenterColumnName,true,Boolean.valueOf(overwriteFlag),String.valueOf(ht.get(splitSegmentNo[0])));
								hdb.codeandNameAL.add(hdb1);
								//					}
							}



						}
					}


				}
				else if(selectedRows.get(0).getHierType().equals("Hierarchy")){
					flag4openDataPopup = "Hierarchy";
					this.editRollupFlag="false";
					this.editDataFlag="false";
					this.editHiryFlag="true";
					this.parentNodeName=ParentNodeValue;
					this.type="Hierarchy";
					this.popupleft="200";
					this.popuptop="200";
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	//End code change Jayaramu 06FEB14

	boolean overRideCodeCombinationFlag;
	//start code change Jayaramu 04FEB14
	public void redirect(String pageName,String isAddnewHry,String hierarchy_ID,String HierarchyName,String hierarchyCreatedDate,String hierarchyModifiedDate,String hierarchyCategory,String hierCodeCombination,String hierarchyStatus){    // code change -- hierarchyStatus--- Menaka 15FEB2014 
		System.out.println("Entering: "
				+ new Exception().getStackTrace()[0].getClassName() + "."
				+ new Exception().getStackTrace()[0].getMethodName());

		Map<String, Object> viewMap = FacesContext.getCurrentInstance().getViewRoot().getViewMap();
		ReportsInvXMLTreeBean rIXTB = (ReportsInvXMLTreeBean) viewMap.get("reportsInvXMLTreeBean");

		try{
			hierarchyList= new ArrayList<>();  // code change Menaka 02MAY2014
			formID = "HRYTree";
			//			loadData2TreeBean ldb = new loadData2TreeBean();
			//			ldb.getReportsForSession(hierarchyXmlFileName, "runReports");
			if(isAddnewHry.equals("ReloadFromDataEditPopup") || isAddnewHry.equals("Reloadload")){
				selectedRows =  new ArrayList<ReportTree>(); 
			}
			this.msg="";
			this.msg1="";
			FacesContext ctx = FacesContext.getCurrentInstance();
			ExternalContext extContext = ctx.getExternalContext();
			Map sessionMap = extContext.getSessionMap();
			HierarchyDBBean hdb = (HierarchyDBBean) sessionMap.get("hierarchyDBBean");
			HeaderBean heb = (HeaderBean) sessionMap.get("headerBean");
			LoginBean lgnbn = (LoginBean) sessionMap.get("loginBean");
			PropUtil prop=new PropUtil();
			String xmlDir = prop.getProperty("HIERARCHY_XML_DIR");
			String xmlLevelFileName = prop.getProperty("HIERARCHY_XML_FILE");
			Document doc = Globals.openXMLFile(xmlDir, xmlLevelFileName);
			if(pageName.equals("Hierarchytree.xhtml")){
				formName4OpenAddSegPopup="HRYTree";
				cmgFromViewVersion="HRYTree";  // code change Menaka 03APR2014
				cmgFromFactpage="HRYTree"; // code change Menaka 07APR2014
			}

			if(pageName.equals("FactPopup.xhtml")){
				cmgFromFactpage="factConfig";
			}
			listOfUsersMsg = "";
			if(isAddnewHry.equals("addNewHrytrue"))
			{
				setEditHierarchy(false); // code change Menaka 14FEB2014
				this.flagNew="newAdded";
				this.hierarchy_ID= String.valueOf(Globals.getMaxID(HIERARCHY_XML_DIR, hierarchyConfigFile,"Hierarchy_Level_ID", "ID"));
				renderValue4SessionInfo = "false";
				renderValue4SessionInfo1 = "true";
				this.hierarchyName = "";  // code change Menaka 12FEB2014
				this.hierarchyCategory=""; //  code change Menaka 21FEB2014
				this.status="Independent";
				this.hirename="";
				this.parentNodeName="";
				this.type="";
				codecombinationFlag="DontCreateCodeCombination";
				this.disableDataNHier=true;  // code change Menaka 20MAR2014
				versionNo = "Master";
				auditDisable = "true";
				loadReferenceHierarchyFromHIRXML();
				auditEnable = "false";
				cmngFromPage4RIConstraints = "AdminRulepopup";
				this.wfUniqueID= Globals.getMaxID(HIERARCHY_XML_DIR, hierarchyConfigFile,"WF_Unique_ID", "ID");
				isAdminConfigureRIConstraints = false;
				Document confDoc = Globals.openXMLFile(HIERARCHY_XML_DIR, hierarchyConfigFile);
				NodeList catNdList = confDoc.getElementsByTagName("Hierarchy_Category");
				Node catNd = catNdList.item(0);
				NodeList catsNdList = catNd.getChildNodes();
				for(int i=0;i<catsNdList.getLength();i++){
					if(catsNdList.item(i).getNodeType() == Node.ELEMENT_NODE){
						hierarchyCategoryTM.put(catsNdList.item(i).getTextContent(), catsNdList.item(i).getTextContent());
					}
				}
			}
			else if(isAddnewHry.equals("addExistsHry"))
			{
				setEditHierarchy(true);   // code change Menaka 14FEB2014
				this.disableDataNHier=false;  // code change Menaka 20MAR2014
				this.hierarchy_ID=hierarchy_ID;
				renderValue4SessionInfo = "true";
				this.flagNew="alreadyadded";
				renderValue4SessionInfo1= "false";
				this.hierarchyCreatedDate = hierarchyCreatedDate;
				this.hierarchyModifiedDate = hierarchyModifiedDate;
				this.hierarchyName = HierarchyName;
				this.subHierarchyName=HierarchyName;   // code change Menaka 05APR2014
				isAdminConfigureRIConstraints = true;
				this.wfUniqueID= Globals.getMaxID(HIERARCHY_XML_DIR, hierarchyConfigFile,"WF_Unique_ID", "ID");
				if(this.subHierarchyName.length()>17){
					subHierarchyName=subHierarchyName.substring(0,15)+"...";
				}
				this.hirename=HierarchyName;//Code Change Jayaramu 22FEB14
				this.hierarchyCategory = hierarchyCategory;
				this.hrchyCategory = hierarchyCategory;
				this.subHrchyCategory=hierarchyCategory;  // code change Menaka 05APR2014
				if(subHrchyCategory.length()>10){
					subHrchyCategory=subHrchyCategory.substring(0,8)+"...";
				}
				cmngFromPage4RIConstraints = "RI-RHDeleteProcess";
				this.status=hierarchyStatus;  // code change Menaka 15FEB2014
				//				this.status= "Approved123";   //  Code change Menaka 13FEB2014 - need to remove the hard code - get status from XML
				loadReferenceHierarchyFromHIRXML();
				//				if(this.status.equals("Draft")){
				//					this.statusColor= "blue";
				//				}else if(this.status.equals("Approved")||this.status.equals("Published")){
				//					this.statusColor= "green";
				//				}else{
				//					this.statusColor= "red";
				//				}
				getVersionNumber(hierarchy_ID);
				this.codecombinationFlag=hierCodeCombination;
				versionNo = "Master";

				Node nd = Globals.getNodeByAttrVal(doc, "Hierarchy_Level", "Hierarchy_ID", hierarchy_ID);
				if(!Globals.getAttrVal4AttrName(nd, "Disable_Toggle").equals("") && !Globals.getAttrVal4AttrName(nd, "Enable_Toggle").equals("")){
					auditDisable = Globals.getAttrVal4AttrName(nd, "Disable_Toggle");
					auditEnable  = Globals.getAttrVal4AttrName(nd, "Enable_Toggle");
				}else{
					auditDisable = "true";
					auditEnable = "false";
				}
				Document confDoc = Globals.openXMLFile(HIERARCHY_XML_DIR, hierarchyConfigFile);
				NodeList catNdList = confDoc.getElementsByTagName("Hierarchy_Category");
				Node catNd = catNdList.item(0);
				NodeList catsNdList = catNd.getChildNodes();
				for(int i=0;i<catsNdList.getLength();i++){
					if(catsNdList.item(i).getNodeType() == Node.ELEMENT_NODE){
						hierarchyCategoryTM.put(catsNdList.item(i).getTextContent(), catsNdList.item(i).getTextContent());
					}
				}


				Node rulenodeDH=Globals.getNodeByAttrVal(doc, "RI-Rules", "Hierarchy_ID", hierarchy_ID);	//code change Vishnu 04Aug2014
				Hashtable ruleHT = new Hashtable<>();

				if(rulenodeDH!=null){
					ruleHT=Globals.getAttributeNameandValHT(rulenodeDH);
					ArrayList l=new ArrayList();
					Enumeration en=ruleHT.keys();
					while(en.hasMoreElements()){

						String key=(String)en.nextElement();
						if(!key.equals("Hierarchy_ID")){
							//							System.out.println("--->"+key);
							String value=(String)ruleHT.get(key);
							if(value.equals("true")){
								l.add(key);
							}


						}

					}
					rulesSize = l.size();
				}	//end code change Vishnu 04Aug2014
			}else if(isAddnewHry.equals("DataReload")){

			}
			getRIContraintsRulesFromRICXml();
			this.hierarchyXmlFileName=hierarchyXmlFileName;

			if(status.equalsIgnoreCase("Dependent")){
				riMessage = "This is a Dependent Hierarchy. As you have changed the Referential Integrity constraints configuration, you need to ensure the Hierarchy still conforms to the updated constraints list. Do you want to validate the Hierarchy now?";
			}else if(status.equalsIgnoreCase("Reporting")){
				riMessage = "This is a Reporting Hierarchy. As you have changed the Referential Integrity constraints configuration, you need to ensure the Hierarchy still conforms to the updated constraints list. Do you want to validate the Hierarchy now?";
			}else if(status.equalsIgnoreCase("Reference")){
				riMessage = "This Reference Hierarchy has Dependant Hierarchies. Do you want to Validate them now?";
			}
			reGenerateMess = "This will delete the underlying Hierarchy data (Dimension) for the selected Hierarchy and regenerate it again. Do you wish to proceed?";
			///////////////////////Start code change pandian 17MAR2014 
			//			String hierID="1";
			//			String hierarchyName="SG & A";
			//			String LoginID="rbid";
			//			String processStatus="";
			//			String currentStageName="";
			//			String currentStageNo="";
			//			String totalNoStages="";
			//			String currentStageStatus="";
			//			String currentUserStatus="";
			//			String currentUserEmailID="";
			if(pageName.equals("Hierarchytree.xhtml")){

				setStageMessageinFront(hierarchy_ID);
			}// end of if(pageName.equals("Hierarchytree.xhtml")){
			///////////////////////End code change pandian 17MAR2014




			if((pageName.equals("Hierarchytree.xhtml") && isAddnewHry.equals("addExistsHry")) || isAddnewHry.equals("reload")){

				if(rIXTB != null){
					System.out.println("Enter In the view scoped ACCESS=========>>>>>>>>");
					rIXTB.getReportsForSession(hierarchyXmlFileName, hierarchy_ID, null, "");
				}else{
					System.out.println("Enter In the NON view scoped ACCESS=========>>>>>>>>");
					ReportsInvXMLTreeBean rpt = new ReportsInvXMLTreeBean(hierarchyXmlFileName, hierarchy_ID,null,"");
					rpt.getReportsForSession(hierarchyXmlFileName, hierarchy_ID,null,"");
				}
				System.out.println("ENTER IN TEE LOAD------>>>>>");
			}

			HeaderBean hb=new HeaderBean();
			hb.processMenu(pageName);

		}catch(Exception e){
			e.printStackTrace();

		}

		System.out.println("Exiting: "
				+ new Exception().getStackTrace()[0].getClassName() + "."
				+ new Exception().getStackTrace()[0].getMethodName());
	}



	public void setStageMessageinFront(String Hierarchy_ID){

		System.out.println("Entering: " + new Exception().getStackTrace()[0].getClassName() + "."
				+ new Exception().getStackTrace()[0].getMethodName());

		try{
			System.out.println("==========================>Set User Status On Front Page **** Start"); 

			LoginProcessManager  log=new LoginProcessManager();

			// CODE CHANGE PANDIAN 21Mar2014 disable/enable icons
			FacesContext ctx1 = FacesContext.getCurrentInstance();
			ExternalContext extContext1 = ctx1.getExternalContext();
			Map sessionMap1 = extContext1.getSessionMap();
			SecurityBean secbn = (SecurityBean) sessionMap1.get("securityBean");
			LoginBean lgnbn = (LoginBean) sessionMap1.get("loginBean");

			DashboardBean dshbn = (DashboardBean) sessionMap1.get("dashboardBean");
			String dashboardURL= dshbn.getUrlParameter();
			String dashboardEditURL= dshbn.getEditwrkflowflag();
			String currentLogin="";
			String currentLoginKEY="";
			
			if(dashboardURL.equalsIgnoreCase("true") || dashboardEditURL.equalsIgnoreCase("true")) {
				
				currentLogin=dshbn.getUrlLogin();
				currentLoginKEY=dshbn.getUrlcus_key();
				
			}else {
				
				currentLogin=lgnbn.username;
				currentLoginKEY=lgnbn.getCustomerKey();
			}
			
			
			
			
			
			String userName=currentLogin; //userLoginId
			Hashtable LoginDetailsHT=log.getLoginDetails(userName, currentLoginKEY);
			System.out.println("User Login ID "+userName+" Login Details "+LoginDetailsHT);
			String accessType=(String)LoginDetailsHT.get("Access_Type");
			String accessendDate=(String)LoginDetailsHT.get("Access_End_Date");
			String AccessUniqID=(String)LoginDetailsHT.get("Access_Unique_ID");
			String emailID=(String)LoginDetailsHT.get("Email_ID");




			this.memberloginID=userName;
			this.accessUniqID=AccessUniqID;

			System.out.println("LoginDetailsHT.get(0)=========>>>"+(LoginDetailsHT.get("User_ID")));

			if (!this.urlGlbParameter.equalsIgnoreCase("true")) {
			if(memberloginID.equals(LoginDetailsHT.get("User_ID"))){  // code change Menaka 26MAR2014
				accessType="Enabled";
			}
			}


			if(accessType.equals("Disabled")) {// if user disabled by admin user can not process						
				//		this.commonStatus="Access Type : Disabled. Please contact Adminstration";
				this.commonStatus="None (Access type : disabled).";  // code change Menaka 31MAR2014
				System.out.println("Access Type : Disabled Please contact Adminstration");
				this.memberStatus="NA";
				this.memberMailID="NA";
				this.currentStatgeName="NA";
				this.currentStageNumb="NA";
				this.totalStageNum="NA";	   
				this.stageStatus="NA";

				this.currentprocessMember="NA"; // code change pandian 21APR2014 add member who is process and set his Role
				this.currentprocessMemberRole="NA";
				this.wfmemberAcessType="NA";

				this.renderstatus4compltd="false"; 
				this.renderstatus4Rejected="false";
				this.renderstatus4Approved="false";
				this.renderstatus4Wip="false";

				// CODE CHANGE PANDIAN 21Mar2014 disable/enable icons
				secbn.setAlliconObject("AllDisable",secbn);
				System.out.println("All icons disabled as the Member access is disabled. Please contact Administrator.");


				//		return;
			}else{

				boolean isneeddisableallicons=false;	

				boolean isFinalStageCompleted=false;
				String finalStageNo="";

				try{ // check current Date and Accessing Date 
					PropUtil prop=new PropUtil();
					String	DateFormat=prop.getProperty("DATE_FORMAT");
					Date currentDateNow=new Date();

					if(!memberloginID.equals(LoginDetailsHT.get("User_ID")) && false){		//code change Vishnu 11May2018  // code change Menaka 26MAR2014

						SimpleDateFormat sdf = new SimpleDateFormat(DateFormat); 
						String currentformatDate=sdf.format(currentDateNow);

						Date accessEnddate = sdf.parse(accessendDate);  //1
						Date currentdate = sdf.parse(currentformatDate);	 //2

						if(currentdate.equals(accessEnddate)){
							System.out.println("Access End Date "+accessEnddate+ " Equals user Login Date "+currentdate);
						}else if(currentdate.before(accessEnddate)){
							System.out.println("user Login Date "+currentdate+"is before Access End Date "+accessEnddate);
						}else if(currentdate.after(accessEnddate)){
							System.out.println("user Login Date "+currentdate+"is After Access End Date "+accessEnddate);
							//	        		this.commonStatus="Accessing End Date :"+accessendDate+" You cannot Process. Please contact Adminstration";	 
							this.commonStatus="None (Accessing end date :"+accessendDate+" You cannot process).";	 // code change Menaka 31MAR2014
							//	        		return;
						}

					}
					Hashtable WFNodeDetailsHT=Globals.getWFNodeDetails(Hierarchy_ID, "",true,AccessUniqID);
					System.out.println("WorkFLow Node Value  WFNodeDetailsHT "+WFNodeDetailsHT);
					if(WFNodeDetailsHT.size()==0){  // if work flow is null 
						System.out.println("WorkFLow Node is Empty WFNodeDetailsHT "+WFNodeDetailsHT);

						this.totalStageNum="NA";
						this.stageStatus="NA";
						this.currentStatgeName="NA";
						this.currentStageNumb="NA";

						this.memberMailID="NA";
						this.memberStatus="NA";

						this.currentprocessMember="NA"; // code change pandian 21APR2014 add member who is process and set his Role
						this.currentprocessMemberRole="NA";
						this.wfmemberAcessType="NA";

						this.commonStatus="None (Workflow is not created yet).";
						this.renderstatus4compltd="false"; 
						this.renderstatus4Rejected="false";
						this.renderstatus4Approved="false";
						this.renderstatus4Wip="false";


						// CODE CHANGE PANDIAN 21Mar2014 disable/enable icons
						isneeddisableallicons=true;	
						System.out.println("Disabled All icons because the Work FLow did not created yet");

					}else{ // Pandian 18MAR2014
						String currentStageName="";
						if(WFNodeDetailsHT.get("Current_Stage_Name")==null){
							currentStageName="NA";
						}else{
							currentStageName=(String)WFNodeDetailsHT.get("Current_Stage_Name");
						}

						// code change pandian 21APR2014 add member who is process and set his Role start
						String currentprocessMember1="";
						if(WFNodeDetailsHT.get("currentprocessMember")==null){
							currentprocessMember1="NA";
						}else{
							currentprocessMember1=(String)WFNodeDetailsHT.get("currentprocessMember");
						}

						String currentprocessMemberRole1="";
						if(WFNodeDetailsHT.get("currentprocessMemberRole")==null){
							currentprocessMemberRole1="NA";
						}else{
							currentprocessMemberRole1=(String)WFNodeDetailsHT.get("currentprocessMemberRole");
						}

						String wfmemberAcessType1="";
						if(WFNodeDetailsHT.get("wfmemberAcessType")==null){
							wfmemberAcessType1="NA";
						}else{
							wfmemberAcessType1=(String)WFNodeDetailsHT.get("wfmemberAcessType");
						}
						// code change pandian 21APR2014 add member who is process and set his Role end

						String currentStageNo="";	 
						if(WFNodeDetailsHT.get("Current_Stage_No")==null){
							currentStageNo="NA";
						}else{
							currentStageNo=(String)WFNodeDetailsHT.get("Current_Stage_No");	
						}
						if(WFNodeDetailsHT.get("Total_No_Stages")==null){
							this.totalStageNum="NA";	
						}else{
							String totalnoOfstage=(String)WFNodeDetailsHT.get("Total_No_Stages");
							this.totalStageNum=totalnoOfstage;	
							int tot=Integer.parseInt(totalnoOfstage);
							int pre=tot-1;	
							if(!currentStageNo.equals("Completed") && !currentStageNo.equalsIgnoreCase("Cancel") && !currentStageNo.equalsIgnoreCase("Rules Failed")){
								int curstage=Integer.parseInt(currentStageNo);

								LoginProcessManager lgn=new LoginProcessManager();
								Hashtable stageDetailsHT=lgn.getStageDetails(Hierarchy_ID, currentStageNo,"");
								Hashtable mssgeDetailsHT=(Hashtable)stageDetailsHT.get("MessagedetHT");

								int statusnum=0;
								this.renderstatus4compltd="false"; 
								this.renderstatus4Rejected="false";
								this.renderstatus4Approved="false";
								this.renderstatus4Wip="false";
								for(int i=0;i<mssgeDetailsHT.size();i++){
									statusnum=i+1;
									String stmsg="";
									if(statusnum==mssgeDetailsHT.size()){
										stmsg=(String)mssgeDetailsHT.get("Final");
									}else{
										stmsg=(String)mssgeDetailsHT.get(String.valueOf(statusnum));
									}  

									System.out.println("stmsg->"+stmsg);

									if(stmsg.equals("WIP")){
										this.renderstatus4Wip="true";
									}else  if(stmsg.equals("Rejected")){
										this.renderstatus4Rejected="true";
									}else  if(stmsg.equals("Approved")){
										this.renderstatus4Approved="true";
									}else  if(stmsg.equals("Completed")){
										this.renderstatus4compltd="true"; 
									}


								}

								// 
								//	        			if(curstage==tot){
								//	        				this.renderstatus4compltd="false"; 
								//	        				this.renderstatus4Rejected="true";
								//	        				this.renderstatus4Approved="true";
								//	        				this.renderstatus4Wip="true";
								//	        			}else if(curstage==pre){
								//	        				this.renderstatus4compltd="false"; 
								//	        				this.renderstatus4Rejected="true";
								//	        				this.renderstatus4Approved="true";
								//	        				this.renderstatus4Wip="true";
								//	        			}else{
								//	        				this.renderstatus4compltd="true"; 
								//	        				this.renderstatus4Rejected="false";
								//	        				this.renderstatus4Approved="false";
								//	        				this.renderstatus4Wip="true";
								//	        			}

							}


						}
						if(WFNodeDetailsHT.get("Current_Stage_Status")==null){
							this.stageStatus="NA";
						}else{
							this.stageStatus=(String)WFNodeDetailsHT.get("Current_Stage_Status");
						}


						Hashtable UserDetailsHT=new Hashtable();
						UserDetailsHT=(Hashtable)WFNodeDetailsHT.get("UserDetailsHT");
						this.currentStatgeName=currentStageName;
						this.currentStageNumb=currentStageNo;
						this.currentprocessMember=currentprocessMember1;  // code change pandian 21APR2014 add member who is process and set his Role
						this.currentprocessMemberRole=currentprocessMemberRole1;
						this.wfmemberAcessType=wfmemberAcessType1;
						if(currentStageNo.equals("Completed") || currentStageNo.equalsIgnoreCase("Cancel") || currentStageNo.equalsIgnoreCase("Rules Failed")){
							//		        		this.commonStatus="All Stages are processed and closed. Please contact your Adminstrator.";	 
							this.commonStatus="None (All stages are processed and closed).";	 // code change Menaka 31MAR2014
							this.memberStatus="NA";
							this.memberMailID="NA";
							this.stageStatus="Completed";


							this.renderstatus4compltd="false"; 
							this.renderstatus4Rejected="false";
							this.renderstatus4Approved="false";
							this.renderstatus4Wip="false";


							isneeddisableallicons=true;	
							System.out.println("Disabled All icons because All the Stage are Closed");//				    	
							//				    	currentStageNo=this.totalStageNum;//Code change Gokul 14APR2014				    	
							isFinalStageCompleted=true;
							finalStageNo=totalStageNum;


							//		        		System.out.println(processStatus);
							//		        		return;
						}else{
							if(UserDetailsHT==null){
								this.memberStatus="NA";
								this.memberMailID=emailID;
							}else{
								if(UserDetailsHT.get("User_Status")==null){
									this.memberStatus="NA";
								}else{  //code change pandian 19MAR2014
									String memberstatuss=(String)UserDetailsHT.get("User_Status");
									this.memberStatus=memberstatuss;
									LoginProcessManager lgn=new LoginProcessManager();
									String isstatusAvaible=lgn.getFinalMsg(Hierarchy_ID,memberstatuss);


									if(isstatusAvaible.equals("true")){
										isneeddisableallicons=true;	
										System.out.println("Disabled All icons because MemberStatus is Completed / Approved");

									}

									//			        			if(memberstatuss.equals("Completed") || memberstatuss.equals("Approved")){
									//			        				isneeddisableallicons=true;	
									//							    	System.out.println("Disabled All icons because MemberStatus is Completed / Approved");
									//			        				
									//			        			}
								}

								if(UserDetailsHT.get("E-mail")==null){
									this.memberMailID=emailID;
								}else{
									this.memberMailID=(String)UserDetailsHT.get("E-mail");	
								}
							}



							this.currentStatgeName=currentStageName;
							this.currentStageNumb=currentStageNo;
							this.currentprocessMember=currentprocessMember1;  // code change pandian 21APR2014 add member who is process and set his Role
							this.currentprocessMemberRole=currentprocessMemberRole1;
							this.wfmemberAcessType=wfmemberAcessType1;
							this.commonStatus="";
						}
						// check all process (Serial are parellel processing )
						System.out.println("Hierarchy_ID: "+Hierarchy_ID+"; hierarchyName: "+hierarchyName+"; currentStageNo: "+currentStageNo+"; AccessUniqID: "+AccessUniqID);
						String previlge=(String)LoginDetailsHT.get("Super_Privilege_Admin"); // code change pandian 28Mar2013
						if(AccessUniqID!=null && previlge.equals("false")){  // code change Menaka 27MAR2014
							Hashtable NextProcessHT=new Hashtable();
							if(isFinalStageCompleted){  // serach all stage for this current member
								NextProcessHT=log.setStatusMsg4EMP(Hierarchy_ID,hierarchyName, finalStageNo, AccessUniqID, "","",AccessUniqID,true);
							}else{
								NextProcessHT=log.setStatusMsg4EMP(Hierarchy_ID,hierarchyName, currentStageNo, AccessUniqID, "","",AccessUniqID,true);
							}

							if(NextProcessHT.size()==0){
								//		        		this.commonStatus="Member cannot process this Hierarchy. Please contact your Adminstrator.";
								this.commonStatus="None (Member cannot process this hierarchy).";  // code change Menaka 31MAR2014
								this.renderstatus4compltd="false"; 
								this.renderstatus4Rejected="false";
								this.renderstatus4Approved="false";
								this.renderstatus4Wip="false";

								isneeddisableallicons=true;	
								System.out.println("Disabled All icons because the User Can not Process NextProcessHT"+NextProcessHT);
								//		        		System.out.println(processStatus);
								//		        		return;
							}else{

								Boolean loginProcess=(Boolean)NextProcessHT.get("LoginProcess");
								if(loginProcess){ // user can process to next
									this.commonStatus="None (Member can process the workflow).";
									this.commonStatus="";

								}else{ // user cannot Process
									String indicate=(String)NextProcessHT.get("indicate");
									this.commonStatus=indicate;

									this.renderstatus4compltd="false"; 
									this.renderstatus4Rejected="false";
									this.renderstatus4Approved="false";
									this.renderstatus4Wip="false";

									isneeddisableallicons=true;	
									System.out.println("Disabled All icons because indicate :"+indicate);

									//					    	//Start Code Change Gokul 14APR2014 for Showing the buttons for final Stage Users
									//					    	if(currentStageNo.equalsIgnoreCase("Completed")){
									//					    		String role=(String)WFNodeDetailsHT.get("MemberRole");
									//					    		if(role!=null && !role.equalsIgnoreCase("")){
									//					    			secbn.setAlliconObject(role,secbn);	
									//					    			
									//					    		}
									//					    	}
									////					    	End Code Change Gokul 14APR2014 for Showing the buttons for final Stage Users


								}

							}

							//////
							// CODE CHANGE PANDIAN 21Mar2014 disable/enable icons
							if(isneeddisableallicons){			        		
								System.out.println("Disabled All icons********isneeddisableallicons :"+isneeddisableallicons);
								secbn.setAlliconObject("AllDisable",secbn);	

								if(WFNodeDetailsHT.get("MemberRole")!=null){
									String Role=(String)WFNodeDetailsHT.get("MemberRole");	

									if(Role.equals("Admin")){
										this.commonStatus="None (Member logged in with admin role).";

									}else if(Role.equals("public")){
										this.commonStatus="None ("+commonStatus+" logged in as public)";

									}
									System.out.println("aDMIN===>1"+Role);
									secbn.setAlliconObject(Role,secbn);					    		

									//code change vishnu for hide DH and Reporting hierarchy
									if(!Role.equals("public")){
										
										hideHierarchyDHNREP(Role,secbn,lgnbn,dshbn);	
										
									}



								}

								this.renderstatus4compltd="false"; 
								this.renderstatus4Rejected="false";
								this.renderstatus4Approved="false";
								this.renderstatus4Wip="false";

							}else{
								System.out.println("WFNodeDetailsHT.get(MemberRole)"+WFNodeDetailsHT.get("MemberRole"));

								if(WFNodeDetailsHT.get("MemberRole")!=null){
									String Role=(String)WFNodeDetailsHT.get("MemberRole");	

									if(Role.equals("Admin")){
										this.commonStatus="None (Member logged in with admin role).";

									}else if(Role.equals("public")){
										this.commonStatus="None ("+commonStatus+" logged in as public)";

									}
									//				    		System.out.println("aDMIN===>"+Role);

									secbn.setAlliconObject(Role,secbn);	

									//code change vishnu for hide DH and Reporting hierarchy
									if(!Role.equals("public")){
										hideHierarchyDHNREP(Role,secbn,lgnbn,dshbn);	
									}

									if(currentStageNo.equals(totalStageNum)){
										secbn.setReGenerateHierarchy("false");
										secbn.setGenerateData("false");
										secbn.setGenerateFact("false");
									}

								}	
							}

						}



						//		        	else if(WFNodeDetailsHT.get("MemberRole")==null){
						//			    		this.currentStatgeName="NA";
						//			    		
						//			    	}


					}

					allStages=getAllStageNames(Hierarchy_ID);//Code Change Gokul 07APR2014

					fullCommonStatus=commonStatus;
					fullCurrentprocessMember=currentprocessMember; // code change Menaka 23APR2014

					if(currentprocessMember.length()>8){
						currentprocessMember=currentprocessMember.substring(0,6).concat("...");
					}

					if(currentStatgeName.equals("NA")&&currentprocessMember.equals("NA")&&stageStatus.equals("NA")){

						if(commonStatus.length()>25){  // code change Menaka 31MAR2014  // 10APR2014
							commonStatus=commonStatus.substring(0,23).concat("...");
						}
					}else{
						if(commonStatus.length()>13){  // code change Menaka 31MAR2014  // 10APR2014
							commonStatus=commonStatus.substring(0,11).concat("...");
						}
					}





				}catch(Exception ex){
					ex.printStackTrace();
				}

			}

			System.out.println("==========================>Set User Status On Front Page **** End");

		}catch(Exception e){
			e.printStackTrace();

		}

		System.out.println("Exiting: "
				+ new Exception().getStackTrace()[0].getClassName() + "."
				+ new Exception().getStackTrace()[0].getMethodName());
	}

	
	public void redirectWF(String pageName,String isAddnewHry,String workflow_ID,String document_ID,String workflowName,String documentName,String createdBy,String createdDate,String hierarchyStatus,String due_Date)
	{    // code change -- hierarchyStatus--- Menaka 15FEB2014 
		System.out.println("Entering: "
				+ new Exception().getStackTrace()[0].getClassName() + "."
				+ new Exception().getStackTrace()[0].getMethodName());


		Map<String, Object> viewMap = FacesContext.getCurrentInstance().getViewRoot().getViewMap();
		ReportsInvXMLTreeBean rIXTB = (ReportsInvXMLTreeBean) viewMap.get("reportsInvXMLTreeBean");

		try{
			hierarchyList= new ArrayList<>();  // code change Menaka 02MAY2014
			formID = "HRYTree";
			//			loadData2TreeBean ldb = new loadData2TreeBean();
			//			ldb.getReportsForSession(hierarchyXmlFileName, "runReports");
			/*if(isAddnewHry.equals("ReloadFromDataEditPopup") || isAddnewHry.equals("Reloadload")){
				selectedRows =  new ArrayList<ReportTree>(); 
			}*/
			this.msg="";
			this.msg1="";
			FacesContext ctx = FacesContext.getCurrentInstance();
			ExternalContext extContext = ctx.getExternalContext();
			Map sessionMap = extContext.getSessionMap();
			HierarchyDBBean hdb = (HierarchyDBBean) sessionMap.get("hierarchyDBBean");
			HeaderBean heb = (HeaderBean) sessionMap.get("headerBean");
			LoginBean lgnbn = (LoginBean) sessionMap.get("loginBean");
			WorkflowTabBean wtb = (WorkflowTabBean) sessionMap.get("workflowTabBean");
			PropUtil prop=new PropUtil();
			String xmlDir = prop.getProperty("HIERARCHY_XML_DIR");
			String xmlLevelFileName = prop.getProperty("DOCUMENT_XML_FILE");
			Document doc = Globals.openXMLFile(xmlDir, xmlLevelFileName);
			
			if(pageName.equals("Hierarchytree.xhtml")){

				
				String workflowNameStr=document_ID;
				String workflowName1 = hierarchyCreatedDate;
				String workflowID1 = hierarchy_ID;
				
				this.hierarchy_ID=workflow_ID;
				
				this.document_ID=document_ID;
				this.hirename=workflowName;
				this.documentNameWF=documentName;
				
				String strType=documentName;
				String[] arr1 = strType.split("\\.(?=[^\\.]+$)");
				
			 
				String docType = arr1[1];
				
				this.docType=docType;
				this.doccreatedBy=createdBy;
				this.doccreatedDate=createdDate;
				//------------------Vignesh Start date --------------------------
				
				
				this.docdueDate=due_Date;
				
				
				 if(docdueDate != null && !docdueDate.trim().isEmpty())
				 {
					 System.out.println("$$$$$ NO DUEDATE $$$");
					 
				
					 
		

					 System.out.println("due date not date format>>>>>>>>"+ docdueDate);
					 try {
						 SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
						 Date duedate1 = sdf.parse(docdueDate);

						 /* if(duedate1!=null || !duedate1.equals(null))
			        {

			        	System.out.println("$$$Duedate is missing....");

			        }*/


						 System.out.println("^^^^^^^****^^^ docdueDate >>>> :"+duedate1);

						 //reference link :https://www.mkyong.com/java/how-to-compare-dates-in-java/			
						 DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
						 Date date = new Date();

						 String sr=dateFormat.format(date);
						 date = dateFormat.parse(sr);
						 System.out.println("**********"+date);
						 System.out.println("date format string......"+sr);

						 Calendar cal = Calendar.getInstance();				
						 //Add one day to current date.
						 cal.add(Calendar.DATE,1);
						 System.out.println(" DATE ONE DAY BEFORE ...."+dateFormat.format(cal.getTime()));
						 //DateFormat dateFormat0 = new SimpleDateFormat("MM-dd-yyyy");
						 Date afterdt=cal.getTime();
						 String afterdate=dateFormat.format(afterdt);
						 afterdt=dateFormat.parse(afterdate);

						 System.out.println(">>>>.....>>>>>>>>"+afterdt);
						 System.out.println("String value ...."+afterdate);

						 if ((duedate1.compareTo(date)==0) || duedate1.compareTo(afterdt)==0 ) 
						 {
							 this.dueDteClr="yellow";
							 //this.dueDatecolor="yellow";
							 System.out.println(">>>>>>>>>>>>>>>====== Yellow");
						 } 			    
						// else if (duedate1.compareTo(date) < 0) {
							 
						 else if (duedate1.after(date))  { 
							 
							 System.out.println("Date1 is before Date2 ==== RED ");
							 this.dueDteClr="green";
							 // this.dueDatecolor="red";
						 } 		
						 
						 
						// else if(duedate1.compareTo(date) > 0)
						 else if (duedate1.before(date))
							 
						 {
							 System.out.println(">>>>>>>>>>>>>>>====== Green");
							 this.dueDteClr="red";

						 } 	
						 else {
							
				                 System.out.println("How to get here?");
						 }
						 
						 
					 }catch (ParseException e) {
						// TODO: handle exception
					}
					 
				 }
				 else
				 {
					 this.docdueDate="NA";
				 }
				 
				
				
				
				
				
				
				
				
				
				
				//---------------------------Vignesh End date ----------------------
				setStageMessageinFrontWF(workflow_ID,document_ID);
				this.attachmentTabDetailsAL1.clear();
				if(this.sendPrimaryFileOnly.equalsIgnoreCase("true")) {
					String userName=lgnbn.getUsername();
					this.attachmentTabDetailsAL1 = WorkflowManager.loadAttachmentByStageAndUser(document_ID, this.currentStatgeName, userName);
				}else {
					this.attachmentTabDetailsAL1 = WorkflowManager.loadAttachmentTable(document_ID);
				}
				
				wtb.getAllAttributesFromWorkflow4Stage(document_ID);
			}
			
			if((pageName.equals("Hierarchytree.xhtml") && isAddnewHry.equals("addExistsHry")) || isAddnewHry.equals("reload")){

				if(rIXTB != null){
					System.out.println("Enter In the view scoped ACCESS=========>>>>>>>>");
					rIXTB.getReportsForSession(hierarchyXmlFileName, hierarchy_ID, null, "");
				}else{
					System.out.println("Enter In the NON view scoped ACCESS=========>>>>>>>>");
					ReportsInvXMLTreeBean rpt = new ReportsInvXMLTreeBean(hierarchyXmlFileName, hierarchy_ID,null,"");
					rpt.getReportsForSession(hierarchyXmlFileName, hierarchy_ID,null,"");
				}
				System.out.println("ENTER IN TEE LOAD------>>>>>");
			}
			if(pageName.equals("HierarchyList.xhtml")){
				
				String drprvilage=(String)lgnbn.getLoginprvilageKey();
				
				if(drprvilage.equals("true")) {
					
					
					HeaderBean hb = new HeaderBean();
					hb.processMenu("HierarchyList.xhtml");
					
				}else {
					
					HeaderBean hb = new HeaderBean();
					hb.processMenu("DocumentList.xhtml");
					
				}				
			}
			HeaderBean hb=new HeaderBean();
			hb.processMenu(pageName);

		}catch(Exception e){
			e.printStackTrace();

		}

		System.out.println("Exiting: "
				+ new Exception().getStackTrace()[0].getClassName() + "."
				+ new Exception().getStackTrace()[0].getMethodName());
	}

	public void setStageMessageinFrontWF(String workflow_ID,String document_ID){

		System.out.println("Entering: " + new Exception().getStackTrace()[0].getClassName() + "."
				+ new Exception().getStackTrace()[0].getMethodName());

		try{
			System.out.println("==========================>Set User Status On Front Page **** Start"); 

			LoginProcessManager  log=new LoginProcessManager();

			// CODE CHANGE PANDIAN 21Mar2014 disable/enable icons
			FacesContext ctx1 = FacesContext.getCurrentInstance();
			ExternalContext extContext1 = ctx1.getExternalContext();
			Map sessionMap1 = extContext1.getSessionMap();
			SecurityBean secbn = (SecurityBean) sessionMap1.get("securityBean");
			LoginBean lgnbn = (LoginBean) sessionMap1.get("loginBean");
			
			DashboardBean dshbn = (DashboardBean) sessionMap1.get("dashboardBean");
			String dashboardURL= dshbn.getUrlParameter();
			String currentLogin="";
			String currentLoginKEY="";
			
			if(dashboardURL.equalsIgnoreCase("true")) {
				
				currentLogin=dshbn.getUrlLogin();
				currentLoginKEY=dshbn.getUrlcus_key();
				
			}else {
				
				currentLogin=lgnbn.getUsername();
				currentLoginKEY=lgnbn.getCustomerKey();
			}
			
			
			
			String userName=currentLogin; //userLoginId
			Hashtable LoginDetailsHT=log.getLoginDetails(userName, currentLoginKEY);
			System.out.println("User Login ID "+userName+" Login Details "+LoginDetailsHT);
			String accessType=(String)LoginDetailsHT.get("Access_Type");
			String accessendDate=(String)LoginDetailsHT.get("Access_End_Date");
			String AccessUniqID=(String)LoginDetailsHT.get("Access_Unique_ID");
			String emailID=(String)LoginDetailsHT.get("Email_ID");




			this.memberloginID=userName;
			this.accessUniqID=AccessUniqID;

			System.out.println("LoginDetailsHT.get(0)=========>>>"+(LoginDetailsHT.get("User_ID")));


			if(memberloginID.equals(LoginDetailsHT.get("User_ID"))){  // code change Menaka 26MAR2014
				accessType="Enabled";
			}


			if(accessType.equals("Disabled")) {// if user disabled by admin user can not process						
				//		this.commonStatus="Access Type : Disabled. Please contact Adminstration";
				this.commonStatus="None (Access type : disabled).";  // code change Menaka 31MAR2014
				System.out.println("Access Type : Disabled Please contact Adminstration");
				this.memberStatus="NA";
				this.memberMailID="NA";
				this.currentStatgeName="NA";
				this.currentStageNumb="NA";
				this.totalStageNum="NA";	   
				this.stageStatus="NA";

				this.currentprocessMember="NA"; // code change pandian 21APR2014 add member who is process and set his Role
				this.currentprocessMemberRole="NA";
				this.wfmemberAcessType="NA";

				this.renderstatus4compltd="false"; 
				this.renderstatus4Rejected="false";
				this.renderstatus4Approved="false";
				this.renderstatus4Wip="false";

				// CODE CHANGE PANDIAN 21Mar2014 disable/enable icons
				secbn.setAlliconObject("AllDisable",secbn);
				System.out.println("All icons disabled as the Member access is disabled. Please contact Administrator.");


				//		return;
			}else{

				boolean isneeddisableallicons=false;	

				boolean isFinalStageCompleted=false;
				String finalStageNo="";

				try{ // check current Date and Accessing Date 
					
					PropUtil prop=new PropUtil();
					String	DateFormat=prop.getProperty("DATE_FORMAT");
					Date currentDateNow=new Date();

					if(!memberloginID.equals(LoginDetailsHT.get("User_ID")) && false){		//code change Vishnu 11May2018  // code change Menaka 26MAR2014

						SimpleDateFormat sdf = new SimpleDateFormat(DateFormat); 
						String currentformatDate=sdf.format(currentDateNow);

						Date accessEnddate = sdf.parse(accessendDate);  //1
						Date currentdate = sdf.parse(currentformatDate);	 //2

						if(currentdate.equals(accessEnddate)){
							System.out.println("Access End Date "+accessEnddate+ " Equals user Login Date "+currentdate);
						}else if(currentdate.before(accessEnddate)){
							System.out.println("user Login Date "+currentdate+"is before Access End Date "+accessEnddate);
						}else if(currentdate.after(accessEnddate)){
							System.out.println("user Login Date "+currentdate+"is After Access End Date "+accessEnddate);
							//	        		this.commonStatus="Accessing End Date :"+accessendDate+" You cannot Process. Please contact Adminstration";	 
							this.commonStatus="None (Accessing end date :"+accessendDate+" You cannot process).";	 // code change Menaka 31MAR2014
							//	        		return;
						}

					}
					Hashtable WFNodeDetailsHT=Globals.getWFNodeDetails(workflow_ID, document_ID, true,AccessUniqID);
					
				  /*  Hashtable wfAttachTH=new Hashtable();
					wfAttachTH=(Hashtable)WFNodeDetailsHT.get("attchDocHT");
					System.out.println("wfAttachTH :"+wfAttachTH);
					System.out.println("wfAttachTH :"+wfAttachTH.size());
					System.out.println("WorkFLow Node Value  WFNodeDetailsHT "+WFNodeDetailsHT);
					
					System.out.println("WorkFLow Node Value  WFNodeDetailsHT "+WFNodeDetailsHT);*/
					
					if(WFNodeDetailsHT.size()==0){  // if work flow is null 
						System.out.println("WorkFLow Node is Empty WFNodeDetailsHT "+WFNodeDetailsHT);

						this.totalStageNum="NA";
						this.stageStatus="NA";
						this.currentStatgeName="NA";
						this.currentStageNumb="NA";

						this.memberMailID="NA";
						this.memberStatus="NA";

						this.currentprocessMember="NA"; // code change pandian 21APR2014 add member who is process and set his Role
						this.currentprocessMemberRole="NA";
						this.wfmemberAcessType="NA";

						this.commonStatus="None (Workflow is not created yet).";
						this.renderstatus4compltd="false"; 
						this.renderstatus4Rejected="false";
						this.renderstatus4Approved="false";
						this.renderstatus4Wip="false";


						// CODE CHANGE PANDIAN 21Mar2014 disable/enable icons
						isneeddisableallicons=true;	
						System.out.println("Disabled All icons because the Work FLow did not created yet");

					}else{ // Pandian 18MAR2014
						String workflowName = WFNodeDetailsHT.get("WorkflowName") == null ? "" : String.valueOf(WFNodeDetailsHT.get("WorkflowName"));
						this.hierarchyName = workflowName;
						String currentStageName="";
						if(WFNodeDetailsHT.get("Current_Stage_Name")==null){
							currentStageName="NA";
						}else{
							currentStageName=(String)WFNodeDetailsHT.get("Current_Stage_Name");
						}

						// code change pandian 21APR2014 add member who is process and set his Role start
						String currentprocessMember1="";
						if(WFNodeDetailsHT.get("currentprocessMember")==null){
							currentprocessMember1="NA";
						}else{
							currentprocessMember1=(String)WFNodeDetailsHT.get("currentprocessMember");
						}
						String currentprocessMemberAll="";
						if(WFNodeDetailsHT.get("currentprocessMemberAll")==null){
							currentprocessMemberAll="NA";
						}else{
							currentprocessMemberAll=(String)WFNodeDetailsHT.get("currentprocessMemberAll");
						}

						String currentprocessMemberRole1="";
						if(WFNodeDetailsHT.get("currentprocessMemberRole")==null){
							currentprocessMemberRole1="NA";
						}else{
							currentprocessMemberRole1=(String)WFNodeDetailsHT.get("currentprocessMemberRole");
						}

						String wfmemberAcessType1="";
						if(WFNodeDetailsHT.get("wfmemberAcessType")==null){
							wfmemberAcessType1="NA";
						}else{
							wfmemberAcessType1=(String)WFNodeDetailsHT.get("wfmemberAcessType");
						}
						// code change pandian 21APR2014 add member who is process and set his Role end

						
						String currentStageNo="";	 
						if(WFNodeDetailsHT.get("Current_Stage_No")==null){
							currentStageNo="NA";
						}else{
							currentStageNo=(String)WFNodeDetailsHT.get("Current_Stage_No");	
						}
						
						if(currentStageNo.equals("1")){
							
							this.delete4DocsFlag="true";
							
						}

						if(WFNodeDetailsHT.get("Total_No_Stages")==null){
							this.totalStageNum="NA";	
						}else{
							String totalnoOfstage=(String)WFNodeDetailsHT.get("Total_No_Stages");
							this.totalStageNum=totalnoOfstage;	
							int tot=Integer.parseInt(totalnoOfstage);
							int pre=tot-1;	
							if(!currentStageNo.equals("Completed") && !currentStageNo.equalsIgnoreCase("Cancel") && !currentStageNo.equalsIgnoreCase("Rules Failed")){
								this.sendPrimaryFileOnly = WFNodeDetailsHT.get("SendPrimaryFileOnly") == null ? "" : WFNodeDetailsHT.get("SendPrimaryFileOnly").toString();
								this.allow_Upload = WFNodeDetailsHT.get("Allow_Upload") == null ? "" : WFNodeDetailsHT.get("Allow_Upload").toString();
								
								int curstage=Integer.parseInt(currentStageNo);
								String hierDir=prop.getProperty("HIERARCHY_XML_DIR");
								String docXmlFileName = prop.getProperty("DOCUMENT_XML_FILE");
								Document doc=Globals.openXMLFile(hierDir, docXmlFileName);
								Element docNd=(Element)Globals.getNodeByAttrVal(doc, "Document", "Document_ID", document_ID);
								Node workFlowN = docNd.getElementsByTagName("Workflow").item(0);
								LoginProcessManager lgn=new LoginProcessManager();
								System.out.println("dd currentStageNo :"+currentStageNo);
								Hashtable stageDetailsHT=lgn.retriveStageDetailsFromXML(workFlowN, currentStageNo, "");
								Hashtable mssgeDetailsHT=(Hashtable)stageDetailsHT.get("MessagedetHT");

								int statusnum=0;
								this.renderstatus4compltd="false"; 
								this.renderstatus4Rejected="false";
								this.renderstatus4Approved="false";
								this.renderstatus4Wip="false";
								for(int i=0;i<mssgeDetailsHT.size();i++){
									statusnum=i+1;
									String stmsg="";
									if(statusnum==mssgeDetailsHT.size()){
										stmsg=(String)mssgeDetailsHT.get("Final");
									}else{
										stmsg=(String)mssgeDetailsHT.get(String.valueOf(statusnum));
									}  

									System.out.println("stmsg->"+stmsg);

									if(stmsg.equals("WIP")){
										this.renderstatus4Wip="true";
									}else  if(stmsg.equals("Rejected")){
										this.renderstatus4Rejected="true";
									}else  if(stmsg.equals("Approved") || stmsg.equals("Initiate")){
										this.renderstatus4Approved="true";
									}else  if(stmsg.equals("Completed")){
										this.renderstatus4compltd="true"; 
									}


								}

								// 
								//	        			if(curstage==tot){
								//	        				this.renderstatus4compltd="false"; 
								//	        				this.renderstatus4Rejected="true";
								//	        				this.renderstatus4Approved="true";
								//	        				this.renderstatus4Wip="true";
								//	        			}else if(curstage==pre){
								//	        				this.renderstatus4compltd="false"; 
								//	        				this.renderstatus4Rejected="true";
								//	        				this.renderstatus4Approved="true";
								//	        				this.renderstatus4Wip="true";
								//	        			}else{
								//	        				this.renderstatus4compltd="true"; 
								//	        				this.renderstatus4Rejected="false";
								//	        				this.renderstatus4Approved="false";
								//	        				this.renderstatus4Wip="true";
								//	        			}

							}


						}
						if(WFNodeDetailsHT.get("Current_Stage_Status")==null){
							this.stageStatus="NA";
						}else{
							this.stageStatus=(String)WFNodeDetailsHT.get("Current_Stage_Status");
						}


						Hashtable UserDetailsHT=new Hashtable();
						UserDetailsHT=(Hashtable)WFNodeDetailsHT.get("UserDetailsHT");
						System.out.println(UserDetailsHT+"*******currentStageName :"+currentStageName);
						this.currentStatgeName=currentStageName;
						this.currentStageNumb=currentStageNo;
						this.currentprocessMember=currentprocessMemberAll;  // code change pandian 21APR2014 add member who is process and set his Role
						this.currentprocessMemberRole=currentprocessMemberRole1;
						this.wfmemberAcessType=wfmemberAcessType1;
						if(currentStageNo.equals("Completed") || currentStageNo.equalsIgnoreCase("Cancel") || currentStageNo.equalsIgnoreCase("Rules Failed")){
							//		        		this.commonStatus="All Stages are processed and closed. Please contact your Adminstrator.";	 
							this.commonStatus="None (All stages are processed and closed).";	 // code change Menaka 31MAR2014
							this.memberStatus="NA";
							this.memberMailID="NA";
							this.stageStatus="Completed";


							this.renderstatus4compltd="false"; 
							this.renderstatus4Rejected="false";
							this.renderstatus4Approved="false";
							this.renderstatus4Wip="false";


							isneeddisableallicons=true;	
							System.out.println("Disabled All icons because All the Stage are Closed");//				    	
							//				    	currentStageNo=this.totalStageNum;//Code change Gokul 14APR2014				    	
							isFinalStageCompleted=true;
							finalStageNo=totalStageNum;


							//		        		System.out.println(processStatus);
							//		        		return;
						}else{
							if(UserDetailsHT==null){
								this.memberStatus="NA";
								this.memberMailID=emailID;
							}else{
								if(UserDetailsHT.get("User_Status")==null){
									this.memberStatus="NA";
								}else{  //code change pandian 19MAR2014
									String memberstatuss=(String)UserDetailsHT.get("User_Status");
									this.memberStatus=memberstatuss;
									LoginProcessManager lgn=new LoginProcessManager();
									String isstatusAvaible=lgn.getFinalMsg(workflow_ID,memberstatuss);


									if(isstatusAvaible.equals("true")){
										isneeddisableallicons=true;	
										System.out.println("Disabled all icons because MemberStatus is Completed / Approved");

									}

									//			        			if(memberstatuss.equals("Completed") || memberstatuss.equals("Approved")){
									//			        				isneeddisableallicons=true;	
									//							    	System.out.println("Disabled All icons because MemberStatus is Completed / Approved");
									//			        				
									//			        			}
								}

								if(UserDetailsHT.get("E-mail")==null){
									this.memberMailID=emailID;
								}else{
									this.memberMailID=(String)UserDetailsHT.get("E-mail");	
								}
							}



							this.currentStatgeName=currentStageName;
							this.currentStageNumb=currentStageNo;
							this.currentprocessMember=currentprocessMemberAll;  // code change pandian 21APR2014 add member who is process and set his Role
							this.currentprocessMemberRole=currentprocessMemberRole1;
							this.wfmemberAcessType=wfmemberAcessType1;
							this.commonStatus="";
							
							// ***************START***********************VIJAY
							
							boolean currUserFlag = RulesManager.checkUserIsInCurrStage(document_ID, userName);
							String statusCode4Documents = WFNodeDetailsHT.get("StatusCode4Documents") == null ? "" : WFNodeDetailsHT.get("StatusCode4Documents").toString();
							String change_PrimaryFileOnly = WFNodeDetailsHT.get("Change_PrimaryFileOnly") == null ? "" : WFNodeDetailsHT.get("Change_PrimaryFileOnly").toString();
												
						if(currUserFlag && change_PrimaryFileOnly.equals("true")) {
							
							this.changeBTNpanelStr="true";
						}
						if(currUserFlag && statusCode4Documents.equalsIgnoreCase("true")) {
							this.statusCode4DocsFlag = "true";
						}
								// ***************END***********************
						}
						// check all process (Serial are parellel processing )
						System.out.println("Hierarchy_ID: "+workflow_ID+"; hierarchyName: "+hierarchyName+"; currentStageNo: "+currentStageNo+"; AccessUniqID: "+AccessUniqID);
						String previlge=(String)LoginDetailsHT.get("Super_Privilege_Admin"); // code change pandian 28Mar2013
						if(AccessUniqID!=null && previlge.equals("false")){  // code change Menaka 27MAR2014
							Hashtable NextProcessHT=new Hashtable();
							if(isFinalStageCompleted){  // serach all stage for this current member
								//workflow_ID
//								NextProcessHT=log.setStatusMsg4EMP(document_ID,hierarchyName, finalStageNo, AccessUniqID, "","",AccessUniqID,true);
								NextProcessHT=WorkflowManager.setStatusMsg4EMP(workflow_ID, document_ID, hierarchyName, finalStageNo, AccessUniqID, "", "", AccessUniqID, true, userName);
							}else{
//								NextProcessHT=log.setStatusMsg4EMP(document_ID,hierarchyName, currentStageNo, AccessUniqID, "","",AccessUniqID,true);
								NextProcessHT=WorkflowManager.setStatusMsg4EMP(workflow_ID, document_ID, hierarchyName, currentStageNo, AccessUniqID, "", "", AccessUniqID, true, userName);
							}

							if(NextProcessHT.size()==0){
								//		        		this.commonStatus="Member cannot process this Hierarchy. Please contact your Adminstrator.";
								this.commonStatus="None (Member cannot process this hierarchy).";  // code change Menaka 31MAR2014
								this.renderstatus4compltd="false"; 
								this.renderstatus4Rejected="false";
								this.renderstatus4Approved="false";
								this.renderstatus4Wip="false";

								isneeddisableallicons=true;	
								System.out.println("Disabled All icons because the User Can not Process NextProcessHT"+NextProcessHT);
								//		        		System.out.println(processStatus);
								//		        		return;
							}else{

								Boolean loginProcess=(Boolean)NextProcessHT.get("LoginProcess");
								if(loginProcess){ // user can process to next
									this.commonStatus="None (Member can process the workflow).";
									this.commonStatus="";

								}else{ // user cannot Process
									String indicate=(String)NextProcessHT.get("indicate");
									this.commonStatus=indicate;

									this.renderstatus4compltd="false"; 
									this.renderstatus4Rejected="false";
									this.renderstatus4Approved="false";
									this.renderstatus4Wip="false";

									isneeddisableallicons=true;	
									System.out.println("Disabled All icons because indicate :"+indicate);

									//					    	//Start Code Change Gokul 14APR2014 for Showing the buttons for final Stage Users
									//					    	if(currentStageNo.equalsIgnoreCase("Completed")){
									//					    		String role=(String)WFNodeDetailsHT.get("MemberRole");
									//					    		if(role!=null && !role.equalsIgnoreCase("")){
									//					    			secbn.setAlliconObject(role,secbn);	
									//					    			
									//					    		}
									//					    	}
									////					    	End Code Change Gokul 14APR2014 for Showing the buttons for final Stage Users


								}

							}

							//////
							// CODE CHANGE PANDIAN 21Mar2014 disable/enable icons
							if(isneeddisableallicons){			        		
								System.out.println("Disabled All icons********isneeddisableallicons :"+isneeddisableallicons);
								secbn.setAlliconObject("AllDisable",secbn);	

								if(WFNodeDetailsHT.get("MemberRole")!=null){
									String Role=(String)WFNodeDetailsHT.get("MemberRole");	

									if(Role.equals("Admin")){
										this.commonStatus="None (Member logged in with admin role).";

									}else if(Role.equals("public")){
										this.commonStatus="None ("+commonStatus+" logged in as public)";

									}
									System.out.println("aDMIN===>1"+Role);
//									secbn.setAlliconObject(Role,secbn);					    		

									//code change vishnu for hide DH and Reporting hierarchy
									if(!Role.equals("public")){
										hideHierarchyDHNREP(Role,secbn,lgnbn,dshbn);	
									}



								}

								this.renderstatus4compltd="false"; 
								this.renderstatus4Rejected="false";
								this.renderstatus4Approved="false";
								this.renderstatus4Wip="false";

							}else{
								System.out.println("WFNodeDetailsHT.get(MemberRole)"+WFNodeDetailsHT.get("MemberRole"));

								if(WFNodeDetailsHT.get("MemberRole")!=null){
									String Role=(String)WFNodeDetailsHT.get("MemberRole");	

									if(Role.equals("Admin")){
										this.commonStatus="None (Member logged in with admin role).";

									}else if(Role.equals("public")){
										this.commonStatus="None ("+commonStatus+" logged in as public)";

									}
									//				    		System.out.println("aDMIN===>"+Role);

//									secbn.setAlliconObject(Role,secbn);	
//
//									//code change vishnu for hide DH and Reporting hierarchy
//									if(!Role.equals("public")){
//										hideHierarchyDHNREP(Role,secbn,lgnbn);	
//									}

//									if(currentStageNo.equals(totalStageNum)){
//										secbn.setReGenerateHierarchy("false");
//										secbn.setGenerateData("false");
//										secbn.setGenerateFact("false");
//									}

								}	
							}

						}



						//		        	else if(WFNodeDetailsHT.get("MemberRole")==null){
						//			    		this.currentStatgeName="NA";
						//			    		
						//			    	}


					}
					System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$ :"+workflow_ID);
					allStages.clear();
					allStages=getAllStageNamesWF(document_ID); //Code Change VIJAY 06AUG2018
					
					fullCommonStatus=commonStatus;
					fullCurrentprocessMember=currentprocessMember; 

					if(currentprocessMember.length()>45){
						currentprocessMember=currentprocessMember.substring(0,43).concat("...");
					}

					if(currentStatgeName.equals("NA")&&currentprocessMember.equals("NA")&&stageStatus.equals("NA")){

						if(commonStatus.length()>25){  
							commonStatus=commonStatus.substring(0,23).concat("...");
						}
					}else{
						if(commonStatus.length()>13){  
							commonStatus=commonStatus.substring(0,11).concat("...");
						}
					}





				}catch(Exception ex){
					ex.printStackTrace();
				}

			}

			System.out.println("==========================>Set User Status On Front Page **** End");

		}catch(Exception e){
			e.printStackTrace();

		}

		System.out.println("Exiting: "
				+ new Exception().getStackTrace()[0].getClassName() + "."
				+ new Exception().getStackTrace()[0].getMethodName());
	}
	public ArrayList getAllStageNamesWF(String heirCode){
		System.out.println("Entering: " + new Exception().getStackTrace()[0].getClassName() + "."
				+ new Exception().getStackTrace()[0].getMethodName());
		ArrayList stagesAL=new ArrayList();
		try{
			PropUtil prop=new PropUtil();
			String docLevelXML=prop.getProperty("DOCUMENT_XML_FILE");
			String heirLeveldir=prop.getProperty("HIERARCHY_XML_DIR");
			Document xmlDoc=Globals.openXMLFile(heirLeveldir, docLevelXML);
			Element docEle=(Element)Globals.getNodeByAttrVal(xmlDoc, "Document", "Document_ID", heirCode);
			Node workFlowN = docEle.getElementsByTagName("Workflow").item(0);
			if(workFlowN!=null){


				Element workFlowNEL=(Element)workFlowN;

				String totstage=workFlowNEL.getAttribute("Total_No_Stages");
				int totalStages=Integer.parseInt(totstage);
				int stage=0;


				//		Node stageNode=Globals.getNodeByAttrValUnderParent(xmlDoc, workFlowN, "Stage_No", "-1");
				//		stagesAL.add(stageNode.getAttributes().getNamedItem("Stage_Name").getNodeValue());  // code comment Menaka 14APR2014

				NodeList workFLNL=workFlowN.getChildNodes();
				for(int i=1;i<totalStages+1;i++)
				{
					//			Node wFN=workFLNL.item(i);
					//			if(wFN.getNodeType()==Node.ELEMENT_NODE)
					//			{
					//				if(wFN.getNodeName().equalsIgnoreCase("Stage"))
					//				{

					Node stageNode1=Globals.getNodeByAttrValUnderParent(xmlDoc, workFlowN, "Stage_No", String.valueOf(i));

//					if(stage>0){  // code change Menaka 14APR2014
						stagesAL.add(stageNode1.getAttributes().getNamedItem("Stage_Name").getNodeValue());
//					}
					stage++;
					//				}
					//			}
				}
			}


		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		System.out.println("Exiting: " + new Exception().getStackTrace()[0].getClassName() + "."
				+ new Exception().getStackTrace()[0].getMethodName());
		return stagesAL;
	}
	//Start Code Change Gokul 07APR2014
	private ArrayList allStages=new ArrayList<>();



	public ArrayList getAllStages() {

		return allStages;
	}
	public void setAllStages(ArrayList allStages) {
		this.allStages = allStages;
	}

	public void hideHierarchyDHNREP(String Role,SecurityBean secbn,LoginBean lgnbn,DashboardBean dshbn){
		System.out.println("Entering: " + new Exception().getStackTrace()[0].getClassName() + "."
				+ new Exception().getStackTrace()[0].getMethodName());

		try{

			PropUtil prop=new PropUtil();

			boolean isSuperPrevilageUser = false;
			String xmlDir = prop.getProperty("HIERARCHY_XML_DIR");							
			String hierLAFileName = prop.getProperty("LOGIN_XML_FILE");
			Document laDoc = Globals.openXMLFile(xmlDir, hierLAFileName);
			NodeList obieeUsersNdList = laDoc.getElementsByTagName("Obiee_Users"); 
			Node obieeUsersNd = obieeUsersNdList.item(0);
			NodeList userNdList = obieeUsersNd.getChildNodes();
		
			String dashboardURL= dshbn.getUrlParameter();
			String currentLogin="";
			String currentLoginKEY="";
			
			if(dashboardURL.equalsIgnoreCase("true")) {
				
				currentLogin=dshbn.getUrlLogin();
				currentLoginKEY=dshbn.getUrlcus_key();
				
			}else {
				
				currentLogin=lgnbn.getUsername();
				currentLoginKEY=lgnbn.getCustomerKey();
			}
			
			
			for(int i=0;i<userNdList.getLength();i++){
				if(userNdList.item(i).getNodeType() == Node.ELEMENT_NODE && userNdList.item(i).getNodeName().equals("User")){
					
					if(userNdList.item(i).getTextContent().equalsIgnoreCase(currentLogin) && Globals.getAttrVal4AttrName(userNdList.item(i), "Super_Privilege_Admin").equals("true")){
						isSuperPrevilageUser = true;
						break;
					}
					
				}
			}
			if(status.equalsIgnoreCase("Dependent") || status.equalsIgnoreCase("Reporting")){
				if(Role.equals("Admin") || isSuperPrevilageUser){
					secbn.setPushOnLevelOut("true");
					secbn.setPushOnLevelIn("true");
					secbn.setMoveUP("true");
					secbn.setMoveDown("true");
					secbn.setAddRollUpNode("true");
					secbn.setAddDataNode("true");
					secbn.setEditSelectedNode("true");
				}else{
					//					System.out.println("aDMIN===>"+isAdmin);
					//					System.out.println("Super===>"+isSuperPrevilageUser);
					secbn.setPushOnLevelOut("false");
					secbn.setPushOnLevelIn("false");
					secbn.setMoveUP("false");
					secbn.setMoveDown("false");
					secbn.setAddRollUpNode("false");
					secbn.setAddDataNode("false");
					secbn.setEditSelectedNode("false");
				}
			}



		}catch(Exception e){
			e.printStackTrace();

		}

		System.out.println("Exiting: "
				+ new Exception().getStackTrace()[0].getClassName() + "."
				+ new Exception().getStackTrace()[0].getMethodName());
	}

	public ArrayList getAllStageNames(String heirCode){
		System.out.println("Entering: " + new Exception().getStackTrace()[0].getClassName() + "."
				+ new Exception().getStackTrace()[0].getMethodName());
		ArrayList stagesAL=new ArrayList();
		try{
			PropUtil prop=new PropUtil();
			String heirLevelXML=prop.getProperty("HIERARCHY_XML_FILE");
			String heirLeveldir=prop.getProperty("HIERARCHY_XML_DIR");
			Document xmlDoc=Globals.openXMLFile(heirLeveldir, heirLevelXML);
			Node workFlowN=Globals.getNodeByAttrVal(xmlDoc, "Workflow", "Hierarchy_ID", heirCode);
			if(workFlowN!=null){


				Element workFlowNEL=(Element)workFlowN;

				String totstage=workFlowNEL.getAttribute("Total_No_Stages");
				int totalStages=Integer.parseInt(totstage);
				int stage=0;


				//		Node stageNode=Globals.getNodeByAttrValUnderParent(xmlDoc, workFlowN, "Stage_No", "-1");
				//		stagesAL.add(stageNode.getAttributes().getNamedItem("Stage_Name").getNodeValue());  // code comment Menaka 14APR2014

				NodeList workFLNL=workFlowN.getChildNodes();
				for(int i=0;i<totalStages+1;i++)
				{
					//			Node wFN=workFLNL.item(i);
					//			if(wFN.getNodeType()==Node.ELEMENT_NODE)
					//			{
					//				if(wFN.getNodeName().equalsIgnoreCase("Stage"))
					//				{

					Node stageNode1=Globals.getNodeByAttrValUnderParent(xmlDoc, workFlowN, "Stage_No", String.valueOf(stage));

					if(stage!=0){  // code change Menaka 14APR2014
						stagesAL.add(stageNode1.getAttributes().getNamedItem("Stage_Name").getNodeValue());
					}
					stage++;
					//				}
					//			}
				}
			}


		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		System.out.println("Exiting: " + new Exception().getStackTrace()[0].getClassName() + "."
				+ new Exception().getStackTrace()[0].getMethodName());
		return stagesAL;
	}

	//End Code Change Gokul 07APR2014

	public void reDirect2Tree(String pageName) {
		try{
			HeaderBean hb=new HeaderBean();
			hb.processMenu(pageName);
			WFdateTimeOper = "IN";
			WFdateTimeText = "";
			WFdateTimeBetText = "";
			WFActionByOper = "Starts With";
			WFActionByText = "";
			WFActionByBetText = "";
			roleOper = "Starts With";
			roleText = "";
			roleBetText = "";
			fromStageOper = "Starts With";
			fromStageText = "";
			fromStageBetText = "";
			toStageOper = "Starts With";
			toStageText = "";
			toStageBetText = "";
			fromStatusOper ="Starts With";
			fromStatusText = "";
			fromStatusBetText = "";
			notesOper = "Starts With";
			notesText = "";
			notesBetText = "";
			memberStatusOper = "Starts With";
			memberStatusText = "";
			memberStatusBetText = "";
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public void temdDirect(String pageName){
		try{
			formID = "factConfig";
			HeaderBean hb=new HeaderBean();
			hb.processMenu(pageName);
			msg="";
			loginOper = "Starts With";
			loginText = "";
			dateTimeOper = "IN";
			dateTimeText = "";
			actionOper = "Starts With";
			actionText = "";
			parentOper = "IN";
			parentText = "";
			beforeOper = "Contains";
			beforeText = "";
			afterOper = "Contains";
			afterText = "";
			oldOper = "Starts With";
			oldText = "";
			oldPostionOper = "Starts With";
			oldPostionText = "";
			newOper = "Starts With";
			newText = "";
			newPostionOper = "Starts With";
			newPostionText = "";
			loginBetText = "";
			dateTimeBetText = "";
			actionBetText = "";
			parentBetText = "";
			beforeBetText = "";
			afterBetText = "";
			oldBetText = "";
			oldPostionBetText = "";
			newBetText = "";
			newPostionBetText = "";
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}



	public String gettingLastchildofNode(String selectedNdID,String nodeName,String typeName) {

		try{
			PropUtil prop=new PropUtil();
			String dir=prop.getProperty("HIERARCHY_XML_DIR");
			Document doc=Globals.openXMLFile(dir, "HierachyLevel.xml");

			Node nd;
			int nodelen;
			String LASTNodename=nodeName;
			String LASTNodeid=selectedNdID;
			String selectedNdID1 = "";
			if(typeName.equalsIgnoreCase("Hierarchy")){
				if(selectedNdID.contains("~")){
					LASTNodename=selectedNdID.split("~")[1];
					LASTNodeid=selectedNdID.split("~")[0];
				}
			}else{
				LASTNodename=nodeName;
				LASTNodeid=selectedNdID;
			}

			if(nodeName.equals("Hierarchy_Level")){
				nd=Globals.getNodeByAttrVal(doc, nodeName, "Hierarchy_ID", LASTNodeid);

				nodelen=0;
				selectedNdID1=LASTNodeid;
			}else{
				nd=Globals.getNodeByAttrVal(doc, nodeName, "ID", LASTNodeid);  // rootlevel
				nodelen=nd.getChildNodes().getLength();
				if(Globals.getAttrVal4AttrName(nd, "Type").equalsIgnoreCase("Hierarchy")){
					selectedNdID1=Globals.getAttrVal4AttrName(nd, "Last_Node_ID");
				}else{
					selectedNdID1=LASTNodeid;
				}
			}


			if(nodelen==0){

				if(typeName.equalsIgnoreCase("Hierarchy")){
					return selectedNdID;
				}else{
					return selectedNdID1;
				}

			}else{
				Node ndlist=nd.getLastChild();
				String lastidVal="";
				String lastidName="";
				if(ndlist.getNodeType()==Node.ELEMENT_NODE && !ndlist.getNodeName().equalsIgnoreCase("#text")){
					lastidVal=ndlist.getAttributes().getNamedItem("ID").getNodeValue();
					lastidName=ndlist.getNodeName();
					if(typeName.equalsIgnoreCase("Hierarchy")){
						selectedNdID=gettingLastchildofNode(lastidVal+"~"+lastidName,lastidName,typeName );
					}else{
						selectedNdID1=gettingLastchildofNode(lastidVal,lastidName,typeName );
					}
				}else if(!(ndlist.getNodeType()==Node.ELEMENT_NODE) && ndlist.getNodeName().equalsIgnoreCase("#text") && nodelen>1){
					lastidVal=ndlist.getPreviousSibling().getAttributes().getNamedItem("ID").getNodeValue();
					lastidName=ndlist.getPreviousSibling().getNodeName();
					if(typeName.equalsIgnoreCase("Hierarchy")){
						selectedNdID=gettingLastchildofNode(lastidVal+"~"+lastidName,lastidName,typeName );
					}else{
						selectedNdID1=gettingLastchildofNode(lastidVal,lastidName,typeName );
					}


				}


			}

			if(typeName.equalsIgnoreCase("Hierarchy")){
				return selectedNdID;
			}else{
				return selectedNdID1;
			}
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return "";
		}




	}

	public String codecombinationFlag;



	public String getCodecombinationFlag() {
		return codecombinationFlag;
	}
	public void setCodecombinationFlag(String codecombinationFlag) {
		this.codecombinationFlag = codecombinationFlag;
	}
	String addedName="";
	public String getAddedName() {
		return addedName;
	}

	public void setAddedName(String addedName) {
		this.addedName = addedName;
	}
	int levelNo;
	public int getLevelNo() {
		return levelNo;
	}

	public void setLevelNo(int levelNo) {
		this.levelNo = levelNo;
	}
	String typeName="";
	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	Hashtable valueHT=new Hashtable<>();
	public Hashtable getValueHT() {
		return valueHT;
	}

	public void setValueHT(Hashtable valueHT) {
		this.valueHT = valueHT;
	}
	Hashtable basicValueHT=new Hashtable<>();
	public Hashtable getBasicValueHT() {
		return basicValueHT;
	}

	public void setBasicValueHT(Hashtable basicValueHT) {
		this.basicValueHT = basicValueHT;
	}
	String selectedNdID="";
	public String getSelectedNdID() {
		return selectedNdID;
	}

	public void setSelectedNdID(String selectedNdID) {
		this.selectedNdID = selectedNdID;
	}
	String selectednodeName="";
	public String getSelectednodeName() {
		return selectednodeName;
	}

	public void setSelectednodeName(String selectednodeName) {
		this.selectednodeName = selectednodeName;
	}
	String insertType="";
	public String getInsertType() {
		return insertType;
	}

	public void setInsertType(String insertType) {
		this.insertType = insertType;
	}
	Node addedNode;
	public Node getAddedNode() {
		return addedNode;
	}

	public void setAddedNode(Node addedNode) {
		this.addedNode = addedNode;
	}
	String previoustype4ReGenerate="";
	public String getPrevioustype4ReGenerate() {
		return previoustype4ReGenerate;
	}

	public void setPrevioustype4ReGenerate(String previoustype4ReGenerate) {
		this.previoustype4ReGenerate = previoustype4ReGenerate;
	}
	Connection dbConnection;
	public Connection getDbConnection() {
		return dbConnection;
	}
	public void setDbConnection(Connection dbConnection) {
		this.dbConnection = dbConnection;
	}
	//public void insertionofValues2DB(String addedName, int levelNo,String typeName,Hashtable valueHT,Hashtable basicValueHT,String selectedNdID
	//		,String selectednodeName, String insertType,String addedNodeID,String previoustype4ReGenerate,Connection con,String addedNodeName,Document insertDoc){
	//	
	//	
	////	new HierarchyBean(addedName,levelNo,typeName,valueHT,basicValueHT,selectedNdID,selectednodeName,insertType,addedNodeID,previoustype4ReGenerate,con,addedNodeName,insertDoc);
	////	t.start();
	////	
	////	dbInsert();
	//	
	//}
	//@Override
	//public synchronized void run(){
	//	dbInsert();
	//}

	public String codeCombinationflag4Lastnode;
	private String createCodeCombinationflag4Lastnode = "";


	public String getCreateCodeCombinationflag4Lastnode() {
		return createCodeCombinationflag4Lastnode;
	}
	public void setCreateCodeCombinationflag4Lastnode(
			String createCodeCombinationflag4Lastnode) {
		this.createCodeCombinationflag4Lastnode = createCodeCombinationflag4Lastnode;
	}
	public String getCodeCombinationflag4Lastnode() {
		return codeCombinationflag4Lastnode;
	}
	public void setCodeCombinationflag4Lastnode(String codeCombinationflag4Lastnode) {
		this.codeCombinationflag4Lastnode = codeCombinationflag4Lastnode;
	}

	public String editcodeCombinationflag4Lastnode;
	public String editcreateCodeCombinationflag4Lastnode;
	public String getEditcreateCodeCombinationflag4Lastnode() {
		return editcreateCodeCombinationflag4Lastnode;
	}
	public void setEditcreateCodeCombinationflag4Lastnode(
			String editcreateCodeCombinationflag4Lastnode) {
		this.editcreateCodeCombinationflag4Lastnode = editcreateCodeCombinationflag4Lastnode;
	}
	public String getEditcodeCombinationflag4Lastnode() {
		return editcodeCombinationflag4Lastnode;
	}
	public void setEditcodeCombinationflag4Lastnode(
			String editcodeCombinationflag4Lastnode) {
		this.editcodeCombinationflag4Lastnode = editcodeCombinationflag4Lastnode;
	}
	public int insertionofValues2DB(String addedName, int levelNo,String typeName,Hashtable valueHT,Hashtable basicValueHT,String selectedNdID
			,String selectednodeName, String insertType,String addedNodeID,String previoustype4ReGenerate,Connection con,
			String addedNodeName,Document insertDoc,Hashtable dimStatusHT,Hashtable coldetHT,String pdatatype, int dimCount) {
		System.out.println("Entering: "
				+ new Exception().getStackTrace()[0].getClassName() + "."
				+ new Exception().getStackTrace()[0].getMethodName());
		try{



			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");



			Hashtable<Integer, String> columnNameHT=new Hashtable<>();

			//In this method the hierarchy table columns are loaded to columnNameHT
			columnNameHT = Globals.getcolumnName();
			Connection conn2 ;
			conn2=con;

			PropUtil prop=new PropUtil();
			HIERARCHY_XML_DIR = prop.getProperty("HIERARCHY_XML_DIR");

			int count=levelNo*2;
			int t=0;
			int k=0;


			Hashtable basicValueHTtemp=new Hashtable<>();

			for(int i=0;i<basicValueHT.size();i++){
				basicValueHTtemp.put(i, basicValueHT.get(i));	//loading basic information like hier_id,row_wid,hierarchyname,etc(except hier1 to hier20 columns)
			}

			Document doc=insertDoc;
			String hierName="";
			String hierarchyID="";
			Node rootNd=Globals.getNodeByAttrVal(doc, "RootLevel", "ID", String.valueOf(basicValueHTtemp.get(3))); // 15 Feb 14		//code change Vishnu 17Fev2014

			//in this loop getting hierarchy name and hierarchy id
			for(int ndcount=0;ndcount<rootNd.getChildNodes().getLength();ndcount++){
				rootNd=rootNd.getParentNode();
				if(rootNd.getNodeType()==Node.ELEMENT_NODE && rootNd.getNodeName().equalsIgnoreCase("Hierarchy_Level")){
					hierName=rootNd.getAttributes().getNamedItem("Hierarchy_Name").getNodeValue();
					hierarchyID=rootNd.getAttributes().getNamedItem("Hierarchy_ID").getNodeValue();
				}
			}


			Hashtable finalValueHT=new Hashtable<>();	//this finalValueHT HT is used to store values for hier1 to hier20 columns
			if(typeName.equalsIgnoreCase("Data")){	

				for(int i=0;i<count;i++){		//in this loop values inserted upto current node in finalvalueHT (value is delimited by ~ for seprating value and code)
					if(String.valueOf(valueHT.get(i/2)).contains("~")){
						if(k==0){
							finalValueHT.put(i, String.valueOf(String.valueOf(valueHT.get(i/2)).split("~")[1]));
							k++;
						}else{
							finalValueHT.put(i, String.valueOf(String.valueOf(valueHT.get(i/2)).split("~")[0]));
							k=0; // code change Menaka 22FEB2014
						}
					}else{
						finalValueHT.put(i, String.valueOf(valueHT.get(i/2)));
					}
				}
			}else{

				for(int i=0;i<count;i++){	//in this loop values inserted upto current node in finalvalueHT
					if(String.valueOf(valueHT.get(i/2)).contains("~")){
						if(k==0){
							finalValueHT.put(i, String.valueOf(String.valueOf(valueHT.get(i/2)).split("~")[1]));
							k++;
						}else{
							finalValueHT.put(i, String.valueOf(String.valueOf(valueHT.get(i/2)).split("~")[0]));

						}	
					}else{
						finalValueHT.put(i, String.valueOf(valueHT.get(i/2)));

					}
				}
			}

			Hashtable finalValueHT4ReGenerate=new Hashtable<>(); 
			// 
			for (int i = 0; i < finalValueHT.size(); i++) {
				finalValueHT4ReGenerate.put(i, String.valueOf(finalValueHT.get(i)));
			}

			Node addedNode=Globals.getNodeByAttrVal(doc, addedNodeName, "ID", addedNodeID);


			//////getting common segment from xml and loaded into segmentHT and the segment that has no common value means the segment is added to valuemissedHT//////
			Hashtable segmentKeyHT=new Hashtable<>();
			Hashtable segmentHT=new Hashtable<>();
			Hashtable valueMissedsegmentHT=new Hashtable<>();
			int p=1;
			int q=0;
			int missedcount=0;
			int segmentCount=0;
			Node hierarchyNd=Globals.getNodeByAttrVal(doc, "Hierarchy_Level", "Hierarchy_Name", hierName); 
			if(!Globals.getAttrVal4AttrName(hierarchyNd, "Number_of_Segment").equals("")){
				segmentCount=Integer.parseInt(Globals.getAttrVal4AttrName(hierarchyNd, "Number_of_Segment"));
			}
			NodeList hierarchyNdlist=hierarchyNd.getChildNodes();

			for(int i=0;i<hierarchyNdlist.getLength();i++){
				Node segmentnd=hierarchyNdlist.item(i);
				if(segmentnd.getNodeType()==Node.ELEMENT_NODE && segmentnd.getNodeName().equals("Segments")){
					NodeList segmentndlist=segmentnd.getChildNodes();
					for(int j=0;j<segmentndlist.getLength();j++){
						if(segmentndlist.item(j).getNodeType()==Node.ELEMENT_NODE){
							String[] segmentcodewithvalue=segmentndlist.item(j).getTextContent().split("~");
							String original="";
							int samp=0;
							if(segmentndlist.item(j).getTextContent().equals("")){
								samp=0;
							}else{
								samp = segmentcodewithvalue.length;
							}
							for(int r=0;r<samp;r++){
								String temp=segmentcodewithvalue[r];
								original=original+temp.substring(0, temp.indexOf("#$#"))+"~";
							}
							segmentHT.put(segmentndlist.item(j).getNodeName(), original);
							segmentKeyHT.put(q, segmentndlist.item(j).getNodeName());
							q++;
							p++;

						}else{
							p++;
						}
					}
				}
			}
			Hashtable ht=new Hashtable<>();
			missedcount=0;
			int count1=0;
			missedcount=0;
			for(int i=1;i<=segmentCount;i++){
				ht=new Hashtable<>();
				for(int j=0;j<segmentKeyHT.size();j++){
					if(segmentKeyHT.get(j).equals("Segment_"+i)){
						ht.put(missedcount, "false");
						missedcount++;
					}else{
						ht.put(missedcount, "true");
						missedcount++;

					}
				}

				if(ht.contains("false")){

				}else{

					valueMissedsegmentHT.put(count1,"Segment_"+i);
					segmentHT.put("Segment_"+i, "");
					count1++;
				}
			}

			if (SOP_ENABLED)
				System.out.println("valueMissedsegmentHT====>"+valueMissedsegmentHT);
			if (SOP_ENABLED)
				System.out.println("segmentHT====>"+segmentHT);

			//////////////////////




			int m=0;
			if(!typeName.equalsIgnoreCase("hierarchy")){	//except hierarchy, all other type will come inside

				for(int i=finalValueHT.size();i<columnNameHT.size();i++){	//in this loop finalvalueHT will be filled with all other values from current node to hie20
					if(addedName.contains("~")){
						if(m==0){
							finalValueHT.put(i, addedName.split("~")[1]);
							m++;
						}else if(m==1){
							finalValueHT.put(i, addedName.split("~")[0]);
							m--;
						}	
					}else{
						finalValueHT.put(i, addedName);
					}
				}
				String selectednodeNameTemp=selectednodeName;
				String selectedNdIDTemp=selectedNdID;

				int rowID=0;
				int sortorder=0;
				System.out.println("previoustype4ReGenerate===>"+previoustype4ReGenerate);
				// in this we use the parent node id to get the row_wid and sort_order from db.
				System.out.println("ROW_WID not hierrachy===>"+sdf.format(new Date()));
				if(previoustype4ReGenerate.contains("#~#")){
					String sql4RowCount="SELECT ROW_WID,SORT_ORDER FROM WC_FLEX_HIERARCHY_D WHERE XML_NODE_ID = '"+previoustype4ReGenerate.split("#~#")[1]+"'";

					PreparedStatement pre4RowCount=conn2.prepareStatement(sql4RowCount);
					ResultSet rs2=pre4RowCount.executeQuery();

					while(rs2.next()){
						rowID=rs2.getInt("ROW_WID");
						sortorder=rs2.getInt("SORT_ORDER");

					}
					pre4RowCount.close();
					rs2.close();

				}else{
					String sql4RowCount="SELECT ROW_WID,SORT_ORDER FROM WC_FLEX_HIERARCHY_D WHERE XML_NODE_ID = '"+selectedNdID+"'";

					PreparedStatement pre4RowCount=conn2.prepareStatement(sql4RowCount);
					ResultSet rs2=pre4RowCount.executeQuery();

					while(rs2.next()){
						rowID=rs2.getInt("ROW_WID");
						sortorder=rs2.getInt("SORT_ORDER");

					}
					pre4RowCount.close();
					rs2.close();
				}
				System.out.println("end ROW_WID not hierrachy===>"+sdf.format(new Date()));
				// if sortorder is 0 means get sortorder from maxID
				// If sortorder is not 0 means the sortorder(column value) greater than sortOrder is incremented by 1 
				System.out.println("Updating sort order===>"+sdf.format(new Date()));
				if(sortorder==0){
					basicValueHTtemp.put(6, Globals.getMaxID(HIERARCHY_XML_DIR, "Hierarchy_Config.xml", "Sort_Order4Oracle", "ID"));
				}else{
					basicValueHTtemp.put(6, sortorder+1);
					String sqlUpdate="UPDATE WC_FLEX_HIERARCHY_D SET SORT_ORDER=SORT_ORDER+1 WHERE SORT_ORDER >= "+(sortorder+1);
					PreparedStatement update4RowCount=conn2.prepareStatement(sqlUpdate);
					update4RowCount.execute();
					update4RowCount.close();
				}
				System.out.println("end Updating sort order===>"+sdf.format(new Date()));
				// if current node is itselfcodecombination or CreateCodeCombinationDuringFact means updating node type as code 
				if(Globals.getAttrVal4AttrName(addedNode, "Create_Code_Combination").equalsIgnoreCase("ItselfCodeCombination") || 
						(Globals.getAttrVal4AttrName(hierarchyNd, "Code_Combination").equalsIgnoreCase("CreateCodeCombinationDuringFact") && 
								Globals.getAttrVal4AttrName(addedNode, "Type").equalsIgnoreCase("Data"))){
					basicValueHTtemp.put(10, "Code");
				}else{
					basicValueHTtemp.put(10, "null");
				}

				basicValueHTtemp.put(11, "null");
				basicValueHTtemp.put(12, -1); //code change Vishnu 17Fev2014
				int size=basicValueHTtemp.size();
				int b=0;
				//this condition for current node which is not CreateCodeCombinationAtLeaf because that node will be done after this condition
				//		if(!Globals.getAttrVal4AttrName(addedNode, "Create_Code_Combination").equalsIgnoreCase("CreateCodeCombinationAtLeaf")){
				Hashtable valHT = new Hashtable<>();
				Hashtable nameHT = new Hashtable<>();
				String val = "";
				String name = "";
				String nameTemp = "";
				String valTemp = "";
				//if current node is direct sql type
				System.out.println("Start of direct SQL===>"+sdf.format(new Date()));
				if(Globals.getAttrVal4AttrName(addedNode, "Data_SubType").equalsIgnoreCase("DirectSQL")){
					String segSQL = Globals.getAttrVal4AttrName(addedNode, "Custom_SQL");
					String selectedColname = Globals.getAttrVal4AttrName(addedNode, "Selected_ColumnName");
					Hashtable colNameHT = new Hashtable<>();

					if(selectedColname.contains("#~#")){
						for(int i=0;i<selectedColname.split("#~#").length;i++){
							if(!selectedColname.split("#~#")[i].equals("")){
								String colNamewithTable = selectedColname.split("#~#")[i];
								colNameHT.put(i, colNamewithTable.split("\\.")[1]);
							}
						}

					}
					System.out.println("segSQL===>"+segSQL);
					PreparedStatement ps1 = con.prepareStatement(segSQL);
					ResultSet rs1 = ps1.executeQuery();
					String segValue = "";
					int a = 0;
					val = Globals.getAttrVal4AttrName(addedNode, "Code");
					valTemp = Globals.getAttrVal4AttrName(addedNode, "Code");
					name = Globals.getAttrVal4AttrName(addedNode, "value");
					nameTemp = Globals.getAttrVal4AttrName(addedNode, "value");
					ResultSetMetaData resMdata= rs1.getMetaData();
					//if direct sql means run the sql given by user and fill the codevalue and namevalue in valHT and nameHT respc.
					while (rs1.next()) {
						for(int i=1;i<=colNameHT.size();i++){
							int indexCol = rs1.findColumn((String)colNameHT.get(i-1));
							String decodeColDtype=resMdata.getColumnTypeName(indexCol);
							if(decodeColDtype.contains("VARCHAR")||decodeColDtype.contains("CHAR"))
							{

								//						    	insPS.setString(i, JoinRS.getString(insIndex));
								valHT.put(a, rs1.getString(1));
								nameHT.put(a, rs1.getString(2));
							}else if(decodeColDtype.contains("NUMBER"))   
							{
								valHT.put(a, String.valueOf(rs1.getDouble(1)));
								nameHT.put(a, String.valueOf(rs1.getDouble(2)));
								//						    	insPS.setDouble(i, JoinRS.getDouble(insIndex));
							}else if(decodeColDtype.contains("DATE"))  
							{
								valHT.put(a, String.valueOf(rs1.getDate(1)));
								nameHT.put(a, String.valueOf(rs1.getDate(2)));
								//						    	insPS.setDate(i, JoinRS.getDate(insIndex));
							}else if(decodeColDtype.contains("TIMESTAMP"))  
							{
								valHT.put(a, String.valueOf(rs1.getTimestamp(1)));
								nameHT.put(a, String.valueOf(rs1.getTimestamp(2)));
								//						    	insPS.setTimestamp(i, JoinRS.getTimestamp(insIndex));
							}
						}



						a++;
					}
					rs1.close();
					ps1.close();
				}else{
					//if current node is not direct sql means take code and value attribute value and insert into the valHT and nameHT. 
					val = Globals.getAttrVal4AttrName(addedNode, "Code");
					if(Globals.getAttrVal4AttrName(addedNode, "Type").equalsIgnoreCase("Data")){
						name = Globals.getAttrVal4AttrName(addedNode, "value").substring(0, Globals.getAttrVal4AttrName(addedNode, "value").lastIndexOf("("));
					}else{
						name = Globals.getAttrVal4AttrName(addedNode, "value");
					}
					int a = 0;
					valHT.put(a, Globals.getAttrVal4AttrName(addedNode, "Code"));
					nameHT.put(a, Globals.getAttrVal4AttrName(addedNode, "value"));
				}
				System.out.println("End of direct SQL===>"+sdf.format(new Date()));
				//this loop loops according to the type of the current node type.
				//if curent node is direct sql means the no. of values produced byy sql.
				//if current node is not direct Sql means it loops one time
				String parentRowWid = "";
				parentRowWid = String.valueOf(basicValueHTtemp.get(7));
				//			System.out.println("basicValueHTtemp==>"+basicValueHTtemp);
				//			System.out.println("finalValueHT4ReGenerate===>"+finalValueHT4ReGenerate);
				System.out.println("Start of nameHT loop===>"+sdf.format(new Date()));
				for(int v=0;v<nameHT.size();v++){


					b=0;
					// in this loop we will concat basic values(hierarchyid,name,category,etc) with the node values from hier1 to hier20
					for(int n=size;n<columnNameHT.size();n++){
						if(String.valueOf(finalValueHT.get(b)).equalsIgnoreCase(val)){
							basicValueHTtemp.put(n, String.valueOf(valHT.get(v)));
							b++;
						}else if(String.valueOf(finalValueHT.get(b)).equalsIgnoreCase(name)){
							basicValueHTtemp.put(n, String.valueOf(nameHT.get(v)));
							b++;
						}else{
							basicValueHTtemp.put(n, String.valueOf(finalValueHT.get(b)));	//merging finalvalueHT and basicValueHTtemp in basicValueHTtemp
							b++;
						}

					}
					/////////////////////////////Start Code Change Gokul 22FEB2014

					PreparedStatement pre=null;
					// creating sql for batch in db (excluding hier20Num)
					String sql=Globals.insertSQL(columnNameHT,"HIER20_NUM",pre,"gettingSQL",""); //code change Vishnu 17Fev2014	
					pre=conn2.prepareStatement(sql);

					// inserting values into batch (excluding hier20Num)
					Globals.insertvalue(columnNameHT,basicValueHTtemp,"HIER20_NUM",pre,"settingValues",sql,conn2,coldetHT,pdatatype);	//insering sql for inserting sql in db
					pre.addBatch();
					pre.executeBatch();

					Element addedele=(Element)addedNode;
					addedele.setAttribute("Last_Node_ID", String.valueOf(basicValueHTtemp.get(8)));
					dimCount = dimCount+1;


					// if current node is CreateCodeCombinationAtLeaf means updating node type as code 
					if(!Globals.getAttrVal4AttrName(addedNode, "Create_Code_Combination").equalsIgnoreCase("ItselfCodeCombination") && 
							!Globals.getAttrVal4AttrName(hierarchyNd, "Code_Combination").equalsIgnoreCase("CreateCodeCombinationDuringFact")){
						if(insertType.equalsIgnoreCase("ReGenerate") && Globals.getAttrVal4AttrName(hierarchyNd, "Code_Combination").equalsIgnoreCase("CreateCodeCombinationInDim") && Globals.getAttrVal4AttrName(addedNode, "Create_Code_Combination").equalsIgnoreCase("CreateCodeCombinationAtLeaf")){
							if(levelNo!=0){


								String lastNodeID=gettingLastchildofNode(addedNodeID, "Level_"+String.valueOf(levelNo), typeName);
								//checking the current node is leaf and if it is leaf means get the segment value from the current node
								if(addedNodeID.equals(lastNodeID) || Globals.getAttrVal4AttrName(addedNode, "Last_Node_ID").equals(lastNodeID)){
									Node nd2=Globals.getNodeByAttrVal(doc, selectednodeNameTemp, "ID", selectedNdIDTemp);
									String segmentType1[]=Globals.getAttrVal4AttrName(nd2, "Segment").split("\\$~\\$");

									for(int h=0;h<segmentType1.length;h++){
										for(int i=1;i<=segmentCount;i++){
											if(segmentType1[h].equalsIgnoreCase("Segment_"+i)){
												String segmentNumber=segmentType1[h].substring(segmentType1[h].lastIndexOf("_")+1);
												String temp=String.valueOf(segmentHT.get("Segment_"+i));
												String segmentValue="";
												if(temp.equals("")){
													segmentValue=nd2.getAttributes().getNamedItem("seg"+segmentNumber+"_Code").getNodeValue().replace(";", "~");
													segmentHT.put("Segment_"+i, segmentValue);
												}else if(temp.endsWith("~")){
													segmentValue=temp+nd2.getAttributes().getNamedItem("seg"+segmentNumber+"_Code").getNodeValue().replace(";", "~");
													segmentHT.put("Segment_"+i, segmentValue);
												}else{
													segmentValue=temp+"~"+nd2.getAttributes().getNamedItem("seg"+segmentNumber+"_Code").getNodeValue().replace(";", "~");
													segmentHT.put("Segment_"+i, segmentValue);
												}
												break;
											}
										}
									}
									Node nd1=addedNode;
									String segmentType[]=Globals.getAttrVal4AttrName(nd1, "Segment").split("\\$~\\$");
									boolean isDirectSQL = false;
									if(Globals.getAttrVal4AttrName(nd1, "Data_SubType").equalsIgnoreCase("Segment") || Globals.getAttrVal4AttrName(nd1, "Data_SubType").equalsIgnoreCase("") 
											|| Globals.getAttrVal4AttrName(nd1, "Data_SubType").equalsIgnoreCase("fromData4DirectSQL")){
										isDirectSQL = false;
										for(int h=0;h<segmentType.length;h++){
											for(int i=1;i<=segmentCount;i++){
												if(segmentType[h].equalsIgnoreCase("Segment_"+i)){
													String segmentNumber=segmentType[h].substring(segmentType[h].lastIndexOf("_")+1);
													String temp=String.valueOf(segmentHT.get("Segment_"+i));
													String segmentValue="";
													if(temp.equals("")){
														segmentValue=nd1.getAttributes().getNamedItem("seg"+segmentNumber+"_Code").getNodeValue().replace(";", "~");
														segmentHT.put("Segment_"+i, segmentValue);
													}else if(temp.endsWith("~")){
														segmentValue=temp+nd1.getAttributes().getNamedItem("seg"+segmentNumber+"_Code").getNodeValue().replace(";", "~");
														segmentHT.put("Segment_"+i, segmentValue);
													}else{
														segmentValue=temp+"~"+nd1.getAttributes().getNamedItem("seg"+segmentNumber+"_Code").getNodeValue().replace(";", "~");
														segmentHT.put("Segment_"+i, segmentValue);
													}
													break;
												}
											}
										}
									}else if(Globals.getAttrVal4AttrName(nd1, "Data_SubType").equalsIgnoreCase("DirectSQL")){
										isDirectSQL = true;
										String segValue = "";
										segValue = segValue + String.valueOf(valHT.get(v))+"~";
										segmentHT.put(Globals.getAttrVal4AttrName(nd1, "Primary_Segment"), segValue);

									}
									//sending segmentHT and getting code combination 
									Hashtable codeCombinationvalueHT=Globals.Segment(segmentHT, conn2, hierarchyID,false);
									Hashtable codeCombinationHT = (Hashtable)codeCombinationvalueHT.get(0);
									String typeOfColumn = "";
									typeOfColumn = String.valueOf(codeCombinationvalueHT.get(1));
									String parentRow_wid="";
									String parentCode="";
									parentRow_wid=String.valueOf(basicValueHTtemp.get(0));
									parentCode=String.valueOf(basicValueHTtemp.get(51));
									Hashtable finalValueHT4ReGeneratetemp=new Hashtable<>(); 
									for(int i=0;i<finalValueHT4ReGenerate.size();i++){
										finalValueHT4ReGeneratetemp.put(i, String.valueOf(finalValueHT4ReGenerate.get(i)));
									}
									int batchCount=0;
									int batchSize=1000;
									PreparedStatement pre1=null;
									//				int rowWid = Globals.getMaxID(HIERARCHY_XML_DIR, "Hierarchy_Config.xml", "Row_ID4Oracle", "ID");
									//				int sortOrder = Globals.getMaxID(HIERARCHY_XML_DIR, "Hierarchy_Config.xml", "Sort_Order4Oracle", "ID");
									//				int nodeId = Globals.getMaxID(HIERARCHY_XML_DIR, "Hierarchy_Config.xml", "Node_Level_ID", "ID");

									String sql1=Globals.insertSQL(columnNameHT,"",pre1,"gettingSQL",""); //code chane Vishnu 17Fev2014	// creating sql for batch in db
									pre1=conn2.prepareStatement(sql1);
									//if there is no code combintion for node means dummy codecombination will be inserted
									if(codeCombinationHT.size()==0){
										//					rowWid = rowWid+1;
										//					sortOrder = sortOrder+1;
										//					nodeId = nodeId+1;
										basicValueHTtemp.put(0, Globals.getMaxID(HIERARCHY_XML_DIR, "Hierarchy_Config.xml", "Row_ID4Oracle", "ID"));
										basicValueHTtemp.put(6, Globals.getMaxID(HIERARCHY_XML_DIR, "Hierarchy_Config.xml", "Sort_Order4Oracle", "ID"));
										basicValueHTtemp.put(7, parentRow_wid);
										basicValueHTtemp.put(8, Globals.getMaxID(HIERARCHY_XML_DIR, "Hierarchy_Config.xml", "Node_Level_ID", "ID"));
										basicValueHTtemp.put(10, "Code");
										basicValueHTtemp.put(11, parentCode);
										basicValueHTtemp.put(12, String.valueOf(Globals.getMaxID(HIERARCHY_XML_DIR, hierarchyConfigFile, "Dummy_CodeCombination_ID", "ID")));
										String codeCombination=String.valueOf(Globals.getMaxID(HIERARCHY_XML_DIR, hierarchyConfigFile, "Dummy_CodeCombination_ID", "ID"));
										m=0;
										for(int i=finalValueHT4ReGenerate.size();i<columnNameHT.size();i++){	//in this loop finalvalueHT will be filled with all other values with newly added node
											if(addedName.contains("~")){
												if(m==0){
													finalValueHT4ReGenerate.put(i, codeCombination);
													m++;
												}else if(m==1){
													finalValueHT4ReGenerate.put(i, addedName.split("~")[0]);
													m--;
												}	
											}

										}

										int s=0;
										for(int n=size;n<columnNameHT.size();n++){
											if(String.valueOf(finalValueHT4ReGenerate.get(s)).equalsIgnoreCase(valTemp)){
												basicValueHTtemp.put(n, String.valueOf(valHT.get(v)));
												s++;
											}else if(String.valueOf(finalValueHT4ReGenerate.get(s)).equalsIgnoreCase(nameTemp)){
												basicValueHTtemp.put(n, String.valueOf(nameHT.get(v)));
												s++;
											}else{
												basicValueHTtemp.put(n, String.valueOf(finalValueHT4ReGenerate.get(s)));	//merging finalvalueHT and basicValueHTtemp in basicValueHTtemp
												s++;
											}
										}




										if(typeOfColumn.equalsIgnoreCase("Number")){
											Globals.insertvalue(columnNameHT,basicValueHTtemp,"",pre1,"settingValues4HIERNUM",sql1,conn2,coldetHT,pdatatype);	//insering sql for inserting sql in db
											pre1.addBatch();
										}else{
											Globals.insertvalue(columnNameHT,basicValueHTtemp,"HIER20_NUM",pre,"settingValues",sql,conn2,coldetHT,pdatatype);
											pre.addBatch();
										}
										//					pre1.addBatch();
										dimCount = dimCount+1;
										finalValueHT4ReGenerate=new Hashtable<>();
										for(int i=0;i<finalValueHT4ReGeneratetemp.size();i++){
											finalValueHT4ReGenerate.put(i, String.valueOf(finalValueHT4ReGeneratetemp.get(i)));
										}
									}
									// this loop will insert all codecombination into db for current node 
									for(int j=0;j<codeCombinationHT.size();j++){
										//					rowWid = rowWid+1;
										//					sortOrder = sortOrder+1;
										//					nodeId = nodeId+1;
										basicValueHTtemp.put(0, Globals.getMaxID(HIERARCHY_XML_DIR, "Hierarchy_Config.xml", "Row_ID4Oracle", "ID"));
										basicValueHTtemp.put(6, Globals.getMaxID(HIERARCHY_XML_DIR, "Hierarchy_Config.xml", "Sort_Order4Oracle", "ID"));
										basicValueHTtemp.put(7, parentRow_wid);
										basicValueHTtemp.put(8, Globals.getMaxID(HIERARCHY_XML_DIR, "Hierarchy_Config.xml", "Node_Level_ID", "ID"));
										basicValueHTtemp.put(10, "Code");
										basicValueHTtemp.put(11, parentCode);
										basicValueHTtemp.put(12, String.valueOf(codeCombinationHT.get(j)));
										String codeCombination=String.valueOf(codeCombinationHT.get(j));
										m=0;
										for(int i=finalValueHT4ReGenerate.size();i<columnNameHT.size();i++){	//in this loop finalvalueHT will be filled with all other values with newly added node
											if(addedName.contains("~")){
												if(m==0){
													finalValueHT4ReGenerate.put(i, codeCombination);
													m++;
												}else if(m==1){
													finalValueHT4ReGenerate.put(i, addedName.split("~")[0]);
													m--;
												}	
											}

										}
										int s=0;
										for(int n=size;n<columnNameHT.size();n++){
											if(String.valueOf(finalValueHT4ReGenerate.get(s)).equalsIgnoreCase(valTemp)){
												basicValueHTtemp.put(n, String.valueOf(valHT.get(v)));
												s++;
											}else if(String.valueOf(finalValueHT4ReGenerate.get(s)).equalsIgnoreCase(nameTemp)){
												basicValueHTtemp.put(n, String.valueOf(nameHT.get(v)));
												s++;
											}else{
												basicValueHTtemp.put(n, String.valueOf(finalValueHT4ReGenerate.get(s)));	//merging finalvalueHT and basicValueHTtemp in basicValueHTtemp
												s++;
											}
										}
										System.out.println("Start insertion===>"+sdf.format(new Date()));
										if(typeOfColumn.equalsIgnoreCase("Number")){
											Globals.insertvalue(columnNameHT,basicValueHTtemp,"",pre1,"settingValues4HIERNUM",sql1,conn2,coldetHT,pdatatype);	//insering sql for inserting sql in db
											pre1.addBatch();
										}else{
											Globals.insertvalue(columnNameHT,basicValueHTtemp,"HIER20_NUM",pre,"settingValues",sql,conn2,coldetHT,pdatatype);
											pre.addBatch();
										}
										System.out.println("End insertion===>"+sdf.format(new Date()));
										//					Globals.insertvalue(columnNameHT,basicValueHTtemp,"",pre1,"settingValues",sql1,conn2,coldetHT,pdatatype);	//insering sql for inserting sql in db
										//					System.out.println("===>j"+j);
										dimCount = dimCount+1;

										if((++batchCount%batchSize)==0){
											pre1.executeBatch();

											batchCount=0;
											System.out.println("------------------INSERT BATCH EXECUTED----------------------");

										}

										finalValueHT4ReGenerate=new Hashtable<>();
										for(int i=0;i<finalValueHT4ReGeneratetemp.size();i++){
											finalValueHT4ReGenerate.put(i, String.valueOf(finalValueHT4ReGeneratetemp.get(i)));
										}
									}
									pre1.executeBatch();
									pre1.close();
									pre.close();
									//				System.out.println("row_wid after"+rowWid);
									//				Globals.updateMaxID(HIERARCHY_XML_DIR, hierarchyConfigFile, "Sort_Order4Oracle", "ID", String.valueOf(sortOrder+2));
									//				Globals.updateMaxID(HIERARCHY_XML_DIR, hierarchyConfigFile, "Row_ID4Oracle", "ID", String.valueOf(rowWid+2));
									//				Globals.updateMaxID(HIERARCHY_XML_DIR, hierarchyConfigFile, "Node_Level_ID", "ID", String.valueOf(nodeId+2));


								}
							}
							Element addedele1=(Element)addedNode;
							addedele1.setAttribute("Last_Node_ID", String.valueOf(basicValueHTtemp.get(8)));
							basicValueHTtemp.put(7, parentRowWid);
							basicValueHTtemp.put(0, Globals.getMaxID(HIERARCHY_XML_DIR, "Hierarchy_Config.xml", "Row_ID4Oracle", "ID"));
							basicValueHTtemp.put(6, Globals.getMaxID(HIERARCHY_XML_DIR, "Hierarchy_Config.xml", "Sort_Order4Oracle", "ID"));
							basicValueHTtemp.put(8, Globals.getMaxID(HIERARCHY_XML_DIR, "Hierarchy_Config.xml", "Node_Level_ID", "ID"));
							basicValueHTtemp.put(10, "");
						}
					}else{
						if(nameHT.size()!=1){
							basicValueHTtemp.put(0, Globals.getMaxID(HIERARCHY_XML_DIR, "Hierarchy_Config.xml", "Row_ID4Oracle", "ID"));
							basicValueHTtemp.put(6, Globals.getMaxID(HIERARCHY_XML_DIR, "Hierarchy_Config.xml", "Sort_Order4Oracle", "ID"));
							basicValueHTtemp.put(8, Globals.getMaxID(HIERARCHY_XML_DIR, "Hierarchy_Config.xml", "Node_Level_ID", "ID"));
						}
					}

				}
				System.out.println("End of nameHT loop===>"+sdf.format(new Date()));
				System.out.println("------------------INSERT BATCH EXECUTED - NODE ----------------------");
				/////////////////////////////End Code Change Gokul 22FEB2014
				//updating largest sortorder in cofigxml 
				String updatesortorder="SELECT MAX(SORT_ORDER) FROM WC_FLEX_HIERARCHY_D";	
				PreparedStatement updatesortorderpre=conn2.prepareStatement(updatesortorder);
				ResultSet re=updatesortorderpre.executeQuery();
				int i=0;
				while(re.next()){
					System.out.println("===>"+i++);
					Globals.updateMaxID(HIERARCHY_XML_DIR, hierarchyConfigFile, "Sort_Order4Oracle", "ID", String.valueOf(re.getInt(1)));
				}
				updatesortorderpre.close(); // &&&&&&
				re.close();

			}else{	//if current node is hierarchy

				String sql="SELECT PARENT_ROW_WID,ROW_WID,XML_NODE_ID,";
				for(int i=10;i<columnNameHT.size();i++){
					sql=sql+String.valueOf(columnNameHT.get(i))+",";
				}
				sql=sql.substring(0,sql.length()-1);
				sql=sql+" FROM WC_FLEX_HIERARCHY_D WHERE HIER_NAME = '"+addedName+"' ORDER BY SORT_ORDER";	//getting inserted hierarchy values from db


				PreparedStatement pre=conn2.prepareStatement(sql);
				ResultSet rs=pre.executeQuery();
				String selectedNodeType="";
				int l=finalValueHT.size();
				int rowID=0;
				int sortorder=0;
				String selectedNdIDtemp="";
				Node selectedNode=Globals.getNodeByAttrVal(doc, selectednodeName, "ID", selectedNdID);
				if (SOP_ENABLED)
					System.out.println("selectedNdID ==>"+selectedNdID);
				String selectedNdIDTemp=selectedNdID;
				String selectednodeNameTemp=selectednodeName;
				selectedNodeType=Globals.getAttrVal4AttrName(selectedNode, "Type");

				Node subHierarchyNd=Globals.getNodeByAttrVal(doc, "Hierarchy_Level", "Hierarchy_Name", addedName);
				//this will process all node of a hierarchy and insert all node attributes to a hashtable (nodeHT)		
				Hashtable nodeNameandTypeHT4Segment=new Hashtable<>();
				Hashtable nodeHT=new Hashtable<>();
				Globals.processUpDowninDB(subHierarchyNd, nodeHT, 1, nodeNameandTypeHT4Segment);

				Hashtable codeCombinationHT=new Hashtable<>();

				int hierarchyRowCount=0;

				// If CreateCodeCombinationInDim is TRUE and selected Node is a Rollup
				// Process the Hierarchy and insert in the DB
				//
				if(Globals.getAttrVal4AttrName(hierarchyNd, "Code_Combination").equalsIgnoreCase("CreateCodeCombinationInDim")
						&& selectedNodeType.equalsIgnoreCase("RollUp")){	//code change Vishnu 17Feb2014
					Hashtable hierarchyLevelHT=new Hashtable<>();
					Node nd=Globals.getNodeByAttrVal(doc, selectednodeName, "ID", selectedNdID);

					int v=0;
					boolean rollupFlag=false;
					String rollupID="";
					String rollupNodeName="";
					// In this loop getting the parent node values of rollup Hierarchy and inserted into hierarchyLevelHT
					for(int i=levelNo;i>0;i++){

						if(nd.getNodeType()==Node.ELEMENT_NODE){
							// In this getting node id and name of the parent node of rollup hierarchy(Node that is immedieate parent of rollup hierarchy)
							if(nd.getAttributes().getNamedItem("Type").getNodeValue().equalsIgnoreCase("RollUp")){
								if(!rollupFlag){
									rollupID=nd.getAttributes().getNamedItem("ID").getNodeValue();
									rollupNodeName=nd.getNodeName();
									rollupFlag=true;
								}
							}
						}


						if(rollupFlag){

							if(nd.getNodeType()==Node.ELEMENT_NODE && nd.getNodeName().equalsIgnoreCase("RootLevel")){
								hierarchyLevelHT.put(v, nd.getAttributes().getNamedItem("value").getNodeValue());
								v++;
								break;
							}else{
								hierarchyLevelHT.put(v, nd.getAttributes().getNamedItem("value").getNodeValue());
								v++;
								nd=nd.getParentNode();
								continue;
							}
						}
						nd=nd.getParentNode();

					}

					if (SOP_ENABLED)
						System.out.println("hierarchyLevelHT=====>"+hierarchyLevelHT);
					Hashtable segmentHTTemp=new Hashtable<>();
					for(int i=1;i<=segmentCount;i++){
						segmentHTTemp.put("Segment_"+i, String.valueOf(segmentHT.get("Segment_"+i)));
					}

					//			segmentHTTemp=segmentHT;
					Hashtable rollupHT=new Hashtable<>();
					int y=(hierarchyLevelHT.size()*2)-1;
					// in this values will be alligned in rollupHT like in db
					for(int i=0;i<hierarchyLevelHT.size()*2;i++){

						rollupHT.put(y, String.valueOf(hierarchyLevelHT.get((i/2))));
						y--;
					}
					Hashtable basicValueHTtmp=new Hashtable<>();
					for(int i=0;i<basicValueHTtemp.size();i++){
						basicValueHTtmp.put(i, String.valueOf(basicValueHTtemp.get(i)));

					}
					rollupHierarchy(rollupHT,rollupID,rollupNodeName,doc,rs,nodeHT,valueMissedsegmentHT,
							segmentHTTemp,basicValueHTtmp,addedName,hierarchyID,hierName,addedNode,conn2,segmentCount,nodeNameandTypeHT4Segment,addedNodeID,coldetHT,pdatatype,dimCount);
					Element addedele=(Element)addedNode;
					addedele.setAttribute("Hierarchy_Type", "RollUpHierarchy");

				}else{	//code change Vishnu 17Feb2014


					Node nd1=Globals.getNodeByAttrVal(doc, selectednodeNameTemp, "ID", selectedNdIDTemp);
					if(Globals.getAttrVal4AttrName(nd1, "Segment").contains("$~$")){
						String segmentType[]=Globals.getAttrVal4AttrName(nd1, "Segment").split("\\$~\\$");
						// Process each Segment (Segment 1, Segment 2)... and
						// load the Segment Values
						for(int h=0;h<segmentType.length;h++){
							for(int i=1;i<=segmentCount;i++){
								if(segmentType[h].equalsIgnoreCase("Segment_"+i)){
									String segmentNumber=segmentType[h].substring(segmentType[h].lastIndexOf("_")+1);
									String temp=String.valueOf(segmentHT.get("Segment_"+i));
									String segmentValue="";
									if(temp.equals("")){
										segmentValue=nd1.getAttributes().getNamedItem("seg"+segmentNumber+"_Code").getNodeValue().replace(";", "~");
										segmentHT.put("Segment_"+i, segmentValue);
									}else if(temp.endsWith("~")){
										segmentValue=temp+nd1.getAttributes().getNamedItem("seg"+segmentNumber+"_Code").getNodeValue().replace(";", "~");
										segmentHT.put("Segment_"+i, segmentValue);
									}else{
										segmentValue=temp+"~"+nd1.getAttributes().getNamedItem("seg"+segmentNumber+"_Code").getNodeValue().replace(";", "~");
										segmentHT.put("Segment_"+i, segmentValue);
									}
									break;
								}
							}
						}
					}else{
						for(int i=0;i<valueMissedsegmentHT.size();i++){

							String segmentType[]=Globals.getAttrVal4AttrName(nd1, "Segment").split("~");
							for(int h=0;h<segmentType.length;h++){
								if(segmentType[h].equalsIgnoreCase(String.valueOf(valueMissedsegmentHT.get(i)))){

									segmentHT.put(String.valueOf(valueMissedsegmentHT.get(i)), nd1.getAttributes().getNamedItem("Code").getNodeValue());
								}
							}
						}
					}
					// in this we use the parent node id to get the row_wid and sort_order from db.
					System.out.println("previoustype4ReGenerate hierarchy===>"+previoustype4ReGenerate);
					System.out.println("Start ROW_WID in Hierarchy===>"+sdf.format(new Date()));
					if(previoustype4ReGenerate.contains("#~#")){

						//			if(previoustype4ReGenerate.split("#~#")[0].equalsIgnoreCase("Hierarchy")){
						String sql4RowCount="SELECT ROW_WID,SORT_ORDER FROM WC_FLEX_HIERARCHY_D WHERE XML_NODE_ID = '"+previoustype4ReGenerate.split("#~#")[1]+"'";

						PreparedStatement pre4RowCount=conn2.prepareStatement(sql4RowCount);
						ResultSet rs2=pre4RowCount.executeQuery();

						while(rs2.next()){
							rowID=rs2.getInt("ROW_WID");
							sortorder=rs2.getInt("SORT_ORDER");
							if (SOP_ENABLED)
								System.out.println("sortorder inside rs===>"+sortorder);
						}
						pre4RowCount.close();
						rs2.close();
						//			}

					}else{
						String sql4RowCount="SELECT ROW_WID,SORT_ORDER FROM WC_FLEX_HIERARCHY_D WHERE XML_NODE_ID = '"+selectedNdID+"'";

						PreparedStatement pre4RowCount=conn2.prepareStatement(sql4RowCount);
						ResultSet rs2=pre4RowCount.executeQuery();

						while(rs2.next()){
							rowID=rs2.getInt("ROW_WID");
							sortorder=rs2.getInt("SORT_ORDER");
							if (SOP_ENABLED)
								System.out.println("sortorder inside rs===>"+sortorder);
						}
						pre4RowCount.close();
						rs2.close();
					}
					System.out.println("End ROW_WID in Hierarchy===>"+sdf.format(new Date()));
					if (SOP_ENABLED)
						System.out.println("segmentHT=====>"+segmentHT);
					int parentrow=sortorder+1;
					String codeValue="";

					int parentcode=0;
					int oldparent=0;


					Hashtable parentRowWidHT=new Hashtable<>();
					Hashtable parentRowWidHTTemp=new Hashtable<>();
					PreparedStatement pre1=null;
					PreparedStatement pre2=null;

					int batchCount=0;
					int batchSize=1000;
					String sql1=Globals.insertSQL(columnNameHT,"HIER20_NUM",pre1,"gettingSQL",""); //code chane Vishnu 17Fev2014
					String sqlHIER20_NUM=Globals.insertSQL(columnNameHT,"",pre2,"gettingSQL",""); //code chane Vishnu 17Fev2014

					pre1=conn2.prepareStatement(sql1);
					pre2=conn2.prepareStatement(sqlHIER20_NUM);	
					Hashtable segtempHT=new Hashtable<>();
					boolean segFlag=true;
					//		int rowWid = Globals.getMaxID(HIERARCHY_XML_DIR, "Hierarchy_Config.xml", "Row_ID4Oracle", "ID");
					//		int sortOrder = Globals.getMaxID(HIERARCHY_XML_DIR, "Hierarchy_Config.xml", "Sort_Order4Oracle", "ID");
					//		int nodeId = Globals.getMaxID(HIERARCHY_XML_DIR, "Hierarchy_Config.xml", "Node_Level_ID", "ID");
					boolean createCodeCombinflag = false;
					//this is result set of innerhierarchy 
					System.out.println("Start ROW insert Hierarchy===>"+sdf.format(new Date()));
					while(rs.next()){


						boolean isParent4CodeCombination=false;
						// it enters if Code_Combination is CreateCodeCombinationInDim
						if(Globals.getAttrVal4AttrName(hierarchyNd, "Code_Combination").equalsIgnoreCase("CreateCodeCombinationInDim")){
							codeCombinationHT=new Hashtable<>();
							boolean oldCodeChange=false;
							Hashtable tempHT=new Hashtable<>();
							Hashtable codeCombinationvalueHT = new Hashtable<>();
							String typeOfColumn = "";
							//getting currnt node information in tempHt
							System.out.println("Start codecombination Hierarchy===>"+sdf.format(new Date()));
							for(int key1=0;key1<nodeHT.size();key1++){

								tempHT=(Hashtable) nodeHT.get(key1);
								if(String.valueOf(tempHT.get("ID")).equals(rs.getString("XML_NODE_ID"))){
									break;
								}
							}
							System.out.println("Mid codecombination Hierarchy===>"+sdf.format(new Date()));
							if(String.valueOf(tempHT.get("NodeName")).equals("RootLevel") || String.valueOf(tempHT.get("NodeName")).startsWith("Level_")){


								// getting leaf node ID of the current node 	
								String lastNodeID=gettingLastchildofNode(String.valueOf(tempHT.get("ID")), String.valueOf(tempHT.get("NodeName")), String.valueOf(tempHT.get("Type")));
								//if current node is leaf 
								if(lastNodeID.equals(String.valueOf(tempHT.get("ID"))) || lastNodeID.equals(String.valueOf(tempHT.get("Last_Node_ID")))){
									if(String.valueOf(tempHT.get("ID")).equals(rs.getString("XML_NODE_ID"))){
										//if current node Create_Code_Combination is ItselfCodeCombination(We wont get code combination)
										if(String.valueOf(tempHT.get("Create_Code_Combination")).equals("ItselfCodeCombination")){
											createCodeCombinflag = true;
											oldCodeChange = false;
										}else{
											//if segment attribute does not have delimiter $~$ means get the segment value from current and get code combination in codeCombinationHt
											if(!String.valueOf(tempHT.get("Segment")).contains("$~$")){
												//								oldCodeChange=true;
												codeCombinationHT=new Hashtable<>();
												//								for(int key=0;key<nodeNameandTypeHT4Segment.size();key++){

												String dataSegment=String.valueOf(tempHT.get("Code"));

												if(dataSegment.equals(rs.getString("HIER20_CODE"))
														&& String.valueOf(tempHT.get("Type")).equalsIgnoreCase("Data")){
													for(int i=0;i<valueMissedsegmentHT.size();i++){
														if(String.valueOf(tempHT.get("Segment")).equalsIgnoreCase(String.valueOf(valueMissedsegmentHT.get(i)))){
															segmentHT.put(String.valueOf(valueMissedsegmentHT.get(i)), rs.getString("HIER20_CODE"));
															codeValue=rs.getString("HIER20_CODE");

														}
													}
													//								codeCombinationHT=Globals.Segment(segmentHT,conn2,hierarchyID,false);
													System.out.println("Start Segment===>"+sdf.format(new Date()));
													codeCombinationvalueHT=Globals.Segment(segmentHT,conn2,hierarchyID,false);
													System.out.println("End Segment===>"+sdf.format(new Date()));
													codeCombinationHT = (Hashtable) codeCombinationvalueHT.get(0);
													typeOfColumn = String.valueOf(codeCombinationvalueHT.get(1));
													isParent4CodeCombination=true;
													if(codeCombinationHT.size()<=0){
														codeCombinationHT.put(0, "null");
														typeOfColumn = "Number";
													}else{

													}
												}
											}else{
												createCodeCombinflag = false;
												String dataSegment="";
												oldCodeChange=false;

												String segments[]=String.valueOf(tempHT.get("Segment")).split("\\$~\\$");
												boolean isDirectSQL = false;
												for(int val=0;val<segments.length;val++){

													if(segFlag){

														segtempHT.put(segments[val], String.valueOf(segmentHT.get(segments[val])));
													}
													String segmentNumber="";
													segmentNumber=segments[val].substring(segments[val].lastIndexOf("_")+1);
													dataSegment=String.valueOf(tempHT.get("seg"+segmentNumber+"_Code"));
													//getting the multisegment values and insert it in segmentHt and get code combination in codeCombinationHT
													dataSegment=dataSegment.replace(";", "~");
													if(String.valueOf(tempHT.get("Type")).equalsIgnoreCase("Data")){
														//							if(String.valueOf(tempHT.get("Data_SubType")).equalsIgnoreCase("Segment") || String.valueOf(tempHT.get("Data_SubType")).equalsIgnoreCase("null")
														//									|| String.valueOf(tempHT.get("Data_SubType")).equalsIgnoreCase("fromData4DirectSQL")){
														isDirectSQL = false;
														for(int j=1;j<=segmentCount;j++){

															if(segments[val].equalsIgnoreCase("Segment_"+j)){
																String temp=String.valueOf(segtempHT.get(segments[val]));
																String segmentValue="";
																if(temp.equals("")){
																	segmentValue=dataSegment.replace(";", "~");
																	segmentHT.put("Segment_"+j, segmentValue);
																}else if(temp.endsWith("~")){
																	segmentValue=temp+dataSegment.replace(";", "~");
																	segmentHT.put("Segment_"+j, segmentValue);
																}else{
																	segmentValue=temp+"~"+dataSegment.replace(";", "~");
																	segmentHT.put("Segment_"+j, segmentValue);
																}
																codeValue=rs.getString("HIER20_CODE");

															}
														}
														////							}else if(String.valueOf(tempHT.get("Data_SubType")).equalsIgnoreCase("DirectSQL")){
														//								isDirectSQL = false;
														//								String segSQL = String.valueOf(tempHT.get("Custom_SQL"));
														//								System.out.println("segSQL===>"+segSQL);
														//								PreparedStatement ps1 = con.prepareStatement(segSQL);
														//								ResultSet rs1 = ps1.executeQuery();
														//								String segValue = "";
														//								while (rs1.next()) {
														//									segValue = segValue + rs1.getString(1)+"~";
														//									segmentHT.put(String.valueOf(tempHT.get("Primary_Segment")), segValue);
														//									
														//									
														//								}
														//								rs1.close();
														//								ps1.close();
														//								codeValue=rs.getString("HIER20_CODE");
														//							}
													}
												}

												System.out.println("Start Segment===>"+sdf.format(new Date()));
												codeCombinationvalueHT=Globals.Segment(segmentHT,conn2,hierarchyID,false);
												System.out.println("End Segment===>"+sdf.format(new Date()));
												codeCombinationHT = (Hashtable) codeCombinationvalueHT.get(0);
												typeOfColumn = String.valueOf(codeCombinationvalueHT.get(1));
												isParent4CodeCombination=true;
												if(codeCombinationHT.size()<=0){
													typeOfColumn = "Number";
													codeCombinationHT.put(0, "null");
												}else{

												}
												segFlag=false;
												//					break;
											}
										}
									}
								}
							}
							System.out.println("End codecombination Hierarchy===>"+sdf.format(new Date()));

							boolean isCodeCombination=false;
							System.out.println("Start codeCombinationHT Loop===>"+sdf.format(new Date()));
							// loops for the code combination of current node
							for(int key=0;key<codeCombinationHT.size();key++){
								//			t=0;
								String columnName2Exclude="";
								boolean isCodeCombinationtemp=false;
								//inserting code combination for current node
								if(isCodeCombination){	//code change Vishnu 17Feb2014
									String dummyCodeCombination="";
									dummyCodeCombination=String.valueOf(Globals.getMaxID(HIERARCHY_XML_DIR, hierarchyConfigFile, "Dummy_CodeCombination_ID", "ID"));
									if(String.valueOf(codeCombinationHT.get(key)).equalsIgnoreCase("null")){	//if code combiation is null means insert dummy code combination

										for(int i=13;i<columnNameHT.size();i++){

											if(l<columnNameHT.size()){	
												if(rs.getString(String.valueOf(columnNameHT.get(i))).equals(rs.getString("HIER20_CODE"))){
													if(isCodeCombinationtemp){

														finalValueHT.put(l, dummyCodeCombination);
														l++;
													}else{
														finalValueHT.put(l, rs.getString(String.valueOf(columnNameHT.get(i))));
														l++;
														isCodeCombinationtemp=true;
													}

												}else{
													finalValueHT.put(l, rs.getString(String.valueOf(columnNameHT.get(i))));
													l++;
												}

											}
										}
										basicValueHTtemp.put(11, codeValue);
										basicValueHTtemp.put(10, "Code");
										basicValueHTtemp.put(12, dummyCodeCombination);
										basicValueHTtemp.put(7, parentRowWidHTTemp.get(String.valueOf(basicValueHTtemp.get(11))));
										columnName2Exclude="";
									}else{	
										for(int i=13;i<columnNameHT.size();i++){

											if(l<columnNameHT.size()){	
												if(rs.getString(String.valueOf(columnNameHT.get(i))).equals(rs.getString("HIER20_CODE"))){
													if(isCodeCombinationtemp){
														finalValueHT.put(l, String.valueOf(codeCombinationHT.get(key)));
														l++;
													}else{
														finalValueHT.put(l, rs.getString(String.valueOf(columnNameHT.get(i))));
														l++;
														isCodeCombinationtemp=true;
													}
												}else{
													finalValueHT.put(l, rs.getString(String.valueOf(columnNameHT.get(i))));
													l++;
												}

											}
										}
										boolean iscodeCombinationinserted=false;
										System.out.println("Start Duplication check===>"+sdf.format(new Date()));
										String codecombinationSQL="Select * from WC_FLEX_HIERARCHY_D where hier20_code='"+String.valueOf(codeCombinationHT.get(key))+"' AND HIERARCHY_ID='"+hierarchyID+"'";

										PreparedStatement codecombinationpre=conn2.prepareStatement(codecombinationSQL);
										ResultSet codecombinationrs=codecombinationpre.executeQuery();
										while(codecombinationrs.next()){
											iscodeCombinationinserted=true;

										}
										System.out.println("End Duplication check===>"+sdf.format(new Date()));
										if(iscodeCombinationinserted){
											basicValueHTtemp.put(12, -1);
											basicValueHTtemp.put(11, "null");
											basicValueHTtemp.put(10, "null");
											columnName2Exclude="HIER20_NUM";
										}else{
											basicValueHTtemp.put(12, codeCombinationHT.get(key));
											basicValueHTtemp.put(11, codeValue);
											basicValueHTtemp.put(10, "Code");
											columnName2Exclude="";
										}
										codecombinationpre.close();
										codecombinationrs.close();
										basicValueHTtemp.put(7, parentRowWidHTTemp.get(codeValue));

									}

								}else{	//inserting parent value of the code combination for first time
									for(int i=13;i<columnNameHT.size();i++){

										if(l<columnNameHT.size()){	

											finalValueHT.put(l, rs.getString(String.valueOf(columnNameHT.get(i))));
											l++;


										}
									}
									basicValueHTtemp.put(12, -1);

									key--;
									isCodeCombination=true;
									columnName2Exclude="HIER20_NUM";
									basicValueHTtemp.put(11, "null");
									basicValueHTtemp.put(10, "Parent_Code");
									System.out.println("parentRowWidHT 2==>"+parentRowWidHT);
									System.out.println("rs.getInt()==>"+rs.getInt("PARENT_ROW_WID"));
									basicValueHTtemp.put(7, parentRowWidHT.get(rs.getInt("PARENT_ROW_WID")));
									parentRowWidHTTemp.put(rs.getString("HIER20_CODE"), String.valueOf(basicValueHTtemp.get(0)));
								}
								//end code change Vishnu 17Feb2014

								System.out.println("Start sortorder in hierrarchy===>"+sdf.format(new Date()));	
								//updating sort order in DB when sortOrder is greater than 0
								if(sortorder==0){
									//				sortOrder = sortOrder+1;
									basicValueHTtemp.put(6, Globals.getMaxID(HIERARCHY_XML_DIR, "Hierarchy_Config.xml", "Sort_Order4Oracle", "ID"));
								}else{
									basicValueHTtemp.put(6, sortorder+1);
									String sqlUpdate="UPDATE WC_FLEX_HIERARCHY_D SET SORT_ORDER=SORT_ORDER+1 WHERE SORT_ORDER >= "+(sortorder+1);
									PreparedStatement update4RowCount=conn2.prepareStatement(sqlUpdate);
									update4RowCount.execute();
									update4RowCount.close();
								}
								System.out.println("End sortorder in hierrarchy===>"+sdf.format(new Date()));
								int h=0;
								basicValueHTtemp.put(9, addedName);
								//concating basic information of a node(hierarcyName) and values of the node(hier1 to hier20)
								for(int a=basicValueHTtemp.size();a<columnNameHT.size();a++){
									basicValueHTtemp.put(a, String.valueOf(finalValueHT.get(h)));
									h++;
								}
								l=count;
								System.out.println("Start Insert data in hierarchy===>"+sdf.format(new Date()));
								// this will exclude hier20Num while inserting if the inserted value doesnt have code combination
								if(columnName2Exclude.equalsIgnoreCase("HIER20_NUM")){

									Globals.insertvalue(columnNameHT,basicValueHTtemp,"HIER20_NUM",pre1,"settingValues",sql1,conn2,coldetHT,pdatatype);
									pre1.addBatch();
									dimCount = dimCount+1;
								}else{
									if(typeOfColumn.equalsIgnoreCase("Number")){
										Globals.insertvalue(columnNameHT,basicValueHTtemp,"",pre2,"settingValues4HIERNUM",sqlHIER20_NUM,conn2,coldetHT,pdatatype);	//insering sql for inserting sql in db
										pre2.addBatch();
									}else{
										Globals.insertvalue(columnNameHT,basicValueHTtemp,"HIER20_NUM",pre1,"settingValues",sql,conn2,coldetHT,pdatatype);
										pre1.addBatch();
									}
									//				Globals.insertvalue(columnNameHT,basicValueHTtemp,"",pre2,"settingValues4HIERNUM",sqlHIER20_NUM,conn2,coldetHT,pdatatype);

									dimCount = dimCount+1;

								}
								System.out.println("End Insert data in hierarchy===>"+sdf.format(new Date()));
								Element addedele=(Element)addedNode;
								addedele.setAttribute("Last_Node_ID", String.valueOf(basicValueHTtemp.get(8)));
								System.out.println("parentRowWidHT 1==>"+batchCount);
								parentRowWidHT.put(rs.getInt("ROW_WID"), String.valueOf(basicValueHTtemp.get(0)));
								//			nodeId = nodeId+1;
								//			rowWid = rowWid+1;
								String hierID=String.valueOf(Globals.getMaxID(HIERARCHY_XML_DIR, "Hierarchy_Config.xml", "Node_Level_ID", "ID"));
								basicValueHTtemp=new Hashtable<>();
								basicValueHTtemp.put(0, Globals.getMaxID(HIERARCHY_XML_DIR, "Hierarchy_Config.xml", "Row_ID4Oracle", "ID"));
								basicValueHTtemp.put(1, "GL");
								basicValueHTtemp.put(2, String.valueOf(basicValueHT.get(2)));
								basicValueHTtemp.put(3, String.valueOf(basicValueHT.get(3)));
								basicValueHTtemp.put(4, String.valueOf(basicValueHT.get(4)));
								basicValueHTtemp.put(5, String.valueOf(basicValueHT.get(5)));
								basicValueHTtemp.put(6, sortorder+1);

								basicValueHTtemp.put(8, hierID);
								basicValueHTtemp.put(9, addedName);
								sortorder=sortorder+1;
								if((++batchCount%batchSize)==0){
									pre1.executeBatch();
									pre2.executeBatch();
									batchCount=0;
									System.out.println("------------------INSERT BATCH EXECUTED----------------------");

								}
							}
							System.out.println("End codeCombinationHT Loop===>"+sdf.format(new Date()));
							System.out.println("Start normal node update hierarchy===>"+sdf.format(new Date()));
							if(codeCombinationHT.size()<=0){	//if current node is not a data node or leaf node
								t=0;
								Hashtable valHT = new Hashtable<>();
								Hashtable nameHT = new Hashtable<>();
								String val = "";
								String name = "";
								if(String.valueOf(tempHT.get("Data_SubType")).equalsIgnoreCase("DirectSQL")){
									String segSQL = String.valueOf(tempHT.get("Custom_SQL"));
									System.out.println("segSQL===>"+segSQL);
									PreparedStatement ps1 = con.prepareStatement(segSQL);
									ResultSet rs1 = ps1.executeQuery();
									String segValue = "";
									int a = 0;
									val = String.valueOf(tempHT.get("Code"));
									name = String.valueOf(tempHT.get("value"));
									while (rs1.next()) {
										//						segValue = segValue + rs1.getString(1)+"~";
										valHT.put(a, rs1.getString(1));
										nameHT.put(a, rs1.getString(2));
										a++;
									}
									rs1.close();
									ps1.close();
									codeValue=rs.getString("HIER20_CODE");
								}else{
									val = String.valueOf(tempHT.get("Code"));
									name = String.valueOf(tempHT.get("value"));
									int a = 0;
									valHT.put(a, String.valueOf(tempHT.get("Code")));
									if(String.valueOf(tempHT.get("Type")).equalsIgnoreCase("Data")){
										name = String.valueOf(tempHT.get("value")).substring(0, String.valueOf(tempHT.get("value")).lastIndexOf("("));
										nameHT.put(a, name);
									}else{
										name = String.valueOf(tempHT.get("value"));
										nameHT.put(a, String.valueOf(tempHT.get("value")));
									}
								}
								for(int v=0;v<nameHT.size();v++){
									//concating basic information of a node(hierarcyName) and values of the node(hier1 to hier20)
									for(int i=13;i<columnNameHT.size();i++){

										if(l<columnNameHT.size()){
											if(rs.getString(String.valueOf(columnNameHT.get(i))).equalsIgnoreCase(val)){
												finalValueHT.put(l, String.valueOf(valHT.get(v)));
												l++;
											}else if(rs.getString(String.valueOf(columnNameHT.get(i))).equalsIgnoreCase(name)){
												finalValueHT.put(l, String.valueOf(nameHT.get(v)));
												l++;
											}else{
												finalValueHT.put(l, rs.getString(String.valueOf(columnNameHT.get(i))));
												l++;
											}
										}
									}
									basicValueHTtemp.put(11, "null");
									System.out.println("parentRowWidHT==>"+parentRowWidHT);
									//getting the parent row_wid of the current node
									if(rs.getString("PARENT_ROW_WID") != null && !rs.getString("PARENT_ROW_WID").equalsIgnoreCase("null")){ // 15 Feb 14
										if(parentRowWidHT.size()<=0){	//code change Vishnu 18Feb2014
											basicValueHTtemp.put(7, rowID);
										}else{
											basicValueHTtemp.put(7, parentRowWidHT.get(rs.getInt("PARENT_ROW_WID")));
										}
									}else{
										basicValueHTtemp.put(7, rowID);
									}
									System.out.println("Start sort order hierarchy===>"+sdf.format(new Date()));
									//updating sort order in DB when sortOrder is greater than 0(sortorder will be 0 if current node is first node)
									if(sortorder==0){
										//					sortOrder = sortOrder+1;
										basicValueHTtemp.put(6, Globals.getMaxID(HIERARCHY_XML_DIR, "Hierarchy_Config.xml", "Sort_Order4Oracle", "ID"));
									}else{
										basicValueHTtemp.put(6, sortorder+1);
										String sqlUpdate="UPDATE WC_FLEX_HIERARCHY_D SET SORT_ORDER=SORT_ORDER+1 WHERE SORT_ORDER >= "+(sortorder+1);
										PreparedStatement update4RowCount=conn2.prepareStatement(sqlUpdate);
										update4RowCount.execute();
										update4RowCount.close();
									}
									System.out.println("End sort order hierarchy===>"+sdf.format(new Date()));
									int h=0;
									basicValueHTtemp.put(9, addedName);
									//				basicValueHTtemp.put(10, "null");
									if(isParent4CodeCombination){
										basicValueHTtemp.put(10, "Parent_Code");
										parentRowWidHTTemp.put(rs.getString("HIER20_CODE"), String.valueOf(basicValueHTtemp.get(0)));
									}else{
										//if current node Create_code_Combination is ItselfCodeCombination
										if(createCodeCombinflag){
											basicValueHTtemp.put(10, "Code");	
										}else{
											basicValueHTtemp.put(10, "null");	
										}

									}
									basicValueHTtemp.put(12, -1);//code change Vishnu 17Feb2014
									for(int a=basicValueHTtemp.size();a<columnNameHT.size();a++){

										basicValueHTtemp.put(a, String.valueOf(finalValueHT.get(h)));
										h++;
									}
									l=count;

									Globals.insertvalue(columnNameHT,basicValueHTtemp,"HIER20_NUM",pre1,"settingValues",sql1,conn2,coldetHT,pdatatype);
									dimCount = dimCount+1;
									pre1.addBatch();

									parentRowWidHT.put(rs.getInt("ROW_WID"), String.valueOf(basicValueHTtemp.get(0)));

									Element addedele=(Element)addedNode;
									addedele.setAttribute("Last_Node_ID", String.valueOf(basicValueHTtemp.get(8)));

									//				nodeId = nodeId+1;
									//				rowWid = rowWid+1;
									String hierID=String.valueOf(Globals.getMaxID(HIERARCHY_XML_DIR, "Hierarchy_Config.xml", "Node_Level_ID", "ID"));
									basicValueHTtemp=new Hashtable<>();

									basicValueHTtemp.put(0, Globals.getMaxID(HIERARCHY_XML_DIR, "Hierarchy_Config.xml", "Row_ID4Oracle", "ID"));
									basicValueHTtemp.put(1, "GL");
									basicValueHTtemp.put(2, String.valueOf(basicValueHT.get(2)));
									basicValueHTtemp.put(3, String.valueOf(basicValueHT.get(4)));
									basicValueHTtemp.put(4, String.valueOf(basicValueHT.get(4)));
									basicValueHTtemp.put(5, String.valueOf(basicValueHT.get(5)));
									basicValueHTtemp.put(6, sortorder+1);

									basicValueHTtemp.put(8, hierID);
									basicValueHTtemp.put(9, addedName);
									sortorder=sortorder+1;


									hierarchyRowCount++;
								}
							}
							System.out.println("End normal node update hierarchy===>"+sdf.format(new Date()));
						}else{	//if createcodecombination is not checked
							t=0;

							//concating basic information of a node(hierarcyName) and values of the node(hier1 to hier20)
							for(int i=13;i<columnNameHT.size();i++){

								if(l<columnNameHT.size()){	
									finalValueHT.put(l, rs.getString(String.valueOf(columnNameHT.get(i))));
									l++;
								}
							}
							//getting the parent row_wid of the current node
							if(rs.getString("PARENT_ROW_WID") != null && !rs.getString("PARENT_ROW_WID").equalsIgnoreCase("null")){
								if(parentRowWidHT.size()<=0){	//code change Vishnu 18Feb2014
									basicValueHTtemp.put(7, rowID);
								}else{
									basicValueHTtemp.put(7, parentRowWidHT.get(rs.getInt("PARENT_ROW_WID")));
								}
							}else{
								basicValueHTtemp.put(7, rowID);
							}
							basicValueHTtemp.put(11, "null");

							//updating sort order in DB when sortOrder is greater than 0(sortorder will be 0 if current node is first node)
							if(sortorder==0){
								//					sortOrder = sortOrder+1;
								basicValueHTtemp.put(6, Globals.getMaxID(HIERARCHY_XML_DIR, "Hierarchy_Config.xml", "Sort_Order4Oracle", "ID"));
							}else{
								basicValueHTtemp.put(6, sortorder+1);
								String sqlUpdate="UPDATE WC_FLEX_HIERARCHY_D SET SORT_ORDER=SORT_ORDER+1 WHERE SORT_ORDER >= "+(sortorder+1);
								PreparedStatement update4RowCount=conn2.prepareStatement(sqlUpdate);
								update4RowCount.execute();
								update4RowCount.close();
							}

							int h=0;
							basicValueHTtemp.put(9, addedName);
							basicValueHTtemp.put(10, "null");
							basicValueHTtemp.put(12, -1);//code change Vishnu 17Feb2014
							for(int a=basicValueHTtemp.size();a<columnNameHT.size();a++){
								basicValueHTtemp.put(a, String.valueOf(finalValueHT.get(h)));
								h++;
							}
							l=count;
							Globals.insertvalue(columnNameHT,basicValueHTtemp,"HIER20_NUM",pre1,"settingValues",sql1,conn2,coldetHT,pdatatype);
							dimCount = dimCount+1;
							pre1.addBatch();
							Element addedele=(Element)addedNode;
							addedele.setAttribute("Last_Node_ID", String.valueOf(basicValueHTtemp.get(8)));

							parentRowWidHT.put(rs.getInt("ROW_WID"), String.valueOf(basicValueHTtemp.get(0)));

							//				nodeId = nodeId+1;
							//				rowWid = rowWid+1;
							String hierID=String.valueOf(Globals.getMaxID(HIERARCHY_XML_DIR, "Hierarchy_Config.xml", "Node_Level_ID", "ID"));
							basicValueHTtemp=new Hashtable<>();
							basicValueHTtemp.put(0, Globals.getMaxID(HIERARCHY_XML_DIR, "Hierarchy_Config.xml", "Row_ID4Oracle", "ID"));
							basicValueHTtemp.put(1, "GL");
							basicValueHTtemp.put(2, String.valueOf(basicValueHT.get(2)));
							basicValueHTtemp.put(3, String.valueOf(basicValueHT.get(4)));
							basicValueHTtemp.put(4, String.valueOf(basicValueHT.get(4)));
							basicValueHTtemp.put(5, String.valueOf(basicValueHT.get(5)));
							basicValueHTtemp.put(6, sortorder+1);

							basicValueHTtemp.put(8, hierID);
							basicValueHTtemp.put(9, addedName);
							sortorder=sortorder+1;

							hierarchyRowCount++;

						}
						//executing batch if it reaches 1000
						if((++batchCount%batchSize)==0){
							pre1.executeBatch();
							pre2.executeBatch();
							batchCount=0;
							System.out.println("------------------INSERT BATCH EXECUTED----------------------");

						}

					}
					System.out.println("End ROW insert Hierarchy===>"+sdf.format(new Date()));
					//		Globals.updateMaxID(HIERARCHY_XML_DIR, hierarchyConfigFile, "Sort_Order4Oracle", "ID", String.valueOf(sortOrder+2));
					//		Globals.updateMaxID(HIERARCHY_XML_DIR, hierarchyConfigFile, "Row_ID4Oracle", "ID", String.valueOf(rowWid+2));
					//		Globals.updateMaxID(HIERARCHY_XML_DIR, hierarchyConfigFile, "Node_Level_ID", "ID", String.valueOf(nodeId+2));
					pre1.executeBatch();
					pre2.executeBatch();
					pre1.close();
					pre2.close();
					System.out.println("------------------INSERT BATCH EXECUTED - REMAINDER ----------------------");



					Element addedele=(Element)addedNode;	//code change Vishnu 18Feb2014
					addedele.setAttribute("Hierarchy_Type", "NormalHierarchy");

				}
				//updating largest sortorder in configXML
				String updatesortorder="SELECT MAX(SORT_ORDER) FROM WC_FLEX_HIERARCHY_D";
				PreparedStatement updatesortorderpre=conn2.prepareStatement(updatesortorder);
				ResultSet re=updatesortorderpre.executeQuery();
				while(re.next()){

					Globals.updateMaxID(HIERARCHY_XML_DIR, hierarchyConfigFile, "Sort_Order4Oracle", "ID", String.valueOf(re.getInt(1)));
				}

				updatesortorderpre.close();
				re.close();

				pre.close();
				rs.close();
			}

			// code change Menaka 20FEB2014

		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();

			String dimStatus = Globals.getErrorString(e);
			dimStatusHT.put("dimResult","Error"); 
			dimStatusHT.put("errorMessage",dimStatus);
		}
		System.out.println("Exiting: "
				+ new Exception().getStackTrace()[0].getClassName() + "."
				+ new Exception().getStackTrace()[0].getMethodName());
		return dimCount;

	}

	public void rollupHierarchy(Hashtable rollupHT,String rollupID,String rollupName,Document doc,ResultSet rs,Hashtable nodeHT
			,Hashtable valueMissedsegmentHT,Hashtable segmentHT,Hashtable basicValueHT,String addedName,String hierarchyID,
			String hierarchyName,Node addedNode,Connection conn2,int segmentCount,Hashtable nodeNameandTypeHT4Segment,String addedNodeID,Hashtable coldetHT,
			String pdatatype, int dimCount) {
		System.out.println("Entering: "
				+ new Exception().getStackTrace()[0].getClassName() + "."
				+ new Exception().getStackTrace()[0].getMethodName());
		Hashtable rollupHTTemp=rollupHT;
		Hashtable rollUpNodeHT=new Hashtable<>();
		Hashtable rollUpNodeandTypeHT=new Hashtable<>();
		Hashtable basicValueHTtemp=new Hashtable<>();
		Hashtable segmentHTTemp=new Hashtable<>();
		try{


			segmentHTTemp=segmentHT;
			String segValue="";
			String segValuetemp="";

			Node rollupNd=Globals.getNodeByAttrVal(doc, rollupName, "ID", rollupID);
			Globals.processUpDowninDB(rollupNd, rollUpNodeHT, 1, rollUpNodeandTypeHT);

			Hashtable parentRowWidHT=new Hashtable<>();
			Hashtable parentRowWidHTTemp=new Hashtable<>();
			// Loop get the segment values for the rollup hiearchy from child nodes of rollup node
			for(int i=0;i<rollUpNodeHT.size();i++){
				Hashtable tempHT=new Hashtable<>();
				tempHT=(Hashtable) rollUpNodeHT.get(i);
				if(String.valueOf(tempHT.get("Segment")).contains("$~$")){
					if(addedNodeID.equals(String.valueOf(tempHT.get("ID")))){
						continue;
					}

					for(int j=1;j<=segmentCount;j++){

						String segmentype[]=String.valueOf(tempHT.get("Segment")).split("\\$~\\$");
						for(int k=0;k<segmentype.length;k++){

							if(segmentype[k].equals("Segment_"+j)){
								String segmentNumber="";
								segmentNumber=segmentype[k].substring(segmentype[k].lastIndexOf("_")+1);

								segValue=String.valueOf(tempHT.get("seg"+segmentNumber+"_Code"));    // code 64130

								String temp=String.valueOf(segmentHTTemp.get("Segment_"+j));
								String segmentValue="";
								if(temp.equals("")){
									segmentValue=String.valueOf(tempHT.get("seg"+segmentNumber+"_Code")).replace(";", "~");
									segmentHTTemp.put("Segment_"+j, segmentValue);
								}else if(temp.endsWith("~")){
									segmentValue=temp+String.valueOf(tempHT.get("seg"+segmentNumber+"_Code")).replace(";", "~");
									segmentHTTemp.put("Segment_"+j, segmentValue);
								}else{
									segmentValue=temp+"~"+String.valueOf(tempHT.get("seg"+segmentNumber+"_Code")).replace(";", "~");
									segmentHTTemp.put("Segment_"+j, segmentValue);
								}
							}
						}
					}
				}else{


					String segmentype[]=String.valueOf(tempHT.get("Segment")).split("~");
					for(int k=0;k<segmentype.length;k++){
						for(int j=1;j<=segmentCount;j++){
							if(segmentype[k].equals("Segment_"+j)){
								//				segValue=String.valueOf(rollUpNodeandTypeHT.get(i)).split("#~#")[0];
								segValue=String.valueOf(tempHT.get("Code"));    // code 64130
								//				segValue=segValue.substring(segValue.indexOf("(")+1,segValue.lastIndexOf(")"));
								//				segValue=segValue.substring(0, segValue.length()-1);
								segValuetemp=segValuetemp+segValue;
								segmentHTTemp.put("Segment_"+j, segValuetemp);
								segValuetemp=segValuetemp+"~";
							}
						}
					}
				}
			}

			int l=rollupHTTemp.size();
			int count=rollupHT.size();
			//	Connection conn2=Globals.getDBConnection("DW_Connection");
			String nodelevelno="";
			String previousNodelevelno="";
			if(rollupName.equalsIgnoreCase("RootLevel")){
				nodelevelno="2";
				previousNodelevelno="1";
			}else{
				int temp=Integer.parseInt(rollupName.substring(6))+1;
				nodelevelno=String.valueOf(temp);
				previousNodelevelno=rollupName.substring(6);
			}

			String sql4RowCount="SELECT ROW_WID,SORT_ORDER FROM WC_FLEX_HIERARCHY_D WHERE XML_NODE_ID = '"+rollupID+"'";
			PreparedStatement pre4RowCount=conn2.prepareStatement(sql4RowCount);
			ResultSet rs2=pre4RowCount.executeQuery();


			System.out.println("Sort Order sql4RowCount===>"+sql4RowCount);
			int rowID=0;
			int sortorder=0;
			while(rs2.next()){
				rowID=rs2.getInt("ROW_WID");
				sortorder=rs2.getInt("SORT_ORDER");
			}
			pre4RowCount.close();
			rs2.close();
			System.out.println("Sort Order sortorder===>"+sortorder);

			String newNodeID=String.valueOf(String.valueOf(Globals.getMaxID(HIERARCHY_XML_DIR, hierarchyConfigFile,"Node_Level_ID", "ID")));
			basicValueHTtemp.put(0, Globals.getMaxID(HIERARCHY_XML_DIR, "Hierarchy_Config.xml", "Row_ID4Oracle", "ID"));
			basicValueHTtemp.put(1, "GL");
			basicValueHTtemp.put(2, String.valueOf(basicValueHT.get(2)));
			basicValueHTtemp.put(3, String.valueOf(basicValueHT.get(4)));
			basicValueHTtemp.put(4, hierarchyID);
			basicValueHTtemp.put(5, hierarchyName);
			basicValueHTtemp.put(6, sortorder);

			basicValueHTtemp.put(8, newNodeID);
			basicValueHTtemp.put(9, addedName);

			if(SOP_ENABLED)
				System.out.println("Segment HT for rollup hierarchy"+segmentHTTemp);



			int parentcode=0;
			int oldparent=0;
			String codeValue="";
			Hashtable columnNameHT=Globals.getcolumnName();
			PreparedStatement pre1=null;
			PreparedStatement pre2=null;
			String sql1=Globals.insertSQL(columnNameHT,"HIER20_NUM",pre1,"gettingSQL",""); //code chane Vishnu 17Fev2014
			String sqlHIER20_NUM=Globals.insertSQL(columnNameHT,"",pre2,"gettingSQL",""); //code chane Vishnu 17Fev2014
			pre1=conn2.prepareStatement(sql1);
			pre2=conn2.prepareStatement(sqlHIER20_NUM);
			int batchCount=0;
			int batchSize=1000;

			Hashtable segtempHT=new Hashtable<>();
			boolean segFlag=true;

			//	int rowWid = Globals.getMaxID(HIERARCHY_XML_DIR, "Hierarchy_Config.xml", "Row_ID4Oracle", "ID");
			//	int sortOrder = Globals.getMaxID(HIERARCHY_XML_DIR, "Hierarchy_Config.xml", "Sort_Order4Oracle", "ID");
			//	int nodeId = Globals.getMaxID(HIERARCHY_XML_DIR, "Hierarchy_Config.xml", "Node_Level_ID", "ID");
			// resultset which loops for rollup hierarchy
			while(rs.next()){
				boolean isParent4CodeCombination=false;
				Hashtable codeCombinationHT=new Hashtable<>();
				boolean oldCodeChange=false;
				Hashtable tempHT=new Hashtable<>();
				String typeOfColumn = "";
				//getting currnt node information in tempHt
				for(int key1=0;key1<nodeHT.size();key1++){

					tempHT=(Hashtable) nodeHT.get(key1);
					if(String.valueOf(tempHT.get("ID")).equals(rs.getString("XML_NODE_ID"))){
						break;
					}
				}
				if(String.valueOf(tempHT.get("NodeName")).equals("RootLevel") || String.valueOf(tempHT.get("NodeName")).startsWith("Level_")){
					// getting leaf node ID of the current node 	
					String lastNodeID=gettingLastchildofNode(String.valueOf(tempHT.get("ID")), String.valueOf(tempHT.get("NodeName")), String.valueOf(tempHT.get("Type")));

					if(lastNodeID.equals(String.valueOf(tempHT.get("ID"))) || lastNodeID.equals(String.valueOf(tempHT.get("Last_Node_ID")))){
						if(!String.valueOf(tempHT.get("Segment")).contains("$~$")){
							codeCombinationHT=new Hashtable<>();
							//					for(int key=0;key<nodeNameandTypeHT4Segment.size();key++){

							String dataSegment=String.valueOf(tempHT.get("Code"));

							if(dataSegment.equals(rs.getString("HIER20_CODE"))
									&& String.valueOf(tempHT.get("Type")).equalsIgnoreCase("Data")){
								for(int i=0;i<valueMissedsegmentHT.size();i++){
									if(String.valueOf(tempHT.get("Segment")).equalsIgnoreCase(String.valueOf(valueMissedsegmentHT.get(i)))){
										segmentHTTemp.put(String.valueOf(valueMissedsegmentHT.get(i)), rs.getString("HIER20_CODE"));
										codeValue=rs.getString("HIER20_CODE");

									}
								}
								Hashtable codeCombinationvalueHT=Globals.Segment(segmentHT, conn2, hierarchyID,false);
								codeCombinationHT = (Hashtable)codeCombinationvalueHT.get(0);

								typeOfColumn = String.valueOf(codeCombinationvalueHT.get(1));
								isParent4CodeCombination=true;
								if(codeCombinationHT.size()<=0){
									codeCombinationHT.put(0, "null");
									typeOfColumn = "Number";
								}else{

								}
							}

						}else{
							if(String.valueOf(tempHT.get("ID")).equals(String.valueOf(rs.getString("XML_NODE_ID")))){
								//				String dataSegment=String.valueOf(nodeNameandTypeHT4Segment.get(key)).split("#~#")[0];	//value#~#type#~#Segment#~#Code
								String dataSegment="";
								oldCodeChange=false;

								String segments[]=String.valueOf(tempHT.get("Segment")).split("\\$~\\$");

								for(int val=0;val<segments.length;val++){
									if(segFlag){

										segtempHT.put(segments[val], String.valueOf(segmentHTTemp.get(segments[val])));
									}
									String segmentNumber="";
									segmentNumber=segments[val].substring(segments[val].lastIndexOf("_")+1);
									dataSegment=String.valueOf(tempHT.get("seg"+segmentNumber+"_Code"));

									dataSegment=dataSegment.replace(";", "");
									if(String.valueOf(tempHT.get("Type")).equalsIgnoreCase("Data")){
										if(String.valueOf(tempHT.get("Data_SubType")).equalsIgnoreCase("Segment") || String.valueOf(tempHT.get("Data_SubType")).equalsIgnoreCase("")
												|| String.valueOf(tempHT.get("Data_SubType")).equalsIgnoreCase("fromData4DirectSQL")){
											for(int j=1;j<=segmentCount;j++){

												if(segments[val].equalsIgnoreCase("Segment_"+j)){

													//								segmentHTTemp.put("Segment_"+j,dataSegment);
													String temp=String.valueOf(segtempHT.get(segments[val]));
													String segmentValue="";
													if(temp.equals("")){
														segmentValue=dataSegment.replace(";", "~");
														segmentHTTemp.put("Segment_"+j, segmentValue);
													}else if(temp.endsWith("~")){
														segmentValue=temp+dataSegment.replace(";", "~");
														segmentHTTemp.put("Segment_"+j, segmentValue);
													}else{
														segmentValue=temp+"~"+dataSegment.replace(";", "~");
														segmentHTTemp.put("Segment_"+j, segmentValue);
													}
													codeValue=rs.getString("HIER20_CODE");

													//								
												}

											}
										}else if(String.valueOf(tempHT.get("Data_SubType")).equalsIgnoreCase("DirectSQL")){
											String segSQL = String.valueOf(tempHT.get("Custom_SQL"));
											System.out.println("segSQL===>"+segSQL);
											PreparedStatement ps1 = conn2.prepareStatement(segSQL);
											ResultSet rs1 = ps1.executeQuery();
											String segValue1 = "";
											while (rs1.next()) {
												segValue1 = segValue1 + rs1.getString(1)+"~";
												segmentHT.put(String.valueOf(tempHT.get("Primary_Segment")), segValue1);


											}
											codeValue=rs.getString("HIER20_CODE");
										}
									}
								}
								//getting code combination for cost center
								//				sdf
								Hashtable codeCombinationvalueHT=Globals.Segment(segmentHT, conn2, hierarchyID,false);
								codeCombinationHT = (Hashtable)codeCombinationvalueHT.get(0);

								typeOfColumn = String.valueOf(codeCombinationvalueHT.get(1));
								System.out.println("codeCombinationHT.size()===>"+codeCombinationHT.size());
								isParent4CodeCombination=true;
								if(codeCombinationHT.size()<=0){
									codeCombinationHT.put(0, "null");
								}else{

								}
								segFlag=false;
								break;
							}

						}
						//			}
					}

				}
				//		}

				//loop for inserting code combination in db
				boolean isCodeCombination=false;
				for(int key=0;key<codeCombinationHT.size();key++){
					boolean isCodeCombinationtemp=false;

					String columnName2Exclude="";
					if(isCodeCombination){//code change Vishnu 17Feb2014
						if(String.valueOf(codeCombinationHT.get(key)).equalsIgnoreCase("null")){
							String dummyCodeCombination="";
							dummyCodeCombination=String.valueOf(Globals.getMaxID(HIERARCHY_XML_DIR, hierarchyConfigFile, "Dummy_CodeCombination_ID", "ID"));
							for(int i=13;i<columnNameHT.size();i++){

								if(l<columnNameHT.size()){	
									if(rs.getString(String.valueOf(columnNameHT.get(i))).equals(rs.getString("HIER20_CODE"))){
										if(isCodeCombinationtemp){

											rollupHTTemp.put(l, dummyCodeCombination);
											l++;
										}else{
											rollupHTTemp.put(l, rs.getString(String.valueOf(columnNameHT.get(i))));
											l++;
											isCodeCombinationtemp=true;
										}

									}else{
										rollupHTTemp.put(l, rs.getString(String.valueOf(columnNameHT.get(i))));
										l++;
									}

								}
							}

							basicValueHTtemp.put(11, codeValue);
							basicValueHTtemp.put(10, "Code");
							basicValueHTtemp.put(12, dummyCodeCombination);
							//					System.out.println("String.valueOf(finalValueHT.get(11))====>"+String.valueOf(basicValueHTtemp.get(11)));
							basicValueHTtemp.put(7, parentRowWidHTTemp.get(String.valueOf(basicValueHTtemp.get(11))));
							columnName2Exclude="";
						}else{
							for(int i=13;i<columnNameHT.size();i++){

								if(l<columnNameHT.size()){	
									if(rs.getString(String.valueOf(columnNameHT.get(i))).equals(rs.getString("HIER20_CODE"))){
										if(isCodeCombinationtemp){
											rollupHTTemp.put(l, String.valueOf(codeCombinationHT.get(key)));
											l++;
										}else{
											rollupHTTemp.put(l, rs.getString(String.valueOf(columnNameHT.get(i))));
											l++;
											isCodeCombinationtemp=true;
										}
									}else{
										rollupHTTemp.put(l, rs.getString(String.valueOf(columnNameHT.get(i))));
										l++;
									}

								}
							}

							//start code change Vishnu 25Feb2014 /to eliminate duplication in data table when we have rollup hierarchy and last level hierarchy
							boolean iscodeCombinationinserted=false;
							String codecombinationSQL="Select * from WC_FLEX_HIERARCHY_D where hier20_code='"+String.valueOf(codeCombinationHT.get(key))+"' AND HIERARCHY_ID='"+hierarchyID+"'";

							PreparedStatement codecombinationpre=conn2.prepareStatement(codecombinationSQL);
							ResultSet codecombinationrs=codecombinationpre.executeQuery();
							while(codecombinationrs.next()){

								iscodeCombinationinserted=true;

							}
							if(iscodeCombinationinserted){
								basicValueHTtemp.put(12, -1);
								basicValueHTtemp.put(11, "null");
								basicValueHTtemp.put(10, "null");
								columnName2Exclude="HIER20_NUM";
							}else{
								basicValueHTtemp.put(12, codeCombinationHT.get(key));
								basicValueHTtemp.put(11, codeValue);
								basicValueHTtemp.put(10, "Code");
								columnName2Exclude="";
							}
							codecombinationpre.close();
							codecombinationrs.close();
							//end code change Vishnu 25Feb2014

							basicValueHTtemp.put(7, parentRowWidHTTemp.get(rs.getString("HIER20_CODE")));

						}
					}else{
						for(int i=13;i<columnNameHT.size();i++){

							if(l<columnNameHT.size()){	

								rollupHTTemp.put(l, rs.getString(String.valueOf(columnNameHT.get(i))));
								l++;


							}
						}
						basicValueHTtemp.put(12, -1);
						columnName2Exclude="HIER20_NUM";
						basicValueHTtemp.put(11, "null");
						basicValueHTtemp.put(10, "Parent_Code");
						basicValueHTtemp.put(7, parentRowWidHT.get(rs.getInt("PARENT_ROW_WID")));
						parentRowWidHTTemp.put(rs.getString("HIER20_CODE"), String.valueOf(basicValueHTtemp.get(0)));
						key--;
						isCodeCombination=true;
					}
					//end code change Vishnu 17Feb2014






					if(sortorder==0){
						//			sortOrder = sortOrder+1;
						basicValueHTtemp.put(6, Globals.getMaxID(HIERARCHY_XML_DIR, "Hierarchy_Config.xml", "Sort_Order4Oracle", "ID"));
					}else{
						basicValueHTtemp.put(6, sortorder+1);
						String sqlUpdate="UPDATE WC_FLEX_HIERARCHY_D SET SORT_ORDER=SORT_ORDER+1 WHERE SORT_ORDER >= "+(sortorder+1);
						PreparedStatement update4RowCount=conn2.prepareStatement(sqlUpdate);
						update4RowCount.execute();
						update4RowCount.close();
					}

					int h=0;

					for(int a=basicValueHTtemp.size();a<columnNameHT.size();a++){
						basicValueHTtemp.put(a, String.valueOf(rollupHTTemp.get(h)));
						h++;
					}

					l=count;
					//		if(typeOfColumn.equalsIgnoreCase("Number")){
					//			Globals.insertvalue(columnNameHT,basicValueHTtemp,"",pre1,"settingValues4HIERNUM",sql1,conn2,coldetHT,pdatatype);	//insering sql for inserting sql in db
					//			pre1.addBatch();
					//		}else{
					//			Globals.insertvalue(columnNameHT,basicValueHTtemp,"HIER20_NUM",pre,"settingValues",sql,conn2,coldetHT,pdatatype);
					//			pre.addBatch();
					//		}

					if(columnName2Exclude.equalsIgnoreCase("HIER20_NUM")){
						Globals.insertvalue(columnNameHT,basicValueHTtemp,"HIER20_NUM",pre1,"settingValues",sql1,conn2,coldetHT,pdatatype);
						pre1.addBatch();
						dimCount = dimCount+1;
					}else{
						//			Globals.insertvalue(columnNameHT,basicValueHTtemp,"",pre2,"settingValues4HIERNUM",sqlHIER20_NUM,conn2,coldetHT,pdatatype);
						//			pre2.addBatch();
						if(typeOfColumn.equalsIgnoreCase("Number")){
							Globals.insertvalue(columnNameHT,basicValueHTtemp,"",pre2,"settingValues4HIERNUM",sqlHIER20_NUM,conn2,coldetHT,pdatatype);	//insering sql for inserting sql in db
							pre2.addBatch();
						}else{
							Globals.insertvalue(columnNameHT,basicValueHTtemp,"HIER20_NUM",pre1,"settingValues",sql1,conn2,coldetHT,pdatatype);
							pre1.addBatch();
						}
						dimCount = dimCount+1;
					}
					Element addedele=(Element)addedNode;
					addedele.setAttribute("Last_Node_ID", String.valueOf(basicValueHTtemp.get(8)));

					parentRowWidHT.put(rs.getInt("ROW_WID"), String.valueOf(basicValueHTtemp.get(0)));

					//		nodeId = nodeId+1;
					//		rowWid = rowWid+1;

					String hierID=String.valueOf(Globals.getMaxID(HIERARCHY_XML_DIR, "Hierarchy_Config.xml", "Node_Level_ID", "ID"));
					basicValueHTtemp=new Hashtable<>();
					basicValueHTtemp.put(0, Globals.getMaxID(HIERARCHY_XML_DIR, "Hierarchy_Config.xml", "Row_ID4Oracle", "ID"));
					basicValueHTtemp.put(1, "GL");
					basicValueHTtemp.put(2, String.valueOf(basicValueHT.get(2)));
					basicValueHTtemp.put(3, String.valueOf(basicValueHT.get(3)));
					basicValueHTtemp.put(4, String.valueOf(basicValueHT.get(4)));
					basicValueHTtemp.put(5, String.valueOf(basicValueHT.get(5)));

					basicValueHTtemp.put(6, sortorder+1);

					basicValueHTtemp.put(8, hierID);
					basicValueHTtemp.put(9, addedName);
					sortorder=sortorder+1;


				}
				if(codeCombinationHT.size()<=0){	//inserting non leaf node values in db

					for(int i=13;i<columnNameHT.size();i++){

						if(l<columnNameHT.size()){	
							rollupHTTemp.put(l, rs.getString(String.valueOf(columnNameHT.get(i))));
							l++;
						}
					}

					if(rs.getString("PARENT_ROW_WID") != null && !rs.getString("PARENT_ROW_WID").equalsIgnoreCase("null") && !rs.getString("PARENT_ROW_WID").equalsIgnoreCase("0")){ // 15 Feb 14

						if(parentRowWidHT.size()<=0){	//code change Vishnu 18Feb2014
							basicValueHTtemp.put(7, rowID);
						}else{
							basicValueHTtemp.put(7, parentRowWidHT.get(rs.getInt("PARENT_ROW_WID")));
						}
					}else{
						basicValueHTtemp.put(7, rowID);
					}
					basicValueHTtemp.put(11, "null");

					if(sortorder==0){
						//				sortOrder = sortOrder+1;
						basicValueHTtemp.put(6, Globals.getMaxID(HIERARCHY_XML_DIR, "Hierarchy_Config.xml", "Sort_Order4Oracle", "ID"));
					}else{
						basicValueHTtemp.put(6, sortorder+1);
						String sqlUpdate="UPDATE WC_FLEX_HIERARCHY_D SET SORT_ORDER=SORT_ORDER+1 WHERE SORT_ORDER >= "+(sortorder+1);
						PreparedStatement update4RowCount=conn2.prepareStatement(sqlUpdate);
						update4RowCount.execute();
						update4RowCount.close();
					}

					int h=0;
					if(isParent4CodeCombination){
						basicValueHTtemp.put(10, "Parent_Code");
						parentRowWidHTTemp.put(rs.getString("HIER20_CODE"), String.valueOf(basicValueHTtemp.get(0)));
					}else{
						basicValueHTtemp.put(10, "null");
					}

					basicValueHTtemp.put(12, -1);//code change Vishnu 17Feb2014
					if (SOP_ENABLED)
						System.out.println("rollupHTTemp===>"+rollupHTTemp);
					for(int a=basicValueHTtemp.size();a<columnNameHT.size();a++){
						basicValueHTtemp.put(a, String.valueOf(rollupHTTemp.get(h)));
						h++;
					}
					l=count;
					Globals.insertvalue(columnNameHT,basicValueHTtemp,"HIER20_NUM",pre1,"settingValues",sql1,conn2,coldetHT,pdatatype);
					pre1.addBatch();
					dimCount = dimCount+1;
					parentRowWidHT.put(rs.getInt("ROW_WID"), String.valueOf(basicValueHTtemp.get(0)));

					Element addedele=(Element)addedNode;
					addedele.setAttribute("Last_Node_ID", String.valueOf(basicValueHTtemp.get(8)));

					//			nodeId = nodeId+1;
					//			rowWid = rowWid+1;

					String hierID=String.valueOf(Globals.getMaxID(HIERARCHY_XML_DIR, "Hierarchy_Config.xml", "Node_Level_ID", "ID"));
					basicValueHTtemp=new Hashtable<>();
					basicValueHTtemp.put(0, Globals.getMaxID(HIERARCHY_XML_DIR, "Hierarchy_Config.xml", "Row_ID4Oracle", "ID"));
					basicValueHTtemp.put(1, "GL");
					basicValueHTtemp.put(2, String.valueOf(basicValueHT.get(2)));
					basicValueHTtemp.put(3, String.valueOf(basicValueHT.get(4)));
					basicValueHTtemp.put(4, String.valueOf(basicValueHT.get(4)));
					basicValueHTtemp.put(5, String.valueOf(basicValueHT.get(5)));
					basicValueHTtemp.put(6, sortorder+1);

					basicValueHTtemp.put(8, hierID);
					basicValueHTtemp.put(9, addedName);
					sortorder=sortorder+1;


				}
				if((++batchCount%batchSize)==0){
					pre1.executeBatch();
					pre2.executeBatch();
					batchCount=0;
					System.out.println("------------------INSERT BATCH EXECUTED----------------------");
				}
			}
			//	Globals.updateMaxID(HIERARCHY_XML_DIR, hierarchyConfigFile, "Sort_Order4Oracle", "ID", String.valueOf(sortOrder+2));
			//	Globals.updateMaxID(HIERARCHY_XML_DIR, hierarchyConfigFile, "Row_ID4Oracle", "ID", String.valueOf(rowWid+2));
			//	Globals.updateMaxID(HIERARCHY_XML_DIR, hierarchyConfigFile, "Node_Level_ID", "ID", String.valueOf(nodeId+2));

			pre1.executeBatch();
			pre2.executeBatch();
			pre1.close();
			pre2.close();
			System.out.println("------------------INSERT BATCH EXECUTED - REMAINDER ----------------------");

			String sortingSQL="select * from WC_FLEX_HIERARCHY_D order by SORT_ORDER ASC"; //code change Vishnu 18Fev2014
			PreparedStatement sortpre=conn2.prepareStatement(sortingSQL);
			sortpre.execute();
			sortpre.close();
			segmentHTTemp=new Hashtable<>();




		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	public String regeneratedHierarchyID="";
	public String getRegeneratedHierarchyID() {
		return regeneratedHierarchyID;
	}
	public void setRegeneratedHierarchyID(String regeneratedHierarchyID) {
		this.regeneratedHierarchyID = regeneratedHierarchyID;
	}
	boolean flag4reGeneratingFact;
	public boolean isFlag4reGeneratingFact() {
		return flag4reGeneratingFact;
	}
	public void setFlag4reGeneratingFact(boolean flag4reGeneratingFact) {
		this.flag4reGeneratingFact = flag4reGeneratingFact;
	}
	String userName = "";
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	String factGenType=  "";

	public String getFactGenType() {
		return factGenType;
	}
	public void setFactGenType(String factGenType) {
		this.factGenType = factGenType;
	}
	public void reGenerateHierarchy(String hierarchy_ID,String codecombinationFlag, boolean flag4GeneratingFact,String factGenType){
		try{
			FacesContext ctx = FacesContext.getCurrentInstance();
			ExternalContext extContext = ctx.getExternalContext();
			Map sessionMap = extContext.getSessionMap();
			LoginBean lb = (LoginBean) sessionMap.get("loginBean");
			String userName = "";
			userName = lb.username;
			this.msg1 =  "The Hierarchy re-generation is in progress. You may track the progress from the Hierarchy List screen.";
			color4HierTreeMsg = "blue";
			Document doc=Globals.openXMLFile(HIERARCHY_XML_DIR, hierarchyXmlFileName);

			Node nd=Globals.getNodeByAttrVal(doc, "Hierarchy_Level", "Hierarchy_ID", hierarchy_ID);
			Element hierElement=(Element)nd;
			System.out.println("codecombinationFlag==>"+codecombinationFlag);
			hierElement.setAttribute("Code_Combination", String.valueOf(codecombinationFlag));
			hierElement.setAttribute("Dim_Status", "In Progress");
			hierElement.setAttribute("Dim_Status_Details", "In Progress");
			System.out.println("flag4reGeneratingFact--->"+flag4GeneratingFact);

			Globals.writeXMLFile(doc, HIERARCHY_XML_DIR, hierarchyXmlFileName);
			regeneratedHierarchyID=hierarchy_ID;
			Thread t=new Thread(new HierarchyBean(hierarchy_ID,flag4GeneratingFact,userName,factGenType));
			isReGenerated=true;
			t.start();

			fullMsg1=msg1;
			if(msg1.length()>80){
				msg1=msg1.substring(0, 78)+"...";
			}

			//		}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	//	@Override
	public void run(){

		Hashtable dimStatusHT = new Hashtable<>();
		System.out.println("this.flag4reGeneratingFact--->"+flag4reGeneratingFact);
		reGenerate(regeneratedHierarchyID,dimStatusHT,flag4reGeneratingFact,factGenType);
	}
	//	public void reGenerate(){
	//		
	//	}
	public void reGenerate(String hierID,Hashtable dimStatusHT, boolean flag4reGeneratingFact,String factGenType) {

		System.out.println("Entering: " + new Exception().getStackTrace()[0].getClassName() + "."
				+ new Exception().getStackTrace()[0].getMethodName());


		Document doc=  null;
		Node nd = null;
		int dimCount = 0;
		try{


			String dateFormat = "";

			PropUtil prop = new PropUtil();
			HIERARCHY_XML_DIR = prop.getProperty("HIERARCHY_XML_DIR");
			hierarchyXmlFileName = prop.getProperty("HIERARCHY_XML_FILE");
			dateFormat = prop.getProperty("DATE_FORMAT");
			Date startDate = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
			dimStatusHT.put("dimResult","Generated"); 
			dimStatusHT.put("errorMessage","Dim Generated : "+" Started at "+startDate);
			doc=Globals.openXMLFile(HIERARCHY_XML_DIR, hierarchyXmlFileName);

			nd=Globals.getNodeByAttrVal(doc, "Hierarchy_Level", "Hierarchy_ID", hierID);

			System.out.println("hierID===>"+hierID);
			Connection conn=Globals.getDBConnection("DW_Connection");
			Hashtable nodeHT=new Hashtable<>();
			Hashtable nodevalueandtypeHT=new Hashtable<>();
			Globals.processUpDowninDB(nd, nodeHT, 1, nodevalueandtypeHT);
			String addedName="";
			int levelNo=0;
			String typeName="";
			Hashtable valueHT=new Hashtable<>();
			Hashtable basicValueHT=new Hashtable<>();
			String selectedNdID="";
			String selectednodeName="";
			String selectednodeType="";
			String insertType="";
			Node addedNode;
			String hierarchyID="";
			String hierarchyName="";
			String hierarchyCat="";
			String previoustype4ReGenerate="";
			Hashtable coldetHT=new Hashtable<>();
			String pdatatype="";
			Hashtable columnNameHT=Globals.getcolumnName();
			genarateFactsBean gfb=new genarateFactsBean("","","");
			Hashtable coldetHTtemp=new Hashtable<>();
			for(int i=0;i<columnNameHT.size();i++){
				coldetHTtemp=gfb.getDatatypefromDBtable("WC_FLEX_HIERARCHY_D."+String.valueOf(columnNameHT.get(i)),conn,"","","");
				coldetHT.put(i, coldetHTtemp);
			}
			//		System.out.println("nodeHT===>"+nodeHT);
			for(int j=0;j<nodeHT.size();j++){
				Hashtable ht=new Hashtable<>();
				ht=(Hashtable)nodeHT.get(j);
				if(String.valueOf(ht.get("NodeName")).equals("Hierarchy_Level")){
					hierarchyID=String.valueOf(ht.get("Hierarchy_ID"));
					hierarchyName=String.valueOf(ht.get("Hierarchy_Name"));
					hierarchyCat=String.valueOf(ht.get("Hierarchy_Category"));
					String deleteSql="Delete from WC_FLEX_HIERARCHY_D where HIERARCHY_ID='"+hierarchyID+"'";
					System.out.println("deleteSql===>"+deleteSql);
					//				Connection conn=Globals.getDBConnection("DW_Connection");
					PreparedStatement delePre=conn.prepareStatement(deleteSql);
					delePre.execute();
					delePre.close();
				}

				if(String.valueOf(ht.get("NodeName")).equals("RootLevel") || String.valueOf(ht.get("NodeName")).startsWith("Level_")){
					if(String.valueOf(ht.get("NodeName")).equals("RootLevel")){

						addedNode=Globals.getNodeByAttrVal(doc, "RootLevel", "ID", hierarchyID);
						HierarchyBean he=new HierarchyBean();
						addedName=String.valueOf(ht.get("value"));
						levelNo=0;
						typeName=String.valueOf(ht.get("Type"));
						basicValueHT.put(0, String.valueOf(Globals.getMaxID(HIERARCHY_XML_DIR, hierarchyConfigFile,"Row_ID4Oracle", "ID")));
						basicValueHT.put(1, "GL"); //???????????
						basicValueHT.put(2, hierarchyCat);
						basicValueHT.put(3, hierarchyID);
						basicValueHT.put(4, hierarchyID);
						basicValueHT.put(5, hierarchyName);
						basicValueHT.put(6, String.valueOf(String.valueOf(Globals.getMaxID(HIERARCHY_XML_DIR, hierarchyConfigFile,"Sort_Order4Oracle", "ID"))));
						basicValueHT.put(7, "null"); // NA
						basicValueHT.put(8, hierarchyID);
						basicValueHT.put(9, hierarchyName);
						valueHT.put(0, addedName);

						dimCount = he.insertionofValues2DB(addedName, levelNo, typeName, valueHT, basicValueHT, hierarchyID, "Hierarchy_Level","ReGenerate",hierarchyID,"",conn,String.valueOf(ht.get("NodeName")),doc,dimStatusHT,coldetHT,pdatatype,dimCount);//					Node afterAddNd=Globals.getNodeByAttrVal(doc, addedNodetemp.getNodeName(), "ID", String.valueOf(ht.get("ID")));

						previoustype4ReGenerate=String.valueOf(ht.get("Type"))+"#~#"+Globals.getAttrVal4AttrName(addedNode, "Last_Node_ID");
					}else{
						addedNode=Globals.getNodeByAttrVal(doc, String.valueOf(ht.get("NodeName")), "ID", String.valueOf(ht.get("ID")));
						//					Element em=(Element)addedNode;
						HierarchyBean he=new HierarchyBean();
						addedName=String.valueOf(ht.get("value"));
						levelNo=Integer.parseInt(String.valueOf(ht.get("NodeName")).replace("Level_", ""));
						typeName=String.valueOf(ht.get("Type"));
						Node addedNodeParent=addedNode.getParentNode();
						selectednodeName=addedNodeParent.getNodeName();
						selectedNdID=addedNodeParent.getAttributes().getNamedItem("ID").getNodeValue();
						selectednodeType=addedNodeParent.getAttributes().getNamedItem("Type").getNodeValue();
						String sortorder=String.valueOf(String.valueOf(Globals.getMaxID(HIERARCHY_XML_DIR, hierarchyConfigFile,"Sort_Order4Oracle", "ID")));
						basicValueHT.put(0, String.valueOf(Globals.getMaxID(HIERARCHY_XML_DIR, hierarchyConfigFile,"Row_ID4Oracle", "ID")));
						basicValueHT.put(1, "GL");  // ???????????????????
						basicValueHT.put(2, hierarchyCat);

						Node nd4BasicInf=Globals.getNodeByAttrVal(doc, String.valueOf(ht.get("NodeName")), "ID", String.valueOf(ht.get("ID")));
						for(int i=levelNo;i>0;i--){
							nd4BasicInf=nd4BasicInf.getParentNode();

							if(nd4BasicInf.getNodeName().equalsIgnoreCase("Hierarchy_Level") && nd4BasicInf.getNodeType()==Node.ELEMENT_NODE){
								if(typeName.equalsIgnoreCase("Hierarchy")){
									Node nd1=Globals.getNodeByAttrVal(doc, "Hierarchy_Level", "Hierarchy_Name", addedName);
									basicValueHT.put(3, nd4BasicInf.getAttributes().getNamedItem("Hierarchy_ID").getNodeValue());
									basicValueHT.put(4, nd4BasicInf.getAttributes().getNamedItem("Hierarchy_ID").getNodeValue());
									basicValueHT.put(5, nd4BasicInf.getAttributes().getNamedItem("Hierarchy_Name").getNodeValue());
									basicValueHT.put(6, sortorder);
									basicValueHT.put(7, Globals.gettingParentCodefromDB(selectedNdID,conn));
									basicValueHT.put(8, String.valueOf(ht.get("ID")));
									basicValueHT.put(9, hierarchyName);
									break;
								}else{
									basicValueHT.put(3, nd4BasicInf.getAttributes().getNamedItem("Hierarchy_ID").getNodeValue());
									basicValueHT.put(4, nd4BasicInf.getAttributes().getNamedItem("Hierarchy_ID").getNodeValue());
									basicValueHT.put(5, nd4BasicInf.getAttributes().getNamedItem("Hierarchy_Name").getNodeValue());
									basicValueHT.put(6, sortorder);
									basicValueHT.put(7, Globals.gettingParentCodefromDB(selectedNdID,conn));
									basicValueHT.put(8, String.valueOf(ht.get("ID")));
									basicValueHT.put(9, hierarchyName);
									break;
								}

							}
						}
						if(!typeName.equalsIgnoreCase("Hierarchy")){
							if(typeName.equals("Data")){
								if(String.valueOf(ht.get("Segment")).contains("$~$")){
									String primarySeg=String.valueOf(ht.get("Primary_Segment"));
									if(primarySeg!=null && !primarySeg.equalsIgnoreCase("null") && !primarySeg.equalsIgnoreCase("")){
										String primarysegValue = String.valueOf(ht.get("seg"+primarySeg.substring(primarySeg.indexOf("_")+1)+"_value")).replace(";", "");
										String primarysegCode = String.valueOf(ht.get("seg"+primarySeg.substring(primarySeg.indexOf("_")+1)+"_Code")).replace(";", "");
										valueHT.put(levelNo-1, primarysegValue+"~"+primarysegCode);
									}else{
										String primarySeg1=String.valueOf(ht.get("Segment")).split("\\$~\\$")[0];
										String primarysegValue = String.valueOf(ht.get("seg"+primarySeg1.substring(primarySeg1.indexOf("_")+1)+"_value")).replace(";", "");
										String primarysegCode = String.valueOf(ht.get("seg"+primarySeg1.substring(primarySeg1.indexOf("_")+1)+"_Code")).replace(";", "");
										valueHT.put(levelNo-1, primarysegValue+"~"+primarysegCode);
									}

								}else{
									String nameTemp=String.valueOf(ht.get("value")).split(";")[0];
									valueHT.put(levelNo-1, nameTemp+"~"+String.valueOf(ht.get("Code")));
								}

							}else{
								valueHT.put(levelNo-1, String.valueOf(ht.get("value")));
							}
						}
						for(int i=levelNo-1;i>0;i--){

							addedNode=addedNode.getParentNode();

							if(addedNode.getAttributes().getNamedItem("Type").getNodeValue().equalsIgnoreCase("Data")){
								if(addedNode.getAttributes().getNamedItem("Segment").getNodeValue().contains("$~$")){
									String primarySeg=Globals.getAttrVal4AttrName(addedNode, "Primary_Segment");
									if(primarySeg!=null && !primarySeg.equalsIgnoreCase("null") && !primarySeg.equalsIgnoreCase("")){
										String primarysegValue = addedNode.getAttributes().getNamedItem("seg"+primarySeg.substring(primarySeg.indexOf("_")+1)+"_value").getNodeValue().replace(";", "");
										String primarysegCode = addedNode.getAttributes().getNamedItem("seg"+primarySeg.substring(primarySeg.indexOf("_")+1)+"_Code").getNodeValue().replace(";", "");
										valueHT.put(levelNo-1, primarysegValue+"~"+primarysegCode);
									}else{
										String primarySeg1=addedNode.getAttributes().getNamedItem("Segment").getNodeValue().split("\\$~\\$")[0];

										String primarysegValue = addedNode.getAttributes().getNamedItem("seg"+primarySeg1.substring(primarySeg1.indexOf("_")+1)+"_value").getNodeValue().replace(";", "");
										String primarysegCode = addedNode.getAttributes().getNamedItem("seg"+primarySeg1.substring(primarySeg1.indexOf("_")+1)+"_Code").getNodeValue().replace(";", "");
										valueHT.put(levelNo-1, primarysegValue+"~"+primarysegCode);
									}

								}else{
									String nameTemp=addedNode.getAttributes().getNamedItem("value").getNodeValue().split(";")[0];
									valueHT.put(levelNo-1, nameTemp+"~"+addedNode.getAttributes().getNamedItem("Code").getNodeValue());
								}
							}else{
								valueHT.put(i-1, addedNode.getAttributes().getNamedItem("value").getNodeValue());
							}

						}
						Hashtable tempHT=new Hashtable<>();
						tempHT=(Hashtable)nodeHT.get(j-1);
						String seletedID="";
						String selectedName="";
						if(selectednodeType.equalsIgnoreCase("Rollup") && typeName.equalsIgnoreCase("Hierarchy")){

							selectedName=selectednodeName;
							seletedID=selectedNdID;
						}else{
							seletedID=String.valueOf(tempHT.get("ID"));

							selectedName="Level_"+(levelNo-1);
						}

						Node addedNodetemp=Globals.getNodeByAttrVal(doc, String.valueOf(ht.get("NodeName")), "ID", String.valueOf(ht.get("ID")));

						if(typeName.equalsIgnoreCase("Data")){
							String primarysegValue = "";
							String primarysegCode = "";
							if(String.valueOf(ht.get("Segment")).contains("$~$")){
								String primarySeg=String.valueOf(ht.get("Primary_Segment"));
								if(primarySeg!=null && !primarySeg.equalsIgnoreCase("null") && !primarySeg.equalsIgnoreCase("")){
									primarysegValue = String.valueOf(ht.get("seg"+primarySeg.substring(primarySeg.indexOf("_")+1)+"_value")).replace(";", "");
									primarysegCode = String.valueOf(ht.get("seg"+primarySeg.substring(primarySeg.indexOf("_")+1)+"_Code")).replace(";", "");

								}else{
									String primarySeg1=String.valueOf(ht.get("Segment")).split("\\$~\\$")[0];
									primarysegValue = String.valueOf(ht.get("seg"+primarySeg1.substring(primarySeg1.indexOf("_")+1)+"_value")).replace(";", "");
									primarysegCode = String.valueOf(ht.get("seg"+primarySeg1.substring(primarySeg1.indexOf("_")+1)+"_Code")).replace(";", "");

								}

							}else{
								primarysegValue=String.valueOf(ht.get("value")).split(";")[0];
								primarysegCode=String.valueOf(ht.get("Code")).split(";")[0];

							}
							dimCount = he.insertionofValues2DB(primarysegValue+"~"+primarysegCode, levelNo, typeName,valueHT,basicValueHT,seletedID,selectedName,"ReGenerate",String.valueOf(ht.get("ID")),previoustype4ReGenerate,conn,String.valueOf(ht.get("NodeName")),doc,dimStatusHT,coldetHT,pdatatype,dimCount);

						}else if(typeName.equalsIgnoreCase("Hierarchy")){
							dimCount = he.insertionofValues2DB(String.valueOf(ht.get("value")), levelNo-1, typeName,valueHT,basicValueHT,seletedID,selectedName,"ReGenerate",String.valueOf(ht.get("ID")),previoustype4ReGenerate,conn,String.valueOf(ht.get("NodeName")),doc,dimStatusHT,coldetHT,pdatatype,dimCount);

						}else{
							dimCount = he.insertionofValues2DB(String.valueOf(ht.get("value")), levelNo, typeName,valueHT,basicValueHT,seletedID,selectedName,"ReGenerate",String.valueOf(ht.get("ID")),previoustype4ReGenerate,conn,String.valueOf(ht.get("NodeName")),doc,dimStatusHT,coldetHT,pdatatype,dimCount);

						}


						if(String.valueOf(ht.get("Type")).equalsIgnoreCase("Hierarchy")){
							if(String.valueOf(ht.get("Hierarchy_Type")).equalsIgnoreCase("RollUpHierarchy")){
								Hashtable tempHT1=new Hashtable<>();
								tempHT1=(Hashtable)nodeHT.get(j-1);
								tempHT=(Hashtable)nodeHT.get(j);
								Node afterAddNd=Globals.getNodeByAttrVal(doc, String.valueOf(tempHT.get("NodeName")), "ID", String.valueOf(tempHT.get("ID")));

								previoustype4ReGenerate=String.valueOf(tempHT.get("Type"))+"#~#"+Globals.getAttrVal4AttrName(afterAddNd, "Last_Node_ID");  // code change Menaka 20FEB2014

							}else{
								Node afterAddNd=Globals.getNodeByAttrVal(doc, addedNodetemp.getNodeName(), "ID", String.valueOf(ht.get("ID")));

								previoustype4ReGenerate=String.valueOf(ht.get("Type"))+"#~#"+Globals.getAttrVal4AttrName(afterAddNd, "Last_Node_ID");  // code change Menaka 20FEB2014	

							}

						}else{
							Node afterAddNd=Globals.getNodeByAttrVal(doc, addedNodetemp.getNodeName(), "ID", String.valueOf(ht.get("ID")));
							previoustype4ReGenerate=String.valueOf(ht.get("Type"))+"#~#"+Globals.getAttrVal4AttrName(afterAddNd, "Last_Node_ID");
						}
					}
				}
			}
			conn.close();

		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			String dimStatus = Globals.getErrorString(e);
			dimStatusHT.put("dimResult","Error"); 
			dimStatusHT.put("errorMessage",dimStatus);



		}
		Element ele = (Element)nd;
		String status = (String)dimStatusHT.get("dimResult");
		Date endDate = new Date();
		//		status = status+" finished at "+endDate;
		String errorStatus = (String)dimStatusHT.get("errorMessage");
		errorStatus = errorStatus+" finished at "+endDate+" Count : "+dimCount;
		ele.setAttribute("Dim_Status", status);
		ele.setAttribute("Dim_Status_Details", errorStatus);

		Globals.writeXMLFile(doc, HIERARCHY_XML_DIR, hierarchyXmlFileName);

		System.out.println("flag4reGeneratingFact==>"+flag4reGeneratingFact);
		System.out.println("String.valueOf(dimStatusHT.get())==>"+String.valueOf(dimStatusHT.get("dimResult")));

		if(flag4reGeneratingFact){
			if(String.valueOf(dimStatusHT.get("dimResult")).equalsIgnoreCase("Error")){

				message = String.valueOf(dimStatusHT.get("errorMessage"));
				return;

			}else{

				// code change Menaka 21APR2014


				Thread t=new Thread(new genarateFactsBean(hierID,userName,factGenType));
				t.start();//Code Change Gokul 21FEB2014
			}
		}
		System.out.println("Exiting: " + new Exception().getStackTrace()[0].getClassName() + "."
				+ new Exception().getStackTrace()[0].getMethodName());

	}
	String color4AddUserMsg = "";
	public String getColor4AddUserMsg() {
		return color4AddUserMsg;
	}
	public void setColor4AddUserMsg(String color4AddUserMsg) {
		this.color4AddUserMsg = color4AddUserMsg;
	}

	public void emptyFormfield() {

		try {

			userEmailId = "";
			emailPassword = "";
			listfirstNameTXT = "";
			listlastNameTXT = "";
			listtitleTXT = "";

			listph1TXT = "";
			listph2TXT = "";
			listph3TXT = "";

			listMail1TXT = "";
			listMail2TXT = "";
			listaddressTXT = "";
			listcityTXT = "";
			liststateTXT= "";
			listcountryTXT= "";
			listzipTXT= "";




		}catch (Exception e) {
			// TODO: handle exception
		}



	}
	//start code change Jayaramu 12MAR14
	public void writeUserToXml(String userMail,String useremailPswd,String confrmPassword,Boolean newcustomer,Boolean superPrivilegeVAL,Boolean activeBoxVAL,Boolean disableBoxVAL,String listfirstNameTXT,String listlastNameTXT,String listtitleTXT,String listph1TXT,String listph2TXT,String listph3TXT,String listMail1TXT,String listMail2TXT,String listaddressTXT,String listcityTXT,String liststateTXT,String listcountryTXT,String listzipTXT){
		try{

			// code change Menaka 29MAR2014
			System.out.println("selectedUserList=====1=====>>>"+selectedUserList.size());
			/*if(allUsersAL==null||allUsersAL.size()<=0){
				msg4AddUsr="Get users list before click Add.";
				color4AddUserMsg = "red";
				return;
			}*/
			/*if(selectedUserList.size()<=0){
				msg4AddUsr="Select at least one User from the list.";
				color4AddUserMsg = "red";
				return;
			}*/

			/*if(userMail == null || userMail.equals("") && useremailPswd == null || useremailPswd.equals("")) {
				msg4AddUsr="Please choose all fields and proceed further.";
				return;

			}*/
			if(userMail == null || userMail.equals("") && useremailPswd == null || useremailPswd.equals("")&&confrmPassword==null||confrmPassword.equals("")&&listfirstNameTXT==null||listfirstNameTXT.equals("")) {
				msg4AddUsr="Please choose all fields and proceed further.";
				color4AddUserMsg = "red";
				color4EditUserMsg = "red";
				return;

			}

			if(!useremailPswd.equals(confrmPassword)) {
				msg4AddUsr="Please verify the password to proceed further.";
				color4AddUserMsg = "red";
				color4EditUserMsg = "red";
				return;
			}

			PropUtil prop = new PropUtil();
			String loginXmlFile = prop.getProperty("LOGIN_XML_FILE");
			HIERARCHY_XML_DIR = prop.getProperty("HIERARCHY_XML_DIR");		
			boolean fileexists = Globals.isFileExists(HIERARCHY_XML_DIR,loginXmlFile);
			Document doc = null;
			FileInputStream fis = null;
			FileOutputStream fos = null;
			DocumentBuilderFactory dbf = null;
			DocumentBuilder db = null;

			FacesContext fxContext = FacesContext.getCurrentInstance();
			ExternalContext extContext = fxContext.getExternalContext();
			Map sessionMap = extContext.getSessionMap();
			LoginBean lb = (LoginBean) sessionMap.get("loginBean");
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
			int j=1;
			String accessStartDte = "";
			String accessEndDte = "";

			String dateFormat = prop.getProperty("DATE_FORMAT");
			SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
			//		if(accessStartDate != null){
			//			accessStartDte = formatter.format(accessStartDate);
			//		}
			//		
			//		if(accessEndDate != null && !accessEndDate.equals("")){
			//			accessEndDte = formatter.format(accessEndDate);
			//			 
			//			}


			NodeList nodeList = doc.getElementsByTagName("User");
			for(int x1=0,size= nodeList.getLength(); x1<size; x1++) {
				if (nodeList.item(x1).getNodeType() == Node.ELEMENT_NODE) {

					Element el=(Element)nodeList.item(x1);
					String s1=el.getAttribute("Email_ID");

					String s2=el.getAttribute("CustomerKey");
					if(s1.equalsIgnoreCase(userMail) && s2.equalsIgnoreCase(lb.getCustomerKey()))
					{

						System.out.println("The entered E-mail id is already in use."); 
						msg4AddUsr = "The entered E-mail id is already in use.";  
						/// msg4AddUsr ="The selected User ID is added successfully.";  

						color4AddUserMsg = "red";
						return;

					}

				}
			}

			System.out.println("selectedUserList==========>>>"+selectedUserList.size());

			//	for(int i=0;i<selectedUserList.size();i++){  // code change Menaka 20MAR2014
			//			
			//			
			//			Node getLastChild = firstRootNode.getLastChild().getPreviousSibling();
			//			if(getLastChild == null){
			//				
			//			}
			//			else if(getLastChild.getNodeType() == Node.ELEMENT_NODE){
			//					System.out.println("firstRootNode inside===>>>>"+firstRootNode.getLastChild());
			//					String nodeNumber = getLastChild.getNodeName();
			//					j = Integer.parseInt(nodeNumber.substring(nodeNumber.indexOf("_")+1));
			//				j++;
			//			}

			//			Element childNode = doc.createElement("User_"+j);

			Element childNode = doc.createElement("User");
			firstRootNode.appendChild(childNode);


			//	userLoginId=selectedUserList.get(i).userLoginID;
			// eMailID=selectedUserList.get(i).emailID;

			System.out.println("userLoginId==========>>>"+userLoginId);
			System.out.println("eMailID==========>>>"+eMailID);

			// code change Menaka 21MAR2013

			NodeList ndlist=Globals.getNodeList(HIERARCHY_XML_DIR, loginXmlFile, "Obiee_Users");
			Hashtable UserIDHT=new Hashtable<>();
			Hashtable UserDetailsHT=new Hashtable<>();
			Hashtable UserIDNDetailsHT=new Hashtable<>();
			String attrName="",arrtValue="";
			NodeList ndlist2=ndlist.item(0).getChildNodes();
			int k=0;
			for(int x=0;x<ndlist2.getLength();x++){
				Node nd=ndlist2.item(x);

				UserDetailsHT=new Hashtable<>();
				if(nd.getNodeType()==Node.ELEMENT_NODE){

					UserIDHT.put(k, nd.getTextContent());
					k++;

				}

			}

			/*if(UserIDHT.contains(userLoginId)){

				System.out.println("-------------------"+userLoginId+" already added------------------");
				this.msg4AddUsr="The selected User ID is already added.";
				color4AddUserMsg = "red";
				return;
			}*/

//			System.err.println("&&&&&&&&&&&&&&&&&&&&&&&  :"+listfirstNameTXT);
			/*System.out.println("-------------------"+activeBoxVAL+"------------------");
				String activeBoxStr="";
				if(activeBoxVAL) {
			
			     activeBoxStr="Active";
			
		      }else {
			
		     	activeBoxStr="InActive";
		      }*/
			
			
			
			childNode.setAttribute("Email_ID", userMail);
			childNode.setAttribute("Access_Start_Date", accessStartDte);
			childNode.setAttribute("Access_End_Date", accessEndDte);
			int accessMaxID = Globals.getMaxID(HIERARCHY_XML_DIR, hierarchyConfigFile,"Acess_User_ID", "ID");
			childNode.setAttribute("Access_Unique_ID", String.valueOf(accessMaxID));
			childNode.setAttribute("Access_Type", "Enabled");
			childNode.setAttribute("Allowed_Hierarchies", "");	
			childNode.setAttribute("CustomerKey",lb.getCustomerKey());
			childNode.setAttribute("Super_Privilege_Admin",  String.valueOf(superPrivilegeVAL));	 // code change VIJAY
			childNode.setAttribute("Login_ID",userMail);
			childNode.setAttribute("First_Name",listfirstNameTXT);
			childNode.setAttribute("Last_Name",listlastNameTXT);	
			childNode.setAttribute("Title",listtitleTXT);
			childNode.setAttribute("Phone_Number_1",listph1TXT);	
			childNode.setAttribute("Phone_Number_2",listph2TXT);	
			childNode.setAttribute("Phone_Number_3",listph3TXT);	
			childNode.setAttribute("E_Mail_ID_1",listMail1TXT);
			childNode.setAttribute("E_Mail_ID_2",listMail2TXT);	
			childNode.setAttribute("Address",listaddressTXT);
			childNode.setAttribute("City",listcityTXT);
			childNode.setAttribute("State",liststateTXT);
			childNode.setAttribute("Country",listcountryTXT);
			childNode.setAttribute("Zip",listzipTXT);
			childNode.setAttribute("User_Password",Inventory.store(useremailPswd));
			childNode.setAttribute("New_Customer", String.valueOf(newcustomer));
			childNode.setAttribute("Active", String.valueOf(!activeBoxVAL));
			childNode.setAttribute("Disable", String.valueOf(disableBoxVAL));
			childNode.setTextContent(userMail);


			//}

			Globals.writeXMLFile(doc, HIERARCHY_XML_DIR, loginXmlFile);
			msg4AddUsr = "User saved successfully.";  
			/// msg4AddUsr ="The selected User ID is added successfully.";  
			readUsersFromXml();
            color4AddUserMsg = "blue";
		}catch(Exception e){
			e.printStackTrace();
		}
	}//End code change Jayaramu 12MAR14

	public void emptyMsg() {
		
		try{
			msg4AddUsr="";
			
	   }catch(Exception e){
		e.printStackTrace();
		
	   }
		
		
	}


	//start code change Jayaramu 12MAR14

	public void readUsersFromXml(){

		try{

			userLoginId = "";
			eMailID = "";
			accessStartDate = null;
			accessEndDate = null;
			accessStatus = "";
			userLegendName = "Add User";
			flag4AddUpdUser = "true";
			flag4AddUpdUser1 = "false";
			PropUtil prop = new PropUtil();
			String loginXmlFile = prop.getProperty("LOGIN_XML_FILE");
			HIERARCHY_XML_DIR = prop.getProperty("HIERARCHY_XML_DIR");	
			HeirarchyDataBean hdb;
			userListAL = new ArrayList<>();
			Document doc = Globals.openXMLFile(HIERARCHY_XML_DIR, loginXmlFile);
			NodeList usersList =	Globals.getNodeList(HIERARCHY_XML_DIR, loginXmlFile, "User");  // code change Menaka 26MAR2014
			//		NodeList root = doc.getElementsByTagName("Obiee_Users");
			//		Node firstRootNode = root.item(0);
			//		NodeList childNodes = firstRootNode.getChildNodes();

			String userLoginID = "";
			String emailID = "";
			boolean newcustomer ;
			boolean eactiveBox ;
			boolean edisableBox ;
			String accessStartDate = "";
			String accessEndDate = "";
			String access = "";
			String nodeName = "";

			String editfirstNameTXT = "";
			String editlastNameTXT = "";
			String edittitleTXT = "";
			String editph1TXT = "";
			String editph2TXT = "";
			String editph3TXT = "";
			String editMail1TXT = "";
			String editMail2TXT = "";
			String editaddressTXT = "";
			String editcityTXT = "";
			String editstateTXT = "";
			String editcountryTXT = "";
			String editzipTXT = "";
			String editUserNameTXT =""; 
			String editUserpassTXT =""; 
			int accessID = 0;
			boolean privillage;

			//		System.out.println("childNodes.getLength()=================>>>"+usersList.getLength());

			for(int i=0;i<usersList.getLength();i++){

				if(usersList.item(i).getNodeType() == Node.ELEMENT_NODE){

					//				System.out.println("usersList.item(i).getTextContent().=================>>>"+usersList.item(i).getTextContent());

					if(usersList.item(i).getTextContent().equals("rbid")){  // code change Menaka 
						continue;
					}

					emailID = usersList.item(i).getAttributes().getNamedItem("Email_ID").getTextContent().toString();
					accessStartDate = usersList.item(i).getAttributes().getNamedItem("Access_Start_Date").getTextContent().toString();
					accessEndDate = usersList.item(i).getAttributes().getNamedItem("Access_End_Date").getTextContent().toString();
					access = usersList.item(i).getAttributes().getNamedItem("Access_Type").getTextContent().toString();
					accessID = Integer.parseInt(usersList.item(i).getAttributes().getNamedItem("Access_Unique_ID").getTextContent().toString());
					userLoginID = usersList.item(i).getTextContent().toString();
					nodeName = usersList.item(i).getNodeName();
					privillage=Boolean.parseBoolean(usersList.item(i).getAttributes().getNamedItem("Super_Privilege_Admin").getTextContent().toString());



					editUserNameTXT =usersList.item(i).getAttributes().getNamedItem("Login_ID").getTextContent().toString();

					
					editUserpassTXT = Inventory.retrieve(usersList.item(i).getAttributes().getNamedItem("User_Password").getTextContent().toString());
					newcustomer =Boolean.parseBoolean(usersList.item(i).getAttributes().getNamedItem("New_Customer").getTextContent().toString());
				    eactiveBox	=!Boolean.parseBoolean(usersList.item(i).getAttributes().getNamedItem("Active").getTextContent().toString());	
				    edisableBox	=Boolean.parseBoolean(usersList.item(i).getAttributes().getNamedItem("Disable").getTextContent().toString());
				    
//				    System.out.println(eactiveBox+"0)))))))))  :"+editUserpassTXT);
				    
					editfirstNameTXT =usersList.item(i).getAttributes().getNamedItem("First_Name").getTextContent().toString();
					editlastNameTXT =usersList.item(i).getAttributes().getNamedItem("Last_Name").getTextContent().toString();
					edittitleTXT =usersList.item(i).getAttributes().getNamedItem("Title").getTextContent().toString();
					editph1TXT =usersList.item(i).getAttributes().getNamedItem("Phone_Number_1").getTextContent().toString();
					editph2TXT =usersList.item(i).getAttributes().getNamedItem("Phone_Number_2").getTextContent().toString();
					editph3TXT =usersList.item(i).getAttributes().getNamedItem("Phone_Number_3").getTextContent().toString();
					editMail1TXT =usersList.item(i).getAttributes().getNamedItem("E_Mail_ID_1").getTextContent().toString();
					editMail2TXT =usersList.item(i).getAttributes().getNamedItem("E_Mail_ID_2").getTextContent().toString();
					editaddressTXT =usersList.item(i).getAttributes().getNamedItem("Address").getTextContent().toString();
					editcityTXT =usersList.item(i).getAttributes().getNamedItem("City").getTextContent().toString();
					editstateTXT =usersList.item(i).getAttributes().getNamedItem("State").getTextContent().toString();
					editcountryTXT =usersList.item(i).getAttributes().getNamedItem("Country").getTextContent().toString();
					editzipTXT =usersList.item(i).getAttributes().getNamedItem("Zip").getTextContent().toString();
				

					hdb = new HeirarchyDataBean(userLoginID,emailID,accessStartDate,accessEndDate,access,accessID,nodeName,privillage,editUserNameTXT,editUserpassTXT,newcustomer,eactiveBox,edisableBox,editfirstNameTXT,editlastNameTXT,edittitleTXT,editph1TXT,editph2TXT,editph3TXT,editMail1TXT,editMail2TXT,editaddressTXT,editcityTXT,editstateTXT,editcountryTXT,editzipTXT);

					// hdb = new HeirarchyDataBean(userLoginID,emailID,accessStartDate,accessEndDate,access,accessID,nodeName,privillage);

				//	hdb = new HeirarchyDataBean(userLoginID,emailID,accessStartDate,accessEndDate,access,accessID,nodeName,privillage,editUserNameTXT,editUserpassTXT,newcustomer,editfirstNameTXT,editlastNameTXT,edittitleTXT,editph1TXT,editph2TXT,editph3TXT,editMail1TXT,editMail2TXT,editaddressTXT,editcityTXT,editstateTXT,editcountryTXT,editzipTXT);

					userListAL.add(hdb);
				}}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	//End code change Jayaramu 12MAR14

	//start code change Jayaramu 12MAR14
	
	
	
	

	public void newmode() {
		try{
		

			FacesContext ctx = FacesContext.getCurrentInstance();
			ExternalContext extContext = ctx.getExternalContext();
			Map sessionMap = extContext.getSessionMap();
			LoginRegistrationBean lgnbn = (LoginRegistrationBean) sessionMap.get("loginRegistrationBean");

			lgnbn.userEmailId="";
			lgnbn.emailPassword="";
			lgnbn.confrmPassword = "";
			lgnbn.newcustomerBoxVAL=false;
			lgnbn.superPrivilegeVAL=false;
			lgnbn.activeBoxVAL=false;
			lgnbn.disableBoxVAL=false;
			
			userEmailId ="";
			emailPassword = "";
			confrmPassword="";
			listfirstNameTXT = "";
			listlastNameTXT = "";
			listtitleTXT = "";
			listph1TXT = "";
			listph2TXT = "";
			listph3TXT = "";

			listMail1TXT = "";
			listMail2TXT = "";
			listaddressTXT = "";
			listcityTXT = "";
			liststateTXT = "";
			listcountryTXT = "";
			listzipTXT = "";
			msg4AddUsr="";
			
			PropUtil prop=new PropUtil();
			String configFileName=prop.getProperty("HIERARCHY_CONFIG_FILE");
			String hierLeveldir=prop.getProperty("HIERARCHY_XML_DIR");
			Document doc=Globals.openXMLFile(hierLeveldir, configFileName);
			Element redirectEle = (Element)doc.getElementsByTagName("Download_Template").item(0);
		    redirectURL = redirectEle.getAttribute("URL")==null || redirectEle.getAttribute("URL").trim().isEmpty() ? "https://www.ultraworkflow.com/DocumentWF/downloadtemplate" : redirectEle.getAttribute("URL");
		
			System.out.println("-=-=-=-=-=redirectURL-=-=-=-="+redirectURL);
		}catch(Exception e){
			e.printStackTrace();
			
		}
		
	}
	
	
	
	public void deleteDocsMethod(String attachmentdocName, int index, String docID, String attachmentID){

		try{
			
			
			
			System.out.println("-=-=-=-attachmentdocName-=-=-=::"+attachmentdocName);
			System.out.println("-=-=-=-attachmentID-=-=-=::"+attachmentID);
			System.out.println("-=-=-=-docID-=-=-=::"+docID);
			PropUtil prop = new PropUtil();
			String xmlDir = prop.getProperty("HIERARCHY_XML_DIR");
			String xmlLevelFileName = prop.getProperty("DOCUMENT_XML_FILE");
			Document doc = Globals.openXMLFile(xmlDir, xmlLevelFileName);
			
			Element docNd=(Element)Globals.getNodeByAttrVal(doc, "Document", "Document_ID", docID);
			
			NodeList docsList = docNd.getElementsByTagName("Attachment");
			
			for(int i=0;i<docsList.getLength();i++){
				if(docsList.item(i).getNodeType() == Node.ELEMENT_NODE){
					Node attNodes=docsList.item(i);
					
					 Element attnode = (Element) docsList.item(i);
			         String attnodeID = attnode.getAttribute("AttachMentFile_ID");
			         System.out.println("-=-=-=-attnodeID-=-=-=::"+attnodeID);
			      
			    	if(attnodeID.equals(attachmentID)) {
			    		
					 Node node = docsList.item(i);
					 attNodes.getParentNode().removeChild(node);
					 
					 this.attachmentTabDetailsAL1.remove(i);
					 
			    	}
				
				
				}

			}
			
	
			
		Globals.writeXMLFile(doc, xmlDir, xmlLevelFileName);
			
			
			
		}catch(Exception e){
			e.printStackTrace();
			
		}
		
	}
	
	
	
	
	
	public void deleteUserbyAdmin(String userLoginID, int index){

		try{
			PropUtil prop = new PropUtil();
			String loginXmlFile = prop.getProperty("LOGIN_XML_FILE");
			HIERARCHY_XML_DIR = prop.getProperty("HIERARCHY_XML_DIR");	
			Document laDoc = Globals.openXMLFile(HIERARCHY_XML_DIR, loginXmlFile);
			String xmlDir = "";
			String hierLevelFileName = "";

			xmlDir = prop.getProperty("HIERARCHY_XML_DIR");
			hierLevelFileName  = prop.getProperty("HIERARCHY_XML_FILE");
			Document doc = Globals.openXMLFile(xmlDir, hierLevelFileName);
			//			for(int k=0;k<selectedUserslistAL.size();k++){

			NodeList hierList = doc.getElementsByTagName("Hierarchy_Level");
			boolean flag = false; 
			for(int i=0;i<hierList.getLength();i++){
				if(hierList.item(i).getNodeType() == Node.ELEMENT_NODE){
					Node hierNd = hierList.item(i);
					NodeList hierchNdList = hierNd.getChildNodes();
					for(int j=0;j<hierchNdList.getLength();j++){
						if(hierchNdList.item(j).getNodeType() == Node.ELEMENT_NODE && hierchNdList.item(j).getNodeName().equals("Workflow")){
							Node wfNd = hierchNdList.item(j);
							NodeList wfChNdList = wfNd.getChildNodes();
							for(int m=0;m<wfChNdList.getLength();m++){
								if(wfChNdList.item(m).getNodeType() == Node.ELEMENT_NODE && wfChNdList.item(m).getNodeName().equals("Stage")){
									Node stageNd = wfChNdList.item(m);
									NodeList stageNdList = stageNd.getChildNodes();
									for(int n=0;n<stageNdList.getLength();n++){
										if(stageNdList.item(n).getNodeType() == Node.ELEMENT_NODE && stageNdList.item(n).getNodeName().equals("Employee_Names")){
											//System.out.println("===>"+stageNdList.item(n).getTextContent().trim());
											if(userLoginID.equals(stageNdList.item(n).getTextContent().trim())){
												listOfUsersMsg="User is participated in work flow so you can't delete user. Modify the workflow to delete users.";
												flag = true;
												break;
											}
										}
									}
								}
								if(flag){
									break;
								}
							}
						}

					}
				}

			}
			//				if(flag){
			//					break;
			//				}
			System.out.println("-=-=-=-=-=selectedUserslistAL.indexOf(hdb)-=-=-=::"+index);
			userListAL.remove(index);
			Node deleteNode=Globals.getNodeByAttrVal(laDoc, "User", "Login_ID", userLoginID); // code chang Menaka 26MAR2014
			if(deleteNode.getNodeType() == Node.ELEMENT_NODE){

				deleteNode.getParentNode().removeChild(deleteNode);
			}

			//			}

			Globals.writeXMLFile(laDoc, HIERARCHY_XML_DIR, loginXmlFile);

		}catch(Exception e){
			e.printStackTrace();
		}

	}//End code change Jayaramu 12MAR14
	
	String dbData = "";
	
	
	
	public void uploadFile(FileUploadEvent event) throws Exception {


//		this.dirname = "";


		PropUtil prop = new PropUtil();
//		rbid_metadata_dir = prop.getProperty("RBID_METADATA_DIR");
		String docVersionFolder = prop.getProperty("DOCUMENTVERSION_DIR");
//		createBIMetaXML(rbid_metadata_dir);
//		System.out.println("rbid_metadata_dir: " + rbid_metadata_dir);
		// String target = rbid_metadata_dir;
		File sourceFile = null;
		File targetFile = null;
		String infile = "";
		InputStream fileContent = null;
		//	ischagesMade = true;
		//deletemsg = "";
		try {



			UploadedFile item = event.getUploadedFile();
			infile = item.getName();

			String infilesize = String.valueOf(item.getSize());
			String infiletype = item.getContentType();
			System.out.println("File to be uploaded: " + infile);

			sourceFile = new File(infile);
			docVersionFolder = docVersionFolder+documentNameWF.substring(0, documentNameWF.lastIndexOf("."))+"_"+document_ID+"\\";
			targetFile = new File(docVersionFolder + sourceFile.getName());

			String BIMetadataFile = sourceFile.getName();
			fileContent =item.getInputStream();
			BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(targetFile));
			byte[] buffer = new byte[1024];
			int len;
			while ((len = fileContent.read(buffer)) >= 0) {

				bos.write(buffer, 0, len);
			}

			fileContent.close();

			bos.close();


			if (Globals.isFileExists(docVersionFolder, BIMetadataFile)) {



				System.out.println("BI Metadata File Uploaded Successfully: " + docVersionFolder + BIMetadataFile);
			} else {

				System.out.println("*** Error: BI Metadata File is not Uplodaed: " + docVersionFolder + BIMetadataFile);
				System.out.println("*** Error: Discovery Process Aborted.");

				// ??Devan: Disable Discover Button. If the file is uploaded later correctly, ensure Discover Button is
				// enabled.
				return;
			}
			FacesContext fxContext = FacesContext.getCurrentInstance();
			ExternalContext extContext = fxContext.getExternalContext();
			Map sessionMap = extContext.getSessionMap();
			LoginBean lb = (LoginBean) sessionMap.get("loginBean");
			WorkflowManager.saveAttachment(document_ID, infile, infilesize, infiletype, lb.username, currentStatgeName);
			this.attachmentTabDetailsAL1.clear();
			if(sendPrimaryFileOnly.equalsIgnoreCase("true"))
				this.attachmentTabDetailsAL1 = WorkflowManager.loadAttachmentByStageAndUser(document_ID, currentStatgeName, lb.username);
			else
				this.attachmentTabDetailsAL1 = WorkflowManager.loadAttachmentTable(document_ID);

			System.out.println("attachmentsList ::::"+attachmentTabDetailsAL1);
			System.out.println("Exiting : " + new Exception().getStackTrace()[0].getClassName() + "."
					+ new Exception().getStackTrace()[0].getMethodName());
		} catch (Exception e) {
			// Globals.getException(e);
			e.printStackTrace();
		}

	}
	public void uploadFile3(FileUploadEvent event) throws Exception {


		
		
		String documentNAMEStr="";
		
		
		documentNAMEStr=this.documentNameWF;
		System.out.println("documentNAMEStr :"+documentNAMEStr);
		System.out.println("this.document_ID :"+this.document_ID);
		
	
    	
		this.dirname = "";


		PropUtil prop = new PropUtil();
		rbid_metadata_dir = prop.getProperty("RBID_METADATA_DIR");

		System.out.println("rbid_metadata_dir: " + rbid_metadata_dir);
	
		File sourceFile = null;
		File targetFile = null;
		String infile = "";
		InputStream fileContent = null;
		
		try {


			UploadedFile item = event.getUploadedFile();
			infile = item.getName();
			
			String infilesize = String.valueOf(item.getSize());
			String infiletype = item.getContentType();
			System.out.println("File to be uploaded: " + infile);

			sourceFile = new File(infile);
			
			String tempfolder = infile.substring(0, infile.lastIndexOf("."));
			BIMetadataFile = sourceFile.getName();
			Random ran = new Random();
		
			int val=1;
			
			
			String documentPath = WorkflowManager.createDocumentVersionForWFFromXML(documentNAMEStr, this.document_ID);
			String bi_metadata_dir = documentPath.split("###")[0];
			
			System.out.println("uploadedFolder4Attachment: " + uploadedFolder4Attachment);
			targetFile = new File(bi_metadata_dir+"\\"+documentNAMEStr);
			
			

			this.dirname = sourceFile.getAbsolutePath();

			System.out.println("Source File Location (dirname): " + dirname);

			
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

				
				return;
			}




			System.out.println("Exiting : " + new Exception().getStackTrace()[0].getClassName() + "."
					+ new Exception().getStackTrace()[0].getMethodName());
		} catch (Exception e) {
			// Globals.getException(e);
			e.printStackTrace();
		}

	}

	public String getDbData() {
		listOfUsersMsg = "";
		return dbData;
	}
	public void setDbData(String dbData) {
		this.dbData = dbData;
	}


	String listOfUsersMsg = "";

	public String getListOfUsersMsg() {
		return listOfUsersMsg;
	}
	public void setListOfUsersMsg(String listOfUsersMsg) {
		this.listOfUsersMsg = listOfUsersMsg;
	}
	//start code change Jayaramu 12MAR14
	public void AddeditUser(String userFrom, String loginID){
		try{
			
			/*if(userFrom==null||userFrom.equals("")&&loginID==null||loginID.equals("")&&confrmPassword==null||confrmPassword.equals("")&&listfirstNameTXT==null||listfirstNameTXT.equals("")&&listlastNameTXT==null||listlastNameTXT.equals("")) {
				msg4AddUsr="Please fill all the fields to proceed further.";
				color4EditUserMsg = "red";
				return;
			}*/
			
			/*if(!loginID.equals(confrmPassword)) {
				msg4AddUsr="Please enter valid password to proceed further.";
				color4EditUserMsg = "red";
				return;
			}*/
			this.msg4UpdateUser="";
			System.out.println("userFrom: "+userFrom);	
			boolean editFalg = false;
			if(userFrom.equals("fromClickTabel")){

				if(flag4AddUpdUser1.equals("true")){

					editFalg = true;
				}
			}
			if(userFrom.equals("FromEdit")){
				editFalg = true;
			}
		
			if(editFalg){

				PropUtil prop = new PropUtil();
				userLegendName = "Edit User";
				flag4AddUpdUser = "false";
				flag4AddUpdUser1 = "true";
				System.out.println("-=-=-=-=-=-=loginID-=-=-=-=-=-="+loginID);

				FacesContext ctx = FacesContext.getCurrentInstance();
				ExternalContext extContext = ctx.getExternalContext();
				Map sessionMap = extContext.getSessionMap();
				LoginRegistrationBean lgnbn = (LoginRegistrationBean) sessionMap.get("loginRegistrationBean");
				
				// code change VIJAY 29JUNE2018
				HeirarchyDataBean hdb = null;
				for(int i=0;i<userListAL.size();i++) {
					
					HeirarchyDataBean hdbTemp = (HeirarchyDataBean)userListAL.get(i);
				  	
				  	
				  	if(hdbTemp.getUserLoginID().equalsIgnoreCase(loginID)) {
				  		hdb = hdbTemp;
				  		break;
				  	}
					
				}
				if(hdb == null)
					return;
				
				
				
			
				boolean activebox_editStr;	
				String checkActivestr=String.valueOf(hdb.activeboxval);
				
				if(checkActivestr.equals("Active")) {
					
					activebox_editStr=true;
					
				}else {
					
					activebox_editStr=false;
					
				}
				
				
				lgnbn.userEmailId = hdb.getEditUserNameTXT();
				lgnbn.emailPassword =  hdb.getPasswordID();
				lgnbn.newcustomerBoxVAL =  hdb.isNewcustomerval();
				lgnbn.superPrivilegeVAL =  hdb.isSuperAdminPriv();
				//lgnbn.setActiveBoxVAL(activebox_editStr); 
				lgnbn.setActiveBoxVAL(hdb.isActiveboxval());
				lgnbn.setDisableBoxVAL(hdb.isDisableboxval());
				
			   /* System.out.println("VIJAY::"+Boolean.parseBoolean(hdb.activeboxval));
				System.out.println("VIJAY1::"+hdb.disableboxval);*/
				
				userLoginId4Edit = hdb.getUserLoginID();  // code change Menaka 19MAR2014
				eMailID4Edit = hdb.getEmailID();
				

				listfirstNameTXT = hdb.getEdit_firstNameTXT();
				listlastNameTXT = hdb.getEdit_lastNameTXT();
				listtitleTXT = hdb.getEdit_titleTXT();
				listph1TXT = hdb.getEdit_ph1TXT();
				listph2TXT = hdb.getEdit_ph2TXT();
				listph3TXT = hdb.getEdit_ph3TXT();
				listMail1TXT = hdb.getEdit_Mail1TXT();
				listMail2TXT = hdb.getEdit_Mail2TXT();
				listaddressTXT = hdb.getEdit_addressTXT();
				listcityTXT=hdb.getEdit_cityTXT();
				liststateTXT=hdb.getEdit_stateTXT();
				listcountryTXT=hdb.getEdit_countryTXT();
				listzipTXT=hdb.getEdit_zipTXT();
				System.out.println((String)hdb.getPasswordID() +":%%%%%%%%%%%%%%%%%%"+hdb.isNewcustomerval() +"%%%%%%%%%%%%%%%%%%%%%%% :"+(String)lgnbn.emailPassword);
				String dateFormat = prop.getProperty("DATE_FORMAT");
				SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
				Date startdate = null,enddate = null;

				// code change Menaka 20MAR2014

				if(hdb.accessStartDate!=null&&!hdb.accessStartDate.equals("")){
					startdate = formatter.parse(hdb.accessStartDate);
				}
				if(hdb.endDate!=null&&!hdb.endDate.equals("")){
					enddate = formatter.parse(hdb.endDate);
				}

				boolean superAdminPriv=hdb.superAdminPriv;  


				accessStartDate4Edit = startdate;
				accessEndDate4Edit = enddate;
				accessStatus4Edit = hdb.getAccess();
				superAdminPrivillage=superAdminPriv;  // code change Menaka 25MAR2014

			}else if(userFrom.equals("FromAdd")){

				userLegendName = "Add User";
				flag4AddUpdUser = "true";
				flag4AddUpdUser1 = "false";

				userLoginId = "";
				eMailID = "";
				accessStartDate = null;
				accessEndDate = null;
				accessStatus = "";
			}}catch(Exception e){
				e.printStackTrace();
			}
	}//End code change Jayaramu 12MAR14
	String color4EditUserMsg = "";
	public String getColor4EditUserMsg() {
		return color4EditUserMsg;
	}
	public void setColor4EditUserMsg(String color4EditUserMsg) {
		this.color4EditUserMsg = color4EditUserMsg;
	}
	
	
	
	public void caldocumentPage() {
		
		try {
			
			
			
			HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);

			if(session!=null){   // its take getExternalContext().getSession(false); session
				javax.servlet.http.HttpSession ses=(javax.servlet.http.HttpSession)session;




			}else{  // its take getExternalContext().getSession(false); session

				HttpSession session1 = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
				javax.servlet.http.HttpSession ses1=(javax.servlet.http.HttpSession)session1;

			

			}

		String url = FacesContext.getCurrentInstance().getExternalContext().encodeActionURL(FacesContext.getCurrentInstance().getApplication()
				.getViewHandler().getActionURL(FacesContext.getCurrentInstance(), "/" + "DocumentList.xhtml"));
		
			FacesContext.getCurrentInstance().getExternalContext().redirect(url);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	
	
	public void updateUser(String userEmailId,String emailPassword,String confrmPassword,Boolean newcustomerBoxVAL,Boolean superPrivilegeVAL,Boolean activeBoxVAL,Boolean disableBoxVAL, String listfirstNameTXT,String listlastNameTXT,String listtitleTXT,String listph1TXT,String listph2TXT,String listph3TXT,String listMail1TXT,String listMail2TXT,String listaddressTXT,String listcityTXT,String liststateTXT,String listcountryTXT,String listzipTXT){
		try{

		/*	if(userEmailId==null||userEmailId.equals("")&&emailPassword==null||emailPassword.equals("")&&confrmPassword==null||confrmPassword.equals("")&&listfirstNameTXT==null||listfirstNameTXT.equals("")&&listlastNameTXT==null||listlastNameTXT.equals("")) {
				msg4AddUsr="Please fill all the fields to proceed further.";
				color4EditUserMsg = "red";
				return;
			}
			
			if(!emailPassword.equals(confrmPassword)) {
				msg4AddUsr="Please enter valid password to proceed further.";
				color4EditUserMsg = "red";
				return;
			}*/
			
			this.msg4UpdateUser="";
			FacesContext ctx = FacesContext.getCurrentInstance();
			ExternalContext extContext = ctx.getExternalContext();
			Map sessionMap = extContext.getSessionMap();
			LoginRegistrationBean lgnbn = (LoginRegistrationBean) sessionMap.get("loginRegistrationBean");
			SecurityBean securityVal = (SecurityBean) sessionMap.get("securityBean");
			
			if(securityVal.getAddEditHierarchy().equals("false")) {
			
				msg4AddUsr="You don't have Super Admin privilege so, You cannot save the changes.";
				color4EditUserMsg = "red";
				return;
			}
			
			PropUtil prop = new PropUtil();
			String loginXmlFile = prop.getProperty("LOGIN_XML_FILE");
			HIERARCHY_XML_DIR = prop.getProperty("HIERARCHY_XML_DIR");	
			Document doc = Globals.openXMLFile(HIERARCHY_XML_DIR, loginXmlFile);

			//NodeList deleteNode = doc.getElementsByTagName(selectedUserslistAL.get(0).getNodeName());

			Node dlNode=Globals.getNodeByAttrVal(doc, "User", "Login_ID", lgnbn.userEmailId);

			//System.out.println(Node.ELEMENT_NODE+":"+"userLoginId4Edit :"+userLoginId4Edit +" noDE:"+dlNode.getNodeType());
			//		Node dlNode = deleteNode.item(0);
			String accessStartDte = "";
			String accessEndDte = "";
			if(dlNode.getNodeType() == Node.ELEMENT_NODE){

			String dateFormat = prop.getProperty("DATE_FORMAT");
			SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);

			//			if(eMailID4Edit==null||eMailID4Edit.equals("")){
			//				this.msg4UpdateUser="Enter the E Mail ID";
			//				return;
			//			}
			//			

			/*if(accessStartDate4Edit != null){  // code change Menaka 19MAR2014
				accessStartDte = formatter.format(accessStartDate4Edit);
			}else{
				this.msg4UpdateUser="Enter the Access Start Date.";
				color4EditUserMsg = "red";
				return;
			}

			if(accessEndDate4Edit != null && !accessEndDate4Edit.equals("")){
				accessEndDte = formatter.format(accessEndDate4Edit);

				}else{
					this.msg4UpdateUser="Enter the Access End Date";
					color4EditUserMsg = "red";
					return;
				}

			if(accessStatus4Edit==null||accessStatus4Edit.equals("")){
				this.msg4UpdateUser="Select the Access (enabled / disabled)";
				color4EditUserMsg = "red";
				return;
			}*/

			String checkActivestr=String.valueOf(lgnbn.activeBoxVAL);
			
			if(checkActivestr.equals("true")) {
				
				checkActivestr="Active";
				
			}else {
				
				checkActivestr="InActive";
				
			}

			Element childNode = (Element)dlNode;
			childNode.setAttribute("Email_ID", String.valueOf(lgnbn.userEmailId));
			childNode.setAttribute("Access_Start_Date", accessStartDte);
			childNode.setAttribute("Access_End_Date", accessEndDte);
			childNode.setAttribute("Access_Type", accessStatus4Edit);

			childNode.setAttribute("Login_ID",String.valueOf(lgnbn.userEmailId));

			childNode.setAttribute("First_Name",listfirstNameTXT);
			childNode.setAttribute("Last_Name",listlastNameTXT);
			childNode.setAttribute("Title",listtitleTXT);
			childNode.setAttribute("Phone_Number_1",listph1TXT);
			childNode.setAttribute("Phone_Number_2",listph2TXT);
			childNode.setAttribute("Phone_Number_3",listph3TXT);
			childNode.setAttribute("E_Mail_ID_1",listMail1TXT);
			childNode.setAttribute("E_Mail_ID_2",listMail2TXT);
			childNode.setAttribute("Address",listaddressTXT);
			childNode.setAttribute("City",listcityTXT);
			childNode.setAttribute("State",liststateTXT);
			childNode.setAttribute("Country",listcountryTXT);
			childNode.setAttribute("Zip",listzipTXT);
			childNode.setAttribute("User_Password",Inventory.store(String.valueOf(lgnbn.emailPassword)));
			childNode.setAttribute("Disable",String.valueOf(disableBoxVAL));
			childNode.setAttribute("Active",String.valueOf(!activeBoxVAL));	
			childNode.setAttribute("New_Customer", String.valueOf(newcustomerBoxVAL));
			childNode.setAttribute("Super_Privilege_Admin",String.valueOf(superPrivilegeVAL));
			childNode.setTextContent(userLoginId4Edit);
			Globals.writeXMLFile(doc, HIERARCHY_XML_DIR, loginXmlFile);
			readUsersFromXml();
			msg4AddUsr="User updated successfully.";
			color4EditUserMsg = "blue";
			System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$listfirstNameTXT$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$ :"+listfirstNameTXT);

			}

		}catch(Exception e){
			e.printStackTrace();
		}

	}

	public String getLatestVersionNumber(String hierID){
		String latestVersionNo = "";
		try{
			PropUtil prop=new PropUtil();
			String xmlDir="";
			String hierLevelXmlFileName="";
			String hierLevelVersionXmlFileName="";
			hierLevelVersionXmlFileName = prop.getProperty("HIERARCHY_VERSION_XML_FILE");
			String dateFormat="";
			xmlDir = prop.getProperty("HIERARCHY_XML_DIR");
			hierLevelXmlFileName = prop.getProperty("HIERARCHY_XML_FILE");

			dateFormat = prop.getProperty("DATE_FORMAT");
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
			Document versionDoc = Globals.openXMLFile(xmlDir, hierLevelVersionXmlFileName);
			NodeList hiersndList1 = versionDoc.getElementsByTagName("Hierarchy_Version_Levels");
			Node hiersvernd = hiersndList1.item(0);
			NodeList hiersverndList = hiersvernd.getChildNodes();


			Document doc = Globals.openXMLFile(xmlDir, hierLevelXmlFileName);
			NodeList hiersndList = doc.getElementsByTagName("Hierarchy_Levels");
			Node hiersnd = hiersndList.item(0);
			NodeList hierNdList = hiersnd.getChildNodes();

			int previousVersion[] = new int[hiersverndList.getLength()];
			int Larger=0;
			float currentVersion=0;
			int k=0;
			FacesContext fxContext = FacesContext.getCurrentInstance();
			ExternalContext extContext = fxContext.getExternalContext();
			Map sessionMap = extContext.getSessionMap();
			LoginBean lb = (LoginBean) sessionMap.get("loginBean");
			Node newVersionND = null;
			for(int i=0;i<hierNdList.getLength();i++){
				if(hierNdList.item(i).getNodeType() == Node.ELEMENT_NODE && hierNdList.item(i).getNodeName().equals("Hierarchy_Level")){
					if(Globals.getAttrVal4AttrName(hierNdList.item(i), "Hierarchy_ID").equals(hierID)){
						if(Globals.getAttrVal4AttrName(hierNdList.item(i), "Version") == null || Globals.getAttrVal4AttrName(hierNdList.item(i), "Version").equals("")){
							Element hierEle = (Element)hierNdList.item(i);
							hierEle.setAttribute("Version", "Master");
							hierEle.setAttribute("Restored_From", "Master");
							hierEle.setAttribute("Restore_Date", sdf.format(date));
							hierEle.setAttribute("Created_By", lb.username);
							hierEle.setAttribute("Modified_By", lb.username);
						}
						//					System.out.println("hierNdList.getLength===>"+hierNdList.getLength());

						if(Globals.getAttrVal4AttrName(hierNdList.item(i), "Hierarchy_ID").equals(hierID) && Globals.getAttrVal4AttrName(hierNdList.item(i), "Version").equals("Master"))
							newVersionND = hierNdList.item(i).cloneNode(true);


						if(Globals.getAttrVal4AttrName(hierNdList.item(i), "Version").equalsIgnoreCase("Master")){
							previousVersion[k] = 0;
							k++;
						}
					}
				}

			}
			for(int i=0;i<hiersverndList.getLength();i++){
				if(hiersverndList.item(i).getNodeType() == Node.ELEMENT_NODE && hiersverndList.item(i).getNodeName().equals("Hierarchy_Level")){
					if(Globals.getAttrVal4AttrName(hiersverndList.item(i), "Hierarchy_ID").equals(hierID)){
						previousVersion[k] = Integer.parseInt(Globals.getAttrVal4AttrName(hiersverndList.item(i), "Version").split("\\.")[1]);
						k++;
					}
				}
			}



			Larger = previousVersion[0];
			for(int i=1;i<previousVersion.length-1;i++){
				//				System.out.println("previousVersion[i]===>"+previousVersion[i]);
				//				if(previousVersion[i]!=null)
				if(Larger<previousVersion[i]){
					Larger = previousVersion[i];

				}
			}
			//			System.out.println("previousVersion===>"+Larger);
			if(Larger == 0 && previousVersion.length<=0){
				latestVersionNo = "1.0";
			}else{
				//				if(String.valueOf(Larger).contains(".")){
				String version = String.valueOf(Larger);
				int version1 = Larger+1;

				latestVersionNo = "1"+"."+String.valueOf(version1);

				int verNo = Integer.parseInt(version)+2; //code change Jayaramu 22MAR14
				this.versionNumber = String.valueOf(Larger).split("\\.")[0]+"."+String.valueOf(verNo);
				//				}
			}
			System.out.println("latestVersionNo===>"+latestVersionNo);
			newVersionNo=latestVersionNo;


			return latestVersionNo;
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return latestVersionNo;

	}


	//start code change Vishnu 19Mar2014

	public void savingVersion(String hierID) {
		try{
			msg1="";
			PropUtil prop=new PropUtil();
			String xmlDir="";
			String hierLevelXmlFileName="";
			String hierLevelVersionXmlFileName="";
			hierLevelVersionXmlFileName = prop.getProperty("HIERARCHY_VERSION_XML_FILE");
			String dateFormat="";
			xmlDir = prop.getProperty("HIERARCHY_XML_DIR");
			hierLevelXmlFileName = prop.getProperty("HIERARCHY_XML_FILE");

			dateFormat = prop.getProperty("DATE_FORMAT");
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
			Document versionDoc = Globals.openXMLFile(xmlDir, hierLevelVersionXmlFileName);
			NodeList hiersndList1 = versionDoc.getElementsByTagName("Hierarchy_Version_Levels");
			Node hiersvernd = hiersndList1.item(0);
			NodeList hiersverndList = hiersvernd.getChildNodes();


			Document doc = Globals.openXMLFile(xmlDir, hierLevelXmlFileName);
			NodeList hiersndList = doc.getElementsByTagName("Hierarchy_Levels");
			Node hiersnd = hiersndList.item(0);
			NodeList hierNdList = hiersnd.getChildNodes();
			String versionNo = "";
			int previousVersion[] = new int[hiersverndList.getLength()];
			int Larger=0;
			float currentVersion=0;
			int k=0;
			FacesContext fxContext = FacesContext.getCurrentInstance();
			ExternalContext extContext = fxContext.getExternalContext();
			Map sessionMap = extContext.getSessionMap();
			LoginBean lb = (LoginBean) sessionMap.get("loginBean");
			Node newVersionND = null;
			for(int i=0;i<hierNdList.getLength();i++){
				if(hierNdList.item(i).getNodeType() == Node.ELEMENT_NODE && hierNdList.item(i).getNodeName().equals("Hierarchy_Level")){
					if(Globals.getAttrVal4AttrName(hierNdList.item(i), "Hierarchy_ID").equals(hierID)){
						if(Globals.getAttrVal4AttrName(hierNdList.item(i), "Version") == null || Globals.getAttrVal4AttrName(hierNdList.item(i), "Version").equals("")){
							Element hierEle = (Element)hierNdList.item(i);
							hierEle.setAttribute("Version", "Master");
							hierEle.setAttribute("Restored_From", "Master");
							hierEle.setAttribute("Restore_Date", sdf.format(date));
							hierEle.setAttribute("Created_By", lb.username);
							hierEle.setAttribute("Modified_By", lb.username);
						}
						System.out.println("hierNdList.getLength===>"+hierNdList.getLength());

						if(Globals.getAttrVal4AttrName(hierNdList.item(i), "Hierarchy_ID").equals(hierID) && Globals.getAttrVal4AttrName(hierNdList.item(i), "Version").equals("Master"))
							newVersionND = hierNdList.item(i).cloneNode(true);


						if(Globals.getAttrVal4AttrName(hierNdList.item(i), "Version").equalsIgnoreCase("Master")){
							previousVersion[k] = 0;
							k++;
						}
					}
				}

			}
			for(int i=0;i<hiersverndList.getLength();i++){
				if(hiersverndList.item(i).getNodeType() == Node.ELEMENT_NODE && hiersverndList.item(i).getNodeName().equals("Hierarchy_Level")){
					if(Globals.getAttrVal4AttrName(hiersverndList.item(i), "Hierarchy_ID").equals(hierID)){
						previousVersion[k] = Integer.parseInt(Globals.getAttrVal4AttrName(hiersverndList.item(i), "Version").split("\\.")[1]);
						k++;
					}
				}
			}



			Larger = previousVersion[0];
			for(int i=1;i<previousVersion.length;i++){
				//System.out.println("previousVersion[i]===>"+previousVersion[i]);
				//				if(previousVersion[i]!=null)
				if(Larger<previousVersion[i]){
					Larger = previousVersion[i];

				}
			}
			System.out.println("previousVersion===>"+Larger);
			if(Larger == 0 && previousVersion.length<=0){
				versionNo = "1.0";
			}else{
				//				if(String.valueOf(Larger).contains(".")){
				String version = String.valueOf(Larger);
				int version1 = Larger+1;

				versionNo = "1"+"."+String.valueOf(version1);

				int verNo = Integer.parseInt(version)+2; //code change Jayaramu 22MAR14
				this.versionNumber = String.valueOf(Larger).split("\\.")[0]+"."+String.valueOf(verNo);
				//				}
			}
			System.out.println("versionNo===>"+versionNo);
			//			System.out.println("note4VersionRestore===>"+note4VersionRestore);
			Element newVersionEle = (Element) newVersionND;
			newVersionEle.setAttribute("Version", versionNo);
			newVersionEle.setAttribute("Created_Date", sdf.format(date));
			newVersionEle.setAttribute("Modified_Date", sdf.format(date));
			newVersionEle.setAttribute("Created_By", lb.username);
			newVersionEle.setAttribute("Modified_By", lb.username);
			newVersionEle.setAttribute("Was_Master", "NO");
			newVersionEle.setAttribute("Was_Master_End_Date", "NO");
			newVersionEle.setAttribute("Comment", note4VersionRestore);  // code change Menaka 29MAR2014
			Node temp = versionDoc.importNode(newVersionND, true);
			hiersvernd.appendChild(temp);
			Globals.writeXMLFile(versionDoc, xmlDir, hierLevelVersionXmlFileName);
			Globals.writeXMLFile(doc, xmlDir, hierLevelXmlFileName);
			msg1 = "Hierarchy is saved as version "+versionNo;
			color4HierTreeMsg = "blue";
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}


	}


	public void getVersionNumber(String hierID){ //code change Jayaramu 22MAR14

		try{
			PropUtil prop=new PropUtil();
			String xmlDir="";
			String hierLevelXmlFileName="";
			String hierLevelVersionXmlFileName="";
			hierLevelVersionXmlFileName = prop.getProperty("HIERARCHY_VERSION_XML_FILE");
			String dateFormat="";
			xmlDir = prop.getProperty("HIERARCHY_XML_DIR");
			hierLevelXmlFileName = prop.getProperty("HIERARCHY_XML_FILE");


			Document versionDoc = Globals.openXMLFile(xmlDir, hierLevelVersionXmlFileName);
			NodeList hiersndList1 = versionDoc.getElementsByTagName("Hierarchy_Version_Levels");
			Node hiersvernd = hiersndList1.item(0);
			NodeList hiersverndList = hiersvernd.getChildNodes();


			String versionNo = "0";
			int previousVersion[] = new int[hiersverndList.getLength()];
			int Larger=0;
			float currentVersion=0;
			int k=0;

			Node newVersionND = null;

			for(int i=0;i<hiersverndList.getLength();i++){
				if(hiersverndList.item(i).getNodeType() == Node.ELEMENT_NODE && hiersverndList.item(i).getNodeName().equals("Hierarchy_Level")){
					if(Globals.getAttrVal4AttrName(hiersverndList.item(i), "Hierarchy_ID").equals(hierID)){
						previousVersion[k] = Integer.parseInt(Globals.getAttrVal4AttrName(hiersverndList.item(i), "Version").split("\\.")[1]);
						k++;
					}
				}
			}


			//		   if(previousVersion[0]!=null)  // Code Change Bharath 23MAR14
			//		   {
			Larger = previousVersion[0];
			for(int i=1;i<previousVersion.length;i++){
				//System.out.println("previousVersion[i]===>"+previousVersion[i]);
				//			    if(previousVersion[i]!=null)
				if(Larger<previousVersion[i]){
					Larger = previousVersion[i];

				}
			} 
			//		   }

			System.out.println("previousVersion===>"+Larger);
			if(Larger == 0){
				versionNo = "1.0";
			}else{
				//		    if(String.valueOf(Larger).contains(".")){
				//		     String version = String.valueOf(Larger).split("\\.")[1];
				//		     int version1 = Integer.parseInt(version)+1;
				Larger = Larger+1;
				versionNo = "1"+"."+String.valueOf(Larger);
				//		    }
			}


			this.versionNumber = versionNo;
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	String fullmsg4VersDisp = "";


	public String getFullmsg4VersDisp() {
		return fullmsg4VersDisp;
	}
	public void setFullmsg4VersDisp(String fullmsg4VersDisp) {
		this.fullmsg4VersDisp = fullmsg4VersDisp;
	}

	String fullmsg2 = "";

	public String getFullmsg2() {
		return fullmsg2;
	}
	public void setFullmsg2(String fullmsg2) {
		this.fullmsg2 = fullmsg2;
	}
	public void restoringVersion(String hierID) {
		try {

			msg4VersDisp="";	

			if(versionNumberAL==null||versionNumberAL.size()<=0){  // code change Menaka 28MAR2014
				msg4VersDisp="There is no version to restore";
				System.out.println(msg4VersDisp);
				return;
			}

			if(versionSelectedList.size()<=0){   // code change Menaka 28MAR2014
				msg4VersDisp="Please select a version of this hierarchy to proceed further.";
				System.out.println(msg4VersDisp);
				return;
			}

			PropUtil prop=new PropUtil();
			String xmlDir="";
			String hierLevelXmlFileName="";
			String dateFormat="";
			xmlDir = prop.getProperty("HIERARCHY_XML_DIR");
			hierLevelXmlFileName = prop.getProperty("HIERARCHY_XML_FILE");
			String hierLevelVersionXmlFileName="";

			hierLevelVersionXmlFileName = prop.getProperty("HIERARCHY_VERSION_XML_FILE");
			this.hierLevelVersionXmlFileName = hierLevelVersionXmlFileName;
			Document versionDoc = Globals.openXMLFile(xmlDir, hierLevelVersionXmlFileName);
			NodeList hierversionsndList = versionDoc.getElementsByTagName("Hierarchy_Version_Levels");
			Node hierversionsnd = hierversionsndList.item(0);
			NodeList hierversionNdList = hierversionsnd.getChildNodes();



			dateFormat = prop.getProperty("DATE_FORMAT");
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
			Document doc = Globals.openXMLFile(xmlDir, hierLevelXmlFileName);
			NodeList hiersndList = doc.getElementsByTagName("Hierarchy_Levels");
			Node hiersnd = hiersndList.item(0);
			NodeList hierNdList = hiersnd.getChildNodes();
			String versionNo = "";

			float currentVersion=0;
			int previousVersion[] = new int[hierversionNdList.getLength()];
			int Larger=0;
			FacesContext fxContext = FacesContext.getCurrentInstance();
			ExternalContext extContext = fxContext.getExternalContext();
			Map sessionMap = extContext.getSessionMap();
			LoginBean lb = (LoginBean) sessionMap.get("loginBean");
			Node newVersionND = null;
			Node newMasterNd = null;
			int k=0;
			HierarchydataBean hyDB = (HierarchydataBean) versionSelectedList.get(0);
			String versionSelected = hyDB.getVersionNo();
			for(int i=0;i<hierNdList.getLength();i++){
				if(hierNdList.item(i).getNodeType() == Node.ELEMENT_NODE && hierNdList.item(i).getNodeName().equals("Hierarchy_Level")){
					if(Globals.getAttrVal4AttrName(hierNdList.item(i), "Hierarchy_ID").equals(hierID)){
						if(Globals.getAttrVal4AttrName(hierNdList.item(i), "Hierarchy_ID").equals(hierID) && Globals.getAttrVal4AttrName(hierNdList.item(i), "Version").equals("Master")){
							newVersionND = hierNdList.item(i).cloneNode(true);
							hiersnd.removeChild(hierNdList.item(i));
							//							previousVersion[k] = Float.parseFloat("0");
							//							k++;
							//							currentVersion = 0;
						}

					}
				}
			}
			for(int i=0;i<hierversionNdList.getLength();i++){
				if(hierversionNdList.item(i).getNodeType() == Node.ELEMENT_NODE && hierversionNdList.item(i).getNodeName().equals("Hierarchy_Level")){
					if(Globals.getAttrVal4AttrName(hierversionNdList.item(i), "Hierarchy_ID").equals(hierID)){
						previousVersion[k] = Integer.parseInt(Globals.getAttrVal4AttrName(hierversionNdList.item(i), "Version").split("\\.")[1]);
						//						System.out.println("previousVersion===>"+k);
						k++;
						//						
						if(Globals.getAttrVal4AttrName(hierversionNdList.item(i), "Hierarchy_ID").equals(hierID) && Globals.getAttrVal4AttrName(hierversionNdList.item(i), "Version").equals(versionSelected)){
							newMasterNd = hierversionNdList.item(i).cloneNode(true);
						}
					}

				}
			}





			System.out.println("previousVersion[0]===>"+previousVersion.length);
			Larger = previousVersion[0];
			for(int i=1;i<previousVersion.length;i++){
				//System.out.println("previousVersion[i]===>"+previousVersion[i]);
				//				if(previousVersion[i]!=null)
				if(Larger<previousVersion[i]){
					Larger = previousVersion[i];

				}
			}

			System.out.println("previousVersion end===>"+Larger);
			//			if(String.valueOf(Larger).contains(".")){
			//				String version = String.valueOf(Larger).split("\\.")[1];
			//				int version1 = Integer.parseInt(version)+1;
			versionNo = "1"+"."+String.valueOf(Larger+1);
			//			}
			System.out.println("versionNo===>"+versionNo);
			System.out.println("hyDB.getVersionNo()===>"+versionSelected);
			Element newVersionEle = (Element) newVersionND;
			Element newMasterEle = (Element) newMasterNd;
			newMasterEle.setAttribute("Restored_From", versionSelected);
			newMasterEle.setAttribute("Restore_Date", sdf.format(date));
			newMasterEle.setAttribute("Version", "Master");

			newVersionEle.setAttribute("Was_Master", "YES");
			newVersionEle.setAttribute("Was_Master_End_Date", sdf.format(date));
			newVersionEle.setAttribute("Version", versionNo);
			newVersionEle.setAttribute("Comment", note4VersionRestore);  // code change Menaka 29MAR2014

			Node masterndTemp = doc.importNode(newMasterNd, true);
			Node newVersionNDTemp = versionDoc.importNode(newVersionND, true);
			hiersnd.appendChild(masterndTemp);
			hierversionsnd.appendChild(newVersionNDTemp);
			Globals.writeXMLFile(doc, xmlDir, hierLevelXmlFileName);
			Globals.writeXMLFile(versionDoc, xmlDir, this.hierLevelVersionXmlFileName);
			this.versionNo = "Master";
			ReportsInvXMLTreeBean rpt = new ReportsInvXMLTreeBean(hierarchyXmlFileName, hierarchy_ID,null,"");
			rpt.getReportsForSession(hierarchyXmlFileName, hierarchy_ID,null,"");

			HeaderBean hb=new HeaderBean();

			msg4VersDisp="The selected version (Version # "+versionSelected+") is restored as the new Master Hierarchy. The existing Master Hierarchy is saved as Version # "+versionNo+". Refresh the table to view the restored version."; // code change Menaka 02APR2014
			fullmsg4VersDisp =msg4VersDisp;
			if(msg4VersDisp.length()>66){
				msg4VersDisp = msg4VersDisp.substring(0, 66);
			}
			System.out.println("msg4VersDisp=================>>>"+msg4VersDisp);

			msg2="The selected version (Version # "+versionSelected+") is restored as the new Master Hierarchy. The existing Master Hierarchy is saved as Version # "+versionNo;
			fullmsg2 = msg2;
			if(msg2.length()>66){
				msg2 = msg2.substring(0, 66);
			}
			//			msg1="Hiearchy is restored from version "+versionSelected;	
			//			hb.processMenu("Hierarchytree.xhtml");  // code comment Menaka 02APR2014
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public ArrayList versionNumberAL = new ArrayList<>();

	public ArrayList getVersionNumberAL() {
		return versionNumberAL;
	}
	public void setVersionNumberAL(ArrayList versionNumberAL) {
		this.versionNumberAL = versionNumberAL;
	}

	public ArrayList versionSelectedList = new ArrayList<>();

	public ArrayList getVersionSelectedList() {
		return versionSelectedList;
	}
	public void setVersionSelectedList(ArrayList versionSelectedList) {
		this.versionSelectedList = versionSelectedList;
	}

	public void redirectPage(String redirectPage){
		try{
			versionNo = "Master";
			HeaderBean hb=new HeaderBean();

			hb.processMenu(redirectPage);
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	public String hierLevelVersionXmlFileName="";
	public String getHierLevelVersionXmlFileName() {
		return hierLevelVersionXmlFileName;
	}
	public void setHierLevelVersionXmlFileName(String hierLevelVersionXmlFileName) {
		this.hierLevelVersionXmlFileName = hierLevelVersionXmlFileName;
	}
	public void displayingVersion(String hierID) {
		try {
			msg1="";
			msg4VersDisp="";   // code change Menaka 28MAR2014
			versionNumberAL = new ArrayList<>();
			hierarchy_ID = hierID;
			PropUtil prop=new PropUtil();
			String xmlDir="";
			String hierLevelVersionXmlFileName="";
			String dateFormat="";
			xmlDir = prop.getProperty("HIERARCHY_XML_DIR");
			hierLevelVersionXmlFileName = prop.getProperty("HIERARCHY_VERSION_XML_FILE");

			Document doc = Globals.openXMLFile(xmlDir, hierLevelVersionXmlFileName);
			NodeList hiersndList = doc.getElementsByTagName("Hierarchy_Version_Levels");
			Node hiersnd = hiersndList.item(0);
			NodeList hierNdList = hiersnd.getChildNodes();
			for(int i=0;i<hierNdList.getLength();i++){
				if(hierNdList.item(i).getNodeType() == Node.ELEMENT_NODE && 
						hierNdList.item(i).getNodeName().equals("Hierarchy_Level") && Globals.getAttrVal4AttrName(hierNdList.item(i), "Hierarchy_ID").equals(hierID)){
					hierarchyName = Globals.getAttrVal4AttrName(hierNdList.item(i), "Hierarchy_Name");
					if(Globals.getAttrVal4AttrName(hierNdList.item(i), "Version")==null){
						msg1 = "Hierarchy doesn't have any versions";
						color4HierTreeMsg = "red";
					}else{
						if(!Globals.getAttrVal4AttrName(hierNdList.item(i), "Version").equalsIgnoreCase("Master")){
							//							System.out.println("===>"+Globals.getAttrVal4AttrName(hierNdList.item(i), "Created_Date"));
							HierarchydataBean hyDB = new HierarchydataBean(Globals.getAttrVal4AttrName(hierNdList.item(i), "Version"),
									Globals.getAttrVal4AttrName(hierNdList.item(i), "Created_Date"),Globals.getAttrVal4AttrName(hierNdList.item(i), "Created_By"),Globals.getAttrVal4AttrName(hierNdList.item(i), "Comment"));
							versionNumberAL.add(hyDB);


						}
					}
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	public String versionNo="";
	public String getVersionNo() {
		return versionNo;
	}
	public void setVersionNo(String versionNo) {
		this.versionNo = versionNo;
	}
	public boolean render4Version=false;
	public boolean isRender4Version() {
		return render4Version;
	}
	public void setRender4Version(boolean render4Version) {
		this.render4Version = render4Version;
	}


	public void additionalInfoMethod() {
		try {




		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}



	public void viewHierVersion() {
		try {

			msg4VersDisp="";
			msg2="";


			if(versionNumberAL==null||versionNumberAL.size()<=0){  // code change Menaka 28MAR2014
				msg4VersDisp="There is no version to view";
				System.out.println(msg4VersDisp);
				return;
			}

			if(versionSelectedList.size()<=0){   // code change Menaka 28MAR2014
				msg4VersDisp="Please select a version of this Hierarchy to proceed further.";
				System.out.println(msg4VersDisp);
				return;
			}

			//			System.out.println("msg4VersDisp===========>>>"+msg4VersDisp);

			HierarchydataBean hyDB = (HierarchydataBean) versionSelectedList.get(0);
			selectedRows = new ArrayList<ReportTree>();
			//			
			PropUtil prop=new PropUtil();
			hierLevelVersionXmlFileName = prop.getProperty("HIERARCHY_VERSION_XML_FILE");
			versionNo=hyDB.versionNo;
			ReportsInvXMLTreeBean rpt = new ReportsInvXMLTreeBean(hierLevelVersionXmlFileName, hierarchy_ID,null,"version",hyDB.versionNo);
			rpt.getReportsForSession(hierLevelVersionXmlFileName, hierarchy_ID,null,"version",hyDB.versionNo);
			//			rpt.getGRSDbData1(null, "", hyDB.versionNo);
			//			ReportsInvXMLTreeBean rpt1 =new ReportsInvXMLTreeBean();
			HeaderBean hb=new HeaderBean();
			render4Version=true;
			msg4VersDisp="";
			hb.processMenu("viewingVersion.xhtml");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}


	public void logChanges(String action, Hashtable auditHT, Hashtable auditcoldetHT, Connection con){

		try{
			if(auditEnable.equals("true") && auditDisable.equals("false")){
				switch (action) {
				case "Add":{
					logChanges4Add(auditHT,auditcoldetHT,con);
					break;
				}
				case "Delete":{
					logChanges4Add(auditHT,auditcoldetHT,con);
					break;
				}
				case "Modify":{
					logChanges4Add(auditHT,auditcoldetHT,con);
					break;
				}
				case "Move":{
					logChanges4Add(auditHT,auditcoldetHT,con);
					break;
				}
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}

	}

	public String getConcatedString4AttrHT(Hashtable attrHT){
		String concatedString="";
		try{

			Enumeration e=attrHT.keys();
			Hashtable ht1=new Hashtable<>();
			int i=0;
			while(e.hasMoreElements()){
				ht1.put(i, e.nextElement());
				i++;
			}
			//			   String temp="";
			for(int j=0;j<ht1.size();j++){
				concatedString=concatedString+String.valueOf(ht1.get(j))+"="+attrHT.get(String.valueOf(ht1.get(j)))+"#$#";
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return concatedString;
	}
	public void logChanges4Add(Hashtable auditHT, Hashtable auditcoldetHT, Connection con){
		try{
			Hashtable columnNameHT = Globals.auditColumnName();
			String sql="";
			sql = "insert into Hierarchy_audit (";
			for(int i=0;i<columnNameHT.size();i++){
				if(!String.valueOf(auditHT.get(i)).equals(""))
					sql = sql+String.valueOf(columnNameHT.get(i))+",";
			}
			sql=sql.substring(0,sql.length()-1)+")";
			sql=sql+"values (";
			for(int i=0;i<columnNameHT.size();i++){
				if(!String.valueOf(auditHT.get(i)).equals(""))
					sql=sql+"?"+",";
			}
			sql=sql.substring(0,sql.length()-1)+")";
			System.out.println("sql==>"+sql);
			PreparedStatement ps=con.prepareStatement(sql);
			String pdatatype="";
			genarateFactsBean gfb = new genarateFactsBean("","","");
			int index=1;
			for(int j=0;j<columnNameHT.size();j++){
				if(!String.valueOf(auditHT.get(j)).equals("")){
					System.out.println("j===>"+auditHT.get(j));
					//					coldetHT=gfb.getDatatypefromDBtable("WC_FLEX_HIERARCHY_D."+String.valueOf(columnNameHT.get(j)),dbConn,"","","");
					Hashtable coldetHTTemp=(Hashtable) auditcoldetHT.get(j);
					pdatatype=gfb.preferredDataType4SQLDataType(coldetHTTemp);
					String tarmesureCol=(String)coldetHTTemp.get(4);
					System.out.println(pdatatype+"Target Measure Column Name: "+tarmesureCol);
					if(pdatatype!=null&&!pdatatype.equalsIgnoreCase(""))
					{
						String tempvalue=String.valueOf(auditHT.get(j));
						if(tempvalue==null||tempvalue.equalsIgnoreCase("")||tempvalue.equalsIgnoreCase("null"))
						{
							tempvalue="0";
						}
						if(pdatatype.equalsIgnoreCase("String"))
						{
							//						mcolumnValues.add(joinQueryRS.getString(tarmesureCol));
							String temp=String.valueOf(tempvalue);
							ps.setString(index, temp);
							index++;

						}else if(pdatatype.equalsIgnoreCase("int"))
						{
							//						mcolumnValues.add(joinQueryRS.getInt(tarmesureCol));
							if(tempvalue.contains("."))
							{
								double temp=Double.parseDouble(tempvalue);
								int tempint=(int)Math.round(temp);
								ps.setInt(index, Integer.parseInt(String.valueOf(auditHT.get(j))));
								index++;
							}else{
								ps.setInt(index, Integer.parseInt(String.valueOf(auditHT.get(j))));
								index++;
							}
						}else if(pdatatype.equalsIgnoreCase("double"))
						{
							//						mcolumnValues.add(joinQueryRS.getDouble(tarmesureCol));
							ps.setDouble(index, Double.parseDouble(tempvalue));
							index++;
							//						psinsReportDataSQL.setDouble(ii,Double.parseDouble(tempvalue));
							//						ii++;
						}else if(pdatatype.equalsIgnoreCase("date"))
						{
							//						mcolumnValues.add(joinQueryRS.getDate(tarmesureCol));
							ps.setDate(index, (java.sql.Date)(auditHT.get(j)));
							index++;
							//						psinsReportDataSQL.setDate(ii,(java.sql.Date)tarMeasureColValues.get(g));
							//						ii++;
						}else if(pdatatype.equalsIgnoreCase("timestamp"))
						{
							//						mcolumnValues.add(joinQueryRS.getTimestamp(tarmesureCol));
							ps.setTimestamp(index, Timestamp.valueOf(String.valueOf((auditHT.get(j))).replaceAll("/", "-")));
							index++;
							//						psinsReportDataSQL.setTimestamp(ii,(Timestamp)tarMeasureColValues.get(g));
							//						ii++;
						}else if(pdatatype.equalsIgnoreCase("long"))
						{
							//						System.out.println("222"+tempvalue);
							ps.setLong(index, Long.parseLong(tempvalue));
							index++;
							//						psinsReportDataSQL.setLong(ii,Long.parseLong(tempvalue));
							//						ii++;
						}

					}
				}
			}

			ps.addBatch();
			ps.executeBatch();
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	public String test(Node parentnd, String nodeid, Node childNd, String childNodeid,Document doc) {
		String Position="";
		try{
			Node parentndTemp=parentnd;
			Node childNdTemp=childNd;
			String nodeidtemp=nodeid;
			String parentnodeName = parentndTemp.getNodeName();
			String childNdName = childNdTemp.getNodeName();
			String childNodeidTemp = childNodeid;

			XPath xPath = XPathFactory.newInstance().newXPath();
			for(int i=1;i<=parentndTemp.getChildNodes().getLength();i++){
				String expr = "//"+parentnodeName+"[@ID = "+nodeidtemp+"]/"+childNdName+"[position() = "+i+"]";
				Node nd=(Node)xPath.compile(expr).evaluate(doc, XPathConstants.NODE);
				if(Globals.getAttrVal4AttrName(nd, "ID").equals(childNodeid)){
					System.out.println("Position===>"+i);
					Position = String.valueOf(i);
					break;
				}
			}

		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return Position;
	}

	ArrayList auditRecordSelectedList = new ArrayList<>();
	public ArrayList getAuditRecordSelectedList() {
		return auditRecordSelectedList;
	}
	public void setAuditRecordSelectedList(ArrayList auditRecordSelectedList) {
		this.auditRecordSelectedList = auditRecordSelectedList;
	}

	ArrayList auditRecordAL = new ArrayList<>();

	public ArrayList getAuditRecordAL() {
		return auditRecordAL;
	}
	public void setAuditRecordAL(ArrayList auditRecordAL) {
		this.auditRecordAL = auditRecordAL;
	}

	TreeMap stringoperTM = new TreeMap<>();
	public TreeMap getStringoperTM() {
		return stringoperTM;
	}
	public void setStringoperTM(TreeMap stringoperTM) {
		this.stringoperTM = stringoperTM;
	}

	TreeMap intoperTM = new TreeMap<>();

	public TreeMap getIntoperTM() {
		return intoperTM;
	}
	public void setIntoperTM(TreeMap intoperTM) {
		this.intoperTM = intoperTM;
	}

	public ArrayList workflowAL = new ArrayList<>();

	public ArrayList getWorkflowAL() {
		return workflowAL;
	}
	public void setWorkflowAL(ArrayList workflowAL) {
		this.workflowAL = workflowAL;
	}

	public ArrayList selectedWorkflowAL = new ArrayList<>();
	public ArrayList getSelectedWorkflowAL() {
		return selectedWorkflowAL;
	}
	public void setSelectedWorkflowAL(ArrayList selectedWorkflowAL) {
		this.selectedWorkflowAL = selectedWorkflowAL;
	}

	String WFdateTimeOper = "";
	public String getWFdateTimeOper() {
		return WFdateTimeOper;
	}
	public void setWFdateTimeOper(String wFdateTimeOper) {
		WFdateTimeOper = wFdateTimeOper;
	}

	String WFActionByOper = "";
	public String getWFActionByOper() {
		return WFActionByOper;
	}
	public void setWFActionByOper(String wFActionByOper) {
		WFActionByOper = wFActionByOper;
	}


	String roleOper = "";


	public String getRoleOper() {
		return roleOper;
	}
	public void setRoleOper(String roleOper) {
		this.roleOper = roleOper;
	}


	String fromStageOper = "";
	public String getFromStageOper() {
		return fromStageOper;
	}
	public void setFromStageOper(String fromStageOper) {
		this.fromStageOper = fromStageOper;
	}


	String toStageOper = "";
	public String getToStageOper() {
		return toStageOper;
	}
	public void setToStageOper(String toStageOper) {
		this.toStageOper = toStageOper;
	}


	String fromStatusOper = "";
	public String getFromStatusOper() {
		return fromStatusOper;
	}
	public void setFromStatusOper(String fromStatusOper) {
		this.fromStatusOper = fromStatusOper;
	}


	String memberStatusOper = "";
	public String getMemberStatusOper() {
		return memberStatusOper;
	}
	public void setMemberStatusOper(String memberStatusOper) {
		this.memberStatusOper = memberStatusOper;
	}


	String notesOper = "";
	public String getNotesOper() {
		return notesOper;
	}
	public void setNotesOper(String notesOper) {
		this.notesOper = notesOper;
	}


	String WFHierarchyIDOper = "";
	public String getWFHierarchyIDOper() {
		return WFHierarchyIDOper;
	}
	public void setWFHierarchyIDOper(String wFHierarchyIDOper) {
		WFHierarchyIDOper = wFHierarchyIDOper;
	}


	String WFHierarchyNameOper = "";
	public String getWFHierarchyNameOper() {
		return WFHierarchyNameOper;
	}
	public void setWFHierarchyNameOper(String wFHierarchyNameOper) {
		WFHierarchyNameOper = wFHierarchyNameOper;
	}


	String WFHierarchyTypeOper = "";

	public String getWFHierarchyTypeOper() {
		return WFHierarchyTypeOper;
	}
	public void setWFHierarchyTypeOper(String wFHierarchyTypeOper) {
		WFHierarchyTypeOper = wFHierarchyTypeOper;
	}
	String WFdateTimeText = "";
	public String getWFdateTimeText() {
		return WFdateTimeText;
	}
	public void setWFdateTimeText(String wFdateTimeText) {
		WFdateTimeText = wFdateTimeText;
	}

	String WFActionByText = "";
	public String getWFActionByText() {
		return WFActionByText;
	}
	public void setWFActionByText(String wFActionByText) {
		WFActionByText = wFActionByText;
	}

	String roleText = "";


	public String getRoleText() {
		return roleText;
	}
	public void setRoleText(String roleText) {
		this.roleText = roleText;
	}


	String fromStageText = "";
	public String getFromStageText() {
		return fromStageText;
	}
	public void setFromStageText(String fromStageText) {
		this.fromStageText = fromStageText;
	}

	String toStageText = "";
	public String getToStageText() {
		return toStageText;
	}
	public void setToStageText(String toStageText) {
		this.toStageText = toStageText;
	}

	String fromStatusText = "";
	public String getFromStatusText() {
		return fromStatusText;
	}
	public void setFromStatusText(String fromStatusText) {
		this.fromStatusText = fromStatusText;
	}

	String memberStatusText = "";
	public String getMemberStatusText() {
		return memberStatusText;
	}
	public void setMemberStatusText(String memberStatusText) {
		this.memberStatusText = memberStatusText;
	}

	String notesText = "";
	public String getNotesText() {
		return notesText;
	}
	public void setNotesText(String notesText) {
		this.notesText = notesText;
	}

	String WFHierarchyIDText = "";
	public String getWFHierarchyIDText() {
		return WFHierarchyIDText;
	}
	public void setWFHierarchyIDText(String wFHierarchyIDText) {
		WFHierarchyIDText = wFHierarchyIDText;
	}

	String WFHierarchyNameText = "";
	public String getWFHierarchyNameText() {
		return WFHierarchyNameText;
	}
	public void setWFHierarchyNameText(String wFHierarchyNameText) {
		WFHierarchyNameText = wFHierarchyNameText;
	}
	String WFHierarchyTypeText = "";

	public String getWFHierarchyTypeText() {
		return WFHierarchyTypeText;
	}
	public void setWFHierarchyTypeText(String wFHierarchyTypeText) {
		WFHierarchyTypeText = wFHierarchyTypeText;
	}

	String WFdateTimeBetText = "";
	public String getWFdateTimeBetText() {
		return WFdateTimeBetText;
	}
	public void setWFdateTimeBetText(String wFdateTimeBetText) {
		WFdateTimeBetText = wFdateTimeBetText;
	}

	String WFActionByBetText = "";
	public String getWFActionByBetText() {
		return WFActionByBetText;
	}
	public void setWFActionByBetText(String wFActionByBetText) {
		WFActionByBetText = wFActionByBetText;
	}

	String roleBetText = "";

	public String getRoleBetText() {
		return roleBetText;
	}
	public void setRoleBetText(String roleBetText) {
		this.roleBetText = roleBetText;
	}


	String fromStageBetText = "";
	public String getFromStageBetText() {
		return fromStageBetText;
	}
	public void setFromStageBetText(String fromStageBetText) {
		this.fromStageBetText = fromStageBetText;
	}

	String toStageBetText = "";
	public String getToStageBetText() {
		return toStageBetText;
	}
	public void setToStageBetText(String toStageBetText) {
		this.toStageBetText = toStageBetText;
	}

	String fromStatusBetText = "";
	public String getFromStatusBetText() {
		return fromStatusBetText;
	}
	public void setFromStatusBetText(String fromStatusBetText) {
		this.fromStatusBetText = fromStatusBetText;
	}

	String memberStatusBetText = "";
	public String getMemberStatusBetText() {
		return memberStatusBetText;
	}
	public void setMemberStatusBetText(String memberStatusBetText) {
		this.memberStatusBetText = memberStatusBetText;
	}

	String notesBetText = "";
	public String getNotesBetText() {
		return notesBetText;
	}
	public void setNotesBetText(String notesBetText) {
		this.notesBetText = notesBetText;
	}


	String WFHierarchyIDBetText = "";
	public String getWFHierarchyIDBetText() {
		return WFHierarchyIDBetText;
	}
	public void setWFHierarchyIDBetText(String wFHierarchyIDBetText) {
		WFHierarchyIDBetText = wFHierarchyIDBetText;
	}

	String WFHierarchyNameBetText = "";
	public String getWFHierarchyNameBetText() {
		return WFHierarchyNameBetText;
	}
	public void setWFHierarchyNameBetText(String wFHierarchyNameBetText) {
		WFHierarchyNameBetText = wFHierarchyNameBetText;
	}

	String WFHierarchyTypeBetText = "";
	public String getWFHierarchyTypeBetText() {
		return WFHierarchyTypeBetText;
	}
	public void setWFHierarchyTypeBetText(String wFHierarchyTypeBetText) {
		WFHierarchyTypeBetText = wFHierarchyTypeBetText;
	}
	TreeMap tempStringTM = new TreeMap<>();
	{
		tempStringTM.put("--Select Value--","--Select Value--");
		tempStringTM.put("Contains", "Contains");
	}
	public TreeMap getTempStringTM() {
		return tempStringTM;
	}
	public void setTempStringTM(TreeMap tempStringTM) {
		this.tempStringTM = tempStringTM;
	}
	public void viewingWorkFlowRecords(String hierID) {
		try{
			Connection con=Globals.getDBConnection("DW_Connection");
			workflowAL = new ArrayList<>();
			String sql = "";
			sql = "select * from WORK_FLOW_ACTIVITY where HIERARCHY_ID = "+hierID+" order by DATE_TIME DESC";
			PreparedStatement pre = con.prepareStatement(sql);
			ResultSet rs = pre.executeQuery(sql);
			System.out.println("1===>"+sql);
			HierarchydataBean hb; 
			while(rs.next()){
				hb = new HierarchydataBean(rs.getTimestamp("DATE_TIME").toString(), rs.getString("ACTION_BY"), rs.getString("ROLE"), 
						rs.getString("FROM_STAGE"), rs.getString("TO_STAGE"), rs.getString("FROM_STATUS"), rs.getString("MEMBER_STATUS"),
						rs.getString("NOTES"), rs.getString("HIERARCHY_ID"), rs.getString("HIERARCHY_NAME"), rs.getString("HIERARCHY_TYPE"));
				workflowAL.add(hb);
			}
			stringoperTM.put("Starts With", "Starts With");
			stringoperTM.put("Ends With", "Ends With");
			stringoperTM.put("IN", "IN");
			stringoperTM.put("Contains", "Contains");
			stringoperTM.put("Wildcards", "Wildcards");
			stringoperTM.put("Between", "Between");
			stringoperTM.put("Not Between", "Not Between");



			intoperTM.put("IN", "IN");
			intoperTM.put("Greater", "Greater");
			intoperTM.put("Lesser", "Lesser");
			intoperTM.put("Between", "Between");

			WFdateTimeOper = "IN";
			WFActionByOper = "Starts With";
			roleOper = "Starts With";
			fromStageOper = "Starts With";
			toStageOper = "Starts With";
			fromStatusOper ="Starts With";
			notesOper = "Starts With";
			memberStatusOper = "Starts With";

			HeaderBean hb1 = new HeaderBean();
			hb1.processMenu("WorkflowTable.xhtml");

		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}


	public void viewingAuditRecords(String hierID) {
		try{
			msg1="";
			Connection con=Globals.getDBConnection("DW_Connection");
			auditRecordAL = new ArrayList<>();
			String sql = "";
			sql = "select * from HIERARCHY_AUDIT where HIERARCHY_ID = "+hierID+" order by DATE_TIME DESC";
			PreparedStatement pre = con.prepareStatement(sql);
			ResultSet rs = pre.executeQuery(sql);
			HierarchydataBean hdb;
			while(rs.next()){
				String beforeValue=rs.getString("BEFORE_NODE_TEXT");
				String afterValue=rs.getString("AFTER_NODE_TEXT");
				beforeValue = splitingValuefromNode(beforeValue);
				afterValue = splitingValuefromNode(afterValue);
				//				System.out.println("1===>"+rs.getString("LOGIN_ID"));
				//				System.out.println("2===>"+rs.getTimestamp("DATE_TIME").toString());
				//				System.out.println("3===>"+String.valueOf(rs.getInt("PARENT_NODE_ID")));
				//				System.out.println("4===>"+rs.getString("ACTION"));
				//				System.out.println("5===>"+beforeValue);
				//				System.out.println("6===>"+afterValue);
				//				System.out.println("7===>"+rs.getString("BEFORE_PARENT_NODE_MOVE"));
				//				System.out.println("8===>"+rs.getString("BEFORE_POSITION_MOVE"));
				//				System.out.println("9===>"+rs.getString("AFTER_PARENT_NODE_MOVE"));
				//				System.out.println("10===>"+rs.getString("AFTER_POSITION_MOVE"));
				hdb=new HierarchydataBean(rs.getString("LOGIN_ID"), rs.getTimestamp("DATE_TIME").toString(), rs.getString("ACTION"), String.valueOf(rs.getInt("PARENT_NODE_ID")),
						beforeValue, afterValue, rs.getString("BEFORE_PARENT_NODE_MOVE"), rs.getString("BEFORE_POSITION_MOVE"),
						rs.getString("AFTER_PARENT_NODE_MOVE"), rs.getString("AFTER_POSITION_MOVE"));
				auditRecordAL.add(hdb);
			}
			stringoperTM.put("Starts With", "Starts With");
			stringoperTM.put("Ends With", "Ends With");
			stringoperTM.put("IN", "IN");
			stringoperTM.put("Contains", "Contains");
			stringoperTM.put("Wildcards", "Wildcards");
			stringoperTM.put("Between", "Between");
			stringoperTM.put("Not Between", "Not Between");

			intoperTM.put("IN", "IN");
			intoperTM.put("Greater", "Greater");
			intoperTM.put("Lesser", "Lesser");
			intoperTM.put("Between", "Between");

			loginOper = "Starts With";
			dateTimeOper = "IN";
			actionOper = "Starts With";
			parentOper = "IN";
			beforeOper = "Contains";
			afterOper = "Contains";
			oldOper = "Starts With";
			oldPostionOper = "Starts With";
			newOper = "Starts With";
			newPostionOper = "Starts With";

			HeaderBean hb = new HeaderBean();
			hb.processMenu("AuditTable.xhtml");
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public String splitingValuefromNode(String value) {
		String nodeValue = "";
		try{
			if(value!=null){
				String val1[]=value.split("#\\$#");
				for(int i=0;i<val1.length;i++){
					//				System.out.println("val==>"+val1[i]);
					if(val1[i].contains("value")){
						//					System.out.println("val==>1"+val1[i].split("=")[1]);
						nodeValue = val1[i].split("=")[1];
					}
				}
			}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return nodeValue;
	}

	public String auditEnable="";
	public String getAuditEnable() {
		return auditEnable;
	}
	public void setAuditEnable(String auditEnable) {
		this.auditEnable = auditEnable;
	}

	public String auditDisable="";

	public String getAuditDisable() {
		return auditDisable;
	}
	public void setAuditDisable(String auditDisable) {
		this.auditDisable = auditDisable;
	}
	public void toggle(String auditEnable, String auditDisable) {
		try{
			msg1="";
			String xmlDir="";
			String xmlLevelFileName="";
			PropUtil prop=new PropUtil();
			xmlDir = prop.getProperty("HIERARCHY_XML_DIR");
			xmlLevelFileName = prop.getProperty("HIERARCHY_XML_FILE");
			this.auditDisable = auditDisable;
			this.auditEnable = auditEnable;
			Document doc = Globals.openXMLFile(xmlDir, xmlLevelFileName);
			Node nd = Globals.getNodeByAttrVal(doc, "Hierarchy_Level", "Hierarchy_ID", hierarchy_ID);
			if(nd!=null){
				Element ndEle = (Element)nd;
				ndEle.setAttribute("Enable_Toggle", auditEnable);
				ndEle.setAttribute("Disable_Toggle", auditDisable);
				Globals.writeXMLFile(doc, xmlDir, xmlLevelFileName);
			}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}


	public void getusersfromLDAP() throws Exception   //Getting User List & Mail ID From LDAP SERVER : Code Change Bharath 20-MAR-2014
	{
		System.out.println("Entering: " + new Exception().getStackTrace()[0].getClassName() + "."
				+ new Exception().getStackTrace()[0].getMethodName());

		PropUtil prop=new PropUtil();

		String HIERARCHY_CONFIG_FILE=null;

		msg4AddUsr="";
		//		int ldapPort = LDAPConnection.DEFAULT_PORT;
		//		int searchScope = LDAPConnection.SCOPE_SUB;
		//		int ldapVersion = LDAPConnection.LDAP_V3;
		//		boolean attributeOnly = false;
		//		String attrs[] = {LDAPConnection.ALL_USER_ATTRS}; 
		//		String ldapHost = "SERVER";
		//		String loginDN = "cygnussoftwares\\bharath";
		//		String password = "br$2013";
		//		String searchBase = "CN=Users,DC=cygnussoftwares,DC=com";
		//		String searchFilter = "(objectCategory=person)";	

		System.out.println("%%%%%%%%%%%%%%%listfirstNameTXT%%%%%%%%%%%%%%%%%%%% :"+listfirstNameTXT);


		int ldapPort;
		int searchScope;
		int ldapVersion;
		boolean attributeOnly;
		//		String attrs[]; 
		String ldapHost = hostName;   // code change Menaka 20MAR2014
		String loginDN ;
		String password = this.password;


		String searchBase ="";
		String searchFilter ="";

		LDAPConnection lc = new LDAPConnection();


		//		System.out.println("searchScope============>>>"+searchScope);


		try {

			msg4AddUsr="";   // code change Menaka 29MAR2014
			titleMsg4AddUsr = "";

			if(hostName==null||hostName.equals("")){
				msg4AddUsr="Enter the Domain Name.";
				color4AddUserMsg = "red";
				return;
			}
			if(loginID==null||loginID.equals("")){
				msg4AddUsr="Enter the User ID.";
				color4AddUserMsg = "red";
				return;
			}
			if(this.password==null||this.password.equals("")){
				msg4AddUsr="Enter the Password.";
				color4AddUserMsg = "red";
				return;
			}

			// code change Menaka 21MAR2014
			HIERARCHY_CONFIG_FILE=prop.getProperty("HIERARCHY_CONFIG_FILE");
			NodeList nodeList=null;

			nodeList=Globals.getNodeList(HIERARCHY_XML_DIR, HIERARCHY_CONFIG_FILE, "Ldap_Port");
			ldapPort=Integer.parseInt(nodeList.item(0).getTextContent());			

			nodeList=Globals.getNodeList(HIERARCHY_XML_DIR, HIERARCHY_CONFIG_FILE, "Search_Scope");
			searchScope=Integer.parseInt(nodeList.item(0).getTextContent());		

			nodeList=Globals.getNodeList(HIERARCHY_XML_DIR, HIERARCHY_CONFIG_FILE, "Ldap_Version");
			ldapVersion=Integer.parseInt(nodeList.item(0).getTextContent());

			nodeList=Globals.getNodeList(HIERARCHY_XML_DIR, HIERARCHY_CONFIG_FILE, "Attribute_Only");
			attributeOnly=Boolean.parseBoolean(String.valueOf(nodeList.item(0).getTextContent()));

			nodeList=Globals.getNodeList(HIERARCHY_XML_DIR, HIERARCHY_CONFIG_FILE, "Attributes");
			String attrs[]={String.valueOf(nodeList.item(0).getTextContent())};

			nodeList=Globals.getNodeList(HIERARCHY_XML_DIR, HIERARCHY_CONFIG_FILE, "Search_Base");
			searchBase=String.valueOf(nodeList.item(0).getTextContent());

			nodeList=Globals.getNodeList(HIERARCHY_XML_DIR, HIERARCHY_CONFIG_FILE, "Search_Filter");
			searchFilter=String.valueOf(nodeList.item(0).getTextContent());


			nodeList=Globals.getNodeList(HIERARCHY_XML_DIR, HIERARCHY_CONFIG_FILE, "Domain_Name");
			String domainName=String.valueOf(nodeList.item(0).getTextContent());
			loginDN=domainName.concat(loginID);  // code change Menaka 26MAR2014


			// connect to the server
			lc.connect(ldapHost, ldapPort); // 3268
			System.out.println("LDAP connection Success");
			// bind to the server
			lc.bind(ldapVersion, loginDN, password.getBytes("UTF8"));
			System.out.println("LDAP bind Success");
			LDAPSearchResults searchResults = lc.search(searchBase,searchScope,searchFilter,attrs,attributeOnly); 
			HeirarchyDataBean hdb;
			allUsersAL=new ArrayList<>();
			copyAllUsersAL= new ArrayList<>(); 
			// print out all the objects
			while (searchResults.hasMore()) {
				LDAPEntry nextEntry = null;
				try {
					nextEntry = searchResults.next();
				}
				catch (LDAPException e) {
					System.out.println("Error: " + e.toString());
					e.printStackTrace(); // Exception is thrown, go for next  // entry
					continue;
				}

				String userName="",emailID="";

				System.out.println("userName==========>>>" + nextEntry.getAttribute("samAccountName"));

				if(nextEntry.getAttribute("samAccountName")!=null){

					//				System.out.println("userName==========>>>" + nextEntry.getAttribute("samAccountName").getStringValue()); 
					userName=nextEntry.getAttribute("samAccountName").getStringValue();
					if(nextEntry.getAttribute("userPrincipalName")!=null){
						//					System.out.println("emailID===========>>> " + nextEntry.getAttribute("userPrincipalName").getStringValue()); //.getAttribute("samAccountName")); //.getDN());
						emailID=nextEntry.getAttribute("userPrincipalName").getStringValue();
					}
				}

				int access = 0;
				hdb = new HeirarchyDataBean(userName,emailID,"","","",access,"",false,"","",false,false,false,"","","","","","","","","","","","",""); 
				this.allUsersAL.add(hdb);
				this.copyAllUsersAL.add(hdb);


			}
			// disconnect with the server
			lc.disconnect();
			System.out.println("****Server Disconnected****");
		}
		catch (LDAPException e) {
			e.printStackTrace();
			System.out.println("Error: " + e.toString());
			String error=	Globals.getErrorString(e);
			if(error.length()>70){ //code  change Jayaramu 14APR14
				titleMsg4AddUsr = error;
				error = error.substring(0,70).concat("...");
			}

			msg4AddUsr=error;   // code change Menaka 29MAR2014 
			color4AddUserMsg = "red";
		}
		catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			System.out.println("Error: " + e.toString());
			String error=	Globals.getErrorString(e);
			if(error.length()>70){//code  change Jayaramu 14APR14
				titleMsg4AddUsr = error;
				error = error.substring(0,70).concat("...");
			}
			msg4AddUsr=error;   // code change Menaka 29MAR2014
			color4AddUserMsg = "red";
		}
		System.out.println("Exiting: " + new Exception().getStackTrace()[0].getClassName() + "."
				+ new Exception().getStackTrace()[0].getMethodName());
	}
	//start code change Jayaramu 27MAR14
	public void loadReferenceHierarchyFromHIRXML(){
		try{

			PropUtil prop = new PropUtil();
			String dir = prop.getProperty("HIERARCHY_XML_DIR");
			String hierXmlFile = prop.getProperty("HIERARCHY_XML_FILE");
			Document HIRXmlDoc = Globals.openXMLFile(dir, hierXmlFile);
			NodeList hierLevel = HIRXmlDoc.getElementsByTagName("Hierarchy_Level");
			referenceHierarchyAL = new ArrayList<>();
			String hierarchyName = "";
			flag4RenderedHyrType =true;
			for(int i=0;i<hierLevel.getLength();i++){

				Node hirLevelNode = hierLevel.item(i);
				if(hirLevelNode.getNodeType() == Node.ELEMENT_NODE){

					Node rIHryType = hirLevelNode.getAttributes().getNamedItem("RI_Hierarchy_Type");

					if(rIHryType != null){
						String hierType = rIHryType.getNodeValue();
						if(hierType.equals("Reference")){

							hierarchyName = hirLevelNode.getAttributes().getNamedItem("Hierarchy_Name").getNodeValue();

							String hierarchyID=hirLevelNode.getAttributes().getNamedItem("Hierarchy_ID").getNodeValue();
							Node workFlowN = Globals.getNodeByAttrVal(HIRXmlDoc, "Workflow", "Hierarchy_ID", hierarchyID);
							if(workFlowN!=null){
								Hashtable workFlowHT=Globals.getAttributeNameandValHT(workFlowN);

								String iscompleted=(String)workFlowHT.get("Current_Stage_No");
								if(iscompleted.equalsIgnoreCase("Completed") || iscompleted.equalsIgnoreCase("Cancel") || iscompleted.equalsIgnoreCase("Rules Failed")){

									referenceHierarchyAL.add(hierarchyName);						
									flag4RenderedHyrType = false;
								}

							}





						}

					}
				}

			}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}
	//End code change Jayaramu 27MAR14
	//start code change Jayaramu 27MAR14
	public Node getReferenceHierarchyNodeFromHIRLevelXml(Document HIRXmlDoc){

		Node hirLevelNode = null;
		try{

			if(selectedReferenceHierarchy.equals("selectValue")){
				System.out.println("Please select Reference Hierarchy to proceed further...");
				message4RIconstraints = "The current Hierarchy is a "+status+" Hierarchy. You need to choose a Reference Hierarchy to proceed further.";
				return null;
			}
			message4RIconstraints = "";
			PropUtil prop = new PropUtil();
			String dir = prop.getProperty("HIERARCHY_XML_DIR");
			String hierXmlFile = prop.getProperty("HIERARCHY_XML_FILE");
			NodeList hierLevel = HIRXmlDoc.getElementsByTagName("Hierarchy_Level");
			String hierarchyName = "";


			for(int i=0;i<hierLevel.getLength();i++){

				hirLevelNode = hierLevel.item(i);

				if(hirLevelNode.getNodeType() == Node.ELEMENT_NODE){
					hierarchyName = hirLevelNode.getAttributes().getNamedItem("Hierarchy_Name").getNodeValue();
					if(selectedReferenceHierarchy.equals(hierarchyName)){
						//					hierarchyID = hirLevelNode.getAttributes().getNamedItem("Hierarchy_ID").getNodeValue();
						break;
					}
				}
			}



		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return hirLevelNode;
	}//End code change Jayaramu 27mar14

	//start code change Jayaramu 27MAR14
	public void getRIContraintsRulesFromRICXml(){
		try{
			PropUtil prop = new PropUtil();
			String dir = prop.getProperty("HIERARCHY_XML_DIR");
			String RIConstraintsXmlFile = prop.getProperty("RI_CONSTRAINTS_XML");
			String hrLevelXmlFile = prop.getProperty("HIERARCHY_XML_FILE");
			Document RIConstraintsXmlDoc = Globals.openXMLFile(dir, RIConstraintsXmlFile);
			Document HierLevelXmlDoc = Globals.openXMLFile(dir, hrLevelXmlFile);
			NodeList RIConstraints = RIConstraintsXmlDoc.getElementsByTagName("Rule_Description");
			Node rootNode = RIConstraints.item(0);
			NodeList RIRulesNode  = rootNode.getChildNodes();
			Node rules = null;
			HeirarchyDataBean hdb;
			rIConstraintsRulesAL3 = new ArrayList<>();
			rIConstraintsRulesAL1 = new ArrayList<>();
			rIConstraintsRulesAL2 = new ArrayList<>();
			selectedRIConsRule = new ArrayList<>();
			flag4OpenRIConsValiPopup = false;
			isAdminConfigureRIConstraints = false;
			int rul = 1;
			Node hierlevelNodeDH=Globals.getNodeByAttrVal(HierLevelXmlDoc, "Hierarchy_Level", "Hierarchy_ID", hierarchy_ID);   
			if(hierlevelNodeDH == null){
				message4RIconstraints = "Please create hierarchy to proceed further.";
				return;
			}
			message4RIconstraints = "";
			//	    Node rulenodeDH=Globals.getNodeByAttrValUnderParent(HierLevelXmlDoc, hierlevelNodeDH, "Hierarchy_ID", hierarchy_ID);   
			Node rulenodeDH=Globals.getNodeByAttrVal(HierLevelXmlDoc, "RI-Rules", "Hierarchy_ID", hierarchy_ID);  
			Hashtable hierlevelAttrDHHT = new Hashtable<>();
			Hashtable ruleHT = new Hashtable<>();;
			if(rulenodeDH != null){
				hierlevelAttrDHHT=Globals.getAttributeNameandValHT(rulenodeDH);

				ruleHT =Globals.getAttributeNameandValHT(rulenodeDH);
			}   
			Node riHierType = hierlevelNodeDH.getAttributes().getNamedItem("RI_Hierarchy_Type");
			Node riRefHierName = hierlevelNodeDH.getAttributes().getNamedItem("RI_Reference_Hierarchy_Name");
			if(riHierType!=null){
				selectedHierarchyType =  riHierType.getNodeValue();
				selectedHierarchyTypeCompare = selectedHierarchyType;
				isAdminConfigureRIConstraints = true;

			}if(riRefHierName != null){
				selectedReferenceHierarchy = riRefHierName.getNodeValue();
				selectedReferenceHierarchyCompare = selectedReferenceHierarchy;
			}
			for(int i=0;i<RIRulesNode.getLength();i++){
				rules = RIRulesNode.item(i);
				if(rules.getNodeType() == Node.ELEMENT_NODE){
					String rICRuleNode = rules.getNodeName();
					String rICRule = rules.getAttributes().getNamedItem("Rule").getNodeValue();
					String ruleNo = "Rule_"+rul;
					hdb = new HeirarchyDataBean(rICRuleNode,rICRule, ruleNo);
					rIConstraintsRulesAL3.add(hdb);

					if(rulenodeDH != null){
						Enumeration en=ruleHT.keys();
						while(en.hasMoreElements()){

							String key=(String)en.nextElement();
							String value=(String)ruleHT.get(key);

							if(rICRuleNode.equals(key)){
								if(value.equals("true")){

									selectedRIConsRule.add(hdb);
								}

							}
						}
					}

					if(i == (RIRulesNode.getLength()-2)){
						rIConstraintsRulesAL1.add(hdb);
					}else{

						rIConstraintsRulesAL2.add(hdb);
					}
					rul++;
				}

			}
			selectedRIConsRuleCompare = selectedRIConsRule;
			getAL4RIConstraints("ForEdit");

		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}	
	}
	public void WriteSelectedRulesToHry(){
		try{
			PropUtil prop = new PropUtil();
			String dir = prop.getProperty("HIERARCHY_XML_DIR");
			String hierXmlFile = prop.getProperty("HIERARCHY_XML_FILE");
			Document HIRXmlDoc = Globals.openXMLFile(dir, hierXmlFile);
			Node HirNode = Globals.getNodeByAttrVal(HIRXmlDoc, "Hierarchy_Level", "Hierarchy_ID", hierarchy_ID);
			HeirarchyDataBean hdb;
			flag4OpenRIConsValiPopup = false;
			if(HirNode == null){

				System.out.println("Create Hierarchy to proceed further.");
				message4RIconstraints = "Please create hierarchy to proceed further.";
				return;
			}
			Node rICRuleNode = Globals.getNodeByAttrVal(HIRXmlDoc, "RI-Rules", "Hierarchy_ID", hierarchy_ID);

			if(rICRuleNode != null){
				rICRuleNode.getParentNode().removeChild(rICRuleNode);
			}
			String hirID = "";


			//		if(selectedHierarchyType==null||selectedHierarchyType.equals("")){  // code change Menaka 31MAR2014
			//			selectedHierarchyType="None";
			//		}   //comment by jayaramu 04APR14

			this.status=selectedHierarchyType; // code change Menaka 04APR2014
			if(status.equalsIgnoreCase("Independent")){
				riMessage = "";
			}else if(status.equalsIgnoreCase("Reference")){
				riMessage = "This Reference Hierarchy has Dependant Hierarchies. Do you want to Validate them now?";
			}
			if(selectedHierarchyType.equals("Reporting") || selectedHierarchyType.equals("Dependent")){
				Node hierNode = getReferenceHierarchyNodeFromHIRLevelXml(HIRXmlDoc);
				if(hierNode == null){
					return;
				}

				riMessage = "This is a "+selectedHierarchyType+" Hierarchy. As you have changed the Referential Integrity constraints configuration, you need to ensure the Hierarchy still confirms to the updated constraints list. Do you want to validate the Hierarchy now?";


				hirID = hierNode.getAttributes().getNamedItem("Hierarchy_ID").getNodeValue();
				String refHierarchyID = hierarchy_ID;
				Node nn = hierNode.getAttributes().getNamedItem("RI_Dependent_Hierarchies");
				System.out.println("nn===>>."+nn);
				Element em = (Element)hierNode;
				if(nn == null){

					em.setAttribute("RI_Dependent_Hierarchies", refHierarchyID);
				}else {

					String dependntAttr = nn.getNodeValue();


					if(dependntAttr.contains(",")){

						String[] attrvalues =  dependntAttr.split(",");
						for(int j=0;j<attrvalues.length;j++){

							if(!refHierarchyID.contains(attrvalues[j])){

								refHierarchyID = refHierarchyID.concat(",")+attrvalues[j];
							}
						}

					}else if(dependntAttr.equals("")){


					}else{
						if(!refHierarchyID.contains(dependntAttr)){
							refHierarchyID = refHierarchyID.concat(",")+dependntAttr;
						}}
				}
				em.setAttribute("RI_Dependent_Hierarchies", refHierarchyID);
			}

			if(selectedHierarchyType.equals("Independent") || selectedHierarchyType.equals("Reference")){
				selectedReferenceHierarchy = "";
			}

			Element hirarchyNodeElm = (Element)HirNode;
			hirarchyNodeElm.setAttribute("RI_Reference_Hierarchy_ID", hirID);
			hirarchyNodeElm.setAttribute("RI_Hierarchy_Type", selectedHierarchyType);
			hirarchyNodeElm.setAttribute("RI_Reference_Hierarchy_Name", selectedReferenceHierarchy);
			String dependentHier = hirarchyNodeElm.getAttribute("RI_Dependent_Hierarchies");

			if(dependentHier == null || !selectedHierarchyType.equals("Reference")){
				hirarchyNodeElm.setAttribute("RI_Dependent_Hierarchies", "");
			}

			hirarchyNodeElm.setAttribute("RI_Validated", "false"); // code change pandian 21APR2014 add ri validation False


			if(!selectedHierarchyType.equals("Independent")){
				Node hIRruleNode = HIRXmlDoc.createElement("RI-Rules");
				Element rulNode = (Element)hIRruleNode;
				HirNode.appendChild(rulNode);
				rulNode.setAttribute("Hierarchy_ID", hierarchy_ID);
				String[] noOfrules = new String[selectedRIConsRule.size()];

				for(int i=0;i<rIConstraintsRulesAL3.size();i++){
					hdb = (HeirarchyDataBean)rIConstraintsRulesAL3.get(i);
					rulNode.setAttribute(hdb.getrICRuleNode(), "false");

				}
				for(int j=0;j<selectedRIConsRule.size();j++){

					rulNode.setAttribute(selectedRIConsRule.get(j).getrICRuleNode(), "true");
					noOfrules[j] = selectedRIConsRule.get(j).getRuleNo();
				}


				if(isAdminConfigureRIConstraints){
					if(!selectedHierarchyTypeCompare.equals(selectedHierarchyType)){
						flag4OpenRIConsValiPopup = true;
						selectedHierarchyTypeCompare = selectedHierarchyType;

					}else if(!selectedRIConsRuleCompare.equals(selectedRIConsRule)){
						flag4OpenRIConsValiPopup = true;
						selectedRIConsRuleCompare = selectedRIConsRule;
					}else if(!selectedReferenceHierarchyCompare.equals(selectedReferenceHierarchy)){
						flag4OpenRIConsValiPopup = true;
						selectedReferenceHierarchyCompare = selectedReferenceHierarchy;
					}
				}

				if(status.equalsIgnoreCase("Reference")){
					if(!Globals.getAttrVal4AttrName(HirNode, "RI_Dependent_Hierarchies").equals("")){
						riMessage = "This Reference Hierarchy has Dependant Hierarchies. Do you want to Validate them now?";
						flag4OpenRIConsValiPopup =true;
					}else{
						flag4OpenRIConsValiPopup = false;
					}
					//		riMessage = "This Reference Hierarchy has Dependant Hierarchies. Do you want to Validate them now?";
				}


			}
			Globals.writeXMLFile(HIRXmlDoc, dir, hierXmlFile);
			message4RIconstraints = "Changes saved successfully.";
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}}
	public void getAL4RIConstraints(String flag4Edit){
		try{
			PropUtil prop = new PropUtil();
			String dir = prop.getProperty("HIERARCHY_XML_DIR");
			String hierXmlFile = prop.getProperty("HIERARCHY_XML_FILE");
			Document HIRXmlDoc = Globals.openXMLFile(dir, hierXmlFile);
			Node hierlevelNode=Globals.getNodeByAttrVal(HIRXmlDoc, "Hierarchy_Level", "Hierarchy_ID", hierarchy_ID);   

			if(hierlevelNode == null){
				message4RIconstraints = "Please create Hierarchy to proceed further.";
				return;
			}
			message4RIconstraints = "";
			rIConstraintsRulesAL = new ArrayList<>();

			if(!flag4Edit.equals("ForEdit")){
				selectedRIConsRule = new ArrayList<>();
			}
			HeirarchyDataBean hdb;
			if(!Globals.getAttrVal4AttrName(hierlevelNode, "RI_Hierarchy_Type").equalsIgnoreCase("Independent")){
				if(!selectedHierarchyType.equals(Globals.getAttrVal4AttrName(hierlevelNode, "RI_Hierarchy_Type"))){
					if(Globals.getAttrVal4AttrName(hierlevelNode, "RI_Hierarchy_Type").equalsIgnoreCase("Reference")){
						//				rIConstraintsRulesAL = rIConstraintsRulesAL1;
						//				selectedHierarchyType = "Reference";
						//				message4RIconstraints = "Reference hierarchy cannot be change to another hierarchy Type";
						//				return;
						if(Globals.getAttrVal4AttrName(hierlevelNode, "RI_Dependent_Hierarchies").equals("")){

						}else{
							rIConstraintsRulesAL = rIConstraintsRulesAL1;
							selectedHierarchyType = "Reference";
							String dependentHierIds=Globals.getAttrVal4AttrName(hierlevelNode, "RI_Dependent_Hierarchies");
							dependentHierIds=dependentHierIds.replace(",",", ");
							message4RIconstraints = "Reference Hierarchy cannot be changed to another Hierarchy Type as this Hierarchy is referenced by: "+dependentHierIds+ "";
							return;
						}
					}else if(Globals.getAttrVal4AttrName(hierlevelNode, "RI_Hierarchy_Type").equalsIgnoreCase("Dependent")){
						rIConstraintsRulesAL = rIConstraintsRulesAL2;
						selectedHierarchyType = "Dependent";
						message4RIconstraints = "Dependent hierarchy cannot be change to another hierarchy Type";
						return;
					}else if(Globals.getAttrVal4AttrName(hierlevelNode, "RI_Hierarchy_Type").equalsIgnoreCase("Reporting")){
						rIConstraintsRulesAL = rIConstraintsRulesAL2;
						selectedHierarchyType = "Reporting";
						message4RIconstraints = "Reporting hierarchy cannot be change to another hierarchy Type";
						return;
					}
				}
			}
			System.out.println("selectedHierarchyType====>>"+selectedHierarchyType);
			if(selectedHierarchyType==null){
				rIConstraintsRulesAL = rIConstraintsRulesAL2;
			}else{
				if(selectedHierarchyType.equals("Reference")){
					rIConstraintsRulesAL = rIConstraintsRulesAL1;
				}else{
					rIConstraintsRulesAL = rIConstraintsRulesAL2;
				}
			}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	int rulesSize = 0;
	public void clearMsg(){
		this.message4RIconstraints = "";
		this.msg4AddUsr=""; // code change Menaka 29MAR2014
		try{
			PropUtil prop = new PropUtil();
			String dir = prop.getProperty("HIERARCHY_XML_DIR");
			String hrLevelXmlFile = prop.getProperty("HIERARCHY_XML_FILE");
			Document doc = Globals.openXMLFile(dir, hrLevelXmlFile);
			Node rulenodeDH=Globals.getNodeByAttrVal(doc, "RI-Rules", "Hierarchy_ID", hierarchy_ID);
			Hashtable ruleHT = new Hashtable<>();

			if(rulenodeDH!=null){
				ruleHT=Globals.getAttributeNameandValHT(rulenodeDH);
				ArrayList l=new ArrayList();
				Enumeration en=ruleHT.keys();
				while(en.hasMoreElements()){

					String key=(String)en.nextElement();
					if(!key.equals("Hierarchy_ID")){
						//					System.out.println("--->"+key);
						String value=(String)ruleHT.get(key);
						if(value.equals("true")){
							l.add(key);
						}


					}

				}
				rulesSize = l.size();
			}

		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		//		System.out.println("msg4AddUsr=========>>>"+msg4AddUsr);
	}
	String loginOper = "";
	public String getLoginOper() {
		return loginOper;
	}
	public void setLoginOper(String loginOper) {
		this.loginOper = loginOper;
	}

	String loginText = "";
	public String getLoginText() {
		return loginText;
	}
	public void setLoginText(String loginText) {
		this.loginText = loginText;
	}

	String dateTimeOper = "";
	public String getDateTimeOper() {
		return dateTimeOper;
	}
	public void setDateTimeOper(String dateTimeOper) {
		this.dateTimeOper = dateTimeOper;
	}

	String dateTimeText = "";
	public String getDateTimeText() {
		return dateTimeText;
	}
	public void setDateTimeText(String dateTimeText) {
		this.dateTimeText = dateTimeText;
	}

	String actionOper = "";
	public String getActionOper() {
		return actionOper;
	}
	public void setActionOper(String actionOper) {
		this.actionOper = actionOper;
	}

	String actionText = "";
	public String getActionText() {
		return actionText;
	}
	public void setActionText(String actionText) {
		this.actionText = actionText;
	}

	String parentOper = "";
	public String getParentOper() {
		return parentOper;
	}
	public void setParentOper(String parentOper) {
		this.parentOper = parentOper;
	}

	String parentText = "";
	public String getParentText() {
		return parentText;
	}
	public void setParentText(String parentText) {
		this.parentText = parentText;
	}

	String beforeOper = "";
	public String getBeforeOper() {
		return beforeOper;
	}
	public void setBeforeOper(String beforeOper) {
		this.beforeOper = beforeOper;
	}

	String beforeText = "";
	public String getBeforeText() {
		return beforeText;
	}
	public void setBeforeText(String beforeText) {
		this.beforeText = beforeText;
	}

	String afterOper = "";
	public String getAfterOper() {
		return afterOper;
	}
	public void setAfterOper(String afterOper) {
		this.afterOper = afterOper;
	}

	String afterText = "";
	public String getAfterText() {
		return afterText;
	}
	public void setAfterText(String afterText) {
		this.afterText = afterText;
	}

	String oldOper = "";
	public String getOldOper() {
		return oldOper;
	}
	public void setOldOper(String oldOper) {
		this.oldOper = oldOper;
	}

	String oldText = "";
	public String getOldText() {
		return oldText;
	}
	public void setOldText(String oldText) {
		this.oldText = oldText;
	}

	String oldPostionOper = "";
	public String getOldPostionOper() {
		return oldPostionOper;
	}
	public void setOldPostionOper(String oldPostionOper) {
		this.oldPostionOper = oldPostionOper;
	}

	String oldPostionText = "";
	public String getOldPostionText() {
		return oldPostionText;
	}
	public void setOldPostionText(String oldPostionText) {
		this.oldPostionText = oldPostionText;
	}

	String newOper = "";
	public String getNewOper() {
		return newOper;
	}
	public void setNewOper(String newOper) {
		this.newOper = newOper;
	}

	String newText = "";
	public String getNewText() {
		return newText;
	}
	public void setNewText(String newText) {
		this.newText = newText;
	}

	String newPostionOper = "";
	public String getNewPostionOper() {
		return newPostionOper;
	}
	public void setNewPostionOper(String newPostionOper) {
		this.newPostionOper = newPostionOper;
	}

	String newPostionText = "";

	public String getNewPostionText() {
		return newPostionText;
	}
	public void setNewPostionText(String newPostionText) {
		this.newPostionText = newPostionText;
	}

	String loginBetText = "";
	public String getLoginBetText() {
		return loginBetText;
	}
	public void setLoginBetText(String loginBetText) {
		this.loginBetText = loginBetText;
	}

	String dateTimeBetText = "";
	public String getDateTimeBetText() {
		return dateTimeBetText;
	}
	public void setDateTimeBetText(String dateTimeBetText) {
		this.dateTimeBetText = dateTimeBetText;
	}

	String actionBetText = "";
	public String getActionBetText() {
		return actionBetText;
	}
	public void setActionBetText(String actionBetText) {
		this.actionBetText = actionBetText;
	}

	String parentBetText = "";
	public String getParentBetText() {
		return parentBetText;
	}
	public void setParentBetText(String parentBetText) {
		this.parentBetText = parentBetText;
	}

	String beforeBetText = "";
	public String getBeforeBetText() {
		return beforeBetText;
	}
	public void setBeforeBetText(String beforeBetText) {
		this.beforeBetText = beforeBetText;
	}

	String afterBetText = "";
	public String getAfterBetText() {
		return afterBetText;
	}
	public void setAfterBetText(String afterBetText) {
		this.afterBetText = afterBetText;
	}

	String oldBetText = "";
	public String getOldBetText() {
		return oldBetText;
	}
	public void setOldBetText(String oldBetText) {
		this.oldBetText = oldBetText;
	}

	String oldPostionBetText = "";
	public String getOldPostionBetText() {
		return oldPostionBetText;
	}
	public void setOldPostionBetText(String oldPostionBetText) {
		this.oldPostionBetText = oldPostionBetText;
	}

	String newBetText = "";
	public String getNewBetText() {
		return newBetText;
	}
	public void setNewBetText(String newBetText) {
		this.newBetText = newBetText;
	}

	String newPostionBetText = "";

	public String getNewPostionBetText() {
		return newPostionBetText;
	}
	public void setNewPostionBetText(String newPostionBetText) {
		this.newPostionBetText = newPostionBetText;
	}

	public void filteringWorkflowTable(String hierID) {

		try{
			genarateFactsBean gfb = new genarateFactsBean("","","");
			Connection con=Globals.getDBConnection("DW_Connection");
			String sql = "";
			Hashtable ht = new Hashtable<>();
			Hashtable WFdateTimeht = new Hashtable<>();
			Hashtable WFActionByht = new Hashtable<>();
			Hashtable Roleht = new Hashtable<>();
			Hashtable fromStageht = new Hashtable<>();
			Hashtable toStageht = new Hashtable<>();
			Hashtable fromStatusht = new Hashtable<>();
			Hashtable memberStatusht = new Hashtable<>();
			Hashtable notesht = new Hashtable<>();

			Hashtable typeHT=new Hashtable<>();
			String type = "";

			typeHT = new Hashtable<>();
			typeHT = gfb.getDatatypefromDBtable("WORK_FLOW_ACTIVITY.DATE_TIME", con, "", "", "");
			type = gfb.preferredDataType4SQLDataType(typeHT);
			WFdateTimeht.put("Operator", WFdateTimeOper);
			WFdateTimeht.put("Text", WFdateTimeText);
			WFdateTimeht.put("BetText", WFdateTimeBetText);
			WFdateTimeht.put("Type", type);

			typeHT = new Hashtable<>();
			typeHT = gfb.getDatatypefromDBtable("WORK_FLOW_ACTIVITY.ACTION_BY", con, "", "", "");
			type = gfb.preferredDataType4SQLDataType(typeHT);
			WFActionByht.put("Operator", WFActionByOper);
			WFActionByht.put("Text", WFActionByText);
			WFActionByht.put("BetText", WFActionByBetText);
			WFActionByht.put("Type", type);

			typeHT = new Hashtable<>();
			typeHT = gfb.getDatatypefromDBtable("WORK_FLOW_ACTIVITY.ROLE", con, "", "", "");
			type = gfb.preferredDataType4SQLDataType(typeHT);
			Roleht.put("Operator", roleOper);
			Roleht.put("Text", roleText);
			Roleht.put("Type", type);

			typeHT = new Hashtable<>();
			typeHT = gfb.getDatatypefromDBtable("WORK_FLOW_ACTIVITY.FROM_STAGE", con, "", "", "");
			type = gfb.preferredDataType4SQLDataType(typeHT);
			fromStageht.put("Operator", fromStageOper);
			fromStageht.put("Text", fromStageText);
			fromStageht.put("BetText", fromStageBetText);
			fromStageht.put("Type", type);

			typeHT = new Hashtable<>();
			typeHT = gfb.getDatatypefromDBtable("WORK_FLOW_ACTIVITY.TO_STAGE", con, "", "", "");
			type = gfb.preferredDataType4SQLDataType(typeHT);
			toStageht.put("Operator", toStageOper);
			toStageht.put("Text", toStageText);
			toStageht.put("BetText", toStageBetText);
			toStageht.put("Type", type);

			typeHT = new Hashtable<>();
			typeHT = gfb.getDatatypefromDBtable("WORK_FLOW_ACTIVITY.FROM_STATUS", con, "", "", "");
			type = gfb.preferredDataType4SQLDataType(typeHT);
			fromStatusht.put("Operator", fromStatusOper);
			fromStatusht.put("Text", fromStatusText);
			fromStatusht.put("BetText", fromStatusBetText);
			fromStatusht.put("Type", type);

			typeHT = new Hashtable<>();
			typeHT = gfb.getDatatypefromDBtable("WORK_FLOW_ACTIVITY.MEMBER_STATUS", con, "", "", "");
			type = gfb.preferredDataType4SQLDataType(typeHT);
			memberStatusht.put("Operator", memberStatusOper);
			memberStatusht.put("Text", memberStatusText);
			memberStatusht.put("BetText", memberStatusBetText);
			memberStatusht.put("Type", type);

			typeHT = new Hashtable<>();
			typeHT = gfb.getDatatypefromDBtable("WORK_FLOW_ACTIVITY.NOTES", con, "", "", "");
			type = gfb.preferredDataType4SQLDataType(typeHT);
			notesht.put("Operator", notesOper);
			notesht.put("Text", notesText);
			notesht.put("BetText", notesBetText);
			notesht.put("Type", type);


			ht.put("DATE_TIME", WFdateTimeht);
			ht.put("ACTION_BY", WFActionByht);
			ht.put("ROLE", Roleht);
			ht.put("FROM_STAGE", fromStageht);
			ht.put("TO_STAGE", toStageht);
			ht.put("FROM_STATUS", fromStatusht);
			ht.put("MEMBER_STATUS", memberStatusht);
			ht.put("NOTES", notesht);
			Enumeration e = ht.keys();
			int k=0;
			Hashtable columnNameHT = new Hashtable<>();
			while(e.hasMoreElements()){
				columnNameHT.put(k, e.nextElement());
				k++;
			}
			String upperCase = "";

			//		ht.put(0, loginOper);
			sql = "select * from WORK_FLOW_ACTIVITY where HIERARCHY_ID = "+hierID+" and ";
			for(int i=0;i<columnNameHT.size();i++){
				Hashtable tempHT=new Hashtable<>();
				tempHT = (Hashtable) ht.get(String.valueOf(columnNameHT.get(i)));
				String pdatatype = "";
				String selectedcondition = String.valueOf(tempHT.get("Operator"));
				//Start code change Jayaramu 19APR14
				String columnValue = "";
				String betweenValue = "";
				if(!matchCase4workFlow){
					upperCase = "UPPER";
					columnValue = String.valueOf(tempHT.get("Text")).toUpperCase();
					if(selectedcondition.equals("Between") || selectedcondition.equals("Not Between")){
						betweenValue = String.valueOf(tempHT.get("BetText")).toUpperCase();
					}

				}else{
					columnValue = String.valueOf(tempHT.get("Text"));
					if(selectedcondition.equals("Between") || selectedcondition.equals("Not Between")){
						betweenValue = String.valueOf(tempHT.get("BetText"));
					}
				}
				//End code change Jayaramu 19APR14
				if(!selectedcondition.equals("-- select Value --") && !String.valueOf(tempHT.get("Text")).equals("")){
					pdatatype = String.valueOf(tempHT.get("Type"));
					System.out.println("tempHT===>"+tempHT.get("Type"));
					if(pdatatype.equalsIgnoreCase("String"))
					{
						sql = sql+upperCase+"("+columnNameHT.get(i)+")"; //code change Jayaramu 19APR14
						if(selectedcondition.equalsIgnoreCase("Starts With")){
							sql = sql+" LIKE '"+columnValue+"%' and ";
						}else if(selectedcondition.equalsIgnoreCase("Ends With")){
							sql = sql+" LIKE '%"+columnValue+"' and ";
						}else if(selectedcondition.equalsIgnoreCase("IN")){
							sql = sql+" IN ('"+columnValue+"') and ";
						}else if(selectedcondition.equalsIgnoreCase("Contains")){
							sql = sql+" LIKE '%"+columnValue+"%' and ";
						}else if(selectedcondition.equalsIgnoreCase("Wildcards")){
							sql = sql+" LIKE '"+columnValue+"' and ";
						}else if(selectedcondition.equalsIgnoreCase("Between")){
							sql = sql+" BETWEEN '"+columnValue+"'"+" AND "+ "'"+betweenValue+"' and ";
						}
						else if(selectedcondition.equalsIgnoreCase("Not Between")){
							sql = sql+" NOT BETWEEN '"+String.valueOf(tempHT.get("Text"))+"'"+" AND "+ "'"+betweenValue+"' and ";
						}
						//					sql = sql+columnNameHT.get(i)+String.valueOf(tempHT.get("Operator"))+"\""+String.valueOf(tempHT.get("Text"))+"\" and ";
					}else if(pdatatype.equalsIgnoreCase("int"))
					{
						if(selectedcondition.equalsIgnoreCase("Greater")){
							sql = sql+" >= " + String.valueOf(tempHT.get("Text"))+" and ";
						}else if(selectedcondition.equalsIgnoreCase("Lesser")){
							sql = sql+" <= " + String.valueOf(tempHT.get("Text"))+" and ";
						}else if(selectedcondition.equalsIgnoreCase("Between")){
							sql = sql+" BETWEEN " + String.valueOf(tempHT.get("Text"))+" and "+betweenValue+ " and ";
						}else if(selectedcondition.equalsIgnoreCase("IN")){
							sql = sql+" = " + String.valueOf(tempHT.get("Text"))+" and ";
						}
						//					sql = sql+columnNameHT.get(i)+" = "+String.valueOf(tempHT.get("Text"))+" and ";
					}else if(pdatatype.equalsIgnoreCase("double"))
					{
						if(selectedcondition.equalsIgnoreCase("Greater")){
							sql = sql+" >= " + String.valueOf(tempHT.get("Text"))+" and ";
						}else if(selectedcondition.equalsIgnoreCase("Lesser")){
							sql = sql+" <= " + String.valueOf(tempHT.get("Text"))+" and ";
						}else if(selectedcondition.equalsIgnoreCase("Between")){
							sql = sql + " BETWEEN " + String.valueOf(tempHT.get("Text"))+" and "+betweenValue+ " and ";
						}else if(selectedcondition.equalsIgnoreCase("IN")){
							sql = sql + " = " + String.valueOf(tempHT.get("Text"))+" and ";
						}
						//					sql = sql+columnNameHT.get(i)+" = "+String.valueOf(tempHT.get("Text"))+" and ";
					}else if(pdatatype.equalsIgnoreCase("date"))
					{
						if(selectedcondition.equalsIgnoreCase("Greater")){
							sql = sql + " >= " + String.valueOf(tempHT.get("Text"))+" and ";
						}else if(selectedcondition.equalsIgnoreCase("Lesser")){
							sql = sql + " <= " + String.valueOf(tempHT.get("Text"))+" and ";
						}else if(selectedcondition.equalsIgnoreCase("Between")){
							sql = sql + " BETWEEN " + String.valueOf(tempHT.get("Text"))+" and "+betweenValue+ " and ";
						}else if(selectedcondition.equalsIgnoreCase("IN")){
							sql = sql + " = " + String.valueOf(tempHT.get("Text"))+" and ";
						}
						//					sql = sql+columnNameHT.get(i)+" = \""+String.valueOf(tempHT.get("Text"))+"\" and ";
					}else if(pdatatype.equalsIgnoreCase("timestamp"))
					{
						if(selectedcondition.equalsIgnoreCase("Greater")){
							//						TO_TIMESTAMP('2003/12/13 10:13:18', 'YYYY/MM/DD HH:MI:SS')
							sql = sql + " >= TO_TIMESTAMP('" + String.valueOf(tempHT.get("Text"))+"', 'yyyy-MM-dd HH24:mi:ss.FF')"+" and ";
						}else if(selectedcondition.equalsIgnoreCase("Lesser")){
							sql = sql + " <= TO_TIMESTAMP('" + String.valueOf(tempHT.get("Text"))+"', 'yyyy-MM-dd HH24:mi:ss.FF')"+" and ";
						}else if(selectedcondition.equalsIgnoreCase("Between")){
							sql = sql + " BETWEEN TO_TIMESTAMP('" + String.valueOf(tempHT.get("Text"))+"', 'yyyy-MM-dd HH24:mi:ss.FF')"+" and  TO_TIMESTAMP('" + betweenValue+"', 'yyyy/MM/dd HH24:mi:ss.FF')"+ " and ";
						}else if(selectedcondition.equalsIgnoreCase("IN")){
							sql = sql + " = TO_TIMESTAMP('" + String.valueOf(tempHT.get("Text"))+"', 'yyyy-MM-dd HH24:mi:ss.FF')"+" and ";
						}
						//					sql = sql+columnNameHT.get(i)+" = \""+String.valueOf(tempHT.get("Text"))+"\" and ";
					}else if(pdatatype.equalsIgnoreCase("long"))
					{
						if(selectedcondition.equalsIgnoreCase("Greater")){
							sql = sql + " >= " + String.valueOf(tempHT.get("Text"))+" and ";
						}else if(selectedcondition.equalsIgnoreCase("Lesser")){
							sql = sql + " <= " + String.valueOf(tempHT.get("Text"))+" and ";
						}else if(selectedcondition.equalsIgnoreCase("Between")){
							sql = sql + " BETWEEN " + String.valueOf(tempHT.get("Text"))+" and "+betweenValue+ " and ";
						}else if(selectedcondition.equalsIgnoreCase("IN")){
							sql = sql + " = " + String.valueOf(tempHT.get("Text"))+" and ";
						}
						//					sql = sql+columnNameHT.get(i)+" = "+String.valueOf(tempHT.get("Text"))+" and ";
					}
					//				sql = sql+columnNameHT.get(i) 
				}

			}
			sql = sql.substring(0, sql.length()-4)+"order by DATE_TIME DESC";
			System.out.println("sql===>"+sql);
			PreparedStatement pre = con.prepareStatement(sql);
			ResultSet rs = pre.executeQuery();
			HierarchydataBean hdb;
			auditRecordAL = new ArrayList<>();
			HierarchydataBean hb;
			workflowAL = new ArrayList<>(); 
			while(rs.next()){
				hb = new HierarchydataBean(rs.getTimestamp("DATE_TIME").toString(), rs.getString("ACTION_BY"), rs.getString("ROLE"), 
						rs.getString("FROM_STAGE"), rs.getString("TO_STAGE"), rs.getString("FROM_STATUS"), rs.getString("MEMBER_STATUS"),
						rs.getString("NOTES"), rs.getString("HIERARCHY_ID"), rs.getString("HIERARCHY_NAME"), rs.getString("HIERARCHY_TYPE"));
				workflowAL.add(hb);
			}
			hierarchy_ID = hierID;
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}

	public void filteringAuditTable(String hierID) {
		try{
			genarateFactsBean gfb = new genarateFactsBean("","","");
			Connection con=Globals.getDBConnection("DW_Connection");
			String sql = "";
			Hashtable ht = new Hashtable<>();
			Hashtable loginht = new Hashtable<>();
			Hashtable dateTimeht = new Hashtable<>();
			Hashtable actionht = new Hashtable<>();
			Hashtable parentht = new Hashtable<>();
			Hashtable beforeht = new Hashtable<>();
			Hashtable afterht = new Hashtable<>();
			Hashtable oldht = new Hashtable<>();
			Hashtable oldPostionht = new Hashtable<>();
			Hashtable newht = new Hashtable<>();
			Hashtable newPostionht = new Hashtable<>();
			Hashtable typeHT=new Hashtable<>();
			String type = "";

			typeHT = new Hashtable<>();
			typeHT = gfb.getDatatypefromDBtable("HIERARCHY_AUDIT.LOGIN_ID", con, "", "", "");
			type = gfb.preferredDataType4SQLDataType(typeHT);
			loginht.put("Operator", loginOper);
			loginht.put("Text", loginText);
			loginht.put("BetText", loginBetText);
			loginht.put("Type", type);

			typeHT = new Hashtable<>();
			typeHT = gfb.getDatatypefromDBtable("HIERARCHY_AUDIT.DATE_TIME", con, "", "", "");
			type = gfb.preferredDataType4SQLDataType(typeHT);
			dateTimeht.put("Operator", dateTimeOper);
			dateTimeht.put("Text", dateTimeText);
			dateTimeht.put("BetText", dateTimeBetText);
			dateTimeht.put("Type", type);

			typeHT = new Hashtable<>();
			typeHT = gfb.getDatatypefromDBtable("HIERARCHY_AUDIT.ACTION", con, "", "", "");
			type = gfb.preferredDataType4SQLDataType(typeHT);
			actionht.put("Operator", actionOper);
			actionht.put("Text", actionText);
			actionht.put("Type", type);

			typeHT = new Hashtable<>();
			typeHT = gfb.getDatatypefromDBtable("HIERARCHY_AUDIT.PARENT_NODE_ID", con, "", "", "");
			type = gfb.preferredDataType4SQLDataType(typeHT);
			parentht.put("Operator", parentOper);
			parentht.put("Text", parentText);
			parentht.put("BetText", parentBetText);
			parentht.put("Type", type);

			typeHT = new Hashtable<>();
			typeHT = gfb.getDatatypefromDBtable("HIERARCHY_AUDIT.BEFORE_NODE_TEXT", con, "", "", "");
			type = gfb.preferredDataType4SQLDataType(typeHT);
			beforeht.put("Operator", beforeOper);
			beforeht.put("Text", beforeText);
			beforeht.put("BetText", beforeBetText);
			beforeht.put("Type", type);

			typeHT = new Hashtable<>();
			typeHT = gfb.getDatatypefromDBtable("HIERARCHY_AUDIT.AFTER_NODE_TEXT", con, "", "", "");
			type = gfb.preferredDataType4SQLDataType(typeHT);
			afterht.put("Operator", afterOper);
			afterht.put("Text", afterText);
			afterht.put("BetText", afterBetText);
			afterht.put("Type", type);

			typeHT = new Hashtable<>();
			typeHT = gfb.getDatatypefromDBtable("HIERARCHY_AUDIT.BEFORE_PARENT_NODE_MOVE", con, "", "", "");
			type = gfb.preferredDataType4SQLDataType(typeHT);
			oldht.put("Operator", oldOper);
			oldht.put("Text", oldText);
			oldht.put("BetText", oldBetText);
			oldht.put("Type", type);

			typeHT = new Hashtable<>();
			typeHT = gfb.getDatatypefromDBtable("HIERARCHY_AUDIT.BEFORE_POSITION_MOVE", con, "", "", "");
			type = gfb.preferredDataType4SQLDataType(typeHT);
			oldPostionht.put("Operator", oldPostionOper);
			oldPostionht.put("Text", oldPostionText);
			oldPostionht.put("BetText", oldPostionBetText);
			oldPostionht.put("Type", type);

			typeHT = new Hashtable<>();
			typeHT = gfb.getDatatypefromDBtable("HIERARCHY_AUDIT.AFTER_PARENT_NODE_MOVE", con, "", "", "");
			type = gfb.preferredDataType4SQLDataType(typeHT);
			newht.put("Operator", newOper);
			newht.put("Text", newText);
			newht.put("BetText", newBetText);
			newht.put("Type", type);

			typeHT = new Hashtable<>();
			typeHT = gfb.getDatatypefromDBtable("HIERARCHY_AUDIT.AFTER_POSITION_MOVE", con, "", "", "");
			type = gfb.preferredDataType4SQLDataType(typeHT);
			newPostionht.put("Operator", newPostionOper);
			newPostionht.put("Text", newPostionText);
			newPostionht.put("BetText", newPostionBetText);
			newPostionht.put("Type", type);

			ht.put("LOGIN_ID", loginht);
			ht.put("DATE_TIME", dateTimeht);
			ht.put("ACTION", actionht);
			ht.put("PARENT_NODE_ID", parentht);
			ht.put("BEFORE_NODE_TEXT", beforeht);
			ht.put("AFTER_NODE_TEXT", afterht);
			ht.put("BEFORE_PARENT_NODE_MOVE", oldht);
			ht.put("BEFORE_POSITION_MOVE", oldPostionht);
			ht.put("AFTER_PARENT_NODE_MOVE", newht);
			ht.put("AFTER_POSITION_MOVE", newPostionht);
			Enumeration e = ht.keys();
			int k=0;
			Hashtable columnNameHT = new Hashtable<>();
			while(e.hasMoreElements()){
				columnNameHT.put(k, e.nextElement());
				k++;
			}
			String upperCase = "";
			String columnValue = "";
			String betweenValue = "";
			//			ht.put(0, loginOper);
			sql = "select * from HIERARCHY_AUDIT where HIERARCHY_ID = "+hierID+" and ";
			for(int i=0;i<columnNameHT.size();i++){
				Hashtable tempHT=new Hashtable<>();
				tempHT = (Hashtable) ht.get(String.valueOf(columnNameHT.get(i)));
				String pdatatype = "";
				String selectedcondition = String.valueOf(tempHT.get("Operator"));

				if(!matchCase4AuditTable){ // code change Jayaramu 19APR14
					upperCase = "UPPER";
					columnValue = String.valueOf(tempHT.get("Text")).toUpperCase();
					if(selectedcondition.equals("Between") || selectedcondition.equals("Not Between")){
						betweenValue = String.valueOf(tempHT.get("BetText")).toUpperCase();
					}

				}else{
					columnValue = String.valueOf(tempHT.get("Text"));
					if(selectedcondition.equals("Between") || selectedcondition.equals("Not Between")){
						betweenValue = String.valueOf(tempHT.get("BetText"));
					}
				}

				if(!selectedcondition.equals("-- select Value --") && !String.valueOf(tempHT.get("Text")).equals("")){
					pdatatype = String.valueOf(tempHT.get("Type"));
					System.out.println("tempHT===>"+tempHT.get("Type"));
					sql = sql+upperCase+"("+columnNameHT.get(i)+")"; //code change Jayaramu 19APR14
					if(pdatatype.equalsIgnoreCase("String"))
					{
						if(selectedcondition.equalsIgnoreCase("Starts With")){
							sql = sql+" LIKE '"+columnValue+"%' and ";
						}else if(selectedcondition.equalsIgnoreCase("Ends With")){
							sql = sql+" LIKE '%"+columnValue+"' and ";
						}else if(selectedcondition.equalsIgnoreCase("IN")){
							sql = sql+" IN ('"+columnValue+"') and ";
						}else if(selectedcondition.equalsIgnoreCase("Contains")){
							sql = sql+" LIKE '%"+columnValue+"%' and ";
						}else if(selectedcondition.equalsIgnoreCase("Wildcards")){
							sql = sql+" LIKE '"+columnValue+"' and ";
						}else if(selectedcondition.equalsIgnoreCase("Between")){
							sql = sql+" BETWEEN '"+columnValue+"'"+" AND "+ "'"+betweenValue+"' and ";
						}
						else if(selectedcondition.equalsIgnoreCase("Not Between")){
							sql = sql+" NOT BETWEEN '"+columnValue+"'"+" AND "+ "'"+betweenValue+"' and ";
						}
						//						sql = sql+columnNameHT.get(i)+String.valueOf(tempHT.get("Operator"))+"\""+String.valueOf(tempHT.get("Text"))+"\" and ";
					}else if(pdatatype.equalsIgnoreCase("int"))
					{
						if(selectedcondition.equalsIgnoreCase("Greater")){
							sql = sql +  " >= " + columnValue+" and ";
						}else if(selectedcondition.equalsIgnoreCase("Lesser")){
							sql = sql +  " <= " + columnValue+" and ";
						}else if(selectedcondition.equalsIgnoreCase("Between")){
							sql = sql + " BETWEEN " + columnValue+" and "+betweenValue+ " and ";
						}else if(selectedcondition.equalsIgnoreCase("IN")){
							sql = sql + " = " + columnValue+" and ";
						}
						//						sql = sql+columnNameHT.get(i)+" = "+String.valueOf(tempHT.get("Text"))+" and ";
					}else if(pdatatype.equalsIgnoreCase("double"))
					{
						if(selectedcondition.equalsIgnoreCase("Greater")){
							sql = sql + " >= " + columnValue+" and ";
						}else if(selectedcondition.equalsIgnoreCase("Lesser")){
							sql = sql + " <= " + columnValue+" and ";
						}else if(selectedcondition.equalsIgnoreCase("Between")){
							sql = sql + " BETWEEN " + columnValue+" and "+betweenValue+ " and ";
						}else if(selectedcondition.equalsIgnoreCase("IN")){
							sql = sql + " = " + columnValue+" and ";
						}
						//						sql = sql+columnNameHT.get(i)+" = "+String.valueOf(tempHT.get("Text"))+" and ";
					}else if(pdatatype.equalsIgnoreCase("date"))
					{
						if(selectedcondition.equalsIgnoreCase("Greater")){
							sql = sql + " >= " + columnValue+" and ";
						}else if(selectedcondition.equalsIgnoreCase("Lesser")){
							sql = sql + " <= " + columnValue+" and ";
						}else if(selectedcondition.equalsIgnoreCase("Between")){
							sql = sql + " BETWEEN " + columnValue+" and "+betweenValue+ " and ";
						}else if(selectedcondition.equalsIgnoreCase("IN")){
							sql = sql + " = " + columnValue+" and ";
						}
						//						sql = sql+columnNameHT.get(i)+" = \""+String.valueOf(tempHT.get("Text"))+"\" and ";
					}else if(pdatatype.equalsIgnoreCase("timestamp"))
					{
						if(selectedcondition.equalsIgnoreCase("Greater")){
							//							TO_TIMESTAMP('2003/12/13 10:13:18', 'YYYY/MM/DD HH:MI:SS')
							sql = sql + " >= TO_TIMESTAMP('" + columnValue+"', 'yyyy-MM-dd HH24:mi:ss.FF')"+" and ";
						}else if(selectedcondition.equalsIgnoreCase("Lesser")){
							sql = sql + " <= TO_TIMESTAMP('" + columnValue+"', 'yyyy-MM-dd HH24:mi:ss.FF')"+" and ";
						}else if(selectedcondition.equalsIgnoreCase("Between")){
							sql = sql + " BETWEEN TO_TIMESTAMP('" + columnValue+"', 'yyyy-MM-dd HH24:mi:ss.FF')"+" and  TO_TIMESTAMP('" + betweenValue+"', 'yyyy/MM/dd HH24:mi:ss.FF')"+ " and ";
						}else if(selectedcondition.equalsIgnoreCase("IN")){
							sql = sql + " = TO_TIMESTAMP('" + columnValue+"', 'yyyy-MM-dd HH24:mi:ss.FF')"+" and ";
						}
						//						sql = sql+columnNameHT.get(i)+" = \""+String.valueOf(tempHT.get("Text"))+"\" and ";
					}else if(pdatatype.equalsIgnoreCase("long"))
					{
						if(selectedcondition.equalsIgnoreCase("Greater")){
							sql = sql + " >= " + columnValue+" and ";
						}else if(selectedcondition.equalsIgnoreCase("Lesser")){
							sql = sql + " <= " + columnValue+" and ";
						}else if(selectedcondition.equalsIgnoreCase("Between")){
							sql = sql + " BETWEEN " + columnValue+" and "+betweenValue+ " and ";
						}else if(selectedcondition.equalsIgnoreCase("IN")){
							sql = sql + " = " + columnValue+" and ";
						}
						//						sql = sql+columnNameHT.get(i)+" = "+String.valueOf(tempHT.get("Text"))+" and ";
					}
					//					sql = sql+columnNameHT.get(i) 
				}

			}
			sql = sql.substring(0, sql.length()-4)+"order by DATE_TIME DESC";
			System.out.println("sql===>"+sql);
			PreparedStatement pre = con.prepareStatement(sql);
			ResultSet rs = pre.executeQuery();
			HierarchydataBean hdb;
			auditRecordAL = new ArrayList<>();
			while(rs.next()){
				String beforeValue=rs.getString("BEFORE_NODE_TEXT");
				String afterValue=rs.getString("AFTER_NODE_TEXT");
				beforeValue = splitingValuefromNode(beforeValue);
				afterValue = splitingValuefromNode(afterValue);
				//				System.out.println("1===>"+rs.getString("LOGIN_ID"));
				//				System.out.println("2===>"+rs.getTimestamp("DATE_TIME").toString());
				//				System.out.println("3===>"+String.valueOf(rs.getInt("PARENT_NODE_ID")));
				//				System.out.println("4===>"+rs.getString("ACTION"));
				//				System.out.println("5===>"+rs.getString("BEFORE_NODE_TEXT"));
				//				System.out.println("6===>"+rs.getString("AFTER_NODE_TEXT"));
				//				System.out.println("7===>"+rs.getString("BEFORE_PARENT_NODE_MOVE"));
				//				System.out.println("8===>"+rs.getString("BEFORE_POSITION_MOVE"));
				//				System.out.println("9===>"+rs.getString("AFTER_PARENT_NODE_MOVE"));
				//				System.out.println("10===>"+rs.getString("AFTER_POSITION_MOVE"));
				hdb=new HierarchydataBean(rs.getString("LOGIN_ID"), rs.getTimestamp("DATE_TIME").toString(), rs.getString("ACTION"), String.valueOf(rs.getInt("PARENT_NODE_ID")),
						beforeValue, afterValue, rs.getString("BEFORE_PARENT_NODE_MOVE"), rs.getString("BEFORE_POSITION_MOVE"),
						rs.getString("AFTER_PARENT_NODE_MOVE"), rs.getString("AFTER_POSITION_MOVE"));
				auditRecordAL.add(hdb);
			}
			hierarchy_ID = hierID;
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public void setValidationMsg(){
		try{
			PropUtil prop = new PropUtil();
			String dir = prop.getProperty("HIERARCHY_XML_DIR");
			String hrLevelXmlFile = prop.getProperty("HIERARCHY_XML_FILE");
			Document HierLevelXmlDoc = Globals.openXMLFile(dir, hrLevelXmlFile);
			Node hierlevelNode=Globals.getNodeByAttrVal(HierLevelXmlDoc, "Hierarchy_Level", "Hierarchy_ID", hierarchy_ID);   
			if(hierlevelNode == null){
				message4RIconstraints = "Please create hierarchy to proceed further.";
				return;
			}else {
				getRIContraintsRulesFromRICXml();
				message4RIconstraints = "";
			}

		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	public void resetWFFilter() {
		try{
			WFdateTimeOper = "IN";
			WFdateTimeText = "";
			WFdateTimeBetText = "";
			WFActionByOper = "Starts With";
			WFActionByText = "";
			WFActionByBetText = "";
			roleOper = "Starts With";
			roleText = "";
			roleBetText = "";
			fromStageOper = "Starts With";
			fromStageText = "";
			fromStageBetText = "";
			toStageOper = "Starts With";
			toStageText = "";
			toStageBetText = "";
			fromStatusOper ="Starts With";
			fromStatusText = "";
			fromStatusBetText = "";
			notesOper = "Starts With";
			notesText = "";
			notesBetText = "";
			memberStatusOper = "Starts With";
			memberStatusText = "";
			memberStatusBetText = "";
			viewingWorkFlowRecords(hierarchy_ID);
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	public void resetFilter() {
		try{
			loginOper = "Starts With";
			loginText = "";
			dateTimeOper = "IN";
			dateTimeText = "";
			actionOper = "Starts With";
			actionText = "";
			parentOper = "IN";
			parentText = "";
			beforeOper = "Contains";
			beforeText = "";
			afterOper = "Contains";
			afterText = "";
			oldOper = "Starts With";
			oldText = "";
			oldPostionOper = "Starts With";
			oldPostionText = "";
			newOper = "Starts With";
			newText = "";
			newPostionOper = "Starts With";
			newPostionText = "";
			loginBetText = "";
			dateTimeBetText = "";
			actionBetText = "";
			parentBetText = "";
			beforeBetText = "";
			afterBetText = "";
			oldBetText = "";
			oldPostionBetText = "";
			newBetText = "";
			newPostionBetText = "";
			viewingAuditRecords(hierarchy_ID);
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}


	String reGenerateMess = "";


	public String getReGenerateMess() {
		return reGenerateMess;
	}
	public void setReGenerateMess(String reGenerateMess) {
		this.reGenerateMess = reGenerateMess;
	}


	String riMessage = "";
	public String getRiMessage() {
		return riMessage;
	}
	public void setRiMessage(String riMessage) {
		this.riMessage = riMessage;
	}
	public void setFlag4valipopup(){
		cmngFromPage4RIConstraints = "AdminRulepopup";
		try{
			//			Reference
			//			Dependent
			if(status.equalsIgnoreCase("Independent")){
				riMessage = "";
			}else if(status.equalsIgnoreCase("Reference")){
				riMessage = "This Reference Hierarchy has Dependant Hierarchies. Do you want to Validate them now?";
			}else if(status.equalsIgnoreCase("Dependent")){
				riMessage = "This is a Dependent Hierarchy. As you have changed the Referential Integrity constraints configuration, you need to ensure the Hierarchy still conforms to the updated constraints list. Do you want to validate the Hierarchy now?";
			}else if(status.equalsIgnoreCase("Reporting")){
				riMessage = "This is a Reporting Hierarchy. As you have changed the Referential Integrity constraints configuration, you need to ensure the Hierarchy still conforms to the updated constraints list. Do you want to validate the Hierarchy now?";
			}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	//start code change Menaka 29MAR2014
	public void saveNrestoreVersion(){ 

		//		System.out.println("versionRestore===MMMMMMMMMMMMMMMMMMMMMMM============>>>"+this.hierarchy_ID);

		if(versionRestore.equals("Save")){
			savingVersion(this.hierarchy_ID);
		}else{
			restoringVersion(this.hierarchy_ID);
		}
	}

	public void setVersionFrom(String from){
		//		System.out.println("from=====MMMMMMMMMMMMMMMMMMMMMMMMMMM==========>>>"+from);
		msg1="";
		msg4VersDisp="";
		if(from.equals("Restore")){
			if(versionSelectedList.size()>0){
				verNotePopupVisible=true;
				cmgFromViewVersion="HRYTree";
				HierarchydataBean hyDB = (HierarchydataBean) versionSelectedList.get(0);
				versionNo= hyDB.getVersionNo();
			}else{
				verNotePopupVisible=false;
				if(versionNumberAL==null||versionNumberAL.size()<=0){  // code change Menaka 28MAR2014
					msg4VersDisp="There is no version to restore";
					//					System.out.println("msg4VersDisp===========>>>"+msg4VersDisp);
					return;
				}

				if(versionSelectedList.size()<=0){   // code change Menaka 28MAR2014
					msg4VersDisp="Please select a version of this hierarchy to proceed further.";
					//					System.out.println("msg4VersDisp===========>>>"+msg4VersDisp);
					return;
				}

			}

		}
		if(from.equals("Restore1")){
			cmgFromViewVersion="HRYTree1";
			from="Restore";
		}
		setVersionRestore(from);
		//		System.out.println("VersionRestore=================>>>"+versionRestore);

	}
	//end code change Menaka 29MAR2014
	TreeMap hierarchyCategoryTM = new TreeMap<>();
	public TreeMap getHierarchyCategoryTM() {
		return hierarchyCategoryTM;
	}
	public void setHierarchyCategoryTM(TreeMap hierarchyCategoryTM) {
		this.hierarchyCategoryTM = hierarchyCategoryTM;
	}
	public void changeHierName(String hierName, String updateNdName) {
		try{
			//			Globals.openXMLFile(XMLFileLocation, XMLFileName)
			System.out.println("changeHierName=================>>>"+hierName);
			msg1 = "";
			if(hierName.equals("")){
				msg1 = "Hierarchy Name must not be empty. Please enter hierarchy Name";
				color4HierTreeMsg = "red";
				return;
			}
			String xmlDir = "";
			String hierLevelXmlFileName = "";
			PropUtil prop = new PropUtil();
			xmlDir = prop.getProperty("HIERARCHY_XML_DIR");
			hierLevelXmlFileName = prop.getProperty("HIERARCHY_XML_FILE");
			FileInputStream fis = new FileInputStream(xmlDir+hierLevelXmlFileName);
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(fis);
			doc.getDocumentElement().normalize();
			Node hierNd = Globals.getNodeByAttrVal(doc, "Hierarchy_Level", "Hierarchy_ID", hierarchy_ID);
			if(updateNdName.equals("Name")){
				NodeList hierNdList = doc.getElementsByTagName("Hierarchy_Level");

				if(hierNdList!=null){
					for(int i=0;i<hierNdList.getLength();i++){
						if(hierNdList.item(i).getNodeType() == Node.ELEMENT_NODE){
							if(Globals.getAttrVal4AttrName(hierNdList.item(i), "Hierarchy_Name").equals(hierName)){
								msg1 = "Hierarchy Name entered is not unique. Please enter another hierarchy Name";
								color4HierTreeMsg = "red";
								fullMsg1=msg1;
								if(msg1.length()>65){  // code change Menaka 10APR2014
									msg1=msg1.substring(0, 65)+"...";
								}
								return;
							}
						}

					}
				}

				if(hierNd!=null){
					Element hierEle = (Element) hierNd;
					hierEle.setAttribute("Hierarchy_Name", hierName);

				}
			}else if(updateNdName.equals("Category")){
				if(hierNd!=null){
					Element hierEle = (Element) hierNd;
					hierEle.setAttribute("Hierarchy_Category", hierName);

				}
			}else if(updateNdName.equals("CodeCombination")){
				if(hierNd!=null){
					Element hierEle = (Element) hierNd;
					hierEle.setAttribute("Code_Combination", hierName);

				}
			}
			Globals.writeXMLFile(doc, xmlDir, hierLevelXmlFileName);
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}


	public void insertWorkFlowActivity2DB(Document doc){ //Start code change Jayaramu 10APR14
		try{
			System.out.println("Entering: " + new Exception().getStackTrace()[0].getClassName() + "."

		     + new Exception().getStackTrace()[0].getMethodName());

			FacesContext ctx1 = FacesContext.getCurrentInstance();
			ExternalContext extContext1 = ctx1.getExternalContext();
			Map sessionMap1 = extContext1.getSessionMap();
			LoginBean lgnbn = (LoginBean) sessionMap1.get("loginBean");

			LoginProcessManager  log=new LoginProcessManager();
			Hashtable LoginDetailsHT=log.getLoginDetails(lgnbn.getUsername());
			String AccessUniqID=(String)LoginDetailsHT.get("Access_Unique_ID");
			//		  System.out.println("AccessUniqID====>>>"+AccessUniqID);

			String userRole = "";
			String from_Status = "";
			String member_Mail_ID = "";
			String from_Stage = "";
			String to_Stage = "";
			String to_Status = "";
			String currentStageNo = "";
			String hierarchy_Name = "";
			String hierarchy_Type = "";
			int totalStage = 0;
			int curStage = 0;
			PropUtil prop=new PropUtil();
			String xmlDir = prop.getProperty("HIERARCHY_XML_DIR");
			Node nd = Globals.getNodeByAttrVal(doc, "Hierarchy_Level", "Hierarchy_ID",hierarchy_ID);
			Hashtable WFNodeDetailsHT=Globals.getWFNodeDetails(hierarchy_ID, "",true,AccessUniqID);
			Hashtable UserDetailsHT=(Hashtable)WFNodeDetailsHT.get("UserDetailsHT");

			if(UserDetailsHT != null){		   
				userRole =  (String)UserDetailsHT.get("User_Role");
				from_Status=(String)UserDetailsHT.get("User_Status");
				member_Mail_ID = (String)UserDetailsHT.get("E-mail");
			}
			if(WFNodeDetailsHT != null){
				from_Stage = (String)WFNodeDetailsHT.get("Current_Stage_Name");
				currentStageNo=(String)WFNodeDetailsHT.get("Current_Stage_No"); 
				String totalnoOfstage=(String)WFNodeDetailsHT.get("Total_No_Stages");
				totalStage = Integer.parseInt(totalnoOfstage);
				if(currentStageNo.equals("Completed") || currentStageNo.equalsIgnoreCase("Cancel") || currentStageNo.equalsIgnoreCase("Rules Failed")){
					currentStageNo=totalnoOfstage;
				}
				curStage = Integer.parseInt(currentStageNo);
			}

			int hierID = Integer.parseInt(hierarchy_ID);
			int toStageNo =0;
			if(totalStage > curStage){
				toStageNo = curStage+1;
			}else  if(totalStage==curStage){
				toStageNo = curStage;
			}
			Node wrkFlowNode=Globals.getNodeByAttrVal(doc, "Workflow", "Hierarchy_ID", hierarchy_ID);
			Node stageNode=Globals.getNodeByAttrValUnderParent(doc, wrkFlowNode, "Stage_No", String.valueOf(toStageNo));
			Node stgNode = stageNode.getAttributes().getNamedItem("Stage_Name");
			Node stgstatus = stageNode.getAttributes().getNamedItem("Stage_Status");
			if(stgNode != null){
				to_Stage = stgNode.getNodeValue();
				to_Status = stageNode.getAttributes().getNamedItem("Stage_Status").getNodeValue();
			}

			if(nd != null){
				hierarchy_Name = nd.getAttributes().getNamedItem("Hierarchy_Name").getNodeValue();
				hierarchy_Type = nd.getAttributes().getNamedItem("RI_Hierarchy_Type").getNodeValue();
			}

			//		  System.out.println("to_Stage====>>>"+to_Stage);
			//		  System.out.println("currentStageNo====>>>"+currentStageNo);
			//		  System.out.println("userRole====>>>"+userRole);
			//		  System.out.println("memberstatuss====>>>"+from_Status);
			//		  System.out.println("stageRole====>>>"+from_Stage);
			//		  System.out.println("to_Status====>>>"+to_Status);

			System.out.println("User Login Details For UPDATE DB TABLE : to_Stage "+to_Stage+" currentStageNo "+currentStageNo+" userRole "+userRole+
					" from_Status "+from_Status+" from_Stage "+from_Stage+" to_Status "+to_Status);

			Connection connection = Globals.getDBConnection("DW_Connection");
			String insertSql = "UPDATE WORK_FLOW_ACTIVITY SET ROLE='"+userRole+"',FROM_STAGE='"+from_Stage+"',TO_STAGE='"+to_Stage+"',FROM_STATUS='"+from_Status+"',HIERARCHY_ID='"+hierID+"',HIERARCHY_NAME='"+hierarchy_Name+"',HIERARCHY_TYPE='"+hierarchy_Type+"' WHERE LOGIN_ID= '"+lgnbn.getUniqueID()+"'";
			System.out.println("User Login Details For UPDATE DB TABLE : insertSql  :"+insertSql);
			PreparedStatement ps=connection.prepareStatement(insertSql);
			ps.execute();
			ps.close();
			connection.close();
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		System.out.println("Exiting: " + new Exception().getStackTrace()[0].getClassName() + "."
				+ new Exception().getStackTrace()[0].getMethodName());
	}
	//end code change Jayaramu 10APR14


	public void updateWFActivityInDB(Connection con,ArrayList UserStatusAL,int wfUniqueID,String userName,Hashtable UserStatusHT,boolean isLoginIDisExists) { 
		try{

			System.out.println("Entering: " + new Exception().getStackTrace()[0].getClassName() + "."	     
					+ new Exception().getStackTrace()[0].getMethodName());
			String userStatus="";
			String values = "";
			PreparedStatement ps = null;
			String insertSql = "";

			if(isLoginIDisExists){
				Enumeration enumm=UserStatusHT.keys();
				while(enumm.hasMoreElements()){
					String htKey=(String)enumm.nextElement();
					String htValue=(String)UserStatusHT.get(htKey);
					userStatus=userStatus+htKey+"='"+htValue+"',";
				}
				if(userStatus.endsWith(",")){			  
					userStatus=userStatus.substring(0,userStatus.length()-1);
				}
				System.out.println("USER Value From HT For Update DB Table Column : "+userStatus);
				System.out.println("USER Value From HT For Update DB Table Values: "+values);
				insertSql = "UPDATE WORK_FLOW_ACTIVITY SET "+userStatus+" WHERE WF_UNIQUE_ID="+wfUniqueID+" AND HIERARCHY_ID="+hierarchy_ID+"";
				ps=con.prepareStatement(insertSql);
			}else{ //Start code change Jayaramu 10APR14
				insertSql = "INSERT INTO WORK_FLOW_ACTIVITY(WF_UNIQUE_ID,DATE_TIME,ACTION_BY,ROLE,MEMBER_MAIL_ID,FROM_STAGE,TO_STAGE,FROM_STATUS,MEMBER_STATUS,NOTES,HIERARCHY_ID,HIERARCHY_NAME,HIERARCHY_TYPE,MAIL_NOTIFICATION,REJECTED_RETURN_TYPE,REJECTED_SET_STATUS,REJECTED_NOTIFICATION_TYPE,REJECTED_RETURN_TO_MEMBER) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
				System.out.println("User Login Details For UPDATE DB TABLE : insertSql  :"+insertSql);
				ps=con.prepareStatement(insertSql);

				for(int i=0;i<UserStatusAL.size();i++){
					if(i == 0 || i==10){
						ps.setInt(i+1, Integer.parseInt(String.valueOf(UserStatusAL.get(i))));
					}else if(i == 1){
						ps.setTimestamp(i+1, (Timestamp) UserStatusAL.get(i));
					}else{
						ps.setString(i+1, (String) UserStatusAL.get(i));
					}
				}
			}//End code change Jayaramu 10APR14


			ps.execute();
			ps.close();
			//	  connection.close();
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		System.out.println("Exiting: " + new Exception().getStackTrace()[0].getClassName() + "."
				+ new Exception().getStackTrace()[0].getMethodName());
	}
	//Start code change Jayaramu 10APR14
	public void updateMailID(String mailID){
		try{
			System.out.println("Entering: " + new Exception().getStackTrace()[0].getClassName() + "."
					+ new Exception().getStackTrace()[0].getMethodName());

			FacesContext ctx1 = FacesContext.getCurrentInstance();
			ExternalContext extContext1 = ctx1.getExternalContext();
			Map sessionMap1 = extContext1.getSessionMap();
			LoginBean lgnbn = (LoginBean) sessionMap1.get("loginBean");

			Connection connection = Globals.getDBConnection("DW_Connection");

			String isLoginIDExists = "select * from WORK_FLOW_ACTIVITY where WF_UNIQUE_ID="+wfUniqueID+" AND HIERARCHY_ID="+hierarchy_ID+"";
			System.out.println("User Login isExists : isLoginIDExists  :"+isLoginIDExists);
			System.out.println("Notification mailID   :"+mailID);
			Statement statment=connection.createStatement();
			ResultSet results = statment.executeQuery(isLoginIDExists);
			System.out.println("results  :"+results);
			PreparedStatement ps = null;
			String insertSql = "";
			boolean update = false;
			if(results.next()){
				update = true;
			}

			if(!update){
				insertSql = "INSERT INTO WORK_FLOW_ACTIVITY(WF_UNIQUE_ID,DATE_TIME,HIERARCHY_ID,MAIL_NOTIFICATION) VALUES(?,?,?,?)";
				ps=connection.prepareStatement(insertSql);
				int hierID = Integer.parseInt(hierarchy_ID);
				ps.setInt(1,wfUniqueID);
				ps.setTimestamp(2,lgnbn.getUseraccsDate());
				ps.setInt(3,hierID);
				ps.setString(4,mailID);
			}else{

				insertSql = "UPDATE WORK_FLOW_ACTIVITY SET MAIL_NOTIFICATION='"+mailID+"' WHERE WF_UNIQUE_ID="+wfUniqueID+" AND HIERARCHY_ID="+hierarchy_ID+"";
				ps=connection.prepareStatement(insertSql);
			}
			System.out.println("insertSql  :"+insertSql);
			ps.execute();
			ps.close();
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		System.out.println("Exiting: " + new Exception().getStackTrace()[0].getClassName() + "."
				+ new Exception().getStackTrace()[0].getMethodName());
	}
	String copyHierarchyName = "";
	public String getCopyHierarchyName() {
		return copyHierarchyName;
	}
	public void setCopyHierarchyName(String copyHierarchyName) {
		this.copyHierarchyName = copyHierarchyName;
	}
	String copyConfirmMess = "";
	public String getCopyConfirmMess() {
		return copyConfirmMess;
	}
	public void setCopyConfirmMess(String copyConfirmMess) {
		this.copyConfirmMess = copyConfirmMess;
	}
	public void messageConstructor4Copy(){
		try{
			copyMess = "";
			copyHierarchyName = "";
			copyConfirmMess = "This hierarchy will be saved as another hierarchy";
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	String copyMess = "";
	public String getCopyMess() {
		return copyMess;
	}
	public void setCopyMess(String copyMess) {
		this.copyMess = copyMess;
	}
	public void copyHierarchyCheck(String hierId, String copyHierarchyName) {
		try{
			copyMess = "";
			String HIERARCHY_XML_DIR = "";
			String HIERARCHY_XML_File = "";
			PropUtil prop = new PropUtil();
			HIERARCHY_XML_DIR = prop.getProperty("HIERARCHY_XML_DIR");
			HIERARCHY_XML_File = prop.getProperty("HIERARCHY_XML_FILE");
			Document doc = Globals.openXMLFile(HIERARCHY_XML_DIR, HIERARCHY_XML_File);
			NodeList ndList = doc.getElementsByTagName("Hierarchy_Levels");
			Node nd = ndList.item(0);
			Node hierNd = Globals.getNodeByAttrVal(doc, "Hierarchy_Level", "Hierarchy_Name", copyHierarchyName);
			System.out.println("--->"+hierNd);
			if(hierNd==null){
				copyHierarchy(hierId, copyHierarchyName);
				msg1 = "This Hierarchy will be copied as an Independent Hierarchy.";
				color4HierTreeMsg = "blue";
			}else{
				copyMess = "The given hierarchy name is already avilable in hierarchy level. Please change the hierarchy Name";
			}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	public void copyHierarchy(String hierId, String copyHierarchyName) {
		System.out.println("Entering: "
				+ new Exception().getStackTrace()[0].getClassName() + "."
				+ new Exception().getStackTrace()[0].getMethodName());
		try{
			String HIERARCHY_XML_DIR = "";
			String HIERARCHY_XML_File = "";
			PropUtil prop = new PropUtil();
			HIERARCHY_XML_DIR = prop.getProperty("HIERARCHY_XML_DIR");
			HIERARCHY_XML_File = prop.getProperty("HIERARCHY_XML_FILE");
			Document doc = Globals.openXMLFile(HIERARCHY_XML_DIR, HIERARCHY_XML_File);
			NodeList ndList = doc.getElementsByTagName("Hierarchy_Levels");
			Node nd = ndList.item(0);
			//		 = "256";
			Node hierNd = Globals.getNodeByAttrVal(doc, "Hierarchy_Level", "Hierarchy_ID", hierId);
			Node copyOfHierNd = hierNd.cloneNode(true);
			Element copyOfHierEle = (Element) copyOfHierNd;

			String codeCombinationflag4Lastnode = "DontCreateCodeCombination";
			String copyHierID=String.valueOf(Globals.getMaxID(HIERARCHY_XML_DIR, "Hierarchy_Config.xml","Hierarchy_Level_ID", "ID"));
			copyOfHierEle.setAttribute("Hierarchy_ID", copyHierID);
			copyOfHierEle.setAttribute("Hierarchy_Name", copyHierarchyName);
			copyOfHierEle.setAttribute("RI_Dependent_Hierarchies", "");
			copyOfHierEle.setAttribute("RI_Hierarchy_Type", "Independent");
			copyOfHierEle.setAttribute("RI_Reference_Hierarchy_ID", "");
			copyOfHierEle.setAttribute("RI_Reference_Hierarchy_Name", "");
			copyOfHierEle.setAttribute("RI_Validated", "");
			copyOfHierEle.setAttribute("Restore_Date", "");
			copyOfHierEle.setAttribute("Restored_From", "");
			copyOfHierEle.setAttribute("Was_Master", "");
			copyOfHierEle.setAttribute("Was_Master_End_Date", "");
			codeCombinationflag4Lastnode = copyOfHierEle.getAttribute("Code_Combination");
			nd.appendChild(copyOfHierEle);
			ReportsInvXMLTreeBean riBean=new ReportsInvXMLTreeBean();
			int key=0;
			NodeList hierNdList = copyOfHierEle.getChildNodes();
			for(int i=0;i<hierNdList.getLength();i++){
				if(hierNdList.item(i).getNodeType() == Node.ELEMENT_NODE){
					if(hierNdList.item(i).getNodeName().equals("RootLevel")){
						String rootLevelID=copyHierID;
						Element rootEle = (Element)hierNdList.item(i);
						rootEle.setAttribute("ID", rootLevelID);
						NodeList chNd = rootEle.getChildNodes();

						riBean.processChildNodes(2, hierNdList.item(i), doc, HIERARCHY_XML_DIR, rootLevelID,true);

						//					for(int j=0;j<chNd.getLength();j++){
						//						if(chNd.item(j).getNodeType() == Node.ELEMENT_NODE){
						//							if(chNd.item(j).getNodeName().equalsIgnoreCase("ID")){
						//								
						//							}
						//						}
						//					}

					}else if(hierNdList.item(i).getNodeName().equals("Fact_Tables")){
						Element factEle = (Element)hierNdList.item(i);
						factEle.setAttribute("ID", copyHierID);
						factEle.setAttribute("Gen_Mode", "");
						factEle.setAttribute("Previous_Gen_Type", "");
						factEle.setAttribute("Last_Generated_Time", "");
					}else if(hierNdList.item(i).getNodeName().equals("Segments")){
						Element factEle = (Element)hierNdList.item(i);
						factEle.setAttribute("ID", copyHierID);
					}else if(hierNdList.item(i).getNodeName().equals("Workflow")){
						Element factEle = (Element)hierNdList.item(i);
						factEle.setAttribute("Hierarchy_ID", copyHierID);
					}else if(hierNdList.item(i).getNodeName().equals("RI-Rules")){
						Element factEle = (Element)hierNdList.item(i);
						factEle.setAttribute("Hierarchy_ID", copyHierID);
					}
				}
			}
			Globals.writeXMLFile(doc, HIERARCHY_XML_DIR, HIERARCHY_XML_File);
			//		reGenerateHierarchy(copyHierID, codeCombinationflag4Lastnode,false,"");
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		System.out.println("Exiting: "
				+ new Exception().getStackTrace()[0].getClassName() + "."
				+ new Exception().getStackTrace()[0].getMethodName());
	}


	public void setEditButtonRendered(String Data){
		try{

			PropUtil prop = new PropUtil();
			String XMLFileName=prop.getProperty("HIERARCHY_XML_FILE");  // code change Menaka 20MAR2014
			String configFileName = prop.getProperty("HIERARCHY_CONFIG_FILE"); 
			Document doc=Globals.openXMLFile(HIERARCHY_XML_DIR, XMLFileName);	
			String levelID = selectedRows.get(0).getID();
			String nodeName = selectedRows.get(0).getLevelNode();				
			Node levelNode = Globals.getNodeByAttrVal(doc, nodeName, "ID", levelID);
			String Datatype = Globals.getAttrVal4AttrName(levelNode, "Data_SubType");
			if(Datatype.equalsIgnoreCase("DirectSQL") || Datatype.equalsIgnoreCase("fromData4DirectSQL")){
				renderEditbutton = "false";
				renderEditbutton1 = "true";
			}else{
				renderEditbutton = "true";
				renderEditbutton1 = "false";
			}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}



	public void addDatatoXml(){ //code change Jayaramu for add "data" to hierarchy xml
		try{
			FacesContext ctx = FacesContext.getCurrentInstance();
			ExternalContext extContext = ctx.getExternalContext();
			Map sessionMap = extContext.getSessionMap();
			HierarchyDBBean hdb = (HierarchyDBBean) sessionMap.get("hierarchyDBBean");
			System.out.println("hdb.codeandNameAL.size()====>"+hdb.codeandNameAL.size());
			if(!hdb.isDisplayAddDataBtn())//Start Code Change Gokul 07MAY2014
			{
				for(int i=0;i<hdb.codeandNameAL.size();i++){
					String segmentID= String.valueOf(Globals.getMaxID(HIERARCHY_XML_DIR, "Hierarchy_Config.xml", "Segments_ID", "ID"));

					HeirarchyDataBean hDB = (HeirarchyDataBean) hdb.codeandNameAL.get(i);
					setName(hDB.namevalue);
					setCode(hDB.codevalue);
					setType("Data");
					//		setSegmentNumber(segmentNumber);
					setSegmentID(segmentID);
					System.out.println("hDB.namevalue====>"+hDB.namevalue);
					System.out.println("hDB.codevalue====>"+hDB.codevalue);
					System.out.println("segmentID====>"+segmentID);
					System.out.println("hDB.overRideCodeCombinationFlag====>"+hDB.overRideCodeCombinationFlag);
					addHierarchyNodes("AdddataNode",hDB.overRideCodeCombinationFlag);
				}
			}//End Code Change Gokul 07MAY2014
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public void loadHierarchyTree(){
		//code change Jayaramu 20MAY2014 for load hierarchy tree.
		msg1 = "";
		Map<String, Object> viewMap = FacesContext.getCurrentInstance().getViewRoot().getViewMap();
		ReportsInvXMLTreeBean viewScopedBean = (ReportsInvXMLTreeBean) viewMap.get("reportsInvXMLTreeBean");
		viewScopedBean.getReportsForSession(hierarchyXmlFileName,hierarchy_ID,null,"FromHierTree");

	}

	public void DownloadAttachFile(String fileName, String type) {
		try {
			PropUtil prop = new PropUtil();
			String hierarchyDIR = prop.getProperty("HIERARCHY_XML_DIR");
			String configFileName=prop.getProperty("HIERARCHY_CONFIG_FILE");
			Document configdoc=Globals.openXMLFile(hierarchyDIR, configFileName);
			Element downLinkEle = (Element)configdoc.getElementsByTagName("Download_Link").item(0);
			String downLink = downLinkEle.getAttribute("URL").trim().isEmpty() ? "" : downLinkEle.getAttribute("URL").trim();
			String attachFilesEncode=URLEncoder.encode(fileName, "UTF-8");
			downloadURL = downLink.replace("$docName$", attachFilesEncode).replace("$docID$", document_ID).replace("$username$", "").replace("$downloadFrom$", "mail").replace("$filepath$", "").replace("$Type$", type);
			System.out.println("-=-=-downloadURL=-=-="+downloadURL);
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	public String getDownloadURL() {
		return downloadURL;
	}
	public void setDownloadURL(String downloadURL) {
		this.downloadURL = downloadURL;
	}

	public String getUserNameID() {
		return userNameID;
	}
	public void setUserNameID(String userNameID) {
		this.userNameID = userNameID;
	}
	public String getRedirectURL() {
		return redirectURL;
	}
	public void setRedirectURL(String redirectURL) {
		this.redirectURL = redirectURL;
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
	public boolean isAutoUpload() {
		return autoUpload;
	}
	public void setAutoUpload(boolean autoUpload) {
		this.autoUpload = autoUpload;
	}
	public String getUploadedFolder4Attachment() {
		return uploadedFolder4Attachment;
	}
	public void setUploadedFolder4Attachment(String uploadedFolder4Attachment) {
		this.uploadedFolder4Attachment = uploadedFolder4Attachment;
	}
	public String getDirname() {
		return dirname;
	}
	public void setDirname(String dirname) {
		this.dirname = dirname;
	}
	public String getRbid_metadata_dir() {
		return rbid_metadata_dir;
	}
	public void setRbid_metadata_dir(String rbid_metadata_dir) {
		this.rbid_metadata_dir = rbid_metadata_dir;
	}
	public String getBIMetadataFile() {
		return BIMetadataFile;
	}
	public void setBIMetadataFile(String bIMetadataFile) {
		BIMetadataFile = bIMetadataFile;
	}
	public String getBi_metadata_dir() {
		return bi_metadata_dir;
	}
	public void setBi_metadata_dir(String bi_metadata_dir) {
		this.bi_metadata_dir = bi_metadata_dir;
	}
	public String getChangeBTNpanelStr() {
		return changeBTNpanelStr;
	}
	public void setChangeBTNpanelStr(String changeBTNpanelStr) {
		this.changeBTNpanelStr = changeBTNpanelStr;
	}
	public String getStatusCode4DocsFlag() {
		return statusCode4DocsFlag;
	}
	public void setStatusCode4DocsFlag(String statusCode4DocsFlag) {
		this.statusCode4DocsFlag = statusCode4DocsFlag;
	}
	public String getSendPrimaryFileOnly() {
		return sendPrimaryFileOnly;
	}
	public void setSendPrimaryFileOnly(String sendPrimaryFileOnly) {
		this.sendPrimaryFileOnly = sendPrimaryFileOnly;
	}
	public String getAllow_Upload() {
		return allow_Upload;
	}
	public void setAllow_Upload(String allow_Upload) {
		this.allow_Upload = allow_Upload;
	}
	public String getDelete4DocsFlag() {
		return delete4DocsFlag;
	}
	public void setDelete4DocsFlag(String delete4DocsFlag) {
		this.delete4DocsFlag = delete4DocsFlag;
	}
	
	
	
	

}