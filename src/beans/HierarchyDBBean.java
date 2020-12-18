package beans;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.print.Doc;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;




import utils.Globals;
import utils.PropUtil;

import com.jniwrapper.v;
import com.jniwrapper.win32.jexcel.Application;
import com.jniwrapper.win32.jexcel.ExcelException;
import com.jniwrapper.win32.jexcel.Range;
import com.jniwrapper.win32.jexcel.Worksheet;
@ManagedBean(name = "hierarchyDBBean")
@SessionScoped
public class HierarchyDBBean {

	public String rollUpName=""; 
	private String segmentStatus="";
	private ArrayList statusValueAL = new ArrayList<>();
	private ArrayList segmentListAL = new ArrayList<>();
	private ArrayList periodValuesAL = new ArrayList<>();
	private ArrayList copyPeriodValuesAL = new ArrayList<>();//code change Jayaramu 20FEB14
	private TreeMap segmentAL = new TreeMap<>();
	private ArrayList operator = new ArrayList<>();
	//start code change Jayaramu 20FEB14
	TreeMap selectedMeasureColumnTM=new TreeMap<>();
	TreeMap copySelectedMeasureColumnTM=new TreeMap<>();
	TreeMap selectedcolumnNameTM4Join=new TreeMap<>();
	TreeMap copySelectedcolumnNameTM4Join=new TreeMap<>();
	private ArrayList selectedPeriodValuesAL = new ArrayList<>();
	private ArrayList copySelectedPeriodValuesAL = new ArrayList<>();
	//end code change Jayaramu 20FEB14
	private String selectedsegment="";
	private String datasegValues="";
	private String[] selectedsegmentvalue;
	private String segErrorMsg = "";
	private String segmentName="";
	private String segInfoMsg="";
	private String segmentNameRendered="";
	private String segSelectedsegment=""; 
	private String segselectedcondition="";
	private String segsearchValue="";
	private String[] segSelectedsegmentvalue;
	//start code change Jayaramu 20FEB14
	private String periodvalueOperator="";
	private String filterPeriodValues="";
	private String measureSelectedOperator="";
	private String measureFilterValue="";
	private String selectedMeasureOperator="";
	private String selectedmeasurefilterValue="";
	private String joinclmnselectedOperator="";
	private String joinclmnfilterValue="";
	private String selectedJoinClumnOperator="";
	private String selectedJoinClmnfilterValue="";
	private String[] selectedMeasurecolumnName;
	private String[] selectedjoincolumns;
	private String selectedPeriodvalueOperator="";
	private String filterSelectedPeriodValues="";
	private String[] frwdPeriodValues;
	//end code change Jayaramu 20FEB14
	private boolean displayAddDataBtn=false; // code change Menaka 19FEB2014
	private String selectedType4Data;
	private String dataInfoMsg=""; // Code change Menaka 10MAR2014
	boolean checkbox4genFact = true; // Code change Menaka 17MAR2014
	private String connectionName4Edit = "";
	private String hostName4Edit = "";
	private String portName4Edit = "";
	private String dBName4Edit = "";
	private String conUserName4Edit = "";
	private String conPassword4Edit = "";
	private String connectionType4Edit = "";
	private String testConMsg = "";
	private String tartestConMsg = "";
	private String testConMsg1 = "";
	private String tartestConMsg1 = "";
boolean primaryType = false;
boolean checkbox4Regen = false;
boolean matchCase4AddSeg = false;
boolean matchCase4ChooseSeg = false;	
boolean matchCase4FilterSeg = false;
boolean matchCase4genData = false;
boolean matchCase4GenDataSelPer = false;
boolean matchCase4SegAdmin = false;
boolean matchCase4SegAdminColumns = false;
private String tarTableName4FactJoin = "";
private String selectedColumn4HierDim = "";
private TreeMap column4HierDimTM = new TreeMap<>();
private boolean renderedTargDim = false;
private ArrayList chooseColumn4Join=new ArrayList<>();  
private String operator4chooseColumn4Join = "";
private String srcWarJointextBox = "";
private String flag4CallSrcWarHusPopup = "";
private String combinationType = "";
private TreeMap tableNamesFromFactPopUp = new TreeMap<>();
private boolean matchCase4ExistingTableSelect = false;
private String existingTableMessage = "";
public String getExistingTableMessage() {
	return existingTableMessage;
}
public void setExistingTableMessage(String existingTableMessage) {
	this.existingTableMessage = existingTableMessage;
}
public boolean isMatchCase4ExistingTableSelect() {
	return matchCase4ExistingTableSelect;
}
public void setMatchCase4ExistingTableSelect(
		boolean matchCase4ExistingTableSelect) {
	this.matchCase4ExistingTableSelect = matchCase4ExistingTableSelect;
}
public TreeMap getTableNamesFromFactPopUp() {
	return tableNamesFromFactPopUp;
}
public void setTableNamesFromFactPopUp(TreeMap tableNamesFromFactPopUp) {
	this.tableNamesFromFactPopUp = tableNamesFromFactPopUp;
}

private String joinClumnListLegend = "Create Code Combination in Dimension is Checked, Hierarchy Dimension is loaded";

	public String getJoinClumnListLegend() {
	return joinClumnListLegend;
}
public void setJoinClumnListLegend(String joinClumnListLegend) {
	this.joinClumnListLegend = joinClumnListLegend;
}
	public String getCombinationType() {
	return combinationType;
}
public void setCombinationType(String combinationType) {
	this.combinationType = combinationType;
}
	public String getFlag4CallSrcWarHusPopup() {
	return flag4CallSrcWarHusPopup;
}
public void setFlag4CallSrcWarHusPopup(String flag4CallSrcWarHusPopup) {
	this.flag4CallSrcWarHusPopup = flag4CallSrcWarHusPopup;
}
	public String getSrcWarJointextBox() {
	return srcWarJointextBox;
}
public void setSrcWarJointextBox(String srcWarJointextBox) {
	this.srcWarJointextBox = srcWarJointextBox;
}
	public String getOperator4chooseColumn4Join() {
	return operator4chooseColumn4Join;
}
public void setOperator4chooseColumn4Join(String operator4chooseColumn4Join) {
	this.operator4chooseColumn4Join = operator4chooseColumn4Join;
}
	public ArrayList getChooseColumn4Join() {
	return chooseColumn4Join;
}
public void setChooseColumn4Join(ArrayList chooseColumn4Join) {
	this.chooseColumn4Join = chooseColumn4Join;
}
	public boolean isRenderedTargDim() {
	return renderedTargDim;
}
public void setRenderedTargDim(boolean renderedTargDim) {
	this.renderedTargDim = renderedTargDim;
}
	public TreeMap getColumn4HierDimTM() {
	return column4HierDimTM;
}
public void setColumn4HierDimTM(TreeMap column4HierDimTM) {
	this.column4HierDimTM = column4HierDimTM;
}
	public String getSelectedColumn4HierDim() {
	return selectedColumn4HierDim;
}
public void setSelectedColumn4HierDim(String selectedColumn4HierDim) {
	this.selectedColumn4HierDim = selectedColumn4HierDim;
}
	public String getTarTableName4FactJoin() {
	return tarTableName4FactJoin;
}
public void setTarTableName4FactJoin(String tarTableName4FactJoin) {
	this.tarTableName4FactJoin = tarTableName4FactJoin;
}

boolean render4SegPopup = false;

	public boolean isRender4SegPopup() {
	return render4SegPopup;
}
public void setRender4SegPopup(boolean render4SegPopup) {
	this.render4SegPopup = render4SegPopup;
}
	public boolean isMatchCase4SegAdminColumns() {
	return matchCase4SegAdminColumns;
}
public void setMatchCase4SegAdminColumns(boolean matchCase4SegAdminColumns) {
	this.matchCase4SegAdminColumns = matchCase4SegAdminColumns;
}
	public boolean isMatchCase4SegAdmin() {
	return matchCase4SegAdmin;
}
public void setMatchCase4SegAdmin(boolean matchCase4SegAdmin) {
	this.matchCase4SegAdmin = matchCase4SegAdmin;
}
	public boolean isMatchCase4GenDataSelPer() {
	return matchCase4GenDataSelPer;
}
public void setMatchCase4GenDataSelPer(boolean matchCase4GenDataSelPer) {
	this.matchCase4GenDataSelPer = matchCase4GenDataSelPer;
}
	public boolean isMatchCase4genData() {
	return matchCase4genData;
}
public void setMatchCase4genData(boolean matchCase4genData) {
	this.matchCase4genData = matchCase4genData;
}
	public boolean isMatchCase4FilterSeg() {
	return matchCase4FilterSeg;
}
public void setMatchCase4FilterSeg(boolean matchCase4FilterSeg) {
	this.matchCase4FilterSeg = matchCase4FilterSeg;
}
	public boolean isMatchCase4ChooseSeg() {
	return matchCase4ChooseSeg;
}
public void setMatchCase4ChooseSeg(boolean matchCase4ChooseSeg) {
	this.matchCase4ChooseSeg = matchCase4ChooseSeg;
}
	public boolean isMatchCase4AddSeg() {
	return matchCase4AddSeg;
}
public void setMatchCase4AddSeg(boolean matchCase4AddSeg) {
	this.matchCase4AddSeg = matchCase4AddSeg;
}
	public boolean isCheckbox4Regen() {
	return checkbox4Regen;
}
public void setCheckbox4Regen(boolean checkbox4Regen) {
	this.checkbox4Regen = checkbox4Regen;
}
	public boolean isPrimaryType() {
		return primaryType;
	}
	public void setPrimaryType(boolean primaryType) {
		this.primaryType = primaryType;
	}
	public String getTestConMsg1() {
		return testConMsg1;
	}

	public void setTestConMsg1(String testConMsg1) {
		this.testConMsg1 = testConMsg1;
	}

	public String getTartestConMsg1() {
		return tartestConMsg1;
	}

	public void setTartestConMsg1(String tartestConMsg1) {
		this.tartestConMsg1 = tartestConMsg1;
	}




	public String getTartestConMsg() {
		return tartestConMsg;
	}

	public void setTartestConMsg(String tartestConMsg) {
		this.tartestConMsg = tartestConMsg;
	}

	public String getTestConMsg() {
		return testConMsg;
	}

	public void setTestConMsg(String testConMsg) {
		this.testConMsg = testConMsg;
	}

	public String getConnectionName4Edit() {
		return connectionName4Edit;
	}

	public void setConnectionName4Edit(String connectionName4Edit) {
		this.connectionName4Edit = connectionName4Edit;
	}

	public String getHostName4Edit() {
		return hostName4Edit;
	}

	public void setHostName4Edit(String hostName4Edit) {
		this.hostName4Edit = hostName4Edit;
	}

	public String getPortName4Edit() {
		return portName4Edit;
	}

	public void setPortName4Edit(String portName4Edit) {
		this.portName4Edit = portName4Edit;
	}

	public String getdBName4Edit() {
		return dBName4Edit;
	}

	public void setdBName4Edit(String dBName4Edit) {
		this.dBName4Edit = dBName4Edit;
	}

	public String getConUserName4Edit() {
		return conUserName4Edit;
	}

	public void setConUserName4Edit(String conUserName4Edit) {
		this.conUserName4Edit = conUserName4Edit;
	}

	public String getConPassword4Edit() {
		return conPassword4Edit;
	}

	public void setConPassword4Edit(String conPassword4Edit) {
		this.conPassword4Edit = conPassword4Edit;
	}

	public String getConnectionType4Edit() {
		return connectionType4Edit;
	}

	public void setConnectionType4Edit(String connectionType4Edit) {
		this.connectionType4Edit = connectionType4Edit;
	}
	public boolean isCheckbox4genFact() {
		return checkbox4genFact;
	}

	public void setCheckbox4genFact(boolean checkbox4genFact) {
		this.checkbox4genFact = checkbox4genFact;
	}

	
	public String getDataInfoMsg() {
		return dataInfoMsg;
	}

	public void setDataInfoMsg(String dataInfoMsg) {
		this.dataInfoMsg = dataInfoMsg;
	}

	public ArrayList getCopyPeriodValuesAL() {
		return copyPeriodValuesAL;
	}
	
	public String getSelectedType4Data() {
		return selectedType4Data;
	}


	public void setSelectedType4Data(String selectedType4Data) {
		this.selectedType4Data = selectedType4Data;
	}


	public boolean isDisplayAddDataBtn() {
		return displayAddDataBtn;
	}


	public void setDisplayAddDataBtn(boolean displayAddDataBtn) {
		this.displayAddDataBtn = displayAddDataBtn;
	}


	public void setCopySelectedMeasureColumnTM(TreeMap copySelectedMeasureColumnTM) {
		this.copySelectedMeasureColumnTM = copySelectedMeasureColumnTM;
	}





	
	public TreeMap getSelectedMeasureColumnTM() {
		return selectedMeasureColumnTM;
	}


	public void setSelectedMeasureColumnTM(TreeMap selectedMeasureColumnTM) {
		this.selectedMeasureColumnTM = selectedMeasureColumnTM;
	}




	public TreeMap getSelectedcolumnNameTM4Join() {
		return selectedcolumnNameTM4Join;
	}


	public void setSelectedcolumnNameTM4Join(TreeMap selectedcolumnNameTM4Join) {
		this.selectedcolumnNameTM4Join = selectedcolumnNameTM4Join;
	}




	public String[] getFrwdPeriodValues() {
		return frwdPeriodValues;
	}

	public ArrayList getSelectedPeriodValuesAL() {
		return selectedPeriodValuesAL;
	}


	public void setSelectedPeriodValuesAL(ArrayList selectedPeriodValuesAL) {
		this.selectedPeriodValuesAL = selectedPeriodValuesAL;
	}


	public void setFrwdPeriodValues(String[] frwdPeriodValues) {
		this.frwdPeriodValues = frwdPeriodValues;
	}


	public String getFilterSelectedPeriodValues() {
		return filterSelectedPeriodValues;
	}


	public void setFilterSelectedPeriodValues(String filterSelectedPeriodValues) {
		this.filterSelectedPeriodValues = filterSelectedPeriodValues;
	}


	public String getSelectedPeriodvalueOperator() {
		return selectedPeriodvalueOperator;
	}


	public void setSelectedPeriodvalueOperator(String selectedPeriodvalueOperator) {
		this.selectedPeriodvalueOperator = selectedPeriodvalueOperator;
	}


	public String[] getSelectedjoincolumns() {
		return selectedjoincolumns;
	}


	public void setSelectedjoincolumns(String[] selectedjoincolumns) {
		this.selectedjoincolumns = selectedjoincolumns;
	}



	public String getSelectedJoinClmnfilterValue() {
		return selectedJoinClmnfilterValue;
	}


	public void setSelectedJoinClmnfilterValue(String selectedJoinClmnfilterValue) {
		this.selectedJoinClmnfilterValue = selectedJoinClmnfilterValue;
	}


	public String getSelectedJoinClumnOperator() {
		return selectedJoinClumnOperator;
	}


	public void setSelectedJoinClumnOperator(String selectedJoinClumnOperator) {
		this.selectedJoinClumnOperator = selectedJoinClumnOperator;
	}


	public String getJoinclmnfilterValue() {
		return joinclmnfilterValue;
	}


	public void setJoinclmnfilterValue(String joinclmnfilterValue) {
		this.joinclmnfilterValue = joinclmnfilterValue;
	}


	public String getJoinclmnselectedOperator() {
		return joinclmnselectedOperator;
	}


	public void setJoinclmnselectedOperator(String joinclmnselectedOperator) {
		this.joinclmnselectedOperator = joinclmnselectedOperator;
	}


	public String getSelectedmeasurefilterValue() {
		return selectedmeasurefilterValue;
	}


	public void setSelectedmeasurefilterValue(String selectedmeasurefilterValue) {
		this.selectedmeasurefilterValue = selectedmeasurefilterValue;
	}






	public String getSelectedMeasureOperator() {
		return selectedMeasureOperator;
	}


	public void setSelectedMeasureOperator(String selectedMeasureOperator) {
		this.selectedMeasureOperator = selectedMeasureOperator;
	}


	public String getMeasureFilterValue() {
		return measureFilterValue;
	}


	public void setMeasureFilterValue(String measureFilterValue) {
		this.measureFilterValue = measureFilterValue;
	}


	public String getMeasureSelectedOperator() {
		return measureSelectedOperator;
	}


	public void setMeasureSelectedOperator(String measureSelectedOperator) {
		this.measureSelectedOperator = measureSelectedOperator;
	}


	public String getFilterPeriodValues() {
		return filterPeriodValues;
	}


	public void setFilterPeriodValues(String filterPeriodValues) {
		this.filterPeriodValues = filterPeriodValues;
	}


	public String getPeriodvalueOperator() {
		return periodvalueOperator;
	}


	public void setPeriodvalueOperator(String periodvalueOperator) {
		this.periodvalueOperator = periodvalueOperator;
	}


	public String[] getSelectedMeasurecolumnName() {
		return selectedMeasurecolumnName;
	}


	public void setSelectedMeasurecolumnName(String[] selectedMeasurecolumnName) {
		this.selectedMeasurecolumnName = selectedMeasurecolumnName;
	}





	private TreeMap numberOfSegmentAL = new TreeMap<>();
	public TreeMap getNumberOfSegmentAL() {
		return numberOfSegmentAL;
	}

	public void setNumberOfSegmentAL(TreeMap numberOfSegmentAL) {
		this.numberOfSegmentAL = numberOfSegmentAL;
	}





	private String selectedOperator="";
	private String filterValue = "";
	public String getFilterValue() {
		return filterValue;
	}


	public void setFilterValue(String filterValue) {
		this.filterValue = filterValue;
	}


	public String getSelectedOperator() {
		return selectedOperator;
	}


	public void setSelectedOperator(String selectedOperator) {
		this.selectedOperator = selectedOperator;
	}


	public ArrayList getOperator() {
		
		operator = new ArrayList<>();
		operator.add("Starts With");
		operator.add("Ends With");
		operator.add("Contains");
		operator.add("All Values");
		
		
		return operator;
	}


	public void setOperator(ArrayList operator) {
		this.operator = operator;
	}





	public String[] getSegSelectedsegmentvalue() {
		return segSelectedsegmentvalue;
	}


	public void setSegSelectedsegmentvalue(String[] segSelectedsegmentvalue) {
		this.segSelectedsegmentvalue = segSelectedsegmentvalue;
	}


	public String getSegsearchValue() {
		return segsearchValue;
	}


	public void setSegsearchValue(String segsearchValue) {
		this.segsearchValue = segsearchValue;
	}


	public String getSegselectedcondition() {
		return segselectedcondition;
	}


	public void setSegselectedcondition(String segselectedcondition) {
		this.segselectedcondition = segselectedcondition;
	}


	public String getSegSelectedsegment() {
		return segSelectedsegment;
	}


	public void setSegSelectedsegment(String segSelectedsegment) {
		this.segSelectedsegment = segSelectedsegment;
	}


	public String getSegmentNameRendered() {
		return segmentNameRendered;
	}


	public void setSegmentNameRendered(String segmentNameRendered) {
		this.segmentNameRendered = segmentNameRendered;
	}


	public String getSegInfoMsg() {
		return segInfoMsg;
	}


	public void setSegInfoMsg(String segInfoMsg) {
		this.segInfoMsg = segInfoMsg;
	}


	public String getSegmentName() {
		return segmentName;
	}


	public void setSegmentName(String segmentName) {
		this.segmentName = segmentName;
	}


	public String getSegErrorMsg() {
		return segErrorMsg;
	}


	public void setSegErrorMsg(String segErrorMsg) {
		this.segErrorMsg = segErrorMsg;
	}


	public String[] getSelectedsegmentvalue() {
		return selectedsegmentvalue;
	}


	public void setSelectedsegmentvalue(String[] selectedsegmentvalue) {
		this.selectedsegmentvalue = selectedsegmentvalue;
	}

	

	private String flag4AddData="";
	private String message="";
	private String selectedcondition="";
	private String searchValue="";
	private String searchBetweenValue="";
	private String segSearchbetween1 = "";
	private String segSearchbetween2 = "";
	private String genDataTitleMessage="";
	private String factMessageColor="blue";
	public String getFactMessageColor() {
		return factMessageColor;
	}

	public void setFactMessageColor(String factMessageColor) {
		this.factMessageColor = factMessageColor;
	}

	public String getGenDataTitleMessage() {
		return genDataTitleMessage;
	}

	public void setGenDataTitleMessage(String genDataTitleMessage) {
		this.genDataTitleMessage = genDataTitleMessage;
	}

	public String getSegSearchbetween2() {
		return segSearchbetween2;
	}

	public void setSegSearchbetween2(String segSearchbetween2) {
		this.segSearchbetween2 = segSearchbetween2;
	}

	public String getSegSearchbetween1() {
		return segSearchbetween1;
	}

	public void setSegSearchbetween1(String segSearchbetween1) {
		this.segSearchbetween1 = segSearchbetween1;
	}

	public String getSearchBetweenValue() {
		return searchBetweenValue;
	}


	public void setSearchBetweenValue(String searchBetweenValue) {
		this.searchBetweenValue = searchBetweenValue;
	}


	public String getSearchValue() {
		return searchValue;
	}


	public void setSearchValue(String searchValue) {
		this.searchValue = searchValue;
	}


	public String getSelectedcondition() {
		return selectedcondition;
	}


	public void setSelectedcondition(String selectedcondition) {
		this.selectedcondition = selectedcondition;
	}




	private ArrayList conditionAL = new ArrayList<>();
	
	public ArrayList getConditionAL() {
		
		conditionAL  = new ArrayList<>();		

		conditionAL.add("Starts With");
		conditionAL.add("Ends With");
		conditionAL.add("In");
		conditionAL.add("Contains");
//		conditionAL.add("Wildcards");
//		conditionAL.add("Between");
//		conditionAL.add("Not Between");
		
		
		return conditionAL;
	}


	public void setConditionAL(ArrayList conditionAL) {
		this.conditionAL = conditionAL;
	}


	public String getMessage() {
		return message;
	}


	public void setMessage(String message) {
		this.message = message;
	}




	HeirarchyDataBean hdb = null;


	public String getFlag4AddData() {
		return flag4AddData;
	}


	public void setFlag4AddData(String flag4AddData) {
		this.flag4AddData = flag4AddData;
	}



	public String getDatasegValues() {
		return datasegValues;
	}


	public void setDatasegValues(String datasegValues) {
		this.datasegValues = datasegValues;
	}


	public String getSelectedsegment() {
		return selectedsegment;
	}


	public void setSelectedsegment(String selectedsegment) {
		this.selectedsegment = selectedsegment;
	}


	public TreeMap getSegmentAL() {
		try{
			FacesContext ctx = FacesContext.getCurrentInstance();
			ExternalContext extContext = ctx.getExternalContext();
			Map sessionMap = extContext.getSessionMap();
			HierarchyBean hryb = (HierarchyBean) sessionMap.get("hierarchyBean");
		PropUtil prop=new PropUtil();
		String dir=prop.getProperty("HIERARCHY_XML_DIR");
		String hierarchyFileName = prop.getProperty("HIERARCHY_CONFIG_FILE");
		String hierarchyLevelFileName = prop.getProperty("HIERARCHY_XML_FILE");
		Document doc = Globals.openXMLFile(dir, hierarchyFileName);
		Document hierdoc = Globals.openXMLFile(dir, hierarchyLevelFileName);
		NodeList ndlist1=doc.getElementsByTagName("No_Of_segments");
		segmentAL = new TreeMap<>(); 
//		Node rootnode = Globals.getNodeByAttrVal(hierdoc, "Segments", "ID", hryb.getHierarchy_ID());
//		if()
		Node hierNd = Globals.getNodeByAttrVal(hierdoc, "Hierarchy_Level", "Hierarchy_ID", hryb.getHierarchy_ID());
		if(hierNd!=null){
		NodeList hierNdList = hierNd.getChildNodes();
		boolean oldCodeChange=false;
		/*ArrdisplayAl*/
		int k=1;
		for(int i=0;i<hierNdList.getLength();i++){
			if(hierNdList.item(i).getNodeType() == Node.ELEMENT_NODE && hierNdList.item(i).getNodeName().equalsIgnoreCase("Segment_SQLS")){
				oldCodeChange=false;
//				System.out.println("1111111====>>>"+oldCodeChange);
//				if(hierNdList.item(i).getNodeName().equalsIgnoreCase("Segment_Trigger_SQL")){
//					System.out.println("222222====>>>"+oldCodeChange);
					Node segTriggerNd=hierNdList.item(i);
					NodeList segTriggerNdList=segTriggerNd.getChildNodes();
					for(int j=0;j<segTriggerNdList.getLength();j++){
						if(segTriggerNdList.item(j).getNodeName().contains("Segment_SQL") && segTriggerNdList.item(j).getNodeType() == Node.ELEMENT_NODE){
//							columnName=segTriggerNdList.item(j).getTextContent();
							segmentAL.put(segTriggerNdList.item(j).getAttributes().getNamedItem("Display_Name").getNodeValue(),"Segment_"+k);
							k++;
						}
						
					}
					break;
//				}
			}else{
				oldCodeChange=true;
			}
		}
		
		
//		String noOfSegments = Globals.getAttrVal4AttrName(hierNd, "Number_of_Segment");
		String noOfSegments = ndlist1.item(0).getTextContent();
		if(noOfSegments.equals("")){
			noOfSegments="0";
		}
		if(oldCodeChange){
				
		for(int i = 1;i <= Integer.parseInt(noOfSegments); i++){
			
			segmentAL.put("Segment "+i,"Segment_"+i);
		}
		}
		}
		}catch(Exception e){
			e.printStackTrace();
		}
		return segmentAL;
	}
	String selectedsegment1 = "";

	public String getSelectedsegment1() {
		return selectedsegment1;
	}
	public void setSelectedsegment1(String selectedsegment1) {
		this.selectedsegment1 = selectedsegment1;
	}
	public void setSegmentAL(TreeMap segmentAL) {
		this.segmentAL = segmentAL;
	}

	TreeMap connectionNameTM = new TreeMap<>();
	public TreeMap getConnectionNameTM() {
		return connectionNameTM;
	}

	public void setConnectionNameTM(TreeMap connectionNameTM) {
		this.connectionNameTM = connectionNameTM;
	}

	String connectionName = "";


	public String getConnectionName() {
		return connectionName;
	}

	public void setConnectionName(String connectionName) {
		this.connectionName = connectionName;
	}





	private String periodValue="";
	private ArrayList selectedPeriodValues=new ArrayList<>();   // code change Menaka 14FEB2014
	



	public ArrayList getSelectedPeriodValues() {
		return selectedPeriodValues;
	}


	public void setSelectedPeriodValues(ArrayList selectedPeriodValues) {
		this.selectedPeriodValues = selectedPeriodValues;
	}




	private ArrayList selectjoincolumn=new ArrayList<>();   // code change Menaka 14FEB2014


	public ArrayList getSelectjoincolumn() {
		return selectjoincolumn;
	}


	public void setSelectjoincolumn(ArrayList selectjoincolumn) {
		this.selectjoincolumn = selectjoincolumn;
	}


	public String getPeriodValue() {
		return periodValue;
	}


	public void setPeriodValue(String periodValue) {
		this.periodValue = periodValue;
	}


	public ArrayList getPeriodValuesAL() {
		return periodValuesAL;
	}


	public void setPeriodValuesAL(ArrayList periodValuesAL) {
		this.periodValuesAL = periodValuesAL;
	}




	String hierarchyXMLFileName = "HierachyLevel.xml";
	private String joincolumn="";
	private String periodcolumn="";
	
	public String getPeriodcolumn() {
		return periodcolumn;
	}


	public void setPeriodcolumn(String periodcolumn) {
		this.periodcolumn = periodcolumn;
	}


	public String getJoincolumn() {
		return joincolumn;
	}


	public void setJoincolumn(String joincolumn) {
		this.joincolumn = joincolumn;
	}


	public String getHierarchyXMLFileName() {
		return hierarchyXMLFileName;
	}


	public void setHierarchyXMLFileName(String hierarchyXMLFileName) {
		this.hierarchyXMLFileName = hierarchyXMLFileName;
	}


	public ArrayList getSegmentListAL() {
		return segmentListAL;
	}


	public void setSegmentListAL(ArrayList segmentListAL) {
		this.segmentListAL = segmentListAL;
	}


	public ArrayList getStatusValueAL() {
		
		statusValueAL = new ArrayList<>();
		statusValueAL.add("Draft");
		statusValueAL.add("Approved");
		statusValueAL.add("Published");
		statusValueAL.add("Disabled");
		
		
		return statusValueAL;
	}


	public void setStatusValueAL(ArrayList statusValueAL) {
		this.statusValueAL = statusValueAL;
	}


	public String getSegmentStatus() {
		return segmentStatus;
	}


	public void setSegmentStatus(String segmentStatus) {
		this.segmentStatus = segmentStatus;
	}




	private ArrayList segment1ValueM = new ArrayList();
	private ArrayList segmentValues4dataPopupAL = new ArrayList();
	private ArrayList segmentValues4SegPopupAL = new ArrayList();
	private ArrayList copySegmentValues4SegPopupAL = new ArrayList();  // code change Menaka 15MAR2014
	private String width4Horizontal = "width:185px";
	private String tableWidth4Horizontal = "width:185px";
	
	public String getTableWidth4Horizontal() {
		return tableWidth4Horizontal;
	}
	public void setTableWidth4Horizontal(String tableWidth4Horizontal) {
		this.tableWidth4Horizontal = tableWidth4Horizontal;
	}
	public String getWidth4Horizontal() {
		return width4Horizontal;
	}
	public void setWidth4Horizontal(String width4Horizontal) {
		this.width4Horizontal = width4Horizontal;
	}
	public ArrayList getSegmentValues4SegPopupAL() {
		return segmentValues4SegPopupAL;
	}


	public void setSegmentValues4SegPopupAL(ArrayList segmentValues4SegPopupAL) {
		this.segmentValues4SegPopupAL = segmentValues4SegPopupAL;
	}


	public ArrayList getSegmentValues4dataPopupAL() {
		return segmentValues4dataPopupAL;
	}


	public void setSegmentValues4dataPopupAL(ArrayList segmentValues4dataPopupAL) {
		this.segmentValues4dataPopupAL = segmentValues4dataPopupAL;
	}


	public ArrayList getSegment1ValueM() {
		return segment1ValueM;
	}


	public void setSegment1ValueM(ArrayList segment1ValueM) {
		this.segment1ValueM = segment1ValueM;
	}


	public ArrayList getSegment2ValueM() {
		return segment2ValueM;
	}


	public void setSegment2ValueM(ArrayList segment2ValueM) {
		this.segment2ValueM = segment2ValueM;
	}


	public ArrayList getSegment3ValueM() {
		return segment3ValueM;
	}


	public void setSegment3ValueM(ArrayList segment3ValueM) {
		this.segment3ValueM = segment3ValueM;
	}


	public ArrayList getSegment4ValueM() {
		return segment4ValueM;
	}


	public void setSegment4ValueM(ArrayList segment4ValueM) {
		this.segment4ValueM = segment4ValueM;
	}


	public ArrayList getSegment5ValueM() {
		return segment5ValueM;
	}


	public void setSegment5ValueM(ArrayList segment5ValueM) {
		this.segment5ValueM = segment5ValueM;
	}


	public ArrayList getSegment6ValueM() {
		return segment6ValueM;
	}


	public void setSegment6ValueM(ArrayList segment6ValueM) {
		this.segment6ValueM = segment6ValueM;
	}


	public ArrayList getSegment7ValueM() {
		return segment7ValueM;
	}


	public void setSegment7ValueM(ArrayList segment7ValueM) {
		this.segment7ValueM = segment7ValueM;
	}


	public ArrayList getSegment8ValueM() {
		return segment8ValueM;
	}


	public void setSegment8ValueM(ArrayList segment8ValueM) {
		this.segment8ValueM = segment8ValueM;
	}


	public ArrayList getSegment9ValueM() {
		return segment9ValueM;
	}


	public void setSegment9ValueM(ArrayList segment9ValueM) {
		this.segment9ValueM = segment9ValueM;
	}


	public ArrayList getSegment10ValueM() {
		return segment10ValueM;
	}


	public void setSegment10ValueM(ArrayList segment10ValueM) {
		this.segment10ValueM = segment10ValueM;
	}




	private ArrayList segment2ValueM = new ArrayList();
	private ArrayList segment3ValueM = new ArrayList();
	private ArrayList segment4ValueM = new ArrayList();
	private ArrayList segment5ValueM = new ArrayList();
	private ArrayList segment6ValueM = new ArrayList();
	private ArrayList segment7ValueM = new ArrayList();
	private ArrayList segment8ValueM = new ArrayList();
	private ArrayList segment9ValueM = new ArrayList();
	private ArrayList segment10ValueM = new ArrayList();
	
	
	// code change Menaka 11FEB2014
	public String server="";
	public String getServer() {
		return server;
	}

	private String segment1SelectedValue= "";
	private String segment2SelectedValue= "";
	private String segment3SelectedValue= "";
	private String segment4SelectedValue= "";
	private String segment5SelectedValue= "";
	private String segment6SelectedValue= "";
	private String segment7SelectedValue= "";
	private String segment8SelectedValue= "";
	private String segment9SelectedValue= "";
	private String segment10SelectedValue= "";
	
	private String checkexistsValue1="";
	private String checkexistsValue2="";
	private String checkexistsValue3="";
	private String checkexistsValue4="";
	private String checkexistsValue5="";
	private String checkexistsValue6="";
	private String checkexistsValue7="";
	private String checkexistsValue8="";
	private String checkexistsValue9="";
	private String checkexistsValue10="";
	private String msg4SrcTgtFact = "";
	private String msg4JoinColumnList = "";
	private String msg4SrcWarJoin = "";
	private String msg4WarHouseLoad = "";
	private String msg4IncrementLoad = "";
	public String getMsg4IncrementLoad() {
		return msg4IncrementLoad;
	}
	public void setMsg4IncrementLoad(String msg4IncrementLoad) {
		this.msg4IncrementLoad = msg4IncrementLoad;
	}
	public String getMsg4WarHouseLoad() {
		return msg4WarHouseLoad;
	}
	public void setMsg4WarHouseLoad(String msg4WarHouseLoad) {
		this.msg4WarHouseLoad = msg4WarHouseLoad;
	}
	public String getMsg4SrcWarJoin() {
		return msg4SrcWarJoin;
	}
	public void setMsg4SrcWarJoin(String msg4SrcWarJoin) {
		this.msg4SrcWarJoin = msg4SrcWarJoin;
	}
	public String getMsg4JoinColumnList() {
		return msg4JoinColumnList;
	}
	public void setMsg4JoinColumnList(String msg4JoinColumnList) {
		this.msg4JoinColumnList = msg4JoinColumnList;
	}
	public String getMsg4SrcTgtFact() {
		return msg4SrcTgtFact;
	}
	public void setMsg4SrcTgtFact(String msg4SrcTgtFact) {
		this.msg4SrcTgtFact = msg4SrcTgtFact;
	}
	public String getCheckexistsValue1() {
		return checkexistsValue1;
	}


	public void setCheckexistsValue1(String checkexistsValue1) {
		this.checkexistsValue1 = checkexistsValue1;
	}


	public String getCheckexistsValue2() {
		return checkexistsValue2;
	}


	public void setCheckexistsValue2(String checkexistsValue2) {
		this.checkexistsValue2 = checkexistsValue2;
	}


	public String getCheckexistsValue3() {
		return checkexistsValue3;
	}


	public void setCheckexistsValue3(String checkexistsValue3) {
		this.checkexistsValue3 = checkexistsValue3;
	}


	public String getCheckexistsValue4() {
		return checkexistsValue4;
	}


	public void setCheckexistsValue4(String checkexistsValue4) {
		this.checkexistsValue4 = checkexistsValue4;
	}


	public String getCheckexistsValue5() {
		return checkexistsValue5;
	}


	public void setCheckexistsValue5(String checkexistsValue5) {
		this.checkexistsValue5 = checkexistsValue5;
	}


	public String getCheckexistsValue6() {
		return checkexistsValue6;
	}


	public void setCheckexistsValue6(String checkexistsValue6) {
		this.checkexistsValue6 = checkexistsValue6;
	}


	public String getCheckexistsValue7() {
		return checkexistsValue7;
	}


	public void setCheckexistsValue7(String checkexistsValue7) {
		this.checkexistsValue7 = checkexistsValue7;
	}


	public String getCheckexistsValue8() {
		return checkexistsValue8;
	}


	public void setCheckexistsValue8(String checkexistsValue8) {
		this.checkexistsValue8 = checkexistsValue8;
	}


	public String getCheckexistsValue9() {
		return checkexistsValue9;
	}


	public void setCheckexistsValue9(String checkexistsValue9) {
		this.checkexistsValue9 = checkexistsValue9;
	}


	public String getCheckexistsValue10() {
		return checkexistsValue10;
	}


	public void setCheckexistsValue10(String checkexistsValue10) {
		this.checkexistsValue10 = checkexistsValue10;
	}


	private String segment1SelectValue;
	private String segment2SelectValue;
	private String segment3SelectValue;
	private String segment4SelectValue;
	private String segment5SelectValue;
	private String segment6SelectValue;
	private String segment7SelectValue;
	private String segment8SelectValue;
	private String segment9SelectValue;
	private String segment10SelectValue;
	public String getSegment1SelectValue() {
		return segment1SelectValue;
	}


	public void setSegment1SelectValue(String segment1SelectValue) {
		this.segment1SelectValue = segment1SelectValue;
	}


	public String getSegment2SelectValue() {
		return segment2SelectValue;
	}


	public void setSegment2SelectValue(String segment2SelectValue) {
		this.segment2SelectValue = segment2SelectValue;
	}


	public String getSegment3SelectValue() {
		return segment3SelectValue;
	}


	public void setSegment3SelectValue(String segment3SelectValue) {
		this.segment3SelectValue = segment3SelectValue;
	}


	public String getSegment4SelectValue() {
		return segment4SelectValue;
	}


	public void setSegment4SelectValue(String segment4SelectValue) {
		this.segment4SelectValue = segment4SelectValue;
	}


	public String getSegment5SelectValue() {
		return segment5SelectValue;
	}


	public void setSegment5SelectValue(String segment5SelectValue) {
		this.segment5SelectValue = segment5SelectValue;
	}


	public String getSegment6SelectValue() {
		return segment6SelectValue;
	}


	public void setSegment6SelectValue(String segment6SelectValue) {
		this.segment6SelectValue = segment6SelectValue;
	}


	public String getSegment7SelectValue() {
		return segment7SelectValue;
	}


	public void setSegment7SelectValue(String segment7SelectValue) {
		this.segment7SelectValue = segment7SelectValue;
	}


	public String getSegment8SelectValue() {
		return segment8SelectValue;
	}


	public void setSegment8SelectValue(String segment8SelectValue) {
		this.segment8SelectValue = segment8SelectValue;
	}


	public String getSegment9SelectValue() {
		return segment9SelectValue;
	}


	public void setSegment9SelectValue(String segment9SelectValue) {
		this.segment9SelectValue = segment9SelectValue;
	}


	public String getSegment10SelectValue() {
		return segment10SelectValue;
	}


	public void setSegment10SelectValue(String segment10SelectValue) {
		this.segment10SelectValue = segment10SelectValue;
	}




	
	


	
	private Hashtable<Integer, String> segmentValuesHT = new Hashtable<Integer, String>();
	public Hashtable<Integer, String> getSegmentValuesHT() {
		return segmentValuesHT;
	}


	public void setSegmentValuesHT(Hashtable<Integer, String> segmentValuesHT) {
		this.segmentValuesHT = segmentValuesHT;
	}


	public String getSegment1SelectedValue() {
		return segment1SelectedValue;
	}


	public void setSegment1SelectedValue(String segment1SelectedValue) {
		this.segment1SelectedValue = segment1SelectedValue;
	}


	public String getSegment2SelectedValue() {
		return segment2SelectedValue;
	}


	public void setSegment2SelectedValue(String segment2SelectedValue) {
		this.segment2SelectedValue = segment2SelectedValue;
	}


	public String getSegment3SelectedValue() {
		return segment3SelectedValue;
	}


	public void setSegment3SelectedValue(String segment3SelectedValue) {
		this.segment3SelectedValue = segment3SelectedValue;
	}


	public String getSegment4SelectedValue() {
		return segment4SelectedValue;
	}


	public void setSegment4SelectedValue(String segment4SelectedValue) {
		this.segment4SelectedValue = segment4SelectedValue;
	}


	public String getSegment5SelectedValue() {
		return segment5SelectedValue;
	}


	public void setSegment5SelectedValue(String segment5SelectedValue) {
		this.segment5SelectedValue = segment5SelectedValue;
	}


	public String getSegment6SelectedValue() {
		return segment6SelectedValue;
	}


	public void setSegment6SelectedValue(String segment6SelectedValue) {
		this.segment6SelectedValue = segment6SelectedValue;
	}


	public String getSegment7SelectedValue() {
		return segment7SelectedValue;
	}

	public void setSegment7SelectedValue(String segment7SelectedValue) {
		this.segment7SelectedValue = segment7SelectedValue;
	}


	public String getSegment8SelectedValue() {
		return segment8SelectedValue;
	}


	public void setSegment8SelectedValue(String segment8SelectedValue) {
		this.segment8SelectedValue = segment8SelectedValue;
	}


	public String getSegment9SelectedValue() {
		return segment9SelectedValue;
	}


	public void setSegment9SelectedValue(String segment9SelectedValue) {
		this.segment9SelectedValue = segment9SelectedValue;
	}


	public String getSegment10SelectedValue() {
		return segment10SelectedValue;
	}


	public void setSegment10SelectedValue(String segment10SelectedValue) {
		this.segment10SelectedValue = segment10SelectedValue;
	}


	
	int segmentRenderValue=0;
	private int noOfSegments=0;
	


	public int getNoOfSegments() {
		return noOfSegments;
	}


	public void setNoOfSegments(int noOfSegments) {
		this.noOfSegments = noOfSegments;
	}


	public int getSegmentRenderValue() {
		return segmentRenderValue;
	}


	public void setSegmentRenderValue(int segmentRenderValue) {
		this.segmentRenderValue = segmentRenderValue;
	}


	public void setServer(String server) {
		this.server = server;
	}


	public String getPort() {
		return port;
	}


	public void setPort(String port) {
		this.port = port;
	}


	public String getInstance() {
		return instance;
	}


	public void setInstance(String instance) {
		this.instance = instance;
	}


	public String getUser() {
		return user;
	}


	public void setUser(String user) {
		this.user = user;
	}

	public String port="";
	public String instance="";  // code change Menaka 13FEB2014
	public String user="";
	///
	
	
	public String[] tableName=new String[10]; 
	TreeMap columnNameTM=new TreeMap<>();
	TreeMap copyColumnNameTM=new TreeMap<>();
	TreeMap columnNameTM4Join=new TreeMap<>();  // code change Menaka 14FEB2014
	TreeMap columnNameTM4Period=new TreeMap<>();  // code change Menaka 14FEB2014
	TreeMap copyColumnNameTM4Join=new TreeMap<>();
	//start code change Jayaramu 04FEB14
	TreeMap targetTableNameTM=new TreeMap<>(); 
	String selectedTargetTableName="";
	TreeMap selectedTableNameTM=new TreeMap<>(); 
	String selectedTableName[];
	String tarSelectedTableName[];
	String widh4HorizontalBar = "width:185px";
	TreeMap copytableNamesFromFactPopUp=new TreeMap<>();
	public TreeMap getCopytableNamesFromFactPopUp() {
		return copytableNamesFromFactPopUp;
	}
	public void setCopytableNamesFromFactPopUp(TreeMap copytableNamesFromFactPopUp) {
		this.copytableNamesFromFactPopUp = copytableNamesFromFactPopUp;
	}
	public String getWidh4HorizontalBar() {
		return widh4HorizontalBar;
	}
	public void setWidh4HorizontalBar(String widh4HorizontalBar) {
		this.widh4HorizontalBar = widh4HorizontalBar;
	}
	public String[] getTarSelectedTableName() {
		return tarSelectedTableName;
	}

	public void setTarSelectedTableName(String[] tarSelectedTableName) {
		this.tarSelectedTableName = tarSelectedTableName;
	}





	String selectedTablefilterValue = "";
	TreeMap copySelectedTableNameTM = new TreeMap<>(); 
	ArrayList selectedMeasureColumnsAL = new ArrayList<>();
	ArrayList<HeirarchyDataBean> selectedMeasureList = new ArrayList<>();
	ArrayList targetColumNames = new ArrayList<>();
	ArrayList selectedJoinColumnsAL = new ArrayList<>();
	TreeMap joinTargetColumNamesAL = new TreeMap<>();
	String joinTargetTableName = "";
	ArrayList<HeirarchyDataBean> selectedJoinList = new ArrayList<>();
	String selectedTableOperator = "";
	String columnErrorMessage = "";
	ArrayList<HeirarchyDataBean> allFactTablesAL = new ArrayList<>();
	String selTableName = "";
	String msg4Editseg = "";

	public String getMsg4Editseg() {
		return msg4Editseg;
	}

	public void setMsg4Editseg(String msg4Editseg) {
		this.msg4Editseg = msg4Editseg;
	}

	public String getSelTableName() {
		return selTableName;
	}

	public void setSelTableName(String selTableName) {
		this.selTableName = selTableName;
	}

	public ArrayList getAllFactTablesAL() {
		return allFactTablesAL;
	}

	public void setAllFactTablesAL(ArrayList allFactTablesAL) {
		this.allFactTablesAL = allFactTablesAL;
	}

	public String getColumnErrorMessage() {
		return columnErrorMessage;
	}

	public void setColumnErrorMessage(String columnErrorMessage) {
		this.columnErrorMessage = columnErrorMessage;
	}

	public String getSelectedTableOperator() {
		return selectedTableOperator;
	}

	public void setSelectedTableOperator(String selectedTableOperator) {
		this.selectedTableOperator = selectedTableOperator;
	}

	public ArrayList<HeirarchyDataBean> getSelectedJoinList() {
		return selectedJoinList;
	}

	public void setSelectedJoinList(ArrayList<HeirarchyDataBean> selectedJoinList) {
		this.selectedJoinList = selectedJoinList;
	}

	public String getJoinTargetTableName() {
		return joinTargetTableName;
	}

	public void setJoinTargetTableName(String joinTargetTableName) {
		this.joinTargetTableName = joinTargetTableName;
	}

	public TreeMap getJoinTargetColumNamesAL() {
		return joinTargetColumNamesAL;
	}

	public void setJoinTargetColumNamesAL(TreeMap joinTargetColumNamesAL) {
		this.joinTargetColumNamesAL = joinTargetColumNamesAL;
	}

	public ArrayList getSelectedJoinColumnsAL() {
		return selectedJoinColumnsAL;
	}

	public void setSelectedJoinColumnsAL(ArrayList selectedJoinColumnsAL) {
		this.selectedJoinColumnsAL = selectedJoinColumnsAL;
	}

	public ArrayList getTargetColumNames() {
		return targetColumNames;
	}

	public void setTargetColumNames(ArrayList targetColumNames) {
		this.targetColumNames = targetColumNames;
	}

	public ArrayList getSelectedMeasureList() {
		return selectedMeasureList;
	}

	public void setSelectedMeasureList(ArrayList selectedMeasureList) {
		this.selectedMeasureList = selectedMeasureList;
	}





	String selectedtargetTable = "";
	public String getSelectedtargetTable() {
		return selectedtargetTable;
	}

	public void setSelectedtargetTable(String selectedtargetTable) {
		this.selectedtargetTable = selectedtargetTable;
	}

	
	public ArrayList getSelectedMeasureColumnsAL() {
		return selectedMeasureColumnsAL;
	}

	public void setSelectedMeasureColumnsAL(ArrayList selectedMeasureColumnsAL) {
		this.selectedMeasureColumnsAL = selectedMeasureColumnsAL;
	}

	public TreeMap getCopySelectedTableNameTM() {
		return copySelectedTableNameTM;
	}

	public void setCopySelectedTableNameTM(TreeMap copySelectedTableNameTM) {
		this.copySelectedTableNameTM = copySelectedTableNameTM;
	}

	public String[] getSelectedTableName() {
		return selectedTableName;
	}

	public void setSelectedTableName(String[] selectedTableName) {
		this.selectedTableName = selectedTableName;
	}

	public String getSelectedTablefilterValue() {
		return selectedTablefilterValue;
	}

	public void setSelectedTablefilterValue(String selectedTablefilterValue) {
		this.selectedTablefilterValue = selectedTablefilterValue;
	}





	
	

	public TreeMap getSelectedTableNameTM() {
		return selectedTableNameTM;
	}

	public void setSelectedTableNameTM(TreeMap selectedTableNameTM) {
		this.selectedTableNameTM = selectedTableNameTM;
	}

	public String getSelectedTargetTableName() {
		return selectedTargetTableName;
	}

	public void setSelectedTargetTableName(String selectedTargetTableName) {
		this.selectedTargetTableName = selectedTargetTableName;
	}

	public TreeMap getTargetTableNameTM() {
		return targetTableNameTM;
	}

	public void setTargetTableNameTM(TreeMap targetTableNameTM) {
		this.targetTableNameTM = targetTableNameTM;
	}

	public TreeMap getCopyColumnNameTM4Join() {
		return copyColumnNameTM4Join;
	}


	public void setCopyColumnNameTM4Join(TreeMap copyColumnNameTM4Join) {
		this.copyColumnNameTM4Join = copyColumnNameTM4Join;
	}


	public TreeMap getCopyColumnNameTM() {
		return copyColumnNameTM;
	}


	public void setCopyColumnNameTM(TreeMap copyColumnNameTM) {
		this.copyColumnNameTM = copyColumnNameTM;
	}
	public TreeMap getColumnNameTM4Period() {
		return columnNameTM4Period;
	}


	public void setColumnNameTM4Period(TreeMap columnNameTM4Period) {
		this.columnNameTM4Period = columnNameTM4Period;
	}


	public TreeMap getColumnNameTM4Join() {
		return columnNameTM4Join;
	}


	public void setColumnNameTM4Join(TreeMap columnNameTM4Join) {
		this.columnNameTM4Join = columnNameTM4Join;
	}


	String renderedDataPopup="" ;
	String renderedDataPopup1 ="";
	public String getRenderedDataPopup() {
		return renderedDataPopup;
	}


	public void setRenderedDataPopup(String renderedDataPopup) {
		this.renderedDataPopup = renderedDataPopup;
	}


	public String getRenderedDataPopup1() {
		return renderedDataPopup1;
	}


	public void setRenderedDataPopup1(String renderedDataPopup1) {
		this.renderedDataPopup1 = renderedDataPopup1;
	}




	
	public TreeMap getColumnNameTM() {
		return columnNameTM;
	}


	public void setColumnNameTM(TreeMap columnNameTM) {
		this.columnNameTM = columnNameTM;
	}

	public String[] columnName = new String[100];
	public String[] columnName1;
	public String[] getColumnName1() {
		return columnName1;
	}
	public void setColumnName1(String[] columnName1) {
		this.columnName1 = columnName1;
	}
	public String[] getColumnName() {
		return columnName;
	}


	public void setColumnName(String[] columnName) {
		this.columnName = columnName;
	}


	public String[] getTableName() {
		return tableName;
	}


	public void setTableName(String[] tableName) {
		this.tableName = tableName;
	}

	TreeMap tableNameTM=new TreeMap<>();
	TreeMap copyTableNameTM=new TreeMap<>();

	public TreeMap getCopyTableNameTM() {
		return copyTableNameTM;
	}


	public void setCopyTableNameTM(TreeMap copyTableNameTM) {
		this.copyTableNameTM = copyTableNameTM;
	}


	public TreeMap getTableNameTM() {
		return tableNameTM;
	}


	public void setTableNameTM(TreeMap tableNameTM) {
		this.tableNameTM = tableNameTM;
	}
	

	public String getRollUpName() {
		return rollUpName;
	}


	public void setRollUpName(String rollUpName) {
		this.rollUpName = rollUpName;
		
	}

	public void rollUp() {
		
		dataFlag="none";
	}
	
	public String codeValue="";
	public String getCodeValue() {
		return codeValue;
	}


	public void setCodeValue(String codeValue) {
		this.codeValue = codeValue;
	}

	public String nameValue="";
	
	public String getNameValue() {
		return nameValue;
	}


	public void setNameValue(String nameValue) {
		this.nameValue = nameValue;
	}

	
	
	public Hashtable heirarchyNameHT=new Hashtable<>();
	public Hashtable getHeirarchyNameHT() {
		return heirarchyNameHT;
	}


	public void setHeirarchyNameHT(Hashtable heirarchyNameHT) {
		this.heirarchyNameHT = heirarchyNameHT;
	}

	public String heirarchyName="";

	public String getHeirarchyName() {
		return heirarchyName;
	}


	public void setHeirarchyName(String heirarchyName) {
		this.heirarchyName = heirarchyName;
	}
String segcolumnName;
	


	public String getSegcolumnName() {
		return segcolumnName;
	}

	public void setSegcolumnName(String segcolumnName) {
		this.segcolumnName = segcolumnName;
	}





	TreeMap segcolumnNameTM=new TreeMap<>();
	public TreeMap getSegcolumnNameTM() {
		return segcolumnNameTM;
	}

	public void setSegcolumnNameTM(TreeMap segcolumnNameTM) {
		this.segcolumnNameTM = segcolumnNameTM;
	}
	TreeMap segtableNameTM=new TreeMap<>();
	public TreeMap getSegtableNameTM() {
		return segtableNameTM;
	}

	public void setSegtableNameTM(TreeMap segtableNameTM) {
		this.segtableNameTM = segtableNameTM;
	}

	String segtableName;
	

	public String getSegtableName() {
		return segtableName;
	}

	public void setSegtableName(String segtableName) {
		this.segtableName = segtableName;
	}


	public TreeMap getTableName1(String flagFrom, Connection con) throws Exception {
		System.out.println("Entering: "
				+ new Exception().getStackTrace()[0].getClassName() + "."
				+ new Exception().getStackTrace()[0].getMethodName());
		Connection connection = null;
		TreeMap tempTM = new TreeMap<>();
		try {
			
			
			
			segInfoMsg=""; // code change Menaka 17MAR2014
			dataInfoMsg="";
			System.out.println("configType===>"+selectedType);
			setDisplayAddDataBtn(false);
			codeandNameAL=new ArrayList<>();
			codeValue=null;
			nameValue=null;
			setSegmentNameRendered("false");
			// code change Menaka 19FEB2014
			FacesContext ctx = FacesContext.getCurrentInstance();
			ExternalContext extContext = ctx.getExternalContext();
			Map sessionMap = extContext.getSessionMap();
			HierarchyBean hdb = (HierarchyBean) sessionMap.get("hierarchyBean");
			if(!flagFrom.equals("factPopup") && !flagFrom.equals("Adminpopup") && !flagFrom.equals("source") && !flagFrom.equals("target")){
			if(hdb.getSelectedRows().size()<=0){  // Code change Menaka 11MAR2014
//				PropUtil prop=new PropUtil();
//				String XMLFileName=prop.getProperty("HIERARCHY_XML_FILE");
//				Document doc=Globals.openXMLFile(hdb.HIERARCHY_XML_DIR, XMLFileName);  // code change Menaka 20MAR2014
//				
//				Node nd=Globals.getNodeByAttrVal(doc, "RootLevel", "ID", hdb.getHierarchy_ID());
//				
//				if(nd==null){  // code change Menaka 20MAR2014
//				
//					hdb.setMsg1("Add Root node (Rollup) before adding data");
//					hdb.setValid(false);
//					return new TreeMap<>();
//				}
//			else{
					hdb.setMsg1("Please select a Node  from hierarchy to proceed further.");
					hdb.setValid(false);
					return new TreeMap<>();
				
//				}
				
			}else{
				hdb.setMsg1("");
				hdb.setValid(true);
			}
			}
			
			hdb.setSelectedType("Child");//Code change Harini 19FEB14
PropUtil prop=new PropUtil();
			
			String dir=prop.getProperty("HIERARCHY_XML_DIR");
			
			if(hdb.getSelectedRows().size() > 0){
				String selectedNodeID=hdb.getSelectedRows().get(0).getID();
				Document doc=Globals.openXMLFile(dir, "HierachyLevel.xml");
				Node nd=Globals.getNodeByAttrVal(doc, hdb.getSelectedRows().get(0).getLevelNode(), "ID", selectedNodeID);
				if(nd != null && (nd.getParentNode().getNodeType() == Node.ELEMENT_NODE) && nd.getParentNode() != null && nd.getNodeType() == Node.ELEMENT_NODE && !hdb.getSelectedRows().get(0).getLevelNode().equals("RootLevel")){
					hdb.setDisableSibling(false); 
					  
				}
				else
				{
					hdb.setDisableSibling(true); 
				  
				}
				
				}
			tableName=new String[10];
			columnName =new String[100];
			dataFlag="none";
			message = "";
//			segmentAL = new ArrayList<>();
//			segmentValues4dataPopupAL = new ArrayList<>();
			columnNameTM = new TreeMap<>();
			columnNameTM4Join=new TreeMap(); // code change Menaka 14FEB2014
			
			
			
			copySelectedPeriodValuesAL = new ArrayList<>(); 
			targetTableNameTM = new TreeMap<>();
			selectedTableNameTM = new TreeMap<>();
			
			
			selectedcolumnNameTM4Join = new TreeMap<>();
			selectedMeasureColumnTM = new TreeMap<>();
			periodcolumn = "";
			tartableNameTM=new TreeMap<>();
			
//			if(!flagFrom.equals("factPopup")){
//				periodValuesAL=new ArrayList<>();
//				selectedPeriodValuesAL = new ArrayList<>();
//				selectedMeasureColumnsAL = new ArrayList<>();
//				selectedJoinColumnsAL = new ArrayList<>();
//				columnNameTM4Period=new TreeMap();
//				
//			}
			
			
			Node measurecolumns = null;
			Node joincolumns = null;
			HeirarchyDataBean hedb; 
//			codeandNameAL = new ArrayList<>();
			segInfoMsg = "";
//			codeValue = "";
//			segmentName = "";
//			nameValue = "";
			datasegValues = "";
//			selectedsegment = "";
			renderedDataPopup = "true";
			renderedDataPopup1 = "false";
//			Hashtable connStringHT=Globals.ReadingConnectionString("DW_Connection");
//			String hstName=String.valueOf(connStringHT.get("HostName"));
//			String portNo=String.valueOf(connStringHT.get("PortName"));
//			String DBName=String.valueOf(connStringHT.get("DBName"));
//			String userName=String.valueOf(connStringHT.get("UserName"));
//			String passWord=String.valueOf(connStringHT.get("Password"));
//		   Class.forName("oracle.jdbc.driver.OracleDriver");
//			String url = "jdbc:oracle:thin:@"+hstName+":"+portNo+":"+DBName;
//			connection = DriverManager.getConnection(url, userName, passWord);
			
			// code change Menaka 11FEB2014
			
			
		//
			connection = con;
			

			DatabaseMetaData dbmd = connection.getMetaData();
			
			String[] types = {"TABLE", "VIEW"}; // 22MAR14 Devan
			
			ResultSet rs = dbmd.getTables(null,dbmd.getUserName() , "%", types);

			
			while (rs.next()) {

				String tableName = rs.getString(3);
//				tableNameTM.put(tableName, tableName);
				copyTableNameTM.put(tableName, tableName);
//				segtableNameTM.put(tableName, tableName);
//				tartableNameTM.put(tableName, tableName);
				if(tableName.length()>23){
					tableWidth4Horizontal = "";
				}
				tempTM.put(tableName, tableName);
			}
             //start code change Jayaramu 20FEB14 
			if(flagFrom.equals("Factpopup")){
			getFactTables4Edit("FromFactPopup","","");
			}
			 //end code change Jayaramu 20FEB14
		
	
		} catch (SQLException e) {
			msg4SrcTgtFact = Globals.getErrorString(e); //code change Jayaramu 25APR14
			color4SrcFactMsg = "red";
			if (connection != null && !connection.isClosed()) {
				connection.close();
			}
		}
		System.out.println("Exiting: "
				+ new Exception().getStackTrace()[0].getClassName() + "."
				+ new Exception().getStackTrace()[0].getMethodName());
		return tempTM;
	}
	
	private Hashtable getConnectionString(String string) {
		// TODO Auto-generated method stub
		return null;
	}
	TreeMap srcColumTM = new TreeMap<>();
	TreeMap srcWarHusColumTM = new TreeMap<>();

	
	public TreeMap getSrcWarHusColumTM() {
		return srcWarHusColumTM;
	}
	public void setSrcWarHusColumTM(TreeMap srcWarHusColumTM) {
		this.srcWarHusColumTM = srcWarHusColumTM;
	}
	public TreeMap getSrcColumTM() {
		return srcColumTM;
	}

	public void setSrcColumTM(TreeMap srcColumTM) {
		this.srcColumTM = srcColumTM;
	}
	
	String tarSelTableName="";

	
	public String getTarSelTableName() {
		return tarSelTableName;
	}

	public void setTarSelTableName(String tarSelTableName) {
		this.tarSelTableName = tarSelTableName;
	}

	TreeMap propertyTM = new TreeMap<>();
	public TreeMap getPropertyTM() {
		return propertyTM;
	}

	public void setPropertyTM(TreeMap propertyTM) {
		this.propertyTM = propertyTM;
	}

	String property = "";
	
	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}
	
	String tarConnectionName = "";

	public String getTarConnectionName() {
		return tarConnectionName;
	}

	public void setTarConnectionName(String tarConnectionName) {
		this.tarConnectionName = tarConnectionName;
	}
	
	String selectedSrcTable = "";
	public String getSelectedSrcTable() {
		return selectedSrcTable;
	}

	public void setSelectedSrcTable(String selectedSrcTable) {
		this.selectedSrcTable = selectedSrcTable;
	}

	TreeMap selectedSrcTableTM = new TreeMap<>();
	public TreeMap getSelectedSrcTableTM() {
		return selectedSrcTableTM;
	}

	public void setSelectedSrcTableTM(TreeMap selectedSrcTableTM) {
		this.selectedSrcTableTM = selectedSrcTableTM;
	}

	String selectedTargetColumn = "";
	public String getSelectedTargetColumn() {
		return selectedTargetColumn;
	}

	public void setSelectedTargetColumn(String selectedTargetColumn) {
		this.selectedTargetColumn = selectedTargetColumn;
	}

	TreeMap TargetColumn = new TreeMap<>();

	public TreeMap getTargetColumn() {
		return TargetColumn;
	}

	public void setTargetColumn(TreeMap targetColumn) {
		TargetColumn = targetColumn;
	}
	
	public ArrayList selectedsrcAndWHJoinsAL = new ArrayList<>();
	public ArrayList getSelectedsrcAndWHJoinsAL() {
		return selectedsrcAndWHJoinsAL;
	}

	public void setSelectedsrcAndWHJoinsAL(ArrayList selectedsrcAndWHJoinsAL) {
		this.selectedsrcAndWHJoinsAL = selectedsrcAndWHJoinsAL;
	}

	public ArrayList srcAndWHJoinsAL = new ArrayList<>();

	public ArrayList getSrcAndWHJoinsAL() {
		return srcAndWHJoinsAL;
	}

	public void setSrcAndWHJoinsAL(ArrayList srcAndWHJoinsAL) {
		this.srcAndWHJoinsAL = srcAndWHJoinsAL;
	}
	
	ArrayList selectedincrementalLoadAL = new ArrayList<>();

	public ArrayList getSelectedincrementalLoadAL() {
		return selectedincrementalLoadAL;
	}
	public void setSelectedincrementalLoadAL(ArrayList selectedincrementalLoadAL) {
		this.selectedincrementalLoadAL = selectedincrementalLoadAL;
	}
	
	ArrayList incrementalLoadAL = new ArrayList<>();
	
	public ArrayList getIncrementalLoadAL() {
		return incrementalLoadAL;
	}
	public void setIncrementalLoadAL(ArrayList incrementalLoadAL) {
		this.incrementalLoadAL = incrementalLoadAL;
	}
	
	String incrementalType="";
	
	TreeMap incrementalTypeTM = new TreeMap<>();
	
	public TreeMap getIncrementalTypeTM() {
		return incrementalTypeTM;
	}
	public void setIncrementalTypeTM(TreeMap incrementalTypeTM) {
		this.incrementalTypeTM = incrementalTypeTM;
	}
	
	TreeMap srcTableTM = new TreeMap<>();
	
	ArrayList updateIncrementalLoadAL = new ArrayList<>();
	
	public ArrayList getUpdateIncrementalLoadAL() {
		return updateIncrementalLoadAL;
	}
	public void setUpdateIncrementalLoadAL(ArrayList updateIncrementalLoadAL) {
		this.updateIncrementalLoadAL = updateIncrementalLoadAL;
	}
	
	ArrayList selectedUpdateIncrementalLoadAL = new ArrayList<>();
	
	public ArrayList getSelectedUpdateIncrementalLoadAL() {
		return selectedUpdateIncrementalLoadAL;
	}
	public void setSelectedUpdateIncrementalLoadAL(
			ArrayList selectedUpdateIncrementalLoadAL) {
		this.selectedUpdateIncrementalLoadAL = selectedUpdateIncrementalLoadAL;
	}
	String color4FactMsg = "";
	public String getColor4FactMsg() {
		return color4FactMsg;
	}
	public void setColor4FactMsg(String color4FactMsg) {
		this.color4FactMsg = color4FactMsg;
	}
	String color4JoinFactMsg = "";
	public String getColor4JoinFactMsg() {
		return color4JoinFactMsg;
	}
	public void setColor4JoinFactMsg(String color4JoinFactMsg) {
		this.color4JoinFactMsg = color4JoinFactMsg;
	}
	
	String additionalFunc4Column = "";
	
	
	public String getAdditionalFunc4Column() {
		return additionalFunc4Column;
	}
	public void setAdditionalFunc4Column(String additionalFunc4Column) {
		this.additionalFunc4Column = additionalFunc4Column;
	}
	public void getColumnname4SourceFactTable(String rowAddFrom){
		System.out.println("Entering: "
				+ new Exception().getStackTrace()[0].getClassName() + "."
				+ new Exception().getStackTrace()[0].getMethodName());
		try{
			
			String tarFactTables="";
			String srcTable="";
			HierarchydataBean hyDB=new HierarchydataBean();
			this.message = "";
			this.msg4SrcTgtFact = "";
			 this.msg4JoinColumnList = "";
			 msg4SrcWarJoin = "";
			 msg4WarHouseLoad = "";
			 msg4IncrementLoad = "";
			 msg4UpdateIncrementLoad = "";
			 msg4UpdateIncrLoad = "";
			if(srcandTarFactTablesSelectedAl==null){
				
				return;
			}
			if(srcandTarFactTablesSelectedAl.size()>=2){
//				this.message = "Please select only one Source and Target Facts to process.";
//				factMessageColor = "red";
//				color4FactMsg = "red";
//				this.msg4SrcTgtFact = "Please select only one Source and Target Facts to process."; //code change Jayaramu 
//				color4SrcFactMsg = "red";
//				return;
				int selectedSrcTAbles = srcandTarFactTablesSelectedAl.size();
				HierarchydataBean hDB = (HierarchydataBean)srcandTarFactTablesSelectedAl.get(selectedSrcTAbles-1);
				srcandTarFactTablesSelectedAl = new ArrayList<>();
//				for(int i=0;i<srcandTarFactTablesAl.size();i++){
//					HierarchydataBean hDB1 = (HierarchydataBean) srcandTarFactTablesAl.get(i);
//					if(hDB.equals(hDB1)){
//						
//					}
//				}
				srcandTarFactTablesSelectedAl.add(hDB);
				
				
			}
			if(srcandTarFactTablesSelectedAl.size()<1){
				System.out.println("No row selected for write.");
				factMessageColor = "red";
				this.message = "Please select Source and Target Facts to process.";
				color4FactMsg = "red";
				this.msg4SrcTgtFact = "Please select Source and Target Facts to process.";
				color4SrcFactMsg = "red";
				if(rowAddFrom.equals("AddJoinRow")){
				msg4JoinColumnList = "Please select Source and Target Facts to process.";
				}
				if(rowAddFrom.equals("ConfigSrcandWHJoin")){
				msg4SrcWarJoin = "Please select Source and Target Facts to process.";
				}
				if(rowAddFrom.equals("AddMeasureRow")){
				msg4WarHouseLoad = "Please select Source and Target Facts to process.";
				}
				if(rowAddFrom.equals("IncrementalLoad")){
				 msg4IncrementLoad = "Please select Source and Target Facts to process.";
				}
				if(rowAddFrom.equals("UpdateIncrementalLoad")){
				 msg4UpdateIncrementLoad = "Please select Source and Target Facts to process.";
				}
				if(rowAddFrom.equals("UpdateLoad")){
				 msg4UpdateIncrLoad = "Please select Source and Target Facts to process.";
				}
				return;
			}
			if(rowAddFrom.equals("FactSourceTargetDataTable")){
			  writeFactTablesToXml("AddFactSourceTargetTables","");
			  
			  HierarchydataBean hdb=(HierarchydataBean)srcandTarFactTablesSelectedAl.get(0);
//			  System.out.println("!selectedSrcTableTM.containsValue(hdb.getSrcFactTable())==>11"+(!selectedSrcTableTM.containsValue(hdb.getSrcFactTable())));
				if(!selectedSrcTableTM.containsValue(hdb.getSrcFactTable())){
					selectedSrcTableTM.put(hdb.getSrcFactTable(), hdb.getSrcFactTable());
				}
//				System.out.println("selectedSrcTableTM==>11"+selectedSrcTableTM);
			  FacesContext ctx = FacesContext.getCurrentInstance();
				ExternalContext extContext = ctx.getExternalContext();
				Map sessionMap = extContext.getSessionMap();
				HierarchyBean hryb = (HierarchyBean) sessionMap.get("hierarchyBean");
			  String hierDir = "";
			  String hierLevelXmlName = "";
			  PropUtil prop = new PropUtil();
			  hierDir = prop.getProperty("HIERARCHY_XML_DIR");
			  hierLevelXmlName = prop.getProperty("HIERARCHY_XML_FILE");
			  Document doc = Globals.openXMLFile(hierDir, hierLevelXmlName);
			  
			 
			  
//			  Node workNd = Globals.getNodeByAttrValUnderParent(doc, hierNd, "", attrValue)
			  selectedMeasureColumnsAL = new ArrayList<>();
				 selectedJoinColumnsAL = new ArrayList<>();
				 selectedPeriodValuesAL = new ArrayList<>();
				 srcAndWHJoinsAL = new ArrayList<>();
				 incrementalLoadAL = new ArrayList<>();
				 incrementalLoadUpdateAL = new ArrayList<>();
				 updateIncrementalLoadAL = new ArrayList<>();
				 copySelectedcolumnNameTM4Join = new TreeMap<>();
				 selectedInccolumns4IncreUpdateTM = new TreeMap<>();
				 columns4IncreUpdateTM = new TreeMap<>();
				  selectedTargetTableName = "null";
				  srcColumTM = new TreeMap<>();
				  joinsrcColumnTM = new TreeMap<>();
				  
				  hyDB=(HierarchydataBean) srcandTarFactTablesSelectedAl.get(0);
					tarFactTables = hyDB.getTarFactTables();
					srcTable = hyDB.getSrcFactTable();
					tarSelTableName = tarFactTables;
					selTableName = srcTable;
					periodCol4src = hyDB.getPeriodCol4src();
					System.out.println("periodCol4src==>"+periodCol4src);
					Node factNd = Globals.getNodeByAttrVal(doc, "Fact_Tables", "ID", hryb.getHierarchy_ID());
					connectionName = Globals.getAttrVal4AttrName(factNd, "Connection_Source");
					tarConnectionName = Globals.getAttrVal4AttrName(factNd, "Connection_Target");
					selectedFactType = Globals.getAttrVal4AttrName(factNd, "Gen_Mode");
					selectedConnectionTM.put(connectionName, connectionName);
					selectedConnectionTM.put(tarConnectionName, tarConnectionName);
					Connection tarCon = Globals.getDBConnection(tarConnectionName);
					Connection con = Globals.getDBConnection(connectionName);
					joinsrcColumnTM = columnName1("data",srcTable,con);
					TargetColumn = columnName1("",tarSelTableName,tarCon);
					srcColumTM = columnName1("data",srcTable,con);
					msg4SrcTgtFact = columnErrorMessage; //code change Jayaramu 25APR14
					 tartableNameTM = getTableName1("Adminpopup", tarCon);
					 srcTableTM.put(selTableName, selTableName);
					 srcTableTM.put(tarSelTableName, tarSelTableName);
//					periodCols4srcTM = columnName1("data",srcTable,con);
//					  getTargetTableColumn(tarFactTables,"measureTable",con);
					 if(combinationType.equals("CreateCodeCombinationDuringFact")){//code change Jayaramu 05MAY14
						Connection con1 = Globals.getDBConnection("Data_Connection");
						getTargetTableColumn(tarTableName4FactJoin, "joinTable",con1);
						
					 }else{
						 Connection con1 = Globals.getDBConnection("DW_Connection");
						 getTargetTableColumn(joinTargetTableName, "joinTable",con1);
					 }
					  loadPeriodColumnValues();
					  getFactTables4Edit("FromColumn",srcTable,tarFactTables);
			  
			}
				System.out.println("srcTable==>"+srcTable);
				System.out.println("tarFactTables==>"+tarFactTables);
				System.out.println("targetColumNames==>"+targetColumNames);
				System.out.println("rowAddFrom==>"+rowAddFrom);
			  if(rowAddFrom.equals("AddMeasureRow")){
			
			  HeirarchyDataBean hedb1;
			  srcColumTM = new TreeMap<>();
			  hedb1 = new HeirarchyDataBean("",targetColumNames,false,"","","",srcColumTM,propertyTM,"","",selectedSrcTableTM,"",TargetColumn,"","","choseTablecopy.png","choseTablecopy.png");
			  selectedMeasureColumnsAL.add(hedb1);
		
			  }else if(rowAddFrom.equals("AddJoinRow")){			  
				  HeirarchyDataBean hedb;
				  hedb = new HeirarchyDataBean("",joinTargetColumNamesAL,"","",joinTargetTableName,joinsrcColumnTM);
				  selectedJoinColumnsAL.add(hedb);
				
			  }else if(rowAddFrom.equals("ConfigSrcandWHJoin")){
				  HeirarchyDataBean hedb;
				  srcColumnTM = new TreeMap<>(); //code change Jayaramu 05APR14
				  hedb = new HeirarchyDataBean("","","", joinColumnOperTM, 
							"", "", srcColumnTM,srcConn,tarConn,"",srcTableTM,"","","choseTablecopy.png","choseTablecopy.png");
				  srcAndWHJoinsAL.add(hedb);
			  }else if (rowAddFrom.equals("IncrementalLoad")) {
				  HeirarchyDataBean hedb;
//				  srcColumnTM = new TreeMap<>(); //code change Jayaramu 05APR14
//				  joinTargetColumNamesAL = new TreeMap<>();
				  hedb = new HeirarchyDataBean("",joinsrcColumnTM,"",incrementalTypeTM);
				  incrementalLoadAL.add(hedb);
			}else if (rowAddFrom.equals("UpdateIncrementalLoad")) {
				  HeirarchyDataBean hedb;
//				  srcColumnTM = new TreeMap<>(); //code change Jayaramu 05APR14
//				  joinTargetColumNamesAL = new TreeMap<>();
				  hedb = new HeirarchyDataBean("",joinsrcColumnTM,TargetColumn,"");
				  updateIncrementalLoadAL.add(hedb);
			}else if (rowAddFrom.equals("UpdateLoad")) {
				  HeirarchyDataBean hedb;
//				  srcColumnTM = new TreeMap<>(); //code change Jayaramu 05APR14
//				  joinTargetColumNamesAL = new TreeMap<>();
				  hedb = new HeirarchyDataBean("",joinsrcColumnTM);
				  incrementalLoadUpdateAL.add(hedb);
			}
			  
			
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			String errorMsg = Globals.getErrorString(e);
			this.msg4SrcTgtFact = errorMsg; //code change Jayaramu 25APR14
			color4SrcFactMsg = "red";
		} 
		System.out.println("Exiting: "
				+ new Exception().getStackTrace()[0].getClassName() + "."
				+ new Exception().getStackTrace()[0].getMethodName());
	}
	
	
	public void resetValuesInFactPopup(){
		try{
			srcandTarFactTablesSelectedAl = new ArrayList<>();
			selectedMeasureColumnsAL = new ArrayList<>();
			 selectedJoinColumnsAL = new ArrayList<>();
			 selectedPeriodValuesAL = new ArrayList<>();
			 srcAndWHJoinsAL = new ArrayList<>();
			 incrementalLoadAL = new ArrayList<>();
			 incrementalLoadUpdateAL = new ArrayList<>();
			 updateIncrementalLoadAL = new ArrayList<>();
			 copySelectedcolumnNameTM4Join = new TreeMap<>();
			 selectedInccolumns4IncreUpdateTM = new TreeMap<>();
			 columns4IncreUpdateTM = new TreeMap<>();
			  selectedTargetTableName = "null";
			  srcColumTM = new TreeMap<>();
			  joinsrcColumnTM = new TreeMap<>();
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	String srcConn = "";
	String tarConn = "";
	
	String selectTableName = "";
	String factTableNames = "";
	
	public String getFactTableNames() {
		return factTableNames;
	}
	public void setFactTableNames(String factTableNames) {
		this.factTableNames = factTableNames;
	}
	public String getSelectTableName() {
		return selectTableName;
	}

	public void setSelectTableName(String selectTableName) {
		this.selectTableName = selectTableName;
	}

	public TreeMap columnName1(String columnNameFrom,String tableName, Connection con) throws Exception {
		
		System.out.println("Entering: "
				+ new Exception().getStackTrace()[0].getClassName() + "."
				+ new Exception().getStackTrace()[0].getMethodName());
		TreeMap tempTM = new TreeMap<>();
		Connection connection = null;
		  try {
			  widh4HorizontalBar = "width:185px"; //code change Jayaramu 21APR14
			  
			  
			 //start code change Jayaramu 08FEB14
			  
			  
			  dataFlag="none";
			  segmentAL = new TreeMap<>();
//			  segmentValues4dataPopupAL = new ArrayList<>();
			  datasegValues = "";
			  selectedsegment = "";
			  selectedMeasureColumnTM = new TreeMap<>();
			  selectedcolumnNameTM4Join = new TreeMap<>();
			  columnNameTM4Join = new TreeMap<>();
			  copyColumnNameTM4Join = new TreeMap<>();
			  columnNameTM=new TreeMap<>();
			  columnNameTM4Period = new TreeMap<>();
			  segcolumnNameTM=new TreeMap<>();		
			  copyColumnNameTM = new TreeMap<>(); //code change Jayaramu 19APR14
			  this.columnErrorMessage = "";
//			  srcColumTM = new TreeMap<>();
//			  srcColumnTM =new TreeMap<>(); 
//			  Hashtable connStringHT=Globals.ReadingConnectionString("DW_Connection");
//				String hstName=String.valueOf(connStringHT.get("HostName"));
//				String portNo=String.valueOf(connStringHT.get("PortName"));
//				String DBName=String.valueOf(connStringHT.get("DBName"));
//				String userName=String.valueOf(connStringHT.get("UserName"));
//				String passWord=String.valueOf(connStringHT.get("Password"));
//			   Class.forName("oracle.jdbc.driver.OracleDriver");
//				String url = "jdbc:oracle:thin:@"+hstName+":"+portNo+":"+DBName;
//					 
//			
//				 
//				  connection = DriverManager.getConnection(url, userName, passWord);
//			  connection = Globals.getDBConnection("DW_Connection");	 
					  connection = con;
					 
				  System.out.println("Successfully Connected to the database!");
					 
		  
				    } catch (Exception e) {
					 e.printStackTrace();
				 System.out.println("Could not find the database driver " + e.getMessage());
				   }
			 
			    try {
				 
					 
		
				Statement statement = connection.createStatement();
				System.out.println("tableName===>"+tableName);
				String sql="";
				sql="SELECT * FROM "+tableName + " WHERE ROWNUM = 1"; //23MAR14 -- RESTRICT THE RESULT SET
			
				ResultSet results = statement.executeQuery(sql);
				
					 
					// Get resultset metadata
					 
				ResultSetMetaData metadata = results.getMetaData();
				 
				int columnCount = metadata.getColumnCount();
				joincolumn = "";	 
				
					
					 
				// Get the column names; column indices start from 1
					 
					for (int i=1; i<=columnCount; i++) {
					 
				  String columnName = metadata.getColumnName(i);
//				  columnNameTM.put(columnName, columnName);
//				  copyColumnNameTM.put(columnName, columnName);//code change Jayaramu 20FEB14
//				  columnNameTM4Join.put(columnName, columnName);
//				  copyColumnNameTM4Join.put(columnName, columnName);
//				  columnNameTM4Period.put(columnName, columnName);
//				  columnNameTM.put(columnName, columnName);
//				  segcolumnNameTM.put(columnName, columnName);
//				  srcColumTM.add( columnName);
//				  srcColumnTM.put(columnName, columnName);
				  if(columnName.length()>23){
					  widh4HorizontalBar = "";
					
				  }
				 
				  tempTM.put(columnName, columnName);
				  copyColumnNameTM.put(columnName, columnName); //code change Jayaramu 19APR14
				  System.out.println("columnName: " + columnName);
					 
					}
					
					if(columnCount<=0){
//						columnNameTM.put("No Result(s) Found", "No Result(s) Found");
//						copyColumnNameTM.put("No Result(s) Found", "No Result(s) Found");
//						columnNameTM4Join.put("No Result(s) Found", "No Result(s) Found");
//						copyColumnNameTM4Join.put("No Result(s) Found", "No Result(s) Found");
//						columnNameTM4Period.put("No Result(s) Found", "No Result(s) Found");
//						columnNameTM.put("No Result(s) Found", "No Result(s) Found");
						tempTM.put("No Result(s) Found", "No Result(s) Found");
					}
					
					 
					    } catch (SQLException e) {
					 
					    	String errorMsg = Globals.getErrorString(e);
//					    	 columnNameTM.put(errorMsg, errorMsg);
//							  copyColumnNameTM.put(errorMsg, errorMsg);//code change Jayaramu 20FEB14
//							  columnNameTM4Join.put(errorMsg, errorMsg);
//							  copyColumnNameTM4Join.put(errorMsg, errorMsg);
//							  columnNameTM4Period.put(errorMsg, errorMsg);
//							  columnNameTM.put(errorMsg, errorMsg);
					    	tempTM.put(errorMsg, errorMsg);
							  this.columnErrorMessage = errorMsg;
					    	e.printStackTrace();
					  System.out.println("Could not retrieve database metadata " + e.getMessage());
				    }

		System.out.println("Exiting: "
						+ new Exception().getStackTrace()[0].getClassName() + "."
						+ new Exception().getStackTrace()[0].getMethodName());
		return tempTM;
	}
	
	public ArrayList gettingCodeandNameValue(String[] value,String type) {
		
		System.out.println("Entering: "
				+ new Exception().getStackTrace()[0].getClassName() + "."
				+ new Exception().getStackTrace()[0].getMethodName());
try {

	nameValue="";  // code change Menaka 19FEB2014
	this.flag4AddData="fromData";
	segmentNameRendered = "false";
	codeandNameAL = new ArrayList<>();
	System.out.println("value----->>>>"+value.length);
	
	if(value.length<=0){   //code change Menaka 10MAR2014
		this.dataInfoMsg="Please select an item from the list to add";
		return null;
	}
	
	this.dataInfoMsg="";
	
	for(int i=0;i<value.length;i++)
	{
		System.out.println("value----->>>>"+value[i]);
	}
		dataFlag="block";
		if(type.equalsIgnoreCase("Code")){
			
			codeandNameAL = new ArrayList<>();
			
//			nameValue = "";
			codeValue=value[0];
			 
		}else if(type.equalsIgnoreCase("name")){
			nameValue=value[0];
			Connection conn = null;
			   Statement stmt = null;
			   try{
//				   Hashtable connStringHT=Globals.ReadingConnectionString("DW_Connection");
//					String hstName=String.valueOf(connStringHT.get("HostName"));
//					String portNo=String.valueOf(connStringHT.get("PortName"));
//					String DBName=String.valueOf(connStringHT.get("DBName"));
//					String userName=String.valueOf(connStringHT.get("UserName"));
//					String passWord=String.valueOf(connStringHT.get("Password"));
//				   Class.forName("oracle.jdbc.driver.OracleDriver");
//					String url = "jdbc:oracle:thin:@"+hstName+":"+portNo+":"+DBName;
//				  
//
//			    
//			      conn = DriverManager.getConnection(url,userName,passWord);
				   conn = Globals.getDBConnection("DW_Connection");
			      
			      stmt = conn.createStatement();
			      String sql="";
			      sql = "SELECT "+codeValue+","+nameValue+" FROM "+tableName[0]+" WHERE ROWNUM = 2" ;
			      
			      ResultSet rs = stmt.executeQuery(sql);
			      ResultSetMetaData rsmd=rs.getMetaData();
//			      int i=1;
			     Hashtable codeValueHT=new Hashtable<>();
			     Hashtable nameValueHT=new Hashtable<>();
			    	 
			    	 int codeint = rsmd.getColumnType(1);
			    	 codeValueHT = getcolumntype(codeint,rs,codeValue);
			    	 int nameint = rsmd.getColumnType(2);
			    	 
			    	 rs = stmt.executeQuery(sql);
			    	 nameValueHT = getcolumntype(nameint,rs,nameValue);
			    	  
			    	  HeirarchyDataBean hdb = null;
			    	  dataFlag="block";
			    	  codeandNameAL=new ArrayList<>();
	//HeirarchyDataBean hdb=new HeirarchyDataBean();
			   for(int i=0;i<nameValueHT.size();i++){
//				   hdb =new HeirarchyDataBean(String.valueOf(codeValueHT.get(i)),String.valueOf(nameValueHT.get(i)));
				   
				   hdb =new HeirarchyDataBean("",String.valueOf(codeValueHT.get(i)),String.valueOf(nameValueHT.get(i)),"","","","",primaryType,false,String.valueOf(codeValueHT.get(i)));
				   
				   codeandNameAL.add(hdb);
			   }
			   
			    	  
			      rs.close();
			      stmt.close();
			      conn.close();
			     
			   }catch(Exception e){
			      //Handle errors for Class.forName
			      e.printStackTrace();
			   }finally{
			      //finally block used to close resources
			      try{
			         if(stmt!=null)
			            stmt.close();
			      }catch(SQLException se2){
			      }// nothing we can do
			      try{
			         if(conn!=null)
			            conn.close();
			      }catch(SQLException se){
			         se.printStackTrace();
			      }//end finally try
			   }//end try
			   
			   setDisplayAddDataBtn(true);  // code change Menaka 19FEB2014
		}
		
} catch (Exception e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}

System.out.println("Exiting: "
			+ new Exception().getStackTrace()[0].getClassName() + "."
			+ new Exception().getStackTrace()[0].getMethodName());
		
		
		return codeandNameAL; 
	}
	
	ArrayList<HeirarchyDataBean> runReportSessionslist=new ArrayList<>();
	ArrayList<HeirarchyDataBean> segmentValueList=new ArrayList<>();
	ArrayList<HeirarchyDataBean> segmentValueList1=new ArrayList<>();
	public ArrayList<HeirarchyDataBean> getSegmentValueList1() {
		return segmentValueList1;
	}

	public void setSegmentValueList1(ArrayList<HeirarchyDataBean> segmentValueList1) {
		this.segmentValueList1 = segmentValueList1;
	}

	public ArrayList<HeirarchyDataBean> getSegmentValueList() {
		return segmentValueList;
	}


	public void setSegmentValueList(ArrayList<HeirarchyDataBean> segmentValueList) {
		this.segmentValueList = segmentValueList;
	}


	public ArrayList<HeirarchyDataBean> getRunReportSessionslist() {
//		System.out.println("GETTTT SIZE------>>>>>"+runReportSessionslist.size());
		return runReportSessionslist;
	}


	public void setRunReportSessionslist(
			ArrayList<HeirarchyDataBean> runReportSessionslist) {
//		System.out.println("SETTTT SIZE------>>>>>"+runReportSessionslist.size());
		this.runReportSessionslist = runReportSessionslist;
	}

	ArrayList codeandNameAL=new ArrayList<>();
	public ArrayList getCodeandNameAL() {
		return codeandNameAL;
	}


	public void setCodeandNameAL(ArrayList codeandNameAL) {
		this.codeandNameAL = codeandNameAL;
	}
	public String dataFlag="none";

	public String getDataFlag() {
		return dataFlag;
	}


	public void setDataFlag(String dataFlag) {
		this.dataFlag = dataFlag;
	}


	public ArrayList  search(){
		
		System.out.println("Entering: "
				+ new Exception().getStackTrace()[0].getClassName() + "."
				+ new Exception().getStackTrace()[0].getMethodName());
		
		Connection conn = null;
		   Statement stmt = null;
		   try{
			   
//				Hashtable connStringHT=Globals.ReadingConnectionString("DW_Connection");
//				String hstName=String.valueOf(connStringHT.get("HostName"));
//				String portNo=String.valueOf(connStringHT.get("PortName"));
//				String DBName=String.valueOf(connStringHT.get("DBName"));
//				String userName=String.valueOf(connStringHT.get("UserName"));
//				String passWord=String.valueOf(connStringHT.get("Password"));
//			   Class.forName("oracle.jdbc.driver.OracleDriver");
//				String url = "jdbc:oracle:thin:@"+hstName+":"+portNo+":"+DBName;
//				
//
//		    
//		      conn = DriverManager.getConnection(url,userName,passWord);
			   conn = Globals.getDBConnection("DW_Connection");

		      
		      stmt = conn.createStatement();
		      String sql="";
		      sql = "SELECT "+codeValue+","+nameValue+" FROM "+tableName[0];
		      
		      ResultSet rs = stmt.executeQuery(sql);
		      ResultSetMetaData rsmd=rs.getMetaData();
//		      int i=1;
		     Hashtable codeValueHT=new Hashtable<>();
		     Hashtable nameValueHT=new Hashtable<>();
		    	 
		    	 int codeint = rsmd.getColumnType(1);
		    	 codeValueHT = getcolumntype(codeint,rs,codeValue);
		    	 int nameint = rsmd.getColumnType(2);
		    	 
		    	 rs = stmt.executeQuery(sql);
		    	 nameValueHT = getcolumntype(nameint,rs,nameValue);
		    	  
		    	  HeirarchyDataBean hdb = null;
		    	  dataFlag="block";
		    	  codeandNameAL=new ArrayList<>();
//HeirarchyDataBean hdb=new HeirarchyDataBean();
		   for(int i=0;i<nameValueHT.size();i++){
			   hdb =new HeirarchyDataBean(String.valueOf(codeValueHT.get(i)),String.valueOf(nameValueHT.get(i)));
			   codeandNameAL.add(hdb);
		   }
		   
		    	  
		      rs.close();
		      stmt.close();
		      conn.close();
		     
		   }catch(Exception e){
		      //Handle errors for Class.forName
		      e.printStackTrace();
		   }finally{
		      //finally block used to close resources
		      try{
		         if(stmt!=null)
		            stmt.close();
		      }catch(SQLException se2){
		      }// nothing we can do
		      try{
		         if(conn!=null)
		            conn.close();
		      }catch(SQLException se){
		         se.printStackTrace();
		      }//end finally try
		   }//end try
		   
		   
		   System.out.println("Exiting: "
					+ new Exception().getStackTrace()[0].getClassName() + "."
					+ new Exception().getStackTrace()[0].getMethodName());
		   
		   return codeandNameAL; 
	}
	String selectedJoinType4Direct = "";
	public String getSelectedJoinType4Direct() {
		return selectedJoinType4Direct;
	}
	public void setSelectedJoinType4Direct(String selectedJoinType4Direct) {
		this.selectedJoinType4Direct = selectedJoinType4Direct;
	}
	public void addData(String mode) throws Exception {
		System.out.println("Entering: "
				+ new Exception().getStackTrace()[0].getClassName() + "."
				+ new Exception().getStackTrace()[0].getMethodName());
		
		try{
			
			System.out.println("flag4AddData===>"+flag4AddData);
//			System.out.println("SIZE------>>>>>"+runReportSessionslist.size());
//		System.out.println("nameValueHT==>"+runReportSessionslist.get(0).codevalue+"<===>"+runReportSessionslist.get(0).namevalue);
		FacesContext ctx = FacesContext.getCurrentInstance();
		ExternalContext extContext = ctx.getExternalContext();
		Map sessionMap = extContext.getSessionMap();
		HierarchyBean hryb = (HierarchyBean) sessionMap.get("hierarchyBean");
		System.out.println("flag4AddData===>"+flag4AddData);
		System.out.println("flag4AddData------>>>>>"+mode);
		if(flag4AddData.equals("fromData")){ //sart code change Jayaramu 14FEB14
//		for(int i=0;i<runReportSessionslist.size();i++){
			
		hryb.setName("Direct");
		hryb.setCode("SQL");
		hryb.setType("Data");
		hryb.setSegmentNumber(selectedsegment1);
		System.out.println("selectedsegment===>"+selectedsegment1);
		hryb.setSegmentID("");
		
		if(mode.equals("AddMode")){
			hryb.addHierarchyNodes("AdddataNode",false);
		}
		else if(mode.equals("EditMode")){
			hryb.UpdateSelectedHierarchyLevel("DataUpdate","","",false);
		}
		
//		}
		}else if(flag4AddData.equals("fromData4DirectSQL")){ //sart code change Jayaramu 14FEB14
			for(int i=0;i<selecteddata4DirectSQLAL.size();i++){
			HierarchydataBean hdb = (HierarchydataBean) selecteddata4DirectSQLAL.get(i);
			hryb.setName(hdb.name4DirectSQL);
			hryb.setCode(hdb.code4DirectSQL);
			hryb.setType("Data");
			hryb.setSegmentNumber(selectedsegment1);
			System.out.println("selectedsegment===>"+selectedsegment1);
			hryb.setSegmentID("");
			
			if(mode.equals("AddMode")){
				hryb.addHierarchyNodes("AdddataNode",false);
			}
			else if(mode.equals("EditMode")){
				hryb.UpdateSelectedHierarchyLevel("DataUpdate","","",false);
			}
			
			}
			}else if(flag4AddData.equals("fromSegment")){
			
			for(int i=0;i<runReportSessionslist.size();i++){
				
				hryb.setName(runReportSessionslist.get(i).namevalue);
				hryb.setCode(runReportSessionslist.get(i).codevalue);
				hryb.setType("Data");
				hryb.setSegmentID(runReportSessionslist.get(i).getSegmentID());
				
				String segName = runReportSessionslist.get(i).getSegments().substring(0,7);
				int sublen = runReportSessionslist.get(i).getSegments().length()-8;
				String segNumber = runReportSessionslist.get(i).getSegments().substring(runReportSessionslist.get(i).getSegments().length()-sublen);
				String segAttr = segName+"_"+segNumber;
				hryb.setSegmentNumber(segAttr);
				boolean flag = runReportSessionslist.get(i).overRideCodeCombinationFlag;
		if(mode.equals("AddMode")){
			hryb.addHierarchyNodes("AdddataNode",flag);
		}
		else if(mode.equals("EditMode")){
			hryb.UpdateSelectedHierarchyLevel("DataUpdate","","",flag);
		}
			
		}
		} //end code change Jayaramu 14FEB14
		}catch(Exception e){
			e.printStackTrace();
		}
		
		System.out.println("Exiting: "
				+ new Exception().getStackTrace()[0].getClassName() + "."
				+ new Exception().getStackTrace()[0].getMethodName());
		
	}
	
	public Hashtable getcolumntype(int intvalue,ResultSet rs,String columnName) {
		
		System.out.println("Entering: "
				+ new Exception().getStackTrace()[0].getClassName() + "."
				+ new Exception().getStackTrace()[0].getMethodName());
		
//		-7 	BIT
//		-6 	TINYINT
//		-5 	BIGINT
//		-4 	LONGVARBINARY 
//		-3 	VARBINARY
//		-2 	BINARY
//		-1 	LONGVARCHAR
//		0 	NULL
//		1 	CHAR
//		2 	NUMERIC
//		3 	DECIMAL
//		4 	INTEGER
//		5 	SMALLINT
//		6 	FLOAT
//		7 	REAL
//		8 	DOUBLE
//		12 	VARCHAR
//		91 	DATE
//		92 	TIME
//		93 	TIMESTAMP
		int i=0;
		Hashtable valueHT=new Hashtable<>();
		try{
		switch (intvalue) {
		case -7:{
			

			break;
		}
		case -6:
			

			break;
		case -5:
	

			break;
		case -4:
		

			break;
		case -3:
			

			break;
		
		case -2:
			

			break;
		case -1:
	

			break;
		case 0:
			

			break;
		case 1:
			 i=0;
			while(rs.next()){
		        
		    	  String first  =rs.getString(columnName);
		    	  if(first!=null){
			    	  valueHT.put(i, first);
			    	  }else{
			    		  valueHT.put(i, "");
			    	  }
		    	  i++;
//		        

		      }
			
			break;
		case 2:
			i=0;
			while(rs.next()){
		        
		    	  int first  =rs.getInt(columnName);
		    	  valueHT.put(i, first);
		    	  i++;
		        

		      } 
			
			break;

		case 3:
			i=0;
			while(rs.next()){
		        
		    	  float first  =rs.getFloat(columnName);
		    	  valueHT.put(i, first);
		    	  i++;


		      }
			
			break;

		case 4:
			i=0;
			while(rs.next()){
		        
		    	  int first  =rs.getInt(columnName);
		    	  valueHT.put(i, first);
		    	  i++;


		      }

			break;

		case 5:
			i=0;
			while(rs.next()){
		        
		    	  int first  =rs.getInt(columnName);
		    	  valueHT.put(i, first);
		    	  i++;


		      }
			
			break;

		case 6:
			i=0;
			while(rs.next()){
		        
		    	  float first  =rs.getFloat(columnName);
		    	  valueHT.put(i, first);
		    	  i++;


		      }

			break;

		case 7:
			i=0;
			while(rs.next()){
		        
		    	  int first  =rs.getInt(columnName);
		    	  valueHT.put(i, first);
		    	  i++;


		      }

			break;

		case 8:
			i=0;
			while(rs.next()){
		        
		    	  double first  =rs.getDouble(columnName);
		    	  valueHT.put(i, first);
		    	  i++;


		      }

			break;

		case 12:
			i=0;
			while(rs.next()){
		        
		    	  String first  =rs.getString(columnName);
		    	 
		    	  if(first!=null){
		    	  valueHT.put(i, first);
		    	  }else{
		    		  valueHT.put(i, "");
		    	  }
		    	  i++;


		      }

			break;

		case 91:
			i=0;
			while(rs.next()){
		        
		    	  Date first  =rs.getDate(columnName);
		    	  if(first!=null){
			    	  valueHT.put(i, first);
			    	  }else{
			    		  valueHT.put(i, "");
			    	  }
		    	  i++;


		      }
		
			break;

		case 92:
			i=0;
			while(rs.next()){
		        
		    	  Date first  =rs.getTime(columnName);
		    	  if(first!=null){
			    	  valueHT.put(i, first);
			    	  }else{
			    		  valueHT.put(i, "");
			    	  }
		    	  i++;


		      }

			break;

		case 93:
			i=0;
			while(rs.next()){
		        
		    	  Date first  =rs.getTimestamp(columnName);
		    	  if(first!=null){
			    	  valueHT.put(i, first);
			    	  }else{
			    		  valueHT.put(i, "");
			    	  }
		    	  i++;
		         

		      }
			
			break;

		default:

			break;
		}

		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		

System.out.println("Exiting: "
					+ new Exception().getStackTrace()[0].getClassName() + "."
					+ new Exception().getStackTrace()[0].getMethodName());
		
		return valueHT;
	}
	
	String costCenterTableName = "";
	public String getCostCenterTableName() {
		return costCenterTableName;
	}

	public void setCostCenterTableName(String costCenterTableName) {
		this.costCenterTableName = costCenterTableName;
	}


	String costCenterColumnName = "";
	
	public String getCostCenterColumnName() {
		return costCenterColumnName;
	}

	public void setCostCenterColumnName(String costCenterColumnName) {
		this.costCenterColumnName = costCenterColumnName;
	}

//start code change Jayaramu 18FEB14
public void getSegmentValuesFromDB(String getFrom){
	
	segmentValues4dataPopupAL = new ArrayList<>();
	segmentValues4SegPopupAL = new ArrayList<>();
	copySegmentValues4SegPopupAL=new ArrayList(); // code change Menaka 15MAR2014
	
	message = "";
	segInfoMsg = "";
	if(getFrom.equals("FromSegpopup")){
		if(segSelectedsegment==null){  // code change jayaramu 10APR14
			message="Choose segment to search";
			color4FactMsg = "red";
			return; 
		}
		segmentValues4SegPopupAL.add("Please Wait Data Loading....");
	}else{
		if(selectedsegment==null){  // code change jayaramu 10APR14
			segInfoMsg="Choose segment to search";
			color4FactMsg = "red";
			return; 
		}
		
	segmentValues4dataPopupAL.add("Please Wait Data Loading....");
	}
	segErrorMsg = "";
	width4Horizontal = "width:185px";//code change Jayaramu 21APR24
	Statement stmt = null;
	ResultSet rs = null;
		try{
	PropUtil prop=new PropUtil();
	String dir=prop.getProperty("HIERARCHY_XML_DIR");
	String hierarchyConfigFileName = prop.getProperty("HIERARCHY_CONFIG_FILE");
	String hierarchyLevelFileName = prop.getProperty("HIERARCHY_XML_FILE");
	Document doc=Globals.openXMLFile(dir, hierarchyConfigFileName);
	Document hierLeveldoc=Globals.openXMLFile(dir, hierarchyLevelFileName);
	//start code change Jayaramu 17APR14
	String caseSensitive = "";
	String searchValue = "";
	String segSearchbetween1 = "";
	String segSearchbetween2 = "";
//	datasegValues = new String();
//	datasegValues = null;
//	segmentValues4dataPopupAL = new ArrayList<>();
//	segment1ValueM = new ArrayList();
//	segment2ValueM = new ArrayList();
//	segment3ValueM = new ArrayList();
//	segment4ValueM = new ArrayList();
//	segment5ValueM = new ArrayList();
//	segment6ValueM = new ArrayList();
//	segment7ValueM = new ArrayList();
//	segment8ValueM = new ArrayList();
//	segment9ValueM = new ArrayList();
//	segment10ValueM = new ArrayList();
//	segmentRenderValue= 0;
//	codeandNameAL = new ArrayList<>();
	
	
	
	String segSQLNode = "";
	Connection conn = Globals.getDBConnection("Data_Connection");;
//	int initialLoopvalue = Integer.parseInt(codeColumnNumber);
//	int conditionLoopvalue = Integer.parseInt(codeColumnName);
	
	stmt = conn.createStatement();
	
	int sublen = 0;
	int initialLoopvalue = 0;
	

	
message="";
	
	
	if(getFrom.equals("FromSegpopup")){
		sublen = segSelectedsegment.length()-8;
		
		initialLoopvalue = Integer.parseInt(segSelectedsegment.substring(segSelectedsegment.length()-sublen));
		if(!matchCase4AddSeg){ //Start code change Jayaramu 19APR14
			caseSensitive = "UPPER";
			searchValue = this.segsearchValue.toUpperCase();
			if(segselectedcondition.equalsIgnoreCase("Not Between") || segselectedcondition.equalsIgnoreCase("Between")){
				segSearchbetween1 = this.segSearchbetween1.toUpperCase();
				segSearchbetween2 = this.segSearchbetween2.toUpperCase();
			}
		}else{
			caseSensitive = "";
			searchValue = this.segsearchValue;
			if(segselectedcondition.equalsIgnoreCase("Not Between") || segselectedcondition.equalsIgnoreCase("Between")){
			segSearchbetween1 = this.segSearchbetween1;
			segSearchbetween2 = this.segSearchbetween2;
			}
		}
	}
	
	else{
		
		sublen = selectedsegment.length()-8;
		
		initialLoopvalue = Integer.parseInt(selectedsegment.substring(selectedsegment.length()-sublen));
		this.flag4AddData = "fromSegment";
		if(!matchCase4AddSeg){ //Start code change Jayaramu 19APR14
			caseSensitive = "UPPER";
			searchValue = this.searchValue.toUpperCase();
			if(selectedcondition.equalsIgnoreCase("Not Between") || selectedcondition.equalsIgnoreCase("Between")){
				segSearchbetween1 = this.segSearchbetween1.toUpperCase();
				segSearchbetween2 = this.segSearchbetween2.toUpperCase();
			}
		}else{
			caseSensitive = "";
			searchValue = this.searchValue;
			if(selectedcondition.equalsIgnoreCase("Not Between") || selectedcondition.equalsIgnoreCase("Between")){
			segSearchbetween1 = this.segSearchbetween1;
			segSearchbetween2 = this.segSearchbetween2;
			}
		}
	}
	
//	String sqlStr = ndlist1.item(0).getTextContent().toString();
//	System.out.println("sqlStr==>"+sqlStr);
//	String begin = sqlStr.substring(sqlStr.indexOf("_code"),sqlStr.indexOf("_name"));
//	String codeColumnNumber = begin.substring(begin.length()-1);
//	String end = sqlStr.substring(sqlStr.indexOf("seg"),sqlStr.indexOf("_code"));
//	String codeColumnName = end.substring(end.length()-1);
	int k=1;
	boolean oldChange=false;
	FacesContext ctx = FacesContext.getCurrentInstance();
	ExternalContext extContext = ctx.getExternalContext();
	Map sessionMap = extContext.getSessionMap();
	HierarchyBean hryb = (HierarchyBean) sessionMap.get("hierarchyBean");
	
	
	Node hierNd=Globals.getNodeByAttrVal(hierLeveldoc, "Hierarchy_Level", "Hierarchy_ID", hryb.getHierarchy_ID());
	NodeList hierNdList = hierNd.getChildNodes();
	System.out.println("hierNdList.getLength()===>"+hierNdList.getLength());
	for(int i=0;i<hierNdList.getLength();i++){
		
		if(hierNdList.item(i).getNodeType() == Node.ELEMENT_NODE && hierNdList.item(i).getNodeName().equalsIgnoreCase("Segment_SQLS")){
			oldChange=false;
			System.out.println("hierNdList.item(i)===>"+initialLoopvalue);
			Node segSqlNd=hierNdList.item(i);
			NodeList segSqlNdList = segSqlNd.getChildNodes();
			for(int j=0;j<segSqlNdList.getLength();j++){
				if(segSqlNdList.item(j).getNodeType() == Node.ELEMENT_NODE && segSqlNdList.item(j).getNodeName().equalsIgnoreCase("Segment_SQL"+k)){
					if(segSqlNdList.item(j).getNodeName().equalsIgnoreCase("Segment_SQL"+String.valueOf(initialLoopvalue))){
					String queryTOExcecute = segSqlNdList.item(j).getTextContent();
					String tableName = segSqlNdList.item(j).getAttributes().getNamedItem("Table_Name").getTextContent().toString();
					String columnName1 = segSqlNdList.item(j).getAttributes().getNamedItem("Column_Name1").getTextContent().toString();
					String columnName2 = segSqlNdList.item(j).getAttributes().getNamedItem("Column_Name2").getTextContent().toString();
					String[] temp=queryTOExcecute.split(" ");
				String queryTOExcecute1="";
					costCenterTableName = tableName;
					costCenterColumnName = columnName1;
					boolean sqlFlag=false;
					for(int jj=0;jj<temp.length;jj++){
//						System.out.println("temp[j]===>"+temp[j]);
						if(temp[jj].equalsIgnoreCase("Where")){
							sqlFlag=true;
							break;
						}else{
							sqlFlag=false;
						}
					}
					if(!sqlFlag){
					if(selectedcondition.equalsIgnoreCase("Starts With")){
						queryTOExcecute1 = queryTOExcecute+" WHERE ("+caseSensitive+"("+columnName1+") LIKE "+ "'"+searchValue+"%'"+" OR "+ caseSensitive+"("+columnName2+") LIKE "+ "'"+searchValue+"%')"+" ORDER "+"BY "+ columnName1;
					}else if(selectedcondition.equalsIgnoreCase("Ends With")){
						queryTOExcecute1 = queryTOExcecute+" WHERE ("+caseSensitive+"("+columnName1+") LIKE "+ "'%"+searchValue+"'"+" OR "+ caseSensitive+"("+columnName2+") LIKE "+ "'%"+searchValue+"')"+" ORDER "+"BY "+ columnName1;
					}else if(selectedcondition.equalsIgnoreCase("IN")){
						queryTOExcecute1 = queryTOExcecute+" WHERE ("+caseSensitive+"("+columnName1+") IN "+ "('"+searchValue+"')"+" OR "+caseSensitive+"("+columnName2+") IN "+ "('"+searchValue+"'))"+" ORDER "+"BY "+ columnName1;
					}else if(selectedcondition.equalsIgnoreCase("Contains")){
						queryTOExcecute1 = queryTOExcecute+" WHERE ("+caseSensitive+"("+columnName1+") LIKE "+ "'%"+searchValue+"%'"+" OR "+caseSensitive+"("+columnName2+") LIKE "+ "'%"+searchValue+"%')"+" ORDER "+"BY "+ columnName1;
					}else if(selectedcondition.equalsIgnoreCase("Wildcards")){
						queryTOExcecute1 = queryTOExcecute+" WHERE ("+caseSensitive+"("+columnName1+") LIKE "+ "'"+searchValue+"'"+" OR "+caseSensitive+"("+columnName2+") LIKE "+ "'"+searchValue+"')"+" ORDER "+"BY "+ columnName1;
					}else if(selectedcondition.equalsIgnoreCase("Between")){
						queryTOExcecute1 = queryTOExcecute+" WHERE ("+caseSensitive+"("+columnName1+") BETWEEN "+"'"+segSearchbetween1+"'"+" AND "+ "'"+segSearchbetween2+"'"+" OR "+ columnName2+" BETWEEN "+ "'"+segSearchbetween1+"'" +" AND "+ "'"+segSearchbetween2+"'"+ ")" + " ORDER "+"BY "+ columnName1;
					}
					else if(selectedcondition.equalsIgnoreCase("Not Between")){
						queryTOExcecute1 = queryTOExcecute+" WHERE ("+caseSensitive+"("+ columnName1+") NOT BETWEEN "+"'"+segSearchbetween1+"'"+" AND "+ "'"+segSearchbetween2+"'"+" OR "+caseSensitive+"("+columnName2+") BETWEEN "+ "'"+segSearchbetween1+"'" +" AND "+ "'"+segSearchbetween2+"'"+ ")" + " ORDER "+"BY "+ columnName1;
					}
					}else{
						if(selectedcondition.equalsIgnoreCase("Starts With")){
							queryTOExcecute1 = queryTOExcecute+" AND ("+caseSensitive+"("+ columnName1+") LIKE "+ "'"+searchValue+"%'"+" OR "+caseSensitive+"("+ columnName2+") LIKE "+ "'"+searchValue+"%')"+" ORDER "+"BY "+ columnName1;
						}else if(selectedcondition.equalsIgnoreCase("Ends With")){
							queryTOExcecute1 = queryTOExcecute+" AND ("+caseSensitive+"("+ columnName1+") LIKE "+ "'%"+searchValue+"'"+" OR "+caseSensitive+"("+ columnName2+") LIKE "+ "'%"+searchValue+"')"+" ORDER "+"BY "+ columnName1;
						}else if(selectedcondition.equalsIgnoreCase("IN")){
							queryTOExcecute1 = queryTOExcecute+" AND ("+caseSensitive+"("+ columnName1+") IN "+ "('"+searchValue+"')"+" OR "+caseSensitive+"("+ columnName2+") IN "+ "('"+searchValue+"'))"+" ORDER "+"BY "+ columnName1;
						}else if(selectedcondition.equalsIgnoreCase("Contains")){
							queryTOExcecute1 = queryTOExcecute+" AND ("+caseSensitive+"("+ columnName1+") LIKE "+ "'%"+searchValue+"%'"+" OR "+caseSensitive+"("+ columnName2+") LIKE "+ "'%"+searchValue+"%')"+" ORDER "+"BY "+ columnName1;
						}else if(selectedcondition.equalsIgnoreCase("Wildcards")){
							queryTOExcecute1 = queryTOExcecute+" AND ("+caseSensitive+"("+ columnName1+") LIKE "+ "'"+searchValue+"'"+" OR "+caseSensitive+"("+ columnName2+") LIKE "+ "'"+searchValue+"')"+" ORDER "+"BY "+ columnName1;
						}else if(selectedcondition.equalsIgnoreCase("Between")){
							queryTOExcecute1 = queryTOExcecute+" AND ("+caseSensitive+"("+ columnName1+") BETWEEN "+"'"+segSearchbetween1+"'"+" AND "+ "'"+segSearchbetween2+"'"+" OR "+caseSensitive+"("+ columnName2+") BETWEEN "+ "'"+segSearchbetween1+"'" +" AND "+ "'"+segSearchbetween2+"'"+ ")" + " ORDER "+"BY "+ columnName1;
						}
						else if(selectedcondition.equalsIgnoreCase("Not Between")){
							queryTOExcecute1 = queryTOExcecute+" AND ("+caseSensitive+"("+ columnName1+") NOT BETWEEN "+"'"+segSearchbetween1+"'"+" AND "+ "'"+segSearchbetween2+"'"+" OR "+caseSensitive+"("+ columnName2+") BETWEEN "+ "'"+segSearchbetween1+"'" +" AND "+ "'"+segSearchbetween2+"'"+ ")" + " ORDER "+"BY "+ columnName1;
						}
					}
					
					if(searchValue==null||searchValue.equals("")){  // code change Menaka 02MAY2014
						queryTOExcecute1=queryTOExcecute;
					}
					System.out.println("queryTOExcecute===>>>>"+queryTOExcecute1);
					rs = stmt.executeQuery(queryTOExcecute1);
					if(getFrom.equals("FromSegpopup")){
						segmentValues4SegPopupAL = new ArrayList<>();
						copySegmentValues4SegPopupAL=new ArrayList<>();
					}
					else{
						segmentValues4dataPopupAL = new ArrayList<>();
					}
					while(rs.next()){
						
						String segmentValue = rs.getString(2);
						String segmentCode = rs.getString(1);
						String codeValue = "("+segmentCode+")"+segmentValue;
						if(getFrom.equals("FromSegpopup")){
							
							segmentValues4SegPopupAL.add(codeValue);
						}
						else{
							if(codeValue.length()>23){ //code change Jayaramu 21APR24
								width4Horizontal = "";
							}
							segmentValues4dataPopupAL.add(codeValue.toUpperCase());
						}
						
					}
					rs.close();
				     stmt.close();
				     conn.close();
					
					
				}
					k++;
					
				}
				
				}
			
			break;
			}else{
				oldChange=true;
			}
		}
	System.out.println("oldChange===>"+oldChange);
	if(oldChange){
	NodeList ndlist1=doc.getElementsByTagName("Segment_SQLS");
	NodeList ndlist2 = null;
	
	Node chNode = ndlist1.item(0);
	if(chNode != null && chNode.getNodeType() == Node.ELEMENT_NODE){
		
		 ndlist2 = chNode.getChildNodes();
	}
	
	if(ndlist2 != null){
	for(int i=0;i<ndlist2.getLength();i++){
		
		segSQLNode = ndlist2.item(i).getNodeName();
		 
		if(segSQLNode.equals("Segment_SQL"+initialLoopvalue)){
			
			String SqlQuery = ndlist2.item(i).getTextContent().toString();
			String tableName = ndlist2.item(i).getAttributes().getNamedItem("Table_Name").getTextContent().toString();
			String columnName1 = ndlist2.item(i).getAttributes().getNamedItem("Column_Name1").getTextContent().toString();
			String columnName2 = ndlist2.item(i).getAttributes().getNamedItem("Column_Name2").getTextContent().toString();
			costCenterTableName = tableName;
			costCenterColumnName = columnName1;
			String queryTOExcecute = SqlQuery.replace("$$Table_Name$$", tableName).replace("$$Column_Name1$$", columnName1).replace("$$Column_Name2$$", columnName2);
			String queryTOExcecute1="";
			System.out.println("queryTOExcecute before===>>>>"+queryTOExcecute);
			String[] temp=queryTOExcecute.split(" ");
			boolean sqlFlag=false;
			for(int j=0;j<temp.length;j++){
//				System.out.println("temp[j]===>"+temp[j]);
				if(temp[j].equalsIgnoreCase("Where")){
					sqlFlag=true;
					break;
				}else{
					sqlFlag=false;
				}
			}
			
			if(searchValue.contains("'")){  // code change Menaka 03MAY2014
				searchValue=searchValue.replaceAll("'", "");
			}
			if(!sqlFlag){
			if(selectedcondition.equalsIgnoreCase("Starts With")){
				queryTOExcecute1 = queryTOExcecute+" WHERE ("+caseSensitive+"("+ columnName1+") LIKE "+ "'"+searchValue+"%'"+" OR "+caseSensitive+"("+columnName2+") LIKE "+ "'"+searchValue+"%')"+" ORDER "+"BY "+ columnName1;
			}else if(selectedcondition.equalsIgnoreCase("Ends With")){
				queryTOExcecute1 = queryTOExcecute+" WHERE ("+caseSensitive+"("+columnName1+") LIKE "+ "'%"+searchValue+"'"+" OR "+caseSensitive+"("+ columnName2+") LIKE "+ "'%"+searchValue+"')"+" ORDER "+"BY "+ columnName1;
			}else if(selectedcondition.equalsIgnoreCase("IN")){
				queryTOExcecute1 = queryTOExcecute+" WHERE ("+caseSensitive+"("+ columnName1+") IN "+ "('"+searchValue+"')"+" OR "+caseSensitive+"("+columnName2+") IN "+ "('"+searchValue+"'))"+" ORDER "+"BY "+ columnName1;
			}else if(selectedcondition.equalsIgnoreCase("Contains")){
				queryTOExcecute1 = queryTOExcecute+" WHERE ("+caseSensitive+"("+ columnName1+") LIKE "+ "'%"+searchValue+"%'"+" OR "+caseSensitive+"("+ columnName2+") LIKE "+ "'%"+searchValue+"%')"+" ORDER "+"BY "+ columnName1;
			}else if(selectedcondition.equalsIgnoreCase("Wildcards")){
				queryTOExcecute1 = queryTOExcecute+" WHERE ("+caseSensitive+"("+ columnName1+") LIKE "+ "'"+searchValue+"'"+" OR "+caseSensitive+"("+ columnName2+") LIKE "+ "'"+searchValue+"')"+" ORDER "+"BY "+ columnName1;
			}else if(selectedcondition.equalsIgnoreCase("Between")){
				queryTOExcecute1 = queryTOExcecute+" WHERE ("+caseSensitive+"("+ columnName1+") BETWEEN "+"'"+segSearchbetween1+"'"+" AND "+ "'"+segSearchbetween2+"'"+" OR "+caseSensitive+"("+ columnName2+") BETWEEN "+ "'"+segSearchbetween1+"'" +" AND "+ "'"+segSearchbetween2+"'"+ ")" + " ORDER "+"BY "+ columnName1;
			}
			else if(selectedcondition.equalsIgnoreCase("Not Between")){
				queryTOExcecute1 = queryTOExcecute+" WHERE ("+caseSensitive+"("+ columnName1+") NOT BETWEEN "+"'"+segSearchbetween1+"'"+" AND "+ "'"+segSearchbetween2+"'"+" OR "+caseSensitive+"("+ columnName2+") BETWEEN "+ "'"+segSearchbetween1+"'" +" AND "+ "'"+segSearchbetween2+"'"+ ")" + " ORDER "+"BY "+ columnName1;
			}
			}else{
				if(selectedcondition.equalsIgnoreCase("Starts With")){
					queryTOExcecute1 = queryTOExcecute+" AND ("+caseSensitive+"("+ columnName1+") LIKE "+ "'"+searchValue+"%'"+" OR "+caseSensitive+"("+ columnName2+") LIKE "+ "'"+searchValue+"%')"+" ORDER "+"BY "+ columnName1;
				}else if(selectedcondition.equalsIgnoreCase("Ends With")){
					queryTOExcecute1 = queryTOExcecute+" AND ("+caseSensitive+"("+ columnName1+") LIKE "+ "'%"+searchValue+"'"+" OR "+caseSensitive+"("+ columnName2+") LIKE "+ "'%"+searchValue+"')"+" ORDER "+"BY "+ columnName1;
				}else if(selectedcondition.equalsIgnoreCase("IN")){
					queryTOExcecute1 = queryTOExcecute+" AND ("+caseSensitive+"("+ columnName1+") IN "+ "('"+searchValue+"')"+" OR "+caseSensitive+"("+ columnName2+") IN "+ "('"+searchValue+"'))"+" ORDER "+"BY "+ columnName1;
				}else if(selectedcondition.equalsIgnoreCase("Contains")){
					queryTOExcecute1 = queryTOExcecute+" AND ("+caseSensitive+"("+ columnName1+") LIKE "+ "'%"+searchValue+"%'"+" OR "+caseSensitive+"("+ columnName2+") LIKE "+ "'%"+searchValue+"%')"+" ORDER "+"BY "+ columnName1;
				}else if(selectedcondition.equalsIgnoreCase("Wildcards")){
					queryTOExcecute1 = queryTOExcecute+" AND ("+caseSensitive+"("+ columnName1+") LIKE "+ "'"+searchValue+"'"+" OR "+caseSensitive+"("+ columnName2+") LIKE "+ "'"+searchValue+"')"+" ORDER "+"BY "+ columnName1;
				}else if(selectedcondition.equalsIgnoreCase("Between")){
					queryTOExcecute1 = queryTOExcecute+" AND ("+caseSensitive+"("+ columnName1+") BETWEEN "+"'"+segSearchbetween1+"'"+" AND "+ "'"+segSearchbetween2+"'"+" OR "+caseSensitive+"("+ columnName2+") BETWEEN "+ "'"+segSearchbetween1+"'" +" AND "+ "'"+segSearchbetween2+"'"+ ")" + " ORDER "+"BY "+ columnName1;
				}
				else if(selectedcondition.equalsIgnoreCase("Not Between")){
					queryTOExcecute1 = queryTOExcecute+" AND ("+caseSensitive+"("+ columnName1+") NOT BETWEEN "+"'"+segSearchbetween1+"'"+" AND "+ "'"+segSearchbetween2+"'"+" OR "+caseSensitive+"("+ columnName2+") BETWEEN "+ "'"+segSearchbetween1+"'" +" AND "+ "'"+segSearchbetween2+"'"+ ")" + " ORDER "+"BY "+ columnName1;
				}
			}
			
			if(searchValue==null||searchValue.equals("")){  // code change Menaka 02MAY2014
				queryTOExcecute1=queryTOExcecute;
			}
			
			System.out.println("queryTOExcecute===>>>>"+queryTOExcecute1);
			rs = stmt.executeQuery(queryTOExcecute1);
			if(getFrom.equals("FromSegpopup")){
				segmentValues4SegPopupAL = new ArrayList<>();
				copySegmentValues4SegPopupAL=new ArrayList<>();
			}
			else{
				segmentValues4dataPopupAL = new ArrayList<>();
			}
			while(rs.next()){
				
				String segmentValue = rs.getString(columnName2);
				String segmentCode = rs.getString(columnName1);
				String codeValue = "("+segmentCode+")"+segmentValue;
				if(getFrom.equals("FromSegpopup")){
					
					segmentValues4SegPopupAL.add(codeValue);
					copySegmentValues4SegPopupAL.add(codeValue);
				}
				else{
					if(codeValue.length()>23){ //code change Jayaramu 21APR24
						width4Horizontal = "";
					}
					segmentValues4dataPopupAL.add(codeValue.toUpperCase());
				}
			}
			
			
		}
		
	}
	}
	rs.close();
    stmt.close();
    conn.close();
		}
	
	if(segmentValues4SegPopupAL == null || segmentValues4SegPopupAL.isEmpty() || segmentValues4SegPopupAL.equals("")){
		
		segmentValues4SegPopupAL.add("No result(s) found");
	}
	if(segmentValues4dataPopupAL == null || segmentValues4dataPopupAL.isEmpty() || segmentValues4dataPopupAL.equals("")){
		
		segmentValues4dataPopupAL.add("No result(s) found");
	}
	System.out.println("initialLoopvalue==>"+segmentValues4dataPopupAL);
	System.out.println("initialLoopvalue==>"+segmentValues4SegPopupAL);

	 
     
	}catch(Exception e){
		
		segmentValues4dataPopupAL = new ArrayList<>();
		segmentValues4SegPopupAL = new ArrayList<>();
		String errorMsg = "";
		if(e.toString().length() < 90)
		{
			errorMsg = e.toString();
		}
		else
		{
			errorMsg = e.toString().substring(0,90);
		}
		segErrorMsg = errorMsg;
		if(getFrom.equals("FromSegpopup")){
			segmentValues4SegPopupAL.add(errorMsg);
		}else{
		segmentValues4dataPopupAL.add(errorMsg);
		}
		e.printStackTrace();
		
	}
	}//End code change Jayaramu 18FEB14

//start code change Jayaramu 18FEB14
public void addTodatatable(String mode){
	
	
	
	try{
		
		String primarySegment = "";
		String primaryTableName = "";
		String primaryColumnName = "";
		FacesContext ctx = FacesContext.getCurrentInstance();
		ExternalContext extContext = ctx.getExternalContext();
		Map sessionMap = extContext.getSessionMap();
		HierarchyBean hryb = (HierarchyBean) sessionMap.get("hierarchyBean");
		boolean overwriteFlag4Data = false;
		
		if(mode.equals("EditMode")){
//			if(runReportSessionslist.size()<=0){ comment by Jayaramu 14APR14
//				
//				this.msg4Editseg = "Must select one primary to add.";
//				return;
//			}else if(runReportSessionslist.size()>1){
//				
//				this.msg4Editseg = "Must select only one primary to add.";
//				return;
//			}
			
if(codeandNameAL.size()<1){
	PropUtil prop = new PropUtil();
	String HIERARCHY_XML_DIR = prop.getProperty("HIERARCHY_XML_DIR");
	Document doc = Globals.openXMLFile(HIERARCHY_XML_DIR, hryb.getHierarchyXmlFileName());
				if(hryb.getSelectedRows().size()>0){
					String NodeName= hryb.getSelectedRows().get(0).getLevelNode();
					String hryId = hryb.getSelectedRows().get(0).getID();
					Node DeleteNode = Globals.getNodeByAttrVal(doc, NodeName, "ID", hryId);
					DeleteNode.getParentNode().removeChild(DeleteNode);
					Globals.writeXMLFile(doc, HIERARCHY_XML_DIR, hryb.getHierarchyXmlFileName());
					this.msg4Editseg = "Segment/Data added successfully.";
					hryb.redirect("Hierarchytree.xhtml","Reload","","","","","","DontCreateCodeCombination","");
					return;
				}
				
			}
			
			
			HeirarchyDataBean hdb = new HeirarchyDataBean();
			int j=0;
			int k=0;
			for(int i=0;i<codeandNameAL.size();i++){
				hdb = (HeirarchyDataBean)codeandNameAL.get(i);
				if(hdb.isPrimaryType() == true){
					primarySegment = hdb.getSegments();
					primaryTableName = hdb.getTableName();
					primaryColumnName = hdb.getColumnName();
					j++;
					
					System.out.println("primarySegment==>>>"+primarySegment+"primaryTableName===>>"+primaryTableName+"primaryColumnName===>>>"+primaryColumnName);
				}
				if(hdb.overRideCodeCombinationFlag && k==0){
					overwriteFlag4Data = Boolean.valueOf(hdb.overRideCodeCombinationFlag);
					k++;
				}
			}
			
			if(j==0){
				this.msg4Editseg = "Must select one primary to add.";
				System.out.println("msg4Editseg====>>>"+msg4Editseg);
				return;
			}else if(j>1){
				this.msg4Editseg = "Must select only one primary to add.";
				System.out.println("msg4Editseg dddd====>>>"+msg4Editseg);
				return;
			}
		}
		
		setDisplayAddDataBtn(false);  // code change Menaka 19FEB2014
		
		
		segInfoMsg="";
		
	codeValue = "Segment_Code";
	nameValue = "Segment_Value";
	segmentName = "Segment_Name";
	segmentNameRendered = "true";
	PropUtil prop=new PropUtil();
	String dir=prop.getProperty("HIERARCHY_XML_DIR");
	String hierarchyXmlFileName=prop.getProperty("HIERARCHY_XML_FILE");
	HeirarchyDataBean hdb = new HeirarchyDataBean();
	int length = 0;
	String updateCode = "";
	String updateValue = "";
	String value4update = "";
	String segmentNumber = "";
	
	
//	if(mode.equals("AddMode")){ comment by jayaramu 06MAR14
//		
//		 length = selectedsegmentvalue.length;
//	}
//	
//	else if(mode.equals("EditMode")){
//		
//		if(selectedsegmentvalue.length>0)
//		{		
//		 length = 1;
//	}}
	if(mode.equals("AddMode")){
		
		if(selectedsegmentvalue.length<=0){  // code change Menaka 23APR2014
			segInfoMsg="Please select an item from the list to add";
			
			return;
		}
		
		
		
	for(int i=0;i<selectedsegmentvalue.length;i++){
		
		if(selectedsegmentvalue[i].equals(segErrorMsg)){
			
			segInfoMsg = "Error occur can't add";
			return;
		}else if(selectedsegmentvalue[i].equals("No result(s) found")){
			
			segInfoMsg = "No result(s) found, so can't add";
			return;
		}
		
		segmentNumber = selectedsegment.replace(" ", "_");
			
		if(!selectedsegmentvalue[i].equals(segErrorMsg) && !selectedsegmentvalue[i].equals("No result(s) found")){
		String segmentCode = selectedsegmentvalue[i].substring(selectedsegmentvalue[i].indexOf("(")+1, selectedsegmentvalue[i].indexOf(")"));
		String segmentValue = selectedsegmentvalue[i].substring(selectedsegmentvalue[i].indexOf(")")+1,selectedsegmentvalue[i].length());
		segInfoMsg = "Node successfully added to the Hierarchy..";
		System.out.println("selectedsegmentvalue[i]===>>>"+selectedsegmentvalue[i]);
		System.out.println("segmentCode===>>>"+segmentCode);
		System.out.println("segmentValue===>>>"+segmentValue);
		String segmentID= String.valueOf(Globals.getMaxID(dir, "Hierarchy_Config.xml", "Segments_ID", "ID"));
		
		
		

			
//			hryb.setName(segmentValue);
//			hryb.setCode(segmentCode);
//			hryb.setType("Data");
			hryb.setSegmentNumber(segmentNumber);
//			hryb.setSegmentID(segmentID);
			Hashtable ht = new Hashtable<>();
			Collection c = segmentAL.keySet();
			Iterator itr = c.iterator();
			while(itr.hasNext()){
				String temp = (String)itr.next();
//				System.out.println("itr.next()===>"+itr.next());
//				System.out.println("String.valueOf(segmentAL.get(itr.next()))===>"+String.valueOf(segmentAL.get(String.valueOf(itr.next()))));
				ht.put(String.valueOf(segmentAL.get(temp)), temp);
			}
			
			
//			hryb.addHierarchyNodes("AdddataNode");
			
		
		String[] segNumber=segmentNumber.split("_");  	// code change Menaka 17MAR2014
		hdb =new  HeirarchyDataBean(segNumber[1],segmentCode,segmentValue,"",selectedsegmentvalue[i],costCenterTableName,costCenterColumnName,primaryType,false,String.valueOf(ht.get(selectedsegment)));
		codeandNameAL.add(hdb);
			
		}
//		else if(mode.equals("EditMode")){
//			
//			updateValue = updateValue+segmentValue+"("+segmentCode+")"+";";
//			updateCode = updateCode+segmentCode+";";
//			value4update = value4update+segmentValue+";";
////			codeandNameAL.remove(0);
//		}
//		
//		if(!codeandNameAL.contains(segmentCode) || !codeandNameAL.contains(segmentValue)){
//		 hdb =new HeirarchyDataBean(selectedsegment,segmentCode,segmentValue,segmentID);
//		 codeandNameAL.add(hdb);
//		}
		}

				
		}
	 if(mode.equals("EditMode")){
		 
		 String segmentCode = "";
			String segmentValue = "";
			String segCodeValue = "";
			hryb.setPrimarySegment(primarySegment);
			String tableName =  "";
			String columnName = "";
			tableName = primaryTableName;
			columnName = primaryColumnName;
			System.out.println("tableName===>>>"+tableName);
			System.out.println("columnName===>>>"+columnName);
			if(codeandNameAL != null && !codeandNameAL.isEmpty()){
				
				for(int i=0;i<codeandNameAL.size();i++){
					
//					System.out.println("i===>>>"+i);
					
					hdb = (HeirarchyDataBean)codeandNameAL.get(i);
					
						String segcode = hdb.getCodevalue();
						System.out.println("segcode===>>>"+segcode);
						segmentCode = segcode;
						String segvalue = hdb.getNamevalue();
						System.out.println("segvalue===>>>"+segvalue);
						segmentValue = segvalue;
						String codeValue = hdb.getSegCodeWithValue();
						segCodeValue = codeValue;
						segmentNumber = hdb.getSegments().replace(" ", "_");
						hryb.setName(segCodeValue);
						hryb.setCode(segmentCode);
						hryb.setType("Data");
						hryb.setSegValueAttr4Edit(segmentValue);
						hryb.setSegmentNumber(segmentNumber);
						System.out.println("tableName===>>>"+hdb.getTableName());
						System.out.println("columnName===>>>"+hdb.getColumnName());
						
						hryb.UpdateSelectedHierarchyLevel("DataUpdate",tableName,columnName,overwriteFlag4Data);  
						this.msg4Editseg = "Segment/Data added successfully.";
//						hryb.redirect("Hierarchytree.xhtml","Reload","","","","","",false,"");
				}
			}else{
				this.msg4Editseg = "No data(s) to add.";
				return;
			}
			
	 }
	 
	 System.out.println("hierarchyXmlFileName===>>>"+hierarchyXmlFileName);
	 System.out.println("hryb.getHierarchy_ID()===>>>"+hryb.getHierarchy_ID());
	 	//code change Jayaramu 20MAY2014 for load hierarchy tree.
		Map<String, Object> viewMap = FacesContext.getCurrentInstance().getViewRoot().getViewMap();
		ReportsInvXMLTreeBean viewScopedBean = (ReportsInvXMLTreeBean) viewMap.get("reportsInvXMLTreeBean");
		viewScopedBean.getReportsForSession(hierarchyXmlFileName,hryb.getHierarchy_ID(),null,"FromHierTree");
	}
	
	
	catch(Exception e){
		e.printStackTrace();
		}
			
			
		}
	
//End code change Jayaramu 18FEB14
	
	
	public void  setFieldValue1(){
		
		System.out.println("segmentNumber---->>>");
	}
	
	public void setFieldValue(String segmentNumber){
		
		
		
		System.out.println("segmentNumber---->>>"+segmentNumber);
		System.out.println("segment1SelectValue---->>>"+segment1SelectValue);
		System.out.println("segment1SelectValue---->>>"+segment2SelectValue);
		System.out.println("segment3SelectedValue---->>>"+segment3SelectedValue);
		System.out.println("segment4SelectedValue---->>>"+segment4SelectedValue);
		System.out.println("segment5SelectedValue---->>>"+segment5SelectedValue);
		System.out.println("segment6SelectedValue---->>>"+segment6SelectedValue);
		System.out.println("segment7SelectedValue---->>>"+segment7SelectedValue);
		System.out.println("segment8SelectedValue---->>>"+segment8SelectedValue);
		System.out.println("segment9SelectedValue---->>>"+segment9SelectedValue);
		System.out.println("segment10SelectedValue---->>>"+segment10SelectedValue);
		
		if(segmentNumber.equalsIgnoreCase("first")){
			
			
			System.out.println("segment1SelectValue.length()---->>>"+segment1SelectValue.length());
			
			System.out.println("checkexistsValue1---->>>"+checkexistsValue1);
			System.out.println("!segment1SelectValue.contains(checkexistsValue1)---->>>"+!segment1SelectValue.contains(segment1SelectedValue));
			System.out.println("segment1SelectedValue111---->>>"+segment1SelectedValue);
			System.out.println("segment1SelectValue1111---->>>"+segment1SelectValue);
			
			if (segment1SelectValue != null && segment1SelectValue.length() > 0) {
				
					if (!segment1SelectedValue.contains(segment1SelectValue)) {
						
						segment1SelectedValue = segment1SelectedValue.concat(segment1SelectValue + ";");
//						segmentValuesHT.put(0, segment1SelectedValue);
					}

				}

			}
			
		else if(segmentNumber.equalsIgnoreCase("second")){
			
			if (segment2SelectValue != null && segment2SelectValue.length() > 0) {
				

					if (!segment2SelectedValue.contains(segment2SelectValue)) {

						segment2SelectedValue = segment2SelectedValue.concat(segment2SelectValue+ ";");
//						segmentValuesHT.put(1, segment2SelectedValue);
					}
			}
			
		}
		
		
		else if(segmentNumber.equalsIgnoreCase("third")){
			
			if (segment3SelectValue != null && segment3SelectValue.length() > 0) {
				

				if (!segment3SelectedValue.contains(segment3SelectValue)) {

					segment3SelectedValue = segment3SelectedValue.concat(segment3SelectValue+ ";");
//					segmentValuesHT.put(2, segment3SelectedValue);
				}
		}
			
		}else if(segmentNumber.equalsIgnoreCase("four")){
			
			if (segment4SelectValue != null && segment4SelectValue.length() > 0) {
				

				if (!segment4SelectedValue.contains(segment4SelectValue)) {

					segment4SelectedValue = segment4SelectedValue.concat(segment4SelectValue+ ";");
//					segmentValuesHT.put(3, segment4SelectedValue);
				}
		}
			
		}else if(segmentNumber.equalsIgnoreCase("five")){
			
			if (segment5SelectValue != null && segment5SelectValue.length() > 0) {
				

				if (!segment5SelectedValue.contains(segment5SelectValue)) {

					segment5SelectedValue = segment5SelectedValue.concat(segment5SelectValue+ ";");
//					segmentValuesHT.put(4, segment5SelectedValue);
				}
		}
			
		}else if(segmentNumber.equalsIgnoreCase("six")){
			
			if (segment6SelectValue != null && segment6SelectValue.length() > 0) {
				

				if (!segment6SelectedValue.contains(segment6SelectValue)) {

					segment6SelectedValue = segment6SelectedValue.concat(segment6SelectValue+ ";");
//					segmentValuesHT.put(5, segment6SelectedValue);
				}
		}
			
		}else if(segmentNumber.equalsIgnoreCase("seven")){
			
			if (segment7SelectValue != null && segment7SelectValue.length() > 0) {
				

				if (!segment7SelectedValue.contains(segment7SelectValue)) {

					segment7SelectedValue = segment7SelectedValue.concat(segment7SelectValue+ ";");
//					segmentValuesHT.put(6, segment7SelectedValue);
				}
		}
			
		}else if(segmentNumber.equalsIgnoreCase("eight")){
			if (segment8SelectValue != null && segment8SelectValue.length() > 0) {
				

				if (!segment8SelectedValue.contains(segment8SelectValue)) {

					segment8SelectedValue = segment8SelectedValue.concat(segment8SelectValue+ ";");
//					segmentValuesHT.put(7, segment8SelectedValue);
				}
		}
			
		}else if(segmentNumber.equalsIgnoreCase("nine")){
			
			if (segment9SelectValue != null && segment9SelectValue.length() > 0) {
				

				if (!segment9SelectedValue.contains(segment9SelectValue)) {

					segment9SelectedValue = segment9SelectedValue.concat(segment9SelectValue+ ";");
//					segmentValuesHT.put(8, segment9SelectedValue);
				}
		}
			
		}else if(segmentNumber.equalsIgnoreCase("ten")){
			
			if (segment10SelectValue != null && segment10SelectValue.length() > 0) {
				

				if (!segment10SelectedValue.contains(segment10SelectValue)) {

					segment10SelectedValue = segment10SelectedValue.concat(segment10SelectValue+ ";");
//					segmentValuesHT.put(9, segment10SelectedValue);
				}
		}
		}
		
		
		
		
		System.out.println("segmentNumber---->>>"+segmentNumber);
		System.out.println("segment1SelectedValue---->>>"+segment1SelectedValue);
		System.out.println("segment1SelectValue---->>>"+segment2SelectValue);
		System.out.println("segment3SelectedValue---->>>"+segment3SelectedValue);
		System.out.println("segment4SelectedValue---->>>"+segment4SelectedValue);
		System.out.println("segment5SelectedValue---->>>"+segment5SelectedValue);
		System.out.println("segment6SelectedValue---->>>"+segment6SelectedValue);
		System.out.println("segment7SelectedValue---->>>"+segment7SelectedValue);
		System.out.println("segment8SelectedValue---->>>"+segment8SelectedValue);
		System.out.println("segment9SelectedValue---->>>"+segment9SelectedValue);
		System.out.println("segment10SelectedValue---->>>"+segment10SelectedValue);
		
		
	}
	//End code change Jayaramu 18FEB14
	//start code change Jayaramu 18FEB14
	public void addSegmentValues2HT() throws Exception{
			
//		segmentListAL = new ArrayList<>();
		
//		segmentValuesHT.put(0, segment1SelectedValue);
//		segmentValuesHT.put(1, segment2SelectedValue);
//		segmentValuesHT.put(2, segment3SelectedValue);
//		segmentValuesHT.put(3, segment4SelectedValue);
//		segmentValuesHT.put(4, segment5SelectedValue);
//		segmentValuesHT.put(5, segment6SelectedValue);
//		segmentValuesHT.put(6, segment7SelectedValue);
//		segmentValuesHT.put(7, segment8SelectedValue);
//		segmentValuesHT.put(8, segment9SelectedValue);
//		segmentValuesHT.put(9, segment10SelectedValue);
		FacesContext ctx = FacesContext.getCurrentInstance();
		ExternalContext extContext = ctx.getExternalContext();
		Map sessionMap = extContext.getSessionMap();
		HierarchyBean hryb = (HierarchyBean) sessionMap.get("hierarchyBean");
		//Start Code change Jayaramu 22FEB14		
		if(hryb.getHirename()==null||hryb.getHirename().equals("")||hryb.getHirename().isEmpty()){  
			this.message="Enter the hierarchy name.";
			return;
		}
		
if(hryb.getHierarchyNameList().containsValue(hryb.getHirename())){
			
			System.out.println("Hierarchy Name already exists.. Please Enter another Name...");
			this.message="Hierarchy name already exists. Please enter another name.";
			return;
			
		}
this.message="";
		
//End Code change Jayaramu 22FEB14
		PropUtil prop=new PropUtil();
		String dir=prop.getProperty("HIERARCHY_XML_DIR");
		Document doc = Globals.openXMLFile(dir, hierarchyXMLFileName);
		Node rootnode = null;
		
		Node deleteExistsSegments = Globals.getNodeByAttrVal(doc, "Segments", "ID", hryb.getHierarchy_ID());
		if(deleteExistsSegments != null){
			
			deleteExistsSegments.getParentNode().removeChild(deleteExistsSegments);
			
		}
		
	
		if(hryb.getFlagNew().equals("newAdded")){
			
			
			hryb.addHierarchyNodes("Fromsegment",false);
			hryb.setFlagNew("alreadyadded");
			 doc = Globals.openXMLFile(dir, hierarchyXMLFileName);
			
			
		}
		rootnode = Globals.getNodeByAttrVal(doc, "Hierarchy_Level", "Hierarchy_ID", hryb.getHierarchy_ID());
		System.out.println("rootnode====>>>>"+rootnode);
		Element rootndElement=(Element)rootnode;
		rootndElement.setAttribute("Number_of_Segment", String.valueOf(noOfSegments));
		Node appndchild = rootnode.getLastChild();
		
		if(rootnode.getNodeType() == Node.ELEMENT_NODE && appndchild.getNodeType() == Node.ELEMENT_NODE ){
			System.out.println("appndchild---->>>"+appndchild);
			
			
			
			
		Node ChildElement = null;
		Element element = null;
		HeirarchyDataBean hdb = null;
		Element rootElement = doc.createElement("Segments");
		rootnode.appendChild(rootElement);
		Node appenChild  = (Node)rootElement;
		rootElement.setAttribute("Status", segmentStatus);
		rootElement.setAttribute("ID", hryb.getHierarchy_ID());
	int k=1;
		
		for(int i=0;i<noOfSegments;i++){
				
			ChildElement = doc.createElement("Segment_"+ k);
			String concatvalues = "";
			
			
				
				
				
				for(int j=0;j<segmentValueList.size();j++){
					
					
					System.out.println(segmentValueList.get(j).getSegmentNumber());
					
					
					
					if(String.valueOf(segmentValueList.get(j).getSegmentNumber()).contains("Segment "+k))
					{
				String code  = segmentValueList.get(j).getSegmentValue().substring(0,segmentValueList.get(j).getSegmentValue().indexOf("("));
				String segmentValue = segmentValueList.get(j).getSegmentValue().substring(segmentValueList.get(j).getSegmentValue().indexOf("(")+1, segmentValueList.get(j).getSegmentValue().indexOf(")"));
				
				String separator = code+"#$#"+segmentValue;
				concatvalues = concatvalues+separator.concat("~");
				System.out.println("concatvalues----->>>"+concatvalues);
					}
				}
				
				appenChild.appendChild(ChildElement);
				ChildElement.setTextContent(concatvalues);
				
				
				
			
//			ChildElement = doc.createElement("Segment_"+ k);
//			appenChild.appendChild(ChildElement);
//			ChildElement.setTextContent(segmentValuesHT.get(i-1).replace(";", "~"));
			
//			hdb =new HeirarchyDataBean(i,"Segment"+" "+i+" "+"Default",segmentValuesHT.get(i-1));
//			segmentListAL.add(hdb);
			k++;
			
		}
		
		Globals.writeXMLFile(doc, dir, hierarchyXMLFileName);
		System.out.println("segmentValuesHT---->>>"+segmentValuesHT);
		message = "Segment added successFully";
		
		}
	}//End code change Jayaramu 18FEB14
	
	
	String statusMsg="";
	
	public String getStatusMsg() {
		return statusMsg;
	}

	public void setStatusMsg(String statusMsg) {
		this.statusMsg = statusMsg;
	}

	//start code change Jayaramu 18FEB14
	public void retrieveSegmentsFromXml(){
		try{
		FacesContext ctx = FacesContext.getCurrentInstance();
		ExternalContext extContext = ctx.getExternalContext();
		Map sessionMap = extContext.getSessionMap();
		HierarchyBean hryb = (HierarchyBean) sessionMap.get("hierarchyBean");
		hryb.msg1="";
		segInfoMsg = "";
		message = "";
				
		segmentListAL = new ArrayList<>();
//		segment1SelectedValue = "";
//		segment2SelectedValue = "";
//		segment3SelectedValue = "";
//		segment4SelectedValue = "";
//		segment5SelectedValue = "";
//		segment6SelectedValue = "";
//		segment7SelectedValue = "";
//		segment8SelectedValue = "";
//		segment9SelectedValue = "";
//		segment10SelectedValue = "";
//		noOfSegments = 0;
//		segmentRenderValue = 0;
//		segmentStatus = "";
//		message = "";
		boolean flag=false;
		PropUtil prop=new PropUtil();
		Node childnode = null;
		HeirarchyDataBean hdb = null;
		segmentListAL = new ArrayList<>();
		numberOfSegmentAL = new TreeMap<>();
		String dir=prop.getProperty("HIERARCHY_XML_DIR");
		String hierarchyConfigFile=prop.getProperty("HIERARCHY_CONFIG_FILE");
		System.out.println("hierarchyXMLFileName====>>>"+hierarchyXMLFileName);
		Document doc = Globals.openXMLFile(dir, hierarchyXMLFileName);
		Document configDoc = Globals.openXMLFile(dir, hierarchyConfigFile);
		NodeList segrootnode = (NodeList) configDoc.getElementsByTagName("Segments");
		Node rootnode = Globals.getNodeByAttrVal(doc, "Segments", "ID", hryb.getHierarchy_ID());
		Node hierNd = Globals.getNodeByAttrVal(doc, "Hierarchy_Level", "Hierarchy_ID", hryb.getHierarchy_ID());
		NodeList hierNdList = hierNd.getChildNodes();
		boolean oldCodeChange=false;
		/*ArrdisplayAl*/
		int k=1;
		statusMsg="";
		for(int i=0;i<hierNdList.getLength();i++){
			if(hierNdList.item(i).getNodeType() == Node.ELEMENT_NODE && hierNdList.item(i).getNodeName().equalsIgnoreCase("Segment_SQLS")){
				oldCodeChange=false;
				System.out.println("1111111====>>>"+oldCodeChange);
//				if(hierNdList.item(i).getNodeName().equalsIgnoreCase("Segment_Trigger_SQL")){
//					System.out.println("222222====>>>"+oldCodeChange);
					Node segTriggerNd=hierNdList.item(i);
					NodeList segTriggerNdList=segTriggerNd.getChildNodes();
					for(int j=0;j<segTriggerNdList.getLength();j++){
						if(segTriggerNdList.item(j).getNodeName().contains("Segment_SQL") && segTriggerNdList.item(j).getNodeType() == Node.ELEMENT_NODE){
//							columnName=segTriggerNdList.item(j).getTextContent();
							System.out.println("1111111====>>>"+Globals.getAttrVal4AttrName(segTriggerNdList.item(j),"Display_Name"));
							numberOfSegmentAL.put(Globals.getAttrVal4AttrName(segTriggerNdList.item(j),"Display_Name"),"Segment_"+k);
							if(Globals.getAttrVal4AttrName(segTriggerNdList.item(j),"Display_Name").equals("") || segTriggerNdList.item(j).getTextContent().equals("")){
								if(!statusMsg.contains(segTriggerNdList.item(j).getNodeName().replace("_SQL", "_")))
									statusMsg=statusMsg+segTriggerNdList.item(j).getNodeName().replace("_SQL", "_")+", ";
								flag=true;
							}
							k++;
						}
						
					}
					break;
//					continue;
//				}
			}else{
				oldCodeChange=true;
			}
		}
		for(int i=0;i<hierNdList.getLength();i++){
		if(hierNdList.item(i).getNodeType() == Node.ELEMENT_NODE && hierNdList.item(i).getNodeName().equalsIgnoreCase("Segment_Trigger_SQL")){
			Node segmentTriggerSqlNd=hierNdList.item(i);
			int l=1;
			NodeList segmentTriggerSqlNdList=segmentTriggerSqlNd.getChildNodes();
			
			for(int j=0;j<segmentTriggerSqlNdList.getLength();j++){
				
				if(segmentTriggerSqlNdList.item(j).getNodeType() == Node.ELEMENT_NODE && segmentTriggerSqlNdList.item(j).getNodeName().contains("Segment_")){
//					displayNameHT.put(k, segmentSqlsNdList.item(j).getAttributes().getNamedItem("Segment_SQLS").getNodeValue());
					
					if(segmentTriggerSqlNdList.item(j).getTextContent().equals("")){
						
						if(!statusMsg.contains(segmentTriggerSqlNdList.item(j).getNodeName()))
							statusMsg=statusMsg+segmentTriggerSqlNdList.item(j).getNodeName()+", ";
						flag=true;
						l++;
					}

				}
			}
		}
		}
		
		if(flag){
		statusMsg=statusMsg.substring(0, statusMsg.length()-2);
		statusMsg=statusMsg+" not configured yet. Please contact your administrator.";
		flag=false;
		}
		System.out.println("oldCodeChange====>>>"+oldCodeChange);
		NodeList ndlist1=configDoc.getElementsByTagName("No_Of_segments");
		int noOfSegments = Integer.parseInt(ndlist1.item(0).getTextContent().toString());
		this.noOfSegments = noOfSegments;
		if(oldCodeChange){
		for(int i=1;i<=noOfSegments;i++){
			numberOfSegmentAL.put("Segment "+i,"Segment_"+i);
				}
		}
		if(rootnode == null && segrootnode != null){
			
			
			
			rootnode = segrootnode.item(0);
			
		}
		
		
		if(rootnode != null){
//		this.segmentStatus = rootnode.getAttributes().getNamedItem("Status").getTextContent().toString();
		NodeList segmentNodes = rootnode.getChildNodes();
		int j=0;
		for(int i=0;i<segmentNodes.getLength();i++){
			
		 childnode = segmentNodes.item(i);
			if(childnode.getNodeType() == Node.ELEMENT_NODE){
			
				String segNo = childnode.getNodeName().toString();
				int sublen = segNo.length()-8;
				j = Integer.parseInt(segNo.substring(segNo.length()-sublen));
				if(childnode.getTextContent() != null && !childnode.getTextContent().equals("")){
				String segments = childnode.getTextContent().toString().replace("#$#", "(").replace("~", ");");
				
//				String[] segmentsValue = segments.split("~");
				Hashtable ht = new Hashtable<>();
				Collection c = getNumberOfSegmentAL().keySet();
				Iterator itr = c.iterator();
				while(itr.hasNext()){
					String temp = (String)itr.next();
//					System.out.println("itr.next()===>"+itr.next());
//					System.out.println("String.valueOf(segmentAL.get(itr.next()))===>"+String.valueOf(segmentAL.get(String.valueOf(itr.next()))));
					ht.put(String.valueOf(getNumberOfSegmentAL().get(temp)), temp);
				}
				System.out.println("ht--------->>>>"+ht);
				
//				hdb =new HeirarchyDataBean(segNo,segSelectedsegment+" Default",segmentValuesWithcode,String.valueOf(ht.get(segSelectedsegment)));		
					hdb = new HeirarchyDataBean(j,"Segment"+" "+j+" "+"Default",segments,String.valueOf(ht.get("Segment"+"_"+j)));
					segmentListAL.add(hdb);
					System.out.println("segments====>>>"+segments);
					}}
				
				
			
			
			
		}
		
		}
		}catch(Exception e){
			
			e.printStackTrace();
		}
		
	}//End code change Jayaramu 18FEB14
	
	
	public String segColumnName="";
	public String getSegColumnName() {
		return segColumnName;
	}

	public void setSegColumnName(String segColumnName) {
		this.segColumnName = segColumnName;
	}
	public String segTableName="";
	public String getSegTableName() {
		return segTableName;
	}

	public void setSegTableName(String segTableName) {
		this.segTableName = segTableName;
	}
	ArrayList segmentAdministrationAL=new ArrayList<>();
	
	public ArrayList getSegmentAdministrationAL() {
		return segmentAdministrationAL;
	}

	public void setSegmentAdministrationAL(ArrayList segmentAdministrationAL) {
		this.segmentAdministrationAL = segmentAdministrationAL;
	}
	String segmentColumnName="";
	
	public String getSegmentColumnName() {
		return segmentColumnName;
	}

	public void setSegmentColumnName(String segmentColumnName) {
		this.segmentColumnName = segmentColumnName;
	}
	
	public void dispSegTableColumnName() {
//		System.out.println("dispSegTableColumnName==>"+tableName[0]+"==="+columnName[0]);
		segTableName=tableName[0];
		segcolumnName=columnName[0];
		dispSegmentAdmin("false");
	}
	

	public void dispSegmentAdmin(String cmgFrmAdmin) {
		
		try{
//			if(noOfSegments)
			
			int sizeOfSegmentAL = segmentAdministrationAL.size();
//			if(segmentAdministrationAL.isEmpty()){  // code comment Menaka 02MAY2014
//				segmentAdministrationAL = new ArrayList<>();
				HeirarchyDataBean hrdb;
				if(cmgFrmAdmin.equalsIgnoreCase("true")){
				if(sizeOfSegmentAL<noOfSegments){
					for(int i=sizeOfSegmentAL;i<noOfSegments;i++){
//						hrdb=new HeirarchyDataBean("Segment_"+i, "", "", columnNameTM,segColumnName);
//						hrdb=new HeirarchyDataBean("Segment_"+i, "", "", copyColumnNameTM,segColumnName);  // code change Menaka 02MAY2014
//						segmentAdministrationAL.add(hrdb);
						hrdb=new HeirarchyDataBean("Segment_"+(i+1), "", "", segcolumnNameTM,segColumnName);  // code change Menaka 02MAY2014
						segmentAdministrationAL.add(hrdb);
					}
				}else if(sizeOfSegmentAL>noOfSegments){
					ArrayList tempAL = new ArrayList<>();
					for(int i=noOfSegments;i<sizeOfSegmentAL;i++){
//						hrdb=new HeirarchyDataBean("Segment_"+i, "", "", columnNameTM,segColumnName);
						tempAL.add(segmentAdministrationAL.get(i));
						
//						sizeOfSegmentAL--;
					}
					segmentAdministrationAL.removeAll(tempAL);
				}
				}else{
//					if(sizeOfSegmentAL<noOfSegments){
						for(int i=0;i<sizeOfSegmentAL;i++){
							System.out.println("segment column of "+segTableName+" "+segcolumnNameTM);
							hrdb =  (HeirarchyDataBean) segmentAdministrationAL.get(i);
							hrdb.segColumnNameTM = segcolumnNameTM;
						}
//					}else if(sizeOfSegmentAL>noOfSegments){
//						for(int i=0;i<noOfSegments;i++){
//							hrdb =  (HeirarchyDataBean) segmentAdministrationAL.get(i);
//							hrdb.segColumnNameTM = segcolumnNameTM;
//						}
//					}
				}
//			}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		
		
	}
	public String messConfig="";
	
	public String getMessConfig() {
		return messConfig;
	}

	public void setMessConfig(String messConfig) {
		this.messConfig = messConfig;
	}

	boolean missingConfigFlag=false;
	
	public boolean isMissingConfigFlag() {
		return missingConfigFlag;
	}

	public void setMissingConfigFlag(boolean missingConfigFlag) {
		this.missingConfigFlag = missingConfigFlag;
	}

	public void messageConstructor(String hierID,String configFlag) {
		try{
			System.out.println("Entering");
			PropUtil prop=new PropUtil();
			String hierXMLDir=prop.getProperty("HIERARCHY_XML_DIR");
			String hierLevelXMLFileName=prop.getProperty("HIERARCHY_XML_FILE");
			String hierConfigFile = prop.getProperty("HIERARCHY_CONFIG_FILE");
			Document doc=Globals.openXMLFile(hierXMLDir, hierLevelXMLFileName);
			missingConfigFlag=false;
			if(configFlag.equalsIgnoreCase("true")){
			Node nd=Globals.getNodeByAttrVal(doc, "Hierarchy_Level", "Hierarchy_ID", hierID);
			String noOfSegments="";
			if(noOfSegments.equals("")){
				Document doc1 = Globals.openXMLFile(hierXMLDir, hierConfigFile);
				NodeList ndList = doc1.getElementsByTagName("No_Of_segments"); 
				Node nd1 = ndList.item(0);
				noOfSegments = nd1.getTextContent();
			}else {
				noOfSegments=Globals.getAttrVal4AttrName(nd, "Number_of_Segment");
			}
			mess="You have configured "+noOfSegments+" Segments. ";
			System.out.println("mess==>1 "+mess);
			System.out.println("segmentValueList==> "+segmentValueList1.size());
			for(int i=0;i<segmentValueList1.size();i++){
				HeirarchyDataBean HDB=(HeirarchyDataBean) segmentValueList1.get(i);
				mess=mess+HDB.getSegmentid()+", ";
//				messConfig=messConfig+HDB.getSegmentid()+" and ";
			}
			System.out.println("mess==>2 "+mess);
			mess=mess.substring(0,mess.length()-2);
//			messConfig=messConfig.substring(0,messConfig.length()-4);
			mess=mess+" will be deleted. Do you want to proceed?";
			}else{
			String messConfigtemp="";
			messConfig="";
//			messConfig=messConfig+"are not configured yet. Do you want to proceed?";
			for(int i=0;i<segmentAdministrationAL.size();i++){
				HeirarchyDataBean HDB=(HeirarchyDataBean) segmentAdministrationAL.get(i);
				System.out.println("HDB.getDisplayName()===>"+HDB.getDisplayName());
				System.out.println("HDB.getSqltext()===>"+HDB.getSqltext());
				System.out.println("HDB.getSegColumnName()===>"+HDB.getSegColumnName());
				if(HDB.getDisplayName().equals("") || HDB.getSqltext().equals("") || HDB.getSegColumnName()==null){
					
					messConfigtemp=messConfigtemp+HDB.getSegmentid()+", ";
					missingConfigFlag=true;
			}
				
					
			}
			if(missingConfigFlag){
			if(messConfigtemp.length()>=4)
				messConfigtemp=messConfigtemp.substring(0,messConfigtemp.length()-2);
			messConfig=messConfigtemp+" are not configured yet. Do you want to proceed?";
			}else{
				saveSegmentAdministration(hierID);
				missingConfigFlag=true;
			}
			System.out.println("messConfig==>:"+messConfig);
			}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	
	public void EditSegmentAdministration(String hierID) {
		try{
			PropUtil prop=new PropUtil();
			String hierLevelXMLFileName=prop.getProperty("HIERARCHY_XML_FILE");
			String hierConfigXMLFileName=prop.getProperty("HIERARCHY_CONFIG_FILE");
			String hierXMLDir=prop.getProperty("HIERARCHY_XML_DIR");
			Document doc=Globals.openXMLFile(hierXMLDir, hierLevelXMLFileName);
			
			Document confDoc = Globals.openXMLFile(hierXMLDir, hierConfigXMLFileName);
			NodeList segList = confDoc.getElementsByTagName("Segment_SQLS");
			Node segSqlNd = segList.item(0);
			NodeList segSqlNdList = segSqlNd.getChildNodes();
			NodeList trigList = confDoc.getElementsByTagName("Segment_Trigger_SQL");
			Node segtrigSqlNd = trigList.item(0);
			NodeList segtrigSqlNdList = segtrigSqlNd.getChildNodes();
			
			Node hierNd = Globals.getNodeByAttrVal(doc, "Hierarchy_Level", "Hierarchy_ID", hierID);
			NodeList hierNdList=hierNd.getChildNodes();
			Hashtable displayNameHT=new Hashtable<>();
			Hashtable sqlTextHT=new Hashtable<>();
			Hashtable segColumnNameHT=new Hashtable<>();
			int k=0;
			int l=0;
			Connection con = Globals.getDBConnection("Data_Connection");
			saveMess="";
			segmentAdministrationAL=new ArrayList<>();
			HeirarchyDataBean hrdb;
			segtableNameTM=new TreeMap<>();
			segcolumnNameTM=new TreeMap<>();
			segtableNameTM = getTableName1("Adminpopup",con);
			boolean oldChange = true;
			if(!Globals.getAttrVal4AttrName(hierNd, "Number_of_Segment").equals(""))
			noOfSegments = Integer.parseInt(Globals.getAttrVal4AttrName(hierNd, "Number_of_Segment"));
			for(int i=0;i<hierNdList.getLength();i++){
				if(hierNdList.item(i).getNodeName().equalsIgnoreCase("Segment_SQLS") && hierNdList.item(i).getNodeType() == Node.ELEMENT_NODE){
					oldChange = false;
					Node segmentSqlsNd=hierNdList.item(i);
					NodeList segmentSqlsNdList=segmentSqlsNd.getChildNodes();
					for(int j=0;j<segmentSqlsNdList.getLength();j++){
						if(segmentSqlsNdList.item(j).getNodeType() == Node.ELEMENT_NODE){
							displayNameHT.put(k, segmentSqlsNdList.item(j).getAttributes().getNamedItem("Display_Name").getNodeValue());
							sqlTextHT.put(k, segmentSqlsNdList.item(j).getTextContent());
							k++;
						}
					}
				}
				if(hierNdList.item(i).getNodeName().equalsIgnoreCase("Segment_Trigger_SQL") && hierNdList.item(i).getNodeType() == Node.ELEMENT_NODE){
					Node segmentTriggerSqlNd=hierNdList.item(i);
					NodeList segmentTriggerSqlNdList=segmentTriggerSqlNd.getChildNodes();
					for(int j=0;j<segmentTriggerSqlNdList.getLength();j++){
						if(segmentTriggerSqlNdList.item(j).getNodeType() == Node.ELEMENT_NODE && segmentTriggerSqlNdList.item(j).getNodeName().contains("Segment_")){
//							displayNameHT.put(k, segmentSqlsNdList.item(j).getAttributes().getNamedItem("Segment_SQLS").getNodeValue());
							segColumnNameHT.put(l, segmentTriggerSqlNdList.item(j).getTextContent());
							l++;
						}else if(segmentTriggerSqlNdList.item(j).getNodeType() == Node.ELEMENT_NODE && segmentTriggerSqlNdList.item(j).getNodeName().equalsIgnoreCase("TABLE_NAME")){
							segTableName=segmentTriggerSqlNdList.item(j).getTextContent();
							tableName[0]=segTableName;
							segcolumnNameTM = columnName1("",segTableName,con);
						}else if(segmentTriggerSqlNdList.item(j).getNodeType() == Node.ELEMENT_NODE && segmentTriggerSqlNdList.item(j).getNodeName().equalsIgnoreCase("COLUMN_NAME")){
							segcolumnName=segmentTriggerSqlNdList.item(j).getTextContent();
							columnName[0]=segcolumnName;
						}
					}
				}
			}
			
			if(oldChange){
				k=0;
				l=0;
				NodeList segCountList = confDoc.getElementsByTagName("No_Of_segments");
				
				noOfSegments = Integer.parseInt(segCountList.item(0).getTextContent());
				for(int i=0;i<segSqlNdList.getLength();i++){
					if(segSqlNdList.item(i).getNodeType() == Node.ELEMENT_NODE){
						displayNameHT.put(k, "SEGMENT_"+(k+1));
						String sql =segSqlNdList.item(i).getTextContent().replace("$$Column_Name1$$", Globals.getAttrVal4AttrName(segSqlNdList.item(i), "Column_Name1"));
						sql = sql.replace("$$Column_Name2$$", Globals.getAttrVal4AttrName(segSqlNdList.item(i), "Column_Name2"));
						sql = sql.replace("$$Table_Name$$", Globals.getAttrVal4AttrName(segSqlNdList.item(i), "Table_Name"));
						sqlTextHT.put(k, sql);
						k++;
					}
				}
				for(int j=0;j<segtrigSqlNdList.getLength();j++){
					if(segtrigSqlNdList.item(j).getNodeType() == Node.ELEMENT_NODE && segtrigSqlNdList.item(j).getNodeName().contains("Segment_")){
//						displayNameHT.put(k, segmentSqlsNdList.item(j).getAttributes().getNamedItem("Segment_SQLS").getNodeValue());
						segColumnNameHT.put(l, segtrigSqlNdList.item(j).getTextContent());
						l++;
					}else if(segtrigSqlNdList.item(j).getNodeType() == Node.ELEMENT_NODE && segtrigSqlNdList.item(j).getNodeName().equalsIgnoreCase("TABLE_NAME")){
						segTableName=segtrigSqlNdList.item(j).getTextContent();
						tableName[0]=segTableName;
						segcolumnNameTM = columnName1("",segTableName,con);
					}else if(segtrigSqlNdList.item(j).getNodeType() == Node.ELEMENT_NODE && segtrigSqlNdList.item(j).getNodeName().equalsIgnoreCase("COLUMN_NAME")){
						segcolumnName=segtrigSqlNdList.item(j).getTextContent();
						columnName[0]=segcolumnName;
					}
				}
			}
//			if(tableName[0].equals("") && tableName[0]!=null && tableName[0].equals("null")){
//				
//			}
			for(int i=0;i<segColumnNameHT.size();i++){
				hrdb=new HeirarchyDataBean("Segment_"+(i+1),String.valueOf(displayNameHT.get(i)),String.valueOf(sqlTextHT.get(i)),segcolumnNameTM,String.valueOf(segColumnNameHT.get(i)).toUpperCase());
				segmentAdministrationAL.add(hrdb);
			}
			
			System.out.println("displayNameHT===>"+displayNameHT);
			System.out.println("sqlTextHT===>"+sqlTextHT);
			System.out.println("segColumnNameHT===>"+segColumnNameHT);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
	public void getColumnNames(String selectedTypes,String connectionType){
		try{
			if(selectedTypes.equals("SegmentAdmin")){
				if(!segmentAdministrationAL.isEmpty()){
//					segmentAdministrationAL = new ArrayList<>();
					HeirarchyDataBean hrdb;
				for(int i=0;i<segmentAdministrationAL.size();i++){
					hrdb=(HeirarchyDataBean)segmentAdministrationAL.get(i);
					hrdb.segColumnName = "";
					
				}
				}
			}
			Connection con = Globals.getDBConnection(connectionType);
			if(selectedTypes.equalsIgnoreCase("SegAdmin")){
				segcolumnNameTM = columnName1("data",tableName[0],con);
			}else if(selectedTypes.equalsIgnoreCase("dataGen")){
				columnNameTM4Period = columnName1("data",tableName[0],con);
			}else if(selectedTypes.equalsIgnoreCase("directSQL")){
				columnNameTM4DirectSQL = columnName1("directSQL", selectedTableName4DirectSQL[0], con);
			}else{
				columnNameTM = columnName1("data",tableName[0],con);
			}
		
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public void redirect4Segment(String hierId, boolean render4SegPopup) {
		try{
			if(render4SegPopup){
				saveSegmentAdministration(hierId);
			}else{
				writeGenDataToXml("","");
			}
			
		}catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	
	
	public void saveSegmentAdministration(String hierID) {
		System.out.println("Entering: "
				+ new Exception().getStackTrace()[0].getClassName() + "."
				+ new Exception().getStackTrace()[0].getMethodName());
		try{
			
			PropUtil prop=new PropUtil();
			String hierLevelXMLFileName=prop.getProperty("HIERARCHY_XML_FILE");
			String hierConfigXMLFileName=prop.getProperty("HIERARCHY_CONFIG_FILE");
			String hierXMLDir=prop.getProperty("HIERARCHY_XML_DIR");
//			System.out.println("segmentColumnName===>"+segmentColumnName);
//			String
			
			System.out.println("hierID====>"+hierID);
			Document doc=Globals.openXMLFile(hierXMLDir, hierLevelXMLFileName);
//			NodeList ndList=doc.getElementsByTagName("Hierarchy_Level");
			Node hierNd = Globals.getNodeByAttrVal(doc, "Hierarchy_Level", "Hierarchy_ID", hierID);
			Element hierNdEle = (Element)hierNd;
			hierNdEle.setAttribute("Number_of_Segment", String.valueOf(noOfSegments));
			NodeList hierNdList=hierNd.getChildNodes();
			for(int i=0;i<hierNdList.getLength();i++){
				if(hierNdList.item(i).getNodeName().equalsIgnoreCase("Segment_SQLS") || hierNdList.item(i).getNodeName().equalsIgnoreCase("Segment_Trigger_SQL")){
					hierNd.removeChild(hierNdList.item(i));
				}
			}
			
			Element segmentSQLsEle=doc.createElement("Segment_SQLS");
			hierNd.appendChild(segmentSQLsEle);
			Node segmentSQLNd=(Node)segmentSQLsEle;
			
			Element segmentTriggerSQLEle=doc.createElement("Segment_Trigger_SQL");
			hierNd.appendChild(segmentTriggerSQLEle);
			Node segmentTriggerSQLNd=(Node)segmentTriggerSQLEle;
			
			Element SQLEle=doc.createElement("SQL");
			SQLEle.setTextContent("Select $$COLUMN_NAME$$ from $$TABLE_NAME$$ T91397");
			segmentTriggerSQLNd.appendChild(SQLEle);
			
			Element tableNameSQLEle=doc.createElement("TABLE_NAME");
			tableNameSQLEle.setTextContent(tableName[0]);
			segmentTriggerSQLNd.appendChild(tableNameSQLEle);
			
			Element columnNameSQLEle=doc.createElement("COLUMN_NAME");
			columnNameSQLEle.setTextContent(columnName[0]);
			segmentTriggerSQLNd.appendChild(columnNameSQLEle);
			
			
			for(int i=0;i<segmentAdministrationAL.size();i++){
				HeirarchyDataBean HDB=(HeirarchyDataBean) segmentAdministrationAL.get(i);
				Element segmentSQLEle=doc.createElement("Segment_SQL"+(i+1));
				segmentSQLEle.setAttribute("Display_Name", HDB.getDisplayName());
				Hashtable sqlInfomationHT = gettingSqlInformation(HDB.getSqltext());		//code change Vishnu 17Mar2014
				segmentSQLEle.setAttribute("Table_Name", String.valueOf(sqlInfomationHT.get("tableName")));
				segmentSQLEle.setAttribute("Column_Name1", String.valueOf(sqlInfomationHT.get("columnName0")));
				segmentSQLEle.setAttribute("Column_Name2", String.valueOf(sqlInfomationHT.get("columnName1")));
				segmentSQLEle.setTextContent(HDB.getSqltext());
				segmentSQLNd.appendChild(segmentSQLEle);
//				System.out.println("segmentAdministrationAL.get("+i+")====>"+HDB.getSegmentid());
//				System.out.println("segmentAdministrationAL.get("+i+")====>"+HDB.getDisplayName());
//				System.out.println("segmentAdministrationAL.get("+i+")====>"+HDB.getSqltext());
//				System.out.println("segmentAdministrationAL.get("+i+")====>"+HDB.getSegColumnName());
				Element segmentEle=doc.createElement("Segment_"+(i+1));
				segmentEle.setTextContent(HDB.getSegColumnName());
				segmentTriggerSQLNd.appendChild(segmentEle);
			}
			
			if(missingConfigFlag){
				missingConfigFlag=false;

			}
			
			Globals.writeXMLFile(doc, hierXMLDir, hierLevelXMLFileName);
			saveMess="Segment configurations are saved successfully";
		}catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Exiting: "
				+ new Exception().getStackTrace()[0].getClassName() + "."
				+ new Exception().getStackTrace()[0].getMethodName());
		}
	
	public Hashtable gettingSqlInformation(String sqlText) {		//code change Vishnu 17Mar2014
		Hashtable sqlInfomationHT=new Hashtable<>();
		try{
			System.out.println("sqlText =="+sqlText);
			if(sqlText.equals("")){
				return new Hashtable<>();
			}
		   String sql = sqlText.toLowerCase();  //23MAR14
		   String tableNameTemp= sql.split(" from ")[1].trim();
		   String tableName=tableNameTemp.split(" ")[0];
		   String columnNamesTemp = sql.split(" from ")[0].trim();
		   String columnName1 = columnNamesTemp.split(",")[1].trim();
		   String columnName0Temp = columnNamesTemp.split(",")[0].trim();
		   String columnName0 = columnName0Temp.substring(columnName0Temp.lastIndexOf(" ")+1).trim();
		   
		   System.out.println(tableName);
		   System.out.println(columnName0);
		   System.out.println(columnName1);
		   
		   sqlInfomationHT.put("tableName", tableName);
		   sqlInfomationHT.put("columnName0", columnName0);
		   sqlInfomationHT.put("columnName1", columnName1);
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return sqlInfomationHT;
	}
	
		String mess="";
	
	public String getMess() {
			return mess;
		}

		public void setMess(String mess) {
			this.mess = mess;
		}

	public String saveMess="";
	public String getSaveMess() {
		return saveMess;
	}

	public void setSaveMess(String saveMess) {
		this.saveMess = saveMess;
	}
    public void closeAdministration(){
    	saveMess="";
    }
	public void deleteSelectedSegments4Administration() {
		try{
//			
			System.out.println("mess==========>"+mess);
			HeirarchyDataBean hrdb1;
			HeirarchyDataBean hrdb;
			for(int i=0;i<segmentValueList1.size();i++){
				for(int j=0;j<segmentAdministrationAL.size();j++){
					hrdb=(HeirarchyDataBean)segmentValueList1.get(i);
					hrdb1=(HeirarchyDataBean)segmentAdministrationAL.get(j);
					if(hrdb.getSegmentid().equals(hrdb1.getSegmentid())){
						hrdb1=(HeirarchyDataBean)segmentAdministrationAL.remove(j);
					}
				}
					
			}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	
	
	
	//start code change Jayaramu 18FEB14
	
	
	public void loadPeriodColumnValues(){
		try{
		PropUtil prop=new PropUtil();
		String dir=prop.getProperty("HIERARCHY_XML_DIR");
		String hierarchyFileName = prop.getProperty("HIERARCHY_CONFIG_FILE");
		Document doc=Globals.openXMLFile(dir, hierarchyFileName);
		
		String hstName="";
		String portNo="";
		String DBName="";
		String userName="";
		String passWord="";
		String sql="";
		
		String id = "/Config/row_wid_SQL";
		String ConfigFleName = "Hierarchy_Config.xml";
		try{
			String filepath = prop.getProperty("HIERARCHY_XML_DIR");
			FileInputStream fis = new FileInputStream(filepath + ConfigFleName);
			  DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
			  DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
			  Document doc1 = docBuilder.parse((fis));

			  XPathFactory xPathfactory = XPathFactory.newInstance();
			  XPath xpath = xPathfactory.newXPath();
			  XPathExpression expr = xpath.compile(id);
			  
			  Object exprResult = expr.evaluate(doc1, XPathConstants.NODESET);
				NodeList nodeList = (NodeList) exprResult;
				Node n=nodeList.item(0);
				sql=n.getTextContent()+" ORDER BY mcal_period_wid";
		  
			 
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
		
		periodValuesAL = new ArrayList<>();
		copyPeriodValuesAL = new ArrayList<>();
//		selectedPeriodValues = new String();     
		Connection conn = Globals.getDBConnection("DW_Connection");
		if(conn == null)
			return;
		Statement stmt = conn.createStatement();
		PreparedStatement pre = conn.prepareStatement(sql);
//		String SqlQuery = "SELECT"+" "+periodcolumn+" "+"FROM"+" "+tableName[0];
		System.out.println("sql====>>>"+sql);
		ResultSet rs = pre.executeQuery();
		while(rs.next()){
		
			String value = rs.getString("MCAL_PERIOD_NAME");  // code change Menaka 14FEB2014
			System.out.println("value====>>>"+value);
			if(!periodValuesAL.contains(value)){
				periodValuesAL.add(value);
				copyPeriodValuesAL.add(value);
				periodValuesAL.removeAll(selectedPeriodValuesAL);
				copyPeriodValuesAL.removeAll(copySelectedPeriodValuesAL);
			}
			
		}
		
		stmt.close();
		rs.close();
		conn.close();
		
		System.out.println("periodValuesAL====>>>"+periodValuesAL);
		
		
		}catch(Exception e){
			
			e.printStackTrace();
		}
		
	}
	
	
	
	
	
	//start code change Jayaramu 17FEB14
//	public void setsegValues2Field(){
//		
//		this.flag4AddData = "fromSegment";
//if(selectedsegmentvalue != null && !selectedsegmentvalue.equals("") && selectedsegmentvalue.length() > 0){
//			if(datasegValues == null){
//				
//				datasegValues="";
//			}
//	System.out.println("datasegValues====>>>>>"+datasegValues);
//	System.out.println("selectedsegmentvalue====>>>>>"+selectedsegmentvalue);
//	
//			if(!datasegValues.contains(selectedsegmentvalue)){
//			
//				datasegValues = datasegValues.concat(selectedsegmentvalue+";");
//				
//				
//				String segmentVal[] = selectedsegmentvalue.split(";");
//				
//				String segName = selectedsegment.substring(0,7);
//				
//				int sublen = selectedsegment.length()-8;
//				String segNumber = selectedsegment.substring(selectedsegment.length()-sublen);
//				
//				String segAttr = segName+"_"+segNumber;
//				
//				codeValue = segAttr+" "+"Code";
//				nameValue = segAttr+" "+"Value";
//					String value = segmentVal[0].substring(segmentVal[0].indexOf("(")+1,segmentVal[0].indexOf(")"));
//					String code = segmentVal[0].substring(0,segmentVal[0].indexOf("("));
//				
//					 hdb =new HeirarchyDataBean(code,value);
//					 codeandNameAL.add(hdb);
//				
//			}
//		}
//		
//		
//		
//	}//end code change Jayaramu 17FEB14
	//start code change Jayaramu 18FEB14
public void addTosegmenttable(){
		
	
	
	try{
	
		if(segSelectedsegmentvalue.length<=0){   // code change Menaka 28MAR2014
			message="Select value(s) to add";
			return;
		}
		
		message="";

		HeirarchyDataBean hdb = new HeirarchyDataBean();
		int segNo = 0;
		
		 FacesContext ctx = FacesContext.getCurrentInstance();
			ExternalContext extContext = ctx.getExternalContext();
			Map sessionMap = extContext.getSessionMap();
			HierarchyBean hryb = (HierarchyBean) sessionMap.get("hierarchyBean");

	if(hryb.getHierarchyNameList().containsValue(hryb.getHirename())){
				
				System.out.println("Hierarchy Name already exists.. Please Enter another Name...");
				return;
				
			}
			
			
			PropUtil prop=new PropUtil();
			String dir=prop.getProperty("HIERARCHY_XML_DIR");
			Document doc = Globals.openXMLFile(dir, hierarchyXMLFileName);
			Node rootnode = null;
			String concatvalues="";
			Node appndchild = null;
			Node appenChild = null;
			int k=0;
			String nodeValue = "";
			Node ChildElement = null;
			if(segSelectedsegmentvalue.length>0){
				
//				Node deleteExistsSegments = Globals.getNodeByAttrVal(doc, "Segments", "ID", hryb.getHierarchy_ID());
//				if(deleteExistsSegments != null){
//					
//					deleteExistsSegments.getParentNode().removeChild(deleteExistsSegments);
//					
//				}

				if(hryb.getFlagNew().equals("newAdded")){
					
					hryb.addHierarchyNodes("Fromsegment",false);
					hryb.setFlagNew("alreadyadded");
					 doc = Globals.openXMLFile(dir, hierarchyXMLFileName);
				
				}
				
				rootnode = Globals.getNodeByAttrVal(doc, "Hierarchy_Level", "Hierarchy_ID", hryb.getHierarchy_ID());
				System.out.println("rootnode====>>>>"+rootnode);
				
				
				if(rootnode.getNodeType() == Node.ELEMENT_NODE){
					
					Element rootndElement=(Element)rootnode;
					rootndElement.setAttribute("Number_of_Segment", String.valueOf(noOfSegments));
					appndchild = rootnode.getLastChild(); 
					
					System.out.println("appndchild---->>>"+appndchild);
					
					ChildElement = null;
					Element element = null;
					int sublen = segSelectedsegment.length()-8;
					String segmentNumber = segSelectedsegment.substring(segSelectedsegment.length()-sublen);
					Node segmentRootNode = Globals.getNodeByAttrVal(doc, "Segments", "ID", hryb.getHierarchy_ID());
					
					System.out.println("segmentRootNode---->>>"+segmentRootNode);
					
					if(segmentRootNode == null){
						Element rootElement = doc.createElement("Segments");
						rootnode.appendChild(rootElement);
						appenChild  = (Node)rootElement;
//						rootElement.setAttribute("Status", segmentStatus);
						rootElement.setAttribute("ID", hryb.getHierarchy_ID());
						Globals.writeXMLFile(doc, dir, hierarchyXMLFileName);
						doc = Globals.openXMLFile(dir, hierarchyXMLFileName);
						segmentRootNode = Globals.getNodeByAttrVal(doc, "Segments", "ID", hryb.getHierarchy_ID());
						appenChild = segmentRootNode;
					
					}else{
						
						appenChild = segmentRootNode;
						
					}
					
					int checknodeExists=0;
					NodeList segChildNodes = segmentRootNode.getChildNodes();
					for(int j=0;j<segChildNodes.getLength();j++){
						
						if(segChildNodes.item(j).getNodeName().equals("Segment_"+segmentNumber)){
							
							ChildElement = segChildNodes.item(j);
							nodeValue = segChildNodes.item(j).getTextContent().toString();
							
							checknodeExists++;
						}
					}
					
					if(checknodeExists == 0){
						
						ChildElement = doc.createElement("Segment_"+segmentNumber);
						appenChild.appendChild(ChildElement);
						
					}
					
					
				}
			}
			
			else{
				System.out.println("Please Select atleast one value to create segment.");
				return;
			}
			String segmentValuesWithcode = "";
			String withOutDuplicateValueWithcode = "";
			String add2XmlWithoutDuplicate[];
		for(int i=0;i<segSelectedsegmentvalue.length;i++){
			
			if(segSelectedsegmentvalue[i].equals(segErrorMsg)){
				
				segInfoMsg = "Error occur can't add";
				return;
			}else if(segSelectedsegmentvalue[i].equals("No result(s) found")){
				
				segInfoMsg = "No result(s) found, so can't add";
				return;
			}
			
			
			
			
			if(!segSelectedsegmentvalue[i].equals(segErrorMsg) && !segSelectedsegmentvalue[i].equals("No result(s) found")){
			String segmentCode = segSelectedsegmentvalue[i].substring(segSelectedsegmentvalue[i].indexOf("(")+1, segSelectedsegmentvalue[i].indexOf(")"));
			String segmentValue = segSelectedsegmentvalue[i].substring(segSelectedsegmentvalue[i].indexOf(")")+1,segSelectedsegmentvalue[i].length());
			segmentValuesWithcode = segmentValuesWithcode+segmentCode+"("+segmentValue+")".concat(";");
			segInfoMsg = "Successfully added to the hierarchy.";
			System.out.println("segSelectedsegmentvalue[i]===>>>"+segSelectedsegmentvalue[i]);
			System.out.println("segmentCode===>>>"+segmentCode);
			System.out.println("segmentValue===>>>"+segmentValue);
			String segmentID="";
			
			
			 k++;
				
				String separator = segmentCode+"#$#"+segmentValue;
				int dub = 0;
				add2XmlWithoutDuplicate = nodeValue.split("~");
				for(int m=0;m<add2XmlWithoutDuplicate.length;m++)
				{
					if(separator.equals(add2XmlWithoutDuplicate[m])){
				
						dub++;
						
				}}
				
				if(dub == 0){
					
				concatvalues = concatvalues+separator.concat("~");
				System.out.println("concatvalues----->>>"+concatvalues);
				}
				
				
			 
			}	
			
			
		}
		segNo++;
		
		System.out.println("segmentListAL---->>>"+segmentListAL);
		System.out.println("Default---->>>"+segSelectedsegment+" Default");
		segNo = Integer.parseInt(segSelectedsegment.substring(segSelectedsegment.length()-1));
		
		System.out.println("segNo---------->"+segNo);
		String[] dublicateValues = segmentValuesWithcode.split(";");
		for(int n=0;n<segmentListAL.size();n++){
			
		HeirarchyDataBean segALobject=(HeirarchyDataBean)segmentListAL.get(n);
		System.out.println("segmentListAL---------->"+segALobject.getSegmentNumber());
		
if(segALobject.getSegmentNumber().contains(segSelectedsegment+" Default")){
		
	
	segmentValuesWithcode =segmentValuesWithcode+segALobject.getSegmentValue();
	segNo = segALobject.getNumberOfSegment();
	segmentListAL.remove(segALobject);
				
	String[] findDublicate = segALobject.getSegmentValue().split(";");
		
	
	for(int i=0;i<dublicateValues.length;i++){
		
		int m=0;
		for(int j=0;j<findDublicate.length;j++){
			
			if(dublicateValues[i].equals(findDublicate[j])){
				
				m++;
}
			if(m == 0){
				
			withOutDuplicateValueWithcode = dublicateValues[i]+withOutDuplicateValueWithcode.concat(";");
		}
		}
	
	}
	
	segmentValuesWithcode =segALobject.getSegmentValue()+withOutDuplicateValueWithcode;
	
}}
		Hashtable ht = new Hashtable<>();
		Collection c = getNumberOfSegmentAL().keySet();
		Iterator itr = c.iterator();
		while(itr.hasNext()){
			String temp = (String)itr.next();
//			System.out.println("itr.next()===>"+itr.next());
//			System.out.println("String.valueOf(segmentAL.get(itr.next()))===>"+String.valueOf(segmentAL.get(String.valueOf(itr.next()))));
			ht.put(String.valueOf(getNumberOfSegmentAL().get(temp)), temp);
		}
		System.out.println("ht--------->>>>"+ht);
		
		hdb =new HeirarchyDataBean(segNo,segSelectedsegment+" Default",segmentValuesWithcode,String.valueOf(ht.get(segSelectedsegment)));
		segmentListAL.add(hdb);
		ChildElement.setTextContent(nodeValue+concatvalues);
		Globals.writeXMLFile(doc, dir, hierarchyXMLFileName);
		System.out.println("segmentValuesHT---->>>"+segmentValuesHT);
		message = "Segment added successfully";
		retrieveSegmentsFromXml();
	}
		
		catch(Exception e){
			e.printStackTrace();
		}
		
	}//end code change Jayaramu 18FEB14
//start code change Jayaramu 18FEB14
public void loadSegmentValues(String hierID)
{
	try{
		FacesContext ctx = FacesContext.getCurrentInstance();
		ExternalContext extContext = ctx.getExternalContext();
		Map sessionMap = extContext.getSessionMap();
		HierarchyBean hryb = (HierarchyBean) sessionMap.get("hierarchyBean");
		
if(hryb.getHierarchyNameList().containsValue(hryb.getHirename())){
			
			System.out.println("Hierarchy Name already exists.. Please Enter another Name...");
			return;
			
		}
		
		
	PropUtil prop=new PropUtil();
	String dir=prop.getProperty("HIERARCHY_XML_DIR");
	Document doc = Globals.openXMLFile(dir, hierarchyXMLFileName);
//	
	Node segmentRootNode = Globals.getNodeByAttrVal(doc, "Hierarchy_Level", "Hierarchy_ID", hierID);
	Element RootEle=(Element)segmentRootNode;
	RootEle.setAttribute("Number_of_Segment", String.valueOf(noOfSegments));
//	
//	if(segmentRootNode != null){
//		
//		segmentRootNode.getParentNode().removeChild(segmentRootNode);
//		
//	}
//	
//	if(hryb.getFlagNew().equals("newAdded")){
//		
//		
//		
//		
//		hryb.addHierarchyNodes("Fromsegment");
//		doc = Globals.openXMLFile(dir, hierarchyXMLFileName);
//		hryb.setFlagNew("Alreadyadded");
//		
//	}
//	Node rootnode = Globals.getNodeByAttrVal(doc, "Hierarchy_Level", "Hierarchy_ID", hryb.getHierarchy_ID());
//	Element rootndElement=(Element)rootnode;
//	rootndElement.setAttribute("Number_of_Segment", String.valueOf(noOfSegments));
//	Node appndchild = rootnode.getLastChild();
//	Node appenChild = null;
//	Element ChildElement = null;
//	if(rootnode != null && rootnode.getNodeType() == Node.ELEMENT_NODE ){
//		System.out.println("appndchild---->>>"+appndchild);
//		Element rootElement = doc.createElement("Segments");
//		rootnode.appendChild(rootElement);
//		appenChild  = (Node)rootElement;
//		rootElement.setAttribute("Status", segmentStatus);
//		rootElement.setAttribute("ID", hryb.getHierarchy_ID());
//	}
//	numberOfSegmentAL = new ArrayList<>();
////	segmentValues4SegPopupAL = new ArrayList<>();
////	segmentListAL = new ArrayList<>();
//	for(int i=1;i<=noOfSegments;i++){
//		
//		
////		ChildElement = doc.createElement("Segment_"+ i);
////		appenChild.appendChild(ChildElement);
//		numberOfSegmentAL.add("Segment "+i);
//		
//		
//		
//		
//	}
	Globals.writeXMLFile(doc, dir, hryb.getHierarchyXmlFileName());

	dispSegmentAdmin("true");
	// code change Menaka 02MAY2014
//	segmentAdministrationAL= new ArrayList<>();
//	tableName=null;
//	columnName=null;
//	segTableName=null;
//	segcolumnName=null;
	
	
	
	}catch(Exception e){
		
		e.printStackTrace();
	}
	
	
	
}//end code change Jayaramu 18FEB14
	
	//code change Jayaramu 20FEB14
	public void deleteSelectedDataSegments(){
		try{
			PropUtil prop=new PropUtil();                           
			String dir=prop.getProperty("HIERARCHY_XML_DIR");
			String hierarchyFileName = prop.getProperty("HIERARCHY_XML_FILE");
			Document doc=Globals.openXMLFile(dir, hierarchyFileName);
			FacesContext ctx = FacesContext.getCurrentInstance();
			ExternalContext extContext = ctx.getExternalContext();
			Map sessionMap = extContext.getSessionMap();
			HierarchyBean hryb = (HierarchyBean) sessionMap.get("hierarchyBean");
			String selectedRowID = "";
			String selectedNode = "";
			if( hryb.getSelectedRows().size()>0){
				selectedRowID = hryb.getSelectedRows().get(0).getID();
				selectedNode = hryb.getSelectedRows().get(0).getLevelNode();
			}else{
				System.out.println("Please select row to delete");
				return;
			}
			Node DataRootNode = Globals.getNodeByAttrVal(doc, selectedNode, "ID", selectedRowID);
			NamedNodeMap attributes = null;
			String segNamesAttribute = "";
			Node primeNode = null;
			if(DataRootNode != null){
			attributes = DataRootNode.getAttributes();
			 segNamesAttribute = attributes.getNamedItem("Segment").getTextContent().toString();
			 primeNode = attributes.getNamedItem("Primary_Segment");
			}
			String primarySeg = "";
			String primarySegment = "";
			if(primeNode != null){
				primarySeg = attributes.getNamedItem("Primary_Segment").getTextContent().toString();
				primarySegment = primarySeg.substring(primarySeg.indexOf("_")+1);
			}
			for(int i=0;i<runReportSessionslist.size();i++){
				
				codeandNameAL.remove(runReportSessionslist.get(i));
				String segNo = runReportSessionslist.get(i).getSegments();
				String segmentName = segNo.replace(" ", "_");
				segNo = segNo.substring(segNo.length()-1);
				if(DataRootNode != null){
			String splitsegNanme[] = segNamesAttribute.split("\\$~\\$");	
				System.out.println("splitsegNanme...."+splitsegNanme.length);
				 Element element = (Element)DataRootNode;
				if(primarySegment.equals(segNo)){
					 attributes.removeNamedItem("Primary_Segment");
					 element.setAttribute("Primary_Segment", "");
				}
				if(segNamesAttribute.contains("$~$")){
					
					Node segCodeisExists = attributes.getNamedItem("seg"+segNo+"_Code");
					if(segCodeisExists != null){
			    attributes.removeNamedItem("seg"+segNo+"_Code");
			    attributes.removeNamedItem("seg"+segNo+"_value");
			    if(attributes.getNamedItem("value") != null){
			    	 attributes.removeNamedItem("value");
			    }
			   
			    element.setAttribute("Segment", segNamesAttribute.replace(segmentName, ""));
			    element.setAttribute("Segment", segNamesAttribute.replace(segmentName+"$~$", ""));
					}
				}else{
				Node parentNode = DataRootNode.getParentNode();
				parentNode.removeChild(DataRootNode);
				}
				System.out.println("Deleted Successfully....");
				
			}
			
			} 
			Globals.writeXMLFile(doc, dir, hierarchyXMLFileName);
			}catch(Exception e){
				e.printStackTrace();
			}
			
			
		
		
	}//end code change Jayaramu 20FEB14
	
	public void deleteSelectedSegments(){///////start code change Jayaramu 20FEB14
		
		try{
			
			// code change Menaka 29MAR2014
			if(segmentListAL==null||segmentListAL.size()<=0){
				this.message = "There is no segment to Delete";  
				return;
			}
			
			if(segmentValueList.size()<=0){  
				this.message = "Select at least one segment to Delete.";  
				return;
			}
			
			
			FacesContext ctx = FacesContext.getCurrentInstance();
			ExternalContext extContext = ctx.getExternalContext();
			Map sessionMap = extContext.getSessionMap();
			HierarchyBean hryb = (HierarchyBean) sessionMap.get("hierarchyBean");
			PropUtil prop=new PropUtil();
			String dir=prop.getProperty("HIERARCHY_XML_DIR");
			String configFileName=prop.getProperty("HIERARCHY_CONFIG_FILE");
			Document doc = null;
			Node segmentRootNode = null;
			String xmlFileName = hierarchyXMLFileName;
			if(segmentValueList.size() > 0){
				
			 doc = Globals.openXMLFile(dir, hierarchyXMLFileName);
			 segmentRootNode = Globals.getNodeByAttrVal(doc, "Segments", "ID", hryb.getHierarchy_ID());
			 if(segmentRootNode == null){
				 
				 doc = Globals.openXMLFile(dir, configFileName);
				 NodeList segParentNode = doc.getElementsByTagName("Segments");
				 segmentRootNode = segParentNode.item(0);
				 xmlFileName = configFileName;
			 }
			 
			}
			System.out.println("segmentValueList NAME---->>>>"+segmentValueList.size());
			for(int i=0;i<segmentValueList.size();i++){
				
				segmentListAL.remove(segmentValueList.get(i));
				String segmentNumber = segmentValueList.get(i).getSegmentNumber().substring(0, segmentValueList.get(i).getSegmentNumber().indexOf(" Default")).replace(" ", "_");
				NodeList segmentChildNodes = segmentRootNode.getChildNodes();
				for(int j=0;j<segmentChildNodes.getLength();j++){
					System.out.println("segmentChildNodes.item(j).getNodeName() NAME---->>>>"+segmentChildNodes.item(j).getNodeName());
					System.out.println("segmentName---->>>>"+segmentNumber);
					
					if(segmentChildNodes.item(j).getNodeName().equals(segmentNumber)){

						segmentRootNode.removeChild(segmentChildNodes.item(j));
						System.out.println("deleted Succcessfully....");
						
						
					}
					
				}
				
				
				
			}
			Globals.writeXMLFile(doc, dir, xmlFileName);
			this.message = "Segment(s) deleted successfully.";
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}//end code change Jayaramu 20FEB14
	boolean incrmatchCase4TableSelect;
	public boolean isIncrmatchCase4TableSelect() {
		return incrmatchCase4TableSelect;
	}
	public void setIncrmatchCase4TableSelect(boolean incrmatchCase4TableSelect) {
		this.incrmatchCase4TableSelect = incrmatchCase4TableSelect;
	}
	//start code change Jayaramu 20FEB14
	public void filterFacts(String filterFrom){
				
		try{
			
			this.message = "";
		if(filterFrom.equals("filterTables")){
		
				if(selectedOperator == null || selectedOperator.equals("") || selectedOperator.equals("null")){
					
					this.message = "Please select operator to filter fact table(s).";
					color4FactMsg = "red";
					return;
				}else if(copyTableNameTM == null || copyTableNameTM.isEmpty()){
					this.message = "No data(s) to filter.";
					color4FactMsg = "red";
					return;
				}
				String filterValue = this.filterValue; //code change Jayaramu 19APR14
		if(selectedOperator.equals("Contains")){
		
			
			tableNameTM.putAll(copyTableNameTM);
			Set set = tableNameTM.entrySet();
			tableNameTM = new TreeMap<>();
			// Get an iterator 
			Iterator i = set.iterator(); 
			// Filter elements 
			System.out.println("filterValue====>>>>"+filterValue); 
			System.out.println("matchCase4TableSelect====>>>>"+matchCase4TableSelect); 
			while(i.hasNext()) { 
			Map.Entry me = (Map.Entry)i.next(); 
			System.out.print(me.getKey() + ": "); 
			System.out.println(me.getValue()); 
			String contains = (String) me.getValue();
			if(matchCase4TableSelect){
//				filterValue = filterValue;
			}else{
				filterValue = filterValue.toUpperCase();
				contains = contains.toUpperCase();
			}
			if(contains.contains(filterValue)){
				tableNameTM.put(contains, contains);
			}
			
			} 
		}
		
		if(selectedOperator.equals("Starts With")){
			
		
			tableNameTM.putAll(copyTableNameTM);
			Set set = tableNameTM.entrySet(); 
			tableNameTM = new TreeMap<>();
			// Get an iterator 
			Iterator i = set.iterator(); 
			// Filter elements 
			System.out.println("filterValue====>>>>"+filterValue+"selectedOperator-====>>>"+selectedOperator); 
			
			while(i.hasNext()) { 
			Map.Entry me = (Map.Entry)i.next(); 
			System.out.print(me.getKey() + ": "); 
			System.out.println(me.getValue()); 
			String startswith = (String) me.getValue();
			
			System.out.println("startswith===>>>"+startswith); 
			if(matchCase4TableSelect){
//				filterValue = filterValue;
			}else{
				filterValue = filterValue.toUpperCase();
				startswith = startswith.toUpperCase();
			}
			if(startswith.startsWith(filterValue)){
				
				System.out.println("startswith filterValue===>>>"+startswith); 
				tableNameTM.put(startswith, startswith);
			} 
		}

		}
		
		if(selectedOperator.equals("Ends With")){
		

			tableNameTM.putAll(copyTableNameTM);
			Set set = tableNameTM.entrySet();
			tableNameTM = new TreeMap<>();
			// Get an iterator 
			Iterator i = set.iterator(); 
			// Display elements 
			System.out.println("filterValue====>>>>"+filterValue); 
			while(i.hasNext()) { 
			Map.Entry me = (Map.Entry)i.next(); 
			System.out.print(me.getKey() + ": "); 
			System.out.println(me.getValue()); 
			String endswith = (String) me.getValue();
			if(matchCase4TableSelect){
//				filterValue = filterValue;
			}else{
				filterValue = filterValue.toUpperCase();
				endswith = endswith.toUpperCase();
			}
			if(endswith.endsWith(filterValue)){
				
				tableNameTM.put(endswith, endswith);
			}
			
			}
			} 
		
		if(selectedOperator.equals("All Values")){
			
			tableNameTM = new TreeMap<>();
			tableNameTM = copyTableNameTM;
			
		}
		}
			else if(filterFrom.equals("selectedTablesFilter")){
		
if(selectedTableOperator == null || selectedTableOperator.equals("") || selectedTableOperator.equals("null")){
					
					this.message = "Please select operator to filter selected table(s).";
					color4FactMsg = "red";
					return;
				}else if(copySelectedTableNameTM == null || copySelectedTableNameTM.isEmpty()){
					this.message = "No data(s) to filter.";
					color4FactMsg = "red";
					return;
					
				}

				if(selectedTableOperator.equals("Contains")){
					
					
					selectedTableNameTM.putAll(copySelectedTableNameTM);
					Set set = selectedTableNameTM.entrySet();
					selectedTableNameTM = new TreeMap<>();
					// Get an iterator 
					Iterator i = set.iterator(); 
					// Filter elements 
					System.out.println("selectedTablefilterValue====>>>>"+selectedTablefilterValue); 
					while(i.hasNext()) { 
					Map.Entry me = (Map.Entry)i.next(); 
					System.out.print(me.getKey() + ": "); 
					System.out.println(me.getValue()); 
					String contains = (String) me.getValue();
					if(contains.contains(selectedTablefilterValue)){
						selectedTableNameTM.put(contains, contains);
					}
					
					} 
				}
				
				if(selectedTableOperator.equals("Starts With")){
					
				
					selectedTableNameTM.putAll(copySelectedTableNameTM);
					Set set = selectedTableNameTM.entrySet(); 
					selectedTableNameTM = new TreeMap<>();
					// Get an iterator 
					Iterator i = set.iterator(); 
					// Filter elements 
					System.out.println("selectedTablefilterValue====>>>>"+selectedTablefilterValue+"selectedOperator-====>>>"+selectedTableOperator); 
					
					while(i.hasNext()) { 
					Map.Entry me = (Map.Entry)i.next(); 
					System.out.print(me.getKey() + ": "); 
					System.out.println(me.getValue()); 
					String startswith = (String) me.getValue();
					
					System.out.println("startswith===>>>"+startswith); 
					
					if(startswith.startsWith(selectedTablefilterValue)){
						
						System.out.println("startswith filterValue===>>>"+startswith); 
						selectedTableNameTM.put(startswith, startswith);
					} 
				}

				}
				
				if(selectedTableOperator.equals("Ends With")){
				

					selectedTableNameTM.putAll(copySelectedTableNameTM);
					Set set = selectedTableNameTM.entrySet();
					selectedTableNameTM = new TreeMap<>();
					// Get an iterator 
					Iterator i = set.iterator(); 
					// Display elements 
					System.out.println("selectedTablefilterValue====>>>>"+selectedTablefilterValue); 
					while(i.hasNext()) { 
					Map.Entry me = (Map.Entry)i.next(); 
					System.out.print(me.getKey() + ": "); 
					System.out.println(me.getValue()); 
					String endswith = (String) me.getValue();
					if(endswith.endsWith(selectedTablefilterValue)){
						
						selectedTableNameTM.put(endswith, endswith);
					}
					
					}
					} 
				
				if(selectedTableOperator.equals("All Values")){
					
					selectedTableNameTM = new TreeMap<>();
					selectedTableNameTM = copySelectedTableNameTM;
					
				}
				
			}
		
			else if(filterFrom.equals("measureColumnFilter")){
			
			if(selectedOperator == null || selectedOperator.equals("") || selectedOperator.equals("null")){
				
				this.message = "Please select operator to filter measure column(s).";
				color4FactMsg = "red";
				return;
			}else if(copyColumnNameTM == null || copyColumnNameTM.isEmpty()){
				this.message = "No data(s) to filter.";
				color4FactMsg = "red";
				return;
				
			}
			System.out.println("copyColumnNameTM===>"+copyColumnNameTM);
			System.out.println("columnNameTM1===>"+columnNameTM1);
			if(selectedOperator.equals("Contains")){
				
				
				columnNameTM1.putAll(copyColumnNameTM);
				Set set = columnNameTM1.entrySet(); 
				columnNameTM1 = new TreeMap<>();
				// Get an iterator 
				Iterator i = set.iterator(); 
				// Filter elements 
				while(i.hasNext()) { 
				Map.Entry me = (Map.Entry)i.next(); 
				System.out.print(me.getKey() + ": "); 
				System.out.println(me.getValue()); 
				String contains = (String) me.getValue();
				if(matchCase4TableSelect){
//					filterValue = filterValue;
				}else{
					filterValue = filterValue.toUpperCase();
					contains = contains.toUpperCase();
				}
				if(contains.contains(filterValue)){
					columnNameTM1.put(contains, contains);
				}
			
				} 
				
			}else if(selectedOperator.equals("Starts With")){
				
				columnNameTM1.putAll(copyColumnNameTM);
				Set set = columnNameTM1.entrySet(); 
				columnNameTM1 = new TreeMap<>();
				// Get an iterator 
				Iterator i = set.iterator(); 
				// Filter elements 
				while(i.hasNext()) { 
				Map.Entry me = (Map.Entry)i.next(); 
				System.out.print(me.getKey() + ": "); 
				System.out.println(me.getValue()); 
			String startswith = (String) me.getValue();
			if(matchCase4TableSelect){
//				filterValue = filterValue;
			}else{
				filterValue = filterValue.toUpperCase();
				startswith = startswith.toUpperCase();
			}
				if(startswith.startsWith(filterValue)){
				
					columnNameTM1.put(startswith, startswith);
			}
				}
			
			}else if(selectedOperator.equals("Ends With")){
		
				columnNameTM1.putAll(copyColumnNameTM);
				Set set = columnNameTM1.entrySet(); 
				columnNameTM1 = new TreeMap<>();
				// Get an iterator 
				Iterator i = set.iterator(); 
				// Display elements 
				while(i.hasNext()) { 
				Map.Entry me = (Map.Entry)i.next(); 
				System.out.print(me.getKey() + ": "); 
				System.out.println(me.getValue()); 
				String endswith = (String) me.getValue();
				if(matchCase4TableSelect){
//					filterValue = filterValue;
				}else{
					filterValue = filterValue.toUpperCase();
					endswith = endswith.toUpperCase();
				}
				if(endswith.endsWith(filterValue)){
			
					columnNameTM1.put(endswith, endswith);
				}
		
			}
			
			
		}else if(selectedOperator.equals("All Values")){
			
			columnNameTM1 = new TreeMap<>();
			columnNameTM1 = copyColumnNameTM;
			} 
		
		}
			else if(filterFrom.equals("selectedMeasureFilter")){

	if(selectedOperator == null || selectedOperator.equals("") || selectedOperator.equals("null")){
		
		this.message = "Please select operator to filter selected measure column(s).";
		color4FactMsg = "red";
		return;
	}else if(copyTableNameTM == null || copyTableNameTM.isEmpty()){
		this.message = "No data(s) to filter.";
		color4FactMsg = "red";
		return;
		
	}

		if(selectedOperator.equals("Contains")){
			
			tableNameTM1.putAll(copyTableNameTM);
			Set set = tableNameTM1.entrySet(); 
			tableNameTM1 = new TreeMap<>();
			// Get an iterator 
			Iterator i = set.iterator(); 
			// Filter elements 
			while(i.hasNext()) { 
			Map.Entry me = (Map.Entry)i.next(); 
			System.out.print(me.getKey() + ": "); 
			System.out.println(me.getValue()); 
			String contains = (String) me.getValue();
			if(matchCase4TableSelect){
//				filterValue = filterValue;
			}else{
				filterValue = filterValue.toUpperCase();
				contains = contains.toUpperCase();
			}
			if(contains.contains(filterValue)){
				tableNameTM1.put(contains, contains);
			}
		
			} 
			
		}else if(selectedOperator.equals("Starts With")){
			
			tableNameTM1.putAll(copyTableNameTM);
			Set set = tableNameTM1.entrySet(); 
			tableNameTM1 = new TreeMap<>();
			// Get an iterator 
			Iterator i = set.iterator(); 
			// Filter elements 
			while(i.hasNext()) { 
			Map.Entry me = (Map.Entry)i.next(); 
			System.out.print(me.getKey() + ": "); 
			System.out.println(me.getValue()); 
			String startswith = (String) me.getValue();
			if(matchCase4TableSelect){
//				filterValue = filterValue;
			}else{
				filterValue = filterValue.toUpperCase();
				startswith = startswith.toUpperCase();
			}
			if(startswith.startsWith(filterValue)){
				
				tableNameTM1.put(startswith, startswith);
			}			
			}
		}else if(selectedOperator.equals("Ends With")){
			
			tableNameTM1.putAll(copyTableNameTM);
			Set set = tableNameTM1.entrySet(); 
			tableNameTM1 = new TreeMap<>();
			// Get an iterator 
			Iterator i = set.iterator(); 
			// Display elements 
			while(i.hasNext()) { 
			Map.Entry me = (Map.Entry)i.next(); 
			System.out.print(me.getKey() + ": "); 
			System.out.println(me.getValue()); 
			String endswith = (String) me.getValue();
			if(matchCase4TableSelect){
//				filterValue = filterValue;
			}else{
				filterValue = filterValue.toUpperCase();
				endswith = endswith.toUpperCase();
			}
			if(endswith.endsWith(filterValue)){
				
				tableNameTM1.put(endswith, endswith);
			}
			
			}
		
		}else if(selectedOperator.equals("All Values")){
		
			tableNameTM1 = new TreeMap<>();
			tableNameTM1 = copyTableNameTM;
		}
			
			
			} 
			else if(filterFrom.equals("joinColumnFilter")){
		System.out.println("selectedOperator===>"+selectedOperator);
		System.out.println("copyColumnNameTM4Join===>"+copyColumnNameTM4Join);
	if(selectedOperator == null || selectedOperator.equals("") || selectedOperator.equals("null")){
		
		this.message = "Please select operator to filter join column(s).";
		color4FactMsg = "red";
		return;
	}else if(copyColumnNameTM4Join == null || copyColumnNameTM4Join.isEmpty()){
		this.message = "No data(s) to filter.";
		color4FactMsg = "red";
		return;
		
	}
	
	if(selectedOperator.equals("Contains")){
		
		columns4IncreUpdateTM.putAll(copyColumnNameTM4Join);
		Set set = columns4IncreUpdateTM.entrySet(); 
		columns4IncreUpdateTM = new TreeMap<>();
		// Get an iterator 
		Iterator i = set.iterator(); 
		// Filter elements 
		while(i.hasNext()) { 
		Map.Entry me = (Map.Entry)i.next(); 
		System.out.print(me.getKey() + ": "); 
		System.out.println(me.getValue()); 
		String contains = (String) me.getValue();
		if(matchCase4TableSelect){
//			filterValue = filterValue;
		}else{
			filterValue = filterValue.toUpperCase();
			contains = contains.toUpperCase();
		}
		if(contains.contains(filterValue)){
			columns4IncreUpdateTM.put(contains, contains);
	}
	
		} 
		
		
	}else if(selectedOperator.equals("Starts With")){
		
		columns4IncreUpdateTM.putAll(copyColumnNameTM4Join);
		Set set = columns4IncreUpdateTM.entrySet(); 
		columns4IncreUpdateTM = new TreeMap<>();
		// Get an iterator 
		Iterator i = set.iterator(); 
		// Filter elements 
		while(i.hasNext()) { 
		Map.Entry me = (Map.Entry)i.next(); 
		System.out.print(me.getKey() + ": "); 
		System.out.println(me.getValue()); 
		String startswith = (String) me.getValue();
		
		if(matchCase4TableSelect){
//			filterValue = filterValue;
		}else{
			filterValue = filterValue.toUpperCase();
			startswith = startswith.toUpperCase();
		}
		if(startswith.startsWith(filterValue)){
			
			columns4IncreUpdateTM.put(startswith, startswith);
		}			
		}
		
	}else if(selectedOperator.equals("Ends With")){
		
		columns4IncreUpdateTM.putAll(copyColumnNameTM4Join);
		Set set = columns4IncreUpdateTM.entrySet(); 
		columns4IncreUpdateTM = new TreeMap<>();
		// Get an iterator 
		Iterator i = set.iterator(); 
		// Display elements 
		while(i.hasNext()) { 
		Map.Entry me = (Map.Entry)i.next(); 
		System.out.print(me.getKey() + ": "); 
		System.out.println(me.getValue()); 
		String endswith = (String) me.getValue();
		if(matchCase4TableSelect){
//			filterValue = filterValue;
		}else{
			filterValue = filterValue.toUpperCase();
			endswith = endswith.toUpperCase();
		}
		if(endswith.endsWith(filterValue)){
			
			columns4IncreUpdateTM.put(endswith, endswith);
		}
		
	}
		
	}else if(selectedOperator.equals("All Values")){
		
		columns4IncreUpdateTM = new TreeMap<>();
		columns4IncreUpdateTM = copyColumnNameTM4Join;
	}
	
}
			else if(filterFrom.equals("selectedJoinColumnsFilter")){
	
if(selectedJoinClumnOperator == null || selectedJoinClumnOperator.equals("") || selectedJoinClumnOperator.equals("null")){
	
		this.message = "Please select operator to filter selected join column(s).";
		color4FactMsg = "red";
			return;
}else if(copySelectedcolumnNameTM4Join == null || copySelectedcolumnNameTM4Join.isEmpty()){
	this.message = "No data(s) to filter.";
	color4FactMsg = "red";
	return;
	
}
	
	if(selectedJoinClumnOperator.equals("Contains")){
		

		selectedInccolumns4IncreUpdateTM.putAll(copySelectedcolumnNameTM4Join);
		Set set = selectedInccolumns4IncreUpdateTM.entrySet(); 
		selectedInccolumns4IncreUpdateTM = new TreeMap<>();
		// Get an iterator 
		Iterator i = set.iterator(); 
		// Filter elements 
		while(i.hasNext()) { 
		Map.Entry me = (Map.Entry)i.next(); 
		System.out.print(me.getKey() + ": "); 
		System.out.println(me.getValue()); 
		String contains = (String) me.getValue();
		if(incrmatchCase4TableSelect){
//			filterValue = filterValue;
		}else{
			selectedJoinClmnfilterValue = selectedJoinClmnfilterValue.toUpperCase();
			contains = contains.toUpperCase();
		}
		if(contains.contains(selectedJoinClmnfilterValue)){
			selectedInccolumns4IncreUpdateTM.put(contains, contains);
		}
	
		} 
		
	}else if(selectedJoinClumnOperator.equals("Starts With")){
		
		selectedInccolumns4IncreUpdateTM.putAll(copySelectedcolumnNameTM4Join);
		Set set = selectedInccolumns4IncreUpdateTM.entrySet(); 
		selectedInccolumns4IncreUpdateTM = new TreeMap<>();
		// Get an iterator 
		Iterator i = set.iterator(); 
		// Filter elements 
		while(i.hasNext()) { 
		Map.Entry me = (Map.Entry)i.next(); 
		System.out.print(me.getKey() + ": "); 
		System.out.println(me.getValue()); 
		String startswith = (String) me.getValue();
		if(incrmatchCase4TableSelect){
//			filterValue = filterValue;
		}else{
			selectedJoinClmnfilterValue = selectedJoinClmnfilterValue.toUpperCase();
			startswith = startswith.toUpperCase();
		}
		if(startswith.startsWith(selectedJoinClmnfilterValue)){
			
			selectedInccolumns4IncreUpdateTM.put(startswith, startswith);
		}			
		}
		
	}else if(selectedJoinClumnOperator.equals("Ends With")){
		
		selectedInccolumns4IncreUpdateTM.putAll(copySelectedcolumnNameTM4Join);
		Set set = selectedInccolumns4IncreUpdateTM.entrySet(); 
		selectedInccolumns4IncreUpdateTM = new TreeMap<>();
		// Get an iterator 
		Iterator i = set.iterator(); 
		// Display elements 
		while(i.hasNext()) { 
		Map.Entry me = (Map.Entry)i.next(); 
		System.out.print(me.getKey() + ": "); 
		System.out.println(me.getValue()); 
		String endswith = (String) me.getValue();
		if(incrmatchCase4TableSelect){
//			filterValue = filterValue;
		}else{
			selectedJoinClmnfilterValue = selectedJoinClmnfilterValue.toUpperCase();
			endswith = endswith.toUpperCase();
		}
		if(endswith.endsWith(selectedJoinClmnfilterValue)){
			
			selectedInccolumns4IncreUpdateTM.put(endswith, endswith);
		}
		
	}
		
	}else if(selectedJoinClumnOperator.equals("All Values")){
		
		selectedInccolumns4IncreUpdateTM = new TreeMap<>();
		selectedInccolumns4IncreUpdateTM = copySelectedcolumnNameTM4Join;
	}
	
	
}
			else if(filterFrom.equals("periodValueFilter")){
	
if(periodvalueOperator == null || periodvalueOperator.equals("") || periodvalueOperator.equals("null")){
		
		this.message = "Please select operator to filter period value(s).";
		color4FactMsg = "red";
			return;
}else if(copyPeriodValuesAL == null || copyPeriodValuesAL.isEmpty()){
	this.message = "No data(s) to filter.";
	color4FactMsg = "red";
	return;
	
}
String filterValue = filterPeriodValues; //code change Jayaramu 19APR14

	if(periodvalueOperator.equals("Contains")){
		
		
//		periodValuesAL.add(copyPeriodValuesAL);
		
		periodValuesAL = new ArrayList<>();
		for(int i=0;i<copyPeriodValuesAL.size();i++){
			
			String contains = (String) copyPeriodValuesAL.get(i);
			String copyFilterValue = contains;
			if(matchCase4genData){
				
			}else{
				filterValue = filterValue.toUpperCase();
				contains = contains.toUpperCase();
			}
			if(contains.contains(filterValue)){
			periodValuesAL.add(copyFilterValue);
			}
		}
		
		
	}else if(periodvalueOperator.equals("Starts With")){
		
		periodValuesAL = new ArrayList<>();
		for(int i=0;i<copyPeriodValuesAL.size();i++){
			
			String startswith = (String) copyPeriodValuesAL.get(i);
			String copyFilterValue = startswith;
			if(matchCase4genData){
				
			}else{
				filterValue = filterValue.toUpperCase();
				startswith = startswith.toUpperCase();
			}
			
			if(startswith.startsWith(filterValue)){
				
		System.out.println("startswith======>>>>>>>>>"+startswith);
				
			periodValuesAL.add(copyFilterValue);
			}
		}
		
		
		
	}else if(periodvalueOperator.equals("Ends With")){
		
		
		periodValuesAL = new ArrayList<>();
		for(int i=0;i<copyPeriodValuesAL.size();i++){
			
			String endswith = (String) copyPeriodValuesAL.get(i);
			String copyFilterValue = endswith;
			if(matchCase4genData){
				
			}else{
				filterValue = filterValue.toUpperCase();
				endswith = endswith.toUpperCase();
			}
			if(endswith.endsWith(filterValue)){
			periodValuesAL.add(copyFilterValue);
			}
		}
		
	}
		
	else if(periodvalueOperator.equals("All Values")){
		
		periodValuesAL = new ArrayList<>();
		periodValuesAL = copyPeriodValuesAL;
	}
	
}
			else if(filterFrom.equals("selectedPeriodValues")){
	
if(selectedPeriodvalueOperator == null || selectedPeriodvalueOperator.equals("") || selectedPeriodvalueOperator.equals("null")){
		
		this.message = "Please select operator to filter selected period value(s).";
		color4FactMsg = "red";
			return;
}else if(copySelectedPeriodValuesAL == null || copySelectedPeriodValuesAL.isEmpty()){
	this.message = "No data(s) to filter.";
	color4FactMsg = "red";
	return;
	
}
	String filterValue = filterSelectedPeriodValues;
	if(selectedPeriodvalueOperator.equals("Contains")){
		
		selectedPeriodValuesAL = new ArrayList<>();
for(int i=0;i<copySelectedPeriodValuesAL.size();i++){
			String contains = (String) copySelectedPeriodValuesAL.get(i);
			String copyFilterValue = contains;
			if(matchCase4GenDataSelPer){
				
			}else{
				filterValue = filterValue.toUpperCase();
				contains = contains.toUpperCase();
			}
			if(contains.contains(filterValue)){
				selectedPeriodValuesAL.add(copyFilterValue);
			}
		}
		
	}else if(selectedPeriodvalueOperator.equals("Starts With")){
		
		selectedPeriodValuesAL = new ArrayList<>();
		for(int i=0;i<copySelectedPeriodValuesAL.size();i++){
			
			String startswith = (String) copySelectedPeriodValuesAL.get(i);
			String copyFilterValue = startswith;
			if(matchCase4GenDataSelPer){
				
			}else{
				filterValue = filterValue.toUpperCase();
				startswith = startswith.toUpperCase();
			}
			if(startswith.startsWith(filterValue)){
				selectedPeriodValuesAL.add(copyFilterValue);
			}
		}
		
	}else if(selectedPeriodvalueOperator.equals("Ends With")){
		
		selectedPeriodValuesAL = new ArrayList<>();
		for(int i=0;i<copySelectedPeriodValuesAL.size();i++){
			
			String endswith = (String) copySelectedPeriodValuesAL.get(i);
			String copyFilterValue = endswith;
			if(matchCase4GenDataSelPer){
				
			}else{
				filterValue = filterValue.toUpperCase();
				endswith = endswith.toUpperCase();
			}
			if(endswith.endsWith(filterValue)){
				selectedPeriodValuesAL.add(copyFilterValue);
			}
		}
		
	}else if(selectedPeriodvalueOperator.equals("All Values")){
		
		selectedPeriodValuesAL = new ArrayList<>();
		selectedPeriodValuesAL = copySelectedPeriodValuesAL;
	}
	

}  // Start code change Menaka 15MAR2014
		else if(filterFrom.equals("filterTables4Seg")){
			
			if(selectedOperator == null || selectedOperator.equals("") || selectedOperator.equals("null")){
				
				this.message = "Please select operator to filter fact table(s).";
				color4FactMsg = "red";
				return;
			}else if(copyTableNameTM == null || copyTableNameTM.isEmpty()){
				this.message = "No data(s) to filter.";
				color4FactMsg = "red";
				return;
			}
			String filterValue = this.filterValue;
	if(selectedOperator.equals("Contains")){
	
		
		segtableNameTM.putAll(copyTableNameTM);
		Set set = segtableNameTM.entrySet();
		segtableNameTM = new TreeMap<>();
		// Get an iterator 
		Iterator i = set.iterator(); 
		// Filter elements 
		System.out.println("filterValue====>>>>"+filterValue); 
		while(i.hasNext()) { 
		Map.Entry me = (Map.Entry)i.next(); 
		System.out.print(me.getKey() + ": "); 
		System.out.println(me.getValue()); 
		String contains = (String) me.getValue();
		
		if(matchCase4SegAdmin){
			
		}else{
			
			filterValue = filterValue.toUpperCase();
		}
		if(contains.contains(filterValue)){
			segtableNameTM.put(contains, contains);
		}
		
		} 
	}
	
	if(selectedOperator.equals("Starts With")){
		
	
		segtableNameTM.putAll(copyTableNameTM);
		Set set = segtableNameTM.entrySet(); 
		segtableNameTM = new TreeMap<>();
		// Get an iterator 
		Iterator i = set.iterator(); 
		// Filter elements 
		System.out.println("filterValue====>>>>"+filterValue+"selectedOperator-====>>>"+selectedOperator); 
		
		while(i.hasNext()) { 
		Map.Entry me = (Map.Entry)i.next(); 
		System.out.print(me.getKey() + ": "); 
		System.out.println(me.getValue()); 
		String startswith = (String) me.getValue();
		
		System.out.println("startswith===>>>"+startswith); 
		if(matchCase4SegAdmin){
			
		}else{
			
			filterValue = filterValue.toUpperCase();
		}
		if(startswith.startsWith(filterValue)){
			
			System.out.println("startswith filterValue===>>>"+startswith); 
			segtableNameTM.put(startswith, startswith);
		} 
	}

	}
	
	if(selectedOperator.equals("Ends With")){
	

		segtableNameTM.putAll(copyTableNameTM);
		Set set = segtableNameTM.entrySet();
		segtableNameTM = new TreeMap<>();
		// Get an iterator 
		Iterator i = set.iterator(); 
		// Display elements 
		System.out.println("filterValue====>>>>"+filterValue); 
		while(i.hasNext()) { 
		Map.Entry me = (Map.Entry)i.next(); 
		System.out.print(me.getKey() + ": "); 
		System.out.println(me.getValue()); 
		String endswith = (String) me.getValue();
		if(matchCase4SegAdmin){
			
		}else{
			
			filterValue = filterValue.toUpperCase();
		}
		if(endswith.endsWith(filterValue)){
			
			segtableNameTM.put(endswith, endswith);
		}
		
		}
		} 
	
	if(selectedOperator.equals("All Values")){
		
		segtableNameTM = new TreeMap<>();
		segtableNameTM = copyTableNameTM;
		
	}
	}
		else if(filterFrom.equals("segColumns4SelectedTable")){
			
//			System.out.println("filterFrom---->>>>"+filterFrom);
			
			
			
	
if(selectedTableOperator == null || selectedTableOperator.equals("") || selectedTableOperator.equals("null")){
				
				this.message = "Please select operator to filter selected table(s).";
				color4FactMsg = "red";
				return;
			}else if(copyColumnNameTM == null || copyColumnNameTM.isEmpty()){
				this.message = "No data(s) to filter.";
				color4FactMsg = "red";
				return;
				
			}

//System.out.println("selectedTableOperator---->>>>"+selectedTableOperator);

			String filterValue = selectedTablefilterValue;
			if(selectedTableOperator.equals("Contains")){
				
			
				segcolumnNameTM.putAll(copyColumnNameTM);
				Set set = segcolumnNameTM.entrySet();
				segcolumnNameTM = new TreeMap<>();
				// Get an iterator 
				Iterator i = set.iterator(); 
				// Filter elements 
				System.out.println("selectedTablefilterValue====>>>>"+selectedTablefilterValue); 
				while(i.hasNext()) { 
				Map.Entry me = (Map.Entry)i.next(); 
				System.out.print(me.getKey() + ": "); 
				System.out.println(me.getValue()); 
				String contains = (String) me.getValue();
				if(matchCase4SegAdminColumns){
					
				}else{
					filterValue = filterValue.toUpperCase();
				}
				if(contains.contains(filterValue)){
					segcolumnNameTM.put(contains, contains);
				}
				
				
				
				} 
//				System.out.println("segcolumnNameTM---->>>>"+segcolumnNameTM);
			}
			
			if(selectedTableOperator.equals("Starts With")){
				
			
				segcolumnNameTM.putAll(copyColumnNameTM);
				Set set = segcolumnNameTM.entrySet(); 
				segcolumnNameTM = new TreeMap<>();
				// Get an iterator 
				Iterator i = set.iterator(); 
				// Filter elements 
				System.out.println("selectedTablefilterValue====>>>>"+selectedTablefilterValue+"selectedOperator-====>>>"+selectedTableOperator); 
				
				while(i.hasNext()) { 
				Map.Entry me = (Map.Entry)i.next(); 
				System.out.print(me.getKey() + ": "); 
				System.out.println(me.getValue()); 
				String startswith = (String) me.getValue();
				
				System.out.println("startswith===>>>"+startswith); 
				if(matchCase4SegAdminColumns){
					
				}else{
					filterValue = filterValue.toUpperCase();
				}
				if(startswith.startsWith(filterValue)){
					
					System.out.println("startswith filterValue===>>>"+startswith); 
					segcolumnNameTM.put(startswith, startswith);
				} 
			}
				
//				System.out.println("segcolumnNameTM---->>>>"+segcolumnNameTM);

			}
			
			if(selectedTableOperator.equals("Ends With")){
			

				segcolumnNameTM.putAll(copyColumnNameTM);
				Set set = segcolumnNameTM.entrySet();
				segcolumnNameTM = new TreeMap<>();
				// Get an iterator 
				Iterator i = set.iterator(); 
				// Display elements 
				System.out.println("selectedTablefilterValue====>>>>"+selectedTablefilterValue); 
				while(i.hasNext()) { 
				Map.Entry me = (Map.Entry)i.next(); 
				System.out.print(me.getKey() + ": "); 
				System.out.println(me.getValue()); 
				String endswith = (String) me.getValue();
				if(matchCase4SegAdminColumns){
					
				}else{
					filterValue = filterValue.toUpperCase();
				}
				if(endswith.endsWith(filterValue)){
					
					segcolumnNameTM.put(endswith, endswith);
				}
				
				}
				} 
			
			if(selectedTableOperator.equals("All Values")){
				
				segcolumnNameTM = new TreeMap<>();
				segcolumnNameTM = copyColumnNameTM;
				
			}
			
		}
		
	
		else if(filterFrom.equals("filter4Segment")){
			
			if(selectedPeriodvalueOperator == null || selectedPeriodvalueOperator.equals("") || selectedPeriodvalueOperator.equals("null")){
					
					this.message = "Please select operator to filter selected period value(s).";
					color4FactMsg = "red";
						return;
			}else if(	copySegmentValues4SegPopupAL== null || copySegmentValues4SegPopupAL.isEmpty()){
				this.message = "No data(s) to filter.";
				color4FactMsg = "red";
				return;
				
			}
				String filterValue = filterSelectedPeriodValues;
				
				if(selectedPeriodvalueOperator.equals("Contains")){
					
					segmentValues4SegPopupAL = new ArrayList<>();
			for(int i=0;i<copySegmentValues4SegPopupAL.size();i++){
						String contains = (String) copySegmentValues4SegPopupAL.get(i);
						String copyContains = contains;
						if(matchCase4FilterSeg){
							
						}else{
							contains = contains.toUpperCase();
							filterValue = filterValue.toUpperCase();			
						}
						if(contains.contains(filterValue)){
							segmentValues4SegPopupAL.add(copyContains);
						}
					}
					
				}else if(selectedPeriodvalueOperator.equals("Starts With")){
					
					segmentValues4SegPopupAL = new ArrayList<>();
					for(int i=0;i<copySegmentValues4SegPopupAL.size();i++){
						
						String startswith = (String) copySegmentValues4SegPopupAL.get(i);
						String copyfilterValue = startswith;
						if(matchCase4FilterSeg){
							
						}else{
							startswith = startswith.toUpperCase();
							filterValue = filterValue.toUpperCase();			
						}
						if(startswith.startsWith(filterValue)){
							segmentValues4SegPopupAL.add(copyfilterValue);
						}
					}
					
				}else if(selectedPeriodvalueOperator.equals("Ends With")){
					
					segmentValues4SegPopupAL = new ArrayList<>();
					for(int i=0;i<copySegmentValues4SegPopupAL.size();i++){
						
						String endswith = (String) copySegmentValues4SegPopupAL.get(i);
						String copyfilterValue = endswith;
						if(matchCase4FilterSeg){
							
						}else{
							endswith = endswith.toUpperCase();
							filterValue = filterValue.toUpperCase();			
						}
						if(endswith.endsWith(filterValue)){
							segmentValues4SegPopupAL.add(copyfilterValue);
						}
					}
					
				}else if(selectedPeriodvalueOperator.equals("All Values")){
					
					segmentValues4SegPopupAL = new ArrayList<>();
					segmentValues4SegPopupAL = copySegmentValues4SegPopupAL;
				}
				

			}
	 if(filterFrom.equals("fromUsersList")){  //  code change Menaka 28MAR2014
		
//		System.out.println("MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM==========>>>>"+filterFrom);
		 
		FacesContext ctx = FacesContext.getCurrentInstance();
		ExternalContext extContext = ctx.getExternalContext();
		Map sessionMap = extContext.getSessionMap();
		HierarchyBean hryb = (HierarchyBean) sessionMap.get("hierarchyBean");
		
		
			if(selectedOperator == null || selectedOperator.equals("") || selectedOperator.equals("null")){
					
					this.message = "Please select operator to filter selected period value(s).";
					color4FactMsg = "red";
						return;
			}else if(	hryb.copyAllUsersAL== null || hryb.copyAllUsersAL.isEmpty()){
				this.message = "No data(s) to filter.";
				color4FactMsg = "red";
				return;
				
			}
			
				if(selectedOperator.equals("Contains")){
					
					hryb.allUsersAL = new ArrayList<>();
			for(int i=0;i<hryb.copyAllUsersAL.size();i++){
						String contains = hryb.copyAllUsersAL.get(i).userLoginID;
//						
						if(contains.contains(filterValue)){
							hryb.allUsersAL.add(hryb.copyAllUsersAL.get(i));
						}
					}
					
				}else if(selectedOperator.equals("Starts With")){
					
					hryb.allUsersAL = new ArrayList<>();
					for(int i=0;i<hryb.copyAllUsersAL.size();i++){
						
						String startswith = hryb.copyAllUsersAL.get(i).userLoginID;
												
						if(startswith.startsWith(filterValue)){
							hryb.allUsersAL.add(hryb.copyAllUsersAL.get(i));
						}
					}
					
				}else if(selectedOperator.equals("Ends With")){
					
					hryb.allUsersAL = new ArrayList<>();
					for(int i=0;i<hryb.copyAllUsersAL.size();i++){
						
						String endswith = hryb.copyAllUsersAL.get(i).userLoginID;
						if(endswith.endsWith(filterValue)){
							hryb.allUsersAL.add(hryb.copyAllUsersAL.get(i));
						}
					}
					
				}else if(selectedOperator.equals("All Values")){
					
					hryb.allUsersAL = new ArrayList<>();
					hryb.allUsersAL = hryb.copyAllUsersAL;
				}
				

			}
	//start code change Jayaramu 10MAY14 for filter existing tables in select table popup
	 else if(filterFrom.equals("FromExistingTable")){ 
			
//			System.out.println("filterFrom---->>>>"+filterFrom);
			
			
			
	
if(selectedOperator == null || selectedOperator.equals("") || selectedOperator.equals("null")){
				
				this.existingTableMessage = "Please select operator to filter selected table(s).";
				return;
			}else if(copytableNamesFromFactPopUp == null || copytableNamesFromFactPopUp.isEmpty()){
				this.existingTableMessage = "No data(s) to filter.";
				return;
				
			}

//System.out.println("selectedTableOperator---->>>>"+selectedTableOperator);

			String filterValue = this.filterValue;
			if(selectedOperator.equals("Contains")){
				
			
				tableNamesFromFactPopUp.putAll(copytableNamesFromFactPopUp);
				Set set = tableNamesFromFactPopUp.entrySet();
				tableNamesFromFactPopUp = new TreeMap<>();
				// Get an iterator 
				Iterator i = set.iterator(); 
				// Filter elements 
				while(i.hasNext()) { 
				Map.Entry me = (Map.Entry)i.next(); 
				System.out.print(me.getKey() + ": "); 
				System.out.println(me.getValue()); 
				String contains = (String) me.getValue();
				if(matchCase4ExistingTableSelect){
					
				}else{
					filterValue = filterValue.toUpperCase();
				}
				if(contains.contains(filterValue)){
					tableNamesFromFactPopUp.put(contains, contains);
				}
				
				
				
				} 
//				System.out.println("segcolumnNameTM---->>>>"+segcolumnNameTM);
			}
			
			if(selectedOperator.equals("Starts With")){
				
			
				tableNamesFromFactPopUp.putAll(copytableNamesFromFactPopUp);
				Set set = tableNamesFromFactPopUp.entrySet(); 
				tableNamesFromFactPopUp = new TreeMap<>();
				// Get an iterator 
				Iterator i = set.iterator(); 
				// Filter elements 
				
				while(i.hasNext()) { 
				Map.Entry me = (Map.Entry)i.next(); 
				System.out.print(me.getKey() + ": "); 
				System.out.println(me.getValue()); 
				String startswith = (String) me.getValue();
				
				System.out.println("startswith===>>>"+startswith); 
				if(matchCase4ExistingTableSelect){
					
				}else{
					filterValue = filterValue.toUpperCase();
				}
				if(startswith.startsWith(filterValue)){
					
					System.out.println("startswith filterValue===>>>"+startswith); 
					tableNamesFromFactPopUp.put(startswith, startswith);
				} 
			}
				
//				System.out.println("segcolumnNameTM---->>>>"+segcolumnNameTM);

			}
			
			if(selectedOperator.equals("Ends With")){
			

				tableNamesFromFactPopUp.putAll(copytableNamesFromFactPopUp);
				Set set = tableNamesFromFactPopUp.entrySet();
				tableNamesFromFactPopUp = new TreeMap<>();
				// Get an iterator 
				Iterator i = set.iterator(); 
				// Display elements 
				System.out.println("selectedTablefilterValue====>>>>"+selectedTablefilterValue); 
				while(i.hasNext()) { 
				Map.Entry me = (Map.Entry)i.next(); 
				System.out.print(me.getKey() + ": "); 
				System.out.println(me.getValue()); 
				String endswith = (String) me.getValue();
				if(matchCase4ExistingTableSelect){
					
				}else{
					filterValue = filterValue.toUpperCase();
				}
				if(endswith.endsWith(filterValue)){
					
					tableNamesFromFactPopUp.put(endswith, endswith);
				}
				
				}
				} 
			
			if(selectedOperator.equals("All Values")){
				
				tableNamesFromFactPopUp = new TreeMap<>();
				tableNamesFromFactPopUp = copytableNamesFromFactPopUp;
				
			}
			
		}
		
		
		////////////////////
		
		
		
		
		
		}catch(Exception e){
			
			e.printStackTrace();
		}
	}
	
	//End code change Jayaramu 20FEB14
	ArrayList selectedColumns4IncreUpdateAL = new ArrayList<>();
	
	public ArrayList getSelectedColumns4IncreUpdateAL() {
		return selectedColumns4IncreUpdateAL;
	}
	public void setSelectedColumns4IncreUpdateAL(
			ArrayList selectedColumns4IncreUpdateAL) {
		this.selectedColumns4IncreUpdateAL = selectedColumns4IncreUpdateAL;
	}

	TreeMap columns4IncreUpdateTM = new TreeMap<>();
	TreeMap columns4IncreUpdateTM1 = new TreeMap<>();
	public TreeMap getColumns4IncreUpdateTM1() {
		return columns4IncreUpdateTM1;
	}
	public void setColumns4IncreUpdateTM1(TreeMap columns4IncreUpdateTM1) {
		this.columns4IncreUpdateTM1 = columns4IncreUpdateTM1;
	}
	public TreeMap getColumns4IncreUpdateTM() {
		return columns4IncreUpdateTM;
	}
	public void setColumns4IncreUpdateTM(TreeMap columns4IncreUpdateTM) {
		this.columns4IncreUpdateTM = columns4IncreUpdateTM;
	}
	
	ArrayList seleIncColumns4IncreUpdateAL = new ArrayList<>();
	
	public ArrayList getSeleIncColumns4IncreUpdateAL() {
		return seleIncColumns4IncreUpdateAL;
	}
	public void setSeleIncColumns4IncreUpdateAL(
			ArrayList seleIncColumns4IncreUpdateAL) {
		this.seleIncColumns4IncreUpdateAL = seleIncColumns4IncreUpdateAL;
	}
	TreeMap selectedInccolumns4IncreUpdateTM = new TreeMap<>();
	public TreeMap getSelectedInccolumns4IncreUpdateTM() {
		return selectedInccolumns4IncreUpdateTM;
	}
	public void setSelectedInccolumns4IncreUpdateTM(
			TreeMap selectedInccolumns4IncreUpdateTM) {
		this.selectedInccolumns4IncreUpdateTM = selectedInccolumns4IncreUpdateTM;
	}
	//start code change Jayaramu 20FEB14
	public void moveForwardBackwardPV(String processFrom,String process){
		System.out.println("Entering: "
				+ new Exception().getStackTrace()[0].getClassName() + "."
				+ new Exception().getStackTrace()[0].getMethodName());
		try{
			
			
			
			this.message = "";
			HeirarchyDataBean hedb; 
		if(process.equals("forward")){
				
			if(processFrom.equals("IncrementalColumn")){
				
				if(columns4IncreUpdateTM == null || columns4IncreUpdateTM.isEmpty()){
					this.message = "No data(s) to move";
					color4FactMsg = "red";
					return;
				}
				else if(selectedColumns4IncreUpdateAL == null || selectedColumns4IncreUpdateAL.size() <= 0){
					this.message = "Please select fact table(s) to move forward";
					color4FactMsg = "red";
					return;
				}
				
				
for(int i=0;i<selectedColumns4IncreUpdateAL.size();i++){
	
	selectedInccolumns4IncreUpdateTM.put(selectedColumns4IncreUpdateAL.get(i), selectedColumns4IncreUpdateAL.get(i));
	copySelectedcolumnNameTM4Join.put(i, selectedColumns4IncreUpdateAL.get(i));
	columns4IncreUpdateTM.remove(selectedColumns4IncreUpdateAL.get(i));
//					copySelectedTableNameTM.put(selectedColumns4IncreUpdateAL.get(i), selectedColumns4IncreUpdateAL.get(i));
//					tableNameTM.remove(selectedTableName[i]);
//					copyTableNameTM.remove(selectedTableName[i]);
					
				}


				
			}
			
			else if(processFrom.equals("measureColumn")){
				
				if(columnNameTM == null || columnNameTM.isEmpty()){
					this.message = "No data(s) to move";
					return;
				}
//				else if(columnName == null || columnName.length <= 0){
//					this.message = "Please select Measure column(s) to Move forward";
//					return;
//				}
//				else if(tableName.length <=0){
//					
//					this.message = "Please select Source Fact Table";
//					return;
//				}
				else if(selectedTargetTableName == null || selectedTargetTableName.equals("")){
					
					this.message = "Please select target table name";
					return;
				}
				
				for(int i=0;i<columnName.length;i++){
					
					if(columnName[i].equalsIgnoreCase("No Result(s) Found")){
						
						columnNameTM = new TreeMap<>();
						columnNameTM.put("No column(s) can't Move", "No column(s) can't Move");
						
					}else if(columnName[i].equalsIgnoreCase(columnErrorMessage)){
						
						
						columnNameTM = new TreeMap<>();
						columnNameTM.put("Error occur can't Move", "Error occur can't Move");
					}
					else if(columnName[i].equalsIgnoreCase("Error occur can't Move") || columnName[i].equalsIgnoreCase("No column(s) can't Move")){
						
					}
					else{
					selectedMeasureColumnTM.put(columnName[i], columnName[i]);
					copySelectedMeasureColumnTM.put(columnName[i], columnName[i]);
					columnNameTM.remove(columnName[i]);
					copyColumnNameTM.remove(columnName[i]);
					System.out.println("targetColumNames====>"+targetColumNames);
					hedb = new HeirarchyDataBean(columnName[i],targetColumNames,false,"------Select Value------",srcTable,selectedTargetTableName,srcColumTM,propertyTM,property,selectedSrcTable,
							selectedSrcTableTM,selectedTargetColumn,TargetColumn,"","","","");
					
					selectedMeasureColumnsAL.add(hedb);
				}
					}
				
				
			}else if(processFrom.equals("joinColumn")){
				
				if(columnNameTM4Join == null || columnNameTM4Join.isEmpty()){
					this.message = "No data(s) to move";
					return;
				}
//				else if(tableName.length <=0){
//					
//					this.message = "Please select Source Fact Table";
//					return;
//				}
				else if(selectjoincolumn == null || selectjoincolumn.size() <= 0){
					this.message = "Please select join column(s) to move forward";
					return;
				}
				
for(int j=0;j<selectjoincolumn.size();j++){
					
	if(String.valueOf(selectjoincolumn.get(j)).equalsIgnoreCase("No Result(s) Found")){
		
		columnNameTM4Join = new TreeMap<>();
		columnNameTM4Join.put("No column(s) can't Move", "No column(s) can't Move");
		
	}else if(String.valueOf(selectjoincolumn.get(j)).equalsIgnoreCase(columnErrorMessage)){
		
		
		columnNameTM4Join = new TreeMap<>();
		columnNameTM4Join.put("Error occur can't Move", "Error occur can't Move");
	}else if(String.valueOf(selectjoincolumn.get(j)).equalsIgnoreCase("Error occur can't Move") || String.valueOf(selectjoincolumn.get(j)).equalsIgnoreCase("No column(s) can't Move")){
				
	}
	else{
	
	selectedcolumnNameTM4Join.put(selectjoincolumn.get(j), selectjoincolumn.get(j));
	copySelectedcolumnNameTM4Join.put(selectjoincolumn.get(j), selectjoincolumn.get(j));	
					columnNameTM4Join.remove(selectjoincolumn.get(j));
					copyColumnNameTM4Join.remove(selectjoincolumn.get(j));
					
					hedb = new HeirarchyDataBean(String.valueOf(selectjoincolumn.get(j)),joinTargetColumNamesAL,"------Select Value------",srcTable,joinTargetTableName,srcColumTM);
					selectedJoinColumnsAL.add(hedb);
					
			}
				
				
			}}else if(processFrom.equals("periodvalues")){
				
				if(periodValuesAL == null || periodValuesAL.isEmpty()){
					this.message = "Select a Period to add to Selected Period Value(s).";
					color4FactMsg = "red";
					return;
				}
				else if(selectedPeriodValues == null || selectedPeriodValues.size() <= 0){
					this.message = "Select a Period to add to Selected Period Value(s).";
					color4FactMsg = "red";
					return;
				}
				
				for(int k=0;k<selectedPeriodValues.size();k++){
					periodValuesAL.remove(selectedPeriodValues.get(k));
					copyPeriodValuesAL.remove(selectedPeriodValues.get(k));
//					if(!selectedPeriodValues.get(k).equals(" ")){
					selectedPeriodValuesAL.add(selectedPeriodValues.get(k));
					copySelectedPeriodValuesAL.add(selectedPeriodValues.get(k));
//					}
				}
				
			}
		
			else if(processFrom.equals("srcWarHusJoin")){
				String clumnList = srcWarJointextBox;
				for(int i=0;i<chooseColumn4Join.size();i++){
					clumnList = String.valueOf(chooseColumn4Join.get(i));
				}
				
				srcWarJointextBox = clumnList;
				System.out.println("srcWarJointextBox===>>>"+srcWarJointextBox);
			}
			
			
		}else if(process.equals("backward")){
			
				
			if(processFrom.equals("tableName")){
//				if(selectedTableNameTM == null || selectedTableNameTM.isEmpty()){
//					this.message = "No data(s) to move";
//					return;
//				}
//				else if(tableName == null || tableName.length <= 0){
//					this.message = "Please select selected Fact table(s) to Move backward";
//					return;
//				}
				msg4SrcTgtFact = "";
				if(srcandTarFactTablesAl.size()<=0){
					this.message = "No data(s) to move";
					color4FactMsg = "red";
					return;
				}

				FacesContext ctx = FacesContext.getCurrentInstance();
				ExternalContext extContext = ctx.getExternalContext();
				Map sessionMap = extContext.getSessionMap();
				HierarchyBean hryb = (HierarchyBean) sessionMap.get("hierarchyBean");
				PropUtil prop=new PropUtil();
				String dir=prop.getProperty("HIERARCHY_XML_DIR");
				Document doc = Globals.openXMLFile(dir,hierarchyXMLFileName);
				Node rootnode = Globals.getNodeByAttrVal(doc, "Hierarchy_Level", "Hierarchy_ID",hryb.getHierarchy_ID());
				Node factNodeTables = Globals.getNodeByAttrVal(doc, "Fact_Tables", "ID", hryb.getHierarchy_ID());
		

				

				
//for(int m=0;m<tableName.length;m++){
					
//				tableNameTM.put(tableName[0],tableName[0]);
//					copyTableNameTM.put(tableName[0],tableName[0]);
//					selectedTableNameTM.remove(tableName[0]);
//					copySelectedTableNameTM.remove(tableName[0]);
				HierarchydataBean hydb;
				HierarchydataBean hydb1;
				
				srcColumTM = new TreeMap<>();
				periodcolumn = "null";
				
			for(int ii=0;ii<srcandTarFactTablesAl.size();ii++){
				hydb=(HierarchydataBean) srcandTarFactTablesSelectedAl.get(0);
				hydb1=(HierarchydataBean) srcandTarFactTablesAl.get(ii);
				if(hydb.getSrcFactTable().equals(hydb1.getSrcFactTable()) && hydb.getTarFactTables().equals(hydb1.getTarFactTables())){
					
					srcandTarFactTablesAl.remove(hydb1);
					
					if(factNodeTables!=null){
						
					NodeList rootNodeChild = rootnode.getChildNodes();
					
					Node factNodes = null;
				for(int i=0;i<rootNodeChild.getLength();i++){
					
					
					factNodes = rootNodeChild.item(i);
					if(factNodes.getNodeName().equals("Fact_Tables")){
						
						NodeList tableChild = factNodes.getChildNodes();
						 
						for(int j=0;j<tableChild.getLength();j++){
							
							if(tableChild.item(j).getNodeName().equals("Table")){
								
								String tableName1 = tableChild.item(j).getAttributes().getNamedItem("Source_Fact_TableName").getTextContent().toString();
								String tartableName1 = tableChild.item(j).getAttributes().getNamedItem("Target_Fact_TableName").getTextContent().toString();
								if(tableName1.equals(hydb.getSrcFactTable()) && tartableName1.equals(hydb.getTarFactTables())){
									tableChild.item(j).getParentNode().removeChild(tableChild.item(j));
									
								}
							}
							
						}
					}
					
					
				}

					}
				}
			
				}
				
			Globals.writeXMLFile(doc, dir, hierarchyXMLFileName);
			msg4SrcTgtFact = "Deleted successfully."; //code change Jayaramu 25APR14
			color4SrcFactMsg = "blue";
			Set set = selectedTableNameTM.entrySet(); 
			
			// Get an iterator 
			Iterator i = set.iterator(); 
			// Filter elements 
			int no = 0;
			if(no == 0){
			while(i.hasNext()) { 
				no++;
			Map.Entry me = (Map.Entry)i.next(); 
			System.out.print(me.getKey() + ": "); 
			System.out.println(me.getValue()); 
//			tableName[0] = (String) me.getValue();	//??????
			}
			}
			
//			 selectedMeasureColumnsAL = new ArrayList<>();
//			  selectedJoinColumnsAL = new ArrayList<>();
//			  periodValuesAL = new ArrayList<>();
//			 selectedPeriodValuesAL = new ArrayList<>();
			  selectedTargetTableName = "null";
			  targetTableNameTM = new TreeMap<>();
			  columnNameTM = new TreeMap<>();
			  selectedMeasureColumnsAL = new ArrayList<>();
			  selectedJoinColumnsAL = new ArrayList<>();
			  selectedPeriodValuesAL = new ArrayList<>();
			  columnNameTM4Period = new TreeMap<>();
			  selTableName = "";
			  srcAndWHJoinsAL = new ArrayList<>();
			  incrementalLoadAL = new ArrayList<>();
			  incrementalLoadUpdateAL = new ArrayList<>();
			}
			
			if(processFrom.equals("IncrementalColumn")){
//				if(selectedTableNameTM == null || selectedTableNameTM.isEmpty()){
//					this.message = "No data(s) to move";
//					return;
//				}
//				else if(tableName == null || tableName.length <= 0){
//					this.message = "Please select selected Fact table(s) to Move backward";
//					return;
//				}
				System.out.println("seleIncColumns4IncreUpdateAL.size()===>"+seleIncColumns4IncreUpdateAL.size());
				if(seleIncColumns4IncreUpdateAL.size()<=0){
					this.message = "No data(s) to move";
					color4FactMsg = "red";
					return;
				}

				
				
				for(int i=0;i<seleIncColumns4IncreUpdateAL.size();i++){
					System.out.println("seleIncColumns4IncreUpdateAL===>"+seleIncColumns4IncreUpdateAL.get(i));
					selectedInccolumns4IncreUpdateTM.remove(seleIncColumns4IncreUpdateAL.get(i));
					if(!columns4IncreUpdateTM.containsValue(seleIncColumns4IncreUpdateAL.get(i))){
						columns4IncreUpdateTM.put(seleIncColumns4IncreUpdateAL.get(i), seleIncColumns4IncreUpdateAL.get(i));
//								
									
					}
				}
			  

			}
			
			
			else if(processFrom.equals("selmeasureColumn")){
				

				if(selectedMeasureColumnTM == null || selectedMeasureColumnTM.isEmpty()){
					this.message = "No data(s) to move";
					color4FactMsg = "red";
					return;
				}
				else if(selectedMeasurecolumnName == null || selectedMeasurecolumnName.length <= 0){
					this.message = "Please select selected measure column(s) to move backward";
					color4FactMsg = "red";
					return;
				}
				
	for(int i=0;i<selectedMeasurecolumnName.length;i++){
	
	selectedMeasureColumnTM.remove(selectedMeasurecolumnName[i]);
	copySelectedMeasureColumnTM.remove(selectedMeasurecolumnName[i]);
	columnNameTM.put(selectedMeasurecolumnName[i], selectedMeasurecolumnName[i]);
	copyColumnNameTM.put(selectedMeasurecolumnName[i], selectedMeasurecolumnName[i]);
	
	selectedMeasureColumnsAL.remove(selectedMeasurecolumnName[i]);
	}
			}else if(processFrom.equals("seljoinColumn")){
				

				if(selectedcolumnNameTM4Join == null || selectedcolumnNameTM4Join.isEmpty()){
					this.message = "No data(s) to move";
					color4FactMsg = "red";
					return;
				}
				else if(selectedjoincolumns == null || selectedjoincolumns.length <= 0){
					this.message = "Please select selected join column(s) to move backward";
					color4FactMsg = "red";
					return;
				}
				
for(int j=0;j<selectedjoincolumns.length;j++){
					
					columnNameTM4Join.put(selectedjoincolumns[j],selectedjoincolumns[j]);
					copyColumnNameTM4Join.put(selectedjoincolumns[j],selectedjoincolumns[j]);
					selectedcolumnNameTM4Join.remove(selectedjoincolumns[j]);
					copySelectedcolumnNameTM4Join.remove(selectedjoincolumns[j]);
					selectedJoinColumnsAL.remove(selectedjoincolumns[j]);
				}

			}else if(processFrom.equals("selperiodvalues")){
				
				if(selectedPeriodValuesAL == null || selectedPeriodValuesAL.isEmpty()){
					this.message = "No data(s) to move";
					color4FactMsg = "red";
					return;
				}
				else if(frwdPeriodValues == null || frwdPeriodValues.length <= 0){
					this.message = "Please select selected period value(s) to move backward";
					color4FactMsg = "red";
					return;
				}
				
				for(int k=0;k<frwdPeriodValues.length;k++){
					
					
					System.out.println("frwdPeriodValues[k]===>>>"+frwdPeriodValues[k]);
					
					selectedPeriodValuesAL.remove(frwdPeriodValues[k]);
					periodValuesAL.add(frwdPeriodValues[k]);
					copyPeriodValuesAL.add(frwdPeriodValues[k]);
					copySelectedPeriodValuesAL.remove(frwdPeriodValues[k]);
					
				}
			}
		}
		}catch(Exception e){
			e.printStackTrace();
		}
		System.out.println("Exiting: "
				+ new Exception().getStackTrace()[0].getClassName() + "."
				+ new Exception().getStackTrace()[0].getMethodName());
	}//end code change Jayaramu 20FEB14
	//Start Code Change Jayaramu 22FEB14
	

	
	public void addToArrayList(){
		
		try{
			if(selectedsegmentvalue.length<=0){   //code change Menaka 10MAR2014	
				segInfoMsg = "Please select an item from the list to add";
				return;
			}
		
		
		
		HeirarchyDataBean hdb;
		
		int k=0;
		int m = 0;
		String segmentCode = "";
		String segmentValue = "";
		String SegCodeValue = "";
		String segmentID = "";
		
		
		System.out.println("selectedsegmentvalue.length===>>>"+selectedsegmentvalue.length);
		System.out.println("codeandNameAL.size()===>>>"+codeandNameAL.size());
		System.out.println("selectedsegment===>>>"+selectedsegment);
		
		
		for(int j=0;j<selectedsegmentvalue.length;j++){
			
			if(selectedsegmentvalue[j].equals(segErrorMsg)){
				
				segInfoMsg = "Error occur can't add";
				return;
			}else if(selectedsegmentvalue[j].equals("No result(s) found")){
				
				segInfoMsg = "No result(s) found, so can't add";
				return;
			}
			
			
		segmentCode = segmentCode+selectedsegmentvalue[j].substring(selectedsegmentvalue[j].indexOf("(")+1, selectedsegmentvalue[j].indexOf(")"));
		segmentValue = segmentValue+selectedsegmentvalue[j].substring(selectedsegmentvalue[j].indexOf(")")+1,selectedsegmentvalue[j].length());
		SegCodeValue = segmentValue+("("+segmentCode+")").concat(";");
		segmentCode = segmentCode.concat(";");
		segmentValue = segmentValue.concat(";");
		}
		
		System.out.println("segmentCode===>>>"+segmentCode);
		System.out.println("segmentValue===>>>"+segmentValue);
		
		
		
		
		
		
			
			for(int i=0;i<codeandNameAL.size();i++){
				
				System.out.println("i===>>>"+i);
				
				hdb = (HeirarchyDataBean)codeandNameAL.get(i);
				
				if(hdb.getSegments().equals(selectedsegment)){
					k++;
					m=i;
					String segID  = hdb.getSegmentID(); 
					System.out.println("segID===>>>"+segID);
					String segcode = hdb.getCodevalue();
					System.out.println("segcode===>>>"+segcode);
					segmentCode = segcode+segmentCode;
					String segvalue = hdb.getNamevalue();
					System.out.println("segvalue===>>>"+segvalue);
					segmentValue = segvalue+segmentValue;
					
				}
			}
			Hashtable ht = new Hashtable<>();
			Collection c = getSegmentAL().keySet();
			Iterator itr = c.iterator();
			while(itr.hasNext()){
				String temp = (String)itr.next();
//				System.out.println("itr.next()===>"+itr.next());
//				System.out.println("String.valueOf(segmentAL.get(itr.next()))===>"+String.valueOf(segmentAL.get(String.valueOf(itr.next()))));
				ht.put(String.valueOf(getSegmentAL().get(temp)), temp);
			}
			if(k == 0){
				
				hdb =new HeirarchyDataBean(selectedsegment,segmentCode,segmentValue,"",SegCodeValue,costCenterTableName,costCenterColumnName,primaryType,false,String.valueOf(ht.get(selectedsegment)));
				 codeandNameAL.add(hdb);
			}
			if(k>0){
				
				codeandNameAL.remove(m);
				hdb =new HeirarchyDataBean(selectedsegment,segmentCode,segmentValue,"",SegCodeValue,costCenterTableName,costCenterColumnName,primaryType,false,String.valueOf(ht.get(selectedsegment)));
				 codeandNameAL.add(hdb);
			}
			
			
			
		
		
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		
		
	}
		public void defaultselectContd(){
			try{
				
			System.out.println("segSelectedsegment===>"+segSelectedsegment);
			
			if(segSelectedsegment.equalsIgnoreCase("null")){
				
				segselectedcondition = "null";
				
			}else{
				
			}
			System.out.println("segSelectedsegment111===>"+segSelectedsegment);
			}catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}//End Code Change Jayaramu 22FEB14
		
	//Start code change Jayaramu 05FEB14	
	public void getTargetTableColumn(String selectedTargetTableName,String tableNameFrom,Connection con){
	
		try{
			
			if(selectedTargetTableName == null || selectedTargetTableName.equals("") || selectedTargetTableName.equals("null")){
				return;
			}
//			if()
		Connection connection = con;	
		 System.out.println("Successfully Connected to the database!");
		Statement statement = connection.createStatement();
		String sql="";
		sql="SELECT * FROM "+selectedTargetTableName;
		ResultSet results = statement.executeQuery(sql);
			// Get resultset metadata
		ResultSetMetaData metadata = results.getMetaData();
		int columnCount = metadata.getColumnCount();
		if(tableNameFrom.equals("measureTable")){
		targetColumNames=new ArrayList();
			for (int i=1; i<=columnCount; i++) {
		  String columnName = metadata.getColumnName(i);
		  targetColumNames.add(columnName);
			}
		}else if(tableNameFrom.equals("joinTable")){
			joinTargetColumNamesAL=new TreeMap();
			for (int i=1; i<=columnCount; i++) {
		  String columnName = metadata.getColumnName(i);
		  joinTargetColumNamesAL.put(columnName,columnName);
			}
		}
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}//End code change Jayaramu 05FEB14
	String msg4UpdateIncrLoad = "";
	public String getMsg4UpdateIncrLoad() {
		return msg4UpdateIncrLoad;
	}
	public void setMsg4UpdateIncrLoad(String msg4UpdateIncrLoad) {
		this.msg4UpdateIncrLoad = msg4UpdateIncrLoad;
	}
	public void deleteFactDataTableList(String deleteDataFrom){
		System.out.println("Entering: "
				+ new Exception().getStackTrace()[0].getClassName() + "."
				+ new Exception().getStackTrace()[0].getMethodName());
		try{
			this.message = "";
		if(deleteDataFrom.equals("deleteMeasureDataTableList")){
			msg4WarHouseLoad = "";
//			if(selectedMeasureColumnsAL == null || selectedMeasureColumnsAL.isEmpty()){
//				this.message = "No data(s) to delete.";
//				color4FactMsg = "red";
//				return;
//			}
			if(selectedMeasureList.size()<=0){
				this.message = "Please select atleast one row to delete.";
				color4FactMsg = "red";
				return;
			}
			
			for(int i=0;i<selectedMeasureList.size();i++){
				
				selectedMeasureColumnsAL.remove(selectedMeasureList.get(i));
				
				
			}
			
			System.out.println("deleted Fact Measure DataTable List=====>>>");
			
			msg4WarHouseLoad = "Deleted successfully.";
			color4SrcFactMsg = "blue";
		}else if(deleteDataFrom.equals("deleteJoinDataTableList")){
			
			msg4JoinColumnList = "";
				if(selectedJoinColumnsAL == null || selectedJoinColumnsAL.isEmpty()){
					this.message = "No data(s) to delete.";
					color4FactMsg = "red";
					return;
				}
				else if(selectedJoinList.size()<=0){
					this.message = "Please select atleast one row to delete.";
					color4FactMsg = "red";
					return;
				}
				
			for(int j=0;j<selectedJoinList.size();j++){
				
				selectedJoinColumnsAL.remove(selectedJoinList.get(j));
			}
			System.out.println("deleted Fact Join DataTable List=====>>>");
			msg4JoinColumnList = "Deleted successfully.";
			color4SrcFactMsg = "blue";
		}else if(deleteDataFrom.equals("deleteConfigureJoins")){
			msg4SrcWarJoin = "";
			if(srcAndWHJoinsAL == null || srcAndWHJoinsAL.isEmpty()){
				this.message = "No data(s) to delete.";
				color4FactMsg = "red";
				return;
			}
			else if(selectedsrcAndWHJoinsAL.size()<=0){
				this.message = "Please select atleast one row to delete.";
				color4FactMsg = "red";
				return;
			}
			
		for(int j=0;j<selectedsrcAndWHJoinsAL.size();j++){
			
			srcAndWHJoinsAL.remove(selectedsrcAndWHJoinsAL.get(j));
		}
		for(int j=0;j<srcAndWHJoinsAL.size();j++){
			HeirarchyDataBean hdb = (HeirarchyDataBean) srcAndWHJoinsAL.get(j);
			if(!selectedSrcTableTM.containsValue(hdb.sourceTableName)){
				selectedSrcTableTM.put(hdb.sourceTableName, hdb.sourceTableName);
			}
		}
		
		System.out.println("deleted Configure Join DataTable List=====>>>");
		msg4SrcWarJoin = "Deleted successfully.";
		color4SrcFactMsg = "blue";
		}else if(deleteDataFrom.equals("IncrementalLoad")){
			msg4IncrementLoad = "";
			if(incrementalLoadAL == null || incrementalLoadAL.isEmpty()){
				this.message = "No data(s) to delete.";
				color4FactMsg = "red";
			}else if(selectedincrementalLoadAL.size()<=0){
				this.message = "Please select atleast one row to delete.";
				color4FactMsg = "red";
				return;
			}
			for(int j=0;j<selectedincrementalLoadAL.size();j++){
				
				incrementalLoadAL.remove(selectedincrementalLoadAL.get(j));
			}
			msg4IncrementLoad = "Deleted successfully.";
			color4SrcFactMsg = "blue";
		}else if(deleteDataFrom.equals("UpdateIncrementalLoad")){
			msg4UpdateIncrementLoad = "";
			if(updateIncrementalLoadAL == null || updateIncrementalLoadAL.isEmpty()){
				this.message = "No data(s) to delete.";
				color4FactMsg = "red";
			}else if(selectedUpdateIncrementalLoadAL.size()<=0){
				this.message = "Please select atleast one row to delete.";
				color4FactMsg = "red";
				return;
			}
			for(int j=0;j<updateIncrementalLoadAL.size();j++){
				
				updateIncrementalLoadAL.remove(selectedUpdateIncrementalLoadAL.get(j));
			}
			msg4UpdateIncrementLoad = "Deleted successfully.";
			color4SrcFactMsg = "blue";
		}else if(deleteDataFrom.equals("UpdateLoad")){
			msg4UpdateIncrLoad = "";
			if(incrementalLoadUpdateAL == null || incrementalLoadUpdateAL.isEmpty()){
				this.message = "No data(s) to delete.";
				color4FactMsg = "red";
			}else if(selectedincrementalLoadUpdateAL.size()<=0){
				this.message = "Please select atleast one row to delete.";
				color4FactMsg = "red";
				return;
			}
			for(int j=0;j<incrementalLoadUpdateAL.size();j++){
				
				incrementalLoadUpdateAL.remove(selectedincrementalLoadUpdateAL.get(j));
			}
			msg4UpdateIncrLoad = "Deleted successfully.";
			color4SrcFactMsg = "blue";
		}
		
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
	}
		System.out.println("Exiting: "
				+ new Exception().getStackTrace()[0].getClassName() + "."
				+ new Exception().getStackTrace()[0].getMethodName());
	}

	String selectedFactType = "";
	
	public String getSelectedFactType() {
		return selectedFactType;
	}
	public void setSelectedFactType(String selectedFactType) {
		this.selectedFactType = selectedFactType;
	}
	String msg4UpdateIncrementLoad = "";
	public String getMsg4UpdateIncrementLoad() {
		return msg4UpdateIncrementLoad;
	}
	public void setMsg4UpdateIncrementLoad(String msg4UpdateIncrementLoad) {
		this.msg4UpdateIncrementLoad = msg4UpdateIncrementLoad;
	}
	public void writeFactTablesToXml(String isTempSave,String FactGenType){
		System.out.println("Entering: "
				+ new Exception().getStackTrace()[0].getClassName() + "."
				+ new Exception().getStackTrace()[0].getMethodName());
		try{
		 System.out.println("selTableName====>>>"+selTableName);
		 System.out.println("Connection====>>>"+connName);
		  if(selTableName != null && !selTableName.equals("")){
			  
			  this.message="";	
			  System.out.println("isTempSave====>>>"+isTempSave);
			  int q=0;
			  color4FactMsg = "red";
			  if(srcandTarFactTablesAl == null || srcandTarFactTablesAl.isEmpty()){
				  if(isTempSave.equals("FromSaveLater")){
					  this.message = "The Fact Table(s) are configured successfully. To generate Fact data use Generate Data option(Warning : No Source and Target table)."; 
				  }else if(isTempSave.equals("FromGenerate") || isTempSave.equals("FromAdminGenerate")){
					  this.message = "No Source and Target table";
				  }
				  
				  q++;
			  }else if(selectedJoinColumnsAL == null || selectedJoinColumnsAL.isEmpty()){
				  if(isTempSave.equals("FromSaveLater")){
				  	  this.message = "The Fact Table(s) are configured successfully. To generate Fact data use Generate Data option(Warning : No row in Join column(s) list section).";
				  }else if(isTempSave.equals("FromGenerate") || isTempSave.equals("FromAdminGenerate")){
					  this.message = "No row in Join column(s) list section";
				  }
				  
				  q++;
			  }else if(srcAndWHJoinsAL == null || srcAndWHJoinsAL.isEmpty() || selectedMeasureColumnsAL == null || selectedMeasureColumnsAL.isEmpty()){
				  if(isTempSave.equals("FromSaveLater")){
				  	  this.message = "The Fact Table(s) are configured successfully. To generate Fact data use Generate Data option(Warning : No row in configure source and warehouse joins section).";
				  }else if(isTempSave.equals("FromGenerate") || isTempSave.equals("FromAdminGenerate")){
					  this.message = "No row in configure source and warehouse joins section";
				  }
				  
				  
			  }else if(selectedPeriodValuesAL == null || selectedPeriodValuesAL.isEmpty()){
				  if(isTempSave.equals("FromSaveLater")){
					  this.message = "The Fact Table(s) are configured successfully. To generate Fact data use Generate Data option(Warning : No row in Configure Warehouse Load section).";
				  }else if(isTempSave.equals("FromGenerate") || isTempSave.equals("FromAdminGenerate")){
					  this.message = "No row in Configure Warehouse Load section";
				  }
				  
				  q++;
			  }
for(int i=0;i<srcAndWHJoinsAL.size();i++){ //code change Jayaramu 05APR14
				  
				  HeirarchyDataBean hyDB = (HeirarchyDataBean) srcAndWHJoinsAL.get(i);
				 String oper = hyDB.getJoinColumnOper();
				  String srctabName = hyDB.getSourceTableName();
				 String tarTabName = hyDB.getTarTableName();
				 String sourceColumName=  hyDB.getSourcecolumnName();
				  String tarcolumName = hyDB.getTarColumnName();
				  String istableNameString = "";
				  boolean istableStrng = false;
//				  if(!tarTabName.equals("")){
//				  istableNameString = tarTabName.substring(0,1);
//				  System.out.println("istableNameString======>>>>"+istableNameString);
//				  if(istableNameString.equals("")){
//					  if(oper != null){
//					  if(oper.equals("BETWEEN")){
//						  if(!tarcolumName.equals("")){
//							  istableStrng = true;
//						  }  
//					  }else{
//						  istableStrng = true;
//						  }
//					  }
//				  }else{
//					  if(!tarcolumName.equals("")){
//						  istableStrng = true;
//					  }
//				  }
//				  }
				  if((tarcolumName.equals("") && tarTabName.equals("")) || tarTabName.equals("")){
					  istableStrng = false;
				  }else if(tarcolumName.equals("")){
					  if(oper != null){
						  if(oper.equals("BETWEEN")){
							  
								  istableStrng = false;
							  
						  }else{
							  istableStrng = true;
							  }
						  }
				  }else{
					  istableStrng = true;
				  }
				  System.out.println("istableStrng======>>>>"+istableStrng);
				  if(oper != null && !srctabName.equals("") && !tarTabName.equals("") && istableStrng && sourceColumName != null){
					  
				  }else if(oper == null && srctabName.equals("") && tarTabName.equals("") && sourceColumName == null && tarcolumName.equals("")){
				  
				  }else{
					  if(isTempSave.equals("AddFactSourceTargetTables")){
						  message = "Warning : Row(s) are incomplete in Configure Source and Warehouse Joins.";
					  }else if(isTempSave.equals("FromSaveLater")){
						  message = "The Fact Table(s) are configured successfully. To generate Fact data use Generate Data option(Warning : Row(s) are incomplete in Configure Source and Warehouse Joins).";
					  }else if(isTempSave.equals("FromGenerate") || isTempSave.equals("FromAdminGenerate")){
						  message = "Row(s) are incomplete in Configure Source and Warehouse Joins."; 
					  }
					  
					  q++;
				  }
				  
				  
			  }
for(int i=0;i<selectedMeasureColumnsAL.size();i++){ //code change Jayaramu 05APR14
				  
	 HeirarchyDataBean hyDB = (HeirarchyDataBean) selectedMeasureColumnsAL.get(i);
	String targetColumnName = hyDB.getSelectedTargetColumn();
	String srouceTable = hyDB.getSelectedSrcTable();
	String property = hyDB.getProperty();
	String srcTable = hyDB.getSourceTable();
	  if(targetColumnName != null && srouceTable != null && !property.equals("-- select Value --")  && srcTable != null){
		  
	  }else if(targetColumnName == null && srouceTable == null && property.equals("-- select Value --")  && srcTable == null){
		  
		  
	  }else{
		  if(isTempSave.equals("AddFactSourceTargetTables")){
		  message = "Warning : Row(s) are incomplete in Configure Warehouse Load.";
		  }else if(isTempSave.equals("FromSaveLater")){
		  message = "The Fact Table(s) are configured successfully. To generate Fact data use Generate Data option(Warning : Row(s) are incomplete in Configure Warehouse Load).";
		  }else if(isTempSave.equals("FromGenerate") || isTempSave.equals("FromAdminGenerate")){
			  message = "Row(s) are incomplete in Configure Warehouse Load.";
		  }
		  q++;
	  }
	
			  }
			  
for(int i=0;i<selectedJoinColumnsAL.size();i++){ //code change Jayaramu 07APR14
	
	 HeirarchyDataBean hyDB = (HeirarchyDataBean) selectedJoinColumnsAL.get(i);
	 String targetColumnName = hyDB.getSelectedJoinTargetTable();
	 String sourceColumnName = hyDB.getJoinSourceTable();
	 if(targetColumnName != null && sourceColumnName != null){
			  
		  }else if(targetColumnName == null && sourceColumnName == null){
			  		  
		  }else{
			  if(isTempSave.equals("AddFactSourceTargetTables")){
			  message = "Warning : Row(s) are incomplete in Join Column(s) List.";
			  }else if(isTempSave.equals("FromSaveLater")){
			  message = "The Fact Table(s) are configured successfully. To generate Fact data use Generate Data option(Warning : Row(s) are incomplete in Join Column(s) List).";
			  }else if(isTempSave.equals("FromGenerate") || isTempSave.equals("FromAdminGenerate")){
				  message = "Row(s) are incomplete in Join Column(s) List.";
			  }
			  q++;
		  }
}
				if(selectedPeriodValuesAL.size()<1){
					if(isTempSave.equals("AddFactSourceTargetTables")){
						  message = "Warning : Please select period(s).";
						  }else if(isTempSave.equals("FromSaveLater")){
						  message = "The Fact Table(s) are configured successfully. To generate Fact data use Generate Data option(Warning : Please select period(s)).";
						  }else if(isTempSave.equals("FromGenerate")){
							  message = "Please select period(s).";
						  }
						  q++;
					
				}
				
			
				
					HierarchydataBean hyDB = (HierarchydataBean) srcandTarFactTablesAl.get(0);
					String pridcol = hyDB.getPeriodCol4src();
					
					System.out.println("periodCol4src====>>>"+periodCol4src);
					if(isTempSave.equals("AddFactSourceTargetTables")){
						 if(periodCol4src.equals("-- select Value --")){
						  message = "Warning : Please select period(s).";
						  q++;
						 }
						  }else if(isTempSave.equals("FromSaveLater")){
							  if(pridcol.equals("-- select Value --")){
						  message = "The Fact Table(s) are configured successfully. To generate Fact data use Generate Data option(Warning : Please select period column).";
							q++;
							  }
							  }else if(isTempSave.equals("FromGenerate") || isTempSave.equals("FromAdminGenerate")){
								  if(pridcol.equals("-- select Value --")){
							  message = "Please select period column.";
								q++;
								  }}
						 if(incrementalLoadUpdateAL.size()>=1){
							 if(updateIncrementalLoadAL.size()<1){
								 msg4UpdateIncrementLoad  = "Please select unique identifier for incremental update";
							 }
						 }
				
				
				FacesContext ctx = FacesContext.getCurrentInstance();
				ExternalContext extContext = ctx.getExternalContext();
				Map sessionMap = extContext.getSessionMap();
				HierarchyBean hryb = (HierarchyBean) sessionMap.get("hierarchyBean");
				
				
				
			PropUtil prop=new PropUtil();
			String dir=prop.getProperty("HIERARCHY_XML_DIR");
			System.out.println("hierarchyConfigFileName====>>>"+hierarchyXMLFileName);
			Document doc = Globals.openXMLFile(dir, hierarchyXMLFileName);
			Element factNode1 = null;
			String tableNames="";
			String columns="";
			Element table = null;
			Element columnNames = null;
			Element joinColumnNames = null;
			Element srcWH = null;
			Element periodValue = null;
			Element factResult=null;  // code change Menaka 15FEB2014
			Element errorMessage=null; 
			Element measureChildColumns = null;
			Element joinChildColumns = null;
			String heirID="";
			Node factNode = null;
			Node parentfactNode = null;
			String periodcolumn="";  
			  
			  
			  heirID= hryb.getHierarchy_ID();//Code Change Gokul 21FEB2014
//				Node rootnode = Globals.getNodeByAttrVal(doc, "Hierarchy_Level", "Hierarchy_ID",heirID);
				
				if(hryb.getFlagNew().equals("newAdded")){
					hryb.addHierarchyNodes("Fromsegment",false);
					hryb.setFlagNew("alreadyadded");
					 doc = Globals.openXMLFile(dir, hierarchyXMLFileName);
					
				}
				Node rootnode = Globals.getNodeByAttrVal(doc, "Hierarchy_Level", "Hierarchy_ID",heirID);
				Node factNodeTables = Globals.getNodeByAttrVal(doc, "Fact_Tables", "ID", heirID);
				Element hierElement = (Element)rootnode;
				
				if(factNodeTables!=null){
//					 System.out.println("1====>>>");
					if(FactGenType.equalsIgnoreCase("GenerateData")){
						if(Globals.getAttrVal4AttrName(factNodeTables, "Previous_Gen_Type").equalsIgnoreCase("GenerateFact")){
							FactGenType = "InsertFact";
						}else if(Globals.getAttrVal4AttrName(factNodeTables, "Previous_Gen_Type").equalsIgnoreCase("InsertFact") ||
								Globals.getAttrVal4AttrName(factNodeTables, "Previous_Gen_Type").equalsIgnoreCase("UpdateFact") ||
								Globals.getAttrVal4AttrName(factNodeTables, "Previous_Gen_Type").equalsIgnoreCase("")){
							FactGenType = "GenerateFact";
						}
					}else if(FactGenType.equalsIgnoreCase("InsertFact") && Globals.getAttrVal4AttrName(factNodeTables, "Gen_Mode").equalsIgnoreCase("")){
						message = "Full Load not generated so not possible to generate Incremental Load";
						return;
					}
				NodeList rootNodeChild = rootnode.getChildNodes();
//				 System.out.println("2====>>>");
				Node factNodes = null;
			for(int i=0;i<rootNodeChild.getLength();i++){
//				 System.out.println("3====>>>");
				
				factNodes = rootNodeChild.item(i);
				if(factNodes.getNodeName().equals("Fact_Tables")){
//					System.out.println("4====>>>");
					NodeList tableChild = factNodes.getChildNodes();
					Element factNode2 = (Element) factNodes;
					factNode2.setAttribute("Connection_Source", connectionName);
					factNode2.setAttribute("Connection_Target", tarConnectionName);
//					factNode2.setAttribute("Gen_Mode", selectedFactType);
					factNode2.setAttribute("Gen_Mode", FactGenType);
//					factNode2.setAttribute("Previous_Gen_Type", FactGenType);
					for(int j=0;j<tableChild.getLength();j++){
//						System.out.println("5====>>>");
						if(tableChild.item(j).getNodeName().equals("Table")){
//							System.out.println("6====>>>");
							factNode1 =(Element) factNodes;
							
							String tableName = tableChild.item(j).getAttributes().getNamedItem("Source_Fact_TableName").getTextContent().toString();
							String targetTableName = tableChild.item(j).getAttributes().getNamedItem("Target_Fact_TableName").getTextContent().toString();
							
							if(tableName.equals(selTableName) && targetTableName.equals(tarSelTableName)){
//								System.out.println("7====>>>"+tableName);
								table = (Element) tableChild.item(j);
								NodeList childNodeOfTable = tableChild.item(j).getChildNodes();
//								System.out.println("8====>>>"+tableChild.item(j).getNodeName());
								for(int k=0;k<childNodeOfTable.getLength();k++){
									
									if(childNodeOfTable.item(k).getNodeType() == Node.ELEMENT_NODE){
//									System.out.println("9==r==>>>"+childNodeOfTable.item(k).getNodeName());
									
									tableChild.item(j).removeChild(childNodeOfTable.item(k));
								}}
								
//								Globals.writeXMLFile(doc, dir, hierarchyXMLFileName);
							}
						}
						
					}
				}
				
				
			}
			
//				if(factNode1 == null){
//					
//					factNode1 = doc.createElement("Fact_Tables");
//					
//				}
				
				if(table == null){
					
					table = doc.createElement("Table");
					rootnode.appendChild(factNodeTables);
//					factNode1.setAttribute("ID", hryb.getHierarchy_ID());
					parentfactNode = (Node) factNodeTables;
					parentfactNode.appendChild(table);
					table.setAttribute("Source_Fact_TableName", selTableName);
				}
				
				}
				
//				Node deleteExists = Globals.getNodeByAttrVal(doc, "Fact_Tables", "ID", heirID);
////				
//				if(deleteExists != null){
//					
//					
//					deleteExists.getParentNode().removeChild(deleteExists);
//				}
				
				
//				rootnode = Globals.getNodeByAttrVal(doc, "Hierarchy_Level", "Hierarchy_ID", hryb.getHierarchy_ID());
					
				System.out.println("Creation Started ====>>>"+factNode1);
				System.out.println("Creation Started ====>>>"+table);
				System.out.println("factNodeTables====>>>"+factNodeTables);
				
				
				
				
				if(factNodeTables==null){
					
					factNode1 = doc.createElement("Fact_Tables");
					table = doc.createElement("Table");
					rootnode.appendChild(factNode1);
					factNode1.setAttribute("ID", hryb.getHierarchy_ID());
					factNode1.setAttribute("Connection_Source", connectionName);
//					factNode1.setAttribute("Gen_Mode", selectedFactType);
					if(FactGenType.equalsIgnoreCase("GenerateData")){
						if(Globals.getAttrVal4AttrName(factNodeTables, "Previous_Gen_Type").equalsIgnoreCase("GenerateFact")){
							FactGenType = "InsertFact";
						}else if(Globals.getAttrVal4AttrName(factNodeTables, "Previous_Gen_Type").equalsIgnoreCase("InsertFact") ||
								Globals.getAttrVal4AttrName(factNodeTables, "Previous_Gen_Type").equalsIgnoreCase("UpdateFact") ||
								Globals.getAttrVal4AttrName(factNodeTables, "Previous_Gen_Type").equalsIgnoreCase("")){
							FactGenType = "GenerateFact";
						}
					}else if(FactGenType.equalsIgnoreCase("InsertFact") && Globals.getAttrVal4AttrName(factNodeTables, "Gen_Mode").equalsIgnoreCase("")){
						message = "Full Load not generated so not possible to generate Incremental Load";
						return;
					}
					factNode1.setAttribute("Gen_Mode", FactGenType);
					factNode1.setAttribute("Connection_Target", tarConnectionName);
					parentfactNode = (Node) factNode1;
					parentfactNode.appendChild(table);
					table.setAttribute("Source_Fact_TableName", selTableName);
					
					factResult = doc.createElement("Fact_Result"); // code change Menaka 15FEB2014
					 errorMessage = doc.createElement("Error_Message");  
					 parentfactNode.appendChild(factResult);// code change Menaka 15FEB2014
						parentfactNode.appendChild(errorMessage);
				}
				
					
//				System.out.println("factNodeTables====>>>"+periodCol4src);	
//				System.out.println("FromSaveLater====>>>"+isTempSave);	
//				HierarchydataBean hd = (HierarchydataBean)srcandTarFactTablesSelectedAl.get(0);
				if(isTempSave.equalsIgnoreCase("FromSaveLater") || isTempSave.equalsIgnoreCase("FromGenerate")){
					if(srcandTarFactTablesSelectedAl.size()>0){
					HierarchydataBean hb = (HierarchydataBean) srcandTarFactTablesSelectedAl.get(0);
					table.setAttribute("Source_Period_Column",hb.getPeriodCol4src());
					}
				}else{

					table.setAttribute("Source_Period_Column",periodCol4src);
				}
				
					factNode = (Node) table;
					columnNames = doc.createElement("Measure_Columns");
					joinColumnNames = doc.createElement("Join_Columns");
					srcWH = doc.createElement("Source_Warehouse_Joins");
					periodValue = doc.createElement("Period_Values");
					Node incrLoad = doc.createElement("Incremental_Load");
					Node incrUpdateNd = doc.createElement("Incremental_Update");
					System.out.println("selectedJoinType4InsertIncr====>>>"+selectedJoinType4InsertIncr);	
					Element incrLoadEle = (Element)incrLoad; 
					incrLoadEle.setAttribute("Join_Type4Insert", selectedJoinType4InsertIncr);
					System.out.println("selectedJoinType4UpdateIncr====>>>"+selectedJoinType4UpdateIncr);
					Element incrUpdateEle = (Element)incrUpdateNd; 
					incrLoadEle.setAttribute("Join_Type4Update", selectedJoinType4UpdateIncr);
					
					factNode.appendChild(columnNames);
					factNode.appendChild(joinColumnNames);
					factNode.appendChild(srcWH);
					factNode.appendChild(periodValue);
					factNode.appendChild(incrLoad);
					factNode.appendChild(incrUpdateNd);
					if(selectedColumn4HierDim != null){
					joinColumnNames.setAttribute("Dim_Code_Column",selectedColumn4HierDim);
					}else{
						joinColumnNames.setAttribute("Dim_Code_Column","");
					}
				System.out.println("tableName====>>>"+tableName);
				System.out.println("columnName====>>>"+columnName);
				System.out.println("joincolumn====>>>"+joincolumn);
				System.out.println("periodcolumn====>>>"+periodcolumn);
				

//				String joincolumnNames[] = joincolumn.split(";");
				TreeMap joincolumnNames = selectedcolumnNameTM4Join;  // code change Menaka 14FEB2014
				TreeMap measurecolumnNames = selectedMeasureColumnTM;
				String joinColumnAttr = "";
				String measureclmn="";
				String factTableName = "";
				String measureCheck;
				String joinclmn = "";
				String targetTableName = "";
				
				
				
				if(selectedTableNameTM != null && !selectedTableNameTM.isEmpty()){
				Set set = selectedTableNameTM.entrySet(); 
				Iterator i = set.iterator(); 
				while(i.hasNext()) { 
				Map.Entry me = (Map.Entry)i.next(); 
				System.out.print(me.getKey() + ": "); 
				System.out.println(me.getValue()); 
				String facttables = (String) me.getValue();
				factTableName = factTableName+facttables.concat(";");	
				}
				}
				targetTableName = tarSelTableName;
				String srouceTable = "";
				System.out.println("targetTableName====>>>"+targetTableName);
				System.out.println("srouceTable====>>>"+srouceTable);
				int m=0;
				int p=0;
				HeirarchyDataBean values;
				System.out.println("selectedMeasureColumnsAL: "+selectedMeasureColumnsAL.size());
				for(int i=0;i<selectedMeasureColumnsAL.size();i++){ //for get selected Measure Column from measure datatable
					m++;
					values = (HeirarchyDataBean)selectedMeasureColumnsAL.get(i);
					
//					String targetColumnName = values.getSelectedTargetTable();
					String targetColumnName = values.selectedTargetColumn;
					System.out.println("targetColumnName====>>> "+targetColumnName);
					srouceTable = values.getSelectedSrcTable();
					if(targetColumnName!=null){
						if(values.property.equalsIgnoreCase("Period")){
							if(p>=1){
								p++;
							}else{
								measureChildColumns = doc.createElement("Measure_Column"+m);
								columnNames.appendChild(measureChildColumns);
//								targetColumnName = "";
								
								measureclmn = targetTableName+"."+targetColumnName+"="+srouceTable+"."+values.getSourceTable();
								System.out.println("measureclmn====>>>"+measureclmn);
								measureCheck = values.property;
								measureChildColumns.setTextContent(measureclmn);
								measureChildColumns.setAttribute("Column_Property", values.property);
								p++;
							}
						}else{
						measureChildColumns = doc.createElement("Measure_Column"+m);
						columnNames.appendChild(measureChildColumns);
//						targetColumnName = "";
						
						measureclmn = targetTableName+"."+targetColumnName+"="+srouceTable+"."+values.getSourceTable();
						System.out.println("measureclmn====>>>"+measureclmn);
						measureCheck = values.property;
						measureChildColumns.setTextContent(measureclmn);
						
						
						
							measureChildColumns.setAttribute("Column_Property", values.property);
						}
						
						measureChildColumns.setAttribute("Source_Column_Function", values.additionalFunction4SrcColumn);
						measureChildColumns.setAttribute("Target_Column_Function", values.additionalFunction4TarColumn);
						
				}
					
				}
				
//				String srouceTable1 = hydb.getSrcFactTable();
				int n=0;
				System.out.println("selectedJoinColumnsAL: " + selectedJoinColumnsAL.size());
				for(int i=0;i<selectedJoinColumnsAL.size();i++){ //for get selected Join column from join datatable
				n++;
				
				values = (HeirarchyDataBean)selectedJoinColumnsAL.get(i);
				srouceTable = values.joinSourceTableName;
				String targetColumnName = values.getSelectedJoinTargetTable();
				if(targetColumnName!=null){
					joinChildColumns = doc.createElement("Join_Column"+n);
					joinColumnNames.appendChild(joinChildColumns);	
//					targetColumnName = "";
					joinclmn = "WC_FLEX_HIERARCHY_D"+"."+targetColumnName+"="+selTableName+"."+values.getJoinSourceTable();
					joinChildColumns.setTextContent(joinclmn);
				}
				
				}
				m = 0;
				for(int i=0;i<srcAndWHJoinsAL.size();i++){
					m++;
					HeirarchyDataBean hdb = (HeirarchyDataBean) srcAndWHJoinsAL.get(i);
					Element srcWHConfigEle = doc.createElement("Source_Warehouse_Joins"+m);
					srcWH.appendChild(srcWHConfigEle);
					srcWHConfigEle.setAttribute("Join_Operator", hdb.getJoinColumnOper());
					System.out.println("tarTableName====>>>"+hdb.tarTableName);
					if(hdb.tarColumnName.equals("") ){
						srcWHConfigEle.setAttribute("Join_type", "Value");
						
							
							srcWHConfigEle.setAttribute("Target_Column", hdb.tarTableName);
						
						
					}else if(!hdb.tarColumnName.equals("") || !hdb.tarTableName.equals("")){
						if(hdb.getJoinColumnOper().equalsIgnoreCase("Between")){
							srcWHConfigEle.setAttribute("Join_type", "Value");
							srcWHConfigEle.setAttribute("Target_Column", hdb.tarTableName+"#-#"+hdb.tarColumnName);
						}else{
							srcWHConfigEle.setAttribute("Join_type", "Column");
							srcWHConfigEle.setAttribute("Target_Column", hdb.tarTableName+"."+hdb.tarColumnName);
						}
					}
					srcWHConfigEle.setAttribute("Source_Column", hdb.sourceTableName+"."+hdb.sourcecolumnName);
					srcWHConfigEle.setAttribute("Source_Connection", hdb.srcConn);
					
					srcWHConfigEle.setAttribute("Target_Connection", hdb.tarConn);
					srcWHConfigEle.setAttribute("Source_Column_Function", hdb.additionalFunction4SrcWarColumn);
					srcWHConfigEle.setAttribute("Target_Column_Function", hdb.additionalFunction4TarWarColumn);
				}
				m = 0;
				for(int i=0;i<incrementalLoadAL.size();i++){
					m++;
					HeirarchyDataBean hdb = (HeirarchyDataBean) incrementalLoadAL.get(i);
					System.out.println("hdb.getTarCloumn4incr()====>>>"+hdb.getIncrementalType()+"hdb.getSrcCloumn4incr()"+hdb.getSrcCloumn4incr());
					Element incrEle = doc.createElement("Column"+m);
					incrLoad.appendChild(incrEle);
					String temp = selTableName+"."+hdb.getSrcCloumn4incr();
					incrEle.setAttribute("Type","Insert");
					
					incrEle.setTextContent(temp);
				}
				
				for(int i=0;i<incrementalLoadUpdateAL.size();i++){
					HeirarchyDataBean hdb = (HeirarchyDataBean) incrementalLoadUpdateAL.get(i);
					Element incrEle = doc.createElement("Column"+m);
					incrLoad.appendChild(incrEle);
					String temp = selTableName+"."+hdb.srcCloumn4incrUpdate;
					incrEle.setAttribute("Type","Update");
					
					incrEle.setTextContent(temp);
				}
			
				for(int i=0;i<updateIncrementalLoadAL.size();i++){
					HeirarchyDataBean hdb = (HeirarchyDataBean) updateIncrementalLoadAL.get(i);
					Element incrEle = doc.createElement("Unique_Identifier");
					incrUpdateNd.appendChild(incrEle);
					String temp = selTableName+".";
					String uniColumn = temp.concat(hdb.srcCloumn4uniqUpdate);
					String tartemp = tarSelTableName+".";
					String uniColumnTar = tartemp.concat(hdb.tarCloumn4incr);
					incrEle.setTextContent(uniColumnTar+"="+uniColumn);
				}
				
//				HierarchydataBean HyDB;
//				HyDB=(HierarchydataBean)srcandTarFactTablesAl.get(0);
//				tableName[0] = HyDB.getSrcFactTable();
				System.out.println("table====>>>"+table);
				System.out.println("columnNames====>>>"+columnNames);
				System.out.println("joinColumnNames====>>>"+joinColumnNames);
//				System.out.println("periodName====>>>"+periodName);
//					System.out.println("set texet====>>>"+HyDB.getSrcFactTable());
					System.out.println("set texet columns====>>>"+measureclmn);
					System.out.println("periodcolumn====>>>"+periodcolumn);
					String periodColumnWithTableName = selTableName+"."+periodcolumn;
				table.setAttribute("Target_Fact_TableName", targetTableName);
				System.out.println("periodColumnWithTableName====>>>"+periodColumnWithTableName);
//				periodName.setTextContent(periodColumnWithTableName);
//				periodName.setAttribute("Column_Name", periodcolumn);
				String periodValus="";
				System.out.println("selectedPeriodValuesAL====>>>"+selectedPeriodValuesAL.size());
				for(int k=0;k<selectedPeriodValuesAL.size();k++){   // code change Menaka 14FEB2014
					periodValus=periodValus.concat(String.valueOf(selectedPeriodValuesAL.get(k)))+";";
				}
				
				System.out.println("periodValus----->>>"+periodValus);
				
				periodValue.setTextContent(periodValus); 
				 if(q==0){
					  hierElement.setAttribute("Fact_Config_Completed","Yes");
					  factMessageColor = "blue";
					  }else{
						  hierElement.setAttribute("Fact_Config_Completed","No");
						  factMessageColor = "red";
					  }
				
				
				Globals.writeXMLFile(doc, dir, hierarchyXMLFileName);
				
				if(p>=2){
					this.message = "The Fact Table(s) are configured successfully. To generate Fact data use Generate Data option(Warning : Configure Warehouse Load must have only one period column).";
					System.out.println("this.message----->>>"+this.message);
					return;
				}else if(p==0){
					this.message = "The Fact Table(s) are configured successfully. To generate Fact data use Generate Data option(Warning : Configure Warehouse Load must have atleast one period column.";
					System.out.println("this.message----->>>"+this.message);
					return;
				}
				System.out.println("this.isTempSave----->>>"+isTempSave);
//				 selTableName = HyDB.getSrcFactTable();
				  if(isTempSave.equals("FromSaveLater") && !isTempSave.equals("AddFactSourceTargetTables")){ 
					  if(q==0){
						  this.message = "The Fact Table(s) are configured successfully. To generate Fact data use Generate Data option.";
						  color4FactMsg = "blue";
					  }
					}else if(!isTempSave.equals("AddFactSourceTargetTables")){
						
						System.out.println("q----->>>"+q);
					if(q==0){
						// code change Menaka 21APR2014
					
						Thread t=new Thread(new genarateFactsBean(heirID,hryb.getLoginUserName(),FactGenType));
						t.start();//Code Change Gokul 21FEB2014
//						if(isTempSave.equals("FromAdminGenerate")){
//							 factMessageColor = "red";
//							this.message ="Warning : This Hierarchy is not approved yet.";
//						}else{
					this.message = "The Fact Tables are being generated now. You may track the progress from the Hierarchy List Screen.";
					color4FactMsg = "blue";
//						}
					}
					}
			  
		  }
		  
		  
		  
		 
		  
		  
		}catch(Exception e){
			e.printStackTrace();
		}
		System.out.println("Exiting: "
				+ new Exception().getStackTrace()[0].getClassName() + "."
				+ new Exception().getStackTrace()[0].getMethodName());
	}
		
	public ArrayList srcandTarFactTablesAl=new ArrayList<>();
	
	public ArrayList getSrcandTarFactTablesAl() {
		return srcandTarFactTablesAl;
	}
	
	public void setSrcandTarFactTablesAl(ArrayList srcandTarFactTablesAl) {
		this.srcandTarFactTablesAl = srcandTarFactTablesAl;
	}
	
	public ArrayList srcandTarFactTablesSelectedAl = new ArrayList<>();
		
	public ArrayList getSrcandTarFactTablesSelectedAl() {
		return srcandTarFactTablesSelectedAl;
	}

	public void setSrcandTarFactTablesSelectedAl(
			ArrayList srcandTarFactTablesSelectedAl) {
		this.srcandTarFactTablesSelectedAl = srcandTarFactTablesSelectedAl;
	}
	
	TreeMap srcColumnNameTM=new TreeMap<>();
	public TreeMap getSrcColumnNameTM() {
		return srcColumnNameTM;
	}

	public void setSrcColumnNameTM(TreeMap srcColumnNameTM) {
		this.srcColumnNameTM = srcColumnNameTM;
	}

	TreeMap tarColumnNameTM = new TreeMap<>();
	public TreeMap getTarColumnNameTM() {
		return tarColumnNameTM;
	}

	public void setTarColumnNameTM(TreeMap tarColumnNameTM) {
		this.tarColumnNameTM = tarColumnNameTM;
	}

	String tarColumnName="";
	public String getTarColumnName() {
		return tarColumnName;
	}

	public void setTarColumnName(String tarColumnName) {
		this.tarColumnName = tarColumnName;
	}

	String srcColumnName="";
	public String getSrcColumnName() {
		return srcColumnName;
	}

	public void setSrcColumnName(String srcColumnName) {
		this.srcColumnName = srcColumnName;
	}

	String srcTable = "";
	
	String SourceTableName = "";
	String sourcecolumnName = "";
	TreeMap srcColumnTM = new TreeMap<>();
	String joinColumnOper = "";
	TreeMap joinColumnOperTM = new TreeMap<>();
	String tarTableName = "";
	String targetColumnName = "";
	
	TreeMap joinsrcColumnTM = new TreeMap<>();
	public TreeMap getJoinsrcColumnTM() {
		return joinsrcColumnTM;
	}
	public void setJoinsrcColumnTM(TreeMap joinsrcColumnTM) {
		this.joinsrcColumnTM = joinsrcColumnTM;
	}
	
	TreeMap periodCols4srcTM = new TreeMap<>();
	public TreeMap getPeriodCols4srcTM() {
		return periodCols4srcTM;
	}

	public void setPeriodCols4srcTM(TreeMap periodCols4srcTM) {
		this.periodCols4srcTM = periodCols4srcTM;
	}

	String periodCol4src = "";
	public String getPeriodCol4src() {
		return periodCol4src;
	}

	public void setPeriodCol4src(String periodCol4src) {
		this.periodCol4src = periodCol4src;
	}
	
	public boolean matchCase4TableSelect = false;
	
	public boolean isMatchCase4TableSelect() {
		return matchCase4TableSelect;
	}

	public void setMatchCase4TableSelect(boolean matchCase4TableSelect) {
		this.matchCase4TableSelect = matchCase4TableSelect;
	}
	
	String selectedJoinType4UpdateIncr = "";
	public String getSelectedJoinType4UpdateIncr() {
		return selectedJoinType4UpdateIncr;
	}
	public void setSelectedJoinType4UpdateIncr(String selectedJoinType4UpdateIncr) {
		this.selectedJoinType4UpdateIncr = selectedJoinType4UpdateIncr;
	}

	String selectedJoinType4InsertIncr = "";
	

	public String getSelectedJoinType4InsertIncr() {
		return selectedJoinType4InsertIncr;
	}
	public void setSelectedJoinType4InsertIncr(String selectedJoinType4InsertIncr) {
		this.selectedJoinType4InsertIncr = selectedJoinType4InsertIncr;
	}
	public void getFactTables4Edit(String editFrom,String srcTableName1,String tarTableName1){
		System.out.println("Entering: "
				+ new Exception().getStackTrace()[0].getClassName() + "."
				+ new Exception().getStackTrace()[0].getMethodName());
		try{
			srcandTarFactTablesAl=new ArrayList<>();
//			srcandTarFactTablesSelectedAl=new ArrayList<>();
			Node measurecolumns = null;
			Node joincolumns = null;
			HeirarchyDataBean hedb; 
			selectedSrcTableTM = new TreeMap<>();
			TreeMap tempTM = new TreeMap<>();
			Connection con = null;
			Connection tarCon = null;
//			propertyTM.put("Attribute", "Attribute");
//			propertyTM.put("Measure", "Measure");
//			propertyTM.put("Period", "Period");
			
			
			incrementalTypeTM.put("Insert", "Insert");
			incrementalTypeTM.put("Update", "Update");
			tarTableName4FactJoin = "";
			
			joinColumnOperTM.put("=", "=");
			joinColumnOperTM.put("BETWEEN", "BETWEEN");
			joinColumnOperTM.put("<", "<");
			joinColumnOperTM.put(">", ">");
			joinColumnOperTM.put("!=", "!=");
			joinColumnOperTM.put("<=", "<=");
			joinColumnOperTM.put(">=", ">=");
			joinColumnOperTM.put("LIKE", "LIKE");
			joinColumnOperTM.put("IN", "IN");
			
			PropUtil prop=new PropUtil();
			String dir=prop.getProperty("HIERARCHY_XML_DIR");
			renderedDataPopup = "false";
			renderedDataPopup1 = "true";
			testConMsg = "";
			testConMsg1 = "";
			tartestConMsg = "";
			tartestConMsg1 = "";
			String srcTableName = "";
			String codeCombinationType = "";
			this.message = "";
			int factNo = 0;
			Node RootNode = null;
			Node hierarchyLevelNode = null;
			String segmentTrigerSqlTable = "";
			String segmentTrigerSqlColumn = "";
			Node xmlFactNode = null;
			FacesContext ctx = FacesContext.getCurrentInstance();
			ExternalContext extContext = ctx.getExternalContext();
			Map sessionMap = extContext.getSessionMap();
			HierarchyBean hdb = (HierarchyBean) sessionMap.get("hierarchyBean");
			
			
			
		Document xmldoc = Globals.openXMLFile(dir, hierarchyXMLFileName);
		
System.out.println("hdb.getHierarchy_ID()==>>"+hdb.getHierarchy_ID());		
		 RootNode = Globals.getNodeByAttrVal(xmldoc, "Fact_Tables", "ID", hdb.getHierarchy_ID());
		//Start code change Jayaramu 03MAY14
		 hierarchyLevelNode = Globals.getNodeByAttrVal(xmldoc, "Hierarchy_Level", "Hierarchy_ID", hdb.getHierarchy_ID());
		 if(hierarchyLevelNode != null){
			Element hierLevelNode =(Element)hierarchyLevelNode;
			 codeCombinationType = hierLevelNode.getAttribute("Code_Combination");
			 if(codeCombinationType.equals("CreateCodeCombinationDuringFact")){
			 NodeList segmentTrigerSql = hierarchyLevelNode.getChildNodes();
			 for(int i=0;i<segmentTrigerSql.getLength();i++){
				 if(segmentTrigerSql.item(i).getNodeType() == Node.ELEMENT_NODE){
					 if(segmentTrigerSql.item(i).getNodeName().equals("Segment_Trigger_SQL")){
						 NodeList segmentTrigerSqlTableNode = segmentTrigerSql.item(i).getChildNodes();
						 for(int j=0;j<segmentTrigerSqlTableNode.getLength();j++){
							 if(segmentTrigerSqlTableNode.item(j).getNodeType() == Node.ELEMENT_NODE){
								 if(segmentTrigerSqlTableNode.item(j).getNodeName().equals("TABLE_NAME")){
									 segmentTrigerSqlTable = segmentTrigerSqlTableNode.item(j).getTextContent();
								 }else if(segmentTrigerSqlTableNode.item(j).getNodeName().equals("COLUMN_NAME")){
									 segmentTrigerSqlColumn = segmentTrigerSqlTableNode.item(j).getTextContent();
								 }
								 
							 }
						 }
					 }
				 }
			 }}
			 
		 } //End code change Jayaramu 03MAY14
		String hierarchyConfigFileName=prop.getProperty("HIERARCHY_CONFIG_FILE");
		Document doc = Globals.openXMLFile(dir, hierarchyConfigFileName);
		
		//start code change Jayaramu 03MAY14
		if(codeCombinationType.equals("CreateCodeCombinationDuringFact")){
			joinClumnListLegend = "Choose Join Columns for Source Transaction & Hierarchy Dimension";
			if(segmentTrigerSqlTable.equals("") && segmentTrigerSqlColumn.equals("")){
				NodeList segTrigerSql = doc.getElementsByTagName("Segment_Trigger_SQL");
				Node sqlTrigerNode = segTrigerSql.item(0);
				if(sqlTrigerNode != null){
					 NodeList segmentTrigerSqlTableNode = sqlTrigerNode.getChildNodes();
					 for(int j=0;j<segmentTrigerSqlTableNode.getLength();j++){
						 if(segmentTrigerSqlTableNode.item(j).getNodeType() == Node.ELEMENT_NODE){
							 if(segmentTrigerSqlTableNode.item(j).getNodeName().equals("TABLE_NAME")){
								 segmentTrigerSqlTable = segmentTrigerSqlTableNode.item(j).getTextContent();
							 }else if(segmentTrigerSqlTableNode.item(j).getNodeName().equals("COLUMN_NAME")){
								 segmentTrigerSqlColumn = segmentTrigerSqlTableNode.item(j).getTextContent();
							 }
							 
						 }
					 }
				 }
				}
			}else{
				tarTableName4FactJoin = "";
				column4HierDimTM = new TreeMap<>();
				selectedColumn4HierDim = "";
				joinClumnListLegend = "Create Code Combination in Dimension is Checked, Hierarchy Dimension is loaded";
			}
		combinationType = codeCombinationType;
	//End code change Jayaramu 03MAY14
		int l=1;
		NodeList propertyList = doc.getElementsByTagName("Property");
		Node propertyNd = propertyList.item(0);
		NodeList propertyNdList = propertyNd.getChildNodes();
		for(int i=0;i<propertyNdList.getLength();i++){
			if(propertyNdList.item(i).getNodeType() == Node.ELEMENT_NODE){
				propertyTM.put(propertyNdList.item(i).getTextContent(), propertyNdList.item(i).getTextContent());
//				System.out.println("propertyTM==>>"+propertyTM);
				l++;
			}
		}
		
		NodeList connList = doc.getElementsByTagName("Connection");
		Node connNd = connList.item(0);
		NodeList connChdNdlList = connNd.getChildNodes();
		for(int i=0;i<connChdNdlList.getLength();i++){
			if(connChdNdlList.item(i).getNodeType() == Node.ELEMENT_NODE){
				connectionNameTM.put(connChdNdlList.item(i).getNodeName(), connChdNdlList.item(i).getNodeName());
			}
		}
		if(editFrom.equals("FromFactPopup")){
			selectedMeasureColumnsAL = new ArrayList<>();
			  selectedJoinColumnsAL = new ArrayList<>();
			  selectedPeriodValuesAL = new ArrayList<>();
			  srcAndWHJoinsAL = new ArrayList<>();
			  selectedTargetTableName = "null";
			  srcColumTM = new TreeMap<>();
			  selectedSrcTableTM = new TreeMap<>();
			  incrementalLoadAL = new ArrayList<>();
			  incrementalLoadUpdateAL = new ArrayList<>();
			  updateIncrementalLoadAL = new ArrayList<>();
			  msg4SrcTgtFact = "";
			  msg4JoinColumnList = "";
			  msg4IncrementLoad = "";
			  msg4SrcWarJoin = "";
			  msg4UpdateIncrementLoad = "";
			  msg4WarHouseLoad = "";
			  msg4UpdateIncrLoad = "";
//			  msg4SrcTgtFact = "";
			  if(RootNode == null){
				  NodeList factTableNode1 = doc.getElementsByTagName("Fact_Tables");
				  Node RootNode1 = factTableNode1.item(0);
//					xmlFactNode = factTableNode1.item(0);
				  connectionName = Globals.getAttrVal4AttrName(RootNode1, "Connection_Source");
					tarConnectionName = Globals.getAttrVal4AttrName(RootNode1, "Connection_Target");
					selectedFactType = Globals.getAttrVal4AttrName(RootNode1, "Gen_Mode");
			  }else{
				  connectionName = Globals.getAttrVal4AttrName(RootNode, "Connection_Source");
					tarConnectionName = Globals.getAttrVal4AttrName(RootNode, "Connection_Target");
					selectedFactType = Globals.getAttrVal4AttrName(RootNode, "Gen_Mode");
			  }
			  selectedConnectionTM.put(connectionName, connectionName);
			  selectedConnectionTM.put(tarConnectionName, tarConnectionName);
			  System.out.println("connectionName===>"+connectionName);
			  System.out.println("tarConnectionName===>"+tarConnectionName);
				con = Globals.getDBConnection(connectionName);
				tarCon = Globals.getDBConnection(tarConnectionName);
		NodeList targetTableNameNode = doc.getElementsByTagName("Target_Fact_Tables");
		
		NodeList dimTargetTableNameNode = doc.getElementsByTagName("Dim_Table_Name");
		
		if(targetTableNameNode != null){ //// get target factTables from config file
		Node targetTableNames = targetTableNameNode.item(0);
		if(targetTableNames != null){
		if(targetTableNames.getNodeType() == Node.ELEMENT_NODE){
			NodeList targetTablechild = targetTableNames.getChildNodes();
			for(int i=0;i<targetTablechild.getLength();i++){
				
				Node targetchildNode = targetTablechild.item(i);
				if(targetchildNode.getNodeType() == Node.ELEMENT_NODE){
					
					targetTableNameTM.put(targetchildNode.getTextContent().toString(),targetchildNode.getTextContent().toString());
				}
				
			}
		}}
		}
		if(dimTargetTableNameNode != null){//// get target dimTables from config file
			Node dimTargetTableNames = dimTargetTableNameNode.item(0);
			if(dimTargetTableNames != null){
			if(dimTargetTableNames.getNodeType() == Node.ELEMENT_NODE){
				this.joinTargetTableName = dimTargetTableNames.getTextContent().toString();
				Connection con1 = Globals.getDBConnection("Data_Connection");
				if(con1 != null)
					
					getTargetTableColumn(joinTargetTableName,"joinTable",con1);
			}
		}}
		
		System.out.println("hdb.RootNode()aaa==>>"+RootNode);	
		
	if(RootNode == null){
		
		NodeList factTableNode = doc.getElementsByTagName("Fact_Tables");
		RootNode = factTableNode.item(0);
		xmlFactNode = factTableNode.item(0);

		NodeList rootNdList = RootNode.getChildNodes();
		for(int i=0;i<rootNdList.getLength();i++){
			if(rootNdList.item(i).getNodeType() == Node.ELEMENT_NODE && rootNdList.item(i).getNodeName().equals("Table")){
				NodeList tableNdList = rootNdList.item(i).getChildNodes();
				for(int j=0;j<tableNdList.getLength();j++){
					if(tableNdList.item(j).getNodeType() == Node.ELEMENT_NODE && tableNdList.item(j).getNodeName().equals("Source_Warehouse_Joins")){
						NodeList srcWHList = tableNdList.item(j).getChildNodes();
						for(int k=0;k<srcWHList.getLength();k++){
							if(srcWHList.item(k).getNodeType() == Node.ELEMENT_NODE){
								String[] temp = Globals.getAttrVal4AttrName(srcWHList.item(k), "Source_Column").split("\\.");
								String[] temp1 = null;
								if(Globals.getAttrVal4AttrName(srcWHList.item(k), "Target_Column").contains(".")){
									temp1 = Globals.getAttrVal4AttrName(srcWHList.item(k), "Target_Column").split("\\.");
								}
								System.out.println("temp[0]====>>> "+temp[0]);
								if(!selectedSrcTableTM.containsValue(temp[0])){
									tempTM.put(temp[0], Globals.getAttrVal4AttrName(srcWHList.item(k), "Source_Connection"));
									selectedSrcTableTM.put(temp[0], temp[0]);
								}
								if(temp1!=null)
								if(!selectedSrcTableTM.containsValue(temp1[0])){
									tempTM.put(temp1[0], Globals.getAttrVal4AttrName(srcWHList.item(k), "Target_Connection"));
									selectedSrcTableTM.put(temp1[0], temp1[0]);
								}
							}
						}
					}
				}
				break;
			}
		}
	
	}else{
		NodeList rootNdList = RootNode.getChildNodes();
		for(int i=0;i<rootNdList.getLength();i++){
			if(rootNdList.item(i).getNodeType() == Node.ELEMENT_NODE && rootNdList.item(i).getNodeName().equals("Table")){
				NodeList tableNdList = rootNdList.item(i).getChildNodes();
				for(int j=0;j<tableNdList.getLength();j++){
					if(tableNdList.item(j).getNodeType() == Node.ELEMENT_NODE && tableNdList.item(j).getNodeName().equals("Source_Warehouse_Joins")){
						NodeList srcWHList = tableNdList.item(j).getChildNodes();
						for(int k=0;k<srcWHList.getLength();k++){
							if(srcWHList.item(k).getNodeType() == Node.ELEMENT_NODE){
								String[] temp = Globals.getAttrVal4AttrName(srcWHList.item(k), "Source_Column").split("\\.");
								String[] temp1 = null;
								if(Globals.getAttrVal4AttrName(srcWHList.item(k), "Target_Column").contains(".")){
									temp1 = Globals.getAttrVal4AttrName(srcWHList.item(k), "Target_Column").split("\\.");
								}
								System.out.println("temp[0]====>>> "+temp[0]);
								
								if(temp1!=null)
								if(!selectedSrcTableTM.containsValue(temp1[0])){
									tempTM.put(temp1[0], Globals.getAttrVal4AttrName(srcWHList.item(k), "Target_Connection"));
									selectedSrcTableTM.put(temp1[0], temp1[0]);
								}
								
								System.out.println("temp[0]====>>> "+temp[0]);
								if(!selectedSrcTableTM.containsValue(temp[0])){
									tempTM.put(temp[0], Globals.getAttrVal4AttrName(srcWHList.item(k), "Source_Connection"));
									selectedSrcTableTM.put(temp[0], temp[0]);
									}
							}
						}
					}
				}
				break;
			}
		}
	}
	
		}
		
		System.out.println("selTableName====>>> "+selTableName);
		System.out.println("tarSelTableName====>>> "+tarSelTableName);
		if(editFrom.equals("FromColumn")){
		NodeList rootNdList = RootNode.getChildNodes();
		for(int i=0;i<rootNdList.getLength();i++){
			if(rootNdList.item(i).getNodeType() == Node.ELEMENT_NODE && rootNdList.item(i).getNodeName().equals("Table")
					 && Globals.getAttrVal4AttrName(rootNdList.item(i), "Source_Fact_TableName").equals(selTableName)
					 && Globals.getAttrVal4AttrName(rootNdList.item(i), "Target_Fact_TableName").equals(tarSelTableName)){
				NodeList tableNdList = rootNdList.item(i).getChildNodes();
				for(int j=0;j<tableNdList.getLength();j++){
					if(tableNdList.item(j).getNodeType() == Node.ELEMENT_NODE && tableNdList.item(j).getNodeName().equals("Source_Warehouse_Joins")){
						NodeList srcWHList = tableNdList.item(j).getChildNodes();
						for(int k=0;k<srcWHList.getLength();k++){
							if(srcWHList.item(k).getNodeType() == Node.ELEMENT_NODE){
								String[] temp = Globals.getAttrVal4AttrName(srcWHList.item(k), "Source_Column").split("\\.");
								String[] temp1 = null;
								if(Globals.getAttrVal4AttrName(srcWHList.item(k), "Target_Column").contains(".")){
									temp1 = Globals.getAttrVal4AttrName(srcWHList.item(k), "Target_Column").split("\\.");
								}
								
								if(temp1!=null)
								if(!selectedSrcTableTM.containsValue(temp1[0])){
									tempTM.put(temp1[0], Globals.getAttrVal4AttrName(srcWHList.item(k), "Target_Connection"));
									selectedSrcTableTM.put(temp1[0], temp1[0]);
								}
								System.out.println("temp[1]====>>> "+temp[0]);
								if(!selectedSrcTableTM.containsValue(temp[0])){
									selectedSrcTableTM.put(temp[0], temp[0]);
									tempTM.put(temp[0], Globals.getAttrVal4AttrName(srcWHList.item(k), "Source_Connection"));
									System.out.println("FromColumn====>>> "+selectedSrcTableTM);
									}
							}
						}
					}
				}
//				break;
			}
		}
		con = Globals.getDBConnection(connectionName);
		tarCon = Globals.getDBConnection(tarConnectionName);
		}
		
		if(RootNode != null){
			
			if(RootNode.getNodeType() == Node.ELEMENT_NODE){
				NodeList tableNodes = RootNode.getChildNodes();
				
				
				
				
				for(int k=0;k<tableNodes.getLength();k++){
					
				
					if(tableNodes.item(k).getNodeName().equals("Table")){
						
						periodCols4srcTM = new TreeMap<>();
						srcTableName = tableNodes.item(k).getAttributes().getNamedItem("Source_Fact_TableName").getTextContent().toString();
						String tarTableName = tableNodes.item(k).getAttributes().getNamedItem("Target_Fact_TableName").getTextContent().toString();
						
						periodCols4srcTM = columnName1("FromEditFact",srcTableName,con);
						System.out.println("periodCols4srcTM===>"+periodCols4srcTM);
						HierarchydataBean hdatab=new HierarchydataBean(srcTableName, tarTableName,periodCols4srcTM,Globals.getAttrVal4AttrName(tableNodes.item(k), "Source_Period_Column"));
						srcandTarFactTablesAl.add(hdatab);
						
						if(editFrom.equals("FromFactPopup")){
							
							HierarchydataBean hyDataBean=(HierarchydataBean) srcandTarFactTablesAl.get(0);
							srcTableName1 = hyDataBean.getSrcFactTable();
							tarTableName1 = hyDataBean.getTarFactTables();
							TreeMap periodCols4srcTM1 = columnName1("FromEditFact",srcTableName1,con);
							System.out.println("periodCols4srcTM1===>"+periodCols4srcTM1);
							periodCol4src = hyDataBean.getPeriodCol4src();
							HierarchydataBean hyDataBean1=new HierarchydataBean(srcTableName1, tarTableName1,periodCols4srcTM1,periodCol4src);
							srcandTarFactTablesSelectedAl.add(srcandTarFactTablesAl.get(0));
						if(factNo == 0){
							
//							tableName = {srcTableName};
//							selTableName = tableName[0];
							selTableName = srcTableName1;
							tarSelTableName = tarTableName1;
							srcTable=srcTableName;
							srcTableTM.put(selTableName, selTableName);
							srcTableTM.put(tarSelTableName, tarSelTableName);
								HierarchydataBean hdb4 = (HierarchydataBean) srcandTarFactTablesAl.get(0);
								columns4IncreUpdateTM = columnName1("", hdb4.getSrcFactTable(), con);
								System.out.println("=========Coming==========="+columns4IncreUpdateTM);
								copyColumnNameTM4Join = columns4IncreUpdateTM;
							
							
						}
							System.out.println("srcTableName====>>> "+srcTableName);
							selectedTableNameTM.put(srcTableName, srcTableName);
							copySelectedTableNameTM.put(srcTableName, srcTableName);
							
//						}
							factNo++;
							
						}
						System.out.println("selTableName====>>> "+selTableName);
						System.out.println("srcTableName====>>> "+srcTableName);
						
						if(editFrom.equals("FromColumn")){
							HierarchydataBean hdatabean=(HierarchydataBean)srcandTarFactTablesSelectedAl.get(0);
							selTableName=hdatabean.getSrcFactTable();
							if(factNo == 0){
							columns4IncreUpdateTM = columnName1("", hdatabean.getSrcFactTable(), con);
							copyColumnNameTM4Join = (TreeMap)columns4IncreUpdateTM.clone();
							factNo++;
							}
						}else{
//							HierarchydataBean hdb4 = (HierarchydataBean) srcandTarFactTablesAl.get(0);
//							columns4IncreUpdateTM1 = columnName1("", hdb4.getSrcFactTable(), con);
//							copyColumnNameTM4Join = columns4IncreUpdateTM;
						
						}
						
						NodeList child = tableNodes.item(k).getChildNodes();
					if(srcTableName1.equals(srcTableName) && tarTableName.equals(tarTableName1)){
					for(int i=0;i<child.getLength();i++){
						
					if(srcTableName.equals(selTableName)){
						
						columnNameTM = columnName1("FromEditFact",selTableName,con);
						
						
					if(child.item(i).getNodeName().equals("Measure_Columns")){ 
						
						measurecolumns = child.item(i);
						NodeList measure  = measurecolumns.getChildNodes();
						if(editFrom.equals("FromColumn")){
						HierarchydataBean hdb1=(HierarchydataBean)srcandTarFactTablesSelectedAl.get(0);
						if(!selectedSrcTableTM.containsValue(hdb1.getSrcFactTable())){
							tempTM.put(hdb1.getSrcFactTable(), connectionName);
							selectedSrcTableTM.put(hdb1.getSrcFactTable(), hdb1.getSrcFactTable());
						}
						}else if(editFrom.equals("FromFactPopup")){
							HierarchydataBean hdb1=(HierarchydataBean)srcandTarFactTablesAl.get(0);
							if(!selectedSrcTableTM.containsValue(hdb1.getSrcFactTable())){
								tempTM.put(hdb1.getSrcFactTable(), connectionName);
								selectedSrcTableTM.put(hdb1.getSrcFactTable(), hdb1.getSrcFactTable());
							}
						}
						System.out.println("tempTM===> "+tempTM);
						for(int n=0;n<measure.getLength();n++){
							
							Node measureChildColumn = measure.item(n);
							if(measureChildColumn.getNodeType() == Node.ELEMENT_NODE){
								String measureAttr = measureChildColumn.getAttributes().getNamedItem("Column_Property").getTextContent().toString();
								String separateSrcTgt[] = measureChildColumn.getTextContent().toString().split("=");
								
								if(separateSrcTgt==null){
									continue;
								}
								System.out.println("separateSrcTgt00==>> "+separateSrcTgt[0].split(".")+"separateSrcTgt111"+separateSrcTgt[1].split("."));
								
								String separateTgtTableColumn[] = separateSrcTgt[0].split("\\.");
								String separateSrcTableColumn[] = separateSrcTgt[1].split("\\.");
								
								System.out.println("separateSrcTableColumn[1]==>>>"+separateSrcTableColumn[1]);
								System.out.println("columnNameTM==>>>"+columnNameTM);
								
								this.selectedTargetTableName = separateTgtTableColumn[0];
								columnNameTM.remove(separateSrcTableColumn[1]);
								copyColumnNameTM.remove(separateSrcTableColumn[1]);
								System.out.println("separateTgtTableColumn000==>>"+separateTgtTableColumn[0]+"separateTgtTableColumn11==>>>"+separateTgtTableColumn[1]);
								
//								getTargetTableColumn(separateTgtTableColumn[0], "measureTable",con);
								String property="";
//								if(measureAttr.equals("true")){
//									property = "Measure";
//								}else if(measureAttr.equals("false")){
//									property = "Attribute";
//								}
								System.out.println("separateSrcTableColumn[0]==>>>"+String.valueOf(tempTM.get(separateSrcTableColumn[0])));
								if(String.valueOf(tempTM.get(separateSrcTableColumn[0])).equals("null")){
									continue;
								}
								Connection con1 = Globals.getDBConnection(String.valueOf(tempTM.get(separateSrcTableColumn[0]))); 
								srcColumTM = columnName1("", separateSrcTableColumn[0], con1);
								TargetColumn = columnName1("", selectedTargetTableName, tarCon);
								System.out.println("srcColumTM==>>>"+srcColumTM);
								
								String additionalFunc4Src = Globals.getAttrVal4AttrName(measureChildColumn, "Source_Column_Function");
								String additionalFunc4Tar = Globals.getAttrVal4AttrName(measureChildColumn, "Target_Column_Function");
								System.out.println("additionalFunc4Src==>>>"+additionalFunc4Src);
								System.out.println("additionalFunc4Tar==>>>"+additionalFunc4Tar);
								String measureimagesrc = "";
								String measureimagetar = "";
								if(additionalFunc4Src.equals("") || additionalFunc4Src.equals(separateSrcTableColumn[0]+"."+separateSrcTableColumn[1])){
									measureimagesrc = "choseTablecopy.png";
								}else{
									measureimagesrc = "chooseTablecopy.png";
								}
								if(additionalFunc4Tar.equals("") || additionalFunc4Tar.equals(tarTableName1+"."+separateTgtTableColumn[1])){
									measureimagetar = "choseTablecopy.png";
								}else{
									measureimagetar = "chooseTablecopy.png";
								}
								System.out.println("measureimagesrc==>>>"+tarTableName1);
								System.out.println("measureimagetar==>>>"+measureimagetar);
								hedb = new HeirarchyDataBean(separateSrcTableColumn[1],targetColumNames,false,separateTgtTableColumn[1],separateSrcTableColumn[0],separateTgtTableColumn[0],
										srcColumTM,propertyTM,measureAttr,separateSrcTableColumn[0],selectedSrcTableTM,separateTgtTableColumn[1],
										TargetColumn,additionalFunc4Src,additionalFunc4Tar,measureimagesrc,measureimagetar);
								selectedMeasureColumnsAL.add(hedb);
						}
					
						}
						
						
						System.out.println("child.item(i)====>>> "+child.item(i).getTextContent().toString());						
					}else if(child.item(i).getNodeName().equals("Join_Columns")){
						
						selectedColumn4HierDim = Globals.getAttrVal4AttrName(child.item(i), "Dim_Code_Column");
						joincolumns = child.item(i);
						NodeList join  = joincolumns.getChildNodes();
						String tarColumnName = "";
						for(int n=0;n<join.getLength();n++){
							
							Node joinChildColumn = join.item(n);
							if(joinChildColumn.getNodeType() == Node.ELEMENT_NODE){
								
								String separateSrcTgt[] = joinChildColumn.getTextContent().toString().split("=");
								System.out.println("join separateSrcTgt00==>>"+separateSrcTgt[0]+"join separateSrcTgt111"+separateSrcTgt[1]);
								String separateTgtTableColumn[] = separateSrcTgt[0].split("\\.");
								String separateSrcTableColumn[] = separateSrcTgt[1].split("\\.");
								System.out.println("separateSrcTableColumn join[1]==>>>"+separateSrcTableColumn[1]);
								System.out.println("columnNameTM4Join==>>>"+columnNameTM4Join);
								String aa= separateSrcTableColumn[1];
								columnNameTM4Join.remove(aa);
								copyColumnNameTM4Join.remove(separateSrcTableColumn[1]);
								//start code change Jayaramu 03MAY14
								if(codeCombinationType.equals("CreateCodeCombinationDuringFact")){
									if(!segmentTrigerSqlTable.equals("")){
										tarTableName4FactJoin = "("+segmentTrigerSqlTable+")";
										System.out.println("Segment_Trigger_SQL Table Name--------->>>"+segmentTrigerSqlTable);
										tarColumnName = segmentTrigerSqlColumn;
										Connection con1 = Globals.getDBConnection("Data_Connection");
									getTargetTableColumn(segmentTrigerSqlTable, "joinTable",con1);
									column4HierDimTM = new TreeMap<>();
									column4HierDimTM = columnName1("", "WC_FLEX_HIERARCHY_D", tarCon);
									renderedTargDim  = true;
									}else{
										System.out.println("************There is no Segment_Trigger_SQL*************");
									}
								}else{
									Connection con1 = Globals.getDBConnection("DW_Connection");
									getTargetTableColumn(separateTgtTableColumn[0], "joinTable",con1);
									tarColumnName = separateTgtTableColumn[1];
									renderedTargDim = false;
								} //End code change Jayaramu 05MAY14
								joinsrcColumnTM = columnName1("", separateSrcTableColumn[0], con);
								System.out.println("join separateTgtTableColumn000==>>"+separateTgtTableColumn[0]+"join separateTgtTableColumn11==>>>"+separateTgtTableColumn[1]);
								hedb = new HeirarchyDataBean(separateSrcTableColumn[1],joinTargetColumNamesAL,tarColumnName,separateSrcTableColumn[0],separateTgtTableColumn[0],joinsrcColumnTM);
								selectedJoinColumnsAL.add(hedb);
						}
					
						}
					
					}else if(child.item(i).getNodeName().equals("Source_Warehouse_Joins")){
						Node srcCon = child.item(i);
						NodeList srcConList  = srcCon.getChildNodes();
						for(int m=0;m<srcConList.getLength();m++){
							Node srcWH = srcConList.item(m);
							HeirarchyDataBean hdb1;
							if(srcWH.getNodeType() == Node.ELEMENT_NODE){
								String[] srcColandTableName = Globals.getAttrVal4AttrName(srcWH, "Source_Column").split("\\.");
								String[] tarColandTableName = Globals.getAttrVal4AttrName(srcWH, "Target_Column").split("\\.");
								String targetcol = Globals.getAttrVal4AttrName(srcWH, "Target_Column");
								if(srcColandTableName!=null && tarColandTableName!=null){
									if(Globals.getAttrVal4AttrName(srcWH, "Source_Connection").equals("")){
										continue;
									}
									Connection con1 = Globals.getDBConnection(Globals.getAttrVal4AttrName(srcWH, "Source_Connection"));
									
									srcColumnTM = columnName1("", srcColandTableName[0], con1);
								
									String subSourceTableName=srcColandTableName[0];  // code change Menaka 03APR2014
									if(subSourceTableName.length()>15){
										subSourceTableName=subSourceTableName.substring(0,14)+"...";
									}
									if(!srcTableTM.containsValue(srcColandTableName[0])){
										srcTableTM.put(srcColandTableName[0], srcColandTableName[0]);	
									}
									String additionalFunc4Src = Globals.getAttrVal4AttrName(srcWH, "Source_Column_Function");
									String additionalFunc4Tar = Globals.getAttrVal4AttrName(srcWH, "Target_Column_Function");
									if(Globals.getAttrVal4AttrName(srcWH, "Join_type").equals("Column")){
										String measureimagesrc = "";
										String measureimagetar = "";
										if(additionalFunc4Src.equals("") || additionalFunc4Src.equals(srcColandTableName[0]+"."+srcColandTableName[1])){
											measureimagesrc = "choseTablecopy.png";
										}else{
											measureimagesrc = "chooseTablecopy.png";
										}
										if(additionalFunc4Tar.equals("") || additionalFunc4Tar.equals(tarColandTableName[0]+"."+tarColandTableName[1])){
											measureimagetar = "choseTablecopy.png";
										}else{
											measureimagetar = "chooseTablecopy.png";
										}
										hdb1 =new HeirarchyDataBean(srcColandTableName[0],srcColandTableName[1],Globals.getAttrVal4AttrName(srcWH, "Join_Operator"), joinColumnOperTM, 
												tarColandTableName[0], tarColandTableName[1], srcColumnTM,Globals.getAttrVal4AttrName(srcWH, "Source_Connection"),
												Globals.getAttrVal4AttrName(srcWH, "Target_Connection"),subSourceTableName,srcTableTM,additionalFunc4Src,additionalFunc4Tar,measureimagesrc,measureimagetar);
										srcAndWHJoinsAL.add(hdb1);
									}else{
										if(Globals.getAttrVal4AttrName(srcWH, "Join_Operator").equals("Between")){
											hdb1 =new HeirarchyDataBean(srcColandTableName[0],srcColandTableName[1],Globals.getAttrVal4AttrName(srcWH, "Join_Operator"), joinColumnOperTM, 
													targetcol.split("#-#")[0], targetcol.split("#-#")[1], srcColumnTM,Globals.getAttrVal4AttrName(srcWH, "Source_Connection"),
													Globals.getAttrVal4AttrName(srcWH, "Target_Connection"),subSourceTableName,srcTableTM,additionalFunc4Src,additionalFunc4Tar,"choseTablecopy.png","choseTablecopy.png");
											srcAndWHJoinsAL.add(hdb1);
										}else{
											hdb1 =new HeirarchyDataBean(srcColandTableName[0],srcColandTableName[1],Globals.getAttrVal4AttrName(srcWH, "Join_Operator"), joinColumnOperTM, 
													targetcol, "", srcColumnTM,Globals.getAttrVal4AttrName(srcWH, "Source_Connection"),Globals.getAttrVal4AttrName(srcWH, "Target_Connection"),
													subSourceTableName,srcTableTM,additionalFunc4Src,additionalFunc4Tar,"choseTablecopy.png","choseTablecopy.png");
											srcAndWHJoinsAL.add(hdb1);
										}
									}
								
								
								}
								
							}
						}
//						System.out.println("child.item(i)====>>>"+child.item(i).getTextContent().toString());
//						periodcolumn =  child.item(i).getAttributes().getNamedItem("Column_Name").getNodeValue();
//						String srcTable = child.item(i).getTextContent().split("\\.")[0];
//						hedb = new HeirarchyDataBean(periodcolumn,targetColumNames,false,"",periodcolumn,"",srcColumTM,propertyTM,"Period",selectedSrcTable,selectedSrcTableTM,selectedTargetColumn,TargetColumn);
//						selectedMeasureColumnsAL.add(hedb);
						System.out.println("periodcolumn====>>>1"+periodcolumn);			
					}else if(child.item(i).getNodeName().equals("Incremental_Load")){
						selectedJoinType4InsertIncr = Globals.getAttrVal4AttrName(child.item(i), "Join_Type4Insert");
						selectedJoinType4UpdateIncr = Globals.getAttrVal4AttrName(child.item(i), "Join_Type4Update");
						Node incrNd = child.item(i);
						NodeList incrNdList  = incrNd.getChildNodes();
						HeirarchyDataBean hdb1;
						for(int n=0;n<incrNdList.getLength();n++){
							if(incrNdList.item(n).getNodeType() == Node.ELEMENT_NODE){
								String increCol = incrNdList.item(n).getTextContent();
								String tableName = increCol.split("\\.")[0];
								String columnName = increCol.split("\\.")[1];
								String incrementType = Globals.getAttrVal4AttrName(incrNdList.item(n), "Type");
								if(incrementType.equalsIgnoreCase("Insert")){
								TreeMap srcColTM = columnName1("", tableName, con);
								hdb1 = new HeirarchyDataBean(columnName, srcColTM, incrementType, incrementalTypeTM);
								incrementalLoadAL.add(hdb1);
								}else{
									TreeMap srcColTM = columnName1("", tableName, con);
									hdb1 = new HeirarchyDataBean(columnName, srcColTM);
									incrementalLoadUpdateAL.add(hdb1);
								}
							}
						}
					}else if(child.item(i).getNodeName().equals("Incremental_Update")){
						
//						selectedJoinType4UpdateIncr = Globals.getAttrVal4AttrName(child.item(i), "Join_Type");
						Node incrNd = child.item(i);
						NodeList incrNdList  = incrNd.getChildNodes();
						HeirarchyDataBean hdb1;
						int j=0;
						HeirarchyDataBean hdb2;
						for(int n=0;n<incrNdList.getLength();n++){
							if(incrNdList.item(n).getNodeType() == Node.ELEMENT_NODE){
								String increColSrc = incrNdList.item(n).getTextContent().split("=")[1];
								String tableName = increColSrc.split("\\.")[0];
								String columnName = increColSrc.split("\\.")[1];
								
								String increColTar = incrNdList.item(n).getTextContent().split("=")[0];
								String targTableName = increColTar.split("\\.")[0];
								String tarColumnName = increColTar.split("\\.")[1];
								TreeMap srcColTM = new TreeMap<>();
								srcColTM = columnName1("", tableName, con);
								
								TreeMap tarColTM = new TreeMap<>();
								tarColTM  = columnName1("", targTableName, tarCon);
								hdb2 = new HeirarchyDataBean(columnName, srcColTM,tarColTM,tarColumnName);
								updateIncrementalLoadAL.add(hdb2);
								j++;
							}
						}

					}else if(child.item(i).getNodeName().equals("Period_Values")){
						System.out.println("child.item(i)====>>>1"+child.item(i).getTextContent().toString());
//						selectedPeriodValues = child.item(i).getTextContent().toString();;
						
					
						// code change Menaka 14FEB2014
						String value=child.item(i).getTextContent().toString();
						String str[]=value.split(";");
						
						for(int j=0;j<str.length;j++){
							
							if(str[j] != null && !str[j].equals(" ") && !str[j].equals("")){
							selectedPeriodValuesAL.add(str[j]);
							copySelectedPeriodValuesAL.add(str[j]);
							periodValuesAL.remove(str[j]);
							copyPeriodValuesAL.remove(str[j]);
							}
						}
						
					System.out.println("selectedPeriodValues---->>> "+selectedPeriodValues);
					}	 
					}
					}
					}
				}
				}
			
				}
			
		}
		
		if(editFrom.equals("FromFactPopup") && xmlFactNode != null){
			
//			writeFactTablesToXml("AddFactSourceTargetTables");
		}
//		for(int i=0;i<selectedInccolumns4IncreUpdateTM.size();i++){
//			columns4IncreUpdateTM1.remove(selectedInccolumns4IncreUpdateTM.get());
//		}
//		System.out.println("columns4IncreUpdateTM1---->>> "+columns4IncreUpdateTM1);
		
		}catch(Exception e){
			e.printStackTrace();
			msg4SrcTgtFact = Globals.getErrorString(e); //code change Jayaramu 25APR14
			color4SrcFactMsg = "red"; 
		}
		System.out.println("Exiting: "
				+ new Exception().getStackTrace()[0].getClassName() + "."
				+ new Exception().getStackTrace()[0].getMethodName());
	}
	
	String color4SrcFactMsg = "";
	
	public String getColor4SrcFactMsg() {
		return color4SrcFactMsg;
	}
	public void setColor4SrcFactMsg(String color4SrcFactMsg) {
		this.color4SrcFactMsg = color4SrcFactMsg;
	}

	String tarselectedOperator="";
	public String getTarselectedOperator() {
		return tarselectedOperator;
	}

	public void setTarselectedOperator(String tarselectedOperator) {
		this.tarselectedOperator = tarselectedOperator;
	}


	String tarfilterValue="";
	public String getTarfilterValue() {
		return tarfilterValue;
	}

	public void setTarfilterValue(String tarfilterValue) {
		this.tarfilterValue = tarfilterValue;
	}
	public TreeMap tartableNameTM=new TreeMap<>();
	

	public TreeMap getTartableNameTM() {
		return tartableNameTM;
	}

	public void setTartableNameTM(TreeMap tartableNameTM) {
		this.tartableNameTM = tartableNameTM;
	}

	
	public void filteringTargetTable(String selectedOperator,String filterValue){
		try{

			
			if(selectedOperator == null || selectedOperator.equals("") || selectedOperator.equals("null")){
				
				this.message = "Please select operator to filter Fact Table(s).";
				color4FactMsg = "red";
				return;
			}else if(copyTableNameTM == null || copyTableNameTM.isEmpty()){
				this.message = "No data(s) to Filter.";
				color4FactMsg = "red";
				return;
			}
			
	if(selectedOperator.equals("Contains")){
	
		
		tartableNameTM.putAll(copyTableNameTM);
		Set set = tartableNameTM.entrySet();
		tartableNameTM = new TreeMap<>();
		// Get an iterator 
		Iterator i = set.iterator(); 
		// Filter elements 
		System.out.println("filterValue====>>>>"+filterValue); 
		while(i.hasNext()) { 
		Map.Entry me = (Map.Entry)i.next(); 
		System.out.print(me.getKey() + ": "); 
		System.out.println(me.getValue()); 
		String contains = (String) me.getValue();
		if(matchCase4TableSelect){
//			filterValue = filterValue;
		}else{
			filterValue = filterValue.toUpperCase();
			contains = contains.toUpperCase();
		}
		if(contains.contains(filterValue)){
			tartableNameTM.put(contains, contains);
		}
		
		} 
	}
	
	if(selectedOperator.equals("Starts With")){
		
	
		tartableNameTM.putAll(copyTableNameTM);
		Set set = tartableNameTM.entrySet(); 
		tartableNameTM = new TreeMap<>();
		// Get an iterator 
		Iterator i = set.iterator(); 
		// Filter elements 
		System.out.println("filterValue====>>>>"+filterValue+"selectedOperator-====>>>"+selectedOperator); 
		
		while(i.hasNext()) { 
		Map.Entry me = (Map.Entry)i.next(); 
		System.out.print(me.getKey() + ": "); 
		System.out.println(me.getValue()); 
		String startswith = (String) me.getValue();
		
		System.out.println("startswith===>>>"+startswith); 
		if(matchCase4TableSelect){
//			filterValue = filterValue;
		}else{
			filterValue = filterValue.toUpperCase();
			startswith = startswith.toUpperCase();
		}
		if(startswith.startsWith(filterValue)){
			
			System.out.println("startswith filterValue===>>>"+startswith); 
			tartableNameTM.put(startswith, startswith);
		} 
	}

	}
	
	if(selectedOperator.equals("Ends With")){
	

		tartableNameTM.putAll(copyTableNameTM);
		Set set = tartableNameTM.entrySet();
		tartableNameTM = new TreeMap<>();
		// Get an iterator 
		Iterator i = set.iterator(); 
		// Display elements 
		System.out.println("filterValue====>>>>"+filterValue); 
		while(i.hasNext()) { 
		Map.Entry me = (Map.Entry)i.next(); 
		System.out.print(me.getKey() + ": "); 
		System.out.println(me.getValue()); 
		String endswith = (String) me.getValue();
		if(matchCase4TableSelect){
//			filterValue = filterValue;
		}else{
			filterValue = filterValue.toUpperCase();
			endswith = endswith.toUpperCase();
		}
		if(endswith.endsWith(filterValue)){
			
			tartableNameTM.put(endswith, endswith);
		}
		
		}
		} 
	
	if(selectedOperator.equals("All Values")){
		
		tartableNameTM = new TreeMap<>();
		tartableNameTM = copyTableNameTM;
		
	}
	
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	String srctartableMessage="";
	public String getSrctartableMessage() {
		return srctartableMessage;
	}

	public void setSrctartableMessage(String srctartableMessage) {
		this.srctartableMessage = srctartableMessage;
	}

	public void setselectedFactTables(){
		try{
			if(selectedTableName.length <= 0){
				srctartableMessage = "Please select source table to proceed.";
				return;
			}
			if(tarSelectedTableName.length <= 0){
				srctartableMessage = "Please select target table to proceed.";
				return;
			}
			
			HierarchydataBean hyDB;
			int tablesize=srcandTarFactTablesAl.size();
			Connection con = Globals.getDBConnection(connectionName);
			
			periodCols4srcTM = columnName1("", selectedTableName[0], con);
			
					
			hyDB = new HierarchydataBean(selectedTableName[0], tarSelectedTableName[0],periodCols4srcTM,"");
			srcandTarFactTablesAl.add(hyDB);
			selectedMeasureColumnsAL = new ArrayList<>();
			srcandTarFactTablesSelectedAl = new ArrayList<>();
			srcandTarFactTablesSelectedAl.add(hyDB);
			selectedJoinColumnsAL = new ArrayList<>();
			selectedPeriodValuesAL = new ArrayList<>();
			selectedJoinColumnsAL = new ArrayList<>();
			incrementalLoadAL = new ArrayList<>();
			incrementalLoadUpdateAL = new ArrayList<>();
			updateIncrementalLoadAL = new ArrayList<>();
			srcColumTM = new TreeMap<>();
			selTableName = selectedTableName[0];
			tarSelTableName = tarSelectedTableName[0];
//			writeFactTablesToXml("AddFactSourceTargetTables","");
			tableNameTM = new TreeMap<>();
			tartableNameTM = new TreeMap<>();
			getColumnname4SourceFactTable("FactSourceTargetDataTable");
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	String selectedConnection = "";
	public String getSelectedConnection() {
		return selectedConnection;
	}

	public void setSelectedConnection(String selectedConnection) {
		this.selectedConnection = selectedConnection;
	}
	TreeMap selectedConnectionTM = new TreeMap<>();

	public TreeMap getSelectedConnectionTM() {
		return selectedConnectionTM;
	}

	public void setSelectedConnectionTM(TreeMap selectedConnectionTM) {
		this.selectedConnectionTM = selectedConnectionTM;
	}
	
	public void chooseTable() {
		try{
			
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	public String selectedType = "";
	public String selectedType4ExistingTable = "";
	public String getSelectedType4ExistingTable() {
		return selectedType4ExistingTable;
	}
	public void setSelectedType4ExistingTable(String selectedType4ExistingTable) {
		this.selectedType4ExistingTable = selectedType4ExistingTable;
	}
	public String getSelectedType() {
		return selectedType;
	}

	public void setSelectedType(String selectedType) {
		this.selectedType = selectedType;
	}
	String renderFlag4JoinCol = "true";

	public String getRenderFlag4JoinCol() {
		return renderFlag4JoinCol;
	}

	public void setRenderFlag4JoinCol(String renderFlag4JoinCol) {
		this.renderFlag4JoinCol = renderFlag4JoinCol;
	}
	
	TreeMap tableNameTM1 = new TreeMap<>();
	
	public TreeMap getTableNameTM1() {
		return tableNameTM1;
	}

	public void setTableNameTM1(TreeMap tableNameTM1) {
		this.tableNameTM1 = tableNameTM1;
	}
	String modeType = "";
	public String getModeType() {
		return modeType;
	}
	public void setModeType(String modeType) {
		this.modeType = modeType;
	}
	public void gettingTableName(String flagType){
		try{
				valiMsg = "";
//			#{hierarchyDBBean.gettingTableName('factPopup')}
			tableWidth4Horizontal = "width:185px";//code change Jayaramu 21APR24
			if(flagType.equals("factPopup")){
				System.out.println("connectionName===>"+connectionName);
				System.out.println("tarConnectionName===>"+tarConnectionName);
				srcAndWHJoinsAL=  new ArrayList<>();
			Connection con = Globals.getDBConnection(connectionName);
//			tableNameTM=new TreeMap<>();
			tableNameTM = getTableName1("Adminpopup",con);
			
			Connection tarCon = Globals.getDBConnection(tarConnectionName);
//			tartableNameTM=new TreeMap<>();
			tartableNameTM = getTableName1("Adminpopup",tarCon);
			}else if(flagType.equals("selectTable")){
				System.out.println("selectedcondition===>"+selectedConnection);
				
				Connection con = Globals.getDBConnection(selectedConnection);
				tableNameTM1 = getTableName1("Adminpopup",con);
			}else if(flagType.equals("dataPopup")){ //code change Jayaramu 05APR14
				codeandNameAL = new ArrayList<>();
				FacesContext cxt = FacesContext.getCurrentInstance();
				ExternalContext extContext = cxt.getExternalContext();
				Map sessionmap = extContext.getSessionMap();
				HierarchyBean hb = (HierarchyBean) sessionmap.get("hierarchyBean");
				
				
//				
//				System.out.println("View Scope=====>>>>"+viewScopedBean.getHierarchyXmlFileName());
				
				if(hb!=null){
					if(hb.getSelectedRows().size()<=0){
						hb.valid = false;
						hb.msg1 = "Please select a Node to proceed further.";
						hb.color4HierTreeMsg = "red";
						return;
					}
				}
				hb.valid = true;
				if(hb.codecombinationFlag.equalsIgnoreCase("DontCreateCodeCombination")){
					hb.disableOption4hier = true;
					hb.setCodeCombinationflag4Lastnode("");
				}else if(hb.codecombinationFlag.equalsIgnoreCase("CreateCodeCombinationInDim")){
					hb.disableOption4hier = false;
					hb.setCodeCombinationflag4Lastnode("CreateCodeCombinationAtLeaf");
				}else{
					hb.disableOption4hier = true;
					hb.setCodeCombinationflag4Lastnode("DuringFactGen");
				}
				
				System.out.println("selectedcondition===>"+selectedConnection);
				Hashtable connectionDetailsHT = getconnectionDetails("Data_Connection");
				server = (String)connectionDetailsHT.get("HostName");
		    	port = (String)connectionDetailsHT.get("PortName");
		    	instance = (String)connectionDetailsHT.get("DBName");
		    	user = (String)connectionDetailsHT.get("UserName");
		    	System.out.println("server===>"+server);
		    	System.out.println("port===>"+port);
		    	System.out.println("instance===>"+instance);
		    	System.out.println("user===>"+user);
				Connection con = Globals.getDBConnection("Data_Connection");
				tableNameTM = getTableName1("Adminpopup",con);
				
				///////want to change//////
				whereClauseList = new ArrayList<>();
				selectedTableNameTM4DirectSQL = new TreeMap<>();
				selectedColumnNameTM4DirectSQL = new TreeMap<>();
				columnNameTM4DirectSQL.clear();
				directSQL = "";
				modeType = "AddMode";
				enable4DirectSQL = false;
//				flag4AddData = "";
				////////////
				codeandNameAL = new ArrayList<>();
				columnNameTM = new TreeMap<>();
						
			}else{
				Connection con = Globals.getDBConnection("DW_Connection");
				tableNameTM1 = getTableName1("Adminpopup",con);
			}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	TreeMap columnNameTM1 = new TreeMap<>();
	public TreeMap getColumnNameTM1() {
		return columnNameTM1;
	}

	public void setColumnNameTM1(TreeMap columnNameTM1) {
		this.columnNameTM1 = columnNameTM1;
	}

	public void selectedType4Column(String type){
		try{
//			columnNameTM1 = new TreeMap<>();
			renderFlag4JoinCol = "false";
//			HeirarchyDataBean hdb = (HeirarchyDataBean)selectedsrcAndWHJoinsAL.get(0);
			System.out.println("type===>"+type);
//			System.out.println("hdb.getSourceTable()===>"+hdb.getSourceTable());
			selectedType = type;
//			if(!hdb.getTarConn().equals("")){
////			Connection con = Globals.getDBConnection(hdb.getTarConn());
////			columnNameTM1 = columnName1("", hdb.tarTableName, con);
//			}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public void selectedType4Table(String selectedType,String tableName) {
		System.out.println("Entering: "
				+ new Exception().getStackTrace()[0].getClassName() + "."
				+ new Exception().getStackTrace()[0].getMethodName());
		try{
			this.selectedType = selectedType;
			if(selectedType.equals("source")){
				selectedType4ExistingTable = "ExistingTableSource";
			}else if(selectedType.equals("target")){
				selectedType4ExistingTable = "ExistingTableTarget";
			}else if(selectedType.equals("DirectSQL")){
				selectedType4ExistingTable = "DirectSQLExistingTable";
			}
			
			existingTableMessage = "";
			System.out.println("selectedType===>"+selectedType);
//			tableNameTM = new TreeMap<>();
			if(selectedType.equalsIgnoreCase("ChoosingColumn")){
				
					
			}else if(selectedType.equalsIgnoreCase("ChoosingMeasureColumn")){
				msg4WarHouseLoad = "";
				System.out.println("srcColumTM11===>"+selectedMeasureColumnsAL.size());
				String selectedSrcTable = "";
				String sourcecolumnName = "";
				TreeMap selectedSrcTableTM = new TreeMap<>();
				String selectedTargetColumn = "";
				TreeMap targetColumn = new TreeMap<>();
				String property = "";
				TreeMap propertyTM = new TreeMap<>();
				renderFlag4JoinCol = "true";
				String connName = "";
//				if(selectedMeasureList.size()==0){
//					return;
//				}
//				String tableName1 = "";
				HeirarchyDataBean hdb = (HeirarchyDataBean)selectedMeasureList.get(0);
				
				TreeMap srcColumTM = new TreeMap<>();
				System.out.println("hdb.selectedSrcTable===>"+hdb.selectedSrcTable);
				System.out.println("selectedMeasureList===>"+selectedMeasureList);
				for(int i=0;i<srcAndWHJoinsAL.size();i++){
					HeirarchyDataBean hdb1 = (HeirarchyDataBean) srcAndWHJoinsAL.get(i);
					System.out.println("hdb1.getSourceTableName()===>"+tableName);
					System.out.println("hdb1.getSrcConn()===>"+hdb1.getSrcConn());
				if(hdb1.getSourceTableName().equals(tableName)){
					connName = hdb1.getSrcConn();
//					tableName1 = hdb.selectedSrcTable;
					Connection con = Globals.getDBConnection(connName);
					srcColumTM = columnName1("", tableName, con);
					
					break;
				}else if(hdb1.tarTableName.equals(tableName)){
					connName = hdb1.getTarConn();
//					tableName1 = hdb.selectedSrcTable;
					Connection con = Globals.getDBConnection(connName);
					srcColumTM = columnName1("", tableName, con);
				}else{
					connName = connectionName;
					Connection con = Globals.getDBConnection(connName);
					srcColumTM = columnName1("", tableName, con);
				}
				}
				if(srcAndWHJoinsAL.size()==0){
					
						connName = connectionName;
						Connection con = Globals.getDBConnection(connName);
						srcColumTM = columnName1("", tableName, con);
					
				}
				System.out.println("hdb.getTarConn()===>"+connName);
//				System.out.println("hdb.getTarConn()===>"+connName);
				System.out.println("srcColumTM===>"+srcColumTM);
				selectedSrcTable = tableName;
				selectedSrcTableTM = hdb.selectedSrcTableTM;
//				selectedTargetColumn = 
				selectedTargetColumn = hdb.selectedTargetColumn;
				sourcecolumnName = hdb.sourceTable;
				property = hdb.property;
				propertyTM = hdb.propertyTM;
				targetColumn = hdb.TargetColumn;
				String additionalFunction4SrcColumn = "";
				String additionalFunction4TarColumn = "";
				additionalFunction4SrcColumn = hdb.additionalFunction4SrcColumn;
				additionalFunction4TarColumn = hdb.additionalFunction4TarColumn;
				selectedMeasureColumnsAL.remove(selectedMeasureColumnsAL.indexOf(hdb));
//				hdb.srcColumTM = srcColumTM;
				System.out.println("additionalFunction4TarColumn===>"+additionalFunction4TarColumn);
				System.out.println("additionalFunction4SrcColumn===>"+additionalFunction4SrcColumn);
				hdb = new HeirarchyDataBean(sourcecolumnName,targetColumNames,false,"","","",srcColumTM,propertyTM,property,selectedSrcTable,selectedSrcTableTM,
						selectedTargetColumn,targetColumn,additionalFunction4SrcColumn,additionalFunction4TarColumn,"choseTablecopy.png","choseTablecopy.png");
				selectedMeasureColumnsAL.add(hdb);
				
				System.out.println("srcColumTM122222===>"+selectedMeasureColumnsAL.size());
				if(columnErrorMessage.equals("")){ //code change Jayaramu 25APR14
					msg4WarHouseLoad = "";
				}else{
					msg4WarHouseLoad = columnErrorMessage;
				}
				
//				selectedMeasureList.add(hdb);
			}else if(selectedType.equalsIgnoreCase("DirectSQL")){ //code change Jayaramu 19MAY14
//				selectedType
				
				tableNamesFromFactPopUp = new TreeMap<>();
				copytableNamesFromFactPopUp = new TreeMap<>();
				if(selectedTableNameTM4DirectSQL != null){
					String selectedTableName = "";
						Set set = selectedTableNameTM4DirectSQL.entrySet();
						// Get an iterator 
						Iterator j = set.iterator(); 
						// Filter elements 
						while(j.hasNext()) { 
						Map.Entry me = (Map.Entry)j.next(); 
						selectedTableName = (String) me.getValue();
						tableNamesFromFactPopUp.put(selectedTableName,selectedTableName);
						copytableNamesFromFactPopUp.put(selectedTableName,selectedTableName);
						
					}
				}
				
			}
			else{
				if(selectedType.equals("source") || selectedType.equals("target")){ //code change Jayaramu 10MAY14
					copytableNamesFromFactPopUp = new TreeMap<>();
					tableNamesFromFactPopUp = readTableNameFromFactPopUP();
					copytableNamesFromFactPopUp.putAll(tableNamesFromFactPopUp);
				}
				renderFlag4JoinCol = "true";
			}
			
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		System.out.println("Exiting: "
				+ new Exception().getStackTrace()[0].getClassName() + "."
				+ new Exception().getStackTrace()[0].getMethodName());
	}
	String selectColumnName= "";
	public String getSelectColumnName() {
		return selectColumnName;
	}

	public void setSelectColumnName(String selectColumnName) {
		this.selectColumnName = selectColumnName;
	}
	String tableMessage = "";

	public String getTableMessage() {
		return tableMessage;
	}

	public void setTableMessage(String tableMessage) {
		this.tableMessage = tableMessage;
	}
	

	public void setselectedJoinTables(String configType) {
		try{
			String srcTable = "";
			String sourcecolumnName = "";
			String joinColumnOper = "";
			TreeMap joinColumnOperTM1; 
			String tarTableName = "";
			String tarColumnName = "";
			TreeMap srcColumnTM1; 
			String srcConn = "";
			String tarConn = "";
			String srcWharImage = "";
			String tarWharImage = "";
			TreeMap sourceTableTM = new TreeMap<>();
			existingTableMessage = "";
			System.out.println("configType===>"+configType);
			if(configType.equalsIgnoreCase("Source") || configType.equalsIgnoreCase("ExistingTableSource")){
				
				if(configType.equalsIgnoreCase("ExistingTableSource")){
					selectTableName = factTableNames;
				}
				SourceTableName = selectTableName;
				srcConn = selectedConnection;
				HeirarchyDataBean hdb = (HeirarchyDataBean)selectedsrcAndWHJoinsAL.get(0);
				System.out.println("hdb===>"+hdb.getSourceTableName());
//				selectTableName = hdb.sourceTableName;
				srcTable = hdb.sourceTableName;
				sourcecolumnName = hdb.sourcecolumnName;
				joinColumnOper = hdb.joinColumnOper;
				joinColumnOperTM1 = hdb.joinColumnOperTM;
				tarTableName = hdb.tarTableName;
				tarColumnName = hdb.tarColumnName;
				srcColumnTM1 = hdb.srcColumnTM;
				srcWharImage = hdb.srcImageName4Whar;
				tarWharImage = hdb.tarImageName4Whar;
//				srcConn = hdb.srcConn;
				tarConn = hdb.tarConn;
				sourceTableTM = hdb.srcTableTM;
				String additionalFunction4SrcWarColumn = "";
				String additionalFunction4TarWarColumn = "";
				additionalFunction4SrcWarColumn  =hdb.additionalFunction4SrcWarColumn;
				additionalFunction4TarWarColumn = hdb.additionalFunction4TarWarColumn;
//				System.out.println("size===>"+srcAndWHJoinsAL.size());
				System.out.println("sourcecolumnName===>"+selectTableName);
				System.out.println("srcTable===>"+srcTable);
				if(selectedConnection==null ||selectedConnection.equals("") ){
					existingTableMessage = "Select Connection";
					return;
				}
					
				System.out.println("sourcecolumnName===>"+srcConn);
				System.out.println("sourcecolumnName===>"+connectionName);
				if(srcConn==null){
					this.tableMessage = "Please Select one connection to proceed.";
					return;
				}
				if(selectTableName==null){
					this.tableMessage = "Please select table to proceed.";
					return;
				}
				srcAndWHJoinsAL.remove(srcAndWHJoinsAL.indexOf(hdb));
				Connection con = Globals.getDBConnection(srcConn);
				srcColumnTM = columnName1("", selectTableName,con);
				
				String subSourceTableName=selectTableName;  // code change Menaka 03APR2014
				if(subSourceTableName.length()>15){
					subSourceTableName=subSourceTableName.substring(0,14)+"...";
				}
				sourceTableTM.put(selectTableName, selectTableName);
				hdb =new HeirarchyDataBean(selectTableName,sourcecolumnName,joinColumnOper, joinColumnOperTM1, 
						tarTableName, tarColumnName, srcColumnTM,srcConn,tarConn,subSourceTableName,sourceTableTM,
						additionalFunction4SrcWarColumn,additionalFunction4TarWarColumn,srcWharImage,tarWharImage);
				srcAndWHJoinsAL.add(hdb);
				selectedsrcAndWHJoinsAL.add(hdb);
				System.out.println("!selectedSrcTableTM.containsValue(selectTableName)===>"+!selectedSrcTableTM.containsValue(selectTableName));
				if(!selectedSrcTableTM.containsValue(selectTableName)){
				selectedSrcTableTM.put(selectTableName, selectTableName);
				}
				
//				hdb.sourcecolumnName = selectTableName;
			}else if(configType.equalsIgnoreCase("target") || configType.equalsIgnoreCase("ExistingTableTarget")){
				if(configType.equalsIgnoreCase("ExistingTableTarget")){
					selectTableName = factTableNames;
//					selectedConnection = "";
				}
				tarTableName = selectTableName;
				tarConn = selectedConnection;
				HeirarchyDataBean hdb = (HeirarchyDataBean)selectedsrcAndWHJoinsAL.get(0);
				srcTable = hdb.sourceTableName;
				sourcecolumnName = hdb.sourcecolumnName;
				joinColumnOper = hdb.joinColumnOper;
				joinColumnOperTM1 = hdb.joinColumnOperTM;
//				tarTableName = hdb.tarTableName;
				tarColumnName = hdb.tarColumnName;
				srcColumnTM1 = hdb.srcColumnTM;
				srcConn = hdb.srcConn;
//				tarConn = hdb.tarConn;
				sourceTableTM = hdb.srcTableTM;
				String additionalFunction4SrcWarColumn = "";
				String additionalFunction4TarWarColumn = "";
				additionalFunction4SrcWarColumn  =hdb.additionalFunction4SrcWarColumn;
				additionalFunction4TarWarColumn = hdb.additionalFunction4TarWarColumn;
//				System.out.println("size===>"+srcAndWHJoinsAL.size());
//				System.out.println("sourcecolumnName===>"+tarConn);
//				Connection con = Globals.getDBConnection(tarConn);
//				columnName1("", selectTableName,con);
				if(selectedConnection==null ||selectedConnection.equals("") ){
					existingTableMessage = "Select Connection";
					return;
				}
				if(tarTableName.equals("")){
					this.tableMessage = "Please Select one table to proceed.";
					return;
				}
				if(tarConn!=null){
					
				if(tarConn.equals("") && !configType.equalsIgnoreCase("ExistingTableTarget")){
					this.tableMessage = "Please Select one connection to proceed.";
					return;
				}
				}else{
					this.tableMessage = "Please Select one connection to proceed.";
					return;
				}
				srcAndWHJoinsAL.remove(srcAndWHJoinsAL.indexOf(hdb));
				 String subSourceTableName=srcTable;  // code change Menaka 03APR2014
					if(subSourceTableName.length()>15){
						subSourceTableName=subSourceTableName.substring(0,14)+"...";
					}
				
					srcWharImage = hdb.srcImageName4Whar;
					tarWharImage = hdb.tarImageName4Whar;
				hdb =new HeirarchyDataBean(srcTable,sourcecolumnName,joinColumnOper, joinColumnOperTM1, 
						tarTableName, tarColumnName, srcColumnTM1,srcConn,tarConn,subSourceTableName, sourceTableTM,additionalFunction4SrcWarColumn,
						additionalFunction4TarWarColumn,srcWharImage,tarWharImage);
				if(!tarTableName.startsWith("'"))
				if(!selectedSrcTableTM.containsValue(tarTableName)){
					selectedSrcTableTM.put(tarTableName, tarTableName);
					}
				srcAndWHJoinsAL.add(hdb);
				selectedsrcAndWHJoinsAL.add(hdb);
				Connection con = Globals.getDBConnection(tarConn);
				columnNameTM1 = columnName1("", tarTableName, con);
				copyColumnNameTM = new TreeMap<>();
				copyColumnNameTM = columnNameTM1;
			}else if(configType.equalsIgnoreCase("DirectSQL")){
//				Connection Con = Globals.getDBConnection("DW_Connection"); code comment jayaramu ????? 19MAY14
//				TreeMap srcColumnTM = new TreeMap<>(); 
//				TreeMap tableNameTM = new TreeMap<>();
//				String tableName = "";
//				String columnName = "";
//				String operator = "";
//				String targetColumnName = "";
//				for(int i=0;i<whereClauseList.size();i++){
//					if(whereClauseList.get(i).equals(selectedWhereClauseList.get(0))){
//						System.out.println("selected is removed");
//						HierarchydataBean hdb1 = (HierarchydataBean) whereClauseList.get(i);
//						tableName = hdb1.selectedTableName;
//						columnName = hdb1.selectedSrcColumn;
//						operator = hdb1.joinColumnOper;
//						targetColumnName = hdb1.tarColumnName;
//						tableNameTM = hdb1.selectedSrcTableNameTM;
//						srcColumnTM = hdb1.selectedSrcColumnTM;
//						
//						whereClauseList.remove(i);
//						break;
//					}
//				}
//				HierarchydataBean hdb = new HierarchydataBean(tableName,tableNameTM,columnName,srcColumnTM,operator,joinOperTM,selectTableName,targetColumnName);
//				whereClauseList.add(hdb); code comment jayaramu ????? 19MAY14
			
				if(selectedWhereClauseList != null && !selectedWhereClauseList.isEmpty()){
					HierarchydataBean hdb1 = (HierarchydataBean) selectedWhereClauseList.get(0);
					if(selectTableName != null && !selectTableName.equals("")){
					hdb1.setTarTableName(selectTableName);
				}
				}
			
			
			}else if(configType.equalsIgnoreCase("DirectSQLExistingTable")){ //code change jayaramu 19MAY14
				if(selectedWhereClauseList != null && !selectedWhereClauseList.isEmpty()){
					HierarchydataBean hdb1 = (HierarchydataBean) selectedWhereClauseList.get(0);
					if(factTableNames != null && !factTableNames.equals("")){
					hdb1.setTarTableName(factTableNames);
				}
				}
				
				
			}
			else{
				tarColumnName = selectColumnName;
				tarConn = selectedConnection;
				HeirarchyDataBean hdb = (HeirarchyDataBean)selectedsrcAndWHJoinsAL.get(0);
				srcTable = hdb.sourceTableName;
				sourcecolumnName = hdb.sourcecolumnName;
				joinColumnOper = hdb.joinColumnOper;
				joinColumnOperTM1 = hdb.joinColumnOperTM;
				tarTableName = hdb.tarTableName;
//				tarColumnName = hdb.tarColumnName;
				srcColumnTM1 = hdb.srcColumnTM;
				srcConn = hdb.srcConn;
				tarConn = hdb.tarConn;
				String additionalFunction4SrcWarColumn = "";
				String additionalFunction4TarWarColumn = "";
				additionalFunction4SrcWarColumn  =hdb.additionalFunction4SrcWarColumn;
				additionalFunction4TarWarColumn = hdb.additionalFunction4TarWarColumn;
				srcAndWHJoinsAL.remove(srcAndWHJoinsAL.indexOf(hdb));
				System.out.println("size===>"+srcAndWHJoinsAL.size());
				System.out.println("sourcecolumnName===>"+tarConn);
//				Connection con = Globals.getDBConnection(tarConn);
//				columnName1("", selectTableName,con);
				sourceTableTM = hdb.srcTableTM;
				String subSourceTableName=srcTable;  // code change Menaka 03APR2014
				if(subSourceTableName.length()>15){
					subSourceTableName=subSourceTableName.substring(0,14)+"...";
				}
				srcWharImage = hdb.srcImageName4Whar;
				tarWharImage = hdb.tarImageName4Whar;
				hdb =new HeirarchyDataBean(srcTable,sourcecolumnName,joinColumnOper, joinColumnOperTM1, 
						tarTableName, tarColumnName, srcColumnTM1,srcConn,tarConn,subSourceTableName,sourceTableTM,
						additionalFunction4SrcWarColumn,additionalFunction4TarWarColumn,srcWharImage,tarWharImage);
				srcAndWHJoinsAL.add(hdb);
				selectedsrcAndWHJoinsAL.add(hdb);
			}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	ArrayList incrementalLoadUpdateAL = new ArrayList<>(); 
	

	public ArrayList getIncrementalLoadUpdateAL() {
		return incrementalLoadUpdateAL;
	}
	public void setIncrementalLoadUpdateAL(ArrayList incrementalLoadUpdateAL) {
		this.incrementalLoadUpdateAL = incrementalLoadUpdateAL;
	}

	ArrayList selectedincrementalLoadUpdateAL = new ArrayList<>();
	public ArrayList getSelectedincrementalLoadUpdateAL() {
		return selectedincrementalLoadUpdateAL;
	}
	public void setSelectedincrementalLoadUpdateAL(
			ArrayList selectedincrementalLoadUpdateAL) {
		this.selectedincrementalLoadUpdateAL = selectedincrementalLoadUpdateAL;
	}
	public void closeTable() {
		try{
			tableMessage = "";
			srctartableMessage = "";
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	//start code change Jayaramu 13MAR14
	public void readTableFromXmlAndPerValues(){
		
		try{
			
			message = "";
			FacesContext ctx = FacesContext.getCurrentInstance();
			ExternalContext extContext = ctx.getExternalContext();
			Map sessionMap = extContext.getSessionMap();
			HierarchyBean hdb = (HierarchyBean) sessionMap.get("hierarchyBean");
			
			PropUtil prop=new PropUtil();
			String dir=prop.getProperty("HIERARCHY_XML_DIR");
			Document xmldoc = Globals.openXMLFile(dir, hierarchyXMLFileName);
			 Node RootNode = Globals.getNodeByAttrVal(xmldoc, "Fact_Tables", "ID", hdb.getHierarchy_ID());
			 selectedTableNameTM = new TreeMap<>();
			 copySelectedTableNameTM = new TreeMap<>();
			 message = "";
			 if(RootNode == null){
				 
				 message = "The Fact Table is not configured. Please contact the administrator to configure the facts and proceed further.";
				 color4FactMsg = "red";
				 genDataTitleMessage=message;
				 if(message.length()>80){ //code change Jayaramu 05APR14
				 message = message.substring(0,80)+"...";
					 
					 }
			 return;
			 }else{  // code change Menaka 17MAR2014
					NodeList ndlist=RootNode.getChildNodes();
					String value="";
					for(int k=0;k<ndlist.getLength();k++){
						Node nd1=ndlist.item(k);
						
						NodeList ndlist1=nd1.getChildNodes();
						
						for(int i=0;i<ndlist1.getLength();i++){
							Node nd=ndlist1.item(i);
							
							if(nd.getNodeType()==Node.ELEMENT_NODE && nd.getNodeName().equals("Period_Values")){
								value=nd.getTextContent();
							}
						}
					}
					
					 selectedPeriodValuesAL = new ArrayList<>();
					 copySelectedPeriodValuesAL = new ArrayList<>();
					
					
					String[] periodVals=value.split(";");
					
					for(int j=0;j<periodVals.length;j++){
						selectedPeriodValuesAL.add(periodVals[j]);
						copySelectedPeriodValuesAL.add(periodVals[j]);
					}
					
					
					 }
			 
			 
//			 else{     //code comment by jayaramu 13MAR14
//				 
//				 
//				 if(RootNode.getNodeType() == Node.ELEMENT_NODE){
//				 NodeList childNodes = RootNode.getChildNodes();
//				 
//				 for(int i=0;i<childNodes.getLength();i++){
//					 
//					 Node table = childNodes.item(i);
//					 if(table.getNodeType() == Node.ELEMENT_NODE){
//						System.out.println("Tables====>>>>"+table); 
//						if(table.getNodeName().equals("Table")){
//						String tableName = table.getAttributes().getNamedItem("Source_Fact_TableName").getTextContent().toString();
//						 if(tableName != null && !tableName.equals("")){
//						 selectedTableNameTM.put(tableName, tableName);
//						 copySelectedTableNameTM.put(tableName, tableName);
//					 }}
//					 }
//				 }
//				 }
//				 
//			 }
			 loadPeriodColumnValues();
			}catch(Exception e){
				
				e.printStackTrace();
			}
		
	}
	//End code change Jayaramu 13MAR14
	//start code change Jayaramu 13MAR14
	public void writeGenDataToXml(String genDataFrom, String generateType){
		
		try{
		FacesContext ctx = FacesContext.getCurrentInstance();
		ExternalContext extContext = ctx.getExternalContext();
		Map sessionMap = extContext.getSessionMap();
		HierarchyBean hdb = (HierarchyBean) sessionMap.get("hierarchyBean");
		PropUtil prop=new PropUtil();
		String dir=prop.getProperty("HIERARCHY_XML_DIR");
		Document xmldoc = Globals.openXMLFile(dir, hierarchyXMLFileName);
		Node hierNd = Globals.getNodeByAttrVal(xmldoc, "Hierarchy_Level", "Hierarchy_ID", hdb.getHierarchy_ID());
		 Node RootNode = Globals.getNodeByAttrVal(xmldoc, "Fact_Tables", "ID", hdb.getHierarchy_ID());
		 Element RootEle = (Element)RootNode;
		 if(RootNode==null){
			 message = "Fact is not yet configured. Please contact administrator";
			 color4FactMsg = "red";
			 return;
		 }
		 if(generateType.equalsIgnoreCase("InsertFact") && Globals.getAttrVal4AttrName(RootEle, "Gen_Mode").equalsIgnoreCase("")){
				message = "Full Load not generated so not possible to generate Incremental Load";
				color4FactMsg = "red";
				return;
			}
		 System.out.println("Previous_Gen_Type====>>>>"+RootEle.getAttribute("Previous_Gen_Type")); 
		 String factRunType = "";
		 if(!generateType.equalsIgnoreCase("GenerateData")){
//			 if(generateType.equalsIgnoreCase("Incremental")){
//				 RootEle.setAttribute("Gen_Mode", "InsertFact");
//				 if(checkbox4genFact){
//					 
//				 }
//			 }else{
				 RootEle.setAttribute("Gen_Mode", generateType);
				 factRunType = generateType;
//			 }
		 
		 }else{
			 if(RootEle.getAttribute("Previous_Gen_Type").equalsIgnoreCase("GenerateFact")){
				 RootEle.setAttribute("Gen_Mode", "InsertFact");
				 factRunType = "InsertFact";
			 }else if(RootEle.getAttribute("Previous_Gen_Type").equalsIgnoreCase("InsertFact") || 
					 RootEle.getAttribute("Previous_Gen_Type").equalsIgnoreCase("UpdateFact")){
				 RootEle.setAttribute("Gen_Mode", "InsertFact");
				 factRunType = "InsertFact";
			 }else if(RootEle.getAttribute("Previous_Gen_Type").equals("")){
				 RootEle.setAttribute("Gen_Mode", "GenerateFact");
				 factRunType = "GenerateFact";
			 }
			 
		 }
		 message = ""; //code change Jayaramu 05APR14
		 genDataTitleMessage = "";
		 if(RootNode == null){
			 
			 message = "The fact table is not configured. Please contact the administrator to configure the facts and proceed further.";
			 color4FactMsg = "red";
			 if(message.length()>70){ //code change Jayaramu 05APR14
				 genDataTitleMessage = message;
				 message = message.substring(0,68)+"...";
				 
				 }
			
		 return;
		 }else{
			 String periodValues = "";
			 int p=0;

				for(int k=0;k<selectedPeriodValuesAL.size();k++){
					
					periodValues = periodValues+String.valueOf(selectedPeriodValuesAL.get(k)).concat(";");
				
				}
				
			 if(RootNode.getNodeType() == Node.ELEMENT_NODE){
				 NodeList childNodes = RootNode.getChildNodes();
				 
				 for(int i=0;i<childNodes.getLength();i++){
					 
					 Node table = childNodes.item(i);
					 if(table.getNodeType() == Node.ELEMENT_NODE){
						System.out.println("Tables====>>>>"+table); 
						if(table.getNodeName().equals("Table")){
							
							NodeList periodValuesNode = table.getChildNodes();
							
							for(int j=0;j<periodValuesNode.getLength();j++){
								
								Node periodValue = periodValuesNode.item(j);
								if(periodValue.getNodeType() == Node.ELEMENT_NODE){
									if(periodValue.getNodeName().equals("Period_Values")){
										
										
										periodValue.setTextContent(periodValues);
										
									}else if(periodValue.getNodeName().equals("Measure_Columns")){
										NodeList meas =  periodValue.getChildNodes();
										for(int k=0;k<meas.getLength();k++){
											if(meas.item(k).getNodeType() == Node.ELEMENT_NODE){
												if(Globals.getAttrVal4AttrName(meas.item(k), "Column_Property").equals("Period")){
													p++;
												}
												
											}
										}
										
										
									}
								}
							}
							
							
						}
					 }
				 }
				 Globals.writeXMLFile(xmldoc, dir, hierarchyXMLFileName);
				 System.out.println("p=====>"+p);
//				 if(p==1){
					 
//				 }else{
//					
//					 if(p<=0){
//						 this.message = "The Fact Table(s) must have atleast one period column.";
////							System.out.println("this.message----->>>"+this.message);
//							return;
//					 }else{
//						 this.message = "The Fact Table(s) must have only one period column.";
////							System.out.println("this.message----->>>"+this.message);
//							return;
//					 }
//				 }
				 
				 if(!checkbox4genFact && !checkbox4Regen){  // code change Menaka 23APR2014
					 message = "Please select atleast one option to generate.";
					 color4FactMsg = "red";
					 return;
				 }
				 
				 if(checkbox4genFact && checkbox4Regen){
					 
					 Hashtable dimStatusHT = new Hashtable<>();
					 hdb.reGenerateHierarchy(hdb.getHierarchy_ID(), hdb.codecombinationFlag,true,factRunType);
					 if(String.valueOf(dimStatusHT.get("dimResult")).equalsIgnoreCase("Error")){
						 
						 message = String.valueOf(dimStatusHT.get("errorMessage"));
						 color4FactMsg = "red";
						 return;
						 
					 }else{
//					 Thread t=new Thread(new genarateFactsBean(hdb.getHierarchy_ID()));
//					 t.start();//Code Change Gokul 21FEB2014
					 if(genDataFrom.equals("FromAdmin")){
						 message = "Warning : This Hierarchy is not approved yet.";
						 color4FactMsg = "red";
					 }else{
					 message = "The Hierarchy and Fact(s) generation are in progress. You may track the progress from the Hierarchy List screen.";  // code change Menaka 27MAR2014
					 color4FactMsg = "blue";
					 }
					 }
				 }else if(checkbox4Regen){
					 Hashtable dimStatusHT = new Hashtable<>();
					 hdb.reGenerateHierarchy(hdb.getHierarchy_ID(), hdb.codecombinationFlag,false,"");
					 if(genDataFrom.equals("FromAdmin")){
						 message = "Warning : This Hierarchy is not approved yet.";
						 color4FactMsg = "red";
					 }else{
					 message = "The Hierarchy re-generate is in progress. You may track the progress from the Hierarchy List screen.";
					 color4FactMsg = "blue";
					 }
				 }else if(checkbox4genFact){
					 Node hierarchyLevelNode = Globals.getNodeByAttrVal(xmldoc, "Hierarchy_Level", "Hierarchy_ID", hdb.getHierarchy_ID());
					 if(hierarchyLevelNode != null){
						 Element dimstatus = (Element)hierarchyLevelNode;
						 String dimStatus = dimstatus.getAttribute("Dim_Status");
						 
					 Thread t=new Thread(new genarateFactsBean(hdb.getHierarchy_ID(),hdb.getLoginUserName(),factRunType));
					 t.start();//Code Change Gokul 21FEB2014
					 if(genDataFrom.equals("FromAdmin")){
						 message = "Warning : This Hierarchy is not approved yet.";
						 color4FactMsg = "red";
					 }else{
					 message = "The Fact(s) generation are in progress. You may track the progress from the Hierarchy List screen.";  // code change Menaka 27MAR2014
					 color4FactMsg = "blue";
					 }}else{
						 message = "The dimension not created yet.";
						 color4FactMsg = "red";
					 }
					 }
				 
				 
			 }
		 }
		 if(message.length()>70){ //code change Jayaramu 05APR14
		 genDataTitleMessage = message;
		 message = message.substring(0,68)+"...";
		 
		 }
		
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	//End code change Jayaramu 13MAR14
	
	
	
	
	
	String connName = "";
	public String getConnName() {
		return connName;
	}

	public void setConnName(String connName) {
		this.connName = connName;
	}

	String hostName = "";
	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	String portName = "";
	public String getPortName() {
		return portName;
	}

	public void setPortName(String portName) {
		this.portName = portName;
	}

	String dbName = "";
	public String getDbName() {
		return dbName;
	}

	public void setDbName(String dbName) {
		this.dbName = dbName;
	}

	String userName = "";
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	String password = "";
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	String connMsg = "";
	String connMsg1 = "";
	public String getConnMsg1() {
		return connMsg1;
	}

	public void setConnMsg1(String connMsg1) {
		this.connMsg1 = connMsg1;
	}

	public String getConnMsg() {
		return connMsg;
	}

	public void setConnMsg(String connMsg) {
		this.connMsg = connMsg;
	}

	public void connectionSave() {
		try{
			if(connName.equals("")){
				connMsg = "Connection name cannot be empty. Please fill.";
				return;
			}
			if(portName.equals("")){
				connMsg = "Port name cannot be empty. Please fill.";
				return;
			}
			if(hostName.equals("")){
				connMsg = "Host name cannot be empty. Please fill.";
				return;
			}if(dbName.equals("")){
				connMsg = "DB name cannot be empty. Please fill.";
				return;
			}
			if(userName.equals("")){
				connMsg = "User name cannot be empty. Please fill.";
				return;
			}
			if(password.equals("")){
				connMsg = "Password cannot be empty. Please fill.";
				return;
			}
			String hierDir = "";
			String hierConfigXmlName = "";
			PropUtil prop = new PropUtil();
			hierDir = prop.getProperty("HIERARCHY_XML_DIR");
			hierConfigXmlName = prop.getProperty("HIERARCHY_CONFIG_FILE");
			Document doc = Globals.openXMLFile(hierDir, hierConfigXmlName);
			NodeList connList = doc.getElementsByTagName("Connection");
			Node connNd = connList.item(0);
			Element connNameEle = doc.createElement(connName);
			connNd.appendChild(connNameEle);
			Node connNameNd = (Node) connNameEle;
			Element NameEle = doc.createElement("ConnectionName");
			connNameNd.appendChild(NameEle);
			NameEle.setTextContent(connName);
			Element hostEle = doc.createElement("HostName");
			connNameNd.appendChild(hostEle);
			hostEle.setTextContent(hostName);
			Element portEle = doc.createElement("PortName");
			connNameNd.appendChild(portEle);
			portEle.setTextContent(portName);
			Element dbEle = doc.createElement("DBName");
			connNameNd.appendChild(dbEle);
			dbEle.setTextContent(dbName);
			Element userEle = doc.createElement("UserName");
			connNameNd.appendChild(userEle);
			userEle.setTextContent(userName);
			Element passEle = doc.createElement("Password");
			connNameNd.appendChild(passEle);
			passEle.setTextContent(password);
			Element conTypeEle = doc.createElement("ConnectionType");
			connNameNd.appendChild(conTypeEle);
			conTypeEle.setTextContent("OCI 10g/11g");
			connMsg = "Connection saved successfully";
			Globals.writeXMLFile(doc, hierDir, hierConfigXmlName);
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	public void connectionClose() {
		try{
			PropUtil prop=new PropUtil();
			String dir=prop.getProperty("HIERARCHY_XML_DIR");
			connName = "";
			hostName = "";
			portName = "";
			password = "";
			dbName = "";
			userName = "";
			connMsg = "";
			connectionName4Edit = "";
			hostName4Edit = "";
			portName4Edit = "";
			dBName4Edit = "";
			conUserName4Edit = "";
			conPassword4Edit = "";
			connectionType4Edit = "";
			String hierarchyConfigFileName=prop.getProperty("HIERARCHY_CONFIG_FILE");
			Document doc = Globals.openXMLFile(dir, hierarchyConfigFileName);
			NodeList connList = doc.getElementsByTagName("Connection");
			Node connNd = connList.item(0);
			NodeList connChdNdlList = connNd.getChildNodes();
			for(int i=0;i<connChdNdlList.getLength();i++){
				if(connChdNdlList.item(i).getNodeType() == Node.ELEMENT_NODE){
					connectionNameTM.put(connChdNdlList.item(i).getNodeName(), connChdNdlList.item(i).getNodeName());
				}
			}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	//start code change Jayaramu 04APR14
	public void getConnectionDetails4Edit(String editFrom){
		try{
			
			System.out.println("Entering: "
					+ new Exception().getStackTrace()[0].getClassName() + "."
					+ new Exception().getStackTrace()[0].getMethodName());
			String connectionName = "";
			if(editFrom.equals("FromSourceFactConnection")){
				connectionName = this.connectionName;
			}else if(editFrom.equals("FromTargetFactConnection")){
				connectionName = this.tarConnectionName;
			}
			System.out.println("connectionName:==>>> "+connectionName);
			
		Hashtable connectionDetailsHT = getconnectionDetails(connectionName);
		System.out.println("connectionDetailsHT==>>>"+connectionDetailsHT);
		
		connectionName4Edit = (String)connectionDetailsHT.get("ConnectionName");
		hostName4Edit = (String)connectionDetailsHT.get("HostName");
		portName4Edit = (String)connectionDetailsHT.get("PortName");
		dBName4Edit = (String)connectionDetailsHT.get("DBName");
		conUserName4Edit = (String)connectionDetailsHT.get("UserName");
		conPassword4Edit = (String)connectionDetailsHT.get("Password");
		connectionType4Edit = (String)connectionDetailsHT.get("ConnectionType");
		}catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		System.out.println("Exiting: "
				+ new Exception().getStackTrace()[0].getClassName() + "."
				+ new Exception().getStackTrace()[0].getMethodName());
		}
	//End code change Jayaramu 04APR14
	
	//start code change Jayaramu 04APR14
	public Hashtable getconnectionDetails(String connectionName){
		System.out.println("Entering: "
				+ new Exception().getStackTrace()[0].getClassName() + "."
				+ new Exception().getStackTrace()[0].getMethodName());
		
		Hashtable connectionDetailsHT = new Hashtable<>();
		try{
			PropUtil prop=new PropUtil();
			String dir=prop.getProperty("HIERARCHY_XML_DIR");
			String hierarchyConfigFileName=prop.getProperty("HIERARCHY_CONFIG_FILE");
			Document doc = Globals.openXMLFile(dir, hierarchyConfigFileName);
			NodeList connList = doc.getElementsByTagName("Connection");
			Node connNd = connList.item(0);
			NodeList connChdNdlList = connNd.getChildNodes();
			for(int i=0;i<connChdNdlList.getLength();i++){
				Node connChild = connChdNdlList.item(i);
				if(connChild.getNodeType() == Node.ELEMENT_NODE){
					
					if(connChild.getNodeName().equals(connectionName)){
						
						NodeList connDetailsNode = connChild.getChildNodes();
						for(int j=0;j<connDetailsNode.getLength();j++){
							Node conDetails = connDetailsNode.item(j);
							if(conDetails.getNodeType() == Node.ELEMENT_NODE){
								
								String conNodes = conDetails.getTextContent();
								if(conDetails.getNodeName().equals("ConnectionName")){
									connectionDetailsHT.put("ConnectionName", conNodes);
								}else if(conDetails.getNodeName().equals("HostName")){
									connectionDetailsHT.put("HostName", conNodes);
								}else if(conDetails.getNodeName().equals("PortName")){
									connectionDetailsHT.put("PortName", conNodes);
								}else if(conDetails.getNodeName().equals("DBName")){
									connectionDetailsHT.put("DBName", conNodes);
								}else if(conDetails.getNodeName().equals("UserName")){
									connectionDetailsHT.put("UserName", conNodes);
								}else if(conDetails.getNodeName().equals("Password")){
									connectionDetailsHT.put("Password", conNodes);
								}else if(conDetails.getNodeName().equals("ConnectionType")){
									connectionDetailsHT.put("ConnectionType", conNodes);
								}
							}
							
						}
					}
					
					
					
				}
			}
			}catch (Exception e) {
				// TODO: handle exception
			e.printStackTrace();
			}
		System.out.println("Exiting: "
				+ new Exception().getStackTrace()[0].getClassName() + "."
				+ new Exception().getStackTrace()[0].getMethodName());
		
		return connectionDetailsHT;
	}
	//End code change Jayaramu 04APR14
	
	//start code change Jayaramu 04APR14
	public  void testDBConnection(String testConnectionFrom) {
	   
	    try {
	    	String connectionName = "";
	    	tartestConMsg = "";
			testConMsg = "";
			tartestConMsg1 = "";
			testConMsg1 = "";
			connMsg = "";
			connMsg1 = "";
	    	if(testConnectionFrom.equals("testSourceConnection")){
	    		connectionName = this.connectionName;
	    		if(connectionName.equals("-- select Value --")){
	    			testConMsg = "Please choose connection for Test.";
	    			color4SrcFactMsg = "red";
	    			return;
	    		}
	    	}else if(testConnectionFrom.equals("testTargetConnection")){
	    		connectionName = this.tarConnectionName;
	    		if(connectionName.equals("-- select Value --")){
	    			tartestConMsg = "Please choose connection for Test.";
	    			color4SrcFactMsg = "red";
	    			return;
	    		}
	    	}
	    	String conHostName = "";
	    	String conPortNumber = "";
	    	String conDBName = "";
	    	String conUserName = "";
	    	String conPassword = "";
	    	//when test connection from factPopup
	    	if(testConnectionFrom.equals("testSourceConnection") || testConnectionFrom.equals("testTargetConnection")){  
	    	Hashtable connectionDetailsHT = getconnectionDetails(connectionName);
	    	conHostName = (String)connectionDetailsHT.get("HostName");
	    	conPortNumber = (String)connectionDetailsHT.get("PortName");
	    	conDBName = (String)connectionDetailsHT.get("DBName");
	    	conUserName = (String)connectionDetailsHT.get("UserName");
			conPassword = (String)connectionDetailsHT.get("Password");
	    	}else if(testConnectionFrom.equals("testFromEditConnection")){ //when test connection from Edit ConnectionPopup
	    		
	    		conHostName = hostName4Edit;
	    		conPortNumber = portName4Edit;
	    		conDBName = dBName4Edit;
	    		conUserName = conUserName4Edit;
	    		conPassword = conPassword4Edit;
	    	}
	    	
	    	String url = "jdbc:oracle:thin:@" + conHostName + ":" + conPortNumber + ":" + conDBName;
	        Class.forName("oracle.jdbc.driver.OracleDriver");
	        Connection conn = DriverManager.getConnection(url, conUserName, conPassword);
	        if(testConnectionFrom.equals("testSourceConnection")){
	        	testConMsg = "Connection Success.";
	        	color4SrcFactMsg = "blue";
	    	}else if(testConnectionFrom.equals("testTargetConnection")){
	    		tartestConMsg = "Connection Success.";
	    		color4SrcFactMsg = "blue";
	    	}else if(testConnectionFrom.equals("testFromEditConnection")){
	    		connMsg = "Connection Success.";
	    		color4SrcFactMsg = "blue";
	    	}
	        
	        
	        
	    } catch (Exception er) { 
	    	String errorMsg = "";
	    	 errorMsg = Globals.getErrorString(er);
	    	 if(testConnectionFrom.equals("testSourceConnection")){
	    		 testConMsg = errorMsg.substring(0,33);
	    		 color4SrcFactMsg = "red";
	    		 testConMsg1 = errorMsg;
		    	}else if(testConnectionFrom.equals("testTargetConnection")){
		    		tartestConMsg = errorMsg.substring(0,33);
		    		color4SrcFactMsg = "red";
		    		tartestConMsg1 = errorMsg;
		    	}else if(testConnectionFrom.equals("testFromEditConnection")){
		    		connMsg = errorMsg.substring(0,40);
		    		color4SrcFactMsg = "red";
		    		connMsg1 = errorMsg;
		    	}
	        er.printStackTrace();
	        
	    }
}
	//End code change Jayaramu 04APR14
	
	//start code change Jayaramu 04APR14
	public  void SaveEditedConnections(){
		
		System.out.println("Entering: "
				+ new Exception().getStackTrace()[0].getClassName() + "."
				+ new Exception().getStackTrace()[0].getMethodName());
		try{
			PropUtil prop=new PropUtil();
			String dir=prop.getProperty("HIERARCHY_XML_DIR");
			String hierarchyConfigFileName=prop.getProperty("HIERARCHY_CONFIG_FILE");
			Document doc = Globals.openXMLFile(dir, hierarchyConfigFileName);
			NodeList connList = doc.getElementsByTagName("Connection");
			Node connNd = connList.item(0);
			NodeList connChdNdlList = connNd.getChildNodes();
			for(int i=0;i<connChdNdlList.getLength();i++){
				Node connChild = connChdNdlList.item(i);
				if(connChild.getNodeType() == Node.ELEMENT_NODE){
					
					if(connChild.getNodeName().equals(connectionName4Edit)){
						
						NodeList connDetailsNode = connChild.getChildNodes();
						for(int j=0;j<connDetailsNode.getLength();j++){
							Node conDetails = connDetailsNode.item(j);
							if(conDetails.getNodeType() == Node.ELEMENT_NODE){
								if(conDetails.getNodeName().equals("HostName")){
									conDetails.setTextContent(hostName4Edit);
								}else if(conDetails.getNodeName().equals("PortName")){
									conDetails.setTextContent(portName4Edit);
								}else if(conDetails.getNodeName().equals("DBName")){
									conDetails.setTextContent(dBName4Edit);
								}else if(conDetails.getNodeName().equals("UserName")){
									conDetails.setTextContent(conUserName4Edit);
								}else if(conDetails.getNodeName().equals("Password")){
									conDetails.setTextContent(conPassword4Edit);
								}
							}
							
						}
					}
					
					
					
				}
			}
			
			Globals.writeXMLFile(doc, dir, hierarchyConfigFileName);
			connMsg = "Connection Details Updated Successfully.";
			
			
			}catch (Exception e) {
				// TODO: handle exception
			e.printStackTrace();
			}
		System.out.println("Exiting: "
				+ new Exception().getStackTrace()[0].getClassName() + "."
				+ new Exception().getStackTrace()[0].getMethodName());
		
	}
	//End code change Jayaramu 04APR14
	
	//start code change Jayaramu 04APR14
	public void deleteConnectionFromConfigXml(String connDeleteFrom){
		
		System.out.println("Entering: "
				+ new Exception().getStackTrace()[0].getClassName() + "."
				+ new Exception().getStackTrace()[0].getMethodName());
		try{
			
			String connectionName = "";
			tartestConMsg = "";
			testConMsg = "";
			tartestConMsg1 = "";
			testConMsg1 = "";
			if(connDeleteFrom.equals("FromSourceFactDelete")){
				connectionName = this.connectionName;
				if(connectionName.equals("Data_Connection") || connectionName.equals("DW_Connection")){
					testConMsg = "You cannot delete a default connection.";
					color4SrcFactMsg = "red";
					return;
				}
				else if(connectionName.equals("-- select Value --")){
					testConMsg = "Please choose connection for Delete.";
					color4SrcFactMsg = "red";
					return;
				}
			}else if(connDeleteFrom.equals("FromTargetFactDelete")){
				connectionName = this.tarConnectionName;
				if(connectionName.equals("Data_Connection") || connectionName.equals("DW_Connection")){
					tartestConMsg = "You cannot delete a default connection.";
					color4SrcFactMsg = "red";
					return;
				}
				else if(connectionName.equals("-- select Value --")){
					tartestConMsg = "Please choose Connection to Delete.";
					color4SrcFactMsg = "red";
					return;
				}
			}
			
			
			PropUtil prop=new PropUtil();
			String dir=prop.getProperty("HIERARCHY_XML_DIR");
			String hierarchyConfigFileName=prop.getProperty("HIERARCHY_CONFIG_FILE");
			Document doc = Globals.openXMLFile(dir, hierarchyConfigFileName);
			NodeList connList = doc.getElementsByTagName("Connection");
			Node connNd = connList.item(0);
			NodeList connChdNdlList = connNd.getChildNodes();
			
			for(int i=0;i<connChdNdlList.getLength();i++){
				Node connChild = connChdNdlList.item(i);
				if(connChild.getNodeType() == Node.ELEMENT_NODE){
					
					if(connChild.getNodeName().equals(connectionName)){
						connectionNameTM.remove(connectionName);
						connChild.getParentNode().removeChild(connChild);
						
						if(connDeleteFrom.equals("FromSourceFactDelete")){
							testConMsg = "Connection deleted Successfully.";
							color4SrcFactMsg = "blue";
						}else if(connDeleteFrom.equals("FromTargetFactDelete")){
							tartestConMsg = "Connection deleted Successfully.";
							color4SrcFactMsg = "blue";
						}
	
					}
				
				}
			}
			
			Globals.writeXMLFile(doc, dir, hierarchyConfigFileName);
			
			}catch (Exception e) {
				// TODO: handle exception
			e.printStackTrace();
			}
		System.out.println("Exiting: "
				+ new Exception().getStackTrace()[0].getClassName() + "."
				+ new Exception().getStackTrace()[0].getMethodName());
	}//End code change Jayaramu 04APR14
	//Start code change Jayaramu 05APR14
	public void setServerDetails(){
		try{
		Hashtable connectionDetailsHT = getconnectionDetails("Data_Connection");
		server = (String)connectionDetailsHT.get("HostName");
    	port = (String)connectionDetailsHT.get("PortName");
    	instance = (String)connectionDetailsHT.get("DBName");
    	user = (String)connectionDetailsHT.get("UserName");
    	System.out.println("server===>"+server);
    	System.out.println("port===>"+port);
    	System.out.println("instance===>"+instance);
    	System.out.println("user===>"+user);
	}catch (Exception e) {
		// TODO: handle exception
		e.printStackTrace();
	}
		}//End code change Jayaramu 05APR14
	
	
	public void booleanActionToRadio(String actionFrom){ //code change Jayaramu 14APR14
		
	
		HeirarchyDataBean hdb = new HeirarchyDataBean();
		for(int j=0;j<codeandNameAL.size();j++){
			hdb = (HeirarchyDataBean)codeandNameAL.get(j);
			if(!hdb.equals(runReportSessionslist.get(0))){
				hdb.setPrimaryType(false);
			}
			
		}
	}
	
	
	String fromFactPopup = "";
	public String getFromFactPopup() {
		return fromFactPopup;
	}
	public void setFromFactPopup(String fromFactPopup) {
		this.fromFactPopup = fromFactPopup;
	}
	public void getSelectedJoinRowColumn(String fromWhere){
		try{
		flag4CallSrcWarHusPopup = fromWhere;
		srcWarHusColumTM = new TreeMap<>();
		srcWarJointextBox = "";
		if(fromWhere.equals("SrcWarHusJoin")){
			
		HeirarchyDataBean hdb = (HeirarchyDataBean)selectedsrcAndWHJoinsAL.get(0);
		srcWarHusColumTM.putAll(hdb.getSrcColumnTM());
		srcWarJointextBox = hdb.getSourcecolumnName();
		}else if(fromWhere.equals("ConfSrcWarHusJoins")){
			fromFactPopup = "block";
			HeirarchyDataBean hdb = (HeirarchyDataBean)selectedsrcAndWHJoinsAL.get(0);
			srcWarHusColumTM.putAll(copyColumnNameTM);
			srcWarJointextBox = hdb.getTarColumnName();
		}else if(fromWhere.equals("ConfWarHusLoad")){
			
			HeirarchyDataBean hdb = (HeirarchyDataBean)selectedMeasureList.get(0);
			srcWarHusColumTM.putAll(hdb.getSrcColumTM());
			srcWarJointextBox = hdb.getSourceTable();
		}else if(fromWhere.equals("ConfWarHusLoadTarClumn")){
			
			HeirarchyDataBean hdb = (HeirarchyDataBean)selectedMeasureList.get(0);
			srcWarHusColumTM.putAll(hdb.getTargetColumn());
			srcWarJointextBox = hdb.getSelectedTargetColumn();
		}else if(fromWhere.equals("DirectSQL")){
			Connection Con = Globals.getDBConnection("DW_Connection");
			fromFactPopup = "block";
			HierarchydataBean hdb = (HierarchydataBean)selectedWhereClauseList.get(0);
			srcWarHusColumTM = columnName1("", hdb.tarTableName, Con);
			
		}else if (fromWhere.equalsIgnoreCase("ConfWarHusLoad4Src")) {
			HeirarchyDataBean hdb = (HeirarchyDataBean)selectedMeasureList.get(0);
//			srcWarHusColumTM.putAll(hdb.getSrcColumnTM());
			
			if(hdb.getAdditionalFunction4SrcColumn().equals("")){
				additionalFunc4Column = hdb.selectedSrcTable+"."+hdb.sourceTable;
			}else{
				additionalFunc4Column = hdb.getAdditionalFunction4SrcColumn();
			}
			
		}else if (fromWhere.equalsIgnoreCase("ConfWarHusLoad4Tar")) {
			HeirarchyDataBean hdb = (HeirarchyDataBean)selectedMeasureList.get(0);
			HierarchydataBean hdb1 = (HierarchydataBean) srcandTarFactTablesSelectedAl.get(0);
//			srcWarHusColumTM.putAll(hdb.getSrcColumnTM());
			System.out.println("hdb.getAdditionalFunction4TarColumn()===>"+hdb.getAdditionalFunction4TarColumn());
			if(hdb.getAdditionalFunction4TarColumn().equals("")){
				additionalFunc4Column = hdb1.tarFactTables+"."+hdb.selectedTargetColumn;
			}else{
				additionalFunc4Column = hdb.getAdditionalFunction4TarColumn();
			}
			
		}else if (fromWhere.equalsIgnoreCase("SrcWarHusJoin4Func")) {
			HeirarchyDataBean hdb = (HeirarchyDataBean)selectedsrcAndWHJoinsAL.get(0);
			if(hdb.getAdditionalFunction4SrcWarColumn().equals("")){
				additionalFunc4Column = hdb.sourceTableName+"."+hdb.sourcecolumnName;
			}else{
				additionalFunc4Column = hdb.getAdditionalFunction4SrcWarColumn();
			}
		}else if (fromWhere.equalsIgnoreCase("TarWarHusJoin4Func")) {
			HeirarchyDataBean hdb = (HeirarchyDataBean)selectedsrcAndWHJoinsAL.get(0);
			if(hdb.getAdditionalFunction4TarWarColumn().equals("")){
				additionalFunc4Column = hdb.tarTableName+"."+hdb.tarColumnName;
			}else{
				additionalFunc4Column = hdb.getAdditionalFunction4TarWarColumn();
			}
		}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void addColumns2Table(String saveFrom){
		try{
		if(saveFrom.equals("SrcWarHusJoin")){
		HeirarchyDataBean hdb = (HeirarchyDataBean)selectedsrcAndWHJoinsAL.get(0);
		hdb.setSourcecolumnName(String.valueOf(chooseColumn4Join.get(0)));
		}else if(saveFrom.equals("ConfSrcWarHusJoins")){
			HeirarchyDataBean hdb = (HeirarchyDataBean)selectedsrcAndWHJoinsAL.get(0);
			hdb.setTarColumnName(String.valueOf(chooseColumn4Join.get(0)));
			}else if(saveFrom.equals("ConfWarHusLoad")){
			HeirarchyDataBean hdb = (HeirarchyDataBean)selectedMeasureList.get(0);
			hdb.setSourceTable(String.valueOf(chooseColumn4Join.get(0)));
		}else if(saveFrom.equals("ConfWarHusLoadTarClumn")){
			HeirarchyDataBean hdb = (HeirarchyDataBean)selectedMeasureList.get(0);
			hdb.setSelectedTargetColumn(String.valueOf(chooseColumn4Join.get(0)));
		}else if(saveFrom.equals("DirectSQL")){

//			Connection Con = Globals.getDBConnection("DW_Connection");  ?????? comment by jayaramu 19MAY14
//			TreeMap srcColumnTM = new TreeMap<>();
//			TreeMap tableNameTM = new TreeMap<>();
//			String tableName = "";
//			String columnName = "";
//			String operator = "";
//			String targetTableName = "";
//			for(int i=0;i<whereClauseList.size();i++){
//				if(whereClauseList.get(i).equals(selectedWhereClauseList.get(0))){
//					System.out.println("selected is removed");
//					HierarchydataBean hdb1 = (HierarchydataBean) whereClauseList.get(i);
//					tableName = hdb1.selectedTableName;
//					columnName = hdb1.selectedSrcColumn;
//					operator = hdb1.joinColumnOper;
//					targetTableName = hdb1.tarTableName;
//					tableNameTM = hdb1.selectedSrcTableNameTM;
//					srcColumnTM = hdb1.selectedSrcColumnTM;
//					
//					whereClauseList.remove(i);
//					break;
//				}
//			}
//			HierarchydataBean hdb = new HierarchydataBean(tableName,tableNameTM,columnName,srcColumnTM,operator,joinOperTM,targetTableName,srcWarJointextBox);
//			whereClauseList.add(hdb); ?????? comment by jayaramu 19MAY14
		if(selectedWhereClauseList != null && !selectedWhereClauseList.isEmpty()){
			HierarchydataBean hdb = (HierarchydataBean) selectedWhereClauseList.get(0);
			hdb.setTarColumnName(String.valueOf(chooseColumn4Join.get(0)));
		}
		}else if(saveFrom.equalsIgnoreCase("ConfWarHusLoad4Src")){
			HeirarchyDataBean hdb = (HeirarchyDataBean)selectedMeasureList.get(0);
			hdb.setAdditionalFunction4SrcColumn(additionalFunc4Column);
			if(additionalFunc4Column.equals(hdb.selectedSrcTable+"."+hdb.sourceTable)){
				hdb.setSrcImageName4measure("choseTablecopy.png");
			}else{
				hdb.setSrcImageName4measure("chooseTablecopy.png");
			}
		}else if (saveFrom.equalsIgnoreCase("ConfWarHusLoad4Tar")) {
			HeirarchyDataBean hdb = (HeirarchyDataBean)selectedMeasureList.get(0);
			HierarchydataBean hd = (HierarchydataBean)srcandTarFactTablesSelectedAl.get(0);
			hdb.setAdditionalFunction4TarColumn(additionalFunc4Column);
			if(additionalFunc4Column.equals(hd.tarFactTables+"."+hdb.selectedTargetColumn)){
				hdb.setTarImageName4measure("choseTablecopy.png");
			}else{
				hdb.setTarImageName4measure("chooseTablecopy.png");
			}
		}else if (saveFrom.equalsIgnoreCase("SrcWarHusJoin4Func")) {
			HeirarchyDataBean hdb = (HeirarchyDataBean)selectedsrcAndWHJoinsAL.get(0);
			hdb.setAdditionalFunction4SrcWarColumn(additionalFunc4Column);
			if(additionalFunc4Column.equals("") || additionalFunc4Column.equals(hdb.sourceTableName+"."+hdb.sourcecolumnName)){
				hdb.srcImageName4Whar = "choseTablecopy.png";
			}else{
				hdb.srcImageName4Whar = "chooseTablecopy.png";
			}
		}else if (saveFrom.equalsIgnoreCase("TarWarHusJoin4Func")) {
			HeirarchyDataBean hdb = (HeirarchyDataBean)selectedsrcAndWHJoinsAL.get(0);
			hdb.setAdditionalFunction4TarWarColumn(additionalFunc4Column);
			if(additionalFunc4Column.equals("") || additionalFunc4Column.equals(hdb.tarTableName+"."+hdb.tarColumnName)){
				hdb.tarImageName4Whar = "choseTablecopy.png";
			}else{
				hdb.tarImageName4Whar = "chooseTablecopy.png";
			}
			
		}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	//Start code change Jayaramu 10MAY14 for get existing tables from factpopup and display it on selectTablePopup  
	public TreeMap readTableNameFromFactPopUP(){
		System.out.println("Entering: "
				+ new Exception().getStackTrace()[0].getClassName() + "."
				+ new Exception().getStackTrace()[0].getMethodName());
	
		TreeMap tableNames = new TreeMap<>();
		try{
		HierarchydataBean hdb;
		HeirarchyDataBean hryDB;
	
		if(srcandTarFactTablesAl != null){ 
			//Get source and target table from Source Transaction Table and Target Facts
		for(int i=0;i<srcandTarFactTablesAl.size();i++){
			hdb = (HierarchydataBean)srcandTarFactTablesAl.get(i);
			String sourceTableName = hdb.getSrcFactTable();
			String tarTableName = hdb.getTarFactTables();
			System.out.println("Source Transaction Table and Target Facts: Source Table Name: "+sourceTableName+" and Target Table Name: "+tarTableName);
			if(sourceTableName != null && !sourceTableName.equals("")){
				tableNames.put(sourceTableName, sourceTableName);
			}
			if(tarTableName != null && !tarTableName.equals("")){
			tableNames.put(tarTableName, tarTableName);
			}
		}
		}
		
		if(srcAndWHJoinsAL != null){
			//Get source and target table from Configure Source and Warehouse Joins
			String subSourceTableName = "";
			String joinColumnOper = "";
			String tarColumnName = "";
			String joinTarTableName = "";
		for(int i=0;i<srcAndWHJoinsAL.size();i++){
			hryDB = (HeirarchyDataBean)srcAndWHJoinsAL.get(i);
			subSourceTableName = hryDB.getSubSourceTableName();
			joinColumnOper = hryDB.getJoinColumnOper();
			tarColumnName = hryDB.getTarColumnName();
			joinTarTableName = hryDB.getTarTableName();
			System.out.println("Configure Source and Warehouse Joins: Source Table Name: "+subSourceTableName+" and Target Table Name: "+joinTarTableName);		
			if(subSourceTableName != null && !subSourceTableName.equals("")){
				tableNames.put(subSourceTableName, subSourceTableName);
			}
			if(joinColumnOper==null && !tarColumnName.equals("") && !joinTarTableName.equals("")){
					tableNames.put(joinTarTableName, joinTarTableName);
			}else if(!joinColumnOper.equals("BETWEEN") && !tarColumnName.equals("") && !joinTarTableName.equals("")){
				tableNames.put(joinTarTableName, joinTarTableName);
			}
		}
		}
		}catch (Exception e) {
		// TODO: handle exception
		e.printStackTrace();
		}
		System.out.println("Exiting: "
				+ new Exception().getStackTrace()[0].getClassName() + "."
				+ new Exception().getStackTrace()[0].getMethodName());
	
	return tableNames;
	}
	//End code change Jayaramu 10MAY14
	TreeMap selectedTableNameTM4DirectSQL = new TreeMap<>();



	public TreeMap getSelectedTableNameTM4DirectSQL() {
		return selectedTableNameTM4DirectSQL;
	}
	public void setSelectedTableNameTM4DirectSQL(
			TreeMap selectedTableNameTM4DirectSQL) {
		this.selectedTableNameTM4DirectSQL = selectedTableNameTM4DirectSQL;
	}
	
	String[] selectedTableName4DirectSQL;

	public String[] getSelectedTableName4DirectSQL() {
		return selectedTableName4DirectSQL;
	}
	public void setSelectedTableName4DirectSQL(String[] selectedTableName4DirectSQL) {
		this.selectedTableName4DirectSQL = selectedTableName4DirectSQL;
	}
	
	TreeMap selectedColumnNameTM4DirectSQL = new TreeMap<>();
	
	public TreeMap getSelectedColumnNameTM4DirectSQL() {
		return selectedColumnNameTM4DirectSQL;
	}
	public void setSelectedColumnNameTM4DirectSQL(
			TreeMap selectedColumnNameTM4DirectSQL) {
		this.selectedColumnNameTM4DirectSQL = selectedColumnNameTM4DirectSQL;
	}
	String[] selectedcolumnName4DirectSQL;
	
	public String[] getSelectedcolumnName4DirectSQL() {
		return selectedcolumnName4DirectSQL;
	}
	public void setSelectedcolumnName4DirectSQL(
			String[] selectedcolumnName4DirectSQL) {
		this.selectedcolumnName4DirectSQL = selectedcolumnName4DirectSQL;
	}
	TreeMap columnNameTM4DirectSQL = new TreeMap<>();
	public TreeMap getColumnNameTM4DirectSQL() {
		return columnNameTM4DirectSQL;
	}
	public void setColumnNameTM4DirectSQL(TreeMap columnNameTM4DirectSQL) {
		this.columnNameTM4DirectSQL = columnNameTM4DirectSQL;
	}
	Hashtable selectColNameHT = new Hashtable<>();
	Hashtable SelectTableNameHT = new Hashtable<>();
	
	public void moveForward4DrectSQL(String[] selectedTableColumnName,String typeOfInsert) {
		try{
			System.out.println("selectedTableName.size()"+selectedTableColumnName.length+"selectedTableColumnName"+selectedTableColumnName[0]);
			
			if(selectedTableColumnName.length==0){
				
			}else{
				joinOperTM.put("=", "=");
				joinOperTM.put("BETWEEN", "BETWEEN");
				joinOperTM.put("<", "<");
				joinOperTM.put(">", ">");
				joinOperTM.put("!=", "!=");
				joinOperTM.put("<=", "<=");
				joinOperTM.put(">=", ">=");
				joinOperTM.put("LIKE", "LIKE");
				joinOperTM.put("IN", "IN");
			if(typeOfInsert.equalsIgnoreCase("table")){
			for(int i=0;i<selectedTableColumnName.length;i++){
				System.out.println("selectedTableName"+selectedTableColumnName[i]);
				selectedTableNameTM4DirectSQL.put(selectedTableColumnName[i], selectedTableColumnName[i]);
				tableNameTM.remove(selectedTableColumnName[i]);
				tableNameTM1.put(selectedTableColumnName[i], selectedTableColumnName[i]);
				if(!SelectTableNameHT.containsValue(selectedTableColumnName[i]))
				SelectTableNameHT.put(SelectTableNameHT.size(), selectedTableColumnName[i]);
			}
			}else if(typeOfInsert.equalsIgnoreCase("CodeColumn")){
				if(selectedTableColumnName.length==1)
				for(int i=0;i<selectedTableColumnName.length;i++){
					System.out.println("selectedTableName"+selectedTableColumnName[i]);
					selectedColumnNameTM4DirectSQL.put(selectedTableColumnName[i], selectedTableColumnName[i]);
					columnNameTM4DirectSQL.remove(selectedTableColumnName[i]);
					selectColNameHT.put(0, selectedTableName4DirectSQL[0]+"."+selectedTableColumnName[i]);
				}
			}else if(typeOfInsert.equalsIgnoreCase("NameColumn")){
				if(selectedTableColumnName.length==1){
				for(int i=0;i<selectedTableColumnName.length;i++){
					System.out.println("selectedTableName"+selectedTableColumnName[i]);
					selectedColumnNameTM4DirectSQL.put(selectedTableColumnName[i], selectedTableColumnName[i]);
					columnNameTM4DirectSQL.remove(selectedTableColumnName[i]);
					selectColNameHT.put(1, selectedTableName4DirectSQL[0]+"."+selectedTableColumnName[i]);
				}
				constructingSQL("Select");
				}
			}
			}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	TreeMap joinOperTM = new TreeMap<>();
	public void deleteColumnTable(String[] selectedTableColumnName,String typeOfInsert) {
		try{
			System.out.println("typeOfInsert====>"+typeOfInsert);
			
			if(typeOfInsert.equalsIgnoreCase("Table")){
				for(int i=0;i<selectedTableColumnName.length;i++){
					selectedTableNameTM4DirectSQL.remove(selectedTableColumnName[i]);
				}
			}else if(typeOfInsert.equalsIgnoreCase("Where")){
				System.out.println("selectedWhereClauseList====>"+selectedWhereClauseList.size());
				for(int i=0;i<selectedWhereClauseList.size();i++){
					HierarchydataBean hdb = (HierarchydataBean) selectedWhereClauseList.get(i);
					whereClauseList.remove(hdb);
				}
			}else{
				for(int i=0;i<selectedTableColumnName.length;i++){
					selectedColumnNameTM4DirectSQL.remove(selectedTableColumnName[i]);
				}
			}
			
			constructingSQL("FULL");
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	ArrayList whereClauseList = new ArrayList<>();
	public ArrayList getWhereClauseList() {
		return whereClauseList;
	}
	public void setWhereClauseList(ArrayList whereClauseList) {
		this.whereClauseList = whereClauseList;
	}

	ArrayList selectedWhereClauseList = new ArrayList<>();
	String valiMsg = "";
	public String getValiMsg() {
		return valiMsg;
	}
	public void setValiMsg(String valiMsg) {
		this.valiMsg = valiMsg;
	}
	public ArrayList getSelectedWhereClauseList() {
		return selectedWhereClauseList;
	}
	public void setSelectedWhereClauseList(ArrayList selectedWhereClauseList) {
		this.selectedWhereClauseList = selectedWhereClauseList;
	}
	public void addRows4WhereClause(){
		try{
			String selTableName = null;
			String selSrcColumn = null;
			String operator = null;
			String tarTableName = "";
			String tarColumnNmae= "";
			valiMsg = "";
			if(whereClauseList != null && !whereClauseList.isEmpty()){
				for(int i=0;i<whereClauseList.size();i++){
					HierarchydataBean hdb = (HierarchydataBean)whereClauseList.get(i);
					selTableName = hdb.getSelectedTableName();
					selSrcColumn = hdb.getSelectedSrcColumn();
					operator = hdb.getJoinColumnOper();
					tarTableName = hdb.getTarTableName();
					tarColumnNmae= hdb.getTarColumnName();
					
					if(selTableName == null){
						valiMsg = "Please select source table.";
						return;
					}else if(selSrcColumn == null){
						valiMsg = "Please select column name.";
						return;
					}else if(operator == null){
						valiMsg = "Please select operator.";
						return;
					}else if(tarTableName.equals("")){
						valiMsg = "Please select target table.";
						return;
					}
					
				}
			}
			
			joinOperTM.put("=", "=");
			joinOperTM.put("BETWEEN", "BETWEEN");
			joinOperTM.put("<", "<");
			joinOperTM.put(">", ">");
			joinOperTM.put("!=", "!=");
			joinOperTM.put("<=", "<=");
			joinOperTM.put(">=", ">=");
			joinOperTM.put("LIKE", "LIKE");
			joinOperTM.put("IN", "IN");
			
//				constructingSQL("Where");
				HierarchydataBean hdb1 = new HierarchydataBean("",selectedTableNameTM4DirectSQL,"",new TreeMap<>(),"",joinOperTM,"","");
				whereClauseList.add(hdb1);
			
//			}else{
//				HierarchydataBean hdb1 = new HierarchydataBean("",selectedTableNameTM4DirectSQL,"",new TreeMap<>(),"",joinOperTM,"","");
//				whereClauseList.add(hdb1);
//			}
			
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public void displayColumnName4SeleTable(String selectedTablename){
		try{
			Connection Con = Globals.getDBConnection("DW_Connection");
			TreeMap srcColumnTM = columnName1("", selectedTablename, Con);
			HierarchydataBean hdb1 = (HierarchydataBean) selectedWhereClauseList.get(0);
			hdb1.selectedSrcColumnTM = new TreeMap<>();
			hdb1.selectedSrcColumnTM.putAll(srcColumnTM);
//			String tableName = ""; //code comment by jayaramu 19MAY14
//			for(int i=0;i<whereClauseList.size();i++){
//				System.out.println("1===>"+whereClauseList.get(i).equals(selectedWhereClauseList.get(0)));
//				if(whereClauseList.get(i).equals(selectedWhereClauseList.get(0))){
//					System.out.println("selected is removed");
//					HierarchydataBean hdb1 = (HierarchydataBean) whereClauseList.get(i);
//					tableName = hdb1.selectedTableName;
//					whereClauseList.remove(i);
//				}
//			}
//			HierarchydataBean hdb = new HierarchydataBean(tableName,selectedTableNameTM4DirectSQL,"",srcColumnTM,"",joinOperTM,"","");
//			whereClauseList.add(hdb);
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	String directSQL  = "";
	public String getDirectSQL() {
		return directSQL;
	}
	public void setDirectSQL(String directSQL) {
		this.directSQL = directSQL;
	}
	
	public String constructingSQL(String sqlClause) {
	try{
		if(sqlClause.equalsIgnoreCase("Select")){
			directSQL = "Select ";
			for(int i=0;i<selectColNameHT.size();i++){
				directSQL = directSQL+String.valueOf(selectColNameHT.get(i))+", ";
			}
			directSQL = directSQL.substring(0, directSQL.length()-2);
			directSQL = directSQL+" From ";
			System.out.println("SelectTableNameHT===>"+SelectTableNameHT);
			
			for(int i=0;i<SelectTableNameHT.size();i++){
				directSQL = directSQL+String.valueOf(SelectTableNameHT.get(i))+", ";
			}
			directSQL = directSQL.substring(0, directSQL.length()-2);
		}else if(sqlClause.equalsIgnoreCase("Where")){
			if(whereClauseList.size()>0)
//			directSQL = directSQL+" Where ";
			for(int i=0;i<whereClauseList.size();i++){
				if(whereClauseList.size() == 1){
					
				}else if(whereClauseList.size()>1){
					 i = whereClauseList.size()-1;
					 directSQL = directSQL+" "+selectedJoinType4Direct+" ";
				}
				HierarchydataBean hdb = (HierarchydataBean) whereClauseList.get(i);
				System.out.println("hdb.getTarColumnName()===>"+hdb.getTarColumnName());
				if(hdb.getTarColumnName().equals("")){
					if(hdb.joinColumnOper.equalsIgnoreCase("in")){
						String sql =hdb.selectedTableName+"."+hdb.selectedSrcColumn+" "+hdb.joinColumnOper+" ("+
								hdb.tarTableName+")";
							directSQL = directSQL+sql;
						
					}else{
						String sql = hdb.selectedTableName+"."+hdb.selectedSrcColumn+" "+hdb.joinColumnOper+" "+
								hdb.tarTableName;
						directSQL = directSQL+sql;
					}
				}else{
					if(hdb.joinColumnOper.equalsIgnoreCase("Between")){
						String sql = hdb.selectedTableName+"."+hdb.selectedSrcColumn+" "+hdb.joinColumnOper+" "+
								hdb.tarTableName+" and "+hdb.tarColumnName;
						
						directSQL = directSQL+sql;
						if(whereClauseList.size()==1){
							directSQL = directSQL+" Where "+sql;
						}else{
							directSQL = directSQL+sql;
						}
					}else {
					String sql = hdb.selectedTableName+"."+hdb.selectedSrcColumn+" "+hdb.joinColumnOper+" "+
							hdb.tarTableName+"."+hdb.tarColumnName;
					System.out.println("sql===>"+sql);
					
					if(whereClauseList.size()==1){
						directSQL = directSQL+" Where "+sql;
					}else{
						directSQL = directSQL+sql;
					}
					}
				}
				if(i==whereClauseList.size()-1){
					directSQL = directSQL;
				}else{
					directSQL = directSQL+" "+selectedJoinType4Direct+" ";
				}
				
			}
		}else if(sqlClause.equalsIgnoreCase("Full")){
			directSQL ="";
			directSQL = "Select ";
			for(int i=0;i<selectColNameHT.size();i++){
				directSQL = directSQL+String.valueOf(selectColNameHT.get(i))+", ";
			}
			directSQL = directSQL.substring(0, directSQL.length()-2);
			directSQL = directSQL+" From ";
			for(int i=0;i<SelectTableNameHT.size();i++){
				
				directSQL = directSQL+String.valueOf(SelectTableNameHT.get(i))+", ";
			}
			directSQL = directSQL.substring(0, directSQL.length()-2);
			if(whereClauseList != null && !whereClauseList.isEmpty()){
			
			}
			int j=0;
for(int i=0;i<whereClauseList.size();i++){
				
				HierarchydataBean hdb = (HierarchydataBean) whereClauseList.get(i);

				String selTableName = hdb.getSelectedTableName();
				String selSrcColumn = hdb.getSelectedSrcColumn();
				String operator = hdb.getJoinColumnOper();
				String tarTableName = hdb.getTarTableName();
				String tarColumnNmae= hdb.getTarColumnName();
				
				if(selTableName == null || selSrcColumn == null || selSrcColumn == null || operator == null || tarTableName.equals("")){
					j++;
					
				}
}

if(whereClauseList.size() != j){
	directSQL = directSQL+" Where ";
}

			for(int i=0;i<whereClauseList.size();i++){
				
				HierarchydataBean hdb = (HierarchydataBean) whereClauseList.get(i);

				String selTableName = hdb.getSelectedTableName();
				String selSrcColumn = hdb.getSelectedSrcColumn();
				String operator = hdb.getJoinColumnOper();
				String tarTableName = hdb.getTarTableName();
				String tarColumnNmae= hdb.getTarColumnName();
				
				if(selTableName == null || selSrcColumn == null || selSrcColumn == null || operator == null || tarTableName.equals("")){
					continue;
				}
				
				
				System.out.println("hdb.getTarColumnName()===>"+hdb.joinColumnOper);
				System.out.println("hdb===>"+hdb);
				if(hdb.getTarColumnName().equals("")){
					if(hdb.joinColumnOper.equalsIgnoreCase("in")){
						directSQL = directSQL+hdb.selectedTableName+"."+hdb.selectedSrcColumn+" "+hdb.joinColumnOper+" ("+
								hdb.tarTableName+")";
					}else{
						directSQL = directSQL+hdb.selectedTableName+"."+hdb.selectedSrcColumn+" "+hdb.joinColumnOper+" "+
								hdb.tarTableName;
					}
				}else{
					if(hdb.joinColumnOper.equalsIgnoreCase("Between")){
						String sql = hdb.selectedTableName+"."+hdb.selectedSrcColumn+" "+hdb.joinColumnOper+" "+
								hdb.tarTableName+" and "+hdb.tarColumnName;
						
						directSQL = directSQL+sql;
					}else {
					directSQL = directSQL+hdb.selectedTableName+"."+hdb.selectedSrcColumn+" "+hdb.joinColumnOper+" "+
									hdb.tarTableName+"."+hdb.tarColumnName;
					}
				}
				if(i==whereClauseList.size()-1){
					directSQL = directSQL;
				}else{
					HierarchydataBean hdb1 = (HierarchydataBean) whereClauseList.get(i+1);
					selTableName = hdb1.getSelectedTableName();
					selSrcColumn = hdb1.getSelectedSrcColumn();
					operator = hdb1.getJoinColumnOper();
					tarTableName = hdb1.getTarTableName();
					tarColumnNmae= hdb1.getTarColumnName();
					
					if(selTableName == null || selSrcColumn == null || selSrcColumn == null || operator == null || tarTableName.equals("")){
						continue;
					}
					
					directSQL = directSQL+" "+selectedJoinType4Direct+" ";
				}
			}
		}
//		customDirectSql = directSQL; 
	}catch (Exception e) {
		// TODO: handle exception
		e.printStackTrace();
	}
	return directSQL;
	}
	boolean customDirectSql = true;
	public boolean isCustomDirectSql() {
		return customDirectSql;
	}
	public void setCustomDirectSql(boolean customDirectSql) {
		this.customDirectSql = customDirectSql;
	}
	public void customDirectSQL() {
		try{
			enable4DirectSQL = true;
			customDirectSql = false;
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	boolean enable4DirectSQL = false;
	
	
	public boolean isEnable4DirectSQL() {
		return enable4DirectSQL;
	}
	public void setEnable4DirectSQL(boolean enable4DirectSQL) {
		this.enable4DirectSQL = enable4DirectSQL;
	}
	String name4DirectSQL = "";
	public String getName4DirectSQL() {
		return name4DirectSQL;
	}
	public void setName4DirectSQL(String name4DirectSQL) {
		this.name4DirectSQL = name4DirectSQL;
	}

	String value4DirectSQL = "";
	public String getValue4DirectSQL() {
		return value4DirectSQL;
	}
	public void setValue4DirectSQL(String value4DirectSQL) {
		this.value4DirectSQL = value4DirectSQL;
	}
	public void enableSQL(){
		try{
			FacesContext ctx = FacesContext.getCurrentInstance();
			ExternalContext extContext = ctx.getExternalContext();
			Map sessionMap = extContext.getSessionMap();
			HierarchyBean hryb = (HierarchyBean) sessionMap.get("hierarchyBean");
			PropUtil prop = new PropUtil();
			String xmlDir = "";
			String hierLevelXMLFile = "";
			xmlDir = prop.getProperty("HIERARCHY_XML_DIR");
			hierLevelXMLFile = prop.getProperty("HIERARCHY_XML_FILE");
			enable4DirectSQL = false;
			Document doc = Globals.openXMLFile(xmlDir, hierLevelXMLFile);
			String levelID = hryb.selectedRows.get(0).getID();
			String nodeName = hryb.selectedRows.get(0).getLevelNode();	
			Node levelNode = Globals.getNodeByAttrVal(doc, nodeName, "ID", levelID);
			directSQL = Globals.getAttrVal4AttrName(levelNode, "Normal_SQL");
			customDirectSql = true;
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	public void directSQL(){
		try{
			flag4AddData = "fromData";
			selectedTableNameTM4DirectSQL = new TreeMap<>();
			columnNameTM4DirectSQL = new TreeMap<>();
			selectedColumnNameTM4DirectSQL = new TreeMap<>();
			whereClauseList = new ArrayList<>();
			directSQL = "";
			data4DirectSQLAL = new ArrayList<>();
			valiMsg = "";
			setDispTable4Data("false");
			
			selectedsegment1 = "";
			selectedTableName4DirectSQL = new String[tableNameTM.size()];
			columnName1 = new String[200];
			selectedcolumnName4DirectSQL = new String[2];
			tableName = new String[tableNameTM.size()];
			
			
			SelectTableNameHT = new Hashtable<>();
			selectColNameHT = new Hashtable<>();
			
			
			FacesContext cxt = FacesContext.getCurrentInstance();
			ExternalContext extContext = cxt.getExternalContext();
			Map sessionmap = extContext.getSessionMap();
			HierarchyBean hb = (HierarchyBean) sessionmap.get("hierarchyBean");
			if(hb.codecombinationFlag.equalsIgnoreCase("DontCreateCodeCombination")){
				hb.disableOption4hier = true;
				hb.setCodeCombinationflag4Data("");
			}else if(hb.codecombinationFlag.equalsIgnoreCase("CreateCodeCombinationInDim")){
				hb.disableOption4hier = false;
				hb.setCodeCombinationflag4Data("CreateCodeCombinationAtLeaf");
			}else{
				hb.disableOption4hier = true;
				hb.setCodeCombinationflag4Data("DuringFactGen");
			}
//			customDirectSql = true;
//			dispTable4Data = "none";
			System.out.println("dispTable4Data===>"+dispTable4Data);
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	ArrayList data4DirectSQLAL = new ArrayList<>();
	public ArrayList getData4DirectSQLAL() {
		return data4DirectSQLAL;
	}
	public void setData4DirectSQLAL(ArrayList data4DirectSQLAL) {
		this.data4DirectSQLAL = data4DirectSQLAL;
	}

	ArrayList selecteddata4DirectSQLAL = new ArrayList<>();
	public ArrayList getSelecteddata4DirectSQLAL() {
		return selecteddata4DirectSQLAL;
	}
	public void setSelecteddata4DirectSQLAL(ArrayList selecteddata4DirectSQLAL) {
		this.selecteddata4DirectSQLAL = selecteddata4DirectSQLAL;
	}
	public void addSQLasData(){
		try{
			data4DirectSQLAL = new ArrayList<>();
			dispTable4Data = "block";
			Connection con = Globals.getDBConnection("DW_Connection");
			HierarchydataBean hdb = null;
			
			flag4AddData = "fromData4DirectSQL";
			if(!directSQL.equals("")){
				System.out.println("directSQL===>"+directSQL);
				PreparedStatement pre = con.prepareStatement(directSQL);
				ResultSet rs = pre.executeQuery();
				while (rs.next()) {
					System.out.println("rs.getString(1), rs.getString(1)"+rs.getString(1)+"--"+ rs.getString(2));
					hdb = new HierarchydataBean(rs.getString(1), rs.getString(2));
					data4DirectSQLAL.add(hdb);
				}	
				
				
			}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	String dispTable4Data = "none";
	public String getDispTable4Data() {
		return dispTable4Data;
	}
	public void setDispTable4Data(String dispTable4Data) {
		this.dispTable4Data = dispTable4Data;
	}
	
	
	public static void main(String[] args) throws Exception {
		
	
	}

	
	
	
}
