package beans;

import java.io.IOException;
import java.util.Map;

import javax.faces.FacesException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.swing.JOptionPane;
import utils.PropUtil;
@ManagedBean(name = "sessionExpireBean")
@SessionScoped
public class SessionExpireBean<HttpSession> implements java.io.Serializable{

	/**
	 * @param args
	 */
	
	
	private String checkSessionexpire="";
//	private String dashBoardUrl="";
//	public String getDashBoardUrl() {
//		return dashBoardUrl;
//	}
//
//
//	public void setDashBoardUrl(String dashBoardUrl) {
//		this.dashBoardUrl = dashBoardUrl;
//	}
	public String getCheckSessionexpire() {	
		
		
//		System.out.println("Entering: " + new Exception().getStackTrace()[0].getClassName() + "."
//				+ new Exception().getStackTrace()[0].getMethodName());
		
		HttpServletRequest req = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
		String reqadvanceWorkflow=req.getParameter("advanceWorkflow")== null || req.getParameter("advanceWorkflow").length()<= 0 ? "false" :req.getParameter("advanceWorkflow");
		String editwrkflowflag=req.getParameter("editAdvanceWorkflow")== null || req.getParameter("editAdvanceWorkflow").length()<= 0 ? "false" :req.getParameter("editAdvanceWorkflow");
////////////////
try { 
	//code change pandian  08AUG13
//for checking same session or diffrent session

	
	
	
	
	
	
	System.out.println(editwrkflowflag+"**********reqadvanceWorkflow****222222222222222222********:"+reqadvanceWorkflow);
	
	
	if(reqadvanceWorkflow.equalsIgnoreCase("true")|| editwrkflowflag.equalsIgnoreCase("true")){
		

		System.out.println("**********reqadvanceWorkflow****11111********:"+reqadvanceWorkflow);
	}
	else {
		
FacesContext context = FacesContext.getCurrentInstance();                  //code change pandian  08AUG13
Map sessionMap = context.getExternalContext().getSessionMap();
LoginBean lb = (LoginBean) sessionMap.get("loginBean");
String httpSessionID=lb.getHttpSessionID();
HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);

//PropUtil propUtil = new PropUtil();
//this.dashBoardUrl= propUtil.getProperty("DASHBOARD_URL");
//System.out.println("login id====>"+lb.username);

if(session!=null){
javax.servlet.http.HttpSession ses=(javax.servlet.http.HttpSession)session;

if(httpSessionID.equalsIgnoreCase(ses.getId())){       
//System.out.println("You are working in the same session");

}else{  // redirect to logout page

FacesContext ctx = FacesContext.getCurrentInstance();
ExternalContext extContext = ctx.getExternalContext();
String url = extContext.encodeActionURL(ctx.getApplication()
		.getViewHandler().getActionURL(ctx, "/" + "index.xhtml"));

try {
	extContext.redirect(url);
} catch (IOException ioe) {
	throw new FacesException(ioe);
}
//LogoutBean logout = new LogoutBean<>();
//if(lb!=null){
//	logout.getRedirect(lb.username, lb.loginDetHT);
//}

System.out.println("You are working in different session");

}


}
}

} catch (Exception e) {
	//TODO Auto-generated catch block
//e.printStackTrace();
if(reqadvanceWorkflow.equalsIgnoreCase("true")){
		

		System.out.println("**********reqadvanceWorkflow****%%%%%%%%%%%%********:"+reqadvanceWorkflow);
	}else {
		
	FacesContext ctx = FacesContext.getCurrentInstance();
	ExternalContext extContext = ctx.getExternalContext();
	String url = extContext.encodeActionURL(ctx.getApplication()
	.getViewHandler().getActionURL(ctx, "/" + "index.xhtml"));
	Map sessionMap = ctx.getExternalContext().getSessionMap();
	LoginBean lb = (LoginBean) sessionMap.get("loginBean");
	if(ctx.getResponseComplete()){
//		if(lb!=null)
//			System.out.println("===>"+lb.username);
		System.out.println("ctx.getResponseComplete()-->"+ctx.getResponseComplete());
		
	}else{
		
		try {
			
			extContext.redirect(url);
			} catch (IOException ioe) {
			throw new FacesException(ioe);
			}
			System.out.println("Error While Testing the Session. You are working in different sessionssssss");
			
	}
	//extContext.responseReset();
	}
		
		

//System.out.println("Exiting: " + new Exception().getStackTrace()[0].getClassName() + "."
//		+ new Exception().getStackTrace()[0].getMethodName());
		
	
}
return checkSessionexpire;
	}


	public void setCheckSessionexpire(String checkSessionexpire) {
		this.checkSessionexpire = checkSessionexpire;
	}


	public SessionExpireBean(){
		
//		
//////////////////
//try {  //code change pandian  08AUG13
////for checking same session or diffrent session
//
//FacesContext context = FacesContext.getCurrentInstance();                  //code change pandian  08AUG13
//Map sessionMap = context.getExternalContext().getSessionMap();
//LoginBean lb = (LoginBean) sessionMap.get("loginBean");
//String httpSessionID=lb.getHttpSessionID();
//HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
//
//if(session!=null){
//javax.servlet.http.HttpSession ses=(javax.servlet.http.HttpSession)session;
//
//if(httpSessionID.equalsIgnoreCase(ses.getId())){       
//	System.out.println("you are working same session");
//	

//}else{  // redirect to logout page
//	
//	FacesContext ctx = FacesContext.getCurrentInstance();
//	ExternalContext extContext = ctx.getExternalContext();
//	String url = extContext.encodeActionURL(ctx.getApplication()
//			.getViewHandler().getActionURL(ctx, "/" + "logout.xhtml"));
//	
//	try {
//		extContext.redirect(url);
//	} catch (IOException ioe) {
//		throw new FacesException(ioe);
//	}
//
//	
//	System.out.println("you are working diffrent session");
//	
//}
//
//
//}
//
//
//} catch (Exception e) {
////TODO Auto-generated catch block
//
//FacesContext ctx = FacesContext.getCurrentInstance();
//ExternalContext extContext = ctx.getExternalContext();
//String url = extContext.encodeActionURL(ctx.getApplication()
//.getViewHandler().getActionURL(ctx, "/" + "logout.xhtml"));
//
//try {
//extContext.redirect(url);
//} catch (IOException ioe) {
//throw new FacesException(ioe);
//}
//System.out.println("Error While Testing the Session you are working diffrent session");
//
//}
//
//////////////////

	}
	
	
	public String isSameSession() {
		
		
//		System.out.println("Entering: " + new Exception().getStackTrace()[0].getClassName() + "."
//				+ new Exception().getStackTrace()[0].getMethodName());
		
////////////////
		
		String isSameSession="";
try {  //code change pandian  08AUG13
//for checking same session or diffrent session

FacesContext context = FacesContext.getCurrentInstance();                  //code change pandian  08AUG13
Map sessionMap = context.getExternalContext().getSessionMap();
LoginBean lb = (LoginBean) sessionMap.get("loginBean");
String httpSessionID=lb.getHttpSessionID();
//System.out.println("===>"+lb.username);
HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);

if(session!=null){
javax.servlet.http.HttpSession ses=(javax.servlet.http.HttpSession)session;

if(httpSessionID.equalsIgnoreCase(ses.getId())){       
//System.out.println("You are working in the same session");

}else{  // redirect to logout page

FacesContext ctx = FacesContext.getCurrentInstance();
ExternalContext extContext = ctx.getExternalContext();
String url = extContext.encodeActionURL(ctx.getApplication()
	.getViewHandler().getActionURL(ctx, "/" + "index.xhtml"));

try {
extContext.redirect(url);
} catch (IOException ioe) {
throw new FacesException(ioe);
}


System.out.println("You are working in different session");

}


}


} catch (Exception e) {
//TODO Auto-generated catch block
//e.printStackTrace();
FacesContext ctx = FacesContext.getCurrentInstance();
if(ctx!=null){
ExternalContext extContext = ctx.getExternalContext();
String url = extContext.encodeActionURL(ctx.getApplication()
.getViewHandler().getActionURL(ctx, "/" + "index.xhtml"));
Map sessionMap = ctx.getExternalContext().getSessionMap();
LoginBean lb = (LoginBean) sessionMap.get("loginBean");
//if(lb!=null)
//	System.out.println("===>"+lb.username);
if(ctx.getResponseComplete()){
//	if(lb!=null)
//		System.out.println("===>"+lb.username);
	System.out.println("ctx.getResponseComplete()-->"+ctx.getResponseComplete());
	
}else{

	try {
		extContext.redirect(url);
		} catch (IOException ioe) {
		throw new FacesException(ioe);
		}
	
		System.out.println("Error While Testing the Session.You are working diffrent session");
		
}
}
//extContext.responseReset();


isSameSession="redirect";


}

//System.out.println("Exiting: " + new Exception().getStackTrace()[0].getClassName() + "."
//		+ new Exception().getStackTrace()[0].getMethodName());
		
	return isSameSession;	
	}
	
	
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
