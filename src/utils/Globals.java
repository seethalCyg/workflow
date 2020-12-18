package utils;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import managers.LoginProcessManager;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.quartz.JobDataMap;
import org.w3c.dom.Attr;
import org.w3c.dom.CharacterData;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import javax.faces.context.FacesContext;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;


import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import beans.ExceptionBean;
import beans.HierarchyBean;
import beans.SecurityBean;
import beans.genarateFactsBean;

public class Globals {

	private final static ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();

	private final static Lock read = readWriteLock.readLock();

	private final static Lock write = readWriteLock.writeLock();

	private static final boolean SOP_ENABLED = true;
	public static boolean checkStringIsNumber(String string) {
		boolean numeric = true;

		try {
			Integer num = Integer.parseInt(string);
		} catch (NumberFormatException e) {
			numeric = false;
		}
		return numeric;
	}
	
	
	public synchronized static Node getChildNodeByAttrVal(Document doc, String tag_nm, String attrName, String attrValue, String childNodeName) {

		System.out.println("Entering: " + new Exception().getStackTrace()[0].getClassName() + "."
				+ new Exception().getStackTrace()[0].getMethodName());
		Node node = null;
		try {
			NodeList nodelist = doc.getElementsByTagName(tag_nm);
			String nodenm = "";
			String nodeval = "";

			for (int i = 0; i < nodelist.getLength(); i++) {
				node = nodelist.item(i);
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					nodenm = node.getNodeName();
					if (nodenm.equals(tag_nm)) {
						nodeval = Globals.getAttrVal4AttrName(node, attrName);
						if (nodeval.equals(attrValue)) {
							NodeList nodelist1 = node.getChildNodes();
							for(int j=0;j<nodelist1.getLength();j++) {
								Node node2=node = nodelist1.item(j);
								if (node2.getNodeType() == Node.ELEMENT_NODE) {
									String nodeNam=node2.getNodeName();
									if(nodeNam.equals(childNodeName)) {
										return node2;
									}
								}
							}
							
						}

					}// if(nodenm.equals(tag_nm)){

				}// if(node.getNodeType()==Node.ELEMENT_NODE){

			}// for(int i=0;i<nodelist.getLength();i++){

			//			System.out.println("*** Warning: Did not find a Node under Parent: " + tag_nm + " that has " + attrName + " and " + attrValue);

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		if (node != null) {

			//			System.out.println("*** Warning: Did not find a Node under Parent: " + node.getNodeName() + " that has " + attrName + " and "
			//													+ attrValue);
		}
		return null;
	}
	
	public static String getException( Exception ex) {

		String status;

		try {


			ex.printStackTrace();

			status = Globals.stackTraceToExceptionStr(ex);

			if (status == null)
				status = "*** Unknown Error: Unable to capture the Error. Check the log file for details.";


			

		} catch (Exception e) {

			e.printStackTrace();
			return "*** Exception in getting Exception.";
		}

		return status;
	}
	public static NodeList getNodeList(String base_dir, String XMLfile, String tag_nm) {	

		FileInputStream fis = null;
		NodeList nodeList = null;

		read.lock();

		try {
			fis = new FileInputStream(base_dir + XMLfile);
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(fis);
			doc.getDocumentElement().normalize();

			nodeList = doc.getElementsByTagName(tag_nm);

			fis.close();

		} catch (Exception e) {
			System.out.println("Exception in getNodeList...");
			getErrorString(e);
			return null;
		} finally {
			read.unlock(); // Code Change Bharath 16DEC13
			try {
				if (fis != null) {
					fis.close();
					fis = null; // code change Vishnu 22OCT2013 For deleting RRS
					// temp file
				}
			} catch (IOException ioe) {
				getErrorString(ioe);
			}
		}

		// System.out.println("Exiting: " + new
		// Exception().getStackTrace()[0].getClassName() + "."
		// + new Exception().getStackTrace()[0].getMethodName());

		return nodeList;
	}

	public synchronized static String convert2minsandSec(double totalRunTime) {   //  code change Menaka 14MAR2014

		String totruntime = "";
		totalRunTime=totalRunTime/1000;
		if (totalRunTime < 60) { // start code change Vishnu 04oct2013
			int firstIndex = String.valueOf(totalRunTime).indexOf(".");
			totruntime = String.valueOf(totalRunTime).substring(0, firstIndex);
			totruntime = totruntime + "s";
		} else {
			if (totalRunTime == 60) {
				totruntime = "1m";
			}
			double totalMin = totalRunTime / 60;
			int totalSec = (int) totalRunTime % 60;
			int firstIndex = String.valueOf(totalMin).indexOf(".");
			totruntime = String.valueOf(totalMin).substring(0, firstIndex);
			totruntime = totruntime + "m" + " " + String.valueOf(totalSec) + "s";

		} // end code change Vishnu 04oct2013

		return totruntime;
	}



	public static void removeNodeFromXML(String nodeName, String attrName, String attrValue, String XMLFileName) { // code change Menaka 07FEB2014

		System.out.println("Entering: " + new Exception().getStackTrace()[0].getClassName() + "."
				+ new Exception().getStackTrace()[0].getMethodName());

		try {

			PropUtil propUtil = new PropUtil();
			String	 HIERARCHY_XML_DIR = propUtil.getProperty("HIERARCHY_XML_DIR");

			Document doc = Globals.openXMLFile(HIERARCHY_XML_DIR, XMLFileName);

			Node node = Globals.getNodeByAttrVal(doc, nodeName, attrName, attrValue);
			if (node != null) {
				node.getParentNode().removeChild(node);
			}

			Globals.writeXMLFile(doc, HIERARCHY_XML_DIR, XMLFileName);

		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Exiting: " + new Exception().getStackTrace()[0].getClassName() + "."
				+ new Exception().getStackTrace()[0].getMethodName());

	}

	//	public static Hashtable ReadingConnectionString(String nodeName) {
	//	Hashtable connectionStringHT=new Hashtable<>();
	//	try{
	//	PropUtil prop=new PropUtil();
	//	String dir=prop.getProperty("HIERARCHY_XML_DIR");
	//	Document doc1=Globals.openXMLFile(dir, "Hierarchy_Config.xml");
	//	NodeList ndlist=doc1.getElementsByTagName(nodeName);
	//	
	//	for(int i=0;i<ndlist.getLength();i++){
	//		Node nd=ndlist.item(i);
	//		NodeList ndliList1=nd.getChildNodes();
	//		for(int j=0;j<ndliList1.getLength();j++){
	//
	//			if(ndliList1.item(j).getNodeType()==Node.ELEMENT_NODE && ndliList1.item(j).getNodeName().equals("HostName")){
	//				connectionStringHT.put("HostName", ndliList1.item(j).getTextContent());
	//			}else if(ndliList1.item(j).getNodeType()==Node.ELEMENT_NODE && ndliList1.item(j).getNodeName().equals("PortName")){
	//				connectionStringHT.put("PortName",ndliList1.item(j).getTextContent());
	//			}else if(ndliList1.item(j).getNodeType()==Node.ELEMENT_NODE && ndliList1.item(j).getNodeName().equals("DBName")){
	//				connectionStringHT.put("DBName",ndliList1.item(j).getTextContent());
	//			}else if(ndliList1.item(j).getNodeType()==Node.ELEMENT_NODE && ndliList1.item(j).getNodeName().equals("UserName")){
	//				connectionStringHT.put("UserName",ndliList1.item(j).getTextContent());
	//			}else if(ndliList1.item(j).getNodeType()==Node.ELEMENT_NODE && ndliList1.item(j).getNodeName().equals("Password")){
	//				connectionStringHT.put("Password",ndliList1.item(j).getTextContent());
	//			}
	//		}
	//	}
	//	
	//	}catch (Exception e) {
	//		// TODO: handle exception
	//		e.printStackTrace();
	//	}
	//	return connectionStringHT;
	//}

	public static Connection getDBConnection(String ConnectionType) {

		System.out.println("Entering: " + new Exception().getStackTrace()[0].getClassName() + "."
				+ new Exception().getStackTrace()[0].getMethodName());

		// Get from RBID Config: RBID
		/*
		 * String strHost = "localhost"; String strPortNO = "1521"; String sDbName = "orcl"; String suserName = "rbid"; String sPwd = "rbid";
		 */
		Connection conn = null;

		String strHost = "";
		String strPortNO = "";
		String sDbName = "";
		String suserName = "";
		String sPwd = "";

		try {



			Hashtable conectionDatailsHT = getConnectionString(ConnectionType);
			strHost = conectionDatailsHT.get(0).toString();
			strPortNO = conectionDatailsHT.get(1).toString();
			sDbName = conectionDatailsHT.get(2).toString();
			suserName = conectionDatailsHT.get(3).toString();
			sPwd = conectionDatailsHT.get(4).toString();

			// load the Oracle drive with uname, pwd and port number
			String url = "jdbc:oracle:thin:@" + strHost + ":" + strPortNO + ":" + sDbName;

			System.out.println("Preparing to connect to Database. URL:  " + url);
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection(url, suserName, sPwd);

			System.out.println("Connection established successfully.");

		} catch (Exception er) {

			er.printStackTrace();



		}



		System.out.println("Exiting: " + new Exception().getStackTrace()[0].getClassName() + "."
				+ new Exception().getStackTrace()[0].getMethodName());
		return conn;
	}

	public static Hashtable getConnectionString(String strConnectionName) {

		System.out.println("Entering: " + new Exception().getStackTrace()[0].getClassName() + "."
				+ new Exception().getStackTrace()[0].getMethodName());
		Hashtable setValuesHT = new Hashtable<>();

		try {

			String ConnName = strConnectionName;
			String id = "/Config/Connection/"+strConnectionName+"[ConnectionName='" + strConnectionName + "']";
			String filepath;
			String ConfigFleName = "Hierarchy_Config.xml";

			PropUtil prop = new PropUtil();
			filepath = prop.getProperty("HIERARCHY_XML_DIR");

			System.out.println("RBID Config File Location & Name (filepath + ConfigFleName): " + id + ConfigFleName);

			FileInputStream fis = new FileInputStream(filepath + ConfigFleName);
			DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
			Document doc = docBuilder.parse((fis));

			XPathFactory xPathfactory = XPathFactory.newInstance();
			XPath xpath = xPathfactory.newXPath();
			XPathExpression expr = xpath.compile(id);

			Object exprResult = expr.evaluate(doc, XPathConstants.NODESET);
			NodeList nodeList = (NodeList) exprResult;

			int len = nodeList.getLength();
			for (int i = 0; i < len; i++) {

				Node firstReport = nodeList.item(i);

				if (firstReport.getNodeType() == Node.ELEMENT_NODE) {
					Element firstelement = (Element) firstReport;
					NodeList tableList = firstelement.getElementsByTagName("ConnectionName");
					Element firstNameElement = (Element) tableList.item(0);

					for (int ij = 0; ij < tableList.getLength(); ij++) {
						NodeList HostNamenode = firstelement.getElementsByTagName("HostName");
						Element hostnameElement = (Element) HostNamenode.item(ij);
						NodeList rehostname = hostnameElement.getChildNodes();
						String sthostname = rehostname.item(0).getNodeValue();
						setValuesHT.put(0, sthostname);

						NodeList PortNamenode = firstelement.getElementsByTagName("PortName");
						Element portnameElement = (Element) PortNamenode.item(ij);
						NodeList rePortname = portnameElement.getChildNodes();

						String stportname = rePortname.item(0).getNodeValue();

						setValuesHT.put(1, stportname);
						// System.out.println("PortNameHT: "+setValuesHT.get(1));

						//
						NodeList DBNamenode = firstelement.getElementsByTagName("DBName");
						Element dbnameElement = (Element) DBNamenode.item(ij);
						NodeList redbname = dbnameElement.getChildNodes();

						String stdbname = redbname.item(0).getNodeValue();

						setValuesHT.put(2, stdbname);
						// System.out.println("DBNameHT: "+setValuesHT.get(2));

						//
						NodeList UserNamenode = firstelement.getElementsByTagName("UserName");
						Element usernameElement = (Element) UserNamenode.item(ij);
						NodeList reusernname = usernameElement.getChildNodes();

						String stusername = reusernname.item(0).getNodeValue();

						setValuesHT.put(3, stusername);
						// System.out.println("UserNameHT: "+setValuesHT.get(3));
						//
						NodeList Passwordnode = firstelement.getElementsByTagName("Password");
						Element passwordElement = (Element) Passwordnode.item(ij);
						NodeList repassword = passwordElement.getChildNodes();

						String stpassword = repassword.item(0).getNodeValue();

						setValuesHT.put(4, stpassword);

					} // for ij

				} // end of if
			}

		} catch (Exception e) {
			getErrorString(e);
			setValuesHT.put("Error", "Error"); // ??
			return setValuesHT;
		}
		System.out.println("setValuesHT===>"+setValuesHT);
		System.out.println("Exiting: " + new Exception().getStackTrace()[0].getClassName() + "."
				+ new Exception().getStackTrace()[0].getMethodName());

		return setValuesHT;
	}
	public synchronized static int getMaxID(String xml_dir, String xml_nm, String tag_nm, String attr_nm) {

		// System.out.println("Entering: " + new
		// Exception().getStackTrace()[0].getClassName() + "."
		// + new Exception().getStackTrace()[0].getMethodName());
		int max = 0;

		File fLock = null;
		try {



			try {
				NodeList nodelist1 = Globals.getNodeList(xml_dir, xml_nm, tag_nm);
				Node node = nodelist1.item(0);
				if (SOP_ENABLED)
					System.out.println("node: " + node);
				String val = getAttrVal4AttrName(node, attr_nm);
				max = Integer.parseInt(val) + 1;
				updateMaxID(xml_dir, xml_nm, tag_nm, attr_nm, max + "");
			} catch (Exception e) {
				e.printStackTrace();
			}

			// System.out.println("Exiting: " + new
			// Exception().getStackTrace()[0].getClassName() + "."
			// + new Exception().getStackTrace()[0].getMethodName());

		} catch (Exception ie) {
			ie.printStackTrace();
		} 
		return max;
	}
	private static String getStageName(Node workflowNode, String from_Stage) {
		// TODO Auto-generated method stub
		String stgName="";
		try {
			NodeList workflowNodelist=workflowNode.getChildNodes();
			for(int i=0;i<workflowNodelist.getLength();i++) {
				if(workflowNodelist.item(i).getNodeType()==Node.ELEMENT_NODE && workflowNodelist.item(1).getNodeName().equals("Stage") ) {
					Element stageEle = (Element) workflowNodelist.item(i);
					String stagenam=stageEle.getAttribute("Stage_Name");
					String stagenum=stageEle.getAttribute("Stage_No");
					if((!stagenam.equals("Admin")||!stagenam.equals("public"))&&stagenum.equals(from_Stage)) {
						stgName=stagenam;
						break;
					}
					
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return stgName;
	}
	
	
	// code change vishnu
	public static Hashtable getWFNodeDetails(String hierID, String docID,Boolean isneedUserDetails,String AccessUniqID) {
		PropUtil prop=new PropUtil();
		//			String hierLevelXmlFileName="";
		String hierDir="";
		Hashtable attrHT=new Hashtable<>();
		try{

			boolean useravaiableonstage=false;
			System.out.println("hierarchy ID "+hierID+" isneedUserDetails "+isneedUserDetails +" AccessUniqID "+AccessUniqID); 

			hierDir=prop.getProperty("HIERARCHY_XML_DIR");
			String docXmlFileName = prop.getProperty("DOCUMENT_XML_FILE");
			Document doc=Globals.openXMLFile(hierDir, docXmlFileName);
			Node docNd=Globals.getNodeByAttrVal(doc, "Document", "Document_ID", docID);
			if(docNd==null){  //code change  Pandian 18MAR2014
				return attrHT;
			}
			Element docEle = (Element) docNd;
			String pauseFlag = docEle.getAttribute("Paused");
			String pausedBy = docEle.getAttribute("PausedBy");
			String pauseStageNo = docEle.getAttribute("PauseStageNo");
			
			NodeList hierNdList=docNd.getChildNodes();			
			Hashtable attchDocHT=new Hashtable<>();
			for(int i=0;i<hierNdList.getLength();i++){
				if(hierNdList.item(i).getNodeType()==Node.ELEMENT_NODE && hierNdList.item(i).getNodeName().equals("Attachments")){
					Node docAttchement=hierNdList.item(i);
					NodeList attchNdList=docAttchement.getChildNodes();	
					
					
					for(int k=0;k<attchNdList.getLength();k++) {
						if(attchNdList.item(k).getNodeType() == Node.ELEMENT_NODE && attchNdList.item(k).getNodeName().equals("Attachment")) {
							Node attchDoc=attchNdList.item(k);
							Hashtable attachHT = Globals.getAttributeNameandValHT(attchDoc);
							
							/*String attachFileID=(String)attachHT.get("AttachMentFile_ID");
							String attachConnectnTyp=(String)attachHT.get("Connection_Type");
							String attachFileName=(String)attachHT.get("FileName");
							String attachFilePath=(String)attachHT.get("FilePath");
							String attachFileSize=(String)attachHT.get("FileSize");
							String attachFileType=(String)attachHT.get("FileType");
							String attachFilAddDate=(String)attachHT.get("File_AddDate");
							String attachSourceTyp=(String)attachHT.get("Source_types");
							String attachSourcLink=(String)attachHT.get("SrcLink");
						*/	
							attchDocHT.put("R"+attchDocHT.size(), attachHT);
							
					}
					}					
					attchDocHT.put("NoofAttaachements",String.valueOf(attchDocHT.size()));
					
				}
				if(hierNdList.item(i).getNodeType()==Node.ELEMENT_NODE && hierNdList.item(i).getNodeName().equals("Workflow")){
					Node workflowNode=hierNdList.item(i);
					Node stageNode=null;					
					attrHT = Globals.getAttributeNameandValHT(workflowNode);
					String currentStageNo=(String)attrHT.get("Current_Stage_No");
					String currentStageName=(String)attrHT.get("Current_Stage_Name");
					String totalStageNo=(String)attrHT.get("Total_No_Stages");
					
					//kishor15--8
					if(!attrHT.containsKey("LastAccessStageName")&&!attrHT.containsKey("LastAccessMember")&&!attrHT.containsKey("LastAccessStage")) {
						for(int j=0;j<hierNdList.getLength();j++){
							if(hierNdList.item(j).getNodeType()==Node.ELEMENT_NODE && hierNdList.item(j).getNodeName().equals("History")){
								Node historyNode=hierNdList.item(j);
								NodeList histNdList=historyNode.getChildNodes();	
								for(int k=0;k<histNdList.getLength();k++) {
									if(histNdList.item(k).getNodeType() == Node.ELEMENT_NODE && histNdList.item(k).getNodeName().equals("Action") ) {
										Hashtable ht = Globals.getAttributeNameandValHT(histNdList.item(k));
										String from_Stage=(String)ht.get("FromStage");
										String from=(String)ht.get("From");
										if(from_Stage!=null && from!=null) {
											from_Stage=getStageName(workflowNode,from_Stage);
											attrHT.put("Assigned_From_Stage", from_Stage);
											attrHT.put("Assigned_From_Stage_Users", from);
										}
									}
								}
							}
							}
					}else {
						String from_Stage=(String)attrHT.get("LastAccessStageName");
						String from=(String)attrHT.get("LastAccessMember");
						if(from_Stage!=null && from!=null) {
							attrHT.put("Assigned_From_Stage", from_Stage);
							attrHT.put("Assigned_From_Stage_Users", from);
						}
						
					}
					
					
					/*if(userPreviousFlag) {
						if(!username.equalsIgnoreCase(lastMemberName)) {
							attrHT.clear();
							return attrHT;
						}
						if(lastStageName!= null) {
							attrHT.put("LastStageName", lastStageName);
							attrHT.put("LastMemberName", lastMemberName);
						}else {
							attrHT.clear();
							return attrHT;
						}
					}*/
		////////////vishnu/////////
					LoginProcessManager logn1=new LoginProcessManager();
					if(!currentStageNo.equalsIgnoreCase("Completed") && !currentStageNo.equalsIgnoreCase("Rules Failed") && !currentStageNo.equalsIgnoreCase("Cancel")) {
					
					Hashtable laststageDetailsHT=logn1.retriveStageDetailsFromXML(workflowNode, currentStageNo,"");
					Hashtable lastemplyeeDetailsHT=(Hashtable)laststageDetailsHT.get("EmployeedetHT");
					for (int approv = 0; approv < lastemplyeeDetailsHT.size(); approv++) {
						Hashtable ApproversHT=new Hashtable();
						ApproversHT=(Hashtable)lastemplyeeDetailsHT.get(approv);
						String lastAccDates=(String)ApproversHT.get("Last_Access");
						if(lastAccDates != null)
							attrHT.put("LastAccessDates", lastAccDates);
						else
							attrHT.put("LastAccessDates", "");
					}
					}
					////////////vishnu/////////
					attrHT.put("DocumentName", docEle.getAttribute("DocumentName"));
					attrHT.put("DocumentID", docEle.getAttribute("Document_ID"));
					attrHT.put("WorkflowName", docEle.getAttribute("WorkflowName"));
					attrHT.put("Created_By", docEle.getAttribute("Created_By"));
					attrHT.put("Created_Date", docEle.getAttribute("Created_Date"));
					attrHT.put("WorkflowStartDate", docEle.getAttribute("WorkflowStartDate"));
					attrHT.put("WorkflowEndDate", docEle.getAttribute("WorkflowEndDate"));
					System.out.println("currentStageNo "+currentStageNo);
					if(!currentStageNo.equals("NA") && !currentStageNo.equals("")){


						if(currentStageNo.equals("Completed") || currentStageNo.equalsIgnoreCase("Rules Failed") || currentStageNo.equalsIgnoreCase("Cancel")){
							attrHT.put("Current_Stage_Status", "NA");
							stageNode=Globals.getNodeByAttrValUnderParent(doc, workflowNode, "Stage_Name", currentStageName);
						}else{

							stageNode=Globals.getNodeByAttrValUnderParent(doc, workflowNode, "Stage_No", currentStageNo);
							Element stageNodeEL=(Element)stageNode;
							String cuerrentStageStatus=(String)stageNodeEL.getAttribute("Stage_Status");
							attrHT.put("Current_Stage_Status", cuerrentStageStatus);	
							attrHT.put("AssignedDate", stageNodeEL.getAttribute("EffectiveStarDate"));
							attrHT.put("DueDate", stageNodeEL.getAttribute("EffectiveEndDate"));
							attrHT.put("StatusCode4Documents", stageNodeEL.getAttribute("StatusCode4Documents"));
							attrHT.put("Change_PrimaryFileOnly", stageNodeEL.getAttribute("Change_PrimaryFileOnly"));
							attrHT.put("SendPrimaryFileOnly", stageNodeEL.getAttribute("SendPrimaryFileOnly"));
							attrHT.put("Allow_Upload", stageNodeEL.getAttribute("Allow_Upload"));
							// code change pandian 21APR2014 add member who is process and set his Role
							
							
							//kishor15--8
							for(int j=0;j<hierNdList.getLength();j++){
								if(hierNdList.item(j).getNodeType()==Node.ELEMENT_NODE && hierNdList.item(j).getNodeName().equals("History")){
									Node historyNode=hierNdList.item(j);
									NodeList histNdList=historyNode.getChildNodes();	
									for(int k=0;k<histNdList.getLength();k++) {
										if(histNdList.item(k).getNodeType() == Node.ELEMENT_NODE && histNdList.item(k).getNodeName().equals("Action") ) {
											Hashtable ht = Globals.getAttributeNameandValHT(histNdList.item(k));
											String sctualStartDate=(String)ht.get("Date");
											String from=(String)ht.get("From");
											if(sctualStartDate!=null) {
												attrHT.put("Actual_StartDate", sctualStartDate);
											}
										}
									}
								}
								}
							
							currentStageNo = Globals.checkStringIsNumber(currentStageNo) ? currentStageNo : totalStageNo;
							boolean flag = false;
							for(int l = Integer.valueOf(currentStageNo); l <= Integer.valueOf(totalStageNo);l++) {
								Hashtable currentstageDetailsHT=logn1.retriveStageDetailsFromXML(workflowNode, currentStageNo,"");
								Hashtable currentemplyeeDetailsHT=(Hashtable)currentstageDetailsHT.get("EmployeedetHT");
								Hashtable curentPropertiesHT=(Hashtable)currentstageDetailsHT.get("PropertiesHT");
								String Properties=(String)curentPropertiesHT.get("Properties");
								Hashtable mssgeDetailsHT=(Hashtable)currentstageDetailsHT.get("MessagedetHT");
								String finalMsg=(String)mssgeDetailsHT.get("Final");
								ArrayList<String> userCodesAl = new ArrayList<>(mssgeDetailsHT.values());
								attrHT.put("UserCodes", userCodesAl);
								String currStagStatus = currentstageDetailsHT.get("Stage_Status") == null ? "YTS" : currentstageDetailsHT.get("Stage_Status").toString() ;
								String currAllProcessMember = "";
								for (int approv = 0; approv < currentemplyeeDetailsHT.size(); approv++) {
									Hashtable ApproversHT=new Hashtable();
									ApproversHT=(Hashtable)currentemplyeeDetailsHT.get(approv);
									String userStatus=(String)ApproversHT.get("User_Status");								
									if(userStatus.equals(finalMsg)){
									}else{
										// get current user name and his Role and Status
										String currentMember=(String)ApproversHT.get("empName");	
										String currentMemberRole=(String)currentstageDetailsHT.get("Stage_Name");	
										System.out.println("Current Member Name :"+currentMember+" Current Member Role :"+currentMemberRole+" CurrentMemberType :"+Properties);
										if(pauseFlag.equalsIgnoreCase("true") && !pausedBy.equalsIgnoreCase(currentMember) && !currentStageNo.equalsIgnoreCase(pauseStageNo)) {
											return new Hashtable<>();
										}
										String activeFlag = ApproversHT.get("Active") == null || String.valueOf(ApproversHT.get("Active")).isEmpty() ? "true" : String.valueOf(ApproversHT.get("Active"));
										Hashtable<String, String> loginDetailsHT = logn1.getLoginDetails(currentMember, docEle.getAttribute("CustomerKey"));
										String disableFlag = loginDetailsHT.get("Disable") == null || String.valueOf(loginDetailsHT.get("Disable")).isEmpty() ? "false" : String.valueOf(loginDetailsHT.get("Disable"));
										//String disableFlag = loginDetailsHT.get("Disable");
										System.out.println("activeFlag %%%%%%%%%%% :"+activeFlag);
										System.out.println("disableFlag %%%%%%%%%%% :"+disableFlag);
										if(activeFlag.equalsIgnoreCase("false") || disableFlag.equalsIgnoreCase("true"))
											continue;
										flag = true;
										attrHT.put("currentprocessMember", currentMember);
										attrHT.put("currentprocessMemberRole", currentMemberRole);
										attrHT.put("wfmemberAcessType", Properties);
										currAllProcessMember = currAllProcessMember.isEmpty() ? currentMember : currAllProcessMember+", "+currentMember;
										attrHT.put("currentprocessMemberAll", currAllProcessMember);
										attrHT.put("Current_Stage_Status", currStagStatus);
										
										if(Properties.equalsIgnoreCase("Serial")) {
											approv = approv+1;
											if(approv>=currentemplyeeDetailsHT.size()) {
												int nextStageNo = Integer.valueOf(currentStageNo)+1;
												//											Node nxtNode=Globals.getNodeByAttrValUnderParent(doc, workflowNode, "Stage_No", String.valueOf(nextStageNo));
												Hashtable toStageDetailsHT=logn1.retriveStageDetailsFromXML(workflowNode, String.valueOf(nextStageNo),"");
												System.out.println("-=-=-=-=-toStageDetailsHT=-=-=-=-=-="+toStageDetailsHT);
												if(toStageDetailsHT != null && !toStageDetailsHT.isEmpty()) {
													Hashtable nextPropertiesHT=(Hashtable)toStageDetailsHT.get("PropertiesHT");
													String nextProp=(String)nextPropertiesHT.get("Properties");
													Hashtable toEmplyeeDetailsHT=(Hashtable)toStageDetailsHT.get("EmployeedetHT");
													//												Hashtable toemplyeeDetailsHT=(Hashtable)toEmplyeeDetailsHT.get("EmployeedetHT");
													String toMember="";
													String toMemberRole=(String)toStageDetailsHT.get("Stage_Name");
													String toAllMembers="";
													if(nextProp.equalsIgnoreCase("Serial")) {
														ApproversHT=(Hashtable)toEmplyeeDetailsHT.get(0);
														toMember=(String)ApproversHT.get("empName");	
														toAllMembers=toMemberRole+"\\"+toMember;
													}else {
														String toAllMembers1="";
														for(int m=0;m<toEmplyeeDetailsHT.size();m++) {
															ApproversHT=(Hashtable)toEmplyeeDetailsHT.get(m);
															toMember=(String)ApproversHT.get("empName");	
															toAllMembers1 = toAllMembers1.isEmpty() ? toMember : toAllMembers1+", "+toMember;
														}
														toAllMembers = toMemberRole+"\\"+toAllMembers1;
													}
													
													attrHT.put("ToprocessMember", toMember);
													attrHT.put("ToprocessMemberRole", toMemberRole);
													
													attrHT.put("ToprocessMemberAll", toAllMembers);
													
												}else {
													attrHT.put("ToprocessMember", "-");
													attrHT.put("ToprocessMemberRole", "-");
													attrHT.put("ToprocessMemberAll", "-\\-");
												}
											}else {
												ApproversHT=(Hashtable)currentemplyeeDetailsHT.get(approv);
												String toMember=(String)ApproversHT.get("empName");	
												String toMemberRole=(String)currentstageDetailsHT.get("Stage_Name");
												attrHT.put("ToprocessMember", toMember);
												attrHT.put("ToprocessMemberRole", toMemberRole);
												
												String toAllMembers=toMemberRole+"\\"+toMember;
												attrHT.put("ToprocessMemberAll", toAllMembers);
												
												
											}

										}else {
//											int nextStageNo = Integer.valueOf(currentStageNo)+1;
//											//										Node nxtNode=Globals.getNodeByAttrValUnderParent(doc, workflowNode, "Stage_No", String.valueOf(nextStageNo));
//											
//											Hashtable toStageDetailsHT=logn1.retriveStageDetailsFromXML(workflowNode, String.valueOf(nextStageNo),"");
//											if(toStageDetailsHT != null && !toStageDetailsHT.isEmpty()) {
//												Hashtable toEmplyeeDetailsHT=(Hashtable)toStageDetailsHT.get("EmployeedetHT");
//												ApproversHT=(Hashtable)toEmplyeeDetailsHT.get(0);
//												String toMember=(String)ApproversHT.get("empName");	
//												String toMemberRole=toStageDetailsHT.get("Stage_Name") == null ? "" : (String) toStageDetailsHT.get("Stage_Name");	
//												attrHT.put("ToprocessMember", toMember);
//												attrHT.put("ToprocessMemberRole", toMemberRole);
//											}else {
//												attrHT.put("ToprocessMember", "Completed");
//												attrHT.put("ToprocessMemberRole", "Completed");
//											}
											
											int nextStageNo = Integer.valueOf(currentStageNo)+1;
											//kishor--15-8
											if(Integer.valueOf(totalStageNo) >= nextStageNo) {
//												Node nxtNode=Globals.getNodeByAttrValUnderParent(doc, workflowNode, "Stage_No", String.valueOf(nextStageNo));
												Hashtable toStageDetailsHT=logn1.retriveStageDetailsFromXML(workflowNode, String.valueOf(nextStageNo),"");
												if(toStageDetailsHT != null && !toStageDetailsHT.isEmpty()) {
													Hashtable toEmplyeeDetailsHT=(Hashtable)toStageDetailsHT.get("EmployeedetHT");
													ApproversHT=(Hashtable)toEmplyeeDetailsHT.get(0);
													String toMember=(String)ApproversHT.get("empName");	
													String toMemberRole=toStageDetailsHT.get("Stage_Name") == null ? "" : (String) toStageDetailsHT.get("Stage_Name");	
													attrHT.put("ToprocessMember", toMember);
													attrHT.put("ToprocessMemberRole", toMemberRole);
													
													String toAllMembers="";
													for(int j=0;j<toEmplyeeDetailsHT.size();j++) {
														ApproversHT=(Hashtable)toEmplyeeDetailsHT.get(j);
														String toMember1=(String)ApproversHT.get("empName");	
														if(j==0) {
															toAllMembers=toMemberRole+"/"+toMember1;
														}else {
															toAllMembers=toAllMembers+", "+toMemberRole+"/"+toMember1;
														}
													}
													//System.out.println("=========toAllMembers  "+toAllMembers);
													attrHT.put("ToprocessMemberAll", toAllMembers);
													
												}else {
													attrHT.put("ToprocessMember", "-");
													attrHT.put("ToprocessMemberRole", "-");
													attrHT.put("ToprocessMemberAll", "-/-");
												}
											}else {
												attrHT.put("ToprocessMember", "-");
												attrHT.put("ToprocessMemberRole", "-");
												attrHT.put("ToprocessMemberAll", "-\\-");
											}
											
										}
										if(Properties.equalsIgnoreCase("Serial"))
											break;

									}
								}
								if(flag)
									break;
							}
							///



						}

						if(isneedUserDetails && stageNode != null){

							NodeList stageList=stageNode.getChildNodes();
							for(int j=0;j<stageList.getLength();j++){
								Node stagechildNode=stageList.item(j);
								if(stagechildNode.getNodeType()==Node.ELEMENT_NODE && stagechildNode.getNodeName().equals("Employee_Names")){

									Node userNode=Globals.getNodeByAttrValUnderParent(doc, stagechildNode, "Access_Unique_ID", AccessUniqID);
									if(userNode==null){
										if(attrHT.get("Current_Stage_Status") == null)
										attrHT.put("Current_Stage_Status", "NA");
									}else{  // Pandian 18MAR2014
										useravaiableonstage=true;
										Hashtable userDetailsHTfrmLevelXmlHT = Globals.getAttributeNameandValHT(userNode);
										String userLoginID=userNode.getTextContent();
										String userRole=(String)userDetailsHTfrmLevelXmlHT.get("User_Role");
										userDetailsHTfrmLevelXmlHT.put("userLoginID", userLoginID);
										attrHT.put("UserDetailsHT", userDetailsHTfrmLevelXmlHT); 
										attrHT.put("MemberRole", userRole);

									}
								}
							}
						}



					}else{ attrHT.put("Current_Stage_Status", "NA"); }

					// CODE CHANGE PANDIAN 21Mar2014 disable/enable icons
					// check weather the member is Admin /Public
					if(!useravaiableonstage){

						boolean userasAdmin=false;

						Node adminORpublicNode=Globals.getNodeByAttrValUnderParent(doc, workflowNode, "Stage_Name", "Admin");
						if(adminORpublicNode!=null){
							NodeList adminList=adminORpublicNode.getChildNodes();
							for(int j=0;j<adminList.getLength();j++){
								Node adminchildNode=adminList.item(j);
								if(adminchildNode.getNodeType()==Node.ELEMENT_NODE && adminchildNode.getNodeName().equals("Employee_Names")){

									Node userNode=Globals.getNodeByAttrValUnderParent(doc, adminchildNode, "Access_Unique_ID", AccessUniqID);
									if(userNode==null){}else{  // Pandian 18MAR2014
										useravaiableonstage=true;
										Hashtable userDetailsHTfrmLevelXmlHT = Globals.getAttributeNameandValHT(userNode);
										String userLoginID=userNode.getTextContent();
										String userRole=(String)userDetailsHTfrmLevelXmlHT.get("User_Role");
										userDetailsHTfrmLevelXmlHT.put("userLoginID", userLoginID);
										attrHT.put("UserDetailsHT", userDetailsHTfrmLevelXmlHT); 
										attrHT.put("MemberRole", userRole);

									}
								}
							}

						}// end of if(adminORpublicNode!=null){
						// chech the user have public
						if(!userasAdmin){

							Node publicNode=Globals.getNodeByAttrValUnderParent(doc, workflowNode, "Stage_Name", "public");
							if(publicNode!=null){
								NodeList publicList=publicNode.getChildNodes();
								for(int j=0;j<publicList.getLength();j++){
									Node publichildNode=publicList.item(j);
									if(publichildNode.getNodeType()==Node.ELEMENT_NODE && publichildNode.getNodeName().equals("Employee_Names")){

										Node userNode=Globals.getNodeByAttrValUnderParent(doc, publichildNode, "Access_Unique_ID", AccessUniqID);
										if(userNode==null){}else{  // Pandian 18MAR2014
											useravaiableonstage=true;
											Hashtable userDetailsHTfrmLevelXmlHT = Globals.getAttributeNameandValHT(userNode);
											String userLoginID=userNode.getTextContent();
											String userRole=(String)userDetailsHTfrmLevelXmlHT.get("User_Role");
											userDetailsHTfrmLevelXmlHT.put("userLoginID", userLoginID);
											attrHT.put("UserDetailsHT", userDetailsHTfrmLevelXmlHT); 
											attrHT.put("MemberRole", userRole);

										}
									}
								}
							}// end of if(publicNode!=null){

						}

					}


					/////


				}
			}
			attrHT.put("AttachementDetails",attchDocHT);
			System.out.println("attrHT===>"+attrHT);
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return attrHT;
	}







	public static Node getNodeByAttrValUnderParent(Document doc, Node parentNode, String attrName, String attrValue) {

		try {
			// System.out.println("Enter Attr1");
			NodeList nodelist = parentNode.getChildNodes();
			Node node = null;
			String attrValInNode = "";

			for (int i = 0; i < nodelist.getLength(); i++) {
				node = nodelist.item(i);
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					System.out.println("-=-=-=-=attrName-=-=-=-=-="+node.getNodeName());
					attrValInNode = Globals.getAttrVal4AttrName(node, attrName);
					System.out.println("ChkattrValInNode-=-=-=-=-="+attrValInNode+"-=-=-=-=attrValInNode-=-=-=-=-="+attrValue);
					if (attrValInNode.equalsIgnoreCase(attrValue)) {
						// System.out.println("Found Node: " +
						// node.getNodeName() + " that has " + attrName +
						// " and "
						// + attrValue);
						return node;
					}

				}// if(node.getNodeType()==Node.ELEMENT_NODE){

			}// for(int i=0;i<nodelist.getLength();i++){

		} catch (Exception e) {

			return null;

		}
		//		System.out.println("*** Warning: Did not find a Node under Parent: " + parentNode.getNodeName() + " that has " + attrName + " and "
		//												+ attrValue);
		return null;
	}


	public static void updateSingleNode(String nodename,String nodeID){


		try{
			PropUtil prop=new PropUtil();
			String dir=prop.getProperty("HIERARCHY_XML_DIR");
			Document doc=Globals.openXMLFile(dir, "HierachyLevel.xml");
			Node nd=Globals.getNodeByAttrVal(doc, nodename, "ID", nodeID);
			Hashtable nodedetHT=new Hashtable<>();
			Hashtable nodeNameandTypeHT4Segment=new Hashtable<>();
			int kk=1;
			processUpDowninDB(nd,nodedetHT,kk,nodeNameandTypeHT4Segment);


			///////////////////////
			for(int i=0; i<nodedetHT.size();i++){
				Hashtable ht=new Hashtable<>();  //3 19
				ht=(Hashtable)nodedetHT.get(i);
				String updatecodeValuew="";
				String updatenameValuew="";
				String ID=(String)ht.get("ID");



				if(i==0){
					String nodeName=(String)ht.get("NodeName");	
					int levelNo=0;
					if(nodeName.equalsIgnoreCase("RootLevel")){
						levelNo=1;
					}else{
						levelNo=Integer.parseInt(nodeName.replace("Level_", ""));
					}
					String hierType=(String)ht.get("Type");	
					Hashtable tempHT=new Hashtable<>();
					if(hierType.equals("Data")){

						updatecodeValuew=(String)ht.get("Code");
						String temp=(String)ht.get("value");
						temp=temp.replace("("+updatecodeValuew+")", "");
						updatenameValuew=temp;
						tempHT.put(0, updatecodeValuew);
						tempHT.put(1, updatenameValuew);
					}else{
						updatecodeValuew=(String)ht.get("value");
						updatenameValuew=updatecodeValuew;
						tempHT.put(0, updatecodeValuew);
						tempHT.put(1, updatenameValuew);
					}


					// full row update 
					//				update
					Hashtable columnNameHT=new Hashtable<>();
					Hashtable columnvalueHT=new Hashtable<>();
					columnNameHT=Globals.getcolumnName();
					int columnNameNo=0;
					int k=0;
					Hashtable columnNameTempHT=new Hashtable<>();
					for(int j=13;j<columnNameHT.size();j++){
						String temp=String.valueOf(columnNameHT.get(j)).split("_")[0];
						columnNameNo=Integer.parseInt(temp.substring(temp.lastIndexOf("R")+1,temp.length()));
						if(levelNo<=columnNameNo){
							columnNameTempHT.put(k, String.valueOf(columnNameHT.get(j)));
							k++;
						}
					}
					//				System.out.println("columnNameTempHT===>"+columnNameTempHT);
					for(int j=0;j<columnNameTempHT.size();j++){
						columnvalueHT.put(j, String.valueOf(tempHT.get(j%2)));
					}
					//				System.out.println("columnvalueHT===>"+columnvalueHT);
					String sql="UPDATE WC_FLEX_HIERARCHY_D SET ";
					for(int j=0;j<columnvalueHT.size();j++){
						sql=sql+columnNameTempHT.get(j)+"='"+columnvalueHT.get(j)+"',";
					}
					sql=sql.substring(0,sql.length()-1);
					sql=sql+" WHERE XML_NODE_ID = '"+ID+"'";
					Connection conn=Globals.getDBConnection("DW_Connection");
					//				System.out.println("SQL====>"+sql);
					PreparedStatement pre=conn.prepareStatement(sql);
					pre.execute();


				}else{
					String nodeName=nodename;
					if(nodeName.equalsIgnoreCase("RootLevel")){
						nodeName="1";
					}else{
						nodeName=nodeName.replace("Level_", "");
					}
					String updatecode="HIER"+nodeName+"_CODE";
					String updatename="HIER"+nodeName+"_NAME";
					ht=(Hashtable)nodedetHT.get(0);
					String hierType=(String)ht.get("Type");	

					if(hierType.equals("Data")){

						updatecodeValuew=(String)ht.get("Code");
						String temp=(String)ht.get("value");
						temp=temp.replace("("+updatecodeValuew+")", "");
						updatenameValuew=temp;

					}else{
						//					if()
						updatecodeValuew=(String)ht.get("value");
						updatenameValuew=updatecodeValuew;
					}


					String updateSQL4thread = "UPDATE WC_FLEX_HIERARCHY_D SET "+updatecode+"='"+updatecodeValuew+"',"+updatename+"='"+updatenameValuew+"' WHERE XML_NODE_ID = '"+ID+"'";
					Connection conn=Globals.getDBConnection("DW_Connection");
					//					System.out.println("updateSQL4thread====>"+updateSQL4thread);
					PreparedStatement pre=conn.prepareStatement(updateSQL4thread);
					pre.execute();

				}

			}
			/////////////////////////// 
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	public static void processUpDowninDB(Node node, Hashtable nodedetHT,int kk,Hashtable nodeNameandTypeHT4Segment) throws Exception {


		Hashtable parNodeDetHT=Globals.getAttributeNameandValHT(node);
		parNodeDetHT.put("NodeName", node.getNodeName());
		nodedetHT.put(0,parNodeDetHT);
		nodeNameandTypeHT4Segment.put(0, String.valueOf(parNodeDetHT.get("value")+"#~#"+String.valueOf(parNodeDetHT.get("Type"))+"#~#"+String.valueOf(parNodeDetHT.get("Segment"))+"#~#"+String.valueOf(parNodeDetHT.get("Code"))));
		processHierarchyNode(kk,nodedetHT,node,nodeNameandTypeHT4Segment);

		if (SOP_ENABLED)
			System.out.println("nodedetHT "+nodedetHT);
	}

	public static int processHierarchyNode(int key,Hashtable nodedetHT,Node node,Hashtable nodeNameandTypeHT4Segment)
	{

		System.out.println("Entering: " + new Exception().getStackTrace()[0].getClassName() + "."
				+ new Exception().getStackTrace()[0].getMethodName());

		try {

			if (node.getNodeType() == Node.ELEMENT_NODE) {

				NodeList nl = node.getChildNodes();
				// System.out.println("Reading HierarchyNode Length: " + nl.getLength());
				for (int i = 0; i < nl.getLength(); i++) {
					node = nl.item(i);

					if (node.getNodeType() == Node.ELEMENT_NODE) {

						System.out.println("------------>Reading HierarchyNode Name : " + node.getNodeName()+" "+node.getAttributes().getNamedItem("ID"));
						Hashtable NodeHT = new Hashtable();
						NodeHT = Globals.getAttributeNameandValHT(node);
						NodeHT.put("NodeName", node.getNodeName());
						//		      if(!node.getNodeName().equalsIgnoreCase("ID") && !node.getNodeName().equalsIgnoreCase("RootLevel_Name")){
						nodeNameandTypeHT4Segment.put(key, String.valueOf(NodeHT.get("value")+"#~#"+NodeHT.get("Type")+"#~#"+NodeHT.get("Segment")+"#~#"+NodeHT.get("Code")));
						//		      }
						System.out.println("Key "+key+" HT "+NodeHT);
						nodedetHT.put(key,NodeHT);
						key++;
						key= processHierarchyNode(key,nodedetHT,node,nodeNameandTypeHT4Segment); 
						System.out.println("Key =====>");
						//		      if(node.hasChildNodes())
						//		      {
						////		      key++;
						//		      }
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

	public static Hashtable Segment(Hashtable segmentHT,Connection conn,String hierarchyID,boolean fromFact) {
		Hashtable codeCombinationHT=new Hashtable<>();
		try{
			Hashtable segHT = new Hashtable<>();
			int s=0;
			PropUtil prop=new PropUtil();
			String hierarchy_XML=prop.getProperty("HIERARCHY_XML_DIR");
			String hierarchyConfig_XML=prop.getProperty("HIERARCHY_CONFIG_FILE");
			String hierarchyLevel_XML=prop.getProperty("HIERARCHY_XML_FILE");
			Document hierDoc=Globals.openXMLFile(hierarchy_XML, hierarchyLevel_XML);
			Node hiernd=Globals.getNodeByAttrVal(hierDoc, "Hierarchy_Level", "Hierarchy_ID", hierarchyID);
			NodeList hierNdList=hiernd.getChildNodes();
			boolean oldCodeChange=false;
			String sql="";
			String tableName="";
			String columnName="";
			Hashtable segColumnNameHT=new Hashtable<>(); 
			int k=1;
			String gropBy = "";
			// getting sql from xml
			for(int i=0;i<hierNdList.getLength();i++){
				if(hierNdList.item(i).getNodeType() == Node.ELEMENT_NODE && (hierNdList.item(i).getNodeName().equalsIgnoreCase("Segment_SQLS") || hierNdList.item(i).getNodeName().equalsIgnoreCase("Segment_Trigger_SQL"))){
					oldCodeChange=false;
					if(hierNdList.item(i).getNodeName().equalsIgnoreCase("Segment_Trigger_SQL")){
						Node segTriggerNd=hierNdList.item(i);
						NodeList segTriggerNdList=segTriggerNd.getChildNodes();
						for(int j=0;j<segTriggerNdList.getLength();j++){
							if(segTriggerNdList.item(j).getNodeName().equalsIgnoreCase("SQL") && segTriggerNdList.item(j).getNodeType() == Node.ELEMENT_NODE){
								sql=segTriggerNdList.item(j).getTextContent();
							}else if(segTriggerNdList.item(j).getNodeName().equalsIgnoreCase("TABLE_NAME") && segTriggerNdList.item(j).getNodeType() == Node.ELEMENT_NODE){
								tableName=segTriggerNdList.item(j).getTextContent();
							}else if(segTriggerNdList.item(j).getNodeName().equalsIgnoreCase("COLUMN_NAME") && segTriggerNdList.item(j).getNodeType() == Node.ELEMENT_NODE){
								columnName=segTriggerNdList.item(j).getTextContent();
							}else if(segTriggerNdList.item(j).getNodeName().contains("Segment_") && segTriggerNdList.item(j).getNodeType() == Node.ELEMENT_NODE){
								//										columnName=segTriggerNdList.item(j).getTextContent();
								segColumnNameHT.put(k, segTriggerNdList.item(j).getTextContent());
								k++;
							}

						}
						break;
					}
				}else{
					oldCodeChange=true;
				}
			}
			String numberOfSegments=Globals.getAttrVal4AttrName(hiernd, "Number_of_Segment");
			if(numberOfSegments.equals("") || numberOfSegments==null || numberOfSegments.equals("null")){
				numberOfSegments="0";
			}
			String temp="";
			if (SOP_ENABLED)
				System.out.println("segmentHT+=========>"+segmentHT);
			if(oldCodeChange){
				Document doc=Globals.openXMLFile(hierarchy_XML, hierarchyConfig_XML);
				NodeList ndlist=Globals.getNodeList(hierarchy_XML, hierarchyConfig_XML, "Segment_Trigger_SQL");

				//				String sql="";


				for(int i=0;i<ndlist.getLength();i++){
					//					if(segmentHT.get("Segment_"+i) == null || String.valueOf(segmentHT.get("Segment_"+i)).equalsIgnoreCase("null")) {
					//			            
					//				     } else {
					if(ndlist.item(i).getNodeType()==Node.ELEMENT_NODE && ndlist.item(i).getNodeName().equalsIgnoreCase("Segment_Trigger_SQL")){
						NodeList ndlist1=ndlist.item(i).getChildNodes();

						for(int j=0;j<ndlist1.getLength();j++){

							if(ndlist1.item(j).getNodeType()==Node.ELEMENT_NODE && ndlist1.item(j).getNodeName().equalsIgnoreCase("SQL")){
								if (SOP_ENABLED)
									System.out.println("ndl===>"+ndlist1.item(j).getTextContent());
								sql=ndlist1.item(j).getTextContent();
							}else if(ndlist1.item(j).getNodeType()==Node.ELEMENT_NODE && ndlist1.item(j).getNodeName().equalsIgnoreCase("TABLE_NAME")){
								tableName=ndlist1.item(j).getTextContent();
							}else if(ndlist1.item(j).getNodeType()==Node.ELEMENT_NODE && ndlist1.item(j).getNodeName().equalsIgnoreCase("COLUMN_NAME")){
								columnName=ndlist1.item(j).getTextContent();
							}
						}
					}
					//				     }
				}
				if(sql.contains("$$COLUMN_NAME$$") && sql.contains("$$TABLE_NAME$$")){
					sql=sql.replace("$$COLUMN_NAME$$", columnName);
					sql=sql.replace("$$TABLE_NAME$$", tableName);
				}
				sql=sql+" WHERE";
				for(int i=1;i<=Integer.parseInt(numberOfSegments);i++){ // 23FEB14

					if(segmentHT.get("Segment_"+i) == null || String.valueOf(segmentHT.get("Segment_"+i)).equalsIgnoreCase("null") || String.valueOf(segmentHT.get("Segment_"+i)).equalsIgnoreCase("") ) {

					} else {

						if(String.valueOf(segmentHT.get("Segment_"+i)).contains("~") && String.valueOf(segmentHT.get("Segment_"+i)).endsWith("~")){
							String segValue=String.valueOf(segmentHT.get("Segment_"+i));
							segValue=segValue.substring(0, segValue.length()-1);
							sql=sql+" T91397.account_seg"+i+"_code in ('"+segValue.replace("~", "','")+"') AND";
						}else if(String.valueOf(segmentHT.get("Segment_"+i)).contains("~") && !String.valueOf(segmentHT.get("Segment_"+i)).endsWith("~")){
							sql=sql+" T91397.account_seg"+i+"_code in ('"+String.valueOf(segmentHT.get("Segment_"+i)).replace("~", "','")+"') AND";
						}else{
							sql=sql+" T91397.account_seg"+i+"_code in ('"+String.valueOf(segmentHT.get("Segment_"+i))+"') AND";
						}
					}

				}
			}else{
				System.out.println("sql==>"+sql);

				if(sql.contains("$$COLUMN_NAME$$") && sql.contains("$$TABLE_NAME$$")){
					sql=sql.replace("$$COLUMN_NAME$$", columnName);
					sql=sql.replace("$$TABLE_NAME$$", tableName);
				}
				sql=sql+" WHERE";
				//constructing sql for getting code combination for the current node
				for(int i=1;i<=Integer.parseInt(numberOfSegments);i++){ // 23FEB14

					if(segmentHT.get("Segment_"+i) == null || String.valueOf(segmentHT.get("Segment_"+i)).equalsIgnoreCase("null") || String.valueOf(segmentHT.get("Segment_"+i)).equalsIgnoreCase("")) {

					} else {
						if(fromFact){
							temp = temp+"T91397"+"."+String.valueOf(segColumnNameHT.get(i))+",";
							gropBy = gropBy+","+"cc."+String.valueOf(segColumnNameHT.get(i));
							segHT.put(s, String.valueOf(segColumnNameHT.get(i)));
							s++;
						}

						if(String.valueOf(segmentHT.get("Segment_"+i)).contains("~") && String.valueOf(segmentHT.get("Segment_"+i)).endsWith("~")){
							String segValue=String.valueOf(segmentHT.get("Segment_"+i));
							segValue=segValue.substring(0, segValue.length()-1);
							sql=sql+" T91397."+String.valueOf(segColumnNameHT.get(i))+" in ('"+segValue.replace("~", "','")+"') AND";
						}else if(String.valueOf(segmentHT.get("Segment_"+i)).contains("~") && !String.valueOf(segmentHT.get("Segment_"+i)).endsWith("~")){
							sql=sql+" T91397."+String.valueOf(segColumnNameHT.get(i))+" in ('"+String.valueOf(segmentHT.get("Segment_"+i)).replace("~", "','")+"') AND";
						}else{
							sql=sql+" T91397."+String.valueOf(segColumnNameHT.get(i))+" in ('"+String.valueOf(segmentHT.get("Segment_"+i))+"') AND";
						}
					}

				}if(fromFact){
					temp = temp.substring(0, temp.length()-1);
					//					gropBy =gropBy.substring(0, gropBy.length()-1);
				}
			}
			if(fromFact){
				String origSql = sql.substring((sql.indexOf("from")));
				String sqltemp = sql.substring(0, (sql.indexOf("from")-1));
				sqltemp = sqltemp+","+temp+" ";

				sql = sqltemp+origSql;
			}
			sql=sql.substring(0, sql.length()-4);
			if (SOP_ENABLED)
				System.out.println("sql+=========>"+sql);
			//				Connection conn=Globals.getDBConnection("Data_Connection");
			if(!fromFact){
				PreparedStatement pre=conn.prepareStatement(sql);
				ResultSet rs=pre.executeQuery();

				//				System.out.println("qqqqqqqqqq+=========>"+rs.getString("ROW_WID"));
				genarateFactsBean gfb = new genarateFactsBean(hierarchyID, "", ""); 
				Hashtable dataTypeHT = gfb.getDatatypefromDBtable(tableName+"."+columnName, conn, hierarchyID, "", "");

				String decodeColDtype=(String)dataTypeHT.get(1);
				String size=(String)dataTypeHT.get(2);
				String precision=(String)dataTypeHT.get(3);

				String typeOfColumn = "";
				Hashtable codeCombinationvalueHT = new Hashtable<>();

				int i=0;
				while(rs.next()){

					if(decodeColDtype.contains("VARCHAR")||decodeColDtype.contains("CHAR"))
					{
						typeOfColumn = "VARCHAR";
						codeCombinationvalueHT.put(i, rs.getString(1));
						i++;

					}else if(decodeColDtype.contains("NUMBER"))   
					{
						typeOfColumn = "NUMBER";
						codeCombinationvalueHT.put(i, rs.getDouble(1));
						i++;
					}else if(decodeColDtype.contains("DATE"))  
					{
						typeOfColumn = "DATE";
						codeCombinationvalueHT.put(i, rs.getDate(1));
						i++;
					}else if(decodeColDtype.contains("TIMESTAMP"))  
					{
						typeOfColumn = "TIMESTAMP";
						codeCombinationvalueHT.put(i, rs.getTimestamp(1));
						i++;
					}

				}
				codeCombinationHT.put(0, codeCombinationvalueHT);
				codeCombinationHT.put(1, typeOfColumn);
				if (SOP_ENABLED)
					System.out.println("codeCombinationHT+=========>"+codeCombinationHT);
				pre.close();
				rs.close();
			}else{
				codeCombinationHT.put(0, sql);
				codeCombinationHT.put(1, gropBy);
				codeCombinationHT.put(2, tableName);
				codeCombinationHT.put(3, segHT);
			}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return codeCombinationHT;
	}
	public synchronized static void updateMaxID(String xml_dir, String xml_nm, String tag_nm, String attr_nm, String val) {

		// System.out.println("Entering: " + new
		// Exception().getStackTrace()[0].getClassName() + "."
		// + new Exception().getStackTrace()[0].getMethodName());

		FileOutputStream fos = null;
		FileInputStream fis = null;

		try {

			String tempFileName = xml_dir + "/" + xml_nm + "_temp";

			fis = new FileInputStream(xml_dir + "/" + xml_nm);
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(fis);
			doc.getDocumentElement().normalize();
			NodeList nodelist1 = doc.getElementsByTagName(tag_nm);
			Node node = nodelist1.item(0);
			Element el = (Element) node;
			el.setAttribute(attr_nm, val);
			// TransformerFactory transformerFactory = TransformerFactory
			// .newInstance();
			// Transformer transformer = transformerFactory.newTransformer();
			// transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			// DOMSource source = new DOMSource(doc);
			// fos = new FileOutputStream(xml_dir + "/" + xml_nm);

			writeXMLFile(doc, xml_dir, xml_nm);

		} catch (Exception e) {
			getErrorString(e);
		} finally {
			try {
				if (fis != null) { // code change Vishnu 22OCT2013 For deleting
					// RRS temp file
					fis.close();
					fis = null;
				}
				if (fos != null) {
					fos.flush();
					fos.close();
					fos = null;
				}
			} catch (Exception ie) {
				getErrorString(ie);
			}
		}

		// System.out.println("Exiting: " + new
		// Exception().getStackTrace()[0].getClassName() + "."
		// + new Exception().getStackTrace()[0].getMethodName());

	}



	public static Hashtable<String, String> getAttributeNameandValHT(Node node) {

		String[] attrStrArr = null;
		String attr_nm = "";
		String attr_val = "";
		NamedNodeMap attributes = node.getAttributes();
		Hashtable<String, String> ht = new Hashtable<String, String>();
		if (attributes != null) {
			int attributeCount = attributes.getLength();
			attrStrArr = new String[attributeCount];
			for (int j = 0; j < attributeCount; j++) {
				Attr attr = (Attr) attributes.item(j);
				attr_nm = attr.getName();
				if (attr_nm == null)
					attr_nm = "";
				attr_val = attr.getValue();
				if (attr_val == null)
					attr_val = "";
				ht.put(attr_nm, attr_val);
			}
		}
		return ht;

	}
	public static boolean isFileExists(String dir, String file_nm_with_ext) {

		boolean found = false;

		try {
			File f = new File(dir + file_nm_with_ext);
			found = f.exists();

		} catch (Exception e) {
			getErrorString(e);
		}
		return found;
	}


	// 15 FEB 14
	// Single Quote and other special chars are an issue. Create PreparedStmt with ? & setString, SetInt...
	//
	//	public static String insertSQL(Hashtable columnNameHT,Hashtable columnValueHT,String column2Exclude) {	//code change Vishnu 17Feb2014
	//		String sql="";
	//		try{
	//		sql="insert into WC_FLEX_HIERARCHY_D (";
	//		for(int j=0;j<columnNameHT.size();j++){
	//			if(!column2Exclude.equalsIgnoreCase(String.valueOf(columnNameHT.get(j)))){	//code change Vishnu 17Fev2014
	//			sql=sql+String.valueOf(columnNameHT.get(j))+",";
	//			}
	//		}
	//		
	////		System.out.println("===="+sql);
	//		sql=sql.substring(0,sql.length()-1)+")";
	////		System.out.println(">>>>"+sql);
	//		sql=sql+"values (";
	//		for(int j=0;j<columnValueHT.size();j++){
	//			if(!column2Exclude.equalsIgnoreCase(String.valueOf(columnNameHT.get(j)))){	//code change Vishnu 17Fev2014
	//			sql=sql+"'"+String.valueOf(columnValueHT.get(j))+"'"+",";
	//			}
	//		}
	//		sql=sql.substring(0,sql.length()-1)+")";
	//		
	//		System.out.println("insertSQL----> " + sql);
	//		
	//		}catch (Exception e) {
	//			// TODO: handle exception
	//			e.printStackTrace();
	//		}
	//		return sql;
	//	}
	//	

	public static String insertSQL(Hashtable columnNameHT,String column2Exclude,PreparedStatement ps,String flag4SQL6value,String preParedsql) {	//code change Vishnu 17Feb2014
		String sql="";
		try{

			if(flag4SQL6value.equalsIgnoreCase("gettingSQL"))//Code Change Gokul 22FEB2014
			{

				sql="insert into WC_FLEX_HIERARCHY_D (";
				for(int j=0;j<columnNameHT.size();j++){
					if(!column2Exclude.equalsIgnoreCase(String.valueOf(columnNameHT.get(j)))){	//code change Vishnu 17Fev2014
						sql=sql+String.valueOf(columnNameHT.get(j))+",";
					}
				}

				//		System.out.println("===="+sql);
				sql=sql.substring(0,sql.length()-1)+")";
				//		System.out.println(">>>>"+sql);
				sql=sql+"values (";
				for(int j=0;j<columnNameHT.size();j++){
					if(!column2Exclude.equalsIgnoreCase(String.valueOf(columnNameHT.get(j)))){	//code change Vishnu 17Fev2014
						//			sql=sql+"'"+String.valueOf(columnValueHT.get(j))+"'"+",";

						sql=sql+"?"+",";//Code change Gokul 21FEB2014


					}
				}
				sql=sql.substring(0,sql.length()-1)+")";

				//		System.out.println("insertSQL----> " + sql);
			}


		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return sql;
	}

	public static void insertvalue(Hashtable columnNameHT,Hashtable columnValueHT,String column2Exclude,PreparedStatement ps,
			String flag4SQL6value,String preParedsql,Connection dbConn,Hashtable coldetHT,String pdatatype) {
		try{
			genarateFactsBean gfb=new genarateFactsBean("","","");
			if(flag4SQL6value.equalsIgnoreCase("settingValues"))//code Change Gokul 22FEB2014
			{
				int index=1;


				for(int j=0;j<columnNameHT.size();j++){
					if(!column2Exclude.equalsIgnoreCase(String.valueOf(columnNameHT.get(j)))){	//code chane Vishnu 17Fev2014

						//					coldetHT=gfb.getDatatypefromDBtable("WC_FLEX_HIERARCHY_D."+String.valueOf(columnNameHT.get(j)),dbConn,"","","");
						Hashtable coldetHTTemp=(Hashtable) coldetHT.get(j);
						pdatatype=gfb.preferredDataType4SQLDataType(coldetHTTemp);
						String tarmesureCol=(String)coldetHTTemp.get(4);

						if(pdatatype!=null&&!pdatatype.equalsIgnoreCase(""))
						{
							String tempvalue=String.valueOf(columnValueHT.get(j));
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
								if(tempvalue.contains("."))
								{
									double temp=Double.parseDouble(tempvalue);
									int tempint=(int)Math.round(temp);
									ps.setInt(index, Integer.parseInt(String.valueOf(columnValueHT.get(j))));
									index++;
								}else{
									ps.setInt(index, Integer.parseInt(String.valueOf(columnValueHT.get(j))));
									index++;
								}
							}else if(pdatatype.equalsIgnoreCase("double"))
							{
								ps.setDouble(index, Double.parseDouble(tempvalue));
								index++;
							}else if(pdatatype.equalsIgnoreCase("date"))
							{
								ps.setDate(index, (java.sql.Date)(columnValueHT.get(j)));
								index++;
							}else if(pdatatype.equalsIgnoreCase("timestamp"))
							{
								ps.setTimestamp(index, (Timestamp)(columnValueHT.get(j)));
								index++;
							}else if(pdatatype.equalsIgnoreCase("long"))
							{
								ps.setLong(index, Long.parseLong(tempvalue));
								index++;
							}

						}

					}
				}
			}else if(flag4SQL6value.equalsIgnoreCase("settingValues4HIERNUM")){
				int index=1;

				if (SOP_ENABLED)
					System.out.println("preParedsql"+preParedsql);
				for(int j=0;j<columnNameHT.size();j++){

					Hashtable coldetHTTemp=(Hashtable) coldetHT.get(j);
					pdatatype=gfb.preferredDataType4SQLDataType(coldetHTTemp);
					String tarmesureCol=(String)coldetHTTemp.get(4);
					if(pdatatype!=null&&!pdatatype.equalsIgnoreCase(""))
					{
						String tempvalue=String.valueOf(columnValueHT.get(j));
						if(tempvalue==null||tempvalue.equalsIgnoreCase("")||tempvalue.equalsIgnoreCase("null"))
						{
							tempvalue="0";
						}
						if(pdatatype.equalsIgnoreCase("String"))
						{
							String temp=String.valueOf(columnValueHT.get(j));
							ps.setString(index, temp);
							index++;

						}else if(pdatatype.equalsIgnoreCase("int"))
						{
							if(tempvalue.contains("."))
							{
								double temp=Double.parseDouble(tempvalue);
								int tempint=(int)Math.round(temp);
								ps.setInt(index, Integer.parseInt(String.valueOf(columnValueHT.get(j))));
								index++;
							}else{
								ps.setInt(index, Integer.parseInt(String.valueOf(columnValueHT.get(j))));
								index++;
							}
						}else if(pdatatype.equalsIgnoreCase("double"))
						{
							ps.setDouble(index, Double.parseDouble(tempvalue));
							index++;
						}else if(pdatatype.equalsIgnoreCase("date"))
						{
							ps.setDate(index, (java.sql.Date)(columnValueHT.get(j)));
							index++;
						}else if(pdatatype.equalsIgnoreCase("timestamp"))
						{
							ps.setTimestamp(index, (Timestamp)(columnValueHT.get(j)));
							index++;
						}else if(pdatatype.equalsIgnoreCase("long"))
						{
							ps.setLong(index, Long.parseLong(tempvalue));
							index++;
						}

					}

				}

			}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		//		return ps;
	}
	public static Hashtable getcolumnName() {
		Hashtable<Integer, String> columnNameHT=new Hashtable<>();

		columnNameHT.put(0, "ROW_WID");
		columnNameHT.put(1, "HIER_CAT_CODE");
		columnNameHT.put(2, "HIER_CAT_DESC");
		columnNameHT.put(3, "HIERARCHY_ID");
		columnNameHT.put(4, "HIER_CODE");
		columnNameHT.put(5, "HIER_NAME");
		columnNameHT.put(6, "SORT_ORDER");
		columnNameHT.put(7, "PARENT_ROW_WID");
		columnNameHT.put(8,"XML_NODE_ID");
		columnNameHT.put(9,"SUB_HIER_NAME");
		columnNameHT.put(10,"NODE_TYPE");
		columnNameHT.put(11,"PARENT_CODE");
		columnNameHT.put(12,"HIER20_NUM");	//code change Vishnu 17Fev2014
		columnNameHT.put(13, "HIER1_CODE");
		columnNameHT.put(14, "HIER1_NAME");
		columnNameHT.put(15, "HIER2_CODE");
		columnNameHT.put(16, "HIER2_NAME");
		columnNameHT.put(17, "HIER3_CODE");
		columnNameHT.put(18, "HIER3_NAME");
		columnNameHT.put(19, "HIER4_CODE");
		columnNameHT.put(20, "HIER4_NAME");
		columnNameHT.put(21, "HIER5_CODE");
		columnNameHT.put(22, "HIER5_NAME");
		columnNameHT.put(23, "HIER6_CODE");
		columnNameHT.put(24, "HIER6_NAME");
		columnNameHT.put(25, "HIER7_CODE");
		columnNameHT.put(26, "HIER7_NAME");
		columnNameHT.put(27, "HIER8_CODE");
		columnNameHT.put(28, "HIER8_NAME");
		columnNameHT.put(29, "HIER9_CODE");
		columnNameHT.put(30, "HIER9_NAME");
		columnNameHT.put(31, "HIER10_CODE");
		columnNameHT.put(32, "HIER10_NAME");
		columnNameHT.put(33, "HIER11_CODE");
		columnNameHT.put(34, "HIER11_NAME");
		columnNameHT.put(35, "HIER12_CODE");
		columnNameHT.put(36, "HIER12_NAME");
		columnNameHT.put(37, "HIER13_CODE");
		columnNameHT.put(38, "HIER13_NAME");
		columnNameHT.put(39, "HIER14_CODE");
		columnNameHT.put(40, "HIER14_NAME");
		columnNameHT.put(41, "HIER15_CODE");
		columnNameHT.put(42, "HIER15_NAME");
		columnNameHT.put(43, "HIER16_CODE");
		columnNameHT.put(44, "HIER16_NAME");
		columnNameHT.put(45, "HIER17_CODE");
		columnNameHT.put(46, "HIER17_NAME");
		columnNameHT.put(47, "HIER18_CODE");
		columnNameHT.put(48, "HIER18_NAME");
		columnNameHT.put(49, "HIER19_CODE");
		columnNameHT.put(50, "HIER19_NAME");
		columnNameHT.put(51, "HIER20_CODE");
		columnNameHT.put(52, "HIER20_NAME");

		return columnNameHT;
	}

	public static String gettingParentCodefromDB(String nodeID,Connection conn) {

		String sql="";
		String parentCode="";
		try{

			//			Connection conn=getDBConnection("DW_Connection");
			sql="SELECT ROW_WID FROM WC_FLEX_HIERARCHY_D WHERE XML_NODE_ID = '"+nodeID+"'";
			PreparedStatement pre=conn.prepareStatement(sql);
			ResultSet rs=pre.executeQuery();
			while(rs.next()){
				parentCode=rs.getString("ROW_WID");
			}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return parentCode;
	}

	public synchronized static String writeXMLFile(Document doc, String XMLFileLocation, String XMLFileName) {

		if (SOP_ENABLED)
			System.out.println("Entering: " + new Exception().getStackTrace()[0].getClassName() + "."
					+ new Exception().getStackTrace()[0].getMethodName());

		FileInputStream fis = null;
		FileOutputStream fos = null;
		DocumentBuilderFactory dbf = null;
		DocumentBuilder db = null;
		Document doc1 = doc;

		// write.lock(); ?? pandian for what use this code so just i have
		// comments this line

		try {

			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			DOMSource source = new DOMSource(doc1);

			fos = new FileOutputStream(XMLFileLocation + XMLFileName);

			StreamResult result = new StreamResult(fos);
			transformer.transform(source, result);
			result.getOutputStream().flush();
			result.getOutputStream().close();

		} catch (Exception e) {
			getErrorString(e);
		} finally {
			try {
				if (fis != null) {
					fis.close();
					fis = null; // code change Vishnu 22OCT2013 For deleting RRS
					// temp file
				}
				if (fos != null) {
					fos.flush(); // code change Vishnu 22OCT2013 For deleting
					// RRS temp file
					fos.close();
					fos = null;
				}
			} catch (Exception ioe) {
				getErrorString(ioe);
			}

		}

		if (SOP_ENABLED)
			System.out.println("Exiting: " + new Exception().getStackTrace()[0].getClassName() + "."
					+ new Exception().getStackTrace()[0].getMethodName());

		return "Success";

	}


	public synchronized static Document openXMLFile(String XMLFileLocation, String XMLFileName) {

		FileInputStream fis = null;
		FileOutputStream fos = null;
		DocumentBuilderFactory dbf = null;
		DocumentBuilder db = null;
		Document doc = null;

		read.lock();
		try {

			fis = new FileInputStream(XMLFileLocation + XMLFileName);

			dbf = DocumentBuilderFactory.newInstance();
			dbf.setValidating(false);
			dbf.setExpandEntityReferences(false);
			db = dbf.newDocumentBuilder();
			doc = db.parse(fis);
			doc.getDocumentElement().normalize();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			getErrorString(e);
		} finally { // code change Vishnu 22OCT2013 For deleting RRS temp file
			read.unlock(); // Code Change Bharath 16DEC13
			try {
				if (fis != null) {
					fis.close();
					fis = null;
				}
			} catch (Exception ex) {

			}
		}
		return doc;
	}
	public synchronized static Node getNodeByAttrVal(Document doc, String tag_nm, String attrName, String attrValue) {

		Node node = null;
		try {
			NodeList nodelist = doc.getElementsByTagName(tag_nm);
			String nodenm = "";
			String nodeval = "";

			for (int i = 0; i < nodelist.getLength(); i++) {
				node = nodelist.item(i);
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					nodenm = node.getNodeName();
					if (nodenm.equals(tag_nm)) {
						nodeval = Globals.getAttrVal4AttrName(node, attrName);
						if (nodeval.equals(attrValue)) {

							return node;
						}

					}// if(nodenm.equals(tag_nm)){

				}// if(node.getNodeType()==Node.ELEMENT_NODE){

			}// for(int i=0;i<nodelist.getLength();i++){

			//			System.out.println("*** Warning: Did not find a Node under Parent: " + tag_nm + " that has " + attrName + " and " + attrValue);

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		if (node != null) {

			//			System.out.println("*** Warning: Did not find a Node under Parent: " + node.getNodeName() + " that has " + attrName + " and "
			//													+ attrValue);
		}
		return null;
	}
	public static String getAttrVal4AttrName(Node node, String attr_nm2check) { 

		String attr_nm = "";
		String attr_val = "";
		NamedNodeMap attributes = null;
		try {

			if (node != null) {
				attributes = node.getAttributes();
			}

			if (attributes != null) {
				int attributeCount = attributes.getLength();
				for (int j = 0; j < attributeCount; j++) {
					Attr attr = (Attr) attributes.item(j);
					attr_nm = attr.getName();
					
					if (attr_nm.equals(attr_nm2check)) {
						attr_val = attr.getValue();
						break;
					}
				}
			}
		} catch (Exception e) {
			Globals.getErrorString(e);
		}
		return attr_val;
	}




	public static void updateFactStatus(Hashtable resultHT,String heirID){   // code change Menaka 15FEB2014


		try{
			String result=(String) resultHT.get("result");
			String errorMessage=(String) resultHT.get("errorMessage");

			System.out.println("result---->>>"+result);
			System.out.println("errorMessage---->>>"+errorMessage);

			//Code Commented Gokul 21FEB2014
			//			FacesContext ctx = FacesContext.getCurrentInstance();
			//			ExternalContext extContext = ctx.getExternalContext();
			//			Map sessionMap = extContext.getSessionMap();
			//			HierarchyBean hryb = (HierarchyBean) sessionMap.get("hierarchyBean");

			PropUtil prop=new PropUtil();
			String dir=prop.getProperty("HIERARCHY_XML_DIR");

			Document doc = Globals.openXMLFile(dir, "HierachyLevel.xml");
			Node rootnode = Globals.getNodeByAttrVal(doc, "Fact_Tables", "ID",heirID);

			if(rootnode != null){

				if(rootnode.getNodeType() == Node.ELEMENT_NODE){
					NodeList child = rootnode.getChildNodes();
					for(int i=0;i<child.getLength();i++){

						if(child.item(i).getNodeName().equals("Fact_Result")){

							child.item(i).setTextContent(result);

						}
						if(child.item(i).getNodeName().equals("Error_Message")){

							child.item(i).setTextContent(errorMessage);

						}

					}
				}
			}

			writeXMLFile(doc, dir, "HierachyLevel.xml");
			System.out.println("Fact Status Updated in XML");	
		}catch(Exception e){
			e.printStackTrace();
		}

	}

	public static void updateDimStatus(Hashtable dimResultHT,String hierarchyXMLFile,Document doc){   // start code change Jayaramu 21FEB2014


		try{
			String result=(String) dimResultHT.get("dimResult");
			String errorMessage=(String) dimResultHT.get("errorMessage");

			FacesContext ctx = FacesContext.getCurrentInstance();
			ExternalContext extContext = ctx.getExternalContext();
			Map sessionMap = extContext.getSessionMap();
			HierarchyBean hryb = (HierarchyBean) sessionMap.get("hierarchyBean");

			PropUtil prop=new PropUtil();
			String dir=prop.getProperty("HIERARCHY_XML_DIR");


			Node rootnode = Globals.getNodeByAttrVal(doc, "Hierarchy_Level", "Hierarchy_ID", hryb.getHierarchy_ID());

			if(rootnode != null){

				if(rootnode.getNodeType() == Node.ELEMENT_NODE){

					Element em=(Element)rootnode;
					em.setAttribute("Dim_Status", result);
					em.setAttribute("Dim_Status_Details", errorMessage);
				}
			}

			writeXMLFile(doc, dir, hierarchyXMLFile);
			System.out.println("<=========Dim generation Status Updated in XML========>>>>>>");	
		}catch(Exception e){
			e.printStackTrace();
		}

	}//End code change Jayaramu 21FEB2014


	public static String getErrorString(Exception e){  // code change Menaka 24FEB2014


		String exceptionStr = null;
		StringWriter sw = null;
		PrintWriter pw = null;

		try {

			sw = new StringWriter();
			pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			exceptionStr = sw.toString();
			if (exceptionStr.contains("at ")){
				exceptionStr = exceptionStr.substring(0, exceptionStr.indexOf("at ") - 3);
			}else{
				exceptionStr = exceptionStr;
			}

			System.out.println("exceptionStr---->>>"+exceptionStr);

		} finally {
			try {
				if (pw != null) {
					pw.close();
				}
				if (sw != null) {
					sw.close();
				}
			} catch (IOException ignore) {

			}
		}

		return exceptionStr;

	}

	public static Hashtable readSecurityxml(String HIERARCHY_XML_DIR) { //code change Jayaramu 12MAR14

		Hashtable ha1 = new Hashtable();
		Hashtable ha2 = new Hashtable();
		Hashtable ha3 = new Hashtable();
		try {
			NodeList secNodeList = getNodeList(HIERARCHY_XML_DIR, "Security.xml", "login");
			if (secNodeList == null) {
				ha1.put("status", "Error");
				return ha1;
			}
			int totalReports = secNodeList.getLength();

			for (int s = 0; s < totalReports; s++) {
				Node rootNode = secNodeList.item(s);
				if (rootNode.getNodeType() == Node.ELEMENT_NODE) {
					Element loginEle = (Element) rootNode;
					NodeList userNamenodeList = loginEle.getElementsByTagName("username");
					Element userNameElement = (Element) userNamenodeList.item(0);
					NodeList userNameNode = userNameElement.getChildNodes();

					ha1.put(s, userNameNode.item(0).getNodeValue());
					Element pwdEle = (Element) rootNode;
					NodeList pwdNodelist = pwdEle.getElementsByTagName("password");
					Element pwdElement = (Element) pwdNodelist.item(0);
					NodeList pwdNode = pwdElement.getChildNodes();
					ha2.put(s, pwdNode.item(0).getNodeValue());

				}
			}
			ha3.put("user", ha1);
			ha3.put("pwd", ha2);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ha3; // Node Values
	}
	public static String getException(ExceptionBean exceptionBean, Exception ex) {

		String status;

		try {


			ex.printStackTrace();

			status = Globals.stackTraceToExceptionStr(ex);

			if (status == null)
				status = "*** Unknown Error: Unable to capture the Error. Check the log file for details.";


			exceptionBean.setMsg1(status.substring(0, status.length() <= 200 ? status.length() : 199));

		} catch (Exception e) {

			e.printStackTrace();
			return "*** Exception in getting Exception.";
		}

		return status;
	}
	//public static Node getNodeByAttrValUnderParent(Document doc, Node parentNode, String attrName, String attrValue) {
	//
	//	try {
	//		// System.out.println("Enter Attr1");
	//		NodeList nodelist = parentNode.getChildNodes();
	//		Node node = null;
	//		String attrValInNode = "";
	//
	//		for (int i = 0; i < nodelist.getLength(); i++) {
	//			node = nodelist.item(i);
	//			if (node.getNodeType() == Node.ELEMENT_NODE) {
	//				attrValInNode = Globals.getAttrVal4AttrName(node, attrName);
	//				if (attrValInNode.equals(attrValue)) {
	//					// System.out.println("Found Node: " +
	//					// node.getNodeName() + " that has " + attrName +
	//					// " and "
	//					// + attrValue);
	//					return node;
	//				}
	//
	//			}// if(node.getNodeType()==Node.ELEMENT_NODE){
	//
	//		}// for(int i=0;i<nodelist.getLength();i++){
	//
	//	} catch (Exception e) {
	//		e.printStackTrace();
	//		return null;
	//
	//	}
	//	System.out.println("*** Warning: Did not find a Node under Parent: " + parentNode.getNodeName() + " that has " + attrName + " and "
	//											+ attrValue);
	//	return null;
	//}
	public static String stackTraceToExceptionStr(Exception e) {
		String exceptionStr = null;
		StringWriter sw = null;
		PrintWriter pw = null;

		try {
			sw = new StringWriter();
			pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			exceptionStr = sw.toString();
			if (exceptionStr.contains("at "))
				exceptionStr = exceptionStr.substring(0, exceptionStr.indexOf("at ") - 3);
			else
				exceptionStr = exceptionStr;
		} finally {
			try {
				if (pw != null) {
					pw.close();
				}
				if (sw != null) {
					sw.close();
				}
			} catch (IOException ignore) {
				getErrorString(ignore);
			}
		}

		return exceptionStr;
	}


	public static Hashtable auditColumnName() {
		Hashtable auditColumnNameHT=new Hashtable<>();

		try{
			auditColumnNameHT.put(0, "LOGIN_ID");
			auditColumnNameHT.put(1, "DATE_TIME");
			auditColumnNameHT.put(2, "HIERARCHY_ID");
			auditColumnNameHT.put(3, "ACTION");
			auditColumnNameHT.put(4, "PARENT_NODE_ID");
			auditColumnNameHT.put(5, "PARENT_NODE_VALUE");
			auditColumnNameHT.put(6, "NODE_ID");
			auditColumnNameHT.put(7, "BEFORE_NODE_TEXT");
			auditColumnNameHT.put(8, "AFTER_NODE_TEXT");
			auditColumnNameHT.put(9, "BEFORE_PARENT_NODE_MOVE");
			auditColumnNameHT.put(10, "BEFORE_PARENT_VALUE_MOVE");
			auditColumnNameHT.put(11, "BEFORE_POSITION_MOVE");
			auditColumnNameHT.put(12, "AFTER_PARENT_NODE_MOVE");
			auditColumnNameHT.put(13, "AFTER_PARENT_VALUE_MOVE");
			auditColumnNameHT.put(14, "AFTER_POSITION_MOVE");

		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return auditColumnNameHT;
	}
	public static String getContentType4File(String fileType)
    {
        String contentType = "";
        if (fileType.equalsIgnoreCase("pdf"))
        {
            contentType = "application/pdf";
        }
        else if (fileType.equalsIgnoreCase("txt"))
        {
            contentType = "application/octet-stream";
        }
        else if (fileType.equalsIgnoreCase("pptx"))
        {
            contentType = "application/vnd.openxmlformats-officedocument.presentationml.presentation";
        }
        else if (fileType.equalsIgnoreCase("ppt"))
        {
            contentType = "application/vnd.ms-powerpoint";
        }
        else if (fileType.equalsIgnoreCase("docx"))
        {
            contentType = "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
        }
        else if (fileType.equalsIgnoreCase("doc"))
        {
            contentType = "application/msword";
        }
        else if (fileType.equalsIgnoreCase("xlsx"))
        {
            contentType = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
        }
        else if (fileType.equalsIgnoreCase("xls"))
        {
            contentType = "application/vnd.ms-excel";
        }
        else if (fileType.equalsIgnoreCase("jpeg") || fileType.equalsIgnoreCase("jpg") || fileType.equalsIgnoreCase("png"))
        {
            contentType = "image/jpeg,image/png";
        }
        return contentType;
    }
	
	public static String getwrkflowUserloginUrl(String xmlFileDir, String configFileName, String rootName)
    {
        String str = "";
        try
        {
            Document doc = Globals.openXMLFile(xmlFileDir, configFileName);
            NodeList rootNode = doc.getElementsByTagName(rootName);
            Node node = null;
            //XmlNode Template_Node = doc.FirstChild;
            if (rootNode != null && rootNode.getLength() > 0)
            {
                node = rootNode.item(0);
                str = node.getTextContent();
                //node.InnerText = "FALSE";
                //doc.Save(xmlFileDir + configFileName);
            }


        }
        catch (Exception e)
        {
            str = e.getMessage();

        }
        return str;
    }
	
	public static String wrkflw_webService(String strUrl, Boolean flag)
    {
        String str = "";
        try
        {
        	String completeUrl = strUrl;                // Create a request for the URL.         
            completeUrl = encodeUrlParameters(completeUrl);
            URL url = new URL(completeUrl);
            HttpURLConnection request = (HttpURLConnection) url.openConnection();
            // If required by the server, set the credentials.
//            request.setRequestProperty("UserAgent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1; .NET CLR 1.1.4322; .NET CLR 2.0.50727)");
//            ServicePointManager.SecurityProtocol = SecurityProtocolType.Tls | SecurityProtocolType.Tls11 | SecurityProtocolType.Tls12;
//            request.Credentials = CredentialCache.DefaultCredentials;
//            request.UserAgent = "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1; .NET CLR 1.1.4322; .NET CLR 2.0.50727)";
            //ServicePointManager.SecurityProtocol = SecurityProtocolType.Tls;
            //Get the response.
            request.connect();
            InputStream response = request.getInputStream();
        	StringBuilder stringBuilder = new StringBuilder();
        	String line = null;
        	
        	try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(response))) {	
        		while ((line = bufferedReader.readLine()) != null) {
        			stringBuilder.append(line);
        		}
        		bufferedReader.close();
        	}
            str = stringBuilder.toString();
            // Cleanup the streams and the response.
//            request.Close();
//            dataStream.Close();
            response.close();
        }
        catch (Exception e)
        {
        	e.printStackTrace();
            //MessageBox.Show("URL :"+ strUrl);
            String status = e.getMessage();
            if (flag)
            {

                //StringBuilder sb = Globals.getException1(e);
                //Globals.displays(status, sb.ToString());
                //Globals.writelogfile(status + "\n" + sb.ToString(), Datasources.logfilename);
            }
            else
            {
                return "error~" + status;
            }

        }
        return str;
    }
	
	public static String encodeUrlParameters(String url) throws Exception
    {
        String convertedURL = url.trim();
        if (url.contains("?"))
        {
            convertedURL = url.split("\\?")[0] + "?";
            String[] parameters = url.split("\\?")[1].split("&");
            int i = 0;
            for (String parameter : parameters)
            {
                String key = parameter.split("=")[0];
                String value = parameter.split("=")[1].trim();
                convertedURL = convertedURL + (i == 0 ? "" : "&") + key + "=" + URLEncoder.encode(value, "UTF-8");
                i++;
            }
        }
        return convertedURL;
    }
	
	public static Boolean addConnectionDetails(String xmlDirectory, String xmlFileName, String xmlConfigFileName, Hashtable connectionHT,
	        String connectionName, boolean isAdd, String comingFrom, String configmetadirectory)
	    {
	        
	            System.out.println("Entering: " + new Exception().getStackTrace()[0].getClassName() + "."
	    				+ new Exception().getStackTrace()[0].getMethodName());

	            Boolean isEdit = false;

	            int maxID = Globals.getMaxID(configmetadirectory, xmlConfigFileName, "Script_Creation_ID", "ID");
	            Document doc = Globals.openXMLFile(xmlDirectory, xmlFileName);

	            Node chldNd = null;
	            Node root = doc.getElementsByTagName("Config").item(0);

	            NodeList childLst = root.getChildNodes();

	            System.out.println("connectionName" + connectionName);
	            System.out.println("comingFrom" + comingFrom);
	            String connectionID = "";

	            for (int m = 0; m < childLst.getLength(); m++)
	            {

	                Node chldNwd = childLst.item(m);
	                if (chldNwd.getNodeType() == Node.ELEMENT_NODE && chldNwd.getNodeName().equalsIgnoreCase("Repository"))
	                {
	                    chldNd = chldNwd;
	                    NodeList childConlst = chldNwd.getChildNodes();
	                    for (int j = 0; j < childConlst.getLength(); j++)
	                    {
	                        Node connectionNameND = childConlst.item(j);
	                        if (connectionNameND.getNodeType() == Node.ELEMENT_NODE && connectionNameND.getNodeName().equalsIgnoreCase("Type"))
	                        {
	                            Element typeELE = (Element)connectionNameND;
	                            if (typeELE.getAttribute("Connection_Type").equalsIgnoreCase(comingFrom))
	                            {
	                                //MessageBox.Show(connectionName + " <=====> " + typeELE.GetAttribute("Connection_Name"));
	                                if (typeELE.getAttribute("Connection_Name").equalsIgnoreCase(connectionName))
	                                {
	                                    System.out.println("connectionName-----" + connectionName);
	                                    //MessageBox.Show(connectionName + " <==TRUEEEEEEEEEEEEEEE===> " + typeELE.GetAttribute("Connection_Name"));
	                                    connectionID = typeELE.getAttribute("Connection_ID");
	                                    connectionNameND.getParentNode().removeChild(connectionNameND);
	                                    isEdit = true;
	                                    break;

	                                }


	                            }



	                        }
	                    }
	                }

	            }

	            //MessageBox.Show("connectionName: " + connectionName+ " connectionID: " + connectionID);
	            if (isAdd)
	            {
	                if (connectionID == null || connectionID.isEmpty())
	                {

	                    connectionID = "C_" + maxID;

	                }


	                Element TypeNode = doc.createElement("Type");
	                if (chldNd == null)
	                {
	                    chldNd = doc.createElement("Repository");
	                    root.appendChild(chldNd);
	                }


	                TypeNode.setAttribute("Connection_ID", connectionID);
	                Set<String> test = connectionHT.keySet(); 
	                for (String key : test)
	                {
	                	String value = (String)connectionHT.get(key);
//	                    if (key.equalsIgnoreCase("Password"))
//	                    {
//	                        value = Encrypt(value);
//	                    }
	                    TypeNode.setAttribute(key, value);

	                    chldNd.appendChild(TypeNode);

	                }

	                chldNd.appendChild(TypeNode);


	            }
	            Globals.writeXMLFile(doc, xmlDirectory, xmlFileName);
//	            System.out.println("isEdit" + isEdit);
//	            doc.Save(xmlDirectory + xmlFileName);
	            //Thread.Sleep(500);
	            System.out.println("Exiting: " + new Exception().getStackTrace()[0].getClassName() + "."
	    				+ new Exception().getStackTrace()[0].getMethodName());
	            return isEdit;
	        
	    }
}

