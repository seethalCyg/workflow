package rootLevel;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;
import java.util.Map;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import beans.HierarchyBean;
import beans.HierarchyDBBean;
import beans.LoginBean;
import beans.RIConstrainsBean;
import beans.genarateFactsBean;

import utils.Globals;
import utils.PropUtil;

public class dataProcess {

	/**
	 * @param args
	 */
	
	private static final boolean UPDATE_DB_FOR_EACH_NODE = false;
	public static String nodeNavigationUpandDown(String currentnodeName4Navi,String currentnoderefAttri,String currentnodeId,String navigationType,
			String xmlfilePath,String xmlFileName,String hierID,String LoginID)
	{//Node Navigation UP and DOWN
		
		 System.out.println("Entering: " + new Exception().getStackTrace()[0].getClassName() + "."
	               + new Exception().getStackTrace()[0].getMethodName());

		
		String nodeValidator="Valid";
		try{
			PropUtil prop=new PropUtil();
		Document doc=Globals.openXMLFile(xmlfilePath,xmlFileName);
		Node currentNode=Globals.getNodeByAttrVal(doc, currentnodeName4Navi,currentnoderefAttri, currentnodeId);
		Node previousNode=null;
		Node nextNode=null;
		Node nextNodeinDB=null;

		FacesContext ctx = FacesContext.getCurrentInstance();  // Code change Menaka 10MAR2014
		ExternalContext extContext = ctx.getExternalContext();
		Map sessionMap = extContext.getSessionMap();
		HierarchyBean hryb = (HierarchyBean) sessionMap.get("hierarchyBean");
		Connection conn1 = Globals.getDBConnection("Data_Connection");
		Node parNode=currentNode.getParentNode();
Node rootNd = Globals.getNodeByAttrVal(doc, "Hierarchy_Level", "Hierarchy_ID", hryb.getHierarchy_ID());
		
		if(!Globals.getAttrVal4AttrName(rootNd, "RI_Dependent_Hierarchies").equals("")){
			hryb.setRiMessage("This Reference Hierarchy has Dependant Hierarchies. Do you want to Validate them now?");
			hryb.setRhContainsDH(true);
		}
		if(navigationType.equalsIgnoreCase("UP"))
		{
		previousNode=currentNode.getPreviousSibling();
		if(previousNode.getNodeType()!=Node.ELEMENT_NODE)
		{
			previousNode=previousNode.getPreviousSibling();
			String nodeName="";
			if(previousNode!=null){
				nodeName=previousNode.getNodeName();
			}
		
			
			if(previousNode==null||nodeName.equalsIgnoreCase("RootLevel_Name")||nodeName.equalsIgnoreCase("RootLevel")||nodeName.equalsIgnoreCase("Hierarchy_Level")||nodeName.equalsIgnoreCase("Hierarchy_Levels"))
			{
				nodeValidator="Invalid";
				hryb.setMsg1("Not possible to move UP");   // Code change Menaka 10MAR2014
				return nodeValidator;
			}else{
				
				//previousNode moved node
				//currentNode 
				
			   Hashtable currentNodeHT = new Hashtable();
			   currentNodeHT = Globals.getAttributeNameandValHT(currentNode);
			   Hashtable movedNodeHT = new Hashtable();
			   movedNodeHT = Globals.getAttributeNameandValHT(previousNode);
			   
			   String currentNodeId=(String)currentNodeHT.get("ID");
			   String currentNodeName=currentNode.getNodeName();
			   			   
			   String movedNodeID=(String)movedNodeHT.get("ID");
			   String movedNodeName=previousNode.getNodeName();
			   
			   swapDBData(currentNodeId,movedNodeID,currentNodeName,movedNodeName,"UP", doc);
			   Hashtable auditHT=new Hashtable<>();
			   auditHT.put(9, Globals.getAttrVal4AttrName(parNode, "ID"));
				auditHT.put(10, Globals.getAttrVal4AttrName(parNode, "value"));
				auditHT.put(11, hryb.test(parNode, Globals.getAttrVal4AttrName(parNode, "ID"), currentNode, Globals.getAttrVal4AttrName(currentNode, "ID"), doc));
			parNode.insertBefore(currentNode, previousNode);
			
			
			
			
//			HierarchyBean hdb = (HierarchyBean) sessionMap.get("hierarchyBean");
			
			Hashtable auditcolumnNameHT=Globals.auditColumnName();
			Hashtable auditcoldetHT=new Hashtable<>();
			Hashtable auditcoldetHTtemp=new Hashtable<>();
			genarateFactsBean gfb = new genarateFactsBean("","","");
			Date HIERYDate = new Date();
			String dateFormat = prop.getProperty("DATE_FORMAT");
			DateFormat sdf1 = new SimpleDateFormat(dateFormat);
			String StartDate = sdf1.format(HIERYDate);
			for(int i=0;i<auditcolumnNameHT.size();i++){
				auditcoldetHTtemp=gfb.getDatatypefromDBtable("HIERARCHY_AUDIT."+String.valueOf(auditcolumnNameHT.get(i)),conn1,"","","");
				auditcoldetHT.put(i, auditcoldetHTtemp);
			}
			
			
			auditHT.put(0, LoginID);
			auditHT.put(1, StartDate);
			auditHT.put(2, hierID);
			auditHT.put(3, "Move");
			
			auditHT.put(4, Globals.getAttrVal4AttrName(parNode, "ID"));
			auditHT.put(5, Globals.getAttrVal4AttrName(parNode, "value"));
			auditHT.put(6, Globals.getAttrVal4AttrName(currentNode, "ID"));
			
			
			Hashtable beforHT=Globals.getAttributeNameandValHT(currentNode);

			String nodeText=hryb.getConcatedString4AttrHT(beforHT);
			auditHT.put(7, nodeText);
			auditHT.put(8, nodeText);
			
			
			
			auditHT.put(12, Globals.getAttrVal4AttrName(parNode, "ID"));
			auditHT.put(13, Globals.getAttrVal4AttrName(parNode, "value"));
			auditHT.put(14, hryb.test(parNode, Globals.getAttrVal4AttrName(parNode, "ID"), currentNode, Globals.getAttrVal4AttrName(currentNode, "ID"), doc));
			hryb.logChanges("Move",auditHT,auditcoldetHT,conn1);
			System.out.println("Node moved UP Successfully");
			/////////////////
			}
			
		}
		}else if(navigationType.equalsIgnoreCase("DOWN")){
			
			try{
			nextNode=currentNode.getNextSibling().getNextSibling().getNextSibling().getNextSibling();
			nextNodeinDB=currentNode.getNextSibling().getNextSibling();
			}catch(Exception e)
			{
				nodeValidator="Invalid";
	hryb.setMsg1("Not possible to move DOWN");   // Code change Menaka 10MAR2014
				
				return nodeValidator;
		
			}
			
			if(nextNodeinDB==null){
				
				nodeValidator="Invalid";
				hryb.setMsg1("Not possible to move DOWN");   // Code change Menaka 10MAR2014
				
				return nodeValidator;
			}
			

			// nextNodeinDB moved node			
			// currentNode

			   Hashtable currentNodeHT = new Hashtable();
			   currentNodeHT = Globals.getAttributeNameandValHT(currentNode);
			   Hashtable movedNodeHT = new Hashtable();
			   movedNodeHT = Globals.getAttributeNameandValHT(nextNodeinDB);
			   
			   String currentNodeId=(String)currentNodeHT.get("ID");
			   String currentNodeName=currentNode.getNodeName();
			   			   
			   String movedNodeID=(String)movedNodeHT.get("ID");
			   String movedNodeName=nextNodeinDB.getNodeName();
			   
			   swapDBData(currentNodeId,movedNodeID,currentNodeName,movedNodeName,"DOWN", doc);

			   Hashtable auditHT=new Hashtable<>();
			   auditHT.put(9, Globals.getAttrVal4AttrName(parNode, "ID"));
				auditHT.put(10, Globals.getAttrVal4AttrName(parNode, "value"));
				auditHT.put(11, hryb.test(parNode, Globals.getAttrVal4AttrName(parNode, "ID"), currentNode, Globals.getAttrVal4AttrName(currentNode, "ID"), doc));
			   
			if(nextNode==null)
			{
				parNode.appendChild(currentNode);
			}else
			{
				parNode.insertBefore(currentNode, nextNode);
			}
			////////////////		
			
			Hashtable auditcolumnNameHT=Globals.auditColumnName();
			Hashtable auditcoldetHT=new Hashtable<>();
			Hashtable auditcoldetHTtemp=new Hashtable<>();
			genarateFactsBean gfb = new genarateFactsBean("","","");
			Date HIERYDate = new Date();
			String dateFormat = prop.getProperty("DATE_FORMAT");
			DateFormat sdf1 = new SimpleDateFormat(dateFormat);
			String StartDate = sdf1.format(HIERYDate);
			for(int i=0;i<auditcolumnNameHT.size();i++){
				auditcoldetHTtemp=gfb.getDatatypefromDBtable("HIERARCHY_AUDIT."+String.valueOf(auditcolumnNameHT.get(i)),conn1,"","","");
				auditcoldetHT.put(i, auditcoldetHTtemp);
			}
			
			
			auditHT.put(0, LoginID);
			auditHT.put(1, StartDate);
			auditHT.put(2, hierID);
			auditHT.put(3, "Move");
			
			auditHT.put(4, Globals.getAttrVal4AttrName(parNode, "ID"));
			auditHT.put(5, Globals.getAttrVal4AttrName(parNode, "value"));
			auditHT.put(6, Globals.getAttrVal4AttrName(currentNode, "ID"));
			
			
			Hashtable beforHT=Globals.getAttributeNameandValHT(currentNode);

			String nodeText=hryb.getConcatedString4AttrHT(beforHT);
			auditHT.put(7, nodeText);
			auditHT.put(8, nodeText);
			
			
			
			auditHT.put(12, Globals.getAttrVal4AttrName(parNode, "ID"));
			auditHT.put(13, Globals.getAttrVal4AttrName(parNode, "value"));
			auditHT.put(14, hryb.test(parNode, Globals.getAttrVal4AttrName(parNode, "ID"), currentNode, Globals.getAttrVal4AttrName(currentNode, "ID"), doc));
			hryb.logChanges("Move",auditHT,auditcoldetHT,conn1);
			System.out.println("Node moved DOWN Successfully");
		}
	
		
		System.out.println("Node Validation :"+nodeValidator);
		if(!nodeValidator.equalsIgnoreCase("Invalid"))
		{
		Globals.writeXMLFile(doc,xmlfilePath,xmlFileName);
		}
		
		
		Node hierarchyNode=Globals.getNodeByAttrVal(doc, "Hierarchy_Level","Hierarchy_ID", hierID);
		if(hierarchyNode != null){
			Node hierarchyTypeNode = hierarchyNode.getAttributes().getNamedItem("RI_Hierarchy_Type");
			if(hierarchyTypeNode != null){
				String hierarchyType = hierarchyTypeNode.getNodeValue();
				if(hierarchyType.equals("Reporting") || hierarchyType.equals("Dependent")){
					RIConstrainsBean ri = new RIConstrainsBean();
					ri.implementationRI(hierID,currentnodeId,currentNode);
		}
				}
			}
		
		
		
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return nodeValidator;
		
	}

	public static void swapDBData(String currentNodeId,String movedNodeID,String currentNodeName,String movedNodeName,String navigationType,Document doc){

		
		try{
			
			
		
		
//		String navigationType="UP";
//		String navigationType="DOWN";
		////////up
//		String nodeId="524";
//		String prenodeId="535";
////////down
		String nodeId=currentNodeId;
		String prenodeId=movedNodeID;
		String nodeName4Navi=currentNodeName;
		String prenodeName4Navi=movedNodeName;
//		String xmlfilePath="E:\\Hierarchy Project\\Hierarchy\\XML DATA\\";
//		String xmlFileName="HierachyLevel.xml";
//		String noderefAttri="ID";
//		Document doc=Globals.openXMLFile(xmlfilePath,xmlFileName);
		

		
		Connection conn1 = Globals.getDBConnection("Data_Connection");
		
		
		Node currentNode=Globals.getNodeByAttrVal(doc, nodeName4Navi,"ID", nodeId);
		Node movedNode=Globals.getNodeByAttrVal(doc, prenodeName4Navi,"ID", prenodeId);
		//////////////up
//		Hashtable CurrentMovingNodeIDSHT=gettingAllchildAttributesFrmXml(currentNode,"MoveUpNDown");
//		System.out.println("CurrentMovingNodeIDSHT"+CurrentMovingNodeIDSHT);
//	
//		Hashtable MovedNodeIDSHT=gettingAllchildAttributesFrmXml(movedNode,"MoveUpNDown");
//		System.out.println("MovedNodeIDSHT"+MovedNodeIDSHT);
		
		////////////down
		Hashtable CurrentMovingNodeIDSHT=gettingAllchildAttributesFrmXml(currentNode,"MoveUpNDown");
//		System.out.println("CurrentMovingNodeIDSHT"+CurrentMovingNodeIDSHT);
	
		Hashtable MovedNodeIDSHT=gettingAllchildAttributesFrmXml(movedNode,"MoveUpNDown");
//		System.out.println("MovedNodeIDSHT"+MovedNodeIDSHT);
		

		Hashtable CurrentMovingNodeROWSHT=gettingRow_WidfrmDB(CurrentMovingNodeIDSHT,conn1);
		Hashtable MovedNodeROWSHT=gettingRow_WidfrmDB(MovedNodeIDSHT,conn1);		
		
		System.out.println("CurrentMovingNodeIDSHT--->"+CurrentMovingNodeIDSHT);
		System.out.println("CurrentMovingNodeROWSHT--->"+CurrentMovingNodeROWSHT);
		System.out.println("");
		System.out.println("MovedNodeIDSHT--->"+MovedNodeIDSHT);
		System.out.println("MovedNodeROWSHT--->"+MovedNodeROWSHT);
		

		if(UPDATE_DB_FOR_EACH_NODE)
		 updateDBtable4RowsUPDOWN(CurrentMovingNodeROWSHT,CurrentMovingNodeIDSHT,
				MovedNodeIDSHT,MovedNodeROWSHT,conn1,navigationType);
//		
		
		
		/////////////////////
		 
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
	}
	
	public static void updateDBtable4RowsUPDOWN(Hashtable CurrentMovingNodeROWSHT,Hashtable CurrentMovingNodeIDSHT,
			Hashtable MovedNodeIDSHT,Hashtable MovedNodeROWSHT,Connection conn1,String navigationType){
		
		try{
			
			int mvngsize=CurrentMovingNodeROWSHT.size();
			int mvedsize=MovedNodeROWSHT.size();
			
//			System.out.println(mvedsize+"SQL====> "+mvngsize);
			
			if(mvngsize==mvedsize){
				
				for(int i=0;i<mvedsize;i++){
					String mvedIDs=String.valueOf(MovedNodeIDSHT.get(i));  //1010  1011
					String mvedrows=String.valueOf(MovedNodeROWSHT.get(i));  // 6  7
					
					String mvngIDs=String.valueOf(CurrentMovingNodeIDSHT.get(i)); //1001   1002
					String mvngrows=String.valueOf(CurrentMovingNodeROWSHT.get(i));    //3     4
					
//					String sql1="UPDATE WC_FLEX_HIERARCHY_D SET ROW_WID="+mvedrows+" WHERE XML_NODE_ID="+mvngIDs+" AND ROW_WID="+mvngrows+"";
//					
//					String sql2="UPDATE WC_FLEX_HIERARCHY_D SET ROW_WID="+mvngrows+" WHERE XML_NODE_ID="+mvedIDs+" AND ROW_WID="+mvedrows+"";
					String sql1="UPDATE WC_FLEX_HIERARCHY_D SET SORT_ORDER="+mvedrows+" WHERE XML_NODE_ID="+mvngIDs+" AND SORT_ORDER="+mvngrows+"";
					
					String sql2="UPDATE WC_FLEX_HIERARCHY_D SET SORT_ORDER="+mvngrows+" WHERE XML_NODE_ID="+mvedIDs+" AND SORT_ORDER="+mvedrows+"";
					
					PreparedStatement pre1=conn1.prepareStatement(sql1);
					pre1.execute();
					PreparedStatement pre2=conn1.prepareStatement(sql2);
					pre2.execute();
					
//					System.out.println("SQL====> "+sql1);
//					System.out.println("SQL====>1 "+	);
				
					
					//0. update tablename set rowth=mvedrows 6 where nodeid=mvngIDs 1001 and rowth= mvedrows 3  3=6
					//1. update tablename set rowth=mvedrows 7 where nodeid=mvngIDs 1002 and rowth= mvedrows 4  4=7
					//2. update tablename set rowth=mvedrows 8 where nodeid=mvngIDs 1003 and rowth= mvedrows 5  5=8
					
					//0. update tablename set rowth=mvngrows 3 where nodeid=mvedIDs 1010 and rowth= mvedrows 6  6=3
					//1. update tablename set rowth=mvngrows 4 where nodeid=mvedIDs 1011 and rowth= mvedrows 7  7=4
					//2. update tablename set rowth=mvngrows 5 where nodeid=mvedIDs 1012 and rowth= mvedrows 8  8=5
				}
				
			}else {
				
				if(navigationType.equals("UP")){
					
					updateDBtable4RowsUPDOWN2(CurrentMovingNodeROWSHT,CurrentMovingNodeIDSHT,
					MovedNodeIDSHT,MovedNodeROWSHT,mvedsize,mvngsize,conn1);
					
				}else{
					
			/////////down
					updateDBtable4RowsUPDOWN2(MovedNodeROWSHT,MovedNodeIDSHT,
							CurrentMovingNodeIDSHT,CurrentMovingNodeROWSHT,mvngsize,mvedsize,conn1);
				}
			}
				
//				if(mvngsize>mvedsize){
//				
//				updateDBtable4RowsUPDOWN2(CurrentMovingNodeROWSHT,CurrentMovingNodeIDSHT,
//						MovedNodeIDSHT,MovedNodeROWSHT,mvedsize,mvngsize,conn1);
//
//			}else if(mvngsize<mvedsize){
//				
//				updateDBtable4RowsUPDOWN2(MovedNodeROWSHT,MovedNodeIDSHT,
//						CurrentMovingNodeIDSHT,CurrentMovingNodeROWSHT,mvngsize,mvedsize,conn1);
//				
//			}
			
			
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		
		
		
	}
	

	public static void updateDBtable4RowsUPDOWN2(Hashtable CurrentMovingNodeROWSHT,Hashtable CurrentMovingNodeIDSHT,
			Hashtable MovedNodeIDSHT,Hashtable MovedNodeROWSHT,int mvedsize,int mvngsize,Connection conn1){
	try{	
		
		
	//////////////////////up////////
//		Hashtable totalIDsHT=new Hashtable<>();
		Hashtable totalRowHT=new Hashtable<>();
		
		
		int totrow=0;
		for(int i=0;i<MovedNodeROWSHT.size();i++){
//			totalIDsHT.put(totrow, String.valueOf(MovedNodeIDSHT.get(i)));
			totalRowHT.put(totrow, String.valueOf(MovedNodeROWSHT.get(i)));
			totrow++;
		}
		for(int i=0;i<CurrentMovingNodeROWSHT.size();i++){
//			totalIDsHT.put(totrow, String.valueOf(CurrentMovingNodeIDSHT.get(i)));
			totalRowHT.put(totrow, String.valueOf(CurrentMovingNodeROWSHT.get(i)));
			totrow++;
		}
		
		
		System.out.println("totalRowHT--->"+totalRowHT);
		int getrow=0;
		for(int i=0;i<CurrentMovingNodeROWSHT.size();i++){
			
			String mvngIDs=String.valueOf(CurrentMovingNodeIDSHT.get(i)); //537   1002
			String mvngrows=String.valueOf(CurrentMovingNodeROWSHT.get(i));    //33     4
			
			String changeID=(String)totalRowHT.get(getrow);
			
//			String sql1="UPDATE WC_FLEX_HIERARCHY_D SET ROW_WID="+changeID+" WHERE XML_NODE_ID="+mvngIDs+" AND ROW_WID="+mvngrows+"";
			String sql1="UPDATE WC_FLEX_HIERARCHY_D SET SORT_ORDER="+changeID+" WHERE XML_NODE_ID="+mvngIDs+" AND SORT_ORDER="+mvngrows+"";
			PreparedStatement pre1=conn1.prepareStatement(sql1);
			pre1.execute();
			
			System.out.println("sql1--->"+sql1);
			
			getrow++;
			
		}
		
		for(int i=0;i<MovedNodeROWSHT.size();i++){
			
			String mvedIDs=String.valueOf(MovedNodeIDSHT.get(i));  //534
			String mvedrows=String.valueOf(MovedNodeROWSHT.get(i));  // 29
			
	         String changeID=(String)totalRowHT.get(getrow);
			
//			String sql1="UPDATE WC_FLEX_HIERARCHY_D SET ROW_WID="+changeID+" WHERE XML_NODE_ID="+mvedIDs+" AND ROW_WID="+mvedrows+"";
	         String sql1="UPDATE WC_FLEX_HIERARCHY_D SET SORT_ORDER="+changeID+" WHERE XML_NODE_ID="+mvedIDs+" AND SORT_ORDER="+mvedrows+"";
			PreparedStatement pre1=conn1.prepareStatement(sql1);
			pre1.execute();
			
			System.out.println("sql1--->"+sql1);
			
			getrow++;
		}
		
		
	///////////////////////	
//	
//	
//
//		Hashtable TempROWSHT=new Hashtable<>();
//		Hashtable TempIDSHT=new Hashtable<>();
//		Hashtable alreadyPUTROWSTempHT=new Hashtable<>();
//		Hashtable alreadyPUTIDSTempHT=new Hashtable<>();
//		
//		for(int i=0;i<CurrentMovingNodeROWSHT.size();i++){
//			TempROWSHT.put(i, String.valueOf(CurrentMovingNodeROWSHT.get(i)));
//			TempIDSHT.put(i, String.valueOf(CurrentMovingNodeIDSHT.get(i)));
//		}
////		TempROWSHT=CurrentMovingNodeROWSHT; //3,4,5 
////		TempIDSHT=CurrentMovingNodeIDSHT; //3,4,5 
//		
//		int romove=0;
//		for(int i=0;i<mvedsize;i++){
//			
//			String mvedIDs=String.valueOf(MovedNodeIDSHT.get(i));  //534
//			String mvedrows=String.valueOf(MovedNodeROWSHT.get(i));  // 29
//			
//			String mvngIDs=String.valueOf(CurrentMovingNodeIDSHT.get(i)); //537   1002
//			String mvngrows=String.valueOf(CurrentMovingNodeROWSHT.get(i));    //33     4
//			
////			alreadyPUTempHT.put(mvngrows, mvngIDs);  // 1001,3   1002,4
//			TempROWSHT.remove(i);  //3 4
//			TempIDSHT.remove(i);
//			romove=i;
//			String sql1="UPDATE WC_FLEX_HIERARCHY_D SET ROW_WID="+mvngrows+" WHERE XML_NODE_ID="+mvedIDs+" AND ROW_WID="+mvedrows+"";
//			PreparedStatement pre1=conn1.prepareStatement(sql1);
//			pre1.execute();
//			
//			System.out.println("sql1-->"+sql1);
//			
//			
//			//0. update tablename set rowth=mvngIDs 3 where nodeid=mvedrows 1010 and rowth= mvedIDs 6  6=3
//			
//			//1. update tablename set rowth=mvngIDs 4 where nodeid=mvedrows 1011 and rowth= mvedIDs 7	 7=4			
//			
//		}
//		
//		int k=romove+1;
//		for(int i=0;i<TempROWSHT.size();i++){
//			String getid=String.valueOf(TempIDSHT.get(k));
//			String getRows=String.valueOf(TempROWSHT.get(k));
//			
//			alreadyPUTROWSTempHT.put(i, getRows);  // 5
//			alreadyPUTIDSTempHT.put(i, getid);  //1003 
//			
//		}
//		
//		
////		int k=0;
////		Enumeration enumatn=TempROWSHT.keys();
////		while(enumatn.hasMoreElements()){
////			String htval=String.valueOf(enumatn.nextElement());
//////			alreadyPUTROWSTempHT.put(k, htval);  // 5
////			alreadyPUTROWSTempHT.put(k, htval);  // 5
////			k++;
////		}
////		
////		int l=0;
////		Enumeration enumatn2=TempIDSHT.keys();
////		while(enumatn2.hasMoreElements()){
////			String htval=String.valueOf(enumatn2.nextElement());
//////			alreadyPUTIDSTempHT.put(l, htval);  //1003
////			alreadyPUTIDSTempHT.put(l, htval);  //1003
////			l++;
////		}
//		
//		
//		int m=1;
//		int mvdid=0;
//		int putsize=alreadyPUTROWSTempHT.size();
//		System.out.println("alreadyPUTROWSTempHT-->"+alreadyPUTROWSTempHT);
//		System.out.println("alreadyPUTIDSTempHT-->"+alreadyPUTIDSTempHT);
//		
//		for(int i=0;i<mvngsize;i++){
//			if(m<=putsize){
//				
//				String mvngIDs=String.valueOf(CurrentMovingNodeIDSHT.get(i));  //1001  
//				String mvngrows=String.valueOf(CurrentMovingNodeROWSHT.get(i));  //3     
//				
//				String mvedIDs=String.valueOf(alreadyPUTIDSTempHT.get(i));  // 5  
//				String mvedrows=String.valueOf(alreadyPUTROWSTempHT.get(i));  // 1003
//				
//				String sql1="UPDATE WC_FLEX_HIERARCHY_D SET ROW_WID="+mvedrows+" WHERE XML_NODE_ID="+mvngIDs+" AND ROW_WID="+mvngrows+"";
//				PreparedStatement pre1=conn1.prepareStatement(sql1);
//				pre1.execute();
//				
//				System.out.println("sql2-->"+sql1);
//				
//				// update tablename set rowth=id 5 where nodeid=id 1001 and rowth= rowth 3	 3=5
//			}else{
//				
//				String mvngIDs=String.valueOf(CurrentMovingNodeIDSHT.get(i)); // 1002
//				String mvngrows=String.valueOf(CurrentMovingNodeROWSHT.get(i));    //     4
//				
//				String mvedIDs=String.valueOf(MovedNodeIDSHT.get(mvdid));  // 6
//				String mvedrows=String.valueOf(MovedNodeROWSHT.get(mvdid));  // 1010
//				
//
//				String sql1="UPDATE WC_FLEX_HIERARCHY_D SET ROW_WID="+mvedrows+" WHERE XML_NODE_ID="+mvngIDs+" AND ROW_WID="+mvngrows+"";
//				PreparedStatement pre1=conn1.prepareStatement(sql1);
//				pre1.execute();
//				
//				System.out.println("sql3-->"+sql1);
//				
//				mvdid++;
//				
//				//1. update tablename set rowth=mvngIDs 6 where nodeid=mvngIDs 1002 and rowth= mvngIDs 4	 4=6	
//				
//				
//			}
//			m++;
//		}	
		
		
		//////////////////////
		
	}catch (Exception e) {
		// TODO: handle exception
		e.printStackTrace();
	}
		
		
	}
	
	public static Hashtable gettingAllchildAttributesFrmXml(Node node,String comngFrm) throws Exception {
		Hashtable nodedetHT=new Hashtable<>();
		Hashtable nodeNameandTypeHT4Segment=new Hashtable<>();
		int key=1;
		Hashtable parNodeDetHT=Globals.getAttributeNameandValHT(node);
		
		nodedetHT.put(0,(String)parNodeDetHT.get("ID"));
		processHierarchyNode(key,nodedetHT,node,nodeNameandTypeHT4Segment,comngFrm);
		System.out.println("nodedetHT "+nodedetHT);
		return nodedetHT;
	}
	
	 public static int processHierarchyNode(int key,Hashtable nodedetHT,Node node,Hashtable nodeNameandTypeHT4Segment,String comngFrm)
	   {

	    System.out.println("Entering: " + new Exception().getStackTrace()[0].getClassName() + "."
	              + new Exception().getStackTrace()[0].getMethodName());

	    try {

	     if (node.getNodeType() == Node.ELEMENT_NODE) {
	        NodeList nl = node.getChildNodes();
	       System.out.println("Reading HierarchyNode Length: " + nl.getLength());
	     for (int i = 0; i < nl.getLength(); i++) {
	       node = nl.item(i);
	       if (node.getNodeType() == Node.ELEMENT_NODE) {

	        System.out.println("------------>Reading HierarchyNode Name : " + node.getNodeName()+" "+node.getAttributes().getNamedItem("ID"));
	        Hashtable NodeHT = new Hashtable();
	        NodeHT = Globals.getAttributeNameandValHT(node);
	        
	        if(comngFrm.equals("MoveUpNDown")){
	        	String nodeId=(String)NodeHT.get("ID");
				nodedetHT.put(key,nodeId);
	        }else{
	        	
		        NodeHT.put("NodeName", node.getNodeName());
//		        if(!node.getNodeName().equalsIgnoreCase("ID") && !node.getNodeName().equalsIgnoreCase("RootLevel_Name")){
		        nodeNameandTypeHT4Segment.put(key, node.getNodeName()+"#~#"+NodeHT.get("Type"));
//		        }
		        System.out.println("Key "+key+" HT "+NodeHT);
		        nodedetHT.put(key,NodeHT);	
	        }
	        
	        key++;
	       key= processHierarchyNode(key,nodedetHT,node,nodeNameandTypeHT4Segment,comngFrm); 
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
	
	 public static Hashtable gettingRow_WidfrmDB(Hashtable curNodedetHT,Connection conn1)
		{
			Hashtable rowWIDht=new Hashtable<>();
			try{
//			System.out.println("NodeHT SIZE : "+curNodedetHT.size());
//			Connection dbConn=null;
//			Hashtable connHT=Globals.ReadingConnectionString();
//			
//			String hstName=String.valueOf(connHT.get("HostName"));
//			 String portNo=String.valueOf(connHT.get("PortName"));
//			 String DBName=String.valueOf(connHT.get("DBName"));
//			 String userName=String.valueOf(connHT.get("UserName"));
//			 String passWord=String.valueOf(connHT.get("Password"));
//			
//			 String url1 = "jdbc:oracle:thin:@"+hstName+":"+portNo+":"+DBName;
//			   Class.forName("oracle.jdbc.driver.OracleDriver");
//			   Connection conn1 = DriverManager.getConnection(url1, userName, passWord);
			   
			   int key=0;
			for(int i=0;i<curNodedetHT.size();i++)
			{
//				System.out.println("ID----> "+curNodedetHT.get(i));
				String nodeId4sql=(String)curNodedetHT.get(i);
//				 String sql1="SELECT ROW_WID FROM WC_FLEX_HIERARCHY_D WHERE XML_NODE_ID='"+nodeId4sql+"'";
				 String sql1="SELECT SORT_ORDER FROM WC_FLEX_HIERARCHY_D WHERE XML_NODE_ID='"+nodeId4sql+"'";
				 PreparedStatement pre1=conn1.prepareStatement(sql1);
				 ResultSet rs=pre1.executeQuery();
				 while(rs.next()){
					 int rowId=rs.getInt(1);
//					 System.out.println("row Wid== "+rowId);	
					 rowWIDht.put(key,rowId);
					 key++;
					 
				 }
				 
			}
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			return rowWIDht;
		}
	 
	 
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
