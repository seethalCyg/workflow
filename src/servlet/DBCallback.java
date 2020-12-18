package servlet;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import utils.PropUtil;

/**
 * Servlet implementation class SMCallback
 */
public class DBCallback extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DBCallback() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		try {
		PropUtil prop = new PropUtil();
		String smDir = prop.getProperty("SMARTSHEET_DIR");
		String error = request.getParameter("error");
		String state = request.getParameter("state");
		if(error != null && !error.isEmpty()) {
			/*invalid_client 	The client information is invalid. Ensure your client id is correct.
			invalid_grant 	The authorization code or refresh token is invalid or expired or the hash value does not match the app secret and/or code.
			invalid_request 	The request parameters are invalid or missing.
			invalid_scope 	One or more of the requested access scopes is invalid. Please check the list of access scopes.
			unsupported_grant_type 	grant_type must equal authorization_code or refresh_token.
			unsupported_response_type 	response_type must be set to code.*/
			String errStr = request.getParameter("error_description");
			
			response.getWriter().append("Dropbox account is not authenticated. Because, "+errStr+" You can close this tab now.");
			File file = new File(smDir+state+".txt");
			BufferedWriter writer = new BufferedWriter(new FileWriter(file));
			writer.write("error:"+errStr);
			writer.close();
			return;
		}
		
		System.out.println("-=-=-=-=-=-=-=-=-=-=-="+request.getRequestURL().toString()+"?"+request.getQueryString());
		File file = new File(smDir+state+".txt");
		BufferedWriter writer = new BufferedWriter(new FileWriter(file));
		writer.write(request.getRequestURL().toString()+"?"+request.getQueryString());
		writer.close();
		response.getWriter().append("Dropbox account is authenticated successfully. You can close this tab now.");
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
