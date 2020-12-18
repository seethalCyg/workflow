package workflow.operations;

public class Testing {
	
	public static void main(String args[]){
		String connJsonStr = "";
		OperationService operService=new OperationService();
		
		//String json={"\"Primary_FileName\":\"accel-mso-243.docx\",\"Primary_FilePath\":\"C:\ProdenTech\Templates\accel-mso-243.docx\",\"Primary_FileConn\":\"LocalFile\",\"AttachMent_Files\":{\"TableLength\":\"0\",},\"WorkflowName\":\"ABZ InfoTek\",\"WorkflowID\":\"257\",\"DocumentID\":\"184\",\"CustomerKey\":\"xbstjb1ciumjt3hu\",\"CreatedBy\":\"bharath@cygnussoftwares.com\",\"CreatedDate\":\"08-24-2018 09:47\",\"DocumentName\":\"accel-mso-243.docx\",\"ChooseTeamName\":\"\",\"Sendnotes\":\"\",\"Timeline\":\"False\",\"Showhours\":\"False\",\"ExecludeWeekEnd\":\"False\",\"Eachteam\":\"False\",\"ExecludeHoliday\":\"False\",\"SendAlertBefore\":\"False\",\"SendAlertBeforeDay\":\"\",\"SendAlertAfter\":\"False\",\"SendAlertAfterCount\":\"\",\"SendAlertAfterDay\":\"\",\"SendAlertDayHourchooser\":\"Day\",\"SendAlertSubSequentTeams\":\"False\",\"Tablevalue\":{\"TableLength\":\"0\",},};

		String str="{\"Primary_FileName\":\"accel-mso-247.docx\",\"Primary_FilePath\":\"C:\\ProdenTech\\Templates\\accel-mso-247.docx\",\"Primary_FileConn\":\"LocalFile\",\"AttachMent_Files\":{\"TableLength\":\"0\",},\"WorkflowName\":\"TECH  SOLUTION\",\"WorkflowID\":\"262\",\"DocumentID\":\"272\",\"CustomerKey\":\"xbstjb1ciumjt3hu\",\"CreatedBy\":\"bharath@cygnussoftwares.com\",\"CreatedDate\":\"08-24-2018 18:38\",\"DocumentName\":\"accel-mso-247.docx\",\"ChooseTeamName\":\"\",\"Sendnotes\":\"\",\"Timeline\":\"False\",\"Showhours\":\"False\",\"ExecludeWeekEnd\":\"False\",\"Eachteam\":\"False\",\"ExecludeHoliday\":\"False\",\"SendAlertBefore\":\"False\",\"SendAlertBeforeDay\":\"\",\"SendAlertAfter\":\"False\",\"SendAlertAfterCount\":\"\",\"SendAlertAfterDay\":\"\",\"SendAlertDayHourchooser\":\"Day\",\"SendAlertSubSequentTeams\":\"False\",\"Tablevalue\":{\"TableLength\":\"0\",},}";
				connJsonStr = operService.authenticateUser("bharath@cygnussoftwares.com", "java$cbe", "accel-mso-181.docx", "");
		operService.attachDocument(str);
		System.out.println("connJsonStr "+connJsonStr);
		
		//.connJsonStr = operService.getWorkflowStatusDetails("241");
		
		System.out.println("=============>>> "+connJsonStr);
	}

}
