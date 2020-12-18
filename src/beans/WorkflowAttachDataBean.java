package beans;

public class WorkflowAttachDataBean {

	
	
	String fileNameattachstr = "";
	String fileTypeattachstr = "";
	String fileSizeattachstr = "";
	String fileDateattachstr = "";
	
	
	
	
	


	public WorkflowAttachDataBean(String fileNameattachstr, String fileTypeattachstr,  String fileSizeattachstr, String fileDateattachstr){
		
		
		
		this.fileNameattachstr = fileNameattachstr;
		this.fileTypeattachstr = fileTypeattachstr;
		this.fileSizeattachstr = fileSizeattachstr;
		this.fileDateattachstr = fileDateattachstr;
		
		

		// TODO Auto-generated constructor stub
	}
	
	
	
	public String getFileNameattachstr() {
		return fileNameattachstr;
	}

	public void setFileNameattachstr(String fileNameattachstr) {
		this.fileNameattachstr = fileNameattachstr;
	}

	public String getFileTypeattachstr() {
		return fileTypeattachstr;
	}

	public void setFileTypeattachstr(String fileTypeattachstr) {
		this.fileTypeattachstr = fileTypeattachstr;
	}
	public String getFileSizeattachstr() {
		return fileSizeattachstr;
	}
	public void setFileSizeattachstr(String fileSizeattachstr) {
		this.fileSizeattachstr = fileSizeattachstr;
	}

	public String getFileDateattachstr() {
		return fileDateattachstr;
	}
   public void setFileDateattachstr(String fileDateattachstr) {
		this.fileDateattachstr = fileDateattachstr;
	}

}
