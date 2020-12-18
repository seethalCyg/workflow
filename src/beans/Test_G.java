package beans;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Timestamp;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import managers.LoginProcessManager;
import utils.Globals;
import utils.PropUtil;

public class Test_G {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub

		/*Connection srcConn=Globals.getDBConnection("Data_Connection");
	
		String sql="select tname from tab where tname LIKE '%_IN%'";
		
		
		PreparedStatement ps=srcConn.prepareStatement(sql);
		
		ResultSet rs=ps.executeQuery();
		
		while(rs.next())
		{
			String nm=rs.getString("tname");
		String sql2="drop table "+nm;

		PreparedStatement ps2=srcConn.prepareStatement(sql2);
		
		ResultSet rs2=ps2.executeQuery();
		
		System.out.println(nm+" DELETED");
			
		}
		*/
		
		LoginRegistrationBean rg = new LoginRegistrationBean();
		String[] recipients = {"vijay@cygnussoftwares.com"};
		PropUtil prop = new PropUtil();
		String heirLevelXML = prop.getProperty("HIERARCHY_XML_FILE");
		String heirLeveldir = prop.getProperty("HIERARCHY_XML_DIR");
		String hierlink4Mail= prop.getProperty("HIERARCHY_BASE_URL");
		rg.postMail(recipients, "Test", "Test", "sysadmin@cygnussoftwares.com", "Cygnus$admin", null, hierlink4Mail);
		
		
		
		
		
	}
	
	public static Hashtable createTemptable4IncrementalLoad(Hashtable incrementLoadHT,String lastGentime,String XmlDir,String configFileNM,String srcFacttableName,Connection srcConn,String hierCode,String tableLevelstatus,String targetfacttableName)
	{
		System.out.println("Entering: " + new Exception().getStackTrace()[0].getClassName() + "."
	            + new Exception().getStackTrace()[0].getMethodName());
		String createdTemptable="";
		Hashtable resHT=new Hashtable<>();
		try{
			
				String parseDATE ="";
				PropUtil prop=new PropUtil();
				String dateFormatDF= prop.getProperty("DATE_FORMAT");

				
//				SELECT SERIAL,W_INSERT_DT,W_UPDATE_DT FROM W_GL_OTHER_F WHERE W_INSERT_DT >sysdate OR W_UPDATE_DT > sysdate;
			
				Hashtable insertLoadHT=new Hashtable<>();
				int inskey=0;
				Hashtable updateLoadHT=new Hashtable<>();
				int upkey=0;
				
				String consSQLstmt="";
				
				
				for(int i=0;i<incrementLoadHT.size();i++)
				{
					Hashtable loadDetHT=(Hashtable)incrementLoadHT.get(i);
					String loadType=(String)loadDetHT.get("Type");
					String colNM=(String)loadDetHT.get("Column");
				
					if(loadType.equalsIgnoreCase("Insert"))
					{
						insertLoadHT.put(inskey, colNM);
						inskey++;
				
				
					}else if(loadType.equalsIgnoreCase("Update"))
					{
						updateLoadHT.put(upkey, colNM);
						upkey++;
					}
				}
				
				for(int i=0;i<insertLoadHT.size();i++)
				{
						String colNM=(String)insertLoadHT.get(i);
										
						if(i!=insertLoadHT.size()-1)
						{
						consSQLstmt=consSQLstmt+"to_char("+colNM+",'"+dateFormatDF+"') > '"+lastGentime +"' OR ";
						}else
						{
							consSQLstmt=consSQLstmt+"to_char("+colNM+",'"+dateFormatDF+"') > '"+lastGentime+"'";
						}
						
						
				}
				
				
				int tempTableDBId=Globals.getMaxID(XmlDir, configFileNM, "DB_Temp_Table_ID", "ID");
				createdTemptable=srcFacttableName+"_TEMP_"+tempTableDBId;
			
				//Creating a New table in Src system
				String createsql="";
			
							
				createsql="CREATE TABLE "+createdTemptable+" AS SELECT * FROM "+srcFacttableName+" WHERE "+consSQLstmt;
				
	
				System.out.println("Create SQL:"+createsql);
			
				PreparedStatement tableCreate=srcConn.prepareStatement(createsql);
				tableCreate.execute();
				System.out.println("Temp Table "+createdTemptable+" has Created Successfully.");
				tableCreate.close();
				
			
			
			
		}catch(Exception e){
//			tableLevelstatus=settingErrorStatusinXML(hierCode, tableLevelstatus, targetfacttableName, e.getMessage());
			e.printStackTrace();
		}
		System.out.println("Exiting: " + new Exception().getStackTrace()[0].getClassName() + "."
	            + new Exception().getStackTrace()[0].getMethodName());
		resHT.put(0, createdTemptable);
		resHT.put(1, tableLevelstatus);
		
		return resHT;
	
	}
	
public static void updateCurrentFactGenTimeinXML(String heirID){   
		
		try{
			
			PropUtil prop=new PropUtil();
			String dir=prop.getProperty("HIERARCHY_XML_DIR");
			String dateFormat=prop.getProperty("DATE_FORMAT");
			Document doc = Globals.openXMLFile(dir, "HierachyLevel.xml");
			Node rootnode = Globals.getNodeByAttrVal(doc, "Fact_Tables", "ID",heirID);
			Element factTableE=(Element)rootnode;
			
			Date currentTime=new Date();
			Format formatter = new SimpleDateFormat(dateFormat);
			String lastgendate=formatter.format(currentTime);
			
			factTableE.setAttribute("Last_Generated_Time",lastgendate);
			
			
			Globals.writeXMLFile(doc, dir, "HierachyLevel.xml");
		System.out.println("Fact Genarated Time Updated in XML");	
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	

	
}
