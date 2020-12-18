package beans;

import java.util.Collections;
import java.util.UUID;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleOAuthConstants;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.FileContent;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.Drive.Files;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import com.google.api.services.drive.model.User;

import test.Authorizer;
import utils.Inventory;
import workflow.operations.OperationService;

public class Test {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
//		OperationService os = new OperationService();
//		os.authenticateUser("vijay@cygnussoftwares.com", "java$cbe", "accel-mso-22.docx");
//		System.out.println("-=-=-=-=-=-=-="+GoogleOAuthConstants.OOB_REDIRECT_URI);
		try {
		User a = new User();
		a.setEmailAddress("ppvishnu60@gmail.com");
		HttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
		JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
		Authorizer auth = new Authorizer(a, httpTransport, JSON_FACTORY, "");
		Credential cre = auth.get();
		String accessToken = cre.getAccessToken();
		System.out.println("-=-=-=-=-=-=-=-=-="+accessToken);
		String APPLICATION_NAME = "Web client 1";
		Drive drive = new Drive.Builder(httpTransport, JSON_FACTORY, cre).setApplicationName(
		          APPLICATION_NAME).build();
		String folderId = "1bGZPeiN8jnV0fl9xPrHnWVJuin97dbKB";
		/*String pageToken = null;
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
		    System.out.println(file.getId()+"-=-=-=-=-=-=-=-=-=-=-=-="+file.getExplicitlyTrashed()+"-=-=-=-=-=-=-=-="+file.getFileExtension());
		  }
		  pageToken = result.getNextPageToken();
		} while (pageToken != null);*/
		
		if(true) {
			return;
		}
		
		File file1 = drive.files().get(folderId)
			    .setFields("id")
			    .execute();
		System.out.println("-=-=-=-=-=-=-=-=-=-=-=-="+file1);
		System.out.println("-=-=-=-=-=-=-dfdf=-=-=-=-=-="+file1.getId());

//		DriveFolder folder = Drive.DriveApi.getFolder(getGoogleApiClient(), driveId));
		
		File fileMetadata = new File();
		fileMetadata.setName("CollationTemplate_100");
		fileMetadata.setMimeType("application/vnd.google-apps.folder");
		fileMetadata.setParents(Collections.singletonList(folderId));
//		java.io.File filePath = new java.io.File("C:\\Users\\Officepc\\Desktop\\CollationTemplate_00173.docx");
//		FileContent mediaContent = new FileContent("application/vnd.google-apps.document", filePath);
		File file = drive.files().create(fileMetadata)
		    .setFields("id, parents")
		    .execute();
		String folderID = file.getId();
		System.out.println("-=-=-=-=-=-=-folderID=-=-=-=-=-=-=-="+folderID);
		fileMetadata = new File();
		fileMetadata.setName("CollationTemplate_00173.docx");
		fileMetadata.setMimeType("application/vnd.google-apps.document");
		fileMetadata.setParents(Collections.singletonList(folderID));
		java.io.File filePath = new java.io.File("C:\\Users\\Officepc\\Desktop\\CollationTemplate_00173.docx");
		FileContent mediaContent = new FileContent("application/vnd.google-apps.document", filePath);
		file = drive.files().create(fileMetadata, mediaContent)
		    .setFields("id, parents")
		    .execute();
		System.out.println("-=-=-=-=-=-fileID=-=-=-=-=-="+file.getId());
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
	}

}
