package oracle.bi.webservices.v6;

public class ReportEditingServiceSoapProxy implements oracle.bi.webservices.v6.ReportEditingServiceSoap {
  private String _endpoint = null;
  private oracle.bi.webservices.v6.ReportEditingServiceSoap reportEditingServiceSoap = null;
  
  public ReportEditingServiceSoapProxy() {
    _initReportEditingServiceSoapProxy();
  }
  
  public ReportEditingServiceSoapProxy(String endpoint) {
    _endpoint = endpoint;
    _initReportEditingServiceSoapProxy();
  }
  
  private void _initReportEditingServiceSoapProxy() {
    try {
      reportEditingServiceSoap = (new oracle.bi.webservices.v6.ReportEditingServiceLocator()).getReportEditingServiceSoap();
      if (reportEditingServiceSoap != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)reportEditingServiceSoap)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)reportEditingServiceSoap)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (reportEditingServiceSoap != null)
      ((javax.xml.rpc.Stub)reportEditingServiceSoap)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public oracle.bi.webservices.v6.ReportEditingServiceSoap getReportEditingServiceSoap() {
    if (reportEditingServiceSoap == null)
      _initReportEditingServiceSoapProxy();
    return reportEditingServiceSoap;
  }
  
  public java.lang.Object applyReportParams(oracle.bi.webservices.v6.ReportRef reportRef, oracle.bi.webservices.v6.ReportParams reportParams, boolean encodeInString, java.lang.String sessionID) throws java.rmi.RemoteException{
    if (reportEditingServiceSoap == null)
      _initReportEditingServiceSoapProxy();
    return reportEditingServiceSoap.applyReportParams(reportRef, reportParams, encodeInString, sessionID);
  }
  
  public java.lang.String generateReportSQL(oracle.bi.webservices.v6.ReportRef reportRef, oracle.bi.webservices.v6.ReportParams reportParams, java.lang.String sessionID) throws java.rmi.RemoteException{
    if (reportEditingServiceSoap == null)
      _initReportEditingServiceSoapProxy();
    return reportEditingServiceSoap.generateReportSQL(reportRef, reportParams, sessionID);
  }
  
  public java.lang.String applyReportDefaults(oracle.bi.webservices.v6.ReportRef reportRefs, java.lang.String sessionID) throws java.rmi.RemoteException{
    if (reportEditingServiceSoap == null)
      _initReportEditingServiceSoapProxy();
    return reportEditingServiceSoap.applyReportDefaults(reportRefs, sessionID);
  }
  
  
}