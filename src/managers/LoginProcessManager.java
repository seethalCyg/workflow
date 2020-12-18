package managers;

import java.util.Hashtable;

import org.apache.commons.io.FilenameUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import utils.Globals;
import utils.Inventory;
import utils.PropUtil;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;



import utils.Globals;
import utils.PropUtil;

import beans.HeaderBean;
import beans.HierarchyBean;
import beans.HierarchyDBBean;
import beans.LoginBean;
import beans.MemberStatusBean;
import beans.SecurityBean;

import com.jniwrapper.win32.jexcel.Application;
import com.jniwrapper.win32.jexcel.ExcelException;
import com.jniwrapper.win32.jexcel.Range;
import com.jniwrapper.win32.jexcel.Worksheet;

import java.net.Authenticator;
import java.net.URLEncoder;
import java.util.Properties;
import java.util.TimeZone;

import javax.activation.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import javax.transaction.Synchronization;


public class LoginProcessManager {

	/**
	 * @param args
	 */


	//code change vishnu getLogin Detaails  
	public static Hashtable<String,String> getLoginDetails(String loginID) {
		Hashtable<String,String> attrHT = new Hashtable<>();
		String HierarchyDir="";
		String HierarchyloginXml="";
		try{
			PropUtil prop=new PropUtil();
			HierarchyDir=prop.getProperty("HIERARCHY_XML_DIR");
			HierarchyloginXml=prop.getProperty("LOGIN_XML_FILE");

			Document doc=Globals.openXMLFile(HierarchyDir, HierarchyloginXml);
			NodeList ndList1=doc.getElementsByTagName("Obiee_Users");
			NodeList ndList=ndList1.item(0).getChildNodes();
			for(int i=0;i<ndList.getLength();i++){
				if(ndList.item(i).getNodeType() == Node.ELEMENT_NODE && ndList.item(i).getNodeName().contains("User")){
					if(ndList.item(i).getTextContent().equalsIgnoreCase(loginID)){
						attrHT = Globals.getAttributeNameandValHT(ndList.item(i));   
						attrHT.put("Node_Name", ndList.item(i).getNodeName());
						attrHT.put("Login_ID", ndList.item(i).getTextContent());
						break;
					}
				}
			}		   

		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return attrHT;
	}
	
	public static Hashtable<String,String> getLoginDetailsById(String loginID, String accessId) {
		Hashtable<String,String> attrHT = new Hashtable<>();
		String HierarchyDir="";
		String HierarchyloginXml="";
		try{
			PropUtil prop=new PropUtil();
			HierarchyDir=prop.getProperty("HIERARCHY_XML_DIR");
			HierarchyloginXml=prop.getProperty("LOGIN_XML_FILE");

			Document doc=Globals.openXMLFile(HierarchyDir, HierarchyloginXml);
			NodeList ndList1=doc.getElementsByTagName("Obiee_Users");
			NodeList ndList=ndList1.item(0).getChildNodes();
			for(int i=0;i<ndList.getLength();i++){
				if(ndList.item(i).getNodeType() == Node.ELEMENT_NODE && ndList.item(i).getNodeName().contains("User")){
					String id = ((Element)ndList.item(i)).getAttribute("Access_Unique_ID");
					if(ndList.item(i).getTextContent().equalsIgnoreCase(loginID) && accessId.equalsIgnoreCase(id)){
						attrHT = Globals.getAttributeNameandValHT(ndList.item(i));   
						attrHT.put("Node_Name", ndList.item(i).getNodeName());
						attrHT.put("Login_ID", ndList.item(i).getTextContent());
						break;
					}
				}
			}		   

		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return attrHT;
	}
	
	public static Hashtable<String,String> getLoginDetails(String loginID, String custkey) {
		Hashtable<String,String> attrHT = new Hashtable<>();
		String HierarchyDir="";
		String HierarchyloginXml="";
		try{
			PropUtil prop=new PropUtil();
			HierarchyDir=prop.getProperty("HIERARCHY_XML_DIR");
			HierarchyloginXml=prop.getProperty("LOGIN_XML_FILE");

			Document doc=Globals.openXMLFile(HierarchyDir, HierarchyloginXml);
			NodeList ndList1=doc.getElementsByTagName("Obiee_Users");
			NodeList ndList=ndList1.item(0).getChildNodes();
			for(int i=0;i<ndList.getLength();i++){
				if(ndList.item(i).getNodeType() == Node.ELEMENT_NODE && ndList.item(i).getNodeName().contains("User")){
					String key = ((Element)ndList.item(i)).getAttribute("CustomerKey");
					if(ndList.item(i).getTextContent().equalsIgnoreCase(loginID) && key.equalsIgnoreCase(custkey)){
						attrHT = Globals.getAttributeNameandValHT(ndList.item(i));   
						attrHT.put("Node_Name", ndList.item(i).getNodeName());
						attrHT.put("Login_ID", ndList.item(i).getTextContent());
						break;
					}
				}
			}		   

		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return attrHT;
	}
	
	public static Hashtable<String,String> getCustomerDetailsByCustomerKey(String customerKey) {
		Hashtable<String,String> attrHT = new Hashtable<>();
		String HierarchyDir="";
		String HierarchyloginXml="";
		try{
			PropUtil prop=new PropUtil();
			HierarchyDir=prop.getProperty("HIERARCHY_XML_DIR");
			HierarchyloginXml=prop.getProperty("LOGIN_XML_FILE");

			Document doc=Globals.openXMLFile(HierarchyDir, HierarchyloginXml);
			NodeList ndList1=doc.getElementsByTagName("Obiee_Users");
			NodeList ndList=ndList1.item(0).getChildNodes();
			for(int i=0;i<ndList.getLength();i++){
				if(ndList.item(i).getNodeType() == Node.ELEMENT_NODE && ndList.item(i).getNodeName().contains("User")){
					Element ele = (Element)ndList.item(i);
					if(ele.getAttribute("CustomerKey").equals(customerKey) && ele.getAttribute("New_Customer").equalsIgnoreCase("true")){
						attrHT = Globals.getAttributeNameandValHT(ndList.item(i));   
						attrHT.put("Node_Name", ndList.item(i).getNodeName());
						attrHT.put("Login_ID", ndList.item(i).getTextContent());
					}
				}
			}		   

		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return attrHT;
	}
	
	public Hashtable getStageDetailsWF(String heirCode, String stageNo,String comingFrom)
	{
		System.out.println("Entering: " + new Exception().getStackTrace()[0].getClassName() + "."
				+ new Exception().getStackTrace()[0].getMethodName());
		Hashtable stagedetHT=new Hashtable<>();
		try{
			PropUtil prop=new PropUtil();
			String heirLevelXML=prop.getProperty("DOCUMENT_XML_FILE");
			String heirLeveldir=prop.getProperty("HIERARCHY_XML_DIR");
			Document xmlDoc=Globals.openXMLFile(heirLeveldir, heirLevelXML);
			Node workFlowN=Globals.getNodeByAttrVal(xmlDoc, "Workflow", "Hierarchy_ID", heirCode);

			stagedetHT = retriveStageDetailsFromXML(workFlowN, stageNo, comingFrom);

		}catch(Exception e)
		{
			e.printStackTrace();
		}

		System.out.println("Exiting: " + new Exception().getStackTrace()[0].getClassName() + "."
				+ new Exception().getStackTrace()[0].getMethodName());
		return stagedetHT;

		
	}
	//	public  void temp(){
	//		
	//		String hierID="1";
	//		String hierarchyName="SG & A";
	//		String LoginID="rbid";
	//		String processStatus="";
	//		String currentStageName="";
	//		String currentStageNo="";
	//		String totalNoStages="";
	//		String currentStageStatus="";
	//		String currentUserStatus="";
	//		String currentUserEmailID="";
	//		
	//		
	//		Hashtable LoginDetailsHT=getLoginDetails(LoginID);
	//		String accessType=(String)LoginDetailsHT.get("Access_Type");
	//		String accessendDate=(String)LoginDetailsHT.get("Access_End_Date");
	//		String AccessUniqID=(String)LoginDetailsHT.get("Access_Unique_ID");
	//		
	//		if(accessType.equals("Disabled")) {// if user disabled by admin user can not process						
	//		processStatus="Access Type : Disabled Please contact Adminstration";
	//		return;
	//		}else{
	//			try{ // check current Date and Accessing Date 
	//				PropUtil prop=new PropUtil();
	//		    	String	DateFormat=prop.getProperty("DATE_FORMAT");
	//				Date currentDateNow=new Date();
	//	    		SimpleDateFormat sdf = new SimpleDateFormat(DateFormat); 
	//	    		String currentformatDate=sdf.format(currentDateNow);
	//	    		
	//	        	Date accessEnddate = sdf.parse(accessendDate);  //1
	//	        	Date currentdate = sdf.parse(currentformatDate);	 //2
	//	        	
	//	        	if(currentdate.equals(accessEnddate)){
	//	        		System.out.println("Access End Date "+accessEnddate+ " Equals user Login Date "+currentdate);
	//	        	}else if(currentdate.before(accessEnddate)){
	//	        		System.out.println("user Login Date "+currentdate+"is before Access End Date "+accessEnddate);
	//	        	}else if(currentdate.after(accessEnddate)){
	//	        		System.out.println("user Login Date "+currentdate+"is After Access End Date "+accessEnddate);
	//	        		processStatus="Accessing End Date :"+accessendDate+" You cannot Process Please contact Adminstration";	 
	//	        		return;
	//	        	}
	//	        	
	//	        	Hashtable WFNodeDetailsHT=Globals.getWFNodeDetails(hierID,true,AccessUniqID);
	//	        	currentStageName=(String)WFNodeDetailsHT.get("Current_Stage_Name");
	//	        	currentStageNo=(String)WFNodeDetailsHT.get("Current_Stage_No");	   
	//	        	totalNoStages=(String)WFNodeDetailsHT.get("Total_No_Stages");	   
	//	        	currentStageStatus=(String)WFNodeDetailsHT.get("Current_Stage_Status");
	//	        	Hashtable UserDetailsHT=(Hashtable)WFNodeDetailsHT.get("UserDetailsHT");
	//	        	currentUserStatus=(String)UserDetailsHT.get("User_Status");
	//	        	currentUserEmailID=(String)UserDetailsHT.get("E-mail");	        	
	//	        	
	//	        	if(currentStageName.equals("Completed") && currentStageNo.equals("Completed")){
	//	        		processStatus="All the Stage are Closed. Please contact Adminstration";	 
	//	        		System.out.println(processStatus);
	//	        		return;
	//	        	}
	//	        	
	//	        	
	//	        	// check all process (Serial are parellel processing )
	//	        	Hashtable NextProcessHT=setStatusMsg4EMP(hierID,hierarchyName, currentStageNo, AccessUniqID, "",AccessUniqID,true);
	//	        	if(NextProcessHT.size()==0){
	//	        		processStatus="User Can not Process . Please contact Adminstration";	 
	//	        		System.out.println(processStatus);
	//	        		return;
	//	        	}else{
	//	        		Boolean loginProcess=(Boolean)NextProcessHT.get("LoginProcess");
	//		        	if(loginProcess){ // user can process to next
	//		        		processStatus="User can process Now ";
	//		        		
	//		        	}else{ // user cannot Process
	//		        		
	//		        		processStatus=(String)NextProcessHT.get("indicate");
	//		        		
	//		        	}
	//	        		
	//	        	}
	//	        	
	//	  
	//	        	
	//	 
	//	    	}catch(Exception ex){
	//	    		ex.printStackTrace();
	//	    	}
	//			
	//		}
	//		
	//		
	//		
	//		
	//	}


	public   void sendmailFromAdmin(Hashtable currentemplyeeDetailsHT,String subject,String bobyofMail,String hierlink4Mail,String mailSendBy, String mailPassword, String custKey) throws Exception {
		System.out.println("Entering: " + new Exception().getStackTrace()[0].getClassName() + "."
				+ new Exception().getStackTrace()[0].getMethodName());
		try {   
			ArrayList<String> tempAL = new ArrayList<>();
			String userMailAddress[] = null;
			int mailsend=0;
			String updateMail2DB = "";
			for (int mail = 0; mail < currentemplyeeDetailsHT.size(); mail++) {
				Hashtable mailApproversHT=new Hashtable();
				mailApproversHT=(Hashtable)currentemplyeeDetailsHT.get(mail);
				System.out.println("-=-=-=-mailApproversHT=-=-="+mailApproversHT);
				String usermailID=(String)mailApproversHT.get("E-mail");	
				String usrname=(String)mailApproversHT.get("empName");	
				String activeFlag = mailApproversHT.get("Active") == null || String.valueOf(mailApproversHT.get("Active")).isEmpty() ? "true" : String.valueOf(mailApproversHT.get("Active"));
				Hashtable<String, String> loginDetailsHT = getLoginDetails(usrname, custKey);
				String disableFlag = loginDetailsHT.get("Disable") == null ? "false" : loginDetailsHT.get("Disable");;
				if(activeFlag.equalsIgnoreCase("false") || disableFlag.equalsIgnoreCase("true"))
					continue;
				if(!tempAL.contains(usermailID))
				tempAL.add(usermailID);
				if(mailsend == 0){ //code change Jayaramu 10APR14
					updateMail2DB = usermailID;
				}else{
					updateMail2DB = usermailID+","+updateMail2DB;
				}
				mailsend++;
			}
			userMailAddress = tempAL.toArray(new String[tempAL.size()]);
			if(userMailAddress.length > 0)
				postMail(userMailAddress, subject, bobyofMail, mailSendBy,mailPassword, null, hierlink4Mail, custKey);
			/*FacesContext ctx1 = FacesContext.getCurrentInstance();
			ExternalContext extContext1 = ctx1.getExternalContext();
			Map sessionMap1 = extContext1.getSessionMap();
			HierarchyBean hryb = (HierarchyBean) sessionMap1.get("hierarchyBean");
			hryb.updateMailID(updateMail2DB);//code change Jayaramu 10APR14
			 */			
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("Exiting: " + new Exception().getStackTrace()[0].getClassName() + "."
				+ new Exception().getStackTrace()[0].getMethodName());

	}
	public   void sendmailFromAdminWithoutUI(Hashtable currentemplyeeDetailsHT,String subject,String bobyofMail,String hierlink4Mail,String mailSendBy, String mailPassword) throws Exception {
		System.out.println("Entering: " + new Exception().getStackTrace()[0].getClassName() + "."
				+ new Exception().getStackTrace()[0].getMethodName());
		try {   
			String custKey = "";
			String userMailAddress[] = new String[currentemplyeeDetailsHT.size()];
			int mailsend=0;
			String updateMail2DB = "";
			for (int mail = 0; mail < currentemplyeeDetailsHT.size(); mail++) {
				Hashtable mailApproversHT=new Hashtable();
				mailApproversHT=(Hashtable)currentemplyeeDetailsHT.get(mail);
				String usermailID=(String)mailApproversHT.get("E-mail");	
				String usrname=(String)mailApproversHT.get("empName");
				String id=(String)mailApproversHT.get("Access_Unique_ID");
//				String
				Hashtable testHT = getLoginDetailsById(usermailID, id);
				custKey = testHT.get("CustomerKey").toString();
				userMailAddress[mailsend]=usermailID;
				if(mailsend == 0){ //code change Jayaramu 10APR14
					updateMail2DB = usermailID;
				}else{
					updateMail2DB = usermailID+","+updateMail2DB;
				}
				mailsend++;
			}

			postMail(userMailAddress, subject, bobyofMail, mailSendBy,mailPassword, null, hierlink4Mail, custKey);

		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("Exiting: " + new Exception().getStackTrace()[0].getClassName() + "."
				+ new Exception().getStackTrace()[0].getMethodName());

	}


	public Hashtable getallStageStatus(String hierarchyID) throws Exception {
		System.out.println("Entering: " + new Exception().getStackTrace()[0].getClassName() + "."
				+ new Exception().getStackTrace()[0].getMethodName());

		Hashtable allstatusadminHT=new Hashtable();
		try {    

			PropUtil prop = new PropUtil();
			String heirLevelXML = prop.getProperty("HIERARCHY_XML_FILE");
			String heirLeveldir = prop.getProperty("HIERARCHY_XML_DIR");			
			Document xmlDoc = Globals.openXMLFile(heirLeveldir, heirLevelXML);
			Node workFlowN = Globals.getNodeByAttrVal(xmlDoc, "Workflow", "Hierarchy_ID", hierarchyID);
			Element workFlowNEL=(Element)workFlowN;
			String totStagestr=workFlowNEL.getAttribute("Total_No_Stages");
			int totstage=Integer.parseInt(totStagestr);
			allstatusadminHT.put("Total_No_Stages", totstage);
			int stage=1;
			for(int process=0; process<totstage; process++){
				Hashtable curstageDetailsHT=getStageDetails(hierarchyID, String.valueOf(stage),"");
				Hashtable curentmssgeDetailsHT=(Hashtable)curstageDetailsHT.get("MessagedetHT");
				String stageName=(String)curstageDetailsHT.get("Stage_Name");
				//					System.out.println("stageName->"+stageName);
				int statusnum=0;
				Hashtable allmsgHT=new Hashtable<>();
				for(int i=0;i<curentmssgeDetailsHT.size();i++){
					statusnum=i+1;
					String stmsg="";
					if(statusnum==curentmssgeDetailsHT.size()){
						stmsg=(String)curentmssgeDetailsHT.get("Final");
					}else{
						stmsg=(String)curentmssgeDetailsHT.get(String.valueOf(statusnum));
					}		

					//				  		 System.out.println("stmsg->"+stmsg);
					allmsgHT.put(i, stmsg);

				}
				allstatusadminHT.put(stageName, allmsgHT);
				allstatusadminHT.put(stage, stageName);


				stage++;
			}


		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("Exiting: " + new Exception().getStackTrace()[0].getClassName() + "."
				+ new Exception().getStackTrace()[0].getMethodName());
		return allstatusadminHT;
	}


	public   void setAdminNameInStage(Node curstageN,Document xmlDoc,String adminAccesID,String adminMail,String adminStatusmsg,String adminRole,String adminUsername,String reasonText4admin,String	DateFormat) throws Exception {
		System.out.println("Entering: " + new Exception().getStackTrace()[0].getClassName() + "."
				+ new Exception().getStackTrace()[0].getMethodName());
		try {  

			NodeList msgEnamePropNL = curstageN.getChildNodes();
			for (int i = 0; i < msgEnamePropNL.getLength(); i++) {
				Node checkN = msgEnamePropNL.item(i);
				if (checkN.getNodeType() == Node.ELEMENT_NODE) {
					if (checkN.getNodeName().equalsIgnoreCase("Employee_Names")) {

						Node nameN = Globals.getNodeByAttrValUnderParent(xmlDoc, checkN, "Access_Unique_ID", adminAccesID);
						if(nameN!=null){

							nameN.getParentNode().removeChild(nameN);
						}

						System.out.println("Admin set the value here : adminMail"+adminMail+"adminAccesID "+adminAccesID+"adminStatusmsg "+adminStatusmsg
								+" adminRole "+adminRole+" adminUsername "+adminUsername);

						Date lastAccDate = new Date();
						Format formatter = new SimpleDateFormat(DateFormat);
						String accdate = formatter.format(lastAccDate);

						Element nameE=xmlDoc.createElement("Name");
						nameE.setAttribute("E-mail",adminMail);
						nameE.setAttribute("Access_Unique_ID",adminAccesID);
						nameE.setAttribute("User_Status",adminStatusmsg);
						nameE.setAttribute("User_Role",adminRole);
						nameE.setAttribute("Last_Access", accdate);
						nameE.setAttribute("Notes", reasonText4admin);
						nameE.setTextContent(adminUsername);
						checkN.appendChild(nameE);

						break;
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("Exiting: " + new Exception().getStackTrace()[0].getClassName() + "."
				+ new Exception().getStackTrace()[0].getMethodName());

	}


	public   void setAdminStage(String hierarchyID,String hierarchyName,String curStageName,String adminMail,String adminAccesID,String adminStatusmsg,
			String adminRole,String adminUsername,String reasonText4admin) throws Exception {
		System.out.println("Entering: " + new Exception().getStackTrace()[0].getClassName() + "."
				+ new Exception().getStackTrace()[0].getMethodName());
		try {   


			FacesContext ctx1 = FacesContext.getCurrentInstance();
			ExternalContext extContext1 = ctx1.getExternalContext();
			Map sessionMap1 = extContext1.getSessionMap();
			MemberStatusBean mbrbn = (MemberStatusBean) sessionMap1.get("memberStatusBean");
			mbrbn.setMessage("");


			PropUtil prop = new PropUtil();
			String heirLevelXML = prop.getProperty("HIERARCHY_XML_FILE");
			String heirLeveldir = prop.getProperty("HIERARCHY_XML_DIR");
			String hierlink4Mail= prop.getProperty("HIERARCHY_BASE_URL");
			String	DateFormat=prop.getProperty("DATE_FORMAT");
			Document xmlDoc = Globals.openXMLFile(heirLeveldir, heirLevelXML);


			Node workFlowN = Globals.getNodeByAttrVal(xmlDoc, "Workflow", "Hierarchy_ID", hierarchyID);
			Node curstageN = Globals.getNodeByAttrValUnderParent(xmlDoc, workFlowN, "Stage_Name", String.valueOf(curStageName));
			setAdminNameInStage(curstageN,xmlDoc,adminAccesID,adminMail,adminStatusmsg,adminRole,adminUsername,reasonText4admin,DateFormat);
			Element docEle = (Element)workFlowN;
			///
			Element workFlowNEL=(Element)workFlowN;
			Element curstageNEL=(Element)curstageN;
			String  curStageNO=curstageNEL.getAttribute("Stage_No");
			Hashtable curstageDetailsHT=getStageDetails(hierarchyID, curStageNO,"");
			Hashtable currentemplyeeDetailsHT=(Hashtable)curstageDetailsHT.get("EmployeedetHT");
			Hashtable curentPropertiesHT=(Hashtable)curstageDetailsHT.get("PropertiesHT");
			Hashtable curentmssgeDetailsHT=(Hashtable)curstageDetailsHT.get("MessagedetHT");
			String finalMsg=(String)curentmssgeDetailsHT.get("Final");


			Hashtable mailDtailsHT=getMailDetailsFromConfig(xmlDoc,hierarchyID,curStageNO,adminUsername);
			String mailSendBy=(String)mailDtailsHT.get("Mail_ID_Send_by");
			String mailPassword=(String)mailDtailsHT.get("Mail_Password");
			String mailMessage=(String)mailDtailsHT.get("Mail_Message");
			String bobyofMail=mailMessage;
			//				String bobyofMail="Status From Admin:"+adminStatusmsg+" Please Login to the Heiarchy Genarator to View the Progress.This is an Auto Genarated Email Don't reply to this Mail-ID";
			String subject = "Hierarchy ID / Name : "+hierarchyID+" / "+ hierarchyName;

			Date lastAccDate=new Date();

			//check previousStage Details
			int curstage=Integer.parseInt(curStageNO);
			if(curstage==1){}else{

				if(adminStatusmsg.equals(finalMsg)){  // if last status set whole stage is completed


					int prestage=curstage-1;
					Node prestageN = Globals.getNodeByAttrValUnderParent(xmlDoc, workFlowN, "Stage_No", String.valueOf(prestage));
					Element prestageNEL=(Element)prestageN;
					String preStageStatus=prestageNEL.getAttribute("Stage_Status"); 
					String preStageName=prestageNEL.getAttribute("Stage_Name"); 
					if(!preStageStatus.equals("Completed")){							
						// return Error
						//						String str="Previews Stage :"+preStageName+" is Not Completed ("+preStageStatus+") Please Set Previews Stage Status ";

						// start code change pandian 19APR2014 

						int stagelevel=1;
						for(int process=0; process<prestage; process++){
							Node prestagelvN = Globals.getNodeByAttrValUnderParent(xmlDoc, workFlowN, "Stage_No", String.valueOf(stagelevel));
							Element prestagelvNEL=(Element)prestagelvN;
							String preStagelvStatus=prestagelvNEL.getAttribute("Stage_Status"); 
							String preStagelvName=prestagelvNEL.getAttribute("Stage_Name"); 
							if(!preStagelvStatus.equals("Completed")){

								Hashtable curstageDetailslvHT=getStageDetails(hierarchyID, String.valueOf(stagelevel),"");
								Hashtable currentemplyeeDetailslvHT=(Hashtable)curstageDetailslvHT.get("EmployeedetHT");
								//								Hashtable curentPropertieslvHT=(Hashtable)curstageDetailslvHT.get("PropertiesHT");
								//								Hashtable curentmssgeDetailslvHT=(Hashtable)curstageDetailslvHT.get("MessagedetHT");
								//								String finallvMsg=(String)curentmssgeDetailsHT.get("Final");

								//update admin node in that stage
								setAdminNameInStage(prestagelvN,xmlDoc,adminAccesID,adminMail,adminStatusmsg,adminRole,adminUsername,reasonText4admin,DateFormat);

								// send mail to not completed Stage all Employee in that Stage Member
								sendmailFromAdmin(currentemplyeeDetailslvHT,subject,bobyofMail,hierlink4Mail,mailSendBy,mailPassword, docEle.getAttribute("CustomerKey"));

								// set current stage msg mail and workflow stage name
								Format formatter = new SimpleDateFormat(DateFormat);							
								String accdate=formatter.format(lastAccDate);
								prestagelvNEL.setAttribute("Stage_Status", "Completed");
								prestagelvNEL.setAttribute("MailSent", "Yes");
								prestagelvNEL.setAttribute("Mail_Sent_Time", accdate);


							}

							stagelevel++;
						}


						//						FacesContext ctx1 = FacesContext.getCurrentInstance();
						//						ExternalContext extContext1 = ctx1.getExternalContext();
						//						Map sessionMap1 = extContext1.getSessionMap();
						//						MemberStatusBean mbrbn = (MemberStatusBean) sessionMap1.get("memberStatusBean");
						//						mbrbn.setMessage(str);					
						//						return;
					}

				} // end of ==> if(adminStatusmsg.equals(finalMsg)){  // if last status set whole stage is completed

			}

			String totStagestr=workFlowNEL.getAttribute("Total_No_Stages");
			int totstage=Integer.parseInt(totStagestr);

			if(adminStatusmsg.equals(finalMsg)){// admin set this stage is closed   completed

				// send mail to  current stage member
				sendmailFromAdmin(currentemplyeeDetailsHT,subject,bobyofMail,hierlink4Mail,mailSendBy,mailPassword, docEle.getAttribute("CustomerKey"));
				// set current stage msg mail and workflow stage name
				Format formatter = new SimpleDateFormat(DateFormat);
				//					 Date lastAccDate=new Date();
				String accdate=formatter.format(lastAccDate);
				curstageNEL.setAttribute("Stage_Status", "Completed");
				curstageNEL.setAttribute("MailSent", "Yes");
				curstageNEL.setAttribute("Mail_Sent_Time", accdate);



				// send mail to next stage member 
				if(curstage==totstage){
					workFlowNEL.setAttribute("Current_Stage_Name", curStageName);
					workFlowNEL.setAttribute("Current_Stage_No", "Completed");

				}else{
					int nxtstage=curstage+1;
					Hashtable nxtstageDetailsHT=getStageDetails(hierarchyID, String.valueOf(nxtstage),"");
					Hashtable nxtemplyeeDetailsHT=(Hashtable)nxtstageDetailsHT.get("EmployeedetHT");


					String nextStageName=(String)nxtstageDetailsHT.get("Stage_Name");
					String nextStageNo=(String)nxtstageDetailsHT.get("Stage_No");
					workFlowNEL.setAttribute("Current_Stage_Name", nextStageName);
					workFlowNEL.setAttribute("Current_Stage_No", nextStageNo);

					//serial  send first person alone 
					Hashtable nxtPropertiesHT=(Hashtable)nxtstageDetailsHT.get("PropertiesHT");
					String Properties=(String)nxtPropertiesHT.get("Properties");
					if(Properties.equals("Parallel")){  // send all the person
						//parellel
						sendmailFromAdmin(nxtemplyeeDetailsHT,subject,bobyofMail,hierlink4Mail,mailSendBy,mailPassword, docEle.getAttribute("CustomerKey"));

					}else{  // seroal send only one person

						Hashtable mssgeDetailsHT=(Hashtable)nxtstageDetailsHT.get("MessagedetHT");
						String finalMsg1=(String)mssgeDetailsHT.get("Final");
						sendmail2SerialProcess(xmlDoc,finalMsg1,nxtemplyeeDetailsHT,hierarchyID,hierarchyName,hierlink4Mail,String.valueOf(curstage), docEle.getAttribute("CustomerKey"));

					} 



				}

			}else { //send mail current stage alone  yts

				if(adminStatusmsg.equalsIgnoreCase("Rejected"))
				{
					//Do Rejection Configuration



					doRejectionConfiguration(xmlDoc, workFlowN, hierarchyID, curStageNO, adminUsername, curstageDetailsHT, curentmssgeDetailsHT, currentemplyeeDetailsHT, curentPropertiesHT,  adminAccesID, hierarchyName);

				}else{
					resetStageStatus(xmlDoc, curstageNEL, workFlowNEL, curStageName, curstage, totstage,
							currentemplyeeDetailsHT, subject, bobyofMail, hierlink4Mail, mailSendBy, mailPassword, workFlowNEL,true,"","","","YTS");
				}
			}

			Globals.writeXMLFile(xmlDoc, heirLeveldir, heirLevelXML);

			HierarchyBean hryb = (HierarchyBean) sessionMap1.get("hierarchyBean");

			if(currentemplyeeDetailsHT != null || !currentemplyeeDetailsHT.isEmpty()){

			}


			//Start code change Jayaramu 23APR14
			for(int i=0;i<currentemplyeeDetailsHT.size();i++){
				Hashtable currentemplyeeNameHT=(Hashtable)currentemplyeeDetailsHT.get(i);
				String userName = (String)currentemplyeeNameHT.get("empName");
				String userStatus = (String)currentemplyeeNameHT.get("User_Status");
				if(curStageName.equals(hryb.getCurrentStatgeName())){
					hryb.setCurrentprocessMember(userName);
					break;
				}
				else if(!userStatus.equals("Approved")){
					hryb.setCurrentprocessMember(userName);
					break;
				}

			}
			hryb.setCurrentStatgeName(curStageName);
			hryb.setStageStatus(adminStatusmsg);//End code change Jayaramu 23APR14
			//set
			mbrbn.setMessage("Updated Successfully");


		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("Exiting: " + new Exception().getStackTrace()[0].getClassName() + "."
				+ new Exception().getStackTrace()[0].getMethodName());

	}


	public void resetStageStatus(Document xmlDoc,Element curstageNEL,Element workFlowNEL,String curStageName,int curstage,int totstage,
			Hashtable currentemplyeeDetailsHT,String subject,String bobyofMail,String hierlink4Mail,String mailSendBy,String mailPassword,Node workFlowN,Boolean isAdmin,String affectedUser,String setStatus,String setTo,String adminStatusmsg){
		System.out.println("Entering: " + new Exception().getStackTrace()[0].getClassName() + "."
				+ new Exception().getStackTrace()[0].getMethodName());
		try{


			curstageNEL.setAttribute("Stage_Status","YTS");
			curstageNEL.setAttribute("MailSent", "");
			curstageNEL.setAttribute("Mail_Sent_Time", "");

			workFlowNEL.setAttribute("Current_Stage_Name", curStageName);
			workFlowNEL.setAttribute("Current_Stage_No", String.valueOf(curstage));
			Element docEle = (Element)workFlowNEL.getParentNode();
			if(curstage==totstage){

				if(isAdmin){
					sendmailFromAdmin(currentemplyeeDetailsHT,subject,bobyofMail,hierlink4Mail,mailSendBy,mailPassword, docEle.getAttribute("CustomerKey"));
				}
				setStageStatus(xmlDoc, curstageNEL,isAdmin,affectedUser,setStatus,setTo);

			}else{

				if(isAdmin){
					sendmailFromAdmin(currentemplyeeDetailsHT,subject,bobyofMail,hierlink4Mail,mailSendBy,mailPassword, docEle.getAttribute("CustomerKey"));
				}
				setStageStatus(xmlDoc, curstageNEL,isAdmin,affectedUser,setStatus,setTo);

				int stage=curstage;
				for(int process=curstage-1; process<totstage; process++){

					Node  isStageExists=Globals.getNodeByAttrValUnderParent(xmlDoc, workFlowN, "Stage_No", String.valueOf(stage));
					Element isStageExistsEL=(Element)isStageExists;
					String stageStatus=isStageExistsEL.getAttribute("Stage_Status");
					if(stageStatus.equals("Completed")){

						if(stage!=curstage){

							Hashtable processstageDetailsHT=retriveStageDetailsFromXML(workFlowNEL,  String.valueOf(stage),"");
							Hashtable processemplyeeDetailsHT=(Hashtable)processstageDetailsHT.get("EmployeedetHT");

							setStageStatus(xmlDoc, isStageExists,isAdmin,affectedUser,setStatus,setTo);
							if(isAdmin)
							{
								sendmailFromAdmin(processemplyeeDetailsHT,subject,bobyofMail,hierlink4Mail,mailSendBy,mailPassword, docEle.getAttribute("CustomerKey"));
							}

						}

					}



					stage++;
				}

			}





		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("Exiting: " + new Exception().getStackTrace()[0].getClassName() + "."
				+ new Exception().getStackTrace()[0].getMethodName());
	}



	public static void setStageStatus(Document xmlDoc, Node  isStageExists,Boolean isadmin,String affecUName,String Status,String setTo){
		System.out.println("Entering: " + new Exception().getStackTrace()[0].getClassName() + "."
				+ new Exception().getStackTrace()[0].getMethodName());	  
		try{

			//			Node  isStageExists=Globals.getNodeByAttrValUnderParent(xmlDoc, checkifWorkflowisExists, "Stage_No", currentStageNo);
			Element isStageExistsEL=(Element)isStageExists;
			if(isadmin)
			{
				isStageExistsEL.setAttribute("Stage_Status", "YTS");
			}else
			{
				isStageExistsEL.setAttribute("Stage_Status", "WIP");
			}
			isStageExistsEL.setAttribute("MailSent", "");
			isStageExistsEL.setAttribute("Mail_Sent_Time","");
			String stgName=isStageExistsEL.getAttribute("Stage_Name");

			NodeList prelist=isStageExists.getChildNodes();
			for (int m = 0; m < prelist.getLength(); m++) {
				Node precheckN = prelist.item(m);

				if (precheckN.getNodeType() == Node.ELEMENT_NODE) {
					if (precheckN.getNodeName().equalsIgnoreCase("Employee_Names")) {									
						NodeList preNL = precheckN.getChildNodes();
						for (int h = 0; h < preNL.getLength(); h++) {
							Node preNod = preNL.item(h);
							if (preNod.getNodeType() == Node.ELEMENT_NODE) {
								if (preNod.getNodeName().equalsIgnoreCase("Name")) {


									if(stgName.equals("Admin")){

									}else{
										Hashtable empHT=Globals.getAttributeNameandValHT(preNod);
										String role=(String)empHT.get("User_Role");
										if(role.equals("Admin")){
											continue;
										}
									}

									if(isadmin)
									{
										Element prenameE = (Element) preNod;
										prenameE.setAttribute("User_Status", "WIP");
									}
									else 
									{
										if(setTo.equalsIgnoreCase("all"))
										{
											Element prenameE = (Element) preNod;
											prenameE.setAttribute("User_Status", "WIP");
										}else{

											if(preNod.getTextContent().equalsIgnoreCase(affecUName))
											{
												Element prenameE = (Element) preNod;
												prenameE.setAttribute("User_Status",Status);
											}
										}
									}

								}
							}
						}

					}

				}

			}


		}catch (Exception e) {
			e.printStackTrace();
		}

		//		 System.out.println("Exiting: " + new Exception().getStackTrace()[0].getClassName() + "."
		//			            + new Exception().getStackTrace()[0].getMethodName());


	}


	public static String IsRejectionProceed(Hashtable memDetHT,Hashtable propHT,int addOnePerson)
	{
		String result="wait";
		try
		{
			String concurrency=(String)propHT.get("Concurrency");

			if(concurrency.equalsIgnoreCase("Serial"))
			{
				result="proceed";
			}else
			{
//				int minApprovers=Integer.parseInt((String)propHT.get("Min_Approvers"));
//				int totalMeminStage=memDetHT.size();
//				int maxRejectors=totalMeminStage-minApprovers;
//
//				int currentRejectCount=0;
//				for(int i=0;i<memDetHT.size();i++)
//				{
//					Hashtable indMemHT=(Hashtable)memDetHT.get(i);
//					String isrej=(String)indMemHT.get("User_Status");
//					if(isrej!=null)
//					{
//						if(isrej.equalsIgnoreCase("Rejected"))
//						{
//							currentRejectCount++;
//						}
//					}
//				}
//
//				currentRejectCount=currentRejectCount+addOnePerson;
//
//				System.out.println("maxRejectors : "+maxRejectors +" (<) "+currentRejectCount);
//				if(maxRejectors<currentRejectCount)
//				{
//					result="proceed";
//				}

				result="proceed";

			}


		}catch(Exception e)
		{
			e.printStackTrace();
		}

		return result;
	}

	public  Hashtable setStatusMsg4EMP(String hierarchyID,String hierarchyName, String stageNo, String empID, String reasonText,String statusMsg,String accessUniqIDcmnFrmLogin,Boolean cmngFromLogin) throws Exception {
		System.out.println("Entering: " + new Exception().getStackTrace()[0].getClassName() + "."

					+ new Exception().getStackTrace()[0].getMethodName());

		Hashtable NextProcessHT=new Hashtable<>();

		boolean issendback=false;

		try {
			PropUtil prop = new PropUtil();
			String heirLevelXML = prop.getProperty("HIERARCHY_XML_FILE");
			String heirLeveldir = prop.getProperty("HIERARCHY_XML_DIR");
			String hierlink4Mail= prop.getProperty("HIERARCHY_BASE_URL");

			String currentUserName="";


			Document xmlDoc = Globals.openXMLFile(heirLeveldir, heirLevelXML);
			if(!cmngFromLogin){
				Node workFlowN = Globals.getNodeByAttrVal(xmlDoc, "Workflow", "Hierarchy_ID", hierarchyID);
				Node stageN = Globals.getNodeByAttrValUnderParent(xmlDoc, workFlowN, "Stage_No", stageNo);
				NodeList msgEnamePropNL = stageN.getChildNodes();
				for (int i = 0; i < msgEnamePropNL.getLength(); i++) {
					Node checkN = msgEnamePropNL.item(i);
					if (checkN.getNodeType() == Node.ELEMENT_NODE) {
						if (checkN.getNodeName().equalsIgnoreCase("Employee_Names")) {

							Node nameN = Globals.getNodeByAttrValUnderParent(xmlDoc, checkN, "Access_Unique_ID", empID);
							if (nameN != null) {

								String	DateFormat=prop.getProperty("DATE_FORMAT");

								Date lastAccDate = new Date();
								Format formatter = new SimpleDateFormat(DateFormat);
								String accdate = formatter.format(lastAccDate);

								Element nameE = (Element) nameN;
								nameE.setAttribute("User_Status", statusMsg);
								nameE.setAttribute("Last_Access", accdate);
								nameE.setAttribute("Notes", reasonText);
								currentUserName=nameN.getTextContent();

								Element stageNELL=(Element)stageN;
								stageNELL.setAttribute("Stage_Status", "WIP");

								String isstatusAvaible=getFinalMsg(hierarchyID,statusMsg);



								// CODE CHANGE PANDIAN 21Mar2014 disable/enable icons
								if(statusMsg.equals("Rejected")){

									issendback=true;

									/*nameE.setAttribute("User_Status", "WIP");

							int currentstageno=Integer.parseInt(stageNo);
							int preStageNO=currentstageno-1;
							Node prevstageN = Globals.getNodeByAttrValUnderParent(xmlDoc, workFlowN, "Stage_No", String.valueOf(preStageNO));
							Element prevstageEL=(Element)prevstageN;
							prevstageEL.setAttribute("Stage_Status", "WIP");
							prevstageEL.setAttribute("MailSent", "");
							prevstageEL.setAttribute("Mail_Sent_Time","");

							String prevStageName=prevstageEL.getAttribute("Stage_Name");
							Element workFlowEL=(Element)workFlowN;
							workFlowEL.setAttribute("Current_Stage_Name", prevStageName);
							workFlowEL.setAttribute("Current_Stage_No", String.valueOf(preStageNO));



							NodeList prelist=prevstageN.getChildNodes();
							for (int m = 0; m < prelist.getLength(); m++) {
								Node precheckN = prelist.item(m);
								if (precheckN.getNodeType() == Node.ELEMENT_NODE) {
									if (precheckN.getNodeName().equalsIgnoreCase("Employee_Names")) {									
									NodeList preNL = precheckN.getChildNodes();
							for (int h = 0; h < preNL.getLength(); h++) {
								Node preNod = preNL.item(h);
								if (preNod.getNodeType() == Node.ELEMENT_NODE) {
									if (preNod.getNodeName().equalsIgnoreCase("Name")) {
										Element prenameE = (Element) preNod;
										prenameE.setAttribute("User_Status", "WIP");
									           }
									         }
								     	}

									}

								}

							}

							FacesContext ctx = FacesContext.getCurrentInstance();
							ExternalContext extContext = ctx.getExternalContext();
							Map sessionMap = extContext.getSessionMap();
							LoginBean lgnbn = (LoginBean) sessionMap.get("loginBean");
							String adminUsername=lgnbn.getUsername();
							 Hashtable mailDtailsHT=getMailDetailsFromConfig(xmlDoc,hierarchyID,stageNo,adminUsername);
							 String mailSendBy=(String)mailDtailsHT.get("Mail_ID_Send_by");
							 String mailPassword=(String)mailDtailsHT.get("Mail_Password");
							 String mailMessage=(String)mailDtailsHT.get("Mail_Message");
							 String bobyofMail=mailMessage;
//							String bobyofMail="Status :"+statusMsg+"Reason : "+reasonText+" Please Login to the Heiarchy Genarator to View the Progress.This is an Auto Genarated Email Don't reply to this Mail-ID";
							String subject = "Hierarchy ID / Name : "+hierarchyID+" / "+ hierarchyName;
							Hashtable nxtstageDetailsHT=getStageDetails(hierarchyID, String.valueOf(preStageNO));
							Hashtable nxtemplyeeDetailsHT=(Hashtable)nxtstageDetailsHT.get("EmployeedetHT");
									String userMailAddress[] = new String[nxtemplyeeDetailsHT.size()];
								int mailsend=0;
								String updateMail2DB = "";
								for (int mail = 0; mail < nxtemplyeeDetailsHT.size(); mail++) {
									Hashtable mailApproversHT=new Hashtable();
									mailApproversHT=(Hashtable)nxtemplyeeDetailsHT.get(mail);
									String usermailID=(String)mailApproversHT.get("E-mail");	
									String usrname=(String)mailApproversHT.get("empName");	
									userMailAddress[mailsend]=usermailID;
									if(mailsend == 0){ //code change Jayaramu 10APR14
										updateMail2DB = usermailID;
									}else{
										updateMail2DB = usermailID+","+updateMail2DB;
									}
									mailsend++;
								}

								postMail(userMailAddress, subject, bobyofMail, mailSendBy, mailPassword, null, hierlink4Mail);
								HierarchyBean hyb = new HierarchyBean();
								hyb.updateMailID(updateMail2DB);//code change Jayaramu 10APR14

									 */

									///////////////////////////////
									LoginProcessManager lgn=new LoginProcessManager();
									Hashtable stageDetailsHT =lgn.getStageDetails(hierarchyID,stageNo,"");
									Hashtable messagesHT =(Hashtable)stageDetailsHT.get("MessagedetHT");
									Hashtable empNMHT =(Hashtable)stageDetailsHT.get("EmployeedetHT");
									Hashtable propHT =(Hashtable)stageDetailsHT.get("PropertiesHT");
									System.out.println("empNMHT "+empNMHT);
									int addOnePerson=1;
									String triggerRejected=IsRejectionProceed(empNMHT,propHT,addOnePerson);
									if(triggerRejected.equals("proceed")){

										doRejectionConfiguration(xmlDoc, workFlowN, hierarchyID, stageNo, currentUserName, stageDetailsHT, messagesHT, empNMHT, propHT, empID, hierarchyName);
										//								Globals.writeXMLFile(xmlDoc, heirLeveldir, heirLevelXML);
									}







								}

								else if(isstatusAvaible.equals("true")){


									// code change pandian 28Mar2013

									//							send mail to next stage Reviewer
									//..
									FacesContext ctx = FacesContext.getCurrentInstance();
									ExternalContext extContext = ctx.getExternalContext();
									Map sessionMap = extContext.getSessionMap();
									LoginBean lgnbn = (LoginBean) sessionMap.get("loginBean");
									String adminUsername=lgnbn.getUsername();
									Hashtable mailDtailsHT=getMailDetailsFromConfig(xmlDoc,hierarchyID,stageNo,adminUsername);
									String mailSendBy=(String)mailDtailsHT.get("Mail_ID_Send_by");
									String mailPassword=(String)mailDtailsHT.get("Mail_Password");
									String mailMessage=(String)mailDtailsHT.get("Mail_Message");
									String bobyofMail=mailMessage;
									//							String bobyofMail="Please Login to the Heiarchy Genarator to View the Progress.This is an Auto Genarated Email Don't reply to this Mail-ID";
									String subject = "Hierarchy ID / Name : "+hierarchyID+" / "+ hierarchyName;
									Hashtable currentstageDetailsHT=getStageDetails(hierarchyID, String.valueOf(stageNo),"");
									Hashtable currentemplyeeDetailsHT=(Hashtable)currentstageDetailsHT.get("EmployeedetHT");
									Hashtable curentPropertiesHT=(Hashtable)currentstageDetailsHT.get("PropertiesHT");
									String Properties=(String)curentPropertiesHT.get("Properties");
									if(Properties.equals("Parallel")){  // send all the person
										String userMailAddress[] = new String[currentemplyeeDetailsHT.size()];
										int mailsend=0;
										Hashtable EmailHT=new Hashtable<>();
										String updateMail2DB = "";
										for (int mail = 0; mail < currentemplyeeDetailsHT.size(); mail++) {
											Hashtable mailApproversHT=new Hashtable();
											mailApproversHT=(Hashtable)currentemplyeeDetailsHT.get(mail);
											String usermailID=(String)mailApproversHT.get("E-mail");	
											String AccessUNiqID=(String)mailApproversHT.get("Access_Unique_ID");
											String UserStatus=(String)mailApproversHT.get("User_Status");
											String isstatusAvaiblee=getFinalMsg(hierarchyID,UserStatus);

											if(AccessUNiqID.equals(empID) || isstatusAvaiblee.equals("true")){


											}else{
												EmailHT.put(mailsend, usermailID);										
												mailsend++;

											}

										}

										userMailAddress = new String[EmailHT.size()];
										for (int mail = 0; mail < EmailHT.size(); mail++) {
											String usermailID=(String)EmailHT.get(mail);
											userMailAddress[mail]=usermailID;
											if(mail == 0){
												updateMail2DB = usermailID;
											}else{
												updateMail2DB = usermailID+","+updateMail2DB;
											}
										}
										if(userMailAddress.length>0){
											postMail(userMailAddress, subject, bobyofMail, mailSendBy, mailPassword, null, hierlink4Mail, ((Element)workFlowN).getAttribute("CustomerKey"));
											FacesContext ctx1 = FacesContext.getCurrentInstance();
											ExternalContext extContext1 = ctx1.getExternalContext();
											Map sessionMap1 = extContext1.getSessionMap();
											HierarchyBean hryb = (HierarchyBean) sessionMap1.get("hierarchyBean");
											hryb.updateMailID(updateMail2DB);//code change Jayaramu 11APR14

										}	
									}

									//							else{ // send only one person to next stage who is available on first (Serial)
									//								
									//								int mailsend=0;
									//								Hashtable EmailHT=new Hashtable<>();
									//								for (int mail = 0; mail < currentemplyeeDetailsHT.size(); mail++) {
									//									Hashtable mailApproversHT=new Hashtable();
									//									mailApproversHT=(Hashtable)currentemplyeeDetailsHT.get(mail);
									//									String usermailID=(String)mailApproversHT.get("E-mail");	
									//									String AccessUNiqID=(String)mailApproversHT.get("Access_Unique_ID");
									//									String UserStatus=(String)mailApproversHT.get("User_Status");
									//									if(AccessUNiqID.equals(empID) || UserStatus.equals("Completed") || UserStatus.equals("Approved")){
									//										
									//									}else{
									//										EmailHT.put(mailsend, usermailID);										
									//										mailsend++;
									//										break;
									//									}
									//									
									//								}
									//								
									//								 String userMailAddress[] = new String[EmailHT.size()];
									//								for (int mail = 0; mail < EmailHT.size(); mail++) {
									//									String usermailID=(String)EmailHT.get(mail);
									//									userMailAddress[mail]=usermailID;
									//								}
									//							
									//								
									//							
									//									
									//								postMail(userMailAddress, subject, bobyofMail, "accelbi@cygnussoftwares.com","acbi$2014", null, hierlink4Mail);
									//								
									//								
									//							}

								}

								System.out.println("update Last Accessed Date : " + accdate);
								Globals.writeXMLFile(xmlDoc, heirLeveldir, heirLevelXML);


							} else{
								NextProcessHT.put("ERROR", "Employee is not available in This Stage");
								return NextProcessHT;
							}
						}

					}

				}
			}

			if(!issendback){
				xmlDoc = Globals.openXMLFile(heirLeveldir, heirLevelXML);
				NextProcessHT=workFlowNCheckAccessUser(xmlDoc, hierarchyID,hierarchyName,stageNo,cmngFromLogin,accessUniqIDcmnFrmLogin,heirLeveldir, heirLevelXML);
				System.out.println("current Stage Processing  HT :"+NextProcessHT);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("Exiting: " + new Exception().getStackTrace()[0].getClassName() + "."
				+ new Exception().getStackTrace()[0].getMethodName());

		return NextProcessHT;

	}

	public  Hashtable getMailDetailsFromConfig(Document levelXmlDoc,String hierarchyID,String currentStageNo,String userloginName){

		System.out.println("Entering: " + new Exception().getStackTrace()[0].getClassName() + "."
				+ new Exception().getStackTrace()[0].getMethodName());

		Hashtable mailDtailsHT=new Hashtable(); 
		try{
			PropUtil prop = new PropUtil();
			String hierarchyDIR = prop.getProperty("HIERARCHY_XML_DIR");
			String configFileName=prop.getProperty("HIERARCHY_CONFIG_FILE");

			String mailSendBy="";
			String mailPassword="";
			String mailMessage="";
			Document configdoc=Globals.openXMLFile(hierarchyDIR, configFileName);
			NodeList ndlist=configdoc.getElementsByTagName("Mail_Configuration");
			Node nd=ndlist.item(0);
			NodeList ndlist1=nd.getChildNodes();

			for(int i=0;i<ndlist1.getLength();i++){
				Node nd1=ndlist1.item(i);
				if(nd1.getNodeType()==Node.ELEMENT_NODE && nd1.getNodeName().equalsIgnoreCase("Mail_ID_Send_by")){
					mailSendBy=nd1.getTextContent();
					mailDtailsHT.put("Mail_ID_Send_by", mailSendBy);

				}
				if(nd1.getNodeType()==Node.ELEMENT_NODE && nd1.getNodeName().equalsIgnoreCase("Mail_Password")){
					mailPassword=nd1.getTextContent();
					mailDtailsHT.put("Mail_Password", mailPassword);
				}
				if(nd1.getNodeType()==Node.ELEMENT_NODE && nd1.getNodeName().equalsIgnoreCase("Mail_Message")){
					mailMessage=nd1.getTextContent();
					mailDtailsHT.put("Mail_Message", mailMessage);
				}
			}


			//				 Please see the bellow Details here for workfloe Prograss
			//		         From  Stage   : $FromStage$   TO Stage   : $TOStage$
			//		         From  Status  : $FromStatu$   TO Status  : $TOStatus$
			//		         Last Access Member : $LastAccessMember$
			//		         Last Access Member EmailID : $LastAccessMemberEmailID$                 
			//		         Last Member Role : $LastMemberRole$
			//		         Last Member Status : $LastMemberStatus$
			//		         Notes : $Notes$


			String FromStage="";
			String FromStatu="";
			String TOStage="";
			String TOStatus="";
			String LastAccessMember="";
			String LastAccessMemberEmailID="";
			String LastMemberRole="";
			String LastMemberStatus="";
			String Notes="";
			String nextStageNo="";

			String levelXml=prop.getProperty("HIERARCHY_XML_FILE");
			//				 Document levelXmlDoc=Globals.openXMLFile(hierarchyDIR, levelXml);
			Node workFlowN = Globals.getNodeByAttrVal(levelXmlDoc, "Workflow", "Hierarchy_ID", hierarchyID);

			mailDtailsHT = getMailDetailsFromConfig(levelXmlDoc, workFlowN, currentStageNo, userloginName, "", "", false, "");




		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("Exiting: " + new Exception().getStackTrace()[0].getClassName() + "."
				+ new Exception().getStackTrace()[0].getMethodName());

		return mailDtailsHT;

	}


	public  Hashtable getMailDetailsFromConfig(Document levelXmlDoc,Node workFlowN,String currentStageNo,String userloginName, String actionName, 
			String mailTo, Boolean nextTeamFlag, String attchFileName){

		System.out.println("Entering: " + new Exception().getStackTrace()[0].getClassName() + "."
				+ new Exception().getStackTrace()[0].getMethodName());

		Hashtable mailDtailsHT=new Hashtable(); 
		try{
			PropUtil prop = new PropUtil();
			String hierarchyDIR = prop.getProperty("HIERARCHY_XML_DIR");
			String configFileName=prop.getProperty("HIERARCHY_CONFIG_FILE");
			String templateDir=prop.getProperty("TEMPLATE_DIR");
			String timeFormat = prop.getProperty("DATE_FORMAT");
			String timeFormatzone = prop.getProperty("DATE_FORMAT_ZONE");
			SimpleDateFormat sdf = new SimpleDateFormat(timeFormatzone);
			if(actionName.equalsIgnoreCase("Approved") || actionName.equalsIgnoreCase("Approve"))
				actionName = "Approve";
			if(actionName.equalsIgnoreCase("Initiate"))
				actionName = "RequestApproval";
			String mailSendBy="";
			String mailPassword="";
			String mailMessage="";
			String subject = "";
			String resubmitUser="";
			String resubmitFileID="";
			String hideshowType="block";
			String bodyTEXThideshowType="";
			//String createdMember="";
			// String esign_str= ((Element) workFlowN.getParentNode()).getAttribute("Esign_configure");
			Element docEle = (Element)workFlowN.getParentNode();
			String document_ID = docEle.getAttribute("Document_ID");
			String ultratemplate_Name = docEle.getAttribute("Template_Name");
			String ultratemplate_FolderName = docEle.getAttribute("Template_FolderName");
			String esign_configure= docEle.getAttribute("Esign_configure")  == null ? "False" : docEle.getAttribute("Esign_configure").toString();
			String ultratemplate_Descripter = docEle.getAttribute("Template_Descriptor");
			String createdUser = docEle.getAttribute("Created_By");
			String customerKey= docEle.getAttribute("CustomerKey");
			Element workFlowNEL=(Element)workFlowN;
			String totalStage=workFlowNEL.getAttribute("Total_No_Stages");
			String workflow_Type=workFlowNEL.getAttribute("Workflow_Type") == null ? "" : workFlowNEL.getAttribute("Workflow_Type").toString();
			
			int tottalst=Integer.parseInt(totalStage);
			int curst=Integer.parseInt(currentStageNo);
			LoginProcessManager lgnpro=new LoginProcessManager();
			//
			Hashtable stageDetailsHT=lgnpro.retriveStageDetailsFromXML(workFlowN, currentStageNo,"");
			String externalUserFlag = stageDetailsHT.get("ExternalUser") == null ? "" : stageDetailsHT.get("ExternalUser").toString();
			String autoLoginFlag = stageDetailsHT.get("Auto_Login") == null ? "" : stageDetailsHT.get("Auto_Login").toString();
			Hashtable mssgeDetailsHT=(Hashtable)stageDetailsHT.get("MessagedetHT");
			String finalMsg=(String)mssgeDetailsHT.get("Final");
			Hashtable currentemplyeeDetailsHT=(Hashtable)stageDetailsHT.get("EmployeedetHT");
			
			Document configdoc=Globals.openXMLFile(hierarchyDIR, configFileName);
			System.out.println(userloginName+"^^^^^^^^^^^hierarchyDIR+configFileName^^^^^^^^^^^^^^^^^^^^^^^^^^^^^"+hierarchyDIR+configFileName);
			NodeList ndlist=configdoc.getElementsByTagName("Mail_Configuration");
			Node nd=ndlist.item(0);
			NodeList ndlist1=nd.getChildNodes();
			String oldMessage = "";
			String resubmitValue = "";
			String resubmitLatestFileName = FilenameUtils.removeExtension(attchFileName);
			String attchFileNamewithextension="";
			String attchFirstPerson="";
			String stageLUserName="";	
			String stageLUserFirstName="";	
			String	resubmitUser1="";
			String strmailLength1="";
			boolean attachStageEmailbol=false;
			boolean htmlFileFlag = false;
			String curstgNo="";String nxtStgNo="";
			if(tottalst==curst){	
				curstgNo=String.valueOf(tottalst);
				nxtStgNo="Completed";
			}else {							
				curstgNo=String.valueOf(curst);
				int nxtstg=curst+1;
				nxtStgNo=String.valueOf(nxtstg);						
			}
			
			String nextStageNo = "";
			ArrayList<String> userAL = new ArrayList<>();
			if(actionName.equalsIgnoreCase("Rejected")) {
				Hashtable nextStageDetailsHT = WorkflowManager.rejectNextUsers(levelXmlDoc, workFlowNEL, currentStageNo, userloginName);
				if(nextStageDetailsHT != null && nextStageDetailsHT.get("StageNo") != null && !nextStageDetailsHT.get("StageNo").toString().equalsIgnoreCase("Rules Failed")) {
					nextStageNo = nextStageDetailsHT.get("StageNo").toString();
					userAL = nextStageDetailsHT.get("Users") == null ? new ArrayList<>() : (ArrayList)nextStageDetailsHT.get("Users");
				}else {
					nextStageNo = nxtStgNo;
				}
			}else if(actionName.equalsIgnoreCase("Attach")){
				nextStageNo = "1";
				Hashtable nxtstageDetailsHT=lgnpro.retriveStageDetailsFromXML(workFlowN, nextStageNo,"");
				Hashtable nxtemplyeeDetailsHT=(Hashtable)nxtstageDetailsHT.get("EmployeedetHT");
				Hashtable allPropertiesHT=(Hashtable)nxtstageDetailsHT.get("PropertiesHT");
				Hashtable allmssgeDetailsHT=(Hashtable)nxtstageDetailsHT.get("MessagedetHT");
				String Properties=(String)allPropertiesHT.get("Properties");
				String allfinalMsg=(String)allmssgeDetailsHT.get("Final");
				for (int mail = 0; mail < nxtemplyeeDetailsHT.size(); mail++) {
					Hashtable mailApproversHT=new Hashtable();
					mailApproversHT=(Hashtable)nxtemplyeeDetailsHT.get(mail);
					String usermailID=(String)mailApproversHT.get("E-mail");	
					String userStatus=(String)mailApproversHT.get("User_Status");
					if(Properties.equalsIgnoreCase("Serial")) {
						if(!userStatus.equalsIgnoreCase(allfinalMsg)) {
							userAL.add(usermailID);
							break;
						}
					}else {
						if(!userStatus.equalsIgnoreCase(allfinalMsg)) {
							userAL.add(usermailID);
//							break;
						}
					}
				}
			}else {
				Hashtable nextStageDetailsHT = WorkflowManager.getNextDocumentUsers(levelXmlDoc, workFlowNEL, currentStageNo, userloginName);//(levelXmlDoc, workFlowNEL, currentStageNo);
				if(nextStageDetailsHT != null && nextStageDetailsHT.get("StageNo") != null && !nextStageDetailsHT.get("StageNo").toString().equalsIgnoreCase("Rules Failed")) {
					nextStageNo = nextStageDetailsHT.get("StageNo").toString();
					userAL = nextStageDetailsHT.get("Users") == null ? new ArrayList<>() : (ArrayList)nextStageDetailsHT.get("Users");
				}else {
					nextStageNo = nxtStgNo;
				}
			}
			Hashtable stageDetailsHT2=lgnpro.retriveStageDetailsFromXML(workFlowN, nextStageNo,"");
			Boolean checkesignCf=stageDetailsHT2.get("IsESignConfigured") == null ? false : Boolean.valueOf(stageDetailsHT2.get("IsESignConfigured").toString());
			Boolean allowUDEdit=stageDetailsHT2.get("AllowUltraDocEdit") == null ? false : Boolean.valueOf(stageDetailsHT2.get("AllowUltraDocEdit").toString());
			String docId = docEle.getAttribute("Document_ID");
			Hashtable tempHT1 = FormsManager.getTemplateNdFromDocId(docId);
			Node templateNd = tempHT1 == null ? null : (Node)tempHT1.get("TemplateNode");
			System.out.println(tempHT1+"----------tempHT1-----checkesignCf-----dsfsdfdsf---------- "+templateNd);
			
			System.out.println("xxxxxxxxxxxxxxxxxxxcheckesignCfxxxxxxxxxxxxxxxx"+checkesignCf);
			Element templateEle = (Element) templateNd;
			boolean flag = false;
			if(templateEle != null) {
				Element esignEle = templateEle.getElementsByTagName("ESign_Details").getLength() <= 0 ? null : (Element)templateEle.getElementsByTagName("ESign_Details").item(0);
				
				if(esignEle != null) {
					String metadataFilePath = esignEle.getAttribute("MetadataFilePath");

					System.out.println(metadataFilePath+"----------sdfsd-----checkesignCf-----dsfsdfdsf---------- "+userAL);
					flag = FormsManager.checkEsignConfigured4User(metadataFilePath, userAL, nextStageNo);
				}
			}
			Hashtable testHT = WorkflowManager.getNextDocumentUsers(levelXmlDoc, workFlowN, currentStageNo, userloginName);
			String nxtStageNo = testHT == null || testHT.get("StageNo") == null ? "" : (String)testHT.get("StageNo");
			//String enableEsignStgLevel = "";
			Hashtable<String, String> tempHT = WorkflowManager.checkEnableEsignInStageLevel(document_ID);
			String enableEsignFlag = tempHT == null || tempHT.get("EsignFlag") == null ? "" : tempHT.get("EsignFlag");
			String enableEsignStgNo = tempHT == null || tempHT.get("EsignStageNo") == null ? "" : tempHT.get("EsignStageNo");
			for(int i=0;i<ndlist1.getLength();i++){
				Node nd1=ndlist1.item(i);
				if(nd1.getNodeType()==Node.ELEMENT_NODE && nd1.getNodeName().equalsIgnoreCase("Mail_ID_Send_by")){
					mailSendBy=nd1.getTextContent();
					mailDtailsHT.put("Mail_ID_Send_by", mailSendBy);

				}
				if(nd1.getNodeType()==Node.ELEMENT_NODE && nd1.getNodeName().equalsIgnoreCase("Mail_Password")){
					mailPassword=nd1.getTextContent();
					mailDtailsHT.put("Mail_Password", mailPassword);
				}
				if(nd1.getNodeType()==Node.ELEMENT_NODE && nd1.getNodeName().equalsIgnoreCase("Mail_Message")){
					Element ele = (Element)nd1;

					if(ele.getAttribute("Type").isEmpty())
						oldMessage=nd1.getTextContent();
					if(actionName.equalsIgnoreCase(ele.getAttribute("Type")) && mailTo.equalsIgnoreCase(ele.getAttribute("To"))) {
						mailMessage=nd1.getTextContent();
						if(!ele.getAttribute("HTMLFileName").isEmpty() && new File(templateDir+ele.getAttribute("HTMLFileName")).isFile()) {
							String fileNme = ele.getAttribute("HTMLFileName");
							System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx"+fileNme);
							
							if(!attchFileName.equalsIgnoreCase("") && attchFileName !=null) {
								
							if(fileNme.equalsIgnoreCase("Resubmit.html")) {
								
								resubmitValue="Resubmit";
								resubmitUser= getattachmentUser(levelXmlDoc,document_ID,attchFileName);
								attchFileNamewithextension=FilenameUtils.removeExtension(attchFileName);
								
								
								
							}
							else if(fileNme.equalsIgnoreCase("Resubmit-Success.html")) {
								
								
								resubmitUser= getattachmentUser(levelXmlDoc,document_ID,attchFileName);
								System.out.println("xxxxxxxxxxxxxxxxxxxresubmitUserxxxxxxxxxxxxxxxx"+resubmitUser);
							
								
							}
							
							

							
							
							
							}

							
							resubmitUser1 = getFristLastName(resubmitUser, customerKey);
							if(fileNme.equalsIgnoreCase("Attach.html")) {
								
								System.out.println("xxxxxxxxxxxxxxxxxxxrfileNmexxxxssssxxxxxxxxxxx"+fileNme);
								attachStageEmailbol=true;
								Node stageN = Globals.getNodeByAttrValUnderParent(levelXmlDoc, workFlowN, "Stage_No", currentStageNo);
								NodeList msgEnamePropNL = stageN.getChildNodes();
								System.out.println("**********************resubmitUser*******currentStageNo******************************"+currentStageNo);
								String documentName = docEle.getAttribute("DocumentName");
							
								
								for (int ih = 0; ih < msgEnamePropNL.getLength(); ih++) {
									Node propertiesNode = msgEnamePropNL.item(ih);
									if (propertiesNode.getNodeType() == Node.ELEMENT_NODE) {
										if (propertiesNode.getNodeName().equalsIgnoreCase("Properties")) {

											
											Hashtable propHT=(Hashtable)Globals.getAttributeNameandValHT(propertiesNode);
											String propertiesValue=(String)propHT.get("Concurrency");
											 if(propertiesValue != null && propertiesValue.equals("Serial")){
												
												Hashtable stageDetailsHT1=retriveStageDetailsFromXML(workFlowN, currentStageNo,"");
												Hashtable emplyeeDetailsHT=(Hashtable)stageDetailsHT1.get("EmployeedetHT");
												
												for (int approv = 0; approv < emplyeeDetailsHT.size(); approv++) {
													
													Hashtable ApproversHT=new Hashtable();
													ApproversHT=(Hashtable)emplyeeDetailsHT.get(approv);
													stageLUserName=(String)ApproversHT.get("E-mail");
													System.out.println("**********************%%%%%*******stageLUserName******************************"+stageLUserName);
													stageLUserName=getFristLastName(stageLUserName, customerKey);
													break;
												
												}
												
												
											}else if(propertiesValue != null && propertiesValue.equals("Parallel")){
												
												Hashtable stageDetailsHT1=retriveStageDetailsFromXML(workFlowN, currentStageNo,"");
												Hashtable emplyeeDetailsHT=(Hashtable)stageDetailsHT1.get("EmployeedetHT");
												String strmailLength="";
												String strmailLength2="";
												for (int approv = 0; approv < emplyeeDetailsHT.size(); approv++) {
													Hashtable ApproversHT=new Hashtable();
													ApproversHT=(Hashtable)emplyeeDetailsHT.get(approv);
													stageLUserName=(String)ApproversHT.get("E-mail");
													
													
													
													strmailLength1=getFristLastName(stageLUserName, customerKey);
													
													if(strmailLength2.length()==0) {
														
														
														strmailLength2=strmailLength1;
														System.out.println("strmailLength21111111111111111111 :"+strmailLength2);
													
													}else {
														
														strmailLength2=strmailLength2+ ", " +strmailLength1;
														System.out.println("strmailLength2222222222222 :"+strmailLength2);
													}
													
													
													
													}
												
												stageLUserName=strmailLength2;
												
										//	stageLUserFirstName=stageLUserName;
											}
											
											
										}
									}
								}
								
							
								
							}
							
							System.out.println(stageLUserName+"**********************EEEEEEEEEEE*******resubmitUser******************************"+enableEsignStgNo);
							System.out.println(enableEsignFlag+"**********************EEEEEEEEEEE*******enableEsignStgNo***********"+flag+"*******************"+nxtStageNo);
							if(enableEsignFlag.equalsIgnoreCase("true") && flag) {
								if(Globals.checkStringIsNumber(nxtStageNo) && Globals.checkStringIsNumber(enableEsignStgNo)) {
									int currStg = Integer.valueOf(nxtStageNo);
									int esignStgNo = Integer.valueOf(enableEsignStgNo);
									if(currStg <= esignStgNo) {
										checkesignCf = false;
									}else {
												fileNme = fileNme.substring(0, fileNme.lastIndexOf(".")).trim();
												fileNme= fileNme+"-esign.html";
												checkesignCf = true;
									}
								}else {
										if(checkesignCf) {

											fileNme = fileNme.substring(0, fileNme.lastIndexOf(".")).trim();
											fileNme= fileNme+"-esign.html";
										}

								}
							}else {

									if(checkesignCf && flag) {

										fileNme = fileNme.substring(0, fileNme.lastIndexOf(".")).trim();
										fileNme= fileNme+"-esign.html";
									}
							}
							
													
							
							if((actionName.equalsIgnoreCase("Approve") || actionName.equalsIgnoreCase("Completed") || actionName.equalsIgnoreCase("RequestApproval")) && externalUserFlag.equalsIgnoreCase("true"))
								
								fileNme = "External Approved.html";
							
							System.out.println("-=-=-=-=-=-=-=-=-="+fileNme);
							BufferedReader buffReader = new BufferedReader(new FileReader(new File(templateDir+fileNme)));
							StringBuilder sb = new StringBuilder();
						    String line = buffReader.readLine();
						    while (line != null) {
						      sb.append(line).append("\n");
						      line = buffReader.readLine();
						    }
							mailMessage = sb.toString();
							htmlFileFlag = true;
						}
						mailDtailsHT.put("Mail_Message", mailMessage);
						subject = ele.getAttribute("Subject");
						mailDtailsHT.put("Subject", ele.getAttribute("Subject"));
					}
				}
			}
			if(mailDtailsHT.get("Mail_Message") == null || String.valueOf(mailDtailsHT.get("Mail_Message")).isEmpty()) {
				mailMessage = oldMessage;
				mailDtailsHT.put("Mail_Message", oldMessage);
			}
			

			//				 Please see the bellow Details here for workfloe Prograss
			//		         From  Stage   : $FromStage$   TO Stage   : $TOStage$
			//		         From  Status  : $FromStatu$   TO Status  : $TOStatus$
			//		         Last Access Member : $LastAccessMember$
			//		         Last Access Member EmailID : $LastAccessMemberEmailID$                 
			//		         Last Member Role : $LastMemberRole$
			//		         Last Member Status : $LastMemberStatus$
			//		         Notes : $Notes$


			String FromStage="";
			String FromStatu="";
			String TOStage="";
			String TOStatus="";
			String LastAccessMember="";
			String LastAccessMemberEmailID="";
			String LastMemberRole="";
			String LastMemberStatus="";
			String Notes="";
			


			//				 Document levelXmlDoc=Globals.openXMLFile(hierarchyDIR, levelXml);
			//			Node workFlowN = Globals.getNodeByAttrVal(levelXmlDoc, "Workflow", "Hierarchy_ID", hierarchyID);
			

			Node prevstageN = Globals.getNodeByAttrValUnderParent(levelXmlDoc, workFlowN, "Stage_No", currentStageNo);	

			System.out.println("Current Stage Number "+currentStageNo);
			Element prevstageNEL=(Element)prevstageN;
			String stgName=prevstageNEL.getAttribute("Stage_Name");
			NodeList prelist=prevstageN.getChildNodes();
			for (int m = 0; m < prelist.getLength(); m++) {
				Node precheckN = prelist.item(m);
				if (precheckN.getNodeType() == Node.ELEMENT_NODE) {
					if (precheckN.getNodeName().equalsIgnoreCase("Employee_Names")) {									
						NodeList preNL = precheckN.getChildNodes();
						for (int h = 0; h < preNL.getLength(); h++) {
							Node preNod = preNL.item(h);
							if (preNod.getNodeType() == Node.ELEMENT_NODE) {
								if (preNod.getNodeName().equalsIgnoreCase("Name")) {
									Hashtable empHT=Globals.getAttributeNameandValHT(preNod);
									System.out.println(empHT +"empHT "+preNod);

									String empName=preNod.getTextContent();
									if(empName.equals(userloginName)){

										LastAccessMember=empName;
										LastAccessMemberEmailID=(String)empHT.get("E-mail");	;
										LastMemberRole=(String)empHT.get("User_Role");
										LastMemberStatus=(String)empHT.get("User_Status");	
										Notes=(String)empHT.get("Notes");								
										break;
										
									}

								}
							}
						}

					}

				}
				
			}

			if(LastAccessMember==null){LastAccessMember="";};
			if(LastMemberRole==null){LastMemberRole="";};
			if(LastMemberStatus==null){LastMemberStatus="";};
			if(Notes==null){Notes="";};
			if(LastAccessMember.isEmpty())
				LastAccessMember = userloginName;


			//					 for (int mail = 0; mail < currentemplyeeDetailsHT.size(); mail++) {
			//							Hashtable mailApproversHT=new Hashtable();
			//							mailApproversHT=(Hashtable)currentemplyeeDetailsHT.get(mail);
			//							String empName=(String)mailApproversHT.get("empName");
			//							if(empName.equals(userloginName)){
			//								
			//								  LastAccessMember=empName;
			//								  LastAccessMemberEmailID=(String)mailApproversHT.get("E-mail");	;
			//								  LastMemberRole=(String)mailApproversHT.get("User_Role");
			//								  LastMemberStatus=(String)mailApproversHT.get("User_Status");	
			//								  Notes=(String)mailApproversHT.get("Notes");								
			//								break;
			//							}
			//						}
			
			
			FromStage=(String)stageDetailsHT.get("Stage_Name");
			
			String stagestatus=(String)stageDetailsHT.get("Stage_Status");
			if(stagestatus.equals("Completed")){
				FromStatu=finalMsg;
			}else{
				FromStatu=stagestatus;
			}

			
			
			

			if(nxtStgNo.equals("Completed")){
				TOStage=FromStage;
				TOStatus=finalMsg;
			}else{
				TOStage=(String)stageDetailsHT.get("Stage_Name");
				TOStatus=(String)stageDetailsHT.get("Stage_Status");
			}
			String sendPrimaryDocOnlyFlag = "";
			if(nextTeamFlag) {
				if(!currentStageNo.equalsIgnoreCase("Completed")) {
					int next = Integer.valueOf(currentStageNo)+1;
					Hashtable nxtstageDetailsHT=lgnpro.retriveStageDetailsFromXML(workFlowN, String.valueOf(next),"");
					sendPrimaryDocOnlyFlag = nxtstageDetailsHT.get("SendPrimaryFileOnly") == null ? "" : nxtstageDetailsHT.get("SendPrimaryFileOnly").toString();
				}else {
					sendPrimaryDocOnlyFlag = stageDetailsHT.get("SendPrimaryFileOnly") == null ? "" : stageDetailsHT.get("SendPrimaryFileOnly").toString();
				}
				
			}else {
				sendPrimaryDocOnlyFlag = stageDetailsHT.get("SendPrimaryFileOnly") == null ? "" : stageDetailsHT.get("SendPrimaryFileOnly").toString();
			}
			

			// Element docEle = (Element)workFlowN.getParentNode();
			String eachTeamFlag = docEle.getAttribute("TimelineEachteam").toLowerCase();
			String dueDate ="";//$DueDate$
			if(eachTeamFlag.equalsIgnoreCase("true")) {

			}else {
				dueDate = docEle.getAttribute("EffectiveEndDate");
			}
			Element downLinkEle = (Element)configdoc.getElementsByTagName("Download_Link").item(0);
			Element actionLinkEle = (Element)configdoc.getElementsByTagName("Action_Link").item(0);
			Element resubmitLinkEle = (Element)configdoc.getElementsByTagName("Resubmit_Link").item(0);
			Element editUDLinkEle = (Element)configdoc.getElementsByTagName("UltraEdit_Link").item(0);
			String documnetName = docEle.getAttribute("DocumentName");//$DocName$
			String docNameTemp = documnetName;
			
			String fileNameWithOutExt = FilenameUtils.removeExtension(documnetName);
			System.out.println("docnamewithoutX::::::::::::::::::::::"+fileNameWithOutExt);
			String oDSFilePath = docEle.getAttribute("Primary_FilePath");
			String oDSFileconn = docEle.getAttribute("Primary_FileConn");
			String attamentDocs=getAttachFileNames(workFlowN, htmlFileFlag, externalUserFlag, FromStage, currentemplyeeDetailsHT);
			
			System.out.println(oDSFileconn+"====AattamentDocs =--=-==> "+attamentDocs);
			
			
			
			String currDate = sdf.format(new Date());//$CurrDate$
		//	sdf1 = new SimpleDateFormat("dd MMM yyyy hh:mm:ss zzz");
		//	System.out.println(sdf1.format(date));
			
			/*TimeZone tz = Calendar.getInstance().getTimeZone();  
			System.out.println("TIMEZONE ::::ssssssssssssss::::::"+tz.getDisplayName());
			*/
			
			String wfName = docEle.getAttribute("WorkflowName");//$WorkflowName$
			String downLink = downLinkEle.getAttribute("URL").trim().isEmpty() ? "" : downLinkEle.getAttribute("URL").trim();//$downloadlink$
//			http://localhost:8080/rbid/download?filename=$docName$&amp;filepath=$filepath$&amp;docID=$docID$&amp;username=$username$&amp;downloadFrom=$downloadFrom$&amp;attachmentType=$Type$&amp;index=$Index$
			String actionLink=actionLinkEle.getAttribute("URL").trim().isEmpty() ? "" : actionLinkEle.getAttribute("URL").trim();
//			http://localhost:8080/rbid/Statuspopup.jsf?documentId=$documentid$&amp;documentName=$documentname$&amp;username=$userName$&amp;index=$Index$
			String resubmitLink=resubmitLinkEle.getAttribute("URL").trim().isEmpty() ? "" : resubmitLinkEle.getAttribute("URL").trim();
//			http://localhost:8080/rbid/Resubmitpopup.jsf?documentId=$documentid$&amp;documentName=$documentname$&amp;username=$userName$
			String editUDLink = editUDLinkEle.getAttribute("URL").trim().isEmpty() ? "" : editUDLinkEle.getAttribute("URL").trim();
//			http://localhost:38391/index.aspx?id=$templateName$&amp;parent=$parentFolder$&amp;key=$key$&amp;name=$userid$&amp;from=$from$&amp;index=$Index$&amp;did=$docId$
			String multipleAttchments="";
			String attachMentsExt="";
			String attachMents="";
			
			String linkText="Please click here to process this document";
			String bodysignText="review / approval";
			String compltedsignText="processed";
			if(attamentDocs.equalsIgnoreCase("NA")) {
				attachMents=attamentDocs;
			}else {
				String[] arr=attamentDocs.split("~~@@");
				String downLoadAttachment=arr[0];
				String attachmentFiles="";
				//System.out.println("Length----------------------> "+arr[1].length());
				
				if(arr.length>1) {
					attachmentFiles=arr[1];
				}
				
				String[] arr1=null;
				String[] arr2=null;
				if(downLoadAttachment.contains("~~~")) {
					arr1=new String[downLoadAttachment.split("~~~").length];
					arr1=downLoadAttachment.split("~~~");
				}else {
					arr1=new String[1];
					arr1[0]=downLoadAttachment;
				}
				if(attachmentFiles.contains("@@@")) {
					arr2=new String[attachmentFiles.split("@@@").length];
					arr2=attachmentFiles.split("@@@");
				}else {
					arr2=new String[1];
					arr2[0]=attachmentFiles;
				}
				//String[] arr2=attachmentFiles.split("@@@");
				
				System.out.println("downLoadAttachment =--=-==> "+downLoadAttachment);
				System.out.println("attachmentFiles =--=-==-=-> "+attachmentFiles);
				for (int i = 0; i < arr1.length; i++) {
					String downLoadAttachment1="";
					String attachmentFiles1="";
					boolean found = false;
					if(arr1.length>0) {
						downLoadAttachment1=arr1[i];
					}
					if(arr2.length>0) {
						attachmentFiles1=arr2[i];
						if(externalUserFlag.equalsIgnoreCase("true") && attachmentFiles1.split("~~").length > 1) {
							found = Boolean.valueOf(attachmentFiles1.split("~~")[1]);
							attachmentFiles1 = attachmentFiles1.split("~~")[0];
						}
					}
					
					String attachFilesEncode=URLEncoder.encode(attachmentFiles1, "UTF-8");
					
					String downLink1 = downLink.replace("$docName$", attachFilesEncode).replace("$docID$", docId).replace("$username$", "$userName$").replace("$downloadFrom$", "mail").
							replace("$filepath$", "").replace("$Type$", "Attachment").replace("$Index$", nextStageNo);
					System.out.println("FromStage "+FromStage+"FromStatu "+FromStatu+"TOStage "+TOStage+"TOStatus "+TOStatus+"LastAccessMember "+LastAccessMember
							+"LastAccessMemberEmailID "+LastAccessMemberEmailID+"LastMemberRole "+LastMemberRole+"LastMemberStatus "+LastMemberStatus+"Notes "+Notes+": documnetName :"+documnetName);
					if(found) {
						if(attachMentsExt.trim().equals("")) {
							if(downLoadAttachment1.contains("-"+attachmentFiles1)) {
								attachMentsExt=downLoadAttachment1.replace("$downloadlink$-"+attachmentFiles1, downLink1);
							}else {
								attachMentsExt=downLoadAttachment1;
							}
						}else {
							if(downLoadAttachment1.contains("-"+attachmentFiles1)) {
								attachMentsExt=attachMentsExt+downLoadAttachment1.replace("$downloadlink$-"+attachmentFiles1, downLink1);
							}else {
								attachMentsExt=attachMentsExt+downLoadAttachment1;
							}
						}
					}else {
						if(attachMents.trim().equals("")) {
							if(downLoadAttachment1.contains("-"+attachmentFiles1)) {
								attachMents=downLoadAttachment1.replace("$downloadlink$-"+attachmentFiles1, downLink1);
							}else {
								attachMents=downLoadAttachment1;
							}
						}else {
							if(downLoadAttachment1.contains("-"+attachmentFiles1)) {
								attachMents=attachMents+downLoadAttachment1.replace("$downloadlink$-"+attachmentFiles1, downLink1);
							}else {
								attachMents=attachMents+downLoadAttachment1;
							}
						}
					}
					
				}
			}
			
			
			
			
			Hashtable userHT = getLoginDetails(createdUser, customerKey);
//			String getUsername = String.valueOf(userHT.get("First_Name"))+" "+String.valueOf(userHT.get("Last_Name"));
//			
//			System.out.println(getUsername+"^^^^^^^^^^^name^^^^:^^^^^createdMember^^^^^^^^^^^"+createdUser);
//			if(!getUsername.isEmpty() && getUsername != null) {
//				
//				getUsername=String.valueOf(userHT.get("First_Name"))+" "+String.valueOf(userHT.get("Last_Name"));
//				
//			}else {
//				
//				getUsername=createdUser;
//				
//			}
			String getUsername = getFristLastName(createdUser, customerKey);
			
			Hashtable rejectedtestHT = WorkflowManager.rejectNextUsers(levelXmlDoc, workFlowN, currentStageNo, userloginName);
			ArrayList<String> rejectedtestAL = (ArrayList<String>)rejectedtestHT.get("Users");
			ArrayList<String> rejectedUserNameAL = (ArrayList<String>)rejectedtestHT.get("UserName");
		
			String rejectedstage = (String)rejectedtestHT.get("Stage");
			String rejectedUser="";
			String onlyrejectedUserName="";
			for(String user : rejectedtestAL) {
				
				rejectedUser = rejectedUser+"\n\t"+user;
				
			}
		
		
			for(String user : rejectedUserNameAL) {
				
				onlyrejectedUserName = onlyrejectedUserName.trim().isEmpty() ? user : onlyrejectedUserName+", "+user;
			
				
			}
			
			
			System.out.println("^^^^^^^^^^^^^^^:^FINAL^^^^onlyrejectedUserName^^^^^^^^^^^"+onlyrejectedUserName);
			rejectedUser = getFristLastName(rejectedUser, customerKey);
			String	lastAccessMemberFirstName = getFristLastName(LastAccessMember, customerKey);
			String	userAttchloginName = getFristLastName(userloginName, customerKey);
			
			
			ArrayList<String> testAL = (ArrayList<String>)testHT.get("Users");
			ArrayList<String> userNametestAL = (ArrayList<String>)testHT.get("UserName");
			System.out.println(userNametestAL+"^^^^^^^^^^^name^^^^:^FINAL^^^^createdMember^^^^^^^^^^^"+userNametestAL.size());
			String stage = (String)testHT.get("Stage");
			String apprvedUser="";
			for(String user : testAL) {
				if(!apprvedUser.contains(user))
					apprvedUser = apprvedUser.trim().isEmpty() ? user : apprvedUser+", "+user;
				
			}
			
			String apprvedfullusername="";	
			
			for(String fullusername : userNametestAL) {
				
				if(!apprvedfullusername.contains(fullusername))
					
					apprvedfullusername = apprvedfullusername.trim().isEmpty() ? fullusername : apprvedfullusername+", "+fullusername;
				
			}
			
			
			
			
			System.out.println(attachStageEmailbol+"::::::::workflow_Type :"+workflow_Type);
			

			if(workflow_Type.equalsIgnoreCase("Simple")) {
				
				hideshowType="none";
				
				if(attachStageEmailbol) {
					
					bodyTEXThideshowType= getUsername;
					
				}else {
					
					bodyTEXThideshowType= lastAccessMemberFirstName;
					
				}
			
				
				
				
			}else {
				
				hideshowType="block";
				if(attachStageEmailbol) {
				
					bodyTEXThideshowType=FromStage +" / "+ getUsername ;
					
				}else {
					
					bodyTEXThideshowType=FromStage +" / "+ lastAccessMemberFirstName;
				}
				
			}
			
			System.out.println(bodyTEXThideshowType+"TTTTTTTTTapbodyTEXThideshowTypeeTTTTTTTTTTTT :"+apprvedfullusername);
			attamentDocs=attachMents;
			if(sendPrimaryDocOnlyFlag.equalsIgnoreCase("true"))
				attamentDocs = "NA";
			System.out.println(sendPrimaryDocOnlyFlag+"-----attamentDocs------- "+attamentDocs);
			System.out.println(sendPrimaryDocOnlyFlag+"-----ultratemplate_Name------- "+ultratemplate_Name);
			System.out.println(apprvedUser+"-----attamentDocs------- "+currentStageNo+"-=-=-=-=-=-=-=-=-=-=-="+userloginName+"-=-=-=-=-testHT=-=-=-="+testHT);
			System.out.println(sendPrimaryDocOnlyFlag+"-----attachMentsExt------- "+attachMentsExt);
			String documnetNameEncode=URLEncoder.encode(documnetName, "UTF-8");
			String attchmentNameEncode=URLEncoder.encode(attchFileName, "UTF-8");
//			http://localhost:8080/rbid/download?filename=$docName$&amp;filepath=$filepath$&amp;docID=$docID$&amp;username=$username$&amp;downloadFrom=$downloadFrom$&amp;attachmentType=$Type$&amp;index=$Index$
			String downLink2 = downLink.replace("$docName$", attchFileName).replace("$docID$", docId).replace("$username$", "").replace("$downloadFrom$", "mail").replace("$filepath$", "").replace("$Type$", "Attachment").replace("$Index$", nextStageNo);
			downLink = downLink.replace("$docName$", documnetNameEncode).replace("$docID$", docId).replace("$username$", "$userName$").replace("$downloadFrom$", "mail").replace("$filepath$", "").replace("$Type$", "Primary").replace("$Index$", nextStageNo);
			
			System.out.println("FromStage "+FromStage+"FromStatu "+FromStatu+"TOStage "+TOStage+"TOStatus "+TOStatus+"LastAccessMember "+LastAccessMember
					+"LastAccessMemberEmailID "+LastAccessMemberEmailID+"LastMemberRole "+LastMemberRole+"LastMemberStatus "+LastMemberStatus+"Notes "+Notes+": documnetName :"+documnetName);
			
			
			actionLink=actionLink.replace("$documentid$", docId).replace("$documentname$", documnetNameEncode).replace("$Index$", nextStageNo);
			resubmitLink=resubmitLink.replace("$documentid$", docId).replace("$documentname$", attchFileName.trim().equalsIgnoreCase("") ? documnetName : attchFileName);		//code Change Vishnu 31Jan2019
			resubmitLink = resubmitLink+"&type="+(attchFileName.trim().equalsIgnoreCase("") ? "Or" : "Re");
			editUDLink = editUDLink.replace("$templateName$", Inventory.encrypt4DotNet(ultratemplate_Name)).replace("$parentFolder$", Inventory.encrypt4DotNet(ultratemplate_FolderName)).
					replace("$key$", Inventory.encrypt4DotNet(customerKey)).replace("$from$", "wf").replace("$Index$", nextStageNo).replace("$docId$", docId);
			System.out.println(resubmitValue+" : &&&&&&&&&&&&&&resubmitLink&&&&&&&&&&&&&"+resubmitLink);
			
			String title = "";
			// Hashtable userHT = getLoginDetails(userloginName);
//			String customerKey = userHT.get("CustomerKey") == null ? "" : userHT.get("CustomerKey").toString();
			if(actionName.equalsIgnoreCase("ExternalUser")) {
				Hashtable custDetailsHT = getCustomerDetailsByCustomerKey(customerKey);
				System.out.println(actionName+"---------------custDetailsHT--------------- "+custDetailsHT);
				title = custDetailsHT.get("Title") == null ? "" : custDetailsHT.get("Title").toString();
			}else {
				title = userHT.get("Title") == null ? "" : userHT.get("Title").toString();
			}
			System.out.println(customerKey+"---------------customerKey--------------- "+title+"====downLink===="+downLink);
			
			System.out.println(docId+"---------------checkesignCf--------------- "+checkesignCf+"-=-=-=-=-=-=-=-=-=-="+nextStageNo);
			
			
			if(checkesignCf && flag) {
				
				 linkText="Please click here to sign this document";
				 bodysignText="review / e-signature ";
				 compltedsignText="signed ";
			}else {
						
				
				
				
				 linkText="Please click here to process this document";
				 bodysignText="review / approval";
				 compltedsignText="processed";
				
			}
			
			if(!ultratemplate_Descripter.isEmpty() || ultratemplate_Descripter != null) {
				
				documnetName=ultratemplate_Descripter;
				fileNameWithOutExt=ultratemplate_Descripter;
			}else {
					
				documnetName=ultratemplate_Name;
				fileNameWithOutExt=ultratemplate_Name;
		     }
			System.out.println(workflow_Type+"-=-=-=-=-=-=-=-=workflow_Type-=-=-=-=-=-="+esign_configure+"-=-=-=-=-="+flag+"-=-=-=-=-=-=userAL-=-=-=-=-=-=-=");
			if(workflow_Type.trim().toLowerCase().equalsIgnoreCase("simple") && esign_configure.trim().toLowerCase().equalsIgnoreCase("true") && flag) {
				
				actionLink=downLink;
				
			}
			if(allowUDEdit) {
				actionLink=editUDLink;
			}
			
			System.out.println(actionName+"---------------actionLink--------------- "+actionLink);
			if(!oDSFileconn.trim().equalsIgnoreCase("Smartsheet")) {
				System.out.println(customerKey+"---------------11111111111111111111--------------- "+title+"====downLink===="+downLink);
				
				if(resubmitValue.trim().equalsIgnoreCase("Resubmit")) {
					System.out.println(resubmitValue+"---------------HERE COMING--------------- "+title+"====downLink===="+downLink+"***************"+stageLUserName);
					
					mailMessage=mailMessage.replace("$FromStage$", FromStage).replace("$StageNamehide$", hideshowType).replace("$FromStatu$", FromStatu).replace("$LastAccessMember$", lastAccessMemberFirstName).replace("$LastAccessMemberEmailID$", LastAccessMemberEmailID).replace("$FirstPersonUser$", userAttchloginName).
							replace("$LastMemberRole$", LastMemberRole).replace("$LastMemberStatus$", LastMemberStatus).replace("$Notes$", Notes.isEmpty() ? "$Notes$" : Notes).replace("$DocName$", documnetName).replace("$CreatedMember$", getUsername).replace("$RejectedTo$", onlyrejectedUserName).replace("$hideshowType$", hideshowType).
							replace("$CurrDate$", currDate).replace("$WorkflowName$", wfName).replace("$downloadlink$", oDSFilePath).replace("$$Actions$$", resubmitLink).replace("$title$", title).replace("$DocNameXXX$", fileNameWithOutExt).replace("$requestedTO$", resubmitUser1).replace("$bodysignText$", bodysignText).replace("$IssignorNotLINK$", linkText)
							.replace("$StageandMemberLBL$", bodyTEXThideshowType).replace("$Attachmentdownloadlink$", downLink2).replace("$completedSignORNOT$", compltedsignText).replace("$NextMember$", apprvedfullusername).replace("$ResubmitDocName$", attchFileName).replace("$ResumbitLatestUserName$", resubmitUser1).replace("$ResumbitLatestDocNameXXX$", resubmitLatestFileName).replace("$selResubmitDocNameXXX$", FilenameUtils.removeExtension(attchFileNamewithextension)).replace("$selResubmitDocName$", attchFileName);
					if(htmlFileFlag)
						mailMessage=mailMessage.replace("$Attachments$", attamentDocs.equalsIgnoreCase("NA") ? attamentDocs : "").replace("$AttachmentsTree$", attamentDocs.equalsIgnoreCase("NA") ? "" : attamentDocs);
					else
						mailMessage=mailMessage.replace("$Attachments$", attamentDocs);
					mailMessage = mailMessage.replace("$$AttachExt$$", attachMentsExt);
				}
				else {
				
				mailMessage=mailMessage.replace("$FromStage$", FromStage).replace("$StageNamehide$", hideshowType).replace("$FromStatu$", FromStatu).replace("$LastAccessMember$", lastAccessMemberFirstName).replace("$LastAccessMemberEmailID$", LastAccessMemberEmailID).replace("$CreatedMember$", getUsername).replace("$FirstPersonUser$", stageLUserName).replace("$SentTo$", apprvedfullusername).
						replace("$Attachmentdownloadlink$", downLink2).replace("$LastMemberRole$", LastMemberRole).replace("$LastMemberStatus$", LastMemberStatus).replace("$Notes$", Notes.isEmpty() ? "$Notes$" : Notes).replace("$DocName$", documnetName).replace("$RejectedTo$", onlyrejectedUserName).replace("$hideshowType$", hideshowType).
						replace("$CurrDate$", currDate).replace("$WorkflowName$", wfName).replace("$downloadlink$", downLink).replace("$$Actions$$", actionLink).replace("$title$", title).replace("$DocNameXXX$", fileNameWithOutExt).replace("$bodysignText$", bodysignText).replace("$IssignorNotLINK$", linkText).replace("$completedSignORNOT$", compltedsignText).replace("$NextMember$", apprvedfullusername).
						replace("$StageandMemberLBL$", bodyTEXThideshowType).replace("$ResubmitDocName$", attchFileName).replace("$ResumbitLatestUserName$", resubmitUser1).replace("$ResumbitLatestDocNameXXX$", resubmitLatestFileName);
				if(htmlFileFlag)
					mailMessage=mailMessage.replace("$Attachments$", attamentDocs.equalsIgnoreCase("NA") ? attamentDocs : "").replace("$AttachmentsTree$", attamentDocs.equalsIgnoreCase("NA") ? "" : attamentDocs);
				else
					mailMessage=mailMessage.replace("$Attachments$", attamentDocs);
				mailMessage = mailMessage.replace("$$AttachExt$$", attachMentsExt);
				}
				
			}
			else {
				
				System.out.println(customerKey+"---------------HERE COMING-ELSE-------------- "+title+"====downLink===="+downLink);
				mailMessage=mailMessage.replace("$FromStage$", FromStage).replace("$StageNamehide$", hideshowType).replace("$FromStatu$", FromStatu).replace("$LastAccessMember$", lastAccessMemberFirstName).replace("$LastAccessMemberEmailID$", LastAccessMemberEmailID).replace("$CreatedMember$", getUsername).replace("$FirstPersonUser$", stageLUserName).replace("$SentTo$", apprvedfullusername).
						replace("$Attachmentdownloadlink$", downLink2).replace("$LastMemberRole$", LastMemberRole).replace("$LastMemberStatus$", LastMemberStatus).replace("$Notes$", Notes.isEmpty() ? "$Notes$" : Notes).replace("$DocName$", documnetName).replace("$RejectedTo$", onlyrejectedUserName).replace("$hideshowType$", hideshowType).
						replace("$StageandMemberLBL$", bodyTEXThideshowType).replace("$CurrDate$", currDate).replace("$WorkflowName$", wfName).replace("$downloadlink$", oDSFilePath).replace("$$Actions$$", actionLink).replace("$title$", title).replace("$DocNameXXX$", fileNameWithOutExt).replace("$bodysignText$", bodysignText).replace("$IssignorNotLINK$", linkText).replace("$completedSignORNOT$", compltedsignText).replace("$NextMember$", apprvedfullusername);;
				if(htmlFileFlag)
					mailMessage=mailMessage.replace("$Attachments$", attamentDocs.equalsIgnoreCase("NA") ? attamentDocs : "").replace("$AttachmentsTree$", attamentDocs.equalsIgnoreCase("NA") ? "" : attamentDocs);
				else
					mailMessage=mailMessage.replace("$Attachments$", attamentDocs);
				mailMessage = mailMessage.replace("$$AttachExt$$", attachMentsExt);
			}
			
			

			if(dueDate != null && !dueDate.isEmpty())
				mailMessage = mailMessage.replace("$DueDate$", dueDate);

			subject = subject.replace("$FromStage$", FromStage).replace("$FromStatu$", FromStatu).replace("$StageNamehide$", hideshowType).replace("$LastAccessMember$", lastAccessMemberFirstName).replace("$LastAccessMemberEmailID$", LastAccessMemberEmailID).replace("$FirstPersonUser$", apprvedfullusername)
					.replace("$LastMemberRole$", LastMemberRole).replace("$LastMemberStatus$", LastMemberStatus).replace("$Notes$", Notes).replace("$DocName$", documnetName).
					replace("$CurrDate$", currDate).replace("$WorkflowName$", wfName).replace("$downloadlink$", downLink).replace("$title$", title).replace("$DocNameXXX$", fileNameWithOutExt).replace("$bodysignText$", bodysignText).replace("$IssignorNotLINK$", linkText).replace("$completedSignORNOT$", compltedsignText).replace("$ResubmitUserName$", userloginName).replace("$ResubmitDocName$", attchFileName);
			mailMessage=mailMessage.replace("tablestart", "<table>");
			mailMessage=mailMessage.replace("tableend", "</table>");
			mailMessage=mailMessage.replace("trstart", "<tr>");
			mailMessage=mailMessage.replace("trend", "</tr>");
			mailMessage=mailMessage.replace("tdstart", "<td>");
			mailMessage=mailMessage.replace("tdend", "</td>");
			
			
			
		String customerLOGOPath	= returnLOGOpath(levelXmlDoc,docId,userloginName);
		 System.out.println("customerLOGOPath*********************** "+customerLOGOPath);
		 String customerLOGOdisplay="";
		 
		 if(customerLOGOPath!=null && !customerLOGOPath.equalsIgnoreCase("")) {
				
				customerLOGOdisplay="block";
				 System.out.println("customerLOGOdisplay********IFFFFFFF*************** "+customerLOGOdisplay);
			}else {
				
				 System.out.println("customerLOGOdisplay***********ELSE************ "+customerLOGOdisplay);
				customerLOGOdisplay="none";
			}
			
		
			mailMessage=mailMessage.replace("$customerLOGOPath$", customerLOGOPath).replace("$cusimageHIDE$", customerLOGOdisplay);
		
			
			
			mailDtailsHT.put("Subject", subject);

							
			System.out.println("---------------mailMessage-------------- "+mailMessage);




			mailDtailsHT.put("Mail_Message", mailMessage);

			
			sendAcknowledgementuermail(levelXmlDoc, workFlowN, currentStageNo, userloginName, actionName, mailTo, "acknowledgement-success",wfName,FromStage,FromStatu,LastAccessMember,
					LastAccessMemberEmailID,LastMemberRole,LastMemberStatus,Notes,docNameTemp,title,oDSFilePath,attamentDocs,attachMentsExt,docId, attchFileName,hideshowType,ultratemplate_Name,ultratemplate_Descripter);




		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("Exiting: " + new Exception().getStackTrace()[0].getClassName() + "."
				+ new Exception().getStackTrace()[0].getMethodName());

		return mailDtailsHT;

	}
	
	
	
	public static String getattachmentUser(Document levelXmlDoc,String document_ID,String attchFileName) {

						
				Node docND = Globals.getNodeByAttrVal(levelXmlDoc, "Document", "Document_ID", document_ID);
				Element docEle = (Element) docND;
				Node Attachments_Nd = docEle.getElementsByTagName("Attachments").item(0);
				String attresubmitUser ="";
				String attresubmitUserID="";
				String concentUserName_ID ="";
				if(Attachments_Nd == null) {
					return "";
				}
			
				NodeList Attachments_List = Attachments_Nd.getChildNodes();
				System.out.println("ssssssssAttachments_Listssssssssssssssssssss"+Attachments_List.getLength());
					for (int j = 0; j < Attachments_List.getLength(); j++) {
						
						if(Attachments_List.item(j).getNodeType() == Node.ELEMENT_NODE && Attachments_List.item(j).getNodeName().equals("Attachment")) {
							
							Node nameN=Attachments_List.item(j);
							if (nameN.getNodeType() == Node.ELEMENT_NODE ) {
								
							Element attachEle=(Element)nameN;
							String attFileName = attachEle.getAttribute("FileName");
							String attachUser = attachEle.getAttribute("AttachedUser");
							System.out.println(attFileName+"ssssssssattFileName::::::::::ssssssssssssssssssss"+attchFileName);
							if(attFileName.equalsIgnoreCase(attchFileName))
							{
								attresubmitUser = attachEle.getAttribute("AttachedUser");
								
							}
							
							}
				
						}
					}
					
				
				System.out.println("attresubmitUser::::::::::::::"+attresubmitUser);
					
				return attresubmitUser;
			}
	
	
	public String getASPcustomerKey(String loginMailID) {

		String customerLOGOPath = "";

		try {

			PropUtil prop = new PropUtil();
			String adminXMLPath = prop.getProperty("IISUSERADMINFILEPATH");
			String IISUSERADMINFILENAME = prop.getProperty("IISUSERADMINFILENAME");
			Document adminXmlDoc = Globals.openXMLFile(adminXMLPath, IISUSERADMINFILENAME);
			NodeList adminChNdList = adminXmlDoc.getElementsByTagName("Admin");

			for (int i = 0; i < adminChNdList.getLength(); i++) {
				if (adminChNdList.item(i).getNodeType() == Node.ELEMENT_NODE) {
					Element adminEle = (Element) adminChNdList.item(i);
					NodeList adminsubChNdList = adminXmlDoc.getElementsByTagName("User");
					for (int j = 0; j < adminsubChNdList.getLength(); j++) {
						Element adminEle1 = (Element) adminsubChNdList.item(j);
						String userId = adminEle1.getAttribute("UserID");

						if (userId.equalsIgnoreCase(loginMailID)) {
							
							customerLOGOPath = adminEle1.getParentNode().getAttributes().getNamedItem("CustomerKey").getNodeValue();
				

						}
					}
				}
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return customerLOGOPath;

	}
	
	public static String getCustomerLOGOPath(String userCustomerKey) {

		String customerLOGOPath = "";

		try {

			PropUtil prop = new PropUtil();
			String adminXMLPath = prop.getProperty("IISUSERADMINFILEPATH");
			String IISUSERADMINFILENAME = prop.getProperty("IISUSERADMINFILENAME");
			Document adminXmlDoc = Globals.openXMLFile(adminXMLPath, IISUSERADMINFILENAME);
			NodeList adminChNdList = adminXmlDoc.getElementsByTagName("Admin");

			for (int i = 0; i < adminChNdList.getLength(); i++) {
				if (adminChNdList.item(i).getNodeType() == Node.ELEMENT_NODE) {
					Element adminEle = (Element) adminChNdList.item(i);
					NodeList adminsubChNdList = adminXmlDoc.getElementsByTagName("User");
					for (int j = 0; j < adminsubChNdList.getLength(); j++) {
						Element adminEle1 = (Element) adminsubChNdList.item(j);
						String userId = adminEle1.getAttribute("UserID");
						String aspcustomerKey = adminEle1.getParentNode().getAttributes().getNamedItem("CustomerKey").getNodeValue();
						
						if (aspcustomerKey.equalsIgnoreCase(userCustomerKey)) {
							
							if(adminEle1.getParentNode().getAttributes().getNamedItem("Logo_Path") != null){
								
								customerLOGOPath =  adminEle1.getParentNode().getAttributes().getNamedItem("Logo_Path").getNodeValue();
								
							}
							
				

						}
					}
				}
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return customerLOGOPath;

	}
	
	public static void sendAcknowledgementuermail(Document docXmlDoc,Node workFlowN,String stageNo,String adminUsername,String actionName,String mailTo,
			String coimingfrom,String wfName,String FromStage,String FromStatu,String LastAccessMember,
			String LastAccessMemberEmailID,String LastMemberRole,String LastMemberStatus,String Notes,String documnetName,String title,
			String oDSFilePath,String attamentDocs,String attachMentsExt,String docId,String attchFileName,String hideshowType,String ultratemplate_Name,String ultratemplate_Descripter) {
		try {
			
			
			System.out.println(docId+":stageNo ::::::::1111111:::::"+stageNo);	
			System.out.println("adminUsername :::11111111::::::::::"+adminUsername);
			System.out.println("statusMsg :::::::::1111111111::::"+actionName);
			System.out.println("wfName :::11111111::::::::::"+wfName);
			System.out.println("FromStage :::::::::1111111111::::"+FromStage);
			System.out.println("FromStatu :::11111111::::::::::"+FromStatu);
			System.out.println("coimingfrom :::::::::1111111111::::"+coimingfrom);
			System.out.println("LastAccessMember :::::::::1111111111::::"+LastAccessMember);
			System.out.println("LastMemberStatus :::::::::1111111111::::"+LastMemberStatus);
			System.out.println("Notes :::11111111::::::::::"+Notes);
			System.out.println("documnetName :::::::::1111111111::::"+documnetName);
			System.out.println("title :::11111111::::::::::"+title);
			System.out.println("oDSFilePath :::::::::1111111111::::"+oDSFilePath);
			System.out.println("attamentDocs :::11111111::::::::::"+attamentDocs);
			System.out.println("attachMentsExt :::::::::1111111111::::"+attachMentsExt);
			System.out.println("hideshowType :::::::::1111111111::::"+hideshowType);
			System.out.println("ultratemplate_Name :::::::::1111111111::::"+ultratemplate_Name);
			System.out.println("ultratemplate_Descripter :::::::::1111111111::::"+ultratemplate_Descripter);
//			String downloaddocname=oDSFilePath.contains("BP") ? oDSFilePath.substring(oDSFilePath.lastIndexOf("BP")) : oDSFilePath;	
			String checkapprve="";
			String attachmentUser="";
					
			
			if(actionName.equalsIgnoreCase("Approve") || actionName.equalsIgnoreCase("Approved") || actionName.equalsIgnoreCase("Completed"))
			{
				checkapprve="AKWApprove";
				
			}else if(actionName.equalsIgnoreCase("Finished")) {
				checkapprve="AKWComplete";
				
			}
			else if(actionName.equalsIgnoreCase("Cancel") || actionName.equalsIgnoreCase("Cancelled")) {
				
				checkapprve="AKWCancel";
				
			}
			else if(actionName.equalsIgnoreCase("Resubmit") || actionName.equalsIgnoreCase("Resubmited")) {
	
				attachmentUser=	getattachmentUser(docXmlDoc, docId, attchFileName);
				checkapprve="AKWResubmit";
							
			}
			else if(actionName.equalsIgnoreCase("Rejected")) {
				
				checkapprve="AKWReject";
			}
			else if(actionName.equalsIgnoreCase("ResubmitedSuccess")) {
				
				attachmentUser=	getattachmentUser(docXmlDoc, docId, attchFileName);
				checkapprve="AKWResubmit-success";
				
			}
			else if(actionName.equalsIgnoreCase("Attach")) {
				
				checkapprve="AKWAttach";
			}
			String customerKey = ((Element)workFlowN).getAttribute("CustomerKey");
			String	attachmentUser1 = getFristLastName(attachmentUser, customerKey);
			
			System.out.println("attachmentUser :::::::::1111111111::::"+attachmentUser);
			String currstageno=stageNo;
			String fileNamewithoutextension=FilenameUtils.removeExtension(attchFileName);
			String attchFileNamewithoutextension=FilenameUtils.removeExtension(attchFileName);
			
			
			PropUtil prop = new PropUtil();
			String hierLeveldir = prop.getProperty("HIERARCHY_XML_DIR");
			String hierlink4Mail= prop.getProperty("HIERARCHY_BASE_URL");
			String hierarchyDIR = prop.getProperty("HIERARCHY_XML_DIR");
			String configFileName=prop.getProperty("HIERARCHY_CONFIG_FILE");
			String templateDir=prop.getProperty("TEMPLATE_DIR");
			String timeFormat = prop.getProperty("DATE_FORMAT");
			String timeFormatzone = prop.getProperty("DATE_FORMAT_ZONE");
			SimpleDateFormat sdf = new SimpleDateFormat(timeFormatzone);
			String	mailSendBy="";
			String mailPassword="";
			String mailMessage="";
			String bobyofMail="";
			String hideshowType1="block";
			String bodyTEXThideshowType1="";
			
			Hashtable mailDtailsHT=new Hashtable<>();
			LoginProcessManager lgn=new LoginProcessManager();
			Hashtable testHT = WorkflowManager.getNextDocumentUsers(docXmlDoc, workFlowN, stageNo, adminUsername);
			String nxtStageNo = testHT == null || testHT.get("StageNo") == null ? "" : (String)testHT.get("StageNo");
			//Boolean checkesignCf=WorkflowManager.checkEsignconfigureORnot(docId);
			String linkText="";
			String bodysignText="";
			Hashtable stageDetailsHT=lgn.retriveStageDetailsFromXML(workFlowN, currstageno,"");
			Boolean checkesignCf=stageDetailsHT.get("IsESignConfigured") == null ? false : Boolean.valueOf(stageDetailsHT.get("IsESignConfigured").toString());
			
			Hashtable<String, String> tempHT = WorkflowManager.checkEnableEsignInStageLevel(docId);
			String enableEsignFlag = tempHT == null || tempHT.get("EsignFlag") == null ? "" : tempHT.get("EsignFlag");
			String enableEsignStgNo = tempHT == null || tempHT.get("EsignStageNo") == null ? "" : tempHT.get("EsignStageNo");
			if(enableEsignFlag.equalsIgnoreCase("true")) {
				if(Globals.checkStringIsNumber(currstageno) && Globals.checkStringIsNumber(enableEsignStgNo)) {
					int currStg = Integer.valueOf(currstageno);
					int esignStgNo = Integer.valueOf(enableEsignStgNo);
					if(currStg <= esignStgNo) {
						checkesignCf = false;
					}else {
								
								checkesignCf = true;
					}
				}
			}
			String fileNameWithOutExt = FilenameUtils.removeExtension(documnetName);
		    Document configdoc=Globals.openXMLFile(hierarchyDIR, configFileName);
			NodeList ndlist=configdoc.getElementsByTagName("Mail_Configuration");
		    Node nd=ndlist.item(0);
			NodeList ndlist1=nd.getChildNodes();
			String oldMessage = "";
			String resubmitValue = "";
			boolean htmlFileFlag = false;
			Element downLinkEle = (Element)configdoc.getElementsByTagName("Download_Link").item(0);
			String documnetNameEncode=URLEncoder.encode(documnetName, "UTF-8");
			String downLink = downLinkEle.getAttribute("URL").trim().isEmpty() ? "" : downLinkEle.getAttribute("URL").trim();
			downLink = downLink.replace("$docName$", documnetNameEncode).replace("$docID$", docId).replace("$username$", "$userName$").replace("$downloadFrom$", "mail").replace("$filepath$", "").replace("$Type$", "Primary").replace("$Index$", currstageno);
			
			String attchFiledocumnetEncode=URLEncoder.encode(attchFileName, "UTF-8");
			
			if(coimingfrom.equalsIgnoreCase("acknowledgement-success")) {
				
				System.out.println(coimingfrom+":::::##########:::Type :::::::::**::::");
				System.out.println(mailTo+":::::::::###########::::mailTo :::To::::::::::");
				
				
			for(int i=0;i<ndlist1.getLength();i++){
				Node nd1=ndlist1.item(i);
				if(nd1.getNodeType()==Node.ELEMENT_NODE && nd1.getNodeName().equalsIgnoreCase("Mail_ID_Send_by")){
					mailSendBy=nd1.getTextContent();
					mailDtailsHT.put("Mail_ID_Send_by", mailSendBy);

				}
				if(nd1.getNodeType()==Node.ELEMENT_NODE && nd1.getNodeName().equalsIgnoreCase("Mail_Password")){
					mailPassword=nd1.getTextContent();
					mailDtailsHT.put("Mail_Password", mailPassword);
				}
				if(nd1.getNodeType()==Node.ELEMENT_NODE && nd1.getNodeName().equalsIgnoreCase("Mail_Message")){
					Element ele = (Element)nd1;

					if(ele.getAttribute("Type").isEmpty())
						oldMessage=nd1.getTextContent();
					
					System.out.println(coimingfrom+"::::::::Type :::::::::**::::"+ele.getAttribute("Type"));
					System.out.println(mailTo+":::::::::::::mailTo :::To::::::::::"+ele.getAttribute("To"));
					
					if(coimingfrom.equalsIgnoreCase(ele.getAttribute("Type"))) {
						mailMessage=nd1.getTextContent();
						System.out.println(mailTo+":::::::::::::INSIDE :::To::::::::::"+ele.getAttribute("To"));
						if(!ele.getAttribute("HTMLFileName").isEmpty() && new File(templateDir+ele.getAttribute("HTMLFileName")).isFile()) {
						String fileNme = ele.getAttribute("HTMLFileName");
						System.out.println(mailTo+"::::::::::AFTER:::INSIDE :::To::::::::::"+ele.getAttribute("To"));
						if(fileNme.equalsIgnoreCase("acknowledgement.html")) {
								
							if(checkapprve.equalsIgnoreCase("AKWApprove")) {
								
								fileNme="acknowledgement-Approved.html";
								
								
							}else if(checkapprve.equalsIgnoreCase("AKWCancel")) {
								
								
								fileNme="acknowledgement-wkCancel.html";
								
							}
							else if(checkapprve.equalsIgnoreCase("AKWResubmit")) {
								
								fileNme="acknowledgement-Resubmit.html";
								
							 downLink = downLink.replace("$docName$", attchFiledocumnetEncode).replace("$docID$", docId).replace("$username$", "").replace("$downloadFrom$", "mail").replace("$filepath$", "").replace("$Type$", "Attachment");
								
							}else if(checkapprve.equalsIgnoreCase("AKWResubmit-success")) {
								
								fileNme="acknowledgement-Resubmit-Success.html";
								downLink = downLink.replace("$docName$", attchFiledocumnetEncode).replace("$docID$", docId).replace("$username$", "").replace("$downloadFrom$", "mail").replace("$filepath$", "").replace("$Type$", "Attachment");
								
							}
							else if(checkapprve.equalsIgnoreCase("AKWReject")) {
								
								fileNme="acknowledgement-Reject.html";
								
							}
							else if(checkapprve.equalsIgnoreCase("AKWAttach")) {
								fileNme="acknowledgement-Attach.html";
							}else if(checkapprve.equalsIgnoreCase("AKWComplete")) {
								fileNme="acknowledgement-Completed.html";
							}
							else {
								
								fileNme="acknowledgement.html";
								
							}
							
							System.out.println("fileNme::::::::::::::::::"+fileNme);
							BufferedReader buffReader = new BufferedReader(new FileReader(new File(templateDir+fileNme)));
							StringBuilder sb = new StringBuilder();
						    String line = buffReader.readLine();
						    while (line != null) {
						      sb.append(line).append("\n");
						      line = buffReader.readLine();
						    }
							mailMessage = sb.toString();
							htmlFileFlag = true;
							
							}

						}
						
					}
				}
			}
		
		
	   String currDate = sdf.format(new Date());//$CurrDate$
	   System.out.println("downLink :::::::::1111111111::::"+downLink);
	   System.out.println("bobyofMail:::::::::::5555555555555:::::::::"+bobyofMail);
	   System.out.println("mailMessage :::::::::1111111111::::"+mailMessage);
	   
	   String subject = "";
	   String apprveAkWSubject = "";
	   String apprveAkW1Body = "";
	   String apprveAkW2Body = "";
	   String resubmitDocumentName = "";
	   String signORnotDate = "";
	   String signORnotSentByText = "";
	   
	   Hashtable rejectedtestHT = WorkflowManager.rejectNextUsers(docXmlDoc, workFlowN, stageNo, adminUsername);
		ArrayList<String> rejectedtestAL = (ArrayList<String>)rejectedtestHT.get("Users");
		ArrayList<String> rejectedUserNameAL = (ArrayList<String>)rejectedtestHT.get("UserName");
		String rejectedstage = (String)rejectedtestHT.get("Stage");
		String rejectedUser="";
		String onlyrejectedUserName="";
		
		for(String user : rejectedtestAL) {
			
			rejectedUser = rejectedUser.trim().isEmpty() ? user : rejectedUser+", "+user;
		
			
		}
		
		for(String user : rejectedUserNameAL) {
			
			onlyrejectedUserName = onlyrejectedUserName.trim().isEmpty() ? user : onlyrejectedUserName+", "+user;
		
			
		}
		
		System.out.println(":::::::::::onlyrejectedUserName::::::::::::"+onlyrejectedUserName);
		String	rejectedUserFirstName = getFristLastName(rejectedUser, customerKey);
		System.out.println(rejectedUserFirstName+":::::::::::rejectedUser::::::::::::"+rejectedUser);
		String	lastAccessMemberUserFirstName = getFristLastName(LastAccessMember, customerKey);
		String	rejectedTo = getFristLastName(adminUsername, customerKey);
	   
		System.out.println(stageNo+":::::::::::stageNo::::::::::::"+adminUsername);
	    //Hashtable testHT = WorkflowManager.getNextDocumentUsers(docXmlDoc, workFlowN, stageNo, adminUsername);
		ArrayList<String> testAL = (ArrayList<String>)testHT.get("Users");
		String stage = (String)testHT.get("Stage");


		Element workFlowNEL=(Element)workFlowN;
		
		String workflow_Type=workFlowNEL.getAttribute("Workflow_Type") == null ? "" : workFlowNEL.getAttribute("Workflow_Type").toString();
		
		if(workflow_Type.equalsIgnoreCase("Simple")) {
			
			hideshowType1="none";
			bodyTEXThideshowType1= lastAccessMemberUserFirstName;
			
		}else {
			
			hideshowType1="block";
			bodyTEXThideshowType1=stage +" / "+ lastAccessMemberUserFirstName;
		}
		
		String apprvedUser="";
		String userAB="";
		for(String user : testAL) {
			if(!apprvedUser.contains(user))
				
				apprvedUser = apprvedUser.trim().isEmpty() ? user : apprvedUser+", "+user;
		}
	
		ArrayList<String> userNametestAL1 = (ArrayList<String>)testHT.get("UserName");
		System.out.println(userNametestAL1+"^^^^^^^^^^^name^^^^:^FINAL^^^^userNametestAL1^^^^^^^^^^^"+userNametestAL1.size());
		String apprvedfullusername1="";	
		for(String fullusername1 : userNametestAL1) {
			if(!apprvedfullusername1.contains(fullusername1))
				apprvedfullusername1 = apprvedfullusername1.trim().isEmpty() ? fullusername1 : apprvedfullusername1+", "+fullusername1;
			
		}
		System.out.println("apprvedUserapprvedUser::"+apprvedUser);

		apprvedUser = getFristLastName(apprvedUser, customerKey);
		String attachStr = "";
	   if(checkesignCf) {
			
			 linkText="Please click here to sign this document";
			 bodysignText=" review / e-signature";
			 apprveAkWSubject=" signed ";
			 apprveAkW2Body=" for signature.";
			 apprveAkW1Body= stage+" / "+ apprvedUser;
			 signORnotDate = "Signed Date";
			 signORnotSentByText = "Signed by";
			 attachStr = "electronic signature";  
		}else {
			
		
			
			 linkText="Please click here to process this document";
			 bodysignText=" review / approval";
			 apprveAkWSubject=" processed";
			 apprveAkW2Body=" to process further";
			 apprveAkW1Body= stage+" / "+ apprvedUser;
			 signORnotDate = "Processed Date";
			 signORnotSentByText = "Processed by";
			 attachStr = "review / approval";  
		}
		
		if(!ultratemplate_Descripter.isEmpty() || ultratemplate_Descripter != null) {
			
			documnetName=ultratemplate_Descripter;
		
		}else {
				
			documnetName=ultratemplate_Name;
				
	     }
		fileNameWithOutExt=documnetName;
		
	if(actionName.equalsIgnoreCase("Approve") || actionName.equalsIgnoreCase("Finished")) {
		
		subject = "You have successfully "+ apprveAkWSubject +" document '" + fileNameWithOutExt +"'." ;
		mailMessage=mailMessage.replace("$ActionNamecontent$", "You have successfully "+ apprveAkWSubject +" document '"+ fileNameWithOutExt +"'." );
		mailMessage=mailMessage.replace("$ActionName2ndcontent$", "It has now been sent to "+ apprveAkW1Body +apprveAkW2Body +"." );
				
	}
	else if(actionName.equalsIgnoreCase("Completed")) {
		
		subject = "You have successfully "+ apprveAkWSubject +" document '" + fileNameWithOutExt +"'." ;
		mailMessage=mailMessage.replace("$ActionNamecontent$", "You have successfully "+ apprveAkWSubject +" document '"+ fileNameWithOutExt +"'." );
		mailMessage=mailMessage.replace("$ActionName2ndcontent$", " It has now been sent to "+ apprveAkW1Body +apprveAkW2Body +"." );
	}
	else if(actionName.equalsIgnoreCase("Acknowledged")) {
		return;
		
		/*subject = mailDtailsHT.get("Subject") == null || String.valueOf(mailDtailsHT.get("Subject")).isEmpty() ? 
				"You have requested Acknowledged Workflow Name / Document Name : "+ wfName +" / "+ documnetName : String.valueOf(mailDtailsHT.get("Subject"));
		mailMessage=mailMessage.replace("$ActionNamecontent$", "You have requested (Acknowledged) for Document "+documnetName);*/
		
	}else if(actionName.equalsIgnoreCase("Rejected")) {
		
		subject = "You have rejected the document '"+ fileNameWithOutExt+"'.";
			
		mailMessage=mailMessage.replace("$ActionNamecontent$", "You have requested Rejected for Document '"+fileNameWithOutExt+"'");
	}
	else if(actionName.equalsIgnoreCase("Resubmited")) {
		
		subject = mailDtailsHT.get("Subject") == null || String.valueOf(mailDtailsHT.get("Subject")).isEmpty() ? 
				"You have asked "+attachmentUser1+" to revise the document "+" " +attchFileNamewithoutextension  : String.valueOf(mailDtailsHT.get("Subject"));
		mailMessage=mailMessage.replace("$ActionNamecontent$", "You have requested (Revise) for Document "+attchFileNamewithoutextension);
	}
	else if(actionName.equalsIgnoreCase("ResubmitedSuccess")) {
		
		
		subject = "You have successfully submitted the revised document '" + fileNamewithoutextension+"'";
		mailMessage=mailMessage.replace("$ResumbitLatestDocNameXXX$", fileNamewithoutextension).replace("$ResumbitLatestUserName$", attachmentUser1);
	}
	else if(actionName.equalsIgnoreCase("Initiate")) {
		
		return;
		
		/*subject = mailDtailsHT.get("Subject") == null || String.valueOf(mailDtailsHT.get("Subject")).isEmpty() ? 
				"You have requested Initiate Workflow Name / Document Name : "+ wfName +" / "+ documnetName : String.valueOf(mailDtailsHT.get("Subject"));
		mailMessage=mailMessage.replace("$ActionNamecontent$", "You have requested (Initiate) for Document "+documnetName);*/
	}
	else if(actionName.equalsIgnoreCase("Escalate")) {
		return;
		/*
		subject = mailDtailsHT.get("Subject") == null || String.valueOf(mailDtailsHT.get("Subject")).isEmpty() ? 
				"You have requested Escalate Workflow Name / Document Name : "+ wfName +" / "+ documnetName : String.valueOf(mailDtailsHT.get("Subject"));
		mailMessage=mailMessage.replace("$ActionNamecontent$", "You have requested (Escalate) for Document "+documnetName);*/
	
	}
	else if(actionName.equalsIgnoreCase("Cancel") || actionName.equalsIgnoreCase("Cancelled")) {

		System.out.println("Cancel coming *********"+actionName);
		
		subject = "You have cancelled Document '"+fileNameWithOutExt+"'" ;
		mailMessage=mailMessage.replace("$ActionNamecontent$", "You have cancelled Document '"+fileNameWithOutExt+"'");
	}
	else if(actionName.equalsIgnoreCase("Attach")) {
		
		
		System.out.println("here coming *********"+actionName);
		
		
	
		Node stageN = Globals.getNodeByAttrValUnderParent(docXmlDoc, workFlowN, "Stage_No", stageNo);
		NodeList msgEnamePropNL = stageN.getChildNodes();
		Element docEle = (Element)workFlowN.getParentNode();
		String documentName = docEle.getAttribute("DocumentName");
		String stageLUserName="";	
		
		for (int i = 0; i < msgEnamePropNL.getLength(); i++) {
			Node propertiesNode = msgEnamePropNL.item(i);
			if (propertiesNode.getNodeType() == Node.ELEMENT_NODE) {
				if (propertiesNode.getNodeName().equalsIgnoreCase("Properties")) {

					
					Hashtable propHT=(Hashtable)Globals.getAttributeNameandValHT(propertiesNode);
					String propertiesValue=(String)propHT.get("Concurrency");
					 if(propertiesValue != null && propertiesValue.equals("Serial")){
						
						//Hashtable stageDetailsHT=retriveStageDetailsFromXML(workFlowN, currstageno,"");
						Hashtable emplyeeDetailsHT=(Hashtable)stageDetailsHT.get("EmployeedetHT");
						
						for (int approv = 0; approv < emplyeeDetailsHT.size(); approv++) {
							
							Hashtable ApproversHT=new Hashtable();
							ApproversHT=(Hashtable)emplyeeDetailsHT.get(approv);
							stageLUserName=(String)ApproversHT.get("E-mail");
							
							break;
						
						}
						
						
					}else if(propertiesValue != null && propertiesValue.equals("Parallel")){
						
						//Hashtable stageDetailsHT=retriveStageDetailsFromXML(workFlowN, currstageno,"");
						Hashtable emplyeeDetailsHT=(Hashtable)stageDetailsHT.get("EmployeedetHT");
						String strmailLength="";
						for (int approv = 0; approv < emplyeeDetailsHT.size(); approv++) {
							Hashtable ApproversHT=new Hashtable();
							ApproversHT=(Hashtable)emplyeeDetailsHT.get(approv);
							stageLUserName=(String)ApproversHT.get("E-mail");
							
							if(strmailLength.length()==0) {
								
								System.out.println("strmailLength Length:"+ strmailLength.length());
								strmailLength=stageLUserName;
								
							}else {
								
								strmailLength=strmailLength+ ", " +stageLUserName;
								
							}
							
							
							}
						
						
						stageLUserName=strmailLength;
						
					}
					
					
				}
			}
		}
		System.out.println("here stageLUserName *********"+stageLUserName);
		
		
		ArrayList<String> userNametestAL = (ArrayList<String>)testHT.get("UserName");
		System.out.println(userNametestAL+"^^^^^^^^^^^name^^^^:^FINAL^^^^createdMember^^^^^^^^^^^"+userNametestAL.size());
		String apprvedfullusername="";	
		for(String fullusername : userNametestAL) {
			if(!apprvedfullusername.contains(fullusername))
				apprvedfullusername = apprvedfullusername.trim().isEmpty() ? fullusername : apprvedfullusername+", "+fullusername;
			
		}
		
		String CreatedUser= getFristLastName(adminUsername, customerKey);
		
		System.out.println(CreatedUser +": CreatedUser((((((((((((((stageLUserName *********"+stageLUserName);
		apprvedfullusername1 = stageLUserName;
		subject="You have sent document '"+ fileNameWithOutExt+ "' to "+stageLUserName +" for "+attachStr;
		mailMessage=mailMessage.replace("$apprveAkWSubject$", apprveAkWSubject).replace("$SendTOUSER$", stageLUserName).replace("$signatureORNOT$", bodysignText)
		.replace("$SignatureORNOTSentDate$", signORnotDate).replace("$CreatedMember$", CreatedUser).replace("$SignatureORNOTSentBy$", signORnotSentByText).replace("$ToStage$", stage)
		.replace("$attachstr$", attachStr);
		
	}
	 
	else {
		
		subject = mailDtailsHT.get("Subject") == null || String.valueOf(mailDtailsHT.get("Subject")).isEmpty() ? 
				"You have "+""+actionName+""+ wfName +" / "+ documnetName : String.valueOf(mailDtailsHT.get("Subject"));
		mailMessage=mailMessage.replace("$ActionNamecontent$", "You have"+"("+actionName+")"+" for Document "+documnetName);
	}
	
	
	mailMessage=mailMessage.replace("$apprveAkWSubject$", apprveAkWSubject).replace("$StageNamehide$", hideshowType1).replace("$StageandMemberLBL$", bodyTEXThideshowType1).replace("$SendTOUSER$", apprvedfullusername1).replace("$signatureORNOT$", bodysignText).replace("$rejectedNextMember$", rejectedUserFirstName). replace("$RejectedToStage$", rejectedstage).replace("$LastAccessMember$", lastAccessMemberUserFirstName).replace("$ToStage$", stage).replace("$LastAccessMemberEmailID$", LastAccessMemberEmailID).replace("$RejectedTo$", onlyrejectedUserName).replace("$ApprovedTo$", apprvedfullusername1)
			.replace("$LastMemberRole$", LastMemberRole).replace("$LastMemberStatus$", LastMemberStatus).replace("$Notes$", Notes.isEmpty() ? "$Notes$" : Notes).replace("$DocName$", documnetName).replace("$DocNameXXX$", FilenameUtils.removeExtension(documnetName)).replace("$selResubmitDocNameXXX$", FilenameUtils.removeExtension(attchFileNamewithoutextension)).replace("$selResubmitDocName$", attchFileName).
			replace("$hideshowType$", hideshowType).replace("$CurrDate$", currDate).replace("$WorkflowName$", wfName).replace("$downloadlink$", downLink).replace("$title$", title).replace("$attachmentUser$", attachmentUser1).replace("$ResumbitLatestUserName$", attachmentUser1).replace("$NextMember$", apprvedfullusername1);
	
	if(htmlFileFlag)
		mailMessage=mailMessage.replace("$Attachments$", attamentDocs.equalsIgnoreCase("NA") ? attamentDocs : "").replace("$AttachmentsTree$", attamentDocs.equalsIgnoreCase("NA") ? "" : attamentDocs);
	else
	mailMessage=mailMessage.replace("$Attachments$", attamentDocs);
	mailMessage = mailMessage.replace("$$AttachExt$$", attachMentsExt);
	String customerLOGOPath	= returnLOGOpath(docXmlDoc,docId,adminUsername);
	System.out.println("**********customerLOGOPath***********customerLOGOPath****************"+customerLOGOPath);
	
	String customerLOGOdisplay="";
	
	if(customerLOGOPath!=null && !customerLOGOPath.equalsIgnoreCase("")) {
		
		customerLOGOdisplay="block";
		
	}else {
		
		customerLOGOdisplay="none";
	}
	
	 System.out.println(customerLOGOPath+":customerLOGOPath : ::::customerLOGOdisplay********22222*************** "+customerLOGOdisplay);
	mailMessage=mailMessage.replace("$customerLOGOPath$", customerLOGOPath).replace("$cusimageHIDE$", customerLOGOdisplay);
	
	bobyofMail=mailMessage;
	
	System.out.println("**********ACKNOWLEDGEMENT***********bobyofMail****************"+bobyofMail);
	
	if(actionName.equalsIgnoreCase("ResubmitedSuccess")) {
		
		attachmentUser=	getattachmentUser(docXmlDoc, docId, attchFileName);
		checkapprve="AKWResubmit-success";
		
		String[] mailList= {attachmentUser};
		lgn.postMail(mailList, subject, bobyofMail, mailSendBy, mailPassword, null, hierlink4Mail, customerKey);
		
	}else {
		
		String[] mailList= {adminUsername};
		lgn.postMail(mailList, subject, bobyofMail, mailSendBy, mailPassword, null, hierlink4Mail, customerKey);
	}
	
	
}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
	}
	
	
	
	
	public static String getFristLastName(String usrname, String customerKey) {
		
		Hashtable<String, String> loginDetailsHT = getLoginDetails(usrname, customerKey);
		String userFirstName=String.valueOf(loginDetailsHT.get("First_Name"));
		String userLastName=String.valueOf(loginDetailsHT.get("Last_Name"));
		String userGetfullName="";
		//String getApproverNextUsername = String.valueOf(loginDetailsHT.get("First_Name"))+" "+String.valueOf(loginDetailsHT.get("Last_Name"));
		
		System.out.println(customerKey+"^^^^^^^^^^^customerKey^^^^^^^^^^^"+usrname);
		if(userFirstName != null  && userLastName != null && !userFirstName.isEmpty() && !userLastName.isEmpty())
		{	
			userGetfullName=userFirstName+" "+ userLastName;
		
		
		}else if(userFirstName != null  &&  !userFirstName.isEmpty() || userLastName == null &&  userLastName.isEmpty()) {
			
			userGetfullName=userFirstName;
			
		}
		
		else {
		
			userGetfullName=String.valueOf(loginDetailsHT.get("Login_ID")) ;
			
		}
			
		System.out.println("^^^^^^^^^^^userGetfullName^^^^^^^^^^^"+userGetfullName);
		System.out.println("^^^^^^^^^^^userFirstName^^^^^^^^^^^"+userFirstName);
		System.out.println("^^^^^^^^^^^userLastName^^^^^^^^^^^"+userLastName);
		System.out.println("^^^^^^^^^^^LoginID^^^^^^^^^^^"+String.valueOf(loginDetailsHT.get("Login_ID")));
		
		return userGetfullName;
			
		}
	
	
	
	
	
	private static String getAttachFileNames(Node wrkflwNode, Boolean htmlFileFlag, String externalUser, String stageName, Hashtable currentemplyeeDetailsHT) {
		// TODO Auto-generated method stub
		String str="";
		try {
			String attachMent="";
			
			String attachMentFNames="";
			Node docNode=wrkflwNode.getParentNode();
			
			NodeList docNodeList = docNode.getChildNodes();
			System.out.println("~~~~~~~docNodeList Node :Length ~~~~~~~~~ "+docNodeList.getLength());
			for (int i = 0; i < docNodeList.getLength(); i++) {
				Node checkN = docNodeList.item(i);
				System.out.println("~~~YYYYYY~~~~Node Name~~~~~~~~~ "+checkN.getNodeName());
				if (checkN.getNodeType() == Node.ELEMENT_NODE &&checkN.getNodeName().equals("Attachments")) {
					System.out.println("~~~~~~~Node Name~~~~~~~~~ "+checkN.getNodeName());
					NodeList attachNodeList = checkN.getChildNodes();
					
					System.out.println("~~~~~~attachNodeList~Node Length~~~~~~~~~ "+attachNodeList.getLength());
					for (int j = 0; j < attachNodeList.getLength(); j++) {
						Node childN = attachNodeList.item(j);
						if (childN.getNodeType() == Node.ELEMENT_NODE ) {
							Element attEle=(Element)childN;
							String fileNam=attEle.getAttribute("FileName");
							String filePath=attEle.getAttribute("FilePath");
							String attachedStg = attEle.getAttribute("AttachedStage");
							String attachedUser = attEle.getAttribute("AttachedUser");
							String uploadedDate = attEle.getAttribute("UploadedDate");
							System.out.println(filePath+" <------------> "+fileNam);
							String notes = "";
							boolean found = false;
							for(int k=0;k<currentemplyeeDetailsHT.size();k++) {
								Hashtable mailApproversHT=new Hashtable();
								mailApproversHT=(Hashtable)currentemplyeeDetailsHT.get(k);
								String empName=(String)mailApproversHT.get("empName");
								if(attachedUser.equalsIgnoreCase(empName)) {
									notes = mailApproversHT.get("Notes") == null ? "" : mailApproversHT.get("Notes").toString();
									found=true;
									break;
								}
							}
							
							String tdStyle = "style=\"white-space: nowrap;text-overflow: ellipsis;overflow: hidden; \"";
							if(attachMent.trim().equals("")) {
								
								if(filePath.startsWith("https://")) {
									if(htmlFileFlag) {
										if(externalUser.equalsIgnoreCase("true") && found) {
											attachMent = "<tr style=\"text-align: left; padding: 4px;\"> <td "+tdStyle+">"+fileNam+"</td> <td "+tdStyle+">"+attachedStg+" / "+attachedUser+"</td> <td "+tdStyle+">"+uploadedDate+"</td> <td "+tdStyle+">"+notes+"</td> </tr>";
										}else {
											attachMent="<div style=\"margin-top: 5px;margin-left: 35px;\"><a href='"+filePath+"'>"+fileNam+"</a></div>";
										}
									}										
									else
										attachMent="<tr><td><a href='"+filePath+"'>"+fileNam+"</a></td></tr>";
								}else {
									attachMentFNames=fileNam+(externalUser.equalsIgnoreCase("true") ? "~~"+found : "");
									if(htmlFileFlag) {
										if(externalUser.equalsIgnoreCase("true") && found) {
											attachMent = "<tr style=\"text-align: left; padding: 4px;\"> <td "+tdStyle+">"+fileNam+"</td> <td "+tdStyle+">"+attachedStg+" / "+attachedUser+"</td> <td "+tdStyle+">"+uploadedDate+"</td> <td "+tdStyle+">"+notes+"</td> </tr>";
										}else {
											attachMent="<div style=\"margin-top: 5px;margin-left: 35px;\">"+fileNam+"</div>";
										}
									}
										
									else
										attachMent="<tr><td>"+fileNam+"</td></tr>";
								}
								
//								attachMentFNames=fileNam;
//								attachMent="<tr><td><a href='$downloadlink$-"+fileNam+"'>"+fileNam+"</a></td></tr>";
							}else {
								
								if(filePath.startsWith("https://")) {
									if(htmlFileFlag) {
										if(externalUser.equalsIgnoreCase("true") && found) {
											attachMent = attachMent+"<tr style=\"text-align: left; padding: 4px;\"> <td "+tdStyle+">"+fileNam+"</td> <td "+tdStyle+">"+attachedStg+" / "+attachedUser+"</td> <td "+tdStyle+">"+uploadedDate+"</td> <td "+tdStyle+">"+notes+"</td> </tr>";
										}else {
											attachMent=attachMent+"<div style=\"margin-top: 5px;margin-left: 35px;\">"+fileNam+"</div>";
										}
									}
										
									else
										attachMent=attachMent+"<tr><td>"+fileNam+"</td></tr>";
									//attachMent=attachMent+"~~~<tr><td><a href='$downloadlink$-"+fileNam+"'>"+fileNam+"</a></td></tr>";
								}else {
									attachMentFNames=attachMentFNames+"@@@"+fileNam+(externalUser.equalsIgnoreCase("true") ? "~~"+found : "");;
									if(htmlFileFlag) {
										if(externalUser.equalsIgnoreCase("true") && found) {
											attachMent = attachMent+"~~~<tr style=\"text-align: left; padding: 4px;\"> <td "+tdStyle+">"+fileNam+"</td> <td "+tdStyle+">"+attachedStg+" / "+attachedUser+"</td> <td "+tdStyle+">"+uploadedDate+"</td> <td "+tdStyle+">"+notes+"</td> </tr>";
										}else {
											attachMent=attachMent+"~~~<div style=\"margin-top: 5px;margin-left: 35px;\">"+fileNam+"</div>";
										}
									}
										
									else
										attachMent=attachMent+"~~~<tr><td>"+fileNam+"</td></tr>";
								}
//								attachMentFNames=attachMentFNames+"@@@"+fileNam;
//								attachMent=attachMent+"~~~<tr><td><a href='$downloadlink$-"+fileNam+"'>"+fileNam+"</a></td></tr>";
							}
						}
						
					}
				}
				}
			System.out.println(" <------------> "+attachMent);
			if(attachMent.trim().equals("")) {
				attachMent="NA";
			}
			if(attachMent.equalsIgnoreCase("NA")&&attachMent.length()==2) {
				str="NA";
			}else {
				str=attachMent+"~~@@"+attachMentFNames;
			}
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		
		return str;
	}


	public  String getFinalMsg(String heirarchyID,String userStatus){

		System.out.println("Entering: " + new Exception().getStackTrace()[0].getClassName() + "."
				+ new Exception().getStackTrace()[0].getMethodName());

		String ishasUserStatus="false";
		try{
			PropUtil prop = new PropUtil();
			String heirLevelXML = prop.getProperty("HIERARCHY_XML_FILE");
			String heirLeveldir = prop.getProperty("HIERARCHY_XML_DIR");
			Document xmlDoc=Globals.openXMLFile(heirLeveldir, heirLevelXML);				
			Node workFlowN = Globals.getNodeByAttrVal(xmlDoc, "Workflow", "Hierarchy_ID", heirarchyID);
			System.out.println("User Status  : "+userStatus +" heirarchyID "+heirarchyID);
			if(workFlowN!=null){
				Element workFlowNEL=(Element)workFlowN;
				String totstage=workFlowNEL.getAttribute("Total_No_Stages");;
				int tot=Integer.parseInt(totstage);
				int stage=0;
				for(int k=0; k<tot; k++){
					stage=k+1;

					Hashtable stageDetailsHT=getStageDetails(heirarchyID, String.valueOf(stage),"");
					Hashtable mssgeDetailsHT=(Hashtable)stageDetailsHT.get("MessagedetHT");
					String finalMsg=(String)mssgeDetailsHT.get("Final");

					System.out.println("User Status  : "+userStatus +" finalMsg "+finalMsg+" heirarchyID "+heirarchyID+" stage "+stage);
					if(userStatus.equals(finalMsg)){
						ishasUserStatus="true";

						return ishasUserStatus;
					}						

				}




			}




		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("Exiting: " + new Exception().getStackTrace()[0].getClassName() + "."
				+ new Exception().getStackTrace()[0].getMethodName());

		return ishasUserStatus;

	}

	public  Hashtable workFlowNCheckAccessUser(Document xmlDoc, String heirarchyID,String hierarchyName,String currentStageNO,Boolean cmngFromLogin,String accessUniqIDcmnFrmLogin,String hierarchyDIR,String hierarchyXML){

		System.out.println("Entering: " + new Exception().getStackTrace()[0].getClassName() + "."
				+ new Exception().getStackTrace()[0].getMethodName());

		Hashtable NextProcessHT=new Hashtable(); 
		try{


			Node workFlowN = Globals.getNodeByAttrVal(xmlDoc, "Workflow", "Hierarchy_ID", heirarchyID);
			NextProcessHT = workFlowNCheckAccessUser(xmlDoc, heirarchyID, hierarchyName, currentStageNO, cmngFromLogin, accessUniqIDcmnFrmLogin, hierarchyDIR, hierarchyXML);



		} catch (Exception e) {
			e.printStackTrace();
		}


		System.out.println("Exiting: " + new Exception().getStackTrace()[0].getClassName() + "."
				+ new Exception().getStackTrace()[0].getMethodName());

		return NextProcessHT;
		
	}

	public Hashtable workFlowNCheckAccessUser(Document xmlDoc, Node workFlowN, String heirarchyID,String hierarchyName,String currentStageNO,
			Boolean cmngFromLogin,String accessUniqIDcmnFrmLogin,String hierarchyDIR,String hierarchyXML, String userName, String actionName) throws Exception {
		Hashtable NextProcessHT=new Hashtable(); 
		PropUtil prop = new PropUtil();
		String hierlink4Mail = prop.getProperty("HIERARCHY_BASE_URL");
		if(!currentStageNO.equals("Completed") && !currentStageNO.equalsIgnoreCase("Cancel") && !currentStageNO.equalsIgnoreCase("Rules Failed")) {
			Node stageN = Globals.getNodeByAttrValUnderParent(xmlDoc, workFlowN, "Stage_No", currentStageNO);

			System.out.println("heirarchyID "+heirarchyID+"hierarchyName "+hierarchyName+"currentStageNO "+currentStageNO+"accessUniqIDcmnFrmLogin "+accessUniqIDcmnFrmLogin+"hierarchyDIR "+hierarchyDIR+"hierarchyXML "+hierarchyXML);
			NodeList msgEnamePropNL = stageN.getChildNodes();
			Element docEle = (Element)workFlowN.getParentNode();
			String documentName = docEle.getAttribute("DocumentName");
			// (currentheirCode, currentstageNo);
			String indicate="";
			NextProcessHT.put("properties", "NA");
			NextProcessHT.put("Min_Approvers", "NA");
			NextProcessHT.put("FinalMsg", "NA");
			NextProcessHT.put("indicate", "NA");
			NextProcessHT.put("LoginProcess", false);
			Hashtable noOfApproversHT=new Hashtable(); 
			NextProcessHT.put("noOfApproversHT", noOfApproversHT);
			for (int i = 0; i < msgEnamePropNL.getLength(); i++) {
				Node propertiesNode = msgEnamePropNL.item(i);
				if (propertiesNode.getNodeType() == Node.ELEMENT_NODE) {
					if (propertiesNode.getNodeName().equalsIgnoreCase("Properties")) {

						// get Properties
						//code change pandian 19MAR2014
						Hashtable propHT=(Hashtable)Globals.getAttributeNameandValHT(propertiesNode);
						String propertiesValue=(String)propHT.get("Concurrency");
						//						String propertiesValue=propertiesNode.getTextContent();
						System.out.println("propertiesValue  :"+propertiesValue);
						if(propertiesValue != null && propertiesValue.equals("Parallel")){
							Element propE = (Element) propertiesNode;
							String minApprovers=propE.getAttribute("Min_Approvers");
							NextProcessHT.put("properties", propertiesValue);
							NextProcessHT.put("Min_Approvers", minApprovers);
							Hashtable stageDetailsHT=retriveStageDetailsFromXML(workFlowN, currentStageNO,"");
							Hashtable emplyeeDetailsHT=(Hashtable)stageDetailsHT.get("EmployeedetHT");
							Hashtable mssgeDetailsHT=(Hashtable)stageDetailsHT.get("MessagedetHT");
							String finalMsg=(String)mssgeDetailsHT.get("Final");
							NextProcessHT.put("FinalMsg", finalMsg);
							int appr=0;
							noOfApproversHT=new Hashtable(); 
							int minAprrovers=0;

							//code change pandian 19MAR2014
							boolean iscorrectperson=false;
							for (int approv = 0; approv < emplyeeDetailsHT.size(); approv++) {
								Hashtable ApproversHT=new Hashtable();
								ApproversHT=(Hashtable)emplyeeDetailsHT.get(approv);
								String accessuniqID=(String)ApproversHT.get("Access_Unique_ID");								
								if(accessuniqID.equals(accessUniqIDcmnFrmLogin)){
									iscorrectperson=true;
								}
							}

							if(!iscorrectperson){ 
								System.out.println("Member cannot Process Please Contact Administration  :"+accessUniqIDcmnFrmLogin);

								NextProcessHT.put("indicate", "None (Member cannot Process Please Contact Administration)");									 
								NextProcessHT.put("LoginProcess", false);
								return NextProcessHT;
							}

							for (int approv = 0; approv < emplyeeDetailsHT.size(); approv++) {
								Hashtable ApproversHT=new Hashtable();
								ApproversHT=(Hashtable)emplyeeDetailsHT.get(approv);
								String userStatus=(String)ApproversHT.get("User_Status");	
								System.out.println(userStatus+"-=-=-=-=-=-=dddddd-=-=-=-="+finalMsg+"=----empName-----"+(String)ApproversHT.get("empName"));
								if(userStatus.equals(finalMsg)){
									noOfApproversHT.put(appr, ApproversHT);
									minAprrovers=minAprrovers+1;
									appr++;
								}
							}
							NextProcessHT.put("noOfApproversHT", noOfApproversHT);
							int fromxmlminapprovers=Integer.parseInt(minApprovers);
							System.out.println(fromxmlminapprovers+"-=-=-=-=-=-=fromxmlminapprovers-=-=-=-="+minAprrovers);
							if(minAprrovers>=fromxmlminapprovers){ // min aprovers are satisfaction the condition you can send mail now

								System.out.println("INSIDE COMING*****************");
								if(cmngFromLogin){

									System.out.println("Minimum Member's is ("+minApprovers+") So you Can not Process Any More :"+accessUniqIDcmnFrmLogin);

									//if comming from login page indicate to person you cannot process
									NextProcessHT.put("indicate", "None (Minimum members to approve is "+minApprovers+". So you can not process any more)");									 
									NextProcessHT.put("LoginProcess", false);
								}else{

									System.out.println("Minimum Approvers Availavble So Mail will be Send After Complete the Process."+accessUniqIDcmnFrmLogin);

									NextProcessHT.put("indicate", "None (Minimum Approvers Availavble So Mail will be Send After Complete the Process.)"); 
									NextProcessHT.put("LoginProcess", true);

									// send mail to Employees
									String isMailSent="";
									Element stageE=(Element)stageN;
									isMailSent=stageE.getAttribute("MailSent");
									if(!isMailSent.equalsIgnoreCase("Yes"))
									{
										sendmail2Employees(stageN, currentStageNO,xmlDoc,workFlowN,heirarchyID,hierarchyName,hierlink4Mail, userName, actionName);
									}



									Globals.writeXMLFile(xmlDoc, hierarchyDIR, hierarchyXML);
								}


								 break;
							}else{  // you can not send mail now


								System.out.println("E COMING*****************");
								if(cmngFromLogin){


									//if comming from login page indicate to person you  process Now
									for (int approv = 0; approv < emplyeeDetailsHT.size(); approv++) {
										Hashtable ApproversHT=new Hashtable();
										ApproversHT=(Hashtable)emplyeeDetailsHT.get(approv);
										String accessUniqID=(String)ApproversHT.get("Access_Unique_ID");								
										if(accessUniqID.equals(accessUniqIDcmnFrmLogin)){
											NextProcessHT.put("indicate", "None (Minimum members to approve is "+minApprovers+" So member can process now)");
											NextProcessHT.put("LoginProcess", true);
											break;
										}
									}

									return NextProcessHT;
								}else{


									int waitperson=fromxmlminapprovers-minAprrovers;
									indicate="None (Minimum Member's is "+minApprovers+" Waiting For "+waitperson+" Person to Proceed Next Process)";
									NextProcessHT.put("indicate", indicate);
									NextProcessHT.put("LoginProcess", false);
									int nonApprovedUsersCount = 0;
									for (int approv = 0; approv < emplyeeDetailsHT.size(); approv++) {
										Hashtable ApproversHT=new Hashtable();
										ApproversHT=(Hashtable)emplyeeDetailsHT.get(approv);
										String userStatus=(String)ApproversHT.get("User_Status");
										String usrname=(String)ApproversHT.get("empName");
										String activeFlag = ApproversHT.get("Active") == null || String.valueOf(ApproversHT.get("Active")).isEmpty() ? "true" : String.valueOf(ApproversHT.get("Active"));
										Hashtable<String, String> loginDetailsHT = getLoginDetails(usrname, docEle.getAttribute("CustomerKey"));
										String disableFlag = loginDetailsHT.get("Disable") == null ? "false" : loginDetailsHT.get("Disable");;
										if(!userStatus.equals(finalMsg) && !activeFlag.equalsIgnoreCase("false") && !disableFlag.equalsIgnoreCase("true")){
											nonApprovedUsersCount++;
										}
									}
									if(nonApprovedUsersCount<=0) {
										System.out.println("Minimum Approvers Availavble So Mail will be Send After Complete the Process."+accessUniqIDcmnFrmLogin);

										NextProcessHT.put("indicate", "None (Minimum Approvers Availavble So Mail will be Send After Complete the Process.)"); 
										NextProcessHT.put("LoginProcess", true);

										// send mail to Employees
										String isMailSent="";
										Element stageE=(Element)stageN;
										isMailSent=stageE.getAttribute("MailSent");
										if(!isMailSent.equalsIgnoreCase("Yes"))
										{
											sendmail2Employees(stageN, currentStageNO,xmlDoc,workFlowN,heirarchyID,hierarchyName,hierlink4Mail, userName, actionName);
										}



										Globals.writeXMLFile(xmlDoc, hierarchyDIR, hierarchyXML);
									}
								}



							}



						}else if(propertiesValue != null && propertiesValue.equals("Serial")){// end of if(propertiesValue.equals("Parallel")){

							System.out.println("1.propertiesValue  :"+propertiesValue);


							NextProcessHT.put("properties", propertiesValue);
							NextProcessHT.put("Min_Approvers", "NA");
							Hashtable stageDetailsHT=retriveStageDetailsFromXML(workFlowN, currentStageNO,"");
							Hashtable emplyeeDetailsHT=(Hashtable)stageDetailsHT.get("EmployeedetHT");
							Hashtable mssgeDetailsHT=(Hashtable)stageDetailsHT.get("MessagedetHT");
							String finalMsg=(String)mssgeDetailsHT.get("Final");
							if(finalMsg==null){
								NextProcessHT.put("indicate", "None (Final Message Not Available). Please contact Adminstration");	
								NextProcessHT.put("LoginProcess", false);
								return NextProcessHT;
							}
							NextProcessHT.put("FinalMsg", finalMsg);

							if(emplyeeDetailsHT==null){
								NextProcessHT.put("indicate", "None (Member not available). Please contact Adminstration");	
								NextProcessHT.put("LoginProcess", false);
								return NextProcessHT;
							}
							int lastperson=emplyeeDetailsHT.size()-1;
							if(lastperson==-1){
								NextProcessHT.put("indicate", "None (Member not available). Please contact Adminstration");	
								NextProcessHT.put("LoginProcess", false);
								return NextProcessHT;
							}

							Hashtable ApproversHT=new Hashtable();
							ApproversHT=(Hashtable)emplyeeDetailsHT.get(lastperson);
							String accessUniqID=(String)ApproversHT.get("Access_Unique_ID");
							String userStatus=(String)ApproversHT.get("User_Status");	
							if(accessUniqID.equals(accessUniqIDcmnFrmLogin) && userStatus.equals(finalMsg)){

								if(cmngFromLogin){
									//if comming from login page indicate to person you cannot process
									NextProcessHT.put("indicate", "None (This stage already completed). So you can not process now");	
									NextProcessHT.put("LoginProcess", false);
									break; 
								}else{
									NextProcessHT.put("indicate", "None (User Can Process) , Mail will be Send After Complete the Process");	
									NextProcessHT.put("LoginProcess", true);

									// set status on Stage Node and Work flow Node Stage_Status="WIP" MailSent="yes" Mail_Sent_Time="11-03-2014 05:32 PM"
									// send mail to Employees
									sendmail2Employees(stageN, currentStageNO,xmlDoc,workFlowN,heirarchyID,hierarchyName,hierlink4Mail, userName, actionName);


									Globals.writeXMLFile(xmlDoc, hierarchyDIR, hierarchyXML);

									break; 
								}

							}else{
								if(cmngFromLogin){

									//if comming from login page indicate to person you cannot process
									for (int approv = 0; approv < emplyeeDetailsHT.size(); approv++) {
										Hashtable ApproverstempHT=new Hashtable();
										ApproverstempHT=(Hashtable)emplyeeDetailsHT.get(approv);
										userStatus=(String)ApproverstempHT.get("User_Status");		
										accessUniqID=(String)ApproverstempHT.get("Access_Unique_ID");

										System.out.println("accessUniqID:"+accessUniqID);
										System.out.println("userStatus & finalMsg :"+userStatus+"-->"+finalMsg);


										if(userStatus.equals(finalMsg)){

										}else{// check this status applicable for current user
											if(accessUniqID.equals(accessUniqIDcmnFrmLogin)){
												System.out.println(accessUniqIDcmnFrmLogin+"User Can Process Now  :"+accessUniqID);

												NextProcessHT.put("indicate", "None (User can process now)");
												NextProcessHT.put("LoginProcess", true);
												return  NextProcessHT;
											}else{

												System.out.println(accessUniqIDcmnFrmLogin+"User Can not Process Now :"+accessUniqID);

												NextProcessHT.put("indicate", "None (User can not process now)");
												NextProcessHT.put("LoginProcess", false);
												return  NextProcessHT;
											}
										}
									}
								}else{
									NextProcessHT.put("indicate", "None (Mail can not send to Next page)");
									NextProcessHT.put("LoginProcess", false);
									//send mail to bottom level next person
									//...
									boolean actionMovedNextUser = false;
									boolean mailSendAlready=false;
									for (int mail = 0; mail < emplyeeDetailsHT.size(); mail++) {

										Hashtable serialApproverstempHT=(Hashtable)emplyeeDetailsHT.get(mail);
										userStatus=(String)serialApproverstempHT.get("User_Status");		
										accessUniqID=(String)serialApproverstempHT.get("Access_Unique_ID");
										if(accessUniqID.equals(accessUniqIDcmnFrmLogin) && userStatus.equals(finalMsg)){
											mailSendAlready=true;
										}
										System.out.println(userStatus+"-=-=-=-=-userStatus=-=-=-=-="+finalMsg+"=--mailSendAlready--"+mailSendAlready);
										if(mailSendAlready && !userStatus.equals(finalMsg)){ // send mail to bottom level person
											mailSendAlready=false;
											int bottomlevelperson=mail;
											for (int k = bottomlevelperson; k < emplyeeDetailsHT.size(); k++) {

												Hashtable serialApproversHT=(Hashtable)emplyeeDetailsHT.get(k);

												String usermailID=(String)serialApproversHT.get("E-mail");	
												String usrname=(String)serialApproversHT.get("empName");	
												String activeFlag = serialApproversHT.get("Active") == null || String.valueOf(serialApproversHT.get("Active")).isEmpty() ? "true" : String.valueOf(serialApproversHT.get("Active"));
												Hashtable<String, String> loginDetailsHT = getLoginDetails(usrname, docEle.getAttribute("CustomerKey"));
												String disableFlag = loginDetailsHT.get("Disable") == null ? "false" : loginDetailsHT.get("Disable");;
												//fgf
												System.out.println(activeFlag+"-=-=-=-=-=-=disableFlag-=-=-=-=-=-="+disableFlag);
												if(activeFlag.equalsIgnoreCase("false") || disableFlag.equalsIgnoreCase("true"))
													continue;
												actionMovedNextUser = true;
												String userMailAddress[]={usermailID};
												String updateMail2DB = "";
												Hashtable mailDtailsHT=getMailDetailsFromConfig(xmlDoc,workFlowN, currentStageNO,userName, actionName, "Next", false, "");
												String mailSendBy=(String)mailDtailsHT.get("Mail_ID_Send_by");
												String mailPassword=(String)mailDtailsHT.get("Mail_Password");
												String mailMessage=(String)mailDtailsHT.get("Mail_Message");
												String bobyofMail=mailMessage;
												//												 String bobyofMail="Please Login to the Heiarchy Genarator to View the Progress.This is an Auto Genarated Email Don't reply to this Mail-ID";
												String subject = "Hierarchy ID / Name : "+heirarchyID+" / "+hierarchyName;	
												subject = mailDtailsHT.get("Subject") == null || String.valueOf(mailDtailsHT.get("Subject")).isEmpty() ? 
														"Workflow Name / Document Name : "+hierarchyName+" / "+ documentName : String.valueOf(mailDtailsHT.get("Subject"));
												String members = ""; 
												for(int t=0;t<emplyeeDetailsHT.size();t++) {
													Hashtable empHt = (Hashtable) emplyeeDetailsHT.get(t);
													members = members.isEmpty() ? (String)empHt.get("empName") : members+", "+(String)empHt.get("empName");
												}
												String toStage = (String)stageDetailsHT.get("Stage_Name");
												String dueDate = (String)stageDetailsHT.get("EffectiveEndDate");
												SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
												int diffInDays = 0;
												if(dueDate != null && !dueDate.trim().isEmpty()) {
													Date due = sdf.parse(dueDate);
													long diff = new Date().getTime() - due.getTime();
													diffInDays = (int) (diff / (1000 * 60 * 60 * 24));
												}else
													dueDate = "";
												bobyofMail = bobyofMail.replace("$ToStage$", toStage).replace("$DueDate$", dueDate).replace("$Days$", String.valueOf(diffInDays)).replaceAll("$Members$", members);
												subject = subject.replace("$ToStage$", toStage).replace("$DueDate$", dueDate).replace("$Days$", String.valueOf(diffInDays)).replaceAll("$Members$", members);
												updateMail2DB = usermailID; //code change Jayaramu 10APR14
												postMail(userMailAddress, subject, bobyofMail, mailSendBy,mailPassword, null, hierlink4Mail, docEle.getAttribute("CustomerKey"));
												break;
												/*FacesContext ctx1 = FacesContext.getCurrentInstance();
											ExternalContext extContext1 = ctx1.getExternalContext();
											Map sessionMap1 = extContext1.getSessionMap();
											HierarchyBean hryb = (HierarchyBean) sessionMap1.get("hierarchyBean");
											hryb.updateMailID(updateMail2DB); //code change Jayaramu 10APR14
												 */	
											}
											break;
										}

									} // end of  for (int mail = 0; mail < emplyeeDetailsHT.size(); mail++) {
									System.out.println(actionMovedNextUser+"-=-=-=-=-=-=actionMovedNextUser-=-=-=-=-=-="+cmngFromLogin);
									if(!actionMovedNextUser) {
										if(cmngFromLogin){
											//if comming from login page indicate to person you cannot process
											NextProcessHT.put("indicate", "None (This stage already completed). So you can not process now");	
											NextProcessHT.put("LoginProcess", false);
											break; 
										}else{
											NextProcessHT.put("indicate", "None (User Can Process) , Mail will be Send After Complete the Process");	
											NextProcessHT.put("LoginProcess", true);

											// set status on Stage Node and Work flow Node Stage_Status="WIP" MailSent="yes" Mail_Sent_Time="11-03-2014 05:32 PM"
											// send mail to Employees
											System.out.println(actionName+"-=-=-=-=-=-=actionMovedNextUser-=-=-=-=-=-="+currentStageNO);
											sendmail2Employees(stageN, currentStageNO,xmlDoc,workFlowN,heirarchyID,hierarchyName,hierlink4Mail, userName, actionName);


											Globals.writeXMLFile(xmlDoc, hierarchyDIR, hierarchyXML);

											break; 
										}
									}
									break;
								}
							}
						} // end of===> }else if(propertiesValue.equals("Parallel"))
					}

				}

			}

		}
		return NextProcessHT;
	}


	public  void sendmail2SerialProcess(Document xmlDoc,String finalMsg,Hashtable emplyeeDetailsHT,String heirarchyID,String hierarchyName,String hierlink4Mail,String curstage,
			String custKey){
		System.out.println("Entering: " + new Exception().getStackTrace()[0].getClassName() + "."
				+ new Exception().getStackTrace()[0].getMethodName());

		try{

			boolean mailSendAlready=false;
			int person=1;
			for (int mail = 0; mail < emplyeeDetailsHT.size(); mail++) {

				Hashtable serialApproverstempHT=(Hashtable)emplyeeDetailsHT.get(mail);
				String	userStatus=(String)serialApproverstempHT.get("User_Status");
				if(userStatus.equals(finalMsg)){
					mailSendAlready=true;
				}

				if(mailSendAlready && person<emplyeeDetailsHT.size()){ // send mail to bottom level person
					mailSendAlready=false;
					int bottomlevelperson=mail+1;
					Hashtable serialApproversHT=(Hashtable)emplyeeDetailsHT.get(bottomlevelperson);

					String usermailID=(String)serialApproversHT.get("E-mail");	
					String usrname=(String)serialApproversHT.get("empName");	
					String userMailAddress[]={usermailID};

					Hashtable mailDtailsHT=getMailDetailsFromConfig(xmlDoc,heirarchyID,curstage,usrname);
					String mailSendBy=(String)mailDtailsHT.get("Mail_ID_Send_by");
					String mailPassword=(String)mailDtailsHT.get("Mail_Password");
					String mailMessage=(String)mailDtailsHT.get("Mail_Message");
					String bobyofMail=mailMessage;
					//						 String bobyofMail="Please Login to the Heiarchy Genarator to View the Progress.This is an Auto Genarated Email Don't reply to this Mail-ID";
					String subject = "Hierarchy ID / Name : "+heirarchyID+" / "+hierarchyName;	
					postMail(userMailAddress, subject, bobyofMail, mailSendBy, mailPassword, null, hierlink4Mail, custKey);
					FacesContext ctx1 = FacesContext.getCurrentInstance();
					ExternalContext extContext1 = ctx1.getExternalContext();
					Map sessionMap1 = extContext1.getSessionMap();
					HierarchyBean hryb = (HierarchyBean) sessionMap1.get("hierarchyBean");
					hryb.updateMailID(usermailID); //code change Jayaramu 10APR14
					break;
				}
				person++;

			} // end of  for (int mail = 0; mail < emplyeeDetailsHT.size(); mail++) {

		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Exiting: " + new Exception().getStackTrace()[0].getClassName() + "."
				+ new Exception().getStackTrace()[0].getMethodName());
	}



	public  void sendmail2Employees(Node stageN, String currentStageNO,Document xmlDoc,Node workFlowN,String heirarchyID,String hierarchyName,String hierlink4Mail, String adminUsername, String actionName){
		System.out.println("Entering: " + new Exception().getStackTrace()[0].getClassName() + "."
				+ new Exception().getStackTrace()[0].getMethodName());

		try{

			// set status on Stage Node and Work flow Node Stage_Status="WIP" MailSent="yes" Mail_Sent_Time="11-03-2014 05:32 PM"
			Date lastAccDate=new Date();
			PropUtil prop = new PropUtil();
			String Dateformat = prop.getProperty("DATE_FORMAT");			
			Format formatter = new SimpleDateFormat(Dateformat);
			String accdate=formatter.format(lastAccDate);
			Element stageNodeEl = (Element) stageN;
			stageNodeEl.setAttribute("Stage_Status", "Completed");
			stageNodeEl.setAttribute("MailSent", "Yes");
			stageNodeEl.setAttribute("Mail_Sent_Time", accdate);

			Element WKflowNodeEl = (Element) workFlowN;
			String totalStage=WKflowNodeEl.getAttribute("Total_No_Stages");
			Element docEle = (Element)workFlowN.getParentNode();
			String documentName = docEle.getAttribute("DocumentName");
			int nextstageno=Integer.parseInt(currentStageNO)+1;
			String currentStgName = stageNodeEl.getAttribute("Stage_Name");
			ArrayList<String> ruleMailListAL = new ArrayList<>();
			Hashtable<String, String> detailsHT = RulesManager.applyRuleIfApplicable(workFlowN, currentStgName, String.valueOf(nextstageno), ruleMailListAL);
			System.out.println("-=-=-=-=-=-=detailsHT-=-=-=-=-=-="+detailsHT);
			String result = detailsHT.get("Result") == null ? "false" : detailsHT.get("Result").toString();
			if(result.equalsIgnoreCase("false")) {
				WKflowNodeEl.setAttribute("Current_Stage_Name", "Rules Failed");
				WKflowNodeEl.setAttribute("Current_Stage_No", "Rules Failed");
				String rulesStr = detailsHT.get("RulesStr") == null ? "false" : detailsHT.get("RulesStr").toString();
				ArrayList<String> emailList = new ArrayList<>();
				Hashtable nxtstageDetailsHT=retriveStageDetailsFromXML(workFlowN, String.valueOf(currentStageNO),"");
				Hashtable nxtemplyeeDetailsHT=(Hashtable)nxtstageDetailsHT.get("EmployeedetHT");
				
				for (int mail = 0; mail < nxtemplyeeDetailsHT.size(); mail++) {
					Hashtable mailApproversHT=new Hashtable();
					mailApproversHT=(Hashtable)nxtemplyeeDetailsHT.get(mail);
					String usermailID=(String)mailApproversHT.get("E-mail");	
					String usrname=(String)mailApproversHT.get("empName");	
					String activeFlag = mailApproversHT.get("Active") == null || String.valueOf(mailApproversHT.get("Active")).isEmpty() ? "true" : String.valueOf(mailApproversHT.get("Active"));
					Hashtable<String, String> loginDetailsHT = getLoginDetails(usrname, docEle.getAttribute("CustomerKey"));
					String disableFlag = loginDetailsHT.get("Disable") == null || String.valueOf(mailApproversHT.get("Disable")).isEmpty() ? "false" : String.valueOf(mailApproversHT.get("Disable"));;
					if(activeFlag.equalsIgnoreCase("false") || disableFlag.equalsIgnoreCase("true"))
						continue;
					emailList.add(usermailID);
				}
				
				nxtstageDetailsHT=retriveStageDetailsFromXML(workFlowN, String.valueOf(1),"");
				nxtemplyeeDetailsHT=(Hashtable)nxtstageDetailsHT.get("EmployeedetHT");
				
				for (int mail = 0; mail < nxtemplyeeDetailsHT.size(); mail++) {
					Hashtable mailApproversHT=new Hashtable();
					mailApproversHT=(Hashtable)nxtemplyeeDetailsHT.get(mail);
					String usermailID=(String)mailApproversHT.get("E-mail");	
					String usrname=(String)mailApproversHT.get("empName");	
					String activeFlag = mailApproversHT.get("Active") == null || String.valueOf(mailApproversHT.get("Active")).isEmpty() ? "true" : String.valueOf(mailApproversHT.get("Active"));
					Hashtable<String, String> loginDetailsHT = getLoginDetails(usrname, docEle.getAttribute("CustomerKey"));
					String disableFlag = loginDetailsHT.get("Disable") == null || String.valueOf(mailApproversHT.get("Disable")).isEmpty() ? "false" : String.valueOf(mailApproversHT.get("Disable"));;
					if(activeFlag.equalsIgnoreCase("false") || disableFlag.equalsIgnoreCase("true"))
						continue;
					if(!emailList.contains(usermailID))
					emailList.add(usermailID);
				}
				System.out.println(emailList+"-=-=-=-=-=-=nextStageNoStr-=-=-=-=-=-="+rulesStr);
				Hashtable mailDtailsHT=getMailDetailsFromConfig(xmlDoc,workFlowN,currentStageNO,adminUsername, "Rules Failed", "Next", true , "");
				String mailSendBy=(String)mailDtailsHT.get("Mail_ID_Send_by");
				String mailPassword=(String)mailDtailsHT.get("Mail_Password");
				String mailMessage=(String)mailDtailsHT.get("Mail_Message");
				String bobyofMail=mailMessage;
				String subject = mailDtailsHT.get("Subject") == null || String.valueOf(mailDtailsHT.get("Subject")).isEmpty() ? 
						"Workflow Name / Document Name : "+hierarchyName+" / "+ documentName : String.valueOf(mailDtailsHT.get("Subject"));
				bobyofMail = bobyofMail.replace("$RulesCondition$", rulesStr).replace("$RulesResult$", "Failed");
//				subject = subject.replace("$ToStage$", toStage).replace("$DueDate$", dueDate).replace("$Days$", String.valueOf(diffInDays)).replaceAll("$Members$", members);
				String userMailAddress[] = emailList.toArray(new String[emailList.size()]);//check Vishnu - for inactive user
				postMail(userMailAddress, subject, bobyofMail, mailSendBy, mailPassword, null, hierlink4Mail, docEle.getAttribute("CustomerKey"));
				return;
			}
			String nextStageNoStr = detailsHT.get("ToStageNo") == null ? String.valueOf(nextstageno) : detailsHT.get("ToStageNo").toString();
			System.out.println("-=-=-=-=-=-=nextStageNoStr-=-=-=-=-=-="+nextStageNoStr);
			nextstageno = Integer.valueOf(nextStageNoStr);
			int totstageno=Integer.parseInt(totalStage);
//			if(nextstageno>totstageno){
//				nextstageno=totstageno;
//			}

			
			Node nxtStageN = Globals.getNodeByAttrValUnderParent(xmlDoc, workFlowN, "Stage_No", String.valueOf(nextstageno));

			if(nxtStageN!=null){ // set Current status on work flow node										
				Element nxtstageEl = (Element) nxtStageN;
				String nextStageName=nxtstageEl.getAttribute("Stage_Name");
				String nextStageNo=nxtstageEl.getAttribute("Stage_No");

				WKflowNodeEl.setAttribute("Current_Stage_Name", nextStageName);
				WKflowNodeEl.setAttribute("Current_Stage_No", nextStageNo);
				System.out.println(nextStageNo+"-=-=-=-=-nextStageName=-=-=-=-="+nextStageName);
				//				send mail to next stage Reviewer
				//..
				/*FacesContext ctx = FacesContext.getCurrentInstance();
				ExternalContext extContext = ctx.getExternalContext();
				Map sessionMap = extContext.getSessionMap();
				LoginBean lgnbn = (LoginBean) sessionMap.get("loginBean");
				HierarchyBean hryb = (HierarchyBean) sessionMap.get("hierarchyBean");
				String adminUsername=lgnbn.getUsername();*/
				Hashtable nxtstageDetailsHT=retriveStageDetailsFromXML(workFlowN, String.valueOf(nextstageno),"");
				String externalUserFlag = nxtstageDetailsHT.get("ExternalUser") == null ? "" : nxtstageDetailsHT.get("ExternalUser").toString();
				String autoLoginFlag = nxtstageDetailsHT.get("Auto_Login") == null ? "" : nxtstageDetailsHT.get("Auto_Login").toString();
				String actionPrev = actionName;
				if(externalUserFlag.equalsIgnoreCase("true")) {
					actionName = "ExternalUser";
				}
				Hashtable mailDtailsHT=getMailDetailsFromConfig(xmlDoc,workFlowN,currentStageNO,adminUsername, actionName, "Next", true , "");
				actionName = actionPrev;
				String mailSendBy=(String)mailDtailsHT.get("Mail_ID_Send_by");
				String mailPassword=(String)mailDtailsHT.get("Mail_Password");
				String mailMessage=(String)mailDtailsHT.get("Mail_Message");
				String bobyofMail=mailMessage;
				//				String bobyofMail="Please Login to the Heiarchy Genarator to View the Progress.This is an Auto Genarated Email Don't reply to this Mail-ID";
				String subject = "Hierarchy ID / Name : "+heirarchyID+" / "+ hierarchyName;
				subject = mailDtailsHT.get("Subject") == null || String.valueOf(mailDtailsHT.get("Subject")).isEmpty() ? 
						"Workflow Name / Document Name : "+hierarchyName+" / "+ documentName : String.valueOf(mailDtailsHT.get("Subject"));
				
				Hashtable nxtemplyeeDetailsHT=(Hashtable)nxtstageDetailsHT.get("EmployeedetHT");
				Hashtable nxtPropertiesHT=(Hashtable)nxtstageDetailsHT.get("PropertiesHT");
				String Properties=(String)nxtPropertiesHT.get("Properties");

				String members = ""; 
				for(int i=0;i<nxtemplyeeDetailsHT.size();i++) {
					Hashtable empHt = (Hashtable) nxtemplyeeDetailsHT.get(i);
					members = members.isEmpty() ? (String)empHt.get("empName") : members+", "+(String)empHt.get("empName");
				}
				String toStage = (String)nxtstageDetailsHT.get("Stage_Name");
				String dueDate = (String)nxtstageDetailsHT.get("EffectiveEndDate");
				SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
				int diffInDays = 0;
				if(dueDate != null && !dueDate.trim().isEmpty()) {
					Date due = sdf.parse(dueDate);
					long diff = new Date().getTime() - due.getTime();
					diffInDays = (int) (diff / (1000 * 60 * 60 * 24));
				}else
					dueDate = "";
				bobyofMail = bobyofMail.replace("$ToStage$", toStage).replace("$DueDate$", dueDate).replace("$Days$", String.valueOf(diffInDays)).replaceAll("$Members$", members);
				subject = subject.replace("$ToStage$", toStage).replace("$DueDate$", dueDate).replace("$Days$", String.valueOf(diffInDays)).replaceAll("$Members$", members);
				String mailID4UpdateDB = "";
				boolean flagTemp = false;
				if(Properties.equals("Parallel")){  // send all the person
					ArrayList<String> emailList = new ArrayList<>();
					//String userMailAddress[] = new String[nxtemplyeeDetailsHT.size()];
					int mailsend=0;
					for (int mail = 0; mail < nxtemplyeeDetailsHT.size(); mail++) {
						Hashtable mailApproversHT=new Hashtable();
						mailApproversHT=(Hashtable)nxtemplyeeDetailsHT.get(mail);
						String usermailID=(String)mailApproversHT.get("E-mail");	
						String usrname=(String)mailApproversHT.get("empName");	
						String activeFlag = mailApproversHT.get("Active") == null || String.valueOf(mailApproversHT.get("Active")).isEmpty() ? "true" : String.valueOf(mailApproversHT.get("Active"));
						Hashtable<String, String> loginDetailsHT = getLoginDetails(usrname, docEle.getAttribute("CustomerKey"));
						String disableFlag = loginDetailsHT.get("Disable") == null || String.valueOf(mailApproversHT.get("Disable")).isEmpty() ? "false" : String.valueOf(mailApproversHT.get("Disable"));;
						if(activeFlag.equalsIgnoreCase("false") || disableFlag.equalsIgnoreCase("true"))
							continue;
						System.out.println("=====ruleMailListAL==="+ruleMailListAL);
						if(ruleMailListAL.isEmpty() || ruleMailListAL.contains(usermailID))
							emailList.add(usermailID);
						if(mailsend == 0){ //code change Jayaramu 10APR14
							mailID4UpdateDB = usermailID;
						}else{
							mailID4UpdateDB = usermailID+","+mailID4UpdateDB;
						}
						mailsend++;
					}
					if(!emailList.isEmpty()) {
						flagTemp = true;
						String userMailAddress[] = emailList.toArray(new String[emailList.size()]);  ;
						postMail(userMailAddress, subject, bobyofMail, mailSendBy, mailPassword, null, hierlink4Mail, docEle.getAttribute("CustomerKey"));
					}else {
						sendmail2Employees(nxtStageN, String.valueOf(nextstageno),xmlDoc,workFlowN,heirarchyID,hierarchyName,hierlink4Mail, adminUsername, actionName);
					}

				}else{ // send only one person to next stage who is available on first (Serial)
					Hashtable mailApproversHT=new Hashtable();
					mailApproversHT=(Hashtable)nxtemplyeeDetailsHT.get(0);
					String usermailID=(String)mailApproversHT.get("E-mail");	
					String usrname=(String)mailApproversHT.get("empName");	
					String activeFlag = mailApproversHT.get("Active") == null || String.valueOf(mailApproversHT.get("Active")).isEmpty() ? "true" : String.valueOf(mailApproversHT.get("Active"));
					Hashtable<String, String> loginDetailsHT = getLoginDetails(usrname, docEle.getAttribute("CustomerKey"));
					String disableFlag = loginDetailsHT.get("Disable") == null ? "false" : loginDetailsHT.get("Disable");;
					if(!activeFlag.equalsIgnoreCase("false") && !disableFlag.equalsIgnoreCase("true") && (ruleMailListAL.isEmpty() || ruleMailListAL.contains(usermailID))){
						String userMailAddress[] ={usermailID};
						mailID4UpdateDB = usermailID;	
						postMail(userMailAddress, subject, bobyofMail, mailSendBy, mailPassword, null, hierlink4Mail, docEle.getAttribute("CustomerKey"));
						flagTemp = true;
					}else {
						boolean flag = false;
						for (int mail = 1; mail < nxtemplyeeDetailsHT.size(); mail++) {
							mailApproversHT=(Hashtable)nxtemplyeeDetailsHT.get(mail);
							usermailID=(String)mailApproversHT.get("E-mail");	
							usrname=(String)mailApproversHT.get("empName");	
							activeFlag = mailApproversHT.get("Active") == null || String.valueOf(mailApproversHT.get("Active")).isEmpty() ? "true" : String.valueOf(mailApproversHT.get("Active"));
							loginDetailsHT = getLoginDetails(usrname, docEle.getAttribute("CustomerKey"));
							disableFlag = loginDetailsHT.get("Disable") == null ? "false" : loginDetailsHT.get("Disable");;
							if(!activeFlag.equalsIgnoreCase("false") && !disableFlag.equalsIgnoreCase("true")){
								flag = true;
								flagTemp = true;
								String userMailAddress[] ={usermailID};
								mailID4UpdateDB = usermailID;	
								postMail(userMailAddress, subject, bobyofMail, mailSendBy, mailPassword, null, hierlink4Mail, docEle.getAttribute("CustomerKey"));
								break;
							}
						}
						if(!flag) {
							sendmail2Employees(nxtStageN, String.valueOf(nextstageno),xmlDoc,workFlowN,heirarchyID,hierarchyName,hierlink4Mail, adminUsername, actionName);
						}
					}
				}
				if(flagTemp && (actionName.equalsIgnoreCase("Initiate") || actionName.equalsIgnoreCase("Remind"))) {
					mailDtailsHT=getMailDetailsFromConfig(xmlDoc,workFlowN,currentStageNO,adminUsername, actionName, "Initiator", false , "");
					mailSendBy=(String)mailDtailsHT.get("Mail_ID_Send_by");
					mailPassword=(String)mailDtailsHT.get("Mail_Password");
					mailMessage=(String)mailDtailsHT.get("Mail_Message");
					bobyofMail=mailMessage;
					subject = mailDtailsHT.get("Subject") == null || String.valueOf(mailDtailsHT.get("Subject")).isEmpty() ? 
							"Workflow Name / Document Name : "+hierarchyName+" / "+ documentName : String.valueOf(mailDtailsHT.get("Subject"));
					bobyofMail = bobyofMail.replace("$ToStage$", toStage).replace("$DueDate$", dueDate).replace("$Days$", String.valueOf(diffInDays)).replaceAll("$Members$", members);
					subject = subject.replace("$ToStage$", toStage).replace("$DueDate$", dueDate).replace("$Days$", String.valueOf(diffInDays)).replaceAll("$Members$", members);
					String userMailAddress[] = {adminUsername};//check Vishnu - for inactive user
					postMail(userMailAddress, subject, bobyofMail, mailSendBy, mailPassword, null, hierlink4Mail, docEle.getAttribute("CustomerKey"));
				}
				//				hryb.updateMailID(mailID4UpdateDB); //code change Jayaramu 10APR14
			}

			//if Current stage is last stage you can set work floe stage status
			String totalNoofStage=WKflowNodeEl.getAttribute("Total_No_Stages");
			String currentStageNo=stageNodeEl.getAttribute("Stage_No");
			String currentStageName=stageNodeEl.getAttribute("Stage_Name");
			if(totalNoofStage.equals(currentStageNo)){
				WKflowNodeEl.setAttribute("Current_Stage_Name", currentStageName);
				WKflowNodeEl.setAttribute("Current_Stage_No", "Completed");
				Date dt = new Date();
//				String Dateformat1 = prop.getProperty("DATE_FORMAT");	
				SimpleDateFormat sdf = new SimpleDateFormat(Dateformat);
				WKflowNodeEl.setAttribute("WorkflowEndDate", sdf.format(dt));

				// CODE CHANGE PANDIAN 21Mar2014 disable/enable icons
				/*FacesContext ctx1 = FacesContext.getCurrentInstance();
				ExternalContext extContext1 = ctx1.getExternalContext();
				Map sessionMap1 = extContext1.getSessionMap();
				SecurityBean secbn = (SecurityBean) sessionMap1.get("securityBean");
				secbn.setGenerateData("true");
				secbn.setReGenerateHierarchy("true");*/

				//				send mail to Everyone
				//..
				int totNoofStage=Integer.parseInt(totalNoofStage);
				// get all MailID
				Hashtable allusermailHT=new Hashtable();
				int allusermail=0;

				/*FacesContext ctx = FacesContext.getCurrentInstance();
				ExternalContext extContext = ctx.getExternalContext();
				Map sessionMap = extContext.getSessionMap();
				LoginBean lgnbn = (LoginBean) sessionMap.get("loginBean");
				String adminUsername=lgnbn.getUsername();*/
				Hashtable mailDtailsHT=getMailDetailsFromConfig(xmlDoc,workFlowN,currentStageNO,adminUsername, "Finished", "Next", false , "");
				String mailSendBy=(String)mailDtailsHT.get("Mail_ID_Send_by");
				String mailPassword=(String)mailDtailsHT.get("Mail_Password");
				String mailMessage=(String)mailDtailsHT.get("Mail_Message");
				String bobyofMail=mailMessage;
				//				String bobyofMail="Please Login to the Heiarchy Genarator to View the Progress.This is an Auto Genarated Email Don't reply to this Mail-ID";
				String subject = "Hierarchy ID / Name : "+heirarchyID+" / "+ hierarchyName;
				subject = mailDtailsHT.get("Subject") == null || String.valueOf(mailDtailsHT.get("Subject")).isEmpty() ? 
						"Workflow Name / Document Name : "+hierarchyName+" / "+ documentName : String.valueOf(mailDtailsHT.get("Subject"));

				bobyofMail = bobyofMail.replace("$ToStage$", "Completed").replace("$DueDate$", "Completed");
				subject = subject.replace("$ToStage$", "Completed").replace("$DueDate$", "Completed");
				for(int totstg=0; totstg<totNoofStage; totstg++){
					int stageno=totstg+1;
					Hashtable allstageDetailsHT=retriveStageDetailsFromXML(workFlowN, String.valueOf(stageno),"");
					Hashtable allemplyeeDetailsHT=(Hashtable)allstageDetailsHT.get("EmployeedetHT");
					Hashtable allPropertiesHT=(Hashtable)allstageDetailsHT.get("PropertiesHT");
					Hashtable allmssgeDetailsHT=(Hashtable)allstageDetailsHT.get("MessagedetHT");
					String Properties=(String)allPropertiesHT.get("Properties");
					String allfinalMsg=(String)allmssgeDetailsHT.get("Final");
					String externalUser = allstageDetailsHT.get("ExternalUser") == null ? "" : allstageDetailsHT.get("ExternalUser").toString();
					String autoLoginFlag = allstageDetailsHT.get("Auto_Login") == null ? "" : allstageDetailsHT.get("Auto_Login").toString();
					if(externalUser.equalsIgnoreCase("true")) {
						continue;
					}
					for (int emp = 0; emp < allemplyeeDetailsHT.size(); emp++) {
						Hashtable allApproversHT=new Hashtable();
						allApproversHT=(Hashtable)allemplyeeDetailsHT.get(emp);
						String userStatus=(String)allApproversHT.get("User_Status");	
						String usermailID=(String)allApproversHT.get("E-mail");	
						String usrname=(String)allApproversHT.get("empName");
						String activeFlag = allApproversHT.get("Active") == null || String.valueOf(allApproversHT.get("Active")).isEmpty() ? "true" : String.valueOf(allApproversHT.get("Active"));
						Hashtable<String, String> loginDetailsHT = getLoginDetails(usrname, docEle.getAttribute("CustomerKey"));
						String disableFlag = loginDetailsHT.get("Disable") == null ? "false" : loginDetailsHT.get("Disable");;
						if(userStatus.equals(allfinalMsg) && !activeFlag.equalsIgnoreCase("false") && !disableFlag.equalsIgnoreCase("true") && !allusermailHT.containsValue(usermailID)){
							allusermailHT.put(allusermail, usermailID);
							allusermail++;
						}
					}
				}// end of for(int totstg=0; totstg<totNoofStage; totstg++){
				if(!docEle.getAttribute("Created_By").trim().isEmpty() && !allusermailHT.containsValue(docEle.getAttribute("Created_By")))
					allusermailHT.put(allusermailHT.size(), docEle.getAttribute("Created_By"));
				String mailID4UpdateDB = "";
				String userMailAddress[]=new String[allusermailHT.size()];
				for(int ml=0; ml<allusermailHT.size(); ml++){
					userMailAddress[ml]=(String)allusermailHT.get(ml);
					if(ml == 0){ //code change Jayaramu 11APR14
						mailID4UpdateDB = (String)allusermailHT.get(ml);
					}else{
						mailID4UpdateDB = (String)allusermailHT.get(ml)+","+mailID4UpdateDB;
					}

				}
				if(userMailAddress.length > 0)
					postMail(userMailAddress, subject, bobyofMail, mailSendBy, mailPassword, null, hierlink4Mail, docEle.getAttribute("CustomerKey"));
				if(actionName.equalsIgnoreCase("Initiate") || actionName.equalsIgnoreCase("Remind")) {
					mailDtailsHT=getMailDetailsFromConfig(xmlDoc,workFlowN,currentStageNO,adminUsername, actionName, "Initiator", false , "");
					mailSendBy=(String)mailDtailsHT.get("Mail_ID_Send_by");
					mailPassword=(String)mailDtailsHT.get("Mail_Password");
					mailMessage=(String)mailDtailsHT.get("Mail_Message");
					bobyofMail=mailMessage;
					subject = mailDtailsHT.get("Subject") == null || String.valueOf(mailDtailsHT.get("Subject")).isEmpty() ? 
							"Workflow Name / Document Name : "+hierarchyName+" / "+ documentName : String.valueOf(mailDtailsHT.get("Subject"));
					bobyofMail = bobyofMail.replace("$ToStage$", "Completed").replace("$DueDate$", "Completed");
					subject = subject.replace("$ToStage$", "Completed").replace("$DueDate$", "Completed");
					String[] userMailAddress1 = {adminUsername};//check Vishnu - for inactive user
					postMail(userMailAddress1, subject, bobyofMail, mailSendBy, mailPassword, null, hierlink4Mail, docEle.getAttribute("CustomerKey"));
				}
				/*HierarchyBean hryb = (HierarchyBean) sessionMap.get("hierarchyBean");
				hryb.updateMailID(mailID4UpdateDB); //code change Jayaramu 11APR14
				 */			}

		}catch(Exception e){
			e.printStackTrace();
		}

		System.out.println("Exiting: " + new Exception().getStackTrace()[0].getClassName() + "."
				+ new Exception().getStackTrace()[0].getMethodName());
	}


	public Hashtable getStageDetails(String heirCode, String stageNo,String comingFrom)
	{
		System.out.println("Entering: " + new Exception().getStackTrace()[0].getClassName() + "."
				+ new Exception().getStackTrace()[0].getMethodName());
		Hashtable stagedetHT=new Hashtable<>();
		try{
			PropUtil prop=new PropUtil();
			String heirLevelXML=prop.getProperty("HIERARCHY_XML_FILE");
			String heirLeveldir=prop.getProperty("HIERARCHY_XML_DIR");
			Document xmlDoc=Globals.openXMLFile(heirLeveldir, heirLevelXML);
			Node workFlowN=Globals.getNodeByAttrVal(xmlDoc, "Workflow", "Hierarchy_ID", heirCode);

			stagedetHT = retriveStageDetailsFromXML(workFlowN, stageNo, comingFrom);

		}catch(Exception e)
		{
			e.printStackTrace();
		}

		System.out.println("Exiting: " + new Exception().getStackTrace()[0].getClassName() + "."
				+ new Exception().getStackTrace()[0].getMethodName());
		return stagedetHT;

	}

	public static Hashtable retriveStageDetailsFromXML(Node workFlowN, String stageNo, String comingFrom) throws Exception{
		Hashtable stagedetHT=new Hashtable<>();
		NodeList stageNL=workFlowN.getChildNodes();

		int key=0,stageKey=0,msgKey=0,empKey=0;

		for(int i=0;i<stageNL.getLength();i++)
		{
			Node stageN=stageNL.item(i);

			if(stageN.getNodeType()==Node.ELEMENT_NODE)
			{
				if(stageN.getNodeName().equalsIgnoreCase("Stage")&&stageN.getAttributes().getNamedItem("Stage_No").getNodeValue().equalsIgnoreCase(stageNo))
				{

					stagedetHT=Globals.getAttributeNameandValHT(stageN);
					NodeList msgANDNameNL=stageN.getChildNodes();

					for(int j=0;j<msgANDNameNL.getLength();j++)
					{

						Node nodeCheckN = msgANDNameNL.item(j);

						if (nodeCheckN.getNodeType() == nodeCheckN.ELEMENT_NODE) {

							if(nodeCheckN.getNodeName().equalsIgnoreCase("Status_Codes"))
							{

								NodeList MsgNL=nodeCheckN.getChildNodes();
								Hashtable messagedetHT=new Hashtable<>();
								for(int k=0;k<MsgNL.getLength();k++)
								{
									Node msgN = MsgNL.item(k);
									if(msgN.getNodeType()==Node.ELEMENT_NODE)
									{
										String levelNo=msgN.getAttributes().getNamedItem("level").getNodeValue();
										String msg=msgN.getTextContent();
										if(levelNo==null){levelNo="";}
										if(msg==null){msg="";}

										messagedetHT.put(levelNo,msg);
										msgKey++;
									}

								}
								stagedetHT.put("MessagedetHT",messagedetHT);


							}else if(nodeCheckN.getNodeName().equalsIgnoreCase("Employee_Names"))
							{

								NodeList empNameNL=nodeCheckN.getChildNodes();
								Hashtable empNameHT=new Hashtable<>();
								for(int k=0;k<empNameNL.getLength();k++)
								{
									Hashtable nameHT=new Hashtable<>();
									Node nameN = empNameNL.item(k);
									if(nameN.getNodeType()==Node.ELEMENT_NODE)
									{

										String stgName=(String)stagedetHT.get("Stage_Name");
										if(stgName.equals("Admin")){

										}else{
											Hashtable empHT=Globals.getAttributeNameandValHT(nameN);
											String role=(String)empHT.get("User_Role");
											if(!comingFrom.equalsIgnoreCase("getStageInfo"))//Code Change Gokul 07MAY2013
											{
												if(role.equals("Admin")){
													continue;
												}
											}
										}



										nameHT=Globals.getAttributeNameandValHT(nameN);
										nameHT.put("empName",nameN.getTextContent());
										empNameHT.put(empKey,nameHT);
										empKey++;
									}


								}

								stagedetHT.put("EmployeedetHT",empNameHT);

							}else if(nodeCheckN.getNodeName().equalsIgnoreCase("Escalate_Managers"))
							{

								NodeList empNameNL=nodeCheckN.getChildNodes();
								Hashtable empNameHT=new Hashtable<>();
								empKey=0;
								for(int k=0;k<empNameNL.getLength();k++)
								{
									Hashtable nameHT=new Hashtable<>();
									Node nameN = empNameNL.item(k);
									if(nameN.getNodeType()==Node.ELEMENT_NODE)
									{

										String stgName=(String)stagedetHT.get("Stage_Name");
										if(stgName.equals("Admin")){

										}else{
											Hashtable empHT=Globals.getAttributeNameandValHT(nameN);
											String role=(String)empHT.get("User_Role");
											if(!comingFrom.equalsIgnoreCase("getStageInfo"))//Code Change Gokul 07MAY2013
											{
												if(role != null && role.equals("Admin")){
													continue;
												}
											}
										}


										if(nameN.getTextContent().trim().isEmpty())
											continue;
										nameHT=Globals.getAttributeNameandValHT(nameN);
										nameHT.put("empName",nameN.getTextContent());
										empNameHT.put(empKey,nameHT);
										empKey++;
									}


								}

								stagedetHT.put("EscalateManagersHT",empNameHT);

							}else if(nodeCheckN.getNodeName().equalsIgnoreCase("Properties"))
							{
								Hashtable propHT=new Hashtable<>();
								String cncuren = "";
								String minApprovalsPercent = "";
								String parallelType = "";
								if(nodeCheckN.getAttributes().getNamedItem("Concurrency")!=null){
									cncuren=nodeCheckN.getAttributes().getNamedItem("Concurrency").getNodeValue();
								}
								if(nodeCheckN.getAttributes().getNamedItem("Parallel_Type")!=null){
									parallelType=nodeCheckN.getAttributes().getNamedItem("Parallel_Type").getNodeValue();
								}
								if(cncuren.equalsIgnoreCase("Parallel"))
								{
									
									String minApprovals=nodeCheckN.getAttributes().getNamedItem("Min_Approvers").getNodeValue();
									propHT.put("Min_Approvers",minApprovals);
									Element minPercent = (Element)nodeCheckN;
									minApprovalsPercent=minPercent.getAttribute("Min_Approvers_Percent");

								}

								propHT.put("Min_Approvers_Percent",minApprovalsPercent);
								propHT.put("Properties",cncuren);
								propHT.put("Parallel_Type",parallelType);
								propHT.put("Concurrency",cncuren);
								stagedetHT.put("PropertiesHT",propHT);
							}

						}
					}

				}
			}


		}
		System.out.println("Stage Details HT: "+stagedetHT); 
		return stagedetHT;
	}
	
	
	

	public static String returnLOGOpath(Document docXmlDoc,String docID,String user) {
		
		String customerLogopath="";
		System.out.println(docID+" : docID*************user  :"+ user);
		
		Node docNd=Globals.getNodeByAttrVal(docXmlDoc, "Document", "Document_ID", docID);
		Element docNdEle = (Element) docNd;
		String createByUser= docNdEle.getAttribute("Created_By")==null ?"": docNdEle.getAttribute("Created_By");
		String userCustomerKey= docNdEle.getAttribute("CustomerKey")==null ?"": docNdEle.getAttribute("CustomerKey");;
		// Created_By="vignesh@cygnussoftwares.com"
		String Login_ID="";
		
		Hashtable userHT = getLoginDetails(user, docNdEle.getAttribute("CustomerKey"));
		
		String cxmlCustomerKey = (String) userHT.get("CustomerKey");
		
		System.out.println(cxmlCustomerKey+" : cxmlCustomerKey*************userCustomerKey  :"+userCustomerKey);
		System.out.println(user+" : cxmlCustomerKey*************createByUser  :"+createByUser);
		//if(cxmlCustomerKey.equals(userCustomerKey))
		//{
			
			Login_ID = (String) userHT.get("Login_ID");
			customerLogopath= getCustomerLOGOPath(userCustomerKey);
		//}
		
		

		System.out.println(Login_ID+" : Login_ID*****customerLogopath********user  :"+ customerLogopath);
		
		return customerLogopath;
		
		
	}
	
	
	
	
	

	public void postMail(String recipients[], String subject, String message,
			String from, String emailPassword, String[] files,String hostNameWthPrtNum, String custKey) throws Exception { //code change Jayaramu 03FEB14
		System.out.println("Entering: " + new Exception().getStackTrace()[0].getClassName() + "."
				+ new Exception().getStackTrace()[0].getMethodName());
		boolean debug = false;
		//  java.security.Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider()); // ?? Commented - Devan
		PropUtil prop = new PropUtil();
		String hierarchyDIR = prop.getProperty("HIERARCHY_XML_DIR");
		String configFileName=prop.getProperty("HIERARCHY_CONFIG_FILE");
		Document doc = Globals.openXMLFile(hierarchyDIR, configFileName);
		LoginProcessManager loginMethod=new LoginProcessManager();
		Element mailConfEle = (Element)doc.getElementsByTagName("Mail_Configuration").item(0);
		Node sigNd = mailConfEle.getElementsByTagName("Signature").item(0);
		Node hostNd = mailConfEle.getElementsByTagName("Mail_Host").getLength() <= 0 ? null : mailConfEle.getElementsByTagName("Mail_Host").item(0);
		String signature = sigNd.getTextContent().trim();
		String HOST_NAME = hostNd == null ? "smtp.prodentechnologies.net" : hostNd.getTextContent();
		String messageBody;
		System.out.println(HOST_NAME+": HOST_NAME :"+from+": from :"+emailPassword);
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
		for(int j=0;j<recipients.length;j++) {
			InternetAddress[] addressTo = new InternetAddress[1];
			//		for (int i = 0; i < recipients.length; i++) {
			String userName=recipients[j];
			
			
			addressTo[0] = new InternetAddress(recipients[j]);
			
			//		}
			msg.setRecipients(Message.RecipientType.TO, addressTo);

			// Setting the Subject and Content Type
			msg.setSubject(subject);
			String message1 = message;
			message1 = message1.replace("$userName$", recipients[j]);
			message1 = message1.replace("$userid$", Inventory.encrypt4DotNet(recipients[j]));
//			System.out.println("----------------^^^^^^^^^^^^^^------------------"+message1);
			Hashtable userHT = getLoginDetails(recipients[j], custKey);
//			String customerKEY= loginMethod.getASPcustomerKey(recipients[j]);
//			String cxmlCustomerKey = (String) userHT.get("CustomerKey");
			String customerLOGOPath= loginMethod.getCustomerLOGOPath("CustomerKey");
			
			message1 = message1.replace("$userName$", recipients[j]).replace("$customerLOGOPath$", customerLOGOPath);
			System.out.println(String.valueOf(userHT.get("Access_Unique_ID"))+"----------------^^^^^^^^^^^^^^------------------"+message1);
			String t = userHT.get("First_Name") == null ? "" : String.valueOf(userHT.get("First_Name"));
			String t1 = userHT.get("Last_Name") == null ? "" : String.valueOf(userHT.get("Last_Name"));
			String name = t+" "+t1;
			name = name.trim().equals("") ? recipients[j] : name.trim();
			name = name==null || name.equalsIgnoreCase("null") ? recipients[j] : name;
			message1 = message1.replace("$User$", name).replace("$Notes$", "");
			
			System.out.println(userHT+"recipients[i]========================>>>>>>>>>>>>>>"+recipients[j]);
//			if(!message1.contains("Regards"))
//				message1 = message1+signature;
			msg.setContent(message1, "text/html");

			//	        headers.addHeader("Content-type", "text/html; charset=UTF-8");
			//	        String html = "Test\n" + "Click Here" + "\n<a href='http://test.com'>Test.com</a>";
			//	        
			MimeBodyPart messageBodyPart = new MimeBodyPart();
			//		message = message+"<br/>"+ "<a href=\""+hostNameWthPrtNum+"\">Please login in the Heirarchy Genarator to review the Workflow details.</a> <br/><br/><br/>"+"Regards,<br/>"+
			//				"accel-BI Team";

			messageBodyPart.setText(message1,"UTF-8","html");

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

			System.out.println("Mail sent sucessfully to "+recipients[j]);
		}
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


	public Hashtable doRejectionConfiguration(Document levelDoc, Node WfNode,String heirCode,String currentStageNo,String curUserId,
			Hashtable stageDetailsHT,Hashtable messagesHT,Hashtable empNMHT,Hashtable propHT,String currentuserAccUID,String hierarchyName)
	{
		System.out.println("Entering: " + new Exception().getStackTrace()[0].getClassName() + "."
				+ new Exception().getStackTrace()[0].getMethodName());
		try{
			//				PropUtil prop=new PropUtil();
			//				String heirLevelXML=prop.getProperty("HIERARCHY_XML_FILE");
			//				String heirLeveldir=prop.getProperty("HIERARCHY_XML_DIR");
			//				Document xmlDoc=Globals.openXMLFile(heirLeveldir, heirLevelXML);
			//				Node workFlowN=Globals.getNodeByAttrVal(levelDoc, "Workflow", "Hierarchy_ID", heirCode);			
			//				Node stageN = Globals.getNodeByAttrValUnderParent(levelDoc, workFlowN, "Stage_No", String.valueOf(currentStageNo));	
			//				LoginProcessManager lgn=new LoginProcessManager();
			//				Hashtable stageDetailsHT =lgn.getStageDetails(heirCode,currentStageNo);
			//				Hashtable messagesHT =(Hashtable)stageDetailsHT.get("MessagedetHT");
			//				Hashtable empNMHT =(Hashtable)stageDetailsHT.get("EmployeedetHT");
			//				Hashtable propHT =(Hashtable)stageDetailsHT.get("PropertiesHT");
			Element docEle = (Element)WfNode.getParentNode();

			String stagefinalStatus=(String)messagesHT.get("Final");
			String Rejected_Return_To =(String)stageDetailsHT.get("Rejected_Return_To");
			String Rejected_Set_Status_To =(String)stageDetailsHT.get("Rejected_Set_Status_To");
			String Rejected_Notification_To =(String)stageDetailsHT.get("Rejected_Notification_To");
			String concurrency =(String)propHT.get("Concurrency");			
			System.out.println("Rejected_Set_Status_To "+Rejected_Set_Status_To);
			System.out.println("Rejected_Return_To "+Rejected_Return_To);
			System.out.println("Rejected_Notification_To "+Rejected_Notification_To);			
			String returnToUser="";
			if(Rejected_Return_To==null){
				System.out.println("********ERROR****Rejection Configuration not Configured yet Now (Not in Xml)");
				Rejected_Return_To = "";
			}
			String toStageNo = "";
			System.out.println("Something here...+++++++++++++++");
			String rejectedReturnTosplit=Rejected_Return_To.isEmpty()?"":Rejected_Return_To;
			String[] arrOfStr = rejectedReturnTosplit.split("~", 3); 
			System.out.println("rejectedReturnTosplit   "+rejectedReturnTosplit);
			//Rejected Return To
			if(Rejected_Return_To.equalsIgnoreCase("Previous_Member")){  // member			
				returnToUser=settingStatus4PrevoiusAffectedMember(levelDoc,WfNode, empNMHT, currentStageNo, curUserId, Rejected_Set_Status_To,
						propHT);
				toStageNo = currentStageNo;
			}else if(Rejected_Return_To.equalsIgnoreCase("Previous_Stage"))  // previews Stage First Member
			{
				int stageNo=Integer.parseInt(currentStageNo);
				int preStage=stageNo-1;
				if(preStage==0){
					preStage=1;
				}
				toStageNo = String.valueOf(preStage);
				Hashtable prevStageDetailsHT=retriveStageDetailsFromXML(WfNode, String.valueOf(preStage),"");
				String externalUserFlag = prevStageDetailsHT.get("ExternalUser") == null ? "" : prevStageDetailsHT.get("ExternalUser").toString();
				String autoLoginFlag = prevStageDetailsHT.get("Auto_Login") == null ? "" : prevStageDetailsHT.get("Auto_Login").toString();
				if(externalUserFlag.equalsIgnoreCase("true") && preStage > 1) {
					preStage=preStage-1;
				}
				returnToUser=settingStatus4StageFirstMember(levelDoc,WfNode,String.valueOf(preStage),currentStageNo, Rejected_Set_Status_To,currentuserAccUID,curUserId);
			}else if(Rejected_Return_To.equalsIgnoreCase("First_Stage"))  // First Stage First Member
			{
				returnToUser=settingStatus4StageFirstMember(levelDoc,WfNode,"1",currentStageNo, Rejected_Set_Status_To,currentuserAccUID,curUserId);
				toStageNo = "1";
			}
			else if(arrOfStr[0].equalsIgnoreCase("Goto_Stage"))
			{
				System.out.println("rejectedReturnTosplit   "+arrOfStr[0]);
				returnToUser=settingStatus4StageFirstMember(levelDoc,WfNode,arrOfStr[2],currentStageNo, Rejected_Set_Status_To,currentuserAccUID,curUserId);
				toStageNo = arrOfStr[2];
			}

			String documentName = Globals.getAttrVal4AttrName(WfNode.getParentNode(), "DocumentName");
			//Mail Details
//			String subject = "Workflow Name / Document Name : "+hierarchyName+" / "+ documentName;

			PropUtil prop = new PropUtil();
			String hierlink4Mail= prop.getProperty("HIERARCHY_BASE_URL");
			Hashtable mailDtailsHT=getMailDetailsFromConfig(levelDoc,WfNode,currentStageNo,curUserId, "Rejected", "Next", false , "");
			String mailSendBy=(String)mailDtailsHT.get("Mail_ID_Send_by");
			String mailPassword=(String)mailDtailsHT.get("Mail_Password");
			String mailMessage=(String)mailDtailsHT.get("Mail_Message");
			String bobyofMail=mailMessage;
			String subject = mailDtailsHT.get("Subject") == null || String.valueOf(mailDtailsHT.get("Subject")).isEmpty() ? 
					"Workflow Name / Document Name : "+hierarchyName+" / "+ documentName : String.valueOf(mailDtailsHT.get("Subject"));
			Hashtable currentstageDetailsHT=retriveStageDetailsFromXML(WfNode, String.valueOf(toStageNo),"");
			Hashtable currentemplyeeDetailsHT=(Hashtable)currentstageDetailsHT.get("EmployeedetHT");
			String members = ""; 
			for(int t=0;t<currentemplyeeDetailsHT.size();t++) {
				Hashtable empHt = (Hashtable) currentemplyeeDetailsHT.get(t);
				members = members.isEmpty() ? (String)empHt.get("empName") : members+", "+(String)empHt.get("empName");
			}
			String toStage = (String)currentstageDetailsHT.get("Stage_Name");
			String dueDate = (String)currentstageDetailsHT.get("EffectiveEndDate");
			SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
			int diffInDays = 0;
			if(dueDate != null && !dueDate.trim().isEmpty()) {
				Date due = sdf.parse(dueDate);
				long diff = new Date().getTime() - due.getTime();
				diffInDays = (int) (diff / (1000 * 60 * 60 * 24));
			}else
				dueDate = "";
			bobyofMail = bobyofMail.replace("$ToStage$", toStage).replace("$DueDate$", dueDate).replace("$Days$", String.valueOf(diffInDays)).replaceAll("$Members$", members);
			subject = subject.replace("$ToStage$", toStage).replace("$DueDate$", dueDate).replace("$Days$", String.valueOf(diffInDays)).replaceAll("$Members$", members);



			//Send Rejected Notification To
			if(Rejected_Notification_To.equalsIgnoreCase("Affected_Members")){ // only one person

				int currentStageno=Integer.parseInt(currentStageNo);

				for(int i=1;i<=currentStageno;i++)
				{
					Hashtable stageDetHT=retriveStageDetailsFromXML(WfNode, String.valueOf(i),"");
					Hashtable memdetHT=(Hashtable)stageDetHT.get("EmployeedetHT");
					Hashtable stageaffectedmemHT=gettingLastAccOrAffectedMembers(memdetHT, "affectedMembersDetails", curUserId);

					System.out.println("stageaffectedmemHT "+stageaffectedmemHT+" "+stageaffectedmemHT.size());
					if(stageaffectedmemHT!=null&&stageaffectedmemHT.size()!=0)
					{
						String test = ((Element)WfNode.getParentNode()).getAttribute("Created_By");
						Hashtable<String, String> testHT = new Hashtable<>();
						testHT.put("E-mail", test);
						testHT.put("empName", test);
						stageaffectedmemHT.put(stageaffectedmemHT.size(), testHT);
						sendmailFromAdmin(stageaffectedmemHT, subject, bobyofMail, hierlink4Mail, mailSendBy, mailPassword, docEle.getAttribute("CustomerKey"));
					}
				}

			}else if(Rejected_Notification_To.equalsIgnoreCase("All_Members_in_Stage")){

				//Getting index position of Current User
				int userPos=0;
				String curUserAccUID="";
				for(int i=0;i<empNMHT.size();i++)
				{
					Hashtable indEmpHT =(Hashtable)	empNMHT.get(i);
					String accUNamexml=(String)indEmpHT.get("empName");
					if(curUserId.equalsIgnoreCase(accUNamexml))
					{	
						curUserAccUID=(String)indEmpHT.get("Access_Unique_ID");
						userPos=i;
						break;
					}
				}

				//deside which Stage to Proceed
				int sendMailStageNo=0;
				if(concurrency.equalsIgnoreCase("serial"))
				{
					if(userPos==0)
					{
//						if(!currentStageNo.equals("1"))
//						{
//							sendMailStageNo=Integer.parseInt(currentStageNo)-1;
//						}else
//						{
							sendMailStageNo=Integer.parseInt(currentStageNo);	
//						}
					}
					else
					{
						sendMailStageNo=Integer.parseInt(currentStageNo);
					}
				}else
				{
					sendMailStageNo=Integer.parseInt(currentStageNo);
				}



				Hashtable sendMailStageHT =retriveStageDetailsFromXML(WfNode,String.valueOf(sendMailStageNo),"");
				Hashtable sendmailempNMHT =(Hashtable)sendMailStageHT.get("EmployeedetHT");

				if(sendmailempNMHT!=null&&sendmailempNMHT.size()!=0)
				{
					String test = ((Element)WfNode.getParentNode()).getAttribute("Created_By");
					Hashtable<String, String> testHT = new Hashtable<>();
					testHT.put("E-mail", test);
					testHT.put("empName", test);
					sendmailempNMHT.put(sendmailempNMHT.size(), testHT);
					sendmailFromAdmin(sendmailempNMHT, subject, bobyofMail, hierlink4Mail, mailSendBy, mailPassword, docEle.getAttribute("CustomerKey"));
				}



			}else if(Rejected_Notification_To.equalsIgnoreCase("All_Members_in_WF")){


				//				Node workFlowN=Globals.getNodeByAttrVal(levelDoc, "Workflow", "Hierarchy_ID", heirCode);
				int totalStages=Integer.parseInt(WfNode.getAttributes().getNamedItem("Total_No_Stages").getNodeValue());

				for(int i=1;i<=totalStages;i++)
				{
					Hashtable stageDetHT=retriveStageDetailsFromXML(WfNode, String.valueOf(i),"");
					Hashtable memdetHT=(Hashtable)stageDetHT.get("EmployeedetHT");
					if(memdetHT!=null&&memdetHT.size()!=0){
						String test = ((Element)WfNode.getParentNode()).getAttribute("Created_By");
						Hashtable<String, String> testHT = new Hashtable<>();
						testHT.put("E-mail", test);
						testHT.put("empName", test);
						memdetHT.put(memdetHT.size(), testHT);
						sendmailFromAdmin(memdetHT, subject, bobyofMail, hierlink4Mail, mailSendBy, mailPassword, docEle.getAttribute("CustomerKey"));
					}
				}

			}

		}catch(Exception e)
		{
			e.printStackTrace();
		}

		System.out.println("Exiting: " + new Exception().getStackTrace()[0].getClassName() + "."
				+ new Exception().getStackTrace()[0].getMethodName());
		return null;
	}
	
	
	
	
	
	
	public Hashtable sendMailResubmit(Document levelDoc, Node WfNode,String heirCode,String currentStageNo,String curUserId,
			Hashtable stageDetailsHT,Hashtable messagesHT,Hashtable empNMHT,Hashtable propHT,String currentuserAccUID,String hierarchyName)
	
	{
		
		System.out.println("Entering: " + new Exception().getStackTrace()[0].getClassName() + "."
				+ new Exception().getStackTrace()[0].getMethodName());
		try {
		
			String stagefinalStatus=(String)messagesHT.get("Final");
			String Rejected_Return_To =(String)stageDetailsHT.get("Rejected_Return_To");
			String Rejected_Set_Status_To =(String)stageDetailsHT.get("Rejected_Set_Status_To");
			String Rejected_Notification_To =(String)stageDetailsHT.get("Rejected_Notification_To");
			String concurrency =(String)propHT.get("Concurrency");	
			String documentName = Globals.getAttrVal4AttrName(WfNode.getParentNode(), "DocumentName");
			PropUtil prop = new PropUtil();
			String hierlink4Mail= prop.getProperty("HIERARCHY_BASE_URL");
			Hashtable mailDtailsHT=getMailDetailsFromConfig(levelDoc,WfNode,currentStageNo,curUserId, "Resubmited", "Next", false , "");
			String mailSendBy=(String)mailDtailsHT.get("Mail_ID_Send_by");
			String mailPassword=(String)mailDtailsHT.get("Mail_Password");
			String mailMessage=(String)mailDtailsHT.get("Mail_Message");
			String bobyofMail=mailMessage;
			String subject = mailDtailsHT.get("Subject") == null || String.valueOf(mailDtailsHT.get("Subject")).isEmpty() ? 
					"Workflow Name / Document Name : "+hierarchyName+" / "+ documentName : String.valueOf(mailDtailsHT.get("Subject"));
			
			// postMail(userMailAddress, subject, bobyofMail, mailSendBy,mailPassword, null, hierlink4Mail);
		
		}catch(Exception e)
		{
		
			e.printStackTrace();
			
		}
		
		System.out.println("Exiting: " + new Exception().getStackTrace()[0].getClassName() + "."
				+ new Exception().getStackTrace()[0].getMethodName());
		
		return null;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public Hashtable doResubmitConfiguration(Document levelDoc, Node WfNode,String heirCode,String currentStageNo,String curUserId,
			Hashtable stageDetailsHT,Hashtable messagesHT,Hashtable empNMHT,Hashtable propHT,String currentuserAccUID,String hierarchyName)
	{
		System.out.println("Entering: " + new Exception().getStackTrace()[0].getClassName() + "."
				+ new Exception().getStackTrace()[0].getMethodName());
		try{
				
			String stagefinalStatus=(String)messagesHT.get("Final");
			String Rejected_Return_To =(String)stageDetailsHT.get("Rejected_Return_To");
			String Rejected_Set_Status_To =(String)stageDetailsHT.get("Rejected_Set_Status_To");
			String Rejected_Notification_To =(String)stageDetailsHT.get("Rejected_Notification_To");
			String concurrency =(String)propHT.get("Concurrency");			
			System.out.println("Rejected_Set_Status_To "+Rejected_Set_Status_To);
			System.out.println("Rejected_Return_To "+Rejected_Return_To);
			System.out.println("Rejected_Notification_To "+Rejected_Notification_To);			
			String returnToUser="";
			if(Rejected_Return_To==null){
				System.out.println("********ERROR****Rejection Configuration not Configured yet Now (Not in Xml)");
				Rejected_Return_To = "";
			}
			String toStageNo = "";
			//Rejected Return To
			if(Rejected_Return_To.equalsIgnoreCase("Previous_Member")){  // member			
				returnToUser=settingStatus4PrevoiusAffectedMember(levelDoc,WfNode, empNMHT, currentStageNo, curUserId, Rejected_Set_Status_To,
						propHT);
				toStageNo = currentStageNo;
			}else if(Rejected_Return_To.equalsIgnoreCase("Previous_Stage"))  // previews Stage First Member
			{
				int stageNo=Integer.parseInt(currentStageNo);
				int preStage=stageNo-1;
				if(preStage==0){
					preStage=1;
				}
				toStageNo = String.valueOf(preStage);
				Hashtable prevStageDetailsHT=retriveStageDetailsFromXML(WfNode, String.valueOf(preStage),"");
				String externalUserFlag = prevStageDetailsHT.get("ExternalUser") == null ? "" : prevStageDetailsHT.get("ExternalUser").toString();
				String autoLoginFlag = prevStageDetailsHT.get("Auto_Login") == null ? "" : prevStageDetailsHT.get("Auto_Login").toString();
				if(externalUserFlag.equalsIgnoreCase("false") && preStage > 1) {
					preStage=preStage-1;
				}
				returnToUser=settingStatus4StageFirstMember(levelDoc,WfNode,String.valueOf(preStage),currentStageNo, Rejected_Set_Status_To,currentuserAccUID,curUserId);
			}else if(Rejected_Return_To.equalsIgnoreCase("First_Stage"))  // First Stage First Member
			{
				returnToUser=settingStatus4StageFirstMember(levelDoc,WfNode,"1",currentStageNo, Rejected_Set_Status_To,currentuserAccUID,curUserId);
				toStageNo = "1";
			}

			String documentName = Globals.getAttrVal4AttrName(WfNode.getParentNode(), "DocumentName");
			String custKey = Globals.getAttrVal4AttrName(WfNode.getParentNode(), "CustomerKey");
			
			//Mail Details
//			String subject = "Workflow Name / Document Name : "+hierarchyName+" / "+ documentName;

			PropUtil prop = new PropUtil();
			String hierlink4Mail= prop.getProperty("HIERARCHY_BASE_URL");
			Hashtable mailDtailsHT=getMailDetailsFromConfig(levelDoc,WfNode,currentStageNo,curUserId, "Resubmited", "Next", false , "");
			String mailSendBy=(String)mailDtailsHT.get("Mail_ID_Send_by");
			String mailPassword=(String)mailDtailsHT.get("Mail_Password");
			String mailMessage=(String)mailDtailsHT.get("Mail_Message");
			String bobyofMail=mailMessage;
			String subject = mailDtailsHT.get("Subject") == null || String.valueOf(mailDtailsHT.get("Subject")).isEmpty() ? 
					"Workflow Name / Document Name : "+hierarchyName+" / "+ documentName : String.valueOf(mailDtailsHT.get("Subject"));
			Hashtable currentstageDetailsHT=retriveStageDetailsFromXML(WfNode, String.valueOf(toStageNo),"");
			Hashtable currentemplyeeDetailsHT=(Hashtable)currentstageDetailsHT.get("EmployeedetHT");
			String members = ""; 
			for(int t=0;t<currentemplyeeDetailsHT.size();t++) {
				Hashtable empHt = (Hashtable) currentemplyeeDetailsHT.get(t);
				members = members.isEmpty() ? (String)empHt.get("empName") : members+", "+(String)empHt.get("empName");
			}
			String toStage = (String)currentstageDetailsHT.get("Stage_Name");
			String dueDate = (String)currentstageDetailsHT.get("EffectiveEndDate");
			SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
			int diffInDays = 0;
			if(dueDate != null && !dueDate.trim().isEmpty()) {
				Date due = sdf.parse(dueDate);
				long diff = new Date().getTime() - due.getTime();
				diffInDays = (int) (diff / (1000 * 60 * 60 * 24));
			}else
				dueDate = "";
			bobyofMail = bobyofMail.replace("$ToStage$", toStage).replace("$DueDate$", dueDate).replace("$Days$", String.valueOf(diffInDays)).replaceAll("$Members$", members);
			subject = subject.replace("$ToStage$", toStage).replace("$DueDate$", dueDate).replace("$Days$", String.valueOf(diffInDays)).replaceAll("$Members$", members);


			System.out.println("###########bobyofMail#########################:"+bobyofMail);
			//Send Rejected Notification To
			if(Rejected_Notification_To.equalsIgnoreCase("Affected_Members")){ // only one person

				int currentStageno=Integer.parseInt(currentStageNo);

				for(int i=1;i<=currentStageno;i++)
				{
					Hashtable stageDetHT=retriveStageDetailsFromXML(WfNode, String.valueOf(i),"");
					Hashtable memdetHT=(Hashtable)stageDetHT.get("EmployeedetHT");
					Hashtable stageaffectedmemHT=gettingLastAccOrAffectedMembers(memdetHT, "affectedMembersDetails", curUserId);

					System.out.println("stageaffectedmemHT "+stageaffectedmemHT+" "+stageaffectedmemHT.size());
					if(stageaffectedmemHT!=null&&stageaffectedmemHT.size()!=0)
					{
						sendmailFromAdmin(stageaffectedmemHT, subject, bobyofMail, hierlink4Mail, mailSendBy, mailPassword, custKey);
					}
				}

			}else if(Rejected_Notification_To.equalsIgnoreCase("All_Members_in_Stage")){

				//Getting index position of Current User
				int userPos=0;
				String curUserAccUID="";
				for(int i=0;i<empNMHT.size();i++)
				{
					Hashtable indEmpHT =(Hashtable)	empNMHT.get(i);
					String accUNamexml=(String)indEmpHT.get("empName");
					if(curUserId.equalsIgnoreCase(accUNamexml))
					{	
						curUserAccUID=(String)indEmpHT.get("Access_Unique_ID");
						userPos=i;
						break;
					}
				}

				//deside which Stage to Proceed
				int sendMailStageNo=0;
				if(concurrency.equalsIgnoreCase("serial"))
				{
					if(userPos==0)
					{
//						if(!currentStageNo.equals("1"))
//						{
//							sendMailStageNo=Integer.parseInt(currentStageNo)-1;
//						}else
//						{
							sendMailStageNo=Integer.parseInt(currentStageNo);	
//						}
					}
					else
					{
						sendMailStageNo=Integer.parseInt(currentStageNo);
					}
				}else
				{
					sendMailStageNo=Integer.parseInt(currentStageNo);
				}



				Hashtable sendMailStageHT =retriveStageDetailsFromXML(WfNode,String.valueOf(sendMailStageNo),"");
				Hashtable sendmailempNMHT =(Hashtable)sendMailStageHT.get("EmployeedetHT");

				if(sendmailempNMHT!=null&&sendmailempNMHT.size()!=0)
				{
					sendmailFromAdmin(sendmailempNMHT, subject, bobyofMail, hierlink4Mail, mailSendBy, mailPassword, custKey);
				}



			}else if(Rejected_Notification_To.equalsIgnoreCase("All_Members_in_WF")){




			}

		}catch(Exception e)
		{
			e.printStackTrace();
		}

		System.out.println("Exiting: " + new Exception().getStackTrace()[0].getClassName() + "."
				+ new Exception().getStackTrace()[0].getMethodName());
		return null;
	}


	public String settingStatus4PrevoiusAffectedMember(Document levelDoc,String heirCode,Hashtable memdetHT,String curStageNo,String curUserID,String statusCode,
			Hashtable propHT)
	{
		System.out.println("Entering: " + new Exception().getStackTrace()[0].getClassName() + "."
				+ new Exception().getStackTrace()[0].getMethodName());
		String returnToUser="";
		try{
			//Getting index position of Current User

			//				PropUtil prop=new PropUtil();
			//				String heirLevelXML=prop.getProperty("HIERARCHY_XML_FILE");
			//				String heirLeveldir=prop.getProperty("HIERARCHY_XML_DIR");
			//				Document xmlDoc=Globals.openXMLFile(heirLeveldir, heirLevelXML);
			//				LoginProcessManager lgn=new LoginProcessManager();
			//				Hashtable stageDetailsHT =lgn.getStageDetails(heirCode,curStageNo);
			//				Hashtable messagesHT =(Hashtable)stageDetailsHT.get("MessagedetHT");
			//				Hashtable empNMHT =(Hashtable)stageDetailsHT.get("EmployeedetHT");
			//				Hashtable propHT =(Hashtable)stageDetailsHT.get("PropertiesHT");


			Node workFlowN=Globals.getNodeByAttrVal(levelDoc, "Workflow", "Hierarchy_ID", heirCode);			
			returnToUser=settingStatus4PrevoiusAffectedMember(levelDoc, workFlowN, memdetHT, curStageNo, curUserID, statusCode, propHT);;

			//				Globals.writeXMLFile(xmlDoc, heirLeveldir, heirLevelXML);

			System.out.println("Exiting: " + new Exception().getStackTrace()[0].getClassName() + "."
					+ new Exception().getStackTrace()[0].getMethodName());
		}catch(Exception e)
		{
			e.printStackTrace();
		}

		return returnToUser;
	}

	public String settingStatus4PrevoiusAffectedMember(Document levelDoc,Node workFlowN,Hashtable memdetHT,String curStageNo,String curUserID,String statusCode,
			Hashtable propHT) throws Exception {
		String returnToUser="";
		Node stageN = Globals.getNodeByAttrValUnderParent(levelDoc, workFlowN, "Stage_No", String.valueOf(curStageNo));

		int userPos=0;
		String curUserAccUID="";
		for(int i=0;i<memdetHT.size();i++)
		{
			Hashtable indEmpHT =(Hashtable)	memdetHT.get(i);
			String accUNamexml=(String)indEmpHT.get("empName");
			if(curUserID.equalsIgnoreCase(accUNamexml))
			{	
				curUserAccUID=(String)indEmpHT.get("Access_Unique_ID");
				userPos=i;
				break;
			}
		}
		//Setting Status Based on the Index Position of the User			
		if(userPos==0)
		{
			//Setting the Status to the Previous Stage User
			int stageNO=Integer.parseInt(curStageNo);
			int preStageNo=stageNO-1;
			if(preStageNo==0){
				preStageNo=1;
			}
			Hashtable prestageDetailsHT = retriveStageDetailsFromXML(workFlowN,String.valueOf(preStageNo),"");
			String externalUserFlag = prestageDetailsHT.get("ExternalUser") == null ? "" : prestageDetailsHT.get("ExternalUser").toString();
			String autoLoginFlag = prestageDetailsHT.get("Auto_Login") == null ? "" : prestageDetailsHT.get("Auto_Login").toString();
			if(externalUserFlag.equalsIgnoreCase("true") && preStageNo>1) {
				preStageNo=preStageNo-1;
			}
			prestageDetailsHT = retriveStageDetailsFromXML(workFlowN,String.valueOf(preStageNo),"");
			Node prestageN = Globals.getNodeByAttrValUnderParent(levelDoc, workFlowN, "Stage_No", String.valueOf(preStageNo));
			
			Hashtable preStagememdetHT=(Hashtable)prestageDetailsHT.get("EmployeedetHT");
			Hashtable preStageaffMemHT=gettingLastAccOrAffectedMembers(preStagememdetHT, "lastAccess", curUserID);
			String affectedMem=(String)preStageaffMemHT.get(0);
			//getting Access Unique Id 4 affected member
			String affectedUserAccUID="";
			for(int i=0;i<preStagememdetHT.size();i++)
			{
				Hashtable indEmpHT =(Hashtable)	preStagememdetHT.get(i);
				String accUNamexml=(String)indEmpHT.get("empName");
				if(affectedMem.equalsIgnoreCase(accUNamexml))
				{	
					affectedUserAccUID=(String)indEmpHT.get("Access_Unique_ID");
					break;
				}
			}

			NodeList stagenodesNL=prestageN.getChildNodes();
			for(int j=0;j<stagenodesNL.getLength();j++)
			{

				Node checkNode=stagenodesNL.item(j);
				if(checkNode.getNodeName().equalsIgnoreCase("Employee_Names"))
				{
					Node affMemN = Globals.getNodeByAttrValUnderParent(levelDoc, checkNode, "Access_Unique_ID", affectedUserAccUID);

					Element affMemE=(Element)affMemN;
					affMemE.setAttribute("User_Status", statusCode);
					affMemE.setAttribute("isWorkStarted", "false");
					break;
				}

			}

			// need to set stage status  // call stage reset method
			Element prestageE=(Element)prestageN;
			//					prestageE.setAttribute("Stage_Status", "YTS");

			String totStage=workFlowN.getAttributes().getNamedItem("Total_No_Stages").getNodeValue();
			int totalStage=Integer.parseInt(totStage);
			String curStageName=prestageN.getAttributes().getNamedItem("Stage_Name").getNodeValue();

			resetStageStatus(levelDoc, prestageE, (Element)workFlowN, curStageName, preStageNo, totalStage, null, "", "", "", "", "", workFlowN, false,affectedMem,statusCode,"affectedonly","WIP");



		}else
		{
			String concurrency=(String)propHT.get("Concurrency");
			//getting Current Member Node 

			if(concurrency.equalsIgnoreCase("Serial"))
			{
				NodeList stagenodesNL=stageN.getChildNodes();
				for(int j=0;j<stagenodesNL.getLength();j++)
				{

					Node checkNode=stagenodesNL.item(j);
					if(checkNode.getNodeName().equalsIgnoreCase("Employee_Names"))
					{
						Node curempN = Globals.getNodeByAttrValUnderParent(levelDoc, checkNode, "Access_Unique_ID", curUserAccUID);					

						//Getting Previous Member Node
						Node preempN =curempN.getPreviousSibling();
						if(preempN.getNodeType()==Node.TEXT_NODE)
						{
							preempN =curempN.getPreviousSibling().getPreviousSibling();	

						}
						Hashtable empHT=Globals.getAttributeNameandValHT(preempN);
						String role=(String)empHT.get("User_Role");
						if(role.equals("Admin")){
							preempN =preempN.getPreviousSibling();
							if(preempN.getNodeType()==Node.TEXT_NODE)
							{
								preempN =preempN.getPreviousSibling().getPreviousSibling();	

							}
						}




						Element preempE=(Element)preempN;
						preempE.setAttribute("User_Status", statusCode);
						preempE.setAttribute("isWorkStarted", "false");
						returnToUser=preempN.getTextContent();


					}
				}
			}else //4 parallel
			{

				System.out.println("memdetHT "+memdetHT);
				Hashtable affectedMemHT=gettingLastAccOrAffectedMembers(memdetHT, "lastAccess", curUserID);
				String affectedMem=(String)affectedMemHT.get(0);


				//getting Access Unique Id 4 affected member
				String affectedUserAccUID="";
				for(int i=0;i<memdetHT.size();i++)
				{
					Hashtable indEmpHT =(Hashtable)	memdetHT.get(i);
					String accUNamexml=(String)indEmpHT.get("empName");
					if(affectedMem.equalsIgnoreCase(accUNamexml))
					{	
						affectedUserAccUID=(String)indEmpHT.get("Access_Unique_ID");
						returnToUser=accUNamexml;
						break;
					}
				}

				NodeList stagenodesNL=stageN.getChildNodes();
				for(int j=0;j<stagenodesNL.getLength();j++)
				{

					Node checkNode=stagenodesNL.item(j);
					if(checkNode.getNodeName().equalsIgnoreCase("Employee_Names"))
					{
						Node affMemN = Globals.getNodeByAttrValUnderParent(levelDoc, checkNode, "Access_Unique_ID", affectedUserAccUID);

						Element affMemE=(Element)affMemN;
						affMemE.setAttribute("User_Status", statusCode);
						affMemE.setAttribute("isWorkStarted", "false");
						//								//updating Stage Status Also
						//								Element stageE=(Element)stageN;
						//								stageE.setAttribute("Stage_Status", "YTS");
						break;
					}

				}

			}

		}
		return returnToUser;
	}

	public  String settingStatus4StageFirstMember(Document levelDoc,String heirCode,String stageno,String currentStageNo,String statusCode,String affectedUserAccUID,String affectedUname)
	{
		System.out.println("Entering: " + new Exception().getStackTrace()[0].getClassName() + "."
				+ new Exception().getStackTrace()[0].getMethodName());
		String returnToUsr="";
		try
		{
			//			PropUtil prop=new PropUtil();
			//			String heirLevelXML=prop.getProperty("HIERARCHY_XML_FILE");
			//			String heirLeveldir=prop.getProperty("HIERARCHY_XML_DIR");		
			//			Document xmlDoc=Globals.openXMLFile(heirLeveldir, heirLevelXML);
			Node workFlowN=Globals.getNodeByAttrVal(levelDoc, "Workflow", "Hierarchy_ID", heirCode);		

			returnToUsr=settingStatus4StageFirstMember(levelDoc, workFlowN, stageno, currentStageNo, statusCode, affectedUserAccUID, affectedUname);

			//			Globals.writeXMLFile(xmlDoc, heirLeveldir, heirLevelXML);

			System.out.println("Exiting: " + new Exception().getStackTrace()[0].getClassName() + "."
					+ new Exception().getStackTrace()[0].getMethodName());
		}catch(Exception e)
		{
			e.printStackTrace();
		}

		return returnToUsr;
	}

	public String settingStatus4StageFirstMember(Document levelDoc,Node workFlowN,String stageno,String currentStageNo,String statusCode,String affectedUserAccUID,String affectedUname) {
		String returnToUsr="";
		Node stageN = Globals.getNodeByAttrValUnderParent(levelDoc, workFlowN, "Stage_No", String.valueOf(stageno));

		//			LoginProcessManager lgn=new LoginProcessManager();
		// call method stage change method current user stageN current stage
		String totStage=workFlowN.getAttributes().getNamedItem("Total_No_Stages").getNodeValue();
		int totalStage=Integer.parseInt(totStage);
		String curStageName=stageN.getAttributes().getNamedItem("Stage_Name").getNodeValue();

		//Setting 4 Previous Stage
		resetStageStatus(levelDoc, (Element)stageN,(Element) workFlowN, curStageName,Integer.parseInt(stageno), totalStage, null, "", "", "", "", "", workFlowN, false,affectedUname,statusCode,"all","WIP");


		NodeList stagenodesNL=stageN.getChildNodes();
		for(int j=0;j<stagenodesNL.getLength();j++)
		{

			Node checkNode=stagenodesNL.item(j);
			if(checkNode.getNodeName().equalsIgnoreCase("Employee_Names"))
			{
				NodeList empNL=checkNode.getChildNodes();
				for(int k=0;k<empNL.getLength();k++)
				{
					Node firstNode=empNL.item(k);
					if(firstNode.getNodeType()==Node.ELEMENT_NODE)
					{
						Hashtable empHT=Globals.getAttributeNameandValHT(firstNode);
						String role=(String)empHT.get("User_Role");
						String activeFlag = empHT.get("Active") == null || String.valueOf(empHT.get("Active")).isEmpty() ? "true" : String.valueOf(empHT.get("Active"));
						if(role.equals("Admin") || activeFlag.equalsIgnoreCase("false")){
							continue;
						}else{
							Element checkNodeE=(Element)firstNode;
							checkNodeE.setAttribute("User_Status", statusCode);
							checkNodeE.setAttribute("isWorkStarted", "false");
							returnToUsr=firstNode.getTextContent();
							break;
						}
					}
				}
			}
		}



		Node currentstageN = Globals.getNodeByAttrValUnderParent(levelDoc, workFlowN, "Stage_No", currentStageNo);
		NodeList curstagenodesNL=currentstageN.getChildNodes();
		for(int j=0;j<curstagenodesNL.getLength();j++){
			Node checkNode=curstagenodesNL.item(j);
			if(checkNode.getNodeName().equalsIgnoreCase("Employee_Names"))
			{
				Node affMemN = Globals.getNodeByAttrValUnderParent(levelDoc, checkNode, "Access_Unique_ID", affectedUserAccUID);

				Element affMemE=(Element)affMemN;					
				affMemE.setAttribute("Return_To", returnToUsr);
				break;
			}

		}	


		//Setting 4 Current Stage
		resetStageStatus(levelDoc, (Element)currentstageN,(Element) workFlowN, curStageName,Integer.parseInt(stageno), totalStage, null, "", "", "", "", "", workFlowN, false,affectedUname,statusCode,"all","WIP");

		return returnToUsr;
	}


	public static Hashtable gettingLastAccOrAffectedMembers(Hashtable memdetHT,String accessFlag,String curUserID)
	{
		System.out.println("Entering: " + new Exception().getStackTrace()[0].getClassName() + "."
				+ new Exception().getStackTrace()[0].getMethodName());
		Hashtable resHT=new Hashtable<>(); 
		String affectedUserAccesUniqueId="";
		try{

			Hashtable lastAcctimeHT=new Hashtable<>();
			Hashtable affUserHT=new Hashtable<>();
			Hashtable affUserdetHT=new Hashtable<>();
			int key=0;

			for(int i=0;i<memdetHT.size();i++)
			{
				Hashtable indEmpHT =(Hashtable)	memdetHT.get(i);
				String accUNamexml=(String)indEmpHT.get("empName");
				if(curUserID.equalsIgnoreCase(accUNamexml))
				{	
				}else
				{
					String userLastAcc=(String)indEmpHT.get("Last_Access");
					if(userLastAcc!=null&&!userLastAcc.equals(""))
					{
						lastAcctimeHT.put(accUNamexml,userLastAcc);
						affUserHT.put(key,accUNamexml);
						affUserdetHT.put(key, indEmpHT);
						key++;
					}
				}
			}

			if(accessFlag.equalsIgnoreCase("lastAccess"))
			{
				Date latestTimeDT=gettingLatestDateTime(lastAcctimeHT);


				for(int i=0;i<memdetHT.size();i++)
				{
					Hashtable indEmpHT =(Hashtable)	memdetHT.get(i);
					String lastAccDt=(String)indEmpHT.get("Last_Access");
					if(lastAccDt!=null&&!lastAccDt.equals(""))
					{
						Date userLastAcc=new Date(lastAccDt);

						System.out.println(userLastAcc+" = "+latestTimeDT);
						if(userLastAcc.equals(latestTimeDT))
						{
							affectedUserAccesUniqueId=(String)indEmpHT.get("empName");
						}
					}

				}
				resHT.put(0, affectedUserAccesUniqueId);

				System.out.println("Latest Access Time : "+latestTimeDT);
				System.out.println("Previous Affected User : "+affectedUserAccesUniqueId);

			}else if(accessFlag.equalsIgnoreCase("affectedMembers"))
			{
				resHT=affUserHT;
			}else if(accessFlag.equalsIgnoreCase("affectedMembersDetails"))
			{
				resHT=affUserdetHT;
			}







		}catch(Exception e)
		{
			e.printStackTrace();
		}
		System.out.println("Exiting: " + new Exception().getStackTrace()[0].getClassName() + "."
				+ new Exception().getStackTrace()[0].getMethodName());

		return resHT;
	}

	public static Date gettingLatestDateTime(Hashtable lastAcctimeHT)
	{
		System.out.println("Entering: " + new Exception().getStackTrace()[0].getClassName() + "."
				+ new Exception().getStackTrace()[0].getMethodName());
		Date preaccDT=null;
		try
		{

			Enumeration uIdEnum=lastAcctimeHT.keys();


			while(uIdEnum.hasMoreElements())
			{
				String uid=(String)uIdEnum.nextElement();

				Date accDT=new Date((String)lastAcctimeHT.get(uid));

				if(preaccDT==null)
				{
					preaccDT=accDT;
				}else
				{
					//					System.out.println("Compare preaccDT.after(accDT): "+preaccDT +" "+accDT);
					if(preaccDT.after(accDT))
					{	
					}else
					{
						preaccDT=accDT;
					}

				}



			}
			System.out.println("Latest Date : "+preaccDT);



		}catch(Exception e)
		{
			e.printStackTrace();
		}
		System.out.println("Exiting: " + new Exception().getStackTrace()[0].getClassName() + "."
				+ new Exception().getStackTrace()[0].getMethodName());

		return preaccDT;
	}











	public static void main(String[] args) {
		// TODO Auto-generated method stub




		List unsortList = new ArrayList();

		unsortList.add(0);
		unsortList.add(-1);
		unsortList.add(1);
		unsortList.add(2);
		unsortList.add(3);
		unsortList.add(4);
		unsortList.add(5);


		//before sort
		System.out.println("ArrayList is unsort");
		for(int i=0;i<unsortList.size();i++){
			System.out.println(unsortList.get(i));
		}

		//sort the list
		Collections.sort(unsortList);

		//after sorted
		System.out.println("ArrayList is sorted");
		for(int i=0;i<unsortList.size();i++){
			System.out.println(unsortList.get(i));
		}
	}



}
