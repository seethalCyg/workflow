package beans;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Random;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.print.Doc;
import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.richfaces.event.FileUploadEvent;
import org.richfaces.model.UploadedFile;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import managers.FormsManager;
import managers.LoginProcessManager;
import managers.RulesManager;
import managers.WorkflowManager;
import utils.Globals;
import utils.PropUtil;
@ManagedBean(name = "statuscode")
@SessionScoped
public class StatusCode {
	
	public String getApproved() {
		return approved;
	}
	public void setApproved(String approved) {
		this.approved = approved;
	}
	public String getReject() {
		return reject;
	}
	public void setReject(String reject) {
		this.reject = reject;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String approved;
	public String reject;
	public String status;
	public String selvalue;
	public String selvalue4Attach;
	private String outputvalue;
	private String resubmitoutputvalue="";
	private String resubmitoutputvaluecolor="";
	private String  statusDocNamestrshowValue="";
	
	
	
	
	private String outputvalueSubStr;
	private String outputvalue4Attach;
	private String outputvalueSubStr4Attach;
	private String statusDisplayFlag = "none";
	private String fileReNamed="";
	private String onlyshowADVworkflow="block";
	
	public String userNam;
	public String mailStgNo = "";
	public String docname;
	public String urlDocname;		//code Change Vishnu 31Jan2019
	public String docID;
	public String docID1;
	public static String urlDocID; 
	public static String urlUserName; 
	
	private String eSignstrURL;
	public static String eSignDocumentID; 
	public static String eSignDocumntNam; 
	public static String eSignuserNam; 
	
	private String statusUserNamestr;
	private String statusWFNamestr;
	private String statusDocNamestr;
	private String statusStageNamestr;
	private String userStageNamestr;
	private String primaryDocOnlyFlag = "";
	private String uploadFileFlag = "";
	private String autoLoginFlag = "";
	private String externalUserFlag = "";
	private String dirname = null;
	private String reuploadShowFlag="";
	private String atchFile="";
	private String atchUsr="";
	
	private String docattachUser="";
	
	private String docattachID="";
	private String docattachFilename="";
	
	
	private boolean disableUIProp=false;
	
	private ArrayList<HeirarchyDataBean> attachmentsList = new ArrayList<>();
	private ArrayList<HeirarchyDataBean> viewDocattachmentsList = new ArrayList<>();
	public ArrayList<StatusCode> historyPopList=new ArrayList<>();
	
	
	public ArrayList<HeirarchyDataBean> getViewDocattachmentsList() {
		return viewDocattachmentsList;
	}
	public void setViewDocattachmentsList(ArrayList<HeirarchyDataBean> viewDocattachmentsList) {
		this.viewDocattachmentsList = viewDocattachmentsList;
	}
	HeirarchyDataBean selectedAttachDocData = null;
	private String downloadURL = "";
	private String statusCode4DocsFlag = "";
	String type = "";
	public boolean reuploadFlag=false;
	public String reupDocName=""; 
	private boolean esignStageLevelEnabled = false;
	private String esignEnabledStageNo = "0";
	private String currStageNo = "0";
	private String statusCodeTitle = "Approve / Review the Document";
	public String getDocID1() {
		try {
			statusCodeArr.clear();
			selvalue = "";
			selvalue4Attach="";
			selNotesvalue = "";
			selNotesvalue4Attach="";
			outputvalue="";
			outputvalue4Attach = "";
			outputvalueSubStr4Attach = "";
			outputvalueSubStr="";
			esignStageLevelEnabled = false;
			esignEnabledStageNo = "0";
			currStageNo = "0";
			atchUsr="";		//code Change Vishnu.S 04Feb2019
			atchFile="";
			statusCodeTitle = "Approve / Review the Document";
			System.out.println("=-=-=-=eeee-=-=-=-=-"+docID);
			HttpServletRequest req = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
			if(req.getParameter("documentId") == null) {
				
			}else {
				
				docID=req.getParameter("documentId");
				docname=req.getParameter("documentName");
				userNam=req.getParameter("username");
				mailStgNo = req.getParameter("index") == null ? "" : req.getParameter("index");
				urlDocname = docname;		//code Change Vishnu 31Jan2019
				type = req.getParameter("type") == null ? "" : req.getParameter("type");
			}
			
			if(docID == null)
				return docID1;
			
			System.out.println(mailStgNo+"=-=-=-=-mailStgNo=-=-=-=-"+docID);
			System.out.println("=-=-=-=-=-=-=-=-"+docname);
			System.out.println("=-=-=-=-=-=-=-=-"+userNam);

			loadStageStatus(docID,urlDocname,userNam);		//code Change Vishnu 31Jan2019
			System.out.println(primaryDocOnlyFlag+"=-=-=-=-selvalue=-=-=-=-"+selvalue);
			validate();
			this.attachmentsList.clear();
			if(!primaryDocOnlyFlag.equalsIgnoreCase("true"))
				this.attachmentsList = WorkflowManager.loadAttachmentTable(docID);
			else
				this.attachmentsList = WorkflowManager.loadAttachmentByStageAndUser(docID, userStageNamestr, userNam);
			
			Hashtable tempHT = FormsManager.getTemplateNdFromDocId(docID);
			checkWorkflowType(docID);
			Node templateNd = tempHT == null ? null : (Node)tempHT.get("TemplateNode");
			statusDisplayMss = "";
			statusDisplayMss1="";
			statusDisplayMssdisplay="none";
			System.out.println(disableUIProp+"=-=-=-=-disableUIProp=-=-=-=-"+selvalue);
			if(templateNd != null) {
				Element templateEle = (Element)templateNd;
				String ultratempName=templateEle.getAttribute("Name");
				String ultratempDescriptor=templateEle.getAttribute("Descriptor");
				Element esignEle = templateEle.getElementsByTagName("ESign_Details").getLength() <= 0 ? null : (Element)templateEle.getElementsByTagName("ESign_Details").item(0);
				
				
			

					if(!ultratempDescriptor.isEmpty() || ultratempDescriptor != null) {
						
						this.statusDocNamestrshowValue= ultratempDescriptor;
					
					}else {
							
						this.statusDocNamestrshowValue= ultratempName;
							
				     }
					boolean flag = false;
					if(esignEle != null) {
					String metadataFilePath = esignEle.getAttribute("MetadataFilePath");
					
					File metaFile = new File(metadataFilePath);
					if(metaFile.exists()) {
						BufferedReader buffR = new BufferedReader(new FileReader(metaFile));
						StringBuilder sb = new StringBuilder();
						String line = "";
						while((line = buffR.readLine()) != null) {
							sb.append(line);
						}
						org.json.simple.JSONArray jObj = (org.json.simple.JSONArray) new JSONParser().parse(sb.toString());
						for (int i = 0; i < jObj.size(); i++) {
							JSONObject obj = (JSONObject) jObj.get(i);
							org.json.simple.JSONArray arr = (org.json.simple.JSONArray) obj.get("values");
							for (int j = 0; j < arr.size(); j++) {
								JSONObject obj1 = (JSONObject) arr.get(j);
								String userId = obj1.get("userid") == null ? "" : obj1.get("userid").toString();
								String stgNo = obj1.get("stageno") == null ? "" : obj1.get("stageno").toString();
								if(stgNo.equalsIgnoreCase("All") || (userId.equalsIgnoreCase("All") && stgNo.equalsIgnoreCase(mailStgNo)) || userId.equalsIgnoreCase(userNam)) {
									flag = true;
									break;
								}
							}
						}
						
					}
					}
				if(esignStageLevelEnabled) {
					if(Globals.checkStringIsNumber(esignEnabledStageNo)) {
						int esignStgNo = Integer.valueOf(esignEnabledStageNo);
						if(Globals.checkStringIsNumber(mailStgNo)) {
							int currStgNo = Integer.valueOf(mailStgNo);
							if(currStgNo <= esignStgNo) {
								statusDisplayFlag = "none";
							}else {
								if(disableUIProp) {
									statusDisplayMss = "This document is already been signed / rejected.";
									statusDisplayMss1="";
									statusDisplayMssdisplay="none";
								}else {
									if(flag) {
									disableUIProp = true;
									statusDisplayMss = "'"+ this.statusDocNamestrshowValue +"'"+" is waiting for your signature / fill-in. To open this document and e-sign / fill-in, Please click the below link.";
									statusDisplayMssdisplay="block";
									}else {
										statusDisplayMss = "";
										disableUIProp = false;
										statusDisplayMssdisplay="none";
									}
									statusDisplayMss1="";
									
								}
								statusDisplayFlag = "block";
								statusCodeTitle = "Sign / Fill-in";
							}
						}else {
							if(esignEle != null) {
								if(disableUIProp) {
									statusDisplayMss = "This document is already been signed / rejected.";
									statusDisplayMss1="";
									statusDisplayMssdisplay="none";
								}else {
									if(flag) {
									disableUIProp = true;
									statusDisplayMss = "'"+ this.statusDocNamestrshowValue +"'"+" is waiting for your signature / fill-in. To open this document and e-sign / fill-in, Please click the below link.";
									statusDisplayMssdisplay="block";
									}else {
										statusDisplayMss = "";
										disableUIProp = false;
										statusDisplayMssdisplay="none";
									}
									statusDisplayMss1="";
									
								}
								statusDisplayFlag = "block";
								statusCodeTitle = "Sign / Fill-in";
							}
						}
					}else {
						if(esignEle != null) {
							if(disableUIProp) {
								statusDisplayMss = "This document is already been signed / rejected.";
								statusDisplayMss1="";
								statusDisplayMssdisplay="none";
							}else {
								if(flag) {
									disableUIProp = true;
									statusDisplayMss = "'"+ this.statusDocNamestrshowValue +"'"+" is waiting for your signature / fill-in. To open this document and e-sign / fill-in, Please click the below link.";
									statusDisplayMssdisplay="block";
								}else {
									statusDisplayMss = "";
									disableUIProp = false;
									statusDisplayMssdisplay="none";
								}
								statusDisplayMss1="";
								
							}
							statusDisplayFlag = "block";
							statusCodeTitle = "Sign / Fill-in";
						}
					}
					
					
				}else {
					if(esignEle != null) {
						if(disableUIProp) {
							statusDisplayMss = "This document is already been signed / rejected.";
							statusDisplayMss1="";
							statusDisplayMssdisplay="none";
						}else {
							if(flag) {
							disableUIProp = true;
							statusDisplayMss = "'"+ this.statusDocNamestrshowValue +"'"+" is waiting for your signature / fill-in. To open this document and e-sign / fill-in, Please click the below link.";
							statusDisplayMssdisplay="block";
							}else {
								statusDisplayMss = "";
								disableUIProp = false;
								statusDisplayMssdisplay="none";
							}
							statusDisplayMss1="";
							
						}
						
						statusDisplayFlag = "block";
						statusCodeTitle = "Sign / Fill-in";
					}
				}
				
			}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return docID1;
	}
	
	public void DownloadAttachFile(String fileName, String type) {
		try {
			PropUtil prop = new PropUtil();
			String hierarchyDIR = prop.getProperty("HIERARCHY_XML_DIR");
			String configFileName=prop.getProperty("HIERARCHY_CONFIG_FILE");
			Document configdoc=Globals.openXMLFile(hierarchyDIR, configFileName);
			System.out.println("-=-=-hierarchyDIR=-=-="+hierarchyDIR);
			Element downLinkEle = (Element)configdoc.getElementsByTagName("Download_Link").item(0);
			String downLink = downLinkEle.getAttribute("URL").trim().isEmpty() ? "" : downLinkEle.getAttribute("URL").trim();
			String attachFilesEncode=URLEncoder.encode(fileName, "UTF-8");
			downloadURL = downLink.replace("$docName$", attachFilesEncode).replace("$docID$", docID).replace("$username$", userNam).
					replace("$downloadFrom$", "mail").replace("$filepath$", "").replace("$Type$", type).replace("$Index$", mailStgNo);//$Index$
			System.out.println("-=-=-downloadURL=-=-="+downloadURL);
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	
	
	public void checkWorkflowType(String document_ID) throws Exception {

		
		PropUtil prop = new PropUtil();
		String hierDir=prop.getProperty("HIERARCHY_XML_DIR");
		String docXmlFileName = prop.getProperty("DOCUMENT_XML_FILE");
		Document docXmlDoc=Globals.openXMLFile(hierDir, docXmlFileName);

		Node docND = Globals.getNodeByAttrVal(docXmlDoc, "Document", "Document_ID", document_ID);
		Element docEle = (Element) docND;

		Node workflow_Nd = docEle.getElementsByTagName("Workflow").item(0);
		Element workFlowNEL=(Element)workflow_Nd;
		String workflow_Type=workFlowNEL.getAttribute("Workflow_Type") == null ? "" : workFlowNEL.getAttribute("Workflow_Type").toString();

		if(workflow_Type.equalsIgnoreCase("Simple")) {

			onlyshowADVworkflow="none";
		}
		else {
			
			onlyshowADVworkflow="block";	
		}
		
		System.out.println(workflow_Type+"::::::::::::onlyshowADVworkflow::::::::"+onlyshowADVworkflow);
		
	}
	
public void reUploadFile(FileUploadEvent event) throws Exception {							//code Change Vishnu.S 04Feb2019
		
		String documentNAMEStr="";
		documentNAMEStr=this.docname;
		System.out.println("documentNAMEStr :"+documentNAMEStr);
		System.out.println("this.document_ID :"+this.docID);
		this.dirname = "";
		PropUtil prop = new PropUtil();
		String doc_Version_dir = prop.getProperty("DOCUMENTVERSION_DIR");
		String HIERARCHY_XML_DIR = prop.getProperty("HIERARCHY_XML_DIR");
		String docXmlFileName = prop.getProperty("DOCUMENT_XML_FILE");
		Document docXmlDoc = Globals.openXMLFile(HIERARCHY_XML_DIR, docXmlFileName);
		Node atthmentsNd = Globals.getChildNodeByAttrVal(docXmlDoc, "Document", "Document_ID", docID,"Resubmits");
		NodeList resubmit_NDL=null;
		 if(atthmentsNd!=null&&!atthmentsNd.equals("")) {
		  resubmit_NDL=atthmentsNd.getChildNodes(); 
		 }
		 Node resubmitNode=null;
		System.out.println("rbid_metadata_dir: " + doc_Version_dir);
		String primaryFileName=statusDocNamestr;
		File sourceFile = null;
		File targetFile = null;
		String infile = "";
		InputStream fileContent = null;
		
		try {
			UploadedFile item = event.getUploadedFile();
			infile = item.getName();
			System.out.println("File to be uploaded: " + infile);
			sourceFile = new File(infile);
			String BIMetadataFile = sourceFile.getName();
			doc_Version_dir = doc_Version_dir+docname.substring(0, docname.lastIndexOf("."))+"_"+docID+"\\1\\";
			
			fileReNamed="";
			 if(!BIMetadataFile.equalsIgnoreCase(statusDocNamestr)) {
      			fileReNamed=statusDocNamestr;}
			String[] extenstion;
			System.out.println("statusDocNamestr+++++++ "+statusDocNamestr);
			
			if (Globals.isFileExists(doc_Version_dir, BIMetadataFile)==true) {
				System.out.println("BIMetadataFile+++++++ "+BIMetadataFile);
				
				extenstion = BIMetadataFile.split("\\.",2); 
			
				System.out.println(extenstion[0]+" extenstion "+extenstion[1]);
				System.out.println(" File Already there: "+doc_Version_dir+ BIMetadataFile);
					String absolutePath =doc_Version_dir;
			        File dir = new File(absolutePath);
			        
			      
			      
			        Random ran = new Random();
			        int val1 = ran.nextInt((99999 - 10000) + 1) + 10000;
			        
			        if (dir.exists()) { // make sure it's a directory
			        	 System.out.println("make sure it's a directory ");
			            for (final File f : dir.listFiles()) {
			                try {
			                	System.out.println("file name"+f.getName());
			                	if(f.getName().equals(BIMetadataFile)) {
			                		
			                    File newfile = new File(doc_Version_dir + "\\" + extenstion[0] + "_" + val1 + "." + extenstion[1]);
			                   
			                   
			                    if(newfile.exists()){
			                    	System.out.println("Rename failed: " + extenstion[0]+ "_" + val1 + "." + extenstion[1]);
			                    	val1 = ran.nextInt((99999 - 10000) + 1) + 10000;
			                    	 newfile = new File(doc_Version_dir + "\\" + extenstion[0] + "_" + val1 + "." + extenstion[1]);
			                    	 f.renameTo(newfile);
			                    } else {
			                    	 f.renameTo(newfile);
			                    	 
			                   		 fileReNamed=newfile.getName();
			                   		 
			                    	  for (int j = 0; j < resubmit_NDL.getLength(); j++) {
			               			   resubmitNode = resubmit_NDL.item(j);
			               				if (resubmitNode.getNodeType() == Node.ELEMENT_NODE
			               						&& resubmitNode.getNodeName().equals("Resubmit")) {
			               					Element stageEle = (Element) resubmitNode;
			               					if(stageEle.getAttribute("FileName").equalsIgnoreCase(BIMetadataFile))
			               					{System.out.println("YYYYYYYYYYYYYYYYY:   "+stageEle.getAttribute("FileName"));
			               						stageEle.setAttribute("FileName", fileReNamed);
			               						
			               					}
			               				}
			               				}
			                    	  
			                    	  Globals.writeXMLFile(docXmlDoc, HIERARCHY_XML_DIR, docXmlFileName);
			                    	  if(!BIMetadataFile.equalsIgnoreCase(statusDocNamestr)) {
			                    			fileReNamed=statusDocNamestr;}
			                    	 
			                    	 System.out.println("Rename succesful: " + extenstion[0]+ "_" + val1 + "." + extenstion[1]);
			                        System.out.println("Rename "+fileReNamed);
			                        
			                    }
			                	}
			                   
			                } catch (Exception e) {
			                    e.printStackTrace();
			                }
			            }

			        }
				}
			
			targetFile = new File(doc_Version_dir + sourceFile.getName());
			this.statusDocNamestr=BIMetadataFile;
			this.dirname = sourceFile.getAbsolutePath();
			
			System.out.println(infile+" primaryFileName+++ "+primaryFileName);
			
			
			System.out.println(targetFile.getAbsolutePath()+": Source File Location (dirname): " + dirname);
			fileContent =item.getInputStream();
			BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(targetFile));
			byte[] buffer = new byte[1024];
			int len;
			while ((len = fileContent.read(buffer)) >= 0) {

				bos.write(buffer, 0, len);
			}
			fileContent.close();
			bos.close();
			System.out.println("It`s the link"+doc_Version_dir+"\\"+infile);
			if (Globals.isFileExists(doc_Version_dir, BIMetadataFile)) 
			{
				reUploadWirtetoXml(infile);
				reuploadFlag=true;
				System.out.println("BI Metadata File Uploaded Successfully: " + doc_Version_dir + BIMetadataFile);
			} else {

				System.out.println("*** Error: BI Metadata File is not Uplodaed: " + doc_Version_dir + BIMetadataFile);
				System.out.println("*** Error: Discovery Process Aborted.");
				return;
			}

			System.out.println("Exiting : " + new Exception().getStackTrace()[0].getClassName() + "."
					+ new Exception().getStackTrace()[0].getMethodName());
		} catch (Exception e) {
			e.printStackTrace();
		}

}
public void reUploadWirtetoXml(String docName )							//code Change Vishnu.S 04Feb2019
{
	System.out.println("Entering : " + new Exception().getStackTrace()[0].getClassName() + "."
		+ new Exception().getStackTrace()[0].getMethodName());
	try {
		PropUtil prop = new PropUtil();
		String dateFormat = prop.getProperty("DATE_FORMAT");
		Date updDate = new Date();
		DateFormat sdf1 = new SimpleDateFormat(dateFormat);
		String modifyDate = sdf1.format(updDate);
		String HIERARCHY_XML_DIR = prop.getProperty("HIERARCHY_XML_DIR");
		String docXmlFileName = prop.getProperty("DOCUMENT_XML_FILE");
				
		Document docXmlDoc = Globals.openXMLFile(HIERARCHY_XML_DIR, docXmlFileName);
		Node docNd =Globals.getNodeByAttrVal(docXmlDoc, "Document", "Document_ID", docID);
		Element docNdELE = (Element)docNd;
		String prmyDocName=docNdELE.getAttribute("Primary_FileName");
		String prmyCreated_Date=docNdELE.getAttribute("Created_Date");
		String prmyModified_Date=docNdELE.getAttribute("Modified_Date");
		
		System.out.println("Slow Hands"+prmyDocName);
//docNdELE.setAttribute("DocumentName",docName);
				docNdELE.setAttribute("Primary_FileName",docName);
				reupDocName=docNdELE.getAttribute("DocumentName");
				System.out.println("Slow Hands "+docNdELE.getAttribute("DocumentName"));
				System.out.println(modifyDate+" modifyDate "+docNdELE.getAttribute("Modified_Date"));
			    docNdELE.setAttribute("Modified_Date",modifyDate);
	   String secoModified_Date=docNdELE.getAttribute("Modified_Date");
			  String filerenme=fileReNamed==null||fileReNamed.equals("")?prmyDocName:fileReNamed;
	   Node atthmentsNd = Globals.getChildNodeByAttrVal(docXmlDoc, "Document", "Document_ID", docID,"Resubmits");
	   Element resubsFile_Ele=null;
	   Element resubmit_Ele=null;
		if(atthmentsNd == null) {
			resubsFile_Ele=docXmlDoc.createElement("Resubmits");
			docNdELE.appendChild(resubsFile_Ele);
			System.out.println("resubsFile_Ele+++++ "+resubsFile_Ele );
			resubmit_Ele=docXmlDoc.createElement("Resubmit");
			resubmit_Ele.setAttribute("AttachMentFile_ID","Primary");
			resubmit_Ele.setAttribute("AttachedStage",statusStageNamestr);
			resubmit_Ele.setAttribute("AttachedUser",statusUserNamestr);
			resubmit_Ele.setAttribute("FileName",filerenme);				
			resubmit_Ele.setAttribute("UploadedDate",prmyCreated_Date);
			resubmit_Ele.setAttribute("ReplacedDate",secoModified_Date);
			resubsFile_Ele.appendChild(resubmit_Ele);
			
		}else {
			resubsFile_Ele = (Element) atthmentsNd;
	
			resubmit_Ele=docXmlDoc.createElement("Resubmit");
			resubmit_Ele.setAttribute("AttachMentFile_ID","Primary");
			resubmit_Ele.setAttribute("AttachedStage",statusStageNamestr);
			resubmit_Ele.setAttribute("AttachedUser",statusUserNamestr);
			resubmit_Ele.setAttribute("FileName",filerenme);
			if(docNdELE.getAttribute("Modified_Date")==null) {					
			resubmit_Ele.setAttribute("UploadedDate",prmyCreated_Date);
			resubmit_Ele.setAttribute("ReplacedDate",secoModified_Date);
			}
			else {					
				resubmit_Ele.setAttribute("UploadedDate",prmyModified_Date);
				resubmit_Ele.setAttribute("ReplacedDate",secoModified_Date);
				
			}
			resubsFile_Ele.appendChild(resubmit_Ele);
		}
				
				Globals.writeXMLFile(docXmlDoc, HIERARCHY_XML_DIR, docXmlFileName);
		
		
	} catch (Exception e)
	{
		System.out.println("Y"+	e.getMessage());
		e.printStackTrace();
	}

}
public void historydataTableXmlRead()				//code Change Vishnu.S 04Feb2019
{
	this.historyPopList.clear();
	try {
	PropUtil prop = new PropUtil();
	String HIERARCHY_XML_DIR = prop.getProperty("HIERARCHY_XML_DIR");
	String docXmlFileName = prop.getProperty("DOCUMENT_XML_FILE");
			
	Document docXmlDoc = Globals.openXMLFile(HIERARCHY_XML_DIR, docXmlFileName);
	  Node atthmentsNd = Globals.getChildNodeByAttrVal(docXmlDoc, "Document", "Document_ID", docID,"Resubmits");
	  String	atchedUsr="";
	  String	atchedFileName="";
	  if(atthmentsNd!=null&&!atthmentsNd.equals("")) {
	   NodeList resubmit_NDL=atthmentsNd.getChildNodes();
	   Node resubmitNode=null;
	   for (int j = 0; j < resubmit_NDL.getLength(); j++) {
		   resubmitNode = resubmit_NDL.item(j);
			if (resubmitNode.getNodeType() == Node.ELEMENT_NODE
					&& resubmitNode.getNodeName().equals("Resubmit")) {
				Element stageEle = (Element) resubmitNode;
		String atchMentFile_ID=stageEle.getAttribute("AttachMentFile_ID");
				if(atchMentFile_ID.equalsIgnoreCase("Primary"))
				{
				atchedUsr=stageEle.getAttribute("AttachedUser");
				atchedFileName=stageEle.getAttribute("FileName");
				System.out.println(atchedUsr+"+AttachedUser+FileName++"+atchedFileName);
				}
				  StatusCode historyPopListAL = new StatusCode(atchedUsr,atchedFileName);
					historyPopList.add(historyPopListAL);
			}
	   }
	  }
	}
	 catch (Exception e)
	{
		System.out.println("Y"+	e.getMessage());
		e.printStackTrace();
	}
}
	//code Change Vishnu 31Jan2019
	public void downloadDocument(String fileName) {
		try {
			System.out.println(fileName+"-=-=-=-=-=-type=-=-=-=-=-=-="+type);
			if(type.equalsIgnoreCase("Or")) {
				DownloadAttachFile(fileName, "Primary");
			}else if(type.equalsIgnoreCase("Re"))
			{
				for(HeirarchyDataBean hdb : attachmentsList) {
					if(hdb.getAttfileName().equalsIgnoreCase(urlDocname)) {
						getResubmitDocument(hdb);
//						if(viewDocattachmentsList.size() > 0) {
//							HeirarchyDataBean attachData4Table = new HeirarchyDataBean(hdb.getAttID(),hdb.getAttfileName(),hdb.getAttfileSize(),hdb.getAttfileType(),
//									hdb.getAttachStg(), hdb.getAttachUser(), hdb.getActionPerformedBy(), hdb.getActionName(), hdb.getActionPerformedOn(), hdb.getUploadedOn());
//							System.out.println(hdb.getAttID()+"-=-=-=-=-=-type=-=-=-=-=-=-="+hdb.getAttfileName());
//							viewDocattachmentsList.add(attachData4Table);
//						}
							
						break;
					}
				}
				if(viewDocattachmentsList.size() <= 0)
					DownloadAttachFile(fileName, "Attachment");
//				HeirarchyDataBean temp = 
				
			}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	////////////////
	public void esignTabClik(String fileName, String type) {
		try {
			PropUtil prop = new PropUtil();
			String hierarchyDIR = prop.getProperty("HIERARCHY_XML_DIR");
			String configFileName=prop.getProperty("HIERARCHY_CONFIG_FILE");
			String hirachyxml=prop.getProperty("DOCUMENT_XML_FILE");
			Document configdoc=Globals.openXMLFile(hierarchyDIR, configFileName);
			System.out.println("-=-=-hierarchyDIR=-=-="+hierarchyDIR);
			Document hiroc=Globals.openXMLFile(hierarchyDIR, hirachyxml);
			System.out.println("-=-=-docID=-=-="+docID);
			Element hierEle=(Element)Globals.getNodeByAttrVal(hiroc, "Document", "Document_ID", docID);
			//hierEle.getAttribute("Hierarchy_ID") ;
			
			Element downLinkEle = (Element)configdoc.getElementsByTagName("EsignURL_Link").item(0);
			String downLink = downLinkEle.getAttribute("URL").trim().isEmpty() ? "" : downLinkEle.getAttribute("URL").trim();
			String customerKey=hierEle.getAttribute("CustomerKey") == null || hierEle.getAttribute("CustomerKey").equals("") ? "" : hierEle.getAttribute("CustomerKey").toString();
			String hirsrchyID=hierEle.getAttribute("Document_ID") == null || hierEle.getAttribute("Document_ID").equals("") ? "" : hierEle.getAttribute("Document_ID").toString();
			System.out.println(customerKey+"-=-=-hirsrchyID=-=-="+hirsrchyID);
			String attachFilesEncode=URLEncoder.encode("true", "UTF-8");
			downloadURL = downLink.replace("$flag$", attachFilesEncode).replace("$docNam$", eSignDocumntNam).replace("$docID$", hirsrchyID).replace("$username$", eSignuserNam).
					replace("$custmKey$", customerKey).replace("$Index$", mailStgNo);
			System.out.println(customerKey+"-=-=-downloadURL=-=-="+downloadURL);
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	public void sendresubmituserMail(String fileName,String notes, String type) {
		try {
			
			PropUtil prop = new PropUtil();
			
			LoginProcessManager lg=new LoginProcessManager();
			String hierLeveldir = prop.getProperty("HIERARCHY_XML_DIR");
			String hierlink4Mail= prop.getProperty("HIERARCHY_BASE_URL");
			String docXmlFileName = prop.getProperty("DOCUMENT_XML_FILE");
			Document docXmlDoc = Globals.openXMLFile(hierLeveldir, docXmlFileName);
			System.out.println("docIDdocIDdocIDdocIDdocIDdocIDdocIDdocIDdocIDdocIDdocIDdocIDdocIDdocID**:"+docID);
			Node docNd=Globals.getNodeByAttrVal(docXmlDoc, "Document", "Document_ID", docID);
			Element docEle = (Element)docNd;
			String wfID = Globals.getAttrVal4AttrName(docNd, "WorkflowID");
			String documentName = Globals.getAttrVal4AttrName(docNd, "DocumentName");
			Node workFlowN =Globals.getNodeByAttrValUnderParent(docXmlDoc, docNd, "Hierarchy_ID", wfID);
			Element wfEle = (Element) workFlowN;
			String currStageNo = wfEle.getAttribute("Current_Stage_No");
			String stageNo = wfEle.getAttribute("LastAccessStage");
			String stageName = wfEle.getAttribute("LastAccessStageName");
			String adminUsername = wfEle.getAttribute("LastAccessMember");
			String action = wfEle.getAttribute("LastAccessAction");
			Element stageN = (Element) Globals.getNodeByAttrValUnderParent(docXmlDoc, workFlowN, "Stage_No", currStageNo);
			String sendMailStatus = stageN.getAttribute("Pass_Notification_To");
			
			Hashtable mailDtailsHT=lg.getMailDetailsFromConfig(docXmlDoc, workFlowN, currStageNo, adminUsername, "ResubmitedSuccess", "Next", false , this.statusDocNamestr);
			String userMailAddress[]= {adminUsername};
			String mailSendBy=(String)mailDtailsHT.get("Mail_ID_Send_by");
			String mailPassword=(String)mailDtailsHT.get("Mail_Password");
			String mailMessage=(String)mailDtailsHT.get("Mail_Message");
			String bobyofMail=mailMessage;
			String submitedUser="";
			
			submitedUser=getlatestAttachmentUser(docXmlDoc,docID,statusDocNamestr);
			
			String subject="The revised document "+statusDocNamestr+" is submitted by "+submitedUser;
			System.out.println("****revise********bobyofMail*********"+bobyofMail);
			//bobyofMail = bobyofMail.replace("$ToStage$", toStage).replace("$DueDate$", dueDate).replace("$Days$", String.valueOf(diffInDays)).replaceAll("$Members$", members);
			lg.postMail(userMailAddress, subject, bobyofMail, mailSendBy,mailPassword, null, hierlink4Mail, docEle.getAttribute("CustomerKey"));
			
			this.resubmitoutputvaluecolor="blue";
			this.resubmitoutputvalue="Revise document Mail Send Successfully"+ "("+adminUsername+")";
			
			
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	
	
	
	
	
   public static String getlatestAttachmentUser(Document levelXmlDoc,String document_ID,String attchFileName) {

		
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
	
	
	public void uploadFile22(FileUploadEvent event) throws Exception {


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
			docVersionFolder = docVersionFolder+docname.substring(0, docname.lastIndexOf("."))+"_"+docID+"\\";
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

			WorkflowManager.saveAttachment(docID, infile, infilesize, infiletype, userNam, statusStageNamestr);
			this.attachmentsList.clear();
			if(primaryDocOnlyFlag.equalsIgnoreCase("true"))
				this.attachmentsList = WorkflowManager.loadAttachmentByStageAndUser(docID, userStageNamestr, userNam);
			else
				this.attachmentsList = WorkflowManager.loadAttachmentTable(docID);

			System.out.println("attachmentsList ::::"+attachmentsList);
			System.out.println("Exiting : " + new Exception().getStackTrace()[0].getClassName() + "."
					+ new Exception().getStackTrace()[0].getMethodName());
		} catch (Exception e) {
			// Globals.getException(e);
			e.printStackTrace();
		}

	}
	
	
	
	public void uploadFile33(FileUploadEvent event) throws Exception {


		PropUtil prop = new PropUtil();
//		rbid_metadata_dir = prop.getProperty("RBID_METADATA_DIR");
		String docVersionFolder = prop.getProperty("DOCUMENTVERSION_DIR");
		String getAttachUser =this.docattachUser;
		String getAttachUserID =this.docattachID;
		String getAttachFileName =	this.docattachFilename;

		String hierDir=prop.getProperty("HIERARCHY_XML_DIR");
		String docXmlFileName = prop.getProperty("DOCUMENT_XML_FILE");
		Document docXmlDoc=Globals.openXMLFile(hierDir, docXmlFileName);
		
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
			docVersionFolder = docVersionFolder+docname.substring(0, docname.lastIndexOf("."))+"_"+docID+"\\";
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
			//statusDocNamestr=BIMetadataFile;

			if (Globals.isFileExists(docVersionFolder, BIMetadataFile)) {



				System.out.println("BI Metadata File Uploaded Successfully: " + docVersionFolder + BIMetadataFile);
			} else {

				System.out.println("*** Error: BI Metadata File is not Uplodaed: " + docVersionFolder + BIMetadataFile);
				System.out.println("*** Error: Discovery Process Aborted.");

			
				return;
			}
			
			
			
			System.out.println("*** getAttachUserID: "+getAttachUserID);
			System.out.println("*** getAttachUser: "+getAttachUser);
			System.out.println("*** getAttachFileName: "+getAttachFileName);
			System.out.println("*** docID: "+docID);
			
			
			boolean updateflag=	checkvalidatecopyFlag(docXmlDoc,docID,getAttachUserID);
			System.out.println("*** updateflag***************: "+updateflag);
			WorkflowManager.checkResubmitAttachment(docXmlDoc,docID,getAttachUserID,getAttachUser,getAttachFileName,infile,infilesize,infiletype,updateflag);
			updatedAttchFileDetails(docXmlDoc,docID,getAttachUserID,getAttachFileName,getAttachUser,infile,infilesize,infiletype);
			
			Globals.writeXMLFile(docXmlDoc, hierDir, docXmlFileName);
			statusDocNamestr = infile;		//code Change Vishnu 31Jan2019
			urlDocname = infile;
			this.attachmentsList.clear();
			
			this.attachmentsList = WorkflowManager.loadAttachmentTable(docID);
			System.out.println("Exiting : " + new Exception().getStackTrace()[0].getClassName() + "."
					+ new Exception().getStackTrace()[0].getMethodName());
		} catch (Exception e) {
			// Globals.getException(e);
			e.printStackTrace();
		}

	}
	
	
	public boolean checkvalidatecopyFlag(Document docXmlDoc,String document_ID,String rowAttID) {
		
		boolean updateFlag=true;
		System.out.println(":######################&:::::::::"+document_ID);
		String resumbitattID="";
		String attchmentID="";
		String attchmentID1="";
		String resumbitattUser="";
		String attchmentIDUser="";
		Node docND = Globals.getNodeByAttrVal(docXmlDoc, "Document", "Document_ID", document_ID);
		Element docEle = (Element) docND;
		String xmldocID = docEle.getAttribute("Document_ID");
		Node resubmits_Nd = docEle.getElementsByTagName("Resubmits").item(0);
		Node attachments_Nd = docEle.getElementsByTagName("Attachments").item(0);
		
		if(resubmits_Nd == null || attachments_Nd==null) {
			Element resubEle=docXmlDoc.createElement("Resubmits");
			docEle.appendChild(resubEle);
			System.out.println("oOOOOOOOOOOOOOOOOOOnullOOOOOOOOOOOOO");
			return updateFlag;
			
		}
		
		
		NodeList reAttachments_List = resubmits_Nd.getChildNodes();
		NodeList Attachmentxxxs_List = attachments_Nd.getChildNodes();
		
		for (int j = 0; j < Attachmentxxxs_List.getLength(); j++) {
			
			if(Attachmentxxxs_List.item(j).getNodeType() == Node.ELEMENT_NODE && Attachmentxxxs_List.item(j).getNodeName().equals("Attachment")) {

				Node nameN=Attachmentxxxs_List.item(j);
				
				if(nameN.getNodeType()==Node.ELEMENT_NODE)
				{
					Element attachEle=(Element)nameN;
					attchmentID = attachEle.getAttribute("AttachMentFile_ID");
					
					if(rowAttID.equalsIgnoreCase(attchmentID)) {
					attchmentIDUser = attachEle.getAttribute("AttachedUser");
					attchmentID1 = attachEle.getAttribute("AttachMentFile_ID");
					}
				}
				
			}
			
			
		for (int jm = 0; jm < reAttachments_List.getLength(); jm++) {
			if(reAttachments_List.item(jm).getNodeType() == Node.ELEMENT_NODE && reAttachments_List.item(jm).getNodeName().equals("Resubmit")) {

				Node nameN=reAttachments_List.item(jm);
				if(nameN.getNodeType()==Node.ELEMENT_NODE)
				{
					Element attachEle=(Element)nameN;
					resumbitattID = attachEle.getAttribute("AttachMentFile_ID");
					resumbitattUser = attachEle.getAttribute("AttachedUser");
					
					System.out.println(resumbitattID+"attchmentID1**************"+attchmentID1);
					if(attchmentID1.equalsIgnoreCase(resumbitattID) && attchmentIDUser.equalsIgnoreCase(resumbitattUser))
					{
						
						updateFlag=false;
					}
					
				}
				
			}
			
			
		}
		}
		
		
		System.out.println("updateFlag:*************"+updateFlag);
		
		return updateFlag;
	}
	
	
	
	
	public void updatedAttchFileDetails(Document docXmlDoc,String docId, String att_id,String att_fileName,String att_attchuser,String infile,String infilesize,String infiletype) {
	try {
		
		
		PropUtil prop = new PropUtil();
		String	DateFormat=prop.getProperty("DATE_FORMAT");
		SimpleDateFormat sdf = new SimpleDateFormat(DateFormat);
		
		Node docND = Globals.getNodeByAttrVal(docXmlDoc, "Document", "Document_ID", docId);
		Element docEle = (Element) docND;
		String xmldocID = docEle.getAttribute("Document_ID");
		Node Attachments_Nd = docEle.getElementsByTagName("Attachments").item(0);
		Element copyOfAttacNodeTempEle=null;
	//	Node addAttacNodeTemp =null;
		
		if(Attachments_Nd == null) {
			return;
		}
		
		NodeList Attachments_List = Attachments_Nd.getChildNodes();

		for (int j = 0; j < Attachments_List.getLength(); j++) {
			if(Attachments_List.item(j).getNodeType() == Node.ELEMENT_NODE && Attachments_List.item(j).getNodeName().equals("Attachment")) {

				Node nameN=Attachments_List.item(j);
				if(nameN.getNodeType()==Node.ELEMENT_NODE)
				{
					Element attachEle=(Element)nameN;
					String attID = attachEle.getAttribute("AttachMentFile_ID");
					String attachUser = attachEle.getAttribute("AttachedUser");
					
					System.out.println(attID+":attID***TTTTTTT******* :"+att_id);
					System.out.println(attachUser+" : attachUser***EEEEEEEEEEE************ :"+att_attchuser);
					
					
					if(attID.equalsIgnoreCase(att_id)&& attachUser.equalsIgnoreCase(att_attchuser))
					{
						
						String ss=attachEle.getAttribute("FileName");
						
						attachEle.removeAttribute("FileName");
						attachEle.removeAttribute("FileSize");
						attachEle.removeAttribute("FileType");
						attachEle.removeAttribute("UploadedDate");
						attachEle.setAttribute("FileName", infile);
						attachEle.setAttribute("FileSize", infilesize);
						attachEle.setAttribute("FileType", infiletype);
						attachEle.setAttribute("UploadedDate", sdf.format(new Date()));
						
												
						System.out.println(ss+":infile *********RRRRRRR******** :"+infile);
						System.out.println("infilesize *****RRRRRRRRR************ :"+infilesize);
						System.out.println("infiletype ******EEEEEEEEEE*********** :"+infiletype);
						System.out.println("sdf.format(new Date()) *****EEEEEEEEEEEE************ :"+sdf.format(new Date()));
						
					}
					
				

					
				}
				
			}
			
			
		}
		
		
	//	Globals.writeXMLFile(docXmlDoc, hierDir, docXmlFileName);
		
	} catch (Exception e) {
		// Globals.getException(e);
		e.printStackTrace();
	}

}

	
	
	
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
			docVersionFolder = docVersionFolder+docname.substring(0, docname.lastIndexOf("."))+"_"+docID+"\\";
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

			WorkflowManager.saveAttachment(docID, infile, infilesize, infiletype, userNam, statusStageNamestr);
			this.attachmentsList.clear();
			if(primaryDocOnlyFlag.equalsIgnoreCase("true"))
				this.attachmentsList = WorkflowManager.loadAttachmentByStageAndUser(docID, userStageNamestr, userNam);
			else
				this.attachmentsList = WorkflowManager.loadAttachmentTable(docID);

			System.out.println("attachmentsList ::::"+attachmentsList);
			System.out.println("Exiting : " + new Exception().getStackTrace()[0].getClassName() + "."
					+ new Exception().getStackTrace()[0].getMethodName());
		} catch (Exception e) {
			// Globals.getException(e);
			e.printStackTrace();
		}

	}
	public void setDocID1(String docID1) {
		this.docID1 = docID1;
	}
	private String selNotesvalue;
	private String selNotesvalue4Attach;
	private boolean switchComponents = false;
	ArrayList membersNameAL = new ArrayList<>();
	
	public ArrayList statusCodeArr=new ArrayList<>();
	
	
	public ArrayList getMembersNameAL() {
		return membersNameAL;
	}
	public void setMembersNameAL(ArrayList membersNameAL) {
		this.membersNameAL = membersNameAL;
	}
	public String getSelvalue() {
		return selvalue;
	}
	public void setSelvalue(String selvalue) {
		this.selvalue = selvalue;
	}
	
	public StatusCode() {
		// TODO Auto-generated constructor stub
		HttpServletRequest req = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
		this.urlDocID=req.getParameter("documentId");
		this.urlUserName=req.getParameter("username");
		this.resubmitoutputvaluecolor="";
		this.resubmitoutputvalue="";
		this.eSignDocumentID =req.getParameter("documentId");
		this.eSignDocumntNam=req.getParameter("documentName");
		this.eSignuserNam=req.getParameter("username");
		
	}
	
	
	
	public StatusCode(String atchedUsr, String atchedFileName) {
		//code Change Vishnu.S 04Feb2019
		this.atchUsr=atchedUsr;
		this.atchFile=atchedFileName;
		// TODO Auto-generated constructor stub
	}
	public ArrayList validate() {
		ArrayList arrList = new ArrayList<>();
		try {
			
			PropUtil prop = new PropUtil();
			String HIERARCHY_XML_DIR = prop.getProperty("HIERARCHY_XML_DIR");
			String hierarchyXmlFileName = prop.getProperty("HIERARCHY_XML_FILE");
			String docXmlFileName = prop.getProperty("DOCUMENT_XML_FILE");
			String configFileName = prop.getProperty("HIERARCHY_CONFIG_FILE");
			Document wfDoc = Globals.openXMLFile(HIERARCHY_XML_DIR, hierarchyXmlFileName);
			Document docXmlDoc = Globals.openXMLFile(HIERARCHY_XML_DIR, docXmlFileName);
			Node docNd = Globals.getNodeByAttrVal(docXmlDoc, "Document", "Document_ID", docID);
			Element docEle = (Element)docNd;
			String revisionType = docEle.getAttribute("RevisionType");
			esignStageLevelEnabled = revisionType.equalsIgnoreCase("Online") ? true : false;
			NodeList hierNdList = docNd.getChildNodes();
			for (int i = 0; i < hierNdList.getLength(); i++) {
				if (hierNdList.item(i).getNodeType() == Node.ELEMENT_NODE
						&& hierNdList.item(i).getNodeName().equals("Workflow")) {
					Node workflowNode = hierNdList.item(i);
					Element wrkEle = (Element) workflowNode;
					String crrStageName = wrkEle.getAttribute("Current_Stage_Name");
					String crrStageNumr = wrkEle.getAttribute("Current_Stage_No");
					if (crrStageNumr.contains("Completed") || crrStageNumr.equalsIgnoreCase("Cancel") || crrStageNumr.equalsIgnoreCase("Rules Failed")) {
						System.out.println("crrStageNumr------------- " + crrStageNumr);
						disableUIProp=true;
						if(crrStageNumr.equalsIgnoreCase("Completed")) {
							this.outputvalue = "Document "+docname+" is completed.";
						}else if(crrStageNumr.equalsIgnoreCase("Cancel")) {
							this.outputvalue = "Document "+docname+" is canceled.";
						}else if(crrStageNumr.equalsIgnoreCase("Rules Failed")) {
							this.outputvalue = "Document "+docname+" is stopped due to rules failure.";
						}
						
						this.outputvalueSubStr = this.outputvalue;
						return new ArrayList<>();
					} else {
						int currstgNumr = Integer.valueOf(crrStageNumr);
						Node stageNode = null;
						NodeList wrkFlowNdList = workflowNode.getChildNodes();
						for (int j = 0; j < wrkFlowNdList.getLength(); j++) {
							stageNode = wrkFlowNdList.item(j);
							if (stageNode.getNodeType() == Node.ELEMENT_NODE
									&& stageNode.getNodeName().equals("Stage")) {
								Element stageEle = (Element) stageNode;
								String stagename = stageEle.getAttribute("Stage_Name");
								String stage_No = stageEle.getAttribute("Stage_No");
								String enableEsign = stageEle.getAttribute("Enable_Esign");
								if(enableEsign.trim().equalsIgnoreCase("true")) {
									
									esignEnabledStageNo = stage_No;
								}
								int stgNumr = Integer.valueOf(stage_No);
								System.out.println(stgNumr+"-----crrStageNumr------------- " + crrStageNumr);
								if ((!stagename.equals("Admin") && !stagename.equals("public"))
										&& currstgNumr == stgNumr) {
									NodeList stageNdList = stageNode.getChildNodes();
									Node statusCodeNod = null;
									String final_level = "";
									String final_stage = "";
									String property = "";
									boolean isStatusCodeNod = false;
									boolean isPropertyNod = false;
									boolean parllelUser = false;
									for (int k = 0; k < stageNdList.getLength(); k++) {
										statusCodeNod = stageNdList.item(k);

										if (statusCodeNod.getNodeType() == Node.ELEMENT_NODE
												&& statusCodeNod.getNodeName().equals("Status_Codes")) {
											NodeList statusCodeNodchild = statusCodeNod.getChildNodes();
											for (int l = 0; l < statusCodeNodchild.getLength(); l++) {
												if (statusCodeNodchild.item(l).getNodeType() == Node.ELEMENT_NODE
														&& statusCodeNodchild.item(l).getNodeName()
																.equals("Status_Code")) {
													Element statusEle = (Element) statusCodeNodchild.item(l);
													final_level = statusEle.getAttribute("level");
													if (final_level.trim().equals("Final")) {
														final_stage = statusCodeNodchild.item(l).getTextContent();
														isStatusCodeNod = true;
														break;
													}
												}
											}

										} else if (statusCodeNod.getNodeType() == Node.ELEMENT_NODE
												&& statusCodeNod.getNodeName().equals("Properties")) {
											property = statusCodeNod.getTextContent();
											isPropertyNod = true;
										}
										if (isStatusCodeNod && isPropertyNod) {
											break;
										}
									}

									for (int k = 0; k < stageNdList.getLength(); k++) {
										statusCodeNod = stageNdList.item(k);
										if (statusCodeNod.getNodeType() == Node.ELEMENT_NODE
												&& statusCodeNod.getNodeName().equals("Employee_Names")) {
											NodeList statusCodeNodchild = statusCodeNod.getChildNodes();
											for (int l = 0; l < statusCodeNodchild.getLength(); l++) {
												if (statusCodeNodchild.item(l).getNodeType() == Node.ELEMENT_NODE
														&& statusCodeNodchild.item(l).getNodeName().equals("Name")) {
													Element namEle = (Element) statusCodeNodchild.item(l);
													String stageUsrNam = statusCodeNodchild.item(l).getTextContent();
													String usrStatus = namEle.getAttribute("User_Status");
													System.out.println(property+"-----property------------- " + usrStatus+"-=-=-=-="+final_stage+"-=-=-=stageUsrNam-=-=-=-="+stageUsrNam+"-=-=-=-=-=-="+userNam);
													if (property.equals("Serial")) {
														if (!usrStatus.equals(final_stage)) {
															if (stageUsrNam.equals(userNam)) {
																String userMail = namEle.getAttribute("E-mail");
																arrList.add(userMail);
																disableUIProp=false;
															}else {
																disableUIProp=true;
															}
															break;
														}
													} else if (property.equals("Parallel")) {
														if (stageUsrNam.equals(userNam) && !usrStatus.equals(final_stage)) {
															// parllelUser=true;
															disableUIProp=false;
															break;
														}else {
															disableUIProp=true;
														}
													}

												}
											}
										}

									}

								}

							}
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return arrList;
	}
	
	public void getSelectedDocument(HeirarchyDataBean selectedData) {
		this.selectedAttachDocData = selectedData;
		this.outputvalue4Attach = "";
		this.outputvalueSubStr4Attach = "";
		this.selNotesvalue4Attach = "";
		this.docattachUser=selectedData.getAttachUser().trim().toString();
		this.docattachID=selectedData.getAttID().trim().toString();
		this.docattachFilename=selectedData.getAttfileName().trim().toString();
	}
	//code Change Vishnu 31Jan2019
	public void getSelectedDocumentDetails(String documentName) {
		if(type.equalsIgnoreCase("Or")) {
			this.docattachID="Primary";
		}else if(type.equalsIgnoreCase("re")) {
			for(HeirarchyDataBean hdb : attachmentsList) {
				if(hdb.getAttfileName().equalsIgnoreCase(urlDocname)) {
					getSelectedDocument(hdb);
					break;
				}
			}
		}
		
	}
	/////////////////////
	public void saveStatusBtnAction(String statusStr,String notes, String type) {
		try {
			System.out.println("=-=-=-=ACTION DETAILS-=-=-=-=-");
			System.out.println("=-=-=-=-=-=-=-=-"+userNam);
			System.out.println("=-=-=-=-=-=-=-=-"+notes);
			System.out.println("=-=-=-=-=-=-=-=-"+statusStr);
			System.out.println("=-=-=-=-=-=-=-=-"+docID);
			System.out.println("=-=-=-=-type=-=-=-=-"+type);
			
			System.out.println("********$$$docattachUser$$$$$************:"+docattachUser);
			System.out.println("********$$$docattachUserID$$$$$************:"+docattachID);
			System.out.println("********$$$docattachUserFilename$$$$$************:"+docattachFilename);
			Boolean bbboolean=WorkflowManager.checkEsignconfigureORnot(docID);
			
			System.out.println("********$$$bbboolean$$$$$***bbboolean*********:"+bbboolean);
			
			if(statusStr.equalsIgnoreCase("YTS") || statusStr.equalsIgnoreCase("WIP")) {
				System.out.println("currUser============YTS || WIP ");	
				PropUtil prop = new PropUtil();
				String hierDir=prop.getProperty("HIERARCHY_XML_DIR");
				String docXmlFileName = prop.getProperty("DOCUMENT_XML_FILE");
				Document docXmlDoc=Globals.openXMLFile(hierDir, docXmlFileName);
				Node docNd=Globals.getNodeByAttrVal(docXmlDoc, "Document", "Document_ID", docID);
				if(docNd == null) {
					this.outputvalue = "The status is not updated. Document "+docname+" is not found.";
					this.outputvalueSubStr = this.outputvalue;
					return;
				}
				Element docEle = (Element)docNd;
				Element wfEle = (Element)docEle.getElementsByTagName("Workflow").item(0);
				String wfId = docEle.getAttribute("WorkflowID");
				String wfName = docEle.getAttribute("WorkflowName");
				String currStgNo = wfEle.getAttribute("Current_Stage_No");
				if(!currStgNo.equalsIgnoreCase("Completed") && !currStgNo.equalsIgnoreCase("Cancel") && !currStgNo.equalsIgnoreCase("Rules Failed")) {
					Node nd = Globals.getNodeByAttrValUnderParent(docXmlDoc, wfEle, "Stage_No", currStgNo);
					if(nd != null) {
						Element ele = (Element)nd;
						Element empEle = (Element)ele.getElementsByTagName("Employee_Names").item(0);
						Node nd1 = Globals.getNodeByAttrValUnderParent(docXmlDoc, empEle, "E-mail", userNam);
						if(nd1 != null) {
							Element ele1 = (Element)nd1;
							ele1.setAttribute("User_Status", statusStr);
							Globals.writeXMLFile(docXmlDoc, hierDir, docXmlFileName);
							this.outputvalue = "The status is updated successfully.";
							this.outputvalueSubStr = this.outputvalue;
							return;
						}else {
							this.outputvalue = "The status is not updated. User "+userNam+" is not found in the document.";
							this.outputvalueSubStr = this.outputvalue;
						}
						//ele.setAttribute("", arg1);
					}else {
						this.outputvalue = "The status cannot updated.";
						this.outputvalueSubStr = this.outputvalue;
					}
				}else {
					this.outputvalue = "The status cannot updated.";
					this.outputvalueSubStr = this.outputvalue;
				}
				
				return;
			}
			if(type.equalsIgnoreCase("Primary")) {
				if(statusStr.equalsIgnoreCase("Approve") || statusStr.equalsIgnoreCase("Approved")) {
					statusStr="Approve";
				}else if(statusStr.equalsIgnoreCase("Completed") || statusStr.equalsIgnoreCase("Complete")) {
					statusStr="Completed";
				}else if(statusStr.equalsIgnoreCase("Rejected") || statusStr.equalsIgnoreCase("Reject")) {
					statusStr="Reject";
				}else if(statusStr.equalsIgnoreCase("Initiate") || statusStr.equalsIgnoreCase("Initiated")) {
					statusStr="Initiate";
				}else if(statusStr.equalsIgnoreCase("Remind") || statusStr.equalsIgnoreCase("Reminded")) {
					statusStr="Remind";
				}else if(statusStr.equalsIgnoreCase("Escalate") || statusStr.equalsIgnoreCase("Escalated")) {
					statusStr="Escalate";
				}else if(statusStr.equalsIgnoreCase("Cancel")) {
					statusStr="Cancel";
				}else if(statusStr.equalsIgnoreCase("Pause")) {
					statusStr="Pause";
				}else if(statusStr.equalsIgnoreCase("Acknowledged")) {
					statusStr="Acknowledged";
				}
				else if(statusStr.equalsIgnoreCase("Resubmit")) {
					statusStr="Resubmit";
				}
				PropUtil prop = new PropUtil();
				String hierDir=prop.getProperty("HIERARCHY_XML_DIR");
				String docXmlFileName = prop.getProperty("DOCUMENT_XML_FILE");
				Document docXmlDoc=Globals.openXMLFile(hierDir, docXmlFileName);
			//	DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
				//Calendar cal = Calendar.getInstance();
				//WorkflowManager.addLog(docXmlDoc, docID, statusStr, userNam, (String)dateFormat.format(cal.getTime()), notes);
				System.out.println("love---"+docID+"----"+userNam+"---"+statusStr);
				Node docNd=Globals.getNodeByAttrVal(docXmlDoc, "Document", "Document_ID", docID);
				Element docEle = (Element)docNd;
				Element wfEle = (Element)docEle.getElementsByTagName("Workflow").item(0);
				String wfId = docEle.getAttribute("WorkflowID");
				String workflow_Type=wfEle.getAttribute("Workflow_Type") == null ? "" : wfEle.getAttribute("Workflow_Type").toString();
				String wfName = docEle.getAttribute("WorkflowName");
				String currStgNo = wfEle.getAttribute("Current_Stage_No");
				String totalStage = wfEle.getAttribute("Total_No_Stages");
				LoginProcessManager lpm = new LoginProcessManager();
				Hashtable stgDetailsHT = lpm.retriveStageDetailsFromXML(wfEle, currStgNo, "");
				String externalUserFlag = stgDetailsHT.get("ExternalUser") == null ? "" : stgDetailsHT.get("ExternalUser").toString();
				String autoLoginFlag = stgDetailsHT.get("Auto_Login") == null ? "" : stgDetailsHT.get("Auto_Login").toString();
				// ***********************performingAction VIJAY**
				Hashtable detailsHT = WorkflowManager.performingAction(userNam, notes, statusStr, docname, docID, "JavaWF", docattachID, docattachUser, "");		//code Change Vishnu 31Jan2019
				String str=(String)detailsHT.get("Status");
				System.out.println(statusStr+"-----------** statusStr **--------------- >> "+str);
				System.out.println("local---"+statusStr+"-----------** statusStr **--------------- >> "+str+"-----"+docID);
		
				if(str!=null && !str.trim().equals("") && str.equals("Success")) {
					
					if(statusStr.equalsIgnoreCase("Rejected") || statusStr.equalsIgnoreCase("Reject")) {
						Hashtable testHT = WorkflowManager.rejectNextUsers(docXmlDoc, wfEle, currStgNo, userNam);
						String nxtStgMsg = "";
						if(testHT != null) {
							ArrayList<String> testAL = testHT.get("Users") == null ? new ArrayList<String>() : (ArrayList<String>)testHT.get("Users");
							String stage = testHT.get("Stage") == null ? "" : (String)testHT.get("Stage");
							nxtStgMsg = stage;
							for (int i=0;i<testAL.size();i++) {
								if(workflow_Type.toLowerCase().equalsIgnoreCase("simple")) {
									
									 nxtStgMsg = (i == 0 ? "(":"")+testAL.get(i)+(i < testAL.size()-1 ? ", " : "")+(i == testAL.size()-1 ? ")" : "");
								}else{
									
									 nxtStgMsg = nxtStgMsg+(i == 0 ? "(":"")+testAL.get(i)+(i < testAL.size()-1 ? ", " : "")+(i == testAL.size()-1 ? ")" : "");
								}
								
							}
						}
						System.out.println("-=-=-=-=-nxtStgMsg=-=-rej=-=-status=-="+nxtStgMsg);
						if(nxtStgMsg.trim().isEmpty())
							this.outputvalue="The status is updated successfully. This document is rejected to previous team.";
						else
							if(workflow_Type.toLowerCase().equalsIgnoreCase("simple")) {
								
								this.outputvalue="The status is updated successfully. This document is rejected to "+nxtStgMsg+".";
								
						 }else {
							 
								this.outputvalue="The status is updated successfully. This document is rejected to "+nxtStgMsg+".";
						 }
//						this.outputvalue="The status is updated successfully. This document is forwarded to previous team";
						
					}else {
						Hashtable testHT = WorkflowManager.getNextDocumentUsers(docXmlDoc, wfEle, currStgNo, userNam);
						String nxtStgMsg = "";
						if(testHT != null) {
							ArrayList<String> testAL = testHT.get("Users") == null ? new ArrayList<String>() : (ArrayList<String>)testHT.get("Users");
							String stage = testHT.get("Stage") == null ? "" : (String)testHT.get("Stage");
							nxtStgMsg = stage;
							for (int i=0;i<testAL.size();i++) {
								if(workflow_Type.toLowerCase().equalsIgnoreCase("simple")) {
									nxtStgMsg = (i == 0 ? " ":"")+testAL.get(i)+(i < testAL.size()-1 ? ", " : "")+(i == testAL.size()-1 ? " " : "");
									
									
								}else {
									
									nxtStgMsg = nxtStgMsg+(i == 0 ? " (":"")+testAL.get(i)+(i < testAL.size()-1 ? ", " : "")+(i == testAL.size()-1 ? ")" : "");
									
								}
								 
							}
						}
						System.out.println("-=-=-=-=-nxtStgMsg=-=-=-=-status=-="+nxtStgMsg);
						if(nxtStgMsg.trim().isEmpty())
							this.outputvalue="The status is updated successfully. This document is forwarded to next team.";
						else if(nxtStgMsg.trim().equalsIgnoreCase("Rules Failed"))
							this.outputvalue="This document will not be forwarded to next stage because of rules failure.";
						else
							if(Integer.valueOf(currStgNo)==Integer.valueOf(totalStage)) {
								docXmlDoc=Globals.openXMLFile(hierDir, docXmlFileName);
								docNd=Globals.getNodeByAttrVal(docXmlDoc, "Document", "Document_ID", docID);
								docEle = (Element)docNd;
								wfEle = (Element)docEle.getElementsByTagName("Workflow").item(0);
								
								Hashtable stageDetailsHT=lpm.retriveStageDetailsFromXML(wfEle, currStgNo,"");
								Hashtable emplyeeDetailsHT=(Hashtable)stageDetailsHT.get("EmployeedetHT");
								Hashtable mssgeDetailsHT=(Hashtable)stageDetailsHT.get("MessagedetHT");
								String finalMsg=(String)mssgeDetailsHT.get("Final");
								Hashtable nxtPropertiesHT=(Hashtable)stageDetailsHT.get("PropertiesHT");
								String Properties=(String)nxtPropertiesHT.get("Properties");
								int appr=0;
								int minAprrovers=0;

								System.out.println("-=-=-=-=-Properties=-=-=-=-status=-="+Properties);
								//code change pandian 19MAR2014
								boolean completeFlag = false; 
								if(Properties.equalsIgnoreCase("Parallel")) {
									String minApprovers = (String)nxtPropertiesHT.get("Min_Approvers");
									int fromxmlminapprovers=Integer.parseInt(minApprovers);
									for (int approv = 0; approv < emplyeeDetailsHT.size(); approv++) {
										Hashtable ApproversHT=new Hashtable();
										ApproversHT=(Hashtable)emplyeeDetailsHT.get(approv);
										String userStatus=(String)ApproversHT.get("User_Status");								
										if(userStatus.equals(finalMsg)){

											minAprrovers=minAprrovers+1;
											appr++;
										}
									}
									if(minAprrovers>=fromxmlminapprovers){
										completeFlag = true;
									}
								}else {
									Hashtable ApproversHT=new Hashtable();
									ApproversHT=(Hashtable)emplyeeDetailsHT.get(emplyeeDetailsHT.size()-1);
									String userStatus=(String)ApproversHT.get("User_Status");	
									System.out.println(finalMsg+" : -=-=-=-=-userStatus=-=-=-=-status=-=: "+userStatus);
									if(userStatus.equalsIgnoreCase(finalMsg)){

										completeFlag = true;
									}
								}
								System.out.println(workflow_Type+"-=-=-=-=-completeFlag=-=-=-=-status=-="+completeFlag);
								
								if(completeFlag)
									this.outputvalue="This document is approved by all parties concerned. A copy of this document is sent to all members.";
								else
									if(workflow_Type.toLowerCase().equalsIgnoreCase("simple")) {
										
										this.outputvalue="Document is approved successfully. This document has been sent to "+nxtStgMsg+".";
										
								 }else {
									this.outputvalue="Document is approved successfully. This document has been sent to "+nxtStgMsg+".";
										}
							}
								
							else
								if(workflow_Type.toLowerCase().equalsIgnoreCase("simple")) {
									
									this.outputvalue="Document is approved successfully. This document has been sent to "+nxtStgMsg+".";
									
							 }else {
								this.outputvalue="Document is approved successfully. This document has been sent to "+nxtStgMsg+".";
							 }
						if(externalUserFlag.equalsIgnoreCase("true")) {
//							this.outputvalue="The document is uploaded successfully. We will update you on the progress in 2 - 5 business days.";
//							this.outputvalueSubStr = "The document is uploaded successfully. We will update you on the progress in 2 - 5 business days.";
							this.outputvalue="This document is processed successfully.";
						}else {
//							this.outputvalue="The status is updated successfully. This document is forwarded to next team.";
//							this.outputvalueSubStr = "The status is updated successfully. This document is forwarded to next team.";
						}
						
					}
					this.outputvalueSubStr = this.outputvalue;
					//this.outputvalueSubStr = this.outputvalue.length()> 90 ? this.outputvalue.substring(0, 90) + "..." : this.outputvalue;
					disableUIProp=true;
//					if(outputvalueSubStr!=null && outputvalueSubStr.length()>80) {
//						outputvalueSubStr = outputvalueSubStr.substring(0, 78)+"..";
//					}
				}
			}else {
				System.out.println(selectedAttachDocData.getAttachUser()+"-=-=-=-=attachUser-=-=-=-="+selectedAttachDocData.getUploadDetails());
				if(statusStr != null) {
					if(statusStr.equalsIgnoreCase("Resubmit")) {
					
						Hashtable detailsHT = WorkflowManager.performingAction(userNam, notes, statusStr, docname, docID, "JavaWF", docattachID, docattachUser, docattachFilename);
					
					}
					
					else {
					RulesManager.performActionOnAttachmentDocument(statusStr, selectedAttachDocData, docID, userNam, notes);
					
					}
					
					
					
					
//					if(selectedStatus.equals("Rejected")){
//						message="The status is updated successfully. This document is forwarded to previous team.";
//					}else{
					
					
					outputvalue4Attach="The status of the document is updated to the uploaded user.";
//					}
					outputvalueSubStr4Attach = outputvalue4Attach;
	
					if(!primaryDocOnlyFlag.equalsIgnoreCase("true"))
						this.attachmentsList = WorkflowManager.loadAttachmentTable(docID);
					else
						this.attachmentsList = WorkflowManager.loadAttachmentByStageAndUser(docID, statusStageNamestr, userNam);
				}
			}
			
			//Hashtable detailsHT = WorkflowManager.performingAction(userNam, notes, statusStr, docname, docID);
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	public void  getResubmitDocument(HeirarchyDataBean selectedData) throws Exception{
		
		System.out.println("^^^^^^^^^^^^RRRRRRRR^^^^^^^^^^^ :"+docID);
		this.resubmitoutputvalue="";
		this.resubmitoutputvaluecolor="";
		this.viewDocattachmentsList=WorkflowManager.loadResubmitTable(docID,selectedData);
		if(viewDocattachmentsList.size() <= 0)		//code Change Vishnu 31Jan2019
			DownloadAttachFile(selectedData.getAttfileName(), "Attachment");
	}
			
	
	
	private ArrayList getStageDetails(Node stageNode,String userNam) {
		// TODO Auto-generated method stub
		ArrayList arrlist=new ArrayList<>();
		try {
			boolean isContain=false;
			NodeList stageChildNods=stageNode.getChildNodes();
			for (int i = 0; i < stageChildNods.getLength(); i++) {
				Node empNode=stageChildNods.item(i);
				if(empNode.getNodeType()==Node.ELEMENT_NODE && empNode.getNodeName().equals("Employee_Names")){
					NodeList empChildNods=empNode.getChildNodes();
					for (int j = 0; j < empChildNods.getLength(); j++) {
						if(empChildNods.item(j).getNodeType()==Node.ELEMENT_NODE && empChildNods.item(j).getNodeName().equals("Name")){
							String message=empChildNods.item(j).getTextContent();
							if(message.equalsIgnoreCase(userNam)) {
								isContain=true;
								break;
							}
						}
					}
					
					//if(isContain) {
						for (int j = 0; j < empChildNods.getLength(); j++) {
							if(empChildNods.item(j).getNodeType()==Node.ELEMENT_NODE && empChildNods.item(j).getNodeName().equals("Name")){
								Element memerEle=(Element)empChildNods.item(j);
								String mailStr=memerEle.getAttribute("E-mail");
								arrlist.add(mailStr);
								
							}
						}
					//}
				}
				
			}
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return arrlist;
	}
	private void loadStageStatus(String documentId, String docname, String userNam) {
		// TODO Auto-generated method stub
		try {
			primaryDocOnlyFlag = "";
			uploadFileFlag = "";
			statusCode4DocsFlag = "";
			statusCodeArr.clear();
			this.historyPopList.clear();
			Hashtable detailsHT = new Hashtable<>();
			PropUtil prop = new PropUtil();
			String HIERARCHY_XML_DIR = prop.getProperty("HIERARCHY_XML_DIR");
			String hierarchyXmlFileName = prop.getProperty("HIERARCHY_XML_FILE");
			String docXmlFileName = prop.getProperty("DOCUMENT_XML_FILE");
			String configFileName = prop.getProperty("HIERARCHY_CONFIG_FILE"); 
			Document wfDoc = Globals.openXMLFile(HIERARCHY_XML_DIR, hierarchyXmlFileName);
			Document docXmlDoc = Globals.openXMLFile(HIERARCHY_XML_DIR, docXmlFileName);
			
			Element docNdELE = (Element)Globals.getNodeByAttrVal(docXmlDoc, "Document", "Document_ID", docID);
			Element resubmitsNdELE = (Element)Globals.getChildNodeByAttrVal(docXmlDoc, "Document", "Document_ID", docID,"Resubmits");
			
			Node docNd = Globals.getNodeByAttrVal(docXmlDoc, "Document", "Document_ID", documentId);
			
			Element wName=(Element)docNd;
			String crrWfName =wName.getAttribute("WorkflowName");
			this.docname = wName.getAttribute("DocumentName");		//code Change Vishnu 31Jan2019
			NodeList hierNdList=docNd.getChildNodes();			
			boolean currentUser = false;
			LoginProcessManager lpm = new LoginProcessManager();
			for(int i=0;i<hierNdList.getLength();i++){
				if(hierNdList.item(i).getNodeType()==Node.ELEMENT_NODE && hierNdList.item(i).getNodeName().equals("Workflow")){
					
					Node workflowNode=hierNdList.item(i);
					Element wrkEle=(Element)workflowNode;
					String crrStageName=wrkEle.getAttribute("Current_Stage_Name");
					String crrStageNo=wrkEle.getAttribute("Current_Stage_No");
					this.statusUserNamestr=userNam;
					this.statusWFNamestr=crrWfName;
					//this.statusDocNamestr=docname;
					this.statusStageNamestr=crrStageName;
					this.userStageNamestr=crrStageName;
					this.statusDocNamestr=docNdELE.getAttribute("Primary_FileName");            //code Change Vishnu.S 04Feb2019
					if(!crrStageNo.equalsIgnoreCase("Completed") && !crrStageNo.equalsIgnoreCase("Rules Failed") && !crrStageNo.equalsIgnoreCase("Cancel")) {
						currStageNo = crrStageNo;
						Hashtable currStgDetailsHT = lpm.retriveStageDetailsFromXML(wrkEle, crrStageNo, "");
						Hashtable currentemplyeeDetailsHT=(Hashtable)currStgDetailsHT.get("EmployeedetHT");
						Hashtable mssgeDetailsHT=(Hashtable)currStgDetailsHT.get("MessagedetHT");
						Hashtable curentPropertiesHT=(Hashtable)currStgDetailsHT.get("PropertiesHT");
						String Properties=(String)curentPropertiesHT.get("Properties");
						String finalMsg = (String)mssgeDetailsHT.get("Final");
						externalUserFlag = currStgDetailsHT.get("ExternalUser") == null ? "" : currStgDetailsHT.get("ExternalUser").toString();
						autoLoginFlag = currStgDetailsHT.get("Auto_Login") == null ? "" : currStgDetailsHT.get("Auto_Login").toString();
						System.out.println("Change_PrimaryFileOnly:::::>>>"+currStgDetailsHT.get("Change_PrimaryFileOnly"));
						reuploadShowFlag=currStgDetailsHT.get("Change_PrimaryFileOnly") == null ? "" : currStgDetailsHT.get("Change_PrimaryFileOnly").toString(); 		//code Change Vishnu.S 04Feb2019
						if(resubmitsNdELE!=null) {historydataTableXmlRead();}
						
						uploadFileFlag = currStgDetailsHT.get("Allow_Upload") == null ? "" : currStgDetailsHT.get("Allow_Upload").toString();
						
						if(Properties.equalsIgnoreCase("Serial")) {
							for (int approv = 0; approv < currentemplyeeDetailsHT.size(); approv++) {
								Hashtable ApproversHT=new Hashtable();
								ApproversHT=(Hashtable)currentemplyeeDetailsHT.get(approv);
								String member=(String)ApproversHT.get("empName");
								String userStatus=(String)ApproversHT.get("User_Status");
								if(!userStatus.equalsIgnoreCase(finalMsg)) {
									if(userNam.equalsIgnoreCase(member)) {

										statusCode4DocsFlag = currStgDetailsHT.get("StatusCode4Documents") == null ? "" : currStgDetailsHT.get("StatusCode4Documents").toString();
										primaryDocOnlyFlag = currStgDetailsHT.get("SendPrimaryFileOnly") == null ? "" : currStgDetailsHT.get("SendPrimaryFileOnly").toString();
										currentUser = true;
									}
									break;
								}
							}
						}else {
							for (int approv = 0; approv < currentemplyeeDetailsHT.size(); approv++) {
								Hashtable ApproversHT=new Hashtable();
								ApproversHT=(Hashtable)currentemplyeeDetailsHT.get(approv);
								String member=(String)ApproversHT.get("empName");
								String userStatus=(String)ApproversHT.get("User_Status");
								if(!userStatus.equalsIgnoreCase(finalMsg)) {
									if(userNam.equalsIgnoreCase(member)) {

										statusCode4DocsFlag = currStgDetailsHT.get("StatusCode4Documents") == null ? "" : currStgDetailsHT.get("StatusCode4Documents").toString();
										primaryDocOnlyFlag = currStgDetailsHT.get("SendPrimaryFileOnly") == null ? "" : currStgDetailsHT.get("SendPrimaryFileOnly").toString();
										currentUser = true;
										break;
									}
									//								break;
								}
							}
						}
						Iterator<Entry> it = mssgeDetailsHT.entrySet().iterator();
						while(it.hasNext()) {
							Entry ent = it.next();
							statusCodeArr.add(ent.getValue().toString());
						}
					}else {
						crrStageNo=wrkEle.getAttribute("Total_No_Stages");
					}
					System.out.println("-=-=-=-currentUser=-=-=-=-="+currentUser);
					if(!currentUser) {
						int currStg = Integer.valueOf(crrStageNo);
						for(int j = currStg; j>=1;j--) {
							Hashtable currStgDetailsHT = lpm.retriveStageDetailsFromXML(wrkEle, String.valueOf(j), "");
							Hashtable currentemplyeeDetailsHT=(Hashtable)currStgDetailsHT.get("EmployeedetHT");
							System.out.println(j+"-=-=-=-j=-=-=-=-="+currStgDetailsHT);
							boolean flag = false;
							for (int approv = 0; approv < currentemplyeeDetailsHT.size(); approv++) {
								Hashtable ApproversHT=new Hashtable();
								ApproversHT=(Hashtable)currentemplyeeDetailsHT.get(approv);
								String member=(String)ApproversHT.get("empName");
								if(userNam.equalsIgnoreCase(member)) {
									externalUserFlag = currStgDetailsHT.get("ExternalUser") == null ? "" : currStgDetailsHT.get("ExternalUser").toString();
									autoLoginFlag = currStgDetailsHT.get("Auto_Login") == null ? "" : currStgDetailsHT.get("Auto_Login").toString();

									uploadFileFlag = currStgDetailsHT.get("Allow_Upload") == null ? "" : currStgDetailsHT.get("Allow_Upload").toString();
									primaryDocOnlyFlag = currStgDetailsHT.get("SendPrimaryFileOnly") == null ? "" : currStgDetailsHT.get("SendPrimaryFileOnly").toString();
									this.userStageNamestr=currStgDetailsHT.get("Stage_Name") == null ? "" : currStgDetailsHT.get("Stage_Name").toString();
									flag = true;
									break;
								}
							}
							if(flag)
								break;
						}
					}
				}
			}
			
			
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	public void processMenu(String menuname)
	{
		FacesContext ctx = FacesContext.getCurrentInstance();
		ExternalContext extContext = ctx.getExternalContext();
		String url = extContext.encodeActionURL(ctx.getApplication().getViewHandler()
				.getActionURL(ctx, "/" + menuname));
		System.out.println("menuname::::"+menuname);
		try {
			extContext.redirect(url);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void valuechange() {
		try {
			
			status=selvalue;
			
			System.out.println("status::::"+switchComponents);
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	public boolean isSwitchComponents() {
		return switchComponents;
	}
	public void setSwitchComponents(boolean switchComponents) {
		this.switchComponents = switchComponents;
	}
	public ArrayList getStatusCodeArr() {
		return statusCodeArr;
	}
	public void setStatusCodeArr(ArrayList statusCodeArr) {
		this.statusCodeArr = statusCodeArr;
	}
	public String getSelNotesvalue() {
		return selNotesvalue;
	}
	public void setSelNotesvalue(String selNotesvalue) {
		this.selNotesvalue = selNotesvalue;
	}
	public String getOutputvalue() {
		return outputvalue;
	}
	public void setOutputvalue(String outputvalue) {
		this.outputvalue = outputvalue;
	}
	public boolean isDisableUIProp() {
		return disableUIProp;
	}
	public void setDisableUIProp(boolean disableUIProp) {
		this.disableUIProp = disableUIProp;
	}
	public ArrayList<HeirarchyDataBean> getAttachmentsList() {
		return attachmentsList;
	}
	public void setAttachmentsList(ArrayList<HeirarchyDataBean> attachmentsList) {
		this.attachmentsList = attachmentsList;
	}
	public String getPrimaryDocOnlyFlag() {
		return primaryDocOnlyFlag;
	}
	public void setPrimaryDocOnlyFlag(String primaryDocOnlyFlag) {
		this.primaryDocOnlyFlag = primaryDocOnlyFlag;
	}
	public String getUploadFileFlag() {
		return uploadFileFlag;
	}
	public void setUploadFileFlag(String uploadFileFlag) {
		this.uploadFileFlag = uploadFileFlag;
	}
	public String getDownloadURL() {
		return downloadURL;
	}
	public void setDownloadURL(String downloadURL) {
		this.downloadURL = downloadURL;
	}
	public String getOutputvalueSubStr() {
		return outputvalueSubStr;
	}
	public void setOutputvalueSubStr(String outputvalueSubStr) {
		this.outputvalueSubStr = outputvalueSubStr;
	}
	public String getStatusCode4DocsFlag() {
		return statusCode4DocsFlag;
	}
	public void setStatusCode4DocsFlag(String statusCode4DocsFlag) {
		this.statusCode4DocsFlag = statusCode4DocsFlag;
	}
	public String getAutoLoginFlag() {
		return autoLoginFlag;
	}
	public void setAutoLoginFlag(String autoLoginFlag) {
		this.autoLoginFlag = autoLoginFlag;
	}
	public String getExternalUserFlag() {
		return externalUserFlag;
	}
	public void setExternalUserFlag(String externalUserFlag) {
		this.externalUserFlag = externalUserFlag;
	}
	public String getOutputvalue4Attach() {
		return outputvalue4Attach;
	}
	public void setOutputvalue4Attach(String outputvalue4Attach) {
		this.outputvalue4Attach = outputvalue4Attach;
	}
	public String getOutputvalueSubStr4Attach() {
		return outputvalueSubStr4Attach;
	}
	public void setOutputvalueSubStr4Attach(String outputvalueSubStr4Attach) {
		this.outputvalueSubStr4Attach = outputvalueSubStr4Attach;
	}
	public String getSelvalue4Attach() {
		return selvalue4Attach;
	}
	public void setSelvalue4Attach(String selvalue4Attach) {
		this.selvalue4Attach = selvalue4Attach;
	}
	public HeirarchyDataBean getSelectedAttachDocData() {
		return selectedAttachDocData;
	}
	public void setSelectedAttachDocData(HeirarchyDataBean selectedAttachDocData) {
		this.selectedAttachDocData = selectedAttachDocData;
	}
	public String getSelNotesvalue4Attach() {
		return selNotesvalue4Attach;
	}
	public void setSelNotesvalue4Attach(String selNotesvalue4Attach) {
		this.selNotesvalue4Attach = selNotesvalue4Attach;
	}
	public static String getUrlDocID() {
		return urlDocID;
	}
	public static void setUrlDocID(String urlDocID) {
		StatusCode.urlDocID = urlDocID;
	}
	public static String getUrlUserName() {
		return urlUserName;
	}
	public static void setUrlUserName(String urlUserName) {
		StatusCode.urlUserName = urlUserName;
	}
	public String getDocattachUser() {
		return docattachUser;
	}
	public void setDocattachUser(String docattachUser) {
		this.docattachUser = docattachUser;
	}
	public String getDocattachID() {
		return docattachID;
	}
	public void setDocattachID(String docattachID) {
		this.docattachID = docattachID;
	}
	public String getDocattachFilename() {
		return docattachFilename;
	}
	public void setDocattachFilename(String docattachFilename) {
		this.docattachFilename = docattachFilename;
	}
	public String getUrlDocname() {
		return urlDocname;
	}
	public void setUrlDocname(String urlDocname) {
		this.urlDocname = urlDocname;
	}
	public String getReuploadShowFlag() {
		return reuploadShowFlag;
	}
	public void setReuploadShowFlag(String reuploadShowFlag) {
		this.reuploadShowFlag = reuploadShowFlag;
	}
	public String getAtchFile() {
		return atchFile;
	}
	public void setAtchFile(String atchFile) {
		this.atchFile = atchFile;
	}
	public String getAtchUsr() {
		return atchUsr;
	}
	public void setAtchUsr(String atchUsr) {
		this.atchUsr = atchUsr;
	}
	public ArrayList<StatusCode> getHistoryPopList() {
		return historyPopList;
	}
	public void setHistoryPopList(ArrayList<StatusCode> historyPopList) {
		this.historyPopList = historyPopList;
	}

	
	
	
	
	
	
	
	
	public String getOnlyshowADVworkflow() {
		return onlyshowADVworkflow;
	}
	public String getStatusDocNamestrshowValue() {
		return statusDocNamestrshowValue;
	}
	public void setStatusDocNamestrshowValue(String statusDocNamestrshowValue) {
		this.statusDocNamestrshowValue = statusDocNamestrshowValue;
	}
	public String getResubmitoutputvaluecolor() {
		return resubmitoutputvaluecolor;
	}
	public void setResubmitoutputvaluecolor(String resubmitoutputvaluecolor) {
		this.resubmitoutputvaluecolor = resubmitoutputvaluecolor;
	}
	public String getResubmitoutputvalue() {
		return resubmitoutputvalue;
	}
	public void setResubmitoutputvalue(String resubmitoutputvalue) {
		this.resubmitoutputvalue = resubmitoutputvalue;
	}
	public void setOnlyshowADVworkflow(String onlyshowADVworkflow) {
		this.onlyshowADVworkflow = onlyshowADVworkflow;
	}
	private String statusDisplayMss = "";
	private String statusDisplayMss1 = "";
	private String statusDisplayMssdisplay = "none";
	
	public String getStatusDisplayMssdisplay() {
		return statusDisplayMssdisplay;
	}
	public void setStatusDisplayMssdisplay(String statusDisplayMssdisplay) {
		this.statusDisplayMssdisplay = statusDisplayMssdisplay;
	}
	public String getStatusDisplayMss1() {
		return statusDisplayMss1;
	}
	public void setStatusDisplayMss1(String statusDisplayMss1) {
		this.statusDisplayMss1 = statusDisplayMss1;
	}
	public String getStatusDisplayMss() {
		return statusDisplayMss;
	}
	public void setStatusDisplayMss(String statusDisplayMss) {
		this.statusDisplayMss = statusDisplayMss;
	}
	public String getStatusDisplayFlag() {
		return statusDisplayFlag;
	}
	public void setStatusDisplayFlag(String statusDisplayFlag) {
		this.statusDisplayFlag = statusDisplayFlag;
	}
	public String getStatusUserNamestr() {
		return statusUserNamestr;
	}
	public void setStatusUserNamestr(String statusUserNamestr) {
		this.statusUserNamestr = statusUserNamestr;
	}
	public String getStatusWFNamestr() {
		return statusWFNamestr;
	}
	public void setStatusWFNamestr(String statusWFNamestr) {
		this.statusWFNamestr = statusWFNamestr;
	}
	public String getStatusDocNamestr() {
		return statusDocNamestr;
	}
	public void setStatusDocNamestr(String statusDocNamestr) {
		this.statusDocNamestr = statusDocNamestr;
	}
	public String getStatusStageNamestr() {
		return statusStageNamestr;
	}
	public void setStatusStageNamestr(String statusStageNamestr) {
		this.statusStageNamestr = statusStageNamestr;
	}
	public String getStatusCodeTitle() {
		return statusCodeTitle;
	}
	public void setStatusCodeTitle(String statusCodeTitle) {
		this.statusCodeTitle = statusCodeTitle;
	}
	
}
