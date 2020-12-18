
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


@ManagedBean(name = "headerBean")
@SessionScoped
public class HeaderBean implements java.io.Serializable {


	String menuname="WFadminscreen.xhtml";  // code change Menaka 27MAR2014
	
	private String urlParameter = "";
	

	public String getUrlParameter() {
		return urlParameter;
	}

	public void setUrlParameter(String urlParameter) {
		this.urlParameter = urlParameter;
	}

	public String getMenuname() {
	
		return menuname;
	}

	public void setMenuname(String menuname) {
		this.menuname = menuname;
	}
	
	
	
	public void processMenu(String menuname)
	{
		System.out.println("Entering: "
				+ new Exception().getStackTrace()[0].getClassName() + "."
				+ new Exception().getStackTrace()[0].getMethodName());
		
		try{
			FacesContext ctx = FacesContext.getCurrentInstance();
			ExternalContext extContext = ctx.getExternalContext();
			Map sessionMap = extContext.getSessionMap();
			HierarchyBean hyb = (HierarchyBean) sessionMap.get("hierarchyBean");
			HierarchyDBBean hyDBb = (HierarchyDBBean) sessionMap.get("hierarchyDBBean");
			DashboardBean dsbn = (DashboardBean) sessionMap.get("dashboardBean");
			
			HttpServletRequest req = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
			this.urlParameter=req.getParameter("advanceWorkflow")== null || req.getParameter("advanceWorkflow").length()<= 0 ? "false" :req.getParameter("advanceWorkflow");
			System.out.println("******headerBean*******urlParameter*****555555555555**************:"+urlParameter);
			
		/*	if(this.urlParameter.equalsIgnoreCase("true")){
			
				dsbn.setCloseDisplay("none");
				dsbn.setCloseDisplayADV("block");
				
			}else {
				
				dsbn.setCloseDisplay("block");
				dsbn.setCloseDisplayADV("none");*/
			
			
			if(menuname.equals("SegmentAdmin.xhtml")){
				hyb.setFormName4OpenAddSegPopup("segAdmin");
				
			}else if(menuname.equals("RIConstraintsPopUp.xhtml")){
				
				hyb.setFormName4OpenAddSegPopup("RIConstraints");
				hyb.setCmngFromPage4RIConstraints("AdminRulepopup");
			}else{
				hyb.setFormName4OpenAddSegPopup("HRYTree");
				hyb.setCmngFromPage4RIConstraints("RI-RHDeleteProcess");
			}
			
			if(menuname.equals("FactPopup.xhtml")){  // code change Menaka 07APR2014
				if(hyDBb!=null)
				hyDBb.fromFactPopup = "block";
				hyb.setCmgFromFactpage("factConfig");
				hyb.setFormID("factConfig");
			}else{
				if(hyDBb!=null)
				hyDBb.fromFactPopup = "none";
				hyb.setCmgFromFactpage("HRYTree");
				hyb.setFormID("HRYTree");
			}
			
			if(menuname.equals("viewingVersion.xhtml")){  // code change Menaka 03APR2014
				hyb.setCmgFromViewVersion("HRYTree1");
			}else {
				hyb.setCmgFromViewVersion("HRYTree");
			}
			
			
			this.menuname = menuname;
			
			if(this.menuname.equals("Hierarchytree.xhtml")){  // code change Menaka 28MAR2014
				this.menuname="WFadminscreen.xhtml";
			}
			
			
		String url = extContext.encodeActionURL(ctx.getApplication().getViewHandler()
				.getActionURL(ctx, "/" + menuname));

		if(ctx.getResponseComplete()){
			System.out.println("ctx.getResponseComplete()-->"+ctx.getResponseComplete());
			
		}else{
			
			try {
			extContext.redirect(url);
		} catch (IOException ioe) {
			throw new FacesException(ioe);
		}
			
				System.out.println("Error While Testing the Session you are working diffrent session");
				
		}
		
		
		}catch(Exception e){
			e.printStackTrace();
		}
		
		System.out.println("Exiting: "
				+ new Exception().getStackTrace()[0].getClassName() + "."
				+ new Exception().getStackTrace()[0].getMethodName());
	}
	



}
