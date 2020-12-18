package test;

import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.auth.oauth2.DataStoreCredentialRefreshListener;
import com.google.api.client.auth.oauth2.StoredCredential;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.DataStore;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.client.util.store.MemoryDataStoreFactory;

public class ApiCredentialManager {
    private DataStore<StoredCredential> dataStore;
    static ApiCredentialManager instance = null;
        //Put your scopes here
        public static String[] SCOPES_ARRAY = { "https://www.googleapis.com/auth/admin.directory.user" };

        private ApiCredentialManager() {

            try {
            	FileDataStoreFactory fileDataStoreFactory =
            		      new FileDataStoreFactory(new File("E:\\Document WF New\\Hierarchy Tool\\Datastore"));
            	DataStore<StoredCredential> dd = fileDataStoreFactory.getDataStore("accel-BI Datastore");
//            	if(dd == null) {
//            		fileDataStoreFactory.
//            	}
                dataStore = MemoryDataStoreFactory.getDefaultInstance().getDataStore("credentialDatastore");
            } catch (IOException e) {
                throw new RuntimeException("Unable to create in memory credential datastore", e);
            }
        }

        public static ApiCredentialManager getInstance() {
        	
            if (instance == null)
                instance = new ApiCredentialManager();

            return instance;
        }

        public Credential getCredential(String username) throws Exception {
            try {
            	Credential credential = new GoogleCredential.Builder()
                        .setTransport(new NetHttpTransport())
                        .setJsonFactory(new JacksonFactory())
                        .addRefreshListener(
                                new DataStoreCredentialRefreshListener(
                                        username, dataStore))
                        .build();

                if(dataStore.containsKey(username)){
                    StoredCredential storedCredential = dataStore.get(username);
                    credential.setAccessToken(storedCredential.getAccessToken());
                    credential.setRefreshToken(storedCredential.getRefreshToken());
                }else{
                    //Do something of your own here to obtain the access token.
                    //Most usually redirect the user to the OAuth page
                }

                return credential;
            } catch (IOException e) {
                e.printStackTrace();
                throw new Exception("isuue while setting credentials", e);
            }
        }

        //Call this when you've obtained the access token and refresh token from Google
        public void saveCredential(String username, String accessToken, String refreshToken) throws IOException{
            StoredCredential storedCredential = new StoredCredential();
            storedCredential.setAccessToken(accessToken);
            storedCredential.setRefreshToken(refreshToken);
            dataStore.set(username, storedCredential);
        }
}