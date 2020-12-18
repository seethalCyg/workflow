package beans;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import managers.LoginProcessManager;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.jniwrapper.win32.jexcel.bo;

import utils.Globals;
import utils.PropUtil;
@ManagedBean(name = "rIConstrainsBean")
@SessionScoped
public class RIConstrainsBean {

	/**
	 * @param args
	 */

	boolean rIValidationFailure=false;
	String riValidationFromPage="";  // code change Menaka 05APR2014
	
	
	 public String getRiValidationFromPage() {
		return riValidationFromPage;
	}
	public void setRiValidationFromPage(String riValidationFromPage) {
		this.riValidationFromPage = riValidationFromPage;
	}
	 public boolean isrIValidationFailure() {
		return rIValidationFailure;
	}
	public void setrIValidationFailure(boolean rIValidationFailure) {
		this.rIValidationFailure = rIValidationFailure;
	}
	
	 public static void setRIConstrainStatus(String Hierarchy_ID,String cmngFromPage,String userSelectionType){
			System.out.println("Entering: " + new Exception().getStackTrace()[0].getClassName() + "."
		            + new Exception().getStackTrace()[0].getMethodName());	  
		  try{
			  
 // RI Validation = in prograss
			  
			  PropUtil prop=new PropUtil();
				String heirLevelXML=prop.getProperty("HIERARCHY_XML_FILE");
				String heirLevelDir=prop.getProperty("HIERARCHY_XML_DIR");
				String riConstrainXml=prop.getProperty("RI_CONSTRAINTS_XML");
							
				//DH
			  System.out.println("setRIConstrainStatus: Hierarchy_ID "+Hierarchy_ID +" cmngFromPage "+cmngFromPage+" userSelectionType "+userSelectionType);
			
				
			  FacesContext ctx = FacesContext.getCurrentInstance();  
				ExternalContext extContext = ctx.getExternalContext();
				Map sessionMap = extContext.getSessionMap();
				HierarchyBean hierdb = (HierarchyBean) sessionMap.get("hierarchyBean");
			  
			  if(cmngFromPage.equals("AdminRulepopup")){
					
					   if(userSelectionType.equalsIgnoreCase("Yes")){
						hierdb.setMessage4RIconstraints("Referential Integrity Validation is in progress. You can view the status in the hierarchy list.");
						hierdb.setMsg1("Referential Integrity Validation is in progress. You can view the status in the hierarchy list.");
						hierdb.color4HierTreeMsg = "blue";
					   }
					  Document xmlDoc1=Globals.openXMLFile(heirLevelDir, heirLevelXML);
					  setprograssMsg(Hierarchy_ID,xmlDoc1);
					  Globals.writeXMLFile(xmlDoc1, heirLevelDir, heirLevelXML);
					  
					  System.out.println("-- Next Proces Start");
					  
					  Document xmlDoc=Globals.openXMLFile(heirLevelDir, heirLevelXML);
//					  if(xmlDoc==null)
						  System.out.println("-- Start"+xmlDoc+"==>"+heirLevelDir+"==>"+heirLevelXML);
				      differRHNDH(Hierarchy_ID,xmlDoc,userSelectionType,heirLevelDir,heirLevelXML);
					
					
				}else if(cmngFromPage.equals("RI-RHDeleteProcess")){
					
					if(userSelectionType.equalsIgnoreCase("Yes")){
						hierdb.setMsg1("Referential Integrity Validation is in progress. You can view the status in the hierarchy list.");
						hierdb.color4HierTreeMsg = "blue";
					}
						
					
					 Document xmlDoc1=Globals.openXMLFile(heirLevelDir, heirLevelXML);
					  setprograssMsg(Hierarchy_ID,xmlDoc1);
					  Globals.writeXMLFile(xmlDoc1, heirLevelDir, heirLevelXML);
					
					 Document xmlDoc=Globals.openXMLFile(heirLevelDir, heirLevelXML);
					differRHNDH(Hierarchy_ID,xmlDoc,userSelectionType,heirLevelDir,heirLevelXML);
					
					/////					
				}
				
				
				hierdb.fullMsg1=hierdb.msg1;   // code change Menaka 29APR2014
				if(hierdb.msg1.length()>80){
					hierdb.msg1=hierdb.msg1.substring(0, 78)+"...";
				}
			  
			  
		  }catch (Exception e) {
				e.printStackTrace();
			}
			
		 System.out.println("Exiting: " + new Exception().getStackTrace()[0].getClassName() + "."
			            + new Exception().getStackTrace()[0].getMethodName());
			  
			
		}
	 
	 public static void setprograssMsg(String Hierarchy_ID,Document xmlDoc){
			System.out.println("Entering: " + new Exception().getStackTrace()[0].getClassName() + "."
		            + new Exception().getStackTrace()[0].getMethodName());	  
		  try{
			  
			  Node HeirLevelNRH=Globals.getNodeByAttrVal(xmlDoc, "Hierarchy_Level", "Hierarchy_ID", Hierarchy_ID);
				Element HeirLevelNRHEL=(Element)HeirLevelNRH;
				String hierType=HeirLevelNRHEL.getAttribute("RI_Hierarchy_Type"); 
				
           if(hierType.equals("Reference")){
					
           	String alldepent=HeirLevelNRHEL.getAttribute("RI_Dependent_Hierarchies"); 
					
					if(alldepent.contains(",")){
						String depArray[]=alldepent.split(",");
						String Hierarchy_IDRH=Hierarchy_ID;
						for(int k=0;k<depArray.length;k++){
							String DependHierarchy_ID=depArray[k];
							
							
							
							Node DependHierarchyNode=Globals.getNodeByAttrVal(xmlDoc, "Hierarchy_Level", "Hierarchy_ID", DependHierarchy_ID);
							Element DependHierarchyNodeEL=(Element)DependHierarchyNode;
							DependHierarchyNodeEL.setAttribute("Dim_Status", "In Progress");
							DependHierarchyNodeEL.setAttribute("Dim_Status_Details", "Referential Integrity validation in progress");
							
							System.out.println(DependHierarchy_ID+ "Setting as in prograss in dim status setprograssMsg:");
							
							
						}
						
						
					}else if(!alldepent.equals("")){
						
						Node DependHierarchyNode=Globals.getNodeByAttrVal(xmlDoc, "Hierarchy_Level", "Hierarchy_ID", alldepent);
						Element DependHierarchyNodeEL=(Element)DependHierarchyNode;
						DependHierarchyNodeEL.setAttribute("Dim_Status", "In Progress");
						DependHierarchyNodeEL.setAttribute("Dim_Status_Details", "Referential Integrity validation in progress");
						
						System.out.println(alldepent+ " alldepent Setting as in prograss in dim status setprograssMsg:");
					}
					
				}else if(hierType.equals("Dependent") || hierType.equals("Reporting")){
					
					Node DependHierarchyNode=Globals.getNodeByAttrVal(xmlDoc, "Hierarchy_Level", "Hierarchy_ID", Hierarchy_ID);
					Element DependHierarchyNodeEL=(Element)DependHierarchyNode;
					DependHierarchyNodeEL.setAttribute("Dim_Status", "In Progress");
					DependHierarchyNodeEL.setAttribute("Dim_Status_Details", "Referential Integrity validation in progress");
					
					System.out.println(Hierarchy_ID+ "Reporting/Dependent alldepent Setting as in prograss in dim status setprograssMsg:");
				}
			  
	 
		  }catch (Exception e) {
				e.printStackTrace();
			}
			
		 System.out.println("Exiting: " + new Exception().getStackTrace()[0].getClassName() + "."
			            + new Exception().getStackTrace()[0].getMethodName());
			  
			
		}
	 
	 
	 
	 public static void differRHNDH(String Hierarchy_ID,Document xmlDoc,String userSelectionType,String heirLevelDir,String  heirLevelXML){
			System.out.println("Entering: " + new Exception().getStackTrace()[0].getClassName() + "."
		            + new Exception().getStackTrace()[0].getMethodName());	  
		  try{
			  
			  Node HeirLevelNRH=Globals.getNodeByAttrVal(xmlDoc, "Hierarchy_Level", "Hierarchy_ID", Hierarchy_ID);
				Element HeirLevelNRHEL=(Element)HeirLevelNRH;
				String hierType=HeirLevelNRHEL.getAttribute("RI_Hierarchy_Type"); 
				
				
				
              if(hierType.equals("Reference")){
					
              	String alldepent=HeirLevelNRHEL.getAttribute("RI_Dependent_Hierarchies"); 
              	
              	 System.out.println("differRHNDH: Hierarchy_ID "+Hierarchy_ID +" hierType "+hierType+" userSelectionType "+userSelectionType+" alldepent"+alldepent);
					
					if(alldepent.contains(",")){
						String depArray[]=alldepent.split(",");
						String Hierarchy_IDRH=Hierarchy_ID;
						for(int k=0;k<depArray.length;k++){
							String DependHierarchy_ID=depArray[k];
							 System.out.println("ssssss===>"+DependHierarchy_ID);
							setRIConstrainStatusProcess(xmlDoc,DependHierarchy_ID,Hierarchy_IDRH,userSelectionType,heirLevelDir, heirLevelXML);
						}
						
						
					}else if(!alldepent.equals("")){
						setRIConstrainStatusProcess(xmlDoc,alldepent,Hierarchy_ID,userSelectionType,heirLevelDir, heirLevelXML);
					}
					
				}else if(hierType.equals("Dependent") || hierType.equals("Reporting")){
					
					String riRefHierID=HeirLevelNRHEL.getAttribute("RI_Reference_Hierarchy_ID");
					
					 System.out.println("differRHNDH: Hierarchy_ID "+Hierarchy_ID +" hierType "+hierType+" userSelectionType "+userSelectionType+" riRefHierID"+riRefHierID);
					 
					setRIConstrainStatusProcess(xmlDoc,Hierarchy_ID,riRefHierID,userSelectionType,heirLevelDir, heirLevelXML);
				}
			  
	 
		  }catch (Exception e) {
				e.printStackTrace();
			}
			
		 System.out.println("Exiting: " + new Exception().getStackTrace()[0].getClassName() + "."
			            + new Exception().getStackTrace()[0].getMethodName());
			  
			
		}
	 public static void setRIConstrainStatusProcess(Document xmlDoc,String DependHierarchy_ID,String Hierarchy_IDRH,String userSelectionType,String heirLevelDir,String heirLevelXML){
			System.out.println("Entering: " + new Exception().getStackTrace()[0].getClassName() + "."
		            + new Exception().getStackTrace()[0].getMethodName());	  
		  try{
			  
			
			  Date currentDate=null;
				PropUtil prop=new PropUtil();
			String dateFormat=	prop.getProperty("DATE_FORMAT");
				SimpleDateFormat sdf=new SimpleDateFormat(dateFormat);  // code change Menaka 14APR2014
				
				
			  
			  			    
				Node depHeirLevelN=Globals.getNodeByAttrVal(xmlDoc, "Hierarchy_Level", "Hierarchy_ID", DependHierarchy_ID);
				Node defrootlevelNode=Globals.getNodeByAttrValUnderParent(xmlDoc, depHeirLevelN, "ID", DependHierarchy_ID);
//				Node rulenodeDH=Globals.getNodeByAttrValUnderParent(xmlDoc, depHeirLevelN, "Hierarchy_ID", DependHierarchy_ID);	
				Node rulenodeDH=Globals.getNodeByAttrVal(xmlDoc, "RI-Rules", "Hierarchy_ID", DependHierarchy_ID);  
				
				// RH
					Node HeirLevelNRH=Globals.getNodeByAttrVal(xmlDoc, "Hierarchy_Level", "Hierarchy_ID", Hierarchy_IDRH);
//					Node rootlevelNodeRH=Globals.getNodeByAttrValUnderParent(xmlDoc, HeirLevelNRH, "ID", Hierarchy_IDRH);
					
					
						
					
//					System.out.println("--->"+rootlevelNodeRH.getNodeName());
					
					
				Hashtable ruleHT=Globals.getAttributeNameandValHT(rulenodeDH);
			    ArrayList l=new ArrayList();
				Enumeration en=ruleHT.keys();
				while(en.hasMoreElements()){
					
					String key=(String)en.nextElement();
					if(!key.equals("Hierarchy_ID")){
//						System.out.println("--->"+key);
						String value=(String)ruleHT.get(key);
						if(value.equals("true")){
							l.add(key);
						}
						
						
					}
					
				}
				
				
				
				Collections.sort(l);
		
				
				if(userSelectionType.equalsIgnoreCase("Yes")){  // yes 
					Hashtable riValidationStatusHT=new Hashtable<>();
					riValidationStatusHT.put("RI_status", "Success");
					riValidationStatusHT.put("RI_Details", "Success");
					
					Element defrootlevelNodeEL=(Element)defrootlevelNode;
					defrootlevelNodeEL.setAttribute("RI_Failure_Code", "");
					defrootlevelNodeEL.setAttribute("RI_Failure_Msg", "");
					defrootlevelNodeEL.setAttribute("RI_Validation_Status", "");
					
					
					
					
					riImplement4WholeHirerachy(xmlDoc,DependHierarchy_ID,defrootlevelNode,l,HeirLevelNRH,riValidationStatusHT);
					
					
					if(riValidationStatusHT.get("RI_status")!=null){
						String rival=(String)riValidationStatusHT.get("RI_status");
						 Element depHeirLevelNEL=(Element)depHeirLevelN;
						if(rival.equals("Failure")){
							
							
							String failuremsg=(String)riValidationStatusHT.get("RI_Details");
							System.out.println("riImplement4WholeHirerachy: Setting as Failure in Dim Status" +failuremsg);
							depHeirLevelNEL.setAttribute("Dim_Status", "Failure");
							depHeirLevelNEL.setAttribute("Dim_Status_Details", failuremsg);
						}else{
							// temporary comments no neede to write success
							 String error=depHeirLevelNEL.getAttribute("Dim_Status");
							 if(!error.equalsIgnoreCase("Error")){
								 
							String valStatus4Hier=(String) riValidationStatusHT.get("RI_Validation_Failure");
							currentDate=new Date();
							if(valStatus4Hier!=null&&valStatus4Hier.equals("Failure")){
//								System.out.println(sdf.format(currentDate));
								 depHeirLevelNEL.setAttribute("Dim_Status", "Validation Failure");
								 depHeirLevelNEL.setAttribute("Dim_Status_Details", "Referential Integrity validation failed. "+sdf.format(currentDate)+". Click View to see the details");
								
							}else{
							     depHeirLevelNEL.setAttribute("RI_Validated", "true"); // code change pandian 21APR2014 add ri validation true
								
								 depHeirLevelNEL.setAttribute("Dim_Status", "Validation Success");
								 depHeirLevelNEL.setAttribute("Dim_Status_Details", "Referential Integrity validation succeded. "+sdf.format(currentDate)+". Click View to see the details");
							}
								 
								
							 }
							
							
						}
						
					}
					 // RI Validation = Completed
					Globals.writeXMLFile(xmlDoc, heirLevelDir, heirLevelXML);
				}else{ // no set Root Node as Error
					
					//set attributes here for this parent (DH)
					 System.out.println("  (**SET ATTRIBUTES RI-000 **) when Validate Both Reference Hierarchy And Dependant Hierarchies Does not match these Hierarchy");
					 Element setAtrributesEL=(Element)defrootlevelNode;
					 setAtrributesEL.setAttribute("RI_Validation_Status", "Failure");
					 setAtrributesEL.setAttribute("RI_Failure_Code", "RI-000");
					 setAtrributesEL.setAttribute("RI_Failure_Msg", "Referential Integrity constraints configuration has changed and validation is not done. You need to perform a RI Validation before you can generate this hierarchy.");
					 
					 //set current Status as YTS or Draft
					 Node checkifWorkflowisExists=Globals.getNodeByAttrVal(xmlDoc, "Workflow", "Hierarchy_ID", DependHierarchy_ID);
					 if(checkifWorkflowisExists!=null){
						 Element checkifWorkflowisExistsEL=(Element)checkifWorkflowisExists;
					String currentStageName=checkifWorkflowisExistsEL.getAttribute("Current_Stage_Name");
					String currentStageNo=checkifWorkflowisExistsEL.getAttribute("Current_Stage_No");
					String totalStage=checkifWorkflowisExistsEL.getAttribute("Total_No_Stages");
					 Node isStageExists=null;		
					if(currentStageNo.equals("Completed")){	
						int tot=Integer.parseInt(totalStage);
						int st=1;
						for(int j=0;j<tot;j++){
							 setStageStatus(xmlDoc, checkifWorkflowisExists, String.valueOf(st));
							 st++;
						}
						
						// code change pandian 28Mar2013
					 Node  isStageExists1=Globals.getNodeByAttrValUnderParent(xmlDoc, checkifWorkflowisExists, "Stage_No", String.valueOf(1));
					 Element isStageExistsEL=(Element)isStageExists1;
					 checkifWorkflowisExistsEL.setAttribute("Current_Stage_Name", isStageExistsEL.getAttribute("Stage_Name"));
					 checkifWorkflowisExistsEL.setAttribute("Current_Stage_No",String.valueOf(1));
					
		            
		            
					}else{
						setStageStatus(xmlDoc, checkifWorkflowisExists, currentStageNo) ;
					
					}
					
					
				
					
						 
					 }
					 // RI Validation = YTS
					// temporary comments no neede to write success
						 Element depHeirLevelNEL=(Element)depHeirLevelN;
						 String error=depHeirLevelNEL.getAttribute("Dim_Status");
						 if(!error.equalsIgnoreCase("Error")){
							 currentDate=new Date();  // code change Menaka 14APR2014
							 depHeirLevelNEL.setAttribute("Dim_Status", "Validation Success");
							 depHeirLevelNEL.setAttribute("Dim_Status_Details", "Referential Integrity validation succeded. " +sdf.format(currentDate)+". Click View to see the details");
							
						 }
						
						
					   Globals.writeXMLFile(xmlDoc, heirLevelDir, heirLevelXML);
					 
		}
				
				
				
			  
			  
		  }catch (Exception e) {
				e.printStackTrace();
			}
			
		 System.out.println("Exiting: " + new Exception().getStackTrace()[0].getClassName() + "."
			            + new Exception().getStackTrace()[0].getMethodName());
			  
			
		}
	
	 public static void setStageStatus(Document xmlDoc, Node checkifWorkflowisExists, String currentStageNo){
			System.out.println("Entering: " + new Exception().getStackTrace()[0].getClassName() + "."
		            + new Exception().getStackTrace()[0].getMethodName());	  
		  try{
			  
			Node  isStageExists=Globals.getNodeByAttrValUnderParent(xmlDoc, checkifWorkflowisExists, "Stage_No", currentStageNo);
             Element isStageExistsEL=(Element)isStageExists;
             isStageExistsEL.setAttribute("Stage_Status", "YTS");
             isStageExistsEL.setAttribute("MailSent", "");
             isStageExistsEL.setAttribute("Mail_Sent_Time","");
             
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
						Element prenameE = (Element) preNod;
						prenameE.setAttribute("User_Status", "WIP");
					           }
					         }
				     	}
				
					}

				}

			}
			  
	 
		  }catch (Exception e) {
				e.printStackTrace();
			}
			
		 System.out.println("Exiting: " + new Exception().getStackTrace()[0].getClassName() + "."
			            + new Exception().getStackTrace()[0].getMethodName());
			  
			
		}


	public static void riImplement4WholeHirerachy(Document hierxmlDoc,String DependHierarchy_ID,Node defrootlevelNode, ArrayList l,Node refrootlevelNode,Hashtable riValidationStatusHT){
		System.out.println("Entering: " + new Exception().getStackTrace()[0].getClassName() + "."
	            + new Exception().getStackTrace()[0].getMethodName());	  
	  try{
		  
		    
		  
		  NodeList defrootNodeList=defrootlevelNode.getChildNodes();
//		  System.out.println("Refrence NodeList Length "+refrootNodeList.getLength()+" : Depennent NodeList Length "+defrootNodeList.getLength());
		  
		  for(int nod=0;nod<defrootNodeList.getLength();nod++){
			
			  Node defchnod=defrootNodeList.item(nod);
//			  System.out.println("1.refchnod"+refchnod);
//			  System.out.println("2.defchnod"+defchnod);
//			  System.out.println("");
			  
			  if(defchnod.getNodeType()==Node.ELEMENT_NODE){
				  
				  String defchnodName=defchnod.getNodeName();
				  if(defchnodName.equals("ID") || defchnodName.equals("RootLevel_Name")){
					  continue;
				  }
				  
				  if(defchnodName.contains("Level")){
					  
				  }else{ continue;}
				  
//				  System.out.println("defchnodName Name "+defchnodName);
				  Hashtable depchildHT=Globals.getAttributeNameandValHT(defchnod);
				  String defchName=(String)depchildHT.get("value");
//				  System.out.println("depchildHT"+depchildHT);
				  
					//compare parent
				  Node depParentNode=defchnod.getParentNode();
				  Hashtable depParentHT=Globals.getAttributeNameandValHT(depParentNode);
				  String defparentName=(String)depParentHT.get("value");
				  
				  System.out.println(": Child Name :"+defchName +" Parent Name is "+defparentName);
				  System.out.println("");
				  
				  
				  String nodeTypefromDH=(String)depchildHT.get("Type");
				  if(nodeTypefromDH.equalsIgnoreCase("Hierarchy")){
						System.out.println("%% the Node Type is Hierarchy so no need to  (return this method )validate RI   :"+defchnod.getNodeName()+"<--->"+depchildHT);
						
					}else{
						  // implements every node 				
						for(int m=0; m<l.size(); m++){
							String constrain=(String)l.get(m);		
							 System.out.println("DependHierarchy_ID "+DependHierarchy_ID +" constrain "+constrain+" defparentName "+defparentName+
									 "defchName "+defchName +" refrootlevelNode "+refrootlevelNode+" defchnod "+defchnod+
									 "depParentNode "+depParentNode +" riValidationStatusHT "+riValidationStatusHT);
							
							invokeConstrains(hierxmlDoc,DependHierarchy_ID,constrain,defparentName,defchName,refrootlevelNode,defchnod,depParentNode,riValidationStatusHT,l);
						}
					}
				  
				
					
					
					
				/////////
						riImplement4WholeHirerachy(hierxmlDoc,DependHierarchy_ID,defchnod,l,refrootlevelNode,riValidationStatusHT);
				
				  
				 
				  
				
			  }
		  }
		  
	  }catch (Exception e) {
			e.printStackTrace();
			riValidationStatusHT.put("RI_status", "Failure");
			riValidationStatusHT.put("RI_Details", e.getMessage());
			
		}
		
	 System.out.println("Exiting: " + new Exception().getStackTrace()[0].getClassName() + "."
		            + new Exception().getStackTrace()[0].getMethodName());
		  
		
	}
	
	
	
//	Hierarchy_ID="120"
	
	
public static void implementationRI(String DHHierarchyID,String levelID,Node setAtrributes4ParentDH){
		
		System.out.println("Entering: " + new Exception().getStackTrace()[0].getClassName() + "."
	            + new Exception().getStackTrace()[0].getMethodName());	
		
		Hashtable allsiblingHTfromRH=new Hashtable<>();
		Hashtable allsiblingHTfromDH=new Hashtable<>();
	  try{
		  
		  PropUtil prop=new PropUtil();
			String heirLevelXML=prop.getProperty("HIERARCHY_XML_FILE");
			String heirLevelDir=prop.getProperty("HIERARCHY_XML_DIR");
			
			Date currentDate=null;
		String dateFormat=	prop.getProperty("DATE_FORMAT");
			SimpleDateFormat sdf=new SimpleDateFormat(dateFormat);  // code change Menaka 14APR2014
		  
		    Document hierxmlDoc=Globals.openXMLFile(heirLevelDir, heirLevelXML);
			Node hierlevelNodeDH=Globals.getNodeByAttrVal(hierxmlDoc, "Hierarchy_Level", "Hierarchy_ID", DHHierarchyID);			
//			Node rulenodeDH=Globals.getNodeByAttrValUnderParent(hierxmlDoc, hierlevelNodeDH, "Hierarchy_ID", DHHierarchyID);		
			Node rulenodeDH=Globals.getNodeByAttrVal(hierxmlDoc, "RI-Rules", "Hierarchy_ID", DHHierarchyID);  
			Hashtable hierlevelAttrDHHT=Globals.getAttributeNameandValHT(hierlevelNodeDH);
			String riTypeDH=(String)hierlevelAttrDHHT.get("RI_Hierarchy_Type");
			if(riTypeDH.equals("Reporting") || riTypeDH.equals("Dependent")){ //implementRI Rule
			String riRefHierarchyID=(String)hierlevelAttrDHHT.get("RI_Reference_Hierarchy_ID");
			
			Node RefheirLevelN=Globals.getNodeByAttrVal(hierxmlDoc, "Hierarchy_Level", "Hierarchy_ID", riRefHierarchyID);				
			Node refrootlevelNode=Globals.getNodeByAttrValUnderParent(hierxmlDoc, RefheirLevelN, "ID", riRefHierarchyID);
			
			Hashtable ruleHT=Globals.getAttributeNameandValHT(rulenodeDH);
		    ArrayList l=new ArrayList();
			Enumeration en=ruleHT.keys();
			while(en.hasMoreElements()){
				
				String key=(String)en.nextElement();
				if(!key.equals("Hierarchy_ID")){
					System.out.println("key--->"+key);
					String value=(String)ruleHT.get(key);
					if(value.equals("true")){
						l.add(key);
					}
					
					
				}
				
			}
			
			Hashtable nodeHT=getNodeUsingID(levelID,hierlevelNodeDH);
			boolean foundnode=(boolean)nodeHT.get("found");
			Node setAtrributes4ChildDH=null;
			if(foundnode){
			setAtrributes4ChildDH=(Node)nodeHT.get("Node");			
			}else{System.out.println("Node if Not found So Error");return;}
			
			
			////////
           
			Hashtable attrChildHT=Globals.getAttributeNameandValHT(setAtrributes4ChildDH);
			System.out.println("Child Node Name  :"+setAtrributes4ChildDH.getNodeName()+"<--->"+attrChildHT);
			// check the node type is hierarchy no need to validate integrity
			String nodeTypefromDH=(String)attrChildHT.get("Type");
			if(nodeTypefromDH.equalsIgnoreCase("Hierarchy")){
				System.out.println("%% the Node Type is Hierarchy so no need to  (return this method )validate RI   :"+setAtrributes4ChildDH.getNodeName()+"<--->"+attrChildHT);
				
				return;
			}
			
			
			
			Node parentNode=setAtrributes4ChildDH.getParentNode();
			Hashtable attrParentHT=Globals.getAttributeNameandValHT(parentNode);
			String defchildNodeValue=(String)attrChildHT.get("value");
			String defParentNodeValue=(String)attrParentHT.get("value");
			
			Hashtable riValidationStatusHT=new Hashtable<>();
			riValidationStatusHT.put("RI_status", "Success");
			riValidationStatusHT.put("RI_Details", "Success");
			
		
			System.out.println("%% ======>User Selected Node "+setAtrributes4ChildDH+"%% levelID "+levelID+"Parent Value Name :"+defParentNodeValue+"Child Value Name :"+defchildNodeValue+"l.size();"+l.size());
			Collections.sort(l);
			for(int m=0; m<l.size(); m++){
				String constrain=(String)l.get(m);		
				System.out.println("constrain Rules"+constrain);
				invokeConstrains(hierxmlDoc,DHHierarchyID,constrain,defParentNodeValue,defchildNodeValue,RefheirLevelN,setAtrributes4ChildDH,setAtrributes4ParentDH,riValidationStatusHT,l);
			}
			
			
			if(riValidationStatusHT.get("RI_status")!=null){
				String rival=(String)riValidationStatusHT.get("RI_status");
				 Element depHeirLevelNEL=(Element)hierlevelNodeDH;
				if(rival.equals("Failure")){
					String failuremsg=(String)riValidationStatusHT.get("RI_Details");
					depHeirLevelNEL.setAttribute("Dim_Status", "Failure");
					depHeirLevelNEL.setAttribute("Dim_Status_Details", failuremsg);
				}else{
					
					// temporary comments no neede to write success
					 String error=depHeirLevelNEL.getAttribute("Dim_Status");
					 if(!error.equalsIgnoreCase("Error")){
						 
						 String valStatus4Hier=(String) riValidationStatusHT.get("RI_Validation_Failure");
							
						
							currentDate=new Date();  // code change Menaka 14APR2014
							if(valStatus4Hier!=null&&valStatus4Hier.equals("Failure")){
								 depHeirLevelNEL.setAttribute("Dim_Status", "Validation Failure");
								 depHeirLevelNEL.setAttribute("Dim_Status_Details", "Referential Integrity validation failed. "+sdf.format(currentDate)+". Click View to see the details");
								
							}else{
								
								 depHeirLevelNEL.setAttribute("Dim_Status", "Validation Success");
								 depHeirLevelNEL.setAttribute("Dim_Status_Details", "Referential Integrity validation succeded. "+sdf.format(currentDate)+". Click View to see the details");
							}
					 }
					
					
				}
				
			}
			Globals.writeXMLFile(hierxmlDoc, heirLevelDir, heirLevelXML);
			
		/////////	
		
			
			}else if(riTypeDH.equals("Reference")){ //implementRI 7 Rule alone 
				
			}
				
			
		  
	  }catch (Exception e) {
			e.printStackTrace();
		}
		
	 System.out.println("Exiting: " + new Exception().getStackTrace()[0].getClassName() + "."
		            + new Exception().getStackTrace()[0].getMethodName());
		  
		
	}
	
public static void invokeConstrains(Document hierxmlDoc,String DependHierarchy_ID,String rule,String defParentNodeValue,String defchildNodeValue,Node refrootlevelNode,Node setAtrributes4ChildDH,Node setAtrributes4ParentDH,Hashtable riValidationStatusHT, ArrayList ruleAL){
	
	System.out.println("Entering: " + new Exception().getStackTrace()[0].getClassName() + "."
            + new Exception().getStackTrace()[0].getMethodName());	
	
  try{
	//RI constrain_config File	
	    PropUtil prop=new PropUtil();
		String heirLevelDir=prop.getProperty("HIERARCHY_XML_DIR");
		String riConstrainXml=prop.getProperty("RI_CONSTRAINTS_XML");
	    Document riConstrainXmlDoc=Globals.openXMLFile(heirLevelDir, riConstrainXml);    
	    Node riConsfailNode=Globals.getNodeByAttrVal(riConstrainXmlDoc, "Rule_Failure_Description", "Description", "Failure");
	    String faildes="Fail_"+rule;
	    Node failDesNode=Globals.getNodeByAttrValUnderParent(riConstrainXmlDoc, riConsfailNode, "Failure_ID", faildes);	    
	    Hashtable FailureDescriptionHT=Globals.getAttributeNameandValHT(failDesNode);
	    String FailureDescriptionstr=(String)FailureDescriptionHT.get("Failure_Msg");
	    
	    System.out.println("%% ======>Now Process the Rule : hierxmlDoc"+hierxmlDoc+",DependHierarchy_ID,"+DependHierarchy_ID+" rule"+rule+",defParentNodeValue "+defParentNodeValue+",defchildNodeValue,"+defchildNodeValue+"refrootlevelNode"+ refrootlevelNode +", setAtrributes4ChildDH"+setAtrributes4ChildDH+",setAtrributes4ParentDH"+setAtrributes4ParentDH);
	  
	  switch(rule){
	  case "RI-001":
		                    
		                    rIParentChild(defParentNodeValue,defchildNodeValue,refrootlevelNode,setAtrributes4ChildDH,setAtrributes4ParentDH,FailureDescriptionstr,riValidationStatusHT,ruleAL);
		  					break;
	  case "RI-002":
		                    rISiblings(defchildNodeValue,refrootlevelNode,setAtrributes4ChildDH,FailureDescriptionstr,riValidationStatusHT,ruleAL);
		  					break;
	  case "RI-003":        
	  {                        //code change vishnu 
		  					System.out.println("setAtrributes4ChildDH.getParentNode().getNodeName()==>"+setAtrributes4ChildDH.getParentNode().getNodeName());
		  					if(setAtrributes4ChildDH.getParentNode().getNodeName().equals("RootLevel")
		  							&& setAtrributes4ChildDH.getParentNode().getFirstChild().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getNextSibling().equals(setAtrributes4ChildDH)){
		  						
		  						String defchildNodeValue1 = Globals.getAttrVal4AttrName(setAtrributes4ChildDH.getParentNode(), "value");
		  						rIParentWtExactChildren(defchildNodeValue1,refrootlevelNode,setAtrributes4ChildDH.getParentNode(),FailureDescriptionstr,riValidationStatusHT,ruleAL);
		  						rIParentWtExactChildren(defchildNodeValue,refrootlevelNode,setAtrributes4ChildDH,FailureDescriptionstr,riValidationStatusHT,ruleAL);
			  					break;
		  					}else{   //code change vishnu 
		                     rIParentWtExactChildren(defchildNodeValue,refrootlevelNode,setAtrributes4ChildDH,FailureDescriptionstr,riValidationStatusHT,ruleAL);
		  					break;
		  					}
	  }
	  case "RI-004":
		                      rIaddNodewthSbiling(defchildNodeValue,refrootlevelNode,setAtrributes4ChildDH,FailureDescriptionstr,riValidationStatusHT,ruleAL);
		  					break;
	  case "RI-005":
		                       rIaddChildNodeIgnoreParentSbiling(hierxmlDoc,DependHierarchy_ID,defchildNodeValue,refrootlevelNode,setAtrributes4ChildDH,FailureDescriptionstr,riValidationStatusHT,ruleAL);
		  					break;
	  case "RI-006":
		                        riLowChildRolledupToRoot(hierxmlDoc,DependHierarchy_ID,defchildNodeValue,refrootlevelNode,setAtrributes4ChildDH,FailureDescriptionstr,riValidationStatusHT,ruleAL);
		  					break;
	
	  
	  }
	  
  }catch (Exception e) {
		e.printStackTrace();
		
		riValidationStatusHT.put("RI_status", "Failure");
		riValidationStatusHT.put("RI_Details", e.getMessage());
	}
	
 System.out.println("Exiting: " + new Exception().getStackTrace()[0].getClassName() + "."
	            + new Exception().getStackTrace()[0].getMethodName());
	  
	
}

public static void rIParentChild(String defParentNodeValue,String defchildNodeValue,Node refrootlevelNode,Node setAtrributes4ChildDH,Node setAtrributes4ParentDH,String FailureDescriptionstr,Hashtable riValidationStatusHT,ArrayList ruleAL){
	System.out.println("Entering: " + new Exception().getStackTrace()[0].getClassName() + "."
            + new Exception().getStackTrace()[0].getMethodName());	  
  try{
	  
	    Hashtable nodeinfoHT=getparentChildRelship(defParentNodeValue,defchildNodeValue,refrootlevelNode,"Parent");
		Boolean isparentAvalble=(Boolean)nodeinfoHT.get("found");
	    System.out.println("Parent Available in Ref Hierarchy :"+nodeinfoHT);
		if(isparentAvalble){
//			 System.out.println("test process start for Child ");
			Node parentNode=(Node)nodeinfoHT.get("Node");
			Hashtable childnodeinfoHT=getparentChildRelship(defchildNodeValue,"",parentNode,"Child");
			Boolean ischildAvalble=(Boolean)childnodeinfoHT.get("found");					
			 System.out.println("Child Available in Ref Hierarchy :"+childnodeinfoHT);
			 
			 Element setAtrributes4ChildDHEL=(Element)setAtrributes4ChildDH;
			 if(!ischildAvalble){				 
				 //set attributes here for this parent (DH)
				 System.out.println(" (**1 SET ATTRIBUTES FAILURE RI-001 **) No Child available in RH so Extra Child node availble in DH : :"+childnodeinfoHT);
				
				 String msg=FailureDescriptionstr.replace("$childName$", defchildNodeValue);
				 msg=msg.replace("$parentName$", defParentNodeValue);
				 setAtrributes4ChildDHEL.setAttribute("RI_Validation_Status", "Failure");
				 setAtrributes4ChildDHEL.setAttribute("RI_Failure_Code", "RI-001");
				 setAtrributes4ChildDHEL.setAttribute("RI_Failure_Msg", msg);
				 riValidationStatusHT.put("RI_Validation_Failure", "Failure");  // code change Menaka 14APR2014
			 }else{
				 System.out.println(" (**1 SET SUCCESS RI-001 **)  :"+childnodeinfoHT);
				 String failcode=setAtrributes4ChildDHEL.getAttribute("RI_Failure_Code");
				  if(failcode.equals("") || failcode.equals("RI-001") || failcode.equals("RI-000") || (!ruleAL.contains(failcode)) ){
						 setAtrributes4ChildDHEL.setAttribute("RI_Validation_Status", "Success");
						 setAtrributes4ChildDHEL.setAttribute("RI_Failure_Code", "");
						 setAtrributes4ChildDHEL.setAttribute("RI_Failure_Msg", "");
				  }
			 }
		}else{
			//set attributes here for this parent (DH)
			 System.out.println("  (**2 SET ATTRIBUTES FAILURE RI-001 **) No Parentr available in RH so Extra Parent node availble in DH : :"+defchildNodeValue);
			 Element setAtrributes4ChildDHEL=(Element)setAtrributes4ChildDH;
			 
			 String msg=FailureDescriptionstr.replace("$childName$", defchildNodeValue);
			 msg=msg.replace("$parentName$", defParentNodeValue);
			 setAtrributes4ChildDHEL.setAttribute("RI_Validation_Status", "Failure");
			 setAtrributes4ChildDHEL.setAttribute("RI_Failure_Code", "RI-001");
			 setAtrributes4ChildDHEL.setAttribute("RI_Failure_Msg", msg);
			 
			 riValidationStatusHT.put("RI_Validation_Failure", "Failure");  // code change Menaka 14APR2014
		
		
		}
	  
	  
  }catch (Exception e) {
		e.printStackTrace();
		
		riValidationStatusHT.put("RI_status", "Failure");
		riValidationStatusHT.put("RI_Details", e.getMessage());
	}
	
 System.out.println("Exiting: " + new Exception().getStackTrace()[0].getClassName() + "."
	            + new Exception().getStackTrace()[0].getMethodName());
	  
	
}

public static Hashtable getparentChildRelship(String defParentNodeValue,String defchildNodeValue,Node refrootlevelNode,String strType) {
	
	Hashtable nodeinfoHT=new Hashtable<>();
	 nodeinfoHT.put("found", false);
	  nodeinfoHT.put("Node", "NA");
	 try{
		  NodeList refrootNodeList=refrootlevelNode.getChildNodes();			  
		  for(int nod=0;nod<refrootNodeList.getLength();nod++){
			  Node refchnod=refrootNodeList.item(nod);
			  if(refchnod.getNodeType()==Node.ELEMENT_NODE){
				  String refchnodName=refchnod.getNodeName();
				  if(refchnodName.equals("ID") || refchnodName.equals("RootLevel_Name")){
					  continue;
				  }
				  
 if(refchnodName.contains("Level")){
					  
				  }else{ continue;}
				  
				  //first checking parent
				  Hashtable refchildHT=Globals.getAttributeNameandValHT(refchnod);
				  String refchName=(String)refchildHT.get("value");
				  System.out.println(defParentNodeValue+ " refchName  "+refchName);
				  
				  if(refchName.equals(defParentNodeValue)){
					  
					  System.out.println("");
					  System.out.println("********(Depennent)"+strType+" Name is "+defParentNodeValue+" IS EQUAL "+strType+" Name is ****"+refchName);
					  System.out.println("**Found Parent next process is  ckecking the Child Availablity**");
						
					
					  nodeinfoHT.put("found", true);
					  nodeinfoHT.put("Node", refchnod);
					  
					
					  return nodeinfoHT;
						 
					  
				  }else{
					  
					  Boolean returnval= (boolean)nodeinfoHT.get("found");
						 if(returnval){
							 return nodeinfoHT;
						 }
//					  System.out.println("(Depennent) "+strType+" Name is "+defParentNodeValue+" IS Not EQUAL "+strType+" Name is "+refchName);
//					  System.out.println("ckeck next level");
					  
					  nodeinfoHT= getparentChildRelship(defParentNodeValue,defchildNodeValue,refchnod,strType);
				  }
				  
				  
				  
				  
				  
			  }
			  
		  }
		  
		  
		 
		  
	  }catch (Exception e) {
			e.printStackTrace();
		}
	 
	 return nodeinfoHT;
	
}


public static Hashtable getNodeUsingID(String nodeID,Node defrootlevelNode) {
	
	Hashtable nodeinfoHT=new Hashtable<>();
	 nodeinfoHT.put("found", false);
	  nodeinfoHT.put("Node", "NA");
	 try{
		  NodeList refrootNodeList=defrootlevelNode.getChildNodes();			  
		  for(int nod=0;nod<refrootNodeList.getLength();nod++){
			  Node refchnod=refrootNodeList.item(nod);
			  if(refchnod.getNodeType()==Node.ELEMENT_NODE){
				  String refchnodName=refchnod.getNodeName();
				  if(refchnodName.equals("ID") || refchnodName.equals("RootLevel_Name")){
					  continue;
				  }
				  
 if(refchnodName.contains("Level")){
					  
				  }else{ continue;}
				  
				  //first checking parent
				  Hashtable refchildHT=Globals.getAttributeNameandValHT(refchnod);
				  String refchName=(String)refchildHT.get("ID");
				  System.out.println(nodeID+ "nodeID refchName  "+refchName);
				  
				  if(refchName.equals(nodeID)){
					  
					  System.out.println("");
					  System.out.println("********(Depennent) Name is nodeID "+nodeID+" IS EQUAL Name is ****"+refchName);
					  System.out.println("**Found Parent next process is  ckecking the Child Availablity**");
						
					
					  nodeinfoHT.put("found", true);
					  nodeinfoHT.put("Node", refchnod);
					  
					
					  return nodeinfoHT;
						 
					  
				  }else{
					  
					  Boolean returnval= (boolean)nodeinfoHT.get("found");
						 if(returnval){
							 return nodeinfoHT;
						 }
//					  System.out.println("(Depennent) "+strType+" Name is "+defParentNodeValue+" IS Not EQUAL "+strType+" Name is "+refchName);
//					  System.out.println("ckeck next level");
					  
					  nodeinfoHT= getNodeUsingID(nodeID,refchnod);
				  }
				  
				  
				  
				  
				  
			  }
			  
		  }
		  
		  
		 
		  
	  }catch (Exception e) {
			e.printStackTrace();
		}
	 
	 return nodeinfoHT;
	
}


public static void rIParentWtExactChildren(String defchildNodeValue,Node refrootlevelNode,Node definserNode,String FailureDescriptionstr,Hashtable riValidationStatusHT,ArrayList ruleAL){ 
	
	System.out.println("Entering: " + new Exception().getStackTrace()[0].getClassName() + "."
            + new Exception().getStackTrace()[0].getMethodName());	
	
	Hashtable allsiblingHTfromRH=new Hashtable<>();
	Hashtable allsiblingHTfromDH=new Hashtable<>();
  try{
	  
	  int sib=0;
	  int sibdh=0;
	  Hashtable nodeinfoHT=getNodeFromRHHierarchyXML(defchildNodeValue,refrootlevelNode);
		Boolean isparentAvalble=(Boolean)nodeinfoHT.get("found");
	    System.out.println("Parent Available in Ref Hierarchy :"+nodeinfoHT);
		if(isparentAvalble){
			
			Node refNd=(Node)nodeinfoHT.get("Node");
			rIParentWtExactChildrenProcess(definserNode,refNd,refrootlevelNode,FailureDescriptionstr,riValidationStatusHT,ruleAL);
			
			  
		}else{
			//set attributes here for this parent (DH)
			 System.out.println(" (**SET ATTRIBUTES RI-003 **) THIS NODE NOT AVAILABLE IN RH : :"+defchildNodeValue);
			 
			 Element setAttri4CurrentNd = (Element) definserNode;		//code change Vishnu 04Aug2014
			 System.out.println("setAttri4CurrentNd=====>"+ruleAL.contains(setAttri4CurrentNd.getAttribute("RI_Failure_Code")));
			 if(!ruleAL.contains(setAttri4CurrentNd.getAttribute("RI_Failure_Code"))){
				 setAttri4CurrentNd.setAttribute("RI_Validation_Status", "");
				 setAttri4CurrentNd.setAttribute("RI_Failure_Code", "");
				 setAttri4CurrentNd.setAttribute("RI_Failure_Msg", "");
			 }
			
			 Element setAtrributes4ChildDHEL=(Element)definserNode.getParentNode();
			 String msg=FailureDescriptionstr.replace("$childName$", defchildNodeValue);
			 msg=msg.replace("$parentName$", setAtrributes4ChildDHEL.getAttribute("value"));
			 setAtrributes4ChildDHEL.setAttribute("RI_Validation_Status", "Failure");
			 setAtrributes4ChildDHEL.setAttribute("RI_Failure_Code", "RI-003");
			 setAtrributes4ChildDHEL.setAttribute("RI_Failure_Msg", msg);
			 riValidationStatusHT.put("RI_Validation_Failure", "Failure");   // code change Menaka 14APR2014
		}
	  
  }catch (Exception e) {
		e.printStackTrace();
		riValidationStatusHT.put("RI_status", "Failure");
		riValidationStatusHT.put("RI_Details", e.getMessage());
	}
	
 System.out.println("Exiting: " + new Exception().getStackTrace()[0].getClassName() + "."
	            + new Exception().getStackTrace()[0].getMethodName());
	  
	
}


	
public static void rISiblings(String defchildNodeValue,Node refrootlevelNode,Node definserNode,String FailureDescriptionstr,Hashtable riValidationStatusHT,ArrayList ruleAL){ 
	
	System.out.println("Entering: " + new Exception().getStackTrace()[0].getClassName() + "."
            + new Exception().getStackTrace()[0].getMethodName());	
	
	Hashtable allsiblingHTfromRH=new Hashtable<>();
	Hashtable allsiblingHTfromDH=new Hashtable<>();
  try{
	  
	  int sib=0;
	  int sibdh=0;
	  Hashtable nodeinfoHT=getNodeFromRHHierarchyXML(defchildNodeValue,refrootlevelNode);
		Boolean isparentAvalble=(Boolean)nodeinfoHT.get("found");
	    System.out.println("Parent Available in Ref Hierarchy :"+nodeinfoHT);
		if(isparentAvalble){
			
			Node sibNode=(Node)nodeinfoHT.get("Node");
			Node refParentNode=sibNode.getParentNode();
			allsiblingHTfromRH=getallSiblinginHT(refParentNode);
//			  NodeList refParentNodeList=refParentNode.getChildNodes();			  
//			  for(int nod=0;nod<refParentNodeList.getLength();nod++){
//				  Node refNode=refParentNodeList.item(nod);
//				  if(refNode.getNodeType()==Node.ELEMENT_NODE){
//					  
//					  if(refNode.getNodeName().equals("ID") || refNode.getNodeName().equals("RootLevel_Name")){
//						  continue;
//					  }
//					 
//					  Hashtable refsiblnHT=Globals.getAttributeNameandValHT(refNode);
//					  String refsiblngName=(String)refsiblnHT.get("value");
//					  allsiblingHTfromRH.put(sib, refsiblngName);
//					  sib++;
//				  } 
//			  }
			  
			  
			  Node defParentNode=definserNode.getParentNode();
			  allsiblingHTfromDH=getallSiblinginHT(defParentNode);
//			  NodeList defParentNodeList=defParentNode.getChildNodes();			  
//			  for(int nod=0;nod<defParentNodeList.getLength();nod++){
//				  Node defNode=defParentNodeList.item(nod);
//				  if(defNode.getNodeType()==Node.ELEMENT_NODE){
//					  
//					  if(defNode.getNodeName().equals("ID") || defNode.getNodeName().equals("RootLevel_Name")){
//						  continue;
//					  }
//					  
//					
//					  Hashtable refsiblnHT=Globals.getAttributeNameandValHT(defNode);
//					  String refsiblngName=(String)refsiblnHT.get("value");
//					  allsiblingHTfromDH.put(sibdh, refsiblngName);
//					  sibdh++;
//				  } 
//			  }
			  
			  System.out.println("RH Tables (all Siblings) : :"+allsiblingHTfromRH);
			  System.out.println("DH Tables (all Siblings) : :"+allsiblingHTfromDH);
			  boolean sizenotequal=false;
			
			  Element setAtrributes4ParentHEL=(Element)defParentNode;
			  Element setAtrributes4CurrentChildHEL=(Element)definserNode;
			  for(int i=0; i<allsiblingHTfromDH.size();i++){
				  
				  String sibValue=(String)allsiblingHTfromDH.get(i);
				  
				 
				  
				  if(allsiblingHTfromRH.containsValue(sibValue)){
					  String failcode=setAtrributes4ParentHEL.getAttribute("RI_Failure_Code");
					  if(failcode.equals("") || failcode.equals("RI-002") || failcode.equals("RI-000") || (!ruleAL.contains(failcode))){
						  
						  String sibValue2="";
						  if(i==0){
						  sibValue2=(String)allsiblingHTfromDH.get(i);
						  }else {
							  int k=i-1;
							  sibValue2=(String)allsiblingHTfromDH.get(k);
						  }
						  
						  String msg=FailureDescriptionstr.replace("$currentNodeValue$", sibValue);
							 msg=msg.replace("$SiblingsValue$", sibValue2);
							 setAtrributes4ParentHEL.setAttribute("RI_Validation_Status", "Success");
							 setAtrributes4ParentHEL.setAttribute("RI_Failure_Code", "");
							 setAtrributes4ParentHEL.setAttribute("RI_Failure_Msg", "");
							 
							 System.out.println(" (**1 SET SUCCESS RI-002 **)  :"); 
						  
					  }
					  
					  String failcode1=setAtrributes4CurrentChildHEL.getAttribute("RI_Failure_Code");
					  if(failcode1.equals("") || failcode1.equals("RI-002") || failcode1.equals("RI-000") || (!ruleAL.contains(failcode))){
						  
				
						  setAtrributes4CurrentChildHEL.setAttribute("RI_Validation_Status", "Success");
						  setAtrributes4CurrentChildHEL.setAttribute("RI_Failure_Code", "");
						  setAtrributes4CurrentChildHEL.setAttribute("RI_Failure_Msg", "");
							 
							 System.out.println(" (**1 SET SUCCESS RI-002 set in current node**)  :"); 
						  
					  }
					  
				  }else{
					//set attributes here for this parent (DH)
					 
				
						  String sibValue2="";
						  if(i==0){
						  sibValue2=(String)allsiblingHTfromDH.get(i);
						  }else {
							  int k=i-1;
							  sibValue2=(String)allsiblingHTfromDH.get(k);
						  }
						  
						  String msg=FailureDescriptionstr.replace("$currentNodeValue$", sibValue);
							 msg=msg.replace("$SiblingsValue$", sibValue2);
							 setAtrributes4ParentHEL.setAttribute("RI_Validation_Status", "Failure");
							 setAtrributes4ParentHEL.setAttribute("RI_Failure_Code", "RI-002");
							 setAtrributes4ParentHEL.setAttribute("RI_Failure_Msg", msg);
							 
							 riValidationStatusHT.put("RI_Validation_Failure", "Failure"); // code change Menaka 14APR2014
						  
							 System.out.println(""+sibValue+"(1.**SET FAILURE ATTRIBUTES RI-002 **)  This sibling not available in DH");
						
							 sizenotequal=false;
					  
				  }
				  
			  }
			  
			  if(allsiblingHTfromRH.size()!=allsiblingHTfromDH.size()){sizenotequal=true;}
			  for(int l=0;l<allsiblingHTfromDH.size();l++ ){
				  if(sizenotequal){
						//set attributes here for this parent (DH)
					  String sibValue=(String)allsiblingHTfromDH.get(l);
						

							  String sibValue2="";
							  
							 if(allsiblingHTfromDH.size()>2)
							  sibValue2=(String)allsiblingHTfromDH.get(1);
							
							  
							  String msg=FailureDescriptionstr.replace("$currentNodeValue$", sibValue);
								 msg=msg.replace("$SiblingsValue$", sibValue2);
								 setAtrributes4ParentHEL.setAttribute("RI_Validation_Status", "Failure");
								 setAtrributes4ParentHEL.setAttribute("RI_Failure_Code", "RI-002");
								 setAtrributes4ParentHEL.setAttribute("RI_Failure_Msg", msg);  
								  System.out.println("2. "+sibValue+"(**SET ATTRIBUTES RI-002 **)  This sibling not available in DH");		
								  
								  riValidationStatusHT.put("RI_Validation_Failure", "Failure");   // code change Menaka 14APR2014
										  
										  sizenotequal=false;
									  }
				  
			  }
			 
			  
			
			  
		}else{
			//set attributes here for this parent (DH)
			 System.out.println(" (**SET ATTRIBUTES RI-002 **) No Siblinf  available in RH so Extra Parent node availble in DH : :"+defchildNodeValue);
			 Element setAtrributes4ChildDHEL=(Element)definserNode;
			 String msg=FailureDescriptionstr.replace("$childName$", defchildNodeValue);
			 Element setAtrributes4ChildDHEL2=(Element)definserNode.getParentNode();
			 msg=msg.replace("$parentName$", setAtrributes4ChildDHEL2.getAttribute("value"));
			 setAtrributes4ChildDHEL.setAttribute("RI_Validation_Status", "Failure");
			 setAtrributes4ChildDHEL.setAttribute("RI_Failure_Code", "RI-002");
			 setAtrributes4ChildDHEL.setAttribute("RI_Failure_Msg", msg);
			 
			 riValidationStatusHT.put("RI_Validation_Failure", "Failure");   // code change Menaka 14APR2014
		}
	  
  }catch (Exception e) {
		e.printStackTrace();
		
		riValidationStatusHT.put("RI_status", "Failure");
		riValidationStatusHT.put("RI_Details", e.getMessage());
	}
	
 System.out.println("Exiting: " + new Exception().getStackTrace()[0].getClassName() + "."
	            + new Exception().getStackTrace()[0].getMethodName());
	  
	
}

public static Hashtable getNodeFromRHHierarchyXML(String defsiblingNodeValue,Node refrootlevelNode) { 
	
	Hashtable nodeinfoHT=new Hashtable<>();
	 nodeinfoHT.put("found", false);
	  nodeinfoHT.put("Node", "NA");
	 try{
		  NodeList refrootNodeList=refrootlevelNode.getChildNodes();			  
		  for(int nod=0;nod<refrootNodeList.getLength();nod++){
			  Node refchnod=refrootNodeList.item(nod);
			  if(refchnod.getNodeType()==Node.ELEMENT_NODE){
				  String refchnodName=refchnod.getNodeName();
				  if(refchnodName.equals("ID") || refchnodName.equals("RootLevel_Name")){
					  continue;
				  }
				  
 if(refchnodName.contains("Level")){
					  
				  }else{ continue;}
				  
				  //first checking parent
				  Hashtable refchildHT=Globals.getAttributeNameandValHT(refchnod);
				  String refchName=(String)refchildHT.get("value");
				  
//				  System.out.println(defsiblingNodeValue+"--->"+refchName);
				  
				  
				  if(refchName.equals(defsiblingNodeValue)){
					  
					  System.out.println("");
					  System.out.println("********(DH)  Name is "+defsiblingNodeValue+" IS EQUAL  Name is RH ****"+refchName);
					 	
					
					  nodeinfoHT.put("found", true);
					  nodeinfoHT.put("Node", refchnod);
					  
					
					  return nodeinfoHT;
						 
					  
				  }else{
					  System.out.println("nodeinfoHT"+nodeinfoHT);
					 Boolean returnval= (boolean)nodeinfoHT.get("found");
					 if(returnval){
						 return nodeinfoHT;
					 }
//					  System.out.println("**Error******(DH)  Name is "+defsiblingNodeValue+" IS NOT EQUAL  Name is RH ****"+refchName);
					  
					  nodeinfoHT=getNodeFromRHHierarchyXML(defsiblingNodeValue,refchnod);
					 
					 
				  }
				  
				  
				  
				  
				  
			  }
			  
		  }
		  
		  
		 
		  
	  }catch (Exception e) {
			e.printStackTrace();
		}
	 
	 return nodeinfoHT;
	
}

public static Hashtable  getallSiblinginHT(Node nodeObj){
	
	System.out.println("Entering: " + new Exception().getStackTrace()[0].getClassName() + "."
            + new Exception().getStackTrace()[0].getMethodName());	
	
	Hashtable allsiblingHT=new Hashtable<>();
  try{
	  int sib=0;
	  NodeList refParentNodeList=nodeObj.getChildNodes();			  
	  for(int nod=0;nod<refParentNodeList.getLength();nod++){
		  Node refNode=refParentNodeList.item(nod);
		  if(refNode.getNodeType()==Node.ELEMENT_NODE){
			  
			  if(refNode.getNodeName().equals("ID") || refNode.getNodeName().equals("RootLevel_Name")){
				  continue;
			  }
			  
			  if(refNode.getNodeName().contains("Level")){
				  
			  }else{ continue;}
			 
			  Hashtable refsiblnHT=Globals.getAttributeNameandValHT(refNode);
			  String refsiblngName=(String)refsiblnHT.get("value");
			  System.out.println("refsiblngName124===>>>>"+refsiblngName);
  if(refsiblngName!=null&&!refsiblngName.equals("")){  // code change Menaka 06MAY2014
	  allsiblingHT.put(sib, refsiblngName);
	  sib++;
			  }
			 
		  } 
	  }

  }catch (Exception e) {
		e.printStackTrace();
	}
	
 System.out.println("Exiting: " + new Exception().getStackTrace()[0].getClassName() + "."
	            + new Exception().getStackTrace()[0].getMethodName());
 
 return allsiblingHT;
 
}	 

public static void rIParentWtExactChildrenProcess(Node createdNd,Node refNd,Node refRootNd,String FailureDescriptionstr,Hashtable riValidationStatusHT,ArrayList ruleAL) {
	try{
		Node createTemp = createdNd;
		Node refNdTemp = refNd;
		Node parCreateNd = createdNd.getParentNode();
		Node parRefNd = refNd.getParentNode();
		int k=0;
		System.out.println("parCreateNd.getNodeName()===>"+parCreateNd.getNodeName());
		System.out.println("refNd.getNodeName()===>"+refNd.getChildNodes().getLength()+"===>"+Globals.getAttrVal4AttrName(refNd, "value"));
		System.out.println("createTemp.getChildNodes().getLength()===>"+createdNd.getChildNodes().getLength()+"===>"+Globals.getAttrVal4AttrName(createdNd, "value"));
		for(int i=0;i<createdNd.getChildNodes().getLength();i++){
			
			if(createdNd.getChildNodes().item(i).getNodeType() == Node.ELEMENT_NODE){
				
				k++;
			}
		}
		if(k==0){
			 Element setAtrributes4ChildDHEL2=(Element)createdNd;
			  String failcode=setAtrributes4ChildDHEL2.getAttribute("RI_Failure_Code");
			  if(failcode.equals("") || failcode.equals("RI-003") || failcode.equals("RI-000") || (!ruleAL.contains(failcode))){
				  
			
				  Element setAtrributes4ChildDHEL=(Element)createdNd;
				  String msg=FailureDescriptionstr.replace("$childName$", setAtrributes4ChildDHEL2.getAttribute("value"));
					 msg=msg.replace("$parentName$", setAtrributes4ChildDHEL.getAttribute("value"));
					 setAtrributes4ChildDHEL.setAttribute("RI_Validation_Status", "Success");
					 setAtrributes4ChildDHEL.setAttribute("RI_Failure_Code", "");
					 setAtrributes4ChildDHEL.setAttribute("RI_Failure_Msg", "");
				  
			  }
		}
		int refNdCount = 0;
//		if(!parCreateNd.getNodeName().equals("Hierarchy_Level")){
//		if(!parCreateNd.getNodeName().equals("RootLevel")){
			for(int i=0;i<refNdTemp.getChildNodes().getLength();i++){
				if(refNdTemp.getChildNodes().item(i).getNodeType() == Node.ELEMENT_NODE){
					refNdCount++;
				}
			}
			if(refNdCount==0){
				Hashtable ht = getNodeFromRHHierarchyXML(Globals.getAttrVal4AttrName(parCreateNd, "value"), refRootNd);
				parRefNd = (Node)ht.get("Node");
				int DHleafCount = 0;
				int RHLeafCount = gettingLeafCount(parRefNd, 0);
				NodeList parCreateNdList = parCreateNd.getChildNodes();
				for(int i=0;i<parCreateNdList.getLength();i++){
					if(parCreateNdList.item(i).getNodeType() == Node.ELEMENT_NODE){
						
						 if(parCreateNdList.item(i).getNodeName().contains("Level")){
							  
						  }else{ continue;}
						
						if(parCreateNdList.item(i).getNodeName().equals("ID") || parCreateNdList.item(i).getNodeName().equals("RootLevel_Name")){
							continue;
						}else{
//							System.out.println("parCreateNdList.item(i)===> non"+parCreateNdList.item(i).getNodeName());
							DHleafCount++;
						}
					}
				}
				System.out.println("parRefNd===>"+Globals.getAttrVal4AttrName(parRefNd, "value"));
				System.out.println("DHleafCount===>"+DHleafCount);
				System.out.println("RHLeafCount===>"+RHLeafCount);
				if(DHleafCount == RHLeafCount){
					System.out.println("rule 3 is not violated");
					 Element setAtrributes4ChildDHEL2=(Element)createdNd;
					  String failcode=setAtrributes4ChildDHEL2.getAttribute("RI_Failure_Code");
					  if(failcode.equals("") || failcode.equals("RI-003") || failcode.equals("RI-000") || (!ruleAL.contains(failcode))){
						  
					
						  Element setAtrributes4ChildDHEL=(Element)createdNd.getParentNode();
						  String msg=FailureDescriptionstr.replace("$childName$", setAtrributes4ChildDHEL2.getAttribute("value"));
							 msg=msg.replace("$parentName$", setAtrributes4ChildDHEL.getAttribute("value"));
							 setAtrributes4ChildDHEL.setAttribute("RI_Validation_Status", "Success");
							 setAtrributes4ChildDHEL.setAttribute("RI_Failure_Code", "");
							 setAtrributes4ChildDHEL.setAttribute("RI_Failure_Msg", "");
						  
					  }
					
				}else{
					System.out.println("rule 3 is violated");
					//set attributes here for this parent (DH)
					 System.out.println(" (1**SET ATTRIBUTES RI-003 **) THIS NODE NOT AVAILABLE IN RH : :");
					 Element setAtrributes4ChildDHEL=(Element)createdNd.getParentNode();
					 Element setAtrributes4ChildDHEL2=(Element)createdNd;
					 String msg=FailureDescriptionstr.replace("$childName$", setAtrributes4ChildDHEL2.getAttribute("value"));
					 msg=msg.replace("$parentName$", setAtrributes4ChildDHEL.getAttribute("value"));
					 setAtrributes4ChildDHEL.setAttribute("RI_Validation_Status", "Failure");
					 setAtrributes4ChildDHEL.setAttribute("RI_Failure_Code", "RI-003");
					 setAtrributes4ChildDHEL.setAttribute("RI_Failure_Msg", msg);
					 riValidationStatusHT.put("RI_Validation_Failure", "Failure");   // code change Menaka 14APR2014
				}
			}else{
				int DHleafCount = 0;
				int RHLeafCount = 0;
				NodeList CreateNdList = createdNd.getChildNodes();
				NodeList RefNdList = refNd.getChildNodes();
				for(int i=0;i<CreateNdList.getLength();i++){
					if(CreateNdList.item(i).getNodeType() == Node.ELEMENT_NODE){
						System.out.println("parCreateNdList.item(i)===> non"+CreateNdList.item(i).getNodeName());
						System.out.println("parCreateNdList"+CreateNdList.item(i).getNodeName().contains("Level"));
						 if(CreateNdList.item(i).getNodeName().contains("Level")){
							 
						  }else{ continue;}
						
						if(CreateNdList.item(i).getNodeName().equals("ID") || CreateNdList.item(i).getNodeName().equals("RootLevel_Name")){
							continue;
						}else{
//							System.out.println("parCreateNdList.item(i)===> non"+parCreateNdList.item(i).getNodeName());
							DHleafCount++;
						}
					}
				}
				for(int i=0;i<RefNdList.getLength();i++){
					if(RefNdList.item(i).getNodeType() == Node.ELEMENT_NODE){
						System.out.println("RefNdList.item(i)===> non"+RefNdList.item(i).getNodeName());
						System.out.println("RefNdList"+RefNdList.item(i).getNodeName().contains("Level"));
						 if(RefNdList.item(i).getNodeName().contains("Level")){
							  
						  }else{ continue;}
						
						
						if(RefNdList.item(i).getNodeName().equals("ID") || RefNdList.item(i).getNodeName().equals("RootLevel_Name")){
							continue;
						}else{
//							System.out.println("parRefNdList.item(i)===> non"+parRefNdList.item(i).getNodeName());
							RHLeafCount++;
						}
					}
				}
				System.out.println("DHleafCount===>123"+DHleafCount);
				System.out.println("RHLeafCount===>123"+RHLeafCount);
				if(DHleafCount == RHLeafCount){
					System.out.println("rule 3 is not violated");
					
					 Element setAtrributes4ChildDHEL2=(Element)createdNd;
					  String failcode=setAtrributes4ChildDHEL2.getAttribute("RI_Failure_Code");
					  if(failcode.equals("") || failcode.equals("RI-003") || failcode.equals("RI-000") || (!ruleAL.contains(failcode))){
						  
					
						  Element setAtrributes4ChildDHEL=(Element)createdNd;
						  String msg=FailureDescriptionstr.replace("$childName$", setAtrributes4ChildDHEL2.getAttribute("value"));
							 msg=msg.replace("$parentName$", setAtrributes4ChildDHEL.getAttribute("value"));
							 setAtrributes4ChildDHEL.setAttribute("RI_Validation_Status", "Success");
							 setAtrributes4ChildDHEL.setAttribute("RI_Failure_Code", "");
							 setAtrributes4ChildDHEL.setAttribute("RI_Failure_Msg", "");
						  
					  }
				}else{
					System.out.println("rule 3 is violated");
					
					 System.out.println(" (2**SET ATTRIBUTES RI-003 **) THIS NODE NOT AVAILABLE IN RH : :");
					 System.out.println("createdNd.getNodeName()===>"+createdNd.getNodeName());
					 if(createdNd.getNodeName().equals("RootLevel")){
						 Element setAtrributes4ChildDHEL=(Element)createdNd;
						 Element setAtrributes4ChildDHEL2=(Element)createdNd;
						 String msg=FailureDescriptionstr.replace("$childName$", setAtrributes4ChildDHEL2.getAttribute("value"));
						 msg=msg.replace("$parentName$", setAtrributes4ChildDHEL.getAttribute("value"));
						 setAtrributes4ChildDHEL.setAttribute("RI_Validation_Status", "Failure");
						 setAtrributes4ChildDHEL.setAttribute("RI_Failure_Code", "RI-003");
						 setAtrributes4ChildDHEL.setAttribute("RI_Failure_Msg", msg);
						 riValidationStatusHT.put("RI_Validation_Failure", "Failure");   // code change Menaka 14APR2014
					 }else{
						 Element setAtrributes4ChildDHEL=(Element)createdNd;
						 Element setAtrributes4ChildDHEL2=(Element)createdNd;
						 String msg=FailureDescriptionstr.replace("$childName$", setAtrributes4ChildDHEL2.getAttribute("value"));
						 msg=msg.replace("$parentName$", setAtrributes4ChildDHEL.getAttribute("value"));
						 setAtrributes4ChildDHEL.setAttribute("RI_Validation_Status", "Failure");
						 setAtrributes4ChildDHEL.setAttribute("RI_Failure_Code", "RI-003");
						 setAtrributes4ChildDHEL.setAttribute("RI_Failure_Msg", msg);
						 riValidationStatusHT.put("RI_Validation_Failure", "Failure");   // code change Menaka 14APR2014
					 }
					
				}
				
				///////////////////////////
				if(!createdNd.getNodeName().equals("RootLevel")){
				int parDHleafCount = 0;
				int parRHLeafCount = 0;
				NodeList parCreateNdList = createdNd.getParentNode().getChildNodes();
				NodeList parRefNdList = refNd.getParentNode().getChildNodes();
				for(int i=0;i<parCreateNdList.getLength();i++){
					if(parCreateNdList.item(i).getNodeType() == Node.ELEMENT_NODE){
						System.out.println("parCreateNdList.item(i)===> non"+parCreateNdList.item(i).getNodeName());
						System.out.println("parCreateNdList"+parCreateNdList.item(i).getNodeName().contains("Level"));
						 if(parCreateNdList.item(i).getNodeName().contains("Level")){
							 
						  }else{ continue;}
						
						if(parCreateNdList.item(i).getNodeName().equals("ID") || parCreateNdList.item(i).getNodeName().equals("RootLevel_Name")){
							continue;
						}else{
//							System.out.println("parCreateNdList.item(i)===> non"+parCreateNdList.item(i).getNodeName());
							parDHleafCount++;
						}
					}
				}
				for(int i=0;i<parRefNdList.getLength();i++){
					if(parRefNdList.item(i).getNodeType() == Node.ELEMENT_NODE){
						System.out.println("parRefNdList.item(i)===> non"+parRefNdList.item(i).getNodeName());
						System.out.println("parRefNdList"+parRefNdList.item(i).getNodeName().contains("Level"));
						 if(parRefNdList.item(i).getNodeName().contains("Level")){
							  
						  }else{ continue;}
						
						
						if(parRefNdList.item(i).getNodeName().equals("ID") || parRefNdList.item(i).getNodeName().equals("RootLevel_Name")){
							continue;
						}else{
//							System.out.println("parRefNdList.item(i)===> non"+parRefNdList.item(i).getNodeName());
							parRHLeafCount++;
						}
					}
				}
				System.out.println("parDHleafCount===>123"+parDHleafCount);
				System.out.println("parRHLeafCount===>123"+parRHLeafCount);
				if(parDHleafCount == parRHLeafCount){
					System.out.println("rule 3 is not violated");
					
					 Element setAtrributes4ChildDHEL2=(Element)createdNd;
					  String failcode=setAtrributes4ChildDHEL2.getAttribute("RI_Failure_Code");
					  if(failcode.equals("") || failcode.equals("RI-003") || failcode.equals("RI-000") || (!ruleAL.contains(failcode))){
						  
					
						  Element setAtrributes4ChildDHEL=(Element)createdNd.getParentNode();
						  String msg=FailureDescriptionstr.replace("$childName$", setAtrributes4ChildDHEL2.getAttribute("value"));
							 msg=msg.replace("$parentName$", setAtrributes4ChildDHEL.getAttribute("value"));
							 setAtrributes4ChildDHEL.setAttribute("RI_Validation_Status", "Success");
							 setAtrributes4ChildDHEL.setAttribute("RI_Failure_Code", "");
							 setAtrributes4ChildDHEL.setAttribute("RI_Failure_Msg", "");
						  
					  }
				}else{
					System.out.println("rule 3 is violated");
					
					 System.out.println(" (2**SET ATTRIBUTES RI-003 **) THIS NODE NOT AVAILABLE IN RH : :");
					 System.out.println("createdNd.getNodeName()===>"+createdNd.getNodeName());
					
				}
				}
				///////////////////////////
				
			}
//		}else{
//			if(parCreateNd.getFirstChild().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getNextSibling().equals(createdNd)){
//				   System.out.println("Rule 3 is not violated");
//					 Element setAtrributes4ChildDHEL2=(Element)createdNd;
//					  String failcode=setAtrributes4ChildDHEL2.getAttribute("RI_Failure_Code");
//					  if(failcode.equals("") || failcode.equals("RI-003") || failcode.equals("RI-000")){
//						  
//					
//						  Element setAtrributes4ChildDHEL=(Element)createdNd.getParentNode();
//						  String msg=FailureDescriptionstr.replace("$childName$", setAtrributes4ChildDHEL2.getAttribute("value"));
//							 msg=msg.replace("$parentName$", setAtrributes4ChildDHEL.getAttribute("value"));
//							 setAtrributes4ChildDHEL.setAttribute("RI_Validation_Status", "Success");
//							 setAtrributes4ChildDHEL.setAttribute("RI_Failure_Code", "");
//							 setAtrributes4ChildDHEL.setAttribute("RI_Failure_Msg", "");
//						  
//					  }
//				   
//			}else{
//				for(int i=0;i<refNdTemp.getChildNodes().getLength();i++){
//					if(refNdTemp.getChildNodes().item(i).getNodeType() == Node.ELEMENT_NODE){
//						refNdCount++;
//					}
//				}
//				if(refNdCount==0){
//					int DHleafCount = 0;
//					int RHLeafCount = gettingLeafCount(parRefNd, 0);
//					NodeList parCreateNdList = parCreateNd.getChildNodes();
//					for(int i=0;i<parCreateNdList.getLength();i++){
//						if(parCreateNdList.item(i).getNodeType() == Node.ELEMENT_NODE){
//							
//							 if(parCreateNdList.item(i).getNodeName().contains("Level")){
//								  
//							  }else{ continue;}
//							
//							if(parCreateNdList.item(i).getNodeName().equals("ID") || parCreateNdList.item(i).getNodeName().equals("RootLevel_Name")){
//								continue;
//							}else{
////								System.out.println("parCreateNdList.item(i)===> non"+parCreateNdList.item(i).getNodeName());
//								DHleafCount++;
//							}
//						}
//					}
//					System.out.println("DHleafCount===>"+DHleafCount);
//					System.out.println("RHLeafCount===>"+RHLeafCount);
//					if(DHleafCount == RHLeafCount){
//						System.out.println("rule 3 is not violated");
//						 Element setAtrributes4ChildDHEL2=(Element)createdNd;
//						  String failcode=setAtrributes4ChildDHEL2.getAttribute("RI_Failure_Code");
//						  if(failcode.equals("") || failcode.equals("RI-003") || failcode.equals("RI-000")){
//							  
//						
//							  Element setAtrributes4ChildDHEL=(Element)createdNd.getParentNode();
//							  String msg=FailureDescriptionstr.replace("$childName$", setAtrributes4ChildDHEL2.getAttribute("value"));
//								 msg=msg.replace("$parentName$", setAtrributes4ChildDHEL.getAttribute("value"));
//								 setAtrributes4ChildDHEL.setAttribute("RI_Validation_Status", "Success");
//								 setAtrributes4ChildDHEL.setAttribute("RI_Failure_Code", "");
//								 setAtrributes4ChildDHEL.setAttribute("RI_Failure_Msg", "");
//							  
//						  }
//						
//					}else{
//						System.out.println("rule 3 is violated");
//						
//						System.out.println(" (3**SET ATTRIBUTES RI-003 **) THIS NODE NOT AVAILABLE IN RH : :");
//						 Element setAtrributes4ChildDHEL=(Element)createdNd.getParentNode();
//						 Element setAtrributes4ChildDHEL2=(Element)createdNd;
//						 String msg=FailureDescriptionstr.replace("$childName$", setAtrributes4ChildDHEL2.getAttribute("value"));
//						 msg=msg.replace("$parentName$", setAtrributes4ChildDHEL.getAttribute("value"));
//						 setAtrributes4ChildDHEL.setAttribute("RI_Validation_Status", "Failure");
//						 setAtrributes4ChildDHEL.setAttribute("RI_Failure_Code", "RI-003");
//						 setAtrributes4ChildDHEL.setAttribute("RI_Failure_Msg", msg);
//					}
//				}else{
//					int DHleafCount = 0;
//					int RHLeafCount = 0;
//					NodeList parCreateNdList = createdNd.getChildNodes();
//					NodeList parRefNdList = refNd.getChildNodes();
//					for(int i=0;i<parCreateNdList.getLength();i++){
//						if(parCreateNdList.item(i).getNodeType() == Node.ELEMENT_NODE){
//							
//							 if(parCreateNdList.item(i).getNodeName().contains("Level")){
//								  
//							  }else{ continue;}
//							
//							if(parCreateNdList.item(i).getNodeName().equals("ID") || parCreateNdList.item(i).getNodeName().equals("RootLevel_Name")){
//								continue;
//							}else{
////								System.out.println("parCreateNdList.item(i)===> non"+parCreateNdList.item(i).getNodeName());
//								DHleafCount++;
//							}
//						}
//					}
//					for(int i=0;i<parRefNdList.getLength();i++){
//						if(parRefNdList.item(i).getNodeType() == Node.ELEMENT_NODE){
//							
//							 if(parRefNdList.item(i).getNodeName().contains("Level")){
//								  
//							  }else{ continue;}
//							
//							if(parRefNdList.item(i).getNodeName().equals("ID") || parRefNdList.item(i).getNodeName().equals("RootLevel_Name")){
//								continue;
//							}else{
////								System.out.println("parRefNdList.item(i)===> non"+parRefNdList.item(i).getNodeName());
//								RHLeafCount++;
//							}
//						}
//					}
//					System.out.println("parCreateNd===> non"+Globals.getAttrVal4AttrName(parCreateNd, "value"));
//					System.out.println("parRefNd===> non"+Globals.getAttrVal4AttrName(parRefNd, "value"));
//					
//					System.out.println("DHleafCount===> non"+DHleafCount);
//					System.out.println("RHLeafCount===> non"+RHLeafCount);
//					if(DHleafCount == RHLeafCount){
//						System.out.println("rule 3 is not violated");
//						 Element setAtrributes4ChildDHEL2=(Element)createdNd;
//						  String failcode=setAtrributes4ChildDHEL2.getAttribute("RI_Failure_Code");
//						  if(failcode.equals("") || failcode.equals("RI-003") || failcode.equals("RI-000")){
//							  
//						
//							  Element setAtrributes4ChildDHEL=(Element)createdNd.getParentNode();
//							  String msg=FailureDescriptionstr.replace("$childName$", setAtrributes4ChildDHEL2.getAttribute("value"));
//								 msg=msg.replace("$parentName$", setAtrributes4ChildDHEL.getAttribute("value"));
//								 setAtrributes4ChildDHEL.setAttribute("RI_Validation_Status", "Success");
//								 setAtrributes4ChildDHEL.setAttribute("RI_Failure_Code", "");
//								 setAtrributes4ChildDHEL.setAttribute("RI_Failure_Msg", "");
//							  
//						  }
//						
//					}else{
//						System.out.println("rule 3 is violated");
//						
//						System.out.println(" (4**SET ATTRIBUTES RI-003 **) THIS NODE NOT AVAILABLE IN RH : :");
//						 Element setAtrributes4ChildDHEL=(Element)createdNd.getParentNode();
//						 Element setAtrributes4ChildDHEL2=(Element)createdNd;
//						 String msg=FailureDescriptionstr.replace("$childName$", setAtrributes4ChildDHEL2.getAttribute("value"));
//						 msg=msg.replace("$parentName$", setAtrributes4ChildDHEL.getAttribute("value"));
//						 setAtrributes4ChildDHEL.setAttribute("RI_Validation_Status", "Failure");
//						 setAtrributes4ChildDHEL.setAttribute("RI_Failure_Code", "RI-003");
//						 setAtrributes4ChildDHEL.setAttribute("RI_Failure_Msg", msg);
//					}
//				}
//			}
//		}
//	}else{
//		System.out.println("Rule 3 is not violated");
//		Element setAtrributes4ChildDHEL2=(Element)createdNd;
//		  String failcode=setAtrributes4ChildDHEL2.getAttribute("RI_Failure_Code");
//		  if(failcode.equals("") || failcode.equals("RI-003") || failcode.equals("RI-000")){
//			  
//		
//			  Element setAtrributes4ChildDHEL=(Element)createdNd.getParentNode();
//			  String msg=FailureDescriptionstr.replace("$childName$", setAtrributes4ChildDHEL2.getAttribute("value"));
//				 msg=msg.replace("$parentName$", setAtrributes4ChildDHEL.getAttribute("value"));
//				 setAtrributes4ChildDHEL.setAttribute("RI_Validation_Status", "Success");
//				 setAtrributes4ChildDHEL.setAttribute("RI_Failure_Code", "");
//				 setAtrributes4ChildDHEL.setAttribute("RI_Failure_Msg", "");
//			  
//		  }
//	}
		
	}catch (Exception e) {
		// TODO: handle exception
		e.printStackTrace();
		
		riValidationStatusHT.put("RI_status", "Failure");
		riValidationStatusHT.put("RI_Details", e.getMessage());
	}
	
}


public static int gettingLeafCount(Node nd,int k){
	try{
		int m=0;
		NodeList ndlList = nd.getChildNodes();
		for(int i=0;i<ndlList.getLength();i++){
			if(ndlList.item(i).getNodeType() == Node.ELEMENT_NODE){
				Node nd1 = ndlList.item(i);
				for(int j=0;j<nd1.getChildNodes().getLength();j++){
					if(nd1.getChildNodes().item(j).getNodeType() == Node.ELEMENT_NODE){
						m++;
					}
				}
				if(m==0){
					k++;
				}else{
					k=gettingLeafCount(nd1,k);
				}
			}
		}
		return k;
	}catch (Exception e) {
		// TODO: handle exception
		e.printStackTrace();
		return k;
	}
}


public static void rIaddNodewthSbiling(String defchildNodeValue,Node refrootlevelNode,Node definserNode,String FailureDescriptionstr,Hashtable riValidationStatusHT,ArrayList ruleAL){ 
	
	System.out.println("Entering: " + new Exception().getStackTrace()[0].getClassName() + "."
            + new Exception().getStackTrace()[0].getMethodName());	
	
	Hashtable allsiblingHTfromRH=new Hashtable<>();
	Hashtable allsiblingHTfromDH=new Hashtable<>();
  try{
	  
	  int sib=0;
	  int sibdh=0;
	  Hashtable nodeinfoHT=getNodeFromRHHierarchyXML(defchildNodeValue,refrootlevelNode);
		Boolean isparentAvalble=(Boolean)nodeinfoHT.get("found");
	    System.out.println("Parent Available in Ref Hierarchy :"+nodeinfoHT);
		if(isparentAvalble){
			
			Node sibNode=(Node)nodeinfoHT.get("Node");
			rIaddNodewthSbilingProcess(definserNode,sibNode,FailureDescriptionstr,riValidationStatusHT,ruleAL);
			
			  
		}else{
			//set attributes here for this parent (DH)
			 System.out.println(" (**SET ATTRIBUTES RI-004 **) THIS NODE NOT AVAILABLE IN RH : :"+defchildNodeValue);
			 Element setAtrributes4ChildDHEL=(Element)definserNode;
			 String msg=FailureDescriptionstr.replace("$currentNodeValue$", defchildNodeValue);
			 Element setAtrributes4ChildDHEL2=(Element)definserNode.getParentNode();
			 msg=msg.replace("$SiblingsValue$", "");
			 setAtrributes4ChildDHEL.setAttribute("RI_Validation_Status", "Failure");
			 setAtrributes4ChildDHEL.setAttribute("RI_Failure_Code", "RI-004");
			 setAtrributes4ChildDHEL.setAttribute("RI_Failure_Msg", msg);
		
			 riValidationStatusHT.put("RI_Validation_Failure", "Failure");   // code change Menaka 14APR2014
			 
		}
	  
  }catch (Exception e) {
		e.printStackTrace();
		riValidationStatusHT.put("RI_status", "Failure");
		riValidationStatusHT.put("RI_Details", e.getMessage());
	}
	
 System.out.println("Exiting: " + new Exception().getStackTrace()[0].getClassName() + "."
	            + new Exception().getStackTrace()[0].getMethodName());
	  
	
}


public static void rIaddNodewthSbilingProcess(Node createdNd,Node refNd,String FailureDescriptionstr,Hashtable riValidationStatusHT,ArrayList ruleAL) {
	try{
		String createdValue = "";
		   String refValue = "";
		   
		   Node createTemp = createdNd;
		   Node refNdTemp = refNd;
		   Node parCreateNd = createdNd.getParentNode();
//		   Node parRefNd = refNd.getParentNode();
		   String validSibName = "";
		   System.out.println("parCreateNd==>1"+parCreateNd);
		   if(!parCreateNd.getNodeName().equals("Hierarchy_Level")){
		   if(!parCreateNd.getNodeName().equals("RootLevel")){
			   System.out.println("parCreateNd.getFirstChild()==>1"+Globals.getAttrVal4AttrName(parCreateNd.getFirstChild().getNextSibling(),"ID"));
			   System.out.println("createdNd==>1"+Globals.getAttrVal4AttrName(createdNd,"ID"));
//			   System.out.println("parCreateNd.equals(createdNd)==>1"+parCreateNd.equals(createdNd));
			   System.out.println("parCreateNd.getFirstChild().getNextSibling()==>"+parCreateNd.getFirstChild().getNextSibling());
			   if(parCreateNd.getFirstChild().getNextSibling().equals(createdNd)){
			   //no validation
			   System.out.println("Rule 4 is not violated");
			   Element setAtrributes4ChildDHEL2=(Element)createdNd;
				  String failcode=setAtrributes4ChildDHEL2.getAttribute("RI_Failure_Code");
				  if(failcode.equals("") || failcode.equals("RI-004") || failcode.equals("RI-000") || (!ruleAL.contains(failcode))){
					  
				
					  Element setAtrributes4ChildDHEL=(Element)createdNd.getParentNode();
					  String msg=FailureDescriptionstr.replace("$currentNodeValue$", setAtrributes4ChildDHEL2.getAttribute("value"));
						 msg=msg.replace("$SiblingsValue$", setAtrributes4ChildDHEL.getAttribute("value"));
						 setAtrributes4ChildDHEL2.setAttribute("RI_Validation_Status", "Success");
						 setAtrributes4ChildDHEL2.setAttribute("RI_Failure_Code", "");
						 setAtrributes4ChildDHEL2.setAttribute("RI_Failure_Msg", "");
					  
				  }
		   }else{
//			   createdValue = createdNd.getNodeName();
			   refValue = refNd.getNodeName();
			   Hashtable siblingHT = new Hashtable<>();
			   NodeList parentList = createdNd.getParentNode().getChildNodes();
			   int k=0;
			   
			   for(int i=0;i<parentList.getLength();i++){
				   if(parentList.item(i).getNodeType() == Node.ELEMENT_NODE){
//					   siblingHT.put(k, parentList.item(i));
					   if(!Globals.getAttrVal4AttrName(parentList.item(i), "RI_Validation_Status").equals("Failure") || Globals.getAttrVal4AttrName(parentList.item(i), "RI_Validation_Status").equals("")){
			   				createdValue = parentList.item(i).getNodeName();
//						   createdValue = createdNd.getPreviousSibling().getPreviousSibling().getNodeName();	//check attribute to find valid sibling
			   				validSibName = Globals.getAttrVal4AttrName(parentList.item(i),"value");
						   break;
					   }
				   }
			   }
			   
			   System.out.println("createdValue==>1"+createdValue);
			     System.out.println("refValue==>1"+refValue);
			   if(!createdValue.equals("")){
			   if(createdValue.equals(refValue)){
				   //set attribute as ok
				   System.out.println("Rule 4 is not violated");
				   Element setAtrributes4ChildDHEL2=(Element)createdNd;
					  String failcode=setAtrributes4ChildDHEL2.getAttribute("RI_Failure_Code");
					  if(failcode.equals("") || failcode.equals("RI-004") || failcode.equals("RI-000") || (!ruleAL.contains(failcode))){
						  
					
						  Element setAtrributes4ChildDHEL=(Element)createdNd.getParentNode();
						  String msg=FailureDescriptionstr.replace("$currentNodeValue$", setAtrributes4ChildDHEL2.getAttribute("value"));
							 msg=msg.replace("$SiblingsValue$", setAtrributes4ChildDHEL.getAttribute("value"));
							 setAtrributes4ChildDHEL2.setAttribute("RI_Validation_Status", "Success");
							 setAtrributes4ChildDHEL2.setAttribute("RI_Failure_Code", "");
							 setAtrributes4ChildDHEL2.setAttribute("RI_Failure_Msg", "");
						  
					  }
			   }else{
				   //set attribute as false
				   System.out.println("Rule 4 is violated");
				   Element setAtrributes4ChildDHEL=(Element)createdNd;
				   System.out.println("1 (**SET ATTRIBUTES RI-004 **) THIS NODE NOT AVAILABLE IN RH : :"+setAtrributes4ChildDHEL.getAttribute("value"));
					 
					 String msg=FailureDescriptionstr.replace("$currentNodeValue$", setAtrributes4ChildDHEL.getAttribute("value"));
//					 Element setAtrributes4ChildDHEL2=(Element)definserNode.getParentNode();
					 msg=msg.replace("$SiblingsValue$", validSibName);
					 setAtrributes4ChildDHEL.setAttribute("RI_Validation_Status", "Failure");
					 setAtrributes4ChildDHEL.setAttribute("RI_Failure_Code", "RI-004");
					 setAtrributes4ChildDHEL.setAttribute("RI_Failure_Msg", msg);
					 riValidationStatusHT.put("RI_Validation_Failure", "Failure");   // code change Menaka 14APR2014
			   }
			   }else{
				   //alertmess
				   System.out.println("Rule 4 is violated");
				   Element setAtrributes4ChildDHEL=(Element)createdNd;
				   System.out.println("1 (**SET ATTRIBUTES RI-004 **) RI-004: There is no valid sibling to compare. : :"+setAtrributes4ChildDHEL.getAttribute("value"));
					 
					 String msg="RI-004: There is no valid sibling to compare.";
//					 Element setAtrributes4ChildDHEL2=(Element)definserNode.getParentNode();
//					 msg=msg.replace("$SiblingsValue$", validSibName);
					 setAtrributes4ChildDHEL.setAttribute("RI_Validation_Status", "Failure");
					 setAtrributes4ChildDHEL.setAttribute("RI_Failure_Code", "RI-004");
					 setAtrributes4ChildDHEL.setAttribute("RI_Failure_Msg", msg);
					 riValidationStatusHT.put("RI_Validation_Failure", "Failure");   // code change Menaka 14APR2014
			   }
		   }
		   }else{
//			   System.out.println("Rule 4 is not violated"+parCreateNd.getFirstChild().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getNextSibling());
			   if(parCreateNd.getFirstChild().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getNextSibling().equals(createdNd)){
				   System.out.println("Rule 4 is not violated");
				   Element setAtrributes4ChildDHEL2=(Element)createdNd;
					  String failcode=setAtrributes4ChildDHEL2.getAttribute("RI_Failure_Code");
					  if(failcode.equals("") || failcode.equals("RI-004") || failcode.equals("RI-000") || (!ruleAL.contains(failcode))){
						  
					
						  Element setAtrributes4ChildDHEL=(Element)createdNd.getParentNode();
						  String msg=FailureDescriptionstr.replace("$currentNodeValue$", setAtrributes4ChildDHEL2.getAttribute("value"));
							 msg=msg.replace("$SiblingsValue$", setAtrributes4ChildDHEL.getAttribute("value"));
							 setAtrributes4ChildDHEL2.setAttribute("RI_Validation_Status", "Success");
							 setAtrributes4ChildDHEL2.setAttribute("RI_Failure_Code", "");
							 setAtrributes4ChildDHEL2.setAttribute("RI_Failure_Msg", "");
						  
					  }
			   }else{
//				   createdValue = createdNd.getNodeName();
				   refValue = refNd.getNodeName();
				   NodeList parentList = createdNd.getParentNode().getChildNodes();
				   int k=0;
				   for(int i=0;i<parentList.getLength();i++){
					   if(parentList.item(i).getNodeType() == Node.ELEMENT_NODE){
//						   siblingHT.put(k, parentList.item(i));
							 if(parentList.item(i).getNodeName().contains("Level")){
								  
							  }else{ continue;}
						   
						   if(parentList.item(i).getNodeName().equals("ID") || parentList.item(i).getNodeName().equals("RootLevel")){
							   continue;
						   }
						   if(Globals.getAttrVal4AttrName(parentList.item(i), "RI_Validation_Status").equals("Success")){
				   			   createdValue = parentList.item(i).getNodeName();
				   			validSibName = Globals.getAttrVal4AttrName(parentList.item(i), "value");
//							   createdValue = createdNd.getPreviousSibling().getPreviousSibling().getNodeName();	//check attribute to find valid sibling
							   break;
						   }
					   }
				   }
				   createdValue = createdNd.getPreviousSibling().getPreviousSibling().getNodeName();	//check attribute to find valid sibling
				   System.out.println("createdValue==>1"+createdValue);
				     System.out.println("refValue==>1"+refValue);
				     if(!createdValue.equals("")){
				   if(createdValue.equals(refValue)){
					   //set attribute as ok
					   
					   System.out.println("Rule 4 is not violated");
					   Element setAtrributes4ChildDHEL2=(Element)createdNd;
						  String failcode=setAtrributes4ChildDHEL2.getAttribute("RI_Failure_Code");
						  if(failcode.equals("") || failcode.equals("RI-004") || failcode.equals("RI-000") || (!ruleAL.contains(failcode))){
							  
						
							  Element setAtrributes4ChildDHEL=(Element)createdNd.getParentNode();
							  String msg=FailureDescriptionstr.replace("$currentNodeValue$", setAtrributes4ChildDHEL2.getAttribute("value"));
								 msg=msg.replace("$SiblingsValue$", setAtrributes4ChildDHEL.getAttribute("value"));
								 setAtrributes4ChildDHEL2.setAttribute("RI_Validation_Status", "Success");
								 setAtrributes4ChildDHEL2.setAttribute("RI_Failure_Code", "");
								 setAtrributes4ChildDHEL2.setAttribute("RI_Failure_Msg", "");
							  
						  }
				   }else{
					   //set attribute as false
					   System.out.println("Rule 4 is violated");
					   Element setAtrributes4ChildDHEL=(Element)createdNd;
					   System.out.println("2 (**SET ATTRIBUTES RI-004 **) THIS NODE NOT AVAILABLE IN RH : :"+setAtrributes4ChildDHEL.getAttribute("value"));
						 
						 String msg=FailureDescriptionstr.replace("$currentNodeValue$", setAtrributes4ChildDHEL.getAttribute("value"));
//						 Element setAtrributes4ChildDHEL2=(Element)definserNode.getParentNode();
						 msg=msg.replace("$SiblingsValue$", validSibName);
						 setAtrributes4ChildDHEL.setAttribute("RI_Validation_Status", "Failure");
						 setAtrributes4ChildDHEL.setAttribute("RI_Failure_Code", "RI-004");
						 setAtrributes4ChildDHEL.setAttribute("RI_Failure_Msg", msg);
						 riValidationStatusHT.put("RI_Validation_Failure", "Failure");   // code change Menaka 14APR2014
				   }
			   }
			   else {
				   System.out.println("Rule 4 is violated");
				   Element setAtrributes4ChildDHEL=(Element)createdNd;
				   System.out.println("1 (**SET ATTRIBUTES RI-004 **) RI-004: There is no valid sibling to compare. : :"+setAtrributes4ChildDHEL.getAttribute("value"));
					 
					 String msg="RI-004: There is no valid sibling to compare.";
//					 Element setAtrributes4ChildDHEL2=(Element)definserNode.getParentNode();
//					 msg=msg.replace("$SiblingsValue$", validSibName);
					 setAtrributes4ChildDHEL.setAttribute("RI_Validation_Status", "Failure");
					 setAtrributes4ChildDHEL.setAttribute("RI_Failure_Code", "RI-004");
					 setAtrributes4ChildDHEL.setAttribute("RI_Failure_Msg", msg);
					 riValidationStatusHT.put("RI_Validation_Failure", "Failure");   // code change Menaka 14APR2014
			}
			   //no validation
		   }
		   }
	}else{
		System.out.println("Rule 4 is not violated");
		Element setAtrributes4ChildDHEL2=(Element)createdNd;
		  String failcode=setAtrributes4ChildDHEL2.getAttribute("RI_Failure_Code");
		  if(failcode.equals("") || failcode.equals("RI-004") || failcode.equals("RI-000") || (!ruleAL.contains(failcode))){
			  
		
			  Element setAtrributes4ChildDHEL=(Element)createdNd.getParentNode();
			  String msg=FailureDescriptionstr.replace("$currentNodeValue$", setAtrributes4ChildDHEL2.getAttribute("value"));
				 msg=msg.replace("$SiblingsValue$", setAtrributes4ChildDHEL.getAttribute("value"));
				 setAtrributes4ChildDHEL2.setAttribute("RI_Validation_Status", "Success");
				 setAtrributes4ChildDHEL2.setAttribute("RI_Failure_Code", "");
				 setAtrributes4ChildDHEL2.setAttribute("RI_Failure_Msg", "");
			  
		  }
	}
	}catch (Exception e) {
		// TODO: handle exception
		e.printStackTrace();
		
		riValidationStatusHT.put("RI_status", "Failure");
		riValidationStatusHT.put("RI_Details", e.getMessage());
	}
	
	
}



public static void rIaddChildNodeIgnoreParentSbiling(Document hierxmlDoc,String DependHierarchy_ID,String defchildNodeValue,Node refrootlevelNode,Node definserNode,String FailureDescriptionstr,Hashtable riValidationStatusHT,ArrayList ruleAL){ 
	
	System.out.println("Entering: " + new Exception().getStackTrace()[0].getClassName() + "."
            + new Exception().getStackTrace()[0].getMethodName());	
	
	Hashtable allsiblingHTfromRH=new Hashtable<>();
	Hashtable allsiblingHTfromRH1=new Hashtable<>();
	Hashtable allsiblingHTfromDH=new Hashtable<>();
  try{
	  
	  int sib=0;
	  int sibdh=0;
	  Hashtable nodeinfoHT=getNodeFromRHHierarchyXML(defchildNodeValue,refrootlevelNode);
		Boolean isparentAvalble=(Boolean)nodeinfoHT.get("found");
	    System.out.println("Parent Available in Ref Hierarchy :"+nodeinfoHT);
		if(isparentAvalble){
			
			Node refNode=(Node)nodeinfoHT.get("Node");
			
			Node defParentNode=null;
			String nodename=definserNode.getNodeName();
			if(nodename.equals("RootLevel")){  
				defParentNode=Globals.getNodeByAttrVal(hierxmlDoc, "RootLevel_Name", "ID", DependHierarchy_ID);		
			}else{
				defParentNode=definserNode.getParentNode();
			}			 
			allsiblingHTfromDH=getallSiblinginHT(defParentNode);
			System.out.println("allsiblingHTfromDH---->"+allsiblingHTfromDH);
			
			Node refgrandParentNode=refNode.getParentNode().getParentNode();
			allsiblingHTfromRH=getallSiblinginHT(refgrandParentNode);
			System.out.println("allsiblingHTfromRH---->"+allsiblingHTfromRH);
			
			Hashtable commenhashHT=new Hashtable();
			Node prevsNode=definserNode.getPreviousSibling().getPreviousSibling();
			if((allsiblingHTfromDH.size()>1)  && (prevsNode!=null)  && (!prevsNode.getNodeName().equals("RootLevel_Name")) && (prevsNode.getNodeType()==Node.ELEMENT_NODE)){
				
//				Hashtable curnodeHT=Globals.getAttributeNameandValHT(definserNode);
//				String curnodevalue=(String)curnodeHT.get("value");
//				System.out.println("definserNode---->"+definserNode.getNodeName()+"curnodevalue  "+curnodevalue);
				
				
//				Node prevsNode=definserNode.getPreviousSibling().getPreviousSibling();
				System.out.println("prevsNode---->"+prevsNode.getNodeName());
				Hashtable prevsnodeHT=Globals.getAttributeNameandValHT(prevsNode);
//				System.out.println("nodeHT---->"+prevsnodeHT);
				String prevsnodevalue=(String)prevsnodeHT.get("value");
				Hashtable refnodeinfoHT=getNodeFromRHHierarchyXML(prevsnodevalue,refrootlevelNode);
				Boolean isparentAvalble1=(Boolean)nodeinfoHT.get("found");
		    System.out.println("Parent Available in Ref Hierarchy :"+nodeinfoHT);
		    Node refParentNode=null;
			if(isparentAvalble1 && !refnodeinfoHT.get("Node").equals("NA")){   // code change Menaka 14APR2014
				
				 refParentNode=(Node)refnodeinfoHT.get("Node");
				
			}else{
				
				//set attributes here for this parent (DH)
				 System.out.println(" (**0 SET ATTRIBUTES RI-005 **) THIS NODE NOT AVAILABLE IN RH : :"+defchildNodeValue);
				
			}
				
			
			if(refParentNode!=null){
				Node refgrandParent1Node=refParentNode.getParentNode().getParentNode();
				allsiblingHTfromRH1=getallSiblinginHT(refgrandParent1Node);
				System.out.println("allsiblingHTfromRH1---->"+allsiblingHTfromRH1);
				
				
				 boolean isequal=allsiblingHTfromRH.equals(allsiblingHTfromRH1) ;
				 System.out.println("Value isequal  ---->"+isequal);
				 Element setAtrributes4ChildDHEL=(Element)definserNode;
				 if(!isequal){
					 //set Attributes here
					 
					//set attributes here for this parent (DH)
					 System.out.println(" (**1 SET ATTRIBUTES RI-005 **) THIS NODE NOT AVAILABLE IN RH : :"+defchildNodeValue);
					
					 String msg=FailureDescriptionstr.replace("$currentNodeValue$", defchildNodeValue);
					 msg=msg.replace("$SiblingsValue$", prevsnodevalue);
					 setAtrributes4ChildDHEL.setAttribute("RI_Validation_Status", "Failure");
					 setAtrributes4ChildDHEL.setAttribute("RI_Failure_Code", "RI-005");
					 setAtrributes4ChildDHEL.setAttribute("RI_Failure_Msg", msg);
					 riValidationStatusHT.put("RI_Validation_Failure", "Failure");   // code change Menaka 14APR2014
				 }else{
					   System.out.println(" (**1 SET SUCCESS RI-005 **)  :"+defchildNodeValue);
					     String failcode=setAtrributes4ChildDHEL.getAttribute("RI_Failure_Code");
					      if(failcode.equals("") || failcode.equals("RI-005") || failcode.equals("RI-000") || (!ruleAL.contains(failcode))){
					       setAtrributes4ChildDHEL.setAttribute("RI_Validation_Status", "Success");
					       setAtrributes4ChildDHEL.setAttribute("RI_Failure_Code", "");
					       setAtrributes4ChildDHEL.setAttribute("RI_Failure_Msg", "");
					      } 
				 }
			}
				

				
			}
			
			  
		}else{
			//set attributes here for this parent (DH)
			 System.out.println(" (**2.SET ATTRIBUTES RI-005 **) THIS NODE NOT AVAILABLE IN RH : :"+defchildNodeValue);
			 Element setAtrributes4ChildDHEL=(Element)definserNode;
			 String msg=FailureDescriptionstr.replace("$currentNodeValue$", defchildNodeValue);
			 Element setAtrributes4ChildDHEL2=(Element)definserNode.getParentNode();
			 msg=msg.replace("$SiblingsValue$", "");
			 setAtrributes4ChildDHEL.setAttribute("RI_Validation_Status", "Failure");
			 setAtrributes4ChildDHEL.setAttribute("RI_Failure_Code", "RI-005");
			 setAtrributes4ChildDHEL.setAttribute("RI_Failure_Msg", msg);
			 riValidationStatusHT.put("RI_Validation_Failure", "Failure");   // code change Menaka 14APR2014
			 
		}
	  
  }catch (Exception e) {
		e.printStackTrace();
		
		riValidationStatusHT.put("RI_status", "Failure");
		riValidationStatusHT.put("RI_Details", e.getMessage());
	}
	
 System.out.println("Exiting: " + new Exception().getStackTrace()[0].getClassName() + "."
	            + new Exception().getStackTrace()[0].getMethodName());
	  
	
}






public static void riLowChildRolledupToRoot(Document hierxmlDoc,String DependHierarchy_ID,String defchildNodeValue,Node refrootlevelNode,Node definserNode,String FailureDescriptionstr,Hashtable riValidationStatusHT,ArrayList ruleAL){ 
	
	System.out.println("Entering: " + new Exception().getStackTrace()[0].getClassName() + "."
            + new Exception().getStackTrace()[0].getMethodName());	
	
	Hashtable allsiblingHTfromRH=new Hashtable<>();
	Hashtable allsiblingHTfromRH1=new Hashtable<>();
	Hashtable allsiblingHTfromDH=new Hashtable<>();
  try{
	  
	  int sib=0;
	  int sibdh=0;
	  Hashtable nodeinfoHT=getNodeFromRHHierarchyXML(defchildNodeValue,refrootlevelNode);
		Boolean isparentAvalble=(Boolean)nodeinfoHT.get("found");
	    System.out.println("Parent Available in Ref Hierarchy :"+nodeinfoHT);
		if(isparentAvalble){
			
			Node refNode=(Node)nodeinfoHT.get("Node");
			///////////start  
			int lenght=refNode.getChildNodes().getLength();
			
			NodeList nlist=refNode.getChildNodes();
			int noOfch=0;
			for(int nod=0; nod<nlist.getLength();nod++){
				Node n=nlist.item(nod);
				if(n.getNodeType()==Node.ELEMENT_NODE){
					noOfch++;
				}
				
			}
			
			System.out.println("lenght============"+lenght);
			lenght=noOfch;
			System.out.println("lenght---->>"+lenght);
			
			if(lenght==0){// defrootlevelNode is  leaf Node so no more child is available
				 System.out.println("this is  boottom level Node ---->"+defchildNodeValue);
				 Element setAtrributes4ChildDHEL=(Element)definserNode;
				 
				 
				 Node defparenNode=definserNode.getParentNode();
					Hashtable parentnameHT=Globals.getAttributeNameandValHT(defparenNode);
					String defparenNodevalue=(String)parentnameHT.get("value");
					 System.out.println("defparenNodevalue ---->"+defparenNodevalue);
					Hashtable rootlevelHT=new Hashtable();
				  rootlevelHT=geRootLevelParentHT(rootlevelHT,refNode);
				 System.out.println("rootlevelHT ---->"+rootlevelHT);
				if(rootlevelHT.containsValue(defparenNodevalue)){
					 System.out.println("its contain ---->"+defchildNodeValue);
					 
					 System.out.println(" (**1 SET SUCCESS RI-006 **)  :");
				     String failcode=setAtrributes4ChildDHEL.getAttribute("RI_Failure_Code");
				      if(failcode.equals("") || failcode.equals("RI-006") || failcode.equals("RI-000") || (!ruleAL.contains(failcode))){
				       setAtrributes4ChildDHEL.setAttribute("RI_Validation_Status", "Success");
				       setAtrributes4ChildDHEL.setAttribute("RI_Failure_Code", "");
				       setAtrributes4ChildDHEL.setAttribute("RI_Failure_Msg", "");
				      }
					 
				}else{
					// set attributes as error
					
					System.out.println(" (**2.SET ATTRIBUTES RI-006 **) THIS NODE NOT AVAILABLE IN RH : :"+defchildNodeValue);
					 String msg=FailureDescriptionstr.replace("$childName$", defchildNodeValue);
					 msg=msg.replace("$ParentName$", "");
					 setAtrributes4ChildDHEL.setAttribute("RI_Validation_Status", "Failure");
					 setAtrributes4ChildDHEL.setAttribute("RI_Failure_Code", "RI-006");
					 setAtrributes4ChildDHEL.setAttribute("RI_Failure_Msg", msg);
					 riValidationStatusHT.put("RI_Validation_Failure", "Failure");   // code change Menaka 14APR2014
				}
				
				
			}else{ // this node has some children
				
				 Element setAtrributes4ChildDHEL=(Element)definserNode;
				
				Node rootlevelParent=geRootLevelNodeObj(refNode);
				allsiblingHTfromRH=getallSiblinginHT(rootlevelParent);	
				Node rootlevelParent4DH = geRootLevelNodeObj(definserNode);
				Hashtable allSiblingHTfromDH = getallSiblinginHT(rootlevelParent4DH);
				System.out.println("rootlevelParent==>"+Globals.getAttrVal4AttrName(rootlevelParent, "value"));
				System.out.println("definserNode.getParentNode()==>"+Globals.getAttrVal4AttrName(definserNode.getParentNode(), "value"));
				System.out.println("allsiblingHTfromRH==>"+allsiblingHTfromRH);
				System.out.println("allSiblingHTfromDH==>"+allSiblingHTfromDH);
				boolean flag4RI6Violation = false;
				for(int i=0;i<allSiblingHTfromDH.size();i++){
					if(allsiblingHTfromRH.containsValue(String.valueOf(allSiblingHTfromDH.get(i)))){
						flag4RI6Violation = false;
					}else{
						flag4RI6Violation = true;
						break;
					}
				}
				
				 System.out.println("allsiblingHTfromRH ---->"+allsiblingHTfromRH);
				if(!flag4RI6Violation){
					 System.out.println("its contain ---->"+defchildNodeValue);
					 
					 System.out.println(" (**1 SET SUCCESS RI-006 **)  :");
				     String failcode=setAtrributes4ChildDHEL.getAttribute("RI_Failure_Code");
				      if(failcode.equals("") || failcode.equals("RI-006") || failcode.equals("RI-000") || (!ruleAL.contains(failcode))){
				       setAtrributes4ChildDHEL.setAttribute("RI_Validation_Status", "Success");
				       setAtrributes4ChildDHEL.setAttribute("RI_Failure_Code", "");
				       setAtrributes4ChildDHEL.setAttribute("RI_Failure_Msg", "");
				      }
					 
				}else{
					// set attributes as error
					
					 System.out.println(" (**2.SET ATTRIBUTES RI-006 **) THIS NODE NOT AVAILABLE IN RH : :"+defchildNodeValue);
					 String msg=FailureDescriptionstr.replace("$currentNodeValue$", defchildNodeValue);
					 msg=msg.replace("$ParentName$", "");
					 setAtrributes4ChildDHEL.setAttribute("RI_Validation_Status", "Failure");
					 setAtrributes4ChildDHEL.setAttribute("RI_Failure_Code", "RI-006");
					 setAtrributes4ChildDHEL.setAttribute("RI_Failure_Msg", msg);
					 riValidationStatusHT.put("RI_Validation_Failure", "Failure");   // code change Menaka 14APR2014
				}
			}
			
				
			  
		}else{
			//set attributes here for this parent (DH)
			 System.out.println(" (**3.SET ATTRIBUTES RI-006 **) THIS NODE NOT AVAILABLE IN RH : :"+defchildNodeValue);
			 Element setAtrributes4ChildDHEL=(Element)definserNode;
			 String msg=FailureDescriptionstr.replace("$childName$", defchildNodeValue);
			 msg=msg.replace("$ParentName$", "");
			 setAtrributes4ChildDHEL.setAttribute("RI_Validation_Status", "Failure");
			 setAtrributes4ChildDHEL.setAttribute("RI_Failure_Code", "RI-006");
			 setAtrributes4ChildDHEL.setAttribute("RI_Failure_Msg", msg);
			 riValidationStatusHT.put("RI_Validation_Failure", "Failure");   // code change Menaka 14APR2014
		}
	  
  }catch (Exception e) {
		e.printStackTrace();
		
		riValidationStatusHT.put("RI_status", "Failure");
		riValidationStatusHT.put("RI_Details", e.getMessage());
	}
	
 System.out.println("Exiting: " + new Exception().getStackTrace()[0].getClassName() + "."
	            + new Exception().getStackTrace()[0].getMethodName());
	  
	
}


	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	public static Hashtable geRootLevelParentHT(Hashtable rootlevelHT,Node nodeObj){ 
		
//		System.out.println("Entering: " + new Exception().getStackTrace()[0].getClassName() + "."
//	            + new Exception().getStackTrace()[0].getMethodName());	
		Node rootnode=null;
	
		
	  try{
		  rootnode= nodeObj.getParentNode();
		 String nodeName= rootnode.getNodeName();
//		 System.out.println("Parent Node Name:"+nodeName);
		 Hashtable parentHT=Globals.getAttributeNameandValHT(rootnode);
		 String parentName=(String)parentHT.get("value");
		 rootlevelHT.put(parentName, parentName);
		  if(nodeName.equals("RootLevel")){
			  System.out.println("rootlevelHT :"+rootlevelHT);
			  return rootlevelHT;
		  }else{
			  rootlevelHT=geRootLevelParentHT(rootlevelHT,rootnode);
		  }
		  
	
			
}catch (Exception e) {
		e.printStackTrace();
	}
	
//System.out.println("Exiting: " + new Exception().getStackTrace()[0].getClassName() + "."
//	            + new Exception().getStackTrace()[0].getMethodName());
return rootlevelHT;  
	
}
	public static Node geRootLevelNodeObj(Node nodeObj){ 
		
//		System.out.println("Entering: " + new Exception().getStackTrace()[0].getClassName() + "."
//	            + new Exception().getStackTrace()[0].getMethodName());	
		Node rootnode=null;
	  try{
		  rootnode= nodeObj.getParentNode();
		 String nodeName= rootnode.getNodeName();
		  if(nodeName.equals("RootLevel")){
			  System.out.println("return root level Node :"+rootnode.getNodeName());
			  return rootnode;
		  }else{
			  rootnode=geRootLevelNodeObj(rootnode);
		  }
		  
	
			
}catch (Exception e) {
		e.printStackTrace();
	}
	
//System.out.println("Exiting: " + new Exception().getStackTrace()[0].getClassName() + "."
//	            + new Exception().getStackTrace()[0].getMethodName());
return rootnode;  
	
}
	
	public static void removedataNode(String hierID,String dataNodeID){
		
		try{
			
			System.out.println("Entering: " + new Exception().getStackTrace()[0].getClassName() + "."
            + new Exception().getStackTrace()[0].getMethodName());	
			
			PropUtil prop=new PropUtil();
			String heirRefXML=prop.getProperty("HIERARCHY_REFERENCE_XML");
			String heirLeveldir=prop.getProperty("HIERARCHY_XML_DIR");
			Document refDoc=Globals.openXMLFile(heirLeveldir, heirRefXML);

			//Check if it is a Reference Heirarchy
			Node refhierNode=Globals.getNodeByAttrVal(refDoc, "Hierarchy_Level", "Hierarchy_ID", hierID);
					//check Data and Rule Node Present in Ref Hier XML
					NodeList hierChildNL=refhierNode.getChildNodes();
					for(int j=0;j<hierChildNL.getLength();j++)
					{
						Node childN=hierChildNL.item(j);
						if(childN.getNodeType()==Node.ELEMENT_NODE){
						String childNodeName=childN.getNodeName();
						if(childNodeName.equalsIgnoreCase("Data"))
						{
							NodeList dataNodeNL=childN.getChildNodes();
							for(int k=0;k<dataNodeNL.getLength();k++)
							{
								Node dataNodeCheck=dataNodeNL.item(k);
								if(dataNodeCheck.getNodeType()==Node.ELEMENT_NODE){
								System.out.println("dataNodeCheck      "+dataNodeCheck.getNodeName());
								String checknodeType=dataNodeCheck.getAttributes().getNamedItem("Type").getNodeValue();
								if(checknodeType!=null){
									if(checknodeType.equalsIgnoreCase("Data"))
									{
										
										String refXmlNodeID=dataNodeCheck.getAttributes().getNamedItem("ID").getNodeValue();
										
										if(refXmlNodeID.equalsIgnoreCase(dataNodeID))
										{
											// remove node
											dataNodeCheck.getParentNode().removeChild(dataNodeCheck);
											
											Globals.writeXMLFile(refDoc, heirLeveldir, heirRefXML);
											
											
										}
									}
								}
								}
							}
								
							
							
						}
					}
					}

		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
		System.out.println("Exiting: " + new Exception().getStackTrace()[0].getClassName() + "."
        + new Exception().getStackTrace()[0].getMethodName());
	}
	
	
	
	public static Boolean addDataNodetoRefXML(String hierID,String nodeType,String dataNodeLevel,String dataNodeuniqueID,String dataNodeID,String dataNodeValue,String depTableNColName)
	{
		
		Boolean isDataDup=false;
		try{
			
			System.out.println("Entering: " + new Exception().getStackTrace()[0].getClassName() + "."
            + new Exception().getStackTrace()[0].getMethodName());	
			
//			if(nodeType!=null&&nodeType.equalsIgnoreCase("data"))
//			{
			PropUtil prop=new PropUtil();
			String heirRefXML=prop.getProperty("HIERARCHY_REFERENCE_XML");
			String heirLevelXML=prop.getProperty("HIERARCHY_XML_FILE");
			String heirLeveldir=prop.getProperty("HIERARCHY_XML_DIR");
			
			Document levelDoc=Globals.openXMLFile(heirLeveldir, heirLevelXML);
			Document refDoc=Globals.openXMLFile(heirLeveldir, heirRefXML);

			//Check if it is a Reference Heirarchy
			Node hierNode=Globals.getNodeByAttrVal(levelDoc, "Hierarchy_Level", "Hierarchy_ID", hierID);
			java.util.Hashtable<String, String> hierattHT=Globals.getAttributeNameandValHT(hierNode);
			
			String hierType=(String)hierattHT.get("RI_Hierarchy_Type");
			if(hierType!=null&&hierType.equalsIgnoreCase("Reference"))
			{
				//Adding data to the Reference Hierarchy XML
				if(nodeType!=null&&nodeType.equalsIgnoreCase("data"))
					{
					
					/////////
					Boolean alreadyavaiblewholeXml=searchRIwholeXml(refDoc,hierID,dataNodeValue,depTableNColName);
					if(alreadyavaiblewholeXml){
						System.out.println("This Data Available one of Hierachy dataNodeValue (So exsit method) :"+dataNodeValue+" depTableNColName :"+depTableNColName);
						return true;
					}
					
					///
					
					//Check if hierarchy Node exists in refXML
					Node refhierNode=Globals.getNodeByAttrVal(refDoc, "Hierarchy_Level", "Hierarchy_ID", hierID);
										
					Element hierLevelE=null;
					Element dataE=null;
					Element RIruleE=null;
					if(refhierNode==null)
					{
					//Creating new Node in refXML
					Node rootN=refDoc.getFirstChild();
					hierLevelE=refDoc.createElement("Hierarchy_Level");
					hierLevelE.setAttribute("Hierarchy_ID", hierID);
					hierLevelE.setAttribute("Hierarchy_Name", (String)hierattHT.get("Hierarchy_Name"));
					hierLevelE.setAttribute("RI_Hierarchy_Type", hierType);
					hierLevelE.setAttribute("Unique_ID", (String)hierattHT.get("Unique_ID"));
					hierLevelE.setAttribute("RI_Validated", "false"); // code change pandian 21APR2014 add ri validation False
					rootN.appendChild(hierLevelE);
					
					}else
					{
						hierLevelE=(Element)refhierNode;
						
						//check Data and Rule Node Present in Ref Hier XML         /////////
						NodeList hierChildNL=refhierNode.getChildNodes();
						for(int j=0;j<hierChildNL.getLength();j++)
						{
							Node childN=hierChildNL.item(j);
							if(childN.getNodeType()==Node.ELEMENT_NODE){
							String childNodeName=childN.getNodeName();
							if(childNodeName.equalsIgnoreCase("Data"))
							{
								dataE=(Element)childN;
								NodeList dataNodeNL=childN.getChildNodes();
								for(int k=0;k<dataNodeNL.getLength();k++)
								{
									Node dataNodeCheck=dataNodeNL.item(k);
									if(dataNodeCheck.getNodeType()==Node.ELEMENT_NODE){
									System.out.println("dataNodeCheck      "+dataNodeCheck.getNodeName());
									String checknodeType=dataNodeCheck.getAttributes().getNamedItem("Type").getNodeValue();
									if(checknodeType!=null){
										if(checknodeType.equalsIgnoreCase("Data"))
										{
											
											String tbleNcolName="";
											if(dataNodeCheck.getAttributes().getNamedItem("CostCenter_TableName_ColumnName")!=null){
												
												tbleNcolName=dataNodeCheck.getAttributes().getNamedItem("CostCenter_TableName_ColumnName").getNodeValue();
											}
											
											String checknodeValue=dataNodeCheck.getAttributes().getNamedItem("value").getNodeValue();
											
											if(dataNodeValue.equalsIgnoreCase(checknodeValue) && depTableNColName.equalsIgnoreCase(tbleNcolName))
											{
												isDataDup=true;
											}
										}
									}
									}
								}
									
								
								
							}else if(childNodeName.equalsIgnoreCase("RI-Rules"))
							{
								RIruleE=(Element)childN;
							}
						}
						}/////////////////////////
						
					}
					
					
					if(RIruleE==null)
					{
					//getting RI-Rule from Level XML
					NodeList hierChildNL=hierNode.getChildNodes();
					String isRule7="";
					for(int i=0;i<hierChildNL.getLength();i++)
					{
						Node childN=hierChildNL.item(i);
					if(childN.getNodeType()==Node.ELEMENT_NODE){
						String childNodeName=childN.getNodeName();
						if(childNodeName.equalsIgnoreCase("RI-Rules"))
						{
							isRule7=childN.getAttributes().getNamedItem("RI-007").getNodeValue();
							break;
						}
					}
					}
					
					RIruleE=refDoc.createElement("RI-Rules");
					RIruleE.setAttribute("RI-007", isRule7);
					hierLevelE.appendChild(RIruleE);
					}
					
					
					if(dataE==null)
					{
					dataE=refDoc.createElement("Data");
					hierLevelE.appendChild(dataE);
					}
					
					////
					////
					
					
					if(!isDataDup)
					{
					Element dataNodeE=refDoc.createElement(dataNodeLevel);
					dataNodeE.setAttribute("Type", nodeType);
					dataNodeE.setAttribute("Unique_ID", dataNodeuniqueID);
					dataNodeE.setAttribute("ID", dataNodeID);
					dataNodeE.setAttribute("value", dataNodeValue);
					dataNodeE.setAttribute("CostCenter_TableName_ColumnName", depTableNColName);
					
					dataE.appendChild(dataNodeE);
					System.out.println("Entry added in Reference Heirarchy");
					Globals.writeXMLFile(refDoc, heirLeveldir, heirRefXML);
					}
					else{
						System.out.println("Rule 7:Data is Already available in Reference XML,Not Possible to add Data");
					}
					}
				
			}else{
				System.out.println("This Hierarchy is not a Reference Heirarchy");
			}
			
			
			
//			}
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
		
		System.out.println("Exiting: " + new Exception().getStackTrace()[0].getClassName() + "."
        + new Exception().getStackTrace()[0].getMethodName());
		
		return isDataDup;
	}
	
	
	public static Boolean searchRIwholeXml(Document refDoc,String hierID,String dataNodeValue,String depTableNColName)
	{
		
		Boolean isavailable=false;
		try{
			
			System.out.println("Entering: " + new Exception().getStackTrace()[0].getClassName() + "."
            + new Exception().getStackTrace()[0].getMethodName());	
			
			
			// search whole XMl File
			Node rootN=refDoc.getFirstChild();
			NodeList nodlst=rootN.getChildNodes();
			
			for(int child=0; child<nodlst.getLength();child++){
				Node chNode=nodlst.item(child);
				if(chNode.getNodeType()==Node.ELEMENT_NODE){
					if(chNode.getNodeName().equals("Hierarchy_Level")){
						Hashtable nodeHT=Globals.getAttributeNameandValHT(chNode);
						String hierarchyID=(String)nodeHT.get("Hierarchy_ID");
						if(!hierarchyID.equals(hierID)){
							//check Data and Rule Node Present in Ref Hier XML
							NodeList hierChildNL=chNode.getChildNodes();
							for(int j=0;j<hierChildNL.getLength();j++)
							{
								Node childN=hierChildNL.item(j);
								if(childN.getNodeType()==Node.ELEMENT_NODE){
								String childNodeName=childN.getNodeName();
								if(childNodeName.equalsIgnoreCase("Data"))
								{
									NodeList dataNodeNL=childN.getChildNodes();
									for(int k=0;k<dataNodeNL.getLength();k++)
									{
										Node dataNodeCheck=dataNodeNL.item(k);
										if(dataNodeCheck.getNodeType()==Node.ELEMENT_NODE){
										String checknodeType=dataNodeCheck.getAttributes().getNamedItem("Type").getNodeValue();
										if(checknodeType!=null){
											if(checknodeType.equalsIgnoreCase("Data"))
											{
												
												String tbleNcolName="";
												if(dataNodeCheck.getAttributes().getNamedItem("CostCenter_TableName_ColumnName")!=null){
													
													tbleNcolName=dataNodeCheck.getAttributes().getNamedItem("CostCenter_TableName_ColumnName").getNodeValue();
												}
												
												String checknodeValue=dataNodeCheck.getAttributes().getNamedItem("value").getNodeValue();
												
												if(dataNodeValue.equalsIgnoreCase(checknodeValue) && depTableNColName.equalsIgnoreCase(tbleNcolName))
												{
													isavailable=true;
													
													return true;
												}
											}
										}
										}
									}
										
									
									
								}
							}
							}//
						}
						
						
						
						
					}
					
				}
			}
			
			///////////
			
			
	
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	System.out.println("Exiting: " + new Exception().getStackTrace()[0].getClassName() + "."
	        + new Exception().getStackTrace()[0].getMethodName());
			
			return isavailable;
		}
	
	

public static Boolean getRIValidationprocess(Node defrootlevelNode) {	
	Boolean ishavingError=false;
	
	 try{
		 
		 //start code change Menaka 28APR2014
		 if(defrootlevelNode.getNodeName().equals("RootLevel")){
			  Hashtable refchildHT=Globals.getAttributeNameandValHT(defrootlevelNode);
			  String rIValidationStatus=(String) refchildHT.get("RI_Validation_Status");
//			  System.out.println("rIValidationStatus  "+rIValidationStatus);
			  if(rIValidationStatus !=null){
			  if(rIValidationStatus.equals("Failure")){
				  
				  System.out.println("*********rIValidationStatus having Error at root node : "+rIValidationStatus);
				  ishavingError=true;
				  return true;
				  
			  }
		  }
			 
		 }
		  NodeList refrootNodeList=defrootlevelNode.getChildNodes();			  
		  for(int nod=0;nod<refrootNodeList.getLength();nod++){
			  Node refchnod=refrootNodeList.item(nod);
			  if(refchnod.getNodeType()==Node.ELEMENT_NODE){
				  
				  String refchnodName=refchnod.getNodeName();
				  if(refchnodName.equals("ID") || refchnodName.equals("RootLevel_Name")){
					  continue;
				  }
				  
 if(refchnodName.contains("Level")){
					  
				  }else{ continue;}
				  
				  //first checking parent
				  Hashtable refchildHT=Globals.getAttributeNameandValHT(refchnod);
				  String rIValidationStatus=(String) refchildHT.get("RI_Validation_Status");
//				  System.out.println("rIValidationStatus  "+rIValidationStatus);
				  if(rIValidationStatus !=null){
				  if(rIValidationStatus.equals("Failure")){
					  
					  System.out.println("*********rIValidationStatus having Error : "+rIValidationStatus);
					  ishavingError=true;
					  return true;
						 
					  
				  }else{
					  
					
						 if(ishavingError){
							 return true;
						 }
						 
						 ishavingError=getRIValidationprocess(refchnod);
				  }
				  
				  }else{ // end of  if(!rIValidationStatus==null)
					  
					  if(ishavingError){
							 return true;
						 }
						 
						 ishavingError=getRIValidationprocess(refchnod);
					  
				  }
				  
				  
				  
				  
			  }
			  
		  }
		  
		  
		 
		  
	  }catch (Exception e) {
			e.printStackTrace();
		}
	 
	 return ishavingError;
	
}
	
	///////////////


public boolean isHierarchyApproved(Document doc,String hierarchyID){   // code change Menaka 05APR2014
	
	boolean isApproved=false;
	  
    // code change Menaka 05APR2014
		
		Node workFlowN = Globals.getNodeByAttrVal(doc, "Workflow", "Hierarchy_ID", hierarchyID);
		if(workFlowN!=null){
			Hashtable workFlowHT=Globals.getAttributeNameandValHT(workFlowN);
			
			String iscompleted=(String)workFlowHT.get("Current_Stage_No");
			if(iscompleted.equalsIgnoreCase("Completed")){
				
				isApproved=true;
				
			       }
			}
		
		return isApproved;
}
	boolean flag4 = false;

public boolean isFlag4() {
		return flag4;
	}
	public void setFlag4(boolean flag4) {
		this.flag4 = flag4;
	}
	String flag4FactDimGenerate = "";
public String getFlag4FactDimGenerate() {
		return flag4FactDimGenerate;
	}
	public void setFlag4FactDimGenerate(String flag4FactDimGenerate) {
		this.flag4FactDimGenerate = flag4FactDimGenerate;
	}
	
	String riConstraintMess = "";
	
public String getRiConstraintMess() {
		return riConstraintMess;
	}
	public void setRiConstraintMess(String riConstraintMess) {
		this.riConstraintMess = riConstraintMess;
	}
	public String factRunType = "";
public String getFactRunType() {
		return factRunType;
	}
	public void setFactRunType(String factRunType) {
		this.factRunType = factRunType;
	}
public void checkRefrentialConstrains(String hierarchyID,String form,boolean cmgFrmFactPopup) {
	try{

		rIValidationFailure=false;
		boolean isApproved=false;
		
	
		 try{
			 
			  FacesContext ctx = FacesContext.getCurrentInstance();
				ExternalContext extContext = ctx.getExternalContext();
				Map sessionMap = extContext.getSessionMap();
				HierarchyDBBean hdb = (HierarchyDBBean) sessionMap.get("hierarchyDBBean");
				HierarchyBean hb = (HierarchyBean) sessionMap.get("hierarchyBean");
				String message="";
				hdb.setMessage("");
			
				hdb.setMessage("");
				hb.setMsg1("");
				factRunType = form;
			 PropUtil prop=new PropUtil();
			 String HIERARCHY_XML_DIR=prop.getProperty("HIERARCHY_XML_DIR");
				String XMLFileName=prop.getProperty("HIERARCHY_XML_FILE");  // code change Menaka 20MAR2014
				Document doc=Globals.openXMLFile(HIERARCHY_XML_DIR, XMLFileName);
				
				Node defrootlevelNode=Globals.getNodeByAttrVal(doc, "RootLevel", "ID", hierarchyID);
			 
				isApproved=isHierarchyApproved(doc,hierarchyID);
//				System.out.println("isApproved============>>>"+isApproved);
				
				if(isApproved){
					rIValidationFailure=getRIValidationprocess(defrootlevelNode) ;
					if(rIValidationFailure){
						FacesContext ctx1 = FacesContext.getCurrentInstance();
						ExternalContext extContext1 = ctx1.getExternalContext();
						Map sessionMap1 = extContext1.getSessionMap();
						LoginBean lgnbn = (LoginBean) sessionMap1.get("loginBean");
						String adminusername=lgnbn.username;
						workFlowBean wfbn=new workFlowBean();
						Hashtable allUsersHT=wfbn.getAllUsersFromlaXML();
						
						String adminprivillage="false";
						boolean isthisAdminUser=false;
						for(int member=0;member<allUsersHT.size();member++){
							Hashtable memberHT=(Hashtable)allUsersHT.get(member);
							String username=(String)memberHT.get("Login_ID");
							if(adminusername.equals(username)){
								adminprivillage=(String)memberHT.get("Super_Privilege_Admin");	
								if(adminprivillage.equals("true")){isthisAdminUser=true;}
								break;
							}
							
						}
						
						LoginProcessManager login=new LoginProcessManager();
						String adminstageNo="0";
						Hashtable curstageDetailsHT=login.getStageDetails(hierarchyID, adminstageNo,"");
						Hashtable currentemplyeeDetailsHT=(Hashtable)curstageDetailsHT.get("EmployeedetHT");
						 
						for (int mail = 0; mail < currentemplyeeDetailsHT.size(); mail++) {
							Hashtable mailApproversHT=new Hashtable();
							mailApproversHT=(Hashtable)currentemplyeeDetailsHT.get(mail);
							String enpname=(String)mailApproversHT.get("empName");	
							if(adminusername.equals(enpname)){
								isthisAdminUser=true;
								break;
							}
						}
						
						System.out.println("The User "+adminusername+"is an administrator.");
						
						
						if(!isthisAdminUser){
							 message="This hierarchy still has unresolved referential integrity errors.So you can't proceed.";
							
//							if(from.equals("GenerateData")){
//								hdb.setMessage(message);
//							}else if(from.equals("Regenerate")){
							 hb.fullMsg1 = message;
							 if(message.length() > 60){
								 hb.setMsg1(message.substring(0, 60));
							 }else{
								 hb.setMsg1(message); 
							 }
							 hb.color4HierTreeMsg = "red";
//							}else  if(from.equals("GenerateFact")){
//								hdb.setMessage(message);
//							}
							System.out.println(message);
							flag4FactDimGenerate = "true";
						
						}else{
							riConstraintMess = "This hierarchy still has unresolved referential integrity errors.Do you want to proceed?";
							flag4FactDimGenerate = "false";
						}
						
					}else{
//						boolean flag = isAdminUser(hierarchyID);
						flag4FactDimGenerate = "fact";
//						if(form.equalsIgnoreCase("GenerateFact")){
						if(cmgFrmFactPopup)
							generateFact(hierarchyID,form);
							
//						}
					}
//					System.out.println("rIValidationFailure=====after approved===MMMMMM====>>>"+rIValidationFailure);
				}else{
					boolean flag = isAdminUser(hierarchyID);
					System.out.println("flag===>"+flag);
					if(flag){
						rIValidationFailure=getRIValidationprocess(defrootlevelNode) ;
						if(rIValidationFailure){
							flag4FactDimGenerate = "false";
							riConstraintMess = "This hierarchy not yet approved and has unresolved referential integrity errors. Do you want to proceed?";
						}else{
							flag4FactDimGenerate = "false";
							riConstraintMess = "This hierarchy not yet approved. Do you want to proceed?";
						}
					}else{
						message="This hierarchy not yet approved.So you can't proceed.";
						
//						if(from.equals("GenerateData")){
//							hdb.setMessage(message);
//						}else if(from.equals("Regenerate")){
						 hb.fullMsg1 = message;
						 if(message.length() > 60){
							 hb.setMsg1(message.substring(0, 60));
						 }else{
							 hb.setMsg1(message); 
						 }
						 hb.color4HierTreeMsg = "red";
					}
					
				}
				System.out.println("rIValidationFailure=====after approved=======>>>"+rIValidationFailure);
					
					
//					System.out.println("rIValidationFailure=====after approved===MMMMMM====>>>"+from);
				
			if(!rIValidationFailure) {
//				flag4FactDimGenerate = true;
				
				
			}
			
			System.out.println("rIValidationFailure============>>>"+rIValidationFailure);
			
			
			
		
		 
			  
		  }catch (Exception e) {
				e.printStackTrace();
			}
	}catch (Exception e) {
		// TODO: handle exception
		e.printStackTrace();
	}
}

public void generateFact(String hierarchyID,String factGenType) {
	try{
		 FacesContext ctx = FacesContext.getCurrentInstance();
			ExternalContext extContext = ctx.getExternalContext();
			Map sessionMap = extContext.getSessionMap();
			HierarchyDBBean hdb = (HierarchyDBBean) sessionMap.get("hierarchyDBBean");
		boolean isthisAdminUser = false;
		   isthisAdminUser = isAdminUser(hierarchyID);
		  
		PropUtil prop=new PropUtil();
		 String HIERARCHY_XML_DIR=prop.getProperty("HIERARCHY_XML_DIR");
			String XMLFileName=prop.getProperty("HIERARCHY_XML_FILE");  // code change Menaka 20MAR2014
			Document doc=Globals.openXMLFile(HIERARCHY_XML_DIR, XMLFileName);
		Node hierLevelNode=Globals.getNodeByAttrVal(doc, "Hierarchy_Level", "Hierarchy_ID", hierarchyID);
		Hashtable arrtsHT=Globals.getAttributeNameandValHT(hierLevelNode);
		 boolean isApproved = false;
		   isApproved=isHierarchyApproved(doc,hierarchyID);
		System.out.println("arrtsHT:"+arrtsHT);
		
		String isFactConfigCompleted=(String)arrtsHT.get("Fact_Config_Completed");
		
		if(isFactConfigCompleted==null||isFactConfigCompleted.equals("")){
			isFactConfigCompleted="No";
		}
			if(isthisAdminUser && !isApproved){
				hdb.writeFactTablesToXml("FromAdminGenerate",factGenType);
				
			}
		else if(isFactConfigCompleted.equals("Yes")){
					hdb.writeFactTablesToXml("FromGenerate",factGenType);
					
				}else{
					hdb.setMessage("Fact configuration is not completed yet.");
					System.out.println("****Fact configuration is not completed yet.");
					
				}
		
	}catch (Exception e) {
		// TODO: handle exception
		e.printStackTrace();
	}
}

public  boolean getRIValidationStatus4Hierarchy(String hierarchyID,String from) {  // code change Menaka 31MAR2014
	System.out.println("Entering: " + new Exception().getStackTrace()[0].getClassName() + "."
            + new Exception().getStackTrace()[0].getMethodName());	  
		rIValidationFailure=false;
		boolean isApproved=false;
		
	
		 try{
			 
			  FacesContext ctx = FacesContext.getCurrentInstance();
				ExternalContext extContext = ctx.getExternalContext();
				Map sessionMap = extContext.getSessionMap();
				HierarchyDBBean hdb = (HierarchyDBBean) sessionMap.get("hierarchyDBBean");
				HierarchyBean hb = (HierarchyBean) sessionMap.get("hierarchyBean");
				String message="This Hierarchy is not approved yet. Please approve this Hierarchy to generate data and analysis.";
				hdb.setMessage("");
				if(from.equals("GenerateData")){
				if(hdb.getSelectedPeriodValuesAL() == null || hdb.getSelectedPeriodValuesAL().isEmpty()){
					hdb.setMessage("Please add Period values to Generate Data.");
					return false;
				}
				}
				
				hdb.setMessage("");
				hb.setMsg1("");
			 PropUtil prop=new PropUtil();
			 String HIERARCHY_XML_DIR=prop.getProperty("HIERARCHY_XML_DIR");
				String XMLFileName=prop.getProperty("HIERARCHY_XML_FILE");  // code change Menaka 20MAR2014
				Document doc=Globals.openXMLFile(HIERARCHY_XML_DIR, XMLFileName);
				
				Node defrootlevelNode=Globals.getNodeByAttrVal(doc, "RootLevel", "ID", hierarchyID);
			 
				isApproved=isHierarchyApproved(doc,hierarchyID);
//				System.out.println("isApproved========MMMMMM====>>>"+isApproved);
               boolean isthisAdminUser = false;
			   isthisAdminUser = isAdminUser(hierarchyID);
				if(isApproved){
					rIValidationFailure=getRIValidationprocess(defrootlevelNode) ;
//					System.out.println("rIValidationFailure=====after approved===MMMMMM====>>>"+rIValidationFailure);
				}else{
					// set message this Hierarchy is not Approved
					
					if(from.equals("GenerateData")){
						if(isthisAdminUser){
							rIValidationFailure=getRIValidationprocess(defrootlevelNode) ;
							}else{
							rIValidationFailure=false;
							return false;
							}
					}else if(from.equals("Regenerate")){
						hb.setMsg1(message);
						hb.color4HierTreeMsg = "blue";
						rIValidationFailure=false;
						return false;
					}else if(from.equals("GenerateFact")){
						if(isthisAdminUser){
						rIValidationFailure=getRIValidationprocess(defrootlevelNode) ;
						}else{
						
						rIValidationFailure=false;
						return false;
						}
					}
					System.out.println("****"+message);
					
					
				}
					
					if(rIValidationFailure && from.equals("GenerateFact")){
						Node hierLevelNode=Globals.getNodeByAttrVal(doc, "Hierarchy_Level", "Hierarchy_ID", hierarchyID);
						Hashtable arrtsHT=Globals.getAttributeNameandValHT(hierLevelNode);
						
						System.out.println("arrtsHT:"+arrtsHT);
						
						String isFactConfigCompleted=(String)arrtsHT.get("Fact_Config_Completed");
						
						if(isFactConfigCompleted==null||isFactConfigCompleted.equals("")){
							isFactConfigCompleted="No";
						}
							if(isFactConfigCompleted.equals("No")){
								hdb.setMessage("Fact configuration is not completed yet.");
								rIValidationFailure=false;
									return false;
								}
					}
					
				
				
			if(!rIValidationFailure) {
				
				if(from.equals("GenerateData")){
					if(isthisAdminUser && !isApproved){
						hdb.writeGenDataToXml("FromAdmin","");
					}else{
					hdb.writeGenDataToXml("FromRIValidation","");
					}
				}else if(from.equals("Regenerate")){
					hb.reGenerateHierarchy(hb.getHierarchy_ID(),hb.getCodecombinationFlag(),false,"");
					
				}else if(from.equals("GenerateFact")){
					
					Node hierLevelNode=Globals.getNodeByAttrVal(doc, "Hierarchy_Level", "Hierarchy_ID", hierarchyID);
					Hashtable arrtsHT=Globals.getAttributeNameandValHT(hierLevelNode);
					
					System.out.println("arrtsHT:"+arrtsHT);
					
					String isFactConfigCompleted=(String)arrtsHT.get("Fact_Config_Completed");
					
					if(isFactConfigCompleted==null||isFactConfigCompleted.equals("")){
						isFactConfigCompleted="No";
					}
						if(isthisAdminUser && !isApproved){
							hdb.writeFactTablesToXml("FromAdminGenerate",factRunType);
							
						}
					else if(isFactConfigCompleted.equals("Yes")){
								hdb.writeFactTablesToXml("FromGenerate",factRunType);
								
							}else{
								hdb.setMessage("Fact configuration is not completed yet.");
								System.out.println("****Fact configuration is not completed yet.");
								return false;
							}
					}
				
			}
			
			System.out.println("rIValidationFailure============>>>"+rIValidationFailure);
			
			
			if(rIValidationFailure){
				if(!isthisAdminUser){
					 message="This hierarchy still has unresolved referential integrity errors.So you can't proceed.";
					
					if(from.equals("GenerateData")){
						hdb.setMessage(message);
					}else if(from.equals("Regenerate")){
						hb.setMsg1(message);
					}else  if(from.equals("GenerateFact")){
						hdb.setMessage(message);
					}
					System.out.println(message);
					rIValidationFailure=false;
					return false;
				}
				
			}
			
			riValidationFromPage=from;  
		 
			  
		  }catch (Exception e) {
				e.printStackTrace();
			}
		 System.out.println("Exiting: " + new Exception().getStackTrace()[0].getClassName() + "."
		            + new Exception().getStackTrace()[0].getMethodName());	 
		 return rIValidationFailure;
		 
	}


public void methodAfterAlert(String from){
	
	 FacesContext ctx = FacesContext.getCurrentInstance();
		ExternalContext extContext = ctx.getExternalContext();
		Map sessionMap = extContext.getSessionMap();
		HierarchyDBBean hdb = (HierarchyDBBean) sessionMap.get("hierarchyDBBean");
		HierarchyBean hb = (HierarchyBean) sessionMap.get("hierarchyBean");
		MemberStatusBean msb=(MemberStatusBean) sessionMap.get("memberStatusBean");
		String str[];
		String userStatus="";
		if(from.contains(":")){
			str=from.split(":");
			userStatus=str[1];
			from=str[0];
		}
		
	
	if(from.equals("GenerateData")){
		hdb.writeGenDataToXml("FromAlert","");
	}else if(from.equals("Regenerate")){
		hb.reGenerateHierarchy(hb.getHierarchy_ID(),hb.getCodecombinationFlag(),false,"");
	}else  if(from.equals("GenerateFact")){
		hdb.writeFactTablesToXml("FromGenerate",factRunType);	
	}else if(from.equals("ApproveHierarchy")){
		msb.selectedStatus="";
		msb.message="";
		msb.statusHM=new LinkedHashMap<String,String>();
		
		msb.statusHM.put(userStatus, userStatus);
	}
}


public boolean isAdminUser(String hierarchyID){
	boolean isthisAdminUser=false;
	try{
		FacesContext ctx1 = FacesContext.getCurrentInstance();
		ExternalContext extContext1 = ctx1.getExternalContext();
		Map sessionMap1 = extContext1.getSessionMap();
		LoginBean lgnbn = (LoginBean) sessionMap1.get("loginBean");
		String adminusername=lgnbn.username;
		workFlowBean wfbn=new workFlowBean();
		Hashtable allUsersHT=wfbn.getAllUsersFromlaXML();
		
		String adminprivillage="false";
		
		for(int member=0;member<allUsersHT.size();member++){
			Hashtable memberHT=(Hashtable)allUsersHT.get(member);
			String username=(String)memberHT.get("Login_ID");
			if(adminusername.equals(username)){
				adminprivillage=(String)memberHT.get("Super_Privilege_Admin");	
				if(adminprivillage.equals("true")){isthisAdminUser=true;}
				break;
			}
			
		}
		if(!isthisAdminUser){
		LoginProcessManager login=new LoginProcessManager();
		String adminstageNo="0";
		Hashtable curstageDetailsHT=login.getStageDetails(hierarchyID, adminstageNo,"");
		Hashtable currentemplyeeDetailsHT=(Hashtable)curstageDetailsHT.get("EmployeedetHT");
		 
		for (int mail = 0; mail < currentemplyeeDetailsHT.size(); mail++) {
			Hashtable mailApproversHT=new Hashtable();
			mailApproversHT=(Hashtable)currentemplyeeDetailsHT.get(mail);
			String enpname=(String)mailApproversHT.get("empName");	
			if(adminusername.equals(enpname)){
				isthisAdminUser=true;
				break;
			}
		}
		}
		System.out.println("The User "+adminusername+"is an administrator.");
	}catch (Exception e) {
		// TODO: handle exception
		e.printStackTrace();
	}
	return isthisAdminUser;
}
	
	
	
	
}
