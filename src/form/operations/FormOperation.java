package form.operations;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import managers.FormsManager;
import managers.LoginProcessManager;
import utils.Globals;
import utils.PropUtil;
import workflow.operations.OperationService;

@Path("/form")
public class FormOperation {
	FormService formService;
	
	public FormOperation() {
		formService = new FormService();
	}
	
	@Path("/addform")
	@POST
	@Consumes({MediaType.APPLICATION_FORM_URLENCODED, MediaType.APPLICATION_JSON})
	@Produces(MediaType.APPLICATION_JSON)
	public Response addForm(@QueryParam("formid") String formId, @QueryParam("folderid") String folderId, String jsonStr) {
		String message = "Success";
		message = formService.updateForm(formId, folderId, jsonStr);
		
		return Response.ok(message).header("Access-Control-Allow-Origin", "*").
				header("Content-Type", "application/json").build();
	}
	
	@Path("/addform")
	@OPTIONS
	@Consumes()
	public Response addForm() {
		System.out.println("-=-=-=-=-addForm=-=-=-=-=");
		return Response.status(200).header("Access-Control-Allow-Origin", "*").
				header("Access-Control-Allow-Methods", "POST").
				header("Access-Control-Allow-Headers", "Content-Type").
				header("Content-Type", "application/json").build();
	}
	
	@Path("/addesignform")
	@POST
	@Consumes({MediaType.APPLICATION_FORM_URLENCODED, MediaType.APPLICATION_JSON})
	@Produces(MediaType.APPLICATION_JSON)
	public Response addEsignForm(@QueryParam("templatename") String templateName, @QueryParam("key") String customerKey, @QueryParam("id") String createdBy, @QueryParam("saveas") String saveasFlag, @QueryParam("newtemplate") String newTemplateName, String jsonStr) {
		String message = "Success";
		System.out.println(customerKey+"-=-=-=-customerKey=-=-=-=-="+templateName+"<====saveasFlag===>"+saveasFlag+"<====newTemplateName===>"+newTemplateName);
		message = formService.updateEsignForm(templateName, jsonStr, customerKey, createdBy, saveasFlag, newTemplateName);
		
		return Response.ok(message).header("Access-Control-Allow-Origin", "*").
				header("Content-Type", "application/json").build();
	}
	
	@Path("/addesignform")
	@OPTIONS
	@Consumes()
	public Response addEsignForm() {
		System.out.println("-=-=-=-=-addForm=-=-=-=-=");
		return Response.status(200).header("Access-Control-Allow-Origin", "*").
				header("Access-Control-Allow-Methods", "POST").
				header("Access-Control-Allow-Headers", "Content-Type").
				header("Content-Type", "application/json").build();
	}
	
	@Path("/getform")
	@GET
	@Consumes({MediaType.APPLICATION_FORM_URLENCODED})
	@Produces(MediaType.APPLICATION_JSON)
	public Response getForm(@QueryParam("formid") String formId, @QueryParam("customerkey") String customerKey) {
		String formDetails = "";
		formDetails = formService.getFormMetadata(formId, customerKey);
		System.out.println("-=-=-=-=-formDetails=-=-=-=-="+formDetails);
		return Response.ok(formDetails).header("Access-Control-Allow-Origin", "*").
				header("Content-Type", "application/json").build();
	}
	
	@Path("/getform")
	@OPTIONS
	public Response getForm() {
		System.out.println("-=-=-=-=-formDetails=-=-=-=-=");
		return Response.status(200).header("Access-Control-Allow-Origin", "*").
				header("Access-Control-Allow-Methods", "GET").build();
	}
	
	@Path("/getesignform")
	@GET
	@Consumes({MediaType.APPLICATION_FORM_URLENCODED})
	@Produces(MediaType.APPLICATION_JSON)
	public Response getEsignForm(@QueryParam("templatename") String templateName, @QueryParam("customerkey") String customerKey) {
		String formDetails = "";
		formDetails = formService.getEsignFormMetadata(templateName, customerKey);
		System.out.println("-=-=-=-=-formDetails=-=-=-=-="+formDetails);
		return Response.ok(formDetails).header("Access-Control-Allow-Origin", "*").
				header("Content-Type", "application/json").build();
	}
	
	@Path("/getesignform")
	@OPTIONS
	public Response getEsignForm() {
		System.out.println("-=-=-=-=-formDetails=-=-=-=-=");
		return Response.status(200).header("Access-Control-Allow-Origin", "*").
				header("Access-Control-Allow-Methods", "GET").build();
	}
	
	@Path("/getformdata")
	@GET
	@Consumes({MediaType.APPLICATION_FORM_URLENCODED})
	@Produces(MediaType.APPLICATION_JSON)
	public Response getFormData(@QueryParam("formid") String formId, @QueryParam("customerkey") String customerKey, @QueryParam("dataname") String dataFileName) {
		String formDetails = "";
		formDetails = formService.getFormData(formId, customerKey, dataFileName);
		System.out.println("-=-=-=-=-formDetails=-=-=-=-="+formDetails);
		return Response.ok(formDetails).header("Access-Control-Allow-Origin", "*").
				header("Content-Type", "application/json").build();
	}
	
	@Path("{custkey}/{formid}")
	@GET
	@Consumes({MediaType.APPLICATION_FORM_URLENCODED})
	@Produces(MediaType.TEXT_HTML)
	public String opForm(@PathParam("custkey") String custKey, @PathParam("formid") String formId, @QueryParam("dataname") String dataFilename,@QueryParam("type") String typ,@QueryParam("templatename") String template,@QueryParam("uname") String uname) {		
		String formDetails = "";
		try {			
			String type = typ==null?"":typ.toString();
			String tempname=template==null?"":template.toString();
			String usrname=uname==null?"":uname.toString();
			System.out.println(formId+"-=-=-=-=-form view type=-=-=-=-="+typ);
			String htmlFilePath = "";
			PropUtil prop = new PropUtil();
			String iisAdminFilePath = prop.getProperty("IISUSERADMINFILEPATH");
			String iisAdminFileName = prop.getProperty("IISUSERADMINFILENAME");
			String iisConfigFilePath = prop.getProperty("IISCONFIGFILEPATH");
			String formsXMLFileName = prop.getProperty("IISFORMCONFIGFILENAME");
			Document iisAdminDoc = Globals.openXMLFile(iisAdminFilePath, iisAdminFileName);
			Node adminNd = Globals.getNodeByAttrVal(iisAdminDoc, "Admin", "CustomerKey", custKey);
			if(adminNd == null) {
				new Exception("Could not find the customer.");
			}
			Element adminEle = (Element) adminNd;
			String compName = adminEle.getAttribute("CompanyName");
			iisConfigFilePath = iisConfigFilePath+compName+"_"+custKey+"\\"+"User_Templates_Config\\";
			Document doc = Globals.openXMLFile(iisConfigFilePath, formsXMLFileName);
			Node formsListNd = doc.getElementsByTagName("FormsList").item(0);
			
			Node formNd = null;
			ArrayList<Node> ndAL = new ArrayList<>();
			FormsManager.getNodeByID(formsListNd, formId, ndAL);
			if(ndAL.size() > 0)
				formNd = ndAL.get(0);
			if(formNd != null) {
				Element ele = (Element)formNd;
				htmlFilePath = ele.getAttribute("OutputFilePath");
				BufferedReader reader = new BufferedReader(new FileReader(new File(htmlFilePath)));
				String line = "";
				StringBuilder sb = new StringBuilder();
				while((line = reader.readLine()) != null) {
					sb.append(line);
				}
				formId = formId == null || formId.trim().isEmpty() ? "" : formId;
				custKey = custKey == null || custKey.trim().isEmpty() ? "" : custKey;
				dataFilename = dataFilename == null || dataFilename.trim().isEmpty() ? "" : dataFilename;
				
				if(type.trim().equals("")) {
				formDetails = sb.toString().replace("$$FormID$$", formId).replace("$$CustKey$$", custKey).replace("$$DataFileName$$", dataFilename).replace("button disabled","button enabled");
				}				
				else {
					formDetails = sb.toString().replace("$$FormID$$", formId).replace("$$CustKey$$", custKey).replace("$$DataFileName$$", dataFilename).replace("button enabled","button disabled");
				}				
				if(formDetails.toString().contains("/SaveFormDetails")) {
					System.out.println(tempname+"-=gggg-=-=-=-=-formDetails=contains-=-=-=-="+formDetails);
					formDetails=formDetails.toString().replace("/SaveFormDetails", "/SaveFormDetails?templatename="+tempname+"&uname="+usrname);
				}
				else {					
					System.out.println("-=gggg-=-=-=-=-formDetails=not contains-=-=-=-="+formDetails);
				}
				
				System.out.println("-=gggg-=-=-=-=-formDetails=-=-=-=-="+formDetails);
				reader.close();
			}
			
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return formDetails;
	}
	
	@Path("/updateadmin")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateAdminFile(String jsonStr) {
		String message = "Success";
		//message = formService.updateForm(jsonStr);
		message = formService.updateAdminFile(jsonStr);
		
		return Response.ok(message).header("Access-Control-Allow-Origin", "*").
				header("Content-Type", "application/json").build();
	}
	
	@Path("/generate")
	@POST
	@Consumes({MediaType.TEXT_HTML, MediaType.APPLICATION_FORM_URLENCODED})
	@Produces(MediaType.APPLICATION_JSON)
	public Response saveHTMLContent(@QueryParam("formid") String formId, @QueryParam("customerkey") String customerKey, String htmlText) {
		String message = "Success";
		//message = formService.updateForm(jsonStr);
		message = formService.saveHTMLContent(formId, customerKey, htmlText);
		
		return Response.ok(message).header("Access-Control-Allow-Origin", "*").
				header("Content-Type", "application/json").build();
	}
	@Path("/generate")
	@OPTIONS
	@Consumes()
	public Response saveHTMLContent() {
		System.out.println("-=-=-=-=-saveHTMLContent=-=-=options-=-=");
		return Response.status(200).header("Access-Control-Allow-Origin", "*").
				header("Access-Control-Allow-Methods", "POST").
				header("Access-Control-Allow-Headers", "Content-Type").
				header("Content-Type", "text/html").build();
	}
	
	@Path("/generateesign")
	@POST
	@Consumes({MediaType.TEXT_HTML, MediaType.APPLICATION_FORM_URLENCODED})
	@Produces(MediaType.APPLICATION_JSON)
	public Response saveEsignHTMLContent(@QueryParam("templatename") String templateName, @QueryParam("customerkey") String customerKey, @QueryParam("saveas") String esignflag, String htmlText) {
		String message = "Success";
		//message = formService.updateForm(jsonStr);
		message = formService.saveEsignHTMLContent(templateName, customerKey, htmlText,esignflag);
		
		return Response.ok(message).header("Access-Control-Allow-Origin", "*").
				header("Content-Type", "application/json").build();
	}
	@Path("/generateesign")
	@OPTIONS
	@Consumes()
	public Response saveEsignHTMLContent() {
		System.out.println("-=-=-=-=-saveHTMLContent=-=-=options-=-=");
		return Response.status(200).header("Access-Control-Allow-Origin", "*").
				header("Access-Control-Allow-Methods", "POST").
				header("Access-Control-Allow-Headers", "Content-Type").
				header("Content-Type", "text/html").build();
	}
	
//	http://localhost:8080/rbid/formoperation/form/esign/8i7pY/FORM10000?dataname=&id=vishnu@cygnussoftwares.com
	@Path("esign/{custkey}/{templatename}")
	@GET
	@Consumes({MediaType.APPLICATION_FORM_URLENCODED})
	@Produces(MediaType.TEXT_HTML)
	public String opEsignForm(@PathParam("custkey") String custKey, @PathParam("templatename") String templateName, @QueryParam("dataname") String dataFilename, @QueryParam("id") String id) {
		String formDetails = formService.loadEsignHTML(custKey, templateName, dataFilename, id);
		
		return formDetails;
	}
	
	@Path("/saveesigndata")
	@POST
	@Consumes({MediaType.APPLICATION_FORM_URLENCODED, MediaType.APPLICATION_JSON})
	@Produces(MediaType.APPLICATION_JSON)
	public Response saveEsignData(@QueryParam("templatename") String templateName, @QueryParam("key") String customerKey, @QueryParam("id") String id, 
			@QueryParam("notes") String notes, @QueryParam("action") String actionName, String jsonStr) {
		String message = "Success";
		System.out.println(customerKey+"-=-=-=-=-=-formDetails=-=-=-=-="+templateName);
		message = formService.saveEsignData(templateName, jsonStr, customerKey, id, notes, actionName);
		
		return Response.ok(message).header("Access-Control-Allow-Origin", "*").
				header("Content-Type", "application/json").build();
	}
	
	@Path("/saveesigndata")
	@OPTIONS
	@Consumes()
	public Response saveEsignData() {
		return Response.status(200).header("Access-Control-Allow-Origin", "*").
				header("Access-Control-Allow-Methods", "POST").
				header("Access-Control-Allow-Headers", "Content-Type").
				header("Content-Type", "application/json").build();
	}
	
	@Path("/savesignature")
	@POST
	@Consumes({MediaType.APPLICATION_FORM_URLENCODED, MediaType.TEXT_PLAIN})
	@Produces(MediaType.APPLICATION_JSON)
	public Response saveSignatureAsImg(@QueryParam("templatename") String templateName, @QueryParam("key") String customerKey, @QueryParam("fieldid") String fieldId, String svgData, @QueryParam("cform") String imgbse64,@QueryParam("fname") String fname) {
		String message = "Success";
		String cform = imgbse64==null ? "" : (String)imgbse64;
		System.out.println(customerKey+"-=-=-=-=-=-formDetails=-=-=-=-="+templateName);
		if(cform.equals("true")) {
			String filename=fname==null?"":(String)fname;
			message = formService.saveSignatureAsImgform(templateName, svgData, customerKey, fieldId,filename);
		}
		else {
		message = formService.saveSignatureAsImg(templateName, svgData, customerKey, fieldId);
		}
		
		return Response.ok(message).header("Access-Control-Allow-Origin", "*").
				header("Content-Type", "application/json").build();
	}
	
	@Path("/savesignature")
	@OPTIONS
	@Consumes()
	public Response saveSignatureAsImg() {
		return Response.status(200).header("Access-Control-Allow-Origin", "*").
				header("Access-Control-Allow-Methods", "POST").
				header("Access-Control-Allow-Headers", "Content-Type").
				header("Content-Type", "text/plain").build();
	}
	
	
	@Path("/rejectsignature")
	@GET
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public Response rejectDocumentFromEsignForm(@QueryParam("templatename") String templateName, @QueryParam("key") String customerKey, @QueryParam("id") String id, 
			@QueryParam("notes") String notes, @QueryParam("action") String actionName) {
		String message = "Success";
		message = formService.rejectDocumentFromEsignForm(templateName, customerKey, id, notes);
		
		return Response.ok(message).header("Access-Control-Allow-Origin", "*").
				header("Content-Type", "application/json").build();
	}
	
	@Path("/rejectsignature")
	@OPTIONS
	public Response rejectDocumentFromEsignForm() {
		return Response.status(200).header("Access-Control-Allow-Origin", "*").
				header("Access-Control-Allow-Methods", "GET").build();
	}

	
	@Path("/getipdetailurl")
	@POST
	@Consumes({MediaType.APPLICATION_FORM_URLENCODED, MediaType.APPLICATION_JSON})
	@Produces(MediaType.APPLICATION_JSON)
	public Response getIpDetails(@QueryParam("templatename") String templateName, @QueryParam("key") String customerKey, String jsonStr) {
		String message = "Success";
		System.out.println("-=-=-=-=-=-getIpDetails=-=jsonStr-=-=-="+jsonStr);
		System.out.println("-=-=-=-=-=-getIpDetails=-=-=templateName-=-="+templateName);
		System.out.println("-=-=-=-=-=-getIpDetails=-=-customerKey=-=-="+customerKey);
		//message = formService.saveSignatureAsImg(templateName, svgData, customerKey, fieldId);
		message = formService.getIpDetailsMethod(templateName, customerKey, jsonStr);
		return Response.ok(message).header("Access-Control-Allow-Origin", "*").
				header("Content-Type", "application/json").build();
	}
	
	
	@Path("/savesignatureaudit")
	@POST
	@Consumes({MediaType.APPLICATION_FORM_URLENCODED, MediaType.APPLICATION_JSON})
	@Produces(MediaType.APPLICATION_JSON)
	public Response saveSignatureAudit(@QueryParam("templatename") String templateName, @QueryParam("key") String customerKey, String jsonStr) {
		String message = "Success";
		System.out.println(customerKey+"-=-=-=-=-=-formDetails=-=-=-=-="+templateName);
		//message = formService.saveSignatureAsImg(templateName, svgData, customerKey, fieldId);
		message = formService.saveSignatureAudit(templateName, customerKey, jsonStr);
		return Response.ok(message).header("Access-Control-Allow-Origin", "*").
				header("Content-Type", "application/json").build();
	}
	
	@Path("/savesignatureaudit")
	@OPTIONS
	@Consumes()
	public Response saveSignatureAudit() {
		return Response.status(200).header("Access-Control-Allow-Origin", "*").
				header("Access-Control-Allow-Methods", "POST").
				header("Access-Control-Allow-Headers", "Content-Type").
				header("Content-Type", "application/json").build();
	}
	
	@Path("/consent")
	@POST
	@Consumes({MediaType.APPLICATION_FORM_URLENCODED})
	@Produces(MediaType.APPLICATION_JSON)
	public Response saveSignatureConsent(@QueryParam("docid") String docId, @QueryParam("flag") String flag, @QueryParam("id") String id) {
		String message = "Success";
		//System.out.println(customerKey+"-=-=-=-=-=-formDetails=-=-=-=-="+templateName);
		//message = formService.saveSignatureAsImg(templateName, svgData, customerKey, fieldId);
		message = formService.saveSignatureConsent(docId, flag, id);
		return Response.ok(message).header("Access-Control-Allow-Origin", "*").
				header("Content-Type", "application/json").build();
	}
	
	@Path("/consent")
	@OPTIONS
	@Consumes()
	public Response saveSignatureConsent() {
		return Response.status(200).header("Access-Control-Allow-Origin", "*").
				header("Access-Control-Allow-Methods", "POST").
				header("Access-Control-Allow-Headers", "Content-Type").
				/*header("Content-Type", "application/json").*/build();
	}
	@Path("/deletehirarchy")
	@POST
	@Consumes({MediaType.APPLICATION_FORM_URLENCODED, MediaType.APPLICATION_JSON})
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteHirarchy(@QueryParam("templatename") String templateName, @QueryParam("hirarchyID") String hirarchyID, @QueryParam("hirarchyName") String hirarchyName) throws Exception  {
		String message = "Success";
		System.out.println(hirarchyID+"-=-=-=-=-=1111-formDetails=-=22-=-=-="+templateName);
		
		PropUtil prop=new PropUtil();
		String XMLFileName=prop.getProperty("HIERARCHY_XML_FILE");
		String dir = prop.getProperty("HIERARCHY_XML_DIR");
		Document doc=Globals.openXMLFile(dir, XMLFileName);

		Node workFlowNode = Globals.getNodeByAttrVal(doc, "Hierarchy_Level", "Hierarchy_ID", hirarchyID);
		Element workflwEle=(Element)workFlowNode;
		String hirachyID=workflwEle.getAttribute("Hierarchy_ID") == null || workflwEle.getAttribute("Hierarchy_ID").equals("") ? "" : workflwEle.getAttribute("Hierarchy_ID");
		System.out.println(hirarchyID+"-=-=-=-=-=Delete-=-=-="+hirachyID);
		if(hirachyID.equals(hirarchyID)) {
			workFlowNode.getParentNode().removeChild(workFlowNode);
		}
		Globals.writeXMLFile(doc, dir, XMLFileName);
	//message = formService.saveSignatureAsImg(templateName, svgData, customerKey, fieldId);
		return Response.status(200).header("Access-Control-Allow-Origin", "*").
				header("Access-Control-Allow-Methods", "POST").
				header("Access-Control-Allow-Headers", "Content-Type").build();
	}
}
