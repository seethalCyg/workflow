package form.operations;

import java.util.Hashtable;

import org.json.simple.JSONObject;

import com.itextpdf.text.pdf.PdfStructTreeController.returnType;

import managers.FormsManager;
import utils.Globals;

public class FormService {
	public String updateForm(String formId, String folderId, String jsonStr) {
		String opJsonStr = "";
		try {
			
			Hashtable<String, String> detailsHT = FormsManager.saveFormMetadata(formId, folderId, jsonStr);
			JSONObject j = new JSONObject(detailsHT);
			opJsonStr = j.toJSONString();
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			Hashtable<String, String> detailsHT = new Hashtable<>();
			detailsHT.put("Status", "Error");
			detailsHT.put("StatusDetails", Globals.getException(e));
			JSONObject j = new JSONObject(detailsHT);
			opJsonStr = j.toJSONString();
		}
		return opJsonStr;
	}
	
	public String getIpDetailsMethod(String templateName, String customerKey, String jsonStr) {
		String opJsonStr = "";
		try {
			Hashtable<String, String> detailsHT = FormsManager.getIpDetailsAudit(templateName, customerKey, jsonStr);
			JSONObject j = new JSONObject(detailsHT);
			opJsonStr = j.toJSONString();
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			Hashtable<String, String> detailsHT = new Hashtable<>();
			detailsHT.put("Status", "Error");
			detailsHT.put("StatusDetails", Globals.getException(e));
			JSONObject j = new JSONObject(detailsHT);
			opJsonStr = j.toJSONString();
		}
		System.out.println("^^^^^^^^^^^getIpDetailsMethod^^^^^^^^^^^^^ :"+opJsonStr);
		return opJsonStr;
	}
	public String updateEsignForm(String templateName, String jsonStr, String customerKey, String createdBy, String saveasFlage, String newTemplateName) {
		String opJsonStr = "";
		try {
			
			Hashtable<String, String> detailsHT = FormsManager.saveEsignFormMetadata(templateName, jsonStr, customerKey, createdBy, saveasFlage, newTemplateName);
			JSONObject j = new JSONObject(detailsHT);
			opJsonStr = j.toJSONString();
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			Hashtable<String, String> detailsHT = new Hashtable<>();
			detailsHT.put("Status", "Error");
			detailsHT.put("StatusDetails", Globals.getException(e));
			JSONObject j = new JSONObject(detailsHT);
			opJsonStr = j.toJSONString();
		}
		return opJsonStr;
	}
	
	public String saveEsignData(String templateName, String jsonStr, String customerKey, String username, String notes, String actionName) {
		String opJsonStr = "";
		try {
			
			Hashtable<String, String> detailsHT = FormsManager.saveEsignFormdata(templateName, jsonStr, customerKey, username, notes, actionName);
			JSONObject j = new JSONObject(detailsHT);
			opJsonStr = j.toJSONString();
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			Hashtable<String, String> detailsHT = new Hashtable<>();
			detailsHT.put("Status", "Error");
			detailsHT.put("StatusDetails", Globals.getException(e));
			JSONObject j = new JSONObject(detailsHT);
			opJsonStr = j.toJSONString();
		}
		return opJsonStr;
	}
	
	public String saveSignatureAsImg(String templateName, String svgData, String customerKey, String fieldId) {
		String opJsonStr = "";
		try {
			
			Hashtable<String, String> detailsHT = FormsManager.saveSignatureAsImg(templateName, svgData, customerKey, fieldId);
			JSONObject j = new JSONObject(detailsHT);
			opJsonStr = j.toJSONString();
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			Hashtable<String, String> detailsHT = new Hashtable<>();
			detailsHT.put("Status", "Error");
			detailsHT.put("StatusDetails", Globals.getException(e));
			JSONObject j = new JSONObject(detailsHT);
			opJsonStr = j.toJSONString();
		}
		return opJsonStr;
	}
	public String saveSignatureAsImgform(String templateName, String svgData, String customerKey, String fieldId,String filename) {
		String opJsonStr = "";
		try {
			
			Hashtable<String, String> detailsHT = FormsManager.saveSignatureAsImgform(templateName, svgData, customerKey, fieldId,filename);
			JSONObject j = new JSONObject(detailsHT);
			opJsonStr = j.toJSONString();
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			Hashtable<String, String> detailsHT = new Hashtable<>();
			detailsHT.put("Status", "Error");
			detailsHT.put("StatusDetails", Globals.getException(e));
			JSONObject j = new JSONObject(detailsHT);
			opJsonStr = j.toJSONString();
		}
		return opJsonStr;
	}
	
	public String getFormMetadata(String formId, String customerKey) {
		String opJsonStr = "";
		try {
			opJsonStr = FormsManager.getFormMetadata(formId, customerKey);
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			Hashtable<String, String> detailsHT = new Hashtable<>();
			detailsHT.put("Status", "Error");
			detailsHT.put("StatusDetails", Globals.getException(e));
			JSONObject j = new JSONObject(detailsHT);
			opJsonStr = j.toJSONString();
		}
		return opJsonStr;
	}
	
	public String getEsignFormMetadata(String templateName, String customerKey) {
		String opJsonStr = "";
		try {
			opJsonStr = FormsManager.getEsignFormMetadata(templateName, customerKey);
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			Hashtable<String, String> detailsHT = new Hashtable<>();
			detailsHT.put("Status", "Error");
			detailsHT.put("StatusDetails", Globals.getException(e));
			JSONObject j = new JSONObject(detailsHT);
			opJsonStr = j.toJSONString();
		}
		return opJsonStr;
	}
	
	public String getFormData(String formId, String customerKey, String dataFileName) {
		String opJsonStr = "";
		try {
			opJsonStr = FormsManager.getFormData(formId, customerKey, dataFileName);
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			Hashtable<String, String> detailsHT = new Hashtable<>();
			detailsHT.put("Status", "Error");
			detailsHT.put("StatusDetails", Globals.getException(e));
			JSONObject j = new JSONObject(detailsHT);
			opJsonStr = j.toJSONString();
		}
		return opJsonStr;
	}
	
	public String updateAdminFile(String jsonStr) {
		String opJsonStr = "";
		try {
			Hashtable<String, String> detailsHT = FormsManager.updateAdminFile(jsonStr);
			JSONObject j = new JSONObject(detailsHT);
			opJsonStr = j.toJSONString();
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			Hashtable<String, String> detailsHT = new Hashtable<>();
			detailsHT.put("Status", "Error");
			detailsHT.put("StatusDetails", Globals.getException(e));
			JSONObject j = new JSONObject(detailsHT);
			opJsonStr = j.toJSONString();
		}
		return opJsonStr;
	}
	
	public String saveHTMLContent(String formId, String customerKey, String htmlText) {
		String opJsonStr = "";
		try {
			Hashtable<String, String> detailsHT = FormsManager.saveHTMLContent(formId, customerKey, htmlText);
			JSONObject j = new JSONObject(detailsHT);
			opJsonStr = j.toJSONString();
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			Hashtable<String, String> detailsHT = new Hashtable<>();
			detailsHT.put("Status", "Error");
			detailsHT.put("StatusDetails", Globals.getException(e));
			JSONObject j = new JSONObject(detailsHT);
			opJsonStr = j.toJSONString();
		}
		return opJsonStr;
	}
	
	public String saveEsignHTMLContent(String templateName, String customerKey, String htmlText, String esignflag) {
		String opJsonStr = "";
		try {
			FormsManager.saveEsignHTMLContent(templateName, customerKey, htmlText, esignflag);
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			Hashtable<String, String> detailsHT = new Hashtable<>();
			detailsHT.put("Status", "Error");
			detailsHT.put("StatusDetails", Globals.getException(e));
			JSONObject j = new JSONObject(detailsHT);
			opJsonStr = j.toJSONString();
		}
		return opJsonStr;
	}
	
	public String loadEsignHTML(String custKey, String templateName, String dataFilename, String id) {
		String opJsonStr = "";
		try {
			opJsonStr = FormsManager.loadEsignHTML(custKey, templateName, dataFilename, id);
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return opJsonStr;
	}
	public String rejectDocumentFromEsignForm(String templateName, String customerKey, String username, String notes) {
		String opJsonStr = "";
		try {
			Hashtable<String, String> detailsHT = FormsManager.rejectDocumentFromEsignForm(templateName, customerKey, username, notes);
			JSONObject j = new JSONObject(detailsHT);
			opJsonStr = j.toJSONString();
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			Hashtable<String, String> detailsHT = new Hashtable<>();
			detailsHT.put("Status", "Error");
			detailsHT.put("StatusDetails", Globals.getException(e));
			JSONObject j = new JSONObject(detailsHT);
			opJsonStr = j.toJSONString();
		}
		return opJsonStr;
	}

	public String saveSignatureAudit(String templateName, String customerKey, String jsonStr) {
		String opJsonStr = "";
		try {
			Hashtable<String, String> detailsHT = FormsManager.saveSignatureAudit(templateName, customerKey, jsonStr);
			JSONObject j = new JSONObject(detailsHT);
			opJsonStr = j.toJSONString();
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			Hashtable<String, String> detailsHT = new Hashtable<>();
			detailsHT.put("Status", "Error");
			detailsHT.put("StatusDetails", Globals.getException(e));
			JSONObject j = new JSONObject(detailsHT);
			opJsonStr = j.toJSONString();
		}
		
		return opJsonStr;
	}
	
	public String saveSignatureConsent(String docId, String flag, String emailId) {
		String opJsonStr = "";
		try {
			Hashtable<String, String> detailsHT = FormsManager.saveSignatureConsent(docId, flag, emailId);
			JSONObject j = new JSONObject(detailsHT);
			opJsonStr = j.toJSONString();
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			Hashtable<String, String> detailsHT = new Hashtable<>();
			detailsHT.put("Status", "Error");
			detailsHT.put("StatusDetails", Globals.getException(e));
			JSONObject j = new JSONObject(detailsHT);
			opJsonStr = j.toJSONString();
		}
		return opJsonStr;
	}
}
