package oracle.bi.webservices.v6;

public class HtmlViewServiceSoapProxy implements oracle.bi.webservices.v6.HtmlViewServiceSoap {
  private String _endpoint = null;
  private oracle.bi.webservices.v6.HtmlViewServiceSoap htmlViewServiceSoap = null;
  
  public HtmlViewServiceSoapProxy() {
    _initHtmlViewServiceSoapProxy();
  }
  
  public HtmlViewServiceSoapProxy(String endpoint) {
    _endpoint = endpoint;
    _initHtmlViewServiceSoapProxy();
  }
  
  private void _initHtmlViewServiceSoapProxy() {
    try {
      htmlViewServiceSoap = (new oracle.bi.webservices.v6.HtmlViewServiceLocator()).getHtmlViewService();
      if (htmlViewServiceSoap != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)htmlViewServiceSoap)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)htmlViewServiceSoap)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (htmlViewServiceSoap != null)
      ((javax.xml.rpc.Stub)htmlViewServiceSoap)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public oracle.bi.webservices.v6.HtmlViewServiceSoap getHtmlViewServiceSoap() {
    if (htmlViewServiceSoap == null)
      _initHtmlViewServiceSoapProxy();
    return htmlViewServiceSoap;
  }
  
  public java.lang.String startPage(oracle.bi.webservices.v6.StartPageParams options, java.lang.String sessionID) throws java.rmi.RemoteException{
    if (htmlViewServiceSoap == null)
      _initHtmlViewServiceSoapProxy();
    return htmlViewServiceSoap.startPage(options, sessionID);
  }
  
  public void endPage(java.lang.String pageID, java.lang.String sessionID) throws java.rmi.RemoteException{
    if (htmlViewServiceSoap == null)
      _initHtmlViewServiceSoapProxy();
    htmlViewServiceSoap.endPage(pageID, sessionID);
  }
  
  public void addReportToPage(java.lang.String pageID, java.lang.String reportID, oracle.bi.webservices.v6.ReportRef report, java.lang.String reportViewName, oracle.bi.webservices.v6.ReportParams reportParams, oracle.bi.webservices.v6.ReportHTMLOptions options, java.lang.String sessionID) throws java.rmi.RemoteException{
    if (htmlViewServiceSoap == null)
      _initHtmlViewServiceSoapProxy();
    htmlViewServiceSoap.addReportToPage(pageID, reportID, report, reportViewName, reportParams, options, sessionID);
  }
  
  public java.lang.String getHeadersHtml(java.lang.String pageID, java.lang.String sessionID) throws java.rmi.RemoteException{
    if (htmlViewServiceSoap == null)
      _initHtmlViewServiceSoapProxy();
    return htmlViewServiceSoap.getHeadersHtml(pageID, sessionID);
  }
  
  public java.lang.String getCommonBodyHtml(java.lang.String pageID, java.lang.String sessionID) throws java.rmi.RemoteException{
    if (htmlViewServiceSoap == null)
      _initHtmlViewServiceSoapProxy();
    return htmlViewServiceSoap.getCommonBodyHtml(pageID, sessionID);
  }
  
  public java.lang.String getHtmlForReport(java.lang.String pageID, java.lang.String pageReportID, java.lang.String sessionID) throws java.rmi.RemoteException{
    if (htmlViewServiceSoap == null)
      _initHtmlViewServiceSoapProxy();
    return htmlViewServiceSoap.getHtmlForReport(pageID, pageReportID, sessionID);
  }
  
  public void setBridge(java.lang.String bridge, java.lang.String sessionID) throws java.rmi.RemoteException{
    if (htmlViewServiceSoap == null)
      _initHtmlViewServiceSoapProxy();
    htmlViewServiceSoap.setBridge(bridge, sessionID);
  }
  
  public java.lang.String getHtmlForPageWithOneReport(java.lang.String reportID, oracle.bi.webservices.v6.ReportRef report, java.lang.String reportViewName, oracle.bi.webservices.v6.ReportParams reportParams, oracle.bi.webservices.v6.ReportHTMLOptions reportOptions, oracle.bi.webservices.v6.StartPageParams pageParams, java.lang.String sessionID) throws java.rmi.RemoteException{
    if (htmlViewServiceSoap == null)
      _initHtmlViewServiceSoapProxy();
    return htmlViewServiceSoap.getHtmlForPageWithOneReport(reportID, report, reportViewName, reportParams, reportOptions, pageParams, sessionID);
  }
  
  
}