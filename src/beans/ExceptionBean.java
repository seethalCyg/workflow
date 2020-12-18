
package beans;


import java.io.IOException;

import javax.faces.FacesException;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;


@ManagedBean(name = "exceptionBean")
@SessionScoped

public class ExceptionBean implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	// Code Change -- Devan 22Mar13 -- Minor SOP Changes
	//
	
	//Start Code Change -- BHARATH 190213 
	public String msg = "";
	public String msgReset = "";
	public String testConnection="";
	public String getTestConnection() {
		return testConnection;
	}



	public void setTestConnection(String testConnection) {
		this.testConnection = testConnection;
	}



	public String blank = "";

	/**
	 * @return the msgReset
	 */
	public boolean flag4FullLog=false;
	
	public boolean isFlag4FullLog() {
			return flag4FullLog;
		}



		public void setFlag4FullLog(boolean flag4FullLog) {
			this.flag4FullLog = flag4FullLog;
		}

	
	
	public String msg1="";

	public String getMsg1() {
			System.out.println("In Exception Bean getMsg1(): Message: " + msg1);
			return msg1;
		}


		public void setMsg1(String msg1) {
			System.out.println("In Exception Bean setMsg1(): Message: " + msg1);
			this.msg1 = msg1;
		}
	
	
	
	public String getMsgReset() {
		msg = "";
		return msgReset;
	}


	/**
	 * @param msgReset the msgReset to set
	 */
	public void setMsgReset(String msgReset) {
		this.msgReset = msgReset;
		msg = "";
	}


	public String getMsg() {

		System.out.println("Exception Bean: Message: " + msg);
		return msg;
	}


	public void setMsg(String msg) {
		System.out.println("In Exception Bean setMsg(): Message: " + msg);
		this.msg = msg;
	}

	public void setBlank(String blank) {
		System.out.println("In Exception Bean setNull().");
		blank = "";
		this.msg = "";
	}
	
	public String getBlank() {
		System.out.println("In Exception Bean getNull().");
		this.msg = "";
		return "";
	}

	//End Code Change -- BHARATH 190213 

}
