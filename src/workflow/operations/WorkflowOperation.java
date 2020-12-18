package workflow.operations;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/workflow")
public class WorkflowOperation {
	OperationService operService;

	public WorkflowOperation() {
		operService = new OperationService();
	}
	@Path("/authenticate")
	@GET
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public String authenticateUser(@QueryParam("userid") String userEmailID, @QueryParam("password") String password,
			@QueryParam("documentname") String documentName, @QueryParam("docid") String docid) {
		String connJsonStr = "";
		connJsonStr = operService.authenticateUser(userEmailID, password, documentName, docid);
		return connJsonStr;
	}
	
	@Path("/list")
	@GET
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public String listWorkflow(@QueryParam("customerKey") String customerKey) {
		String connJsonStr = "";
		connJsonStr = operService.listWorkflow(customerKey);
		return connJsonStr;
	}
	
	@Path("/attachDocument")
	@POST
	@Consumes({MediaType.APPLICATION_FORM_URLENCODED, MediaType.APPLICATION_JSON})
	@Produces(MediaType.APPLICATION_JSON)
	public String attachDocument(@QueryParam("customerKey") String customerKey, String jsonStr) {
		String message = "";
		message = operService.attachDocument(jsonStr);
		return message;
	}
	@Path("/listTasks")
	@GET
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public String listTasksForUser(@QueryParam("userid") String userEmailID) {
		String connJsonStr = "";
		connJsonStr = operService.listTasksForUser(userEmailID);
		return connJsonStr;
	}
	
	@Path("/get/attach")
	@GET
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public String getAttachmentDeails(@QueryParam("docname") String documentName, @QueryParam("docid") String documentId) {
		String connJsonStr = "";
		connJsonStr = operService.getAttachmentDeails(documentName, documentId);
		return connJsonStr;
	}
	
	@Path("/checkexist")
	@GET
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String checkDocExist(@QueryParam("docname") String documentName, @QueryParam("docid") String docId, @QueryParam("userid") String userEmailID) {
		String message = "";
		message = operService.checkDocumentAttached2WF(documentName, docId, userEmailID);
		return message;
	}
	
	@Path("/performAction")
	@GET
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public String performingAction(@QueryParam("userid") String userEmailID, @QueryParam("actionnotes") String notes, 
			@QueryParam("action") String actionName, @QueryParam("docname") String documentName, @QueryParam("docid") String documentId) {
		String message = "";
		message = operService.performingWorkflowAction(userEmailID, notes, actionName, documentName, documentId);
		return message;
	}
	
	@Path("/history")
	@GET
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public String getDocumentHistory(@QueryParam("docid") String docId) {
		String message = "";
		message = operService.getDocumentHistory(docId);
		return message;
	}
	@Path("/notification")
	@GET
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public String getNotificationDetails(@QueryParam("userid") String userEmailID, @QueryParam("actionnotes") String notes,
			@QueryParam("action") String actionName, @QueryParam("docname") String documentName, @QueryParam("docid") String documentId) {
		String message = "";
		System.out.println("-=-=-getNotificationDetails=-=-=documentId-=-=-=-="+documentId);
		message = operService.getNotificationDetails(userEmailID, notes, actionName, documentName, documentId);
		return message;
	}
	@Path("/changepassword")
	@GET
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public String changePasswordMail(@QueryParam("userid") String userEmailID) {
		String message = "";
		message = operService.changePasswordMail(userEmailID);
		return message;
	}
	@Path("/signout")
	@GET
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String signoutUser(@QueryParam("userid") String userEmailID, @QueryParam("docid") String docId) {
		String message = "Success";
		operService.updateWorkingUserInXML(userEmailID, docId);
		return message;
	}
	@Path("/document/checklocked")
	@GET
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public String checkDocumentLocked(@QueryParam("docid") String docId) {
		String connJsonStr = "";
		connJsonStr = operService.checkDocumentLocked(docId);
		return connJsonStr;
	}
	
	@Path("/status")
	@GET
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public String getWorkflowStatusDetails(@QueryParam("docid") String docId) {
		String connJsonStr = "";
		connJsonStr = operService.getWorkflowStatusDetails(docId);
		return connJsonStr;
	}
	
	@Path("/reports")
	@GET
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public String getDocumentDetails(@QueryParam("customerKey") String customerKey) {
		String connJsonStr = "";
		connJsonStr = operService.getDocumentDetails(customerKey);
		return connJsonStr;
	}
	
	@Path("/getsmartsheetcode")
	@GET
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String getSmartSheetCode(@QueryParam("state") String state) {
		String connJsonStr = "";
		connJsonStr = operService.getSmartSheetCode(state);
		return connJsonStr;
	}
	
	@Path("/getdropboxcode")
	@GET
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String getDropBoxCode(@QueryParam("redirecturl") String url) {
		String connJsonStr = "";
		connJsonStr = operService.getDropboxCode(url);
		return connJsonStr;
	}
	
	
	@Path("/getUSerAudit")
	@GET
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUSerAudit(@QueryParam("IPAddress") String url) {		
		String connJsonStr = "";
		connJsonStr = operService.getUserAudit(url);
		connJsonStr=connJsonStr.trim();		
		
		return Response.ok(connJsonStr).header("Access-Control-Allow-Origin", "*").
				header("Content-Type", "application/json").build();
	}	
	
	@Path("/getUSerAudit")
	@OPTIONS
	public Response getUSerAudit() {
		System.out.println("-=-=-=-=-getUSerAudit=-=-=-=-=");
		return Response.status(200).header("Access-Control-Allow-Origin", "*").
				header("Access-Control-Allow-Methods", "GET").build();
	}
	
		
	
	
	
	@Path("/stagedetails")
	@GET
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public String getStageDetails(@QueryParam("docid") String docId, @QueryParam("stageno") String stgNo) {
		System.out.println("-=============================");
		String connJsonStr = "";
		connJsonStr = operService.getStageDetails(docId, stgNo);
		return connJsonStr;
	}
	
	@Path("/changefiles")
	@POST
	@Consumes({MediaType.APPLICATION_FORM_URLENCODED, MediaType.APPLICATION_JSON})
	@Produces(MediaType.APPLICATION_JSON)
	public String changeFilesAFterStageApproval(@QueryParam("customerKey") String customerKey, String jsonStr) {
		String message = "";
		message = operService.changeFilesAFterStageApproval(customerKey, jsonStr);
		return message;
	}
}
