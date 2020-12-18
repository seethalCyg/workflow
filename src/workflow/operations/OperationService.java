package workflow.operations;

import java.util.Hashtable;

import org.json.simple.JSONObject;

import com.sun.corba.se.spi.orbutil.threadpool.Work;

import managers.WorkflowManager;

public class OperationService {
	public String authenticateUser(String userEmailID, String password, String documentName, String documentId) {
		String jsonStr = "";
		try {
		Hashtable HT = new Hashtable<>();
		String loginStatus = WorkflowManager.checkLogin(userEmailID, password, HT);
		System.out.println("=--loginStatus---"+loginStatus);
//		jsonStr = "{";
		if(loginStatus.equalsIgnoreCase("Success")) {
			String customerKey = String.valueOf(HT.get("CustomerKey"));
			Hashtable wfHT = WorkflowManager.actionsWaitingForUser(userEmailID);
			wfHT.put("LoginStatus", "Success");
			wfHT.put("CustomerKey", customerKey);
			Hashtable openDocTask = new Hashtable<>(); 
			Hashtable userAwaitingTasksHT = (Hashtable)wfHT.get("UserAwaitingTasks");
			int tableCount = Integer.valueOf((String) userAwaitingTasksHT.get("TableLength"));
			for(int i=0;i<tableCount;i++) {
				Hashtable taskHT = (Hashtable)userAwaitingTasksHT.get("R"+i);
				String docName = (String) taskHT.get("DocumentName");
				String docId = (String) taskHT.get("DocumentID");
				if(docId.equalsIgnoreCase(documentId)) {
					openDocTask.putAll(taskHT);
					break;
				}
			}
			Hashtable userTaskHT = (Hashtable)wfHT.get("UserTasks");
			tableCount = Integer.valueOf((String) userTaskHT.get("TableLength"));
			for(int i=0;i<tableCount;i++) {
				Hashtable taskHT = (Hashtable)userTaskHT.get("R"+i);
				String docName = (String) taskHT.get("DocumentName");
				String docId = (String) taskHT.get("DocumentID");
				if(docId.equalsIgnoreCase(documentId)) {
					openDocTask.clear();
					openDocTask.putAll(taskHT);
					break;
				}
			}
			Hashtable adminTaskHT = (Hashtable)wfHT.get("AdminTasks");
			tableCount = Integer.valueOf((String) adminTaskHT.get("TableLength"));
			
			for(int i=0;i<tableCount;i++) {
				Hashtable taskHT = (Hashtable)adminTaskHT.get("R"+i);
				String docName = (String) taskHT.get("DocumentName");
				String docId = (String) taskHT.get("DocumentID");
				if(docId.equalsIgnoreCase(documentId)) {
					openDocTask.clear();
					openDocTask.putAll(taskHT);
					break;
				}
			}
			
			wfHT.put("OpenDocumentTask", openDocTask);
			System.out.println("-=-=-=-wfHT=-=-=-="+wfHT);
			JSONObject j = new JSONObject(wfHT);
			jsonStr = j.toJSONString();
			//jsonStr = "{\"LoginStatus\":\"Success\", \"CustomerKey\":\""+customerKey+"\"}";
		}else {
			jsonStr = "{\"LoginStatus\":\"Failure\", \"CustomerKey\":\"\"}";
		}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			jsonStr = "{\"LoginStatus\":\"Failure\", \"CustomerKey\":\"\"}";
		}
		System.out.println("-=-=-=-authenticateUser=-=-=-="+jsonStr);
		return jsonStr;
	}
	public String listWorkflow(String customerKey) {
		String wfListJStr = "";
		try {
			Hashtable wfHT = WorkflowManager.listWorkflowForCustomer(customerKey);
			JSONObject j = new JSONObject(wfHT);
			wfListJStr = j.toJSONString();
			
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			wfListJStr = "{\"Status\":\"Error\", \"StatusDetails\":\""+e.getMessage()+"\"}";
		}
		System.out.println("-=-=-=-listWorkflow=-=-=-="+wfListJStr);
		return wfListJStr;
	}
	public String attachDocument(String jsonStr) {
		String message = "";
		Hashtable detailsHT = new Hashtable<>();
		try {
			System.out.println("-=-=-=-jsonStr=-=-=-="+jsonStr);
			detailsHT = WorkflowManager.attachDocument2TheWF(jsonStr, "Save");
			JSONObject j = new JSONObject(detailsHT);
			message = j.toJSONString();
			
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
//			message = "Document is not attached to workflow.";
			detailsHT.put("Status", "Failure");
			detailsHT.put("StatusDetails",e.getMessage() );
			JSONObject j = new JSONObject(detailsHT);
			message = j.toJSONString();
		}
		System.out.println("-=-=-=-attachDocument=-=-=-="+message);
		return message;
	}
	public String listTasksForUser(String userName) {
		String wfListJStr = "";
		try {
			Hashtable wfHT = WorkflowManager.actionsWaitingForUser(userName);
			JSONObject j = new JSONObject(wfHT);
			wfListJStr = j.toJSONString();
		}catch (Exception e) {
			// TODO: handle exception
			wfListJStr = "{\"Status\":\"Error\", \"StatusDetails\":\""+e.getMessage()+"\"}";
		}
		System.out.println("-=-=-=-listTasksForUser=-=-=-="+wfListJStr);
		return wfListJStr;
	}
	
	public String getAttachmentDeails(String documentName, String documentId) {
		String wfListJStr = "";
		try {
			Hashtable wfHT = WorkflowManager.getAttachDocumentDetailsFromXML(documentName, documentId);
			JSONObject j = new JSONObject(wfHT);
			wfListJStr = j.toJSONString();
		}catch (Exception e) {
			// TODO: handle exception
			wfListJStr = "{\"Status\":\"Error\", \"StatusDetails\":\""+e.getMessage()+"\"}";
			return wfListJStr;
		}
		System.out.println("-=-=-=-getAttachmentDeails=-=-=-="+wfListJStr);
		return wfListJStr;
	}
	
	public String checkDocumentAttached2WF(String documentName, String docId, String userEmailID) {
		String str = "";
		try {
			str = WorkflowManager.checkDocumentAttached2WF(documentName, docId, userEmailID);
		}catch (Exception e) {
			// TODO: handle exception
			str = "false";
			return str;
		}
		return str;
	}
	public String performingWorkflowAction(String userName, String notes, String actionName, String docName, String documentId) {
		String mess = "";
		Hashtable detailsHT = new Hashtable<>();
		try {
			detailsHT = WorkflowManager.performingAction(userName, notes, actionName, docName, documentId, "MSO","","","");
			JSONObject j = new JSONObject(detailsHT);
			mess = j.toJSONString();
			System.out.println("-=-=-=-=-mess=-=-=-="+mess);
//			detailsHT
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			detailsHT.put("Status", "Error");
			detailsHT.put("StatusDetails", e.getMessage());
			JSONObject j = new JSONObject(detailsHT);
			mess = j.toJSONString();
			return mess;
		}
		return mess;
	}
	public String getDocumentHistory(String docId) {
		String mess = "";
		Hashtable detailsHT = new Hashtable<>();
		try {
			WorkflowManager.getHistoryDetails(detailsHT, docId);
			JSONObject j = new JSONObject(detailsHT);
			mess = j.toJSONString();
		}catch (Exception e) {
			// TODO: handle exception
			detailsHT.put("Status", "Error");
			detailsHT.put("StatusDetails", e.getMessage());
			JSONObject j = new JSONObject(detailsHT);
			mess = j.toJSONString();	
			return mess;
		}
		return mess;
	}
	
	public String getNotificationDetails(String userName, String notes, String actionName, String docName, String docId) {
		String mess = "";
		Hashtable detailsHT = new Hashtable<>();
		try {
			detailsHT = WorkflowManager.getNotificationDetails4TheAction(userName, notes, actionName, docName, docId);
			detailsHT.put("Status", "Success");
			detailsHT.put("StatusDetails", "Success");
			JSONObject j = new JSONObject(detailsHT);
			mess = j.toJSONString();
//			String bobyofMail = (String)detailsHT.get("Message");
//			String subject = (String)detailsHT.get("Subject");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			detailsHT.put("Status", "Error");
			detailsHT.put("StatusDetails", e.getMessage());
			JSONObject j = new JSONObject(detailsHT);
			mess = j.toJSONString();
			return mess;
		}
		return mess;
	}
	
	public String changePasswordMail(String userName) {
		String message = WorkflowManager.changePasswordMail(userName);
		message = message.equalsIgnoreCase("true") ? "Password changed successfully and mailed to your email id." : "Entered user id is not a vaild user.";
		return message;
	}
	
	public void updateWorkingUserInXML(String userName, String docId) {
		try {
			WorkflowManager.updateWorkingUserInXML(docId, userName, false);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String checkDocumentLocked(String docId) {
		String docLockFlag = "";
		Hashtable detailsHT = new Hashtable<>();
		try {
			detailsHT = WorkflowManager.checkDocumentLocked(docId);
			JSONObject j = new JSONObject(detailsHT);
			docLockFlag = j.toJSONString();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			detailsHT.put("DocumentLocked", "false");
			detailsHT.put("DocumentLockedBy", "");
			JSONObject j = new JSONObject(detailsHT);
			docLockFlag = j.toJSONString();
			return docLockFlag;
		}
		return docLockFlag;
	}
	
	public String getWorkflowStatusDetails(String docId) {
		String details = "";
		Hashtable detailsHT = new Hashtable<>();
		try {
			detailsHT = WorkflowManager.getWorkflowStatusDetails(docId);
			detailsHT.put("Status", "Success");
			detailsHT.put("StatusDetails", "Success");
			JSONObject j = new JSONObject(detailsHT);
			details = j.toJSONString();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			detailsHT.put("Status", "Error");
			detailsHT.put("StatusDetails", e.getMessage());
			JSONObject j = new JSONObject(detailsHT);
			details = j.toJSONString();
		}
		return details;
	}
	
	public String getDocumentDetails(String customerKey) {
		try {
			return WorkflowManager.getDocumentDetails(customerKey);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Hashtable detailsHT = new Hashtable<>();
			detailsHT.put("Status", "Error");
			detailsHT.put("StatusDetails", e.getMessage());
			JSONObject j = new JSONObject(detailsHT);
			return j.toJSONString();
		}
	}
	public String getSmartSheetCode(String state) {
		try {
			return WorkflowManager.getSmartSheetCode(state);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			return "";
		}
	}
	
	public String getDropboxCode(String url) {
		try {
			return WorkflowManager.getDropboxCode(url);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			return "";
		}
	}
	
	public String getStageDetails(String docId, String stgNo) {
		try {
			Hashtable detailsHT = WorkflowManager.getStageDetails(docId, stgNo);
			detailsHT.put("Status", "Success");
			detailsHT.put("StatusDetails", "Success");
			JSONObject j = new JSONObject(detailsHT);
			System.out.println("-=-=-=-=-=-=-getStageDetails=-=-=-=-=-=-=>>>"+j.toJSONString());
			return j.toJSONString();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Hashtable detailsHT = new Hashtable<>();
			detailsHT.put("Status", "Error");
			detailsHT.put("StatusDetails", e.getMessage());
			JSONObject j = new JSONObject(detailsHT);
			return j.toJSONString();
		}
	}
	
	
	public String getUserAudit(String ip) {
		String details = "";
		Hashtable detailsHT = new Hashtable<>();
		try {
			
			String ht = WorkflowManager.getUserAudit(ip);			
			/*detailsHT.put("Status", "Success");
			detailsHT.put("StatusDetails", "Success");
			JSONObject j = new JSONObject(detailsHT);
			details = j.toJSONString();*/
			details=ht;
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			detailsHT.put("Status", "Error");
			detailsHT.put("StatusDetails", e.getMessage());
			JSONObject j = new JSONObject(detailsHT);
			details = j.toJSONString();
		}
		return details;
	}	
	
	
	public String changeFilesAFterStageApproval(String custKey, String jsonStr) {
		try {
			WorkflowManager.changeFilesAFterStageApproval(custKey, jsonStr);
			Hashtable detailsHT = new Hashtable<>();
			detailsHT.put("Status", "Success");
			detailsHT.put("StatusDetails", "Success");
			JSONObject j = new JSONObject(detailsHT);
			System.out.println("-=-=-=-=-=-=-getStageDetails=-=-=-=-=-=-=>>>"+j.toJSONString());
			return j.toJSONString();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Hashtable detailsHT = new Hashtable<>();
			detailsHT.put("Status", "Error");
			detailsHT.put("StatusDetails", e.getMessage());
			JSONObject j = new JSONObject(detailsHT);
			return j.toJSONString();
		}
	}
}
