
package rootLevel;


import java.util.List;
import java.util.Map;

import javax.faces.FacesException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import utils.Globals;
import utils.PropUtil;
import beans.Report;



@ManagedBean(name = "reportsInvXMLParser")
@SessionScoped
public class ReportsInvXMLParser {

	private List<Report> reportSessionsList;
	private String hierarchyXmlFileNm = "";
	public String getHierarchyXmlFileNm() {
		return hierarchyXmlFileNm;
	}


	public void setHierarchyXmlFileNm(String hierarchyXmlFileNm) {
		this.hierarchyXmlFileNm = hierarchyXmlFileNm;
	}


	private String HIERARCHY_XML_DIR;// = "E:\\Hierarchy Tool Backup\\Hierarchy Tool\\XML DATA\\";
	public String hierarchyID="";


	public String getHierarchyID() {
		return hierarchyID;
	}


	public void setHierarchyID(String hierarchyID) {
		this.hierarchyID = hierarchyID;
	}


	// Code Change -- Devan 22Mar13 -- Code change Comments
	//
	public ReportsInvXMLParser(String genhierarchyID,String hierarchyXmlFileNm) {	

		System.out.println("Entering: " + new Exception().getStackTrace()[0].getClassName() + "."
				+ new Exception().getStackTrace()[0].getMethodName());
		try {
			
//				FacesContext context = FacesContext.getCurrentInstance();
//				Map sessionMap = context.getExternalContext().getSessionMap();
//				Map requestMap = context.getExternalContext().getRequestMap();
				this.hierarchyID=genhierarchyID;
				this.hierarchyXmlFileNm=hierarchyXmlFileNm;
//				DiscoverMetadataBean dmb = (DiscoverMetadataBean) sessionMap.get("discoverMetadataBean");
//				hierarchyID=genhierarchyID;//code change vISHNU 18Dec2013
//				if (dmb == null) {
//					System.out
//							.println("Fatal Error: Application is not initialized correctly (DiscoverMetadataBean is NULL).");
//					return;
//				} else {
//
					hierarchyXmlFileNm = "HierachyLevel.xml";
					// hierarchyXmlFileNm = ("Report_Inv_" + dmb.getSubjectarea());
					PropUtil prop = new PropUtil();
					HIERARCHY_XML_DIR = prop.getProperty("HIERARCHY_XML_DIR");
//					HIERARCHY_XML_DIR = "E:\\Hierarchy Tool Backup\\Hierarchy Tool\\XML DATA\\";
					System.out.println("Setting Field: hierarchyXmlFileNm: " + hierarchyXmlFileNm);
					System.out.println("Setting Field: HIERARCHY_XML_DIR: " + HIERARCHY_XML_DIR);
//				}
			

		} catch (Exception e) {
			Globals.getErrorString(e);

		}
		System.out.println("Exiting: " + new Exception().getStackTrace()[0].getClassName() + "."
				+ new Exception().getStackTrace()[0].getMethodName());
	}


	@XmlRootElement(name = "Hierarchy_Level")
	// start code change vISHNU 18Dec2013
	private static final class RptsInvHolder {

		private List<Report> reports;

		// /////////////////////////////////////
		private List<Report> RootLevel;
		private String Hierarchy_ID;

		@XmlElement(name = "RootLevel")
		public List<Report> getRootLevel() {
			return RootLevel;
		}

		public void setRootLevel(List<Report> rootLevel) {
			this.RootLevel = rootLevel;
		}

		@XmlAttribute(name = "Hierarchy_ID")
		public String getHierarchy_ID() {
			return Hierarchy_ID;
		}

		public void setHierarchy_ID(String hierarchy_ID) {
			this.Hierarchy_ID = hierarchy_ID;
		}


		
	}// static inner class


	public synchronized List<Report> getReportSessionsList(String genhierarchyID,String hierarchyXmlFileNm) {

		System.out.println("Entering: " + new Exception().getStackTrace()[0].getClassName() + "."
				+ new Exception().getStackTrace()[0].getMethodName());

		try {

			if (reportSessionsList == null) {

//				hierarchyXmlFileNm = "HierachyLevel.xml";
				this.hierarchyID=genhierarchyID;
				this.hierarchyXmlFileNm=hierarchyXmlFileNm;
				
				PropUtil prop = new PropUtil();
				  HIERARCHY_XML_DIR = prop.getProperty("HIERARCHY_XML_DIR");

				if (Globals.isFileExists(HIERARCHY_XML_DIR, hierarchyXmlFileNm)) {
					Document doc=Globals.openXMLFile(HIERARCHY_XML_DIR, hierarchyXmlFileNm);
					Node nd = Globals.getNodeByAttrVal(doc, "Hierarchy_Level", "Hierarchy_ID", hierarchyID);
					NodeList ndlist=doc.getElementsByTagName("Hierarchy_Levels");
					Node nd1=ndlist.item(0);
					NodeList ndlist1 = nd1.getChildNodes();
//					Node nd=null;
					if(Globals.getAttrVal4AttrName(nd, "Version").equals("")){
						
					}else{
					for(int i=0;i<ndlist1.getLength();i++){
						if(ndlist1.item(i).getNodeType() == Node.ELEMENT_NODE && ndlist1.item(i).getNodeName().equals("Hierarchy_Level")
								&& Globals.getAttrVal4AttrName(ndlist1.item(i),"Hierarchy_ID").equals(hierarchyID) && 
								Globals.getAttrVal4AttrName(ndlist1.item(i),"Version").equals("Master")){
							nd = ndlist1.item(i);
						}
							
						}
					
					}
					System.out.println("Globals.getAttrVal4AttrName(" + Globals.getAttrVal4AttrName(nd, "Version"));
					if(nd!=null){
						System.out.println("Processing Reports Inv File: " + HIERARCHY_XML_DIR + hierarchyXmlFileNm);
						System.out.println("Processing hierarchyID: " + hierarchyID);
//						InputStream is = new FileInputStream(HIERARCHY_XML_DIR + hierarchyXmlFileNm);
						JAXBContext jc = JAXBContext.newInstance(RptsInvHolder.class);
						Unmarshaller u = jc.createUnmarshaller();
						RptsInvHolder rptsInvHolder = (RptsInvHolder) u.unmarshal(nd);
						// reportSessionsList = rptsInvHolder.getReport();
						reportSessionsList = rptsInvHolder.getRootLevel();
					}
					
				} else {
					System.out.println("Reports Inventory File " + hierarchyXmlFileNm
							+ " does not exist / not created yet. Returning");
				}

			}
		} catch (Exception e) {
			Globals.getErrorString(e);

		}
		System.out.println("Exiting: " + new Exception().getStackTrace()[0].getClassName() + "."
				+ new Exception().getStackTrace()[0].getMethodName());
		return reportSessionsList;
	}

	public synchronized List<Report> getReportSessionsList1(String genhierarchyID,String hierarchyXmlFileNm,String versionNo) {

		System.out.println("Entering: " + new Exception().getStackTrace()[0].getClassName() + "."
				+ new Exception().getStackTrace()[0].getMethodName());

		try {

			if (reportSessionsList == null) {

//				hierarchyXmlFileNm = "HierachyLevel.xml";
				this.hierarchyID=genhierarchyID;
				this.hierarchyXmlFileNm=hierarchyXmlFileNm;
				
				PropUtil prop = new PropUtil();
				  HIERARCHY_XML_DIR = prop.getProperty("HIERARCHY_XML_DIR");

				if (Globals.isFileExists(HIERARCHY_XML_DIR, hierarchyXmlFileNm)) {
					Document doc=Globals.openXMLFile(HIERARCHY_XML_DIR, hierarchyXmlFileNm);
					NodeList ndlist=doc.getElementsByTagName("Hierarchy_Version_Levels");
					Node nd1=ndlist.item(0);
					NodeList ndlist1 = nd1.getChildNodes();
					Node nd=null;
					for(int i=0;i<ndlist1.getLength();i++){
						if(ndlist1.item(i).getNodeType() == Node.ELEMENT_NODE && ndlist1.item(i).getNodeName().equals("Hierarchy_Level")
								&& Globals.getAttrVal4AttrName(ndlist1.item(i),"Hierarchy_ID").equals(hierarchyID) && 
								Globals.getAttrVal4AttrName(ndlist1.item(i),"Version").equals(versionNo)){
							nd = ndlist1.item(i);
						}
						
					}
					
					System.out.println("Globals.getAttrVal4AttrName(" + Globals.getAttrVal4AttrName(nd, "Version"));
					if(nd!=null){
						System.out.println("Processing Reports Inv File: " + HIERARCHY_XML_DIR + hierarchyXmlFileNm);
						System.out.println("Processing hierarchyID: " + hierarchyID);
//						InputStream is = new FileInputStream(HIERARCHY_XML_DIR + hierarchyXmlFileNm);
						JAXBContext jc = JAXBContext.newInstance(RptsInvHolder.class);
						Unmarshaller u = jc.createUnmarshaller();
						RptsInvHolder rptsInvHolder = (RptsInvHolder) u.unmarshal(nd);
						// reportSessionsList = rptsInvHolder.getReport();
						reportSessionsList = rptsInvHolder.getRootLevel();
					}
					
				} else {
					System.out.println("Reports Inventory File " + hierarchyXmlFileNm
							+ " does not exist / not created yet. Returning");
				}

			}
		} catch (Exception e) {
			Globals.getErrorString(e);

		}
		System.out.println("Exiting: " + new Exception().getStackTrace()[0].getClassName() + "."
				+ new Exception().getStackTrace()[0].getMethodName());
		return reportSessionsList;
	}
	public static void main(String[] args) {

//		List<ReportSession> reports;
//
//		try {
//			InputStream is = new FileInputStream(
//					"C:\\RBID BACKUP\\rbid\\BI Metadata\\Report_Inv_Sample Targets Lite_270_306.xml");
//			JAXBContext jc = JAXBContext.newInstance(RptsInvHolder.class);
//			Unmarshaller u = jc.createUnmarshaller();
//			RptsInvHolder rptsInvHolder = (RptsInvHolder) u.unmarshal(is);
//
//			System.out.println("Loading reports...");
//			reports = rptsInvHolder.getReports();
//
//			ListIterator<ReportSession> itr = reports.listIterator();
//
//			while (itr.hasNext()) {
//				ReportSession cReports = itr.next();
//
//				System.out.println("gethierarchyID(): " + cReports.gethierarchyID());
//				System.out.println("getReportName: " + cReports.getReport().get(2).getReportName());
//			}

			//for (String current : reports(0)...getReports())
			//		System.out.println("reports " + reports.getReport().getReportName());

//		} catch (Exception e) {
//			throw new FacesException("In getReportsInvList: " + e.getMessage());
//		}

	}

}
