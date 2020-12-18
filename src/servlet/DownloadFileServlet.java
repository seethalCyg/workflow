package servlet;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import org.apache.commons.lang.ArrayUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.google.api.services.drive.Drive;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.xml.xmp.XmpWriter;

import managers.FormsManager;
import managers.GoogleStorageMaintenace;
import managers.LoginProcessManager;
import managers.WorkflowManager;
import utils.Globals;
import utils.PropUtil;  

@WebServlet(name = "DownloadFileServlet", urlPatterns = {"/download"})
public class DownloadFileServlet extends HttpServlet {  

	public void doGet(HttpServletRequest request, HttpServletResponse response)  
			throws ServletException, IOException {  
		try {
			
			String fileName = request.getParameter("filename");
			fileName = URLDecoder.decode(fileName, "UTF-8");
			String docId = request.getParameter("docID");
			String filepath = request.getParameter("filepath") == null ? "" : request.getParameter("filepath").trim();
			String downloadUsername = request.getParameter("username") == null ? "" : request.getParameter("username").trim();
			String downloadFrom = request.getParameter("downloadFrom") == null ? "" : request.getParameter("downloadFrom").trim();
			String attachmentType = request.getParameter("attachmentType") != null ? request.getParameter("attachmentType").trim() : "Primary";
			String downloadFlag = request.getParameter("flag") != null ? request.getParameter("flag").trim() : "";
			String stageNo = request.getParameter("index") != null ? request.getParameter("index").trim() : "";
			System.out.println(fileName+"-=-=-=-=-=filepath-=-=-=-=-=-=-="+downloadFrom+"-=-=-=-=-=-=-=-="+docId+"-=-=-=-=-=-=-=-=-="+downloadFlag);
			String username = "";
			ArrayList<String> tempAL = WorkflowManager.getExternalApiDetailsFromXML(docId);
			if(tempAL == null){
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				response.getWriter().write("Couldn't find the file.");
				return;
			}
			PropUtil prop = new PropUtil();
			String metadataDir = prop.getProperty("HIERARCHY_XML_DIR");
			String docXmlFileName = prop.getProperty("DOCUMENT_XML_FILE");
			String configFileName=prop.getProperty("HIERARCHY_CONFIG_FILE");
			String priDocName = tempAL.get(0);
			username = tempAL.get(1);
			Hashtable tempHT = FormsManager.getTemplateNdFromDocId(docId);
			Node templateNd = tempHT == null ? null : (Node)tempHT.get("TemplateNode");
			if(templateNd != null && downloadFlag.equals("")) {
				
				Document doc = Globals.openXMLFile(metadataDir, docXmlFileName);
				Element docEle = (Element)Globals.getNodeByAttrVal(doc, "Document", "Document_ID", docId);
				Element wfNd = (Element)docEle.getElementsByTagName("Workflow").item(0);
				LoginProcessManager lpm = new LoginProcessManager();
				String totalStgNd = wfNd.getAttribute("Total_No_Stages");
				String docName = docEle.getAttribute("DocumentName");
				String currStgNoStr = wfNd.getAttribute("Current_Stage_No");
				boolean enableEsignFlag = false;
				enableEsignFlag = docEle.getAttribute("RevisionType").equalsIgnoreCase("Online") ? true : false;
				if(!currStgNoStr.equalsIgnoreCase("Completed") && !currStgNoStr.equalsIgnoreCase("Cancel") && !currStgNoStr.equalsIgnoreCase("Rules Failed")) {
					int currStgNo = Integer.valueOf(currStgNoStr);
					int count = Integer.valueOf(totalStgNd);
					
					int esignStgNo = 0; 
					for(int i=1;i<=count;i++) {
						Hashtable stgDetailsHT = lpm.retriveStageDetailsFromXML(wfNd, String.valueOf(i), "");
						String esignFlagStr = stgDetailsHT.get("Enable_Esign") == null ? "false" : (String)stgDetailsHT.get("Enable_Esign");
						boolean flag = esignFlagStr.trim().isEmpty() ? false : Boolean.parseBoolean(esignFlagStr);
						System.out.println(i+"::EEEEEEEEEEEEE*****enableEsignFlag*******EEEEEEEEEEEEE :"+enableEsignFlag);
						if(flag) {
							esignStgNo = i;
							enableEsignFlag = true;
							break;
						}
					}
					
					Hashtable stgDetailsHT = lpm.retriveStageDetailsFromXML(wfNd, currStgNoStr, "");
					Hashtable currentemplyeeDetailsHT=(Hashtable)stgDetailsHT.get("EmployeedetHT");
					Hashtable mssgeDetailsHT=(Hashtable)stgDetailsHT.get("MessagedetHT");
					String finalMsg=(String)mssgeDetailsHT.get("Final");
					Hashtable curentPropertiesHT=(Hashtable)stgDetailsHT.get("PropertiesHT");
					String Properties=(String)curentPropertiesHT.get("Properties");
					boolean currFlag = false;
					for (int approv = 0; approv < currentemplyeeDetailsHT.size(); approv++) {
						Hashtable ApproversHT=new Hashtable();
						ApproversHT=(Hashtable)currentemplyeeDetailsHT.get(approv);
						String member=(String)ApproversHT.get("empName");
						String userStatus=(String)ApproversHT.get("User_Status");
						System.out.println(Properties+"::EEEEEEEEEEEEE*****member*******EEEEEEEEEEEEE :"+member);
						if(Properties.equalsIgnoreCase("Parallel")) {
							String activeFlag = ApproversHT.get("Active") == null || String.valueOf(ApproversHT.get("Active")).isEmpty() ? "true" : String.valueOf(ApproversHT.get("Active"));
							Hashtable<String, String> loginDetailsHT = lpm.getLoginDetails(member, docEle.getAttribute("CustomerKey"));

							String disableFlag = loginDetailsHT.get("Disable") == null || String.valueOf(loginDetailsHT.get("Disable")).isEmpty() ? "true" : String.valueOf(loginDetailsHT.get("Disable"));
							if(activeFlag.equalsIgnoreCase("false") || disableFlag.equalsIgnoreCase("true"))
								continue;
							if(member.equalsIgnoreCase(downloadUsername) && !userStatus.equalsIgnoreCase(finalMsg)) {
								currFlag = true;
								break;
							}

						}else {
							String activeFlag = ApproversHT.get("Active") == null || String.valueOf(ApproversHT.get("Active")).isEmpty() ? "true" : String.valueOf(ApproversHT.get("Active"));
							Hashtable<String, String> loginDetailsHT = lpm.getLoginDetails(member, docEle.getAttribute("CustomerKey"));
							String disableFlag = loginDetailsHT.get("Disable") == null || String.valueOf(loginDetailsHT.get("Disable")).isEmpty() ? "true" : String.valueOf(loginDetailsHT.get("Disable"));
							System.out.println(disableFlag+"::EEEEEEEEEEEEE*****disableFlag*******EEEEEEEEEEEEE :"+activeFlag);
							System.out.println(userStatus+"::EEEEEEEEEEEEE*****disableFlag11111*******EEEEEEEEEEEEE :"+finalMsg);
							if(activeFlag.equalsIgnoreCase("false") || disableFlag.equalsIgnoreCase("true"))
								break;
							if(!userStatus.equalsIgnoreCase(finalMsg)) {
								currFlag = member.equalsIgnoreCase(downloadUsername);
								break;
							}
						}
					}
					System.out.println(downloadUsername+"-=-=-=-downloadUsername=-=-=-="+currStgNoStr+"-=-=-=-=esignStgNo-=-=-=-=-="+esignStgNo+"-=-=-=-=-=-=-="+currFlag+"-=-=-=stageNo-=-=-="+stageNo);
					currFlag = !currStgNoStr.equalsIgnoreCase(stageNo) ? false : currFlag;
					System.out.println(enableEsignFlag+"-=-=-=-enableEsignFlag=-=-=-="+currStgNoStr+"-=-=-=-=esignStgNo-=-=-=-=-="+esignStgNo+"-=-=-=-=-=-=-="+currFlag+"-=-=-=stageNo-=-=-="+stageNo);
					if(enableEsignFlag) {
						if(Globals.checkStringIsNumber(stageNo)) {
							int stgNo = Integer.parseInt(stageNo);
							if(stgNo == esignStgNo) {
								Document configdoc=Globals.openXMLFile(metadataDir, configFileName);
								Element downLinkEle = (Element)configdoc.getElementsByTagName("EsignURL_Link").item(0);
								String downLink = downLinkEle.getAttribute("URL").trim().isEmpty() ? "" : downLinkEle.getAttribute("URL").trim();
								downLink = downLink.replace("$flag$", "true").replace("$docID$", docId).replace("$username$", downloadUsername).replace("$final$", finalMsg).replace("$Index$", stageNo);
								System.out.println("-=-=-=-=-=downLink-=-=-=-=-="+downLink);
								response.sendRedirect(downLink);								
								return;
							}else if(stgNo < currStgNo && esignStgNo == 0) {
								if(fileName.equalsIgnoreCase(docName)) {
									String webUrl = docEle.getAttribute("OneDriveDownloadUrl");
									response.sendRedirect(webUrl);								
									//								return;
								}else {
									Node nd = docEle.getElementsByTagName("Attachments").item(0);
									Element fileEle = (Element)Globals.getNodeByAttrValUnderParent(doc, nd, "FileName", fileName);// getChildNodeByAttrVal(nd, "", attrName, attrValue, childNodeName)
									String webUrl = fileEle.getAttribute("OneDriveDownloadUrl");
									response.sendRedirect(webUrl);								
									//								return;
								}
								return;
							}
						}
						if(currStgNo < esignStgNo || esignStgNo == 0) {
							if(currFlag) {
								if(fileName.equalsIgnoreCase(docName)) {
									String webUrl = docEle.getAttribute("OneDriveWebUrl");
									response.sendRedirect(webUrl);								
									//								return;
								}else {
									Node nd = docEle.getElementsByTagName("Attachments").item(0);
									Element fileEle = (Element)Globals.getNodeByAttrValUnderParent(doc, nd, "FileName", fileName);// getChildNodeByAttrVal(nd, "", attrName, attrValue, childNodeName)
									String webUrl = fileEle.getAttribute("OneDriveWebUrl");
									response.sendRedirect(webUrl);								
									//								return;
								}
							}else {
								if(fileName.equalsIgnoreCase(docName)) {
									String webUrl = docEle.getAttribute("OneDriveDownloadUrl");
									response.sendRedirect(webUrl);								
									//								return;
								}else {
									Node nd = docEle.getElementsByTagName("Attachments").item(0);
									Element fileEle = (Element)Globals.getNodeByAttrValUnderParent(doc, nd, "FileName", fileName);// getChildNodeByAttrVal(nd, "", attrName, attrValue, childNodeName)
									String webUrl = fileEle.getAttribute("OneDriveDownloadUrl");
									response.sendRedirect(webUrl);								
									//								return;
								}
							}
							return;
						}else if(currStgNo == esignStgNo) {
							Document configdoc=Globals.openXMLFile(metadataDir, configFileName);
							Element downLinkEle = (Element)configdoc.getElementsByTagName("EsignURL_Link").item(0);
							String downLink = downLinkEle.getAttribute("URL").trim().isEmpty() ? "" : downLinkEle.getAttribute("URL").trim();
							downLink = downLink.replace("$flag$", "true").replace("$docID$", docId).replace("$username$", downloadUsername).replace("$final$", finalMsg).replace("$Index$", stageNo);
							System.out.println("-=-=-=-=-=downLink-=-=-=-=-="+downLink);
							response.sendRedirect(downLink);								
							return;
						}else {

						}


					}
					Element templateEle = (Element) templateNd;
					Element esignEle = templateEle.getElementsByTagName("ESign_Details").getLength() <= 0 ? null : (Element)templateEle.getElementsByTagName("ESign_Details").item(0);
					String templateName = templateEle.getAttribute("Name");
					String customerKey = tempHT == null ? "" : (String)tempHT.get("CustomerKey");
					String url = FormsManager.getUrlFromConfig("EsignHTMLFormOutput");
					String dataFileName = "";
					if(esignEle != null) {
						String metadataFilePath = esignEle.getAttribute("MetadataFilePath");
						String dataFilePath = esignEle.getAttribute("FormDataFilePath");
						File file = new File(dataFilePath);
						if(file.exists()) {
							dataFileName = dataFilePath.substring(dataFilePath.lastIndexOf("\\")+1);
							dataFileName = dataFileName.substring(0, dataFileName.lastIndexOf("."));
						}
						boolean flag = false;
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
									if(stgNo.equalsIgnoreCase("All") || (userId.equalsIgnoreCase("All") && stgNo.equalsIgnoreCase(stageNo)) || userId.equalsIgnoreCase(downloadUsername)) {
										flag = true;
										break;
									}
								}
							}

						}
						if(flag) {
							url = url.replace("$CustomerKey$", customerKey).replace("$TemplateName$", templateName).
									replace("$TemplateName$", templateName).replace("$FileName$", dataFileName).
									replace("$Emailid$", downloadUsername).replace("$DocId$", docId);
							response.sendRedirect(url);
							return;
						}else
							downloadFlag = "true";
					}
				}else if(enableEsignFlag) {
					if(fileName.equalsIgnoreCase(docName)) {
						String webUrl = docEle.getAttribute("OneDriveDownloadUrl");
						response.sendRedirect(webUrl);								
						//								return;
					}else {
						Node nd = docEle.getElementsByTagName("Attachments").item(0);
						Element fileEle = (Element)Globals.getNodeByAttrValUnderParent(doc, nd, "FileName", fileName);// getChildNodeByAttrVal(nd, "", attrName, attrValue, childNodeName)
						String webUrl = fileEle.getAttribute("OneDriveDownloadUrl");
						response.sendRedirect(webUrl);								
						//								return;
					}
				}else if(currStgNoStr.equalsIgnoreCase("Completed")) {
					Element templateEle = (Element) templateNd;
					Element esignEle = templateEle.getElementsByTagName("ESign_Details").getLength() <= 0 ? null : (Element)templateEle.getElementsByTagName("ESign_Details").item(0);
					if(esignEle!=null)
						downloadFlag = "true";
				}

				//				http://localhost:8080/rbid/formoperation/form/esign/$CustomerKey$/$TemplateName$?dataname=$FileName$&amp;id=$Emailid$

			}
			
			System.out.println(downloadFlag+" <<-----priDocName-----username-------> "+username);
			if(username == null || username.trim().isEmpty()) {
				String pdfFilePath = "";
				boolean enableEsignFlag = false;
				if(downloadFlag.equals("true")) {
					Document doc = Globals.openXMLFile(metadataDir, docXmlFileName);
					Element docEle = (Element)Globals.getNodeByAttrVal(doc, "Document", "Document_ID", docId);
					Element wfNd = (Element)docEle.getElementsByTagName("Workflow").item(0);
					LoginProcessManager lpm = new LoginProcessManager();
					String totalStgNd = wfNd.getAttribute("Total_No_Stages");
					String docName = docEle.getAttribute("DocumentName");
					String currStgNoStr = wfNd.getAttribute("Current_Stage_No");
//					if(!currStgNoStr.equalsIgnoreCase("Completed") && !currStgNoStr.equalsIgnoreCase("Cancel") && !currStgNoStr.equalsIgnoreCase("Rules Failed")) {
//						int currStgNo = Integer.valueOf(currStgNoStr);
						int count = Integer.valueOf(totalStgNd);
						
						int esignStgNo = 0; 
						for(int i=1;i<=count;i++) {
							Hashtable stgDetailsHT = lpm.retriveStageDetailsFromXML(wfNd, String.valueOf(i), "");
							String esignFlagStr = stgDetailsHT.get("Enable_Esign") == null ? "false" : (String)stgDetailsHT.get("Enable_Esign");
							enableEsignFlag = esignFlagStr.trim().isEmpty() ? false : Boolean.parseBoolean(esignFlagStr);
							esignStgNo = i;
							if(enableEsignFlag)
								break;
						}
//					}
					Hashtable<String, String> detailsHT = FormsManager.downloadPDFWithEsignDetails(docId, enableEsignFlag);
					System.out.println(String.valueOf(detailsHT.get("NewFileName"))+" <<-----dfdff-----username-------> "+String.valueOf(detailsHT.get("Status")));
					if(String.valueOf(detailsHT.get("Status")).equalsIgnoreCase("success")) {

						pdfFilePath = String.valueOf(detailsHT.get("FileChanged")).equalsIgnoreCase("true") ? String.valueOf(detailsHT.get("NewFileName")) : "";
						if(!pdfFilePath.isEmpty())
						fileName = pdfFilePath.substring(pdfFilePath.lastIndexOf("\\")+1);
					}
					System.out.println(priDocName+" <<-----pdfFilePath-----username-------> "+pdfFilePath);
				}

				if((filepath == null || filepath.isEmpty()) && pdfFilePath.isEmpty()) {

					String docVersionDir = "";
					try {
						docVersionDir = prop.getProperty("DOCUMENTVERSION_DIR");
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					String folderName = (priDocName.contains(".") ? priDocName.substring(0, priDocName.lastIndexOf(".")) : priDocName)+"_"+docId;
					String filePath = docVersionDir+folderName;
					File file = new File(filePath);
					if(!file.exists()) {
						response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
						response.getWriter().write("Couldn't find the file.");
						return;
					}
					String[] filePaths = file.list(new FilenameFilter() {
						@Override
						public boolean accept(File current, String name) {
							return new File(current, name).isDirectory();
						}
					});
					int[] folderNames = new int[filePaths.length];
					int i=0;
					for(String val : filePaths) {
						int j = 0;
						try {
							File folder = new File(filePath+"\\"+val);
							System.out.println(filePath+"\\"+val+"-=-=-=-=-val=-=-=-=-=-=-="+folder.exists());
							if(folder.exists()) {
								File tempfile = new File(filePath+"\\"+val +"\\"+ fileName);
								System.out.println(filePath+"\\"+val +"\\"+ fileName+"-=-=-=-=-val=-=ss-=-=-=-=-="+tempfile.exists());
								if(tempfile.exists()) {
									j = Integer.valueOf(val.substring(val.lastIndexOf("\\")+1));
									folderNames[i] = j;
									i++;
								}
							}

						}catch (NumberFormatException e) {
							// TODO: handle exception
							j = 0;
							folderNames[i] = j;
							i++;
						}

					}
					List<Integer> b = Arrays.asList(ArrayUtils.toObject(folderNames));
					int maxName = b.size() > 0 ? Collections.max(b) : 1;
					if(attachmentType.equalsIgnoreCase("Primary"))
						filepath = filePath+"\\"+maxName+"\\";
					else
						filepath = filePath+"\\";
					pdfFilePath = filepath + fileName;
				}else if(filepath != null && !filepath.isEmpty()) {
					pdfFilePath = filepath + fileName;
				}
				PrintWriter out = response.getWriter();  
				//			String filename = "Finance.docx";   
				//			String filepath = "E:\\Document WF WS\\Document WF\\DocumentVersion\\Finance_51\\3\\";   
				//response.setContentType("APPLICATION/OCTET-STREAM");   
				System.out.println(pdfFilePath+"-=-=-=-=-dfgfgfdg=-=-=-=-="+fileName);
				String fileNameTemp = fileName;
				String content = Globals.getContentType4File(fileNameTemp.substring(fileNameTemp.lastIndexOf(".")+1));
				response.setContentType(content.trim().isEmpty() ? "text/html" : content);  
				if(downloadFrom.equalsIgnoreCase("mail"))
					fileNameTemp = fileName.substring(0, fileName.lastIndexOf("."))+"#"+docId+"#"+downloadFrom+fileName.substring(fileName.lastIndexOf("."));
//				Code Change : Add Custom Prop for E-signed PDF
				String pdfFileWithCustomProp = AddCustomPDFProp(pdfFilePath,metadataDir,docXmlFileName,docId,fileName);
				pdfFilePath = pdfFileWithCustomProp.split("~")[0];
				fileNameTemp = pdfFileWithCustomProp.split("~")[1];
				System.out.println("Final File Name : "+fileNameTemp);
				System.out.println("Final PDF File Path  : "+pdfFilePath);
				response.setHeader("Content-Disposition","attachment; filename=\"" + fileNameTemp + "\"");   
//				if()
				FileInputStream fileInputStream = new FileInputStream(pdfFilePath);  

				int it;   
				while ((it=fileInputStream.read()) != -1) {  
					out.write(it);   
				}   
				if(downloadFrom.equalsIgnoreCase("File"))
					WorkflowManager.updateWorkingUserInXML(docId, downloadUsername, true);
				fileInputStream.close();   
				out.close();   
			}else {
				Drive drive = GoogleStorageMaintenace.getGoogleDriveObj(username);
				if(filepath == null || filepath.isEmpty()) {

					String folderName = fileName.substring(0, fileName.lastIndexOf("."))+"_"+docId;
					String docFolderID = GoogleStorageMaintenace.getFileIDByName(drive, folderName, "application/vnd.google-apps.folder");
					if(docFolderID == null || docFolderID.trim().isEmpty()) {
						response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
						response.getWriter().write("Couldn't find the file.");
						return;
					}
					Hashtable<String, String> folderNamesHT = GoogleStorageMaintenace.getFilesUnderFolderById(drive, docFolderID);
					Iterator<Entry<String, String>> it = folderNamesHT.entrySet().iterator();
					ArrayList<String> foldersAL = new ArrayList<>();
					ArrayList<String> foldersIDAL = new ArrayList<>();
					while (it.hasNext()) {
						Map.Entry<java.lang.String, java.lang.String> entry = (Map.Entry<java.lang.String, java.lang.String>) it
								.next();
						Hashtable<String, String> fileNamesHT = GoogleStorageMaintenace.getFilesUnderFolderById(drive, entry.getKey());
						Iterator<Entry<String, String>> itFile = fileNamesHT.entrySet().iterator();
						String fileID = "";
						while (itFile.hasNext()) {
							Map.Entry<java.lang.String, java.lang.String> entry1 = (Map.Entry<java.lang.String, java.lang.String>) itFile
									.next();
							fileID = entry1.getKey();
						}
						if(Globals.checkStringIsNumber(entry.getValue()) && fileID != null && !fileID.trim().isEmpty()) {
							foldersAL.add(entry.getValue());
							foldersIDAL.add(entry.getKey());
						}
					}
					int[] folderNames = new int[foldersAL.size()];
					int i=0;
					for(String val : foldersAL) {
						int j = 0;
						try {
							j = Integer.valueOf(val.substring(val.lastIndexOf("\\")+1));
						}catch (NumberFormatException e) {
							// TODO: handle exception
							j = 0;
						}
						folderNames[i] = j;
						i++;
					}
					
					System.out.println("-=-=-=-=-folderNames=-=-=-=-=-="+folderNames);
					List<Integer> b = Arrays.asList(ArrayUtils.toObject(folderNames));
					System.out.println("-=-=-=-=-b=-=-=-=-=-="+b);
//					while(b.size() > 0) {
						int maxName = Collections.max(b);
						System.out.println(maxName+"-=-=-=-=-b=-=-=-=-=-="+b);
						int index = b.indexOf(maxName);
						System.out.println(maxName+"-=-=-=-=-maxName=-=-=-=-=-="+index);
						String latestFolderID = foldersIDAL.get(index);
						filepath = latestFolderID;
						/*Hashtable<String, String> fileNamesHT = GoogleStorageMaintenace.getFilesUnderFolderById(drive, filepath);
						Iterator<Entry<String, String>> itFile = fileNamesHT.entrySet().iterator();
						String fileID = "";
						while (itFile.hasNext()) {
							Map.Entry<java.lang.String, java.lang.String> entry = (Map.Entry<java.lang.String, java.lang.String>) itFile
									.next();
							fileID = entry.getKey();
						}
						if(fileID != null && !fileID.trim().isEmpty()) {
							break;
						}
					}*/
					
				}
				Hashtable<String, String> fileNamesHT = GoogleStorageMaintenace.getFilesUnderFolderById(drive, filepath);
				Iterator<Entry<String, String>> itFile = fileNamesHT.entrySet().iterator();
				String fileID = "";
				while (itFile.hasNext()) {
					Map.Entry<java.lang.String, java.lang.String> entry = (Map.Entry<java.lang.String, java.lang.String>) itFile
							.next();
					fileID = entry.getKey();
				}
				if(fileID == null || fileID.trim().isEmpty()) {
					response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
					response.getWriter().write("Couldn't find the file.");
					return;
				}
				String mimeType = GoogleStorageMaintenace.getDownloadMimeTypeForFile(fileName);
				OutputStream out1 = GoogleStorageMaintenace.downloadFilesFromGoogleDrive(drive, fileID, mimeType);
//				response.setContentType("text/html");  
				PrintWriter out = response.getWriter();  
				ByteArrayOutputStream str = (ByteArrayOutputStream) out1;
				byte[] data = str.toByteArray();
				ByteArrayInputStream inpStr = new ByteArrayInputStream(data);
				//			inpStr.re
				//			String filename = "Finance.docx";   
				//			String filepath = "E:\\Document WF WS\\Document WF\\DocumentVersion\\Finance_51\\3\\";   
				//response.setContentType("APPLICATION/OCTET-STREAM");   
				String fileNameTemp = fileName;
				String content = Globals.getContentType4File(fileNameTemp.substring(fileNameTemp.lastIndexOf(".")+1));
				response.setContentType(content.trim().isEmpty() ? "text/html" : content);
				if(downloadFrom.equalsIgnoreCase("mail"))
					fileNameTemp = fileName.substring(0, fileName.lastIndexOf("."))+"#"+docId+"#"+downloadFrom+fileName.substring(fileName.lastIndexOf("."));
				response.setHeader("Content-Disposition","attachment; filename=\"" + fileNameTemp + "\"");   

				//			FileInputStream fileInputStream = new FileInputStream(filepath + fileName
				//					);  

				int it;   
				while ((it=inpStr.read()) != -1) {  
					out.write(it);   
				}   
				if(downloadFrom.equalsIgnoreCase("File"))
					WorkflowManager.updateWorkingUserInXML(docId, downloadUsername, true);
				inpStr.close();   
				out.close();   
			}
			

		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}  
public String AddCustomPDFProp(String FileNameTemp,String XMlPath,String DocDetailsXML,String DocID,String FileName ) throws FileNotFoundException, DocumentException, IOException {
		
//		Setting Custom Property for the E-signed PDF, reading the Document XML and checking Action_Type="Member E-Signed" and returning FileName and FilePath. 
		
		System.out.println("+++++++ Adding Custom Properties to Esigned PDf");
		System.out.println("______ Pdf file name "+FileNameTemp);
		System.out.println("______ Document Details Path : "+XMlPath+DocDetailsXML);
		System.out.println("______ Document Id "+ DocID);

		Document doc = Globals.openXMLFile(XMlPath,DocDetailsXML);
		Element docEle = (Element)Globals.getNodeByAttrVal(doc, "Document", "Document_ID", DocID);
		NodeList logNd = docEle.getElementsByTagName("Logs");
		System.out.println("---------------logndcount"+logNd.getLength());

		NodeList log=logNd.item(0).getChildNodes();
		
		Hashtable<String, String> customPropHT = new Hashtable<String, String>(); 
		int count = 0;
		for (int temp =0;temp < log.getLength();temp++) {
			Node nNode = log.item(temp);
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) nNode;
				System.out.println("___ Document Action Type : "+ eElement.getAttribute("Action_type"));
				String acttype=eElement.getAttribute("Action_type");				
				if(acttype.equals("Member E-Signed")) {
					count=count+1;
					String namekeky="ActionBy_"+count;
					String e_signInfo = eElement.getAttribute("Esign_info");
					String [] esignInfoList = e_signInfo.split("~");	
					System.out.println("___ Esign Info list : "+esignInfoList[0]);
					
					String ipaddress = "IpAddress_"+count;
					String location  = "Location_"+count;
					String state = "State_"+count;
					String country = "Country_"+count;
					String timezon = "TimeZone_"+count;
					String OS = "OperatingSystem_"+count;
					String Zipcode = "Zipcode_"+count;
					customPropHT.put(namekeky, eElement.getAttribute("Action_by"));
					customPropHT.put(ipaddress, esignInfoList[0]);
					customPropHT.put(location, esignInfoList[1]);
					customPropHT.put(state, esignInfoList[2]);
					customPropHT.put(country, esignInfoList[3]);
					customPropHT.put(Zipcode, esignInfoList[4]);
					customPropHT.put(timezon, esignInfoList[5]);
					customPropHT.put(OS, esignInfoList[6]);
					
				}
			}
			
		}
		// read existing pdf document
        PdfReader reader = new PdfReader(FileNameTemp);
        String filePath = FileNameTemp.
	    	     substring(0,FileNameTemp.lastIndexOf("."));

	    System.out.println("File path : " + filePath);
        filePath = filePath+"audit.pdf";
        String filenamenopth = filePath.
	    	     substring(filePath.lastIndexOf("\\"));
        String finalFilePathWithName = filePath+"~"+filenamenopth;
        PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(filePath));
        
              
        // add updated meta-data to pdf
        stamper.setMoreInfo(customPropHT);
        // update xmp meta-data
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        XmpWriter xmp = new XmpWriter(baos, customPropHT);
        xmp.close();
        stamper.setXmpMetadata(baos.toByteArray());
        stamper.close();
		return finalFilePathWithName;
	}


}  