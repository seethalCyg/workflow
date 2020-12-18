package servlet;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.google.api.services.drive.Drive;
import com.jniwrapper.e;

import managers.GoogleStorageMaintenace;
import managers.WorkflowManager;

/**
 * Servlet implementation class FileUploadServlet
 */
@WebServlet(name = "FileUploadServlet", urlPatterns = {"/upload"})
@MultipartConfig
public class FileUploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FileUploadServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html;charset=UTF-8");

	    // Create path components to save the file
	    String path = request.getParameter("destination");
	    final Part filePart = request.getPart("file");
//	    final String fileName = getFileName(filePart);
	    System.out.println(path+"-========path======");
	    OutputStream out = null;
	    InputStream filecontent = null;
	    final PrintWriter writer = response.getWriter();

	    try {
	    	String username = "";
	    	String folderID = "";
	    	String documentName = "";
	    	ArrayList<String> tempAL =new ArrayList<>();
	    	if(path.toLowerCase().contains("#NoFile".toLowerCase())) {
		    	documentName = path.split("#")[0];
		    	String documentId = path.split("#")[1];
		    	String documentPath = WorkflowManager.createDocumentVersionForWFFromXML(documentName, documentId);
		    	path = documentPath.split("###")[1].equalsIgnoreCase("Server") ? documentPath.split("###")[0]+"\\"+documentName : documentPath.split("###")[0];
//		    	storageType = documentPath.split("###")[1];
		    	System.out.println(documentPath+"-==============");
		    }else {
		    	documentName = path.substring(path.lastIndexOf("\\")+1);
		    	System.out.println(documentName+"-==============");
		    	path = documentName.contains("#") ? path.substring(0, path.lastIndexOf("#")) : path;
		    	documentName = documentName.contains("#") ? documentName.substring(0, documentName.lastIndexOf("#")) : documentName;
		    	String docId = documentName.contains("#") ? documentName.substring(documentName.lastIndexOf("#")+1) : documentName;
		    	//username = WorkflowManager.getExternalApiDetailsFromXML(documentName);
		    	//tempAL = WorkflowManager.getExternalApiDetailsFromXML(documentName);
		    	
		    	tempAL = WorkflowManager.getExternalApiDetailsFromXML(docId);
		    	
		    	System.out.println(username+"-====username==========");
		    }
	    	if(tempAL==null || tempAL.isEmpty()) {
//	    		return;
	    	}else {
	    		String priDocName = tempAL.get(0);
		    	username=tempAL.get(1);
	    	}
	    	
	    	
	    	
		    System.out.println(username+"::::destination :::"+path);
		    if(username != null && !username.trim().isEmpty()) {
		    	folderID = path.substring(0, path.lastIndexOf("\\"));;
		    	path = System.getProperty("java.io.tmpdir")+documentName;
		    }
		    System.out.println("destination ---> :::"+path);
	        out = new FileOutputStream(new File(path));
	        filecontent = filePart.getInputStream();

	        int read = 0;
	        final byte[] bytes = new byte[1024];

	        while ((read = filecontent.read(bytes)) != -1) {
	            out.write(bytes, 0, read);
	        }
	        out.close();
	        if(username != null && !username.trim().isEmpty()) {
//	        	String username = WorkflowManager.getExternalApiDetailsFromXML(documentName);
	        	Drive drive = GoogleStorageMaintenace.getGoogleDriveObj(username);
	        	String mimeType = GoogleStorageMaintenace.getMimeTypeForFile(documentName);
	        	System.out.println("username ---> :::"+username);
	        	System.out.println("drive ---> :::"+drive);
	        	System.out.println("mimeType ---> :::"+mimeType);
	        	GoogleStorageMaintenace.uploadFilesToGoogleDrive(drive, folderID, path, documentName, mimeType);
	        }
//	        response.f
	        writer.println("New file is created at " + path);
//	        LOGGER.log(Level.INFO, "File{0}being uploaded to {1}", 
//	                new Object[]{fileName, path});
//	        response.getOutputStream().println("File Uploaded successfully.");
	        response.setStatus(HttpServletResponse.SC_OK);
//	        response.se
	    } catch (Exception fne) {
//	        writer.println("You either did not specify a file to upload or are "
//	                + "trying to upload a file to a protected or nonexistent "
//	                + "location.");
	        writer.println("<br/> ERROR: " + fne.getMessage());
	        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
	    	fne.printStackTrace();
//	    	response.getOutputStream().println("File Uploaded failed :"+fne.getMessage());
//	        LOGGER.log(Level.SEVERE, "Problems during file upload. Error: {0}", 
//	                new Object[]{fne.getMessage()});
	    	
	    } finally {
	        if (out != null) {
	            out.close();
	        }
	        if (filecontent != null) {
	            filecontent.close();
	        }
	        
	    }
	}
/*
	private String getFileName(final Part part) {
	    final String partHeader = part.getHeader("content-disposition");
//	    LOGGER.log(Level.INFO, "Part Header = {0}", partHeader);
	    System.out.println("-=-=content-=-="+part.getHeader("content-disposition"));
	    for (String content : part.getHeader("content-disposition").split(";")) {
	        if (content.trim().startsWith("filename")) {
	        	System.out.println("-=-=filename-=-="+content.trim());
	        	String temp = content.substring(
	                    content.indexOf('=') + 1).trim().replace("\"", "");
	        	temp = temp.substring(temp.lastIndexOf("\\")+1);
	            return temp;
	        }
	    }
	    return null;
	}*/

}
