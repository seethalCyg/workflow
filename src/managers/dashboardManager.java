package managers;

import java.util.ArrayList;

public class dashboardManager {
	
	private String panelName;
	private String properties;
	private String minApprovers;
	private String stageNumber;
	private String minApprovLbleRender;

	public String getMinApprovLbleRender() {
		return minApprovLbleRender;
	}

	public void setMinApprovLbleRender(String minApprovLbleRender) {
		this.minApprovLbleRender = minApprovLbleRender;
	}

	public String getStageNumber() {
		return stageNumber;
	}

	public void setStageNumber(String stageNumber) {
		this.stageNumber = stageNumber;
	}

	public String getMinApprovers() {
		return minApprovers;
	}

	public void setMinApprovers(String minApprovers) {
		this.minApprovers = minApprovers;
	}

	public String getProperties() {
		return properties;
	}

	public void setProperties(String properties) {
		this.properties = properties;
	}

	public String getPanelName() {
		return panelName;
	}

	public void setPanelName(String panelName) {
		this.panelName = panelName;
	}
	
	private ArrayList statusMessageAL=new ArrayList<>();	
	private ArrayList emplyeeNamesAL=new ArrayList<>();
	


	public ArrayList getEmplyeeNamesAL() {
		return emplyeeNamesAL;
	}

	public void setEmplyeeNamesAL(ArrayList emplyeeNamesAL) {
		this.emplyeeNamesAL = emplyeeNamesAL;
	}

	public ArrayList getStatusMessageAL() {
		return statusMessageAL;
	}

	public void setStatusMessageAL(ArrayList statusMessageAL) {
		this.statusMessageAL = statusMessageAL;
	}

	public dashboardManager(String panelName,ArrayList StatusMessageAL,ArrayList emplyeeNamesAL,String properties,String minApprovers,String stageNumber,String minApprovLbleRender){
		this.panelName=panelName;
		this.statusMessageAL=StatusMessageAL;
		this.emplyeeNamesAL=emplyeeNamesAL;
		this.properties=properties;
		this.minApprovers=minApprovers;
		this.stageNumber=stageNumber;
		this.minApprovLbleRender=minApprovLbleRender;
		
	}
}
