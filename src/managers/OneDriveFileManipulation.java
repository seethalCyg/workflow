package managers;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;
import java.util.Set;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import utils.Globals;

public class OneDriveFileManipulation {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		String accessCode = getAccessCodeFromOneDrive("E:\\Business Planner Original - Copy\\Business Planner\\XML\\", "accelConfig.xml", "E:\\Business Planner Original - Copy\\Business Planner\\XML\\", "", "");
//		String accessCode = "EwBgA8l6BAAURSN/FHlDW5xN74t6GzbtsBBeBUYAAYjxixoIic3sutW7Nd52qP2zwYk9T8YKngMfTf/+sWp/M3do3zAmDUk1gMo1OweFx4HdGHX2qYiKw9A2EaYMSh+tSi1KHh/3gPR/A8KGvSKuHvmcg0qjMp5UBfKHwVJ4QYaqG/8Dr+xK6lu5BTiztAoUF/TGO21g2xmcHYZQDwnvwCyJR4R1g93Lo28cyL5WycrJZe1otA3fg7Aw9v2nFIyc8EyHzhWN2574pAuk/WRGDKK9txxcar5rKg8+JQCbUJ44rNGndLkzhkIQBmU7jwayPsOhhsZ1E+HMllNRc/Avp3t6bxQ+aTCBp30cvAx0dw9MKMQ1OlHX4VyjQsmBU30DZgAACFAUXW4nBvJRMAILhekElKZpgdAXFHcttAZiE3/ZowiV/6JxPkv72WP/sx69s6QDxt7ffULyBrUusQxsTaP9YnSlFv//loyVG6KQx+fdLWKUrnZediPWv0kOlzCBs3y0VS/NI8aE9RNktzqKfX56Ecj5VnycQ5ZtiFRDEdnlNa4IAkEbxT/zbp6Yk6Fiy7KiBeldCNJFyv9jbzT1u1/6IFaePtdrBw6dsloupijB2Y+ccdJpH85A2SRDYO/KeuCj5RilO1EsbNU8KCMj0ZOd4QassVziFV4l+pqie/JO0mMlCQWv1bxlj67NEglQPUJR9xUaNI0TIcoRt2XggwVx027TpGanY3sCsLSk/4iEW8ACqgeUgRS4bhzDlTXF9VF+juIUJ2EUUM33DPi3wbVXRW6O0XYjICcXwcOHnLiEw8i62bVyZuvdHqleZR/dzyE47Qjif1m8IkzKc/+6wQte+vS8o7ayrGOT16MyA9OxmF2LSWCOU/voMhuvumQRDe0qZoxoLSzsXZbtBANvPX2GMLcYdyg5NTR4A/QKF4y9GkXRiMbdcjb0WS0zHMOv7z987/DE/RBSvR5OPXD8hWxRY7j+TwiXy8z9Uhzc+iqJcFd+/N/xfo1wHwzi2k88UvRBnQmAgyP3jAuBVVlaOhXJ1R+yjcJ1eTIETdyFNVUMced/AP6gdU+wDoo3/8+AnkNmd3F+VDYNQJUrU985bYcAYqQC0IF8IZ1TSIB8WJ/cOlvkft30nYeiWHYu9nkC";
		System.out.println("-=-=-=-=accessCode-=-=-=-="+accessCode);
		
		
//		String address = "https://graph.microsoft.com/v1.0/me/drive/root:$$FilePath$$:/content";
//		address = address.replace("$$FilePath$$", "/COSTS_DATA_TABLE_65692.xlsx");
//		
//		
//		try {
//			uploadFiles2OneDrive("C:\\Users\\JavaDev05\\Documents\\COSTS_DATA_TABLE_65692.xlsx", accessCode, address);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		
		String address = "https://graph.microsoft.com/v1.0/me/drive/items/$$FileID$$/content";
		address = address.replace("$$FileID$$", "E90E74F6ED6BBA50!740");
		downloadFilesFromOneDrive("C:\\Users\\JavaDev05\\Documents\\COSTS_12345.xlsx", accessCode, address);
		
		
		
//		File file = new File("C:\\Users\\JavaDev05\\Documents\\Community Assets.docx");
//		File file1 = new File("C:\\Users\\JavaDev05\\Documents\\Community Assets12.docx");
//		FileOutputStream bos = new FileOutputStream(file1);
//		FileInputStream bis = new FileInputStream(file);
//		byte[] by = new byte[(int)file.length()];
//		int i;
//		// read byte by byte until end of stream
//		while ((i = bis.read(by)) > 0) {
//			bos.write(by);
//		}
//		bis.close();
//		bos.close();
	}
	
	public static void downloadFilesFromOneDrive(String filePath, String accessToken, String address) throws Exception {
		URL url = new URL(address);
		URLConnection connection = (URLConnection)url.openConnection();
//		connection.connect();
		connection.setRequestProperty("Authorization", "Bearer "+accessToken);
		InputStream is = connection.getInputStream();
		ReadableByteChannel readableByteChannel = Channels.newChannel(is);
		FileOutputStream fileOutputStream = new FileOutputStream(filePath);
		FileChannel fileChannel = fileOutputStream.getChannel();
		fileOutputStream.getChannel()
		  .transferFrom(readableByteChannel, 0, Long.MAX_VALUE);
	}
	
	public static String uploadFiles2OneDrive(String filePath, String accessToken, String address) throws Exception
	{

		boolean status;

		String url = address; 
		//exceptions are not coming if i mention https:// in the url. but no output is coming
		File file = new File(filePath);
		byte[] content = new byte[(int)file.length()];
		FileInputStream bis = new FileInputStream(file);
		bis.read(content);
		String contentType = Globals.getContentType4File(filePath.substring(filePath.lastIndexOf(".")+1));
		MediaType JSON = MediaType.parse(contentType);
		RequestBody body = RequestBody.create(JSON, file);//create(JSON, by);
		Request request = new Request.Builder()
				.url(url)
				.put(body)
				.addHeader("Authorization", "Bearer "+accessToken)
				.build();

		OkHttpClient okHttpClient = new OkHttpClient();


		Response response = okHttpClient.newCall(request).execute();

		status = response.code()==200 ? true : false;
		System.out.println("-=-=-=response.message()-=-=-="+response.body().string());
		return response.body().string();
	}
	
	public static String getAccessCodeFromOneDrive(String configDir, String accelFilename, String onedriveDir, String templateFilePath, String connFileName) {
		String accessCode = "";
		try {
			Hashtable connectionHT = getRepositoryOneDriveDetails(templateFilePath, connFileName);
			
			StringBuilder sb = new StringBuilder();
	        if (connectionHT != null && connectionHT.size() > 0)
	        {

	            //accessToken = (String)connectionHT["access_token"];
	            //connID = (String)connectionHT["Connection_ID"];

	            String expires_date = (String)connectionHT.get("expires_date");
	            String expires_in = (String)connectionHT.get("expires_in");
	            String refresh_token = (String)connectionHT.get("refresh_token");

	            //MessageBox.Show("refresh_token: " + refresh_token);
	            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ssSSS");
	            
	            String currTime = sdf.format(new Date());
	            Date dt2 = new Date();
	            Date dt1 = sdf.parse(expires_date);
	            System.out.println(dt2 + ": New Current Time :" + currTime);
//	            sb.AppendLine(dt2 + ": New Current Time :" + currTime);
	            System.out.println(dt1 + ": Expire Time :" + expires_date);
//	            sb.AppendLine(dt1 + ": Expire Time :" + expires_date);
//	            TimeSpan span = dt2 - dt1;
//	            System.out.println(span.Seconds + ": Span :" + span.Minutes);
//	            sb.AppendLine(span.Seconds + ": Span :" + span.Minutes);
	            double ms = (dt2.getTime()-dt1.getTime())/1000;
	            System.out.println(": Span :" + ms);
	            //File.AppendAllText(tempConsoleFilePath, "\n Span in MM :" + ms);
	            double expMScnd = Double.parseDouble(expires_in);
	            expMScnd = expMScnd - 10;
	            //File.AppendAllText(tempConsoleFilePath, "\n expMScnd :" + expMScnd);
//	            sb.AppendLine(ms + "-=-=-=-=-=-expMScnd=-=-=-=-=-=-=" + expMScnd);
	            System.out.println(ms + "-=-=-=-=-=-expMScnd=-=-=-=-=-=-=" + expMScnd);
	            if (ms > expMScnd)
	            {
//	                sb.AppendLine(ms + "-=-=-=-=-=-true=-=-=-=-=-=-=" + expMScnd);
	                String codeFilePath = onedriveDir + "ProcessData.txt";
	                //String tempConsoleFilePath = onedriveDir + "tempConsole.txt";
	                Boolean isExcute = getaccesToken(connectionHT, refresh_token, "refresh_token", codeFilePath, "OneDrive", configDir, accelFilename);
//	                sb.AppendLine(ms + "-=-=-=-=-=-isExcute=-=-=-=-=-=-=" + isExcute);
	                //File.AppendAllText(tempConsoleFilePath, "\n isExcute :" + isExcute);
	                if (isExcute)
	                {
	                    changeRepositoryOneDriveDetails(templateFilePath, connFileName, connectionHT);
	                }
	                accessCode = (String)connectionHT.get("access_token");
	            }
	            else
	            {
	                //sb.AppendLine(ms + "-=-=-=-=-=-false=-=-=-=-=-=-=");
	            	accessCode = (String)connectionHT.get("access_token");
	            }
//	            sb.AppendLine(ms + "-=-=-=-=-=-access_token=-=-=-=-=-=-=" + accessToken);
	        }
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return accessCode;
	}

	public static Hashtable getRepositoryOneDriveDetails(String accelConfigDir, String accelConfigFileName)
    {
		Hashtable ht = null;
        Document doc = Globals.openXMLFile(accelConfigDir, accelConfigFileName);

        Element repoEle = (Element)doc.getElementsByTagName("Repository").item(0);
        if (repoEle != null)
        {
        	for(int i=0;i<repoEle.getChildNodes().getLength();i++) {
        		if(repoEle.getChildNodes().item(i).getNodeType() == Node.ELEMENT_NODE) {
        			Element ele = (Element)repoEle.getChildNodes().item(i);
        			if (ele == null)
        				ht = null;
        			else
        				ht = Globals.getAttributeNameandValHT(ele);
        		}
        	}
        }
        else
        	ht = null;
        return ht;
    }
	
	public static void changeRepositoryOneDriveDetails(String accelConfigDir, String accelConfigFileName, Hashtable connectionHT)
    {
        
            Document doc = Globals.openXMLFile(accelConfigDir, accelConfigFileName);

            Element repoEle = (Element)doc.getElementsByTagName("Repository").item(0);
            if (repoEle != null)
            {
            	for(int i=0;i<repoEle.getChildNodes().getLength();i++) {
            		if(repoEle.getChildNodes().item(i).getNodeType() == Node.ELEMENT_NODE) {
            			Element ele = (Element)repoEle.getChildNodes().item(i);
            			Set<String> keys = connectionHT.keySet(); 
            			for (String key : keys)
            			{
            				String value = (String)connectionHT.get(key);
            				ele.setAttribute(key, value);
            			}
            		}
            	}
                Globals.writeXMLFile(doc, accelConfigDir, accelConfigFileName);
            }
    }
	
	public static Boolean getaccesToken(Hashtable connectionHT, String code, String grant_type, String codeFilePath, String connectionType, String configmetadirectory, String accelfilename) throws Exception
    {
        Boolean isExecute = true;
        String request_body = "";
        // HttpWebResponse response = null;
        String mailID = (String)connectionHT.get("UserID_Name");
        String old_refresh_token = (String)connectionHT.get("refresh_token");
        Hashtable smartShturlHT = getsmartSheetProp(connectionType, configmetadirectory, accelfilename);
        String clintID = (String)smartShturlHT.get("Client_Id");
        String authnticateURL = (String)smartShturlHT.get("AccessToken_URL");
        String password = (String)smartShturlHT.get("Client_Secrete");
        String redirctURL = (String)smartShturlHT.get("Redirect_URL");
        //String salt = code;//"wn7cmmyv3ailrue0";
        String passWithSalt = password + "|" + code;
        String generatedPassword = sha256Hash(passWithSalt);
        BufferedWriter writ = new BufferedWriter(new FileWriter(codeFilePath));
        writ.append("\nGeneratedPassword: " + generatedPassword);
//        System.IO.File.AppendAllText(codeFilePath, "\nGeneratedPassword: " + generatedPassword);

        String authURL = "";
        if (connectionType.equalsIgnoreCase("smartsheet"))
        {

            authURL = authnticateURL.replace("$$Client_Id$$", clintID);
            if (grant_type.equalsIgnoreCase("refresh_token"))
            {
                authURL = authURL.replace("$$Grant_Type$$", "refresh_token");
                authURL = authURL.replace("$$TokenType$$", "refresh_token");
            }
            else
            {
                authURL = authURL.replace("$$Grant_Type$$", "authorization_code");
                authURL = authURL.replace("$$TokenType$$", "code");
            }
            //code = salt;

            if (!mailID.isEmpty())
            {
                authURL = authURL.replace("$$CODE$$", code).replace("$$HASH$$", generatedPassword);
            }
        }
        else if (connectionType.equalsIgnoreCase("google drive"))
        {

            authURL = authnticateURL.replace("$$Client_Id$$", clintID);
            if (grant_type.equalsIgnoreCase("refresh_token"))
            {
                authURL = authURL.replace("$$Grant_Type$$", "refresh_token");
                authURL = authURL.replace("$$TokenType$$", "refresh_token");
            }
            else
            {
                authURL = authURL.replace("$$Grant_Type$$", "authorization_code");
                authURL = authURL.replace("$$TokenType$$", "code");
            }

            code = URLEncoder.encode(code, java.nio.charset.StandardCharsets.UTF_8.toString());
            generatedPassword = URLEncoder.encode(generatedPassword, java.nio.charset.StandardCharsets.UTF_8.toString());
            clintID = URLEncoder.encode(clintID, java.nio.charset.StandardCharsets.UTF_8.toString());
            password = URLEncoder.encode(password, java.nio.charset.StandardCharsets.UTF_8.toString());
            redirctURL = URLEncoder.encode(redirctURL, java.nio.charset.StandardCharsets.UTF_8.toString());

            if (!mailID.equalsIgnoreCase(""))
            {
                authURL = authURL.replace("$$Code$$", code).replace("$$HASH$$", generatedPassword).replace("$$Client_Id$$", clintID).replace("$$secretID$$", password).replace("$$redirect_uri$$", redirctURL).replace("$$Code$$", code).replace("$$Grant_Type$$", "refresh_token");
            }

        }
        else
        {

            authURL = authnticateURL.replace("$$Client_Id$$", clintID).replace("$$secretID$$", password).replace("$$redirect_uri$$", redirctURL).replace("$$Code$$", code).replace("$$Grant_Type$$", "authorization_code");
        }
        // File.WriteAllText(@"E:\ASP\Org BackUP\Collation_project\Console\testseess1.txt", "authURL--->" + authURL);
        System.out.println("-=-=-=-=-=******-=-=-=-=-=" + authURL);
        writ.append("\n authURL: " + authURL);
//        System.IO.File.AppendAllText(codeFilePath, "\n authURL: " + authURL);
        if (connectionType.equalsIgnoreCase("onedrive"))
        {
            if (grant_type.equalsIgnoreCase("refresh_token"))
            {
                request_body = (String)smartShturlHT.get("Refresh_Tokrn");
            }
            else
            {
                request_body = (String)smartShturlHT.get("Access_Body");
            }
        }
        String postData = "";
        String respbody = null;
        URL obj = new URL(authURL);
		HttpURLConnection gettimeline = (HttpURLConnection) obj.openConnection();
        InputStream respR = null;
//        HttpWebRequest gettimeline = WebRequest.Create(authURL) as HttpWebRequest;
        gettimeline.setRequestMethod("POST");
        gettimeline.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

        if (connectionType.equalsIgnoreCase("onedrive"))
        {
            if (grant_type.equalsIgnoreCase("refresh_token"))
            {
                postData = request_body.replace("$$Client_Id$$", clintID).replace("$$secretID$$", password).replace("$$redirect_uri$$", redirctURL).replace("$$Refresh_token$$", old_refresh_token).replace("$$Grant_Type$$", grant_type);
            }
            else
            {
                postData = request_body.replace("$$Client_Id$$", clintID).replace("$$secretID$$", password).replace("$$redirect_uri$$", redirctURL).replace("$$Code$$", code).replace("$$Grant_Type$$", grant_type);
            }
            gettimeline.setDoOutput(true);
            System.out.println("postData===>" + postData);
            OutputStream stream = gettimeline.getOutputStream();
            byte[] data = postData.getBytes();
//            var data = Encoding.ASCII.GetBytes(postData);
//
//            gettimeline.ContentLength = data.Length;
//            using (var stream = gettimeline.GetRequestStream())
//            {
                stream.write(data, 0, data.length);
//            }
        }
        else
        {
            gettimeline.setRequestProperty("ContentLength", "0");
        }
        //gettimeline.Method = "GET";

//        var response = (HttpWebResponse)gettimeline.GetResponse();
        int codee = gettimeline.getResponseCode();
        if (codee != 200 && codee != 202)
        {
            throw new Exception(gettimeline.getResponseMessage());
            //return false;
        }
        String strResponse = "";
    	StringBuilder stringBuilder = new StringBuilder();
    	String line = null;
    	
    	try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(gettimeline.getInputStream()))) {	
    		while ((line = bufferedReader.readLine()) != null) {
    			stringBuilder.append(line);
    		}
    	}
    	respbody = stringBuilder.toString();
//        using (var sr = new StreamReader(response.GetResponseStream()))
//        {
//            respbody = sr.ReadToEnd();
//
//        }
        System.out.println("-=-=-=-=-=respbody-=-=-=-=-=" + respbody);
        //MessageBox.Show("AUTH-respbody: " + respbody);

        String access_token = "";
        String token_type = "";
        String refresh_token = "";
        String expires_in = "";
        if (!respbody.toLowerCase().contains("error"))
        {
        	JSONObject stuff = (JSONObject) new JSONParser().parse(respbody);
        	
            //MessageBox.Show("access_token===> " + stuff.access_token);
            access_token = (String)stuff.get("access_token");
            token_type = (String)stuff.get("token_type");
            refresh_token = (String)stuff.get("refresh_token");
            Long expires_inTemp = (Long)stuff.get("expires_in");
            expires_in = String.valueOf(expires_inTemp);
        }
        else
        {
            throw new Exception(respbody);
        }


        //accessToken = access_token;
        writ.append("\n access_token: " + access_token);
//        System.IO.File.AppendAllText(codeFilePath, "\n access_token: " + access_token);
        connectionHT.put("access_token", access_token);
        connectionHT.put("token_type", token_type);

        if (connectionType.equalsIgnoreCase("google drive"))
        {
            if (!grant_type.equals("refresh_token"))
            {
            	connectionHT.put("refresh_token", refresh_token);
            }
        }
        else
        {
        	connectionHT.put("refresh_token", refresh_token);
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ssSSS");
        String currTime = sdf.format(new Date());
        connectionHT.put("expires_date", currTime);
        connectionHT.put("expires_in", expires_in);
        connectionHT.put("code", code);
        connectionHT.put("hash", generatedPassword);
        connectionHT.put("Access_Flag", "True");

        return isExecute;
    }
	
	public static Hashtable getsmartSheetProp(String connectionType, String xml_directory, String xml_Filename)
    {
        //String xml_directory = Datasources.configmetadirectory;
        //String xml_Filename = Datasources.accelfilename;
        System.out.println("connectionType====>" + connectionType);
        String targetName = "";
        if (connectionType.equalsIgnoreCase("dropbox"))
        {
            targetName = "DropBox_URL";
        }
        else if (connectionType.equalsIgnoreCase("google drive"))
        {
            targetName = "Googledrive_URL";
        }
        else if (connectionType.equalsIgnoreCase("smartsheet"))
        {
            targetName = "Smartsheet_URL";
        }
        else if (connectionType.equalsIgnoreCase("onedrive"))
        {
            targetName = "OneDrive_URL";
        }
        Hashtable formatPropertyHT = getDefaultFormatProp(xml_directory, xml_Filename, targetName);
        return formatPropertyHT;
    }
	
	public static Hashtable getDefaultFormatProp(String xml_dir, String xml_nm, String tag_nm)
    {
//        System.out.println("Entering: " + new StackTrace(true).GetFrame(0).GetMethod().ReflectedType.FullName + "." + MethodBase.GetCurrentMethod().Name);
        Hashtable allNodeHT = new Hashtable();
        Document doc = Globals.openXMLFile(xml_dir, xml_nm);
        NodeList nodelist1 = doc.getElementsByTagName(tag_nm);
        Node node = nodelist1.item(0);
        NodeList nodelist2 = node.getChildNodes();
        Element tempel = null;
        Node node2 = null;
        System.out.println("getOperatorSymbol:tag_nm" + tag_nm);
        for (int x = 0; x < nodelist2.getLength(); x++)
        {
            node2 = nodelist2.item(x);
            if (node2.getNodeType() == Node.ELEMENT_NODE)
            {
                tempel = (Element)node2;
                Hashtable nodeAttrHT = Globals.getAttributeNameandValHT(node2);
                String textValue = node2.getNodeName();
                if (tag_nm.equalsIgnoreCase("Smartsheet_URL") || tag_nm.equalsIgnoreCase("DropBox_URL") || tag_nm.equalsIgnoreCase("Googledrive_URL") || tag_nm.equalsIgnoreCase("OneDrive_URL"))
                {
                    allNodeHT.put(textValue, node2.getTextContent());
                }
                else
                {
                    allNodeHT.put(textValue, nodeAttrHT);
                }

            }
        }


        System.out.println("getOperatorSymbol:allNodeHT.COUNT:" + allNodeHT.size());
//        System.out.println("Exiting: " + new StackTrace(true).GetFrame(0).GetMethod().ReflectedType.FullName + "." + MethodBase.GetCurrentMethod().Name);
        return allNodeHT;
    }
	
	public static Hashtable getNodeName(String selectedCon, String xmlDirectory, String configFileName, String comingFrom)
    {
        Hashtable nodeNameHt = new Hashtable();
        Document doc = Globals.openXMLFile(xmlDirectory, configFileName);
        
        Node root = doc.getFirstChild();
        NodeList rbidConn = root.getChildNodes();
        System.out.println("getNodeName:comingFrom" + comingFrom);
        for (int i = 0; i < rbidConn.getLength(); i++)
        {
        	Node rbidConnNode = rbidConn.item(i);
            if (rbidConnNode.getNodeType()==Node.ELEMENT_NODE && rbidConnNode.getNodeName().equalsIgnoreCase("RBID_connection"))
            {
            	NodeList conDetails = rbidConnNode.getChildNodes();
                for (int j = 0; j < conDetails.getLength(); j++)
                {
                    Node conDetailsNode = conDetails.item(j);
                    if (conDetailsNode.getNodeType()==Node.ELEMENT_NODE && conDetailsNode.getNodeName().equalsIgnoreCase("Type"))
                    {
                        Element typeELE = (Element)conDetailsNode;
                        if (typeELE.getAttribute("Connection_Type").equalsIgnoreCase(comingFrom))
                        {
                            if (typeELE.getAttribute("Connection_Name").equalsIgnoreCase(selectedCon) || typeELE.getAttribute("Connection_ID").equalsIgnoreCase(selectedCon))
                            {

                                System.out.println("getNodeName:Connection_Name" + typeELE.getAttribute("Connection_Name"));
                                // MessageBox.Show(typeELE.GetAttribute("Connection_Name"));


                                nodeNameHt = Globals.getAttributeNameandValHT(conDetailsNode);


//                                if (nodeNameHt.ContainsKey("Password"))
//                                {
//                                    String password = (String)nodeNameHt["Password"];
//                                    String decryptedPwd = Globals.Decrypt(password);
//                                    nodeNameHt.Remove("Password");
//                                    nodeNameHt.Add("Password", decryptedPwd);
//
//                                }


                            }


                        }


                    }
                }


            }
        }

        return nodeNameHt;

    }
	
	public static String sha256Hash(String message)
    {
        String generatedPassword = "";
        try
        {

        	MessageDigest md = MessageDigest.getInstance("SHA-256"); 
        	  
            // digest() method called 
            // to calculate message digest of an input 
            // and return array of byte 
            byte[] messageDigest = md.digest(message.getBytes()); 
  
            // Convert byte array into signum representation 
            BigInteger no = new BigInteger(1, messageDigest); 
  
            // Convert message digest into hex value 
            String hashtext = no.toString(16); 
  
            while (hashtext.length() < 32) { 
                hashtext = "0" + hashtext; 
            } 
  
            generatedPassword = hashtext; 

        }
        catch (Exception ex)
        {

        }
        return generatedPassword;
    }
}
