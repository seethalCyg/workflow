package managers;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;

import java.util.Random;

import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.PNGTranscoder;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.ArrayUtils;
import org.json.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.google.gson.JsonObject;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.html.WebColors;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfGState;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfStructTreeController.returnType;
import com.sun.org.apache.bcel.internal.generic.NEW;

import sun.misc.BASE64Decoder;

import beans.RandomString;
import utils.Globals;
import utils.PropUtil;

public class FormsManager {
	public static void main(String[] mains) {
		try {
			// PropUtil prop = new PropUtil();
			// String formsXMLFileName = prop.getProperty("IISFORMCONFIGFILENAME");
			// String iisConfigFilePath = "E:\\Business Planner Original Vishnu\\Business
			// Planner\\Config\\Alpha Hotels_2oMpw\\User_Templates_Config\\";
			// Document doc = Globals.openXMLFile(iisConfigFilePath, formsXMLFileName);
			// Node formsListNd = doc.getElementsByTagName("FormsList").item(0);
			// String folderId = "142554";
			// ArrayList<Node> ndAL = new ArrayList<>();
			// getNodeByID(formsListNd, folderId, ndAL);
			// System.out.println(formsListNd.getNodeName()+"-=-=-=-formParentNd=-=-=-="+ndAL+"-=-=-="+folderId);

			/*
			 * String type = (String)jObj.get("Type"); String subcriptionType =
			 * (String)jObj.get("SubcriptionType"); String subcriptionStartDate =
			 * (String)jObj.get("SubcriptionStartDate"); String subcriptionEndDate =
			 * (String)jObj.get("SubcriptionEndDate");
			 * if(type.equalsIgnoreCase("Trail2Subscription")) { String mailId =
			 * (String)jObj.get("MailId");
			 */
			SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a");
			Calendar cal = Calendar.getInstance();
			Hashtable<String, String> tempHt = new Hashtable<>();

			// tempHt.put("Type", "Trail2Subscription");
			tempHt.put("Type", "DirectSubscription");
			tempHt.put("SubcriptionType", "3 users");
			tempHt.put("SubcriptionStartDate", sdf.format(cal.getTime()));
			cal.set(Calendar.DATE, 30);
			tempHt.put("SubcriptionEndDate", sdf.format(cal.getTime()));
			tempHt.put("MailId", "ppvishnu60@gmail.com");

			JSONObject j = new JSONObject(tempHt);
			String message = j.toJSONString();
			System.out.println("-=-=-=-=" + message);
			updateAdminFile(message);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public static Hashtable<String, String> getIpDetailsAudit(String templateName, String customerKey, String jsonStr)
			throws Exception {
		Hashtable<String, String> detailsHT = new Hashtable<>();
		System.out.println("getIpDetailsAuditOOOOOOOOOOOOOOOO:" + templateName + "::::" + jsonStr);

		JSONObject jObj1 = (JSONObject) new JSONParser().parse(jsonStr);
		String ip = jObj1.get("ip") == null ? "" : jObj1.get("ip").toString();

		System.out.println("from jsonstring-=-=-=-=-=-=-=-=-=-=-=-=-=" + ip);
		System.out.println("inside getIpDetailsAudit");

		String url = "http://api.ipstack.com/" + ip + "?access_key=d00ec5f2835f647f22d14e2da48c1edf&output=json";

		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		// optional default is GET
		con.setRequestMethod("GET");
		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'GET' request to URL : " + url);
		System.out.println("Response Code : " + responseCode);
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		// print in String
		System.out.println(response.toString());

		JSONObject jObj = (JSONObject) new JSONParser().parse(response.toString());

		String iip = jObj.get("ip") == null ? "" : jObj.get("ip").toString();
		String country = jObj.get("country_name") == null ? "" : jObj.get("country_name").toString();
		String region = jObj.get("region_name") == null ? "" : jObj.get("region_name").toString();
		String city = jObj.get("city") == null ? "" : jObj.get("city").toString();
		String zip = jObj.get("zip") == null ? "" : jObj.get("zip").toString();

		System.out.println("values from ipstack-==-=-=-=-=- ip:" + iip + "country:" + country + "city:" + city
				+ "region:" + region + "zip:" + zip);

		detailsHT.put("Status", "Success");
		detailsHT.put("StatusDetails", "Success");
		detailsHT.put("ip", iip);
		detailsHT.put("country_name", country);
		detailsHT.put("region_name", region);
		detailsHT.put("city", city);
		detailsHT.put("zip", zip);

		System.out.println("****END****");

		return detailsHT;
	}

	public static Hashtable<String, String> saveFormMetadata(String formId, String folderId, String metadata)
			throws Exception {
		Hashtable<String, String> detailsHT = new Hashtable<>();
		folderId = folderId == null ? "" : folderId;
		PropUtil prop = new PropUtil();
		String iisConfigFilePath = prop.getProperty("IISCONFIGFILEPATH");
		String formsXMLFileName = prop.getProperty("IISFORMCONFIGFILENAME");
		String formsMetadataFolder = prop.getProperty("FORMSMETADATA_FOLDER");
		String dateFormat = prop.getProperty("DATE_FORMAT");
		String iisAdminFilePath = prop.getProperty("IISUSERADMINFILEPATH");
		String iisAdminFileName = prop.getProperty("IISUSERADMINFILENAME");

		JSONObject jObj = (JSONObject) new JSONParser().parse(metadata);
		String formName = jObj.get("FormName").toString();
		String formDecription = jObj.get("FormDescription").toString();
		String customerKey = jObj.get("CustomerKey").toString();
		String createdBy = jObj.get("CreatedBy").toString();
		Document iisAdminDoc = Globals.openXMLFile(iisAdminFilePath, iisAdminFileName);
		Node adminNd = Globals.getNodeByAttrVal(iisAdminDoc, "Admin", "CustomerKey", customerKey);
		if (adminNd == null) {
			throw new Exception("Could not find the customer.");
		}
		Element adminEle = (Element) adminNd;
		String compName = adminEle.getAttribute("CompanyName");
		iisConfigFilePath = iisConfigFilePath + compName + "_" + customerKey + "\\" + "User_Templates_Config\\";
		Document doc = Globals.openXMLFile(iisConfigFilePath, formsXMLFileName);
		Node formsListNd = doc.getElementsByTagName("FormsList").item(0);
		System.out.println(formName + "-=-=-=-=-=-=-formName=-=-=-=-=-=" + metadata);
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		String currDate = sdf.format(date);
		Node formNd = null;
		ArrayList<Node> ndAL = new ArrayList<>();
		getNodeByID(formsListNd, formId, ndAL);
		if (ndAL.size() > 0)
			formNd = ndAL.get(0);
		Element formEle = null;
		if (formNd != null) {
			formEle = (Element) formNd;
			formEle.setAttribute("ModifiedDate", currDate);
			formEle.setAttribute("ModifiedBy", createdBy);
			formEle.setAttribute("Name", formName);
			formEle.setAttribute("FormDescription", formDecription);
		} else {
			Node opNd = null;
			Node formParentNd = folderId.equalsIgnoreCase("Root") ? formsListNd : null;
			ndAL = new ArrayList<>();
			getNodeByID(formsListNd, folderId, ndAL);
			if (ndAL.size() > 0)
				formParentNd = ndAL.get(0);
			System.out.println(
					formsListNd.getNodeName() + "-=-=-=-formParentNd=-=-=-=" + formParentNd + "-=-=-=" + folderId);
			if (formParentNd == null)
				formParentNd = formsListNd;
			formEle = doc.createElement("Form");
			formEle.setAttribute("CreatedDate", currDate);
			formEle.setAttribute("ModifiedDate", currDate);
			formEle.setAttribute("CreatedBy", createdBy);
			formEle.setAttribute("ModifiedBy", createdBy);
			formEle.setAttribute("ID", formId);
			formEle.setAttribute("Name", formName);
			formEle.setAttribute("Type", "Form");
			formEle.setAttribute("FormDescription", formDecription);
			formEle.setAttribute("CustomerKey", customerKey);
			formParentNd.appendChild(formEle);
		}
		formsMetadataFolder = formsMetadataFolder + compName + "_" + customerKey + "\\";
		String fileNameWithPath = formsMetadataFolder;
		Random rand = new Random();
		File jsonFile = null;
		System.out.println("last123---" + metadata);
		while (true) {
			int uniqueNo = rand.nextInt((99999 - 10000) + 1) + 10000;
			fileNameWithPath = fileNameWithPath + "FormMetadata_" + uniqueNo + ".json";

			jsonFile = new File(fileNameWithPath);
			if (!jsonFile.exists())
				break;
		}
		System.out.println("last---" + metadata);
		BufferedWriter buffWriter = new BufferedWriter(new FileWriter(jsonFile));
		buffWriter.write(metadata);
		buffWriter.flush();
		buffWriter.close();
		formEle.setAttribute("MetadataFilePath", fileNameWithPath);
		System.out.println(iisConfigFilePath + "-=-=-=-=-formsXMLFileName=-=-=-=-=" + formsXMLFileName);
		Globals.writeXMLFile(doc, iisConfigFilePath, formsXMLFileName);
		detailsHT.put("Status", "Success");
		detailsHT.put("StatusDetails", "Form metadata is save successfully.");
		return detailsHT;
	}

	public static Hashtable<String, String> saveEsignFormMetadata(String templateName, String metadata,
			String customerKey, String createdBy, String saveasFlage, String newTemplateName) throws Exception {

		System.out.println("sudeesh====" + metadata);

		Hashtable<String, String> detailsHT = new Hashtable<>();
		PropUtil prop = new PropUtil();
		String iisConfigFilePath = prop.getProperty("IISCONFIGFILEPATH");
		String templateFileName = prop.getProperty("IISTEMPLATEFILENAME");
		String formsMetadataFolder = prop.getProperty("FORMSMETADATA_FOLDER");
		String dateFormat = prop.getProperty("DATE_FORMAT");
		String iisAdminFilePath = prop.getProperty("IISUSERADMINFILEPATH");
		String iisAdminFileName = prop.getProperty("IISUSERADMINFILENAME");
		System.out.println(saveasFlage + "<=====metadata==templateName======>" + templateName);
		String templateSignName = prop.getProperty("IISAVEASXML");
		// metadata = metadata.replace("\\", "\\\\").replace("'", "\\'").replace("`",
		// "\\`");

		System.out.println("sudeesh2====" + metadata);
		// JSONArray jObj = (JSONArray) new JSONParser().parse(metadata);
		// String formName = jObj.get("FormName").toString();.ÃƒÅ’Ã‚Â£.........
		// String formDecription = jObj.get("FormDescription").toString();
		// String customerKey = jObj.get("CustomerKey").toString();
		// String createdBy = jObj.get("CreatedBy").toString();
		String HIERARCHY_XML_DIR = prop.getProperty("HIERARCHY_XML_DIR");
		String hierarchyXmlFileName = prop.getProperty("HIERARCHY_XML_FILE");
		Hashtable<String, String> hierarchyAttr = new Hashtable<String, String>();
		Document dochirachy = Globals.openXMLFile(HIERARCHY_XML_DIR, hierarchyXmlFileName);
		Document iisAdminDoc = Globals.openXMLFile(iisAdminFilePath, iisAdminFileName);
		Node adminNd = Globals.getNodeByAttrVal(iisAdminDoc, "Admin", "CustomerKey", customerKey);
		System.out.println(HIERARCHY_XML_DIR + "<<HIERARCHY_XML_DIR");
		System.out.println(hierarchyXmlFileName + "<<hierarchyXmlFileName");
		System.out.println(dochirachy + "...........");
		if (adminNd == null) {
			throw new Exception("Could not find the customer.");
		}
		Element adminEle = (Element) adminNd;
		String compName = adminEle.getAttribute("CompanyName");
		iisConfigFilePath = iisConfigFilePath + compName + "_" + customerKey + "\\" + "User_Templates_Config\\";
		Document doc = Globals.openXMLFile(iisConfigFilePath, templateFileName);
		System.out.println(iisConfigFilePath + "-=-=-=-=-=-=-iisConfigFilePath=-=-=-=-=-=" + templateFileName);
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		String currDate = sdf.format(date);
		formsMetadataFolder = formsMetadataFolder + compName + "_" + customerKey + "\\";
		String fileNameWithPath = formsMetadataFolder;
		Random rand = new Random();
		File jsonFile = null;
		while (true) {
			int uniqueNo = rand.nextInt((99999 - 10000) + 1) + 10000;
			fileNameWithPath = fileNameWithPath + "FormMetadata_" + uniqueNo + ".json";
			jsonFile = new File(fileNameWithPath);
			if (!jsonFile.exists())
				break;
		}
		if (saveasFlage.equals("false")) {
			System.out.println("templateName>>>>>>" + templateName);
			Node templateNd = Globals.getNodeByAttrVal(doc, "Template", "Name", templateName);
			if (templateNd == null) {
				new Exception("Could not find the template.");
			}
			Element templateEle = (Element) templateNd;
			// Element esignEle =
			// templateEle.getElementsByTagName("ESign_Details").getLength() <= 0 ? null :
			// (Element)templateEle.getElementsByTagName("ESign_Details").item(0);
			String wrkFlow_docID = templateEle.getAttribute("Workflow_ID") == null
					|| templateEle.getAttribute("Workflow_ID").equals("") ? ""
							: templateEle.getAttribute("Workflow_ID");
			String wrkFlow_docName = templateEle.getAttribute("Workflow_Name") == null
					|| templateEle.getAttribute("Workflow_Name").equals("") ? ""
							: templateEle.getAttribute("Workflow_Name");
			String docID = templateEle.getAttribute("Workflow_Document_ID") == null
					|| templateEle.getAttribute("Workflow_Document_ID").equals("") ? ""
							: templateEle.getAttribute("Workflow_Document_ID");
			String docName = templateEle.getAttribute("Workflow_Document_Name") == null
					|| templateEle.getAttribute("Workflow_Document_Name").equals("") ? ""
							: templateEle.getAttribute("Workflow_Document_Name");
			String esignFlag = templateEle.getAttribute("EsignApplied") == null
					|| templateEle.getAttribute("EsignApplied").equals("") ? ""
							: templateEle.getAttribute("EsignApplied");
			String outpuFilePath = templateEle.getAttribute("Output_File") == null
					|| templateEle.getAttribute("Output_File").equals("") ? ""
							: templateEle.getAttribute("Output_File");
			String outpuFilePath4EsignStg = templateEle.getAttribute("Output_File4Esign") == null ? outpuFilePath
					: templateEle.getAttribute("Output_File4Esign");
			Document xmlDoc = Globals.openXMLFile(iisConfigFilePath, templateSignName);
			String temPNAM = "";
			Node atthmentsNd = Globals.getNodeByAttrVal(xmlDoc, "Template", "Descriptor", newTemplateName);
			Element existTEmpXMl = (Element) atthmentsNd;
			Element hierEle = null;
			Element esigEle = null;
			if (atthmentsNd == null) {
				System.out.println("---------------------- IF NULL "+newTemplateName);
				hierEle = xmlDoc.createElement("Template");
				esigEle = xmlDoc.createElement("ESign_Details");
			} else {
				System.out.println("---------------------- IF NOT NULL "+newTemplateName);
				temPNAM = existTEmpXMl.getAttribute("Descriptor") == null
						|| existTEmpXMl.getAttribute("Descriptor").equals("") ? ""
								: existTEmpXMl.getAttribute("Descriptor");
				esigEle = existTEmpXMl.getElementsByTagName("ESign_Details").getLength() <= 0 ? null
						: (Element) existTEmpXMl.getElementsByTagName("ESign_Details").item(0);
				if (!temPNAM.equals(newTemplateName)) {
					System.out.println(temPNAM+"---------------------- IF NOT NULL-------------------AND EQUALS "+newTemplateName);
					hierEle = xmlDoc.createElement("Template");
					esigEle = xmlDoc.createElement("ESign_Details");
				} else {
					System.out.println(temPNAM+"---------------------- IF NOT NULL-------------------AND NOT EQUALS "+newTemplateName);
					hierEle = (Element) atthmentsNd;
				}

			}
			System.out.println(dochirachy + "<<<<<<<<<wrkFlow_docID>>>>>>>>>" + wrkFlow_docID);
			System.out.println("tempNam===>" + temPNAM);
			String stageflag = "false";
			if (!wrkFlow_docID.equalsIgnoreCase("")) {
				Node workFlowNode = Globals.getNodeByAttrVal(dochirachy, "Workflow", "Hierarchy_ID", wrkFlow_docID);
				Element wfEle = (Element) workFlowNode;
				NodeList stageNdList = wfEle.getElementsByTagName("Stage");
				for (int l = 0; l < stageNdList.getLength(); l++) {
					if (stageNdList.item(l).getNodeType() == Node.ELEMENT_NODE) {
						Element stageEle = (Element) stageNdList.item(l);
						stageflag = stageEle.getAttribute("Enable_Esign") == null
								|| stageEle.getAttribute("Enable_Esign").equals("") ? "false"
										: stageEle.getAttribute("Enable_Esign");
						if (stageflag.toLowerCase().equals("true")) {
							break;
						}
					}
				}
			}
			String tenpLateName = templateName.equals(newTemplateName) ? templateName : newTemplateName;
			if (stageflag.toLowerCase().equals("false")) {
				hierEle.setAttribute("TemplateName", "Esign_" + (rand.nextInt((99999 - 10000) + 1) + 10000));
				hierEle.setAttribute("Descriptor", tenpLateName);
				hierEle.setAttribute("Enable_Esign", "false");
				hierEle.setAttribute("OutputFilePath", outpuFilePath);
				hierEle.setAttribute("WorkflowId", wrkFlow_docID);
				hierEle.setAttribute("WorkflowName", wrkFlow_docName);
			} else {
				hierEle.setAttribute("Enable_Esign", "true");
				hierEle.setAttribute("TemplateName", "Esign_" + (rand.nextInt((99999 - 10000) + 1) + 10000));
				hierEle.setAttribute("Descriptor", tenpLateName);
				hierEle.setAttribute("Document_ID", docID);
				hierEle.setAttribute("Document_Name", docName);
				hierEle.setAttribute("WorkflowId", wrkFlow_docID);
				hierEle.setAttribute("WorkflowName", wrkFlow_docName);
				hierEle.setAttribute("OutputFilePath", outpuFilePath4EsignStg);
			}
			System.out.println(wrkFlow_docID + "<===========dociiiiiiiiiidddd===>" + wrkFlow_docName);
			// Element esigEle = xmlDoc.createElement("ESign_Details");
			esigEle.setAttribute("CreatedDate", currDate);
			esigEle.setAttribute("ModifiedDate", currDate);
			esigEle.setAttribute("CreatedBy", createdBy);
			esigEle.setAttribute("ModifiedBy", createdBy);
			esigEle.setAttribute("uniqnum",tenpLateName.substring(templateName.lastIndexOf("_")+1));
			System.out.println("sudeesh3===" + metadata);

			esigEle.setAttribute("MetadataFilePath", fileNameWithPath);
			File metaFile = new File(fileNameWithPath);
			// if(metaFile.isFile()) {
			org.json.simple.JSONArray jObj = (org.json.simple.JSONArray) new JSONParser().parse(metadata);
			for (int i = 0; i < jObj.size(); i++) {
				JSONObject obj = (JSONObject) jObj.get(i);
				System.out.println("---Printing Metadata:"+obj.toJSONString());
				org.json.simple.JSONArray arr = (org.json.simple.JSONArray) obj.get("values");
				String   page_number_Medata =  obj.get("pagenumber").toString();
				
				System.out.println("-----Doc ID:"+tenpLateName.substring(templateName.lastIndexOf("_")+1));
				System.out.println("----page_number_Medata:"+page_number_Medata);
				obj.put("DocumentPageNumber", page_number_Medata);
				obj.put("DocumentId", tenpLateName.substring(templateName.lastIndexOf("_")+1));
				for (int j = 0; j < arr.size(); j++) {
					JSONObject obj1 = (JSONObject) arr.get(j);
					obj1.put("stageno", "All");
					obj1.put("userid", "All");
					obj1.put("stagename", "All");
				}
				// }
				String finalData = jObj.toString();
				System.out.println("final data===" + finalData);

				BufferedWriter buffW = new BufferedWriter(new FileWriter(metaFile));
				buffW.write(finalData);
				buffW.flush();
				buffW.close();
			}
			if (temPNAM.equals("") || !temPNAM.equals(templateName)) {
				hierEle.appendChild(esigEle);
				xmlDoc.getFirstChild().appendChild(hierEle);
			}
			Globals.writeXMLFile(xmlDoc, iisConfigFilePath, templateSignName);
		} else {
			Node templateNd = Globals.getNodeByAttrVal(doc, "Template", "Name", templateName);
			if (templateNd == null) {
				new Exception("Could not find the template.");
			}
			Element templateEle = (Element) templateNd;
			Element esignEle = templateEle.getElementsByTagName("ESign_Details").getLength() <= 0 ? null
					: (Element) templateEle.getElementsByTagName("ESign_Details").item(0);
			if (esignEle != null) {
				esignEle.setAttribute("ModifiedDate", currDate);
				esignEle.setAttribute("ModifiedBy", createdBy);
				// esignEle.setAttribute("Name", formName);
				// esignEle.setAttribute("FormDescription", formDecription);
			} else {
				// Node opNd = null;
				// Node formParentNd = folderId.equalsIgnoreCase("Root") ? formsListNd : null;
				// ndAL = new ArrayList<>();
				// getNodeByID(formsListNd, folderId, ndAL);
				// if(ndAL.size() > 0)
				// formParentNd = ndAL.get(0);
				// System.out.println(formsListNd.getNodeName()+"-=-=-=-formParentNd=-=-=-="+formParentNd+"-=-=-="+folderId);
				// if(formParentNd== null)
				// formParentNd = formsListNd;
				esignEle = doc.createElement("ESign_Details");
				esignEle.setAttribute("CreatedDate", currDate);
				esignEle.setAttribute("ModifiedDate", currDate);
				esignEle.setAttribute("CreatedBy", createdBy);
				esignEle.setAttribute("ModifiedBy", createdBy);
				// formEle.setAttribute("ID", formId);
				// formEle.setAttribute("Name", formName);
				templateEle.setAttribute("EsignApplied", "true");
				// formEle.setAttribute("FormDescription", formDecription);
				// esignEle.setAttribute("CustomerKey", customerKey);
				templateEle.appendChild(esignEle);
			}
			System.out.println("sudeesh555++++++" + jsonFile);
			metadata = updateEsignMetadataWithDocumentID(metadata, templateEle);
			System.out.println("metadata++++++" + metadata);
			esignEle.setAttribute("MetadataFilePath", fileNameWithPath);
			BufferedWriter buffWriter = new BufferedWriter(new FileWriter(jsonFile));
			buffWriter.write(metadata);
			buffWriter.flush();
			buffWriter.close();
			Globals.writeXMLFile(doc, iisConfigFilePath, templateFileName);
		}

		detailsHT.put("Status", "Success");
		detailsHT.put("StatusDetails", "Form metadata is save successfully.");
		return detailsHT;
	}

	public static Hashtable<String, String> saveEsignFormdata(String templateName, String metadata, String customerKey,
			String username, String notes, String actionName) throws Exception {
		Hashtable<String, String> detailsHT = new Hashtable<>();
		PropUtil prop = new PropUtil();
		String iisConfigFilePath = prop.getProperty("IISCONFIGFILEPATH");
		String templateFileName = prop.getProperty("IISTEMPLATEFILENAME");
		String formsDataFolder = prop.getProperty("FORMSDATA_FOLDER");
		String dateFormat = prop.getProperty("DATE_FORMAT");
		String iisAdminFilePath = prop.getProperty("IISUSERADMINFILEPATH");
		String iisAdminFileName = prop.getProperty("IISUSERADMINFILENAME");

		String hierDir = prop.getProperty("HIERARCHY_XML_DIR");
		String docXmlFileName = prop.getProperty("DOCUMENT_XML_FILE");
		Document docXmlDoc = Globals.openXMLFile(hierDir, docXmlFileName);
		// JSONArray jObj = (JSONArray) new JSONParser().parse(metadata);
		// String formName = jObj.get("FormName").toString();
		// String formDecription = jObj.get("FormDescription").toString();
		// String customerKey = jObj.get("CustomerKey").toString();
		// String createdBy = jObj.get("CreatedBy").toString();
		System.out.println(templateName + "-=-=-=-=-=-=-formName=-=-=-=-=-=" + actionName);
		Document iisAdminDoc = Globals.openXMLFile(iisAdminFilePath, iisAdminFileName);
		Node adminNd = Globals.getNodeByAttrVal(iisAdminDoc, "Admin", "CustomerKey", customerKey);
		if (adminNd == null) {
			throw new Exception("Could not find the customer.");
		}
		Element adminEle = (Element) adminNd;
		String compName = adminEle.getAttribute("CompanyName");
		iisConfigFilePath = iisConfigFilePath + compName + "_" + customerKey + "\\" + "User_Templates_Config\\";
		Document doc = Globals.openXMLFile(iisConfigFilePath, templateFileName);
		System.out.println(templateName + "-=-=-=-=-=-=-formName=-=-=-=-=-=" + metadata);
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		String currDate = sdf.format(date);
		Node templateNd = Globals.getNodeByAttrVal(doc, "Template", "Name", templateName);
		if (templateNd == null) {
			new Exception("Could not find the template.");
		}
		Element templateEle = (Element) templateNd;
		Element esignEle = templateEle.getElementsByTagName("ESign_Details").getLength() <= 0 ? null
				: (Element) templateEle.getElementsByTagName("ESign_Details").item(0);

		formsDataFolder = formsDataFolder + compName + "_" + customerKey + "\\" + templateName + "\\";
		File temp = new File(formsDataFolder);
		temp.mkdirs();
		String fileNameWithPath = formsDataFolder;
		Random rand = new Random();
		File jsonFile = null;
		while (true) {
			int uniqueNo = rand.nextInt((99999 - 10000) + 1) + 10000;
			fileNameWithPath = fileNameWithPath + "FormData_" + uniqueNo + ".json";

			jsonFile = new File(fileNameWithPath);
			if (!jsonFile.exists())
				break;
		}
		BufferedWriter buffWriter = new BufferedWriter(new FileWriter(jsonFile));
		buffWriter.write(metadata);
		buffWriter.flush();
		buffWriter.close();
		esignEle.setAttribute("FormDataFilePath", fileNameWithPath);
		Globals.writeXMLFile(doc, iisConfigFilePath, templateFileName);
		String documentId = templateEle.getAttribute("Workflow_Document_ID");
		String docName = templateEle.getAttribute("Workflow_Document_Name");
		String statusStr = actionName;
		if (statusStr.equalsIgnoreCase("Approve") || statusStr.equalsIgnoreCase("Approved")) {
			statusStr = "Approve";
		} else if (statusStr.equalsIgnoreCase("Completed") || statusStr.equalsIgnoreCase("Complete")) {
			statusStr = "Completed";
		} else if (statusStr.equalsIgnoreCase("Rejected") || statusStr.equalsIgnoreCase("Reject")) {
			statusStr = "Reject";
		} else if (statusStr.equalsIgnoreCase("Initiate") || statusStr.equalsIgnoreCase("Initiated")) {
			statusStr = "Initiate";
		} else if (statusStr.equalsIgnoreCase("Remind") || statusStr.equalsIgnoreCase("Reminded")) {
			statusStr = "Remind";
		} else if (statusStr.equalsIgnoreCase("Escalate") || statusStr.equalsIgnoreCase("Escalated")) {
			statusStr = "Escalate";
		} else if (statusStr.equalsIgnoreCase("Cancel")) {
			statusStr = "Cancel";
		} else if (statusStr.equalsIgnoreCase("Pause")) {
			statusStr = "Pause";
		} else if (statusStr.equalsIgnoreCase("Acknowledged")) {
			statusStr = "Acknowledged";
		}
		System.out.println("metadata>>>>" + metadata);
		Node docNd = Globals.getNodeByAttrVal(docXmlDoc, "Document", "Document_ID", documentId);
		Element docEle = (Element) docNd;
		Element wfEle = (Element) docEle.getElementsByTagName("Workflow").item(0);
		String wfType = wfEle.getAttribute("Workflow_Type");
		String currStgNo = wfEle.getAttribute("Current_Stage_No");
		String totalStage = wfEle.getAttribute("Total_No_Stages");
		String nxtStgMsg = "";
		LoginProcessManager lpm = new LoginProcessManager();
		Hashtable stageDetailsHT = lpm.retriveStageDetailsFromXML(wfEle, currStgNo, "");
		Hashtable nxtPropertiesHT = (Hashtable) stageDetailsHT.get("PropertiesHT");
		String Properties = (String) nxtPropertiesHT.get("Properties");
		Hashtable emplyeeDetailsHT = (Hashtable) stageDetailsHT.get("EmployeedetHT");
		Hashtable mssgeDetailsHT = (Hashtable) stageDetailsHT.get("MessagedetHT");
		JSONArray jsonArray = new JSONArray(metadata);
		int minAprrovers1 = 1;
		for (int i = 0; i < jsonArray.length(); i++) {
			org.json.JSONObject explrObject = jsonArray.getJSONObject(i);
			String mandatory = (String) explrObject.get("mandatory");
			String fieldValue = (String) explrObject.get("value");
			String userId = (String) explrObject.get("userid");
			String lastUser = (String) explrObject.get("lastuser");
			System.out.println(fieldValue + "<<<++no of times====" + mandatory);
			System.out.println((fieldValue == null) + "<<<////////////====" + (fieldValue.equals("")) + "  "
					+ (fieldValue.equals("null")));
			if (mandatory.equalsIgnoreCase("true")
					&& (fieldValue == null || fieldValue.equals("") || fieldValue.equals("null"))
					&& (lastUser.equals("~") && userId.equalsIgnoreCase("all"))) {
				String finalMsg2 = (String) mssgeDetailsHT.get("Final");
				boolean completeMantFlag = false;
				if (Properties.equalsIgnoreCase("Parallel")) {
					System.out.println(emplyeeDetailsHT.size() + "<<<ParallelParallelParallel" + emplyeeDetailsHT);
					String minApprovers = (String) nxtPropertiesHT.get("Min_Approvers");
					int fromxmlminapprovers = Integer.parseInt(minApprovers);
					for (int approv = 0; approv < emplyeeDetailsHT.size(); approv++) {
						Hashtable ApproversHT = new Hashtable();
						ApproversHT = (Hashtable) emplyeeDetailsHT.get(approv);
						String userStatus = (String) ApproversHT.get("User_Status");
						System.out.println(fromxmlminapprovers + "<<<<<User_Status>>>>>>>>" + userStatus);
						if (userStatus.equals(finalMsg2)) {
							minAprrovers1 = minAprrovers1 + 1;
							System.out.println(minAprrovers1 + "<<<<<completeMantFlag1>>>" + completeMantFlag);
						}
					}
					if (minAprrovers1 >= fromxmlminapprovers) {
						completeMantFlag = true;
						System.out.println("completeMantFlag2>>>" + completeMantFlag);
						detailsHT.put("Status", "Alert");
						detailsHT.put("StatusDetails", "This document needs you to sign or fill in details.");
						System.out.println("detailsHT):>>222>" + detailsHT);
						return detailsHT;
					}
				}
				System.out.println("completeMantFlag3>>>" + completeMantFlag);
			}
		}
		if (statusStr.equalsIgnoreCase("SaveValues")) {
			return detailsHT;
		}
		WorkflowManager.performingAction(username, notes, statusStr, docName, documentId, "", "", "", "");
		Hashtable testHT = WorkflowManager.getNextDocumentUsers(docXmlDoc, wfEle, currStgNo, username);
		System.out.println("testHT):>>>" + testHT);
		if (testHT != null) {
			String users = "";
			ArrayList<String> testAL = testHT.get("Users") == null ? new ArrayList<String>()
					: (ArrayList<String>) testHT.get("Users");
			String stage = testHT.get("Stage") == null ? "" : (String) testHT.get("Stage");
			nxtStgMsg = stage;
			for (int i = 0; i < testAL.size(); i++) {
				nxtStgMsg = nxtStgMsg + (i == 0 ? "(" : "") + testAL.get(i) + (i < testAL.size() - 1 ? ", " : "")
						+ (i == testAL.size() - 1 ? ")" : "");
				users = testAL.get(i) + (i < testAL.size() - 1 ? ", " : "");
			}
			if (wfType.equalsIgnoreCase("Simple")) {
				nxtStgMsg = users;
			}
		}
		if (Globals.checkStringIsNumber(currStgNo) && Integer.valueOf(currStgNo) == Integer.valueOf(totalStage)) {
			docXmlDoc = Globals.openXMLFile(hierDir, docXmlFileName);
			docNd = Globals.getNodeByAttrVal(docXmlDoc, "Document", "Document_ID", documentId);

			docEle = (Element) docNd;
			wfEle = (Element) docEle.getElementsByTagName("Workflow").item(0);
			LoginProcessManager lpm2 = new LoginProcessManager();
			Hashtable stageDetailsHT2 = lpm2.retriveStageDetailsFromXML(wfEle, currStgNo, "");
			Hashtable nxtPropertiesHT2 = (Hashtable) stageDetailsHT2.get("PropertiesHT");
			String Properties2 = (String) nxtPropertiesHT2.get("Properties");
			Hashtable emplyeeDetailsHT2 = (Hashtable) stageDetailsHT2.get("EmployeedetHT");
			Hashtable mssgeDetailsHT2 = (Hashtable) stageDetailsHT2.get("MessagedetHT");
			String finalMsg = (String) mssgeDetailsHT2.get("Final");
			int appr = 0;
			int minAprrovers = 0;
			System.out.println("-=-=-=-=-Properties=-=-=-=-status=-=" + Properties2);
			boolean completeFlag = false;
			if (Properties2.equalsIgnoreCase("Parallel")) {
				String minApprovers = (String) nxtPropertiesHT2.get("Min_Approvers");
				int fromxmlminapprovers = Integer.parseInt(minApprovers);
				for (int approv = 0; approv < emplyeeDetailsHT2.size(); approv++) {
					Hashtable ApproversHT = new Hashtable();
					ApproversHT = (Hashtable) emplyeeDetailsHT2.get(approv);
					String userStatus = (String) ApproversHT.get("User_Status");
					if (userStatus.equals(finalMsg)) {
						minAprrovers = minAprrovers + 1;
						appr++;
					}
				}
				if (minAprrovers >= fromxmlminapprovers) {
					completeFlag = true;
				}
			} else {
				Hashtable ApproversHT = new Hashtable();
				ApproversHT = (Hashtable) emplyeeDetailsHT2.get(emplyeeDetailsHT2.size() - 1);
				String userStatus = (String) ApproversHT.get("User_Status");
				System.out.println(finalMsg + " : -=-=-=-=-userStatus=-=-=-=-status=-=: " + userStatus);
				if (userStatus.equalsIgnoreCase(finalMsg)) {
					completeFlag = true;
				}
			}
			System.out.println("-=-=-=-=-completeFlag=-=-=-=-status=-=" + completeFlag);
			detailsHT.put("CompleteFlag", String.valueOf(completeFlag));
		} else {
			detailsHT.put("CompleteFlag", "false");
		}
		detailsHT.put("Status", "Success");
		detailsHT.put("StatusDetails", "Form metadata is save successfully.");
		detailsHT.put("NextStageUserDetails", nxtStgMsg);
		System.out.println("detailsHT):>>222>" + detailsHT);
		return detailsHT;
	}

	public static Hashtable<String, String> rejectDocumentFromEsignForm(String templateName, String customerKey,
			String username, String notes) throws Exception {
		PropUtil prop = new PropUtil();
		String iisConfigFilePath = prop.getProperty("IISCONFIGFILEPATH");
		String templateFileName = prop.getProperty("IISTEMPLATEFILENAME");
		String formsDataFolder = prop.getProperty("FORMSDATA_FOLDER");
		String dateFormat = prop.getProperty("DATE_FORMAT");
		String iisAdminFilePath = prop.getProperty("IISUSERADMINFILEPATH");
		String iisAdminFileName = prop.getProperty("IISUSERADMINFILENAME");
		String hierDir = prop.getProperty("HIERARCHY_XML_DIR");
		String docXmlFileName = prop.getProperty("DOCUMENT_XML_FILE");
		Document docXmlDoc = Globals.openXMLFile(hierDir, docXmlFileName);
		// JSONArray jObj = (JSONArray) new JSONParser().parse(metadata);
		// String formName = jObj.get("FormName").toString();
		// String formDecription = jObj.get("FormDescription").toString();
		// String customerKey = jObj.get("CustomerKey").toString();
		// String createdBy = jObj.get("CreatedBy").toString();
		Document iisAdminDoc = Globals.openXMLFile(iisAdminFilePath, iisAdminFileName);
		Node adminNd = Globals.getNodeByAttrVal(iisAdminDoc, "Admin", "CustomerKey", customerKey);
		if (adminNd == null) {
			throw new Exception("Could not find the customer.");
		}
		Element adminEle = (Element) adminNd;
		String compName = adminEle.getAttribute("CompanyName");
		iisConfigFilePath = iisConfigFilePath + compName + "_" + customerKey + "\\" + "User_Templates_Config\\";
		Document doc = Globals.openXMLFile(iisConfigFilePath, templateFileName);
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		String currDate = sdf.format(date);
		Node templateNd = Globals.getNodeByAttrVal(doc, "Template", "Name", templateName);
		if (templateNd == null) {
			new Exception("Could not find the template.");
		}
		Element templateEle = (Element) templateNd;
		String documentId = templateEle.getAttribute("Workflow_Document_ID");
		String docName = templateEle.getAttribute("Workflow_Document_Name");
		String statusStr = "Rejected";
		if (statusStr.equalsIgnoreCase("Approve") || statusStr.equalsIgnoreCase("Approved")) {
			statusStr = "Approve";
		} else if (statusStr.equalsIgnoreCase("Completed") || statusStr.equalsIgnoreCase("Complete")) {
			statusStr = "Completed";
		} else if (statusStr.equalsIgnoreCase("Rejected") || statusStr.equalsIgnoreCase("Reject")) {
			statusStr = "Reject";
		} else if (statusStr.equalsIgnoreCase("Initiate") || statusStr.equalsIgnoreCase("Initiated")) {
			statusStr = "Initiate";
		} else if (statusStr.equalsIgnoreCase("Remind") || statusStr.equalsIgnoreCase("Reminded")) {
			statusStr = "Remind";
		} else if (statusStr.equalsIgnoreCase("Escalate") || statusStr.equalsIgnoreCase("Escalated")) {
			statusStr = "Escalate";
		} else if (statusStr.equalsIgnoreCase("Cancel")) {
			statusStr = "Cancel";
		} else if (statusStr.equalsIgnoreCase("Pause")) {
			statusStr = "Pause";
		} else if (statusStr.equalsIgnoreCase("Acknowledged")) {
			statusStr = "Acknowledged";
		}
		Hashtable<String, String> detailHT = WorkflowManager.performingAction(username, notes, statusStr, docName,
				documentId, "", "", "", "");
		Node docNd = Globals.getNodeByAttrVal(docXmlDoc, "Document", "Document_ID", documentId);
		Element docEle = (Element) docNd;
		Element wfEle = (Element) docEle.getElementsByTagName("Workflow").item(0);
		String currStgNo = wfEle.getAttribute("Current_Stage_No");
		String wfType = wfEle.getAttribute("Workflow_Type");
		Hashtable testHT = WorkflowManager.rejectNextUsers(docXmlDoc, wfEle, currStgNo, username);
		String nxtStgMsg = "";
		if (testHT != null) {
			String users = "";
			ArrayList<String> testAL = testHT.get("Users") == null ? new ArrayList<String>()
					: (ArrayList<String>) testHT.get("Users");
			String stage = testHT.get("Stage") == null ? "" : (String) testHT.get("Stage");
			nxtStgMsg = stage;
			for (int i = 0; i < testAL.size(); i++) {
				nxtStgMsg = nxtStgMsg + (i == 0 ? "(" : "") + testAL.get(i) + (i < testAL.size() - 1 ? "," : "")
						+ (i == testAL.size() - 1 ? ")" : "");
				users = testAL.get(i) + (i < testAL.size() - 1 ? ", " : "");
			}
			if (wfType.equalsIgnoreCase("Simple")) {
				nxtStgMsg = users;
			}
		}
		detailHT.put("NextStageUserDetails", nxtStgMsg);
		System.out.println("detailHT):>>222>" + detailHT);
		return detailHT;
	}

	public static Hashtable<String, String> saveSignatureAsImg(String templateName, String svgData, String customerKey,
			String fieldId) throws Exception {
		Hashtable<String, String> detailsHT = new Hashtable<>();
		PropUtil prop = new PropUtil();
		String iisConfigFilePath = prop.getProperty("IISCONFIGFILEPATH");
		String templateFileName = prop.getProperty("IISTEMPLATEFILENAME");
		String signatureFolder = prop.getProperty("IISESIGNIMAGEPATH");
		String dateFormat = prop.getProperty("DATE_FORMAT");
		String iisAdminFilePath = prop.getProperty("IISUSERADMINFILEPATH");
		String iisAdminFileName = prop.getProperty("IISUSERADMINFILENAME");

		// JSONArray jObj = (JSONArray) new JSONParser().parse(metadata);
		// String formName = jObj.get("FormName").toString();
		// String formDecription = jObj.get("FormDescription").toString();
		// String customerKey = jObj.get("CustomerKey").toString();
		// String createdBy = jObj.get("CreatedBy").toString();
		System.out.println(templateName + "-=-=-=-=-=-=-formName=-=-=-=-=-=" + svgData);
		Document iisAdminDoc = Globals.openXMLFile(iisAdminFilePath, iisAdminFileName);
		Node adminNd = Globals.getNodeByAttrVal(iisAdminDoc, "Admin", "CustomerKey", customerKey);
		if (adminNd == null) {
			throw new Exception("Could not find the customer.");
		}
		Element adminEle = (Element) adminNd;
		String compName = adminEle.getAttribute("CompanyName");
		// iisConfigFilePath =
		// iisConfigFilePath+compName+"_"+customerKey+"\\"+"User_Templates_Config\\";
		// Document doc = Globals.openXMLFile(iisConfigFilePath, templateFileName);
		// System.out.println(templateName+"-=-=-=-=-=-=-formName=-=-=-=-=-="+metadata);
		// Date date = new Date();
		// SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		// String currDate = sdf.format(date);
		// Node templateNd = Globals.getNodeByAttrVal(doc, "Template", "Name",
		// templateName);
		// if(templateNd == null) {
		// new Exception("Could not find the template.");
		// }
		// Element templateEle = (Element)templateNd;
		// Element esignEle =
		// templateEle.getElementsByTagName("ESign_Details").getLength() <= 0 ? null :
		// (Element)templateEle.getElementsByTagName("ESign_Details").item(0);

		signatureFolder = signatureFolder + compName + "_" + customerKey + "\\" + templateName + "_" + fieldId + "\\";
		File dir = new File(signatureFolder);
		dir.mkdirs();
		byte[] bytes = Base64.getDecoder().decode(new String(svgData).getBytes("UTF-8"));

		String result = new String(bytes);
		System.out.println("-=-=result-=-=:" + result + ":-=-=-result=-=");
		String svgFileStr = signatureFolder + fieldId + ".svg";
		File svgFile = new File(svgFileStr);
		svgFile.deleteOnExit();
		BufferedWriter buffWriter = new BufferedWriter(new FileWriter(svgFile));
		buffWriter.write(result);
		buffWriter.flush();
		buffWriter.close();

		String svg_URI_input = Paths.get(svgFileStr).toUri().toURL().toString();
		TranscoderInput input_svg_image = new TranscoderInput(svg_URI_input);
		// Step-2: Define OutputStream to PNG Image and attach to TranscoderOutput
		String pngFile = signatureFolder + fieldId + ".png";
		OutputStream png_ostream = new FileOutputStream(pngFile);
		TranscoderOutput output_png_image = new TranscoderOutput(png_ostream);
		// Step-3: Create PNGTranscoder and define hints if required
		PNGTranscoder my_converter = new PNGTranscoder();
		// Step-4: Convert and Write output
		my_converter.transcode(input_svg_image, output_png_image);
		// Step 5- close / flush Output Stream
		png_ostream.flush();
		png_ostream.close();

		// SvgDocument svgDocument = SvgDocument.Open(@"");
		// string imagePath = @"C:\Users\JavaDev05\Documents\Esign Files\local.jpg";
		// svgDocument.Draw().Save(imagePath);

		// String fileNameWithPath = formsDataFolder;
		// Random rand = new Random();
		// File jsonFile = null;
		// while(true) {
		// int uniqueNo = rand.nextInt((99999 - 10000) + 1) + 10000;
		// fileNameWithPath = fileNameWithPath+"FormData_"+uniqueNo+".json";
		//
		// jsonFile = new File(fileNameWithPath);
		// if(!jsonFile.exists())
		// break;
		// }
		// BufferedWriter buffWriter = new BufferedWriter(new FileWriter(jsonFile));
		// buffWriter.write(metadata);
		// buffWriter.flush();
		// buffWriter.close();
		// esignEle.setAttribute("FormDataFilePath", fileNameWithPath);
		// Globals.writeXMLFile(doc, iisConfigFilePath, templateFileName);
		detailsHT.put("FilePath", pngFile);
		detailsHT.put("Status", "Success");
		detailsHT.put("StatusDetails", "Form metadata is save successfully.");
		return detailsHT;
	}
	
	public static Hashtable<String, String> saveSignatureAsImgform(String templateName, String svgData, String customerKey,
			String fieldId,String filename) throws Exception {
		PropUtil prop = new PropUtil();
		Hashtable<String, String> detailsHT = new Hashtable<>();
		String iisConfigFilePath = prop.getProperty("IISCONFIGFILEPATH");
		String templateFileName = prop.getProperty("IISTEMPLATEFILENAME");
		String signatureFolder = prop.getProperty("IISESIGNIMAGEPATH");
		String dateFormat = prop.getProperty("DATE_FORMAT");
		String iisAdminFilePath = prop.getProperty("IISUSERADMINFILEPATH");
		String iisAdminFileName = prop.getProperty("IISUSERADMINFILENAME");
		
		System.out.println(templateName + "-=-=-=-=-=-=-formName=-=-=-=-=-=" + svgData);
		Document iisAdminDoc = Globals.openXMLFile(iisAdminFilePath, iisAdminFileName);
		Node adminNd = Globals.getNodeByAttrVal(iisAdminDoc, "Admin", "CustomerKey", customerKey);
		if (adminNd == null) {
			throw new Exception("Could not find the customer.");
		}
		Element adminEle = (Element) adminNd;
		String compName = adminEle.getAttribute("CompanyName");		

		signatureFolder = signatureFolder + compName + "_" + customerKey + "\\" + templateName + "_" + fieldId + "\\";
		
		BufferedImage image = null;
		byte[] imageByte;

		BASE64Decoder decoder = new BASE64Decoder();
		imageByte = decoder.decodeBuffer(svgData);
		ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
		image = ImageIO.read(bis);
		bis.close();
		
		File file = new File(signatureFolder);
	      //Creating the directory
		if(!file.isDirectory()) {
	      boolean bool = file.mkdir();
		}
	      
		Random rand = new Random();
		File pngfile = null;
		while (true) {
			int uniqueNo = rand.nextInt((99999 - 10000) + 1) + 10000;
			signatureFolder = signatureFolder + filename;

			pngfile = new File(signatureFolder);
			if (!pngfile.exists())
				break;
		}
		
		// write the image to a file
		File outputfile = new File(pngfile.toString());
		ImageIO.write(image, "png", outputfile);

		
		detailsHT.put("FilePath", pngfile.toString());
		detailsHT.put("Status", "Success");
		detailsHT.put("StatusDetails", "Form metadata is save successfully.");
		return detailsHT;
	}

	public static void getNodeByID(Node formsListNd, String id, ArrayList<Node> testAL) {
		NodeList ndList = formsListNd.getChildNodes();
		for (int i = 0; i < ndList.getLength(); i++) {
			if (ndList.item(i).getNodeType() == Node.ELEMENT_NODE) {
				Element ele = (Element) ndList.item(i);
				String idXML = ele.getAttribute("ID");
				String type = ele.getAttribute("Type");
				String name = ele.getAttribute("Name");

				if (id.equalsIgnoreCase(idXML)) {
					testAL.add(ndList.item(i));
					System.out.println(name + "-=-=-=-=-=-=-type=-=-=-=" + testAL.size() + "-=-=-=-=-=-=-=" + idXML
							+ "-=-=-=-=-=" + id);
					return;
				} else if (type.equalsIgnoreCase("Folder")) {
					getNodeByID(ele, id, testAL);
				}
			}
		}
		// return null;
	}

	public static String getFormData(String formId, String customerKey, String dataFileName) throws Exception {
		String formMetadata = "";
		PropUtil prop = new PropUtil();
		String formsdata = prop.getProperty("FORMSDATA_FOLDER");
		String iisAdminFilePath = prop.getProperty("IISUSERADMINFILEPATH");
		String iisAdminFileName = prop.getProperty("IISUSERADMINFILENAME");
		Document iisAdminDoc = Globals.openXMLFile(iisAdminFilePath, iisAdminFileName);
		Node adminNd = Globals.getNodeByAttrVal(iisAdminDoc, "Admin", "CustomerKey", customerKey);
		if (adminNd == null) {
			new Exception("Could not find the customer.");
		}
		Element adminEle = (Element) adminNd;
		String compName = adminEle.getAttribute("CompanyName");
		formsdata = formsdata + compName + "_" + customerKey + "\\" + formId + "\\" + dataFileName;

		File metadataFile = new File(formsdata);
		if (!metadataFile.exists()) {
			throw new Exception("Form metadata not found.");
		}
		FileReader re = new FileReader(metadataFile);
		BufferedReader buffReader = new BufferedReader(re);
		String jsonStr = "";
		String line = "";
		while ((line = buffReader.readLine()) != null) {
			jsonStr = jsonStr + line;
		}
//		re.close();
		buffReader.close();
		JSONObject jObj = (JSONObject) new JSONParser().parse(jsonStr);
		jObj.put("Status", "Success");
		jObj.put("StatusDetails", "Success");
		return jObj.toJSONString();
	}

	public static String getFormMetadata(String formId, String customerKey) throws Exception {
		String formMetadata = "";
		PropUtil prop = new PropUtil();
		String iisConfigFilePath = prop.getProperty("IISCONFIGFILEPATH");
		String formsXMLFileName = prop.getProperty("IISFORMCONFIGFILENAME");
		String formsMetadataFolder = prop.getProperty("FORMSMETADATA_FOLDER");
		String dateFormat = prop.getProperty("DATE_FORMAT");
		String iisAdminFilePath = prop.getProperty("IISUSERADMINFILEPATH");
		String iisAdminFileName = prop.getProperty("IISUSERADMINFILENAME");
		Document iisAdminDoc = Globals.openXMLFile(iisAdminFilePath, iisAdminFileName);
		Node adminNd = Globals.getNodeByAttrVal(iisAdminDoc, "Admin", "CustomerKey", customerKey);
		if (adminNd == null) {
			new Exception("Could not find the customer.");
		}
		Element adminEle = (Element) adminNd;
		String compName = adminEle.getAttribute("CompanyName");
		iisConfigFilePath = iisConfigFilePath + compName + "_" + customerKey + "\\" + "User_Templates_Config\\";
		Document doc = Globals.openXMLFile(iisConfigFilePath, formsXMLFileName);
		Node formsListNd = doc.getElementsByTagName("FormsList").item(0);
		Node formNd = null;
		ArrayList<Node> ndAL = new ArrayList<>();
		getNodeByID(formsListNd, formId, ndAL);
		if (ndAL.size() > 0)
			formNd = ndAL.get(0);
		if (formNd == null) {
			throw new Exception("Form metadata not found.");
		}
		Element ele = (Element) formNd;

		String fileNameWithPath = ele.getAttribute("MetadataFilePath");
		File metadataFile = new File(fileNameWithPath);
		if (!metadataFile.exists()) {
			throw new Exception("Form metadata not found.");
		}
		BufferedReader buffReader = new BufferedReader(new FileReader(metadataFile));
		String jsonStr = "";
		String line = "";
		while ((line = buffReader.readLine()) != null) {
			jsonStr = jsonStr + line;
		}
		JSONObject jObj = (JSONObject) new JSONParser().parse(jsonStr);
		jObj.put("Status", "Success");
		jObj.put("StatusDetails", "Success");
		return jObj.toJSONString();
	}

	public static String getEsignFormMetadata(String templateName, String customerKey) throws Exception {
		String formMetadata = "";
		PropUtil prop = new PropUtil();
		String iisConfigFilePath = prop.getProperty("IISCONFIGFILEPATH");
		String templateFileName = prop.getProperty("IISTEMPLATEFILENAME");
		String formsMetadataFolder = prop.getProperty("FORMSMETADATA_FOLDER");
		String dateFormat = prop.getProperty("DATE_FORMAT");
		String iisAdminFilePath = prop.getProperty("IISUSERADMINFILEPATH");
		String iisAdminFileName = prop.getProperty("IISUSERADMINFILENAME");
		Document iisAdminDoc = Globals.openXMLFile(iisAdminFilePath, iisAdminFileName);
		Node adminNd = Globals.getNodeByAttrVal(iisAdminDoc, "Admin", "CustomerKey", customerKey);
		if (adminNd == null) {
			new Exception("Could not find the customer.");
		}
		Element adminEle = (Element) adminNd;
		String compName = adminEle.getAttribute("CompanyName");
		iisConfigFilePath = iisConfigFilePath + compName + "_" + customerKey + "\\" + "User_Templates_Config\\";
		Document doc = Globals.openXMLFile(iisConfigFilePath, templateFileName);
		Node templateNd = Globals.getNodeByAttrVal(doc, "Template", "Name", templateName);

		if (templateNd == null) {
			throw new Exception("Could not find the template.");
		}
		Element templateEle = (Element) templateNd;
		Element esignEle = templateEle.getElementsByTagName("ESign_Details").getLength() <= 0 ? null
				: (Element) templateEle.getElementsByTagName("ESign_Details").item(0);
		if (esignEle == null)
			throw new Exception("Esign is not configured for this template.");
		String fileNameWithPath = templateEle.getAttribute("MetadataFilePath");
		File metadataFile = new File(fileNameWithPath);
		if (!metadataFile.exists()) {
			throw new Exception("Form metadata not found.");
		}
		BufferedReader buffReader = new BufferedReader(new FileReader(metadataFile));
		String jsonStr = "";
		String line = "";
		while ((line = buffReader.readLine()) != null) {
			jsonStr = jsonStr + line;
		}
		JSONObject jObj = (JSONObject) new JSONParser().parse(jsonStr);
		jObj.put("Status", "Success");
		jObj.put("StatusDetails", "Success");
		return jObj.toJSONString();
	}

	// <Admin CompanyName="cygnussoftwares" CustomerKey="2oMOq"
	// CompanyNameEntered="Yes" StartedDate="10/3/2018 5:26:16 PM"
	// ExpireDate="10/28/2018 5:26:16 PM" pack="Trial">
	// <User Key="2oMOqmLE" UserID="krishnacygnus95@gmail.com" LastName="R"
	// FirstName="krishnaraj" ticker="false" />
	// </Admin>
	public static Hashtable<String, String> updateAdminFile(String jsonStr) throws Exception {
		Hashtable<String, String> detailsHT = new Hashtable<>();
		PropUtil prop = new PropUtil();
		String iisAdminFilePath = prop.getProperty("IISUSERADMINFILEPATH");
		String iisAdminFileName = prop.getProperty("IISUSERADMINFILENAME");
		Document iisAdminDoc = Globals.openXMLFile(iisAdminFilePath, iisAdminFileName);
		Node adminNd = iisAdminDoc.getElementsByTagName("Admin_Users").item(0);
		NodeList adminChNdList = adminNd.getChildNodes();
		System.out.println("-=-=-=-Form=-=-jsonStr=-=-=-=-=" + jsonStr);
		JSONObject jObj = (JSONObject) new JSONParser().parse(jsonStr);

		String type = (String) jObj.get("SubcriptionMode");
		String mailId = (String) jObj.get("MailId");
		JSONObject adminHM = jObj.get("Admin") == null ? new JSONObject() : (JSONObject) jObj.get("Admin");
		JSONObject userHM = jObj.get("User") == null ? new JSONObject() : (JSONObject) jObj.get("User");
		Iterator<Entry<String, String>> adminIt = adminHM.entrySet().iterator();
		Iterator<Entry<String, String>> userIt = userHM.entrySet().iterator();
		// HashM
		// String subcriptionType = (String)jObj.get("SubcriptionType");
		// String subcriptionStartDate = (String)jObj.get("SubcriptionStartDate");
		// String subcriptionEndDate = (String)jObj.get("SubcriptionEndDate");
		// String payerEmail = jObj.get("PayerEmail") == null ? "" :
		// (String)jObj.get("PayerEmail");
		// String payerID = jObj.get("PayerID") == null ? "" :
		// (String)jObj.get("PayerID");
		// String productName = jObj.get("ProductName") == null ? "" :
		// (String)jObj.get("ProductName");

		if (type.equalsIgnoreCase("Trail2Subscription")) {

			changeSubsriptionPlan(adminChNdList, mailId, adminIt, userIt);
		} else if (type.equalsIgnoreCase("Buy_Count")) {
			// String mailId = (String)jObj.get("MailId");
			changeSubsriptionPlan(adminChNdList, mailId, adminIt, userIt);
		} else {
			// String mailId = (String)jObj.get("MailId");
			boolean flag = changeSubsriptionPlan(adminChNdList, mailId, adminIt, userIt);
			System.out.println(mailId + ": mailId Key :" + flag);
			if (flag) {
				Globals.writeXMLFile(iisAdminDoc, iisAdminFilePath, iisAdminFileName);
				detailsHT.put("Status", "Success");
				detailsHT.put("StatusDetails", "Form metadata is save successfully.");
				return detailsHT;
			}

			ArrayList<String> custKeyAL = getAllCustomerKeyFromAdminXML(iisAdminDoc);
			Element adminEle = iisAdminDoc.createElement("Admin");
			Element userEle = iisAdminDoc.createElement("User");
			String custKey = generateRandomAlphaNumericStr(8);
			System.out.println("Customer Key :" + custKey);
			while (true) {
				if (!custKeyAL.contains(custKey)) {
					break;
				}
				custKey = generateRandomAlphaNumericStr(8);
			}
			String passKey = generateRandomAlphaNumericStr(8);
			// adminEle.setAttribute("CompanyName", "");
			adminEle.setAttribute("CustomerKey", custKey);
			// adminEle.setAttribute("CompanyNameEntered", "No");
			// adminEle.setAttribute("SubcriptionMode", type);
			// adminEle.setAttribute("pack", "Premium");
			// adminEle.setAttribute("SubcriptionType", subcriptionType);
			// adminEle.setAttribute("UserID", mailId);
			// adminEle.setAttribute("Key", passKey);
			// adminEle.setAttribute("StartedDate", subcriptionStartDate);
			// adminEle.setAttribute("ExpireDate", subcriptionEndDate);
			// adminEle.setAttribute("PayerEmail", payerEmail);
			// adminEle.setAttribute("PayerID", payerID);
			// adminEle.setAttribute("ProductName", productName);
			while (adminIt.hasNext()) {
				Entry<java.lang.String, java.lang.String> entry = (Entry<java.lang.String, java.lang.String>) adminIt
						.next();
				if (entry.getKey() != null) {
					adminEle.setAttribute(entry.getKey(), entry.getValue() == null ? "" : entry.getValue());
				}
			}

			while (userIt.hasNext()) {
				Entry<java.lang.String, java.lang.String> entry = (Entry<java.lang.String, java.lang.String>) userIt
						.next();
				if (entry.getKey() != null) {
					userEle.setAttribute(entry.getKey(), entry.getValue() == null ? "" : entry.getValue());
				}
			}
			adminNd.appendChild(adminEle);
			adminEle.appendChild(userEle);
			String formsMetadata = prop.getProperty("FORMSMETADATA_FOLDER");
			String formsdata = prop.getProperty("FORMSDATA_FOLDER");
			String formsHTML = prop.getProperty("FORMSHTML_FOLDER");
			String iisConfigPath = prop.getProperty("IISCONFIGFILEPATH");
			String iisConsolePath = prop.getProperty("IISCONSOLEPATH");
			String iisDropBoxPath = prop.getProperty("IISDROPBOXPATH");
			String iisGoogleDrivePath = prop.getProperty("IISGOOGLEDRIVEPATH");
			String iisOneDrivePath = prop.getProperty("IISONEDRIVEPATH");
			String iisOpPath = prop.getProperty("IISOUTPUTPATH");
			String iisSSPath = prop.getProperty("IISSMARTSHEETPATH");
			String iisSupportFilesPath = prop.getProperty("IISSUPPORTFILESPATH");
			String iisTempFilesPath = prop.getProperty("IISTEMPFILESPATH");
			String iisTemplateXmlPath = prop.getProperty("IISTEMPLATEXMLPATH");

			File file = new File(formsMetadata + "\\" + mailId + "_" + custKey);
			file.mkdirs();

			file = new File(formsdata + "\\" + mailId + "_" + custKey);
			file.mkdirs();

			file = new File(formsHTML + "\\" + mailId + "_" + custKey);
			file.mkdirs();

			file = new File(iisConsolePath + "\\" + mailId + "_" + custKey);
			file.mkdirs();

			file = new File(iisDropBoxPath + "\\" + mailId + "_" + custKey);
			file.mkdirs();

			file = new File(iisGoogleDrivePath + "\\" + mailId + "_" + custKey);
			file.mkdirs();

			file = new File(iisOneDrivePath + "\\" + mailId + "_" + custKey);
			file.mkdirs();

			file = new File(iisOpPath + "\\" + mailId + "_" + custKey);
			file.mkdirs();

			file = new File(iisSSPath + "\\" + mailId + "_" + custKey);
			file.mkdirs();

			file = new File(iisSupportFilesPath + "\\" + mailId + "_" + custKey);
			file.mkdirs();

			file = new File(iisTempFilesPath + "\\" + mailId + "_" + custKey);
			file.mkdirs();

			File configFile = new File(iisConfigPath + "\\" + mailId + "_" + custKey);
			file.mkdirs();

			File templateFilePath = new File(iisTemplateXmlPath);
			FileUtils.copyDirectory(templateFilePath, configFile);
		}
		Globals.writeXMLFile(iisAdminDoc, iisAdminFilePath, iisAdminFileName);
		detailsHT.put("Status", "Success");
		detailsHT.put("StatusDetails", "Form metadata is save successfully.");
		return detailsHT;
	}

	public static boolean changeSubsriptionPlan(NodeList adminChNdList, String mailId,
			Iterator<Entry<String, String>> adminIt, Iterator<Entry<String, String>> userIt) {
		boolean flag = false;
		for (int i = 0; i < adminChNdList.getLength(); i++) {
			if (adminChNdList.item(i).getNodeType() == Node.ELEMENT_NODE) {
				Element adminEle = (Element) adminChNdList.item(i);
				String userId = adminEle.getAttribute("UserID");
				if (userId.equalsIgnoreCase(mailId)) {
					flag = true;
					// adminEle.setAttribute("SubcriptionMode", type);
					adminEle.setAttribute("pack", "Premium");
					// adminEle.setAttribute("SubcriptionType", subcriptionType);
					// adminEle.setAttribute("StartedDate", subcriptionStartDate);
					// adminEle.setAttribute("ExpireDate", subcriptionEndDate);
					// adminEle.setAttribute("PayerEmail", payerEmail);
					// adminEle.setAttribute("PayerID", payerID);
					// adminEle.setAttribute("ProductName", productName);
					while (adminIt.hasNext()) {
						Entry<java.lang.String, java.lang.String> entry = (Entry<java.lang.String, java.lang.String>) adminIt
								.next();
						if (entry.getKey() != null) {
							adminEle.setAttribute(entry.getKey(), entry.getValue() == null ? "" : entry.getValue());
						}
					}
					break;
				}
				NodeList userNdList = adminEle.getChildNodes();
				for (int j = 0; j < userNdList.getLength(); j++) {
					if (userNdList.item(j).getNodeType() == Node.ELEMENT_NODE) {
						Element userEle = (Element) userNdList.item(j);
						userId = userEle.getAttribute("UserID");
						if (userId.equalsIgnoreCase(mailId)) {
							flag = true;
							// adminEle.setAttribute("SubcriptionMode", type);
							adminEle.setAttribute("pack", "Premium");
							// adminEle.setAttribute("SubcriptionType", subcriptionType);
							// adminEle.setAttribute("StartedDate", subcriptionStartDate);
							// adminEle.setAttribute("ExpireDate", subcriptionEndDate);
							// adminEle.setAttribute("PayerEmail", payerEmail);
							// adminEle.setAttribute("PayerID", payerID);
							// adminEle.setAttribute("ProductName", productName);
							while (adminIt.hasNext()) {
								Entry<java.lang.String, java.lang.String> entry = (Entry<java.lang.String, java.lang.String>) adminIt
										.next();
								if (entry.getKey() != null) {
									adminEle.setAttribute(entry.getKey(),
											entry.getValue() == null ? "" : entry.getValue());
								}
							}

							while (userIt.hasNext()) {
								Entry<java.lang.String, java.lang.String> entry = (Entry<java.lang.String, java.lang.String>) userIt
										.next();
								if (entry.getKey() != null) {
									userEle.setAttribute(entry.getKey(),
											entry.getValue() == null ? "" : entry.getValue());
								}
							}
							break;
						}
					}
				}
			}
		}
		return flag;
	}

	public static ArrayList<String> getAllCustomerKeyFromAdminXML(Document iisAdminDoc) {
		ArrayList<String> custKeyAL = new ArrayList<>();
		NodeList adminNdList = iisAdminDoc.getElementsByTagName("Admin");
		for (int i = 0; i < adminNdList.getLength(); i++) {
			if (adminNdList.item(i).getNodeType() == Node.ELEMENT_NODE) {
				Element adminEle = (Element) adminNdList.item(i);
				custKeyAL.add(adminEle.getAttribute("CustomerKey"));
			}
		}
		return custKeyAL;
	}

	public static String generateRandomAlphaNumericStr(int noOfCharacters) {
		String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
		char[] stringChars = new char[noOfCharacters];
		Random random = new Random();
		for (int i = 0; i < stringChars.length; i++) {
			stringChars[i] = chars.charAt(random.nextInt(chars.length()));
		}
		String finalString = new String(stringChars);
		String ranpass = finalString;
		return ranpass;
	}

	public static Hashtable<String, String> saveHTMLContent(String formId, String customerKey, String htmlText)
			throws Exception {
		Hashtable<String, String> detailsHT = new Hashtable<>();
		PropUtil prop = new PropUtil();
		String formsHTML = prop.getProperty("FORMSHTML_FOLDER");
		String iisAdminFilePath = prop.getProperty("IISUSERADMINFILEPATH");
		String iisAdminFileName = prop.getProperty("IISUSERADMINFILENAME");
		String iisConfigFilePath = prop.getProperty("IISCONFIGFILEPATH");
		String formsXMLFileName = prop.getProperty("IISFORMCONFIGFILENAME");
		Document iisAdminDoc = Globals.openXMLFile(iisAdminFilePath, iisAdminFileName);
		Node adminNd = Globals.getNodeByAttrVal(iisAdminDoc, "Admin", "CustomerKey", customerKey);
		if (adminNd == null) {
			throw new Exception("Could not find the customer.");
		}
		Element adminEle = (Element) adminNd;
		String compName = adminEle.getAttribute("CompanyName");
		formsHTML = formsHTML + compName + "_" + customerKey + "\\" + formId + "\\";
		File temp = new File(formsHTML);
		if (!temp.exists()) {
			temp.mkdirs();
		}
		String fileNameWithPath = formsHTML;
		Random rand = new Random();
		File htmlFile = null;
		while (true) {
			int uniqueNo = rand.nextInt((99999 - 10000) + 1) + 10000;
			fileNameWithPath = fileNameWithPath + "FormsHTML_" + uniqueNo + ".html";

			htmlFile = new File(fileNameWithPath);
			if (!htmlFile.exists())
				break;
		}
		BufferedWriter buffWriter = new BufferedWriter(new FileWriter(htmlFile));
		buffWriter.write(htmlText);
		buffWriter.flush();
		buffWriter.close();

		iisConfigFilePath = iisConfigFilePath + compName + "_" + customerKey + "\\" + "User_Templates_Config\\";
		Document doc = Globals.openXMLFile(iisConfigFilePath, formsXMLFileName);
		Node formsListNd = doc.getElementsByTagName("FormsList").item(0);

		Node formNd = null;
		ArrayList<Node> ndAL = new ArrayList<>();
		getNodeByID(formsListNd, formId, ndAL);
		if (ndAL.size() > 0)
			formNd = ndAL.get(0);
		if (formNd != null) {
			Element ele = (Element) formNd;
			ele.setAttribute("OutputFilePath", fileNameWithPath);
		}
		Globals.writeXMLFile(doc, iisConfigFilePath, formsXMLFileName);
		detailsHT.put("Status", "Success");
		detailsHT.put("StatusDetails", "Success");
		return detailsHT;
	}

	public static void saveEsignHTMLContent(String templateName, String customerKey, String htmlText, String esignflag)
			throws Exception {
		PropUtil prop = new PropUtil();
		String formsHTML = prop.getProperty("FORMSHTML_FOLDER");
		String iisAdminFilePath = prop.getProperty("IISUSERADMINFILEPATH");
		String iisAdminFileName = prop.getProperty("IISUSERADMINFILENAME");
		String iisConfigFilePath = prop.getProperty("IISCONFIGFILEPATH");
		String templateFileName = prop.getProperty("IISTEMPLATEFILENAME");
		String xmltemplatpName = "";
		if (esignflag.toLowerCase().equals("false")) {
			templateFileName = prop.getProperty("IISAVEASXML");
			xmltemplatpName = "Descriptor";

		} else {
			templateFileName = prop.getProperty("IISTEMPLATEFILENAME");
			xmltemplatpName = "Name";
		}
		Document iisAdminDoc = Globals.openXMLFile(iisAdminFilePath, iisAdminFileName);
		Node adminNd = Globals.getNodeByAttrVal(iisAdminDoc, "Admin", "CustomerKey", customerKey);
		if (adminNd == null) {
			throw new Exception("Could not find the customer.");
		}
		Element adminEle = (Element) adminNd;
		String compName = adminEle.getAttribute("CompanyName");
		formsHTML = formsHTML + compName + "_" + customerKey + "\\" + templateName + "\\";
		File temp = new File(formsHTML);
		if (!temp.exists()) {
			temp.mkdirs();
		}
		String fileNameWithPath = formsHTML;
		Random rand = new Random();
		File htmlFile = null;
		while (true) {
			int uniqueNo = rand.nextInt((99999 - 10000) + 1) + 10000;
			fileNameWithPath = fileNameWithPath + "FormsHTML_" + uniqueNo + ".html";

			htmlFile = new File(fileNameWithPath);
			if (!htmlFile.exists())
				break;
		}
		BufferedWriter buffWriter = new BufferedWriter(new FileWriter(htmlFile));
		buffWriter.write(htmlText);
		buffWriter.flush();
		buffWriter.close();
		System.out.println(customerKey + "-=-=-=-=-=customerKey-=-=-=-=-=-=-=" + templateName);
		iisConfigFilePath = iisConfigFilePath + compName + "_" + customerKey + "\\" + "User_Templates_Config\\";
		Document doc = Globals.openXMLFile(iisConfigFilePath, templateFileName);
		Node templateNd = Globals.getNodeByAttrVal(doc, "Template", xmltemplatpName, templateName);
		System.out.println(templateNd + "-=-=-=-=-=templateName-=-=-=-=-=-=-=" + templateName);
		if (templateNd != null) {
			Element templateEle = (Element) templateNd;
			Element esignEle = templateEle.getElementsByTagName("ESign_Details").getLength() <= 0 ? null
					: (Element) templateEle.getElementsByTagName("ESign_Details").item(0);
			if (esignEle == null)
				throw new Exception("Esign is not configured for this template.");
			System.out.println("-=-=-=-=-=-=-=-=-=-=-=-=" + fileNameWithPath);
			esignEle.setAttribute("OutputFilePath", fileNameWithPath);
		}
		Globals.writeXMLFile(doc, iisConfigFilePath, templateFileName);
	}

	public static Hashtable<String, String> downloadPDFWithEsignDetails(String docId, boolean esignEnableFlag)
			throws Exception {
		// CustomerKey
		Hashtable<String, String> detailsHT = new Hashtable<>();
		detailsHT.put("Status", "Success");
		detailsHT.put("StatusDetails", "Success");
		detailsHT.put("FileChanged", "false");
		PropUtil prop = new PropUtil();
		String hierDir = prop.getProperty("HIERARCHY_XML_DIR");
		String docXmlFileName = prop.getProperty("DOCUMENT_XML_FILE");
		String iisTempFilesPath = prop.getProperty("IISTEMPFILESPATH");
		Document docXmlDoc = Globals.openXMLFile(hierDir, docXmlFileName);
		Node docNd = Globals.getNodeByAttrVal(docXmlDoc, "Document", "Document_ID", docId);
		if (docNd == null) {
			throw new Exception("There is no document.");
		}
		Element docEle = (Element) docNd;
		String custKey = docEle.getAttribute("CustomerKey");
		String primaryDocName = docEle.getAttribute("Primary_FileName");
		Hashtable tempHT = getTemplateNdFromDocId(docId);
		Node templateNd = tempHT == null ? null : (Node) tempHT.get("TemplateNode");
		String changePdfFile = downloadWithValues(templateNd, primaryDocName, docId, iisTempFilesPath, esignEnableFlag);
		if (!changePdfFile.equals("")) {
			detailsHT.put("NewFileName", changePdfFile);
			detailsHT.put("FileChanged", "true");
		}
		return detailsHT;
	}

	public static String downloadWithValues(Node templateNd, String primaryDocName, String docId,
			String iisTempFilesPath, boolean esignEnableFlag) throws Exception {
		String changePdfFile = "";
		if (templateNd != null) {

			Element templateEle = (Element) templateNd;
			String templateName = templateEle.getAttribute("Name");
			Element esignEle = templateEle.getElementsByTagName("ESign_Details").getLength() <= 0 ? null
					: (Element) templateEle.getElementsByTagName("ESign_Details").item(0);
			if (esignEle != null) {
				String dataFilePath = esignEle.getAttribute("FormDataFilePath");
				File formDataFile = new File(dataFilePath);
				System.out.println("-========dataFilePath=========" + dataFilePath);
				if (formDataFile.exists()) {
					Random rd = new Random();
					RandomString rs = new RandomString(5, rd);
					String newPassword = rs.nextString();
					changePdfFile = iisTempFilesPath + templateName + "_" + newPassword + ".pdf";
					BufferedReader buffReader = new BufferedReader(new FileReader(formDataFile));
					StringBuilder jsonSB = new StringBuilder();
					String line = "";
					while ((line = buffReader.readLine()) != null) {
						jsonSB.append(line);
					}
					System.out.println("-=======jsonSB==========" + jsonSB);
					String pdfFile = "";
					if (!esignEnableFlag)
						pdfFile = getPrimaryFilePathInWf(primaryDocName, docId);
					else
						pdfFile = templateEle.getAttribute("Output_File4Esign");
					System.out.println("-=======pdfFile==========" + pdfFile);
					changePDFFileBasedOnEsign(jsonSB.toString(), pdfFile, changePdfFile);
				}
			}
		}
		return changePdfFile;
	}

	public static Hashtable getTemplateNdFromDocId(String docId) throws Exception {
		PropUtil prop = new PropUtil();
		String hierDir = prop.getProperty("HIERARCHY_XML_DIR");
		String docXmlFileName = prop.getProperty("DOCUMENT_XML_FILE");
		String iisConfigFilePath = prop.getProperty("IISCONFIGFILEPATH");
		String templateFileName = prop.getProperty("IISTEMPLATEFILENAME");
		String iisAdminFilePath = prop.getProperty("IISUSERADMINFILEPATH");
		String iisAdminFileName = prop.getProperty("IISUSERADMINFILENAME");
		String iisTempFilesPath = prop.getProperty("IISTEMPFILESPATH");
		Document docXmlDoc = Globals.openXMLFile(hierDir, docXmlFileName);
		Node docNd = Globals.getNodeByAttrVal(docXmlDoc, "Document", "Document_ID", docId);
		if (docNd == null) {
			throw new Exception("There is no document.");
		}
		Element docEle = (Element) docNd;
		String custKey = docEle.getAttribute("CustomerKey");
		String templateName = docEle.getAttribute("Template_Name");
		Document iisAdminDoc = Globals.openXMLFile(iisAdminFilePath, iisAdminFileName);
		Node adminNd = Globals.getNodeByAttrVal(iisAdminDoc, "Admin", "CustomerKey", custKey);
		System.out.println("-=-=-=-custKey=-=-=-="+custKey);
		if (adminNd == null) {
			// throw new Exception("Could not find the customer.");
			return null;
		}
		Element adminEle = (Element) adminNd;
		String compName = adminEle.getAttribute("CompanyName");
		iisConfigFilePath = iisConfigFilePath + compName + "_" + custKey + "\\" + "User_Templates_Config\\";
		iisTempFilesPath = iisTempFilesPath + compName + "_" + custKey + "\\";

		Document doc = Globals.openXMLFile(iisConfigFilePath, templateFileName);
		Node templateNd = Globals.getNodeByAttrVal(doc, "Template", "Name", templateName);
		Hashtable tempHT = new Hashtable<>();
		tempHT.put("CustomerKey", custKey == null ? "" : custKey);
		if (templateNd != null)
			tempHT.put("TemplateNode", templateNd);
		else {
			templateNd = Globals.getNodeByAttrVal(doc, "Template", "Workflow_Document_ID", docId);
			if (templateNd != null)
				tempHT.put("TemplateNode", templateNd);
		}
		return tempHT;
	}

	public static boolean checkEsignIsConfiguredInWF(String docId) throws Exception {
		return (getTemplateNdFromDocId(docId) == null ? false : true);
	}

	public static void changePDFFileBasedOnEsign(String jsonStr, String pdfFile, String changePdfFile)
			throws Exception {

		// String opPdfFile = pdfFile;
		System.out.println("-=======changePdfFile==========" + jsonStr);
		PdfReader reader = new PdfReader(pdfFile);

		PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(changePdfFile));
		org.json.simple.JSONArray jObjArr = (org.json.simple.JSONArray) new JSONParser().parse(jsonStr);
		// select two pages from the original document

		// reader.SelectPages("1" + "-" + 17);

		int num_of_elements = 0;
		for (int q = 1; q <= jObjArr.size(); q++) {
			num_of_elements = 0;

			org.json.simple.JSONObject jObj = (org.json.simple.JSONObject) jObjArr.get(q - 1);
			int pgNo = Integer.valueOf(jObj.get("page_no").toString());
			// gettins the page size in order to substract from the iTextSharp coordinates
			Rectangle pageSize = reader.getPageSize(Integer.valueOf(pgNo));

			// PdfContentByte from stamper to add content to the pages over the original
			// content
			PdfContentByte pbover = stamper.getOverContent(Integer.valueOf(pgNo));

			// List<string> element_id = new List<string>();
			// element_id.Clear();
			// //add content to the page using ColumnText
			// for (int c=0;c< result.Count;c++)
			// {
			// if(unique[q - 1]== result[c].page_no)
			// {
			// num_of_elements = num_of_elements + 1;
			// element_id.Add(result[c].id);
			// }
			// }
			// Debug.WriteLine(unique[q - 1] + " ====== " + num_of_elements);

			// for(int l=0;l< num_of_elements; l++)
			// {

			Font font = new Font();
			// font.setSize(25);
			float x = 0;
			float y = 0;
			String value1 = "";
			String defaultsize="";
			String defaultfami="";
			String fieldId = "";
			float width = 0;
			float height = 0;
			String mandatory = "";
			value1 = jObj.get("value").toString();
			System.out.println("ccccccccccccccccccccccccccccc------" + value1);
			if(value1.trim().equals("")) {
				value1=jObj.get("default")==null?"":jObj.get("default").toString();
			}
			defaultsize=jObj.get("fontsize")==null?"":jObj.get("fontsize").toString();
			defaultfami=jObj.get("fontfamily")==null?"":jObj.get("fontfamily").toString();			
			
			fieldId = jObj.get("id").toString();
			mandatory = jObj.get("mandatory").toString();

			System.out.println("ccccccccccccccccccccccccccccc------" + fieldId);

			x = Integer.valueOf(jObj.get("x").toString());// result[z].x;
			y = Integer.valueOf(jObj.get("y").toString());// result[z].y;
			width = Integer.valueOf(jObj.get("width").toString());// result[z].width;
			height = Integer.valueOf(jObj.get("height").toString());// result[z].height;
			String font_size = jObj.get("fontsize").toString();

			int f_size = 12;
			try {
				f_size = Integer.parseInt(font_size.replaceAll("[^0-9]", ""));
			} catch (Exception e) {
				f_size = 12;
			}
			// int

			// f_size=f_size-5;

			String font_color = jObj.get("fontcolor").toString();
			System.out.println("font------" + font_color);
			String font_family = jObj.get("fontfamily").toString();
			System.out.println(pageSize.getWidth() + "-=-=-=-=-=-=-=-=value1-=-=-=-=-=" + pageSize.getHeight());

			y = (pageSize.getHeight() - (y * 0.75f));

			x = (x * 0.75f);
			width = width * 0.75f;
			height = height * 0.75f;
			System.out.println(x + "-=-=-=-=-=-=-=-=x-=-=-=-=-=" + y);
			System.out.println(width + "-=-=-=-=-=-=-=-=height-=-=-=-=-=" + height);

			if (value1.equalsIgnoreCase("null") || value1.trim().isEmpty()) {
				if (fieldId.contains("eraser")) {
					PdfGState gState = new PdfGState();
					gState.setFillOpacity(1);
					pbover.setGState(gState);
					pbover.rectangle(x, (y - height), width, height);
					BaseColor Colorpan = WebColors.getRGBColor(font_color);
					pbover.setColorFill(Colorpan);
					pbover.fill();
					System.out.println("love");

				} else if (fieldId.contains("highlight")) {
					PdfGState gState = new PdfGState();
					gState.setFillOpacity(0.4f);
					pbover.setGState(gState);

					pbover.rectangle(x, (y - height), width, height);

					BaseColor Colorpan = WebColors.getRGBColor(font_color);

					pbover.setColorFill(Colorpan);
					pbover.fill();
					System.out.println("hate");

				} else if (fieldId.contains("blackout")) {
					PdfGState gState = new PdfGState();
					gState.setFillOpacity(1);
					pbover.setGState(gState);
					pbover.rectangle(x, (y - height), width, height);
					// BaseColor Colorpan = WebColors.getRGBColor(BLACK);
					pbover.setColorFill(BaseColor.BLACK);
					pbover.fill();
					System.out.println("adi dhool");
				}

				continue;
			}

			System.out.println("successsssssssssssss");
			PdfGState gState = new PdfGState();
			gState.setFillOpacity(1);
			pbover.setGState(gState);

			String fb = jObj.get("bold")==null?"": jObj.get("bold").toString();
			String fi = jObj.get("italic")==null?"":jObj.get("italic").toString();
			String fu = jObj.get("underline")==null?"": jObj.get("underline").toString();
			System.out.println("l--jjjjjjjjjjjjjj---------------------" + fb);

			// font = FontFactory.getFont(fontstyleExtra(font_family), f_size, Font.NORMAL);
			font = FontFactory.getFont(fontstyleExtra(font_family), Math.round(f_size * 0.75), Font.NORMAL);
			BaseColor myColorpan = WebColors.getRGBColor(font_color);
			font.setColor(myColorpan);
			if (fb.equalsIgnoreCase("bold") && fi.equalsIgnoreCase("italic") && fu.equalsIgnoreCase("underline")) {
				System.out.println("bold italic underline");
				font.setStyle(Font.UNDERLINE | Font.BOLD | Font.ITALIC);
			} else if (fb.equalsIgnoreCase("bold") && fi.equalsIgnoreCase("italic")) {
				System.out.println("bold italic ");
				font.setStyle(Font.BOLD | Font.ITALIC);
			} else if (fb.equalsIgnoreCase("bold") && fu.equalsIgnoreCase("underline")) {
				System.out.println("bold  underline");
				font.setStyle(Font.BOLD | Font.UNDERLINE);
			} else if (fi.equalsIgnoreCase("italic") && fu.equalsIgnoreCase("underline")) {
				System.out.println(" italic underline");
				font.setStyle(Font.ITALIC | Font.UNDERLINE);
			} else if (fi.equalsIgnoreCase("italic")) {
				System.out.println(" italic ");
				font.setStyle(Font.ITALIC);
			} else if (fu.equalsIgnoreCase("underline")) {
				System.out.println("underline");
				font.setStyle(Font.UNDERLINE);
			} else if (fb.equalsIgnoreCase("bold")) {
				System.out.println("bold ");
				font.setStyle(Font.BOLD);
			} else {
				System.out.println("normal");
				font.setStyle(Font.NORMAL);
			}

			System.out.println("ssssssssssssssssssssssssss------" + fieldId);

			if (fieldId.contains("text")) {
				// String[] font_prop = value1.split("~");
				// int f_size = Integer.parseInt(font_size.replaceAll("[^0-9]", ""));
				// font = FontFactory.getFont(fontstyleExtra(font_family), f_size, Font.NORMAL);
				// BaseColor myColorpan = WebColors.getRGBColor(font_color);
				// font.setColor(myColorpan);
				ColumnText.showTextAligned(pbover, com.itextpdf.text.Element.ALIGN_LEFT, new Phrase(value1, font), x,
						y - (height / 2) - 5, 0);
				// ColumnText.showTextAligned(pbover, com.itextpdf.text.Element.ALIGN_LEFT, new
				// Phrase(value1, font), x, (y-(height/2)), 0);
			}

			else if (fieldId.contains("section")) {

				int back = Integer.valueOf(jObj.get("spacing").toString());

				if (back != 0) {
					pbover.rectangle(x, (y - height), width, height);
				}

				BaseColor Colorpan;
				try {
					Colorpan = WebColors.getRGBColor(mandatory);
				} catch (Exception e) {
					Colorpan = WebColors.getRGBColor("#ffff00");
				}

				pbover.setColorFill(Colorpan);
				pbover.fill();
				System.out.println("petta");

				String bestseller = value1;
				String alternative = bestseller.replaceAll("`", "\n");

				System.out.println(bestseller + "::::::::::" + alternative);
				// x, y, x + width + 5, y + height + 5)
				Rectangle rect = new Rectangle(x, y + (f_size * 0.50f), (width + x) - 5, height);
				// Rectangle rect = new Rectangle( x,y, width+x, height-y);

				// Rectangle rect = new Rectangle( x, (y-height), width, height);
				ColumnText ct = new ColumnText(pbover);
				ct.setSimpleColumn(rect);
				ct.addElement(new Paragraph(alternative, font));
				ct.go();

			}

			else if (fieldId.contains("chargroup")) {
				System.out.println("chargroup");
				// String[] font_prop = value1.split("~");
				// int f_size = Integer.parseInt(font_size.replaceAll("[^0-9]", ""));
				// font = FontFactory.getFont(fontstyleExtra(font_family), f_size, Font.NORMAL);
				// BaseColor myColorpan = WebColors.getRGBColor(font_color);
				// font.setColor(myColorpan);
				ColumnText.showTextAligned(pbover, com.itextpdf.text.Element.ALIGN_LEFT, new Phrase(value1, font), x,
						y - height, 0);

			} else if (fieldId.contains("number")) {
				// String[] font_prop = value1.split("~");
				// int f_size = Integer.parseInt(font_size.replaceAll("[^0-9]", ""));
				// font = FontFactory.getFont(fontstyleExtra(font_family), f_size, Font.NORMAL);
				// BaseColor myColorpan = WebColors.getRGBColor(font_color);
				// font.setColor(myColorpan);
				ColumnText.showTextAligned(pbover, com.itextpdf.text.Element.ALIGN_LEFT, new Phrase(value1, font), x,
						y - (height / 2) - 5, 0);

			} else if (fieldId.contains("date")) {
				// String[] font_prop = value1.split("~");
				// int f_size = Integer.parseInt(font_size.replaceAll("[^0-9]", ""));
				// font = FontFactory.getFont(fontstyleExtra(font_family), f_size, Font.NORMAL);
				// BaseColor myColorpan = WebColors.getRGBColor(font_color);
				// font.setColor(myColorpan);
				ColumnText.showTextAligned(pbover, com.itextpdf.text.Element.ALIGN_LEFT, new Phrase(value1, font), x,
						y - (height / 2) - 5, 0);

			}

			else if (fieldId.contains("comments")) {
				// int f_size = Integer.parseInt(font_size.replaceAll("[^0-9]", ""));
				// font = FontFactory.getFont(fontstyleExtra(font_family), f_size, Font.NORMAL);
				// BaseColor myColorpan = WebColors.getRGBColor(font_color);
				// font.setColor(myColorpan);
				// ColumnText.showTextAligned(pbover, com.itextpdf.text.Element.ALIGN_LEFT, new
				// Phrase(font_prop[0], font), x, y , 0);
				// ColumnText.showTextAligned(pbover, com.itextpdf.text.Element.ALIGN_LEFT, new
				// Phrase(value1, font), x, (y - (height+f_size)+height) - 7 , 0);

				// font = FontFactory.getFont(fontstyleExtra(font_family), f_size, Font.NORMAL);
				// BaseColor myColorpan = WebColors.getRGBColor(font_color);
				// font.setColor(myColorpan);

				String bestseller = value1;
				String alternative = bestseller.replaceAll("`", "\n");
				System.out.println(bestseller + "::::::::::" + alternative);
				// x, y, x + width + 5, y + height + 5)
				// Rectangle rect = new Rectangle( x, y, (width+x)-5, height);

				// Rectangle rect = new Rectangle( x,y, width+x, height-y);
				Rectangle rect = new Rectangle(x, y + (f_size * 0.50f), (width + x) - 5, height);
				ColumnText ct = new ColumnText(pbover);
				ct.setSimpleColumn(rect);
				ct.addElement(new Paragraph(alternative, font));
				ct.go();

			} else if (fieldId.contains("multiline") || fieldId.contains("selectable")) {
				// font = FontFactory.getFont(fontstyleExtra(font_family), f_size, Font.NORMAL);
				// BaseColor myColorpan = WebColors.getRGBColor(font_color);
				// font.setColor(myColorpan);

				String bestseller = value1;
				String alternative = bestseller.replaceAll("`", "\n");
				System.out.println(bestseller + "::::::::::" + alternative);
				// x, y, x + width + 5, y + height + 5)
				// Rectangle rect = new Rectangle( x, y, (width+x)-5, height);
				// Rectangle rect = new Rectangle( x,y, width+x, height-y);
				Rectangle rect = new Rectangle(x, y + (f_size * 0.50f), (width + x) - 5, height);
				ColumnText ct = new ColumnText(pbover);
				ct.setSimpleColumn(rect);
				ct.addElement(new Paragraph(alternative, font));
				ct.go();

			} else if (fieldId.contains("eraser")) {
				// font = FontFactory.getFont(fontstyleExtra(font_family), f_size, Font.NORMAL);
				// BaseColor myColorpan = WebColors.getRGBColor(font_color);
				// font.setColor(myColorpan);

				String bestseller = value1;
				String alternative = bestseller.replaceAll("`", "\n");
				System.out.println(bestseller + "::::::hello::::" + alternative);
				// x, y, x + width + 5, y + height + 5)
				Rectangle rect = new Rectangle(x, y, (width + x) - 5, height);

				ColumnText ct = new ColumnText(pbover);
				ct.setSimpleColumn(rect);
				// ct.addElement(new Paragraph(alternative, font));
				ct.go();

			}

			else if (fieldId.contains("label")) {
				// int f_size = Integer.parseInt(font_size.replaceAll("[^0-9]", ""));
				// font = FontFactory.getFont(fontstyleExtra(font_family), f_size, Font.NORMAL);
				// BaseColor myColorpan = WebColors.getRGBColor(font_color);
				// font.setColor(myColorpan);
				ColumnText.showTextAligned(pbover, com.itextpdf.text.Element.ALIGN_LEFT, new Phrase(value1, font), x,
						y - (height / 2) - 5, 0);
				// ColumnText.showTextAligned(pbover, com.itextpdf.text.Element.ALIGN_LEFT, new
				// Phrase(font_prop[0], font), x, y - (height/2) , 0);
			} else if (fieldId.contains("checkbox")) {
				// y = (int)(pageSize.getHeight() - y);

				// ColumnText.showTextAligned(pbover, com.itextpdf.text.Element.ALIGN_LEFT, new
				// Phrase(value1, font), x, y - (height/2), 0);
				if (value1.contains("true")) {
					PdfContentByte pdfContentByte = pbover;

					com.itextpdf.text.Image image = com.itextpdf.text.Image
							.getInstance("https://ultradocuments.com/FieldsImg/checkedcheck.jpg"); //checked
					System.out.println(width + "-=-=-=-=-=-=-=-=subeeeeeeeee-=-=-=-=-=" + value1);
					image.setAbsolutePosition(x, y - (height));
					image.scaleToFit(new Rectangle(x, y, x + width + 5, y + height + 5));
					pdfContentByte.addImage(image);
				} else {
					PdfContentByte pdfContentByte = pbover;

					com.itextpdf.text.Image image = com.itextpdf.text.Image
							.getInstance("https://ultradocuments.com/FieldsImg/checkbox.png"); //unchecked
					System.out.println(width + "-=-=-=-=-=-=-=-=subeeeeeeeee-=-=-=-=-=" + value1);
					image.setAbsolutePosition(x, y - (height));
					image.scaleToFit(new Rectangle(x, y, x + width + 5, y + height + 5));
					pdfContentByte.addImage(image);
				}
			} else if (fieldId.contains("signature") || fieldId.contains("initial")) {
				
				System.out.println(width + "-=-=-=-=signature value-=-=-=-=" + value1);
				if (value1.contains("~")) {
					String[] words = value1.split("~");
					String fontsize = words[2].substring(0, words[2].length() - 2);
					String fontpath = fontstyle(words[1]);
					// value1 = words[0];
					// font.setSize(10);
					// y = (int)(pageSize.getHeight() - y);
					int fs = Integer.valueOf(fontsize);
					font = FontFactory.getFont(fontpath, fs, Font.NORMAL);

					ColumnText.showTextAligned(pbover, com.itextpdf.text.Element.ALIGN_LEFT, new Phrase(words[0], font),
							x, y - (height / 2) - 5, 0);
				}
				else if(!value1.contains("~")&&!value1.contains("/")&&!value1.contains("\\")) {															
					
					 String fontsze = defaultsize;
                     String fntpth = defaultfami;
                     value1 = value1 + "~" + fntpth + "~" + fontsze;
					String[] words = value1.split("~");
					String fontsize = words[2].substring(0, words[2].length() - 2);
					String fontpath = fontstyle(words[1]);
					int fs = Integer.valueOf(fontsize);
					font = FontFactory.getFont(fontpath, fs, Font.NORMAL);

					ColumnText.showTextAligned(pbover, com.itextpdf.text.Element.ALIGN_LEFT, new Phrase(words[0], font),
							x, y - (height / 2) - 5, 0);
				}
				else if (!value1.equalsIgnoreCase("null")) {
					PdfContentByte pdfContentByte = pbover;					
					System.out.println((y + height) + "-=-=-=-=-=-=-=-=-=-=value1-=-=-=-=-=-=-=" + value1);										
					String str="ESignImageFolder";
					value1=	value1.substring(value1.lastIndexOf(str)+(str.length()));					
					PropUtil prop=new PropUtil();
					String url= prop.getProperty("IISESIGNIMAGEPATH");
					value1=url+value1;
					System.out.println("-=-=-=-=-=-=-=-=value1-=-after replace=-=-=-=-=-=" + value1);
					com.itextpdf.text.Image image = com.itextpdf.text.Image.getInstance(value1);
					image.setAbsolutePosition(x, y - (height));
					image.scaleToFit(new Rectangle(x, y, x + width, y + height));
					pdfContentByte.addImage(image);				
				}

			}

		}
		stamper.close();
		reader.close();
		System.out.println("-=======Out==========" + pdfFile);
	}

	public static String fontstyleExtra(String textFormat) {
		String fontpath = "C:\\\\Windows\\\\Fonts\\\\times.TTF";
		if (textFormat.toLowerCase().equals("arial")) {
			fontpath = "C:\\Windows\\Fonts\\arial.TTF";
		} else if (textFormat.toLowerCase().equals("times new roman")) {
			fontpath = "C:\\Windows\\Fonts\\times.TTF";
		} else if (textFormat.toLowerCase().equals("courier new")) {
			fontpath = "C:\\Windows\\Fonts\\cour.TTF";
		} else if (textFormat.toLowerCase().equals("verdana")) {
			fontpath = "C:\\Windows\\Fonts\\verdana.TTF";

		} else if (textFormat.toLowerCase().equals("georgia")) {
			fontpath = "C:\\Windows\\Fonts\\georgia.TTF";
		} else if (textFormat.toLowerCase().equals("palace script mt")) {
			fontpath = "C:\\Windows\\Fonts\\PALSCRI.TTF";
		} else if (textFormat.toLowerCase().equals("garamond")) {
			fontpath = "C:\\Windows\\Fonts\\GARA.TTF";
		} else if (textFormat.toLowerCase().equals("bookman old style")) {
			fontpath = "C:\\Windows\\Fonts\\BOOKOSI.TTF";
		} else if (textFormat.toLowerCase().equals("comic sans ms")) {
			fontpath = "C:\\Windows\\Fonts\\comic.TTF";
		} else if (textFormat.toLowerCase().equals("trebuchet ms")) {
			fontpath = "C:\\Windows\\Fonts\\trebuc.TTF";
		} else if (textFormat.toLowerCase().equals("impact")) {
			fontpath = "C:\\Windows\\Fonts\\impact.TTF";
		}

		return fontpath;
	}

	public static String fontstyle(String textFormat) {
		String fontpath = "C:\\\\Windows\\\\Fonts\\\\times.TTF";
		if (textFormat.toLowerCase().equals("edwardian script itc")) {
			fontpath = "C:\\Windows\\Fonts\\ITCEDSCR.TTF";
		} else if (textFormat.toLowerCase().equals("brush script mt")) {
			fontpath = "C:\\Windows\\Fonts\\BRUSHSCI.TTF";
		} else if (textFormat.toLowerCase().equals("freestyle script")) {
			fontpath = "C:\\Windows\\Fonts\\FREESCPT.TTF";
		} else if (textFormat.toLowerCase().equals("french script mt")) {
			fontpath = "C:\\Windows\\Fonts\\FRSCRIPT.TTF";

		} else if (textFormat.toLowerCase().equals("gigi")) {
			fontpath = "C:\\Windows\\Fonts\\GIGI.TTF";
		} else if (textFormat.toLowerCase().equals("ink free")) {
			fontpath = "C:\\Windows\\Fonts\\Inkfree.TTF";
		} else if (textFormat.toLowerCase().equals("kunstler script")) {
			fontpath = "C:\\Windows\\Fonts\\KUNSTLER.TTF";
		} else if (textFormat.toLowerCase().equals("lucida handwriting")) {
			fontpath = "C:\\Windows\\Fonts\\LHANDW.TTF";
		} else if (textFormat.toLowerCase().equals("mistral")) {
			fontpath = "C:\\Windows\\Fonts\\MISTRAL.TTF";
		} else if (textFormat.toLowerCase().equals("rage italic")) {
			fontpath = "C:\\Windows\\Fonts\\RAGE.TTF";
		} else if (textFormat.toLowerCase().equals("script mt bold")) {
			fontpath = "C:\\Windows\\Fonts\\SCRIPTBL.TTF";
		} else if (textFormat.toLowerCase().equals("segoe script")) {
			fontpath = "C:\\Windows\\Fonts\\segoesc.TTF";
		} else if (textFormat.toLowerCase().equals("viner hand itc")) {
			fontpath = "C:\\Windows\\Fonts\\VINERITC.TTF";
		} else if (textFormat.toLowerCase().equals("vladimir script")) {
			fontpath = "C:\\Windows\\Fonts\\VLADIMIR.TTF";
		}
		return fontpath;
	}

	public static String getPrimaryFilePathInWf(String priDocName, String docId) {
		PropUtil prop = new PropUtil();
		String docVersionDir = "";
		try {
			docVersionDir = prop.getProperty("DOCUMENTVERSION_DIR");
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String folderName = (priDocName.contains(".") ? priDocName.substring(0, priDocName.lastIndexOf("."))
				: priDocName) + "_" + docId;
		String filePath = docVersionDir + folderName;
		File file = new File(filePath);
		if (!file.exists()) {
			return "";
		}
		String[] filePaths = file.list(new FilenameFilter() {
			@Override
			public boolean accept(File current, String name) {
				return new File(current, name).isDirectory();
			}
		});
		int[] folderNames = new int[filePaths.length];
		int i = 0;
		for (String val : filePaths) {
			int j = 0;
			try {
				File folder = new File(filePath + "\\" + val);
				System.out.println(filePath + "\\" + val + "-=-=-=-=-val=-=-=-=-=-=-=" + folder.exists());
				if (folder.exists()) {
					File tempfile = new File(filePath + "\\" + val + "\\" + priDocName);
					System.out.println(filePath + "\\" + val + "\\" + priDocName + "-=-=-=-=-val=-=ss-=-=-=-=-="
							+ tempfile.exists());
					if (tempfile.exists()) {
						j = Integer.valueOf(val.substring(val.lastIndexOf("\\") + 1));
						folderNames[i] = j;
						i++;
					}
				}

			} catch (NumberFormatException e) {
				// TODO: handle exception
				j = 0;
				folderNames[i] = j;
				i++;
			}

		}
		List<Integer> b = Arrays.asList(ArrayUtils.toObject(folderNames));
		int maxName = b.size() > 0 ? Collections.max(b) : 1;
		String filepath = filePath + "\\" + maxName + "\\";
		return filepath + priDocName;
	}

	public static String getUrlFromConfig(String nodeName) throws Exception {
		PropUtil prop = new PropUtil();
		String configFileName = prop.getProperty("HIERARCHY_CONFIG_FILE");
		String hierLeveldir = prop.getProperty("HIERARCHY_XML_DIR");
		Document doc = Globals.openXMLFile(hierLeveldir, configFileName);
		Element redirectEle = (Element) doc.getElementsByTagName(nodeName).item(0);
		String redirectURL = redirectEle.getAttribute("URL") == null || redirectEle.getAttribute("URL").trim().isEmpty()
				? ""
				: redirectEle.getAttribute("URL");
		return redirectURL;
	}

	public static String loadEsignHTML(String custKey, String templateName, String dataFilename, String id)
			throws Exception {
		String formDetails = "";
		System.out.println("custkey :" + custKey + ": templatename :" + templateName + ": dataFilename :" + dataFilename
				+ ": id :" + id);
		String htmlFilePath = "";
		PropUtil prop = new PropUtil();
		String iisAdminFilePath = prop.getProperty("IISUSERADMINFILEPATH");
		String iisAdminFileName = prop.getProperty("IISUSERADMINFILENAME");
		String iisConfigFilePath = prop.getProperty("IISCONFIGFILEPATH");
		String iisTemplateXMLFileName = prop.getProperty("IISTEMPLATEFILENAME");
		String HIERARCHY_XML_DIR = prop.getProperty("HIERARCHY_XML_DIR");
		String docXmlFileName = prop.getProperty("DOCUMENT_XML_FILE");

		Document iisAdminDoc = Globals.openXMLFile(iisAdminFilePath, iisAdminFileName);
		Node adminNd = Globals.getNodeByAttrVal(iisAdminDoc, "Admin", "CustomerKey", custKey);
		if (adminNd == null) {
			new Exception("Could not find the customer.");
		}
		System.out.println("Customer key passed");
		Element adminEle = (Element) adminNd;
		String compName = adminEle.getAttribute("CompanyName");
		iisConfigFilePath = iisConfigFilePath + compName + "_" + custKey + "\\" + "User_Templates_Config\\";
		Document doc = Globals.openXMLFile(iisConfigFilePath, iisTemplateXMLFileName);
		// Node formsListNd = doc.getElementsByTagName("FormsList").item(0);

		Node formNd = Globals.getNodeByAttrVal(doc, "Template", "Name", templateName);
		if (formNd == null)
			new Exception("Could not find the template.");
		Element templateEle = (Element) formNd;
		String wfDocId = templateEle.getAttribute("Workflow_Document_ID");
		System.out.println("Template passed :" + wfDocId);
		// ArrayList<Node> ndAL = new ArrayList<>();
		// FormsManager.getNodeByID(formsListNd, formId, ndAL);
		// if(ndAL.size() > 0)
		// formNd = ndAL.get(0);
		System.out.println("-=-=-=-=-=-ESign_Details=-=-=-=-=-=-="
				+ templateEle.getElementsByTagName("ESign_Details").getLength());
		Node esignDetailsNd = templateEle.getElementsByTagName("ESign_Details").getLength() > 0
				? templateEle.getElementsByTagName("ESign_Details").item(0)
				: null;
		// System.out.println("-=-=-=-=-=-esignDetailsNd=-=-=-=-=-=-="+esignDetailsNd.getNodeName());
		if (esignDetailsNd != null) {
			String downloadUrl = FormsManager.getUrlFromConfig("Download_Link");
			String consentURL = FormsManager.getUrlFromConfig("ConsentURL");
			Element ele = (Element) esignDetailsNd;
			htmlFilePath = ele.getAttribute("OutputFilePath");
			String dataJsonFile = ele.getAttribute("FormDataFilePath");
			System.out.println("htmlFilePath :" + htmlFilePath);
			BufferedReader reader = new BufferedReader(new FileReader(new File(htmlFilePath)));
			String line = "";
			StringBuilder sb = new StringBuilder();
			while ((line = reader.readLine()) != null) {
				sb.append(line);
				sb.append("\n");
			}
			templateName = templateName == null || templateName.trim().isEmpty() ? "" : templateName;
			custKey = custKey == null || custKey.trim().isEmpty() ? "" : custKey;
			dataFilename = dataFilename == null || dataFilename.trim().isEmpty() ? "" : dataFilename;
			formDetails = sb.toString().replace("$$FormID$$", templateName).replace("$$CustKey$$", custKey)
					.replace("$$DataFileName$$", dataFilename);

			Document docXmlDoc = Globals.openXMLFile(HIERARCHY_XML_DIR, docXmlFileName);
			Element docEle = (Element) Globals.getNodeByAttrVal(docXmlDoc, "Document", "Document_ID", wfDocId);
			if (docEle != null) {
				String primaryFileName = docEle.getAttribute("Primary_FileName");
				Element wfEle = (Element) docEle.getElementsByTagName("Workflow").item(0);
				String currStageNo = wfEle.getAttribute("Current_Stage_No");
				String currUser = "";
				String consentFlag = "false";// $$ConsentFlag$$
				String finalMsg = "";
				if (!currStageNo.equalsIgnoreCase("Completed") && !currStageNo.equalsIgnoreCase("Cancel")) {
					LoginProcessManager lpm = new LoginProcessManager();
					Hashtable currentstageDetailsHT = lpm.retriveStageDetailsFromXML(wfEle, currStageNo, "");
					Hashtable currentemplyeeDetailsHT = (Hashtable) currentstageDetailsHT.get("EmployeedetHT");
					Hashtable mssgeDetailsHT = (Hashtable) currentstageDetailsHT.get("MessagedetHT");
					Hashtable curentPropertiesHT = (Hashtable) currentstageDetailsHT.get("PropertiesHT");
					String Properties = (String) curentPropertiesHT.get("Properties");
					finalMsg = (String) mssgeDetailsHT.get("Final");
					for (int approv = 0; approv < currentemplyeeDetailsHT.size(); approv++) {
						Hashtable ApproversHT = (Hashtable) currentemplyeeDetailsHT.get(approv);
						String userStatus = (String) ApproversHT.get("User_Status");
						String member = (String) ApproversHT.get("empName");
						if (!userStatus.equalsIgnoreCase(finalMsg)) {
							if (Properties.equalsIgnoreCase("Serial")) {
								currUser = member;
								consentFlag = ApproversHT.get("ConsentFlag") == null
										|| String.valueOf(ApproversHT.get("ConsentFlag")).trim().isEmpty() ? "false"
												: (String) ApproversHT.get("ConsentFlag");
								break;
							} else {
								currUser = currUser.isEmpty() ? member : currUser + "~~" + member;
							}
						}
					}
				}
				System.out.println("-=-=-=-=-=-currStageNo=-=-=-=-=" + currStageNo);
				System.out.println(consentFlag + "-=-=-=-=-=-currUser=-=-=-=-=" + currUser);
				formDetails = formDetails.replace("$$CurrStage$$", currStageNo).replace("$$CurrStageUsers$$", currUser)
						.replace("$$ConsentFlag$$", consentFlag);
				// $docName$&amp;filepath=$filepath$&amp;docID=$docID$&amp;username=$username$&amp;downloadFrom=$downloadFrom$&amp;attachmentType=$Type$
				downloadUrl = downloadUrl.replace("$docName$", primaryFileName).replace("$docID$", wfDocId)
						.replace("$filepath$", "").replace("$username$", id).replace("$downloadFrom$", "Mail")
						.replace("$Type$", "Primary").replace("$Index$", currStageNo);
				consentURL = consentURL.replace("$DocId$", wfDocId);
				formDetails = formDetails.replace("$$DownloadURL$$", downloadUrl);
				formDetails = formDetails.replace("$$ConsentSaveURL$$", consentURL);
				File dataFilePath = new File(dataJsonFile);
				if (dataFilePath.exists()) {
					BufferedReader dataReader = new BufferedReader(new FileReader(dataFilePath));
					line = "";
					StringBuilder dataSB = new StringBuilder();
					while ((line = dataReader.readLine()) != null) {
						dataSB.append(line);
					}
					dataReader.close();
					formDetails = formDetails.replace("$$DataJsonStr$$", dataSB.toString().replace("\\\\", "/").replace("\\", "\\\\").replace("'", "\\'"));
				}
				boolean rejectFlag = templateNodeStageCheck(wfDocId);
				formDetails = formDetails.replace("$$RejectFlag$$", String.valueOf(rejectFlag));
				formDetails = formDetails.replace("$$actionName$$", finalMsg);
			}
			reader.close();
		}
		return formDetails;
	}

	public static boolean templateNodeStageCheck(String docID) throws Exception {
		boolean rejectFlag = false;
		try {
			PropUtil prop = new PropUtil();
			String hierDir = prop.getProperty("HIERARCHY_XML_DIR");
			String docXmlFileName = prop.getProperty("DOCUMENT_XML_FILE");
			Document docXmlDoc = Globals.openXMLFile(hierDir, docXmlFileName);
			Node docNd = Globals.getNodeByAttrVal(docXmlDoc, "Document", "Document_ID", docID);

			NodeList docDetailNodeList = docNd.getChildNodes();
			Element docEle = (Element) docNd;
			String DocumentName = docEle.getAttribute("DocumentName");
			String DocumentID = docEle.getAttribute("Document_ID");
			String Doc_CustmrKey = docEle.getAttribute("CustomerKey");
			System.out.println(
					DocumentName + "<==DocumentID===>" + DocumentID + "<====++Doc_CustmrKey++++===>>" + Doc_CustmrKey);
			d_ID = DocumentID;
			if (docID.equals(DocumentID)) {
				for (int i = 0; i < docDetailNodeList.getLength(); i++) {
					if (docDetailNodeList.item(i).getNodeType() == Node.ELEMENT_NODE
							&& docDetailNodeList.item(i).getNodeName().equals("Workflow")) {
						Node workflowNode = docDetailNodeList.item(i);
						Element wrkflowEle = (Element) workflowNode;
						String crrStageName = wrkflowEle.getAttribute("Current_Stage_Name");
						String crrStageNumr = wrkflowEle.getAttribute("Current_Stage_No");
						System.out.println(crrStageName + "<======crrStageNumr=====>" + crrStageNumr);
						if (crrStageNumr.contains("Complete")) {
							rejectFlag = false;
						} else if (crrStageNumr.contains("Cancel")) {
							rejectFlag = false;
						} else {
							int currstagNumbr = Integer.valueOf(crrStageNumr);
							Node stageNode = null;
							NodeList wrkFlowNodList = workflowNode.getChildNodes();
							for (int j = 0; j < wrkFlowNodList.getLength(); j++) {
								stageNode = wrkFlowNodList.item(j);
								if (stageNode.getNodeType() == Node.ELEMENT_NODE
										&& stageNode.getNodeName().equals("Stage")) {
									Element stageEle = (Element) stageNode;
									String stagename = stageEle.getAttribute("Stage_Name");
									String stage_No = stageEle.getAttribute("Stage_No");
									int stgNumr = Integer.valueOf(stage_No);
									if ((!stagename.equals("Admin") && !stagename.equals("public"))
											&& currstagNumbr == stgNumr) {
										NodeList stageNdList = stageNode.getChildNodes();
										Node statusCodeNod = null;
										String final_level = "";
										String final_stage = "";
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
														final_stage = statusEle.getTextContent();
														System.out.println(final_stage + "<======final_level==111===>"
																+ final_level);
														if (final_stage.trim().equals("Rejected")) {
															rejectFlag = true;
															break;
														}

													}
												}
												break;
											}
										}
										break;
									}
								}
							}
						}
					}
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			rejectFlag = false;
		}
		return rejectFlag;
	}

	public static String d_ID = "";

	public static Hashtable<String, String> saveSignatureAudit(String templateName, String customerKey, String jsonStr)
			throws Exception {
		Hashtable<String, String> detailsHT = new Hashtable<>();
		System.out.println("SAVE SIGNATURE AUDIT FUNCTION" + templateName + "::::" + jsonStr);

		JSONObject jObj1 = (JSONObject) new JSONParser().parse(jsonStr);
		String id = jObj1.get("id") == null ? "" : jObj1.get("id").toString();
		String ip = jObj1.get("ip") == null ? "" : jObj1.get("ip").toString();
		String os = jObj1.get("os") == null ? "" : jObj1.get("os").toString();
		String esignid = jObj1.get("esignid") == null ? "" : jObj1.get("esignid").toString();
		String timezone = jObj1.get("timezone") == null ? "" : jObj1.get("timezone").toString();
		System.out.println("from jsonstring-=-=-=-=-=-=-=-=-=-=-=-=-=" + ip);
		System.out.println("inside saveSignatureAudit");

		String url = "http://api.ipstack.com/" + ip + "?access_key=d00ec5f2835f647f22d14e2da48c1edf&output=json";

		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		// optional default is GET
		con.setRequestMethod("GET");
		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'GET' request to URL : " + url);
		System.out.println("Response Code : " + responseCode);
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		// print in String
		System.out.println(response.toString());
		// Read JSON response and print
		JSONObject jObj = (JSONObject) new JSONParser().parse(response.toString());
		String iip = jObj.get("ip") == null ? "" : jObj.get("ip").toString();
		String country = jObj.get("country_name") == null ? "" : jObj.get("country_name").toString();
		String region = jObj.get("region_name") == null ? "" : jObj.get("region_name").toString();
		String city = jObj.get("city") == null ? "" : jObj.get("city").toString();
		String zip = jObj.get("zip") == null ? "" : jObj.get("zip").toString();
		System.out.println("values from ipstack-==-=-=-=-=- ip:" + iip + "country:" + country + "city:" + city
				+ "region:" + region + "zip:" + zip);
		System.out.println("values to strore in xml-==-=-=-=-=- ip:" + iip + " city:" + city + " region:" + region
				+ " country:" + country + " zip:" + zip + " Time Zone:" + timezone + " OS:" + os);

		PropUtil prop = new PropUtil();
		String iisConfigFilePath = prop.getProperty("IISCONFIGFILEPATH");
		String templateFileName = prop.getProperty("IISTEMPLATEFILENAME");
		String iisAdminFilePath = prop.getProperty("IISUSERADMINFILEPATH");
		String iisAdminFileName = prop.getProperty("IISUSERADMINFILENAME");
		Document iisAdminDoc = Globals.openXMLFile(iisAdminFilePath, iisAdminFileName);
		Node adminNd = Globals.getNodeByAttrVal(iisAdminDoc, "Admin", "CustomerKey", customerKey);

		if (adminNd == null) {
			new Exception("Could not find the customer.");
		}
		Element adminEle = (Element) adminNd;
		String compName = adminEle.getAttribute("CompanyName");
		iisConfigFilePath = iisConfigFilePath + compName + "_" + customerKey + "\\" + "User_Templates_Config\\";
		Document doc = Globals.openXMLFile(iisConfigFilePath, templateFileName);

		Node templateNd1 = Globals.getNodeByAttrVal(doc, "Template", "Name", templateName);
		if (templateNd1 == null) {
			new Exception("Could not find the template.");
		}
		Element templateEle1 = (Element) templateNd1;
		Element esignEle = templateEle1.getElementsByTagName("ESign_Details").getLength() <= 0 ? null
				: (Element) templateEle1.getElementsByTagName("ESign_Details").item(0);
		if (esignEle != null) {
			String OutputFilePath = esignEle.getAttribute("OutputFilePath");
			Node esignNode = (Node) esignEle;

			// Esign Audit
			Node templateNd = Globals.getNodeByAttrVal(doc, "ESign_Details", "OutputFilePath", OutputFilePath);

			if (templateNd == null) {
				new Exception("Could not find the template.");
			}
			Element templateEle = (Element) templateNd;
			Element esignauditEle = templateEle.getElementsByTagName("ESignAudit_Details").getLength() <= 0 ? null
					: (Element) templateEle.getElementsByTagName("ESignAudit_Details").item(0);
			// if(esignauditEle == null) {

			esignauditEle = doc.createElement("ESignAudit_Details");
			esignauditEle.setAttribute("IP", iip);
			esignauditEle.setAttribute("Country_Name", country);
			esignauditEle.setAttribute("Region_Name", region);
			esignauditEle.setAttribute("City", city);
			esignauditEle.setAttribute("ZIP", zip);
			esignauditEle.setAttribute("OS", os);
			esignauditEle.setAttribute("esignid", esignid);
			esignauditEle.setAttribute("TimeZone", timezone);
			templateEle.appendChild(esignauditEle);
			/*
			 * } else { esignauditEle = doc.createElement("ESignAudit_Details");
			 * esignauditEle.setAttribute("IP", iip);
			 * esignauditEle.setAttribute("Country_Name", country);
			 * esignauditEle.setAttribute("Region_Name", region);
			 * esignauditEle.setAttribute("City", city); esignauditEle.setAttribute("ZIP",
			 * zip); esignauditEle.setAttribute("OS", os);
			 * esignauditEle.setAttribute("TimeZone", timezone);
			 * esignauditEle.setAttribute("esignid", esignid);
			 * 
			 * }
			 */
			System.out.println("signed------------------------------------------------------------");
			Globals.writeXMLFile(doc, iisConfigFilePath, templateFileName);

			PropUtil prop1 = new PropUtil();
			String HIERARCHY_XML_DIR = prop1.getProperty("HIERARCHY_XML_DIR");
			String docXmlFileName = prop1.getProperty("DOCUMENT_XML_FILE");
			// String wfDocId = templateEle.getAttribute("Workflow_Document_ID");
			Document docXmlDoc = Globals.openXMLFile(HIERARCHY_XML_DIR, docXmlFileName);
			String DateFormat = prop1.getProperty("DATE_FORMAT");
			Date lastAccDate = new Date();
			Format formatter = new SimpleDateFormat(DateFormat);
			String accdate = formatter.format(lastAccDate);
			////////////////////////
			Node docNd = Globals.getNodeByAttrVal(docXmlDoc, "Document", "Document_ID", d_ID);
			Element docEle = (Element) docNd;

			String s_name = "NA";

			NodeList workflowNdList = docEle.getElementsByTagName("Workflow");
			for (int temp = 0; temp < workflowNdList.getLength(); temp++) {
				Node nNode = workflowNdList.item(temp);
				Element wor_Ele = (Element) nNode;
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					s_name = wor_Ele.getAttribute("Current_Stage_Name");
				}
			}

			/////////////////////////
			// WorkflowManager.addLog(docXmlDoc,d_ID , "Member E-Signed",id , accdate,
			// iip+"~"+city+"~"+region+"~"+country+"~"+zip+"~"+timezone+"~"+os,"NA");
			WorkflowManager.addLog(docXmlDoc, d_ID, "Member E-Signed", id, accdate,
					iip + "~" + city + "~" + region + "~" + country + "~" + zip + "~" + timezone + "~" + os, "NA",
					s_name);
			Globals.writeXMLFile(docXmlDoc, HIERARCHY_XML_DIR, docXmlFileName);
			// Esign Audit

		}
		detailsHT.put("Status", "Success");
		detailsHT.put("StatusDetails", "Success");
		return detailsHT;
	}

	public static Hashtable<String, String> saveSignatureConsent(String docId, String flag, String emailId)
			throws Exception {
		Hashtable<String, String> detailsHT = new Hashtable<>();
		PropUtil prop = new PropUtil();
		System.out.println("Flagcame from C#====>" + flag + "<====docID=" + docId + "=====emailId==>" + emailId);
		String hierDir = prop.getProperty("HIERARCHY_XML_DIR");
		String docXmlFileName = prop.getProperty("DOCUMENT_XML_FILE");
		Document docXmlDoc = Globals.openXMLFile(hierDir, docXmlFileName);
		Element docEle = (Element) Globals.getNodeByAttrVal(docXmlDoc, "Document", "Document_ID", docId);
		if (docEle != null) {
			String primaryFileName = docEle.getAttribute("Primary_FileName");
			Element wfEle = (Element) docEle.getElementsByTagName("Workflow").item(0);
			String currStageNo = wfEle.getAttribute("Current_Stage_No");
			String currUser = "";
			String consentFlag = "false";// $$ConsentFlag$$
			String finalMsg = "";
			if (!currStageNo.equalsIgnoreCase("Completed") && !currStageNo.equalsIgnoreCase("Cancel")) {
				Element docEle1 = (Element) Globals.getNodeByAttrValUnderParent(docXmlDoc, wfEle, "Stage_No",
						currStageNo);
				if (docEle1 != null) {
					Element empListEle = (Element) docEle1.getElementsByTagName("Employee_Names").item(0);
					System.out.println("before empchildnodes " + empListEle);

					NodeList empChildNods = empListEle.getChildNodes();
					for (int j = 0; j < empChildNods.getLength(); j++) {
						if (empChildNods.item(j).getNodeType() == Node.ELEMENT_NODE) {
							Element userEle = (Element) empChildNods.item(j);

							String message = userEle.getAttribute("E-mail");
							System.out.println("message" + message);

							if (message.equalsIgnoreCase(emailId)) {
								System.out
										.println("Values in for loop setup=====>" + message + "<=====flag====>" + flag);
								userEle.setAttribute("ConsentFlag", flag);
								break;
							}

						}

					}
				}

			}
			Globals.writeXMLFile(docXmlDoc, hierDir, docXmlFileName);
		}
		detailsHT.put("Status", "Success");
		detailsHT.put("StatusDetails", "Success");
		return detailsHT;
	}

	public static Hashtable getRepositoryURL(String accelConfigDir, String accelConfigFileName, String type) {
		Document doc = Globals.openXMLFile(accelConfigDir, accelConfigFileName);

		Element repoEle = (Element) doc.getElementsByTagName("RepositoryManagement").item(0);
		if (repoEle != null) {
			Element ele = (Element) repoEle.getElementsByTagName(type).item(0);
			if (ele != null) {
				Hashtable ht = Globals.getAttributeNameandValHT(ele);
				ht.put("InnerText", ele.getTextContent());
				return ht;
			} else
				return null;
		} else {
			return null;
		}
	}
	
	public static boolean checkEsignConfigured4User(String metadataFilePath, ArrayList<String> userAL, String stageNo) throws Exception {
		boolean flag = false;
		File metaFile = new File(metadataFilePath);
		if(metaFile.exists()) {
			BufferedReader buffR = new BufferedReader(new FileReader(metaFile));
			StringBuilder sb = new StringBuilder();
			String line = "";
			while((line = buffR.readLine()) != null) {
				sb.append(line);
			}
			System.out.println("-=-=sb-=-=-=sb-=-=sb-="+sb.toString());
			org.json.simple.JSONArray jObj = (org.json.simple.JSONArray) new JSONParser().parse(sb.toString());
			for (int i = 0; i < jObj.size(); i++) {
				JSONObject obj = (JSONObject) jObj.get(i);
				org.json.simple.JSONArray arr = (org.json.simple.JSONArray) obj.get("values");
				for (int j = 0; j < arr.size(); j++) {
					JSONObject obj1 = (JSONObject) arr.get(j);
					String userId = obj1.get("userid") == null ? "" : obj1.get("userid").toString();
					String stgNo = obj1.get("stageno") == null ? "" : obj1.get("stageno").toString();
					System.out.println(obj1.get("userid").toString()+"---------------checkesignCf-----dsfsdfdsf---------- "+userAL);
					if(stgNo.equalsIgnoreCase("All") || (userId.equalsIgnoreCase("All") && stgNo.equalsIgnoreCase(stageNo)) || userAL.contains(userId)) {
						flag = true;
						break;
					}
				}
			}

		}	
		return flag;
	}
	
	public static String updateEsignMetadataWithDocumentID(String metadata, Element templateEle) throws ParseException {
		Hashtable<String, Hashtable<String, String>> filePageIndexHT = new Hashtable<String, Hashtable<String, String>>();
		NodeList collationNdList = templateEle.getElementsByTagName("CollationTemplates");
		for(int i = 0; i < collationNdList.getLength(); i++) {
			if(collationNdList.item(i).getNodeType() == Element.ELEMENT_NODE) {
				NodeList fileNdList = collationNdList.item(i).getChildNodes();
				for(int j = 0; j < fileNdList.getLength(); j++) {
					if(fileNdList.item(j).getNodeType() == Element.ELEMENT_NODE) {
						Element fileEle = (Element)fileNdList.item(j);
						String docId = fileEle.getAttribute("UniqueNo");
						String docName = fileEle.getAttribute("FileName");
						String outputIndex = fileEle.getAttribute("OutputPageIndex");
						Hashtable<String, String> detailsHT = new Hashtable<>();
						detailsHT.put("FileName", docName);
						detailsHT.put("OutputPageIndex", outputIndex);
						if(!outputIndex.trim().isEmpty())
							filePageIndexHT.put(docId, detailsHT);
					}
				}
			}
		}
		org.json.simple.JSONArray jObj = (org.json.simple.JSONArray) new JSONParser().parse(metadata);
		for (int i = 0; i < jObj.size(); i++) {
			JSONObject obj = (JSONObject) jObj.get(i);
			Long pageNumberStr = (Long)obj.get("pagenumber");
			if(pageNumberStr == null) {
				jObj.remove(i);
				continue;
			}
			Hashtable<String, String> fileDocIdAndPageNumberHT = getDocIdByPageNumber(pageNumberStr, filePageIndexHT);
			if(fileDocIdAndPageNumberHT == null)
				continue;
			String docId = fileDocIdAndPageNumberHT.get("DocId");
			String docName = fileDocIdAndPageNumberHT.get("DocName");
			String docPageNumber = fileDocIdAndPageNumberHT.get("PageNumber");
			obj.put("DocumentId", docId);
			obj.put("DocumentName", docName);
			obj.put("DocumentPageNumber", docPageNumber);
		}
		return jObj.toJSONString();
	}
	private static Hashtable<String, String> getDocIdByPageNumber(Long pageNumber, Hashtable<String, Hashtable<String, String>> filePageIndexHT) {
		
		Hashtable<String, String> fileDocIdAndPageNumberHT = new Hashtable<String, String>();
		if(pageNumber == 0)
			return null;
		Iterator<Entry<String, Hashtable<String, String>>> it = filePageIndexHT.entrySet().iterator();
		while(it.hasNext()) {
			Entry<String, Hashtable<String, String>> ent = it.next();
			String fileDocId = ent.getKey();
			Hashtable<String, String> detailsHT = ent.getValue();
			String docName = detailsHT.get("FileName");
			String pageIndex = detailsHT.get("OutputPageIndex");
			if(pageIndex.contains("-")) {
				String startingNumberStr = pageIndex.split("-")[0];
				String endingNumberStr = pageIndex.split("-")[1];
				int startingNumber = Globals.checkStringIsNumber(startingNumberStr) ? Integer.valueOf(startingNumberStr) : 0;
				int endingNumber = Globals.checkStringIsNumber(endingNumberStr) ? Integer.valueOf(endingNumberStr) : 0;
				if(startingNumber <= pageNumber && endingNumber >= pageNumber) {
					fileDocIdAndPageNumberHT.put("DocId", fileDocId);
					fileDocIdAndPageNumberHT.put("DocName", docName);
					fileDocIdAndPageNumberHT.put("PageNumber", String.valueOf((pageNumber - startingNumber)+1));
					break;
				}
			}
		}
		if(fileDocIdAndPageNumberHT.size() <= 0)
			fileDocIdAndPageNumberHT = null;
		return fileDocIdAndPageNumberHT;
	}
}
