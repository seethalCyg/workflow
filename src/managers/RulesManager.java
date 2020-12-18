package managers;

import java.io.BufferedReader;
import java.io.FileReader;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.itextpdf.text.pdf.PdfStructTreeController.returnType;
import com.jniwrapper.Str;

import beans.HeirarchyDataBean;
import beans.HierarchydataBean;
import model.RuleAttributeDataModel;
import model.RuleDataModel;
import utils.Globals;
import utils.PropUtil;

public class RulesManager {
	public static ArrayList<RuleDataModel> getRulesDetailsFromXML(String stageNameFromAddStage, ArrayList<String> stageNameAL, 
			Node workFlowN) throws Exception {
		ArrayList<RuleDataModel> rulesAL = new ArrayList<>();
			
			
//			Node workFlowN=Globals.getNodeByAttrVal(xmlDoc, "Workflow", "Hierarchy_ID", hierID);
			Node stageNd = Globals.getNodeByAttrValUnderParent(workFlowN.getOwnerDocument(), workFlowN, "Stage_Name", stageNameFromAddStage);
			if(stageNd == null) {
				return rulesAL;
			}
			Element stageEle = (Element) stageNd;
			NodeList rulesNdList = stageEle.getElementsByTagName("Rules");
			Node rulesNd = null;
			if(rulesNdList == null || rulesNdList.getLength() <= 0) {
				return rulesAL;
			}
			rulesNd = rulesNdList.item(0);
			NodeList ruleNdList = rulesNd.getChildNodes();
			for(int i=0;i<ruleNdList.getLength();i++) {
				if(ruleNdList.item(i).getNodeType() == Node.ELEMENT_NODE) {
					Element ele = (Element) ruleNdList.item(i);
					String ruleName = ele.getAttribute("RuleName");
					String currStage = ele.getAttribute("CurrentStage");
					String trgStage = ele.getAttribute("TargetStage");
					String ruleAttrVal = ele.getAttribute("RuleAttributesValue");
					String ruleAttrOper = ele.getAttribute("RuleAttributesOperator");
					String joinOper = ele.getAttribute("JoinOperator");
					String[] valuesArr = new String[ruleAttrOper.split("~#~").length*2];
					for(int j=0;j<ruleAttrVal.split("~#~").length;j++) {
						valuesArr[j*2]=ruleAttrVal.split("~#~")[j].split("~").length > 1 ? ruleAttrVal.split("~#~")[j].split("~")[1] : "";
						valuesArr[(j*2)+1]=ruleAttrOper.split("~#~")[j];
					}
					for(String val : valuesArr)
						System.out.println("-=-=-=-=valuesArr-=-=-=-="+val);
					RuleDataModel rdm = new RuleDataModel(ruleName, currStage, trgStage, joinOper, stageNameAL, valuesArr);
					rulesAL.add(rdm);
				}
			}
		return rulesAL;
	}
	
	public static void saveRulesInStage(String hierID, String stageNameFromAddStage, ArrayList<RuleDataModel> rulesAL,
			ArrayList<RuleAttributeDataModel> rulesALTemp) {
		try {
			PropUtil prop=new PropUtil();
			String heirLevelXML=prop.getProperty("HIERARCHY_XML_FILE");
			String heirLeveldir=prop.getProperty("HIERARCHY_XML_DIR");
			Document xmlDoc=Globals.openXMLFile(heirLeveldir, heirLevelXML);
			String hierarchyID=hierID;
			Node workFlowN=Globals.getNodeByAttrVal(xmlDoc, "Workflow", "Hierarchy_ID", hierarchyID);
			Node stageNd = Globals.getNodeByAttrValUnderParent(xmlDoc, workFlowN, "Stage_Name", stageNameFromAddStage);
			System.out.println(hierarchyID+"-=-=-=-=stageNd-=-=-=-=-="+workFlowN+"-=-=-=stageNd-=-=-="+stageNameFromAddStage+"-=-=-=-stageNd=-=-="+stageNd);
			if(stageNd == null) {
				return;
			}
			Element stageEle = (Element) stageNd;
			NodeList rulesNdList = stageEle.getElementsByTagName("Rules");
			Node rulesNd = null;
			if(rulesNdList == null || rulesNdList.getLength() <= 0) {

			}else {
				rulesNd = rulesNdList.item(0);
				stageEle.removeChild(rulesNd);
			}
			rulesNd = xmlDoc.createElement("Rules");
			stageEle.appendChild(rulesNd);
			for(int i=0;i<rulesAL.size();i++) {
				Element ruleEle = xmlDoc.createElement("Rule");
				RuleDataModel rdm = rulesAL.get(i);
				String currStage = rdm.getCurrStageName();
				String trgStage = rdm.getTrgStageName();
				String ruleName = rdm.getRuleName();
				String joinOp = rdm.getSelectedJoinType();
				ruleEle.setAttribute("CurrentStage", currStage);
				ruleEle.setAttribute("TargetStage", trgStage);
				ruleEle.setAttribute("RuleName", ruleName);
				ruleEle.setAttribute("JoinOperator", joinOp);
				String value = "";
				String oper = "";
				int j=0;
				boolean flag = false;
				System.out.println(currStage+"-===trgStage======="+ruleName+"-=-=-=-=-=-=-="+trgStage);
				for(String val : rdm.getValuesAL()) {
					if(j % 2 == 0) {
						System.out.println(j+"-====fghfghfg====="+rulesALTemp.get(j).getAttributeType());
						
						if(rulesALTemp.get(j).getAttributeType().equalsIgnoreCase("Default")) {
							flag = val.equalsIgnoreCase("Use") ? true : false;
						}else
							flag = !val.trim().isEmpty();
							
						if(flag)
							break;
					}
					j++;
				}
				if(!flag)
					continue;
				j=0;
				for(String val : rdm.getValuesAL()) {
					System.out.println(j+"-=========="+rulesALTemp.get(j).getAttributeName());
					if(j % 2 == 0) {
						
						value = value.isEmpty() ? rulesALTemp.get(j).getAttributeName()+"~"+val : value + "~#~" + rulesALTemp.get(j).getAttributeName()+"~"+val;
					}else {
						oper = oper.isEmpty() ? val: oper+"~#~"+val;
					}
					j++;
					
				}
				ruleEle.setAttribute("RuleAttributesValue", value);
				ruleEle.setAttribute("RuleAttributesOperator", oper);
				rulesNd.appendChild(ruleEle);
			}
			Globals.writeXMLFile(xmlDoc, heirLeveldir, heirLevelXML);
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public static void deleteRuleAttributesFromXML(String hierID, String attributeName) throws Exception {
		PropUtil prop=new PropUtil();
		String heirLevelXML=prop.getProperty("HIERARCHY_XML_FILE");
		String heirLeveldir=prop.getProperty("HIERARCHY_XML_DIR");
		Document xmlDoc=Globals.openXMLFile(heirLeveldir, heirLevelXML);
		String hierarchyID=hierID;
		Node workFlowN=Globals.getNodeByAttrVal(xmlDoc, "Workflow", "Hierarchy_ID", hierarchyID);
		Element workflowEle = (Element) workFlowN;
		String ruleAttriName = workflowEle.getAttribute("RuleAttributeName");
		String ruleAttriType = workflowEle.getAttribute("RuleAttributeType");
		String ruleAttriDataType = workflowEle.getAttribute("RuleAttributeDataType");
		String[] opt = ruleAttriName.split("#~#");
		List<String> tempAL = Arrays.asList(opt);
		ArrayList<String> ruleAttrName = new ArrayList<>(tempAL);
		String[] ruleAttriTypeArr = ruleAttriType.split("#~#");
		List<String> ruleAttriTypeAL = Arrays.asList(ruleAttriTypeArr);
		ArrayList<String> ruleAttrType = new ArrayList<>(ruleAttriTypeAL);
		String[] ruleAttriDataTypeArr = ruleAttriDataType.split("#~#");
		List<String> ruleAttriDataTypeAL = Arrays.asList(ruleAttriDataTypeArr);
		ArrayList<String> ruleAttrDataType = new ArrayList<>(ruleAttriDataTypeAL);
		if(!ruleAttrName.contains(attributeName)) {
			return;
		}
		int index = ruleAttrName.indexOf(attributeName);
		System.out.println(index+"-=-=-=-tempAL=-=-=-="+ruleAttrName);
		System.out.println(index+"-=-=-=-=-=-=-="+ruleAttrType);
		ruleAttrType.remove(index);
		ruleAttrDataType.remove(index);
		ruleAttrName.remove(index);
		String[] newRuleAttriNameArr = ruleAttrName.toArray(new String[ruleAttrName.size()]);
		String[] newRuleAttriTypeArr = ruleAttrType.toArray(new String[ruleAttrType.size()]);
		String[] newRuleAttriDataTypeArr = ruleAttrDataType.toArray(new String[ruleAttrDataType.size()]);
		String newRuleAttriName = "";
		String newRuleAttriType = "";
		String newRuleAttriDataType = "";
		for(int i=0;i<newRuleAttriNameArr.length;i++) {
			newRuleAttriName = i == 0 ? newRuleAttriNameArr[i] : newRuleAttriName+"#~#"+newRuleAttriNameArr[i];
			newRuleAttriType = i == 0 ? newRuleAttriTypeArr[i] : newRuleAttriType+"#~#"+newRuleAttriTypeArr[i];
			newRuleAttriDataType = i == 0 ? newRuleAttriDataTypeArr[i] : newRuleAttriDataType+"#~#"+newRuleAttriDataTypeArr[i];
		}
		workflowEle.setAttribute("RuleAttributeName", newRuleAttriName);
		workflowEle.setAttribute("RuleAttributeType", newRuleAttriType);
		workflowEle.setAttribute("RuleAttributeDataType", newRuleAttriDataType);
		Globals.writeXMLFile(xmlDoc, heirLeveldir, heirLevelXML);
	}
	
	public static String saveRuleAttributeInXML(String hierID, String attributeName, String attributeType, String attrDataType) throws Exception {
		String status = "success";
		PropUtil prop=new PropUtil();
		String heirLevelXML=prop.getProperty("HIERARCHY_XML_FILE");
		String heirLeveldir=prop.getProperty("HIERARCHY_XML_DIR");
		Document xmlDoc=Globals.openXMLFile(heirLeveldir, heirLevelXML);
		String hierarchyID=hierID;
		Node workFlowN=Globals.getNodeByAttrVal(xmlDoc, "Workflow", "Hierarchy_ID", hierarchyID);
		Element workflowEle = (Element) workFlowN;
		String ruleAttriName = workflowEle.getAttribute("RuleAttributeName");
		String ruleAttriType = workflowEle.getAttribute("RuleAttributeType");
		String ruleAttriDataType = workflowEle.getAttribute("RuleAttributeDataType");
		String[] opt = ruleAttriName.split("#~#");
		List<String> tempAL = Arrays.asList(opt);
		if(tempAL.contains(attributeName)) {
			status = "Entered attribute name '"+ruleAttriName+"' is already used.";
//			authorizeMsgColor = "red";
			return status;
		}
		ruleAttriName = ruleAttriName.trim().isEmpty() ? attributeName : ruleAttriName+"#~#"+attributeName;
		ruleAttriType = ruleAttriType.trim().isEmpty() ? attributeType : ruleAttriType+"#~#"+attributeType;
		ruleAttriDataType = ruleAttriDataType.trim().isEmpty() ? attrDataType : ruleAttriDataType+"#~#"+attrDataType;
		workflowEle.setAttribute("RuleAttributeName", ruleAttriName);
		workflowEle.setAttribute("RuleAttributeType", ruleAttriType);
		workflowEle.setAttribute("RuleAttributeDataType", ruleAttriDataType);
		Globals.writeXMLFile(xmlDoc, heirLeveldir, heirLevelXML);
		return status;
	}
	
	public static ArrayList<RuleAttributeDataModel> getRulesAttributeFromWF(Node workFlowN) throws Exception {
		ArrayList<RuleAttributeDataModel> ruleAttrDetailsAL = new ArrayList<>();
		
		
		if(workFlowN == null) {
			return ruleAttrDetailsAL;
		}
		Element workflowEle = (Element) workFlowN;
		String ruleAttriName = workflowEle.getAttribute("RuleAttributeName");
		String ruleAttriType = workflowEle.getAttribute("RuleAttributeType");
		String ruleAttriDataType = workflowEle.getAttribute("RuleAttributeDataType");
		String ruleAttriValue = workflowEle.getAttribute("RuleAttributeValue");
		String currentStage = workflowEle.getAttribute("Current_Stage_No");
		if(ruleAttriName == null || ruleAttriName.trim().isEmpty())
			return ruleAttrDetailsAL;
		String[] opt = ruleAttriName.split("#~#");
		List<String> tempAL = Arrays.asList(opt);
		ArrayList<String> ruleAttrName = new ArrayList<>(tempAL);
		String[] ruleAttriTypeArr = ruleAttriType.split("#~#");
		List<String> ruleAttriTypeAL = Arrays.asList(ruleAttriTypeArr);
		ArrayList<String> ruleAttrType = new ArrayList<>(ruleAttriTypeAL);
		String[] ruleAttriDataTypeArr = ruleAttriDataType.split("#~#");
		List<String> ruleAttriDataTypeAL = Arrays.asList(ruleAttriDataTypeArr);
		ArrayList<String> ruleAttrDataType = new ArrayList<>(ruleAttriDataTypeAL);
		ArrayList<String> ruleAttrValue = new ArrayList<>();
		if(!ruleAttriValue.trim().isEmpty()) {
			String[] ruleAttriValueArr = ruleAttriValue.split("#~#");
			List<String> ruleAttriValueAL = Arrays.asList(ruleAttriValueArr);
			ruleAttrValue = new ArrayList<>(ruleAttriValueAL);
		}
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		String date = sdf.format(new Date());
		for(int i=0;i<ruleAttrName.size();i++) {
			String value = ruleAttrValue.size() > i ? ruleAttrValue.get(i) : "";
			RuleAttributeDataModel radm = new RuleAttributeDataModel(String.valueOf(i+1), ruleAttrName.get(i), ruleAttrType.get(i), 
					ruleAttrDataType.get(i), value, ruleAttrDataType.get(i).equalsIgnoreCase("date") ? (value.equalsIgnoreCase("currentDate") || value.isEmpty() ? "CurrentDate" : "ChooseDate") : "", 
							ruleAttrDataType.get(i).equalsIgnoreCase("date") ? (value.equalsIgnoreCase("currentDate") ? new Date() : value.isEmpty() ? new Date() : sdf.parse(value)) : new Date());
			ruleAttrDetailsAL.add(radm);
			System.out.println("-=-=-=-=-=radm-=-=-=-="+radm.getDateValue());
		}
		return ruleAttrDetailsAL;
	}
	
	public static ArrayList<RuleDataModel> getAllRulesFromWF(Node workFlowN) throws Exception {
		ArrayList<RuleDataModel> rulesAL = new ArrayList<>();
		
		
		ArrayList<String> stageNameAL = new ArrayList<>();
		
		NodeList stageNdList = workFlowN.getChildNodes();
		for(int i=0;i<stageNdList.getLength();i++) {
			if(stageNdList.item(i).getNodeType() == Node.ELEMENT_NODE && stageNdList.item(i).getNodeName().equalsIgnoreCase("Stage")) {
				Element stgEle = (Element) stageNdList.item(i);
				stageNameAL.add(stgEle.getAttribute("Stage_Name"));
				NodeList rulesNdList = stgEle.getElementsByTagName("Rules");
				if(rulesNdList.getLength() >= 1) {
					Element rulesEle = (Element)rulesNdList.item(0);
					NodeList ndList = rulesEle.getChildNodes();
					for(int j=0;j<ndList.getLength();j++) {
						if(ndList.item(j).getNodeType() == Node.ELEMENT_NODE) {
							Element ruleEle = (Element)ndList.item(j);
							String[] valuesArr = null;
							String attrOper = ruleEle.getAttribute("RuleAttributesOperator") == null ? "" : ruleEle.getAttribute("RuleAttributesOperator").trim();
							String attrValue = ruleEle.getAttribute("RuleAttributesValue") == null ? "" : ruleEle.getAttribute("RuleAttributesValue").trim();
							
							String[] opers = attrOper.split("~#~");
							String[] vals = attrValue.split("~#~");
							valuesArr = new String[opers.length * 2];
							for(int k=0;k<opers.length;k++) {
								valuesArr[k*2] = vals[k];
								valuesArr[(k*2)+1] = opers[k];
							}
//							Rule CurrentStage="Creator" JoinOperator="And" RuleAttributesOperator="lesserthan~#~equals" RuleAttributesValue="PO Amount~50000~#~PO State~TN" RuleName="Rule #1" TargetStage="Manager"
							RuleDataModel rdm = new RuleDataModel(ruleEle.getAttribute("RuleName"), ruleEle.getAttribute("CurrentStage"), ruleEle.getAttribute("TargetStage"),
									ruleEle.getAttribute("JoinOperator"), stageNameAL, valuesArr);
							rulesAL.add(rdm);
						}
					}
				}
			}
		}
		return rulesAL;
	}
	
	public static Hashtable<String, String> applyRuleIfApplicable(Node workFlowN, String currentStageName, String nextStageNo, ArrayList<String> ruleMailListAL) throws Exception {
		Hashtable<String, String> detailsHT = new Hashtable<>();
		
		Node docNd = workFlowN.getParentNode();
		Element docEle = (Element)docNd;
		Element wfEle = (Element)workFlowN;
		String ruleAttributeName = wfEle.getAttribute("RuleAttributeName");
		String ruleAttributeType = wfEle.getAttribute("RuleAttributeType");
		String ruleAttributeDataType = wfEle.getAttribute("RuleAttributeDataType");
		String ruleAttributeValue = wfEle.getAttribute("RuleAttributeValue");
		if(ruleAttributeValue == null || ruleAttributeValue.isEmpty()) {
			detailsHT.put("Result", "true");
			detailsHT.put("ToStageNo", nextStageNo);
			return detailsHT;
		}
		ArrayList<String> ruleAttrNameAL = new ArrayList<>();
		ArrayList<String> ruleAttrTypeAL = new ArrayList<>();
		ArrayList<String> ruleAttrDataTypeAL = new ArrayList<>();
		ArrayList<String> ruleAttrValAL = new ArrayList<>();
		for(int i=0;i<ruleAttributeName.split("#~#").length;i++) {
			ruleAttrNameAL.add(ruleAttributeName.split("#~#")[i]);
			ruleAttrTypeAL.add(ruleAttributeType.split("#~#")[i]);
			if(ruleAttributeValue.split("#~#").length > i)
			ruleAttrValAL.add(ruleAttributeValue.split("#~#")[i]);
			ruleAttrDataTypeAL.add(ruleAttributeDataType.split("#~#")[i]);
		}
		ArrayList<RuleDataModel> allrulesAL = getAllRulesFromWF(workFlowN);
		boolean ruleAppliedFlag = false;
		boolean prevFlag = false;
		System.out.println(ruleAttributeValue+"-=-=-=-=-=-=-=-=-=-=-=-="+allrulesAL.size());
		String rulesStr = "";
		for(RuleDataModel rdm : allrulesAL) {
			System.out.println(rdm.getCurrStageName()+"-=-=-=-=-=-=-=-=-=-=-=-="+currentStageName);
			if(rdm.getCurrStageName().equalsIgnoreCase(currentStageName)) {
				ruleAppliedFlag = true;
				boolean ruleFlag = true;
				for(int i=0;i<ruleAttrValAL.size();i++) {
					
					String dataType = ruleAttrDataTypeAL.get(i);
					String attrType = ruleAttrTypeAL.get(i);
					String operator = rdm.getValuesAL().length > (i*2)+1 ? rdm.getValuesAL()[(i*2)+1] : "";
					String val2 = rdm.getValuesAL().length > (i*2) ? rdm.getValuesAL()[(i*2)] : "";
					String val2Attr = val2.split("~")[0];
					val2 = val2.split("~").length > 1 ? val2.split("~")[1] : "";
					System.out.println(val2Attr+"-=-=-=-=-=-=val2-=-=-=-=-=-="+attrType+"-=-=-=-=valtttt-=-=-="+ruleAttrValAL.get(ruleAttrNameAL.indexOf(val2Attr)));
					if(val2.isEmpty() && !operator.equalsIgnoreCase("isempty") && !attrType.equalsIgnoreCase("Default"))
						continue;
					if(attrType.equalsIgnoreCase("Custom")) {
						
						String val1 = ruleAttrNameAL.indexOf(val2Attr) >= 0 ? ruleAttrValAL.get(ruleAttrNameAL.indexOf(val2Attr)) : ruleAttrValAL.get(i);
						System.out.println(val1+"-=-=-=-=-=-=-val2=-=-=-=-=-="+val2+"-=-=-=-=-=-=-=-=-=-="+ruleAttrNameAL.indexOf(val2Attr)+"-=-=-=-=-=-="+val2Attr);
						System.out.println(operator+"-=-=-=-=-=-=dataType-=-=-=-=-=-="+dataType);
						prevFlag = checkRules(operator, val1, val2, dataType, prevFlag);
						System.out.println(rdm.getSelectedJoinType()+"-=-=-=-=-=-=-prevFlag=-=-=-=-=-="+prevFlag);
						rulesStr = rulesStr.trim().isEmpty() ? "'"+val1+"' "+operator+" '"+val2+"' = "+(prevFlag ? "Success" : "Failure") : rulesStr+" "+rdm.getSelectedJoinType() + "</br>'"+val1+"' "+operator+" '"+val2+"' = "+(prevFlag ? "Success" : "Failure");
					}else {
						LoginProcessManager lpg = new LoginProcessManager();
						System.out.println(val2Attr+"-=-=-=-=-=-=dataType-=-=-=-=-=-="+ruleAttrNameAL.get(i));
						if(val2Attr.equalsIgnoreCase("Member Email ID")) {
							String val1 = ruleAttrNameAL.indexOf(val2Attr) >= 0 ? ruleAttrValAL.get(ruleAttrNameAL.indexOf(val2Attr)) : ruleAttrValAL.get(i);
							String trgStgNo = getStageNoFromStageName(rdm.getTrgStageName(), workFlowN);
 							Hashtable nextStgDetailsHT = lpg.retriveStageDetailsFromXML(workFlowN, trgStgNo, "");
							Hashtable nextStgEmplyeeDetailsHT = (Hashtable) nextStgDetailsHT.get("EmployeedetHT");
							for (int approv = 0; approv < nextStgEmplyeeDetailsHT.size(); approv++) {
								Hashtable ApproversHT=(Hashtable)nextStgEmplyeeDetailsHT.get(approv);
								String member=(String)ApproversHT.get("empName");
								if(member.equalsIgnoreCase(val1)) {
									prevFlag = true;
									ruleMailListAL.add(member);
									break;
								}
							}
							rulesStr = rulesStr.trim().isEmpty() ? (prevFlag ? "Member '"+val1+"' is available in "+currentStageName : "Member "+val1+" is not available in "+currentStageName) : rulesStr+" "+rdm.getSelectedJoinType() + "</br>"+(prevFlag ? "Member '"+val1+"' is available in "+currentStageName : "Member "+val1+" is not available in "+currentStageName);
						}else if(val2Attr.equalsIgnoreCase("Vendor ID")) {
							String val1 = ruleAttrNameAL.indexOf(val2Attr) >= 0 ? ruleAttrValAL.get(ruleAttrNameAL.indexOf(val2Attr)) : ruleAttrValAL.get(i);
							String trgStgNo = getStageNoFromStageName(rdm.getTrgStageName(), workFlowN);
							Hashtable nextStgDetailsHT = lpg.retriveStageDetailsFromXML(workFlowN, trgStgNo, "");
							Hashtable nextStgEmplyeeDetailsHT = (Hashtable) nextStgDetailsHT.get("EmployeedetHT");
							for (int approv = 0; approv < nextStgEmplyeeDetailsHT.size(); approv++) {
								Hashtable ApproversHT=(Hashtable)nextStgEmplyeeDetailsHT.get(approv);
								String member=(String)ApproversHT.get("empName");
								Hashtable LoginDetailsHT=lpg.getLoginDetails(member, docEle.getAttribute("CustomerKey"));
								String vendorID=LoginDetailsHT.get("Vendor_ID") == null ? "" : (String)LoginDetailsHT.get("Vendor_ID");
								if(vendorID.equalsIgnoreCase(val1) && (!vendorID.trim().isEmpty() && !val1.trim().isEmpty())) {
									prevFlag = true;
									ruleMailListAL.add(member);
									break;
								}
							}
							rulesStr = rulesStr.trim().isEmpty() ? (prevFlag ? "Vendor '"+val1+"' is available in "+currentStageName : "Vendor "+val1+" is not available in "+currentStageName) : rulesStr+" "+rdm.getSelectedJoinType() + "</br>"+(prevFlag ? "Vendor '"+val1+"' is available in "+currentStageName : "Vendor "+val1+" is not available in "+currentStageName);
						}
					}
					if(rdm.getSelectedJoinType().equalsIgnoreCase("And")) {
						if(!prevFlag) {
							ruleFlag = false;
							break;
						}
					}else if(rdm.getSelectedJoinType().equalsIgnoreCase("Or")) {
						if(prevFlag) {
							ruleFlag = true;
							break;
						}else
							ruleFlag = false;
					}
				}
				System.out.println(rdm.getTrgStageName()+"-=-=-=-=-=-=-ruleFlag=-=-=-=-=-="+ruleFlag);
				System.out.println(rdm.getTrgStageName()+"-=-=-=-=-=-=-rulesStr=-=-=-=-=-="+rulesStr);
				if(!ruleFlag){
					ruleMailListAL.clear();
				}
				if(ruleFlag) {
					detailsHT.put("Result", "true");
					detailsHT.put("ToStageNo", getStageNoFromStageName(rdm.getTrgStageName(), workFlowN));
					return detailsHT;
				}
			}
		}
		
		detailsHT.put("Result", ruleAppliedFlag ? "false" : "true");
		detailsHT.put("ToStageNo", nextStageNo);
		detailsHT.put("RulesStr", rulesStr);
		return detailsHT;	
			
	}
	
	public static String getStageNoFromStageName(String stageName, Node wfNd) {
		Hashtable<String, String> detailsHT = getStageNameAndNoInHT(wfNd, "Name");
		return detailsHT.get(stageName);
	}
	
	public static Hashtable<String, String> getStageNameAndNoInHT(Node wfNd, String key) {
		Hashtable<String, String> detailsHT = new Hashtable<>();
		Element docEle = (Element) wfNd;
		NodeList stgNdList = docEle.getElementsByTagName("Stage");
		for(int i=0;i<stgNdList.getLength();i++) {
			if(stgNdList.item(i).getNodeType() == Node.ELEMENT_NODE) {
				Element stgEle = (Element)stgNdList.item(i);
				String stgNo = stgEle.getAttribute("Stage_No");
				String stgName = stgEle.getAttribute("Stage_Name");
				if(key.equals("Number")) {
					detailsHT.put(stgNo, stgName);
				}else {
					detailsHT.put(stgName, stgNo);
				}
				
			}
		}
		return detailsHT;
	}
	
	public static boolean checkRules(String operator, String val1, String val2, String dataType, boolean prevFlag) throws ParseException {
		val1 = val1.trim();
		val2 = val2.trim();
		if(val1.trim().isEmpty() && val2.trim().isEmpty() && !operator.equalsIgnoreCase("isempty")) {
			return prevFlag;
		}
		
		boolean flag = false;
		switch (operator) {
		case "equals":
		{
			if(dataType.equalsIgnoreCase("text") || dataType.equalsIgnoreCase("Custom")) {
				flag = val1.equals(val2);
			}else if(dataType.equalsIgnoreCase("number")) {
				int intVal1 = val1.isEmpty() ? 0 : Integer.parseInt(val1);
				int intVal2 = val2.isEmpty() ? 0 : Integer.parseInt(val2);
				flag = intVal1 == intVal2;
			}else if(dataType.equalsIgnoreCase("currency")) {
				val1 = Character.getType(val1.indexOf(0)) == Character.CURRENCY_SYMBOL ? val1.substring(1).replace(",", "").replace(".", "") : val1.replace(",", "").replace(".", "");
				val2 = Character.getType(val2.indexOf(0)) == Character.CURRENCY_SYMBOL ? val2.substring(1).replace(",", "").replace(".", "") : val2.replace(",", "").replace(".", "");
				int intVal1 = val1.isEmpty() ? 0 : Integer.parseInt(val1);
				int intVal2 = val2.isEmpty() ? 0 : Integer.parseInt(val2);
				flag = intVal1 == intVal2;
			}else if(dataType.equalsIgnoreCase("date")) {
				SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
				Date dateVal1 = val1.isEmpty() ? new Date() : sdf.parse(val1);
				Date dateVal2 = val2.isEmpty() ? new Date() : sdf.parse(val2);
				flag = dateVal1.equals(dateVal2);
			}
			break;
		}
		case "notequals":
		{
			if(dataType.equalsIgnoreCase("text") || dataType.equalsIgnoreCase("Custom")) {
				flag = !val1.equals(val2);
			}else if(dataType.equalsIgnoreCase("number")) {
				int intVal1 = val1.isEmpty() ? 0 : Integer.parseInt(val1);
				int intVal2 = val2.isEmpty() ? 0 : Integer.parseInt(val2);
				flag = intVal1 != intVal2;
			}else if(dataType.equalsIgnoreCase("currency")) {
				val1 = Character.getType(val1.indexOf(0)) == Character.CURRENCY_SYMBOL ? val1.substring(1).replace(",", "").replace(".", "") : val1.replace(",", "").replace(".", "");
				val2 = Character.getType(val2.indexOf(0)) == Character.CURRENCY_SYMBOL ? val2.substring(1).replace(",", "").replace(".", "") : val2.replace(",", "").replace(".", "");
				int intVal1 = val1.isEmpty() ? 0 : Integer.parseInt(val1);
				int intVal2 = val2.isEmpty() ? 0 : Integer.parseInt(val2);
				flag = intVal1 != intVal2;
			}else if(dataType.equalsIgnoreCase("date")) {
				SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
				Date dateVal1 = val1.isEmpty() ? new Date() : sdf.parse(val1);
				Date dateVal2 = val2.isEmpty() ? new Date() : sdf.parse(val2);
				flag = !dateVal1.equals(dateVal2);
			}
			break;
		}
		case "greaterthan":
		{
			if(dataType.equalsIgnoreCase("text") || dataType.equalsIgnoreCase("Custom")) {
				int temp = val1.compareTo(val2);
				flag = temp > 0 ? true : false;
			}else if(dataType.equalsIgnoreCase("number")) {
				int intVal1 = val1.isEmpty() ? 0 : Integer.parseInt(val1);
				int intVal2 = val2.isEmpty() ? 0 : Integer.parseInt(val2);
				flag = intVal1 > intVal2;
			}else if(dataType.equalsIgnoreCase("currency")) {
				val1 = Character.getType(val1.indexOf(0)) == Character.CURRENCY_SYMBOL ? val1.substring(1).replace(",", "").replace(".", "") : val1.replace(",", "").replace(".", "");
				val2 = Character.getType(val2.indexOf(0)) == Character.CURRENCY_SYMBOL ? val2.substring(1).replace(",", "").replace(".", "") : val2.replace(",", "").replace(".", "");
				int intVal1 = val1.isEmpty() ? 0 : Integer.parseInt(val1);
				int intVal2 = val2.isEmpty() ? 0 : Integer.parseInt(val2);
				flag = intVal1 > intVal2;
			}else if(dataType.equalsIgnoreCase("date")) {
				SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
				Date dateVal1 = val1.isEmpty() ? new Date() : sdf.parse(val1);
				Date dateVal2 = val2.isEmpty() ? new Date() : sdf.parse(val2);
				flag = dateVal1.after(dateVal2);
			}
			break;
		}
		case "greaterthanequals":
		{
			if(dataType.equalsIgnoreCase("text") || dataType.equalsIgnoreCase("Custom")) {
				int temp = val1.compareTo(val2);
				flag = temp >= 0 ? true : false;
			}else if(dataType.equalsIgnoreCase("number")) {
				int intVal1 = val1.isEmpty() ? 0 : Integer.parseInt(val1);
				int intVal2 = val2.isEmpty() ? 0 : Integer.parseInt(val2);
				flag = intVal1 > intVal2;
			}else if(dataType.equalsIgnoreCase("currency")) {
				val1 = Character.getType(val1.indexOf(0)) == Character.CURRENCY_SYMBOL ? val1.substring(1).replace(",", "").replace(".", "") : val1.replace(",", "").replace(".", "");
				val2 = Character.getType(val2.indexOf(0)) == Character.CURRENCY_SYMBOL ? val2.substring(1).replace(",", "").replace(".", "") : val2.replace(",", "").replace(".", "");
				int intVal1 = val1.isEmpty() ? 0 : Integer.parseInt(val1);
				int intVal2 = val2.isEmpty() ? 0 : Integer.parseInt(val2);
				flag = intVal1 > intVal2;
			}else if(dataType.equalsIgnoreCase("date")) {
				SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
				Date dateVal1 = val1.isEmpty() ? new Date() : sdf.parse(val1);
				Date dateVal2 = val2.isEmpty() ? new Date() : sdf.parse(val2);
				flag = dateVal1.after(dateVal2) || dateVal1.equals(dateVal2);
			}
			break;
		}
		case "lesserthan":
		{
			if(dataType.equalsIgnoreCase("text") || dataType.equalsIgnoreCase("Custom")) {
				int temp = val1.compareTo(val2);
				flag = temp < 0 ? true : false;
			}else if(dataType.equalsIgnoreCase("number")) {
				int intVal1 = val1.isEmpty() ? 0 : Integer.parseInt(val1);
				int intVal2 = val2.isEmpty() ? 0 : Integer.parseInt(val2);
				flag = intVal1 < intVal2;
			}else if(dataType.equalsIgnoreCase("currency")) {
				val1 = Character.getType(val1.indexOf(0)) == Character.CURRENCY_SYMBOL ? val1.substring(1).replace(",", "").replace(".", "") : val1.replace(",", "").replace(".", "");
				val2 = Character.getType(val2.indexOf(0)) == Character.CURRENCY_SYMBOL ? val2.substring(1).replace(",", "").replace(".", "") : val2.replace(",", "").replace(".", "");
				int intVal1 = val1.isEmpty() ? 0 : Integer.parseInt(val1);
				int intVal2 = val2.isEmpty() ? 0 : Integer.parseInt(val2);
				flag = intVal1 < intVal2;
			}else if(dataType.equalsIgnoreCase("date")) {
				SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
				Date dateVal1 = val1.isEmpty() ? new Date() : sdf.parse(val1);
				Date dateVal2 = val2.isEmpty() ? new Date() : sdf.parse(val2);
				flag = dateVal1.before(dateVal2);
			}
			break;
		}
		case "lesserthanequals":
		{
			if(dataType.equalsIgnoreCase("text") || dataType.equalsIgnoreCase("Custom")) {
				int temp = val1.compareTo(val2);
				flag = temp <= 0 ? true : false;
			}else if(dataType.equalsIgnoreCase("number")) {
				int intVal1 = val1.isEmpty() ? 0 : Integer.parseInt(val1);
				int intVal2 = val2.isEmpty() ? 0 : Integer.parseInt(val2);
				flag = intVal1 <= intVal2;
			}else if(dataType.equalsIgnoreCase("currency")) {
				val1 = Character.getType(val1.indexOf(0)) == Character.CURRENCY_SYMBOL ? val1.substring(1).replace(",", "").replace(".", "") : val1.replace(",", "").replace(".", "");
				val2 = Character.getType(val2.indexOf(0)) == Character.CURRENCY_SYMBOL ? val2.substring(1).replace(",", "").replace(".", "") : val2.replace(",", "").replace(".", "");
				int intVal1 = val1.isEmpty() ? 0 : Integer.parseInt(val1);
				int intVal2 = val2.isEmpty() ? 0 : Integer.parseInt(val2);
				flag = intVal1 <= intVal2;
			}else if(dataType.equalsIgnoreCase("date")) {
				SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
				Date dateVal1 = val1.isEmpty() ? new Date() : sdf.parse(val1);
				Date dateVal2 = val2.isEmpty() ? new Date() : sdf.parse(val2);
				flag = dateVal1.before(dateVal2) || dateVal1.equals(dateVal2);
			}
			break;
		}
		case "between":
		{
			if(dataType.equalsIgnoreCase("text") || dataType.equalsIgnoreCase("Custom")) {
				String valTemp1 = val2.contains("-") ? val2.split("-")[0] : val2;
				String valTemp2 = val2.contains("-") ? val2.split("-")[1] : val2;
				int temp = val1.compareTo(valTemp1);
				int temp1 = val1.compareTo(valTemp2);
				flag = temp >= 0 && temp1 <= 0 ? true : false;
			}else if(dataType.equalsIgnoreCase("number")) {
				String valTemp1 = val2.contains("-") ? val2.split("-")[0] : val2;
				String valTemp2 = val2.contains("-") ? val2.split("-")[1] : val2;
				int intVal1 = val1.isEmpty() ? 0 : Integer.parseInt(val1);
				int intVal2 = valTemp1.isEmpty() ? 0 : Integer.parseInt(valTemp1);
				int intVal3 = valTemp2.isEmpty() ? 0 : Integer.parseInt(valTemp2);
				flag = intVal1 >= intVal2 && intVal1 <= intVal3;
			}else if(dataType.equalsIgnoreCase("currency")) {
				val1 = Character.getType(val1.indexOf(0)) == Character.CURRENCY_SYMBOL ? val1.substring(1).replace(",", "").replace(".", "") : val1.replace(",", "").replace(".", "");
				String valTemp1 = val2.contains("-") ? val2.split("-")[0] : val2;
				String valTemp2 = val2.contains("-") ? val2.split("-")[1] : val2;
				val2 = Character.getType(valTemp1.indexOf(0)) == Character.CURRENCY_SYMBOL ? valTemp1.substring(1).replace(",", "").replace(".", "") : valTemp1.replace(",", "").replace(".", "");
				String val3 = Character.getType(valTemp2.indexOf(0)) == Character.CURRENCY_SYMBOL ? valTemp2.substring(1).replace(",", "").replace(".", "") : valTemp2.replace(",", "").replace(".", "");
				int intVal1 = val1.isEmpty() ? 0 : Integer.parseInt(val1);
				int intVal2 = val2.isEmpty() ? 0 : Integer.parseInt(val2);
				int intVal3 = val3.isEmpty() ? 0 : Integer.parseInt(val3);
				flag = intVal1 <= intVal3 && intVal1 >= intVal2;
			}else if(dataType.equalsIgnoreCase("date")) {
				SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
				String valTemp1 = val2.contains("'-'") ? val2.split("'-'")[0].replace("'", "") : val2.replace("'", "");
				String valTemp2 = val2.contains("'-'") ? val2.split("'-'")[1].replace("'", "") : val2.replace("'", "");
				Date dateVal1 = val1.isEmpty() ? new Date() : sdf.parse(val1);
				Date dateVal2 = valTemp1.isEmpty() ? new Date() : sdf.parse(valTemp1);
				Date dateVal3 = valTemp2.isEmpty() ? new Date() : sdf.parse(valTemp2);
				flag = dateVal1.after(dateVal2) || dateVal1.before(dateVal3);
			}
			break;
		}
		case "isempty":
		{
			flag = val1.isEmpty();
			break;
		}
		default:
			flag = prevFlag;
			break;
		}
		return flag;
	}
	
	public static boolean checkUserIsInCurrStage(String document_ID, String userName) {
		boolean flag1 = false;
		try {
			PropUtil prop = new PropUtil();
			String HIERARCHY_XML_DIR = prop.getProperty("HIERARCHY_XML_DIR");
			String docXmlFileName = prop.getProperty("DOCUMENT_XML_FILE");
	 
			Document docXmlDoc = Globals.openXMLFile(HIERARCHY_XML_DIR, docXmlFileName);
			
			Node docNd = Globals.getNodeByAttrVal(docXmlDoc, "Document", "Document_ID", document_ID);
			
			Element wName=(Element)docNd;
			String crrWfName =wName.getAttribute("WorkflowName");
			NodeList hierNdList=docNd.getChildNodes();			
			for(int i=0;i<hierNdList.getLength();i++){
				if(hierNdList.item(i).getNodeType()==Node.ELEMENT_NODE && hierNdList.item(i).getNodeName().equals("Workflow")){
					
					Node workflowNode=hierNdList.item(i);
					Element wrkEle=(Element)workflowNode;
					String stage_No=wrkEle.getAttribute("Current_Stage_No");
					
					LoginProcessManager logn1 = new LoginProcessManager();
					Hashtable currentstageDetailsHT = logn1.retriveStageDetailsFromXML(workflowNode,
							stage_No, "");

					
					System.out.println(stage_No+":::::::::currentstageDetailsHT::::::::: "+currentstageDetailsHT);
					Hashtable currentemplyeeDetailsHT = (Hashtable) currentstageDetailsHT.get("EmployeedetHT");
					Hashtable curentPropertiesHT = (Hashtable) currentstageDetailsHT.get("PropertiesHT");
					String Properties = (String) curentPropertiesHT.get("Properties");
					Hashtable mssgeDetailsHT = (Hashtable) currentstageDetailsHT.get("MessagedetHT");
					System.out.println("currentemplyeeDetailsHT ::"+currentemplyeeDetailsHT);
					System.out.println("currentemplyeeDetailsHT ::"+currentemplyeeDetailsHT);
					System.out.println("mssgeDetailsHT ::"+mssgeDetailsHT);
					
					
					String finalMsg=(String)mssgeDetailsHT.get("Final");
					System.out.println("finalMsg ::"+finalMsg);
					
					String userStatus="";
					String changePrimaryFilebol = currentstageDetailsHT.get("Change_PrimaryFileOnly") == null ? "" : currentstageDetailsHT.get("Change_PrimaryFileOnly").toString();
					String statusCode4Documents = currentstageDetailsHT.get("StatusCode4Documents") == null ? "" : currentstageDetailsHT.get("StatusCode4Documents").toString();
					
					
					boolean flag = false;
					
					if(Properties.equalsIgnoreCase("Serial"))
					{
						
					for (int approv = 0; approv < currentemplyeeDetailsHT.size(); approv++) {
						Hashtable ApproversHT=new Hashtable();
						ApproversHT=(Hashtable)currentemplyeeDetailsHT.get(approv);
						userStatus=(String)ApproversHT.get("User_Status");
						String member=(String)ApproversHT.get("empName");
						
						System.out.println(userStatus+"%%%%%%%%%%%%%%%finalMsg%%%%%%%%"+finalMsg);
						System.out.println(member+"-=-=-=-=-=-=-=userName-=-=-=-=-="+userName);
						flag = !userStatus.equalsIgnoreCase(finalMsg);
						if(flag)	{
							
							if(member.equalsIgnoreCase(userName)) {
								
								return true;
							}
								
							break;
							
						}
							
					}
					}else {
						
						for (int approv = 0; approv < currentemplyeeDetailsHT.size(); approv++) {
							Hashtable ApproversHT=new Hashtable();
							ApproversHT=(Hashtable)currentemplyeeDetailsHT.get(approv);
							userStatus=(String)ApproversHT.get("User_Status");
							String member=(String)ApproversHT.get("empName");
							
							flag = !userStatus.equalsIgnoreCase(finalMsg);
							if(flag){
								
								if(member.equalsIgnoreCase(userName)) {
									
									return true;
//									break;
								}
							}
						}
					}
				}
			}
			// ***************END***********************
	
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			flag1 = false;
		}
		return flag1;
	}
	static String[] dateFormats = { "MM/dd/yy", "MM/dd/yyyy", "dd/MM/yyyy", "yyyy/MM/dd", "MM-dd-yy", "MM-dd-yyyy", "dd-MM-yyyy", "yyyy-MM-dd"}; 
	public static String checkAndConvertDate(String date, String lastDate) {
		String convertedDate = "";
		boolean flag = false;
		SimpleDateFormat sdf1 = new SimpleDateFormat("MM/dd/yyyy");
		for(int i=0;i<dateFormats.length;i++) {
			SimpleDateFormat sdf = new SimpleDateFormat(dateFormats[i]);
			try {
				System.out.println(dateFormats[i]+"-=-=-=-=-=-=-=-=-=-="+flag+"-=-=-=-=-=fsdfdf-=-=-=-=-="+lastDate);
				Date dt = sdf.parse(date);
				convertedDate = sdf1.format(dt);
				flag = true;
				break;
			}catch (Exception e) {
				// TODO: handle exception
//				flag = false;
//				break;
			}
		}
		System.out.println(convertedDate+"-=-=-=-=-=-=-=-=-=-="+flag+"-=-=-=-=-=-=-=-=-=-="+lastDate);
		if(!flag) {
			try {
				int i = Integer.valueOf(date);
				Date dt = sdf1.parse(lastDate);
				Calendar cal = Calendar.getInstance();
				cal.setTime(dt);
				System.out.println(dt+"-=-=-=-=-=-fddd-=-=-=-="+cal.getTime()+"-=-=-=-=-=-=-=-=-=-="+lastDate);
				cal.add(cal.DATE, i);  
				System.out.println(convertedDate+"-=-=-=-=-dd=-=-=-=-=-="+cal.getTime()+"-=-=-=-=-=-=-=-=-=-="+lastDate);
				convertedDate = sdf1.format(cal.getTime());
			}catch (Exception e) {
				// TODO: handle exception
				return "Error";
			}
		}
		return convertedDate;
	}
	
	public static void performActionOnAttachmentDocument(String selectedStatus, HeirarchyDataBean selectedAttachDocData, 
			String documentId, String userName, String notes) throws Exception {
		
			String actionName = "";
			if(selectedStatus.equalsIgnoreCase("Approved")) {

				actionName="Approve";
			}else if(selectedStatus.equalsIgnoreCase("Completed")) {

				actionName="Approve";
			}else if(selectedStatus.equalsIgnoreCase("Rejected")) {

				actionName="Reject";
			}
			String message = "";
			String subject = "";
			String filePath = "";
			PropUtil prop = new PropUtil();
			String templatesDir = prop.getProperty("TEMPLATE_DIR");
			String dateFor = prop.getProperty("DATE_FORMAT");
			String hierarchyDIR = prop.getProperty("HIERARCHY_XML_DIR");
			String docXmlFileName = prop.getProperty("DOCUMENT_XML_FILE");
			String configFileName=prop.getProperty("HIERARCHY_CONFIG_FILE");
			String hierlink4Mail= prop.getProperty("HIERARCHY_BASE_URL");
			Document configdoc=Globals.openXMLFile(hierarchyDIR, configFileName);
			NodeList ndlist=configdoc.getElementsByTagName("Mail_Configuration");
			Node nd=ndlist.item(0);
			NodeList ndlist1=nd.getChildNodes();
			String mailPassword="";
			String mailSendBy = "";
			for(int i=0;i<ndlist1.getLength();i++){
				Node nd1=ndlist1.item(i);
				if(nd1.getNodeType()==Node.ELEMENT_NODE && nd1.getNodeName().equalsIgnoreCase("Mail_ID_Send_by")){
					mailSendBy=nd1.getTextContent();
				}
				if(nd1.getNodeType()==Node.ELEMENT_NODE && nd1.getNodeName().equalsIgnoreCase("Mail_Password")){
					mailPassword=nd1.getTextContent();
				}
			}
			Element downLinkEle = (Element)configdoc.getElementsByTagName("Download_Link").item(0);
			String downLink = downLinkEle.getAttribute("URL").trim().isEmpty() ? "" : downLinkEle.getAttribute("URL").trim();
			if(actionName.equalsIgnoreCase("Approve")) {
				filePath = templatesDir+"ApproveAttachDoc.html";
				subject = "Your document is approved.";
			}else if(actionName.equalsIgnoreCase("Reject")) {
				filePath = templatesDir+"RejectAttachDoc.html";
				subject = "Your document is rejected.";
			}
			BufferedReader buff = new BufferedReader(new FileReader(filePath));
			String line = "";
			while((line = buff.readLine()) != null) {
				message = message.trim().isEmpty() ? line : message+"\n"+line;
			}
			
			buff.close();
			String attachFilesEncode=URLEncoder.encode(selectedAttachDocData.getAttfileName(), "UTF-8");
			downLink=downLink.replace("$docName$", attachFilesEncode).replace("$docID$", documentId).replace("$username$", "").replace("$downloadFrom$", "mail").replace("$filepath$", "").
					replace("$Type$", "Attachment").replace("$Index$", "");
			SimpleDateFormat sdf = new SimpleDateFormat(dateFor);
			message = message.replace("$DocName$", selectedAttachDocData.getAttfileName()).replace("$LastAccessMember$", userName).
					replace("$CurrDate$", sdf.format(new Date())).replace("$Notes$", notes).replace("$downloadlink$", downLink);
			
			LoginProcessManager lpm = new LoginProcessManager();
			String[] recipients = new String[1];
			recipients[0] = selectedAttachDocData.getAttachUser();
			Document docXmlDoc=Globals.openXMLFile(hierarchyDIR, docXmlFileName);
			Node docNd=Globals.getNodeByAttrVal(docXmlDoc, "Document", "Document_ID", documentId);
			Element docEle = (Element)docNd;
			
			lpm.postMail(recipients, subject, message, mailSendBy, mailPassword, null, hierlink4Mail, docEle.getAttribute("CustomerKey"));
			
			Element attachments = (Element)docEle.getElementsByTagName("Attachments").item(0);
			NodeList ndList = attachments.getChildNodes();
			for(int i=0;i<ndList.getLength();i++) {
				if(ndList.item(i).getNodeType() == Node.ELEMENT_NODE) {
					Element attacEle = (Element)ndList.item(i);
					if(!selectedAttachDocData.getAttfileName().equalsIgnoreCase(attacEle.getAttribute("FileName")))
						continue;
					attacEle.setAttribute("ActionName", actionName);
					attacEle.setAttribute("ActionPerformedBy", userName);
					attacEle.setAttribute("ActionPerformedOn", sdf.format(new Date()));
					attacEle.setAttribute("ActionNotes", notes);
				}
			}
			Globals.writeXMLFile(docXmlDoc, hierarchyDIR, docXmlFileName);
		
	}
}
