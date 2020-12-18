package beans;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.io.StringWriter;
import java.net.URL;
import java.sql.Connection;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import javax.faces.FacesException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.swing.tree.TreeNode;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;


import model.ReportTree;

import org.openfaces.util.Faces;
import org.richfaces.component.UITree;
import org.richfaces.event.TreeSelectionChangeEvent;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import rootLevel.ReportsInvXMLParser;


import utils.Globals;

import utils.PropUtil;



@ManagedBean(name = "reportsInvXMLTreeBean")
// code change pandian 022813 - Changed to SessionScoped and called
// getGRSDBData() as a hidden field
@ViewScoped
public class ReportsInvXMLTreeBean implements Serializable {

	private final ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();
	private final Lock read = readWriteLock.readLock();
	private final Lock write = readWriteLock.writeLock();

//	private String statusMesg = "Success";
	private NodeList nodeList=null; //code change Jayaramu 13NOV13
	private Document document=null; //code change Jayaramu 13NOV13
	private TransformerFactory     factory          =null;
    private Transformer            transformer      =null;
	private String dbData;
	private String GRSDbData;
	private String GRSDbData1;
	private String selectedReferenceHierarchy = "";
	private String refHierarchyID = "";
	public String getRefHierarchyID() {
		return refHierarchyID;
	}



	public void setRefHierarchyID(String refHierarchyID) {
		this.refHierarchyID = refHierarchyID;
	}

	private List<ReportTree> selectedRefHierarchyRows = new ArrayList<ReportTree>();

	
private String msg4RH="";  // code change Menaka 28MAR2014
	
	

	public String getMsg4RH() {
		return msg4RH;
	}



	public void setMsg4RH(String msg4rh) {
		msg4RH = msg4rh;
	}

	
	public List<ReportTree> getSelectedRefHierarchyRows() {
		return selectedRefHierarchyRows;
	}



	public void setSelectedRefHierarchyRows(
			List<ReportTree> selectedRefHierarchyRows) {
		this.selectedRefHierarchyRows = selectedRefHierarchyRows;
	}



	public String getSelectedReferenceHierarchy() {
		return selectedReferenceHierarchy;
	}



	public void setSelectedReferenceHierarchy(String selectedReferenceHierarchy) {
		this.selectedReferenceHierarchy = selectedReferenceHierarchy;
	}

	private List<Report> repSession;
	private List<TreeNode> rootNodes = new ArrayList<TreeNode>();

	private List<ReportTree> reportTree1 = new ArrayList<ReportTree>(); 
	private List<ReportTree> reportTree = new ArrayList<ReportTree>(); 
	private List<ReportTree> selectedReports = new ArrayList<ReportTree>();
	private TreeNode currentSelection = null;
	HeirarchyDataBean hdb2 = null;
	String flagForEditdata="";
	public String getFlagForEditdata() {
		return flagForEditdata;
	}



	public void setFlagForEditdata(String flagForEditdata) {
		this.flagForEditdata = flagForEditdata;
	}

	private static String HIERARCHY_XML_DIR;
	
	public static String getHIERARCHY_XML_DIR() {
		return HIERARCHY_XML_DIR;
	}



	public static void setHIERARCHY_XML_DIR(String hIERARCHY_XML_DIR) {
		HIERARCHY_XML_DIR = hIERARCHY_XML_DIR;
	}

	private String hierarchyXmlFileName;
	private String hierarchyId;

	public String getHierarchyXmlFileName() {
		return hierarchyXmlFileName;
	}



	public void setHierarchyXmlFileName(String hierarchyXmlFileName) {
		this.hierarchyXmlFileName = hierarchyXmlFileName;
	}



	public String getHierarchyId() {
		return hierarchyId;
	}



	public void setHierarchyId(String hierarchyId) {
		this.hierarchyId = hierarchyId;
	}


	public ReportsInvXMLTreeBean(){
//		System.out.println("aa---->>>> ");
		try{
		SessionExpireBean sesExpire=new SessionExpireBean();  // code change pandian 26Aug2013
		String isSameSession=sesExpire.isSameSession();
//		 System.out.println("isSameSession---->>"+isSameSession);
		if(isSameSession.equalsIgnoreCase("redirect")){
			
			 System.out.println("Session timed out");
			return;
		}else{
			getGRSDbData(hdb2, flagForEditdata);
		}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}
	
	public ReportsInvXMLTreeBean(String hierarchyXmlFileName, String hierarchyId,HeirarchyDataBean hdb1,String flagForEditdata) throws Exception {		
		this.hierarchyId=hierarchyId;
		this.hierarchyXmlFileName=hierarchyXmlFileName;
		hdb2 =  hdb1;
		this.flagForEditdata = flagForEditdata;
			
		getGRSDbData(hdb2,flagForEditdata); 
		
		
	}
	public String versionNo = "";
	public ReportsInvXMLTreeBean(String hierarchyXmlFileName, String hierarchyId,HeirarchyDataBean hdb1,String flagForEditdata,String versionNo) throws Exception {
		try{
		this.hierarchyId=hierarchyId;
		this.hierarchyVersionXmlFileName=hierarchyXmlFileName;
		hdb2 =  hdb1;
		this.flagForEditdata = flagForEditdata;
		this.versionNo=versionNo;	
		getGRSDbData1(hdb2,flagForEditdata,versionNo); 
		}catch (Exception e) {
			
			e.printStackTrace();
		}
		
	}
	public String hierarchyVersionXmlFileName="";
	public String getHierarchyVersionXmlFileName() {
		return hierarchyVersionXmlFileName;
	}



	public void setHierarchyVersionXmlFileName(String hierarchyVersionXmlFileName) {
		this.hierarchyVersionXmlFileName = hierarchyVersionXmlFileName;
	}



	public String getGRSDbData1(HeirarchyDataBean hdb1,String flagForEditdata,String versionNo) {
		try{
		FacesContext context = FacesContext.getCurrentInstance();
		Map sessionMap = context.getExternalContext().getSessionMap();
		Map applicationMap = context.getExternalContext().getApplicationMap();
		HierarchyBean hier = (HierarchyBean) sessionMap.get("hierarchyBean");
		
		this.hierarchyId=hier.getHierarchy_ID();
		this.hierarchyVersionXmlFileName=hier.hierLevelVersionXmlFileName;
		this.versionNo = versionNo;
//		System.out.println("hierarchyId---->>>> "
//				+ hierarchyId + "hierarchyXmlFileName---->>>"
//				+ hierarchyXmlFileName);
//		if(versionNo.equals("")){
//			getReportsForGenSession(hierarchyXmlFileName,hierarchyId,hdb1,flagForEditdata);
//		}else{
			getReportsForGenSession(hierarchyVersionXmlFileName,hierarchyId,hdb1,flagForEditdata,versionNo);
//		}

			
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
			System.out.println("Exiting: "
					+ new Exception().getStackTrace()[0].getClassName() + "."
					+ new Exception().getStackTrace()[0].getMethodName());
		
		return GRSDbData1;
	}



	public void setGRSDbData1(String gRSDbData1) {
		GRSDbData1 = gRSDbData1;
	}
	public List<ReportTree> getSelectedReports() {

		for (int i = 0; i < selectedReports.size(); i++)
			System.out.println("selectedReports: "
					+ selectedReports.get(i).getId());
		return selectedReports;
	}

	public void setSelectedReports(List<ReportTree> selectedReports) {

		this.selectedReports = selectedReports;
	}

	public String getGRSDbData(HeirarchyDataBean hdb1,String flagForEditdata) {		
		FacesContext context = FacesContext.getCurrentInstance();
		Map sessionMap = context.getExternalContext().getSessionMap();
		Map applicationMap = context.getExternalContext().getApplicationMap();
		HierarchyBean hier = (HierarchyBean) sessionMap.get("hierarchyBean");
		
		this.hierarchyId=hier.getHierarchy_ID();
		this.hierarchyXmlFileName=hier.getHierarchyXmlFileName();
		this.versionNo=hier.versionNo;
		System.out.println("hierarchyId---->>>> "
				+ hierarchyId + "hierarchyXmlFileName---->>>"
				+ hierarchyXmlFileName+"versionNo====>"+versionNo);
		
		if(this.versionNo.equals("") || this.versionNo.equalsIgnoreCase("Master"))
			getReportsForGenSession(hierarchyXmlFileName,hierarchyId,hdb1,flagForEditdata);
		else{
			this.hierarchyVersionXmlFileName = hier.hierLevelVersionXmlFileName;
			getReportsForGenSession(hierarchyVersionXmlFileName,hierarchyId,hdb1,flagForEditdata,this.versionNo);
		}

			System.out.println("Exiting: "
					+ new Exception().getStackTrace()[0].getClassName() + "."
					+ new Exception().getStackTrace()[0].getMethodName());


		return GRSDbData;
	}

	/**
	 * @param gRSDbData
	 *            the gRSDbData to set
	 */
	public void setGRSDbData(String gRSDbData) {
		GRSDbData = gRSDbData;
	}

	
	public void getReportsForGenSession(String hierarchyXmlFileName,String hierarchyId,HeirarchyDataBean hdb1,String flagForEditdata) {

		try {
			System.out.println("Entering: "
					+ new Exception().getStackTrace()[0].getClassName() + "."
					+ new Exception().getStackTrace()[0].getMethodName());

//			this.sessionID = this.GRSSessionID;
			getReportsForSession(hierarchyXmlFileName,hierarchyId,hdb1,flagForEditdata);

			System.out.println("Exiting: "
					+ new Exception().getStackTrace()[0].getClassName() + "."
					+ new Exception().getStackTrace()[0].getMethodName());

		} catch (Exception e) {
			// TODO Auto-generated catch block
			Globals.getErrorString(e);
		}
	}
	public void getReportsForGenSession(String hierarchyXmlFileName,String hierarchyId,HeirarchyDataBean hdb1,String flagForEditdata,String versionNo) {

		try {
			System.out.println("Entering: "
					+ new Exception().getStackTrace()[0].getClassName() + "."
					+ new Exception().getStackTrace()[0].getMethodName());

//			this.sessionID = this.GRSSessionID;
			getReportsForSession(hierarchyXmlFileName,hierarchyId,hdb1,flagForEditdata,versionNo);

			System.out.println("Exiting: "
					+ new Exception().getStackTrace()[0].getClassName() + "."
					+ new Exception().getStackTrace()[0].getMethodName());

		} catch (Exception e) {
			// TODO Auto-generated catch block
			Globals.getErrorString(e);
		}
	}

	
	//
	public void getReportsForSession(String xmlFileName, String hierID,HeirarchyDataBean hdb1,String flagForEditdata)
	
	{

		try {

			System.out.println("Entering: "
					+ new Exception().getStackTrace()[0].getClassName() + "."
					+ new Exception().getStackTrace()[0].getMethodName());

			
			boolean isgenrepsessionTree;

			repSession = new ArrayList();
			rootNodes = new ArrayList<TreeNode>();
if(flagForEditdata.equals("FromReferenceHierarchy")){ //code change Jayaramu 27MAR14
	reportTree1 = new ArrayList<ReportTree>();
	
}else if(flagForEditdata.equals("FromWriteSelCopyHIR")){
//	reportTree1 = new ArrayList<ReportTree>();
	reportTree = new ArrayList<ReportTree>();
}else {
	reportTree = new ArrayList<ReportTree>();
}
			
			
			
			// ReportTree ReportTree = new ReportTree();

//			factsCache = new HashMap<String, Fact>();
//			dimensionsCache = new HashMap<String, Dimension>();

			// ******* Get List of Sessions ************
			
			System.out.println("hierID,xmlFileName " +hierID+""+xmlFileName);
			
				repSession = new ReportsInvXMLParser(hierID,xmlFileName).getReportSessionsList(hierID,xmlFileName);	//code change vISHNU 18Dec2013
		

//			if (xmlFileType.equals("genReports")){
//				isgenrepsessionTree=true;				
//				repSession = new ReportsInvXMLParser(sessionID).getReportSessionsList();	//code change vISHNU 18Dec2013
//			}else{
//				isgenrepsessionTree=false;
////				repSession = new RunSessionsXmlParser(sessionID).getReportSessionsList();//code change vISHNU 18Dec2013
//				
//			}
//				
			System.out.println("List of Sessions (repSession): " + repSession);

			if (repSession == null) {

				System.out
						.println("getReportsForSession (List of Sessions) Status: "
								+ "Failure " + new Date());


			} else {

				// Process each Session....
				for (Report currRepSession : repSession) {

					System.out.println("Processing Session: "
 + currRepSession.getID());

				

					String deploymentFolder = "";	//code change Vishnu 20Dec2013
					Node node=null;
					PropUtil prop = new PropUtil();
					  HIERARCHY_XML_DIR = prop.getProperty("HIERARCHY_XML_DIR");	
					
//					xmlFileName = "HierachyLevel.xml";
					Document xmlDoc=Globals.openXMLFile(HIERARCHY_XML_DIR, xmlFileName);

					Node node1 = Globals.getNodeByAttrVal(xmlDoc, "Hierarchy_Level", "Hierarchy_ID", currRepSession.getID());
					if(Globals.getAttrVal4AttrName(node1, "Version").equals("")){
						node = Globals.getNodeByAttrVal(xmlDoc, "RootLevel", "ID",  currRepSession.getID());
					}else{
					
					NodeList hiersNdList = xmlDoc.getElementsByTagName("Hierarchy_Levels");
					Node hiersNd = hiersNdList.item(0);
					NodeList hierNdList = hiersNd.getChildNodes();
					for(int i=0;i<hierNdList.getLength();i++){
						if(hierNdList.item(i).getNodeType() == Node.ELEMENT_NODE && hierNdList.item(i).getNodeName().equals("Hierarchy_Level")
								&& Globals.getAttrVal4AttrName(hierNdList.item(i),"Hierarchy_ID").equals(hierID) && 
								Globals.getAttrVal4AttrName(hierNdList.item(i),"Version").equals("Master")){
							System.out.println("Processing Session:===> "
									 + Globals.getAttrVal4AttrName(hierNdList.item(i), "Version"));
							Node rootnd = hierNdList.item(i);
							System.out.println("Processing Session:===>1 "
									 + Globals.getAttributeNameandValHT(rootnd));
//							newND=Globals.getNodeByAttrValUnderParent(xmlDoc, rootnd, "Version", "1.3");
							NodeList rootNdList = rootnd.getChildNodes();
							for(int j=0;j<rootNdList.getLength();j++){
								if(rootNdList.item(j).getNodeType() == Node.ELEMENT_NODE && rootNdList.item(j).getNodeName().equals("RootLevel")){
									node = rootNdList.item(j);
								}
							}
						}
					}
					}
					getAllChildsInformation(currRepSession,  node,hdb1,flagForEditdata); 
					
					
					
					}

//				} // End of -- for (ReportSession currRepSession : repSession)

				System.out.println("Report List (rootsNodes): " + rootNodes);

				for (int i = 0; i < reportTree.size(); i++) {
					System.out.println("Report List (reportTree): "
							+ reportTree.get(i));
					System.out.println("Report List (reportTree) Children: "
							+ reportTree.get(i).getChildren());

				}

				if (reportTree == null) {
					System.out.println("getReportsForSession Status: "
							+ "Failure " + new Date());

				} else {

					System.out.println("getReportsForSession Status: "
							+ "Success " + new Date());

				}
			}

			System.out.println("Exiting: "
					+ new Exception().getStackTrace()[0].getClassName() + "."
					+ new Exception().getStackTrace()[0].getMethodName());

		} catch (Exception e) {
			// TODO Auto-generated catch block
			Globals.getErrorString(e);
		}
	}

public void getReportsForSession(String xmlFileName, String hierID,HeirarchyDataBean hdb1,String flagForEditdata,String versionNo)
	
	{

		try {

			System.out.println("Entering: "
					+ new Exception().getStackTrace()[0].getClassName() + "."
					+ new Exception().getStackTrace()[0].getMethodName());

			
			boolean isgenrepsessionTree;

			repSession = new ArrayList();
			rootNodes = new ArrayList<TreeNode>();

			reportTree1 = new ArrayList<ReportTree>();
			reportTree = new ArrayList<ReportTree>();
			
			// ReportTree ReportTree = new ReportTree();

//			factsCache = new HashMap<String, Fact>();
//			dimensionsCache = new HashMap<String, Dimension>();

			// ******* Get List of Sessions ************
			
			System.out.println("hierID,xmlFileName " +hierID+""+xmlFileName);
			
				repSession = new ReportsInvXMLParser(hierID,xmlFileName).getReportSessionsList1(hierID,xmlFileName,versionNo);	//code change vISHNU 18Dec2013
		

//			if (xmlFileType.equals("genReports")){
//				isgenrepsessionTree=true;				
//				repSession = new ReportsInvXMLParser(sessionID).getReportSessionsList();	//code change vISHNU 18Dec2013
//			}else{
//				isgenrepsessionTree=false;
////				repSession = new RunSessionsXmlParser(sessionID).getReportSessionsList();//code change vISHNU 18Dec2013
//				
//			}
//				
			System.out.println("List of Sessions (repSession): " + versionNo);

			if (repSession == null) {

				System.out
						.println("getReportsForSession (List of Sessions) Status: "
								+ "Failure " + new Date());


			} else {

				// Process each Session....
				for (Report currRepSession : repSession) {

					System.out.println("Processing Session: "
 + currRepSession.getID());

				

					String deploymentFolder = "";	//code change Vishnu 20Dec2013
					Node node=null;
					PropUtil prop = new PropUtil();
					  HIERARCHY_XML_DIR = prop.getProperty("HIERARCHY_XML_DIR");	
					  Node newND=null;
//					xmlFileName = "HierachyLevel.xml";
					Document xmlDoc=Globals.openXMLFile(HIERARCHY_XML_DIR, xmlFileName);
					NodeList hiersNdList = xmlDoc.getElementsByTagName("Hierarchy_Version_Levels");
					Node hiersNd = hiersNdList.item(0);
					NodeList hierNdList = hiersNd.getChildNodes();
					for(int i=0;i<hierNdList.getLength();i++){
						if(hierNdList.item(i).getNodeType() == Node.ELEMENT_NODE && hierNdList.item(i).getNodeName().equals("Hierarchy_Level")
								&& Globals.getAttrVal4AttrName(hierNdList.item(i),"Hierarchy_ID").equals(hierID) && 
								Globals.getAttrVal4AttrName(hierNdList.item(i),"Version").equals(versionNo)){
							System.out.println("Processing Session:===> "
									 + Globals.getAttrVal4AttrName(hierNdList.item(i), "Version"));
							Node rootnd = hierNdList.item(i);
							System.out.println("Processing Session:===>1 "
									 + Globals.getAttributeNameandValHT(rootnd));
//							newND=Globals.getNodeByAttrValUnderParent(xmlDoc, rootnd, "Version", "1.3");
							NodeList rootNdList = rootnd.getChildNodes();
							for(int j=0;j<rootNdList.getLength();j++){
								if(rootNdList.item(j).getNodeType() == Node.ELEMENT_NODE && rootNdList.item(j).getNodeName().equals("RootLevel")){
									node = rootNdList.item(j);
								}
							}
						}
					}
					

					getAllChildsInformation(currRepSession,  node,hdb1,flagForEditdata); 
					
					
					
					}

//				} // End of -- for (ReportSession currRepSession : repSession)

				System.out.println("Report List (rootsNodes): " + rootNodes);

				for (int i = 0; i < reportTree.size(); i++) {
					System.out.println("Report List (reportTree): "
							+ reportTree.get(i));
					System.out.println("Report List (reportTree) Children: "
							+ reportTree.get(i).getChildren());
					//Start code change Jayaramu 21APR14
					FacesContext ctx1 = FacesContext.getCurrentInstance();
					ExternalContext extContext1 = ctx1.getExternalContext();
					Map sessionMap1 = extContext1.getSessionMap();
					HierarchyBean hb = (HierarchyBean) sessionMap1.get("hierarchyBean");
					if(hb.getSelectedRows() != null && !hb.getSelectedRows().isEmpty()){
						if(hb.selectedRows.get(0).getID().equals(reportTree.get(i).getID())){
						hb.selectedRows.set(0, reportTree.get(i));
						}
					}
					//End code change Jayaramu 21APR14

				}

				if (reportTree == null) {
					System.out.println("getReportsForSession Status: "
							+ "Failure " + new Date());

				} else {

					System.out.println("getReportsForSession Status: "
							+ "Success " + new Date());

				}
			}

			System.out.println("Exiting: "
					+ new Exception().getStackTrace()[0].getClassName() + "."
					+ new Exception().getStackTrace()[0].getMethodName());

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Globals.getErrorString(e);
		}
	}

	public void selectionChanged(TreeSelectionChangeEvent selectionChangeEvent) {

		System.out.println("Entering: "
				+ new Exception().getStackTrace()[0].getClassName() + "."
				+ new Exception().getStackTrace()[0].getMethodName());

		// considering only single selection
		List<Object> selection = new ArrayList<Object>(
				selectionChangeEvent.getNewSelection());
		Object currentSelectionKey = selection.get(0);
		UITree tree = (UITree) selectionChangeEvent.getSource();
		Object storedKey = tree.getRowKey();
		tree.setRowKey(currentSelectionKey);
		currentSelection = (TreeNode) tree.getRowData();
		tree.setRowKey(storedKey);

		System.out.println("Exiting: "
				+ new Exception().getStackTrace()[0].getClassName() + "."
				+ new Exception().getStackTrace()[0].getMethodName());
	}

	

	static void aa(Node node)
	{

		System.out.println("Entering: " + new Exception().getStackTrace()[0].getClassName() + "."
												+ new Exception().getStackTrace()[0].getMethodName());

		try {

			if (node.getNodeType() == Node.ELEMENT_NODE) {

			NodeList nl = node.getChildNodes();
			String segNames[];
				// System.out.println("Reading HierarchyNode Length: " + nl.getLength());
			for (int i = 0; i < nl.getLength(); i++) {
					node = nl.item(i);
					if (node.getNodeType() == Node.ELEMENT_NODE) {



						ReportTree cTreeF = new ReportTree();

						System.out.println("------------>Reading HierarchyNode Name : " + node.getNodeName());
						System.out.println("------------->Adding Catalog  HierarchyNode Value : " + node.getTextContent());
						Hashtable NodeHT = new Hashtable();
						NodeHT = Globals.getAttributeNameandValHT(node);
						System.out.println("------------>NodeHT : " + NodeHT);
						aa(node);
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

	}
	public boolean imgDispFlag=false;
	
	
	
	public boolean isImgDispFlag() {
		return imgDispFlag;
	}



	public void setImgDispFlag(boolean imgDispFlag) {
		this.imgDispFlag = imgDispFlag;
	}



	public void processHierarchyNode(ReportTree cTreeParent, Node node, List<ReportTree> rt,HeirarchyDataBean hdb1,String flagForEditdata)
	{

		System.out.println("Entering: " + new Exception().getStackTrace()[0].getClassName() + "."
												+ new Exception().getStackTrace()[0].getMethodName());

		try {

			if (node.getNodeType() == Node.ELEMENT_NODE) {

			NodeList nl = node.getChildNodes();
			String segNames[];
				// System.out.println("Reading HierarchyNode Length: " + nl.getLength());
			for (int i = 0; i < nl.getLength(); i++) {
					node = nl.item(i);
					if (node.getNodeType() == Node.ELEMENT_NODE) {

						FacesContext ctx = FacesContext.getCurrentInstance();
						ExternalContext extContext = ctx.getExternalContext();
						Map sessionMap = extContext.getSessionMap();
						HierarchyBean hrb = (HierarchyBean) sessionMap.get("hierarchyBean");
						HierarchyDBBean hdb = (HierarchyDBBean) sessionMap.get("hierarchyDBBean");

						ReportTree cTreeF = new ReportTree();

						System.out.println("------------>Reading HierarchyNode Name : " + node.getNodeName());
						System.out.println("------------->Adding Catalog  HierarchyNode Value : " + node.getTextContent());
						// cTreeF = new ReportTree(node.getNodeName(), node.getNodeName(), node.getNodeName(), node.getNodeName(), rt, node
						// .getNodeName());
						Hashtable NodeHT = new Hashtable();
						NodeHT = Globals.getAttributeNameandValHT(node);
						String segValueWithCode = "";
						String segmentName = (String.valueOf(NodeHT.get("Segment")));
						System.out.println("------------>segmentName : " + segmentName);
						

						if(segmentName != null && !segmentName.equals("0") && !segmentName.equals("") && !segmentName.equals("null")){
							
							String segNo = "";
							String segPrimary= "";
							String segNo4RemoveDublicate = "";
							String segCode = "";
							String segValue = "";
							if(segmentName.contains("$~$")){
								
								segNames = segmentName.split("\\$~\\$");
								segPrimary = (String)NodeHT.get("Primary_Segment");
								String primarySegNo="";
								if(segPrimary!=null && !segPrimary.equals("")){
									primarySegNo= segPrimary.substring(segPrimary.indexOf("_")+1);
									int primeSegNumber = 0;
									if(!primarySegNo.equals("")){
										primeSegNumber = Integer.parseInt(primarySegNo);
										segNo4RemoveDublicate = primarySegNo;
										segCode = (String)NodeHT.get("seg"+primeSegNumber+"_Code");
										segValue = (String)NodeHT.get("seg"+primeSegNumber+"_value");
										
										
										String splitSegValue[] = segValue.split(";");
										String splitSegCode[] = segCode.split(";");
										for(int l=0;l<splitSegValue.length;l++){
											
											segValueWithCode = segValueWithCode+splitSegValue[l]+"("+splitSegCode[l]+");";
											
										}
										
										segValueWithCode = "["+segValueWithCode+"]";
									}	
												
								}
//												else{
//									primarySegNo=segNames[0].substring(segNames[0].indexOf("_")+1);
//								}
						
											
									
								
								
								for(int j=0;j<segNames.length;j++){
									
									System.out.println("primarySegNo===>>>"+primarySegNo+"j===>>>"+j);
									 segNo = segNames[j].substring(segNames[j].indexOf("_")+1);
									 
									 if(!segNo4RemoveDublicate.equals(segNo)){
									 segCode = (String)NodeHT.get("seg"+segNo+"_Code");
										segValue = (String)NodeHT.get("seg"+segNo+"_value");
										String splitSegValue[] = segValue.split(";");
										String splitSegCode[] = segCode.split(";");
										for(int k=0;k<splitSegValue.length;k++){
											
											segValueWithCode = segValueWithCode+splitSegValue[k]+"("+splitSegCode[k]+");";
											
										}
									 }
								}
								
								System.out.println("segValueWithCode===>>>"+segValueWithCode);
								
								NodeHT.put("value", segValueWithCode);
								
							}else{
								///////////////////????
//								if(String.valueOf(NodeHT.get("Segment")).contains("$~$")){
//								 segNo = segmentName.substring(segmentName.length()-1);
//								 
//								 String segCode = (String)NodeHT.get("seg"+segNo+"_Code");
//									String segValue = (String)NodeHT.get("seg"+segNo+"_value");
//									String splitSegValue[] = segValue.split(";");
//									String splitSegCode[] = segCode.split(";");
//									for(int k=0;k<splitSegValue.length;k++){
//										
//										segValueWithCode = segValueWithCode+splitSegValue[k]+"("+splitSegCode[k]+");";
//										
//									}
//								}else{
//									
//								}
								String valueCode = (String.valueOf(NodeHT.get("value")));
								
								String overwriteFlag = (String.valueOf(NodeHT.get("value")));
								
								if(hrb.getSelectedRows().size() <= 0){
									
									String segmentCode = valueCode.substring(valueCode.indexOf("(")+1, valueCode.indexOf(")"));
									String segmentValue = valueCode.substring(0,valueCode.indexOf("("));
//									String segmentName = (String.valueOf(NodeHT.get("Segment")));
									System.out.println("------------>segmentCode : " + segmentCode);
									System.out.println("------------>segmentValue : " + segmentValue);
									
//								if(segmentName.equals("0")){
//									System.out.println("------------>segmentName : " + segmentName);
//									hdb.setSegmentNameRendered("false");
//									
//								}else{
//									hdb.setSegmentNameRendered("true");
//								}
									System.out.println("------------>hrb.getSelectedRows() : " + segmentName);
									System.out.println("------------>hrb.segmentCode() : " + segmentCode);
									System.out.println("------------>hrb.segmentValue() : " + segmentValue);
			
								hdb1 = new HeirarchyDataBean(segmentName.replace("_", " "),segmentCode,segmentValue,"",null,"","",false,Boolean.valueOf(overwriteFlag),segmentName.replace("_", " "));
								if(hdb!=null)
								hdb.codeandNameAL.add(hdb1);
									
								}else if(NodeHT.get("ID").equals(hrb.getSelectedRows().get(0).getID())){
								
								String segmentCode = valueCode.substring(valueCode.indexOf("(")+1, valueCode.indexOf(")"));
								String segmentValue = valueCode.substring(0,valueCode.indexOf("("));
//								String segmentName = (String.valueOf(NodeHT.get("Segment")));
								System.out.println("------------>segmentCode : " + segmentCode);
								System.out.println("------------>segmentValue : " + segmentValue);
								
							hdb1 = new HeirarchyDataBean(segmentName.replace("_", " "),segmentCode,segmentValue,"",null,"","",false,Boolean.valueOf(overwriteFlag),segmentName.replace("_", " "));
							if(hdb!=null)
							hdb.codeandNameAL.add(hdb1);
								
							}
							}
							
							
//							NodeHT.put("value", segValueWithCode);
						}
						String failureMsg="";
						Boolean riConsVisible=false;
						if(String.valueOf(NodeHT.get("RI_Validation_Status"))!=null){
						if(hrb.rulesSize == 0){
							riConsVisible=false;
						}else{
							if(String.valueOf(NodeHT.get("RI_Validation_Status")).equalsIgnoreCase("Failure")){
								riConsVisible=true;
								failureMsg = String.valueOf(NodeHT.get("RI_Failure_Msg"));
							}else{
								riConsVisible=false;
								failureMsg = "";
							}
						}
						
						}else{
							riConsVisible=false;
							failureMsg = "";
							
						}
						
						System.out.println("------------>NodeHT : " + NodeHT);
						System.out.println("------------>NodeHT.get(value) : " + NodeHT.get("value"));

						// cTreeF = new ReportTree("11", (String) NodeHT.get("value"), "33", "44", rt, "55");
						cTreeF = new ReportTree((String) NodeHT.get("Type"), node.getNodeName(), (String) NodeHT.get("ID"), (String) NodeHT
																.get("value"),riConsVisible, failureMsg, rt);
						if (node != null && NodeHT.get("value") != null && !node.getNodeName().equals("RootLevel_Name")) {

							cTreeParent.getChildren().add(cTreeF);
						}

						processHierarchyNode(cTreeF, node, rt,hdb1,flagForEditdata);
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

	}
	
	private void getAllChildsInformation(Report curReport, Node node123,HeirarchyDataBean hdb1,String flagForEditdata) throws Exception {
try{
		System.out.println("Entering: "
				+ new Exception().getStackTrace()[0].getClassName() + "."
				+ new Exception().getStackTrace()[0].getMethodName());

		ReportTree rTreeF = new ReportTree();
		ReportTree rTreeFNA = new ReportTree();
		List<ReportTree> rt = new ArrayList<ReportTree>();
		

//		System.out.println("----node123getNodeName" + node123.getNodeName());
//		System.out.println("----getNodeValue" + node123.getNodeValue());
//
//		System.out.println("----node123getNodeName getRootLevel_Name " + curReport.getRootLevel_Name());
//		System.out.println("-----------getID" + curReport.getID());

		// rTreeFNA = new ReportTree("aa", curReport.getRootLevel_Name(), "cc", "dd", rt, "ee");
		String failureMsg = "";		//code change Vishnu 7Apr2014
		Boolean riConsVisible=false;
		if(Globals.getAttrVal4AttrName(node123, "RI_Validation_Status")!=null){
		if(Globals.getAttrVal4AttrName(node123, "RI_Validation_Status").equalsIgnoreCase("Failure")){
			riConsVisible=true;
			failureMsg = Globals.getAttrVal4AttrName(node123, "RI_Failure_Msg");
		}else{
			riConsVisible=false;
			failureMsg = "";
		}
		}else{
			riConsVisible=false;
			failureMsg = "";
			
		}
		rTreeFNA = new ReportTree("Rollup", "RootLevel", curReport.getID(), curReport.getRootLevel_Name(),riConsVisible, failureMsg, rt);
//		(String hierType, String levelNode, String ID, String levelValue,boolean riConsVisible,String RIFailureMsg, List<ReportTree> children)
		
		if(flagForEditdata.equals("FromReferenceHierarchy")){ //code change Jayaramu 27MAR14
			reportTree1.add(rTreeFNA);
			
		}else if(flagForEditdata.equals("FromWriteSelCopyHIR")){
//			reportTree1.add(rTreeFNA);
			reportTree.add(rTreeFNA);
		}else {
			reportTree.add(rTreeFNA);
		}
		
		
//		

		// rTreeD.getChildren().add(rTreeFNA); // Add this ReportTree Object in
		
		// ReportTree cTreeF = new ReportTree("ABC", "CD", "EFG", "123", rt, "AAA");
//		ReportTree cTreeF = new ReportTree("hai", rt);
		// rTreeFNA.getChildren().add(cTreeF);

		processHierarchyNode(rTreeFNA, node123, rt,hdb1,flagForEditdata);

		// rTreeFNA.getChildren().add(cTreeF);

		System.out.println("Exiting: "
				+ new Exception().getStackTrace()[0].getClassName() + "."
				+ new Exception().getStackTrace()[0].getMethodName());
}catch (Exception e) {

e.printStackTrace();
}
		// return fact;
	}

	
	public void setRepSession(List<Report> repSession) {
		this.repSession = repSession;
	}

	public List<TreeNode> getRootNodes() {
		System.out.println("Entering: "
				+ new Exception().getStackTrace()[0].getClassName() + "."
				+ new Exception().getStackTrace()[0].getMethodName());
		System.out.println("getRootNodes: " + rootNodes);
		return rootNodes;
	}

	public void setRootNodes(List<TreeNode> rootNodes) {
		System.out.println("Entering: "
				+ new Exception().getStackTrace()[0].getClassName() + "."
				+ new Exception().getStackTrace()[0].getMethodName());
		this.rootNodes = rootNodes;
	}

	public TreeNode getCurrentSelection() {
		return currentSelection;
	}

	public void setCurrentSelection(TreeNode currentSelection) {
		this.currentSelection = currentSelection;
	}

	public List<? extends TreeNode> getNodeChildren() {
		System.out.println("Entering: "
				+ new Exception().getStackTrace()[0].getClassName() + "."
				+ new Exception().getStackTrace()[0].getMethodName());
		ReportTree node = getNode();

		

		System.out.println("node: " + node);
		System.out.println("reportTree: " + reportTree);

		if (node != null) {
			//Start code change Jayaramu 21APR14
			for(int i=0;i<node.getChildren().size();i++){
				System.out.println("node.getChildren(): "
						+ node.getChildren().get(i).getID());
				
				FacesContext ctx1 = FacesContext.getCurrentInstance();
				ExternalContext extContext1 = ctx1.getExternalContext();
				Map sessionMap1 = extContext1.getSessionMap();
				HierarchyBean hb = (HierarchyBean) sessionMap1.get("hierarchyBean");
				if(hb.getSelectedRows() != null && !hb.getSelectedRows().isEmpty()){
					
					if(hb.selectedRows.get(0).getID().equals(node.getChildren().get(i).getID())){
						hb.selectedRows.set(0, node.getChildren().get(i));
					}else if(node.getLevelNode().equals("RootLevel")){  // code change Menaka 03MAY2014
						if(hb.selectedRows.get(0).getID().equals(node.getID())){
							hb.selectedRows.set(0,node);  
						}
					}
					
				}
			}
			//End code change Jayaramu 21APR14

			List<ReportTree> RTList = node.getChildren();

			java.util.ListIterator<ReportTree> it2 = RTList.listIterator();
			while (it2.hasNext()) {

				ReportTree RT = it2.next();

//				System.out.println("RT.getId(): " + RT.getId()
//						+ "; RT.getUnitTest(): " + RT.getUnitTest());

			}
		} // return reportTree;

		for (int i = 0; i < reportTree.size(); i++) {
			System.out.println("reportTree - Report ID: "
					+ reportTree.get(i).getId());
		}



		System.out.println("Exiting: "
				+ new Exception().getStackTrace()[0].getClassName() + "."
				+ new Exception().getStackTrace()[0].getMethodName());

		return node != null ? node.getChildren() : reportTree;
	}

	public boolean getNodeHasChildren() {
		System.out.println("Entering: "
				+ new Exception().getStackTrace()[0].getClassName() + "."
				+ new Exception().getStackTrace()[0].getMethodName());
		ReportTree node = getNode();
		System.out.println("!node.isLeaf(): " + !node.isLeaf());

		System.out.println("Exiting: "
				+ new Exception().getStackTrace()[0].getClassName() + "."
				+ new Exception().getStackTrace()[0].getMethodName());

		return !node.isLeaf();
	}

	
	
	public List<? extends TreeNode> getNodeChildren1() { //code change Jayaramu 27MAR14
		System.out.println("Entering: "
				+ new Exception().getStackTrace()[0].getClassName() + "."
				+ new Exception().getStackTrace()[0].getMethodName());
		ReportTree node = getNode();

		

		System.out.println("node: " + node);
		System.out.println("reportTree1: " + reportTree1);

		if (node != null) {
			System.out.println("node.getChildren(): "
					+ node.getChildren().indexOf(1));

			List<ReportTree> RTList = node.getChildren();

			java.util.ListIterator<ReportTree> it2 = RTList.listIterator();
			while (it2.hasNext()) {

				ReportTree RT = it2.next();

//				System.out.println("RT.getId(): " + RT.getId()
//						+ "; RT.getUnitTest(): " + RT.getUnitTest());

			}
		} // return reportTree1;

		for (int i = 0; i < reportTree1.size(); i++) {
			System.out.println("reportTree1 - Report ID: "
					+ reportTree1.get(i).getId());
		}



		System.out.println("Exiting: "
				+ new Exception().getStackTrace()[0].getClassName() + "."
				+ new Exception().getStackTrace()[0].getMethodName());

		return node != null ? node.getChildren() : reportTree1;
	}

	public boolean getNodeHasChildren1() {//code change Jayaramu 27MAR14
		System.out.println("Entering: "
				+ new Exception().getStackTrace()[0].getClassName() + "."
				+ new Exception().getStackTrace()[0].getMethodName());
		ReportTree node = getNode();
		System.out.println("!node.isLeaf(): " + !node.isLeaf());

		System.out.println("Exiting: "
				+ new Exception().getStackTrace()[0].getClassName() + "."
				+ new Exception().getStackTrace()[0].getMethodName());

		return !node.isLeaf();
	}
	
	
	private ReportTree getNode() {
		return Faces.var("node", ReportTree.class);
	}


	String meta_dir;
	
	public static void main(String[] args) throws Exception {

		

	}
	String cmgFromRI = ""; 
	
	public String getCmgFromRI() {
		return cmgFromRI;
	}
	public void setCmgFromRI(String cmgFromRI) {
		this.cmgFromRI = cmgFromRI;
	}



	public void getReferenceHierarchyNodeFromHIRLevelXml(String cmgFromRI){
		try{
			
			msg4RH=""; // code change Menaka 28MAR2014
			
			FacesContext ctx1 = FacesContext.getCurrentInstance();
			ExternalContext extContext1 = ctx1.getExternalContext();
			Map sessionMap1 = extContext1.getSessionMap();
			HierarchyBean hb = (HierarchyBean) sessionMap1.get("hierarchyBean");
			this.cmgFromRI = cmgFromRI;
			System.out.println("this.cmgFromRI====>>>>>"+this.cmgFromRI);
			if(cmgFromRI.equals("true")){
			selectedReferenceHierarchy = hb.getSelectedReferenceHierarchy();
			cpyNdPopupName = "Reference Hierarchy";
			if(selectedReferenceHierarchy.equals("selectValue") || selectedReferenceHierarchy.equals("")){
				System.out.println("Please select Reference Hierarchy to proceed further...");
				return;
			}
			}else{
				cpyNdPopupName = "Copy Hierarchy";
				System.out.println("Size of selected hierarchylist"+hb.hierarchyList.size());
//				if(hb.hierarchyList.size()>=1){
				HierarchydataBean hb1 = (HierarchydataBean) hb.hierarchyList.get(0);
				selectedReferenceHierarchy = hb1.hierarchyName;
//			}
			}
			
			
			PropUtil prop = new PropUtil();
			String dir = prop.getProperty("HIERARCHY_XML_DIR");
			String hierXmlFile = prop.getProperty("HIERARCHY_XML_FILE");
			Document HIRXmlDoc = Globals.openXMLFile(dir, hierXmlFile);
		NodeList hierLevel = HIRXmlDoc.getElementsByTagName("Hierarchy_Level");
		String hierarchyName = "";
		String hierarchyID = "";
		
		System.out.println("selectedReferenceHierarchy====>>>>>"+selectedReferenceHierarchy);
		
		for(int i=0;i<hierLevel.getLength();i++){
			
			Node hirLevelNode = hierLevel.item(i);
			
			if(hirLevelNode.getNodeType() == Node.ELEMENT_NODE){
				hierarchyName = hirLevelNode.getAttributes().getNamedItem("Hierarchy_Name").getNodeValue();
				if(selectedReferenceHierarchy.equals(hierarchyName)){
					hierarchyID = hirLevelNode.getAttributes().getNamedItem("Hierarchy_ID").getNodeValue();
					refHierarchyID = hierarchyID;
				}
				
				
			}
		}
		
		System.out.println("hierarchyName====>>>>>"+hierarchyName);
		System.out.println("hierarchyID====>>>>>"+hierarchyID);
		String hirID = hb.getHierarchy_ID();
		hb.setHierarchy_ID(hierarchyID);
		getReportsForSession(hierXmlFile, hierarchyID,null,"FromReferenceHierarchy");
		hb.setHierarchy_ID(hirID);
		
	}catch (Exception e) {
		// TODO: handle exception
		e.printStackTrace();
	}
	}
	String cpyNdPopupName = "";
	public String getCpyNdPopupName() {
		return cpyNdPopupName;
	}



	public void setCpyNdPopupName(String cpyNdPopupName) {
		this.cpyNdPopupName = cpyNdPopupName;
	}



	public void WriteSelectedRefHirchyToCurrentHry(String clickedButtonTreePopup, String cmgFromRI){
		try{
			
			FacesContext ctx1 = FacesContext.getCurrentInstance();
			ExternalContext extContext1 = ctx1.getExternalContext();
			Map sessionMap1 = extContext1.getSessionMap();
			HierarchyBean hb = (HierarchyBean) sessionMap1.get("hierarchyBean");
			String cmeFrom = "";
			if(cmgFromRI.equals("true")){
				cmeFrom="FromWriteSelRefHIR";
			}else if(cmgFromRI.equals("false")){
				cmeFrom="FromWriteSelCopyHIR";
			}
			PropUtil prop=new PropUtil();
			String XMLFileName=prop.getProperty("HIERARCHY_XML_FILE");
			Document doc=Globals.openXMLFile(HIERARCHY_XML_DIR, XMLFileName); 
			Node nd=Globals.getNodeByAttrVal(doc, "RootLevel", "ID", hb.getHierarchy_ID());   // code change Menaka 30APR2014
			
			if(hb.getSelectedRows().size()<=0){
			System.out.println("Please Select Node in Current hierarchy");	
			if(cmgFromRI.equalsIgnoreCase("true")){
				
				if(nd==null){
					msg4RH="Please create root node in dependent hierarchy";  // code change Menaka 30APR2014
					
				}else{
					msg4RH="Please select a node from dependent hierarchy";  // code change Menaka 28MAR2014
				
				}
				return;
			}else{
				
				if(nd==null){
					msg4RH="Please create root node in current hierarchy";  // code change Menaka 30APR2014
				}else{
					msg4RH="Please select a node from current hierarchy";  // code change Menaka 28MAR2014
				}
			
				return;
			}
				
			}else if(selectedRefHierarchyRows.size()<=0){
				if(cmgFromRI.equalsIgnoreCase("true")){
					System.out.println("Please Select Node in Reference hierarchy");
					msg4RH="Please select a node from reference hierarchy";  // code change Menaka 28MAR2014
					return;
				}else{
					System.out.println("Please Select Node in Reference hierarchy");
					msg4RH="Please select a node from selected hierarchy";  // code change Menaka 28MAR2014
					return;
				}
				
			}
			
			msg4RH="";// code change Menaka 28MAR2014
			
		
		String dir = prop.getProperty("HIERARCHY_XML_DIR");
		String hierXmlFile = prop.getProperty("HIERARCHY_XML_FILE");
		
		String levelID = hb.getSelectedRows().get(0).getID();
		String nodeName = hb.getSelectedRows().get(0).getLevelNode();
		
		Document HIRXmlDoc = Globals.openXMLFile(dir, hierXmlFile);
		Node addAppendNode = Globals.getNodeByAttrVal(HIRXmlDoc, nodeName, "ID", levelID);
		
		String refHierlevelID = selectedRefHierarchyRows.get(0).getID();
		String refHiernodeName = selectedRefHierarchyRows.get(0).getLevelNode();
		Node refHierNode = Globals.getNodeByAttrVal(HIRXmlDoc, refHiernodeName, "ID", refHierlevelID);
		Node copyOfRefHierNode = null;
		Node addedNode = null;
		if(clickedButtonTreePopup.equals("addWithChildNode")){
			copyOfRefHierNode = refHierNode.cloneNode(true);
			addAppendNode.appendChild(copyOfRefHierNode);
//			processallchiled(copyOfRefHierNode,dir,hierXmlFile,HIRXmlDoc);
			addedNode = addAppendNode.getFirstChild().getNextSibling();
			String selnodeName = addAppendNode.getNodeName();
			 if(selnodeName.equals("RootLevel")){
				 HIRXmlDoc.renameNode(copyOfRefHierNode,null, "Level_2"); 
	         }else{
			   String increLevel=addAppendNode.getNodeName().replace("Level_", ""); 
			   int increlevel=Integer.parseInt(increLevel);
			   int nextincre=increlevel+1;  
			HIRXmlDoc.renameNode(copyOfRefHierNode,null, "Level_"+nextincre);
	         }
			 Element elem = (Element)copyOfRefHierNode;
			 String NodeID = String.valueOf(Globals.getMaxID(dir, "Hierarchy_Config.xml", "Node_Level_ID", "ID"));
			   String uniqueid=String.valueOf(Globals.getMaxID(HIERARCHY_XML_DIR, "Hierarchy_Config.xml","Row_ID4Oracle", "ID"));
			   elem.setAttribute("ID",NodeID);
			   elem.setAttribute("Unique_ID",uniqueid);
			processChildNodes(1,copyOfRefHierNode,HIRXmlDoc,dir,NodeID,false);		
		
			Globals.writeXMLFile(HIRXmlDoc, dir, hierXmlFile);
			if(cmgFromRI.equalsIgnoreCase("true")){
			RIConstrainsBean ri = new RIConstrainsBean();
			String refHierID = "";
			Node DHHierNode = Globals.getNodeByAttrVal(HIRXmlDoc, "Hierarchy_Level", "Hierarchy_ID", hb.getHierarchy_ID());
			refHierID = Globals.getAttrVal4AttrName(DHHierNode, "RI_Reference_Hierarchy_ID");
			ri.implementationRI(hb.getHierarchy_ID(), NodeID, null);
			}
		}else if(clickedButtonTreePopup.equals("addNode")){
			
			String levelNodeName = selectedRefHierarchyRows.get(0).getLevelNode();
			String levelNodeID = selectedRefHierarchyRows.get(0).getID();
			Node levelNode = Globals.getNodeByAttrVal(HIRXmlDoc, levelNodeName,"ID",levelNodeID);
			String levelNodeType = levelNode.getAttributes().getNamedItem("Type").getNodeValue();
			
			hb.setType(levelNodeType);
			if(levelNodeType.equals("Rollup") || levelNodeType.equals("Hierarchy")){
				hb.setName(selectedRefHierarchyRows.get(0).getLevelName());
				if(cmgFromRI.equalsIgnoreCase("true")){
					hb.addHierarchyNodes("AddRefHier",false);
				}else{
					System.out.println("Entering: 1"+hb.getFlagNew());
					hb.setSelectedType("Child");
					hb.addHierarchyNodes(hb.getFlagNew(),false);
				}
			}
			
			if(levelNodeType.equals("Data")){
				String segNumber = levelNode.getAttributes().getNamedItem("Segment").getNodeValue();
				String levelCode = levelNode.getAttributes().getNamedItem("Code").getNodeValue();
				String levelValue = levelNode.getAttributes().getNamedItem("value").getNodeValue();
				String segmentID= String.valueOf(Globals.getMaxID(dir, "Hierarchy_Config.xml", "Segments_ID", "ID"));
				String codeCombination = levelNode.getAttributes().getNamedItem("Create_Code_Combination").getNodeValue();
				String codeCombination1 = codeCombination;
				String hierLevelID="";
				if(segNumber.equals("0")){
					levelValue = levelValue.substring(0,levelValue.indexOf("("));
					hb.setName(levelValue);
					hb.setCode(levelCode);
					hb.setSegmentNumber(segNumber);
					hb.setCodeCombinationflag4Lastnode(codeCombination1);
					hb.setSegmentID(segmentID);
					
					if(cmgFromRI.equalsIgnoreCase("true")){
					hb.addHierarchyNodes("AddRefHier",Boolean.valueOf(Globals.getAttrVal4AttrName(levelNode, "OverWrite_Code_Combination")));
					}else{
						System.out.println("Entering: 1"+hb.getFlagNew());
						hb.setSelectedType("Child");
						hb.addHierarchyNodes(hb.getFlagNew(),Boolean.valueOf(Globals.getAttrVal4AttrName(levelNode, "OverWrite_Code_Combination")));
					}
				}else{
					hierLevelID = String.valueOf(Globals.getMaxID(dir, "Hierarchy_Config.xml", "Node_Level_ID", "ID"));
					String uniqueid=String.valueOf(Globals.getMaxID(HIERARCHY_XML_DIR, "Hierarchy_Config.xml","Row_ID4Oracle", "ID"));
					copyOfRefHierNode = refHierNode.cloneNode(false);
					
					if(nodeName.equals("RootLevel")){
						HIRXmlDoc.renameNode(copyOfRefHierNode,null, "Level_2");	
					}
					else{
					String LevelNodename = nodeName.substring(nodeName.indexOf("_")+1,nodeName.length());
					int levelNo = Integer.parseInt(LevelNodename)+1;
					HIRXmlDoc.renameNode(copyOfRefHierNode,null, "Level_"+levelNo);
					}
					addAppendNode.appendChild(copyOfRefHierNode);
					addedNode = addAppendNode.getFirstChild().getNextSibling();
					Element refEle = (Element)copyOfRefHierNode;
					refEle.setAttribute("ID", hierLevelID);
					refEle.setAttribute("Segment_ID", segmentID);
					refEle.setAttribute("Unique_ID", uniqueid);
					Globals.writeXMLFile(HIRXmlDoc, dir, hierXmlFile);
				}if(cmgFromRI.equalsIgnoreCase("true")){
				RIConstrainsBean ri = new RIConstrainsBean();
				String refHierID = "";
				Node DHHierNode = Globals.getNodeByAttrVal(HIRXmlDoc, "Hierarchy_Level", "Hierarchy_ID", hb.getHierarchy_ID());
				refHierID = Globals.getAttrVal4AttrName(DHHierNode, "RI_Reference_Hierarchy_ID");
				ri.implementationRI(hb.getHierarchy_ID(), hierLevelID, addAppendNode);
				}
			}
		}
		if(cmgFromRI.equalsIgnoreCase("false")){
			hb.msg1 = "The node successfully added to the hierarchy.";
			hb.color4HierTreeMsg = "blue";
		}
		getReportsForSession(hierXmlFile, hb.getHierarchy_ID(),null,cmeFrom);
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public static int processChildNodes(int key,Node node,Document doc,String dir,String NodeID,boolean frmCopyHierarchy)
    {

     System.out.println("Entering: " + new Exception().getStackTrace()[0].getClassName() + "."
               + new Exception().getStackTrace()[0].getMethodName());

     try {
    	 String HIERARCHY_XML_DIR = "";
			
			PropUtil prop = new PropUtil();
			HIERARCHY_XML_DIR = prop.getProperty("HIERARCHY_XML_DIR");
      if (node.getNodeType() == Node.ELEMENT_NODE) {
         NodeList nl = node.getChildNodes();
//         System.out.println(key+"**************>Reading HierarchyNode Name : " + node.getNodeName()+" "+node.getAttributes().getNamedItem("ID"));
         
//        System.out.println("Reading HierarchyNode Length: " + nl.getLength());
      for (int i = 0; i < nl.getLength(); i++) {
        node = nl.item(i);
        if (node.getNodeType() == Node.ELEMENT_NODE) {
         Hashtable NodeHT = new Hashtable();
         NodeHT = Globals.getAttributeNameandValHT(node);
         String nodeName = node.getParentNode().getNodeName();
         String nodeName1 = node.getNodeName();
         if(nodeName1.equals("ID") || nodeName1.equals("RootLevel_Name")){
        	 if(!frmCopyHierarchy){
        	 node.getParentNode().removeChild(node);
        	 }else{
        		 Element ele = (Element) node;
        		 if(!nodeName1.equals("ID"))
        			 ele.setAttribute("ID", NodeID);
        		 else
        			 ele.setTextContent(NodeID);
        	 }
         }
         
         if(!nodeName1.equals("ID") && !nodeName1.equals("RootLevel_Name")){
         if(nodeName.equals("RootLevel")){
        	 doc.renameNode(node,null, "Level_2");
         }else{
         String increLevel=nodeName.replace("Level_", ""); 
		   int increlevel=Integer.parseInt(increLevel);
		   int nextincre=increlevel+1;  
		   System.out.println("Level_"+nextincre); 
		   doc.renameNode(node,null, "Level_"+nextincre);
         }
		   Element elem = (Element)node;
		   System.out.println("nodeName====>>>"+nodeName);
		   System.out.println("node====>>>"+node+"elem======>>"+elem);
		   String levelNodeType = elem.getAttribute("Type");
		   String hierLevelID ="";
		   if(key==1){
			   hierLevelID =NodeID;
		   }else{
			   hierLevelID = String.valueOf(Globals.getMaxID(dir, "Hierarchy_Config.xml", "Node_Level_ID", "ID"));
		   }
		
		   String uniqueid=String.valueOf(Globals.getMaxID(HIERARCHY_XML_DIR, "Hierarchy_Config.xml","Row_ID4Oracle", "ID"));
		   elem.setAttribute("ID",hierLevelID);
		   elem.setAttribute("Unique_ID",uniqueid);
		   if(levelNodeType.equals("Data")){
		   String segmentID= String.valueOf(Globals.getMaxID(dir, "Hierarchy_Config.xml", "Segments_ID", "ID"));
		   elem.setAttribute("Segment_ID",segmentID);
		   
		   }
         key++;
         }
        key= processChildNodes(key,node,doc,dir,NodeID,frmCopyHierarchy);
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
	
	public void deleteSelectedRefHierarchy(String treeDeleteFlag){
		try{
			
		PropUtil prop = new PropUtil();
		String hierXmlFile = prop.getProperty("HIERARCHY_XML_FILE");
		FacesContext ctx1 = FacesContext.getCurrentInstance();
		ExternalContext extContext1 = ctx1.getExternalContext();
		Map sessionMap1 = extContext1.getSessionMap();
		HierarchyBean hb = (HierarchyBean) sessionMap1.get("hierarchyBean");
		hb.deleteSelectedHRY(treeDeleteFlag,selectedRefHierarchyRows);
		String hierID;
		if(treeDeleteFlag.equals("FromRefHierTree")){
			hierID = refHierarchyID;
			getReportsForSession(hierXmlFile,hierID,null,"FromReferenceHierarchy");
		}else{
			hierID = hb.getHierarchy_ID();
			getReportsForSession(hierXmlFile,hierID,null,"FromHierTree");
		}
		
		}catch(Exception e) {
			// TODO: handle exception
		e.printStackTrace();
		}
	}	
	
	
	
	
	
	public void updateHierarchy(){ //code change Jayaramu 14APR14
		try{
			
			
			FacesContext ctx = FacesContext.getCurrentInstance();
			ExternalContext extContext = ctx.getExternalContext();
			Map sessionMap = extContext.getSessionMap();
			HierarchyDBBean hdb = (HierarchyDBBean) sessionMap.get("hierarchyDBBean");
			HierarchyBean hb = (HierarchyBean) sessionMap.get("hierarchyBean");
			if(hb.getSelectedRows().size()<1){
				hdb.setMsg4Editseg("Please select row in hierarchy tree to Edit");
				return;
			}
		PropUtil prop = new PropUtil();
		String hierXmlFile = prop.getProperty("HIERARCHY_XML_FILE");
		hdb.addTodatatable("EditMode");
//		getReportsForSession(hierXmlFile,hb.getHierarchy_ID(),null,"FromHierTree");
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	
	
	
//	public void redirect(String pageName,String isAddnewHry,String Hierarchy_ID,String HierarchyName,String hierarchyCreatedDate,String hierarchyModifiedDate,String hierarchyCategory,boolean hierCodeCombination,String hierarchyStatus){    // code change -- hierarchyStatus--- Menaka 15FEB2014 
//		System.out.println("Entering: "
//				+ new Exception().getStackTrace()[0].getClassName() + "."
//				+ new Exception().getStackTrace()[0].getMethodName());
//		
//		try{
////			loadData2TreeBean ldb = new loadData2TreeBean();
////			ldb.getReportsForSession(hierarchyXmlFileName, "runReports");
//			FacesContext ctx = FacesContext.getCurrentInstance();
//			ExternalContext extContext = ctx.getExternalContext();
//			Map sessionMap = extContext.getSessionMap();
//			HierarchyDBBean hdb = (HierarchyDBBean) sessionMap.get("hierarchyDBBean");
//			HierarchyBean hryb = (HierarchyBean) sessionMap.get("hierarchyDBBean");
//			HeaderBean heb = (HeaderBean) sessionMap.get("headerBean");
//			
//			hryb.setMsg("");
//			hryb.setMsg1("");
//			
//			LoginBean lgnbn = (LoginBean) sessionMap.get("loginBean");
//			PropUtil prop=new PropUtil();
//			String xmlDir = prop.getProperty("HIERARCHY_XML_DIR");
//			String xmlLevelFileName = prop.getProperty("HIERARCHY_XML_FILE");
//			
//			if(pageName.equals("Hierarchytree.xhtml")){
//				hryb.setFormName4OpenAddSegPopup("HRYTree");
//			}
//			
//			if(isAddnewHry.equals("addNewHrytrue"))
//			{
//				hryb.setEditHierarchy(false); // code change Menaka 14FEB2014
//				hryb.setFlagNew("newAdded");
//				hryb.setHierarchy_ID(String.valueOf(Globals.getMaxID(HIERARCHY_XML_DIR, hryb.getHierarchyConfigFile(),"Hierarchy_Level_ID", "ID")));
//				hryb.setRenderValue4SessionInfo("false");
//				hryb.setRenderValue4SessionInfo1("true");
//				hryb.setHierarchyName("");  // code change Menaka 12FEB2014
//				hryb.setHierarchyCategory(""); //  code change Menaka 21FEB2014
//				hryb.setStatus("Draft");
//				hryb.setHirename("");
//				hryb.setParentNodeName("");
//				hryb.setType("");
//				hryb.setCodecombinationFlag(false);
//				hryb.setDisableDataNHier(true);  // code change Menaka 20MAR2014
//				hryb.setVersionNo("Master");
//				hryb.setAuditDisable("true");
//				hryb.loadReferenceHierarchyFromHIRXML();
//				hryb.setAuditEnable("false");
//			}
//			else if(isAddnewHry.equals("addExistsHry"))
//			{
//				hryb.setEditHierarchy(true);   // code change Menaka 14FEB2014
//				hryb.setDisableDataNHier(false);  // code change Menaka 20MAR2014
//				hryb.setHierarchy_ID(Hierarchy_ID);
//				hryb.setRenderValue4SessionInfo("true");
//				hryb.setRenderValue4SessionInfo1("false");
//				hryb.setHierarchyCreatedDate(hierarchyCreatedDate);
//				hryb.setHierarchyModifiedDate(hierarchyModifiedDate);
//				hryb.setHierarchyName(HierarchyName);
//				hryb.setHirename(HierarchyName);//Code Change Jayaramu 22FEB14
//				hryb.setHrchyCategory(hierarchyCategory);
//				hryb.setStatus(hierarchyStatus);  // code change Menaka 15FEB2014
////				this.status= "Approved123";   //  Code change Menaka 13FEB2014 - need to remove the hard code - get status from XML
//				hryb.loadReferenceHierarchyFromHIRXML();
////				if(this.status.equals("Draft")){
////					this.statusColor= "blue";
////				}else if(this.status.equals("Approved")||this.status.equals("Published")){
////					this.statusColor= "green";
////				}else{
////					this.statusColor= "red";
////				}
//				hryb.getVersionNumber(Hierarchy_ID);
//				hryb.setCodecombinationFlag(hierCodeCombination);
//				hryb.setVersionNo("Master");
//				Document doc = Globals.openXMLFile(xmlDir, xmlLevelFileName);
//				Node nd = Globals.getNodeByAttrVal(doc, "Hierarchy_Level", "Hierarchy_ID", hryb.getHierarchy_ID());
//				if(!Globals.getAttrVal4AttrName(nd, "Disable_Toggle").equals("") && !Globals.getAttrVal4AttrName(nd, "Enable_Toggle").equals("")){
//					hryb.setAuditDisable(Globals.getAttrVal4AttrName(nd, "Disable_Toggle"));
//					hryb.setAuditEnable(Globals.getAttrVal4AttrName(nd, "Enable_Toggle"));
//				}else{
//					hryb.setAuditDisable("true");
//					hryb.setAuditEnable("false");
//				}
//			}
//			hryb.getRIContraintsRulesFromRICXml();
//			hryb.setHierarchyXmlFileName(hierarchyXmlFileName);
//			///////////////////////Start code change pandian 17MAR2014 
////			String hierID="1";
////			String hierarchyName="SG & A";
////			String LoginID="rbid";
////			String processStatus="";
////			String currentStageName="";
////			String currentStageNo="";
////			String totalNoStages="";
////			String currentStageStatus="";
////			String currentUserStatus="";
////			String currentUserEmailID="";
//if(pageName.equals("Hierarchytree.xhtml")){
//				
//	hryb.setStageMessageinFront(hryb.getHierarchy_ID());
//				}// end of if(pageName.equals("Hierarchytree.xhtml")){
//				///////////////////////End code change pandian 17MAR2014
//			
//			
//			
//			
//			System.out.println("hierarchyId------>>>>>"+hryb.getHierarchy_ID());
//			if((pageName.equals("Hierarchytree.xhtml") && isAddnewHry.equals("addExistsHry")) || isAddnewHry.equals("reload")){
//			getReportsForSession(hierarchyXmlFileName, hryb.getHierarchy_ID(), null, "");	
//		System.out.println("ENTER IN TEE LOAD------>>>>>");
//			}
//			
//		HeaderBean hb=new HeaderBean();
//		hb.processMenu(pageName);
//			
//		}catch(Exception e){
//			e.printStackTrace();
//			
//		}
//		
//		System.out.println("Exiting: "
//				+ new Exception().getStackTrace()[0].getClassName() + "."
//				+ new Exception().getStackTrace()[0].getMethodName());
//		}
//	
	
	
	
	
	
}
