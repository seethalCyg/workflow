package beans;

import java.util.Hashtable;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import utils.Globals;
import utils.PropUtil;

@ManagedBean(name = "securityBean")
@SessionScoped
public class SecurityBean {

	/**
	 * @param args
	 */
	
	//setting all icon object
	//hierarchyList
	private String adminSetting="true";
	private String configSetting="true";
	private String addHierarchy="true";
	private String deleteHierarchy="true";
	
	private String addUserHierarchy="true";
	private String addEditHierarchy="true";
	
	private String value4Login="";  // code change Menaka 25MAR2013
	
	
	public String getAddUserHierarchy() {
		return addUserHierarchy;
	}


	public void setAddUserHierarchy(String addUserHierarchy) {
		this.addUserHierarchy = addUserHierarchy;
	}


	public String getAddEditHierarchy() {
		return addEditHierarchy;
	}


	public void setAddEditHierarchy(String addEditHierarchy) {
		this.addEditHierarchy = addEditHierarchy;
	}

	

	public String getValue4Login() {
		return value4Login;
	}


	public void setValue4Login(String value4Login) {
		this.value4Login = value4Login;
	}

	//hierarchyTree
	private String segmentAdminstration="true";
	private String configCommonSegment="true";
	private String generateFact="true";
	private String generateData="true";
	private String reGenerateHierarchy="true";
	private String workFlowProcess="true";
	private String addRollUpNode="true";
	private String addDataNode="true";
	private String addHierarchyNode="true";
	private String editSelectedNode="true";	
	private String pushOnLevelOut="true";
	private String pushOnLevelIn="true";
	private String moveUP="true";
	private String moveDown="true";
	private String deleteNode="true";
	private String viewingAudit="true";
	private String versionRestore="true";
	private String savingVersion="true";

	private String viewHierarchy="true";
	private String role="true";
	
	private String copyHierarchy="true";
	private String restoreHierarchy="true";
	private String createVersionHierarchy="true";
	private String auditingHierarchy="true";
	
	private String auditEnable="true";
	private String auditDisable="true";
	
	private String referenceHierarchy="true";
	private String riValidationOnhtree="true";
	
	private String factFullLoad = "true";
	private String factIncrementalload = "true";
	private String factGenerateLoad = "true";
	
	public String getFactGenerateLoad() {
		return factGenerateLoad;
	}


	public void setFactGenerateLoad(String factGenerateLoad) {
		this.factGenerateLoad = factGenerateLoad;
	}


	public String getFactIncrementalload() {
		return factIncrementalload;
	}


	public void setFactIncrementalload(String factIncrementalload) {
		this.factIncrementalload = factIncrementalload;
	}


	public String getFactFullLoad() {
		return factFullLoad;
	}


	public void setFactFullLoad(String factFullLoad) {
		this.factFullLoad = factFullLoad;
	}


	public String getReferenceHierarchy() {
		return referenceHierarchy;
	}


	public void setReferenceHierarchy(String referenceHierarchy) {
		this.referenceHierarchy = referenceHierarchy;
	}


	public String getRiValidationOnhtree() {
		return riValidationOnhtree;
	}


	public void setRiValidationOnhtree(String riValidationOnhtree) {
		this.riValidationOnhtree = riValidationOnhtree;
	}

	
	public String getAuditEnable() {
		return auditEnable;
	}


	public void setAuditEnable(String auditEnable) {
		this.auditEnable = auditEnable;
	}


	public String getAuditDisable() {
		return auditDisable;
	}


	public void setAuditDisable(String auditDisable) {
		this.auditDisable = auditDisable;
	}


	public String getViewingAudit() {
		return viewingAudit;
	}


	public void setViewingAudit(String viewingAudit) {
		this.viewingAudit = viewingAudit;
	}


	public String getVersionRestore() {
		return versionRestore;
	}


	public void setVersionRestore(String versionRestore) {
		this.versionRestore = versionRestore;
	}


	public String getSavingVersion() {
		return savingVersion;
	}


	public void setSavingVersion(String savingVersion) {
		this.savingVersion = savingVersion;
	}

	
			 
	public String getConfigSetting() {
		return configSetting;
	}


	public void setConfigSetting(String configSetting) {
		this.configSetting = configSetting;
	}
	public String getAddHierarchy() {
		return addHierarchy;
	}


	public void setAddHierarchy(String addHierarchy) {
		this.addHierarchy = addHierarchy;
	}


	public String getDeleteHierarchy() {
		return deleteHierarchy;
	}


	public void setDeleteHierarchy(String deleteHierarchy) {
		this.deleteHierarchy = deleteHierarchy;
	}


	public String getAdminSetting() {
		return adminSetting;
	}


	public void setAdminSetting(String adminSetting) {
		this.adminSetting = adminSetting;
	}


	public String getSegmentAdminstration() {
		return segmentAdminstration;
	}


	public void setSegmentAdminstration(String segmentAdminstration) {
		this.segmentAdminstration = segmentAdminstration;
	}


	public String getConfigCommonSegment() {
		return configCommonSegment;
	}


	public void setConfigCommonSegment(String configCommonSegment) {
		this.configCommonSegment = configCommonSegment;
	}


	public String getGenerateData() {
		return generateData;
	}


	public void setGenerateData(String generateData) {
		this.generateData = generateData;
	}


	public String getReGenerateHierarchy() {
		return reGenerateHierarchy;
	}


	public void setReGenerateHierarchy(String reGenerateHierarchy) {
		this.reGenerateHierarchy = reGenerateHierarchy;
	}


	public String getWorkFlowProcess() {
		return workFlowProcess;
	}


	public void setWorkFlowProcess(String workFlowProcess) {
		this.workFlowProcess = workFlowProcess;
	}


	public String getAddRollUpNode() {
		return addRollUpNode;
	}


	public void setAddRollUpNode(String addRollUpNode) {
		this.addRollUpNode = addRollUpNode;
	}


	public String getAddDataNode() {
		return addDataNode;
	}


	public void setAddDataNode(String addDataNode) {
		this.addDataNode = addDataNode;
	}


	public String getAddHierarchyNode() {
		return addHierarchyNode;
	}


	public void setAddHierarchyNode(String addHierarchyNode) {
		this.addHierarchyNode = addHierarchyNode;
	}


	public String getEditSelectedNode() {
		return editSelectedNode;
	}


	public void setEditSelectedNode(String editSelectedNode) {
		this.editSelectedNode = editSelectedNode;
	}


	public String getGenerateFact() {
		return generateFact;
	}


	public void setGenerateFact(String generateFact) {
		this.generateFact = generateFact;
	}


	public String getViewHierarchy() {
		return viewHierarchy;
	}


	public void setViewHierarchy(String viewHierarchy) {
		this.viewHierarchy = viewHierarchy;
	}


	public String getRole() {
		return role;
	}


	public void setRole(String role) {
		this.role = role;
	}


	public String getPushOnLevelOut() {
		return pushOnLevelOut;
	}


	public void setPushOnLevelOut(String pushOnLevelOut) {
		this.pushOnLevelOut = pushOnLevelOut;
	}


	public String getPushOnLevelIn() {
		return pushOnLevelIn;
	}


	public void setPushOnLevelIn(String pushOnLevelIn) {
		this.pushOnLevelIn = pushOnLevelIn;
	}


	public String getMoveUP() {
		return moveUP;
	}


	public void setMoveUP(String moveUP) {
		this.moveUP = moveUP;
	}


	public String getMoveDown() {
		return moveDown;
	}


	public void setMoveDown(String moveDown) {
		this.moveDown = moveDown;
	}


	public String getDeleteNode() {
		return deleteNode;
	}


	public void setDeleteNode(String deleteNode) {
		this.deleteNode = deleteNode;
	}


	public String getCopyHierarchy() {
		return copyHierarchy;
	}


	public void setCopyHierarchy(String copyHierarchy) {
		this.copyHierarchy = copyHierarchy;
	}


	public String getRestoreHierarchy() {
		return restoreHierarchy;
	}


	public void setRestoreHierarchy(String restoreHierarchy) {
		this.restoreHierarchy = restoreHierarchy;
	}


	public String getCreateVersionHierarchy() {
		return createVersionHierarchy;
	}


	public void setCreateVersionHierarchy(String createVersionHierarchy) {
		this.createVersionHierarchy = createVersionHierarchy;
	}


	public String getAuditingHierarchy() {
		return auditingHierarchy;
	}


	public void setAuditingHierarchy(String auditingHierarchy) {
		this.auditingHierarchy = auditingHierarchy;
	}
	
	
	public void setAlliconObject(String RoleMap,SecurityBean secBn) {
		System.out.println("Entering: " + new Exception().getStackTrace()[0].getClassName() + "."
	            + new Exception().getStackTrace()[0].getMethodName());
Hashtable roleHT=new Hashtable<>();
	  try{	
		
		  roleHT=gettingAllRoleinHT("onlyOneNode",RoleMap);		  
//			this.addHierarchy=(String)roleHT.get("addHierarchy");
//			this.deleteHierarchy=(String)roleHT.get("deleteHierarchy");
//			this.adminSetting=(String)roleHT.get("adminSetting");
//			this.segmentAdminstration=(String)roleHT.get("segmentAdminstration");
//			this.configCommonSegment=(String)roleHT.get("configCommonSegment");
//			this.generateData=(String)roleHT.get("generateData");
//			this.reGenerateHierarchy=(String)roleHT.get("reGenerateHierarchy");
//			this.workFlowProcess=(String)roleHT.get("workFlowProcess");
//			this.addRollUpNode=(String)roleHT.get("addRollUpNode");
//			this.addDataNode=(String)roleHT.get("addDataNode");
//			this.addHierarchyNode=(String)roleHT.get("addHierarchyNode");
//			this.editSelectedNode=(String)roleHT.get("editSelectedNode");
//			this.generateFact=(String)roleHT.get("generateFact");
//			this.viewHierarchy=(String)roleHT.get("viewHierarchy");
//			this.role=(String)roleHT.get("Role");
//			this.pushOnLevelOut=(String)roleHT.get("pushOnLevelOut");
//			this.pushOnLevelIn=(String)roleHT.get("pushOnLevelIn");
//			this.moveUP=(String)roleHT.get("moveUP");
//			this.moveDown=(String)roleHT.get("moveDown");
//			this.deleteNode=(String)roleHT.get("deleteNode");
//			this.copyHierarchy=(String)roleHT.get("copyHierarchy");
//			this.restoreHierarchy=(String)roleHT.get("restoreHierarchy");
//			this.createVersionHierarchy=(String)roleHT.get("createVersionHierarchy");
//			this.auditingHierarchy=(String)roleHT.get("auditingHierarchy");
		  
		  System.out.println("sestting security button disable/Enable Value roleHT"+roleHT);
		  
		  
		  secBn.setAddHierarchy((String)roleHT.get("addHierarchy"));
		  secBn.setDeleteHierarchy((String)roleHT.get("deleteHierarchy"));
		  secBn.setAddUserHierarchy((String)roleHT.get("addUserHierarchy"));
		  secBn.setAddEditHierarchy((String)roleHT.get("addEditHierarchy"));
		  
		  secBn.setAdminSetting((String)roleHT.get("adminSetting"));
		  secBn.setSegmentAdminstration((String)roleHT.get("segmentAdminstration"));
		  secBn.setConfigCommonSegment((String)roleHT.get("configCommonSegment"));
		  secBn.setGenerateData((String)roleHT.get("generateData"));
		  secBn.setReGenerateHierarchy((String)roleHT.get("reGenerateHierarchy"));
		  secBn.setWorkFlowProcess((String)roleHT.get("workFlowProcess"));
		  secBn.setAddRollUpNode((String)roleHT.get("addRollUpNode"));
		  secBn.setAddDataNode((String)roleHT.get("addDataNode"));
		  secBn.setAddHierarchyNode((String)roleHT.get("addHierarchyNode"));
		  secBn.setEditSelectedNode((String)roleHT.get("editSelectedNode"));
		  secBn.setGenerateFact((String)roleHT.get("generateFact"));
		  secBn.setViewHierarchy((String)roleHT.get("viewHierarchy"));
		  secBn.setRole((String)roleHT.get("Role"));
		  secBn.setPushOnLevelOut((String)roleHT.get("pushOnLevelOut"));
		  secBn.setPushOnLevelIn((String)roleHT.get("pushOnLevelIn"));
		  secBn.setMoveUP((String)roleHT.get("moveUP"));
		  secBn.setMoveDown((String)roleHT.get("moveDown"));
		  secBn.setDeleteNode((String)roleHT.get("deleteNode"));
		  secBn.setCopyHierarchy((String)roleHT.get("copyHierarchy"));
		  secBn.setRestoreHierarchy((String)roleHT.get("restoreHierarchy"));
		  secBn.setCreateVersionHierarchy((String)roleHT.get("createVersionHierarchy"));
		  secBn.setAuditingHierarchy((String)roleHT.get("auditingHierarchy"));
		  secBn.setViewingAudit((String)roleHT.get("viewingAudit"));
		  secBn.setVersionRestore((String)roleHT.get("versionRestore"));
		  secBn.setSavingVersion((String)roleHT.get("savingVersion"));
		  secBn.setConfigSetting((String)roleHT.get("configSetting"));  // code change Menaka 25MAR2014		  
		  secBn.setAuditEnable((String)roleHT.get("auditEnable"));
		  secBn.setAuditDisable((String)roleHT.get("auditDisable"));  // code change Menaka 25MAR2014
		  secBn.setReferenceHierarchy((String)roleHT.get("referenceHierarchy"));
		  secBn.setRiValidationOnhtree((String)roleHT.get("riValidationOnhtree")); // code change Pandian 04APr2014
		  secBn.setFactFullLoad((String)roleHT.get("factFullLoad"));
		  secBn.setFactIncrementalload((String)roleHT.get("factIncrementalload"));
		  secBn.setFactGenerateLoad((String)roleHT.get("factGenerateLoad"));
		  
		  
		// CODE CHANGE PANDIAN 21Mar2014 disable/enable icons
//			FacesContext ctx = FacesContext.getCurrentInstance();
//			ExternalContext extContext = ctx.getExternalContext();
//			Map sessionMap = extContext.getSessionMap();
//			HierarchyBean hierbn = (HierarchyBean) sessionMap.get("hierarchyBean");
//			hierbn.setAuditEnable((String)roleHT.get("auditEnable"));
//			hierbn.setAuditDisable((String)roleHT.get("auditDisable"));
		
	  }catch (Exception e) {
			e.printStackTrace();
		}
		
	 System.out.println("Exiting: " + new Exception().getStackTrace()[0].getClassName() + "."
		            + new Exception().getStackTrace()[0].getMethodName());
		  
		
		
	}
	
	
	public static Hashtable gettingAllRoleinHT(String type,String speciedRole) throws Exception
	{
	System.out.println("Entering: " + new Exception().getStackTrace()[0].getClassName() + "."
		            + new Exception().getStackTrace()[0].getMethodName());
	Hashtable roleHT=new Hashtable<>();
		  try{
			  
		PropUtil prop=new PropUtil();
		String heirRolemapXML=prop.getProperty("HIERARCHY_ROLE_MAP_XML");
		String heirLeveldir=prop.getProperty("HIERARCHY_XML_DIR");
		
		
		Document xmlDoc=Globals.openXMLFile(heirLeveldir, heirRolemapXML);
		Node firastChileNode=xmlDoc.getFirstChild();
		
		
		System.out.println("heirLeveldir:"+heirLeveldir+"heirRolemapXML:"+heirRolemapXML);
		System.out.println("get First child Node Name :"+firastChileNode.getNodeName());
		
		
		if(type.equals("onlyOneNode")){
			
			Node roleNode=Globals.getNodeByAttrValUnderParent(xmlDoc, firastChileNode, "Role", speciedRole);
			roleHT=Globals.getAttributeNameandValHT(roleNode);
		}else{
		
			int roleno=0;
			NodeList firstNodechilds=firastChileNode.getChildNodes();
			for(int child=0; child<firstNodechilds.getLength(); child++){
				Node childnode=firstNodechilds.item(child);
				if(childnode.getNodeType()==Node.ELEMENT_NODE){		
						Hashtable allht=Globals.getAttributeNameandValHT(childnode);
						String role=(String)allht.get("Role");
						if(role.equals("SuperPrivilegeAdmin") || role.equals("AllDisable")){}else{
							
							roleHT.put(roleno, role);
							roleno++;
						}
						
						
					
				}
			}
			
			
			}
			
			System.out.println("Role Map HT:"+roleHT);
		
		  }catch (Exception e) {
			e.printStackTrace();
		}
		
	 System.out.println("Exiting: " + new Exception().getStackTrace()[0].getClassName() + "."
		            + new Exception().getStackTrace()[0].getMethodName());
		  
		
		return roleHT;
		
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
