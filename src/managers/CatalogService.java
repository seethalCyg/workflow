/**
 * WebCatalogService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package managers;


import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import beans.ExceptionBean;


import oracle.bi.webservices.v6.*;
import utils.Globals;


//import oracle.webservices.transport.ClientTransport;
//import oracle.webservices.OracleStub;

public class CatalogService {

	private SAWSessionServiceSoap sessionService;
	private String sessionID;
	private String serverURL; // Example: "http://system14:7001/analytics/saw.dll";
	private String rootDir; // /Shared
	private String BIUserName; //= "weblogic";
	private String BIPassword; // = "weblogic1";
	static WebCatalogServiceSoap m_catalog;
	private WebCatalogServiceSoap catalog;
	private String logonException;


	public CatalogService(String serverURL, String rootDir, String BIUserName, String BIPassword,
			ExceptionBean exceptionBean,String generatelogFileName) {

		System.out.println("Entering: " + new Exception().getStackTrace()[0].getClassName() + "."
				+ new Exception().getStackTrace()[0].getMethodName());
		
	
		try {
			this.serverURL = serverURL;
			this.BIUserName = BIUserName;
			this.BIPassword = BIPassword;
			this.rootDir = rootDir;

			System.out.println("serverURL: " + serverURL );//+ "; BIUserName: " + BIUserName);// + "; BIPassword: "	+ BIPassword);
			
			
			WebCatalogServiceLocator webCatalogServiceLocator = new WebCatalogServiceLocator();

			this.catalog = webCatalogServiceLocator.getWebCatalogServiceSoap(new URL(serverURL
					+ "?SoapImpl=webCatalogService"));

			System.out.println("Exiting: " + new Exception().getStackTrace()[0].getClassName() + "."
					+ new Exception().getStackTrace()[0].getMethodName());
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Globals.getException(exceptionBean, e);
		
		}
	}


	public String logon(ExceptionBean exceptionBean,boolean clearCursorFlag) throws Exception {

		try {
			System.out.println("Entering: " + new Exception().getStackTrace()[0].getClassName() + "."
					+ new Exception().getStackTrace()[0].getMethodName());

			SAWSessionParameters sessionParams = new SAWSessionParameters();
			sessionParams.setUserAgent("Mozilla/5.0 (compatible; MSIE 8.0; Windows NT 6.1; WOW64; Trident/5.0)");

			SAWSessionServiceLocator awsessionservicelocator = new SAWSessionServiceLocator();
			
			
			
			
		
			sessionService = awsessionservicelocator.getSAWSessionServiceSoap(new URL(serverURL
					+ "?SoapImpl=nQSessionService"));
			
			//System.out.println("BIUserName: " + BIUserName + "; BIPassword: " + BIPassword);

			System.out.println("Trying to logon, authenticate and create a Session...");

			AuthResult authResult = sessionService.logonex(BIUserName, BIPassword, sessionParams);
			
			sessionID = authResult.getSessionID();
//			String aa = sessionService.
		
			
			System.out.println("Logon Successful. Generated Session ID: " + sessionID);

			System.out.println("Exiting: " + new Exception().getStackTrace()[0].getClassName() + "."
					+ new Exception().getStackTrace()[0].getMethodName());

		} catch (Exception e) {

			// Code Change -- Devan 09Mar13 -- Code change Comments
			setLogonException(Globals.getException(exceptionBean, e));
			return null;
		}

		return sessionID;

	}


	public void logoff() throws Exception {

		System.out.println("Entering: " + new Exception().getStackTrace()[0].getClassName() + "."
				+ new Exception().getStackTrace()[0].getMethodName());

		sessionService.logoff(sessionID);

		sessionService = null;

		System.out.println("Exiting: " + new Exception().getStackTrace()[0].getClassName() + "."
				+ new Exception().getStackTrace()[0].getMethodName());

	}



	/**
	 * @return the logonException
	 */
	public String getLogonException() {
		return logonException;
	}


	/**
	 * @param logonException
	 *            the logonException to set
	 */
	public void setLogonException(String logonException) {
		this.logonException = logonException;
	}

}