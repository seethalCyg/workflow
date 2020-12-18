package scheduler;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import beans.genarateFactsBean;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import utils.Globals;
import utils.PropUtil;

public class EveryDayBackUp implements Job {

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		// TODO Auto-generated method stub
		System.out.println("Entering: " + new Exception().getStackTrace()[0].getClassName() + "."
				+ new Exception().getStackTrace()[0].getMethodName());
		try{
			PropUtil prop = new PropUtil();
			String xmlDir = "";
			String hierLevelFileName = "";
			xmlDir = prop.getProperty("HIERARCHY_XML_DIR");
			hierLevelFileName = prop.getProperty("HIERARCHY_XML_FILE");
			Document hierDoc = Globals.openXMLFile(xmlDir, hierLevelFileName);
			NodeList hierNdList = hierDoc.getElementsByTagName("Hierarchy_Levels");
			Node hierNd = hierNdList.item(0);
			NodeList HierList = hierNd.getChildNodes();
			boolean isApproved = false;
			String hierID = "";
			for(int i=0;i<HierList.getLength();i++){
				isApproved = false;
				hierID = "";
				if(HierList.item(i).getNodeType() == Node.ELEMENT_NODE && HierList.item(i).getNodeName().equals("Hierarchy_Level")){
					hierID = Globals.getAttrVal4AttrName(HierList.item(i), "Hierarchy_ID");
					Node hierChNd = HierList.item(i);
					NodeList hierChNdList = hierChNd.getChildNodes();
					for(int j=0;j<hierChNdList.getLength();j++){
						if(hierChNdList.item(j).getNodeType() == Node.ELEMENT_NODE){
							if(hierChNdList.item(j).getNodeName().equals("Workflow") && 
									Globals.getAttrVal4AttrName(hierChNdList.item(j), "Current_Stage_No").equalsIgnoreCase("Completed")){
								isApproved = true;
							}
							if(hierChNdList.item(j).getNodeName().equals("Fact_Tables") && 
									(Globals.getAttrVal4AttrName(hierChNdList.item(j), "Gen_Mode").equalsIgnoreCase("UpdateFact") || 
									Globals.getAttrVal4AttrName(hierChNdList.item(j), "Gen_Mode").equalsIgnoreCase("InsertFact"))){
								System.out.println("HIerID =>"+hierID);
								Thread t=new Thread(new genarateFactsBean(hierID,"","InsertFact"));
								t.start();//Code Change Gokul 21FEB2014
							}
						}
					}
				}
			}
//			Thread t=new Thread(new genarateFactsBean("328",""));
//			t.start();//Code Change Gokul 21FEB2014
			
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		System.out.println("Exiting: " + new Exception().getStackTrace()[0].getClassName() + "."
				+ new Exception().getStackTrace()[0].getMethodName());
	}

}
