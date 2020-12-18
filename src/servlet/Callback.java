package servlet;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.GeneralSecurityException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpConnection;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.drive.model.User;

import test.ApiCredentialManager;
import test.Authorizer;
import utils.Globals;
import utils.PropUtil;

/**
 * Servlet implementation class Callback
 */
public class Callback extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Callback() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//    	System.out.println("-=-=-=-=-=-=-=-=-=-=-="+request.getParameter("code"));
		try {
			System.out.println("-=-=-=-=-=-=-=-=-=-=-="+request.getParameter("code"));
			String code = request.getParameter("code");
			String state = request.getParameter("state");
			String userName = state.split("=")[1].split("~~")[0];
			String hierID = state.split("=")[1].split("~~")[1];
			PropUtil prop=new PropUtil();
			String configFileName=prop.getProperty("HIERARCHY_CONFIG_FILE");
			String hierLeveldir=prop.getProperty("HIERARCHY_XML_DIR");
			String hierLevelXML=prop.getProperty("HIERARCHY_XML_FILE");
			Document doc=Globals.openXMLFile(hierLeveldir, configFileName);
			Element redirectEle = (Element)doc.getElementsByTagName("GoogleRedirectURL").item(0);
			String redirectURL = redirectEle.getAttribute("URL")==null || redirectEle.getAttribute("URL").trim().isEmpty() ? "http://localhost:8080/rbid/Callback" : redirectEle.getAttribute("URL");
//			String redirectURL = "http://localhost:8080/rbid/Callback";
			User a = new User();
			a.setEmailAddress(userName);
			HttpTransport httpTransport = null;
			try {
				httpTransport = GoogleNetHttpTransport.newTrustedTransport();
			} catch (GeneralSecurityException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
			Authorizer auth = new Authorizer(a, httpTransport, JSON_FACTORY, redirectURL);
			Credential cre = auth.saveCredentials(code);
			
			System.out.println("-=-=-=-=-=cre-=-=-=-=-=-="+cre.getAccessToken());
			Document xmlDoc=Globals.openXMLFile(hierLeveldir, hierLevelXML);
			Element ele = (Element)Globals.getNodeByAttrVal(xmlDoc, "Workflow", "Hierarchy_ID", hierID);
			ele.setAttribute("Storage", "External");
			ele.setAttribute("ExternalStorage", "Google");
			ele.setAttribute("ExternalStorageDetails", userName);
			Globals.writeXMLFile(xmlDoc, hierLeveldir, hierLevelXML);
			if(true)
				return;
			//    	String state = request.getParameter("state");
			//    	String userName = state.split("=")[1].split("~~")[0];
			//    	String hierID = state.split("=")[1].split("~~")[1];
			System.out.println(hierID+"-=-=-=-=-=-="+userName);
			//    	if(true)
			//    		return;
			String clientID = "529892980888-474215ug7erl2qoube52n81u6k7q26bi.apps.googleusercontent.com";
			String clientSecret = "c3jTsJAySjIPMjpUuos0TM88";
			//    	String redirectURL = "http://localhost:8080/rbid/Callback";
			String accessTokenURLSyntax = "https://www.googleapis.com/oauth2/v4/token?code=$$Code$$&client_id=$$ClientID$$&client_secret=$$ClientSecret$$&redirect_uri=$$RedirectURL$$&grant_type=authorization_code";
			accessTokenURLSyntax = accessTokenURLSyntax.replace("$$Code$$", code).replace("$$ClientID$$", clientID).replace("$$ClientSecret$$", clientSecret).replace("$$RedirectURL$$", redirectURL);

			HttpURLConnection connection = (HttpURLConnection) new URL(accessTokenURLSyntax).openConnection();
			connection.setRequestMethod("POST");
			connection.setDoOutput(true);
			OutputStreamWriter out = new OutputStreamWriter(
					connection.getOutputStream());

			InputStreamReader isr = new InputStreamReader(connection.getInputStream());
			char[] by = new char[1024];
			int read = 0;
			StringBuilder sb = new StringBuilder();
			while((read = isr.read(by, 0, by.length)) > 0) {
				sb.append(by);
			}

			System.out.println("-=-=-=-=sddfvd-=-=-=-="+sb.toString());
			System.out.println("-=-=-=-sdsd=-=-=-=-="+connection.getResponseMessage());
			out.close();

			JSONObject jObj = (JSONObject) new JSONParser().parse(sb.toString());
			String accessToken = (String) jObj.get("access_token");
			String refreshToken = (String) jObj.get("refresh_token");
			ApiCredentialManager api = ApiCredentialManager.getInstance();
			api.saveCredential(userName, accessToken, refreshToken);
			//			PropUtil prop=new PropUtil();
			//			String hierLevelXML=prop.getProperty("HIERARCHY_XML_FILE");
			//			String hierLeveldir=prop.getProperty("HIERARCHY_XML_DIR");
			//			Document xmlDoc=Globals.openXMLFile(hierLeveldir, hierLevelXML);
			//			Element ele = (Element)Globals.getNodeByAttrVal(xmlDoc, "Workflow", "Hierarchy_ID", hierID);
			ele.setAttribute("Storage", "External");
			ele.setAttribute("ExternalStorage", "Google");
			ele.setAttribute("ExternalStorageDetails", userName);
			Globals.writeXMLFile(xmlDoc, hierLeveldir, hierLevelXML);

		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
