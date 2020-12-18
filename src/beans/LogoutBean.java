package beans;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;
import java.util.Map;

import javax.faces.FacesException;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import utils.Globals;
import utils.PropUtil;

@ManagedBean


public class LogoutBean<HttpSession> implements java.io.Serializable{

	public String getRedirect(String userName, Hashtable loginDetHT,boolean flag) throws Exception{
	//	 HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);  // code change pandian 24Aug2013
			//session.invalidate();
		 
		 
		 if(flag){
		 HttpSession session1 = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false); // code change pandian 24Aug2013
		 if(session1!=null){
			 javax.servlet.http.HttpSession ses=(javax.servlet.http.HttpSession)session1;
				System.out.println("HttpSession session already created in Logout Bean");
				System.out.println("session CreationTime : " + ses.getCreationTime());
				System.out.println("session sessionId: " + ses.getId());
				System.out.println("session LastAccessedTime: " + ses.getLastAccessedTime());
				System.out.println("session MaxInactiveInterval: " + ses.getMaxInactiveInterval());
				System.out.println("session isNew: " + ses.isNew());
				
				//Start code change Gokul 14SEP2013
//				FacesContext context1 = FacesContext.getCurrentInstance();	
//				Map sessionMap1 = context1.getExternalContext().getSessionMap();
//				CrtRptDefnBean crdb = (CrtRptDefnBean) sessionMap1.get("crtRptDefnBean");
//				if(crdb==null)
//				{
//					 ses.invalidate();
//				}else if(crdb.getDirectURL().equalsIgnoreCase("")||crdb.getDirectURL().equalsIgnoreCase(null)||(!crdb.getReportParam().contains("CASE_RESULT")&&!crdb.getReportParam().contains("PRE_POST_VALIDATION"))){//Code Change Gokul 16OCT2013
//					
//			 ses.invalidate();
//				}//End code change Gokul 14SEP2013
				
				ses.invalidate();
		 }
		
			FacesContext ctx = FacesContext.getCurrentInstance();         
		    ExternalContext extContext = ctx.getExternalContext();
		    String url = extContext.encodeActionURL(ctx.getApplication().getViewHandler().getActionURL(ctx, "/index.xhtml"));
		    extContext.redirect(url);
		 }
		    
		    FileInputStream fis = null;
			FileOutputStream fos = null;
			Document doc = null;
			DocumentBuilderFactory dbf = null;
			DocumentBuilder db = null;
			PropUtil prop=new PropUtil();
			String trackingFileName;
			String HIERARCHY_XML_DIR;
		    
		    try {
	           
	            
	            trackingFileName=prop.getProperty("TRACKING_XML_FILE");
	            HIERARCHY_XML_DIR=prop.getProperty("HIERARCHY_XML_DIR");
	            fis = new FileInputStream(HIERARCHY_XML_DIR
						+ trackingFileName);
				Element em = null;
				DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory
						.newInstance();
				DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
				doc = docBuilder.parse((fis));
				
			
				System.out.println("username==========>>>"+	userName);	
				
		NodeList nodl=	doc.getElementsByTagName(userName);
		
//	System.out.println("==========>>>"+	nodl.item(0).getLastChild());
				
//				Element em1 = doc.createElement("Login_ID");
//				Element em1 = doc.createElement(username);
//				
//				node.appendChild(em1);
	
	String format=prop.getProperty("DATE_FORMAT");
	
	

	SimpleDateFormat formatter = new SimpleDateFormat(format);
				Date curDate=new Date();
				String logoutDate=String.valueOf(formatter.format(curDate));
			
				 System.out.println();
				 
				 
			Hashtable ht=(Hashtable) loginDetHT.get(userName);
			
			Node nd= Globals.getNodeByAttrVal(doc, "User", "Unique_ID", String.valueOf(ht.get("Unique_ID")));
			Element ed=(Element)nd;
			 
	            
			String loginTime=ed.getAttribute("Login_Date");
			
			Date login=formatter.parse(loginTime);

			long timeSpent=curDate.getTime()-login.getTime();
			
			System.out.println("LogoutDate:"+logoutDate+" / TimeSpent:"+timeSpent);
			
		String timeinMNS=Globals.convert2minsandSec(timeSpent);
		
		ed.setAttribute("Logout_Date", logoutDate);
		
		ed.setAttribute("Time_Spent",timeinMNS); 
		
		Globals.writeXMLFile(doc, HIERARCHY_XML_DIR, trackingFileName);
	            
	        } catch (IOException ioe) {
	            throw new FacesException(ioe);
	        }
// 		return redirect;
			return "";
 	}
 

}
