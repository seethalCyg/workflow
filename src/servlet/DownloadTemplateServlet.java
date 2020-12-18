package servlet;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import managers.WorkflowManager;
import utils.Globals;
import utils.PropUtil;

/**
 * Servlet implementation class DownloadTemplateServlet
 */
@WebServlet(name = "DownloadTemplateServlet", urlPatterns = {"/downloadtemplate"})
public class DownloadTemplateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DownloadTemplateServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		response.getWriter().append("Served at: ").append(request.getContextPath());
		try {
		PrintWriter out = response.getWriter();  
		//			String filename = "Finance.docx";   
		//			String filepath = "E:\\Document WF WS\\Document WF\\DocumentVersion\\Finance_51\\3\\";   
		//response.setContentType("APPLICATION/OCTET-STREAM");   
		PropUtil prop = new PropUtil();
		String filepath = prop.getProperty("TEMPLATE_DIR");
		String fileName = prop.getProperty("TEMPLATE_FILENAME");
		System.out.println("-=-=-=-=-dfgfgfdg=-=-=-=-="+filepath);
		String fileNameTemp = fileName;
		String content = Globals.getContentType4File(fileNameTemp.substring(fileNameTemp.lastIndexOf(".")+1));
		response.setContentType(content.trim().isEmpty() ? "text/html" : content);  
//		if(downloadFrom.equalsIgnoreCase("mail"))
//			fileNameTemp = fileName.substring(0, fileName.lastIndexOf("."))+"#"+docId+"#"+downloadFrom+fileName.substring(fileName.lastIndexOf("."));
		response.setHeader("Content-Disposition","attachment; filename=\"" + fileNameTemp + "\"");   
//		if()
		FileInputStream fileInputStream = new FileInputStream(filepath + fileName);  

		int it;   
		while ((it=fileInputStream.read()) != -1) {  
			out.write(it);   
		}   
//		if(downloadFrom.equalsIgnoreCase("File"))
//			WorkflowManager.updateWorkingUserInXML(docId, downloadUsername, true);
		fileInputStream.close();   
		out.close();   
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			response.getWriter().write("Couldn't download the file.");
			return;
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
