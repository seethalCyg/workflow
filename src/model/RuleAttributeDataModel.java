package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;

public class RuleAttributeDataModel {
	private String serialNo = "";
	private String attributeName = "";
	private String attributeType = "";
	private String selectedOperator = "";
	private String attrDataType = "";
	private String attrValue = "";
	private String dateType = "";
	private Date dateValue = null;
	private Hashtable<String, String> operatorsHT = new Hashtable<String, String>();
	public RuleAttributeDataModel(String serialNo, String attributeName, String attributeType, String attrDataType) {
		this.serialNo = serialNo;
		this.attributeName = attributeName;
		this.attributeType = attributeType;
		this.attrDataType = attrDataType;
	}
	
	public RuleAttributeDataModel(String attributeName, String ruleAttrType, Hashtable<String, String> operatorsHT) {
		this.attributeName = attributeName;
		this.attributeType = ruleAttrType;
		this.operatorsHT = operatorsHT;
	}
	
	public RuleAttributeDataModel(String serialNo, String attributeName, String attributeType, String attrDataType, String attrValue, String dateType, 
			Date dateValue) {
		this.serialNo = serialNo;
		this.attributeName = attributeName;
		this.attributeType = attributeType;
		this.attrDataType = attrDataType;
		this.attrValue = attrValue;
		this.dateType = dateType;
		this.dateValue = dateValue;
	}
	
	public String getSerialNo() {
		return serialNo;
	}
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}
	public String getAttributeName() {
		return attributeName;
	}
	public void setAttributeName(String attributeName) {
		this.attributeName = attributeName;
	}
	public String getAttributeType() {
		return attributeType;
	}
	public void setAttributeType(String attributeType) {
		this.attributeType = attributeType;
	}

	public String getSelectedOperator() {
		return selectedOperator;
	}

	public void setSelectedOperator(String selectedOperator) {
		this.selectedOperator = selectedOperator;
	}

	public Hashtable<String, String> getOperatorsHT() {
		return operatorsHT;
	}

	public void setOperatorsHT(Hashtable<String, String> operatorsHT) {
		this.operatorsHT = operatorsHT;
	}

	public String getAttrDataType() {
		return attrDataType;
	}

	public void setAttrDataType(String attrDataType) {
		this.attrDataType = attrDataType;
	}

	public String getAttrValue() {
		return attrValue;
	}

	public void setAttrValue(String attrValue) {
		this.attrValue = attrValue;
	}

	public String getDateType() {
		return dateType;
	}

	public void setDateType(String dateType) {
		this.dateType = dateType;
	}

	public Date getDateValue() {
		return dateValue;
	}

	public void setDateValue(Date dateValue) {
		this.dateValue = dateValue;
	}


}
