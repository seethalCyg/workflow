package model;

import java.util.ArrayList;

public class RuleDataModel {
	private String ruleName = "";
	private String currStageName = "";
	private String trgStageName = "";
	private String selectedJoinType = "";
	private ArrayList<String> joinTypeAL = new ArrayList<String>();
	
	private ArrayList<String> stageNameAL = new ArrayList<String>();
	private ArrayList<RuleAttributeDataModel> attributesAL = new ArrayList<RuleAttributeDataModel>();
	private String attrlength = "";
	private String[] valuesAL = null;
	public RuleDataModel(String ruleName, String currStageName, String trgStageName, String selectedJoinType, ArrayList<String> stageNameAL,
			String[] valuesAL) {
		this.ruleName = ruleName;
		this.currStageName = currStageName;
		this.trgStageName = trgStageName;
		this.selectedJoinType = selectedJoinType;
		this.stageNameAL = stageNameAL;
		this.joinTypeAL.add("And");
		this.joinTypeAL.add("Or");
		this.attrlength = String.valueOf(attributesAL.size());
		this.valuesAL = valuesAL;
	}
	
	public String getRuleName() {
		return ruleName;
	}
	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}
	public String getCurrStageName() {
		return currStageName;
	}
	public void setCurrStageName(String currStageName) {
		this.currStageName = currStageName;
	}
	public String getTrgStageName() {
		return trgStageName;
	}
	public void setTrgStageName(String trgStageName) {
		this.trgStageName = trgStageName;
	}
	public String getSelectedJoinType() {
		return selectedJoinType;
	}
	public void setSelectedJoinType(String selectedJoinType) {
		this.selectedJoinType = selectedJoinType;
	}
	public ArrayList<String> getJoinTypeAL() {
		return joinTypeAL;
	}
	public void setJoinTypeAL(ArrayList<String> joinTypeAL) {
		this.joinTypeAL = joinTypeAL;
	}
	public ArrayList<RuleAttributeDataModel> getAttributesAL() {
		return attributesAL;
	}
	public void setAttributesAL(ArrayList<RuleAttributeDataModel> attributesAL) {
		this.attributesAL = attributesAL;
	}

	public ArrayList<String> getStageNameAL() {
		return stageNameAL;
	}

	public void setStageNameAL(ArrayList<String> stageNameAL) {
		this.stageNameAL = stageNameAL;
	}

	public String getAttrlength() {
		return attrlength;
	}

	public void setAttrlength(String attrlength) {
		this.attrlength = attrlength;
	}

	public String[] getValuesAL() {
		return valuesAL;
	}

	public void setValuesAL(String[] valuesAL) {
		this.valuesAL = valuesAL;
	}

	
}
