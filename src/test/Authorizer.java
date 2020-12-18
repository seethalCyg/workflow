package test;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.auth.oauth2.DataStoreCredentialRefreshListener;
import com.google.api.client.auth.oauth2.StoredCredential;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.auth.oauth2.GoogleOAuthConstants;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.util.store.DataStore;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.User;
import com.google.common.base.Charsets;
import com.google.common.collect.ImmutableList;
import com.google.common.io.Resources;

import utils.PropUtil;

import javax.inject.Inject;
import javax.inject.Provider;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Iterator;

/**
 * Encapsulates the authorization and authentication flow.
 */
public class Authorizer implements Provider<Credential> {
	private HttpTransport httpTransport;
	private JsonFactory jsonFactory;
	private User user;
	private String redirectURL = "";
	@Inject
	public Authorizer(
			User user,
			HttpTransport httpTransport,
			JsonFactory jsonFactory, String redirectURL) {
		this.httpTransport = httpTransport;
		this.jsonFactory = jsonFactory;
		this.user = user;
		this.redirectURL = redirectURL;
	}

	public Credential get() {
		try {

			GoogleClientSecrets clientSecrets = loadGoogleClientSecrets(jsonFactory);

			DataStore<StoredCredential> dataStore = null;;
			try {
				dataStore = getStoredCredentialDataStore();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(user.getEmailAddress()+"-=-=-=-=-dataStore=-=-=-="+dataStore);
			System.out.println("-=-=-=-=-=-=dataStore.keySet().-=-=-=-=-=-=-=-="+dataStore.keySet().size());
			Iterator<String> it = dataStore.keySet().iterator();
			while (it.hasNext()) {
				String string = (String) it.next();
				System.out.println("-=-=-=-=-=-=string-=-=-=-=-=-=-=-="+string);
			}
			// Allow user to authorize via url.
			GoogleAuthorizationCodeFlow flow =
					new GoogleAuthorizationCodeFlow.Builder(
							httpTransport,
							jsonFactory,
							clientSecrets,
							ImmutableList.of(DriveScopes.DRIVE_FILE, DriveScopes.DRIVE_METADATA, "https://www.googleapis.com/auth/drive.readonly"))
					.setCredentialDataStore(dataStore)
					.setAccessType("offline")
					.setApprovalPrompt("force").addRefreshListener(
                            new DataStoreCredentialRefreshListener(
                            		user.getEmailAddress(), dataStore))
					.build();
			// First, see if we have a stored credential for the user.
			Credential credential = flow.loadCredential(user.getEmailAddress());

			// If we don't, prompt them to get one.
			
			if(credential != null) {
				credential.refreshToken();
				System.out.println(credential.getAccessToken()+"-=-=-=get-=-=-=-=getRefreshToken-=-=-=-=-=-=-="+credential.getRefreshToken());
				System.out.println(credential.getAccessToken()+"-=-=-=get-=-=-=-=getRefreshToken-=-=-=-=-=-=-="+credential.getExpiresInSeconds());
			}
			return credential;
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
	}

	public String createCredentialForNewUser(String hierID) throws Exception {
		GoogleClientSecrets clientSecrets = loadGoogleClientSecrets(jsonFactory);

		DataStore<StoredCredential> dataStore = getStoredCredentialDataStore();

		// Allow user to authorize via url.
		GoogleAuthorizationCodeFlow flow =
				new GoogleAuthorizationCodeFlow.Builder(
						httpTransport,
						jsonFactory,
						clientSecrets,
						ImmutableList.of(DriveScopes.DRIVE_FILE, DriveScopes.DRIVE_METADATA, "https://www.googleapis.com/auth/drive.readonly"))
				.setCredentialDataStore(dataStore)
				.setAccessType("offline")
				.setApprovalPrompt("force").addRefreshListener(
                        new DataStoreCredentialRefreshListener(
                        		user.getEmailAddress(), dataStore))
				.build();
		String url = flow.newAuthorizationUrl()
	            .setRedirectUri(redirectURL)
	            .setState("User="+user.getEmailAddress()+"~~"+hierID)
	            .build();
		return url;
	}
	
	public Credential saveCredentials(String code) throws Exception {
		GoogleClientSecrets clientSecrets = loadGoogleClientSecrets(jsonFactory);

		DataStore<StoredCredential> dataStore = getStoredCredentialDataStore();
		GoogleAuthorizationCodeFlow flow =
				new GoogleAuthorizationCodeFlow.Builder(
						httpTransport,
						jsonFactory,
						clientSecrets,
						ImmutableList.of(DriveScopes.DRIVE_FILE, DriveScopes.DRIVE_METADATA, "https://www.googleapis.com/auth/drive.readonly"))
				.setCredentialDataStore(dataStore)
				.setAccessType("offline")
				.setApprovalPrompt("force").addRefreshListener(
                        new DataStoreCredentialRefreshListener(
                        		user.getEmailAddress(), dataStore))
				.build();
		GoogleTokenResponse response = flow.newTokenRequest(code)
				.setRedirectUri(redirectURL)
				.execute();

		Credential credential =
				flow.createAndStoreCredential(response, user.getEmailAddress());
		System.out.println(credential.getRefreshToken()+"-=-=-=saveCredentials-=-=-=-=getRefreshToken-=-=-=-=-=-=-="+credential.getAccessToken());
		return credential;
	}

	private GoogleClientSecrets loadGoogleClientSecrets(JsonFactory jsonFactory)
			throws IOException {
		GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(jsonFactory,
				new InputStreamReader(DriveSample.class.getResourceAsStream("/client_secrets.json")));

		return clientSecrets;
	}

	private DataStore<StoredCredential> getStoredCredentialDataStore()
			throws Exception {
		File userHomeDir = getUserHomeDir();
		System.out.println("-=-=-=-=-=-=userHomeDir-=-=-=-=-=-="+userHomeDir.getAbsolutePath());
		File mailimporter = new File(userHomeDir, ".mailimporter");
		FileDataStoreFactory dataStoreFactory =
				new FileDataStoreFactory(mailimporter);
		return dataStoreFactory.getDataStore("credentials");
	}

	private File getUserHomeDir() throws Exception {
		PropUtil prop = new PropUtil();
		File userHome = new File(prop.getProperty("EXTERNALDATASTORE_DIR"));
		//    Verify.verify(userHome.exists() && userHome.canRead(),
		//        "Can not find user's home: %s", userHome);
		return userHome;
	}
}