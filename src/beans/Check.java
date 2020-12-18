package beans;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;
import java.util.Random;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.google.api.services.drive.Drive;

import managers.GoogleStorageMaintenace;
import managers.LoginProcessManager;
import utils.Globals;
import utils.Inventory;
import utils.PropUtil;

public class Check {

	public static void main(String[] args) throws GeneralSecurityException, IOException {
		// TODO Auto-generated method stub
		try {
			/*Random rd = new Random();
			RandomString rs = new RandomString(16, rd);
		System.out.println(rs.nextString());*/
			
//			String randomnum=  randomGenstring();
//			System.out.println("-=-=-=-=$########-=-= :"+randomnum);
//			
//			System.out.println("-=-=-=-=-=-="+Inventory.retrieve("GKv8dPLQxWb1IoUUOsNRLg=="));
//			LoginProcessManager lpm = new LoginProcessManager();
//			String[] recipients = {"melinda@cygnussoftwares.com"};
//			String subject = "Escalation is initiated.";
//			String message = "";
//			BufferedReader buffReader = new BufferedReader(new FileReader(new File("C:\\Users\\Officepc\\Downloads\\Escalate.html")));
//			StringBuilder sb = new StringBuilder();
//		    String line = buffReader.readLine();
//		    while (line != null) {
//		      sb.append(line).append("\n");
//		      line = buffReader.readLine();
//		    }
//		    message = sb.toString();
//		    String from = "sysadmin@cygnussoftwares.com";
//		    String emailPassword = "Cygnus$admin";
//		    PropUtil prop = new PropUtil();
//			//			String hierLevelXML = prop.getProperty("DOCUMENT_XML_FILE");
//			String hierLeveldir = prop.getProperty("HIERARCHY_XML_DIR");
//			String hierlink4Mail= prop.getProperty("HIERARCHY_BASE_URL");
//			String timeFormat = prop.getProperty("DATE_FORMAT");
//			SimpleDateFormat sdf = new SimpleDateFormat(timeFormat);
//			String currDate = sdf.format(new Date());
//			message = message.replace("$User$", "Melinda").replace("$DocName$", "Competition Analysis HR.pdf").replace("$LastAccessMember$", "Admin").
//					replace("$CurrDate$", currDate).replace("$DueDate$", "10/08/2018").replace("$Days$", "2 Day(s)").replace("$WorkflowName$", "Cap Ex WF").
//					replace("$ToStage$", "Contracts").replace("$Members$", "bharath@cygnussoftwares.com").replace("$Notes$", "");
//			lpm.postMail(recipients, subject, message, from, emailPassword, null, hierlink4Mail, "");
//		sendPasswordMail("vishnu@cygnussoftwares.com");
		/*Drive drive = GoogleStorageMaintenace.getGoogleDriveObj("ppvishnu60@gmail.com");
		String id = GoogleStorageMaintenace.getFileIDByName(drive, "accel-mso-26_90", "application/vnd.google-apps.folder");
		System.out.println("-=-=-=-=-=-="+id);*/
			
			System.out.println(Inventory.encrypt4DotNet("BP_67430"));
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	public static String randomGenstring() {
		
		
		char[] chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();
		Random rnd = new Random();
		StringBuilder sb = new StringBuilder((100000 + rnd.nextInt(900000)) + "");
		for (int i = 0; i < 5; i++)
		    sb.append(chars[rnd.nextInt(chars.length)]);

		return sb.toString();
		
		
	}
	
	
	public static void name(String customerKey) throws Exception {
		LoginProcessManager lgn = new LoginProcessManager();
		Document doc = Globals.openXMLFile("", "");
		NodeList docNdList = doc.getElementsByTagName("Document"); 
		for(int i=0;i<docNdList.getLength();i++) {
			if(docNdList.item(0).getNodeType() == Node.ELEMENT_NODE) {
				Element docEle = (Element) docNdList.item(0); 
				String custKey = docEle.getAttribute("CustomerKey");
				if(customerKey.equals(custKey)) {
					Element wfEle = (Element) docEle.getElementsByTagName("Workflow").item(0);
					wfEle.getAttribute("Current_Stage_No");
					Hashtable detailsHT = lgn.retriveStageDetailsFromXML(wfEle, wfEle.getAttribute("Current_Stage_No"), "");
					
				}
			}
		}
	}
	

}
