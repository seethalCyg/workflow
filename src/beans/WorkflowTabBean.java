package beans;


import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.TreeMap;
import org.json.JSONException;
import org.json.JSONObject;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.print.Doc;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.swing.tree.TreeNode;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import managers.LoginProcessManager;
import managers.RulesManager;
import managers.WorkflowManager;
import model.ReportTree;
import model.RuleAttributeDataModel;

import org.apache.commons.io.FileUtils;

import org.richfaces.event.FileUploadEvent;
import org.richfaces.model.UploadedFile;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.itextpdf.text.pdf.PdfStructTreeController.returnType;
import com.novell.ldap.LDAPConnection;
import com.novell.ldap.LDAPEntry;
import com.novell.ldap.LDAPException;
import com.novell.ldap.LDAPSearchResults;
import com.sun.org.apache.regexp.internal.REUtil;

import jdk.nashorn.api.scripting.JSObject;
import rootLevel.dataProcess;
import utils.Globals;
import utils.Inventory;
import utils.PropUtil;

@ManagedBean(name = "workflowTabBean")
@SessionScoped
public class WorkflowTabBean implements Serializable {



	//ArrayList targetTable = new ArrayList<>();
	private List<WorkflowDataBean> WorkflowDataBeanAL = new ArrayList<WorkflowDataBean>();
	//private List<WorkflowDataBean> WorkflowDataBeanAL2 = new ArrayList<WorkflowDataBean>();
	private List<WorkflowDataBean> WorkflowDataBeanAL2 = new ArrayList<WorkflowDataBean>();
	
	

	
	public ArrayList documentDetailsAL = new ArrayList<>();
	public ArrayList<WorkflowDataBean> calendarDetailsAL = new ArrayList<>();
	
	public ArrayList documentDetailsAL1 = new ArrayList<>();
	
	
	public ArrayList getDocumentDetailsAL1() {
		return documentDetailsAL1;
	}


	public void setDocumentDetailsAL1(ArrayList documentDetailsAL1) {
		this.documentDetailsAL1 = documentDetailsAL1;
	}


	private ArrayList<RuleAttributeDataModel> rulesAttrDetailsAL = new ArrayList<>();
	private Hashtable<String, String> rulesAttrValueHT = new Hashtable<>();
	public ArrayList<WorkflowTabBean> attachdocumentDetailsAL = new ArrayList<>();

	public ArrayList<WorkflowDataBean> timeLineTableAL = new ArrayList<>();

	public ArrayList<WorkflowDataBean> getTimeLineTableAL() {
		return timeLineTableAL;
	}


	public void setTimeLineTableAL(ArrayList<WorkflowDataBean> timeLineTableAL) {
		this.timeLineTableAL = timeLineTableAL;
	}






	public boolean  sendalertStr;
	public boolean  sendalertAfterdeadLineStr;
	public boolean  sendalertsubseqStr;
	public boolean  timelineBoxStr=true;
	public boolean  forEachtimeBoxStr=true;
	public boolean  showHoursBoxStr;
	public boolean  exweekBoxStr;
	public boolean  exholidayBoxStr;
	public String documentTextarea;
	
	public String coloduedate;
	
	
	
	
	
	
	
	
	public String getColoduedate() {
		return coloduedate;
	}


	public void setColoduedate(String coloduedate) {
		this.coloduedate = coloduedate;
	}


	public String getDocumentTextarea() {
		return documentTextarea;
	}


	public void setDocumentTextarea(String documentTextarea) {
		this.documentTextarea = documentTextarea;
	}




	public String attachDocumentMsgstr;
	public String attachDocumentMsgColor;
	public String getAttachDocumentMsgColor() {
		return attachDocumentMsgColor;
	}


	public void setAttachDocumentMsgColor(String attachDocumentMsgColor) {
		this.attachDocumentMsgColor = attachDocumentMsgColor;
	}


	public String getAttachDocumentMsgstr() {
		return attachDocumentMsgstr;
	}


	public void setAttachDocumentMsgstr(String attachDocumentMsgstr) {
		this.attachDocumentMsgstr = attachDocumentMsgstr;
	}






	public String globlworkflowID;
	public String getGloblworkflowID() {
		return globlworkflowID;
	}


	public void setGloblworkflowID(String globlworkflowID) {
		this.globlworkflowID = globlworkflowID;
	}






	public String deadLinebeforeTextBox1Str;
	public String deadLineTextBox1Str;
	public String deadLineTextBox2Str;
	public String deadLineSelectMenuStr;

	public String endDateIdstr; 
	public String  startDateIdstr;
	public String getEndDateIdstr() {
		return endDateIdstr;
	}


	public void setEndDateIdstr(String endDateIdstr) {
		this.endDateIdstr = endDateIdstr;
	}


	public String getStartDateIdstr() {
		return startDateIdstr;
	}


	public void setStartDateIdstr(String startDateIdstr) {
		this.startDateIdstr = startDateIdstr;
	}




	public String timelinedisplay="block";
	public String getTimelinedisplay() {
		return timelinedisplay;
	}


	public void setTimelinedisplay(String timelinedisplay) {
		this.timelinedisplay = timelinedisplay;
	}





	public String choosewfNameStr;
	String[] listWorkflowALsel;
	String serialNo="";
	String calendarFrom="";
	String calendarTo="";

	public String startEffectiveDateIdStr;
	public String endEffectiveDateIdStr;

	public String getStartEffectiveDateIdStr() {
		return startEffectiveDateIdStr;
	}


	public void setStartEffectiveDateIdStr(String startEffectiveDateIdStr) {
		this.startEffectiveDateIdStr = startEffectiveDateIdStr;
	}


	public String getEndEffectiveDateIdStr() {
		return endEffectiveDateIdStr;
	}


	public void setEndEffectiveDateIdStr(String endEffectiveDateIdStr) {
		this.endEffectiveDateIdStr = endEffectiveDateIdStr;
	}







	public String getSerialNo() {
		return serialNo;
	}


	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}


	public String getCalendarFrom() {
		return calendarFrom;
	}


	public void setCalendarFrom(String calendarFrom) {
		this.calendarFrom = calendarFrom;
	}


	public String getCalendarTo() {
		return calendarTo;
	}


	public void setCalendarTo(String calendarTo) {
		this.calendarTo = calendarTo;
	}


	public void setCalendarDetailsAL(ArrayList<WorkflowDataBean> calendarDetailsAL) {
		this.calendarDetailsAL = calendarDetailsAL;
	}






	public String[] getListWorkflowALsel() {
		return listWorkflowALsel;
	}


	public void setListWorkflowALsel(String[] listWorkflowALsel) {
		this.listWorkflowALsel = listWorkflowALsel;
	}


	public String getChoosewfNameStr() {
		return choosewfNameStr;
	}


	public void setChoosewfNameStr(String choosewfNameStr) {
		this.choosewfNameStr = choosewfNameStr;
	}


	public boolean isSendalertStr() {
		return sendalertStr;
	}


	public void setSendalertStr(boolean sendalertStr) {
		this.sendalertStr = sendalertStr;
	}


	public boolean isSendalertAfterdeadLineStr() {
		return sendalertAfterdeadLineStr;
	}


	public void setSendalertAfterdeadLineStr(boolean sendalertAfterdeadLineStr) {
		this.sendalertAfterdeadLineStr = sendalertAfterdeadLineStr;
	}


	public boolean isSendalertsubseqStr() {
		return sendalertsubseqStr;
	}


	public void setSendalertsubseqStr(boolean sendalertsubseqStr) {
		this.sendalertsubseqStr = sendalertsubseqStr;
	}


	public boolean isTimelineBoxStr() {
		return timelineBoxStr;
	}


	public void setTimelineBoxStr(boolean timelineBoxStr) {
		this.timelineBoxStr = timelineBoxStr;
	}


	public boolean isForEachtimeBoxStr() {
		return forEachtimeBoxStr;
	}


	public void setForEachtimeBoxStr(boolean forEachtimeBoxStr) {
		this.forEachtimeBoxStr = forEachtimeBoxStr;
	}


	public boolean isShowHoursBoxStr() {
		return showHoursBoxStr;
	}


	public void setShowHoursBoxStr(boolean showHoursBoxStr) {
		this.showHoursBoxStr = showHoursBoxStr;
	}


	public boolean isExweekBoxStr() {
		return exweekBoxStr;
	}


	public void setExweekBoxStr(boolean exweekBoxStr) {
		this.exweekBoxStr = exweekBoxStr;
	}


	public boolean isExholidayBoxStr() {
		return exholidayBoxStr;
	}


	public void setExholidayBoxStr(boolean exholidayBoxStr) {
		this.exholidayBoxStr = exholidayBoxStr;
	}


	public String getDeadLinebeforeTextBox1Str() {
		return deadLinebeforeTextBox1Str;
	}


	public void setDeadLinebeforeTextBox1Str(String deadLinebeforeTextBox1Str) {
		this.deadLinebeforeTextBox1Str = deadLinebeforeTextBox1Str;
	}


	public String getDeadLineTextBox1Str() {
		return deadLineTextBox1Str;
	}


	public void setDeadLineTextBox1Str(String deadLineTextBox1Str) {
		this.deadLineTextBox1Str = deadLineTextBox1Str;
	}


	public String getDeadLineTextBox2Str() {
		return deadLineTextBox2Str;
	}


	public void setDeadLineTextBox2Str(String deadLineTextBox2Str) {
		this.deadLineTextBox2Str = deadLineTextBox2Str;
	}


	public String getDeadLineSelectMenuStr() {
		return deadLineSelectMenuStr;
	}


	public void setDeadLineSelectMenuStr(String deadLineSelectMenuStr) {
		this.deadLineSelectMenuStr = deadLineSelectMenuStr;
	}





	public ArrayList listWorkflowAL = new ArrayList();




	public ArrayList getListWorkflowAL() {
		return listWorkflowAL;
	}


	public void setListWorkflowAL(ArrayList listWorkflowAL) {
		this.listWorkflowAL = listWorkflowAL;
	}


	public String[] getSelectedWorkflowstr() {
		return selectedWorkflowstr;
	}


	public void setSelectedWorkflowstr(String[] selectedWorkflowstr) {
		this.selectedWorkflowstr = selectedWorkflowstr;
	}





	String[] selectedWorkflowstr;

	public String fileNameAttachStr;
	public String fileTypeAttachStr;
	public String fileSizeAttachStr;
	public String fileDateAttachStr;

	private String rbid_metadata_dir = "";
	public String BIMetadataFile = null;
	private String bi_metadata_dir;
	public String primaryFileName;





	public String getPrimaryFileName() {
		return primaryFileName;
	}


	public void setPrimaryFileName(String primaryFileName) {
		this.primaryFileName = primaryFileName;
	}


	public String getDirname() {
		return dirname;
	}


	public String getBi_metadata_dir() {
		return bi_metadata_dir;
	}


	public void setBi_metadata_dir(String bi_metadata_dir) {
		this.bi_metadata_dir = bi_metadata_dir;
	}




	private String dirname = null;


	public void setDirname(String dirname) {
		this.dirname = dirname;
	}




	UploadedFile file;
	private boolean useFlash = false;

	public String getRbid_metadata_dir() {
		return rbid_metadata_dir;
	}


	public void setRbid_metadata_dir(String rbid_metadata_dir) {
		this.rbid_metadata_dir = rbid_metadata_dir;
	}




	public boolean isUseFlash() {
		return useFlash;
	}


	public void setUseFlash(boolean useFlash) {
		this.useFlash = useFlash;
	}





	boolean autoUpload = false;


	public boolean isAutoUpload() {
		return autoUpload;
	}


	public void setAutoUpload(boolean autoUpload) {
		this.autoUpload = autoUpload;
	}

	public UploadedFile getFile() {
		return file;
	}








	public void setFile(UploadedFile file) {
		this.file = file;
	}








	public String getFileNameAttachStr() {
		return fileNameAttachStr;
	}








	public void setFileNameAttachStr(String fileNameAttachStr) {
		this.fileNameAttachStr = fileNameAttachStr;
	}








	public String getFileTypeAttachStr() {
		return fileTypeAttachStr;
	}








	public void setFileTypeAttachStr(String fileTypeAttachStr) {
		this.fileTypeAttachStr = fileTypeAttachStr;
	}








	public String getFileSizeAttachStr() {
		return fileSizeAttachStr;
	}








	public void setFileSizeAttachStr(String fileSizeAttachStr) {
		this.fileSizeAttachStr = fileSizeAttachStr;
	}








	public String getFileDateAttachStr() {
		return fileDateAttachStr;
	}








	public void setFileDateAttachStr(String fileDateAttachStr) {
		this.fileDateAttachStr = fileDateAttachStr;
	}








	public ArrayList getAttachdocumentDetailsAL() {
		return attachdocumentDetailsAL;
	}








	public void setAttachdocumentDetailsAL(ArrayList attachdocumentDetailsAL) {
		this.attachdocumentDetailsAL = attachdocumentDetailsAL;
	}








	public ArrayList getDocumentDetailsAL() {
		return documentDetailsAL;
	}
	public void setDocumentDetailsAL(ArrayList documentDetailsAL) {
		this.documentDetailsAL = documentDetailsAL;
	}
	private String uploadedFolder4Attachment = "";
	private boolean saveBtnDisableFlag = false;
	private boolean startWFBtnDisableFlag = true;
	public boolean createBIMetaXML(String rbid_metadata_dir) {

		System.out.println("Entering: " + new Exception().getStackTrace()[0].getClassName() + "."
				+ new Exception().getStackTrace()[0].getMethodName());

		FileInputStream fis = null;
		FileOutputStream fos = null;
		DocumentBuilderFactory dbf = null;
		DocumentBuilder db = null;
		Document doc = null;
		boolean file_created = false;
		try {

			// Check if BI Metadata File exists
			boolean fileexists = Globals.isFileExists(rbid_metadata_dir, "BI Metadata.xml");

			// If BI Metadata.xml exists already return
			if (fileexists) {
				System.out.println("BI Metadata File " + rbid_metadata_dir + "BI Metadata.xml"
						+ " exists already. Exiting.");
			}
			// If BI Metadata.xml does NOT exists, Create it
			else {
				dbf = DocumentBuilderFactory.newInstance();
				db = dbf.newDocumentBuilder();
				doc = db.newDocument();
				String root = "BI_Metadatas";
				Element rootElement = doc.createElement(root);

				doc.appendChild(rootElement);
				TransformerFactory transformerFactory = TransformerFactory.newInstance();
				Transformer transformer = transformerFactory.newTransformer();
				transformer.setOutputProperty(OutputKeys.INDENT, "yes");
				DOMSource source = new DOMSource(doc);
				fos = new FileOutputStream(rbid_metadata_dir + "BI Metadata.xml");
				StreamResult result = new StreamResult(fos);
				transformer.transform(source, result);
				result.getOutputStream().flush();
				result.getOutputStream().close();
			}

		} catch (Exception e) {
			Globals.getException(e);
			file_created = false;
		} finally {
			try {
				if (fis != null)
					fis.close();
				if (fos != null)
					fis.close();

			} catch (Exception ioe) {
				Globals.getException(ioe);
				file_created = false;
			}
		}

		System.out.println("Exiting: " + new Exception().getStackTrace()[0].getClassName() + "."
				+ new Exception().getStackTrace()[0].getMethodName());
		return file_created;

	}

	public WorkflowTabBean() {

		WorkflowDataBeanAL.add(new WorkflowDataBean("accel mso.docx", "AD", "create/vijay@cygnussoftwares.com", "Review/vijay@cygnussoftwares.com", "07-31-2018", "","08-31-2018","","YTS"));
		WorkflowDataBeanAL.add(new WorkflowDataBean("accel mso1.docx", "AS", "create/vijay1@cygnussoftwares.com", "Review/vijay@cygnussoftwares.com", "04-31-2018", "","08-31-2018","","YTS"));
		WorkflowDataBeanAL2.add(new WorkflowDataBean("accel mso.docx", "AD", "create/vijay@cygnussoftwares.com", "Review/vijay@cygnussoftwares.com", "07-31-2018", "08-31-2018","","YTS"));
		WorkflowDataBeanAL2.add(new WorkflowDataBean("accel mso24.docx", "AS", "create/vijay1@cygnussoftwares.com", "Review/vijay@cygnussoftwares.com", "04-31-2018", "08-31-2018","","YTS"));
		WorkflowDataBeanAL2.add(new WorkflowDataBean("accel mso42.docx", "workflow", "create/vijay2@cygnussoftwares.com", "nill/vijay@cygnussoftwares.com", "05-31-2018", "08-31-2018","","YTS"));
		WorkflowDataBeanAL2.add(new WorkflowDataBean("accel mso43.docx", "win_workflow", "create/vijay3@cygnussoftwares.com", "nill/vijay@cygnussoftwares.com", "06-31-2018", "08-31-2018","","YTS"));
		WorkflowDataBeanAL2.add(new WorkflowDataBean("accel mso45.docx", "finance", "create/vijay4@cygnussoftwares.com", "nill/vijay@cygnussoftwares.com", "07-31-2018", "08-31-2018","","YTS"));
		WorkflowDataBeanAL2.add(new WorkflowDataBean("accel mso53.docx", "ABC finance", "create/vijay5@cygnussoftwares.com", "Review/vijay@cygnussoftwares.com", "07-31-2018", "08-31-2018","","YTS"));

	}






	public WorkflowTabBean(String fileNameAttachStr, String fileTypeAttachStr, 
			String fileSizeAttachStr, String fileDateAttachStr) {
		this.fileNameAttachStr = fileNameAttachStr;
		this.fileTypeAttachStr = fileTypeAttachStr;
		this.fileSizeAttachStr = fileSizeAttachStr;
		this.fileDateAttachStr = fileDateAttachStr;
	}








	public void selListAction() {

		try {
			String stageALStr="";
			ArrayList<WorkflowDataBean> selListActionAL=new ArrayList();
			for(int i=0;i<listWorkflowALsel.length;i++){

				choosewfNameStr = listWorkflowALsel[i];
			}
			System.out.println("choosewfNameStr" +choosewfNameStr);

			FacesContext ctx1 = FacesContext.getCurrentInstance();
			ExternalContext extContext1 = ctx1.getExternalContext();
			Map sessionMap1 = extContext1.getSessionMap();
			LoginBean lgnbn = (LoginBean) sessionMap1.get("loginBean");

			String userCustomerKey=lgnbn.getCustomerKey();
			String userLoginId=lgnbn.getUsername();
			Hashtable wfHT = WorkflowManager.listWorkflowForCustomer(userCustomerKey);
			System.out.println("wfHT :"+wfHT);
			for (int i = 1; i <= wfHT.size(); i++) {

				Hashtable taskHT = (Hashtable)wfHT.get("R"+i);
				String workflowName = (String)taskHT.get("WorkflowName");
				String workflowID   = (String)taskHT.get("WorkflowID");

				globlworkflowID=workflowID;
				if(workflowName.equals(choosewfNameStr)) {

					ArrayList taskAL = (ArrayList)taskHT.get("StageName");

					for(int j=0;j<taskAL.size();j++) {
						stageALStr=(String) taskAL.get(j);
						System.out.println("taskHT J:"+stageALStr);
						WorkflowDataBean timeLineTablevalue = new WorkflowDataBean(j,stageALStr,"","","","");
						selListActionAL.add(timeLineTablevalue);
					}
					PropUtil prop = new PropUtil();
					String HIERARCHY_XML_DIR = prop.getProperty("HIERARCHY_XML_DIR");
					String hierarchyXmlFileName = prop.getProperty("HIERARCHY_XML_FILE");
					Hashtable<String, String> hierarchyAttr = new Hashtable<String, String>();
					LoginProcessManager lpm = new LoginProcessManager();
					Document doc = Globals.openXMLFile(HIERARCHY_XML_DIR, hierarchyXmlFileName);
					Node workFlowN = Globals.getNodeByAttrVal(doc, "Workflow", "Hierarchy_ID", workflowID);
					Hashtable currTeamHT = lpm.retriveStageDetailsFromXML(workFlowN, "1", "");
					System.out.println("currTeamHT -->" +currTeamHT);
					Hashtable currentemplyeeDetailsHT=(Hashtable)currTeamHT.get("EmployeedetHT");
					Hashtable curentPropertiesHT=(Hashtable)currTeamHT.get("PropertiesHT");
					String Properties=(String)curentPropertiesHT.get("Properties");
	        		Hashtable msgHT=(Hashtable)currTeamHT.get("MessagedetHT");
					String finalmsg=(String)msgHT.get("Final");
					if(Properties.equalsIgnoreCase("Serial")) {
						Hashtable ApproversHT=new Hashtable();
						ApproversHT=(Hashtable)currentemplyeeDetailsHT.get(0);
						String member=(String)ApproversHT.get("empName");
						if(member.equalsIgnoreCase(userLoginId)) 
							startWFBtnDisableFlag = false;
					}else {
						for(int j=0;j<currentemplyeeDetailsHT.size();j++) {
							Hashtable ApproversHT=new Hashtable();
							ApproversHT=(Hashtable)currentemplyeeDetailsHT.get(j);
							String member=(String)ApproversHT.get("empName");
							if(member.equalsIgnoreCase(userLoginId)) {
								startWFBtnDisableFlag = false;
								break;
							}
						}
					}
					break;
				}


				
			}
			timeLineTableAL=selListActionAL;
			saveBtnDisableFlag = false;
			rulesAttrDetailsAL.clear();
			System.out.println("wfHT -->" +startWFBtnDisableFlag);

//			getAllAttributesFromWorkflow4AttachDoc(globlworkflowID);
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}


	}




	public void uploadFile(FileUploadEvent event) throws Exception {


		this.dirname = "";


		PropUtil prop = new PropUtil();
		rbid_metadata_dir = prop.getProperty("RBID_METADATA_DIR");
		String bi_metadata_dir = prop.getProperty("BI_METADATA_DIR");
//		createBIMetaXML(rbid_metadata_dir);
		System.out.println("rbid_metadata_dir: " + rbid_metadata_dir);
		// String target = rbid_metadata_dir;
		File sourceFile = null;
		File targetFile = null;
		String infile = "";
		InputStream fileContent = null;
		//	ischagesMade = true;
		//deletemsg = "";
		try {



			UploadedFile item = event.getUploadedFile();
			infile = item.getName();

			long infilesize = item.getSize();
			String infiletype = item.getContentType();
			System.out.println("File to be uploaded: " + infile);

			sourceFile = new File(infile);
			bi_metadata_dir = uploadedFolder4Attachment;
			targetFile = new File(bi_metadata_dir + sourceFile.getName());
			String infilesize1 = getFileSize(infilesize);
			BIMetadataFile = sourceFile.getName();

			//		FileUtils.copyFile(sourceFile, targetFile, false);   code change pandian 12Aug2013



			this.dirname = sourceFile.getAbsolutePath();

			System.out.println("Source File Location (dirname): " + dirname);

			// write file to bimetadat dir
			fileContent =item.getInputStream();
			BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(targetFile));
			byte[] buffer = new byte[1024];
			int len;
			while ((len = fileContent.read(buffer)) >= 0) {

				bos.write(buffer, 0, len);
			}

			fileContent.close();

			bos.close();


			if (Globals.isFileExists(bi_metadata_dir, BIMetadataFile)) {



				System.out.println("BI Metadata File Uploaded Successfully: " + bi_metadata_dir + BIMetadataFile);
			} else {

				System.out.println("*** Error: BI Metadata File is not Uplodaed: " + bi_metadata_dir + BIMetadataFile);
				System.out.println("*** Error: Discovery Process Aborted.");

				// ??Devan: Disable Discover Button. If the file is uploaded later correctly, ensure Discover Button is
				// enabled.
				return;
			}


			WorkflowTabBean attachorder = new WorkflowTabBean(infile, infiletype, 
					infilesize1, "");

			attachdocumentDetailsAL.add(attachorder);


			System.out.println("Exiting : " + new Exception().getStackTrace()[0].getClassName() + "."
					+ new Exception().getStackTrace()[0].getMethodName());
		} catch (Exception e) {
			// Globals.getException(e);
		}

	}





	public void	calendarFromAction(int rowIndex) {

		try { 

			System.out.println("rowIndexFrom :"+rowIndex);
			WorkflowDataBean wdbCal = calendarDetailsAL.get(rowIndex);

			System.out.println("wdbCal.getCalendarFrom(); :"+wdbCal.getCalendarFrom());	
		}
		catch (Exception e) {
			// TODO: handle exception
		}

	}


	public void	calendarToAction(int rowIndex) {

		try { 


			System.out.println("rowIndexTo :"+rowIndex);
			WorkflowDataBean wdbCal = calendarDetailsAL.get(rowIndex);
			System.out.println("wdbCal.getCalendarFrom(); :"+wdbCal.getCalendarTo());	
		}
		catch (Exception e) {
			// TODO: handle exception  
		}

	}
	public void	startDateIdAction(int rowIndex) {

		try {

			SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
			if(rowIndex == 0) {
				System.out.println("1111 : "+rowIndex);
				
				Date date = new Date();
				WorkflowDataBean wdb = timeLineTableAL.get(rowIndex);
				String dateStr = RulesManager.checkAndConvertDate(wdb.getStageSDstr(), dateFormat.format(date));
				if(dateStr.equalsIgnoreCase("Error")) {
					return;
				}
				wdb.setStageESDstr(dateStr);
//				String oldDate = dateFormat.format(date);  
//
////				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
//				Calendar c = Calendar.getInstance();
//
//				try{ 
//					c.setTime(dateFormat.parse(oldDate));
//				}catch(ParseException e){
//					e.printStackTrace();
//				}
//
//				c.add(Calendar.DAY_OF_MONTH, Integer.parseInt(wdb.getStageSDstr()));  
//				String newDate = dateFormat.format(c.getTime()); 
//				wdb.setStageESDstr(newDate);
				

			}else {
				System.out.println("22222222 +: "+rowIndex);
//				DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
				Date date = new Date();
				WorkflowDataBean wdb1 = null;
				for(int i=1;i<=rowIndex;i++) {
					wdb1 = timeLineTableAL.get(rowIndex - i);
					if(wdb1.getStageEEDstr() == null || wdb1.getStageEEDstr().isEmpty()) {
						wdb1 = null;
					}else
						break;
				}
				
				WorkflowDataBean wdb2 = timeLineTableAL.get(rowIndex);
				String oldDate =wdb1 == null ? dateFormat.format(date) : wdb1.getStageEEDstr();
//				System.out.println("oldDate1 +: "+oldDate);
////				SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy");
//				Calendar c = Calendar.getInstance();
//
//				try{
//					c.setTime(dateFormat.parse(oldDate));
//				}catch(ParseException e){
//					e.printStackTrace();
//				}
//				System.out.println("Integer.parseInt(wdb1.getStageSDstr() : +: "+Integer.parseInt(wdb1.getStageSDstr()));
//
//				c.add(Calendar.DAY_OF_MONTH, Integer.parseInt(wdb2.getStageSDstr()));  
//				String newDate = dateFormat.format(c.getTime());
				String dateStr = RulesManager.checkAndConvertDate(wdb2.getStageSDstr(), oldDate);
				if(dateStr.equalsIgnoreCase("Error")) {
					return;
				}
				wdb2.setStageESDstr(dateStr);
//				wdb2.setStageESDstr(newDate);
			}


			//startEffectiveDateIdStr=dateFormat.format(date);



			//Incrementing the date by 1 day
			/*	c.add(Calendar.DAY_OF_MONTH, Integer.parseInt(startDateIdstr));  
			String newDate = sdf.format(c.getTime());  
			startEffectiveDateIdStr =newDate;
			System.out.println("Date Incremented by One: "+newDate);*/
		} catch (Exception e) {
			// TODO: handle exception
		}
	}


	public void	endDateIdAction(int rowIndex) {

		try {


			DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");

//			if(rowIndex == 0) {
				System.out.println("dddddddddddddd aoldDate :");
				
				Date date = new Date();
				WorkflowDataBean wdb = timeLineTableAL.get(rowIndex);
				String startDt = wdb.getStageESDstr();
				String oldDate =wdb.getStageEDstr();
				String dateStr = RulesManager.checkAndConvertDate(oldDate, startDt);
				if(dateStr.equalsIgnoreCase("Error")) {
					return;
				}
				wdb.setStageEEDstr(dateStr);
//				System.out.println("dddddddddddddd oldDate :"+oldDate);
////				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
//				Calendar c = Calendar.getInstance();
//
//				try{
//					c.setTime(dateFormat.parse(oldDate));
//				}catch(ParseException e){
//					e.printStackTrace();
//				}
//				System.out.println("33333333 : "+rowIndex);
//				c.add(Calendar.DAY_OF_MONTH, Integer.parseInt(wdb.getStageEDstr()));  
//				String newDate = dateFormat.format(c.getTime()); 
//				wdb.setStageEEDstr(newDate);

//			}else {
//
////				DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
//				Date date = new Date();
//				WorkflowDataBean wdb1 = timeLineTableAL.get(rowIndex - 1);
//				WorkflowDataBean wdb3 = timeLineTableAL.get(rowIndex);
//				String oldDate = wdb3.getStageESDstr(); 
//				System.out.println("dddddddVVVddddddd oldDate :"+oldDate);
////				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
//				Calendar c = Calendar.getInstance();
//
//				try{
//					c.setTime(dateFormat.parse(oldDate));
//				}catch(ParseException e){
//					e.printStackTrace();
//				}
//				System.out.println("44444444 +: "+rowIndex);
//
//				c.add(Calendar.DAY_OF_MONTH, Integer.parseInt(wdb3.getStageEDstr()));  
//				String newDate = dateFormat.format(c.getTime()); 
//				wdb3.setStageEEDstr(newDate);
//			}




			/*System.out.println("rowIndex :"+rowIndex +"colIndex :" +colIndex);
			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			Date date = new Date();
			// endEffectiveDateIdStr=dateFormat.format(date);

			  String oldDate = startEffectiveDateIdStr;  
				System.out.println("Date before Addition: "+oldDate);
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				Calendar c = Calendar.getInstance();
				try{
				   c.setTime(sdf.parse(oldDate));
				}catch(ParseException e){
				   e.printStackTrace();
				 }
				//Incrementing the date by 1 day
				c.add(Calendar.DAY_OF_MONTH, Integer.parseInt(endDateIdstr));  
				String newDate = sdf.format(c.getTime());  
				endEffectiveDateIdStr =newDate;*/


		} catch (Exception e) {
			// TODO: handle exception
		}
	}


	public void timelineBoxAction() {

		try {


			if(!timelineBoxStr) {

				timelinedisplay="none";
				System.out.println("sssssssssssssssss" +timelineBoxStr);

			}else {
				timelinedisplay="block";
				System.out.println("sssssssssssssssss" +timelineBoxStr);

			}
		}catch (Exception e) {
			// TODO: handle exception
		}



	}

	public void uploadFile2(FileUploadEvent event) throws Exception {


		this.dirname = "";


		PropUtil prop = new PropUtil();
		rbid_metadata_dir = prop.getProperty("RBID_METADATA_DIR");
		bi_metadata_dir = prop.getProperty("BI_METADATA_DIR");
//		createBIMetaXML(rbid_metadata_dir
		System.out.println("rbid_metadata_dir: " + rbid_metadata_dir);
		// String target = rbid_metadata_dir;
		File sourceFile = null;
		File targetFile = null;
		String infile = "";
		InputStream fileContent = null;
		//	ischagesMade = true;
		//deletemsg = "";
		try {


			UploadedFile item = event.getUploadedFile();
			infile = item.getName();
			primaryFileName=infile;
			String infilesize = String.valueOf(item.getSize());
			String infiletype = item.getContentType();
			System.out.println("File to be uploaded: " + infile);

			sourceFile = new File(infile);
			
			String temp = infile.substring(0, infile.lastIndexOf("."));
			BIMetadataFile = sourceFile.getName();
			Random ran = new Random();
			int val = ran.nextInt((99999 - 10000) + 1) + 10000;
			temp = temp+"_"+val;
			while(new File(bi_metadata_dir+temp).isDirectory()) {
				
				val = ran.nextInt((99999 - 10000) + 1) + 10000;
				temp = temp+"_"+val;
			}
			new File(bi_metadata_dir+temp).mkdir();
			bi_metadata_dir = bi_metadata_dir+temp+"\\";
			uploadedFolder4Attachment = bi_metadata_dir;
			System.out.println("uploadedFolder4Attachment: " + uploadedFolder4Attachment);
			targetFile = new File(bi_metadata_dir + sourceFile.getName());
//			if (Globals.isFileExists(bi_metadata_dir, BIMetadataFile)) {
//
//				System.out.println("*** Warning: BI Metadata File exists already. Deleting it: " + bi_metadata_dir
//						+ BIMetadataFile);
//
//				FileUtils.deleteQuietly(new File(bi_metadata_dir + BIMetadataFile));
//			}

			//		FileUtils.copyFile(sourceFile, targetFile, false);   code change pandian 12Aug2013



			this.dirname = sourceFile.getAbsolutePath();

			System.out.println("Source File Location (dirname): " + dirname);

			// write file to bimetadat dir
			fileContent =item.getInputStream();
			BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(targetFile));
			byte[] buffer = new byte[1024];
			int len;
			while ((len = fileContent.read(buffer)) >= 0) {

				bos.write(buffer, 0, len);
			}

			fileContent.close();

			bos.close();


			if (Globals.isFileExists(bi_metadata_dir, BIMetadataFile)) {



				System.out.println("BI Metadata File Uploaded Successfully: " + bi_metadata_dir + BIMetadataFile);
			} else {

				System.out.println("*** Error: BI Metadata File is not Uplodaed: " + bi_metadata_dir + BIMetadataFile);
				System.out.println("*** Error: Discovery Process Aborted.");

				// ??Devan: Disable Discover Button. If the file is uploaded later correctly, ensure Discover Button is
				// enabled.
				return;
			}




			System.out.println("Exiting : " + new Exception().getStackTrace()[0].getClassName() + "."
					+ new Exception().getStackTrace()[0].getMethodName());
		} catch (Exception e) {
			// Globals.getException(e);
			e.printStackTrace();
		}

	}

	public static String getFileSize(Long filesize) {
	    String modifiedFileSize = null;
	 //   double fileSize = 0.0;
	    

	        if (filesize < 1024) {
	            modifiedFileSize = String.valueOf(filesize).concat("B");
	            
	        } else if (filesize > 1024 && filesize < (1024 * 1024)) {
	            modifiedFileSize = String.valueOf(Math.round((filesize / 1024 * 100.0)) / 100.0).concat("KB");
	        } else {
	            modifiedFileSize = String.valueOf(Math.round((filesize / (1024 * 1204) * 100.0)) / 100.0).concat("MB");
	        }
	   

	    return modifiedFileSize;
	}

	
	public void addAction(FileUploadEvent event) {	
		System.out.println("dddddd :"+fileDateAttachStr);
		try {
			UploadedFile item = event.getUploadedFile();
			String infile = item.getName();
			String infilesize = String.valueOf(item.getSize());
			String infiletype = item.getContentType();
			//	String infiledata = item.getData();

			System.out.println("File to be uploaded: " + infile);
			WorkflowTabBean attachorder = new WorkflowTabBean(infile, infiletype, 
					infilesize, "");

			attachdocumentDetailsAL.add(attachorder);

			File sourceFile = null;
			File targetFile = null;
			//	String infile = "";
			InputStream fileContent = null;
			// ischagesMade = true;



			//UploadedFile item = event.getUploadedFile();
			infile = item.getName();

			System.out.println("File to be uploaded: " + infile);

			sourceFile = new File(infile);
			targetFile = new File(bi_metadata_dir + sourceFile.getName());

			BIMetadataFile = sourceFile.getName();

			if (Globals.isFileExists(bi_metadata_dir, BIMetadataFile)) {

				System.out.println("*** Warning: BI Metadata File exists already. Deleting it: " + bi_metadata_dir
						+ BIMetadataFile);

				FileUtils.deleteQuietly(new File(bi_metadata_dir + BIMetadataFile));
			}

			//		FileUtils.copyFile(sourceFile, targetFile, false);   code change pandian 12Aug2013



			//this.dirname = sourceFile.getAbsolutePath();

			//System.out.println("Source File Location (dirname): " + dirname);

			// write file to bimetadat dir
			fileContent =item.getInputStream();
			BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(targetFile));
			byte[] buffer = new byte[1024];
			int len;
			while ((len = fileContent.read(buffer)) >= 0) {

				bos.write(buffer, 0, len);
			}

			fileContent.close();

			bos.close();


			if (Globals.isFileExists(bi_metadata_dir, BIMetadataFile)) {

				System.out.println("BI Metadata File Uploaded Successfully: " + bi_metadata_dir + BIMetadataFile);
			} else {

				System.out.println("*** Error: BI Metadata File is not Uplodaed: " + bi_metadata_dir + BIMetadataFile);
				System.out.println("*** Error: Discovery Process Aborted.");

				// ??Devan: Disable Discover Button. If the file is uploaded later correctly, ensure Discover Button is
				// enabled.
				return;
			}

			System.out.println("Exiting : " + new Exception().getStackTrace()[0].getClassName() + "."
					+ new Exception().getStackTrace()[0].getMethodName());
		}
		catch (Exception e) {
			// TODO: handle exception
		}
		//return null;
	}


	public ArrayList<WorkflowDataBean> getAttachmentDOCDeails(String custKey) throws Exception {

		try{


			ArrayList<WorkflowDataBean> documentDetailsALAttrAL=new ArrayList();
			
			ArrayList<WorkflowDataBean> documentDetailsALAttrAL1=new ArrayList();
			
			
			
			String wfLoginHTdocumentID="";
			String wfLoginHTdocumentName="";
			String wfLoginHTworkflowID="";
			String wfLoginHTworkflowName="";
			String wfLoginHTcreatedDate="";
			String wfLoginHTcreatedBy="";



			//	Hashtable wfHT = WorkflowManager.getAttachDocumentDetailsFromXML("accel-mso-23.docx");
			LoginProcessManager  log=new LoginProcessManager();

			FacesContext ctx1 = FacesContext.getCurrentInstance();
			ExternalContext extContext1 = ctx1.getExternalContext();
			Map sessionMap1 = extContext1.getSessionMap();
			LoginBean lgnbn = (LoginBean) sessionMap1.get("loginBean");

			String userName=lgnbn.getUsername(); //userLoginId
			Hashtable LoginDetailsHT=log.getLoginDetails(userName, custKey);
			String hierarchyIDs = (String) LoginDetailsHT.get("Allowed_Hierarchies");

			String AccessUniqID=(String)LoginDetailsHT.get("Access_Unique_ID");
			if(hierarchyIDs.trim().isEmpty()) {
				//return new Hashtable<>();
				return documentDetailsALAttrAL;
			}
			String customerKey=(String)LoginDetailsHT.get("CustomerKey");
			Hashtable wfListHT = WorkflowManager.actionsWaitingForUser(userName);
			Hashtable userTasksHT = (Hashtable)wfListHT.get("UserTasks");
			Iterator<Entry> it = userTasksHT.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry entry = (Map.Entry) it.next();
				String key = entry.getKey().toString();
				if(key.equalsIgnoreCase("TableLength"))
					continue;
				Hashtable docDetailsHT = (Hashtable)entry.getValue();
//				System.out.println(docDetailsHT.get("currentprocessMember")+"-=-=-=-=docDetailsHT-=-=-="+docDetailsHT);
				if(docDetailsHT.get("currentprocessMember") == null)
					continue;
				String wfId = docDetailsHT.get("Hierarchy_ID").toString();
				String wfName = docDetailsHT.get("WorkflowName").toString();
				String docId = docDetailsHT.get("DocumentID").toString();
				String docName = docDetailsHT.get("DocumentName").toString();
				String currentStg = docDetailsHT.get("currentprocessMemberRole").toString();
				String currentStgUser = docDetailsHT.get("currentprocessMemberAll").toString();
				String nextTeam = docDetailsHT.get("ToprocessMemberRole") == null ? "-" : docDetailsHT.get("ToprocessMemberRole").toString();
				String nextTeamUser = docDetailsHT.get("ToprocessMember") == null ? "-" : docDetailsHT.get("ToprocessMember").toString();
				String nextTeamDetails = docDetailsHT.get("ToprocessMemberAll") == null ? "-" : docDetailsHT.get("ToprocessMemberAll").toString();
				String dueDate = docDetailsHT.get("DueDate").toString();
				String docCreatedby = docDetailsHT.get("Created_By").toString();
				String docCreatedDate = docDetailsHT.get("Created_Date").toString();
				String docType = "User Awaiting Task";
				
				
				
				
				
				//-------------Vignesh Start code-----1
				

				System.out.println(":::::::$$$::>>>>>>>"+dueDate);

				String color = getColorCode4DueDate(dueDate);

//				ddfdf

				
				
				
				
				//-------------------Vignesh End code-----------1
				
				
				
				WorkflowDataBean workflowData4Table = new WorkflowDataBean(wfId,wfName,docId,docName,currentStg,currentStgUser,nextTeam, 
						nextTeamUser, dueDate, docType, nextTeamDetails,docCreatedby,docCreatedDate, color);
				documentDetailsALAttrAL.add(workflowData4Table);
			}
			Hashtable userTasksOtherHT = (Hashtable)wfListHT.get("UserAwaitingTasks");
			Iterator<Entry> it1 = userTasksOtherHT.entrySet().iterator();
			while (it1.hasNext()) {
				Map.Entry entry = (Map.Entry) it1.next();
				String key = entry.getKey().toString();
				if(key.equalsIgnoreCase("TableLength"))
					continue;
				Hashtable docDetailsHT = (Hashtable)entry.getValue();
				System.out.println(docDetailsHT.get("currentprocessMember")+"-=-=-=-=docDetailsHT-=-=-="+docDetailsHT);
//				if(docDetailsHT.get("currentprocessMember") == null)
//					continue;
				String wfId = docDetailsHT.get("Hierarchy_ID").toString();
				String wfName = docDetailsHT.get("WorkflowName").toString();
				String docId = docDetailsHT.get("DocumentID").toString();
				String docName = docDetailsHT.get("DocumentName").toString();
				String currentStg = docDetailsHT.get("currentprocessMemberRole") == null ? (docDetailsHT.get("Current_Stage_Name") == null ? "Completed" : docDetailsHT.get("Current_Stage_Name").toString()) : docDetailsHT.get("currentprocessMemberRole").toString();
				String currentStgUser = docDetailsHT.get("currentprocessMemberAll") == null ? (docDetailsHT.get("Current_Stage_Name") == null ? "Completed" : docDetailsHT.get("Current_Stage_Name").toString()) : docDetailsHT.get("currentprocessMemberAll").toString();
				String nextTeam = docDetailsHT.get("ToprocessMemberRole") == null ? "-" : docDetailsHT.get("ToprocessMemberRole").toString();
				String nextTeamUser = docDetailsHT.get("ToprocessMember") == null ? "-" : docDetailsHT.get("ToprocessMember").toString();
				String nextTeamDetails = docDetailsHT.get("ToprocessMemberAll") == null ? "-" : docDetailsHT.get("ToprocessMemberAll").toString();
				String dueDate = docDetailsHT.get("DueDate") == null ? "" : docDetailsHT.get("DueDate").toString();
				String docCreatedby = docDetailsHT.get("Created_By").toString();
				String docCreatedDate = docDetailsHT.get("Created_Date").toString();
				String docType = "Waiting for Others";
				String color = getColorCode4DueDate(dueDate);
				WorkflowDataBean workflowData4Table = new WorkflowDataBean(wfId,wfName,docId,docName,currentStg,currentStgUser,nextTeam, 
						nextTeamUser, dueDate, docType, nextTeamDetails,docCreatedby,docCreatedDate, color);
				documentDetailsALAttrAL1.add(workflowData4Table);
			}
			documentDetailsAL.clear();
			documentDetailsAL.addAll(documentDetailsALAttrAL);
	//-------------Vignesh Start code-------
			
			documentDetailsAL1.clear();
			documentDetailsAL1.addAll(documentDetailsALAttrAL1);
			
	//---------------------Vignesh End code--------	
			
			
			java.util.Collections.sort(documentDetailsAL, StuNameComparator);
			java.util.Collections.sort(documentDetailsAL1, StuNameComparator);
		}catch(Exception e){
			e.printStackTrace();
		}

		//return null;

		return documentDetailsAL;


	}
	
	public String getColorCode4DueDate(String dueDate) {
		String color = "BLACK";
		if(dueDate != null && !dueDate.trim().isEmpty())
		{
			System.out.println("$$$$$ NO DUEDATE $$$");





//			System.out.println("due date not date format>>>>>>>>"+ dueDate);
			try {
				
				SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
				Date duedate1 = sdf.parse(dueDate);
				
				
				
				
				/* if(duedate1!=null || !duedate1.equals(null))
	        {

	        	System.out.println("$$$Duedate is missing....");

	        }*/


//				System.out.println("^^^^^^^****^^^ docdueDate >>>> :"+duedate1);

				//reference link :https://www.mkyong.com/java/how-to-compare-dates-in-java/			
				DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
				Date date = new Date();
	
				String sr=dateFormat.format(date);
				date = dateFormat.parse(sr);
				
				
				
				
				
//				System.out.println("**********"+date);
//				System.out.println("date format string......"+sr);
				
				
				
				

				Calendar cal = Calendar.getInstance();				
				//Add one day to current date.
				cal.add(Calendar.DATE,1);
//				System.out.println(" DATE ONE DAY BEFORE ...."+dateFormat.format(cal.getTime()));
				//DateFormat dateFormat0 = new SimpleDateFormat("MM-dd-yyyy");
				
				Date afterdt=cal.getTime();
				String afterdate=dateFormat.format(afterdt);
				afterdt=dateFormat.parse(afterdate);

//				System.out.println(">>>>.....>>>>>>>>"+afterdt);
//				System.out.println("String value ...."+afterdate);

				if ((duedate1.compareTo(date)==0) || duedate1.compareTo(afterdt)==0 ) 
				{
					// this.dueDteClr="yellow";
					//this.dueDatecolor="yellow";
					color="yellow";
//					System.out.println("$$$$$$$$$$$$$$$$$$$$ Yellow");
				} 			    
				//else if (duedate1.compareTo(date) < 0) {
				else if (duedate1.after(date)) 
				{
					color="GREEN";
//					System.out.println("##################### ==== RED ");
					// this.dueDteClr="red";
					// this.dueDatecolor="red";
				} 	
				
			//	else if(duedate1.compareTo(date) > 0) {
				else if (duedate1.before(date))
						{
					color="RED";
					
//					System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@== Green");
					// this.dueDteClr="green";

				} 	
				else {
					System.out.println("How to get here?");
				}
			}catch (ParseException e) {
				// TODO: handle exception
			}

		}
		return color;
	}

	public static Comparator<WorkflowDataBean> StuNameComparator = new Comparator<WorkflowDataBean>() {

		public int compare(WorkflowDataBean s1, WorkflowDataBean s2) {
		   String StudentName1 = s1.getDocumentID().toUpperCase();
		   String StudentName2 = s2.getDocumentID().toUpperCase();

		   //ascending order
//		   return StudentName1.compareTo(StudentName2);

		   //descending order
		   return StudentName2.compareTo(StudentName1);
	    }};


	public	void calcalenderLink() {
		try {

			int j=1;
			int rowKeyCount=0;
			WorkflowDataBean workflowcalendar4Table = new WorkflowDataBean(rowKeyCount,j,calendarFrom,calendarTo);
			calendarDetailsAL.add(workflowcalendar4Table);

		}catch (Exception e) {
			// TODO: handle exception
		}



	}
	public	void calendarAddAction() {

		try {
			System.out.println("dd:COMING");
			//	ArrayList<WorkflowDataBean> workflowcalendarAL=new ArrayList();

			int rowKeyCount=0;
			WorkflowDataBean workflowcalendar4Table = new WorkflowDataBean(calendarDetailsAL.size()+1,calendarDetailsAL.size()+1,calendarFrom,calendarTo);
			calendarDetailsAL.add(workflowcalendar4Table);


			//calendarDetailsAL=workflowcalendarAL;

		}catch (Exception e) {
			// TODO: handle exception
		}

		//return calendarDetailsAL;

	}

	public void	closeAction() {

		try {

			this.attachDocumentMsgstr="";

		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public void loadWF() {

		try {

			ArrayList documentlistWorkflowAL=new ArrayList();


			String wfLoginHTdocumentID="";
			String wfLoginHTdocumentName="";
			String wfLoginHTworkflowID="";
			String wfLoginHTworkflowName="";
			String wfLoginHTcreatedDate="";
			String wfLoginHTcreatedBy="";

			Hashtable workflowHT= new Hashtable();


			//	Hashtable workflowHT=WorkflowManager.getAttachDocumentDetailsFromXML("accel-mso-23.docx");


			/*LoginProcessManager  log=new LoginProcessManager();

				FacesContext ctx1 = FacesContext.getCurrentInstance();
				ExternalContext extContext1 = ctx1.getExternalContext();
				Map sessionMap1 = extContext1.getSessionMap();
				LoginBean lgnbn = (LoginBean) sessionMap1.get("loginBean");

				String userName=lgnbn.getUsername(); //userLoginId
				Hashtable LoginDetailsHT=log.getLoginDetails(userName);
				String hierarchyIDs = (String) LoginDetailsHT.get("Allowed_Hierarchies");

				String AccessUniqID=(String)LoginDetailsHT.get("Access_Unique_ID");
				if(hierarchyIDs.trim().isEmpty()) {
					//return new Hashtable<>();
					return;
				}
				String[] hierIds = hierarchyIDs.split(",");
				System.out.println("-=-=-=-hierarchyIDs=-=-=-="+hierarchyIDs);

				PropUtil prop = new PropUtil();
				String hierDir=prop.getProperty("HIERARCHY_XML_DIR");
				String hierLevelXmlFileName=prop.getProperty("HIERARCHY_XML_FILE");
				Document doc=Globals.openXMLFile(hierDir, hierLevelXmlFileName);
				String docXmlFileName = prop.getProperty("DOCUMENT_XML_FILE");
				Document docXmlDoc=Globals.openXMLFile(hierDir, docXmlFileName);

				for(int i=0;i<hierIds.length;i++) {
					String hierID = hierIds[i];
					Node wfNd=Globals.getNodeByAttrVal(doc, "Workflow", "Hierarchy_ID", hierID);
					if(wfNd == null)
						continue;
					Element wfEle = (Element) wfNd;
					String docIds = wfEle.getAttribute("DependentObjectID");
					if(docIds.trim().isEmpty()) {
						continue;
					}
					String[] docs = docIds.split(",");
				//	Hashtable WFNodeDetailsHT=Globals.getNodeByAttrVal(docXmlDoc, tag_nm, attrName, attrValue);
					for(int j=0;j<docs.length;j++) {
						System.out.println("Document ID :"+docs[j]);
						String docID = docs[j];
						Node wfDOCNd=Globals.getNodeByAttrVal(docXmlDoc, "Document", "Document_ID", docID);

						if(wfDOCNd == null)
							continue;
						Element wfdocEle = (Element) wfDOCNd;

						wfLoginHTworkflowID = wfdocEle.getAttribute("WorkflowID");
						wfLoginHTworkflowName = wfdocEle.getAttribute("WorkflowName");
						wfLoginHTdocumentID = wfdocEle.getAttribute("Document_ID");
						wfLoginHTdocumentName = wfdocEle.getAttribute("DocumentName");
						wfLoginHTcreatedDate = wfdocEle.getAttribute("Created_Date");
						wfLoginHTcreatedBy = wfdocEle.getAttribute("Created_By");
						System.out.println("wfLoginHTworkflowName :"+wfLoginHTworkflowName);
						//workflowHT.put("wfLoginHTworkflowName", wfLoginHTworkflowName);		

						documentlistWorkflowAL.add(wfLoginHTworkflowName);
					}*/

			FacesContext ctx1 = FacesContext.getCurrentInstance();
			ExternalContext extContext1 = ctx1.getExternalContext();
			Map sessionMap1 = extContext1.getSessionMap();
			LoginBean lgnbn = (LoginBean) sessionMap1.get("loginBean");

			String userCustomerKey=lgnbn.getCustomerKey();

			Hashtable wfHT = WorkflowManager.listWorkflowForCustomer(userCustomerKey);
			listWorkflowAL.clear();
			for (int i = 1; i <= wfHT.size(); i++) {

				Hashtable taskHT = (Hashtable)wfHT.get("R"+i);
				String wfLoginHTworkflowName1 = (String)taskHT.get("WorkflowName");



				System.out.println("wfLoginHTworkflowName1 :"+wfLoginHTworkflowName1);
				listWorkflowAL.add(wfLoginHTworkflowName1);
			}

			listWorkflowAL=documentlistWorkflowAL;

			//System.out.println("listWorkflowAL : "+listWorkflowAL.get(i));

		}catch (Exception e) {
			// TODO: handle exception
		}

	}

	public void saveAction(String actionType) {

		try {
			Hashtable allDetailsHT=new Hashtable();
			Hashtable GetHT=new Hashtable();
			Hashtable otherAttHT=new Hashtable();

			if(primaryFileName.equals("") || primaryFileName==null) {
				this.attachDocumentMsgColor="red";
				this.attachDocumentMsgstr="Please Choose Primary File and to proceed further.";
				return;
			}

			if(choosewfNameStr.equals("") || choosewfNameStr==null) {
				this.attachDocumentMsgColor="red";
				this.attachDocumentMsgstr="Please Choose Workflow and to proceed further.";
				return;
			}


			PropUtil prop = new PropUtil();
			String HIERARCHY_XML_DIR = prop.getProperty("HIERARCHY_XML_DIR");
			String docXmlFileName = prop.getProperty("DOCUMENT_XML_FILE");
			String dateFormatStr = prop.getProperty("DATE_FORMAT");
			DateFormat dateFormat = new SimpleDateFormat(dateFormatStr);
			Date date = new Date();
			System.out.println(dateFormat.format(date)); 
			String credDATE=dateFormat.format(date);
			String docID="";

			String conWorkflowName="";
			String conWorkflowID="";
			FacesContext ctx1 = FacesContext.getCurrentInstance();
			ExternalContext extContext1 = ctx1.getExternalContext();
			Map sessionMap1 = extContext1.getSessionMap();
			LoginBean lgnbn = (LoginBean) sessionMap1.get("loginBean");

			String userCustomerKey=lgnbn.getCustomerKey();
			String userCustomerName=lgnbn.getUsername();

			Hashtable wfHT = WorkflowManager.listWorkflowForCustomer(userCustomerKey);
			// System.out.println("wfHT :"+wfHT);


			for(int i=1;i< wfHT.size();i++) {
				Hashtable GetkeyHT=new Hashtable();
				GetkeyHT=(Hashtable)wfHT.get("R"+i);

				conWorkflowName=(String)GetkeyHT.get("WorkflowName");


				System.out.println("conWorkflowName :"+conWorkflowName+"choosewfNameStr :"+choosewfNameStr+"conWorkflowID :"+conWorkflowID+"globlworkflowID :"+globlworkflowID);
				if(conWorkflowName.equals(choosewfNameStr)) {

					conWorkflowID=(String)GetkeyHT.get("WorkflowID");
					System.out.println("(String)GetkeyHT.get(\"WorkflowID\") :"+(String)GetkeyHT.get("CreatedDate"));

				}

				//this.globlworkflowID

			}


			//String attachMentFiles=getattachmentFile();
			//JSONObject	jObject = new JSONObject(attachMentFiles);






			allDetailsHT.put("WorkflowName",choosewfNameStr);

			allDetailsHT.put("WorkflowID",conWorkflowID);
			allDetailsHT.put("DocumentID",docID);
			allDetailsHT.put("CustomerKey",userCustomerKey);
			allDetailsHT.put("CreatedBy",userCustomerName);
			allDetailsHT.put("CreatedDate",credDATE);

			allDetailsHT.put("DocumentName",primaryFileName);
			allDetailsHT.put("ChooseTeamName","");
			if(documentTextarea==null || documentTextarea.equals("")) {
				allDetailsHT.put("Sendnotes","");
			}else {
				allDetailsHT.put("Sendnotes",documentTextarea);
			}		

			//chooseprimaryFileName
			allDetailsHT.put("Primary_FileName",primaryFileName);
			allDetailsHT.put("Primary_FilePath","");
			allDetailsHT.put("Primary_FileConn","");


			allDetailsHT.put("Timeline", String.valueOf(timelineBoxStr));
			allDetailsHT.put("Showhours", String.valueOf(showHoursBoxStr));
			allDetailsHT.put("ExecludeWeekEnd", String.valueOf(exweekBoxStr));
			allDetailsHT.put("Eachteam", String.valueOf(forEachtimeBoxStr));
			allDetailsHT.put("ExecludeHoliday", String.valueOf(exholidayBoxStr));
			allDetailsHT.put("SendAlertBefore", String.valueOf(sendalertStr));
			allDetailsHT.put("SendAlertAfter", String.valueOf(sendalertAfterdeadLineStr));
			allDetailsHT.put("SendAlertSubSequentTeams", String.valueOf(sendalertsubseqStr));


			allDetailsHT.put("SendAlertBeforeDay", String.valueOf(deadLinebeforeTextBox1Str));
			allDetailsHT.put("SendAlertAfterCount", String.valueOf(deadLineTextBox1Str));
			allDetailsHT.put("SendAlertAfterDay", String.valueOf(deadLineTextBox2Str));
			allDetailsHT.put("SendAlertDayHourchooser",(String)deadLineSelectMenuStr);

			//allDetailsHT.put("otherAttHT",otherAttHT);

			Hashtable rowHTDetailsHT=new Hashtable();

			for (int i = 0; i < attachdocumentDetailsAL.size(); i++) {
				Hashtable rowHT=new Hashtable();
				rowHT.put("FileName", attachdocumentDetailsAL.get(i).fileNameAttachStr);
				rowHT.put("FileType", attachdocumentDetailsAL.get(i).fileTypeAttachStr);
				rowHT.put("FileSize", attachdocumentDetailsAL.get(i).fileSizeAttachStr);
				rowHT.put("File_AddDate", "No");
//				AttachedStage="Creator" AttachedUser="vishnu@cygnussoftwares.com"
				String attachStgName = "";
				if(actionType.equalsIgnoreCase("Move2NextStage")) {
//		        	String docId = uploadDetails.get("DocumentID");
		        	if(conWorkflowID != null && conWorkflowID.isEmpty()) {
//				        PropUtil prop = new PropUtil();
//						String HIERARCHY_XML_DIR = prop.getProperty("HIERARCHY_XML_DIR");
						String hierXmlFileName = prop.getProperty("HIERARCHY_XML_FILE");
						Document hierXmlDoc = Globals.openXMLFile(HIERARCHY_XML_DIR, hierXmlFileName);
						Element docEle = (Element) Globals.getNodeByAttrVal(hierXmlDoc, "Document", "Document_ID", conWorkflowID);
						Node workFlowN = docEle.getElementsByTagName("Workflow").item(0);
		        		LoginProcessManager lpm = new LoginProcessManager();
		        		Hashtable currTeamHT = lpm.retriveStageDetailsFromXML(workFlowN, "1", "");
		        		attachStgName = currTeamHT.get("Stage_Name") == null ? "Attach" : currTeamHT.get("Stage_Name").toString();
		        	}
				}else {
					attachStgName = "Attach";
				}
				rowHT.put("AttachedStage", attachStgName);
				rowHT.put("AttachedUser", userCustomerName);
				rowHT.put("UploadedDate", dateFormat.format(new Date()));
				rowHTDetailsHT.put("R"+i,rowHT);

			}


			rowHTDetailsHT.put("TableLength",String.valueOf(rowHTDetailsHT.size()));

			allDetailsHT.put("AttachMent_Files",rowHTDetailsHT);


			Hashtable rowTimeLinetolHT=new Hashtable();

			for (int i = 0; i < timeLineTableAL.size(); i++) {
				Hashtable rowTimeLineHT=new Hashtable();
				System.out.println("timeLineTableAL :: "+timeLineTableAL.size());


				rowTimeLineHT.put("tblCol_Team", timeLineTableAL.get(i).stageTeamstr);
				rowTimeLineHT.put("tblCol_StartDay",  timeLineTableAL.get(i).stageSDstr);
				rowTimeLineHT.put("tblCol_EndDay",  timeLineTableAL.get(i).stageEDstr);
				rowTimeLineHT.put("tblCol_EffStartDate",  timeLineTableAL.get(i).stageESDstr);
				rowTimeLineHT.put("tblCol_EffEndDate",  timeLineTableAL.get(i).stageEEDstr);


				rowTimeLinetolHT.put("R"+i,rowTimeLineHT);


			}



			rowTimeLinetolHT.put("TableLength",String.valueOf(rowTimeLinetolHT.size()));

			allDetailsHT.put("Tablevalue",rowTimeLinetolHT);
			String attrValue = "";
//			Iterator<Entry<String, String>> it = rulesAttrDetailsAL;
			
			for(RuleAttributeDataModel radm : rulesAttrDetailsAL) {
				String value = radm.getAttrValue();
				SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
				if(radm.getAttrDataType().equalsIgnoreCase("date")) {
					value = radm.getDateType().equalsIgnoreCase("currentdate") ? "CurrentDate" : sdf.format(radm.getDateValue());
				}
				value = value.isEmpty() ? " " : value;
				System.out.println(rulesAttrValueHT.get(radm.getAttributeName())+"-=-=-=-=-dfd=-=-=-="+value);
				attrValue = attrValue.isEmpty() ? value : attrValue +"#~#"+ value;
			}
			allDetailsHT.put("RuleAttributeValue", attrValue);
			System.out.println("allDetailsHT :: "+allDetailsHT);


			JSONObject jObject = new JSONObject(allDetailsHT);
			String jsonbject=jObject.toString();

			System.out.println("jsonbject :: "+jsonbject);

			/*	for (int k = 0; k < allDetailsHT.size(); k++) {
					GetHT=(Hashtable)allDetailsHT.get("R"+k);

					    String fileName = (String)GetHT.get("AttachfileName");
	                    String filetype = (String)GetHT.get("AttachfileType");
	                    String fileSize = (String)GetHT.get("AttachfileSize");
	                    String fileaddDate = (String)GetHT.get("AttachfileDate");
	                    String rowkey = "R"+k;
					System.out.println("allDetailsHT Size:: "+GetHT.get("AttachfileName"));

				}
			 */
			/*TEST JSON STRING */

			/*	String val="{\"Primary_FileName\":\"GroupCDown - Copy.png\",\"AttachMent_Files\":{\"TableLength\":\"4\",\"R0\":{\"FileName\":\"GroupCDown - Copy.png\",\"FileType\":\"png\",\"FileSize\":\"367KB\",\"File_AddDate\":\"No\"},\"R1\":{\"FileName\":\"GroupCDown.png\",\"FileType\":\"png\",\"FileSize\":\"367KB\",\"File_AddDate\":\"No\"},\"R2\":{\"FileName\":\"GroupCUp.png\",\"FileType\":\"png\",\"FileSize\":\"425KB\",\"File_AddDate\":\"No\"},\"R3\":{\"FileName\":\"hardrock.jpg\",\"FileType\":\"jpg\",\"FileSize\":\"341831KB\",\"File_AddDate\":\"No\"}},\"WorkflowName\":\"Finance_WF\",\"WorkflowID\":\"110\",\"DocumentID\":\"\",\"CustomerKey\":\"xbstjb1ciumjt3hu\",\"CreatedBy\":\"bharath@cygnussoftwares.com\",\"CreatedDate\":\"08-09-2018 20:40\",\"DocumentName\":\"accel-mso-288.docx\",\"ChooseTeamName\":\"Initiate\",\"Sendnotes\":\"asdf\",\"Timeline\":\"True\",\"Showhours\":\"False\",\"ExecludeWeekEnd\":\"True\",\"Eachteam\":\"True\",\"ExecludeHoliday\":\"True\",\"SendAlertBefore\":\"True\",\"SendAlertBeforeDay\":\"1\",\"SendAlertAfter\":\"True\",\"SendAlertAfterCount\":\"1\",\"SendAlertAfterDay\":\"1\",\"SendAlertDayHourchooser\":\"Day\",\"SendAlertSubSequentTeams\":\"True\",\"Tablevalue\":{\"TableLength\":\"4\",\"R0\":{\"tblCol_Team\":\"Initiate\",\"tblCol_StartDay\":\"1\",\"tblCol_EndDay\":\"1\",\"tblCol_EffStartDate\":\"08-13-2018\",\"tblCol_EffEndDate\":\"08-14-2018\"},\"R1\":{\"tblCol_Team\":\"Create\",\"tblCol_StartDay\":\"1\",\"tblCol_EndDay\":\"1\",\"tblCol_EffStartDate\":\"08-15-2018\",\"tblCol_EffEndDate\":\"08-16-2018\"},\"R2\":{\"tblCol_Team\":\"Review\",\"tblCol_StartDay\":\"1\",\"tblCol_EndDay\":\"1\",\"tblCol_EffStartDate\":\"08-17-2018\",\"tblCol_EffEndDate\":\"08-20-2018\"},\"R3\":{\"tblCol_Team\":\"\",\"tblCol_StartDay\":\"\",\"tblCol_EndDay\":\"\",\"tblCol_EffStartDate\":\"\",\"tblCol_EffEndDate\":\"\"}},\"ExcludeDates\":\"08-09-2018~08-12-2018\"}";
				JSONObject jObject1 = new JSONObject(val);
				String jsonbject22=jObject1.toString();*/


			/*	WorkflowManager.attachDocument2TheWF(jsonbject22);*/

			Hashtable<String, String> uploadDetails = WorkflowManager.attachDocument2TheWF(jsonbject, actionType);

			String docPath = uploadDetails.get("DocumentName");
//			String primaryDocPathTemp = docPath.substring(0, docPath.lastIndexOf("\\"));
			String primaryDocPath = docPath.substring(0, docPath.lastIndexOf("\\"));
			String attachDocPath = primaryDocPath.substring(0, primaryDocPath.lastIndexOf("\\"));
			System.out.println(primaryDocPath+"-=-=-=-=-=-=-=-="+docPath);
			if(!new File(primaryDocPath).isDirectory())
				new File(primaryDocPath).mkdir();
			if(!new File(attachDocPath).isDirectory())
				new File(attachDocPath).mkdir();
	        File directory = new File(uploadedFolder4Attachment);

	        //get all the files from a directory
	        if(directory.isDirectory()) {
	        	File[] fList = directory.listFiles();

	        	for (File file : fList){
	        		System.out.println(file.getName());
	        		if(file.getName().equalsIgnoreCase(primaryFileName)) {
	        			File to = new File(primaryDocPath+"\\"+primaryFileName);
	        			Files.copy(file.toPath(), to.toPath());
	        		}else {
	        			File to = new File(attachDocPath+"\\"+file.getName());
	        			Files.copy(file.toPath(), to.toPath());
	        		}
	        		

	        	}
	        }
			// String jsObjStr = "{\"Primary_FileName\":\"" + primaryFileName + "\",\"AttachMent_Files\":" + attachMentFiles + ",\"WorkflowName\":\"" + workflowName + "\",\"WorkflowID\":\"" + wrkflwId + "\",\"DocumentID\":\""+addinModule.WfDocId+"\",\"CustomerKey\":\"" + customerKey + "\",\"CreatedBy\":\"" + createdBy + "\",\"CreatedDate\":\"" + createdDate + "\",\"DocumentName\":\"" + documentName + "\",\"ChooseTeamName\":\"" + chooseTeamName + "\",\"Sendnotes\":\"" + sendnotes + "\",\"Timeline\":\"" + timeline + "\",\"Showhours\":\"" + showhours + "\",\"ExecludeWeekEnd\":\"" + execludWeenEnd + "\",\"Eachteam\":\"" + eachteam + "\",\"ExecludeHoliday\":\"" + execludHoliday + "\",\"SendAlertBefore\":\"" + sndAlertBfor + "\",\"SendAlertBeforeDay\":\"" + sndAlertBfortext + "\",\"SendAlertAfter\":\"" + sndAlertAfor + "\",\"SendAlertAfterCount\":\"" + sndAlertAfortext1 + "\",\"SendAlertAfterDay\":\"" + sndAlertAfortext2 + "\",\"SendAlertDayHourchooser\":\"" + sndAlertAforchoosr + "\",\"SendAlertSubSequentTeams\":\"" + sndalrtSubsequentTeams + "\",\"Tablevalue\":" + tableval + "," + holiday_Dates + "}";
	        if(actionType.equalsIgnoreCase("Move2NextStage")) {
	        	String docId = uploadDetails.get("DocumentID");
	        	if(docId != null) {
			        
					Document docXmlDoc = Globals.openXMLFile(HIERARCHY_XML_DIR, docXmlFileName);
					Element docEle = (Element) Globals.getNodeByAttrVal(docXmlDoc, "Document", "Document_ID", docId);
					Node workFlowN = docEle.getElementsByTagName("Workflow").item(0);
	        		LoginProcessManager lpm = new LoginProcessManager();
	        		Hashtable currTeamHT = lpm.retriveStageDetailsFromXML(workFlowN, "1", "");
	        		Hashtable msgHT=(Hashtable)currTeamHT.get("MessagedetHT");
					String finalmsg=(String)msgHT.get("Final");
					if(finalmsg.equalsIgnoreCase("Approve") || finalmsg.equalsIgnoreCase("Approved")) {
						finalmsg="Approve";
					}else if(finalmsg.equalsIgnoreCase("Completed") || finalmsg.equalsIgnoreCase("Complete")) {
						finalmsg="Completed";
					}else if(finalmsg.equalsIgnoreCase("Rejected") || finalmsg.equalsIgnoreCase("Reject")) {
						finalmsg="Reject";
					}else if(finalmsg.equalsIgnoreCase("Initiate") || finalmsg.equalsIgnoreCase("Initiated")) {
						finalmsg="Initiate";
					}else if(finalmsg.equalsIgnoreCase("Remind") || finalmsg.equalsIgnoreCase("Reminded")) {
						finalmsg="Remind";
					}else if(finalmsg.equalsIgnoreCase("Escalate") || finalmsg.equalsIgnoreCase("Escalated")) {
						finalmsg="Escalate";
					}else if(finalmsg.equalsIgnoreCase("Cancel")) {
						finalmsg="Cancel";
					}else if(finalmsg.equalsIgnoreCase("Pause")) {
						finalmsg="Pause";
					}else if(finalmsg.equalsIgnoreCase("Acknowledged")) {
						finalmsg="Acknowledged";
					}
					String attachNotes = documentTextarea==null ? "Document is attached." :  documentTextarea;
	        		WorkflowManager.performingAction(userCustomerName, attachNotes, finalmsg, docPath.substring(docPath.lastIndexOf("\\")+1), docId, "JavaWF","","","");
	        		Hashtable testHT = WorkflowManager.getNextDocumentUsers(docXmlDoc, workFlowN, "1", userCustomerName);
	        		Element wfEle = (Element)workFlowN;
	        		String wfType = wfEle.getAttribute("Workflow_Type");
					String nxtStgMsg = "";
					if(testHT != null) {
						ArrayList<String> testAL = testHT.get("Users") == null ? new ArrayList<String>() : (ArrayList<String>)testHT.get("Users");
						String stage = testHT.get("Stage") == null ? "" : (String)testHT.get("Stage");
						nxtStgMsg = stage;
						String users = "";
						for (int i=0;i<testAL.size();i++) {
							 nxtStgMsg = nxtStgMsg+(i == 0 ? "(":"")+testAL.get(i)+(i < testAL.size()-1 ? ", " : "")+(i == testAL.size()-1 ? ")" : "");
							 users = testAL.get(i)+(i < testAL.size()-1 ? ", " : "");
						}
						if(wfType.equalsIgnoreCase("Simple")) {
							nxtStgMsg = users;
						}
					}
					System.out.println("-=-=-=-=-nxtStgMsg=-=-=-=-attach=-="+nxtStgMsg);
					if(nxtStgMsg.trim().isEmpty())
						this.attachDocumentMsgstr="Document attached successfully. This document is forwarded to next team.";
					else if(nxtStgMsg.equalsIgnoreCase("Rules Failed")) {
						this.attachDocumentMsgstr="Document attached will not move to next stage because rules failed.";
					}
					else
						this.attachDocumentMsgstr="Document attached successfully. This document is forwarded to "+nxtStgMsg+".";
	        	}
	        }
	        

			// WorkflowManager
	        getAttachmentDOCDeails(userCustomerKey);
			this.attachDocumentMsgColor="blue";
			if(actionType.equalsIgnoreCase("Save"))
				this.attachDocumentMsgstr="Document attached successfully.";
			startWFBtnDisableFlag = true;
			saveBtnDisableFlag = true;

		}

		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			this.attachDocumentMsgColor="red";
			this.attachDocumentMsgstr=Globals.getException(e);
		}
	}

	public void EachtimeBoxAction(boolean forEachtimeBoxStr) {

		try {
			System.out.println("forEachtimeBoxStr  ::" +forEachtimeBoxStr);

			if(!forEachtimeBoxStr) {

				if(timelineBoxStr) {
					System.out.println("timelineBoxStr  !!::" +timelineBoxStr);
					timeLineTableAL.clear();
					int k=0;
					WorkflowDataBean timeLineTablevalue = new WorkflowDataBean(k,choosewfNameStr,"","","","");
					timeLineTableAL.add(timeLineTablevalue);

				}

			}else {
				if(timelineBoxStr) {
					selListAction();

				}

			}
		}catch (Exception e) {
			// TODO: handle exception
		}



	}

	public void timelineBoxAction(boolean timelineBoxStr) {

		try {

			if(!timelineBoxStr) {


				timeLineTableAL.clear();


			}else {

				if(!forEachtimeBoxStr) {
					timeLineTableAL.clear();
					int k=0;
					WorkflowDataBean timeLineTablevalue = new WorkflowDataBean(k,choosewfNameStr,"","","","");
					timeLineTableAL.add(timeLineTablevalue);

				}
				selListAction();

			}
		}catch (Exception e) {
			// TODO: handle exception
		}


	}
	public void emptyAllvalues() {

		try {

			this.primaryFileName="";
			this.choosewfNameStr="";
			this.timeLineTableAL.clear();
			this.attachDocumentMsgColor="";
			this.attachDocumentMsgstr="";
			this.attachdocumentDetailsAL.clear();
			this.documentTextarea="";
			this.deadLinebeforeTextBox1Str="";
			this.deadLineTextBox1Str="";
			this.deadLineTextBox2Str="";
			this.listWorkflowAL.clear();
			this.sendalertStr=false;
			this.sendalertAfterdeadLineStr=false;
			this.sendalertsubseqStr=false;
			this.globlworkflowID = "";



		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	private String getattachmentFile() {
		String strng1 = "";
		String strng2 = "";
		Hashtable allDetailsHT=new Hashtable();
		Hashtable GetHT=new Hashtable();
		try
		{


			for (int i = 0; i < attachdocumentDetailsAL.size(); i++) {
				Hashtable rowHT=new Hashtable();
				System.out.println("attachdocumentDetailsAL :: "+attachdocumentDetailsAL.get(i).fileNameAttachStr);


				rowHT.put("AttachfileName", attachdocumentDetailsAL.get(i).fileNameAttachStr);
				rowHT.put("AttachfileType", attachdocumentDetailsAL.get(i).fileTypeAttachStr);
				rowHT.put("AttachfileSize", attachdocumentDetailsAL.get(i).fileSizeAttachStr);
				rowHT.put("AttachfileDate", attachdocumentDetailsAL.get(i).fileDateAttachStr);

				allDetailsHT.put("R"+i,rowHT);

				System.out.println("allDetailsHT Size:: "+allDetailsHT.size());
				System.out.println("allDetailsHT :: "+allDetailsHT);

				for (int k = 0; k < allDetailsHT.size(); k++) {








					//							GetHT=(Hashtable)allDetailsHT.get("R"+k);
					//							
					//							    String fileName = (String)GetHT.get("AttachfileName");
					//			                    String filetype = (String)GetHT.get("AttachfileType");
					//			                    String fileSize = (String)GetHT.get("AttachfileSize");
					//			                    String fileaddDate = (String)GetHT.get("AttachfileDate");
					//			                    String rowkey = "R"+k;
					//							System.out.println("allDetailsHT Size:: "+GetHT.get("AttachfileName"));
					//							  if (k == 0)
					//			                  {
					//			                      strng1 = "\"" + rowkey + "\":{\"FileName\":\"" + fileName + "\",\"FileType\":\"" + filetype + "\",\"FileSize\":\"" + fileSize + "\",\"File_AddDate\":\"" + fileaddDate + "\",\"Source_types\":\"" + "" + "\",\"Connection_Type\":\"" + "" + "\",\"SrcLink\":\"" + "" + "\",\"FilePath\":\"" + "" + "\"}";
					//			                  }
					//			                  else
					//			                  {
					//			                      strng1 = strng1 + "," + "\"" + rowkey + "\":{\"FileName\":\"" + fileName + "\",\"FileType\":\"" + filetype + "\",\"FileSize\":\"" + fileSize + "\",\"File_AddDate\":\"" + fileaddDate + "\",\"Source_types\":\"" + "" + "\",\"Connection_Type\":\"" + "" + "\",\"SrcLink\":\"" + "" + "\",\"FilePath\":\"" + "" + "\"}";
					//			                  }
				}

				int rowsLen = attachdocumentDetailsAL.size();
				String keywithrowlen = "\"TableLength\":\"" + rowsLen + "\"";
				strng2 = "{" + keywithrowlen + "," + strng1 + "}";

			}



		}catch (Exception e) {
			// TODO: handle exception
		}

		return strng2;
	}

	
	public void getAllAttributesFromWorkflow4AttachDoc(String wfID) {
		try {
			ArrayList<RuleAttributeDataModel> temp = new ArrayList<>();
			temp.addAll(this.rulesAttrDetailsAL);
			this.rulesAttrDetailsAL.clear();
			PropUtil prop=new PropUtil();
			String heirLevelXML=prop.getProperty("HIERARCHY_XML_FILE");
			String heirLeveldir=prop.getProperty("HIERARCHY_XML_DIR");
			Node workFlowN=null;
			
			Document xmlDoc=Globals.openXMLFile(heirLeveldir, heirLevelXML);
			String hierarchyID=wfID;
			workFlowN=Globals.getNodeByAttrVal(xmlDoc, "Workflow", "Hierarchy_ID", hierarchyID);
			ArrayList<RuleAttributeDataModel> rulesAttrDetailsAL = RulesManager.getRulesAttributeFromWF(workFlowN);
			if(rulesAttrDetailsAL== null || rulesAttrDetailsAL.isEmpty()) {
				return;
			}
				
			this.rulesAttrDetailsAL.addAll(rulesAttrDetailsAL);
			int i=0;
			for(RuleAttributeDataModel radm : rulesAttrDetailsAL) {
				String value = temp.size() > i ? temp.get(i).getAttrValue() : radm.getAttrValue();
				rulesAttrDetailsAL.get(i).setAttrValue(value);
				rulesAttrValueHT.put(radm.getAttributeName(), value);
//				System.out.println(i+"-=-=-=-=temp.get(i).getAttrValue()-=-=-=-=-="+temp.get(i).getAttrValue());
				i++;
			}
			System.out.println(hierarchyID+"-=-=-=-=rulesAttrDetailsAL-=-=-=-=-="+rulesAttrDetailsAL.size());
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public void getAllAttributesFromWorkflow4Stage(String docId) {
		try {
			rulesAttrDetailsAL.clear();
			PropUtil prop=new PropUtil();
			String heirLevelXML=prop.getProperty("HIERARCHY_XML_DIR");
			String docXmlFileName = prop.getProperty("DOCUMENT_XML_FILE");
			Document docXmlDoc=Globals.openXMLFile(heirLevelXML, docXmlFileName);
			Node workFlowN=null;
			Node docNd=Globals.getNodeByAttrVal(docXmlDoc, "Document", "Document_ID", docId);
			Element docEle = (Element)docNd;
			workFlowN = docEle.getElementsByTagName("Workflow").item(0);
			rulesAttrDetailsAL = RulesManager.getRulesAttributeFromWF(workFlowN);
			for(RuleAttributeDataModel radm : rulesAttrDetailsAL) {
				rulesAttrValueHT.put(radm.getAttributeName(), radm.getAttrValue());
			}
			System.out.println("-=-=-=-=rulesAttrDetailsAL-=-=-=-=-="+rulesAttrDetailsAL.size());
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public void saveAttributesValue() {
		for(RuleAttributeDataModel radm : rulesAttrDetailsAL) {
			System.out.println("-=====radm.getAttributeValue()====="+radm.getAttrDataType());
			String value = radm.getAttrValue();
			SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
			if(radm.getAttrDataType().equalsIgnoreCase("date")) {
				value = radm.getDateType().equalsIgnoreCase("currentdate") ? "CurrentDate" : sdf.format(radm.getDateValue());
			}
			rulesAttrValueHT.put(radm.getAttributeName(), value);
		}
	}
	
	
	
	public String getBIMetadataFile() {
		return BIMetadataFile;
	}


	public void setBIMetadataFile(String bIMetadataFile) {
		BIMetadataFile = bIMetadataFile;
	}

	public List<WorkflowDataBean> getWorkflowDataBeanAL() {
		return WorkflowDataBeanAL;
	}

	public void setWorkflowDataBeanAL(List<WorkflowDataBean> workflowDataBeanAL) {
		WorkflowDataBeanAL = workflowDataBeanAL;
	}


	public List<WorkflowDataBean> getWorkflowDataBeanAL2() {
		return WorkflowDataBeanAL2;
	}

	public ArrayList getCalendarDetailsAL() {
		return calendarDetailsAL;
	}

	public void setWorkflowDataBeanAL2(List<WorkflowDataBean> workflowDataBeanAL2) {
		WorkflowDataBeanAL2 = workflowDataBeanAL2;
	}


	public ArrayList<RuleAttributeDataModel> getRulesAttrDetailsAL() {
		return rulesAttrDetailsAL;
	}


	public void setRulesAttrDetailsAL(ArrayList<RuleAttributeDataModel> rulesAttrDetailsAL) {
		this.rulesAttrDetailsAL = rulesAttrDetailsAL;
	}


	public boolean isSaveBtnDisableFlag() {
		return saveBtnDisableFlag;
	}


	public void setSaveBtnDisableFlag(boolean saveBtnDisableFlag) {
		this.saveBtnDisableFlag = saveBtnDisableFlag;
	}


	public boolean isStartWFBtnDisableFlag() {
		return startWFBtnDisableFlag;
	}


	public void setStartWFBtnDisableFlag(boolean startWFBtnDisableFlag) {
		this.startWFBtnDisableFlag = startWFBtnDisableFlag;
	}

}