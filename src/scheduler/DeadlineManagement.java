package scheduler;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;

import org.apache.http.impl.cookie.DateParseException;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import managers.LoginProcessManager;
import utils.Globals;
import utils.PropUtil;

public class DeadlineManagement implements Job {

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		// TODO Auto-generated method stub
		try {
			PropUtil prop = new PropUtil();
			//			String hierLevelXML = prop.getProperty("DOCUMENT_XML_FILE");
			String hierLeveldir = prop.getProperty("HIERARCHY_XML_DIR");
			String docXmlFileName = prop.getProperty("DOCUMENT_XML_FILE");
			Document docXmlDoc = Globals.openXMLFile(hierLeveldir, docXmlFileName);
			NodeList nodeList = docXmlDoc.getDocumentElement().getChildNodes();
            System.out.println("Root element :" + nodeList.getLength());
            LoginProcessManager lgn=new LoginProcessManager();
            for(int i=0;i<nodeList.getLength();i++){
            	Node node=nodeList.item(i);
            	
            	if(node.getNodeType()==Node.ELEMENT_NODE){
        			Hashtable hash=new Hashtable<>();
            		Element docEle = (Element) node;
        			/*getchildNodeswithAttribute(node,hash);
        			sendalert(hash);*/
            		if(docEle.getAttribute("Paused").equalsIgnoreCase("true")) {
            			continue;
            		}
        			hash = Globals.getAttributeNameandValHT(node);
        			Element wfEle = (Element)docEle.getElementsByTagName("Workflow").item(0);
        			Hashtable wfHt =Globals.getAttributeNameandValHT(wfEle); 
        			hash.putAll(wfHt);
        			String currStgNo = (String) wfHt.get("Current_Stage_No");
        			if(currStgNo.equalsIgnoreCase("Completed") || currStgNo.equalsIgnoreCase("Rules Failed") || currStgNo.equalsIgnoreCase("Cancel"))
        				continue;
        			Node stgNd = Globals.getNodeByAttrValUnderParent(docXmlDoc, wfEle, "Stage_No", currStgNo);
        			if(stgNd == null)
        				continue;
        			Hashtable stgHt =Globals.getAttributeNameandValHT(stgNd); 
        			hash.putAll(stgHt);
        			sendalert(hash, docXmlDoc, wfEle, lgn);
            	}
            	
            }
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	private void sendalert(Hashtable hash, Document docXmlDoc, Element wfEle, LoginProcessManager lgn) {
		// TODO Auto-generated method stub
		try {
			PropUtil prop = new PropUtil();
			String hierlink4Mail= prop.getProperty("HIERARCHY_BASE_URL");
			String current_stage_no=(String) hash.get("Current_Stage_No");
			boolean timeLineEachTeam=Boolean.parseBoolean((String) hash.get("TimelineEachteam"));
			boolean beforeDeadlineAlertFlag=Boolean.parseBoolean((String) hash.get("BeforeDeadlineAlertFlag"));
			String stage_No=(String) hash.get("Stage_No");
			String stageName = (String) hash.get("Stage_Name");
			String beforeDeadlineAlert=(String) hash.get("BeforeDeadlineAlert");
			String effectiveEndDate=(String) hash.get("EffectiveEndDate");
			boolean afterDeadlineAlertFlag=Boolean.parseBoolean((String) hash.get("AfterDeadlineAlertFlag"));
			String afterDeadlineAlertCount=(String) hash.get("AfterDeadlineAlertCount");
			String afterDeadlineAlert=(String) hash.get("AfterDeadlineAlert");
			String workflowEffEndDate=(String) hash.get("EffectiveEndDate");
			String hierarchyName = (String) hash.get("WorkflowName");
			String documentName = (String) hash.get("DocumentName");
			String excludeWeekends = (String) hash.get("ExecludeWeekEnd");
			String excludeHolidays = (String) hash.get("ExecludeHoliday");
			String excludeDates = (String) hash.get("ExcludeDates");
			Date localDate = new Date();
			Element docEle = (Element)wfEle.getParentNode();
			if(excludeWeekends != null && excludeWeekends.equalsIgnoreCase("true")) {				 
				SimpleDateFormat simpleDateformat = new SimpleDateFormat("E"); // the day of the week abbreviated
				String day = simpleDateformat.format(localDate);
				if(day.equalsIgnoreCase("sun") || day.equalsIgnoreCase("sat")) {
					return;
				}
			}
			
			SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
			if(excludeHolidays != null && excludeHolidays.equalsIgnoreCase("true") && excludeDates!=null && !excludeDates.isEmpty()) {
				String[] rangeDates = excludeDates.split(";");
				for(String dates : rangeDates) {
					String startDateStr = dates.split("~")[0];
					String endDateStr = dates.split("~")[1];
					Date startDate = null;
					Date endDate = null;
					try {
						startDate = sdf.parse(startDateStr);
						endDate = sdf.parse(endDateStr);
					}catch (DateTimeParseException e) {
						// TODO: handle exception
					}
					if(localDate.after(startDate) && localDate.before(endDate)) {
						return;
					}
				}
			}
			String currentDate=sdf.format(localDate);
			
			if(!current_stage_no.equalsIgnoreCase("Completed") && !current_stage_no.equalsIgnoreCase("Rules Failed") && !current_stage_no.equalsIgnoreCase("Cancel")){
				if(timeLineEachTeam==true){
					
					if(beforeDeadlineAlertFlag==true){
						 int beforeDeadlineAlertcount=Integer.valueOf(beforeDeadlineAlert);
						 String beforeDate = subtractDays(effectiveEndDate, beforeDeadlineAlertcount);
						 System.out.println(beforeDate+"-=-=-=-=-=-=-currentDate=-=-=-=-="+currentDate);
						 if(currentDate.equalsIgnoreCase(beforeDate)){
							 System.out.println("One day before Send Alert");
							 Hashtable mailDtailsHT=lgn.getMailDetailsFromConfig(docXmlDoc,wfEle,current_stage_no,"Admin", "Remind", "Next", false , "");
							 String mailSendBy=(String)mailDtailsHT.get("Mail_ID_Send_by");
							 String mailPassword=(String)mailDtailsHT.get("Mail_Password");
							 String mailMessage=(String)mailDtailsHT.get("Mail_Message");
							 String bobyofMail=mailMessage;
							 String subject = "Workflow Name / Document Name : "+hierarchyName+" / "+ documentName;
							 subject = mailDtailsHT.get("Subject") == null || String.valueOf(mailDtailsHT.get("Subject")).isEmpty() ? 
										subject : String.valueOf(mailDtailsHT.get("Subject"));
							 bobyofMail = bobyofMail.replace("$DueDate$", effectiveEndDate).replace("$ToStage$", stageName);
							 subject = subject.replace("$DueDate$", effectiveEndDate).replace("$ToStage$", stageName);
							 Hashtable stageDetailsHT = lgn.retriveStageDetailsFromXML(wfEle, current_stage_no, "");
							 Hashtable empNMHT =(Hashtable)stageDetailsHT.get("EmployeedetHT");
							 if(empNMHT!=null&&empNMHT.size()!=0)
							 {
									lgn.sendmailFromAdmin(empNMHT, subject, bobyofMail, hierlink4Mail, mailSendBy, mailPassword, docEle.getAttribute("CustomerKey"));
							 }
						 }
					} 
					
					if(afterDeadlineAlertFlag==true){
						int AfterDeadlineAlertcount=Integer.valueOf(afterDeadlineAlertCount);
						int dayInterval = Integer.valueOf(afterDeadlineAlert);
						Date dueDate = sdf.parse(effectiveEndDate);
						long diff = localDate.getTime() - dueDate.getTime();
						int diffInDays = (int) (diff / (1000 * 60 * 60 * 24));
						
						if(dueDate.before(localDate) && !sdf.format(dueDate).equalsIgnoreCase(sdf.format(localDate)) && diffInDays % (dayInterval+1) == 0) {
							System.out.println("One day after Send Alert");
							Hashtable mailDtailsHT=lgn.getMailDetailsFromConfig(docXmlDoc,wfEle,current_stage_no,"Admin", "Escalate", "Next", false , "");
							 String mailSendBy=(String)mailDtailsHT.get("Mail_ID_Send_by");
							 String mailPassword=(String)mailDtailsHT.get("Mail_Password");
							 String mailMessage=(String)mailDtailsHT.get("Mail_Message");
							 String bobyofMail=mailMessage;
							 String subject = "Workflow Name / Document Name : "+hierarchyName+" / "+ documentName;
							 subject = mailDtailsHT.get("Subject") == null || String.valueOf(mailDtailsHT.get("Subject")).isEmpty() ? 
										subject : String.valueOf(mailDtailsHT.get("Subject"));
							 bobyofMail = bobyofMail.replace("$DueDate$", effectiveEndDate).replace("$ToStage$", stageName);
							 subject = subject.replace("$DueDate$", effectiveEndDate).replace("$ToStage$", stageName);
							 Hashtable stageDetailsHT = lgn.retriveStageDetailsFromXML(wfEle, current_stage_no, "");
							 Hashtable empNMHT =(Hashtable)stageDetailsHT.get("EmployeedetHT");
							 for(int i=0;i<AfterDeadlineAlertcount;i++) {
								 if(empNMHT!=null&&empNMHT.size()!=0)
								 {
									 lgn.sendmailFromAdmin(empNMHT, subject, bobyofMail, hierlink4Mail, mailSendBy, mailPassword, docEle.getAttribute("CustomerKey"));
								 }
							 }
						}
						/*String afterDate = addDays(effectiveEndDate, AfterDeadlineAlertcount);
						if(currentDate.equalsIgnoreCase(afterDate)){
							 System.out.println("One day after Send Alert");
						 }*/
					}
					
					
				}else{
					if(beforeDeadlineAlertFlag==true){
						 int beforeDeadlineAlertcount=Integer.valueOf(beforeDeadlineAlert);
						 String beforeDate = subtractDays(workflowEffEndDate, beforeDeadlineAlertcount);
						 if(currentDate.equalsIgnoreCase(beforeDate)){
							 System.out.println("One day before Send Workflow Alert");
							 Hashtable mailDtailsHT=lgn.getMailDetailsFromConfig(docXmlDoc,wfEle,current_stage_no,"Admin", "Remind", "Next", false , "");
							 String mailSendBy=(String)mailDtailsHT.get("Mail_ID_Send_by");
							 String mailPassword=(String)mailDtailsHT.get("Mail_Password");
							 String mailMessage=(String)mailDtailsHT.get("Mail_Message");
							 String bobyofMail=mailMessage;
							 String subject = "Workflow Name / Document Name : "+hierarchyName+" / "+ documentName;
							 subject = mailDtailsHT.get("Subject") == null || String.valueOf(mailDtailsHT.get("Subject")).isEmpty() ? 
										subject : String.valueOf(mailDtailsHT.get("Subject"));
							 Hashtable stageDetailsHT = lgn.retriveStageDetailsFromXML(wfEle, current_stage_no, "");
							 Hashtable empNMHT =(Hashtable)stageDetailsHT.get("EmployeedetHT");
							 bobyofMail = bobyofMail.replace("$DueDate$", effectiveEndDate).replace("$ToStage$", stageName);
							 subject = subject.replace("$DueDate$", effectiveEndDate).replace("$ToStage$", stageName);
							 if(empNMHT!=null&&empNMHT.size()!=0)
							 {
									lgn.sendmailFromAdmin(empNMHT, subject, bobyofMail, hierlink4Mail, mailSendBy, mailPassword, docEle.getAttribute("CustomerKey"));
							 }
						 }
					}
					
					if(afterDeadlineAlertFlag==true){
						int AfterDeadlineAlertcount=Integer.valueOf(afterDeadlineAlertCount);
						int dayInterval = Integer.valueOf(afterDeadlineAlert);
						Date dueDate = sdf.parse(workflowEffEndDate);
						long diff = localDate.getTime() - dueDate.getTime();
						int diffInDays = (int) (diff / (1000 * 60 * 60 * 24));
						if(dueDate.before(localDate) && diffInDays % (dayInterval+1) == 0) {
							System.out.println("One day after Send Workflow Alert");
							Hashtable mailDtailsHT=lgn.getMailDetailsFromConfig(docXmlDoc,wfEle,current_stage_no,"Admin", "Escalate", "Next", false , "");
							 String mailSendBy=(String)mailDtailsHT.get("Mail_ID_Send_by");
							 String mailPassword=(String)mailDtailsHT.get("Mail_Password");
							 String mailMessage=(String)mailDtailsHT.get("Mail_Message");
							 String bobyofMail=mailMessage;
							 String subject = "Workflow Name / Document Name : "+hierarchyName+" / "+ documentName;
							 subject = mailDtailsHT.get("Subject") == null || String.valueOf(mailDtailsHT.get("Subject")).isEmpty() ? 
										subject : String.valueOf(mailDtailsHT.get("Subject"));
							 Hashtable stageDetailsHT = lgn.retriveStageDetailsFromXML(wfEle, current_stage_no, "");
							 Hashtable empNMHT =(Hashtable)stageDetailsHT.get("EmployeedetHT");
							 bobyofMail = bobyofMail.replace("$DueDate$", effectiveEndDate).replace("$ToStage$", stageName);
							 subject = subject.replace("$DueDate$", effectiveEndDate).replace("$ToStage$", stageName);
							 for(int i=0;i<AfterDeadlineAlertcount;i++) {
								 if(empNMHT!=null&&empNMHT.size()!=0)
								 {
									 lgn.sendmailFromAdmin(empNMHT, subject, bobyofMail, hierlink4Mail, mailSendBy, mailPassword, docEle.getAttribute("CustomerKey"));
								 }
							 }
						}
						/*String afterDate = addDays(workflowEffEndDate, AfterDeadlineAlertcount);
						if(currentDate.equalsIgnoreCase(afterDate)){
							 System.out.println("One day after Send Workflow Alert");
						 }*/
					}
				}
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
	}
	public String addDays(String endDate, int days) {
		String AfterDAte="";
		try {
			Date date;
			if(endDate.contains("/")){
				 date = new SimpleDateFormat("MM/dd/yyyy").parse(endDate);
			}else{
				date = new SimpleDateFormat("MM/dd/yyyy").parse(endDate);
			}
			Calendar cal=Calendar.getInstance();
			cal.setTime(date);
			cal.add(Calendar.DATE, days);
			AfterDAte = new SimpleDateFormat("MM/dd/yyyy").format(cal.getTime());
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
				
		return AfterDAte;
	}
	
	public String subtractDays(String endDate, int days) {
		
		String beforedat="";
		try {
			Date date;
			if(endDate.contains("/")){
				 date = new SimpleDateFormat("MM/dd/yyyy").parse(endDate);
			}else{
				date = new SimpleDateFormat("MM/dd/yyyy").parse(endDate);
			}
		 
		Calendar cal=Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, -days);
		beforedat = new SimpleDateFormat("MM/dd/yyyy").format(cal.getTime());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return beforedat;
	}

}
