package managers;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.Hashtable;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.FileContent;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import com.google.api.services.drive.model.User;
import com.sun.org.apache.regexp.internal.recompile;

import test.Authorizer;

public class GoogleStorageMaintenace {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	public static Drive getGoogleDriveObj(String username) throws GeneralSecurityException, IOException {
		String APPLICATION_NAME = "Web client 1";
		User a = new User();
		a.setEmailAddress(username);
		HttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
		JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
		Authorizer auth = new Authorizer(a, httpTransport, JSON_FACTORY, "");
		Credential cre = auth.get();
		String accessToken = cre.getAccessToken();
		System.out.println("-=-=-=-=-=-=-=-=-="+accessToken);
		
		Drive drive = new Drive.Builder(httpTransport, JSON_FACTORY, cre).setApplicationName(
		          APPLICATION_NAME).build();
		return drive;
	}
	public static String createFolder(Drive drive, String folderId, String folderName) throws GeneralSecurityException, IOException {
		
//		Drive drive = getGoogleDriveObj(username);
		File fileMetadata = new File();
		File file = null;
		System.out.println(folderId+"-=-=-=-=-=-=folderName-=-=-=-=-="+folderName);
		fileMetadata.setName(folderName);
		fileMetadata.setMimeType("application/vnd.google-apps.folder");
		fileMetadata.setParents(Collections.singletonList(folderId));
//		java.io.File filePath = new java.io.File("C:\\Users\\Officepc\\Desktop\\CollationTemplate_00173.docx");
//		FileContent mediaContent = new FileContent("application/vnd.google-apps.document", filePath);
		file = drive.files().create(fileMetadata)
		    .setFields("id, parents")
		    .execute();
		
		
		
		
		return file.getId();
	}
	
	public static String getFileIDByName(Drive drive, String fileName, String mimeType)  {
		String fileID = "";
		try {
		String pageToken = null;
		do {
			FileList result = drive.files().list()
					.setQ("mimeType='"+mimeType+"'")
					.setSpaces("drive")
					.setFields("nextPageToken, files(id, name)")
					.setPageToken(pageToken)
					.execute();
			System.out.println("-=-=-=-=ddd-=-=-=-="+result.getFiles().size());
			for (File file : result.getFiles()) {
				System.out.printf("Found file: %s (%s)\n",
						file.getName(), file.getId());
				if(file.getName().equalsIgnoreCase(fileName)) {
				fileID = file.getId();
				break;
				}
				
			}
			pageToken = result.getNextPageToken();
		} while (pageToken != null);
		return fileID;
		}catch (Exception e) {
			// TODO: handle exception
			fileID = "";
			return fileID;
		}
	}
	
	public static Hashtable<String, String> getFilesUnderFolderById(Drive drive, String folderId) throws IOException {
		Hashtable<String, String> folderNamesHT = new Hashtable<>();
		String pageToken = null;
		do {
		  FileList result = drive.files().list()
		      .setQ("'"+folderId+"' in parents")
		      .setSpaces("drive")
		      .setFields("nextPageToken, files(id, name)")
		      .setPageToken(pageToken)
		      .execute();
		  System.out.println("-=-=-=-=-=-=-=-="+result.getFiles().size());
		  for (File file : result.getFiles()) {
		    System.out.printf("Found file: %s (%s)\n",
		        file.getName(), file.getId());
		    folderNamesHT.put(file.getId(), file.getName());
		    System.out.println(file.getId()+"-=-=-=-=-=-=-=-=-=-=-=-="+file.getExplicitlyTrashed()+"-=-=-=-=-=-=-=-="+file.getFileExtension());
		  }
		  pageToken = result.getNextPageToken();
		} while (pageToken != null);
		return folderNamesHT;
	}
	
	public static boolean checkFolderExistinGoogleDrive(Drive drive, String folderId) {
		try {
			File file1 = drive.files().get(folderId)
				    .setFields("id")
				    .execute();
			return file1 == null ? false : true;
		}catch (Exception e) {
			// TODO: handle exception
			return false;
		}
	}
	
	public static void uploadFilesToGoogleDrive(Drive drive, String folderID, String filePath, String documentName, String mimeType) throws IOException {
		try {
			System.out.println("mimeType ::"+mimeType);
			System.out.println("documentName ::"+documentName);
			File fileMetadata = new File();
			fileMetadata.setName(documentName);
			fileMetadata.setMimeType(mimeType);
			fileMetadata.setParents(Collections.singletonList(folderID));
			java.io.File filecontent = new java.io.File(filePath);
			FileContent mediaContent = new FileContent(mimeType, filecontent);
			File file = drive.files().create(fileMetadata, mediaContent)
					.setFields("id, parents")
					.execute();
			System.out.println("file ID ::"+file.getId());
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public static String getMimeTypeForFile(String documentName) {
		String mimeType = "";
    	if(documentName.toLowerCase().contains(".docx") || documentName.toLowerCase().contains(".doc") || documentName.toLowerCase().contains(".rtf"))
    		mimeType = "application/vnd.google-apps.document";
    	else if(documentName.toLowerCase().contains(".xls") || documentName.toLowerCase().contains(".xlsx") || documentName.toLowerCase().contains(".csv"))
    		mimeType = "application/vnd.google-apps.spreadsheet";
    	else if(documentName.toLowerCase().contains(".ppt") || documentName.toLowerCase().contains(".pptx"))
    		mimeType = "application/vnd.google-apps.presentation";
    	else if(documentName.toLowerCase().contains(".pdf") || documentName.toLowerCase().contains(".png") || documentName.toLowerCase().contains(".jpg") || documentName.toLowerCase().contains(".gif"))
    		mimeType = "application/vnd.google-apps.document";
    	return mimeType;
	}
	
	public static String getDownloadMimeTypeForFile(String documentName) {
		String mimeType = "";
    	if(documentName.toLowerCase().contains(".docx") || documentName.toLowerCase().contains(".doc") || documentName.toLowerCase().contains(".rtf"))
    		mimeType = "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
    	else if(documentName.toLowerCase().contains(".xls") || documentName.toLowerCase().contains(".xlsx"))
    		mimeType = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    	else if(documentName.toLowerCase().contains(".csv"))
    		mimeType = "text/csv";
    	else if(documentName.toLowerCase().contains(".ppt") || documentName.toLowerCase().contains(".pptx"))
    		mimeType = "application/vnd.openxmlformats-officedocument.presentationml.presentation";
    	else if(documentName.toLowerCase().contains(".pdf"))
    		mimeType = "application/pdf";
    	else if(documentName.toLowerCase().contains(".png"))
    		mimeType = "image/png";
    	else if(documentName.toLowerCase().contains(".jpg"))
    		mimeType = "image/jpeg";
    	return mimeType;
	}
	
	public static OutputStream downloadFilesFromGoogleDrive(Drive drive, String fileID, String downloadMimeType) throws IOException {
//		String fileId = "0BwwA4oUTeiV1UVNwOHItT0xfa2M";
		OutputStream outputStream = new ByteArrayOutputStream();
		drive.files().export(fileID, downloadMimeType)
	    .executeMediaAndDownloadTo(outputStream);

		return outputStream;
	}
}

