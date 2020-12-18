package servlet;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Random;
import java.util.stream.Stream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.quartz.Job;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.itextpdf.text.log.SysoCounter;

import beans.RandomString;
import javassist.bytecode.annotation.EnumMemberValue;
import utils.Globals;
import utils.PropUtil;

/**
 * Servlet implementation class SaveFormDetails
 */
@WebServlet(name = "SaveFormDetails", urlPatterns = {"/saveform"})
public class SaveFormDetails extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SaveFormDetails() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
		try {
			JSONObject jObj = new JSONObject();
			String formId = request.getParameter("FormId");
			String customerKey = request.getParameter("CustomerKey");
			String mode = request.getParameter("mode") == null || request.getParameter("mode").trim().isEmpty() ? "new" : request.getParameter("mode");
			String bpname=request.getParameter("templatename") == null || request.getParameter("templatename").trim().isEmpty() ? "" : request.getParameter("templatename");
			
			
			PropUtil prop = new PropUtil();
			String formsDataFolder = prop.getProperty("FORMSDATA_FOLDER");
			String iisAdminFilePath = prop.getProperty("IISUSERADMINFILEPATH");
			String iisAdminFileName = prop.getProperty("IISUSERADMINFILENAME");
			String iisImagefilepath = prop.getProperty("IISESIGNIMAGEPATH");
			Document iisAdminDoc = Globals.openXMLFile(iisAdminFilePath, iisAdminFileName);
			Node adminNd = Globals.getNodeByAttrVal(iisAdminDoc, "Admin", "CustomerKey", customerKey);
			if(adminNd == null) {
				response.getWriter().append("Could not find the customer.").append(request.getContextPath());
				return;
			}
			Element adminEle = (Element) adminNd;
			String compName = adminEle.getAttribute("CompanyName");
			
			
			
			System.out.println("-===================-=-=-=-=-=-=-=.....................................-=-=-=-=-=-=-=-========================"+bpname);
			Enumeration<String> en = request.getParameterNames();
			while (en.hasMoreElements()) {
				String paraName = (String) en.nextElement();				
				String[] paraVals = request.getParameterValues(paraName);
				if(paraVals.length == 1) {
					System.out.println(paraName+"=====first val======="+paraVals[0]);
					if(paraVals[0].endsWith(".png")||paraVals[0].endsWith(".jpg")||paraVals[0].endsWith(".jpeg")) {
						String jimgval="";
						jimgval=iisImagefilepath+compName+"_"+customerKey+"\\";
						jimgval=jimgval+formId+"_ imgfileup"+paraName+"\\"+paraVals[0];
						jObj.put(paraName, jimgval);
					}
					else {
					jObj.put(paraName, paraVals[0]);
					}
				}else {
					JSONArray jArr = new JSONArray();
					for(int i=0;i<paraVals.length;i++) {
						jArr.add(paraVals[i]);
					}
					System.out.println(paraName+"=====arrayval======="+jArr);
					jObj.put(paraName, jArr);
				}
			}
			
			
			formsDataFolder = formsDataFolder+compName+"_"+customerKey+"\\";
			File formDataDir = new File(formsDataFolder+formId);
			if(!formDataDir.exists()) {
				boolean flag = formDataDir.mkdirs();
				if(!flag) {
					response.getWriter().append("Data not saved").append(request.getContextPath());
					return;
				}
					
			}
				
			Random rand = new Random();
			String fileNameWithPath = formsDataFolder+formId+"\\";
			File jsonFile = null;
			long count;
			String Uniqueid="";
			if(mode.equalsIgnoreCase("new")) {				
				
				try (Stream<Path> files = Files.list(Paths.get(fileNameWithPath))) {
					count = files.count();
					count=count+1;
				}		
				jObj.put("Uniqueid", String.valueOf(count));				
											
				while(true) {
					int uniqueNo = rand.nextInt((9999999 - 1) + 1) + 1;
					fileNameWithPath = fileNameWithPath+"Data_"+uniqueNo+".json";					
					jsonFile = new File(fileNameWithPath);
					if(!jsonFile.exists())
						break;
				}
			}else {				
				try (Stream<Path> files = Files.list(Paths.get(fileNameWithPath))) {
					count = files.count();
					count=count+1;
				}
				fileNameWithPath = fileNameWithPath+mode+".json";
				jsonFile = new File(fileNameWithPath);
				if(jsonFile.exists()) {					
					FileReader reader = new FileReader(jsonFile);
					JSONObject jObj1 = (JSONObject) new JSONParser().parse(reader);					
					
					for(Iterator iterator = jObj1.keySet().iterator(); iterator.hasNext();) {
					    String key = (String) iterator.next();
					    /*System.out.println(key+"<< [Key] -- [Value] >>"+jObj1.get(key));*/
					    if(jObj1.get(key).toString().contains(".jpg")||jObj1.get(key).toString().contains(".jpeg")||jObj1.get(key).toString().contains(".png")) {
					    	if((jObj.get(key)).toString().trim().equals("")) {
					    		jObj.put(key,jObj1.get(key));
					    	}					    						    						    							    	
					    }
					}
					
					Uniqueid = jObj1.get("Uniqueid") == null ? "" : jObj1.get("Uniqueid").toString();						
					System.out.println("=================uniqidval======"+Uniqueid);
										
					if(Uniqueid.trim().equals("")) {								
						jObj.put("Uniqueid",String.valueOf(count));
					}	
					else {
						jObj.put("Uniqueid", Uniqueid);
					}
					
					System.out.println("-==before delete on edit====jObj.toJSONString()=========="+jObj.toJSONString());
					jsonFile.delete();
				}
			}
			System.out.println(count+":filecount::-=-=-=-=-fileNameWithPath=-=-=-=-=-="+fileNameWithPath);
			
			System.out.println("-======jObj.toJSONString()=========="+jObj.toJSONString());
			BufferedWriter buffWriter = new BufferedWriter(new FileWriter(jsonFile));
			buffWriter.write(jObj.toJSONString());
			buffWriter.flush();
			buffWriter.close();
			//response.getWriter().append("Data saved successfully. You can close this tab.");
			String configFileName=prop.getProperty("HIERARCHY_CONFIG_FILE");
			String hierLeveldir=prop.getProperty("HIERARCHY_XML_DIR");
			Document doc=Globals.openXMLFile(hierLeveldir, configFileName);
			Element redirectEle = (Element)doc.getElementsByTagName("FormSubmit_Link").item(0);
			String redirectURL = redirectEle.getAttribute("URL")==null || redirectEle.getAttribute("URL").trim().isEmpty() ? "https://www.ultradocuments.com/businessplanner/successmsg.aspx" : redirectEle.getAttribute("URL");
			redirectURL=redirectURL+"?"+count;
			response.sendRedirect(redirectURL);
//			Document doc = Globals.openXMLFile(xmlDir, formsXMLFileName);
//			Node formNd = Globals.getNodeByAttrVal(doc, "Form", "FormId", formId);
//			Element ele = (Element)formNd;
//			String opFileName = ele.getAttribute("OutputJsonFilePath");
//			JSONObject wholeObj = null;
//			if(opFileName != null && !opFileName.isEmpty()) {
//				File opJsonFile = new File(opFileName);
//				if(opJsonFile.exists()) {
//					BufferedReader buffreader = new BufferedReader(new FileReader(opFileName));
//					StringBuilder sb =new StringBuilder(); 
//					String line = "";
//					while((line = buffreader.readLine()) != null) {
//						sb.append(line);
//					}
//					buffreader.close();
//					JSONParser parser = new JSONParser();
//					wholeObj = (JSONObject) parser.parse(sb.toString());
//					JSONArray itemsA = (JSONArray)wholeObj.get("Items");
//					itemsA.add(jObj);
//				}else {
//					wholeObj = new JSONObject();
//					JSONArray itemsA = new JSONArray();
//					itemsA.add(jObj);
//					wholeObj.put("Items", itemsA);
//					wholeObj.put("FormID", formId);
//					opFileName = formsDataFolder+"Data_"+formId+".json";
//				}
//			}else {
//				wholeObj = new JSONObject();
//				JSONArray itemsA = new JSONArray();
//				itemsA.add(jObj);
//				wholeObj.put("Items", itemsA);
//				wholeObj.put("FormID", formId);
//				opFileName = formsDataFolder+"Data_"+formId+".json";
//			}
			
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

}
