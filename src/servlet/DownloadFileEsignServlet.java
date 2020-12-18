package servlet;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Hashtable;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import managers.FormsManager;
import managers.LoginProcessManager;
import managers.WorkflowManager;
import utils.Globals;
import utils.PropUtil;

/**
 * Servlet implementation class DownloadFileEsignServlet
 */
public class DownloadFileEsignServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DownloadFileEsignServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		try {
			PropUtil prop = new PropUtil();
			String hierDir=prop.getProperty("HIERARCHY_XML_DIR");
			String docXmlFileName = prop.getProperty("DOCUMENT_XML_FILE");
			String iisConfigFilePath = prop.getProperty("IISCONFIGFILEPATH");
			String templateFileName = prop.getProperty("IISTEMPLATEFILENAME");
			String iisAdminFilePath = prop.getProperty("IISUSERADMINFILEPATH");
			String iisAdminFileName = prop.getProperty("IISUSERADMINFILENAME");
			String iisTempFilesPath = prop.getProperty("IISTEMPFILESPATH");
			String custKey = request.getParameter("key");
			String templateName = request.getParameter("name");
			
			Document iisAdminDoc = Globals.openXMLFile(iisAdminFilePath, iisAdminFileName);
			Node adminNd = Globals.getNodeByAttrVal(iisAdminDoc, "Admin", "CustomerKey", custKey);
			if(adminNd == null) {
				throw new Exception("Could not find the customer.");
			}
			Element adminEle = (Element) adminNd;
			String compName = adminEle.getAttribute("CompanyName");
			iisConfigFilePath = iisConfigFilePath+compName+"_"+custKey+"\\"+"User_Templates_Config\\";
			iisTempFilesPath = iisTempFilesPath+compName+"_"+custKey+"\\";
			
			Document doc = Globals.openXMLFile(iisConfigFilePath, templateFileName);
			Node formNd = Globals.getNodeByAttrVal(doc, "Template", "Name", templateName);
			if(formNd == null)
				new Exception("Could not find the template.");
			Element templateEle = (Element) formNd;
			String docId = templateEle.getAttribute("Workflow_Document_ID");
			
			Hashtable tempHT = FormsManager.getTemplateNdFromDocId(docId);
			Node templateNd = tempHT == null ? null : (Node)tempHT.get("TemplateNode");
			if(templateNd == null) {
				new Exception("Could not find the template.");
			}
			boolean enableEsignFlag = false;
			Document doc1 = Globals.openXMLFile(hierDir, docXmlFileName);
			Element docEle = (Element)Globals.getNodeByAttrVal(doc1, "Document", "Document_ID", docId);
			Element wfNd = (Element)docEle.getElementsByTagName("Workflow").item(0);
			LoginProcessManager lpm = new LoginProcessManager();
			String totalStgNd = wfNd.getAttribute("Total_No_Stages");
			String docName = docEle.getAttribute("DocumentName");
			String currStgNoStr = wfNd.getAttribute("Current_Stage_No");
//			if(!currStgNoStr.equalsIgnoreCase("Completed") && !currStgNoStr.equalsIgnoreCase("Cancel") && !currStgNoStr.equalsIgnoreCase("Rules Failed")) {
//				int currStgNo = Integer.valueOf(currStgNoStr);
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
//			}
			Hashtable<String, String> detailsHT = FormsManager.downloadPDFWithEsignDetails(docId, enableEsignFlag);
			if(String.valueOf(detailsHT.get("Status")).equalsIgnoreCase("success")) {

				String pdfFilePath = String.valueOf(detailsHT.get("FileChanged")).equalsIgnoreCase("true") ? String.valueOf(detailsHT.get("NewFileName")) : "";
				String fileName = pdfFilePath.substring(pdfFilePath.lastIndexOf("\\"));
				PrintWriter out = response.getWriter();  
				//			String filename = "Finance.docx";   
				//			String filepath = "E:\\Document WF WS\\Document WF\\DocumentVersion\\Finance_51\\3\\";   
				//response.setContentType("APPLICATION/OCTET-STREAM");   
				System.out.println("-=-=-=-=-dfgfgfdg=-=-=-=-="+fileName);
				String fileNameTemp = fileName;
				String content = Globals.getContentType4File(fileNameTemp.substring(fileNameTemp.lastIndexOf(".")+1));
				response.setContentType(content.trim().isEmpty() ? "text/html" : content);  
				//if(downloadFrom.equalsIgnoreCase("mail"))
				//fileNameTemp = fileName.substring(0, fileName.lastIndexOf("."))+"#"+docId+"#"+downloadFrom+fileName.substring(fileName.lastIndexOf("."));
				response.setHeader("Content-Disposition","attachment; filename=\"" + fileNameTemp + "\"");   
//				if()
				FileInputStream fileInputStream = new FileInputStream(pdfFilePath);  

				int it;   
				while ((it=fileInputStream.read()) != -1) {  
					out.write(it);   
				}   
				fileInputStream.close();   
				out.close();  
			}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
