package servlet;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class CreateForms
 */
public class CreateForms extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateForms() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			String formType = request.getParameter("formtype");
			String fieldType = request.getParameter("fieldtype");
			if(request.getReader() == null) {
				return;
			}
			BufferedReader buff = request.getReader();
			StringBuffer jsonObjStr = new StringBuffer();
			String line = "";
			while((line = buff.readLine()) != null) {
				jsonObjStr.append(line); 
			}
//			String is = request.getParameter("jsonobj");
			ServletContext servletContext = getServletContext();
			String contextPath = servletContext.getRealPath(File.separator);
			System.out.println(jsonObjStr+"-=-=-=-=-=-="+contextPath);
			response.getWriter().append("success");
			
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		//doGet(request, response);
	}

}
